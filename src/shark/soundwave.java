package shark;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

 public class soundwave extends JPanel {
   int screenwidth;
   int screenheight;
   Rectangle r = new Rectangle();
   int x1,x2;
   static final short MARGIN = 8;
   byte pickedup;
   byte[] data;
   int start,end,samplestart,sampleend;
   int beepx[]= new int[0],beepxm[]= new int[0],currbeep;
   BorderLayout layout1 = new BorderLayout();
   recordWords owner;
   boolean wantremovebeep;
   public boolean showTrim = true;
   
   public soundwave() {
       init();
   }

  public soundwave(boolean showtrim) {
      showTrim = showtrim;
      init();
  }
  
  private void init(){
     setBorder(BorderFactory.createEtchedBorder());
     addMouseListener(new java.awt.event.MouseAdapter() {
       public void mousePressed(MouseEvent e) {
         if(data == null) return;
         int x = e.getX();
         if(pickedup == 0) {
            int dist = Math.abs(x1-x);pickedup = 1;
            if(dist > Math.abs(x-x2)) { dist =  Math.abs(x-x2); pickedup=2;}
            for(int i=0;i<beepx.length;++i) {
               if(dist >= Math.abs(x-beepxm[i])) {
                  dist =  Math.abs(x-beepxm[i]);
                  pickedup=3;
                  currbeep = i;
               }
            }
         }
         if(pickedup==3 && wantremovebeep) {
            int len = beepx.length;
            int beepxx[] = new int[len-1];
            System.arraycopy(beepx,0,beepxx,0,currbeep);
            if(currbeep < len)
                System.arraycopy(beepx,currbeep,beepxx,currbeep,len-currbeep);
            beepx=beepxx;
            beepxx = new int[len-1];
            System.arraycopy(beepxm,0,beepxx,0,currbeep);
            if(currbeep < len)
                System.arraycopy(beepxm,currbeep,beepxx,currbeep,len-currbeep);
            beepxm=beepxx;
            pickedup = 0;
         }
         wantremovebeep = false;
      }
      public void mouseReleased(MouseEvent e) {
        if(data == null) return;
         int x = e.getX();
         switch(pickedup) {
            case 0:return;
            case 1:
               start = Math.max(0,Math.min(data.length, (x-MARGIN)*data.length/(getWidth()-MARGIN*2)));
               end = Math.max(end,start);
               break;
            case 2:
               end = Math.max(0,Math.min(data.length, (x-MARGIN)*data.length/(getWidth()-MARGIN*2)));
               start = Math.min(end,start);
               break;
            case 3:
              beepx[currbeep] = Math.max(0,Math.min(data.length, (x-MARGIN)*data.length/(getWidth()-MARGIN*2)));
              break;
         }
         pickedup=0;
         if(owner != null) owner.setButtons();
         repaint();
      }
    });
    addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
      public void mouseDragged(MouseEvent e) {
        if(data == null) return;
         int x = e.getX();
         switch(pickedup) {
            case 0:return;
            case 1:
               start = Math.max(0,Math.min(data.length, (x-MARGIN)*data.length/(getWidth()-MARGIN*2)));
               end = Math.max(end,start);
               break;
            case 2:
              end = Math.max(0,Math.min(data.length, (x-MARGIN)*data.length/(getWidth()-MARGIN*2)));
               start = Math.min(end,start);
               break;
            case 3:
               beepx[currbeep] = Math.max(0,Math.min(data.length, (x-MARGIN)*data.length/(getWidth()-MARGIN*2)));
               break;
         }
         repaint();
      }
    });      
  }
  //-----------------------------------------------------------------------
  public void setup(byte data1[]) {
         data = data1;
         start = 0;end = data.length;beepx = new int[0];beepxm=new int[0] ;
         repaint();
  }
  //-----------------------------------------------------------------------
 public void setup(byte data1[],int len) {
        data = data1;
        start = 0;end = len;beepx = new int[0];beepxm=new int[0] ;
        repaint();
 }
 //-----------------------------------------------------------------------
  public byte[] getsound() {
     pickedup = 0;
     int dlen = end-start,i, j,k;
     byte data1[];
     boolean endmarker=false;
     if(dlen != data.length) {
        data1 = new byte[dlen];
        System.arraycopy(data,start,data1,0,dlen);
     }
     else data1 = data;
     java.util.Arrays.sort(beepx);
     int extra=0;
     for(i=0;i<beepx.length;++i) {
         byte mark[] = noise.marker2();
         byte newdata[] = new byte[dlen+mark.length];
         int beepxx =beepx[i]-start+extra;
         if(beepxx <= 0) {
               System.arraycopy(mark,0,newdata,0,mark.length);
               System.arraycopy(data1,0,newdata,mark.length,dlen);
         }
         else if(beepxx >= data1.length) {
               System.arraycopy(data1,0,newdata,0,dlen);
               System.arraycopy(mark,0,newdata,dlen,mark.length);
               endmarker=true;
         }
         else{
               System.arraycopy(data1,0,newdata,0,beepxx);
               System.arraycopy(mark,0,newdata,beepxx,mark.length);
               System.arraycopy(data1,beepxx,newdata,beepxx+mark.length,dlen-beepxx);
         }
         dlen = newdata.length;
         extra += mark.length;
         data1 = newdata;
         mark = null;
      }
      if(!endmarker && data1 !=data) for(i=data1.length-1,j=0; j< noise.fade; --i,++j) {
                  k = (int)data1[i] & 0x00ff;
                  data1[i] = 0;
                  data1[i] |= (k-127)*j/noise.fade + 127;
      }
      return data1;
  }
  //-----------------------------------------------------------------------
  public void addbeep() {
    pickedup = 0;
    beepx = u.addint(beepx,0);
    beepxm = u.addint(beepxm,0);
    currbeep = beepx.length-1;
    beepx[currbeep] = (start+end)/2;
    repaint();
  }
  //-----------------------------------------------------------------------
  public void removebeep() {
      wantremovebeep = true;
      pickedup=0;
      beepx = new int[0];beepxm=new int[0] ;
      currbeep=0;
      repaint();
  }
  //-----------------------------------------------------------------------
  public void clear() {
         data = null;
         repaint();
  }
  void drawwave(Graphics g, int start, int end, int x1 ,int y1, int x2, int y2, Color fg) {
     int i,x,currx=0,tot = end-start,width=x2-x1,height=y2-y1;
     g.setColor(fg);
     if(tot>=width) {
       short curr,max=0,min=255;
       for(i=start;i<end;++i) {
          x = x1 + (i-start)*width/tot;
          if(x != currx) {
             g.drawLine(currx,y2 - max*height/255, currx, y2 - min*height/255);
             currx = x;
             max=0;
             min=255;
          }
          curr = (short)(((short)data[i]) & 0x00ff);
          max = (short)Math.max(max,curr);
          min = (short)Math.min(min,curr);
        }
        g.drawLine(currx,y2 - max*height/255, currx, y2 - min*height/255);
     }
     else {
       int curr = ((short)data[start]) & 0x00ff;
       currx = x1;
       int curry = y2 - curr*height/255,y;
       for(i=start+1;i<end;++i) {
          curr = ((short)data[i]) & 0x00ff;
          x = x1 + (i-start)*width/tot;
          y = y2 - curr*height/255;
          g.drawLine(currx, curry, x, y);
          currx = x;
          curry = y;
        }

     }
  }
  public void paint(Graphics g) {
      Graphics2D g2d = (Graphics2D)g;
        int h1,w1,x;
        super.paint(g);
        if(data == null || data.length==0) return;
        g2d.setColor(Color.white);
        g2d.fillRect(0,0,w1=getWidth(),h1=getHeight());
        x = MARGIN;
        w1 -= MARGIN*2;
        int y1 = h1;
        x1 = x+w1*start/data.length;
        x2 = x+w1*end/data.length;
        for(int i=0;i<beepx.length;++i) {
           beepxm[i] = x+w1*beepx[i]/data.length;
        }
        drawwave(g2d, 0, data.length,
                      x,  0,   x+w1, y1,
                      Color.black);
        if(showTrim){
        g2d.setStroke(new BasicStroke(3.0f));
        g2d.setColor(Color.blue);

        g2d.drawLine(x1,0,x1,y1);
        g2d.drawLine(x2,0,x2,y1);
        }
        g2d.setStroke(new BasicStroke(1.0f));
        for(int i=0;i<beepx.length;++i) {
            int x3 = x+w1*beepx[i]/data.length;
            g.setColor(Color.red);
            g.drawLine(x3,0,x3,y1);
        }
   }
}