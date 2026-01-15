package shark.games;

import java.awt.*;

import shark.*;
import shark.spokenWord.*;

public class NoahsArk extends sharkGame {//SS 03/12/05
  short wordtot;
  static final short MAXWORDS = 6;     // max with splits
  static final short MAXSEGS = 16;
  int MAXSEGSINWORDS = phonicsw ? 24:16;
  static final int MAXWORDW = mover.WIDTH/5;
  static final int MAXALLOWW = mover.WIDTH/8;
  static final int FLOODTIME = 3000;
  static final int FLOATTIME = 3000;
  static final int FLOODTOP = mover.HEIGHT/2;
  static final int ARKWAIT = 2000;
  int mintime;
  wholeword words[],curr;
  String segbits[] = new String[0];
  segment segs[],movingseg;
  short o[],got;
  int maxwidth = mover.WIDTH/6,maxheight= mover.HEIGHT/4;
  sharkImage ark;
  boolean seesegs,gotsegs,ending;
  long arktime;
  long starttime = gtime(),startflood;
  boolean flooding;
  int flooddepth;
  randrange_base arkvert =   new randrange_base(-100,100,500);
  randrange_base arkrot  =   new randrange_base(-100,100,400);
  int arky;
  int oldx,oldy;
  word sayword;
  boolean forceseg;   // force segment display after fit - needed for whiteboard
  long forcesegtime;
  word wd1[];
  int lastsegover = -1,lastsaid = -1;
  long lastsay;
  word saveword[];
  String helpsuff;
  public NoahsArk() {
    errors = false;
    gamescore1 = true;
//    peeps = true;
//    listen= true;
//    peep = true;
    wantspeed=true;
//    gamePanel.setBackground(u.darkcolor());
    wordfont = null;
    getArk();
    setupWords();
    help("help_noah1");
    buildTopPanel();
    gamePanel.clearWholeScreen = true;
//    settimer();
 }
 String helpsuff() {
   if(phonicsw) return  blended?"phb":"phw";
   else if(phonics) return "ph";
   else if(curr!=null && curr.split)  return "a";
   else return "";
 }
 //-------------------------------------------------------------
 void getArk() {
    sharkImage arks[] =  sharkImage.findall("ark_");
    ark = arks[u.rand(arks.length)];
    ark.w = mover.WIDTH*3/8;
    ark.h = mover.HEIGHT/2;
    ark.adjustSize(screenwidth,screenheight);
    addMover(ark,u.rand(mover.WIDTH-ark.w),mover.HEIGHT - ark.h);
 }
 //-------------------------------------------------------------
 void putinark() {
       curr.moveto(ark.x+ark.w/8+u.rand(Math.max(2,ark.w*3/4-curr.w)), ark.y+(curr.curry=ark.h/3+u.rand(Math.max(1,ark.h/2-curr.h))), 800);
       curr.inArk = true;
       gamePanel.showSprite = true;
       gamePanel.puttobottom(curr);
       curr = null;
       gamescore(1);
       if(++got >= wordtot) {
          if(!flooding)
              startflood();
       }
       else {
           if(delayedflip && !completed){
             flip();
           }
          for(short i=0;i<wordtot;++i) {
             if(!words[i].inArk) addMover(words[i],words[i].x,words[i].y);
          }
       }
       gotsegs = false;
//startPR2005-04-18^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       if(Demo_base.isDemo){
         if (Demo_base.demoIsReadyForExit(0)) return;
       }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR

 }
 //-------------------------------------------------------------
 void setupWords() {
    short i,j,k;
    int x1=0,y1=0,xx1,xx2;
    wholeword ww;
    sharkImage animals[] = sharkImage.findall("animal_");
    short alen = (short)animals.length;
    short animalo[] = u.shuffle(u.select(alen,alen));
    word wd[] = sharkStartFrame.mainFrame.wordTree.getvisiblewords();
    if(wd.length<=4 && !phonicsw && !wd[0].split() ) wd = u.augmentlist(wd,false);
    short o[] = u.shuffle(u.select((short)wd.length,(short)wd.length));
    wordtot = MAXWORDS;
    short got=0,segsofar=0;
    short[] want = new short[wordtot];
    wd = clearphonics(wd);
    wd1 = wd;
    for(i=0;i<wd.length && got<wordtot && segsofar < MAXSEGSINWORDS;++i)  {
      if(!phonicsw && wd[o[i]].value.indexOf('/') > 0)  {
         want[got++] = o[i];
         segsofar += wd[o[i]].segtot();
      }
      else if(phonicsw && wd[o[i]].value.indexOf(u.phonicsplit) > 0  && !wd[o[i]].onephoneme)  {
         want[got++] = o[i];
         segsofar += wd[o[i]].phsegtot();
      }
    }
                     // make up with unsplit ones
    for(i=0;i<wd.length && got<wordtot && segsofar < MAXSEGSINWORDS;++i)  {
       if(!phonicsw && wd[o[i]].value.indexOf('/') <= 0)  {want[got++] = o[i]; ++segsofar;}
    }
    wordtot=got;
    o = u.shuffle(u.select(wordtot,wordtot));
    words = new wholeword[wordtot];
    saveword = new word[wordtot];
    for(i=0;i<wordtot;++i) {
        words[i] = ww = new wholeword(saveword[i] = wd[want[o[i]]],
                  (i<alen)? animals[animalo[i]]:(new sharkImage(animals[animalo[i%alen]],false)));
        ww.animal.lefttoright = u.rand(2) == 0;
        words[i].split = (words[i].wd.value.indexOf('/') >= 0);
        if(phonicsw) segbits = wd[want[o[i]]].phaddsegs(segbits,true);
        else segbits = u.simplesplit(segbits,wd[want[o[i]]].vsplit(),true);
        short repeats=0;
        loop1:while (true) {
           x1 = u.rand(mover.WIDTH - ww.w);
           y1 =   mover.HEIGHT/16 + u.rand(mover.HEIGHT*15/16 - ww.h);
           xx1 = Math.max(x1, x1+ww.w/2 - MAXALLOWW/2);
           xx2 = Math.min(x1+ww.w, x1+ww.w/2 + MAXALLOWW/2);
           if(xx1 < ark.x+ ark.w && xx2 > ark.x
                   && y1 < ark.y+ ark.h && y1 + ww.h > ark.y) continue;
           if(++repeats < 500) {
              for(j=0;j<i;++j) {
                 if(xx1 < words[j].x+  words[j].w && xx2 >  words[j].x
                    && y1 <  words[j].y+  words[j].h && y1 + ww.h >  words[j].y)
                    continue loop1;
              }
           }
           break;
        }
        addMover(words[i],x1,y1);
    }
    mintime = wordtot*3000;
    if(phonicsw) mintime *= 3;
    if(segbits.length > wordtot*3/2) mintime *= 2;
 }
 //-------------------------------------------------------------------
 void addsegments() {
    short i,j,repeats;
    if(!gotsegs) assignsegments();
    for(i = 0; i< segs.length; ++i) {
       if(segs[i].wanted && !segs[i].used && segs[i] != movingseg) addMover(segs[i],segs[i].x,segs[i].y);
    }
    seesegs = true;
 }
 //-------------------------------------------------------------------
 void assignsegments() {
    short i,j,repeats;
    short segtot = 0,maxsegs;
    int x1=0,y1=0,w;
    segment ww;
    for(i = 0; i< segs.length; ++i) {
        segs[i].used = segs[i].wanted = false;
        if(segs[i].val.equals(curr.segbits[curr.currseg])) segs[i].wanted = true;
                       //also other segs if no clash
        else for(j=(short)(curr.currseg+1); j<curr.segbits.length;++j) {
            if(segs[i].val.equals(curr.segbits[j])
                && (phonicsw || !u.startswith(curr.val.substring(curr.currpos),segs[i].val))) {
               segs[i].wanted = true;
               ++segtot;
            }
        }
    }
    short o[] = u.shuffle(u.select((short)segs.length,(short)segs.length));
    if(phonics) maxsegs = (short)Math.max(segtot+1,Math.min(3,segtot+2));
    else if(segtot > MAXSEGS - 5) maxsegs = (short)(segtot+5);
    else                     maxsegs = MAXSEGS;
    loop:for(short ii = 0; ii< segs.length && segtot <maxsegs; ++ii) { // select all useable then extras up to max
       i = o[ii];
       if(!segs[i].wanted &&
              (phonicsw || !u.startswith(curr.val.substring(curr.currpos),segs[i].val))) {
          segs[i].wanted = true;
          ++segtot;
       }
    }
    for(i = 0; i< segs.length; ++i) {
        if(!segs[i].wanted) continue;
        ww = segs[i];
        w = ww.w;
        repeats = 0;
        loop1:while (true) {
           if(++repeats<200) {
              x1 = u.rand(mover.WIDTH - ww.w);
              y1 =   u.rand(mover.HEIGHT/2/ww.h) * ww.h;
           }
           else {
             if(repeats == 200) x1=y1=0;
             if((x1 += w) > mover.WIDTH - w) {x1=0;y1+=ww.h;}
           }
           if(x1 < ark.x+ ark.w && x1+w > ark.x
              && y1 < ark.y+ ark.h && y1 + ww.h > ark.y) continue loop1;
           for(j=0;j<i;++j) {
              if(segs[j].wanted && x1 < segs[j].x+  segs[j].w*2 && x1+w*2 >  segs[j].x
                 && y1 <  segs[j].y +  segs[j].h && y1 + ww.h >  segs[j].y)
                    continue loop1;
           }
           if(curr != null) {
              if(x1 < curr.x+  curr.w && x1+w > curr.x
                 && y1 <  curr.y+  curr.h && y1 + ww.h >  curr.y)
                    continue loop1;
           }
           break;
        }
        ww.x = x1;
        ww.y = y1;
        ww.startx = x1;
        ww.starty = y1;
    }
    gotsegs = true;
 }
 //-------------------------------------------------------------------
 void tooclose(segment movingseg) {
      movingseg.moveto(movingseg.startx,movingseg.starty,1000);
 }
 //-------------------------------------------------------------------
 void removesegments() {
    short i,j;
    for(i = 0; i< segs.length; ++i) {
       if(segs[i].wanted && !segs[i].used && movingseg != segs[i]) removeMover(segs[i]);
    }
    seesegs = false;
 }
 //--------------------------------------------------------------------
  public void afterDraw(long t){
     if(arktime != 0) {
        if(gtime < arktime + ARKWAIT) return;
        arktime = 0;
        putinark();
        help("help_noah1");
     }
     if(flooding) {
        if(!ending)
          flooddepth =  Math.min(FLOODTOP, (int)(FLOODTOP*(gtime-startflood)/FLOODTIME));
        ark.y = ark.toy = mover.HEIGHT - flooddepth  + mover.HEIGHT/20 - ark.h + (arky =arkvert.next(gtime)*mover.HEIGHT/6000);
        arky = arky*screenwidth/mover.WIDTH;
        ark.angle = (double)arkrot.next(gtime)/800;
        if(!ending && gtime-startflood > FLOODTIME) {
            String message;
            floodfull();
            if(got>=wordtot) message =  rgame.getParm("gotall");
            else if(got >= Math.min(5,wordtot-1) )
               message =  rgame.getParm("gotgood") + " "+String.valueOf(got) + " " + rgame.getParm("animals");
            else if(got==0)
              message =  rgame.getParm("gotnone") + " " + rgame.getParm("tryagain");
            else if(got==1)
              message =  rgame.getParm("gotonly1");
            else
              message =  rgame.getParm("gotonly") + " " + String.valueOf(got) +  " " + rgame.getParm("animals")+ " " +rgame.getParm("tryagain");
            showmessage(message,
                0,mover.HEIGHT/2, mover.WIDTH,mover.HEIGHT*3/4);
            if(gametot1 < 2) cancelreward = true;
            score(gametot1);
            exitbutton(mover.HEIGHT*3/4);
            ending = true;
        }
        return;
     }
     if(!seesegs && curr != null) {
       if(curr != null)  match(true);
     }
     if(mintime>0 && curr==null && gtime-starttime > (16-speed) * mintime / 4) startflood();
     if(seesegs && segs != null && movingseg == null) {
        gamePanel.showSprite = true;
        int newsegover = -1;
        for(int i=0;i<segs.length;++i)  {
             if(segs[i].mouseOver)  {
                newsegover = i;
                if(phonics && i != lastsegover && (gtime-lastsay > 2000||lastsaid != i && !segs[i].silent)) {
                   lastsay = gtime;
                   lastsaid = i;
                   if(segs[i].val.equals(curr.segbits[curr.currseg]))  {
                     spokenWord.flushspeaker(true);
                     if (phonicsw) {
                       spokenWord.sayPhonicsBit(curr.wd, curr.currseg);
                     }
                     else spokenWord.sayPhonicsWord(curr.wd,500,false,false,!singlesound);
                   }
                   else if(!spokenWord.sayPhonicsBit(segs[i].val, new word[]{curr.wd}))
                     spokenWord.sayPhonicsBit(segs[i].val, saveword);
               }
               gamePanel.showSprite = false;
               break;
             }
        }
        lastsegover = newsegover;
     }
     if(curr == null
        ||movingseg != null
            && movingseg.x < curr.x + curr.w &&  movingseg.x + movingseg.w > curr.x
            && movingseg.y < curr.y + curr.h &&  movingseg.y + movingseg.h > curr.y
        || (!forceseg || gtime<forcesegtime) && curr != null && curr.mouseOver) {
           if(seesegs) {
             removesegments();
           }
           if(curr!=null && movingseg==null) gamePanel.showSprite = false;
     }
     else if(!seesegs) addsegments();
     if(curr!=null && forceseg && !curr.mouseOver) forceseg = false;
  }
  //---------------------------------------------------------------
  void match(boolean exact) {
     int i,j;
     int dx = metrics.charWidth('a')/3 ;
     int dy = metrics.getHeight()/4 ;
     if(curr == null || movingseg == null) return;
     int x = curr.wordx + metrics.stringWidth(curr.val.substring(0,curr.currpos));
     int y = curr.wordy;
     int sx = movingseg.x * screenwidth / mover.WIDTH;
     int sy = (movingseg.y+movingseg.h/2) * screenheight / mover.HEIGHT - metrics.getHeight()/2;
     if(!movingseg.val.equals(curr.segbits[curr.currseg])
          || exact && (Math.abs(y-sy) > dy ||  Math.abs(x-sx) > dx)) { return; }
     noise.plop();
     if(phonicsw) spokenWord.sayPhonicsSyl(curr.wd,curr.wd.fullval(curr.currseg+1));    // rb 25/1/08
     removeMover(movingseg);
     gamePanel.finishwith(movingseg);
     movingseg.used = true;
     movingseg.tox = movingseg.x = oldx;
     movingseg.toy = movingseg.y = oldy;
     movingseg.dontgrabmouse = false;
     movingseg.mouseOver = false;
     if(phonicsw) {  // if wanted again, reinstate
       for(i=curr.currseg+1; i< curr.segbits.length;++i) {
          if(curr.segbits[i].equals(movingseg.val)) {
            movingseg.used = false;
            break;
          }
       }
     }
     movingseg = null;
     gamePanel.showSprite = true;
     i = curr.currpos;
     curr.currpos += curr.segbits[curr.currseg].length();
     if(phonics && curr.wd.ismagicsyl(curr.currseg+2)) {
       curr.marked[i] = true;
       curr.marked[i+2] = true;
       curr.currpos-=2;
     }
     else {
       if (phonics && curr.wd.ismagicsyl(curr.currseg + 1)) {
         ++curr.currpos;
       }
       for (; i < curr.currpos; ++i)
         curr.marked[i] = true;
     }
     ++curr.currseg;
     if(curr.currseg >= curr.segbits.length)  {
        sayword = curr.wd;
        help(null);
        if(!phonics || phonicsw) new spokenWord.whenfree(600) {
          public void action() {
           if(phonicsw) spokenWord.sayPhonicsWhole(curr.wd);
           else  sayword.say();
          }
        };
        arktime = gtime;
     }
     else    {
      if(!phonics) gotsegs = false;
      help("help_noah2b"+helpsuff());
      forceseg = true;
      forcesegtime = gtime+1500;   // force seg display after 1.5 sec
      }
   }
 //---------------------------------------------------------------
 public boolean click(int x1,  int y1)  {
   short i,j;
   String s;
   if(curr != null && !curr.mouseOver) forceseg = false;
   if(curr != null && curr.mouseOver &&  movingseg != null) {
     movingseg.my = -movingseg.h;
     movingseg.mx = -movingseg.w;
   }
   if(arktime != 0) return true;
   if(ending) return false;
   if(flooding) return true;
   if(movingseg != null) {
      if(!seesegs) {
        match(false);
      }
      else {        // drop it
             gamePanel.showSprite = true;
             gamePanel.drop(movingseg);
             movingseg.dontgrabmouse = false;
             boolean moved = false;
             for(i = 0;i<segs.length;++i) {
               if (segs[i] != movingseg && segs[i].wanted && segs[i].mouseOver) {
                 movingseg.moveto(movingseg.startx, movingseg.starty, 0);
                 moved = true;
                 break;
               }
             }
             if(!moved) tooclose(movingseg);
             movingseg = null;
             String helpstring = (curr.split?((curr.currseg==0)?"help_noah2a":"help_noah2b"):"help_noah2")+helpsuff();
             if(specialprivateon){
               if(helpstring.equals("help_noah2"))
                   helpstring = helpstring.concat("def");
             }
             help(helpstring);
      }
   }
   else if(seesegs)  {
      for(i = 0;i<segs.length;++i) {
         if(segs[i].wanted && segs[i].mouseOver) {
            gamePanel.showSprite = false;
                 // switch segs if same phonic but written differently
            if(phonics && !singlesound && !segs[i].val.equals(curr.segbits[curr.currseg])
                  && curr.wd.phonics()[curr.currseg].equals(spokenWord.phonicname(segs[i].val,saveword))) {
               for(j=0;j<segs.length;++j) {
                 if (segs[j].val.equals(curr.segbits[curr.currseg])) {
                   int sx = segs[j].startx;
                   int sy = segs[j].starty;
                   segs[j].x = segs[j].tox = segs[j].startx = segs[i].startx;
                   segs[j].y = segs[j].toy = segs[j].starty = segs[i].starty;
                   segs[i].x = segs[i].tox = segs[i].startx = sx;
                   segs[i].y = segs[i].toy = segs[i].starty = sy;
                   i=j;
                   break;
                 }
               }
            }
            movingseg = segs[i];
            oldx = movingseg.x;
            oldy = movingseg.y;
            gamePanel.attachToMouse(segs[i]);
            segs[i].dontgrabmouse = true;
            help("help_noah4"+helpsuff());
            break;
         }
      }
   }
   else if(curr == null) {
     if(segs == null) {
        for(i=0;i<wordtot;++i) {
            wordfont  = sizeFont(wordfont,words[i].val,words[i].w,mover.HEIGHT/12);
        }
        segs = new segment[segbits.length];
        for(j=0;j<segbits.length;++j) {
          segs[j] = new segment(segbits[j]);
        }
     }
     for(i = 0;i<words.length;++i) {
         if(words[i].mouseOver && !words[i].inArk) {
            curr = words[i];
            curr.mx = -x1+curr.x;
            curr.my = -y1+curr.y;
            String helpstring = "help_noah2"+helpsuff();
            if(specialprivateon){
               if(helpstring.equals("help_noah2"))
                   helpstring = helpstring.concat("def");
            }
            help(helpstring);
            for(j=0; j<wordtot;++j) {
              if(i!=j && !words[j].inArk) removeMover(words[j]);
            }
            forceseg = true;
            forcesegtime = gtime+3000;   // force seg display after 3 sec

           break;
         }
      }
   }
   else {   // click to show whole word on whiteboard
     forceseg = true;
     forcesegtime = gtime+2000;   // force seg display after 2 sec

   }
   return true;
 }
 //-------------------------------------------------------------------
 void startflood() {
     short i;
     startflood = gtime;
     if(seesegs) removesegments();
     help(null);
     flooding=true;
     for(i=0;i<wordtot;++i) {
         if(!words[i].inArk)   {
             if(words[i].y < FLOODTOP)
                 words[i].toy = words[i].y = mover.HEIGHT*2/3 + u.rand(Math.max(1,mover.HEIGHT/3-words[i].h));
             if(curr  != null && words[i] != curr)
                 addMover(words[i],words[i].x,words[i].y);
         }
     }
     curr = null;
//     ark.moveto(ark.x,FLOODTOP + ark.h/10 - ark.h,FLOODTIME);
     new flood();
     gamePanel.puttobottom(ark);
     for(i=0;i<wordtot;++i) {
        if(words[i].inArk) gamePanel.puttobottom(words[i]);
     }
 }
 //-------------------------------------------------------------------
 void floodfull() {
     for(short i=0;i<wordtot;++i) {
         if(!words[i].inArk) {
             words[i].temp = true;
             words[i].moveto((u.rand(2)==0)?mover.WIDTH:(-words[i].w) ,
             FLOODTOP -words[i].h/3+ u.rand(words[i].h/3),1200+u.rand(2000));
         }
     }
 }
   //--------------------------------------------------------------
 class segment extends mover {
    String val;
    short vlen;
    Color color=Color.black;
    boolean used,wanted;
    short from[];
    int startx,starty;
    spokenWord sw;
    sharkImage ear = new sharkImage(findsound.ear,false);
    boolean silent;

    public segment(String s) {
       super(true);
       val = s;
       vlen = (short)val.length();
       w = metrics.stringWidth(s)*mover.WIDTH/screenwidth;
       h = metrics.getHeight()*mover.HEIGHT/screenheight;
       if(phonics) {
         w = Math.max(w,mover.WIDTH/20);
         h = Math.max(h,mover.HEIGHT/8);
         silent = spokenWord.isSilent(val,saveword);
       }
       this.mouseSensitive = true;
       mustSave = true;
    }
    public void paint(Graphics g,int x, int y, int w, int h) {
        if(shark.macOS) ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
      g.setFont(wordfont);
      if(gamePanel.movesWithMouse(this) || mouseOver && movingseg == null)
         g.setColor(white());
      else g.setColor(color);
      if(phonics && !used && seesegs) {
          if(mouseOver && movingseg == null) {
            g.setColor(background.darker());
            g.fillRect(x,y,w,h);
          }
          if(silent) {
            g.setColor(ear.getcolor(0));
            g.drawString(val, x + w/2 - metrics.stringWidth(val)/2, y + h/2 - metrics.getHeight()/2 + metrics.getMaxAscent());
            if(!gamePanel.movesWithMouse(this)) g.drawRect(x,y,w,h);
          }
          else ear.paint(g,x,y,w,h);
          return;
      }
      g.drawString(val, x ,
                y + h/2 - metrics.getHeight()/2 + metrics.getMaxAscent());
      int i;
//      if((i=val.indexOf(' ')) >= 0) {
//         g.drawRect(x + metrics.stringWidth(val.substring(0,i)), y,
//                               metrics.charWidth(' '), metrics.getMaxAscent());
//      }
    }
  }
 //--------------------------------------------------------------
 class wholeword extends mover {
    String val;
    short vlen;
    boolean marked[],split;
    word wd;
    Color color=Color.black;
    Color boxcolor;
    sharkImage animal;
    boolean inArk;
    int wordx,wordy,curry;
    randrange_base rr = new randrange_base(-100,+100,400);
    String segbits[];
    short currpos,currseg;
//startPR2004-09-15^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    boolean isSpecialVowel[];
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR

    public wholeword(word s, sharkImage animal1) {
       super(false);
       val = s.v();
//startPR2004-09-15^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       isSpecialVowel = new boolean[val.length()];
       isSpecialVowel = u.findSpecialVowels(val);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       wd = s;
       marked = new boolean[vlen = (short)val.length()];
       animal = animal1;
       animal.w = maxwidth;
       animal.h = maxheight;
       animal.adjustSize(screenwidth,screenheight);
       w = Math.max(MAXWORDW,animal.w);
       h = animal.h;
       boxcolor = animal.getcolor(0);
       if(u.tooclose(boxcolor,Color.white) || u.tooclose2(boxcolor,Color.black)) {
          boxcolor = Color.green;
       }
       if(phonicsw) segbits =  wd.phsegs();
       else segbits = u.simplesplit(null,wd.vsplit(),false);
    }
    public void changeImage(long t) {
       long lasttime=0;
       if(inArk) {
         if(flooding && !ending ) {
            y = toy = fromy = ark.y + curry;
         }
         if(x == tox && y == toy) {
            moveto(ark.x+ark.w/8+u.rand(Math.max(2,ark.w*3/4-w)), ark.y+ark.h/3+u.rand(Math.max(1,ark.h/2-h)), 800+u.rand(800));
            animal.lefttoright = tox<fromx;
         }
         curry = y - ark.y;
       }
       if(!inArk && flooding) {
          if(toy > mover.HEIGHT-flooddepth-h)  {
             fromy = y = toy = Math.min(toy, mover.HEIGHT-flooddepth-h/3);
             animal.angle = Math.PI*rr.next(t)/100;
          }
       }
       lasttime = t;
    }
    public void paint(Graphics g,int x, int y, int w, int h) {
       short i;
       int x1,y1;
       animal.paint(g, x, y+(inArk?arky:0), w, h);
       if(shark.macOS) ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
       if(metrics != null && !seesegs && !inArk &&  curr == this) {
          wordx = x1 =(x + w/2 - metrics.stringWidth(val)/2);
          wordy = y1 =(y + h/2 - metrics.getHeight()/2 );
          int xx1 =  Math.max(x,x1 - metrics.charWidth('a'));
          int xx2 =  x + w/2 + (x+w/2 - xx1);
          int yy1 = y1 - metrics.getHeight()/8;
          int yy2 = y1 + metrics.getHeight()*9/8;
          g.setColor(boxcolor);
          g.fillRect(xx1,yy1,xx2-xx1,yy2-yy1);
          g.setColor(Color.black);
          g.drawRect(xx1,yy1,xx2-xx1,yy2-yy1);
          g.setColor(color);
          g.setFont(wordfont);
          for(i=0,x1=wordx;i<vlen;++i) {
             if((rgame.options & word.VOWELS) != 0
                 && !(i==0 && val.charAt(i)=='y')
//startPR2004-09-15^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                 && (u.vowelsy.indexOf(val.charAt(i)) >= 0 ||isSpecialVowel[i])){
//                 && u.vowelsy.indexOf(val.charAt(i)) >= 0 )
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                g.setColor(marked[i] ? Color.yellow : Color.red);
              }
              else g.setColor(marked[i]?Color.white:Color.black);
              g.drawString(val.substring(i,i+1), x1, wordy  + metrics.getMaxAscent());
              x1+=metrics.charWidth(val.charAt(i));
          }
       }
    }
  }
  //------------------------------------------------------------
  class flood extends mover {
     Color  color = u.watercolor();
     randrange_base amp[] = new randrange_base[3];
     randrange_base len[] = new randrange_base[3];
     randrange_base speed[] = new randrange_base[3];
     double aa[] = new double[3];
     flood() {
        super(false);
        w = mover.WIDTH;
        h = mover.HEIGHT/2-mover.HEIGHT/10;
        addMover(this,0,mover.HEIGHT-h);
        gamePanel.puttobottom(this);
        for(short i=0;i<amp.length;++i) {
           amp[i] = new randrange_base(screenheight/2,screenheight,1000);
           len[i] = new randrange_base(64,300,100);
           if(u.rand(2)==0) speed[i] = new randrange_base(100,200,20);
           else  speed[i] = new randrange_base(-200,-100,20);
        }
     }
     public void paint(Graphics g,int x1, int y1, int w1, int h1) {
        int depth2 =  Math.min(screenheight/2, (int)(screenheight/2*(gtime-startflood)/FLOODTIME));
        Polygon p = new Polygon();
        double amp1[] = new double[3];
        double len1[] = new double[3];
        int yy,i,j;
        p.addPoint(screenwidth,screenheight);
        p.addPoint(0,screenheight);
        for(j=0;j<amp.length;++j) {
           aa[j] += (double)speed[j].next(gtime)/100000*(gtime-startflood)/120;
           amp1[j] = (double)amp[j].next(gtime)/60;
           len1[j] = (double)len[j].next(gtime)/4/screenwidth;
        }
        for(i=0;i<=screenwidth;++i) {
           yy=screenheight-depth2;
           for(j=0;j<amp.length;++j)
              yy += (int)(amp1[j] *Math.sin(aa[j] + i*len1[j]));
           p.addPoint(i,yy);
        }
        g.setColor(color);
        g.fillPolygon(p);
     }
  }
}
