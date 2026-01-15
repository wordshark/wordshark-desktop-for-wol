package shark;

import java.awt.*;

public class lottery extends sharkGame {
   int bwidth,bheight;
   lmachine  fm = new lmachine();
   long startmove, endmove, nextcoin,lasttime,lastaddball,lastout,stopping;
   int movespeed;
   short wantfruit,givescore[];//,wantcoin;
   boolean secondgo,exitnow,stopping2;
   mover lastmessage,pickmessage;
   Font numberfont;
   FontMetrics nmetrics;
   coin_base coinpile[];
   short cointot;
   String holdnarr;
   boolean droppingballs;
   picknumber numbers[] = new picknumber[9];
   short eorder[] = u.shuffle(u.select((short)9,(short)9));
   int nextout;
   int dyfactor = 150 + u.rand(100);

   short balltot;
   ball balls[] = new ball[9];
   short ballorder[],wantnum[]=new short[3],wanttot,outnums[] = new short[4],totout;
   int numberwidth, numberheight;

   public lottery() {
    errors = false;
//    gamescore1 = true;
//    peeps = true;
//    listen= true;
//    peep = true;
//    wantspeed = true;
    sharkStartFrame.reward = (short)Math.max(3,sharkStartFrame.reward);
    givescore = new short[]  {(short)(sharkStartFrame.reward/3),(short)(sharkStartFrame.reward*2/3),(short)(sharkStartFrame.reward) };
    sharkStartFrame.reward = 0;
    buildTopPanel();
//    gamePanel.showSprite = false;
    fm.w = mover.WIDTH/2;
    fm.h = mover.HEIGHT*7/8;
    fm.recolor(3,this.background);
    fm.recolor(5,u.brightcolor());
    fm.recolor(11,this.background);
    Color main = u.darkcolor();
    fm.recolor(0,main);
    fm.recolor(1,main);
    fm.recolor(2,main);
    addMover(fm,mover.WIDTH/4 - fm.w/2,(mover.HEIGHT-fm.h)/2);
    showmessage(u.edit(rgame.getParm("match2"), String.valueOf(givescore[0]))
               + "|" +  u.edit(rgame.getParm("match2b"), String.valueOf(givescore[1]))
               + "|" +  u.edit(rgame.getParm("match3"), String.valueOf(givescore[2])),
     mover.WIDTH/2, mover.HEIGHT/24,mover.WIDTH,mover.HEIGHT/3);
     pickmessage =  showmessage(rgame.getParm("pick3"), mover.WIDTH*7/12,mover.HEIGHT*2/3,mover.WIDTH*11/12,mover.HEIGHT);
    bwidth = mover.WIDTH/36;
    bheight = bwidth*screenwidth/screenheight;
    wordfont = sizeFont(null,"4",bwidth,bheight*5/4);
    numberfont = sizeFont(null,"4",mover.WIDTH/10,mover.HEIGHT/10);
    metrics = getFontMetrics(wordfont);
    nmetrics =  getFontMetrics(numberfont);
    ballorder=u.shuffle(u.select((short)9,(short)9));
    numberwidth = mover.WIDTH/14;
    numberheight = numberwidth*screenwidth/screenheight;
    for(short i=0;i<9;++i) {
       numbers[i] = new picknumber((short)(i+1), mover.WIDTH/2 + mover.WIDTH/16 + mover.WIDTH/6*(i%3),
                                      mover.HEIGHT*3/8 + mover.HEIGHT/8*(i/3));
    }
  }
  //-----------------------------------------------------------------------
  public void afterDraw(long t) {
    if(!fm.drawn) return;
    boolean stopped = false, ok = true;
    int i, x=0,x1,y,br = bwidth/2*screenwidth/mover.WIDTH,brsq = br*br;
    Rectangle cylinder = fm.getRect(7);
    Rectangle channel;
    int cx = cylinder.x+cylinder.width/2;
    int cy = cylinder.y+cylinder.height/2;
    int ca = cylinder.width/2;
    int cb = cylinder.height/2;
    Rectangle r = fm.getRect(3);
    Polygon pout =  fm.getPolygon(8);
    Rectangle rout = pout.getBounds();
    if(balltot<9 && gtime > lastaddball+200) {
        lastaddball = gtime;
        x = r.x+br+(balltot%3)*(r.width/3);
        y = (r.y+r.height) - (balltot/3)* br*2 - br;
        balls[balltot] = new ball((byte)(ballorder[balltot]+1),x,y);
        ++balltot;
    }
    else if(droppingballs && balltot == 9) {
      boolean notend = false;
      for(i=0;i<balltot;++i) {
         if(!balls[i].incylinder) {
              balls[i].repos(balls[i].xs,
                    (int)(balls[i].ys + screenheight*(gtime-lasttime)/5000));
              if(balls[i].ys > cylinder.y+br)  balls[i].incylinder = true;
              else notend = true;
              gamePanel.copyall = true;
        }
      }
      if(!notend) {
         droppingballs = false;
         gamePanel.copyall = true;
         fm.setControl("drop.1");
         fm.setControl("exit.2");
         fm.setControl("normal");
         lastout = gtime;
      }
    }
    else if(wantcoin > 0 && t > nextcoin) {
          noise.coindrop();
          dropcoin();
          nextcoin = t + 600;
          score(1);
          --wantcoin;
    }
                        // balls in cylinder
    int push = (int)(screenheight*(gtime-lasttime)/50),bounce;
    for(i=0;i<balltot;++i) {
       double a;
       if(balls[i].incylinder) {
          if(!balls[i].finished ) {
             if(stopping2) {
                x = balls[i].xs;
                y = balls[i].ys;
                balls[i].dy += (int)(screenheight*(gtime-lasttime)/dyfactor);
                y += balls[i].dy/100;
                x += balls[i].dx/100;
                if((x-cx)*(x-cx) + (y-cy)*(y-cy) > ca*cb)  {
                   if(balls[i].dx != 0) {
                      x = cx-ca/2 + u.rand(ca);
                      balls[i].dx = 0;
                      balls[i].dy = 3;
                   }
                   int endy = cy + (int)((cb-br) *  Math.sqrt(1 - (double)(x-cx)*(x-cx)/((ca-br)*(ca-br))));
                   if(y >= endy) {y=endy;balls[i].finished = true;}
                }
             }
             else {
                x = balls[i].xs;
                y = balls[i].ys;
                balls[i].dy += (int)(screenheight*(gtime-lasttime)/dyfactor);
                y += balls[i].dy/100;
                x += balls[i].dx/100;
                if((x-cx)*(x-cx) + (y-cy)*(y-cy) > ca*cb)  {
                  a = Math.atan2(y-cy, x-cx);
                  bounce = ((int)Math.sqrt(balls[i].dx*balls[i].dx+balls[i].dy*balls[i].dy)/2);
                  balls[i].dy = (int)(push * Math.cos(-a) - bounce * Math.sin(a))-8+u.rand(17);
                  balls[i].dx = (int)(push * Math.sin(-a) - bounce * Math.cos(a))-8+u.rand(17);
                  x = cx + (int)((ca-br)*Math.cos(a));
                  y = cy + (int)((cb-br)*Math.sin(a));
                }
                else if( (x-cx)*(x-cx) + (y-cy)*(y-cy) < ca*cb/2)  {
                  a = Math.atan2(y-cy, x-cx);
                  balls[i].dy += (int)(push*(3+u.rand(2))/3 * Math.cos(a));
                  balls[i].dx -= (int)(push*(3+u.rand(2))/3 * Math.sin(a));
                }
             }
             balls[i].repos(x,y);
          }
       }
     }
     if(lastout > 0 && gtime > lastout + 1600) {
         int lowest=eorder[nextout];
//         for(i=0;i<balltot;++i) {
//            if(balls[i].incylinder &&  (lowest == -1 || balls[i].y > balls[lowest].y))
//               {lowest = i;}
//         }
         if(balls[lowest].ys > cy + (cb - br)*3/4) { // ---rb  2005/07/10 -----------------------
            ++nextout;
            balls[lowest].incylinder = false;
            balls[lowest].repos(rout.x - br/2 + rout.width, rout.y +  br*2);
            balls[lowest].moveto(rout.x*mover.WIDTH/screenwidth+bwidth*totout+bwidth/8,
                                 (rout.y +rout.height - br*2 - br*totout/6)*mover.HEIGHT/screenheight,
                                 1000);
            outnums[totout++] = balls[lowest].number;
            numbers[balls[lowest].number-1].out = true;
            numbers[balls[lowest].number-1].ended = false;
            if(totout == 4 || totout == 3 && gotok(3)==3
                || totout == 3 && gotok(3) < 2) {lastout = 0; stopping2=true;stopping = gtime+1000;}
            else                                          lastout = gtime;
         }
     }
     if(stopping!=0 && gtime>stopping) {
        stopping = 0;
        fm.setControl("exit.1");
        fm.setControl("rot.1");
        if(gotok(3)  == 3)
           showmessage(u.edit(rgame.getParm("jackpot"),String.valueOf(wantcoin=givescore[2])), mover.WIDTH/2, mover.HEIGHT*5/8,mover.WIDTH,mover.HEIGHT*7/8);
        else if(gotok(4)  == 3)
           showmessage(u.edit(rgame.getParm("win2b"),String.valueOf(wantcoin=givescore[1])), mover.WIDTH/2, mover.HEIGHT*5/8,mover.WIDTH,mover.HEIGHT*7/8);
        else if(gotok(3)  == 2)
           showmessage(u.edit(rgame.getParm("win2"),String.valueOf(wantcoin=givescore[0])), mover.WIDTH/2, mover.HEIGHT*5/8,mover.WIDTH,mover.HEIGHT*7/8);
        else showmessage(rgame.getParm("hardluck"), mover.WIDTH/2, mover.HEIGHT*5/8,mover.WIDTH,mover.HEIGHT*7/8);
        this.exitbutton(mover.WIDTH/2,mover.HEIGHT*7/8);
        exitnow = true;
    }
    lasttime = gtime;
  }
  //--------------------------------------------------------------------
  short gotok(int out) {
     short i,j,tot=0;
     for(i=0;i<out;++i) {
        for(j=0; j<3; ++j) {
          if(wantnum[j] == outnums[i]) {++ tot; break;}
        }
     }
     return tot;
  }
  //--------------------------------------------------------------------
  boolean touchesballs(int x, int y, int brsq, int exclude) {
      for(short i=0;i<balltot;++i) {
         if(i != exclude && (x-balls[i].xs)*(x-balls[i].xs) + (y-balls[i].ys)*(y-balls[i].ys) < brsq*4)
             return true;
      }
      return false;
  }
  //--------------------------------------------------------------------
  void dropcoin() {
     Rectangle r = fm.getRect(4);
     int coinrad = mover.WIDTH/60;
     int cointhick =   mover.WIDTH/200;
     if(coinpile == null || coinpile.length < wantcoin) {coinpile = new coin_base[wantcoin]; cointot = 0;}
     coin_base cc = new coin_base(coinrad, cointhick,
                        (r.x+coinrad*screenwidth/mover.WIDTH+u.rand(r.width-coinrad*2*screenwidth/mover.WIDTH)-screenwidth/2)*mover3d.BASEU/screenmax + mover3d.BASEU/2,
                        (r.y+r.height-coinrad*screenheight/mover.HEIGHT-screenheight/2)*mover3d.BASEU/screenmax + mover3d.BASEU/2,
                         Math.PI*u.rand(100)/400,0,
                         -Math.PI/4 + Math.PI/2*u.rand(100)/100,
                         Color.yellow,Color.orange,
                         fm.y+fm.h);
     cc.mustSave = true;
     cc.endax = Math.PI/2*u.rand(100)/100;
     cc.endaz = -Math.PI/2 + Math.PI*u.rand(100)/100;
     cc.pile = coinpile;
     coinpile[cointot++] = cc;
     addMover(cc,0,0);
  }
  //-----------------------------------------------------------
  class lmachine extends sharkImage {
     short i;
     short o[][] = new short[3][];
     int dy[] = new int[3];
     int fy[] = new int[3];      // y of start of fruit carrier
     int savefy[] = new int[3];  // fy in mover units
     int stopat[] = new int[3];  // count in fruits where it stopped
     long lasttime = gtime();
     boolean holding[] = new boolean[3];
     boolean pulling,pulled;
     long pulltime,spintime[] = new long[3];
     lmachine() {
        super("lottery",false);
     }
     public void paint(Graphics g, int x, int y, int w, int h) {
        super.paint(g,x,y,w,h);
     }
  }
  class ball extends mover{
     byte number;
     String display;
     Color color;
     int xs,ys,dx,dy;     // screen coords of centre
     boolean incylinder,finished;
     ball(byte num,int x, int y) {
        super(false);
        number = num;
        display = String.valueOf((int)num);
        color=u.brightcolor();
        w = bwidth;
        h = bheight;
        xs = x;
        ys = y;
        keepMoving = true;
        dontclear = true;
        addMover(this, x*mover.WIDTH/screenwidth-bwidth/2,  y*mover.HEIGHT/screenheight-bheight/2);
     }
     void repos(int xx, int yy) {
        xs=xx;
        ys=yy;
        x = tox = fromx = xx*mover.WIDTH/screenwidth-bwidth/2;
        y = toy = fromy = yy*mover.HEIGHT/screenheight-bheight/2;
     }
     public void paint(Graphics g,int x1, int y1, int w1, int h1) {
        g.setColor(color);
        g.fillOval(x1,y1,w1,h1);
        g.setColor(Color.black);
        g.setFont(wordfont);
        g.drawString(display, x1+w1/2-metrics.stringWidth(display)/2,
                          y1+h1/2-metrics.getAscent()/2 + metrics.getAscent());
     }
  }
  class picknumber extends mover{
     byte number;
     String display;
     boolean out,selected;
     picknumber(short num, int x, int y) {
        super(false);
        number = (byte)num;
        display = String.valueOf(num);
        w = numberwidth;
        h = numberheight;
        addMover(this, x-w/2,  y-h/2);
     }
     public void mouseClicked(int mx,int my) {
        if(wanttot == 3) return;
        ended=false;
        selected=true;
        for(short i=0;i<wanttot;++i)
            if(wantnum[i] == number) {noise.beep(); return;}
        wantnum[wanttot++] = number;
        if(wanttot == 3) {
           droppingballs=true;
           fm.setControl("rot.2");
           fm.setControl("drop.2");
           fm.setControl("opening");
           if(pickmessage != null) {removeMover(pickmessage);pickmessage=null;}
        }
    }
     public void paint(Graphics g,int x1, int y1, int w1, int h1) {

        g.setColor(Color.red);
        g.setFont(numberfont);
        g.drawString(display, x1+w1/2-nmetrics.stringWidth(display)/2,
                          y1+h1/2-nmetrics.getAscent()/2 + nmetrics.getAscent());
        int boxrad = w1/3;
        int boxleft = x1+w1/2-boxrad;
        int boxtop = y1+h1/2-boxrad;
        int boxright = x1+w1/2+boxrad;
        int boxbot = y1+h1/2+boxrad;
        int lip = boxrad/8;
        int thick = boxrad/6;
        g.drawPolyline(new int[] {boxleft,boxleft,boxright,boxright},
                       new int[] {boxtop+lip,boxtop,boxtop,boxtop+lip}, 4);
        g.drawPolyline(new int[] {boxleft,boxleft,boxright,boxright},
                       new int[] {boxbot-lip,boxbot,boxbot,boxbot-lip}, 4);
        if(selected) {
           g.setColor(Color.black);
           g.fillRect(x1+w1/2,y1,thick,h1);
        }
        if(out && selected) {
           if(totout==4 && outnums[3] == number) g.setColor(Color.red);
           else                                  g.setColor(Color.white);
           g.drawOval(x1,y1,w1,h1);
        }
     }
  }
}
