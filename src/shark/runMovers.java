package shark;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * <p>Title: WordShark</p>
 * <p>Description: An object of this class is a panel that runs "movers" i.e. games objects,
 *  messages - some movers move but not all do so. The movers created are stored in an array</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: WhiteSpace</p>
 * @author Roger Burton
 * @version 1.0
 */
public class runMovers extends JComponent implements Runnable{
   public static  Font tooltipfont = UIManager.getFont("ToolTip.font").deriveFont((float)Math.max(10, UIManager.getFont("ToolTip.font").getSize()-4));
   static boolean splitdraw;
//startPR2007-11-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//   public static Font f[] = new Font[] {tooltipfont,
//                       new Font(tooltipfont.getName(),
//                                tooltipfont.ITALIC,tooltipfont.getSize()),
//                       new Font(tooltipfont.getName(),
//                                tooltipfont.BOLD,tooltipfont.getSize()),
//                       new Font(tooltipfont.getName(),
//                                tooltipfont.ITALIC+tooltipfont.BOLD,tooltipfont.getSize())};
   public static Font f[] = new Font[] {tooltipfont,
       tooltipfont.deriveFont(tooltipfont.ITALIC,(float)tooltipfont.getSize()),
       tooltipfont.deriveFont(tooltipfont.BOLD,(float)tooltipfont.getSize()),
       tooltipfont.deriveFont(tooltipfont.ITALIC+tooltipfont.BOLD,(float)tooltipfont.getSize())};
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   public static FontMetrics fm[] = new FontMetrics[] {sharkStartFrame.mainFrame.getFontMetrics(tooltipfont),
                              sharkStartFrame.mainFrame.getFontMetrics(f[1]),
                              sharkStartFrame.mainFrame.getFontMetrics(f[2]),
                              sharkStartFrame.mainFrame.getFontMetrics(f[3])};
//startPR2007-11-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//  public static  Font smalltooltipfont = new Font(tooltipfont.getName(),tooltipfont.PLAIN,tooltipfont.getSize()*3/4);
//  public static Font smallf[] = new Font[] {smalltooltipfont,
//      new Font(tooltipfont.getName(),tooltipfont.ITALIC,tooltipfont.getSize()*3/4),
//      new Font(tooltipfont.getName(),tooltipfont.BOLD,tooltipfont.getSize()*3/4),
//      new Font(tooltipfont.getName(),tooltipfont.ITALIC+tooltipfont.BOLD,tooltipfont.getSize()*3/4)};
   public static  Font smalltooltipfont =  tooltipfont.deriveFont(tooltipfont.PLAIN,tooltipfont.getSize()*3/4);
   public static Font smallf[] = new Font[] {smalltooltipfont,
       tooltipfont.deriveFont(tooltipfont.ITALIC,(float)tooltipfont.getSize()*3/4),
       tooltipfont.deriveFont(tooltipfont.BOLD,(float)tooltipfont.getSize()*3/4),
       tooltipfont.deriveFont(tooltipfont.ITALIC+tooltipfont.BOLD,(float)tooltipfont.getSize()*3/4)};
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   public static FontMetrics smallfm[] = new FontMetrics[] {sharkStartFrame.mainFrame.getFontMetrics(smalltooltipfont),
                               sharkStartFrame.mainFrame.getFontMetrics(smallf[1]),
                               sharkStartFrame.mainFrame.getFontMetrics(smallf[2]),
                               sharkStartFrame.mainFrame.getFontMetrics(smallf[3])};
//startPR2007-11-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//   public static  Font bigtooltipfont = new Font(tooltipfont.getName(),tooltipfont.PLAIN,tooltipfont.getSize()*5/4);
//   public static Font bigf[] = new Font[] {bigtooltipfont,
//                       new Font(bigtooltipfont.getName(),bigtooltipfont.ITALIC,bigtooltipfont.getSize()),
//                       new Font(bigtooltipfont.getName(),bigtooltipfont.BOLD,bigtooltipfont.getSize()),
//                       new Font(bigtooltipfont.getName(),bigtooltipfont.ITALIC+bigtooltipfont.BOLD,bigtooltipfont.getSize())};
   public static Font bigtooltipfont = tooltipfont.deriveFont(tooltipfont.PLAIN,(float)tooltipfont.getSize()*5/4);
   public static Font bigf[] = new Font[] {bigtooltipfont,
       bigtooltipfont.deriveFont(bigtooltipfont.ITALIC,(float)bigtooltipfont.getSize()),
       bigtooltipfont.deriveFont(bigtooltipfont.BOLD,(float)bigtooltipfont.getSize()),
       bigtooltipfont.deriveFont(bigtooltipfont.ITALIC+bigtooltipfont.BOLD,(float)bigtooltipfont.getSize())};
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   public static FontMetrics bigfm[] = new FontMetrics[] {sharkStartFrame.mainFrame.getFontMetrics(bigtooltipfont),
                              sharkStartFrame.mainFrame.getFontMetrics(bigf[1]),
                              sharkStartFrame.mainFrame.getFontMetrics(bigf[2]),
                              sharkStartFrame.mainFrame.getFontMetrics(bigf[3])};
   public static  Color tooltipbg = sharkStartFrame.textbgcolor;  //UIManager.getColor("ToolTip.background");
   public static  Color tooltipfg = sharkStartFrame.textfgcolor;   //UIManager.getColor("ToolTip.foreground");

   /**
    * array of movers that are presently instantiated.
    */
   public mover m[] = new mover[0];
   public short mtot;
   public Image offscreen[] = new Image[2];
   /**
    * Contains the fixed (not moving) offscreen image.
    */
   public Image offscreenfixed;
   /**
    * Sprite image saved
    */
   public Image spritesave;
   public byte curroffscreen;
   /**
    * The on screen graphics context
    */
   Graphics2D ong;
   /**
    * The off screen graphics context
    */
   Graphics2D offg;
   Graphics2D offgx[]=new Graphics2D[2],savefixedg,spritesaveg;
   class uparea {int x1;int y1; int x2; int y2;}
   uparea up[] = new uparea[20];
   int uptot;
   /**
    * The logical x position of the mouse.
    */
   public int mousex = -1;
   /**
    * The logical y position of the mouse.
    */
   public int mousey = -1;
   /**
    * Holds x coordinate of the mouse's physical position within the component in pixels
    */
   public int mousexs;
   /**
    * Holds y coordinate of the mouse's physical position within the component in pixels.
    */
   public int mouseys;
   /**
    * used for prev drawing
    */
   int latestmousexs;
   /**
    * used for prev drawing
    */
   int latestmouseys;
   /**
    * The thread that runs while the runmover panel exists
    */
   Thread runthread;
   /**
    * Number of fragments a mover is broken into when zapped.
    */
   static final short MAXZAPBITS = 10;
   static final int ZAPTIME = 800;
   /**
    * Used to give a specified time for redrawing.
    */
   public static final short REDRAWINT = 40;
   /**
    * The game currently being played
    */
   public sharkGame currgame;
   /**
    * Holds time of last click
    */
   long mousedowntime;
   Point mousedownpoint;
   int dragQualifyingDistance = 5;
   boolean mousedragged;
   public long mousepressedat;
   /**
    * Holds time of last click
    */
   long  mousedowntimex;
   mover inputmover;
   /**
    * The width of the runmovers panel
    */
   public int screenwidth;
   public int screenheight,screenmax ;
   public Polygon gettingPolygon;
   public boolean allclicks;
   public boolean rightclick;   // last click is with right button
   /**
    * A polygon is wanted
    */
   public boolean wantPolygon;
   /**
    * When true whole screen is to be cleared.
    */
   public boolean clearWholeScreen;
   /**
    * True if the enter and space keys need to be detected
    */
   public boolean detectEnterSpace;
   public boolean mouseOutside,freeze,pause;
   public boolean canquiesce=shark.doImageScreenshots?false:true;
   /**
    * True when a sprite should be shown.
    */
   public boolean showSprite;
   /**
    * Causes the draw method to redraw the whole screen
    */
   public boolean copyall;
   /**
    * When true the game is not ready to start.
    */
   public boolean dontstart;
   /**
    * The runmovers panel is the main panel on the screen at the time e.g. it is
    * a panel such as the games panel or the shark panel that appears when the
    * program is run initially.
    */
   public boolean onmainscreen;
   /**
    * True when the normal windows sprite is wanted as opposed to a customised one.
    */
   public boolean winsprite;
   /**
    * True when the normal windows sprite is visible.
    */
   public boolean winspritevisible;
   public boolean makingtrails;
   /**
    * When set the runmover's thread is started.
    */
   public boolean canrun;
   /**
    * When true movers are not redrawn unless they have changed - this stops the
    * program slowing down too much.
    */
   public boolean savefixed;
   public int viewerx=mover3d.BASEU/2,viewery=mover3d.BASEU/2;
   /**
    * When set the runmover's thread will stop executing.
    */
   boolean stoprun;
   /**
    * When set the runmover's thread is paused  but can be restarted.
    */
   boolean haltrun;
   /**
    * True if no sprite is needed
    */
   boolean cancelSprite;
   /**
    * When true the fixed mover is redrawn. i.e. The whole screen is redrawn
    * even those parts that have not changed and would not otherwise have been
    * redrawn as they are part of the off screen buffer.
    */
   boolean redrawoffscreen;
   /**
    * The sprite in use
    */
   public sharkImage sprite;
   /**
    * Mover that needs to react to key events.
    */
   public mover moverwantskey;
   int oldmousexs,oldmouseys;
   sharkGame.showpicture wantpic;
   boolean dontrun;
   public boolean simpleoptions;   // set to leave screen if options for game dont change screen
   /**
    * Updated x coordinate of the first corner of the rectangle.
    */
   int ux1 = -999999;
   /**
    * Updated x coordinate of the second corner of the rectangle.
    */
   int ux2;
   /**
    * Updated y coordinate of the first corner of the rectangle.
    */
   int uy1;
   /**
    * Updated y coordinate of the second corner of the rectangle.
    */
   int uy2;
   int oldux1=-999999,oldux2,olduy1,olduy2;
   int spritex1,spritex2,spritey1,spritey2;
   /**
    * x coordinate for the sprites left corner to be used for refreshing
    */
   int refreshx1;
   /**
    * x coordinate for the sprites right corner to be used for refreshing
    */
   int refreshx2;
   /**
    * y coordinate for the sprites left corner to be used for refreshing
    */
   int refreshy1;
   /**
    * y coordinate for the sprites right corner to be used for refreshing
    */
   int refreshy2;
   /**
    * Contains the sentence, from a mover, that needs to be spoken
    */
   String sentencefrommover;
   /**
    * Used to mark movers whose text needs to be spoken - highlights mover when
    * speech is in progress
    */
   public marker sentencemarker;
   /**
    * Used when help is active so that a tooltip_base appears when mouse is over an object
    */
   tooltipmover tooltipmover1;
   boolean drawing;
   /**
    * Lets redrawing occur if draw method is called
    */
   boolean canredraw;
   boolean recopy;
   /**
    * A new sprite is required
    */
   public boolean newsprite;
   /**
    * Contains mouse movers
    */
   public mover mousemovers[] = new mover[0];
   /**
    * Number of mousemovers.
    */
   public int mousemovertot;
   /**
    * Used to move fragments when a mover is zapped
    */
   fragmover mzz[];
   /**
    * Assigned mover to be zapped
    */
   mover zapmover;
   public boolean sort3dmovers;
   short repaints=0,repaint2=0,waitfordraw;
   /**
    * The period of time after which the screen should be redrawn
    */
//startPR2005-10-16^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//   long refreshat;
    public long refreshat;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   MouseMotionListener li1;
   MouseListener li2;
   /**
    * Used to to indicate when the tooltip_base has been repainted
    * in which circumstance lastttpaint != tooltip_base.painted
    */
   int lastttpaint = tooltip_base.painted;
   public boolean waitforsay;
   /**
    * TRUE WHEN PRESSING SPACE CAUSES FOCUS TO MOVE DOWN WHEN SWITCHES ARE IN USE
    * FALSE WHEN FOCUS MOVES ACROSS - SS
    */
   boolean goDown = true;
   /**
    * TIME FOR NEXT MOVE FOR SINGLE SWITCH ACCESS - SS
    */
   long nextTime = (int) System.currentTimeMillis() +
                  (sharkStartFrame.switchResponse * 1000);
   /**
    * FOR SINGLE SWITCH ACCESS TRUE WHEN IN A GAME IS STARTED AND IS USING SWITCH ACCESS- SS
    */
   boolean switchGameStarted = false;
   /**
    * Used to indicate when switch use has been initialised
    */
   boolean switchInitialise = true;

//startPR2006-07-19^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   char lastkey;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   /**
    *
    */
   public Color bg = Color.lightGray;
//startPR2004-10-23^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   /**
    * When true the setBounds method of the current game is called. This is only
    * needed for when running on a Mac. It enables the game window to jump
    * back into position when it is moved.
    */
//   public static boolean macGameSize = false;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2009-11-13^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   public boolean dragging = false;
//endPRwin7^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR

   //start rb2005-5-9^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^RB
   // permanent storage for buffers for use by games
   static Image buffers[] = new Image[3];
   int bstartx;
   static int leftx;
   public boolean usepool; // request to use buffer pool
   boolean extraclear;
   //end rb2005-5-9^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^RB
   // changes to idle after given time  rb 23/1/06
   long lastactivity; // the last time anything happened
   static final int idletime = 60000;
   // end rb 23/1/06


   Cursor handcursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
   Cursor defaultcursor = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
   public GradientPaint gradientBg;
   public int upAfterDownDragDelay = 300;
   public boolean touchchanges;  // perhaps should be amalgamated with clickenddrag?
   int tooltipmoverdelay = 500;


   public runMovers() {
      super();
      setDoubleBuffered(false);
      setOpaque(false);
   }
   /**
    * <p>Adds mouse listener for these mouse events:-
    * <li>mousePressed
    * <li>mouseEntered
    * <li>mouseExited
    * <p>Adds mouse motion listener for these mouse events:-
    * <li>mouseMoved
    * <li>mouseDragged
    */
   public void start1() {
      canrun = true;
      startrunning();
      if(li2==null) {
        addMouseListener(new MouseAdapter() {
          public void mouseReleased(MouseEvent e) {
            mouseUp(e.getX(), e.getY());
//startPR2009-11-13^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            dragging = false;
//endPRwin7^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          }

          public void mousePressed(MouseEvent e) {
            rightclick = e.getModifiers() == e.BUTTON3_MASK;
            if (currgame != null) {
              currgame.rightclick = rightclick;
              if (!currgame.clickonrelease && currgame.mouseDownDelay)   u.pause(100); // allow screen draw to catch up
            }
            mouseDown(e.getX(), e.getY());
          }

          public void mouseEntered(MouseEvent e) {
            mouseOutside = false;
            lastactivity = System.currentTimeMillis();                                 // rb 23/1/06
            if (!stoprun && !haltrun && (runthread==null || !runthread.isAlive()))     // rb 23/1/06
              startrunning();                                                          // rb 23/1/06
            lastttpaint = tooltip_base.painted;
            if (onmainscreen && (sharkStartFrame.mainFrame.manBar1.hasFocus()
                                 || sharkStartFrame.gametot > 0)) {
              return;
            }
            haltrun = false;
            startrunning();
          }

          public void mouseExited(MouseEvent e) {
              // games icon screen not repainting well before this change. Particaulrly after
              // clicking next list and prev list
           mouseOutside = true;
            if (tooltipmover1 != null) {
              removeMover(tooltipmover1);
              redrawoffscreen = true;
              tooltipmover1 = null;
              u.pause(400);
            }
            if (freeze && runthread != null && !stoprun)
              haltrun = true;
          }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
          public void mouseMoved(MouseEvent e) {
            if (tooltipmover1 != null)
              tooltipmover1.starttime2 = System.currentTimeMillis();
            mousemoved(e.getX(), e.getY());
          }

          public void mouseDragged(MouseEvent e) {
//startPR2009-11-13^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            dragging = true;
//endPRwin7^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            mousemoved(e.getX(), e.getY());
          }
        });
      }
   }
   void addlisteners() {
        addMouseListener(li2);
        addMouseMotionListener(li1);
   }
   public void setBackground(Color color) {
     bg = color;
     if(currgame != null) {currgame.setBackground(bg);currgame.getContentPane().setBackground(bg);}
   }
   public Color getBackground() {
     return bg;
   }
   /**
    * Starts the thread for the runmover panel.
    */
   public void startrunning() {
     if((runthread == null || !runthread.isAlive()) && !stoprun && !pause) { // rb 15/1/07
         runthread = new Thread(this);
         runthread.start();
      }
   }
   /**
    * Pauses the thread and sets the stoprun flag so that, when a suitable point in
    * the threads execution is reached, the thread will stop itself.
    */
   public void stop() {
      short count = 0;
      currgame = null;
      inputmover = moverwantskey = null;
      stoprun = true;
      while(runthread != null && runthread.isAlive()
               && ++count < 200){
           u.pause(100);
      }
      for(short i=0;i<mtot;++i) {
             m[i].manager = null;
             m[i]=null;
      }
      mtot=0;
   }
   /**
    * <li>Returns if there is no runmover's thread running.
    * <li>Ensures the image only is drawn if a menu is in use. Otherwise the image
    * is redrawn and flags set that cause the other draw method to redraw the screen.
    * @param g Graphics context to be used
    */
   public void paint(Graphics g) { // let normal painting cover it
      int i;
      if(!canrun)                                                         //If 1
        return;                                         //A runmover's thread is not running
      if(onmainscreen && (i=sharkStartFrame.mainFrame.                     //If 2
                      manBar1.getSelectionModel().getSelectedIndex()) >=0//The menu is in use
              && i < sharkStartFrame.mainFrame.manBar1.getMenuCount()
              && sharkStartFrame.mainFrame.manBar1.getMenu(i).isSelected()) {
          if(offg != null)                                                 //If 2.1
              g.drawImage(offscreen[curroffscreen^1],0,0,screenwidth,screenheight,bstartx,0,bstartx+screenwidth,screenheight,null);   //rb
      }
      else {                                                             //Else 2
//startPR2004-11-04^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         if (offg != null && (runthread == null || !runthread.isAlive()))
           g.drawImage(offscreen[curroffscreen ^ 1],0,0,screenwidth,screenheight,bstartx,0,bstartx+screenwidth,screenheight, null);   //rb
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         copyall = true;
         haltrun = false;
         startrunning();
         copyall = true;
      }
   }
   /**
    * <li>If a game is running sets the size of the game's panel to that of the
    * runmover's panel.
    * <li>Creates offscreen buffers to contain a rectangle the same colour as the
    * current background colour. (This may be to stop flickerf?)
    */
   public void reset() {
//startPR2006-08-19^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       if(Demo_base.isDemo && !Demo_base.finishedSetup) return;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      short i,gcCounter = 0;
      if(onmainscreen) {
          sharkStartFrame.mainFrame.headfont[0]=null;
          sharkStartFrame.mainFrame.titfont[0]=null;
      }
      screenwidth = getWidth();
      screenheight = getHeight();
      screenmax = Math.max(screenwidth,screenheight);
      if(screenwidth<=0 || screenheight <= 0) return;
      if(currgame != null) {
         currgame.screenwidth = screenwidth;
         currgame.screenheight = screenheight;
         currgame.screenmax = screenmax;
      }
      while(gcCounter<3) {
        try {
          if(usepool) {
            offscreen[0] = buffers[0];
            offscreen[1] = buffers[1];
           if(savefixed) {
              offscreenfixed = buffers[2];
              savefixedg = (Graphics2D) offscreenfixed.getGraphics();
            }
            try {
              bstartx = this.getLocationOnScreen().x - leftx;
              if (bstartx < 0)
                bstartx = 0;
              else if (bstartx > 0)
                ++bstartx;
            }
            catch(IllegalComponentStateException e) {bstartx = 0;}
          }
          else {
            offscreen[0] = createImage(screenwidth, screenheight);
            offscreen[1] = createImage(screenwidth, screenheight);
            if (offscreen[0] == null || offscreen[1] == null) {
              screenwidth = screenheight = 0;
              return;
            }
            if (offscreen[0] == null || offscreen[1] == null) {
              screenwidth = screenheight = 0;
              return;
            }
            offscreenfixed = null;
          }
          spritesave = null;
          offgx[0] = (Graphics2D)offscreen[0].getGraphics();
//startPR2008-10-12^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//          offgx[0].setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
          if(sharkStartFrame.antialiasing) offgx[0].setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2007-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          offgx[0].setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          offgx[1] = (Graphics2D)offscreen[1].getGraphics();
//startPR2008-10-12^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//          offgx[1].setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
          if(sharkStartFrame.antialiasing) offgx[1].setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2007-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          offgx[1].setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          offg = (Graphics2D)offgx[curroffscreen];
          if(usepool && bstartx > 0) {
            offgx[0].translate(bstartx,0);
            offgx[1].translate(bstartx,0);
            if(savefixed) savefixedg.translate(bstartx,0);
          }
          offgx[0].setColor(bg);
          offgx[0].fillRect(0, 0, screenwidth, screenheight);
          offgx[1].setColor(bg);
          offgx[1].fillRect(0, 0, screenwidth, screenheight);
//startPR2010-02-17^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//          ong = (Graphics2D)getGraphics();
//          if(ong!=null)ong.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
//endPRtoupdate^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          copyall = true;
          canredraw = false;
          if(getTopLevelAncestor() != null) getTopLevelAncestor().repaint();
          return;                              //  ok
        }
        catch (OutOfMemoryError e) {
          offscreen[0] = null;
          offscreen[1] = null;
          offgx[0] = null;
          offgx[1] = null;
          offg = null;
          ong = null;
          System.gc();
          u.pause(500);
          ++gcCounter;
        }
      }
      screenwidth = screenheight = 0;   // force retry if failed 3 times
   }
   //-----------------------------------------------------
   public void addTempMover(mover m1,int x1,int y1) {
      m1.temp = true;
      addMover(m1,x1,y1);
   }
   //-----------------------------------------------------
   public  boolean fitMover(mover m1,int x1,int y1,int x2, int y2) {
      int i,x,y,repeats=0;
      int gap = mover.WIDTH/30;
      outer:while(repeats++ < 1000) {
         x = x1 + u.rand(x2-x1-m1.w);
         y = y1 + u.rand(y2-y1-m1.h);
         for(i=0; i<mtot; ++i) {
            if( m[i].w <x2-x1 && m[i].h < y2-y1
               && m[i].x <= x + m1.w + gap
               && x <= m[i].x + m[i].w + gap
               && m[i].y <= y + m1.h
               && y <= m[i].y + m[i].h) continue outer;
         }
         addMover(m1,x,y);
         return true;
      }
      return false;
   }
   /**
    * Adds a mover
    * @param m1 mover to be added
    * @param x1 x coordinate where mover is added
    * @param y1 y coordinate where mover is added
    */
   public  void addMover(mover m1,int x1,int y1) {
      if(savefixed && !m1.keepMoving)
        redrawoffscreen = true;
      m1.ended = false;
      m1.mouseOver = mousex > x1 && mousex < x1+m1.w && mousey > y1 && mousey < y1 +m1.h;
      m1.manager = this;
      m1.x = m1.fromx = m1.tox = x1;
      m1.y = m1.fromy = m1.toy = y1;
      addremove(m1,0);
   }



   /**
    * @param m1 Mover to be removed
    */
   public void removeMover(mover m1) {
      for(short i=0;i<mtot;++i) {
          if(m[i] == m1) {
             if(savefixed && !m[i].keepMoving)
               redrawoffscreen = true;
             if(moverwantskey == m1)
               moverwantskey = null;
             if(!savefixed)
               clearover(i);
             m1.kill = true;
             if(sentencefrommover != null && sentencefrommover == m1.sayit) {  //If 2.1.1 - sentencefrommover does not contain same headings as sayit
               if (sentencemarker != null) { //If 2.1.1.1 - There is a sentencemarker
                 sentencemarker.kill = true;
                 sentencemarker = null;
               }
             }
             return;
          }
      }
    }

   public void removeGamesIcons() {
        for(int i=mtot-1; i>=0; i--) {
            if(m[i].isGamesIconPart)removeMover(m[i]);
        }
    }

    /**
     *
     */
    public void removeAllMovers() {
        
      for(short i=0;i<mtot;++i){
             m[i].kill=true;
          }
          copyall=true;
    }
    /**
     * @param m1 Mover to be found
     * @return True if m1 is a mover and false if it is not.
     */
    public boolean isMover(mover m1) {
      for(short i=0;i<mtot;++i) {
          if(m[i] == m1) return true;
      }
      return false;
    }
    /**
     * @param m1 mover to be brought to the top
     */
    public  void bringtotop(mover m1) {
        
      for(short i=0;i<mtot-1;++i) {
          if(m[i] == m1) {
             System.arraycopy(m,i+1,m,i,mtot-i-1);
             m[mtot-1] = m1;
             copyall=true;
             return;
          }
      }
   }
   /**
    * Finds where a particular mover is positioned in the array of movers
    * @param m1 Mover whose position is to be found
    * @return Position of mover.
    */
  public short pos(mover m1) {
      for(short i=0;i<mtot;++i){
        if(m[i] == m1){
          return i;
        }
      }
      return -1;
   }
 /**
 * @param m1 Mover to be put to the bottom of other movers.
 */
   public  void puttobottom(mover m1) {
      for(short i=0;i<mtot;++i) {
          if(m[i] == m1) {
             if(i>0) {
                System.arraycopy(m,0,m,1,i);
                m[0] = m1;
                copyall=true;
             }
             return;
          }
      }
   }
   /**
    * Causes whole screen to be redrawn if it is not positioned where it should be
    * and it is already drawn on the screen.
    * @param which Mover to be checked.
    */
   void clearover(short which) {
      mover m1 = m[which];
      for(short i=(short)(mtot-1);i>which;--i) {
           if(m[i].drawn
                && m[i].x <= m1.x+m1.w && m[i].x + m[i].w >= m1.x
                && m[i].y <= m1.y+m1.h && m[i].y + m[i].h >= m1.y) {
             copyall = true;
           }
      }
   }
   /**
    * This method loops while the runmovers panel is needed.
    * <li>The method returns if the thread is to be paused or stopped .
    * <li>Ensures the runmovers panel fits the screen.
    * <li>Stops the movement of movers when necessary.
    * <li>Redraws the panel if a specified interval has passed.
    * <li>Sets the automatic movement of focus for single switch access.
    * <li>Necessary evaluation and updating are done if a mouse move has occurred.
    * <li>Restarts the current game, if there is one, when a games option has been changed.
    * <li>Pictures are added to the runmovers screen where necessary.
    * <li>Movers are all drawn on the panel.
    * <li>Extra activities, that the games cannot do while drawing is in progress, are done.
    * <li>Hides or shows the cursor as necessary.
    */
   public void run() {
      long newtime, oldtime = 0;
      int i;
      Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
      refreshat = System.currentTimeMillis() + 2000; // make sure redrawn totally after 2 sec
      lastactivity = System.currentTimeMillis();   // rb 23/1/06
      while(true) {
        if(Thread.currentThread() != runthread)                           //If 1
          return;
        if(System.currentTimeMillis() - lastactivity  > idletime && canquiesce) {  // rb 23/1/06
          runthread=null;                                            // rb 23/1/06
          return;                                                    // rb 23/1/06
        }
//        if(stoprun || haltrun || pause ||  mouseOutside && lastttpaint != tooltip_base.painted    // rb 15/1/07        //If 2
        if(stoprun || haltrun || pause    // rb 15/1/07        //If 2
           || onmainscreen && sharkStartFrame.gametot > 0) {//There is a game running
           runthread = null;         //AND EITHER the thread is to be paused or stopped,
           haltrun = false;          //OR the tooltip_base in use is not the last one painted.
          return;
        }
         if(getWidth()!=screenwidth || getHeight() != screenheight ){       //If 3
           reset();           //The width is not the same as the screen's width
        }                     //and the height is not the same as the screen's height
        //Stops all movement when the below condition is FALSE.
        if(waitforsay && System.currentTimeMillis() < spokenWord.endsay    //If 4
           || screenwidth == 0 || screenheight == 0//There is something being or waiting to be said
           || offg==null     //OR There is no screen OR There is no off screen graphics context
           || dontstart      //OR The game should'nt start yet
           || onmainscreen   //OR The current panel on the main screen
           && (i = sharkStartFrame.mainFrame. //AND There is a selection for the main menu bar
               manBar1.getSelectionModel().getSelectedIndex()) >= 0
           && i < sharkStartFrame.
               mainFrame.manBar1.getMenuCount()//AND The item chosen from the menu is present.
           && sharkStartFrame.mainFrame.manBar1.
               getMenu(i).isSelected()         //AND There is something selected
           || tooltipmover1 != null            //OR Help is not active and so there is no tooltipmover
           && (System.currentTimeMillis() >
               tooltipmover1.starttime2+tooltipmover1.delayadjuster+tooltipmoverdelay    //AND current time is greater that the start time for the mover + 500 milliseconds
           || isMover(tooltipmover1))) {       //OR Does the tooltipmover exist.
           if(tooltipmover1 != null                                       //If 4.1
                && System.currentTimeMillis() >  //There is a tooltipmover
                     tooltipmover1.starttime2+tooltipmover1.delayadjuster+tooltipmoverdelay//AND The tooltipmover has started
                && !isMover(tooltipmover1)){     //AND The tooltipmover has not been added to the list of movers
              addMover(tooltipmover1, tooltipmover1.x, tooltipmover1.y);
            }
           else {                                                         //Else 4.1
             u.pause((int)(REDRAWINT));
             continue;//Starts loop again i.e.busy wait
          }
        }
        oldtime = System.currentTimeMillis();
//startPR2005-10-16^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//        if(refreshat != 0 && oldtime > refreshat){                         //If 5
//          copyall = true;                                    //It is time to redraw the screen
//          refreshat = 0;
//        }
        if(refreshat >0) {
          copyall=true;
          if(oldtime>refreshat){
            refreshat = 0;
            // make sure Mac cursor is correct
            if(shark.macOS)
              macCheckNullCursor();
          }
        }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        if(mousexs != oldmousexs || mouseys != oldmouseys){                //If 6
           mouseMove();                 //The position of the mouse is not the same as it was
        }
        if(currgame != null) {             //If 7
//startPR2005-10-16^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          if(shark.macOS){
            if(!chooser_base.active && !options.active && !currgame.isFocusOwner()){
              // otherwise no game keyboard input on Macs
              currgame.requestFocus();
            }
//            if(macGameSize){
//              currgame.setBounds((int)sharkGame.gameLocation.getX(),
//                                 (int)sharkGame.gameLocation.getX(),
//                                 sharkGame.macGameWidth,
//                                 sharkGame.macGameHeight);
//              validate();
//              macGameSize = false;
//            }
          }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
           currgame.gtime = oldtime-sharkGame.losetime;    // rb 15/01/07//There is a game currently being played
           if(options.changed) {                                           //If 7.1
              options.changed=false;    //Options have been changed and the window closed
              if(!simpleoptions) {
                mtot = 0;
                m  = new mover[0];
              }
              if(currgame != null) {                                       //If 7.1.1
                 currgame.restart();                  //There is a current game.
              }
           }
        }
        if(wantpic != null) {                                              //If 8
          wantpic.add();                    //There is a picture to be displayed
          wantpic = null;
        }
        draw();
        if (currgame != null) {                                            //If 9
          currgame.afterDraw(oldtime-sharkGame.losetime); // rb 15/01/07//There is a current game
//startSS^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
          if (switchGameStarted) {                                     //If 10 - A game is in progress
            if (sharkStartFrame.switchOptions == 1 &&                      //If 10.1
                System.currentTimeMillis() > nextTime && !switchInitialise) { // One switch is in use AND it is time for focus to move - SS
              switchMovement();
            }
            if (switchInitialise) {                                        //if 10.2
              Point position = new Point();
              if (sharkGame.focusInOrder) {
                position = (Point) currgame.pointSwitch2.get(0); //INITIALISES POSITION TO HOLD FIRST POINT FOR SWITCH USE
              }
              else if (!sharkGame.focusInOrder) {                     //else if 10.2
                position = currgame.pointSwitch1[0][0]; //INITIALISES POSITION TO HOLD FIRST POINT FOR SWITCH USE
              }
              int x = position.x * screenwidth / mover.WIDTH;
              int y = position.y * screenheight / mover.HEIGHT;
              mousemoved(x, y);
              switchInitialise = false;
              nextTime = (int) System.currentTimeMillis() +
                  (sharkStartFrame.switchResponse * 1000);
            }
          }
//endSS^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
        }
        if (!onmainscreen && sharkStartFrame.showteachingnotes != null) {  //If 11
                sharkStartFrame.showteachingnotes.dispose();//The runmovers panel is not on the main screen
                sharkStartFrame.showteachingnotes = null;   //AND The teaching notes are to be shown
                copyall = true;
        }
        if(winsprite) {                        //If 12 - The normal windows sprite is in use
            if(winspritevisible) {             //If 12.1 - The windows sprite is visible
                if(!showSprite || mousemovertot > 0){//If 12.1.1 - Sprite is not to be shown
                   winspritevisible = false;                      //OR there are mouse movers
                   setCursor(sharkStartFrame.nullcursor);
//startPR2006-10-13^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//                   currgame.macSetNullCursor = true;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                }
            }
            else {                                                       //Else 12.1
                if(showSprite &&  mousemovertot == 0) {                    //If 12.1.1
                   winspritevisible = true;                   //The sprite is needed
                   setCursor(null);                           //AND There is no mouse mover
                }
            }
        }
        if((newtime = System.currentTimeMillis()) < oldtime + REDRAWINT){  //If 13
           u.pause((int)(oldtime+REDRAWINT-newtime));//There has been time for redrawing to occur
        }
      }
   }
//startSS^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
   /**
    * Provides change of focus when only one switch is in use.
    */
   private void switchMovement(){
     if(currgame != null){
       if (!sharkGame.focusInOrder) {
         if (currgame.pointSwitch1[0][0] != null) { //If 1 - There is a item in the array of points
           if (goDown == true) { //If 1.1 - Focus moves down
             if (currgame.currentPositionX < //If 1.1.1
                 currgame.pointSwitch1.length - 1) { //Current position is within the array
               ++currgame.currentPositionX;
             }
             else { //Else 1.1.1
               currgame.currentPositionX = 0;
             }
           }
           if (goDown == false) { //If 1.2 - Focus moves across
             if (currgame.currentPositionY < //If 1.2.1 - Current position is
                 currgame.pointSwitch1[currgame.currentPositionX].length - 1) { //within the array
               ++currgame.currentPositionY;
             }
             else { //Else 1.2.1
               currgame.currentPositionY = 0;
             }
           }
           //SS INITIALISES POSITION TO HOLD CURRENT POINT FOR SWITCH USE.
           Point position = currgame.pointSwitch1[currgame.currentPositionX][
               currgame.currentPositionY];
           int x = position.x * screenwidth / mover.WIDTH;
           int y = position.y * screenheight / mover.HEIGHT;
           mousemoved(x, y);
         }
       }
       if (sharkGame.focusInOrder) {
         if (!currgame.pointSwitch2.isEmpty()) {
           if (currgame.currentPosition < //If 1.2.1 - Current position is
               currgame.pointSwitch2.size() - 1) { //within the array
             ++currgame.currentPosition;
           }
           else {
             currgame.currentPosition = 0;
           }
           Point position = (Point) currgame.pointSwitch2.get(currgame.currentPosition);
           int x = position.x * screenwidth / mover.WIDTH;
           int y = position.y * screenheight / mover.HEIGHT;
           mousemoved(x, y);
         }
       }
     }
   nextTime = System.currentTimeMillis()+(sharkStartFrame.switchResponse*1000);
   }
//endSS^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
   /**
    * Removes the mover.
    * <li>Saves movers offscreen image if needed.
    * <li>Sets the drawn flag to false so image will not be redrawn.
    * @param m Mover to be removed
    */
   void clear(mover m) {
      int w = m.w * screenwidth / mover.WIDTH;
      int h = m.h * screenheight / mover.HEIGHT;
      int  x =  m.x * screenwidth / mover.WIDTH;
      int  y = m.y * screenheight / mover.HEIGHT;
      if(m.drawn) {
         if(m.mustSave && m.save != null) {
            offg.drawImage(m.save,m.savex,m.savey,m.savex+m.savew,
                           m.savey+m.saveh,0,0,m.savew,m.saveh,null);
         }
         else {
            offg.setColor(this.bg);
            offg.fillRect( m.savex,m.savey, m.savew+1, m.saveh+1);
         }
         updatearea(m.savex, m.savey, m.savex+m.savew, m.savey+m.saveh);
      }
      m.drawn = false;
   }
   //---------------------------------------------------------
   boolean offedge(int x, int y, mover m) {
     return x+m.w > mover.WIDTH  || x < 0
                          || y+m.h > mover.HEIGHT || y < 0;
   }
   /**
    * Draws the mover on the screen and then saves it if necessary
    * @param m Mover to be drawn
    * @param currtime Time
    * @param g Graphics context to be used
    */
   void draw(mover m,long currtime,Graphics g) {
//startPR2004-09-15^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//     int elapsed = (int)(currtime - m.starttime);
       int elapsed;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     int w = m.w;
     int h = m.h;
     int x,y,i,x1,y1;
     currtime -= sharkGame.losetime;  //rb 15/01/07
     if(m instanceof mover3d) {                                            //If 1
           ((mover3d)m).paint(g);                    //If the mover is an instance of mover3d
           x =  m.x * screenwidth / mover.WIDTH;
           y = m.y *screenheight / mover.HEIGHT;
           w =  m.w * screenwidth / mover.WIDTH;
           h = m.h *screenheight / mover.HEIGHT;
           updatearea(x, y, x+w, y+h);
           return;
     }
     elapsed = (int) (currtime - m.starttime);
//startPR2004-09-15^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       if(currtime < m.starttime)
         elapsed = 0;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     if(m.h > 0 && m.w > 0) {           //If 2 - If the mover has a height and a width
        if(m.ti == 0 || elapsed >= m.ti && !m.bouncer) {                   //If 2.1
           elapsed = m.ti;          //No speed has been set for mover movements
           m.x = m.fromx = m.tox;   //OR Sufficient time has passed and so a movement should occur.
           m.y = m.fromy = m.toy;   //AND The mover does not bounce off the edge of the screen
//startPR2008-08-29^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
           if(m.tow != 0){
             m.w = m.tow;
             m.tow = 0;
           }
           if(m.toh != 0){
             m.h = m.toh;
             m.toh = 0;
           }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        }
        else {                                                           //Else 2.1
           m.x = (int)((m.fromx*(m.ti-elapsed) + m.tox*elapsed) /m.ti);
           m.y = (int)((m.fromy*(m.ti-elapsed) + m.toy*elapsed) /m.ti);
//startPR2008-08-29^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
           if(m.tow!=0)m.w = (int)((m.fromw*(m.ti-elapsed) + m.tow*elapsed) /m.ti);
           if(m.toh!=0)m.h = (int)((m.fromh*(m.ti-elapsed) + m.toh*elapsed) /m.ti);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        }
        i = 0;
        if(m.bouncer) {                                 //If 2.2 - The mover bounces
           if(m.x < 0) {                                                   //If 2.2.1
              m.x =  Math.min(-m.x,mover.WIDTH-m.w);   //Mover does'nt have an x coordinate
              m.moveto(m.x - (m.tox - m.fromx), m.y + (m.toy - m.fromy),m.ti);
           }
           if(m.x + m.w > mover.WIDTH) {                                   //If 2.2.2
              m.x =  Math.max(0,2*(mover.WIDTH-m.w) - m.x);
              m.moveto(m.x - (m.tox - m.fromx), m.y + (m.toy - m.fromy),m.ti);
           }
           if(m.y < 0) {                                                   //If 2.2.3
              m.y =  Math.min(mover.HEIGHT-m.h,-m.y);
              m.moveto(m.x + (m.tox - m.fromx), m.y - (m.toy - m.fromy),m.ti);
           }
           if(m.y + m.h > mover.HEIGHT) {                                  //If 2.2.4
              m.y =  Math.max(0,2*(mover.HEIGHT-m.h) - m.y);
              m.moveto(m.x + (m.tox - m.fromx), m.y -(m.toy - m.fromy),m.ti);
           }
        }
        x = m.x * screenwidth / mover.WIDTH;
        y = m.y * screenheight / mover.HEIGHT;
        w = m.w * screenwidth / mover.WIDTH;
        h = m.h * screenheight / mover.HEIGHT;
        if(m.mustSave) {                                                   //If 2.3
           m.savex = Math.max(0,x-2);                            //Mover needs saving
           m.savey = Math.max(0,y-2);
           m.savew = Math.max(0,Math.min(x+w+2,screenwidth)-m.savex);
           m.saveh = Math.max(0,Math.min(y+h+2,screenheight)-m.savey);
        }
        else {                                                           //Else 2.3
           m.savex = Math.max(0,x);
           m.savey = Math.max(0,y);
           m.savew = Math.max(0,Math.min(x+w,screenwidth)-m.savex);
           m.saveh = Math.max(0,Math.min(y+h,screenheight)-m.savey);
        }
        if(m.mustSave && (m.save == null ||m.save.getWidth(this)!= w       //If 2.4
                          || m.save.getHeight(this) != h)) {//Mover needs saving
           m.save = createImage(w+4, h+4);//AND there is no image ready to be saved
           m.saveg = m.save.getGraphics();//OR Image to be saved is not the same size as the mover
        }
        if(x < screenwidth && x + w > 0                                    //If 2.5
           && y < screenheight && y+h > 0) {            //The mover is positioned on the screen
           if(m.mustSave) {                                                //If 2.5.1
              m.saveg.drawImage(offscreen[curroffscreen],    //The mover needs saving
                                0, 0, m.savew, m.saveh,  bstartx+m.savex,
                                m.savey, bstartx+m.savex+m.savew, m.savey+m.saveh, null);
           }
           m.drawn = true;
//startPR2009-08-04^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//           if(!clearWholeScreen && m.ismoving()) {
           if((!shark.linuxOS&&!clearWholeScreen) && m.ismoving()) {
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
             g.setClip(x,y,w,h);
             m.paint(g,x,y,w,h);
             g.setClip(null);
           }
           else m.paint(g,x,y,w,h);
           updatearea(x, y, x +w, y+h);
           if(elapsed == m.ti)
             m.ended = true;
        }
        else if(!m.bouncer )                                         //Else if 2.5
          m.ended = true;                  //The mover does not bounce off the edge of the screen.
        if(m.keepMoving || !m.drawn)                                      //If 2.6
          m.ended = false;                     //The mover is moving OR the mover has'nt been drawn
      }
   }
   /**
    * Sets flag so that the draw method redraws the whole screen.
    */
   public void clearall() {
        copyall=true;
  }
  /**
   * Draws movers
   * <li>An off screen image is created and the movers are drawn onto it
   * <li>Redraws movers only if they have moved (though under certain
   * circumstances all movers are redrawn).
   * <li>Clears movers that are unwanted.
   * <li>Clears the whole screen if necessary.
   * <li>Records updated coordinates for movers.
   */
  public  void draw() {
      long currtime = System.currentTimeMillis();
      short i;
      for(i=0;i<mtot;++i){                                                //For 1
         m[i].changeImage(currtime-sharkGame.losetime);  // rb 15/01/07
      }
//startPR2009-08-04^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      boolean clearall = clearWholeScreen || copyall;
      boolean clearall = shark.linuxOS || clearWholeScreen || copyall;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      copyall = false;
      if(savefixed                                                         //If 2
         && (offscreenfixed == null//The mover is not to be redrawn unless it has moved
         || redrawoffscreen        //AND There is no fixed offscreen image
         || clearall)) {           //OR All movers are to be redrawn even those that are still
         redrawoffscreen = false;  //OR All movers are to be removed
         clearall=true;
         if(offscreenfixed == null) {                                      //If 2.1
            offscreenfixed = createImage(screenwidth,screenheight);//There is no fixed offscreen image
            savefixedg = (Graphics2D)offscreenfixed.getGraphics();
//startPR2008-10-12^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//            savefixedg.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
            if(sharkStartFrame.antialiasing) savefixedg.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         }
         offg.setColor(bg);
         offg.fillRect(0,0,screenwidth,screenheight);
         for(i=0;i<mtot;++i) {                                            //For 2.2
            if(!m[i].keepMoving && !m[i].kill){                            //If 2.2.1
               draw(m[i], currtime,offg);               //The mover is not moving
               if(m[i]!=null)
                m[i].drawn = true;                       //AND The mover does'nt need removing
            }
         }
         savefixedg.drawImage(offscreen[curroffscreen],0,0,screenwidth,screenheight,bstartx,0,bstartx+screenwidth,screenheight,null);
      }
      else if(gradientBg!=null){
            offg.setPaint(gradientBg);
            offg.fill(new Rectangle(0,0,screenwidth, screenheight));
      }
//startPR2009-08-04^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      else if(clearall && !clearWholeScreen){                         //Else if 2
        else if(clearall && (!shark.linuxOS && !clearWholeScreen)){                         //Else if 2
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
           offg.setColor(bg);           //All movers are to be removed
           offg.fillRect(0,0,screenwidth,screenheight);//AND The screen does not need to be cleared
      }
      if(haltrun || stoprun) return;
      if(!clearall) {                            //If 3 - All the movers are not to be removed
         if(!savefixed) {                        //If 3.1 - All movers are to be redrawn
            for(i=(short)(mtot-1);i>=0;--i) {          //For 3.1.1 - Iterate through the movers
                if((m[i].kill                    //If 3.1.1.1 - The mover is to be removed
                   || !m[i].ended)               //OR The mover is moving and needs redrawing
                   && m[i].drawn                 //AND The mover has been drawn
                   && !m[i].dontclear)          //AND The mover is to be removed
                     clear(m[i]);
            }
         }
         else {               //Else 3.1 - Only those movers that have moved are to be redrawn
            for(i=(short)(mtot-1);i>=0;--i) {                             //For 3.1.1
               if((m[i].kill                                               //If 3.1.1.1
                   || !m[i].ended)                             //The mover is to be removed
                   && m[i].drawn                               //OR The mover is still moving
                   && !m[i].dontclear ){                       //AND The mover has been drawn
                      updatearea(m[i].savex, m[i].savey,       //AND The mover should be cleared
                                 m[i].savex + m[i].savew,
                                 m[i].savey + m[i].saveh);
                 }
            }
         }
      }
      mover newinputmover = null;
      boolean mustredraw = false;
      if(mousexs>0 && wantPolygon) addToPolygon();
      for(i=0;i<mtot;++i){                                               //For 4
         if(haltrun || stoprun) return;
         mustredraw |= m[i].keepMoving;
         if(m[i].input != null) {                                          //If 4.1
                newinputmover = m[i];                               //There is input
                if(inputmover == null || inputmover != m[i]) {             //If 4.1.1
                   m[i].ended = false;                //Inputmover contains nothing
                }                                     //OR it is not the same as the current mover.
         }
//startPR2010-05-04^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         else if(m[i].input2 != null) {                                          //If 4.1
                newinputmover = m[i];                               //There is input
                if(inputmover == null || inputmover != m[i]) {             //If 4.1.1
                   m[i].ended = false;                //Inputmover contains nothing
                }                                     //OR it is not the same as the current mover.
         }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         else if(inputmover != null && inputmover != m[i]) {        //Else if 4.1
            m[i].ended = false;                       //inputmover contains something
         }                                            //AND it's contents are not the present mover
         if((clearall || !m[i].ended                                       //If 4.2
             || !m[i].drawn) && !m[i].kill) {         //All movers are to be removed
            draw(m[i], currtime,offg);                //OR The mover is still moving
            m[i].drawn = true;                        //OR The mover has'nt been drawn
         }                                            //AND The mover does'nt need to be removed
         if(m[i].ended && (m[i].endmove()                                  //If 4.3
                           || m[i].temp) || m[i].kill){//Mover is not moving.
            m[i].kill = false;  // in case re-used     //AND The move has ended.
            addremove(null,i);
             --i;
         }
      }
      inputmover = newinputmover;
      if(haltrun || stoprun) return;
      if(!clearall && ux1 == -999999                                       //If 5
         && !mustredraw && !(mousemovertot>0 //All movers are not to be removed
         && mousemovers[0].keepMoving)){     //AND The screen coordinates for updating offscreen graphics has not been set
          return; // no change               //AND Redrawing is'nt needed.
        }                                    //AND There is at most a single mousemover
                                             //AND The mousemover is moving
      if(onmainscreen &&                                                   //If 6
         ((i=(short)sharkStartFrame.mainFrame.manBar1.//The runmovers panel is the main panel
           getSelectionModel().getSelectedIndex()) >=0//AND An item is selected from the menu
              && i < sharkStartFrame.mainFrame.manBar1.getMenuCount()//AND It is a valid menu choice
              && sharkStartFrame.mainFrame.manBar1.getMenu(i).isSelected())//AND It really is selected
//          || mouseOutside && lastttpaint != tooltip_base.painted){         //OR The tooltip_base has been repainted.
          ){         //OR The tooltip_base has been repainted.
         return;
       }
//startPR2010-02-17^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      if (shark.macOS)
//        ong = (Graphics2D)getGraphics();
      ong = (Graphics2D)getGraphics();
      if(ong!=null)ong.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
//endPRtoupdate^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      if(!clearall && ux1 != -999999) {                                    //If 7
         ux1 = Math.max(0,ux1-1);                  //All movers are not to be removed
         uy1 = Math.max(0,uy1-1); //AND The screen coords for drawing offscreen graphics have been set
         ux2 = Math.min(screenwidth,ux2+1);
         uy2 = Math.min(screenheight,uy2+1);
         offg = offgx[curroffscreen ^= 1];
         draw2(ong,ux1,uy1,ux2,uy2);
      }
      else {                                                             //Else 7
         offg = offgx[curroffscreen ^= 1];
         draw2(ong,0,0,screenwidth,screenheight);
      }
      u.pause(10);
      if(haltrun || stoprun) return;
      canredraw = true; //allow repaint if necessary
                // get ready for next time
      ++repaints;
      ++waitfordraw;
      recopy = true;
      if(offscreenfixed != null)  {    //If 8 - there is a fixed off screen image
             if(ux1>=0 && !clearall){                                      //If 8.1
               offg.drawImage(offscreenfixed, ux1, uy1, ux2, //There is an updated coordinate
                              uy2,
                              bstartx+ux1, uy1,bstartx+ ux2, uy2, null);//AND All movers are not to be removed
             }
             else{                                                       //Else 8.1
               offg.drawImage(offscreenfixed,0,0,screenwidth,screenheight, bstartx,0,bstartx+screenwidth,screenheight,null);
             }
      }
//startPR2009-08-04^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      else if(clearWholeScreen)  {                                    //Else if 8
      else if(shark.linuxOS || clearWholeScreen)  {                                    //Else if 8
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
           offg.setColor(bg);                   //Whole screen is to be cleared
           offg.fillRect(0,0,screenwidth,screenheight);
      }
      else /*if(clearall)*/{                                              //Else if 8
       offg.drawImage(offscreen[curroffscreen ^ 1], 0,0,screenwidth,screenheight,bstartx,0,bstartx+screenwidth,screenheight,null);//All movers are to be redrawn even those that are still
     }
//     else {                                                              //Else 8
//       offg.drawImage(offscreen[curroffscreen ^ 1], ux1, uy1, ux2, uy2, ux1, uy1,
//                      ux2, uy2, null);
//     }
     recopy = false;
     ux1=-999999;
     if(sort3dmovers)                                                    //If 9
       sortmovers();                                           //3d movers need to be sorted.



          mover m = mouseOverMover();
          if(m!=null){
            if(m.handcursor)
                setCursor(handcursor);
            else setsprite();//setCursor(defaultcursor);
          }
          else setsprite();//setCursor(defaultcursor);



   }
   synchronized void addremove(mover m1,int i) {   // add m1 or delete mover no i
       try{
      if(m1 != null) {
        if(mtot < m.length) {
            m[mtot] = m1;
        }
        else {
           mover m2[] = new mover[mtot+1];
           System.arraycopy(m,0,m2,0,m.length);
           m2[mtot] = m1;
           m = m2;
        }
        ++mtot;
      }
      else {
        System.arraycopy(m,i+1,m,i,mtot-i-1);
        --mtot;
        m[mtot] = null;
      }
       }
       catch(Exception e){}
   }
   /**
    * Draws the sprite
    * <li>Problems occur if the mouse is moved while the draw routine executes.
    * If this has occurred a recursive call is made to the method with parameter x1
    * set to -1.
    * @param g The graphics context to be used
    * @param x1 x coordinate of left corner
    * @param y1 y coordinate of left corner
    * @param x2 x coordinate of right corner
    * @param y2 y coordinate of right corner
    */
//startPR2004-11-01^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//   void draw2(Graphics g,int x1,int y1,int x2,int y2) {
    synchronized void draw2(Graphics g,int x1,int y1,int x2,int y2) {
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      if(g == null)                                                       //If 1
        return;                                   //there is no graphics context
      int i;
      drawing = true;
      if(showSprite && !cancelSprite                                       //If 2
                    &&(!winsprite     //A sprite is to be shown AND The sprite is not cancelled
                    || sprite != null             //AND The windows sprite is not used
                    && sprite.midcontrol())       //OR There is a sprite AND The sprite is active
                    || mousemovertot>0) {         //OR There is a mouse mover
         if(winsprite) extraclear = true;
         if(newsprite                                                      //If 2.1
               || mousexs != latestmousexs        //A new sprite is required
               || mouseys != latestmouseys        //OR The mouse position recorded is not the upto date mouse position
               || mousemovertot>0                 //OR There is a mouse mover
               && mousemovers[0].keepMoving) {    //AND The first mouse mover in the array is to keep moving
             newsprite = false;
             setsprite();
             if(x1<0) {                                                    //If 2.1.1
               drawsprite(g);// just sprite               //x1 has not been set to -1
             }
             else if(refreshx1 <= x2                                  //Else if 2.1.1
                     && refreshx2 >= x1 // overlap  //There is an overlap between the present
                     && refreshy1 <= y2             //sprite's coordinates and those for refreshing
                     && refreshy2 >= y1) {
                 refreshx1 = Math.min(x1,refreshx1);
                 refreshy1 = Math.min(y1,refreshy1);
                 refreshx2 = Math.max(x2,refreshx2);
                 refreshy2 = Math.max(y2,refreshy2);
                 drawsprite(g);
             }
             else {   // no overlap - separate draws                     //Else 2.1.1
                drawsprite(g);
                drawit(g,offscreen[curroffscreen^1],x1,y1,x2,y2);
             }
         }
         else if(x1<0){                                             //Else if 2.1
           drawing = false;
           return;                                               //x1 has been set to -1
         }
         else if((showSprite                                          //Else if 2.1
                    && !cancelSprite           //The sprite should be shown
                    || mousemovertot>0)        //AND the sprite is needed
                    && spritex2 > spritex1     //AND There is an overlap between the sprite's
                    && spritex1 <= x2 && spritex2 >= x1 //coords and those for use in refreshing
                    && spritey1 <= y2 && spritey2 >= y1) {
                 refreshx1 = Math.min(x1,spritex1);
                 refreshy1 = Math.min(y1,spritey1);
                 refreshx2 = Math.max(x2,spritex2);
                 refreshy2 = Math.max(y2,spritey2);
                 drawsprite(g);
         }
         else {
            drawit(g,offscreen[curroffscreen^1],x1,y1,x2,y2);
         }
      }
      else {                                                             //Else 2
        if(extraclear) {copyall = true; extraclear = false;}
        drawit(g, offscreen[curroffscreen ^ 1], x1, y1, x2, y2);
      }
      if(x1 >= 0 && mzz != null && !isMover(zapmover)) {
         dozapp();
      }
      if(mousex >= 0                                                       //If 4
         && x1 >= 0                                //The mouse has an x coordinate
         && (newsprite      //AND x coordinate passed was > 0 -This stops endless recursive calls
             || mousexs != latestmousexs           //AND A new sprite is wanted
             || mouseys != latestmouseys)          //OR Mouse has moved while drawing in progress
         && (showSprite                            //AND Sprite should be shown
             && !cancelSprite                      //AND sprite has'nt been cancelled
             || mousemovertot>0)){                 //AND There is a mouse mover
        draw2(g, -1, 0, 0, 0);//draws whole screen using recursive call
      }
      drawing=false;
   }
   /**
    * Paints the mouse to the screen
    * <li>If the current sprite is not saved it does so
    * <li>If there is no sprite in the array then one is drawn as long as it is needed
    * @param g Graphics environment to be used
    */
   void drawsprite(Graphics g) {
      byte i;
      int savex1 = Math.max(0,spritex1-1);
      int savex2 = Math.min(screenwidth,spritex2+1);
      int savey1 = Math.max(0,spritey1-1);
      int savey2 = Math.min(screenheight,spritey2+1);
      int savew = savex2-savex1;
      int saveh = savey2-savey1;
      int hs=spritey2-spritey1;
      int ws=spritex2-spritex1;
      short oldrepaints=repaints, oldrepaint2 = ++repaint2;
      boolean inrecopy = recopy;

      if(spritesave == null                                                //If 1
                || spritesave.getWidth(null) < ws+2    //No sprite image is saved
                || spritesave.getHeight(null) < hs+2) {//OR The size of the saved sprite is less than that of the current sprite.
         spritesave = createImage(ws+2,hs+2);
         spritesaveg = (Graphics2D)spritesave.getGraphics();
//startPR2008-10-12^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//         spritesaveg.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        if(sharkStartFrame.antialiasing) spritesaveg.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      }
      Image os = offscreen[i = (byte)(curroffscreen^1)];
      Graphics osg = offgx[i];
      spritesaveg.drawImage(os,0,0,savew,saveh,bstartx+savex1,savey1,bstartx+savex2,savey2,null);
      if(mousemovertot>0){                                                 //If 2
        for(i = 0; i < mousemovertot; ++i) {                //There are mouse movers
          osg.setClip(mousemovers[i].x * screenwidth / mover.WIDTH,
                               mousemovers[i].y * screenheight / mover.HEIGHT,
                               mousemovers[i].w * screenwidth / mover.WIDTH,
                               mousemovers[i].h * screenheight / mover.HEIGHT);

          mousemovers[i].paint(osg, mousemovers[i].x * screenwidth / mover.WIDTH,
                               mousemovers[i].y * screenheight / mover.HEIGHT,
                               mousemovers[i].w * screenwidth / mover.WIDTH,
                               mousemovers[i].h * screenheight / mover.HEIGHT);
        }
        osg.setClip(null);
      }
      else if(sprite!=null && showSprite                                              //Else if 2
              && !cancelSprite                       //Sprite should be shown
              && (!winsprite                         //AND Sprite is not cancelled
                  || sprite != null                  //AND The normal windows sprite is not in use
                  && sprite.midcontrol())) {         //OR There is a sprite in use
         sprite.paint(osg, spritex1, spritey1, ws,hs);//AND The sprite is active
      }
      drawit(g,os,refreshx1,refreshy1,refreshx2,refreshy2);
      osg.drawImage(spritesave,savex1,savey1,savex2,savey2,0,0,savew,saveh,null);
      if((oldrepaints != repaints                                          //If 3
          ||oldrepaint2 != repaint2                          // fix if mucked up copy
          || inrecopy)
         && !makingtrails)
        copyall = true;
   }
   void drawit(Graphics g, Image os, int x1,int y1, int x2, int y2) {
      if(x1<=0 && y1<=0  && x2 >=screenwidth-1 && y2 >= screenheight-1) {
          if(!splitdraw)  g.drawImage(os,0,0,screenwidth,screenheight,bstartx,0,bstartx+screenwidth,screenheight,null);
          else {
            g.drawImage(os, x1, y1, x2, y2 / 2, bstartx+x1, y1, bstartx+x2, y2 / 2, null);
            g.drawImage(os, x1, y2 / 2, x2, y2, bstartx+x1, y2 / 2, bstartx+x2, y2, null);
           }
      }
      else g.drawImage(os,x1,y1,x2,y2,bstartx+x1,y1,bstartx+x2,y2,null);
   }

   void sortmovers() {
     short i,j;
      for(i=0;i<mtot;++i) {
         if(m[i] instanceof mover3d) {
            ((mover3d)m[i]).aftercopyscreen();
            for(j=0;j<i;++j) {
               if(m[j] instanceof mover3d
                 && ((mover3d)m[j]).rcentre.z < ((mover3d)m[i]).rcentre.z) {
                   mover mm = m[j];
                   m[j] = m[i];
                   m[i] = mm;//swap
               }
            }
         }
      }
   }
   /**
    * @return Last mover in the array of movers.
    */
   public mover topmost() {
      if(mtot>0){
        return m[mtot - 1];
      }
      else {
        return null;
      }
   }
   /**
    * <li>If the whole screen needs to be cleared this does nothing.
    * <li>If the coordinates have not been updated previously then these
    * coordinates are used as the updated coordinates.
    * <li>Otherwise the previously updated coordinates and the ones passed
    * as parameters are compared and the smaller used.
    * @param x1 x coordinate of the left corner of the mover's rectangle
    * @param y1 y coordinate of the left corner of the mover's rectangle
    * @param x2 x coordinate of the right corner of the mover's rectangle
    * @param y2 y coordinate of the right corner of the mover's rectangle
    */
   public void updatearea(int x1, int y1, int x2, int y2) {
//startPR2009-08-04^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//     if(clearWholeScreen) return;
     if(shark.linuxOS || clearWholeScreen) return;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      if(ux1 == -999999) {
         ux1 = x1;
         uy1 = y1;
         ux2 = x2;
         uy2 = y2;
      }
      else {
         ux1 = Math.min(x1,ux1);
         uy1 = Math.min(y1,uy1);
         ux2 = Math.max(x2,ux2);
         uy2 = Math.max(y2,uy2);
      }
  }
  /**
   * Creates a new polygon
   */
  public void getPolygon() {
      wantPolygon = true;
      gettingPolygon = new Polygon();
   }
   /**
    * Where necessary records both the mouse's physical and logical positions in the component.
    * @param x Physical x coordinate of mouse position
    * @param y Physical y coordinate of mouse position
    */
   public void mousemoved(int x, int y) {
//startPR2004-11-01^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     // stops a problem for Macs. Without it mousemovers came adrift from the mouse
     // and became stuck on the edge of the screen.
     if (latestmousexs == x && latestmouseys == y)
       return;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR

     lastactivity = System.currentTimeMillis();                                 // rb 23/1/06
     if (!stoprun && !haltrun && (runthread==null || !runthread.isAlive()))     // rb 23/1/06
       startrunning();                                                          // rb 23/1/06

      if(screenwidth <= 0 || dontstart || !canredraw && !wantPolygon
        || onmainscreen && sharkStartFrame.gametot > 0){
       return;
     }
      latestmousexs = x; latestmouseys = y;
      if(showSprite && !cancelSprite && (!winsprite || sprite != null && sprite.midcontrol())
              || mousemovertot>0) {
        if(!drawing) draw2(ong,-1,0,0,0);
      }
      else {
         mousexs = x; mouseys = y;
         mousex = mousexs*mover.WIDTH/screenwidth;
         mousey = mouseys*mover.HEIGHT/screenheight;
      }
   }
   /**
    * Sets position for the sprite
    */
   void setsprite() {
       if(currgame==null){
           setCursor(defaultcursor);
           return;
       }
      int x = latestmousexs, y = latestmouseys;
      mousexs = x; mouseys=y;
      mousex = mousexs*mover.WIDTH/screenwidth; mousey = mouseys*mover.HEIGHT/screenheight;
      if(sprite!=null && ((currgame!=null && currgame.wantSprite) && showSprite                                                        //If 1
         && !cancelSprite                             //Sprite is to be shown
         && mousemovertot == 0                        //AND The sprite is not cancelled
         && (!winsprite                               //AND There are no mouse movers
             || sprite != null                        //AND The windows sprite is not in use
             && sprite.midcontrol()))) {               //OR There is a sprite
         int ws = sprite.w*screenwidth/mover.WIDTH;   //AND The sprite is active
         int hs = sprite.h*screenheight/mover.HEIGHT;
         int sx = Math.min(screenwidth-ws-1,Math.max(1,x-ws/2));
         int sy = Math.min(screenheight-hs-1,Math.max(1,y-hs/2));
         refreshx1 = Math.max(0,Math.min(sx,spritex1)-1);
         refreshy1 = Math.max(0,Math.min(sy,spritey1)-1);
         refreshx2 = Math.min(screenwidth,Math.max(sx+ws,spritex2)+1);
         refreshy2 = Math.min(screenheight,Math.max(sy+hs,spritey2)+1);
         spritex1 = sx; spritey1 = sy;
         spritex2 = sx+ws; spritey2 = sy+hs;
      }
      else if(mousemovertot>0){                                       //Else if 1
            int x1 = screenwidth;                       //There is at least one mouse mover
            int y1 = screenheight, x2 = 0, y2 = 0;
            int wx,wy;
            short i;
            int  sx = screenwidth;
            int  sy = screenheight;
            int sx2 = 0;
            int sy2 = 0;
            mover m;

            for(i=0;i<mousemovertot;++i) {                                //For 1.1
                  m = mousemovers[i];
                  int ws = m.w*screenwidth/mover.WIDTH;
                  int hs = m.h*screenheight/mover.HEIGHT;
                  if(m.moveWithin != null) {                               //If 1.1.1
                     int xx1 = m.moveWithin.x*screenwidth/mover.WIDTH;//There is a rectangle that mover should keep within
                     int xx2 = xx1+m.moveWithin.width*screenwidth/mover.WIDTH;
                     int yy1 = m.moveWithin.y*screenheight/mover.HEIGHT;
                     int yy2 = yy1+m.moveWithin.height*screenheight/mover.HEIGHT;
                     x1=Math.min(xx2-ws-1,Math.max(xx1+1, x+m.mx*screenwidth/mover.WIDTH));
                     y1=Math.min(yy2-hs-1,Math.max(yy1+1, y+m.my*screenheight/mover.HEIGHT));
                  }
                  else {                                                 //Else 1.1.1
                     x1=Math.min(screenwidth-ws-1,Math.max(1,x+m.mx*screenwidth/mover.WIDTH));
                     y1=Math.min(screenheight-hs-1,Math.max(1,y+m.my*screenheight/mover.HEIGHT));
                  }
                  sx = Math.min(sx,x1);
                  sy = Math.min(sy,y1);
                  sx2 = Math.max(sx2,x1+ws);
                  sy2 = Math.max(sy2,y1+hs);
                  m.x = m.tox = x1*mover.WIDTH/screenwidth;
                  m.y = m.toy = y1*mover.HEIGHT/screenheight;
            }
            if(sx2 < sx)                                                  //If 1.2
              return;
            refreshx1 = Math.max(0,Math.min(sx,spritex1)-1);
            refreshy1 = Math.max(0,Math.min(sy,spritey1)-1);
            refreshx2 = Math.min(screenwidth,Math.max(sx2,spritex2) + 1);
            refreshy2 = Math.min(screenheight,Math.max(sy2,spritey2) + 1);
            int w1 = (x2-x1);
            int h1 = (y2-y1);
            spritex1 = sx;
            spritey1 = sy;
            spritex2 = sx2;
            spritey2 = sy2;
       }
    }
    /**
     * <li>Determines whether a mover needs to respond to mouse events or not.
     * <li>Sets up speach ready to be said if a click occurs for movers that need
     * this facility.
     */
    void mouseMove() {
      int x = mousexs, y = mouseys;
      int xx = mousex, yy = mousey;
      boolean gotit = false;
      boolean gotsent = false;
      oldmousexs = x; oldmouseys = y;                                     //A polygon is wanted
      cancelSprite = false;
      for(short i=(short)(mtot-1);i>=0;--i){//For 2 - Iterate through all the movers



         if(currgame != null && currgame.wantcoin<1 && (m[i].sayit != null                                             //If 2.1
         && (m[i].sayitrect != null      //sayit contains headings from a wordlist
         && x < m[i].sayitrect.x + m[i]. //AND The mover is not a rectangle that contains a message
              sayitrect.width            //AND The mouse is within the bounds of the mover
         && x > m[i].sayitrect.x
         && y < m[i].sayitrect.y + m[i].
              sayitrect.height && y > m[i].sayitrect.y
         || m[i].sayitrect == null              //OR The mover is a rectangle containing a message
         && xx < m[i].x + m[i].w && xx > m[i].x //AND The mouse is within the bounds of the mover
         && yy < m[i].y + m[i].h && yy > m[i].y ))) {
         if(sentencefrommover != m[i].sayit) {  //If 2.1.1 - sentencefrommover does not contain same headings as sayit
           if(sentencemarker != null){          //If 2.1.1.1 - There is a sentencemarker
             removeMover(sentencemarker);
           }
           sentencemarker = new marker((m[i].sayitrect == null)?
                  new Rectangle(m[i].x, m[i].y, m[i].w, m[i].h)
                  :new Rectangle(m[i].sayitrect.x*mover.WIDTH/screenwidth,
                     m[i].sayitrect.y*mover.HEIGHT/screenheight,
                     m[i].sayitrect.width*mover.WIDTH/screenwidth,
                     m[i].sayitrect.height*mover.HEIGHT/screenheight));
           sentencefrommover = m[i].sayit;
         }
         gotit = gotsent = true;
         continue;
         }
         if(!gotit && xx < m[i].x + m[i].w && xx > m[i].x                  //If 2.2
            && yy < m[i].y + m[i].h && yy > m[i].y   //The mover has been marked
            && m[i].isOver(x,y)) {                   //AND The mouse is over the mover
             if(!m[i].dontgrabmouse)                                      //If 2.2.1
               gotit = true;                              //The mover is clickable
             if(m[i].mouseSensitive)                                      //If 2.2.2
               cancelSprite = m[i].noSpriteIfOver;        //The mover responds to mouse events
             if(!m[i].mouseOver) {                                         //If 2.2.3
                m[i].mouseOver = true;                    //The mouse is not over the mover
                if(m[i].mouseSensitive)                                   //If 2.2.3.1
                   m[i].ended = false;                    //The mover responds to mouse events
             }
         }
         else if(m[i].mouseOver){                                     //Else if 2.2
                m[i].mouseOver = false;                   //The mouse is over the mover
                m[i].mouseDown = false;
                if(m[i].mouseSensitive){                                   //If 2.2.1
                   m[i].ended = false;                    //The mover responds to mouse events
                }
         }
      }
      if(!gotsent && sentencefrommover != null) {                          //If 3
         sentencefrommover = null;                        //gotsent is false
         removeMover(sentencemarker);          //AND There was a sentence was taken from a mover
         sentencemarker = null;
      }
    }
    /**
     * <li>When a game is running an escape key press terminates that game.
     * <li>When two switches are in use focus moves down when space is pressed. Then
     * when enter is pressed focus moves across until enter is pressed again to select the item.
     * <li>With single switch access focus moves down using a timer. Either space or return
     * key presses cause focus to start moving across. Then the next space or return
     * causes a selection to be made.
     * <li>Enter or space is treated as a click event by most games.
     * <li>F4 keypress gives next player a turn.
     * <li>Movers that respond to keyhits are passed the keyevent so that the
     * current game can take appropriate action.
     * @param e Key event
     */
    public void keypressed(KeyEvent e) {
      lastactivity = System.currentTimeMillis();                                  // rb 23/1/06
      if (!stoprun && !haltrun && (runthread==null || !runthread.isAlive()))      // rb 23/1/06
        startrunning();                                                           // rb 23/1/06
//startPR2006-07-19^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      char key = (char)e.getKeyChar();
      char key = lastkey =(char)e.getKeyChar();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2010-05-04^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//       if(inputmover != null                                                //If 1
//          && inputmover.input != null) { //There is an input mover AND it has some input
//                 if(inputmover.input.keypressed(e)) {                         //If 1.1
//                        copyall = true; // ask for redraw
//                        startrunning();
//                 }
//       }
       if(inputmover != null){                                           //If 1
          if(inputmover.input != null) { //There is an input mover AND it has some input
                 if(inputmover.input.keypressed(e)) {                         //If 1.1
                        copyall = true; // ask for redraw
                        startrunning();
                 }
       }
       else if( inputmover.input2 != null) { //There is an input mover AND it has some input
            if(((SignOn_base.namesq)inputmover.input2).editable){
                if (((SignOn_base.namesq)inputmover.input2).keypressed(e)) { //If 1.1
                    copyall = true; // ask for redraw
                    startrunning();
                }
             }
         }
      }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       else if (currgame != null){                                     //Else if 1
                               //There is a game currently being played
          int code = e.getKeyCode();
          if(code == KeyEvent.VK_ESCAPE){                                   //If 1.1
            if(TopicTest.stage>=0){
                if(!currgame.exitTopicTest())
                    return;
            }
          else
            currgame.terminate();                       //The key press was the escape key
            return;
          }
          else if(detectEnterSpace) {       //Else if 1.1 The space and enter keys need to be detected
//startSS^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
              switchAccess(code, key);          //SS -Switch access is provided
//endSS^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
            if(code == KeyEvent.VK_ENTER) {                                 //If 1.1.1
              currgame.click(-1,-1);  //If the key pressed is enter and no switch access is in use
              return;
            }
            else if(key == ' '){                                       //Else if 1.1.1
              currgame.click(-2,-2);                //The key pressed was a space
              return;
            }
          }
//startPR2004-08-27^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          // if running on a Macintosh
          if (shark.macOS) {
            if (code == KeyEvent.VK_S) {
              if(e.isMetaDown()){                              //If 1.2
                currgame.nextstudent();                 //Command-S was pressed
                return;
              }
            }
          }
          // if running on Windows
          else {
            if(code == KeyEvent.VK_F4) {                                      //If 1.2
              currgame.nextstudent();                         //The F4 key was pressed
              return;
            }
          }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          currgame.keyhit(key);
       }
       if(moverwantskey != null) {                                          //If 2
         moverwantskey.keyhit(e);                            //The mover responds to keyhits.
       }
    }
    public void keytyped(KeyEvent e) {
      lastactivity = System.currentTimeMillis();                                  // rb 23/1/06
      if (!stoprun && !haltrun && (runthread==null || !runthread.isAlive()))      // rb 23/1/06
        startrunning();                                                           // rb 23/1/06
//startPR2010-05-04^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      if(inputmover != null                                                //If 1
//         && inputmover.input != null) { //There is an input mover AND it has some input
//        if (inputmover.input.keytyped(e)) { //If 1.1
//          copyall = true; // ask for redraw
//          startrunning();
//        }
//      }
       if(inputmover != null){                                                //If 1
         if( inputmover.input != null) { //There is an input mover AND it has some input
            if (inputmover.input.keytyped(e)) { //If 1.1
                copyall = true; // ask for redraw
                startrunning();
            }
         }
         else if( inputmover.input2 != null) { //There is an input mover AND it has some input
            if(((SignOn_base.namesq)inputmover.input2).editable){
                if (((SignOn_base.namesq)inputmover.input2).keytyped(e)) { //If 1.1
                    copyall = true; // ask for redraw
                    startrunning();
                }
             }
         }
      }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2006-07-19^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      if(lastkey != (char)e.getKeyChar()) {
        keypressed(e);
      }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    }
//startSS04-23-02^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
   /**
    * Causes switch focus to move sequentially or by row then column.
    * @param code Code of key pressed
    * @param key The key pressed
    */
   private void switchAccess(int code, char key){
     if(!sharkGame.focusInOrder){  //2-D array of points is in use
        focusNonSequentially(code,key);
     }
     if(sharkGame.focusInOrder){  //1-D array of points is in use
       focusSequentially(code,key);
     }
   }
//endSS^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
//startSS04-23-02^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
   /**
    * Provides sequential movement for switch access
    * @param code The code for the key pressed
    * @param key The key pressed.
    */
   private void focusSequentially(int code, char key){
     if(currgame != null){
       if (!currgame.pointSwitch2.isEmpty()) { //The vector has elements
         if (sharkStartFrame.switchOptions == 2) {
           if (key == ' ') {
             if (currgame.currentPosition <
                 currgame.pointSwitch2.size() - 1) {
               currgame.currentPosition++;
             }
             else {
               currgame.currentPosition = 0;
             }
             Point position = (Point) currgame.pointSwitch2.get(currgame.currentPosition);
             //INITIALISES POSITION TO HOLD CRRENT POINT FOR SWITCH USE
             int x = position.x * screenwidth / mover.WIDTH;
             int y = position.y * screenheight / mover.HEIGHT;
             mousemoved(x, y);
           }
           if (code == KeyEvent.VK_ENTER) {
             Point position = (Point) currgame.pointSwitch2.get(currgame.currentPosition);
             //POSITION NOW HOLDS CURRENT POINT FOR SWITCH USE
             int x = position.x * screenwidth / mover.WIDTH;
             int y = position.y * screenheight / mover.HEIGHT;
             mouseDown(x, y);
           }

         }
         if (sharkStartFrame.switchOptions == 1) {
           if (code == KeyEvent.VK_ENTER || key == ' ') {
             Point position = (Point) currgame.pointSwitch2.get(currgame.currentPosition);
             //POSITION NOW HOLDS CURRENT POINT FOR SWITCH USE
             int x = position.x * screenwidth / mover.WIDTH;
             int y = position.y * screenheight / mover.HEIGHT;
             mouseDown(x, y);
           }
         }
       }
     }
   }
//endSS^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
//startSS04-23-02^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
   /**
    * Used with switch access to move focus first downwards and then after input across
    * @param code The code of the key pressed
    * @param key The key pressed
    */
   private void focusNonSequentially(int code, char key){
     if(currgame != null){
       if ( (sharkStartFrame.switchOptions == 2)                           //If 1
           && (currgame.pointSwitch1[0][0] != null)) { //2 switches are in use AND There are points referenced for switch use
         if (code == KeyEvent.VK_ENTER) { //If 1.1 - The key pressed was the enter key
           if (goDown == true) {                                           //If 1.1.1
             goDown = false;                                        //Focus for switches is going down
           }                                                             //Else 1.1.1
           else { //Focus for switches is going across
             Point position = currgame.pointSwitch1[currgame.currentPositionX][
                 currgame.currentPositionY];
             //POSITION NOW HOLDS CURRENT POINT FOR SWITCH USE
             int x = position.x * screenwidth / mover.WIDTH;
             int y = position.y * screenheight / mover.HEIGHT;
             mouseDown(x, y);
             goDown = true;
             currgame.currentPositionY = 0;
           }
           return;
         }                                                            //Else if 1.1
         else if (key == ' ') { //Key pressed was a space
           if (goDown == true) {                  //If 1.1.1 - Focus for switches is going down
             if (currgame.currentPositionX < currgame.pointSwitch1.length - 1) { //If 1.1.1.1
               currgame.currentPositionX++; //The position of focus is less the length of the array
             }
             else {                                                      //Else 1.1.1.1
               currgame.currentPositionX = 0;
               if(currgame.pointSwitch1.length  == 1)
                 goDown = false;
             }
           }                                                               //If 1.1.2
           if (goDown == false) { //The focus is moving across
             if (currgame.currentPositionY <                                        //If 1.1.2.1
                 currgame.pointSwitch1[currgame.currentPositionX].length - 1) { //The position of focus is less the length of the array
               currgame.currentPositionY++;
             }
             else {                                                      //Else 1.1.2.1
               currgame.currentPositionY = 0;
             }
           }
           Point position = currgame.pointSwitch1[currgame.currentPositionX][
               currgame.currentPositionY];
           //INITIALISES POSITION TO HOLD CuRRENT POINT FOR SWITCH USE
           int x = position.x *screenwidth/mover.WIDTH;
           int y = position.y *screenheight/mover.HEIGHT;
           mousemoved(x, y);
         }
       }
       if ( (sharkStartFrame.switchOptions == 1)                           //If 2
           && (currgame.pointSwitch1[0][0] != null)) { //Single switch access is in use
         if (code == KeyEvent.VK_ENTER || key == ' ') { //If 2.1 - Enter or space key pressed
           if (goDown == true) {                                           //If 2.1
             goDown = false; //Focus is moving down
           }
           else {                                                        //Else 2.1
             Point position = currgame.pointSwitch1[currgame.currentPositionX][
                 currgame.currentPositionY];
             //POSITION NOW HOLDS CURRENT POINT FOR SWITCH USE.
             int x = position.x * screenwidth / mover.WIDTH;
             int y = position.y * screenheight / mover.HEIGHT;
             mouseDown(x, y);
             goDown = true;
             currgame.currentPositionY = 0;
           }
         }
       }
     }

}
//endSS^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
   /**
    * @return The mover the mouse is over.
    */
   public mover mouseOverMover() {
      for(short i=(short)(mtot-1);i>=0;--i) {
         if(mousex < m[i].x + m[i].w && mousex > m[i].x
           && mousey < m[i].y + m[i].h && mousey > m[i].y
                && m[i].isOver(mousexs,mouseys))
         return m[i];
      }
      return null;
   }



   /**
    * Gives the desired response to mouse down events, A click occurs if:-
    * <li>Says sentences for movers
    * <li>Mouse mover does'nt grab mouse and a mouse click is done
    * <li>If the mouse mover grabs the mouse then the mover that the mouse is over
    * has it's mousedown property set to true. If the mover takes input then
    * the input is clicked otherwise the mover is clicked
    * @param x x coordinate of the mouse event
    * @param y y coordinate of the mouse event
    */
   //---------------------------------------------------------
   public void mouseDown(int x, int y) {
     lastactivity = mousepressedat = System.currentTimeMillis();
     mousedownpoint = new Point(x,y);
     mousedragged = false;
     if (!stoprun && !haltrun && (runthread==null || !runthread.isAlive()))     // rb 23/1/06
       startrunning();                                                          // rb 23/1/06
//startPR2009-11-13^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     mouseMove();
//endPRwin7^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     for(short i=(short)(mtot-1);i>=0;--i) {
         if(m[i].mouseOver) {
            m[i].mouseDown = true;
            if(m[i].mouseSensitive) m[i].ended = false;
            break;
         }
     }
     if(currgame != null) {
       if(mousemovertot>0 && !mousemovers[0].dontgrabmouse)     mousemovers[0].mouseDown = true;
       if (currgame.clickonrelease)         return;
    }
     mouseDown2(x,  y);
   }
   public void mouseDown2(int x, int y) {
      long mousedowntime2 = System.currentTimeMillis();
      
      int clickgap = 1000;
//      boolean foundone = false;
      if(runthread == null || !runthread.isAlive())                       //If 1
        return;                                                //There is no thread running
      if(currgame != null) {                                      //If 2 - A game is running
         if(sentencefrommover != null               //If 2.1 - There is something to be said
            && spokenWord.isfree()) {                        //AND it can be said
            if(!spokenWord.findandsaysentence(sentencefrommover)){         //If 2.1.1
               spokenWord.findandsay(sentencefrommover);     //There is nothing to be said
            }
            return;
         }
        if(currgame.click(x*mover.WIDTH/screenwidth,                       //If 2.2
                           y*mover.HEIGHT/screenheight)  //If there has been a click
               && !currgame.completed && sentencemarker==null){                  //AND the current game is not finished
              mousedowntime = mousedowntime2;
              return;
         }
      }
      else if(mousedowntime2 - mousedowntimex > 200)   {             //Else if 2
         mouseclick(x,y);                          
         mousedowntimex = mousedowntime2;
      }
      if(mousemovertot > 0 && !mousemovers[0].dontgrabmouse) {               //If 3
           if((((touchchanges && mousedragged) || mousedowntime2 - mousedowntime > upAfterDownDragDelay)) || allclicks) {                      //If 3.1
                mousemovers[0].mouseClicked(x,y);  //300 ms have passed since last click
                mousedowntime = mousedowntime2;
                return;
           }
           clickgap = 500;
      }
      else {                                                             //Else 3
         long olddown = mousedowntime;
         loop1:for(short i=(short)(mtot-1);i>=0;--i) {                          //For 3.1
             if(m[i]!=null && m[i].mouseOver){                                          //If 3.1.1
                if((m[i].sharkim!=null)&&(!m[i].sharkim.covers(new Rectangle(
                    x-m[i].sharkim.hitspread,
                    y-m[i].sharkim.hitspread,
                    m[i].sharkim.hitspread*2,m[i].sharkim.hitspread*2)))){
                        continue loop1;
                }
                clickgap=500;                                  //Mouse is over the mover
                if(mousedowntime2 - olddown > 500 || allclicks) {                       //If 3.1.1.2
                    if(m[i].input != null) {                               //If 3.1.1.2.1
                       m[i].input.mouseClick(x,y );//The mover has input associated with it
                    }
                    else
                        m[i].mouseClicked(x,y);                         //Else 3.1.1.2.1
                }
                mousedowntime = mousedowntime2;
                return;
             }
         }
      }
      if(currgame != null && mousedowntime2 - mousedowntime > 1000)      //If 4
        currgame.badclick(x, y);                                //There is a current game
      if(mousedowntime2 - mousedowntime > clickgap)                      //If 5
        mousedowntime = mousedowntime2;//the time between clicks is greater than that allowed
  }
  /**
   * To be overwritten
   * @param x x Position of mouse
   * @param y y Position of mouse
   */
  public void mouseclick(int x, int y) {}
  /**
   * Gives the desired response to mouse up events, A mouse up event occurs if:-
   * @param x x coordinate of the mouse event
   * @param y y coordinate of the mouse event
   */
   public void mouseUp(int x, int y) {
      if(runthread == null || !runthread.isAlive())                       //If 1
        return;                                          //Runmovers panel is not running
      if(screenwidth <= 0)                                                 //If 2
        return;                                          //The runmovers panel is not there
      int xx = x*mover.WIDTH/screenwidth;
      int yy = y*mover.HEIGHT/screenheight;
      double dist = u.getDistanceBetween(mousedownpoint, new Point(x,y));
      if(dist>dragQualifyingDistance){
          mousedragged=true;
      }
      short i;
      for(i=0;i<mtot;++i) {                                               //For 3
         if(m[i].mouseDown) {                                              //If 3.1
            m[i].mouseDown = false;                  //Mover's mouse down variable is set.
            if(m[i].mouseSensitive)                                       //IF 3.1.1
              m[i].ended = false;                    //Mover responds to mouse events
         }
         if(m[i].hyperlink!=null && m[i].mouseOver){
             u.launchWebSite(m[i].hyperlink);
         }
//startPR2009-11-13^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         m[i].mouseUp(x,y);
//endPRwin7^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      }
      if(currgame != null && (currgame.clickonrelease || 
              ((currgame.clickenddrag && System.currentTimeMillis() - mousedowntime > upAfterDownDragDelay)
              &&  (!touchchanges || mousedragged))) // only click on up when it's a drag
              ) {  // end of drag
          mouseDown2(x,y);
      }
   }
   /**
    * Causes the mover to become attached to the mouse so it moves with the mouse.
    * @param m Mover to be attached to mouse
    */
   public void attachToMouse(mover m) {
      moveWithMouse(m);
      m.mx = m.x - mousex;
      m.my = m.y - mousey;
   }

   public void attachToMouseNoKill(mover m) {
      moveWithMouseNoKill(m);
      m.mx = m.x - mousex;
      m.my = m.y - mousey;
   }
   
   public void setTouchDragGame(){
    touchchanges = true;
    upAfterDownDragDelay = 0;
   }


   /**
    * Turns mover passed into a mouse mover so it can move with the mouse
    * @param m mover that needs to move with the mouse
    */
   public void moveWithMouse(mover m) {
      short i;
      newsprite = true;
      m.kill = true;
      for(i = 0;i<mousemovertot;++i) {
         if(mousemovers[i] == m) return;  // already there
      }
      if(mousemovertot >= mousemovers.length) {
         mover mv[] = new mover[mousemovers.length+1];
         System.arraycopy(mousemovers,0,mv,0,mousemovers.length);
         mousemovers = mv;
      }
      mousemovers[mousemovertot++] = m;
      copyall=true;
   }

   private void moveWithMouseNoKill(mover m) {
      short i;
      newsprite = true;

      for(i = 0;i<mousemovertot;++i) {
         if(mousemovers[i] == m) return;  // already there
      }
      if(mousemovertot >= mousemovers.length) {
         mover mv[] = new mover[mousemovers.length+1];
         System.arraycopy(mousemovers,0,mv,0,mousemovers.length);
         mousemovers = mv;
      }
      mousemovers[mousemovertot++] = m;
      copyall=true;
   }

   
   /**
    * Removes a mover that is attached to the mouse. The mover is not then visible.
    * @param m Mover that is to be removed from mouse.
    */
   public void finishwith(mover m) {
      newsprite = true;
      m.kill = false;
      m.ended = false;
      m.drawn = false;
      for(short i = 0;i<mousemovertot;++i) {
         if(mousemovers[i] == m) {
            if(--mousemovertot > i)
              System.arraycopy(mousemovers,i+1,mousemovers,i,mousemovertot-i);
            mousemovers[mousemovertot] = null;
            break;
         }
      }
   }
   /**
    * Removes a mover that is attached to the mouse and leaves it visible on the screen.
    * @param m Mover to be removed from the mouse.
    */
   public void drop(mover m) {
      finishwith(m);
      addMover(m, m.x, m.y);
   }
   public void drop() {
      while(mousemovertot > 0)
          drop(mousemovers[0]);
   }
   public void finishwith() {
      while(mousemovertot > 0)
          finishwith(mousemovers[0]);
   }
   public boolean movesWithMouse(mover m) {
      for(short i=0; i<mousemovertot;++i) {
         if(mousemovers[i] == m) return true;
      }
      return false;
   }
   /**
    * Fragments the mover so it appears to explode
    * @param m mover to be fragmented
    * @param xm x coordinate of click event.
    * @param ym y coordinate of click event.
    * @param savezapp True if zapp to be saved
    */

     
   public void zapp(mover m, int xm, int ym, boolean savezapp) {
     int x1 = m.x * screenwidth / mover.WIDTH;
     int y1 = m.y * screenheight / mover.HEIGHT;
     int w = m.w * screenwidth / mover.WIDTH;
     int h =  m.h * screenheight / mover.HEIGHT;
     int wh = Math.max(w,h)*3/2;
     int topx,midx,botx,topy,midy,boty;
     int  x,y,xx1,xx2,xxx1=xm,xxx2=xm;
     short as[] = new short[MAXZAPBITS];
     double a[] = new double[MAXZAPBITS], ma;
     int dx1,dy1,dx2,dy2,i,ii,j,k;
     int maxx,minx,maxy,miny;
     int move2x[] = new int[MAXZAPBITS], move2y[] = new int[MAXZAPBITS];
     fragmover mz;
     Graphics g;
     Image im = createImage(w,h);
     fragmover mzzz[] = new fragmover[MAXZAPBITS];

     noise.zapp();
     im.getGraphics().drawImage(offscreen[curroffscreen^1],0,0,w,h,bstartx+x1,y1,bstartx+x1+w,y1+h,null);
     for(i=0,j=0;i<MAXZAPBITS;++i) {
         as[i] = (short)(j += MAXZAPBITS + u.rand(MAXZAPBITS));
     }
     for(i=0;i<MAXZAPBITS;++i) {
         a[i] = as[i] * Math.PI * 2 / j;
     }
     dy1 = (int)Math.rint(wh * Math.sin(a[0]));
     if(dy1 == 0)                                                         //If 1
       dy1 = 1;
     dx1 = (int)Math.rint(wh * Math.cos(a[0]));
     for(i=0;i<MAXZAPBITS;++i) {                                          //For 2
        dy2 = (int)Math.rint(wh * Math.sin(a[ii=(i+1)%MAXZAPBITS]));
        dx2 = (int)Math.rint(wh * Math.cos(a[ii]));
        if(dy2 == 0)                                                      //If 2.1
          dy2 = 1;
        if(dy1 < 0) {                                                      //If 2.2
           if(dy1 < dy2) {                                                 //If 2.2.1
              topy = ym + dy1; topx = xm +dx1;
              if(dy2 < 0) {                                                //If 2.1.1.1
                 midy = ym+dy2; midx = xm+dx2;
                 boty = ym; botx = xm;
              }
              else {                                                     //Else 2.1.1.1
                 boty = ym+dy2; botx = xm+dx2;
                 midy = ym; midx = xm;
              }
           }
           else {                                                        //Else 2.2.1
              topy = ym + dy2; topx = xm + dx2;
              midy = ym + dy1; midx = xm +dx1;
              boty = ym; botx = xm;
           }
        }
        else {                                                           //Else 2.2
           if(dy1 > dy2) {                                                 //If 2.2.1
              boty = ym + dy1; botx = xm +dx1;
              if(dy2 > 0) {                                               //If 2.2.1.1
                 midy = ym+dy2; midx = xm+dx2;
                 topy = ym; topx = xm;
              }
              else {                                                     //Else 2.2.1.1
                 topy = ym+dy2; topx = xm+dx2;
                 midy = ym; midx = xm;
              }
           }
           else {                                                        //Else 2.2.1
              boty = ym + dy2; botx = xm + dx2;
              midy = ym + dy1; midx = xm +dx1;
              topy = ym; topx = xm;
           }
        }
        minx = xm; maxx = xm;
        miny = ym; maxy = ym;
        for(y = Math.max(y1,topy); y < midy; ++y) {                       //For 2.3
            xx1 = topx + (midx-topx)* (y-topy)/(midy-topy);
            xx2 = topx + (botx-topx)* (y-topy)/(boty-topy);
            if(xx1 < x1 && xx2 < x1 || xx1 >= x1+w && xx2 >= x1+w)       //If 2.3.1
              continue;
            minx = Math.min(minx,Math.min(xx1,xx2));
            maxx = Math.max(maxx,Math.max(xx1,xx2));
            miny = Math.min(miny,y);
            maxy = Math.max(maxy,y);
        }
        for(; y < Math.min(y1+h,boty); ++y) {                             //For 2.4
            xx1 = midx + (botx-midx)* (y-midy)/(boty-midy);
            xx2 = topx + (botx-topx)* (y-topy)/(boty-topy);
            if(xx1 < x1 && xx2 < x1 || xx1 >= x1+w && xx2 >= x1+w)       //If 2.4.1
              continue;
            minx = Math.min(minx,Math.min(xx1,xx2));
            maxx = Math.max(maxx,Math.max(xx1,xx2));
            miny = Math.min(miny,y);
            maxy = Math.max(maxy,y);
        }
        maxx = Math.max(maxx,x1+w);
        minx = Math.min(minx, x1);
        if(maxx==minx || maxy==miny)                                      //If 2.5
          continue;
        mz = new fragmover(im, minx,miny,maxx+1,maxy+1);
        for(y = miny; y < Math.min(midy,maxy); ++y) {                     //For 2.6
            xx1 = topx + (midx-topx)* (y-topy)/(midy-topy);
            xx2 = topx + (botx-topx)* (y-topy)/(boty-topy);
            if(xx1 < x1 && xx2 < x1 || xx1 >= x1+w && xx2 >= x1+w)        //If 2.6.1
              continue;
            xxx1 = Math.min(xx1,xx2);
            xxx2 = Math.max(xx1,xx2);
            xxx1 = Math.max(xxx1,x1);
            xxx2 = Math.min(xxx2,x1+w);
            mz.yf[y-miny] = y-miny;
            mz.xf1[y-miny] = xxx1-minx;
            mz.xf2[y-miny] = xxx2-minx;
        }
        for(; y < maxy; ++y) {                                            //For 2.7
            xx1 = midx + (botx-midx)* (y-midy)/(boty-midy);
            xx2 = topx + (botx-topx)* (y-topy)/(boty-topy);
            if(xx1 < x1 && xx2 < x1 || xx1 >= x1+w && xx2 >= x1+w)       //If 2.7.1
              continue;
            xxx1 = Math.min(xx1,xx2);
            xxx2 = Math.max(xx1,xx2);
            xxx1 = Math.max(xxx1,x1);
            xxx2 = Math.min(xxx2,x1+w);
            mz.yf[y-miny] = y-miny;
            mz.xf1[y-miny] = xxx1-minx;
            mz.xf2[y-miny] = xxx2-minx;
        }
        mz.orgx = minx - x1;
        mz.orgy = miny - y1;

        mz.x = mz.fromx = minx;
        mz.y = mz.fromy = miny;
        if(a[i] < a[ii])                                                  //If 2.8
          ma = (a[i] + a[ii]) / 2;
        else                                                            //Else 2.8
          ma = (a[i] + a[ii] + 2 * Math.PI) / 2;
        mz.tox = minx +  (int)((screenwidth+screenheight) * Math.cos(ma));
        mz.toy = miny +  (int)((screenwidth+screenheight) * Math.sin(ma));
        mzzz[i] = mz;
        dy1=dy2;
        dx1=dx2;
     }
     while (mzz != null){                                               //While 3
       u.pause(100);
     }
     zapmover = m;
     removeMover(m);
     mzz = mzzz;
  }
  /**
   * Fragments the mover when it is zapped
   */
  void dozapp(){
    int j,k,ii,i;
      fragmover[] mzzz = mzz;
      mzz = null;
      zapmover=null;
      short order[] = u.shuffle(u.select(MAXZAPBITS,MAXZAPBITS));
      long t1 = System.currentTimeMillis()-ZAPTIME/20,t2;
      boolean hadone;
      Graphics gg = ong;
      fragmover mz;
      do {                                                                 //Do 1
        hadone= false;
        for(ii=0;ii<MAXZAPBITS;++ii) {                                    //For 1.1
            i = order[ii];
            if((mz=mzzz[i]) != null) {                                     //If 1.1.1
               if(mz.savex >=0){                                           //If 1.1.1.1
                 gg.drawImage(offscreen[curroffscreen ^ 1], mz.savex, mz.savey,
                              mz.savex + mz.savew, mz.savey + mz.saveh,
                              bstartx+mz.savex, mz.savey, bstartx+mz.savex + mz.savew,
                              mz.savey + mz.saveh, null);
               }
               j = (int)(System.currentTimeMillis()-t1);
               if(j>ZAPTIME) {                                             //If 1.1.1.2
                 mzzz[i] = null;
                 continue;
               }
               k = ZAPTIME - j;
               mz.x = (mz.fromx*k + mz.tox*j)/ZAPTIME;
               mz.y = (mz.fromy*k + mz.toy*j)/ZAPTIME;
               mz.savex = Math.max(0,mz.x);
               mz.savey = Math.max(0,mz.y);
               mz.savew = Math.min(screenwidth,mz.x+mz.w) - mz.savex;
               mz.saveh = Math.min(screenheight,mz.y+mz.h) - mz.savey;
               if(mz.savew <=0 || mz.saveh <= 0){                          //If 1.1.1.3
                 mzzz[i] = null;
               }
               else {                                                    //Else 1.1.1.3
                 hadone = true;
                 mz.paint(gg);
               }
            }
         }
         u.pause(10);
      }while(hadone);                                                   //While 1
      copyall=true;
  }
  /**
   * @param xm point for the polygon
   * @param ym point for the polygon
   * @param x1 point for the polygon
   * @param y1 point for the polygon
   * @param x2 point for the polygon
   * @param y2 point for the polygon
   * @param w point for the polygon
   * @param h point for the polygon
   * @return Polygon created
   */
  Polygon peripoly(int xm,int ym,int x1, int y1, int x2, int y2, int w, int h) {
     Polygon p = new Polygon();
     p.addPoint(xm,ym);
     p.addPoint(x1,y1);
     while(true) {
        if(y1==0 && x1 != w && (y2 != 0 || x2<x1)) {
          p.addPoint(w,0);
          x1=w;
        }
        else if(x1==w && y1 != h && (x2 != w || y2<y1)) {
          p.addPoint(w,h);
          y1=h;
        }
        else if(y1==h && x1 != 0 && (y2 != h || x2>x1)) {
          p.addPoint(0,h);
          x1=0;
        }
        else if(x1==0 && y1 != 0 && (x2 != 0 || y2>y1)) {
        p.addPoint(0,0);
        y1=0;
        }
        else {
          p.addPoint(x2,y2);
          break;
        }
     }
     return p;
  }
  /**
   * Adds further points to a polygon
   */
  void addToPolygon(){
    int i = gettingPolygon.npoints-1;
     if(i<0 || Math.abs(gettingPolygon.xpoints[i] - mousexs)
           + Math.abs(gettingPolygon.ypoints[i] - mouseys) > 1){
         gettingPolygon.addPoint(mousexs,mouseys);
        if(u.crosses(gettingPolygon)) {
            wantPolygon = false;
            gettingPolygon = u.clean(gettingPolygon);
            return;
        }
     }
    if(i>=0) {
       offg.setColor(Color.white);
       offg.drawPolyline(gettingPolygon.xpoints,
             gettingPolygon.ypoints,gettingPolygon.npoints);
       Rectangle r = gettingPolygon.getBounds();
       updatearea(r.x, r.y, r.x+r.width,r.y+r.height);
    }
  }
  /**
   * Clears the off screen graphics content
   */
  public void clear(){
      if(offg!=null){
    offg.setColor(bg);
    offg.fillRect(0,0,screenwidth,screenheight);
      }
  }
  /**
   *
   */
  public void cleartotal() {
      offgx[0].setColor(bg);
      offgx[0].fillRect(0,0,screenwidth,screenheight);
      offgx[1].setColor(bg);
      offgx[1].fillRect(0,0,screenwidth,screenheight);
      copyall = true;
  }
//startSS^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
  /**
   * Used when switches are to be used in the games:-
   * <li>Removes mouse and mouse motion listeners so that games no longer
   * respond to the mouse
   * <li>Initialises the array to hold the first position for focus on entry to the game.
   * <li>Sets up the timer for single switch access.
   */
  public void SwitchSetUp(){
    MouseListener listener[] = this.getMouseListeners();
    for(int i = 0; i< listener.length;i++){
      removeMouseListener(listener[i]);
    }
    MouseMotionListener motionListener[] = this.getMouseMotionListeners();
    for(int i = 0; i < motionListener.length;i++){
      removeMouseMotionListener(motionListener[i]);
    }
    currgame.currentPositionX = 0;
    currgame.currentPositionY = 0;
    switchGameStarted = true;
  }
//endSS^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
//startPR2005-10-16^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  // seems silly but needed on Macs otherwise the cursor might not have been set
   void macCheckNullCursor(){
//startPR2008-06-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     // needed to get the game active again on Macs
     if(shark.macOS&&currgame!=null&&!options.active&&!chooser_base.active){
       Window ws[] = currgame.getOwnedWindows();
//startPR2009-04-17^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//       boolean nonkeypadwindowvisible = false;
       boolean keypadwindow = false;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       for(int i = 0; i < ws.length; i++){
         if(ws[i]!=null){
//startPR2009-04-17^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//           if(((ws[i] instanceof JDialog) && ((JDialog)ws[i]).isUndecorated())||!ws[i].isVisible())continue;
//           if(!(ws[i] instanceof keypad.usekeypad)){
//             nonkeypadwindowvisible = true;
             if((ws[i] instanceof keypad.usekeypad)){
               keypadwindow = true;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
           }
         }
       }
//startPR2009-04-17^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//       if(!nonkeypadwindowvisible)currgame.toFront();
        if(!keypadwindow)currgame.toFront();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     if (getCursor() != Cursor.getDefaultCursor()) {
       if (getCursor() == sharkStartFrame.nullcursor)
         setCursor(sharkStartFrame.macnullcursor);
       else if (getCursor() == sharkStartFrame.macnullcursor)
         setCursor(sharkStartFrame.nullcursor);
     }
   }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR

   public void removeTooltip(){
      if(tooltipmover1 != null) {
            if(isMover(tooltipmover1)) {
              removeMover(tooltipmover1);
              redrawoffscreen=true;
            }
            tooltipmover1=null;
       }
   }

  /**
   * <p>Title: WordShark</p>
   * <p>Description: When a mover's text can be spoken that mover is marked.</p>
   * <p>Copyright: Copyright (c) 2004</p>
   * <p>Company: WhiteSpace</p>
   * @author Roger Burton
   * @version 1.0
   */
  class marker extends mover{
    /**
     * @param r Rectangle to be used
     */
    marker(Rectangle r) {
        super(true);
        keepMoving = true;
        w = r.width;
        h = r.height;
        addMover(this,r.x,r.y);
//startPR2012-06-15^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//        dontclear = true;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     }
     /**
      * Sets Highlights a mover whose text is being read to red.
      * @param g Graphics context to be used
      * @param x x position
      * @param y y position
      * @param w width
      * @param h height
      */
     public void paint(Graphics g,int x, int y, int w, int h) {
        if(mouseOutside)
          return;
        g.setColor(Color.red);
        g.drawRect(x,y,w-1,h-1);
//startPR2012-06-15^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        if(runningGame.currGameRunner!=null && runningGame.currGameRunner.game!=null){
                  float curralpha = 0.8f;
                  Graphics2D g2d = (Graphics2D) g;
                  g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,curralpha));
                  g2d.drawImage(runningGame.currGameRunner.game.speakerIm, x+2, y+2,
                          runningGame.currGameRunner.game.speakerIm.getWidth(null),
                          runningGame.currGameRunner.game.speakerIm.getHeight(null), null);
                  g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     }
  }
  /**
   * <p>Title: WordShark</p>
   * <p>Description: This is a tooltip_base mover that has a paint method</p>
   * <p>Copyright: Copyright (c) 2004</p>
   * <p>Company: WhiteSpace</p>
   * @author Roger Burton
   * @version 1.0
   */
  public void addtooltipmover(String s, int x1,int y1,int x2,int y2){
     new tooltipmover(s,x1,y1,x2,y2);
  }
  public class tooltipmover extends mover {
      String[] message;
      int margin = 4;
      long starttime2 = System.currentTimeMillis();
      int delayadjuster = 0;
      /**
       * Creates the tooltip_base so that it displays the string passed.
       * @param s String to be displayed
       * @param x1 x coordinate
       * @param y1 y coordinate
       * @param x2 x coordinate
       * @param y2 y coordinate
       */
      
      public tooltipmover(String s, int x1,int y1,int x2,int y2){
          super(false);
          init(s, x1,y1,x2,y2, 0);
      }
      
      public tooltipmover(String s, int x1,int y1,int x2,int y2, int adjustdelay){
         super(false);
         init(s, x1,y1,x2,y2, adjustdelay);

     }
      
      void init(String s, int x1,int y1,int x2,int y2, int adjustdelay){
         delayadjuster = adjustdelay;
         if(tooltipmover1 != null) {
            if(isMover(tooltipmover1)) {
              removeMover(tooltipmover1);
              redrawoffscreen=true;
            }
            tooltipmover1=null;
         }
         if(s==null || s.length()==0)
           return;
         keepMoving = true;
         message = spellchange.spellchange(u.splitString(s));
         Dimension d = u.getdim2(message,bigfm,margin);
         w = d.width*mover.WIDTH/screenwidth;
         h = (d.height + margin*2)*mover.HEIGHT/screenheight + margin*2;
         x = Math.max(0,Math.min(mover.WIDTH - w, (x1+x2)/2 - w/2));
         if(y2+h < mover.HEIGHT)
           y = y2;
         else
           y = y1 - h;
         tooltipmover1=this;
     }
     /**
      * Paint method - overridden by games classes
      * @param g graphics object to be painted
      * @param x x coordinate
      * @param y y coordinate
      * @param w1 width
      * @param h1 height
      */
     public void paint(Graphics g,int x, int y, int w1, int h1) {
       FontMetrics m = bigfm[0];
       g.setColor(tooltipbg);
       g.fillRect(x,y,w1,h1);
       g.setColor(tooltipfg);
       g.drawRect(x,y,w1-1,h1-1);
       x += margin;
       y += margin + m.getMaxAscent();
       for(short i=0; i<message.length;++i) {
          u.drawString(g,bigf,bigfm,message[i], x ,y);
          y += m.getHeight();
       }
    }
  }
  /**
   * <p>Title: WordShark</p>
   * <p>Description: Bit of an image that has been zapped</p>
   * <p>Copyright: Copyright (c) 2004</p>
   * <p>Company: WhiteSpace</p>
   * @author Roger Burton
   * @version 1.0
   */
  class fragmover extends mover {
     int yf[],xf1[],xf2[];
     int orgx,orgy;
     int zapy = Math.max(3,screenheight/40);
     /**
      * Constructs fragment of zapped mover
      * @param im1 Image to be used (fragment of a mover that has been zapped)
      * @param xx1 x coordinate of one corner
      * @param yy1 y coordinate of one corner
      * @param xx2 x coordinate of other corner
      * @param yy2 y coordinate of other corner
      */
     public fragmover(Image im1,int xx1,int yy1,int xx2,int yy2) {
       super(false);
       w = (xx2-xx1)*mover.WIDTH/screenwidth;
       h = (yy2-yy1);
       yf = new int[h];
       xf1 = new int[h];
       xf2 = new int[h];
       h = h*mover.HEIGHT/screenheight;
       im = im1;
       savex = -1;
     }
     /**
      * @param gg image to be painted
      */
     public void paint(Graphics gg) {
        int startx,endx,starty,endy;
        for(int i=0;i<yf.length;i+=zapy) {
           startx = Math.max(xf1[i],savex-x);
           endx = Math.min(xf2[i],savex+savew-x);
           starty = Math.max(yf[i],savey-y);
           endy = Math.min(starty+zapy,savey+saveh-y);
           if(starty > savey+saveh-y) break;
           if(starty >=savey-y && startx<endx) {

            gg.drawImage(im, x+startx, y+starty,
                           x+endx, y+endy,
                           orgx+startx, orgy+starty,
                           orgx+endx, orgy+endy,
                           null);
           }
        }
     }
  }
}
