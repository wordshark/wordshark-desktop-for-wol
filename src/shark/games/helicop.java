package shark.games;

import java.awt.*;

import shark.*;
import shark.sharkGame.*;

public class helicop extends sharkGame {//SS 03/12/05
  final int MINCLOSETIME = 600,MAXCLOSETIME = 2000,WAITAFTERDROP=800;
  final short MAXCRACKS = 10,MAXCRACKTIME = 1000, MINCRACKTIME=400;
  final short MAXSMASHERS = 10;
  final short TURNTIME = 600,FRONTALTIMETURN = 200,FRONTALTIME = 1500;
  static final Color scorecolors[] = new Color[] {new Color(0,128,0),
                                           new Color(64,0,128),
                                           new Color(128,64,0),
                                           new Color(192,0,64),
                                           new Color(255,0,0) };
//                               Color.gray,Color.blue,Color.green,Color.pink,Color.red };
  word[][] words;
  bucket b[];
  short wordtot,curr;
  short buckettot;
  short[] order;
  int bbetween,bbottom,bwidth,bheight,bslotheight,bwordx,wordheight;
  Color bucketcolor = u.darkcolor();
  String headings[],headings2[];
  helicopter heli;
  dropword drop;
  bucket currbuck, dropinto;
  int maxwordwidth,wmax;
  smasher smashers[] = new smasher[MAXSMASHERS];
  int GOODTIME;
  short turntot;
  turner turners[];
  boolean ending,endmessup,iscursor=true;
  byte wantend;
  long endturn,frontal;
  bucket zappbuck;
  boolean wasbad;
  byte good,bad;
  boolean nonoise;
  int headingtop;
//startPR2004-11-10^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  /**
   * true if the helicopter is ready to be set back to move with the mouse
   */
  boolean wantPickUp = false;
  /**
   * when true allows the helicopter to be set to move with the mouse.
   */
  boolean mouseHeli = false;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR


  public helicop() {
    gamePanel.dontstart = true;
    errors = true;
    gamescore1 = true;
    peeps = true;
    listen= true;
    peep = true;
    wantspeed = true;
    pictureat = 2;
    help("help_buckets1");
    this.wantSprite = false;
    gamePanel.simpleoptions = true;
    optionlist = new String[] {"helicop-nonoise"};
    nonoise = options.option("helicop-nonoise");
    //    rgame.options |= word.HIGHLIGHT;
//    gamePanel.clearWholeScreen = true;
    buildTopPanel();
//    addMover(heli,0,0);
    gamePanel.dontstart = false;
    clickonrelease = true;
    markoption();
   }
   //--------------------------------------------------------------
   public void restart() {
     int i;
     nonoise = options.option("helicop-nonoise");
     markoption();
     for(i=0;i<buckettot; ++i)  addMover(b[i],b[i].x,b[i].y);
   }
  //-------------------------------------------------------------
  void nextword() {
     short i;
     if (curr >= wordtot && wantend==0) {wantend = 10; return; }
     short num = order[curr++];
     for (i=0;i<buckettot;++i) {
        if(num < words[i].length) {
           currword = words[i][num];
           currword.say();
           pictureat = heli.x > mover.WIDTH/2 ? 1: 2;
           new showpicture();
           currbuck = b[i];
           break;
        }
        num -= words[i].length;
     }
  }
  //--------------------------------------------------------------
  void endgame() {
     short i,j;
     ending = true;
     turners = new turner[wordtot];
     help("help_buckets2");
     for(i=0;i<buckettot;++i) {
        for(j=(short)(b[i].btot-1);j>=0;--j) {
           turners[turntot] = new  turner(i,j);
           gamescore(b[i].scores[j]);
           ++turntot;
        }
     }
     endturn = gtime + turntot*TURNTIME;
//    this.dispose();
  }
  public void afterDraw(long t) {
     short i;
     boolean hadzapp = false;
     if(heli == null) {
       setupWords();
       for(i=0;i<buckettot; ++i)  b[i] = new bucket(i);
       heli = new helicopter();
       nextword();
     }
     if(zappbuck != null || wantend>0) {
         for(i=0;i<MAXSMASHERS;++i) {
            if(smashers[i] != null   && (wantend>0 ||smashers[i].buck == zappbuck)) {
              if(gamePanel.isMover(smashers[i])) {
                   if(!hadzapp) {
//startPR2004-11-10^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                     /*if this is to be the first (or only) smasher to be zapped
                     on the last correct turn*/
                     if (curr >= wordtot && !mouseHeli) {
                       // drop the helicopter from moving with the mouse
                       gamePanel.drop(heli);
                       mouseHeli = true;
                       boolean thisBucket = false;
                       /* check to see if there is a smasher in the bucket the
                                  word is being dropped into*/
                       for (int j = 0; j < MAXSMASHERS; ++j) {
                         if(smashers[j]!=null){
                           if(smashers[j].buck == zappbuck)
                             thisBucket=true;
                         }
                       }
                       // so that the helicopter is drawn properly
                       if(thisBucket)
                         heli.kill=true;
                     }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                      hadzapp = true;
                      zapp(smashers[i]);
                   }
                   else {
                      removeMover(smashers[i]);
                   }
               }
               smashers[i].buck.btot = (short)Math.min(smashers[i].buck.droptot, smashers[i].buck.btot);
               smashers[i] = null;
            }
         }
         if(zappbuck != null) {
            zappbuck.cancelcracks();
//startPR2004-11-10^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            // if on the last turn of the game
            if(curr < wordtot)
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
              zappbuck = null;
         }
     }
     if(wantend > 0 && !ending ) {
            if(wantend == 1)  {
               for(i=0;i<buckettot;++i)
                 if(b[i].btot != b[i].droptot)
                   return;
//startPR2004-11-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
               // sets the helicopter to move with the mouse
               gamePanel.moveWithMouse(heli);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
               endgame();
            }
            else --wantend;
     }
     if(ending && t > endturn && !endmessup) {
           String message;
           gamePanel.finishwith(heli);
           if(gametot1 < wordtot) message = rgame.getParm("tryagain");
           else if(gametot1 < wordtot*2) message = rgame.getParm("good");
           else if(gametot1 < wordtot*3) message = rgame.getParm("verygood");
           else     message = rgame.getParm("wonderful");
           message =  rgame.getParm("totscore")+" "+String.valueOf(gametot1)+"  -  "+message;
           showmessage(message,mover.WIDTH/6,mover.HEIGHT/16,mover.WIDTH*5/6,mover.HEIGHT/5);
           exitbutton(mover.HEIGHT/5);
           endmessup = true;
           score(8 * gametot1/(wordtot*5)  + 1);
           gamePanel.clearall();
           gamePanel.setCursor(null);
     }
//startPR2004-11-11^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       if(wantPickUp && !hadzapp && !mouseHeli){
           gamePanel.moveWithMouse(heli); // can move now
           wantPickUp = false;
       }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     return;
  }
  //-----------------------------------------------------------
  public void windowclose() {
     super.windowclose();
  }
  //-------------------------------------------------------------
  void setupWords() {
     short i,j,maxwords=0;
     if(rgame.getParm("notextended") == null
         || rgame.fullwordlist.shuffled
         || rgame.fullwordlist.wholeextended)
            words = sharkStartFrame.currPlayTopic.getHeadedWords(new String[] {rgame.gamename});
     else   words = sharkStartFrame.currPlayTopic.getHeadedWords(new String[] {rgame.gamename},rgame.w);
     headings2 = sharkStartFrame.currPlayTopic.headings;
     headings = spellchange.spellchange(headings2);
     for(i=0;i<headings.length;++i)
        headings[i] = headings[i].substring(headings[i].indexOf(':')+1);
     buckettot = (short)words.length;
     for(i=0;i<buckettot; ++i) {
        wordtot += words[i].length;
        maxwords = (short)Math.max(maxwords,words[i].length);
     }
     order = u.shuffle(u.select(wordtot,wordtot));
     bwidth = mover.WIDTH / buckettot;
     bbetween = bwidth/20;
     bbottom = mover.HEIGHT/40;
     bwordx = bbetween * 2;
     bheight = mover.HEIGHT*5/8;
     bslotheight = (bheight - bbottom)/(maxwords+1)*screenheight/mover.HEIGHT*mover.HEIGHT/screenheight;
     wordfont = sizeFont(null,"abcdefg",(bwidth - bwordx*2),bslotheight-3*mover.HEIGHT/screenwidth);
     while(true) {
        wordheight = metrics.getHeight()*mover.HEIGHT/screenheight;
        for(i=0;i<buckettot;++i) {
           for(j=0;j<words[i].length;++j) {
              maxwordwidth = Math.max(maxwordwidth, metrics.stringWidth(words[i][j].v()));
           }
        }
        if(maxwordwidth*mover.WIDTH/screenwidth > bwidth *2/3) {
//startPR2007-11-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//          metrics = gamePanel.getFontMetrics(wordfont = new Font(wordfont.getName(),
//                     wordfont.getStyle(),
//                     Math.min(wordfont.getSize()-1,
//                        wordfont.getSize() * (bwidth *2/3) / (maxwordwidth*mover.WIDTH/screenwidth))));
          metrics = gamePanel.getFontMetrics(wordfont = wordfont.deriveFont((float)
              Math.min(wordfont.getSize()-1, wordfont.getSize() * (bwidth *2/3) / (maxwordwidth*mover.WIDTH/screenwidth))));
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
           maxwordwidth = 0;
           continue;
        }
        break;
     }
     wmax = maxwordwidth*mover.WIDTH/screenwidth;
     b = new bucket[buckettot];
     GOODTIME = 400 + 200*buckettot;
  }
  //-----------------------------------------------------------
  int dropspeed() {return   mover.HEIGHT/(10-speed);}
  int sidespeed() {return    bwidth/(12-speed);}
  //-------------------------------------------------------------
  public boolean click(int x, int y) {
     if (ending) return false;     // allow click on exit
     if(gamePanel.mouseys < headingtop && currword != null && gamePanel.movesWithMouse(heli)
             && !heli.isopen && !heli.opening && !heli.closing) {
        int mid = heli.doormiddle();
        dropinto = b[mid/bwidth];
        if(mid-wmax/2 < dropinto.x+bbetween || mid+wmax/2 > dropinto.x+dropinto.w-bbetween) {
           noise.beep();
           dropinto = null;
           return true;
        }
        gamePanel.drop(heli);
        heli.open();
        if (currbuck == dropinto) {
           heli.leverok.pull(heli.opentime);
           if(!wasbad) {++good;  bad = 0; heli.man.happy((byte)2);}
           wasbad = false;

        }
        else {
           heli.leverbad.pull(heli.opentime);
           if(!wasbad) ++bad;
           wasbad = true;
           good = 0;
           heli.man.happy((byte)-2);

        }
     }
     return(true);
  }
  //--------------------------------------------------------------
  class bucket extends mover {
     bucket buck;
     word[] bwords;
     short bno,btot, droptot;
     Polygon cracks[][];
     short cracktot[];
     byte scores[];
     public bucket(short bno1) {
        super(false);
        buck = b[bno=bno1];
        w = bwidth;
        h = bheight;
        x = bno1 * bwidth;
        y = mover.HEIGHT - bheight;
        bwords = new word[words[bno1].length];
        cracks =  new Polygon[words[bno1].length][MAXCRACKS];
        cracktot =  new short[words[bno1].length];
        scores = new byte[words[bno1].length];
        if(bno1 == buckettot-1) {
           w = mover.WIDTH - x;
        }
        sayit = headings2[bno];
        addMover(this,x,y);
     }
     int yy(short bb) {
         return (y+h - bbottom - bslotheight*(bb+1))*screenheight/mover.HEIGHT*mover.HEIGHT/screenheight;
     }
     boolean addcrack(int x1) {
        if(droptot==0) return false;
        short i;
        int dx = bwidth/20;
        int y1 = yy(droptot);
        short crackno = (short)(droptot-1);
        ended = false;
        if(cracktot[crackno] < MAXCRACKS) {
           Polygon p = cracks[crackno][cracktot[crackno]++] = new Polygon();
           for(i=0;i<10;++i, y1 += bslotheight/10) {
              p.addPoint((x1 = Math.min(Math.max(x1 - dx + u.rand(dx*2+1), x), x+w))*screenwidth/mover.WIDTH,
                           y1*screenheight/mover.HEIGHT);
           }
           return true;
        }
        else {
           --droptot;
           return false;
        }
     }
     void cancelcracks() {
        for(short i = (short)btot; i<bwords.length; ++i) {
            for(short j = 0;j<cracktot[i];++j) {
               cracks[i][j] = null;
            }
            cracktot[i] = 0;
        }
     }
     public void paint(Graphics g,int x, int y, int w, int h) {
        int thick = bbetween/2*screenwidth/mover.WIDTH;
        short i,j;
        int  y1 = yy((short)0)*screenheight/mover.HEIGHT;
        int y2 = y1;
        int sloth = bslotheight*screenheight/mover.HEIGHT;
        int wordx = bwordx*screenwidth/mover.WIDTH;
        int bott = bbottom*screenheight/mover.HEIGHT;

        g.setColor(bucketcolor);
        g.fillRect(x,y,thick,h);
        g.fillRect(x,y+h-bott,w,bott);
        g.fillRect(x+w-thick,y,thick+1,h);
        g.drawLine(x,y1,x+w,y1);
        g.setColor(Color.black);

        sayitrect = new Rectangle(x,y1,w,sloth);
        g.setFont(wordfont);

        for(i=0; i<btot; ++i) {
           y1 = yy((short)(i+1)) * screenheight/mover.HEIGHT;
           if(bwords[i].pat != null && bwords[i].value.indexOf('[')<0) {
              wordlist.paintpat(bwords[i].v(),bwords[i].pat, g,
                    Color.blue, Color.red, x+wordx,
                       y1 + sloth/2 -  metrics.getHeight()/2 + metrics.getAscent() + 1);
           }
           else {
               g.setColor(Color.blue);
               u.painthighlight(bwords[i].vhi(),g,x+wordx,
                 y1 + sloth/2 -  metrics.getHeight()/2 + 1);
           }
           g.setColor(bucketcolor);
           g.drawLine(x,y1,x+w,y1);
        }
        for(i=0;i<btot;++i) {
           if(cracktot[i] > 0) {
              g.setColor(Color.white);
              for(j=0;j<cracktot[i];++j) {
                 g.drawPolyline(cracks[i][j].xpoints,cracks[i][j].ypoints,cracks[i][j].npoints);
              }
           }
        }
        for(i=0;i<MAXSMASHERS;++i) {
          if(smashers[i] != null && smashers[i].buck == this && smashers[i].y + smashers[i].h > yy(btot)) {
             g.setColor(background);
             g.fillRect(smashers[i].x*screenwidth/mover.HEIGHT, yy(btot)*screenheight/mover.HEIGHT,
                         smashers[i].w*screenwidth/mover.WIDTH,
                         (smashers[i].y +smashers[i].h - yy(btot))*screenheight/mover.HEIGHT);
          }
        }
        g.setColor(Color.black);
        g.setFont(wordfont);
        if(headings[bno].indexOf('|')>=0) {
           String[] ss =  u.splitString(headings[bno]);
           g.setFont(sizeFont(ss, (bwidth - bbetween)*screenwidth/mover.WIDTH,
                            bslotheight*screenheight/mover.HEIGHT-3 ));
           int ht = g.getFontMetrics().getHeight();
           for(i=0;i<ss.length;++i) {
              u.painthighlight(ss[i], g, x + bbetween*screenwidth/mover.WIDTH/2+1,
                  headingtop = y2 + i*ht );

           }
        }
        else {
           if(metrics.stringWidth(u.striphighlights(headings[bno])) > w - wordx*2) {
              g.setFont(sizeFont(null,g,u.striphighlights(headings[bno]), (bwidth - bbetween)*screenwidth/mover.WIDTH,
                            bslotheight*screenheight/mover.HEIGHT-3 ));
           }
           u.painthighlight(headings[bno], g, x + bbetween*screenwidth/mover.WIDTH/2+1, headingtop = y2 + sloth/2 - g.getFontMetrics().getHeight()/2+1);
        }
     }
  }
  //--------------------------------------------------------------
  class helicopter extends mover {
     boolean left;
     long lastflip = gtime();
     static final short FLIPTIME = 300;
     int lastx;
     int domeradx1 = Math.max(mover.WIDTH/20,(maxwordwidth+screenwidth/40)/2*mover.WIDTH/screenwidth);
     int domerady1 = Math.min(domeradx1*screenwidth/screenheight,(mover.HEIGHT - bheight)/4) ;
     int domeradx2 = domeradx1 * 7/8;
     int domerady2 = domerady1 * 7/8;
     int domeboty = (domerady1 >= (mover.HEIGHT - bheight)/4)?(domerady1/2):(domerady1/4);
     Color domecolor = u.darkcolor();
     Color doorcolor = u.darkcolor();
     int bladewidth = domeradx1*8/7;
     double bladeangle;
     short bladepersec=2;
     int bladey = domerady1/4;
     int doorthick = domerady1/12;
     int doory = domerady1 + domeboty + bladey;
     long timestart = gtime();
     int taillength = Math.min((bwidth-domeradx1*2)*2/3,(buckettot>4)?(domeradx1/3):domeradx1);
     int taily = domeboty;
     int tailbladewidth = Math.min(taillength/4,domeradx1/6);
     double tailbladeangle;
     short tailpersec = (short)(5+u.rand(3));
     double dooropenangle;
     head_base man = new head_base(domeradx2/3,domerady2*4/9);
     sharkpoly body = new sharkpoly(new int[] {-man.w/2,-man.neck.z, man.neck.z,man.w/2, man.w*2/3, -man.w*2/3},
                                new int[] {bladey + domerady1-man.h*3/4,bladey + domerady1-man.h, bladey + domerady1 -man.h,bladey + domerady1-man.h*3/4,doory,doory},6);
     Color bodycolor = u.fairlydarkcolor();
     long lastt = gtime();
     boolean opening,closing,leverpushed,isopen;
     long startopen, startclose;
     final short opentime = 300;
     int closetime;
     lever leverok,leverbad;

     public helicopter() {
        super(false);
        keepMoving=true;
        gamePanel.moveWithMouse(this);
        w = domeradx1*2 + taillength*2 + tailbladewidth;
        h = bladey + domerady1*2 + domeboty;
        mx = -w/2;
        my = -h/2;
        moveWithin = new Rectangle(0, 0,
                   mover.WIDTH, mover.HEIGHT - bheight - h/6);
        man.manager = gamePanel;
        leverok = new lever(-domeradx2*3/4,h*13/24, h/6,-Math.PI*3/8,-Math.PI/4,Color.black);
        leverbad = new lever(-domeradx2*7/8,h*13/24, h/6, -Math.PI*7/16,-Math.PI/4,Color.red);
     }
     void open() {
        opening = isopen=true;
        startopen = gtime;
     }
     int doormiddle() {return(x+w/2); }
     int dooryy() {return(y+doory); }
     public void paint(Graphics g,int x, int y, int w, int h) {
        long t = gtime;
        short i;
        if(!completed && gamePanel.mousey > moveWithin.y && gamePanel.mousey < moveWithin.y+moveWithin.height) {
          if(iscursor) {
             gamePanel.setCursor(sharkStartFrame.nullcursor);
             iscursor=false;
          }
        }
        else  {
           if(!iscursor) {
               gamePanel.setCursor(null);
               iscursor = true;
           }
        }

        if(Math.abs(gamePanel.mousexs-lastx)> w/16) {
              boolean oldleft = left;
              left = (gamePanel.mousexs<lastx);
              lastx=gamePanel.mousexs;
              if(left != oldleft) frontal = Math.max(frontal,gtime + FRONTALTIMETURN);
        }
        if(opening) {
           if(t > startopen+opentime) {
              opening = false;
              if (currbuck == dropinto) {
                 zappbuck = currbuck;
                 dropinto.btot = (short)Math.min(dropinto.droptot, dropinto.btot);
                 long elapsed = gtime;
                 if(currword.spokenword != null) elapsed -= currword.spokenword.endsay;
                 if(elapsed > GOODTIME * 4)  dropinto.scores[dropinto.droptot] = 1;
                 else if(elapsed < 0)  dropinto.scores[dropinto.droptot] = 5;
                 else dropinto.scores[dropinto.droptot] =  (byte)(5 - elapsed/GOODTIME);
                 dropword dd = new dropword(dropinto);
                 closing = true;
                 leverpushed = false;
                 startclose = gtime + WAITAFTERDROP;
                 closetime = MINCLOSETIME + (8 - speed) * (MAXCLOSETIME-MINCLOSETIME)/8;
                 dropinto.ended = false;
              }
              else {
                 for(i=0;i<MAXSMASHERS;++i) {
                    if(smashers[i] == null) {
                       smashers[i] = new smasher(dropinto);
                       break;
                    }
                 }
                 noise.groan();
                 error();
                 closing = true;
                 startclose = gtime + WAITAFTERDROP;
                 closetime = MINCLOSETIME;
                 leverpushed = false;
              }
//startPR2004-11-05^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
              wantPickUp = true;
//              gamePanel.moveWithMouse(this); // can move now
//              return;      // dont draw it
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
           }
           else {
              dooropenangle = Math.PI/2*(t-startopen)/opentime;
           }
        }
        else if(closing) {
           if(t > startclose+closetime) {
              closing = false;
              if(good > 2  ||    bad >= 2) frontal = gtime+FRONTALTIME;
              if(currword == null) {
//startPR2005-04-18^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                if(Demo_base.isDemo){
                  if (Demo_base.demoIsReadyForExit(0)) return;
                }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                nextword();
                if(wantend==0)
                    flip();
              }
              isopen = false;
           }
           else {
              if(t < startclose) dooropenangle = Math.PI/2;
              else {
                  if(!leverpushed) {
                     if(leverok.pulled) leverok.push(closetime);
                     else leverbad.push(closetime);
                     leverpushed = true;
                  }

                  dooropenangle = Math.PI/2 - Math.PI/2*(t-startclose)/closetime;
//                  gamePanel.moveWithMouse(this);
              }
           }
        }
        else dooropenangle = 0;
        bladeangle += (t-lastt)*bladepersec*Math.PI*2/1000;
        while(bladeangle>Math.PI*2) bladeangle -= Math.PI*2;
        tailbladeangle += (t-lastt)*tailpersec*Math.PI/2/1000;
        while(tailbladeangle>Math.PI*2) tailbladeangle -= Math.PI*2;
        if(left) {
           int xmid = x + w/2;
           int ymid = y + (bladey + domerady1)*screenheight/mover.HEIGHT;
           int domerx = domeradx1*screenwidth/mover.WIDTH;
           int domery = domerady1*screenheight/mover.HEIGHT;
           int domerx2 = domeradx2*screenwidth/mover.WIDTH;
           int domery2 = domerady2*screenheight/mover.HEIGHT;
           int domebyb = domeboty*screenheight/mover.HEIGHT;
           int dthick = doorthick*screenheight/mover.HEIGHT;
           int dx1 = xmid-domerx;
           int dx2 = dx1 - (int)(dthick*Math.sin(dooropenangle));
           int addx = (int)(domerx*Math.cos(dooropenangle));
           int dy1 = ymid+domebyb;
           int dy2 = dy1 + (int)(dthick*Math.cos(dooropenangle));
           int addy = (int)(domery*Math.sin(dooropenangle));

           man.lookleft = (frontal < gtime);
           man.lookright = false;

           Polygon door1 = new Polygon(
                         new int[]{dx1, dx1+addx, dx2+addx, dx2},
                         new int[]{dy1, dy1+addy, dy2+addy, dy2},4);
           dx1 = xmid+domerx;
           dx2 = dx1 + (int)(dthick*Math.sin(dooropenangle));
           Polygon door2 = new Polygon(
                         new int[]{dx1, dx1-addx, dx2-addx, dx2},
                         new int[]{dy1, dy1+addy, dy2+addy, dy2},4);
           int tw = taillength*screenwidth/mover.WIDTH;
           int th = taily*screenheight/mover.HEIGHT;
           int tbx = dx1 + tw*5/6;
           int tby = dy1 - th/2;
           int tbw = tailbladewidth*screenwidth/mover.WIDTH;
           int tbdx = (int)(tbw*Math.cos(tailbladeangle));
           int tbdy = (int)(tbw*Math.sin(tailbladeangle));
           int bw = (int)(bladewidth *screenwidth/mover.WIDTH * Math.cos(bladeangle));

           g.setColor(domecolor);
           g.fillArc(xmid - domerx,  ymid - domery, domerx*2,domery*2,0,180);
           g.setColor(background);
           g.fillArc(xmid - domerx2,  ymid - domery2, domerx2*2,domery2*2,0,180);

           leverok.left = true;
           leverok.paint(g,xmid*mover.WIDTH/screenwidth,y*mover.HEIGHT/screenheight);
           leverbad.left = true;
           leverbad.paint(g,xmid*mover.WIDTH/screenwidth,y*mover.HEIGHT/screenheight);

           g.setColor(bodycolor);
           g.fillPolygon(body.translate(xmid*mover.WIDTH/screenwidth,y*mover.HEIGHT/screenheight,screenwidth,screenheight));
           int army = y+body.ypoints[0]*screenheight/mover.HEIGHT;
           int armlen = domerx2*7/8;
           if(leverbad.pulled) u.drawarm(g,xmid,army,leverbad.xt,leverbad.yt,armlen,left);
           else if(leverok.pulled) u.drawarm(g,xmid,army,leverok.xt,leverok.yt,armlen,left);
           else u.drawarm(g,xmid,army,(leverok.xt*3+xmid)/4,(leverbad.yt*2+ymid)/3,armlen,left);
                       man.x = xmid*mover.WIDTH/screenwidth - man.w/2;
           man.x = xmid*mover.WIDTH/screenwidth - man.w/2;
           man.y = ymid*mover.HEIGHT/screenheight - man.h*2;
           man.paint(g,man.x*screenwidth/mover.WIDTH, man.y*screenheight/mover.HEIGHT,
                     man.w*screenwidth/mover.WIDTH,man.h*screenheight/mover.HEIGHT);


           g.setColor(domecolor);
           g.fillRect(xmid - domerx,  ymid, domerx*2,domebyb);
           g.fillRect(dx1,dy1 - th, tw, th);
           g.setColor(Color.black);
           g.drawLine(xmid, y, xmid, ymid - domery);
           g.setColor(Color.white);
           g.drawLine(xmid - bw,y,xmid+bw,y);
           g.drawLine(tbx-tbdx, tby-tbdy, tbx+tbdx, tby+tbdy);
           g.drawLine(tbx+tbdy, tby-tbdx, tbx-tbdy, tby+tbdx);
           g.setColor(doorcolor);
           g.fillPolygon(door1);
           g.fillPolygon(door2);
        }
        else  {
           int xmid = x + w/2;
           int ymid = y + (bladey + domerady1)*screenheight/mover.HEIGHT;
           int domerx = domeradx1*screenwidth/mover.WIDTH;
           int domery = domerady1*screenheight/mover.HEIGHT;
           int domerx2 = domeradx2*screenwidth/mover.WIDTH;
           int domery2 = domerady2*screenheight/mover.HEIGHT;
           int domebyb = domeboty*screenheight/mover.HEIGHT;
           int dthick = doorthick*screenheight/mover.HEIGHT;
           int dx1 = xmid-domerx;
           int dx2 = dx1 - (int)(dthick*Math.sin(dooropenangle));
           int addx = (int)(domerx*Math.cos(dooropenangle));
           int dy1 = ymid + domebyb;
           int dy2 = dy1 + (int)(dthick*Math.cos(dooropenangle));
           int addy = (int)(domery*Math.sin(dooropenangle));
           Polygon door1 = new Polygon(
                         new int[]{dx1, dx1+addx, dx2+addx, dx2},
                         new int[]{dy1, dy1+addy, dy2+addy, dy2},4);
           dx1 = xmid+domerx;
           dx2 = dx1 + (int)(dthick*Math.sin(dooropenangle));
           Polygon door2 = new Polygon(
                         new int[]{dx1, dx1-addx, dx2-addx, dx2},
                         new int[]{dy1, dy1+addy, dy2+addy, dy2},4);
           dx1 = xmid-domerx;
           int tw = taillength*screenwidth/mover.WIDTH;
           int th = taily*screenheight/mover.HEIGHT;
           int tbx = dx1 - tw*5/6;
           int tby = dy1 - th/2;
           int tbw = tailbladewidth*screenwidth/mover.WIDTH;
           int tbdx = (int)(tbw*Math.cos(tailbladeangle));
           int tbdy = (int)(tbw*Math.sin(tailbladeangle));
           int bw = (int)(bladewidth *screenwidth/mover.WIDTH * Math.cos(bladeangle));

           g.setColor(domecolor);
           g.fillArc(xmid - domerx,  ymid - domery, domerx*2,domery*2,0,180);
           g.setColor(background);
           g.fillArc(xmid - domerx2,  ymid - domery2, domerx2*2,domery2*2,0,180);

           leverok.left = false;
           leverok.paint(g,xmid*mover.WIDTH/screenwidth,y*mover.HEIGHT/screenheight);
           leverbad.left = false;
           leverbad.paint(g,xmid*mover.WIDTH/screenwidth,y*mover.HEIGHT/screenheight);

           g.setColor(bodycolor);
           g.fillPolygon(body.translate(xmid*mover.WIDTH/screenwidth,y*mover.HEIGHT/screenheight,screenwidth,screenheight));

           int army = y+body.ypoints[0]*screenheight/mover.HEIGHT;
           int armlen = domerx2*7/8;
           if(leverbad.pulled) u.drawarm(g,xmid,army,leverbad.xt,leverbad.yt,armlen,left);
           else if(leverok.pulled) u.drawarm(g,xmid,army,leverok.xt,leverok.yt,armlen,left);
           else u.drawarm(g,xmid,army,(leverok.xt*3+xmid)/4,(leverbad.yt*2+ymid)/3,armlen,left);

           man.lookleft = false;
           man.lookright = (frontal < gtime);
           man.x = xmid*mover.WIDTH/screenwidth - man.w/2;
           man.y = ymid*mover.HEIGHT/screenheight - man.h*2;
           man.paint(g,man.x*screenwidth/mover.WIDTH, man.y*screenheight/mover.HEIGHT,
                     man.w*screenwidth/mover.WIDTH,man.h*screenheight/mover.HEIGHT);

           g.setColor(domecolor);
           g.fillRect(xmid - domerx,  ymid , domerx*2,domebyb);
           g.fillRect(dx1-tw,dy1-th, tw, th);
           g.setColor(Color.black);
           g.drawLine(xmid, y, xmid, ymid - domery);
           g.setColor(Color.white);
           g.drawLine(xmid - bw,y,xmid+bw,y);
           g.drawLine(tbx-tbdx, tby-tbdy, tbx+tbdx, tby+tbdy);
           g.drawLine(tbx+tbdy, tby-tbdx, tbx-tbdy, tby+tbdx);
           g.setColor(doorcolor);
           g.fillPolygon(door1);
           g.fillPolygon(door2);
        }
        lastt = gtime;
     }
  }
  //------------------------------------------------------------
  class smasher extends mover {
     int headradx=mover3d.BASEU/60 + u.rand(mover3d.BASEU/200);
     int headrady=(headradx*3/2+u.rand(headradx/6))*screenwidth/screenheight;
     double bodyangle;
     bucket buck;
     short currslot;
     double cracktime;
     int bodytop = mover.WIDTH/30;
     int bodybot = bodytop/2;
     int bodyheight = bodytop*3/4*screenwidth/screenheight;
     int leglen = bodyheight;
     int thighlen = leglen/2+bodyheight/3;
     int calflen = leglen/2;
     int calfthick =bodybot/4;
     int footlen = leglen/5;
     int footthick = leglen/7;
     int crotchy = bodyheight/3;
     int handlelen;
     int handlethick = Math.max(1,bodyheight/8);
     int axew = bodytop/4, axeh = bodyheight;
     double thighangle1,thighangle2,calfangle1,calfangle2;
     double axeangle;
     boolean axeleft,axemoving;
     int armthick = bodytop/4;
     int armlen = bodytop;
     int eyew=headradx/12 + u.rand(headradx/12);
   //  int eyeh=headrady/20 + u.rand(headrady/20);
     int eyex=headradx/8 + u.rand(headradx/8);
     int eyey= headrady/3 + headrady/10+u.rand(headrady/5);
     int mouthw = headradx/6+u.rand(headradx/12);
     int mouthy = headrady*4/5;
     Color eyecolor = u.eyecolor();
     Color mouthcolor = u.mouthcolor();
     Color bodycolor = u.darkcolor();
     Color legcolor = u.darkcolor();
     int targetx,targety;
     Color haircolor = u.haircolor();
     short hairtot = (short)(8 + u.rand(4));
     sharkpoly hairp[] = new sharkpoly[hairtot];
                           // multiples of PI/8/100
     randrange_base thigha1 = new randrange_base(400,500,2000);
     randrange_base thigha2 = new randrange_base(300,400,2000);
     randrange_base calfa1 = new randrange_base(0,200,2000);
     randrange_base calfa2 = new randrange_base(0,200,2000);
     randrange_base bodya = new randrange_base(-100,100,800);
     randrange_base axea = new randrange_base(-80,100,2000);
     randrange_base mouth = new randrange_base(-100,100,100);

     public smasher(bucket bu) {
        super(false);
        short i;
        axea.highspeed=true;
        h = headrady + bodyheight + thighlen + calflen+footthick + mover.HEIGHT*3/screenheight;
        handlelen = (int)((h - axeh*3/2)/Math.sqrt(2));
        w = (handlelen+axew*2)*2;
        while(w > bwidth - bwordx*3) {
          handlelen -= mover.WIDTH/screenwidth;
          w = (handlelen+axew*2)*2;
        }
        w += mover.WIDTH/screenwidth;
        buck = bu;
        axeleft = (u.rand(2) == 0);
        for(i=0;i<hairtot;++i) {
           int xx = -headradx/2 + headradx*i/(hairtot-1);
           hairp[i] = new sharkpoly(new int[]{xx,xx},
                    new int[]{headrady/6+u.rand(headrady/12), headrady/3+u.rand(headrady/12)},2);
        }
        addMover(this,heli.doormiddle() - w/2, Math.min(heli.dooryy(),mover.HEIGHT - bheight-h));
        targety =  buck.yy(buck.droptot) - h - mover.HEIGHT/screenheight;
        targetx = buck.x + bwordx;
        currslot = buck.droptot;
        if(x < targetx)
            moveto(targetx, mover.HEIGHT - bheight-h, 200);
        else if(x + w > buck.x + bwidth - bwordx)
            moveto(buck.x + bwidth - bwordx - w, mover.HEIGHT - bheight-h,200);
        else moveto(x,targety,(targety - y)*1000/dropspeed());
     }
     public boolean endmove() {
        if(y != targety) {
           moveto(x,targety,(targety - y)*1000/dropspeed());
           return false;
        }
        if(buck.droptot > 0)   {
           keepMoving = true;
           axemoving = true;
           ended = false;
           cracktime = gtime + MINCRACKTIME + u.rand(MAXCRACKTIME-MINCRACKTIME);
        }
        else {
           if(targety >= mover.HEIGHT-3){
              buck.ended = false;
              return true;
           }
           else {
              axemoving = true;
              moveto(x, targety=mover.HEIGHT-2,(mover.HEIGHT-x)*200/dropspeed());
           }
        }
        return false;
     }
     public void paint(Graphics g,int x1, int y1, int w1, int h1) {
        sharkpoly p = new sharkpoly(), p2 = new sharkpoly(),axep = new sharkpoly();
        int xmid = (x+w/2);
        int ymid = (y+h/2);
        long t = gtime;
        int bodyy = y + headrady;
        int eyeww = eyew*screenwidth/mover.WIDTH;
        int armthicky = armthick*screenwidth/screenheight;
        axeangle = Math.PI/4*axea.next(t)/100  ;
        if(axemoving) {
           if(currslot > buck.droptot) {
                 keepMoving = false;
                 moveto(x,targety = y + bslotheight, bslotheight*1000/dropspeed());
                 --currslot;
           }
           if(buck.droptot>0 && t > cracktime && axeangle > Math.PI/6) {
              if(!buck.addcrack(x + w/10+u.rand(w/10) + (axeleft?0:(w*7/10)))) {
                 keepMoving = false;
                 moveto(x,targety = y + bslotheight, bslotheight*1000/dropspeed());
                 --currslot;
              }
              else {
                 cracktime = gtime + MINCRACKTIME + u.rand(MAXCRACKTIME-MINCRACKTIME);
                 axeleft = !axeleft;
              }
           }
        }
        if(axeleft) axeangle = Math.PI - axeangle;
        thighangle1 = thigha1.next(t)*Math.PI/8/100;
        thighangle2 = thigha2.next(t)*Math.PI/8/100;
        calfangle1 = thighangle1 - calfa1.next(t)*Math.PI/8/100;
        calfangle2 = thighangle2 + calfa2.next(t)*Math.PI/8/100;
        bodyangle = bodya.next(t)*Math.PI/8/100;
        p.addPoint(xmid+ bodybot/2, bodyy + bodyheight);
        p.addPoint(xmid + bodytop/2, bodyy);
        p.addPoint(xmid - bodytop/2, bodyy);
        p.addPoint(xmid - bodybot/2, bodyy + bodyheight);
        p2.addPoint(xmid - bodybot/2, bodyy + bodyheight);
        p2.add(thighlen, thighangle1);
        p2.add(calflen, calfangle1);
        p2.add(footlen, calfangle1+Math.PI/2);
        p2.add(footthick, calfangle1);
        p2.add(footlen+calfthick, calfangle1-Math.PI/2);
        p2.add(calflen+footthick, calfangle1+Math.PI);
        p2.addPoint(xmid, y+headrady+bodyheight+crotchy);
        p2.addBackwards(xmid + bodybot/2, bodyy+bodyheight);
        p2.add(thighlen, thighangle2);
        p2.add(calflen, calfangle2);
        p2.add(footlen, calfangle2-Math.PI/2);
        p2.add(footthick, calfangle2);
        p2.add(footlen+calfthick, calfangle2+Math.PI/2);
        p2.add(calflen+footthick, calfangle2+Math.PI);

        g.setColor(bodycolor);
        g.fillPolygon(p.rotate(xmid,ymid,bodyangle,screenwidth,screenheight));
        g.setColor(legcolor);
        g.fillPolygon(p2.rotate(xmid,ymid,bodyangle,screenwidth,screenheight));

        sharkpoly headp = new sharkpoly(new int[]{xmid - headradx/2,
                                              xmid + headradx/2,
                                              xmid + headradx/2,
                                              xmid,
                                              xmid - headradx/2},
                                      new int[]{y+headrady/3,
                                                y+headrady/3,
                                                y+headrady*2/3,
                                                y+headrady,
                                                y+headrady*2/3}, 5);

        g.setColor(Color.white);
        g.fillPolygon(headp.rotate(xmid,ymid,bodyangle,screenwidth,screenheight));
        g.setColor(haircolor);
        for(short i=0;i<hairtot;++i) {
         hairp[i].tranrotate(xmid,y,xmid,ymid,bodyangle,
                                  screenwidth,screenheight).fit(x1,y1,w1,h1).drawline(g);
        }
        g.setColor(eyecolor);
        Point eyept = rotate(xmid - eyex, y + eyey, xmid,ymid,bodyangle,screenwidth,screenheight);
        g.fillOval(eyept.x-eyeww/2, eyept.y-eyeww/2, eyeww, eyeww);
        eyept = rotate(xmid + eyex, y + eyey, xmid,ymid,bodyangle,screenwidth,screenheight);
        g.fillOval(eyept.x-eyeww/2, eyept.y-eyeww/2, eyeww, eyeww);
        g.setColor(mouthcolor);
        int mouthh = mouth.next(t)*headrady/10/100;
        sharkpoly mp = (new sharkpoly(new int[]{xmid-mouthw,xmid-mouthw/2,xmid,xmid+mouthw/2,xmid+mouthw},
                                     new int[]{y+mouthy+mouthh,y+mouthy+mouthh/3,y+mouthy,y+mouthy+mouthh/3,y+mouthy+mouthh},5))
                                     .rotate(xmid,ymid,bodyangle,screenwidth,screenheight);
        g.drawPolyline(mp.xpoints,mp.ypoints,5);
        g.setColor(Color.orange);
        g.fillPolygon(sharkpoly.rect(xmid,ymid,handlelen,handlethick,xmid,ymid,axeangle,screenwidth,screenheight));
        g.setColor(Color.gray);
        g.fillPolygon(sharkpoly.rect(xmid+handlelen,ymid+handlethick/2-axeh/2,axew,axeh,xmid,ymid,axeangle,screenwidth,screenheight).fit(x1,y1,w1,h1));
        g.setColor(bodycolor);
        int xarmout = bodytop/2 +(int)(bodytop/3*(h/2 + handlelen*Math.sin(axeangle))/h);
        if(axeleft) {
           g.fillPolygon((new sharkpoly(new int[]{xmid+bodytop/2, xmid+xarmout,xmid, xmid+xarmout-armthick/2,xmid+bodytop/2-armthick},
                                    new int[]{bodyy, ymid,ymid, ymid-armthicky/2,bodyy},5)). rotate(xmid,ymid,bodyangle,screenwidth,screenheight));
           sharkpoly armp =(new sharkpoly(new int[]{xmid-bodytop/2, 0,xmid-bodytop/2+armthick,xmid+bodytop/2-armthick},
                                     new int[]{bodyy, 0, bodyy},3)). rotate(xmid,ymid,bodyangle,screenwidth,screenheight);
           armp.xpoints[1] = (xmid+(int)(handlelen*2/3*Math.cos(axeangle)))*screenwidth/mover.WIDTH;
           armp.ypoints[1] = (ymid+(int)(handlelen*2/3*Math.sin(axeangle)))*screenheight/mover.HEIGHT;
           g.fillPolygon(armp);
        }
        else {
          g.fillPolygon((new sharkpoly(new int[]{xmid-bodytop/2, xmid-xarmout,xmid, xmid-xarmout+armthick/2,xmid-bodytop/2+armthick},
                                    new int[]{bodyy, ymid,ymid, ymid-armthicky/2,bodyy},5)). rotate(xmid,ymid,bodyangle,screenwidth,screenheight));
           sharkpoly armp =(new sharkpoly(new int[]{xmid+bodytop/2, 0,xmid+bodytop/2-armthick,xmid+bodytop/2-armthick},
                                     new int[]{bodyy, 0, bodyy},3)). rotate(xmid,ymid,bodyangle,screenwidth,screenheight);
           armp.xpoints[1] = (xmid+(int)(handlelen*2/3*Math.cos(axeangle)))*screenwidth/mover.WIDTH;
           armp.ypoints[1] = (ymid+(int)(handlelen*2/3*Math.sin(axeangle)))*screenheight/mover.HEIGHT;
           g.fillPolygon(armp);
        }
     }
  }
  //---------------------------------------------------------------
  class dropword extends mover {
     word wd;
     String wds,wdhi;
     bucket buck;
     int targetx,targety,dx;
     public dropword(bucket bu) {
        super(false);
        buck = bu;
        wd = currword;
        currword = null;
        w = (metrics.stringWidth(wds = wd.v())+6)*mover.WIDTH/screenwidth;
        dx = 3;
        wdhi = wd.vhi();
        h = wordheight;
        addMover(this,heli.doormiddle() - w/2, heli.dooryy());
        targety =  ((buck.yy(++buck.droptot)
                      + bslotheight/2 -  wordheight/2)*screenheight/mover.HEIGHT + 1) * mover.HEIGHT/screenheight;
        targetx = buck.x + bwordx;
        if(x < targetx)
            moveto(targetx, mover.HEIGHT - bheight-h,1000);
        else {
           int itargety = (y*5+targety)/6;
           moveto(x,itargety,(itargety - y)*1000/dropspeed());
        }
//        else if(x + w > buck.x + bwidth - bwordx)
//            moveto(buck.x + bwidth - bwordx-w, mover.HEIGHT - bheight-h,1000);
//        else moveto(x,targety,(targety - y)*1000/dropspeed());
     }
     public boolean endmove() {
        if(y != targety) {
           moveto(x,targety,(targety - y)*1000/dropspeed());
           return false;
        }
        if(x != targetx)  {
           moveto( targetx,targety,(x-targetx)*1000/sidespeed());
           return false;
        }
        else {
           buck.bwords[buck.btot++] = wd;
           buck.ended = false;
           return true;
        }
     }
     public void paint(Graphics g,int x1, int y1, int w1, int h1) {
         if(shark.macOS) ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setFont(wordfont);
        if(wd.pat != null && wdhi.indexOf('[') < 0) {
           wordlist.paintpat(wds, wd.pat, g,
                    Color.blue, Color.red, x1+2, y1 + metrics.getMaxAscent());
        }
        else {
           g.setColor(Color.blue);
           u.painthighlight(wdhi,g,x1+2,y1);
        }
     }
  }
  //-------------------------------------------------------------
  class turner extends mover {
     Image before,after;
     Graphics g;
     byte[] clip;
     long begin;
     public turner(short buckno,short colno) {
        super(false);
        int x1,y1,w1,h1;
        x = b[buckno].x+bbetween/2;
        x1 = x*screenwidth/mover.WIDTH;
        y = b[buckno].yy((short)(colno+1));
        y1 = y*screenheight/mover.HEIGHT+2;
        y = y1*mover.HEIGHT/screenheight;
        w = bwidth-bbetween;
        w1 = w*screenwidth/mover.WIDTH;
        h = bslotheight;
        h1 = h*screenheight/mover.HEIGHT;
        h = h1*mover.HEIGHT/screenheight;
        before = gamePanel.createImage(w1, h1);
        before.getGraphics().drawImage(gamePanel.offscreen[gamePanel.curroffscreen], 0,0,w1,h1, x1,y1,x1+w1,y1+h1,gamePanel);
        after = gamePanel.createImage(w1, h1);
        g = after.getGraphics();
        g.setColor(scorecolors[b[buckno].scores[colno] - 1]);
        g.fillRect(0,0,w1,h1);
        g.setFont(wordfont);
        g.setColor(white());
        g.drawString(rgame.getParm("score")  + " " + String.valueOf(b[buckno].scores[colno]),
                        10, h1/2-metrics.getHeight()/2+metrics.getAscent());
        begin = gtime+TURNTIME*turntot;
        keepMoving = true;
        dontclear = true;
        addMover(this,x,y);
     }
     public void paint(Graphics g,int x1, int y1, int w1, int h1) {
        int d1,i;
        if(gtime>=begin+TURNTIME) d1 = 0;
        else if(gtime < begin) d1 = h1;
        else d1 = h1  - (int)(h1 * (gtime-begin)/TURNTIME);
        if(d1 >= h1) {
            g.drawImage(before,x1,y1,x1+w1,y1+h1,0,0,w1,h1,gamePanel);
        }
        else if (d1 == 0) {
           g.drawImage(after,x1,y1,x1+w1,y1+h1,0,0,w1,h1,gamePanel);
            if(clip != null) {clip=null;}
            keepMoving = false;
        }
        else {
           if(clip==null && !nonoise) {
              clip = noise.turnover();
             if(gtime() > spokenWord.endsay) {
               spokenWord.say(clip);
             }
           }
           for(i = 0; i < d1; ++i) {
              g.drawImage(before,x1,y1+i,x1+w1,y1+i+1,
                              0,i*h1/d1,w1,1,gamePanel);
           }
           for(; i < h1; ++i) {
              g.drawImage(after,x1,y1+i,x1+w1,y1+i+1,
                              0,(i-d1)*h1/(h1-d1),w1,1,gamePanel);
           }
        }
     }
  }
  //--------------------------------------------------------------------
  class lever {
     Color color;
     int xc, yc, xt,yt,len,  rad,thick;
     boolean left,pulled;
     long stoppull,stoppush,startmove;
     double currangle,angle1,angle2;
     public lever(int x1, int y1,  int len1, double a1, double a2, Color col) {
        xc = x1;
        yc = y1;
        angle1 = a1;
        angle2 = a2;
        len = len1;
        color = col;
        thick = len/8;
        rad = len/5;
     }
     void pull(long t) {stoppull = gtime + t;}
     void push(long t) {stoppush = gtime + t;}
     void paint(Graphics g,int x, int y) {
        double currangle;
        int knobrad = rad*screenheight/mover.HEIGHT;
        int len1 = len*screenheight/mover.HEIGHT;
        int xc1 = (left?(x+xc):(x-xc))*screenwidth/mover.WIDTH;
        int yc1 = (y+yc)*screenheight/mover.HEIGHT;

        pulled = true;
        if (gtime<stoppull) currangle = angle1+(angle2-angle1)*(gtime - startmove)/(stoppull-startmove);
        else if (gtime<stoppush) currangle = angle2+(angle1-angle2)*(gtime - startmove)/(stoppush-startmove);
        else if(stoppush>=stoppull) {currangle = angle1; pulled = false;}
        else currangle = angle2;
        yt = yc1 + (int)(len1*Math.sin(currangle));
        g.setColor(color);
        if(left) {
           xt = xc1 + (int)(len1*Math.cos(currangle));
        }
        else {
           xt = xc1 - (int)(len1*Math.cos(currangle));
        }
        thickline(g,xc1,yc1,xt,yt,thick*screenheight/mover.HEIGHT);
        g.fillOval(xt - knobrad, yt  - knobrad, knobrad*2, knobrad*2);
     }
  }
}
