package shark;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;


public class arrow implements sharkImage.imagefunc {
  int lastw, lasth;
  int headlen1=30,headlen2=30,headw=30, thickh=40,thickt=40,tail1=10,tail2=10,curve;
  static final int hfactor = 120,hwfactor=200,tfactor=200,thfactor=800;
  sharkImage.saveTextImage svt[];
  double rotate;
  Polygon pp;
  boolean changed;
  int lastx1,lasty1,lastw1,lasth1;
  int cx,cy;
//startPR2009-09-28^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  boolean fixedhorizontal = false;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR

  public void setup(String[] arg) {
    int i,j,k;
    for(i=1;i<arg.length;++i) {
//startPR2009-09-28^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      if(arg[i].equals(""))continue;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      int val =  Integer.parseInt(arg[i]);
      switch (i) {
          case 1: headlen1 = val;break;
          case 2: headlen2 = val;break;
          case 3: headw = val;break;
          case 4: thickh = val;break;
          case 5: thickt = val;break;
          case 6: tail1 = val;break;
          case 7: tail2 = val;break;
          case 8: curve = val;break;
//startPR2009-09-28^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          case 9: fixedhorizontal = val<0?true:false;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     }
    }
  }
  public void paint(Graphics g, int x1, int y1, int w1, int h1) {
     if(changed || pp == null || lastw1!=w1 || lasth1 != h1 || lastx1 != x1 || lasty1 != y1)
       arrowp(lastx1=x1,lasty1=y1,lastw1=w1,lasth1=h1);
       changed=false;
     ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
//startPR2009-09-28^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     java.awt.geom.AffineTransform saveAT = null;
     if(!fixedhorizontal){
        saveAT = ((Graphics2D)g).getTransform();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       ((Graphics2D)g).rotate(rotate,x1,y1);
//startPR2009-09-28^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     g.fillPolygon(pp);
//startPR2009-09-28^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     if(!fixedhorizontal)
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       ((Graphics2D)g).setTransform(saveAT);
     ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);
  }
  void arrowp(int x1,int y1,int w1,int h1) {
      int i;
      int len = (int)Math.sqrt(w1*w1+h1*h1);
      int hd1 = len*headlen1/hfactor;
      int hd2 = len*headlen2/hfactor;
      int hdw = len*headw/hwfactor;
      int th1 = len*thickh/thfactor;
      int th2 = len*thickt/thfactor;
      int ta1 = len*tail1/tfactor;
      int ta2 = len*tail2/tfactor;
      rotate = Math.atan2(h1,w1);
      pp = new Polygon();
      pp.addPoint(x1,y1);
      if(curve == 0) {
         pp.addPoint(x1+hd1,y1-hdw);
        pp.addPoint(x1+hd2,y1-th1);
        pp.addPoint(x1+len+ta1,y1-th2);
        pp.addPoint(x1+len,y1);
        pp.addPoint(x1+len+ta2,y1+th2);
        pp.addPoint(x1+hd2,y1+th1);
        pp.addPoint(x1+hd1,y1+hdw);
      }
      else {
        cx=x1+len/2;cy = y1 + (curve<0 ? len*6 + curve*len*10/100 : - len*6 + curve*len*10/100);
        if(cy==y1) cy=y1+1;
        double r1 = (int)Math.sqrt((x1-cx)*(x1-cx) + (y1-cy)*(y1-cy));
        double from = Math.atan2(y1-cy,x1-cx);
        double da = u.signedanglebetween(from,Math.atan2(y1-cy,x1+len-cx));
        if(curve < 0 && cy <y1)
          da = da + Math.PI*2;
        if(curve>0 && cy>y1)
          da = da - Math.PI*2;
        double a1 = from + da*headlen1/hfactor;
        double d1 = r1 + (double)len*headw/hwfactor;
        double a2 = from + da*headlen2/hfactor;
        double d2 = r1 + (double)len*thickh/thfactor;
        double a3 = from + da + da*tail1/tfactor;
        double d3 = r1 + (double)len*thickt/thfactor*(a3-a2)/(from+da-a2);
        double a4 = from + da + da*tail2/tfactor;
        double d4 = r1 - (double)len*thickt/thfactor*(a3-a2)/(from+da-a2);
        double a5 = from + da*headlen2/hfactor;
        double d5 = r1 - (double)len*thickh/thfactor;
        double a6 = from + da*headlen1/hfactor;
        double d6 = r1 - (double)len*headw/hwfactor;

        curve(from,a1,r1,d1);
        curve(a1,a2,d1,d2);
        curve(a2,a3,d2,d3);
        curve(a3,from+da,d3,r1);
        curve(from+da,a4,r1,d4);
        curve(a4,a5,d4,d5);
        curve(a5,a6,d5,d6);
        curve(a6,from,d6,r1);
     }
   }
   void curve(double from,double to,double rfrom, double rto) {
     int i,xx,yy,lastxx=-10000,lastyy=-10000;
     double aa,dd;
     for(i=1;i<100;++i) {
        aa = from + (to-from)*i/100;
        dd = rfrom + (rto-rfrom)*i/100;
        xx = cx+(int)(dd*Math.cos(aa));
        yy = cy+(int)(dd*Math.sin(aa));
        if(xx!=lastxx ||yy != lastyy) pp.addPoint(lastxx=xx,lastyy=yy);
     }
   }
   public JDialog edit(sharkImage.saveTextImage[] sv) {
     svt = sv;
     return new edit();
  }
  class edit extends JDialog {
    JLabel hh1 = new JLabel("Length of head(outside) :");
    JSpinner h1 = new JSpinner(new SpinnerNumberModel(headlen1,0,100,1));
    JLabel hh2 = new JLabel("Length of head(inside) :");
    JSpinner h2 = new JSpinner(new SpinnerNumberModel(headlen2,0,100,1));
    JLabel hh3 = new JLabel("Width of head :");
    JSpinner h3 = new JSpinner(new SpinnerNumberModel(headw,0,100,1));
    JLabel thh1 = new JLabel("Thickness of shaft(at head) :");
    JSpinner th1 = new JSpinner(new SpinnerNumberModel(thickh,1,100,1));
    JLabel thh2 = new JLabel("Thickness of shaft(at tail) :");
    JSpinner th2 = new JSpinner(new SpinnerNumberModel(thickt,1,100,1));
    JLabel taa1 = new JLabel("Length of outer tail :");
    JSpinner ta1 = new JSpinner(new SpinnerNumberModel(tail1,0,100,1));
    JLabel taa2 = new JLabel("Length of inside tail :");
    JSpinner ta2 = new JSpinner(new SpinnerNumberModel(tail2,0,100,1));
    JLabel cuu = new JLabel("Curvature :");
    JSpinner cu = new JSpinner(new SpinnerNumberModel(curve,-100,100,1));
    JPanel p1 = new JPanel(new GridBagLayout());
    JPanel p2 = new JPanel(new GridBagLayout());
    JPanel p3 = new JPanel(new GridBagLayout());
    JPanel p4 = new JPanel(new GridBagLayout());
    JPanel p5 = new JPanel(new GridBagLayout());
    JPanel p6 = new JPanel(new GridBagLayout());
    JPanel p7 = new JPanel(new GridBagLayout());
    JPanel p8 = new JPanel(new GridBagLayout());

    edit() {
      super(sharkStartFrame.mainFrame);
      this.setTitle("Settings for a clock");
      this.setResizable(false);
      setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
      setBounds(new Rectangle(0,
            sharkStartFrame.screenSize.height/6,
            sharkStartFrame.screenSize.width/2,
            sharkStartFrame.screenSize.height*2/3));

      h1.addChangeListener(new ChangeListener() {
        public void stateChanged(ChangeEvent e) {
          headlen1 = ((SpinnerNumberModel)(h1.getModel())).getNumber().intValue();
          save();
        }
      });
      h2.addChangeListener(new ChangeListener() {
         public void stateChanged(ChangeEvent e) {
           headlen2 = ((SpinnerNumberModel)(h2.getModel())).getNumber().intValue();
           save();
         }
      });
      h3.addChangeListener(new ChangeListener() {
          public void stateChanged(ChangeEvent e) {
            headw = ((SpinnerNumberModel)(h3.getModel())).getNumber().intValue();
            save();
          }
      });
      th1.addChangeListener(new ChangeListener() {
           public void stateChanged(ChangeEvent e) {
             thickh = ((SpinnerNumberModel)(th1.getModel())).getNumber().intValue();
             save();
           }
      });
      th2.addChangeListener(new ChangeListener() {
           public void stateChanged(ChangeEvent e) {
             thickt = ((SpinnerNumberModel)(th2.getModel())).getNumber().intValue();
             save();
           }
      });
      ta1.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
              tail1 = ((SpinnerNumberModel)(ta1.getModel())).getNumber().intValue();
              save();
            }
      });
      ta2.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
              tail2 = ((SpinnerNumberModel)(ta2.getModel())).getNumber().intValue();
              save();
            }
      });
      cu.addChangeListener(new ChangeListener() {
             public void stateChanged(ChangeEvent e) {
               curve = ((SpinnerNumberModel)(cu.getModel())).getNumber().intValue();
               save();
             }
      });
      getContentPane().setLayout(new GridBagLayout());
      GridBagConstraints grid1 = new GridBagConstraints();
      grid1.fill = GridBagConstraints.NONE;
      grid1.gridx = -1;
      grid1.gridy = 0;
      grid1.weightx = 1;
      grid1.weighty = 1;
      p1.add(hh1,grid1);
      p1.add(h1,grid1);
      p2.add(hh2,grid1);
      p2.add(h2,grid1);
      p3.add(hh3,grid1);
      p3.add(h3,grid1);
      p4.add(thh1,grid1);
      p4.add(th1,grid1);
      p5.add(thh2,grid1);
      p5.add(th2,grid1);
      p6.add(taa1,grid1);
      p6.add(ta1,grid1);
      p7.add(taa2,grid1);
      p7.add(ta2,grid1);
      p8.add(cuu,grid1);
      p8.add(cu,grid1);

      grid1.gridx = 0;
      grid1.gridy = -1;
      getContentPane().add(p1,grid1);
      getContentPane().add(p2,grid1);
      getContentPane().add(p3,grid1);
      getContentPane().add(p4,grid1);
      getContentPane().add(p5,grid1);
      getContentPane().add(p6,grid1);
      getContentPane().add(p7,grid1);
      getContentPane().add(p8,grid1);
      validate();
      setVisible(true);
    }
    void save() {
      String s[] = new String[]{svt[0].text[0],String.valueOf(headlen1),
                                               String.valueOf(headlen2),
                                               String.valueOf(headw),
                                               String.valueOf(thickh),
                                               String.valueOf(thickt),
                                               String.valueOf(tail1),
                                               String.valueOf(tail2),
                                               String.valueOf(curve)};
      svt[0].text = s;
      changed=true;
    }
  }
  public boolean keepnodeorder() {return true;}
  public void interpolate(String from[], String to[], long start, long end,long currtime) {
    if(currtime>=end) {setup(to);return;}
     int a = (int)(end-currtime), b = (int)(currtime-start),c = (int)(end-start);
     setup(from);
     if(c<=0) return;
     int saveheadlen1 = headlen1;
     int saveheadlen2 = headlen2;
     int saveheadw = headw;
     int savethickh = thickh;
     int savethickt = thickt;
     int savetail1 = tail1;
     int savetail2 = tail2;
     int savecurve = curve;
     setup(to);
     headlen1 = (saveheadlen1*a + headlen1*b)/c;
     headlen2 = (saveheadlen2*a + headlen2*b)/c;
     headw = (saveheadw*a + headw*b)/c;
     thickh = (savethickh *a + thickh*b)/c;
     thickt = (savethickt*a +  thickt*b)/c;
     tail1 = (savetail1*a +  tail1*b)/c;
     tail2 = (savetail2*a +  tail2*b)/c;
     curve = (savecurve*a +  curve*b)/c;
     pp=null;
  }
  public Rectangle getBounds() {
    if(pp != null) return sharkpoly.simplerotate(pp,lastx1,lasty1,rotate).getBounds();
    else return null;
  }
}
