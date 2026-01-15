package shark.games;

import java.awt.*;

import shark.*;

public class pattern extends sharkGame {
  word words[];
  public static final short ALLOCGOOD=15, ALLOCBAD=15;
  final short ENDTIME=1000,ADJUSTTIME=500;
  dword dwords[];
  short wordtot,wtot,curr;
  int xgap;
  FontMetrics m;
  boolean finishing;
  mover headmess;
  boolean wantsay;

  public pattern() {
    errors = true;
    gamescore1 = true;
//    peeps = true;
//    listen= true;
    freepeep = peep = true;
//    wantspeed = true;
    rgame.options |= word.CENTRE;
//    gamePanel.clearWholeScreen = true;
    help(phonics && !phonicsw ? "help_patternph":"help_pattern");
    setupWords();
    buildTopPanel();
    headmess = new heading();
  }
  //----------------------------------------------------------------
  public void peep1() {
    if(headmess == null)  headmess = new heading();
  }
  //-------------------------------------------------------------
  void setupWords()   {
     int x,y;
     short i;
     words = rgame.fullwordlist.getPatternList(rgame.gamename,ALLOCGOOD,ALLOCBAD);
     wordtot = (short)words.length;
     wordfont = sizeFont(words,screenwidth/5,screenheight*wordtot/12);
     m  =  getFontMetrics(wordfont);
     xgap =  m.stringWidth("mm")*mover.WIDTH/screenwidth;

     dwords = new dword[wordtot];
     wtot = 0;
     for(i=0;i<wordtot;++i) {
        addword();
     }
     wordtot = wtot;
   }
  //-------------------------------------------------------------
  void addword() {
     short repeats,i;
     int x,y;
     dword d = new dword(words[curr++]);
     loop1: for(repeats = 0; repeats<200;++repeats) {
        x = u.rand(mover.WIDTH - d.w);
        y = u.rand(mover.HEIGHT - d.h);
        for(i=0;i<wtot;++i) {
           if(dwords[i].x < x+d.w+xgap &&  x < dwords[i].x+dwords[i].w +xgap
              && dwords[i].y < y+d.h &&  y < dwords[i].y+dwords[i].h ) {
                continue loop1;
           }
        }
        addMover((dwords[wtot++] = d),x,y);
        return;
     }
  }
  void testforend() {
     for(short i=0;i<wordtot;++i) {
        if(dwords[i].wd.pat != null && !dwords[i].marked) {
            flip();
            return;
        }
     }
     for(short i=0;i<wordtot;++i) {
        dwords[i].dx = new randrange_base(-mover.WIDTH/100,mover.WIDTH/100,mover.WIDTH/50);
        dwords[i].dy = new randrange_base(-mover.HEIGHT/100,mover.HEIGHT/100,mover.HEIGHT/50);
        dwords[i].keepMoving  = true;
        dwords[i].ended  = false;
     }
     finishing = true;
     score(Math.min(gametot1,8));
     exitbutton(mover.HEIGHT/2);
     gamePanel.makingtrails = true;
  }
  //-------------------------------------------------------------
  class dword extends mover {
     word wd;
     String value;
     boolean patt;
     boolean marked;
     boolean inheap;
     Color endcolor = u.brightcolor();
     randrange_base dx,dy;

     public dword(word v1) {
        super(false);
        wd = v1;
        value = v1.v();
        dontclear=true;
        FontMetrics m = getFontMetrics(wordfont);
        w = (m.stringWidth(value)+1) * mover.WIDTH / screenwidth;
        h = (m.getHeight()+2) * mover.HEIGHT / screenheight;
        patt = wd.pat != null;

     }
     public void paint(Graphics g,int x1, int y1, int w1, int h1) {
       g.setFont(wordfont);
       FontMetrics m = g.getFontMetrics();
       while(m.stringWidth(value) > w1) {
//startPR2007-11-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//         wordfont = new Font(wordfont.getName(),wordfont.getStyle(),wordfont.getSize()-1);
         wordfont = wordfont.deriveFont((float)wordfont.getSize()-1);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         m  =  getFontMetrics(wordfont);
       }
       if(finishing) {
         tox = x = Math.max(0,Math.min(mover.WIDTH-w, x+dx.next(gtime)));
         toy = y = Math.max(0,Math.min(mover.HEIGHT-h, y+dy.next(gtime)));
         x1 = x*screenwidth/mover.WIDTH;
         y1 = y*screenheight/mover.HEIGHT;
       }
       y1 += Math.max(0, h1/2 - m.getHeight()/2 + m.getMaxAscent());
       if(marked) {
           if(wd.pat.length()>0)
             wordlist.paintpat(value,wd.pat,g,finishing?endcolor:Color.black,white(),x1,y1);
           else {
             g.setColor(white());
             g.drawString(value,x1,y1);
           }
       }
       else {
          g.setColor(finishing?endcolor:Color.black);
          g.drawString(value,x1,y1);
       }
     }
     //-------------------------------------------------------------
     public void mouseClicked(int xm, int ym) {
        if(finishing) return;
        if(patt) {
            if(headmess != null) {
               removeMover(headmess);
               headmess = null;
               gamePanel.copyall = true;
            }
            if(!marked) {
               marked = true;
               ended = false;
//startPR2005-04-18^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
               if(Demo_base.isDemo){
                 if (Demo_base.demoIsReadyForExit(0)) return;
               }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
               if(wantsay) wd.say();
               gamescore(1);
               testforend();
            }
        }
        else {
              if(wantsay)wd.say();
              error(value);
              gamescore(-1);
              noise.groan();
        }
     }
   }
   //-----------------------------------------------------------
   class heading extends mover {
      String showit;
      heading() {
         super(false);
         sayit  = sharkStartFrame.currPlayTopic.headings[0];
         showit = spellchange.spellchange(sharkStartFrame.currPlayTopic.headings[0]);
         w = mover.WIDTH;
         h = mover.HEIGHT/8;
         addMover(this,0,mover.HEIGHT/3);
         dontclear=true;
         dontgrabmouse = true;
         if(phonics && !phonicsw) spokenWord.findandsaysentence(sayit);
      }
      public void paint(Graphics g,int x, int y, int w, int h) {
         g.setColor(background);
         g.fillRect(x,y,w,h);
         g.setColor(white());
         g.drawRect(x,y,w,h);
         g.setFont(sizeFont(null,g,showit,w,h));
         FontMetrics m = g.getFontMetrics();
         g.setColor(white());
         u.painthighlight(showit,g,w/2-m.stringWidth(showit)/2,
                            y + h/2 - m.getHeight()/2 );
      }
   }
}
