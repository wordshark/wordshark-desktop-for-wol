
package shark;

import java.awt.*;

public class tilt extends sharkGame {
   long  nextcoin,stopping,movetime,stoptime;
 //  short wantcoin;
   Rectangle coinrect;
   coin_base coinpile[];
   short cointot;
   int ballr;
   short reward1;
   tilter tilter;
   barrier barriers[];
   int maxpos, maxeaterpos;
   boolean hadloss;
   ballclass balls[];
   int balltot,lost;
   boolean started;
   balleater bb;
   long wantball,wantend, wantflash,endflash;

   public tilt() {
    errors = false;
//    gamePanel.dontstart=true;
//    gamescore1 = true;
//    peeps = true;
//    listen= true;
//    peep = true;
//    wantspeed = true;
    forceSharedColor = true;
    reward1 = (short)Math.max(3,sharkStartFrame.reward);
    sharkStartFrame.reward = 0;
//    gamePanel.setBackground(background=u.lightbluecolor());
    gamePanel.clearWholeScreen = true;
    wantSprite = false;
    buildTopPanel();
    gamePanel.setBackground(background = new Color(255,255,190));
    setup();
//    clearexit = true;
  }
  void setup() {
    int i,yy;
    tilter = new tilter();
    new start();
  }
  public void afterDraw(long t) {
    if(!completed && wantend > 0 && gtime > wantend) endroutine();
    if(wantball != 0 && t > wantball) {
      balls[balltot++] = new ballclass();
      wantball = 0;
      bb.setup(0);
    }
    if(wantcoin > 0 && t > nextcoin) {
         noise.coindrop();
         dropcoin();
         nextcoin = t + 600;
         score(1);
         --wantcoin;
    }
  }
  void endroutine() {
    exitbutton(mover.HEIGHT*17/32);
    String message;
    switch (lost) {
        case 0:
           wantcoin = reward1;
           message = u.edit(rgame.getParm("won"),String.valueOf(wantcoin));
           break;
          default:
           message = rgame.getParm("lost");
           break;
    }
    showmessage(message,mover.WIDTH/4,0,mover.WIDTH*3/4,mover.HEIGHT/8);
  }
   //--------------------------------------------------------------------
  void dropcoin() {
     if(coinrect == null)
       coinrect = new Rectangle(screenwidth/2-screenwidth/40,0,screenwidth/20,screenheight/20);
     Rectangle r = coinrect;
     int coinrad = mover.WIDTH/60;
     int cointhick =   mover.WIDTH/200;
     if(coinpile == null || coinpile.length < wantcoin)
         {coinpile = new coin_base[wantcoin]; cointot = 0;}
     coin_base cc = new coin_base(coinrad, cointhick,
                        (r.x+coinrad*screenwidth/mover.WIDTH+u.rand(r.width-coinrad*2*screenwidth/mover.WIDTH)-screenwidth/2)*mover3d.BASEU/screenmax + mover3d.BASEU/2,
                        (r.y+r.height-coinrad*screenheight/mover.HEIGHT-screenheight/2)*mover3d.BASEU/screenmax + mover3d.BASEU/2,
                         Math.PI*u.rand(100)/400,0,
                         -Math.PI/4 + Math.PI/2*u.rand(100)/100,
                         Color.yellow,Color.orange,
                         ebutton.y);
     cc.endax = Math.PI/2*u.rand(100)/100;
     cc.endaz = -Math.PI/2 + Math.PI*u.rand(100)/100;
     cc.pile = coinpile;
     coinpile[cointot++] = cc;
     addMover(cc,0,0);
  }
 //-----------------------------------------------------------------------
  class ballclass extends mover {
      Color color = u.fairlydarkcolor();
      int i,speed,vspeed;
      int barno,lastpos ;       // lastpos is x for unrotated bar * 1000
      long lasttime=gtime;
      int accel = screenheight*3;
      boolean overgap,won;
      int xx1,yy1;
      barrier b;
      boolean rightofeater = true;
      ballclass() {
         w = ballr * 2 * mover.WIDTH/screenwidth;
         h = w*screenwidth/screenheight;
         lastpos = maxpos*1000;
       }
       void getpos() {
         b = barriers[barno];
         double dir;
         int time,nextx, xx, yy;
         long nexttime=gtime;
         int interval = (int)(nexttime-lasttime);
         if(!started) interval = 0;
         if(won) {
           nextx = lastpos + speed * interval;
           if(nextx/1000 > maxpos-tilter.box.thick) {
             nextx = (maxpos-tilter.box.thick)*1000;
             speed = 0;
           }
           if(nextx < ballr*1000) {
             nextx = ballr*1000;
             speed = 0;
           }
           lastpos = nextx;
           for(i=0;this != balls[i];++i) {
             if (balls[i] != null && balls[i].barno == barno && !balls[i].overgap
               && Math.abs(balls[i].lastpos - lastpos) < ballr*2000) {
               if(balls[i].lastpos <= lastpos
                     && balls[i].lastpos < (maxpos-ballr*2)*1000
                   || balls[i].lastpos < ballr*3*1000 ) {
                 lastpos = balls[i].lastpos + ballr*2000;
               }
               else lastpos =  balls[i].lastpos - ballr*2000;
             }
           }
            Point pt = tilter.box.getballpt(lastpos/1000);
           tox = x = (pt.x - ballr)*mover.WIDTH/screenwidth;
           toy = y = (pt.y - ballr)*mover.HEIGHT/screenheight;
           speed += (int) ( interval * Math.sin(tilter.angle) * accel / 1000);
         }
         else if(!overgap) {     // not falling through gap
           dir = b.grad(lastpos/1000) + tilter.angle;
           int newspeed = speed + (int) ( interval * Math.sin(dir) * accel / 1000);
           nextx = lastpos + (speed+newspeed)/2 * interval;
           if((lastpos <= b.gapx*1000 && nextx > b.gapx*1000   // check if entered gap
                  || lastpos >= b.gapx2*1000 && nextx < b.gapx2*1000)
                 && Math.abs(speed)*(6+barno*3) < accel) {
             overgap = true;
             nextx = (b.gapx+b.gapx2)/2 * 1000;
             Point pt = b.getballpt((b.gapx+b.gapx2)/2);
             vspeed = 0;
           }
           else if(nextx/1000 > maxpos) {
             nextx = (maxpos)*1000;
             speed = 0;
           }
           else  if(nextx/1000 < ballr+tilter.box.thick) {
               nextx = (ballr+tilter.box.thick)*1000;
               speed = 0;
           }
           else {
              speed = newspeed;
           }
           if(barno < barriers.length) {
           boolean rightofeater1 = (nextx >= bb.lastpos);
             if(rightofeater != rightofeater1) {
                 ++lost;
                 for (i = 0; i < balls.length; ++i) {
                   if (this == balls[i]) {
                     balls[i] = null;
                     break;
                   }
                 }
                 if(lost==1) wantball = gtime+1000;
                 else wantend = gtime+1000;
                 noise.squash();
                 bb.im.setControl("eat");
             }
           }
           Point pt = b.getballpt(nextx/1000);
           tox = x = (pt.x - ballr)*mover.WIDTH/screenwidth;
           toy = y = (pt.y - ballr)*mover.HEIGHT/screenheight;
           lastpos = nextx;
         }
         else if(overgap) {
//startPR2005-04-14^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//           toy = y = y + vspeed*interval*mover.HEIGHT/screenheight/1000;
       toy = y = y+(int)((long)vspeed*interval*mover.HEIGHT/screenheight/1000);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
           if(barno < barriers.length-1) {
              i = barriers[barno+1].getpos(x*screenwidth/mover.WIDTH,y*screenheight/mover.HEIGHT);
              if(i>=0){
                 barno=barno+1;
                 lastpos = i;
                 b = barriers[barno];
                 Point pt = b.getballpt(lastpos/1000);
                 tox = x = (pt.x - ballr)*mover.WIDTH/screenwidth;
                 toy = y = (pt.y - ballr)*mover.HEIGHT/screenheight;
                 speed = 0;
                 overgap = false;
                 rightofeater = true;
                 bb.setup(barno);
             }
           }
           else {
             i = tilter.box.getpos(x*screenwidth/mover.WIDTH,y*screenheight/mover.HEIGHT);
             if(i>=0){
                lastpos = i;
                Point pt = tilter.box.getballpt(lastpos/1000);
                tox = x = (pt.x - ballr)*mover.WIDTH/screenwidth;
                toy = y = (pt.y - ballr)*mover.HEIGHT/screenheight;
                speed = 0;
                overgap = false;
                won = true;
                lost = 0;
                wantend = gtime+1000;
             }
           }
           if(overgap) {
             Point pt = b.getpt((b.gapx+b.gapx2)/2);
             if(y < pt.y*mover.HEIGHT/screenheight)
               x = tox = (pt.x-ballr)*mover.WIDTH/screenwidth;
             vspeed += interval * accel /1000;
             xx1 = x*screenwidth/mover.WIDTH + ballr;
             yy1 = y*screenheight/mover.HEIGHT + ballr;
           }
         }
        lasttime=gtime;
      }
      public void paint(Graphics g,int x1,int y1,int w1, int h1) {
        int t;
        getpos();
        g.setColor(color);
        g.fillOval(tox*screenwidth/mover.WIDTH,toy*screenheight/mover.HEIGHT,ballr*2,ballr*2);
      }
  }
  class tilter extends mover {
    double angle,startangle;
    int midx,midy;
    boolean first = true;
    box box;
    tilter() {
      h = mover.HEIGHT*2/3;
      w = mover.WIDTH*2/3; // h*screenheight/screenwidth;
      addMover(this,mover.WIDTH/2 - w/2,mover.HEIGHT/2 - h/2);
    }
    public void paint(Graphics g,int x1,int y1,int w1, int h1) {
      int i;
      midx = x1+w1/2;
      midy = y1+h1/2;
      if(barriers==null) {
        barriers = new barrier[3];
        for(i=0;i<3;++i) {
          barriers[i] = new barrier(x1, y1 + (i * 2 + 1) * h1 / 6, w1, h1 / 12,i);
        }
        box = new box(x1,y1,w1,h1);
      }
      if(first && started) {
        startangle = Math.atan2(gamePanel.mouseys - screenheight / 2,
                                screenwidth / 2);
        first = false;
      }
      if(!first && !completed) angle = Math.max(-Math.PI/12, Math.min(Math.PI/12 ,
               u.signedanglebetween(startangle, Math.atan2(gamePanel.mouseys - screenheight/2, screenwidth/2))));
      else angle = 0;
      for(i=0;i<3;++i) {
        barriers[i].paint(g,x1,y1,w1,h1);
      }
      if(balls==null) {
        balls = new ballclass[2];
        balltot = 1;
        balls[0] = new ballclass();
        bb=new balleater();
      }
      box.paint(g,x1,y1,w1,h1);
      for(i=0;i<balltot;++i) {
        if(balls[i] !=null) {
          balls[i].paint(g, x1, y1, w1, h1);
        }
      }
      bb.paint(g);
    }

  }
  class box extends mover {
    sharkpoly b, b1;
    int thick;
    box(int x1,int y1,int w1,int h1) {
      thick = h1/12/4;
      b=new sharkpoly(new int[] {x1,x1+w1,x1+w1,x1,x1,x1+thick,x1+thick,x1+w1-thick,x1+w1-thick,x1},
                      new int[] {y1,y1,y1+h1,y1+h1, y1+thick,y1+thick,
                      y1+h1-thick,y1+h1-thick,y1+thick,y1+thick},10);
    }
    Point getballpt(int xx) {  // xx is xposition on bottom
      int yy1 = b.ypoints[6];
      int xx1 = b.xpoints[6];
      return u.rotate(xx+xx1,yy1-ballr,tilter.midx,tilter.midy,tilter.angle);
    }
    Point getpt(int xx) {  // actual screen addr of point on barrier (xx is xposition on barrier)
      int yy1 = b.ypoints[6];
      int xx1 = b.xpoints[6];
      return u.rotate(xx+xx1,yy1,tilter.midx,tilter.midy,tilter.angle);
    }
    int getpos(int ballx, int bally)  {  // return position from screen x
      int yy1 = b.ypoints[6];
      int xx1 = b.xpoints[6];
      Point pt1 = u.rotate(xx1, yy1, tilter.midx, tilter.midy, tilter.angle);
      int pos = (int)((ballx - pt1.x) / Math.cos(tilter.angle));
      if (pos > maxpos) pos = maxpos;
      Point pt2 = getballpt(pos);
      if(bally < pt2.y) return -1;  // not there yet
      if(ballx < pt1.x) return 0;
      return pos*1000;
    }
    public void paint(Graphics g,int x1,int y1,int w1, int h1) {
       g.setColor(Color.black);
       ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
       g.fillPolygon(b1 = (new sharkpoly(b)).simplerotate(tilter.midx,tilter.midy,tilter.angle));
       ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);
    }
  }
  class barrier extends mover {
     sharkpoly left=new sharkpoly(),right=new sharkpoly(),left1,right1;
     int xx1,yy1;
     int num,thick;
     int a,c;     // curve is y = a*sin(b*(x+c));
     double b;
     int gapx,gapx2;
     barrier(int x1, int y1, int w1,int h1, int num1) {
       int i;
       xx1=x1; yy1=y1;
       num = num1;
       thick = h1/4;
       ballr = screenwidth/80;
       maxpos = w1 - thick - ballr;
       maxeaterpos = maxpos*3/4;
       do {
         a = (h1 / 8 + u.rand(h1 / 16)); // amplitude
         b = (6 + u.rand(6)) * Math.PI / w1; // wavelength
       } while(a*b*6 > 1);
       c = (int)(w1/3/b - maxpos);
       if(num==0)  gapx = w1 *3/8 + u.rand(w1 *3/8);
       else do {
         gapx = w1 / 4 + u.rand(w1 /2);
       }while (Math.abs(gapx - barriers[num-1].gapx) < w1/5);
       gapx2 = gapx + ballr*(17+(2-num1)*2)/8;
       for(i = 0;i<gapx; i+=1) left.addPoint(x1+i, y1+(int)(a*Math.sin(b*(i+c))));
       left.addPoint(x1+gapx,y1 + (int)(a*Math.sin(b*(gapx+c))));
       for(i = gapx;i>0; i-=1) left.addPoint(x1+i, y1+thick + (int)(a*Math.sin(b*(i+c))));
       left.addPoint(x1,y1+thick+(int)(a*Math.sin(b*(c))));
       for(i = gapx2;i<w1; i+=1) right.addPoint(x1+i, y1+(int)(a*Math.sin(b*(i+c))));
       right.addPoint(x1+w1,y1+(int)(a*Math.sin(b*(w1+c))));
       for(i = w1;i>gapx2; i-=1) right.addPoint(x1+i, y1+thick+(int)(a*Math.sin(b*(i+c))));
       right.addPoint(x1+gapx2,y1+thick+(int)(a*Math.sin(b*(gapx2+c))));
     }
     double grad(int pos) { // gradiant (not tilted)
       int xx = pos;
       if (xx<0) xx = -xx-1;
       return Math.atan(a * b * Math.cos(b *(xx + c)));
     }
     Point getballpt(int xx) {  // xx is xposition on barrier
       int yy = (int)(a * Math.sin(b*(xx+c)));
       return u.rotate(xx+xx1,yy+yy1-ballr,tilter.midx,tilter.midy,tilter.angle);
     }
     Point getpt(int xx) {  // actual screen addr of point on barrier (xx is xposition on barrier)
       int yy = (int)(a * Math.sin(b*(xx+c)));
       return u.rotate(xx+xx1,yy+yy1,tilter.midx,tilter.midy,tilter.angle);
     }
     int getpos(int ballx, int bally)  {  // return position from screen x
       Point pt1 = u.rotate(xx1, yy1, tilter.midx, tilter.midy, tilter.angle);
       int pos = (int)((ballx - pt1.x) / Math.cos(tilter.angle));
       if (pos > maxpos) pos = maxpos;
       Point pt2 = getballpt(pos);
       if(bally < pt2.y) return -1;  // not there yet
       if(ballx < pt1.x) return -2;   // missed it
       return pos*1000;
     }
     public void paint(Graphics g,int x1,int y1,int w1, int h1) {
       g.setColor(Color.black);
       ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
       g.fillPolygon(left1 = (new sharkpoly(left)).simplerotate(tilter.midx,tilter.midy,tilter.angle));
       g.fillPolygon(right1 =  (new sharkpoly(right)).simplerotate(tilter.midx,tilter.midy,tilter.angle));
       ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);
     }
  }
  //-------------------------------------------------------
    class start extends mover {
      String message[] = u.splitString(rgame.getParm("start"));
      Font f;
      FontMetrics m;
      public start() {
         super(false);
         w = mover.WIDTH/8;
         h = mover.HEIGHT/8;
         x = mover.WIDTH - w*9/8;
         y = mover.HEIGHT/2- h/2;
         addMover(this,x,y);
      }
      public void mouseClicked(int x1, int y1) {
//startPR2006-09-28^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        bb.lasttime = gtime;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        started=true;
        bb.starttime=gtime;
        kill = true;
      }
      public void paint(Graphics g,int x, int y, int w1, int h1) {
        Rectangle r = new Rectangle(x,y,w1,h1);
        int i;
        if(f==null) {
           f = sizeFont(g,message,w1*3/4,h1*3/4);
           m = getFontMetrics(f);
        }
        g.setFont(f);
        g.setColor(Color.orange);
        g.fillRect(x,y,w1,h1);
        g.setColor(Color.black);
        x += w1/2;
        y += Math.max(0, h1/2 - m.getHeight()*message.length/2 + m.getMaxAscent());
        for(i=0;i<message.length;++i,y += m.getHeight()) {
          g.drawString(message[i], x - m.stringWidth(message[i]) / 2, y);
        }
        u.buttonBorder(g,r,Color.red,!mouseDown);
     }
  }
  class balleater {
    sharkImage im = sharkImage.random("balleater_");
    int lastpos;
//startPR2006-09-28^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    long lasttime = gtime;
    public long lasttime = gtime;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    int barno;
    int ww1 = ballr*3;
    int hh1 = ballr*3;
    int xx1,yy1;
    int speed, aspeed = screenwidth/80,incspeed = screenwidth/160;
    long starttime=gtime;
    balleater() {
      lastpos = (tilter.box.thick + ww1/2)*1000;
      im.distort=true;
    }
    void setup(int bno) {
      barno = bno;
      lastpos = (tilter.box.thick + ww1/2)*1000;
      im.setControl("normal");
    }
    void getpos() {
      barrier b = barriers[barno];
      double dir;
      int time,nextx, xx, yy;
      long nexttime=gtime;
      int interval = (int)(nexttime-lasttime);
      if(completed) {
        nextx = lastpos + (int)(speed * interval/100);
        if (nextx / 1000 > maxpos - tilter.box.thick - ww1/2) {
          nextx = (maxpos - tilter.box.thick-ww1/2) * 1000;
          speed = -aspeed;
        }
        else if (nextx / 1000 < ww1/2 + tilter.box.thick) {
          nextx = (ww1/2 + tilter.box.thick) * 1000;
          speed = aspeed;
        }
        im.lefttoright = (speed<0);
        lastpos = nextx;
      }
      else if(tilter.angle != 0) {
        speed = tilter.angle>0 ? aspeed + (int)(incspeed*(gtime-starttime)/10000)
                     :-aspeed - (int)(incspeed*(gtime-starttime)/10000);
        nextx = lastpos + (int)(speed * interval/100);
        if (nextx / 1000 > maxeaterpos ) {
          nextx = (maxeaterpos) * 1000;
        }
        else if (nextx / 1000 < ww1/2 + tilter.box.thick) {
          nextx = (ww1/2 + tilter.box.thick) * 1000;
        }
        lastpos = nextx;
      }
      Point pt = b.getpt(lastpos/1000);
      xx1 = pt.x - ww1/2;
      yy1 = pt.y - hh1;
    }
    public void paint(Graphics g) {
      int i;
      getpos();
      if(!completed && (im.lefttoright = tilter.angle < 0 && lastpos > ww1*1000)) {
        for (i = 0; i < balltot; ++i) {
          if (balls[i] != null && balls[i].barno == barno) {
            if (balls[i].lastpos < lastpos + (ww1 + ballr) * 1000)
              im.lefttoright = false;
            break;
          }
        }
      }
      im.angle = im.lefttoright ? -tilter.angle-barriers[barno].grad(lastpos/1000) : tilter.angle+barriers[barno].grad(lastpos/1000);
      im.paint(g, xx1, yy1, ww1, hh1);
    }
  }
}
