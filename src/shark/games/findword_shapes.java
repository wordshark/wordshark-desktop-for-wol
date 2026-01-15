package shark.games;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import shark.*;
import shark.sharkGame.*;

public class findword_shapes extends sharkGame {//SS 03/12/05
  wordButton b[];
  short btot,curr=-1,rows,cols;
  int wordWidth,wordHeight,buttonWidth,buttonHeight;
  Color buttonColor;
  boolean shapes,drawshapes,findpicture,findjustword;
  int doDoubleRound = -1;
  long shapestart;
  boolean invis;
  javax.swing.Timer shapetimer;
  boolean givesound,givepic,giveword;
  showword showword;
  boolean wantphonicsw = rgame.getParm("phonicsw") != null || phonics && !phonicsw;
//startPR2004-11-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  int randNo;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  boolean bingo = (rgame.getParm("bingo") != null);   //       v5 rb 19/3/08 start
  bingocard leftbingo,rightbingo;
  int rightorder[];
  boolean inright, startright;
  int rowdepth,itemwidth,bingopicy, picwidth = mover.WIDTH*5/12;
  sharkImage bingopointer;
  long peepat, wantwin;
  boolean winleft;
//startPR2008-10-08^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//  computersprite cs;
  int speakerheight=-1;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2008-08-29^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//  Font bfont, pfont;
//  FontMetrics bm, pm;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  boolean picfromsound = rgame.getParm("picfromsound") != null;
//  boolean learning = orilearn = rgame.getParm("learning") != null, holeinmiddle;
  boolean learning = rgame.getParm("learning") != null, holeinmiddle;
  word[] savewords;
  boolean wantplay;
  boolean savebingo;
  int lastsay;
  long wantnext,cancelword,restartin;                                     //       v5 rb 19/3/08 end
  boolean showplay;
//startPR2008-08-29^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  mover fadeout;
  int zoomlength = 400;
  boolean showfade = true;
  boolean zoom = true;
  boolean showwords;
  boolean endturn;
  boolean dospeaker;
  boolean dopic;
  long endzoom=-1;
  mover zoommovers_left[] = new mover[]{};
  mover zoommovers_right[] = new mover[]{};
  int noofrows;
  showpicture ss;
//startPR2008-09-23^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  speaker speaker;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2008-10-22^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  int leftorder[];
  int compdummyqs[];
  int makeeasier = 1;  // the higher the number the easier the game
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2008-10-24^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  boolean doshowword;
  boolean orilearn = learning;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  Font speakerfont;

  public findword_shapes() {
    errors = true;
    gamescore1 = true;
    peeps = true;
    listen= true;
     peep = true;
    if(learning) {
      if(sp != null){
          sp.switchuser(0, true);
      }
//startPR2008-08-29^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  //    optionlist = new String[]{"bingolearn_nowords"};
 //     showwords = options.option(optionlist[0]);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      savebingo = bingo;
      bingo = false;
      savewords = new word[rgame.w.length];
      System.arraycopy(rgame.w,0,savewords,0,rgame.w.length);
      rgame.w = u.stripdups(rgame.w);
      errors = false;
      gamescore1 = false;
      peeps = false;
      listen= false;
      peep = false;
      timer = false;
      gamescore1 = gamescore2 = pupilscore = false;
//      if(!specialprivate){
      mcheckbox cbShowWords = new mcheckbox(rgame.getParm("showwords"));
      extracheckboxes = new javax.swing.JCheckBox[]{cbShowWords};
      showwords = !student.option("learn_noshowwords") ;
      cbShowWords.setSelected(showwords);
      cbShowWords.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            showwords = ((mcheckbox)e.getSource()).isSelected();
            if(!showwords)
                student.setOption("learn_noshowwords");
            else
                student.clearOption("learn_noshowwords");
            for(int i=0;i<btot;++i) {
                b[i].hadsay = false;
            }
         }
      });
 //     }
 //     else
  //        fillerpanel = new JPanel();
    }
    if(bingo && gamescore2)competitivescore = true;
//    clickonrelease=true;
    shapes = (rgame.getParm("shapes") != null);
    findpicture = (rgame.getParm("findpicture") != null);
    findjustword = (rgame.getParm("findjustword") != null);
    if(shapes) wantspeed = true;
  //  if(wantphonicsw && (!(phonics && !phonicsw))) wantspeed = true;
    rgame.options |= word.CENTRE;
    btot = (short)rgame.w.length;
    b = new wordButton[btot];
    buttonColor = new Color(128+u.rand(110),128+u.rand(110),128+u.rand(110));
//startPR2008-10-21^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    if(bingo) {
      gamePanel.clearWholeScreen = true;
    }
    setup();

      if(learning  && sp != null){
          sp.switchuser(0, true);
      }

//    if(learning) {
//      givesound = false;
//      giveword = false;
//      givepic = false;
//    }
//    else if(findjustword) {
//      givesound = true;
//      giveword = false;
//      givepic = false;
//    }
//    else if(findpicture) {
//        if(picfromsound || phonics) {
//          givesound = true;
//          giveword = false;
//          givepic = false;
//          nolistenpic = true;
//        }
//        else {
//          givesound = false;
//          giveword = true;
//          givepic = false;
//          peep = peeps = listen =false;
//        }
//    }
//    else {
//      givepic = !phonics;
//      givesound = !fl;
//      giveword = false;
//      if(!givesound) {listen=false; peep=false;peeps = false;}
//    }
//    if(bingo) {
//startPR2008-08-29^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      optionlist = new String[]{"bingolearn_endturn"};
//      endturn = options.option(optionlist[0]);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//       inright = startright = gamescore2 && student.option("bingo_startright");
//       leftbingo = new bingocard(true);
//       rightbingo = new bingocard(false);
//startPR2008-10-13^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//       zoommovers_right = u.addmover(zoommovers_right,rightbingo);
//       zoommovers_left = u.addmover(zoommovers_left,leftbingo);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//       oloop: while(true) {
//         rightorder = u.shuffle(u.select(rgame.w.length, rgame.w.length));
//         if(rightorder.length < 3) break;
//         for(int i=0;i<rightorder.length;++i) {
//            if(i == rightorder[i]
//               || !startright && i+1 == rightorder[i] || startright && i == rightorder[i]+1) continue oloop;
//         }
//         break;
//       }
//       bingoButtons();
//    }
//    else setupButtons();
//    if(learning) new play();
//    buildTopPanel();
//    if(bingo) {                                      //       v5 rb 19/3/08 start
//      gamePanel.clearWholeScreen = true;
//      if(!gamescore2) {
//        if (findpicture)  help(picfromsound?"help_bingopicsee":"help_bingopicseelook");
//        else help("help_bingo");
//      }
//      else {
//        if (findpicture)  help(picfromsound?"help_bingopicsee2":"help_bingopicseelook2");
//        else help("help_bingo2");
//      }
//    }                                                //       v5 rb 19/3/08 end
//    else if(findpicture) {
//      gamePanel.clearWholeScreen = true;
//      if(giveword) help( "help_findpicsee");
//      else help(phonicsw?"help_findpicp":"help_findpic");
//    }
//    else if(phonics && !phonicsw && !blended) help("help_find1ph");
//    else if(wantphonicsw) {
//                 help(blended ? "help_findph2":"help_findph");
//    }
//    else help(shapes?"help_findshape2":(blended?"help_find2":"help_find1"));
//startSS^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
//    if((sharkStartFrame.switchOptions == 1)||(sharkStartFrame.switchOptions ==2)){
//      sharkGame.focusInOrder = true;   //1-d array  in use
//      gamePanel.SwitchSetUp();
//      gamePanel.detectEnterSpace = true;
//    }
//endSS^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
 }

//startPR2008-10-21^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
 void setup() {
   curr=-1;
   inright = false;
   if(learning) {
     givesound = false;
     giveword = false;
     givepic = false;
   }
   else if(findjustword) {
     givesound = true;
     giveword = false;
     givepic = false;
   }
   else if(findpicture) {
       doDoubleRound = 0;
       if(picfromsound || phonics) {
         givesound = true;
         giveword = picfromsound && !bingo;
         givepic = false;
         nolistenpic = true;
//startPR2008-10-24^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         peep = peeps = false;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       }
       else {
         givesound = false;
         giveword = true;
         givepic = false;
         peep = peeps = listen =false;
       }
   }
   else {
     doDoubleRound = 0;
     givepic = (!(specialprivate && specialprivateon));
     givesound = true;//(!fl || specialprivate || shapes || bingo); //2012-04-19
     giveword = false;
     if(!givesound) {
         peep=false;
         peeps = false;
//         if(!fl){
             listen=false;
//         }
     }
   }
   if(bingo) {
//startPR2008-08-29^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     optionlist = new String[]{"bingolearn_endturn"};
     endturn = options.option(optionlist[0]);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      inright = startright = gamescore2 && student.option("bingo_startright");
      gamestartflipped = inright;
      leftbingo = new bingocard(true);
      rightbingo = new bingocard(false);
//startPR2008-10-13^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      zoommovers_right = u.addmover(zoommovers_right,rightbingo);
      zoommovers_left = u.addmover(zoommovers_left,leftbingo);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2008-10-22^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      oloop: while(true) {
//        rightorder = u.shuffle(u.select(rgame.w.length, rgame.w.length));
//        if(rightorder.length < 3) break;
//        for(int i=0;i<rightorder.length;++i) {
//           if(i == rightorder[i]
//              || !startright && i+1 == rightorder[i] || startright && i == rightorder[i]+1) continue oloop;
//        }
//        break;
//      }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      bingoButtons();
   }
   else setupButtons();
   if(learning) new play();
   buildTopPanel();
   if(bingo) {                                      //       v5 rb 19/3/08 start
     gamePanel.clearWholeScreen = true;
     if(!gamescore2) {
       if (findpicture)  help(picfromsound?(specialprivateon?"help_bingopicseedef":"help_bingopicsee"):"help_bingopicseelook");
       else help((specialprivateon?"help_bingodef":"help_bingo"));
     }
     else {
       if (findpicture)  help(picfromsound?"help_bingopicsee2":"help_bingopicseelook2");
       else help("help_bingo2");
     }
   }                                                //       v5 rb 19/3/08 end
   else if(learning) {
       help( "help_learn");
   }
   else if(findpicture) {
     gamePanel.clearWholeScreen = true;
     if(giveword) help( "help_findpicsee");
     else help(phonicsw?"help_findpicp":"help_findpic");
   }
   else if(phonics && !phonicsw && !blended) help("help_find1ph");
   else if(wantphonicsw) {
                help(blended ? "help_findph2":"help_findph");
   }
   else help(shapes?"help_findshape2":(blended?"help_find2":(specialprivateon?"help_find1def":"help_find1")));
//startSS^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
   if((sharkStartFrame.switchOptions == 1)||(sharkStartFrame.switchOptions ==2)){
     sharkGame.focusInOrder = true;   //1-d array  in use
     gamePanel.SwitchSetUp();
     gamePanel.detectEnterSpace = true;
   }
//endSS^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
//startPR2008-10-29^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   markoption();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR

 //-------------------------------------------------------------
 void shapestart() {
    if(shapes) {
       shapestart = gtime();
       drawshapes = false;
       gamePanel.clearall();
    }
  }
//startPR2008-08-29^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  public void restart() {
    gamePanel.removeAllMovers();
     super.restart();
     if(!bingo && learning){
//       showwords = options.option(optionlist[0]);
       setupButtons();
       buildTopPanel();
       new play();
     }
     else if (bingo){
       wantnext = 0;
       gamePanel.showSprite = true;
       gamePanel.setCursor(null);
       setup();
     }
  }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   public void afterDraw(long t){
     int i;
     /*
     if(wantplay) {
       wantplay = false;
       errors = true;
       gamescore1 = true;
//startPR2008-08-29^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       optionlist = null;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       listen= true;
       learning = false;
       bingo = savebingo;
        if(bingo) {
          gamePanel.clearWholeScreen = true;
        }
       gamePanel.removeAllMovers();
       rgame.w = savewords;
//startPR2008-08-29^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//       bfont = pfont = null;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       givesound = true;
       giveword = true;
       givepic = false;
       nolistenpic = true;
       curr = -1;
       btot = (short)rgame.w.length;
       b = new wordButton[btot];
       if(bingo) {
//startPR2008-09-23^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         peep = peeps = false;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2008-08-29^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         optionlist = new String[]{"bingolearn_endturn"};
         endturn = options.option(optionlist[0]);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          inright = startright = gamescore2 && student.option("bingo_startright");
          gamestartflipped = inright;
          leftbingo = new bingocard(true);
          rightbingo = new bingocard(false);
//startPR2008-10-13^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          zoommovers_right = u.addmover(zoommovers_right,rightbingo);
          zoommovers_left = u.addmover(zoommovers_left,leftbingo);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2008-10-22^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//          oloop: while(true) {
//            rightorder = u.shuffle(u.select(rgame.w.length, rgame.w.length));
//            if(rightorder.length < 3) break;
//            for(i=0;i<rightorder.length;++i) {
//               if(i == rightorder[i]
//                  || !startright && i+1 == rightorder[i] || startright && i == rightorder[i]+1) continue oloop;
//            }
//            break;
//          }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          bingoButtons();
       }
       else setupButtons();
       if(giveword) {
           if(orilearn) {
               help( "help_findpicseelearn");
           }
           else
               help( "help_findpicsee");
       }
       else help(phonicsw?"help_findpicp":"help_findpic");
       startplaytime = System.currentTimeMillis();
       buildTopPanel();
       return;
     }
     */
//startPR2008-08-29^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     if(bingo && zoom && gtime>endzoom){
       if(dospeaker){
         if (wantphonicsw) spokenWord.sayPhonicsWord(currword, 500, false, false, !singlesound);
         else currword.say();
//startPR2008-10-08^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//                 speaker = new speaker((inright ? mover.WIDTH * 3 / 4 : mover.WIDTH / 4) - picwidth / 2, bingopicy - rowdepth / 2, picwidth, rowdepth);
         speaker = new speaker((inright ? mover.WIDTH * 3 / 4 : mover.WIDTH / 4) - picwidth / 2, bingopicy - rowdepth / 2, picwidth, speakerheight);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         dospeaker = false;
       }
       if(dopic){
 //        ss = new showpicture((inright?mover.WIDTH*3/4 : mover.WIDTH/4)-picwidth/2, bingopicy-rowdepth/2, picwidth, rowdepth);
           ss = new showpicture((inright ? mover.WIDTH * 3 / 4 : mover.WIDTH / 4) - picwidth / 2, bingopicy - rowdepth / 2, picwidth, speakerheight, bingo);
//startPR2008-09-23^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//         if(givesound) ss.stopat = gtime + 6000;
//         else ss.dontremove = true;
         ss.dontremove = true;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         dopic = false;
         if(bingo && fadeout!=null)gamePanel.bringtotop(fadeout);
       }
//startPR2008-10-24^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       if(doshowword){
         showword = new showword(curr,inright?mover.WIDTH*3/4 : mover.WIDTH/4, bingo?bingopicy: mover.HEIGHT/2);
         doshowword = false;
       }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      if(wantwin != 0 && gtime > wantwin)  {
        wantwin = 0;
        win();
      }
      if(restartin !=0 && gtime>restartin) {
        curr = -1;
//        if(bingo && orilearn)
//            wantplay = true;
        inright=false;
        gametot1 = gametot2 = 0;
        restartin = 0;
//startPR2008-08-29^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//        super.restart();
          restart();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2008-09-26^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//        if(bingo) {
//          leftbingo = new bingocard(true);
//          rightbingo = new bingocard(false);
//          bingoButtons();
//        }
//        else setupButtons();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      }
      if(curr < 0) {
         if(bingo && startright)
             currword = rgame.w[rightorder[0]];
         else
             currword = rgame.w[0];
         if(givesound) {
           if (wantphonicsw) 
               spokenWord.sayPhonicsWord(currword, 500, false, false, !singlesound);
           else
               currword.say();
         }
         shapestart();
//startPR2004-11-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         randNo = u.rand(rgame.w.length);
         int repeats = 0;
         while(currword.v().equals(b[randNo].bWord.v()) && ++repeats<100){
           randNo = u.rand(rgame.w.length);
         }
         if(givepic) {
           if(bingo) {
//startPR2008-09-23^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//             showpicture ss = new showpicture((inright?mover.WIDTH*3/4 : mover.WIDTH/4)-picwidth/2, bingopicy-rowdepth/2, picwidth, rowdepth);
//             if(givesound) ss.stopat = gtime + 6000;
//             else ss.dontremove = true;
           //     ss = new showpicture((inright?mover.WIDTH*3/4 : mover.WIDTH/4)-picwidth/2, bingopicy-rowdepth/2, picwidth, rowdepth);
                ss = new showpicture((inright ? mover.WIDTH * 3 / 4 : mover.WIDTH / 4) - picwidth / 2, bingopicy - rowdepth / 2, picwidth, speakerheight, bingo);
                ss.dontremove = true;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          }
           else  if(holeinmiddle) {
               showpicture ss = new showpicture(mover.WIDTH/2-itemwidth/2, mover.HEIGHT/2-rowdepth/2, itemwidth, rowdepth, bingo);
               if(givesound) ss.stopat = gtime + 6000;
               else ss.dontremove = true;
           }
           else  new showpicture(b[randNo].x, b[randNo].y);
         }
         if(giveword)  showword = new showword(0,bingo?(inright?mover.WIDTH*3/4 : mover.WIDTH/4):mover.WIDTH/2,bingo?bingopicy: mover.HEIGHT/2);
//         new showpicture();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2008-10-24^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         if(givesound && !givepic && !giveword){
           speaker = new speaker((inright ? mover.WIDTH * 3 / 4 : mover.WIDTH / 4) - picwidth / 2, bingopicy - rowdepth / 2, picwidth, speakerheight);
         }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         curr = 0;
      }
      if(cancelword != 0 && gtime > cancelword) {
         cancelword = 0;
         if(showword != null) {removeMover(showword); showword=null;}
      }
      if(wantnext != 0 && gtime > wantnext) {    //       v5 rb 19/3/08 start
        wantnext  = 0;
        if(showword != null) {removeMover(showword); showword=null;}
        if(leftbingo.gotall()) {win(true);return;}
        else if(rightbingo.gotall()) {win(false);return;}
        if(bingo && delayedflip && !completed){
          flip();
        }
        if(bingo) {
          inright = !inright;
//startPR2008-10-13^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          setFaded();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          if(startright && inright || !startright && !inright) ++curr;
        }
        else  ++curr;
        if(bingo && !gamescore2)          gamePanel.showSprite = !inright;
//startPR2008-10-08^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//        if(bingo && !inright && cs != null) {removeMover(cs);cs=null;}
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        leftbingo.okpassed = rightbingo.okpassed = false;
        leftbingo.passbutton.used = rightbingo.passbutton.used = false;
//startPR2008-10-22^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//        if(curr >= rgame.w.length) {
        if(curr >= (bingo?leftorder.length:rgame.w.length)) {
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//           if(findpicture && !phonicsw && !giveword) {   // removed rb 6/4/08
//              giveword = true;
//              givesound = false;
//              curr = -1;
//              setupButtons();
//              setlisten(false);
//           }
//           else {
             score(gametot1);
             exit(1000);
//           }
        }
        else {
//startPR2008-10-22^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//             currword = rgame.w[inright?rightorder[curr]:curr];
          currword = rgame.w[inright?rightorder[curr]:(bingo?leftorder[curr]:curr)];
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
 //          if(bingo && gamescore2)
 //              flipstudent();
           if(givesound) {
//startPR2008-08-29^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//             if (wantphonicsw) spokenWord.sayPhonicsWord(currword, 500, false, false, !singlesound);
//             else currword.say();
             if(givesound && !givepic && !giveword){
               dospeaker = true;                  
             }
             else{
               if (wantphonicsw) spokenWord.sayPhonicsWord(currword, 500, false, false, !singlesound);
               else currword.say();
             }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
           }
           else if (bingo) noise.plop();
           if(givepic) {              //       v5 rb 19/3/08  start
             if (bingo) {
//startPR2008-08-29^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//               showpicture ss = new showpicture((inright?mover.WIDTH*3/4 : mover.WIDTH/4)-picwidth/2, bingopicy-rowdepth/2, picwidth, rowdepth);
//               if(givesound) ss.stopat = gtime + 6000;
//               else ss.dontremove = true;
                    dopic = true;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
             }
             else  showpicture(1000);
           }                           //       v5 rb 19/3/08 end
           if(giveword)
//startPR2008-10-24^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//           showword = new showword(curr,inright?mover.WIDTH*3/4 : mover.WIDTH/4, bingo?bingopicy: mover.HEIGHT/2);
           {
             if (bingo) doshowword = true;
             else showword = new showword(curr,inright?mover.WIDTH*3/4 : mover.WIDTH/4, bingo?bingopicy: mover.HEIGHT/2);
           }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
           if(bingo && inright && !gamescore2) checkrightcard();
         }
      }                                           //       v5 rb 19/3/08 end
//      if((!bingo && fl &&( !specialprivate || learning))  && (!findpicture || learning)  && spokenWord.isfree()) {//2012-04-19
      if(learning  && spokenWord.isfree()) {
         boolean gotover = false;
         for(i=0;i<btot;++i) {
            if(b[i].mouseOver && !b[i].removed) {
                gotover = true;
//startPR2008-08-29^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//                if(i != lastsay) {
                if(i != lastsay &&
                   wantnext < gtime &&
                   b[i].bingopic==null &&
                   (gamescore2||(rightbingo==null||(gamePanel.mousexs<(mover.WIDTH/2)*screenwidth/mover.HEIGHT)))) {
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                  if(specialprivateon){
                      rgame.w[b[i].wordnumber].sayandextra();
                  }
                  else
                    rgame.w[b[i].wordnumber].say();
                  lastsay = i;
                  b[i].hadsay = true;
                  showplay = true;
                }
                break;
            }
         }
         if(!gotover) lastsay = -1;
      }
      if(shapes && shapestart != 0 && gtime() > shapestart+ 1000 + 5000 / speed) {
            invis = true;
            shapestart = 0;
//            int i;                                                // start rb 3/1/08
//            Point pt[] = new Point[btot];
//            short o[] = u.shuffle(u.select(btot,btot));
//            for(i=0;i<btot;++i) {
//              pt[i] = new Point(b[i].x,b[i].y);
//            }
//            for(i=0;i<btot;++i) {
//              b[i].x = b[i].tox = pt[o[i]].x;
//              b[i].y = b[i].toy = pt[o[i]].y;
//              b[i].mouseOver = false ;
//             }                                                    // end rb 3/1/08
             closepicture();
             gamePanel.clearall();
      }
     
   }

//startPR2008-08-29^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2008-10-13^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    void setFaded(){
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      if(ss!=null)ss.kill=true;
//startPR2008-09-23^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      if(speaker!=null)speaker.kill=true;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      if(showfade)
        removeMover(fadeout);
      if(inright){
        if(showfade)
          addMover(fadeout, 0, 0);
        if(zoom){
          endzoom = gtime + zoomlength;
//startPR2008-10-13^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//          leftbingo.zoomto(leftbingo.zoomrect2, zoomlength);
//          rightbingo.zoomto(rightbingo.zoomrect1, zoomlength);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          for(int i = 0; i < zoommovers_left.length; i++)
            zoommovers_left[i].zoomto(zoommovers_left[i].zoomrect2,zoomlength);
          for(int i = 0; i < zoommovers_right.length; i++)
            zoommovers_right[i].zoomto(zoommovers_right[i].zoomrect1,zoomlength);
        }
      }
      else{
        if(showfade)
          addMover(fadeout, mover.WIDTH / 2, 0);
        if(zoom){
          endzoom = gtime + zoomlength;
//startPR2008-10-13^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//          rightbingo.zoomto(rightbingo.zoomrect2, zoomlength);
//          leftbingo.zoomto(leftbingo.zoomrect1, zoomlength);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          for(int i = 0; i < zoommovers_left.length; i++)
            zoommovers_left[i].zoomto(zoommovers_left[i].zoomrect1,zoomlength);
          for(int i = 0; i < zoommovers_right.length; i++)
            zoommovers_right[i].zoomto(zoommovers_right[i].zoomrect2,zoomlength);
        }
      }
    }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR

   void win(boolean left) {      // end of bingo game  // v5 rb 10/3/08 start
     winleft = left;
     wantwin= gtime+3000;
     spokenWord.findandsay("bingo1");
     if(gamescore2) {
       student.setOption("bingo_startright",!startright);
     }
   }

   void win() {
      boolean left = winleft;
      completed = true;
      if(left) gametot1+=2;
      else if(gamescore2) gametot2 +=2;
      score(gametot1);
      if(gamescore2) otherplayer.totscore += gametot2;
      if (left) {
        if(!gamescore2)
             flash(new String[] {(rgame.getParm("youwon")),
                "....................."}, 4000);
        else flash(new String[] {(rgame.getParm("winneris")+" "+sharkStartFrame.studentList[sharkStartFrame.currStudent].name),
              "....................."}, 4000);
         rewardcurr = true;
         forcerewardflipped = 0;
      }
      else if(gamescore2) {
          flash(new String[] {(rgame.getParm("winneris")+" " + otherplayer.name),
                "....................."}, 4000);
          rewardother = true;
          forcerewardflipped = 1;
      }
      else {
        flash(u.splitString(rgame.getParm("computerwin")), 4000);
        restartin = gtime+4000;
        completed = false;
        return;
      }
      exit(3000);
   }                              // v5 rb 10/3/08 end
   //--------------------------------------------------------------------
   public void listen1() {
     if(phonics && currword != null && wantphonicsw) {
       spokenWord.sayPhonicsWord(currword, 200, false,true,!singlesound);
       gamePanel.startrunning();
       gamePanel.requestFocus();
     }
     else {
       if(holeinmiddle) {
           if(!findpicture) {
             if(!(specialprivate && specialprivateon)){
                 showpicture ss = new showpicture(mover.WIDTH / 2 - itemwidth / 2, mover.HEIGHT / 2 - rowdepth / 2, itemwidth, rowdepth, bingo);
                 if(givesound) ss.stopat = gtime + 6000;
                 else ss.dontremove = true;
             }
             nolistenpic = true;
           }
       }
       else if(bingo && currword != null && givepic && givesound) {
           if(!(specialprivate && specialprivateon)){
//             showpicture ss = new showpicture((inright?mover.WIDTH*3/4 : mover.WIDTH/4)-picwidth/2, bingopicy-rowdepth/2, picwidth, rowdepth);
                ss = new showpicture((inright ? mover.WIDTH * 3 / 4 : mover.WIDTH / 4) - picwidth / 2, bingopicy - rowdepth / 2, picwidth, speakerheight, bingo);


             if(givesound) ss.stopat = gtime + 6000;
             else ss.dontremove = true;
           }
         nolistenpic = true;
       }
       super.listen1();
     }
//startPR2008-10-24^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//     if(bingo && givesound && !givepic && !giveword){
//startPR2008-10-08^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//                 speaker = new speaker((inright ? mover.WIDTH * 3 / 4 : mover.WIDTH / 4) - picwidth / 2, bingopicy - rowdepth / 2, picwidth, rowdepth);
//       speaker = new speaker((inright ? mover.WIDTH * 3 / 4 : mover.WIDTH / 4) - picwidth / 2, bingopicy - rowdepth / 2, picwidth, speakerheight);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//     }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   }
   public void peep1() {
     if(bingo) {
       new peepwordb(curr,inright?mover.WIDTH*3/4 : mover.WIDTH/4, bingo?bingopicy: mover.HEIGHT/2);
       ++peeptot;
       peepsView.setText(String.valueOf(peeptot));
       if(gamescore2 && inright)
             otherstudentrecord.peepList = u.addString(studentrecord.peepList,currword.v());
       else  studentrecord.peepList = u.addString(studentrecord.peepList,currword.v());
     }
     else super.peep1();
   }
   //----------------------------------------------------------
  void setupButtons() {
    short rows = (short)Math.sqrt(btot);
    if(findpicture && giveword && ((rows & 1) != 0)) ++rows;
    short perRow = (short)(btot / rows);
    if(!findpicture && maxWordLen() > 6) {
        perRow = 1; 
        rows = (short)((btot+1)/2);
    }
    short rowtot;
    short extralist[] = u.select(rows,(short)(btot - rows*perRow));
    if(findpicture || maxWordLen() <= 6 || !sharkStartFrame.currPlayTopic.nopictures) {
       holeinmiddle = true;
       if(btot < 5) perRow = (short)((btot)/2);
       else perRow  = (short)((btot-2)/2);
       if(btot%2 == 1)   extralist = new short[]{(short)0};
       else extralist = new short[0];
       rows = 3;
    }
//startPR2007-11-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    // this is an attempt help the problem of the given word blocking the pictures (for
    // word lists with more than 8 words) - it puts the extras preferentially at
    // the top and bottom rather than in the middle where the word is).
//    if(findpicture && giveword && extralist!=null && extralist.length>0){
//      short res[] = new short[extralist.length];
//      short p = 0;
//      short n = 0;
//      for (; p < extralist.length; p++) {
//        if (p % 2 == 0)
//          res[p] = n;
//        else {
//          res[p] = (short) (rows - 1 - n);
//          n++;
//        }
//      }
//      extralist = res;
//    }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    short extras = (short)extralist.length;
    short i,j,k;
    short gwidth = (short)(perRow * (perRow+1));
    short order[] = u.shuffle(u.select(btot,btot));
    int hh = mover.HEIGHT;
    int rowmax = perRow;
    for (j = 0; j < extralist.length; ++j) {
      if (extralist[j]>0) {
        ++rowmax; 
        break; 
      }
    }
    if(holeinmiddle && btot>=5) rowmax = 3;
    short it = doLayout(perRow, extralist, order, false);
    it = (short)Math.max(it, rowmax);
    
    itemwidth = mover.WIDTH/it;
  //  itemwidth = mover.WIDTH/rowmax;
    
    
    rowdepth = mover.HEIGHT/rows;
    doLayout(perRow, extralist, order, true);
    sizeFont();
    for(i=0;i<btot;++i) {
          int w = b[i].w;
          int h = b[i].h;
          if(!findpicture) {
            b[i].w = Math.min(w, buttonWidth);
            b[i].h = Math.min(h, buttonHeight);
          }
          else {
            b[i].pic.w = w * 3 / 4;
            b[i].pic.h = h * 3 / 4;
            if (!bingo) {
              b[i].pic.adjustSize(screenwidth, screenheight);
              if (b[i].pic.w > w * 3 / 4) {
                b[i].pic.h = b[i].pic.h * (w * 3 / 4) / b[i].pic.w;
                b[i].pic.w = w * 3 / 4;
              }
              if (b[i].pic.h > h * 3 / 4) {
                b[i].pic.w = b[i].pic.w * (h * 3 / 4) / b[i].pic.h;
                b[i].pic.h = h * 3 / 4;
              }
//              b[i].w = b[i].pic.w * 4 / 3;
//              b[i].h = b[i].pic.h * 4 / 3;
            }
          }
          int xPosition = b[i].x + w/2 - b[i].w/2;
          int yPosition = b[i].y + h/2 - b[i].h/2;
          addMover(b[i], xPosition, yPosition);
//startSS^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
          int switchx = xPosition + b[i].w/2;
          int switchy = yPosition + b[i].h/2;
          pointSwitch2.add(new Point(switchx, switchy));
//endSS^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
    }
  }
  
  
  short doLayout(short perRow, short extralist[], short order[], boolean doit){
    short i,j,k;
    short rowtot =0;
    short maxrowtot = 0;
    for(i=0,k = 0;k<btot;++i){
       if(holeinmiddle && i == 1) {
         if(btot<5) continue;
         else rowtot = 2;
       }
       else {
         rowtot = perRow;
         for (j = 0; j < extralist.length; ++j) {
           if (extralist[j] == i) {
             ++rowtot; break; }
         }
       }
       maxrowtot = (short)Math.max(maxrowtot, rowtot);
       for(j=0;j<rowtot;++j, ++k) {
           if(doit){
                b[k] = new wordButton(order[k]);
                b[k].w = itemwidth;
                b[k].h = rowdepth;
                if(holeinmiddle && i == 1)  
                    b[k].x = j==0 ? 0 : mover.WIDTH - b[k].w;
                else
                    b[k].x =  j*mover.WIDTH/rowtot + mover.WIDTH/rowtot/2 - b[k].w/2;
                b[k].y = i*b[k].h;
//startPR2004-11-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                b[k].bWord = rgame.w[order[k]];
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
           }
       }
    }    
    return maxrowtot;
  }
  //----------------------------------------------------------
 void bingoButtons() {                              //       v5 rb 19/3/08 start
//startPR2008-08-29^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   int re = background.getRed();
   int gr = background.getGreen();
   int bl = background.getBlue();
   int alpha = 130;
   int wwfade = gamescore2?mover.WIDTH/2:mover.WIDTH;
   fadeout = new mover.rectMover(new Rectangle(wwfade, mover.HEIGHT),new Color(re,gr,bl,alpha), new Color(re,gr,bl,alpha));
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2008-10-14^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//   int percard = btot*2/3;
   int percard = (short)rgame.w.length*2/3;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   b = new wordButton[btot = (short)(percard*2)];
   short rows = (short)Math.sqrt(percard);
   if(findpicture && giveword && ((rows & 1) != 0)) ++rows;
   short perRow = (short)(percard / rows);
   if(!findpicture && maxWordLen() > 6) {perRow = 1; rows = (short)((percard+1)/2);}
   short rowtot;
   short extralist[] = u.select(rows,(short)(percard - rows*perRow));
   short extras = (short)extralist.length;
   short i,j,k;
   short gwidth = (short)(perRow * (perRow+1));
   int order[];
   int hh = mover.HEIGHT;
   int sel1[];
   int sel2[];
//startPR2008-10-22^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   leftorder = new int[]{};
   for(int p = 0; p < rgame.w.length; p++)
     leftorder = u.addint(leftorder, p);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   sel1 = u.select(rgame.w.length, percard);
//startPR2008-10-22^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//   sel2 = u.select(rgame.w.length, percard);
//   for (i = 0; i < rgame.w.length; ++i) {
//       if (!u.inlist(sel1, i) && !u.inlist(sel2, i)) {
//         sel2 = u.select(rgame.w.length, percard);
//         i = -1;
//       }
//   }
   // make sure all words are used
   sel2 = selectwords(sel1, percard);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   order = u.shuffle(sel1);
//startPR2008-08-29^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   noofrows = rows;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2008-08-29^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  leftbingo.zoomrect2 = new Rectangle(leftbingo.x+leftbingo.w/10,
                                         leftbingo.y+leftbingo.w/10,
                                         leftbingo.w*7/10,
                                         leftbingo.h*7/10);
  rightbingo.zoomrect2 = new Rectangle(rightbingo.x+rightbingo.w/10,
                                         rightbingo.y+rightbingo.w/10,
                                         rightbingo.w*7/10,
                                         rightbingo.h*7/10);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   rowdepth = leftbingo.h * 5 / 6 / (rows+1);
   itemwidth = leftbingo.w/(extralist.length>0 ? (perRow+1) : perRow);
   bingopicy = leftbingo.y+leftbingo.h/12+rowdepth/2;
   for(i=0,k = 0;k<percard;++i){//i IS THE ROW COUNTER, k THE BUTTON COUNTER AND j THE COLUMN COUNTER
      rowtot = perRow;
      for(j=0;j<extralist.length;++j) {
          if(extralist[j]==i) {++rowtot; break;}
      }
      for(j=0;j<rowtot;++j, ++k) {
        b[k] = new wordButton(order[k]);
        b[k].w = itemwidth;
        b[k].h = rowdepth;
        b[k].x = leftbingo.x + j*leftbingo.w / rowtot + leftbingo.w / rowtot /2 - itemwidth/2;
        b[k].y = leftbingo.y +leftbingo.h/12+ (i+1)*rowdepth;
//startPR2008-10-11^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        b[k].inright = false;
        b[k].totinrow = rowtot;
        b[k].currcolumn = j;
        b[k].currrow = i;
        zoommovers_left = u.addmover(zoommovers_left, b[k]);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        b[k].bWord = rgame.w[order[k]];
        b[k].bingocard = leftbingo;
      }
   }
   order = u.shuffle(sel2);
//startPR2008-10-22^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   compdummyqs = new int[]{};
   for(int p = 0; p < rgame.w.length; p++){
     if(!u.inlist(order,p))
       compdummyqs = u.addint(compdummyqs,p);
   }
   dorightorder();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   for(i=0;k<percard*2;++i){//i IS THE ROW COUNTER, k THE BUTTON COUNTER AND j THE COLUMN COUNTER
      rowtot = perRow;
      for(j=0;j<extralist.length;++j) {
          if(extralist[j]==i) {++rowtot; break;}
      }
      for(j=0;j<rowtot;++j, ++k) {
         b[k] = new wordButton(order[k-percard]);
         b[k].w = itemwidth;
         b[k].h = rowdepth;
         b[k].x = rightbingo.x + j*rightbingo.w / rowtot  + rightbingo.w / rowtot /2 - itemwidth/2;
         b[k].y = rightbingo.y  + rightbingo.h/12+ (i+1)*rowdepth;
//startPR2008-10-11^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         b[k].inright = true;
         b[k].totinrow = rowtot;
         b[k].currcolumn = j;
         b[k].currrow = i;
         zoommovers_right = u.addmover(zoommovers_right, b[k]);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         b[k].bWord = rgame.w[order[k-percard]];
         b[k].bingocard = rightbingo;
      }
   }
   sizeFont();
   for(i=0;i<btot;++i) {
         int w = b[i].w;
         int h = b[i].h;
         if(!findpicture) {
             b[i].h = h * 3 / 4;
//           b[i].w = Math.min(w, buttonWidth);
//           b[i].h = Math.min(h, buttonHeight);
         }
         else {
           b[i].pic.w = w * 3 / 4;
           b[i].pic.h = h * 3 / 4;
         }
         int xPosition = b[i].x + w/2 - b[i].w/2;
         int yPosition = b[i].y + h/2 - b[i].h/2;
//startPR2008-10-24^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         b[i].zoomrect1 = new Rectangle(xPosition,yPosition,b[i].w,b[i].h);
         bingocard tc = b[i].inright?rightbingo:leftbingo;
         int rowdepth2 = tc.zoomrect2.height * 5 / 6 / (rows+1);
         int w1 = tc.zoomrect2.width/(extralist.length>0 ? (perRow+1) : perRow);
         int h1;
         if(!findpicture) {
             h1=  b[i].zoomrect1.height *7/10;
//           Font wf = wordfont.deriveFont(wordfont.getSize()*(float)tc.zoomrect2.height/(float)tc.zoomrect1.height);
//           h1 =  Math.min(rowdepth2, getFontMetrics(wf).getHeight()* mover.HEIGHT/screenheight * 3);
         }
         else{
           h1 = rowdepth2;
         }
         int thisx = tc.zoomrect2.x + b[i].currcolumn*tc.zoomrect2.width / b[i].totinrow + tc.zoomrect2.width  / b[i].totinrow /2 - w1/2;
         int thisy = tc.zoomrect2.y  + tc.zoomrect2.height/12+(b[i].currrow+1)*rowdepth2;
         thisy = thisy + rowdepth2/2 - h1/2;
         b[i].zoomrect2 = new Rectangle(thisx,thisy,w1,h1);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         addMover(b[i], xPosition, yPosition);
//startSS^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
         int switchx = xPosition + b[i].w/2;
         int switchy = yPosition + b[i].h/2;
         pointSwitch2.add(new Point(switchx, switchy));
//endSS^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
   }
//startPR2008-10-11^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   setFaded();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
 }
  //-------------------------------------------------------------       //       v5 rb 19/3/08 end
  public void sizeFont() {
      wordfont = null;
      String s;
      if(sharkStartFrame.currPlayTopic.phrases && findpicture ) {       //start  rb 14/1/08
        int ww = mover.WIDTH*2/3;
        for(short i=0;i<btot;++i){
           s = ((rgame.options & word.SPLIT)!=0)?rgame.w[b[i].wordnumber].vsplit():rgame.w[b[i].wordnumber].v();
           wordfont = sizeFont(wordfont,s, ww,  b[i].h*2/3);
        }
      }
      else {
        if (phonics && !phonicsw)
          wordfont = sizeFontbig(new String[] {rgame.w[b[0].wordnumber].vsplit()}
                                 , b[0].w * screenwidth / mover.WIDTH * 2 / 3, b[0].h / 4 * screenheight / mover.HEIGHT);
        for (short i = 0; i < btot; ++i) {
          s = ( (rgame.options & word.SPLIT) != 0) ? rgame.w[b[i].wordnumber].vsplit() : rgame.w[b[i].wordnumber].v();
          wordfont = sizeFont(wordfont, s, b[i].w * 2 / 3, learning?b[i].h/4: b[i].h*2/3);
        }
      }                                                                  //end  rb 14/1/08
      wordWidth  = maxWordWidth(wordfont);
      wordHeight = metrics.getHeight();
      buttonWidth = wordWidth * mover.WIDTH/screenwidth * 2;
      buttonHeight = wordHeight * mover.HEIGHT/screenheight * 3;
  }
  //---------------------------------------------------------------------------
//startPR2008-08-29^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  class speaker extends mover {
//startPR2008-09-23^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    long lag = 400;
//  long killspeaker=-1;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    sharkImage pic = new sharkImage("speaker", false);
    
    speaker(int x, int y, int width, int height) {
      w = width;
      h = height;
      addMover(this,x,y);

    }
    public void paint(Graphics g,int x1, int y1, int w1, int h1) {
//startPR2008-09-23^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      if(killspeaker<0&&spokenWord.isfree()){
//        killspeaker = gtime+lag;
//      }
//      if(killspeaker>0&&gtime>killspeaker){
//        killspeaker = -1;
//        kill=true;return;
//      }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR

      ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
      pic.paint(g,x1,y1,w1,h1);
      if(!specialprivate){
          Font currf = g.getFont();
          g.setFont(speakerfont);
          int sw = g.getFontMetrics().stringWidth(currword.v());
          g.setColor(Color.gray.darker());
          g.drawString(currword.v(), x1+(w1-sw)/2, y1+h1-(h1/14));
          g.setFont(currf);
      }
      
    }
//startPR2008-09-23^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    public void mouseClicked(int x, int y) {
//startPR2008-10-22^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      if(inright || !pic.covers(x,y))return;
      if(inright&&!gamescore2 || !pic.covers(x,y))return;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      if(wantphonicsw) spokenWord.sayPhonicsWord(currword,500,false,false,!singlesound);
//startPR2008-10-22^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      else rgame.w[curr].say();
      else rgame.w[getcurr()].say();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    };
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   class showword extends mover {
     public int wordnumber;
//startPR2004-11-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     public word bWord;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     Image speakerim;
     int lastw = -1;

     public showword(int num,int x, int y) {
       super(false);
       keepMoving = true;
       wordnumber = num;

       w = Math.max(mover .WIDTH/6, metrics.stringWidth(rgame.w[inright?rightorder[wordnumber]:(bingo?leftorder[wordnumber]:wordnumber)].v())*5/4*mover.WIDTH/screenwidth);
       h = metrics.getHeight()*2*mover.HEIGHT/screenheight;
       if(specialprivateon)
           speakerim = Toolkit.getDefaultToolkit().getImage(sharkStartFrame.publicPathplus + "sprites" +
                                           sharkStartFrame.separator +
                                           "speakerON_il48.png");
       addMover(this,x-w/2,y - h/2);
     }
//startPR2008-08-29^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     public boolean isOver(int mx,int my) {
       return new Rectangle(x,y,w,h).contains(mx*mover.WIDTH/screenwidth,my*mover.HEIGHT/screenheight);
     }
     public void mouseClicked(int x, int y) {
         if(listen ){
               if(wantphonicsw) spokenWord.sayPhonicsWord(currword,500,false,false,!singlesound);
               else currword.say();
         }
     }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     public void paint(Graphics g, int x, int y, int w, int h) {
         if(shark.macOS) ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
       Rectangle r = new Rectangle(x, y, w, h);
//startPR2008-08-29^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        if(!bingo && listen && isOver(gamePanel.mousexs,gamePanel.mouseys)){
          g.setColor(new Color(255,255,150));//lighter yellow
        }
        else
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          g.setColor(Color.yellow);
       g.fillRect(r.x ,r.y, r.width, r.height);
      if(listen) u.buttonBorder(g,r,Color.yellow, !mouseDown);
       g.setFont(wordfont);
       g.setColor(Color.black);
//startPR2008-10-22^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//       if(phonicsw) rgame.w[inright?rightorder[wordnumber]:wordnumber].paint(g, r, (byte)(rgame.options & ~word.PHONICSPLIT));
//       else rgame.w[inright?rightorder[wordnumber]:wordnumber].paint(g, r, rgame.options);
       if(specialprivateon){
           int n = r.height*2/3;
           if(r.width!=lastw){
               lastw = r.width;
               speakerim = speakerim.getScaledInstance(n,
                n, Image.SCALE_SMOOTH);
           }
           g.drawImage(speakerim, r.x + ((r.width-n)/2) ,
                  r.y +  ((r.height-n)/2), null);
       }
       else{

        if(phonicsw) rgame.w[inright?rightorder[wordnumber]:(bingo?leftorder[wordnumber]:wordnumber)].paint(g, r, (byte)(rgame.options & ~word.PHONICSPLIT));
        else rgame.w[inright?rightorder[wordnumber]:(bingo?leftorder[wordnumber]:wordnumber)].paint(g, r, rgame.options);
       }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     }
   }
   //---------------------------------------------------------------------------
    class peepwordb extends mover {
      public int wordnumber;
//startPR2004-11-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      public word bWord;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      public peepwordb(int num,int x, int y) {
        super(false);
        keepMoving = true;
        peepat = gtime;
        wordnumber = num;
        w = Math.max(mover .WIDTH/6, metrics.stringWidth(rgame.w[wordnumber].v())*5/4*mover.WIDTH/screenwidth);
        h = metrics.getHeight()*2*mover.HEIGHT/screenheight;
        addMover(this,x-w/2,y - h/2);
      }

      public void paint(Graphics g, int x, int y, int w, int h) {
        if(gtime>peepat+2000) {peepat=0;kill = true;}
        Rectangle r = new Rectangle(x, y, w, h);
        g.setFont(wordfont);
        g.setColor(Color.black);
//startPR2008-10-22^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//        if(phonicsw) rgame.w[inright?rightorder[wordnumber]:wordnumber].paint(g, r, (byte)(rgame.options & ~word.PHONICSPLIT));
//        else rgame.w[inright?rightorder[wordnumber]:wordnumber].paint(g, r, rgame.options);
        if(phonicsw) rgame.w[inright?rightorder[wordnumber]:(bingo?leftorder[wordnumber]:wordnumber)].paint(g, r, (byte)(rgame.options & ~word.PHONICSPLIT));
        else rgame.w[inright?rightorder[wordnumber]:(bingo?leftorder[wordnumber]:wordnumber)].paint(g, r, rgame.options);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      }
    }
   //------------------------------------------------------------------------------------
   void checkrightcard() {       //       v5 rb 19/3/08  start - actions on righthand card
      int i;
      bingocard bc = rightbingo;
      gamePanel.showSprite = false;
//startPR2008-10-08^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      cs = new computersprite();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      int delayforselect = 3000;
//      int delayfornext = 5000;
      int delayforselect = 1700;
      int delayfornext = 3300;
      for(i=0;i<btot;++i) {
            if(b[i].bingocard == bc && !b[i].bingogot && sameword(b[i].wordnumber)) {
               bc.okpassed = true;
               b[i].surroundat = gtime + delayforselect;
//startPR2008-10-08^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//               double a = bingopointer.angle = Math.atan2( (b[i].y+b[i].h/2 - cs.y)*screenheight,  (b[i].x+b[i].w/2 - cs.x)*screenwidth);
//               if(findpicture)
//                 cs.moveto(b[i].x+b[i].w/6 - cs.w/2  - (int)(cs.w/2 * Math.cos(a)),
//                           b[i].y+b[i].h/2 - cs.h/2  - (int)(cs.h/2 * Math.sin(a)),2000);
//               else cs.moveto(b[i].x+b[i].w/2 - metrics.stringWidth(rgame.w[b[i].wordnumber].v())/2*mover.WIDTH/screenwidth - cs.w/2  - (int)(cs.w/2 * Math.cos(a)),
//                         b[i].y+b[i].h/2 - cs.h/2  - (int)(cs.h/2 * Math.sin(a)),2000);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
               break;
           }
      }
      if(!bc.okpassed) {
         bc.passbutton.downat = gtime+delayforselect;
         bc.passbutton.downtime = gtime + delayforselect;
//startPR2008-10-08^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//         double a = bingopointer.angle = Math.atan2(( bc.passbutton.x+bc.passbutton.w/2 - cs.y)*screenheight,
//                                                ( bc.passbutton.y+bc.passbutton.h/2 - cs.x)*screenwidth);
//         cs.moveto(bc.passbutton.x+bc.passbutton.w/2 - cs.w/2 - (int)(cs.w/2 * Math.cos(a)),
//                   bc.passbutton.y+bc.passbutton.h/2  - cs.w/2 - (int)(cs.h/2 * Math.sin(a)),2000);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      }
      cancelword = gtime + delayfornext;
      wantnext = gtime + delayfornext;
   }                                         //       v5 rb 19/3/08  end
  //-------------------------------------------------------------
//startPR2008-10-22^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   void dorightorder(){
     rightorder = u.select(rgame.w.length, rgame.w.length);
     int j = 0;
     for(int i = 0; !gamescore2 && i < makeeasier; i++){
       rightorder = u.addint(rightorder, compdummyqs[j++]);
       if(j>=compdummyqs.length) j=0;
     }
     oloop:while (true) {
       if (rightorder.length < 3)break;
       rightorder = u.shuffle(rightorder);
       for (int i = 0; i < rightorder.length; ++i) {
         if (i == rightorder[i]
             || !startright && i + 1 == rightorder[i] || startright && i == rightorder[i] + 1)
           continue oloop;
       }
       break;
     }
   }

   // adjust when user makes mistake
   // right order perhaps doesn't change
   void dorightorder2(){
     int len = leftorder.length-(curr+1);
     int left[] = new int[len];
     System.arraycopy(leftorder,curr+1,left,0,len);

     len = rightorder.length-curr;
     int right[] = new int[len];
     System.arraycopy(rightorder,curr,right,0,len);
     oloop:while (true) {
       if (rightorder.length < 3)
         break;
       right = u.shuffle(right);
       for (int i = 0; i < left.length; ++i) {
//startPR2008-10-24^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         if (right.length>i+1&&
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
             (left[i] == right[i+1]
             || !startright && left[i] + 1 == right[i+1] || startright && left[i] == right[i+1] + 1))
           continue oloop;
       }
       break;
     }
     System.arraycopy(right,0,rightorder,rightorder.length-len,len);
   }

   // can't change this - buttons already showing - can only change order
   int[] selectwords(int sel1[], int percard){
     int sel2[] = u.select(rgame.w.length, percard);
     for (int i = 0; i < rgame.w.length; ++i) {
       if (!u.inlist(sel1, i) && !u.inlist(sel2, i)) {
         sel2 = u.select(rgame.w.length, percard);
         i = -1;
       }
     }
     return sel2;
   }
   
   
   void shuffleButtons(){
       int ii[] = null;       
       outer:while(true){
            ii = u.select(b.length, b.length);
            ii = u.shuffle(ii);         
            for(int i = 0; i <ii.length; i++){
                if(i==ii[i])continue outer;
            }
            break;
       }
       for(int i = 0; i < b.length; i++){
           b[i].moveto(b[ii[i]].x, b[ii[i]].y, 500);
           
       }
   }
   
   
   
//startPR2008-10-24^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    void mademistake(){
      if(!endturn)return;
      if(inright){
        rightorder = u.addint(rightorder,rightorder[curr]);
      }
      else{
        leftorder = u.addint(leftorder,leftorder[curr]);
      }
      if(!gamescore2) dorightorder2();
    }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   int getcurr(){
     if(inright){
       return rightorder[curr];
     }
//startPR2008-10-24^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     else return leftorder[curr];
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   class wordButton extends mover {
      public int wordnumber;
      sharkImage bingopic;            //       v5 rb 19/3/08
      bingocard bingocard;            //       v5 rb 19/3/08
      boolean bingogot;               //       v5 rb 19/3/08
      long surround,surroundat;       //       v5 rb 19/3/08
      sharkImage pic;                 //       v5 rb 19/3/08
      boolean removed, hadsay;
//startPR2004-11-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      public word bWord;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2008-10-11^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      boolean inright;
      short totinrow;
      short currcolumn;
      short currrow;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      public wordButton(int num) {
        super(false);
        mouseSensitive = true;
        wordnumber = num;
        if(findpicture) {
            
            
            
          if(shark.showPhotos){
            int h = u.getPhotographIndex(rgame.w[wordnumber].vpic());
            if (h>=0){
                  Image im1 = Toolkit.getDefaultToolkit().getImage(sharkStartFrame.publicPhotoNamesFolder
                          + sharkStartFrame.separator + sharkStartFrame.publicPhotoNamesPlusExt[h]); 
                  pic = new sharkImage(im1, rgame.w[wordnumber].vpic());            
            }          
          }
          if(pic == null || ! shark.showPhotos){
            pic = sharkImage.find(rgame.w[wordnumber].vpic());
            if(pic==null)
                pic = new sharkImage(rgame.w[wordnumber].vpic(), false);              
          }
          keepMoving = true;
        }
      }
      public void paint(Graphics g,int x, int y, int w, int h) {
        if(shark.macOS) ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
         Rectangle r = new Rectangle(x,y,w,h);
         if(surroundat != 0 && gtime >= surroundat) {
            surroundat = 0;
            surround = gtime+1000;
         }
         if(shapes) {
            if(invis && !mouseOver) return;
            rgame.options &= ~word.SPLIT;
            if(drawshapes)
                   rgame.options |= word.SHAPE;
            else   rgame.options &= ~word.SHAPE;
         }
         if(!bingogot) {
           if (mouseOver && (!bingo || bingocard.stu != null) && peepat==0
               && (!bingo || !inright && bingocard == leftbingo || inright && gamescore2 && bingocard==rightbingo)
               || surround != 0) {
             g.setColor(buttonColor);
             g.fillRect(r.x, r.y, r.width, r.height);
             u.buttonBorder(g, r, buttonColor, !mouseDown);
             if(surround != 0 && gtime>surround) {
                surround = 0;
                bingogot = true;
              }
           }
//startPR2008-10-13^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
           if(bingo){
             if(bingocard==leftbingo&&(leftbingo.h!=leftbingo.zoomrect1.height)){
               Font wf = wordfont.deriveFont(wordfont.getSize()*((float)leftbingo.h/(float)leftbingo.zoomrect1.height));
               g.setFont(wf);
             }
             else if(bingocard==rightbingo&&(rightbingo.h!=rightbingo.zoomrect1.height)){
               Font wf = wordfont.deriveFont(wordfont.getSize()*((float)rightbingo.h/(float)rightbingo.zoomrect1.height));
               g.setFont(wf);
             }
             else g.setFont(wordfont);
           }
           else
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
             g.setFont(wordfont);
           g.setColor(Color.black);
           if (findpicture) {
               /*
             if(learning) {
                 if(showwords){
                   if(hadsay){
                     g.setColor(Color.black);
                     g.setFont(wordfont);
                     rgame.w[wordnumber].paint(g, new Rectangle(x + w / 8, y + h * 3 / 4, w * 3 / 4, h / 4), rgame.options);
                   }
                   pic.paint(g, x + w / 8, y + h / 8, w * 3 / 4, h *5/8);
                 }
                 else
                   pic.paint(g, x + w / 8, y + h / 8, w * 3 / 4, h *3/4);
               }
               else{
                 if(pic.tifimage==null)
                     pic.paint(g, x + w / 8, y + h / 8, w * 3 / 4, h *3/4);             
                 else {
                     double dw = (double)pic.w/(double)pic.h;
                     double dh = (double)pic.h/(double)pic.w;
                     int imw = ((int)(w*dw));
                     int imh = ((int)(h*dh));
                     
                     int border = Math.min(8,Math.max(1, w / 20));
                     int border2 = border*2;
                     
                     if(imw>w){
                        pic.paint(g, x+border, y+border+((h-imh)/2), w-border2, imh-border2);
                     }
                     else{
                        pic.paint(g, x+border+((w-imw)/2), y+border, imw-border2, h-border2);
                     }
                 }                   
               }
                */
                 int hhh;
                 if(learning && showwords) {
                    if(hadsay){
                        g.setColor(Color.black);
                        g.setFont(wordfont);
                        rgame.w[wordnumber].paint(g, new Rectangle(x + w / 8, y + h * 3 / 4, w * 3 / 4, h / 4), rgame.options);
                    }
                    hhh = h *5/8;
                 }
                 else hhh =h *3/4;
                 if(pic.tifimage==null)
                    pic.paint(g, x + w / 8, y + h / 8, w * 3 / 4, hhh);
                 else {
                    int xx1 = x + w / 8;
                    int yy1 = y + h / 8;
                    int ww1 =  w * 3 / 4;
                    int hh1 = hhh;
                    int ih = pic.tifimage.getHeight(null);
                    int iw = pic.tifimage.getWidth(null);
                    int imw = ww1;
                    int imh = hh1;
                    imw = ((iw*hh1/ih));
                    if(imw > ww1){
                        imw = ww1;
                        imh = ((ih*ww1/iw));
                    }
                    xx1 = xx1+(ww1-imw)/2;
                    yy1 = yy1+(hh1-imh)/2;
                    pic.paint(g,xx1,yy1,imw,imh);
                 }
             }
           else {
             if (phonicsw)
               rgame.w[wordnumber].paint(g, r, (byte) (rgame.options & ~word.PHONICSPLIT));
             else
               rgame.w[wordnumber].paint(g, r, rgame.options);
           }
         }
         else if(bingo && bingogot) {
           if(bingopic==null) {
//startPR2008-10-27^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
             int p=0;
             bingocard tc = inright?rightbingo:leftbingo;
             for(int i=0;i<b.length;i++){
               if (b[i].bingogot && b[i].bingocard==tc) p++;
             }
             tc.setscore(p);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2008-09-23^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//             bingopic = sharkImage.random("bingo_");
                //DO PUBLICIMAGE
                bingopic = new sharkImage("bingo_card", false);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2008-10-14^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                bingopic.recolor(0, buttonColor);
                bingopic.recolor(1, buttonColor.darker());
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
             bingopic.distort = true;
             keepMoving = true;
           }
//startPR2008-09-23^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//           bingopic.paint(g, x+w/8, y+h/8, w*3/4, h*3/4);
              bingopic.paint(g, x, y, w, h);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         }
      }
      public void mouseReleased(int x, int y) {
          clickenddrag = true;
      }
      public void mouseClicked(int x, int y) {
        int i;
        if(learning) {
           if (mouseOver && spokenWord.isfree())  {hadsay = true; showplay = true;defsay(rgame.w[wordnumber]);}
           return;
        }
        if(completed || bingogot || peepat != 0
           || bingo && !gamescore2 && bingocard.stu != sharkStartFrame.studentList[sharkStartFrame.currStudent]
           || bingo && (inright && bingocard != rightbingo || !inright && bingocard != leftbingo) ) return;
        if(currword == null)
          return;
//startPR2004-11-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        pictureat = 0;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        if(sameword(wordnumber)) {
          if(bingo)   {                                      //       v5 rb 19/3/08 start
             bingogot = true;
             bingocard.okpassed = true;
             gamescore(1);
             wantnext = gtime+1000;
//startPR2005-04-18^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            if(Demo_base.isDemo){
              if (Demo_base.demoIsReadyForExit(0)) return;
            }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
             return;
           }
          if(showword != null) {removeMover(showword); showword=null;}
          if(wantphonicsw) {
            spokenWord.sayPhonicsWhole(rgame.w[wordnumber]);
            u.pause((int)(spokenWord.endsay2-gtime));
          }
          lastsay = -1;
          removed = true;
         if(!bingo) {
             int thisx = this.x;
             int thisy = this.y;
            zapp(this, x, y, true);
            if(!completed){
                flip();
            }
            u.pause(1000);     
            if(doDoubleRound == 0){
                clickenddrag = false;
                addMover(this, thisx, thisy);
            }
          }
//startPR2005-04-18^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            if(Demo_base.isDemo){
              if (Demo_base.demoIsReadyForExit(0)) return;
            }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startSS^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
          resetSwitchAccess(this);
//endSS^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
          picturepoint = new Point(this.x,this.y);
          if(bingo) {
             if(leftbingo.gotall()) {win(true);return;}
             else if(rightbingo.gotall()) {win(false);return;}
          }
          ++curr;
          if(!bingo)
              gamescore(1);
          else {
            leftbingo.okpassed = rightbingo.okpassed = false;
            leftbingo.passbutton.used = rightbingo.passbutton.used = false;
          }

          if(curr >= rgame.w.length) {
            
              if(doDoubleRound ==1){
               score(gametot1);
               exit(1000);              
              }
              else if(doDoubleRound == 0){
                  doDoubleRound++;
                  for(int ix=0; ix < b.length; ix++){
                      b[ix].mouseOver = false;
                  }
                  shuffleButtons();
                  u.pause(1000);
                  
                  curr= -1 ;
                  
                  return;
              }
              else{
//             if(findpicture && !phonicsw && !giveword) {   // removed rb 6/4/08
//                giveword = true;
//                givesound = false;
//                curr = -1;
//                setupButtons();
//                setlisten(false);
//             }
//             else {
//               score(gametot1);
//               exit(1000);
              }
//             }
          }
          else {
        //    if(!bingo && delayedflip && !completed){
         //       flip();
        //    }
             shapestart();
             invis = false;
             if(shapes){
               redrawMovers();
             }
             currword = rgame.w[curr];
             if(givesound) {
//startPR2008-08-29^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//               if(wantphonicsw) spokenWord.sayPhonicsWord(currword,500,false,false,!singlesound);
//               else rgame.w[curr].say();
               if(wantphonicsw) spokenWord.sayPhonicsWord(currword,500,false,false,!singlesound);
               else rgame.w[curr].say();
               if(!givepic && !giveword){
//startPR2008-10-08^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//                 speaker = new speaker((inright ? mover.WIDTH * 3 / 4 : mover.WIDTH / 4) - picwidth / 2, bingopicy - rowdepth / 2, picwidth, rowdepth);
                 speaker = new speaker((inright ? mover.WIDTH * 3 / 4 : mover.WIDTH / 4) - picwidth / 2, bingopicy - rowdepth / 2, picwidth, speakerheight);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
               }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
             }
             else if (bingo) noise.plop();
             if(givepic) {              //       v5 rb 19/3/08  start
                if (bingo) {
//                  showpicture ss = new showpicture((inright?mover.WIDTH*3/4 : mover.WIDTH/4)-picwidth/2, bingopicy-rowdepth/2, picwidth, rowdepth);
                  showpicture ss =  new showpicture((inright ? mover.WIDTH * 3 / 4 : mover.WIDTH / 4) - picwidth / 2, bingopicy - rowdepth / 2, picwidth, speakerheight, bingo);
                  if(givesound) ss.stopat = gtime + 6000;
                  else ss.dontremove = true;
               }
                else  if(holeinmiddle) {
                    showpicture ss = new showpicture(mover.WIDTH/2-itemwidth/2, mover.HEIGHT/2-rowdepth/2, itemwidth, rowdepth, bingo);
                    if(givesound) ss.stopat = gtime + 6000;
                    else ss.dontremove = true;
               }
                else   showpicture(1000);
              }                           //       v5 rb 19/3/08 end
             if(giveword)     {
               showword = new showword(curr, mover.WIDTH / 2, bingo ? bingopicy : mover.HEIGHT / 2);
             }
           }
        }
        else {                           // click on wrong word
           invis = false;
           shapestart();
           if(shapes) redrawMovers();
           error(currword.v());
//startPR2008-08-29^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
           if(endturn) 
               wantnext = gtime+1000;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2008-10-22^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
           mademistake();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
           gamescore( -1);
           noise.groan();
//startPR2008-10-22^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          if(bingo)   {
            if(Demo_base.isDemo){
              if (Demo_base.demoIsReadyForExit(0)) return;
            }
           }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        }
     }
  }
  boolean sameword(int num) {
    return (currword.v().equals(rgame.w[num].v())
       || givesound && wantphonicsw && !singlesound && currword.samephonics(rgame.w[num]));
  }
  class bingocard extends mover {
     student stu;
     String head = "?";
     Color fg,bg;
     bingocard thisbingocard = this;
     passbutton passbutton;
     boolean okpassed;
     int lastw1;
     bingocard thiscard = this;
//startPR2008-08-29^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     Color backdarkx1 = background.darker();
     Color backdarkx2 = background.darker().darker();
     public Font bfont;
     FontMetrics bm;
     public boolean l;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2008-09-23^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      short currentscore=0;
      star[] currstars;
//startPR2008-10-14^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      int totcardno = rgame.w.length*2/3;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      double d = 0.6;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR

     bingocard(boolean left) {
//startPR2008-08-29^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       l=left;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       w = mover.WIDTH * 7/16;
       h = mover.HEIGHT * 15/16;
       x = (left?0:mover.WIDTH/2) + mover.WIDTH/4 - w/2;
       y = mover.HEIGHT/2 - h/2;
//startPR2008-08-29^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       zoomrect1 = new Rectangle(x,y,w,h);
       zoomrect2 = new Rectangle(x+w/10,y+w/10,w*7/10,h*7/10);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       bg = u.backgroundcolor(left?new Color[]{background}:new Color[]{background,leftbingo.bg});
       fg = bg.darker();
       addMover(this,x,y);
       if(left) head = (stu=sharkStartFrame.studentList[sharkStartFrame.currStudent]).name;
       else {
         if(gamescore2) head = (stu = otherplayer).name;
         else head = rgame.getParm("computer");
       }
//startPR2008-08-29^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//       passbutton = new passbutton(x, y+h*11/12, w, h/12);
       int wid = w*7/24;
       int wid2 = zoomrect2.width*7/24;
       passbutton = new passbutton(this, x+w/2-wid/2, y+h*11/12, wid, h/12);
       passbutton.zoomrect1 = new Rectangle(x+w/2-wid/2, y+h*11/12, wid, h/12);
       passbutton.zoomrect2 = new Rectangle(zoomrect2.x+zoomrect2.width/2-wid2/2,
                                            zoomrect2.y+zoomrect2.height*11/12,
                                            wid2,
                                            zoomrect2.height/12);
       if(left)
         zoommovers_left = u.addmover(zoommovers_left, passbutton);
       else
         zoommovers_right = u.addmover(zoommovers_right, passbutton);
//startPR2008-09-23^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        currstars = new star[totcardno];
        for (short i = 0; i < currstars.length; i++) {
          currstars[i] = new star(left, i, new Rectangle[]{zoomrect1,zoomrect2});
        }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     }
     public void paint(Graphics g,int x1, int y1, int w1, int h1) {
       int headh = h1/12,i;
       if(!inright && this==leftbingo || inright && this==rightbingo) {
//startPR2008-08-29^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//       g.setColor(Color.red);
         g.setColor(backdarkx1);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         g.fillRoundRect(x1-w1/40,y1-w1/40,w1+w1/20,h1+w1/20,w1/12,h1/12);
         g.setColor(bg);
         g.fillRoundRect(x1,y1,w1,h1,w1/12,h1/12);
       }
       else {
         g.setColor(bg);
         g.fillRoundRect(x1, y1, w1, h1, w1 / 12, h1 / 12);
         g.setColor(fg);
         g.drawRoundRect(x1, y1, w1, h1, w1 / 12, h1 / 12);
       }
       g.setColor(bg.brighter());
//startPR2008-10-08^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       if(speakerheight<0){
           speakerheight = (h * 5 / 6 /  Math.max(3,(noofrows+1)));
               if(speakerfont == null){
                Font f2 = gamePanel.getFont().deriveFont((float)40);
                FontMetrics fm = gamePanel.getFontMetrics(f2);
                int longest = -1;
                String longs = null;
                for(int ii = 0; ii < rgame.w.length; ii++){
                    String s = rgame.w[ii].v();
                    int k;
                    if((k = fm.stringWidth(s))>longest){
                        longest = k;
                        longs = s;
                    }
                }            
                int ww = speakerheight*gamePanel.screenheight/mover.HEIGHT;
                while(fm.stringWidth(longs)>ww){
                    f2 = f2.deriveFont((float)f2.getSize()-1);
                    fm = gamePanel.getFontMetrics(f2);
                }
                speakerfont = f2;   
               }
       }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2008-10-08^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//       g.fillRect(x1,y1+headh,w1,rowdepth*screenheight/mover.HEIGHT);
//       g.fillRect(x1,y1+headh,w1,(h * 5 / 6 / (noofrows+1))*screenheight/mover.HEIGHT);
         g.fillRect(x1,y1+headh,w1,
                    (h * 5 / 6 /   Math.max(3,(noofrows+1)))*screenheight/mover.HEIGHT);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2008-09-23^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        int neww1 = (int)(w1*d);
//       if(bfont == null || bm.stringWidth(head) > w1*2/3 || w1 != lastw1) {
        if(bfont == null || bm.stringWidth(head) > neww1*2/3 || w1 != lastw1) {
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         lastw1 = w1;
//startPR2008-09-23^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//         bfont = sizeFont(new String[] {head}, w1*2/3, headh-4);
          bfont = sizeFont(new String[] {head}, neww1*2/3, headh-4);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         bm = getFontMetrics(bfont);
       }
       g.setFont(bfont);
//startPR2008-08-29^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//       g.setColor(Color.black);
       g.setColor(backdarkx2);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2008-09-23^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//       g.drawString(head,x1 + w1/2 - bm.stringWidth(head)/2, y1+headh/2 - bm.getHeight()/2 + bm.getAscent());
        g.drawString(head,x1 + neww1/2 - bm.stringWidth(head)/2, y1+headh/2 - bm.getHeight()/2 + bm.getAscent());
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//       int underw = Math.max(bm.stringWidth(head),w1/2);
//       g.drawLine(x1+w1/2-underw/2, y1+headh-2, x1+w1/2+underw/2, y1+headh-2);
     }
     boolean gotall() {
        int i;
//startPR2008-09-23^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//        for(i=0;i<btot;++i) if(b[i].bingocard==this && !b[i].bingogot) return false;
//       return true;
         int p=0;
         boolean allgot = true;
          for(i=0;i<btot;++i){
            if(b[i].bingocard==this) {
              if(!b[i].bingogot)
                allgot = false;
              else
                p++;
            }
          }
//startPR2008-10-27^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//          setscore(p);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          return allgot;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     }
//startPR2008-09-23^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      void setscore(int p){
        if(currstars==null)return;
        for(int i = 0; i<p && i < currstars.length; i++){
          if(!currstars[i].score)
            currstars[i].turnon();
        }
      }

      class star extends mover{
        sharkImage im;
        public short no;
        boolean score = false;
        public star(boolean left, short n, Rectangle r[]){
          im = new sharkImage("bingo_score",false);
          im.setControl("normal");
          no = n;
          for(int i = 0; i < r.length; i++){
            int neww = (int) (r[i].width * d);
            int hh = r[i].height/12;
            int margin = hh/20;
            int x1 = r[i].x + neww + margin;
            int w1 = ((r[i].width - neww)-(margin*(totcardno+1)))/totcardno;
            int h1 = hh-(margin*2);
            if(i==0){
              zoomrect1 = new Rectangle(x1 + (no * w1)+(no*margin), r[i].y+margin, w1, h1);
              w=w1;h=h1;
              addMover(this, x1+(no*w1)+(no*margin),r[i].y+margin);
            }
            else
              zoomrect2 = new Rectangle(x1 + (no * w1)+(no*margin), r[i].y+margin, w1, h1);
            if(left) zoommovers_left = u.addmover(zoommovers_left, this);
            else zoommovers_right = u.addmover(zoommovers_right, this);
          }
        }
        public void paint(Graphics g,int x1, int y1, int w1, int h1) {
          ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
          im.paint(g,x1,y1,w1,h1);
        }
        public void turnon(){
          score = true;
          im.setControl("score");
        }
      }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     class passbutton extends mover {
       String text = rgame.getParm("pass");
       Color fg= Color.blue ,bg = Color.lightGray;
       boolean down, used;
       long downtime,downat;
//startPR2008-08-29^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       boolean downerror;
       public Font pfont;
       FontMetrics pm;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       int lastw1;
       Color facecolor = u.backgroundcolor(new Color[]{bg}),eyecolor = u.eyecolor(),mouthcolor=u.mouthcolor();
//startPR2008-08-29^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//       passbutton(int x1, int y1, int w1, int h1) {
         passbutton(mover m, int x1, int y1, int w1, int h1) {
//         w = w1/2;
           w = w1;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
           h = h1;
//startPR2008-08-29^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//          addMover(this,x1+w1/2-w/2,y1);
           addMover(this,x1,y1);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       }
//startPR2008-08-29^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       public boolean isOver(int mx,int my) {
         return new Rectangle(x,y,w,h).contains(mx*mover.WIDTH/screenwidth,my*mover.HEIGHT/screenheight);
       }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       public void mouseClicked(int x, int y) { // click to say 'pass'
         int i;
         if(completed || okpassed || peepat != 0
            || used || !gamescore2 && stu != sharkStartFrame.studentList[sharkStartFrame.currStudent]
            || inright && thiscard != rightbingo || !inright && thiscard != leftbingo) return;
         down = true;
         used = true;
         downtime = gtime + 2000;
         for(i=0; i<btot; ++i) {     // check that curr word is not on card
           if (b[i].bingocard == thisbingocard && !b[i].bingogot && currword.v().equals(b[i].bWord.v())) {
//startPR2008-08-29^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
             downerror = true;
             if(endturn) 
                 wantnext = gtime+1000;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2008-10-24^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
             mademistake();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
             noise.groan();
             gamescore(-1);
             return;
           }
         }
         okpassed = true;
         gamescore(1);
         wantnext = gtime+1000;
       }
       public void paint(Graphics g,int x1, int y1, int w1, int h1) {
         h1-=2;
         if(downat != 0 && gtime > downat) {
             downat = 0;
             down = true;
             downtime = gtime+4000;
         }
//startPR2008-08-29^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//         if(down && gtime > downtime) down = false;
          if(down && gtime > downtime){
            down = downerror = false;
          }
//         g.setColor(down?Color.red:bg);
//startPR2008-09-23^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//          if(down&&downerror) g.setColor(Color.red);
//          else if(!down&&(((inright && thiscard == rightbingo && gamescore2) ||
//                           (!inright && thiscard == leftbingo))&&isOver(gamePanel.mousexs,gamePanel.mouseys)))
            if(down){
              if(downerror)
                  g.setColor(Color.red);
              else
                  g.setColor(Color.green);
            }
//startPR2008-10-14^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//            else if(((inright && thiscard == rightbingo && gamescore2) ||
//                     (!inright && thiscard == leftbingo))&&isOver(gamePanel.mousexs,gamePanel.mouseys))
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//            g.setColor(bg.brighter());
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          else g.setColor(bg);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         g.fillRect(x1,y1,w1,h1);
//startPR2008-08-29^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//         u.buttonBorder(g,new Rectangle(x1,y1,w1,h1),down?Color.red.darker():fg ,!down);
          u.buttonBorder(g, new Rectangle(x1,y1,w1,h1), buttonColor, !down);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         if(pfont == null || pm.stringWidth(head) > w1/2 || w1 != lastw1) {
           lastw1 = w1;
           pfont = sizeFont(new String[] {text}, w1/2, h1*3/4);
           pm = getFontMetrics(pfont);
         }
         g.setFont(pfont);
         g.setColor(Color.black);
//startPR2008-10-11^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//         g.drawString(text,x1 + w1*11/16 - pm.stringWidth(text)/2, y1+h1/2 - pm.getHeight()/2 + pm.getAscent());
//         int ww = h1*3/4;
//         int xx = x1 + w1/8;
//         int yy = y1 + h1/8;
//         int eyew = ww/8;
//         int eyeh = ww/5;
//         int eyex1 = xx+ww/2 - eyew*4/3;
//         int eyex2 =  xx+ww/2 + eyew/3;
//         int eyey = yy + ww/2 - eyeh*5/4;
//         g.setColor(Color.black);
//         ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
//         g.setColor(facecolor);
//         g.fillOval(xx,yy,ww,ww);
//         g.setColor(eyecolor);
//         g.fillOval(eyex1,eyey,eyew,eyeh);
//         g.fillOval(eyex2,eyey,eyew,eyeh);
//         g.setColor(mouthcolor);
//         g.drawArc(xx+ww/4, yy + ww*5/8, ww/2,ww/2, 20,140);
//         ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);
         int margin = w1/10;
        int hh = y1+h1/2 - pm.getHeight()/2 + pm.getAscent();
        int newx2,newx1,arrx,arry,arrw,arrh;
        Polygon arrow;
        if(thiscard == rightbingo){
          newx2 = (x1 + w1 - pm.stringWidth(text) - margin);
          g.drawString(text, newx2, hh);
          newx1 = newx2-margin;
          newx2 = x1 + margin;
        }
        else{
          g.drawString(text, x1 + margin, hh);
          newx2 =  (x1 + w1 - margin);
          newx1 = x1 + (margin*2) + pm.stringWidth(text);
        }
        arrx = newx1;
        arry = y1+margin;
        arrw = newx2-newx1;
        arrh = h1-(2*margin);
        arrow = new Polygon();
        int backofhead = arrx+(int)(arrw * 0.8);
        int yshaft = (arrh - (int) (arrh * 0.65)) / 2;
        int ymid = arrh/2;
        arrow.addPoint(arrx+arrw, arry+ymid);
        arrow.addPoint(backofhead, arry);
        arrow.addPoint(backofhead, arry + ymid - yshaft);
        arrow.addPoint(arrx, arry + ymid - yshaft);
        arrow.addPoint(arrx, arry + ymid + yshaft);
        arrow.addPoint(backofhead, arry + ymid + yshaft);
        arrow.addPoint(backofhead, arry+arrh);
//startPR2008-10-14^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        boolean over = (!down&&((inright && thiscard == rightbingo && gamescore2) ||
                    (!inright && thiscard == leftbingo))&&isOver(gamePanel.mousexs,gamePanel.mouseys));
        g.setColor(over?Color.blue:Color.darkGray);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        g.fillPolygon(arrow);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       }
     }
  }
  class computersprite extends mover {
     computersprite() {
       if(bingopointer==null) bingopointer = sharkImage.random("bingopointer_");
        w = mover.WIDTH/10;
        h = mover.HEIGHT/10;
        addMover(this, gamePanel.mousex, gamePanel.mousey);
     }
     public void paint(Graphics g,int x1, int y1, int w1, int h1) {
        bingopointer.paint(g,x1,y1,w1,h1);
     }
  }
  class play extends mover {
     Color color = Color.lightGray;
     String text = rgame.getParm("play");
     Font f;
     FontMetrics m;
     int lastw1;
     play() {
        w = itemwidth/4;
        h = rowdepth/4;
        x = mover.WIDTH/2 - w/2;
        y = mover.HEIGHT/2 - h/2;
        addMover(this,x,y);
     }
//startPR2008-08-29^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     public boolean isOver(int mx,int my) {
       return new Rectangle(x,y,w,h).contains(mx*mover.WIDTH/screenwidth,my*mover.HEIGHT/screenheight);
     }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     public void paint(Graphics g,int x1, int y1, int w1, int h1) {
        if(!showplay) return;
        if(f == null || lastw1 != w1) {
           f = sizeFont(new String[]{text},w1+3/4,h1*3/4);
           m = getFontMetrics(f);
           lastw1 = w1;
        }
        g.setFont(f);
//startPR2008-08-29^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        if(isOver(gamePanel.mousexs,gamePanel.mouseys))
          g.setColor(color.brighter());
        else
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          g.setColor(color);
        g.fillRect(x1,y1,w1,h1);
        g.setColor(Color.black);
        g.drawString(text,x1+w1/2-m.stringWidth(text)/2,y1+h1/2+m.getAscent()/2);
        u.buttonBorder(g,new Rectangle(x1,y1,w1,h1),color,true);
     }
     public void mouseClicked(int x, int y) { // click to say 'play'
       if(!showplay) return;
//         wantplay = true;
       ended = false;
       u.pause(500);
       gamePanel.currgame.terminate();
     }
  }
}
