package shark.games;

import java.awt.*;
import java.awt.event.*;

import shark.*;

public class snakesandladders extends sharkGame {
  public static final short ALLOCGOOD=30, ALLOCBAD=100;
  final short SHOWTIME=4000,OKTIME=200,ACROSS=6,DOWN=12;
  final short EATTIME = 3000,CELLMOVE=500;
  final short MINSHOW = 3,MAXSHOW=6, SNAKEMAX = 4;
  int cellw = mover.WIDTH/ACROSS;
  int cellh = mover.HEIGHT/DOWN;
  short cellno,goodw,badw;
  int cellx[] = new int[ACROSS*DOWN];
  int celly[] = new int[ACROSS*DOWN];
  short physical[] = new short[ACROSS*DOWN];
  short logical[] = new short[ACROSS*DOWN];
  dword dwords[] = new dword[ACROSS*DOWN];
  word words[];
  short wordtot,curr,heaptot,patttot;
  sharkImage[] heads1 = sharkImage.findall("snakeside_");
  sharkImage[] heads2 = sharkImage.findall("snakefront_");
  sharkImage snakeend = new sharkImage("snakeend_",false);
  Color snakecolors[] = new Color[] {Color.magenta,
                                            yellow(),Color.red,
                                            Color.orange,
                                            u.darkcolor()};
  short co[] = u.shuffle(u.select((short)snakecolors.length, (short)snakecolors.length));
  short conext;
  mover headmess;
  sharkImage mazesprite
          = new sharkImage(sharkStartFrame.studentList[sharkStartFrame.currStudent].spritename,
          false);
  ladder ladders[] = new ladder[MAXSHOW];
  int laddertot,snaketot,snakemin;
  snake snakes[] = new snake[SNAKEMAX];
  boolean beingeaten,moving,haderror;

  public snakesandladders() {
    short i;
    errors = true;
    gamescore1 = true;
//    peeps = true;
//    listen= true;
    freepeep = peep = true;
    wantspeed = true;
    wantSprite = false;
    optionlist = new String[] {"snake-"};
    help(phonics && !phonicsw ? "help_snakesph":"help_snakes");
    rgame.options |= word.CENTRE;
    gamePanel.dontstart = true;
    buildTopPanel();
    setupWords();
    gamePanel.clearWholeScreen = true;
    mazesprite.w =  cellw;
    mazesprite.h =  cellh*7/8;
    mazesprite.manager = gamePanel;
    for(i=0;i<ACROSS*DOWN;++i) {
       if(((i/ACROSS) & 1) == 0) cellx[i] = cellw*(i%ACROSS);
       else      cellx[i] = cellw*(ACROSS-1-i%ACROSS);
       celly[i] = cellh*(DOWN - 1 - i/ACROSS);
       physical[i] = (short)(cellx[i]/cellw + (celly[i]/cellh)*ACROSS);
       logical[physical[i]] = i;
    }
    if(!gamePanel.isMover(mazesprite)) gamePanel.addMover(mazesprite, cellx[0], celly[0]);
    snakeend.w =  cellw;
    snakeend.h =  cellh;
    addMover(snakeend,0,0);
    addMover(new grid(),0,0);
    snakemin = snaketot = options.optionval(optionlist[0]) - 1;
    for(i=0;i<snaketot;++i) {
        snakes[i] = new snake(mover.WIDTH/8+u.rand(mover.WIDTH*3/4),mover.HEIGHT/8+u.rand(mover.HEIGHT*3/4),
             i==0?Color.green:Color.cyan);
    }
    next((short)0);
    headmess = new heading();
    gamePanel.dontstart = false;
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
     for(int ii = 0; ii < words.length; ii++){
         System.out.println(words[ii]);
     }
     
     
     wordfont = sizeFont(words,cellw*screenwidth/mover.WIDTH,
                                      cellh*screenheight/mover.HEIGHT*words.length*7/8);
     metrics  =  getFontMetrics(wordfont);
  }
  //--------------------------------------------------------------
  public void restart() {
    int i;
    if(headmess != null) {
               removeMover(headmess);
               headmess = null;
    }
    addMover(mazesprite, cellx[0], celly[0]);
    snakeend.w =  cellw;
    snakeend.h =  cellh;
    addMover(snakeend,0,0);
    addMover(new grid(),0,0);
    snakemin = snaketot = options.optionval(optionlist[0])-1;
    for(i=0;i<snaketot;++i) {
        snakes[i] = new snake(mover.WIDTH/8+u.rand(mover.WIDTH*3/4),mover.HEIGHT/8+u.rand(mover.HEIGHT*3/4),
                  (i==0)?Color.green:Color.cyan);
    }
    next((short)0);
    markoption();
  }
  //------------------------------------------------------------------
  void wordstotop() {
     short i;
     for(i=(short)(cellno+1); i<ACROSS*DOWN && dwords[i] != null;++i) {
           gamePanel.bringtotop(dwords[i]);
     }
  }
  //------------------------------------------------------------------
  void clearwords() {
     short i;
     for(i=0;i<laddertot;++i) {removeMover(ladders[i]); ladders[i] = null;}
     laddertot = 0;
     for(i=(short)(cellno+1); i<ACROSS*DOWN && dwords[i] != null;++i) {
           removeMover(dwords[i]);
           dwords[i] = null;
     }
  }
   //--------------------------------------------------------------
  void next(short newcell) {
     short i;
     haderror = false;
     clearwords();
     cellno = newcell;
     if(newcell == ACROSS*DOWN-1) {
        noise.play("4C(450)/E/(150)D/E/F/(450)G/(150)5C/",true);
        score(Math.min(gametot1,8));
        this.exitbutton(mover.HEIGHT/2);
        return;
      }
      if(delayedflip && !completed && cellno!=0){
          flip();
      }
     short want = (short)Math.min(ACROSS*DOWN-1-cellno,
                           MINSHOW + u.rand(MAXSHOW-MINSHOW+1));
     short goodpos = u.rand(want);
     

     
 
     
     
     
     for (i=0;i<want;++i) {
         if(ACROSS*DOWN - (cellno+1+i) > ACROSS){
         System.out.println( 
                 String.valueOf(logical[physical[cellno+1+i] - ACROSS]) + "     " +
                 String.valueOf(cellno+1+want)  + "     " +
                 String.valueOf(logical[physical[cellno+1+i] - ACROSS] > cellno+1+want));
         }
         
        if(u.rand(3) == 0
              && ACROSS*DOWN - (cellno+1+i) > ACROSS
              && logical[physical[cellno+1+i] - ACROSS] > cellno+1+want)
          ladders[laddertot++] = new ladder((short)(cellno+1+i));
     }
     for (i=0;i<want;++i) {
        dwords[cellno+1+i] = new dword((i==goodpos)?nextgood():nextbad(), (short)(cellno+1+i));
     }
     for(i=0;i<snaketot;++i) {
        snakes[i].attack();
     }
     beingeaten = false;
     moving = false;
  }
  //----------------------------------------------------------------
  word nextgood() {
     short i;
     for(i=goodw; words[i%=words.length].pat == null; ++i);
     goodw = (short)((i+1)%words.length);
     return words[i];
  }
  //----------------------------------------------------------------
  word nextbad() {
    short i,j,repeats=0;
    loop: while(true) {
        i=badw;
       for(; words[i%=words.length].pat != null; ++i);
       badw = (short)((i+1)%words.length);
       String thisval = words[i].v();
       for(j=(short)(cellno+1); j<ACROSS*DOWN && dwords[j] != null; ++j)
          if(dwords[j].value.equals(thisval) && ++repeats<=words.length) continue loop;
       return words[i];
    }
  }
  //-------------------------------------------------------------------
   public void afterDraw(long t){
      if(!beingeaten && !moving) for(short i=0;i<snaketot;++i) {
         if(Math.abs(snakes[i].currx - (mazesprite.x +cellw/2)) < cellw/4
             && Math.abs(snakes[i].curry - (mazesprite.y +cellh/2)) < cellh/3) {
            beingeaten = true;
            snakes[i].eating = gtime + EATTIME;
            clearwords();
            removeMover(mazesprite);
            noise.scream();
            break;
         }
         if(Math.abs(snakes[i].currx - (mazesprite.x +cellw/2)) < cellw
             && Math.abs(snakes[i].curry - (mazesprite.y +cellh/2)) < cellh) {
             snakes[i].head1.setControl("m.open");
         }
      }
   }

  //-------------------------------------------------------------
  class dword extends mover {
     String value;
     short thiscell;
     word wd;
     boolean selected;
     Color color=Color.black;

     public dword(word v1,short cell) {
        super(false);
        dontclear=true;
        thiscell = cell;
        wd = v1;
        value = v1.v();
        w = cellw;
        h = cellh;
        addMover(this,cellx[cell],celly[cell]);
     }
     public void paint(Graphics g,int x, int y, int w, int h) {
         if(shark.macOS) ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
       g.setFont(wordfont);
       FontMetrics m = g.getFontMetrics();
       y += Math.max(0, h/2 - m.getHeight()/2) + m.getAscent();
       x += w/2-metrics.stringWidth(value)/2;
       if(wd.pat != null && wd.pat.length() > 0  && selected ) {
          wordlist.paintpat(value,wd.pat,g,color,white(),x,y);
       }
       else {
          g.setColor(selected?white():color);
          g.drawString(value, x, y);
       }
     }
     //-------------------------------------------------------------
     public void mouseClicked(int xm, int ym) {
        short i;
        if(selected || moving) return;
        if(wd.pat  !=  null) {
            moving=true;
            gamescore(1);
            wd.say();
            selected = true;
            if(headmess != null) {
               removeMover(headmess);
               headmess = null;
            }
            if(!haderror && snaketot>snakemin) {
              removeMover(snakes[--snaketot]);
            }
                         // subtask the move
            javax.swing.Timer t = new javax.swing.Timer(0,new ActionListener() {
               public void actionPerformed(ActionEvent e){
                  short i;
                  for(i=(short)(cellno+1);i<=thiscell;++i) {
                      mazesprite.moveto(cellx[i],celly[i],CELLMOVE);
                      u.pause(CELLMOVE);
                  }
                  for(i=0;i<laddertot;++i) {
                      if(ladders[i].foot == thiscell) {
                          mazesprite.moveto(cellx[ladders[i].top], celly[ladders[i].top],
                            CELLMOVE*2);
                          u.pause(CELLMOVE*2);
                          next(ladders[i].top);
                          return;
                      }
                   }
                   next(thiscell);
               }
            });
            t.setRepeats(false);
            t.start();
//startPR2005-04-18^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            if(Demo_base.isDemo){
              if (Demo_base.demoIsReadyForExit(0)) return;
            }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        }
        else {
              error(value);
              haderror = true;
              mazesprite.setControl("error");
              gamescore(-1);
              noise.groan();
              if(snaketot<SNAKEMAX) {
                 snakes[snaketot] = new snake(mover.WIDTH/8+u.rand(mover.WIDTH*3/4),mover.HEIGHT/8+u.rand(mover.HEIGHT*3/4),null);
                 snakes[snaketot++].attack();
              }
              wordstotop();
              gamePanel.bringtotop(mazesprite);
        }
     }
  }
  class snake extends mover {
     int neck,tail,hump;          // lengths of each
     int humpwidth;
     int len,thick = Math.max(3,screenwidth/100);
     int headw;
     int headh;
     int targetx,targety, currx,curry;
     long eating;
     Color color;
     boolean lookleft,movev,moveh;
     sharkImage head1,head2;
     long lasttime = gtime();
       // minor variations in head position
     randrange_base dx = new randrange_base(-cellw/12,cellw/12,cellw/4);
     randrange_base dy = new randrange_base(-cellh/4,cellh/4,cellh/6);
     randrange_base dw = new randrange_base(-cellw/2,cellw/2,cellh/4);
     snake(int x,int y,Color color1) {
        super(false);
        short i;
        color = color1;
        dontclear = true;
        keepMoving = true;
        dontgrabmouse = true;
        len = mover.WIDTH/4 + u.rand(mover.WIDTH/3);
        tail = len/8;
        neck = len/4+u.rand(len/2);
        neck = Math.min(neck,(mover.HEIGHT-y)*screenheight/screenwidth);
        hump = len - neck;
        humpwidth = hump*2/3;
        head1=new sharkImage(heads1[i=(short)u.rand(heads1.length)],false);
        head2=new sharkImage(heads2[i],false);
        head1.h = cellh*3/4;
        head1.w = cellw/2;
        head1.adjustSize(screenwidth,screenheight);
        head2.h = cellh*3/4;
        head2.w = cellw/2;
        head2.adjustSize(screenwidth,screenheight);
        if(color==null)
           color = snakecolors[co[conext=(short)((conext+1)%co.length)]];
        targetx = currx = x; targety = curry = y;
        w = mover.WIDTH;
        h = mover.HEIGHT;
        addMover(this,0,0);
     }
     public void paint(Graphics g,int x, int y, int w, int h) {
        int cx = currx*screenwidth/mover.WIDTH;
        int cw = cellw*screenwidth/mover.WIDTH;
        int cy = curry*screenheight/mover.HEIGHT;
        int ch = cellh*screenheight/mover.HEIGHT;
        int neck1 = neck*screenwidth/mover.WIDTH;
        int tail1 = tail*screenwidth/mover.WIDTH;
        int humpwidth1 = (humpwidth+dw.next(gtime))*screenwidth/mover.WIDTH;
        int hump1 = hump*screenwidth/mover.WIDTH;
        int a = Math.max((humpwidth1+thick)/5, thick*3/2);
        int b = Math.max(1,(hump1-humpwidth1)/6) + thick*2;
        int b1 = Math.max(b,neck1/2);
        int cy2 = cy + neck1 - b - thick;
        int endx,ex,ey;
        int vx = dx.next(gtime)*screenwidth/mover.WIDTH;
        int vy = dy.next(gtime)*screenheight/mover.HEIGHT;
        Polygon p = new Polygon();
        g.setColor(color);
        if(lookleft) {
           p.addPoint(cx+vx-thick,cy+vy);
           p.addPoint(cx+vx+thick,cy+vy);
           sharkImage.addarc(p, cx+a, cy+neck1-b1-thick, a-thick, b1-thick, 32, 16);
           sharkImage.addarc(p, cx+a, cy2, a-thick, b-thick, 14, 0);
           sharkImage.addarc(p, cx+a*3, cy2, a+thick, b+thick, 34, 64);
           sharkImage.addarc(p, cx+a*5, cy2, a-thick, b-thick, 30, 16);
           p.addPoint(endx = cx+a*5+tail1, Math.min(screenheight,cy+neck1-thick));
           sharkImage.addarc(p, cx+a*5, cy2, a+thick, b+thick, 16,32);
           sharkImage.addarc(p, cx+a*3, cy2, a-thick, b-thick,  62,32);
           sharkImage.addarc(p, cx+a, cy2, a+thick, b+thick, 2,16);
           sharkImage.addarc(p, cx+a, cy+neck1-b1-thick, a+thick, b1+thick, 18,32);
        }
        else {
           p.addPoint(cx+vx+thick,cy+vy);
           p.addPoint(cx+vx-thick,cy+vy);
           sharkImage.addarc(p, cx-a, cy+neck1-b1-thick, a-thick, b1-thick, 0, 16);
           sharkImage.addarc(p, cx-a, cy2, a-thick, b-thick, 18, 32);
           sharkImage.addarc(p, cx-a*3, cy2, a+thick, b+thick, 62, 32);
           sharkImage.addarc(p, cx-a*5, cy2, a-thick, b-thick, 2, 16);
           p.addPoint(endx = cx-a*5-tail1,  Math.min(screenheight,cy+neck1-thick));
           sharkImage.addarc(p, cx-a*5, cy2, a+thick, b+thick, 16,0);
           sharkImage.addarc(p, cx-a*3, cy2, a-thick, b-thick, 34,64);
           sharkImage.addarc(p, cx-a, cy2, a+thick, b+thick, 30,16);
           sharkImage.addarc(p, cx-a, cy+neck1-b1-thick, a+thick, b1+thick, 14,0);
        }
        g.fillPolygon(p);
        if(eating > gtime) {
           int pos = (int)(len*(gtime-(eating-EATTIME))/EATTIME);
           if(pos<neck1-b1) {ex = cx;ey = cy+vy+pos;}
           else {
              ex = cx + (pos-(neck1-b1))*(endx-cx)/(len-(neck1-b1));
              for(ey = cy+neck1+b1; !p.contains(ex,ey)  && ey > cy-ch; --ey);
              ey -= thick;
           }
           g.fillOval(ex-thick*2, ey-thick*2, thick*4,thick*4);
           head2.recolor(0,color);
           head2.paint(g, cx+vx-head2.w*screenwidth/mover.WIDTH/2,
                          cy+vy-head2.h*screenheight/mover.HEIGHT/2,
                          head2.w*screenwidth/mover.WIDTH,
                          head2.h*screenheight/mover.HEIGHT);
        }
        else {
           if(eating != 0) {
              eating = 0;
              next((short)logical[Math.min(ACROSS-1,Math.max(0,endx/cw))
                      + Math.min(DOWN-1,Math.max(0,(cy+neck1-thick)/ch)) *ACROSS]);
              addMover(mazesprite,cellx[cellno],celly[cellno]);
           }
           head1.lefttoright = lookleft;
           head1.recolor(3,color);
           head1.paint(g, cx+vx-head1.w*screenwidth/mover.WIDTH/2,
                          cy+vy-head1.h*screenheight/mover.HEIGHT/2,
                          head1.w*screenwidth/mover.WIDTH,
                          head1.h*screenheight/mover.HEIGHT);
        }
     }
     public void changeImage(long currtime) {
        if(eating < currtime) {
           int chx=0,chy=0;
           int ch = cellh*screenheight/screenwidth;
           int cy = curry*screenheight/screenwidth;
           int diff = (int) ((speed+4) * (currtime-lasttime)* mover.WIDTH/64000);
           if(targetx != currx && (targety==curry || u.rand(2) == 0)) {
              lookleft = (targetx < currx);
              if(moveh) {
                 if(hump>humpwidth) {
                    chx = Math.min(diff,hump-humpwidth);
                    chx = Math.min(chx,Math.abs(targetx-currx));
                    if(lookleft) currx -= chx;
                    else         currx += chx;
                    humpwidth += chx;
                 }
                 else moveh = false;
              }
              else {
                 if(humpwidth > hump/2) {
                    chx = Math.min(diff,humpwidth-hump/2);
                    humpwidth -= chx;
                 }
                 else moveh = true;
              }
           }
           else if(targety < curry) {   // moving up
              if(movev) {
                 if(hump > humpwidth) {
                    chy = Math.min(diff,hump-humpwidth);
                    chy = Math.min(chy,(curry - targety)*screenheight/screenwidth);
                   hump -= chy;
                    neck += chy;
                    curry -= chy*screenwidth/screenheight;
                 }
                 else movev = false;
              }
              else {
                 if(hump < humpwidth*2 && neck > ch) {
                    chy = Math.min(diff,humpwidth*2-hump);
                    chy = Math.min(chy,neck-ch);
                    hump += chy;
                    neck -= chy;
                 }
                 else movev = true;
              }
           }
           else if(targety > curry) {    //moving down
              if(movev) {
                 if(hump < humpwidth*2  && neck > ch) {
                    chy = Math.min(diff,humpwidth*2-hump);
                    chy = Math.min(chy,neck-ch);
                    chy = Math.min(chy,(targety-curry)*screenheight/screenwidth);
                    hump += chy;
                    neck -= chy;
                    curry += chy*screenwidth/screenheight;
                 }
                 else movev = false;
              }
              else {
                 if(hump > humpwidth) {
                   if(curry + (neck+diff)*screenwidth/screenheight < mover.HEIGHT) {
                      chy = Math.min(diff,hump-humpwidth);
                      hump -= chy;
                      neck += chy;
                   }
                   else if (currx==targetx){
                      chy = Math.min(diff,hump-humpwidth);
                      humpwidth += chy;
                   }
                   else movev = true;
                 }
                 else movev = true;
              }
           }
           if (chx !=0 || chy != 0) lasttime = currtime;
         }
         else lasttime = currtime;
     }
     void attack() {
        targetx = cellx[cellno] + cellw/2;
        targety = Math.min(mover.WIDTH-cellh*3/2,celly[cellno] + cellh/2);
     }
  }
  class ladder extends mover {
     short top,foot;
     Color color = u.darkcolor();
     ladder(short foot1) {
         
         // up is how many row up the ladder goes
        super(false);
        short phy = physical[foot=foot1],top1,up;
        if(phy<ACROSS) return;
        do {
           if(phy < ACROSS)   // top row?? shouldn't be any ladder?
               up = 1;
           else if(phy < ACROSS*2) //  second to top row
               up = (short)(1 + u.rand(1));  // up is 1 - odd becuase u.rand(1) is always 0
           else   // third row or below
               up = (short)(2 + u.rand(Math.min(2,phy/ACROSS-1)));   // third row or below  - up is 2 or 3
           top1 = (short)(phy - up*ACROSS - up + u.rand(up*2+1));
           if(top1>=0) 
               top = logical[top1];
         }while(top1< 0  ||  top1/ACROSS != phy/ACROSS - up || crossover());
        dontclear = true;
        dontgrabmouse = true;
        w = mover.WIDTH;
        h = mover.HEIGHT;
        addMover(this,0,0);
     }
     boolean crossover() {
       for(short i=0;i<laddertot;++i) {
          if(ladders[i] != null && ladders[i] != this
             && (cellx[foot] > cellx[ladders[i].foot] && cellx[top] < cellx[ladders[i].top]
             || cellx[foot]== cellx[ladders[i].foot] && cellx[top] == cellx[ladders[i].top]
             || cellx[foot] < cellx[ladders[i].foot] && cellx[top] > cellx[ladders[i].top]))
                return true;
       }
       return false;
     }
      public void paint(Graphics g,int x, int y, int w, int h) {
         int cw = cellw*screenwidth/mover.WIDTH;
         int ch = cellh*screenheight/mover.HEIGHT;
         int fx = cellx[foot]*screenwidth/mover.WIDTH;
         int fy = celly[foot]*screenheight/mover.HEIGHT;
         int tx = cellx[top]*screenwidth/mover.WIDTH;
         int ty = celly[top]*screenheight/mover.HEIGHT;
         double a = Math.atan2(-celly[top]+celly[foot], cellx[top]-cellx[foot]);
         int lw = Math.min(cw,ch);
         int dx = (int)(lw*Math.sin(a)/3);
         int dy = (int)(lw*Math.cos(a)/3);
         int footx1 = fx + cw/2 - dx;
         int footy1 = fy + ch/2 - dy;
         int footx2 = fx + cw/2 + dx;
         int footy2 = fy + ch/2 + dy;
         int topx1 = tx + cw/2 - dx;
         int topy1 = ty + ch/2 - dy;
         int topx2 = tx + cw/2 + dx;
         int topy2 = ty + ch/2 + dy;
         int len = (int)Math.sqrt((footx1-topx1)*(footx1-topx1) + (footy1-topy1)*(footy1-topy1));
         g.setColor(color);
         g.drawLine(footx1,footy1,topx1,topy1);
         g.drawLine(footx2,footy2,topx2,topy2);
         for(int  i=ch/2;i< len-ch/4; i+=ch/2) {
            g.drawLine(footx1 + i*(topx1-footx1)/len,footy1 + i*(topy1-footy1)/len,
               footx2 + i*(topx2-footx2)/len,footy2 + i*(topy2-footy2)/len);
         }
      }
   }
   //-----------------------------------------------------------
   class heading extends mover {
     String showit;
      heading() {
         super(false);
         sayit  = sharkStartFrame.currPlayTopic.headings[0]; // wordlist.getHeading(words);
         showit = spellchange.spellchange(sharkStartFrame.currPlayTopic.headings[0]);
         w = mover.WIDTH;
         h = cellh*2;
         addMover(this,0,cellh*5/2);
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
   //-------------------------------------------------------------------
   class grid extends mover {
      grid() {
         super(false);
         dontclear = true;
         w = mover.WIDTH;
         h = mover.HEIGHT;
      }
      public void paint(Graphics g,int x, int y, int w, int h) {
         int w1 = cellw*screenwidth/mover.WIDTH;
         int h1 = cellh*screenheight/mover.HEIGHT;
         int lastx = (ACROSS-1)*cellw*screenwidth/mover.WIDTH;
         short i;
         int py,cy,ny,cx;
         g.setColor(Color.gray);
         for(i=1;i<ACROSS;++i) {
            cx = i*cellw*screenwidth/mover.WIDTH;
            g.drawLine(cx,0,cx,screenheight);
         }
         for(i=1;i<DOWN;++i) {
            py = (i-1)*cellh*screenheight/mover.HEIGHT;
            cy = i*cellh*screenheight/mover.HEIGHT;
            ny = (i+1)*cellh*screenheight/mover.HEIGHT;
            if((i & 1) != 0) {
               if(i == 1) {
                  g.fillRect(screenwidth-w1/4,0,w1/4,ny+1);
                  g.setColor(background);
                  g.fillOval(screenwidth-w1/2,0,w1/2,ny);
               }
               else if(i == DOWN-1) {
                  g.fillRect(screenwidth-w1/4,py,w1/4,screenheight-py);
                  g.setColor(background);
                  g.fillOval(screenwidth-w1/2,py+3,w1/2,screenheight-(py+3));
               }
               else {
                  g.fillRect(screenwidth-w1/4,py,w1/4,ny-py);
                  g.setColor(background);
                  g.fillOval(screenwidth-w1/2,py+3,w1/2,ny-py-4);
               }
               g.setColor(Color.gray);
               g.fillRect(0,cy-1,lastx,4);
               g.drawLine(lastx,cy,screenwidth,cy);
            }
            else {
               g.fillRect(0,py,w1/4,ny-py);
               g.setColor(background);
               g.fillOval(0,py+3,w1/2,ny-py-4);
               g.setColor(Color.gray);
               g.fillRect(w1,cy-1,screenwidth-w1,4);
               g.drawLine(0,cy,w1,cy);
            }
         }
      }
   }
}

