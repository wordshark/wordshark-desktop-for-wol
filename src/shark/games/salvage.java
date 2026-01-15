package shark.games;

    

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import shark.*;

public class salvage extends sharkGame {
    short curr;
  int sharktot = 4,wantob = 5,wantob2 = 3,wordtot = wantob+wantob2,wanttreas = 8;
  sharkImage obstacles[] = sharkImage.randomimages("salvageob_",(short)wantob);
  sharkImage obstacles2[] = sharkImage.randomimages("salvageob2_",(short)wantob2);
  sharkImage treasures[] = sharkImage.randomimages("salvagetreasure_",(short)wanttreas);
  sharkImage spareim = sharkImage.random("salvagespare_");
 // int currsum=-1;
//  typesum currinp;
  slot currinp;
  sfish ss[],chosenfish;
  treasure tre[] = new treasure[treasures.length];
  obstacle obs[] = new obstacle[obstacles.length+obstacles2.length];
//  onesum sums[][];
  netc net;
  daveyjones daveyjones;
  int nettot,daveytot;
  int behind[] = u.shuffle(u.select(wordtot,wanttreas));
  boolean isfraction;
  int spread;
  boolean haddraw;
  boolean lastvis;
//startPR2007-04-18^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  boolean typinginanswer;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  boolean sayphonemes;
  boolean tiles;
  static final short FREEPEEPINT = 4000;
  boolean showpicture = !specialprivateon;
  boolean doflipsoon = false;

  public salvage() {
    int i;
    curr = -1;
    errors = true;
    gamescore1 = true;
    wantspeed = true;
    peeps = peep = (!phonicsw) ;
    listen= true;
    this.wantSprite = false;
    gamePanel.allclicks = true;
    gamePanel.setBackground(new Color(0,0,120+u.rand(120)));
    gamePanel.clearWholeScreen = true;
    spareim.distort = true;
    
    
    
    if (!shark.phonicshark && (!phonics || phonicsw && !blended)){
//        if(shark.phonicshark)
//           optionlist = new String[] {"sharks_tiles"};
//        else
            optionlist = new String[] {"spell_freepeep", "salvage_tiles"};
    }
    else
        optionlist = new String[] {"salvage_tiles"};
    if(phonicsw) optionlist=u.addString(optionlist, "salvage_sayphonemes");
    sayphonemes = phonicsw && options.option( "salvage_sayphonemes");
    tiles = options.option("salvage_tiles");
    if(!shark.phonicshark && (!phonics || phonicsw && !blended)) freepeep = options.option(optionlist[0]);
 //   if(rgame.currsum.isfraction) {
 //     isfraction = true;
 //     if(fractionspec.hasfractions()) keypadfraction = true;
 //     onesum ss[] = fractionspec.getsums(sumtot,sumtot);
 //     sums = new onesum[ss.length][];
 //     for(i=0; i<ss.length; ++i) {
 //       sums[i] = new onesum[]{ss[i]};
 //     }
 //   }
 //   else if( sharkStartFrame.currPlayTopic.inorder) {
 //     onesum ss[] = rgame.currsum.sorted(sumtot);
 //     sums = new onesum[ss.length][];
 //     for (i = 0; i < ss.length; ++i) {
 //         sums[i] = new onesum[] {ss[i]};
 //     }
 //   }
 //   else sums = rgame.currsum.shuffledg(sumtot);
//startPR2007-04-25^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    if(rgame.w.length<wordtot){
      wantob2 = rgame.w.length/2;
      wantob = rgame.w.length-wantob2;
      wordtot = wantob+wantob2;
      wanttreas = rgame.w.length;
      obstacles = sharkImage.randomimages("salvageob_",(short)wantob);
      obstacles2 = sharkImage.randomimages("salvageob2_",(short)wantob2);
      treasures = sharkImage.randomimages("salvagetreasure_",(short)wanttreas);

    }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    buildTopPanel();
    spread = screenwidth/40;
//    if(keypadfraction) help("help_salvagefr");
//    else help(fractionspec.curr != null?"help_salvageeq":"help_salvage");
    help("help_salvage");
    
    setupMovers();
    gamePanel.sort3dmovers = true;
  }
  //--------------------------------------------------------------
  
 void setupMovers(){
    net = new netc();
    daveyjones = new daveyjones();
    
     tre = new treasure[treasures.length];
      obs = new obstacle[obstacles.length+obstacles2.length];
      behind = u.shuffle(u.select(wordtot,wanttreas));    
    
    ss = new sfish[sharktot];
    int i;
    for(i=0;i<sharktot;++i) {
      ss[i] = new sfish();
  //    ss[i].sums = sums[i];
      gamePanel.addMover(ss[i],ss[i].x,ss[i].y);
    }
    for(i=0;i<obstacles.length;++i) {
      obs[i] = new obstacle(i,false);
    }
    for(i=0;i<obstacles2.length;++i) {
      obs[i+obstacles.length] = new obstacle(i,true);
    }
    for(i=0;i<treasures.length;++i) {
      tre[i] = new treasure(i);
    }
 }
  
  
 public void restart() {
    super.restart();
    currinp = null;
    if(!shark.phonicshark && (!phonics || phonicsw && !blended)) freepeep = options.option(optionlist[0]);
    tiles = options.option("salvage_tiles");
    sayphonemes = phonicsw && options.option( "salvage_sayphonemes");
    buildTopPanel();
    curr = -1;
    setupMovers();
//    busy = nosnap = 0;
//    help(auto?"help_catchshark1a":"help_catchshark1");
//    setupSharks();
    help("help_salvage");
    keypadDeactivate();
 }  
  
  
  public void afterDraw(long t){
    if(!completed && nettot + daveytot == tre.length) {
       String m;
       exitbutton(mover.HEIGHT*3/4);
       if(daveytot == 0) m = rgame.getParm("gotall");
       else if(nettot == 0) m =  rgame.getParm("gotnone");
       else if(nettot == 1) m =  rgame.getParm("gotone");
       else if(daveytot == 1) m =  rgame.getParm("lostone");
       else m = u.edit( rgame.getParm("yougot"),nettot,daveytot);
       score(gametot1);
       showmessage(m,mover.WIDTH/4,mover.HEIGHT/8,mover.WIDTH*3/4,mover.HEIGHT*5/8);
    }
    if(doflipsoon && stolentot() == 0 && gamePanel.mousemovertot == 0){
       if(delayedflip && !completed && nettot + daveytot != tre.length){
          flip();
       }
       doflipsoon = false;
    }
    haddraw = true;
    int i;
    boolean isvis = false;
    for(i=0;i<tre.length;++i) {
       if(tre[i] != null && tre[i].vis && !tre[i].indavey && !tre[i].netted)  {
         isvis = true; break;
       }
    }
    if(isvis != lastvis) {
        help("help_salvage");
//      if(keypadfraction) help("help_salvagefr");
//      else help(fractionspec.curr != null?"help_salvageeq":"help_salvage");
      lastvis = isvis;
    }
  }
  //-------------------------------------------------------------
  int  stolentot() {
    int i, tot=0;
    for(i=0;i<tre.length;++i) if(tre[i].stolen) ++tot;
    return tot;
  }
  //-------------------------------------------------------
  
   public void peep1() {
     if(currinp.input == null) return;
     currinp.input.showmodel =  gtime() + 2000;
     ++peeptot;
     peepsView.setText(String.valueOf(peeptot));
     studentrecord.peepList = u.addString(studentrecord.peepList,currword.v());
     requestFocus();
   }
   //--------------------------------------------------------------------
   public void listen1() {
       if(currword != null && phonicsw && !sayphonemes) spokenWord.sayPhonicsWhole(currword);
       else if(phonics && currword != null) {
          spokenWord.sayPhonicsWord(currword, 200, true,true,false);
       gamePanel.startrunning();
       gamePanel.requestFocus();
     }
     else super.listen1();
   }  
  
  
  class sfish extends simplefish {
//    onesum sums[];
    boolean gottreas;
    treasure target;
    public sfish()  {
          super(simplefish.TYPE_SALVAGE);
          fishname = "mermaid";
          setup((mover.WIDTH/8 + u.rand(mover.WIDTH/10)) ,
                       gamePanel,false);
          dontgrabmouse = true;
    }
    public void paint(Graphics g) {
      if(aiming) {
         if(target != null) {
           if (!target.vis || target.stolen && target.captor != this || target.netted ||
               target.indavey)
             aiming = false;
           else if (!target.stolen) {
             tocentre = new point3d_base(target.rcentre.x, target.rcentre.y,
                                    target.rcentre.z);
             rcentre.z = target.rcentre.z;
             gottreas = false;
             happy=false;
           }
         }
      }
      else {
        if (x < daveyjones.w && y + h > daveyjones.y) {
          rcentre.x = daveyjones. w;
          xspeed.currval = -xspeed.currval;
          xspeed.currchange = -xspeed.currchange;
        }
        if (x < net.w  && y < net.h) {
          rcentre.x = daveyjones.w;
          xspeed.currval = -xspeed.currval;
          xspeed.currchange = -xspeed.currchange;
        }
      }
      super.paint(g);
    }
  }
  //---------------------------------------------------------
  class obstacle extends mover3d {
    sharkImage im;
    boolean typing;
    long exiting;
    int starty,startx;
    long nextbubble = gtime;
    obstacle(int num,boolean big) {
      im = obstacles[num];
      dontgrabmouse = true;
      if(!big) {
        im = obstacles[num];
        im.w = mover.WIDTH / 6 + u.rand(mover.WIDTH / 10);
        im.h = mover.HEIGHT / 3 + u.rand(mover.HEIGHT / 3);
      }
      else {
        im = obstacles2[num];
        im.w = mover.WIDTH / 3 + u.rand(mover.WIDTH / 3);
        im.h = mover.HEIGHT / 3 + u.rand(mover.HEIGHT / 3);
      }
//startPR2007-04-18^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      /**
       * sets up this sharkImage so that its hit target is known about in runMovers
       * mouseDown2
       */

      im.setupclicktarget(this, screenwidth/40);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      im.adjustSize(screenwidth,screenheight);
      im.lefttoright = (u.rand(2)==0);
      rcentre = new point3d_base(0,0,0);
      w = im.w;
      h = im.h;
      rcentre.z = BASEZ - u.rand(BASEU);
      do {
        rcentre.x = (x = startx = u.rand(mover.WIDTH - w)) + w / 2;
        rcentre.y = (y = starty = mover.HEIGHT - mover.HEIGHT/2*(rcentre.z - BASEU*3)/BASEU - h) + h/2 ;
      } while(x < net.w  && y+h > net.y);
      addMover(this,x,y);
    }
    public void mouseClicked(int mx, int my) {
      if(currinp == null  &&  gamePanel.mousemovertot == 0
//startPR2007-04-18^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         /**
          * whether the click has been near enough to part of the sharkImage is
          * now d
               * ealt with in runMovers mouseDown2 (see explanation there).
          */
         && stolentot() == 0){
           typinginanswer = true;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
           
           
           
        currinp = new slot(im.getcolor(0));          
           
           
           
          
//        keypad.activate(gamePanel.currgame,neg?new char[]{'-'}:null);
//        if(keypadfraction) help("help_salvagefr2");
//        currinp = new typesum(sums[++currsum], null, im.getcolor(0),false);
        if(!tiles){
            char[] extrakeys = new char[] {(char)keypad.SHIFT,(char)keypad.ENTER};
            keypad.activate(gamePanel.currgame,keypad.addmore(extrakeys,rgame.w));      
            help("help_salvageinput");
        }
        else help("help_salvageinputtiles");
    
 //       currinp.forcebg = true;
        typing = true;
        currinp.w = mover.WIDTH/4;
        currinp.h = mover.WIDTH/5;
        currinp.input.overrideTextHeight = (currinp.h*screenheight/mover.HEIGHT)/2;
        int x1 = Math.max(0,Math.min(mover.WIDTH-currinp.w,x + w/2 - currinp.w/2));
        int y1 =  Math.min(y + h/2 - currinp.h/2,mover.HEIGHT-currinp.h*3);
        if(x1+currinp.w > mover.WIDTH - keypad.keypadwidth(gamePanel.currgame)*mover.WIDTH/screenwidth
               && y1 + currinp.h > mover.HEIGHT - keypad.keypadheight(gamePanel.currgame)*mover.HEIGHT/screenheight)
                                x1 =  mover.WIDTH - keypad.keypadwidth(gamePanel.currgame)*mover.WIDTH/screenwidth - currinp.w;
        addMover(currinp, x1,y1);

      }
    }
    public void paint(Graphics g) {
      if(exiting>0) {
        if(gtime - exiting >= 2000){
//startPR2007-04-18^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          /**
           * 'currinp = null' moved to here (see explanation below)
           */

          currinp = null;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          kill = true;
//startPR2006-08-19^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          if(Demo_base.isDemo)
            if (Demo_base.demoIsReadyForExit(0)) return;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          if(delayedflip && !completed){
              if(stolentot()==0)
                flip();
              else
                doflipsoon = true;
          }
        }
        else y = (int)((starty*(2000-gtime+exiting) + (-h)*(gtime-exiting))/ 2000) ;
      }
      int x1 = x*manager.screenwidth/mover.WIDTH;
      int y1 = y*manager.screenheight/mover.HEIGHT;
      int w1 = im.w*manager.screenwidth/mover.WIDTH;
      int h1 = im.h*manager.screenheight/mover.HEIGHT;
      if(typing && currinp.endtime != 0 && gtime > currinp.endtime + (tiles?0:1000)) {
        currinp.moveto(currinp.x, -h/2 + currinp.h/2,2000);
        currinp.temp=true;
//        currinp.calc.kill = true;
//startPR2007-04-18^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        /**
         * 'currinp = null' removed from here because otherwise the obstacle can
         * process a mouseClick even after the sum has been answered. You then
         * get a sum that once answered is never cleared from the screen. Instead
         * moved to after the obstacle has left the screen.
         */

//        currinp = null;
         typinginanswer = false;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        help("help_salvage");
 //       if(keypadfraction) help("help_salvagefr");
        exiting = gtime;
        temp=true;
        typing = false;
        gamescore(1);
        keypad.deactivate(gamePanel.currgame);
      }
      im.paint(g,x1,y1,w1,h1);
      if(gtime > nextbubble) {
        new bubble(x+w/8 + u.rand(w*3/4), y+h/8 + u.rand(h*3/4));
         nextbubble = gtime + u.rand(2000);
      }
    }
  }
  //---------------------------------------------------------
  class treasure extends mover3d {
    sharkImage im;
    boolean stolen, netted, indavey, started,vis;
    long pickuptime;
    sfish captor;
    int starty,startx;
    randrange_base rx,ry;
    int lastw = -1;
    // allow a bit of time after turning help on/off - otherwise treasure can get stolen
    long checkcaptureagain;
    
    treasure(int num) {
      dontgrabmouse = true;
      im = treasures[num];
      im.w = mover.WIDTH / 16 + u.rand(mover.WIDTH / 30);
      im.h = mover.HEIGHT / 12 + u.rand(mover.HEIGHT / 24);
      im.adjustSize(screenwidth,screenheight);
      im.lefttoright = (u.rand(2)==0);
      rcentre = new point3d_base(0,0,0);
      obstacle oo = obs[behind[num]];
      rcentre.z = oo.rcentre.z+BASEU/16;
      w = im.w;
      h = im.h;
      rcentre.x = (x = startx = oo.x+oo.w/2 - w/2) + w/2;
      rcentre.y = (y = starty = oo.y+oo.h*15/16 -h) + h/2;
      addMover(this,x,y);
      gamePanel.puttobottom(this);
    }
    public void mouseClicked(int mx, int my) {
      int i;
      int midx = (x+w/2)*manager.screenwidth/mover.WIDTH;
      int midy = (y+h/2)*manager.screenheight/mover.HEIGHT;
//startPR2007-04-18^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      /**
       * removed "currinp == null" as a condition because currinp isn't now set
       * to null immediately after completing the sum. Instead the new
       * variable "typinginanswer" prevents the treasure from being clicked until
       * the sum has been answered.
       */

//      if(!gamePanel.movesWithMouse(this) && currinp == null && vis  && !netted && !indavey
//           && (im.covers(gamePanel.mousexs,gamePanel.mouseys)
//               || Math.abs(x+w/2-gamePanel.mousex)<w*3/4
//                     && Math.abs(y+h/2-gamePanel.mousey)<h*3/4)) {
              if(!gamePanel.movesWithMouse(this) &&
                 !typinginanswer&&
                 vis  && !netted && !indavey
                 && (im.covers(gamePanel.mousexs,gamePanel.mouseys)
                     || Math.abs(x+w/2-gamePanel.mousex)<w*3/4
                     && Math.abs(y+h/2-gamePanel.mousey)<h*3/4)) {
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       gamePanel.attachToMouseNoKill(this);
         if(stolen) {
           stolen = false;
           captor.gottreas = false;
           captor.happy = false;
           captor.aiming = false;
           captor = null;
         }
         pickuptime = gtime;
      }
      else {
        i=1;
      }
    }
    public void paint(Graphics g) {
      if(screenwidth!=lastw){
          checkcaptureagain = gtime+500;
          lastw = screenwidth;
      }
      int i,j;
      int midx = (x+w/2)*manager.screenwidth/mover.WIDTH;
      int midy = (y+h/2)*manager.screenheight/mover.HEIGHT;
      if(stolen) {
        rcentre.z = captor.rcentre.z-1;
        Rectangle rr = captor.mouth().getBounds();
        x = tox =(rr.x+rr.width/2)*mover.WIDTH/screenwidth - w/2;
        y = toy = (rr.y+rr.height/2)*mover.HEIGHT/screenheight - h/2;
        if(x+w < daveyjones.w && y > daveyjones.y) {
          indavey = true;
          ++daveytot;
          stolen = false;
          captor.aiming = false;
          captor.gottreas = false;
          captor.happy = false;
          captor = null;
          rx = new randrange_base(0,daveyjones.w-w, mover.WIDTH/40);
          rx.currval = x;
          ry = new randrange_base(daveyjones.y, mover.HEIGHT-h, mover.HEIGHT/20);
          ry.currval = y;
        }
      }
      if(!stolen && !netted && !indavey && started && gtime > pickuptime+1000 && haddraw && checkcaptureagain<gtime) {
        boolean vis2 = true;
        for (i = 0; i < obs.length; ++i) {
          if(obs[i].exiting==0 && rcentre.z > obs[i].rcentre.z
             && obs[i].im.covers(new Rectangle(midx-spread,midy-spread,spread*2,spread*2))) {
            vis2 = false;
          }
        }
        vis=vis2;  
        if(vis) {
          for (i = 0; i < ss.length && !stolen; ++i) {
            if (!ss[i].gottreas) {
              if (ss[i].inmouth(midx,midy)
                   || ss[i].target == this
                      && ss[i].isOver2(midx,midy)) {
                  if (gamePanel.movesWithMouse(this))
                    gamePanel.drop(this);
                  captor = ss[i];
                  ss[i].gottreas = true;
                  ss[i].happy = true;
                  stolen = true;
                  captor.aiming = true;
                  captor.target = null;
                  captor.tocentre = new point3d_base(0, mover.HEIGHT-h,captor.rcentre.z);
              }
              else {
                if(!ss[i].aiming)
                  if (ss[i].x - x < (mover.WIDTH/5) && ss[i].x - x < (mover.HEIGHT/3)) {
                     ss[i].target = this;
                     ss[i].aiming = true;
                     ss[i].fromcentre = ss[i].rcentre;
                 }
              }
            }
          }
        }
      }
      if(gamePanel.movesWithMouse(this)) {
        rcentre.x = x+w/2;
        rcentre.y = y+h/2;
        if(x <= net.w && y <= net.h)  {
          gamePanel.drop(this);
          gamePanel.bringtotop(net);
          netted = true;
          gamescore(1);
          ++nettot;
          rx = new randrange_base(0,net.w-w,mover.WIDTH/40);
          rx.currval = x;
          ry = new randrange_base(0,net.h - h,mover.HEIGHT/20);
          ry.currval = y;
        }
        if(x+w/2 < daveyjones.w && y+h/2 >= daveyjones.y) {
          gamePanel.drop(this);
          indavey = true;
          ++daveytot;
          rx = new randrange_base(0,daveyjones.w-w, mover.WIDTH/40);
          rx.currval = x;
          ry = new randrange_base(daveyjones.y, mover.HEIGHT-h, mover.HEIGHT/20);
          ry.currval = y;
        }
      }
      else if(netted || indavey) {
        x = rx.next(gtime);
        y = ry.next(gtime);
      }
      int x1 = x*manager.screenwidth/mover.WIDTH;
      int y1 = y*manager.screenheight/mover.HEIGHT;
      int w1 = im.w*manager.screenwidth/mover.WIDTH;
      int h1 = im.h*manager.screenheight/mover.HEIGHT;

      im.paint(g,x1,y1,w1,h1);
      if(!vis) {
         spareim.paint(g,x1,y1,w1,h1);
      }
      started = true;
    }
  }
  class netc extends mover {
    sharkImage im  = sharkImage.random("salvagenet_");
    netc() {
      im.w = mover.WIDTH/6;
      im.h = mover.HEIGHT/2;
      im.adjustSize(screenwidth,screenheight);
      w = im.w;
      h = im.h;
      addMover(this,0,0);
    }
    public void paint(Graphics g, int x1, int y1, int w1, int h1) {
      im.paint(g,x1,y1,w1,h1);
    }
  }
  class daveyjones extends mover {
    sharkImage im  = sharkImage.random("salvagedavey_");
    daveyjones() {
      im.w = mover.WIDTH/6;
      im.h = mover.HEIGHT/2;
      im.adjustSize(screenwidth,screenheight);
      w = im.w;
      h = im.h;
      addMover(this,0,mover.HEIGHT-h);
    }
    public void paint(Graphics g, int x1, int y1, int w1, int h1) {
      im.paint(g,x1,y1,w1,h1);
    }
  }
  class bubble extends mover {
    bubble(int xx, int yy) {
       w = mover.WIDTH/200 + u.rand(mover.WIDTH/200);
       h = w*screenheight/screenwidth;
       dontgrabmouse = true;
       addMover(this, xx, yy-h);
       moveto(x, -h,4000);
       temp = true;
    }
    public void paint(Graphics g, int x1, int y1, int w1, int h1) {
       g.setColor(Color.white);
       g.drawOval(x1,y1,w1,w1);
    }
  }
  
  
  
  class slot extends mover {
    int xx,yy;
     String val = "",message = "",message2;
     boolean ok;
     Color bg;
     public long endtime;
     mover thism = this;
     
     slot(Color col) {
         bg = col;
         input = new sharkInput(new sharkAction()   {
         public void enter() {
//                      if(converting) {
               if(currword.v().equals(u.stripspaces(input.val))) {
                  input.ended = true;
                  gamePanel.winsprite = false;
                  gamePanel.showSprite = false;
                  end();
               }
               else error();
//                      }
         }
         public void escape() {
             gamePanel.currgame.terminate();
         }
         public void f1() {
              sharkStartFrame.mainFrame.setwanthelp(!sharkStartFrame.mainFrame.wanthelp);
//              ToolTipManager.sharedInstance().setEnabled(sharkStartFrame.mainFrame.wanthelp);
             gamePanel.currgame.changehelp();
         }
         public void error() {
            gamePanel.currgame.error(currword.v());
            gamescore(-1);
            noise.groan();
         }
         public void end() {
           keypadDeactivate();
           endtime = sharkGame.gtime();
  //         if(chosenfish instanceof sfish) ((sfish)chosenfish).frontim.setControl("grin");
           if(tiles)gamePanel.removeMover(thism);
         //  wantzoomback = gtime+1000;
         }
         public void button(byte bno) {}
         public void mouseClick() {
           if(phonicsw && !sayphonemes) spokenWord.sayPhonicsWhole(currword);
           else if (phonics) spokenWord.sayPhonicsWord(currword,500,true,false,false);
           else   currword.say();
         }
       });
         
        input.wantenddelay = 3000; 
       input.cantgoback=true;

//        help((phonics && !phonicsw || blended ?
//              ( freepeep?(len1?"help_catchshark3ph":"help_catchshark3bl"):(len1?"help_catchshark2ph":"help_catchshark2bl"))
//              :( freepeep?"help_catchshark3":"help_catchshark2")) + (tiles?"tiles":""));
         currword =  rgame.w[++curr];
         input.model = currword.v();
         if(!phonics && rgame.fullwordlist.split && currword.value.indexOf('/')>=0 && !tiles) { 
            input.splits = currword.vsplit();
         }
         if(phonicsw && !sayphonemes) spokenWord.sayPhonicsWhole(currword);
         else if (phonics) spokenWord.sayPhonicsWord(currword,500,true,false,false);
         else   currword.say();
         if(!phonics && showpicture)
             new showpicture(mover.WIDTH * 3 / 4,0);
         if(freepeep) {
            input.showmodel =  gtime + FREEPEEPINT;
            input.startafter =  gtime + FREEPEEPINT+500;
         }
     if(tiles) {
        input.setletterlist(rgame.w,currword,null);
        gamePanel.winsprite = true;
     }
         
  //     w =  mover.WIDTH/2;
   //     h =  wordh;
     }
      public void paint(Graphics g, int x1, int y1, int w1, int h1) {
         g.setColor(bg);


    //     if(currinp.input.m!=null)
    //        h1 = currinp.input.m.getHeight();
   //      else return;
         
         Graphics2D g2d = ((Graphics2D)g);
         int yy = y1+h1/2-h1/2;
         if((input.showchar || input.showmodel!=-1) && !input.val.equals(currword.v())){
             g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
             g.fillRect(x1+w1/2-w1/2, yy, w1, h1/2);
             g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
         }
         g.fillRect(x1+w1/2-w1/2, yy + h1/2, w1, h1/2);
         
         
         if(input != null) {
           val = input.val;
           input.paint(g,x1,y1,w1,h1,screenheight);
         }
        
      }
   }  
  
}
