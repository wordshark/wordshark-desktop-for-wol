package shark;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;



public class clock_base implements sharkImage.imagefunc {
  int hour, minute, second, timespeed, markint = 1, marknum = 3;
  boolean digital, markminutes, secondhand, is24hr, actualtime, ampm, pm;
  Font f;
  FontMetrics m;
  int lastw, lasth;
  sharkImage.saveTextImage svt[];
  long starttime = sharkGame.gtime();
  // digital patterns 0-9,A,P     0
  //                             3 5
  //                              1
  //                             4 6
  //                              2
  // digital pattern m        0 7
  //                         3 5 8
  //                          1
  //                         4 6 9
  //                          2
  static int digpat[][] = new int[][] {
      new int[] {0, 5, 6, 2, 4, 3},
      new int[] {5, 6},
      new int[] {0, 5, 1, 4, 2},
      new int[] {0, 5, 1, 6, 2},
      new int[] {3, 1, 5, 6},
      new int[] {0, 3, 1, 6, 2},
      new int[] {0, 3, 1, 4, 2, 6},
      new int[] {0, 5, 6},
      new int[] {0, 3, 5, 1, 4, 6, 2},
      new int[] {0, 3, 1, 5, 6, 2}};
  static int apat[] = new int[] {0, 5, 6, 1, 4, 3};
  static int ppat[] = new int[] {0, 5, 1, 4, 3};
  public void setup(String[] arg) {
    int i,j,k;
    for(i=1;i<arg.length;++i) {
      if(arg[i].startsWith("time=")) {
        j = arg[i].indexOf(':');
        k = arg[i].lastIndexOf(':');
        hour = Integer.parseInt(arg[i].substring(5,j));
        minute = Integer.parseInt(arg[i].substring(j+1,k));
        second = Integer.parseInt(arg[i].substring(k+1));
      }
      else if(arg[i].startsWith("timespeed=")) {
         timespeed = Integer.parseInt(arg[i].substring(10));
      }
      else if(arg[i].startsWith("markint=")) {
         markint = Integer.parseInt(arg[i].substring(8));
      }
      else if(arg[i].startsWith("marknum=")) {
         marknum = Integer.parseInt(arg[i].substring(8));
      }
      else if(arg[i].startsWith("digital")) {
        digital=true;
      }
      else if(arg[i].startsWith("markminutes")) {
        markminutes=true;
      }
      else if(arg[i].startsWith("secondhand")) {
        secondhand=true;
      }
      else if(arg[i].startsWith("actualtime")) {
        actualtime=true;
      }
      else if(arg[i].startsWith("is24hr")) {
        is24hr=true;
      }
      else if(arg[i].startsWith("ampm")) {
        ampm=true;
      }
      else if(arg[i].startsWith("pm")) {
        pm=true;
      }
      starttime = sharkGame.gtime();
    }
  }
  public void paint(Graphics g, int x1, int y1, int w1, int h1) {
    ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
     int ww = Math.min(w1,h1)/2;
     int xx = x1 + w1/2;
     int yy = y1 + h1/2;
     long time;
     if(actualtime) {
       Calendar cc = Calendar.getInstance();
       time = cc.get(Calendar.SECOND) + (long)cc.get(Calendar.MINUTE)*60
           +  (long)cc.get(Calendar.HOUR_OF_DAY)*3600;
     }
     else time = hour*60*60 + minute*60 + second + (timespeed*(sharkGame.gtime() - starttime))/1000;
     if(digital) {
       int ww1, ww2, hh, hh1, xx1, xx2;
       if(!is24hr && (ampm||pm) ) {           // add AM/PM at end
         String pms = time <= 12*60*60 ? "am":"pm";
         int pmwidth = secondhand ? w1*7/25 : w1*7/19;
         ww = (w1-pmwidth) / 2;
         xx = x1 + ww;
         ww1 = secondhand ? ww / 2 : ww;
         xx1 = secondhand ? xx - ww * 3 / 8 : xx;
         xx2 = xx + ww * 9 / 16;
         ww2 = ww * 5 / 16;
         hh = Math.min(ww1 / 5 * 2, h1 / 2);
         hh1 = hh * 15 / 16;
         g.setColor(Color.white);
         g.fillRoundRect(x1,yy-hh,w1,hh*2,hh/2,hh/2);
         g.setColor(Color.black);
         g.drawRoundRect(x1,yy-hh,w1,hh*2,hh/2,hh/2);
         if(ampm || pm && time > 12*60*60) drawDigital(g,pms, x1+w1-pmwidth * 11/20, yy, pmwidth * 4/10, hh1);
       }
       else {
         ww = w1 / 2;
         ww1 = secondhand ? ww / 2 : ww;
         xx1 = secondhand ? xx - ww * 3 / 8 : xx;
         xx2 = xx + ww * 9 / 16;
         ww2 = ww * 5 / 16;
         hh = Math.min(ww1 / 5 * 2, h1 / 2);
         hh1 = hh * 15 / 16;
         g.setColor(Color.white);
         g.fillRoundRect(xx-ww,yy-hh,ww*2,hh*2,hh/2,hh/2);
         g.setColor(Color.black);
         g.drawRoundRect(xx-ww,yy-hh,ww*2,hh*2,hh/2,hh/2);
       }
       if(secondhand) {
         g.drawRoundRect(xx1-ww1,yy-hh1,ww1*2,hh1*2,hh1/2,hh1/2);
         g.drawRoundRect(xx2-ww2,yy-hh1,ww2*2,hh1*2,hh1/2,hh1/2);
       }
       String sh = String.valueOf(time/3600%(digital && is24hr?24:12));
       String sm = String.valueOf(time/60%60);
       if(sm.length()<2) sm = "0"+sm;
       if(!is24hr && sh.equals("0"))sh  = "12";
       if(sh.length()<2) sh = " " + sh;
       drawDigital(g,sh+"-"+sm, xx1, yy, ww1,hh1);
       if(secondhand) {
         if(!actualtime) time =   second  + (sharkGame.gtime() - starttime)/1000;
         String ss = String.valueOf(time%60);
         if(ss.length() < 2) ss = " "+ss;
         drawDigital(g,ss , xx2, yy, ww2, hh1);
       }
       return;
     }
     java.awt.geom.AffineTransform saveAT = ((Graphics2D)g).getTransform();
     int i,midx,midy,dx,dy;
     double a;
     int th1 = 3 + ww/24*2;
     int th2 = 1 + ww/32*2;
     int th3 = 1 + ww/40*2;
     g.setColor(Color.white);
     g.fillOval(xx-ww,yy-ww,ww*2,ww*2);
     g.setColor(Color.black);
     g.drawOval(xx-ww,yy-ww,ww*2,ww*2);
     if(markminutes) {
       for (i=0; i<60; ++i) {
         ((Graphics2D)g).setTransform(saveAT);
         ((Graphics2D)g).rotate(Math.PI * i / 30,xx,yy);
         if(i%5 == 0) {
//startPR2009-09-28^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//           g.fillRect(xx-1,yy-ww,3,th1);
           g.fillRect(xx-1,yy-ww,Math.max(th1/3, 1),th1);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         }
         else g.drawLine(xx,yy-ww+th2,xx,yy-ww);
       }
     }
     else if(markint > 0) {
         for (i=0; i<12; i += markint) {
           ((Graphics2D)g).setTransform(saveAT);
           ((Graphics2D)g).rotate(Math.PI * i /6,xx,yy);
//startPR2009-09-28^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//           g.fillRect(xx-1,yy-ww,3,th1);
           g.fillRect(xx-1,yy-ww,Math.max(th1/3, 1),th1);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         }
     }
     ((Graphics2D)g).setTransform(saveAT);
     if(marknum > 0) {
       if(f == null || lastw != w1 || lasth != h1) {
         f = sharkGame.sizePlainFont(new String[] {"12"},ww/4, ww/4, sharkStartFrame.MAXFONTPOINTS_3, sharkStartFrame.treefont );
         m = sharkStartFrame.mainFrame.getFontMetrics(f);
         lastw = w1;
         lasth = h1;
       }
       g.setFont(f);
       String s;
       int hh = m.getAscent();
       for (i=marknum; i<=12; i += marknum) {
        s = String.valueOf(i);
        int sw = m.stringWidth(s);
        a = Math.PI * i / 6;
        midx = xx + (int) ( (ww - th1 - th1 ) * Math.sin(a));
        midy = yy - (int) ( (ww - th1 - th1 ) * Math.cos(a));
        g.drawString(s, midx - sw / 2, midy + hh / 2 );
       }
     }
     ((Graphics2D)g).rotate(Math.PI*2*time/(60*60*12),xx,yy);
//startPR2009-09-28^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//     g.fillPolygon(clockhand(xx,yy,ww*2/3,6));
      int wid = ww*2/3;
      g.fillPolygon(clockhand(xx,yy,wid,Math.max(wid/10, 2)));
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2009-09-28^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//     g.fillOval(xx-6, yy-ww/2,12,ww/4);
      wid = (ww/4)/2;
      g.fillOval(xx-wid/2, yy-ww/2,(ww/4)/2,ww/4);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     ((Graphics2D)g).setTransform(saveAT);
     ((Graphics2D)g).rotate(Math.PI*2*time/(60*60),xx,yy);
//startPR2009-09-28^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//     g.fillPolygon(clockhand(xx,yy,ww-th2,4));
      wid = Math.max(ww-th2,1);
      g.fillPolygon(clockhand(xx,yy,wid,Math.max(wid/16, 2)));
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     if(secondhand) {
       if(!actualtime) time =   second  + (sharkGame.gtime() - starttime)/1000;
       ((Graphics2D)g).setTransform(saveAT);
       ((Graphics2D)g).rotate(Math.PI*2*time/(60),xx,yy);
       g.drawLine(xx,yy-ww+th3, xx,yy+ww/6);
     }
//startPR2009-09-28^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//     int mm = Math.max(3,ww/40*2 + 1);
     int mm = Math.max(2,ww/40*2 + 1);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     g.fillOval(xx-mm, yy-mm, mm*2, mm*2);
     ((Graphics2D)g).setTransform(saveAT);
     ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);
  }
  //--------------------------------------------------------------------------------------------
  void drawDigital(Graphics g, String s, int x1,int y1,int w1,int h1) {   // x1,y1 are middle of box; w1/h1 are half width/ht
     int chw = Math.min(w1*2/s.length(), h1);
     int usew = chw*2/3;
     int dd = chw/6;
     int i,j,xx,x2,y2;
     char c[] = s.toCharArray();
     int pa[] = null;
     for(i=0,xx = x1-chw*c.length/2;i<c.length;++i,xx+=chw) {
       pa = null;
       switch(c[i]) {
         case ' ':
           continue;
         case '-':
           horz(g, xx + chw / 2 - usew / 2, y1, xx + chw / 2 + usew / 2, y1,dd); break;
         case 'm':
           horz(g, xx + chw / 2 - usew / 2, y1-usew, xx + chw / 2 + usew / 2, y1-usew,dd);
           horz(g, xx + chw / 2 + usew  - usew / 2, y1-usew, xx + chw / 2 + usew + usew / 2, y1-usew,dd);
           vert(g, xx + chw / 2 - usew / 2, y1-usew, xx, y1, dd);
           vert(g, xx + chw / 2 - usew / 2, y1, xx, y1+usew, dd);
           vert(g, xx+usew + chw / 2 - usew / 2, y1-usew, xx+chw, y1, dd);
           vert(g, xx+usew + chw / 2 - usew / 2, y1, xx+chw, y1+usew, dd);
           vert(g, xx+usew*2 + chw / 2 - usew / 2, y1-usew, xx+chw*2, y1, dd);
           vert(g, xx+usew*2 + chw / 2 - usew / 2, y1, xx+chw*2, y1+usew, dd);
           break;
         case 'a': pa = apat;     // and drop thru
         case 'p': if(pa == null) pa = ppat;   //and drop thru
         default:
           if(pa==null) pa = digpat[c[i] - '0'];
           for (j = 0; j < pa.length;++j) {
             if (pa[j] < 3) {
               y2 = y1 - usew + pa[j] * usew;
               horz(g, xx + chw / 2 - usew / 2, y2, xx + chw / 2 + usew / 2, y2, dd);
             }
             else {
               y2 = y1 - usew + (pa[j] - 3) % 2 * usew;
               x2 = xx +chw/2 -usew / 2 + (pa[j] - 3) / 2 * usew;
               vert(g, x2, y2, x2, y2 + usew, dd);
             }
           }
       }
     }
  }
  void horz(Graphics g,int x1,int y1,int x2,int y2,int dd) {
     if(dd==0) g.drawLine(x1,y1,x2,y2);
     else {
       int dd1 = dd/2;
       int dd2 = (dd+1)/2;
       x1 += 1; x2 -= 1;
       g.fillPolygon(new int[] {x1, x1 + dd2, x2 - dd2, x2, x2 - dd2, x1 + dd2},
                     new int[] {y1, y1 - dd1, y1 - dd1, y1, y1 + dd2, y1 + dd2}, 6);

     }
  }
  void vert(Graphics g,int x1,int y1,int x2,int y2,int dd) {
    if(dd==0) g.drawLine(x1,y1,x2,y2);
    else {
      int dd1 = dd/2;
      int dd2 = (dd+1)/2;
//      y1 += 1; y2 -=1;
      g.fillPolygon(new int[] {x1, x1 + dd2, x1 + dd2, x1, x1 - dd1, x1 - dd1},
                    new int[] {y1, y1 + dd2, y2 - dd2, y2, y2 - dd2, y1 + dd2}, 6);
    }
  }
  //----------------------------------------------------------------------
  static Polygon clockhand(int x1,int y1,int ww,int thick) {
     int dx = thick/2;
     int dx2 = (thick)/2;
     int y2 = y1-ww;
     return  new Polygon(new int[] {x1-dx,x1+dx2,x1+dx2,x1,x1-dx},
                         new int[] {y1,y1,y2+thick*2,y2,y2+thick*2},5);
  }
  public JDialog edit(sharkImage.saveTextImage[] sv) {
     svt = sv;
     return new edit();
  }
  class edit extends JDialog {
    JCheckBox jdig = new JCheckBox("Digital ?",digital);
    JCheckBox jampm = new JCheckBox("Show AM/PM ?",ampm);
    JCheckBox jpm = new JCheckBox("Show just PM ?",pm);
    JCheckBox jmin = new JCheckBox("Mark minutes ?",markminutes);
    JCheckBox jsec = new JCheckBox("Second hand ?",secondhand);
    JCheckBox jactualtime = new JCheckBox("Show actual time of day ?",actualtime);
    JCheckBox j24hr = new JCheckBox("24 hour clock ?",is24hr);
    JLabel time1 = new JLabel("Time : Hours :");
    JSpinner hour1 = new JSpinner(new SpinnerNumberModel(hour,0,24,1));
    JLabel time2 = new JLabel("Mins :");
    JSpinner minute1 = new JSpinner(new SpinnerNumberModel(minute,0,59,1));
    JLabel time3 = new JLabel("Secs :");
    JSpinner second1 = new JSpinner(new SpinnerNumberModel(second,0,59,1));
    JLabel speed1 = new JLabel("Speed :");
    JSpinner timespeed1 = new JSpinner(new SpinnerNumberModel(timespeed,0,60*60*12,1));
    JLabel mark1 = new JLabel("Interval for hour markings :");
    JSpinner markint1 = new JSpinner(new SpinnerNumberModel(markint,0,12,1));
    JLabel number1 = new JLabel("Interval for hour numbering :");
    JSpinner marknum1 = new JSpinner(new SpinnerNumberModel(marknum,0,12,1));
    JPanel p1 = new JPanel(new GridBagLayout());
    JPanel p2 = new JPanel(new GridBagLayout());
    JPanel p3 = new JPanel(new GridBagLayout());
    JPanel p4 = new JPanel(new GridBagLayout());

    edit() {
      super(sharkStartFrame.mainFrame);
      this.setTitle("Settings for a clock");
      this.setResizable(false);
      setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
      setBounds(new Rectangle(0,
            sharkStartFrame.screenSize.height/6,
            sharkStartFrame.screenSize.width/2,
            sharkStartFrame.screenSize.height*2/3));

      hour1.addChangeListener(new ChangeListener() {
        public void stateChanged(ChangeEvent e) {
          hour = ((SpinnerNumberModel)(hour1.getModel())).getNumber().intValue();
          starttime = sharkGame.gtime();
          save();
        }
      });
      minute1.addChangeListener(new ChangeListener() {
        public void stateChanged(ChangeEvent e) {
          minute = ((SpinnerNumberModel)(minute1.getModel())).getNumber().intValue();
          starttime = sharkGame.gtime();
          save();
        }
      });
      second1.addChangeListener(new ChangeListener() {
        public void stateChanged(ChangeEvent e) {
          second = ((SpinnerNumberModel)(minute1.getModel())).getNumber().intValue();
          starttime = sharkGame.gtime();
          save();
        }
      });
      timespeed1.addChangeListener(new ChangeListener() {
        public void stateChanged(ChangeEvent e) {
          timespeed = ((SpinnerNumberModel)(timespeed1.getModel())).getNumber().intValue();
          starttime = sharkGame.gtime();
          save();
        }
      });
      markint1.addChangeListener(new ChangeListener() {
        public void stateChanged(ChangeEvent e) {
          markint = ((SpinnerNumberModel)(markint1.getModel())).getNumber().intValue();
          save();
        }
      });
      marknum1.addChangeListener(new ChangeListener() {
        public void stateChanged(ChangeEvent e) {
          marknum = ((SpinnerNumberModel)(marknum1.getModel())).getNumber().intValue();
          save();
        }
      });
      jdig.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
           digital = jdig.isSelected();
           save();
        }
      });
      jampm.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
           ampm = jampm.isSelected();
           if(ampm) {pm = false;jpm.setSelected(false);}
           save();
        }
      });
      jpm.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
           pm = jpm.isSelected();
           if(pm) {ampm = false;jampm.setSelected(false);}
           save();
        }
      });
      jmin.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
           markminutes = jmin.isSelected();
           save();
        }
      });
      jsec.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
           secondhand = jsec.isSelected();
           save();
        }
      });
      jactualtime.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
           actualtime = jactualtime.isSelected();
           save();
        }
      });
      j24hr.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
           is24hr = j24hr.isSelected();
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
      p1.add(time1,grid1);
      p1.add(hour1,grid1);
      p1.add(time2,grid1);
      p1.add(minute1,grid1);
      p1.add(time3,grid1);
      p1.add(second1,grid1);
      p2.add(speed1,grid1);
      p2.add(timespeed1,grid1);
      p3.add(mark1,grid1);
      p3.add(markint1,grid1);
      p4.add(number1,grid1);
      p4.add(marknum1,grid1);

      grid1.gridx = 0;
      grid1.gridy = -1;
      getContentPane().add(jdig,grid1);
      getContentPane().add(j24hr,grid1);
      getContentPane().add(jampm,grid1);
      getContentPane().add(jpm,grid1);
      getContentPane().add(jsec,grid1);
      getContentPane().add(jactualtime,grid1);
      getContentPane().add(p1,grid1);
      getContentPane().add(p2,grid1);
      getContentPane().add(jmin,grid1);
      getContentPane().add(p3,grid1);
      getContentPane().add(p4,grid1);
      setbuttons();
      validate();
      setVisible(true);
    }
    void setbuttons() {
      jmin.setVisible(!digital);
      p3.setVisible(!digital && !jmin.isSelected());
      p4.setVisible(!digital);
      p1.setVisible(!jactualtime.isSelected());
      p2.setVisible(!jactualtime.isSelected());
      j24hr.setVisible(digital);
      jampm.setVisible(!is24hr && digital);
      jpm.setVisible(!is24hr && digital);
    }
    void save() {
      String s[] = new String[]{svt[0].text[0]};
      if(digital) s = u.addString(s,"digital");
      if(ampm) s = u.addString(s,"ampm");
      if(pm) s = u.addString(s,"pm");
      if(markminutes) s = u.addString(s,"markminutes");
      if(secondhand) s = u.addString(s,"secondhand");
      if(actualtime) s = u.addString(s,"actualtime");
      if(is24hr) s = u.addString(s,"is24hr");
      if(hour>0 || minute > 0) s = u.addString(s,"time="+String.valueOf(hour)+ ':' + String.valueOf(minute)+
                                               ':' + String.valueOf(second));
      if(timespeed>0) s = u.addString(s,"timespeed="+String.valueOf(timespeed));
      if(markint>0) s = u.addString(s,"markint="+String.valueOf(markint));
      if(marknum>0) s = u.addString(s,"marknum="+String.valueOf(marknum));
      svt[0].text = s;
      setbuttons();
    }
  }
  public boolean keepnodeorder() {return false;}
  public void interpolate(String from[], String to[], long start, long end,long currtime) {
     if(currtime>=end) {setup(to);return;}
     if(actualtime || timespeed != 0) return;
     int a = (int)(end-currtime), b = (int)(currtime-start),c = (int)(end-start);
     setup(from);
     if(c<=0) return;
     int time1 = hour*60*60 + minute*60 + second;
     setup(to);
     int time2 =  hour*60*60 + minute*60 + second;
     second = (time1*a + time2*b)/c;
     hour = minute =0 ;
  }
  public Rectangle getBounds() {return null;}
}
