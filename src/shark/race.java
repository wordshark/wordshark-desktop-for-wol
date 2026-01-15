package shark;

import java.awt.*;

public class race extends  sharkGame {
   static final int FALLTIME = 1000;
   static final int DOWNTIME = 1500;
   static final int SLEEPTIME = 3000;
   static final int BETWEENFALLS = 5000;
   static final byte BED = 0;
   static final byte EATSTOP = 1;
   int bwidth,bheight;
   short givescore;//,wantcoin;
   mover lastmessage;
   coin_base coinpile[];
   long  nextcoin,stopping;
   short cointot;
   Rectangle coinrect;
   racer winner[] = new racer[4];
   short winnernum[] = new short[4];
   short nextwinner;
   boolean hadwinner;
   racer racers[] = new racer[4];
   int winningline = mover.WIDTH * 15/16,trackborder = mover3d.BASEU/40;
   int winningline2;
   int edge1 = mover.HEIGHT/4, edge2 = mover.HEIGHT*3/4;
   boolean started;
   racetrack track;
   long lastfall;
   int selected;
   mover startmess;
   sharkImage beds[] = sharkImage.findall("bed_");
   short nextbed;
   scoreboard sc;

   public race() {
    errors = false;
    sharkStartFrame.reward = (short)Math.max(3,sharkStartFrame.reward);
    givescore = (short)(sharkStartFrame.reward);
    sharkStartFrame.reward = 0;
    gamePanel.sort3dmovers = true;
    gamePanel.clearWholeScreen = true;
    buildTopPanel();
    gamePanel.viewerx = mover3d.BASEU*3/4;
    gamePanel.viewery = -mover3d.BASEU/3*2;
    track = new racetrack();
    winningline2 = track.rcentre.x + winningline/2;
    gamePanel.winsprite = true;
//    gamePanel.showSprite = false;
//    fm.adjustSize(gamePanel.screenwidth,gamePanel.screenheight);
    winnernum = u.shuffle(u.select((short)racers.length,(short)racers.length));
    short o[] = u.shuffle(u.select((short)4,(short)4));
    racers[0] = new hare(o[0]);
    racers[1] = new kanger(o[1]);
    racers[2] = new tortoise(o[2]);
    racers[3] = new snail(o[3]);
    sc = new scoreboard();
    sc.keepMoving = true;

    startmess = showmessage(u.edit(rgame.getParm("pickwinner"), String.valueOf(givescore),
                      String.valueOf(givescore/3)),
                      0,0,mover.WIDTH,mover.HEIGHT/4);
  }
   public void afterDraw(long t){
    if(!started) {
 //        if(track != null && gamePanel.mousey > track.y && gamePanel.mousey < track.y+track.h)
 //           selected = (gamePanel.mousey-track.y) * 4 / track.h;
        if(track != null && track.yracers!=null && gamePanel.mousey > track.yracers[0] && gamePanel.mousey < track.yracers[4]){
              for(int i = 0; i < track.yracers.length+1; i++){
                  if(gamePanel.mousey<track.yracers[i+1]){
                      selected = i;  
                      break;
                  }
              }
        }      
        return;
    }
     int i,j;
     if(wantcoin > 0 && t > nextcoin) {
          noise.coindrop();
          dropcoin();
          nextcoin = t + 600;
          score(1);
          --wantcoin;
     }
     int leadx = 0;
     for(i=0;i<racers.length;++i) {
        if(racers[i].stopped != racers[i].WINNER && racers[i].rcentre.x > leadx) leadx = racers[i].rcentre.x;
     }
     if(t > lastfall + BETWEENFALLS) {
        if(hit(0,1)) {           // hare and kangeroo collide
          if(leadx < winningline2-mover3d.BASEU/4 || winnernum[nextwinner] != 0)
               racers[0].fall(1,0);
          if(leadx < winningline2-mover3d.BASEU/4 || winnernum[nextwinner] != 1)
               racers[1].fall(0,1);
        }
        else for(i=0;i<2;++i) {  // hares fall over tortoises
           for(j=2;j<4;++j) {
                if(leadx < winningline2-mover3d.BASEU/4 || winnernum[nextwinner] != i)
                      if(hit(i,j))  racers[i].fall(i,j);
              break;
           }
        }
     }
     if(hit(2,3)) {   // tortoises and snail touch
        if(racers[2].y < racers[3].y) {
          racers[2].rrangle.currval = -30;
          racers[3].rrangle.currval = 30;
        }
        else {
          racers[2].rrangle.currval = 10;
          racers[3].rrangle.currval = -10;
        }
     }
     for(i=0;i<racers.length;++i) {
         if(racers[i].fast  && racers[i].stopped == 0 && !racers[i].gotstopper
             && nextwinner < 2
             && racers[i].x > racers[i].decisions[racers[i].nextdecision]) {
            if(i != winnernum[nextwinner]
                     &&  leadx > mover3d.BASEU/2
                     &&  racers[i].rcentre.x == leadx
                 ||  leadx < mover3d.BASEU/2
                     && u.rand(3) == 0) {
               racers[i].addstopper();
            }
            ++racers[i].nextdecision;
        }
           // speed up slow ones
        if(!racers[i].fast
                  && leadx > mover3d.BASEU*2/3
                  && racers[i].rcentre.x < leadx
                  && i==winnernum[nextwinner] ) {
            racers[i].rrvel2.currval = racers[i].rrvel.currval;
            racers[i].rrvel = racers[i].rrvel2;
            racers[i].rrvel.lastt = gtime;

        }
           // stop fast racers
        if(racers[i].fast  && racers[i].stopped == 0 && !racers[i].gotstopper
                  && nextwinner < 2
                  && i!=winnernum[nextwinner] && racers[i].rcentre.x > winningline2 - mover3d.BASEU/8) {
               racers[i].addstopper();
        }
     }
     gamePanel.puttobottom(track);
   }
  //--------------------------------------------------------------------
  void dropcoin() {
     if(coinrect == null)
       coinrect = new Rectangle(screenwidth/2,0,screenwidth/20,screenheight/20);
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
                         mover.HEIGHT*3/4);
     cc.mustSave = true;
     cc.endax = Math.PI/2*u.rand(100)/100;
     cc.endaz = -Math.PI/2 + Math.PI*u.rand(100)/100;
     cc.pile = coinpile;
     coinpile[cointot++] = cc;
     addMover(cc,0,0);
  }
   boolean hit(int i, int j) {
      if(!racers[i].falling && racers[i].stopped == 0
            && ! racers[j].falling && racers[j].stopped == 0) {
         int x1 = racers[i].rcentre.x - racers[j].rcentre.x;
         int z1 = racers[i].rcentre.z - racers[j].rcentre.z;
         int dist = racers[i].w/2 + racers[j].w/2;
         return (x1*x1 + z1*z1 < dist*dist);
      }
      return false;
   }
   int leader() {
      int maxx = 0;
      for(short i=0;i<racers.length;++i) {
         maxx = Math.max(racers[i].rcentre.x, maxx);
      }
      return maxx;
   }
   public boolean click(int x, int y){
        if(!started && selected >= 0 && gamePanel.sentencefrommover == null) {
           removeMover(startmess);
           removeMover(sc);
           sc = null;
           started = true;
//           for(int i=0;i<racers.length;++i) { //testing !!!!!!
//             if(racers[i].num == selected) {
//                winnernum=i;
//                winner=racers[i];
//                break;
//             }
//           }
        }
        return false;
  }
  void gotwinner(racer rr) {
     winner[nextwinner] = rr;
     if(nextwinner==0) sc = new scoreboard();
     else sc.ended = false;
     rr.pic.setControl("h.0");
     if(++nextwinner ==  racers.length-1) {
        if(winner[0].num == selected) {             // end of game
           wantcoin = givescore;
           showmessage(u.edit(rgame.getParm("youwin"),String.valueOf(wantcoin)),0, mover.HEIGHT/3,mover.WIDTH,mover.HEIGHT*9/20);
        }
        else if(winner[1].num == selected) {
           wantcoin = (short)(givescore/3);
           showmessage(u.edit(rgame.getParm("second"),String.valueOf(wantcoin)),0, mover.HEIGHT/3,mover.WIDTH,mover.HEIGHT*9/20);
        }
        else {
          showmessage(rgame.getParm("hardluck"),0, mover.HEIGHT/3,mover.WIDTH,mover.HEIGHT*9/20);
        }
        exitbutton(mover.WIDTH/4,mover.HEIGHT/2);
     }
   }
  //-----------------------------------------------------------------------
  class racetrack extends mover3d {
     int width,depth;
     Color color = new Color(0,196,0);
     Color coldarker = u.darker(color, 0.85f);
     point3d_base[] edge;
     point3d_base[] edge2;
     point3d_base[] edge3;
     Polygon drawpoly;
     Polygon drawpoly2;
     Polygon drawpoly3;
     public int yracers[];
     racetrack() {
        super();
        depth = BASEU/2;
        width = BASEU;
        keepMoving = true;
        rcentre = new point3d_base(BASEU/2, BASEU*3/4, BASEZ+depth);
        edge = new point3d_base[] {new point3d_base(-width/2, 0, -depth),
                                 new point3d_base(width/2, 0, -depth),
                                 new point3d_base(width/2, 0, 0),
                                 new point3d_base(-width/2,0,0) };
        edge2 = new point3d_base[] {new point3d_base(-width/2, 0, -(depth*3/4)),
                                 new point3d_base(width/2, 0, -(depth*3/4)),
                                 new point3d_base(width/2, 0, -(depth*2/4)),
                                 new point3d_base(-width/2,0,-(depth*2/4)) };
        edge3 = new point3d_base[] {new point3d_base(-width/2, 0, -(depth*1/4)),
                                 new point3d_base(width/2, 0, -(depth*1/4)),
                                 new point3d_base(width/2, 0, 0),
                                 new point3d_base(-width/2,0,0) };
        w = mover.WIDTH;
        h = mover.HEIGHT;
        addMover(this,0, 0);
        drawpoly = getPolygon(edge);
        drawpoly2 = getPolygon(edge2);
        drawpoly3 = getPolygon(edge3);
      }
      public void paintm(Graphics g,long t) {
         drawpoly = getPolygon(edge);
         drawpoly2 = getPolygon(edge2);
         drawpoly3 = getPolygon(edge3);
         if(yracers==null){
             yracers = new int[5];
            yracers[0] = drawpoly.ypoints[3]*mover.HEIGHT/screenheight;
            yracers[1] = drawpoly3.ypoints[0]*mover.HEIGHT/screenheight;
            yracers[2] = drawpoly2.ypoints[3]*mover.HEIGHT/screenheight;
            yracers[3] = drawpoly2.ypoints[0]*mover.HEIGHT/screenheight;
            yracers[4] = drawpoly.ypoints[0]*mover.HEIGHT/screenheight;
         }   
         g.setColor(color);
         g.fillPolygon(drawpoly);       
         g.setColor(coldarker);
         g.fillPolygon(drawpoly2);
         g.fillPolygon(drawpoly3);
         g.setColor(Color.black);
         point3d_base p1 = transform(winningline/2,0,0);
         point3d_base p2 = transform(winningline/2,0,-depth);
         g.drawLine(p1.x,p1.y,p2.x,p2.y);
      }
  }
  //------------------------------------------------------------------------
  class racer extends mover3d {
    randrange_base rrvel,rrvel2;
    randrange_base rrangle;
    long stoptime,lasttime,falltime;
    boolean distract,falling,adjusted;
    short stoppers[],nextstop;
    int bedat,bedafter=-1;
    bedc bed;
    boolean bedused,gotoeat;
    String name;
    byte stopped;
     final byte SLEEP = 1;
     final byte EAT = 2;
     final byte FALLOVER = 3;
     final byte WINNER = 4;
     final byte LOSER = 5;
    sharkImage pic,pics[];
    int decisions[];    // x-values to take decisions
    short nextdecision;
    short num;
    double newangle;
    boolean fallforward,fast,hopping;
    long hopend,hoptime,hopstart;
    int hopfromx,hoptox,hopfromz,hoptoz;
     int downtime,sleeptime;
    int minz,maxz;
    eatc eat;
    boolean gotstopper;

    racer(short num1) {
       super();
       num = num1;
       int trackd = track.depth - trackborder;
       rcentre = new point3d_base( BASEU/20,  track.rcentre.y,
                        track.rcentre.z - track.depth/8 - trackd*num/4);
       minz =  track.rcentre.z - trackborder - trackd/4 - trackd*num/4;
       maxz =  track.rcentre.z  - trackborder - trackd*num/4;
       hoptoz = rcentre.z;
       hoptox = rcentre.x;
       keepMoving=true;
    }
     public void changeImage(long currtime) {
        double angle  = Math.PI/2*rrangle.next(currtime)/120;
        int vel = rrvel.next(currtime);
        if(!started ) return;
        if(lasttime==0) lasttime = currtime;
        if(falling) {
            if(currtime-falltime < FALLTIME) {
               newangle = Math.PI/2 * (currtime-falltime) / FALLTIME;
            }
            else {
               newangle = Math.PI/2;
               falling = false;
               stopped = FALLOVER;
               lastfall = stoptime = currtime;
               rrvel.currval = 0;
            }
        }
        else if(stopped  == FALLOVER && !completed) {
           if(currtime-stoptime > downtime) {
              stopped = 0;
              rrvel.lastt = rrangle.lastt = currtime;
              rrvel.currval = u.rand(mover.WIDTH/3);
           }
        }
        else if(stopped == SLEEP && !completed) {
           if(currtime-stoptime > sleeptime) {
              stopped = 0;
              rrvel.lastt = rrangle.lastt = currtime;
               rrvel.currval = u.rand(mover.WIDTH/3);
              pic.angle = 0;
              gotstopper = false;
//              bed.kill = true;
              bed = null;
           }
        }
        else if(stopped==0 && bed != null  && fast  && bed.drawn ) {
           if(rcentre.x > bed.rcentre.x - bed.w/2) {
              stopped = SLEEP;
              sleeptime = SLEEPTIME+u.rand(SLEEPTIME);
              pic.angle = Math.PI/2;
              rrangle.currval = 10;
              stoptime = currtime;
              bedused = true;
           }
           else angle = Math.atan2(bed.rcentre.z-rcentre.z, bed.rcentre.x-rcentre.x);
        }
        else if(stopped==0 && gotoeat) {
           if(rcentre.x >  eat.rcentre.x-eat.w/2) {
              stopped = EAT;
              pics[2].setControl("k.2");
              gotoeat  =false;
           }
        }
        if(stopped == EAT && !completed) {
          if(pics[2].isActiveControl("k.7")) {
            pics[2].setControl("k.8");
            stopped = 0;
            gotstopper = false;
          }
        }
        if(!falling && stopped == 0) {
           if(hopping) {
              if(gtime >= hopend) {
                 hopfromx = hoptox;
                 hopfromz = hoptoz;
                 hoptime = 600;
                 hoptox = Math.max(0,hopfromx+(int)(vel*Math.cos(angle)*hoptime/1000));
                 if(hoptox > winningline2+1) {
                     int oldhoptox = hoptox;
                     hoptox = winningline2+1;
                     hoptime = hoptime*(hoptox-hopfromx)/Math.max(1,oldhoptox-hopfromx);
                     hoptoz = (minz+maxz)/2;
                 }
                 hoptoz = Math.min(maxz,Math.max(minz,hopfromz+(int)(vel*Math.sin(angle)*hoptime/1000)));
                 if(gotstopper) {
                     int oldhoptox = hoptox;
                     hoptox = Math.min( hoptox,bedat);
                     hoptime = hoptime*(hoptox-hopfromx)/Math.max(1,oldhoptox-hopfromx);
                     hoptoz = (minz+maxz)/2;
                 }
                 hopend = (hopstart=gtime)+hoptime;
                 rcentre.x = hopfromx;
                 rcentre.z = hopfromz;
                 rcentre.y =  track.rcentre.y;
              }
              else {
                 rcentre.x = (int) (hopfromx + (hoptox-hopfromx)*(gtime-hopstart)/hoptime);
                 rcentre.z = (int) (hopfromz + (hoptoz-hopfromz)*(gtime-hopstart)/hoptime);
                 int dx = rcentre.x - (hopfromx+hoptox)/2;
                 int dz = rcentre.z - (hopfromz+hoptoz)/2;
                 int ddx = (hoptox-hopfromx)/2;
                 int ddz = (hoptoz-hopfromz)/2;
                 rcentre.y = track.rcentre.y - ((ddx*ddx+ddz*ddz) - (dx*dx+dz*dz))/1000;
              }
           }
           else {
              rcentre.x += (int)(vel*Math.cos(angle)*(currtime-lasttime)/1000);
              rcentre.x = Math.max(rcentre.x, BASEU/20);
              rcentre.z += (int)(vel*Math.sin(angle)*(currtime-lasttime)/1000);
              rcentre.x = Math.min(track.rcentre.x + track.width/2, rcentre.x);
              if(rcentre.z <minz) {
               rcentre.z  = minz;
               rrangle.currval = -Math.abs(rrangle.currval);
              }
              else if(rcentre.z>maxz) {
                rcentre.z=maxz ;
                rrangle.currval = Math.abs(rrangle.currval);
              }
           }
//           rcentre.z = Math.max(track.rcentre.z - track.depth,
//                          Math.min(track.rcentre.z-1, rcentre.z));
           if(rcentre.x > winningline2 ) {
              stopped = WINNER;
              gotwinner(this);
           }
        }
        lasttime=currtime;
     }
     void addstopper() {
        int i;
        int got = Math.max(12,Math.min(BASEU/3,winningline2 - BASEU/6 - rcentre.x));
        bedat = rcentre.x + got/4 + u.rand(got*3/4);
        if(stoppers[nextstop] == BED) {
           bed = new bedc(this);
        }
        else if(stoppers[nextstop] == EATSTOP) {
          if(eat != null) {removeMover(eat); eat=null;}
          gotoeat = true;
          eat = new eatc(pics[2],this);
        }
        gotstopper = true;
        nextstop = (short)(++nextstop%stoppers.length);
     }
     void setdecisions() {
        decisions = new int[6];
        int start = BASEU/20;
        int tot = winningline2 - start - BASEU/6;
        decisions[0] = start + u.rand(tot/4);
        decisions[1] = start + tot/4 + u.rand(tot/4);
        decisions[2] = start + tot/2 + u.rand(tot/6);
        decisions[3] = start + tot*4/3 + u.rand(tot/6);
        decisions[4] = start + tot*6/3 + u.rand(tot/6);
        decisions[5] = BASEU;
     }
     boolean isleadfast() {
       for(short i=0;i<racers.length;++i) {
          if(racers[i].fast && racers[i].rcentre.x > rcentre.x) return false;
       }
       return true;
     }
     void fall(int i, int j) {
        fallforward = racers[i].rcentre.x <= racers[j].rcentre.x;
        racers[i].rcentre.y = track.rcentre.y;
        racers[j].rcentre.y = track.rcentre.y;
        falling = true;
        falltime = gtime;
        downtime = DOWNTIME + u.rand(DOWNTIME);
     }
      public void paintm(Graphics g,long t) {
        if(stopped == EAT) return;
        if(!adjusted) {
            pic.adjustSize(screenwidth,screenheight);
            if(pics != null) pics[1].adjustSize(screenwidth,screenheight);
        }
        adjusted=true;
        int wi = pic.w*screenwidth/mover.WIDTH;
        int hi = pic.h*screenheight/mover.HEIGHT;
        point3d_base bot = transform(0,0, 0);
        if(stopped == SLEEP && pics != null) {
           Rectangle r = bed.pic.getRect(1);
           wi = pics[1].w*screenwidth/mover.WIDTH;
           hi = pics[1].h*screenheight/mover.HEIGHT;
           pics[1].paint(g, xmin = r.x,
                        ymin = r.y + r.height - hi,
                        wi, hi);

        }
        else if(falling) {
           if(fallforward) pic.angle = -newangle;
           else pic.angle = newangle;
           pic.paint(g, xmin = bot.x - (int)(hi*Math.sin(-pic.angle)/2),
                        ymin = bot.y-(int)(hi*Math.cos(-pic.angle)/2)-wi/2,
                        wi, hi);
        }
        else if(stopped == WINNER) {
           pic.angle = 0;
           pic.paint(g, xmin = bot.x-wi/2, ymin = bot.y-hi,wi, hi);
//           g.setColor((u.rand(2)==0)?Color.yellow:Color.red);
//           g.drawRect(xmin,ymin,wi,hi);
        }
        else if(stopped != 0 && pics != null) {
           wi = pics[1].w*screenwidth/mover.WIDTH;
           hi = pics[1].h*screenheight/mover.HEIGHT;
           pics[1].paint(g, xmin = bot.x - wi/2,
                        ymin = bot.y-hi,
                        wi, hi);
        }
        else {
          pic.angle = 0;
          pic.paint(g, xmin = bot.x-wi/2, ymin = bot.y-hi,wi, hi);
        }        
        if(selected == num) {
           g.setColor(Color.white);
           g.drawRect(xmin,ymin,wi,hi);
        }
        xmax = xmin+wi;
        ymax = ymax+hi;
     }
  }
  //------------------------------------------------------------------------
  class hare extends racer {
    long stoptime;
    hare(short num) {
       super(num);
       int i;
       pics = sharkImage.randomset(new String[]{"hare_","harestop_","hareeat_"});
       pic = pics[0];
       rrvel = new randrange_base(mover.WIDTH/20,mover.WIDTH/3,mover.WIDTH/6);
       rrvel.currval = 0;
       rrangle = new randrange_base(-100,+100,300);
       pic.h = pics[1].h = mover.HEIGHT/10;
       pic.w = pics[1].w = mover.WIDTH/16;
       fast = true;
       stoppers = u.shuffle(u.select((short)2,(short)2));
       setdecisions();
       name = rgame.getParm("harename");
       addMover(this,0,edge1 + (1+num*2)*(edge2-edge1)/8);
     }
  }
  //------------------------------------------------------------------------
  class kanger extends racer {
    long stoptime;
    kanger(short num) {
       super(num);
       int i;
       pics = sharkImage.randomset(new String[]{"kanga_","kangastop_","kangaeat_"});
       pic = pics[0];
       rrvel = new randrange_base(mover.WIDTH/20,mover.WIDTH/3,mover.WIDTH/6);
       rrvel.currval = 0;
       rrangle = new randrange_base(-100,+100,300);
       pic.h = pics[1].h = mover.HEIGHT/10;
       pic.w = pics[1].w = mover.WIDTH/15;
       fast = true;
       stoppers = u.shuffle(u.select((short)2,(short)2));
       setdecisions();
       name = rgame.getParm("kanganame");
       hopping=true;
       addMover(this,0,edge1 + (1+num*2)*(edge2-edge1)/8);
     }
  }
  //------------------------------------------------------------------------
  class tortoise extends racer {
    long stoptime;
    tortoise(short num) {
       super(num);
       pic = sharkImage.random("tortoise_");
       rrvel = new randrange_base(mover.WIDTH/30,mover.WIDTH/12,mover.WIDTH/12);
       rrangle = new randrange_base(-50,+50,50);
       rrvel2 = new randrange_base(mover.WIDTH/20,mover.WIDTH/6,mover.WIDTH/2);
       pic.h = mover.HEIGHT/12;
       pic.w = mover.WIDTH/16;
       name = rgame.getParm("tortname");
       addMover(this,0,edge1 + (1+num*2)*(edge2-edge1)/8);
    }
  }
  //------------------------------------------------------------------------
  class snail extends racer {
    long stoptime;
    snail(short num) {
       super(num);
       pic = sharkImage.random("snail_");
       rrvel = new randrange_base(mover.WIDTH/20,mover.WIDTH/14,mover.WIDTH/24);
       rrvel2 = new randrange_base(mover.WIDTH/20,mover.WIDTH/6,mover.WIDTH/2);
       rrangle = new randrange_base(-50,+50,50);
       pic.h = mover.HEIGHT/12;
       pic.w = mover.WIDTH/20;
       name = rgame.getParm("snailname");
       addMover(this,0,edge1 + (1+num*2)*(edge2-edge1)/8);
    }
  }
  //-----------------------------------------------------------------------
  class bedc extends mover3d {
     int width,depth;
     sharkImage pic;
     bedc(racer bracer) {
        super();
        pic = beds[nextbed];
        nextbed = (short)(++nextbed%beds.length);
        pic.w = mover.WIDTH/10;
        pic.h = mover.HEIGHT/6;
        pic.adjustSize(gamePanel.screenwidth,gamePanel.screenheight);
        w = pic.w;
        h = pic.h;
        rcentre = new point3d_base( bracer.bedat,  track.rcentre.y,
                        (bracer.minz + bracer.maxz)/2 );
        addMover(this,0,0);
     }
      public void paintm(Graphics g,long t) {
        int wi = pic.w*screenwidth/mover.WIDTH;
        int hi = pic.h*screenheight/mover.HEIGHT;
        point3d_base bot = transform(0,0, 0);
        pic.paint(g, xmin = bot.x-wi/2, ymin = bot.y-hi,wi, hi);
        xmax = xmin+wi;
        ymax = ymax+hi;
     }
  }
  //-----------------------------------------------------------------------
  class eatc extends mover3d {
     int width,depth;
     sharkImage pic;
     eatc(sharkImage pic2,racer bracer) {
        super();
        pic = pic2;
        pic.w = mover.WIDTH/8;
        pic.h = mover.HEIGHT/8;
        pic.adjustSize(gamePanel.screenwidth,gamePanel.screenheight);
        w = pic.w;
        h = pic.h;
        rcentre = new point3d_base( bracer.bedat,  track.rcentre.y,
                        (bracer.minz + bracer.maxz)/2 );
        pic.setControl("k.1");
        addMover(this,0,0);
        gamePanel.puttobottom(this);
     }
      public void paintm(Graphics g,long t) {
        int wi = pic.w*screenwidth/mover.WIDTH;
        int hi = pic.h*screenheight/mover.HEIGHT;
        point3d_base bot = transform(0,0, 0);
        pic.paint(g, xmin = bot.x-wi/2, ymin = bot.y-hi,wi, hi);
        xmax = xmin+wi;
        ymax = ymax+hi;
     }
  }
  //------------------------------------------------------------------
  class scoreboard extends mover {
    Font f;
    FontMetrics m;
    int xinc,yinc;
    scoreboard() {
      super(false);
      String teststring;
      x = mover.WIDTH/4;
      w = mover.WIDTH/2;
      if(!started) y=mover.HEIGHT/4;
      else y = 0;
      h = mover.HEIGHT/3;
      f = sizeFont(new String[]{teststring="1. kangeroo"},w*screenwidth/mover.WIDTH,h*screenheight/mover.HEIGHT/4);
      m = getFontMetrics(f);
      xinc = w*screenwidth/mover.WIDTH/2 - m.stringWidth(teststring)/2;
      yinc = m.getAscent();
      addMover(this,x,y);
    }
    public void paint(Graphics g, int x1, int y1,int w1, int h1) {
      g.setFont(f);
      if(!started) {
            if(gamePanel.mousey < y+h)
                selected= Math.max(0,Math.min(3, (gamePanel.mousey-y)*4/h));
            for(short i=0;i<racers.length;++i) {
               g.setColor(racers[i].num==selected?Color.white:Color.black);
               g.drawString(racers[i].name,x1+xinc,
                                                    y1+h1/4*racers[i].num + yinc);
            }
      }
      else for(short i=0;i<nextwinner;++i) {
        g.setColor(winner[i].num==selected?Color.white:Color.black);
        g.drawString(String.valueOf(i+1) + '.' + winner[i].name,x1+xinc,
                                                    y1+h1/4*i + yinc);
      }
    }
  }
}