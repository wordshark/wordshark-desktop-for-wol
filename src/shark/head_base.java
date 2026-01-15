package shark;
import java.awt.*;

public class head_base extends mover {
   public boolean lookleft,lookright,roundnose;
   public byte happiness;   // scale to 16
       // point2d refers to frontal view
   public point3d_base top,eye,nosetop,nosetip,nosebot,noseside,chin,
                 neck,back,mouth;
   int eyew,eyeh, mouthh;
   sharkpoly leftpoly,rightpoly,frontpoly;
   Color eyecolor = u.eyecolor();
   Color mouthcolor = u.mouthcolor();
   Color haircolor = u.haircolor();
   int hairlen;
   short hairtot  =  (short)(16 + u.rand(8));
   hair hairs[] = new hair[hairtot];
   public head_base(int w1, int h1) {
      super(false);
      roundnose = (u.rand(2)  == 0);
      happiness = 8;
      w = w1;
      h = h1;
      top = new point3d_base(0,0,0);
      nosetop = new point3d_base(0, h/4 + u.rand(h/8), w/4 + u.rand(w/8));
      nosetip = new point3d_base(0, nosetop.y +h/8 + u.rand(h/4), Math.min(w/2,nosetop.z+w/8 + u.rand(w/8)));
      nosebot = new point3d_base(w/16 + u.rand(w/32), nosetip.y  + u.rand(h/16), w/4 + u.rand(w/8));
      noseside = new point3d_base(nosebot.x + u.rand(w/32), nosetip.y-h/16 ,nosebot.z);
      eye = new point3d_base(w/7 + u.rand(h/12),nosetop.y+u.rand(h/8), nosebot.z - w/8);
      eyew = w/3 + u.rand(w/16);
      eyeh = h/6 + u.rand(h/18);
      chin = new point3d_base(w*3/8 -  u.rand(w/16),h-u.rand(h/12), nosebot.z);
      mouth = new point3d_base(chin.x*3/4,(chin.y+nosebot.y)/2, nosebot.z-w/4);
      mouthh = (chin.y - nosebot.y)/2;
      neck = new point3d_base(chin.z/3+u.rand(chin.z/3),h,chin.z/3+u.rand(chin.z/3));
      back = new point3d_base(w*7/16, h/3+u.rand(h/3), -w*7/16);

      leftpoly = new sharkpoly();
      u.drawcurve(leftpoly, top.z, top.y, Math.PI, -nosetop.z, nosetop.y,Math.PI/2,5);
      if(roundnose) {
         u.drawcurve(leftpoly, -nosetop.z, nosetop.y,Math.PI, -nosetip.z, nosetip.y,Math.PI/2,5);
         u.drawcurve(leftpoly, -nosetip.z, nosetip.y,Math.PI/2, -nosebot.z, nosebot.y,0,5);
      }
      else {
         leftpoly.addPoint(-nosetip.z, nosetip.y);
         leftpoly.addPoint(-nosebot.z, nosebot.y);
      }
      u.drawcurve(leftpoly,  -nosebot.z, mouth.y, Math.PI/2, -chin.z, chin.y, Math.PI/2,5);
      u.drawcurve(leftpoly,  -chin.z, chin.y, Math.PI/2, -neck.z, neck.y,(double)0,5);
      leftpoly.addPoint( neck.z, neck.y);
      u.drawcurve(leftpoly,  neck.z, neck.y, -Math.PI*3/8, -back.z, back.y,-Math.PI/2,5);
      u.drawcurve(leftpoly,   -back.z, back.y,-Math.PI/2,top.z,top.y,Math.PI,5);

      rightpoly = new sharkpoly();
      u.drawcurve(rightpoly, top.z, top.y, 0, nosetop.z, nosetop.y,Math.PI/2,5);
      if(roundnose) {
         u.drawcurve(rightpoly, nosetop.z, nosetop.y,0, nosetip.z, nosetip.y,Math.PI/2,5);
         u.drawcurve(rightpoly, nosetip.z, nosetip.y,Math.PI/2, nosebot.z, nosebot.y,Math.PI/2,5);
      }
      else {
         rightpoly.addPoint(nosetip.z, nosetip.y);
         rightpoly.addPoint(nosebot.z, nosebot.y);
      }
      u.drawcurve(rightpoly,  nosebot.z, mouth.y, Math.PI/2, chin.z, chin.y, Math.PI/2,5);
      u.drawcurve(rightpoly,  chin.z, chin.y, Math.PI/2, neck.z, neck.y,Math.PI,5);
      rightpoly.addPoint( -neck.z, neck.y);
      u.drawcurve(rightpoly,  -neck.z, neck.y, -Math.PI*5/8, back.z, back.y,-Math.PI/2,5);
      u.drawcurve(rightpoly,   back.z, back.y,-Math.PI/2,top.z,top.y,0,5);

      frontpoly = sharkpoly.oval(0,h/2,back.x,h/2,32);

      hairlen = w/12+u.rand(w/8);
      for(short i=0;i<hairtot;++i) {
         hairs[i] = new hair();
      }
   }
      //----------------------------------------------------------
   public void paint(Graphics g, int x1, int y1,int w1,int h1) {
         int currmouthh = (happiness-8)*mouthh/16;
         int mouthmid = mouth.y + currmouthh;
         int mouthedge = mouth.y - currmouthh;
         if(lookleft) {
            g.setColor(Color.white);
            g.fillPolygon(leftpoly.translate(x+w/2,y,manager.screenwidth,manager.screenheight));
            g.setColor(mouthcolor);
            g.drawLine((x+w/2-nosebot.z)*manager.screenwidth/mover.WIDTH, (y+mouthmid)*manager.screenheight/mover.HEIGHT,
                       (x+w/2-mouth.z)*manager.screenwidth/mover.WIDTH, (y+mouthedge)*manager.screenheight/mover.HEIGHT);
            g.setColor(eyecolor);
            g.fillOval((x+w/2-eye.z-eyew/2)*manager.screenwidth/mover.WIDTH, (y+eye.y-eyeh/2)*manager.screenheight/mover.HEIGHT,
                        eyew*manager.screenwidth/mover.WIDTH, eyeh*manager.screenheight/mover.HEIGHT);
         }
         else if(lookright) {
            g.setColor(Color.white);
            g.fillPolygon(rightpoly.translate(x+w/2,y,manager.screenwidth,manager.screenheight));
            g.setColor(mouthcolor);
            g.drawLine((x+w/2+nosebot.z)*manager.screenwidth/mover.WIDTH, (y+mouthmid)*manager.screenheight/mover.HEIGHT,
                       (x+w/2+mouth.z)*manager.screenwidth/mover.WIDTH, (y+mouthedge)*manager.screenheight/mover.HEIGHT);
            g.setColor(eyecolor);
            g.fillOval((x+w/2+eye.z-eyew/2)*manager.screenwidth/mover.WIDTH, (y+eye.y-eyeh/2)*manager.screenheight/mover.HEIGHT,
                        eyew*manager.screenwidth/mover.WIDTH, eyeh*manager.screenheight/mover.HEIGHT);
         }
         else {     // frontal
            g.setColor(Color.white);
            g.fillPolygon(frontpoly.translate(x+w/2,y,manager.screenwidth,manager.screenheight));
            g.setColor(mouthcolor);
            int mouthyy = (mouthedge+mouthmid*2)/3;
           (new sharkpoly(new int[] {-mouth.x,-mouth.x/2,0,mouth.x/2,mouth.x},
                                  new int[] {mouthedge,mouthyy,mouthmid,mouthyy,mouthedge},5))
                                    .translate(x+w/2,y,manager.screenwidth,manager.screenheight)
                                    .drawline(g);
            g.setColor(eyecolor);
            g.fillOval((x+w/2-eye.x-eyew/2)*manager.screenwidth/mover.WIDTH, (y+eye.y-eyeh/2)*manager.screenheight/mover.HEIGHT,
                        eyew*manager.screenwidth/mover.WIDTH, eyeh*manager.screenheight/mover.HEIGHT);
            g.fillOval((x+w/2+eye.x-eyew/2)*manager.screenwidth/mover.WIDTH, (y+eye.y-eyeh/2)*manager.screenheight/mover.HEIGHT,
                        eyew*manager.screenwidth/mover.WIDTH, eyeh*manager.screenheight/mover.HEIGHT);
            g.setColor(Color.black);
            if(roundnose) {
               (new sharkpoly(new int[] {-noseside.x,-noseside.x/2,0,noseside.x/2,noseside.x},
                                  new int[] {noseside.y,(noseside.y+nosebot.y*2)/3,nosebot.y,(noseside.y+nosebot.y*2)/3,noseside.y},5))
                                    .translate(x+w/2,y,manager.screenwidth,manager.screenheight)
                                    .drawline(g);
            }
            else (new sharkpoly(new int[] {-noseside.x,-noseside.x*4/3, noseside.x*4/3,noseside.x},
                                  new int[] {noseside.y,nosebot.y,nosebot.y,noseside.y},4))
                                    .translate(x+w/2,y,manager.screenwidth,manager.screenheight)
                                    .drawline(g);

         }
         g.setColor(haircolor);
         for(short i=0;i<hairtot;++i) {
            hairs[i].paint(g,x1,y1);
         }
   }
   public void happy(byte change) {
      happiness = (byte)Math.max(0,Math.min(16,happiness+change));
   }
   class hair {
      double angle1,anglef;
      int x1,y1,x2,y2,xf,yf,xf2,yf2,len;
      int parting = w/4 + u.rand(w/4);
      public hair() {
         int maxx1 = nosetop.z;
         sharkpoly hairpoly = sharkpoly.oval(0,h/2,back.x,h*3/8,32);

         len = hairlen*3/4 + u.rand(hairlen/2);
         do {
            x1 = -w/2 + u.rand(w/2+maxx1);
            y1 = u.rand(mouth.y);
         } while(!rightpoly.contains(x1,y1)
               || x1 > -w/6 && y1 > nosetop.y*2/3
               || x1 > -w/4 && y1 > noseside.y);
         do {
            xf = -w/2 + u.rand(w);
            yf = u.rand(h/2);
         } while(!frontpoly.contains(xf,yf) || hairpoly.contains(xf,yf));
          double a2 = Math.atan2(y1-h/3, x1);
          x2 = Math.max(-w/2,Math.min(w/2,x1 + (int)(hairlen*Math.cos(a2))));
          y2 = Math.max(0,Math.min(h,y1 + (int)(hairlen*Math.sin(a2))));
          if( x1>-w/3) {
             xf = maxx1 - (maxx1-x1) * 2;
             yf = y1;
             while(!frontpoly.contains(xf,yf)) {
                if(xf < 0) ++xf; else if(xf > 0)--xf; else break;
             }
             a2 = Math.atan2(yf-h/3, xf);
             xf2 = Math.max(-w/2,Math.min(w/2,xf + (int)(hairlen*Math.cos(a2))));
             yf2 = Math.max(0,Math.min(h,yf + (int)(hairlen*Math.sin(a2))));
          }
          else xf = -9999;
      }
      void paint(Graphics g, int x, int y) {
           if(lookleft) {
              int xx = x + (w/2-x1)*manager.screenwidth/mover.WIDTH;
              int yy = y + y1*manager.screenheight/mover.HEIGHT;
              int xx2 = x + (w/2-x2)*manager.screenwidth/mover.WIDTH;
              int yy2 = y + y2*manager.screenheight/mover.HEIGHT;
              g.drawLine(xx, yy, xx2,yy2);
           }
           else if(lookright)  {
              int xx = x + (w/2+x1)*manager.screenwidth/mover.WIDTH;
              int yy = y + y1*manager.screenheight/mover.HEIGHT;
              int xx2 = x + (w/2+x2)*manager.screenwidth/mover.WIDTH;
              int yy2 = y + y2*manager.screenheight/mover.HEIGHT;
              g.drawLine(xx, yy, xx2,yy2);
          }
           else {           // frontal
              if(xf != -9999) {
                 int xx = x + (w/2+xf)*manager.screenwidth/mover.WIDTH;
                 int yy = y + yf*manager.screenheight/mover.HEIGHT;
                 int xx2 = x + (w/2+xf2)*manager.screenwidth/mover.WIDTH;
                 int yy2 = y + yf2*manager.screenheight/mover.HEIGHT;
                 g.drawLine(xx,yy, xx2,yy2);
                 xx = x + (w/2-xf)*manager.screenwidth/mover.WIDTH;
                 xx2 = x + (w/2-xf2)*manager.screenwidth/mover.WIDTH;
                 g.drawLine(xx,yy, xx2,yy2);
              }   
           }
      }
   }
}
