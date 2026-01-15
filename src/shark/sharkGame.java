package shark;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.Vector;
import javax.swing.border.*;
//startPR2008-06-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
import java.net.*;
import java.io.*;
//endPRimagesandvids^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR



/**
 * <p>Title: WordShark</p>
 * <p>Description: This is the parent class to all games classes. It provides methods
 * that can be overridden by the games classes and also methods for them to use.
 * The panel loops doing a periodic draw. Additionally to other methods can have a
 * separate task run in a separate thread. </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: WhiteSpace</p>
 * @author Roger Burton
 * @version 1.0
 */
public class sharkGame
//startPR2004-08-27^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  extends JDialog
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    implements KeyListener{
   public int screenwidth;
   public int screenheight;
   public int screenmax;
   /**
    * Displays the word, currently in use in a game, as a peep.
    */
   public peepword currpeep;
   GridBagLayout layout1 = new GridBagLayout();
   GridBagLayout gb2 = new GridBagLayout();
   GridBagLayout topLayout = new GridBagLayout();
   GridBagConstraints grid = new GridBagConstraints();
   GridBagConstraints grid1 = new GridBagConstraints();
   GridBagConstraints grid2 = new GridBagConstraints();
   /**
    * This is used to contain the movers.
    * It runs them - picking up keypresses and mouse events
    */
   public runMovers gamePanel;
   JPanel helpPanel1;
   /**
    * The help panel that is currently in use.
    */
   public helppanel helpPanel;
   /**
    * The panel at the top of the game that holds the options button, listen button,
    * peep button, score etc
    */
   public JPanel topPanel = new JPanel();
   public JPanel panel2;
   /**
    * Instance of runningGame that is used to access details of game being played.
    */
   public runningGame rgame;
   public Color background;
   /**
    * Set to true if the game is paused (used with the game Trains)
    */
   public boolean pausing;
   Color pausecolor;
   /**
    * Set to true by game if a print button is needed for the top panel.
    */
   public boolean wantsPrint;
   public boolean wantpause;       // rb 15/1/07
   /**
    * When true a timer is placed in the game's top panel
    */
   public boolean timer = true;
   /**
    * When true count of errors is displayed in the top panel
    */
   public boolean errors=true;
   /**
    * Set to true by game if a count of the number of peeps taken is needed
    */
   public boolean peeps;
   /**
    * Set to true by game if a count of the number of deaths is needed
    */
   public boolean deaths;
   
   public boolean bites;   
   
   /**
    * Set to true if a score needs to be displayed for the game
    */
   public boolean gamescore1;
   /**
    * Set to true if more than one player needs their score displayed or the
    * player is signed on additionally to the current student
    */
   public boolean gamescore2;    // set for competitive/cooperative play
   public static student otherplayer;    //  -----   rb 3/3/08 v5
//   int switchevery  = -1;                     //  -----    rb 3/3/08 v5
   int hadscore;                     //  -----    rb 3/3/08 v5
   
//   public static ArrayList lastEducationalGame;
//   static boolean lastreward = true;                  // set if alternative player had last reward_base game  --  rb 3/3/08 v5
   public boolean rewardother,rewardcurr;       // force reward_base at the end of bingo game
   /**
    * Keeps a score for the computer when it is used to play against the current player
    */
   public boolean computerscore;

   public boolean competitivescore;

   public boolean sharednoturns;
   /**
    * Set to true when a display of the student's score for the current session is wanted in a particular game
    */
   public boolean pupilscore = true;
   /**
    * Set to true by game if a peep button is needed for the top panel.
    */
   public boolean peep;
   public boolean freepeep;
   /**
    * Set to true by game if a listen button is needed for the top panel.
    */
   public boolean listen,nolistenpic;
   /**
    * Contains the options for particular games e.g. speed, free peeps e.t.c.
    */
   public String optionlist[];
   public String groupedoptions[];
   public JButton peepButton, listenButton,printButton,optionButton,pauseButton;
   String listentext;
   ImageIcon listenicon;
   JSlider speedView;
   JLabel speedtext;
   JButton helpbutton;
   public boolean rightclick;   // last click is with right button
   /**
    * Displays the time a game has been running when the game is being played
    */
   JLabel timerView;
   JLabel errorView;
   public JLabel peepsView;
   JLabel deathsView;
   /**
    * Displays score for a student
    */
   public JLabel gamescore1View,tot1View;
   /**
    * Displays the score for the additional student if more than one student is playing
    */
//   public JLabel gamescore2View,tot2View;
   /**
    * Displays the student's score for the current session
    */
   JLabel pupilscoreView;
   showtime timerthread;
   /**
    * Keeps a total of the errors that have occurred in a game.
    */
   public int errortot;
   /**
    * Total number of peeps that have occurred in a game.
    */
   public int peeptot;
   /**
    * Number of deaths that have occurred in a game
    */
   public int deathtot;
   /**
    * The current student's game score
    */
   public int gametot1;
   /**
    * The additionally signed on student's game score
    */
   public int gametot2;
   /**
    * Set to true by game if a speed slider is needed for the top panel.
    */
   public boolean wantspeed;
   public Font wordfont = sharkStartFrame.wordfont;
   public Font originalWordfont;
   public int originalScreenwidth, originalScreenheight;
   /**
    * Defines where a picture (that displays the word currently in use in a game)
    * is displayed.
    */
   public int pictureat;
   public Point picturepoint;
   public FontMetrics metrics;
   /**
    * Word currently in use in the game
    */
   public word currword;
   /**
    * True if a sprite is used in the game
    */
   public boolean wantSprite = true;
   /**
    * If true student is playing as a single player. False if student is playing
    * against another student.
    */
//   public boolean solo = true;                // v5 rb 9/3/08
   public long startplaytime = System.currentTimeMillis();
   long endplaytime;
   long startpause;
   /**
    * Displays the current student's name and score.
    */
   JPanel  studentpanel = new JPanel();
   public topicPlayed studentrecord = new topicPlayed(), otherstudentrecord;   // v5
   /**
    * When true the alternative student is playing in a two player game
    */
   public static boolean studentflipped;    // v5 rb 7/3/08 static so continues across games
   public static boolean gamestartflipped;    // v5 rb 7/3/08 static so continues across games
   public static boolean rewardstudentflipped;    // v5 rb 7/3/08 static so continues across games
   public static int forcerewardflipped = -1;    // -1 no force, 0 normal stu's game. 1 other stu's game
   /**
    * Time the current loop in run() method of runmovers started.
    */
   public long gtime = System.currentTimeMillis();
   short thistimescore;
   public byte speed;
   /**
    * Set to true when a game is finished
    */
   public boolean completed;
   public boolean noreward;
   public boolean nogoodforsuper;
   public boolean clickonrelease,clickenddrag=true;   // click on mouse release not mouse pressed
   /**
    * Indicates that a fatal error has occurred and so the game cannot be played.
    */
   public boolean fatal;
   /**
    * Set when a student has played badly and so no reward_base game is given.
    */
   public boolean cancelreward;
   /**
    * Set when some games are ending.
    */
   public boolean clearexit;
   public ebutton ebutton;
   /**
    * True if game is a reward_base game.
    */
   public boolean isreward;
   public boolean nobest;
   boolean stop;
   public boolean nooptionsmess;
  /**
    * The bg colr should not be to close to these values
    */
   public Color bgavoid[];

   public boolean phonics = (!sharkStartFrame.currPlayTopic.phonicsw || wordlist.usephonics)
                                                && sharkStartFrame.currPlayTopic.phonics;
   public boolean phonicsw = wordlist.usephonics && sharkStartFrame.currPlayTopic.phonicsw;
   public boolean blended = phonicsw && sharkStartFrame.currPlayTopic.blended;
   public boolean singlesound = phonics && !phonicsw && sharkStartFrame.currPlayTopic.singlesound;
   public boolean syllables = !phonics && sharkStartFrame.currPlayTopic.phonicsw;
   public boolean nonsensewords = sharkStartFrame.currPlayTopic.nonsense;
   public boolean specialprivate = sharkStartFrame.currPlayTopic.definitions || sharkStartFrame.currPlayTopic.translations;
   public boolean specialprivateon = (sharkStartFrame.currPlayTopic.definitions && wordlist.usedefinitions) ||
           (sharkStartFrame.currPlayTopic.translations &&  wordlist.usetranslations);
   public boolean specialprivateoff = (sharkStartFrame.currPlayTopic.definitions && !wordlist.usedefinitions) ||
           (sharkStartFrame.currPlayTopic.translations &&  !wordlist.usetranslations);
   public boolean fl = sharkStartFrame.currPlayTopic.fl && !specialprivateoff;
   static String pausel=u.gettext("g_pause","label");
   static String pauselx=u.gettext("g_pause","label2");
   static String pauset=u.gettext("g_pause","tooltip");
   static String listenl=u.gettext("g_listen","label");
//   static String listent=u.gettext("g_listen","tooltip_base");
   static String peepl=u.gettext("g_peep","label");
//   static String peept=u.gettext("g_peep","tooltip_base");
   static String freepeepl=u.gettext("g_freepeep","label");
//   static String freepeept=u.gettext("g_freepeep","tooltip_base");
   static String errorsl=u.gettext("g_errors","label");
//   static String errorst=u.gettext("g_errors","tooltip_base");
   static String peepsl=u.gettext("g_peeps","label");
//   static String peepst=u.gettext("g_peeps","tooltip_base");
   static String optionsl=u.gettext("g_options","label");
//   static String optionst=u.gettext("g_options","tooltip_base");
   static String speedl=u.gettext("g_speed","label");
//   static String speedt=u.gettext("g_speed","tooltip_base");
   static String timel=u.gettext("g_time","label");
//   static String timet=u.gettext("g_time","tooltip_base");
   static String deathsl=u.gettext("g_deaths","label"); 
   static String bitesl=u.gettext("g_bites","label");   
//   static String deathst=u.gettext("g_deaths","tooltip_base");
   static String besttimel=u.gettext("g_besttime","label");
//   static String besttimet=u.gettext("g_besttime","tooltip_base");
   static String scorel=u.gettext("g_score","label");
//   static String scoret=u.gettext("g_score","tooltip_base");
//startRB2004-10-24^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//   static String studentt = u.gettext(shark.macOS ? "g_student_mac":"g_student","tooltip_base");
   String pressf1 = u.gettext(shark.macOS ? "g_pressf1_mac":"g_pressf1","title");
   String press = u.gettext("g_press_reward","title");
//endRBmac^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2007-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    static String printl = u.gettext("g_print","label");
//    static String printt=u.gettext("g_print","tooltip_base");
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   /**
    * String contains the help that is currently needed
    */
   String currhelp;
   /**
    * Picture that is displayed (of the word currently in use in a game).
    */
   public showpicture currshowpicture;
   /**
    * The student's sprite - used to differentiate this sprite from the alternative
    * sprite when two students are playing a single game.
    */
   sharkImage oldsprite;
   /**
    * Alternative sprite - used for other student when two students are playing a game.
    */
   sharkImage altsprite;
   /**
    * Used when two players are present - this is the student who was originally
    * signed on
    */
//   static student oldstudent;
   int timetaken;
   public static long losetime;                                                // rb 15/1/07
   public static long gtime() {return System.currentTimeMillis()-losetime;}    // rb 15/1/07
   public static int MAXSAMETOPIC = 20;    // rb 22/02/08 v5
//startSS^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
   /**
    * Used for switch access - when true each mover is focused in turn (uses 1-d array)
    * when false a row is chosen then a selection is made(uses 2-d array).
    */
   public static boolean focusInOrder;
   /**
    * 2-D ARRAY OF POINTS FOR SWITCH USE - TO REFERENCE MOVERS
    */
   public Point pointSwitch1[][];
   /**
    * Vector OF POINTS FOR SWITCH USE - TO REFERENCE MOVERS
    */
   public Vector pointSwitch2 = new Vector();
   /**
   *  POSITION IN 1-D ARRAY OF POINTS USED WITH SWITCHES
   */
  int currentPosition;
  /**
   *  x POSITION IN 2-D ARRAY OF POINTS USED WITH SWITCHES
   */
  public int currentPositionX;
  /**
  * y POSITION IN 2-D ARRAY OF POINTS USED WITH SWITCHES - SS
  */
  public int currentPositionY;

//endSS^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
//startPR2004-10-23^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  /**
  * The Point that is the top left corner of the game's window
  */
  public static Point gameLocation;
  /**
  * The width if the game's window
  */
  public static int macGameWidth;
  /**
  * The height of the game's window
  */
  public static int macGameHeight;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2006-10-13^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//  public boolean macSetNullCursor = false;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2005-02-21^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  Demo_base dem = new Demo_base();
  Timer rewardInfoDialogTimer;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2007-11-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  String demoinfo = "";
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  public mbutton extrabuttons[];
  public JCheckBox extracheckboxes[];
  public JPanel fillerpanel;
  boolean forcekeypad;
  static String forcekeypadname = u.gettext("keypad_","forcekeypad");
//startPR2008-06-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  sharkGame thisgame;
//endPRimagesandvids^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2008-08-05^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  public removeoption unwantedOptions[] = null;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  public sharedplay sp;
  public boolean delayedflip;
  public boolean forceSharedColor = false;
//startPR2012-06-15^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  public Image speakerIm;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  public short wantcoin;
  public boolean mouseDownDelay = true;
  Timer tnTimer;
  int imcounter = 1;
 
   /**
    * Organises the appearance and functionality of the frame in which the game will
    * be positioned
    */
   public sharkGame(){
//startPR2004-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    super(sharkStartFrame.mainFrame);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    String s;
    isreward = runningGame.isrewardgame;
    int i, redb=0,greenb=0,blueb=0;
    losetime=0;   // rb 15/1/07
    if(ChangeScreenSize_base.isActive){
        setBounds( new Rectangle((int)sharkStartFrame.mainFrame.getX(), (int)sharkStartFrame.mainFrame.getY(), 
                (int)sharkStartFrame.originalbounds.getWidth(), (int)sharkStartFrame.originalbounds.getHeight()));
    }
    else
        setBounds(sharkStartFrame.originalbounds);
    this.setResizable(false);
    setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    this.addWindowListener(new java.awt.event.WindowAdapter() {
      public void windowClosing(WindowEvent e) {
         boolean intopictest = TopicTest.stage>=0;
         if(intopictest){
             if(!exitTopicTest())
                 return;
         }
         windowclose(intopictest);
      }
    });
    this.addComponentListener(new ComponentAdapter() {
      public void componentMoved(ComponentEvent e) {
        removeComponentListener(this);
        setBounds(sharkStartFrame.mainFrame.getBounds());
        validate();
        addComponentListener(this);
      }
    });
    this.getContentPane().setLayout(layout1);
    rgame = runningGame.currGameRunner;
//    String stitle = rgame.getParm("icontitle");
    String stitle = rgame.gamename;
    if(stitle == null)     // must be reward_base
        this.setTitle(rgame.gamename + "                         " + u.gettext("game_","reward"));
    else {
        this.setTitle(stitle.replace('|', ' ') + "                " + (isreward?press:pressf1));
    }
    if(!isreward && keypad.keypadname == null && sharkStartFrame.currPlayTopic.forcekeypad) {
      forcekeypad=true;
      keypad.keypadname = forcekeypadname;
    }
    gamePanel = new runMovers();
    gamePanel.usepool=true;
    gamePanel.currgame = this;
    topPanel.setLayout(topLayout);
    topPanel.setBorder(BorderFactory.createEtchedBorder());
    topPanel.setOpaque(true);
//startPR2007-12-06^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    topPanel.setBackground(sharkStartFrame.mainFrame.getBackground());
    topPanel.setBackground(sharkStartFrame.defaultbg);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    grid.gridx = 0;
    grid.gridy = -1;
    grid.gridheight = 1;
    grid.weighty = 0;
    grid.weightx = 1;
    grid.fill = GridBagConstraints.BOTH;
    topPanel.setPreferredSize(new Dimension(sharkStartFrame.screenSize.width,sharkStartFrame.screenSize.height*1/12));
    if(!shark.doImageScreenshots)
        this.getContentPane().add(topPanel, grid);
    makeBackground();
    screenwidth = sharkStartFrame.mainFrame.getContentPane().getWidth();
    screenheight = sharkStartFrame.mainFrame.getRootPane().getHeight()-sharkStartFrame.screenSize.height/12;
    screenmax = Math.max(screenwidth,screenheight);
//startPR2012-06-15^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    int ii = screenheight/22;
    speakerIm = (sharkStartFrame.t.createImage(sharkStartFrame.publicPathplus + "sprites" +
                                shark.sep + "Speak.jpg")).getScaledInstance(ii, ii, Image.SCALE_SMOOTH);
    MediaTracker tracker=new MediaTracker(gamePanel);
    tracker.addImage(speakerIm,1);
    try
    {
        tracker.waitForAll();
    }
    catch (InterruptedException ie){}
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    if(otherplayer != null) {                           //  ----- start  rb 3/3/08 v5
        gamescore2 = true;
        if(!isreward)
        {
            otherstudentrecord = new topicPlayed();

            // shared play experiment for Ruth not needed
//           if(runningGame.currGameRunner.getParm("wholegamesharedplay")!=null && !sharkStartFrame.nowholegame){
//                if(lastEducationalGame!=null && ((Integer)lastEducationalGame.get(3)).intValue()==0){
//                    flipstudent();
//                }
//                switchevery = 0;
//            }
//            else {
//                switchevery = 1;
//            lastEducationalGame.set(3, new Integer(-1));
//            }
        }
            // otherwise switch will be after every game
    }
//    else lastEducationalGame.set(3, new Integer(-1));
    //  ----- end  rb 3/3/08 v5
    if(sharkStartFrame.wanthelp && !runningGame.isrewardgame) {
      addhelppanel();
      screenwidth = screenwidth*3/4;
      screenmax = Math.max(screenwidth,screenheight);
      resizeFont();
    }
    else {
       gamePanel.setPreferredSize(new Dimension(sharkStartFrame.screenSize.width,sharkStartFrame.screenSize.height*11/12));
       grid.weighty = 12;
       grid.gridheight = 12;
       grid.gridx = 0;
       grid.gridy = -1;
       this.getContentPane().add(gamePanel, grid);
    }
    speed = student.getSpeed(rgame.gamename);
//startPR2004-10-23^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    gameLocation = this.getLocation();
    macGameWidth = this.getWidth();
    macGameHeight = this.getHeight();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2007-11-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    if(Demo_base.isDemo){
      rewardInfoDialogTimer = (new Timer(1000, new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          rewardInfoDialogTimer.setRepeats(false);
          rewardInfoDialogTimer.stop();
          rewardInfoDialogTimer = null;
          String phrase = "";
          boolean wantarrow = false;
          int pos = 0;
          if(demoinfo.equals("reward")){
            pos = Demo_base.DIALOGCENTER;
            phrase = "rewardinfo";
            if(Demo_base.shownRewardInfo)return;
            else Demo_base.shownRewardInfo = true;
          }
          else if (demoinfo.equals("help")){
            pos = screenwidth;
            phrase = "helpreminder";
            wantarrow = true;
            if(Demo_base.shownHelpInfo)return;
            else Demo_base.shownHelpInfo = true;
          }
          dem.makeDialog(Demo_base.demogettext("democdprogramtitle",
              "titleshort", false),
                        Demo_base.demogettext("popupdialogs", phrase, true),
                         phrase,
                         gamePanel.currgame, pos, true, wantarrow);
          phrase = "";
          pos = 0;
        }
      }));
      if (Demo_base.isDemo && runningGame.isrewardgame && Demo_base.demoCount == 1) {
        demoinfo = "reward";
        rewardInfoDialogTimer.setRepeats(true);
        rewardInfoDialogTimer.start();
      }
      if (!runningGame.isrewardgame && Demo_base.demoCount == 1) {
         demoinfo = "help";
         rewardInfoDialogTimer.setRepeats(true);
        rewardInfoDialogTimer.start();
      }
    }
    tooltip_base.tooltipsActive = false;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2008-06-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    if(shark.macOS){
      requestFocusInWindow();
      toFront();
    }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  }
//startPR2008-06-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    public void dispose(){
      super.dispose();
      if(shark.macOS){
        sharkStartFrame.mainFrame.requestFocusInWindow();
        sharkStartFrame.mainFrame.toFront();
      }
    }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR


  public boolean exitTopicTest(){
     boolean exittest = true;
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
                 TopicTest.listenMessage = new ListenDialog(u.gettext("topictest", "messageboxtitle"), u.gettext("topictest", "mess_exit"), sharkStartFrame.publicTestSayLib, "mess_exit",
                         thisgame, 0, new JButton[]{jb1, jb2});

                 TopicTest.listenMessage.setVisible(true);
                 if(TopicTest.listenMessage.returnValue == ListenDialog.CANCEL){
                     TopicTest.listenMessage = null;
                     exittest = false;
                 }
                 else if(TopicTest.listenMessage.returnValue == ListenDialog.YES){
                     TopicTest.escapeout();
                     if(gamePanel.currgame!=null)
                         gamePanel.currgame.terminate(true);
                 }
                 TopicTest.listenMessage = null;
             }
     return exittest;
  }  

  /**
   *
   */
  void addhelppanel()  {
       panel2 = new JPanel();
       panel2.setDoubleBuffered(false);
       panel2.setLayout(gb2);
       panel2.setPreferredSize(new Dimension(sharkStartFrame.screenSize.width,
                                             sharkStartFrame.screenSize.height*11/12));
       gamePanel.setPreferredSize(new Dimension(sharkStartFrame.screenSize.width*3/4,
                                             sharkStartFrame.screenSize.height*11/12));
       grid.weightx = 3;
       grid.weighty = 1;
       grid.gridwidth = 3;
       grid.gridx = -1;
       grid.gridy = 0;
       panel2.add(gamePanel,grid);
       grid.weightx = 0;
       grid.gridwidth = 1;
       panel2.add(helpPanel1 = new JPanel(),grid);
       helpPanel1.setLayout(new GridBagLayout());
       grid.weightx = 1;
       grid.weighty = 12;
       grid.gridheight = 12;
       grid.gridx = 0;
       grid.gridy = -1;
       helpPanel = new helppanel();
       helpPanel.usepool = true;
       helpPanel1.add(helpPanel,grid);
       grid.weighty = 0;
       grid.gridheight = 1;
       grid.fill = GridBagConstraints.HORIZONTAL;
//startPR2004-08-27^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       JLabel mess;
       // if running on a Macintosh
       if (shark.macOS) {
         mess = u.label("gamehelp_mac");
       }
       // if running on Windows
       else {
         mess = u.label("gamehelp");
       }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2005-02-21^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       if(Demo_base.isDemo){
         mess.setText(Demo_base.demogettext("democd", "gamehelp", true));
       }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       mess.setFont(runMovers.bigf[2]);
       mess.setForeground(runMovers.tooltipfg);
       mess.setBackground(runMovers.tooltipbg);
       mess.setOpaque(true);
       mess.setHorizontalAlignment(mess.CENTER);
       helpPanel1.add(mess,grid);
       grid.fill = GridBagConstraints.BOTH;
       grid.weighty = 12;
       grid.gridheight = 12;
       helpPanel1.setPreferredSize(new Dimension(sharkStartFrame.screenSize.width/4,
                                                 sharkStartFrame.screenSize.height*11/12));
       helpPanel.setMinimumSize(new Dimension(sharkStartFrame.screenSize.width/4,
                                              sharkStartFrame.screenSize.height*11/12));
       if(keypad.find(this) != null && keypad.find(this).isVisible()) {
         JPanel spare = new JPanel();
         spare.setPreferredSize(new Dimension(sharkStartFrame.screenSize.width/4,
                                           keypad.keypadheight(this)+6));
         spare.setMinimumSize(new Dimension(sharkStartFrame.screenSize.width/4,
                                        keypad.keypadheight(this)+6));
         helpPanel.setMinimumSize(new Dimension(sharkStartFrame.screenSize.width/4,
                                            sharkStartFrame.screenSize.height*11/12-keypad.keypadheight(this)));
         grid.weighty =0;
         helpPanel1.add(spare,grid);
         grid.weighty = 12;
       }
       adjusthelp();
       this.getContentPane().add(panel2,grid);
       gamePanel.refreshat=gtime+500;
  }
  /**
   * If help is wanted displays it otherwise removes it
   */
  public void changehelp() {
    if(isreward) return;
    // so that the sentencemarker box doesn't misalign when Help is turned on / off
    if(gamePanel.sentencemarker  != null){
        gamePanel.removeMover(gamePanel.sentencemarker);
    }
    if(sharkStartFrame.wanthelp) {
       this.getContentPane().remove(gamePanel);
       addhelppanel();
       validate();
       screenwidth = gamePanel.getSize().width;
       screenheight = gamePanel.getSize().height;
       screenmax = Math.max(screenwidth,screenheight);
       resizeFont();
//startPR2007-11-26^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       helpbutton.setForeground(Color.red);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       if(currhelp != null)
         help(currhelp);
//startPR2004-08-31^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//        u.pause(400);
//        helpPanel1.repaint();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    }
    else {
       helpPanel.stop();
       helpPanel=null;
       this.getContentPane().remove(panel2);
       panel2.removeAll();
       grid.weighty = 12;
       grid.gridheight = 12;
       grid.gridx = 0;
       grid.gridy = -1;
       this.getContentPane().add(gamePanel, grid);
       topPanel.setPreferredSize(new Dimension(sharkStartFrame.screenSize.width,
                                               sharkStartFrame.screenSize.height*1/12));
       gamePanel.setPreferredSize(new Dimension(sharkStartFrame.screenSize.width,
                                                sharkStartFrame.screenSize.height*11/12));
//startPR2007-10-19^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       validate();
       screenwidth = gamePanel.getSize().width;
       screenheight = gamePanel.getSize().height;
       screenmax = Math.max(screenwidth,screenheight);
       resizeFont();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2007-11-26^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       helpbutton.setForeground(Color.black);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    }
    gamePanel.refreshat = gtime+500;
    repaint();
  }
  public void adjusthelp() {  //  adjust for keypab on/off
    if(keypad.find(this) != null && keypad.find(this).isVisible()) {
     if(helpPanel1.getComponentCount() == 2) {
      JPanel spare = new JPanel();
      spare.setPreferredSize(new Dimension(sharkStartFrame.screenSize.width/4,
                                        keypad.keypadheight(this)+6));
      spare.setMinimumSize(new Dimension(sharkStartFrame.screenSize.width/4,
                                     keypad.keypadheight(this)+6));
      helpPanel.setMinimumSize(new Dimension(sharkStartFrame.screenSize.width/4,
                                         sharkStartFrame.screenSize.height*11/12-keypad.keypadheight(this)));
      grid.weighty =0;
      helpPanel1.add(spare,grid);
      grid.weighty = 12;
      validate();
     }
    }
    else {
      if(helpPanel1.getComponentCount() == 3) {
        helpPanel1.remove(2);
        helpPanel.setMinimumSize(new Dimension(sharkStartFrame.screenSize.width/4,
                                           sharkStartFrame.screenSize.height*11/12));
        validate();
      }

    }
  }
  /**
   *  Used to close game windows.
   * <li>Stops peeps, words being spoken, timer, game panel and help panel,
   * <li>Records the game details in the student record.
   */
    public void windowclose() {
        windowclose(false);
    }
  
  public void windowclose(boolean blockRewards) {
    int i;
    tooltip_base.tooltipsActive = true;
    if(forcekeypad) keypad.keypadname = null;
    if(currpeep != null)                                                 //If 1
      currpeep.dispose();                            //-If a peep is currently being displayed
     spokenWord.flushspeaker(true);
    if(gamePanel != null) {                                         //If 2-there is a game panel
         if(timerthread != null) {               //If 2.1-The timer for a game is running
           timerthread.stopit=true;timerthread = null;
         }
//startPR2008-06-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         if(currshowpicture!=null&&currshowpicture.video!=null){
           currshowpicture.video.stopall();
         }
//endPRimagesandvids^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         studentRecord();
                                    //  rb 22/2/08  v5 start-----------------------------------------------------------
         if(completed && !isreward && sharkStartFrame.mainFrame.usingsuperlist == null
            && !sharkStartFrame.mainFrame.wantplayprogram && errortot < 2
            && !sharkStartFrame.studentList[sharkStartFrame.currStudent].administrator
            && !sharkStartFrame.studentList[sharkStartFrame.currStudent].teacher) {
               ++sharkStartFrame.studentList[sharkStartFrame.currStudent].sametopic;
               if(sharkStartFrame.studentList[sharkStartFrame.currStudent].sametopic > MAXSAMETOPIC) {
                   sharkStartFrame.mainFrame.tonext();
                   sharkStartFrame.studentList[sharkStartFrame.currStudent].sametopic = 0;
               }
         }
                                      //  rb 22/2/08  v5 end-----------------------------------------------------------
   //      if(completed && !isreward && !nobest && errortot==0) student.setBestTime(rgame.shortgamename,rgame.topicName,(short)timetaken);
         if(completed && !isreward && !nobest && errortot==0) student.setBestTime(rgame.gamename,rgame.topicName,(short)timetaken);
         if(sharkStartFrame.mainFrame.superswap != null) {
           sharkStartFrame.mainFrame.currPlayTopic = sharkStartFrame.mainFrame.superswap;
           sharkStartFrame.mainFrame.superswap = null;
           sharkStartFrame.mainFrame.wordTree.redrawing = true;
           sharkStartFrame.mainFrame.wordTree.setup(sharkStartFrame.mainFrame.currPlayTopic,null);
           sharkStartFrame.mainFrame.wordTree.redrawing = false;
         }
         if(!isreward){ 
                 if(sharkStartFrame.mainFrame.usingsuperlist != null) {
                    sharkStartFrame.mainFrame.usingsuperlist.superendgame(rgame.gamename,
                    u.stripdup(u.addString(studentrecord.errorList, studentrecord.peepList)),
                    studentrecord.incomplete || nogoodforsuper,errortot);
                 }
                 else if(sharkStartFrame.currPlayTopic.revision && sharkStartFrame.mainFrame.wordTree.shuffled){
                     sharkStartFrame.mainFrame.refreshExtendedSample();
                 }
         }
         
         topPanel=null;
         gamePanel.stop();
         gamePanel=null;
         if(helpPanel != null) {                                           //If 2.2
            helpPanel.stop();                                       //There is a help panel
            helpPanel=null;
         }
         rgame.currGameRunner = null;
         rgame.game=null;
         rgame=null;
         this.dispose();
         sharkStartFrame.gametot = (short)Math.max(0,sharkStartFrame.gametot-1);
         losetime=0;     //rb 15/01/07
     }
//     int sgcount = -1;
//     if(lastEducationalGame!=null && !isreward && switchevery==0){
//        sgcount = ((Integer)lastEducationalGame.get(3)).intValue();
//        lastEducationalGame.set(3, new Integer(sgcount+1));
//     }
    boolean intopictest = TopicTest.stage>=0;
    if(intopictest){
        if(!studentrecord.incomplete){
            TopicTest tt = new TopicTest();
            if(!TopicTest.reachedEnd){
                sharkStartFrame.currPlayTopic = topic.findtopic(tt.getTopic());
                jnode node = sharkStartFrame.mainFrame.topicList.findNode(sharkStartFrame.mainFrame.currPlayTopic.name);
                sharkStartFrame.mainFrame.topicList.setSelectionPath(new javax.swing.tree.TreePath(node.getPath()));
                sharkStartFrame.mainFrame.setupGametree();
                sharkStartFrame.mainFrame.startgame(sharkStartFrame.mainFrame.gamename[u.findString(sharkStartFrame.mainFrame.gamename, tt.gameName)]);
            }
            else {
                intopictest = false;
                tt.doResult();
                blockRewards = true;
            }
        }
    }
    if(!intopictest){
       String reward_list[] = sharkStartFrame.okrewards;
       boolean flipping = false;
       if(gamescore2)
       {
           boolean forcing = forcerewardflipped>=0;
           flipping = forcing?(forcerewardflipped==0?false:true):rewardstudentflipped;
           reward_list = sharkStartFrame.okrewards_flip;
//                 setflip(flipping);
//                 if(!forcing)
//                     rewardstudentflipped = !rewardstudentflipped;
//                 forcerewardflipped = -1;
       }
     boolean allowrewards = !blockRewards && db.query(sharkStartFrame.optionsdb, "notwantrewards", db.TEXT) < 0
             && !student.option("s_excludeallrewardgames");
//startPR2007-11-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     if(Demo_base.isDemo && Demo_base.demoCount < 3 && sharkStartFrame.reward > 0
             && allowrewards){
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       new reward_base();
     }
     else if(allowrewards && !Demo_base.isDemo && sharkStartFrame.reward > 0 && reward_list.length>0   // rb 11-1-08
             && ++sharkStartFrame.studentList[sharkStartFrame.currStudent].gamesforreward
               > sharkStartFrame.studentList[sharkStartFrame.currStudent].rewardfreq) {
              new reward_base(flipping);
              sharkStartFrame.studentList[sharkStartFrame.currStudent].gamesforreward =0;
     }
     else {                                  //Else 3-There is no reward_base score for the student
       sharkStartFrame.reward = 0;   // in case not this time
//startPR2007-11-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         if(Demo_base.isDemo && runningGame.isrewardgame && Demo_base.demoCount == 2){
           Demo_base.showWordListInfo = true;
         }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//        if(oldstudent != null)  { //If 3.1-The present student was playing with another student
//             oldstudent.finishSignon(false); //reverse sign-on
//             oldstudent = null;
//             sharkStartFrame.mainFrame.setVisible(true);
//        }
        if(sharkStartFrame.mainFrame.usingsuperlist != null
//           &&  !sharkStartFrame.mainFrame.simplesuper
//startPR2008-11-03^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//           &&  !sharkStartFrame.mainFrame.usingsuperlist.autostarted
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
               &&  sharkStartFrame.mainFrame.usingsuperlist.superd.last3games.indexOf(topic.SPELLING)<0) {
//startPR2008-11-03^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//           int o[] = u.shuffle(u.select(sharkStartFrame.mainFrame.gamename.length, sharkStartFrame.mainFrame.gamename.length));
//           for(i=0;i<o.length;++i) {
//             if(sharkStartFrame.gameflags[o[i]].autospell) {
//                sharkStartFrame.mainFrame.startgame(sharkStartFrame.mainFrame.gamename[o[i]]);
//                sharkStartFrame.mainFrame.usingsuperlist.autostarted = true;
   //           if(!sharkStartFrame.spellingonly) {
//                sharkStartFrame.mainFrame.sametopic = true;
                sharkStartFrame.mainFrame.setupGametree(true);
   //           }
//                break;
//             }
//           }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        }
        else {
          sharkStartFrame.mainFrame.refreshprog();
//startPR2008-11-03^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//          if(sharkStartFrame.mainFrame.usingsuperlist != null)
//                 sharkStartFrame.mainFrame.usingsuperlist.autostarted = false;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        }
//         if(
//         lastEducationalGame!=null &&
//         (sgcount = ((Integer)lastEducationalGame.get(3)).intValue())==0){
//            new runningGame((String)lastEducationalGame.get(0),
//                    (wordlist)lastEducationalGame.get(1),
//                    (String)lastEducationalGame.get(2), false);
//         }
//         else if(sgcount>0){
//             studentflipped = false;
//             setflip(studentflipped);
//             lastEducationalGame.set(3, new Integer(-1));
//         }


        if(sharkStartFrame.mainFrame.gamePanel !=null) {
          if (sharkStartFrame.mainFrame.gamePanel.tooltipmover1 != null) {
            sharkStartFrame.mainFrame.gamePanel.removeMover(sharkStartFrame.mainFrame.gamePanel.tooltipmover1);
            sharkStartFrame.mainFrame.gamePanel.tooltipmover1 = null;
          }
          sharkStartFrame.mainFrame.gamePanel.stoprun=false;
          sharkStartFrame.mainFrame.gamePanel.copyall=true;
           sharkStartFrame.mainFrame.gamePanel.setVisible(true);
          sharkStartFrame.mainFrame.gamePanel.startrunning();
          sharkStartFrame.mainFrame.gamePanel.addlisteners();
        }
        sharkStartFrame.mainFrame.requestFocus();
     }
   }
//startPR2006-08-19^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       // in the demo version resets the number of 'turns' before exiting each game.
       Demo_base.demoTurnsCount = 0;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  }
  /**
   *
   */
  public void terminate(boolean blockRewards) {
    windowclose(blockRewards);
  }  
  
  public void terminate() {
    windowclose();
  }
  /**
   * <li>If there are two players then show reward_base for student with greater score.
   * <li>Store game settings in the student's record.
   * <li>The reward_base for the game is assigned.
   * <li>If necessary the games are set up.
   */
  public void studentRecord() {
//    if(completed && !solo && !sharkStartFrame.mainFrame.mnoreward.getState()//If 1             // v5 start rb 9/3/08
//             && !cancelreward) { //-The game is finished AND rewards are wanted AND reward_base is not cancelled
//       if(gametot1 >= gametot2){                                           //If 1.1
//         sharkStartFrame.reward_base = (short) gametot1; //If the game total for the signed on student
//       }                                            //is greater than that of the additional student
//       else {                                                            //Else 1.1
//          oldstudent = sharkStartFrame.studentList[sharkStartFrame.currStudent]; //If the game total for the signed on student
//          getStudent2().finishSignon(false); // temp sign //is less than that of the additional student
//          sharkStartFrame.reward_base = (short)gametot2;
//       }
//     }                                                                                   // v5 end rb 9/3/08

     if(gamescore2 && !isreward && completed)
        gamestartflipped = !gamestartflipped;
         //         studentflipped = !studentflipped;    // v5 rb 7/3/08
     if(isreward)                            // v5  rb 9/3/08
       return;
     student stu = sharkStartFrame.studentList[sharkStartFrame.currStudent];
     stu.lastplayed = System.currentTimeMillis();
     studentrecord.date =   stu.signonDate;
     studentrecord.timezone = java.util.TimeZone.getDefault();
   //  studentrecord.game = rgame.shortgamename;
     studentrecord.game = rgame.gamename;
     studentrecord.topic = spellchange.spellchange(rgame.topicName);
     timetaken = studentrecord.time = (short)(((endplaytime!=0?endplaytime:System.currentTimeMillis()) - startplaytime)/1000);
     studentrecord.speed = wantspeed?speed:-1;
     studentrecord.teacher = sharkStartFrame.mainFrame.override();
     studentrecord.shuffled = ((rgame.options &  word.SHUFFLED) != 0);
     if(competitivescore && gamescore2)
        studentrecord.score = (short)gametot1;
     else
        studentrecord.score = (short)(gamescore1?gametot1:thistimescore);
     studentrecord.workforteacher = (stu.workforteacher == null || stu.workforteacher.trim().equals(""))?null:stu.workforteacher;    // rb  23/10/06
     if(gamescore2) {
         studentrecord.sharedwith = otherplayer.name;
     }
//startPR2009-04-08^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//     if(completed && !sharkStartFrame.mainFrame.mnoreward.getState()  //If 3-game is finished
//          && !cancelreward) {   //AND Reward games are wanted AND Reward games have not been cancelled
     if(completed) {   //AND Reward games are wanted AND Reward games have not been cancelled
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//        if(solo)                                                         //If 3.1             // v5 start rb 9/3/08
//startPR2009-04-08^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       if(!sharkStartFrame.studentList[sharkStartFrame.currStudent].option("noreward") && !cancelreward) {
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         sharkStartFrame.reward = thistimescore;                  //-There is a single player
         if(errortot != 0)                                                //If 3.2
           sharkStartFrame.reward /= 2;                    //An error has occurred in the game
         if(peeptot != 0)                                                 //If 3.3
           sharkStartFrame.reward /= 2;         //The student has peeped at a word in the game
         sharkStartFrame.reward = (short)Math.min(18,Math.max(3,sharkStartFrame.reward));
//         if(gamescore2) {                                               // v5 rb 7/3/08
//            if(rewardcurr) lastreward = false;
//            else if(rewardother) lastreward = true;
//            else if(switchevery == -1) lastreward = studentflipped;          // v5 rb 7/3/08
//            else lastreward = !lastreward;                             // v5 rb 7/3/08
//         }                                                              // v5 rb 7/3/08
//startPR2009-04-08^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         if(sharkStartFrame.wantplayprogram   && !gamescore2       //v5 rb 9/3/08
            && program.endgame(sharkStartFrame.playprogram,errortot)//AND Game is ended
            || !sharkStartFrame.wantplayprogram //OR The student has not had a personalised set of word list set up for them
            && !sharkStartFrame.studentList[sharkStartFrame.currStudent].administrator//AND the current student is not an administrator
            && db.anyof(sharkStartFrame.studentList//AND the current student has got programs in their database
                        [sharkStartFrame.currStudent].name,db.PROGRAM)) {
            sharkStartFrame.mainFrame.setupgames(true);
         }
         else while(db.anyof(sharkStartFrame.studentList[sharkStartFrame.currStudent].name,
                          db.MESSAGE)) new messages("get");
     }
     else {//Else 3-game is not finished OR Reward games are not wanted OR Reward games have been cancelled
        if(!completed)                                      //If 3.1-Game is not finished
          studentrecord.incomplete = true;
     }
//     if(!stu.isnew && (!gamescore2 || !studentflipped)) {     // v5 rb 9/3/08
     if(!stu.isnew) {     // v5 rb 9/3/08
//       if (stu.studentrecord.length == stu.MAXSTUDENTRECORD) {   --start---- removed rb 21/2/08 v5 ------------------
//           System.arraycopy(stu.studentrecord, 1, stu.studentrecord, 0, stu.MAXSTUDENTRECORD-1);
//           stu.studentrecord[stu.MAXSTUDENTRECORD-1] = studentrecord;
//       }
//       else if (stu.studentrecord.length > stu.MAXSTUDENTRECORD) {
//           topicPlayed sr2[] = new topicPlayed[stu.MAXSTUDENTRECORD];
//           System.arraycopy(stu.studentrecord, stu.studentrecord.length - stu.MAXSTUDENTRECORD+1, sr2, 0,
//                            stu.MAXSTUDENTRECORD-1);
//           sr2[stu.MAXSTUDENTRECORD-1] = studentrecord;
//           stu.studentrecord = sr2;
//       }
//       else {                                              ---end --- removed rb 21/2/08 v5 ------------------



         topicPlayed tp[] = new topicPlayed[stu.studentrecord.length+1];


         System.arraycopy(stu.studentrecord, 0, tp, 0, stu.studentrecord.length);
         tp[stu.studentrecord.length] = studentrecord;
         if(gamescore2) tp[stu.studentrecord.length].sharedwith = otherplayer.name;
         stu.studentrecord = tp;
//       }                                                  ------ removed rb 21/2/08 v5 ------------------
       if (stu.updatestudent()) {
         sharkStartFrame.mainFrame.setStudentMenu();
         sharkStartFrame.mainFrame.setupgames();
       }
     }
//     if(gamescore2 && !otherplayer.isnew && (studentflipped)) {     // v5 rb 9/3/08
     if(gamescore2 && !otherplayer.isnew) {     // v5 rb 9/3/08
       otherstudentrecord.topic = studentrecord.topic;
       otherstudentrecord.game = studentrecord.game;
       otherstudentrecord.date = studentrecord.date;
       otherstudentrecord.dateoff = studentrecord.dateoff;
       otherstudentrecord.time = studentrecord.time;
       otherstudentrecord.speed = studentrecord.speed;
       otherstudentrecord.teacher = studentrecord.teacher;
       otherstudentrecord.incomplete = studentrecord.incomplete;
       otherstudentrecord.shuffled = studentrecord.shuffled;
       if(competitivescore)
            otherstudentrecord.score = (short)gametot2;
       else
            otherstudentrecord.score = (short)(gamescore1?gametot1:thistimescore);
       otherstudentrecord.testresults = studentrecord.testresults;
       otherstudentrecord.timezone = studentrecord.timezone;
       otherstudentrecord.sharedwith = sharkStartFrame.studentList[sharkStartFrame.currStudent].name;

       topicPlayed tp[] = new topicPlayed[otherplayer.studentrecord.length + 1];
       System.arraycopy(otherplayer.studentrecord, 0, tp, 0, otherplayer.studentrecord.length);
       tp[otherplayer.studentrecord.length] = otherstudentrecord;
       otherplayer.studentrecord = tp;
       otherplayer.updatestudent();
     }
  }
  /**
   * Builds the top panel of the game screens - tailoring it the particular requirements
   * of the game in question.
   */
  public void buildTopPanel(){
    int i;
    if(shark.phonicshark)
        peep = peeps = false;
    boolean rebuild = (topPanel.getComponentCount() > 0);
    if(rebuild) {                                                          //If 1
       timerthread.stopit=true;                   //There are components for the top panel
       topPanel.removeAll();
    }
    grid1.gridy = 0;
    grid1.gridx = -1;
    grid1.weightx = 1;
    grid1.weighty = 1;
    grid1.fill = GridBagConstraints.BOTH;
    grid2.gridx = 0;
    grid2.gridy=-1;
    grid2.weightx = grid2.weighty = 1;
    grid2.fill = GridBagConstraints.BOTH;
    if(bgavoid !=null)                                                    //If 2
      makeBackground();                                      //There are bg colors to avoid

      if(optionlist != null) {                                               //If 3
        optionButton = new topbutton(optionsl);         //Options have been set for the game
//        optionButton.setToolTipText(optionst);
        optionButton.setRequestFocusEnabled(false);
//startPR2005-10-16^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        optionButton.setBackground(sharkStartFrame.defaultbg);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        topPanel.add(optionButton,grid1);
        optionButton.addActionListener(new ActionListener(){         //Listener 3.1
            public void actionPerformed(ActionEvent e) {                //Event 3.1.1
               if(!options.active) {                                       //If 3.1.1.1
//startPR2007-02-27^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//                 options o = new options(                     //options are not being used
//                       "Options for '" + rgame.gamename + "'", optionlist, gamePanel);
//                     options o = new options(                     //options are not being used
//                       u.gettext("options", "optionsfor")+" '"+rgame.gamename + "'", optionlist, gamePanel);
//                  options o = new options(u.gettext("options", "optionsfor")+" '"+rgame.gamename+"'", optionlist, gamePanel, nooptionsmess?null:rgame.getParm("optionsmess"));
                    options o = new options(u.gettext("options", "optionsfor")+" '"+rgame.gamename+"'", optionlist, groupedoptions, gamePanel, nooptionsmess?null:rgame.getParm("optionsmess"));
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
               }
            }
        });
        optionButton.setFont(sharkStartFrame.treefont);
    }



      if (computerscore) {
//        gamescore2View = addCounter(u.gettext("game_", "computer"), scoret, "0");
        sp = new sharedplay(sharkStartFrame.studentList[sharkStartFrame.currStudent].name,
            u.gettext("game_", "computer"), true);
        topPanel.add(sp, grid1);
      }
//       else if((isreward || gamescore1) && gamescore2){
       else if(gamescore2){
        sp = new sharedplay(sharkStartFrame.studentList[sharkStartFrame.currStudent].name,
            otherplayer.name, competitivescore);
        if(isreward){
         boolean forcing = forcerewardflipped>=0;
         setflip(forcing?(forcerewardflipped==0?false:true):rewardstudentflipped);
         if(!forcing)
             rewardstudentflipped = !rewardstudentflipped;
         forcerewardflipped = -1;
        }
        topPanel.add(sp, grid1);
      }

    if(extrabuttons != null) {
      for(i=0; i<extrabuttons.length;++i) {
        extrabuttons[i].setFocusable(false);
        topPanel.add(extrabuttons[i],grid1);
      }
    }
    if(extracheckboxes != null) {
      for(i=0; i<extracheckboxes.length;++i) {
        extracheckboxes[i].setFocusable(false);
        grid1.fill = GridBagConstraints.NONE;
        topPanel.add(extracheckboxes[i],grid1);
        grid1.fill = GridBagConstraints.BOTH;
      }
    }
    if(fillerpanel != null) {
        fillerpanel.setFocusable(false);
        grid1.fill = GridBagConstraints.BOTH;
        topPanel.add(fillerpanel,grid1);
        
    }
    if(wantsPrint) {                                                       //If 4
//startPR2007-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//        printButton = new topbutton("print");
      printButton = new topbutton(printl);  //Print button is needed
//      printButton.setToolTipText(printt);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        printButton.setRequestFocusEnabled(false);
//startPR2005-10-16^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        printButton.setBackground(sharkStartFrame.defaultbg);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        topPanel.add(printButton,grid1);
        printButton.addActionListener(new ActionListener(){          //Listener 4.1
           public void actionPerformed(ActionEvent e){                  //Event 4.1.1
//startPR2006-10-13^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
             Cursor prevCursor = null;
             if(shark.macOS){
               prevCursor = gamePanel.getCursor();
               gamePanel.setCursor(Cursor.getDefaultCursor());
             }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
             print();
//startPR2006-10-13^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
             if(shark.macOS){
               gamePanel.setCursor(prevCursor);
               gamePanel.refreshat = System.currentTimeMillis() + 2000;
             }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2004-10-06^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
             if(!u.macBlock)
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
               requestFocus();
           }
        });
        printButton.setFont(sharkStartFrame.treefont);
    }
    if(listen) {                                                           //If 5
        listenButton = new topbutton(listenl,u.eari);      //The game needs a listen button
        listentext = listenButton.getText();
        listenicon = (ImageIcon)listenButton.getIcon();
//        listenButton.setToolTipText(listent);
        listenButton.setRequestFocusEnabled(false);
//        listenButton.setOrientation(Orientation.VERTICAL);
//startPR2005-10-16^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        listenButton.setBackground(sharkStartFrame.defaultbg);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        topPanel.add(listenButton,grid1);
        listenButton.addMouseListener(new MouseAdapter(){
          public void mousePressed(MouseEvent e) {
            rightclick = e.getModifiers() == e.BUTTON3_MASK;
            if(rightclick) {
              listen1();
              requestFocus();
            }
          }
        });
        listenButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
               listen1();
               requestFocus();
            }
        });
        listenButton.setFont(sharkStartFrame.treefont);
     }

    boolean uniAllowPeeps = db.query(sharkStartFrame.optionsdb, "uninopeep", db.TEXT) < 0;
//startPR2004-10-21^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//     if(peep) {
    if(!shark.phonicshark){
         if((peep)&& uniAllowPeeps && !student.option("s_nopeep")) { //If 6
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            peepButton = new topbutton(freepeep?freepeepl:peepl,u.eyei);//Peep button needed by game
    //        peepButton.setToolTipText(freepeep?freepeept:peept);
            peepButton.setRequestFocusEnabled(false);
//startPR2005-10-16^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            peepButton.setBackground(sharkStartFrame.defaultbg);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            topPanel.add(peepButton,grid1);
            peepButton.addActionListener(new ActionListener(){           //Listener 6.1
              public void actionPerformed(ActionEvent e){                   //Event 6.1.1
                 peep1();
                 gamePanel.startrunning();
                 requestFocus();
              }
            });
            peepButton.setFont(sharkStartFrame.treefont);
         }
     }
     if(wantspeed) {                                                       //If 8
        speedView =  addSlider(u.edit(speedl,String.valueOf(speed)),speed);//Slider for speed is needed by game
//        ((JPanel)speedView.getParent()).setToolTipText(speedt);
        speedView.addChangeListener(new ChangeListener(){            //Listener 8.1
             public void stateChanged(ChangeEvent e){                   //Event 8.1.1
               student.setSpeed(rgame.gamename, speed = (byte)speedView.getValue());
               speedtext.setText(u.edit(speedl,String.valueOf(speed)));
               newspeed();
               requestFocus();
             }});
     }
     if(timer) {                                                           //If 9
       timerView = addCounter(timel,null,"00.00");      //Timer placed in game's top panel
       timerthread = new showtime();
       timerthread.start();
       if(!runningGame.isrewardgame & !nobest) {
  //       int best = student.getBestTime(rgame.shortgamename,  rgame.topicName);
         int best = student.getBestTime(rgame.gamename,  rgame.topicName);
         if(best>0) addCounter(besttimel,null, String.valueOf(best/600)+String.valueOf(best%600/60)
                +  "." + String.valueOf(best%60/10)+ String.valueOf(best%10));
       }
     }
     if(errors) {                                                          //If 10
        errorView = addCounter(errorsl,null,"0");//Game needs a count of errors to be displayed in top panel
     }
     if(peeps && uniAllowPeeps && !student.option("s_nopeep")) {   // rb 27/10/06
        peepsView = addCounter(peepsl,null,"0");//Game needs a counter to display the number of peeps taken
     }
     if(bites) {                                                          //If 12
        deathsView = addCounter(bitesl,null,"0");//Game needs a counter to display the number of deaths
     }
     else if(deaths) {                                                          //If 12
        deathsView = addCounter(deathsl,null,"0");//Game needs a counter to display the number of deaths
     }





     if(gamescore2) {                                                      //If 14
       if(gamescore1) {                          //If 13 - Score needs to kept for the game
//         JPanel h1 = new JPanel(new GridBagLayout());
//         h1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black),
//                                                    u.gettext("game_", "gamescore"),TitledBorder.CENTER,TitledBorder.TOP));
//         gamescore1View = addCounter(sharkStartFrame.studentList[sharkStartFrame.
//                                     currStudent].name, scoret, "0");
//         gamescore2View = addCounter(otherplayer.name, scoret, "0");
  //       topPanel.remove(topPanel.getComponentCount()-1);
  //       topPanel.remove(topPanel.getComponentCount()-1);
//         h1.add(gamescore1View.getParent(),grid1);
//         h1.add(gamescore2View.getParent(),grid1);
//         topPanel.add(h1,grid1);
       }  
       JPanel h2 = new JPanel(new GridBagLayout());
       h2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black),
                                                                        u.gettext("game_", "totscore"),TitledBorder.CENTER,TitledBorder.TOP));
       if(gamescore1) {                          //If 13 - Score needs to kept for the game
           gamescore1View = addCounter(scorel, null, "0");
       }
       tot1View = addCounter(sharkStartFrame.studentList[sharkStartFrame.
                                   currStudent].name, null, "0");
//       tot2View = addCounter(otherplayer.name, null, "0");
       tot1View.setText(String.valueOf(sharkStartFrame.studentList[sharkStartFrame.
                                     currStudent].totscore));
 //      tot2View.setText(String.valueOf(otherplayer.totscore));
  //     topPanel.remove(topPanel.getComponentCount()-1);
  //     topPanel.remove(topPanel.getComponentCount()-1);
       h2.add(tot1View.getParent(),grid1);
 //      h2.add(tot2View.getParent(),grid1);
//       if(!gamescore2)
//        topPanel.add(h2,grid1);
     }
     else {
       if(gamescore1 && !computerscore) {                          //If 13 - Score needs to kept for the game
           gamescore1View = addCounter(scorel, null, "0");
       }
//       if (computerscore) {
//         gamescore2View = addCounter(u.gettext("game_", "computer"), scoret, "0");
//       }
       if (pupilscore) { //If 16
         studentpanel.setLayout(new GridBagLayout()); //Pupils total score is displayed
         studentpanel.setBorder(BorderFactory.createEtchedBorder());
//         studentpanel.setToolTipText(studentt);
         addStudent();
       }
     }
//startPR2005-04-18^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      if(!runningGame.isrewardgame && !Demo_base.isDemo) {
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        if((shark.network && (sharkStartFrame.studentList[sharkStartFrame.currStudent].teacher
                       || sharkStartFrame.studentList[sharkStartFrame.currStudent].administrator))
                       || wantpause) {
                      pauseButton = u.Button("g_pause"); // rb 15/1/07
                      pauseButton.setRequestFocusEnabled(false);
                      pauseButton.setBackground(sharkStartFrame.defaultbg);
                      pausecolor = pauseButton.getBackground();
                      pauseButton.setFont(sharkStartFrame.treefont);
                      pauseButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                          pause1();
                          requestFocus();
                        }
                      });
                      grid1.weightx = 0;
                     topPanel.add(pauseButton, grid1);
      }
       String spaces = "   ";
       helpbutton = new mbutton(spaces+u.gettext("g_help", "label")+spaces);
//startPR2005-05-19^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       helpbutton.setFocusable(false);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       helpbutton.setForeground(sharkStartFrame.wanthelp?Color.red:Color.black);
//startPR2007-10-30^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//       helpbutton.setBackground(sharkStartFrame.defaultbg);
       if(!shark.macOS)
         helpbutton.setBackground(runMovers.tooltipbg);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       helpbutton.addActionListener( new ActionListener() {
          public void actionPerformed(ActionEvent e){
            sharkStartFrame.mainFrame.setwanthelp(!sharkStartFrame.wanthelp);
            helpbutton.setForeground(sharkStartFrame.wanthelp?Color.red:Color.black);
          }
       });
       grid1.weightx = 0;
       topPanel.add(helpbutton, grid1);
       grid1.weightx  = 1;
     }
     validate();
     if(!rebuild) {                                                        //If 18
        gamePanel.start1();
        setVisible(true);
        addKeyListener(this);
     }
    setsprite();
    if(isreward) {
       if(gamescore2)
           flipstudentreward();
    }
    else if(gamescore2) {
//        studentflipped = !studentflipped;
        studentflipped = gamestartflipped;
        flipstudent(false);
    }   // v5 rb 7/3/08
    topPanel.setCursor(null);
    gamePanel.requestFocus();
    gamePanel.refreshat = System.currentTimeMillis() + 2000;
    requestFocus();
//startPR2007-09-03^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    if(shark.linuxOS)
      topPanel.repaint();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
 }
  
    public void setErrorCounterVisible(boolean visible) {
        if(errorView!=null)
            errorView.getParent().setVisible(visible);
    } 
    
  
 /**
  * If a sprite is needed then makes one visible.
  */
 void setsprite() {
   if(wantSprite) {
       gamePanel.sprite
          = new sharkImage(sharkStartFrame.studentList[sharkStartFrame.currStudent].spritename,
          false);
       gamePanel.sprite.w =  sharkStartFrame.screenSize.width/20 * mover.WIDTH/screenwidth;
       gamePanel.sprite.h =  sharkStartFrame.screenSize.width/20 * mover.HEIGHT/screenheight;
       gamePanel.sprite.adjustSize(screenwidth,screenheight);
       gamePanel.sprite.mx = - gamePanel.sprite.w/2;
       gamePanel.sprite.my = - gamePanel.sprite.h/2;
       gamePanel.sprite.manager = gamePanel;
       gamePanel.showSprite = true;
       if(!sharkStartFrame.studentList[sharkStartFrame.currStudent].spritename.equals
                                                                       ("x_aaa") ){
//startPR2005-10-16^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2006-10-13^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//         if(shark.macOS)
//           macSetNullCursor = true;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         gamePanel.setCursor(sharkStartFrame.nullcursor);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       }
       else gamePanel.winsprite = gamePanel.winspritevisible = true;
    }
  }
  /**
   * Keylistener interface.
   * @param e Key event
   */
  public void keyPressed(KeyEvent e) {
    if(gamePanel != null){
      if(pausing) return;    // rb 15/1/07
      gamePanel.keypressed(e);
    }
  }
  /**
   * Keylistener interface.
   * @param e Key event
   */
  public void keyReleased(KeyEvent e) {
      if(shark.doImageScreenshots){
      
//    int code = e.getKeyCode();        
//        String ss[] = new String[]{"short"};      


        ToolsOnlineResources tor = new ToolsOnlineResources();
        String ss[] = tor.getToDoList(DoImageScreenshots.toDoIWSJsonPath, false);
        
   //     ss = new String[]{"him", "herd"};
    //    String  ss[] = new String[]{"horse", "cat", "school", "rain"};   
    //    String  ss[] = new String[]{"undo"};
    //  String  ss[] = new String[]{"afternoon"};
          
          /*
          ToolsOnlineResources tor = new ToolsOnlineResources();
       String ssall[] = u.readFile("E:\\Shark Screen Shots\\all.txt");
        for(int i = 0; i < ssall.length; i++){
            if(ssall[i].endsWith("."))ssall[i] = ssall[i].substring(0, ssall[i].length()-1);
            ssall[i] = tor.charToAscii(ssall[i]);
            File f = new File("E:\\Shark Screen Shots\\Stage 1\\"+ssall[i]);
            if(!f.exists()){
                int g;
                g = 0 ;
            }
        }           
        
          
          
          
        String sslog[] = u.readFile("E:\\Shark Screen Shots\\log.txt");
        String sslog_db[] = new String[]{};
        String sslog_name[] = new String[]{};
        for(int i = 0; i < sslog.length; i++){
            String s1[] =u.splitString(sslog[i]);
            sslog_db = u.addString(sslog_db, s1[0]);
            sslog_name = u.addString(sslog_name, s1[1]);
    //        if(sslog_name[i].endsWith("."))sslog_name[i] = sslog_name[i].substring(0, sslog_name[i].length()-1);
        }      
        */
      Tools t = new Tools();
      String dbs[] = new String[]{"publicimage", "publicimageSENT"};
      boolean started = false;
      for(int d = 1; d< dbs.length; d++){
          
          
     //   String  ss[] = db.list(dbs[d], db.IMAGE);  
       // ss = new String[]{"animal_1"};
        


   /*    
        String ssfolders[] = u.readFile("E:\\Shark Screen Shots\\Stage 1\\tmp.txt");
        for(int i = 0; i < ssfolders.length; i++){
            ssfolders[i] = ssfolders[i].replace("[", "").replace("]", "");
        }        
        
        for(int i = 0; i < sslog_name.length; i++){  
            if(u.findString(ssfolders, sslog_name[i])<=0){
                System.out.println("0     "+ sslog_name[i]);
                int g; 
                 g = 0;
            }
        }          
        for(int i = 0; i < ssfolders.length; i++){  
            if(u.findString(sslog_name, ssfolders[i])<=0){
                System.out.println("1     "+ ssfolders[i]);
                int g; 
                 g = 0;
            }
        }          
        
  */        
        String fResultDir = DoImageScreenshots.mainFolder ;
        new File(fResultDir).mkdirs();
        imageloop:for(int i = 0; i< ss.length; i++){
    //        if(ss[i].equals("spoke")){
                started = true;
    //        }
            if(!started)continue;
            if(ss[i].trim().equals(""))continue;
            ss[i] = ss[i].trim();
     //       for(int n = 0; n< sslog_name.length; n++){
   //             if(sslog_name[n].equals(ss[i]) && sslog_db[n].equals(dbs[d])){
    //               continue imageloop;
   //             }
   //         }           
            
            currword = new word(ss[i],"publictopics");
            while(gamePanel.mtot > 0){
                u.pause(100);
            }
  //          DoImageScreenshots.forceImLibrary = 1;
            final showpicture sp = new showpicture(0, 0, mover.WIDTH, mover.HEIGHT, true);
            if(sp.im1 == null || sp.im1.c == null)continue;
            sp.im1.isanimated = sp.im1.c.length>1 && (sp.im1.im.controls[0].min>0 || sp.im1.im.controls[0].max>0); 
    //        if(sp.im1.isanimated){
    //            removeMover(sp);
    //            currshowpicture = null;
     //           continue;
      //      }
            
       //     sp.im1.isanimated = false;
            if(sp.im1.isanimated){
                int avlength = 0;
                for(int k = 0; k < sp.im1.im.controls.length;k++){
                    if(sp.im1.im.controls[k].min!=0 && sp.im1.im.controls[k].max!=0)
                        avlength += (sp.im1.im.controls[k].max + sp.im1.im.controls[k].min) / 2;
                }
                avlength = avlength/DoImageScreenshots.timeFactor;
                avlength = avlength*100;
                int targetGifLength;
                targetGifLength = avlength;
                targetGifLength = Math.min(DoImageScreenshots.maxGifLength, targetGifLength);
                avlength = avlength*DoImageScreenshots.timeFactor;
                int fileNo = targetGifLength/DoImageScreenshots.gifFrameRate;
                fileNo = Math.max(DoImageScreenshots.minFrames, fileNo);
                DoImageScreenshots.workingFramerate = avlength/fileNo;
                java.util.ArrayList ani = new java.util.ArrayList();
                sp.im1.icgroups = new java.util.ArrayList();
                sp.im1.icgroups.add(new int[]{0});
                ani.add(new boolean[]{sp.im1.im.controls[0].max!=0});
                int lastc = 0;
                int lastarrayitem = 0;
                for(short j = 1; j  < sp.im1.c.length; j++){
                    if(sp.im1.c[j].samecgroup((short)lastc)){
                        int[] sa = (int[])sp.im1.icgroups.get(lastarrayitem);
                        sa = u.addint(sa, j);
                        boolean[] ba = (boolean[])ani.get(lastarrayitem);
                        ba = u.addboolean(ba, sp.im1.im.controls[j].max!=0);
                        sp.im1.icgroups.set(lastarrayitem, sa);
                        ani.set(lastarrayitem, ba);
                    }
                    else{
                        sp.im1.icgroups.add(null);
                        sp.im1.icgroups.set(sp.im1.icgroups.size()-1, new int[]{j});
                        ani.add(null);
                        ani.set(ani.size()-1, new boolean[]{sp.im1.im.controls[j].max!=0});
                        lastc = j;
                        lastarrayitem = sp.im1.icgroups.size()-1;
                    }
                }
                // check for and remove non looping controls
                for(int ii = sp.im1.icgroups.size()-1; ii >= 0; ii--){
                    boolean[] bcg = (boolean[])ani.get(ii);
                    if(!bcg[bcg.length-1]){
                        sp.im1.icgroups.set(ii, null);
                    }
                }
            } 
         
            while(!sp.im1.initdrawn){
                try { Thread.sleep(150); } catch (InterruptedException ee) {}
            }
           final Rectangle rect = new Rectangle(
               gamePanel.getLocationOnScreen().x + sp.im1.midx + (sp.im1.im.x1*sp.im1.endw/sp.im1.startw),
               gamePanel.getLocationOnScreen().y + sp.im1.midy + (sp.im1.im.y1*sp.im1.endh/sp.im1.starth),
               (gamePanel.getLocationOnScreen().x + sp.im1.midx + (sp.im1.im.x2*sp.im1.endw/sp.im1.startw))-(gamePanel.getLocationOnScreen().x + sp.im1.midx + (sp.im1.im.x1*sp.im1.endw/sp.im1.startw)),
               (gamePanel.getLocationOnScreen().y + sp.im1.midy + (sp.im1.im.y2*sp.im1.endh/sp.im1.starth))-(gamePanel.getLocationOnScreen().y + sp.im1.midy + (sp.im1.im.y1*sp.im1.endh/sp.im1.starth)));            
           
           Thread imThread = new Thread(new DoImageScreenshots(dbs[d], ss[i], sp, gamePanel, rect));
           imThread.start();
            while(imThread.isAlive()){                  
                u.pause(500);
            }
            boolean gifLoop = sp.im1.loopgif;
 	    File file = new File(DoImageScreenshots.mainFolder + shark.sep +
                ss[i]+  (sp.im1.isanimated? "_"+(gifLoop?"loop":"nonloop") :"")   +".txt");
            try{
                if(sp.im1.isanimated) file.createNewFile();
            }catch(Exception ed){}
         //   ss[i] = ss[i].replace("?", "");
        //    File workingSubFolder = new File ( DoImageScreenshots.mainFolder + shark.sep + DoImageScreenshots.workingFolder + shark.sep + ss[i]);
          //  File ff[] = workingSubFolder.listFiles();
            /*
            if(ff==null){
                File file2 = new File(DoImageScreenshots.mainFolder + shark.sep +DoImageScreenshots.workingFolder + shark.sep + 
                      ss[i]+  "ERRROR"  +".txt");
                              try{
	     if(sp.im1.isanimated) file2.createNewFile();
               }catch(Exception ed){}
                              continue;
            }
            
            if(ff.length == 1){
                u.copyfile(ff[0], new File(fResultDir + shark.sep +ff[0].getName()));
            }
            else if (ff.length > 1){
                
                
                try{
                 // grab the output image type from the first image in the sequence
                 BufferedImage firstImage = ImageIO.read(ff[0]);

                 // create a new BufferedOutputStream with the last argument
                 String newName = ff[0].getName();
                 newName = newName.substring(0, newName.length()-4-5);
                 javax.imageio.stream.ImageOutputStream output =  new javax.imageio.stream.FileImageOutputStream(new File(fResultDir + shark.sep +newName + "." + doImage2.fileAniExtension));

                 GifSequenceWriter writer = 
                   new GifSequenceWriter(output, firstImage.getType(), DoImageScreenshots.gifFrameRate, gifLoop);

                 // write out the first image to our sequence...
                 writer.writeToSequence(firstImage);
                 for(int j=1; j<ff.length-1; j++) {
                   BufferedImage nextImage = ImageIO.read(ff[j]);
                   writer.writeToSequence(nextImage);
                 }
                 writer.close();
                 output.close(); 
                      }
                      catch(Exception ee){
                          int hh;
                          hh=  9;
                      }    
                
            }   */
            
            u2_base.writeFile(DoImageScreenshots.logFile, new String[]{DoImageScreenshots.sdatabase+"|"+DoImageScreenshots.soriname+"|"+DoImageScreenshots.sname+"|"+(sp.im1.isanimated?"1":"0")});
   
              
        
        }          
          
          
          
          
      }

  //    String  ss[] = db.list("publicimage", db.IMAGE);

            


      System.exit(0);          
      }
  }
  /**
   * Keylistener interface.
   * @param e Key event
   */
  public void keyTyped(KeyEvent e) {
    if(pausing) return;    // rb 15/1/07
    gamePanel.keytyped(e);
  }
  /**
   *
   */
  public void resizeFont()  {
    FontMetrics m;
    if(originalWordfont == null)
      return;
    wordfont = originalWordfont;
    m=metrics = getFontMetrics(wordfont);
    if(screenwidth < originalScreenwidth
                  || screenheight < originalScreenheight) {
//startPR2007-11-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//                 wordfont  = new Font(wordfont.getName(),
//                                  wordfont.getStyle(),
//                                  Math.max(6,
//                                    Math.min(wordfont.getSize()*screenwidth/originalScreenwidth,
//                                      wordfont.getSize()*screenheight/originalScreenheight)));
      wordfont = wordfont.deriveFont((float)Math.max(6,Math.min(wordfont.getSize()*screenwidth/originalScreenwidth,
      wordfont.getSize()*screenheight/originalScreenheight)));
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        metrics = getFontMetrics(wordfont);
    }
    if(wantSprite && gamePanel.sprite != null) {
       gamePanel.sprite.w =  sharkStartFrame.screenSize.width/20 * mover.WIDTH/screenwidth;
       gamePanel.sprite.h =  sharkStartFrame.screenSize.width/20 * mover.HEIGHT/screenheight;
       gamePanel.sprite.adjustSize(screenwidth,screenheight);
       gamePanel.sprite.mx = - gamePanel.sprite.w/2;
       gamePanel.sprite.my = - gamePanel.sprite.h/2;
     }
  }
  /**
   * Recolours the option button in a game red if the default options are not being
   * used.
   * @param notdefault Indicates whether default options are being used for the game
   */
  public void markoption() {
    if(optionButton != null)
      //       optionButton.setForeground(notdefault ? Color.red:Color.black);
             optionButton.setForeground(options.werechanged(this) ? Color.red:Color.black);
  }
  /**
   *
   */
  public void keypadDeactivate() {
    keypad.deactivate(this);
  }
  /**
   * Ensures that if the background colour is too close in shade to the yellow
   * that orange is used instead.
   * @return yellow if it is not too similar to the background - otherwise returns
   * orange.
   */
  public Color yellow() {
    return u.tooclose2(background,Color.yellow)?Color.orange:Color.yellow;
  }
  /**
   * Ensures that if the background colour is too close in shade to the white
   * that magenta is used instead.
   * @return white if it is not too similar to the background - otherwise returns
   * magenta.
   */
  public Color white() {
    return u.tooclose(background,Color.white)?Color.magenta:Color.white;
  }



  /**
   * Creates a new counter
   * @param title1 Title for the counter
   * @param tooltip_base tooltip_base for the counter
   * @param text Timer's initial display
   * @return The counter
   */
  public JLabel addCounter(String title1,String tooltip, String text) {
    JPanel p = new JPanel();
    p.setLayout(new GridBagLayout());
    if(tooltip!=null)
        p.setToolTipText(tooltip);
    grid2.fill = GridBagConstraints.VERTICAL;
    JLabel title = new JLabel(title1);
    title.setFont(sharkStartFrame.treefont);
    JLabel view  = new JLabel(text);
    view.setFont(sharkStartFrame.treefont);
    grid2.gridx = 0;
    grid2.gridy = -1;
//startPR2005-10-16^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    p.setBackground(sharkStartFrame.defaultbg);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    topPanel.add(p, grid1);
    p.add(title,grid2);
    p.add(view,grid2);
    return view;
   }
   /**
    * <li>Adds student panel (displays current student's name and score to the game's top panel.
    * <li>Adds a mouse listener so that if two people are playing a game their name
    * and scores can be swapped.
    */
   public void addStudent() {
     setStudent();
//startPR2005-10-16^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     studentpanel.setBackground(sharkStartFrame.defaultbg);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     topPanel.add(studentpanel, grid1);
     if(sharkStartFrame.studentList.length > 1)
        studentpanel.addMouseListener(new java.awt.event.MouseAdapter() {
         public void mousePressed(MouseEvent e) {
           nextstudent();
         }
        });
   }
   /**
    * When two students are playing a game switches from the current player to the other.
    * <li> Resets the sprite currently in use
    * <li> Resets the student panel (part of the top panel that displays the student's name
    * and score).
    */
   void nextstudent(){
     if(sharkStartFrame.studentList.length > 1 ) {
       if(++sharkStartFrame.currStudent >= sharkStartFrame.studentList.length){
         sharkStartFrame.currStudent = 0;
       }
       sharkStartFrame.studentList[sharkStartFrame.currStudent].finishSignon(false);// do setup
       setsprite();
       setStudent();
       studentpanel.validate();
      }
   }
   /**
    * Resets the student panel (part of the game's top panel) to display the current
    * student's name and their score.
    */
   public void setStudent() {
     studentpanel.removeAll();
     grid2.fill = GridBagConstraints.VERTICAL;
     JLabel title = new JLabel(sharkStartFrame.studentList[sharkStartFrame.currStudent].name);
//     title.setFont(sharkStartFrame.wordfont);
     title.setFont(sharkStartFrame.treefont);
     pupilscoreView = new JLabel(String.valueOf(sharkStartFrame.studentList[
                                                sharkStartFrame.currStudent].totscore));
//     pupilscoreView.setFont(sharkStartFrame.wordfont);
     pupilscoreView.setFont(sharkStartFrame.treefont);
     grid2.gridx = 0;
     grid2.gridy = -1;
     studentpanel.add(title,grid2);
     studentpanel.add(pupilscoreView,grid2);
   }
   /**
    * Adds the slider and it's label to the top panel of the game
    * @param title1 Title that is displayed with the slider
    * @param val The value the slider is set to initially
    * @return slider
    */
   public JSlider addSlider(String title1, byte val) {
    JPanel p = new JPanel();
    p.setBorder(BorderFactory.createEtchedBorder());
    p.setLayout(new GridBagLayout());
    grid2.fill = GridBagConstraints.HORIZONTAL;
    JLabel title = new JLabel(title1);
    speedtext = title;
    title.setHorizontalAlignment(title.CENTER);
    title.setFont(sharkStartFrame.treefont);
    title.setForeground(Color.black);
    JSlider view  = new JSlider( 1, 8, val);
//    view.setRequestFocusEnabled(false);
    view.setFocusable(false);
    p.setPreferredSize(new Dimension(screenwidth/8,screenheight/26));
//startPR2005-10-16^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    p.setBackground(sharkStartFrame.defaultbg);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    topPanel.add(p, grid1);
    grid2.gridx = 0;
    grid2.gridy = -1;
    grid2.fill = GridBagConstraints.BOTH;
    p.add(title,grid2);
    p.add(view,grid2);
    return view;
   }
   /**
    * Gets the student's total score for this session and updates the display
    * @param add Amount to be added to the students total score for this session
    */
   public void score(int add){
     thistimescore+=add;
     if(gamescore2) {
         if(gamescore1 && gametot1+gametot2 != 0) {
           sharkStartFrame.studentList[sharkStartFrame.currStudent].totscore
              =(short)Math.max(0,sharkStartFrame.studentList[sharkStartFrame.currStudent].
                               totscore+add*gametot1/(gametot1+gametot2));
           otherplayer.totscore
             =(short)Math.max(0,otherplayer.totscore+add*gametot2/(gametot1+gametot2));
           tot1View.setText(String.valueOf(sharkStartFrame.studentList[sharkStartFrame.
                                       currStudent].totscore));
//           tot2View.setText(String.valueOf(otherplayer.totscore));
           return;
         }
 //        else if(isreward && lastreward || !isreward && studentflipped) {
         else if(isreward || !isreward && studentflipped) {
             otherplayer.totscore
                =(short)Math.max(0,otherplayer.totscore+add);
 //            tot2View.setText(String.valueOf(otherplayer.totscore));
             return;
         }
         else  {
             sharkStartFrame.studentList[sharkStartFrame.currStudent].totscore
                =(short)Math.max(0,sharkStartFrame.studentList[sharkStartFrame.currStudent].
                                 totscore+add);
             tot1View.setText(String.valueOf(sharkStartFrame.studentList[
                                            sharkStartFrame.currStudent].
                                            totscore));
             return;
         }
     }
     sharkStartFrame.studentList[sharkStartFrame.currStudent].totscore
        =(short)Math.max(0,sharkStartFrame.studentList[sharkStartFrame.currStudent].
                         totscore+add);
     if(pupilscoreView != null){
       pupilscoreView.setText(String.valueOf(sharkStartFrame.studentList[
                                             sharkStartFrame.currStudent].
                                             totscore));
     }
   }
   /**
    * Used when two players are playing a game. THis adds the amount passed to
    * the score of the student who is currently active.
    * @param add Amount to be added to the student's score for this game
    */
   public void gamescore(int add) {
     if((gamescore2 || computerscore) && !isreward && studentflipped || isreward) {
        gametot2=Math.max(0,gametot2+add);
        if(sp!=null)sp.setuser2score(gametot2);
     }
     else {
        gametot1=Math.max(0,gametot1+add);
        if(sp!=null)sp.setuser1score(gametot1);
     }
     int score =  gametot1;
     if(gamescore2) score+=gametot2;
     if(gamescore1View!=null)gamescore1View.setText(String.valueOf(score));
     delayedflip = true;
   }
   
/*
   public void gamescore(int add) {
     if(gamescore1View!=null)
         gamescore1View.setText(String.valueOf(gametot1));
//     if(gamescore2 && !isreward && studentflipped || isreward && lastreward) {
     if(gamescore2 && !isreward && studentflipped || isreward) {
        gametot2=Math.max(0,gametot2+add);
        if(sp!=null)sp.setuser2score(gametot2);
        if(gamescore2View!=null)
            gamescore2View.setText(String.valueOf(gametot2));
     }
     else {
        gametot1=Math.max(0,gametot1+add);
        if(gamescore1View!=null)gamescore1View.setText(String.valueOf(gametot1));
        if(sp!=null)sp.setuser1score(gametot1);
     }
     delayedflip = true;
   }
    * 
    * 
    */
   

public void flip() {
    delayedflip = false;
     if(gamescore2) {    // v5 start rb 7/3/08
//        if(++hadscore >= switchevery) {
//           hadscore = 0;
           flipstudent(true);
//        }
     }
}


   /**
    * <li>Causes the sprite to go into it's error state
    * <li>Increments the error total in the top pane's error display
    */
   public void error() {
     if(gamePanel.sprite != null && gamePanel.showSprite && gamePanel.mousemovertot == 0) {
       gamePanel.sprite.setControl("error");
       gamePanel.newsprite  = true;
       gamePanel.mousemoved(gamePanel.mousexs,gamePanel.mouseys);
       gamePanel.refreshat = gtime+2000;
     }
     ++errortot;
     errorView.setText(String.valueOf(errortot));
     errorView.setFont(sharkStartFrame.treefont);
   }
   /**
    * <li>Records the error in the student's record
    * <li>Causes the sprite to go into it's error state
    * <li>Increments the error total in the top panel's error display
    * @param text The text to be recorded in the student's record
    */
   public void error(String text){
     if(gamePanel.sprite != null && gamePanel.showSprite && gamePanel.mousemovertot == 0) {
       gamePanel.sprite.setControl("error");
       gamePanel.newsprite  = true;
       gamePanel.mousemoved(gamePanel.mousexs,gamePanel.mouseys);
       gamePanel.refreshat = gtime+2000;
     }
     ++errortot;
     errorView.setText(String.valueOf(errortot));
     errorView.setFont(sharkStartFrame.treefont);
     if(gamescore2 && studentflipped) {                  // v5 start rb 9/3/08
       if (otherstudentrecord.errorList != null) {
         for (short i = 0; i < otherstudentrecord.errorList.length; ++i) {
           if (otherstudentrecord.errorList[i].equals(text))
             return;
         }
       }
       otherstudentrecord.errorList = u.addString(otherstudentrecord.errorList,text);
     }
     else {
       if (studentrecord.errorList != null) {
         for (short i = 0; i < studentrecord.errorList.length; ++i) {
           if (studentrecord.errorList[i].equals(text))
             return;
         }
       }
       studentrecord.errorList = u.addString(studentrecord.errorList,text);
     }                                                      // v5 end rb 9/3/08
   }
   
   
   public void errorSilent() {
     ++errortot;
     errorView.setText(String.valueOf(errortot));
     errorView.setFont(sharkStartFrame.treefont);
   }    
   
   /**
    * When a death occurs in a game (e.g. in catchshark,bombs or maze)
    * the death total is updated and the number of deaths, that have occurred in the game,
    * are displayed.
    * @param add
    */
   public void death(int add) {//SS 17/12/03
     ++deathtot;
     deathsView.setText(String.valueOf(deathtot));
     deathsView.setFont(sharkStartFrame.treefont);
   }
   /**
    * If there is a word in use and there is no peep being displayed
    * currently this: -
    * <li> Creates a new peep word for display.
    * <li> Updates the student record with the word peeped at.
    */
   public void peep1() {
     if(currword == null || currpeep != null)
       return;
     new peepword(new String[]
                  {((rgame.options & word.SPLIT) != 0)?currword.v():currword.vsplit()},
            "peep",2000,this);
     ++peeptot;
     peepsView.setText(String.valueOf(peeptot));
     peepsView.setFont(sharkStartFrame.treefont);
     if(gamescore1 && studentflipped)
       otherstudentrecord.peepList = u.addString(studentrecord.peepList,currword.v());
     else  studentrecord.peepList = u.addString(studentrecord.peepList,currword.v());
   }
   /**
    * Displays a message for a specified period of time.
    * @param m Message to be displayed.
    * @param time Length of time the message will be displayed for.
    */
  public void flash(String m[], int time) {
     if(currpeep==null)
       new peepword(m, "message", time, this);
  }
  /**
   * Causes current word to be said and displays the appropriate picture.
   */
  public void defsay(word ww) {
    if(rightclick) ww.defsay();
    else ww.say();
  }
  public void listen1() {
     if(currword != null) {
       if(currword.homophone || currword.nohomo()) defsay(currword);  // double-request gives definition
       else {    // if homophone is present but not used by default then force it
         currword.spokenword = null;
         currword.homophone = true;
         defsay(currword);
         currword.homophone = false;
         currword.spokenword = null;
       }
       if(!nolistenpic && !specialprivateon)
           (new showpicture()).stopat = System.currentTimeMillis()+4000;
       if(spokenWord.extrainf != null) {
          listenButton.setText(spokenWord.extrainf);
          listenButton.setIcon(null);
          listenButton.setBackground(Color.red);
          listenButton.setForeground(Color.white);
          new spokenWord.whenfree(3000) {
            public void action() {
             listenButton.setText(listentext);
             listenButton.setIcon(listenicon);
             listenButton.setBackground(sharkStartFrame.defaultbg);
             listenButton.setForeground(Color.black);
            }
          };
       }
     }
     gamePanel.startrunning();
     gamePanel.requestFocus();
   }
   //-------------------------------------------------------
     public void pause1() {
       if(pausing) {
         pauseButton.setText(pausel);
         pauseButton.setBackground(pausecolor);
         pausing=false;
         losetime += (System.currentTimeMillis() - startpause);    // rb 15/1/07
         gamePanel.pause = false;                 // rb 15/1/07
         gamePanel.startrunning();                // rb 15/1/07
       }
       else {
         pauseButton.setText(pauselx);
         pauseButton.setBackground(Color.red);
         pausing=true;
         gamePanel.pause = true;              // rb 15/1/07
         startpause = System.currentTimeMillis();                  // rb 15/1/07
       }
     }
   /**
    * Message is displayed using the default colour black.
    * @param m Message to be displayed
    * @param x1 Smaller x coordinate used to position message
    * @param y1 Smaller y coordinate used to position message
    * @param x2 Larger x coordinate used to position message
    * @param y2 Larger y coordinate used to position message
    * @return Mover that displays the message
    */
   public mover showmessage(String m, int x1, int y1, int x2,int y2) {
     String mess[] = u.splitString(m);
     messmover2 mm = new messmover2(spellchange.spellchange(mess),x2-x1,y2-y1);
     gamePanel.addMover(mm, x1, y1);
     mm.sayit = m;
     return mm;
  }
  /**
   * Message is displayed using the specified colour.
   * @param m Message to be displayed
   * @param x1 Smaller x coordinate used to position message
   * @param y1 Smaller y coordinate used to position message
   * @param x2 Larger x coordinate used to position message
   * @param y2 Larger y coordinate used to position message
   * @param color Colour message should be displayed in
   * @return Mover that displays the message
   */
  public mover showmessage(String m, int x1, int y1, int x2,int y2,Color color) {
     String mess[] = u.splitString(m);
     messmover2 mm = new messmover2(spellchange.spellchange(mess),x2-x1,y2-y1);
     mm.color = color;
     mm.sayit = m;
     gamePanel.addMover(mm, x1, y1);
     return mm;
  }


  public mover showmessage(String m, int x1, int y1, int x2,int y2,Color color, Color bgcolor, int arc, boolean addmover) {
     String mess[] = u.splitString(m);
     messmover2 mm = new messmover2(spellchange.spellchange(mess),x2-x1,y2-y1,bgcolor, arc);
     mm.color = color;
     mm.sayit = m;
     if(addmover)
         gamePanel.addMover(mm, x1, y1);
     return mm;
  }
  /**
   * Used by dicfish - displays the string m
   * @param m String to be displayed
   * @param num The number of baby fish still alive
   * @return String with the number of baby fish alive (if '%' is used
   * to indicate where the number should be positioned) or the string
   * passed as a parameter.
   */
  public String editstring(String m, int num) {
     int i = m.indexOf('%');
     if(i>=0)
       return (m.substring(0, i) + String.valueOf(num) + m.substring(i + 1));
     return m;
  }
  /**
   * @param wait time in milliseconds till game is exited
   */
  public void exit(int wait) {
    if(endplaytime==0) endplaytime = System.currentTimeMillis();
    completed = true;
    javax.swing.Timer etimer = new javax.swing.Timer(wait,
       new ActionListener() {
          public void actionPerformed(ActionEvent e){
            terminate();
          }
      }
    );
    etimer.setRepeats(false);
    etimer.start();
  }
   /**
    * Displays error message
    * @param m error message
    */
   public void fatalerror(String m) {
      if(gamePanel.sprite != null)
        gamePanel.showSprite = true;
      showmessage(m,0,0,mover.WIDTH,mover.HEIGHT/2);
      addMover(new ebutton(),mover.WIDTH/2,mover.HEIGHT*2/3);
      gamePanel.dontstart = false;
      fatal = true;
   }
  /**
  * Creates the exit button that appears after playing a game.
  * @param y Height of button
  */
 public void exitbutton(int y) {
     completed = true;
     if(gamePanel.sprite != null) gamePanel.showSprite = true;
     addMover(new ebutton(),mover.WIDTH/2-mover.WIDTH/12,y);
     if(optionButton != null) optionButton.setEnabled(false);
     help("help_endgame");
  }
   /**
    * Creates the exit button that appears after playing a game.
    * @param x Width of button
    * @param y Height of button
    */
   public void exitbutton(int x,int y) {
      completed = true;
      if(gamePanel.sprite != null) gamePanel.showSprite = true;
      addMover(new ebutton(),x,y);
      if(optionButton != null) optionButton.setEnabled(false);
      help("help_endgame");
   }

   public void exitbutton(int x,int y, String text) {
      completed = true;
      if(gamePanel.sprite != null) gamePanel.showSprite = true;
      addMover(new ebutton(text),x,y);
      if(optionButton != null) optionButton.setEnabled(false);
      help("help_endgame");
   }
   /**
    * <p>Title: WordShark</p>
    * <p>Description: Mover that displays a message with a colour set by a parameter</p>
    * <p>Copyright: Copyright (c) 2004</p>
    * <p>Company: WhiteSpace</p>
    * @author Roger Burton
    * @version 1.0
    */
   public class mmover extends mover {
         String[] message;
         Color color;
         int oldw;
         Font f;
         FontMetrics m;
         /**
          * @param mess Message to be displayed
          * @param w1 width
          * @param h1 height
          * @param color1 colour to be used.
          */
         public mmover(String mess, int w1, int h1,Color color1) {
            super(false);
            message = new String[]  {mess};
            color = color1;
            w = w1;
            h = h1;
         }
         /**
          * @param g Graphics object to be used
          * @param x X coordinate
          * @param y Y coordinate
          * @param w1 Width
          * @param h1 Height
          */
         public void paint(Graphics g,int x, int y, int w1, int h1) {
          if(f==null || oldw != w1) {
             f = sizeFont(g,message,w1,h1);
             m = getFontMetrics(f);
             oldw = w1;
          }
          g.setFont(f);
          g.setColor(color);
          x += w1/2;
          y += Math.max(0, h1/2 - m.getHeight()*message.length/2 + m.getMaxAscent());
          for(short i=0; i<message.length;++i) {
             g.drawString(message[i], x - m.stringWidth(message[i])/2,y);
             y += m.getHeight();
          }
        }
     }
     /**
      * <p>Title: WordShark</p>
      * <p>Description: Mover that displays a message </p>
      * <p>Copyright: Copyright (c) 2004</p>
      * <p>Company: WhiteSpace</p>
      * @author Roger Burton
      * @version 1.0
      */
     public class messmover2 extends mover {
      String[] message;
      Color color = Color.black;
      int oldw;
      Font f;
      FontMetrics m;
      Color bgcol;
      int roundcorner;
      /**
       * @param mess The message to be displayed
       * @param wi Width of message
       * @param hi Height of message
       */
      public messmover2(String[] mess, int wi, int hi) {
         super(false);
         message = mess;
         w = wi;
         h = hi;
      }

      public messmover2(String[] mess, int wi, int hi, Color bgcolor, int arc) {
         super(false);
         message = mess;
         w = wi;
         h = hi;
         bgcol = bgcolor;
         roundcorner = arc;
      }

     public boolean isOver(int mx,int my) {
       return new Rectangle(x,y,w,h).contains(mx,my);
     }

      /**
       * @param g graphics object to be used
       * @param x1 X coordinate
       * @param y1 Y coordinate
       * @param w1 Width of rectangle containing message.
       * @param h1 Height of rectangle containing message.
       */
      public void paint(Graphics g,int x1, int y1, int w1, int h1) {
       int xx=x1,yy=y1;
       Graphics2D g2d = ((Graphics2D)g);
       int xtextadjust = 0;
       g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
       if(bgcol!=null){
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        
        g.setColor(bgcol);
        g.fillRoundRect(x1, y1, w1, h1, roundcorner, roundcorner);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        xtextadjust = 10;
       }

       if(clearexit) {
         g.setColor(Color.white);
         g.fillRect(x1,y1,w1,h1);
       }
       boolean dw = oldw != w1;
       if(f == null || dw) {
          oldw = w1;
          f = sizeFont(g,message,w1-xtextadjust,h1);
          m = getFontMetrics(f);
       }
       g.setFont(f);
       g.setColor(color);
       xx = x1+(w1-xtextadjust)/2 + (xtextadjust==0?0:(xtextadjust/2));
       yy = y1+Math.max(0, h1/2 - m.getHeight()*message.length/2 + m.getMaxAscent());
       for(short i=0; i<message.length;++i) {
          g.drawString(message[i], xx - m.stringWidth(message[i])/2,yy);
          yy += m.getHeight();
       }
       if(sayitrect == null || dw) {
          Dimension dd = u.getdim(message,m,4);
          sayitrect = new Rectangle(xx-dd.width/2, y1+h1/2-dd.height/2,dd.width,dd.height);
       }
       g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
    }
  }
  /**
   * @param m mover to be added
   * @param x1 Logical x position where mover is to be added (References the
   * top left corner)
   * @param x2 Logical y position where mover is to be added (References the
   * top left corner)
   */
  public void addMover(mover m,int x1, int x2) {
      if(gamePanel != null)
        gamePanel.addMover(m,x1,x2);
   }
 //-------------------------------------------------------
   public mover addMover(Image im1, int x1, int x2,Color c) {
      mover m = new mover(im1,screenwidth,screenheight,c);
      gamePanel.addMover(m,x1,x2);
      return m;
   }
   /**
    * Removes a mover from the game panel
    * @param m Mover to be removed
    */
   public void removeMover(mover m) {
      if(gamePanel != null)
        gamePanel.removeMover(m);
   }
   //--------------------------------------
   /**
    * Sizes font so all string elements fit
    * @param g Graphics object
    * @param s String font is to be applied to
    * @param w1 Width in real screen coords.
    * @param h1 Height in real screen coords.
    * @return Resized font
    */
   public static Font sizeFont(Graphics g, String s[], int w1, int h1) {
        Font f  = sizeFont(s,w1,h1);
        g.setFont(f);
        return f;
   }
   /**
    * Sizes font so all string elements fit
    * @param s String to be fitted in
    * @param w1 Width available in real screen coordinates.
    * @param h1 Height available in real screen coordinates.
    * @return Resized font
    */
   public static Font sizeFont(String s[], int w1, int h1) {
        int w = w1;
        int h = h1/s.length;
        int swidth = sharkStartFrame.wordfontm.stringWidth(s[0]);
        int points = sharkStartFrame.MAXFONTPOINTS_3;
        if(s[0].length() > 1) points = Math.min(sharkStartFrame.MAXFONTPOINTS_3,Math.max(8,
           Math.min(sharkStartFrame.BASICFONTPOINTSX*h/sharkStartFrame.wordfontheight,
              sharkStartFrame.BASICFONTPOINTSX*w/swidth)));
//startPR2007-11-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
// Font f  = new Font(sharkStartFrame.wordfont.getName(),
//                sharkStartFrame.wordfont.getStyle(),
//                points);
        Font f  = sharkStartFrame.wordfont.deriveFont((float)points);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        FontMetrics m = sharkStartFrame.mainFrame.getFontMetrics(f);
        while(points>8 && m.getHeight() > h) {
//startPR2007-11-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//              f = new Font(f.getName(), f.getStyle(), --points);
          f = f.deriveFont((float)--points);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          m = sharkStartFrame.mainFrame.getFontMetrics(f);
        }
        for(short i=0;points > 8 && i<s.length;++i) {
           while(points > 8 &&  (swidth=m.stringWidth(s[i])) > w) {
//startPR2007-11-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//             f = new Font(f.getName(), f.getStyle(),
//                   points = Math.max(8,Math.min(points-1,points*w/swidth)));
             f = f.deriveFont((float)(points = Math.max(8,Math.min(points-1,points*w/swidth))));
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
               m = sharkStartFrame.mainFrame.getFontMetrics(f);
           }
        }
        return f;
   }
   public static Font sizePlainFont(String s[], int w1, int h1, int maxsize, Font fbase) {
        int w = w1;
        int h = h1/s.length;
        int swidth = sharkStartFrame.wordfontm.stringWidth(s[0]);
        int points = maxsize;
        if(s[0].length() > 1) points = Math.min(sharkStartFrame.MAXFONTPOINTS_3,Math.max(8,
           Math.min(sharkStartFrame.BASICFONTPOINTSX*h/sharkStartFrame.wordfontheight,
              sharkStartFrame.BASICFONTPOINTSX*w/swidth)));
//startPR2007-11-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
// Font f  = new Font(sharkStartFrame.treefont.getName(),
//                sharkStartFrame.treefont.getStyle(),
//                points);
        Font f  = fbase.deriveFont((float)points);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        FontMetrics m = sharkStartFrame.mainFrame.getFontMetrics(f);
        while(points>8 && m.getHeight() > h) {
//startPR2007-11-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//              f = new Font(f.getName(), f.getStyle(), --points);
          f = f.deriveFont((float)--points);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          m = sharkStartFrame.mainFrame.getFontMetrics(f);
        }
        for(short i=0;points > 8 && i<s.length;++i) {
           while(points > 8 &&  (swidth=m.stringWidth(s[i])) > w) {
//startPR2007-11-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//             f = new Font(f.getName(), f.getStyle(),
//                   points = Math.max(8,Math.min(points-1,points*w/swidth)));
             f = f.deriveFont((float)(points = Math.max(8,Math.min(points-1,points*w/swidth))));
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
             m = sharkStartFrame.mainFrame.getFontMetrics(f);
           }
        }
        return f;
   }
   // same as above with no size limit on single string
   public static Font sizeFontbig(String s[], int w1, int h1) {
        int w = w1;
        int h = h1;
        int swidth = sharkStartFrame.wordfontm.stringWidth(s[0]);
        int points = Math.max(8,
           Math.min(sharkStartFrame.BASICFONTPOINTSX*h/sharkStartFrame.wordfontheight,
              sharkStartFrame.BASICFONTPOINTSX*w/swidth));
//startPR2007-11-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    Font f  = new Font(sharkStartFrame.wordfont.getName(),
//                   sharkStartFrame.wordfont.getStyle(),
//                   points);
        Font f  = sharkStartFrame.wordfont.deriveFont((float)points);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        FontMetrics m = sharkStartFrame.mainFrame.getFontMetrics(f);
        while(points>8 && m.getHeight() > h) {
//startPR2007-11-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//              f = new Font(f.getName(), f.getStyle(), --points);
          f = f.deriveFont((float)--points);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          m = sharkStartFrame.mainFrame.getFontMetrics(f);
        }
        for(short i=0;points > 8 && i<s.length;++i) {
          while (points > 8 && (swidth = m.stringWidth(s[i])) > w) {
//startPR2007-11-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//            f = new Font(f.getName(), f.getStyle(),
//                         points = Math.max(8,
//                                           Math.min(points - 1, points * w / swidth)));
                f = f.deriveFont((float)(points = Math.max(8,Math.min(points - 1, points * w / swidth))));
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            m = sharkStartFrame.mainFrame.getFontMetrics(f);
          }
        }
        return f;
   }
   /**
    * Adjusts font passed so that string s fits, into the area designated
    * using w and h, when the font is applied to it.
    * @param f font to be adjusted
    * @param s String that the new font is to be applied to
    * @param w Real screen coordinate
    * @param h Real screen coordinate
    * @return Adjusted font
    */
   public static Font adjustFont(Font f, String s, int w, int h) {
      if(f == null) {
        return sizeFont(new String[] {s}
                        , w, h);
      }
      else {
        FontMetrics m = sharkStartFrame.mainFrame.getFontMetrics(f);
        int swidth = m.stringWidth(s);
        int points = f.getSize();
        while(points>8 && m.getHeight() > h) {
//startPR2007-11-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//              f = new Font(f.getName(), f.getStyle(), --points);
              f = f.deriveFont((float)--points);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
               m = sharkStartFrame.mainFrame.getFontMetrics(f);
        }
        while(points > 8 &&  (swidth=m.stringWidth(s)) > w) {
//startPR2007-11-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//          f = new Font(f.getName(), f.getStyle(),
//                   points = Math.max(8,Math.min(points-1,points*w/swidth)));
      f = f.deriveFont((float)(points = Math.max(8,Math.min(points-1,points*w/swidth))));
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
               m = sharkStartFrame.mainFrame.getFontMetrics(f);
        }
        return f;
     }
   }
   /**
    * Size font so all word elements fit
    * @param s Word that the resized font is to be applied to
    * @param w1 Width available.
    * @param h1 Height available
    * @return Font to be used for the word passed to enable it to fit into
    * space defined uing w1 and h1.
    */
   public Font sizeFont(word s[], int w1, int h1) {
        String ss[] = new String[s.length];
        for(short i=0;i<s.length;++i){
          ss[i] = s[i].v();
        }
        return sizeFont(ss,w1,h1);
   }
   /**
    * Sizes font for so all current words to a maximum of 40 pts
    * @param w1 Width
    * @param h1 Height
    * @return The font to be used
    */
   public Font sizeFont(int w1, int h1) {
      String s = ((rgame.options & word.SPLIT)!=0)?
                              rgame.w[0].vsplit():rgame.w[0].v();
      int points = Math.max(8,Math.min(sharkStartFrame.MAXFONTPOINTS_2,
              Math.min(sharkStartFrame.BASICFONTPOINTSX*h1/sharkStartFrame.wordfontheight,
              sharkStartFrame.BASICFONTPOINTSX*w1/sharkStartFrame.wordfontm.stringWidth(s))));
//startPR2007-11-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    Font f = new Font(sharkStartFrame.wordfont.getName(),
//                     sharkStartFrame.wordfont.getStyle(),
//                     points);
   Font f = sharkStartFrame.wordfont.deriveFont((float)points);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      for(short i=0;i<rgame.w.length;++i)
           f = adjustFont(f,((rgame.options & word.SPLIT)!=0)?
                              rgame.w[i].vsplit():rgame.w[i].v() , w1,h1);
     metrics = getFontMetrics(f);;
     originalWordfont = f;
     originalScreenwidth = screenwidth;
     originalScreenheight = screenheight;
     return f;
   }
   /**
    * Size font for big char
    * @param ch Char to be resized
    * @param w1 Width
    * @param h1 Height
    * @return font to be used
    */
   public Font bigcharFont(char ch, int w1, int h1) {
        return sizeFontbig(new String[] {String.valueOf(ch)},w1,h1);
   }
   /**
    * Resize font for one string and applies the font to the graphics object.
    * @param f Font to be resized.
    * @param g Object that the font will be applied to.
    * @param s The string that must fit into the area designated using w1 and h1.
    * @param w1 Width available for the string to fit into.
    * @param h1 Height available for the string to fit into.
    * @return Font to be used.
    */
   public static Font sizeFont(Font f,Graphics g, String s, int w1, int h1) {
        f = adjustFont(f,s,w1,h1);
        g.setFont(f);
        return f;
   }
   /**
    * Size font for mover
    * @param f Font to be sized - if NULL then font is created, with a point size
    * between 8 and 40, using sharkStartFrame's font.
    * @param s String to be fitted in
    * @param w1 Sidth of space available to fit the string into.
    * @param h1 Height of space available to fit the string into.
    * @return Font to be used.
    */
   public Font sizeFont(Font f, String s, int w1, int h1) {
        int w = w1*screenwidth/mover.WIDTH;
        int h = h1*screenheight/mover.HEIGHT;
        if(f == null) {
           int points = Math.max(8,Math.min(sharkStartFrame.MAXFONTPOINTS_2,
              Math.min(sharkStartFrame.BASICFONTPOINTSX*h/sharkStartFrame.wordfontheight,
              sharkStartFrame.BASICFONTPOINTSX*w/sharkStartFrame.wordfontm.stringWidth(s))));
//startPR2007-11-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      f  = new Font(sharkStartFrame.wordfont.getName(),
//               sharkStartFrame.wordfont.getStyle(),
//               points);
         f  = sharkStartFrame.wordfont.deriveFont((float)points);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        }
        f = adjustFont(f,s,w,h);
        metrics = getFontMetrics(f);
        originalWordfont = f;
        originalScreenwidth = screenwidth;
        originalScreenheight = screenheight;
        return f;
   }
   /**
    * Closes the illustration of the current word and explodes the mover just clicked.
    * @param m mover to be exploded (fragmented).
    * @param x x coordinate of click event.
    * @param y y coordinate of click event.
    * @param savezapp true if zapp to be saved.
    */
   
   
   public void zapp(mover m, int x,int y,boolean savezapp) {
     closepicture();
     gamePanel.zapp(m,x,y,savezapp);
   }  
   

   /**
    * Closes the illustration of the current word and explodes the mover just clicked.
    * @param m Mover to be exploded (fragmented).
    */
   public void zapp(mover m) {
     closepicture();
     gamePanel.zapp(m,(m.x+m.w/2)*screenwidth/mover.WIDTH,
                    (m.y+m.h/2)*screenheight/mover.HEIGHT,
                    false);
   }
   /**
    *
    */
   public void redrawMovers() {
     if (gamePanel != null) {
       gamePanel.copyall = true;
     }
   }
   /**
    * Removes the current picture(the picture that illustrates the word in use).
    */
   public void closepicture() {
//startPR2008-06-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//     if(currshowpicture != null && gamePanel.isMover(currshowpicture)) {
//       removeMover(currshowpicture);
//     }
     if(currshowpicture != null){
       if(gamePanel.isMover(currshowpicture)) {
         removeMover(currshowpicture);
       }
       if(currshowpicture.video!=null){
         currshowpicture.video.stopall();
       }
     }
//endPRimagesandvids^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     currshowpicture = null;
   }
   /**
     * Gives size needed in a mover for the largest word in the word list
     * currently in use.
     * @param f1 Font in use
     * @return Width of largest word, within the word list in use, when the
     * font passed is applied to it.
     */
   public int maxWordWidth(Font f1) {
        FontMetrics m = getFontMetrics(f1);
        int maxlen = 0;
        for(short i=0;i<rgame.w.length;++i) {
           maxlen = Math.max(maxlen,
                 m.stringWidth(((rgame.options & word.SPLIT)!=0)?
                       rgame.w[i].vsplit():rgame.w[i].v()));
        }
       return maxlen;
   }
      /**
       * Gives size needed in a mover for the largest word in the word list
       * currently in use.
       * @param m Font metrics of the font in use
       * @param s
       * @return Width of largest word, within the word list in use, when the
       * font passed is applied to it.
       */
   public int maxWordWidth(FontMetrics m) {
        int maxlen = 0;
        for(short i=0;i<rgame.w.length;++i) {
           maxlen = Math.max(maxlen,
                 m.stringWidth(((rgame.options & word.SPLIT)!=0)?
                       rgame.w[i].vsplit():rgame.w[i].v()));
        }
        return maxlen;
   }
      /**
       * @return Maximum length of any word in the current word list
       */
      public int maxWordLen() {
        int maxlen = 0;
        for(short i=0;i<rgame.w.length;++i) {
           maxlen = Math.max(maxlen,
              ((rgame.options & word.SPLIT)!=0)?
                       rgame.w[i].vsplit().length():rgame.w[i].v().length());
        }
        return maxlen;
   }
   /**
    * @param s Strings to be compared
    * @return The maximum length of any string contained in the array passed.
    */
   public static short maxWordLen(String s[]) {
        int maxlen = 0;
        for(short i=0;i<s.length;++i) {
           maxlen = Math.max(maxlen, s[i].length());
        }
        return (short)maxlen;
   }
   /**
    *
    */
   public void makeBackground() {
//     gamePanel.setBackground(background = u.backgroundcolor(bgavoid));
     makeBackground(false);
   }

   public void makeBackground(boolean ignoreSetting) {
     gamePanel.setBackground(background = u.backgroundcolor(bgavoid, ignoreSetting));
   }
   /**
    * To be overridden
    */
   public void sparefunc(){};
   /**
    * Called once screen has been drawn so that alterations are done when the
    * screen is not being altered by something else. This is overridden by individual games.
    * @param t Time
    */
   public void afterDraw(long t){};
   /**
    *  set font size - to be overridden.
    */
   public void sizeFont(){}
   /**
    * Click not over mover - to be overridden.
    * @param x X coordinate of click event.
    * @param y Y coordinate of click event.
    */
   public void badclick(int x, int y){}
   /**
    * Used for any mouse click. Can be overridden.
    * @param x x coordinate of click event.
    * @param y y coordinate of click event.
    * @return Default value of false.
    */
   public boolean click(int x, int y){return false;}
   /**
    * Overridden by games that use keyboard input e.g. bombs
    * @param key Key that was pressed.
    */
   public void keyhit(char key) {};
   /**
    * Overridden by games classes so the speed is changed.
    */
   public void newspeed() {};
   /**
    * Overridden by games classes so as to enable printed sheets to be produced
    */
   public void print(){};
   /**
    * To be overridden.
    */
   public void resize(){};
   /**
    * Used to restart a game.
    */
   public void restart(){
     if(gamePanel == null) return;
      errortot=peeptot=deathtot=gametot1=gametot2=0;
      if(errorView != null) errorView.setText(String.valueOf(errortot));
      if(peepsView != null) peepsView.setText(String.valueOf(peeptot));
      if(deathsView != null) deathsView.setText(String.valueOf(deathtot));
      if(gamescore1View != null) gamescore1View.setText(String.valueOf(gametot1));
//      if(gamescore2View != null) gamescore2View.setText(String.valueOf(gamescore2));
//      if(sp!=null)sp.switchuser(1, true);
      if(sp!=null)sp.switchuser(studentflipped?2:1, true);
      gamePanel.cleartotal();
      startplaytime = System.currentTimeMillis();
      endplaytime=0;
      losetime=0;   // rb 15/1/07
      if(timerthread != null) timerthread.stopit = true;
      timerthread = new showtime();
      timerthread.start();
  }
  /**
   * @param on If true enables the peep button in a game
   */
  public void setpeep(boolean on) {
      if(!shark.phonicshark)
        if(peepButton != null) peepButton.setEnabled(on);
  }
  /**
   * @param on True when the listen button is to be enabled.
   */
  public void setlisten(boolean on) {
     if(listenButton != null) listenButton.setEnabled(on);
  }
  /**
   * Used when two players are playing a game together to switch from one player to the other.
   * <li> Changes the sprite if the game uses one.
   * <li> Switches the background colour of the student's score display in games
   * such as snap or pairs. This indicates which player is actively playing.
   */
  public void flipstudent(boolean doflip) {           // v5 start rb 7/3/08
    if(completed)
        return;
    if(doflip)
        studentflipped = !studentflipped;
    setflip(studentflipped);
  }
  public void flipstudentreward() {    //  flip stu for reward_base game
 //  setflip(lastreward);
  }
  void setflip(boolean flipped) {
    if(altsprite == null || oldsprite == null) {
          oldsprite = new sharkImage(sharkStartFrame.studentList[sharkStartFrame.currStudent].spritename,
                                               false);
          oldsprite.w =  sharkStartFrame.screenSize.width/20 * mover.WIDTH/screenwidth;
          oldsprite.h =  sharkStartFrame.screenSize.width/20 * mover.HEIGHT/screenheight;
          oldsprite.adjustSize(screenwidth,screenheight);
          oldsprite.mx = - oldsprite.w/2;
          oldsprite.my = - oldsprite.h/2;
          oldsprite.manager = gamePanel;
          altsprite  = new sharkImage(otherplayer.spritename,
                                               false);
          altsprite.w =  sharkStartFrame.screenSize.width/20 * mover.WIDTH/screenwidth;
          altsprite.h =  sharkStartFrame.screenSize.width/20 * mover.HEIGHT/screenheight;
          altsprite.adjustSize(screenwidth,screenheight);
          altsprite.mx = - altsprite.w/2;
          altsprite.my = - altsprite.h/2;
          altsprite.manager = gamePanel;
    }

    gamePanel.sprite = flipped?altsprite:oldsprite;
    if(wantSprite) {
      if (!gamePanel.sprite.name.equals("x_aaa")) {
        gamePanel.setCursor(sharkStartFrame.nullcursor);
        gamePanel.winsprite = gamePanel.winspritevisible = false;
      }
      else {
        gamePanel.setCursor(null);
        gamePanel.winsprite = gamePanel.winspritevisible = true;
      }
        gamePanel.clearall();
    }
//    JLabel p1,p2;
    if(sp!=null)sp.switchuser(flipped?2:1, true);
//    if(gamescore1) {p1 = gamescore1View; p2 = gamescore2View;}
//    if(gamescore1) {p1 = gamescore1View;}
//    else {p1 = tot1View; p2 = tot2View;}
//    else {p1 = tot1View;}
//    if(flipped) {
//      colorall(p1, sharkStartFrame.defaultbg);
//      colorall(p2, Color.red);
//      ((JPanel)p1.getParent()).setBorder(null);
//      ((JPanel)p2.getParent()).setBorder(BorderFactory.createLineBorder(Color.black,4));
//    }
//    else {
//      colorall(p1, Color.yellow);
//      colorall(p2, sharkStartFrame.defaultbg);
//      ( (JPanel) p1.getParent()).setBorder(BorderFactory.createLineBorder(Color.black, 4));
//      ( (JPanel) p2.getParent()).setBorder(null);
//    }
  }
  public void cancelflip() {
      setflip(false);
      if(gamescore1View!=null)
      ( (JPanel)gamescore1View.getParent()).setBorder(null);
  }                                                               // v5 end rb 7/3/08
  public void adjustscores(short score1,short score2) {
//        if(gamescore1View!=null && gamescore2View!=null){
         if(sp!=null){
//startPR2005-10-16^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      Color c1 = gamescore1View.getBackground();
//      Color c2 = gamescore2View.getBackground();
//        Color c1;
//        Color c2;
//        if(shark.macOS){
//          c1 = sharkStartFrame.defaultbg;
//          c2 = sharkStartFrame.defaultbg;
//        }
//        else{
//          c1 = gamescore1View.getBackground();
//          c2 = gamescore2View.getBackground();
//        }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      gamescore1View.setText(String.valueOf(score1));
//      gamescore2View.setText(String.valueOf(score2));
   
      sp.setuser1score(score1);
      sp.setuser2score(score2);       
      
      if(score1 < score2 && gametot1 >= gametot2) {                        //If 1
          
 
      sp.switchuser(2, false);
          
//         colorall(gamescore1View,c2);
//         colorall(gamescore2View,Color.white);
      }
      else if(score1 > score2 && gametot1 <= gametot2) {              //Else if 1
//         colorall(gamescore1View,Color.white);
//         colorall(gamescore2View,c1);
         
         sp.switchuser(1, false);
      }
      else if(score1 == score2 && gametot1 != gametot2) {             //Else if 1
//         if(gametot1 >= gametot2){                                         //If 1.1
//           c1 = c2; // get neutral color
//         }
//         colorall(gamescore1View,c1);
//         colorall(gamescore2View,c1);
          sp.switchuser(0, false);
      }
        }
                
      gametot1 = score1;
      gametot2 = score2;
  }
//  /**                                    // v5 start rb 10/3/08
//   * Returns a student that has signed on additionally to the current student
//   * @return The student who has signed on additionally to the current student
//   */
//  public student getStudent2() {
//    int tot = sharkStartFrame.studentList.length;
//    short currstudent2;
//    if (sharkStartFrame.currStudent == tot-1) currstudent2 = 0;
//    else currstudent2 = (short)(sharkStartFrame.currStudent+1);
//    return sharkStartFrame.studentList[currstudent2];
//  }                                      // v5 end rb 10/3/08
  /**
   * Colours the component with the colour passed
   * @param comp Object to be coloured
   * @param c Colour to be used
   */
  void colorall(Component comp, Color c) {
      if(comp==null)return;
     Container parent = comp.getParent();
     parent.setBackground(c);
     for(short i=0;i<parent.getComponentCount();++i) {
        parent.getComponent(i).setBackground(c);
     }
  }
  /**
   * Changes the helpPanel to the one requested
   * @param s Indicates which help display is shown
   */
  public void help(String s) {
      currhelp = s;
      if (helpPanel != null) {
         helpPanel.show(s);
      }
   }
   /**
    * Creates a thick line from a polygon.
    * @param g Graphics object.
    * @param x1 coordinate for the polygon.
    * @param y1 coordinate for the polygon.
    * @param x2 coordinate for the polygon.
    * @param y2 coordinate for the polygon.
    * @param thick Thickness of line.
    */
   public static void thickline(Graphics g,int x1, int y1, int x2,int y2, int thick) {
      x1 = x1;
      x2 = x2;
      y1 = y1;
      y2 = y2;
      thick = Math.max(3,thick);
      double angle = Math.atan2(y2-y1,x2-x1) + Math.PI/2;
      int dx = (int)(thick * Math.cos(angle));
      int dy = (int)(thick * Math.sin(angle));
      g.fillPolygon(new int[] {x1-dx/2,x1+dx/2,x2+dx/2,x2-dx/2},
                    new int[] {y1-dy/2,y1+dy/2,y2+dy/2,y2-dy/2},4);
   }
   /**
    * @param delay how long to delay before picture shows
    */
   public void showpicture(int delay) {
      if(currword == null) return;
      javax.swing.Timer tt = new javax.swing.Timer(delay, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               new showpicture();
            }
      });
      tt.setRepeats(false);
      tt.start();
   }
//startSS^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
   /**
    * When a mover is zapped in a game this method is called to remove it from the
    * vector of movers for focussing
    * @param m Mover that no longer needs to be focused
    */
   public void resetSwitchAccess(mover m) {
     if (!pointSwitch2.isEmpty()) {
       for (int i = 0; i < pointSwitch2.size(); i++) {
         Point switchPoint = (Point) pointSwitch2.get(i);
         if ( (switchPoint.x <= m.x + m.w) //Find the mover passed in the vector of points
             && (switchPoint.x >= m.x)
             && (switchPoint.y <= m.y + m.h)
             && (switchPoint.y >= m.y)) {
           pointSwitch2.remove(i);
           currentPosition--;
         }
       }
     }
   }
//endSS^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
//startPR2004-11-28^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    /**
     * Only called in the demo version.
     *
     * Exits midway through the game.
     */
    public void doexit() {
       completed=true;
       javax.swing.Timer etimer = new javax.swing.Timer(1000,
           new ActionListener() {
             public void actionPerformed(ActionEvent e){
               windowclose();
             }
           }
           );
           etimer.setRepeats(false);
           etimer.start();
     }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2007-12-20^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        /**
         * Only called in the demo version.
         *
         * Exits midway through the game.
         */
        public void doexitsooner() {
          completed=true;
          javax.swing.Timer etimer = new javax.swing.Timer(220,
              new ActionListener() {
                public void actionPerformed(ActionEvent e){
                  windowclose();
                }
              }
              );
              etimer.setRepeats(false);
              etimer.start();
        }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2008-08-05^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        // describes a item in the options dialog that shouldn't be shown
        class removeoption {
          // the name of the option
          public String optionname;
          // if the option is subdivided this specifies which of the sub-options
          // should be hidden
          public String suboption;
          // if >= 0 set which sub-option should be selected when the options
          // dialog is loaded.
          public short selected = -1;
          removeoption(String optionnam, String subopt, short select){
            optionname = optionnam;
            suboption = subopt;
            selected = select;
          }
          removeoption(String optionnam, String subopt){
            optionname = optionnam;
            suboption = subopt;
          }
        }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   /**
      * <p>Title: WordShark</p>
      * <p>Description: Creates the button that appear at the end of games (used when
      * exiting to the main icons screen).</p>
      * <p>Copyright: Copyright (c) 2004</p>
      * <p>Company: WhiteSpace</p>
      * @author Roger Burton
      * @version 1.0
      */
     public class ebutton extends mover {
        Font f;
        FontMetrics m;
        String message = new String(u.gettext("g_home","label"));
        /**
         *
         */
        public ebutton(String text) {
          super(false);
           message = text;
           init();
        }

        public ebutton() {
          super(false);
          init();
        }

       void init(){
           ebutton = this;
            keepMoving = true;
           w = mover.WIDTH/6;
           h = mover.HEIGHT/8;
           endplaytime = System.currentTimeMillis();
        }
        /**
         * @param g Graphics object to be used
         * @param x X coordinate
         * @param y Y coordinate
         * @param w1 Width
         * @param h1 Height
         */
        public void paint(Graphics g,int x, int y, int w1, int h1) {
          if(clearexit) {
            g.setColor(Color.white);
            g.fillRect(x,y,w1,h1);
          }
          Rectangle r = new Rectangle(x,y,w1,h1);
          if(f==null) {
             f = sizeFont(null,g,message,w1*3/4,h1*3/4);
             m = g.getFontMetrics();
          }
          g.setFont(f);
          g.setColor(!clearexit && u.tooclose(background,Color.black)?Color.white:Color.black);
          x += w1/2;
          y += Math.max(0, h1/2 - m.getHeight()/2 + m.getMaxAscent());
          g.drawString(message, x - m.stringWidth(message)/2,y);
          u.buttonBorder(g,r,Color.red,!mouseDown);
          }
          /**
           * @param x x coordinate of click event
           * @param y y coordinate of click event
           */
          public void mouseClicked(int x, int y) {
             ended = false;
             u.pause(500);
             gamePanel.currgame.terminate();
          }
      }
      /**
       * <p>Title: WordShark</p>
       * <p>Description: Controls the timer for games as they are played</p>
       * <p>Copyright: Copyright (c) 2004</p>
       * <p>Company: WhiteSpace</p>
       * @author Roger Burton
       * @version 1.0
       */
      //--------------------------------------------------------------
      class showtime extends Thread {
         boolean stopit,stopped;
         public showtime()  {}
         public void run() {
            long lasttime,nexttime = startplaytime+500, time;
            while(true) {
               if(stopit || completed) {stopped=true;return;}
               if(!pausing) {
                 lasttime = sharkGame.gtime();
                 time = (lasttime-startplaytime) / 1000;
                 timerView.setText(String.valueOf(time / 600) + String.valueOf(time % 600 / 60) // rb 15/1/07
                                   + "." + String.valueOf(time % 60 / 10) + String.valueOf(time % 10));
                 if ( (nexttime += 1000) > lasttime)
                   u.pause( (int) (nexttime - lasttime));
               }
               else u.pause(300);
            }
         }
      }
   /**
    *
    * <p>Title: WordShark</p>
    * <p>Description: Displays the relevant picture for the word currently in use
    * in a game.</p>
    * <p>Copyright: Copyright (c) 2004</p>
    * <p>Company: WhiteSpace</p>
    * @author Roger Burton
    * @version 1.0
    */
   public class showpicture extends mover {
      sharkImage im1;
      long starttime;
      public long stopat;
      public boolean dontremove;
      Color color = Color.lightGray;
//startPR2008-06-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      MediaPanel video;
//endPRimagesandvids^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      /**
       * Displays the relevant picture for the word currently in use in a game
       */
      public showpicture() {
         super(true);
         if(init1(-1, -1, false)<0)return;
         if(gamePanel.mousex >=0){
           add();
         }
         else gamePanel.wantpic = this;
      }
      /**
       * Displays the relevant picture for the word currently in use in a game
       * @param xx x coordinate used to position picture.
       * @param yy y coordinate used to position picture.
       */
      public showpicture(int xx, int yy) {
         super(true);
         if(init1(-1, -1, false)<0)return;
         init2(xx, yy, false);
         init3();
      }


      public showpicture(int xx, int yy, boolean leftalign) {
         super(true);
         if(init1(-1, -1, false)<0)return;
         init2(xx, yy, leftalign);
         init3();
      }


      public showpicture(int xx, int yy,int ww,int hh) {
         super(true);
         if(init1(-1, -1, false)<0)return;
//startPR2008-06-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//         gamePanel.addMover(this,xx+ww/2-w/2,yy+hh/2-h/2);
         if(im1.isvideo){
           if(!MediaPanel.isPlayingVideo)
             video = new MediaPanel((Window)thisgame,
                                    new sharkImage(getURL(im1.name), im1.name),
                                    xx+ww/2,yy+hh/2,w,h,MediaPanel.ALIGNCENTER, gamePanel);
         }
         else{
           gamePanel.addMover(this,xx+ww/2-w/2,yy+hh/2-h/2);
         }
//endPRimagesandvids^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        init3();
      }

      public showpicture(int xx, int yy,int ww,int hh, boolean actualrect) {
         super(true);
         if(!actualrect){
             if(init1(-1, -1, false)<0)return;
             if(im1.isvideo){
               if(!MediaPanel.isPlayingVideo)
                 video = new MediaPanel((Window)thisgame,
                                        new sharkImage(getURL(im1.name), im1.name),
                                        xx+ww/2,yy+hh/2,w,h,MediaPanel.ALIGNCENTER, gamePanel);
             }
             else{
               gamePanel.addMover(this,xx,yy);
             }
         }
         else{
             if(init1(ww, hh, actualrect)<0)return;
             if(im1.isvideo){
               if(!MediaPanel.isPlayingVideo)
                 video = new MediaPanel((Window)thisgame,
                                        new sharkImage(getURL(im1.name), im1.name),
                                        xx,yy,ww,hh,MediaPanel.ALIGNCENTER, gamePanel);
             }
             else{
                 if(im1.tifimage!=null){
                     xx = xx+(ww-im1.w)/2;
                     yy = yy+(hh-im1.h)/2;
                 }
                 gamePanel.addMover(this,xx,yy);
             }
         }
         init3();
      }


      int init1(int ww,int hh, boolean actualrect){
//         if(specialprivate)forceshow = false;
         clearold();
         if(currword == null || gamePanel == null
            || !sharkStartFrame.studentList[sharkStartFrame.currStudent].wantpicsgames ) {
//startPR2008-06-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
           if(currshowpicture.video!=null){
             currshowpicture.video.stopall();
           }
//endPRimagesandvids^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
           currshowpicture = null;
           return -1;
         }
//        if(forceshow)
//             im1 = new sharkImage(currword.vpic(), false);
//         else
         if(DoImageScreenshots.forceImLibrary<0)
             im1 = sharkImage.find(currword.vpic());
         else
             im1 = sharkImage.find(currword.vpic(), DoImageScreenshots.forceImLibrary);

         if(im1 == null) {
//startPR2008-06-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
           if(currshowpicture.video!=null){
             currshowpicture.video.stopall();
           }
//endPRimagesandvids^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
           currshowpicture = null;
           return -1;
         }
//         im1.w = ww>=0||actualrect?ww:mover.WIDTH/4;
//         im1.h =  hh>=0||actualrect?hh:mover.HEIGHT/3;
//         if(!actualrect)
//            im1.adjustSize(screenwidth,screenheight);
         if(!actualrect){
             im1.w = ww>=0?ww:mover.WIDTH/4;
             im1.h =  hh>=0?hh:mover.HEIGHT/3;
             im1.adjustSize(screenwidth,screenheight);
         }
         else{
             if(im1.tifimage!=null){
                int ih = im1.tifimage.getHeight(null);
                int iw = im1.tifimage.getWidth(null);
                im1.h = hh;
                im1.w = ((iw*hh/ih)*screenheight/screenwidth);
                if(im1.w > ww){
                    im1.w = ww;
                    im1.h = ((ih*ww/iw)*screenwidth/screenheight);
                }
             }
             else{
                 im1.w = ww;
                 im1.h = hh;
             }
         }
         w = im1.w;
         h = im1.h;
         keepMoving=true;
         mouseOver = true;
         dontgrabmouse = true;
         return 0;
      }




      void init2(int xx,int yy, boolean leftalign){
//startPR2008-06-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         int xxx = xx;
         if(!leftalign)xxx = xx - w/2;
         if(im1.isvideo){
           if(!MediaPanel.isPlayingVideo)
             video = new MediaPanel((Window)thisgame,
                                    new sharkImage(getURL(im1.name), im1.name),
                                    xxx,yy,w,h,MediaPanel.ALIGNCENTER, gamePanel);
         }
         else{
           gamePanel.addMover(this,Math.max(0,Math.min(mover.WIDTH-w,xxx)),
                              Math.max(0,Math.min(mover.HEIGHT-h,yy - h/2)));
         }
//endPRimagesandvids^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      }

      void init3(){
         stopat = System.currentTimeMillis()+6000;
         starttime = System.currentTimeMillis();
      }

      /**
       * Removes the previously displayed picture.
       */
      public void clearold() {
//startPR2008-06-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//        if(currshowpicture != null && gamePanel.isMover(currshowpicture)) {
//    removeMover(currshowpicture);
        if(currshowpicture != null) {
          if(gamePanel!=null && gamePanel.isMover(currshowpicture))
            removeMover(currshowpicture);
          if(currshowpicture.video!=null){
            currshowpicture.video.stopall();
          }
//endPRimagesandvids^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         }
         currshowpicture = this;
      }
//startPR2008-06-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      URL getURL(String word){
        URL u = null;
        try {
          u = (new File(sharkStartFrame.vidPathPlus+word+MediaPanel.fileExt)).toURL();
        }
        catch (java.net.MalformedURLException m) {}
        return u;
      }
//endPRimagesandvids^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      /**
       * Displays the picture at the position defined by the variable pictureat.
       */
      void add() {
        if(pictureat > 0) {
             switch (pictureat) {
//startPR2008-06-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//              case 1: gamePanel.addMover(this,0,0);break;
//              case 2: gamePanel.addMover(this,mover.WIDTH-w,0);break;
//              case 3: gamePanel.addMover(this,0,mover.HEIGHT-h);break;
//              case 4: gamePanel.addMover(this,mover.WIDTH-w,mover.HEIGHT-h);break;
               case 1:
                 if(im1.isvideo){
                   if(!MediaPanel.isPlayingVideo)
                     video = new MediaPanel( (Window) thisgame,
                                             new sharkImage(getURL(im1.name) , im1.name),
                                             0, 0, w, h, MediaPanel.ALIGNLEFT, gamePanel);
                 }
                 else
                   gamePanel.addMover(this,0,0);
                 break;
               case 2:
                 if(im1.isvideo){
                   if(!MediaPanel.isPlayingVideo)
                     video = new MediaPanel( (Window) thisgame,
                                             new sharkImage(getURL(im1.name), im1.name),
                                             mover.WIDTH - w, 0, w, h, MediaPanel.ALIGNLEFT, gamePanel);
                 }
                 else
                   gamePanel.addMover(this,mover.WIDTH-w,0);
                 break;
               case 3:
                 if(im1.isvideo){
                   if(!MediaPanel.isPlayingVideo)
                     video = new MediaPanel( (Window) thisgame,
                                             new sharkImage(getURL(im1.name), im1.name),
                                             0, mover.HEIGHT - h, w, h, MediaPanel.ALIGNLEFT, gamePanel);
                 }
                 else
                   gamePanel.addMover(this,0,mover.HEIGHT-h);
                 break;
               case 4:
                 if(im1.isvideo){
                   if(!MediaPanel.isPlayingVideo)
                     video = new MediaPanel( (Window) thisgame,
                                             new sharkImage(getURL(im1.name), im1.name),
                                             mover.WIDTH - w, mover.HEIGHT - h, w, h, MediaPanel.ALIGNLEFT, gamePanel);
                 }
                 else
                   gamePanel.addMover(this,mover.WIDTH-w,mover.HEIGHT-h);break;
//endPRimagesandvids^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
             }
             stopat = System.currentTimeMillis()+8000;
         }
         else {
            int xx,yy;
            if(picturepoint != null) {
                xx = picturepoint.x;
                yy = picturepoint.y;
            }
            else {
                xx = gamePanel.mousex +  - w*3/4;
                yy = gamePanel.mousey - h/2;
            }
//startPR2008-06-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//            gamePanel.addMover(this,
//               Math.max(0,Math.min(mover.WIDTH-w, xx)),
//               Math.max(0,Math.min(mover.HEIGHT-h,yy)));
            if(this.im1.isvideo){
              if(!MediaPanel.isPlayingVideo)
                video = new MediaPanel((Window)thisgame,
                                       new sharkImage(getURL(im1.name), im1.name),
                                       xx,yy,w,h,MediaPanel.ALIGNLEFT, gamePanel);
            }
            else{
              gamePanel.addMover(this,
                                 Math.max(0,Math.min(mover.WIDTH-w, xx)),
                                 Math.max(0,Math.min(mover.HEIGHT-h,yy)));
            }
//endPRimagesandvids^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         }
         starttime = System.currentTimeMillis();
      }
      /**
       * Removes the picture when it is clicked on.
       * @param x X coordinate of click event.
       * @param y Y coordinate of click event.
       */
      public void mouseClicked(int x, int y) {
//startPR2008-06-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        if(video!=null)
          video.stopall();
        else
//endPRimagesandvids^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        if(dontremove) return;
        removeMover(this);
        currshowpicture = null;
      }
      /**
       * Paints the picture as long as it is appropriate.
       * @param g Graphics object
       * @param x X coordinate
       * @param y Y coordinate
       * @param w1 Width
       * @param h1 Height
       */
      public void paint(Graphics g,int x, int y, int w1, int h1) {
          if(!shark.doImageScreenshots){
            if(!dontremove && ((pictureat <= 0 && picturepoint == null
               && !mouseOver && System.currentTimeMillis()-starttime>3000  && (im1.list == null || im1.doneonepass)
                 || stopat != 0 && (im1.list==null && System.currentTimeMillis() > stopat
                                     || im1.list != null && im1.doneonepass) )
                 || gamePanel == null
                 || !gamePanel.showSprite && System.currentTimeMillis()-starttime>3000)) {
                if(video!=null)
                  video.stopall();
               this.kill = true;
               currshowpicture=null;
            }
            else {
               if(sharkStartFrame.studentList[sharkStartFrame.currStudent].picbg) {
                  g.setColor(color);
                  g.fillRect(x,y,w1,h1);
                  g.setColor(Color.black);
                  g.drawRect(x,y,w1-1,h1-1);
               }
               im1.paint(g,x,y,w1,h1);
            }
          }
          else{
                if(im1.done){        
                  if(video!=null)
                    video.stopall();

                 this.kill = true;
                 currshowpicture=null;
              }
              else {
                 if(sharkStartFrame.studentList[sharkStartFrame.currStudent].picbg) {
                    g.setColor(color);
                    g.fillRect(x,y,w1,h1);
                    g.setColor(Color.black);
                    g.drawRect(x,y,w1-1,h1-1);
                 }
                 im1.paint(g,x,y,w1,h1);
              }              
          }
      }
   }
   
   public class peepwordpan extends JPanel{
       String flash[];
       int w1;
       int h1;
      Color bgcol;
      boolean stuCustomColor;
       public peepwordpan(String s[], int w, int h, Color col, boolean stuCustCol){
           flash = s;
           w1 = w;
           h1 = h;
           bgcol = col;
           stuCustomColor = stuCustCol; 
       }
       
      public void paint(Graphics g) {
//startPR2008-09-25^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       super.paint(g);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2008-10-10^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
       Color c = bgcol;//getParent().getBackground();
       int w = getWidth();
       int h = getHeight();
       g.fillRect(0, 0, w, h);
       g.setColor(c.darker());
       int mar = w/16;
       g.fillRect(0, 0, w, h);
      g.setColor(c.brighter());
       g.setColor((stuCustomColor&&(!(getBackground().equals(c))))?c:u.lighter(c, 1.1f));
       g.fillRoundRect(mar/2, mar/2, w-mar, h-mar, mar, mar);
       g.setColor(Color.black);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2007-10-19^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    //   ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       g.setFont(sizeFont(g,flash,w1,h1));
       FontMetrics m = g.getFontMetrics();
       int ht = m.getHeight();
       int extray = m.getMaxAscent();
       for(short i=0;  i<flash.length; ++i) {
                     g.drawString(flash[i], w1/2 - m.stringWidth(flash[i])/2,
                                        h1/2 +h1/6 - ht*flash.length/2 + ht*i + extray);
       }
     }      
   }   
   
   /**
    * <p>Title: WordShark</p>
    * <p>Description: Responsible for creating and displaying a peep, for the currently
    * used word in a game.</p>
    * <p>Copyright: Copyright (c) 2004</p>
    * <p>Company: WhiteSpace</p>
    * @author Roger Burton
    * @version 1.0
    */
   public class peepword extends JWindow {
     int x1 = screenwidth/4, y1 = screenheight/4,w1 = screenwidth/2,h1 = screenheight/2;
     BorderLayout layout1 = new BorderLayout();
     String flash[] = null;
     String peepword ;
     javax.swing.Timer t1;
     Color bgcol;
     boolean stuCustomColor = false;
     /**
      * Displays the word currently in use in a game for a short time.
      * @param s String currently in use in the game.
      * @param peeptime Time for the peep to be displayed.
      * @param x X coordinate.
      * @param y Y coordinate.
      * @param w Width.
      * @param h Height.
      * @param fr Frame for the game being played
      */
  public peepword(String[] s, String title, int peeptime,
//startPR2004-08-27^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    // JDialog was JFrame
    int x,int y, int w, int h, JDialog fr) {
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       super(fr);
       x1=x*screenwidth/mover.WIDTH;
       y1=y*screenheight/mover.HEIGHT;
       w1=w*screenwidth/mover.WIDTH;
       h1=h*screenheight/mover.HEIGHT;
       initp(s,title,peeptime);
       currpeep = this;

     }
     /**
      * Displays the word that is currently in use for a specified period of time.
      * @param s Word to be displayed.
      * @param title Title
      * @param peeptime Period of time the word is to be displayed for
      * @param fr The game's frame.
      */
//startPR2004-08-27^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      // JDialog was JFrame
       public peepword(String[] s, String title, int peeptime, JDialog fr) {
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       super(fr);
       initp(s,title,peeptime);
     }
     /**
      * Displays the word passed for the required time.
      * @param s Word to be displayed.
      * @param peeptime Time for the word to be displayed
      */
     void initp(String[] s, String title, int peeptime) {
       setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
       flash = s;
       if(ChangeScreenSize_base.isActive){
            setBounds(u2_base.adjustBounds(new Rectangle(x1, (gamePanel.getLocationOnScreen().y-(sharkStartFrame.mainFrame.getLocationOnScreen().y+
               sharkStartFrame.mainFrame.getInsets().top))+y1,w1,h1*7/6)));
       }
       else{
        setBounds(new Rectangle(x1, gamePanel.getLocationOnScreen().y+y1,w1,h1*7/6));
       }
       String ss = student.optionstring("bgcolor");
       stuCustomColor = (ss != null);
       bgcol = wordlist.bgcoloruse;
 /////      if(stuCustomColor)
 //        bgcol= u.backgroundcolor(null);
 //      else
//         bgcol= getBackground().brighter();
//       setBackground(bgcol);
       JPanel jp = new peepwordpan(flash, w1, h1, bgcol, stuCustomColor);
       this.add(jp);
       jp.setBackground(bgcol);
       this.addWindowListener(new java.awt.event.WindowAdapter() {
          public void windowClosed(WindowEvent e) {
//startPR2005-10-16^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            if(shark.macOS && gamePanel!=null)
              gamePanel.refreshat = System.currentTimeMillis() + 800;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            currpeep=null;
          }
       });
       this.setEnabled(true);
       setVisible(true);
       validate();
       t1 = new javax.swing.Timer(peeptime,
          new ActionListener() {
             public void actionPerformed(ActionEvent e){
                 dispose();
            }
          }
       );
       t1.setRepeats(false);
       t1.start();
//startPR2008-10-10^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       repaint();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     }
     /**
      * @param g Graphics object
      */
     /*
     public void paint(Graphics g) {
//startPR2008-09-25^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       super.paint(g);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2008-10-10^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
       Color c = bgcol;//getParent().getBackground();
       int w = getWidth();
       int h = getHeight();
       g.fillRect(0, 0, w, h);
       g.setColor(c.darker());
       int mar = w/16;
       g.fillRect(0, 0, w, h);
//       g.setColor(c.brighter());
       g.setColor((stuCustomColor&&(!(getBackground().equals(c))))?c:u.lighter(c, 1.1f));
       g.fillRoundRect(mar/2, mar/2, w-mar, h-mar, mar, mar);
       g.setColor(Color.black);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2007-10-19^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       g.setFont(sizeFont(g,flash,w1,h1));
       FontMetrics m = g.getFontMetrics();
       int ht = m.getHeight();
       int extray = m.getMaxAscent();
       for(short i=0;  i<flash.length; ++i) {
                     g.drawString(flash[i], w1/2 - m.stringWidth(flash[i])/2,
                                        h1/2 +h1/6 - ht*flash.length/2 + ht*i + extray);
       }
     }
     */
   }
   /**
    * <p>Title: WordShark</p>
    * <p>Description: Produces buttons for the top panel of a game.</p>
    * <p>Copyright: Copyright (c) 2004</p>
    * <p>Company: WhiteSpace</p>
    * @author Roger Burton
    * @version 1.0
    */
   public static class topbutton extends JButton {
     /**
      * @param text Text to go on the Button
      */
     public topbutton(String text) {super(text);}
     /**
      * Creates a button with initial text and an icon.
      * @param text The text of the button.
      * @param im The icon image to be displayed on the button.
      */
     public topbutton(String text,ImageIcon im) {super(text,im);}
     /**
      * @return false
      */
       public boolean isFocusable() {return false;}
}

   public class sharedplay extends JPanel {
     JLabel title1;
     JLabel title2;
     JPanel pan1;
     JPanel pan2;
     boolean scoring;
     String nam1;
     String nam2;
     String textscore1 = "<html>%<br>"+u.gettext("sharedplay", "score")+"%</html>";
     String textnoscore1 = "<html>%</html>";
     String textscore2 = textscore1;
     String textnoscore2 = textnoscore1;
        JPanel innerpan1 = new JPanel(new GridBagLayout());
        JPanel innerpan2 = new JPanel(new GridBagLayout());
        Color defbackg = forceSharedColor?runMovers.tooltipbg:background;


     public sharedplay(String name1, String name2, boolean isscoring) {
        super();
        scoring = isscoring;
        nam1 = name1;
        nam2 = name2;
        if(scoring){
            textscore1 = textscore1.replaceFirst("%", nam1);
            textscore2 = textscore2.replaceFirst("%", nam2);
            title1 = new JLabel(textscore1.replaceFirst("%", "0"));
            title2 = new JLabel(textscore2.replaceFirst("%", "0"));
        }
        else{
            textnoscore1 = textnoscore1.replaceFirst("%", nam1);
            textnoscore2 = textnoscore2.replaceFirst("%", nam2);
            title1 = new JLabel(textnoscore1);
            title2 = new JLabel(textnoscore2);
        }
        this.setLayout(new GridBagLayout());
 //       this.setBackground(sh);
        this.setOpaque(true);
        byte buf[];
        Image image1 = null;
        Image image2 = null;
        int count = 1;
        MediaTracker tracker = new MediaTracker(this);
        if ( (buf = (byte[]) db.find(name1, PickPicture.ownpic, db.PICTURE)) != null) {
            image1 = sharkStartFrame.t.createImage(buf);
            tracker.addImage(image1,count);
            count++;
        }
        else {
            String ss = "signonAdminLight_il96.png";
            student stu1 = student.findStudent(name1);
            if(stu1!=null && !stu1.administrator) ss = "signonStudLight_il96.png";
            image1 = sharkStartFrame.t.createImage(sharkStartFrame.publicPathplus + "sprites" +
            File.separator + ss);
            tracker.addImage(image1,count);
            count++;
        }
        if(nam2.equals(u.gettext("game_", "computer"))){
            image2 = sharkStartFrame.t.createImage(sharkStartFrame.publicPathplus + "sprites" +
            File.separator + "computer_il96.png");
            tracker.addImage(image2,count);
            count++;
        }
        else if ( (buf = (byte[]) db.find(name2, PickPicture.ownpic, db.PICTURE)) != null) {
            image2 = sharkStartFrame.t.createImage(buf);
            tracker.addImage(image2,count);
            count++;
        }
        else {
            String ss = "signonAdminLight_il96.png";
            student stu1 = student.findStudent(name2);
            if(stu1!=null && !stu1.administrator) ss = "signonStudLight_il96.png";
            image2 = sharkStartFrame.t.createImage(sharkStartFrame.publicPathplus + "sprites" +
            File.separator + ss);
            tracker.addImage(image2,count);
        }
        
        try {tracker.waitForAll();}
        catch (InterruptedException ie){}


        int ih = image1.getHeight(null);
        int iw = image1.getWidth(null);
        int iih, iiw;
        int sq = sharkStartFrame.screenSize.height/20;
        if(ih>iw){
            iih = sq;
            iiw = sq*iw/ih;
        }
        else{
            iiw = sq;
            iih = sq*ih/iw;
        }
        title1.setIcon(new ImageIcon(image1.getScaledInstance(
            iiw,
            iih,
            Image.SCALE_SMOOTH)));
        title1.setFont(sharkStartFrame.treefont);
        if(image2!=null){
        ih = image2.getHeight(null);
        iw = image2.getWidth(null);
        if(ih>iw){
            iih = sq;
            iiw = sq*iw/ih;
        }
        else{
            iiw = sq;
            iih = sq*ih/iw;
        }
        title2.setIcon(new ImageIcon(image2.getScaledInstance(
            iiw,
            iih,
            Image.SCALE_SMOOTH)));
        title2.setFont(sharkStartFrame.treefont);
        }

        pan1 = new JPanel(new GridBagLayout());
        pan2 = new JPanel(new GridBagLayout());

        pan1.setPreferredSize(new Dimension((int)sharkStartFrame.mainFrame.getWidth()/5,
            sq));
        pan2.setPreferredSize(new Dimension((int)sharkStartFrame.mainFrame.getWidth()/5,
            sq));

            innerpan2.setOpaque(true);
            innerpan2.setOpaque(true);


    switchuser(1, true);
        GridBagConstraints grid = new GridBagConstraints();
        grid.fill = GridBagConstraints.BOTH;
        grid.weightx = 1;
        grid.weighty = 1;
        grid.gridx = -1;
        grid.gridy = 0;

 //       int xgap = 10;
  //      int ygap = 5;

 //       grid.insets = new Insets(0,xgap,0,xgap);
         grid.insets = new Insets(10,10,10,10);
         grid.fill = GridBagConstraints.BOTH;
        pan1.add(title1, grid);
        pan2.add(title2, grid);
   //      grid.insets = new Insets(0,0,0,0);
 //       grid.insets = new Insets(10,10,10,10);
    //    grid.fill = GridBagConstraints.BOTH;
   //     pan1.add(innerpan1, grid);
   //     pan2.add(innerpan2, grid);
   //     grid.fill = GridBagConstraints.BOTH;
   //     grid.insets = new Insets(ygap,xgap,ygap,xgap);
        grid.insets = new Insets(0,0,0,0);
        add(pan1, grid);
    //    grid.insets = new Insets(ygap,0,ygap,xgap);
        add(pan2, grid);

 //       innerpan2.setBackground(Color.yellow);
        innerpan2.setOpaque(true);
//        innerpan1.setBackground(Color.orange);
        innerpan1.setOpaque(true);
//        pan2.setBackground(Color.red);
        pan2.setOpaque(true);
//        pan1.setBackground(Color.green);
        pan1.setOpaque(true);

        title1.setOpaque(true);
        title2.setOpaque(true);
 //       this.setBackground(Color.pink);
     }

     public void setuser1score(int i){
        if(scoring){
            title1.setText(textscore1.replaceFirst("%", String.valueOf(i)));
        }
     }

     public void setuser2score(int i){
        if(scoring){
            title2.setText(textscore2.replaceFirst("%", String.valueOf(i)));
        }
     }

     public void switchuser(int i, boolean disable) {
         if(sharednoturns){
            pan2.setBackground(Color.darkGray);
            pan1.setBackground(Color.darkGray);
            title1.setBackground(defbackg);
            title2.setBackground(defbackg);
         }
         else if(i == 1)
         {
            pan1.setBackground(Color.darkGray);
            pan2.setBackground(sharkStartFrame.defaultbg);
            title2.setBackground(sharkStartFrame.defaultbg);
            title1.setBackground(defbackg);
            title1.setEnabled(true);
            title1.setForeground(Color.black);
            if(disable){
                title2.setEnabled(false);
                title2.setForeground(Color.gray);
            }
         }
         else if (i==2){
            pan2.setBackground(Color.darkGray);
            pan1.setBackground(sharkStartFrame.defaultbg);
            title1.setBackground(sharkStartFrame.defaultbg);
            title2.setBackground(defbackg);
            if(disable){
                title1.setForeground(Color.gray);
                title1.setEnabled(false);
            }
            title2.setEnabled(true);
            title2.setForeground(Color.black);
         }
         else{
            pan2.setBackground(sharkStartFrame.defaultbg);
            if(disable){
                title2.setEnabled(false);
                title2.setForeground(Color.gray);
            }
            pan1.setBackground(sharkStartFrame.defaultbg);
            title1.setBackground(sharkStartFrame.defaultbg);
            title2.setBackground(sharkStartFrame.defaultbg);
            if(disable){
                title1.setEnabled(false);
                title1.setForeground(Color.gray);
            }
         }
     }

     public boolean isFocusable() {return false;}
    }



     /**
      *
      * <p>Title: WordShark</p>
      * <p>Description: Used to display a message</p>
      * <p>Copyright: Copyright (c) 2004</p>
      * <p>Company: WhiteSpace</p>
      * @author Roger Burton
      * @version 1.0
      */
     static class messmover extends mover {
      String message;
      Color color = Color.black;
      int oldw;
      Font f;
      FontMetrics m;
      /**
       * @param mess Message to be displayed.
       */
      public messmover(String mess) {
         super(false);
         message = mess;
         w = mover.WIDTH;
         h = mover.HEIGHT/8;
      }
      /**
       * @param mess Message to be displayed.
       * @param colorx Colour to use for display.
       */
      public messmover(String mess,Color colorx) {
         super(false);
         color=colorx;
         message = mess;
         w = mover.WIDTH;
         h = mover.HEIGHT/8;
      }
      /**
       * @param g Graphics object
       * @param x X coordinate.
       * @param y Y coordinate.
       * @param w1 Width.
       * @param h1 Height.
       */
      public void paint(Graphics g,int x, int y, int w1, int h1) {
       if(f==null || oldw != w1) {
          oldw = w1;
          f = sizeFont(null,g,message,w1,h1);
          m = g.getFontMetrics(f);
       }
       g.setFont(f);
       g.setColor(color);
       x += w1/2;
       y += Math.max(0, h1/2 - m.getHeight()/2 + m.getMaxAscent());
       g.drawString(message, x - m.stringWidth(message)/2,y);
      }
  }
  public word[]  clearphonics(word w[]) {   // clean up a phonics word if phonics not wanted
    if (sharkStartFrame.currPlayTopic.phonicsw && !wordlist.usephonics) {
      int i, j;
      word ret[] = new word[w.length];
      String rest = "";
      for (i = 0; i < w.length; ++i) {
        ret[i] = new word(w[i]);
        if ( (j = w[i].value.indexOf('@')) >= 0)   rest = ret[i].value.substring(j);
        if ( (j = w[i].value.indexOf('=')) >= 0)   ret[i].value = u.strip(ret[i].value.substring(0, j),u.phonicsplit) + rest;
      }
      return ret;
    }
    return w;
  }
}
