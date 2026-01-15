package shark.games;

import java.awt.*;

import shark.*;
import shark.spokenWord.*;

public class snap extends sharkGame {
  short cardtot,wordtot;
  scard pc[];
  boolean restarting;
  static final short cardw = 2,cardh = 3; // ratios card width:height
  boolean converting,matchsounds,wantswitch;
  int cardheight, cardwidth, textheight, overlapx, overlapy;
  stack stacks[] = new stack[4];
  boolean waiting, flipturn;
  Font cardfont;
  Color colorback1 = new Color(128+u.rand(128),u.rand(128),0);
  int bwidth,bheight;
  long nextturn;
  long restartat;
  boolean fl = sharkStartFrame.currPlayTopic.fl && !specialprivateoff;

  public snap() {
    errors = false;
    if(!gamescore2)
        gamescore1 = true;
    wantspeed = true;
    clickenddrag = false;
    competitivescore = true;
    sharednoturns =true;
    if(student.optionstring("bgcolor")!=null)
        makeBackground(true);
    setup1();
    gamePanel.canquiesce = false;
    if(fl) gamePanel.clearWholeScreen = true;
 }
 //--------------------------------------------------------------
 public void restart() {
    super.restart();
    setup1();
    waiting=false;
 }
    //-------------------------------------------------------------
 void setup1() {
    String s;
    Toolkit t =Toolkit.getDefaultToolkit();
    Image im;
//    boolean oldgamescore2 = gamescore2;                // v5 rb 10/3/08

    wordtot = (short)rgame.w.length;
    if(rgame.getParm("justpairedwords") != null)  {
       converting = true;
       cardtot = (short)(wordtot);
    }
    else cardtot = (short)(wordtot*2);
    optionlist = new String[] {"pelmanism-image-cardback"};
//    opnarr = new String[] { "Choose image for back of card",};
//    if(sharkStartFrame.studentList.length > 1) {                         // v5 rb 10/3/08
//       optionlist = u.addString(optionlist,"card-switch");                // v5 rb 10/3/08
//       opnarr = u.addString(opnarr,"Play against another student");
//    }                                                                     // v5 rb 10/3/08
    if(!converting) {
       optionlist = u.addString(optionlist,"snap-sound");
//       opnarr = u.addString(opnarr,"Match sound to printed word");
    }
    matchsounds = !converting && options.option("snap-sound");
//    gamescore2 =  sharkStartFrame.studentList.length > 1                      // v5 rb 10/3/08
//                        &&   options.option("card-switch");                    // v5 rb 10/3/08
    computerscore = !gamescore2;
//    solo = computerscore;                       // v5  rb 9/3/08

    pupilscore = false;
    gamePanel.detectEnterSpace = true;
//    if(!restarting || oldgamescore2 != gamescore2) buildTopPanel();   // v5 rb 10/3/08
    if(!restarting) buildTopPanel();   // v5 rb 10/3/08
    pc = new scard[cardtot];
    scard.setback("pelmanism-image-cardback");
    if(card.backimage != null) {
       bwidth = card.backimage.width();
       bheight = card.backimage.height();
    }
    else {bwidth = 2; bheight = 3;}
    initstacks();
    initcards();
    cardfont = sizeFont(cardwidth*screenwidth/mover.WIDTH*7/8,
                cardheight*screenheight/mover.HEIGHT);
    textheight = getFontMetrics(cardfont).getHeight()*mover.HEIGHT/screenheight;

    if(gamescore2) help("help_pairs1ax");
    else help((converting||matchsounds)?"help_pairs1b":"help_pairs1a");
    markoption();
    if(gamescore2) cancelflip();
  }
 //---------------------------------------------------------------
 public void afterDraw(long t){
            if(restartat != 0) {
               if(t >= restartat) {
                  restartat = 0;
                  restarting = true;
                  restart();
                  restarting = false;
                  waiting = false;
               }
            }
            else if(!waiting && !completed
                && stacks[0].ended
                && stacks[1].ended
                && stacks[2].ended
                && stacks[3].ended
                && t > nextturn) {
                if(!match(top(1),top(2))) {
                  turnnext();
                  endmove();
                }
                else if(computerscore) {
                   click(-3,-3) ;
                }
              }
 }
 //----------------------------------------------------------------
 void initstacks() {
     gamePanel.removeAllMovers();
    short i;
    int gapx = mover.WIDTH/16;
    cardwidth = (mover.WIDTH-gapx*5)/4;
    cardheight = (int)((long)cardwidth*bheight*screenwidth/bwidth/screenheight);
    int stacky = mover.HEIGHT - cardheight - (mover.HEIGHT - cardheight)/8;
    int stackdy = Math.min(cardheight/12, stacky/cardtot);
    for(i=0;i<4;++i) {
       stacks[i] = new stack();
       stacks[i].yb  = stacky;
       stacks[i].dy = -stackdy;
       stacks[i].h = cardheight + (cardtot)*stackdy;
       stacks[i].c = new short[cardtot];
    }
    stacks[0].dx = -gapx/cardtot;
    stacks[0].xb  = gapx;
    stacks[1].dx = -gapx/cardtot*2/3;
    stacks[1].xb  = gapx*2+cardwidth;
    stacks[2].dx = gapx/cardtot*2/3;
    stacks[2].xb  = gapx*3+cardwidth*2;
    stacks[3].dx = gapx/cardtot;
    stacks[3].xb  = gapx*4+cardwidth*3;
    stacks[0].w = stacks[3].w = (cardtot-1)*stacks[3].dx+ cardwidth;
    stacks[1].w = stacks[2].w = (cardtot-1)*stacks[2].dx + cardwidth;
    short o[] =  u.shuffle(u.select(cardtot,cardtot));
    for(i=0;i<cardtot;i+=2)  {
       stacks[0].c[i/2] = o[i];
       stacks[3].c[i/2] = o[i+1];
    }
    stacks[0].ctot = stacks[3].ctot = (short)(cardtot/2);
    u.shuffle(stacks[0].c,(short)0,stacks[0].ctot);
    u.shuffle(stacks[3].c,(short)0,stacks[3].ctot);
    fixstacks();
    addMover(stacks[0], stacks[0].xb+(cardtot-1)*stacks[0].dx ,  stacks[0].yb + (cardtot-1)*stacks[0].dy);
    addMover(stacks[1], stacks[1].xb+(cardtot-1)*stacks[1].dx ,  stacks[1].yb + (cardtot-1)*stacks[1].dy);
    addMover(stacks[2], stacks[2].xb ,  stacks[2].yb + (cardtot-1)*stacks[2].dy);
    addMover(stacks[3], stacks[3].xb ,  stacks[3].yb + (cardtot-1)*stacks[3].dy);
 }
 //----------------------------------------------------------------
 void initcards() {
    int i,j,k,x,y;
    Font cardfont = sizeFont(cardwidth*screenwidth/mover.WIDTH*7/8,
                cardheight*screenheight/mover.HEIGHT);

    for(i=0;i<cardtot;++i) {
       pc[i] = new scard((short)(converting?i:(i/2)));
       pc[i].colorback1 =colorback1;
       pc[i].f = cardfont;
       if(!converting && matchsounds) {
          if(i%2 != 0) {
             pc[i].dontsay = true;
             pc[i].extraimage = new sharkImage(card.eye,false);
          }
          else  {
             pc[i].frontimage = card.ear;
             pc[i].extraimage = card.ear;
          }
       }
       else if(!converting && fl && i%2 != 0) {
         pc[i].bigfrontimage = new sharkImage(pc[i].value.vpic(), false);
         pc[i].keepMoving = pc[i].bigfrontimage.canmove;
         pc[i].dontsay = true;
       }
//       addMover(pc[i],0,0);
    }
 }
 //--------------------------------------------------------------
 void fixstacks() {
    short i;
    if(stacks[0].ctot == 0 || stacks[3].ctot == 0) return;
    if(!willmatch()) {
       short turns = (short)(stacks[0].ctot + stacks[3].ctot - 1);
       if(turns>1) fixcard((short)(2+u.rand(turns-1)));
    }
 }
 //--------------------------------------------------------------
 short top(int st) {
   if(stacks[st].ctot == 0) return -1;
   else return stacks[st].c[stacks[st].ctot-1];
 }
 //--------------------------------------------------------------
 boolean willmatch() {
    short tot0=stacks[0].ctot, tot3 = stacks[3].ctot,i,j;
    short topcard1= top(0),topcard2=top(3);
    boolean flip = flipturn;
    if (match(topcard1,topcard2)) return true;
    while(tot0 > 0 || tot3 > 0) {
       if(!flip && tot0>0 || tot3==0) topcard1 = stacks[0].c[--tot0];
       else             topcard2 = stacks[3].c[--tot3];
       if (match(topcard1,topcard2)) return true;
       flip = !flip;
    }
    return false;
 }
 //--------------------------------------------------------------
 void fixcard(short turn) {
    short tot0=stacks[0].ctot, tot3 = stacks[3].ctot,i,j=0;
    short topcard1= top(0),topcard2=top(3);
    boolean flip = flipturn;
    short fromstack=0,fromno=0;
    short prevno=0,thisno=0;

    while(turn-- > 0) {
       prevno=thisno;
       if(!flip && tot0>0 || tot3==0) {
          thisno = topcard1 = stacks[0].c[--tot0];
          fromstack = 0;
          fromno = tot0;
       }
       else  {
          thisno = topcard2 = stacks[3].c[--tot3];
          fromstack = 3;
          fromno = tot3;
       }
       flip = !flip;
    }
    if(fromstack == 0) prevno = topcard2;
    else               prevno = topcard1;
    loop1:for(i=0;i<4;++i) {      // find matching card
       for(j=0;j<stacks[i].ctot;++j) {
          if(match(stacks[i].c[j], prevno)) break loop1;
       }
    }
                                  // swap cards
    stacks[fromstack].c[fromno] = stacks[i].c[j];
    stacks[i].c[j] = thisno;
 }
 //---------------------------------------------------------------
 boolean match(short c1, short c2) {
    return (c1>=0 && c2>=0 && c1 != c2 && c1/2 == c2/2);
 }
 //----------------------------------------------------------
           // click, ENTER(x=y=-1) or spacebar(x=y=-2) or timer(x=y=-3)
 public boolean click(int xx, int yy) {
   final int x =xx, y=yy;
    short i;
    if(waiting) return true;
    waiting = true;
    getxy();
    if(match(top(1),top(2))) {
      if(x != -3) spokenWord.findandsay("snap_1");
      pc[top(1)].saying=true;stacks[1].ended=false;
      if(!converting) {pc[top(2)].saying=true;stacks[2].ended=false;}
      new spokenWord.whenfree(0) {
        public void action() {
           pc[top(1)].say();
           new spokenWord.whenfree(0) {
              public void action() {
                    stacks[1].ended=false;
                    if(converting) {pc[top(2)].say(); stacks[2].ended=false;}
                    else {pc[top(2)].saying=false;stacks[2].ended=false;}
                    if(x==-2 && y==-2 || computerscore && x != -3 && y != -3)  {
                        movecards(1,0);
                        movecards(2,0);
                    }
                    else  {
                         movecards(1,3);
                         movecards(2,3);
                    }
                    endmove();
                    fixstacks();
                    checkend();
                    waiting=false;
//startPR2005-04-18^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                    if(Demo_base.isDemo){
                      if (Demo_base.demoIsReadyForExit(0)) return;
                    }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
               }
            };
         }
      };
    }
    else if(x!= -3 && y != -3) {
       if(top(1) >= 0) {pc[top(1)].say();stacks[1].ended=false;}
       new spokenWord.whenfree(0) {
          public void action() {
             stacks[1].ended=false;
             if(top(2) >= 0) {pc[top(2)].say();stacks[2].ended=false;}
             new spokenWord.whenfree(0) {
                public void action() {
                  stacks[2].ended=false;
                  if(x==-2 && y==-2 || computerscore) {
                      movecards(1,3);
                      movecards(2,3);
                  }
                  else  {
                     movecards(1,0);
                     movecards(2,0);
                  }
                  endmove();
                  fixstacks();
                  checkend();
                  waiting=false;
               }
             };
          }
       };
    }
    return true;
 }
 //--------------------------------------------------------------
 void checkend()  {
    if(stacks[0].ctot == 0) {      // computer or righthand player won
       if(computerscore){
         waiting = true;
         flash(u.splitString(rgame.getParm("computerwin")),4000);
         restartat = gtime + 4000;
          return;
       }
       else {
          completed = true;
          otherplayer.totscore += cardtot/4;               // v5 rb 10/3/08
          gametot2 = cardtot/4;
          flash(new String[] {(rgame.getParm("winneris")+" " + otherplayer.name),      // v5 rb 10/3/08
//startPR2007-12-10^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//                               ">>>>>>>>>>>>>>>>>>>>>" }, 4000);
                "....................." }, 4000);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          forcerewardflipped = 1;
          exit(3000);
       }
     }
     else if(stacks[3].ctot == 0)  {
          completed = true;
         score(cardtot/4);
         flash(new String[] {(rgame.getParm("winneris")+" "+sharkStartFrame.studentList[sharkStartFrame.currStudent].name),
//startPR2007-12-10^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//                            "<<<<<<<<<<<<<<<<<<<<<<<" }, 4000);
               "....................." }, 4000);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         forcerewardflipped = 0;
         gametot1 = cardtot/4;
         exit(3000);
     }
 }
 //--------------------------------------------------------------
 void endmove()  {
       adjustscores(stacks[0].ctot,stacks[3].ctot);
       nextturn = gtime() + 1000 + 3000 / speed;
 }
 //--------------------------------------------------------------
 void movecards(int from,int to) {
    stack s1 = stacks[from];
    stack s2 = stacks[to];
    while(s1.ctot > 0) {
       pc[s1.c[0]].faceup = false;
       System.arraycopy(s2.c,0, s2.c,1,s2.ctot++);
       s2.c[0] = s1.c[0];
       System.arraycopy(s1.c,1, s1.c,0,--s1.ctot);
       s1.ended = s2.ended = false;
       u.pause(200);
    }
    flipturn = (to != 0);
 }
 //---------------------------------------------------------------
 void turnnext() {
    short tc;
    if(!flipturn && stacks[0].ctot > 0 || stacks[3].ctot==0) {
       tc = stacks[1].c[stacks[1].ctot++] = stacks[0].c[--stacks[0].ctot];
       stacks[0].ended = false;
       stacks[1].ended = false;
    }
    else {
       tc = stacks[2].c[stacks[2].ctot++] = stacks[3].c[--stacks[3].ctot];
       stacks[2].ended = false;
       stacks[3].ended = false;
    }
    pc[tc].faceup = true;
    flipturn = !flipturn;
    if(matchsounds && !pc[tc].dontsay) {getxy();u.pause(300);pc[tc].say();}
 }
 //-------------------------------------------------------
                 // get pos of top cards of stacks 1 & 2
 void getxy() {
    if(stacks[1].ctot>0) {
       pc[top(1)].x = stacks[1].xb+(stacks[1].ctot-1)*stacks[1].dx;
       pc[top(1)].y = stacks[1].yb+(stacks[1].ctot-1)*stacks[1].dy;
    }
    if(stacks[2].ctot>0) {
       pc[top(2)].x = stacks[2].xb+(stacks[2].ctot-1)*stacks[2].dx;
       pc[top(2)].y = stacks[2].yb+(stacks[2].ctot-1)*stacks[2].dy;
    }
 }
 //--------------------------------------------------------------
 class scard extends card {
    short wordno;
    boolean dontsay;
    public scard( short wordno1) {
       super();
       w = cardwidth;
       h = cardheight;
       manager =  gamePanel;
       value = rgame.w[wordno1];
//startPR2008-08-05^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//       options = (byte)(rgame.options|word.CENTRE);
        optionsval = (byte)(rgame.options|word.CENTRE);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       wordno=wordno1;
       f = cardfont;

    }
  }
  //--------------------------------------------------------------
  class stack extends mover{
     int xb,yb,dx,dy;
     short c[];  // card nos
     short ctot;
     public stack() {
       super(false);
     }
     public void paint(Graphics g,int x,int y,int w,int h) {
       int x1 = xb * screenwidth/mover.WIDTH;
       int y1 = yb * screenheight/mover.HEIGHT;
       int cw = cardwidth * screenwidth/mover.WIDTH;
       int ch = cardheight * screenheight/mover.HEIGHT;
       scard ca;
       for(short i=0;i<ctot-1;++i) {
          ca = pc[c[i]];
          if(ca == null) continue;
          ca.noimage = true;
          word savev = ca.value;
          sharkImage savei = ca.frontimage;
          ca.value = null;
          ca.paint(g,x1,y1,cw,ch);
          ca.noimage = false;
          x1+=dx * screenwidth/mover.WIDTH; y1+= dy* screenheight/mover.HEIGHT;
          ca.value = savev;
       }
       if(ctot > 0 && pc[c[ctot-1]] != null) pc[c[ctot-1]].paint(g,x1,y1,cw,ch);
     }
  }

}
