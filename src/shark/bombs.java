package shark;

import java.awt.*;

public class bombs extends  sharkGame {
   public char items[];
   int bwidth,bheight;
   boolean darkballs;
   short o[],curr;
   static final int gfactor = 17000;
   int lastmousex=-1;
   sharkImage bombimages[];
   gun guns[];
   bomb bombs[];
   boolean usedim[];
   sharkImage gunimages[];
   boolean usedimg[];
   short droptot,deadguns;
   int blasttime = 800;
   int bulletspeed = mover.HEIGHT/500;
   short bombgot,wantbomb = 12,dropping;
   long delaytime;
   final int showlim = 4000;
   final int smoketime = 1000;
   final int initspeed1 = mover.HEIGHT;
   final int initspeed2 = mover.HEIGHT*2/3;
   boolean caps;
   boolean wantag,wanthn,wantot,wantuz, sayphonic;
   int bombbottom = mover.HEIGHT;

   public bombs() {
    errors = false;
    gamescore1 = true;
    deaths = true;
 //    peeps = true;
 //    listen= true;
 //    peep = true;
    wantspeed = true;
    optionlist = new String[] {"bombs-alpha","sort-capitals","sort-ag","sort-hn","sort-ot","sort-uz","sort-sayphonic"};
   // opnarr = new String[] {"1 bomb|2 bombs|3 bombs|4 bombs|5 bombs|6 bombs"};
    rgame.options |= word.CENTRE;
    help("help_bombs");
    buildTopPanel();
//    gamePanel.setBackground(u.brightcolor());
    if(keypad.keypadname == null) gamePanel.showSprite = false;
    gamePanel.clearWholeScreen = true;
   setup1();
   }
   //------------------------------------------------------------------
   void setup1() {
    int i,j,extrax=0;
    if(keypad.keypadname != null) {
      keypad.activate(this, null);
      extrax = keypad.keypadwidth(this) * mover.WIDTH / screenwidth;
      if (extrax > mover.WIDTH / 3) {
        extrax = 0;
        bombbottom = mover.HEIGHT - keypad.keypadheight(this) * mover.HEIGHT / screenheight;
        new fillbottomleft(keypad.keypadheight(this)*mover.HEIGHT/screenheight);
      }
     }
    droptot = options.optionval(optionlist[0]);
    caps = options.option(optionlist[1]);
    wantag = options.option("sort-ag");
    wanthn = options.option("sort-hn");
    wantot = options.option("sort-ot");
    wantuz = options.option("sort-uz");
    sayphonic = options.option("sort-sayphonic");
    markoption();
    String itemlist = rgame.getParm("list");
    if(caps) itemlist = itemlist.toUpperCase();
    items = new char[itemlist.length()];
    for(i=0;i<itemlist.length();++i)
           items[i] = itemlist.charAt(i);
    java.util.Arrays.sort(items);
    bombimages = sharkImage.findall("bomb_");
    usedim = new boolean[bombimages.length];
    gunimages = sharkImage.findall("gun_");
    usedimg = new boolean[gunimages.length];
    bwidth = (mover.WIDTH-extrax)/Math.max(items.length,10);
    bombs = new bomb[wantbomb];
    buildguns();
    if((!wantag ||!wanthn ||!wantot ||!wantuz) && (wantag || wanthn || wantot || wantuz)) {
      String s = (wantag?u.gettext("sort-ag","letters"):"")
          + (wanthn?u.gettext("sort-hn","letters"):"")
          + (wantot?u.gettext("sort-ot","letters"):"")
          + (wantuz?u.gettext("sort-uz","letters"):"");
       if(caps) s = s.toUpperCase();
       for (i = 0; i < guns.length; ++i) {
          if (s.indexOf(items[i]) < 0)   {
            guns[i].ignore = true;
            ++deadguns;
          }
      }
    }
    wordfont = sizeFont(null,"w",bwidth, bheight*4/10);
    metrics = getFontMetrics(wordfont);
    o = u.shuffle(u.select((short)items.length,(short)items.length));
    nextbomb();
    lastmousex = gamePanel.mousex;
  }
  boolean singleword() {
      int i;
      for(i=0;i<rgame.w.length;++i) {
        if(rgame.w[i].v().length() != 1) return false;
      }
      return true;
  }
 //--------------------------------------------------------------
 public void restart() {
    int i,j,ii,jj;
    droptot = options.optionval(optionlist[0]);
    caps = options.option(optionlist[1]);
    if (droptot < 0)   droptot=1;
    wantag = options.option("sort-ag");
    wanthn = options.option("sort-hn");
    wantot = options.option("sort-ot");
    wantuz = options.option("sort-uz");
    sayphonic = options.option("sort-sayphonic");
    markoption();
    for(i=0;i<items.length;++i) {
       if(caps) items[i] = Character.toUpperCase(items[i]);
       else items[i] = Character.toLowerCase(items[i]);
    }
    String s = u.lowercase;
    if((!wantag ||!wanthn ||!wantot ||!wantuz) && (wantag || wanthn || wantot || wantuz))
       s = (wantag?u.gettext("sort-ag","letters"):"")
          + (wanthn?u.gettext("sort-hn","letters"):"")
          + (wantot?u.gettext("sort-ot","letters"):"")
          + (wantuz?u.gettext("sort-uz","letters"):"");
    if(caps) s = s.toUpperCase();
    for(i=0;i<guns.length;++i) {
      boolean wantignore = s.indexOf(items[i]) < 0;
      if(guns[i].ignore && !wantignore) {
         guns[i].ignore = false;
         guns[i].im1.ghost = false;
         --deadguns;
      }
      else if(!guns[i].ignore && wantignore) {
         guns[i].ignore = true;
         guns[i].im1.ghost = true;
         ++deadguns;
      }
      addMover(guns[i], guns[i].x, guns[i].y);
    }
    bombloop: for(i=0;i<bombgot;++i) {
        if(bombs[i].falling) {
          for(j=0;j<guns.length;++j) {
             if(guns[j].ignore && guns[j].x + guns[j].w > bombs[i].x && guns[j].x  < bombs[i].x + bombs[i].w) {
                int o[] = u.shuffle(u.select(guns.length,guns.length));
                iiloop: for(ii=0;ii<o.length;++ii) {
                  jj = o[ii];
                  for(j = jj;  j<guns.length && guns[j].x < guns[jj].x+bombs[i].w; ++j) {
                     if(guns[j].ignore) continue iiloop;
                  }
                  bombs[i].x = guns[jj].x;
                  continue bombloop;
                }
             }
          }
        }
    }
    for(i=0;i<bombgot;++i)
         if(bombs[i].falling) addMover(bombs[i],bombs[i].x,bombs[i].y);
    nextbomb();
 }
 //--------------------------------------------------------------
 public void keyhit(char key) {
    if(delaytime > 0 && gtime < delaytime){
      if(spokenWord.endsay < gtime)
        noise.beep();
      delaytime = Math.min(gtime+1000,delaytime+400);
      return;
     }
    delaytime = gtime + 400;
    if(caps) key = Character.toUpperCase(key);
    else key = Character.toLowerCase(key);
    for(short i=0;i<items.length;++i) {
       if(key == items[i] && guns[i].dead == 0 && !guns[i].ignore) {
          bullets bb = new bullets(i);
          addMover(bb, i*bwidth + bwidth/2 - bb.w/2,
                        mover.HEIGHT - guns[i].h - bb.h);
          smoke ss = new smoke();
          addMover(ss, i*bwidth + bwidth/2 - ss.w/2,
                        mover.HEIGHT - guns[i].h - bheight);
          guns[i].showvalue = gtime;
          guns[i].im1.setControl("fire");
          if(sayphonic) spokenWord.sayPhonic(new String(new char[]{key}));
          break;
        }
    }
 }
 //--------------------------------------------------------------
  void buildguns() {
     guns = new gun[items.length];
     for(short i=0; i<items.length; ++i) {
        guns[i] = new gun(i);
     }
  }
  //----------------------------------------------------------------
  void nextbomb() {
    int i;
//startPR2005-04-18^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    if(Demo_base.isDemo){
      if (Demo_base.demoIsReadyForExit(1)) return;
    }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     if(dropping==0 && bombgot >=wantbomb || deadguns == guns.length) {
        String message;
        gamescore(-Math.min(gametot1,  deathtot));
        if(gametot1>bombgot*3/4)  message = rgame.getParm("wonderful") ;
        else if(gametot1>bombgot/2)   message = rgame.getParm("good");
        else   message = rgame.getParm("end");
        showmessage(u.edit(message,String.valueOf(gametot1)),
           mover.WIDTH/4,mover.HEIGHT/3, mover.WIDTH*3/4,mover.HEIGHT*2/3);
        exitbutton(mover.HEIGHT*2/3);
        gamePanel.showSprite = true;
        score(gametot1);
        return;
     }
     else{
       if(delayedflip && !completed){
           flip();
       }
     }
     while(dropping<droptot && bombgot < wantbomb) {
        do {
           i = o[curr++ % items.length];
        }while(guns[i].dead != 0 || guns[i].ignore);
        bombs[bombgot++] = new bomb((short)i);
        ++dropping;
     }
  }
  //---------------------------------------------------------------------
  class bomb extends mover {
     int dy,dx,bouncedy;
     boolean falling;
     long exploding;
     long lasttime;
     sharkImage im1;
     int blastwidth;

     bomb(short bno) {
        super(true);
        short imno = (short)u.rand(bombimages.length);
        if(usedim[imno])
            im1 = new sharkImage(bombimages[imno],false);
        else {
            im1 = bombimages[imno];
            usedim[imno] = true;
        }
        im1.w = w = bwidth*3/4 + u.rand(bwidth*3/2);
        im1.h = h = bheight;
//        im1.adjustSize(screenwidth,screenheight);
//        h = im1.h;
        im1.distort = true;
        dy = (initspeed1 + u.rand(initspeed2));
        addMover(this, Math.max(0,Math.min(mover.WIDTH-w,bwidth*bno+bwidth/2-w/2)), -h);
        falling = keepMoving = true;
        lasttime = gtime();
     }
     public void changeImage(long time) {
        int i;
        if(falling && !options.active) {
           int t1 = (int)(time-lasttime);
           y = Math.min(mover.HEIGHT-h, y + dy*t1*(speed+3)/11/gfactor);
        }
        if(exploding != 0) {
           if(time > exploding + blasttime)  {
              stopmoving();
           }
           else blastwidth = Math.max(4,(int)(w*(time-exploding)/blasttime*screenwidth/mover.WIDTH));
        }
        else if(falling) {
           if(y > bombbottom - bheight*2/3 - h) {
              exploding = time;
              for(i = x/bwidth; i <= Math.min(guns.length-1,(x+w)/bwidth);++i) {
                 if(guns[i].dead == 0) {
                    guns[i].dead = time;
                    delaytime=0;
                    if(!guns[i].ignore) ++deadguns;
                    death(1);
                 }
              }
           }
           tox=x;toy=y;
        }
        lasttime = time;
     }
      void stopmoving() {
        short i;
        falling = false;
        --dropping;
        removeMover(this);
        nextbomb();
     }
     public void paint(Graphics g,int x1, int y1, int w1, int h1) {
        if(exploding != 0) {
           u.rough(g, x1, y1, x1+w1,y1+h1, im1.getcolor(0));
           int x2 = x1 + (w1 - blastwidth)/2;
           u.rough(g,x2, y1, x2+blastwidth, y1+h1, Color.red);
           u.rough(g,x2+blastwidth/4, y1, x2+blastwidth*3/4, y1+h1, (u.rand(2)!=0)?Color.white:Color.yellow);
        }
        else im1.paint(g,x1,y1,w1,h1);
     }
  }
  class gun extends mover {
     short buckno,val;
     long showvalue;
     long dead;
     sharkImage im1;
     boolean ignore;
     gun(short bno) {
        super(false);
        buckno = bno;
        short imno = u.rand((short)gunimages.length);
        if(usedimg[imno])
            im1 = new sharkImage(gunimages[imno],false);
        else {
            im1 = gunimages[imno];
            usedimg[imno] = true;
        }
        im1.w = w = bwidth;
        im1.adjustSize(screenwidth,screenheight);
        h = im1.h;
        bheight = Math.max(bheight,h);
        addMover(this,bwidth*buckno,bombbottom -h);
        keepMoving = true;
        im1.distort = true;
     }
     public void paint(Graphics g,int x1, int y1, int w1, int h1) {
        if(dead != 0) {
           int ht;
           if(gtime-dead < blasttime*3/2)
            ht = (int)(h1/2+h1/2*(gtime-dead)/(blasttime*3/2));
           else ht = h1/2;
           u.rough(g,x1,y1+h1-ht/2,x1+w1,y1+h1,im1.getcolor(0));
           u.rough(g,x1+w1/4,y1+h1-ht,x1+w1-w1/4,y1+h1,u.redcolor());
        }
        else {
           if(ignore) im1.ghost = true;
           im1.paint(g,x1,y1,w1,h1);
           if(showvalue != 0) {
              if(gtime-showvalue > showlim) {
                 showvalue = 0;
              }
              else {
                 g.setFont(wordfont);
                 g.setColor(Color.white);
                 if(shark.macOS) ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                 g.drawString(String.valueOf(items[buckno]),x1+w1/2-metrics.charWidth(items[buckno])/2,
                              y1 + h1 - metrics.getHeight()+metrics.getAscent());
              }
           }
        }
     }
  }
  class smoke extends mover {
     long start;
     smoke() {
        super(false);
        start = gtime;
        w = bwidth;
        h = bheight*5/4;
        keepMoving = true;
     }
     public void changeImage(long time) {
        if(gtime - start > smoketime) {
           removeMover(this);
        }
     }
     public void paint(Graphics g,int x1, int y1, int w1, int h1) {
        if(gtime - start <= smoketime) {
           int h2 = (int)((gtime - start) * h1/smoketime);
           int w2 = (int)((gtime - start) * w1/smoketime);
           u.rough(g,x1+(w1-w2)/2, y1+h1-h2,x1+(w1+w2)/2,y1+h1,u.graycolor());
        }
    }
  }
  class bullets extends mover {
     long start;
     int starty;
     bullets(short gunno) {
        super(false);
        h = bheight;
        w = bwidth/6;
        start = gtime;
        starty = guns[gunno].y - h;
        keepMoving = true;
    }
     public void changeImage(long time) {
        short i;
        if(starty ==0) starty = y;
        y = starty - (int)(bulletspeed * (time-start));

        for(i=0;i<bombgot;++i)  {
           if(bombs[i].falling && x+w > bombs[i].x && x < bombs[i].x+bombs[i].w
                  && y < bombs[i].y + bombs[i].h) {
               bombs[i].exploding = time;
               removeMover(this);
               bombs[i].falling = false;
               delaytime = 0;
               gamescore(1);
               return;
           }
        }
        if(y < -h) {
           removeMover(this);
        }
        toy=y;
     }
     public void paint(Graphics g, int x1, int y1, int w1, int h1) {
        for(short i=0;i<2;++i) {
           g.setColor((u.rand(2)==0)?Color.yellow:Color.white);
           int x2 = x1 + u.rand(w1);
           g.drawLine(x2, y1+u.rand(h1/2),x2,y1 + h1/2+ u.rand(h1/2));
        }
     }
  }
  class fillbottomleft extends mover {
    Color col;
    fillbottomleft(int hh) {
      w = mover.WIDTH;
      h = hh;
      addMover(this, 0, mover.HEIGHT - hh);
      col = Color.black;
    }

    public void paint(Graphics g, int x1, int y1, int w1, int h1) {
      g.setColor(col);
      g.fillRect(x1,y1,w1,h1);
    }
  }
}
