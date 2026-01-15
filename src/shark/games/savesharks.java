package shark.games;

import java.awt.*;
import shark.*;

public class savesharks extends  sharkGame {
   sentence sentences[];
   String sents[];
   int margin = mover.WIDTH/20;
   int width,lineinc,curr=-1;
   int order[], stot,swidth;
   long wantnext=gtime;
   sharkImage lookfor[],copies[];
   sharkImage distract[];
   int maxwidth =mover.WIDTH/6,minwidth=mover.WIDTH/10;
   int maxheight =mover.HEIGHT/3,minheight=mover.WIDTH/5;
   int narrh = mover.HEIGHT/10,narrw = mover.WIDTH/2;
   boolean found[];
   boolean ending;
   boolean removeanimals;
   int miny;
   mover mess;
   boolean spaces;
   map map;
   boolean first = true,isstarter;
   boolean dontsee,donthear;
   mover startmessage;
   int watery =mover.HEIGHT/8,bottom = mover.HEIGHT*5/8, bottom2 = mover.HEIGHT;
   boat boat;
   sharkImage fishim[] = sharkImage.findall("savesharks_fish_");
   int fishtot = fishim.length;
   fish fish[];
   Color sky = u.bluecolor().brighter().brighter();
   net net;
   rubbish[] rubbish;
   int rubbishtot = 0;
   line line;
   long wantnet,wantrubbish,wantrubbishitem,wantline;
   diver diver;
   mover divermess;
   String usediver = rgame.getParm("usediver");
   boolean haderr;
   static final byte  NET = 0;
   static final byte  LINE = 1;
   static final byte  RUBBISH = 2;
   static final byte[] danger = new byte[]{NET,LINE,RUBBISH};


   static final int  growup = 4000; // time for baby shark to grow up
   static final int  linedrop = 2000; // time for line to drop


   public savesharks() {
    int i;
    errors = true;
    gamescore1 = true;
    peeps = false;
    listen= true;
    peep = false;
    wantspeed = true;
    bgavoid = new Color[] {Color.yellow};
    forceSharedColor = true;
    gamePanel.clearWholeScreen = true;
//    gamePanel.savefixed = true;
    sentences = sharkStartFrame.mainFrame.wordTree.getsentences(sharkStartFrame.currPlayTopic.getSpecials(new String[]{topic.sentencegames2[0]}));
    stot = sentences.length;
    swidth = mover.WIDTH/stot;
    order = u.shuffle(u.select(stot,stot));
    optionlist = new String[]{"crossword1-seehear"};
    dontsee=donthear=false;
    switch(options.optionval("crossword1-seehear")) {
           case 1:donthear = true; break;
           case 2:dontsee = true; break;
    }
//startPR2007-11-19^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    wantSprite=false;
    gamePanel.winsprite = true;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    buildTopPanel();
    gamePanel.setBackground(new Color(80,80,160+u.rand(60)));
    markoption();
    help(donthear?"help_savesharkssee":"help_savesharks");
    setlisten(!donthear);
   }
   //---------------------------------------------------------
   public void restart() {
      dontsee=donthear=false;
      switch(options.optionval("crossword1-seehear")) {
            case 1:donthear = true; break;
            case 2:dontsee = true; break;
      }
      setlisten(!donthear);
      markoption();

      curr=-1;
      haderr = false;
      divermess = null;

      wantnext=gtime;
      first=true;
      isstarter=false;
      fishtot =  fishim.length;                   // rb 9/1/08
      rubbishtot = 0;
      map = null;
 //      startmessage = showmessage(rgame.getParm("startmessage"), 0, mover.HEIGHT * 3 / 4, mover.WIDTH, mover.HEIGHT);
      spaces = options.option(optionlist[0]);
      help(donthear?"help_savesharkssee":"help_savesharks");
   }
   //------------------------------------------------------------------
   public void afterDraw(long t){
     int i;
      if(map==null) {
        map = new map();
        boat = new boat();
        fish = new fish[fishtot];
        for(i=0;i<fishtot;++i) {
           fish[i] = new fish(i);
        }
      }
      if(divermess != null && divermess.mouseOver) {
        removeMover(divermess);
        divermess = null;
      }
      if(wantnext > 0 && gtime > wantnext) {
        wantnext = 0;
        haderr = false;
        help(donthear?"help_savesharkssee":"help_savesharks");
        wantnet = wantrubbish = wantrubbishitem = wantline = 0;
        if(curr>=0) {
          sentences[order[curr]].kill = true;
          boat.endtrap();
        }
        if(++curr < stot) {
          if(curr>0) {
            givebirth();
            boat.moveto(swidth*curr + swidth/2 - boat.w/2,boat.y, 800);
          }
          setsentence();
          boat.settrap();
          if(delayedflip && !completed){
           flip();
          }
        }
        else {
           exitbutton(mover.HEIGHT*7/8);
           boat.temp = true;
           boat.moveto(mover.WIDTH,boat.y,600);
           removeMover(sentences[order[curr-1]]);
           String message=null;
           int deadtot = 0,livetot=0;
           for(i=0;i<fishtot;++i) {
              fish f = fish[i];
//startPR2007-11-12^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//              if(f.dead || f.innet || f.rub != null || f.hook != null)
              if(f.dead || f.innet || f.rub != null || f.onhook)
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                ++deadtot;
              else ++livetot;
           }
           if(deadtot == 0)    message = u.edit(rgame.getParm("nodead"),String.valueOf(livetot));
           else if(deadtot ==1)    message = u.edit(rgame.getParm("onedead"),String.valueOf(livetot));
           else if(deadtot ==2)    message = u.edit(rgame.getParm("twodead"),String.valueOf(livetot));
           else if(livetot ==0)    message = u.edit(rgame.getParm("alldead"),String.valueOf(deadtot));
           else  message = u.edit(rgame.getParm("somedead"),String.valueOf(livetot),String.valueOf(deadtot));
           if(message != null)showmessage(message,0, bottom, mover.WIDTH, bottom + mover.HEIGHT/8);
         }
      }
      if(wantnet != 0 && gtime > wantnet) {
         wantnet = 0;
         net = new net();
      }
      if(wantrubbish != 0 && gtime > wantrubbish) {
         wantrubbish = 0;
         boat.im.setControl("rubbish");
         rubbish = new rubbish[4 + u.rand(4)];
         rubbishtot = 0;
         wantrubbishitem = gtime + 1500;
      }
      if(wantrubbishitem != 0 && gtime > wantrubbishitem) {
         if(++rubbishtot <= rubbish.length) {
           rubbish[rubbishtot-1] = new rubbish();
           wantrubbishitem = gtime + 400 + u.rand(800);
         }
         else {
           wantrubbishitem = 0;
           boat.im.setControl("endrubbish");
           wantrubbish = gtime + 3000;
         }
      }
      if(wantline != 0 && gtime > wantline) {
        wantline = 0;
        line  = new line();
      }
   }
   //--------------------------------------------------------------------
   void givebirth() {
      int i, j, count = 1 + u.rand(3);
      int o[] = u.shuffle(u.select(fishtot,fishtot));
      for(i=0;i<fishtot;++i) {
        fish f = fish[o[i]];
//startPR2007-11-12^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//        if (f.bornat == 0 && !f.dead && !f.innet && f.rub == null && f.hook == null) {
        if (f.bornat == 0 && !f.dead && !f.innet && f.rub == null && !f.onhook) {
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          for (j = 0; j < count; ++j)  new fish(f);
          return;
        }
      }
      new fish(u.rand(fishim.length),true);   // extra
   }
   //---------------------------------------------------------------------
   void setsentence() {
     sentences[order[curr]].manager = gamePanel;
     sentences[order[curr]].dontsee = dontsee;
     sentences[order[curr]].donthear = donthear;
     sentences[order[curr]].nohelp = true;
     sentences[order[curr]].finished = false;               // rb 9/1/08
     sentences[order[curr]].setup(mover.WIDTH, mover.HEIGHT/4, wordlist.getNormalWordlist()) ;  // rb 16/1/08
     addMover(sentences[order[curr]],0,bottom);
   }
   public void listen1() {
     if(curr>=0 && curr < order.length)
       sentences[order[curr]].readsentence();
   }
   //-------------------------------------------------------------
  public void sparefunc() {    // after doing something in sentence
        int i,j;
        if(sentences[order[curr]].cancel) {
          new spokenWord.whenfree(100) {
            public void action() {
              int i;
              i = order[curr];
              sentences[i].kill = true;
              if (curr < stot - 1) {
                System.arraycopy(order, curr + 1, order, curr, stot - curr - 1);
                order[stot - 1] = i;
              }
              setsentence();
           }
          };
        }
        else {
          sentences[order[curr]].crosswd[sentences[order[curr]].doneword].say();
          if(sharksintrouble()) {
            help("help_savesharksdiver");
            diver = new diver();
            divermess = showmessage(usediver,mover.WIDTH/4,mover.HEIGHT/4,mover.WIDTH*3/4,mover.HEIGHT/2,Color.red);
            divermess.sayit = null;
          }
          else moveon();
         }
     }
     //---------------------------------------------------------------
     void moveon() {
          boat.endtrap();
//startPR2005-04-18^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          if (Demo_base.isDemo) {
            if (Demo_base.demoIsReadyForExit(0))
              return;
          }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          if (sentences[order[curr]].finished)
            wantnext = gtime + 2500;
     }
     //-------------------------------------------------------------------
     void finish(String mm) {
           int i;
           String ss = rgame.getParm(mm);
           for(i=0;i<lookfor.length;++i) {
              if(!found[i]) {
                 gamePanel.bringtotop(lookfor[i]);
                 lookfor[i].setControl("missed");
                 copies[i].setControl("missed");
              }
           }
           removeMover(mess);
      }
     //-------------------------------------------------------------------
     int whichlook(sharkImage im) {
        for(int i = 0;i<lookfor.length;++i) {
          if(lookfor[i] ==  im) return i;
        }
        return -1;
     }
     boolean sharksintrouble() {
        int i;
        for(i=0;i<fishtot;++i) {
           if(!fish[i].dead
               && (fish[i].innet || fish[i].onhook || fish[i].rub != null)) return true;
        }
        return false;
     }
   //===========================================================================
    class map extends mover {
       int a,d,side,ht,wi;
       int lastw,firstw;
       map() {
         int i,j;
         w = mover.WIDTH;
         h = watery;
         a = stot*5/2;
         addMover(this,0,0);
//          startmessage = showmessage(rgame.getParm("startmessage"),0,mover.HEIGHT*3/4,mover.WIDTH,mover.HEIGHT);
       }
        public void paint(Graphics g,int x1, int y1, int w1, int h1) {
         int i;
         g.setColor(sky);
         g.fillRect(x1,y1,w1,h1);
       }
    }
    //--------------------------------------------------------------------
    class fish extends sharkImage {
       randrange_base rx;
       randrange_base ry;
       int dx,dy;
       int pos;
       long bornat;
       int fullw, fullh;
       fish fi;
       rubbish rub;   // snarled up with rubbish
       boolean innet; // caught in net
       boolean onhook; //caught on line
       boolean dead;
       sharkImage hook;
       randrange_base ra;
       fish(int pos1) {
          super(fishim[pos1],false);
          pos = pos1;
          w = mover.HEIGHT/12;
          h = mover.HEIGHT/8;
          this.adjustSize(screenwidth,screenheight);
          rx= new randrange_base(0,mover.WIDTH - w, mover.WIDTH/4);
          ry= new randrange_base(watery,bottom-h, mover.HEIGHT/8);
          addMover(this,rx.next(gtime),ry.next(gtime));
      }
      fish(int pos1,boolean extra) {
         super(fishim[pos1],false);
         pos = pos1;
         w = mover.HEIGHT/12;
         h = mover.HEIGHT/8;
         this.adjustSize(screenwidth,screenheight);
         rx= new randrange_base(0,mover.WIDTH - w, mover.WIDTH/4);
         ry= new randrange_base(watery,bottom-h, mover.HEIGHT/8);
         fish newfish[] = new fish[fishtot+1];
         System.arraycopy(fish,0,newfish,0,fishtot);
         newfish[fishtot] = this;
         fish = newfish;
         ++fishtot;
         addMover(this,rx.next(gtime),ry.next(gtime));
     }
      fish(fish fi1) {     // baby of existing fish
         super(fishim[fi1.pos],false);
         fi = fi1;
         fullw = w = mover.HEIGHT/12;
         fullh = h = mover.HEIGHT/8;
         this.adjustSize(screenwidth,screenheight);
         bornat = gtime;
         fish newfish[] = new fish[fishtot+1];
         System.arraycopy(fish,0,newfish,0,fishtot);
         newfish[fishtot] = this;
         fish = newfish;
         ++fishtot;
         dx = u.rand(w/2);
         dy = -h/4+u.rand(h/2);
         rx= new randrange_base(0,mover.WIDTH - w, mover.WIDTH/4);
         ry= new randrange_base(watery,bottom-h, mover.HEIGHT/8);
         addMover(this,fi.lefttoright?fi.x+fi.w+dx:fi.x- w-dx,  fi.y+fi.h/2 - h/2+dy);
     }
     void  free() {
       rub = null;
       onhook = false;
       innet = false;
       dead = false;
       setControl("normal");
       rx.setcurr(x);
       ry.setcurr(y);
       angle = 0;
     }
       //-------------------------------------------------------------
       public void changeImage(long currtime) {
             int i;
             if(dead && !onhook) return;
             else if(innet) {
                if(net == null) {
                   dead = true;
                   setControl("dead");
                   setControl("fin.dead");
                   angle = 0;
                   moveto(Math.max(0,Math.min(mover.WIDTH-w, x-mover.WIDTH/8+u.rand(mover.WIDTH/4))), bottom2-h*3/4,2000);
                }
                else {
                  angle = -Math.PI / 8 + u.rand(Math.PI / 4);
                  lefttoright = u.rand(2) == 0;
                  x = tox = net.xx * mover.WIDTH / screenwidth - w / 8 + u.rand(w / 4) - w / 2;
                  y = toy = (net.yy - net.hh/4 + u.rand(net.hh/2)) * mover.HEIGHT / screenheight - h / 2;
                }
             }
             else if(onhook) {
               if(line == null && !dead) {
                 dead = true;
                 setControl("dead");
                 setControl("fin.dead");
                 angle = 0;
               }
               else {
                  Rectangle r = hook.getRect(2);
                  int hh = h*screenheight/mover.HEIGHT;
                  if(!dead) {
                    if (!lefttoright)  angle = +hook.angle - Math.PI / 16 - Math.PI / 2;
                    else           angle = - hook.angle + Math.PI/16 - Math.PI/2;
                  }
                  if(!lefttoright) {
                    x = tox  = (r.x + r.width/2 - hh/2 - (int)(hh*7/16*Math.cos(angle)))*mover.WIDTH/screenwidth;
                  }
                  else  {
                    x = tox = (r.x + r.width / 2 - hh/2 + (int) (hh *7/16 * Math.cos(angle)))*mover.WIDTH/screenwidth;
                  }
                  y = toy =   (r.y + r.height/2)*mover.HEIGHT/screenheight - h/2 - (int)(h/3*Math.sin(angle));
               }
             }
             else if(rub != null) {
                 int dy = rub.ry.next(currtime) / 200;
                 toy = (y += dy);
                 if(toy >= bottom2-h) {
                   y = toy = bottom2 - h;
                   dead = true;
                   setControl("dead");
                   setControl("fin.dead");
                 }
                 x = rub.rx.next(currtime);
                 lefttoright = x < tox;
                 tox = x;
             }
             else if(bornat != 0) {
              if(gtime > bornat + growup) {
                 rx.setcurr(x=tox=fi.lefttoright?fi.x+fi.w+dx:fi.x- w-dx);
                 ry.setcurr(y=toy=fi.y+fi.h/2 - h/2 +dy);
                 bornat = 0;
                 w = fullw;
                 h = fullh;
              }
              else {
                 x = tox = fi.lefttoright?fi.x+fi.w+dx:fi.x- w-dx;
                 y = toy = fi.y+fi.h/2 - h/2 +dy;
                 w = fullw/10 + (int)(fullw*9/10 * (gtime-bornat) / growup);
                 h = fullh/10 + (int)(fullh*9/10 * (gtime-bornat) / growup);
                 lefttoright = fi.lefttoright;
              }
           }
           else {
             x = rx.next(currtime);
             lefttoright = x < tox;
             tox = x;
             y = toy = ry.next(currtime);
             if(net != null && diver == null && net.caught(this) ) {
               innet = true;
            }
             else if(line != null && (i = line.caught(this)) >= 0) {
                onhook = true;
                hook = line.hooks[i];
                ra = new randrange_base(0,100,200);
                h = w*screenwidth/screenheight;
              }
             else for (i = 0; i < gamePanel.mtot; ++i) {
                 if (gamePanel.m[i] instanceof rubbish && !gamePanel.m[i].kill) {
                   rubbish ru = (rubbish) gamePanel.m[i];
                   if (Math.abs(ru.x + ru.w / 2 - (lefttoright ? x : x + w)) < ru.w / 4
                       && Math.abs(ru.y + ru.h / 2 - (y + h / 2)) < ru.y / 4) {
                     rub = ru;
                     ru.kill = true;
                     break;
                   }
                 }
             }
           }
         }
         public void paint(Graphics g,int x1, int y1, int w1, int h1) {
            super.paint(g,x1,y1,w1,h1);
            if(rub != null) {
               int hh = rub.h*screenheight/mover.HEIGHT;
               int ww = rub.w*screenwidth/mover.WIDTH;
               int xx = (lefttoright?x1:x1+w1) - ww/2;
               int yy = y1+h1/2-hh/2;
               rub.paint(g,xx,yy,ww,hh);
            }
         }
     }
     //----------------------------------------------------------------------------
     class net extends mover {
        int coils = 20,cross=8;
        int hh = mover.HEIGHT/20;
        int ww;
        int starth = hh,startw;
        int xx,yy;
        long starttime = gtime+3000;
        int tottime = 5000;
        boolean finish;
        net() {
           w = mover.WIDTH/8;
           startw = ww =  w/4;
           h = mover.HEIGHT/4 + u.rand(mover.HEIGHT/2);
//startPR2007-11-12^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
           Rectangle r = boat.im.getPolygon(24).getBounds();
           addMover(this,(r.x)*mover.WIDTH/screenwidth - w/2, (r.y + r.height*3/4)*mover.HEIGHT/screenheight);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        }
        void finish() {
           finish = true;
           tottime = Math.min(tottime,(int)(gtime-starttime + 100));
        }
        boolean caught(mover m) {
           int x1 = m.x*screenwidth/mover.WIDTH;
           int y1 = m.y*screenheight/mover.HEIGHT;
           int w1 = m.w*screenwidth/mover.WIDTH;
           int h1 = m.h*screenheight/mover.HEIGHT;
           if(y1+h1 < yy + hh/4 && Math.abs(x1+w1/2 - xx) < ww/4) {
             startw = w*3/4;
             return true;
           }
           return false;
        }
        public void paint(Graphics g,int x1, int y1, int w1, int h1) {
           int i,www,hhh;
           int midx = x1+w1/2;
           if(gtime > starttime + tottime
               && finish) {
               kill = true;
               net = null;
               return;
           }
           else if(gtime>starttime)  {
             hh = (starth + Math.abs((int)((h - starth) * Math.sin(Math.PI * (gtime - starttime)/tottime))))*screenheight/mover.HEIGHT;
             ww = (startw + Math.abs((int)((w - startw) * Math.sin(Math.PI * (gtime - starttime)/tottime))))*screenwidth/mover.WIDTH;
           }
           else {
             hh = starth*screenheight/mover.HEIGHT;
             ww = startw*screenwidth/mover.WIDTH;
           }
           xx = midx;
           yy = y1 + hh/2;
           g.setColor(Color.black);
           for(i=0;i<=coils;++i) {
             www = ww/2 + ww/2*i/coils;
             hhh = hh/6 + hh*5/6*i/coils;
             g.drawOval(midx - www/2, y1, www, hhh);
           }
           for(i=0;i<cross;++i) {
             double a = Math.PI/8 + Math.PI*3/4*i/cross;
             g.drawLine( xx + (int)(ww/4*Math.cos(a)),y1 + hh/12 + (int)(hh/12*Math.sin(a)),
                         xx + (int)(ww/2*Math.cos(a)),yy + (int)(hh/2*Math.sin(a)));
           }
        }
     }
     //=================================================================================
     class boat extends mover {
       sharkImage im = sharkImage.random("savesharks_boat_");
       byte trap = -1;
       int o[] = u.shuffle(u.select(danger.length,danger.length));
       boat() {
         im.h = watery*17/16;
         im.w = swidth;
         im.adjustSize(screenwidth,screenheight);
         w = im.w;
         h = im.h;
//startPR2007-11-12^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         addMover(this, swidth/2,watery - h*7/8);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       }
       void endtrap() {
         int i;
         wantnet = wantrubbish = wantrubbishitem = wantline = 0;
         im.setControl("normal");
         switch (trap) {
            case NET: if(net !=null) net.finish(); break;
            case RUBBISH:   // leave rubbish
               break;
            case LINE: if(line != null) line.finish(); break;
         }
       }
       void settrap() {
         endtrap();
         new spokenWord.whenfree(0) {
           public void action() {
             int delay = (donthear?3000:0) + 1000 +(8-speed)*2000;
             switch (trap = danger[o[curr % o.length]]) {
               case NET:
                 wantnet = gtime + delay; break;
               case RUBBISH:
                 wantrubbish = gtime + delay; break;
               case LINE:
                 wantline = gtime + delay; break;
             }
           }
         };
       }
       public void paint(Graphics g,int x1, int y1, int w1, int h1) {
          im.paint(g,x1,y1,w1,h1);
       }
     }
     public void error() {
        super.error();
        if(haderr) return;
        haderr = true;
        switch (boat.trap) {
           case NET:
             wantnet = gtime; break;
           case RUBBISH:
             wantrubbish = gtime; break;
           case LINE:
             wantline = gtime; break;
         }
     }
     public void error(String name) {
        super.error(name);
        if(haderr) return;
        haderr = true;
        switch (boat.trap) {
           case NET:
             wantnet = gtime; break;
           case RUBBISH:
             wantrubbish = gtime; break;
           case LINE:
             wantline = gtime; break;
         }
     }
     //=====================================================================================
     class rubbish extends mover {
       sharkImage im = sharkImage.random("savesharks_rubbish_");
       randrange_base rx;
       randrange_base ry;
       randrange_base ra;
       rubbish() {
          im.w =mover.WIDTH/12;
          im.h = mover.HEIGHT/40 + u.rand(mover.HEIGHT/60);
          im.adjustSize(screenwidth,screenheight);
          w = im.w;
          h = im.h;
//startPR2007-11-12^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          Rectangle r;
          r = boat.im.getRect(27);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          addMover(this,(r.x + r.width/4 + u.rand(r.width/2))*mover.WIDTH/screenwidth - w/2,
                   (r.y + r.height/4 + u.rand(r.height/2))*mover.HEIGHT/screenheight);
          rx= new randrange_base(curr*(swidth),(curr+1)*(swidth) - w, swidth/6);
          rx.setcurr(x);
          ry= new randrange_base(mover.HEIGHT/20,mover.HEIGHT, mover.HEIGHT/8);
          ra = new randrange_base(0,1000,500);
       }
       public void changeImage(long currtime) {
         if(completed) kill=true;
         if(y < bottom2-h) {
           int dy = ry.next(currtime) / 200;
           toy = (y += dy);
           tox = x = rx.next(currtime);
           if (y >= bottom2 - h)       toy = y = bottom2-h;
           im.angle = Math.PI * 2 * ra.next(currtime) / 1000;
         }
       }
       public void paint(Graphics g,int x1, int y1, int w1, int h1) {
         im.paint(g,x1,y1,w1,h1);
       }
    }
    class line extends mover {
      sharkImage im = sharkImage.random("savesharks_line_");
      sharkImage hooks[] = sharkImage.randomimages("savesharks_hook_",(short)5);
      randrange_base ha[] = new randrange_base[hooks.length];
      boolean hfish[] = new boolean[hooks.length];
      Polygon p;
      boolean finished;

      line() {
         int i;
         im.w =mover.WIDTH/12;
         im.h = bottom - watery;
         im.adjustSize(screenwidth,screenheight);
         im.setControl("start");
         w = im.w;
         h = im.h;
//startPR2007-11-12^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         Rectangle r;
            r = boat.im.getPolygon(24).getBounds();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         for(i=0; i<hooks.length;++i) {
            ha[i] = new randrange_base(-100,100,200);
            hooks[i].rotates = true;
            hooks[i].w = mover.WIDTH/30;
            hooks[i].h = mover.HEIGHT/26;
            hooks[i].adjustSize(screenwidth,screenheight);
         }
//startPR2007-11-12^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         addMover(this,(r.x)*mover.WIDTH/screenwidth - w/2,
                    (r.y + r.height*3/4)*mover.HEIGHT/screenheight);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         gamePanel.puttobottom(this);
         gamePanel.puttobottom(boat);
         gamePanel.puttobottom(map);
      }
      void finish() {
        int i;
         moveto(x,bottom2 - h,600);
         im.setControl("drop");
         line = null;
         finished = true;
         for(i=0; i<hooks.length;++i) {
           hooks[i].angle = u.rand(2)==0 ? Math.PI/2 : Math.PI*3/2;
         }
      }
      public void changeImage(long currtime) {
      }
      int caught(fish f) {
         int i;
         int xx = (f.lefttoright ? f.x : f.x + f.w)*screenwidth/mover.WIDTH ;
         int yy = (f.y + f.h/2) * screenheight/mover.HEIGHT;
         int ww = f.w/6*screenwidth/mover.WIDTH;
         for(i=0;i<hooks.length;++i) {
           Rectangle r = hooks[i].getRect(2);
           if(r != null && !hfish[i] && Math.abs(xx - r.x) < ww
                                        && Math.abs(yy - r.y) < ww ){
             hfish[i] = true;
             return (i);
           }
         }
         return -1;
      }
      public void paint(Graphics g,int x1, int y1, int w1, int h1) {
        int i,j, dx,dy,want = 0;
        Rectangle r;
        im.paint(g,x1,y1,w1,h1);
        p = im.getPolygon(0);
        int hw = hooks[0].w * screenwidth/mover.WIDTH;
        int hh = hooks[0].h * screenheight/mover.HEIGHT;
        int sofar[] = new int[p.npoints];
        int len = 0;
        for(i=1;i<p.npoints;++i) {
          dx = p.xpoints[i] - p.xpoints[i-1];
          dy = p.ypoints[i] - p.ypoints[i-1];
          sofar[i] = (len += Math.sqrt(dx*dx+dy*dy));
        }
        for(i=j=0;i<hooks.length;++i) {
            want = len/(hooks.length+1)*(i+2);
            for(;j<sofar.length && sofar[j] < want;++j);
            if(!finished)
                 hooks[i].angle = ha[i].next(gtime)*Math.PI/2 / 100;
            hooks[i].paint(g,p.xpoints[j] - (int)(hh/2*Math.sin(hooks[i].angle))- hh/2,
                              p.ypoints[j] - hh/6 - (hh/3 - (int)(hh/3*Math.cos(hooks[i].angle))), hh,hh);
        }
      }
   }
   class diver extends mover {
      sharkImage im = sharkImage.random("savesharks_diver");
      long finishat = gtime + 5000;
      long nextbubble = gtime + 500;
      diver() {
         im.h = mover.HEIGHT/12;
         im.w = mover.WIDTH/20;
         im.adjustSize(screenwidth,screenheight);
         h=im.h;
         w=im.w;
         gamePanel.moveWithMouse(this);
         mx = -w/2;
         my = -h/2;
      }
      public void paint(Graphics g, int x1, int y1, int w1, int h1) {
        if(gtime>finishat || !sharksintrouble()) {
          diver = null;
          gamePanel.finishwith(this);
          if(net != null) net.finish();
          if(divermess != null ) {
           removeMover(divermess);
           divermess = null;
         }
         moveon();
          return;
        }
        if(gtime>nextbubble) {
         new bubble(x + w / 2, y);
         nextbubble = gtime+100 + u.rand(500);
        }
        im.paint(g,x1,y1,w1,h1);
      }
      public void mouseClicked(int xm, int ym) {
         int i;
         for(i=0;i<fishtot;++i) {
           if(!fish[i].dead && x+w > fish[i].x && x < fish[i].x + fish[i].w
                   && y+h > fish[i].y && y < fish[i].y + fish[i].h) {
             if (fish[i].innet || fish[i].onhook || fish[i].rub != null) {
               fish[i].free();
               break;
             }
           }
         }
      }
   }
   class bubble extends mover {
     bubble(int xx, int yy) {
        w = mover.WIDTH/200 + u.rand(mover.WIDTH/200);
        h = w*screenheight/screenwidth;
        addMover(this, xx, yy-h);
        moveto(x, watery,4000);
        temp = true;
     }
     public void paint(Graphics g, int x1, int y1, int w1, int h1) {
       if(diver==null) kill = true;
         else {
           g.setColor(Color.white);
           g.fillOval(x1, y1, w1, w1);
         }
     }
   }
}
