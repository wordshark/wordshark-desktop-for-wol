package shark;

import java.awt.*;

public class racetrack extends sharkGame {
  static final int factor = 100;
  static final int polypoints = 200;
  static final int tracky = 2000;
  static final int trackx = 200;
  static final int trackwidth = 50;
  static final int cartot = 5;
  static final int carh = factor * trackwidth  / 4;
  static final int obwidth = 10;
  static final int block = 1;
  static final Color trackcolor = Color.orange;
   long  nextcoin,stopping,movetime,stoptime;
//   short wantcoin;
   Rectangle coinrect;
   coin_base coinpile[];
   short cointot;
   short reward1;
   long wantcrash,wanthitleft,wanthitright;
   boolean wanthit;
   mover flashm;
   int accidents;
   double carangle,wheelangle;
   int carx,cary,carspeed,carxx,caryy;
   static final int accel = 15;
   static final int brake = 2;
   static final int maxspeed = 80000;
   static final int maxfrontcarspeed = 50000;
   static final double dangle = 0.0005/(Math.PI/4)/maxspeed;
   static final double maxangle = 0.0005;
   static final short obtot = 30;
   int assignim;
   car cars[] = new car[cartot];
   track track;
   view view;
   boolean started,offtrack;
   wheel wheel;
   int carorder[] = new int[cartot];
   short caro[];
   int position;
   finishline finishline;
   int rank;
   int ranking[]=new int[cartot+1];
   String you = rgame.getParm("you");
   String ordinals[] = u.splitString(rgame.getParm("positions"));
   sharkImage crash = sharkImage.random("racetrackcrash_");
   sharkImage offtracki = sharkImage.random("racetrackoff_");
   Font fonts[] = new Font[40];
   FontMetrics fm[] = new FontMetrics[40];
   Color cols[] = new Color[]{Color.red,Color.blue,Color.green.darker(),Color.magenta,Color.cyan.darker()};
   int colorder[] = u.shuffle(u.select(cartot,cartot));
   boolean won;
   long wontime;
//   short carnumbers[] = u.shuffle(u.select((short)10,(short)99,(short)cartot));
   sharkImage drivers[] = sharkImage.randomimages("racetrack_driver_",(short)cartot);
   public racetrack() {
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
    gamePanel.setBackground(background = Color.green);
    setup();
//    clearexit = true;
  }
  void setup() {
    int i,yy;
    track = new track();
    track.addcars();
    finishline = new finishline();
    view = new view();
    new start();
  }
  //------------------------------------------------------
  public void afterDraw(long t) {
    if(wantcoin > 0 && t > nextcoin) {
         noise.coindrop();
         dropcoin();
         nextcoin = t + 600;
         score(1);
         --wantcoin;
    }
    if(offtrack) {
      if(spokenWord.isfree()) noise.bang();
      offtrack = false;
    }
    if(wanthit) {
      noise.bang();
      wanthit=false;
      wantcrash = gtime+800;
    }
    if(!completed && cary <= 0) {
      int i,on=0,pc;
      String message="";
      started = false;
      ranking[rank++] = -1;
      for(i=0;i<cartot;++i) {
        if(!cars[caro[i]].finished) {
          ranking[rank++] = cars[caro[i]].number;
          cars[caro[i]].driver.setControl("face");
        }
      }
      removeMover(view);
      finishline.w = view.w;
      finishline.h = view.h;
      addMover(finishline,view.x,view.y);
      exitbutton(mover.HEIGHT*3/4);
      if(position == 0)  {
        wantcoin = reward1;
        won=true;
        wontime=gtime;
        message = u.edit(rgame.getParm("first"), String.valueOf(wantcoin));
      }
      else if(position == 1)  {
        wantcoin = (short)(reward1*2/3);
        won=true;
        wontime=gtime;
        message = u.edit(rgame.getParm("second"), String.valueOf(wantcoin));
      }
      else if(position == 2)  {
        wantcoin = (short)(reward1/3);
        won=true;
        wontime=gtime;
        message = u.edit(rgame.getParm("third"),String.valueOf(wantcoin));
      }
      else if(position == 3)  {
        message = rgame.getParm("fourth");
      }
      else if(position == 4)  {
        message = rgame.getParm("fifth");
      }
      else if(position == 5)  {
        message = rgame.getParm("sixth");
      }
      showmessage(message,mover.WIDTH/4,mover.HEIGHT/2,mover.WIDTH*3/4,mover.HEIGHT*3/4);
    }
  }
  //------------------------------------------
  Point carview(int x, int y, int x1, int y1,int w1,int h1) {
    int dx, dy, di, xx, yy, zz;
    double dist, a;
    dx = x - carx;
    dy = y - cary;
    dist = Math.sqrt(dx * dx + dy * dy);
    a = u.signedanglebetween(carangle, Math.atan2(dy, dx));
    xx = x1 + w1 / 2 + (int) (w1 * Math.sin(a));
    di = (int) (dist * Math.cos(a));
    if(di < 0) return null;
    if (di > tracky / 256) {
      yy = y1 + h1 * 2 * tracky / di / 256;
    }
    else {
      yy = y1 + h1 * 2 + tracky / 256 - di;
    }
    return new Point(xx,yy);
  }
            // returns angle of view from car
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
  //===================================================================================
  class track{
     Polygon p=new Polygon();
     boolean map[] = new boolean[1000000];
     Polygon mapp = new Polygon(new int[polypoints*2+2],new int[polypoints*2+2],polypoints*2+2);
     Polygon mapp2;
     track() {
       maketrack();
//       h = mover.HEIGHT;
//       w = (int)((long)h*screenheight*trackx/tracky/screenwidth);
//       addMover(this,0,0);
     }
     //------------------------------------------------------------
     void maketrack() {
        int i;
        int r,r2;
        // for formula ttrad = rad*11/8 + rad  * (sin(b1*angle) + sin (b2*angle))*3/32
        int b1 = 2+u.rand(4),b2;
        do {
          b2 = 2 + u.rand(4);
        } while(b1==b2);
        double c1 = u.rand(Math.PI*2);
        double c2 = u.rand(Math.PI*2);
        int count=0;
        int rrp[] = new int[polypoints+2];
        for(i=0;i<=polypoints;++i) {
           rrp[i] = r = trackx/2 - trackwidth/2
                + (int)((Math.sin(c1+b1*Math.PI*2*i/polypoints)
                                     + Math.sin(c2+ b2*Math.PI*2*i/polypoints))*(trackx/2-trackwidth/2)/2);
        }
        for(i=1;i<=polypoints-1;++i) {
                      rrp[i] = ( rrp[i-1] + rrp[i] + rrp[i+1]) /3;
        }
        for(i=0;i<=polypoints;++i) {
           p.addPoint(rrp[i], i*tracky/polypoints);
        }
        for(--i;i>=0;--i) {
           p.addPoint(rrp[i] + trackwidth,i*tracky/polypoints);
        }
        carx = p.xpoints[polypoints-4] + trackwidth/2;
        carxx = carx*factor;
        cary = p.ypoints[polypoints-4];
        caryy = cary*factor;
        carangle = Math.atan2(p.ypoints[polypoints-6]-p.ypoints[polypoints-4],
                              p.xpoints[polypoints-6] - p.xpoints[polypoints-4]);
     }
     //----------------------------------------------------------------
     double grad(int x, int y) {
       int i;
       for(i=2;i<polypoints;++i) {
         if(y <= p.ypoints[i]) break;
       }
       return Math.atan2(p.ypoints[i-2] - p.ypoints[i], p.xpoints[i-2] - p.xpoints[i]);
     }
     //----------------------------------------------------------------
     int xpos(int x,int y) {
       int i;
       for(i=2;i<polypoints;++i) {
         if(y <= p.ypoints[i]) break;
       }
       return x - (p.xpoints[i-1]*(p.ypoints[i]-y) + p.xpoints[i]*(y - p.ypoints[i-1]))/(p.ypoints[i]-p.ypoints[i-1]);
     }
     //----------------------------------------------------------------
     int  getontrack(int x, int y) {
       int i;
       for(i=1;i<polypoints;++i) {
         if(y < p.ypoints[i]) break;
       }
       return Math.max(Math.max(p.xpoints[i],p.xpoints[i-1]) + trackwidth/16,
                          Math.min(Math.min(p.xpoints[i],p.xpoints[i-1])+trackwidth*15/16,x));
     }
     void addcars() {
       int o[] = u.shuffle(u.select(cartot+1,cartot+1));
       while(o[0] == cartot || o[cartot] == cartot) o = u.shuffle(o);
       for(int i=0;i<cartot;++i) {
          cars[i] = new car(o[i],i);

       }
       carxx = carh/2 + p.xpoints[polypoints-4]*factor+(trackwidth*factor-carh)*o[cartot]/(cartot+1);
       carx = carxx/factor;
     }
                // paint aerial view
     public void paint(Graphics g,int x1,int y1,int w1, int h1) {
        if(mapp2 == null) {
          int i;
          mapp2 = new Polygon(new int[polypoints*2+2],new int[polypoints*2+2],polypoints*2+2);
          for(i=0;i<mapp2.npoints;++i) {
            mapp2.xpoints[i] = x1 + p.xpoints[i] * w1 / trackx ;
            mapp2.ypoints[i] = y1 + p.ypoints[i] * h1 / tracky;
          }
        }
        g.setColor(trackcolor);
        g.fillPolygon(mapp2);
        g.setColor(Color.black);
        g.drawRect(x1,y1,w1,h1);
        int pcarx = x1 + carx* w1 / trackx ;
        int pcary = y1 + cary* h1 / tracky ;
        int carw = w1/20;
        g.setColor(Color.red);
        g.fillOval(pcarx-carw,pcary-carw,carw*2,carw*2);
        for(int i=0;i<cartot;++i) {
          pcarx = x1 + cars[i].x* w1 / trackx ;
          pcary = y1 + cars[i].y* h1 / tracky ;
          g.setColor(cars[i].col);
          g.fillOval(pcarx-carw,pcary-carw,carw*2,carw*2);
        }
     }
                // paint from car's viewpoint
     void paint2(Graphics g,int x1,int y1,int w1,int h1) {
        int i,dx,dy,di;
        double dist,a;
        Point pt1,pt2;
        for(i=0;i<p.npoints;++i) {
          dx = p.xpoints[i]-carx;
          dy = p.ypoints[i]-cary;
          dist = Math.sqrt(dx*dx+dy*dy);
          a = u.signedanglebetween(carangle,Math.atan2(dy,dx));
          mapp.xpoints[i] = x1 + w1/2 + (int)(w1*Math.sin(a));
          di = (int)(dist * Math.cos(a));
          if(di > tracky/256)   mapp.ypoints[i] = y1 + h1 *2 * tracky / di / 256;
          else    mapp.ypoints[i] = y1 + h1*2 + tracky/256 - di;
        }
        g.setColor(trackcolor);
        g.fillPolygon(mapp);
        g.setColor(Color.white);
        for(i=0;p.ypoints[i] < cary - tracky/2;i+=block*2);
        for(;p.ypoints[i] < cary;i+= block*2) {
          pt1 = carview(p.xpoints[i] + trackwidth/2, p.ypoints[i], x1,y1,w1,h1);
          pt2 = carview(p.xpoints[i + block] + trackwidth/2, p.ypoints[i+block], x1,y1,w1,h1);
          if(pt1 != null && pt2 != null)
                 g.drawLine(pt1.x,pt1.y,pt2.x,pt2.y);
        }
        for(i=0;i<cartot;++i) {carorder[i]=cars[i].yy;}
        caro = u.getorder(carorder);
        finishline.paint(g,x1,y1,w1,h1);
        car prev = null;
        for(i=0;i<cars.length;++i) {
          if(!cars[caro[i]].finished) {
            cars[caro[i]].paint(g, x1, y1, w1, h1, prev);
            prev = cars[caro[i]];
          }
        }

     }
  }
  //==========================================================================
  class finishline extends mover{
    sharkImage im = sharkImage.random("racetrackfinish_");
    sharkImage crowd1 = new sharkImage("racetrackcrowd_1",false);
    sharkImage crowd1a = new sharkImage("racetrackcrowd_2",false);
    sharkImage crowd1b = new sharkImage("racetrackcrowd_1",false);
    sharkImage crowd1c = new sharkImage("racetrackcrowd_2",false);
    sharkImage crowd2 = new sharkImage("racetrackcrowd_2",false);
    sharkImage crowd2a = new sharkImage("racetrackcrowd_1",false);
    sharkImage crowd2b = new sharkImage("racetrackcrowd_2",false);
    sharkImage crowd2c = new sharkImage("racetrackcrowd_1",false);
    int x2 = track.p.xpoints[1],y2=track.p.ypoints[1],w2=trackwidth,h2 = w2*im.height()/im.width();
    boolean passed;
    long hitting;
    Font f;
    FontMetrics m;
    int lastht;
    finishline() {
      im.distort = true;
    }
    public void paint(Graphics g,int x1,int y1,int w1, int h1) {
      int i,j, xx,yy,ww=0,hh=0,ww2,hh2;
      Rectangle r;
      if(completed) {
        if(won && gtime < wontime+8000) {
          ww2 = w1 /4;
          hh2 =ww2 * crowd1.height()/crowd1.width();
          crowd1c.paint(g, x1-ww2/5, y1  + h1 - hh2*9/3, ww2, hh2);
          crowd1b.paint(g, x1-ww2/5, y1  + h1 - hh2*7/3, ww2, hh2);
          crowd1a.paint(g, x1-ww2/5, y1 + h1 - hh2*5/3, ww2, hh2);
          crowd1.paint(g, x1-ww2/5, y1 + h1 - hh2, ww2, hh2);
          ww = hh2 * crowd2.width() / crowd2.height();
          crowd2c.paint(g, x1 + w1 - ww2+ww2/5, y1 + h1 - hh2*9/3, ww2, hh2);
          crowd2b.paint(g, x1 + w1 - ww2+ww2/5, y1 + h1 - hh2*7/3, ww2, hh2);
          crowd2a.paint(g, x1 + w1 - ww2+ww2/5, y1 + h1 - hh2*5/3, ww2, hh2);
          crowd2.paint(g, x1 + w1 - ww2+ww2/5, y1 + h1 - hh2, ww2, hh2);
        }
        im.paint(g, xx = x1+w1/6,yy = y1,ww = w1*2/3,hh = h1);
      }
      else {
        Point pt1 = carview(x2, y2, x1, y1, w1, h1);
        if (pt1 != null && pt1.y > 0) {
          Point pt2 = carview(x2 + w2, y2, x1, y1, w1, h1);
          if (pt2 != null) {
            if (pt1.x < x1 + w1 && pt2.x > x1) {
              ww = pt2.x - pt1.x;
              im.paint(g, xx=pt1.x, yy=0, ww, hh=pt1.y);
              hh2 = hh;
              ww2 = hh2*crowd1.width()/crowd1.height();
              crowd1.paint(g,xx-ww2,0,ww2,hh2);
              ww2 = hh2*crowd2.width()/crowd2.height();
              crowd2.paint(g,xx+ww,0,ww2,hh2);
            }
          }
        }
      }
      if(ww>screenwidth/20) for(i=0;i<rank;++i) {
        r = im.getRect(i);
        g.setColor(Color.white);
        if(ranking[i] == -1 && (gtime & 0x0200) == 0) {
            g.fillRect(r.x,r.y,r.width,r.height);
            g.setColor(Color.black);
        }
        if (f==null || r.height != lastht) {
          f = sizeFont(new String[]{ordinals[3],you},r.width,r.height*2/3);
          m = getFontMetrics(f);
          lastht = r.height;
        }
        g.setFont(f);
        g.drawString(ordinals[i],r.x + r.width/2-m.stringWidth(ordinals[i])/2,
                                              r.y+r.height/6+m.getAscent()/2);
        if(ranking[i] == -1) g.drawString(you, r.x+r.width/2-m.stringWidth(you)/2,r.y+r.height/2 + m.getAscent());
        else drivers[ranking[i]].paint(g,r.x,  r.y+r.height/3,r.width,r.height*2/3);
      }
    }
  }
  //=================================================================
  class car{
    Color col,col2;
    int number;
    int speed;
    double angle;
    int xx,yy,x,y;
    int accel = 6 + u.rand(6);
    int maxspeed = 55000 + u.rand(15000);
    long lasttime=gtime;
    sharkImage driver;
    boolean finished;
    car(int pos, int st) {
      int startat = polypoints - 24 - 12*st;
      driver = drivers[number = st];
      driver.setControl("back");
      col = cols[colorder[st]];
      col2=col.darker();
      xx = carh/2 + track.p.xpoints[startat]*factor+(trackwidth*factor-carh)*pos/(cartot+1);
      y = track.p.ypoints[startat];
      angle =  Math.atan2(track.p.ypoints[startat-2]-track.p.ypoints[startat],
                                    track.p.xpoints[startat-2] - track.p.xpoints[startat]);
      x = xx/factor;
      yy = y*factor;
    }
    public void paint(Graphics g,int x1,int y1,int w1, int h1, car prev) {
      if(finished) return;
      int i, xpos;
      int interval = (int) (gtime - lasttime);
      lasttime = gtime;
      int oldy = yy;
//      if(fonts[0]==null) {
//        for(i=0;i<fonts.length;++i) {
//          fonts[i] = sizeFont(new String[]{"44"},w1/3,h1*i/fonts.length);
//          fm[i] = getFontMetrics(fonts[i]);
//
//        }
//      }
      if(started) {
        if(prev==null && yy < caryy) {
          if (speed > maxfrontcarspeed)
            speed = Math.max(maxfrontcarspeed, speed - brake * interval);
          else speed = Math.min(maxfrontcarspeed, speed + accel * interval);
        }
        else  speed = Math.min(maxspeed, speed + accel * interval);
        xx += (int) (speed * Math.cos(angle) * interval / 10000);
        yy += (int) (speed * Math.sin(angle) * interval / 10000);
        xpos = track.xpos(xx / factor, yy / factor) * factor;
        int closestx = -1, closesty = 0x7fffffff;
        if (oldy > caryy && Math.abs(yy - caryy) < carh/2
            && Math.abs(xx - carxx) < carh/2) {
          speed = 0;
          yy = caryy + carh;
        }
        else {
          if (prev != null) {
            if(Math.abs(prev.xx - xx) < carh/2 && Math.abs(prev.yy - yy) < carh/2) {
              speed = 0;
              yy = prev.yy + carh;
            }
            if (yy > prev.yy && yy - prev.yy < closesty) {
              closesty = yy - prev.yy;
              closestx = track.xpos(prev.x, prev.y) * factor;
            }
          }
          if (yy > caryy && yy - caryy < closesty) {
            closesty = yy - caryy;
            closestx = track.xpos(carx, cary) * factor;
          }
          if (speed > 0) {
            double wantangle = 0; // angle of steeringwheel
            if (xpos > carh*2/3 && xpos < trackwidth*factor+carh*2/3
                && closestx != -1 && Math.abs(xpos - closestx) < carh * 5 / 4) {
              if(closestx < carh*5/4) wantangle = Math.PI /4;
              else if(closestx > trackwidth*factor-carh*5/4) wantangle = -Math.PI /4;
              else if(closestx < xx) wantangle = Math.PI / 4;
              else wantangle = -Math.PI / 4;
            }
            else
              wantangle = (trackwidth / 2 * factor - xpos) * Math.PI / 4 /
                  (trackwidth / 2 * factor);
            angle = u.normalrange(angle +
                                  dangle * wantangle * interval * speed*3/2);
          }
        }
      }
      x = xx / factor;
      y = yy / factor;
      if (y<=0) {
        if(!completed) ++position;
        ranking[rank++] = number;
        driver.setControl("face");
        finished=true;
      }
      if (!track.p.contains(x, y)) {
          angle = track.grad(x, y);
          x = track.getontrack(x, y);
          xx = x * factor;
      }
      Point pt1 = carview(x - carh/2/factor, y , x1,y1,w1,h1);
      Point pt2 = carview(x + carh/2/factor, y , x1,y1,w1,h1);
      if(pt1 != null && pt2 != null) {
        int ww = pt2.x - pt1.x, hh = ww*2/3;
        if(hh>0) {
          g.setColor(col);
          g.fillRoundRect(pt1.x, pt1.y - hh / 2, ww / 4, hh / 2, ww / 12, ww / 6);
          g.fillArc(pt1.x + ww / 6, pt1.y - hh, ww * 2 / 3, hh * 15 / 16,-225,270);
          g.fillRoundRect(pt1.x + ww * 3 / 4, pt1.y - hh / 2, ww / 4, hh / 2, ww / 12, ww / 6);
          g.setColor(col2);
          g.drawArc(pt1.x + ww / 6, pt1.y - hh, ww * 2 / 3, hh * 15 / 16, 45, 90);
          int dx = (int) (ww * 2 / 6 / Math.sqrt(2)), midx = pt1.x + ww / 2;
          int dy = (int) (hh * 15 / 32 / Math.sqrt(2)), midy = pt1.y - hh + hh * 15 / 32;
          int bot = midy + dy/4;
          g.fillPolygon(new int[]{midx - dx, midx - dx*3/4, midx + dx*3/4, midx + dx,midx},
                        new int[]{midy - dy, bot, bot, midy - dy,bot},5);
          driver.w = dx*3/2*mover.WIDTH/screenwidth;
          driver.h = mover.HEIGHT;
          driver.adjustSize(screenwidth,screenheight);
          int dw= driver.w*screenwidth/mover.WIDTH;
          int dh= driver.h*screenheight/mover.HEIGHT;
          driver.paint(g,midx-dw/2,bot-dh,dw,dh+1);
//          if(hh > h1/40 && hh < h1) {
//            g.setFont(fonts[i= hh*fonts.length/h1]);
//            FontMetrics m = fm[i];
//            g.setColor(Color.white);
//            g.drawString(numberstring,pt1.x+ww/2-m.stringWidth(numberstring)/2,pt1.y - hh*2/3 + m.getAscent()/2);
//          }
        }
      }
    }
  }
  class view extends mover {
    long lasttime = gtime;
    view() {
      w = mover.WIDTH;
      h = mover.HEIGHT;
      addMover(this,0,0);
    }
    public void paint(Graphics g,int x1,int y1,int w1, int h1) {
      int interval = (int)(gtime-lasttime), i;
      Point pt1,pt2;
      lasttime = gtime;
      if(started) {
        carspeed = Math.min(maxspeed, carspeed + accel * interval);
        double newangle = carangle;
        carangle = u.normalrange(carangle + dangle * wheelangle*interval * carspeed*2);
        carxx += (int)(carspeed * Math.cos(carangle) * interval / 10000);
        caryy += (int)(carspeed * Math.sin(carangle) * interval / 10000);
        carx = carxx/factor;
        cary = caryy/factor;
        if(cary>0 && !track.p.contains(carx,cary)) {
           offtrack = true;
           if(track.xpos(carx,cary) < trackwidth/2) wanthitleft = gtime+300;
           else  wanthitright = gtime+300;
           carangle = track.grad(carx,cary);
           carx = track.getontrack(carx,cary);
           carxx = carx*factor;
           carspeed = 0;
        }
        if(cary>0) for(i=0;i<cartot;++i) {
          if (!cars[i].finished
              && cars[i].yy < caryy-carh/2 && cars[i].yy > caryy - carh
              && cars[i].speed < carspeed
              && Math.abs(carxx - cars[i].xx) < carh/2) {
            wanthit = true;
            carspeed = 0;
          }
        }
      }
      g.setClip(x1,y1,w1,h1);
      track.paint2(g,x1,y1,w1,h1);
      if(wantcrash != 0) {
       if(gtime < wantcrash) {
         g.setColor(u.brightcolor());
         g.fillPolygon(u.explode(x1+w1/2,y1+h1/2,w1/8,w1,h1));
         crash.paint(g,x1,y1,w1,h1);
       }
       else wantcrash = 0;
      }
      if(wanthitleft != 0) {
       if(gtime < wanthitleft) {
         g.setColor(u.brightcolor());
         g.fillPolygon(u.explode(x1,y1+h1/2,w1/8,w1/2,h1));
         offtracki.paint(g,x1,y1,w1/4,h1);
       }
       else wantcrash = 0;
      }
      if(wanthitright != 0) {
       if(gtime < wanthitright) {
         g.setColor(u.brightcolor());
         g.fillPolygon(u.explode(x1+w1,y1+h1/2,w1/8,w1/2,h1));
         offtracki.paint(g,x1+w1*3/4,y1,w1/4,h1);
       }
       else wantcrash = 0;
      }
      g.setClip(null);
    }
  }
  //-------------------------------------------------------
    class start extends mover {
      String message[] = u.splitString(rgame.getParm("start"));
      Font f;
      FontMetrics m;
      public start() {
         super(false);
         w = mover.WIDTH/6;
         h = mover.HEIGHT/6;
         x = mover.WIDTH/2 - w/2;
         y = mover.HEIGHT-h*3/2;
         addMover(this,x,y);
      }
      public void mouseClicked(int x1, int y1) {
        started=true;
        kill = true;
        wheel = new wheel();
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
  //-------------------------------------------------------
    class wheel extends mover {
      Color color = new Color(200, 0, 0);
      Rectangle r;
      public wheel() {
         w = mover.WIDTH/4;
         h = w*screenwidth/screenheight/2;
         x = mover.WIDTH/2 - w/2;
         y = mover.HEIGHT-h;
         addMover(this,x,y);
      }
      public void paint(Graphics g,int x, int y, int w1, int h1) {
        int i;
        if(completed) {kill=true;return;}
        if(r==null) r = new Rectangle(x+w1*7/16,y+w1/8,w1/8,w1*3/8);
        wheelangle = Math.max( -Math.PI / 4, Math.min(Math.PI / 4,
                                     Math.atan2(gamePanel.mousexs - (x + w1 / 2), w1)));
        g.setColor(color);
        g.fillOval(x,y,w1,w1);
        g.setColor(trackcolor);
        g.fillOval(x+w1/6,y+w1/6,w1*2/3,w1*2/3);
        g.setColor(color);
        g.fillPolygon(sharkpoly.rect(r.x,r.y,r.width,r.height,x+w1/2,y+w1/2,Math.PI/3+wheelangle));
        g.fillPolygon(sharkpoly.rect(r.x,r.y,r.width,r.height,x+w1/2,y+w1/2,-Math.PI/3+wheelangle));
     }
  }
}
