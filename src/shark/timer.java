package shark;
import java.awt.*;


public class timer extends mover {
   int divs;
   long starttime = System.currentTimeMillis();
   public timer(int secs) {
     divs=secs;
     w=mover.WIDTH/12;
     h=mover.HEIGHT/8;
   }
   public void paint(Graphics g, int x1, int y1, int w1, int h1) {
     ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
      int ww = Math.min(w1,h1)/2;
      int xx = x1 + w1/2;
      int yy = y1 + h1/2;
      int time = Math.min(divs,(int)((System.currentTimeMillis() - starttime)/1000));
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
      for (i=0; i<divs; ++i) {
          ((Graphics2D)g).setTransform(saveAT);
          ((Graphics2D)g).rotate(Math.PI * 2 * i / divs,xx,yy);
          if(i%5 == 0) {
            g.fillRect(xx-1,yy-ww,3,th1);
          }
          else g.drawLine(xx,yy-ww+th2,xx,yy-ww);
      }
      ((Graphics2D)g).setTransform(saveAT);
      ((Graphics2D)g).rotate(Math.PI*2*time/(divs),xx,yy);
       g.drawLine(xx,yy-ww+th3, xx,yy+ww/6);
      int mm = Math.max(3,ww/40*2 + 1);
      g.fillOval(xx-mm, yy-mm, mm*2, mm*2);

      ((Graphics2D)g).setTransform(saveAT);
      ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);
   }

}