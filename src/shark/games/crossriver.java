package shark.games;

import shark.*;
import shark.sharkGame.*;

import java.awt.*;

public class crossriver extends sharkGame {
   static final int bloodtime = 500;
   long  nextcoin,stopping,movetime,stoptime;
//   short wantcoin;
   Rectangle coinrect;
   coin_base coinpile[];
   short cointot;
   short reward1;
   int croctot = 6 + u.rand(4);
   croc crocs[];
   river river;
   long wantnext;
   long safeuntil;
   static final int safetime = 1500;
   static final byte mantot = 3;
   int currman;
   man men[] = new man[mantot];
   man man;
   sharkImage menim[] = sharkImage.randomimages("crossriver_man_",mantot);
   sharkImage landingim = new sharkImage("crossriver_landing",false);
   landing landing;
   int twoscore;
   int allscore;
   mover mess;
   int limity = mover.HEIGHT/6;
   int gotover;
   public crossriver() {
    errors = false;
//    gamePanel.dontstart=true;
//    gamescore1 = true;
//    peeps = true;
//    listen= true;
//    peep = true;
//    wantspeed = true;
    forceSharedColor = true;
    reward1 = (short)Math.max(3,sharkStartFrame.reward);
    twoscore = reward1/2;
    allscore = reward1;
    sharkStartFrame.reward = 0;
//    gamePanel.setBackground(background=u.lightbluecolor());
    gamePanel.clearWholeScreen = true;
    wantSprite = false;
    gamePanel.allclicks = true;
    buildTopPanel();
    gamePanel.setBackground(background = Color.green);
  }
  void setup() {
    int i,yy;
    river = new river();
    crocs = new croc[croctot];
    landing = new landing();
    for(i=0;i<croctot;++i) {
       crocs[i] = new croc();
    }
    for(i=0;i<mantot;++i) {
       men[i] = new man(menim[i]);
    }
 }
  //------------------------------------------------------
  public void afterDraw(long t) {
    if(river==null) {
      setup();
      man = men[0];
      clearexit = true;
      mess = showmessage(u.edit(rgame.getParm("startmess"), new int[]{allscore,twoscore}),
        0, 0,mover.WIDTH,limity);
    }
    if (wantcoin > 0 && t > nextcoin) {
      noise.coindrop();
      dropcoin();
      nextcoin = t + 600;
      score(1);
      --wantcoin;
    }
    if(wantnext != 0  && gtime > wantnext) {
      wantnext = 0;
      if(++currman >= mantot) {
        clearexit = true;
        String endmess;
        if(gotover == 3)      endmess = u.edit(rgame.getParm("gotall"), String.valueOf(wantcoin=(short)allscore));
        else if(gotover == 2) endmess = u.edit(rgame.getParm("gottwo"),String.valueOf(wantcoin=(short)twoscore));
        else if(gotover == 1) endmess = rgame.getParm("gotone");
        else  endmess = rgame.getParm("gotnone");
        showmessage(endmess, mover.WIDTH/4,mover.HEIGHT/8, mover.WIDTH*3/4, mover.HEIGHT/2);
        exitbutton(mover.HEIGHT*2/3);
      }
      else man = men[currman];
    }
    if(man != null && man.croc != null && mover.WIDTH - (mover.HEIGHT - man.y)/4 - man.x < man.croc.w) {
         landing.vis = true;
         landing.x = landing.tox = mover.WIDTH - mover.WIDTH/4*(mover.HEIGHT-man.y)/mover.HEIGHT  ;
         landing.y = landing.toy = man.y + man.h/2 - landing.h/2;
     }
     else landing.vis = false;
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
 public boolean click(int mx,int my) {
    if (mess instanceof sharkGame.messmover2 && ((sharkGame.messmover2)mess).isOver(mx, my)) {
      return false;
    }
     if(mess != null && !mess.mouseOver) {
       mess.kill = true;
       mess = null;
       limity = 0;
       spokenWord.flushspeaker(true);
    }
    if(man == null || man.croc ==  null || completed || man.jumping) return false;
               // check for near other bank
    if(gamePanel.mousex > mover.WIDTH - (mover.HEIGHT - gamePanel.mousey)/4
        && (mover.WIDTH - (mover.HEIGHT - man.y)/4 - man.x < man.croc.w || landing.vis && landing.mouseOver)) {
      man.fromx1 = man.x;
      man.fromy1 = man.y;
      man.toy1 = man.y;
      man.tox1 = mover.WIDTH - mover.WIDTH/4*(mover.HEIGHT-man.y)/mover.HEIGHT;
      man.croc = null;
      man.im.setControl("across");
      man.jumping = true;
      man.starttime = gtime;
      return true;
    }
    return false;
 }
 //=======================================================================================
 class river extends mover {
    Polygon p = new Polygon(new int[]{0,screenwidth/4,screenwidth*3/4,screenwidth},
                            new int[]{screenheight,0,0,screenheight},4);
    river() {
       w = mover.WIDTH;
       h = mover.HEIGHT;
       addMover(this,0,0);
    }
    public void paint(Graphics g,int x, int y, int w1, int h1) {
       g.setColor(Color.blue);
       g.fillPolygon(p);
    }

 }
 //=======================================================================================
 class croc extends mover {
    sharkImage im = sharkImage.random("crossriver_croc_");
    int ww = mover.WIDTH/6;
    randrange_base rrs = new randrange_base(mover.WIDTH,mover.WIDTH*8,mover.WIDTH*8);
    int sp, ds = 500 + u.rand(500);
    int turnspeed = 500 + u.rand(1000);
    double a = (-Math.PI/4 - u.rand(Math.PI/2)) ;
    Rectangle rr;
    long startblood;
    long lasttime = gtime;
    long notuntil;

    croc() {
      w = ww;
      h = w*screenwidth/screenheight;
      y = u.rand(mover.HEIGHT-h);
      x = (mover.HEIGHT-y)/4 + u.rand(mover.WIDTH - (mover.HEIGHT-y)/4 - w);
      dontgrabmouse = true;
      addMover(this, x, y);
    }
    public void changeImage(long currtime) {
      double aa,olda = a;
      int i;
      int oldx = x,oldy=y;
      w = ww * (mover.HEIGHT+y) / (mover.HEIGHT*2);
      h = w*screenwidth/screenheight;
      sp = rrs.next(currtime);
      if(man != null &&  man.croc != this && rr != null) {
        aa = Math.atan2((man.y + man.h) * screenheight - (rr.y + rr.height / 2) * mover.HEIGHT,
                         (man.x + man.w/2) * screenwidth - (rr.x + rr.width / 2) * mover.WIDTH);
        double da = (currtime - lasttime) * Math.PI * turnspeed / 1000000 ;
        if(u.anglebetween(aa,a) < da) a = aa;
        else if(u.anglebetween(a-da,aa) < u.anglebetween(a+da,aa)) a -= da;
        else a += da;
      }
      int dx = (int)(sp*Math.cos(a)/screenwidth) ;
      int dy = (int)(sp*Math.sin(a)/screenheight) ;
      y += dy;
      x += dx;
      a = u.normalrange(a);
      boolean leaveit = false;
      if(man == null ||  man.croc != this || gtime < safeuntil)  {
         for(i=0;i<crocs.length; ++i) {
            if(crocs[i] != this && (man == null || crocs[i] != man.croc || gtime < safeuntil) && close(crocs[i])) {
                x = oldx;
                y = oldy;
                if (y <crocs[i].y)         y -= mover.HEIGHT / screenheight;
                else               y += mover.HEIGHT / screenheight; ;
                if(x < crocs[i].x) x -=  mover.WIDTH/screenwidth;
                else           x +=  mover.WIDTH/screenwidth;
              }
          }
      }
      if(y < limity) { y = limity;}
      else if(y >= mover.HEIGHT - w) { y =  mover.HEIGHT - h;}
      if( x < (mover.HEIGHT - y)/4) { x = (mover.HEIGHT - y)/4;}
      else   if( x > mover.WIDTH - (mover.HEIGHT - y)/4 - w ) {
          x = mover.WIDTH - (mover.HEIGHT - y)/4 - w;
      }
      im.lefttoright = a>Math.PI/2 || a < -Math.PI/2;
      im.angle = im.lefttoright ? Math.PI - a :a;
      tox = x;toy = y;
      lasttime = gtime;
    }
    boolean close(croc c) {
      long dx = (c.x+c.w/2 -  x -w/2)*screenwidth;
      long dy = (c.y+c.h/2 -  y -h/2)*screenheight;
      return dx*dx + dy*dy < (long) w*w*screenwidth*screenwidth;
    }
    boolean close(int x1, int y1) {
      long dx = (x1*screenwidth -  (rr.x + rr.width/2)*mover.WIDTH);
      long dy = (y1*screenheight -  rr.y*mover.WIDTH);
      return dx*dx + dy*dy < (long) w*w*screenwidth*screenwidth;
    }
    public void paint(Graphics g,int x1, int y1, int w1, int h1) {
       im.paint(g,x1,y1,w1,w1);
       rr = im.getRect(0);
       if(startblood != 0) {
         if (gtime > startblood + bloodtime) {
           startblood = 0;
         }
         else {
            g.setColor(Color.red);
           int maxrad = h1/2;
           g.fillPolygon(u.randpolygon(rr.x + rr.width/2,
                                       rr.y + rr.height/2,
                                       maxrad / 4 +
                                       (int) (maxrad * 3 / 4 *
                                              (gtime - startblood) / bloodtime)));
         }
       }
    }
    public void mouseClicked(int xm, int ym) {
       if(man != null && close(man.x+man.w/2, man.y + man.h)) {
         if(man.croc != null) man.croc.notuntil = gtime + safetime*2;
        man.fromx1 = man.x;
        man.fromy1 = man.y;
        man.croc = this;
        a = u.rand(-Math.PI/3 + u.rand(Math.PI*2/3));
        rrs.setcurr(mover.WIDTH/4 + u.rand(mover.WIDTH/4));
        man.jumping = true;
        if(man.onbank){
          man.onbank = false;
          man.im.setControl("riding");
        }
        man.starttime = gtime;
        safeuntil = gtime + safetime;
       }
    }
  }
  //============================================================================================
  class man extends mover {
    int ww,hh;
    sharkImage im ;
    boolean jumping, onbank = true, finished;
    croc croc;
    int fromx1, fromy1,tox1,toy1;
    long starttime,endjump;
    int jumptime = 800,jumpheight = mover.HEIGHT/8;
    man(sharkImage im1) {
       int i;
       int repeats = 0;
       im = im1;
       im.h = mover.HEIGHT/8;
       im.w = mover.WIDTH/8;
       im.adjustSize(screenwidth, screenheight);
       w =ww= im.w;
       h =hh= im.h;
       loop1:while(++repeats < 40) {
         y = limity + u.rand(mover.HEIGHT - h - w * 4 - limity);
         x = (mover.HEIGHT - y) / 4 - w;
         for(i=0;i<men.length && men[i] != null;++i) {
            if(Math.abs(men[i].y - y) < h) continue loop1;
         }
         break;
       }
       dontgrabmouse = true;
       addMover(this,x,y);
     }
     public void changeImage(long t) {
       int i;
       w = ww * (mover.HEIGHT+y) / (mover.HEIGHT*2);
       h = hh * (mover.HEIGHT+y) / (mover.HEIGHT*2);
       if(croc != null) {
         Rectangle rr = croc.im.getRect(1);
         int cx = (rr.x + rr.width / 2) * mover.WIDTH / screenwidth - w / 2;
         int cy = rr.y * mover.HEIGHT / screenheight - h*7/8;
         if (jumping) {
           if (t > endjump) {
             jumping = false;
           }
           else {
             x = tox = (int) ( (fromx1 + cx) / 2 -
                              (cx - fromx1) / 2 *
                              Math.cos(Math.PI * (t - starttime) / jumptime));
             y = toy = (int) ( (fromy1 + cy) / 2 -
                              jumpheight *
                              Math.sin(Math.PI * (t - starttime) / jumptime));
           }
         }
         if (!jumping) {
           x = tox = cx;
           y = toy = cy;
         }
         if (gtime > safeuntil) {
           Rectangle rr1 = new Rectangle(x * screenwidth / mover.WIDTH,
                                         y * screenheight / mover.HEIGHT,
                                         w * screenwidth / mover.WIDTH,
                                         h * screenheight / mover.HEIGHT);
           for (i = 0; i < crocs.length; ++i) {
             if (crocs[i] != croc && gtime > crocs[i].notuntil) {
               Rectangle rr2 = crocs[i].im.getRect(0);
               if (rr2.intersects(rr1)) {
                 crocs[i].startblood = t;
                 kill = true;
                 man = null;
                 wantnext = gtime + 1500;
                 break;
               }
             }
           }
         }
       }
       else if (jumping) {
           if (t > endjump) {
             jumping = false;
             finished = true;
             ++gotover;
             wantnext =gtime + 1500;
             x = tox = tox1;
             y = toy = toy1-h;
             crocs[u.rand(crocs.length)].rrs.setcurr(mover.WIDTH*4);
             crocs[u.rand(crocs.length)].rrs.setcurr(mover.WIDTH*4);
//startPR2007-03-05^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
             if(y < limity) {
               toy = limity;
               y = limity;
             }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
           }
           else {
             x = tox = (int) ( (fromx + tox1) / 2 -
                              (tox1 - fromx1) / 2 *
                              Math.cos(Math.PI * (t - starttime) / jumptime));
             y = toy = (int) ( (fromy1 + toy1) / 2 -
                              jumpheight *
                              Math.sin(Math.PI * (t - starttime) / jumptime));
           }
         }
       else if(onbank && this == man) {
           y = toy = Math.min(mover.HEIGHT-w*4, Math.max(limity+h,gamePanel.mousey))-h;
           x = tox = (mover.HEIGHT-y-h) /4 - w;
       }
     }
     public void paint(Graphics g,int x1, int y1, int w1, int h1) {
       im.paint(g, x1, y1, w1, h1);
     }
  }
  class landing extends mover {
    boolean vis;
    landing () {
      landingim.w = mover.WIDTH/8;
      landingim.h = mover.HEIGHT/8;
      landingim.adjustSize(screenwidth,screenheight);
      w = landingim.w;
      h = landingim.h;
      addMover(this,0,0);
      dontgrabmouse = true;
    }
    public void paint(Graphics g,int x1, int y1, int w1, int h1) {
      if(vis) landingim.paint(g, x1, y1, w1, h1);
    }
  }
}
