package shark.games;

import java.awt.*;

import shark.*;
import shark.sharkGame.*;
public class buckets extends sharkGame {
  word words[][], allwords[] = new word[0];
  boolean haderr[];
  bucket buckets[];
  short wordtot, curr=-1;
  short buckettot;
  short[] order;
  Color bucketcolor = u.darkcolor();
  String headings[], headings2[];
  int maxwordwidth, wmax, wordheight,allowforwords;
  int bthick = mover.HEIGHT/120;
  int margin = mover.WIDTH/40;
  Font f,f2;
  FontMetrics m,m2;
  int lastsel = -1;
  long wantnext;
  mword mword;
  boolean started,starting;
  int starinterval = 1500;
  sharkImage star = sharkImage.random("star_");
  int minstar=4;
  boolean suffix;
  int lastw1;
  boolean fontchanged;
  public buckets() {
    gamePanel.dontstart = true;
    errors = true;
    gamescore1 = true;
    peeps = false;
    listen = false;
    peep = false;
    wantspeed = false;
    pictureat = 2;
    this.wantSprite = false;
     clickonrelease = true;
    buildTopPanel();
    gamePanel.dontstart = false;
    markoption();
  }
//---------------------------------------------------------
  public void afterDraw(long t) {
     int i,j;
     if(buckets==null) {
        setupWords();
        if(!buckets[0].htext) {
          help("help_classify1");  // show examples
          starting = true;
          new ssay(0, false);
        }
     }
//     if(gamePanel.mousemovertot != 0) {
//        mover sel = gamePanel.mousemovers[0];
//        for(i=0;i<buckettot;++i) {
//           if(sel.y > buckets[i].y && sel.y+sel.h < buckets[i].y + buckets[i].h) {
//             if(i != lastsel) {
//               if(lastsel != -1) {
//                 spokenWord.flushspeaker();
//                 buckets[i].sw.say();
//               }
//             }
//             lastsel = i;
//             break;
//          }
//         }
//     }
     if(wantnext != 0 && gtime >= wantnext) {
        wantnext = 0;
        if(++curr >= order.length) {
           score(gametot1);
           exitbutton(mover.WIDTH*6/8,mover.HEIGHT/2);
           String message = null;
           if(minstar == 4) {message = rgame.getParm("perfect");score(3);}
           else if(minstar == 3) {message = rgame.getParm("3star");score(2);}
           else if(minstar == 2) {message = rgame.getParm("2star");score(1);}
           if(message != null) showmessage(message,mover.WIDTH/2,mover.HEIGHT*2/3,mover.WIDTH,mover.HEIGHT*7/8);
           gamePanel.clearall();
        }
        else {

         if(delayedflip && !completed){
          flip();
        }

          word next = allwords[order[curr]];
          loopi: for(i=0;i<words.length;++i) {
             for(j=0;j<words[i].length;++j) {
                if(words[i][j] == next) break loopi;
             }
          }
          gamePanel.mousemoved(screenwidth/3, buckets[buckets.length/2].y*screenheight/mover.HEIGHT);   // for whiteboard
          mword = new mword(buckets[i],next,order[curr]);
          gamePanel.moveWithMouse(mword);
          mword.mx = m.stringWidth("ww")*mover.WIDTH/screenwidth;
          mword.my = -mword.h/2;
          int left = Math.min(buckets[i].left(),mover.WIDTH-mword.w*3/2);
          if(suffix) left = Math.min(left,mover.WIDTH/2);
          mword.moveWithin = new Rectangle(left,0, mover.WIDTH-left,mover.HEIGHT);
        }
     }
  }
  //-------------------------------------------------------------
  void setupWords() {
     short i,j;
     if(rgame.getParm("notextended") == null
         || rgame.fullwordlist.shuffled
         || rgame.fullwordlist.wholeextended)
            words = sharkStartFrame.currPlayTopic.getHeadedWords(new String[] {rgame.gamename});
     else   words = sharkStartFrame.currPlayTopic.getHeadedWords(new String[] {rgame.gamename},rgame.w);
     headings2 = sharkStartFrame.currPlayTopic.headings;
     headings = spellchange.spellchange(headings2);
     for(i=0;i<headings.length;++i)
        headings[i] = headings[i].substring(headings[i].indexOf(':')+1);
     buckettot = (short)words.length;
     buckets = new bucket[buckettot];
     for(i=0;i<buckettot; ++i) {
        wordtot += words[i].length;
        allowforwords += Math.max(2,words[i].length);
        allwords = u.addWords(allwords,words[i]);
        if((j = (short)words[i][0].value.indexOf('+')) > 0 && j <= words[i][0].value.length()-2) suffix = true;
     }
     if(suffix) {
        String allwords2[] = new String[allwords.length];
        for(i=0;i<allwords.length;++i) allwords2[i] = suffix2(allwords[i]);
        f = sizeFont(allwords2,screenwidth/2,(mover.HEIGHT - buckettot*2*bthick)*screenheight/mover.HEIGHT/allowforwords * wordtot);
        m = getFontMetrics(f);
       wmax = u.getdim(allwords2, m, margin*screenheight/mover.HEIGHT).width * mover.WIDTH/screenwidth;
     }
     else {
       f = sizeFont(allwords, screenwidth / 2, (mover.HEIGHT - buckettot * 2 * bthick) * screenheight / mover.HEIGHT / allowforwords * wordtot);
       m = getFontMetrics(f);
       wmax = u.getdim(allwords, m, margin*screenheight/mover.HEIGHT).width * mover.WIDTH/screenwidth;
     }
     wordheight = m.getHeight()*mover.HEIGHT/screenheight;
     for(i=0;i<buckettot; ++i) {
       buckets[i] = new bucket(i);
     }
     if (buckets[0].htext) new start();
     order = u.shuffle(u.select(wordtot,wordtot));
     haderr = new boolean[wordtot];
  }
  //-------------------------------------------------
  String suffix1(word wd) {
   int j,k,m,n;
   String s = wd.value,neww;
   if((j = (short)s.indexOf('+')) < 0 || j > s.length()-2)
            return null;
   if(s.charAt(j+1)=='+') {
      neww = s.substring(0,j) + s.substring(j+1);
   }
   else if((k=s.indexOf('=',j)) >= 0) {
     neww = s.substring(0,k);
   }
   else {
     neww = s;
   }
 return neww;
 }
 String suffix2(word wd) {
  int j,k,mm,n;
  String s = wd.value,neww;
  if((j = (short)s.indexOf('+')) < 0 || j > s.length()-2)
           return null ;
  if(s.charAt(j+1)=='+') {
     neww = s.substring(0,j) + s.substring(j+1) + " = " + s.substring(0,j) + s.substring(j+2);
  }
  else if((k=s.indexOf('=',j)) >= 0) {
    if((mm = s.indexOf('(',k)) >= 0 && (n = s.indexOf(')',mm)) >= 0) {
      neww = s.substring(0,mm);
    }
    else neww = s;
  }
  else {
    neww = s + "=" + u.addsuffix(s.substring(0,j), s.substring(j+1));
  }
  return neww;
}
  //--------------------------------------------------------------
  class bucket extends mover {
    mword model;
    short bno, btot;
    spokenWord sw;
    boolean saying;
    boolean htext;
    int eary;
    public bucket(short bno1) {
       int i;
       bno = bno1;
       int allow = (short)Math.max(2,words[bno].length);
       w = mover.WIDTH;
       h = (mover.HEIGHT-bthick*2*buckettot)*allow/allowforwords + bthick*2;
       if(headings[bno].startsWith("=")) {
          i = headings[bno].indexOf(',');
          model = new mword(this, new word(headings[bno].substring(i + 1), null), 0);
          model.dontgrabmouse = true;
          sw = spokenWord.findandreturn(headings[bno].substring(1, i) + '~', null);
       }
       else {
          if(f2 == null) {
            f2 = sizeFont(headings,screenwidth/2,(mover.HEIGHT - buckettot*2*bthick)*screenheight/mover.HEIGHT/3);
            if(f2.getSize() < f.getSize()) f2 = f;
            m2 = getFontMetrics(f2);
          }
          htext = true;
       }
       y=0;
       for(i=0;i<bno;++i) y += buckets[i].h;
       addMover(this,0,y);
    }
    int left() {
       return  wmax * 2 + margin*3;
    }
    public void paint(Graphics g,int x1, int y1, int w1, int h1) {
       if(lastw1 != 0 && w1 < lastw1 && !fontchanged) {
//startPR2007-11-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//           f = new Font(f.getName(),f.getStyle(),f.getSize()*w1/lastw1);
         f = f.deriveFont((float)f.getSize()*w1/lastw1);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
           m = getFontMetrics(f);
           fontchanged = true;
       }
       lastw1 = w1;
       if(saying) {
         g.setColor(Color.white);
         g.fillRect(x1,y1,w1,h1);
       }
       int thick = bthick*screenheight/mover.HEIGHT;
       g.setColor(saying?Color.red:Color.black);
       g.fillRect(x1,y1,w1,thick);
       g.fillRect(x1,y1+h1-thick,w1,thick+1);
       if(!htext && !starting && !saying && model.moved) {
         findsound.ear.paint(g, x1 + w1 - w1 / 20, eary = y1 + m.getHeight() + thick * 2, w1 / 20, w1 / 20);
       }
       if(htext) {
          g.setFont(f2);
          g.setColor(Color.blue);
          if(headings[bno].indexOf('[') >= 0) {
            u.painthighlight(headings[bno],g, x1+w1-m2.stringWidth(u.striphighlights(headings[bno])),
                             y1+thick);
          }
          else g.drawString(headings[bno],x1+w1-m2.stringWidth(headings[bno]),y1 + m2.getAscent()+thick);
          if(!completed) findsound.ear.paint(g, x1 + w1 - w1 / 20, eary = y1 + m2.getHeight() + thick * 2, w1 / 20, w1 / 20);
       }
    }
    public void mouseClicked(int xm,int ym) {
       if(htext) {
         if(! completed && (!started || overear()))  {
              spokenWord.findandsaysentence(headings[bno]);
        }
       }
       else {
         if(! completed && started && overear())  model.wd.say();
         if (started || starting) return;
         new ssay2(bno, 0);
       }
    }
    boolean overear() {
      return gamePanel.mousex > x + w - w/20
                    && gamePanel.mouseys > eary && gamePanel.mouseys < eary + w / 20 * screenwidth / mover.WIDTH;
    }
  }
  //---------------------------------------------------------------
  class mword extends mover {
     word wd;
     String wds,wdhi,neww;
     bucket b;
     int stars;
     int pos;
     long starttime;
     long killtime;
     boolean moved = false;
     int startx1=gamePanel.mousexs,starty1=gamePanel.mouseys;
     public mword(bucket b1, word wd1,int pos1) {
        super(false);
        pos = pos1;
        b = b1;
        wd = wd1;
        w = m.stringWidth(wds = wd.v())*mover.WIDTH/screenwidth;
        wdhi = wd.vhi();
        h = wordheight;
        starttime = gtime;
        neww = suffix1(wd);
     }
     public void mouseClicked(int xm,int ym) {
       int i;
       for(i=0;i<buckettot;++i) {
         if (y > buckets[i].y && y + h < buckets[i].y + buckets[i].h) {
            if(buckets[i].overear()) {
             buckets[i].mouseClicked(0,0);
             return;
            }
            if(gamePanel.movesWithMouse(this)) {
              if (b == buckets[i]) {
                neww = suffix2(wd);
                if (neww != null)
                  w = m.stringWidth(neww) * mover.WIDTH / screenwidth;
                stars = 4;
                if (haderr[pos])
                  --stars;
                stars = (int) Math.max(0, stars - (gtime - starttime) / starinterval);
                minstar = Math.min(minstar, stars);
                gamePanel.drop(this);
                moveto(margin, b.y + bthick + b.btot * wordheight, 500);
                ++b.btot;
                wantnext = gtime + 500;
                gamescore(1);
                gamePanel.clearall();
              }
              else {
                neww = suffix2(wd);
                if (neww != null)
                  w = m.stringWidth(neww) * mover.WIDTH / screenwidth;
                spokenWord.flushspeaker(true);
                noise.groan();
                error();
                haderr[pos] = true;
                gamescore( -1);
                if (curr >= 0 && curr < order.length - 1) {
                  i = order[curr];
                  System.arraycopy(order, curr + 1, order, curr, order.length - curr - 1);
                  order[order.length - 1] = (short) i;
                  --curr;
                  gamePanel.drop();
                  moveto(margin, b.y + bthick + b.btot * wordheight, 1000);
                  gamePanel.clearWholeScreen = true;
                  killtime = gtime + 2000;
                  wantnext = gtime + 2000;
                }
              }
            }
         }
       }
     }
     public void paint(Graphics g,int x1, int y1, int w1, int h1) {
        if(m.stringWidth(wds) > w1) {
            w = m.stringWidth(wds)*mover.WIDTH/screenwidth + screenwidth;
            gamePanel.copyall = true;
            return;
        }
        if(!moved) {
           if(gamePanel.mousexs != startx1 || gamePanel.mouseys != starty1) {
             moved = true;
             gamePanel.copyall = true;
           }
         }
        if(killtime != 0  && gtime >= killtime) {
          kill=true;
          gamePanel.clearWholeScreen = false;
          return;
        }
        if(!moved && gamePanel.movesWithMouse(this)) {
           g.setColor(Color.white);
           g.fillRect(x1,y1,w1,h1);
        }
        g.setFont(f);
        if(neww != null) {
          g.setColor(Color.black);
          u.painthighlight(neww,g,x1,y1);
        }
        else if(wd.pat != null && wdhi.indexOf('[') < 0) {
           wordlist.paintpat(wds, wd.pat, g,
                    Color.blue, Color.red, x1, y1 + m.getMaxAscent());
        }
        else {
           g.setColor(Color.black);
           u.painthighlight(wdhi,g,x1,y1);
        }
        if(completed && stars>0) {
           int xx = x1 + wmax*screenwidth/mover.WIDTH;
           for(int i=0;i<stars;++i) {
             star.paint(g,xx,y1,h1,h1);
             xx+= h1;
           }
        }
     }
  }
  class ssay extends spokenWord.whenfree {
    int bno;
    boolean wd;
    ssay(int bno1, boolean wd1) {
      super(0);
      bno = bno1;
      wd = wd1;
    }

    public void action() {
      if (!wd) {
        if(bno>0) {
          buckets[bno-1].saying = false;
          if(buckets[bno-1].model != null) buckets[bno-1].model.moveto(mover.WIDTH-margin-buckets[bno-1].model.w, buckets[bno-1].y + bthick, 500);
          if(bno == buckettot) {
            new start();
            gamePanel.clearall();
           return;
          }
        }
        buckets[bno].saying = true;
        if(buckets[bno].model !=null)
            addMover(buckets[bno].model,buckets[bno].x + buckets[bno].w/2 - buckets[bno].model.w/2,
                                    buckets[bno].y + buckets[bno].h/2 - buckets[bno].model.h/2);
        gamePanel.clearall();
        if(buckets[bno].sw != null) buckets[bno].sw.say();
        new ssay(bno, true);
      }
      else {
        if(buckets[bno].model != null && buckets[bno].model.wd != null) buckets[bno].model.wd.say();
        new ssay(bno + 1, false);
      }
    }
  }
  class ssay2 extends spokenWord.whenfree {
   int bno;
   int wd;
   ssay2(int bno1, int wd1) {
     super(0);
     bno = bno1;
     wd = wd1;
   }

   public void action() {
     if (wd == 0) {
       buckets[bno].saying = true;
       buckets[bno].model.moveto(buckets[bno].x + buckets[bno].w / 2 - buckets[bno].model.w / 2,
                                 buckets[bno].y + buckets[bno].h / 2 - buckets[bno].model.h / 2, 0);
       gamePanel.clearall();
       buckets[bno].sw.say();
       new ssay2(bno, 1);
     }
     else
       if (wd == 1) {
         buckets[bno].model.wd.say();
         new ssay2(bno, 2);
       }
       else {
         buckets[bno].saying = false;
         buckets[bno].model.moveto(mover.WIDTH - margin - buckets[bno].model.w, buckets[bno].y + bthick, 500);
         gamePanel.clearall();
       }
   }
 }
 //-------------------------------------------------------
    class start extends mover {
      String message[] = u.splitString(rgame.getParm("start"));
      Font f;
      FontMetrics m;
      public start() {
         super(false);
         help("help_classify3");
         starting = false;
         w = mover.WIDTH/8;
         h = mover.HEIGHT/8;
         x = mover.WIDTH/3 - w/2;
         y = mover.HEIGHT/2- h/2;
         addMover(this,x,y);
      }
      public void mouseClicked(int x1, int y1) {
        if(started) return;
        help(suffix? "help_classify4":(buckets[0].htext?"help_classify5":"help_classify2"));
        wantnext = gtime+500;
        kill = started = true;
      }
      public void paint(Graphics g,int x, int y, int w1, int h1) {
        Rectangle r = new Rectangle(x,y,w1,h1);
        int i;
        if(f==null) {
           f = sizeFont(g,message,w1*3/4,h1*3/4);
           m = getFontMetrics(f);
        }
        g.setFont(f);
        g.setColor(Color.orange);
        g.fillRect(x,y,w1,h1);
        g.setColor(Color.black);
        x += w1/2;
        y += Math.max(0, h1/2 - m.getHeight()*message.length/2 + m.getMaxAscent());
        for(i=0;i<message.length;++i,y += m.getHeight()) {
          g.drawString(message[i], x - m.stringWidth(message[i]) / 2, y);
        }
        u.buttonBorder(g,r,Color.red,!mouseDown);
     }
  }
}
