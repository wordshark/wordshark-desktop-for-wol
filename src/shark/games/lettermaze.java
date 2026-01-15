package shark.games;

import java.awt.*;
import java.awt.event.*;

import shark.*;
import shark.sharkGame.*;

public class lettermaze extends  sharkGame {
 static final short FREEPEEPINT = 4000;
 boolean freepeep;
 amaze m;
 short curr,currchar,wordtot;
 String currstring[];
 boolean converting, gotmaze, busy, explode, typing,waiting,shrinking;
 short slotsused;
 final short pathfactor = 32;
 long wantnext;
 long endbusy;
 boolean showpicture = !specialprivateon;
 public word[] currwords = new word[]{};

 public lettermaze() {
//    int st = this.getContentPane().getLocationOnScreen().x+2;
//    this.setBounds(-st,0,getWidth()+st*2,getHeight());
    errors = true;
    gamescore1 = true;
    deaths = false;
    wantspeed=false;
    peeps = true;
    listen= true;
    peep = true;
    forceSharedColor = true;
    clickonrelease = true;
//    if(!phonics || phonicsw && !blended){
//        if(shark.phonicshark)
//            optionlist = new String[] {"maze-freeform", "maze-size=","maze-3d"};
//        else
//            optionlist = new String[] {"maze-freeform", "maze-size=","maze-3d", "spell_freepeep"};
//    }
    if(!shark.phonicshark && (!phonics || phonicsw && !blended)){
       optionlist = new String[] {"maze-freeform", "maze-size=","maze-3d", "spell_freepeep"};
    }
     else optionlist = new String[] {"maze-freeform", "maze-size=","maze-3d"};
//    opnarr = new String[] {"Draw your own shape for maze", "Maze complexity","3-dimensional view","Free peep before spelling"};
//    if(!phonics || phonicsw && !blended) freepeep = options.option(optionlist[3]);
    if(!shark.phonicshark && (!phonics || phonicsw && !blended)) freepeep = options.option(optionlist[3]);
    int segno = 0;
    word prewords[] = u.shuffle(rgame.w);
    for(int i = 0; i < prewords.length; i++){
        segno +=  phonics? prewords[i].phsegs().length:  prewords[i].segments().length;
        currwords = u.addWords(currwords, prewords[i]);
        if(segno > 20)
            break;            
    }
    wordtot = (short)currwords.length;
    gamePanel.setBackground(u.fairlydarkcolor());
    this.wantSprite = false;
    buildTopPanel();
//    gamePanel.setCursor(sharkStartFrame.nullcursor);
    m = new amaze(gamePanel,this);
    m.wallheight /= 4; // reduce wall height
    m.lettermaze = true;
    markoption();
    String helpstring = "help_lettermaze"+helpsuff();
    if(specialprivateon){
      if(helpstring.equals("help_lettermaze"))
        helpstring = helpstring.concat("def");
    }
    help(m.freeform?"help_ownmaze":helpstring);
    pictureat = 1;
 }
 String helpsuff() {
   if(phonicsw) return  blended?"phb":"phw";
   else if(phonics) return "ph";
   else return "";
 }
 //-------------------------------------------------------------
 public void resize() {gamePanel.mtot=0; restart();}
 //--------------------------------------------------------------
 public void restart() {
    curr = currchar = 0;
    slotsused=0;
    gotmaze=false;
    gamePanel.clearWholeScreen = false;
//    if(!phonics || phonicsw && !blended) freepeep = options.option(optionlist[3]);
    if(!shark.phonicshark && (!phonics || phonicsw && !blended)) freepeep = options.option(optionlist[3]);
    super.restart();
    m = new amaze(gamePanel,this);
    m.wallheight /= 4; // reduce wall height
    m.lettermaze = true;
    markoption();
    help(m.freeform?"help_ownmaze":(specialprivateon?"help_lettermazedef":"help_lettermaze"));
 }
 //--------------------------------------------------------------
 public void afterDraw(long t) {
       short i,j, ct = 0,ltot=0,len,lettermax=0,wantleft;
       String s;
       if(m==null) return;
       if(endbusy != 0 && gtime>endbusy) {
         busy = false;
         endbusy  = 0;
       }
       if(wantnext != 0 && gtime > wantnext) {
            if(delayedflip && !completed){
               flip();
            }
         wantnext = 0;
         currword =  currwords[++curr];
         busy = false;
         if( phonicsw) spokenWord.sayPhonicsWhole(currword);
         else if(phonics) spokenWord.sayPhonicsWord(currword,500,false,false,!singlesound);
         else {
          currword.say();
          if(showpicture)
              new showpicture();
         }
         currstring = phonics? currword.phsegs():currword.segments();
         if(freepeep && (!phonics || phonicsw && !blended))  new peepword(new String[] {currword.v()},
                 rgame.getParm("spellthisword"), FREEPEEPINT,gamePanel.currgame);
       }

       if(gamePanel.wantPolygon || gotmaze) return;
       int pathw;
       gotmaze = true;
       for(i=0;i<wordtot;++i) {
          ltot +=  (len = (short)currwords[i].v().length());
          lettermax =  (short)Math.max(lettermax,len);
       }
       wantleft = (short)((lettermax+1)/2);
       if(m.freeform){
          pathw = mover3d.BASEU/(20 + m.mazesize/8);
          String helpstring = "help_lettermaze"+helpsuff();
          if(specialprivateon){
            if(helpstring.equals("help_lettermaze"))
               helpstring = helpstring.concat("def");
          }
          help(helpstring);
       }
       else pathw = mover3d.BASEU/(12 + m.mazesize/8);
       int hh = getContentPane().getHeight() - topPanel.getHeight();
       int ww = getContentPane().getWidth();
       if(!m.buildmaze(m.freeform?gamePanel.gettingPolygon:null,
                    - mover3d.BASEU/2 + wantleft * pathw,
                    - mover3d.BASEU/2*hh/ww+pathw,
                    mover3d.BASEU - (1+wantleft)*pathw,
                    mover3d.BASEU*hh/ww-pathw*2,
                    pathw)) {
          restart();
          return;
       }
       m.openExit();
       gamePanel.clear();

       for(i=0;i<wordtot;++i) {
          String ss[] =  phonics?currwords[i].phsegs(): currwords[i].segments();
          if(m.celltot < ltot) {
             if(!m.freeform && m.mazesize < 100) {
                student.setOption("maze-size",(short)Math.min(m.mazesize+10,100));
              }
             else if(m.freeform) {
                u.okmess(
                     "Maze building",
//startPR2004-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//                     "Cannot fit all letters into maze. Please redraw.");
                                  "Cannot fit all letters into maze. Please redraw.", this.gamePanel.currgame);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
             }
             else --wordtot;
             restart();
             return;
          }
          m.addLetters(ss, i, wordtot);
       }
       addMover(m,mover3d.BASEU/20,0);
       currword =  currwords[0];
       if(phonicsw) spokenWord.sayPhonicsWhole(currword);
       else if(phonics) spokenWord.sayPhonicsWord(currword,500,false,false,!singlesound);
       else {
        currword.say();
        if(showpicture)
            new showpicture();
       }
       currstring = phonics? currword.phsegs():currword.segments();
       if(freepeep && (!phonics || phonicsw && !blended))  new peepword(new String[] {currword.v()},
               rgame.getParm("spellthisword"), FREEPEEPINT,this);

 }
 public void listen1() {
   if(currword != null) {
     if(phonicsw) spokenWord.sayPhonicsWhole(currword);
     else if(phonics) spokenWord.sayPhonicsWord(currword,500,false,false,!singlesound);
     else super.listen1();
   }
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

   public void mouseClicked(int mx, int my) {
       if(!busy) {
          String gotch = attackletter();
          if(gotch != null) {
             if(gotch.equals(currstring[currchar]) || phonics && !phonicsw && !singlesound && samephonics(gotch)) {
                int i;
                busy = true;
                if(phonicsw) {
                  if(!currword.onephoneme) {
                    spokenWord.sayPhonicsBit(currword, currchar);
                    spokenWord.sayPhonicsSyl(currword,currword.fullval(currchar+1));
                  }
                 }
                else if(phonics) spokenWord.sayPhonicsWord(currword,500,false,false,true);
                if(phonicsw &&  currword.phonicsmagic != null
                   && currstring[currchar].length()>1 && (i=currstring[currchar].indexOf('-')) >= 0) {
                  ((letter)frozenanimal).s =
                       currstring[currchar] = currstring[currchar].substring(0,i)+ "  " + currstring[currchar].substring(i+1);
                }
                if(phonicsw &&  currword.ismagicsyl(currchar+1)) {
                   String s = combined(currstring,currchar-1)+currstring[currchar-1].substring(0,2);
                   setforexit(
                      -mover3d.BASEU/2
                       + (lettermetrics.stringWidth(s))*(mover3d.BASEU)/screenmax+8,
                        -mazeheight/2 + slotsused*mazeheight/wordtot);
                }
                else setforexit(
                   -mover3d.BASEU/2
                    + (lettermetrics.stringWidth(combined(currstring,currchar))
                   + lettermetrics.stringWidth(currstring[currchar])/2)*(mover3d.BASEU)/screenmax+8,
                     -mazeheight/2 + slotsused*mazeheight/wordtot);
                ++currchar;
                if(currchar<currstring.length && currstring[currchar].equals(" ")) ++currchar;
                if(currchar >= currstring.length) {
                   if(phonicsw) spokenWord.sayPhonicsWhole(currword);
                   ++slotsused;
//startPR2005-04-18^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                   if(Demo_base.isDemo){
                     if (Demo_base.demoIsReadyForExit(0)) return;
                   }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                   gamescore(2);
                   currchar = 0;
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
                   else {
                      wantnext = gtime + 2000;
                   }
                }
                else 
                    endbusy = spokenWord.endsay2;
            }
             else {
                gamePanel.currgame.error(currword.v());
                mazesprite.setControl("error");
                gamescore(-1);
                if(phonics)
                    spokenWord.sayPhonicsBit(gotch,currwords);
                 else  noise.groan();
                frozenanimal = null;
             }
          }
 //         else noise.beep();
       }
     }
  }
  boolean samephonics(String gotch) {
     int i;
     for(i=0;i<wordtot;++i) {
        if(currwords[i] != currword && currwords[i].v().equals(gotch) && currword.samephonics(currwords[i])) return true;
     }
     return false;
  }
  String combined(String[] ss, int pos) {
    String s = new String();
    for(int i = 0; i < pos; ++i) s += ss[i];
      return s;
  }
}
