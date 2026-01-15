package shark.games;

import java.awt.*;

import shark.*;
import shark.sharkGame.*;
import java.util.Arrays;

public class phrasefrompicture extends sharkGame {//SS 03/12/05
  int ptot,wtot,rows,cols,order[],curr=-1;
  String words[];
  Font f,ff;
  FontMetrics m;
  long nextphrase;
  pic currpic;
  int phtot;


  public phrasefrompicture() {
    errors = true;
    gamescore1 = true;
    peeps = true;
    listen= true;
    peep = true;
    nolistenpic = true;
//    clickonrelease=true;
    rgame.options |= word.CENTRE;
    optionlist = new String[]{"phrasefrompicture-phtot"};
    ptot = options.optionval(optionlist[0])+1;
    if(ptot>5) ptot = rgame.w.length;
    else ptot = Math.min(ptot,rgame.w.length);
    order = u.shuffle(u.select(rgame.w.length,ptot));
    int i;
    for(i=0;i<ptot;++i) {
      words = u.addString(words, u.splitString(rgame.w[order[i]].v(),' '));
    }
    wtot = words.length;
    u.sort(words);
    cols = 1 + wtot/20;
    rows = (wtot+cols - 1)/cols;
    gamePanel.clearWholeScreen = true;
    buildTopPanel();
    help("help_phrasefrompicture");
    phtot = options.optionval(optionlist[0])+1;
    setup();
    markoption();
 }
 //---------------------------------------------------------
 public void restart() {
   ptot = options.optionval(optionlist[0])+1;
   if(ptot>5) ptot = rgame.w.length;
   else ptot = Math.min(ptot,rgame.w.length);
   words = null;
   order = u.shuffle(u.select(rgame.w.length,ptot));
   int i;
   for(i=0;i<ptot;++i) {
     words = u.addString(words, u.splitString(rgame.w[order[i]].v(),' '));
   }
   wtot = words.length;
   cols = 1 + wtot/20;
   rows = (wtot+cols - 1)/cols;
   Arrays.sort(words);
   setup();
   markoption();

 }
 void setup() {
    int i;
    f = sizeFont(words,screenwidth/5/cols,screenheight/rows*words.length);
    Font f2 = sizeFont(rgame.w, screenwidth/2, screenheight*rgame.w.length);
    if(f2.getSize() < f.getSize()) f = f2;
    m = getFontMetrics(f);
    ff = sizeFont(rgame.w, screenwidth/4, screenheight*rgame.w.length);
    for(i=0;i<wtot;++i) {
       new ww(i);
    }
    curr = -1;
    nextphrase = gtime;
 }
 public void afterDraw(long t){
   if (nextphrase != 0 && gtime > nextphrase) {
     nextphrase = 0;
     if(++curr >= ptot) {
         this.exitbutton(mover.WIDTH*9/16, mover.HEIGHT/2);
         score(gametot1);
         String message = null;
         if(errortot==0) message = rgame.getParm("noerr");
         if(errortot==1) message = rgame.getParm("oneerr");
         if(errortot==2) message = rgame.getParm("twoerr");
         if(message != null) {
            showmessage(message, mover.WIDTH/4,mover.HEIGHT/8,mover.WIDTH,mover.HEIGHT*3/8);
         }
     }
     else {
       currpic = new pic(order[curr], curr);
       currword.say();
     }
   }
 }
 class ww extends mover {
    String narr;
    ww(int pos) {
       narr = words[pos];
       w = mover.WIDTH/4/cols;
       h = mover.HEIGHT/rows;
       addMover(this,mover.WIDTH*3/4 + pos/rows*(mover.WIDTH/4/cols), pos%rows*(mover.HEIGHT/rows));
    }
    public void mouseClicked(int x, int y) {
      currpic.test(narr,this);
    }
    public boolean endmove()   {
       if(x < mover.WIDTH*3/4) {
         currpic.next();
         return true;
       }
       else return false;
    }
    public void paint(Graphics g,int x1, int y1, int w1, int h1) {
       if(!ismoving() && mouseOver) {
         g.setColor(Color.pink);
         g.fillRect(x1, y1, w1, h1);
       }
       g.setColor(Color.black);
       g.setFont(f);
       g.drawString(narr,x1,y1+h1/2 - m.getHeight()/2 +m.getAscent());
    }
 }
 class pic extends mover {
    sharkImage im;
    String narr,sofar="",want[];
    int pos,endpos,curr, nextx,nexty, nextbit;
    Font pf;
    FontMetrics pm;
    int starth;
    pic(int pos1,int endpos1) {
       pos = pos1;
       endpos = endpos1;
//startPR2009-07-15^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//       im = sharkImage.find((currword = rgame.w[pos]).vpic());
       im = new sharkImage((currword = rgame.w[pos]).vpic(), false);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       narr = rgame.w[pos].v();
       want = u.splitString(narr,' ');
       w = im.w = mover.WIDTH/2;
       im.h = mover.HEIGHT/2;
       im.adjustSize(screenwidth,screenheight);
       starth = h = im.h + m.getHeight();
       addMover(this,mover.WIDTH/4,0);
    }
    public void changeImage(long currtime) {
       if(ismoving()) {
          w = (mover.WIDTH/2 * x + mover.WIDTH/4 * (mover.WIDTH/4 - x) ) / (mover.WIDTH/4);
          h = (starth * x + mover.HEIGHT/ptot * (mover.WIDTH/4 - x) ) / (mover.WIDTH/4);
          pf = ff;
          pm = getFontMetrics(pf);
       }
    }
    void test(String s,ww ww) {
      if(curr==0 && !spokenWord.isfree()) return;     // rb 20-11-07
       if(s.equals(want[curr])) {
          spokenWord.findandsay(ww.narr);
          ww.moveto(nextx*mover.WIDTH/screenwidth, nexty*mover.HEIGHT/screenheight, 200);
          ++curr;
       }
       else {
          noise.groan();
          error(narr);
        }
    }
    void next() {
      if(sofar.length()>0) sofar += " ";
      sofar += want[nextbit++];
      if(curr >= want.length) {
        new spokenWord.whenfree(200) {
          public void action() {
             gamescore(1);
             rgame.w[pos].say();
             new spokenWord.whenfree(100) {
               public void action() {
                 moveto(0, mover.HEIGHT * endpos / ptot, 1000);
                 nextphrase = gtime + 600;
               }
             };
           }
         };
       }
    }
    public boolean endmove()   {
      if(x>0)  return false;
      w = im.h = mover.WIDTH/4;
      h = mover.HEIGHT/ptot;
      pf = ff;
      pm = getFontMetrics(pf);
      return false;
    }
    public void paint(Graphics g,int x1, int y1, int w1, int h1) {
      int i;
      g.setColor(Color.black);
      g.setFont(pf != null? pf : f);
      FontMetrics mm = (pf != null? pm : m);
      int yb = y1+h1-mm.getHeight();
      if(curr==want.length) g.drawString(narr, Math.max(0,x1+w1/2-mm.stringWidth(narr)/2), yb+mm.getAscent());
      else  {
        g.setColor(Color.red);
        int ww = mm.stringWidth(narr);
        g.fillRect(x1+w1/2-ww/2,yb,ww,mm.getHeight());
        nextx = x1 + w1 / 2 - ww/2 + mm.stringWidth(sofar+" ");
        nexty  = yb;
        g.setColor(Color.white);
        g.drawString(sofar+" ?", x1 + w1 / 2 - ww/2, yb + mm.getAscent());
      }
      im.paint(g, x1, y1,w1,yb-y1);
    }
 }

}
