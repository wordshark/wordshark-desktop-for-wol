package shark.games;

import java.awt.*;
import java.awt.event.*;

import shark.*;
import shark.sharkGame.*;

public class sayword extends sharkGame {   //SS 03/12/05
  int wtot;
  int ht = mover.HEIGHT/15;
  Font bfont,tfont,sfont;
  FontMetrics bm,tm,sm;
  spokenWord currSpokenWord,childsound,bestsound;
  int curr=-1;
  String titles[] = new String[]{rgame.getParm("yoursound"), rgame.getParm("bestsound"), rgame.getParm("mysound")};
  String stop = rgame.getParm("stop");
  String stu = sharkStartFrame.studentList[sharkStartFrame.currStudent].name;
  String lasttopic = (String)db.find(stu,"best_topic",db.TEXT);
  showsound showsound[] = new showsound[2], currsay[];
  String bestname;
  sharkImage ear = sharkImage.random("listen_");
  int sayw = mover.WIDTH/20;
  againbutton againbutton;
  bestbutton bestbutton;
  goodbutton goodbutton;
  sayitbutton sayitbutton;
  boolean waitendrecord;
  long say2, allowtime,waitforsay;
  mbutton nomike;
  boolean nomik,replace=true,startbutton,nostartmess;
  int gap = mover.WIDTH/60;
  spokenWord savesounds[],bestsounds[],presounds[];
  Font endf;
  FontMetrics endm;
  showword showword;
  sharkImage im;
  int lastlength;
  long lasttime;
  long addsayitbutton;
  String speak = rgame.getParm("speak");
  String speakw = rgame.getParm("speakw");
  String speakp1 = rgame.getParm(blended?"speakp2":"speakp1");
  sharkImage next = sharkImage.random("saywordnext_");
//  sharkImage again = sharkImage.random("i_sayword");
  sharkImage again = sharkImage.random("start_recording");
  sharkImage best = sharkImage.random("saywordbest_");
  sharkImage sayim = sharkImage.random("sayword_say");
//  sharkImage clickendim =  sharkImage.random("saywordclickend_");
  sharkImage clickendim =  sharkImage.random("stop_recording");
  long startrecordat;
  boolean wantnext = true;
  mover initmess;
  boolean saywhole;
  String bests = "best_";
  String sayp1,sayp2;
  mover showingmess;
  sayintomik sayintomik;
  boolean cancelled;
  eggtimer eggtimer;
  static int eggtime = 5000;
  String clickend = rgame.getParm("clickend");
  boolean flonly = rgame.getParm("flonly") != null;
  boolean sayphonics = rgame.getParm("phonics")!= null;
  showpicture currpic;
  boolean sentencegame = rgame.getParm("needsentences3") != null;
  boolean phrasegame = sharkStartFrame.currPlayTopic.phrases;
  int changedgo = -1;
  boolean showpic;

  public sayword() {
    errors = false;
    gamescore1 = false;
    peeps = false;
    listen = true;
    peep = false;
    gamePanel.clearWholeScreen = true;
    if(fl && !sayphonics){
        phonics = phonicsw = false;
    }
    
    showpic = fl || nonsensewords;


    if(lasttopic != null && !rgame.topicName.equalsIgnoreCase(lasttopic)) {
      db.deleteAll(stu,db.WAV,"best_");
      lasttopic=null;
    }
    wtot = (short)rgame.w.length;
    spokenWord.autoboost = true;
    spokenWord.recording = false;
//    nomik = student.option("sayword_nomike");
    nomik = spokenWord.isMicrophoneDefinitelyUnavailable() || student.option("sayword_nomike");
    startbutton = options.option("sayword_startbutton");
     setMessage();
    nostartmess = options.option("sayword_nostartmess");
    if(nomik) optionlist = new String[] {"sayword_nostartmess"};
    else  optionlist = new String[] {"sayword_nostartmess","sayword_startbutton"};
 //   extrabuttons = new mbutton[]{nomike = new mbutton(rgame.getParm(nomik?"usemike":"nomike"))};
    nomike = new mbutton(rgame.getParm("usemike"),mbutton.TYPE_NOMIKE);
    nomike.setOn(nomik);
    extrabuttons = new mbutton[]{nomike};    
    
    replace = sharkStartFrame.mainFrame.usingsuperlist == null;
    
  //  nomike.setForeground(nomik?Color.red:Color.black);
    nomike.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
//startPR2009-04-08^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//   listenButton.setEnabled(true);
        listenButton.setEnabled(false);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        if(childsound != null) childsound.stopRecording();
        waitendrecord = false;
        startrecordat= 0;
        nomik = !nomik;     
        spokenWord.flushspeaker(true);
        if(nomik) optionlist = new String[] {"sayword_nostartmess"};
        else  optionlist = new String[] {"sayword_nostartmess","sayword_startbutton"};

//        nomike.setForeground(nomik?Color.red:Color.black);
        if(nomik) student.setOption("sayword_nomike");
        else   student.clearOption("sayword_nomike");
        nomike.setOn(nomik);
        curr = Math.max(-1,curr-1);
        setMessage();
        setHelp1(rgame.w[curr+1]);
        currSpokenWord = null;
        changedgo = curr+1;
        wantnext = true;
        markoption();
        
      }
    });
    buildTopPanel();
    savesounds = new spokenWord[rgame.w.length];
    bestsounds = new spokenWord[rgame.w.length];
    presounds = new spokenWord[rgame.w.length];
    markoption();
    setHelp1(rgame.w[0]);

//startPR2009-04-08^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    listenButton.setEnabled(false);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
 }
 //--------------------------------------------------------------
  void setHelp1(word w){
        if(phonicsw && !w.onephoneme) {
            if(blended) help(nomik ? "help_sayword1nomic_ph2" :(startbutton ? "help_sayword1_ph2" : "help_sayword1auto_ph2"));
            else help(nomik ? "help_sayword1nomic_ph4" :(startbutton ? "help_sayword1_ph4" : "help_sayword1auto_ph4"));
        }
        else if (phonics && w.onephoneme)    
            help(nomik ? "help_sayword1nomic_ph1" :(startbutton ? "help_sayword1_ph1" : "help_sayword1auto_ph1"));
        else {
            if(sentencegame)
                help(nomik?("help_sayword1nomic_phrase"):(startbutton?("help_sayword1_phrase"):("help_sayword1auto_phrase")));
            else if(phrasegame)
                help(nomik?("help_sayword1nomic_sent"):(startbutton?("help_sayword1_sent"):("help_sayword1auto_sent")));
            else
            help(nomik?(specialprivateon?"help_sayword1nomicdef":"help_sayword1nomic"):(startbutton?(specialprivateon?"help_sayword1def":"help_sayword1"):(specialprivateon?"help_sayword1autodef":"help_sayword1auto")));
        }
  }
  
  void setHelp2(){
      if(nomik)
        help("help_sayword2nomic");
      else
        help("help_sayword2");
  }  
  
  void setMessage(){
      String param;
      if(phonicsw && !rgame.w[0].onephoneme){
          if(blended){
              if(nomik) param = "saythenclickp2";
              else{
                  if(startbutton) param = "saythenclickp2withmic";
                  else param = "saythenclickp2withmicauto";
              }
              sayp2 = rgame.getParm(param);              
          }
          else{
            if(nomik) param = "saythenclickp2w";
            else{
                if(startbutton) param = "saythenclickp2wwithmic";
                else param = "saythenclickp2wwithmicauto";
            }
            sayp2 = rgame.getParm(param);
          }
      }
      else if (phonics && rgame.w[0].onephoneme){
        if(nomik) param = "saythenclick";
        else{
            if(startbutton) param = "saythenclickwithmic";
            else param = "saythenclickwithmicauto";
        }
        sayp2 = rgame.getParm(param);                
      }
      else{
          if(nomik) param = "saythenclick";
          else{
              if(startbutton) param = "saythenclickwithmic";
              else param = "saythenclickwithmicauto";
          }
          sayp2 = rgame.getParm(param);
      }
  }
  
  
 public void restart() {
   if(childsound != null) childsound.stopRecording();
   spokenWord.recording = false;
//startPR2009-04-08^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//   listenButton.setEnabled(true);
    listenButton.setEnabled(false);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   waitendrecord = false;
   cancelled = false;
   startrecordat = 0;
   super.restart();
   curr =-1;
   currSpokenWord = null;
   wantnext = true;
   nostartmess = options.option("sayword_nostartmess");
   startbutton = options.option("sayword_startbutton");
   setMessage();
   markoption();
   setHelp1(rgame.w[0]);
 }
 public void windowclose() {
   if(spokenWord.recording) childsound.stopRecording();
   super.windowclose();
 }
//------------------------------------------------------------------------
  public void afterDraw(long t){
    if(waitendrecord && !childsound.running ) {
      waitendrecord = false;
      startrecordat = 0;
      if(eggtimer != null) {eggtimer.kill = true; eggtimer = null;}
      if(childsound!=null && childsound.data != null && childsound.data.length > 0) {
        if(flonly) addMover(showword,showword.x, ht);
        else showword.moveto(showword.x, ht, 500);
        if(showpic && currpic != null) currpic.moveto(mover.WIDTH*3/4 - currpic.w/2, ht, 500);
        if(showsound[0]!=null){
            showsound[0].saying = true;
            showsound[0].keepMoving = true;
            showsound[0].ended = false;
            showsound[0].startsay = gtime;
        }
        if(!cancelled) childsound.say();
        if(showsound[0]!=null){
            showsound[0].endsay = spokenWord.endsay2;
        }
//        currSpokenWord.gotsofar = currSpokenWord.data.length;
        say2 = spokenWord.endsay2 + 800;
        listenButton.setEnabled(true);
      }
      else if(!startbutton) {
        if(showsound[0]!=null){
            showsound[0].keepMoving = false;
        }
        startrecord();
      }
      else new againbutton();
    }
    else if(startrecordat > 0 && gtime >= startrecordat+1800 && !childsound.running) {
        startrecordat = 0;  
        if(eggtimer != null) {eggtimer.kill = true; eggtimer = null;}
        if(!startbutton){
            switch(u.choose(rgame.getParm("nosound"),rgame.getParm("failed"),
                       new String[]{rgame.getParm("trymike"),rgame.getParm("nomik"),rgame.getParm("quit")},
                       this)) {
               case 0:
                 if(showsound[0]!=null) showsound[0].kill = true;
                 startrecord();
                 break;
               case 1:
                 nomik = true;
                 listenButton.setEnabled(true);
      //           nomike.setForeground(Color.red);
                 student.setOption("sayword_nomike");
      //           nomike.setText(rgame.getParm("usemike"));
                 curr = Math.max(-1,curr-1);
                 currSpokenWord = null;
                 wantnext = true;
                 setHelp1(rgame.w[Math.max(0,curr)]);
                 break;
               case 2:
                 new spokenWord.whenfree(100) {
                   public void action() {
                     terminate();
                   }
                 };
                 break;
               default:
                 new spokenWord.whenfree(100) {
                   public void action() {
                     terminate();
                   }
                 };
                 break;
            }
        }
    }
    if(allowtime != 0 && gtime > allowtime) {
      allowtime = 0;
      if (showsound[1] == null && currSpokenWord != null)
        new showsound(currSpokenWord, 1);
      if (showsound[1] != null) {
        if(flonly) addMover(showword,showword.x, ht);
        else showword.moveto(showword.x, ht, 500);
        if(showpic && currpic != null) currpic.moveto(mover.WIDTH*3/4 - currpic.w/2, ht, 500);
        showsound[1].saying = true;
        showsound[1].keepMoving = true;
        showsound[1].ended = false;
        showsound[1].startsay = gtime;
        if(currSpokenWord!=null)currSpokenWord.say();
        currsay = new showsound[] {showsound[1]};
        showsound[1].endsay = spokenWord.endsay2;
        new spokenWord.whenfree(1000) {
          public void action() {
            if(!(showsound==null || showsound.length<=1 || showsound[1]==null)){
                showsound[1].saying = false;
                showsound[1].ended = false;
                showsound[1].keepMoving = false;
            }
            if ((changedgo == curr || curr == 0)  && !nostartmess)
              spokenWord.findandsay(rgame.getParm("getright"));
            new goodbutton();
            setHelp2();
           }
        };
      }
      else {
        if ((changedgo == curr || curr == 0)  && !nostartmess)
          spokenWord.findandsay(rgame.getParm("getright"));
        new goodbutton();
        setHelp2();
      }
    }
    if(say2 != 0 && gtime > say2) {
      say2 = 0;
      if(showsound[0]!=null){
        showsound[0].saying = false;
        showsound[0].ended = false;
      }
      if (!cancelled && showsound[1] == null) {
        if (bestsound != null) {
          new showsound(bestsound, 1); }
        else   if (currSpokenWord != null)
            new showsound(currSpokenWord, 1);
      }
      if (!cancelled && showsound[1] != null) {
        showsound[1].saying = true;
        showsound[1].ended = false;
        showsound[1].keepMoving = true;
        showsound[1].startsay = gtime;
        
        
        if(initmess != null) {initmess.kill=true;initmess=null;}
        
        
        if(phonicsw && !rgame.w[curr].onephoneme && bestsound == null) {
          spokenWord.sayPhonicsWord(currword, 500, true, true,true);
        }
        else if(phonics && rgame.w[curr].onephoneme && bestsound == null)
            spokenWord.sayPhonicsWord(currword,500,true,true,true);
        else{
            if(showsound[1].sw!=null)showsound[1].sw.say();
        }
        showsound[1].endsay = spokenWord.endsay2;
        new spokenWord.whenfree(500) {
          public void action() {
            if (showsound[1] != null) {
              showsound[1].saying = false;
              showsound[1].ended = false;
              showsound[1].keepMoving = false;
              if (!nomik
                  && (bestsound == null
                      &&
                      !showsound[1].sw.database.equals(sharkStartFrame.studentList[
                  sharkStartFrame.currStudent].name))) {
                showsound[1].kill = true; showsound[1] = null; }
              currsay = new showsound[] {showsound[0], showsound[1]};
              new againbutton();
              if (replace) {
                 new bestbutton();
              }
              new goodbutton();
              setHelp2();
            }
          }
        };
      }
      else {          // no prerecorded or 'best' sound
        currsay = new showsound[] {showsound[0]};
        new againbutton();
        if(!cancelled) {
          if (replace) {
            new bestbutton();
          }
        }
        new goodbutton();
        setHelp2();
      }
    }
    if(wantnext && !completed && (waitforsay == 0 || gtime>waitforsay && spokenWord.isfree())) {
//startPR2006-08-19^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      if(Demo_base.isDemo)
        if (Demo_base.demoIsReadyForExit(1)) return;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         
      if(++curr >= wtot) {
//startPR2009-08-19^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        listenButton.setEnabled(false);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          wantnext = false;
          gamePanel.removeAllMovers();
          exitbutton(mover.HEIGHT / 2 - mover.HEIGHT / 16);
          showmessage(rgame.getParm("clicktohear"), 0,
                      mover.HEIGHT / 2 - mover.HEIGHT / 16, mover.WIDTH * 3 / 8,
                      mover.HEIGHT / 2 + mover.HEIGHT / 16);
          nomike.setVisible(false);
          new endshow(0);
          return;
      }
      
      if(delayedflip && !completed){
         flip();
      }
      bests = "best_";
      currword = rgame.w[curr];
      currword.spokenword = null;
      if(bestsounds!=null)
          bestsounds[curr] = null;
      gamePanel.removeAllMovers();
//startPR2009-04-08^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      listenButton.setEnabled(false);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR  
      setHelp1(rgame.w[curr]);
      im = null;
      showsound[0] = null;
      showsound[1] = null;
      say2 = 0;
      if(currword.spokenword == null) {
        if(phonicsw && !rgame.w[curr].onephoneme) {
           spokenWord.getPhonicsWord(currword,500,true,true,true);
        }
        else if(phonics && rgame.w[curr].onephoneme) {
           spokenWord.getPhonicsWord(currword,500,false,false,true);
        }
        else if(phonics && !phonicsw) spokenWord.getPhonicsWord(currword,500,false,false,true);
        else if(phonics) spokenWord.getPhonicsWhole(currword);
        else {
          spokenWord.getSpokenWord(currword);
          if (currword.spokenword != null && !currword.spokenword.decompressed)    // rb 27/5/08
            currword.spokenword.decomp();
        }
      }
      showword = new showword(curr);
      if(curr>0)
         flip();
      
      
      
      if (changedgo == curr || curr == 0){
          
          initmess = showmessage(sayp2, mover.WIDTH / 3,
                                   mover.HEIGHT * 2 / 3, mover.WIDTH,
                                   mover.HEIGHT, Color.red);
      }
      if(!nomik && (changedgo == curr || curr == 0) && waitforsay == 0 && !startbutton && !nostartmess ) {
          /*
         if(phonicsw && !rgame.w[curr].onephoneme)  {
            spokenWord.findandsay(speakp1);
         }
         else if(phonicsw && rgame.w[curr].onephoneme && !blended)  {
           spokenWord.findandsay(speakw);
         }
         else spokenWord.findandsay(speak);
           * 
           */
         spokenWord.findandsay(sayp2);
          waitforsay = spokenWord.endsay2;
         --curr;
         return;
      }
      waitforsay = 0;
      wantnext = false;
      currSpokenWord = currword.spokenword;




         if((changedgo == curr || curr == 0)  && !nostartmess) {
            spokenWord.findandsay(sayp2);
            addsayitbutton = spokenWord.endsay2 + 1000;
         }
         else addsayitbutton = gtime + 2000;

//if (curr == 0)
 //     showingmess = showmessage(sayp2,0,mover.HEIGHT*19/20,mover.WIDTH, mover.HEIGHT);
      



      /*
       if(!nomik) {
      
            if(phonicsw && !rgame.w[curr].onephoneme)  {
               showingmess = showmessage(speakp1,0,mover.HEIGHT*19/20,mover.WIDTH, mover.HEIGHT);
            }
            if(phonicsw && rgame.w[curr].onephoneme) {
              showingmess = showmessage(blended?speak:speakw,0,mover.HEIGHT*19/20,mover.WIDTH, mover.HEIGHT);
            }

        }
       else{
             if(phonicsw && !rgame.w[curr].onephoneme) {
                   showingmess = this.showmessage(sayp2,0,mover.HEIGHT*19/20,mover.WIDTH, mover.HEIGHT);
             }
             if(phonicsw && rgame.w[curr].onephoneme) {
               showingmess = this.showmessage(rgame.getParm("saywordthenclick"),0,mover.HEIGHT*19/20,mover.WIDTH, mover.HEIGHT);
             }

       }
*/

      if(!nomik) {
//startPR2009-04-08^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      listenButton.setEnabled(false);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        if(currword!=null)
            childsound = new spokenWord(bestname = bests + currword.vsay(), stu, false); // does not actually find the sound
        if (!startbutton)
          startrecord();
        else {
          childsound.notrim = true;
          new againbutton();
//          if (curr == 0)
//            initmess = showmessage(rgame.getParm("clickhere"), mover.WIDTH / 3,
//                                   mover.HEIGHT * 2 / 3, mover.WIDTH,
//                                   mover.HEIGHT, Color.red);
        }
//        if(phonicsw && !rgame.w[curr].onephoneme)  {
//           showingmess = showmessage(speakp1,0,mover.HEIGHT*19/20,mover.WIDTH, mover.HEIGHT);
//        }
//        if(phonicsw && rgame.w[curr].onephoneme) {
//          showingmess = showmessage(blended?speak:speakw,0,mover.HEIGHT*19/20,mover.WIDTH, mover.HEIGHT);
//        }
      }
      else {
//         if(curr==0  && !nostartmess) {
//           if(phonicsw && !rgame.w[curr].onephoneme) {
//              spokenWord.findandsay(sayp2);
//           }
//           else if(phonicsw && rgame.w[curr].onephoneme)
//               spokenWord.findandsay(rgame.getParm("saywordthenclick"));
//           else
//               spokenWord.findandsay(rgame.getParm("saythenclick"));
//           addsayitbutton = spokenWord.endsay2 + 1000;
//         }
//         else addsayitbutton = gtime + 2000;
//         if(phonicsw && !rgame.w[curr].onephoneme) {
//               showingmess = this.showmessage(sayp2,0,mover.HEIGHT*19/20,mover.WIDTH, mover.HEIGHT);
//         }
//         if(phonicsw && rgame.w[curr].onephoneme) {
//           showingmess = this.showmessage(rgame.getParm("saywordthenclick"),0,mover.HEIGHT*19/20,mover.WIDTH, mover.HEIGHT);
//         }
       }
       if(!nomik && lasttopic != null && replace) {
        bestsound = spokenWord.findandreturn(bestname, stu);
        bestsounds[curr] = bestsound;
      }
      else  bestsound = null;
   }
   if(addsayitbutton != 0 && gtime > addsayitbutton) {
       addsayitbutton = 0;
       if(nomik){
          new sayitbutton();
       }
   }
  };
  public void listen1() {
      if(currsay != null ) {
        if(spokenWord.isfree()) {
          currsay[0].saying = true;
          currsay[0].ended = false;
          currsay[0].keepMoving = true;
          currsay[0].ended = false;
          currsay[0].startsay = gtime;
          currsay[0].sw.say();
          currsay[0].endsay = spokenWord.endsay2;
          new spokenWord.whenfree(500) {
             public void action() {
               if(currsay != null) {
                 currsay[0].saying = false;
                 currsay[0].ended = false;
                 currsay[0].keepMoving = false;
//startPR2009-04-08^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//                 if(currsay.length > 1) {
                 if(currsay.length > 1 && currsay[1]!=null){
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                   currsay[1].saying = true;
                   currsay[1].ended = false;
                   currsay[1].keepMoving = true;
                   currsay[1].ended = false;
                   currsay[1].startsay = gtime;
                   currsay[1].sw.say();
                   currsay[1].endsay = spokenWord.endsay2;
                   new spokenWord.whenfree(500) {
                     public void action() {
                       if(currsay != null) {
                         currsay[1].saying = false;
                         currsay[1].ended = false;
                         currsay[1].keepMoving = false;
                       }
                     }
                   };
                 }
               }
             }
          };
        }
      }
  }
  //----------------------------------------------------------------------------------
  void startrecord() {
    lastlength = 0;
    cancelled = false;
    new showsound(childsound,0);
    childsound.skipmess=true;
    childsound.startRecording();
    showsound[0].keepMoving = true;
    showsound[0].ended = false;
    startrecordat = gtime;
    new sayintomik();
    eggtimer = new eggtimer();
  }
  //-----------------------------------------------------------------------------------
//  void addim() {
//    if(im !=null) return;
//    im = sharkImage.find(rgame.w[curr].vpic());
//    if(im != null) {
//      im.w = mover.WIDTH / 4;
//      im.h = mover.HEIGHT / 4;
//      im.adjustSize(screenwidth, screenheight);
//      int ww = showword.width();
//      int pos = mover.WIDTH/2 - ww/2 - im.w/2;
//      addMover(im,mover.WIDTH-im.w,ht);
//      im.moveto(pos + ww,ht,500);
//      showword.moveto(pos - (showword.w - ww)/2, ht, 500);
//    }
//    else showword.moveto(showword.x, ht, 500);
//  }
  //---------------------------------------------------------------------------
                    // shows the word
   class showword extends mover {
     int wordnumber;
     String s;
     Font f;
     FontMetrics m;
     int lastw1;
     boolean showphonics;
     public showword(int num) {
       super(false);
       wordnumber = num;
       w = mover.WIDTH/2;
       h = ht*4;
       if(phonicsw && !rgame.w[curr].onephoneme) {
         showphonics = true;
         s = rgame.w[wordnumber].phsplit();
       }
       else s = rgame.w[wordnumber].v();
       if(!flonly) addMover(this,showpic? mover.WIDTH/4 - w/2 : mover.WIDTH/2-w/2, nomik?mover.HEIGHT/2 - h : ht);
       if(showpic) {
         currpic = new showpicture(flonly?mover.WIDTH/4:mover.WIDTH/2, nomik?mover.HEIGHT/2 - h : ht, mover.WIDTH/2, h);
         currpic.dontremove = true;
       }
     }

     public void paint(Graphics g, int x1, int y1, int w1, int h1) {
         if(shark.macOS) ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
       if(f == null || lastw1 != w1) {
         f = sharkGame.sizeFontbig(new String[]{s}, w1*7/8,showphonics?h1*2/3 : h1);
         m = getFontMetrics(f);
         lastw1 = w1;
       }
       g.setColor(Color.darkGray);
       g.setFont(f);
       if(showphonics) rgame.w[wordnumber].paint(g,new Rectangle(x1,y1,w1,h1),word.PHONICSPLIT+word.CENTRE);
       else g.drawString(s, x1 + w1/2 - m.stringWidth(s)/2,
                   y1 + h1/2 - m.getHeight()/2 + m.getAscent());
     }
     int width() {
       return m.stringWidth(s) * mover.WIDTH/screenwidth;
     }
   }
   //---------------------------------------------------------------------------
                     // shows the sound wave
   class showsound extends mover {
      spokenWord sw;
      int start,end;
      String s;
      boolean saying;
      long startsay,endsay;
      public showsound(spokenWord sw1, int pos) {
        super(false);
        sw = sw1;
        w = mover.WIDTH;
        h = ht*2;
        s = titles[pos];
        showsound[pos] = this;
        if(pos != 1 && nomik) return;
        if(pos==0) keepMoving=true;
        addMover(this,0,nomik ? mover.HEIGHT/2 - h/2 :  mover.HEIGHT/3 + pos*(ht*3));
      }
      public void paint(Graphics g, int x1, int y1, int w1, int h1) {
           if(cancelled) return;
           if(sw.data == null || sw.data.length==0
              || sw.recording && sw.gotsofar < sw.RATE/5
              || sw.running && !sw.recording ) return;
           byte data[];
           if(spokenWord.recording && this == showsound[0])  {
              data = spokenWord.topandtail(sw.data, sw.gotsofar,4,true); // temp
             if(!spokenWord.recording && sw.running) return;
             if(data == null || data.length ==0) {
                if(gtime > startrecordat + eggtime) {
                  if(eggtimer != null) {eggtimer.kill = true; eggtimer = null;}
                  if(childsound!=null)childsound.stopRecording();
                  cancelled = true;
                }
                return;
             }
             if(!startbutton && data.length <= lastlength && gtime > lasttime + 600) {
               if(childsound!=null)childsound.stopRecording();
               waitendrecord = true;
             }
             else if (data.length > lastlength) {
                lastlength = data.length;
                lasttime = gtime;
             }
           }
           else data = sw.data;
           if(data == null || data.length ==0) return;
           int i,x,currx=0,tot = Math.max(sw.RATE, data.length),width=w1,height=h1;
           int y2=y1+h1;
           int x2=x1+w1;
           g.setColor(saying?Color.red:Color.black);
           int showlen = saying && endsay > startsay && gtime < endsay
                           ? (int) (data.length * (gtime-startsay) / (endsay - startsay))
                           : data.length;
           if(showlen < data.length && sw.phonicsend != null) {
              int currpos = -1;
              for(i=0;i < sw.phonicsend.length;++i) {
                 if(showlen < sw.phonicsend[i]) {currpos = i; break;}
              }
              if(rgame.w[showword.wordnumber].curr2 != currpos) {
                rgame.w[showword.wordnumber].curr2 = currpos;
                showword.ended = false;
              }
            }
            else {
              if(rgame.w[showword.wordnumber].curr2 != -1) {
                rgame.w[showword.wordnumber].curr2 = -1;
                showword.ended = false;
              }
            }
            if(spokenWord.recording && this == showsound[0])  {
              short curr,max=0,min=255;
              tot = spokenWord.RATE * 5;  // show 5 sec worth
              showlen = data.length;
              int startfrom = Math.max(0,showlen-tot);
              for(i=startfrom;i<showlen;++i) {
                 x = x1 + (i-startfrom)*width/tot;
                 if(x != currx) {
                    g.drawLine(currx,y2 - max*height/255, currx, y2 - min*height/255);
                    currx = x;
                    max=0;
                    min=255;
                 }
                 curr = (short)(((short)data[i]) & 0x00ff);
                 max = (short)Math.max(max,curr);
                 min = (short)Math.min(min,curr);
               }
               g.drawLine(currx, y2 - max*height/255, currx, y2 - min*height/255);
           }
           else if(tot>=width) {
             short curr,max=0,min=255;
             for(i=0;i<showlen;++i) {
                x = x1 + i*width/tot;
                if(x != currx) {
                   g.drawLine(currx,y2 - max*height/255, currx, y2 - min*height/255);
                   currx = x;
                   max=0;
                   min=255;
                }
                curr = (short)(((short)data[i]) & 0x00ff);
                max = (short)Math.max(max,curr);
                min = (short)Math.min(min,curr);
              }
              g.drawLine(currx, y2 - max*height/255, currx, y2 - min*height/255);
           }
           else {
             int curr = ((short)sw.data[start]) & 0x00ff;
             currx = x1;
             int curry = y2 - curr*height/255,y;
             for(i=start+1;i<end;++i) {
                curr = ((short)sw.data[i]) & 0x00ff;
                x = x1 + (i-start)*width/tot;
                y = y2 - curr*height/255;
                g.drawLine(currx, curry, x, y);
                currx = x;
                curry = y;
              }

           }
       }
    }
     //-------------------------------------------------------
          // button to ask them to say it
       class sayitbutton extends mover {
         String text = new String(rgame.getParm("sayit"));
         public sayitbutton() {
            super(false);
            w = mover.WIDTH/6;
            h = ht*2;
            sayitbutton = this;
     //       addMover(this,mover.WIDTH/2-w/2, ht*12);
            addMover(this,(initmess == null)? mover.WIDTH/2-w/2:gap*6, ht*12);
         }
         public void paint(Graphics g,int x1, int y1, int w1, int h1) {
           if(childsound.recording) {kill=true;return;}
           if(bfont == null || bm.stringWidth(text) > w1*3/4) {
             bfont = sizeFont(new String[]{text},w1*7/8,h1*3/4);
             bm = getFontMetrics(bfont);
           }
           g.setFont(bfont);
           Rectangle r = new Rectangle(x1,y1,w1,h1);
           g.setColor(Color.green);
           g.fillRect(x1,y1,w1,h1);
           g.setColor(Color.black);
           g.drawString(text, x1 + w1/2 - bm.stringWidth(text)/2, y1+h1/2-bm.getHeight()/2 + bm.getAscent());
           u.buttonBorder(g,r,Color.green,!mouseDown);
        }
        public void mouseClicked(int x, int y) {
            if(initmess != null) {initmess.kill=true;initmess=null;}
           kill=true;
           allowtime = gtime ;
//startPR2009-04-08^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
           listenButton.setEnabled(true);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
           gamePanel.clearall();
        }
      }
       //-------------------------------------------------------
            // button for OK word
         class goodbutton extends mover {
           public goodbutton() {
              super(false);
              w = 8*gap;
              h = ht*2;
              goodbutton = this;
              addMover(this,nomik?(mover.WIDTH/2-w/2):gap*46, ht*12);
//              if(showingmess != null) {
//                 removeMover(showingmess);
//                 showingmess = null;
//              }
           }
           public void paint(Graphics g,int x1, int y1, int w1, int h1) {
             if(childsound.recording) {kill=true;return;}
             Rectangle r = new Rectangle(x1,y1,w1,h1);
             g.setColor(Color.green);
             g.fillRect(x1,y1,w1,h1);
             g.setColor(Color.black);
             next.paint(g,x1+w1/8,y1+h1/8,w1*3/4,h1*3/4);
             u.buttonBorder(g,r,Color.green,!mouseDown);
          }
          public void mouseClicked(int x, int y) {
            spokenWord.flushspeaker(true);
            if(!nomik) {
              savesounds[curr] = childsound;
              if(bestsound != null) bestsounds[curr] = bestsound;
            }
            presounds[curr] = currSpokenWord;
            score(1);
            currSpokenWord = null;
            wantnext = true;
          }
        }
      //-------------------------------------------------------
           // button to try again
        class againbutton extends mover {
          public againbutton() {
             super(false);
             w = gap*8;
             h = ht*2;
             againbutton = this;
             addMover(this,gap*6, ht*12);
          }
          public void paint(Graphics g,int x1, int y1, int w1, int h1) {             
              if(spokenWord.recording) {kill=true;return;}
            Rectangle r = new Rectangle(x1,y1,w1,h1);
            g.setColor(Color.lightGray);
            g.fillRect(x1,y1,w1,h1);
            g.setColor(Color.red);
            g.fillOval(x1+(w1/2)-((h1/2)/2),
                    y1+(h1/2)-((h1/2)/2)
                    ,h1/2,h1/2);
            u.buttonBorder(g,r,Color.lightGray,!mouseDown || !spokenWord.isfree());
         }
         public void mouseClicked(int x, int y) {
              if(!spokenWord.isfree()){
                  return;
              }
           spokenWord.flushspeaker(true);
           kill = true;
           if(initmess != null) {initmess.kill=true;initmess=null;}
           say2 = 0;
           if(goodbutton != null)        goodbutton.kill = true;
           if(bestbutton != null)        bestbutton.kill = true;
           if(showsound[0] != null) {
               showsound[0].kill = true;
               showsound[0] = null;
           }
           if (showsound[1] != null && showsound[1].saying) {
               showsound[1].saying = false;
               showsound[1].ended = false;
           }
           startrecord();
           if(startbutton) addMover(new stopbutton(w,h),tox,toy);
          }
       }
       //-------------------------------------------------------
            // button to stop recording
         class stopbutton extends mover {
           boolean done;
           public stopbutton(int ww, int hh) {
              super(false);
              w = ww;
              h = hh;
           }
           public void paint(Graphics g,int x1, int y1, int w1, int h1) {
             Rectangle r = new Rectangle(x1,y1,w1,h1);
            g.setColor(Color.lightGray);
            g.fillRect(x1,y1,w1,h1);
            g.setColor(Color.black);
            g.fillRect(x1+(w1/2)-((h1/2)/2),
                    y1+(h1/2)-((h1/2)/2)
                    ,h1/2,h1/2);
            u.buttonBorder(g,r,Color.lightGray,!mouseDown);
          }
          public void mouseClicked(int x, int y) {
            if(childsound != null) {
              childsound.stopRecording();
              waitendrecord = true;
            }
            kill = true;
           }
        }
       //-------------------------------------------------------
            // button to save as best word
         class bestbutton extends mover {
           boolean done;
           public bestbutton() {
              super(false);
              w = gap*14;
              h = phonicsw && !rgame.w[curr].onephoneme ? ht*3:ht*4;
              bestbutton = this;
              best.setControl("normal");
              addMover(this,gap*26, ht*11);
           }
           public void paint(Graphics g,int x1, int y1, int w1, int h1) {
             Rectangle r = new Rectangle(x1,y1,w1,h1);
//            g.setColor(Color.green);
//            g.fillRect(x1,y1,w1,h1);
             g.setColor(Color.black);
             best.paint(g,x1+w1/8,y1+h1/8,w1*3/4,h1*3/4);
//            u.buttonBorder(g,r,Color.green,!mouseDown);
          }
          public void mouseClicked(int x, int y) {
            if(done) return;
            if(lasttopic == null) {
              db.update(stu, "best_topic", lasttopic = rgame.topicName, db.TEXT);
            }
            childsound.save();  // saves as best_
            savesounds[curr] = childsound;
            if(bestsound != null) bestsounds[curr] = bestsound;
            presounds[curr] = currSpokenWord;
            best.setControl("saved");
            ended = false;
            done = true;
            score(1);                             // go on to next
            currSpokenWord = null;
            wantnext = true;
           }
        }
      class endshow extends mover {
          int pos;
          long start,end;
          boolean addednext;
          spokenWord saying;
          String s;
          endshow(int curr1) {
            pos = curr1;
            int toprow = (rgame.w.length+1)/2;
            h = mover.HEIGHT/2-mover.HEIGHT/16;
            w = mover.WIDTH / (pos>=toprow ? rgame.w.length-toprow : toprow);
            y = (pos<toprow) ?0 : mover.HEIGHT/2+ mover.HEIGHT/16;
            x = w *(pos>=toprow ? pos - toprow  : pos) ;
            pos = curr1;
            start = gtime();
            end = start + 500;
            keepMoving = true;
//            if(phonicsw && rgame.w[pos].onephoneme && (pos & 1) == 1) {
//              if(pos < rgame.w.length-1) { new endshow(pos+1);addednext=true;}
//              return;
//            }
            addMover(this,x,y);
            gamePanel.puttobottom(this);
          }
          public void paint(Graphics g,int x1, int y1, int w1, int h1) {
            g.setColor(Color.red);
            g.drawRect(x1,y1,w1,h1);
            if(endf == null ) {
              if(phonicsw) endf = sizeFont(rgame.w, w1, h1*rgame.w.length/12);
              else endf = sizeFont(rgame.w, w1,h1*rgame.w.length/7);
              endm = getFontMetrics(endf);
            }
            if(gtime() > end) {
             if(keepMoving) {
               keepMoving = false;
               if(pos < rgame.w.length-1 && !addednext) {new endshow(pos+1);addednext=true;}
             }
            }
            g.setFont(endf);
            byte b;
            if(fl && !sayphonics)b = (byte)(word.CENTRE);
            else b = (byte)(rgame.options | word.CENTRE);
            rgame.w[pos].paint(g, new Rectangle(x1,y1+h1*6/7,w1,h1/7), b);
            g.setColor(Color.black);
            if(nomik) {
               if(presounds[pos] != null) paint2(presounds[pos],g,x1,y1+h1/6,w1,h1*3/4);
            }
            else {
              if(bestsounds[pos] != null) {
                paint2(savesounds[pos],g, x1, y1, w1, h1 *2/7);
                paint2(bestsounds[pos], g, x1, y1 + h1 * 4 / 7, w1, h1 * 2 / 7);
              }
              else  if(presounds[pos] != null
                       && presounds[pos].database.equals(sharkStartFrame.studentList[sharkStartFrame.currStudent].name) ) {
                 paint2(savesounds[pos],g, x1, y1, w1, h1 *2/7);
                 paint2(presounds[pos], g, x1, y1 + h1 * 4 / 7, w1, h1 * 2 / 7);
               }
               else paint2(savesounds[pos],g, x1,y1+h1/6,w1,h1*3/4);
            }
         }
         public void paint2(spokenWord sw, Graphics g,int x1, int y1, int w1, int h1) {
           if(sw == null) return;
           byte[] data = sw.data;
           int i,  limit =  saying!=null && saying != sw || gtime() > end ? data.length: (int)(data.length*(gtime()-start)/(end-start));
           for(i=0;i<limit;++i) {
             int tot = data.length,height = h1*7/8,y2=y1+h1*15/16;
             short curr,max=0,min=255;
             int currx=x1,x;
             for(i=0;i<limit;++i) {
                x = x1 + i*w1/tot;
                if(x != currx) {
                   g.drawLine(currx,y2 - max*height/255, currx, y2 - min*height/255);
                   currx = x;
                   max=0;
                   min=255;
                }
                curr = (short)(((short)data[i]) & 0x00ff);
                max = (short)Math.max(max,curr);
                min = (short)Math.min(min,curr);
              }
              g.drawLine(currx, y2 - max*height/255, currx, y2 - min*height/255);
           }
         }
         public void mouseClicked(int xx, int yy) {
           if(nomik) {
              if(presounds[pos] != null) {
                presounds[pos].say();
                saying = presounds[pos];
                start = gtime();
                end = spokenWord.endsay2;
                keepMoving = true;
                gamePanel.copyall = true;
              }
           }
           else {
             if(bestsounds[pos] != null) {
                if(gamePanel.mousey<y+h*2/7 || gamePanel.mousey>y+h*6/7) {
                  saying = savesounds[pos];
                 }
                else {
                   saying = bestsounds[pos];
               }
             }
              else  if(presounds[pos] != null
                       && presounds[pos].database.equals(sharkStartFrame.studentList[sharkStartFrame.currStudent].name) ) {
                  if(gamePanel.mousey<y+h*2/7 || gamePanel.mousey>y+h*6/7) {
                    saying = savesounds[pos];
                  }
                  else {
                    saying = presounds[pos];
                  }
               }
              else  {
                saying = savesounds[pos];
              }
              saying.say();
              start = gtime();
              end = spokenWord.endsay2;
              keepMoving = true;
              gamePanel.copyall = true;
            }
         }
      }
      class sayintomik extends mover {
        sayintomik() {
          w = gap*14;
          h = ht*6;
          sayintomik = this;
          keepMoving = true;
          addMover(this,gap*2, ht*6);
        }
        public void paint(Graphics g,int x1, int y1, int w1, int h1) {
         if(!childsound.recording) {kill=true;return;}
         sayim.paint(g,x1,y1,w1,h1);
        }
      }
      class eggtimer extends mover {
        eggtimer() {
           w = mover.WIDTH/40;
           h = mover.HEIGHT/4;
           keepMoving = true;
           addMover(this,sayintomik.x + sayintomik.w,  sayintomik.y);
        }
        public void paint(Graphics g,int x1, int y1, int w1, int h1) {
           if((gtime-startrecordat) > eggtime) {kill=true;eggtimer = null; return;}
           int my = y1+h1/2;
           int my1 = my - w1/2;
           int my2 = my +w1/2;
           int my11 = y1 + w1/2;
           int my22 = y1+h1 - w1/2;
           int topy =  y1 + (int)(h1/2 * (gtime-startrecordat)/eggtime);
           int boty =  y1 + h1 - (int)(h1/2 * (gtime-startrecordat)/eggtime);
           g.setClip(x1,topy, w1,y1+h1/2 - topy);
           g.setColor(Color.orange);
           g.fillOval(x1,y1,w1,w1);
           g.fillPolygon(new int[]{x1,x1,x1+w1/2,x1+w1,x1+w1},
                         new int[]{my11,my1,my,my1,my11},5);
           g.setClip(x1,boty, w1,y1+h1 - boty);
           g.fillPolygon(new int[]{x1,x1,x1+w1/2,x1+w1,x1+w1},
                         new int[]{my22,my2,my,my2,my22},5);
           g.fillOval(x1,my22-w1/2,w1,w1);
           g.setClip(null);
           Polygon p = u.wavyline(x1+w1/2+2,my, boty -my,Math.PI/2,2,4);
           g.drawPolyline(p.xpoints,p.ypoints,p.npoints);
           g.setColor(Color.black);
           g.drawArc(x1,y1,w1,w1,0,180);
           g.drawPolyline(new int[]{x1,x1,x1+w1/2,x1+w1,x1+w1},
                         new int[]{my11,my1,my,my1,my11},5);
           g.drawPolyline(new int[]{x1,x1,x1+w1/2,x1+w1,x1+w1},
                         new int[]{my22,my2,my,my2,my22},5);
           g.drawArc(x1,my22-w1/2,w1,w1,0,-180);

        }
      }
}
