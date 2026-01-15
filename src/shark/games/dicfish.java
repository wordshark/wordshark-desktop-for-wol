package shark.games;

import java.awt.*;
import java.awt.event.*;

import shark.*;

public class dicfish extends  sharkGame {
   public String items[];
   short o[],curr,addscore;
   String diclist[],diclist2[];
   short dic[];
   short dictot;
   Color fishcolor;
   sharkImage fishimage;
   int fishw,fishh,littlew,littleh;
   int bheight;
   static final short LITTLEFISH = 10;
   bigfish mummy;
   littlefish babies[] = new littlefish[LITTLEFISH];
   /**
    * Number of baby fish still alive.
    */
   short babiesleft;
   buoy currbuoy;
   boolean started;
   short currtest,currdic;
   String letterlist,letterlist2;
   String currstring;
   boolean wantsingle,wantpair,want2words,wantfinal,over;
   byte dir;
   short sharktot,gotshark;
   sharkImage sharki[];
   dshark sharks[] = new dshark[3];
   mover oldmessage;
   long endflash;
   long shockstart;
   static String helps[] = new String[] {"help_dicfish1","help_dicfish2","help_dicfish3","help_dicfish4"};
   long freezeuntil;

   static final byte FROMTOP = 1;
   static final byte FROMBOT = 2;
   static final byte FROMLEFT = 3;
   static final byte FROMRIGHT = 4;
   static final byte revdir[] = new byte[]{0,2,1,4,3};

   static final int LITTLEINC = mover.WIDTH/240; // max move in 40 millisec
   static final int SHARKINC =  mover.WIDTH/240;
   static final int SHOCKTIME = 200;
   static final int BIGSHOCKTIME = 1500;
   static final int BETWEENSHOCKS = 1200;
   static final int BLOODTIME = 800;
   static final int MIDWAIT = 1500;
   static final int ENDWAIT  = 4000;


   short lasterrgap;
   Polygon clickpath; // path to move along if click on gap (for whiteboard)
   int clicknext;
 //---rb  2005/07/10 -----------------------------------------------------------------------------------------
   Rectangle mouserect;
   int startx,starty;
 //---rb end 2005/07/10 -----------------------------------------------------------------------------------------
   public dicfish() {
    errors = true;
//    gamescore1 = true;
    peeps = true;
    listen= true;
    peep = true;
    wantspeed = true;
    forceSharedColor = true;
    optionlist = new String[] {"dicfish_letter","dicfish_pair","dicfish_2words","dicfish_final","dicfish_3letters","dicfish_4letters","dicfish_5letters","dicfish_6letters","dicfish_7letters","dicfish_8letters","dicfish_sharktot"};
//    opnarr = new String[] {"First letters","First 2 letters", "Between 2 words","Final location","3-letter words","4-letter words","5-letter words","6-letter words","7-letter words","8-letter words","No sharks|1 shark|2 sharks|3 sharks"};
    rgame.options |= word.CENTRE;
    gamePanel.setBackground(u.bluecolor());
    sharki = sharkImage.findall("dicshark_");
    diclist2 = db.list(sharkStartFrame.getPrimarySoundDb(sharkStartFrame.publicSoundLib),db.WAV);
    diclist2 = u.filterIrregularWords(diclist2);
    diclist = spellchange.spellchange(diclist2);
    dic = new short[diclist.length];
    buildTopPanel();
    gamePanel.showSprite = false;
    gamePanel.savefixed = true;
//    gamePanel.nomousemovers = true;
    fishw = mover.WIDTH/14;
    fishh = fishw*screenwidth/screenheight;
    littlew = mover.WIDTH/28;
    littleh = littlew*screenwidth/screenheight;
    bheight = mover.HEIGHT/10;
//startPR2008-10-31^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    gamePanel.clearWholeScreen = true;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    
  }
  //-------------------------------------------------------------
  public void afterDraw(long t) {
       if(!started && gamePanel.mousey < 0) {
          Point pt = gamePanel.getLocationOnScreen();
          gamePanel.mousemoved(sharkStartFrame.mouseonscreenx - pt.x,
                                sharkStartFrame.mouseonscreenx - pt.y);
       }
       if(!started && gamePanel.mousey >= 0) {
          started = true;
          setup1();
       }
   }
   //------------------------------------------------------------------
   void setup1() {
    short i,j;
    boolean want[] = new boolean[9];
    wantsingle = options.option(optionlist[0]);
    wantpair = options.option(optionlist[1]);
    want2words = options.option(optionlist[2]);
    wantfinal = options.option(optionlist[3]);
    want[3] =  options.option(optionlist[4]);
    want[4] =  options.option(optionlist[5]);
    want[5] =  options.option(optionlist[6]);
    want[6] =  options.option(optionlist[7]);
    want[7] =  options.option(optionlist[8]);
    want[8] =  options.option(optionlist[9]);
    sharktot = (short)(options.optionval(optionlist[10])-1);
    if(!want[3] &&  !want[4] && !want[5] && !want[6] && !want[7] && !want[8]) {
       want[3] = want[4] = want[5] = want[6] = want[7] = want[8] = true;
    }
    if(!wantsingle && !wantpair && !want2words && !wantfinal) {
       wantsingle = wantpair = want2words = wantfinal = true;
    }
    letterlist = new String();
    letterlist2 = new String();
    int len1=-1,len2=-2;
    for(i=0,dictot=0;i<diclist.length;++i) {
        j = (short)diclist[i].length();
        if(j<9 && want[j] && u.onlylower(diclist[i]))  {
           dic[dictot++] = i;
           if(len1<0 || letterlist.charAt(len1) != diclist[i].charAt(0))  {
              letterlist = letterlist + diclist[i].substring(0,1);
              ++len1;
           }
           if(diclist[i].length() >= 2
                && (len2<0 || !letterlist2.substring(len2).equals(diclist[i].substring(0,2)))) {
               letterlist2 = letterlist2 + diclist[i].substring(0,2);
               len2+=2;
           }
        }
    }
    short itemtot = 3;
    if(!wantsingle) ++itemtot;
    if(!wantpair)   ++itemtot;
    if(!want2words) ++itemtot;
    if(!wantfinal)  ++itemtot;
    o = u.shuffle(u.select(dictot,itemtot));
    curr = 0;
//    bwidth = mover.WIDTH/Math.max(items.length,10);
//    bheight = bwidth*screenwidth/screenheight;
    getfish();
    mummy.posfish();
    nextword();
    getlittlefish();
    markoption();
  }
 //--------------------------------------------------------------
 public void restart() {
    gamePanel.removeAllMovers();
    super.restart();
    curr = 0;
    currbuoy  = null;
    started = false;
//    setup1();
 }
 public boolean click(int x, int y){
   if(currbuoy != null && currbuoy.mouseOver && !currbuoy.broken) {
     int mid;
     for (int i = 0; i <= currbuoy.botot;++i) {
         switch (dir) {
           case FROMBOT:
             if(i == currbuoy.botot || gamePanel.mousex < currbuoy.borect[i].x) {
               clickpath = new Polygon();
                clicknext=-1;
                mid =  i == currbuoy.botot ? (currbuoy.borect[i-1].x + currbuoy.borect[i-1].width + mover.WIDTH)/2
                                  : ( i==0 ? currbuoy.borect[i].x/2
                                      :  (currbuoy.borect[i-1].x + currbuoy.borect[i-1].width + currbuoy.borect[i].x)/2);

              if(mummy.y < currbuoy.y + currbuoy.h+ mummy.h/2) {
                   clickpath.addPoint(mummy.x, currbuoy.y + currbuoy.h + mummy.h);
                }
                clickpath.addPoint(mid-mummy.w/2, currbuoy.y + currbuoy.h + mummy.h/2);
                clickpath.addPoint(mid-mummy.w/2, currbuoy.y - mummy.h*3);
                return true;
             }
             else if(gamePanel.mousex < currbuoy.borect[i].x+currbuoy.borect[i].width) return true;
             break;
           case FROMTOP:
             if( i == currbuoy.botot || gamePanel.mousex < currbuoy.borect[i].x) {
                clickpath = new Polygon();
                clicknext=-1;
                mid =  i == currbuoy.botot ? (currbuoy.borect[i-1].x + currbuoy.borect[i-1].width + mover.WIDTH)/2
                                  : ( i==0 ? currbuoy.borect[i].x/2
                                      :  (currbuoy.borect[i-1].x + currbuoy.borect[i-1].width + currbuoy.borect[i].x)/2);
                if(mummy.y > currbuoy.y - mummy.h*3/2) {
                   clickpath.addPoint(mummy.x, currbuoy.y - mummy.h*3/2);
                }
                clickpath.addPoint(mid-mummy.w/2,currbuoy.y - mummy.h*3/2);
                clickpath.addPoint(mid-mummy.w/2, currbuoy.y + currbuoy.h + mummy.h*2);
                return true;
             }
             else if(gamePanel.mousex < currbuoy.borect[i].x+currbuoy.borect[i].width) return true;
             break;
           case FROMRIGHT:
             if(i == currbuoy.botot || gamePanel.mousey < currbuoy.borect[i].y) {
                clickpath = new Polygon();
                clicknext=-1;
                mid =    i == currbuoy.botot ? (currbuoy.borect[i-1].y + currbuoy.borect[i-1].height + mover.HEIGHT)/2
                                  : ( i==0 ? currbuoy.borect[i].y/2
                                      :  (currbuoy.borect[i-1].y + currbuoy.borect[i-1].height + currbuoy.borect[i].y)/2);

                if(mummy.x < currbuoy.x + currbuoy.w+ mummy.w/2) {
                   clickpath.addPoint(currbuoy.x + currbuoy.w + mummy.w,mummy.y);
                }
                clickpath.addPoint(currbuoy.x + currbuoy.w + mummy.w,mid-mummy.h/2);
                clickpath.addPoint(currbuoy.x - mummy.w*3, mid-mummy.h/2);
                return true;
             }
             else if(gamePanel.mousey < currbuoy.borect[i].y+currbuoy.borect[i].height) return true;
             break;
           case FROMLEFT:
             if(i == currbuoy.botot || gamePanel.mousey < currbuoy.borect[i].y) {
                clickpath = new Polygon();
                clicknext=-1;
                mid =    i == currbuoy.botot ? (currbuoy.borect[i-1].y + currbuoy.borect[i-1].height + mover.HEIGHT)/2
                                  : ( i==0 ? currbuoy.borect[i].y/2
                                      :  (currbuoy.borect[i-1].y + currbuoy.borect[i-1].height + currbuoy.borect[i].y)/2);
                if(mummy.x > currbuoy.x - mummy.w*2) {
                   clickpath.addPoint(currbuoy.x - mummy.w*2,mummy.y);
                }
                clickpath.addPoint(currbuoy.x - mummy.w*2,mid-mummy.h/2);
                clickpath.addPoint(currbuoy.x + currbuoy.w +  mummy.w*2, mid-mummy.h/2);
                return true;
             }
             else if(gamePanel.mousey < currbuoy.borect[i].y+currbuoy.borect[i].height) return true;
             break;
          }
      }
   }
   return true;
 }
  //----------------------------------------------------------------
  void getfish() {
    short i;
    sharkImage df[] = sharkImage.findall("dicfish_");
    fishcolor = u.fishcolor();
    fishimage = df[u.rand(df.length)];
    mummy = new bigfish();
  }
  //----------------------------------------------------------------
  void getlittlefish() {
    short i;
    for(i=0;i<LITTLEFISH;++i) {
       babies[i] = new littlefish();
    }
    babiesleft = LITTLEFISH;
  }

  //----------------------------------------------------------------
  void nextword() {
     if(curr< o.length) {
       if(curr > 0 ){
           flip();
       }
       (currword = new word(diclist2[dic[currdic=o[curr]]],null)).say();
        currstring=diclist[dic[currdic=o[curr++]]];
        currtest = -1;
        nexttest();
        if(currbuoy != null) removeMover(currbuoy);
        currbuoy = new buoy();
        gamePanel.copyall = true;
        javax.swing.Timer tt = new  javax.swing.Timer(300,new ActionListener() {
           public void actionPerformed(ActionEvent e) {
              doflash(4000);
           }
        });
        tt.setRepeats(false);
        tt.start();

     }
  }
  //-------------------------------------------------------------------
  int betweenkills() {return 2000 + (8-speed)*500;}
  int betweensharks() {return 3000 + (8-speed)*500;}
  int firstshark() {return 5000 + (8-speed)*600;}
  int newbuoyshark() {return 3000 + (8-speed)*600;}
  //------------------------------------------------------------------
  void doflash(int t) {
    Rectangle r = null;
    switch (dir) {
      case FROMTOP:
        r = new Rectangle(mover.WIDTH / 4,
                          mover.HEIGHT * 5 / 6 - mover.HEIGHT / 20,
                          mover.WIDTH / 2, mover.HEIGHT / 6);
        break;
      case FROMBOT:
        r = new Rectangle(mover.WIDTH / 4, mover.HEIGHT / 20, mover.WIDTH / 2,
                          mover.HEIGHT / 6);
        break;
      case FROMLEFT:
        r = new Rectangle(mover.WIDTH / 2, mover.HEIGHT / 3, mover.WIDTH / 2,
                          mover.HEIGHT / 6);
        break;
      case FROMRIGHT:
        r = new Rectangle(0, mover.HEIGHT / 3, mover.WIDTH / 2,
                          mover.HEIGHT / 6);
        break;
    }
    if (r != null){
      new peepword(new String[] {currstring}
                   , "This is the word to 'look up'", t, r.x, r.y,
                    r.width,  r.height, this);
    }
    else
      new peepword(new String[] {currstring}
                   , "This is the word to 'look up'", t, this);
    endflash = gtime() + t;
  }
  //------------------------------------------------------------------
  short nexttest() {
     while(true) switch(currtest+1) {
        case 0:  if(wantsingle) {help(helps[0]); return (currtest=0);}
        case 1:  if(wantpair)   {help(helps[1]);return (currtest=1);}
        case 2:  if(want2words) {help(helps[2]);return (currtest=2);}
        case 3:  if(wantfinal) {help(helps[3]);return (currtest=3);}
        default: return(currtest = -1);
    }
  }
  //------------------------------------------------------------------
  short qnexttest() {
     while(true) switch(currtest+1) {
        case 0:  if(wantsingle) return (0);
        case 1:  if(wantpair)   return (1);
        case 2:  if(want2words) return (2);
        case 3:  if(wantfinal) return (3);
        default: return( -1);
    }
  }
 //-------------------------------------------------------
   public void peep1() {
     if(currstring == null) return;
     doflash(2000);
     ++peeptot;
     peepsView.setText(String.valueOf(peeptot));
     peepsView.setFont(sharkStartFrame.treefont);
     studentrecord.peepList = u.addString(studentrecord.peepList,currword.v());
   }
  //------------------------------------------------------------
  void addmessage(String m) {
     gamePanel.clearall();
     switch(dir) {
        case FROMTOP: oldmessage = showmessage(m,mover.WIDTH/8,0, mover.WIDTH*7/8,currbuoy.y,Color.white); break;
        case FROMBOT: oldmessage = showmessage(m,mover.WIDTH/8,currbuoy.y+currbuoy.h, mover.WIDTH*7/8,mover.HEIGHT,Color.white);  break;
        case FROMLEFT: oldmessage = showmessage(m,0, mover.HEIGHT/4, currbuoy.x,mover.HEIGHT*3/4,Color.white);  break;
        case FROMRIGHT: oldmessage = showmessage(m,currbuoy.x+currbuoy.w, mover.HEIGHT/4, mover.WIDTH,mover.HEIGHT*3/4,Color.white);  break;
     }
  }
  //-----------------------------------------------------------
  class buoy extends mover {
     String bo[];
     short botot;
     Rectangle borect[];
     int padx=mover.WIDTH/100, pady=mover.HEIGHT/100;
     int  bh = bheight, bw, gap;
     int targetx1,targetx2,targety1,targety2,hole1,hole2;
     boolean horz,broken;
     short currpos;
     buoy() {
        super(false);
        int totlen,start,end;
        short boindex[];
        short exclude;
        int i,j;
        w = mover.WIDTH;
        h = bh;
        lasterrgap = -1;
        if(oldmessage != null) {removeMover(oldmessage); oldmessage = null;}
        switch(currtest) {
           case 0:        // one letter
              horz = true;
              int len = letterlist.length();
              exclude = (short)letterlist.indexOf(currstring.charAt(0)) ;
              botot = (short)Math.min(7,letterlist.length()/3);
              bw = ((mover.WIDTH - 2*fishw - padx*2)*3 - fishw*(len-1))/(len+2);
              boindex = u.select((short)letterlist.length(),botot);
              wordfont = bigcharFont('m',(bw)*screenwidth/mover.WIDTH,
                           (bheight-pady*2)*screenheight/mover.HEIGHT);
              metrics = getFontMetrics(wordfont);
              bo = new String[botot];
              borect = new Rectangle[botot];
              if(u.rand(2) == 0) {
                 for(i=0; i<botot; ++i) {
                    while(i > 0 && boindex[i] < boindex[i-1]+3
                            || boindex[i] == exclude) {
                         ++ boindex[i];
                    }
                 }
                 i = (short)(botot-1);
                 while(boindex[i] > letterlist.length()-1
                            || boindex[i] == exclude) {
                         -- boindex[i];
                 }
                 for(i=(short)(botot-2); i>=0; --i) {
                    while(boindex[i] > boindex[i+1]-3
                            || boindex[i] == exclude) {
                         -- boindex[i];
                    }
                 }
              }
              else  {
                 for(i=(short)(botot-1); i>=0; --i) {
                    while(i <botot-1 && boindex[i] > boindex[i+1]-3
                            || boindex[i] == exclude) {
                         -- boindex[i];
                    }
                 }
                 i =0;
                 while(boindex[i] < 0
                            || boindex[i] == exclude) {
                         ++ boindex[i];
                 }
                 for(i=1; i<botot; ++i) {
                    while(boindex[i] < boindex[i-1]+3
                            || boindex[i] == exclude) {
                         ++ boindex[i];
                    }
                 }
              }
              gap =  (mover.WIDTH - padx*2 - bw*letterlist.length())
                                                    /(letterlist.length()+1);
              for(i=0;i<botot;++i) {
                 bo[i] = letterlist.substring(boindex[i],boindex[i]+1);
                 borect[i] = new Rectangle(padx + fishw + (mover.WIDTH-padx*2-fishw*2-bw) * boindex[i] / (letterlist.length()-1),
                                       0, bw, h);
              }
              break;
           case 1:        // two letters
              horz = true;
              for(exclude=0;exclude < letterlist2.length();exclude+=2) {
                if(letterlist2.substring(exclude,exclude+2).equals(currstring.substring(0,2))) break;
              }
              botot = (short)Math.min(7,letterlist2.length()/2-1);
              bo = new String[botot];
              borect = new Rectangle[botot];
              bw = (mover.WIDTH - fishw*(botot+1) - padx*2)/botot;
              start = Math.max(0,Math.min(letterlist2.length()-(botot+1)*2, exclude - u.rand(botot+1)*2));
              wordfont = sizeFont(null,"mm",bw,bh);
              metrics = getFontMetrics(wordfont);
              for(i=0,j=start;i<botot;j+=2) {
                 if(j != exclude) {
                    bo[i] = letterlist2.substring(j,j+2);
                    borect[i] = new Rectangle(padx + fishw + (fishw+bw)*i, 0, bw, h);
                    ++i;
                 }
              }
              break;
           case 2:        // spaced words
              horz = true;
              j=0;
              botot=5;
              bo = new String[botot];
              borect = new Rectangle[botot];
              boindex = u.select(dictot,(short)Math.min(letterlist.length()*4, dictot));
              for(i=0;i<boindex.length;++i) {
                 if (boindex[i] >= currdic) {
                    j=(short)Math.max(0,Math.min(boindex.length-botot-1,(i-u.rand(botot+1))));
                    break;
                 }
                 if (boindex[i] >  currdic) {
                    j=(short)Math.max(0,Math.min(boindex.length-botot,(i-u.rand(botot+1))));
                    break;
                 }
              }
              String totstring = new String();
              for(i=0;i<botot;++j) {
                 if(boindex[j] != currdic) {
                    bo[i] = diclist[dic[boindex[j]]];
                    totstring = totstring + bo[i];
                    ++i;
                 }
              }
              wordfont = sizeFont(null, totstring, mover.WIDTH-(padx*2 + fishw)*(botot+1), bh);
              metrics = getFontMetrics(wordfont);
              gap = (mover.WIDTH-(padx*2)*(botot+1) - metrics.stringWidth(totstring)*mover.WIDTH/screenwidth)/(botot+1);
              j = padx+gap;
              for(i=0;i<botot;++i) {
                  bw = metrics.stringWidth(bo[i])*mover.WIDTH/screenwidth + padx*2;
                  borect[i] = new Rectangle(j, 0, bw, h);
                  j += bw+gap;
              }
              break;
           case 3:        // in context
              horz = false;
              botot = 10;
              bh = pady;
              bo = new String[botot];
              borect = new Rectangle[botot];
              start = Math.max(0,Math.min(dictot-botot, currdic - u.rand(botot)));
              w = 0;
              for(i=0,j=start;i<botot;++i,++j) {
                 bo[i] = diclist[dic[j]];
              }
              wordfont = sizeFont( bo, screenwidth/4, (gap=(mover.HEIGHT-bh*(botot-1))/botot)*botot*screenheight/mover.HEIGHT);
              metrics = getFontMetrics(wordfont);
              for(i=0,j=start;i<botot;++i,++j) {
                 w = Math.max(w,
                       metrics.stringWidth(bo[i])*mover.WIDTH/screenwidth);
              }
              w += padx*2;
              for(i=0;i<botot;++i) {
                 borect[i] = new Rectangle(0,(bh+gap)*i,w,bh);
              }
              h = mover.HEIGHT;
              break;
        }
        currpos = botot;    // default
        for(i=0;i<botot;++i) {
           if(diclist[dic[currdic]].compareToIgnoreCase(bo[i]) < 0){
               currpos = (short)i;
               break;
           }
        }
        if(horz)  {
          if(mummy.y < mover.HEIGHT/2)  {
               addMover(this,0,mover.HEIGHT/2 + fishh);
               dir = FROMTOP;
               mouserect = new Rectangle(0,0,mover.WIDTH,y - fishh); // ---rb  2005/07/10 ----------------------------------------
           }
           else {
               addMover(this,0,mover.HEIGHT/2 - fishh - h);
               dir = FROMBOT;
               mouserect = new Rectangle(0,y + h,mover.WIDTH,mover.HEIGHT - y - h); // ---rb  2005/07/10 ----------------------------------------
           }
        }
        else    {
            if(mummy.x < mover.WIDTH/2)  {
               addMover(this,mover.WIDTH/2 +fishw,0);
               dir = FROMLEFT;
               mouserect = new Rectangle(0,0,x-fishw,mover.HEIGHT); // ---rb  2005/07/10 ----------------------------------------
           }
            else {
               addMover(this,mover.WIDTH/2 - fishw - w,0);
               dir = FROMRIGHT;
               mouserect = new Rectangle(x+w,0,mover.WIDTH-x-w,mover.HEIGHT); // ---rb  2005/07/10 ----------------------------------------
            }
        }
        startx = gamePanel.mousex;starty=gamePanel.mousey;    // ---rb  2005/07/10 ----------------------------------------
        gamePanel.puttobottom(this);

        if (babies[0] != null) {
            for(i=0;i<babies.length;++i) {
               if(!babies[i].dead) {
                  babies[i].finished = false;
                  switch(dir) {
                     case FROMTOP: babies[i].toy = babies[i].y  = Math.min(babies[i].y,y-littleh*2); break;
                     case FROMBOT: babies[i].toy = babies[i].y = Math.max(babies[i].y,y+h+littleh);  break;
                     case FROMLEFT:  babies[i].tox = babies[i].x = Math.min(babies[i].x,x-littlew*2);  break;
                     case FROMRIGHT: babies[i].tox = babies[i].x = Math.max(babies[i].x,x+w+littlew);  break;
                  }
                  babies[i].oldy = -1;
               }
            }
        }
        mummy.finished = false;
        if(horz) {    // targets for little fish
           if(currpos ==0) {
               hole1 = 0;
               hole2 =  borect[0].x;
           }
           else if(currpos ==botot) {
               hole1 = borect[botot-1].x + borect[botot-1].width;
               hole2 = mover.WIDTH;
           }
           else {
               hole1 = borect[currpos-1].x + borect[currpos-1].width;
               hole2 =  borect[currpos].x;
           }
           targetx1 = targetx2 = (hole1+hole2)/2;
           if(dir==FROMTOP) {
              targety1 = y - littleh*2;
              targety2 = y + h +  littleh*2;
           }
           else {
              targety2 = y - littleh*2;
              targety1 = y + h +  littleh*2;
           }
        }
        else {
           hole1 = borect[currpos-1].y + borect[currpos-1].height;
           if(currpos ==botot) {
               hole2 =  mover.HEIGHT;
           }
           else  {
               hole2 =  borect[currpos].y;
           }
           targety1 = targety2 = (hole1+hole2)/2;
           if(dir==FROMLEFT) {
              targetx1 = x - littlew*2;
              targetx2 = x + w +  littlew*2;
           }
           else {
              targetx2 = x - littlew*2;
              targetx1 = x + w +  littlew*2;
           }
        }
     }
     public void paint(Graphics g, int x1, int y1, int w1, int h1) {
         if(shark.macOS) ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        int linethick = Math.max(2,screenheight/120);
        short i, yy = (short)(y1+h1/2-linethick/2),
                      xx = (short)(x1+w1/2 - linethick/4);
        g.setColor(Color.black);
        g.setFont(wordfont);
        if(horz)  {
            g.fillRect(0,yy,screenwidth,linethick);
            if(broken) {
               g.setColor(gamePanel.getBackground());
               if(currpos == 0) g.fillRect(0,yy,borect[0].x*screenwidth/mover.WIDTH,linethick+2);
               else if(currpos == botot) g.fillRect(borect[currpos-1].x*screenwidth/mover.WIDTH,yy,screenwidth-borect[currpos-1].x*screenwidth/mover.WIDTH,linethick+2);
               else g.fillRect(borect[currpos-1].x*screenwidth/mover.WIDTH,yy,
                     (borect[currpos].x-borect[currpos-1].x)*screenwidth/mover.WIDTH, linethick+2);
            }
            g.setColor(Color.black);
            g.fillArc(-padx*screenwidth/mover.WIDTH,y1+borect[0].y*screenheight/mover.HEIGHT,
                           padx*2*screenwidth/mover.WIDTH,borect[0].height*screenheight/mover.HEIGHT,
                           -90,180);
            g.fillArc(screenwidth-padx*screenwidth/mover.WIDTH,y1+borect[0].y*screenheight/mover.HEIGHT,
                           padx*2*screenwidth/mover.WIDTH,borect[0].height*screenheight/mover.HEIGHT,
                           -90,-180);
        }
        else  {         // vertical list
            g.fillRect(xx,0,linethick/2,screenheight);
            if(broken) {
               g.setColor(gamePanel.getBackground());
               if(currpos == 1) g.fillRect(xx,y1,linethick, borect[1].y*screenheight/mover.HEIGHT);
               else if(currpos == botot) g.fillRect(xx,y1+borect[currpos-1].y*screenheight/mover.HEIGHT,linethick,(mover.HEIGHT-borect[currpos-1].y)*screenheight/mover.HEIGHT);
               else g.fillRect(xx,y1+borect[currpos-1].y*screenheight/mover.HEIGHT,
                     linethick, (borect[currpos].y-borect[currpos-1].y)*screenheight/mover.HEIGHT);
            }
        }
        for(i=0;i<botot;++i) {
           g.setColor(Color.black);
           g.fillRoundRect(x1+borect[i].x*screenwidth/mover.WIDTH, y1+borect[i].y*screenheight/mover.HEIGHT,borect[i].width*screenwidth/mover.WIDTH,
                          borect[i].height*screenheight/mover.HEIGHT,padx*2*screenwidth/mover.WIDTH,pady*2*screenheight/mover.HEIGHT);
           g.setColor(Color.white);
           if(horz) {
              g.drawString(bo[i], x1+(borect[i].x + borect[i].width/2)*screenwidth/mover.WIDTH - metrics.stringWidth(bo[i])/2,
                    y1+(borect[i].y + borect[i].height/2 )*screenheight/mover.HEIGHT - metrics.getHeight()/2 + metrics.getMaxAscent());
           }
           else {
              g.drawString(bo[i], x1+(borect[i].x + padx)*screenwidth/mover.WIDTH,
                   (borect[i].y + borect[i].height + gap/2)*screenheight/mover.HEIGHT -metrics.getHeight()/2 + metrics.getMaxAscent());
           }
        }
     }
  }
  //-------------------------------------------------------------------
  class dfish extends mover{
     sharkImage im1;
     int oldx,oldy=-1;
     short ingap=-1;
     double angle;
     boolean isbigfish,finished,dead,islittlefish;
     int dx,dy,targetx,targety;
     byte currdir;
     long lastinc = gtime;
     dfish() {
        super(false);
        keepMoving = true;
     }
     void adjust() {
        if(currbuoy == null ||  oldy < 0) return;
        short i;
        boolean wantshock = false;
        if(!finished) currdir = dir;
        else currdir = revdir[dir];
        if(currdir == FROMTOP && toy+h > currbuoy.y
            || currdir == FROMBOT && toy < currbuoy.y + currbuoy.h) {
           if(ingap < 0)   for(i=0;i<currbuoy.botot;++i) {
              if(tox + w/2 > currbuoy.borect[i].x
                   && tox + w/2 < currbuoy.borect[i].x + currbuoy.borect[i].width) {
                 if(currdir==FROMTOP)  toy =  currbuoy.y - h;
                 else              toy = currbuoy.y+currbuoy.h;
                 ingap = -1;
                 break;
              }
              else if(tox + w/2 < currbuoy.borect[i].x) {
                 ingap = i;
                 break;
              }
           }
           if(tox + w/2 >= currbuoy.borect[currbuoy.botot-1].x +  currbuoy.borect[currbuoy.botot-1].width) {
                 ingap = currbuoy.botot;
           }
           if(ingap >= 0) {
              if(currdir == FROMTOP && toy > currbuoy.y + currbuoy.h/2 - h
                  ||currdir == FROMBOT && toy < currbuoy.y + currbuoy.h/2) {
                if(!currbuoy.broken && isbigfish && ingap == currbuoy.currpos ) {
                   if(gtime < endflash && currpeep != null) currpeep.dispose();
                   currbuoy.broken = true;
                   mummy.finished = true;
                   gamePanel.clearall();
                }
                else if(!currbuoy.broken || ingap != currbuoy.currpos) {
                   if(currdir == FROMTOP) toy = currbuoy.y + currbuoy.h/2 - h;
                   else         toy = currbuoy.y + currbuoy.h/2;
                   if(gtime > endflash && gtime > shockstart + (isbigfish?BIGSHOCKTIME:SHOCKTIME)
                      && !gamePanel.mouseOutside && !finished && !over)  wantshock = true;
                }
              }
              if(toy > currbuoy.y-h && toy < currbuoy.y + currbuoy.h)
                 tox = Math.max((ingap==0)?0:(currbuoy.borect[ingap-1].x + currbuoy.borect[ingap-1].width),
                       Math.min(((ingap < currbuoy.botot)?currbuoy.borect[ingap].x:mover.WIDTH)-w, tox));
              else ingap = -1;
              if(wantshock) {
                 if(toy <currbuoy.y + currbuoy.h/2) new shock(FROMTOP,tox+w/2,isbigfish);
                 else  new shock(FROMBOT,tox+w/2,isbigfish);
                 shockstart = gtime;
                 if(gtime > spokenWord.endsay + BETWEENSHOCKS) noise.shock();
                 if(isbigfish && ingap != lasterrgap) {
                    error();
                    lasterrgap = ingap;
                    freezeuntil = gtime+1500;
                 }
              }
           }
        }
        else if(currdir == FROMLEFT && tox+w > currbuoy.x
            || currdir == FROMRIGHT && tox < currbuoy.x + currbuoy.w) {
           if(ingap < 0)   for(i=0;i<currbuoy.botot;++i) {
              if(toy + h/2 > currbuoy.borect[i].y
                   && toy + h/2 < currbuoy.borect[i].y + currbuoy.borect[i].height) {
                 if(currdir==FROMLEFT)  tox =  currbuoy.x - w;
                 else              tox = currbuoy.x+currbuoy.w;
                 ingap = -1;
                 break;
              }
              else if(toy + h/2 < currbuoy.borect[i].y) {
                 ingap = i;
                 break;
              }
           }
           if(toy + h/2 >= currbuoy.borect[currbuoy.botot-1].y +  currbuoy.borect[currbuoy.botot-1].height) {
                 ingap = currbuoy.botot;
           }
           if(ingap >= 0) {
              if(currdir == FROMLEFT && tox > currbuoy.x + currbuoy.w/2 - w
                  ||currdir == FROMRIGHT && tox < currbuoy.x + currbuoy.w/2) {
                if(!currbuoy.broken && isbigfish && ingap == currbuoy.currpos) {
                   if(gtime < endflash && currpeep != null) currpeep.dispose();
                   currbuoy.broken = true;
                   mummy.finished = true;
                   currbuoy.ended = false;
                   gamePanel.clearall();
                }
                else if(!currbuoy.broken || ingap != currbuoy.currpos) {
                   if(currdir == FROMLEFT) tox = currbuoy.x + currbuoy.w/2 - w;
                   else         tox = currbuoy.x + currbuoy.w/2;
                   if(gtime>endflash && gtime > shockstart + SHOCKTIME && !over && !finished)  wantshock = true;
                }
              }
              if(tox > currbuoy.x-w && tox < currbuoy.x + currbuoy.w)
                 toy = Math.max((ingap==0)?0:(currbuoy.borect[ingap-1].y + currbuoy.borect[ingap-1].height),
                       Math.min(((ingap < currbuoy.botot)?currbuoy.borect[ingap].y:mover.HEIGHT)-h, toy));
              else ingap = -1;
              if(wantshock) {
                 if(tox <currbuoy.x + currbuoy.w/2) new shock(FROMLEFT,toy+h/2, isbigfish);
                 else  new shock(FROMRIGHT,toy+h/2,isbigfish);
                 if(gtime > spokenWord.endsay + BETWEENSHOCKS) noise.shock();
                 shockstart = gtime;
                 if(isbigfish && ingap != lasterrgap) {
                    error();
                    freezeuntil = gtime+1500;
                    lasterrgap = ingap;
                 }
              }
           }
        }
        else ingap = -1;
     }
    //-------------------------------------------------------------------
    boolean across() {
       if( finished
              || (currbuoy != null && currbuoy.broken
               && (dir == FROMTOP && y >= currbuoy.y + currbuoy.h
                   || dir == FROMBOT && y <= currbuoy.y - h
                   || dir == FROMLEFT && x >= currbuoy.x + currbuoy.w
                   || dir == FROMRIGHT && x <= currbuoy.x - w)))
            {finished = true; return true;}
       return false;
    }
    //-------------------------------------------------------------------
    boolean overhole() {
       return  currbuoy != null && currbuoy.broken
               && (currbuoy.horz && tox >= currbuoy.hole1
                                 && tox+w <= currbuoy.hole2
                  || !currbuoy.horz && toy >= currbuoy.hole1
                                 && toy+h <= currbuoy.hole2);
    }
    //---------------------------------------------------------------
      // speed adjustment for little fish and sharks towards target
    void calcinc(int maxi) {
       int x1 = x + w/2;
       int y1 = y + h/2;
       int maxix = maxi*screenmax/screenwidth;
       int maxiy = maxi*screenmax/screenheight;
       int diffx,diffy,adx,ady,tot,tot2;
       adx=Math.abs(diffx=(targetx-x1)*screenmax/screenwidth);
       ady=Math.abs(diffy=(targety-y1)*screenmax/screenheight);
       tot = adx+ady;
       for(long ti=lastinc;ti<gtime;ti+=runMovers.REDRAWINT) {
          if(adx > maxix*10 || ady > maxiy*10) {
             dx += maxix*diffx/tot;
             dy += maxiy*diffy/tot;

             tot2 = (Math.abs(dx)+Math.abs(dy))/2;
             if(tot2 != 0) {
                dx = dx * maxix /tot2;
                dy = dy * maxiy /tot2;
             }
          }
          if(islittlefish) {
                dx +=  - maxix/2 + u.rand(maxix+1);
                dy +=  - maxiy/2 + u.rand(maxiy+1);
          }
          else {  // sharks
                dx +=  - maxix/8 + u.rand(maxix/4+1);
                dy +=  - maxiy/8 + u.rand(maxiy/4+1);
          }
       }
       tox = Math.max(0,Math.min(mover.WIDTH-w, x + (int)(dx*(gtime-lastinc)/runMovers.REDRAWINT)));
       toy = Math.max(0,Math.min(mover.HEIGHT-h, y + (int)(dy*(gtime-lastinc)/runMovers.REDRAWINT)));
       if(finished) switch(dir) {
          case FROMTOP:  toy = Math.max(currbuoy.y+currbuoy.h,toy); break;
          case FROMBOT:  toy = Math.min(currbuoy.y-h,toy); break;
          case FROMLEFT:  tox = Math.max(currbuoy.x+currbuoy.w,tox); break;
          case FROMRIGHT: tox = Math.min(currbuoy.x-w,tox); break;
       }
       lastinc = gtime;
     }
     //-------------------------------------------------------------------
     public void paint(Graphics g, int x1, int y1, int w1, int h1) {
        if((y1 - oldy)*(y1 - oldy) + (x1 - oldx)*(x1 - oldx) > 9)  {
           angle = Math.atan2(y1 - oldy,x1 - oldx);
           oldx = x1;
           oldy = y1;
        }
        if(angle > -Math.PI/2 && angle < Math.PI/2) {
            im1.angle = angle;
            im1.lefttoright = false;
        }
        else {
            im1.angle = Math.PI - angle;
            im1.lefttoright = true;
        }
        im1.paint(g,x1,y1,w1,h1);
     }

  }
  class bigfish extends dfish {
     long fintime,nextshark;
     bigfish() {
          super();
          im1 = fishimage;
          im1.recolor(Color.black,fishcolor);
          isbigfish = true;
          im1.w = w = fishw; im1.h = h = fishh;
          im1.rotates = true;
          im1.manager = gamePanel;
     }
     void posfish() {
          oldx = gamePanel.mousexs;
          oldy = gamePanel.mouseys;
          addMover(this, gamePanel.mousex, gamePanel.mousey);
          nextshark = gtime + firstshark() + 4000;
     }
     public void changeImage(long time) {
        if(currbuoy == null) return;
        if(freezeuntil==0 || gtime>freezeuntil) {
          freezeuntil = 0;
          if (clickpath != null) {
            if (!ismoving() || clicknext < 0) {
              if (++clicknext >= clickpath.npoints) {
                gamePanel.mousemoved( (x + w / 2) * screenwidth / mover.WIDTH,
                                     (y + h / 2) * screenheight / mover.HEIGHT);
                clickpath = null;
              }
              else
                moveto(clickpath.xpoints[clicknext], clickpath.ypoints[clicknext],
                       500);
            }
          }
          else {
            tox = Math.min(mover.WIDTH - w, gamePanel.mousex);
            toy = Math.min(mover.HEIGHT - h, gamePanel.mousey);
             // ---rb  2005/07/10 ----------------------------------------
            // dont move mouse if mouse has not yet entered allowed area
            if (mouserect != null && (gamePanel.mousex != startx || gamePanel.mousey != starty)) {
              if(!mouserect.contains(tox,toy)) {
                tox = x;
                toy = y;
              }
              else mouserect = null;
            }
             // ---rb end  2005/07/10 ----------------------------------------
          }
        }
        adjust();
        targetx = x + w/2 - (int)(w*2*Math.cos(angle));
        targety = y + h/2 - (int)(h*2*Math.sin(angle));
        if(currbuoy != null) {
           if(currbuoy.horz) {
              targetx =Math.max(littlew,Math.min(mover.WIDTH-littlew,targetx));
              if(y < currbuoy.y+currbuoy.h/2 )
                    targety = Math.max(littleh,Math.min(targety,currbuoy.y-littleh));
              else  targety = Math.min(mover.HEIGHT-littleh,Math.max(targety,currbuoy.y+currbuoy.h+littleh));
           }
           else {
              targety =Math.max(littleh,Math.min(mover.HEIGHT-littleh, targety));
              if(x < currbuoy.x+currbuoy.w/2 )
                    targetx = Math.max(littlew, Math.min(targetx,currbuoy.x-littlew));
              else  targetx = Math.min(mover.WIDTH-littlew, Math.max(targetx,currbuoy.x+currbuoy.w+littlew));
           }
        }
        if(finished || across()) {
           if(fintime != 0) {
              if(gtime > fintime) {
                 noise.plopplop();
                 for(short i=0;i<gotshark;++i) {
                    removeMover(sharks[i]);
                    sharks[i] = null;
                 }
                 gotshark = 0;
                 if(babiesleft==0) {
                    restart();
                    return;
                 }
//startPR2005-04-18^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                 if(Demo_base.isDemo){
                   if (Demo_base.demoIsReadyForExit(1)) return;
                 }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                 if(nexttest() >= 0) {
                    removeMover(currbuoy);
                    currbuoy = new buoy();
                    fintime = 0;
                    nextshark = gtime + newbuoyshark();
                    gamePanel.copyall = true;
                 }
                 else {
                    fintime = 0;
                    nextword();
                    nextshark = gtime + firstshark();
                 }
              }
           }
           else {
              for(short i=0;i<babies.length;++i) {
                 if(!babies[i].finished) return;
              }
              if(qnexttest() < 0) {
                 if(curr < o.length) {
                    int left = o.length - curr;
                    String m1;
                    if(left == 1)  m1 = "|" + rgame.getParm("onemore");
                    else
                       m1 = "|"  + String.valueOf(left) + " " + rgame.getParm("morewords");
                    switch(babiesleft) {
                       case 0: addmessage(rgame.getParm("lostall")); break;
                       case 1: addmessage(rgame.getParm("onlyone")+m1); break;
                       case 9: addmessage(rgame.getParm("lost1")+m1); break;
                       case 10: addmessage(rgame.getParm("allsafe")+m1); break;
                       default: addmessage(editstring(rgame.getParm("stillhave"),babiesleft)+m1);break;
                    }
                    fintime = gtime + ENDWAIT;
                 }
                 else {   // end of game
                    if(over) return;
                    over=true;
                    switch(babiesleft) {
                       case 0: addmessage(rgame.getParm("end5")); score(0);break;
                       case 1: addmessage(rgame.getParm("end4")); score(1);break;
                       case 9: addmessage(rgame.getParm("end2")); score(8);break;
                       case 10: addmessage(rgame.getParm("end1")); score(10);break;
                       default: addmessage(editstring(rgame.getParm("end3"),babiesleft)); score(4); break;
                    }
                    exitbutton(mover.HEIGHT * 3/4);
                    gamePanel.setCursor(null);
                    fintime = 0;
                 }
              }
              else  {
                if(babiesleft == 0) {
                    addmessage(rgame.getParm("lostall"));
                    fintime = gtime + ENDWAIT;
                }
                else fintime = gtime + MIDWAIT;
              }
           }
        }
        else if(gtime > nextshark && gotshark < sharktot) {
           sharks[gotshark++] = new dshark();
           nextshark = gtime + betweensharks();
        }

     }
  }
  //-----------------------------------------------------------------
  class littlefish extends dfish {
     long lasttime = gtime;
     littlefish() {
        super();
        islittlefish = true;
        im1 = new sharkImage(fishimage,true);
        im1.rotates = true;
        im1.manager = gamePanel;
        w = littlew;
        h = littleh;
        if(currbuoy.horz) {
           x = u.rand(mover.WIDTH - w);
           if(dir == FROMTOP) y = u.rand(currbuoy.y - h*3);
           else  y = currbuoy.y + currbuoy.h + h*2 + u.rand(mover.HEIGHT - currbuoy.y - currbuoy.h - h*3);
        }
        else {
           y = u.rand(mover.WIDTH - h);
           if(dir == FROMLEFT) x = u.rand(currbuoy.x - w*3);
           else  x = currbuoy.x + currbuoy.w + w*2 +u.rand(mover.WIDTH - currbuoy.x - currbuoy.w - w*3);
        }
        addMover(this,x,y);
     }
     public void changeImage(long time) {
        if(!currbuoy.broken || !mummy.finished || across()) {
          targetx = mummy.targetx;
          targety = mummy.targety;
        }
        else if(overhole()) {
          targetx = currbuoy.targetx2;
          targety = currbuoy.targety2;
        }
        else {
          targetx = currbuoy.targetx1;
          targety = currbuoy.targety1;
        }
        calcinc(LITTLEINC);
        lasttime = time;
        adjust();
     }
  }
  //-----------------------------------------------------------------
  class dshark extends dfish {
     long lastkill,lasttime=gtime;
     dshark() {
        super();
        im1 = new sharkImage(sharki[u.rand(sharki.length)],true);
        im1.rotates = true;
        im1.manager = gamePanel;
        im1.w = w = littlew*2 + u.rand(littlew);
        im1.h = h = w*screenwidth/screenheight;
        if(currbuoy.horz) {
           x = (mummy.x < mover.WIDTH/2)?(mover.WIDTH-w):0;
           if(dir == FROMBOT) y =mover.HEIGHT-h;
           else               y = 0;
        }
        else {
           y  = (mummy.y < mover.HEIGHT/2)?(mover.HEIGHT-h):0;
           if(dir == FROMRIGHT) x =mover.WIDTH-w;
           else               x = 0;
        }
        addMover(this,x,y);
     }
     public void changeImage(long time) {
        long mindist = 0x7fffffffffffffffL,dist;
        short target = -1;
        long  close = (long)w*w*screenwidth*screenwidth/8
                        + (long)h*h*screenheight*screenheight/8;
        if(currbuoy==null) return;
        for(short i = 0;i<babies.length;++i) {
           if(!babies[i].finished) {
              dist = (long)(babies[i].x - x + littlew/2 - w/2 )*(babies[i].x - x + littlew/2-w/2)*screenwidth*screenwidth
                           + (long)(babies[i].y - y +  littleh/2-h/2)*(babies[i].y - y + littleh/2-h/2)*screenheight*screenheight;
              if(dist < close && gtime > lastkill + betweenkills()) {
                 double angle2 = Math.atan2((babies[i].y-y- littleh/2)*screenheight,(babies[i].x-x- littlew/2)*screenwidth)-angle;
                 if(angle2 >  -Math.PI/4 && angle2 < Math.PI/4
                     || angle2 < -Math.PI*3/2 ||  angle2 > Math.PI*3/2) {
                    babies[i].finished = babies[i].dead = true;
                    removeMover(babies[i]);
                    noise.squeek();
                    new blood(this);
                    --babiesleft;
                    lastkill = gtime;
                 }
              }
              if(dist < mindist) {mindist = dist; target = i;}
           }
        }
        if(target != -1) {
           targetx = babies[target].x+littlew/2;
           targety = babies[target].y+littleh/2;
        }
        calcinc(SHARKINC);
        lasttime = time;
        switch(dir) {
           case FROMTOP: toy = Math.min(toy, currbuoy.y - h); break;
           case FROMBOT: toy = Math.max(toy, currbuoy.y + currbuoy.h);break;
           case FROMLEFT: tox = Math.min(tox, currbuoy.x - w); break;
           case FROMRIGHT: tox = Math.max(tox, currbuoy.x + currbuoy.w); break;
        }
     }
  }
  //---------------------------------------------------------------
  class shock extends mover {
     byte dir;
     long starttime;
     boolean isbigfish;
     shock(byte dir1, int pos,boolean isbig) {
          super(false);
          isbigfish = isbig;
          dir=dir1;
          if(dir == FROMTOP) {
             h = mover.HEIGHT/8;
             w = mover.WIDTH/20;
             y = currbuoy.y + currbuoy.h/2 - h;
             x = pos-w/2;
          }
          else if(dir == FROMBOT) {
             h = mover.HEIGHT/8;
             w = mover.WIDTH/20;
             y = currbuoy.y + currbuoy.h/2;
             x = pos-w/2;
          }
          if(dir == FROMLEFT) {
             h = mover.HEIGHT/20;
             w = mover.WIDTH/8;
             x = currbuoy.x + currbuoy.w/2 - w;
             y = pos-h/2;
          }
          else if(dir == FROMRIGHT) {
             h = mover.HEIGHT/20;
             w = mover.WIDTH/8;
             x = currbuoy.x + currbuoy.w/2;
             y = pos-h/2;
          }
          keepMoving = true;
          starttime = gtime;
          addMover(this,x,y);
     }
     public void changeImage(long time) {
        if(time > starttime + (isbigfish?BIGSHOCKTIME:SHOCKTIME))  removeMover(this);
     }
     public void paint(Graphics g, int x1, int y1, int w1, int h1) {
        short i,lines = (short)(3 + u.rand(3));
        for(i=0;i<lines;++i) {
           g.setColor((u.rand(2)==0)?yellow():Color.white);
           if(dir == FROMTOP) {
              int dx = -(w1/8) + u.rand(w1/4);
              int dy = -u.rand(h1/16);
              int topy1 = y1 +   u.rand(h1/2);
              int topy2 = topy1 + (y1+h1-topy1)/3 +u.rand((y1+h1-topy1)/3);
              int topx1 = x1 +w1/6 + u.rand(w1*2/3);
              int topx2 = x1 + w1/2 + (topx1-x1-w1/2) * (y1+h1-topy2) / (y1+h1-topy1);
              g.drawLine(x1+w1/2, y1+h1-1, topx2-dx, topy2-dy);
              g.drawLine(topx2-dx,topy2-dy,topx2+dx,topy2+dy);
              g.drawLine(topx2+dx,topy2+dy,topx1,topy1);
           }
           else if(dir == FROMBOT) {
              int topy1 = y1 + h1/2  + u.rand(h1/2);
              int topy2 = topy1 - (topy1-y1)/3 +u.rand((topy1-y1)/3);
              int topx1 = x1 +w1/6 + u.rand(w1*2/3);
              int topx2 = x1 + w1/2 + (topx1-x1-w1/2) * (topy2-y1) / (topy1-y1);
              int dx = -(w1/8) + u.rand(w1/4);
              int dy = u.rand(h1/16);
              g.drawLine(x1+w1/2, y1, topx2-dx, topy2-dy);
              g.drawLine(topx2-dx,topy2-dy,topx2+dx,topy2+dy);
              g.drawLine(topx2+dx,topy2+dy,topx1,topy1);
           }
           if(dir == FROMLEFT) {
              int dy = -(h1/8) + u.rand(h1/4);
              int dx = -u.rand(w1/16);
              int topx1 = x1 +   u.rand(w1/2);
              int topx2 = topx1 + (x1+w1-topx1)/3 +u.rand((x1+w1-topx1)/3);
              int topy1 = y1 +h1/6 + u.rand(h1*2/3);
              int topy2 = y1 + h1/2 + (topy1-y1-h1/2) * (x1+w1-topx2) / (x1+w1-topx1);
              g.drawLine( x1+w1-1,y1+h1/2, topx2-dx, topy2-dy);
              g.drawLine(topx2-dx,topy2-dy,topx2+dx,topy2+dy);
              g.drawLine(topx2+dx,topy2+dy,topx1,topy1);
           }
           else if(dir == FROMRIGHT) {
              int topx1 = x1 + w1/2  + u.rand(w1/2);
              int topx2 = topx1 - (topx1-x1)/3 +u.rand((topx1-x1)/3);
              int topy1 = y1 +h1/6 + u.rand(h1*2/3);
              int topy2 = y1 + h1/2 + (topy1-y1-h1/2) * (topx2-x1) / (topx1-x1);
              int dy = -(h1/8) + u.rand(h1/4);
              int dx = u.rand(w1/16);
              g.drawLine( x1,y1+h1/2, topx2-dx, topy2-dy);
              g.drawLine(topx2-dx,topy2-dy,topx2+dx,topy2+dy);
              g.drawLine(topx2+dx,topy2+dy,topx1,topy1);
           }
        }
     }
  }
  //---------------------------------------------------------------
  class blood extends mover {
     long starttime;
     dshark shark1;
     blood(dshark shark) {
          super(false);
          shark1 = shark;
          keepMoving = true;
          starttime = gtime;
          w = littlew;
          h = littleh;
          x = shark1.x;
          y = shark1.y;
          addMover(this,x,y);
     }
     public void changeImage(long time) {
        if(time > starttime + BLOODTIME)  removeMover(this);
        else {
           tox = shark1.x + shark1.w/2 + (int)(shark1.w/2*Math.cos(shark1.angle))-w/2;
           toy = shark1.y + shark1.h/2 + (int)(shark1.h/2*Math.sin(shark1.angle))-h/2;
        }
     }
     public void paint(Graphics g, int x1, int y1, int w1, int h1) {
        int dx  =  (int)(w1 * (gtime-starttime) / BLOODTIME);
        int dy  =  (int)(h1 * (gtime-starttime) / BLOODTIME);
        int xx = x1+w1/2-dx/2;
        int yy = y1+h1/2-dy/2;
        u.rough(g,xx,yy,xx+dx,yy+dy,Color.red);
     }
  }
}

