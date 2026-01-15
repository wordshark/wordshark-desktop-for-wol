package shark.games;

import java.awt.*;
import java.awt.event.*;
import shark.*;
import shark.sharkGame.*;

public class animalmaze extends  sharkGame {
 static final short FREEPEEPINT = 4000;
 boolean freepeep;
 amaze m;
 short curr,wordtot;
 boolean converting, gotmaze, busy, explode, typing, waiting, shrinking, firsttime=true;
 short sharktot,slotsused;
 final short pathfactor = 32,pathfactorsquare = 20;
 char extrakeys[];
 boolean len1;
 boolean tiles;
 String pressenter;
 boolean sayphonemes;
 boolean showpicture = !specialprivateon;

 public animalmaze() {
    errors = true;
    gamescore1 = true;
    wantspeed = true;
    bites = false;
    peeps = peep = (!phonicsw) ;
    listen= true;
    forceSharedColor = true;
    gamePanel.setTouchDragGame();
//    opnarr = new String[] {"Draw your own shape for maze", "Maze complexity","3-dimensional view","Free peep before spelling","Must click to catch animals"};
    curr = -1;
    wordtot = (short)rgame.w.length;
    if(phonics) len1 = rgame.w[0].v().length()==1;
    pressenter = rgame.getParm(tiles?"clicktick":"pressenter");
    sayphonemes = phonicsw && !converting && options.option( "sharks_sayphonemes");
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
//    if(!converting && (!phonics || phonicsw && !blended)){
//        if(shark.phonicshark)
//            optionlist = new String[] {"maze-freeform", "maze-size=","maze-3d", "amaze-mustclick","maze_tiles"};
//        else
//            optionlist = new String[] {"maze-freeform", "maze-size=","maze-3d", "spell_freepeep","amaze-mustclick","maze_tiles"};
//    }
    if(!shark.phonicshark && !converting && (!phonics || phonicsw && !blended)){
        optionlist = new String[] {"maze-freeform", "maze-size=","maze-3d", "spell_freepeep","amaze-mustclick","maze_tiles"};
    }
    else optionlist = new String[] {"maze-freeform", "maze-size=","maze-3d","amaze-mustclick","maze_tiles"};
    if(phonicsw && !converting) optionlist=u.addString(optionlist, "maze_sayphonemes");
//    if(!converting && (!phonics || phonicsw && !blended)) freepeep = options.option(optionlist[3]);
    if(!shark.phonicshark && !converting && (!phonics || phonicsw && !blended)) freepeep = options.option(optionlist[3]);
    sayphonemes = phonicsw && !converting && options.option( "maze_sayphonemes");
    tiles = options.option("maze_tiles");
    pressenter = rgame.getParm(tiles?"clicktick":"pressenter");
    gamePanel.setBackground(u.fairlydarkcolor());
//    gamepanel.sort3dmovers = true;
//    wantSprite = tiles;
    wantSprite = false;
    buildTopPanel();
    gamePanel.freeze = true;
//    gamePanel.setCursor(sharkStartFrame.nullcursor);
    m = new amaze(gamePanel,this);
    m.mustclick = options.option("amaze-mustclick");
    if( m.mustclick) {
      bites = true;
      buildTopPanel();
    }
    markoption();
    pictureat = 1;
    help(m.freeform?"help_ownmaze":
           (m.mustclick?"help_animalmaze1":"help_animalmaze1x"));
 }
 //-------------------------------------------------------------
 public void resize() {gamePanel.mtot=0; restart();}
 //--------------------------------------------------------------
 public void restart() {
    curr = -1;
    slotsused = 0;
    gotmaze=false;
    gamePanel.clearWholeScreen = true;
    converting = gotmaze = busy = explode = typing = waiting = shrinking = false;
    super.restart();
    tiles = options.option("maze_tiles");
//    wantSprite = tiles;
    wantSprite = false;
    pressenter = rgame.getParm(tiles?"clicktick":"pressenter");
//    if(!converting && (!phonics || phonicsw && !blended)) freepeep = options.option(optionlist[3]);
    if(!shark.phonicshark && !converting && (!phonics || phonicsw && !blended)) freepeep = options.option(optionlist[3]);
    sayphonemes = phonicsw && !converting && options.option( "maze_sayphonemes");
    m = new amaze(gamePanel,this);
    m.mustclick = options.option("amaze-mustclick");
    bites = m.mustclick;
    buildTopPanel();
    markoption();
    help(m.freeform?"help_ownmaze":(m.mustclick?"help_animalmaze1":"help_animalmaze1x"));
    keypadDeactivate();
 }
 //--------------------------------------------------------------
 public void afterDraw(long t) {
    short i, ct = 0;
       if(m.freeform && gamePanel.wantPolygon || gotmaze){
         return;
       }
       int pathw;
       gotmaze = true;
       if(m.freeform) {
       help(m.mustclick?"help_animalmaze1":"help_animalmaze1x");
          pathw = mover3d.BASEU/(20 + m.mazesize/8);
       }
       else pathw = mover3d.BASEU/(12 + m.mazesize/8);
       int hh = getContentPane().getHeight()-topPanel.getHeight();
       int ww = getContentPane().getWidth();
       if(!m.buildmaze(m.freeform?gamePanel.gettingPolygon:null,
                    - mover3d.BASEU/2 + pathw*2,
                    - mover3d.BASEU/2*hh/ww+pathw,
                    mover3d.BASEU - pathw*3,
                    mover3d.BASEU*hh/ww-pathw*2,
                    pathw)) {
          restart();
          return;
       }
       m.openExit();
       gamePanel.clear();
       addMover(m,mover3d.BASEU+pathw,0);

       for(i=0;i<sharktot;++i) m.addAnimal();
 }
 //-------------------------------------------------------------
   public void peep1() {
     if(m.input == null) return;
     m.input.showmodel =  gtime() + 2000;
     ++peeptot;
     peepsView.setText(String.valueOf(peeptot));
     studentrecord.peepList = u.addString(studentrecord.peepList,currword.v());
     gamePanel.freeze = false;
     requestFocus();
   }
   //--------------------------------------------------------------------
   public void listen1() {
     if(currword != null && !converting && phonicsw && !sayphonemes) spokenWord.sayPhonicsWhole(currword);
     else if(phonics && currword != null && !converting ) {
       spokenWord.sayPhonicsWord(currword, 200, true,true,false);
       gamePanel.startrunning();
       gamePanel.requestFocus();
     }
     else super.listen1();
   }
  //----------------------------------------------------------
  class amaze  extends maze {
     public amaze(runMovers r,sharkGame gg) {
        super(r,gg);
     }
     Polygon expoly;
     final long explodetime = 800, waittime = 1000;
     int explodewidth,explodeheight;
     Point excentre;
     word convertfrom;
     int paintx,painty,paintw,painth;
     long explodestart,shrinkstart,startwait;
     javax.swing.Timer timerz;

   //-----------------------------------------------------------------
   public void startexplode() {
           if(attackanimal()) {
              explodestart = gtime();
              busy = explode=true;
           }
 }
   public void mouseClicked(int mx, int my) {
       if(!busy ) {
          if(attackanimal()) {
             explodestart = gtime();
             busy = explode=true;
           }
//          else noise.beep();
        }
     }
     public void extradraw(Graphics g, long t, int mx, int my) {
        if(explode) {
           explodewidth = screenwidth/2;
           explodeheight = screenheight/2;
           if(t - explodestart > explodetime) {
              explode = false;
              typing = true;
              excentre = new Point(Math.max(explodewidth,Math.min(screenwidth-explodewidth,mx)),
                                   Math.max(explodeheight,Math.min(screenheight-explodeheight,my)));
              g.setColor(m.animalcolor);
              expoly = u.explode(excentre.x,excentre.y,explodewidth/2,explodewidth,explodeheight);
              paintx = excentre.x-explodewidth/2;
              painty = excentre.y-explodeheight/2;
              paintw = explodewidth;
              painth = explodeheight;
              if(keypad.keypadname != null) gamePanel.setCursor(null);
              input = new sharkInput(new sharkAction()   {
                   public void enter() {
 //                     if(converting) {
                         if(currword.v().equals(u.stripspaces(input.val))) {
                            input.ended = true;
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
//                        ToolTipManager.sharedInstance().setEnabled(sharkStartFrame.mainFrame.wanthelp);
                       gamePanel.currgame.changehelp();
                   }
                   public void error() {
                      gamePanel.currgame.error(currword.v());
                      mazesprite.setControl("error");
                      gamescore(-1);
                      noise.groan();
                   }
                   public void end() {
                       keypadDeactivate();
                       waiting = true;
                       startwait = gtime();
                       currword = null;
                       gamePanel.winsprite = false;
                       gamePanel.showSprite = false;
//startPR2005-04-18^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                       if(Demo_base.isDemo){
                         if (Demo_base.demoIsReadyForExit(0)) return;
                       }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                       gamescore(2);
                       help(mustclick?"help_animalmaze1":"help_animalmaze1x");
                       if (curr >= wordtot-1) {
                           score(gametot1);
                           timerz = new javax.swing.Timer(1000,new ActionListener() {
                              public void actionPerformed(ActionEvent e) {
                                 if(!m.stillactive()) {
                                    timerz.stop();
                                    exit(2000);
                                  }
                              }
                           });
                           timerz.start();
                       }
                       else{
      if(delayedflip && !completed){
         flip();
      }
                       }
                   }
                   public void button(byte bno) {}
                   public void mouseClick() {
                     if(phonicsw && !sayphonemes) spokenWord.sayPhonicsWhole(currword);
                     else if (phonics) spokenWord.sayPhonicsWord(currword,500,true,false,false);
                     else   currword.say();
                  }
              });
              if(!converting) input.cantgoback=true;
              input.background = m.animalcolor;
              input.textcolor = Color.black;
              input.modelcolor = Color.red;
              input.cursorcolor = Color.red;
              if(!tiles) keypad.activate(gamePanel.currgame,keypad.addmore(extrakeys,rgame.w));
              if(freepeep) {
                      input.showmodel =  gtime + FREEPEEPINT;
                      input.startafter =  gtime + FREEPEEPINT+500;
              }
             if(converting) {
                   help("help_catchshark4" + (tiles?"tiles":""));
                   convertfrom = rgame.w[++curr];
                   currword =  rgame.w[++curr];
                   input.model = currword.v();
                   input.dontcompare = true;
                   input.val = convertfrom.v();
                   if(input.model.length() == input.val.length()) input.maxlen =(short)(input.model.length());
                   else                  input.maxlen=(short)(Math.max(input.model.length(),input.val.length())+4);
                   if(!firsttime)
                     spokenWord.findandsay(new Object[] {convertfrom,
                           "%% "+rgame.getParm("convertto"),currword});
                   else  spokenWord.findandsay(new Object[] {convertfrom,
                           "%% "+rgame.getParm("convertto"),currword,
                                 pressenter});
                   firsttime=false;
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
                  input.model = currword.v();
                  if(!phonics && syllables && currword.value.indexOf('/')>=0 && !tiles) {
                      input.splits = currword.vsplit();
                   }
                   if(phonicsw && !sayphonemes) spokenWord.sayPhonicsWhole(currword);
                   else if (phonics) spokenWord.sayPhonicsWord(currword,500,true,false,false);
                   else   currword.say();
             }
             if(tiles) {
               input.setletterlist(rgame.w,currword,converting?convertfrom:null);
               gamePanel.winsprite = true;
               gamePanel.setCursor(null);
               m.iscursor = true;
             }
             if(!phonics && showpicture)  new showpicture();
           }
           else {
              explodewidth =  Math.max(screenwidth/20,(int)(explodewidth*(t-explodestart)/explodetime));
              explodeheight =  Math.max(screenheight/20,(int)(explodeheight*(t-explodestart)/explodetime));
              excentre = new Point(Math.max(explodewidth,Math.min(screenwidth-explodewidth,mx)),
                                   Math.max(explodeheight,Math.min(screenheight-explodeheight,my)));
              g.setColor(m.animalcolor);
              g.fillPolygon(u.explode(excentre.x,excentre.y,explodewidth/2,explodewidth,explodeheight));
           }
         }
         else if(shrinking) {
           explodewidth = screenwidth/2;
           explodeheight = screenheight/2;
           if(t - shrinkstart > explodetime) {
              shrinking = false;
              busy = false;
              setforexit(
                 -mover3d.BASEU/2,
                 -mazeheight/2 + slotsused*mazeheight/atot);
              ++slotsused;
           }
           else {
              explodewidth =  Math.max(screenwidth/20,(int)(explodewidth*(explodetime-t+shrinkstart)/explodetime));
              explodeheight =  Math.max(screenheight/20,(int)(explodeheight*(explodetime-t+shrinkstart)/explodetime));
              excentre = new Point(Math.max(explodewidth,Math.min(screenwidth-explodewidth,mx)),
                                   Math.max(explodeheight,Math.min(screenheight-explodeheight,my)));
              g.setColor(m.animalcolor);
              g.fillPolygon(u.explode(excentre.x,excentre.y,explodewidth/2,explodewidth,explodeheight));
           }
        }
        else if(typing) {
           if(waiting && t > startwait+waittime) {
              typing=waiting=false;
              shrinking = true;
              shrinkstart = t;
              input = null;
           }
           else {
              g.setColor(m.animalcolor);
              if(expoly != null) g.fillPolygon(expoly);
              if(manager != null && input != null)
                 input.paint(g,paintx,painty,paintw,painth,manager.screenheight);
           }
        }
     }
  }
}
