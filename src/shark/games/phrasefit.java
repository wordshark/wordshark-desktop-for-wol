package shark.games;

import java.awt.*;

import shark.*;
import shark.sharkGame.*;

public class phrasefit extends sharkGame {//SS 03/12/05
  int ptot,wtot,order[],curr=-1;
  String words[], distractors[]=new String[0];
  static final byte LISTTOT = 8;
  static final int LEAVEFOR = 800;
  static final int MOVETIME = 2000;
  static final int OBTOT = 6;
  static final short BUGTOT = 20;
  static final int SPEEDX = mover.WIDTH/10;
  static final int SPEEDY = mover.HEIGHT/5;
  static final int UP=0,DOWN=1,RIGHT=2,LEFT=3,NONE=4;
  Font f,ff;
  FontMetrics m;
  flyer flyers[];
  ww ww;
  long startat,newup,newdown;
  boolean newdistractors;
  String hadwords[] = new String[0];
  long screenmove;
  int alt = u.rand(2);
  int obdepth = mover.HEIGHT/40;
  int gapextra = mover.WIDTH/12;
  long wantnext = gtime;
  static final int movetime = 1000;
  boolean anyerror;
  boolean thiserror;

  public phrasefit() {
    errors = true;
    this.deaths = true;
    peeps = true;
    listen= true;
    peep = true;
    wantspeed=true;
    this.gamescore1 = true;
//    clickonrelease=true;
    rgame.options |= word.CENTRE;
    ptot = rgame.w.length;
    order = u.shuffle(u.select(ptot,ptot));
    int i,j;
    for(i=0;i<ptot;++i) {
      String s[] = u.splitPhrase(rgame.w[i].v());
      for(j=0;j<s.length;++j)   words = u.addStringSort(words, s[j]);
    }
    wtot = words.length;
    order = u.shuffle(u.select(rgame.w.length, rgame.w.length));
    gamePanel.clearWholeScreen = true;
    buildTopPanel();
    help("help_phrasefit");
 }
 void setup() {
    int i;
    String ss[] = new String[rgame.w.length];
    for(i=0;i<ss.length;++i) ss[i] = rgame.w[i].v();
    f = sizeFont(words,screenwidth/5,screenheight/20*words.length);
    Font f2 = sizeFont(rgame.w, screenwidth/4, screenheight/20*rgame.w.length);
    if(f2.getSize() < f.getSize()) f = f2;
    m = getFontMetrics(f);
    int obinc = m.getHeight()*2*mover.HEIGHT/screenheight + obdepth ;
    ww = new ww();
    flyers=new flyer[rgame.w.length];
    for(i=0;i<flyers.length;++i) {
       flyers[i] = new flyer(order[i]);
    }
 }
 public void afterDraw(long t){
   if(flyers==null) setup();
   if(wantnext>0 && gtime>=wantnext) {
     wantnext = 0;
     thiserror = false;
     if (++curr == flyers.length) {
       score(gametot1);
       exitbutton(mover.HEIGHT / 2);
       if(!anyerror) showmessage(rgame.getParm("endmess"), mover.WIDTH / 8, mover.HEIGHT * 5 / 8, mover.WIDTH * 7 / 8, mover.HEIGHT * 7 / 8);
     }
     else {
       flyers[curr].start();
        addMover(flyers[curr],flyers[curr].x,flyers[curr].y);
        help("help_phrasefit");
     }
   }
 }
 class ww extends mover {   // shows distractors
    int sel=-1;
    ww() {
       Dimension d = u.getdim(words,m,4);
       w = d.width * mover.WIDTH/screenwidth;
       h = d.height * LISTTOT / words.length*mover.HEIGHT/screenheight;
       mx = - w/2;
       my = - h/2;
    }
    public void mouseClicked(int x1, int y1) {
      sel = (flyers[curr].y2 - y + flyers[curr].h2/2)/(h/distractors.length);
      if(sel >= distractors.length || sel < 0 || Math.abs(flyers[curr].y2 - y - h*sel/distractors.length) > flyers[curr].h2/4) {
        noise.beep();
        return;
      }
      if(sel>=0) {
        flyers[curr].test(distractors[sel]);
      }
    }
    public void changeImage(long currtime) {
       if(completed) kill=true;
       else if(!mouseOver) {
         int mx = gamePanel.mousex;
         int my = gamePanel.mousey;
         if(mx < x) x = tox = mx;
         else if(mx >x+w) x = tox = mx-w;
         if(my < y) y = toy = my;
         else if(my >y+h) y = toy = my-h;
       }
    }
    public void paint(Graphics g,int x1, int y1, int w1, int h1) {
       int i;
       if(distractors==null || distractors.length==0 || flyers[curr].missat < 0) return;
       g.setColor(Color.yellow);
       g.fillRect(x1,y1,w1,h1);
       int incy = h1/distractors.length;
       sel = (flyers[curr].y2 - y + flyers[curr].h2/2)/(h/distractors.length);
       if(sel < distractors.length && sel >= 0 && Math.abs(flyers[curr].y2 - y - h*sel/distractors.length) <= flyers[curr].h2/4) {
         g.setColor(Color.white);
         int hh;
         if(sel==distractors.length-1)
           g.fillRect(x1, y1+sel*incy, w1, hh = h1-sel*incy);
         else g.fillRect(x1, y1+sel*incy, w1, hh = incy);
         g.setColor(Color.black);
         g.drawLine(x1, y1+sel*incy,x1+w1, y1+sel*incy);
         g.drawLine(x1, y1+sel*incy+hh,x1+w1, y1+sel*incy+hh);
       }
       else sel = -1;
       g.setColor(Color.black);
       g.setFont(f);
       for(i=0;i<distractors.length;++i)
          g.drawString(distractors[i],x1+2,y1+incy*i + incy/2 - m.getHeight()/2 + m.getAscent());
    }
 }
 class flyer extends mover {
    String val, part1, part2, fullup,ss[],end;
    String wantup="";
    int missat;
    boolean movemid1,movemid2,moveup,movedown,moveright,moveleft;
    int lastmove = NONE;
    int movey, movex,limity;
    int row;
    int startxx;
    int dir, misspos;
    boolean cap;
    boolean trapping;
    int mvx,mvy;
    Rectangle traparea = new Rectangle();
    sharkpoly shape;
    double angles[] = new double[4];
    int x2,y2,w2,h2;   // bounds of inner rect to hold tect
    int x3,y3;         // starting x,y
    hole hole,hole2;
    long startmove;
    double a;
    boolean done;
    flyer(int pos) {
        int i;
        String missing="";
        val = fullup = rgame.w[pos].v();
        ss = u.splitPhrase(fullup);
        cap = Character.isUpperCase(fullup.charAt(0));
        end = u.endPhrase(fullup);
        int o[] = u.shuffle(u.select(ss.length,ss.length));
        for(i = 0;i<o.length;++i) {
           missing = ss[misspos = o[i]];
           if (u.findString(hadwords, missing) < 0) {
               hadwords = u.addString(hadwords,missing);
                break;
           }
        }
        if(i==o.length) {   // none found - use first as random
          missing = ss[misspos = o[0]];
        }
        fullup = val;
        ss[misspos] = "  ";
        val = u.combineString(ss," ")+ end;
        if(cap && misspos != 0)  val = val.substring(0,1).toUpperCase() + val.substring(1);
        wantup = missing;
        if(cap && misspos==0) wantup = wantup.substring(0,1).toUpperCase() + wantup.substring(1);
        for(missat=i=0;i<misspos;++i) missat += 1+ss[i].length();
        part1 = val.substring(0,missat);
        if(misspos == ss.length-1) part2 = "";
        else part2 = val.substring(missat + ss[misspos].length()+ 1);
        w2 = m.stringWidth(part1+part2+" ")*mover.WIDTH/screenwidth + ww.w;
        h2 = m.getHeight()*mover.HEIGHT/screenheight;
        x2 = mover.WIDTH/2 - w2/2;
        y2 = mover.HEIGHT/2 - h2/2;
        do {
             shape = getshape();
        } while(!okshape());
        Rectangle rr = shape.getBounds();
        x3 = rr.x;
        y3 = rr.y;
        w = rr.width;
        h = rr.height;
        hole = new hole(this);
        x = x3;
        y = y3;
    }
    void start() {
      ww.moveWithin = new Rectangle(x2 + m.stringWidth(part1 + " ")*mover.WIDTH/screenwidth,
                                   y2 + h2 - ww.h, ww.w,  (y2 + ww.h) - (y2 + h2 - ww.h));
      gamePanel.moveWithMouse(ww);
    }
    sharkpoly getshape() {
       int i;
       int xp[] = new int[] {x2,x2+w2,x2+w2,x2};
       int yp[] = new int[] {y2,y2,y2+h2,y2+h2};
       xp[0] -= u.rand(w2/4); yp[0] -= u.rand(h2);
       xp[1] += u.rand(w2/4); yp[1] -= u.rand(h2);
       xp[2] += u.rand(w2/4); yp[2] += u.rand(h2);
       xp[3] -= u.rand(w2/4); yp[3] += u.rand(h2);
       for(i=0;i<4;++i) {
          angles[i] = u.anglebetween(Math.atan2((yp[(i+3)%4] -yp[i])*screenheight,(xp[(i+3)%4] -xp[i])*screenwidth),
                                     Math.atan2((yp[(i+1)%4] -yp[i])*screenheight,(xp[(i+1)%4] -xp[i])*screenwidth));
       }
       return shape = new sharkpoly(xp,yp,4);
    }
    boolean okshape() {
       int i,j,k,m;
       for(i=0;i<flyers.length && flyers[i] != null;++i) {
          for(j = 0; j < 4; ++j) {
            loopk: for(k=0;k<4;++k) {
              for(m=0;m<4;++m) {
                 if (u.anglebetween(flyers[i].angles[(j+m)%4], angles[(k+m)%4]) > Math.PI / 16) continue loopk;
              }
              return false;     // a match to a previous one has been found
            }
          }
       }
       return true;
    }
    public void changeImage(long currtime) {
      if(completed) return;
      int i;
      int oldmisspos = misspos;
      boolean lastcap = cap;
       if(wantup.length()>0
          && (newdistractors || u.findString(distractors,wantup)<0
             || (cap != lastcap || misspos != oldmisspos) && (misspos == 0 || oldmisspos == 0) )) {
           newdistractors = false;
           distractors = new String[]{wantup};
           int o[] = u.shuffle(u.select(wtot,wtot));
           for(i=0;i<o.length && distractors.length < LISTTOT; ++i) {
              distractors = u.addStringSort(distractors,words[o[i]]);
           }
           if(cap && misspos==0) for(i=0;i<distractors.length;++i) {
             distractors[i] = distractors[i].substring(0,1).toUpperCase() + distractors[i].substring(1);
           }
        }
    }
    void test(String s) {
       if(s.equals(wantup) || testother(s)) {
           val = fullup;
           missat = -1;
           gamePanel.finishwith(ww);
           gamePanel.moveWithMouse(this);
           mx = -w/2;
           my = -h/2;
           gamePanel.setCursor(null);
           gamescore(1);
           help("help_phrasefit2");
        }
       else {
          noise.groan();
          error();
          anyerror = true;
          newdistractors = true;
        }
    }
    boolean testother(String s){          // see if happens to fit another phrase
       int i;
       ss[misspos] = s;
       String res = u.combineString(ss," ")+end;
       for(i=0;i<rgame.w.length;++i) {
         if(rgame.w[i].v().equals(res)) {fullup = rgame.w[i].v(); missat = -1; return true;}
       };
       return false;
    }
    public void mouseClicked(int x1, int y1) {
       if(gamePanel.movesWithMouse(this)) {
         int i;
         for (i = 0; i < flyers.length; ++i) {
           if (!flyers[i].hole.full && flyers[i].hole.cshape.contains(gamePanel.mousexs,gamePanel.mouseys)) {
             startmove = gtime;
             hole2 = flyers[i].hole;
             gamePanel.drop(this);
           }
         }
       }
    }
    public void paint(Graphics g,int x1, int y1, int w1, int h1) {
      int i;
      int parttime = 0;
      sharkpoly pp = shape.translate(x-x3,y-y3,screenwidth,screenheight);
      if(startmove != 0) {
         if(gtime >= startmove + movetime) {
              startmove = 0;
              if(hole2 != hole) {
                noise.beep();
                anyerror = true;
                thiserror = true;
                a = 0;
                gamePanel.moveWithMouse(this);
                gamePanel.setCursor(null);
               }
              else {
                if(!thiserror) gamescore(1);
                hole.full = true;
                wantnext = gtime + 1000;
                gamePanel.drop(this);
                done  = true;
                hole.kill=true;
                a  = hole.a;
                sharkpoly pp2 = pp.simplerotate(x1+w1/2,y1+h1/2,a);
                x = tox = x + (hole.cshape.xpoints[0] - pp2.xpoints[0])*mover.WIDTH/screenwidth;
                y = toy = y + (hole.cshape.ypoints[0] - pp2.ypoints[0])*mover.HEIGHT/screenheight;
            }
            hole2 = null;
         }
         else {
             a = hole2.a  * (gtime-startmove)/movetime;
         }
      }
      else if(gamePanel.movesWithMouse(this))    gamePanel.setCursor(null);
      if(a != 0 || done) {
        ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
         java.awt.geom.AffineTransform saveAT = ((Graphics2D)g).getTransform();
         ((Graphics2D)g).rotate(a,x1+w1/2,y1+h1/2);
         g.setColor(Color.black);
         g.setFont(f);
        if(!done) {
           g.setColor(Color.white);
           g.fillPolygon(pp);
           g.setColor(Color.black);
          }
         else {
           g.drawPolygon(pp);
        }
         x1 += (x2 - x3) * screenwidth / mover.WIDTH;
         y1 += (y2 - y3) * screenheight / mover.HEIGHT;
         g.drawString(fullup, x1 + m.charWidth( ' ') / 2, y1 + m.getAscent());
        ((Graphics2D)g).setTransform(saveAT);
        ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);
      }
      else {
        g.setColor(Color.white);
        g.fillPolygon(pp);
        if(missat>=0) {
          g.setColor(Color.black);
          g.drawRect(x2*screenwidth/mover.WIDTH, y2*screenheight/mover.HEIGHT,w2*screenwidth/mover.WIDTH,h2*screenheight/mover.HEIGHT);

        }
        x1 += (x2 - x3) * screenwidth / mover.WIDTH;
        y1 += (y2 - y3) * screenheight / mover.HEIGHT;
        w1 = w2 * screenwidth / mover.WIDTH;
        h1 = h2 * screenheight / mover.HEIGHT;
        g.setColor(Color.black);
        g.setFont(f);
        if (completed)  return;
        if (missat >= 0) {
          g.drawString(part1, ww.moveWithin.x * screenwidth / mover.WIDTH - m.stringWidth(part1), y1 + m.getAscent());
          g.drawString(part2, (ww.moveWithin.x + ww.w) * screenwidth / mover.WIDTH, y1 + m.getAscent());
        }
        else {
          g.drawString(fullup, x1 + m.charWidth(' ') / 2, y1 + m.getAscent());
        }
      }
    }
 }
 class hole extends mover {
    flyer flyer;
    double a;
    int i;
    sharkpoly shape,cshape;
    int lastx1;
    boolean full;
    hole(flyer flyer1) {
      Rectangle middle = new Rectangle(mover.WIDTH/4,mover.HEIGHT*3/8, mover.WIDTH/2,mover.HEIGHT/4);
      Rectangle rr =null;
      flyer = flyer1;
      loop1:while(true) {
         a = Math.PI/16 + u.rand(Math.PI*7/8);
         shape = flyer.shape.rotate(flyer.x2+flyer.w2/2, flyer.y2 + flyer.h2/2,a,screenwidth,screenheight)
                                           .convertback(screenwidth,screenheight);
         rr = shape.getBounds();
         w = rr.width;
         h = rr.height;
         x = u.rand(mover.WIDTH-w);
         y = u.rand(mover.HEIGHT-h);
         sharkpoly pp = shape.translatecopy(x-rr.x, y - rr.y);
         if(pp.intersects(middle))  continue loop1;
         for(i=0;i<flyers.length && flyers[i] != null; ++i) {
            if(flyers[i].hole.shape.intersects(pp)) continue loop1;
         }
         break;
      }
      shape.translate(x-rr.x,y-rr.y);
      addMover(this,x,y);
    }
    public void paint(Graphics g,int x1, int y1, int w1, int h1) {
      if(cshape==null || x1 != lastx1) {
         cshape = shape.convert(screenwidth,screenheight);
         lastx1 = x1;
      }
      g.setColor(Color.black);
      g.drawPolygon(cshape);
      if(full) {
        ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
       java.awt.geom.AffineTransform saveAT = ((Graphics2D)g).getTransform();
        ((Graphics2D)g).rotate(a,x1+w1/2,y1+h1/2);
        g.setColor(Color.black);
        g.setFont(f);
        g.drawString(flyer.fullup, x1 + m.charWidth( ' ') / 2, y1 + m.getAscent());
       ((Graphics2D)g).setTransform(saveAT);
       ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);
      }
    }
 }
}
