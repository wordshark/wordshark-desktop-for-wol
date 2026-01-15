package shark.games;

import java.awt.*;
import shark.*;
import javax.swing.*;
import java.awt.print.*;
import java.util.*;

public class flowers extends sharkGame  implements Printable  {//SS 03/12/05
  private int lastPage = -1;
  boolean printing;
  int printtot;
  short patlen;
  word words[];
  final short ALLOCGOOD=20, ALLOCBAD=35,SHOWTIME=4000,OKTIME=200;
  final int TWIGINCX = mover.WIDTH/(ALLOCGOOD/3);
  final int TWIGINCX2 = TWIGINCX*2/3;

  final int TWIGINCY = mover.HEIGHT/(ALLOCGOOD/6);
  final int TWIGINCY2 = TWIGINCY*2/3;
  final int LEFTLIM = mover.WIDTH/20;
  final int RIGHTLIM = mover.WIDTH - LEFTLIM;
  final int TOPLIM = mover.HEIGHT/10;
  int BOTLIM = mover.HEIGHT - TOPLIM;
  final int DRAWTIME = 2000;
  final int SHOWWORDS = 12;
  dword dwords[];
  short wordtot,curr,heaptot,patttot;
  int xgap, xleft, ytop,xright,ybottom,xnext,ynext;
  FontMetrics m;
  boolean ending,goneround;
  int px = 0;
  int py = mover.HEIGHT;
  double pg = -Math.PI/2;
  long nextadd;
  Color stemcolor = new Color(0,64+u.rand(192),0);
  short showingwords,gotwords;
  boolean endmessup;
  heading headmess;
  byte inseg;

  public flowers() {
    errors = true;
    gamescore1 = true;
//    peeps = true;
//    listen= true;
    freepeep = peep = true;
//startPR2006-04-06^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    clickenddrag = false;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    wantspeed = true;
//    wantsPrint = true;
    rgame.options |= word.CENTRE;
    help(phonics && !phonicsw ?"help_flowersph":"help_flowers");
    setupWords();
    buildTopPanel();
    gamePanel.clearWholeScreen = true;
    headmess = new heading();
  }
  //-------------------------------------------------------------
  void setupWords()   {
     int x,y;
     short i;
     words = rgame.fullwordlist.getPatternList(rgame.gamename,ALLOCGOOD,ALLOCBAD);
     wordtot = (short)words.length;
     wordfont = sizeFont(words,screenwidth/5,screenheight*words.length/10);
     m  =  getFontMetrics(wordfont);
     xgap =  m.stringWidth("mm")*mover.WIDTH/screenwidth;
     xleft = xnext = LEFTLIM*2;
     ytop = ynext = TOPLIM*2;

     dwords = new dword[wordtot];
     for(i=0;i<wordtot;++i) {
        dwords[i] = new  dword(words[i],i);
     }
  }
  //--------------------------------------------------------------
  public void afterDraw(long t) {
     dword d;
     short i;
     String message;
     if(endmessup || printing) return;
     if(ending || goneround) {
        for(i=0;i<wordtot;++i) {
           d = dwords[i];
           if(d.visible) {
              gamePanel.removeMover(d);
              d.visible = d.selected = false;
           }
        }
        clearexit=true;
        if(goneround) {
           message = rgame.getParm("gotall");
           score(Math.min(gametot1,12));
           for(i=(short)(u.rand(4));i>=0;--i)
                  new treefrog();
        }
        else if(inseg == 3) {
           message = rgame.getParm("gotthreeq");
          score(Math.min(gametot1,8));
        }
        else if(inseg == 2) {
           message = rgame.getParm("gothalf");
           score(Math.min(gametot1,4));
        }
        else {
           cancelreward = true;
           message = rgame.getParm("gotunderhalf");
           score(Math.min(gametot1,1));
        }
        showmessage(message,LEFTLIM*3,TOPLIM*3, mover.WIDTH-LEFTLIM*3,mover.HEIGHT*2/3);
        exitbutton(mover.HEIGHT*2/3);
        endmessup = true;
        return;
     }
     if(t > nextadd || curr<0) {
        if(++curr >= wordtot + SHOWWORDS) {ending = true;  return;}
        if(curr < wordtot) {
           addword(dwords[curr]);
           ++showingwords;
        }
        if(curr - SHOWWORDS > 0) {
           removeMover(d=dwords[curr-SHOWWORDS-1]);
           d.visible = d.selected = false;
           --showingwords;
        }
        boolean notend = curr < wordtot+1;
        for(i=(short)Math.max(curr-SHOWWORDS,0);i<Math.min(curr,wordtot);++i) {
           dwords[i].color = new Color(background.getRed()*(curr-i)/SHOWWORDS,
                                       background.getGreen()*(curr-i)/SHOWWORDS,
                                       background.getBlue()*(curr-i)/SHOWWORDS);
           if(curr-i > SHOWWORDS*2/3) dwords[i].missed=true;
           dwords[i].ended = false;
           if(dwords[i].wd.pat != null && !dwords[i].selected && !dwords[i].missed)
                    notend = true;
        }
        if(!notend) ending = true;
        nextadd = t + 500 + 300*(8-speed);
     }
  }
  //-------------------------------------------------------------
  void addword(dword d) {
       if(xnext + d.w + xgap > mover.WIDTH  - xleft) {
           xnext = xleft;
           if((ynext += d.h) > mover.HEIGHT - ytop) ynext = ytop;
        }
        d.color = Color.black;
        addMover(d,xnext,ynext);
        xnext +=  d.w + xgap;
  }
  //----------------------------------------------------------------
  public void peep1() {
    if(headmess == null)  headmess = new heading();
  }
  //-------------------------------------------------------------
  class dword extends mover {
     String value;
     word wd;
     long time;
     boolean selected, visible, missed;
     short which;
     Color color;

     public dword(word v1,short wh) {
        super(false);
        wd = v1;
        value = v1.v();
        which = wh;
        w = (m.stringWidth(value)+1) * mover.WIDTH / screenwidth;
        h = (m.getHeight()+2) * mover.HEIGHT / screenheight;
     }
     public void paint(Graphics g,int x, int y, int w, int h) {
       g.setFont(wordfont);
       FontMetrics m = g.getFontMetrics();
       y += Math.max(0, h/2 - m.getHeight()/2) + m.getMaxAscent();
       if(wd.pat != null  && wd.pat.length()>0 && (selected )) {
          wordlist.paintpat(value,wd.pat,g,color,white(),x,y);
       }
       else if(wd.pat != null  && wd.pat.length()>0 && missed) {
          wordlist.paintpat(value,wd.pat,g,color,Color.red,x,y);
       }
       else {
          g.setColor(selected ?white():(wd.pat != null && missed?Color.red:color));
          g.drawString(value, x, y);
       }
     }
     //-------------------------------------------------------------
     public void mouseClicked(int xm, int ym) {
         if(selected || missed) return;
        if(wd.pat  !=  null) {
            gamescore(1);
            selected = true;
            if(headmess != null) {
               removeMover(headmess);
               headmess = null;
            }
            ended = false;
            ++gotwords;
            time = gtime()+OKTIME;
            plantseg ps = new plantseg(px,py,pg);
            for(int i=u.rand(6);i>=0;--i) {
               switch(u.rand(3)) {
                 case 0: case 1:leaf ll = new leaf(px,py,pg); break;
                 case 2: flower ff = new flower(px,py,pg); break;
               }
            }
            px = ps.x2;
            py = ps.y2;
            pg = ps.g2;
//startPR2005-04-18^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            if(Demo_base.isDemo){
              if (Demo_base.demoIsReadyForExit(0)) return;
            }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            if(delayedflip && !completed){
               flip();
            }
        }
        else {
              error(value);
              gamescore(-1);
              noise.groan();
              u.pause(800);
        }
     }
  }
  //------------------------------------------------------------
  class plantseg extends mover {
     int x1,y1,x2,y2;
     double g1,g2;
     long started = gtime();
     Polygon p = new Polygon();
     boolean hadnode;
     int lastw1;

     public plantseg(int xx1, int yy1, double gg1) {
        super(false);
        keepMoving=true;
        dontclear = true;
        x1 = xx1;
        y1 = yy1;
        g1 = gg1;
        if(x1<LEFTLIM) {
           y2 = y1 - TWIGINCY;
           if(y2 < TOPLIM/2) {
              y2 = TOPLIM/4+u.rand(TOPLIM/2);
              x2 = LEFTLIM + TOPLIM/2 - (y1-TWIGINCY);
              g2 = -Math.PI/4 + u.rand(Math.PI/2);
              x=0; y=0; w = x2; h = TOPLIM;
           }
           else {
              x2 = LEFTLIM/4 + u.rand(LEFTLIM/2);
              g2 = Math.PI*5/4 + u.rand(Math.PI/2);
              x=0; y=y2; w = LEFTLIM; h = y1-y2+2;
           }
        }
        else if(y1<TOPLIM) {
           x2 = x1 + TWIGINCX;
           if(x2 > RIGHTLIM + LEFTLIM/2) {
              x2 = RIGHTLIM + LEFTLIM/4 + u.rand(LEFTLIM/2);
              y2 = TOPLIM + x1 + TWIGINCX - (RIGHTLIM+LEFTLIM/2);
              g2 = Math.PI/4 + u.rand(Math.PI/2);
              x=Math.min(x1,RIGHTLIM); y=0; w = mover.WIDTH-x; h = y2;
           }
           else {
              y2 = TOPLIM/4 + u.rand(TOPLIM/2);
              g2 = -Math.PI/4 + u.rand(Math.PI/2);
              x=x1; y=0; w = x2-x1+2; h = TOPLIM;
           }
        }
        else if(x1>RIGHTLIM) {
           y2 = y1 + TWIGINCY;
           if(y2 > BOTLIM + TOPLIM/2) {
              y2 = BOTLIM + TOPLIM/4+u.rand(TOPLIM/2);
              x2 = RIGHTLIM - (-(BOTLIM+TOPLIM/2) + (y1+TWIGINCY));
              g2 = Math.PI*3/4 + u.rand(Math.PI/2);
              x=x2; y=Math.min(y1,BOTLIM); w = mover.WIDTH-x2; h = mover.HEIGHT-y;
              inseg = 3;
           }
           else {
              x2 = RIGHTLIM + LEFTLIM/4 + u.rand(LEFTLIM/2);
              g2 = Math.PI*4 + u.rand(Math.PI/2);
              x=RIGHTLIM; y=y1; w = mover.WIDTH-RIGHTLIM; h = y2-y1+2;
              inseg = 2;
           }
        }
        else if(y1>BOTLIM) {
           x2 = x1 - TWIGINCX;
           if(x2 < LEFTLIM) {
              goneround = true;
              x2 = 0;
              y2 = BOTLIM;
              g2 = Math.PI*3/4 + u.rand(Math.PI/2);
              x=0; y=BOTLIM; w = x1; h = mover.WIDTH-BOTLIM;
           }
           else {
              y2 = BOTLIM + TOPLIM/4 + u.rand(TOPLIM/2);
              g2 = Math.PI*3/4 + u.rand(Math.PI/2);
              x=x2; y=BOTLIM; w = x1-x2+2; h = mover.HEIGHT-BOTLIM;
           }
        }
        u.drawcurve(p,x1*screenwidth/mover.WIDTH,y1*screenheight/mover.HEIGHT,g1,x2*screenwidth/mover.WIDTH,y2*screenheight/mover.HEIGHT,g2);
        if(!printing) addMover(this,x,y);

     }
     public void paint(Graphics g,int x, int y, int w, int h) {
        if(lastw1 != 0 && lastw1 !=w) {
           for (int i=0;i<p.npoints;++i) p.xpoints[i] = p.xpoints[i]*w/lastw1;
        }
        lastw1 = w;
        long t = gtime();
        int tot;
        if(t-started<DRAWTIME) {
            tot = (int)(p.npoints * (t-started)/DRAWTIME);
         }
         else {
           tot = p.npoints;
           keepMoving = false;
        }
        g.setColor(stemcolor);
        g.drawPolyline(p.xpoints,p.ypoints, tot);
     }
     public void changeImage(long t) {
        if(t-started<DRAWTIME) {
            int tot = (int)(p.npoints * (t-started)/DRAWTIME);
            if(tot > p.npoints/2 && !hadnode) {
              hadnode=true;
              double pg = Math.atan2(p.ypoints[tot]-p.ypoints[tot-1],p.xpoints[tot]-p.xpoints[tot-1]);
              for(int i=u.rand(6);i>=0;--i) {
                 switch(u.rand(3)) {
                    case 0: case 1:leaf ll = new leaf(p.xpoints[tot]*mover.WIDTH/screenwidth,p.ypoints[tot]*mover.HEIGHT/screenheight, pg); break;
                    case 2: flower ff = new flower(p.xpoints[tot]*mover.WIDTH/screenwidth,p.ypoints[tot]*mover.HEIGHT/screenheight,pg); break;
                 }
              }
            }
         }
     }
     }
    //-----------------------------------------------------------
   class leaf extends mover {
      int x1,y1,x2,y2;
      double g1,g2,leafangle,leafangle2,g3;
      Polygon p = new Polygon();;
      long started = gtime();
      Color color;
      int lastw1;
      public leaf(int xx1, int yy1, double gg1) {
        super(false);
        keepMoving=true;
        dontclear = true;
        x1 = xx1;
        y1 = yy1;
        g1 = gg1;
        color = new Color(u.rand(128),128+u.rand(128),u.rand(128));
        if(x1<LEFTLIM) {
           g2 = -Math.PI*2/3 + u.rand(Math.PI*2/3);
           x2 = u.rand(LEFTLIM*2);
           y2 = y1 - TWIGINCY2/2 - u.rand(TWIGINCY2);
           x = 0;y=y2;w = LEFTLIM*2;h = y1-y2;
        }
        else if(y1<TOPLIM) {
           g2 = -Math.PI/3 + u.rand(Math.PI*2/3);
           x2 = x1 + TWIGINCX2/2 + u.rand(TWIGINCX2);
           y2 = u.rand(TOPLIM*2);
           x = x1;y=0;w = x2-x1;h = TOPLIM*2;
        }
        else if(x1>RIGHTLIM) {
           g2 = Math.PI/3 + u.rand(Math.PI*2/3);
           x2 = mover.WIDTH - u.rand(LEFTLIM*2);
           y2 = y1 + TWIGINCY2/2+u.rand(TWIGINCY2);
           x = RIGHTLIM-LEFTLIM;y=y1;w = LEFTLIM*2;h = y2-y1;
        }
        else if(y1>BOTLIM) {
           g2 = Math.PI*2/3 + u.rand(Math.PI*2/3);
           x2 = x1 - TWIGINCX2/2-u.rand(TWIGINCX2);
           y2 = mover.HEIGHT - u.rand(TOPLIM*2);
           x = x2;y=BOTLIM-TOPLIM;w = x1-x2;h = TOPLIM*2;
        }
        leafangle = Math.PI/3 + u.rand(Math.PI/3);
        leafangle2 = u.rand(Math.PI/3);
        u.drawcurve(p,x1*screenwidth/mover.WIDTH,y1*screenheight/mover.HEIGHT,g1,x2*screenwidth/mover.WIDTH,y2*screenheight/mover.HEIGHT,g2);
        if(!printing) addMover(this,x,y);
     }
     public void paint(Graphics g,int x, int y, int w, int h) {
       if(lastw1 != 0 && lastw1 !=w) {
          for (int i=0;i<p.npoints;++i) p.xpoints[i] = p.xpoints[i]*w/lastw1;
       }
       lastw1 = w;
        long t = gtime();
        int tot, mid = p.npoints/2;
        Polygon leafp = new Polygon();
        if(t-started<DRAWTIME) {
           tot = (int)(p.npoints * (t-started)/DRAWTIME);
           tot = Math.min(tot,p.npoints-1);
           if(tot > mid) {
               u.drawcurve(leafp,p.xpoints[mid], p.ypoints[mid],
                                 (g1+g2)/2 + leafangle*(tot-mid)/mid,
                                 p.xpoints[tot], p.ypoints[tot],
                                 (g1*(p.npoints-tot)+g2*tot)/p.npoints - leafangle2*(tot-mid)/mid);
               u.drawcurve(leafp, p.xpoints[tot], p.ypoints[tot],
                                 (g1*(p.npoints-tot)+g2*tot)/p.npoints + leafangle2*(tot-mid)/mid+Math.PI,
                                  p.xpoints[mid], p.ypoints[mid],
                                 (g1+g2)/2 - leafangle*(tot-mid)/mid + Math.PI);
           }
        }
        else {
           tot = p.npoints;
           u.drawcurve(leafp,p.xpoints[mid], p.ypoints[mid],
                                 (g1+g2)/2 + leafangle,
                                 p.xpoints[tot-1], p.ypoints[tot-1],
                                 g2 - leafangle2);
           u.drawcurve(leafp,p.xpoints[tot-1], p.ypoints[tot-1],
                                 g2 + leafangle2 + Math.PI,
                                  p.xpoints[mid], p.ypoints[mid],
                                 (g1+g2)/2 - leafangle + Math.PI);
           keepMoving = false;
        }
        g.setColor(stemcolor);
        g.drawPolyline(p.xpoints,p.ypoints, tot);
        g.setColor(color);
        if(leafp.npoints>0) g.fillPolygon(leafp);
     }
   }
    //-----------------------------------------------------------
   class flower extends mover {
      int x1,y1,x2,y2;
      double g1,g2,leafangle,leafangle2,g3;
      Polygon p = new Polygon();;
      long started = gtime();
      Color color,colormid;
      short petals = (short)(6 + u.rand(6));
      int middle = mover.WIDTH/150 + u.rand(mover.WIDTH/150);
      int red,blue;
      double petstart = u.rand(Math.PI/4);
      int petrad = middle/4 + u.rand(middle);
      int lastw1;
      public flower(int xx1, int yy1, double gg1) {
        super(false);
        keepMoving=true;
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
        if(x1<LEFTLIM) {
           g2 = -Math.PI*2/3 + u.rand(Math.PI*2/3);
           x2 = u.rand(LEFTLIM);
           y2 = y1 - TWIGINCY2/4 - u.rand(TWIGINCY2/4);
           x = 0;y=y2-4;w = LEFTLIM*2;h = y1-y2;
        }
        else if(y1<TOPLIM) {
           g2 = -Math.PI/3 + u.rand(Math.PI*2/3);
           x2 = x1 + TWIGINCX2/4 + u.rand(TWIGINCX2/4);
           y2 = u.rand(TOPLIM);
           x = x1;y=0;w = x2-x1;h = TOPLIM*2;
        }
        else if(x1>RIGHTLIM) {
           g2 = Math.PI/3 + u.rand(Math.PI*2/3);
           x2 = mover.WIDTH - u.rand(LEFTLIM);
           y2 = y1 + TWIGINCY2/4+u.rand(TWIGINCY2/4);
           x = RIGHTLIM-LEFTLIM;y=y1;w = LEFTLIM*2;h = y2-y1;
        }
        else if(y1>BOTLIM) {
           g2 = Math.PI*2/3 + u.rand(Math.PI*2/3);
           x2 = x1 - TWIGINCX2/4-u.rand(TWIGINCX2/4);
           y2 = mover.HEIGHT - u.rand(TOPLIM);
           x = x2;y=BOTLIM-TOPLIM;w = x1-x2;h = TOPLIM*2;
        }
        u.drawcurve(p,x1*screenwidth/mover.WIDTH,y1*screenheight/mover.HEIGHT,g1,x2*screenwidth/mover.WIDTH,y2*screenheight/mover.HEIGHT,g2);
        if(!printing) addMover(this,x,y);
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
   //-----------------------------------------------------------
   class heading extends mover {
      String showit;
      heading() {
         super(false);
         sayit  = sharkStartFrame.currPlayTopic.headings[0]; //wordlist.getHeading(words);
         showit = spellchange.spellchange(sharkStartFrame.currPlayTopic.headings[0]);
         w = mover.WIDTH;
         h = ytop;
         addMover(this,0,0);
         if(phonics && !phonicsw) spokenWord.findandsaysentence(sayit);
      }
      public void paint(Graphics g,int x, int y, int w, int h) {
         g.setFont(sizeFont(null,g,showit,w,h));
         FontMetrics m = g.getFontMetrics();
         g.setColor(white());
         u.painthighlight(showit,g,w/2-m.stringWidth(showit)/2,0);
      }
   }
   //----------------------------------------------------
   class treefrog extends mover {
      sharkImage img = sharkImage.randomimages("treefrog_",(short) 1)[0];
      long lasttime;
      randrange_base dx = new randrange_base(4,12,4);
      randrange_base dy = new randrange_base(mover.HEIGHT*7/8,mover.HEIGHT,mover.HEIGHT/2);
      int xpos=0,ypos;
      treefrog() {
        super(true);
        img.w = mover.WIDTH/20 + u.rand(mover.WIDTH/20);
        img.h = mover.HEIGHT/5;
        img.adjustSize(screenwidth,screenheight);
        w = img.w;
        h = img.h;
        gamePanel.addMover(this,0,mover.HEIGHT-img.h);
        gamePanel.puttobottom(this);
        keepMoving = true;
      }
      public void changeImage(long t) {
        xpos += mover.WIDTH*dx.next(t);
        int ypos = dy.next(t) - img.h;
        xpos = Math.min((mover.WIDTH-img.w)*1000, xpos);
        tox = x =  xpos/1000;
        toy = y = ypos;
        if(x >= mover.WIDTH-img.w) {
           kill=true;
           gamePanel.copyall = true;
        }
        else gamePanel.puttobottom(this);
      }
      public void paint(Graphics g,int x, int y, int w, int h) {
        img.paint(g,x,y,w,h);
      }
   }
   public void print() {
     printing = true;
     String choices[] = u.splitString(rgame.getParm("printchoose"));
     String tots[] = u.splitString(rgame.getParm("printtot"));
     int prev = student.optionint("flowers_lastprint"),i,printnum=0;
     if(prev<0) prev = 0;
     String ret = (String) JOptionPane.showInputDialog(this,rgame.getParm("printtitle"),null,
                                          JOptionPane.INFORMATION_MESSAGE, null,
                                          choices, choices[prev]);
     if(ret==null) return;
     for(i=0;i<choices.length;++i) {
       if(ret == choices[i])   {printnum = i; break;}
     }
     student.setOption("flowers_lastprint",printnum);
     printtot = Integer.parseInt(tots[printnum]);

     PrinterJob pJob = PrinterJob.getPrinterJob();
     pJob.setJobName(rgame.getParm("printname"));
     PageFormat pageFormat = pJob.defaultPage();
     pageFormat.setOrientation(pageFormat.PORTRAIT);
     pJob.setPrintable(this, pageFormat);
     if (pJob.printDialog()) {
      try {
        pJob.print();
      }
      catch (Throwable t) {}
     }
     printing = false;
   }
   public int print(Graphics g, PageFormat f, int a) throws PrinterException {
     // if reached end of print
     if (a > 0) {
       printing = false;
       return NO_SUCH_PAGE;
     }

     // allows skipping of the buffer's graphics context
     if (a != lastPage) {
       lastPage = a;
       printing = false;
       return Printable.PAGE_EXISTS;
     }
     printing = true;
     g.translate( (int) f.getImageableX(), (int) f.getImageableY());
     Dimension dd;
     if (shark.macOS)

       /* width needs to be ajusted slightly for the Mac so that the whole
        sumsearch fits on the printed page*/
       dd = new Dimension( (int) (f.getImageableWidth() * 0.99),
                          (int) f.getImageableHeight());
     else
       dd = new Dimension( (int) f.getImageableWidth(),
                          (int) f.getImageableHeight());

     int w = dd.width;
     int h = dd.height;
     int xx = 0;
     int yy = 0;
     Date d = new Date();
     int x1, y1, x2, y2, fsiz;
     int i, j;
     word words[] = rgame.fullwordlist.getPatternList(rgame.gamename, (short) (printtot * 4 / 10),
                                      (short) (printtot - printtot * 4 / 10));
     int wordtot = (short) words.length;
     String h1 = u.edit(rgame.getParm("printhead"), spellchange.spellchange(rgame.topicName));
     String h2 =  headmess.showit;
     String h1a = u.gettext("printing", "footer");
     Font f1 = null, f2 = null, f3 = null, f4 = null;
     FontMetrics m, m1 = null, m2 = null, m3 = null, m4 = null;

     f1 = sizeFont(g, new String[] {h1}
                   , w, h / 20);
     m1 = g.getFontMetrics(f1);
     f2 = sizeFont(g, new String[] {h2}
                   , w, h / 20);
     m2 = g.getFontMetrics(f2);

     f4 = sizeFont(g, new String[] {h1a}
                   , w, h / 40);
     m4 = g.getFontMetrics(f4);
     g.setFont(f1);
     g.setColor(Color.black);
     g.drawString(h1, xx + (w - m1.stringWidth(h1)) / 2, yy + m1.getAscent());
     g.setFont(f2);
     u.painthighlight(h2,g, xx + (w - m2.stringWidth(h2)) / 2, yy + m1.getHeight());
     // put bottom line
     g.setFont(f4);
     g.drawString(h1a, xx, yy + h - m4.getHeight()+ m4.getAscent());
     h -= m1.getHeight() + m2.getHeight() + m4.getHeight();
     yy += m1.getHeight() + m2.getHeight();
     // put border round words
     int border = w/10;
     paintborder(xx,yy,w, h, border,g);
     border += w/40;

     w -= border*2;
     h -= border*2;
     xx += border;
     yy += border;

     //draw words-------------------
     g.setColor(Color.black);
     int perrow=0, rows=0,saverow=0;
     Font wordfont= null, testfont;
     for(i =0; i < 3;++i) {   // find best fit for 5,4,3 per row
       perrow = 5-i;
       rows = (wordtot + perrow - 1) / perrow;
       testfont = sizeFont(words, w * 3 / (3*perrow + 1), h / rows * wordtot);
       if(i==0 || testfont.getSize() > wordfont.getSize())    { wordfont = testfont;saverow = perrow;}
     }
     perrow = saverow;
     rows = (wordtot + perrow - 1) / perrow;
     g.setFont(wordfont);
     m = g.getFontMetrics(wordfont);
     int y = yy + m.getAscent();
     int incy = h / rows;
     for (i = 0; i < rows; ++i) {
       int wi = 0;
       for (j = i * perrow; j < wordtot && j < i * perrow + perrow; ++j) {
         wi += m.stringWidth(words[j].v());
       }
       int gap = (w - wi) / (perrow - 1);
       int x =xx;
       for (j=i*perrow;j<wordtot && j < i*perrow + perrow;++j) {
         g.drawString(words[j].v(), x, y );
         x += gap + m.stringWidth(words[j].v());
       }
       y += incy;
     }

     printing = false;
     return Printable.PAGE_EXISTS;
   }
   //---------------------------------------------------------------------------------------------
   void paintborder(int xx, int yy, int w,int h, int bw, Graphics g) {
     int i,j,k;
     int side = 6 + u.rand(6);
     int across = side *w / h;
     int tot = side * 2 + across * 2 + 1;
     int nx[] = new int[tot];
     int ny[] = new int[tot];
     double ng[] = new double[tot];

     for(i=0;i<tot;++i) {
        if(i< side) {
          nx[i] = xx + bw / 3 + u.rand(bw / 3);
          ny[i] = yy + h - h * i / side;
          ng[i] = -Math.PI*5/8 + u.rand(Math.PI/4);
        }
        else if(i==side) {
            nx[i] = xx + bw/2;
            ny[i] = yy + bw/2;
            ng[i] = -Math.PI/4;
        }
        else if(i< side + across) {
          nx[i] = xx + w * (i-side) / across;
          ny[i] = yy +  bw/3 + u.rand(bw/3);
          ng[i] = -Math.PI/8 + u.rand(Math.PI/4);
        }
        else if(i==side + across) {
            nx[i] = xx + w - bw/2;
            ny[i] = yy + bw/2;
            ng[i] = Math.PI/4;
        }
        else if(i< side*2 + across) {
          nx[i] = xx + w - bw / 3 - u.rand(bw / 3);
          ny[i] = yy + h * (i-side-across) / side;
          ng[i] = Math.PI*3/8 + u.rand(Math.PI/4);
        }
        else if(i==side*2 + across) {
            nx[i] = xx + w - bw/2;
            ny[i] = yy + +h - bw/2;
            ng[i] = Math.PI*3/4;
        }
        else {
          nx[i] = xx + w - w * (i-side*2-across) / across;
          ny[i] = yy + h - bw/3 - u.rand(bw/3);
          ng[i] = Math.PI*7/8 + u.rand(Math.PI/4);
        }
     }
     Polygon p = new Polygon();
     for(i=0;i<nx.length-1;++i) {
       p = u.drawcurve(p, nx[i],ny[i],ng[i],nx[i+1],ny[i+1],ng[i+1]);
     }
     g.setColor(stemcolor);
     g.drawPolyline(p.xpoints,p.ypoints,p.npoints);
     for(i=0;i<nx.length-1;++i) {
       for(j=u.rand(6);j>=0;--j) {
         int leafw = bw/3 + u.rand(bw/4);
         double leafg = (ng[i] + ng[i+1])/2 - Math.PI/4 + u.rand(Math.PI/2);
         int leafx,leafy;
         if(i< side) {
           leafx = xx + leafw/2 +  u.rand(bw - leafw);
           leafy = ny[i] - leafw/2 - u.rand(ny[i] - ny[i+1] - leafw);
         }
         else if(i< side + across) {
             leafy = yy + leafw/2 +  u.rand(bw - leafw);
             leafx = nx[i] + leafw/2 + u.rand(nx[i+1] - nx[i] - leafw);
         }
          else if(i< side*2 + across) {
             leafx = xx + w - bw + leafw/2 +  u.rand(bw - leafw);
             leafy = ny[i] + leafw/2 + u.rand(ny[i+1] - ny[i] - leafw);
         }
         else {
           leafy = yy + h - bw + leafw/2 +  u.rand(bw - leafw);
           leafx = nx[i] - leafw/2 - u.rand(nx[i] - nx[i+1] - leafw);
         }
         g.setColor(stemcolor);
         p = new Polygon();
         u.drawcurve(p,nx[i],ny[i],ng[i], leafx, leafy, leafg);
         g.drawPolyline(p.xpoints,p.ypoints,p.npoints);
         switch(u.rand(3)) {
            case 0: case 1:
              p = new Polygon();
              int leafx2 = leafx + (int)(leafw*Math.cos(leafg));
              int leafy2 = leafy + (int)(leafw*Math.sin(leafg));
              u.drawcurve(p, leafx, leafy, leafg-Math.PI/2, leafx2,leafy2,leafg + Math.PI/4);
              u.drawcurve(p, leafx2,leafy2,leafg + Math.PI*3/4, leafx, leafy, leafg+Math.PI*3/2);
              g.setColor(new Color(u.rand(128),128+u.rand(128),u.rand(128)));
              g.fillPolygon(p);
              break;
            case 2:
              int red,blue;
              Color color,colormid;
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
              short petals = (short)(6 + u.rand(6));
              int middle = leafw/6 + u.rand(leafw/6);
              double petstart = u.rand(Math.PI/4);
              int petrad = leafw/6 + u.rand(leafw/6);
              int mrad = leafw/2 - petrad;
              g.setColor(color);
              for(k=0;k<petals;++k) {
                double angle = petstart + Math.PI * 2 * k / petals;
                g.fillOval(leafx + (int)(mrad*Math.cos(angle)) - petrad,
                           leafy + (int)(mrad*Math.sin(angle)) - petrad, petrad*2,petrad*2);
             }
             g.setColor(colormid);
             g.fillOval(leafx-middle, leafy - middle, middle*2,middle*2);
             break;
          }
       }
     }
     for(j=2 + u.rand(4);j>=0;--j) {
        sharkImage im = sharkImage.random("treefrog_");
        int leafh = bw/3 + u.rand(bw/3);
        im.adjustSize(w,leafh);
        int leafw = im.w * w / mover.WIDTH;
        im.lefttoright = u.rand(2)==0;
        int leafx = xx + u.rand(w-leafw),leafy = yy + h - bw + u.rand(bw-leafh);
        im.paint(g,leafx,leafy,leafw,leafh);
     }
   }
}

