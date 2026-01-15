package shark.games;

import java.awt.*;
import shark.*;

public class readingtest extends sharkGame {//SS 03/12/05
  short curr,wordtot;
  int maxlen,maxwidth,wordh;
  slot slots[];
  word words[];
  int epos = mover.HEIGHT;
  int xpos,extra,topsofar;
  short targetscore;
  Font ff,f2;
  FontMetrics ffm,metrics2;
  static final byte MAXSUPERWORDS = 12;
  boolean started,fini;
  sharkImage tick = sharkImage.random("tick_");
  int messpos;
  long exitb;
  mover message1;
  Color donecol;
  long wantnext;
  boolean choosing;
  int MARGIN = mover.WIDTH/30;
  boolean showpicture = !specialprivateon;
  int maxx = 0;

  public readingtest() {
    int i, j;
//    errors = false;
    errors = true;
    gamescore1 = false;
    listen = true;
    curr = 0;
    String helpstring = "help_readingtest"+helpsuff();
    if(specialprivateon){
      if(helpstring.equals("help_readingtest"))
         helpstring = helpstring.concat("def");
    }
    help(helpstring);
    /*
    if (sharkStartFrame.mainFrame.currPlayTopic.superlist) {
      words = sharkStartFrame.mainFrame.currPlayTopic.getsupertestwords(
          MAXSUPERWORDS);
    }
    else
     */
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
    gamePanel.clearWholeScreen = true;
    gamePanel.dontstart = false;
  }
  String helpsuff() {
    if(phonicsw) return  blended?"phb":"";
    else if(phonics) return "ph";
    else return "";
  }
  //--------------------------------------------------------------
  public void afterDraw(long t) {
    if(!started) {
      int i;
      started=true;
      wordfont = sizeFont(words, screenwidth*7/16, screenheight/Math.max(words.length+1,10)*words.length);
      metrics = getFontMetrics(wordfont);
//startPR2007-11-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      f2 = new Font(wordfont.getName(),wordfont.getStyle(),wordfont.getSize()*2/3);
      f2 = wordfont.deriveFont((float)wordfont.getSize()*2/3);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      metrics2 = getFontMetrics(f2);
      for(i=0;i<words.length;++i) {
         maxlen = Math.max(maxlen,words[i].v().length());
         maxwidth = Math.max(maxwidth,metrics.stringWidth(words[i].v()+"  ")*mover.WIDTH/screenwidth);
       }
      if(rgame.topicName.equals(student.optionstring("spelltest_topic"))
                 && student.optionval("readingtest_speed") == speed)
          targetscore = student.optionval("readingtest_score");
      else targetscore = 0;
      int keypadw =  (keypad.keypadname != null) ? (keypad.keypadwidth(this)*mover.WIDTH/screenwidth):0;
      extra = extra*mover.HEIGHT/screenheight;
      int o[] = u.shuffle(u.select((int)wordtot,(int)wordtot));
      for(i=0;i<wordtot;++i) {
        slots[i] = new slot();
        slots[i].val = words[i].v();
        slots[i].yy = topsofar + wordh*i;
        slots[i].xx = xpos;
        new choose(o[i],words[i]);
      }
      nextslot();
      int extra = mover.HEIGHT/64;
      if (phonics && !phonicsw)
        message1 = this.showmessage(rgame.getParm("initmessagep"),
                   mover.WIDTH / 16, mover.HEIGHT * 15 / 16 - extra, mover.WIDTH * 15 / 16, mover.HEIGHT - extra, Color.gray);
      else
        message1 = this.showmessage(rgame.getParm(blended ? "initmessageb" : "initmessage"),
                                    mover.WIDTH / 16, mover.HEIGHT * 15 / 16 - extra, mover.WIDTH * 15 / 16, mover.HEIGHT - extra, Color.gray);
    }
    if(wantnext != 0 && gtime >= wantnext) {
      wantnext = 0;
      if(++curr >= wordtot) {
        endroutine();
      }
      else {
          flip();
          nextslot();
      }
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
    setErrorCounterVisible(true);
    exitbutton(mover.WIDTH*13/16,mover.HEIGHT*3/4);
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

    if(oktot !=wordtot && rgame.topicName.equals(student.optionstring("readingtest_topic"))) {
       int lastscore = student.optionval("readingtest_score");
       if (oktot > lastscore && lastscore >=0) {
          message += "|"+u.edit(rgame.getParm("gotbetter"),String.valueOf(lastscore));
          student.setOption("readingtest_score",(short)oktot);
       }
       else if(oktot == lastscore) {
          message += "|"+rgame.getParm("same");
       }
       else{
          message += "|"+u.edit(rgame.getParm("bestwas"),String.valueOf(lastscore));
       }
    }
    else {
       student.setOption("readingtest_topic",rgame.topicName);
       student.setOption("readingtest_score",(short)oktot);
    }
    showmessage(message, mover.WIDTH*3/4,0,mover.WIDTH,mover.HEIGHT/2);
  }
  public void listen1() {
    if(currword == null) return;
    if(phonicsw) spokenWord.sayPhonicsWhole(currword);
    else if(phonics) spokenWord.sayPhonicsWord(currword,500,false,false,!singlesound);
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
//        (new showpicture(mover.WIDTH * 3 / 4,
//                       mover.HEIGHT * 3 / 4 - extra - mover.HEIGHT / 16)).stopat = gtime() + 4000;
        (new showpicture(maxx + mover.WIDTH / 20,
                       mover.HEIGHT * 3 / 4 - extra - mover.HEIGHT / 16, true)).stopat = gtime() + 4000;
    }
    gamePanel.startrunning();
    gamePanel.requestFocus();
  }
  //-----------------------------------------------------------------
    void nextslot()   {
      int i;
       currword = words[curr];
//startPR2005-04-18^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        if(Demo_base.isDemo){
          if (Demo_base.demoIsReadyForExit(1)) return;
        }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      if( phonicsw) spokenWord.sayPhonicsWhole(currword);
      else if(phonics) spokenWord.sayPhonicsWord(currword,500,false,false,!singlesound);
      else {
        currword.say();
        if(showpicture)
//            new showpicture(mover.WIDTH * 3 / 4,
//                        mover.HEIGHT * 3 / 4 - extra - mover.HEIGHT / 16);
            new showpicture(maxx + mover.WIDTH / 20,
                        mover.HEIGHT * 3 / 4 - extra - mover.HEIGHT / 16, true);
      }
      choosing = true;
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
          if(choosing) return;
          if(shark.macOS) ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
          if(completed && !ok && gtime > exitb+1000) g.setColor(Color.pink);
          else   if(ismoving()) g.setColor(Color.black);
          else   g.setColor(donecol);
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
    //-------------------------------------------------------------------
    class choose extends mover {
      int xx,yy;
       boolean clicked;
       int pos;
       word word;
       String val;

       choose(int pos1, word wd) {
          word = wd;
          val = word.v();
          pos = pos1;
          w =  metrics.stringWidth(val)*mover.WIDTH/screenwidth + MARGIN*2;
          h =  mover.HEIGHT / (wordtot+1);
          addMover(this,mover.WIDTH/2, h*pos);
          maxx =  Math.max(maxx,mover.WIDTH/2 + w);
          mouseSensitive = true;
       }
       public void mouseClicked(int x1, int y1) {
         if(!choosing) return;
         choosing = false;
         if(slots[curr].val.equals(val) || phonics && !singlesound && (!currword.homophone || currword.nohomo()) && word.samephonics(currword)) {
            slots[curr].ok = true;
         }
         else slots[curr].message = val;
         if(message1 != null) {removeMover(message1);message1=null;}
         slots[curr].val = val;
         clicked = true;
         addMover(slots[curr],x+MARGIN,y);
         slots[curr].moveto(slots[curr].xx,slots[curr].yy,1000);
         wantnext = gtime + 2000;
       }
       public void paint(Graphics g, int x1, int y1, int w1, int h1) {
           if(completed) {kill=true; return;}
           if(!choosing && clicked) return;
           if(shark.macOS) ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
           clicked = false;
           if(choosing && mouseOver) {
               g.setColor(Color.red);
               g.drawRect(x1,y1,w1,h1);
           }
           g.setFont(wordfont);
           g.setColor(Color.black);
           g.drawString(val, x1+MARGIN*screenwidth/mover.WIDTH, y1 + h1/2-metrics.getHeight()/2 + metrics.getAscent());
        }
     }
}
