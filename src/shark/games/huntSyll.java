package shark.games;

import java.awt.*;

import shark.*;
import shark.sharkGame.*;

public class huntSyll extends sharkGame {//SS 03/12/05
  short wordtot,curr=-1;
  int MAXSEGSINWORDS = phonicsw ? 24:16;
  static final short MONSTERSTART = 2;
  short monstertot = MONSTERSTART;
  short monstercaught;
  static final short MAXWORDS = 6;     // max with splits
  static final short MOVEINT = 2000;
  static final short MOVEMIN = 2000;
  static final short MOVEMAX = 3000;
  static final int FIZZTIME = 4000;
  static final int MOVETIME = 1000;
  static final int MAXEXPLODE = 24;
  wholeword words[];
  String segbits[] = new String[0];
  segment segs[],movingseg;
  int maxwidth = mover.WIDTH/6,maxheight= mover.HEIGHT/4;
  sharkImage hiders[];
  sharkImage monsteri[];
  monster monsters[] = new monster[100];
  boolean showmonsters,freeze;
  hide hides[];
  cagem cagebars;
  long lastmove;
  short cleared;
  int reactivate=-1;
  int leftmargin,cage;
  int fizzx,fizzy;
  wholeword thiswd;
  word wd1[];
  long endfizz,nexttime;
  boolean showpicture = !specialprivateon;
//  javax.swing.Timer tt = new javax.swing.Timer(FIZZTIME,
//             new ActionListener() {
//                public void actionPerformed(ActionEvent e){
//                   if(thiswd.finished != 0) {
//                     showmonsters  =false;
//                     gamePanel.clearall();
//                     thiswd.w -= MAXEXPLODE*2*mover.WIDTH/screenwidth;
//                     thiswd.mx += MAXEXPLODE*mover.WIDTH/screenwidth;
//                     thiswd.h -= MAXEXPLODE*2*mover.HEIGHT/screenheight;
//                     thiswd.my += MAXEXPLODE*mover.HEIGHT/screenheight;
//                     gamePanel.drop(thiswd);
//                     thiswd.moveto(0,curr*cage/wordtot,MOVETIME);
//                     u.pause(2000);
//                     showmonsters = false;
//                     thiswd.keepMoving = false;
//                     gamePanel.clearWholeScreen = false;
//                     nextword();
//                     gamePanel.showSprite = true;
//                     gamePanel.clearall();
//                    }
//                }
//          }
//  );

  public huntSyll() {
    errors = true;
//    tt.setRepeats(false);
    gamescore1 = true;
//    peeps = true;
    listen= true;
//    peep = true;
    wantspeed=true;
//    gamePanel.setBackground(u.darkcolor());
    wordfont = null;

    setupWords();
    buildTopPanel();
    nextword();
 }
 String helpsuff() {
   if(phonicsw) return  blended?"phb":"phw";
   else if(phonics) return "ph";
   else if (curr<words.length && curr>=0 && words[curr].split)  return "a";
   else return "";
 }
 //-------------------------------------------------------------
 void setupWords() {
    short i,j,k;
    int x1=0,y1=0;
    wholeword ww;
    word wd[] = sharkStartFrame.mainFrame.wordTree.getvisiblewords();
    if(wd.length<=4 && !phonicsw && !wd[0].split() ) wd = u.augmentlist(wd,false);
    wd1 = wd;
    wordtot = MAXWORDS;
    short o[] = u.shuffle(u.select((short)wd.length,(short)wd.length));
    short got=0;
    short[] want = new short[wordtot];
    short segsofar = 0;
    wd = clearphonics(wd);
    for(i=0;i<wd.length && got<wordtot && segsofar<MAXSEGSINWORDS;++i)  {
      if(!phonicsw && wd[o[i]].value.indexOf('/') > 0)  {
          want[got++] = o[i];segsofar+=wd[o[i]].segtot();
      }
      else if(phonicsw && wd[o[i]].value.indexOf(u.phonicsplit) > 0  && !wd[o[i]].onephoneme)  {
          want[got++] = o[i];segsofar+=wd[o[i]].phsegtot();
      }
    }
                     // make up with unsplit ones
    for(i=0;i<wd.length && got<wordtot && segsofar<MAXSEGSINWORDS;++i)  {
       if(!phonicsw && wd[o[i]].value.indexOf('/') <= 0)  {want[got++] = o[i];++segsofar;}
    }
    wordtot=got;
    o = u.shuffle(u.select(wordtot,wordtot));
    words = new wholeword[wordtot];
    String wordstrings[] = new String[wordtot];
    for(i=0;i<wordtot;++i) {
        words[i] = ww = new wholeword(wd[want[o[i]]]);
        words[i].split = (!phonics && words[i].wd.value.indexOf('/') >= 0);
        if(phonicsw) segbits = wd[want[o[i]]].phaddsegs(segbits,true);
        else segbits = u.simplesplit(segbits,wd[want[o[i]]].vsplit(),true);
        wordfont = this.sizeFont(wordfont,wd[want[o[i]]].v(),mover.WIDTH/4,mover.HEIGHT/12);
        wordstrings[i] = wd[want[o[i]]].v();
   }
    leftmargin =  Math.max(mover.WIDTH/6,maxWordWidth(metrics) * mover.WIDTH/screenwidth);
    cage = Math.min(mover.WIDTH*3/4, metrics.getHeight()*wordtot*mover.HEIGHT/screenheight);
    new cageclear();
    addsegments();
    cagebars = new cagem();
 }
 //-------------------------------------------------------------------
 void adjustsegments() {
    String v = words[curr].endval.substring(words[curr].currpos);
    String s = words[curr].wordsegbits[words[curr].currseg];
    for(short i = 0; i< segs.length; ++i) {
        segs[i].active = (segs[i].val.equals(s)
                        || !u.startswith(v,segs[i].val));
    }
 }
 //-----------------------------------------------------------------
 void nextword() {
    short i;
    if(++curr < wordtot) {
       adjustsegments();
       currword = words[curr].wd;
//startPR2005-04-18^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         if(Demo_base.isDemo){
           if (Demo_base.demoIsReadyForExit(1)) return;
         }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       if( phonicsw) spokenWord.sayPhonicsWhole(currword);
       else if(phonics) spokenWord.sayPhonicsWord(currword,500,false,false,!singlesound);
       else currword.say();
       if(!phonics && showpicture) 
           new showpicture();
       gamePanel.showSprite = true;

       String helpstring = "help_hunt1"+helpsuff();
       if(specialprivateon){
        if(helpstring.equals("help_hunt1"))
            helpstring = helpstring.concat("def");
        }
       help(helpstring);
       if(delayedflip && !completed){
           flip();
       }
    }
    else {
       score(gametot1);
       clearexit=true;
       if(monstercaught > wordtot*2)
           showmessage(u.edit(rgame.getParm("endsuper"),String.valueOf(monstercaught)),mover.WIDTH/6,mover.HEIGHT/16,mover.WIDTH*5/6,mover.HEIGHT/5);
       else if(monstercaught > wordtot*3/2)
           showmessage(u.edit(rgame.getParm("endgood"),String.valueOf(monstercaught)),mover.WIDTH/6,mover.HEIGHT/16,mover.WIDTH*5/6,mover.HEIGHT/5);
       else if(monstercaught >= wordtot)
           showmessage(u.edit(rgame.getParm("endm"),String.valueOf(monstercaught)),mover.WIDTH/6,mover.HEIGHT/16,mover.WIDTH*5/6,mover.HEIGHT/5);
       exitbutton(mover.HEIGHT*4/5);
    }
 }
 //-------------------------------------------------------------------
 void addsegments() {
    int i,j,xx=0,yy=0,repeats=0;
    int space = metrics.charWidth(' ')*2*mover.WIDTH/screenwidth;
    int textht = metrics.getHeight()*mover.HEIGHT/screenheight;
    short totstartch = 0,hidetot;
    segs = new segment[segbits.length];
    u.sort(segbits);
    for(i=1;i<segbits.length;++i) {
       if(!segbits[i].substring(0,1).equalsIgnoreCase(segbits[i-1].substring(0,1)))
           ++totstartch;
    }
    if(totstartch == 0) hidetot = 3;
   else hidetot = (short)Math.min(12,Math.max(4,totstartch/2));
    int ineach = segbits.length / hidetot;
    for(i = 0; i< segs.length; ++i) {  // allocate to hides
       segs[i] = new segment(segbits[i]);
       if (i>0 && (ineach>0 && (i/ineach >= xx))
            && (totstartch == 0 || !segbits[i].substring(0,1).equalsIgnoreCase(segbits[i-1].substring(0,1)))
            || ((hidetot-xx) > (segs.length - i)) // the number of spaces left is > the number left to place
            )
           ++xx;
       segs[i].underhide = (short)(xx);
    }
    hidetot = (short)Math.max(xx+1,2);
    hidetot = (short)Math.max(hidetot,4);  // new for Ruth - doesn't want 3 cols
    int rows[] = (new int[][] {new int[] {2}, new int[] {3}, new int[] {2,2},
                              new int[] {3,2}, new int[] {3,3}, new int[] {2,3,2},
                              new int[] {4,4}, new int[] {3,3,3},  new int[] {3,4,3},
                               new int[] {4,3,4},  new int[] {4,4,4}}) [hidetot-2];
    int row,col,bwidth;
    int bheight = mover.HEIGHT/rows.length;
    int ytot = bheight/textht;
    int points = wordfont.getSize(),startpoints = points;
    int offset[][] = new int[rows.length][], line[][] =  new int[rows.length][];
    for(i=0;i<rows.length;++i) {
       offset[i] = new int[rows[i]];
       line[i] = new int[rows[i]];
    }
    for(i = 0; i< segs.length; ++i) {
       for(j=row=col=0;j<segs[i].underhide;++j) {
             if(++col >= rows[row]) {++row;col=0;}
       }
       bwidth = (mover.WIDTH-leftmargin)/rows[row];
       repeats = 0;
       if(points<=24 && points < startpoints) {
          if(offset[row][col] + segs[i].w > bwidth) {
             ++line[row][col];
             offset[row][col] = 0;
          }
          segs[i].x = leftmargin + col * bwidth + offset[row][col];
          segs[i].y = bheight * row + line[row][col]*textht;
          offset[row][col] += segs[i].w + space;
       }
       else {
        loop: while(++repeats < 1000) {
          segs[i].y = bheight * row + u.rand(ytot)*textht;
          segs[i].x = leftmargin + col * bwidth + u.rand(bwidth-segs[i].w);
          for(j = 0; j<i; ++j) {
             if(segs[i].underhide ==  segs[j].underhide
                   && segs[j].x < segs[i].x + segs[i].w + space*2
                   && segs[i].x < segs[j].x + segs[j].w + space*2
                   && Math.abs(segs[j].y - segs[i].y) < textht*2) continue loop;
          }
          break;
        }
       }
       if (repeats >= 1000 && points >24) {
         wordfont = wordfont.deriveFont((float)--points);
         metrics = getFontMetrics(wordfont);
         space = metrics.charWidth(' ')*2*mover.WIDTH/screenwidth;
         textht = metrics.getHeight()*mover.HEIGHT/screenheight;
         ytot = bheight/textht;
         i = -1;
         repeats = 0;
         continue;
       }
       repeats = 0;
       addMover(segs[i], segs[i].x, segs[i].y);
    }
    hides = new hide[hidetot];
    hiders = sharkImage.randomimages("hide_",hidetot);
    monsteri = sharkImage.findall("monster_");
    for(i=col=row=0;i<hides.length;++i,++col) {
        if(col >= rows[row]) {++row;col=0;}
        bwidth = (mover.WIDTH-leftmargin)/rows[row];
        hides[i] = new hide(i,leftmargin+col*bwidth,row*bheight,bwidth,bheight);
    }
    for(i=0;i<monstertot;++i)  monsters[i] = new monster(i);
    for(i=0;i<hides.length;++i) {
       addMover(hides[i], hides[i].x, hides[i].y);
    }
 }
 public void listen1() {
   if(currword != null) {
     if( phonicsw) spokenWord.sayPhonicsWhole(currword);
     else if(phonics) spokenWord.sayPhonicsWord(currword,500,false,false,!singlesound);
     else super.listen1();
   }
 }
 //---------------------------------------------------------------
 public boolean click(int mx,  int my)  {
   short i,j;
   int x1,y1,x2,y2;
   int xx1 = gamePanel.mousexs;
   int yy1 = gamePanel.mouseys;
   boolean onebad = false;
   int space = metrics.charWidth(' ')*mover.WIDTH/screenwidth;
   int ydiff = metrics.getHeight()/2*mover.HEIGHT/screenheight;
   if(curr<0 || completed ) return true;
   if(showmonsters) {
      for(j=0;j<monstertot;++j) {
          if(!monsters[j].got && monsters[j].isover()) {
              ++monstercaught;
              noise.squeek();
              monsters[j].got = true;
              monsters[j].moveto(u.rand(leftmargin-monsters[j].w),
                                         cage+u.rand(mover.HEIGHT-cage-monsters[j].h),MOVETIME);
             if(monstertot < monsters.length - 1) {
                 monsters[monstertot] = new monster(monstertot);
                 ++monstertot;
             }
             break;
          }
      }
      return true;
   }
   else                         // see if over word
    if( words[curr].val == null) {   // not started - check mouse over word
      x1 = gamePanel.mousex - gamePanel.sprite.w/2;
      x2 = gamePanel.mousex + gamePanel.sprite.w/2;
      y1 = gamePanel.mousey - gamePanel.sprite.h/2;
      y2 = gamePanel.mousey + gamePanel.sprite.h/2;
      for(i=0;i<segs.length;++i) {
         if(segs[i].active && segs[i].isvisible()
           &&  x1 < segs[i].x +segs[i].w
           &&  x2 > segs[i].x
           &&  y1 < segs[i].y +segs[i].h
           &&  y2 > segs[i].y) {
            if(!words[curr].fits(i)) {
                onebad = true;
             }
             else {
               return true;
             }
         }
      }
   }
   else if(words[curr].finished == 0) {
      x1 = words[curr].x + words[curr].w;
         // start change rb 21/11/07
      if(phonics && words[curr].wd.phonicsmagic != null && words[curr].wd.ismagicsyl(words[curr].currseg+1)) {
         x1 -= metrics.stringWidth("-e")*mover.WIDTH/screenwidth;
      }
//      if(phonics && words[curr].wd.phonicsmagic != null && words[curr].wd.ismagicsyl(words[curr].currseg+1)) {
//         x1 -= space/2;
//      }
      // end change rb 21/11/07
      y1 = words[curr].y;
      for(i=0;i<segs.length;++i) {
         if(segs[i].active && segs[i].isvisible()
           &&  (Math.abs(x1 - segs[i].x) < space*2
                     &&  Math.abs(y1 - segs[i].y) < ydiff
                || segs[i].mouseOver)) {
             if(!words[curr].fits(i)) {
                onebad = true;
             }
             else return true;
         }
         words[curr].mx = - words[curr].w;
         words[curr].my = - words[curr].h;
      }
   }
   for(i=(short)(hides.length-1);i>=0;--i) {  // clear hide if required
      if (hides[i].active
          && hides[i].mouseOver) {
         hides[cleared].active = true;
         hides[i].active = false;
         if(reactivate>=0) {segs[reactivate].active = true;reactivate = -1;}
         cleared = i;
         gamePanel.clearall();
         return true;
      }
   }
   if(onebad) {
      error();
      if(!phonics) noise.groan();
   }
   return true;
 }
 //-----------------------------------------------------------------
 public void afterDraw(long t) {
    int i;
    if(endfizz != 0 && gtime > endfizz) {
        endfizz = 0;
        showmonsters  =false;
        gamePanel.clearall();
        thiswd.w -= MAXEXPLODE*2*mover.WIDTH/screenwidth;
        thiswd.mx += MAXEXPLODE*mover.WIDTH/screenwidth;
        thiswd.h -= MAXEXPLODE*2*mover.HEIGHT/screenheight;
        thiswd.my += MAXEXPLODE*mover.HEIGHT/screenheight;
        gamePanel.drop(thiswd);
        thiswd.moveto(0,curr*cage/wordtot,MOVETIME);
        nexttime = gtime + 2000;
    }
    if(nexttime != 0 && gtime > nexttime) {
       nexttime = 0;
       showmonsters = false;
       thiswd.keepMoving = false;
       gamePanel.clearWholeScreen = false;
       nextword();
       gamePanel.showSprite = true;
       gamePanel.clearall();
    }
    if(showmonsters) {
       for(i=0;i<monstertot;++i) {
          if(!monsters[i].got && !monsters[i].posok) {
             if(monsters[i].visible && !monsters[i].toucheshide(monsters[i].prevhide)) {
                     gamePanel.bringtotop(monsters[i]);
                     gamePanel.bringtotop(cagebars);
                     monsters[i].posok = true;
             }
             else if(!monsters[i].visible && monsters[i].toucheshide(monsters[i].currhide)) {
                     gamePanel.puttobottom(monsters[i]);
                     monsters[i].posok = true;
             }
          }
       }
    }
 }
 //-----------------------------------------------------------------
 void startmonsters() {
    showmonsters = true;
    for(short i = 0;i<monstertot;++i) {
       if(!monsters[i].got) {
           gamePanel.puttobottom(monsters[i]);
           monsters[i].visible = false;
           monsters[i].endmove();
       }
    }
 }
 //-----------------------------------------------------------------
 class hide extends mover {
    sharkImage image;
    boolean active;
    hide(int num,int x1,int y1,int w1, int h1) {
       super(false);
       image = new sharkImage(hiders[num],false);
              w = image.w = w1;
       h = image.h = h1;
       active=true;
       dontgrabmouse=true;
       image.distort=true;
       x = x1;
       y=y1;
    }
    public void paint(Graphics g,int x1, int y1, int w1, int h1) {
       if(active) {
//          g.setColor(gamePanel.getBackground());
//          g.fillRect(x1,y1,w1,h1);
          image.paint(g,x1,y1,w1,h1);
       }
    }
 }
 //-----------------------------------------------------------------
 class monster extends mover {
    sharkImage image;
    boolean got,visible,posok;
    short currhide,prevhide;
    monster(int num) {
       super(false);
       dontclear=true;
       dontgrabmouse=true;
       image = new sharkImage(monsteri[u.rand(monsteri.length)],false);
       w = image.w = mover.WIDTH/(8+u.rand(4));
       h = image.h = mover.HEIGHT/(6+u.rand(2));
       image.adjustSize(screenwidth,screenheight);
       currhide = (short)u.rand(hides.length);
       addMover(this, hides[currhide].x + hides[currhide].w/2 - w/2 ,
                          hides[currhide].y + hides[currhide].h/2 - h/2);
    }
    public void paint(Graphics g,int x1, int y1, int w1, int h1) {
       if(showmonsters || got) {
          image.paint(g,x1,y1,w1,h1);
       }
    }
    public boolean endmove() {
       if(showmonsters && !got ) {
          prevhide = currhide;
          do {currhide = (short)u.rand(hides.length);} while(currhide==prevhide);
          moveto(hides[currhide].x + hides[currhide].w/2 - w/2 ,
               hides[currhide].y + hides[currhide].h/2 - h/2 ,
               (1000+u.rand(4000))* 8 / (speed+4));
          visible = !visible;
       }
       else if(got)  {
          moveto(u.rand(leftmargin-w), cage + u.rand(mover.HEIGHT-cage-h),MOVETIME);
       }
       posok = false;
       return false;
    }
    boolean isover() {  // test for caught
      int fx = fizzx*mover.WIDTH/screenwidth;
      int fy = fizzy*mover.HEIGHT/screenheight;
      if(!(fx >x && fx < x+w
             && fy > y && fy < y+h)) return false;
      int thispos = gamePanel.pos(this);
      for(short i=0;i<hides.length;++i)
         if(gamePanel.pos(hides[i]) > thispos
                && hides[i].x < fx && hides[i].x+hides[i].w > fx
                && hides[i].y < fy && hides[i].y+hides[i].h > fy
                && hides[i].image.covers(fizzx,fizzy)) return false;
      return true;
    }
    boolean toucheshide(short i) {
       int x1 = x*screenwidth/mover.WIDTH;
       int y1 = y*screenheight/mover.HEIGHT;
       int x2 = x1 + w*screenwidth/mover.WIDTH;
       int y2 = y1 + h*screenheight/mover.HEIGHT;
       return hides[i].image.covers(x1,y1)
              || hides[i].image.covers(x1,y2)
              || hides[i].image.covers(x2,y1)
              || hides[i].image.covers(x2,y2);
    }
 }
   //--------------------------------------------------------------
 class segment extends mover {
    String val;
    short vlen,currhide=-1;
    Color color=Color.black;
    boolean active=true;
    short underhide;

    public segment(String s) {
       super(false);
       val = s;
       vlen = (short)val.length();
       w = metrics.stringWidth(s)*mover.WIDTH/screenwidth;
       h = metrics.getHeight()*mover.HEIGHT/screenheight;
    }
    public void paint(Graphics g,int x, int y, int w, int h) {
       if(!active ||!isvisible() || showmonsters) return;
       if(shark.macOS) ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
       g.setFont(wordfont);
       g.setColor(color);
       g.drawString(val, x ,
                y + h/2 - metrics.getHeight()/2 + metrics.getMaxAscent());
       int i;
//       if((i=val.indexOf(' ')) >= 0)
//          g.drawRect(x + metrics.stringWidth(val.substring(0,i)), y,
//                           metrics.charWidth(' '), metrics.getMaxAscent());
    }
    boolean isvisible() {
      return underhide<hides.length && !hides[underhide].active;
   }
 }
 //--------------------------------------------------------------
 class wholeword extends mover {
    String endval,val;
    short vlen;
    String wordsegbits[];
    word wd;
    long finished;
    boolean fizzup = (u.rand(2)==1);
    boolean map[][];
    int maptot;
    short currpos,currseg;
    boolean split;

    public wholeword(word s) {
       super(false);
       int i;
       dontclear = true;
       keepMoving = true;
       endval = s.v();
       wd = s;
       if(phonicsw)       wordsegbits = wd.phsegs();
       else       wordsegbits = u.simplesplit(null,wd.vsplit(),false);
    }
    boolean fits(int seg) {
       int i,j;
       String newval = (val==null)?segbits[seg]:(val+segbits[seg]);
       if(phonicsw && val !=null && currword.ismagicsyl(currseg+1)
          && val.charAt(i =val.length()-2)=='-') newval = val.substring(0,i) + segbits[seg] + val.substring(i+1);
       int len=newval.length();
       if(endval.equals(newval) || phonics && !phonicsw && !singlesound && samephonics(newval)) {
         if(phonicsw) {
           spokenWord.sayPhonicsBit(wd, currseg);
           spokenWord.sayPhonicsSyl(currword,currword.fullval(currseg+1));
         }
          else if(phonics) spokenWord.sayPhonicsWord(currword,500,false,false,!singlesound);
          finished = gtime;
          help("help_hunt3"+helpsuff());
          gamescore(1);
       }
       else if (!wordsegbits[currseg].equals(segbits[seg])) {
           if(phonics)
               spokenWord.sayPhonicsBit(segbits[seg],wd1);
           return false;
       }
       else {
          if(phonicsw) {
            spokenWord.sayPhonicsBit(wd, currseg);
            spokenWord.sayPhonicsSyl(currword,currword.fullval(currseg+1));
          }
          else if(phonics) spokenWord.sayPhonicsWord(currword,500,false,false,!singlesound);
          ++currseg;
          if(!currword.ismagicsyl(currseg+1))   currpos += segbits[seg].length();
          help("help_hunt2"+helpsuff());
       }
       boolean new1 = (val==null);
       val = newval;
       w = metrics.stringWidth(val)*mover.WIDTH/screenwidth;
       if(new1) {
          h = metrics.getHeight()*mover.HEIGHT/screenheight;
          addMover(this, segs[seg].x, segs[seg].y);
          gamePanel.attachToMouse(this);
          my = Math.max(-h,Math.min(0,my));

       }
       if(finished != 0) {
          keepMoving = true;
          w += MAXEXPLODE*2*mover.WIDTH/screenwidth;
          mx -= MAXEXPLODE*mover.WIDTH/screenwidth;
          h += MAXEXPLODE*2*mover.HEIGHT/screenheight;
          my -= MAXEXPLODE*mover.HEIGHT/screenheight;
          startmonsters();
          thiswd = this;
//          tt.start();
          endfizz = gtime+FIZZTIME;
          gamePanel.clearWholeScreen = true;
       }
       else {
          adjustsegments();
          if(segs[seg].active) {segs[reactivate=seg].active = false;}
          else reactivate = -1;
       }
       hides[cleared].active=true;
       gamePanel.clearall();
       return true;
    }
    boolean samephonics(String gotch) {
       int i;
       for(i=0;i<wordtot;++i) {
          if(words[i].wd != currword && words[i].wd.v().equals(gotch) && currword.samephonics(words[i].wd)) return true;
       }
       return false;
    }
    public void paint(Graphics g,int x, int y, int w, int h) {
        if(shark.macOS) ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
       int baseline;
       int i,j,jj,ii;
       g.setFont(wordfont);
       if(finished == 0) {
           g.setColor(white());
           g.drawString(val, x,
                y +  h/2 - metrics.getHeight()/2 + metrics.getMaxAscent());
       }
       else if(x == 0) {
            g.setColor(Color.blue);
           g.drawString(val, x,
                y +  h/2 - metrics.getHeight()/2 + metrics.getMaxAscent());
       }
       else {
//          g.setColor(Color.gray);
//          g.fillRect(x,y,w,h);
          g.setColor(white());
          g.drawString(val, x+MAXEXPLODE,
                y + MAXEXPLODE  + metrics.getMaxAscent());
          if(map==null) {
             map = word.getpix(val, wordfont, metrics, metrics.getMaxAscent());
             for(i=0;i<map.length;++i)
                for(j=0;j<map[0].length;++j)
                    if(map[i][j]) ++ maptot;
          }
          int done=0;
          int limit = (int)(maptot*(gtime-finished)/FIZZTIME);
          for(i=0; i<map.length && done < limit; ++i) {
              for(jj=0; jj<map[0].length && done < limit; ++jj) {
                 j = fizzup?(map[0].length-1-jj):jj;
                 if(map[i][j] && (i==0 || !map[i-1][j])) {
                    for(ii=i+1;ii<map.length && map[ii][j];++ii);
                    if(done < limit-(ii-i)) {
                        g.setColor(Color.darkGray);
                        g.drawLine(x+MAXEXPLODE+i,y+MAXEXPLODE+j,x+MAXEXPLODE+ii-1,y+MAXEXPLODE+j);
                        g.drawLine(x+MAXEXPLODE+i,y+MAXEXPLODE+j,x+MAXEXPLODE+ii-1,y+MAXEXPLODE+j);
                    }
                    else {
                       g.setColor(u.redcolor());
                       g.fillPolygon(u.explode(fizzx = x+MAXEXPLODE+(i+ii)/2, fizzy = y+MAXEXPLODE+j,2,MAXEXPLODE));
                    }
                    done += ii-i;
                 }
              }
          }
       }
    }
 }
 class cageclear extends mover {  // clears under cage
    cageclear() {
       super(false);
       keepMoving = true;
       h = mover.HEIGHT - cage;
       w = leftmargin;
       addMover(this,0,mover.HEIGHT-h);
    }
    public void paint(Graphics g,int x, int y, int w, int h) {
    }
  }
 class cagem extends mover {
    int gap = mover.WIDTH/40;
    cagem() {
       super(false);
       dontclear = true;
       keepMoving = true;
       h = mover.HEIGHT - cage;
       w = leftmargin;
       addMover(this,0,mover.HEIGHT-h);
    }
    public void paint(Graphics g,int x, int y, int w, int h) {
       int gp = gap*screenwidth/mover.WIDTH,i;
       g.setColor(Color.black);
       for(i=0;i<w;i+=gp)  g.drawLine(x+i,y,x+i,y+h);
       g.drawLine(x+w-1,y,x+w-1,y+h);
       for(i=0;i<h;i+=gp)  g.drawLine(x,y+i,x+w,y+i);
       g.drawLine(x,y+h-1,x+w,y+h-1);
       if(monstercaught > 0) {
          g.setColor(white());
          g.setFont(wordfont);
          String s = String.valueOf(monstercaught);
          int wi = metrics.stringWidth(s);
          int wi2 = wi + metrics.charWidth(' ');
          int de = metrics.getHeight();
          g.fillRect(x+w/2-wi2/2, y +h - de,wi2,de);
          g.setColor(Color.black);
          g.drawString(s,x+w/2-wi/2,y + h - de + metrics.getAscent());
       }
    }
  }
}
