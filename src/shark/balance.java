package shark;


import shark.sharkGame.*;

import java.awt.*;


public class balance extends sharkGame {
  long nextcoin, stopping, movetime, stoptime;
//  short wantcoin;
  Rectangle coinrect;
  coin_base coinpile[];
  short cointot;
  short reward1;
  Font f;
  FontMetrics m ;
  boolean firstmess = true;
  long newblock;
  int blocktot=0;
  boolean done;
  support support;
  long startedat,allowtime = 30000;
  static final int sliptime = 2000;
  timer timer;
  block blocks[] = new block[0];   // free blocks
  block gotblocks[]=new block[0];  // captured blocks
  boolean outoftime;
  final static int falltime = 3000;
  long lastsliptime;

  public balance() {
    errors = false;
//    gamePanel.dontstart=true;
//    gamescore1 = true;
//    peeps = true;
//    listen= true;
//    peep = true;
//    wantspeed = true;
    sharkStartFrame.reward = 0;
    gamePanel.setBackground(background=u.lightbluecolor());
    gamePanel.clearWholeScreen = true;
    wantSprite = false;
    gamePanel.allclicks = true;
    nobest = true;
    buildTopPanel();
    newblock = gtime;
  }

  void setup() {
    int i, yy;
    startedat = gtime;
    timer  = new timer((int)allowtime);
    support = new support();
  }
  //------------------------------------------------------
  public void afterDraw(long t) {
    int i;
    if (support == null) {
      setup();
    }

    if (gtime > startedat+allowtime) { outoftime = true;}

    if(!completed && outoftime && blocks.length == 0) {
      wantcoin = (short)( blocktot);
//      clearexit = true;
      gamePanel.drop();
      String endmess;
      int record = student.optionint("balance_best");
      if (record < blocktot)
        student.setOption("balance_best", blocktot);
      if (blocktot >= 2) {
        endmess = u.edit(rgame.getParm("gottwo"), String.valueOf(blocktot));
        if (record > 0) {
          if (blocktot > record)    endmess += rgame.getParm("best");
          else  if (blocktot == record) endmess += rgame.getParm("same");
          else  endmess += u.edit(rgame.getParm("bestis"),String.valueOf(record));
        }
      }
      else if (blocktot == 1) {
          endmess = rgame.getParm("gotone");
          if (record > 1)
            endmess += u.edit(rgame.getParm("bestis"),String.valueOf(record));

      }
      else   endmess = rgame.getParm("gotnone");
      if(support.x + support.w/2 < mover.WIDTH/2) {
        showmessage(endmess, 
                mover.WIDTH / 2,
                0,
                mover.WIDTH,
                mover.HEIGHT / 2);
        exitbutton(mover.WIDTH * 3 / 4, mover.HEIGHT * 2 / 3);
      }
      else {
        showmessage(endmess, 
                0,
                0,
                mover.WIDTH / 2,
                mover.HEIGHT / 2);
        exitbutton(mover.WIDTH / 4, mover.HEIGHT * 2 / 3);
      }
      timer.kill = true;
     }

     if(newblock != 0 && gtime > newblock) {
      newblock = gtime + 2000;
      if(!outoftime) {
        new block();
       }
       else newblock = 0;
    }

    if(!completed && blocks.length > 0) {    // see if catching new block
       int left,right,top;
       if(blocktot>0) {
         block topblock = gotblocks[blocktot - 1];
         left = topblock.x + topblock.topx1;
         right = topblock.x + topblock.topx2;
         top = topblock.y;
       }
       else {
          left = support.x;
          right = support.x + support.w;
          top = support.y;
       }
       for(i=0;i<blocks.length;++i) {
          if(blocks[i].oversupport
             && blocks[i].y + blocks[i].h >= top
            && blocks[i].x + blocks[i].bottomx2  > left
                  && blocks[i].x + blocks[i].bottomx1< right) {
            block b = blocks[i];
            removeblock(b);
            addgotblock(b);
            gamePanel.moveWithMouse(b);
            b.mx = b.x - gamePanel.mousex;
            b.my =  top - b.h - gamePanel.mousey;
          }
          else blocks[i].oversupport = blocks[i].y + blocks[i].h < top;
       }
    }
    if(lastsliptime > 0 && gtime > lastsliptime + sliptime) {
       lastsliptime = 0;
    }
    if(!completed && blocktot > 0) {   // see if out of balance
      int left,right;
       int cx = 0, mass= 0,slipat = -1;
       boolean slipright = false;
       int rotx1=0,roty1=0,top1;
       for(i=blocktot-1; i>=0; --i) {
         cx = (gotblocks[i].mass * (gotblocks[i].x + gotblocks[i].centroidx) + cx*mass)/(mass + gotblocks[i].mass);
         mass += gotblocks[i].mass;
         if(i==0) {left = support.x; right = support.x + support.w; top1 = support.y;}
         else {
           left = gotblocks[i-1].x + gotblocks[i-1].topx1;
           right = gotblocks[i-1].x + gotblocks[i-1].topx2;
           top1 = gotblocks[i-1].y;
         }
         if(cx < left || cx > right) {
           slipat = i;
           slipright = cx>right;
           rotx1 = slipright?right:left;
           roty1 = top1;
         }
       }
       if(slipat >= 0)  {
           for (i = blocktot - 1; i >= slipat; --i) {
                 block b = gotblocks[i];
                 removegotblock(b);
                 gamePanel.drop(b);
                 b.moveto(slipright ? b.x +  b.w*(i-slipat + 1): b.x - b.w*(i-slipat + 1), b.y + b.h, 1000);
                 b.lost = true;
                 b.lostat = gtime;
                 b.slipangle  = slipright ? Math.PI/8: - Math.PI/8;
           }
       }
    }
    if (wantcoin > 0 && gtime > nextcoin) {
      noise.coindrop();
      dropcoin();
      nextcoin = gtime + 600;
      score(1);
      --wantcoin;
    }
  }

  //--------------------------------------------------------------------
  void dropcoin() {
    if (coinrect == null)
      coinrect = new Rectangle(ebutton.x*screenwidth/mover.WIDTH, 0,
                               ebutton.w*screenwidth/mover.WIDTH, screenheight / 20);
    Rectangle r = coinrect;
    int coinrad = mover.WIDTH / 60;
    int cointhick = mover.WIDTH / 200;
    if (coinpile == null || coinpile.length < wantcoin) {
      coinpile = new coin_base[wantcoin]; cointot = 0; }
    coin_base cc = new coin_base(coinrad, cointhick,
                       (r.x + coinrad * screenwidth / mover.WIDTH +
                        u.rand(r.width -
                               coinrad * 2 * screenwidth / mover.WIDTH) -
                        screenwidth / 2) * mover3d.BASEU / screenmax +
                       mover3d.BASEU / 2,
                       (r.y + r.height - coinrad * screenheight / mover.HEIGHT -
                        screenheight / 2) * mover3d.BASEU / screenmax +
                       mover3d.BASEU / 2,
                       Math.PI * u.rand(100) / 400, 0,
                       -Math.PI / 4 + Math.PI / 2 * u.rand(100) / 100,
                       Color.yellow, Color.orange,
                       ebutton.y);
    cc.endax = Math.PI / 2 * u.rand(100) / 100;
    cc.endaz = -Math.PI / 2 + Math.PI * u.rand(100) / 100;
    cc.pile = coinpile;
    coinpile[cointot++] = cc;
    addMover(cc, 0, 0);
  }
  //==========================================================================================
  class block extends mover {
     int topx1,topx2,bottomx1,bottomx2,centroidx,mass;    // relative to current postion x,y
     Color color = u.darkcolor();
     int relx, pos;
     boolean oversupport = true, lost;
     double slipangle;
     long lostat;
     block() {
        w = mover.WIDTH/30 + u.rand(mover.WIDTH/30);
        h = mover.HEIGHT/40 + u.rand(mover.HEIGHT/40);
        switch(u.rand(8)) {
          case 0:
            topx1 = 0;
            topx2 = w;
            do {
              bottomx1 = u.rand(w * 3 / 4);
              bottomx2 = (bottomx1 + w / 4) + u.rand(Math.max(1,w - (bottomx1 + w / 4)));
            } while(!centroid());
            break;
          case 1:
             topx1 = 0;
             bottomx2 = w;
             do {
               topx2 = w - u.rand(w * 3/4);
               bottomx1 = u.rand(w * 3/4);
             } while(!centroid());
             break;
           case 2:
             bottomx1 = 0;
             bottomx2 = w;
             do {
               topx1 = u.rand(w * 3 / 4);
               topx2 = (topx1 + w / 4) + u.rand(Math.max(1,w - (topx1 + w / 4)));
             } while(!centroid());
             break;
           case 3:
              topx2 = w;
              bottomx1 = 0;
              do {
                bottomx2 = w - u.rand(w * 3/4);
                topx1 = u.rand(w * 3/4);
              } while(!centroid());
              break;
            case 4:
              topx1 = 0;
              bottomx1 = 0;
              bottomx2 = w;
              do {
                topx2 = w/8 + u.rand(w * 7 / 8);
              } while(!centroid());
              break;
            case 5:
              topx1 = 0;
              bottomx1 = 0;
              topx2 = w;
              do {
                bottomx2 = w/8 + u.rand(w * 7 / 8);
              } while(!centroid());
              break;
            case 6:
              topx1 = 0;
              bottomx2 = w;
              topx2 = w;
              do {
                bottomx1 = w/8 + u.rand(w * 5 / 8);
              } while(!centroid());
              break;
            case 7:
              bottomx1 = 0;
              bottomx2 = w;
              topx2 = w;
              do {
                topx1 = w/8 + u.rand(w * 5 / 8);
              } while(!centroid());
              break;
        }
        int btot = blocks.length;
        addblock(this);
        addMover(this,mover.WIDTH/20 +  u.rand(mover.WIDTH*9/10-w), -h);
        moveto(x,mover.HEIGHT,falltime);
     }
     public boolean endmove()   {
       if(lost) moveto(x, mover.HEIGHT,falltime*(mover.HEIGHT-y)/mover.HEIGHT/2);
       if(y >= mover.HEIGHT) {
         removeblock(this);
         return true;
       }
         return false;
     }
     boolean centroid() {
       int hh = h *screenheight / mover.HEIGHT;
       int m1 = Math.abs(topx1-bottomx1)*hh/2, x1 = Math.abs(topx1-bottomx1)*2/3;
       int w2 = Math.min(topx2,bottomx2) - Math.max(topx1,bottomx1), m2 = w2*hh, x2 = Math.max(topx1,bottomx1) + w2/2;
       int m3 = Math.abs(topx2-bottomx2)*hh/2, x3 = w - Math.abs(topx2-bottomx2)*2/3;
       centroidx = (m1*x1 + m2*x2 + m3*x3) / (m1+m2+m3);
       mass = m1+m2+m3;
       return centroidx > bottomx1+w/40 && centroidx < bottomx2-w/40;
     }
     public void paint(Graphics g, int x1, int y1, int w1, int h1) {
        Polygon p;
        p = new Polygon(new int[]{x1+topx1*screenwidth/mover.WIDTH, x1+topx2*screenwidth/mover.WIDTH,
                           x1+bottomx2*screenwidth/mover.WIDTH,x1+bottomx1*screenwidth/mover.WIDTH},
                                     new int[]{y1, y1, y1+h1,y1+h1}, 4);

        if(lost) {
          double angle = slipangle + slipangle * 4 * (gtime - lostat) / sliptime;
          p = sharkpoly.simplerotate(p, x1 + w1/2, y1 + h1/2, angle);
        }
        g.setColor(color);
        g.fillPolygon(p);
     }
  }
  //===========================================================================================
  class support extends mover {
     support() {
        w =mover.WIDTH/20;
        h = mover.HEIGHT/20;
        gamePanel.moveWithMouse(this);
     }
     public void paint(Graphics g, int x1, int y1, int w1, int h1) {
        g.setColor(Color.red);
        g.fillRect(x1,y1,w1,h1);
     }
  }
  void addblock(block b) {
    int btot = blocks.length;
    block blocks1[] = new block[btot+1];
    System.arraycopy(blocks,0,blocks1,0,btot);
    blocks1[btot] = b;
    blocks = blocks1;
  }
  void removeblock(block b) {
    int i;
    int btot = blocks.length;
    for(i=0;i<blocks.length;++i) if(blocks[i] == b) {
        block blocks1[] = new block[btot - 1];
        System.arraycopy(blocks, 0, blocks1, 0, i);
        System.arraycopy(blocks, i+1, blocks1, i, btot-i-1);
        blocks = blocks1;
        return;
    }
  }
  void addgotblock(block b) {
    block blocks1[] = new block[blocktot+1];
    System.arraycopy(gotblocks,0,blocks1,0,blocktot);
    blocks1[blocktot] = b;
    b.pos = blocktot;
    gotblocks = blocks1;
    ++blocktot;
  }
  void removegotblock(block b) {
    int i;
    for(i=0;i<blocktot;++i) if(gotblocks[i] == b) {
        block blocks1[] = new block[blocktot - 1];
        System.arraycopy(gotblocks, 0, blocks1, 0, i);
        System.arraycopy(gotblocks, i+1, blocks1, i, blocktot-i-1);
        gotblocks = blocks1;
        --blocktot;
        return;
    }
  }
}
