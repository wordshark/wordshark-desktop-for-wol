package shark.games;

import java.awt.*;

import shark.*;
import shark.spokenWord.*;

public class pairs_scan extends sharkGame {//SS 03/12/05
  short cardtot,wordtot,remains,across,down;
  pcard pc[],zcard1,zcard2;
  static final short cardw = 2,cardh = 3; // ratios card width:height
  boolean converting,random,matchsounds,markturned,wantswitch;
  int cardheight, cardwidth, textheight;
  Font cardfont;
  Color colorback1 = new Color(128+u.rand(128),u.rand(128),0);
  Color colorback2 = new Color(0,u.rand(128),128+u.rand(128));
  short match1,match2;
  short o[];
  boolean snap;
  boolean restarting,busy;
  long nextturn;
  boolean zapp1,zapp2;
  boolean fl = sharkStartFrame.currPlayTopic.fl && !specialprivateoff;
  boolean phhelp = phonics && (!phonicsw || blended);

  public pairs_scan() {
    errors = true;
    if(!gamescore2)
        gamescore1 = true;
    wantspeed = true;
    gamePanel.clearWholeScreen = true;
    competitivescore = true;
    if(rgame.getParm("snap") != null) snap = true;
    if(snap)sharednoturns =true;
    if(student.optionstring("bgcolor")!=null)
        makeBackground(true);
    setup1();
    if(snap) {
      gamePanel.showSprite = false;
      gamePanel.canquiesce = false;
    }
 }
 //--------------------------------------------------------------
 public void restart() {
    super.restart();
    restarting=true;
//startPR2007-12-10^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    gamePanel.removeAllMovers();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    setup1();
    restarting=false;
 }
    //-------------------------------------------------------------
 void setup1() {
    String s;
    Toolkit t =Toolkit.getDefaultToolkit();
    Image im;
    int bwidth,bheight;
//    boolean oldgamescore2 = gamescore2;         // v5 rb 10/3/08
    match1 = match2 = -1;
    wordtot = (short)rgame.w.length;
    if(rgame.getParm("justpairedwords") != null)  {
       converting = true;
       cardtot = (short)(wordtot);
    }
    else cardtot = (short)(wordtot*2);
    if(snap) {
       optionlist = new String[] {"pelmanism-random","pelmanism-image-cardback"};
 //       if(sharkStartFrame.studentList.length > 1) {                             // v5 rb 9/3/08
//          optionlist = u.addString(optionlist,"card-switch");                     // v5 rb 9/3/08
//       }                                                                          // v5 rb 9/3/08
    }
    else if(phonics && !phonicsw) {
       optionlist =  new String[] {"pelmanism-random","pelmanism-dontmarkturned"};
//       if(sharkStartFrame.studentList.length > 1) {                             // v5 rb 9/3/08
//          optionlist = u.addString(optionlist,"card-switch");                     // v5 rb 9/3/08
//       }                                                                          // v5 rb 9/3/08
    }
    else {
       optionlist = new String[] {"pelmanism-random","pelmanism-dontmarkturned","pelmanism-image-cardback"};
//       if(sharkStartFrame.studentList.length > 1) {                           // v5 rb 9/3/08
//          optionlist = u.addString(optionlist,"card-switch");                   // v5 rb 9/3/08
//       }                                                                          // v5 rb 9/3/08
    }
    if(!converting && !snap) {
       optionlist = u.addString(optionlist,"card-sound");
    }
    random = options.option("pelmanism-random");
    matchsounds = phonics && !phonicsw || !snap && !converting && options.option("card-sound");
    markturned  = !options.option("pelmanism-dontmarkturned");
//    gamescore2 =  sharkStartFrame.studentList.length > 1        // v5 rb 9/3/08
//                        &&   options.option("card-switch");       // v5 rb 9/3/08
    pupilscore = !gamescore2;
//    solo = !gamescore2;                             // v5  rb 9/3/08
    if(snap)  gamePanel.detectEnterSpace = true;
//    else if(gamescore2) markcurrstudent = true;      // v5 rb 10/3/08
    if(!restarting) buildTopPanel();                     // v5 rb 10/3/08
    pc = new pcard[cardtot];
    remains = cardtot;
    card.setback("pelmanism-image-cardback");
    down=2;
    if(card.backimage != null) {
       while(true) {
          cardheight = mover.HEIGHT*7/8/down;
          cardwidth = (int) ((long)cardheight * screenheight
             * card.backimage.width() / card.backimage.height() / screenwidth);
          across = (short)(mover.WIDTH*7/8/cardwidth);
          if(across*down >= cardtot) break;
          ++down;
       }
    }
    else {
       while(true) {
          cardheight = mover.HEIGHT*7/8/down;
          cardwidth =  cardheight*screenheight/screenwidth*2/3;
          across = (short)(mover.WIDTH*7/8/cardwidth);
          if(across*down <= cardtot) break;
          ++down;
       }
    }
    across = (short)Math.min(across, (cardtot+down-1)/down);
    cardfont = sizeFont(cardwidth*screenwidth/mover.WIDTH*7/8,
                cardheight*screenheight/mover.HEIGHT);
    textheight = getFontMetrics(cardfont).getHeight()*mover.HEIGHT/screenheight;
    assignSlots();
    o = u.shuffle(u.select(cardtot,cardtot));
    if(!snap && phhelp)   help(snap?((converting||matchsounds)?"help_pairs1b":"help_pairs1a"):"help_pairs1");
    else  if(snap && gamescore2) help("help_pairs1ax");
    else     help(snap?((converting||matchsounds)?"help_pairs1b":"help_pairs1a"):"help_pairs1");
 //   if(gamescore2 && snap) cancelflip();
    markoption();
 }
 //----------------------------------------------------------------
 void assignSlots() {
    int gap = (mover.WIDTH - across*cardwidth)/(across+1);
    int gapy = (mover.HEIGHT - down*cardheight)/(down+1);
    int xlist[] = new int[cardtot];
    int ylist[] = new int[cardtot];
    int i,j,k,x,y;
    short o[] = u.shuffle(u.select(cardtot,cardtot));
    int repeats;

    if(random) {
       loop0:for(i=0;i<cardtot;++i) {
          repeats = 0;
          loop1: while(true) {
            x = u.rand(mover.WIDTH - cardwidth);
            y = u.rand(mover.HEIGHT - cardheight);
            for(k=0;k<i;++k) {
               if(Math.abs(x - xlist[k]) < (cardwidth)
                   &&  Math.abs(y - ylist[k]) < cardheight*3/4) {
                  if(++repeats > 1000) {
                    cardwidth = cardwidth*15/16;
                    cardheight = cardheight*15/16;
                    cardfont = sizeFont(cardwidth*screenwidth/mover.WIDTH*7/8,
                       cardheight*screenheight/mover.HEIGHT);
                    if(cardfont!=null)
                        textheight = getFontMetrics(cardfont).getHeight()*mover.HEIGHT/screenheight;
                     i = -1;
                     continue loop0;
                  }
                  else continue loop1;
               }
            }
            break loop1;
          }
          xlist[i] = x;
          ylist[i] = y;
       }
    }
    else {
       short rowtot[] = new short[down];
       for(i=0;i<cardtot;++i) {
          while(true) {
             k = u.rand(down);
             if(rowtot[k] < across) {
                 xlist[i] = rowtot[k];
                 ylist[i] = k;
                 ++rowtot[k];
                 break;
             }
          }
       }
       for(i=0;i<cardtot;++i) {
          gap =  (mover.WIDTH - cardwidth*rowtot[ylist[i]])/(rowtot[ylist[i]]+1);
          xlist[i] = gap+xlist[i]*(cardwidth+gap);
          ylist[i] = gapy+(cardheight+gapy)*ylist[i];
       }
    }
    for(i=0;i<cardtot;++i) {
       pc[i] = new pcard((short)(converting?o[i]:(o[i]/2)));
       pc[i].colorback1 =colorback1;
       pc[i].colorback2 =colorback2;
       if(!converting && matchsounds) {
          if(o[i]%2 != 0) {
             pc[i].dontsay = true;
             pc[i].extraimage = new sharkImage(card.eye,false);
             pc[i].keepMoving = true;
          }
          else  {
             pc[i].frontimage = card.ear;
             pc[i].extraimage = card.ear;
          }
       }
       else if(!converting && fl && o[i]%2 != 0) {
         pc[i].bigfrontimage = new sharkImage(pc[i].value.vpic(), false);
         pc[i].keepMoving = pc[i].bigfrontimage.canmove;
         pc[i].dontsay = true;
       }
       addMover(pc[i],xlist[i], ylist[i]);
    }
 }
 //----------------------------------------------------------------
 public void afterDraw(long t){
    if(snap) nextturn(t);
    if(zapp1) {
       zapp(zcard1,(zcard1.x+ zcard1.w/2)*screenwidth/mover.WIDTH ,
            (zcard1.y+zcard1.h/2)*screenheight/mover.HEIGHT,true);
       removeMover(zcard2);
//       if(remains>2)
//          flip();
       zapp1 = false;
       zapp2 = true;
    }
    else if(zapp2) {
        zcard1.dead = true;
        zcard2.dead = true;
        match1 = match2 = -1;
        if(random) askforredraw();
        testend();
        zapp2 = false;
    }
 }
 //---------------------------------------------------------------
 void testend() {
    if((remains-=2) > 0) {
       resetcards();
       nextturn = gtime() + 1000 + 5000 / speed;
    }
    else if(gamescore2) {     // end - 2 players
       score(gametot1);
       otherplayer.totscore += gametot2;                    // v5 rb 10/3/08
       
       if(gametot2>gametot1) {
          flash(new String[] {(rgame.getParm("winneris")+" "+otherplayer.name),      // v5 rb 10/3/08
//startPR2007-12-10^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//                                  ">>>>>>>>>>>>>>>>>>>>>" }, 8000);
                "....................." }, 8000);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         forcerewardflipped = 1;
       }
       else if(gametot2==gametot1) {
          flash(u.splitString(rgame.getParm("draw")),8000);
       }
       else {
          flash(new String[] {(rgame.getParm("winneris")+" "+sharkStartFrame.studentList[sharkStartFrame.currStudent].name),
//startPR2007-12-10^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//                "<<<<<<<<<<<<<<<<<<<<<<" }, 8000);
                  "....................." }, 8000);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          forcerewardflipped = 0;
       }
        
       completed = true;
       exit(3000);
    }
    else {   // end, single player
       completed = true;
       score(gametot1);
       exit(3000);
    }

 }
 //---------------------------------------------------------------
 void nextturn(long t) {
             if(!zapp1 && !zapp2 && !busy
                  && t > nextturn) {
                if(!allended()) {
                   nextturn = gtime() + 1000 + 5000 / speed;
                }
                else if(match1 < 0) {
                   turnnext();
                   nextturn = gtime() + 1000 + 5000 / speed;
                }
                else if(!gamescore2) {
                   click(-3,-3) ;
                }
             }
 }
 //-----------------------------------------------------------
 boolean allended() {
    for(short i=0;i<cardtot;++i) if(pc[i] != null && (!pc[i].ended && pc[i].bigfrontimage==null)
                                    && !pc[i].dead) return false;
    return true;
 }
 //-----------------------------------------------------------
 boolean turnnext() {
    short ii,jj,i,j;
    for(ii=0;ii<cardtot;++ii) {
       i =o[ii];
       if(!pc[i].dead && !pc[i].faceup) {
          pc[i].faceup = true;
          pc[i].ended = false;
          if(matchsounds && !pc[i].dontsay) pc[i].say();
          for(jj=0;jj<ii;++jj) {
             j=o[jj];
             if(!pc[j].dead && pc[j].faceup
                 && pc[i].wordno == pc[j].wordno
                   || converting
                     && pc[i].wordno/2 == pc[j].wordno/2) {
                  match1 = i;
                  match2 = j;
             }
          }
          return true;
       }
    }
    return false;
 }
 //----------------------------------------------------------
           // click, ENTER(x=y=-1) or spacebar(x=y=-2) or timer(x=y=-3)
 public boolean click(int xx, int yy) {
    final int x = xx, y = yy;
    short i;
    if(!snap) return false;
    if(zapp1 || zapp2 || restarting || busy) return true;
    if(match1>=0) {
      busy = true;
      pc[match1].saying=true;
      pc[match1].ended=false;
      pc[match2].saying=true;
      pc[match2].ended=false;
      if(x != -3 ) {spokenWord.findandsay("snap"); }
      new spokenWord.whenfree(0) {
        public void action() {
          pc[match2].say();
          if(converting)  pc[match1].say();
          new spokenWord.whenfree(0) {
             public void action() {
                     pc[match1].saying = false;
                     pc[match1].ended = false;
                     if(!gamescore2 && x == -3 && y == -3) {
                       gamescore(-1);
                       error();
                       resetcards();
                       nextturn = gtime() + 1000 + 5000 / speed;
                     }
                     else {
                        if(!gamescore2 && x != -3 && y != -3)  {
//startPR2005-04-18^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                          if(Demo_base.isDemo){
                            if (Demo_base.demoIsReadyForExit(0)) return;
                          }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                          gamescore(1);
                        }
                        else if(x==-2 && y==-2)  {   // space bar
                           adjustscores((short)(gametot1+1),(short)gametot2);
                        }
                        else  {         // enter or click
                            adjustscores((short)gametot1,(short)(gametot2+1));
                       }
                       zcard1 = pc[match2];
                       zcard2 = pc[match1];
                       zapp1 = true;
                     }
                     busy=false;
              }
          };
        }
      };
    }          // error - wrong click
    else if(x!= -3 && y != -3) {
       error();
       if(!gamescore2)  {
          gamescore(-1);
       }
       else {
          if(x==-2 && y==-2)   adjustscores((short)Math.max(0,gametot1-1),(short)gametot2);
          else                adjustscores((short)gametot1,(short)Math.max(0,gametot2-1));
       }
       resetcards();
       nextturn = gtime() + 1000 + 5000 / speed;
     }
     return true;
 }
 //--------------------------------------------------------------
 void resetcards() {
    for(short i=0;i<cardtot;++i) {
       if(!pc[i].dead && pc[i].faceup) {
          pc[i].ended = false;
          pc[i].faceup = false;
       }
    }
    o = u.shuffle(u.select(cardtot,cardtot));
    match1 = match2 = -1;
 }
 //--------------------------------------------------------------
 void askforredraw() {
    for(short i=0;i<cardtot;++i) {
       if(!pc[i].dead) pc[i].ended = false;
    }
 }
 //--------------------------------------------------------------
 class pcard extends card {
    short wordno, other;
    boolean dead,dontsay;
    pcard thisc;
    public pcard( short wordno1) {
       super();
       thisc=this;
       w = cardwidth;
       h = cardheight;
       changecolor = markturned;
       value = rgame.w[wordno1];
//startPR2008-08-05^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//       options = (byte)(rgame.options|word.CENTRE);
        optionsval = (byte)(rgame.options|word.CENTRE);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       wordno=wordno1;
       f = cardfont;
       manager = gamePanel;
    }
    public void mouseClicked(int x, int y) {
       pcard c1;
       short i,j;
       if(snap || dead || zapp1 || zapp2 || faceup) return;
       for(i=j=0;i<cardtot;++i) {if(pc[i].faceup && !pc[i].dead && ++j>1) return;}
       if(matchsounds) {
          for(i=0;i<cardtot;++i) {
             if(pc[i]!= this && pc[i].faceup && !pc[i].dead) {
                if(pc[i].dontsay == this.dontsay) return;
             }
          }
       }
       faceup = true;
       ended = false;
       if(!dontsay) {say();}
       new  spokenWord.whenfree(dontsay?1000:0) {
         public void action() {
             boolean flippeddone = false;
            for(short i=0;i<cardtot;++i) {
              if(pc[i]!= thisc && pc[i].faceup && !pc[i].dead) {
               other = i;
               if(pc[i].wordno == thisc.wordno
                   || converting
                     && pc[i].wordno/2 == thisc.wordno/2) {
                  if(fl && dontsay) {say();u.pause(1000);}
                  gamescore(1);
                  zcard1 = thisc;
                  zcard2 = pc[other];
                  zapp1 = true;
//startPR2006-12-08^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                  if(Demo_base.isDemo){
                    if (Demo_base.demoIsReadyForExit(0)){
                      u.pause(1000);
                      return;
                    }
                  }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                }
                else {
                   if(!snap && !flippeddone){
                       flip();
                       flippeddone = true;
                   }
                   new spokenWord.whenfree(1500/speed) {
                     public void action() {
                         if(pc[other].alreadyTurned || thisc.alreadyTurned) {
                             if(!gamescore2) gamescore(-1);
                             error();
                         }
                         pc[other].alreadyTurned = thisc.alreadyTurned = true;
                         pc[other].faceup = false;
                         thisc.faceup = false;
                         pc[other].ended = thisc.ended = false;
//                         if(gamescore2) flipstudent(true);
                     }
                   };
                }
                if(snap && gamescore2) help("help_pairs1ax");
                else   help(snap?"help_pairs1a":"help_pairs1");
                return;
              }
              if(matchsounds) // one card turned
                     help((dontsay?"help_pairs2b":"help_pairs2c") + (phhelp?"ph":""));
              else   help(converting?"help_pairs2a":"help_pairs2");
            }
         }
       };
    }
 }
}
