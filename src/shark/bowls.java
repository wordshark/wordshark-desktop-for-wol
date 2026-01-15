package shark;

import java.awt.*;

public class bowls extends sharkGame {
   static final short MINBALLTIME = 1000;
   static final short BALLTOT=3;
   int bwidth,bheight;
   long  nextcoin,stopping;
   short givescore[];
   static Color colors[] = new Color[] {Color.blue,
                                        Color.red,
                                        Color.orange,
                                        Color.magenta};
//   short wantcoin;
   Rectangle coinrect;
   mover lastmessage,pickmessage;
   coin_base coinpile[];
   short cointot;
   tableclass1 table1;
   tableclass2 table2;
   ballclass ball;
   bowl bowls[] = new bowl[10];
   int bowlh1,  bowlw1, bowlboty, bowlbotw,bowld;
   Polygon bowlp;
   point3d_base bowlp2[];
   boolean ballfree;
   int ballx,bally;
   int crossat;
   short ballnum;
   int bbdist, lastz, bowltop;
   double ballangle;
   int ninescore,tenscore;
//startPR2004-08-29^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   // stores the coordinates of the line the ball is dragged back from
     int lineX1;
     int lineX2;
     int lineY1;
     int lineY2;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR

   public bowls() {
    errors = false;
    gamePanel.dontstart=true;
//    gamescore1 = true;
//    peeps = true;
//    listen= true;
//    peep = true;
//    wantspeed = true;
    clickonrelease = true;
    sharkStartFrame.reward = (short)Math.max(3,sharkStartFrame.reward);
    ninescore = sharkStartFrame.reward/2;
    tenscore = sharkStartFrame.reward;
    sharkStartFrame.reward = 0;
    buildTopPanel();
//    gamePanel.showSprite = false;
    gamePanel.viewerx = mover3d.BASEU*3/4;
    gamePanel.viewery = -mover3d.BASEU/3;
   table1 = new tableclass1();
   table2 = new tableclass2();
   setupbowls();
   ball = new ballclass();
   bbdist = bowlw1 + ball.ballrad;
   showmessage(rgame.getParm("pullball")
               + "|" +  u.edit(rgame.getParm("ninescore"), String.valueOf(ninescore))
               + "|" +  u.edit(rgame.getParm("tenscore"), String.valueOf(tenscore)),
     0, mover.HEIGHT/8,mover.WIDTH/2,mover.HEIGHT/2);
   gamePanel.dontstart=false;
  }
  //----------------------------------------------------------------------
  void setupbowls() {
     short i;
     int xgap = table1.width/12;           // half gap between bowls
     int zgap = (int)(xgap * Math.sqrt(3));
     int x = table1.rcentre.x+table1.width/2;
     int y = table1.rcentre.y;
     int z = table1.rcentre.z + table1.depth - zgap * 8;
     bowlh1 = (xgap*2);
     bowlw1 = bowlh1/6;
     bowld = bowlw1*2;
     short co[] = u.shuffle(u.select((short)4,(short)4));
     for(i=9;i>=0;--i) {
        if(i == 0)   bowls[i] = new bowl(x, y, z,colors[co[0]]);
        else if(i<3) bowls[i] = new bowl(x-xgap+xgap*2*(i-1), y, z+zgap,colors[co[1]]);
        else if(i<6) bowls[i] = new bowl(x-xgap*2+xgap*2*(i-3), y, z+zgap*2,colors[co[2]]);
        else  bowls[i] = new bowl(x-xgap*3+xgap*2*(i-6), y, z+zgap*3,colors[co[3]]);
     }
  }
  //-------------------------------------------------------------
  void nextball() {
         short down = bowlsdown();
         if(ballnum >= BALLTOT-1 || down == 10) {
                        // end of game
            if(down == 10)
                            showmessage(u.edit(rgame.getParm("tendown"),String.valueOf(wantcoin=(short)tenscore)),0, mover.HEIGHT*6/8,mover.WIDTH/2,mover.HEIGHT*7/8);
            else if(down ==9)
                             showmessage(u.edit(rgame.getParm("ninedown"),String.valueOf(wantcoin=(short)ninescore)), 0, mover.HEIGHT*6/8,mover.WIDTH/2,mover.HEIGHT*7/8);

             else showmessage(String.valueOf(down)+" "+rgame.getParm("hardluck"), 0, mover.HEIGHT*6/8,mover.WIDTH/2,mover.HEIGHT*7/8);
             exitbutton(mover.WIDTH/4,mover.HEIGHT*7/8);
         }
         else {
            ++ballnum;
            ballfree = false;
            ball = new ballclass();
            int miny = (table1.top.ypoints[0] - (table2.top.ypoints[0] -table2.top.ypoints[3]))*mover.HEIGHT/screenheight;
            ball.moveWithin=new Rectangle(table2.top.xpoints[3]*mover.WIDTH/screenwidth,
                            miny,
                        (table2.top.xpoints[2]-table2.top.xpoints[3])*mover.WIDTH/screenwidth,
                        table2.top.ypoints[0]*mover.HEIGHT/screenheight
                           - miny);
            gamePanel.clearWholeScreen = false;
            gamePanel.copyall = true;
         }
  }
  //-----------------------------------------------------------------
  short bowlsdown() {
     short tot=0;
     for(short i=0;i<10;++i) {
        if(bowls[i].angle != 0 || bowls[i].angley != 0) ++tot;
     }
     return tot;
  }
  //-----------------------------------------------------------------
  boolean bowlsmoving() {
     short tot=0;
     for(short i=0;i<10;++i) {
        if(bowls[i].fallrate != 0) return true;
     }
     return false;
  }
  //---------------------------------------------------------------
  public boolean click(int x2, int y2) {
     int y1 = table2.top.ypoints[3];
     if(!ballfree && bally > y1+2 ) {
        ballfree = true;
        gamePanel.clearWholeScreen = true;
        gamePanel.copyall = true;
        gamePanel.drop(ball);
        gamePanel.showSprite = false;
        ball.moveto(new point3d_base(ball.rcentre.x
            + (ball.convertx(crossat,table1.rcentre.z) - ball.rcentre.x)
                  *(table1.rcentre.z + table1.depth - ball.rcentre.z)
                  /(table1.rcentre.z  - ball.rcentre.z),
           ball.rcentre.y, table1.rcentre.z+table1.depth),
           MINBALLTIME * (mover3d.BASEU + (ball.rcentre.z-table2.rcentre.z)*2)/mover3d.BASEU);
        ballangle = Math.atan2(ball.tocentre.z - ball.fromcentre.z,
                               ball.tocentre.x - ball.fromcentre.x);
     }
     crossat = 0;
     return true;  // no individual clicks
  }
  //-----------------------------------------------------------------------
  public void afterDraw(long t) {
    boolean stopped = false, ok = true;
    int i, j, d, x=0,x1,y,br = bwidth/2*screenwidth/mover.WIDTH,brsq = br*br;
    int z, topz, topx;
    double dd;
    boolean changed = false;
    if(gamePanel.mousex<=0) {
      gamePanel.mousemoved( (table2.top.xpoints[3] + table2.top.xpoints[2])/2,
                           (table1.top.ypoints[0] - (table2.top.ypoints[0] -table2.top.ypoints[3])));
    }
    if(wantcoin > 0 && t > nextcoin) {
          noise.coindrop();
          dropcoin();
          nextcoin = t + 600;
          score(1);
          --wantcoin;
    }
    else {
       if(ball == null && !bowlsmoving()  && !completed) {
                 nextball();
       }
       else if(ballfree && ball != null) {
          z = ball.rcentre.z;
          for(i=0;i<10;++i) {
             if(bowls[i].angley == 0 && lastz < bowls[i].rcentre.z
                                 && z > bowls[i].rcentre.z) {
                d = u.signeddist(bowls[i].rcentre.x, bowls[i].rcentre.z,
                     ball.fromcentre.x, ball.fromcentre.z,
                     ball.tocentre.x, ball.tocentre.z);
                if(d*d < bbdist*bbdist) {
                   bowls[i].angley = ballangle + Math.atan2(d, Math.sqrt(bbdist*bbdist-d*d));
                   bowls[i].fallrate = Math.PI/2 /(800+u.rand(500));
                   d = Math.max(bbdist/8,Math.abs(d));
                   bowls[i].rcentre.x += (int)(bbdist * Math.cos(bowls[i].angley) *(-1+(double)bbdist/d));
                   bowls[i].rcentre.z += (int)(bbdist * Math.sin(bowls[i].angley) *(-1+(double)bbdist/d));
                   bowls[i].rcentre.z = Math.min(bowls[i].rcentre.z,table1.rcentre.z + table1.depth);
                   changed = true;
                }
             }
          }
          if(!table1.top.contains(ballx,bally)
              && !table2.top.contains(ballx,bally)) {
             gamePanel.removeMover(ball);
             ball = null;
         }
       }
    }
    for(i=0;i<10;++i) {
       if(bowls[i].angle != 0) {
          dd = bowltop*Math.sin(bowls[i].angle);
          topx = bowls[i].rcentre.x + (int)(dd*Math.cos(bowls[i].angley));
          topz =  bowls[i].rcentre.z + (int)(dd*Math.sin(bowls[i].angley));
          for(j=0;j<10;++j) {
             if(bowls[j].angley == 0
                  && bowls[j].rcentre.x-bowlw1 < Math.max(topx, bowls[i].rcentre.x)
                  && bowls[j].rcentre.x+bowlw1 > Math.min(topx, bowls[i].rcentre.x)) {
                d = u.signeddist(bowls[j].rcentre.x, bowls[j].rcentre.z,
                         bowls[i].prevcentre.x,  bowls[i].prevcentre.z,
                         topx,  topz);
                if(d*d < bowld*bowld) {
                       bowls[j].angley = bowls[i].angley
                            + Math.atan2(d, Math.sqrt(bowld*bowld-d*d));
                       bowls[j].fallrate = Math.PI/2 /(800+u.rand(500));
                       changed = true;
                }
                else if(bowls[i].rcentre.z <= bowls[j].rcentre.z
                         && topz > bowls[j].rcentre.z-bowlw1) {
                       bowls[j].angley = bowls[i].angley;
                       bowls[j].fallrate = Math.PI/2 /(800+u.rand(500));
                       changed = true;
                }
             }
          }
       }
    }
    if(ball!=null && ball.rcentre != null) lastz = ball.rcentre.z;
    if(changed || ball != null && ballfree) {
          gamePanel.sortmovers();
          gamePanel.puttobottom(table1);
          gamePanel.puttobottom(table2);
    }
  }
  //--------------------------------------------------------------------
  void dropcoin() {
     if(coinrect == null)
       coinrect = new Rectangle(screenwidth*3/4,0,screenwidth/20,screenheight/20);
     Rectangle r = coinrect;
     int coinrad = mover.WIDTH/60;
     int cointhick =   mover.WIDTH/200;
     if(coinpile == null || coinpile.length < wantcoin) {coinpile = new coin_base[wantcoin]; cointot = 0;}
     coin_base cc = new coin_base(coinrad, cointhick,
                        (r.x+coinrad*screenwidth/mover.WIDTH+u.rand(r.width-coinrad*2*screenwidth/mover.WIDTH)-screenwidth/2)*mover3d.BASEU/screenmax + mover3d.BASEU/2,
                        (r.y+r.height-coinrad*screenheight/mover.HEIGHT-screenheight/2)*mover3d.BASEU/screenmax + mover3d.BASEU/2,
                         Math.PI*u.rand(100)/400,0,
                         -Math.PI/4 + Math.PI/2*u.rand(100)/100,
                         Color.yellow,Color.orange,
                         mover.HEIGHT/2);
     cc.endax = Math.PI/2*u.rand(100)/100;
     cc.endaz = -Math.PI/2 + Math.PI*u.rand(100)/100;
     cc.pile = coinpile;
     coinpile[cointot++] = cc;
     addMover(cc,0,0);
  }
  //-----------------------------------------------------------------------
  class tableclass1 extends mover3d {
     int width,depth;
     Color color = new Color(0,196,0);
     point3d_base topedge[];
     Polygon top;
     tableclass1() {
        super();
        depth = BASEU*6/2;
        width = BASEU/2;
        rcentre = new point3d_base( BASEU/2, BASEU*3/4, BASEZ+BASEU/2);
        topedge = new point3d_base[] {new point3d_base(0, 0, 0),
                                 new point3d_base(width, 0, 0),
                                 new point3d_base(width, 0, depth),
                                 new point3d_base(0,0,depth) };
        w = mover.WIDTH/2;
        h = mover.HEIGHT;
        addMover(this,mover.WIDTH/2, 0);
        top = getPolygon(topedge);
      }
      public void paintm(Graphics g,long t) {
         top = getPolygon(topedge);
         if(depth != BASEU*2 && top.ypoints[2] <= gamePanel.screenwidth/16) {
           depth = BASEU * 2;
           topedge = new point3d_base[] {new point3d_base(0, 0, 0),
               new point3d_base(width, 0, 0),
               new point3d_base(width, 0, depth),
               new point3d_base(0, 0, depth)};
           top = getPolygon(topedge);
           for(int i=0;i<bowls.length;++i) bowls[i].kill = true;
           setupbowls();
         }
         g.setColor(color);
         g.fillPolygon(top);
//startPR2004-08-29^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         //draws the line the ball is dragged back from
         g.setColor(Color.black);
         g.drawLine(lineX1,lineY1,lineX2,lineY2);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      }
  }
  //-----------------------------------------------------------------------
                        //front half of table
  class tableclass2 extends mover3d {
     int width,depth;
     Color color = new Color(0,196,0);
     Color color2 = new Color(0,255,0);      // darker
     point3d_base topedge[];
     Polygon top;     // top in screen co-ords
     tableclass2() {
        super();
        keepMoving = true;
        depth = BASEU/2;
        width = BASEU/2;
        rcentre = new point3d_base( BASEU/2, BASEU*3/4, BASEZ);
        topedge = new point3d_base[] {new point3d_base(0, 0, 0),
                                 new point3d_base(width, 0, 0),
                                 new point3d_base(width, 0, depth),
                                 new point3d_base(0,0,depth) };
        w = mover.WIDTH/2;
        h = mover.HEIGHT;
        addMover(this,mover.WIDTH/2, 0);
        top = getPolygon(topedge);
      }
      public void paintm(Graphics g,long t) {
         top = getPolygon(topedge);
         int frontx1 = top.xpoints[0];
         int fronty1 = top.ypoints[0];
         int frontx2 = top.xpoints[1];
         int fronty2 = top.ypoints[1];
         g.setColor(color);
         g.fillPolygon(top);
//startPR2004-08-29^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         lineX1 = top.xpoints[2];
         lineX2 = top.xpoints[3];
         lineY1 = top.ypoints[2];
         lineY2 = top.ypoints[3];
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         g.setColor(Color.black);
         g.drawLine(top.xpoints[2],top.ypoints[2],top.xpoints[3],top.ypoints[3]);
         if(!ballfree && bally >= top.ypoints[2] && bally < table2.top.ypoints[0]
              && ballx > top.xpoints[3] && bally < top.xpoints[2]) {
            if(crossat == 0) crossat = ballx;
            g.drawLine(crossat,top.ypoints[3], ballx, bally);
         }
         else crossat = 0;
      }
  }
  //-----------------------------------------------------------------------
  class ballclass extends mover3d {
      int ballrad;
      ballclass() {
         super();
         screenwidth = gamePanel.currgame.screenwidth;
         screenheight = gamePanel.currgame.screenheight;
//         keepMoving = true;
         dontclear=true;
         straight = true;
         manager = gamePanel;
         gamePanel.moveWithMouse(this);
         ballrad = (w = BASEU/30)/2;
         h = w*screenwidth/screenheight;
         my = -ballrad*3;
         mx = -ballrad;
      }
      public void endmove3() {
         gamePanel.removeMover(ball);
         ball = null;
      }
      public void paint(Graphics g,int x1,int y1,int w1, int h1) {
            ballx = x1+w1/2;
            bally = y1+h1/2;
            if(x1 > 0)  {paintm(g,gtime);}
       }
      public void paintm(Graphics g,long t) {
        int rz;
        if (table1.top.ypoints[0] == 0 || table2.top.ypoints[0] == 0) return;
        g.setColor(color.black);
        if(!ballfree) {
          screenmax = Math.max(screenwidth,screenheight);
           if(moveWithin == null) {
              int miny = (table1.top.ypoints[0] - (table2.top.ypoints[0] -table2.top.ypoints[3]))*mover.HEIGHT/screenheight;
              moveWithin=new Rectangle(table2.top.xpoints[3]*mover.WIDTH/screenwidth,
                            miny,
                        (table2.top.xpoints[2]-table2.top.xpoints[3])*mover.WIDTH/screenwidth,
                        table2.top.ypoints[0]*mover.HEIGHT/screenheight
                           - miny);
            }
            rz = convertz(bally,table2.rcentre.y - ballrad);
            rcentre = new point3d_base(convertx(ballx,rz),
                               table2.rcentre.y-ballrad, rz);
         }
         else {    // ball is free
            ballx = centre.x;
            bally = centre.y;
            rz = rcentre.z;
         }
         int rad = ballrad*BASEZ/rz*screenmax/BASEU;
         g.fillOval(ballx-rad,bally-rad,rad*2,rad*2);
      }
   }
  //-------------------------------------------------------------------------
  class bowl extends mover3d {
     Color color1,color2;
     int i;
     long lasttime;
     point3d_base prevcentre,topcentre;
     double fallrate,angle,angley;     // rate of fall, curr angle to vert,dir of fall
     bowl(int x, int y, int z, Color c) {
        screenwidth = gamePanel.currgame.screenwidth;
        screenheight = gamePanel.currgame.screenheight;
        prevcentre = rcentre = new point3d_base(x,y,z);
        color1 = c;
        color2 = new Color(c.getRed()/2,c.getGreen()/2,c.getBlue()/2);
        w = bowlh1+bowlw1*2;
        h = w*screenwidth/screenheight;
        if(bowlp == null) {
           bowlp = new Polygon();
                          // find ht to start at
           sharkImage.addarc(bowlp, 0, 0, bowlh1/2, bowlw1, 19, 20);
           bowlboty = Math.abs(bowlp.ypoints[0]);
           bowlbotw = Math.abs(bowlp.xpoints[0]);
           bowltop = bowlh1-bowlboty+bowlw1;  // topmost point
           bowlp.npoints = 0;
           sharkImage.addarc(bowlp, 0, -(bowlh1/2 - bowlboty), bowlw1, bowlh1/2, 19, 64+13);
           Polygon p2 = new Polygon();
           sharkImage.addarc(p2, 0, 0, bowlbotw, bowlbotw*2/3, 0,63);
           bowlp2 = new point3d_base[p2.npoints];
           for(i=0;i<p2.npoints;++i)
              bowlp2[i] = new point3d_base(p2.xpoints[i],0,p2.ypoints[i]);
        }
        addMover(this,0,0);
     }
     public boolean move(long t) {
        if(fallrate != 0) {
           angle = Math.min(Math.PI/2, angle+fallrate*(t-lasttime));
           rcentre.y = table2.rcentre.y
              - (int)(bowlw1*angle/(Math.PI/2));
           ray = angley;
           raz = angle;
           if(angle == Math.PI/2) fallrate = 0;
           return true;
        }
        lasttime = t;
        return false;
     }
     public void paintm(Graphics g,long t) {
        int toprad = bowlw1*BASEZ/rcentre.z*screenmax/BASEU;
        Polygon p3;
        point3d_base topmid = transform(0, -(bowlh1-bowlboty), 0);
        g.setColor(color1);
        g.fillOval(topmid.x - toprad, topmid.y - toprad, toprad*2, toprad*2);
        g.setColor(color2);
        g.drawOval(topmid.x - toprad, topmid.y - toprad, toprad*2, toprad*2);
        g.setColor(color1);
        if(angle == 0) {
           g.fillPolygon(p3 = transformPolygon(bowlp,0));
        }
        else {
           sharkpoly pp = new sharkpoly();
           point3d_base mid = transform( 0, -(bowlh1/2 - bowlboty), 0),bot = transform( 0, bowlboty, 0);
           double angle = Math.atan2(mid.x-bot.x, -mid.y+bot.y); // angle to y-axis
           int b = (int)Math.sqrt((mid.x-bot.x)*(mid.x-bot.x) + (mid.y-bot.y)*(mid.y-bot.y));
           sharkImage.addarc(pp, mid.x, mid.y,bowlw1*screenmax/mover3d.BASEU, b, 19, 64+13);
           pp.simplerotate(mid.x,mid.y,angle);
           g.setColor(color1);
           g.fillPolygon(pp);
           g.fillPolygon(getPolygon(bowlp2));
           g.setColor(color2);
           g.drawPolygon(getPolygon(bowlp2));
        }
     }
  }
}


