package shark.games;

import java.awt.*;

import shark.*;
import shark.sharkGame.*;

public class fitbits extends sharkGame {     //SS 03/12/05
  Font f;
  FontMetrics m;
  int wordtot;
  int curr = -1;
  long wantnext = gtime();
  boolean haderr;
  letter letter;
  wordc words[],selected,stillopen;
  String canbemade[] = new String[0];
  static final int PAUSE = 1600;
  static final int PAUSE2 = 1600;
  static final int HOOKTIME = 12000;
  static final int HOOKMIN = 3000, HOOKMAX = 10000;
  int maxlen = 0;
  int totlost;
  long showerr,keepattention;
  fish fishes[] = new fish[0];
  int fisho[] = new int[0];
  boolean open,open2;
  long openuntil;
  hook hook;
  long nexthook = gtime()+ HOOKMIN + u.rand(HOOKMAX - HOOKMIN);
  int lostfish,safefish,totfish;
  int cwidth,lastwidth;

  public fitbits() {
    int i;
    errors = true;
    gamescore1 = false;
    peeps = false;
    listen = false;
    peep = false;
    wantspeed = true;
    gamePanel.setBackground(u.bluecolor());
    gamePanel.clearWholeScreen = true;
    //    clickonrelease=true;
    buildTopPanel();
    help("help_fitbits");
  }

  public void afterDraw(long t) {
    int i,j;
    if(words ==null) {
      wordtot = rgame.w.length;
      for (i = 0; i < wordtot; ++i)
        maxlen = Math.max(maxlen, rgame.w[i].v().length());
      int rows =  (wordtot+1)/2;
      f = sizeFont(rgame.w, screenwidth*3/8, screenheight);
      m = getFontMetrics(f);
      cwidth = u.getadvance(m);
      while (cwidth * maxlen > screenwidth * 3/8) {
//startPR2007-11-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//        f = new Font(f.getName(), f.getStyle(), f.getSize() - 1);
        f = f.deriveFont((float)f.getSize() - 1);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        m = getFontMetrics(f);
        cwidth = u.getadvance(m);
      }
      lastwidth=screenwidth;
      words = new wordc[wordtot];
      String list[] = new String[wordtot];
      for (i = 0; i < wordtot; ++i) {
        words[i] = new wordc(rgame.w[i], i);
        list[i] = rgame.w[i].v();
      }
      String emb[] = u.shuffle(u.embedded(list));
      int got[] = new int[words.length];
      for (i = 0; i < emb.length; ++i) {   // first try for words 4 digits or over
        if(emb[i].length() < 4) continue;
        for(j=0;j<words.length;++j) {
           if(got[j]<1 && words[j].wd.indexOf(emb[i]) >= 0 ) {
             canbemade = u.addString(canbemade, emb[i]);
             ++got[j];
             break;
           }
        }
     }
     for (i = 0; i < emb.length; ++i) {   // first try for 3-digit words
         if(emb[i].length() != 3) continue;
         for(j=0;j<words.length;++j) {
            if(got[j]<1 && words[j].wd.indexOf(emb[i]) >= 0 ) {
              canbemade = u.addString(canbemade, emb[i]);
              ++got[j];
              break;
            }
         }
       }
       for (i = 0; i < emb.length; ++i) {
          if(emb[i].length() != 2) continue;  // then smaller ones
          for(j=0;j<words.length;++j) {
             if(got[j]<1 && words[j].wd.indexOf(emb[i]) >= 0 ) {
               canbemade = u.addString(canbemade, emb[i]);
               ++got[j];
               break;
             }
          }
        }
       String can[] = u.shuffle(u.canbemade(list));
       for (i = 0; i < can.length; ++i) {
          if(can[i].length() < 4) continue;
          for(j=0;j<words.length;++j) {
              if(got[j]<3 && words[j].canfit(can[i]) && u.findString(canbemade,can[i])<0) {
                canbemade = u.addString(canbemade, can[i]);
                ++got[j];
                break;
              }
           }
       }
       for (i = 0; i < can.length; ++i) {
          if(can[i].length() != 3) continue;
          for(j=0;j<words.length;++j) {
              if(got[j]<3 && words[j].canfit(can[i]) && u.findString(canbemade,can[i])<0) {
                canbemade = u.addString(canbemade, can[i]);
                ++got[j];
                break;
              }
           }
       }
       for (i = 0; i < can.length; ++i) {
          if(can[i].length() != 2) continue;
          for(j=0;j<words.length;++j) {
              if(got[j]<3 && words[j].canfit(can[i]) && u.findString(canbemade,can[i])<0) {
                canbemade = u.addString(canbemade, can[i]);
                ++got[j];
                break;
              }
           }
       }
       u.shuffle(canbemade);
       curr = -1;
       wantnext = gtime;
    }
    if(lastwidth != screenwidth) {
      f = sizeFont(rgame.w, screenwidth*3/8, screenheight);
      m = getFontMetrics(f);
      cwidth = u.getadvance(m);
      while (cwidth * maxlen > screenwidth * 3/8) {
//startPR2007-11-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//        f = new Font(f.getName(), f.getStyle(), f.getSize() - 1);
        f = f.deriveFont((float)f.getSize() - 1);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        m = getFontMetrics(f);
        cwidth = u.getadvance(m);
      }
      for (i = 0; i < wordtot; ++i)  words[i].resize();
      lastwidth=screenwidth;
    }
    if(showerr != 0 && gtime > showerr) showerr = 0;
    if(open2 && gtime > openuntil) {
      open2 = false;
    }
    if(nexthook != 0 && gtime > nexthook && !completed) {
       nexthook = 0;
       hook  = new hook();
    }
    if(wantnext != 0 && gtime >= wantnext) {
       wantnext = 0;
       if(curr <= canbemade.length * 3/4) {
           new fish();
           ++totfish;
       }
       if(selected != null) selected.reset();
       if(++curr > canbemade.length * 3/4 && safefish == fishes.length) {
         score(Math.max(3,safefish/3));
         String mess = "";
         if(safefish==0) mess = rgame.getParm("safe0");
         else if(safefish==1) mess = rgame.getParm("safe1");
         else if(safefish>1) mess = u.edit(rgame.getParm("safe"),String.valueOf(safefish));
         if(lostfish == 0) mess += "|"+rgame.getParm("lost0");
         else if(lostfish==1) mess += "|" + rgame.getParm("lost1");
         else if(lostfish>1) mess += "|" +  u.edit(rgame.getParm("lost"),String.valueOf(lostfish));
         if(lostfish == 0) mess += "|"+rgame.getParm("brilliant");
         else if(lostfish < 4) mess += "|"+rgame.getParm("good");
         for(i=0;i<wordtot;++i) words[i].moveto(0,words[i].y,800);
         clearexit = true;
         showmessage(mess,mover.WIDTH/2,mover.HEIGHT/4,
                             mover.WIDTH, mover.HEIGHT/2);
         exitbutton(mover.WIDTH*2/3,mover.HEIGHT*3/4);
         return;
       }
       letter = new letter(canbemade[curr % canbemade.length]);
       spokenWord.findandsay(canbemade[curr % canbemade.length]);
    }
  }
  void keepattention() {
    keepattention = gtime + 2000 + (8-speed) * 500;

  }
  class wordc extends mover {
     word word;
     String wd;
     char cc[];
     int p,pos=1;
     int badtot;
     long saying;
     boolean got[],done;
     int lost;
     boolean first=true;
     wordc(word wr, int pp) {
       int i,j;
       p = pp;
       word = wr;
       wd = wr.v().toLowerCase();
       cc = wr.v().toCharArray();
       got = new boolean[cc.length];
       w = cc.length * cwidth * mover.WIDTH/screenwidth;
       h = mover.HEIGHT / wordtot;
       x = mover.WIDTH/2 - cwidth* maxlen * mover.WIDTH/screenwidth /2;
       y =  h * pp;
       if(pp == wordtot-1) h = mover.HEIGHT - y ;
       addMover(this,x,y);
     }
     void resize() {
       w = cc.length * cwidth * mover.WIDTH/screenwidth;
     }
     void testfit() {
        int i,j,k;
        String ch = letter.s.toLowerCase();
        int ww = cwidth*mover.WIDTH/screenwidth;
        int pos;
        if(gamePanel.mousex > x+ww/4 && gamePanel.mousex < x + w - ww/4   // white board - allow touch in middle
            && (gamePanel.mousex - x)%ww >ww/4 && (gamePanel.mousex - x)%ww <= ww*3/4)
            pos = (gamePanel.mousex - x)/ww;
        else pos = gamePanel.mousex > x -  ww/2 ? Math.min(cc.length-1,(gamePanel.mousex + ww/2 - x)/ww)
            : - (x - gamePanel.mousex + ww/2)/ww;
        boolean gotone = false;
        String newch = ch;
        for(i=pos,j=0,k=0; i < cc.length && j < ch.length(); ++i,++j,++k) {
          if(i>=0 && !got[i] && wd.charAt(i) == ch.charAt(j)) {
            got[i] = true;
            gotone = true;
            newch = newch.substring(0, k) + newch.substring(k + 1);
            --k;
          }
        }
        if(!gotone) {
           error();
           noise.groan();
           haderr=true;
           showerr = gtime + 2000;
           return;
        }
        gamePanel.finishwith();
        if(newch.length() == 0) {
           open = true;
           wantnext = gtime + PAUSE;
        }
        else {
          letter = new letter(newch);
          first = false;
        }
     }
     void reset() {
       first  = true;
       selected = null;
       got=new boolean[cc.length];
       haderr = false;
       open=false;
       open2 = true;
       openuntil = gtime + PAUSE2;
     }
     boolean canfit(String ss) {
        int i,j;
        String s = wd.toLowerCase();
        String s2 = ss.toLowerCase();
        for(i=0;i<s2.length();++i) {
           if((j=s.indexOf(s2.charAt(i))) < 0) return false;
           s = s.substring(0,j)+s.substring(j+1);
        }
        return true;
     }
     boolean overword() {
       int allow = cwidth*mover.WIDTH/screenwidth;
       int allowy = m.getHeight()*mover.HEIGHT/screenheight/2;
       return letter.x + letter.w >= x - allow/4 && letter.x <= x+w-allow/4
               && Math.abs(letter.y + letter.h/2 - y - h/2) < allowy
               || mouseOver;
     }
     boolean near() {
       int allow = cwidth*mover.WIDTH/screenwidth;
       return letter.x + letter.w  > x - allow && letter.x <= x+w+allow
               && letter.y >= y && letter.y + letter.h < y+h;
     }
     public void paint(Graphics g, int x1, int y1, int w1, int h1) {
       int ww = cwidth,i,xx=x1;
       if(selected==null || this != selected || this == selected && !open) {
         g.setColor(Color.black);
         if(open2 && stillopen == this) g.fillRect(xx, y1, (int)(w1*(gtime-openuntil+PAUSE2)/PAUSE2), h1);
         else g.fillRect(xx, y1, w1, h1);
       }
       g.setFont(f);
       for(i=0;i<cc.length;++i,xx+=ww) {
         g.setColor(selected != null && this !=selected ? Color.gray:Color.white);
         if(open && !got[i]) g.setColor(Color.gray);
         if(selected == null && !near() || selected != null && this != selected
                                   || got[i] || open || completed || showerr != 0)
           g.drawChars(cc,i,1,xx+ww/2-m.charWidth(cc[i])/2,
                                y1+h1/2-m.getHeight()/2 + m.getAscent());
       }
       g.setColor(gamePanel.getBackground());
       g.drawRect(x1,y1,w1,h1);
        if( selected == null && near() || selected != null && this == selected) {
         int hh = m.getHeight();
         int yy = y1 + h1 /2 - hh/2;
         g.setColor(Color.white);
         for(i=0,xx=x1;i<cc.length;++i,xx+=ww) {
           g.drawRect(xx,yy,ww,hh);
         }
       }
     }
  }
  class letter extends mover {
     char  c[];
     String s;
     showpicture sp;
     int lastw1;
     letter(String ss) {
       s = ss;
       c = ss.toCharArray();
       int i,j,tot=0,tot2=0;
       w = cwidth*c.length*mover.WIDTH/screenwidth;
       h = m.getHeight()*mover.HEIGHT/screenheight;
       gamePanel.moveWithMouse(this);
       letter = this;
       x = mover.WIDTH-w;
       if(selected != null)
         gamePanel.mousemoved((selected.x+selected.w)*screenwidth/mover.WIDTH,selected.y*screenheight/mover.HEIGHT);    // for touch screen
       else {
         i = words.length / 2;
         while (i > 0 && words[i].x + words[i].w >= x)
           --i;
         while (i < words.length - 1 && words[i].x + words[i].w >= x)
           ++i;
         gamePanel.mousemoved(x * screenwidth / mover.WIDTH, words[i].y * screenheight / mover.HEIGHT); // for touch screen
       }
       keepattention();
     }
     public void mouseClicked(int xx, int yy) {
        int i;
        if(showerr != 0 || open2) return;
        if(selected == null) {
          for(i=0;i<words.length;++i) {
            if (words[i].overword()) {
              if (words[i].canfit(letter.s)) {
                stillopen = selected = words[i];
                words[i].word.say();
              }
              else {
                 noise.groan();
                 error();
                 return;
              }
             }
          }
        }
        if(selected != null) selected.testfit();
     };
     public void paint(Graphics g, int x1, int y1, int w1, int h1) {
       if(w1 != lastw1) {
         w =  cwidth*c.length*mover.WIDTH/screenwidth;
         lastw1 = w1;
       }
       int i, xx = x1, ww = cwidth;
        g.setColor(Color.yellow);
        g.setFont(f);
        for(i=0;i<c.length;++i) {
          g.drawChars(c, i, 1, xx+ww/2-m.charWidth(c[i])/2,
                          y1 + h1 / 2 - m.getHeight() / 2 + m.getAscent());
          xx += ww;
        }
     }
  }
  class fish extends mover {
     sharkImage im = sharkImage.random("fitbits_fish_");
     randrange_base sprr = new randrange_base(mover.WIDTH/400,mover.WIDTH/200,mover.WIDTH/400);
     randrange_base rrx = new randrange_base(-mover.WIDTH/200,mover.WIDTH/200,mover.WIDTH/400);
     randrange_base rry = new randrange_base(-mover.HEIGHT/400,mover.HEIGHT/400,mover.HEIGHT/400);
     long lasttime=gtime;
     double dir;
     boolean left=true;
     int pos;
     fish() {
       im.w = mover.WIDTH/16;
       im.h = mover.HEIGHT/16;
       im.rotates = true;
       im.adjustSize(screenwidth,screenheight);
       int ww = Math.max(im.w*screenwidth/mover.HEIGHT,im.h*screenheight/mover.HEIGHT);
       w = ww*mover.WIDTH/screenwidth;
       h = ww*mover.HEIGHT/screenheight;
       addMover(this,0,u.rand(mover.HEIGHT-h));
       fish tfish[] = new fish[fishes.length+1];
        System.arraycopy(fishes, 0, tfish, 0, fishes.length);
        fishes = tfish;
        fishes[fishes.length-1]=this;
        pos = fishes.length-1;
     }
     public void changeImage(long time) {
       int i,j,tx=0, ty=0;
       boolean rand = false;
       boolean intunnel = false;
       int elapsed = (int)(gtime-lasttime);
       double maxturn =  Math.PI/4*elapsed/100000;
       if (left && open) {
         if(x < selected.x) {
           if (y >= selected.y && y + h < selected.y + selected.h) {
             tx = selected.x + selected.w + w;
             intunnel = true;
             ty = y;
           }
           else {
             tx = selected.x - w ;
             ty = selected.y + selected.h/2 - h/2 ;
           }
         }
         else if(x < selected.x+selected.w) {
             tx = selected.x + selected.w + w;
             ty = y;
             intunnel = true;
         }
         else {
             intunnel=false;
             left = false;
             ++safefish;
             rand = true;
         }
       }
       else if(left && open2 && x > stillopen.x) {
           if(x < stillopen.x+stillopen.w) {
               tx = stillopen.x + stillopen.w + w;
               ty = y;
               intunnel = true;
           }
           else {
               intunnel=false;
               left = false;
               ++safefish;
               rand = true;
           }
       }
       else if(gtime>keepattention && left && hook != null && hook.fish == null) {
           tx = (hook.im.lefttoright?hook.x+hook.w*3/4 : hook.x+hook.w/4) - w + u.rand(w);
           ty = hook.y + hook.h/2 - h + u.rand(h);
       }
       else {
         if(x < words[0].x) {
           tx = Math.min(gamePanel.mousex, words[0].x - w*2 + u.rand(w));
           ty = gamePanel.mousey-h/2 + u.rand(h);
         }
         else rand = true;
       }
       if(rand) {
         int dx = rrx.next(gtime);
         int dy = rry.next(gtime);
         x += dx;
         y += dy;
         for (i = 0; i < words.length; ++i) {
           if (y + h > words[i].y && y < words[i].y + words[i].h) {
             if (x < words[i].x + words[i].w) {
               x = words[i].x + words[i].w;
               rrx.setcurr(dx = Math.abs(dx));
             }
           }
         }
         if (x + w > mover.WIDTH) {
             x = mover.WIDTH - w;
             rrx.setcurr( dx = -Math.abs(dx));
         }
         if(y<0) {
            y = 0;
            rry.setcurr(dy = Math.abs(dy));
          }
          if(y> mover.HEIGHT-h) {
            y = mover.HEIGHT-h;
            rry.setcurr(dy =-Math.abs(dy));
          }
          dir = Math.atan2(dy,dx);
          tox = x;
          toy = y;
       }
       else {
         int sp = sprr.next(gtime);
         if (intunnel)
           sp *= 3;
         double newdir = Math.atan2(ty - y, tx - x);
         if (u.anglebetween(newdir, dir) < maxturn) {
           dir = newdir;
         }
         else
           if (u.anglebetween(newdir, u.normalrange(dir + maxturn)) <
               u.anglebetween(newdir, u.normalrange(dir - maxturn)))
             dir = u.normalrange(dir + maxturn);
           else
             dir = u.normalrange(dir - maxturn);
         x = tox = Math.max(0,
                            Math.min(mover.WIDTH - w,
                                     x + (int) (sp * Math.cos(dir))));
         y = toy = Math.max(0,
                            Math.min(mover.HEIGHT - h,
                                     y + (int) (sp * Math.sin(dir))));
         if (intunnel) {
           y = toy = Math.max(stillopen.y,
                              Math.min(stillopen.y + stillopen.h - h, y));
         }
         else {
           for (i = 0; i < words.length; ++i) {
             if (y + h > words[i].y && y < words[i].y + words[i].h) {
               if (!left)
                 x = tox = Math.max(words[i].x + words[i].w, x);
               else
                 x = tox = Math.min(words[i].x - w, x);
             }
           }
         }
         int midx = (x + w / 2) * screenwidth / mover.WIDTH;
         int midy = (y + h / 2) * screenheight / mover.HEIGHT;
         int dx, dy, fmidx, fmidy;
         int dr = w * screenheight / mover.HEIGHT * h * screenheight /
             mover.HEIGHT;
         double mdir = 0;
         for (i = 0; i < pos; ++i) {
           fish ff = fishes[i];
           if (ff == this)
             continue;
           fmidx = (ff.x + ff.w / 2) * screenwidth / mover.WIDTH;
           fmidy = (ff.y + ff.h / 2) * screenheight / mover.HEIGHT;
           dx = (midx - fmidx);
           dy = (midy - fmidy);
           if (dx * dx + dy * dy < dr) {
             if (mdir == 0)
               mdir = Math.atan2(dy, dx);
             for (j = 1; j < screenwidth / 100; ++j) {
               tox = x + (int) (j * Math.cos(mdir) * mover.WIDTH / screenwidth);
               toy = y + (int) (j * Math.sin(mdir) * mover.HEIGHT / screenheight);
               midx = (tox + w / 2) * screenwidth / mover.WIDTH;
               midy = (toy + h / 2) * screenheight / mover.HEIGHT;
               dx = (midx - fmidx);
               dy = (midy - fmidy);
               if (dx * dx + dy * dy >= dr)
                 break;
               if (left && !intunnel && x + w > words[0].x &&
                   mdir > -Math.PI / 2 && mdir < Math.PI / 2) {
                 mdir = mdir + Math.PI;
                 j = 0;
               }
             }
             x = tox;
             y = toy;
             if (intunnel) {
               y = toy = Math.max(stillopen.y,
                                  Math.min(stillopen.y + stillopen.h - h, y));
             }
             else {
               for (j = 0; j < words.length; ++j) {
                 if (y + h > words[j].y && y < words[j].y + words[j].h) {
                   if (!left)
                     x = tox = Math.max(words[j].x + words[j].w, x);
                   else
                     if (!intunnel)
                       x = tox = Math.min(words[j].x - w, x);
                 }
               }
             }
           }
         }
         if (hook != null && hook.fish == null && gtime > keepattention) {
           int mouthx = x + w / 2 + (int) (w / 2 * Math.cos(dir));
           int mouthy = y + h / 2 + (int) (h / 2 * Math.sin(dir));
           if (hook.im.lefttoright &&
               Math.abs(mouthx - hook.x - hook.w / 4) < w / 8 &&
               Math.abs(mouthy - (hook.y + hook.h / 2)) < h / 8
               ||
               !hook.im.lefttoright &&
               Math.abs(mouthx - (hook.x + hook.w * 3 / 4)) < w / 8
               && Math.abs(mouthy - (hook.y + hook.h / 2)) < h / 8) {
             hook.fish = this;
             kill = true;
             fish tfish[] = new fish[fishes.length - 1];
             System.arraycopy(fishes, 0, tfish, 0, pos);
             if (pos < fishes.length - 1)
               System.arraycopy(fishes, pos + 1, tfish, pos,
                                fishes.length - pos - 1);
             fishes = tfish;
             nexthook = gtime + HOOKMIN + u.rand(HOOKMAX - HOOKMIN);
             hook.moveto(hook.x, -hook.h, 2000);
             hook.temp = true;
             ++lostfish;
             for (i = pos; i < fishes.length; ++i)
               fishes[i].pos = i;
             noise.scream();
           }
         }
       }
     }
     public void paint(Graphics g, int x1, int y1, int w1, int h1) {
       if(dir > Math.PI/2 || dir < -Math.PI/2) {
         im.lefttoright = true;
         im.angle =  Math.PI - dir;
       }
       else {
         im.lefttoright = false;
         im.angle =  dir;
       }
       im.paint(g,x1,y1,w1,h1);
       if(hook != null && hook.fish==null) {
         int mouthx = x1+w1/2 + (int) (w1/3 * Math.cos(dir));
         int mouthy = y1+h1/2 + (int) (h1/3 * Math.sin(dir));
         int hx = (hook.im.lefttoright ? hook.x+hook.w :hook.x)*screenwidth/mover.WIDTH;
       }
     }
  }
  class hook extends mover {
     fish fish;
     sharkImage im = sharkImage.random("fitbits_hook_");
     Polygon line;
     long stayuntil = gtime + HOOKTIME;
     hook() {
        im.w = mover.WIDTH/40;
        im.h = mover.HEIGHT/20;
        im.adjustSize(screenwidth, screenheight);
        w = im.w;
        h = im.h;
        im.lefttoright = u.rand(2)==0;
        addMover(this,w*3 + u.rand(words[0].x/2),0);
        moveto(x,mover.HEIGHT/8 + u.rand(mover.HEIGHT/2), 2000);
        if(u.rand(2) == 0) im.lefttoright = true;
        hook = this;
      }
     public void paint(Graphics g, int x1, int y1, int w1, int h1) {
       im.paint(g,x1,y1,w1,h1);
       g.setColor(Color.black);
       Rectangle rr = im.getRect(0);
       g.drawLine(rr.x+rr.width/2,rr.y,rr.x+rr.width/2,0);
       if(gtime > stayuntil && fish==null) {
          temp = true;
          moveto(x, -h, 500);
          hook = null;
          nexthook = gtime + HOOKMIN + u.rand(HOOKMAX - HOOKMIN);
      }
       if(fish != null) {
          fish.im.angle = -Math.PI*3/4 + u.rand(Math.PI/2);
          fish.im.lefttoright = u.rand(2)==0;
          int hookx = im.lefttoright?x1+w1*3/4:x1+w1/4;
          int hooky = y1 + h1/2;
          int ww = fish.w*screenwidth/mover.WIDTH;
          int midx,midy;
          if(fish.im.lefttoright) {
            midx = hookx + (int) (ww / 4 * Math.cos(fish.im.angle));
            midy = hooky - (int) (ww *3/8 * Math.sin(fish.im.angle));
          }
          else  {
           midx = hookx - (int) (ww / 4 * Math.cos(fish.im.angle));
           midy = hooky - (int) (ww *3/8 * Math.sin(fish.im.angle));
          }

          fish.im.paint(g,midx-ww/2,midy-ww/2,ww,ww);
       }
     }
  }
}
