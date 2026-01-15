package shark.games;

import java.util.*;
import java.awt.*;
import shark.*;
//startPR2004-10-06^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
import java.awt.print.*;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR


/**
 * <p>Title: WordShark</p>
 * <p>Description: Creates the game word search</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: WhiteSpace</p>
 * @author Roger Burton
 * @version 1.0
 */
//startPR2004-10-06^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
// Printable implemented for Macintosh computers
public class wordsearch
    extends sharkGame
    implements Printable {
    static String rudewords[];
    static int rudelen,currwordno;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    /**
     * Holds the letters for the word search grid
     */
    String sq[];
    /**
     * Holds the sequential number of the word occupying this space
     */
    int wordno[];
  /**
   * Indicates whether the corresponding square has a letter. The number indicates
   * whether it is start, end or middle of a word and whether it is horizontal,
   * diagonal or vertical.
   */
  short dir[],markdir[];
  /**
   * Temporary variable to hold flag indicating whether the current letter is the
   * start, end or middle of a word and whether it is horizontal, diagonal or vertical
   */
  short tempm[],tempdir;
  /**
   * List of words for use in the game.
   */
  slist wlist[];
  /**
   * Number of words in use
   */
  short wtot;
  /**
   * Letters for the game
   */
  squareletter letter[];
  /**
   * Indicates the letter in the grid on which a click has occurred
   */
  short startMark = -1;
  /**
   * Number of squares in the grid
   */
  short sqtot;
  /**
   * logical x gap between the letters in the grid
   */
  int sqstepx;
  /**
  * logical y gap between the letters in the grid
  */
  int sqstepy;
  /**
   * Space required for a word in the list in use when going across.
   */
  byte across;
  /**
  * Space required for a word in the list in use when going down.
  */
  byte down;
  byte acrosstot;
  byte downtot,diagtot;
  /**
   * The student has the horizontal option set.
   */
  boolean horizontal;
 /**
 * The student has the vertical option set.
 */
  boolean  vertical;
 /**
 * The student has the diagonal option set.
 */

  boolean  diagonal;
  boolean showasfound;     // words to be added as they are found

  Color background2;
  /**
   * Mover containing display of currently selected letters.
   */
  showcurr showit;
  /**
   * Mover containing updated display of currently selected letters
   */
  showcurr newshowit;

  static final short HORIZ = 1;
  static final short VERT = 2;
  static final short DIAG = 4;

  /**
   * Start of a horizontal word
   */
  static final short STARTH = 1;
  /**
   * Middle of a horizontal word
   */
  static final short MIDH = 2;
  /**
   * End of a horizontal word
   */
  static final short ENDH = 4;
  /**
   * start pf a vertical word
   */
  static final short STARTV = 8;
  /**
   * Middle of a vertical word
   */
  static final short MIDV = 16;
  /**
   * End of a vertical word
   */
  static final short ENDV = 32;
  /**
   * Start of a diagonal word
   */
  static final short STARTD = 64;
  /**
   * Middle of a diagonal word
   */
  static final short MIDD = 128;
  /**
   * End of a diagonal word
   */
  static final short ENDD = 256;
  /**
   * Left side y coordinate of the grid (in logical units).
   */
  int  BOUNDY1 = mover.HEIGHT / 40;
  static final int  BORDER = mover.HEIGHT / 40;
  /**
  * Right side y coordinate of the grid (in logical units).
  */
  int  BOUNDY2 = mover.HEIGHT - BOUNDY1;
  /**
   * Left side x coordinate of the grid (in logical units).
   */
  int  BOUNDX1;
  /**
   * Right side x coordinate of the grid (in logical units).
   */
  int  BOUNDX2;
  int BOXDIMX,BOXDIMY,PICDIMY;
  /**
   * <li> The top panel is built.
   * <li> Sets up help panel for the game.
   * <li> Starts the game's set up method.
   */
//startPR2004-10-06^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  /**
   * allows skipping of the buffer's graphics context during mac printing
   */
  private int lastPage = -1;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  Font listfont;
  FontMetrics listm;
  int listheight;
  int listx;
  String list[];
  int notinlist[];
  String combos[] = new String[0];
  short foundpos[];
  short founddir[];
  short foundlen[];
  String inits[];
  short initpos[];
  short initdir[];
  short initlen[];
  short endcount=-1;

  public wordsearch() {
    errors = true;
    gamescore1 = true;
    wantsPrint = true;
    wantspeed=false;
    optionlist = new String[] {
        "wordsearch-horiz", "wordsearch-vert", "wordsearch-diag","wordsearch-showasfound"};
    buildTopPanel();
    help("help_wordsearch");
    if (sharkStartFrame.wanthelp) {
      BOUNDY1 = mover.HEIGHT / 10;
      BOUNDY2 = mover.HEIGHT - BOUNDY1;
    }
    if(rudewords==null) {
      rudewords = u.splitString(rgame.getParm("rudewords"));
      for(int i=0;i<rudewords.length;++i) {
        rudelen = Math.max(rudelen,rudewords[i].length());
      }
    }
    setup1();
//startSS^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
    if ( (sharkStartFrame.switchOptions == 1) ||
        (sharkStartFrame.switchOptions == 2)) {
      gamePanel.detectEnterSpace = true;
      sharkGame.focusInOrder = false; //2-d array  in use
      gamePanel.SwitchSetUp();
    }
//endSS^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
    if(shark.doImageScreenshots){
        this.setTitle(" ");
        gamePanel.removeAllMovers();
    }
  }
  
  
    /**
     * Sets up the game
     * <li> Gets the word list.
     * <li>Builds the square.
     * <li>Fills the square up with letters.
     * <li>Creates the background.
     * <li>Adds the arrows indicating the direction(s) the game is being played in.
     * <li>Adds the list of words to be found.
     */
    void setup1() {
    horizontal = options.option(optionlist[0]);
    vertical = options.option(optionlist[1]);
    diagonal = options.option(optionlist[2]);
    showasfound = options.option(optionlist[3]);
    if(!horizontal && !vertical && !diagonal) {
       horizontal = true;
    }
    markoption();
    buildlist();
    do {
      buildsquare();
      fillUpSquare();
    }  while (rudeword());
    makeBackground2();
    BOUNDX2 = mover.WIDTH*15/16;
    BOUNDX1 = BOUNDX2 - BOUNDY2*screenheight/screenwidth;
    if(sharkStartFrame.wanthelp) {
       BOUNDX2 = mover.WIDTH*31/32;
       BOUNDX1 = BOUNDX2 - (BOUNDY2-BOUNDY1)*screenheight/screenwidth;
    }
    BOXDIMY = Math.min(mover.HEIGHT/8,BOUNDX1/2*screenwidth/screenheight);
    BOXDIMX = BOXDIMY*screenheight/screenwidth;
    PICDIMY = Math.min(mover.HEIGHT/4,BOUNDX1*screenwidth/screenheight);
    sizeFont();
    sizelistfont();
//startSS^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
    pointSwitch1 = new Point[across][down]; //Get array ready for use with switches
//endSS^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
    addletters();
    addMover(new directions(BOXDIMX,BOXDIMY),BOUNDX1/2-BOXDIMX/2,0);
    if(!showasfound) addlist();
    else {
      list = new String[] {"?"};
      notinlist = new int[0];
      new listm();
      new sbutton();
    }
  }
 //--------------------------------------------------------------
 public void restart() {
   super.restart();
   startMark = -1;
   wtot = sqtot = 0;
   list = null;
   setup1();
 }
 /**
  *
  */
 public void sizeFont() {
     int ht = (BOUNDY2 - BOUNDY1 - BORDER*2)*screenheight/mover.HEIGHT;
     int fsiz;
     int i;
     if(phonicsw && combos.length>0)     wordfont = sizeFont(combos,ht/down*3/4,ht/down*combos.length);
     else wordfont = sizeFont(new String[]{"w"},ht/down,ht/down);
     metrics = getFontMetrics(wordfont);
     sqstepx = sqstepy = ht/down;
     originalWordfont = wordfont;
     originalScreenwidth = screenwidth;
     originalScreenheight = screenheight;
 }
 void sizelistfont() {
     String ss[];
     if (list != null) ss = list;
     else {
       ss = new String[wtot];
       for (int i = 0; i < wtot; ++i)
         ss[i] = rgame.w[wlist[i].wordnumber].v();
     }
     listfont = sizeFont(ss, BOUNDX1*screenwidth/mover.WIDTH,
               (listheight = mover.HEIGHT - BOXDIMY - PICDIMY)*screenheight/mover.HEIGHT);
     if(listfont.getSize() > wordfont.getSize()) listfont = wordfont;
     listm = getFontMetrics(listfont);
     listx = BOUNDX1/2 - u.getdim(ss,listm,0).width/2*mover.WIDTH/screenwidth;
 }
    /**
     * Builds a list of words to be used with the game.
     */
  void buildlist() {
     short i;
     wlist = new slist[rgame.w.length];
     for (i=0;i<rgame.w.length; ++i) {
        if(rgame.w[i].v().length()>1){
          wlist[wtot++] = new slist(i);
        }
     }
  }
  /**
   * Adds the list of words to the frame.
   */
  void addlist() {
     int incy = (listheight) / wtot, x = listx, y  = BOXDIMY;
     for(short i=0; i<wtot; ++i) {
        wlist[i].h = incy;
        wlist[i].w = listm.stringWidth(rgame.w[wlist[i].wordnumber].v()) *mover.WIDTH/screenwidth;
        addMover(wlist[i],x,y);
        y += incy;
     }
  }
  /**
   * <li>Updates the display of currently selected letters if this has altered during drawing
   * <li>Shows the sprite when it is not over the grid. If it is over the grid hides
   * the sprite.
   * @param t Not used in this overriding of the afterDraw method
   */
  public void afterDraw(long t){
     if(newshowit != showit) {
        if(showit != null) {removeMover(showit); showit=null;}
        if(newshowit != null) {
           addMover(newshowit,newshowit.x,newshowit.y);
        }
        showit = newshowit;
        gamePanel.copyall = true;
     }
  }
  /**
   * Fits words into the square
   */
  void buildsquare () {
     String s[];
     short o[] = u.shuffle(u.select(wtot,wtot));
     acrosstot=0;downtot=0;diagtot=0;
     short i, j,repeats = 0;
     down = across = (byte)Math.max(8,Math.max(rgame.w.length+2,word.maxlen(rgame.w)+4));
     outer: while(true) {
        sqtot = (short)(across*down);
        sq = new String[sqtot];
        wordno = new int[sqtot];
        dir = new short[sqtot];
        for(i=0;i<sqtot;++i) {
          sq[i] = null;
          dir[i]=0;
        }
        foundpos = new short[0];
        founddir = new short[0];
        foundlen = new short[0];
        inits = new String[0];
        initpos = new short[0];
        initdir = new short[0];
        initlen = new short[0];
        for (i=0;i<wtot;++i) {
          word ww =  rgame.w[currwordno=wlist[o[i]].wordnumber];
          if(phonicsw) {
            s = u.splitString(ww.phsplit(),u.phonicsplit);
            for(j=0;j<s.length;++j) if(s[j].length()>1) combos = u.addStringSort(combos,s[j]);
          }
          else s = u.splitsingles(ww.v());
          if(!fitword(s)) {
              if(++repeats < 20)
                continue outer;
              else {
                ++across;
                ++down;
                repeats = 0;
                continue outer;
              }
          }
        }
        break;
     }
     tempm = new short[sqtot];
     for(i=0;i<sqtot;++i)
       tempm[i] = dir[i]=0;
  }
  /**
   * <li>Adds the movers for the letters to the frame.
   * <li>If switches are in use causes the positions of the movers to be stored for use when focusing
   */
  void addletters() {
    addMover(new box(), BOUNDX1, BOUNDY1);
    letter = new squareletter[sqtot];
    for (short i = 0; (short) (sqtot - 1) >= i; ++i) {
      int acrossPosition = BOUNDX1 + BORDER +
          sqstepx * (i % across) * mover.WIDTH / screenwidth;
      int downPosition = BOUNDY1 + BORDER +
          sqstepy * (i / across) * mover.HEIGHT / screenheight;
      addMover(letter[i] = new squareletter(i), acrossPosition, downPosition);
//startSS^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
      if ( (sharkStartFrame.switchOptions == 1) ||
          (sharkStartFrame.switchOptions == 2)) {
        setSwitchPosition(acrossPosition + (letter[i].w / 2),
                          downPosition + (letter[i].h / 2), i);
      }
//endSS^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
    }
  }
  /**
   * @param s Word to be fitted into word search square.
   * @return True if word has been fitted in otherwise false.
   */
  boolean fitword(String s[]) {
    short trylist[] = new short[3];
    short trytot = 0;
    short i, j, q;
    boolean hadh=false,hadv=false,hadd=false;
     String s2;
     short pos[]=new short[across-2],postot;
     short o[] = u.shuffle(u.select(sqtot,sqtot));

    if (horizontal && acrosstot <= downtot && acrosstot <= diagtot) {
      trylist[trytot++] = HORIZ;
      hadh = true;
    }
    if (vertical && downtot <= acrosstot && downtot <= diagtot) {
      trylist[trytot++] = VERT;
      hadv = true;
    }
    if (diagonal && diagtot <= downtot && diagtot <= acrosstot) {
      trylist[trytot++] = DIAG;
      hadd = true;
    }
    if (trytot > 1)
      u.shuffle(trylist, (short) 0, trytot);
    j = trytot;
    if (horizontal && !hadh)
      trylist[trytot++] = HORIZ;
    if (vertical && !hadv)
      trylist[trytot++] = VERT;
    if (diagonal && !hadd)
      trylist[trytot++] = DIAG;
    u.shuffle(trylist, j, trytot);
    for (i = 0; i < trytot; ++i) { //fit word anywhere
      for (q = 0; q < sqtot; ++q) {
        j = o[q];
        if (addword(s, trylist[i], j))
          return true;
      }
    }
    return false;
  }
  //-------------------------------------------------------------
  boolean addword(String s[], short wdir, short sqpos) {
     short i,j,inc;
     if (wdir == HORIZ) {
       if (s.length > across - sqpos % across
           || sqpos % across > 0 && (dir[sqpos - 1] & HORIZ) != 0
           || sqpos % across + (s.length) < across
           && (dir[sqpos + s.length] & HORIZ) != 0
           )
         return false;
       inc = 1;
     }
     else if (wdir == VERT) {
       if (s.length > down - sqpos / across
           || sqpos / across > 0 && (dir[sqpos - across] & VERT) != 0
           || sqpos / across + s.length < down
           && (dir[sqpos + s.length * across] & VERT) != 0
           )
         return false;
       inc = across;
     }
     else {
        if(  s.length > across - sqpos%across
           || s.length > down - sqpos/across
           || sqpos/across  > 0
                && sqpos%across  > 0
                && (dir[sqpos -(across+1)] & DIAG) != 0
           || sqpos/across + (s.length) < down
               && sqpos%across + (s.length) < across
               && (dir[sqpos + s.length*(across+1)] & DIAG) != 0
             ) return false;
        inc = (short)(across+1);
     }
     for(i = sqpos,j=0;j<s.length;i+=inc,++j) {
        if(sq[i] != null  &&  (!sq[i].equals(s[j])
                               || (dir[i] & wdir) != 0)) return false;
     }
     for(i = sqpos,j=0;j<s.length;i+=inc,++j) {
        sq[i] =  s[j];
        dir[i] |= wdir;
        wordno[i] = currwordno+1;
     }
     inits = u.addString(inits,u.combineString(s,""));
     initpos = u.addshort(initpos,sqpos);
     initdir = u.addshort(initdir,wdir);
     initlen = u.addshort(initlen,(short)s.length);
     return true;
  }
  /**
   * Fills unfilled cells in the square with random letters
   */
  void fillUpSquare() {
     String s[] = u.splitsingles("abcdefghijklmnopqrstuvwxyz");
     if(phonicsw) s = u.addString(s,combos);
     for(int i=sqtot-1; i>=0; --i) {
        if(sq[i] == null)    sq[i] = s[u.rand(s.length)];
      }
  }
  //-------------------------------------------------------------
  boolean rudeword() {
     int ii,i,j,inc;
     for(ii=0;ii<sq.length;++ii) {
       String s = "" + sq[ii];
       int maxlen = Math.min(across - ii % across, rudelen);
       i = ii;
       while (s.length() < maxlen) {
         ++i;
         s = s + sq[i];
         if((wordno[ii]  == 0 || wordno[i] == 0 || wordno[ii]!=wordno[i])
               && u.findString(rudewords, s) >= 0)
           return true;
       }
       maxlen = Math.min(down - ii / down, rudelen);
       s = "" + sq[ii];
       i = ii;
       while (s.length() < maxlen) {
         i += across;
         s = s + sq[i];
         if ((wordno[ii]  == 0 || wordno[i] == 0 || wordno[ii]!=wordno[i])
               && u.findString(rudewords, s) >= 0)
           return true;
       }
       if(diagonal) {
         maxlen = Math.min(Math.min(down - ii / down,across - ii%across), rudelen);
         s = "" + sq[ii];
         i = ii;
         while (s.length() < maxlen) {
           i += across+1;
           s = s + sq[i];
           if ((wordno[ii]  == 0 || wordno[i] == 0 || wordno[ii]!=wordno[i])
                 && u.findString(rudewords, s) >= 0)
             return true;
         }
       }
     }
     return false;
  }
  /**
   * @return String containing the currently selected letters
   */
  String getCurrString() {
    String s = "";
    if (startMark < 0)
      return (s);
    for (short i = startMark; i < sqtot; ++i) {
      if (tempm[i] != 0)
        s = s + sq[i];
      if(tempm[i] == ENDH) tempdir = HORIZ;
      else if(tempm[i] == ENDD) tempdir = DIAG;
      else if(tempm[i] == ENDV) tempdir = VERT;
    }
    return s;
  }
  /**
   * <p>Title: WordShark</p>
   * <p>Description: Your description</p>
   * <p>Copyright: Copyright (c) 2004</p>
   * <p>Company: WhiteSpace</p>
   * @author Roger Burton
   * @version 1.0
   */
  class squareletter extends mover {
    /**
     *The position of this letter in relation to other letters
     */
    public short pos;
    /**
     * Sets relevant flags
     * @param pos1 Mover number - gives position of letter in relation to other letters
     */
    public squareletter(short pos1) {
      super(false);
      mouseSensitive = true;
      pos = pos1;
      w = sqstepx * mover.WIDTH / screenwidth;
      h = sqstepy * mover.HEIGHT / screenheight;
    }
    /**
     * @param g Object to be painted
     * @param x x position
     * @param y y position
     * @param w width
     * @param h height
     */
    public void paint(Graphics g, int x, int y, int w, int h) {
     int i;
     short newtemp;
     boolean changed = false;
     Font f = g.getFont();
     Color c = g.getColor();
     g.setFont(wordfont);
     g.setColor(background2);
     g.fillRect(x, y, w + 1, h + 1);
     if (mouseOver && startMark >= 0) { //If 1 The mouse is over the mover and startMark has been set
       if (horizontal                                                     //If 1.1
           && pos % across > startMark % across //The horizontal option is set AND This mover is positioned further across the row than the start mark
           && pos / across == startMark / across) { //AND The last click occurred in the same row as this letter
         for (i = startMark; i < sqtot; ++i) {                           //For 1.1.1
           if (i == startMark)                                            //If 1.1.1.1
             newtemp = STARTH;                          //i = the start of a selection
           else if (i < pos)                                         //Else if 1.1.1.2
             newtemp = MIDH;                           //i < the position of this letter
           else if (i == pos)                                        //Else if 1.1.1.3
             newtemp = ENDH;                 //i is equal to the position of this letter
           else                                                         //Else 1.1.1.3
             newtemp = 0;
           if (newtemp != tempm[i]) {                                     //If 1.1.1.5
             tempm[i] = newtemp;
             letter[i].paint(g,letter[i].x*screenwidth/mover.WIDTH,letter[i].y*screenheight/mover.HEIGHT,
                           letter[i].w*screenwidth/mover.WIDTH,letter[i].h*screenheight/mover.HEIGHT);
             changed = true;
           }
         }
       }                                                             //Else if 1.2
       else if (vertical                                    //The vertical option is set
                && pos % across == startMark % across //AND This mover is in the same position in the row as the start mark
                && pos / across > startMark / across) { //AND The last click occurred further up the same column as this letter
         for (i = startMark; i < sqtot; ++i) {                           //For 1.2.1
           if (i == startMark)                                           // If 1.2.1.1
             newtemp = STARTV;
           else if (i < pos && pos % across == i % across)           //Else if 1.2.1.2
             newtemp = MIDV;
           else if (i == pos)                                       // Else if 1.2.1.3
             newtemp = ENDV;
           else                                                         //Else 1.2.1.3
             newtemp = 0;
           if (newtemp != tempm[i]) {                                     //If 1.2.1.4
             tempm[i] = newtemp;
             letter[i].paint(g,letter[i].x*screenwidth/mover.WIDTH,letter[i].y*screenheight/mover.HEIGHT,
                           letter[i].w*screenwidth/mover.WIDTH,letter[i].h*screenheight/mover.HEIGHT);
             changed = true;
           }
         }
       }
       else if (diagonal && pos % across > startMark % across &&        //Else if 1.3
                pos % across - startMark % across ==
                pos / across - startMark / across) {
         for (i = startMark; i < sqtot; ++i) {                           //For 1.3.1
           if (i == startMark)                                            //If 1.3.1.1
             newtemp = STARTD;
           else if (i < pos && (i - startMark) % (across + 1) == 0)  //Else if 1.3.1.2
             newtemp = MIDD;
           else if (i == pos)                                        //Else if 1.3.1.3
             newtemp = ENDD;
           else                                                         //Else 1.3.1.3
             newtemp = 0;
           if (newtemp != tempm[i]) {                                     //If 1.3.1.4
             letter[i].ended = false;
             tempm[i] = newtemp;
             changed = true;
           }
         }
       }
       else {                                                           //Else 1.4
         for (i = startMark; i < sqtot; ++i) {                           //For 1.4.1
           if (i == startMark)                                            //If 1.4.1.1
             newtemp = (short) (STARTH + ENDH);
           else                                                         //Else 1.4.1.1
             newtemp = 0;
           if (newtemp != tempm[i]) {                                     //If 1.4.1.2
             tempm[i] = newtemp;
             letter[i].paint(g,letter[i].x*screenwidth/mover.WIDTH,letter[i].y*screenheight/mover.HEIGHT,
                           letter[i].w*screenwidth/mover.WIDTH,letter[i].h*screenheight/mover.HEIGHT);
             changed = true;
             newshowit = null;
           }
         }
       }
     }
     else if (!mouseOver && (tempm[pos] &                            //Else if 2
                             (ENDH + ENDV + ENDD)) != 0) {
       for (i = startMark; i <= pos; ++i) {                              //For 2.1
         if (tempm[i] != 0) {                                             //If 2.1.1
           if (i == startMark) {                                          //If 2.1.1.1
             tempm[i] = (short) (STARTH + ENDH);
           }
           else                                                         //Else 2.1.1.1
             tempm[i] = 0;
           letter[i].ended = false;
           changed = true;
           newshowit = null;
         }
       }
     }                                                                    //if 3
     if (mouseOver) {            //SS - If mouse is over the mover it the letter turns white
       g.setColor(Color.white);
     }
     else {
       g.setColor(Color.black);
     }
     g.drawString(sq[pos],
                 x + (w - metrics.stringWidth(sq[pos])) / 2,
                 y + h / 2 + metrics.getAscent() / 2 -
                 metrics.getDescent() / 2);
     if (dir[pos] != 0) { //If 3
       g.setColor(Color.yellow);
       drawboxing(g, x, y, w, h, dir[pos]);
     }
     if (tempm[pos] != 0) {                                               //If 4
       g.setColor(Color.white);
       drawboxing(g, x, y, w, h, tempm[pos]);
     }
     if(markdir != null ) {
       g.setColor(Color.red);
       drawboxing(g, x, y, w, h, markdir[pos]);
     }
     if (mouseOver && changed) {                                          //If 5
       newshowit = null;
       if (startMark >= 0) {                                              //If 5.1
         String s = getCurrString();                               //A click has occurred
         if (s.length() > 1) {                                            //If 5.1.1
           newshowit = new showcurr(s);
           int y1 = letter[pos].y;
           if (tempm[pos] == ENDH) {                                      //If 5.1.1.1
             if (pos / across < down - 1)                                 //If 5.1.1.1.1
               y1 += letter[pos].h;
             else                                                       //Else 5.1.1.1.1
               y1 -= letter[pos].h;
           }
           newshowit.x = letter[pos].x - newshowit.w;
           newshowit.y = y1;
         }
       }
     }
     g.setFont(f);
     g.setColor(c);
   }
    /**
     * Gives required mouse down responses
     * <li>Sets a startMark for a new selection
     * <li>If the startMark has already been set then gets the selected string and
     * zapps it if it is in the wordlist otherwise does a groan.
     * <li>When switches are in use reduces the area the focus goes through or enlarges it
     * as is needed.
     * @param xm x position of mouse
     * @param ym y position of mouse
     */
    public void mouseClicked(int xm, int ym) {
      if (completed) return;
      int i, j, k, m;
      if (startMark < 0) { //if 1
        startMark = pos; //If the startMark has not been set - set it
        tempm[pos] = (short) (STARTH + ENDH);
        ended = false;
//startSS^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
        if((sharkStartFrame.switchOptions == 1)||
              (sharkStartFrame.switchOptions ==2)){
             reduceSwitchFocus();
           }
//endSS^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
      }
      else {                                                             //else 1
        String s = getCurrString();                                //start mark is greater than 0
        if (s.length() < 2) {                                              //if 1.1
          cancelMark(false);                  //  Length of currently selected word is 1 or less
//startSS04-10-05^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
            if ( (sharkStartFrame.switchOptions == 1) ||
                (sharkStartFrame.switchOptions == 2)) {
              enlargeSwitchFocus();
            }
//endSS^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
          return;                             //the selection is not to be retained
       }
       for(i=0;i<foundpos.length;++i) {    // check not found once already
         if(foundpos[i] == startMark
             && founddir[i] == tempdir
             && foundlen[i] == s.length()) {noise.beep(); return;}
       }
       for (i = 0; i < wtot; ++i) {                                      //for 1.2
          if (s.equals(rgame.w[wlist[i].wordnumber].v())) {                //if 1.2.1
            if(showasfound) {
              int want=0,got=0;
               for(j=0;j<wtot;++j) if(s.equals(rgame.w[wlist[j].wordnumber].v())) ++want;
               for(j=0;j<list.length-1;++j) if(s.equals(list[j])) ++got;
               if (got<want) {
                 if (list.length - notinlist.length == wtot) {
                   list[list.length - 1] = rgame.w[wlist[i].wordnumber].v();
                   exitbutton(BOUNDX1 / 2, mover.HEIGHT -  BOUNDX1 / 2 * screenwidth / screenheight);
                 }
                 else{
                   list = u.addString(list, rgame.w[wlist[i].wordnumber].v(), list.length - 1);
                   }
               }
            }
            else {
              zapp(wlist[i], //The current string is the same as the ith word in the word list
                   (wlist[i].x + wlist[i].w / 2) * screenwidth / mover.WIDTH,
                   (wlist[i].y + wlist[i].h / 2) * screenheight / mover.HEIGHT, true);
            }
            currword = rgame.w[wlist[i].wordnumber];
            showpicture sp = new showpicture(0,mover.HEIGHT-PICDIMY,BOUNDX1,PICDIMY);
            if(phonicsw)  spokenWord.sayPhonicsWord(rgame.w[wlist[i].wordnumber],500,true,false,true);
            else rgame.w[wlist[i].wordnumber].say();
            if(!showasfound) System.arraycopy(wlist, i + 1, wlist, i, --wtot - i);
//startPR2005-04-18^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            if(Demo_base.isDemo){
              if (Demo_base.demoIsReadyForExit(0)) return;
            }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            gamescore(1);

            foundpos = u.addshort(foundpos, startMark);
            founddir = u.addshort(founddir, tempdir);
            foundlen = u.addshort(foundlen, (short)s.length());
            cancelMark(true);
            if (!showasfound && wtot == 0) { //if 1.2.1.1
              score(gametot1); //There are no words in use
              new spokenWord.whenfree(0) {
                public void action() {
                  exit(1000);
                }
              };
            }
            else{
            if(delayedflip && !completed){
               flip();
            }
            }
//startSS^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
            if ( (sharkStartFrame.switchOptions == 1) ||
                (sharkStartFrame.switchOptions == 2)) {
              enlargeSwitchFocus();
            }
//endSS^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
            return;
          }
        }
        if (spokenWord.findandsay(s)) {                                   // if 1.3
         foundpos = u.addshort(foundpos, startMark);
         founddir = u.addshort(founddir, tempdir);
         foundlen = u.addshort(foundlen, (short)s.length());
         cancelMark(true);                         //The current string can be found and said
          if(showasfound && u.findString(list,s)<0) {
            notinlist = u.addint(notinlist,list.length-1);
            list = u.addString(list,s,list.length-1);
            foundpos = u.addshort(foundpos, startMark);
            founddir = u.addshort(founddir, tempdir);
            foundlen = u.addshort(foundlen, (short)s.length());
         }
          return;
        }
        error(s);
//startSS04-10-05^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
        if ( (sharkStartFrame.switchOptions == 1) ||
            (sharkStartFrame.switchOptions == 2)) {
          enlargeSwitchFocus();
        }
//endSS^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
        gamescore( -1);
        noise.groan();
        cancelMark(false);
//startSS^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
        if ( (sharkStartFrame.switchOptions == 1) ||
            (sharkStartFrame.switchOptions == 2)) {
          enlargeSwitchFocus();
        }
//endSS^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
      }
    }
      /**
       * Resets the start mark (which is set as the beginning of a selection).
       * @param keep Indicates whether the position of the selection should be
       * retained.
       */
      void cancelMark(boolean keep) {
        short j;
        for (j = startMark; j < sqtot; ++j) {
          if (tempm[j] != 0) {
            if (keep)
              dir[j] |= tempm[j];
            tempm[j] = 0;
            letter[j].ended = false;
          }
        }
        startMark = -1;
        if (showit != null) {
          removeMover(showit);
          newshowit = showit = null;
        }
        gamePanel.copyall = true;
      }
      //-------------------------------------------------------------
      void drawboxing(Graphics g,int x, int y, int w, int h,short flags) {
        ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
         if((flags & STARTH) != 0 &&  (flags & ENDH) != 0) {
              g.drawOval(x+1,y+1,w-1,h-1);
         }
         else {
            if((flags & STARTH) != 0) {
              g.drawArc(x+1,y+1,w-2,h-2,90,180);
              g.drawLine(x+w/2, y+1, x+w, y+1);
              g.drawLine(x+w/2, y+h-1, x+w, y+h-1);
            }
            if((flags & ENDH) != 0) {
              g.drawArc(x,y+1,w-1,h-2,90,-180);
              g.drawLine(x,y+1,x+w/2, y+1);
              g.drawLine(x, y+h-1, x+w/2, y+h-1);
            }
            if((flags & MIDH) != 0) {
              g.drawLine(x, y+1, x+w, y+1);
              g.drawLine(x, y+h-1, x+w, y+h-1);
            }
           if((flags & STARTV) != 0) {
              g.drawArc(x+1,y+1,w-2,h-2,0,180);
              g.drawLine(x+1, y+h/2, x+1, y+h);
              g.drawLine(x+w-1, y+h/2, x+w-1, y+h);
            }
           if((flags & ENDV) != 0) {
              g.drawArc(x+1,y,w-2,h-1,0,-180);
              g.drawLine(x+1, y, x+1, y+h/2);
              g.drawLine(x+w-1, y, x+w-1, y+h/2);
            }
           if((flags & MIDV) != 0) {
              g.drawLine(x+1, y, x+1, y+h);
              g.drawLine(x+w-1, y, x+w-1, y+h);
           }
           if((flags & STARTD) != 0) {
              g.drawArc(x+1,y+1,w-2,h-2,0,270);
              g.drawLine(x+w-1, y+h/2, x+w-1, y+h-1);
              g.drawLine(x+w/2, y+h-1, x+w-1, y+h-1);
           }
           if((flags & ENDD) != 0) {
              g.drawArc(x+1,y+1,w-2,h-2,90,-270);
              g.drawLine(x+1, y+1, x+w/2, y+1);
              g.drawLine(x+1, y+1, x+1, y+h/2);
           }
           if((flags & MIDD) != 0) {
              g.drawArc(x+1,y+1,w-2,h-2,0,90);
              g.drawArc(x+1,y+1,w-2,h-2,180,90);
              g.drawLine(x+1, y+1, x+w/2, y+1);
              g.drawLine(x+1, y+1, x+1, y+h/2);
              g.drawLine(x+w-1, y+h/2, x+w-1, y+h-1);
              g.drawLine(x+w/2, y+h-1, x+w-1, y+h-1);
           }
        }
        ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);
     }
//startSS^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
     /**
      * Causes focusing to cover the whole square once more, used with switches
      */
     private void enlargeSwitchFocus() {
       pointSwitch1 = new Point[across][down];
       for (short i = 0; (short) (sqtot - 1) >= i; ++i) {
         int acrossPosition = BOUNDX1 + BORDER +
             sqstepx * (i % across) * mover.WIDTH / screenwidth;
         int downPosition = BOUNDY1 + BORDER +
             sqstepy * (i / across) * mover.HEIGHT / screenheight;
         setSwitchPosition(acrossPosition + (letter[i].w / 2),
                           downPosition + (letter[i].h / 2), i);
       }
     }
//endSS^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
//startSS^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
     /**
      * Reduces the size of area focused when using switches
      */
     private void reduceSwitchFocus() {
       int i, j, k, m;
       short longestWord = word.maxlen(rgame.w);   //longest word in current word list
       int gridx = across - (pos / across); //sizing the focusing to so it stays within the square
       if (gridx > longestWord) {
         gridx = longestWord;
       }
       int gridy = down - (pos % across);
       if (gridy > longestWord) {
         gridy = longestWord;
       }
       Point tempPoint[][];
       tempPoint = new Point[gridx][gridy];
       boolean gridFilled = false;   //Indicates whether the tempPoint[][] has been filled
         for (i = 0; i < across; i++) {
           for (j = 0; j < down; j++) {
             Point thePoint = pointSwitch1[i][j];
             if (thePoint.x >= letter[pos].x
                 && thePoint.y >= letter[pos].y
                 && !gridFilled) {
               for (k = 0; k < gridx; k++) {
                 for (m = 0; m < gridy; m++) {
                   tempPoint[k][m] = pointSwitch1[i + k][j + m];
                 }
               }
               gridFilled = true;
             }
           }
         }
         pointSwitch1 = new Point[gridx][gridy];
         for (i = 0; i < gridx; i++) {
           for (j = 0; j < gridy; j++) {
             pointSwitch1[i][j] = tempPoint[i][j];
           }
         }
         currentPositionX = 0;     //reset position within the array
         currentPositionY = 0;
     }
//endSS^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
   }
   //-------------------------------------------------------------
  class slist extends mover {
      public int wordnumber;
      public slist(int wordnumber1) {
         super(false);
         wordnumber=wordnumber1;
      }
      public void paint(Graphics g,int x, int y, int w, int h) {
         Rectangle r = new Rectangle(x,y,w,h);
         g.setFont(listfont);
         g.setColor(Color.black);
         rgame.w[wordnumber].paint(g,r,rgame.options & ~word.PHONICSPLIT);
      }
   }
  //-------------------------------------------------------------
  class directions extends mover {
      int thick,arrowhead;
      public directions(int w1, int h1) {
         super(false);
         h = h1;
         w = w1;
      }
      public void paint(Graphics g,int x, int y, int w, int h) {
         Font f = g.getFont();
         Color c = g.getColor();
         g.setFont(wordfont);
         g.setColor(Color.black);
         int dist = Math.min(w,h);
         int thick  = dist/12;
         int arrowhead = Math.max(2,dist/6);
         int y1 = y + arrowhead-thick;
         int x1 = x + arrowhead-thick;
         if(horizontal)  {
            g.fillRect(x1,y1,dist - arrowhead*3+thick,thick*2+1);
            g.fillPolygon(new int[]{x+dist-arrowhead*2, x+dist, x+dist-arrowhead*2},
                          new int[]{y, y+arrowhead, y+arrowhead*2},3);
         }
         if(vertical)  {
            g.fillRect(x1,y1,thick*2+1,dist- arrowhead*3 +thick);
            g.fillPolygon(new int[]{x, x+arrowhead, x+arrowhead*2},
                          new int[]{y+dist-arrowhead*2, y+dist, y+dist-arrowhead*2},3);
         }
         if(diagonal)  {
            arrowhead = arrowhead *3/4;
            int thick2 = thick*14/10;
            g.fillPolygon(new int[] {x1+thick2,x+dist-arrowhead,x+dist-arrowhead-thick2,x1},
                          new int[] {y1,y+dist-arrowhead-thick2,y+dist-arrowhead,y1+thick2},4);
            g.fillPolygon(new int[]{x+dist-arrowhead, x+dist, x+dist-arrowhead*3},
                          new int[]{y+dist-arrowhead*3, y+dist, y+dist-arrowhead},3);
         }
         g.setFont(f);
         g.setColor(c);
      }
   }
   //--------------------------------------------------------------
   public void makeBackground2() {
      background2= new Color(128+u.rand(64),128+u.rand(64),128+u.rand(64));
   }
   //----------------------------------------------------------
   class box extends mover {
      public box() {
         super(false);
        h =  BOUNDY2-BOUNDY1;
        w =  BOUNDX2 - BOUNDX1;
      }
      public void paint(Graphics g,int x, int y, int w, int h) {
         g.setFont(wordfont);
         g.setColor(background2);
         g.fillRect(x,y,w,h);
         u.buttonBorder(g, new Rectangle (x,y,w,h), background2,true);
      }
   }
   class listm extends mover {
     public listm() {
       h = listheight;
       w = BOUNDX1;
       addMover(this,0,BOXDIMY);
     }
     public void paint(Graphics g,int x1, int y1, int w, int h) {
       int i;
       if(listm.getHeight()*list.length > listheight*screenheight/mover.HEIGHT) {
         sizelistfont();
       }
       g.setFont(listfont);
       int yinc = listheight*screenheight/mover.HEIGHT/(Math.max(wtot,list.length));
       y1 +=  listm.getAscent();
       int xx = listx*screenwidth/mover.WIDTH;
       for(i=0;i<list.length;++i, y1+=yinc) {
         if(endcount>= 0 && i >= endcount) {
           g.setColor(Color.red);
           g.fillRect(0, y1-listm.getAscent(), w, yinc);
           g.setColor(Color.white);
         }
         else g.setColor(u.inlist(notinlist,i)?Color.gray:Color.black);
         g.drawString(list[i],xx,y1);
       }
     }
   }
   /**
    *
    * <p>Title: WordShark</p>
    * <p>Description: Provides a mover that shows the current selection of letters</p>
    * <p>Copyright: Copyright (c) 2004</p>
    * <p>Company: WhiteSpace</p>
    * @author Roger Burton
    * @version 1.0
    */
   class showcurr extends mover {
      String showword;
      /**
       * Creates display of the current selection of letters
       * @param s String to be displayed
       */
      showcurr(String s) {
        super(false);
        showword =s;
        h =  (metrics.getHeight()+4)*mover.HEIGHT/screenheight;
        w =  metrics.stringWidth(showword+"  ")*mover.WIDTH/screenwidth;
      }
      /**
       * Paints the display of the currently displayed letters
       * @param g Object to be painted
       * @param x x position
       * @param y y position
       * @param w width
       * @param h height
       */
      public void paint(Graphics g,int x, int y, int w, int h) {
         Rectangle r = new Rectangle(x,y,w,h);
         Font f = g.getFont();
         Color c = g.getColor();
         g.setFont(wordfont);
         g.setColor(Color.red);
         g.fillRect(x,y,w,h);
         g.setColor(Color.white);
         g.drawString(showword,
               x + (w - metrics.stringWidth(showword))/2,
               y + h/2 + metrics.getAscent()/2 - metrics.getDescent()/2);
         g.setFont(f);
         g.setColor(c);
      }
   }
//startSS^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
   /**
    * Stores the positions of the letters in the grid so that they can be referenced
    * when focus needs to be changed.
    * @param x x coordinate of the point to be referenced
    * @param y y coordinate of the point to be referenced
    * @param moverNo The number of the mover whose position is being referenced
    */
   private void setSwitchPosition(int x, int y, int moverNo) {
     pointSwitch1[moverNo/across][moverNo%across] = new Point(x, y);
   }
//endSS^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
 //startPR2004-10-06^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    public void print() {
   PrinterJob pJob = PrinterJob.getPrinterJob();
   pJob.setJobName(u.gettext("printing","wordsearch"));
   PageFormat pageFormat = pJob.defaultPage();
   pageFormat.setOrientation(pageFormat.PORTRAIT);
   pJob.setPrintable(this, pageFormat);
   if (pJob.printDialog()) {
       try {
         pJob.print();
       }
       catch (Throwable t) {}
   }
 }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2004-10-06^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
public int print(Graphics g, PageFormat f, int a) throws PrinterException {

  // if reached end of print
  if (a > 0) {
    return NO_SUCH_PAGE;
  }

  // allows skipping of the buffer's graphics context
  if (a != lastPage) {
    lastPage = a;
    return Printable.PAGE_EXISTS;
  }

  g.translate( (int) f.getImageableX(), (int) f.getImageableY());
  Dimension dd;
  if (shark.macOS)
    /* width needs to be ajusted slightly for the Mac so that the whole
     sumsearch fits on the printed page*/
    dd = new Dimension( (int) (f.getImageableWidth() * 0.99),
                       (int) f.getImageableHeight());
  else
    dd = new Dimension( (int) f.getImageableWidth(),
                       (int) f.getImageableHeight());

  int w = dd.width * 7 / 8;
  int h = dd.height * 15 / 16;
  int xx = (dd.width - w) / 2;
  int yy = (dd.height - h) / 2;
  Date d = new Date();
  int x1, y1, x2, y2, fsiz;
  short i;
  String h1 = u.gettext("printing", "header2", spellchange.spellchange(rgame.topicName));
  String h1a = u.gettext("printing", "footer");
  Font f1 = null, f2 = null, f3 = null, f4 = null;
  FontMetrics m1 = null, m2 = null, m3 = null, m4 = null;
  String wds[] = new String[wtot];
  for (i = 0; i < wtot; ++i) wds[i] = rgame.w[wlist[i].wordnumber].v();
  int wordwidth = w / 5 - w / 60, sqadv = 0, y;

  f1 = sizeFont(g, new String[] {h1 + "   "}
                , w, h / 10);
  m1 = g.getFontMetrics();

  f2 = sizeFont(g, wds, wordwidth, Math.min(h * 3 / 4, h / 30 * wtot));
  m2 = g.getFontMetrics();
  wordwidth += w / 60;
//startPR2007-11-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//  f3 = new Font(sharkStartFrame.wordfont.getName(),
//                sharkStartFrame.wordfont.getStyle(), fsiz = 60);
  f3 = sharkStartFrame.wordfont.deriveFont((float)(fsiz = 60));
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  g.setFont(f3);
  m3 = g.getFontMetrics(f3);
  for (i = 0; i < sqtot; ++i) {
    while (m3.getHeight() * across > w - wordwidth
           || m3.stringWidth(sq[i]) * 5 / 4 * across > w - wordwidth) {
//startPR2007-11-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      g.setFont(f3 = new Font(f3.getName(), f3.getStyle(), --fsiz));
      g.setFont(f3 = f3.deriveFont((float)--fsiz));
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      m3 = g.getFontMetrics(f3);
    }
  }
  f4 = sizeFont(g, new String[] {h1a}
                , w, h/50);
  m4 = g.getFontMetrics(f4);
  sqadv = (w - wordwidth) / across;

  y = +yy;
  g.setFont(f1);
  g.setColor(Color.black);
  g.drawString(h1, xx + (w - m1.stringWidth(h1)) / 2, yy + m1.getHeight());
  y += m1.getHeight() * 2;
  //draw directions-------------------
  int arrow = w / 30;
  int shaft = w / 10;
  y2 = y;
  x1 = xx + arrow;
  x2 = x1 + shaft;
  if (horizontal) {
    g.drawLine(x1, y, x2, y);
    g.fillPolygon(new int[] {x2, x2 - arrow, x2 - arrow}
                  ,
                  new int[] {y, y - arrow / 3 + 1, y + arrow / 3}
                  , 3);
  }
  if (vertical) {
    y2 += shaft;
    g.drawLine(x1, y, x1, y2);
    g.fillPolygon(new int[] {x1, x1 - arrow / 3 + 1, x1 + arrow / 3}
                  ,
                  new int[] {y2, y2 - arrow, y2 - arrow}
                  , 3);
  }
  if (diagonal) {
    y2 = y + shaft;
    g.drawLine(x1, y, x2, y2);
    g.fillPolygon(new int[] {x2, x2 - arrow / 2, x2 - arrow}
                  ,
                  new int[] {y2, y2 - arrow, y2 - arrow / 2}
                  , 3);
  }
  y = y2 + m1.getHeight() * 2;

  // draw words ----------------------------------
  g.setFont(f2);
  for (i = 0; i < wtot; ++i) {
    g.drawString(wds[i], xx, y + (i + 1) * m2.getHeight());
  }
  // draw word search -----------------------------

  g.setFont(f3);
  for (i = 0; i < sqtot; ++i) {
    g.drawString(sq[i],
                xx + wordwidth + (i % across) * sqadv +
                (sqadv - m3.stringWidth(sq[i])) / 2,
                y + (i / across) * sqadv);
  }
  int thick = w / 60;
  x1 = xx + wordwidth;
  x2 = xx + wordwidth + across * sqadv;
  y1 = y - m3.getMaxAscent() -
      (sqadv - m3.getMaxDescent() - m3.getMaxAscent()) / 2 - sqadv / 6;
  y2 = y1 + down * sqadv + sqadv / 3;

  g.setColor(new Color(196, 196, 196));
  for (i = 1; i < across; ++i) {
    g.drawLine(x1 + sqadv * i, y1, x1 + sqadv * i, y2);
    g.drawLine(x1, y1 + sqadv * i + sqadv / 6, x2, y1 + sqadv * i + sqadv / 6);
  }
  g.setColor(new Color(96, 96, 96));
  g.fillPolygon(new int[] {x2, x2 + thick, x2 + thick, x2}
                ,
                new int[] {y1, y1 - thick, y2 + thick, y2}
                , 4);
  g.fillPolygon(new int[] {x1, x1 - thick, x2 + thick, x2}
                ,
                new int[] {y2, y2 + thick, y2 + thick, y2}
                , 4);
  g.setColor(new Color(196, 196, 196));
  g.fillPolygon(new int[] {x1, x1 - thick, x1 - thick, x1}
                ,
                new int[] {y1, y1 - thick, y2 + thick, y2}
                , 4);
  g.fillPolygon(new int[] {x1, x1 - thick, x2 + thick, x2}
                ,
                new int[] {y1, y1 - thick, y1 - thick, y1}
                , 4);
  g.setColor(Color.black);
  g.drawPolygon(new int[] {x2, x2 + thick, x2 + thick, x2}
                ,
                new int[] {y1, y1 - thick, y2 + thick, y2}
                , 4);
  g.drawPolygon(new int[] {x1, x1 - thick, x2 + thick, x2}
                ,
                new int[] {y2, y2 + thick, y2 + thick, y2}
                , 4);
  g.drawPolygon(new int[] {x1, x1 - thick, x1 - thick, x1}
                ,
                new int[] {y1, y1 - thick, y2 + thick, y2}
                , 4);
  g.drawPolygon(new int[] {x1, x1 - thick, x2 + thick, x2}
                ,
                new int[] {y1, y1 - thick, y1 - thick, y1}
                , 4);

  // put bottom line
  g.setFont(f4);
  g.drawString(h1a, xx, yy + h - m4.getHeight() * 3);

  return Printable.PAGE_EXISTS;
}
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//-------------------------------------------------------
  class sbutton extends mover {
    String message = new String(rgame.getParm("quit"));
    Font f;
    FontMetrics m;
    int lastw1;
    Color col;
    public sbutton() {
       super(false);
       keepMoving = true;
       w = mover.WIDTH/8;
       h = mover.HEIGHT/12;
       addMover(this,BOUNDX1/2 - w/2, mover.HEIGHT - h*5/4);
    }
    public void paint(Graphics g,int x, int y, int w1, int h1) {
      if(completed) {
        kill=true;
        return;
      }
      if(currshowpicture != null) return;
      Rectangle r = new Rectangle(x, y, w1, h1);
      if(lastw1 != w1) {
        f = sizeFont(null, g, message, w1 * 3 / 4, h1 * 3 / 4);
        m = g.getFontMetrics();
        col = u.tooclose(background,Color.black)?white():Color.black;
        lastw1 = w1;
      }
      g.setColor(col);
      g.setFont(f);
      x += w1/2;
      y += Math.max(0, h1/2 - m.getHeight()/2 + m.getMaxAscent());
      g.drawString(message, x - m.stringWidth(message)/2,y);
      u.buttonBorder(g,r,Color.red,!mouseDown);
   }
   public void mouseClicked(int x, int y) {
      int i,j,k;
      list = u.removeString(list,list.length-1);
      endcount = (short)list.length;
      markdir = new short[sqtot];
      if(list.length < rgame.w.length/2) cancelreward = true;
      for(i=0;i<wtot;++i) {
        String s = rgame.w[wlist[i].wordnumber].v();
        int want=0,got=0;
        for(j=0;j<wtot;++j) if(s.equals(rgame.w[wlist[j].wordnumber].v())) ++want;
        for(j=0;j<list.length;++j) if(s.equals(list[j])) ++got;
        if(got<want) {
          list = u.addString(list, s);
          for (j = 0; j < inits.length; ++j) {
            if (inits[j].equals(s)) {
              boolean alreadyfound = false;
              for (k = 0; k < foundpos.length; ++k) {
                if (foundpos[k] == initpos[j] && founddir[k] == initdir[j] && foundlen[k] == s.length()) {
                  alreadyfound = true;
                  break;
                }
              }
              if (!alreadyfound) {
                int inc = initdir[j] == HORIZ ? 1 : (initdir[j] == VERT ? across : across + 1);
                for (k = initpos[j]; k < initpos[j] + inc * initlen[j]; k += inc) {
                  if (k == initpos[j]) {
                    markdir[k] |= initdir[j] == HORIZ ? STARTH : (initdir[j] == VERT ? STARTV : STARTD);
                  }
                  else
                    if (k < initpos[j] + inc * initlen[j] - inc) {
                      markdir[k] |= initdir[j] == HORIZ ? MIDH : (initdir[j] == VERT ? MIDV : MIDD);
                    }
                    else {
                      markdir[k] |= initdir[j] == HORIZ ? ENDH : (initdir[j] == VERT ? ENDV : ENDD);
                    }
                }
                foundpos = u.addshort(foundpos, initpos[j]);
                founddir = u.addshort(founddir, initdir[j]);
                foundlen = u.addshort(foundlen, (short) s.length());
                break;
              }
            }
          }
        }
        exitbutton(BOUNDX1 / 2, mover.HEIGHT -  BOUNDX1 / 2 * screenwidth / screenheight);
        gamePanel.clearall();
      }
   }
  }
}
