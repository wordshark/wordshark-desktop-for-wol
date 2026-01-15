package shark.games;

import java.awt.*;
import shark.*;
import shark.sharkGame.*;

public class buildUp extends sharkGame {//SS 03/12/04
  short wordtot,curr=-1;
  static final short MAXWORDS = 6;     // max with splits
  static final short MINWORDS = 1;
  static short MAXSEGSINWORDS = 16;
  static final short PERCOL = 16;     // max down screen
  wholeword words[];
  String segbits[] = new String[0];
  segment segs[],movingseg;
  short ow[],got;
  int maxwidth = mover.WIDTH/6,maxheight= mover.HEIGHT/4;
  boolean seesegs,gotsegs,ending;
  long lastmove;
  short perline,linetot;
  word wd1[];
  long wantnext;

  Color stemcolor = new Color(0,64+u.rand(192),0);
  final short ALLOCGOOD=20, ALLOCBAD=35,SHOWTIME=4000,OKTIME=200;
  final int TWIGINCX = mover.WIDTH/(ALLOCGOOD/3);
  final int TWIGINCX2 = TWIGINCX*2/3;

  final int TWIGINCY = mover.HEIGHT/(ALLOCGOOD/6);
  final int TWIGINCY2 = TWIGINCY*2/3;
  final int DRAWTIME = 2000;
  boolean map[][][];
  int maptot[], maptottot;
  long wantflower;
  boolean showpicture = !specialprivateon;

  public buildUp() {
    errors = true;
    gamescore1 = true;
//    peeps = true;
    listen= true;
//    peep = true;
//    wantspeed=true;
//    gamePanel.setBackground(u.darkcolor());
    wordfont = null;
    pictureat=1;
    if(!setupWords()) return;
    help(blended?"help_buildup1b":"help_buildup1");
    buildTopPanel();
    gamePanel.clearWholeScreen = true;
    nextword();
 }
 //-------------------------------------------------------------
 boolean setupWords() {
    short i,j,k;
    int x1=0,y1=0;
    wholeword ww;
    word wd[] = sharkStartFrame.mainFrame.wordTree.getvisiblewords();
    wd1 = wd;
    wordtot = MAXWORDS;
    short o[] = u.shuffle(u.select((short)wd.length,(short)wd.length));
    short got=0;
    short[] want = new short[wordtot];
    short segsofar = 0;
    if(phonicsw) MAXSEGSINWORDS = 24;
    for(i=0;i<wd.length && got<wordtot && segsofar < MAXSEGSINWORDS;++i)  {
      if(phonicsw) {
        if(wd[o[i]].value.indexOf(u.phonicsplit) > 0  && !wd[o[i]].onephoneme)
          want[got++] = o[i]; segsofar += wd[o[i]].phsegtot();
      }
      else if(wd[o[i]].value.indexOf('/') > 0)  {want[got++] = o[i];segsofar += wd[o[i]].segtot();}
    }
    if(got < MINWORDS && segsofar < MAXSEGSINWORDS) {
       fatalerror(rgame.getParm("notenough"));
       return false;
    }
    wordtot=got;
    o = u.shuffle(u.select(wordtot,wordtot));
    words = new wholeword[wordtot];
    String wholes[] = new String[wordtot];
    for(i=0;i<wordtot;++i) {
       words[i] = ww = new wholeword(wd[want[o[i]]]);
       wholes[i] = wd[want[o[i]]].v();
       if(phonicsw) segbits = words[i].wd.phaddsegs(segbits,false);
       else segbits = u.simplesplit(segbits,words[i].modelval,false);
    }
    perline = (short)((segbits.length + PERCOL-1) / PERCOL + 1);
    linetot = (short)Math.min(PERCOL, (segbits.length + perline-2)/(perline-1));
    wordfont = sizeFont(wholes,   screenwidth*3/4/perline,
                 (screenheight*15/16)/linetot*wholes.length);
    metrics = getFontMetrics(wordfont);
    addsegments();
    ow = u.select((short)segs.length,(short)segs.length);
    return true;
 }
 //--------------------------------------------------------------
 public void afterDraw(long t) {
   if(completed && gtime > wantflower) {
      wantflower = gtime + u.rand(300);
      addflower();
   }
   if(wantnext != 0 && gtime >wantnext) {
      wantnext = 0;
      nextword();
   }
 }
 //-----------------------------------------------------------------
 void nextword() {
    if(++curr < wordtot) {
       currword = words[curr].wd;
//startPR2005-04-18^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        if(Demo_base.isDemo){
          if (Demo_base.demoIsReadyForExit(1)) return;
        }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       if(phonicsw) spokenWord.sayPhonicsWhole(currword);
       else  currword.say();
       if(showpicture)
           new showpicture();
       help(blended?"help_buildup1b":"help_buildup1");
       gamePanel.showSprite = true;
       adjustsegments();
        if(delayedflip && !completed){
          flip();
        }
    }
    else {
          score(gametot1);
//          showmessage("Score " + String.valueOf(gametot1),mover.WIDTH/6,mover.HEIGHT/16,mover.WIDTH*5/6,mover.HEIGHT/5);
          exitbutton(mover.WIDTH*1/20,mover.HEIGHT*4/5);
    }
 }
 //-------------------------------------------------------------------
 void addsegments() {
    short i,j;
    segs = new segment[segbits.length];
    u.sort(segbits);
    int dy = (mover.HEIGHT*15/16)/linetot;
    int dx = mover.WIDTH*3/4/(perline-1);
    int startx = Math.max(mover.WIDTH/4,dx);
    for(i = 0; i< segs.length; ++i) {
       segs[i] = new segment(segbits[i]);
       segs[i].x = startx + dx*(i/linetot);
       segs[i].y = mover.HEIGHT/32 + dy * (i % linetot);
       addMover(segs[i], segs[i].x, segs[i].y);
       gamePanel.puttobottom(segs[i]);
    }
 }
 //---------------------------------------------------------------
 void adjustsegments() {
    String v = words[curr].endval.substring(words[curr].currpos);
    String s = words[curr].wordsegbits[words[curr].currseg];
    for(short i = 0; i< segs.length; ++i) {
       segs[i].tempinactive = (segs[i].active
                              && !s.equals(segs[i].val)
                               && u.startswith(v,segs[i].val));
    }
    gamePanel.copyall = true;
 }
 //---------------------------------------------------------------
 public boolean click(int xx1,  int yy1)  {
   short i,j;
   int x1,y1,x2,y2,h1;
   boolean onebad = false;
   if(curr<0 || completed || wantnext != 0) return false;
   if( words[curr].val == null) {
       for(i=0;i<segs.length;++i) {
         if(segs[i].active && !segs[i].tempinactive
           && segs[i].mouseover()) {
             if(!words[curr].fits(i)) {
                onebad = true;
                return true;
             }
             else {
               if(blended) help("help_buildup2phb");
               else if(phonicsw) help("help_buildup2phw");
               else  help("help_buildup2");
              return true;
             }
         }
      }
   }
   else {
      x2 = words[curr].x + words[curr].w;
      if(phonics && words[curr].wd.phonicsmagic != null && u.inlist(words[curr].wd.phonicsmagic,words[curr].currseg+1)) {
         x2 -= metrics.stringWidth("-e")*mover.WIDTH/screenwidth;
      }
      y1 = words[curr].y;
      h1 = words[curr].h/2;
      for(i=0;i<segs.length;++i) {
         if(segs[i].active  && !segs[i].tempinactive
           &&  (Math.abs(x2 - segs[i].x) < mover.WIDTH/30
                     &&  Math.abs(y1 - segs[i].y) < h1)
                || gamePanel.mousex >= segs[i].x && gamePanel.mousex <= segs[i].x+segs[i].w
                    && gamePanel.mousey >= segs[i].y && gamePanel.mousey <= segs[i].y+segs[i].h) {
             if(!words[curr].fits(i)) {
                onebad = true;
             }
             else return true;
         }
      }
   }
   if(onebad) {
      error();
      if(!phonicsw) noise.groan();
   }
   if(words[curr].val != null) words[curr].mx = - mover.WIDTH/80;
   return true;
 }
 void addflower() {
   int i,j,k;
   if(map==null) {
     map = new boolean[words.length][][];
     maptot = new int[words.length];
     for(k=0;k<words.length;++k) {
       map[k] = word.getpix(words[k].endval, wordfont, metrics, metrics.getMaxAscent());
       for (i = 0; i < map[k].length; ++i) {
         for (j = 0; j < map[k][0].length; ++j) {
           if (map[k][i][j]) {
             ++maptot[k];
             ++maptottot;
           }
         }
       }
     }
   }
   else if(maptottot == 0) return;
   do {k = u.rand(words.length);} while (maptot[k] == 0);
   int sel = u.rand(maptot[k]);
   for(i=0;i<map[k].length;++i) {
     for(j=0;j<map[k][i].length;++j) {
       if(map[k][i][j]) {
          if(sel == 0) {
             map[k][i][j] = false;
             --maptot[k];
             --maptottot;
             new flower(words[k].x + i * mover.WIDTH/screenwidth,  words[k].y+ j * mover.HEIGHT/screenheight,
                   Math.PI*9/8 + u.rand(Math.PI*3/4),k);
             return;
          }
          else --sel;
       }
     }
   }
 }
   //--------------------------------------------------------------
 class segment extends mover {
    String val;
    Color color=Color.black;
    boolean active=true,tempinactive;

    public segment(String s) {
       super(true);
       val = s;
       w = metrics.stringWidth(s)*mover.WIDTH/screenwidth;
       h = metrics.getHeight()*mover.HEIGHT/screenheight;
    }
    boolean mouseover() {
       return gamePanel.mousey >= y  && gamePanel.mousey < y+h
              && gamePanel.mousex > x - mover.WIDTH/8 && gamePanel.mousex < x + w + mover.WIDTH/8;
    }
    public void paint(Graphics g,int x, int y, int w, int h) {
       g.setFont(wordfont);
       if(mouseover() && !tempinactive) {
          g.setColor(color.darker());
          g.drawRect(x-2,y,w+4,h);
       }
       if(shark.macOS) ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
       g.setColor(tempinactive?Color.lightGray:color);
       g.drawString(val, x ,
                y + h/2 - metrics.getHeight()/2 + metrics.getAscent());
       int i;
//       if((i=val.indexOf(' ')) >= 0)
//           g.drawRect(x + metrics.stringWidth(val.substring(0,i)), y,
//                          metrics.charWidth(' '), metrics.getAscent());
     }
  }
 //--------------------------------------------------------------
 class wholeword extends mover {
    String endval,val,modelval;
    word wd;
    boolean finished;
    String wordsegbits[];
    short currpos,currseg;

    public wholeword(word s) {
       super(false);
       int i;
       endval = s.v();
       modelval = phonicsw?s.phsplit():s.vsplit();
       wd = s;
       wordsegbits = phonics?wd.phsegs():u.simplesplit(null,modelval,false);
    }
    boolean fits(int seg) {
       int i,j;
       String newval = (val==null)?segbits[seg]:(val+segbits[seg]);
       if(phonicsw && val !=null && currword.ismagicsyl(currseg+1)
          && val.charAt(i =val.length()-2)=='-') newval = val.substring(0,i) + segbits[seg] + val.substring(i+1);
       int len=newval.length();
       if(endval.equals(newval)) {
          if(phonicsw)  {
            if (!currword.onephoneme)   spokenWord.sayPhonicsBit(currword, currseg);
            new spokenWord.whenfree(100) {
                public void action() {
                  spokenWord.sayPhonicsSyl(currword, currword.fullval(currseg + 1));
                  new spokenWord.whenfree(100) {
                    public void action() {
                      spokenWord.sayPhonicsWhole(currword);
                      wantnext = spokenWord.endsay2 + 1000;
                    }
                  };
                }
            };
           }
          else wantnext = gtime+1000;
          gamePanel.drop(this);
          finished=true;
       }
       else if (!wordsegbits[currseg].equals(segbits[seg])) {
           if(phonicsw)  {
             spokenWord.sayPhonicsBit(segbits[seg], wd1);
           }
           return false;
       }
       else if(phonicsw) {
           spokenWord.sayPhonicsBit(currword, currseg);
           spokenWord.sayPhonicsSyl(currword,currword.fullval(currseg+1));
       }
       ++currseg;
       boolean new1 = (currseg <= 1);
       removeMover(segs[seg]);
       segs[seg].active = false;
       if(phonics && words[curr].wd.phonicsmagic != null && u.inlist(words[curr].wd.phonicsmagic,words[curr].currseg)) {
       }
       else currpos += segbits[seg].length();
       val = newval;
       w = metrics.stringWidth(val)*mover.WIDTH/screenwidth;
       if(new1) {
          int h2;
          h = (h2=metrics.getHeight())*mover.HEIGHT/screenheight;
          addMover(this, segs[seg].x, segs[seg].y);
          if(!finished) {
            gamePanel.attachToMouse(this);
            my = -h;
          }
       }
       else gamePanel.newsprite = true;
       if(finished) {
          gamePanel.puttobottom(this);
          x = Math.min(0,Math.max(mover.WIDTH-w,x));
          gamescore(1);
          for(i=0;i < curr; ++i) {
             if(words[i].x < x+w*3/2
                  && words[i].x + words[i].w*3/2 > x
                  && words[i].y  <  y+h
                  && words[i].y + words[i].h > h) {
                if(y < h || y<mover.HEIGHT-h && u.rand(2)==0)
                             y=words[i].y + words[i].h;
                else y =  words[i].y - h;
             }
          }
       }
       else {
          adjustsegments();
       }
       return true;
    }
    public void paint(Graphics g,int x, int y, int w, int h) {
       short i;
       g.setFont(wordfont);
       g.setColor(completed?Color.black : (finished?background.darker():white()));
       g.drawString(val, x ,
                y + h/2 - metrics.getHeight()/2 + metrics.getMaxAscent());
    }
  }
  //-----------------------------------------------------------
 class flower extends mover {
    int x1,y1,x2,y2;
    double g1,g2,leafangle,leafangle2,g3;
    Polygon p = new Polygon();
    long started = gtime();
    Color color,colormid;
    short petals = (short)(6 + u.rand(6));
    int middle = mover.WIDTH/350 + u.rand(mover.WIDTH/350);
    int red,blue;
    double petstart = u.rand(Math.PI/4);
    int petrad = middle/4 + u.rand(middle);
    int lastw1;
    public flower(int xx1, int yy1, double gg1, int pos) {
      super(false);
      keepMoving=true;
      dontgrabmouse = true;
      dontclear = true;
      x1 = xx1;
      y1 = yy1;
      g1 = gg1;
      do  {
         red = u.rand(256);
         blue = u.rand(256);
      } while(red+blue < 256);
      color = new Color(red,0,blue);
      do  {
         red = u.rand(256);
         blue = u.rand(256);
      } while(red+blue < 256);
      colormid = new Color(red,0,blue);
      g2 =  g1 - Math.PI/8 + u.rand(Math.PI/4);
      x2 =  x1 - mover.WIDTH/50 + u.rand(mover.WIDTH/25);
      y2 =  words[pos].y  - u.rand(mover.HEIGHT/20);
      x = x2 - 4; y=y2-4; w = Math.abs(x2-x1);h = Math.abs(y1-y2);
      u.drawcurve(p,x1*screenwidth/mover.WIDTH,y1*screenheight/mover.HEIGHT,g1,x2*screenwidth/mover.WIDTH,y2*screenheight/mover.HEIGHT,g2);
      w = mover.WIDTH;
      h = mover.HEIGHT;
      addMover(this,x,y);
      gamePanel.bringtotop(ebutton);
   }
   public void paint(Graphics g,int x, int y, int w, int h) {
     if(lastw1 != 0 && lastw1 !=w) {
        for (int i=0;i<p.npoints;++i) p.xpoints[i] = p.xpoints[i]*w/lastw1;
     }
     lastw1 = w;
      long t = gtime();
      int tot=p.npoints, mid = p.npoints/2, midrad;
      int xm = x2*screenwidth/mover.WIDTH;
      int ym = y2*screenheight/mover.HEIGHT;
      int xx,yy;
      int mrad=0;
      int prad=0;
      double angle;

      if(t-started<DRAWTIME/2) {
         tot = (int)(p.npoints * (t-started)/DRAWTIME*2);
         tot = Math.min(tot,p.npoints-1);
      }
      else if(t-started<DRAWTIME) {
         mrad = (int)(middle*(t-started-DRAWTIME/2)/(DRAWTIME/2)*screenwidth/mover.WIDTH);
         prad = (int)(petrad*(t-started-DRAWTIME/2)/(DRAWTIME/2)*screenwidth/mover.WIDTH);
      }
      else {
         keepMoving = false;
         mrad = middle*screenwidth/mover.WIDTH;
         prad = petrad*screenwidth/mover.WIDTH;
      }
      g.setColor(stemcolor);
      g.drawPolyline(p.xpoints,p.ypoints, tot);
      if(t-started>=DRAWTIME/2) {
         g.setColor(color);
         for(short i=0;i<petals;++i) {
            angle = petstart + Math.PI * 2 * i / petals;
            xx = xm + (int)(mrad*Math.cos(angle));
            yy = ym + (int)(mrad*Math.sin(angle));
            g.fillOval(xx-prad, yy-prad, prad*2,prad*2);
         }
         g.setColor(colormid);
         g.fillOval(xm-mrad, ym-mrad, mrad*2,mrad*2);
      }
   }
 }
}
