package shark;
import java.awt.*;

public class grinder extends mover  {
   int x1,x2,x3,x4,  y1,y2,y3,y4;   // in logical units
   int cornerx,cornery;
   runMovers r;
  static final short GRINDUPINT = 200;
  int minstop;
  long grindnext;
  byte[] clip = noise.grind();
  boolean grinding;

   class dropper {
      Image im;
      byte bitxx = (byte)(6+ u.rand(4));
      byte bityy = (byte)(3+ u.rand(3));
      int bitx[] = new int[bitxx*bityy];
      int bity[]=new int[bitxx*bityy];
      int bitw[]=new int[bitxx*bityy];
      int bith[]=new int[bitxx*bityy];
      int bitx2[]=new int[bitxx*bityy];
      int bity2[]=new int[bitxx*bityy];
      long bitt[]=new long[bitxx*bityy];
      int x,y,y1,w,h;
      long t;
      short btot;
      boolean fallen;

      public dropper(Image im1, int xx, int yy) {
        x = xx; y = y1 = yy; im=im1;
        w=im.getWidth(r)*mover.WIDTH/r.screenwidth;
        h=im.getHeight(r)*mover.HEIGHT/r.screenheight;
        t = sharkGame.gtime();
      }
      //--------------------------------------------------------------
      boolean drop(long ti) {
      int newy = (int)(y1 + (ti-t)*(ti-t)*mover.HEIGHT/2000000);
      short i,j,xx;
      short had = (short)(btot/bitxx);
      int movex = (x3-x2)/10, movey = y4/100;
      short fallenx = 0;

      if(y < y2-h) {
        y = Math.min(y2-h, newy);
        y = Math.max(0,y);
        if(y == y2-h) {
           y1 = y;
           t = ti;
        }
        x = Math.max(x2*(y+h)/y2,
                    Math.min( x4 + (x3-x4)*(y+h)/y2 - w, x));
      }
      if(y >= y2-h && had < bityy) {    // starting grinding
          if(ti - t > GRINDUPINT) {
             t = ti;
             y1 = y = y2 + h*(had+1)/bityy - h;
             xx = 0;
             for(i=0;i<bitxx;++i) {
                bitx[btot] = xx;
                bity[btot] = y2-y;
                bitw[btot] = w/bitxx;
                bith[btot]  = h/bityy;
                bity2[btot] = y2;
                bitx2[btot] = x + bitx[btot];
                bitt[btot] = ti;
                xx+=bitw[btot];
                if(minstop == 0) minstop = y4 - bith[0]/2;
                ++btot;
             }
          }
      }
      for(i=0;i<btot;++i) {
         int movey1 = movey*Math.max(1,(int)((ti-bitt[i])/runMovers.REDRAWINT));
         if(bity2[i] < y3) {
            bitx2[i] = Math.max(x2,Math.min(x3-bitw[i],bitx2[i]-movex+u.rand(2*movex)));
            bity2[i] += u.rand(movey1);
            if(bity2[i] < y3) grinding=true;
         }
         else  if(!fallen) {
            newy = bity2[i]+movey1*2/3+u.rand(movey1/3);
            if(newy > minstop-bith[i]) {
               newy = bity2[i] + u.rand(Math.min(movey1/2, Math.max(1, y4 - mover.HEIGHT*3/r.screenheight - bith[i]-bity2[i])));
               ++fallenx;
            }
            bity2[i] = newy;
         }
         bitt[i] = ti;
      }
      if(btot>0 && fallenx == btot && !fallen) {
         fallen = true;
         for(i=0;i<btot;++i) {
            minstop = Math.min(minstop, bity2[i]);
         }
      }
      return(fallen);
      }
      //----------------------------------------------------------
      public void paint(Graphics g, int xx, int yy) {
         int ix,iy,ix2,iy2,fx1,fx2,fy1,fy2;
         if(y<y2-h) {
            g.drawImage(im,xx+x*r.screenwidth/mover.WIDTH,
                           yy+y*r.screenheight/mover.HEIGHT,r);
         }
         else if(y < y2)  {
            g.drawImage(im,xx+x*r.screenwidth/mover.WIDTH,
                           yy+y*r.screenheight/mover.HEIGHT,
                           xx+(x+w)*r.screenwidth/mover.WIDTH,
                           yy+(y2)*r.screenheight/mover.HEIGHT,
                           0,
                           0,
                           w*r.screenwidth/mover.WIDTH,
                           (y2-y)*r.screenheight/mover.HEIGHT,r);
         }
         for(short i=0; i<btot; ++i) {
            g.drawImage(im,ix = xx+bitx2[i]*r.screenwidth/mover.WIDTH,
                           iy = yy+bity2[i]*r.screenheight/mover.HEIGHT,
                           ix2=xx+(bitx2[i]+bitw[i])*r.screenwidth/mover.WIDTH,
                           iy2=yy+(bity2[i]+bith[i])*r.screenheight/mover.HEIGHT,
                           fx1=bitx[i]*r.screenwidth/mover.WIDTH,
                           fy1=bity[i]*r.screenheight/mover.HEIGHT,
                           fx2=(bitx[i]+bitw[i])*r.screenwidth/mover.WIDTH,
                           fy2=(bity[i]+bith[i])*r.screenheight/mover.HEIGHT,r);
            if(fallen) {
                dr.im.getGraphics().drawImage(im,ix-dr.x*r.screenwidth/mover.WIDTH,
                                      iy-dr.y*r.screenheight/mover.HEIGHT,
                                      ix2-dr.x*r.screenwidth/mover.WIDTH,
                                      iy2-dr.y*r.screenheight/mover.HEIGHT,
                                   fx1, fy1,fx2,fy2,r);

            }
         }
      }
   }
   dropper d[] = new dropper[20];
   short dtot;
   Color gcolor = new Color(100+u.rand(64),100+u.rand(32),100+u.rand(32));
   double angle,startangle;
   long startanglet;    // time for grinder starting to grind
   class droppings extends mover {
      public droppings(Image im1,int w1, int h1) {
         super(false);
         im = im1;
         imwidth=w = w1;
         imheight=h = h1;

      }
      public void paint(Graphics g, int x, int y, int w, int h) {
         g.drawImage(im,x,y,r);
      }
   }
   droppings dr;
   int dropw,droph,oldscreenwidth;
   sharkGame game;

   public grinder(int w1,int h1,runMovers r1, int xx, int yy,sharkGame game1) {
      super(false);
      game = game1;
      y1 = 0;
      y4 = h1 - 1;
      y2 = h1*2/5;
      y3 = h1*3/5;
      x1 = 0;
      x4 = w1 - w1%5;
      x2 = w1/5;
      x3 = w1*4/5;
      w = w1;
      h = h1;
      r = r1;
      cornerx = w1/6;
      cornery = h1/12;
      int droppingx  = (x2-cornerx/2);
      int droppingy  = y3;
      dropw = x3-x2+cornerx;
      droph = (y4-y3);
      createim();
      h = y3;

      r.addMover(dr,xx+droppingx,yy+droppingy);
      r.addMover(this,xx,yy);
    }
    void createim() {
      Graphics gdroppings;
      int droppingw  = dropw*game.screenwidth/mover.WIDTH;
      int droppingh  = droph*game.screenheight/mover.HEIGHT-1;
      Image imdroppings = r.createImage(droppingw, droppingh);
      gdroppings = imdroppings.getGraphics();
      gdroppings.setColor(r.getBackground());
      gdroppings.fillRect(0,0,droppingw,droppingh);
      gdroppings.setColor(Color.black);
      gdroppings.drawRoundRect(1,0,
                      droppingw - 2,
                      droppingh - 3,
                      cornerx*game.screenwidth/mover.WIDTH,
                      cornery*game.screenheight/mover.HEIGHT);
      dr = new droppings(imdroppings,droppingw*mover.WIDTH/game.screenwidth,
                                     droppingh*mover.HEIGHT/game.screenheight);
      oldscreenwidth = game.screenwidth;
    }
    public void resize() {
      if(r.screenwidth != oldscreenwidth) {
        int xx = dr.x;
        int yy = dr.y;
        r.removeMover(dr);
        createim();
        r.addMover(dr,xx,yy);
        r.puttobottom(dr);
      }
    }
    public void stop() {
    }
   public void paint(Graphics g, int x, int y, int w, int h) {
      long ti = sharkGame.gtime();
      int xx;
      short i,j;
      dropper dd;
      double a;
      Font f = g.getFont();
      Color c = g.getColor();
      boolean stillgoing = false;
      grinding=false;

      g.setColor(Color.black);
      g.drawPolyline(new int[]{x+x4*r.screenwidth/mover.WIDTH,
                              x+x3*r.screenwidth/mover.WIDTH,
                              x+x2*r.screenwidth/mover.WIDTH,
                              x+x1*r.screenwidth/mover.WIDTH},
                    new int[]{y+y1*r.screenheight/mover.HEIGHT,
                              y+y2*r.screenheight/mover.HEIGHT,
                              y+y2*r.screenheight/mover.HEIGHT,
                              y+y1*r.screenheight/mover.HEIGHT},4);
      g.drawRect(x+x2*r.screenwidth/mover.WIDTH,
                           y+y2*r.screenheight/mover.HEIGHT,
                           (x3-x2)*r.screenwidth/mover.WIDTH,
                           (y3-y2)*r.screenheight/mover.HEIGHT);
     if(dtot>0) {
         for(i=0;i<dtot;++i)  {
            if(!d[i].fallen ) {
               if(!d[i].drop(ti)) stillgoing = true;
               d[i].paint(g,x,y);
            }
         }
         angle = startangle - Math.PI*(ti-startanglet)/600;
         while(angle < 0) angle += Math.PI*2;
         keepMoving = stillgoing;
         dr.keepMoving = stillgoing;
         dr.drawn = false;
         if(!stillgoing) {
            for(i=0;i<dtot;++i)  {
               d[i] = null;
            }
            dtot = 0;
            startanglet = ti;
            startangle = angle;
         }
      }
      g.setColor(Color.black);
      for(j=0;j<20;++j) {
            a = angle+Math.PI*2*j/20;
            if(a >= Math.PI*2) a -= Math.PI*2;
            if(a< Math.PI) {
                xx = x+((x2+x3)/2+(int)((x3-x2)/2 * Math.cos(a)))*r.screenwidth/mover.WIDTH;
                g.drawLine(xx, y+y2*r.screenheight/mover.HEIGHT,
                          xx, y+y3*r.screenheight/mover.HEIGHT);
            }
      }
      g.setFont(f);
      g.setColor(c);
      if(grinding && sharkGame.gtime() > spokenWord.endsay) {
         int start = u.rand(clip.length*7/8);
         spokenWord.say(clip,start,start + clip.length/8);
      }
    }
    //------------------------------------------------------------
    public void add(Image im, int xx, int yy) {
       if(dtot==0) {
         startanglet = sharkGame.gtime();
         startangle = angle;
         keepMoving = true;
       }
       d[dtot] = new dropper(im,xx-x,yy-y);
       ++dtot;
       ended = false;
   }
   //--------------------------------------------------------------
    public boolean inside(int xm, int ym) {
       Polygon p = new Polygon(new int[]{x1, x4, x3, x2},
                              new int[]{y1, y1, y2, y2}, 4);
       return p.contains(xm - x, ym - y);
    }
}
