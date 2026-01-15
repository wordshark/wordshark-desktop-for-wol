package shark.games;

import java.awt.*;
import shark.*;
import javax.swing.*;
import java.awt.event.*;


public class spellingtest extends sharkGame {//SS 03/12/05
  short curr,wordtot;
  int maxlen,maxwidth,wordh;
  slot slots[];
  word words[];
  int epos = mover.HEIGHT;
  int xpos,extra,topsofar;
  short targetscore;
  Font ff,f2;
  FontMetrics ffm,metrics2;
  sharkInput input;
  static final byte MAXSUPERWORDS = 12;
  char extrakeys[] = new char[] {(char)keypad.SHIFT,(char)keypad.BACKSPACE,
                                     (char)keypad.ENTER};
  boolean started,fini;
  sharkImage tick = sharkImage.random("tick_");
  int messpos;
  long exitb;
  mover message1;
  Color donecol;
  long wantnext;
  boolean tiles;
  boolean showpicture = !specialprivateon;
  mover testmessage;

  public spellingtest() {
    int i, j;
//    errors = false;
    errors = true;
    gamescore1 = false;
    listen = true;
    curr = 0;
    optionlist = new String[] {"spellingtest_tiles"};
    tiles = options.option("spellingtest_tiles");
    help("help_spellingtest"+helpsuff());
    /*
    if (sharkStartFrame.mainFrame.currPlayTopic.superlist) {
      words = sharkStartFrame.mainFrame.currPlayTopic.getsupertestwords(
          MAXSUPERWORDS);
    }
    else*/
      words = rgame.w;
    wordtot = (short) words.length;
    slots = new slot[wordtot];
    gamePanel.dontstart = true;
    buildTopPanel();
    setErrorCounterVisible(false);
    gamePanel.showSprite = true;
    wordh = (epos - topsofar) / wordtot;
    donecol = Color.gray;
    tick.recolor(0,donecol);
    markoption();
    gamePanel.dontstart = false;
    gamePanel.clearWholeScreen = true;
    if(TopicTest.stage>=0){
        int midpoint = mover.WIDTH / 2;
         testmessage = this.showmessage(rgame.getParm("test_label"),
                                      midpoint,
                                      0,
                                      midpoint + mover.WIDTH * 6 / 16,
                                      mover.HEIGHT * 1 / 16,
                                     Color.gray, Color.white, 20, false);
        addMover(testmessage, midpoint-testmessage.w/2,mover.HEIGHT * 1 / 16);
    }
  }
  String helpsuff() {
    if(tiles) return "tiles";
    if(phonicsw) return  blended?"phb":"";
    else if(phonics) return "ph";
    else return "";
  }
  //--------------------------------------------------------------
  public void restart() {
    super.restart();
    tiles = options.option("spellingtest_tiles");
    markoption();
    curr=0;
    started = false;
    message1 = null;
    wantnext = 0;
    help("help_spellingtest"+helpsuff());
    keypad.deactivate(this);
  }
  //--------------------------------------------------------------
  public void afterDraw(long t) {
    if(!started) {
      int i;
      started=true;
      wordfont = sizeFont(words, screenwidth*7/16, screenheight/Math.max(words.length,10)*words.length);
      metrics = getFontMetrics(wordfont);
//startPR2007-11-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      f2 = new Font(wordfont.getName(),wordfont.getStyle(),wordfont.getSize()*2/3);
      f2 = u.fontFromString(wordfont.getName(),wordfont.getStyle(),(float)wordfont.getSize()*2/3);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      metrics2 = getFontMetrics(f2);
      for(i=0;i<words.length;++i) {
         maxlen = Math.max(maxlen,words[i].v().length());
         maxwidth = Math.max(maxwidth,metrics.stringWidth(words[i].v()+"  ")*mover.WIDTH/screenwidth);
       }
      if(rgame.topicName.equals(student.optionstring("spelltest_topic"))
                 && student.optionval("spellingtest_speed") == speed)
          targetscore = student.optionval("spellingtest_score");
      else targetscore = 0;
      int keypadw =  (keypad.keypadname != null) ? (keypad.keypadwidth(this)*mover.WIDTH/screenwidth):0;
      if(!tiles) {
        xpos = mover.WIDTH / 40;
        if (keypadw > mover.WIDTH / 2) {
          topsofar = 0;
          keypad.activate(this, keypad.addmore(extrakeys, words));
          wordh = (epos - topsofar) / wordtot;
          wordfont = sizeFont(words, screenwidth * 7 / 16, (epos - topsofar) * screenheight / mover.HEIGHT / Math.max(words.length, 10) * words.length);
          metrics = getFontMetrics(wordfont);
        }
        else {
//startPR2010-05-04^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//          keypad.activate(this, keypad.addmore(extrakeys, words));
          keypad.activate(this, keypad.addmore(extrakeys, words), keypad.DEFAULT);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        }
        extra = (keypad.keypadname != null) ? keypad.keypadheight(this) : 0;
      }
      extra = extra*mover.HEIGHT/screenheight;
      for(i=0;i<wordtot;++i) {
        slots[i] = new slot();
        slots[i].yy = topsofar + wordh*i;
        slots[i].xx = xpos;
      }
      nextslot();
      if(!tiles) {
        if (phonics && !phonicsw)
          message1 = this.showmessage(rgame.getParm("initmessagep"),
                                      mover.WIDTH / 16, mover.HEIGHT * 15 / 16 - extra, mover.WIDTH * 15 / 16, mover.HEIGHT - extra, Color.gray);
        else
          message1 = this.showmessage(rgame.getParm(blended ? "initmessageb" : "initmessage"),
                                      mover.WIDTH / 16, mover.HEIGHT * 15 / 16 - extra, mover.WIDTH * 15 / 16, mover.HEIGHT - extra, Color.gray);
      }
    }
    if(wantnext != 0 && gtime >= wantnext) {
      wantnext = 0;
      nextslot();
    }
    if(!fini && exitb != 0 && gtime >exitb+1200) {gamePanel.clearall();fini = true;}
    if(curr>0 &&  !slots[curr-1].ended)
      gamePanel.clearall();
  }
  //--------------------------------------------------------------------
  void endroutine() {
    int i, oktot=0;
    String ok = rgame.getParm("recordok");
    String bad = rgame.getParm("recordbad");
    String message;
    keypad.deactivate(this);
    messpos=0;
//startPR2008-06-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    closepicture();
//endPRimagesandvids^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    exitb = gtime;
    for (i = 0; i < slots.length; ++i) {
      if(!slots[i].ok){
          errorSilent();
          studentrecord.errorList = u.addString(studentrecord.errorList,words[i].v());
      }
      slots[i].w = mover.WIDTH *3/4 - xpos;
      if(!slots[i].ok) {
        slots[i].message = words[i].v() + " ";
      }
      else {score(1); ++oktot;}
      if(sharkStartFrame.mainFrame.currPlayTopic.superlist) {
          
          String testres, source = sharkStartFrame.mainFrame.currPlayTopic.supersource(words[i].v());
         if (!slots[i].ok) {
          slots[i].message2 = '('+source+')';
          testres = u.edit(u.edit(bad,words[i].v(), slots[i].val),source);
        }
        else   testres = u.edit(ok,words[i].v(),source);
        studentrecord.testresults = u.addString(studentrecord.testresults,testres);
      }
      while(metrics.stringWidth(slots[i].message + slots[i].val + "   ") > screenwidth*5/8) {
//startPR2007-11-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//        originalWordfont = wordfont = new Font(wordfont.getName(),wordfont.getStyle(),wordfont.getSize()-1);
        originalWordfont = wordfont = wordfont.deriveFont((float)wordfont.getSize()-1);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        metrics = getFontMetrics(wordfont);
      }
    }




    for (i = 0; i < slots.length; ++i) {
      messpos = Math.max(messpos, metrics.stringWidth(slots[i].val));
    }
    if(oktot<3) cancelreward=true;
    if(oktot == wordtot) {
      score(2);
      message = u.edit(rgame.getParm("gotall"), String.valueOf(oktot), String.valueOf(oktot));
    }
    else if(oktot==wordtot - 1) {
      message = u.edit(rgame.getParm("good"), String.valueOf(oktot), String.valueOf(wordtot));
    }
    else {
      message = u.edit(rgame.getParm("got"), String.valueOf(oktot), String.valueOf(wordtot));
    }

    if(oktot !=wordtot && rgame.topicName.equals(student.optionstring("spellingtest_topic"))) {
       int lastscore = student.optionval("spellingtest_score");
       if (oktot > lastscore && lastscore >=0) {
          message += "|"+u.edit(rgame.getParm("gotbetter"),String.valueOf(lastscore));
          student.setOption("spellingtest_score",(short)oktot);
       }
       else if(oktot == lastscore) {
          message += "|"+rgame.getParm("same");
       }
       else{
          message += "|"+u.edit(rgame.getParm("bestwas"),String.valueOf(lastscore));
       }
    }
    else {
       student.setOption("spellingtest_topic",rgame.topicName);
       student.setOption("spellingtest_score",(short)oktot);
    }

    setErrorCounterVisible(true);
    showmessage(message, mover.WIDTH*3/4,0,mover.WIDTH,mover.HEIGHT/2);
    if(TopicTest.stage<0){
        exitbutton(mover.WIDTH*13/16,mover.HEIGHT*3/4);
    }
    else{
        removeMover(testmessage);
        testmessage = null;
        TopicTest.passed = studentrecord.errorList==null || studentrecord.errorList.length <= 2;
        if(TopicTest.stage==0){
            TopicTest.path = TopicTest.passed?TopicTest.PATH_HARD:TopicTest.PATH_EASY;
        }
        TopicTest.reachedEnd =  (!TopicTest.passed && TopicTest.stage>0) || !(TopicTest.stage < (TopicTest.path==TopicTest.PATH_EASY?TopicTest.EASYSTAGEMAX:TopicTest.HARDSTAGEMAX));
        TopicTest.stage++;
        javax.swing.Timer hTimer = new javax.swing.Timer(1000, new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                if(!TopicTest.reachedEnd){
                    String welldone[] = db.list(sharkStartFrame.publicTestSayLib, db.WAV, "welldone");
                    if (welldone != null && welldone.length > 0) {
                        spokenWord.findandsay(welldone[u.rand(welldone.length)], sharkStartFrame.publicTestSayLib);
                    }
                }
                else{
                    JButton jb1 = new JButton(u.gettext("OK", "label"));
                    jb1.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            TopicTest.listenMessage.dispose();
                        }
                    });
                    TopicTest tt = new TopicTest();
                    String ss = tt.getResultTopic();
                    if(ss==null)ss = "";
                    TopicTest.listenMessage = new ListenDialog(
                            u.gettext("topictest", "messageboxtitle"), 
                            u.gettext("topictest", "mess_finished", ss), 
                            sharkStartFrame.publicTestSayLib, 
                            new String[]{"mess_finished", ss,  "mess_finished2"},
                            1200,
                            gamePanel.currgame, 
                            0, 
                            new JButton[]{jb1});  
                    TopicTest.listenMessage.setVisible(true);
                    TopicTest.listenMessage = null;
                    gamePanel.currgame.terminate();
                    return;
                }
            }
          });
          hTimer.setRepeats(false);
          hTimer.start();
          if(!TopicTest.reachedEnd){
              exitbutton(mover.WIDTH*13/16,mover.HEIGHT*3/4, rgame.getParm("test_next"));
          }
          else {
              completed = true;
          }   
    }
    
  }
  public void listen1() {
    if(currword == null) return;
    if(phonicsw) spokenWord.sayPhonicsWhole(currword);
    else if(phonics) spokenWord.sayPhonicsWord(currword,500,false,false,false);
    else {
      if (currword.homophone || currword.nohomo())
        defsay(currword); // double-request gives definition
      else { // if homophone is present but not used by default then force it
        currword.spokenword = null;
        currword.homophone = true;
        defsay(currword);
        currword.homophone = false;
        currword.spokenword = null;
      }
      if(showpicture)
          (new showpicture(mover.WIDTH * 3 / 4,
                       mover.HEIGHT * 3 / 4 - extra - mover.HEIGHT / 16)).stopat = gtime() + 4000;
    }
    gamePanel.startrunning();
    gamePanel.requestFocus();
  }
  //-----------------------------------------------------------------
    void nextslot()   {
         int i;
         gamePanel.moverwantskey = slots[curr];
         input = slots[curr].input = new sharkInput(new sharkAction() {
         public void enter() {
            String s = u.stripspaces(input.val);
            if(s.length()==0) return;
            if(message1 != null) {removeMover(message1);message1=null;}
            slots[curr].ok = words[curr].v().equals(s);
            slots[curr].w = metrics.stringWidth(input.val)*mover.WIDTH/screenwidth;
            slots[curr].val = s;
            input.ended = true;
            gamePanel.moverwantskey = null;
            slots[curr].input = null;
            slots[curr].moveto(slots[curr].xx, slots[curr].yy,400);
            if(++curr >= wordtot) {
              endroutine();
            }
            else {
                flip();
                wantnext = gtime + 1000;
            }
         }

         public void escape() {
           if(TopicTest.stage>=0){
              exitTopicTest();
               /*
             if(!TopicTest.reachedEnd){
                 JButton jb1 = new JButton(u.gettext("topictest", "bt_exit"));
                 jb1.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        TopicTest.listenMessage.returnValue = ListenDialog.YES;
                        TopicTest.listenMessage.dispose();
                    }
                 });
                 JButton jb2 = new JButton(u.gettext("cancel", "label"));
                 jb2.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        TopicTest.listenMessage.returnValue = ListenDialog.CANCEL;
                        TopicTest.listenMessage.dispose();
                    }
                 });
                 TopicTest.listenMessage = new ListenDialog(u.gettext("topictest", "messageboxtitle"), u.gettext("topictest", "mess_exit"), "publictestsay", "mess_exit",
                         gamePanel.currgame, 0, new JButton[]{jb1, jb2});
                 TopicTest.listenMessage.setVisible(true);
                 if(TopicTest.listenMessage.returnValue == ListenDialog.CANCEL){
                     TopicTest.listenMessage = null;
                     return;
                 }
                 else if(TopicTest.listenMessage.returnValue == ListenDialog.YES){
                     TopicTest.escapeout();
                 }
                 TopicTest.listenMessage = null;
             }
             gamePanel.currgame.terminate(true);
                *
                */
           }
           else
              gamePanel.currgame.terminate();
         }

         public void f1() {
          sharkStartFrame.mainFrame.setwanthelp(!sharkStartFrame.mainFrame.
                                                wanthelp);
//          ToolTipManager.sharedInstance().setEnabled(sharkStartFrame.mainFrame.
//              wanthelp);
          gamePanel.currgame.changehelp();
        }

        public void error() {
        }

        public void end() {
        }

        public void button(byte bno) {}

        public void mouseClick() {
        }
      });
      input.fixyellow(this);
      input.leftjustify = true;
      input.textcolor = Color.black;
      input.maxlen = (short)(maxlen+4);
      input.fixfont = wordfont;
      if(phonics && !phonicsw && (i=words[curr].v().indexOf('-'))>=0) input.slashat = i;
      addMover(slots[curr], mover.WIDTH/2, mover.HEIGHT/2-slots[curr].h/2);
      currword = words[curr];
//startPR2005-04-18^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        if(Demo_base.isDemo){
          if (Demo_base.demoIsReadyForExit(1)) return;
        }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      if(tiles) {
           input.model = currword.v();
           input.dontcompare = true;
           if(phonics && sharkStartFrame.mainFrame.wordTree.extended.isSelected())
             input.setletterlist(sharkStartFrame.currPlayTopic.getAllWords(true),currword,null);
           else input.setletterlist(rgame.w,currword,null);
      }
      if( phonicsw) spokenWord.sayPhonicsWhole(currword);
      else if(phonics) spokenWord.sayPhonicsWord(currword,500,false,false,false);
      else {
        currword.say();
        if(showpicture)
            new showpicture(mover.WIDTH * 3 / 4,
                        mover.HEIGHT * 3 / 4 - extra - mover.HEIGHT / 16);
      }
   }

  //-------------------------------------------------------------------
  class slot extends mover {
    int xx,yy;
     String val = "",message = "",message2;
     boolean ok;
     slot() {
        w =  mover.WIDTH/2;
        h =  wordh;
     }
      public void paint(Graphics g, int x1, int y1, int w1, int h1) {
          if(shark.macOS) ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
         if(input != null) {
           val = input.val;
           input.paint(g,x1,y1,w1,h1,screenheight);
           return;
         }
         if(completed && !ok && gtime > exitb+1000) g.setColor(Color.pink);
         else          g.setColor(donecol);
         g.setFont(wordfont);
         g.drawString(val, x1, y1 + h1/2-metrics.getHeight()/2 + metrics.getAscent());
         if(completed && gtime > exitb+1000) {
           int xx1 = x1 + metrics.stringWidth(val) + h1/4;
           if(ok) tick.paint(g, xx1, y1+h1/8, h1*3/4, h1*3/4);
           if(message != null)   {
             g.setColor(Color.black);
             xx1 = x1+messpos+h1;
             g.drawString(message, xx1,
                          y1 + h1 / 2 - metrics.getHeight() / 2 +
                          metrics.getAscent());

             if(message2 != null)   {
               xx1 = x1 + messpos + h1 + metrics.stringWidth(message);
               g.setFont(f2);
               g.drawString(message2, xx1,
                            y1 + h1 / 2 + metrics.getHeight() / 2 - metrics2.getHeight() +
                            metrics2.getAscent());
             }
           }
         }
      }
   }
   //------------------------------------------------------------------
   class sofar extends mover {
      sofar() {
         super(false);
         w = mover.WIDTH-xpos;
         h = epos - topsofar;
         keepMoving = true;
      }
      public void paint(Graphics g, int x1, int y1, int w1, int h1) {
         int w2 = w1/4;
         int x2 = x1 + w1/8;
         int h2 = h1*3/4,h3;
         int bulbh = h1/8;
         int y2 = y1 + h1/2 - (h2+bulbh)/2;
         int dy = metrics.getAscent()/2;

         short maxscore;
         maxscore = wordtot;
         h3 = h2*gametot1/maxscore;
         g.setColor(Color.red);
         g.fillRoundRect(x1,y2+h2,w1/2,bulbh,w1/8,bulbh/2);
         g.setColor(Color.black);
         g.drawRoundRect(x1,y2+h2,w1/2,bulbh,w1/8,bulbh/2);
         g.setColor(Color.red);
         g.fillRect(x2,y2+h2-h3+4,w2,h3);
         g.setColor(Color.black);
         g.drawLine(x2,y2,x2,y2+h2);
         g.drawLine(x2+w2,y2,x2+w2,y2+h2);
         g.setFont(wordfont);
         if(targetscore > 0) {
            int h4 = h2*targetscore/maxscore;
            g.setColor(Color.black);
            g.drawLine(x2,y2+h2-h4,x2+w2,y2+h2-h4);
            g.setColor(white());
            g.drawString(String.valueOf(targetscore),x1+w1/2,y2+h2-h4+dy);
         }
         if(maxscore != targetscore) {
            g.setColor(Color.black);
            g.drawString(String.valueOf(maxscore),x1+w1/2,y2+dy);
         }
         if(gametot1 != targetscore && gametot1 != maxscore) {
            int h4 = h2*gametot1/maxscore;
            g.setColor(Color.red);
            g.drawString(String.valueOf(gametot1),x1+w1/2,y2+h2-h4+dy);
         }

      }
   }


  public class butsq extends mover {
    String text;
    FontMetrics fm;
    Font f;
    int textwidth;
    float fl;

     butsq(String s, float alpha) {
        super(false);
        fl = alpha;
        text = s;
        w = mover.WIDTH/3;//sidebarw*5/20;
        h = mover.HEIGHT/5;//individualh*5/8;
        textwidth = gamePanel.getFontMetrics(gamePanel.getFont()).stringWidth(s);
        this.handcursor = true;
     }

     public void paint(Graphics g,int x1, int y1, int w1, int h1) {
        Font tempf = g.getFont();
        Graphics2D g2d = ((Graphics2D)g);
        if(fl!=1.0f && !mouseOver)
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, fl));
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setColor(Color.white);
        g.fillRoundRect(x1, y1, w1, h1, 5, 5);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);
        if(fl!=1.0f)
           g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        g.setColor(Color.black);
    //    g.setFont(butFont);
        g.drawString(text, x1+((w1-(textwidth))/2), y1+(h1/2)+(g.getFontMetrics().getAscent()/3));
        g.setFont(tempf);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
     }
  }

}
