package shark.games;

import java.awt.*;

import shark.*;
import shark.sharkGame.*;

public class phonicstiles extends sharkGame {
  short curr,wordtot;
  String pressenter;
  sharkInput ip;
  int listw = mover.WIDTH/4;
  ipmover ipmover;
  Font f;
  FontMetrics m;
  int o[] = u.shuffle(u.select(rgame.w.length, rgame.w.length));
  boolean hidewords;
  mover presse;
  long newip = System.currentTimeMillis();
  boolean chekatend;

  public phonicstiles() {
    errors = true;
    gamescore1 = true;
    peeps = false;
    listen= true;
    peep = false;
    forceSharedColor = true;
    optionlist = new String[] {"phonictiles_chekatend"};
    chekatend = options.option("phonictiles_chekatend");
    curr = -1;
    wordtot = (short)rgame.w.length;
    pressenter = rgame.getParm("clicktick");
    gamePanel.setBackground(new Color(0,0,120+u.rand(120)));
    gamePanel.clearWholeScreen = true;
    ipmover = new ipmover();
    buildTopPanel();
    markoption();
    help((chekatend?"help_phonicstiles2":"help_phonicstiles") + helpsuff());
    pictureat = 2;
  }
  String helpsuff() {
    if(phonicsw) return  blended?"phb":"";
    else if(phonics) return "ph";
    else return "";
  }
  //--------------------------------------------------------------
  public void restart() {
    super.restart();
    chekatend = options.option("phonictiles_chekatend");
    curr = -1;
    ipmover = new ipmover();
    newip = gtime;
    help((chekatend?"help_phonicstiles2":"help_phonicstiles") + helpsuff());
    markoption();
    help(chekatend?"help_phonicstiles2":"help_phonicstiles");
  }
 //--------------------------------------------------------------
 public void afterDraw(long t) {
    long lastsnap;
    short i, ct = 0;
    int mx = gamePanel.mousexs, my = gamePanel.mouseys;
    boolean issnap = false;
    if(completed) return;
    if(newip != 0 && gtime >= newip && wordtot>0) {
      newip = 0;
      if(curr<0)     presse = showmessage(pressenter,0,mover.HEIGHT*9/10,mover.WIDTH,mover.HEIGHT,Color.yellow);
      else if(presse != null) {presse.kill=true;presse=null;}
      if (++curr >= wordtot) {
        exitbutton(mover.HEIGHT / 2);
      }
      else {
              if(delayedflip && !completed){
                  flip();
              }   
        ip = new sharkInput(new sharkAction() {
          public void enter() {
            if(phonics) {
              if(ip.correct_phonemes()) {
                ip.ended = true;
                end();
              }
              else error();
           }
            else if (currword.v().equals(ip.val)) {
              ip.ended = true;
              end();
            }
            else
              error();
//                      }
          }

          public void escape() {
            terminate();
          }

          public void f1() {
            sharkStartFrame.mainFrame.setwanthelp(!sharkStartFrame.mainFrame.wanthelp);
//           ToolTipManager.sharedInstance().setEnabled(sharkStartFrame.mainFrame.wanthelp);
            gamePanel.currgame.changehelp();
          }

          public void error() {
            gamePanel.currgame.error(currword.v());
            gamescore( -1);
            noise.groan();
          }

          public void end() {
            hidewords=false;
            gamescore(2);
            mover mm = new wordmover();
            addMover(mm,ip.lettervalx,ip.lettervaly);
            mm.moveto(0,mover.HEIGHT * curr/wordtot,600);
//startPR2007-12-20^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            if(Demo_base.isDemo)
              if (Demo_base.demoIsReadyForExit(0)) return;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            newip = gtime+1200;
          }

          public void button(byte bno) {}

          public void mouseClick() {
            spokenWord.sayPhonicsWhole(currword);
          }
        });
        currword = rgame.w[o[curr]];
        ip.cantgoback = !chekatend;
        ip.model = currword.v();
        ip.dontcompare = chekatend;
        ip.val = "";
        ip.maxlen = (short) (ip.model.length() + 1);
        ip.setphonemelist(rgame.w, currword);
        ip.singlesound = singlesound;
        ipmover.input = ip;
        hidewords=true;
 //        new showpicture();
        if(phonics && !phonicsw)
         spokenWord.sayPhonicsWord(currword,500,false,false,!singlesound);
        else spokenWord.sayPhonicsWhole(currword);
        gamePanel.showSprite = true;
      }
    }
  }
   //--------------------------------------------------------------------
   public void listen1() {
       if(currword != null ) spokenWord.sayPhonicsWhole(currword);
   }
   //===================================================================================================
   class ipmover extends mover {
      ipmover() {
         w = mover.WIDTH - listw;
         h = mover.HEIGHT/4;
         addMover(this,listw,mover.HEIGHT/2-h);
      }
      public void paint(Graphics g, int x1, int y1, int w1, int h1) {
        if(input != null) input.paint(g,x1,y1,w1,h1,screenheight);
      }
   }
   //
   class wordmover extends mover {
     String s;
     wordmover() {
        if(f==null) {
           f = sizeFont(rgame.w,listw*screenwidth/mover.WIDTH,screenheight/Math.max(12,wordtot)*wordtot);
           m = getFontMetrics(f);
        }
        s = currword.v();
        w = m.stringWidth(s)*mover.WIDTH/screenwidth;
        h = mover.HEIGHT/o.length;
     }
     public void paint(Graphics g, int x1, int y1, int w1, int h1) {
       if(hidewords) return;
       if(shark.macOS) ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
       g.setFont(f);
       g.setColor(Color.white);
       g.drawString(s,x1,y1 + h1/2 - m.getHeight()/2 + m.getAscent());
     }
   }
}
