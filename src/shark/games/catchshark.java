package shark.games;

import java.awt.*;

import shark.*;
import shark.sharkGame.*;

public class catchshark extends sharkGame {
  short sharktot,curr,wordtot;
  static final short FREEPEEPINT = 4000;
  xfish s[];
  mover chosenfish;
  sfish ss[];
  long busy,nosnap;
  boolean chosen, converting;
  word convertfrom;
  boolean simple,classic,freepeep,auto,firsttime=true;
  char extrakeys[];
  boolean len1;
  long wantzoom,wantzoomback;
  boolean tiles;
  String pressenter;
  boolean sayphonemes;
  boolean showpicture = !specialprivateon;

  public catchshark() {
    errors = true;
    gamescore1 = true;
    peeps = peep = (!phonicsw) ;
    listen= true;
    curr = -1;
    forceSharedColor = true;
    wordtot = (short)rgame.w.length;
    if(phonics) len1 = rgame.w[0].v().length()==1;
    if(rgame.getParm("pairedwords") != null)  {
       converting = true;
       sharktot = (short)(wordtot/2);
       extrakeys = new char[] {(char)keypad.SHIFT,(char)keypad.LEFT, (char)keypad.RIGHT,
                                                         (char)keypad.ENTER};
       for(int i=0;i<wordtot;i+=2) {
         if(rgame.w[i].v().length() < rgame.w[i+1].v().length())
            extrakeys = new char[] {(char)keypad.SHIFT,(char)keypad.LEFT, (char)keypad.RIGHT,(char)keypad.BACKSPACE,
                                                                (char)keypad.ENTER};
         if(rgame.w[i].v().length() > rgame.w[i+1].v().length()
            || rgame.w[i].v().length() < rgame.w[i+1].v().length()
                && rgame.w[i].v().charAt(0) != rgame.w[i+1].v().charAt(0)) {
           extrakeys = new char[] {(char)keypad.SHIFT,(char)keypad.BACKSPACE,
                          (char)keypad.INSERT, (char)keypad.DELETE, (char)keypad.LEFT, (char)keypad.RIGHT,(char)keypad.ENTER};
           break;
         }
       }
    }
    else {
      sharktot = wordtot;
      extrakeys = new char[] {(char)keypad.SHIFT,(char)keypad.ENTER};
    }
//    if (!converting && (!phonics || phonicsw && !blended)){
//        if(shark.phonicshark)
//            optionlist = new String[] {"sharks_type", "sharks_auto", "sharks_tiles"};
//        else
//            optionlist = new String[] {"sharks_type", "spell_freepeep", "sharks_auto", "sharks_tiles"};
//    }
    if (!shark.phonicshark && !converting && (!phonics || phonicsw && !blended)){
        optionlist = new String[] {"sharks_type", "spell_freepeep", "sharks_auto", "sharks_tiles"};
    }
    else
        optionlist = new String[] {"sharks_type", "sharks_auto", "sharks_tiles"};
    if(phonicsw && !converting) optionlist=u.addString(optionlist, "sharks_sayphonemes");
    sayphonemes = phonicsw && !converting && options.option( "sharks_sayphonemes");
    tiles = options.option("sharks_tiles");
    pressenter = rgame.getParm(tiles?"clicktick":"pressenter");
    gamePanel.setBackground(new Color(0,0,120+u.rand(120)));
    gamePanel.clearWholeScreen = true;
    gamePanel.dontstart = true;
    auto = options.option("sharks_auto");
    wantspeed = !auto;
    bites = !auto;
    buildTopPanel();
    short ov = options.optionval(optionlist[0]);
    simple = ( ov <= 1);
    classic = (ov== 2);
    gamePanel.sort3dmovers = true;
//    if(!converting && (!phonics || phonicsw && !blended)) freepeep = options.option(optionlist[1]);
    if(!shark.phonicshark && !converting && (!phonics || phonicsw && !blended)) freepeep = options.option(optionlist[1]);
    help(auto?"help_catchshark1a":"help_catchshark1");
    setupSharks();
    pictureat = 1;
    nosnap = gtime()+5000;
    gamePanel.dontstart = false;
 }
 //--------------------------------------------------------------
 public void restart() {
    super.restart();
    short ov = options.optionval(optionlist[0]);
    simple = (ov  <= 1);
    classic = (ov== 2);
//    if(!converting && (!phonics || phonicsw && !blended)) freepeep = options.option(optionlist[1]);
    if(!shark.phonicshark && !converting && (!phonics || phonicsw && !blended)) freepeep = options.option(optionlist[1]);
    tiles = options.option("sharks_tiles");
    pressenter = rgame.getParm(tiles?"clicktick":"pressenter");
    sayphonemes = phonicsw && !converting && options.option( "sharks_sayphonemes");
    auto = options.option("sharks_auto");
    wantspeed = !auto;
    bites = !auto;
    buildTopPanel();
    gamePanel.showSprite = true;
    curr = -1;
    busy = nosnap = 0;
    chosen = false;
    help(auto?"help_catchshark1a":"help_catchshark1");
    setupSharks();
    keypadDeactivate();
 }
 //--------------------------------------------------------------
 void setupSharks() {
  short i;

  markoption();

  if(simple || classic) {
     ss = new sfish[sharktot];
     for(i=0;i<sharktot;++i) {
       ss[i] = new sfish();
       gamePanel.addMover(ss[i],ss[i].x,ss[i].y);
     }
  }
  else {
    s = new xfish[sharktot];
    for(i=0;i<sharktot;++i) {
        int a = mover3d.BASEU/16 + u.rand(mover3d.BASEU/16);
        int b = mover3d.BASEU/16 + u.rand(mover3d.BASEU/16);
        int c = b - b/4 + u.rand(b/4);
        int midx = a + u.rand(a/2);
        int tailx = midx + a/2 + u.rand(a/2);
        int midy = a/3 + u.rand(a/3);
        int taily = a + u.rand(a/2);
        s[i] = new xfish(a + u.rand(mover3d.BASEU-a*2),
              b + u.rand(mover3d.BASEU-b*2),
              mover3d.BASEZ/1 + u.rand(mover3d.BASEZ*2),
              a,b,c,
              u.fishcolor(),
              (i==0)?0:u.rand(a/2),   // mouth
              (i==0)?0:u.rand(b/2),
              (short)(8+u.rand(7)),  // teeth
              tailx,taily,midx,midy, //tail
              a/4 + u.rand(a/2),
              a/4 + u.rand(a/2),
              a/4 + u.rand(a/2),
              a/4 + u.rand(a/2));
        s[i].randmove = true;
        gamePanel.addMover(s[i],s[i].x,s[i].y);
    }
  }
 }
 //--------------------------------------------------------------
 public void afterDraw(long t) {
    long lastsnap;
    short i, ct = 0;
    int mx = gamePanel.mousexs, my = gamePanel.mouseys;
    boolean issnap = false;

    if(wantzoom != 0 && gtime > wantzoom) {
      wantzoom = 0;
      if(chosenfish instanceof fish) {
        ((fish)chosenfish).pointforward();
      }
      chosenfish.input = new sharkInput(new sharkAction()   {
         public void enter() {
//                      if(converting) {
               if(currword.v().equals(u.stripspaces(chosenfish.input.val))) {
                  chosenfish.input.ended = true;
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
           if(chosenfish instanceof sfish) ((sfish)chosenfish).frontim.setControl("grin");
           wantzoomback = gtime+1000;
         }
         public void button(byte bno) {}
         public void mouseClick() {
           if(phonicsw && !sayphonemes) spokenWord.sayPhonicsWhole(currword);
           else if (phonics) spokenWord.sayPhonicsWord(currword,500,true,false,false);
           else   currword.say();
         }
       });
       if(!converting) chosenfish.input.cantgoback=true;
       if(chosenfish instanceof xfish)
           chosenfish.input.background = ((xfish)chosenfish).mouthcolor;
        else if(chosenfish instanceof sfish)
           chosenfish.input.background = ((sfish)chosenfish).mouthcolor;
       if(!tiles)
           keypad.activate(gamePanel.currgame,keypad.addmore(extrakeys,rgame.w));
       if(converting) {
         help("help_catchshark4" + (tiles?"tiles":""));
         convertfrom = rgame.w[++curr];
         currword =  rgame.w[++curr];
         chosenfish.input.model = currword.v();
         chosenfish.input.dontcompare = true;
         chosenfish.input.val = convertfrom.v();
         if(chosenfish.input.model.length() == chosenfish.input.val.length()) chosenfish.input.maxlen =(short)(chosenfish.input.model.length());
         else                  chosenfish.input.maxlen=(short)(Math.max(chosenfish.input.model.length(),chosenfish.input.val.length())+4);
         if(!firsttime)
           spokenWord.findandsay(new Object[] {convertfrom,
                 "%% "+rgame.getParm("convertto"),currword});
           else  
             spokenWord.findandsay(new Object[] {convertfrom,
                 "%% "+rgame.getParm("convertto"),currword,
                       pressenter});
         firsttime=false;
         if(showpicture)
            new showpicture();
         if(freepeep) {
            chosenfish.input.showmodel =  gtime + FREEPEEPINT;
            chosenfish.input.startafter =  gtime + FREEPEEPINT+500;
        }
     }
      else {
           String helpstring = (phonics && !phonicsw || blended ?
              ( freepeep?(len1?"help_catchshark3ph":"help_catchshark3bl"):(len1?"help_catchshark2ph":"help_catchshark2bl"))
              :( freepeep?"help_catchshark3":"help_catchshark2")) + (tiles?"tiles":"");
         if(specialprivateon){
              if(helpstring.equals("help_catchshark2"))
                  helpstring = helpstring.concat("def");
              else if (helpstring.equals("help_catchshark2tiles")) 
                  helpstring = helpstring.concat("def");
              else if (helpstring.equals("help_catchshark3"))
                  helpstring = helpstring.concat("def");
         }
         help(helpstring);
         currword =  rgame.w[++curr];
         chosenfish.input.model = currword.v();
         if(!phonics && rgame.fullwordlist.split && currword.value.indexOf('/')>=0 && !tiles) {
            chosenfish.input.splits = currword.vsplit();
         }
         if(phonicsw && !sayphonemes) spokenWord.sayPhonicsWhole(currword);
         else if (phonics) spokenWord.sayPhonicsWord(currword,500,true,false,false);
         else   currword.say();
         if(!phonics && showpicture) new showpicture();
         if(freepeep) {
            chosenfish.input.showmodel =  gtime + FREEPEEPINT;
            chosenfish.input.startafter =  gtime + FREEPEEPINT+500;
         }
     }
     if(tiles) {
        chosenfish.input.setletterlist(rgame.w,currword,converting?convertfrom:null);
     }
    }
    if(wantzoomback != 0 && gtime > wantzoomback) {
      wantzoomback = 0;
      if(chosenfish instanceof xfish) {
        ((xfish)chosenfish).happy = true;
        ((xfish)chosenfish).randmove = true;
        ((xfish)chosenfish).zooming = false;
        ((xfish)chosenfish).fixuntil = t + 4000;
      }
      else {
        ((sfish)chosenfish).zoomback(1200 + u.rand(600));
        ((sfish)chosenfish).happy = true;
        ((sfish)chosenfish).fixuntil = t+4000;
      }
      chosenfish.input = null;
      chosen = false;
//startPR2005-04-18^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      if(Demo_base.isDemo){
        if (Demo_base.demoIsReadyForExit(0)) return;
      }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      gamescore(2);
      gamePanel.showSprite = true;
      nosnap = t + 5000;
      help(auto?"help_catchshark1a":"help_catchshark1");
       if (curr >= wordtot-1) {
         score(gametot1);
         exit(3000);
      }
      if(delayedflip && !completed){
         flip();
      }
    }
    if(busy > t || chosen) return;
    if(busy!=0) {busy=0; gamePanel.showSprite = true;}
    if(auto) {
       if(t > nosnap-4000) {
        if(simple || classic) {
          for(i=0;i<sharktot;++i) {
              if(!ss[i].happy) {
                  ss[i].mouseClicked(0,0);
                  return;
              }
          }
        }
        else  {
          for(i=0;i<sharktot;++i) {
              if(!s[i].happy) {
                  s[i].mouseClicked(0,0);
                  return;
              }
          }
        }
       }
       return;
    }
    if(simple || classic) {
       mover tfish = gamePanel.mouseOverMover();
       sfish f = null;
       if(!gamePanel.mouseOutside && tfish instanceof sfish) f = (sfish)tfish;
       if(f != null) {
          if (!f.happy) {
             if(t > nosnap && f.inmouth(mx,my)) {
                f.snap2(1000);
                f.blood(t,2000);
                death(1);
                f.fixuntil = t+2000;
                busy = t+2000;
                nosnap = t+5000;
                gamePanel.showSprite = false;
                noise.squeek();
             }
             if(!f.snapping)  {
                 f.snap1(mx,my,1000);
             }
          }
       }
    }
    else {
  //     xfish f = (xfish) gamePanel.mouseOverMover();
       mover tfish = gamePanel.mouseOverMover();
       xfish f = null;
       if(!gamePanel.mouseOutside && tfish instanceof xfish) f = (xfish)tfish;
       if(f != null) {
          if (!f.happy) {
             if(t > nosnap && f.inmouth(mx,my)) {
                f.snap2(1000);
                f.blood(t,2000);
                death(1);
                f.fixuntil = t+2000;
                busy = t+2000;
                nosnap = t+5000;
                gamePanel.showSprite = false;
                noise.squeek();
             }
             if(!f.snapping)  {
                 f.snap1(mx,my,1000);
             }
          }
       }
    }
 }
 //-------------------------------------------------------------
   public void peep1() {
     if(!chosen || chosenfish.input == null) return;
     chosenfish.input.showmodel =  gtime() + 2000;
     ++peeptot;
     peepsView.setText(String.valueOf(peeptot));
     studentrecord.peepList = u.addString(studentrecord.peepList,currword.v());
     requestFocus();
   }
   //--------------------------------------------------------------------
   public void listen1() {
       if(currword != null && !converting && phonicsw && !sayphonemes) spokenWord.sayPhonicsWhole(currword);
       else if(phonics && !converting && currword != null) {
          spokenWord.sayPhonicsWord(currword, 200, true,true,false);
       gamePanel.startrunning();
       gamePanel.requestFocus();
     }
     else super.listen1();
   }
 //-------------------------------------------------------
 class xfish extends fish {
     public xfish(int x, int y, int z, int a,int b, int c,
                  Color color, int mx, int my, short t,

                  int tx, int ty, int midx, int midy,
                  int fin1, int fin2,int fin3,int fin4)  {
         super(x,y,z,a,b,c,  color,mx,my,t, tx,ty,midx,midy, fin1,fin2,fin3,fin4);
         dontgrabmouse = true;
    }
   public void mouseClicked(int mx, int my) {
       long t = gtime;
       int zoomtime;
       if(t > busy && !happy && !chosen && curr < wordtot-1
           && (auto || phead != null && phead2 != null && phead.contains(mx,my)
                 || oldphead != null && oldphead.contains(mx,my))) {
         snapping = snapping1 = snapping2 = false;
         fillscreen(zoomtime=1000 + u.rand(1000));
          if(keypad.keypadname == null && !tiles) gamePanel.showSprite = false;
          chosen = true;
          chosenfish = this;
          wantzoom = gtime + zoomtime+100;
       }
   }
 }
 //-------------------------------------------------------
 class sfish extends simplefish {
   public sfish()  {
         super(tiles?simplefish.TYPE_NORMAL_WITH_TILES:simplefish.TYPE_NORMAL);
         setup(classic?(mover.WIDTH/12 + u.rand(mover.WIDTH/12)):(mover.WIDTH/10 + u.rand(mover.WIDTH/10)) ,
         gamePanel, classic);
         dontgrabmouse = true;
   }
   public void mouseClicked(int mx, int my) {
       long t = gtime;
       int zoomtime;
       if(t > busy && !happy && !chosen && curr < wordtot-1) {
         snapping = false;
         fillscreen(zoomtime=1000 + u.rand(1000));
         if(keypad.keypadname == null) gamePanel.showSprite = false;
         chosen = true;
         chosenfish = this;
         wantzoom = gtime + zoomtime+100;
       }
   }
 }
}
