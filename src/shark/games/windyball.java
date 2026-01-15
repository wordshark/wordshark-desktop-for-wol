package shark.games;

import shark.*;
import shark.sharkGame.*;

import java.awt.*;

public class windyball extends sharkGame {
  static final int bloodtime = 500;
  long nextcoin, stopping, movetime, stoptime;
  static final int MAXEGG = 6;
  static final int EGGTIME = 2000;
//  short wantcoin;
  Rectangle coinrect;
  coin_base coinpile[];
  short cointot;
  short reward1;
  long wantnext, lastegg;
  boolean winddir = u.rand(2) == 0;
  int windspeed = (mover.WIDTH / 4 + u.rand(mover.WIDTH/2)) *  (winddir ? 1 : -1);
  int g = mover.HEIGHT*1000 / 2;
  nest nest;
  nestsupport nestsupport;
  boolean leftpos = u.rand(2)==0;
  Font f;
  FontMetrics m ;
  boolean firstmess = true;
  int totlost, totinnest, totegg;
  int twoscore, onescore;
  int allscore;
  mover mess;
  int limity = mover.HEIGHT / 6;
  int gotover;
  double maxda = Math.PI/500;
  shooter shooter;
  int ballw=mover.WIDTH/36;
  int hadball;
  int winners;
  long startedat;
  boolean newball;
  int rewardrate;
  boolean done;
  int allowtime = 60000;
  timer timer;
  boolean wantshoot = false;
  
  
  public windyball() {
    errors = false;
//    gamePanel.dontstart=true;
//    gamescore1 = true;
//    peeps = true;
//    listen= true;
//    peep = true;
//    wantspeed = true;
    clickonrelease = true;
    rewardrate = (short) Math.max(1, sharkStartFrame.reward/3);
    sharkStartFrame.reward = 0;
    gamePanel.setBackground(background=u.lightbluecolor());
    gamePanel.clearWholeScreen = true;
    wantSprite = false;
    gamePanel.allclicks = true;
    buildTopPanel();
  }

  void setup() {
    int i, yy;
    nestsupport = new nestsupport();
    nest = new nest(allscore, 0);
    mess = showmessage(u.edit(rgame.getParm("startmess"), new int[] {rewardrate}),
                          0,0, mover.WIDTH,  Math.min(mover.HEIGHT/4,nest.y));
    shooter = new shooter();
    new ball();
    addwind();
    gamePanel.bringtotop(nest);
  }
  void addwind() {
     int i;
     for(i=0;i<gamePanel.mtot;++i) if(gamePanel.m[i] instanceof wind) gamePanel.m[i].kill = true;
     for(i=mover.HEIGHT/20;i<mover.HEIGHT*7/8-shooter.h ;i += mover.HEIGHT/20+u.rand(mover.HEIGHT/20)) {
        new wind(i);
     }
  }
  //------------------------------------------------------
  public void afterDraw(long t) {
    if (nestsupport == null) {
      setup();
    }
    if(newball) {
      newball = false;
      addwind();
      if (gtime > startedat+allowtime) {
//        clearexit = true;
        wantcoin = (short)( winners * rewardrate);
        String endmess;
        int record = student.optionint("windyball_best");
        if (record < winners)
          student.setOption("windyball_best", winners);
        if (winners >= 2) {
          endmess = u.edit(rgame.getParm("gottwo"), String.valueOf(winners),
                           String.valueOf(wantcoin));
          if (record > 0) {
            if (winners > record)    endmess += rgame.getParm("best");
            else  if (winners == record) endmess += rgame.getParm("same");
            else  endmess += u.edit(rgame.getParm("bestis"),String.valueOf(record));
          }
        }
        else if (winners == 1) {
            endmess = u.edit(rgame.getParm("gotone"), String.valueOf(wantcoin));
            if (record > 1)
              endmess += u.edit(rgame.getParm("bestis"),String.valueOf(record));

        }
        else   endmess = rgame.getParm("gotnone");
        showmessage(endmess, 0, 0,
                    mover.WIDTH, nest.y);
        exitbutton(mover.WIDTH*3/4, mover.HEIGHT * 2 / 3);
        timer.kill = true;
        shooter.kill = true;
       }
       else {
         new ball();
       }
    }
    if (wantcoin > 0 && t > nextcoin) {
      noise.coindrop();
      dropcoin();
      nextcoin = t + 600;
      score(1);
      --wantcoin;
    }
  }

  //--------------------------------------------------------------------
  void dropcoin() {
    if (coinrect == null)
      coinrect = new Rectangle(ebutton.x*screenwidth/mover.WIDTH, 0,
                               ebutton.w*screenwidth/mover.WIDTH, screenheight / 20);
    Rectangle r = coinrect;
    int coinrad = mover.WIDTH / 60;
    int cointhick = mover.WIDTH / 200;
    if (coinpile == null || coinpile.length < wantcoin) {
      coinpile = new coin_base[wantcoin]; cointot = 0; }
    coin_base cc = new coin_base(coinrad, cointhick,
                       (r.x + coinrad * screenwidth / mover.WIDTH +
                        u.rand(r.width -
                               coinrad * 2 * screenwidth / mover.WIDTH) -
                        screenwidth / 2) * mover3d.BASEU / screenmax +
                       mover3d.BASEU / 2,
                       (r.y + r.height - coinrad * screenheight / mover.HEIGHT -
                        screenheight / 2) * mover3d.BASEU / screenmax +
                       mover3d.BASEU / 2,
                       Math.PI * u.rand(100) / 400, 0,
                       -Math.PI / 4 + Math.PI / 2 * u.rand(100) / 100,
                       Color.yellow, Color.orange,
                       ebutton.y);
    cc.endax = Math.PI / 2 * u.rand(100) / 100;
    cc.endaz = -Math.PI / 2 + Math.PI * u.rand(100) / 100;
    cc.pile = coinpile;
    coinpile[cointot++] = cc;
    addMover(cc, 0, 0);
  }

  public boolean click(int mx, int my) {
    if (mess instanceof sharkGame.messmover2 && ((sharkGame.messmover2)mess).isOver(mx, my)) {
      return false;
    }
    if (mess != null && !mess.mouseOver) {
      mess.kill = true;
      mess = null;
      limity = 0;
      spokenWord.flushspeaker(true);
    }
    if(shooter.hasball != null) {
        
        wantshoot = true;
    }
    if(startedat==0) {
      startedat = gtime;
      timer = new timer(60);
      addMover(timer,mover.WIDTH-timer.w,0);
    }
    return false;
  }

  //=======================================================================================
  class nestsupport extends mover {
    int thick;
    nestsupport() {
      w = mover.WIDTH/20;
      h = mover.HEIGHT/3 + u.rand(mover.HEIGHT/2);
      thick = w/10;
      addMover(this, mover.WIDTH/2 - w/2, mover.HEIGHT - h);
    }

    public void paint(Graphics g, int x1, int y1, int w1, int h1) {
      int th = thick*screenwidth/mover.WIDTH;
      g.setColor(Color.black);
      g.fillRect(x1,y1+h1-th,w1,th);
      g.fillRect(x1+w1/2-th/2,y1,th,h1);
    }

  }

  //=======================================================================================
  class nest extends mover {
    int reward;
    String s;
    sharkpoly pp;
    nest(int reward1, int pos) {
      reward = reward1;
      s = String.valueOf(reward);
      w = mover.WIDTH / 12;
      h = mover.HEIGHT / 16;
      switch(pos) {
        case 0:
          addMover(this, nestsupport.x + nestsupport.w/2-w/2, nestsupport.y - h);
          break;
        case 1:
          addMover(this, leftpos? nestsupport.x + nestsupport.w/2 - nestsupport.thick/2-w:nestsupport.x + nestsupport.w/2 + nestsupport.thick/2,
                   nestsupport.y + nestsupport.h/4);
          break;
        case 2:
          addMover(this, (!leftpos)? nestsupport.x + nestsupport.w/2 - nestsupport.thick/2-w:nestsupport.x + nestsupport.w/2 + nestsupport.thick/2,
                   nestsupport.y + nestsupport.h/2);
          break;
      }
    }

    public void paint(Graphics g, int x1, int y1, int w1, int h1) {
      g.setColor(Color.black);
      ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
      ((Graphics2D)g).setStroke(new BasicStroke(4.0f));
      g.drawArc(x1, y1-h1, w1, h1*2 , 0,-180);
      ((Graphics2D)g).setStroke(new BasicStroke(1.0f));
   //   g.drawArc(x1-1, y1-h1-1, w1+2, h1*2+2 , 0,-180);
   //   g.drawArc(x1-2, y1-h1-2, w1+4, h1*2+4 , 0,-180);
      ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);
      if(f==null) {
         f = sizeFont(new String[]{String.valueOf(allscore)}, w1,h1);
         m = getFontMetrics(f);    }
      g.setFont(f);
//      g.setColor(Color.white);
//      g.drawString(s,x1+w1/2 - m.stringWidth(s)/2, y1+m.getAscent());
    }

  }

  //=======================================================================================
  class shooter extends mover {
    int xx1,yy1,lastx1,lasty1,lastx,lasty;
    long lasttime = gtime;
    boolean shooting;
    ball hasball;
    int uptoy,th;
    long stayattop;
    shooter() {
      w = ballw*5/4;
      h = mover.HEIGHT/6;
      addMover(this,0,mover.HEIGHT-h);
    }

    public void changeImage(long currtime) {
      x = tox = Math.max(0, gamePanel.mousex - w);
      if(stayattop> gtime)  uptoy = y+ballw*screenwidth/screenheight;
//startPR2007-10-30^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      /*
       I thought it might be better if the mouse was at the top of the ball rather
       than at the bottom to prevent children clicking on the task bar when they
       pull the ball down and possibly activating other applications.
       */
//      else uptoy = Math.min(y+h*7/8,Math.max(y+ballw*screenwidth/screenheight,gamePanel.mousey));
      else uptoy = Math.min(y+h*7/8,Math.max(y+ballw*screenwidth/screenheight,gamePanel.mousey+(ballw*2)));
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      if(wantshoot){
          shoot();
          wantshoot = false;
      }
    }
    void shoot() {
      if(uptoy < y+ballw*screenwidth/screenheight*5/4) return;
      if(shooter.hasball!=null)shooter.hasball.baseBall();
      double maxshot = Math.sqrt((double)10000*g*(mover.HEIGHT-h));
      int bottom = y+h-th*2*mover.HEIGHT/screenheight;
      int top = y+ballw*screenwidth/screenheight;
      hasball.droptime=gtime;
      hasball.starty = y;
      hasball.dx = 0;
      hasball.startspeedy = - (long)(maxshot*(uptoy-top)/(bottom -top));
      hasball.inshooter = false;
      stayattop = gtime+1000;
      hasball = null;

    }
    public void paint(Graphics g, int x1, int y1, int w1, int h1) {
       th = (w1-ballw*screenwidth/mover.WIDTH)/2-1;
       int upto = uptoy*screenheight/mover.HEIGHT;
       g.setColor(Color.black);
       g.fillRect(x1,y1,th,h1);
       g.fillRect(x1+w1-th,y1,th,h1);
       g.fillRect(x1,y1+h1-th,w1,th);
       g.setColor(Color.blue);
       g.fillRect(x1+th+2,upto,w1 - th*2-2,th);
       int dx = (y1+h1-th-upto-th+5)/10;
       g.drawLine(x1+w1/2,upto+th,x1+w1-th-2,upto+th+dx);
       g.drawLine(x1+th+2,upto+th+dx*3,x1+w1-th-2,upto+th+dx);
       g.drawLine(x1+th+2,upto+th+dx*3,x1+w1-th-2,upto+th+dx*5);
       g.drawLine(x1+th+2,upto+th+dx*7,x1+w1-th-2,upto+th+dx*5);
       g.drawLine(x1+th+2,upto+th+dx*7,x1+w1-th-2,upto+th+dx*9);
       g.drawLine(x1+w1/2,y1+h1-th,x1+w1-th-2,upto+th+dx*9);
    }
  }
  //=======================================================================================
  class ball extends mover {
    double dx;
    int dy, lastdist=1,lastx1,lasty1;
    long startspeedy;
    int starty;
    int ylim;
    long droptime = gtime,lasttime=gtime;
    boolean replaced;
    boolean inshooter=true,innest;
    ball() {
      w = ballw;
      h = w*screenwidth/screenheight;
      startspeedy = 0;
      shooter.hasball = this;
      addMover(this, windspeed>0 ? 0:mover.WIDTH-w,starty = 0);
      ++hadball;
    }

    public void baseBall() {
       if(inshooter) {
          x = shooter.x+(shooter.w-ballw) / 2;
          y = shooter.uptoy - h;
       }
    }

    public void changeImage(long currtime) {
        long dt = (currtime - droptime);
        long dtx = currtime-lasttime;
        int i;
        if(dx==0) {
          int test=0;
        }
        if(!innest && !inshooter) {
          dx += (windspeed - dx) * dtx /4000;
          x += (int) (dx * dtx / 4000);
          y = starty + (int) ( (startspeedy * dt + g * dt * dt / 1000/2) / 4000000);
          dy = (int) ( (startspeedy  + g * dt / 1000));
          if (x < 0)  { x=0;  dx = Math.abs(dx);}
          if (x +w > mover.WIDTH)  { x=mover.WIDTH-w; dx = -Math.abs(dx);}
          if (y < 0)  {  starty=y=0; startspeedy = Math.abs(dy);droptime=currtime;}
          if (y > mover.HEIGHT-h)  {kill = true;}
          if( !replaced && y > nest.y && dy > 0) {replaced=true; newball=true;}
          if(toy+h/2 < nest.y && y+h/2 >nest.y) {
             if(u.linescross(tox+w/2,toy+h/2, x+w/2, y+h/2,nest.x,nest.y,nest.x+nest.w,nest.y)) {
               innest = true;
               gamePanel.bringtotop(nest);
               ylim = nest.y + nest.h - h - winners*nest.h/8;
               ++winners;
               winddir = !winddir;
               windspeed = (mover.WIDTH / 4 + u.rand(mover.WIDTH/2)) *  (winddir ? 1 : -1);
               newball=true;
             }
          }
//          if(toy+h/2 < shooter.y && y+h/2 >shooter.y) {
//            int th = (shooter.w-ballw) / 2;
//            if (u.linescross(tox + w / 2, toy + h / 2, x + w / 2, y + h / 2,
//                           shooter.x+th, shooter.y, shooter.x + shooter.w-th, shooter.y)) {
//              inshooter = true;
//              shooter.hasball = this;
//            }
//          }
        }
        if(inshooter) {
            baseBall();
        }
        if(innest) {
          if(y < ylim) {
             y = Math.min( ylim ,starty + (int) ( (startspeedy * dt + g * dt * dt / 1000/2) / 4000000));
             while((long)(y+h/2-nest.y)*(y+h/2-nest.y)*screenheight*screenheight
                  + (long)(x +w/2 - nest.x-nest.w/2)*(x +w/2 - nest.x-nest.w/2)*screenwidth*screenwidth
                      >  (long)(nest.w/2- w/2)*(nest.w/2- w/2)*screenwidth*screenwidth) {
                if(x+w/2 < nest.x+nest.w/2) ++x;
                else if(x+w/2 > nest.x+nest.w/2) --x;
                else break;
             }
          }
          if(y >= ylim) {
            while((long)(y+h/2-nest.y)*(y+h/2-nest.y)*screenheight*screenheight
                 + (long)(x +w/2 - nest.x-nest.w/2)*(x +w/2 - nest.x-nest.w/2)*screenwidth*screenwidth
                     <  (long)(nest.w/2- w/2)*(nest.w/2- w/2)*screenwidth*screenwidth) {
               if(x+w/2 < nest.x+nest.w/2) --x;
               else  ++x;
             }
         }
        }
        else if(y+h/2 > nest.y && y+h/2 < nest.y+nest.h) {
            while((long)(y+h/2-nest.y)*(y+h/2-nest.y)*screenheight*screenheight
                    + (long)(x +w/2 - nest.x-nest.w/2)*(x +w/2 - nest.x-nest.w/2)*screenwidth*screenwidth
                        <  (long)(nest.w/2+ w/2)*(nest.w/2+ w/2)*screenwidth*screenwidth) {
                if(x+w/2 < nest.x+nest.w/2) --x;
                else  ++x;
            }
        }
        tox = x;
        toy = y;
        lasttime=currtime;
    }

    public void paint(Graphics g, int x1, int y1, int w1, int h1) {
        g.setColor(Color.white);
        g.fillOval(x1, y1, w1, h1);
        g.setColor(gamePanel.getBackground());
        g.drawOval(x1, y1, w1, h1);
    }

  }
 //===================================================================================================
 class wind extends mover {
     int startx;
     long starttime = gtime;
     Color col = gamePanel.getBackground().darker();
     int lastwind=windspeed;
     sharkImage cloud;
     wind(int yy) {
       int ws = Math.abs(windspeed) - mover.WIDTH/4;
       int type = ws/(mover.WIDTH/6);
       cloud = sharkImage.random("windy_" + new String[]{"slow","mid","fast"}[type]);
       cloud.w = mover.WIDTH;
       cloud.h = mover.HEIGHT/30 + u.rand(mover.HEIGHT/30);
       cloud.adjustSize(screenwidth,screenheight);
       w = cloud.w/2 + cloud.w * (ws%(mover.WIDTH/6)) / (mover.WIDTH/6);
       h = cloud.h;
       cloud.distort = true;
       addMover(this, startx=u.rand(mover.WIDTH+w*2)-w, yy-h/2);
       gamePanel.puttobottom(this);
     }
     public void changeImage(long currtime) {
        if(completed) kill=true;
        if(lastwind != windspeed) {
           startx = x;
           starttime=gtime;
           lastwind = windspeed;
        }
        x = startx + (int)(windspeed * (gtime-starttime)/4000);
        if(x < -w) {x = startx = mover.WIDTH; starttime = gtime;}
        else if(x>mover.WIDTH) {x=startx=-w;starttime = gtime;}
        tox=x;
     }
     public void paint(Graphics g,int x1, int y1, int w1, int h1) {
//       int yy = y1+h1/2, tt = h1/6, xx = windspeed > 0? x1+w1-h1/2: x1+h1/2,
//           xs =  windspeed > 0?x1+w1:x1, xe = windspeed > 0?x1:x1+w1;
//       g.setColor(col);
//       g.fillPolygon(new int[] {xs,xx,xx,xe,xe,xx,xx}, new int[] {yy,y1,yy-tt,yy-tt,yy+tt,yy+tt,y1+h1},7);
         cloud.paint(g,x1,y1,w1,h1);
      }
 }
}
