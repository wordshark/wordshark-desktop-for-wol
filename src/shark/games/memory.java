package shark.games;

import java.awt.*;
import java.awt.event.*;

import shark.*;

public class memory extends sharkGame {//SS 03/12/05
  short curr,wordtot;
  int maxlen,maxwidth,wordh;
  slot slots[];
  short slottot;
  word words[];
  boolean extended,forceend;
  int tpos = mover.HEIGHT*7/8;
  int epos = mover.HEIGHT*3/4;
  int rightpos,extrax,extray,topsofar;
  short percol = 8;
  short targetscore;
  Font sofarfont;
  int timerstart,timercurr,timerend; //timer positions
  long stime,endtime;
  mover startmessage;
  showwords list;
  long stoptime;
  String nolimit = rgame.getParm("nolimit");
  Font ff;
  FontMetrics ffm;
  boolean tiles;
  timer timer;

  public memory() {
    int i,j;
    errors = false;
    gamescore1 = true;
    wantspeed = true;
    curr = 0;
    optionlist = new String[] {"challenge_tiles"};
    tiles = options.option("challenge_tiles");
    if(tiles) help("help_challengetiles");
    else help(blended?"help_challenge1b":"help_challenge1");
//startPR2008-09-05^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    if(rgame.fullwordlist.shuffled && rgame.fullwordlist.canextend) {
    if((rgame.fullwordlist.shuffled ||rgame.fullwordlist.wholeextended) && rgame.fullwordlist.canextend) {
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       extended = true;   // CHECK THIS
//startPR2008-09-05^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//       word tw[] = rgame.fullwordlist.getAllWordsBoth();
       word tw[];
       if(rgame.fullwordlist.shuffled)
         tw = rgame.fullwordlist.getExtendedList();
       else tw = rgame.fullwordlist.getAllWordsBoth();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       short newlen = 0;
       boolean areSingles = false;
       for(i=0;i<tw.length;++i) {
          if(!tw[i].paired)  {areSingles = true;  break;}
       }
       for(i=0;i<tw.length;++i) {
          if(tw[i].value.indexOf('*')<0
              && (areSingles || !tw[i].paired)
                                && tw[i].value.indexOf(' ')<0
                                && tw[i].value.indexOf('(')<0) ++newlen;
       }
       words = new word[newlen];
       for(i=j=0;i<tw.length;++i) {
          if(tw[i].value.indexOf('*')<0
            && (areSingles || !tw[i].paired)
                                && tw[i].value.indexOf(' ')<0
                                && tw[i].value.indexOf('(')<0)
             words[j++] = tw[i];
      }
    }
    else words = u.stripdups(rgame.w);
    wordtot = (short)words.length;
    percol = (short)Math.min(wordtot,percol);
    slots = new slot[wordtot];
    gamePanel.dontstart = true;
    buildTopPanel();
    gamePanel.showSprite = true;
    sofarfont=sizeFont(null,"a",mover.WIDTH,mover.HEIGHT/16);
    wordfont=sizeFont(null,"a",mover.WIDTH,wordh=epos/Math.max(8,Math.min(percol,wordtot)));
    rightpos = mover.WIDTH - getFontMetrics(sofarfont).charWidth('0')*mover.WIDTH/screenwidth*4;
    // rb 12/12/05 -----------------------------------------------------------------
    for(i=0;i<words.length;++i) {
       maxlen = Math.max(maxlen,words[i].v().length()+1);
    }
    maxwidth = (metrics.charWidth('m')*maxlen + metrics.charWidth(' ')*2 )*mover.WIDTH/screenwidth;
    while (maxwidth > mover.WIDTH/3) {
//startPR2007-11-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      wordfont = new Font(wordfont.getName(), wordfont.getStyle(), wordfont.getSize()-1);
      wordfont = wordfont.deriveFont((float)wordfont.getSize()-1);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      metrics = getFontMetrics(wordfont);
      maxwidth = (metrics.charWidth('m')*maxlen + metrics.charWidth(' ')*2 )*mover.WIDTH/screenwidth;
    }
    // rb 12/12/05 -----------------------------------------------------------------
    if(rgame.topicName.equals(student.optionstring("challenge_topic"))
               && student.optionval("challenge_speed") == speed)
        targetscore = student.optionval("challenge_score");
    else targetscore = 0;
    endtime = (stime = gtime()) + tottime();
    timerstart = 0;

//    int keypadw =  (keypad.keypadname != null && !tiles) ? (keypad.keypadwidth(this)*mover.WIDTH/screenwidth):0;
//    if (keypadw>mover.WIDTH*3/4) topsofar = extra;
    addMover(new sofar(),rightpos,topsofar);
//    new sbutton();
    String m = rgame.getParm("howmany1")+" "+String.valueOf(wordtot)+" "+rgame.getParm(tiles?"howmany2t":(blended?"howmany2b":"howmany2"));
    startmessage = showmessage(m, mover.WIDTH/4,mover.HEIGHT/24, rightpos,(mover.HEIGHT/3),Color.black);
    nextslot();
    list = new showwords();
    if(!tiles)keypad.activate(this,keypad.addmore(new char[] {(char)keypad.SHIFT,(char)keypad.BACKSPACE,(char)keypad.ENTER},words));
    extrax = (keypad.keypadname != null && !tiles) ? keypad.keypadwidth(this):0;
    extrax = extrax*mover.WIDTH/screenwidth;
    extray = (keypad.keypadname != null) ? keypad.keypadheight(this):0;
    extray = extray*mover.HEIGHT/screenheight;
    markoption();
    gamePanel.dontstart = false;
  }
  //-----------------------------------------------------------------
  long tottime() {return   Math.min(wordtot,percol)*(2+maxlen/4)*(12 - speed)*500; }
  public void newspeed() {
     targetscore = 0;
     timerstart = timercurr;
     endtime =  gtime + Math.max(0,endtime - gtime) * tottime() / (endtime-startplaytime);
     stime = gtime;
  }
  //--------------------------------------------------------------
  public void restart() {
    super.restart();
    tiles = options.option("challenge_tiles");
    markoption();
    curr=0;
    slots = new slot[wordtot];
    slottot = 0;
    if(tiles) help("help_challengetiles");
    else help(blended?"help_challenge1b":"help_challenge1");

    keypad.deactivate(this);
    curr = 0;
    endtime = (stime = gtime()) + tottime();
    timerstart = 0;

//    int keypadw =  (keypad.keypadname != null && !tiles) ? (keypad.keypadwidth(this)*mover.WIDTH/screenwidth):0;
//    if (keypadw>mover.WIDTH*3/4) topsofar = extra;
    addMover(new sofar(),rightpos,topsofar);
//    new sbutton();
    String m = rgame.getParm("howmany1")+" "+String.valueOf(wordtot)+" "+rgame.getParm(tiles?"howmany2t":(blended?"howmany2b":"howmany2"));
    startmessage = showmessage(m, mover.WIDTH/4,mover.HEIGHT/24, rightpos,(mover.HEIGHT/3),Color.black);
    nextslot();
    list = new showwords();
    if(!tiles) 
//startPR2010-05-04^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//        keypad.activate(this,keypad.addmore(new char[] {(char)keypad.SHIFT,(char)keypad.BACKSPACE,(char)keypad.ENTER},words));
        keypad.activate(this,keypad.addmore(new char[] {(char)keypad.SHIFT,(char)keypad.BACKSPACE,(char)keypad.ENTER},words), keypad.DEFAULT);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    extrax = (keypad.keypadname != null) ? keypad.keypadwidth(this):0;
    extrax = extrax*mover.WIDTH/screenwidth;
    extray = (keypad.keypadname != null) ? keypad.keypadheight(this):0;
    extray = extray*mover.HEIGHT/screenheight;
    gamePanel.dontstart = false;
  }
  //-----------------------------------------------------------------
    void nextslot()   {
       String message = null;
       if(completed) return;
       if(slottot == 1) {
           new sbutton();
       }
       if (slottot == wordtot && slots[slottot-1].val.length()>0 && !forceend) {
           u.pause(2000);
           gamePanel.removeMover(timer);
           noise.verygood();
           message = rgame.getParm("gotall");
           student.setOption("challenge_topic",rgame.topicName);
           student.setOption("challenge_score",slottot);
           student.setOption("challenge_speed",speed);
       }
       else if(speed>1 && gtime > endtime  || forceend) {
          if(slots[slottot-1].val.length()==0 || forceend) {
            if (tiles) slots[slottot-1].input.ended = true;
            removeMover(slots[--slottot]);
            slots[slottot] = null;
            gamePanel.refreshat = gtime+100;
          }
          if(rgame.topicName.equals(student.optionstring("challenge_topic"))
               && student.optionval("challenge_speed") == speed) {
             short lastscore = student.optionval("challenge_score");
             if (slottot > lastscore && lastscore >=0) {
                message = rgame.getParm("gotbetter");
                student.setOption("challenge_score",slottot);
             }
             else if(slottot == lastscore) {
                message = rgame.getParm("same");
             }
             else{
                message = rgame.getParm("bestwas") + " " + String.valueOf(lastscore) + ".";
             }
          }
          else {
             message = "";
             student.setOption("challenge_topic",rgame.topicName);
             student.setOption("challenge_score",slottot);
             student.setOption("challenge_speed",speed);
          }
          if(!extended) {
             short i,j;
             String s="";
             while(slottot < wordtot) {
               loop1: for(i=0; i <words.length;++i) {
                  s = words[i].v();
                  for(j=0; j < slottot;++j) {
                     if(s.equals(slots[j].val)) continue loop1;
                  }
                  break;
               }
               slots[slottot] = new slot();
               slots[slottot].highlight = true;
               slots[slottot].val = s;
               gamePanel.moverwantskey  = null;
               addMover(slots[slottot], mover.WIDTH/32 + (slottot/percol) * maxwidth,
                        (slottot%percol)*wordh);
               ++slottot;
            }
          }
       }
       if(message != null) {        // ended
           message = rgame.getParm("yougot") + " " + String.valueOf(gametot1) + "|" + message;
           if(gametot1 < wordtot && !forceend)  message = rgame.getParm("timeup") + " "+message;
           showmessage(message,0,epos,mover.WIDTH,tpos);
           if(gametot1 < 3) cancelreward = true;
           if(gametot1 < 4) nogoodforsuper = true;
           studentrecord.peepList = new String[]{"No of words: " + String.valueOf(gametot1)};
           keypad.deactivate(this);
           exitbutton(mover.HEIGHT*7/8);
           gamePanel.showSprite = true;
       }
       else  {
           slots[slottot] = new slot();
           int xx = mover.WIDTH/32 + (slottot/percol+1)*maxwidth;
           addMover(slots[slottot], xx + (rightpos-xx-maxwidth)/2,
                mover.HEIGHT/2 - wordh/2);
           ++slottot;
       }
    }
    public void afterDraw(long t) {
       if(speed >1 && !completed && gtime > endtime && (slottot < 1 || slots[slottot-1].val.length()==0)) {
          nextslot();           // force end
       }
    }

  //-------------------------------------------------------------------
  class slot extends mover {
     String val = new String();
     int num;
     long hightime;
     boolean highlight,finished;
     int yy;
     slot() {
        super(false);
        num=slottot;

        if(tiles && !completed) {
           input =  new sharkInput(new sharkAction() {
            public void enter() {
             String s = u.stripspaces(input.val);
             if(s.length()==0) return;
             if( check()) {
                 val = s;
                 input.ended = true;
                 gamePanel.moverwantskey = null;
             }
             gamePanel.refreshat = gtime+100;
           }

            public void escape() {
             gamePanel.currgame.terminate();
            }

            public void f1() {
             sharkStartFrame.mainFrame.setwanthelp(!sharkStartFrame.mainFrame.
                                                 wanthelp);
//             ToolTipManager.sharedInstance().setEnabled(sharkStartFrame.mainFrame.
//               wanthelp);
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
       input.leftjustify = true;
       input.textcolor = Color.black;
       input.maxlen = (short)(maxlen+4);
       input.fixfont = wordfont;
       input.allowy = mover.HEIGHT-tpos;
//startPR2005-04-18^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         if(Demo_base.isDemo){
           if (Demo_base.demoIsReadyForExit(1)) return;
         }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            input.model = "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww".substring(0,maxlen);
            input.dontcompare = true;
            input.setletterlist(rgame.w,rgame.w[0],null);
        }
        gamePanel.moverwantskey = this;
        keepMoving = true;
        w =  maxwidth;
        h =  wordh;
        keepMoving = true;
     }
                    //------------------------------------
     public void keyhit(KeyEvent e) {
        char key = (char)e.getKeyChar();
        int code = e.getKeyCode();
        int i;
        if(list != null) {
           startplaytime = stime = gtime;
           removeMover(list);
           list=null;
           if(tiles) help("help_challengetiles2");
           else help(blended?"help_challenge2b":"help_challenge2");
           addMover(new timer(),0,tpos);
           gamePanel.copyall = true;
        }
        if(code == KeyEvent.VK_ESCAPE)
                    {gamePanel.currgame.terminate();  return;}
        else if(code == KeyEvent.VK_ENTER) {
          check();
        }
        else if(code == KeyEvent.VK_BACK_SPACE) {
           if(val.length() > 0) {
              val = val.substring(0,val.length()-1);
//              check();
           }
        }
        else if(code == KeyEvent.VK_LEFT) {
           if(val.length() > 0) {
              val = val.substring(0,val.length()-1);
//              check();
           }
        }
        else if(code == KeyEvent.VK_RIGHT) { // not used
             noise.beep();
        }
//startPR2006-04-06^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        else if(code == KeyEvent.VK_DELETE) {
          return;
        }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        else if(Character.isDefined(key)) {
           if(val.length() == maxlen) noise.beep();
           else {
              val = val + key;
//              check();
           }
        }
      }
      boolean check()  {
        boolean already = false;
        short i;
        for(i=0; i < slottot-1;++i) {
           if(slots[i].val.equals(val)) {
              slots[i].highlight = true;
              already = true;
           }
           else slots[i].highlight = false;
        }
        if(!already)  for(i=0;i<wordtot;++i) {
           if(words[i].v().equals(val)) {
               gamePanel.moverwantskey = null;
               gamescore(1);
               score(1);
               words[i].say();
               finished = true;
               moveto( mover.WIDTH/32 + (num/percol) * maxwidth,
                        (num%percol)*wordh, 2000);
//startPR2005-04-18^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                if(Demo_base.isDemo){
                  if (Demo_base.demoIsReadyForExit(0)) return true;
                }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
               nextslot();
               if(startmessage!=null) {
                  removeMover(startmessage);
                  startmessage = null;
                  gamePanel.showSprite = true;
               }
               gamePanel.bringtotop(this);
            if(delayedflip && !completed){
               flip();
            }
               return true;
           }
        }
        noise.groan();
        return false;
      }
      public void paint(Graphics g, int x1, int y1, int w1, int h1) {
          if(shark.macOS) ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        if(input != null && !finished && !completed) {
           val = input.val;
           if(val.length()>0 && list != null) {
             startplaytime = stime = gtime;
             removeMover(list);
             list=null;
             if(tiles) help("help_challengetiles2");
             else help(blended?"help_challenge2b":"help_challenge2");
             addMover(new timer(),0,tpos);
             gamePanel.puttobottom(timer);
             gamePanel.copyall = true;
          }
           input.paint(g,x1,y1,w1,h1,screenheight);
           return;
         }
         if(highlight) {
             g.setColor(Color.red);
             g.fillRect(x1,y1,w1,h1);
             g.setColor(gamePanel.getBackground());
             g.drawRect(x1,y1,w1,h1);
         }
         else if(!finished) {
             g.setColor(yellow());
             g.fillRect(x1,y1,w1,h1);
         }
         if(highlight) g.setColor(white());
         else          g.setColor(Color.black);
         g.setFont(wordfont);
         if(!finished && val.indexOf(' ')>=0) {
            int x2 = x1+metrics.charWidth(' ');
            int y2 = y1 + h1/2-metrics.getHeight()/2 + metrics.getAscent();
            for(short i = 0;i<val.length();++i) {
               g.drawString(val.substring(i,i+1), x2,y2);
               x2 += metrics.charWidth(val.charAt(i));
            }
            if(val.length()<maxlen) drawcursor(g,x2,y1,metrics.charWidth('m'),h1);
         }
         else if(completed)
            g.drawString(val, x1+metrics.charWidth(' '), y1 + h1/2-metrics.getHeight()/2 + metrics.getAscent());
         else {
            g.drawString(val, x1+metrics.charWidth(' '), y1 + h1/2-metrics.getHeight()/2 + metrics.getAscent());
            drawcursor(g, x1+metrics.charWidth(' ') + metrics.stringWidth(val),y1,metrics.charWidth('m'),h1);
         }
      }
      void drawcursor(Graphics g, int x1,int y1,int w1,int h1) {
        if(val.length()<maxlen && !finished) {
         g.setColor(Color.red);
         g.drawRect(x1,y1,w1,h1);
        }
      }
   }
   //------------------------------------------------------------------
   class timer extends mover {
      sharkImage im1;
      Rectangle clockr;
      timer() {
         super(false);
         im1 = sharkImage.random("clock_");
         im1.h = mover.HEIGHT-tpos;
         im1.w = mover.WIDTH;
         clockr = im1.getRect(-1);
         im1.adjustSize(screenwidth,screenheight);
         w = mover.WIDTH - extrax;
         h = mover.HEIGHT-tpos;
         keepMoving = true;
         timer = this;
         timerend = mover.WIDTH - im1.w - extrax;
      }
      public void paint(Graphics g, int x1, int y1, int w1, int h1) {
         long maxtime = endtime - stime;
         long currtime = stime-startplaytime + Math.min(gtime - stime, maxtime);
         double hour = -Math.PI/2 + Math.PI*2*currtime/maxtime;
         double minute = -Math.PI/2 + Math.PI*24*currtime/maxtime;
         int x2, w2 = im1.w*screenwidth/mover.WIDTH,h2 = im1.h*screenheight/mover.HEIGHT;
         if(speed <= 1) {
           if(ff==null) {
             ff = sizeFont(new String[] {nolimit}, w1, h1);
             ffm = getFontMetrics(ff);
           }
           g.setColor(Color.black);
           g.drawString(nolimit,x1,y1+ffm.getAscent());
           return;
         }
         if(clockr == null) {
            im1.paint(g,x1,y1,h2,w2);
            clockr = im1.getRect(-1);
            clockr.x -= x1;
            x2 = 0;
         }
         else {
            if(forceend)
              x2 = (timercurr = timerstart +(int)((timerend-timerstart)*(stoptime-stime)/(endtime-stime)))*screenwidth/mover.WIDTH;
            else if(gtime < endtime && stime < endtime)
              x2 = (timercurr = timerstart +(int)((timerend-timerstart)*(gtime-stime)/(endtime-stime)))*screenwidth/mover.WIDTH;
            else x2 = x1+h1-w2;
            im1.paint(g,x2,y1,h1,w2);
         }
         g.setColor(Color.black);
         int cx = x2 + clockr.x + clockr.width/2;
         int cy = clockr.y + clockr.height/2;
         int hourlen = clockr.width*3/8;
         int minlen = clockr.width/2;
         g.drawLine(cx,cy,
            cx+(int)(hourlen * Math.cos(hour)), cy+(int)(hourlen * Math.sin(hour)));
         g.drawLine(cx, cy,
            cx + (int)(minlen * Math.cos(minute)),
                       cy+(int)(minlen * Math.sin(minute)));
      }
   }
   //------------------------------------------------------------------
   class sofar extends mover {
      sofar() {
         super(false);
         w = mover.WIDTH-rightpos;
         h = Math.min(tpos,mover.HEIGHT - extray) - topsofar;
         keepMoving = true;
      }
      public void paint(Graphics g, int x1, int y1, int w1, int h1) {
         if(tiles && slots[0] != null) h1 = slots[0].input.toplet*screenheight/mover.HEIGHT - y1;
         int w2 = w1/4;
         int x2 = x1 + w1/8;
         int h2 = h1*3/4,h3;
         int bulbh = h1/8;
         int y2 = y1 + h1/2 - (h2+bulbh)/2;
         int dy = getFontMetrics(sofarfont).getAscent()/2;

         short maxscore;
         if(!extended || wordtot <= percol) maxscore = wordtot;
         else maxscore = (short)Math.min(wordtot,gametot1+2);
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
         g.setFont(sofarfont);
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
 //-------------------------------------------------------
   class sbutton extends mover {
     String message = new String(rgame.getParm("quit"));
     public sbutton() {
        super(false);
        keepMoving = true;
        w = mover.WIDTH/8;
        h = mover.HEIGHT/12;
 //       addMover(this,tiles?mover.WIDTH-w: mover.WIDTH*2/3, tiles?tpos-h:Math.min(tpos,mover.HEIGHT -extray) - h*5/4);
        int newy = Math.min(tpos,mover.HEIGHT -extray) - h*5/4;
        if(keypad.keypadname != null && newy < mover.HEIGHT*5/8)
            newy = mover.HEIGHT*2/8;
        addMover(this,tiles?mover.WIDTH-w: mover.WIDTH*2/3, tiles?tpos-h:newy);
     }
     public void paint(Graphics g,int x, int y, int w1, int h1) {
       if(completed) {
         kill=true;
         return;
       }
       Rectangle r = new Rectangle(x,y,w1,h1);
       sizeFont(null,g,message,w1*3/4,h1*3/4);
       FontMetrics m = g.getFontMetrics();
       g.setColor(u.tooclose(background,Color.black)?white():Color.black);
       x += w1/2;
       y += Math.max(0, h1/2 - m.getHeight()/2 + m.getMaxAscent());
       g.drawString(message, x - m.stringWidth(message)/2,y);
       u.buttonBorder(g,r,Color.red,!mouseDown);
    }
    public void mouseClicked(int x, int y) {
          gamePanel.removeMover(timer);
          kill=true;
          stoptime = gtime;
          forceend = true;
          if (tiles) slots[slottot-1].input.ended = true;
          nextslot();
          gamePanel.refreshat = gtime+100;
   }
   }
   class showwords extends mover {
      word ww[];
//startPR2008-09-05^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      boolean show = true;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      showwords() {
         super(false);
         w = mover.WIDTH/4;
         h = mover.HEIGHT;
         if(wordtot > 60) {
//startPR2008-09-05^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
           show = false;
//           short o[] = u.select(wordtot,(short)60);
//           ww = new word[o.length];
//           for(short i=0;i<o.length;++i) ww[i] = words[o[i]];
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         }
         else {
          ww = words;
         }
         addMover(this,0,0);
     }
     public void paint(Graphics g,int x1, int y1, int w1, int h1) {
//startPR2008-09-05^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       if(!show)return;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        short i;
        if(ww.length>20) {
           word wwx[] = new word[20];
           System.arraycopy(ww,0,wwx,0,20);
           g.setFont(sizeFont(wwx,w1/(ww.length/20+1),h1));
        }
        else   g.setFont(sizeFont(ww,w1,h1));
        FontMetrics m = g.getFontMetrics();
        if(m.getHeight() > getFontMetrics(wordfont).getHeight()) {
           g.setFont(wordfont);
           m = g.getFontMetrics();
        }
        int incy = m.getAscent();
        int dy = m.getHeight();
        g.setColor(Color.blue);
        for(i=0;i<ww.length;++i) {
           g.drawString(ww[i].v(), x1+(i/20)*w1/(ww.length/20+1), y1+incy+dy*(i%20));
        }
     }
   }
}
