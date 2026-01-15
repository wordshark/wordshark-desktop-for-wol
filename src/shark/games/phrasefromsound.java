package shark.games;

import java.awt.*;

import shark.*;
import shark.sharkGame.*;

public class phrasefromsound extends sharkGame {//SS 03/12/05
int ptot,wtot,order[],curr=-1;
String words[],wsegs[][];
static final byte LISTTOT = 8;
Font f,ff;
FontMetrics m,mm;
answer answer;
ww ww[];
int maxlen;
int gap, agap;
int choice = 8;
sharkImage tick = sharkImage.random("tick_");
int pherr;
long nextanswer = gtime;

public phrasefromsound() {
  errors = true;
  this.deaths = false;
  gamescore1 = true;
  peeps = true;
  listen= true;
  peep = true;
//    clickonrelease=true;
  rgame.options |= word.CENTRE;
  ptot = rgame.w.length;
  order = u.shuffle(u.select(ptot,ptot));
  int i,j;
  wsegs = new String[ptot][];
  for(i=0;i<ptot;++i) {
    wsegs[i] = u.splitPhrase(rgame.w[i].v());
    for(j=0;j<wsegs[i].length;++j)   words = u.addStringSort(words, wsegs[i][j].toLowerCase());
    choice = Math.max(choice,wsegs[i].length);
  }
  choice = Math.min(choice,words.length);
  wtot = words.length;
  gamePanel.clearWholeScreen = true;
  buildTopPanel();
  help("help_phrasefromsound");
}
void setup() {
  int i,j;
  f = sizeFont(words,screenwidth/5,screenheight/12*words.length);
  Font f2 = sizeFont(rgame.w, screenwidth*5/8, screenheight/12*rgame.w.length);
  if(f2.getSize() < f.getSize()) f = f2;
  ff = sizeFont(rgame.w, screenwidth*3/2, screenheight/3);
  if(ff.getSize() >= f.getSize()) ff = f;
  m = getFontMetrics(f);
  mm = getFontMetrics(ff);
  gap = m.charWidth('w') * mover.WIDTH/screenwidth;
  agap = gap;
  for(i=0;i<wtot;++i) maxlen = Math.max(maxlen,m.stringWidth(words[i]));
  for(i=0;i<rgame.w.length;++i) {
     int segtot = u.count(rgame.w[i].v(),' ');
     while((maxlen+m.charWidth('w'))*segtot > screenwidth*31/32) {
//startPR2007-11-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//       f = new Font(f.getName(), f.getStyle(), f.getSize() - 1);
       f = f.deriveFont((float)f.getSize()-1);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       if (ff.getSize() >= f.getSize())
         ff = f;
       m = getFontMetrics(f);
       mm = getFontMetrics(ff);
       gap = m.charWidth('w') * mover.WIDTH / screenwidth;
       agap = gap;
       maxlen=0;
       for (j = 0; j < wtot; ++j)
         maxlen = Math.max(maxlen, m.stringWidth(words[j]));
     }
  }
  maxlen = maxlen * mover.WIDTH/screenwidth;
  ww = new ww[choice];
}
void setupww() {
  int i,j;
  int wi=0,rowtot=0,startrow=0;
  String list[] = new String[0];
  int o[] = u.shuffle(u.select(wtot,wtot));
  for(i=0;i<answer.segs.length;++i) {
    list = u.addStringSort(list,answer.segs[i].toLowerCase());
  }
  for(i=0;i<o.length && list.length < choice;++i) {
    list = u.addStringSort(list,words[o[i]]);
  }
  for(i=0;i<list.length;++i) {
    ww[i] = new ww(list[i], i);
    if(wi + ww[i].w > mover.WIDTH) {
       int ex = (mover.WIDTH - wi)/(i-startrow);
       int xx = ex/2;
       for(j=startrow; j<i;++j) {
         ww[j].startx = xx;
         xx += ww[j].w + ex;
        }
        ++rowtot;
        wi = 0;
        startrow = i;
    }
    ww[i].startx = wi;
    ww[i].y = rowtot;
    wi += ww[i].w;
  }
  ++rowtot;
  int ex = (mover.WIDTH - wi)/(list.length-startrow);
  int xx = ex/2;
  for(j=startrow; j<list.length;++j) {
    ww[j].startx = xx;
    xx += ww[j].w + ex;
  }
  int gapy = (mover.HEIGHT*3/8 - ww[0].h * rowtot)/rowtot;
  for(i=0;i<choice;++i) addMover(ww[i],ww[i].startx,ww[i].starty =mover.HEIGHT*5/8 + gapy/2 + ww[i].y*(gapy+ww[0].h));
}
public void afterDraw(long t){
  int i;
  if(completed) return;
  if(ww==null) setup();
  if(nextanswer != 0  && gtime > nextanswer) {
    nextanswer = 0;
    if(++curr >= ptot) {
       score(gametot1);
   //    exitbutton(mover.HEIGHT/2);
       exitbutton(mover.WIDTH * 3 / 4, mover.HEIGHT/2);
       String message;
       if(pherr==0) message = rgame.getParm("noerr");
       else if (pherr==1) message = rgame.getParm("oneerr");
       else message = u.edit(rgame.getParm("goterr"),String.valueOf(ptot-pherr),String.valueOf(ptot));
       showmessage(message,mover.WIDTH/4,mover.HEIGHT*5/8,mover.WIDTH*3/4,mover.HEIGHT);
    }
    else {
      answer = new answer(rgame.w[order[curr]], order[curr], curr);
      setupww();
      if(delayedflip && !completed){
          flip();
      }
    }
  }
}
public void listen1() {
   if(currword != null && !completed )   currword.say();
   gamePanel.startrunning();
   gamePanel.requestFocus();
}
class ww extends mover {   // shows word
  int pos,startx,starty;
  String s;

  ww(String s1, int pos1) {
    s = s1;
    pos=pos1;
    w = m.stringWidth(s) * mover.WIDTH/screenwidth + gap*2;
    h = m.getHeight()*mover.HEIGHT/screenheight;
  }
  public void mouseClicked(int x1, int y1) {
    if(!gamePanel.movesWithMouse(this)) gamePanel.moveWithMouse(this);
    else if ( y >= ww[0].starty) {
      gamePanel.drop(this);
      moveto(startx,starty,200);
    }
    else if(x + w >= answer.x && x <= answer.x+answer.w && y+h >= answer.y && y <= answer.y+answer.h) {
        answer.test(this);
    }
  }
  public void paint(Graphics g,int x1, int y1, int w1, int h1) {
     int i;
     String ss;
     if(answer != null && answer.cap) ss = s.substring(0,1).toUpperCase() + s.substring(1);
     else ss = s;
     g.setColor(Color.black);
     if(mouseOver && !gamePanel.movesWithMouse(this)) {
       g.drawRect(x1, y1, w1, h1);
     }
     g.drawString(ss,x1+w1/2-m.stringWidth(ss)/2, y1 + h1/2 - m.getHeight()/2 +m.getAscent());
  }
}
class answer extends mover {
  String segs[],end, whole;
  int pos,acurr,endy;
  boolean cap,done,error;
  showpicture showpic;
  answer(word wd, int pos1,int order) {
      int i;
      pos = pos1;
      segs = u.splitPhrase(whole = wd.v());
      endy = order*h;
      cap = Character.isUpperCase(whole.charAt(0));
      if(cap) segs[0] = segs[0].substring(0,1).toUpperCase() + segs[0].substring(1);
      end = u.endPhrase(wd.v());
      w = maxlen * segs.length + agap*(segs.length-1) + (m.stringWidth(end)+m.getHeight()/2)* mover.WIDTH /screenwidth;
      w = Math.min(w,mover.WIDTH*31/32);
      h = m.getHeight()*mover.HEIGHT/screenheight;
      endy = order*h;
      addMover(this, mover.WIDTH/2 - w/2, Math.max(h*(order+1), mover.HEIGHT/2-h/2));
      currword = rgame.w[pos];
      rgame.w[pos].say();
  }
  void test(ww ww1) {
     int hh = m.getHeight() * mover.HEIGHT / screenheight;
     int www = m.getHeight() * mover.WIDTH / screenwidth;
     int ws = maxlen;
     int gs = (w - hh/2 - ws*segs.length)/(segs.length-1);
     int currstart = tox + www/4 + (maxlen+gs)*acurr;
     int currend = currstart+maxlen;
     int startww = ww1.x+ww1.w/2 - m.stringWidth(ww1.s)/2*mover.WIDTH /screenwidth;
     int endww = startww + m.stringWidth(ww1.s)*mover.WIDTH /screenwidth;
     if(gamePanel.mousex > currstart && gamePanel.mousex < currend
            || startww>=currstart-agap  && endww <= currend + agap) {
          if (ww1.s.equalsIgnoreCase(segs[acurr])) {
            cap = false;
            gamePanel.finishwith(ww1);
            spokenWord.findandsay(ww1.s);
            if(stillwant(ww1)) addMover(ww1,ww1.startx,ww1.starty);  // if used again, put back
            if (++acurr >= segs.length) {
              if(error) ++pherr;
              gamescore(1);
              new spokenWord.whenfree(500) {
                 public void action() {
                    int i;
                    for(i=0;i<choice;++i) removeMover(ww[i]);
                     showpic = new showpicture(0,mover.HEIGHT/2);
                    done = true;
                    moveto(x,endy,1000);
                    rgame.w[pos].say();
                    new spokenWord.whenfree(500) {
                      public void action() {
                         if(showpic != null) showpic.clearold();
                         answer=null;
                         nextanswer = gtime + 1000;
                      }
                    };
                  }
              };
            }
          }
          else {
             noise.groan();
             error(ww1.s);
             answer.error = true;
             gamePanel.drop(ww1);
             ww1.moveto(ww1.startx,ww1.starty,800);
          }
     }
     else {
        noise.groan();
        answer.error = true;
        error();
      }
  }
  boolean stillwant(ww ww) {
     int i,j;
     for(i=acurr+1;i<segs.length;++i) {
       if(segs[i].equalsIgnoreCase(ww.s)) return true;
     }
     return false;
  }
  public void paint(Graphics g,int x1, int y1, int w1, int h1) {
    int i;
    int hh = m.getHeight();
    int xx = x1;
    if(!done) {
      g.setColor(Color.red);
      g.drawRect(x1, y1 - hh / 2, w1, h1 + hh);
      xx += hh/4;
    }
    g.setColor(Color.black);
     if(done) {
       g.setFont(ff);
       g.drawString(whole, xx = x1+w1/2-mm.stringWidth(whole)/2, y1+h1/2-mm.getHeight()/2+mm.getAscent());
//       if(!error) tick.paint(g, xx-h1,y1,h1,h1);
     }
    else {
      g.setFont(f);
      int ws = maxlen * screenwidth / mover.WIDTH;
      int gs = (w1 - hh/2 - ws*segs.length)/(segs.length-1);
      for (i = 0; i < segs.length; ++i) {
        int ww = m.stringWidth(segs[i]);
        if (i >= acurr) {
          g.drawRect(xx, y1, ws, hh);
          if(i==acurr) {
             int xmid = xx +ws/2;
             int rr = hh/4;
             g.fillPolygon(new int[]{xmid-2, xmid-2,xmid - rr,xmid,xmid+rr,xmid+2,xmid+2},
                           new int[]{y1-hh, y1-rr,y1-rr,y1,y1-rr,y1-rr,y1-hh},7);
          }
        }
        else
          g.drawString(segs[i], xx + ws / 2 - m.stringWidth(segs[i]) / 2, y1 + h1/2 - m.getHeight() / 2 + m.getAscent());
        xx += ws + gs;
      }
      if (end.length() > 0) {
        g.drawString(end, xx - gs, y1 + h1/2 - m.getHeight() / 2 + m.getAscent());
      }
    }
  }
}
}
