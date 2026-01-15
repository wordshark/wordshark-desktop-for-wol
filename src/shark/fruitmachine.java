package shark;

import java.awt.*;

public class fruitmachine extends  sharkGame {
   int bwidth,bheight;
   fmachine  fm = new fmachine();
   sharkImage fruitlist[] = new sharkImage[]{ new sharkImage("cherry",false),
                                           new sharkImage("banana",false),
                                           new sharkImage("pineapple",false)};
   long startmove, endmove, nextcoin;
   int movespeed;
   short wantfruit,givescore[];//,wantcoin;
   boolean secondgo,exitnow;
   mover lastmessage,pullmessage;
   Font holdfont,gofont;
   coin_base coinpile[];
   short cointot;
   String holdnarr,gonarr;
   int clickxs,clickys;

   public fruitmachine() {
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
    gamePanel.clearWholeScreen = true;
//    gamePanel.showSprite = false;
    fm.w = mover.WIDTH/2;
    fm.h = mover.HEIGHT*7/8;
//    fm.adjustSize(gamePanel.screenwidth,gamePanel.screenheight);
    for(short i=0;i<3;++i) {
       fruitlist[i].dontclear = true;
    }
    holdnarr =  rgame.getParm("hold");
    gonarr =  rgame.getParm("go");
    addMover(fm,mover.WIDTH/4 - fm.w/2,(mover.HEIGHT-fm.h)/2);
    showmessage(u.edit(rgame.getParm("cherryscore"),String.valueOf(givescore[0]))
               + "|" +  u.edit(rgame.getParm("bananascore"), String.valueOf(givescore[1]))
               + "|" +  u.edit(rgame.getParm("pineapplescore"), String.valueOf(givescore[2])),
     mover.WIDTH/2, mover.HEIGHT/8,mover.WIDTH,mover.HEIGHT/2);
     pullmessage =  showmessage(rgame.getParm("pulldown"), fm.x+fm.w,fm.y,mover.WIDTH,fm.y+mover.HEIGHT/10);

  }
  public void afterDraw(long t) {
    boolean stopped = false, ok = true;
    int i;
    if(wantcoin > 0 && t > nextcoin) {
          noise.coindrop();
          dropcoin();
          nextcoin = t + 600;
          score(1);
          --wantcoin;
    }
    if(fm.pulling && lastmessage != null) {
       removeMover(lastmessage);
       lastmessage = null;
    }
    if(fm.pulling && pullmessage != null) {
       removeMover(pullmessage);
       pullmessage = null;
    }
    if(fm.pulled) {
       for(i=0;i<3;++i) {
          if(fm.dy[i] != 0) {stopped = false; break;}
          stopped = true;
          if(fm.stopat[i] != fm.stopat[0]) ok = false;
          fm.ended = false;
          fm.keepMoving = false;
       }
       if(stopped && !exitnow) {
//        wantcoin = 20;
//        nextcoin = t-1;
          if (ok) {
            if(fm.stopat[0]==0) lastmessage=showmessage(u.edit(rgame.getParm("wincherry"),String.valueOf(givescore[0])), mover.WIDTH/2, mover.HEIGHT/2,mover.WIDTH,mover.HEIGHT*3/4);
             else if(fm.stopat[0]==1) lastmessage=showmessage(u.edit(rgame.getParm("winbanana"),String.valueOf(givescore[1])), mover.WIDTH/2, mover.HEIGHT/2,mover.WIDTH,mover.HEIGHT*3/4);
             else if(fm.stopat[0]==2) lastmessage=showmessage(u.edit(rgame.getParm("winpineapple"),String.valueOf(givescore[2])), mover.WIDTH/2, mover.HEIGHT/2,mover.WIDTH,mover.HEIGHT*3/4);
             wantcoin = givescore[fm.stopat[0]];
             nextcoin = t-1;
             this.exitbutton(mover.WIDTH*3/4,mover.HEIGHT*7/8);
             exitnow = true;
          }
          else if(!secondgo) {
             secondgo = true;
             lastmessage=showmessage(rgame.getParm("secondgo"), mover.WIDTH/2, mover.HEIGHT/2,mover.WIDTH,mover.HEIGHT*3/4);
             fm.pulled = false;
          }
          else {
             showmessage(rgame.getParm("hardluck"), mover.WIDTH/2, mover.HEIGHT/2,mover.WIDTH,mover.HEIGHT*3/4);
             this.exitbutton(mover.WIDTH*3/4,mover.HEIGHT*7/8);
             exitnow = true;
          }
       }
    }
  }
  //--------------------------------------------------------------------
  void dropcoin() {
     Rectangle r = fm.getRect(10);
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
     cc.endax = Math.PI/2*u.rand(100)/100;
     cc.endaz = -Math.PI/2 + Math.PI*u.rand(100)/100;
     cc.pile = coinpile;
     coinpile[cointot++] = cc;
     addMover(cc,0,0);
  }
  public boolean click(int x, int y){
    if(fm.pulling) fm.mouseClicked(x,y);
    return false;
  }
  //-----------------------------------------------------------
  class fmachine extends sharkImage {
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
     Color brightgreen = new Color(0,255,0);
     Color darkgreen = new Color(0,196,0);
     Color endgreen = new Color(0,64,0);
     fmachine() {
        super("fruit_machine",false);
        recolor(0,u.darkcolor());
        boolean ok=false;
        do {
           ok = false;
           for(i=0;i<3;++i) {
              o[i] = u.shuffle(u.select((short)3,(short)3));
              stopat[i] = o[i][1]; // just in case it is held
              if(stopat[i] != stopat[0]) ok = true;
           }
        } while(!ok);
     }
     public void paint(Graphics g, int x, int y, int w, int h) {
        super.paint(g,x,y,w,h);
        Rectangle r,slot[] = new Rectangle[] {getRect(1), getRect(2), getRect(3) };
        int slotheight = slot[0].height;
        int i,j,xx,yy;
        int maxint = screenheight/10; //max dist of left slot in 1 sec
        for(i=0;i<3;++i) {
            if(pulling && !holding[i] && gtime > pulltime+100) {
               dy[i] = Math.max(dy[i], (int)(Math.max(gamePanel.mouseys-clickys,screenheight/10)*maxint*(i+1)*500/screenheight/(gtime - pulltime)));
               dy[i] = Math.min(maxint*(i+1), dy[i]);
               spintime[i] = Math.min(5000,2000*dy[i]/maxint);
            }
            fy[i] = savefy[i]*screenheight/mover.HEIGHT;
            if(dy[i] != 0) {
               if(pulled) fy[i] += Math.max(0,(int)(dy[i]*(spintime[i]-gtime+pulltime)/spintime[i]));
               else fy[i] += dy[i];
               while(fy[i] > 0) fy[i] -= slotheight;
               if(pulled && gtime - pulltime > spintime[i]) {
                  dy[i] = 0;
                  stopat[i] = o[i][(j=(-fy[i] +slotheight/2)/(slotheight/3))%3];
                  fy[i] =  slotheight/2 - (j*slotheight/3 + slotheight/6);
               }
               savefy[i] = fy[i]*mover.HEIGHT/screenheight;
            }
            Shape ss = g.getClip();
            g.clipRect(slot[i].x, slot[i].y, slot[i].width,slot[i].height);
            for(j=0;j<6;++j) {
               yy = slot[i].y + fy[i]  + j*slotheight/3 + slotheight/36;
               if(yy < slot[i].y+slot[i].height && yy + slotheight*5/18 > slot[i].y) {
                  fruitlist[o[i][j%3]].paint(g,slot[i].x+slot[i].width/6, yy, slot[i].width*2/3, slotheight*5/18);
               }
            }
            g.setClip(ss);
            r = getRect(11+i);
            if(holding[i]) {
               g.setColor(Color.red);
               g.fillRect(r.x,r.y,r.width,r.height);
               u.buttonBorder(g,r,Color.red,false);
            }
            else {
               g.setColor(Color.gray);
               g.fillRect(r.x,r.y,r.width,r.height);
               u.buttonBorder(g,r,Color.gray,true);
            }
            if(holdfont==null) holdfont=sizeFont(null,g,holdnarr,r.width*3/4,r.height);
            g.setFont(holdfont);
            g.setColor(Color.black);
            FontMetrics m = g.getFontMetrics();
            g.drawString(holdnarr, r.x+(r.width-m.stringWidth(holdnarr))/2,r.y+(r.height-m.getHeight())/2+m.getAscent());
        }
        r = getRect(15);
        Color col = getcolor(15);
        if(!exitnow && wantcoin==0 && pulled) {
           g.setColor(brightgreen);
           g.fillRect(r.x,r.y,r.width,r.height);
           u.buttonBorder(g,r,brightgreen,false);
        }
        else {
           g.setColor(exitnow?endgreen:darkgreen);
           g.fillRect(r.x,r.y,r.width,r.height);
           u.buttonBorder(g,r,darkgreen,true);
        }
        if(gofont==null) gofont=sizeFont(null,g,gonarr,r.width*3/4,r.height);
        g.setFont(gofont);
        g.setColor(Color.black);
        FontMetrics m = g.getFontMetrics();
        g.drawString(gonarr, r.x+(r.width-m.stringWidth(gonarr))/2,r.y+(r.height-m.getHeight())/2+m.getAscent());
        g.setColor(Color.black);
        g.fillRect(slot[0].x,slot[0].y+slotheight/2-1,slot[2].x+slot[2].width-slot[0].x,3);
        lasttime = gtime;
     }
     public void mouseClicked(int mx, int my) {
        int i;
        Rectangle r;
        if(pulling) {
           if(gamePanel.mouseys - clickys > screenheight/10) {
                fixtomouse((short)-1);
                 pulled = true;
                 pulltime=gtime;
                 pulling = false;
                 ended=false;
                 keepMoving=true;
           }
        }
        else   if(!exitnow && wantcoin==0 && !pulled && !pulling) {
              r = getRect(4);
              if(mx >= r.x && mx <= r.x+r.width && my >= r.y && my <= r.y+r.height) {
                 ended = false;
                 keepMoving = true;
                 pulling = true;
                 clickxs = gamePanel.mousexs;
                 clickys = gamePanel.mouseys;
                 pulltime = gtime;
                 fixtomouse((short)4);
                 return;
              }
              for(i=0;i<3;++i) {
                 r = getRect(11+i);
                 if(mx >= r.x && mx <= r.x+r.width && my >= r.y && my <= r.y+r.height) {
                    if(!holding[i]) {
                      int tot = 0, j;
                      for (j = 0; j < 3; ++j) if(holding[j]) ++tot;
                      if(tot>=2) noise.beep();
                      else holding[i] = true;
                    }
                    else holding[i] = false;
                    ended = false;
                 }
              }
              r = getRect(15);
              if(mx >= r.x && mx <= r.x+r.width && my >= r.y && my <= r.y+r.height) {
                 pulled = true;
                 pulltime=gtime;
                 pulling = false;
                 ended = false;
                 setControl("handle.2",400);
                 keepMoving=true;
                 if(lastmessage != null) {
                     removeMover(lastmessage);
                     lastmessage = null;
                 }
                 if(pullmessage != null) {
                    removeMover(pullmessage);
                    pullmessage = null;
                 }
                 int maxint = screenheight/10;
                 int dyy =  (screenheight/4+u.rand(screenheight/8))*2;
                 for(i=0;i<3;++i) {
                    if(!holding[i]) {
                       dy[i] = dyy*maxint*(i+1)/screenheight;
                       dy[i] = Math.min(maxint*(i+1), dy[i]);
                       spintime[i] = Math.min(5000,2000*dy[i]/maxint);
                    }
                 }
              }
           }
      }
  }
}
