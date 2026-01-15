package shark.games;

import java.awt.*;

import shark.*;
import shark.sharkGame.*;

public class trains extends sharkGame {//SS 03/12/05
  short wordtot;
  static final short MAXSEGS = 16;
  static final short MAXWORDS = 8;
  int MAXSEGSINWORDS = phonicsw ? 24:16;
  static final int MAXWORDW = mover.WIDTH/5;
  static final int MAXALLOWW = mover.WIDTH/8;
  static final int DONEWAIT = 1000;
  static final int FADETIME = 8000;
  int mintime;
  wholeword words[],curr;
  short totsplits;
  int wordnext;
  String segbits[] = new String[0];
  segment segs[],movingseg;
  short o[],got;
  int maxwidth = mover.WIDTH/6,maxheight= mover.HEIGHT/4;
  boolean seesegs,gotsegs,ending;
  int oldx,oldy;
  byte jtot  = 8,jword;
  junction j[] = new junction[jtot];
  int jh1 = screenheight/18;
  int jh2 = screenheight/10;
  int jw1 = screenwidth/8;
  int jw2 = jh2;
  int logjw1,logjw2;
  byte sectot;
  byte secmax = (byte) (jtot + jtot/2);
  section secs[] = new section[secmax];
  int BORDER = screenwidth/32;
  int BORDERW = screenwidth/16;
  int GAP = screenwidth/12;
  int a1,b1,a2,b2 ;
  int trad = screenwidth/80;
  int tradsq=trad*trad;
  int railgap = trad/3;
  idlecar idlecars[] = new idlecar[secmax];
  byte idlecartot;
  Color carcolor = Color.green;
  boolean stopped, innewsection, starting;
  long completeat;
  long donetime;
  int oldwidth;
  boolean forceseg;  // force segment display after fit - needed for whiteboard
  long forcesegtime;
  long forcewholetime;
  word wd1[];
  int lastsegover = -1,lastsaid = -1;
  long lastsay;
  word sayword;
  word saveword[];
  starter startr;

  public trains() {
    errors = false;
    gamescore1 = true;
    wantpause=true;
//    peeps = true;
//    listen= true;
//    peep = true;
    wantspeed=true;
//    gamePanel.setBackground(u.darkcolor());
    wordfont = null;
    help("help_trains1");
    setupWords();
    buildTopPanel();
    addMover(new track(),0,0);
    addMover(new train(),0,0);
    gamePanel.clearWholeScreen = true;
    if(sharkStartFrame.wanthelp) {
      startr = new starter();
    }
    oldwidth = screenwidth;
  //    settimer();
 }
 String helpsuff() {
   if(phonicsw) return  blended?"phb":"phw";
   else if(phonics) return "ph";
   else if(curr!=null && curr.split)  return "a";
   else return "";
 }
//-------------------------------------------------------------
 
 void setupWords() {
    short i,j,k;
    int x1=0,y1=0,xx1,xx2;
    int maxlen = 0;
    wholeword ww;
    word wd[] = sharkStartFrame.mainFrame.wordTree.getvisiblewords();
    if(wd.length<=4 && !phonicsw && !wd[0].split() ) wd = u.augmentlist(wd,false);
    wordtot = MAXWORDS;
    short o[] = u.shuffle(u.select((short)wd.length,(short)wd.length));
    short got=0,segsofar = 0;
    short[] want = new short[wordtot];
    wd = clearphonics(wd);
    for(i=0;i<wd.length && got<wordtot && segsofar < MAXSEGSINWORDS;++i)  {
      if(!phonicsw && wd[o[i]].value.indexOf('/') > 0)  {
         want[got++] = o[i];
         segsofar += wd[o[i]].segtot();
      }
      else if(phonicsw && wd[o[i]].value.indexOf(u.phonicsplit) > 0 && !wd[o[i]].onephoneme)  {
         want[got++] = o[i];
         segsofar += wd[o[i]].phsegtot();
      }
    }
                     // make up with unsplit ones
    for(i=0;i<wd.length && got<wordtot;++i)  {
       if(wd[o[i]].value.indexOf('/') <= 0)  want[got++] = o[i];
    }
    wordtot=got;
    word wd2[] = new word[wordtot];
    for(i=0;i<wordtot;++i)  {wd2[i] = wd[want[i]];}
    wd = wd2;
    wd1 = wd;
    o = u.shuffle(u.select(wordtot,wordtot));
    got=0;
    totsplits=0;
    for(i=0;i<wd.length ;++i)  {
           totsplits += u.splitString(wd[o[i]].value,'/').length;
    }
    o = u.shuffle(u.select(wordtot,wordtot));
    words = new wholeword[wordtot];
    saveword = new word[wordtot];
    for(i=0;i<wordtot;++i) {
        words[i] = ww = new wholeword(saveword[i] = wd[o[i]]);
        words[i].split = (words[i].wd.value.indexOf('/') >= 0);
        if(phonicsw) segbits = wd[o[i]].phaddsegs(segbits,true);
        else segbits = u.simplesplit(segbits,wd[o[i]].vsplit(),true);
        maxlen = Math.max(maxlen,wd[o[i]].v().length());
    }
    if(maxlen > 6) jw1 = screenwidth/5;
    logjw1 = jw1*mover.WIDTH/screenwidth;
    logjw2 = jw2*mover.WIDTH/screenwidth;
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
    int x1=0,y1=0,w,h;
    segment ww;
    long a1z = (a1+jw1/2)*mover.WIDTH/screenwidth;
    a1z = a1z*a1z;
    long b1z = (b1+jh1/2)*mover.HEIGHT/screenheight;
    b1z=b1z*b1z;
    long a2z = (a2-jw2/2)*mover.WIDTH/screenwidth;
    a2z = a2z*a2z;
    long b2z = (b2-jh2/2)*mover.HEIGHT/screenheight;
    b2z=b2z*b2z;
    int midx = mover.WIDTH/2;
    int midy = mover.HEIGHT/2;
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
       if(!segs[i].wanted
             && (phonicsw || !u.startswith(curr.val.substring(curr.currpos),segs[i].val))) {
          segs[i].wanted = true;
          ++segtot;
       }
    }
    for(i = 0; i< segs.length; ++i) {
        if(!segs[i].wanted) continue;
        ww = segs[i];
        w = ww.w;
        h = ww.h;
        repeats = 0;
        loop1:while (true) {
           if(++repeats<200) {
              x1 = u.rand(mover.WIDTH - ww.w);
              y1 =   u.rand(mover.HEIGHT/ww.h) * ww.h;
           }
           else {
             if(repeats == 200) x1=y1=0;
             if((x1 += w) > mover.WIDTH - w) {x1=0;y1+=ww.h;}
           }
            if( (repeats<100 || (x1-midx)*(x1-midx)*b1z + (y1-midy)*(y1-midy)*a1z < b1z*a1z)
              &&  (x1-midx)*(x1-midx)*b2z + (y1-midy)*(y1-midy)*a2z > b2z*a2z
              || (repeats<100 || (x1+w-midx)*(x1+w-midx)*b1z + (y1+h-midy)*(y1+-midy)*a1z < b1z*a1z)
              &&  (x1+w-midx)*(x1+w-midx)*b2z + (y1+h-midy)*(y1+h-midy)*a2z > b2z*a2z)
                    continue loop1;
           for(j=0;j<i;++j) {
              if(segs[j].wanted && x1 < segs[j].x+  segs[j].w*2 && x1+w*2 >  segs[j].x
                 && y1 <  segs[j].y +  segs[j].h && y1 + ww.h >  segs[j].y)
                    continue loop1;
           }
           if(overjunction(x1*screenwidth/mover.WIDTH, y1*screenheight/mover.HEIGHT,
                ww.w*screenwidth/mover.WIDTH,ww.h*screenheight/mover.HEIGHT)) continue loop1;
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
 boolean overjunction(int x, int y, int w, int h) {
    for(short i=0;i<jtot;++i) {
      if(x <= j[i].x1+j[i].w1 && x+h >= j[i].x1
         &&  y <= j[i].y1+j[i].h1 && y+h >= j[i].y1) return true;
    }
    return false;
 }
 //-------------------------------------------------------------------
 void tooclose(segment ss) {
    ss.moveto(ss.startx,ss.starty,1000);
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
     if(oldwidth != screenwidth) {
       int i;
       for(i=0;i<j.length;++i) {
          j[i].x1 = j[i].logx1 * screenwidth/mover.WIDTH;
          j[i].x2 = j[i].logx2 * screenwidth/mover.WIDTH;
          j[i].w1 = j[i].logw1 * screenwidth/mover.WIDTH;
          j[i].w2 = j[i].logw2 * screenwidth/mover.WIDTH;
       }
       for(i=0;i<secs.length;++i) {
         secs[i].p = u.multx(secs[i].logp,screenwidth,mover.WIDTH);
         secs[i].p1 = u.multx(secs[i].logp1,screenwidth,mover.WIDTH);
         secs[i].p2 = u.multx(secs[i].logp2,screenwidth,mover.WIDTH);
       }
       jw1 = logjw1*screenwidth/mover.WIDTH;
       jw2 = logjw2*screenwidth/mover.WIDTH;
       wordfont = null;
       for(i=0;i<wordtot;++i) {
            wordfont  = adjustFont(wordfont,words[i].val,jw1,jh1);
       }
       metrics = getFontMetrics(wordfont);
       oldwidth = screenwidth;
       gamePanel.copyall = true;
     }
     if(donetime != 0) {
        if(gtime < donetime + DONEWAIT) return;
        donetime = 0;
        switchpoints();
     }
     if(!seesegs && curr != null) {
        if(gamePanel.movesWithMouse(curr)) {
          matchwhole(true);
        }
        else
            match(true);
     }
     if(gtime < forcewholetime
        || curr == null || gamePanel.movesWithMouse(curr)
        ||movingseg != null
            && movingseg.x < curr.x + curr.w &&  movingseg.x + movingseg.w > curr.x
            && movingseg.y < curr.y + curr.h &&  movingseg.y + movingseg.h > curr.y
        ||  (!forceseg || gtime<forcesegtime) && curr != null && j[jword].isover(gamePanel.mousexs,gamePanel.mouseys)) {
           if(seesegs) removesegments();
     }
     else if(!seesegs) {
        addsegments();
     }
     gamePanel.showSprite =   movingseg == null
                              && (curr == null || !gamePanel.movesWithMouse(curr))
                              &&   (completed
                                   || curr == null
                                   || !j[jword].isover(gamePanel.mousexs,gamePanel.mouseys));
     if(seesegs && segs != null && movingseg == null) {
        gamePanel.showSprite = true;
        int newsegover = -1;
        for(int i=0;i<segs.length;++i)  {
             if(segs[i].mouseOver)  {
               newsegover = i;
                if(phonics && i != lastsegover && (gtime-lastsay > 2000||lastsaid != i)) {
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
     if(curr!=null && forceseg && !j[jword].isover(gamePanel.mousexs,gamePanel.mouseys))
        forceseg = false;
  }
  //---------------------------------------------------------------
  void matchwhole(boolean exact) {
    if(curr != null && gamePanel.movesWithMouse(curr)) {
     short i,j;
     int dx = metrics.charWidth('a')/3 ;
     int dy = metrics.getHeight()/4 ;
     int x = curr.wordx + metrics.stringWidth(curr.val.substring(0,curr.currpos));
     int y = curr.wordy;
     int sx = curr.x * screenwidth / mover.WIDTH;
     int sy = curr.y * screenheight / mover.HEIGHT;
     if(!curr.val.equals(curr.segbits[curr.currseg])
          || exact && ( Math.abs(y-sy) > dy ||  Math.abs(x-sx) > dx)) { return; }
      noise.plop();
      if(phonicsw) spokenWord.sayPhonicsSyl(curr.wd,curr.wd.fullval(curr.currseg+1));
//      curr.wd.say();
      donetime = gtime;
    }
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
     spokenWord.sayPhonicsSyl(curr.wd,curr.wd.fullval(curr.currseg+1));
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
     if(curr.currseg >= curr.segbits.length) {
       donetime = gtime;
       if(!phonics || phonicsw) {
         sayword = curr.wd;
         new spokenWord.whenfree(600) {
           public void action() {
             if (phonicsw)
               spokenWord.sayPhonicsWhole(sayword);
             else
               sayword.say();
           }
         };
       }
     }
     else   {
       if(!phonics) gotsegs = false;
       help("help_trains2b"+helpsuff());
       forceseg = true;
       forcesegtime = gtime+1500;   // force seg display after 1.5 sec
     }
   }
   //------------------------------------------------------------
   void switchpoints() {
     stopped = false;
     j[jword].switchover();
     j[jword].gotword = false;
     curr.currseg = 0;
     curr = null;
     help("help_trains1");
     gotsegs = false;
     gamescore(1);
   }
 //---------------------------------------------------------------
 public boolean click(int x1,  int y1)  {
   short i,k;
   String s;
   if(startr!=null && gamePanel.isMover(startr)){
       startr.mouseClicked(x1, y1);
       return false;
   }
   if(curr != null) {
      if(!j[jword].isover(gamePanel.mousexs,gamePanel.mouseys)) forceseg = false;
      else if (movingseg != null) {
        movingseg.my = -movingseg.h;
        movingseg.mx = -movingseg.w;
      }
   }
   if(starting) return false;
   if(donetime != 0) return true;
   if(completed) return false;
   if(movingseg != null) {
      if(!seesegs) {match(false);}
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
             String helpstring = (curr.split?((curr.currseg==0)?"help_trains2a":"help_trains2b"):"help_trains2")+helpsuff();
             if(specialprivateon){
                  if(helpstring.equals("help_trains2"))
                      helpstring = helpstring.concat("def");
             }
             help(helpstring);

      }
   }
   else if(curr != null && gamePanel.movesWithMouse(curr)) {
     if(!seesegs) matchwhole(false);
  }
   else if(seesegs)  {
      for(i = 0;i<segs.length;++i) {
         if(segs[i].wanted && segs[i].mouseOver) {
            gamePanel.showSprite = false;
            // switch segs if same phonic but written differently
         if(phonics && !singlesound && !segs[i].val.equals(curr.segbits[curr.currseg])
             && curr.wd.phonics()[curr.currseg].equals(spokenWord.phonicname(segs[i].val,saveword))) {
              for(k=0;k<segs.length;++k) {
                if (segs[k].val.equals(curr.segbits[curr.currseg])) {
                  int sx = segs[k].startx;
                  int sy = segs[k].starty;
                  segs[k].x = segs[k].tox = segs[k].startx = segs[i].startx;
                  segs[k].y = segs[k].toy = segs[k].starty = segs[i].starty;
                  segs[i].x = segs[i].tox = segs[i].startx = sx;
                  segs[i].y = segs[i].toy = segs[i].starty = sy;
                  i = k;
                  break;
                }
              }
            }
            movingseg = segs[i];
            oldx = movingseg.x;
            oldy = movingseg.y;
            gamePanel.attachToMouse(segs[i]);
            segs[i].dontgrabmouse = true;
            help("help_trains4"+helpsuff());
            break;
         }
      }
   }
   else if(curr == null) {
     for(i = 0;i<jtot;++i) {
         if(j[i].isover(gamePanel.mousexs, gamePanel.mouseys)) {
            j[i].setcurr((byte)i);
            String helpstring = "help_trains2"+helpsuff();
             if(specialprivateon){
                  if(helpstring.equals("help_trains2"))
                      helpstring = helpstring.concat("def");
             }
            help(helpstring);
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
 //-----------------------------------------------------------------
 Color fade(Color c) {
    if(!completed)  return c;
    if(gtime-completeat > FADETIME) return this.background;
    return u.mix(c,background,(int)(gtime-completeat),FADETIME);
 }
 //-----------------------------------------------------------------
 Color fade2(Color c) {
    if(!completed)  return c;
    if(gtime-completeat > FADETIME*2) return this.background;
    return u.mix(c,background,(int)(gtime-completeat),FADETIME*2);
 }
   //--------------------------------------------------------------
 class segment extends mover {
    String val;
    short vlen;
    Color color=Color.black;
    boolean used,wanted;
    short from[];
    int startx,starty;
    sharkImage ear = new sharkImage(findsound.ear,false);
    boolean silent;

    public segment(String s) {
       super(true);
       val = s;
       vlen = (short)val.length();
       w = metrics.stringWidth(s)*mover.WIDTH/screenwidth;
       h = metrics.getHeight()*mover.HEIGHT/screenheight;
       if(phonics ||phonicsw) {
         w = Math.max(w,mover.WIDTH/20);
         h = Math.max(h,mover.HEIGHT/8);
         silent = spokenWord.isSilent(val,saveword);
       }
       this.mouseSensitive = true;
       mustSave = true;
    }
    public void paint(Graphics g,int x, int y, int w1, int h1) {
        if(shark.macOS) ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
       g.setFont(wordfont);
       if(gamePanel.movesWithMouse(this) || mouseOver && movingseg == null)
          g.setColor(white());
       else g.setColor(color);
       if(phonics && !used && seesegs) {
         w = Math.max(w,mover.WIDTH/20);
         h = Math.max(h,mover.HEIGHT/8);
         if(mouseOver && movingseg == null) {
           g.setColor(background.darker());
           g.fillRect(x,y,w1,h1);
         }
         if(silent) {
            mx = 0;
            my = 0;
            g.setColor(ear.getcolor(0));
            g.drawString(val, x + w1/2 - metrics.stringWidth(val)/2, y + h1/2 - metrics.getHeight()/2 + metrics.getMaxAscent());
            if(!gamePanel.movesWithMouse(this)) g.drawRect(x,y,w1,h1);
          }
          else
            ear.paint(g,x,y,w1,h1);
         return;
       }
       w = metrics.stringWidth(val)*mover.WIDTH/screenwidth;
       h = metrics.getHeight()*mover.HEIGHT/screenheight;
       g.drawString(val, x ,
                y + h1/2 - metrics.getHeight()/2 + metrics.getMaxAscent());
       int i;
//       if((i=val.indexOf(' ')) >= 0)
//          g.drawRect(x + metrics.stringWidth(val.substring(0,i)), y,
//                       metrics.charWidth(' '), metrics.getMaxAscent());
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
    int wordx,wordy,curry;
    String segbits[];
    short currpos,currseg;
//startPR2004-09-15^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    boolean isSpecialVowel[];
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR

    public wholeword(word s) {
       super(false);
       val = s.v();
//startPR2004-09-15^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       isSpecialVowel = new boolean[val.length()];
       isSpecialVowel = u.findSpecialVowels(val);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       wd = s;
       marked = new boolean[vlen = (short)val.length()];
       w = jw1*mover.WIDTH/screenwidth;
       h = jh1*mover.HEIGHT/screenheight;
       if(phonicsw) segbits = wd.phsegs();
       else segbits = u.simplesplit(null,wd.vsplit(),false);
    }
    void clearmarked() {
       for(short i=0;i<marked.length;++i) marked[i]=false;
    }
    public void paint(Graphics g,int x, int y, int w, int h) {
       short i;
       int x1,y1;
       if(shark.macOS) ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
       if(metrics != null && !seesegs &&  curr == this) {
          wordx = x1 =(x + w/2 - metrics.stringWidth(val)/2);
          wordy = y1 =(y + h/2 - metrics.getHeight()/2 );
          int xx1 =  Math.max(x,x1 - metrics.charWidth('a'));
          int xx2 =  x + w/2 + (x+w/2 - xx1);
          int yy1 = y1 - metrics.getHeight()/8;
          int yy2 = y1 + metrics.getHeight()*9/8;
          g.setColor(color);
          g.setFont(wordfont);
          for(i=0,x1=wordx;i<vlen;++i) {
             if((rgame.options & word.VOWELS) != 0
                 && !(i==0 && val.charAt(i)=='y')
//startPR2004-09-15^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                  && (u.vowelsy.indexOf(val.charAt(i)) >= 0 ||isSpecialVowel[i])){
     //                 && u.vowelsy.indexOf(val.charAt(i)) >= 0 )
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                 g.setColor(marked[i] ? yellow() : Color.red);
               }
             else g.setColor(marked[i]?white():Color.black);
             g.drawString(val.substring(i,i+1), x1, wordy  + metrics.getMaxAscent());
             x1+=metrics.charWidth(val.charAt(i));
          }
       }
    }
  }
  //----------------------------------------------------------------------
  class track extends mover {
     double angle;
     track() {
         super(false);
         angle =u.rand(Math.PI*2);

         boolean outside = true, switchback=true;
         double inc = Math.PI*2/jtot;
         int xmid = screenwidth/2, ymid = screenheight/2;
         int a1z = a1*31/32;
         int b1z = b1*31/32;
         byte i;
         a1 =screenwidth/2 - jw1/2;
         b1=screenheight/2 - BORDER;
         a2=a1-jw1/2-railgap;
         b2 = b1-GAP;
         for(i=0; i<jtot; ++i,angle+=inc) {
            if(outside) {
               if(switchback) {
                  j[i] = new junction((byte)((secmax+sectot-1)%secmax),(byte)((sectot)%secmax),(byte)((sectot+1)%secmax),xmid+(int)(a1*Math.cos(angle)),ymid+(int)(b1*Math.sin(angle)));
                  new section(angle, angle+inc*3, a1,b1,i,(byte)((i+3)%jtot),9);
                  new section(angle, a1,b1, angle+inc, a2,b2,i,(byte)((i+1)%jtot),4);
                  outside = false;
                  switchback = false;
               }
               else {
                  j[i] = new junction((byte)((sectot)%secmax),(byte)((secmax+sectot-5)%secmax),(byte)((secmax+sectot-1)%secmax),xmid+(int)(a1*Math.cos(angle)),ymid+(int)(b1*Math.sin(angle)));
                  new section(angle, angle+inc, a1,b1,i,(byte)((i+1)%jtot),3);
                  switchback = true;
               }
            }
            else {
                if(switchback) {
                  j[i] = new junction((byte)((secmax+sectot-1)%secmax),(byte)((sectot)%secmax),(byte)((sectot+1)%secmax),xmid+(int)(a2*Math.cos(angle)),ymid+(int)(b2*Math.sin(angle)));
                  new section(angle, angle+inc*3, a2,b2,i,(byte)((i+3)%jtot),6);
                  new section(angle, a2,b2, angle+inc, a1,b1,i,(byte)((i+1)%jtot),4);
                  outside = true;
                  switchback = false;
               }
               else {
                  j[i] = new junction((byte)((sectot)%secmax),(byte)((secmax+sectot-5)%secmax),(byte)((secmax+sectot-1)%secmax),xmid+(int)(a2*Math.cos(angle)),ymid+(int)(b2*Math.sin(angle)));
                  new section(angle, angle+inc, a2,b2,i,(byte)((i+1)%jtot),2);
                  switchback = true;
               }
            }
         }
         short carmax = (short)(sectot - Math.min(5, (totsplits-wordtot)*4/wordtot));
         short o[] = u.shuffle(u.select(sectot,carmax));
         for(i=0;i<sectot;++i) {
            secs[i].calcjmiss();
         }
         for(i=0;i<carmax;++i) {
            idlecars[idlecartot++] = new idlecar((byte)o[i],secs[o[i]].ptot*5/8);
         }
         w = mover.WIDTH;
         h  = mover.HEIGHT;
         dontclear = true;
     }
     public void paint(Graphics g,int x, int y, int w, int h) {
        for(short i=0;i<jtot; ++i) {
           j[i].paint(g);
        }
        for(short i=0;i<sectot; ++i) {
           secs[i].paint(g);
        }
        for(short i=0;i<idlecartot; ++i) {
           idlecars[i].paint(g);
        }
     }
  }
  class section {
    byte j1,j2;
    int jmiss1,jmiss2,jmiss1p1,jmiss2p1,jmiss1p2,jmiss2p2,jstart,jend;
    Polygon p = new Polygon();
    Polygon p1 = new Polygon();
    Polygon p2 = new Polygon();
    Polygon logp,logp1,logp2;
    byte thissec = sectot;
    int length,ballinc,ptot;
    section(double a1, double a2, int a,int b,byte jj1,byte jj2, int len) {  // arc of ellipse
       int midx = screenwidth/2, midy=screenheight/2;
       p = sharkpoly.arc(midx,midy,a,b,100,a1,a2);
       p1 = sharkpoly.arc(midx,midy,a+railgap,b+railgap,100,a1,a2);
       p2 = sharkpoly.arc(midx,midy,a-railgap,b-railgap,100,a1,a2);
       logp = u.multx(p,mover.WIDTH,screenwidth);
       logp1 = u.multx(p1,mover.WIDTH,screenwidth);
       logp2 = u.multx(p2,mover.WIDTH,screenwidth);
       ptot = p.npoints*1000;
       j1 = jj1;
       j2 = jj2;
       length = len;
       secs[sectot++] = this;
    }
                                             //connect ellipses
    section(double a1,  int a,int b, double a2,  int aa,int bb,byte jj1, byte jj2,int len) {
       int midx = screenwidth/2, midy=screenheight/2;
       double a1z = a1,a2z = a2;
       if(a>aa) a1z += Math.PI/8;
       else a2z -= Math.PI/8;
       u.drawcurve(p, midx + (int)(a*Math.cos(a1)),
                             midy + (int)(b*Math.sin(a1)),
                             Math.atan2(b*Math.cos(a1z), -a*Math.sin(a1z)),
                             midx + (int)(aa*Math.cos(a2)),
                             midy + (int)(bb*Math.sin(a2)),
                            Math.atan2(bb * Math.cos(a2z), -aa*Math.sin(a2z)));
       ptot = p.npoints*1000;
       u.drawcurve(p1, midx + (int)((a+railgap)*Math.cos(a1)),
                             midy + (int)((b+railgap)*Math.sin(a1)),
                             Math.atan2(b*Math.cos(a1z), -a*Math.sin(a1z)),
                             midx + (int)((aa+railgap)*Math.cos(a2)),
                             midy + (int)((bb+railgap)*Math.sin(a2)),
                            Math.atan2(bb * Math.cos(a2z), -aa*Math.sin(a2z)));
       u.drawcurve(p2, midx + (int)((a-railgap)*Math.cos(a1)),
                             midy + (int)((b-railgap)*Math.sin(a1)),
                             Math.atan2(b*Math.cos(a1z), -a*Math.sin(a1z)),
                             midx + (int)((aa-railgap)*Math.cos(a2)),
                             midy + (int)((bb-railgap)*Math.sin(a2)),
                            Math.atan2(bb * Math.cos(a2z), -aa*Math.sin(a2z)));
       j1 = jj1;
       j2 = jj2;
       logp = u.multx(p,mover.WIDTH,screenwidth);
       logp1 = u.multx(p1,mover.WIDTH,screenwidth);
       logp2 = u.multx(p2,mover.WIDTH,screenwidth);
       length = len;
       secs[sectot++] = this;
    }
   void getballinc() {
       int x1  =  p.xpoints[p.npoints/2];
       int y1  =  p.ypoints[p.npoints/2];
       int x2,y2;
       for(int i=p.npoints/2; i<p.npoints;++i) {
         x2 = p.xpoints[i];
         y2 = p.xpoints[i];
         if ((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2) > tradsq*4) {
           ballinc = (i - p.npoints/2)*1000;
           return;
         }
       }
    }
    void calcjmiss() {
      int i;
      int x1,y1,x2,y2;
      getballinc();
      for(i=0;i<p.npoints && j[j1].isover(p.xpoints[i],p.ypoints[i]); ++i)  ++jmiss1;
      for(i=p.npoints-1;i>0 && j[j2].isover(p.xpoints[i],p.ypoints[i]); --i)  ++jmiss2;
      x1 = p.xpoints[jmiss1];
      y1 = p.ypoints[jmiss1];
      for(i=jmiss1;i<p.npoints;++i) {
         x2 = p.xpoints[i];
         y2 = p.ypoints[i];
         if ((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2) > tradsq*4) break;
      }
      jstart = i*1000;
      x1 = p.xpoints[p.npoints-1-jmiss2];
      y1 = p.ypoints[p.npoints-1-jmiss2];
      for(i=p.npoints-1-jmiss2;i>0;--i) {
         x2 = p.xpoints[i];
         y2 = p.ypoints[i];
         if ((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2) > tradsq*4) break;
      }
      jend = i*1000;
      for(i=0;i<p1.npoints && j[j1].isover(p1.xpoints[i],p1.ypoints[i]); ++i)  ++jmiss1p1;
      for(i=p1.npoints-1;i>0 && j[j2].isover(p1.xpoints[i],p1.ypoints[i]); --i)  ++jmiss2p1;
      for(i=0;i<p2.npoints && j[j1].isover(p2.xpoints[i],p2.ypoints[i]); ++i)  ++jmiss1p2;
      for(i=p2.npoints-1;i>0 && j[j2].isover(p2.xpoints[i],p2.ypoints[i]); --i)  ++jmiss2p2;
    }
    void paint(Graphics g) {
      int start1 = 0;
      int start2 = 0;
      int end1 = p1.npoints;
      int end2 = p2.npoints;
      g.setColor(fade(Color.black));
      if(j[j1].closed(thissec))  {start1 = jmiss1p1; start2 = jmiss1p2;}
      if(j[j2].closed(thissec))  {end1 -= jmiss2p1; end2 -=jmiss2p2;}
      if(start1==0)   {
        g.drawPolyline(p1.xpoints,p1.ypoints,end1);
        g.drawPolyline(p2.xpoints,p2.ypoints,end2);
        return;
      }
      Polygon pp = u.extract(p1,start1,end1-1);
      g.drawPolyline(pp.xpoints,pp.ypoints,pp.npoints);
      pp = u.extract(p2,start2,end2-1);
      g.drawPolyline(pp.xpoints,pp.ypoints,pp.npoints);
    }
    boolean connected(section sec) {
      byte jj1,jj2;
      return (((jj1=j1) == (jj2=sec.j1) || (jj1=j2) == (jj2=sec.j1) || (jj1=j1) == (jj2=sec.j2) || (jj1=j2) == (jj2=sec.j2))
            && (j[jj1].common == sec.thissec && j[jj2].selected == thissec
              || j[jj2].common == sec.thissec && j[jj1].selected == thissec));
    }
  }
  class junction {
    byte common,selected,unselected;
    int x1,y1,w1,h1;
    int x2,y2,w2,h2;
    int logx1,logx2,logw1,logw2;
    boolean gotword;
//startPR2004-09-15^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    Color color;
    Color selcolor;
//    Color color = yellow(),selcolor=Color.red;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    junction(byte c,byte s1, byte s2,int xx,int yy) {
//startPR2004-09-15^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      color = yellow();
      /*colour stations cyan rather than red when vowels are highlighted. Otherwise
       the vowels are invisible (red on red) */
      if((rgame.options & word.VOWELS) != 0){
           selcolor=Color.CYAN;
      }
      else{
           selcolor=Color.red;
      }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       common=c;
       if(u.rand(2)==0) {selected=s1;unselected=s2;}
       else      {selected=s2;unselected=s1;}
       w1 = jw1;
       w2 = jw2;
       h1 = jh1;
       h2 = jh2;
       x1 = Math.max(0,Math.min(screenwidth-w1,xx - w1/2));
       y1 = yy - h1/2;
       x2 = xx - w2/2;
       y2 = yy - h2/2;
       logx1 = x1 * mover.WIDTH/screenwidth;
       logx2 = x2 * mover.WIDTH/screenwidth;
       logw1 = w1 * mover.WIDTH/screenwidth;
       logw2 = w2 * mover.WIDTH/screenwidth;
    }
    void setcurr(byte jj) {
       short i;
       if(segs == null) {
          for(i=0;i<wordtot;++i) {
            wordfont  = adjustFont(wordfont,words[i].val,jw1,jh1);
          }
          metrics = getFontMetrics(wordfont);
          segs = new segment[segbits.length];
          for(i=0;i<segbits.length;++i) {
            segs[i] = new segment(segbits[i]);
          }
       }
       jword = (byte)jj;
       gotword=true;
       curr = words[wordnext = (wordnext+1)%wordtot];
       curr.currseg = curr.currpos = 0;
       curr.clearmarked();
       curr.x = x1*mover.WIDTH/screenwidth;
       curr.y = y1*mover.HEIGHT/screenheight;
       forceseg = true;
       forcesegtime = gtime+3000;   // force seg display after 3 sec
    }
    void switchover() {
//startPR2005-04-18^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      if(Demo_base.isDemo){
        if (Demo_base.demoIsReadyForExit(0)){
          u.pause(1000);
          return;
        }
      }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       byte bb = unselected;
       unselected = selected;
       selected = bb;
    }
    boolean closed(byte sec) {
      return (sec == unselected || gotword);
    }
    byte newsection(byte oldsec) {
      return (oldsec == common) ? selected : common;
    }
    boolean isover(int x, int y) {
      return (x>=x1 && x <= x1+w1 && y >= y1 && y <= y1 + h1
               || (x2+w2/2-x)*(x2+w2/2-x)+(y2+w2/2-y)*(y2+w2/2-y)
                     < w2*w2/4);
    }
    void paint(Graphics g) {
      g.setColor(fade(gotword?selcolor:color));
      g.fillRect(x1,y1,w1,h1);
      g.fillOval(x2,y2,w2,h2);
      if(gotword && !seesegs) {
        curr.paint(g,x1,y1,w1,h1);
      }
    }
  }
  class car extends mover {
     byte section;
     int pos;    // no of points along * 1000
     Color color = Color.green;
     car(byte num) {
       super(false);
       section =  (byte)num;
       pos =  secs[num].ptot/2;
       w = mover.WIDTH/80;
       h = w*screenwidth/screenheight;
       keepMoving = true;
     }
     public void paint(Graphics g,int x, int y, int w, int h) {
        if(x==0) return;
        g.setColor(fade(color));
        g.fillOval(x,y,w,h);
     }
  }
  class train extends mover {
     byte section, prevsection[]=new byte[4] ,section1;
     boolean forwards,prevforward[]=new boolean[4],forward1;
     short previndex;
     byte cartot;
     int pos,pos1;    // no of points along * 1000
     Color color = Color.red;
     long lasttime = -1;
     boolean afterFirstStop = false;
     train() {
       super(false);
       section = section1 = (byte)u.rand(sectot);
       forwards = (u.rand(2)==0);
       pos = pos1 = secs[section].ptot/2;
       w = trad*2*mover.WIDTH/screenwidth;
       h = trad*2*mover.HEIGHT/screenheight;
       keepMoving = true;
     }
     public void changeImage(long currtime) {
        if(stopped || pausing && x != 0) return;
        section sec = secs[section];
        int tot = sec.ptot;
        if(lasttime<0) lasttime = gtime;
        int inc = (int)((speed+4)*(currtime-lasttime)*sec.p.npoints/secs.length);
        if(completed) {
          inc += (int)((gtime-completeat)*inc/2000);
          if(inc>tot) inc  = tot*2/3+u.rand(tot/3);
        }
        else inc  = Math.min(tot/2,inc);
        if(forwards) {
           pickupcarsf(pos+inc+sec.ballinc);
           pos += inc;
           if(j[sec.j2].gotword) {
               if(pos > sec.jend)   {
                 doStop();
                 pos = sec.jend;
               }
           }
           else if(j[sec.j2].closed(section)) {
              if(completed) j[sec.j2].switchover();
              else if(pos > sec.jend) {
                if(curr != null) {
                    j[jword].gotword = false;
                    jword = sec.j2;
                    j[jword].gotword = true;
                    curr.x = j[sec.j2].x1*mover.WIDTH/screenwidth;
                    curr.y = j[sec.j2].y1*mover.HEIGHT/screenheight;
                }
                else {
                    j[sec.j2].setcurr(sec.j2);
                }
                String helpstring = "help_trains3"+helpsuff();
                if(specialprivateon){
                  if(helpstring.equals("help_trains3"))
                     helpstring = helpstring.concat("def");
                }
                help(helpstring);
                pos = sec.jend;
                forcewholetime = gtime+2000;
                doStop();
              }
           }
           else {
              if(completed && innewsection && section == j[sec.j2].common) {
                if(u.rand(2)==0) j[sec.j2].switchover();
                innewsection = false;
              }
              if(pos>tot) {
                 byte oldj = sec.j2;
                 int notused = pos-tot;
                 innewsection = true;
                 System.arraycopy(prevforward,0,prevforward,1,prevforward.length-1);
                 System.arraycopy(prevsection,0,prevsection,1,prevsection.length-1);
                 prevforward[0] = forwards;
                 prevsection[0] = section;
                 section = j[sec.j2].newsection(section);
                 sec = secs[section];
                 forwards = (sec.j1 == oldj);
                 pos = forwards ? 0 : (sec.ptot);
                 changeImage(currtime+(currtime-lasttime)*notused/inc);
              }
           }
        }
        else {
           pickupcarsb(pos-inc-sec.ballinc);
           pos -= inc;
           if(j[sec.j1].gotword) {
              if(pos <= sec.jstart) {
                 doStop();
                 pos = sec.jstart;
               }
           }
           else if(j[sec.j1].closed(section)) {
              if(completed) j[sec.j1].switchover();
              else if(pos <= sec.jstart) {
                if(curr != null) {
                    j[jword].gotword = false;
                    jword = sec.j1;
                    j[jword].gotword = true;
                    curr.x = j[sec.j1].x1*mover.WIDTH/screenwidth;
                    curr.y = j[sec.j1].y1*mover.HEIGHT/screenheight;
                }
                else {
                  j[sec.j1].setcurr(sec.j1);
                }
                String helpstring = "help_trains3"+helpsuff();
                if(specialprivateon){
                  if(helpstring.equals("help_trains3"))
                     helpstring = helpstring.concat("def");
                }
                help(helpstring);
                pos = sec.jstart;
                forcewholetime = gtime+2000;
                doStop();
              }
           }
           else {
              if(completed && innewsection && section == j[sec.j1].common) {
                if(u.rand(2)==0) j[sec.j1].switchover();
                innewsection = false;
              }
              if(pos<0) {
                 byte oldj = sec.j1;
                 int notused = -pos;
                 System.arraycopy(prevforward,0,prevforward,1,prevforward.length-1);
                 System.arraycopy(prevsection,0,prevsection,1,prevsection.length-1);
                 innewsection = true;
                 prevforward[0] = forwards;
                 prevsection[0] = section;
                 section = j[sec.j1].newsection(section);
                 sec = secs[section];
                 forwards = (sec.j1 == oldj);
                 pos = forwards ? 0 : (sec.ptot);
                 changeImage(currtime+(currtime-lasttime)*notused/inc);
              }
           }
        }
        int realpos = Math.max(0,Math.min(sec.p.npoints-1, pos/1000));
        tox = sec.p.xpoints[realpos] * mover.WIDTH/screenwidth - w/2;
        toy = sec.p.ypoints[realpos] * mover.HEIGHT/screenheight - h/2;
        lasttime = gtime;
        byte  i,k,lastj=sec.j1;
        if(curr==null && idlecartot == 0  && !completed)  {
               completeat = gtime;
               score(gametot1);
               exitbutton(mover.HEIGHT/2);
        }
     }


     void doStop(){
        stopped = true;
        if(afterFirstStop)
            flip();
        afterFirstStop = true;
     }
     void pickupcarsf(int pos2) {
        int x1,y1;
        int pos1 = pos;
        for(byte i = 0;i<idlecartot;++i) {
           if(idlecars[i].section == section
                && idlecars[i].pos > pos1 && idlecars[i].pos < pos2) {
              pos += secs[section].ballinc;
              pos2 += secs[section].ballinc;
              ++cartot;
              if(--idlecartot>i)
                  System.arraycopy(idlecars,i+1,idlecars,i,idlecartot-i);
              i = -1;
           }
        }
     }
     void pickupcarsb(int pos2) {
        int x1,y1;
        int pos1 = pos;
        for(byte i = 0;i<idlecartot;++i) {
           if(idlecars[i].section == section
              && idlecars[i].pos < pos1 && idlecars[i].pos > pos2) {
              pos -= secs[section].ballinc;
              pos2 -= secs[section].ballinc;
              ++cartot;
              if(--idlecartot>i)
                  System.arraycopy(idlecars,i+1,idlecars,i,idlecartot-i);
              i = -1;
           }
        }
     }
     void reverse() {
        pos = Math.min(secs[section].jend,
                       Math.max(secs[section].jstart,pos));
        section1 = section;
        forward1 = forwards;
        pos1 = pos;
        previndex=0;
        while(cartot > 0) {
           idlecars[idlecartot++] = new idlecar(section1,pos1);
           --cartot;
           nextcar();
        }
        pos = pos1;
        forwards = !forward1;
        section = section1;
     }
     void nextcar() {
        section sec = secs[section1];
        int realpos = Math.min(sec.p.npoints-1, pos1/1000);
        int x=sec.p.xpoints[realpos];
        int y=sec.p.ypoints[realpos];
        int x2,y2;
        do {
          if(forward1) {
             if((pos1-=1000)<0) {
               section1 = prevsection[previndex];
               forward1 = prevforward[previndex];
               sec = secs[section1];
               pos1 = forward1?sec.ptot:0;
               ++previndex;
             }
             realpos = Math.min(sec.p.npoints-1, pos1/1000);
             x2=sec.p.xpoints[realpos];
             y2=sec.p.ypoints[realpos];
          }
          else {
            if((pos1+=1000)>=sec.ptot) {
               section1 = prevsection[previndex];
               forward1 = prevforward[previndex];
               sec = secs[section1];
               pos1 = forward1?sec.ptot:0;
               ++previndex;
             }
             realpos = Math.min(sec.p.npoints-1, pos1/1000);
             x2=sec.p.xpoints[realpos];
             y2=sec.p.ypoints[realpos];
          }
        } while((x2-x)*(x2-x) + (y2-y)*(y2-y) < tradsq*4);
      }
      public void paint(Graphics g,int x, int y, int w, int h) {
        if(x==0) return;
        pos1 = pos;
        section1 = section;
        forward1 = forwards;
        int realpos;
        previndex = 0;
        for(byte i = 0; i<cartot; ++i) {
           g.setColor(fade2(carcolor));
           realpos = Math.min(secs[section1].p.npoints-1, pos1/1000);
           g.fillOval(secs[section1].p.xpoints[realpos]-w/2,secs[section1].p.ypoints[realpos]-h/2,w,h);
           nextcar();
        }
        g.setColor(fade2(color));
        realpos = Math.min(secs[section1].p.npoints-1, pos1/1000);
        g.fillOval(secs[section1].p.xpoints[realpos]-w/2,secs[section1].p.ypoints[realpos]-h/2,w,h);
     }
  }
  //--------------------------------------------------------------------------
  class idlecar {
    byte section;
    int pos;
    idlecar(byte sec,int p) {
       section = sec;
       pos = p;
    }
    void paint(Graphics g) {
      g.setColor(carcolor);
      g.fillOval(secs[section].p.xpoints[pos/1000]-trad,secs[section].p.ypoints[pos/1000]-trad,trad*2,trad*2);
    }
  }
  //-------------------------------------------------------------------
  class starter extends messmover2 {
    starter() {
       super(u.splitString(u.gettext("clicktostart","label")),mover.WIDTH/4,mover.HEIGHT/4);
       starting=pausing=true;
       gamePanel.addMover(this,(mover.WIDTH-w)/2,(mover.HEIGHT-h)/2);
    }
    public void mouseClicked(int x, int y) {
      removeMover(this);
      starting=pausing= false;
    }
  }
}
