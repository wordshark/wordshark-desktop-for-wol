
package shark;
import java.io.*;
import java.util.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.Timer;
import javax.swing.event.*;
import javax.swing.tree.*;
import java.util.regex.*;
import static shark.db.lastPublicTopicsSave;
/**
 * <p>Title: WordShark</p>
 * <p>Description: <li>Sets up menus, frames and panels on entry to the program
 * <li>Contains listener classes providing specific functionality when a menunew item selected
 * using sharkStartFrame methods.</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: WhiteSpace</p>tools
 * @author Roger Burton
 * @version 1.0
 */


public class sharkStartFrame extends JFrame {
//startPR2007-11-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  static String okrewards[];
  static String okrewards_flip[];
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  /**
   * Used when the cursor should not be seen
   */
  public static Cursor nullcursor;
//startPR2005-10-16^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  public static Cursor macnullcursor;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  boolean gotdate;
  Timer signTimer;
  JMenuItem currkeypad;
  BorderLayout borderLayout1 = new BorderLayout();
  BorderLayout borderLayout4 = new BorderLayout();
  BorderLayout borderLayout5 = new BorderLayout();
  BorderLayout borderLayout6 = new BorderLayout();

  JMenuBar manBar1 = new JMenuBar();
  JMenu meuFile;
  JMenuItem menuFileExit;
  JMenu features;
  JMenu actions;
  JMenu advanced;
//  JMenu escapemenu;
  JMenuItem signlist;
//  JCheckBoxMenuItem moverride;
//  JCheckBoxMenuItem mwanthello;
//  JCheckBoxMenuItem mwantshapes;      // rb 27-11-07
  JCheckBoxMenuItem mnotwantonsetrime;   // rb 27-11-07
  JMenu studentmenu;
  JMenu helpmenu;
  JCheckBoxMenuItem mwanthelp;
  String wantHelpText_signon;
  String wantHelpText;
  JMenu managestudent;
  JMenuItem memory;
  JMenuItem mshowshared;
  JMenuItem mzipshared;
  JMenu mzipsharednet;
  JMenuItem mzipsharednet_anon;
  JMenuItem mzipsharednet_withstus;

  JCheckBoxMenuItem refreshsound;
//startPR2008-10-12^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//  JCheckBoxMenuItem antialias;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  JCheckBoxMenuItem splitdraw;
  JCheckBoxMenuItem bgcolor, mkeypads[];
//  JMenu topics;
  JMenu msearch;
//startPR2007-10-10^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//  JMenuItem chooseFont;
//  JMenuItem chooseFontInfant;
//  JCheckBoxMenuItem chooseFont2;            // for students
//  JMenuItem defaultfont;
//  JCheckBoxMenuItem defaultfont2;           // for students
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  JMenuItem mownwords;
//  JMenuItem easywordlist;
//  JMenuItem easyrecord;
//startPR2004-10-22^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//  JCheckBoxMenuItem mnogamble;
//  JCheckBoxMenuItem mnoreward, mnomove;
  JCheckBoxMenuItem mnomove;
  JCheckBoxMenuItem mnokeypad;     // rb 27/10/06
  JMenuItem msetrewards;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//  JMenuItem defrecord;
//  JMenuItem defrecord2;
  JMenuItem sentrecord;
  JMenuItem sentrecord2;
  JMenuItem sentrecord3;
  JMenuItem sentrecord4;
//startPR2010-05-28^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//  JMenuItem sentrecord4;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  JMenuItem returnnormal;
//  JMenuItem mtopictree;
//  JMenuItem mgameicons;
  JMenuItem msearchi;
//startPR2008-06-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//  JMenuItem mPickPicture;
//endPRimagesandvids^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  JMenuItem messagesi;
  JMenuItem menuItem1;
  JMenu mtwoplayers;     // v5 rb 7/3/08
  String twoplayertext;              // v5
  JMenuItem signoff;
//  JMenuItem madmin;
  JMenuItem madminlist;
  JMenuItem mversion;
  JMenuItem merror;
  JMenuItem mBackup;
//  JMenuItem msturec;
  JMenuItem mstuchek;
  JMenuItem mChangeScreenSize;
//  JMenu picmenu;
  JMenuItem picpref;
//startPR2008-06-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//  JCheckBoxMenuItem mwantsignvids;
//endPRimagesandvids^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//  JCheckBoxMenuItem mwantsigns;
//  JCheckBoxMenuItem mwantrealpics;
//  JCheckBoxMenuItem mwantfingers;
//  JCheckBoxMenuItem mpicbg;
  JMenuItem morenoise;
//  JCheckBoxMenuItem nogroan;
//startPR2007-10-10^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//  JMenuItem mtreefont;
//  JCheckBoxMenuItem mtreefont2;
//  JMenuItem notreefont;
//  JCheckBoxMenuItem notreefont2;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  public JMenuItem PublicTopics;
  JMenuItem changegames;
  JMenuItem changetext,copysay;
  JMenuItem mkeypad;
  JMenu mchoosekeypad;
  // start rb 23/10/06
  JMenu mchooseteacher;
  JMenu men1;
  JMenu mplay;
  JMenuItem play;
  JMenuItem cut;
  JMenuItem copy;
  JMenuItem paste;
  JMenuItem find;
  JMenuItem findnext;
  JMenuItem pasteimage;
  JMenuItem save;
  JMenuItem muserlist;
  JMenuItem undo;
  JMenuItem imageinfo;
  JMenuItem record;
  JMenuItem recordfl;
  JMenuItem choosesprite;
  JMenuItem chooseicon;
  JMenuItem changepassword;
  JMenuItem studentrecord;
  JMenuItem addstudent;
  JMenuItem addstudent2;
  JMenuItem universal;
//startPR2010-03-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  JMenu mvideotutorial;
  JMenuItem mvidaddstu;
  JMenuItem mvidchooselist;
//  JMenuItem mvidcoursehelp;
  JMenuItem mvidchoosegame;
  JMenuItem mvidsearch;
  JMenuItem mvidprivatelist;
  JMenuItem mvidphonics;
  JMenuItem mvidgettingstarted;
//  JMenuItem mvidoptions;
  JMenuItem mvidsetwork;
  JMenuItem mvidtrackingprogress;
//endPRtoupdate^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  JMenu restrictcourses;
//  JMenu mspellchange;
//startPR2010-05-04^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//  JMenu msignon;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//  JMenu mfontchange;
//  JMenu mactivedirectory;
//startPR2007-11-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//  JMenu mfontchange2;           // for students
  JMenuItem mfontchange2;           // for students
  JMenuItem fontStandardGames_change;
//  JMenuItem fontMenus_change;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//  JCheckBoxMenuItem mnotemp;
//startPR2010-05-04^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//   JCheckBoxMenuItem msideprompt;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2008-09-21^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//  JCheckBoxMenuItem enforceprogram;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//  JCheckBoxMenuItem mautosignon;
//  JCheckBoxMenuItem mautosigncreatestu;
//  JMenuItem mdefcourse;
//  JMenu maxsametopic;          // v5 start rb 7/3/08
//  static int maxsamenum[];
//  static String maxsametext[];
//  static String maxsametooltip;
//  JCheckBoxMenuItem  maxsame[];   // v5 end rb 7/3/08
//startPR2004-10-22^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//  JCheckBoxMenuItem mpeep;
//  JCheckBoxMenuItem mrewards[];
//  JCheckBoxMenuItem mrewardfreqs[];
//  JMenu mrewardfreq;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  JCheckBoxMenuItem mcourses[];
///  JCheckBoxMenuItem mspellchanges[];
  JMenuItem print;
  JMenu printlist;
  JMenuItem mprintflash;
  JMenuItem mprintflash2;
  JMenuItem mprint1;
  JMenuItem mprint2;
  JMenuItem mprint4;
  JMenuItem mprint8;
//startPR2007-02-16^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  JMenuItem mprintex;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  JMenuItem mprintfg;
  JMenuItem mexplore;
  JMenuItem mexplore2;
  JMenuItem mmusic;
//startPR2008-01-23^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//  JMenuItem mglossary;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  JMenuItem msupport;
  JMenuItem findreplace;
  JMenuItem importsentences;
  JMenuItem mergetext;
  JMenuItem mergesay;
  JMenuItem mergesent1;
  JMenuItem mergesent2;
  JMenuItem mergeimage;
  JMenuItem mergeimage23;
//startPR2007-11-19^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  JMenuItem mergemultimage;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  JMenuItem mextrasoundpatches;
  JMenuItem newdb;
  JMenuItem mergetopics;
  JMenuItem pictures;
  JMenuItem mdistribute;
  JMenuItem compresspublic;
  JMenuItem netkeyup;
//startPR2008-08-14^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  JMenuItem acl;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2006-08-19^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  JMenuItem sharedloc;
//startPR2009-07-14^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//  JMenuItem runlocally;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//  JMenu development;
//  JMenu wordlistcolour;
//  JMenu cooperativeplay;
//  static boolean nowholegame = false;
//startSS^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
  /**
   * Menu choice for switch access settings
   */
//  JMenuItem mswitchaccess;
//endSS^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
  JButton prevlist;
  JButton nextlist;
  XrButton_base switchtogames;
  /**
   * Used to contain the main panels e.g. the sharks at the start up. The icon screen
   */
  Container bevelPanel1 = getContentPane(); //Has bevelPanel2 added to it
  JPanel bevelPanel2 = new JPanel(); //Has bevelPanel6 and bevelPanel3 added to it.
  /**
   * Displays the topic options, games and courses.
   */
  JPanel bevelPanel3 = new JPanel();
  JPanel bevelPanel4 = new JPanel();
  JPanel bevelPanel5 = new JPanel();
//startPR2010-05-24^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//  JPanel showallgamespanel = new JPanel(new GridBagLayout());
  JPanel bevelPanelCourses = new JPanel(new GridBagLayout());
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  /**
   * Contains Topic
   */
  JPanel bevelPanel6 = new JPanel();
  JPanel vpanel2 = new JPanel();
  JPanel vpanel3 = new JPanel();
//  wordlist.easyword easywordlistpanel;
  program programpanel;
  Font originalFont;
  static String extracourses,definitions;
  static String notactive2;
//  static String switchto;
    // special for mergeimage publicimagew into publicimage2
  static String[] imageNew, imageChanged;
//StartSS^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
  /**
   * Indicates the number of switches in use(1,2 or none).
   */
  public static short switchOptions;
  /**
   * Gives response time chosen by user using one switch.
   */
  public static short switchResponse;
//endSS^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
  static program.saveprogram playprogram;
  static String playprogramname;
  /**
   * When true the student is using a restricted set of word lists set up by
   * an administrator. When false they have access to all lists.
   */
  static boolean wantplayprogram;
  public static sharkStartFrame mainFrame;
  public static File publicPath;
  public static File sharedPath;
  public static String[] publicSoundLib;
  public static String[] publicSoundLibExtra;
  public static String[] publicDefLib;
  public static String[] publicSentLib;
  public static String[] publicSentLibExtra;
  public static String[] publicSent2Lib;
  public static String[] publicSent2LibExtra;
  public static String[] publicSent3Lib;
  public static String[] publicSent3LibExtra;
  public static String[] publicSent4Lib;
//startPR2010-05-28^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//  public static String[] publicSent4Lib;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  public static String[] publicImageLib;
  public static String[] publicSignLib;
  public static String[] publicFingerLib;
//startPR2008-06-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  public static String[] publicSignVidsNames;
  public static String vidPathPlus;
//endPRimagesandvids^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  public static String[][] publicLangKeypads;
  public static String publicWidgitLib;
  public static String[] publicPhotoNames;
  public static String[] publicPhotoNamesPlusExt;
  public static String publicPhotoNamesFolder;

  /**
   * These are viewed/changed from within the program using the "add and change game
   * details and game trees option". There are two parts
       * <li>The first is the game - this is a text string giving the parameters e.g.
   * "ID", "title" etc.
       * <li>The second is the hierarchy - a topic tree. This shows the games position
   * within the game tree.
   */
  public static String[] publicGameLib;
  public static String[] publicTextLib;
  public static String[] publicTopicLib;
  public static String[] publicSplitsLib;
  public static String publicAvatarLib; 
  public static String publicTestSayLib;
  public static String publicV4TopicsLib;
  public static String publicMarkGamesLib;
  public static String publicMarkGamesFormulaLib;
  public static String[] publicMarkGamesCoursesLib;
  public static String publicKeypadLib;
  public static String[] publicExtraCourseLib;
  public static Image sharkicon;
  /**
   * Contains the local system's path separator
   */
  public static char separator;
  public static String publicPathplus;
  public static String sharedPathplus;
  public static short currStudent = -1;
  public static student[] studentList;
  /**
   * Number of games running
   */
  static short gametot;
  public topicTree topicTreeList;
  recordWords recording;
  topic currTopicView;
  JButton btExcludeWordFromAPTest;
  JComboBox markGamesCodeCombo;
  /**
   * Holds topic currently displayed
   */
  topic currTopicView2;
  saveTree1 topicclipboard[];
  /**
   * Refers to topic currently in use. (Used as reference when a game is in progress_base)
   */
  public static topic currPlayTopic;
  public static String currCourse;
  static final int BASICFONTPOINTS = 16;
  static final int BASICFONTPOINTSX = 80;
  
  public static int MAXFONTPOINTS_1;     // WAS 30
  public static int MAXFONTPOINTS_2;     // WAS 40 - keep distiction Roger put in - a different maxFont used in different places.
  public static int MAXFONTPOINTS_3; 
  public static int MAXFONTPOINTS_4; 
  
  static Toolkit t = Toolkit.getDefaultToolkit();

  public static Dimension screendim = t.getScreenSize();
//  public static Dimension screendim = null;

  public static Font wordfont, treefont;
  public static FontMetrics wordfontm,treefontm;         // rb 7/5/08
  public static int wordfontheight;
  /**
   * Topic options are showing
   */
  boolean showingOptions;
  boolean showingGames;
  boolean showingCourses;
//startPR2009-10-10^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  boolean showingMgHeadings;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  boolean showingMgFormula;
  boolean showingMgCourse;
  /**
   * Indicates whether games are playable.
   */
  boolean playingGames;
  /**
   * True if help is wanted
   */
  public static boolean wanthelp;
  boolean copyfrommiddle, useselectedlist;
  /**
   * True when changes are being made to menus etc
   */
  boolean forcechange;
  /**
   * True when "Hello" is to be said at start of program run
   */
  static boolean wanthello;
//  static public boolean wantshapes;              // rb 27-11-07
  static public boolean notwantonsetrime;           // rb 27-11-07
  static String starttitle;  // wordshark+version
  static String starttitlespace = "    -    ";
  /**
   * Contains - "WORDSHARK 3 - Full list of topics + current word list + list of games"
   */
  static String hometitle1;
  /**
   * Contains - "WORDSHARK 3 -icons for playing games + Current word list"
   */
  static String hometitle2;
  static String homeusing;
  String pictitle, topicstitle, gamestitle, texttitle, saytitle, recwordstitle,
      recsenttitle,
      recdeftitle, recprivatewordstitle, privatetopicstitle, addstutitle;
  String mhome1;
  /**
   * Contains - "Games (F12)"
   */
  String mhome2;
  String signofftext;
  static String optionsdb, courses[];
//startPR2006-04-30^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  static String plistRec;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  static String licence, licence1, oldli,oldli1, school, serial;
  static int users = 0;
  String courselist[];
  String publicCourses[];
//startPR2007-11-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//  static String okrewards[];
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  static String extraflash;
  /**
   * Indicates whether pictures are shown when word is spoken.
   */
  boolean showingPictures;
  /**
   * Indicates whether games are currently being updated.
   */
  boolean updatingGames;
  boolean updatingTopics;
  /**
   * Indicates whether public text is currently being updated.
   */
  boolean updatingText;
  static boolean newuser;
  sharkImage currPicture, picclip[];
  wordlist.wordpicture currPhoto;
  final int CURRPHOTOPOS_NW = 0;
  final int CURRPHOTOPOS_NE = 1;
  final int CURRPHOTOPOS_SE = 2;
  final int CURRPHOTOPOS_SW = 3;
  int currPhotoPos = CURRPHOTOPOS_NW;

  JList picChoice;
  sharklist picControl;
  runMovers picShow;
  /**
   * Time the last game started
   */
  long lastgamestart;

//startPR2010-05-24^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  Cursor handcursor = new Cursor(Cursor.HAND_CURSOR);
  uEditorPane courseinfo = new uEditorPane(new Insets(10,10,10,10));
  uEditorPane topicinfo = new uEditorPane(new Insets(10,10,10,10));
  public JButton btTopicTest;
  String switchtogamesg, switchtogamest;

//  static Color topictreecolor = new Color(64, 255, 255);
  static Color topictreecolor = Color.orange;
  static Color wordlistcolor = Color.gray;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  static Color cream = new Color(255, 255, 204);
  static Color teachbgcolor = new Color(234, 234, 255);  

//  static Color textbgcolor = new Color(255, 255, 204);
  static Color textbgcolor = new Color(234, 234, 255);


//  static Color textfgcolor = new Color(6, 6, 50);
  static Color textfgcolor = Color.black;
  static Color gamescolor = Color.blue;
  static Color defaultbg;
  static Color col1=new Color(191,191,223);
  static Color col2=new Color(223,223,255);
  /**
   * Label that gives teachers information about the word list being used
   */
  static u.shownotes showteachingnotes;
  static long starttime = System.currentTimeMillis();
  /**
   * Value of reward given to a student in a reward game
   */
  public static short reward;
  Font titfont[] = new Font[1], headfont[] = new Font[1];
  public static Rectangle originalbounds;
  /**
   * True when the game icon screen is displayed
   */
  boolean gameicons;
  /**
   * Panel that contains the game icons for games available
   */
  runMoversgame gamePanel;
  Rectangle gamerect[],tabrect[];
  Rectangle pagerect;
  String gametooltip[];
  /**
   * game icons
   */
  static sharkImage gameiconlist[];
  /**
   * Names to go under icon
   */
//  static String gametitle[];
  boolean resetGamesScreen = true;
  byte phtype;   // 0=normal,1=phonics sounds,2=phonics words
  /**
   * Names of games
   */
  static String gamename[];
  static gameflag gameflags[];
  /**
   * Heading no
   */
//  short gamehdno[];
  /**
   * Game is active
   */
  boolean gameactive[];
  
  String gameheadings[];
//  Rectangle gamesurround[];
  int gametextdepth, gameheadwidth,rowdepth;
  /**
   * Path for topicTreeList
   */
  String updatepath;
  /**
   * Path for topicList
   */
  String refpath;
  public static int mouseonscreenx, mouseonscreeny;
  boolean showingStudentRecord;
  public sharkTree lastSelect, lastSelectForCopy;
  public static Dimension screenSize = t.getScreenSize();
  /**
   * Panel with fish swimming on it that shows when WordShark initially starts.
   */
  runMovers sharkPanel;
  // constraints for: update topic trees / topic trees / topics
  // up to 3 vertical columns
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  GridBagLayout vgridBagLayout2 = new GridBagLayout();
  GridBagLayout gridBagLayout2u = new GridBagLayout();
  GridBagLayout vgridBagLayout3 = new GridBagLayout();
  GridBagConstraints grid1 = new GridBagConstraints();
  // constraints for: topic  and  either topic keywords or game tree
  // one block above the other
  GridBagLayout gridBagLayout2 = new GridBagLayout();
  GridBagLayout gridBagLayout3 = new GridBagLayout();
  GridBagConstraints grid2 = new GridBagConstraints();
  GridBagConstraints grid3 = new GridBagConstraints();
  topicTree topicList;
//startPR2010-05-24^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  topicTree courseList;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  JList topicOptions = new JList();
  JList publicCourseList = new JList();
  gamestoplay gameTree;
  public gamestoplay publicGameTree;
//startPR2009-10-10^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  topicTree markgamestree;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  topicTree markgamesformulatree;
  topicTree markgamescoursetree;
  static String gametreeheadings[];
  public wordlist wordTree;
  JScrollPane scroller;
  int lastwordtab = -1;
  String wordlisttab1;
  String wordlisttab2;
  String wordlisttab3;
  JPanel wtab1;
  JPanel wtab2;
  JPanel wtab3;
  KeyListener signonkey;
  int lastrect = -1;
  int scrollbarwidth;
//  JLabel clicktoplay;
  JLabel clicktoexpand;
//  JLabel clicktoplayi;
//  JLabel clicktolisten;
//startPR2009-10-21^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  JLabel choosecourse;
  JLabel current;
  JLabel currentlist;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//  mlabel_base samplesmess;
//  String samplesmesstext;
//  mlabel_base samplesmess2;
//  String samplesmess2text;
//  mlabel_base splitwarning;
  String featurestext;
  public topic usingsuperlist; // curent topic is a 'superlist'
  boolean specialsuperlist, wassuperlist;
  topic superswap;
//  boolean sametopic;
//  mbutton superbutton;
//  mbutton superbutton2;
//  mlabel_base nosuperphonics;
//  mlabel_base superlabel;
//  String superbuttontext;
//  String superbuttontext2;
  Color greencolor = new Color(0,196,0);
//  public static boolean simplesuper;  // no autospell or throwing into subtopic
  public static boolean spellingonly;
  String nogame,nogames;
  boolean showmarkedgames,canshowmarkedgames;
  int markg[], markg2[];
  Rectangle markrect[], markrect2[];
//startPR2010-05-24^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//  mbutton showallgames;
//  JButton showallgames;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  String allgamestext1, allgamestext2, iconheading,iconheading2;
//startPR2009-10-10^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  String allgamesdefaulttext1, allgamesdefaulttooltip,allgamesdefaulttext2,
      iconheadingdefault,iconheadingdefault2, treeheadingdefault, treeheadingdefault2;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR

  public static ImageIcon picicon;
  public static boolean allowStuImportPics_Icon;
  public static boolean allowStuImportPics_OwnWords;
  ArrayList tabs = new ArrayList();

  boolean blockcoursechange =false;
  boolean musicFolderExists, helpFolderExists, helpFolderExists2;


  static String rw[];   // list of possible reward games
//startPR2004-07-22^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  /**
   * Acts as a block for routines that are not appropriate for Macintosh computers.
   * <code>true</code>if the routine is to be blocked.
   */
  public static boolean macBlock = false;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2008-06-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//  Timer checkCDTimer;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   // RB  20/1/06  start
  boolean refreshing;
  // RB  20/1/06  end
//startPR2006-11-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  final int MENUSHORTCUTMASK = t.getMenuShortcutKeyMask();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2007-02-16^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  boolean printMenuSetForExtended;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2007-11-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  String infantcourses[];
  static Font defaultfontname;
  static Font currdefaultfont;
  static Font defaultinfantfontname;
  String importFontNames[] = new String[]{"Sassoon Primary Infant WS.ttf",
      "Sassoon Sans Medium WS.ttf",
      "Sassoon Sans WS.ttf"};
  static Font[] importedfonts = new Font[]{};
//startPR2008-08-05^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//  static String defaultmenufontname;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  Font originalInfantFont;
  static Font gamehelpfont;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2008-06-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  static boolean cdcheckdue = false;
  static boolean cdcheckfirstcheck = true;
  static int cdcheckfailcount = 0;
//  final int CDCHECKDELAY_NA_NORMAL = 300000;
//  final int CDCHECKDELAY_NA_FAILED = 30000;

  final int CDCHECKDELAY_NA_NORMAL = 3000;
  final int CDCHECKDELAY_NA_FAILED = 300;

  final int CDCHECKDELAY_CD_NORMAL = 200000;
  int CDCHECKFAILMAX = 3;
  progress_base loadprogressbar;
  Timer testTimer;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2008-09-16^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//  boolean newVersionForOptions = false;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2008-09-22^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  static final String OTHERWORKFORTEACHER = "";
  public int whichteacherlast = -1;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2008-10-12^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  static boolean antialiasing;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2009-04-24^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  boolean nooptions = false;
  boolean newVersion = false;
  boolean newSubVersion = false;
  boolean newSubSubVersion = false;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2009-10-10^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  public String markgameheadings[][];
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  public String markgameformulas[][];
//startPR2008-11-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  static String location;
//startPR2012-02-01^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  static boolean splitlicence = false;
  static boolean nondeactivatable = false;
  static String expiry;
  JMenuItem mdisablelicence;
  JMenuItem mviewlicence;
  JMenuItem mconvertexpiry;
//  JMenuItem mnewlicence;
//endPRauth^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  JPanel fastmodeok = new JPanel(new GridBagLayout());
  JPanel fastmodena = new JPanel(new GridBagLayout());
  JLabel fastwordlist;
//  static Color fastmodecolor = new Color(102,204,51);
  static Color fastmodecolor = new Color(34, 139, 34);
  static Color fastmodecolor_lighter = new Color(91, 200, 91);
//  boolean fastmodemesslast = true;
//  String fastmodemess;
  String fastmodemessinfo;
  String fastmodetitle;
  JTextPane fasttp;
  JLabel lbfastmlist;
  JButton fminfo;
  static ArrayList sharewith;
  JMenuItem mupdatescheck;
  JCheckBoxMenuItem mupdatescheckall;
//  static boolean updateRequested = false;

  JTextField publictextSearch;
  int switchtomargin = 30;
  static Font gamestabfont = null;
  static String tabstrings[];

  String tabcolors[];
  ArrayList headinggames;
  static String str_recommended;
  static String str_allavailable;
//  String str_setwork;
  int tabpanetop;
  public String[] universalExcludedGames;
  public static String resourcesPlus;
  public static String recordsPlus;
  public static String resourcesFileSuffix;
  public static String resourcesdb;
String cl[];
    ImageIcon im_stu;
    ImageIcon im_admin;
    ImageIcon im_sub;
    int alltype;
    String strtabnextpage,strtabprevpage;
    int lasttab = -1;
    boolean donegameflags = false;
      sharedplay sharedp;
      mover backsharerect;
      int backsharemargin;
      Color topbarcol = new Color(243,243,255);
          long backshareremoveat = -1;
          mover initialGamesHelp = null;
          public mover.picandname currUserPic;
          mover recommendtm;
          mover alsorecommendtm;
          mover supertm;
mover nextpagetm;
mover prevpagetm;
String oriChooseTopicName;
public WebAuthenticate_base wa;
//startPR2012-02-01^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
public WebAuthenticateNetwork_base wan;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
JFrame childFrame = null;
String str_gamewarnaddpicrec, str_gamewarnaddpic, str_gamewarnaddextrarec, str_gamewarnaddrec, str_gamewarownlist;
String str_gamewarnaddpicrec2, str_gamewarnaddpic2, str_gamewarnaddextrarec2, str_gamewarnaddrec2;
      String strdefinition;
      String strtranslation;
      String strrecordingtype;
JPanel pnCourseInfo;
String strnotfastmode;
String strnotfastmodeheading;
JTextPane fasttpna;

  String strOwlTooltip;
  String strOwlTooltipNa;
  String strOwlTooltipNaFix;
  String strMChoseTeacher;
  String strOtherWork;
  DragPanel dp;
  static String soundPatchExtra = "_extra";
  public static String supdates[] = null;
  public static File updateFile = null;
  public String resourcesFolderName = "Resources";
  public String recordsFolderName;
  public static String updatesFolderName = "updates";
  public ArrayList imageToTopicList = new ArrayList();
  String topdb;
  String topicListForImages[];
  JButton btFindTopic;
  public static Color col_ExcludedWords = new Color(255,140,0);
  /**
   * Constructs the frame: -
   * <li>Sets up the public text database so that <code>u.gettext()</code> can be used.
   * <li>Establishes options from the databases using <code>setoptions()</code>
   * <li>Allows network use when the licence is valid
   * <li>Sets up the games trees.
   * <li>Creates the menus using <code>createmenu()</code>.
       * <li>Sets up publicGame library, publicTopic libraries and pulbic course list.
   * <li>Games are set up using <code>topic.buildSentGames()</code>
   * <li>All components are created using <code>jbInit()</code>
   * <li>The publicsay, publicimage, publicsigns, publicfingers, publicsent1
   *      and publicsent2 file lists are set up.
   * <li>The labels that appear on the main frame are created.
   * <li><code>keyEventdispatcher</code> is added to catch F1 and escape key inputs.
   *     These display help and close the program respectively.
       * <li><code>sharkPanel</code> is created using <code>setupsharkpanel()</code>.
       *     This panel has fish swimming on it and is desplayed initially on start up.
   *     The <code>sharkStartFrame</code> is used to hold this panel.
   * <li><code>gamePanel</code> is built using<code>buildgamepanel1()</code>.
   *     This panel displays all the game icons so the user can choose a game to play.
   *     This panel is also put into the <code>sharkStartFrame</code>.
   */
  public sharkStartFrame() {
    int i,j;
    short tot;
    String s[] = null;
    mainFrame = this;
    String newResolutionUser = null;
    Point newResolutionP = null;
    setFontSizeBasedOnScreen();
//startPR2004-12-02^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    spokenWord.startspeaker();
    try {
      publicPath = new File(shark.publicPath);
      separator = publicPath.separatorChar;
      publicPathplus
          = new String(publicPath.toString() + separator);
      sharedPath = new File(shark.sharedPath);
      sharedPathplus = new String(sharedPath.toString() + separator);
      String updateFolder = null;

      if(shark.licenceType.equals(shark.LICENCETYPE_USB)){
          String ss1[] = u2_base.splitString(sharedPath.toString(), separator);
          ss1 = u2_base.removeString(ss1, ss1.length-1);
          updateFolder = u2_base.combineString(ss1, String.valueOf(separator));  
          String currpath;
//          if(shark.macOS &&  shark.macOracleJava) currpath = shark.getAppBundleParent();
          if(shark.macOS &&  shark.macOracleJava) currpath = shark.getAppBundlePath(true);
          else currpath = System.getProperty("user.dir");          
          boolean currRunningUpdate = ((shark.macOS && !publicPathplus.startsWith(currpath))||
                  (!shark.macOS && currpath.charAt(0)!=publicPathplus.charAt(0)));
          boolean macUpdateAppExists = new File(updateFolder+separator+updatesFolderName+separator+shark.programName+" "+shark.versionNo +
                                  shark.getVersionType(shark.LICENCETYPE_USB)+".app").exists();
          File updateZipFile = new File(updateFolder+separator + updatesFolderName+".zip");
          updateFile = new File(updateFolder+separator+updatesFolderName);
          if(!updateFile.exists())updateFile = null;
          boolean relaunch = false;
          String appbundleprefix = null;
          if(shark.macOS){
            if(shark.macOracleJava) appbundleprefix = currpath;
            else appbundleprefix = System.getProperty("user.dir");              
          }
          if(updateZipFile.exists()){
              // if there's any unzipping to do and currently running from update
              // need to restart before unzipping
              if(currRunningUpdate){
                  if(shark.macOS){
                    u2_base.restart_Mac(appbundleprefix +
                              separator+shark.programName+" "+shark.versionNo+
                              shark.getVersionType(shark.LICENCETYPE_USB)+".app"+separator+
                              "Contents"+ separator +
                              shark.ACTIVATE_PREFIX+shark.licenceType+
                              "_Standard_Restart"+
                              ".app");
                    System.exit(0);
                  }
                  else{
                      relaunch = true;
                  }
              }
              if(!relaunch){
                  if(updateFile!=null){
                     String[] myFiles;
                     if(updateFile.isDirectory()){
                        myFiles = updateFile.list();
                        for (int ii=0; ii<myFiles.length; ii++) {
                            File myFile = new File(updateFile, myFiles[ii]);
                            myFile.delete();
                        }
                     }
                      updateFile.delete();
                  }
                  u2_base.unZip( new java.util.zip.ZipFile(updateZipFile),
                          updateFolder+separator);
                  // check if sucessful first?
                  updateZipFile.delete();

                  // if the newly unzipped folder contains a jar/app bundle restart
                  String exename = shark.macOS?
                      shark.programName+" "+shark.versionNo +
                                  shark.getVersionType(shark.LICENCETYPE_USB)+".app":
                     shark.jarName;
                  if(new File(updateFolder+separator+updatesFolderName+separator+exename).exists())relaunch = true;
              }
          }
          else if(shark.macOS && macUpdateAppExists && !currRunningUpdate){
              relaunch = true;
          }
          if(relaunch){
              if(!shark.macOS){
                  File froots[] = File.listRoots();
                  for(int k = 0; k < froots.length; k++){
                      String s1 = javax.swing.filechooser.FileSystemView.getFileSystemView().getSystemDisplayName(froots[k]);
                      if(s1==null) continue;
                      String s2 = javax.swing.filechooser.FileSystemView.getFileSystemView().getSystemTypeDescription(froots[k]);
                      if(s2==null) continue;
                      int p = s1.lastIndexOf(":");
                      if(p<0)continue;
                      String dletter = s1.substring(p-1, p+1);
                      String exe;
                      s1 = s1.toUpperCase();
                      if(s1.startsWith(shark.programName.toUpperCase()+" ") &&
                              new File( exe =  dletter+separator+shark.programName+" "+shark.versionNo +
                              shark.getVersionType(shark.LICENCETYPE_USB)+".exe").exists()){
                           u2_base.launchFile(exe, false);
                           break;
                      }
                  }
              }
              else{
                    u2_base.restart_Mac(appbundleprefix +
                              separator+shark.programName+" "+shark.versionNo+
                              shark.getVersionType(shark.LICENCETYPE_USB)+".app"+separator+
                              "Contents"+separator+
                              shark.ACTIVATE_PREFIX+shark.licenceType+
                              "_Update_Restart"+
                              ".app");
              }
              System.exit(0);
          }
          if(updateFile!=null){
              supdates = db.dblist(new File[] {updateFile});
              for(int k = 0; k < supdates.length; k++){
                  String ss[] = u2_base.splitString(supdates[k], separator);
                  supdates[k] = ss[ss.length-1];
              }
          }
      }
      //----set up text libs (so we can use u.gettext())----------------------------------
      tot = 0;
      s = db.dblist(new File[] {publicPath});
      Arrays.sort(s);
      if(supdates!=null){
          for(int k = 0; k < supdates.length; k++){
              s = u2_base.addString(s, updateFile + shark.sep + supdates[k]);
          }
      }
      for (i = 0; i < s.length; ++i) {
        if (s[i].toLowerCase().indexOf("publictext") >= 0)
          ++tot;
      }
      if (tot == 0) {
        db.create(sharkStartFrame.publicPathplus + "publictext");
        s = db.dblist(new File[] {publicPath});
        tot = 1;
      }
      publicTextLib = new String[tot];
      tot = 0;
      for (i = 0; i < s.length; ++i) {
        if (s[i].toLowerCase().indexOf("publictext") >= 0){
          publicTextLib[tot++] = updateCheck(s[i], supdates, updateFile, publicTextLib);
          }
      }
//startPR2007-05-17^^^^^^^^^^^^^^^^^testtree^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      if(!shark.language.equals(shark.LANGUAGE_EN)){
//        UIManager.put("FileChooser.lookInLabelText", u.gettext("filechooser", "lookin"));
//        UIManager.put("FileChooser.filesOfTypeLabelText", u.gettext("filechooser", "filesoftype"));
//        UIManager.put("FileChooser.fileNameLabelText", u.gettext("filechooser", "filename"));
//      }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      maxsamenum = new int[] {10,20,30,0};                     // v5 start rb 7/3/08
      
//      maxsametext = u.splitString(u.gettext("maxsame","label"));
//      maxsametooltip = u.gettext("maxsame","tooltip_base");
//      maxsame = new JCheckBoxMenuItem[maxsamenum.length];      // v5 end rb 7/3/08
//      starttitle = u.gettext("mainframe", "title", shark.versionNo);
//startPR2008-06-08^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      if(!shark.production){
//        File f = new File(System.getProperty ("java.class.path"));
//        java.util.Date dd = new java.util.Date(f.lastModified());
//        starttitle += "[beta version " + shark.versionNoDetailed + " - " +
//            (new java.text.SimpleDateFormat("dd/MM/yy 'at' HH:mm")).format(dd) + "]" +
//            "     -     ";
//      }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      //--------set up image libs--------------------------------------
      tot = 0;
      for (i = 0; i < s.length; ++i) {
        if (s[i].toLowerCase().indexOf("publicimage") >= 0)
          ++tot;
      }
      if (tot == 0) {
        db.create(sharkStartFrame.publicPathplus + "publicimage");
        s = db.dblist(new File[] {publicPath});
        tot = 1;
      }
      publicImageLib = new String[tot];
      tot = 0;
      for (i = 0; i < s.length; ++i) {
        if (s[i].toLowerCase().indexOf("publicimage") >= 0)
          publicImageLib[tot++] = updateCheck(s[i], supdates, updateFile, publicImageLib);
      }
    tot = 0;
    for (i = 0; i < s.length; ++i) {
      if (s[i].toLowerCase().indexOf("publictopics") >= 0){
        ++tot;
        if(shark.production)break; // don't want more than one publictopics in a release
      }
    }
    if (tot == 0) {
      db.create(sharkStartFrame.publicPathplus + "publictopics");
      s = db.dblist(new File[] {publicPath});
      tot = 1;
    }
    publicTopicLib = new String[tot];
    tot = 0;
    for (i = 0; i < s.length; ++i) {
      if (s[i].toLowerCase().indexOf("publictopics") >= 0){
        publicTopicLib[tot++] = updateCheck(s[i], supdates, updateFile, publicTopicLib);
        if(true || shark.production)break; // don't want more than one publictopics in a release
      }
    }     
    publicKeypadLib = updateCheck(sharkStartFrame.publicPathplus + "publickeypad", supdates, updateFile, null);
      //------------------------------------------------------------

//startPR2006-1-15^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      plistRec = sharedPathplus + "plistRec";
      plistRec = sharedPathplus + "plistrec";
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2007-11-30^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      if(shark.debug)u.okmess(shark.programName+" Debug", "4");
      if(shark.debug)u.okmess(shark.programName+" Debug", "4", sharkStartFrame.mainFrame);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2007-11-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      infantcourses = u.splitString(u.gettext("font", "infantcourses"));
      String gamehfont = u.gettext("font", "gamehelp");
      String infname = u.gettext("font", "defaultinfant");
      String mainname = u.gettext("font", "default");
      if(shark.macOS && !shark.macOracleJava){
          gamehfont = gamehfont.replaceAll(" ", "");
          infname = infname.replaceAll(" ", "");
          mainname = mainname.replaceAll(" ", "");
      }
      try {
        for(int ii = 0; ii < importFontNames.length; ii++){
          String importname = importFontNames[ii];
          InputStream fileStream = getClass().getResourceAsStream("/"+importname);
          Font f = Font.createFont(Font.TRUETYPE_FONT, fileStream);
          importedfonts = u.addFont(importedfonts, f);
          if(f.getName().equals(infname))
            defaultinfantfontname = f.deriveFont(Font.PLAIN, (float)sharkStartFrame.BASICFONTPOINTS);
          else if(f.getName().equals(mainname))
            defaultfontname = f.deriveFont(Font.PLAIN, (float)sharkStartFrame.BASICFONTPOINTS);
          if(f.getName().equals(gamehfont))
            gamehelpfont = f.deriveFont(Font.PLAIN, (float)sharkStartFrame.BASICFONTPOINTS);
          fileStream.close();
        }
      }
      catch (Exception e) {
        if(shark.debug) {
//startPR2007-11-30^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//          u.okmess(shark.programName + " Debug", "Error loading fonts");
          u.okmess(shark.programName + " Debug", "Error loading fonts", sharkStartFrame.mainFrame);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        }
      }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    if(wan == null && shark.licenceType.equals(shark.LICENCETYPE_NETWORKACTIVATION))
      wan = new WebAuthenticateNetwork_base();
      spokenWord.closespeaker();
    testTimer = (new Timer((shark.licenceType.equals(shark.LICENCETYPE_NETWORKACTIVATION))?CDCHECKDELAY_NA_NORMAL:CDCHECKDELAY_CD_NORMAL, new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if((!shark.network||(shark.licenceType.equals(shark.LICENCETYPE_NETWORKACTIVATION)))
           && !Demo_base.isDemo){
          boolean firstafterlaunch =false;
          if(testTimer.getInitialDelay()==0){
              testTimer.setInitialDelay((shark.licenceType.equals(shark.LICENCETYPE_NETWORKACTIVATION))?CDCHECKDELAY_NA_NORMAL:CDCHECKDELAY_CD_NORMAL);
              firstafterlaunch = true;
          }
          else cdcheckfirstcheck = false;
          testTimer.stop();
          if((shark.licenceType.equals(shark.LICENCETYPE_NETWORKACTIVATION)) &&
                  (firstafterlaunch || testTimer.getDelay()==CDCHECKDELAY_NA_FAILED)
                  ){
            Thread copyProtectThread = new Thread(new copyProtect_Work());
            copyProtectThread.start();
              //checkcd();
            }
          else
              cdcheckdue = true;
        }
        spokenWord.closespeaker();
      }
    }));
    testTimer.setRepeats(true);
    testTimer.setInitialDelay(0);
    testTimer.start();
      setoptions(); 
    newResolutionUser = ChangeScreenSize_base.getUserData();
    
    if(newResolutionUser!=null){
        newResolutionP = ChangeScreenSize_base.getResolutionData();
        if(newResolutionP==null)newResolutionUser = null;
    }
    ChangeScreenSize_base.isActive = newResolutionUser != null;   
    
    
    Rectangle newResolutionRect = null;
    if(ChangeScreenSize_base.isActive){
        newResolutionRect = new Rectangle(0, 0, newResolutionP.x, newResolutionP.y);
        screenSize = newResolutionRect.getSize(); 
        setFontSizeBasedOnScreen();
        u.defaultfont(true);
    } 
//      fastmodecolor_lighter = u.lighter(fastmodecolor, 1.25f);
      musicFolderExists = new File(publicPathplus+u.gettext("helpfolder", "musicfolder")).exists();
      helpFolderExists = new File(publicPathplus+u.gettext("helpfolder", "helpfolder", File.separator)).exists();
      helpFolderExists2 = new File(publicPathplus+u.gettext("helpfolder2", "helpfolder", File.separator)).exists();
      strdefinition = u.gettext("definition", "label").toLowerCase();
      strtranslation = u.gettext("translation", "label").toLowerCase(); 
      strrecordingtype = u.gettext("owlgamewarnings", "recordingtype");
      String stractions = u.gettext("actions", "label");
      String strownwords = u.gettext("mownwords", "label");
      str_gamewarnaddpicrec = u.gettext("owlgamewarnings", "str_gamewarnaddpicrec");
      str_gamewarnaddpicrec2 = u.gettext("owlgamewarnings", "str_gamewarnaddpicrec2");
      str_gamewarnaddpic = u.gettext("owlgamewarnings", "str_gamewarnaddpic");
      str_gamewarnaddpic2 = u.gettext("owlgamewarnings", "str_gamewarnaddpic2");
      str_gamewarnaddextrarec = u.gettext("owlgamewarnings", "str_gamewarnaddextrarec");
      str_gamewarnaddextrarec2 = u.gettext("owlgamewarnings", "str_gamewarnaddextrarec2");
      str_gamewarnaddrec = u.gettext("owlgamewarnings", "str_gamewarnaddrec");
      str_gamewarnaddrec2 = u.gettext("owlgamewarnings", "str_gamewarnaddrec2");
      str_gamewarownlist = u.edit(u.gettext("owlgamewarnings", "ownlistextra"), stractions, strownwords);

      String publiclangkeypad = updateCheck(sharkStartFrame.publicPathplus + "publiclangkeypad", supdates, updateFile, null);
      String sskeyp[] = db.list(publiclangkeypad, db.SAVEKEYPAD);
      String ssuserkeyp[] = null;
      File kpfile;
      if(!(kpfile = new File(sharedPathplus+"Keypads")).exists()){
          kpfile.mkdirs();
      }
      else{
          File fpsfile[] = kpfile.listFiles(new FileFilter() {
              public boolean accept(File file) {
                String s = file.getName();
                String kp = "Keypad";
                String sha = ".sha";
                return s.startsWith(kp) && s.endsWith(sha) && (s.length()>kp.length()+sha.length());
              }});
          for(int n = 0; n < fpsfile.length; n++){
              String nam = fpsfile[n].getName();
              String snam[];
              if((snam = db.list("Keypads"+shark.sep+nam.substring(0, nam.lastIndexOf('.')), db.SAVEKEYPAD)).length==1){
                    if(ssuserkeyp==null)
                        ssuserkeyp = new String[]{snam[0]};
                    else
                        ssuserkeyp = u.addString(ssuserkeyp, snam[0]);
              }
          }
      }
      publicWidgitLib = updateCheck(sharkStartFrame.publicPathplus + "publicwidgit", supdates, updateFile, null);
      publicAvatarLib = updateCheck("publicavatars", supdates, updateFile, null);
      publicTestSayLib = updateCheck("publictestsay", supdates, updateFile, null);
      publicV4TopicsLib = updateCheck(sharkStartFrame.publicPathplus + "publicv4topics", supdates, updateFile, null);
      publicMarkGamesLib = updateCheck("publicmarkgames", supdates, updateFile, null);
      publicMarkGamesFormulaLib = updateCheck("publicmarkgamesformulae", supdates, updateFile, null);
      publicLangKeypads = new String[sskeyp.length + (ssuserkeyp!=null?ssuserkeyp.length:0)][];
      for(int n = 0; n < sskeyp.length; n++){
        publicLangKeypads[n]= new String[]{sskeyp[n], publiclangkeypad};
      }
      if(ssuserkeyp!=null){
          int k = 0;
          for(int n = sskeyp.length; n < publicLangKeypads.length; n++){
            publicLangKeypads[n]= new String[]{ssuserkeyp[k], sharkStartFrame.sharedPathplus+"Keypads"+shark.sep+"Keypad"+ssuserkeyp[k]};
            k++;
          }
      }
//startPR2009-10-10^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    im_stu = new ImageIcon(publicPathplus + "sprites" +
                                        sharkStartFrame.separator +
                                         "student_il16.png");
    im_admin = new ImageIcon(publicPathplus + "sprites" +
                                        sharkStartFrame.separator +
                                         "admin_il16.png");
    im_sub = new ImageIcon(publicPathplus + "sprites" +
                                         sharkStartFrame.separator +
                                         "subadmin_il16.png");
      markgameheadings = getMarkGameHeadings();
      markgameformulas = getMarkGameFormulas();
      allowStuImportPics_Icon = db.query(sharkStartFrame.optionsdb, "nostuimportsicon", db.TEXT) < 0;
      allowStuImportPics_OwnWords = db.query(sharkStartFrame.optionsdb, "nostuimportsownwords", db.TEXT) < 0;
      resourcesFileSuffix = "-rs";
      resourcesPlus = resourcesFolderName + separator;
      recordsFolderName = u.gettext("sturec_", "folder");
      recordsPlus = recordsFolderName + separator;


      recordsPlus = u.gettext("sturec_", "folder") + separator;
        File fres = new File(sharedPathplus+resourcesPlus);
        if(!fres.exists()){
            fres.mkdirs();
            u.setNewFilePermissions(fres);
        }
        fres = new File(sharedPathplus+"Universal"+shark.sep);
        if(!fres.exists()){
            fres.mkdirs();
            u.setNewFilePermissions(fres);
        }
      String uniexclude = (String)db.find(sharkStartFrame.optionsdb, "uniexcludegames", db.TEXT);
      if(uniexclude!=null){
          universalExcludedGames = u.splitString(uniexclude);
      }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2008-11-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      if (shark.isLicenceActivated() && shark.debug){
//            String req = (String) db.find(optionsdb, "wa_request", db.TEXT);
//            if (req != null) {
//                req = u.gettext("mversion", "productkey") + " " + req.substring(11);
//                if (shark.debug) u.okmess(shark.programName + " Debug", req, sharkStartFrame.mainFrame);
//            }
//      }
//endPRauth^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2007-11-30^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      if(shark.debug)u.okmess(shark.programName+" Debug", "5");
      if(shark.debug)u.okmess(shark.programName+" Debug", "5", sharkStartFrame.mainFrame);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      strtabnextpage = u.gettext("tabs", "nextpage");
      strtabprevpage = u.gettext("tabs", "prevpage");

//      switchto = u.gettext("signon","switchto") + ' ';
      String yes = u.gettext("yesnocancel", "yes");
      if (yes != null) {
        UIManager.put("OptionPane.yesButtonText", yes);
        UIManager.put("OptionPane.noButtonText", u.gettext("yesnocancel", "no"));
        UIManager.put("OptionPane.cancelButtonText",
                      u.gettext("yesnocancel", "cancel"));
      }
      extraflash = u.gettext("extraflash", "games");
      if (!spokenWord.startspeaker())
        System.exit(100);
//startPR2007-12-20^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      if(!Demo_base.isDemo) deletecheck();
//      if(!Demo_base.isDemo && shark.production) typecheck();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2008-11-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      if(shark.network) {
      if (shark.licenceType.equals(shark.LICENCETYPE_NETWORK)) {
//endPRauth^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        FileInputStream f = new FileInputStream(publicPathplus + "NETWORK.sh");
        byte bb[] = new byte[50000];
        int len = f.read(bb);
        Object noofusers = null;
        InputStream is = new ByteArrayInputStream(bb);
        ObjectInputStream fIndex = new ObjectInputStream(is);
        licence = shark.decrypt1( (String) fIndex.readObject(),
                                 shark.phonicshark?"phonicsharknetwork":"wordsharknetwork");
        if (licence == null || (i = licence.indexOf("((")) < 0) {
//startPR2004-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//          JOptionPane.showMessageDialog(null,
          JOptionPane.showMessageDialog(sharkStartFrame.mainFrame,
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                                        "WORDSHARK network identification corrupt",
                                        "WORDSHARK set-up",
                                        JOptionPane.WARNING_MESSAGE);
          System.exit(100);
        }
        else {
          if ((j = licence.indexOf("((",i+2)) >= 0) {
            serial = licence.substring(j + 2);
            licence1 = licence.substring(i+2, j);
            licence = licence.substring(0, i);
          }
          else {                // older version
            serial = licence.substring(i + 2);
            licence = licence.substring(0, i);
          }
          school = (String) fIndex.readObject();
          try {
            noofusers = fIndex.readObject(); //users
            fIndex.readObject(); //notes
            fIndex.readObject(); //printagree
            Date exp = (Date) fIndex.readObject(); //expiry date
            if ( (new Date()).getTime() > exp.getTime()) {
//startPR2004-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//          JOptionPane.showMessageDialog(null,
              JOptionPane.showMessageDialog(sharkStartFrame.mainFrame,
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                                            "This demo version has expired",
                                            "SHARK set-up",
                                            JOptionPane.WARNING_MESSAGE);
              System.exit(200);
            }
            gotdate = true;
          }
          catch (IOException e) { // expiry date not there - OK
          }
        }
        f.close();
        oldli = licence;
        oldli1 = licence1;
        String ss = (String) db.find(optionsdb, "kkkk", db.TEXT);
//startPR2007-07-12^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        if (gotdate)
          users = Integer.parseInt(String.valueOf(noofusers));
//startPR2010-03-17^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        else  {
            if( ss == null || (users = getusers(serial, school, ss)) < 0)
                ss = checkGetLicence();
            if( ss == null || (users = getusers(serial, school, ss)) < 0){
                if(loadprogressbar!=null){
                    loadprogressbar.setVisible(false);
                }
                new networkkey_base(true);
                while (networkkey_base.running)
                    u.pause(1000);
                if(loadprogressbar!=null){
                    loadprogressbar.setVisible(true);
                }
                    ss = (String) db.find(optionsdb, "kkkk", db.TEXT);
                if (ss == null || (users = getusers(serial, school, ss)) < 0)
                    System.exit(0);
            }
        }
//        else if ( ss == null || (users = getusers(serial, school, ss)) < 0) {
//          if(loadprogressbar!=null)loadprogressbar.setVisible(false);
//          new networkkey_base(true);
//          while (networkkey_base.running)
//            u.pause(1000);
//          if(loadprogressbar!=null)loadprogressbar.setVisible(true);
//          ss = (String) db.find(optionsdb, "kkkk", db.TEXT);
//          if (ss == null || (users = getusers(serial, school, ss)) < 0)
//            System.exit(0);
//        }
//endPRtoupdate^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        if ( (i = licence.indexOf("##")) >= 0) {
          licence = licence.substring(0, i) + String.valueOf(users) +
              licence.substring(i + 2);
        }
      }
//startPR2008-10-08^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2009-04-08^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      else users = 1;
      }
//      else users = 0;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2007-11-30^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      if(shark.debug)u.okmess(shark.programName+" Debug", "6");
      if(shark.debug)u.okmess(shark.programName+((shark.network)?" ("+serial+")":"")+" Debug", "6", sharkStartFrame.mainFrame);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      gameTree = new gamestoplay();
      notactive2 = u.gettext("g_","notactive2");
      publicGameTree = new gamestoplay();
      nullcursor = t.createCustomCursor(t.getImage(sharkStartFrame.
          publicPathplus + "sprites" + sharkStartFrame.separator +
          "nullptr.gif"),
                                        new Point(0, 0), "nullptr");
//startPR2005-10-16^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      macnullcursor = t.createCustomCursor(t.getImage(sharkStartFrame.
          publicPathplus + "sprites" + sharkStartFrame.separator +
          "nullptr.gif"), new Point(0, 0), "macnullptr");
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      sharkicon = t.createImage(publicPathplus + "sprites" +
                                sharkStartFrame.separator + 
              (shark.macOS?"sharkicon_mac.png":"sharkicon.gif"));
      u.loadsystemgif();
      vpanel2.setBorder(BorderFactory.createEtchedBorder());
//startPR2007-11-19^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      if(shark.macOS)
        vpanel2.setBackground(Color.orange);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2007-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      wordfontm = getFontMetrics(new Font("Wordshark", Font.PLAIN,
//                                          BASICFONTPOINTSX));
//  wordfontheight = wordfontm.getHeight();     
      wordfont = getdefaultfont();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    }
    catch (FileNotFoundException e) {
//startPR2004-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//          JOptionPane.showMessageDialog(null,
      JOptionPane.showMessageDialog(sharkStartFrame.mainFrame,
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                                    "SHARK network identification missing",
                                    "SHARK set-up", JOptionPane.WARNING_MESSAGE);
      System.exit(100);
    }
    catch (ClassNotFoundException e) {
//startPR2004-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//          JOptionPane.showMessageDialog(null,
      JOptionPane.showMessageDialog(sharkStartFrame.mainFrame,
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                                    "SHARK network identification currupt",
                                    "SHARK set-up", JOptionPane.WARNING_MESSAGE);
      System.exit(100);
    }
    catch (IOException e) {
//startPR2004-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//          JOptionPane.showMessageDialog(null,
      JOptionPane.showMessageDialog(sharkStartFrame.mainFrame,
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                                    "SHARK network identification IO error",
                                    "SHARK set-up", JOptionPane.WARNING_MESSAGE);
      System.exit(100);
    }
    catch (SecurityException e) {
//startPR2004-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//          JOptionPane.showMessageDialog(null,
      JOptionPane.showMessageDialog(sharkStartFrame.mainFrame,
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          "SHARK network identification not accessible", "SHARK set-up",
          JOptionPane.WARNING_MESSAGE);
      return;
    }
    //----set up games libs----------------------------------
    tot = 0;
//    s = db.dblist(new File[] {publicPath});
    for (i = 0; i < s.length; ++i) {
      if (s[i].toLowerCase().indexOf("publicgames") >= 0)
        ++tot;
    }
    if (tot == 0) {
      db.create(sharkStartFrame.publicPathplus + "publicgames");
      s = db.dblist(new File[] {publicPath});
      tot = 1;
    }
    publicGameLib = new String[tot];
    tot = 0;
    for (i = 0; i < s.length; ++i) {
      if (s[i].toLowerCase().indexOf("publicgames") >= 0)
        publicGameLib[tot++] = updateCheck(s[i], supdates, updateFile, publicGameLib);
    }
    
    
    tot = 0;
    for (i = 0; i < s.length; ++i) {
      if (s[i].toLowerCase().indexOf("publicsplits") >= 0)
        ++tot;
    }
    if (tot == 0) {
      db.create(sharkStartFrame.publicPathplus + "publicsplits");
      s = db.dblist(new File[] {publicPath});
      tot = 1;
    }
    publicSplitsLib = new String[tot];
    tot = 0;
    for (i = 0; i < s.length; ++i) {
      if (s[i].toLowerCase().indexOf("publicsplits") >= 0)
        publicSplitsLib[tot++] = updateCheck(s[i], supdates, updateFile, publicSplitsLib);
    }    
    

    //-----------------set up public course list
    publicCourses = db.listsort(u.absoluteToRelative(publicTopicLib[0]), db.TOPICTREE);
    int coursecount = 0;
    for(i=0;i<publicCourses.length;++i) {                     // start rb 26-11-07
     if(publicCourses[i].startsWith("  ")) {
        publicCourses = u.removeString(publicCourses,i);
        --i;
     }
     else coursecount++;
    }                                                          // end rb 26-11-07
    if(shark.phonicshark && coursecount!=2){
        JOptionPane.showMessageDialog(sharkStartFrame.mainFrame,
                   "Invalid configuration for "+shark.programName,
                   shark.programName, JOptionPane.WARNING_MESSAGE);
        System.exit(100);
    }
    publicCourseList.setListData(publicCourses);
    publicCourseList.addMouseListener(new MouseAdapter() {
      public void mouseReleased(MouseEvent e) {
        if (e.getModifiers() == e.BUTTON3_MASK)
          currTopicView.addCourse( (String) publicCourseList.getSelectedValue());
      }
    });
    //------------- set up public topic libs
    wordlisttab1 = u.gettext("wordlisttabs", "tab1");
    wordlisttab2 = u.gettext("wordlisttabs", "tab2");
    wordlisttab3 = u.gettext("wordlisttabs", "tab3");
    extracourses = u.gettext("dis_", "extracourses");
    definitions = u.gettext("dis_", "definitions");

    publicExtraCourseLib = db.dblist(sharedPath, extracourses);
    Diagnostics_base diagnostics = new Diagnostics_base();
    boolean fixcourses = diagnostics.needFixExtraCourses()||newVersion;
    File fixearlycourses_file[] = diagnostics.needFixEarlyExtraCourses();



    for(int k = 0; k < fixearlycourses_file.length; k++){
        boolean fixearlycourses = fixearlycourses_file[k]!=null;
        if(fixcourses || fixearlycourses){
            if(loadprogressbar==null)
                loadprogressbar = new progress_base(this, shark.programName,
                                                   u.edit(u.gettext("initializing", "label"), shark.programName),
                                                   new Rectangle(sharkStartFrame.screenSize.width/4,
                                                                 sharkStartFrame.screenSize.height*2/5,
                                                                 (sharkStartFrame.screenSize.width/2),
                                                                 (sharkStartFrame.screenSize.height/5)));
            if(fixearlycourses){
                diagnostics.fixearlycourses(fixearlycourses_file);
                fixcourses = true;
            }
        }
    }

    publicTopicLib = u.addString(publicTopicLib, 
            (publicExtraCourseLib = db.dblist(sharedPath, extracourses)));
    if(fixcourses)diagnostics.fixcourses();
    tabcolors = u.splitString(u.gettext("gametree", "tabcolors"));
    
    str_recommended = u.halveText(u.gettext("tabs", "recommended"));
    str_allavailable = u.halveText(u.gettext("tabs", "all"));
    
   
//    str_setwork = u.gettext("tabs", "setwork");

//    development = new JMenu();
//    wordlistcolour = new JMenu();
//    cooperativeplay = new JMenu();
//    development.setText("Development");
//    wordlistcolour.setText("Word list colour");
//    cooperativeplay.setText("Cooperative play");
//startPR2008-09-16^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//   if(newVersionForOptions) student.fixcourses();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2007-11-30^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    if(shark.debug)u.okmess(shark.programName+" Debug", "7");
    if(shark.debug)u.okmess(shark.programName+((shark.network)?" ("+serial+")":"")+" Debug", "7", sharkStartFrame.mainFrame);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    topic.buildsentgames();
    wordTree = new wordlist();
    scroller = u.uScrollPane(wordTree);
    scroller.setHorizontalScrollBarPolicy(scroller.HORIZONTAL_SCROLLBAR_NEVER);
    scroller.setBorder(null);
    createmenu();
    ToolTipManager.sharedInstance().setEnabled(true);
    tooltip_base.tooltipsActive = true;
//    setwanthelp(!student.setup());
    String currnames[] = db.dblistnames(sharkStartFrame.sharedPath);
    if(!Demo_base.isDemo && newSubSubVersion){
        currnames = stripSpacesFromUserName(currnames);
    }
    student.checkadmin();    // rb 6/2/06
    if(!Demo_base.isDemo){
        if(newSubSubVersion){
            updateUserFiles(currnames);
        }
        else{
            Object o = db.find(optionsdb, "shafilecount", db.TEXT);
            // if user files have been dropped in after initialization
            String p = null;
            if(o!=null) p = ((String)o);
            int k = -1;
            if(p!=null){
                try{
                    k = Integer.parseInt(p);
                }
                catch(Exception ee){}
            }
            if(currnames.length!=k){
                updateUserFiles(currnames);
                db.update(optionsdb, "shafilecount", String.valueOf(currnames.length), db.TEXT);
            }
        }
    }
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
//startPR2007-11-30^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      if(shark.debug)u.okmess(shark.programName+" Debug", e.getMessage());
      if(shark.debug)u.okmess(shark.programName+((shark.network)?" ("+serial+")":"")+" Debug", e.getMessage(), sharkStartFrame.mainFrame);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    }
    setIconImage(sharkicon);

    // set up public 'say' and 'image' file list
    tot = 0;
    for (i = 0; i < s.length; ++i) {
      if (s[i].toLowerCase().indexOf("publicsay") >= 0)
        ++tot;
    }
    if (tot == 0) {
      db.create(sharkStartFrame.publicPathplus + "publicsay1");
      s = db.dblist(new File[] {publicPath});
      tot = 1;
    }
    publicSoundLib = new String[tot];
    tot = 0;
    for (i = 0; i < s.length; ++i) {
      if(s[i].endsWith(soundPatchExtra))continue;
      if (s[i].toLowerCase().indexOf("publicsay") >= 0){
        if (u.findString(s, updateCheck(s[i]+soundPatchExtra, supdates, updateFile, null))>=0)
            publicSoundLib[tot++] = updateCheck(s[i]+soundPatchExtra, supdates, updateFile, publicSoundLib);
        publicSoundLib[tot++] = updateCheck(s[i], supdates, updateFile, publicSoundLib);
      }
    }
    for (i = 0, tot = 0; i < s.length; ++i) {
      if (s[i].toLowerCase().indexOf("publicdef") >= 0)
        ++tot;
    }
    publicDefLib = new String[tot];
    tot = 0;
    for (i = 0; i < s.length; ++i) {
      if (s[i].toLowerCase().indexOf("publicdef") >= 0)
        publicDefLib[tot++] = updateCheck(s[i], supdates, updateFile, publicDefLib);
    }
    publicDefLib = u.addString(publicDefLib,
                                 db.dblist(sharedPath, definitions));
    for (i = 0, tot = 0; i < s.length; ++i) {
      if (s[i].toLowerCase().indexOf("publicsent1") >= 0)
        ++tot;
    }
    if (tot == 0) {
      db.create(sharkStartFrame.publicPathplus + "publicsent1");
      s = db.dblist(new File[] {publicPath});
      tot = 1;
    }
    publicSentLib = new String[tot];
    tot = 0;
    for (i = 0; i < s.length; ++i) {
      if(s[i].endsWith(soundPatchExtra))continue;
      if (s[i].toLowerCase().indexOf("publicsent1") >= 0){
        if (u.findString(s, updateCheck(s[i]+soundPatchExtra, supdates, updateFile, null))>=0)
            publicSentLib[tot++] = updateCheck(s[i]+soundPatchExtra, supdates, updateFile, publicSentLib);
        publicSentLib[tot++] = updateCheck(s[i], supdates, updateFile, publicSentLib);
      }
    }  
    //---------------------------------------------------
    for (i = 0, tot = 0; i < s.length; ++i) {
      if (s[i].toLowerCase().indexOf("publicsent2") >= 0)
        ++tot;
    }
    if (tot == 0) {
      db.create(sharkStartFrame.publicPathplus + "publicsent2");
      s = db.dblist(new File[] {publicPath});
      tot = 1;
    }
    publicSent2Lib = new String[tot];
    tot = 0;
    for (i = 0; i < s.length; ++i) {
      if(s[i].endsWith(soundPatchExtra))continue;
      if (s[i].toLowerCase().indexOf("publicsent2") >= 0){
        if (u.findString(s, updateCheck(s[i]+soundPatchExtra, supdates, updateFile, null))>=0)
            publicSent2Lib[tot++] = updateCheck(s[i]+soundPatchExtra, supdates, updateFile, publicSent2Lib);
        publicSent2Lib[tot++] = updateCheck(s[i], supdates, updateFile, publicSent2Lib);
      }
    }    
    //---------------------------------------------------
    for (i = 0, tot = 0; i < s.length; ++i) {
      if (s[i].toLowerCase().indexOf("publicsent3") >= 0)
        ++tot;
    }
    if (tot == 0) {
      db.create(sharkStartFrame.publicPathplus + "publicsent3");
      s = db.dblist(new File[] {publicPath});
      tot = 1;
    }
    publicSent3Lib = new String[tot];
    tot = 0;
    for (i = 0; i < s.length; ++i) {
      if(s[i].endsWith(soundPatchExtra))continue;  
      if (s[i].toLowerCase().indexOf("publicsent3") >= 0){
        if (u.findString(s, updateCheck(s[i]+soundPatchExtra, supdates, updateFile, null))>=0)
            publicSent3Lib[tot++] = updateCheck(s[i]+soundPatchExtra, supdates, updateFile, publicSent3Lib);
        publicSent3Lib[tot++] = updateCheck(s[i], supdates, updateFile, publicSent3Lib);
      }
    }    
    
    
    for (i = 0, tot = 0; i < s.length; ++i) {
      if (s[i].toLowerCase().indexOf("publicsent4") >= 0)
        ++tot;
    }
    if (tot == 0) {
      db.create(sharkStartFrame.publicPathplus + "publicsent4");
      s = db.dblist(new File[] {publicPath});
      tot = 1;
    }
    publicSent4Lib = new String[tot];
    tot = 0;
    for (i = 0; i < s.length; ++i) {
      if(s[i].endsWith(soundPatchExtra))continue;  
      if (s[i].toLowerCase().indexOf("publicsent4") >= 0){
        if (u.findString(s, updateCheck(s[i]+soundPatchExtra, supdates, updateFile, null))>=0)
            publicSent4Lib[tot++] = updateCheck(s[i]+soundPatchExtra, supdates, updateFile, publicSent4Lib);
        publicSent4Lib[tot++] = updateCheck(s[i], supdates, updateFile, publicSent4Lib);
      }
    }        
//startPR2010-05-28^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    for (i = 0, tot = 0; i < s.length; ++i) {
//      if (s[i].toLowerCase().indexOf("publicsent4") >= 0)
//        ++tot;
//    }
//    if (tot == 0) {
//      db.create(sharkStartFrame.publicPathplus + "publicsent4");
//      s = db.dblist(new File[] {publicPath});
//      tot = 1;
//    }
//    publicSent4Lib = new String[tot];
//    tot = 0;
//    for (i = 0; i < s.length; ++i) {
//      if (s[i].toLowerCase().indexOf("publicsent4") >= 0)
//        publicSent4Lib[tot++] = s[i];
//    }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    //--------------------------------------
//    tot = 0;
//    for (i = 0; i < s.length; ++i) {
//      if (s[i].toLowerCase().indexOf("publicsigns") >= 0)
//        ++tot;
//    }
//    if (tot == 0) {
//      db.create(sharkStartFrame.publicPathplus + "publicsigns");
//      s = db.dblist(new File[] {publicPath});
//      tot = 1;
//    }
//    publicSignLib = new String[tot];
      publicSignLib = new String[]{};
//    tot = 0;
//    for (i = 0; i < s.length; ++i) {
//      if (s[i].toLowerCase().indexOf("publicsigns") >= 0)
//        publicSignLib[tot++] = s[i];
//    }
    //--------------------------------------
    tot = 0;
    for (i = 0; i < s.length; ++i) {
      if (s[i].toLowerCase().indexOf("publicfingers") >= 0)
        ++tot;
    }
    if (tot == 0) {
      db.create(sharkStartFrame.publicPathplus + "publicfingers");
      s = db.dblist(new File[] {publicPath});
      tot = 1;
    }
    publicFingerLib = new String[tot];
    tot = 0;
    for (i = 0; i < s.length; ++i) {
      if (s[i].toLowerCase().indexOf("publicfingers") >= 0)
        publicFingerLib[tot++] = updateCheck(s[i], supdates, updateFile, publicFingerLib);
    }
    
    
    
    tot = 0;
    for (i = 0; i < s.length; ++i) {
      if (s[i].toLowerCase().indexOf("publicmarkgamescourses") >= 0)
        ++tot;
    }
    if (tot == 0) {
      db.create(sharkStartFrame.publicPathplus + "publicmarkgamescourses");
      s = db.dblist(new File[] {publicPath});
      tot = 1;
    }    
    publicMarkGamesCoursesLib = new String[tot];
    tot = 0;
    for (i = 0; i < s.length; ++i) {
      if (s[i].toLowerCase().indexOf("publicmarkgamescourses") >= 0)
        publicMarkGamesCoursesLib[tot++] = updateCheck(s[i], supdates, updateFile, publicMarkGamesCoursesLib);
    }    
    
//startPR2008-06-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    vidPathPlus = sharkStartFrame.publicPathplus+u.gettext("media_","foldername")+separator+u.gettext("media_","videofolder")+separator;
    String vidfiles[] = new File(vidPathPlus).list();
    if(vidfiles != null){
      publicSignVidsNames = new String[vidfiles.length];
      for (int v = 0; v < vidfiles.length; v++) {
          String st = vidfiles[v];
          publicSignVidsNames[v] = st.substring(st.lastIndexOf(String.valueOf(separator))+1, st.lastIndexOf("."));
      }
    }
    if(shark.showPhotos){
        publicPhotoNamesFolder = shark.photoFolderPath;
        publicPhotoNamesPlusExt = new File(publicPhotoNamesFolder).list();
        publicPhotoNames =new String[]{};
        for(int imi = 0; imi < publicPhotoNamesPlusExt.length; imi++){
            int k  = publicPhotoNamesPlusExt[imi].lastIndexOf(".");
            if(k < 0)continue;
            String toadd = publicPhotoNamesPlusExt[imi].substring(0, k);        

            publicPhotoNames = u.addString(publicPhotoNames, toadd);
        }
    }
//endPRimagesandvids^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    splitwarning = u.mlabel_base("splitwarning");
//    splitwarning.setOpaque(true);
//    splitwarning.setBackground(Color.magenta);
//    splitwarning.setForeground(Color.white);
//    clicktoplay = new JLabel(u.gettext("gametree", "label"));
//    clicktoplay.setOpaque(true);
//    clicktoplay.setBackground(gamescolor);
//    clicktoplay.setForeground(Color.yellow);
//    clicktoplayi = new JLabel(u.gettext("gameicons", "label"));
//    clicktoplayi.setOpaque(true);
//    clicktoplayi.setBackground(gamescolor);
//    clicktoplayi.setForeground(Color.yellow);
    clicktoexpand = new mlabel_base(u.gettext("topictree", "label"));
    clicktoexpand.setOpaque(true);
    clicktoexpand.setBackground(topictreecolor);
    clicktoexpand.setForeground(Color.black);
//startPR2009-10-21^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    choosecourse = new mlabel_base(u.gettext("choosecourse", "label"));
    choosecourse.setOpaque(true);
    choosecourse.setBackground(gamescolor);
    choosecourse.setForeground(Color.yellow);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    samplesmess = new mlabel_base(u.gettext("superbutton", "samplesmess")); // no tooltip_base
//    samplesmesstext = samplesmess.getText();
//    samplesmess.setForeground(Color.black);
//    samplesmess.setBackground(Color.orange);
//    samplesmess2 = new mlabel_base(u.gettext("superbutton", "samplesmess2")); // no tooltip_base
//    samplesmess2text = samplesmess2.getText();
//    samplesmess2.setForeground(Color.black);
//    samplesmess2.setBackground(Color.orange);
//    clicktolisten = new JLabel(u.gettext("wordlist", "label")); // no tooltip_base
//startPR2009-10-21^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    current = new JLabel(u.convertToHtml(u.gettext("wordlist", "label")));
    current.setBackground(Color.lightGray);
    current.setOpaque(true);
    // needed rather than blank otherwise the wordlist words sometimes don't size to the panel
    // when the next list and prev list are not visible
    currentlist = new JLabel(" ");
    currentlist.setForeground(Color.black);
    currentlist.setBackground(Color.orange);
    currentlist.setOpaque(true);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    clicktolisten.setOpaque(true);
//startPR2010-05-24^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    clicktolisten.setBackground(Color.orange);
//    clicktolisten.setForeground(Color.black);
//    clicktolisten.setBackground(Color.lightGray);
//    clicktolisten.setForeground(Color.black);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2010-05-24^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    clicktolisten.addMouseListener(new java.awt.event.MouseAdapter() {
    currentlist.addMouseListener(new java.awt.event.MouseAdapter() {
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      public void mouseEntered(MouseEvent e) {
        addteachingnotes(true);
      }

      public void mouseExited(MouseEvent e) {
        if (showteachingnotes != null) {
          if (currentlist.isDisplayable() && showteachingnotes.isDisplayable()) {
            int xx = e.getX() + currentlist.getLocationOnScreen().x;
            int yy = e.getY() + currentlist.getLocationOnScreen().y;
            int xx2 = showteachingnotes.getLocationOnScreen().x;
            int yy2 = showteachingnotes.getLocationOnScreen().y;
            if (xx >= xx2 && xx <= xx2 + showteachingnotes.getWidth()
                && yy >= yy2 && yy <= yy2 + showteachingnotes.getHeight())
              return;
          }
          showteachingnotes.setVisible(false);
          showteachingnotes.dispose();
          showteachingnotes = null;
        }
      }
    });
//    gameTree.addMouseListener(new java.awt.event.MouseAdapter() {
//      public void mouseEntered(MouseEvent e) {
//        if (showteachingnotes != null) {
//          showteachingnotes.setVisible(false);
//          showteachingnotes.dispose();
//          showteachingnotes = null;
//        }
//      }
//    });
    homeusing = u.gettext("mainframe", "using");
    featurestext = u.gettext("features","label" );
//startPR2008-11-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    if(shark.network) {
//    if(shark.network || ((shark.isLicenceActivated())&&shark.singleuserMult)) {
//endPRauth^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      settitles();
//    }
//    else {
//      hometitle1 = starttitle + u.gettext("hometitles", "home1");
//      hometitle2 = starttitle + u.gettext("hometitles", "home2");
//    }
//startPR2004-08-27^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    // if running on a Macintosh
    if (shark.macOS) {
      mhome1 = u.gettext("topics_mac", "label");
      mhome2 = u.gettext("topics_mac", "label2");
    }
    // if running on Windows
    else {
      mhome1 = u.gettext("topics", "label");
      mhome2 = u.gettext("topics", "label2");
    }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    setupTitles();

  strOwlTooltip = u.gettext("mownwords", "tooltip");
  strOwlTooltipNa = u.gettext("mownwords", "tooltipna");
  strOwlTooltipNaFix = u.gettext("mownwords", "tooltipnafix");
  strMChoseTeacher = u.gettext("mchooseteacher", "label");
  strOtherWork = u.gettext("otherwork", "label");


    //-------------------------------------------
    KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(
        new KeyEventDispatcher() {
      public boolean dispatchKeyEvent(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_ESCAPE) {
//startPR2004-10-20^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          if(u.blockEscape){
            return true;
          }
//          if(currStudent <0)  {
          if(currStudent <0 && !u.focusBlock)  {
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            mainFrame.finalize();
            return true;
          }
        }
//startPR2007-11-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//        if (code == KeyEvent.VK_F1) {
        if (!Demo_base.isDemo && code == KeyEvent.VK_F1) {
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          if (e.getID() == e.KEY_PRESSED)
            setwanthelp(!wanthelp);
          return true;
        }
        else if (!Demo_base.isDemo && code == KeyEvent.VK_F11){
            if(currPicture == null){
                boolean weregameicons = gameicons;
                gameicons = false;
                if (!playingGames)
                    setupgames();
                else if(weregameicons)
                    switchdisplay();
            }
        }
        else if (!Demo_base.isDemo && code == KeyEvent.VK_F12){
           if(currPicture == null){
          if (playingGames) {
            if (!gameicons) {
              if (specialsuperlist || topicList.getCurrentTopic() != null) {
                gameicons = true;
                switchdisplay();
              }
              else
                u.okmess(u.gettext("switch","heading"),u.gettext("switch","text"), sharkStartFrame.mainFrame);
            }
          }
          else {
            gameicons = true;
            setupgames();
          }
           }
        }
//startPR2004-08-27^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        // if running on a Macintosh
//startPR2007-11-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        if (!Demo_base.isDemo && shark.macOS){
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          if ((code == KeyEvent.VK_F1) && (e.getModifiers() == MENUSHORTCUTMASK)){
            if (e.getID() == e.KEY_PRESSED) {
              setwanthelp(!wanthelp);
            }
            return true;
          }
        }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        return false;
      }

    });
    //-------------------------------------------
//startPR2007-11-30^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    if(shark.debug)u.okmess(shark.programName+" Debug", "8");
    if(shark.debug)u.okmess(shark.programName+((shark.network)?" ("+serial+")":"")+" Debug", "8", sharkStartFrame.mainFrame);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2004-12-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2008-10-31^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    this.setResizable(false);   // do full screen
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    setupsharkpanel();
//    GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
//    setMaximizedBounds(env.getMaximumWindowBounds());
//    setBounds(env.getMaximumWindowBounds());

//    validate();
 //   this.pack();

    GraphicsEnvironment env = null;
    Rectangle newResolutionRect = null;
    if(!shark.doImageScreenshots){
        if(ChangeScreenSize_base.isActive){
            newResolutionRect = new Rectangle(0, 0, newResolutionP.x, newResolutionP.y);
            setMaximizedBounds(newResolutionRect);
            setBounds(newResolutionRect);        
        }
        else{
            env = GraphicsEnvironment.getLocalGraphicsEnvironment();
            setMaximizedBounds(env.getMaximumWindowBounds());
            setBounds(env.getMaximumWindowBounds());
        }  
    }
    else{
        env = GraphicsEnvironment.getLocalGraphicsEnvironment();
            newResolutionRect = new Rectangle(0, 0, env.getMaximumWindowBounds().height, env.getMaximumWindowBounds().height);
            setMaximizedBounds(newResolutionRect);
            setBounds(newResolutionRect);         
    }

//startPR2006-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    if(shark.macOS){
//startPR2008-10-31^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR

//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    }
//    else{
//    this.setExtendedState(this.MAXIMIZED_BOTH);
//    }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2008-10-31^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    // sets the program to run full screen
//    GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
//    GraphicsDevice gd = env.getDefaultScreenDevice();
//    gd.setFullScreenWindow(this);
//    setExtendedState(this.MAXIMIZED_BOTH);
//    getLayeredPane().getComponent(1).removeMouseListener(getLayeredPane().getComponent(1).getMouseListeners()[0]);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  //  this.setVisible(true);
//startPR2008-10-31^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    this.setResizable(false);   // do full screen
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    // rb  special to test on wide screen
//    this.setExtendedState(this.MAXIMIZED_BOTH);
//    originalbounds = this.getBounds();
//    setSize(new Dimension(originalbounds.width,originalbounds.height*2/3));
    //--- end rb special -------------------------------------------------
//startPR2008-10-31^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    originalbounds = env.getMaximumWindowBounds();
    
    if(newResolutionRect!=null){
       originalbounds = newResolutionRect;  
    }
    else{
       originalbounds = env.getMaximumWindowBounds();
    }    
    
//    originalbounds = gd.getFullScreenWindow().getBounds();     // do full screen
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    this.addComponentListener(new ComponentAdapter() {
      public void componentMoved(ComponentEvent e) {
          if(!ChangeScreenSize_base.isActive || !sharkStartFrame.mainFrame.isShowing()){
            setBounds(originalbounds);
          }
         if(ChangeScreenSize_base.isActive && sharkStartFrame.mainFrame.isShowing()){
            runMovers.leftx = sharkStartFrame.mainFrame.getLocationOnScreen().x;
         }
      }
    });  
    if(!ChangeScreenSize_base.isActive)
        screenSize = env.getMaximumWindowBounds().getSize();
    //-----------------------------------------------------------------------------rb
    // get 3 full-screen buffers for runmovers


   //------------------------------------------------------------------------------rb
//startPR2007-11-30^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//     if(shark.debug)u.okmess(shark.programName+" Debug", "9", sharkStartFrame.mainFrame);
      if(shark.debug)u.okmess(shark.programName+((shark.network)?" ("+serial+")":"")+" Debug", "9", sharkStartFrame.mainFrame);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2008-06-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      if(loadprogressbar!=null){
        loadprogressbar.dispose();
        loadprogressbar = null;
      }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   String name = null;
   boolean tryAutoSignOn = false;
   String name2 = System.getProperty("user.name");
   if(name2!=null){
      name = "";
      for(int wi = 0; wi<SignOn_base.maxchar && wi < name2.length(); wi++){
          char c;
          if(u.notAllowedInFileNames.indexOf(c=name2.charAt(wi)) >= 0){
              name += "_";
          }
          else name += String.valueOf(c);
      }
   }
    String exclusions[] = (String[])db.find(sharkStartFrame.optionsdb, "autosignonexclusions", db.TEXT);
    for(int n = 0; exclusions!=null && n < exclusions.length; n++){
        exclusions[n] = exclusions[n].toLowerCase();
    }
    if((exclusions==null || u.findString(exclusions, name.toLowerCase())<0) &&
           db.query(optionsdb,"autosignon_",db.TEXT) >= 0
       && name != null
       && name.length() > 0){
        name = u.stripspaces2(name);
       if(db.query(optionsdb,"mautosigncreatestu_",db.TEXT) >= 0 && student.getStudent(name)==null){
            student stu = new student();
            stu.name = name;
            String dbpath = sharkStartFrame.sharedPath.toString();
            String dbname = dbpath + sharkStartFrame.sharedPath.separatorChar+stu.name;
            db.create(dbname);
            stu.saveStudent();
            student.checkadmin();
        }
        tryAutoSignOn = true;
   }

//    validate();
    if(!ChangeScreenSize_base.isActive || !shark.macOS)
        setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
    if(ChangeScreenSize_base.isActive){
        setPreferredSize(new Dimension((int)originalbounds.getWidth(),(int)originalbounds.getHeight())); 
        setMinimumSize(new Dimension((int)originalbounds.getWidth(),(int)originalbounds.getHeight())); 
    }
    this.pack();
    setLocationRelativeTo(null);
    runMovers.buffers[0] =  createImage(screenSize.width, screenSize.height);
    runMovers.buffers[1] =  createImage(screenSize.width, screenSize.height);
    runMovers.buffers[2] =  createImage(screenSize.width, screenSize.height);
    if(shark.macOS)this.setVisible(true);
    if(ChangeScreenSize_base.isActive){
        student.autoSignon(newResolutionUser, true);
    }  
    else{
        if(!(tryAutoSignOn && student.autoSignon(name))){
          student.signOn(sharkPanel);
          buildgamepanel1x();
          phtype = 0;
        }
    }
    if(!shark.production && shark.testing){
        diagnostics.doTest();
    }
    if(!shark.macOS) this.setVisible(true);
    try {
      runMovers.leftx = this.getLocationOnScreen().x;
      if (runMovers.leftx < 0)
        runMovers.leftx = 0;
      else if (runMovers.leftx > 0)
        ++runMovers.leftx;
    }
    catch(IllegalComponentStateException e) {runMovers.leftx = 0;}
    if(ChangeScreenSize_base.isActive){
        this.setLocation(0,0);
    }
//startSS2005-09-12^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
       //Do a scheduled back up iRf one is needed
    new Backup_base(false);
    
    ToolsOnlineResources tor = new ToolsOnlineResources();
    
    
//    String sim = tor.getImageFileID("C:\\Test\\IPHOTO_starttime.png");
    
  //  tor.renameWithZeros();
  //  tor.renameRuthRecordings();
//    tor.makeDummyRecordings();
//      tor.checkRecordings();
int ht = 0;
 //   tor.compareJsonFiles("D:\\NetBeansProjects\\jbproject6_3\\Release\\Wordshark 6\\wordshark-public\\json\\recordings.json",
 //           "D:\\Dropbox\\Online Masters\\Builder Database Tables\\history\\Wordshark Test\\2019-01-29_wt00002_68\\json\\recordings.json"
  //          );p
 // tor.checkRecordingsAgainstJson();
 //   tor.commentPngs("D:\\T\\", "MattMarcroft");
 //   tor.giffingMakeVideoGifs();
 //   tor.noOfWordListsPerUnit("Wordshark course");
 //  ToolsOnlineResources tor = new ToolsOnlineResources();
 //  tor.renameToOnlineNameImages(false);
   
  // tor.addIDsToImageJson();
  // tor.findUnmatchedSimpleSentences2();
  // tor.checkRecordingsForOnlineNameDuplicates();
 // tor.makeDummyRecordings();
//  tor.makeFindDummyRecordings("C:\\Users\\paulr\\Documents\\NetBeansProjects\\jbproject6_3\\Release\\Wordshark 6\\wordshark-public\\mp3\\");
 //  tor.makeTransparentBG_PNGs();
 //  tor.renameToOnlineNameImages();
 
 //  tor.checkRecordingsAgainstJson();
// tor.checkImagesExists();
 //  tor.writeOutSentences2();
 //  Tools tol = new Tools();
 //  tol.publicSplitsContents();
 //  tol.adjustPublicSplits();
 //  System.out.println("-----------------------**************************----------------------------");
 //  tol.publicSplitsContents();
 //  tor.getImageFileID("C:\\Users\\paulr\\Dropbox\\PhotographicResourcesForOnlineIWS\\IWS_5zeros.png");
 
  // System.out.println(u.syllabletot("s"));
//  tor.makeTransparentBG_PNGs();

//  tor.giffingMakeGifs(true, true);
// tor.renameToOnlineNameImages(true);
//  tor.lookupAndCopyToOnlinePhoto(false);


 // tor.removeExtraExtensions();
 // tor.checkResourcesExists(true, true);
 //tor.checkAllImages(tor.filePublicImages);  
// tor.addFileIDToIWSJson();
// tor.replaceFileIDInIWSJson();
 // tor.saveAllImages();
  //tor.checkAllImages(null);
 // tor.compareTwoTextLists("D:\\ClaireGot.txt", "D:\\ClaireAsked.txt", ".mp3");
//  tor.checkRecordings();
//tor.renamePhotoToOnlineNameImages();
//  tor.giffingPhotosMakeGifs();
//tor.renameRecordings3();
   int h;
    h =0;
 //   tor.textFilesToJason();
//     tor.jsonToHTMLRecordings("C:\\jshark-shared\\publictopicsPrint\\", "C:\\jshark-shared2\\publictopicsPrint\\", "D:\\DummyOutput", false);
 //  tor.jsonImageToCsv();
   
  // System.out.println("test 1");
 //  tor.compareSSent();
 // tor.copyRenameRecordings2(ToolsOnlineResources.recordingFiles);  
 //tor.selectUsedPhotoImages();
 //  tor.dirToLowerCaseNames();
  // Tools tt = new Tools();
  // tt.moveWSImages();
  //tor.makeTransparentBG_PNGs();
    int rh;
    rh = 0;
  
//   tor.saveAllRecordings();
  //  tor.gretchenNotUsed();
 //   tor.moveImageFiles();
 //   tor.productDesktopRecordings();
//    tor.renameRecordings();
 //   tor.organiseSoundlessRecordings();
 //    tor.copyRenameRecordings3(ToolsOnlineResources.recordingFiles);   
  //  tor.copyRenameRecordings2(ToolsOnlineResources.recordingFiles);
 //   tor.copyRenameRecordings2(new String[]{"publicsay1"});
 
 
  //  tor.renameRecordingstoZeros();
//  tor.renameRecordings2();
    
 //   int hh; 
 //   hh = 0;
 //  tor.writeOutJsonRecordings("C:\\jshark-shared2\\publictopicsPrint\\recordings.json", null, new String[]{"publicsay1", "publicsay3", "publicsent1", "publicsent2", "publicsent3"}, 
 //           false, false, true, false);   

    //   String pi[] = db.list("publicimage", db.IMAGE);
 //   String pi2[] = db.list("publicimageWORDS", db.IMAGE);
 //   String ss[]  = tor.findDuplicates(pi, pi2);
 //   int h; 
 //   h = 0;


//    Tools tt = new Tools();
//    tt.removeMarkGames();
//    tt.fillMarkGamesFromFile();
  //  tt.listWordLists("Wordshark Course", new String[]{"Nat.Curriculum 2014 England:spellings","'Alpha to Omega'"});
  //  int g;
//    int g = 0;
    
    
    
    /*
    ArrayList al = new ArrayList();
    ArrayList alinner = new ArrayList();
    String settings[] = new String[]{"maze-size=0", "snakes=3", "sharks=1"};
    alinner.add(314);
    alinner.add(settings);
    ArrayList alinner2 = new ArrayList();
    String settings2[] = new String[]{"ma222ze-size=2", "sn222akes=3", "sh222arks=4"};
    alinner2.add(567);
    alinner2.add(settings2);
    al.add(alinner);
    al.add(alinner2);
    
    
    String fds = tt.getWordListGameOptionSettings(al);
    int rh;
     rh = 0;
    */
//endSS^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
//startPR2007-05-17^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//     if(shark.debug)u.okmess(shark.programName+" Debug", "9", sharkStartFrame.mainFrame);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
 }
  
  public static String updateCheck(String oriFile, String updatesList[], File updatePathF, String[] existingLibrary){
      if(updatePathF==null)return oriFile;
      String updatePath = updatePathF.getAbsolutePath();
      if(updatePath==null)return oriFile;
      String ss[] = u2_base.splitString(oriFile, separator);
      oriFile = ss[ss.length-1]; 
      if(u2_base.findString(updatesList, oriFile)>=0){
          String sres = updatePath+ separator + oriFile;
          if(existingLibrary==null || u2_base.findString(existingLibrary, sres, true)<0)
            return sres;
      }
      return oriFile;
  }  
  
    void setFontSizeBasedOnScreen(){
        MAXFONTPOINTS_1 = u2_base.adjustMaxFontSizeToResolution(60);     // WAS 30
        MAXFONTPOINTS_2 = u2_base.adjustMaxFontSizeToResolution(60);     // WAS 40 - keep distiction Roger put in - a different maxFont used in different places.
        MAXFONTPOINTS_3 = u2_base.adjustMaxFontSizeToResolution(60); 
        MAXFONTPOINTS_4 = u2_base.adjustMaxFontSizeToResolution(400);      
        FontChooser.pointSizeMax = sharkStartFrame.MAXFONTPOINTS_2;        //MAXIMUM FONT POINT SIZE
    }
    
//startPR2010-03-17^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  String checkGetLicence(){
      File f = new File(sharedPathplus+u.gettext("backup_","WS")+"backups");
      File fs[] = f.listFiles();
      if(fs==null)return null;
      String s;
      for(int i = 0; i < fs.length; i++){
          if(fs[i].isDirectory()){
              s = fs[i].getAbsolutePath()+separator+"options";
              if(new File(s+".sha").exists()){
                  if(((s = (String)db.find(s, "kkkk", db.TEXT))!=null)){
                    if((users = getusers(serial, school, s)) >= 0){
                        db.update(optionsdb, "kkkk", s, db.TEXT);
                        return s;
                    }
                  }
              }
          }
      }
      return null;
  }
//endPRtoupdate^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  
  void setupTitles(){
       String prod = null;
       if(shark.licenceType.equals(shark.LICENCETYPE_NETWORKACTIVATION)){
           prod = shark.getVersionType(shark.licenceType, true); 
       }
       else{
           prod = shark.getVersionType(shark.singledownload?shark.LICENCETYPE_STANDALONEACTIVATION_HOME:shark.licenceType, false);
       }
       
       starttitle = u.gettext("mainframe", "title", shark.versionNo + " " + "(" + prod + ")");    
    pictitle = starttitle+starttitlespace+u.gettext("hometitles", "pictures");
    topicstitle = starttitle+starttitlespace+u.gettext("hometitles", "topics");
    gamestitle = starttitle+starttitlespace+u.gettext("hometitles", "games");
    texttitle = starttitle+starttitlespace+u.gettext("hometitles", "text");
    saytitle = starttitle+starttitlespace+u.gettext("hometitles","copywav");
    recwordstitle = starttitle+starttitlespace+u.gettext("hometitles", "recwords");
    recsenttitle = starttitle+starttitlespace+u.gettext("hometitles", "recsent");
    recdeftitle = starttitle+starttitlespace+u.gettext("hometitles", "recdef");
    recprivatewordstitle = starttitle+starttitlespace+u.gettext("hometitles", "recprivatewords");
    privatetopicstitle = starttitle+starttitlespace+u.gettext("hometitles", "privatetopics");
    addstutitle = starttitle+starttitlespace+u.gettext("hometitles", "addstu"); 
  }   
  
  /**
   * Constructs and positions the sharkpanel.
   * This is the panel that is seen initially when the program is started
   */
  static void settitles() {
    String use = "";
    String sss = "";
    if(sharkStartFrame.studentList!=null && sharkStartFrame.studentList.length>0){
        String otherstu = "";
        if(sharkGame.otherplayer!=null){
            otherstu = u.gettext("mainframe", "and") + " " +sharkGame.otherplayer.name+ " ";
        }
        use = sharkStartFrame.studentList[sharkStartFrame.currStudent].name + " " + otherstu +
               homeusing + " ";
    }
//    if(!shark.network && !shark.licenceType.equals(shark.LICENCETYPE_STANDALONEACTIVATION)){
//        hometitle1 = starttitle + starttitlespace+  u.gettext("hometitles", "home1");
//        hometitle2 = starttitle + starttitlespace+  u.gettext("hometitles", "home2");
//    }
//    else{    
        String exp = "";
        if(expiry!=null){
            exp = u.gettext("webauth","expires")+" "+expiry.substring(0,2)+"/"+expiry.substring(2,4)+"/"+expiry.substring(4);
        }
        if(shark.licenceType.equals(shark.LICENCETYPE_NETWORK)){
            sss = u.edit(u.gettext("netkey","licences3"),school,String.valueOf(users));
        }
        else if(shark.licenceType.equals(shark.LICENCETYPE_NETWORKACTIVATION) && school!=null){
            sss = u.edit(u.gettext("netkey","licences3"),school+ (location==null||location.equals("")?"":" - "+location),String.valueOf(users))+" - " + exp;            
        }
        else if(shark.licenceType.equals(shark.LICENCETYPE_STANDALONEACTIVATION) && !shark.singledownload){
            sss = u.edit(u.gettext("netkey","licences4"),school+ (location==null||location.equals("")?"":" - "+location))+" - " + exp;        
        }
        else if(shark.singledownload){
            sss = exp;        
        }
        hometitle1 = starttitle + (sss.equals("")?"":starttitlespace)+ sss+ starttitlespace+  u.gettext("hometitles", "home1");
        hometitle2 = starttitle + (sss.equals("")?"":starttitlespace)+ sss+ starttitlespace+  u.gettext("hometitles", "home2");
//    }

  }
  void setupsharkpanel() {
//startPR2010-05-04^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    sharkPanel = new runMovers();
    sharkPanel = new runMovers();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    sharkPanel.usepool = true;
//startPR2010-05-04^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    sharkPanel.waitforsay = true;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    sharkPanel.freeze = true;
    sharkPanel.onmainscreen = true;
    sharkPanel.sort3dmovers = true;
    sharkPanel.refreshat = System.currentTimeMillis() + 3000;
    grid1.gridwidth = 1;
    grid1.weightx = 2;
    grid1.gridx = grid1.gridy = 0;
    bevelPanel1.add(sharkPanel, grid1);
    addKeyListener(signonkey = new KeyAdapter() {
      public void keyPressed(KeyEvent e) {
        sharkPanel.keypressed(e);
      }

      public void keyTyped(KeyEvent e) {
        sharkPanel.keytyped(e);
      }
    });
  }

  /**
   * Component initialization:-
   * <li>Sets menus
   * <li>Sets fonts
   * @throws java.lang.Exception
   */
  public void jbInit() throws Exception {
    this.addWindowListener(new java.awt.event.WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        if (!playingGames && currStudent >= 0){
          if(updatingTopics){
              if (currTopicView != null && currTopicView.changed) {
                  int res = u.yesnocancel(shark.programName, "Do you want to save any changes?", sharkStartFrame.mainFrame);
                  if(res<0)return;
                  else if(res==0)currTopicView.save();
              }
              if(markgamescoursetree!= null){
                  saveMarkCourseChanges();
              }
          }
          setupgames();
        }
        else{
          if(Demo_base.isDemo)System.exit(0);
          else this_windowClosing(e);
        }
      }

      public void windowActivated(WindowEvent e) {
        if(childFrame!=null){
            childFrame.dispose();
            childFrame = null;
        }
//startPR2007-11-26^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        if(wordTree!=null)
          wordlist.current = wordTree;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        if (gametot > 0) {
//startPR2004-10-06^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          // so that the printframe in sentences is displayed properly
          if(!u.macBlock)
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            runningGame.currGameRunner.game.setVisible(true);
//startPR2004-08-27^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        //            runningGame.currGameRunner.game.setState(Frame.NORMAL);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       }
       else {
//startPR2008-06-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         if(shark.macOS && !Demo_base.isDemo && !sharkStartFrame.mainFrame.getJMenuBar().equals(manBar1)){
           sharkStartFrame.mainFrame.setJMenuBar(manBar1);
         }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         manBar1.validate();
         manBar1.repaint();
//startPR2004-10-19^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         // without this on the Mac the games icon don't move after escaping from a game
         if (gameicons && gamePanel!=null) {
           gamePanel.haltrun = gamePanel.dontstart = false;
           gamePanel.startrunning();
         }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2008-06-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         if(cdcheckdue){
           cdcheckdue = false;
            Thread copyProtectThread = new Thread(new copyProtect_Work());
            copyProtectThread.start();
              //checkcd();
         }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        }
//startPR2006-02-23^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//        if(Demo_base.isDemo){
//          if (viewWordListOn) {
//            removeWordLists();
//            viewWordListOn = false;
//          }
//          if (Demo_base.showWordListInfo && !Demo_base.shownListInfo) {
//            String phrase = "wordlistinfo";
//            dem.makeDialog(Demo_base.demogettext("democdprogramtitle", "titleshort", false),
//                           Demo_base.demogettext("popupdialogs", phrase, true),
//                           phrase,
//                           sharkStartFrame.mainFrame, Demo_base.DIALOGWORDLISTRIGHT, true, false);
//            Demo_base.shownListInfo = true;
//            Demo_base.showWordListInfo = false;
//          }
//        }
//endPRdemo^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      }
    });
    
    this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    studentrecord.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        studentrecord_actionPerformed(e);
      }
    });
    mprintflash.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
           if(playingGames && currPlayTopic != null) {
             wordTree.printlist(currPlayTopic.name,wordTree.FLASH);
        }
      }
    });
    mprintflash2.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
           if(playingGames && currPlayTopic != null) {
             wordTree.printlist(currPlayTopic.name,wordTree.FLASH2);
        }
      }
    });
    mprint1.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
           if(playingGames && currPlayTopic != null) {
             wordTree.printlist(currPlayTopic.name,wordTree.PRINT1);
        }
      }
    });
    mprint2.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
           if(playingGames && currPlayTopic != null) {
             wordTree.printlist(currPlayTopic.name,wordTree.PRINT2);
        }
      }
    });
    mprint4.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
           if(playingGames && currPlayTopic != null) {
             wordTree.printlist(currPlayTopic.name,wordTree.PRINT4);
        }
      }
    });
    mprint8.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
           if(playingGames && currPlayTopic != null) {
             wordTree.printlist(currPlayTopic.name,wordTree.PRINT8);
        }
      }
    });
//startPR2007-02-16^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    mprintex.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if(playingGames && currPlayTopic != null) {
          wordTree.printlist(currPlayTopic.name,wordTree.PRINTEX);
        }
      }
    });
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    mprintfg.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          Color newColor = JColorChooser.showDialog(sharkStartFrame.mainFrame,
                                        u.gettext("printlistcolor", "label"),
                                         student.printfg());
          if(newColor != null) {
             studentList[currStudent].printfg = newColor;
             mprintfg.setForeground(student.printfg());
          }
        }
    });

//startPR2007-05-17^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    mexplore.addActionListener(new java.awt.event.ActionListener() {
//        public void actionPerformed(ActionEvent e) {
//          try {
//            Runtime.getRuntime().exec(new String[]{"explorer.exe",
//                                       shark.publicPath.substring(0, shark.publicPath.lastIndexOf(separator))});
//          }
//          catch(Exception ee){}
//        }
//    });

    mexplore.addActionListener(new java.awt.event.ActionListener() {
      String helpfolder = publicPathplus+u.gettext("helpfolder", "helpfolder", File.separator);
        public void actionPerformed(ActionEvent e) {
          try {
            if(shark.macOS)
              Runtime.getRuntime().exec(new String[]{"open",helpfolder});
            else
              Runtime.getRuntime().exec(new String[]{"explorer.exe",helpfolder});
          }
          catch(Exception ee){}
        }
    });
    mexplore2.addActionListener(new java.awt.event.ActionListener() {
      String helpfolder = publicPathplus+u.gettext("helpfolder2", "helpfolder", File.separator);
        public void actionPerformed(ActionEvent e) {
          try {
            if(shark.macOS)
              Runtime.getRuntime().exec(new String[]{"open",helpfolder});
            else
              Runtime.getRuntime().exec(new String[]{"explorer.exe",helpfolder});
          }
          catch(Exception ee){}
        }
    });
    if (shark.language.equals(shark.LANGUAGE_NL)) {
        mmusic.addActionListener(new java.awt.event.ActionListener() {
          String musicfolder = publicPathplus+u.gettext("helpfolder", "musicfolder");
            public void actionPerformed(ActionEvent e) {
              try {
//                if(shark.macOS)
//                  Runtime.getRuntime().exec(new String[]{"open",musicfolder});
//                else
//                  Runtime.getRuntime().exec(new String[]{"explorer.exe",musicfolder});
                  if(shark.macOS){
                      Runtime.getRuntime().exec(new String[] {"open",
                      musicfolder + shark.sep+ "startMeMac.app/Contents/MacOS/Flash Player"});
                  }
                  else{
                      u.launchFile(musicfolder + shark.sep+ "startMe.exe", false);
                  }

              }
              catch(Exception ee){}
            }
        });
       }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2008-01-23^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    mglossary.addActionListener(new java.awt.event.ActionListener() {
//        public void actionPerformed(ActionEvent e) {
//          int w = sharkStartFrame.screenSize.width/2;
//          int h = sharkStartFrame.screenSize.height*7/8;
//startPR2009-12-14^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//          u.showtext(u.gettext("mglossary","title"),
//          u.showtext(sharkStartFrame.mainFrame, u.gettext("mglossary","title"),
//endPRauth^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//                  u.edit(u.gettext("mglossary","text"),treefont.getName(), String.valueOf(treefont.getSize()-1)),
//                     new Rectangle(10,10,w,h));
//        }
//    });
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2007-09-03^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    msupport.addActionListener(new java.awt.event.ActionListener() {
//        public void actionPerformed(ActionEvent e) {
//          String s = u.gettext("msupport","site");   // "http://www.wordshark.co.uk";
//          try{
//            if(shark.macOS){
//              Class fileMgr = Class.forName("com.apple.eio.FileManager");
//              fileMgr.getDeclaredMethod("openURL",
//                    new Class[] {String.class}).invoke(null, new Object[] {s});
//            }
//            else{
//                String[] cmd = new String[] {"rundll32", "url.dll", "FileProtocolHandler", s};
//                Runtime.getRuntime().exec(cmd);
//            }
//          }
//          catch(Exception ee){
//          }
//        }
//    });
    msupport.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          u.launchWebSite(u.gettext("msupport","site"));
        }
    });

    mupdatescheck.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
            String httpaddress = u.gettext("webauthenticate", "httpaddress");
            String str_serviceaccess = u.gettext("webauthenticate", "serviceaccess");
            WebCall_base wc;
            String methods[];
            String values[];
            String methodname;
            if(shark.licenceType.equals(shark.LICENCETYPE_NETWORKACTIVATION)){
                methodname = "GetPatchNoNetType";
                String networkExtra = shark.network_serverside?"Server":"Client"+(shark.network_RM?"RM":"");
                methods = new String[]{"currno", "ver", "type", "extra"};
                values = new String[]{shark.versionNoDetailed,
                    shark.ACTIVATE_PREFIX, shark.licenceType, networkExtra};
            }
            else {
                methodname = "GetPatchNo";
                methods = new String[]{"currno", "ver", "type"};
                values = new String[]{shark.versionNoDetailed,
                    shark.ACTIVATE_PREFIX, shark.licenceType};
            }
            Thread aThread = new Thread(wc = new WebCall_base(httpaddress, WebAuthenticate_base.servicePatch, "Service1.asmx",
               str_serviceaccess,
               methodname, methods, values, WebAuthenticate_base.waittime));
              
            aThread.start();
            while(aThread.isAlive()){
                u.pause(200);
            }     
            if(wc.returnval == null){
                OptionPane_base.doPatch(sharkStartFrame.mainFrame, OptionPane_base.PATCH_USER_NOCONNECT);
            }
            else if(wc.returnval.equals("0")) {
                OptionPane_base.doPatch(sharkStartFrame.mainFrame, OptionPane_base.PATCH_USER_NOUPDATES);
            }
            else {
                OptionPane_base.doPatch(sharkStartFrame.mainFrame, OptionPane_base.PATCH_USER_UPDATE);
            }            
        }
    });
    mupdatescheckall.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (mupdatescheckall.isSelected())
          db.update(optionsdb, "updatescheckall", new String[] {""}
                    , db.TEXT);
        else
          db.delete(optionsdb, "updatescheckall", db.TEXT);
      }
    });
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    print.addActionListener(new sharkStartFrame_print_actionAdapter(this));
    if (shark.specialfunc) {
      findreplace.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          findreplace();
        }
      });
       importsentences.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
            JFileChooser fc = new JFileChooser();
            fc.setAcceptAllFileFilterUsed(false);
            fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fc.setFileFilter(new javax.swing.filechooser.FileFilter() {
              public boolean accept(File f) {
                  String s = f.getName();
                  String ss = s.substring(s.lastIndexOf(".") + 1);
                  if (f.isDirectory() ||
                      ss.equalsIgnoreCase("txt")) {
                    return true;
                  }
                  return false;
              }
              public String getDescription() {
                 return ".txt files";
              }
            });
            int returnVal = fc.showOpenDialog(sharkStartFrame.mainFrame);
            if (returnVal == fc.APPROVE_OPTION) {
              String path = fc.getSelectedFile().getAbsolutePath();
              String ss[] = u.readFile(path);
              for(int i = 0 ; i < ss.length; i++){
                  boolean isTopicName = !ss[i].startsWith("\t");
                  if(isTopicName){
                    String topicName = u.stripTxtTrailingTabs(ss[i]).trim();
                    Object o = db.find("publictopics",topicName,db.TOPIC);
                    if(o == null || !(o instanceof saveTree1)){
                        System.out.println("Topic not found: "+topicName);
                    }
                    else{
                        saveTree1 stw = (saveTree1)db.find("publictopics",topicName,db.TOPIC);
                        int newLevel = -1;
                        String existingSentences[] = new String[0];
                        for(int j = 0 ; j < stw.curr.names.length; j++){
                            if(stw.curr.names[j].startsWith("Games:Sentence crossword")){
                                newLevel = stw.curr.levels[j+1];
                                for(int k = j+1; k < stw.curr.names.length; k++){
                                    if(stw.curr.levels[k] != newLevel)break;
                                    if(!stw.curr.names[k].startsWith(topic.types[topic.SELECTDISTRACTORS])){
                                        existingSentences = u.addString(existingSentences, stw.curr.names[k]);
                                    }
                                }
                            }
                        }
                        String sentencesToImport[] = new String[0];
                        for(int k = i+1; k < ss.length; k++){
                            if(!ss[k].startsWith("\t")){
                                break;
                            }
                            else{
                                sentencesToImport = u.addString(sentencesToImport, ss[k].substring(1));
                            }
                        }
                        topic ret = new topic("publictopics",topicName,null,null);
                        for(int k = 0; k < sentencesToImport.length; k++){
                            if(u.findString(existingSentences, sentencesToImport[k])< 0){
                                String sent = sentencesToImport[k];
                              sent = u.sentenceConvertBreaks(sent);
                              sent = u.sentenceAddBreaks(sent, 21);
                              ret.addChild(ret.find("Games:Sentence crossword"), sent);
                              ret.changed = true;
                            }
                        }
                        if(ret.changed){
                            db.update("publictopics",topicName,(new saveTree1(ret,ret.root)).curr,db.TOPIC);
                        }
                    }                
                  }
              }
            }            
        }
      });     
    
      if (publicTextLib.length >= 2)
        mergetext.addActionListener(new java.awt.event.ActionListener() {
          public void actionPerformed(ActionEvent e) {
            db.mergetext(publicTextLib[0], publicTextLib[1]);
          }
        });
      if (db.exists("publicsay2"))
        mergesay.addActionListener(new java.awt.event.ActionListener() {
          public void actionPerformed(ActionEvent e) {
            db.mergewav("publicsay1", "publicsay2");
          }
        });
      if (db.exists("publicsent1x"))
        mergesent1.addActionListener(new java.awt.event.ActionListener() {
          public void actionPerformed(ActionEvent e) {
            db.mergewav("publicsent1", "publicsent1x");
          }
        });
      if (db.exists("publicsent2x"))
        mergesent2.addActionListener(new java.awt.event.ActionListener() {
          public void actionPerformed(ActionEvent e) {
            db.mergewav("publicsent2", "publicsent2x");
          }
        });
      if (publicImageLib.length >= 2)
        mergeimage.addActionListener(new java.awt.event.ActionListener() {
          public void actionPerformed(ActionEvent e) {
            db.mergeimage(publicImageLib[0], publicImageLib[1]);
          }
        });
      if (db.exists("publicimage2") && db.exists("publicimagew"))
        mergeimage23.addActionListener(new java.awt.event.ActionListener() {
          public void actionPerformed(ActionEvent e) {
            db.mergeimage23();
          }
        });
//startPR2008-06-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      if (new File(sharedPathplus+"mergeimages").exists()&&new File(sharedPathplus+"mergeimages").list().length>1){
        mergemultimage.addActionListener(new java.awt.event.ActionListener() {
          public void actionPerformed(ActionEvent e) {
            Calendar cal = new GregorianCalendar();
            String fname = "publicimage"+cal.get(Calendar.YEAR)+"-"+cal.get(Calendar.MONTH)
                +"-"+cal.get(Calendar.DAY_OF_MONTH)+"_"+cal.get(Calendar.HOUR_OF_DAY)+"h-"+cal.get(Calendar.MINUTE)+"m";
            db.mergeallimages(fname);
          }
        });
      }
      if(mextrasoundpatches!=null){
        mextrasoundpatches.addActionListener(new java.awt.event.ActionListener() {
          public void actionPerformed(ActionEvent e) {
              File fsoundx;
              if((fsoundx = new File(sharedPathplus+"soundextraold")).exists()){
                  Diagnostics_base diagnostics = new Diagnostics_base();
                  File files[] = fsoundx.listFiles();
                  for(int i = 0; i < files.length; i++){
                    String fname = files[i].getName();
     //               fname = fname.substring(0, fname.length()-7)+fname.substring( fname.length()-4);
                    String newp = sharedPathplus+"soundextranew" + shark.sep + fname;
                    File fx2;
                    if(((fx2 = new File(newp)).exists())){
                        diagnostics.createSoundExtraPatches(files[i], fx2);
                    }
                  }
              }
          }
        });
      }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      if (db.exists("publictopics2"))
        mergetopics.addActionListener(new java.awt.event.ActionListener() {
          public void actionPerformed(ActionEvent e) {
            closeprev(true);
            db.mergetopics("publictopics", "publictopics2");
            topicTreeList = null;   // force rebuild
            setupgames();
          }
        });
      compresspublic.addActionListener(new java.awt.event.ActionListener() {
       public void actionPerformed(ActionEvent e) {
         closeprev(true);
         findword ff = new findword(null);
         ff.dispose();
         db.closeAll();
          String list[] = db.dblist(publicPath,"public");
         db.forcecompress = true;
         for(int i=0;i<list.length;++i) {
           db nn = new db(list[i],true);
           nn.close();
         }
         db.forcecompress = false;
         setupgames();
       }
     });
     newdb.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          JOptionPane getn = new JOptionPane(
              u.splitString(u.gettext("newdb", "text")),
              JOptionPane.PLAIN_MESSAGE,
              JOptionPane.OK_CANCEL_OPTION);
          getn.setWantsInput(true);
          JDialog dialog = getn.createDialog(sharkStartFrame.mainFrame,
                                              u.gettext("newdb", "title"));
          dialog.setVisible(true);
          Object result = getn.getValue();
          String newname = (String) getn.getInputValue();
          if (result != null && newname.length() > 0)    db.create(publicPathplus + newname);
        }
      });
    }
//startPR2004-10-22^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    mnogamble.addActionListener(new java.awt.event.ActionListener() {
//      public void actionPerformed(ActionEvent e) {
//        if (mnogamble.getState())
//          studentList[currStudent].setOption("nogamble");
//        else
//          studentList[currStudent].clearOption("nogamble");
//      }
//    });
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    mnoreward.addActionListener(new java.awt.event.ActionListener() {
//      public void actionPerformed(ActionEvent e) {
        // start rb 27/10/06
//       if(mnoreward.getState()) {
//         int i;
//         studentList[currStudent].setOption("noreward");
//         for (i = 0; i < 4; ++i) {
//           mrewardfreqs[i].setState(false);
//         }
//       }
//       else {
//         mnoreward.setSelected(true);   // can only be set on
//       }
       // end rb 27/10/06
//      }
//    });
//    mteachnotes.addActionListener(new java.awt.event.ActionListener() {
//      public void actionPerformed(ActionEvent e) {
//        if (!mteachnotes.getState())
//          studentList[currStudent].setOption("noteachnotes");
//        else
//          studentList[currStudent].clearOption("noteachnotes");
//      }
//    });
    mnomove.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (mnomove.getState())
          studentList[currStudent].setOption("nomove");
        else
          studentList[currStudent].clearOption("nomove");
//        gamePanel.frozen = mnomove.getState();
        for (int i = 0; i < gameiconlist.length; ++i) {
          if (gameiconlist[i] != null)
            gameiconlist[i].frozen = mnomove.getState();
        }
      }
    });
    prevlist.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jnode old = topicList.getSelectedNode();
        if (old != null) {
          jnode prev = (jnode) old.getPreviousLeaf();
          if (prev != null) {
            if(wantplayprogram && prev.getLevel() > 2 ) {  //  rb 16/3/08  mmmm  start
              jnode pa = prev;
              do {pa = (jnode)pa.getParent();} while (pa.getLevel() > 2);
              if(pa.dontexpand) prev = pa;
            }                                               //  rb 16/3/08  mmmm end
//startPR2007-12-02^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//            if(prev.get().trim().equals(""))prev=(jnode)prev.getPreviousLeaf();
//            if(prev == null) return;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            usingsuperlist = null;
            specialsuperlist = false;
            useselectedlist = false;
            forcechange = true;
//startPR2010-05-24^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//        topicList.setSelectionPath(new TreePath(prev.getPath()));
            TreePath tp = new TreePath(prev.getPath());
            topicList.setSelectionPath(tp);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            canshowmarkedgames = false;
            setupGametree();
            addteachingnotes(false);
//startPR2010-05-24^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            bevelPanel4.validate();
            topicList.scrollPathToVisible(tp);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          }
        }
      }
    });
//startPR2010-05-24^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    nextlist.setCursor(handcursor);
    prevlist.setCursor(handcursor);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    nextlist.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if(refreshprog()) return;
        tonext();                     //       rb 22/02/08 v5
      }
    });
    addstudent.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if(refreshprog()) return;
        student teacher = studentList[currStudent];
        if (teacher.administrator || teacher.teacher ) {
          admin aa = new admin();
          aa.setTitle(addstutitle);
        }
      }
    });
    universal.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
            new mainsettings();
      }
    });

    addstudent2.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if(refreshprog()) return;
        student teacher = studentList[currStudent];
        if (teacher.administrator  || teacher.teacher) {
          admin aa = new admin();
          aa.setTitle(addstutitle);
        }
      }
    });
    {
//      mgameicons.addActionListener(new java.awt.event.ActionListener() {
//        public void actionPerformed(ActionEvent e) {
//          if(currPicture != null) return;
//          if (playingGames) {
//            if (!gameicons) {
//              if (specialsuperlist || topicList.getCurrentTopic() != null) {
//                gameicons = true;
//                switchdisplay();
//              }
//              else
//startPR2004-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//                   u.okmess(u.gettext("switch","heading"),u.gettext("switch","text"));
//                u.okmess(u.gettext("switch","heading"),u.gettext("switch","text"), sharkStartFrame.mainFrame);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//            }
//          }
//          else {
//            gameicons = true;
//            setupgames();
//          }
//        }
//      });
    }
    changepassword.addActionListener(new
        sharkStartFrame_changepassword_actionAdapter(this));
    choosesprite.addActionListener(new
                                   sharkStartFrame_choosesprite_actionAdapter(this));

    chooseicon.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (gamePanel!=null && gamePanel.tooltipmover1 != null) {
          gamePanel.removeMover(gamePanel.tooltipmover1);
          gamePanel.tooltipmover1 = null;
        }
        childFrame =new PickPicture(false,new String[]{studentList[currStudent].name}, studentList[currStudent].name, true);
        sharkStartFrame.mainFrame.setState(ICONIFIED);
      }
    });

    picpref.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
          stupicpref  spp = new stupicpref();
            
      }
    });


    if (shark.specialfunc) {
        imageinfo.addActionListener(new sharkStartFrame_ImageInfo_actionAdapter(this));
      record.addActionListener(new sharkStartFrame_record_actionAdapter(this));
      recordfl.addActionListener(new sharkStartFrame_recordfl_actionAdapter(this));
      undo.addActionListener(new sharkStartFrame_undo_actionAdapter(this));
      save.addActionListener(new sharkStartFrame_save_actionAdapter(this));
    }
    menuFileExit.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        mainFrame.finalize();
      }
    });
    muserlist.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        new student.userlist();
      }
    });

//    mtopictree.addActionListener(new java.awt.event.ActionListener() {
//      public void actionPerformed(ActionEvent e) {
//        if(currPicture != null) return;
//        boolean weregameicons = gameicons;
//        gameicons = false;
//        if (!playingGames)
//          setupgames();
//        else if (weregameicons)
//          switchdisplay();
//      }
//    });
    msearchi.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
//startPR2004-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//        new findword(null);
        if(currPicture != null) return;
        new findword(null, sharkStartFrame.mainFrame);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      }
    });
//startPR2008-06-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    mPickPicture.addActionListener(new java.awt.event.ActionListener() {
//      public void actionPerformed(ActionEvent e) {
//        if (gamePanel!=null && gamePanel.tooltipmover1 != null) {
//          gamePanel.removeMover(gamePanel.tooltipmover1);
//          gamePanel.tooltipmover1 = null;
//        }
//        childFrame =new PickPicture(false,new String[]{studentList[currStudent].name}, studentList[currStudent].name, true);
//        sharkStartFrame.mainFrame.setState(ICONIFIED);
//      }
//    });
//endPRimagesandvids^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    messagesi.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        new messages("new");
      }
    });
//startPR2007-11-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    fontStandardGames_change.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
            setadmingamefont();
      }
    });
    mfontchange2.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        String ss = student.optionstring("s_wordfont") != null ? student.optionstring("s_wordfont") : null;
        Font f;
        String fontdet[];
        boolean isInfant = isinfantcourse();
        if (ss != null) {
          fontdet = u.splitString(ss);
        }
        else {
            if(isinfantcourse()){
                fontdet = (String[]) db.find(sharkStartFrame.optionsdb, "wordfontinfant", db.TEXT);
            }
            else{
                fontdet = (String[]) db.find(sharkStartFrame.optionsdb, "wordfont", db.TEXT);
            }
        }
        if (fontdet == null
            ||
          (f =  u.fontFromString(fontdet[0], Integer.parseInt(fontdet[1]), sharkStartFrame.BASICFONTPOINTS)) == null) {
        f = currdefaultfont;
        }
        originalFont = f;
        new FontChooser(u.gettext("choosegamefont", "label"), wordfont,
                isInfant?defaultinfantfontname:defaultfontname,
//startPR2008-01-15^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//                 null, null, false, mainFrame) {
                 null, null, false, true, true, mainFrame) {
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          public void update() { //OVERRIDE FONTCHOOSER.UPDATE()
//startPR2008-01-15^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//            if (!(chosenfont.equals(originalFont))) { //ONLY UPDATE THINGS IF THE FONT HAS CHANGED.
//              if(chosenfont.getFontName().equalsIgnoreCase(defaultfontname.getFontName()))
//                student.removeOption("s_wordfont");
//              else
            if(settoadmindefault){
              student.removeOption("s_wordfont");
              setwordfont();
//startPR2008-01-17^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//             setVisible(true);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            }
            else if (!(chosenfont.equals(originalFont))) { //ONLY UPDATE THINGS IF THE FONT HAS CHANGED.
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
              student.setOption("s_wordfont", u.combineString(new String[] {chosenfont.getName(),
                  String.valueOf(chosenfont.getStyle())}));
              setwordfont();
//startPR2008-01-17^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//             setVisible(true);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            }
          }
        };
      }
    });
//    fontMenus_change.addActionListener(new java.awt.event.ActionListener() {
//      public void actionPerformed(ActionEvent e) {
//        originalFont = sharkStartFrame.treefont;
//        new FontChooser(u.gettext("choosemenufont", "label"),
//                        sharkStartFrame.treefont,
//startPR2007-12-06^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//                        FontChooser.defaultTreeFont,
//                        FontChooser.defaultTreeFont.deriveFont((float)u.getminimumfontsize(FontChooser.defaultTreeFont)),
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2008-01-15^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//                        null, null, true, sharkStartFrame.mainFrame) {
//                        null, null, true, false, sharkStartFrame.mainFrame) {
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//          public void update() { //OVERRIDE FONTCHOOSER.UPDATE()
//            if (!(chosenfont.equals(originalFont))) { //ONLY UPDATE THINGS IF THE FONT HAS CHANGED.
//startPR2007-12-06^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//              if(chosenfont.equals(FontChooser.defaultTreeFont))
//                if(chosenfont.equals(FontChooser.defaultTreeFont.deriveFont((float)u.getminimumfontsize(FontChooser.defaultTreeFont))))
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//                db.delete(optionsdb, "treefont", db.TEXT);
//              else
//                db.update(sharkStartFrame.optionsdb, "treefont",
//                        new String[] {chosenfont.getName(),
//                        String.valueOf(chosenfont.getStyle()),
//startPR2007-12-06^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//                        String.valueOf(Math.min(FontChooser.pointSizeMenuMax, chosenfont.getSize()))}
 //                       String.valueOf(Math.min(FontChooser.pointSizeMax, chosenfont.getSize()))}
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//                        , db.TEXT);
//              u.defaultfont();
//              u.okmess(u.gettext("treefont", "heading"),
//                       u.gettext("treefont", "message"), sharkStartFrame.mainFrame);
//            }
//          }
//        };
 //     }
 //   });
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    manBar1.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseEntered(MouseEvent e) {
        if (showteachingnotes != null) {
          showteachingnotes.setVisible(false);
          showteachingnotes.dispose();
          showteachingnotes = null;
        }
      }
    });
//startPR2007-10-10^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    // use default font for wordlists and games
//    defaultfont.addActionListener(new java.awt.event.ActionListener() {
//      public void actionPerformed(ActionEvent e) {
//        boolean leaveit = false;
//        short i, j;
//        db.delete(optionsdb, "wordfont", db.TEXT);
//        db.delete(optionsdb, "wordfontinfant", db.TEXT);
//        setwordfont();
//        if(gamePanel != null)
//          buildgamepanel2();
//      }
//    });
//
//    // use default font for wordlists and games FOR STUDENTS
//    defaultfont2.addActionListener(new java.awt.event.ActionListener() {
//      public void actionPerformed(ActionEvent e) {
//        boolean leaveit = false;
//        short i, j;
//        student.removeOption( "s_wordfont");
//        chooseFont2.setState(false);
//        defaultfont2.setState(true);
//        setwordfont();
//      }
//    });
//
//
//    // change font for trees
//    mtreefont.addActionListener(new java.awt.event.ActionListener() {
//      public void actionPerformed(ActionEvent e) {
//        short i, j;
//        int fontStyle = sharkStartFrame.treefont.getStyle(); //GET CURRENT FONT STYLE
//        int fontSize = sharkStartFrame.treefont.getSize(); //GET CURRENT FONT SIZE
//        String fontName = sharkStartFrame.treefont.getFontName(); //GET CURRENT FONT NAME
//        originalFont = new Font(fontName, fontStyle, fontSize); //CREATE A CURRENT FONT FOR COMPARISON
//        new FontChooser(u.gettext("choosefont1", "label"),
//                        sharkStartFrame.treefont, true) {
//          public void update() { //OVERRIDE FONTCHOOSER.UPDATE()
//            if (! (chosenfont.equals(originalFont))) { //ONLY UPDATE THINGS IF THE FONT HAS CHANGED.
//              db.update(sharkStartFrame.optionsdb, "treefont",
//                        new String[] {chosenfont.getName(),
//                        String.valueOf(chosenfont.getStyle()),
//                        String.valueOf(Math.min(18, chosenfont.getSize()))}
//                        , db.TEXT);
//              u.defaultfont();
//              u.okmess(u.gettext("treefont", "heading"),
//                       u.gettext("treefont", "message"), sharkStartFrame.mainFrame);
//            }
//          }
//        };
//      }
//    });
//
//    // change font for trees FOR STUDENTS
//    mtreefont2.addActionListener(new java.awt.event.ActionListener() {
//      public void actionPerformed(ActionEvent e) {
//        short i, j;
//        String s1,s[];
//        mtreefont2.setState(student.optionstring("s_treefont")!= null);   // reset state
//        if((s1 = student.optionstring("s_treefont")) != null  )
//              s = u.splitString(s1);
//        else  s =  (String[]) db.find(sharkStartFrame.optionsdb,"treefont",db.TEXT);
//        UIDefaults ui = UIManager.getDefaults();
//        Font fo, fo2;
//        if(s != null) {
//           originalFont = fo =fo2 = new Font(s[0],Integer.parseInt(s[1]),Integer.parseInt(s[2]));
//        }
//        else {
//           originalFont = fo = fo2 = (Font)ui.get("Tree.font");
//        }
//        new FontChooser(u.gettext("choosefont1", "label"),
//                        fo, true,mainFrame) {  // pr 13/11/06
//          public void update() { //OVERRIDE FONTCHOOSER.UPDATE()
//               student.setOption("s_treefont",u.combineString(
//                        new String[] {chosenfont.getName(),
//                        String.valueOf(chosenfont.getStyle()),
//                        String.valueOf(Math.min(18, chosenfont.getSize()))}));
//              mtreefont2.setState(true);
//              notreefont2.setState(false);
//              u.defaultfont();
//              u.okmess(u.gettext("treefont", "heading"),
//                       u.gettext("treefont", "message"), sharkStartFrame.mainFrame);
//          }
//        };
//      }
//    });
//
//    // use default tree font
//    notreefont.addActionListener(new java.awt.event.ActionListener() {
//      public void actionPerformed(ActionEvent e) {
//        if (db.query(sharkStartFrame.optionsdb, "treefont", db.TEXT) >= 0) {
//          db.delete(sharkStartFrame.optionsdb, "treefont", db.TEXT);
//          u.defaultfont();
//         u.okmess(u.gettext("treefont","heading"), u.gettext("treefont","message"), sharkStartFrame.mainFrame);
//        }
//      }
//    });
//
//    // use default tree font FOR STUDENTS
//    notreefont2.addActionListener(new java.awt.event.ActionListener() {
//      public void actionPerformed(ActionEvent e) {
//        if (student.optionstring( "s_treefont") != null) {
//          student.removeOption("s_treefont");
//          mtreefont2.setState(false);
//          notreefont2.setState(true);
//          u.defaultfont();
//          u.okmess(u.gettext("treefont","heading"), u.gettext("treefont","message"), sharkStartFrame.mainFrame);
//        }
//      }
//    });
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    easywordlist.addActionListener(new java.awt.event.ActionListener() {
//      public void actionPerformed(ActionEvent e) {
//        setTitle(privatetopicstitle);
//        closeprev();
//        grid1.weightx = grid1.weighty = 1;
//        grid1.fill = GridBagConstraints.BOTH;
//        grid1.gridx = grid1.gridy = 0;
//        bevelPanel1.add(easywordlistpanel = new wordlist.easyword(), grid1);
//        bevelPanel1.validate();
//        bevelPanel1.requestFocus();
//      }
//    });
    if(!shark.phonicshark){
        mownwords.addActionListener(new java.awt.event.ActionListener() {
          public void actionPerformed(ActionEvent e) {
    //        setTitle(privatetopicstitle);
              new OwnWordLists();
    //        closeprev();
    //        grid1.weightx = grid1.weighty = 1;
    //        grid1.fill = GridBagConstraints.BOTH;
    //        grid1.gridx = grid1.gridy = 0;
    //        bevelPanel1.add(easywordlistpanel = new wordlist.easyword(), grid1);
    //        bevelPanel1.validate();
    //        bevelPanel1.requestFocus();
          }
        });
    }
//startPR2004-09-03^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    // enables exiting screen via the ESC key
//    bevelPanel1.addKeyListener(new KeyAdapter() {
//      public void keyPressed(KeyEvent e) {
//        int code = e.getKeyCode();
//        if (code == KeyEvent.VK_ESCAPE) {
//          if(easywordlistpanel != null){
//            if (easywordlistpanel.saveit(true)) {
//              sharkStartFrame.mainFrame.easywordlistpanel = null;
//              sharkStartFrame.mainFrame.setupgames();
//              db.closeAll();
//            }
//          }
//        }
//      }
//    });
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    String spellch[] = spellchange.getlists();
//    if(spellch != null) {
//         mspellchanges =  new JCheckBoxMenuItem[spellch.length];
//         for (short i = 0; i < mspellchanges.length; ++i) {
//           mspellchange.add(mspellchanges[i] = new u.myCheckBoxMenuItem(spellch[i]));
//           if(i == 0 && !spellchange.active) mspellchanges[0].setState(true);
//           else  mspellchanges[i].setState(spellch[i].equals(spellchange.name));
//           mspellchanges[i].addActionListener(new java.awt.event.ActionListener() {
//             public void actionPerformed(ActionEvent e) {
//           //     setSpellChange();
//             }
//           });
//         }
//    }
    
    setCourseMenu();
/*
    cl = db.listsort(u.absoluteToRelative(sharkStartFrame.publicTopicLib[0]), db.TOPICTREE);
    for(int i=0;i<cl.length;++i) {    // start rb 26-11-07
     if(cl[i].startsWith("  ") ||  !u.displayCourse(cl[i])) {
        cl = u.removeString(cl,i);
        --i;
     }
    }                                 // end rb 26-11-07
    mcourses = new JCheckBoxMenuItem[cl.length];
    for (short i = 0; i < cl.length; ++i) {
      restrictcourses.add(mcourses[i] = u.CheckBoxMenuItem2(cl[i]));
      mcourses[i].setState(u.findString(courses, cl[i]) < 0);
      mcourses[i].addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          courses = new String[0];
          String stucourses[] = new String[0];
          boolean active = false;
          for (short i = 0; i < mcourses.length; ++i) {
            if (!mcourses[i].getState()){
              courses = u.addString(courses, mcourses[i].getText());
              if(mcourses[i].isEnabled())
                  stucourses = u.addString(stucourses, mcourses[i].getText());
            }
            if(mcourses[i].getState() && mcourses[i].isEnabled())active = true;
          }
          if(!active){
              ((JCheckBoxMenuItem)e.getSource()).setSelected(true);
              return;
          }
          student.setOption("s_courses", u.combineString(stucourses));
          setupgames();
        }
      });

    }
    */
//start rb 16/2/06
    //  set up for choosing reward games
    gamestoplay gg = new gamestoplay();
    gg.setuprewards( publicGameLib, true,true,"Rewards");
    rw = gg.nodeList(gg.root);
//    mrewardfreq = u.Menu("mrewardfreq");
//    mrewardfreqs = new JCheckBoxMenuItem[] {u.CheckBoxMenuItem("mreward1"),u.CheckBoxMenuItem("mreward2"),u.CheckBoxMenuItem("mreward3"),u.CheckBoxMenuItem("mreward4")};
    short i;
//    for (i = 0; i < 4; ++i) {
//      mrewardfreqs[i].addActionListener(new java.awt.event.ActionListener() {
//        public void actionPerformed(ActionEvent e) {
//          int i;
//          for (i = 0; i < 4; ++i) {
//             if(mrewardfreqs[i].getActionListeners()[0] == this) {
//                 mrewardfreqs[i].setState(true);
//                 if(currStudent>=0)studentList[currStudent].rewardfreq = (byte)i;
                   // start  rb 27/10/06
//                 mnoreward.setSelected(false);
//                 studentList[currStudent].clearOption("noreward");
                  // end  rb 27/10/06
//             }
//             else mrewardfreqs[i].setState(false);
//          }
//        }
//      });
//      mrewardfreq.add(mrewardfreqs[i]);
//    }
//    mrewardfreq.add(mnoreward); // rb 27/10/06
//    mrewards = new JCheckBoxMenuItem[rw.length];
//    for (i = 0; i < rw.length; ++i) {
//        msetrewards.add(mrewards[i] = u.CheckBoxMenuItem2(rw[i]));
//      mrewards[i].setToolTipText(u.gettext("msetrewards","tooltip_base"));
 //     u.forcenotes(mrewards[i],true,false); //02/07/2010
//      mrewards[i].addActionListener(new java.awt.event.ActionListener() {
//        public void actionPerformed(ActionEvent e) {
//          String okrewards2[] = new String[0];
//          String okrewards3[] = new String[0];
//          String ss[] = null;
//          String s = student.optionstring("s_okrewards");               // start rb 16/2/06
//          if(s!=null) ss = u.splitString(s);
//          int visno = 0;
//          for (short i = 0; i < mrewards.length; ++i) {
//            if (mrewards[i].isVisible()){
//                visno++;
//                if(mrewards[i].getState())
//                    okrewards2 = u.addString(okrewards2, mrewards[i].getText());
//            }
//            else if(ss==null || u.findString(ss, mrewards[i].getText())>=0){
//                okrewards3 = u.addString(okrewards3, mrewards[i].getText());
//            }
//          }
//          if(okrewards2.length < 3) {
//            okrewards2 = new String[0];
//            for (short i = 0; i < mrewards.length; ++i) {
//              if(mrewards[i].getActionListeners()[0] == this)  mrewards[i].setState(true);
//              if (mrewards[i].isVisible() && mrewards[i].getState())  okrewards2 = u.addString(okrewards2, mrewards[i].getText());
//            }
//          }
//          if(visno<4)return;
//          okrewards = okrewards2;
//          if(okrewards.length == mrewards.length)
//              student.removeOption("s_okrewards");
//          else student.setOption("s_okrewards", u.combineString(u.addString(okrewards, okrewards3)));
//        }
//      });
//    }
///    msetrewards.addSeparator();
//    msetrewards.add(mrewardfreq);
//end  rb 16/2/06
    mBackup.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        Backup_base bkup = new Backup_base(true);
      }
    });
//    msturec.addActionListener(new java.awt.event.ActionListener() {
//      public void actionPerformed(ActionEvent e) {
 //       new student.specs();
//      }
//    });
    mstuchek.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        student.fixstudents();
      }
    });
    mChangeScreenSize.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        new ChangeScreenSize_base(sharkStartFrame.mainFrame);
      }
    });
//startPR2008-11-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    if(shark.licenceType.equals(shark.LICENCETYPE_STANDALONEACTIVATION)){
//      mnewlicence.addActionListener(new java.awt.event.ActionListener() {
//        public void actionPerformed(ActionEvent e) {
//          wa.makeUI(true);
//        }
//      });
      mconvertexpiry.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          wa.doexpirydisable();
        }
      });
      mdisablelicence.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          wa.storeresponsedisable();
        }
      });
      mviewlicence.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          u.launchWebSite(wa.buildURL(
                  u.gettext("webauthenticate", "httpactivation", shark.programName.toLowerCase()) + "/",
                  u.gettext("webauthenticate", "redirectsite"),
                  u.gettext("webauthenticate", "viewsite"),
//                  u.convertForURL(school),
                  school!=null?u.convertForURL(school):null,
                  null,
                  null,null,null));
        }
      });
    }
//endPRauth^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    easyrecord.addActionListener(new java.awt.event.ActionListener() {
//      public void actionPerformed(ActionEvent e) {
//        if (currStudent >= 0) {
//          setTitle(recprivatewordstitle);
//          startrecord(studentList[currStudent].name, 0);
//        }
//      }
//    });
//    defrecord.addActionListener(new java.awt.event.ActionListener() {
//      public void actionPerformed(ActionEvent e) {
//        if (currStudent >= 0) {
//          setTitle(recdeftitle);
//          startrecord("publictopics", 1);
//        }
//      }
//    });
//    defrecord2.addActionListener(new java.awt.event.ActionListener() {
//      public void actionPerformed(ActionEvent e) {
//        if (currStudent >= 0) {
//          setTitle(recdeftitle);
//          startrecord("publictopics", 4);
//        }
//      }
//    });
    sentrecord.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (currStudent >= 0) {
          setTitle(recsenttitle);
          startrecord(u.absoluteToRelative(sharkStartFrame.publicTopicLib[0]), 3);
        }
      }
    });
    sentrecord2.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (currStudent >= 0) {
          setTitle(recsenttitle);
          startrecord(u.absoluteToRelative(sharkStartFrame.publicTopicLib[0]), 2);
        }
      }
    });
    sentrecord3.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (currStudent >= 0) {
          setTitle(recsenttitle);
          startrecord(u.absoluteToRelative(sharkStartFrame.publicTopicLib[0]), 5);
        }
      }
    });
    sentrecord4.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (currStudent >= 0) {
          setTitle(recsenttitle);
          startrecord(u.absoluteToRelative(sharkStartFrame.publicTopicLib[0]), 7);
        }
      }
    });
//startPR2010-05-28^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    sentrecord4.addActionListener(new java.awt.event.ActionListener() {
//      public void actionPerformed(ActionEvent e) {
//        if (currStudent >= 0) {
//          setTitle(recsenttitle);
//          startrecord("publictopics", 6);
//        }
//      }
//    });
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    returnnormal.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (!playingGames)
          setupgames();
      }
    });
    memory.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        u.mem();
      }
    });
    mshowshared.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          try {
            if(shark.macOS)
              Runtime.getRuntime().exec(new String[]{"open",sharkStartFrame.sharedPath.getAbsolutePath()});
            else
              Runtime.getRuntime().exec(new String[]{"explorer.exe",sharkStartFrame.sharedPath.getAbsolutePath()});
          }
          catch(Exception ee){}
        }
    });
    mzipshared.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
//            zipUpShared(false);
            zipUpShared(false, true);
        }
    });
    mzipsharednet_anon.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
//          zipUpShared(true);
          zipUpShared(true, false);
      }
    });
    mzipsharednet_withstus.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
//          zipUpShared(false);
          zipUpShared(false, false);
      }
    });

    refreshsound.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (refreshsound.isSelected())
          db.update(optionsdb, "refreshsound", new String[] {""}
                    , db.TEXT);
        else
          db.delete(optionsdb, "refreshsound", db.TEXT);
      }
    });
//startPR2008-10-12^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    antialias.addActionListener(new java.awt.event.ActionListener() {
//      public void actionPerformed(ActionEvent e) {
//        if (antialias.isSelected()){
//          db.update(optionsdb, "antialiasing", new String[] {""}
//                    , db.TEXT);
//          antialiasing = false;
//        }
//        else{
//          db.delete(optionsdb, "antialiasing", db.TEXT);
//          antialiasing = true;
//        }
//      }
//    });
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    mnotemp.addActionListener(new java.awt.event.ActionListener() {
//      public void actionPerformed(ActionEvent e) {
//        if (mnotemp.isSelected())
//          db.update(optionsdb, "mnotemp", new String[] {""}
//                    , db.TEXT);
//        else
//          db.delete(optionsdb, "mnotemp", db.TEXT);
//      }
//    });
//startPR2010-05-04^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    msideprompt.addActionListener(new java.awt.event.ActionListener() {
//      public void actionPerformed(ActionEvent e) {
//        if (msideprompt.isSelected())
//          db.update(optionsdb, "nosideprompt", new String[] {""}
//                    , db.TEXT);
//        else
//          db.delete(optionsdb, "nosideprompt", db.TEXT);
//      }
//    });
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2008-09-21^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    enforceprogram.addActionListener(new java.awt.event.ActionListener() {
//      public void actionPerformed(ActionEvent e) {
//        if (enforceprogram.isSelected())
//          db.update(optionsdb, "menforceprogram", new String[] {""}
//                    , db.TEXT);
//        else
//          db.delete(optionsdb, "menforceprogram", db.TEXT);
//startPR2008-09-22^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//        whichteacherlast = -1;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      }
//    });
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    mautosignon.addActionListener(new java.awt.event.ActionListener() {
//      public void actionPerformed(ActionEvent e) {
//        mautosigncreatestu.setVisible(mautosignon.isSelected());
//        if(mautosignon.isSelected()) db.update(optionsdb,"autosignon_",new String[]{""},db.TEXT);
//        else {
//            db.delete(optionsdb,"autosignon_",db.TEXT);
//            db.delete(optionsdb,"mautosigncreatestu_",db.TEXT);
//        }
//       }
//    });

//    mautosigncreatestu.addActionListener(new java.awt.event.ActionListener() {
//      public void actionPerformed(ActionEvent e) {
//        if(mautosigncreatestu.isSelected()) db.update(optionsdb,"mautosigncreatestu_",new String[]{""},db.TEXT);
//        else db.delete(optionsdb,"mautosigncreatestu_",db.TEXT);
//       }
//    });


//    mdefcourse.addActionListener(new java.awt.event.ActionListener() {
//      public void actionPerformed(ActionEvent e) {
//         getstartingcourse();
 //      }
 //   });
//    maxsametopic = u.Menu("maxsametopic");                    // v5 start rb 7/3/08
//    String currm = (String) db.find(optionsdb, "maxsametopic",db.TEXT);
//    if(currm == null) sharkGame.MAXSAMETOPIC = maxsamenum[1];
//    else sharkGame.MAXSAMETOPIC = Integer.parseInt(currm);
//    for(i=0;i<maxsame.length;++i) {
//      maxsame[i] = new u.myCheckBoxMenuItem(maxsametext[i]);
//      maxsame[i].setToolTipText(maxsametooltip);
//      maxsame[i].setSelected(sharkGame.MAXSAMETOPIC == maxsamenum[i]);
//      maxsame[i].addActionListener(new java.awt.event.ActionListener() {
//        public void actionPerformed(ActionEvent e) {
//          int i, j;
//          for (i = 0; i < maxsame.length; ++i) {
//            if (maxsame[i].getActionListeners()[0] == this || maxsame[i].getActionListeners()[1] == this) {
//              if (maxsame[i].isSelected()) {
//                sharkGame.MAXSAMETOPIC = maxsamenum[i];
//                db.update(optionsdb, "maxsametopic", String.valueOf(maxsamenum[i]), db.TEXT);
//                for (j = 0; j < maxsame.length; ++j) {
//                  if (maxsame[j].getActionListeners()[0] != this && maxsame[j].isSelected()&& maxsame[i].getActionListeners()[1] != this) {
//                    maxsame[j].setSelected(false);
//                  }
//                }
//              }
//              else {
//                for (j = 0; j < maxsame.length; ++j) {
//                  if (maxsame[j].getActionListeners()[0] != this  && maxsame[i].getActionListeners()[1] != this
//                      && maxsame[j].isSelected())
//                    return;
//                  ;
 //               }
//                noise.beep();
//                maxsame[i].setSelected(true);
//              }
//              return;
//            }
//          }
//        }
//      });
//      maxsametopic.add(maxsame[i]);
 //   }                                           // v5 end rb 7/3/08
//startPR2004-10-21^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    mpeep.addActionListener(new java.awt.event.ActionListener() {  // rb 27/10/06
//      public void actionPerformed(ActionEvent e) {
//        if (!mpeep.isSelected())          student.setOption("s_nopeep"); // rb 27/10/06
//        else student.clearOption("s_nopeep");
//      }
//    });
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    splitdraw.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (splitdraw.isSelected())
          db.update(optionsdb, "splitdraw", new String[] {""}
                    , db.TEXT);
        else
          db.delete(optionsdb, "splitdraw", db.TEXT);
        runMovers.splitdraw = splitdraw.isSelected();
      }
    });
    bgcolor.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (bgcolor.isSelected()) {
            ((u.myCheckBoxMenuItem) bgcolor).stayAfterClick =false;
            new chooseBackground_base(sharkStartFrame.mainFrame);
//          Color newColor = JColorChooser.showDialog(mainFrame,
//              bgcolor.getText(),
//              Color.white);
//          if (newColor != null) {
//            student.setOption("bgcolor", String.valueOf(newColor.getRGB()));
//          }
//          else {
//            bgcolor.setSelected(false);
//            student.removeOption("bgcolor");
//          }
        }
        else{
//startPR2013-02-20^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         wordTree.setBackground(wordlist.wlbgcol);
         wordlist.bgcoloruse = wordlist.wlbgcol;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          student.removeOption("bgcolor");
        }
      }
    });

    refreshsound.setSelected(db.query(optionsdb, "refreshsound", db.TEXT) >= 0);
//    mnotemp.setSelected(db.query(optionsdb, "mnotemp", db.TEXT) >= 0);
//startPR2010-05-04^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    msideprompt.setSelected(db.query(optionsdb, "nosideprompt", db.TEXT) < 0);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    mupdatescheckall.setSelected(db.query(optionsdb, "updatescheckall", db.TEXT) >= 0);
//startPR2008-10-12^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    antialiasing = (db.query(optionsdb, "antialiasing", db.TEXT) >= 0);
//    antialias.setSelected(antialiasing);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2008-09-21^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    enforceprogram.setSelected(db.query(optionsdb, "menforceprogram", db.TEXT) >= 0);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    mautosignon.setSelected(db.query(optionsdb,"autosignon_",db.TEXT )>=0);
//    mautosigncreatestu.setSelected(db.query(optionsdb,"mautosigncreatestu_",db.TEXT )>=0);
 //   mautosigncreatestu.setVisible(mautosignon.isSelected());
    splitdraw.setSelected(runMovers.splitdraw = (db.query(optionsdb,
        "splitdraw", db.TEXT) >= 0));
    menuItem1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        menuItem1_actionPerformed(e);
      }
    });
//    mtwoplayers.addActionListener(new java.awt.event.ActionListener() {   // v5 start rb 7/3/08
//      public void actionPerformed(ActionEvent e) {
//        if(mtwoplayers.isSelected() && studentList.length > 1) {
//          sharkGame.otherplayer = studentList[(currStudent + 1) % studentList.length];
//        }
//        else {
//          sharkGame.otherplayer = null;
//          sharkGame.lastreward = true; sharkGame.studentflipped = false;
//        }
//      }
//    });                                                                   // v5 end rb 7/3/08
    topicOptions.setFont(treefont.deriveFont((float)treefont.getSize()-3));
    topicOptions.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseReleased(MouseEvent e) {
        currTopicView.plugTopic( (String) topicOptions.getSelectedValue());
      }
    });
//    gameTree.addMouseListener(new java.awt.event.MouseAdapter() {
//      public void mouseReleased(MouseEvent e) {
//        if (playingGames && currPlayTopic != null) {
//          TreePath pp = gameTree.getPathForLocation(e.getX(), e.getY());
//          if (pp != null) {
//            jnode node = (jnode) pp.getLastPathComponent();
//            String s[] = gameTree.nodeList(node);
//            if (s.length == 0)
//              return;
//            if (node.isLeaf()) {
//              if (System.currentTimeMillis() > lastgamestart + 2000) {
//                if (oksplit(s[0])) {
//                  Point pt = gameTree.getLocationOnScreen();
//                  mouseonscreenx = e.getX() + pt.x;
//                  mouseonscreeny = e.getY() + pt.y;
//                  startgame(s[0]);
//                }
//              }
//            }
//          }
//        }
//      }
//    });
    publicGameTree.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseReleased(MouseEvent e) {
        if (e.getModifiers() == e.BUTTON3_MASK){
            if(markgamescoursetree != null && markgamescoursetree.isVisible()){
                jnode sel = markgamescoursetree.getSelectedNode();
                if(sel != null){
                   jnode gnode =  sharkStartFrame.mainFrame.publicGameTree.getSelectedNode();
                   if(gnode!=null){
                    String name = gnode.get();
                    markgamescoursetree.addListItem(markgamescoursetree.getSelectedNode(), name, "=");                       
                   }             
                }
            }
            else{
                currTopicView.addGame();                 
            }
  
        }
      }
    });
    gameTree.set(gameTree.root, "games");
    gameTree.setRootVisible(false);
    publicGameTree.setup(publicGameLib,
                         false, true,
                         "Right-click to select game or group of games",gamestoplay.categories);
    gametreeheadings = new String[publicGameTree.root.getChildAt(0).getChildCount()];
    for(i=0;i<gametreeheadings.length;++i) gametreeheadings[i] = ((jnode)publicGameTree.root.getChildAt(0).getChildAt(i)).get();
    publicGameTree.expandPath(new TreePath( ( (jnode) ( (jnode) publicGameTree.
        root.getFirstChild()).getFirstChild()).getPath()));
    topic.makeList(topicOptions);

    grid1.gridy = -1;
    grid1.gridx = 0;
    grid1.weightx = 1;
    grid1.weighty = 0;
    grid1.fill = GridBagConstraints.HORIZONTAL;

    String fn = sharkStartFrame.treefont.getName();
    int fs = Math.max(sharkStartFrame.treefont.getSize()-6, 10);
    String listspaces = "";
    String fastmodemessaori[] = u.splitString(u.gettext("fastmode", "mess"));
    String fastmodemessa[] = new String[fastmodemessaori.length];
    String col = "black";
    fastmodemessa[0] = "<html><head><style type='text/css'>li {color: "+col+";}li span {color: "+col+"</style></head><dl><dd><font face='"+fn+"' color='"+col+"' style='font-size:"+String.valueOf(fs)+"px'>"+fastmodemessaori[0]+"</font></dl>";
    fastmodemessa[1] = "<li><font face='"+fn+"' style='font-size:"+String.valueOf(fs-1)+"px'>"+listspaces+fastmodemessaori[1]+"</font></li>";
    fastmodemessa[2] = "<li><font face='"+fn+"' style='font-size:"+String.valueOf(fs-1)+"px'>"+listspaces+fastmodemessaori[2]+"</font></li>";
    fastmodemessa[3] = "<dl><dd><font face='"+fn+"' color='"+col+"' style='font-size:"+String.valueOf(fs)+"px'>"+fastmodemessaori[3]+"</font></dl>";
    fastmodemessa[4] = "<li><font face='"+fn+"' style='font-size:"+String.valueOf(fs-1)+"px'>"+listspaces+fastmodemessaori[4]+"</font></li>";
    fastmodemessa[5] = "<li><font face='"+fn+"' style='font-size:"+String.valueOf(fs-1)+"px'>"+listspaces+fastmodemessaori[5]+"</font></li>";
    fastmodemessa[6] = "<li><font face='"+fn+"' style='font-size:"+String.valueOf(fs-1)+"px'>"+listspaces+fastmodemessaori[6]+"</font></li>";
    fastmodemessa[7] = "<li><font face='"+fn+"' style='font-size:"+String.valueOf(fs-1)+"px'>"+listspaces+fastmodemessaori[7]+"</font></li></html>";
    //    fastmodemess = "";
    fastmodemessinfo = "";
    for(int k = 0; k < fastmodemessa.length; k++){
        if(fastmodemessa[k]!=null)
            fastmodemessinfo = fastmodemessinfo.concat(fastmodemessa[k]);
    }
//    fastmodemessa = new String[fastmodemessaori.length];
//    col = "white";
//    fastmodemessa[0] = "<html><head><style type='text/css'>li {color: "+col+";}li span {color: "+col+"</style></head><dl><dd><font face='"+fn+"' color='"+col+"' style='font-size:"+String.valueOf(fs)+"px'>"+fastmodemessaori[0]+"</font></dl>";
//    fastmodemessa[1] = "<li><font face='"+fn+"' style='font-size:"+String.valueOf(fs-1)+"px'>"+listspaces+fastmodemessaori[1]+"</font></li>";
//    fastmodemessa[2] = "<li><font face='"+fn+"' style='font-size:"+String.valueOf(fs-1)+"px'>"+listspaces+fastmodemessaori[2]+"</font></li>";
//    fastmodemessa[3] = "<dl><dd><font face='"+fn+"' color='"+col+"' style='font-size:"+String.valueOf(fs)+"px'>"+fastmodemessaori[3]+"</font></dl>";
//    fastmodemessa[4] = "<li><font face='"+fn+"' style='font-size:"+String.valueOf(fs-1)+"px'>"+listspaces+fastmodemessaori[4]+"</font></li>";
//    fastmodemessa[5] = "<li><font face='"+fn+"' style='font-size:"+String.valueOf(fs-1)+"px'>"+listspaces+fastmodemessaori[5]+"</font></li>";
//    fastmodemessa[6] = "<li><font face='"+fn+"' style='font-size:"+String.valueOf(fs-1)+"px'>"+listspaces+fastmodemessaori[6]+"</font></li></html>";
//    for(int k = 0; k < fastmodemessa.length; k++){
//        if(fastmodemessa[k]!=null)
//            fastmodemess = fastmodemess.concat(fastmodemessa[k]);
//    }
    String fastmodetitlea[] = u.splitString(u.gettext("fastmode", "mess2"));
    fastmodetitlea[0] = "<html><center><font face='"+fn+"' style='font-size:"+String.valueOf(fs+4)+"px'>"+fastmodetitlea[0]+"</font></center>";
    fastmodetitlea[1] = "<font face='"+fn+"' style='font-size:"+String.valueOf(fs+2)+"px'>"+fastmodetitlea[1]+"</font><br><br>";
    fastmodetitlea[2] = "<font face='"+fn+"' style='font-size:"+String.valueOf(fs-1)+"px'>"+fastmodetitlea[2]+"</font><html>";
    fastmodetitle = "";
    for(int k = 0; k < fastmodetitlea.length; k++){
        fastmodetitle = fastmodetitle.concat(fastmodetitlea[k]);
    }
    strnotfastmode = "<html><center><font face='"+fn+"' style='font-size:"+String.valueOf(fs+2)+"px'>"+u.convertToInnerHtml(u.gettext("fastmode", "notavailable"))+"</font></center></html>";
    strnotfastmodeheading = "<html><center><font face='"+fn+"' style='font-size:"+String.valueOf(fs+2)+"px'>"+u.convertToInnerHtml(u.gettext("fastmode", "notavailableheading"))+"</font>"+
//            "<br><br><font face='"+fn+"' style='font-size:"+String.valueOf(fs)+"px'>"+u.convertToInnerHtml(u.gettext("fastmode", "notavailableheading2"))+"</font>"+
            "</center></html>";
     fastmodeok.setBackground(Color.white);
     fastmodeok.setOpaque(true);
     fastwordlist = new JLabel();
     fastwordlist.setOpaque(true);
     fastwordlist.setBackground(fastmodecolor);

    fasttp = new textpane();
    fasttp.setEditable(false);
    fasttp.setContentType("text/html");
    fasttp.setCaretPosition(0);
    fasttp.setSelectedTextColor(null);
    fasttp.setSelectionColor(Color.white);
    fasttp.setText(fastmodemessinfo);

    JPanel fmbutpan = new JPanel(new GridBagLayout());
    fmbutpan.setBackground(fastmodecolor_lighter);
    fmbutpan.setOpaque(true);

        grid2.insets = new Insets(0,0,0,0);
        Dimension d = new Dimension(screenSize.width / 3,
                                                 screenSize.height/8);
        Dimension d2 = new Dimension(screenSize.width / 5,
                                                 screenSize.height/14);
        fmbutpan.setPreferredSize(d);
        fmbutpan.setMinimumSize(d);
        grid1.fill = GridBagConstraints.NONE;
          grid2.gridx = 0;
          grid2.gridy = -1;
    XrButton_base usefm = new XrButton_base(u.gettext("fastmode", "use"), fastmodecolor_lighter);
    usefm.setPreferredSize(d2);
    usefm.setMinimumSize(d2);
    grid1.weighty = 1;    
    fmbutpan.add(usefm, grid1);
    grid1.fill = GridBagConstraints.BOTH;


//            setupsuper1();
//            student.setOption("super1", ((jnode)(topicList.getSelectedNode())).get());
//            student.removeOption("super2");

    usefm.addActionListener(new java.awt.event.ActionListener() {
          public void actionPerformed(ActionEvent e) {
            setupsuper2();
            student.setOption("super2", ((jnode)(topicList.getSelectedNode())).get());
            student.removeOption("super1");
      }
    });


//     fastmodeok.add(fastwordlist, grid1);
     grid1.weighty = 1;

   JPanel fmp = new JPanel(new GridBagLayout());
   fmp.add(fasttp, grid1);


   grid1.insets = new Insets(0,0,0,0);

   lbfastmlist = new JLabel();
   lbfastmlist.setForeground(sharkStartFrame.fastmodecolor);
   Font f1 = sharkStartFrame.treefont.deriveFont((float)sharkStartFrame.treefont.getSize()+3);
   lbfastmlist.setFont(f1);
   grid1.gridx = -1;
   grid1.gridy = 0;
   JPanel btpan = new JPanel(new GridBagLayout());
   btpan.setOpaque(false);
   String fastmess[] = u.splitString(u.gettext("fastmode","fastmode"));
   
   fastmess[1] = "<font face='"+fn+"' style='font-size:"+String.valueOf(fs-1)+"px'>"+fastmess[1]+"</font>";   
   
   
   JLabel fml = new JLabel(u.convertToHtml(u.combineString(fastmess),true));
//   fml.setBackground(Color.white);
   fml.setOpaque(false);
   fml.setFont(f1);
   grid1.weightx = 0;
   btpan.add(fml, grid1);
   fminfo = u.sharkButton();
 //  fminfo.addActionListener(new java.awt.event.ActionListener() {
 //         public void actionPerformed(ActionEvent e) {
 //            if(fastmodemesslast){
 //                 fasttp.setText(fastmodemessinfo);
 //             }
 //             else{
 //                 fasttp.setText(fastmodemess);
 //             }
 //            fastmodemesslast = !fastmodemesslast;
 //         }
 //   });

     JPanel fmcontentpn = new JPanel(new GridBagLayout());
     fmcontentpn.setBackground(Color.white);
     fmcontentpn.setOpaque(true);


  //   grid1.insets = new Insets(0,10,0,0);
  //   btpan.add(fminfo, grid1);
     grid1.insets = new Insets(0,0,0,0);
     grid1.gridx = 0;
     grid1.gridy = -1;
     grid1.weighty = 0;
     grid1.weightx = 1;

  //   grid1.anchor = GridBagConstraints.CENTER;
     

     grid1.fill = GridBagConstraints.HORIZONTAL;
     grid1.weighty = 0;
     grid1.anchor = GridBagConstraints.CENTER;
     fmcontentpn.add(btpan, grid1);

    // grid1.anchor = GridBagConstraints.WEST;
     grid1.fill = GridBagConstraints.NONE;
     grid1.insets = new Insets(10,0,0,0);
     grid1.weightx = 0;
     fmcontentpn.add(lbfastmlist, grid1);
     grid1.weightx = 1;
     grid1.fill = GridBagConstraints.BOTH;
     grid1.insets = new Insets(30,0,30,0);
     fmcontentpn.add(fmp, grid1);
     grid1.weightx = 0;
     int g = (int)(screenSize.width*1/35);
     grid1.insets = new Insets(0,g,0,g);
     grid1.weighty = 1;
     grid1.weightx = 0;
     fastmodeok.add(fmcontentpn, grid1);
     grid1.insets = new Insets(0,0,0,0);
     grid1.weighty = 0;
     fastmodeok.add(fmbutpan, grid1);
     grid1.insets = new Insets(0,0,0,0);
     
    fasttpna = new textpane();
    fasttpna.setEditable(false);
    fasttpna.setContentType("text/html");
    fasttpna.setCaretPosition(0);
     grid1.weighty = 1;
     grid1.fill = GridBagConstraints.NONE;
     fastmodena.add(fasttpna, grid1);
     fastmodena.setBackground(Color.white);
     fastmodena.setOpaque(true);

    
    grid1.gridy = 0;
    grid1.weightx = grid1.weighty = 1;
    grid1.fill = GridBagConstraints.BOTH;
    grid2.gridx = grid2.gridy = 0;
    grid2.weightx = grid2.weighty = 1;
    grid2.fill = GridBagConstraints.BOTH;
    grid3.gridx = grid3.gridy = 0;
    grid3.weightx = grid3.weighty = 1;
    grid3.fill = GridBagConstraints.BOTH;

    wordTree.setFont(wordfont);
    publicGameTree.setEditable(false);
    if (shark.specialfunc) {
      play.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          playhelpimage();
        }
      });
    mplay.addMouseListener(new MouseAdapter() {
          public void mousePressed(MouseEvent e) {
              if(!mplay.isEnabled())return;
//startPR2007-03-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            // mousePressed does not register for JMenu on Macs
            //needed to prevent menu from appearing on child dialog
              MenuSelectionManager.defaultManager().clearSelectedPath();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
              mplay.setPopupMenuVisible(false);
              mplay.setSelected(false);
              playhelpimage();
          }
         });      
      
      
      cut.addActionListener(new sharkStartFrame_cut_actionAdapter(this)); //Add Listeners
      copy.addActionListener(new sharkStartFrame_copy_actionAdapter(this));
      paste.addActionListener(new sharkStartFrame_paste_actionAdapter(this));
      find.addActionListener(new sharkStartFrame_find_actionAdapter(this));
      findnext.addActionListener(new sharkStartFrame_findnext_actionAdapter(this));
      pasteimage.addActionListener(new sharkStartFrame_pasteimage_actionAdapter(this));
      PublicTopics.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          PublicTopics_actionPerformed(e);
        }
      });
      changegames.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          updategames();
        }
      });
      changetext.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          updatetext();
        }
      });
      copysay.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent e) {
        copysay();
       }
      });
    }
     mkeypad.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
//        if (keypad.keypadname != null) {
//          if (easywordlistpanel != null) {
//            keypad.deactivate(mainFrame);
//          }
//        }
        student.removeOption("keypad");
//        keypad.keypadname = null;
        keypad.updating = true;
        setwordfont();
        setkeydis();
        new keypad.updatekeypad();
      }
    });
//startSS^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
//    mswitchaccess.addActionListener(new java.awt.event.ActionListener() {
//      public void actionPerformed(ActionEvent e) {
//        boolean leaveit = false;
//        new switchoptions(switchOptions, switchResponse);
//      }
//    });
//endSS^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
    signoff.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        signof_actionPerformed(e);
      }
    });
//    if (madmin != null)
//      madmin.addActionListener(new java.awt.event.ActionListener() {
//        public void actionPerformed(ActionEvent e) {
//          if (studentList[currStudent].makeadmin()) {
//            meuFile.remove(madmin);
//            setStudentMenu();
//          }
//        }
//      });
    madminlist.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
//startPR2004-10-20^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//        new student.adminlist();
//startPR2010-05-04^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//        if(student.signpanel.isFocused()){
        if(student.fishpanel!=null){
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2007-09-03^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//          // blocking focus from the sign on dialog
//          u.focusBlock = true;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          // make it possible to escape out of this dialog
          u.blockEscape = false;
//startPR2010-05-04^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//          new student.adminlist(student.signpanel);
          new student.adminlist(sharkStartFrame.mainFrame);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        }
        else
           new student.adminlist(sharkStartFrame.mainFrame);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      }
    });

    merror.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          int w = sharkStartFrame.screenSize.width/2;
          int h = sharkStartFrame.screenSize.height*7/8;
          u.showtext(sharkStartFrame.mainFrame, shark.programName, u.convertToHtml(u.combineString(Diagnostics_base.errors)) ,
                  new Rectangle(10,10,w,h));

        }
      });
    mversion.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
      // check for additional patches
      File files[] = sharkStartFrame.publicPath.listFiles(new FileFilter() {
        public boolean accept(File file) {
          String s = file.getName();
          return s.endsWith(".pch");
      }});
      String patchtext = "";
      String patchlisttype[] = null;
      int patchlistnumber[] = null;
      for(int i = 0; i < files.length; i++){
         String s = files[i].getName();
         String prefix = s.substring(0, 5);
         String suffix = s.substring(5, s.lastIndexOf('.'));
         int no = -1;
         try{
             no = Integer.parseInt(suffix);
         }
         catch(Exception ex){continue;}
         int p = -1;
         if(patchlisttype==null){
             patchlisttype =new String[]{prefix};
             patchlistnumber=new int[]{no};
         }
         else if((p=u.findString(patchlisttype, prefix))<0){
             patchlisttype = u.addString(patchlisttype, prefix);
             patchlistnumber = u.addint(patchlistnumber, no);
         }
         else if(p>=0){
             if(patchlistnumber[p] < no){
                patchlistnumber[p] = no;
             }
         }
      }
      if(patchlisttype!=null){
          String s1 = u.gettext("mversion", "additionalpatch");
          for(int i = 0; i < patchlisttype.length; i++){
            String num = String.valueOf(patchlistnumber[i]);
            if(num.length()==1)num = "0"+num;
            patchlisttype[i] = patchlisttype[i]+ num;
          }
          Arrays.sort(patchlisttype);
          patchtext = ":s:"+s1+" "+u.combineString(patchlisttype, ", ")+".:s:<br>";
      }

      String mess;
      Date endoverride = null;
      String overrideextra = "";
      if(WebAuthenticateNetwork_base.overrideState!=WebAuthenticateNetwork_base.OVERRIDESTATE_NONE
              && (endoverride=WebAuthenticateNetwork_base.overrideEndDate)!=null){
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyMMdd");
            try{
                Date dnow = sdf.parse(sdf.format(new Date()));
                Calendar datenow = Calendar.getInstance();
                Calendar dateend = Calendar.getInstance();
                datenow.setTime(dnow);
                dateend.setTime(endoverride);
                long daysBetween = 0;
                while (datenow.before(dateend)) {
                    datenow.add(Calendar.DAY_OF_MONTH, 1);
                    daysBetween++;
                }
                String daysb = String.valueOf(Math.max(0, daysBetween));
                while (daysb.length()<3)
                    daysb = "0"+daysb;
                overrideextra = "-"+daysb;
            }
            catch(Exception ex){}
      }
      String versionText = shark.testing?shark.versionNoDetailed.substring(0, 1)+
              "BETA"+shark.versionNoDetailed.substring(1):shark.versionNoDetailed;
      versionText += overrideextra;
//      if(!shark.isLicenceActivated()){
//        mess = u.setHtmlFontSize(      
//             u.edit(u.gettext("mversion", "message"), versionText, patchtext + (shark.network ? (u.gettext("mversion", "serial") + " " + serial + "<br>") : "")),
//                                 treefont, 8, 0);
//      }
//      else{
      String ser = "";
      String licenceid = null;
      String inst = "";
      String networkInstallType = "";
      String lklabel = "";
      if(shark.isLicenceActivated()){
        WebAuthenticateBase_base wab = new WebAuthenticateBase_base();
        String ss[];
        if(shark.network)
            ss = wab.doGetXMLElementValues(sharedPathplus+wan.xmlFileName, new String[]{wan.xmlKey_LicenceKey});
        else
            ss = wab.doGetXMLElementValues(sharedPathplus+u2_base.saXmlFileName, new String[]{wa.ELEMENT_REQUEST, wa.ELEMENT_INSTALLNO, wa.ELEMENT_NOTE});
        String licencekey = ss[0];                
        if(shark.network && serial==null){
            org.w3c.dom.Document xmlDoc = wan.getXMLDocument( new File(sharedPathplus+wan.xmlFileName));
            if(xmlDoc!=null){
                String xmlresult[] = wan.getXMLElement(xmlDoc, wan.xmlKey_LicenceKey);
                if(xmlresult!=null && xmlresult.length>0 && xmlresult[0]!=null)
                serial = wab.getSerial(wab.unChop16(u2_base.swapString(xmlresult[0], wab.userkey, wab.realkey)));
            }
        }
        if(licencekey!=null) {
            if(shark.singledownload){
                String sslk = licencekey.substring(0, 4)+"-"+licencekey.substring(4, 8)+"-"+licencekey.substring(8, 12)+"-"+licencekey.substring(12);
                licenceid = sslk;
            }
            else licenceid = wab.makeCode(wab.userkey, wab.encrypt(licencekey, licencekey), 6);
        }
        String s1;
        inst = (shark.licenceType.equals(shark.LICENCETYPE_STANDALONEACTIVATION) && !shark.singledownload) ? (
          (splitlicence?"":u.gettext("mversion", "installno") + " " + ss[1]) +         
                ( ( (s1 = ss[2]) != null) ?
                 "<br>" + u.gettext("mversion", "installnote") + " " + s1 : "")) : ""; 
        if(shark.licenceType.equals((shark.LICENCETYPE_NETWORKACTIVATION))){
            networkInstallType = (shark.network_serverside?" (Server-based Install)":" (Client-based "+(shark.network_RM?"RM ":"")+"Install)");
        }
        String lkend = " "+licenceid +"<br>";
        if(shark.singledownload){
            lklabel = u.gettext("webauthenticate", "request")+ lkend;
        }
        else if(!(shark.network||licenceid==null)){
            lklabel = u.gettext("mversion", "licenceid")+ lkend;
        }       
      }
      if(serial!=null) ser = shark.network ? (u.gettext("mversion", "serial") + " " + serial + "<br>") : "";
      String versionName = shark.getVersionType(shark.singledownload?shark.LICENCETYPE_STANDALONEACTIVATION_HOME:shark.licenceType, false);
      mess = u.setHtmlFontSize(u.edit(u.gettext("mversion", "message"), versionText, patchtext + (versionName==null?"":(versionName+networkInstallType +"<br>"))+
               lklabel + ser + inst), treefont, 8, 0);
      String heading = u.gettext("mversion", "heading") + " " + shark.programName;
        if(student.fishpanel!=null) {
          // blocking focus from the sign on dialog
          u.focusBlock = true;
          // make it possible to escape out of this dialog
          u.blockEscape = false;
          JOptionPane.showMessageDialog(sharkStartFrame.mainFrame,new String[]{mess},heading,JOptionPane.PLAIN_MESSAGE);
          u.blockEscape = true;
          u.focusBlock = false;
        }
        else
          JOptionPane.showMessageDialog(sharkStartFrame.mainFrame,new String[]{mess},heading,JOptionPane.PLAIN_MESSAGE);
      }
    });
    signlist.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        savesplitwords();
        new student.signonlist();
      }
    });
    mdistribute.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        new distribute();
      }
    });
//startPR2008-08-14^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2009-08-02^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    if(shark.macOS && shark.specialfunc){
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      acl.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          new showacl();
          sharkStartFrame.mainFrame.setState(ICONIFIED);
        }
      });
    }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    if (shark.network){
//startPR2008-11-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      if(shark.licenceType.equals(shark.LICENCETYPE_NETWORK)){
//endPRauth^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        netkeyup.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          int i;
          new networkkey_base(false);
        }
      });
//startPR2008-11-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      }
//endPRauth^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2007-07-12^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2007-11-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      if(shark.macOS){
      if(shark.macOS||shark.linuxOS){
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        sharedloc.addActionListener(new java.awt.event.ActionListener() {
          public void actionPerformed(ActionEvent e) {
//startPR2009-07-14^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//            if(shark.runlocally){
//              mainFrame.setVisible(false);
//              chooseshared_base dia = new chooseshared_base(false, true);
//              dia.setVisible(true);
//              mainFrame.setVisible(true);
//            }
//            else{
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
              JOptionPane getpw = new JOptionPane(
                u.gettext("changeshared","allsignedoff"),
                JOptionPane.PLAIN_MESSAGE,
                JOptionPane.OK_CANCEL_OPTION);
              JDialog dialog2 = getpw.createDialog(sharkStartFrame.mainFrame,u.gettext("changeshared","change", shark.programName.toLowerCase()));
              dialog2.setVisible(true);
              Object result = getpw.getValue();
              if(!(result == null
                   || result instanceof Integer
                   &&((Integer)result).intValue() != JOptionPane.OK_OPTION)){
                mainFrame.setVisible(false);
//startPR2009-07-14^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//                chooseshared_base dia = new chooseshared_base(false, true);
                chooseshared_base dia = new chooseshared_base(true);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                dia.setVisible(true);
                if(chooseshared_base.changed){
                  closeprev(true);
                  student.signoffAll();
                  db.closeAll();
                  mainFrame.dispose();
                  u2_base.restart_Mac(System.getProperty("user.dir") +
                          "/"+shark.programName+" "+shark.versionNo+
                          shark.getVersionType(shark.LICENCETYPE_NETWORK)+".app/Contents/Restart.app");
                  System.exit(0);
                }
                else{
                  mainFrame.setVisible(true);
                }
              }
//startPR2009-07-14^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//            }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          }
        });
//startPR2009-07-14^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//        runlocally.addActionListener(new java.awt.event.ActionListener() {
//          public void actionPerformed(ActionEvent e) {
//             String mess, messtitle;
//            if(shark.runlocally){
//              mess = u.gettext("changeshared","serverreconnect");
//              messtitle = u.gettext("changeshared","serverrun");
//            }
//            else{
//              mess = u.gettext("changeshared","runlocalmess");
//              messtitle = u.gettext("changeshared","runlocally");
//            }
//            JOptionPane getpw = new JOptionPane(mess, JOptionPane.PLAIN_MESSAGE,
//              JOptionPane.OK_CANCEL_OPTION);
//            JDialog dialog2 = getpw.createDialog(sharkStartFrame.mainFrame, messtitle);
//            dialog2.setVisible(true);
//            Object result = getpw.getValue();
//            if (!(result == null
//                  || result instanceof Integer
//                  && ( (Integer) result).intValue() != JOptionPane.OK_OPTION)) {
//              if(!shark.runlocally)
//startPR2007-11-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//                chooseshared_base.xmlSet(chooseshared_base.XMLFILE, chooseshared_base.RUNLOCALLY, "true");
//                chooseshared_base.xmlSet(shark.xmlNetworkFile, chooseshared_base.RUNLOCALLY, "true");
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//              restart();
//              mainFrame.finalize();
//            }
//          }
//        });
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    }
//startPR2008-06-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    helpmenu.addMenuListener(new MenuListener() {
//      public void menuSelected(MenuEvent me) {u.focusBlock = true;}
//      public void menuDeselected(MenuEvent me) {u.focusBlock = false;}
//      public void menuCanceled(MenuEvent me) {u.focusBlock = false;}
//    });
//    meuFile.addMenuListener(new MenuListener() {
//      public void menuSelected(MenuEvent me) {u.focusBlock = true;}
//      public void menuDeselected(MenuEvent me) {u.focusBlock = false;}
//      public void menuCanceled(MenuEvent me) {u.focusBlock = false;}
//    });
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    pictures.setName("pictures");
    pictures.addActionListener(new sharkStartFrame_pictures_actionAdapter(this));
    signoff.setEnabled(false);
    if (save != null)
      meuFile.add(save);
    meuFile.add(signoff);
//    meuFile.add(printlist);
//startPR2007-02-16^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    setPrintMenu(false);
//    printlist.add(mprintflash);
//    printlist.add(mprintflash2);
//    printlist.addSeparator();
//    printlist.add(mprint1);
//    printlist.add(mprint2);
//    printlist.add(mprint4);
//    printlist.add(mprint8);
//    printlist.add(mprintfg);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2007-09-03^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    meuFile.add(mexplore);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    if (shark.specialfunc) {
      meuFile.add(print);
      meuFile.add(findreplace);
      meuFile.add(importsentences);    
      if (publicTextLib.length >= 2)
        meuFile.add(mergetext);
      if (db.exists("publicsay2"))
        meuFile.add(mergesay);
      if (db.exists("publicsent1x"))
        meuFile.add(mergesent1);
      if (db.exists("publicsent2x"))
        meuFile.add(mergesent2);
      if (publicImageLib.length >= 2 && !db.exists("publicimagew"))
        meuFile.add(mergeimage);
      if (db.exists("publicimage2") && db.exists("publicimagew"))
        meuFile.add(mergeimage23);
//startPR2008-06-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      if (new File(sharedPathplus+"mergeimages").exists()&&new File(sharedPathplus+"mergeimages").list().length>1)
        meuFile.add(mergemultimage);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      if(mextrasoundpatches!=null)
          meuFile.add(mextrasoundpatches);
      if (db.exists("publictopics2"))
        meuFile.add(mergetopics);
      meuFile.add(compresspublic);
      meuFile.add(newdb);
    }
    printlist.setEnabled(false);
//    meuFile.add(signoff);
//    signoff.setEnabled(false);
//    if (madmin != null)
//      meuFile.add(madmin);

//    meuFile.add(mversion);
    meuFile.add(menuFileExit);
    if(!Demo_base.isDemo){
        manBar1.add(meuFile);
          studentmenu.removeAll();
          studentmenu.add(madminlist);
          if(shark.network || shark.specialfunc) studentmenu.add(muserlist);
        manBar1.add(studentmenu);
    }
//startPR2010-03-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      if (shark.wantTutVids) {
          mvidaddstu.addActionListener(new java.awt.event.ActionListener() {
              public void actionPerformed(ActionEvent e) {
                  u.launchWebSite(u.gettext("videotutorials", "basesite", shark.getProgramShortName()) + u.gettext("mvidaddstu", "url"));
              }
          });
//          mvidcoursehelp.addActionListener(new java.awt.event.ActionListener() {
//              public void actionPerformed(ActionEvent e) {
//                  u.launchWebSite(u.gettext("videotutorials", "basesite", shark.getProgramShortName()) + u.gettext("mvidcoursehelp", "url"));
//              }
//          });
          mvidchooselist.addActionListener(new java.awt.event.ActionListener() {
              public void actionPerformed(ActionEvent e) {
                  u.launchWebSite(u.gettext("videotutorials", "basesite", shark.getProgramShortName()) + u.gettext("mvidchooselist", "url"));
              }
          });
          mvidchoosegame.addActionListener(new java.awt.event.ActionListener() {
              public void actionPerformed(ActionEvent e) {
                  u.launchWebSite(u.gettext("videotutorials", "basesite", shark.getProgramShortName()) + u.gettext("mvidchoosegame", "url"));
              }
          });
          mvidsearch.addActionListener(new java.awt.event.ActionListener() {
              public void actionPerformed(ActionEvent e) {
                  u.launchWebSite(u.gettext("videotutorials", "basesite", shark.getProgramShortName()) + u.gettext("mvidsearch", "url"));
              }
          });
          mvidprivatelist.addActionListener(new java.awt.event.ActionListener() {
              public void actionPerformed(ActionEvent e) {
                  u.launchWebSite(u.gettext("videotutorials", "basesite", shark.getProgramShortName()) + u.gettext("mvidprivatelist", "url"));
              }
          });
          mvidphonics.addActionListener(new java.awt.event.ActionListener() {
              public void actionPerformed(ActionEvent e) {
                  u.launchWebSite(u.gettext("videotutorials", "basesite", shark.getProgramShortName()) + u.gettext("mvidphonics", "url"));
              }
          });
          mvidgettingstarted.addActionListener(new java.awt.event.ActionListener() {
              public void actionPerformed(ActionEvent e) {
                  u.launchWebSite(u.gettext("videotutorials", "basesite", shark.getProgramShortName()) + u.gettext("mvidgettingstarted", "url"));
              }
          });
//          mvidoptions.addActionListener(new java.awt.event.ActionListener() {
//              public void actionPerformed(ActionEvent e) {
//                  u.launchFile(publicPathplus + "resources" + File.separator + u.gettext("mvidoptions", "filename"), true);
//              }
//          });
          mvidsetwork.addActionListener(new java.awt.event.ActionListener() {
              public void actionPerformed(ActionEvent e) {
                  u.launchWebSite(u.gettext("videotutorials", "basesite", shark.getProgramShortName()) + u.gettext("mvidsetwork", "url"));
              }
          });
          mvidtrackingprogress.addActionListener(new java.awt.event.ActionListener() {
              public void actionPerformed(ActionEvent e) {
                  u.launchWebSite(u.gettext("videotutorials", "basesite", shark.getProgramShortName()) + u.gettext("mvidtrackingprogress", "url"));
              }
          });
          mvideotutorial.add(mvidgettingstarted);
          mvideotutorial.add(mvidaddstu);
//          mvideotutorial.add(mvidcoursehelp);
          mvideotutorial.add(mvidchooselist);
          mvideotutorial.add(mvidchoosegame);
          mvideotutorial.add(mvidphonics);
          mvideotutorial.add(mvidsearch);
          mvideotutorial.add(mvidprivatelist);
//          mvideotutorial.add(mvidoptions);
          mvideotutorial.add(mvidsetwork);
          mvideotutorial.add(mvidtrackingprogress);
      }
    buildHelpMenu(true);
    helpmenu.setBackground(runMovers.tooltipbg);    
//startPR2004-08-27^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^"^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2008-06-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
// if running on Windows
//     if (!shark.macOS)
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    if(!Demo_base.isDemo)
        manBar1.add(helpmenu);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    msearch.add(msearchi);
//startPR2010-02-08^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    moverride.addActionListener(new java.awt.event.ActionListener() {
//      public void actionPerformed(ActionEvent e) {
//        setoverride(moverride.getState());
//      }
//    });
//endPRtoupdate^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    moverride.addActionListener(new java.awt.event.ActionListener() {
//      public void actionPerformed(ActionEvent e) {
//        setoverride(moverride.getState());
//      }
//    });
//    mwanthello.addActionListener(new java.awt.event.ActionListener() {
//      public void actionPerformed(ActionEvent e) {
//        if (!mwanthello.getState())
//          db.update(optionsdb, "nohello", new String[] {""}
 //                   , db.TEXT);
 //       else
 //         db.delete(optionsdb, "nohello", db.TEXT);
 //     }
//    });
//    mwantshapes.addActionListener(new java.awt.event.ActionListener() { // start rb 27-11-07
//      public void actionPerformed(ActionEvent e) {
//        if (wantshapes = mwantshapes.getState())
//          db.update(optionsdb, "wantshapes", new String[] {""}
//                    , db.TEXT);
//        else
//          db.delete(optionsdb, "wantshapes", db.TEXT);
//        setupgames();
//      }
//    });
    if(shark.phonicshark)
        notwantonsetrime = false;
    else{
        mnotwantonsetrime.addActionListener(new java.awt.event.ActionListener() {
          public void actionPerformed(ActionEvent e) {
            if (notwantonsetrime = mnotwantonsetrime.getState())
              db.update(optionsdb, "notwantonsetrime", new String[] {""}
                        , db.TEXT);
            else
              db.delete(optionsdb, "notwantonsetrime", db.TEXT);
            setupgames();
          }
        });    
    }//end rb 27-11-07
    mwanthelp.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        setwanthelp(mwanthelp.getState());
      }
    });
    morenoise.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        addadjustbeepvol(null);   // pr2006-10-26
      }
    });
//    nogroan.addActionListener(new java.awt.event.ActionListener() {
//      public void actionPerformed(ActionEvent e) {
//        if (noise.nogroan =  nogroan.getState())
//          student.setOption("s_nogroan");
//        else
//          student.clearOption("s_nogroan");
//      }
//    });
//startPR2010-05-24^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    showallgames.setBackground(col2);
//    showallgames.setForeground(Color.red);
//    showallgames.setForeground(Color.blue);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2010-05-24^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    showallgames.round = true;
//    showallgames.bordercol = Color.blue;
//    showallgames.fillcolor = col1;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2007-11-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    showallgames.setFont(new Font(showallgames.getFont().getName(),
//                                  showallgames.getFont().getStyle(),
//                                  showallgames.getFont().getSize()*4/3));
//startPR2010-05-24^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    showallgames.setFont(showallgames.getFont().deriveFont((float)showallgames.getFont().getSize()*4/3));
 //   showallgames.setFont(treefont);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    showallgames.addActionListener(new java.awt.event.ActionListener() {
//       public void actionPerformed(ActionEvent e) {
//              if(showmarkedgames) {
//                    showmarkedgames = false;
//                    student.setOption("notmarkedgames");
//                    sametopic=true;
//                    lastrect = -1;
//startPR2008-09-03^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//                    setupGametree();
//                    setupGametree(false, true);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//              }
//              else if(canshowmarkedgames) {
//                        showmarkedgames = true;
//                        student.clearOption("notmarkedgames");
//                        sametopic=true;
//                        lastrect = -1;
//startPR2008-09-03^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//                    setupGametree();
//                        setupGametree(false, true);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//              }
//       }
//    });
//startPR2008-06-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    mwantsignvids.addItemListener(new ItemListener() {
//      public void itemStateChanged(ItemEvent e) {
//        if (currStudent >= 0) {
//          studentList[currStudent].wantsignvids = mwantsignvids.getState();
//        }
//      }
//    });
//endPRimagesandvids^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    mwantsigns.addItemListener(new ItemListener() {
//      public void itemStateChanged(ItemEvent e) {
//        if (currStudent >= 0) {
//          studentList[currStudent].wantsigns = mwantsigns.getState();
//        }
//      }
//    });
//    mwantrealpics.addItemListener(new ItemListener() {
//      public void itemStateChanged(ItemEvent e) {
//        if (currStudent >= 0)
//          studentList[currStudent].wantrealpics = mwantrealpics.getState();
//      }
//    });
//    mwantfingers.addItemListener(new ItemListener() {
//      public void itemStateChanged(ItemEvent e) {
//        if (currStudent >= 0)
//          studentList[currStudent].wantfingersall = mwantfingers.getState();
//      }
//    });
//    mpicbg.addItemListener(new ItemListener() {
//      public void itemStateChanged(ItemEvent e) {
//        if (currStudent >= 0)
//          studentList[currStudent].picbg = mpicbg.getState();
//      }
//    });

//startPR2010-05-24^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    switchtogames.setCursor(handcursor);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    switchtogames.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        wordlist.removeCurrPic();
//startPR2010-05-24^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//        gameicons = true;
        gameicons = !gameicons;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        switchdisplay();
      }
    });
//startPR2008-06-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    checkCDTimer = (new Timer(500, new ActionListener() {
//      public void actionPerformed(ActionEvent e) {
//        if(checkcd()>=0)checkCDTimer.stop();
//      }
//    }));
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR


    features.add(choosesprite);
    features.add(chooseicon);
    features.add(mchoosekeypad);
    features.add(msetrewards);
    features.add(restrictcourses);
    features.addSeparator();
    features.add(mfontchange2);
    features.add(morenoise);
    features.add(picpref);
    features.add(changepassword);
    features.add(mnomove);
    features.add(bgcolor);


    addchoosekeypad();


//    features.add(choosesprite);
//    features.add(picmenu);
//    features.add(mfontchange2);
//    features.add(morenoise);
//    features.add(nogroan);
//    features.add(mpeep);
//    features.add(restrictcourses);
//    features.addSeparator();
//    features.add(mchoosekeypad);
//    features.add(mswitchaccess);
//    features.addSeparator();
//    features.add(easywordlist);
//    features.add(easyrecord);
//    features.addSeparator();
//    features.add(studentrecord);
//    features.add(changepassword);
//    features.addSeparator();
//    features.add(mteachnotes);
//    features.addSeparator();
//    features.add(mnoreward);
//startPR2004-10-22^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    features.add(mnogamble);
//    features.add(msetrewards);
//    features.add(mrewardfreq);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    features.addSeparator();
//    features.add(mnomove);
//    features.add(bgcolor);
//    addchoosekeypad();

//    mownwords.add(easywordlist);
//    mownwords.add(easyrecord);
//startPR2008-06-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR

//    picmenu.add(mPickPicture);
//    picmenu.add(picpref);
//    picmenu.addSeparator();
//endPRimagesandvids^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    picmenu.add(mwantrealpics);
//startPR2005-11-20^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    if(!shark.language.equals(shark.LANGUAGE_NL)){
//startPR2008-06-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      picmenu.add(mwantsignvids);
///endPRimagesandvids^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
 //     picmenu.add(mwantsigns);
 //     picmenu.add(mwantfingers);
 //     picmenu.addSeparator();
 //   }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    picmenu.add(mpicbg);
//startPR2007-11-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    mfontchange.add(chooseFontInfant);
//    mfontchange.add(chooseFont);
//    mfontchange.add(defaultfont);
//    mfontchange.addSeparator();
//    mfontchange.add(mtreefont);
//    mfontchange.add(notreefont);
//    mfontchange2.add(chooseFont2);
//    mfontchange2.add(defaultfont2);
//    mfontchange2.addSeparator();
//    mfontchange2.add(mtreefont2);
//    mfontchange2.add(notreefont2);
//    mfontchange.add(fontStandardGames_change);
//    mfontchange.add(fontMenus_change);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2010-05-04^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR

//    msignon.add(mwanthello);
//    msignon.add(mnotemp);
//    msignon.add(mactivedirectory);
//    mactivedirectory.add(mautosignon);
//    mactivedirectory.add(mautosigncreatestu);
//    msignon.add(msideprompt);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    morenoise.setIcon(new ImageIcon(publicPathplus + "sprites" +
                                       sharkStartFrame.separator +
                                       "speakerON_il16.png"));
    if (shark.specialfunc) {
      men1.add(cut);
      men1.add(copy);
      men1.add(paste);
      men1.add(undo);
      men1.add(pasteimage);
      men1.add(find);
      men1.add(findnext);
      mplay.add(play);
    }
    bevelPanel1.setLayout(gridBagLayout1);
    bevelPanel2.setLayout(gridBagLayout2);
    bevelPanel3.setLayout(gridBagLayout3);
//startPR2010-05-24^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    bevelPanel4.setLayout(borderLayout4);
//    bevelPanel4.setBorder(BorderFactory.createLineBorder(gamescolor, 2));
//        bevelPanel5.setLayout(borderLayout5);
    bevelPanel4.setLayout(new GridBagLayout());
    bevelPanel4.setBorder(BorderFactory.createLineBorder(topictreecolor, 2));
    bevelPanel5.setLayout(new GridBagLayout());
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    bevelPanel5.setBorder(BorderFactory.createLineBorder(gamescolor, 2));
    bevelPanel6.setLayout(borderLayout6);
//startPR2010-05-24^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    bevelPanelCourses.setBorder(BorderFactory.createLineBorder(gamescolor, 2));

//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    if(!Demo_base.isDemo)
        setJMenuBar(manBar1);
    vpanel2.setLayout(vgridBagLayout2);
    vpanel3.setLayout(vgridBagLayout3);
  }

  public void finalize() {
    this.setVisible(false);
    closeprev(false);
    student.signoffAll();
    privateListRecord.check();
    db.closeAll();
    System.exit(0);
  }


void playhelpimage(){
    jnode sel = topicTreeList.getSelectedNode();
    if (sel == null || !sel.get().startsWith("help_"))return;
    String s = currPicture.getPartString();
    if(s==null)return;      
    if(!spokenWord.findandsaysentence(s))  
       spokenWord.findandsay(s);
}

  class sharedplay extends mover.picandname {
    String maintext;
    float buttonalpha = 0.3f;
    boolean something = true;
    Vector choosenames  = new Vector();
    Rectangle r;
    String ssharewith = u.gettext("sharedplay", "sharewith");
    boolean chosen = false;
    Font f1;
    Font f2;
    FontMetrics ftm1;
    FontMetrics ftm2;
    sharedplay tomove;
    int texth;
    String strnoshare=u.gettext("sharedplay", "nosharing");
    boolean showfull = false;


     sharedplay(float alpha, int he) {
        super((u.gettext("sharedplay", "sharewith")), gamePanel, he, mover.picandname.MODE_OTHERUSER);
        maintext = text;
     }

     sharedplay(String name, int he) {
        super(name, gamePanel, he, mover.picandname.MODE_USERTOCHOOSE);
        maintext = text;
     }
     sharedplay(String name, int he, int type) {
        super(name, gamePanel, he, type);
        maintext = text;
     }

     void showsharers() {
         if(!studentList[currStudent].option("nomove")){
                for (int i = 0; i < gameiconlist.length; ++i) {
                  if (gameiconlist[i] != null)
                    gameiconlist[i].frozen = true;
                }
            }
         temptext = ssharewith;
         choosenames.clear();
         String choose[] = null;
         for(int i = 0; i < studentList.length; i++){
            if(studentList[currStudent].name.equals(studentList[i].name))continue;
                if(choose==null)choose=new String[]{};
                choose =  u.addString(choose, studentList[i].name);
         }
         int j2 = this.y+this.h;
         int g = -1;
         int lowesty = 0;
         int cextra = tomove==null?0:1;
         int yy = -1;
         int i;
         sharedplay tt = new sharedplay(ssharewith, this.h);
         int widest = tt.w;
         ArrayList aly = new ArrayList();
         for(i = 0; choose!=null && i < choose.length; i++){
              sharedplay sqn = new sharedplay(choose[i], this.h);
              if(g<0)g=sqn.h/7;
              yy = backsharemargin+j2+((cextra+i)*(sqn.h)+((cextra+i)*g));
              aly.add(new Integer(yy));
              if(sqn.w > widest){
                  widest = sqn.w;
              }
              lowesty = yy+sqn.h;
              choosenames.add(sqn);
         }
         if(mode==mover.picandname.MODE_OTHERUSERCHOSEN){
            sharedplay sqn = new sharedplay(strnoshare,this.h,mover.picandname.MODE_NOSHARE);
              if(g<0)g=sqn.h/7;
              yy = backsharemargin+j2+((cextra+i)*(sqn.h)+((cextra+i)*g));
              aly.add(new Integer(yy));
              if(sqn.w > widest){
                  widest = sqn.w;
              }
              lowesty = yy+sqn.h;
              choosenames.add(sqn);
         }
         sharedp.viewshares = true;
         backsharerect.w = widest + (backsharemargin*2);
         backsharerect.h = lowesty + backsharemargin;
         gamePanel.bringtotop(backsharerect);
         gamePanel.bringtotop(sharedp);
         for(int n = 0; n < choosenames.size(); n++){ 
             gamePanel.addMover((sharedplay)choosenames.get(n), sharedp.x, ((Integer)aly.get(n)).intValue());
         }
         r = new Rectangle(backsharerect.x,backsharerect.y,backsharerect.w, backsharerect.h);
     }


     public void changeImage(long time) {
         if(backshareremoveat>0 && backshareremoveat<System.currentTimeMillis()){
            sharersOff();
            backshareremoveat = -1;
            mode = mover.picandname.MODE_OTHERUSERCHOSEN;
            handcursor = true;
            sharedp = this;
         }
        if(mode!=mover.picandname.MODE_OTHERUSER && mode!=mover.picandname.MODE_OTHERUSERCHOSEN)return;
        if(sharedp.viewshares && !mouseOverRect(r)){
             sharedp.viewshares = false;
             temptext = null;
            sharersOff();
            for(int i = 0; i < choosenames.size(); i++){
                gamePanel.removeMover((mover)choosenames.get(i));
            }
            choosenames.clear();
        }
     }

     boolean mouseOverRect(Rectangle r) {
         if(gamePanel==null || r == null)return false;
         return gamePanel.mousex > r.x
                 && gamePanel.mousex < r.x+r.width
                 && gamePanel.mousey > r.y
                 && gamePanel.mousey < r.y+r.height;
     }

     public void reset(){
        chosen = false;
        for(int i = 0; i < choosenames.size(); i++){
            gamePanel.removeMover((sharedplay)choosenames.get(i));
        }
        sharersOff();
        backshareremoveat = -1;
        gamePanel.removeMover(tomove);
        tomove = null;
        sharedp.viewshares = false;
        sharedp.reset2();
     }

 public void sharersOff() {
            for (int i = 0; i < gameiconlist.length; ++i) {
                  if (gameiconlist[i] != null)
                    gameiconlist[i].frozen = mnomove.getState();
            }
       backsharerect.w = 1;
        backsharerect.h = 1;
 }


     public void mouseClicked(int x, int y) {
         if(mode==mover.picandname.MODE_NOSHARE){
             sharedp.reset();
             for(int i = 0; i < mtwoplayers.getItemCount(); i++){
                 JCheckBoxMenuItem cmi = (JCheckBoxMenuItem)mtwoplayers.getItem(i);
                 if(cmi.isSelected()){
                     doTwoPlayerMenuClick(cmi, false);
                 }
                 cmi.setSelected(false);
             }
         }
         else if(mode==mover.picandname.MODE_USERTOCHOOSE){
             sharedp.chosen(text);
         }
         else if(!viewshares && (mode==mover.picandname.MODE_OTHERUSER || mode ==mover.picandname.MODE_OTHERUSERCHOSEN)){
             showsharers();
         }
     };

     public void chosen(String t){
         gamePanel.removeMover(tomove);
         tomove = null;
         for(int i = 0; i < choosenames.size(); i++){
                sharedplay tn = (sharedplay)choosenames.get(i);
                if(!tn.text.equals(t))
                    gamePanel.removeMover(tn);
                else tomove = tn;
            }
         choosenames.clear();
         if(tomove!=null){
             chosen = true;
             tomove.moveto(sharedp.x, sharedp.y, 200);
             tomove.change(tomove.text, mover.picandname.MODE_OTHERUSERCHOSENMOVING);
             backshareremoveat = System.currentTimeMillis()+200;
//             sharkStartFrame.sharewith = t;
             addsharers(studentList[currStudent].name, t);
         }
         gamePanel.removeMover(sharedp);
         tomove.mouseOver = false;

         for(int i = 0; i < mtwoplayers.getItemCount(); i++){
             JCheckBoxMenuItem cmi = (JCheckBoxMenuItem)mtwoplayers.getItem(i);
             if(cmi.getText().equals(t)){
                 if(!cmi.isSelected()){
                     doTwoPlayerMenuClick(cmi, false);
                 }
             }
             else cmi.setSelected(false);
         }
     }
  }




    void addsharers(String curruser, String otheruser){
        if(sharewith==null){
            sharewith = new ArrayList();
        }
        for(int i = 0; i < sharewith.size(); i++){
            String[] ss = (String[])sharewith.get(i);
            if(ss[0].equals(curruser)){
                ss[1] = otheruser;
                sharewith.set(i, ss);
                return;
            }
        }
        sharewith.add(new String[]{curruser, otheruser});
     }

    void removesharers(String curruser){
        for(int i = 0; i < sharewith.size(); i++){
            String[] ss = (String[])sharewith.get(i);
            if(ss[0].equals(curruser)){
                sharewith.remove(i);
                return;
            }
        }
     }



  /**
   * The keypad configuration
   * <li>Adds menu items
   * <li>Contains anonymous listener for configuring the keypad and sets the
   *     student option
   */
  void tonext() {    // advance to next topic  ---------------  rb 22/02/08 v5
    jnode old = topicList.getSelectedNode();
    if (old != null) {
      jnode next = (jnode) old.getNextLeaf();
      if(wantplayprogram && next.getLevel() > 2 ) {  //  rb 16/3/08  mmmm  start
        jnode pa = next;
        do {pa = (jnode)pa.getParent();} while (pa.getLevel() > 2);
        if(pa.dontexpand) next = pa;
      }                                               //  rb 16/3/08  mmmm end
      if (next != null) {
//startPR2007-12-02^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//            if(next.get().trim().equals(""))next=(jnode)next.getNextLeaf();
//            if(next == null) return;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        usingsuperlist = null;
        specialsuperlist = false;
        useselectedlist = false;
        forcechange = true;
//startPR2010-05-24^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//        topicList.setSelectionPath(new TreePath(next.getPath()));
        TreePath tp = new TreePath(next.getPath());
        topicList.setSelectionPath(tp);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        canshowmarkedgames = false;
        setupGametree();
        addteachingnotes(false);
//startPR2010-05-24^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        bevelPanel4.validate();
        topicList.scrollPathToVisible(tp);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      }
    }
  }
  public void addchoosekeypad() {
    mchoosekeypad.removeAll();
    String kp[] = u.addString(db.list(publicKeypadLib,
                                      db.SAVEKEYPAD),
                              db.list(sharedPathplus + "keypad", db.SAVEKEYPAD));
    String skp[] =  u.splitString(u.gettext("keypad_","hide"));
    if (kp != null && skp !=null) {
        for (int i = 0; i < skp.length; i++) {
            if((u.findString(kp, skp[i]))>=0)
                kp = u.removeString2(kp,skp[i]);
        }
    }
    mkeypads = new JCheckBoxMenuItem[kp.length];
    if (kp != null) { //If 1
      u.sort(u.stripdup(kp));
      for (int i = 0; i < kp.length; ++i) { //for 1.1
        JCheckBoxMenuItem mm = mkeypads[i] = new u.myCheckBoxMenuItem(kp[i]);
        mchoosekeypad.add(mm);
        mm.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            JCheckBoxMenuItem currkeypad = ( (JCheckBoxMenuItem) e.getSource());
            if (currkeypad.isSelected()) { //If 1.1.1
              keypad.keypadname = currkeypad.getText();
              student.setOption("keypad", keypad.keypadname);
              Font ff = keypad.getfont(keypad.keypadname);
              if (ff != null) { //If 1.1.1.1
                wordfont = ff;
                if (wordTree != null) //If 1.1.1.1.1
                  wordTree.font = null;
                wordTree.repaint();
              }
              setkeydis();
              mnokeypad.setSelected(false);
             }
             else {
               currkeypad.setSelected(true);  // can only click on
            }
          }
        });

      }
      mnokeypad = u.CheckBoxMenuItem("mnokeypad");
      mchoosekeypad.add(mnokeypad);
      mnokeypad.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
           if (mnokeypad.isSelected()) {
             for (int i = 0; i < mkeypads.length; ++i) { //for 1.1
               if (mkeypads[i].isSelected()) {
                 mkeypads[i].setSelected(false);
                 keypad.keypadname = null;
                 student.removeOption("keypad");// can only click on
                 setwordfont();
               }
             }
           }
           else mnokeypad.setSelected(true);
         }

       });
                         // end rb 27/10/06
    }
    
    setkeydis();
  }


  public void setadmingamefont(){
        String fontdet[] = (String[]) db.find(sharkStartFrame.optionsdb, "wordfont", db.TEXT);
        String sfont = fontdet==null?defaultfontname.getName():fontdet[0];
        int style = fontdet==null?Font.PLAIN:Integer.parseInt(fontdet[1]);
        Font f = u.fontFromString(sfont, style, BASICFONTPOINTS);
        originalFont = f;
        fontdet = (String[]) db.find(sharkStartFrame.optionsdb, "wordfontinfant", db.TEXT);
        sfont = fontdet==null?defaultinfantfontname.getName():fontdet[0];
        style = fontdet==null?Font.PLAIN:Integer.parseInt(fontdet[1]);
        Font finfant = u.fontFromString(sfont, style, BASICFONTPOINTS);
        originalInfantFont = finfant;
        new FontChooser(u.gettext("choosegamefont", "label"), f,
          defaultfontname,
          finfant,
          defaultinfantfontname,
          false, false, true, sharkStartFrame.mainFrame) {
          public void update() { //OVERRIDE FONTCHOOSER.UPDATE()
            boolean changed = false;
            if (!chosenfont.equals(originalFont)){  //ONLY UPDATE THINGS IF THE FONT HAS CHANGED.
              db.update(optionsdb, "wordfont", new String[] {chosenfont.getName(),
                        String.valueOf(chosenfont.getStyle())}
                        , db.TEXT);
              if(chosenfont.getFontName().equalsIgnoreCase(defaultfontname.getFontName()))
                db.delete(optionsdb, "wordfont", db.TEXT);
              changed = true;
            }
            if(!(chosenInfantFont.equals(originalInfantFont))){
              db.update(optionsdb, "wordfontinfant", new String[] {chosenInfantFont.getName(),
                        String.valueOf(chosenInfantFont.getStyle())}
                        , db.TEXT);
              if(chosenInfantFont.getFontName().equalsIgnoreCase(defaultinfantfontname.getFontName()))
                db.delete(optionsdb, "wordfontinfant", db.TEXT);
              changed = true;
            }
            if(changed){
              setwordfont();
              if (gamePanel != null) {
                buildgamepanel2(true);
              }
            }
          }
        };
  }

  /**
   * Sets up universal options, and also sets up default font.
   */
  void setoptions() {
    optionsdb = sharedPathplus + "options";
//startPR2009-04-24^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    nooptions = !new File(optionsdb+".sha").exists();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2010-04-22^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       if (nooptions){
          u2_base.setNewFilePermissions(sharkStartFrame.sharedPath);
       }
//endPRtoupdate^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2010-03-05^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       u.defaultfont();
    if(!Demo_base.isDemo){
        // 2 error code in publictext
        if (shark.macOS && shark.production &&
                !shark.network && (shark.isLicenceStandard())) {
            String sscp = sharedPathplus + "tmp" + File.separator + "cp001";
            File ff3 = new File(sscp);
            boolean ok = false;
            String ss3 = "";
            int counter = 0;
            progress_base checkpb = null;
            while (!ff3.exists() || ((ss3 = readFile(sscp)).trim().equals(""))) {
                if(checkpb == null)
                    checkpb = new progress_base(this, shark.programName,
                                       u.gettext("initializing", "label"),
                                           new Rectangle(sharkStartFrame.screenSize.width/4,
                                                         sharkStartFrame.screenSize.height*2/5,
                                                         (sharkStartFrame.screenSize.width/2),
                                                         (sharkStartFrame.screenSize.height/4)), true);
                if(counter>20){
                    checkpb.dispose();
//                    u.okmess(shark.programName, u.gettext("errorcodes", "errorcode16"), mainFrame);
                    OptionPane_base.getErrorMessageDialog(mainFrame, 16, u.gettext("errorcodes", "errorcode16"), OptionPane_base.ERRORTYPE_EXIT);
                }
                counter++;
                u.pause(3000);
            }
            if(checkpb!=null){
                checkpb.dispose();
                checkpb = null;
            }
            if (ff3.exists()) {
                try {
                    String buf = shark.toret(shark._encrypt("1JQVF8tdhtNRx2YbKzoVe0uZF8CUnlGmcZaLa3wGnhYlQwS3JARyOII0uedbcCN6HSyOLMkUvikA7T2mv6Hx7T99iM1Kzo".toCharArray()));
                    if (ss3 != null) {
                        ss3 = ss3.trim();
                        if (buf.equals(ss3)) {
                            ok = true;
                        }
                    }
                    // clear the file
                    FileOutputStream fos = new FileOutputStream(ff3);
                    fos.close();
                } catch (Exception ex) {
                }
            }
            if (!ok) {
//                u.okmess(shark.programName, u.gettext("errorcodes", "errorcode17"), mainFrame);
                OptionPane_base.getErrorMessageDialog(mainFrame, 17, u.gettext("errorcodes", "errorcode17"), OptionPane_base.ERRORTYPE_EXIT);
            }
        }
      newVersionBackup();
    }
//endPRtoupdate^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    wanthello = (db.query(optionsdb, "nohello", db.TEXT) < 0);
//    wantshapes = (db.query(optionsdb, "wantshapes", db.TEXT) >= 0);       // rb 27-11-07
    notwantonsetrime = (db.query(optionsdb, "notwantonsetrime", db.TEXT) >= 0); // rb 27-11-07
    spellchange.spellchangesetup();
    
//    u.defaultfont();
    setwordfont();
  }

  void zipUpShared(boolean anonymous, boolean forMigration){
            try {
                String ss[] = new String[0];
                if(shark.network){
                    ss = student.userlist.getUserList();
                    String computerName = u.getComputerName() + "  ";
                    String osSignOnName = System.getProperty("user.name") + "  ";
                    String userName = sharkStartFrame.studentList[sharkStartFrame.currStudent].name;
                    for(int i = ss.length-1; i >= 0; i--){
                        if(ss[i].indexOf(computerName)>=0 && ss[i].indexOf(osSignOnName)>=0 && ss[i].indexOf(userName)>=0)
                            ss = u.removeString(ss, i);
                    }
                    if(ss.length>0){
                        String mess[] = u.splitString(u.edit(u.gettext("mzipshared", "mess2warn"), shark.programName, shark.programName.toLowerCase()));
                        int i;
                        for(i = 0; i < mess.length; i++){
                            if(mess[i].equals("@")){
                                mess = u.removeString(mess, i);
                                break;
                            }
                        }
                        for(int k = ss.length-1; k >= 0; k--){
                             mess = u.addString(mess, ss[k], i);
                        }
                        u.okmess(u.gettext("mzipshared", "label", shark.programName.toLowerCase()),
                               u.convertToHtml(u.combineString(mess)) ,
                                sharkStartFrame.mainFrame);
                        return;
                    }
                }
                if(!u.yesnomess(
                    u.gettext("mzipshared", "label", shark.programName.toLowerCase()),
                         u.convertToHtml( u.edit(u.gettext("mzipshared", "mess1warn"), shark.programName.toLowerCase(), shark.programName)), sharkStartFrame.mainFrame)) return;

                Thread zipSharedThread = new Thread(new doZipShared(anonymous));
                zipSharedThread.start();
                new progress_base(sharkStartFrame.mainFrame, shark.programName,
                                           u.gettext("mzipshared", "creating", shark.programName.toLowerCase()),
                                           new Rectangle(sharkStartFrame.mainFrame.getWidth()/4,
                                                         sharkStartFrame.mainFrame.getHeight()*2/5,
                                                         (sharkStartFrame.mainFrame.getWidth()/2),
                                                         (sharkStartFrame.mainFrame.getHeight()/5)));
            }
            catch (Exception ee) {
                ee.printStackTrace();
            }
  }

//startPR2010-03-05^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  String readFile(String s){
      String ret = "";
              try {
                BufferedReader in = new BufferedReader(new FileReader(s));
                String str;
                while ((str = in.readLine()) != null) {
                    ret = ret.concat(str);
                }
                in.close();
            } catch (Exception ex) {
                ret = "";
            }
      return ret;
  }
//endPRtoupdate^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  /**
   * Sets fonts where no font has been chosen by the student
   * <li>If the present student has no font set then Wordshark's default font is used
   * <li>If there are no wordlists chosen then the font used for the wordlists set to NULL.
   */
  public void setwordfont() {
    String s,fontdet[];
    keypad.savekeypad kp;
    if(keypad.keypadname!=null && (kp=(keypad.savekeypad)db.find(keypad.keypad_db,keypad.keypadname,db.SAVEKEYPAD))!=null){
        wordfont = keypad.getfont(keypad.keypadname, true);
    }
    else{
        if(currStudent>=0 && (s = student.optionstring("s_wordfont")) != null && u.stufontexists("s_wordfont", s))
             fontdet = u.splitString(s);
        else{
            if(isinfantcourse()){
             fontdet = (String[]) db.find(optionsdb, "wordfontinfant", db.TEXT);
             if(!u.adminfontexists("wordfontinfant", fontdet))  fontdet = null;
           }
           else{
             fontdet = (String[]) db.find(optionsdb, "wordfont", db.TEXT);
             if(!u.adminfontexists("wordfont", fontdet))  fontdet = null;
           }            
        }
        if (fontdet == null
            ||(wordfont = u.fontFromString(fontdet[0], Integer.parseInt(fontdet[1]), BASICFONTPOINTS)) == null)
          wordfont = getdefaultfont();
    }
    if (wordTree != null) {
      wordTree.font = null;
      wordTree.repaint();
    }
  }
//startPR2007-11-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  Font getdefaultfont(){
    if(sharkStartFrame.currCourse!=null){
      for(int i = 0; i < infantcourses.length; i++){
        if(isinfantcourse()){
          wordfontm = getFontMetrics(defaultfontname.deriveFont(Font.PLAIN,(float)BASICFONTPOINTSX));
          wordfontheight = wordfontm.getHeight();
          return (currdefaultfont=defaultinfantfontname);
        }
      }
    }
    wordfontm = getFontMetrics(defaultfontname.deriveFont(Font.PLAIN,(float)BASICFONTPOINTSX));
    wordfontheight = wordfontm.getHeight();
    return (currdefaultfont=defaultfontname);
  }

//startPR2007-11-25^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//  boolean isinfantcourse(){
  public boolean isinfantcourse(){
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    if(sharkStartFrame.currCourse!=null){
      for(int i = 0; i < infantcourses.length; i++){
        if(sharkStartFrame.currCourse.equalsIgnoreCase(infantcourses[i])){
          return true;
        }
      }
    }
    return false;
  }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR

public void clearStudentCourses() {

    String hidden[] = settings.getUniversalHiddenCourses();

       courses = new String[0];
       if(hidden!=null)
         courses = u.addString(courses, hidden);
       for (int i = 0; i < mcourses.length; ++i) {
         mcourses[i].setState(u.findString(hidden, mcourses[i].getText()) < 0);
         if(hidden!=null) mcourses[i].setEnabled(u.findString(hidden, mcourses[i].getText()) < 0);
       }
}

  /**
   *Adds menu items to the main menu bar.
   */
  public void setStudentMenu() {
    short i, j;
    student stu = studentList[currStudent];
    if(shark.wantTutVids){
        mvidaddstu.setVisible(stu.administrator);
        mvidsetwork.setVisible(stu.administrator);
        mvidtrackingprogress.setVisible(stu.administrator);
      }
//    if(!stu.isnew && !stu.administrator && !override()
    if(!stu.isnew && !stu.administrator
           && stu.doupdates(true,false)) {
          setupgames();                  // rm 27/10/06
     }
    buildHelpMenu(false);
    forcechange = true;
       // in v4 these become personal
    noise.nogroan = student.option("s_nogroan");
//    nogroan.setSelected(noise.nogroan = student.option("s_nogroan"));
//    mpeep.setSelected(db.query(optionsdb, "mpeep", db.TEXT) < 0);   rb 27/10/06
//startPR2010-01-29^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    mpeep.setSelected(!student.option("s_nopeep"));  // rb 27/10/06
//endPRtoupdate^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    String c =  student.optionstring("s_courses");
    String hidden[] = settings.getUniversalHiddenCourses();
    if(c != null) {
      courses = u.splitString(c);
      if(hidden!=null)
        courses = u.addString(courses, hidden);
      for (i = 0; i < mcourses.length; ++i) {
        mcourses[i].setState(u.findString(courses, mcourses[i].getText()) < 0);
        if(hidden!=null) mcourses[i].setEnabled(u.findString(hidden, mcourses[i].getText()) < 0);
      }
    }
    else {
       courses = new String[0];
       if(hidden!=null)
         courses = u.addString(courses, hidden);
       for (i = 0; i < mcourses.length; ++i) {
         mcourses[i].setState(u.findString(hidden, mcourses[i].getText()) < 0);
         if(hidden!=null) mcourses[i].setEnabled(u.findString(hidden, mcourses[i].getText()) < 0);
       }
    }
    setteachers();   // start rb 23/10/06
    setupOkRewards(false);
    byte bv = (byte)student.optionval("s_beepvol");
    if (bv >= 0) noise.beepvol = bv;
    else noise.beepvol = settings.DEFAULTBEEPVOL;
    noise.setnew();
    setwordfont();
    u.defaultfont();
//startPR2007-11-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    chooseFont2.setState(student.optionstring("s_wordfont")!= null);
//    defaultfont2.setState(student.optionstring("s_wordfont")== null);
//    mtreefont2.setState(student.optionstring("s_treefont")!= null);
//    notreefont2.setState(student.optionstring("s_treefont")== null);
//    fontGames2_change.setState(student.optionstring("s_wordfont") != null);
//    fontGames2_default.setState(student.optionstring("s_wordfont") == null);
//    fontMenus2_change.setState(student.optionstring("s_treefont") != null);
//    fontMenus2_default.setState(student.optionstring("s_treefont") == null);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         // end v4 changes
//startPR2009-10-01^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    manBar1.remove(msearch);
//    manBar1.remove(topics);
//    manBar1.remove(features);
//    manBar1.remove(managestudent);
//    manBar1.remove(studentmenu);
//    manBar1.remove(mchooseteacher);
      manBar1.removeAll();
      if(!Demo_base.isDemo)
          manBar1.add(meuFile);
 //     manBar1.add(helpmenu);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    studentmenu.removeAll();
    if (studentList == null || currStudent < 0) {
      return;
    }
    studentmenu.add(menuItem1);


    sharkGame.otherplayer = null;
    sharkGame.studentflipped = false;
    if(studentList.length > 1) {
        mtwoplayers.removeAll();
        JCheckBoxMenuItem ct;
        for (i = 0; i < studentList.length; ++i) {
            if(studentList[i].name.equals(studentList[currStudent].name))continue;
            mtwoplayers.add(ct = u.CheckBoxMenuItem2(studentList[i].name));
            ((u.myCheckBoxMenuItem)ct).stayAfterClick = false;
            student st = student.getStudent(studentList[i].name);
            if(st!=null){
                if(st.teacher)
                    ct.setIcon(im_sub);
                else if(st.administrator)
                    ct.setIcon(im_admin);
                else
                    ct.setIcon(im_stu);
            }
            ct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                doTwoPlayerMenuClick((JCheckBoxMenuItem)e.getSource(), true);
            }
          });
              //  && studentList[i].name.equals(sharewith)
          for(int n = 0; sharewith!=null && n < sharewith.size(); n++){
              String ss[] = (String[])sharewith.get(n);
              if(ss[0].equals(studentList[currStudent].name)){
                  if(ss[1].equals(studentList[i].name)){
                      ct.doClick();//.setSelected(true);
                      break;
                  }
              }
          }
        }
        studentmenu.add(mtwoplayers);



//    String switchtotooltip = u.gettext("switchto", "tooltip_base");
    boolean firstinlist = true;
    for (i = 0; i < studentList.length; i++) {
      if(i==currStudent)continue;
      j = i;
      JMenuItem m = u.MenuItem("switchto");
      m.setText(m.getText().replaceFirst("%", studentList[j].name));
      m.addActionListener(new studentmenulistener(j));
      if (firstinlist){
//startPR2004-08-27^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        // if running on a Macintosh
        if (shark.macOS) {
          m.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
                                                  MENUSHORTCUTMASK));
        }
        // if running on Windows
        else {
          m.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, 0));
        }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      }
      firstinlist = false;
      student st = student.getStudent(studentList[j].name);
      if(st!=null){
          if(st.teacher)
              m.setIcon(im_sub);
          else if(st.administrator)
              m.setIcon(im_admin);
          else
              m.setIcon(im_stu);
      }
//      m.setToolTipText(switchtotooltip.replaceFirst("%", studentList[j].name ));
      studentmenu.add(m);
    }
    }

    muserlist.setIcon(studentList.length > 1?jnode.icons[jnode.BLANK]:null);
    madminlist.setIcon(studentList.length > 1?jnode.icons[jnode.BLANK]:null);
    studentmenu.addSeparator();
    if(stu.teacher||stu.administrator){
        
        studentmenu.add(signlist);
    }

    studentmenu.add(madminlist);
    if(shark.network || shark.specialfunc) studentmenu.add(muserlist);
                                            // v5 end rb 7/3/08
    signoff.setText(signofftext + " " + stu.name);
    signoff.setEnabled(true);
    manBar1.add(studentmenu);
//    Component cc[] = features.getMenuComponents();
//    Color featurescol = features.getForeground();
//    features = u.Menu(featurestext, stu.name, true);
//    for(int ii = 0; ii < cc.length; ii++){
//        features.add(cc[ii]);
//    }
//    features.setForeground(featurescol);
    features.setText(featurestext.replaceFirst("%", stu.name));
//    features.setMinimumSize(new Dimension((int)features.getPreferredSize().getWidth(), u.menuItemHeight));
//    features.setPreferredSize(new Dimension((int)features.getPreferredSize().getWidth(), u.menuItemHeight));
    features.validate();
    studentmenu.validate();
    manBar1.add(features);
    manBar1.add(actions);
//    manBar1.add(topics);
 //   if (!wantplayprogram)
    if(!stu.administrator)
        manBar1.add(mchooseteacher);
      manBar1.add(msearch);
      if(!shark.phonicshark){
        if (stu.isnew) {
          ownWordListEnabled(false, wantplayprogram);
    //      changepassword.setEnabled(false);
        }
        else {
          ownWordListEnabled(!wantplayprogram, wantplayprogram);
    //      easywordlist.setEnabled(!wantplayprogram);
    //      easyrecord.setEnabled(!wantplayprogram);
    //      changepassword.setEnabled(true);
        }
      }

   changepassword.setEnabled(!stu.isnew);
   studentrecord.setEnabled(!stu.isnew);

    actions.removeAll();
    if(!shark.phonicshark){
        actions.add(mownwords);
        actions.addSeparator();
    }
    actions.add(printlist);
//    actions.addSeparator();
//    actions.add(mPickPicture);
    actions.addSeparator();
    actions.add(studentrecord);
    

//    mPickPicture.setVisible(stu.administrator || allowStuImportPics_Icon);
    chooseicon.setVisible(stu.administrator || allowStuImportPics_Icon);



    if (stu.teacher) {
      managestudent.removeAll();
      managestudent.add(addstudent2);
      managestudent.addSeparator();
//      managestudent.add(moverride);
      managestudent.add(messagesi);
//      if(!shark.network)
          managestudent.add(mBackup);
//      managestudent.add(msturec);
//      managestudent.addSeparator();
      managestudent.add(advanced);
      advanced.removeAll();
      devadmin();
      if(!shark.phonicshark)
          advanced.add(mdistribute);
      advanced.add(mkeypad);
      advanced.addSeparator();
      if(shark.licenceType.equals(shark.LICENCETYPE_NETWORKACTIVATION)){
          advanced.add(mzipsharednet);
          mzipsharednet.add(mzipsharednet_withstus);
          mzipsharednet.add(mzipsharednet_anon);
      }
      else
        advanced.add(mzipshared);
      manBar1.add(managestudent);
      restrictcourses.setVisible(true);      // rb 27/10/06
    }
    else if (stu.administrator) {
      managestudent.removeAll();
      managestudent.add(addstudent);
//      managestudent.addSeparator();
      managestudent.add(universal);
      managestudent.addSeparator();
 //     managestudent.add(moverride);
      managestudent.add(messagesi);
//      if(!shark.network)
          managestudent.add(mBackup);
 //     managestudent.add(msturec);
 //     managestudent.addSeparator();
      managestudent.add(advanced);
      advanced.removeAll();
      devadmin();
      if(!shark.phonicshark)
          advanced.add(mdistribute);
      advanced.add(mkeypad);
      advanced.add(mstuchek);
      if(!ChangeScreenSize_base.isActive)
        advanced.add(mChangeScreenSize);
      if(shark.licenceType.equals(shark.LICENCETYPE_STANDALONEACTIVATION)){
        advanced.addSeparator();
        if(!shark.singledownload)
            advanced.add(mviewlicence);
        advanced.add(mdisablelicence);
        advanced.add(mconvertexpiry);
        mdisablelicence.setVisible(!nondeactivatable && expiry==null);
        mconvertexpiry.setVisible(expiry != null);
        mviewlicence.setVisible(!splitlicence && expiry==null);
      }
      if (!shark.specialfunc) advanced.addSeparator();
//      advanced.add(defrecord2);
      restrictcourses.setVisible(true);      // rb 27/10/06
//      mpeep.setVisible(true);             // rb 27/10/06

      if (shark.network){
//startPR2008-11-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//          advanced.add(netkeyup);
        if(shark.licenceType.equals(shark.LICENCETYPE_NETWORK))advanced.add(netkeyup);
//endPRauth^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2007-07-12^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2007-11-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//        if(shark.macOS){
      if(shark.macOS||shark.linuxOS){
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          advanced.addSeparator();
//startPR2009-08-02^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          if(shark.specialfunc)advanced.add(acl);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          advanced.add(sharedloc);
//startPR2009-07-14^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//          advanced.add(runlocally);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          advanced.addSeparator();
        }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      }
//startPR2009-08-02^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      else if(shark.macOS && shark.specialfunc) advanced.add(acl);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      if (shark.specialfunc) {
        advanced.addSeparator();
        advanced.add(pictures);
        advanced.add(imageinfo);
        advanced.add(PublicTopics);
        advanced.add(changegames);
        advanced.add(changetext);
        advanced.add(record);
        advanced.add(recordfl);
        advanced.add(sentrecord);
        advanced.add(sentrecord2);
        advanced.add(sentrecord3);
        advanced.add(sentrecord4);
        advanced.add(copysay);
        advanced.addSeparator();
        advanced.add(returnnormal);
        returnnormal.setEnabled(false);
        advanced.addSeparator();
      }
      advanced.add(mshowshared);
      if(shark.licenceType.equals(shark.LICENCETYPE_NETWORKACTIVATION)){
          advanced.add(mzipsharednet);
          mzipsharednet.add(mzipsharednet_withstus);
          mzipsharednet.add(mzipsharednet_anon);
      }
      else
        advanced.add(mzipshared);
      advanced.add(memory);
      advanced.add(refreshsound);
//      advanced.add(antialias);
      advanced.add(splitdraw);
      manBar1.add(managestudent);
    }
    else {
      manBar1.remove(managestudent);
      
      restrictcourses.setVisible(false);      // rb 27/10/06
//      mpeep.setVisible(false);             // rb 27/10/06
    }
     boolean disableSignOn = false;
     ArrayList al = u.restrictedUserCount(sharkStartFrame.sharedPath);
     if(al !=null){
        int adminNo = (int)al.get(0);
        int stuNo = (int)al.get(1);
        if(adminNo > shark.maxUsers_Admins || stuNo > shark.maxUsers_Students){
            disableSignOn = true;
        }        
     }     
     menuItem1.setEnabled(!disableSignOn);
    signlist.setEnabled(!disableSignOn && (stu.administrator 
                        && stu.students != null
                        && stu.students.length > 0));
    showmarkedgames = !studentList[currStudent].option("notmarkedgames");
//startPR2004-10-22^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    mnogamble.setState(stu.option("nogamble"));
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    mnoreward.setState(stu.option("noreward"));
//    for(i=0;i<4;++i)
//startPR2010-02-08^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//     mrewardfreqs[i].setState(i == stu.rewardfreq);
//      mrewardfreqs[i].setState(!mnoreward.isSelected() && i == studentList[currStudent].rewardfreq);
//endPRtoupdate^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    mprintfg.setForeground(student.printfg());
//    mteachnotes.setState(!stu.option("noteachnotes"));
    mnomove.setState(stu.option("nomove"));
//startPR2008-06-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    mwantsignvids.setState(stu.wantsignvids);
//endPRimagesandvids^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    mwantsigns.setState(stu.wantsigns);
//    mwantrealpics.setState(stu.wantrealpics);
//    mwantfingers.setState(stu.wantfingersall);
//    mpicbg.setState(stu.picbg);
    // option removed in v5 , reset people who might have had this on
    if(studentList[currStudent].picbg){
        studentList[currStudent].picbg = false;
      }
//startPR2013-02-20^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    String sbg = student.optionstring("bgcolor");
    setBgMenuSelected(sbg!=null);
    if(sbg != null) {
         Color ccol = new Color(Integer.parseInt(sbg));
         wordTree.setBackground(ccol);
         wordlist.bgcoloruse = ccol;
    }
    else{
         wordTree.setBackground(wordlist.wlbgcol);
         wordlist.bgcoloruse = wordlist.wlbgcol;        
    }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startSS^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
    switchOptions = stu.optionval("switchoptions"); //SS Set flags with current student's switch option
    switchResponse = stu.optionval("switchresponse");
//endSS^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
//    if (madmin != null) {
//      madmin.setEnabled(true);
//    }
//startPR2008-06-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    // if running on Macintosh
//    if (shark.macOS){
 //     manBar1.remove(helpmenu);
    if(!Demo_base.isDemo)
      manBar1.add(helpmenu);
//    }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    checkSepsInMenu(advanced);
    manBar1.validate();
    forcechange = false;
    String s;
    if ( (s = student.optionstring("keypad")) != null) {
      keypad.keypadname = s;
      setkeydis();
      Font ff = keypad.getfont(keypad.keypadname);
      if (ff != null)
        wordfont = ff;
    }
    else if (keypad.keypadname != null) {
      keypad.keypadname = null;
      setwordfont();
    }
    if (wordTree != null) {
      wordTree.font = null;
      wordTree.repaint();
    }
    mnokeypad.setSelected(keypad.keypadname == null);
    setkeydis();
    manBar1.repaint();
    bevelPanel1.repaint();
  }

  
  boolean anySpellingExcluded(){
    student stu = studentList[currStudent];
    String excl[] = stu.excludegames != null ?stu.excludegames : new String[0];
    excl = settings.getAllExcludeGames(excl);
    for(int i = 0; i < sharkStartFrame.gameflags.length; i++){
       if(sharkStartFrame.gameflags[i].spelling && u.findString(excl, sharkStartFrame.gamename[i])>=0)
           return true;
    }
    return false;
  }
  

  void setupOkRewards(boolean flipped){
    student stu = flipped?sharkGame.otherplayer:studentList[currStudent];
    String s = flipped?stu.optionstring2("s_okrewards"):student.optionstring("s_okrewards");
    String excl[] = stu.excludegames != null ?stu.excludegames : new String[0];
    excl = settings.getAllExcludeGames(excl);
    String exclrewards[] = new String[]{};
    for(int n = 0; n < excl.length; n++){
        if(u.findString(rw, excl[n])>=0){
            exclrewards = u.addString(exclrewards, excl[n]);
        }
    }
    if(!flipped){
        msetrewards.setVisible(db.query(sharkStartFrame.optionsdb, "notwantrewards", db.TEXT) < 0
            && !student.option("s_excludeallrewardgames") && exclrewards.length<rw.length);
    }
    if(s==null) {
        if(flipped)okrewards_flip = rw;
        else okrewards = rw;
    }
    else{
        if(flipped)okrewards_flip =  u.splitString(s);
        else okrewards = u.splitString(s);
         int okcount = 0;
         boolean userUnchecked = false;
         for(int n = 0; n < rw.length; n++){
             if(u.findString(exclrewards, rw[n])<0){
                 if(u.findString((flipped?okrewards_flip:okrewards), rw[n])>=0){
                    okcount++;
                 }
                 else{
                    userUnchecked = true;
                 }
             }
         }
         if(okcount<3 && userUnchecked){
             if(flipped){
                 stu.removeOption2("s_okrewards");
                 okrewards_flip = rw;
             }
             else{
                 student.removeOption("s_okrewards");
                 okrewards = rw;
             }
         }

    }
    for (int i = 0; i < rw.length; ++i) {
        if((u.findString(excl, rw[i]) >= 0)){
            if(flipped)okrewards_flip = u.removeString2(okrewards_flip,rw[i]);
            else okrewards = u.removeString2(okrewards,rw[i]);
        }
    }
  }

  void doTwoPlayerMenuClick(JCheckBoxMenuItem src, boolean menuclick){
      if(!menuclick){
          MenuSelectionManager.defaultManager().clearSelectedPath();
          src.setSelected(!src.isSelected());
      }
              for(int i = 0; i < mtwoplayers.getItemCount(); i++){
                if(mtwoplayers.getItem(i).equals(src)){
                    if(mtwoplayers.getItem(i).isSelected()){
                        if( studentList.length > 1) {
                            int k;
                            for (k = 0; k < studentList.length; k++) {
                                if(studentList[k].name.equals(mtwoplayers.getItem(i).getText()))break;
                            }
                            addsharers(studentList[currStudent].name, studentList[k].name);
                            sharkGame.otherplayer = studentList[k];
                            setupOkRewards(true);
                            if(sharedp!=null){
                                sharedp.change(sharkGame.otherplayer.name, mover.picandname.MODE_OTHERUSERCHOSEN);
                            }
                        }
                        else {
                            sharkGame.otherplayer = null;
//                            sharkGame.lastreward = true; sharkGame.studentflipped = false;
                            removesharers(studentList[currStudent].name);
                            if(sharedp!=null){
                                sharedp.reset2();
                            }
                        }
                    }
                    else{
                        sharkGame.otherplayer = null;
//                        sharkGame.lastreward = true; sharkGame.studentflipped = false;
                        removesharers(studentList[currStudent].name);
                        if(sharedp!=null){
                            sharedp.reset2();
                        }
                    }
                }
                else ((JCheckBoxMenuItem)mtwoplayers.getItem(i)).setSelected(false);
              }
              settitles();
              displayhometitle();
  }

  void buildHelpMenu(boolean atsignon){
    helpmenu.removeAll();
    helpmenu.setText(atsignon?wantHelpText_signon:wantHelpText);
    if(!atsignon){
        student stu = studentList[currStudent];
        if(!Demo_base.isDemo){
            helpmenu.add(mwanthelp);
        }
        if (shark.wantTutVids) {
            helpmenu.add(mvideotutorial);
        }
        helpmenu.addSeparator();
        boolean added = false;
        if (shark.language.equals(shark.LANGUAGE_NL)) {
            if(musicFolderExists){
              helpmenu.add(mmusic);
              added = true;
            }
        }
        if(helpFolderExists){
            helpmenu.add(mexplore);
            added = true;
        }
        if(stu.administrator){
            if(helpFolderExists2){
                    helpmenu.add(mexplore2);
                    added = true;
            }
        }
        if(added)
            helpmenu.addSeparator();
        helpmenu.add(msupport);
        if(shark.patching && stu.administrator && !stu.teacher)
            helpmenu.add(mupdatescheck);
        helpmenu.addSeparator();
    }
    helpmenu.add(mversion);
    if(shark.testing){
        if(shark.paulTest)
            helpmenu.add(merror);
    }
  }

  void devadmin(){
//      development.removeAll();
//      wordlistcolour.removeAll();
 //     cooperativeplay.removeAll();
//      advanced.add(development);
//      development.add(wordlistcolour);
//      development.add(cooperativeplay);
//      advanced.addSeparator();
/*
      JCheckBoxMenuItem jmcbi1 = u.CheckBoxMenuItem("");
      jmcbi1.setText("No whole game play");
      nowholegame = (db.query(optionsdb, "nowholegame", db.TEXT) >= 0);
      jmcbi1.setSelected(nowholegame);

      jmcbi1.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
            if (((JCheckBoxMenuItem)e.getSource()).getState()){
                db.update(optionsdb, "nowholegame", new String[] {""}
                    , db.TEXT);
                nowholegame = true;
            }
            else{
                db.delete(optionsdb, "nowholegame", db.TEXT);
                nowholegame = false;
            }
            }
      });


      cooperativeplay.add(jmcbi1);
*/
/*
      JMenuItem whitewl1 = new JMenuItem();
      whitewl1.setText("Set word list colour - white");
      wordlistcolour.add(whitewl1);
      JMenuItem whitewl2 = new JMenuItem();
      whitewl2.setText("Set word list colour - ghost white");
      wordlistcolour.add(whitewl2);
      JMenuItem whitewl3 = new JMenuItem();
      whitewl3.setText("Set word list colour - cornsilk");
      wordlistcolour.add(whitewl3);
      JMenuItem whitewl4 = new JMenuItem();
      whitewl4.setText("Set word list colour - ivory");
      wordlistcolour.add(whitewl4);
      JMenuItem whitewl5 = new JMenuItem();
      whitewl5.setText("Set word list colour - ivory 2");
      wordlistcolour.add(whitewl5);
      JMenuItem whitewl6 = new JMenuItem();
      whitewl6.setText("Set word list colour - lemon chiffon");
      wordlistcolour.add(whitewl6);
      JMenuItem whitewl7 = new JMenuItem();
      whitewl7.setText("Set word list colour - alice blue");
      wordlistcolour.add(whitewl7);
      JMenuItem whitewl8 = new JMenuItem();
      whitewl8.setText("Set word list colour - floral white");
      wordlistcolour.add(whitewl8);
      JMenuItem whitewl9 = new JMenuItem();
      whitewl9.setText("Set word list colour - snow");
      wordlistcolour.add(whitewl9);
      JMenuItem whitewla = new JMenuItem();
      whitewla.setText("Set word list colour - light goldenrod yellow");
      wordlistcolour.add(whitewla);
      JMenuItem whitewlb = new JMenuItem();
      whitewlb.setText("Set word list colour - beige");
      wordlistcolour.add(whitewlb);
      JMenuItem whitewld = new JMenuItem();
      whitewld.setText("Set word list colour - antique white");
      wordlistcolour.add(whitewld);
      JMenuItem whitewle = new JMenuItem();
      whitewle.setText("Set word list colour - white smoke");
      wordlistcolour.add(whitewle);
      whitewl1.addActionListener(new java.awt.event.ActionListener() {
              public void actionPerformed(ActionEvent e) {
                 wordlist.wlbgcol = Color.white;
                 wordTree.repaint();
              }
      });
      whitewl2.addActionListener(new java.awt.event.ActionListener() {
              public void actionPerformed(ActionEvent e) {
                 wordlist.wlbgcol = new Color(248,248,255);
                 wordTree.repaint();
              }
      });
       whitewl3.addActionListener(new java.awt.event.ActionListener() {
              public void actionPerformed(ActionEvent e) {
                 wordlist.wlbgcol = new Color(255,248,220);
                 wordTree.repaint();
              }
      });
       whitewl4.addActionListener(new java.awt.event.ActionListener() {
              public void actionPerformed(ActionEvent e) {
                 wordlist.wlbgcol = new Color(255,255,240);
                 wordTree.repaint();
              }
      });
       whitewl5.addActionListener(new java.awt.event.ActionListener() {
              public void actionPerformed(ActionEvent e) {
                 wordlist.wlbgcol = new Color(238,238,224);
                 wordTree.repaint();
              }
      });
       whitewl6.addActionListener(new java.awt.event.ActionListener() {
              public void actionPerformed(ActionEvent e) {
                 wordlist.wlbgcol = new Color(255,250,205);
                 wordTree.repaint();
              }
      });
        whitewl7.addActionListener(new java.awt.event.ActionListener() {
              public void actionPerformed(ActionEvent e) {
                 wordlist.wlbgcol = new Color(240,248,255);
                 wordTree.repaint();
              }
      });
       whitewl8.addActionListener(new java.awt.event.ActionListener() {
              public void actionPerformed(ActionEvent e) {
                 wordlist.wlbgcol = new Color(255,250,240);
                 wordTree.repaint();
              }
      });
        whitewl9.addActionListener(new java.awt.event.ActionListener() {
              public void actionPerformed(ActionEvent e) {
                 wordlist.wlbgcol = new Color(255,250,250);
                 wordTree.repaint();
              }
      });
 
      
      
        whitewla.addActionListener(new java.awt.event.ActionListener() {
              public void actionPerformed(ActionEvent e) {
                 wordlist.wlbgcol = new Color(250,250,210);
                 wordTree.repaint();
              }
      });
         whitewlb.addActionListener(new java.awt.event.ActionListener() {
              public void actionPerformed(ActionEvent e) {
                 wordlist.wlbgcol = new Color(245,245,220);
                 wordTree.repaint();
              }
      });
         whitewld.addActionListener(new java.awt.event.ActionListener() {
              public void actionPerformed(ActionEvent e) {
                 wordlist.wlbgcol = new Color(245,245,245);
                 wordTree.repaint();
              }
      });
        whitewle.addActionListener(new java.awt.event.ActionListener() {
              public void actionPerformed(ActionEvent e) {
                 wordlist.wlbgcol = new Color(245,245,245);
                 wordTree.repaint();
              }
      });
*/
  }
  // start rb 23/10/06
  void setteachers() {
    int i;
    student stu = studentList[currStudent];
//startPR2008-09-21^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    if((db.query(optionsdb, "menforceprogram", db.TEXT) < 0)){
//startPR2008-09-21^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    if (stu.teachers != null && stu.teachers.length > 1) {
//      if(stu.workforteacher == null) {
//         stu.workforteacher = stu.teachers[0];
//      }
    if (stu.teachers != null && stu.teachers.length > 0) {
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        mchooseteacher.removeAll();
        JCheckBoxMenuItem ct;
//startPR2008-09-21^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      for (i = 0; i < stu.teachers.length; ++i) {
//      mchooseteacher.add(ct = new JCheckBoxMenuItem(stu.teachers[i]));
        String fromteacher = u.gettext("fromteacher", "label");
      for (i = 0; i < stu.teachers.length+1; ++i) {
        if(i==stu.teachers.length) mchooseteacher.add(ct = u.CheckBoxMenuItem2(u.gettext("otherwork", "label")));
        else mchooseteacher.add(ct = u.CheckBoxMenuItem2(fromteacher + " " + stu.teachers[i]));
          ((u.myCheckBoxMenuItem)ct).stayAfterClick = false;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          ct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
//startPR2008-09-22^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
              for(int i = 0; i < mchooseteacher.getItemCount(); i++){
                if(mchooseteacher.getItem(i).equals(((JCheckBoxMenuItem)e.getSource()))){
                  if(i==whichteacherlast){
                    mchooseteacher.getItem(i).setSelected(true);
                    return;
                  }
                  whichteacherlast = i;
                  break;
                }
              }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2008-09-22^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            int i, j,old=-1;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
              student stu = studentList[currStudent];
              for (i = 0; i < stu.teachers.length; ++i) { // find old setting
                if (stu.teachers[i].equals(stu.workforteacher)) {
                  old = i;
                  break;
                }
              }
//startPR2008-09-21^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//            for (i = 0; i < stu.teachers.length; ++i) {
            for (i = 0; i < stu.teachers.length+1; ++i) {
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                if (mchooseteacher.getItem(i).isSelected()) {
                  if (i != old) {
//startPR2008-09-21^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//                  stu.workforteacher = stu.teachers[i];
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                    if(old>=0)  mchooseteacher.getItem(old).setSelected(false);
//startPR2008-09-21^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                    if(i==stu.teachers.length)
//startPR2008-09-22^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//                      stu.workforteacher = null;
                      stu.workforteacher = sharkStartFrame.OTHERWORKFORTEACHER;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                    else
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                      stu.workforteacher = stu.teachers[i];
                    setupgames();
                    return;
                  }
                }
               }
            }
          });
        }
        mchooseteacher.setVisible(true);
      }
      else {
        mchooseteacher.setVisible(false);
      }
      if(stu.teachers != null && stu.teachers.length > 0) {
        if(stu.workforteacher == null) {
//startPR2008-09-22^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         stu.workforteacher = stu.teachers[0];
//         if(stu.teachers.length > 1)
          mchooseteacher.getItem(0).setSelected(true);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        }
        else {
          if ((i = u.findString(stu.teachers,stu.workforteacher))<0) {
//startPR2008-09-22^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            if(stu.workforteacher.equals(sharkStartFrame.OTHERWORKFORTEACHER) &&
               mchooseteacher.getItemCount()>stu.teachers.length){
              mchooseteacher.getItem(mchooseteacher.getItemCount()-1).setSelected(true);
            }
            else{
              stu.workforteacher = stu.teachers[0];
//          if(stu.teachers.length > 1)
              mchooseteacher.getItem(0).setSelected(true);
            }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          }
//startPR2008-09-22^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//          else if(stu.teachers.length > 1)
//          else if(stu.teachers.length >= 1)
          else
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            mchooseteacher.getItem(i).setSelected(true);
        }
      }
      else stu.workforteacher = null;
    }
    else{
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    if (stu.teachers != null && stu.teachers.length > 1) {
      if(stu.workforteacher == null) {
         stu.workforteacher = stu.teachers[0];
      }
      mchooseteacher.removeAll();
      JCheckBoxMenuItem ct;
      for (i = 0; i < stu.teachers.length; ++i) {
        mchooseteacher.add(ct = u.CheckBoxMenuItem2(stu.teachers[i]));
        ((u.myCheckBoxMenuItem)ct).stayAfterClick = false;
        ct.addActionListener(new java.awt.event.ActionListener() {
          public void actionPerformed(ActionEvent e) {
//startPR2008-09-22^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            for(int i = 0; i < mchooseteacher.getItemCount(); i++){
              if(mchooseteacher.getItem(i).equals(((JCheckBoxMenuItem)e.getSource()))){
                if(i==whichteacherlast){
                  mchooseteacher.getItem(i).setSelected(true);
                  return;
                }
                whichteacherlast = i;
                break;
              }
            }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            int i, j,old=-1;
            student stu = studentList[currStudent];
            for (i = 0; i < stu.teachers.length; ++i) { // find old setting
              if (stu.teachers[i].equals(stu.workforteacher)) {
                old = i;
                break;
              }
            }
            for (i = 0; i < stu.teachers.length; ++i) {
              if (mchooseteacher.getItem(i).isSelected()) {
                if (i != old) {
                  stu.workforteacher = stu.teachers[i];
                  if(old>=0)  mchooseteacher.getItem(old).setSelected(false);
                  stu.workforteacher = stu.teachers[i];
                  setupgames();
                  return;
                }
              }
             }
          }
        });
      }
      mchooseteacher.setVisible(true);
    }
    else {
      mchooseteacher.setVisible(false);
    }
    if(stu.teachers != null && stu.teachers.length > 0) {
      if(stu.workforteacher == null) {
         stu.workforteacher = stu.teachers[0];
         if(stu.teachers.length > 1) mchooseteacher.getItem(0).setSelected(true);
      }
      else {
             if ((i = u.findString(stu.teachers,stu.workforteacher))<0) {
               stu.workforteacher = stu.teachers[0];
               if(stu.teachers.length > 1) mchooseteacher.getItem(0).setSelected(true);
             }
             else if(stu.teachers.length > 1) mchooseteacher.getItem(i).setSelected(true);
      }
    }
    else stu.workforteacher = null;
//startPR2008-09-21^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2008-09-22^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    for(int k = 0; k < mchooseteacher.getItemCount(); k++){
      if(mchooseteacher.getItem(k).isSelected()){
        whichteacherlast = k;
        break;
      }
    }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  }
  // endrb 23/10/06
  /**
   * Sets which keypad is selected.
   */
  void setkeydis() {
    if(keypad.keypadname == null) {
      mnokeypad.setSelected(true);
      for (int i = 0; i < mkeypads.length; ++i) {
        mkeypads[i].setSelected(false);
      }
    }
    else {
      mnokeypad.setSelected(false);
      for (int i = 0; i < mkeypads.length; ++i) {
        mkeypads[i].setSelected(mkeypads[i].getText().equals(keypad.keypadname));
      }
    }
  }

  /**
   *
   */
  public void clearStudentMenu() {
    boolean saveforce = forcechange;
    forcechange = true;
    printlist.setEnabled(false);
    signoff.setEnabled(false);
    signoff.setText(signofftext);
//startPR2009-10-01^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    manBar1.remove(msearch);
//    manBar1.remove(topics);
//    manBar1.remove(features);
//    manBar1.remove(managestudent);
//    manBar1.remove(studentmenu);
//    manBar1.remove(mchooseteacher);

      manBar1.removeAll();
    if(!Demo_base.isDemo){
      manBar1.add(meuFile);
      studentmenu.removeAll();
      studentmenu.add(madminlist);
      if(shark.network || shark.specialfunc) studentmenu.add(muserlist);
      manBar1.add(studentmenu);
      manBar1.add(helpmenu);
//      helpmenu.remove(mwanthelp);
    }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    mchooseteacher.setVisible(false);
//    if (madmin != null)
//      madmin.setEnabled(false);
    manBar1.revalidate();
    manBar1.repaint();
    forcechange = saveforce;
  }

  /**
   *Changes the menu items available
   */
  void setforedit(boolean wantSQL) {
//startPR2007-11-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    if(Demo_base.isDemo)return;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    save.setEnabled(true);
    returnnormal.setEnabled(true);
      manBar1.removeAll();
      manBar1.add(meuFile);
      manBar1.add(helpmenu);
    manBar1.add(men1);
    if(showingPictures) {
        manBar1.add(mplay);
        mplay.setEnabled(false);
    }
    manBar1.add(msearch);
    manBar1.add(managestudent);
    if(wantSQL){
        JMenu sql = new JMenu("SQL");
        JMenuItem jmiGamesGet = new JMenuItem();
        
        jmiGamesGet.setText("Get games data");
        jmiGamesGet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MYSQLUpload msqlu = new MYSQLUpload();
                msqlu.UploadGames(false);
            }
        });        
        /*
        JMenuItem jmiGames = new JMenuItem();
        jmiGames.setText("Generate games data");
        jmiGames.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MYSQLUpload msqlu = new MYSQLUpload();
                msqlu.UploadGames(true);
            }
        });
        */
        
        JMenuItem jmiGenRecordings = new JMenuItem();
        jmiGenRecordings.setText("Do recordings upload");
        jmiGenRecordings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MYSQLUpload mysql = new MYSQLUpload();
                mysql.doRecordingsUpload();
            }
        });  
        
        
        JMenuItem jmiGenImages = new JMenuItem();
        jmiGenImages.setText("Do images upload");
        jmiGenImages.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MYSQLUpload mysql = new MYSQLUpload();
                mysql.doImagesUpload();
            }
        }); 
        
        
        JMenuItem jmiTopics = new JMenuItem();
        jmiTopics.setText("Generate topic data");
        jmiTopics.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MYSQLUpload mysql = new MYSQLUpload();
                 mysql.UploadGames(false);
                mysql.doTopicsPrint(topicTreeList);
            }
        });
        /*
        JMenuItem jmiRecordings = new JMenuItem();
        jmiRecordings.setText("Generate recording files data");
        jmiRecordings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                byte PUBLICSENT1 = 3;
                byte PUBLICSENT2 = 2;
                byte PUBLICSENT3 = 5;
                byte PUBLICSAY1 = 0;
                byte PUBLICSAY3 = 6;
                byte curr = PUBLICSENT1;
                recordWords recording = new recordWords(u.absoluteToRelative(sharkStartFrame.publicTopicLib[0]), curr,false);
                MYSQLUpload mysql = new MYSQLUpload();
                mysql.UploadRecording(recording.alreadywords,  mysql.getSoundDbType(recording.soundDBName));
            }
        });
        */
        JMenuItem jmiOWLAvailableGames = new JMenuItem();
        jmiOWLAvailableGames.setText("Generate OWL available game sets");
        jmiOWLAvailableGames.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MYSQLUpload mysql = new MYSQLUpload();
                mysql.generateOWLAvailableGameSets();
            }
        });        
 //       sql.add(jmiGamesGet);       
  //      sql.add(jmiGames);
        sql.add(jmiGenRecordings);
        sql.add(jmiGenImages);
        sql.add(jmiTopics);
 //       sql.add(jmiRecordings);
        sql.add(jmiOWLAvailableGames);
        manBar1.add(sql);
    }
    manBar1.validate();
    manBar1.repaint();
  }

  /**
   *Enables tooltips and help screen if flag is true
   * @param flag boolean
   */
  public void setwanthelp(boolean flag) {
    wanthelp = flag;
    mwanthelp.setState(flag);
//    ToolTipManager.sharedInstance().setEnabled(mwanthelp.getState());
    if (gameicons && gamePanel != null && gamePanel.tooltipmover1 != null) {
      gamePanel.removeMover(gamePanel.tooltipmover1);
      gamePanel.redrawoffscreen = true;
      gamePanel.tooltipmover1 = null;
    }
//startPR2007-11-26^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    // if running on a Macintosh
//    if (shark.macOS){
//      if (flag) {
//        helpmenu.setText(u.gettext("helpmenuon_mac", "label"));
//      }
//      else {
//        helpmenu.setText(u.gettext("helpmenu_mac", "label"));
//      }
//    }
//    if (flag) {
//      helpmenu.setText(u.gettext(shark.macOS?"helpmenuon_mac":"helpmenuon", "label"));
//    }
//    else {
//      helpmenu.setText(u.gettext(shark.macOS?"helpmenu_mac":"helpmenu", "label"));
//    }
//    helpmenu.setBackground(flag ? Color.red:runMovers.tooltipbg);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    if (gametot > 0)
      runningGame.currGameRunner.game.changehelp();
  }

  /**
   *
   */
  void createmenu() {
    u2_base.setupMenuItemHeight();
    meuFile = u.Menu("file", true);
    menuFileExit = u.MenuItem("exit");
    features = u.Menu("features", true);
 //   features.setFont(features.getFont().deriveFont(Font.ITALIC | Font.BOLD));
 //   features.setText(features.getText()+"("+studentList[currStudent]+")");
    actions = u.Menu("actions", true);
//    actions.setForeground(Color.BLUE);
//    featurestext = features.getText();
//startPR2008-09-25^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    features.setBackground(greencolor);
    features.setForeground(greencolor);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    advanced = u.Menu("advanced");
    advanced.setToolTipText(advanced.getToolTipText().replaceFirst("%", shark.programName));
//startPR2008-10-12^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    antialias = u.CheckBoxMenuItem("antialiasing");
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2004-08-27^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    // if running on a Macintosh
    if (shark.macOS) {
      helpmenu = u.Menu("helpmenu_mac", true);
      refreshsound = u.CheckBoxMenuItem("refreshsound_mac");
//      topics = u.Menu("topics_mac");
//      mtopictree = u.MenuItem("mtopictree",KeyStroke.getKeyStroke(KeyEvent.VK_T,MENUSHORTCUTMASK));
//      mgameicons = u.MenuItem("mgameicons",KeyStroke.getKeyStroke(KeyEvent.VK_G,MENUSHORTCUTMASK));
      addstudent = u.MenuItem("addstudent", KeyStroke.getKeyStroke(KeyEvent.VK_A,MENUSHORTCUTMASK));
      addstudent2 = u.MenuItem("addstudent2", KeyStroke.getKeyStroke(KeyEvent.VK_A,MENUSHORTCUTMASK));
      studentrecord = u.MenuItem("studentrecord",KeyStroke.getKeyStroke(KeyEvent.VK_R,MENUSHORTCUTMASK));
      signlist = u.MenuItem("signlist", KeyStroke.getKeyStroke(KeyEvent.VK_I,MENUSHORTCUTMASK));
      msearchi = u.MenuItem("msearchi",KeyStroke.getKeyStroke(KeyEvent.VK_F,MENUSHORTCUTMASK));
      print = u.MenuItem("print", KeyStroke.getKeyStroke(KeyEvent.VK_P,MENUSHORTCUTMASK));
//startPR2007-05-17^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      printlist = u.Menu("mprintlist");
//      mprintflash = u.MenuItem("mprintflash");
//      mprintflash2 = u.MenuItem("mprintflash2");
//      mprint1 = u.MenuItem("mprint1");
//      mprint2 = u.MenuItem("mprint2");
//      mprint4 = u.MenuItem("mprint4");
//      mprint8 = u.MenuItem("mprint8");
//      mprintfg = u.MenuItem("mprintfg");
//      mexplore = u.MenuItem("mexplore");
//      msupport = u.MenuItem("msupport");
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      easywordlist = u.MenuItem("easywordlist_mac", KeyStroke.getKeyStroke(KeyEvent.VK_L,MENUSHORTCUTMASK));

//startPR2006-04-30^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      muserlist = u.MenuItem("muserlist_mac");
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      mkeypad = u.MenuItem("mkeypad_mac");
    }
    // if running on Windows
    else {
      helpmenu = u.Menu("helpmenu", true);
      
      
      refreshsound = u.CheckBoxMenuItem("refreshsound");
//      topics = u.Menu("topics");
//      mtopictree = u.MenuItem("mtopictree",KeyStroke.getKeyStroke(KeyEvent.VK_F11,0));
//      mgameicons = u.MenuItem("mgameicons",KeyStroke.getKeyStroke(KeyEvent.VK_F12,0));

      addstudent = u.MenuItem("addstudent");
      addstudent2 = u.MenuItem("addstudent2");
      studentrecord = u.MenuItem("studentrecord");
      signlist = u.MenuItem("signlist");
      msearchi = u.MenuItem("msearchi",KeyStroke.getKeyStroke(KeyEvent.VK_F9,0));
      print = u.MenuItem("print");
//startPR2007-05-17^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      printlist = u.Menu("mprintlist");
//      mprintflash = u.MenuItem("mprintflash");
//      mprintflash2= u.MenuItem("mprintflash2");
//      mprint1 = u.MenuItem("mprint1");
//      mprint2 = u.MenuItem("mprint2");
//      mprint4 = u.MenuItem("mprint4");
//      mprint8 = u.MenuItem("mprint8");
//      mprintfg = u.MenuItem("mprintfg");
//      mexplore = u.MenuItem("mexplore");
//      msupport = u.MenuItem("msupport");
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      easywordlist = u.MenuItem("easywordlist");
      
      
//startPR2006-04-30^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      muserlist = u.MenuItem("muserlist");
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      mkeypad = u.MenuItem("mkeypad");
    }
    mwanthelp = u.CheckBoxMenuItem(shark.macOS?"mwanthelp_mac":"mwanthelp", false, true);
    madminlist = u.MenuItem("madminlist");
    wantHelpText = helpmenu.getText();
    wantHelpText_signon = u.gettext("helpmenu", "signon");
    studentmenu = u.Menu("studentmenu", true);
    mdistribute = u.MenuItem("mdistribute");
    studentrecord.setIcon(new ImageIcon(publicPathplus + "sprites" +
                                         sharkStartFrame.separator +
                                         "studentrec_il16.png"));
    msearch = u.Menu("msearch", true);
    universal = u.MenuItem("universal");
    universal.setToolTipText(universal.getToolTipText().replaceFirst("%", shark.programName));
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//   topics.addMouseListener(new MouseAdapter() {
//         public void mousePressed(MouseEvent e) {
//startPR2007-03-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
           // mousePressed does not register for JMenu on Macs
           //needed to prevent menu from appearing on child dialog
//           MenuSelectionManager.defaultManager().clearSelectedPath();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//           topics.setPopupMenuVisible(false);
//           topics.setSelected(false);
//           if(currPicture != null) return;
//           if (playingGames) {
//              if (!gameicons) {
//               if (specialsuperlist || topicList.getCurrentTopic() != null) {
//                 gameicons = true;
//                 switchdisplay();
//                 gamePanel.startrunning();
//                 requestFocus();
//               }
//               else
//                 u.okmess(u.gettext("switch","heading"),u.gettext("switch","text"), sharkStartFrame.mainFrame);
//             }
//             else {
//               gameicons = false;
//              switchdisplay();
//               requestFocus();
//             }
//           }
//           else {
//             setupgames();
//           }
//         }
//    });
    msearch.addMouseListener(new MouseAdapter() {
          public void mousePressed(MouseEvent e) {
//startPR2007-03-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            // mousePressed does not register for JMenu on Macs
            //needed to prevent menu from appearing on child dialog
              MenuSelectionManager.defaultManager().clearSelectedPath();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
              msearch.setPopupMenuVisible(false);
              msearch.setSelected(false);
              if(currPicture != null) return;
              new findword(null, sharkStartFrame.mainFrame);
          }
         });
//startPR2007-05-17^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         printlist = u.Menu("mprintlist");
         mprintflash = u.MenuItem("mprintflash");
         mprintflash2= u.MenuItem("mprintflash2");
         mprint1 = u.MenuItem("mprint1");
         mprint2 = u.MenuItem("mprint2");
         mprint4 = u.MenuItem("mprint4");
         mprint8 = u.MenuItem("mprint8");
         mprintfg = u.MenuItem("mprintfg");
         mexplore = u.MenuItem("mexplore");
         mexplore2 = u.MenuItem("mexplore2");
         if (shark.language.equals(shark.LANGUAGE_NL)) {
            mmusic = u.MenuItem("mmusic");
         }
//startPR2008-01-23^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//         mglossary= u.MenuItem("mglossary");
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2010-03-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         if(shark.wantTutVids){
            mvideotutorial= u.Menu("mvideotutorial");
            mvidaddstu= u.MenuItem("mvidaddstu");
            mvidchooselist = u.MenuItem("mvidchooselist");
//            mvidcoursehelp = u.MenuItem("mvidcoursehelp");
            mvidchoosegame= u.MenuItem("mvidchoosegame");
            mvidsearch= u.MenuItem("mvidsearch");
            mvidprivatelist= u.MenuItem("mvidprivatelist");
            mvidphonics= u.MenuItem("mvidphonics");
            mvidgettingstarted= u.MenuItem("mvidgettingstarted");
            mvidgettingstarted.setText(mvidgettingstarted.getText().replaceFirst("%", shark.programName));
//            mvidoptions= u.MenuItem("mvidoptions");
            mvidsetwork= u.MenuItem("mvidsetwork");
            mvidtrackingprogress= u.MenuItem("mvidtrackingprogress");
         }
//endPRtoupdate^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   msupport = u.MenuItem("msupport");
   mupdatescheck= u.MenuItem("mupdatescheck");
   mupdatescheckall = u.CheckBoxMenuItem("mupdatescheckall");
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2008-06-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//         mPickPicture = u.MenuItem("mpickpicture");
//endPRimagesandvids^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2006-04-30^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    muserlist = u.MenuItem("muserlist");
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2007-02-16^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    mprintex= u.MenuItem("mprintex");
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    restrictcourses = u.Menu("restrictcourses");
    restrictcourses.setIcon(new ImageIcon(publicPathplus + "sprites" +
                                          sharkStartFrame.separator +
                                          "course_il16.png"));
//    mspellchange = u.Menu("spellchange");
//startPR2010-05-04^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    msignon = u.Menu("msignon");
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    mactivedirectory = u.Menu("mautosignon");

    
//    mfontchange = u.Menu("mfontchange");
//startPR2008-08-05^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    ImageIcon iifont = new ImageIcon(publicPathplus+"sprites" +sharkStartFrame.separator+"font_il16.png");
//    mfontchange.setIcon(iifont);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2007-11-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//        mfontchange2 = u.Menu("mfontchange2");
    mfontchange2 = u.MenuItem("mfontchange2");
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    mfontchange2.setIcon(iifont);
//    mnotemp = u.CheckBoxMenuItem("mnotemp");
//startPR2010-05-04^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    msideprompt = u.CheckBoxMenuItem("mnosideprompt");
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2008-09-21^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    enforceprogram = u.CheckBoxMenuItem("menforceprogram");
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    mautosignon = u.CheckBoxMenuItem("mautosignon");
//    mautosigncreatestu = u.CheckBoxMenuItem("mautosigncreatestu");
//    mdefcourse = u.MenuItem("mdefcourse");
//startPR2004-10-21^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    mpeep = u.CheckBoxMenuItem("mpeep");
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    moverride = u.CheckBoxMenuItem("moverride");
//    mwanthello = u.CheckBoxMenuItem("mwanthello");
//    mwanthello.setState(wanthello);
//    mwantshapes = u.CheckBoxMenuItem("mwantshapes");    // start rb 27-11-07
//    mwantshapes.setState(wantshapes);
    if(!shark.phonicshark){
        mnotwantonsetrime = u.CheckBoxMenuItem("mnotwantonsetrime");
        mnotwantonsetrime.setState(notwantonsetrime);             //end  rb 27-11-07
    }
//startPR2008-10-27^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    moverride.setIcon(new ImageIcon(publicPathplus + "sprites" +
//                                    sharkStartFrame.separator + "override.gif"));
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    defaultbg = mwanthelp.getBackground();
    Color tcol = new Color(238,238,238); // silly to have to do it this way, but if not, on Mac, game top bar is same bg color as game.
    defaultbg = new Color(tcol.getRed(),tcol.getGreen(),tcol.getBlue());
    managestudent = u.Menu("managestudent", true);
    memory = u.MenuItem("memory");
    mshowshared = u.MenuItem("mshowshared");
    mshowshared.setText(mshowshared.getText().replaceFirst("%", shark.programName.toLowerCase()));
    mzipshared = u.MenuItem("mzipshared");
    mzipshared.setText(mzipshared.getText().replaceFirst("%", shark.programName.toLowerCase()));
    mzipshared.setToolTipText(mzipshared.getToolTipText().replaceFirst("%", shark.programName.toLowerCase()));
    mzipsharednet = u.Menu("mzipshared");
    mzipsharednet.setText(mzipsharednet.getText().replaceFirst("%", shark.programName.toLowerCase()));
    mzipsharednet.setToolTipText(mzipsharednet.getToolTipText().replaceFirst("%", shark.programName.toLowerCase()));
    mzipsharednet_anon = u.MenuItem("mzipsharednet_anon");
    mzipsharednet_withstus = u.MenuItem("mzipsharednet_withstus");
    splitdraw = u.CheckBoxMenuItem("splitdraw");
    bgcolor = u.CheckBoxMenuItem("bgcolor", true);
//startPR2008-09-25^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    topics.setBackground(Color.orange);
//    topics.setForeground(u.brown);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2007-11-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    chooseFont = u.MenuItem("choosefont");
//    chooseFontInfant = u.MenuItem("choosefontinfant");
//    chooseFont2 = u.CheckBoxMenuItem("choosefont");
//    mtreefont = u.MenuItem("treefont");
//    mtreefont2 = u.CheckBoxMenuItem("treefont");
//    defaultfont = u.MenuItem("defaultfont");
//    defaultfont2 = u.CheckBoxMenuItem("defaultfont2");
//    notreefont = u.MenuItem("notreefont");
//    notreefont2 = u.CheckBoxMenuItem("notreefont");
    fontStandardGames_change = u.MenuItem("choosegamefont");
//    fontMenus_change = u.MenuItem("choosemenufont");
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//  treeFont.setIcon(new ImageIcon(publicPathplus+"sprites" +sharkStartFrame.separator+"font.gif"));
//    easyrecord = u.MenuItem("easyrecord");
    if(!shark.phonicshark)
        mownwords = u.MenuItem("mownwords");
//    defrecord = u.MenuItem("defrecord");
//    defrecord2 = u.MenuItem("defrecord2");
    sentrecord = u.MenuItem("sentrecord");
    sentrecord2 = u.MenuItem("sentrecord2");
    sentrecord3 = u.MenuItem("sentrecord3");
    sentrecord4 = u.MenuItem("sentrecord4");
//startPR2010-05-28^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    sentrecord4 = u.MenuItem("sentrecord4");
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    returnnormal = u.MenuItem("returnnormal");
//    mtopictree.setBackground(Color.orange);
    messagesi = u.MenuItem("messages_");
    menuItem1 = u.MenuItem("signon");
    mtwoplayers = u.Menu("mtwoplayers");              // v5 rb 7/3/08
    twoplayertext = mtwoplayers.getText();                // v5 rb 7/3/08
    signoff = u.MenuItem("signoff");
//    madmin = u.MenuItem("madmin");
    mversion = u.MenuItem("mversion");
    merror = u.MenuItem("mversion");
    merror.setText("Xml read fails");
    signofftext = signoff.getText();
    mBackup = u.MenuItem("mBackup");
//    msturec = u.MenuItem("msturec");
    mstuchek = u.MenuItem("mstuchek");
    mChangeScreenSize = u.MenuItem("mChangeScreenSize");
//startPR2008-11-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    if(shark.licenceType.equals(shark.LICENCETYPE_STANDALONEACTIVATION)){
      mdisablelicence = u.MenuItem("mdisablelicence");
      mviewlicence = u.MenuItem("mviewlicence");
      mconvertexpiry = u.MenuItem("mconvertexpiry");
//      mnewlicence = u.MenuItem("mnewlicence");
    }
//endPRauth^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    msetrewards = u.MenuItem("msetrewards");
    msetrewards.setIcon(new ImageIcon(publicPathplus+"sprites" +sharkStartFrame.separator+"reward_il16.png"));
    msetrewards.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        student stu = studentList[currStudent];
        String s = student.optionstring("s_okrewards");               // start rb 16/2/06
        String excl[] = stu.excludegames != null ?stu.excludegames : new String[0];
        excl = settings.getAllExcludeGames(excl);
        String exclrewards[] = new String[]{};
        for(int n = 0; n < excl.length; n++){
            if(u.findString(rw, excl[n])>=0){
                exclrewards = u.addString(exclrewards, excl[n]);
            }
        }
        new rewardGamesDialog(exclrewards);
      }
    });    
//    mnoreward = u.CheckBoxMenuItem("mnoreward");
//    mteachnotes = u.CheckBoxMenuItem("mteachnotes");
    mnomove = u.CheckBoxMenuItem("mnomove", true);
//startPR2008-06-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    mwantsignvids = u.CheckBoxMenuItem("mwantsignvids");
//endPRimagesandvids^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    mwantsigns = u.CheckBoxMenuItem("mwantsigns");
//    mwantrealpics = u.CheckBoxMenuItem("mwantrealpics");
//    mwantfingers = u.CheckBoxMenuItem("mwantfingers");
//    mpicbg = u.CheckBoxMenuItem("mpicbg");
    morenoise = u.MenuItem("morenoise");
//startSS^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
//    mswitchaccess = u.MenuItem("mswitchaccess");
//endSS^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
//    nogroan = u.CheckBoxMenuItem("nogroan");
//    nogroan.setState(noise.nogroan);
//    picmenu = u.Menu("picmenu");

    PublicTopics = u.MenuItem("publictopics");
    changegames = u.MenuItem("changegames");
    changetext = u.MenuItem("changetext");
    copysay = u.MenuItem("copysay");
    mchoosekeypad = u.Menu("mchoosekeypad");
    mchooseteacher = u.Menu("mchooseteacher", true);   // rb 23/10/06
    mchooseteacher.setForeground(Color.red);
//startPR2008-09-25^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    mchooseteacher.setBackground(Color.red);     // rb 23/10/06
//    mchooseteacher.setForeground(Color.white);   // rb 23/10/06
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    mchoosekeypad.setIcon(new ImageIcon(publicPathplus + "sprites" +
                                        sharkStartFrame.separator +
                                        "keypad_il16.png"));
    if (shark.specialfunc) {
      men1 = u.Menu("edit", true);
      mplay = u.Menu("play", true);
      play = u.MenuItem("play");
//startPR2004-08-27^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      // if running on a Macintosh
      if (shark.macOS) {
        copy = u.MenuItem("copy_mac",KeyStroke.getKeyStroke(KeyEvent.VK_C,MENUSHORTCUTMASK));
        cut = u.MenuItem("cut_mac",KeyStroke.getKeyStroke(KeyEvent.VK_X, MENUSHORTCUTMASK));
      }
      // if running on Windows
      else {
        copy = u.MenuItem("copy",KeyStroke.getKeyStroke(KeyEvent.VK_C,MENUSHORTCUTMASK));
        cut = u.MenuItem("cut",KeyStroke.getKeyStroke(KeyEvent.VK_X, MENUSHORTCUTMASK));
      }
      

//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      paste = u.MenuItem("paste",
                         KeyStroke.getKeyStroke(KeyEvent.VK_V, MENUSHORTCUTMASK));
      find = u.MenuItem("find",
                         KeyStroke.getKeyStroke(KeyEvent.VK_F, MENUSHORTCUTMASK));
      findnext = u.MenuItem("findnext",
                         KeyStroke.getKeyStroke(KeyEvent.VK_N, MENUSHORTCUTMASK));
      pasteimage = u.MenuItem("pasteimage",
                              KeyStroke.getKeyStroke(KeyEvent.VK_B,MENUSHORTCUTMASK));
      save = u.MenuItem("save");
      undo = u.MenuItem("undo",
                        KeyStroke.getKeyStroke(KeyEvent.VK_Z, MENUSHORTCUTMASK));
      record = u.MenuItem("record");
      recordfl = u.MenuItem("recordfl");
      imageinfo = new u.myJMenuItem("Image information");
    }
    picpref = u.MenuItem("picpref");
    picpref.setIcon(picicon = new ImageIcon(publicPathplus + "sprites" +
                                  sharkStartFrame.separator + "picopts_il16.png"));
    chooseicon = u.MenuItem("chooseicon");
    chooseicon.setIcon(picicon = new ImageIcon(publicPathplus + "sprites" +
                                  sharkStartFrame.separator + "signonAdminLight_il16.png"));
    choosesprite = u.MenuItem("choosesprite");
    choosesprite.setIcon(new ImageIcon(publicPathplus + "sprites" +
                                       sharkStartFrame.separator + "sprite_il16.png"));
    changepassword = u.MenuItem("choosepassword");
    changepassword.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0));
    changepassword.setIcon(new ImageIcon(publicPathplus + "sprites" +
                                         sharkStartFrame.separator +
                                         "password_il16.png"));
//    studentrecord.setIcon(new ImageIcon(publicPathplus + "sprites" +
//                                        sharkStartFrame.separator +
//                                        "sturecord.gif"));
    if (shark.specialfunc) {
      newdb = u.MenuItem("newdb");
      findreplace = u.MenuItem("findreplace");
      importsentences = u.MenuItem("importsentences");
      if (publicTextLib.length >= 2)
        mergetext = u.MenuItem("mergetext");
      if (db.exists("publicsay2"))
        mergesay = u.MenuItem("mergesay");
      if (db.exists("publicsent1x"))
        mergesent1 = u.MenuItem("mergesent1");
      if (db.exists("publicsent2x"))
        mergesent2 = u.MenuItem("mergesent2");
      if (publicImageLib.length >= 2) {
        mergeimage = u.MenuItem("mergeimage");
        mergeimage.setText(u.edit(mergeimage.getText(),(new File(publicImageLib[1])).getName()));
      }
      if (db.exists("publicimage2") && db.exists("publicimagew")){
        mergeimage23 = u.MenuItem("mergeimage23");
      }
//startPR2008-06-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      if (new File(sharedPathplus+"mergeimages").exists()&&new File(sharedPathplus+"mergeimages").list().length>1){
        mergemultimage = u.MenuItem("mergemultimage");
      }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      if(!shark.production){
          if(new File(sharedPathplus+"soundextraold").exists()){
              mextrasoundpatches = u.MenuItem("mcreatesoundpatches");
          }
      }
      if (db.exists("publictopics2"))
        mergetopics = u.MenuItem("mergetopics");
      compresspublic = u.MenuItem("compresspublic");
    }
    pictures = u.MenuItem("pictures");
//startPR2009-08-02^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    if(shark.macOS && shark.specialfunc) acl = u.MenuItem("macl");
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    if (shark.network){
//startPR2008-11-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      netkeyup = u.MenuItem("netkey");
      if(shark.licenceType.equals(shark.LICENCETYPE_NETWORK))netkeyup = u.MenuItem("netkey");
//endPRauth^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2006-08-19^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2007-11-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//        if(shark.macOS){
      if(shark.macOS||shark.linuxOS){
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        sharedloc = u.MenuItem("sharedloc");
//startPR2009-07-14^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//        if(shark.runlocally)
//          runlocally = u.MenuItem("runfromserver");
//        else
//          runlocally = u.MenuItem("runfromlocal");
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    }
//startPR2010-05-24^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    prevlist = u.Button("prevlist");
    prevlist = u.sharkButton();
    prevlist.setText("<");
    prevlist.setToolTipText(u.gettext("topic_back", "tooltip"));
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    prevlist.setBackground(u.lighter(Color.orange, 1.15f));
//startPR2010-05-24^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    nextlist = u.Button("nextlist");
    nextlist = u.sharkButton();
    nextlist.setText(">");
    nextlist.setToolTipText(u.gettext("topic_forward", "tooltip"));
    Font bfont = treefont.deriveFont((float)treefont.getSize()+4);
    nextlist.setFont(bfont);
    prevlist.setFont(bfont);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    nextlist.setBackground(u.lighter(Color.orange, 1.15f));
    if(shark.macOS){
        prevlist.setOpaque(true);
        nextlist.setOpaque(true);
    }
    switchtogames = new XrButton_base(gamescolor);
//startPR2010-05-24^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  switchtogamesg = u.gettext("bgameicons", "label");
  switchtogamest = u.gettext("bgameicons", "labelt");
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2004-08-27^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    // if running on a Macintosh
    if (shark.macOS){
 //     switchtogames.setForeground(gamescolor);
    }
    // if running on Windows
    else{
//      switchtogames.setForeground(Color.yellow);
    }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    switchtogames.setBackground(gamescolor);
//startPR2007-11-19^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    switchtogames.setOpaque(true);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2010-05-24^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    showallgames = u.mbutton("showallgames");
//    showallgames = new JButton(u.gettext("showallgames", "label"));
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2009-10-10^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    allgamestext1 = showallgames.text;
//    allgamestext2 = u.gettext("showallgames","label2");
//    iconheading  = u.gettext("showallgames", "iconheading");
//    iconheading2  = u.gettext("showallgames", "iconheading2");
    int z;
    for (z = 0; z < markgameheadings.length; z++) {
      if (markgameheadings[z][0].indexOf("default")>=0) {
        break;
      }
    }
    allgamestext1 = allgamesdefaulttext1 = markgameheadings[z][1];
    allgamesdefaulttooltip = markgameheadings[z][3];
    allgamestext2 = allgamesdefaulttext2 = markgameheadings[z][2];
    iconheading = iconheadingdefault = markgameheadings[z][4];
    iconheading2 = iconheadingdefault2 = markgameheadings[z][5];
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2010-05-24^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    showallgames.setBackground(Color.blue);
//    showallgames.setForeground(Color.yellow);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR

  }

  /**
   *Teaching notes (a label that gives teaching information about the current topic)
   * appears like a tooltip_base. This happens if:
   * <li>byclick is true
   * <li>Teaching notes are available for the current topic
   * <li>"Show teaching notes for wordlists" has been selected from the features menu.
   * @param byclick boolean
   */
  void addteachingnotes(boolean byclick) {
//    if(Demo_base.isDemo && !Demo_base.blockTeachingNotes)return;
    if (showteachingnotes != null) {
      showteachingnotes.setVisible(false);
      showteachingnotes.dispose();
      showteachingnotes = null;
    }
    int i;
//startPR2007-11-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    if (!mteachnotes.isSelected())
//      return;
//    if (!Demo_base.isDemo && !mteachnotes.isSelected())
    if (!Demo_base.isDemo)
      return;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    if ( (i = manBar1.getSelectionModel().getSelectedIndex()) >= 0
        && i < manBar1.getMenuCount()
        && manBar1.getMenu(i).isSelected()) {
      return;
    }
    if (reward == 0 && gametot == 0 && currPlayTopic != null &&
      currentlist.isShowing() &&  gameicons &&
      currPlayTopic.teachingnotes != null) {
      Point p = currentlist.getLocationOnScreen();
      String title = spellchange.spellchange(wordTree.name);
      if((i = title.indexOf('@'))>=0) title = title.substring(0,i);
      showteachingnotes = new u.shownotes(spellchange.spellchange(currPlayTopic.teachingnotes),
                                          title,
                                          gameicons ? p.x :
                                          (p.x + currentlist.getWidth()), p.y,
                                          gameicons);
      showteachingnotes.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseExited(MouseEvent e) {
          if (showteachingnotes != null) {
            showteachingnotes.setVisible(false);
            showteachingnotes.dispose();
            showteachingnotes = null;
          }
        }
      });
      if (!byclick)
        showteachingnotes.addMouseListener(new java.awt.event.MouseAdapter() {
          public void mouseEntered(MouseEvent e) {
            showteachingnotes.setVisible(false);
            showteachingnotes.dispose();
            showteachingnotes = null;
          }
        });
    }
  }
//startPR2008-11-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  void removedExpiryChangeTitle(){
     if(mdisablelicence!=null) mdisablelicence.setVisible(!nondeactivatable && expiry==null);
     if(mviewlicence!=null) mviewlicence.setVisible(shark.licenceType.equals(shark.LICENCETYPE_STANDALONEACTIVATION) && !splitlicence);
//     if(mnewlicence!=null) mnewlicence.setVisible(true);
     if(mconvertexpiry!=null) mconvertexpiry.setVisible(false);
     checkSepsInMenu(advanced);
  }
//endPRauth^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR

    void checkSepsInMenu(JMenu m){
        if(m == null)return;
        Component c[] = m.getMenuComponents();
        boolean last = false;
        if(c!=null){
            for(int i = c.length-1; i >=0; i--){
                if(c[i]== null || !c[i].isVisible())continue;
                if(last && c[i] instanceof JPopupMenu.Separator){
                    m.remove(c[i]);
                }
                last = c[i] instanceof JPopupMenu.Separator;
            }
        }
    }


  void typecheck(){
      String jarType = shark.singledownload?shark.LICENCETYPE_STANDALONEACTIVATION_HOME:shark.licenceType;
      String sharedType = (String) db.find(optionsdb, "lictype", db.TEXT);
      if(sharedType==null){
        db.update(optionsdb, "lictype", jarType, db.TEXT);
        return;
      }
      String sharedTypeName = shark.getVersionType(sharedType, false).trim();
      String jarTypeName = shark.getVersionType(jarType, false).trim();
      if(!sharedType.equals(jarType)){
              OptionPane_base.getErrorMessageDialog(mainFrame, 30, 
                      u.edit(u.gettext("errorcodes", "errorcode30"), 
                      new String[]{shark.programName, jarTypeName, sharedTypeName}),
                      OptionPane_base.ERRORTYPE_EXIT);
      }
  }


//startPR2008-06-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  // this is something Rik asked for - he is getting a number of cases caused by
  // people not having delete permissions on the shared folder. This creates a file
  // and then deletes it to test for this. It happens every run on single users and for
  // the first 5 users of a network.
  void deletecheck(){
    File f[] = new File(sharedPathplus).listFiles(new FileFilter() {
      public boolean accept(File file) {
        String s = file.getName();
        int i =  s.lastIndexOf(".");
        if(i<0)return false;
        String s2 = s.substring(i);
        int p = s2.length();
        return s.indexOf("hs_err_pid")>=0||(p==4&&s2.substring(0,3).equalsIgnoreCase(".sh")&&
//startPR2009-08-19^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//                                            !s2.substring(3).equalsIgnoreCase("a"));
//                                            !s2.substring(3).equalsIgnoreCase("a"))
                (!s2.substring(3).equalsIgnoreCase("a") && 
                !s2.substring(3).equalsIgnoreCase("u") && !s2.substring(3).equalsIgnoreCase("w")))
//startPR2010-03-15^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//            || (shark.macOS&&shark.network&&s.endsWith(MacLock.LOCKEXTENSION));
            || (MacLock.DOJNI&&s.endsWith(MacLock.LOCKEXTENSION));
//endPRtoupdate^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      }});
    if(f.length>0){
      PrintWriter write=null;
      Calendar cal = new GregorianCalendar();
      File home = new File(this.sharedPathplus + "shfile.log");
      String date = String.valueOf(cal.get(Calendar.DAY_OF_MONTH)) + "/" +
          String.valueOf(cal.get(Calendar.MONTH)+1) + "/" +
          String.valueOf(cal.get(Calendar.YEAR));
      for (int i = 0; i < f.length; i++) {
//startPR2010-02-06^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        if(f[i].getName().indexOf("hs_err_pid")>=0) {
            String bkupprefix = u.gettext("backup_", "WS");
            String dirs = sharkStartFrame.sharedPathplus + File.separator + bkupprefix + "backups" +
                    File.separator + "Log";
            // Destination directory
            File dir = new File(dirs);
            if(!dir.exists())dir.mkdirs();
                // Move file to new directory
                f[i].renameTo(new File(dir, f[i].getName()));
        }
        else if (f[i].delete()) {
//        if (f[i].delete()) {
//endPRtoupdate^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          try {
            if (!home.exists()) {home.createNewFile(); }
            write = new PrintWriter(new FileWriter(home, true));
            write.println(date + " " + "deleted : " + f[i].getName());
          } catch (IOException e) {
            e.printStackTrace();
          } finally {
            if (write != null) write.close();
          }
        }
      }
    }
    if(shark.network && (new File(sharedPathplus).listFiles(new FileFilter() {
            public boolean accept(File file) {
              return file.getName().endsWith(".nlock");
            }}).length>5))
          return;
    try {
        String fname = sharedPathplus+"~"+String.valueOf(System.currentTimeMillis());
        File testDelete = new File(fname);
        FileOutputStream deletefos = new FileOutputStream(new File(fname));
        deletefos.close();
        if (!testDelete.delete()) {
//          u.okmess(shark.programName, u.gettext("errorcodes", "errorcode11", sharkStartFrame.sharedPath.getAbsolutePath()));
          OptionPane_base.getErrorMessageDialog(mainFrame, 11, u.gettext("errorcodes", "errorcode11", sharkStartFrame.sharedPath.getAbsolutePath()), OptionPane_base.ERRORTYPE_EXIT);
        }
      }
      catch (SecurityException e) {
//        u.okmess(shark.programName, u.gettext("errorcodes", "errorcode11", sharkStartFrame.sharedPath.getAbsolutePath()));
        OptionPane_base.getErrorMessageDialog(mainFrame, 11, u.gettext("errorcodes", "errorcode11", sharkStartFrame.sharedPath.getAbsolutePath()), OptionPane_base.ERRORTYPE_EXIT);
      } catch (IOException e) {
      }
    }

//startPR2012-02-01^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
/*
   void activationInit(){
    if(!shark.isLicenceActivated())return;
        wa = new WebAuthenticate_base();
      boolean auth = false;
      String request = (String) db.find(optionsdb, "wa_request", db.TEXT);
      String response = (String) db.find(optionsdb, "wa_response", db.TEXT);
      if (request != null && response != null) {
        auth = wa.tryAuthenticate(request, response);
      }
      if (!auth) {
        if (loadprogressbar != null) {
            loadprogressbar.setVisible(false);
        }
        IntroFrame_base ifr = wa.makeUI(false);
        while (ifr.running)
          u.pause(1000);
        if (expiry != null) {
          java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("ddMMyyyy");
          Date d = null;
          try {
            d = sdf.parse(expiry);
            if ( (new Date()).getTime() > d.getTime()) {
              ifr = wa.makeExpiryUI();
              while (ifr.running)
                u.pause(1000);
            }
          } catch (Exception e) {}
        }
        if (loadprogressbar != null){
            loadprogressbar.setVisible(true);
        }
      }
      else {
        wa.doend(false);
        if (!auth) {
          System.exit(0);
        }
      }
      if(shark.network){
        if(!wa.getNetDetails()){
                            JOptionPane.showMessageDialog(sharkStartFrame.mainFrame,
                                              u.gettext("webauthenticate", "netservercontitle"),
                                              u.gettext("webauthenticate", "noidtitle"),
                                              JOptionPane.WARNING_MESSAGE);
          mainFrame.finalize();
        }
      }
      if(false){
          try{
              Calendar now = new GregorianCalendar();
              String sdue = (String) db.find(optionsdb, "nxtck", db.TEXT);
              if(sdue!=null){
                  String ss[] = u.splitString(sdue);
                  Calendar due = Calendar.getInstance();
                  due.set(Integer.parseInt(ss[0]),
                          Integer.parseInt(ss[1]),
                          Integer.parseInt(ss[2]));

                  if(now.before(due))
                      return;
              }
              wa.serverReport((String) db.find(sharkStartFrame.optionsdb, "wa_request", db.TEXT));
              int checkAfter = 35;
              now.add(Calendar.DAY_OF_MONTH, checkAfter);
              String update = String.valueOf(now.get(Calendar.YEAR)) + "|" +
                      String.valueOf(now.get(Calendar.MONTH)) + "|" +
                      String.valueOf(now.get(Calendar.DAY_OF_MONTH));
              db.update(optionsdb, "nxtck", update, db.TEXT);
          }
          catch(Exception e){}
      }
   }

   */  
  

  void activationInitNonNetwork(){
    if(!shark.isLicenceActivated())return;
    if(wa==null)
        wa = new WebAuthenticate_base();
    if(wa.xmlInitallyMissing){
            wa.writeFile(sharkStartFrame.sharedPathplus+u2_base.saXmlFileName, wa.xmlContents);
            u.setNewFilePermissions(new File(sharkStartFrame.sharedPathplus+u2_base.saXmlFileName));
            wa.updateToXML();   
    }
      boolean auth = false;
//      String request = (String) db.find(optionsdb, "wa_request", db.TEXT);
//      String response = (String) db.find(optionsdb, "wa_response", db.TEXT);
      String sss[] = wa.wab.doGetXMLElementValues(sharedPathplus+u2_base.saXmlFileName, new String[]{wa.ELEMENT_REQUEST, wa.ELEMENT_RESPONSE});
      String request = sss[0];
      String response = sss[1];
      if (request != null && response != null) {
        auth = wa.tryAuthenticate(request, response);
      }
      if (!auth) {
        if (loadprogressbar != null) {
            loadprogressbar.setVisible(false);
        }
        IntroFrame_base ifr = wa.makeUI(false);
        while (ifr.running)
          u.pause(1000);
        if (expiry != null) {
          java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("ddMMyyyy");
          Date d = null;
          try {
            d = sdf.parse(expiry);
            if ( (new Date()).getTime() > d.getTime()) {
              ifr = wa.makeExpiryUI();
              while (ifr.running)
                u.pause(1000);
            }
          } catch (Exception e) {}
        }
        if (loadprogressbar != null){
            loadprogressbar.setVisible(true);
        }
      }
      else {
        wa.doend(false);
        if (!auth) {
          System.exit(0);
        }
      }
      if(shark.singledownload){
        setupTitles();
        settitles();
      }
      /*
      if(false){
          try{
              Calendar now = new GregorianCalendar();
              String sdue = (String) db.find(optionsdb, "nxtck", db.TEXT);
              if(sdue!=null){
                  String ss[] = u.splitString(sdue);
                  Calendar due = Calendar.getInstance();
                  due.set(Integer.parseInt(ss[0]),
                          Integer.parseInt(ss[1]),
                          Integer.parseInt(ss[2]));

                  if(now.before(due))
                      return;
              }
              wa.serverReport((String) db.find(sharkStartFrame.optionsdb, "wa_request", db.TEXT));
              int checkAfter = 35;
              now.add(Calendar.DAY_OF_MONTH, checkAfter);
              String update = String.valueOf(now.get(Calendar.YEAR)) + "|" +
                      String.valueOf(now.get(Calendar.MONTH)) + "|" +
                      String.valueOf(now.get(Calendar.DAY_OF_MONTH));
              db.update(optionsdb, "nxtck", update, db.TEXT);
          }
          catch(Exception e){}  
      }
      */
  }

   void activationInit(){
       if(!shark.network)
           activationInitNonNetwork();
   }  
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   boolean doUsbMacBackupMessage(String filepath){
     filepath = sharkStartFrame.sharedPathplus+"config.ini";
     File fname = new File(filepath);
     boolean showmess = true;
     if(fname.exists()){
         String s[] = u.readFile(fname.getAbsolutePath());
         for(int i = 0; s!=null && i < s.length; i++){
             String key = "showbackupmess=";
            if(s[i].startsWith(key)){
                String sres = s[i].substring(key.length());
                showmess = Boolean.valueOf(sres).booleanValue();
            }
         }
     }
     if(showmess){
         InfoRemindChoiceDialog_base choicedlg = new InfoRemindChoiceDialog_base(shark.programName,
                 u.convertToCR(u.gettext("usbbackupmess", "mess", shark.programName)),
                 new String[]{u.gettext("usbbackupmess", "allow"),u.gettext("usbbackupmess", "dontallow")}) {
            public void doCheckboxAction(boolean checked) {
                PrintWriter pw = null;
                try {
                    File fname = new File(sharkStartFrame.sharedPathplus+"config.ini");
                    String item = "showbackupmess="+(checked?"false":"true");
                    String s[] = u.readFile(fname.getAbsolutePath());
                    if(s==null)s=new String[]{item};
                    if(fname.exists()){
                        if(s.length==0){
                            s = u.addString(s, item);
                        }
                        else{
                            boolean found = false;
                            for(int i = 0; i < s.length; i++){
                                if(s[i].startsWith("showbackupmess")){
                                    s[i] = item;
                                    found = true;
                                    break;
                                }
                            }
                            if(!found)s = u.addString(s, item);
                        }
                    }
                    pw = new PrintWriter(new FileWriter(fname.getAbsolutePath()));
                    for(int i = 0; i < s.length; i++){
                        pw.println(s[i]);
                    }
                    pw.flush();
                  }
                  catch (Exception e) {
                  }
                  finally{
                    if (pw != null) pw.close();
                  }
            }
            public void doButtonAction(int option) {}
         };
         return choicedlg.result == InfoRemindChoiceDialog_base.ANSWER_YES;
       }
     return false;
    }

//startPR2008-11-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        void newVersionBackup(){
            int thisver = Integer.parseInt(u.replaceEvery(shark.versionNoDetailed, ".", ""));
            /*
            if(shark.macOS && shark.licenceType.equals(shark.LICENCETYPE_USB) && shark.macusb2){
                try{
                    File f;
                    if(!(f = new File(shark.sep + "Volumes" + shark.sep + shark.usb2VolName + shark.sep + "jar")).exists()){
                        f.mkdir();
                    }
                    // if jar does not exist in jar backup folder, create it
                    String jarfolderjar = shark.sep + "Volumes" + shark.sep + shark.usb2VolName + shark.sep + "jar" + shark.sep + shark.programName.toLowerCase() + ".jar";
                    if(f.exists() && (!(f = new File(jarfolderjar)).exists())){
                        String cmds[] = new String[]{"cp", shark.sep + "Volumes" + shark.sep + shark.usb2VolName + shark.sep+ shark.programName.toLowerCase() + ".jar",
                            jarfolderjar};
                        Runtime.getRuntime().exec((cmds));
                    }
                    // sort out the backup path on the computer
                    String base = shark.getProgramDataPath() + shark.sep + shark.companyName;
                    String baseto = base + shark.sep + shark.mainFolder;
                    String basestickto = baseto + shark.sep + shark.usbSerial;
                    boolean notcancelled = true;
                    if(!(f = new File(base)).exists()){
                        shark.mkFolder(base);
                        if(!(new File(base)).exists()){
                          if(notcancelled = doUsbMacBackupMessage(base))
                              shark.createSharedMac(base);
                        }
                    }
                    if(!(f = new File(baseto)).exists()){
                        shark.mkFolder(baseto);
                        if(notcancelled && !(new File(baseto)).exists()){
                          if(notcancelled = doUsbMacBackupMessage(baseto))
                              shark.createSharedMac(baseto);
                        }
                    }
                    if(!(f = new File(basestickto)).exists()){
                        shark.mkFolder(basestickto);
                        if(notcancelled && !(new File(basestickto)).exists()){
                          if(doUsbMacBackupMessage(basestickto))
                              shark.createSharedMac(basestickto);
                        }
                    }
                    // make the backup copy of the jar
                    String bkupto = basestickto + shark.sep + shark.programName.toLowerCase() + ".jar";
                    String bkupfrom = shark.sep + "Volumes" + shark.sep + shark.usb2VolName + shark.sep + "jar" + shark.sep +shark.programName.toLowerCase() + ".jar";
                    if(!new File(bkupto).exists()){
                        String cmds[] = new String[]{"cp", bkupfrom, bkupto};
                        Runtime.getRuntime().exec((cmds));
                    }
            }
            catch(Exception ee){
             u.okmess(shark.programName, ee.getMessage());
            }
           }
            */
          String sharedversionno = (String) db.find(optionsdb, "vernumber", db.TEXT);
          int storedver = -1;
          if(sharedversionno==null)
              newVersion = newSubVersion = newSubSubVersion = true;
          else{
              try{
                 storedver = Integer.parseInt(u.replaceEvery(sharedversionno, ".", ""));
              }
              catch(Exception e){}  
              String vshared[] = u.splitString(sharedversionno, ".");
              String vprog[] = u.splitString(shark.versionNoDetailed, ".");
              if(!vshared[0].equals(vprog[0])){
                  newVersion = newSubVersion = newSubSubVersion = true;
              }
              if(vshared.length<2 || !vshared[1].equals(vprog[1])){
                  newSubVersion = newSubSubVersion = true;
              }
              if(vshared.length<3 || !vshared[2].equals(vprog[2])){
                  newSubSubVersion = true;
              }           
          }
          if(newSubSubVersion){
               db.delete(sharkStartFrame.optionsdb, "critcalpatchnoask", db.TEXT);
               db.delete(sharkStartFrame.optionsdb, "reminderpatchnoask", db.TEXT);
               db.delete(sharkStartFrame.optionsdb, "lastpatchreminderdate", db.TEXT);
               db.update(sharkStartFrame.optionsdb, "startofcurrentversion", 
                     new String[] {String.valueOf(System.currentTimeMillis())}, db.TEXT);
          }
          if(newVersion){
              LicenceAgreement_base l = new LicenceAgreement_base();
              while (l.running)
                u.pause(1000);
           }
          activationInit();
          if(!Demo_base.isDemo && shark.production) typecheck();
          if(newVersion && nooptions && !shark.network && !shark.singledownload){
                  // if new version with empty shared - ask whether to import from previous version
                     String ss[] = null;
                     if(shark.macOS)ss = u.splitString(u.gettext("v4changes", "prevsharedloc_mac"));
                     else {
                         ss = u.splitString(u.gettext("v4changes", "prevsharedloc_pc"));
                         String root = sharkStartFrame.sharedPathplus.substring(0,1);
                         for(int i = 0; i < ss.length; i++){
                             ss[i] = ss[i].replaceFirst("%", root);
                             if((ss[i].indexOf('%'))>=0){
                                 ss[i] = ss[i].replaceFirst("%", shark.getVersionType(null));
                             }
                         }
                     }
                     for(int i = 0; i < ss.length; i++){
                         File fle;
                         if((fle = new File(ss[i])).exists()){
                            String s1[] = db.dblistnames(fle);
                            if(s1.length>0){
                                ImportMess im = new ImportMess(ss[i]);
                                while (im.running)
                                  u.pause(1000);
                                nooptions = false;
                                break;
                            }
                         }
                     }
              }
          else{
            File importPath = null;
            if((importPath = detectImportableShared())!= null){
                new ImportShared_base(importPath);
            }              
          }
//          }
//          activationInit();

          if(newSubSubVersion){
              // when to update the version in the shared
              // if not network - if ever different
              // if network - only if newer
              boolean writeVer = false;
              if(shark.network){
                  if(thisver > storedver) writeVer = true;
                  String vnfpatch = (String) db.find(optionsdb, "vernumbercriticalpatch", db.TEXT);
                  int sharedlastcricitalver = -1;
                  if(vnfpatch != null){
                     try{
                        sharedlastcricitalver = Integer.parseInt(u.replaceEvery(vnfpatch, ".", ""));
                     }
                     catch(Exception e){}
                  }       
                  // update last critcal version if necessary
                  int thislastcriticalver = Integer.parseInt(u.replaceEvery(shark.versionLatestCritical, ".", ""));
                  if(thislastcriticalver > sharedlastcricitalver) {
                     db.update(optionsdb, "vernumbercriticalpatch", shark.versionLatestCritical, db.TEXT); 
                     sharedlastcricitalver = thislastcriticalver;
                  }
                  if(thisver < sharedlastcricitalver){
                      OptionPane_base.doPatch(sharkStartFrame.mainFrame, OptionPane_base.PATCH_USER_CRITICALBLOCK);
                  }
              }
              else writeVer = true;
              if(writeVer){
                db.update(optionsdb, "vernumber", shark.versionNoDetailed, db.TEXT);
                if(newSubVersion){
                    backupShared(thisver, storedver, !nooptions);
                }
              }
          }
        }
 

        File detectImportableShared(){
            String[] searchRoots;         
            if(shark.macOS){
                searchRoots = new File(shark.sep+"Volumes"+shark.sep).list(); 
            }
            else{
                searchRoots = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
                for(int i = 0; i < searchRoots.length; i++){
                    searchRoots[i] = searchRoots[i]+shark.sep;
                }
            }
            searchRoots = u.addString(searchRoots, u.getDesktopPath()+shark.sep, 0);
            File importFile = null;

            String prog = shark.programName.toLowerCase()+"-shared";
            String regex = prog + "_.*m.zip";
            for(int i = 0; i < searchRoots.length; i++){
                File dir = new File(searchRoots[i]);
                final Pattern p = Pattern.compile(regex); // careful: could also throw an exception!
                File[] files = dir.listFiles(new FileFilter(){
                    @Override
                    public boolean accept(File file) {
                        return p.matcher(file.getName()).matches();
                    }
                });                
                if(files!=null && files.length>0){
                    importFile = files[0];
                    break;
                }             
            } 
            if(importFile==null)return null;
            if(db.query(sharkStartFrame.optionsdb,"imf_"+importFile.getName(),db.TEXT )>=0)return null; 
            try{
                String maccodes[] = u2_base.getMacAddresses();
                if(maccodes!=null && maccodes.length>0){
                    boolean found = false;
                    for(int j = 0; j < maccodes.length; j++){
                        if(importFile.getName().startsWith(prog+"_"+maccodes[j])) found = true;           
                    }
                    if(found)return null;         
                }
                return importFile;
            }
            catch(Exception e){}
            return null;
        }
                
        
        void backupShared(int thisv, int storedv, boolean hasoptions){
            if((thisv > storedv) && hasoptions){
                loadprogressbar = new progress_base(this, shark.programName,
                                               u.edit(u.gettext("progressbar_", "loadnewver"), shark.programName),
                                               new Rectangle(sharkStartFrame.screenSize.width/4,
                                                             sharkStartFrame.screenSize.height*2/5,
                                                             (sharkStartFrame.screenSize.width/2),
                                                             (sharkStartFrame.screenSize.height/5)));
              File[] f = sharkStartFrame.sharedPath.listFiles(new FileFilter() {
                public boolean accept(File file) {
                  return !file.isDirectory();}});

                if(f.length>0){
                  String bkupprefix = u.gettext("backup_", "WS");
                  String[] o = (String[]) db.find(sharkStartFrame.optionsdb, "backup_schedule", db.TEXT);
                  String location;
                  if (o != null && o.length > 0 && o[0] != null) location = o[0];
                  else location = sharkStartFrame.sharedPathplus + File.separator + bkupprefix + "backups";
                  File ff;
                  if ((ff = new File(location + File.separator + bkupprefix + u.edit(u.gettext("backup_", "newversion"), String.valueOf(shark.versionNoDetailed)))).mkdirs()) {
                    for (int i = 0; i < f.length; i++) {
                      if (f[i] != null) {
                        u.copyfile(f[i], new File(ff.getAbsolutePath() + File.separator + f[i].getName()));
                      }
                    }
                  }
                }
            }
        }        

        String[] stripSpacesFromUserName(String[] cnames){
            try{
                for(int i = 0; i < cnames.length; i++){
                    String ctrimmed = cnames[i].trim();
                    if(!ctrimmed.equals(cnames[i])){
                        File oriFile = new File(sharkStartFrame.sharedPathplus+cnames[i]+".sha");
                        if(oriFile.renameTo(new File(sharkStartFrame.sharedPathplus+ctrimmed+".sha"))){
                            // tstu.saveStudent() done anyway as student version out of date
                            student.getStudent(ctrimmed);
                            cnames[i] = ctrimmed;
                        }
                    }
                }
            }
            catch(Exception e){}
            return cnames;
        }


    void updateUserFiles(String[] cnames) {
        // change own word lists to the new format
        for (int j = 0; j < cnames.length; j++) {
            String name = cnames[j];
            u.updateUser(name);
            // change all teachers to administrators in single user
            student tstu = student.getStudent(name);
            if (tstu != null) {
                boolean changed1 = false;
                boolean changed2 = false;
                if (tstu.spritename != null && !tstu.spritename.equals("x_aaa")
                        && sharkImage.find(tstu.spritename) == null) {
                    changed1 = true;
                    tstu.spritename = null;
                }
                if (!shark.network && tstu.teacher) {
                    tstu.teacher = false;
                    changed2 = true;
                }
                if (changed1 || changed2) {
                    tstu.saveStudent();
                    if (changed2) {
                        student.checkadmin(tstu);
                    }
                }
            }
        }
    }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  /**
   *Creates dialogue box asking for cd to be inserted if production or a network version is in use.
   */
//startPR2008-06-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//  int checkcd() {
//    if(Demo_base.isDemo)return 0;
//    Object active;
//    if(shark.macOS){
//      while (!shark.checkcd1.exists() || !shark.checkcd2.exists() || !shark.checkcd3.exists()) {
//        if((active=KeyboardFocusManager.getCurrentKeyboardFocusManager().getActiveWindow())==null) return -1;
//        if (u.choose(u.gettext("cdremoved", "heading"), u.gettext("cdremoved", "message"),
//                     new String[] {"OK","CANCEL"},active)==1)
//          mainFrame.finalize();
//      }
//    }
//    else{
//      File tester = new File(shark.checkcd);
//      while(!tester.exists()) {
//        if((active=KeyboardFocusManager.getCurrentKeyboardFocusManager().getActiveWindow())==null) return -1;
//        if(u.choose(u.gettext("cdremoved","heading"),u.gettext("cdremoved","message"),
//                    new String[]{"OK","CANCEL"}, active)==1)
//            mainFrame.finalize();
//      }
//    }
//    return 0;
//  }
  void checkcd() {
    if(!shark.licenceType.equals(shark.LICENCETYPE_DVD) && !shark.licenceType.equals(shark.LICENCETYPE_NETWORKACTIVATION))return;
    String ss[];
    boolean donefailcount = false;
    while (!"0".equals((ss=checkcdfilesexist())[0])) {
//startPR2009-11-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      if(shark.licenceType.equals(shark.LICENCETYPE_NETWORKACTIVATION)){
          if(cdcheckfirstcheck){
              testTimer.setDelay(CDCHECKDELAY_NA_FAILED);
              testTimer.setInitialDelay(CDCHECKDELAY_NA_FAILED);
          }
          if(!donefailcount){
              cdcheckfailcount++;
              donefailcount = true;
          }
          if(cdcheckfailcount < CDCHECKFAILMAX || wan.isInOverride(ss)) {
              testTimer.start();
              return;
          }
//        if (u.choose(u.gettext("webauthenticate", "netservercontitle"), serr,
//                     new String[] {"OK", "CANCEL"}, mainFrame) == 1){
//          mainFrame.finalize();
//        }
          if (loadprogressbar != null) {
              loadprogressbar.setVisible(false);
          }
          if(WebAuthenticateNetwork_base.overrideState == WebAuthenticateNetwork_base.OVERRIDOVERRIDESTATE_ELAPSED){
             OptionPane_base.getErrorMessageDialog(mainFrame, 99, u.gettext("webauthnet", "overrideelapsed"),
                     OptionPane_base.ERRORTYPE_EXIT);
          }
          else if(ss[0] != null)
            OptionPane_base.getErrorMessageDialog(mainFrame, Float.parseFloat(ss[0]), ss[1], OptionPane_base.ERRORTYPE_CANCELEXIT);
          else
            OptionPane_base.getErrorMessageDialog(mainFrame, -1, ss[1], OptionPane_base.ERRORTYPE_CANCELEXIT);
      }
      else{
        if (u.choose(u.gettext("cdremoved", "heading", shark.programName), u.gettext("cdremoved", "message", shark.programName),
                     new String[] {"OK", "CANCEL"}, mainFrame) == 1)
          mainFrame.finalize();
      }
//endPRauth^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    }
    if(shark.licenceType.equals(shark.LICENCETYPE_NETWORKACTIVATION)){
        testTimer.setDelay(CDCHECKDELAY_NA_NORMAL);
        testTimer.setInitialDelay(CDCHECKDELAY_NA_NORMAL);
    }
    cdcheckfailcount = 0;
    testTimer.start();
  }
  String[] checkcdfilesexist() {
//startPR2009-11-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    if(shark.licenceType.equals(shark.LICENCETYPE_NETWORKACTIVATION)){
//startPR2012-02-01^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      return wa.getNetDetails();
      return wan.getNetDetails();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    }
    else{
        if(shark.network || !shark.production)return new String[]{"0"};
//startPR2010-02-23^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//        if(shark.macOS) return (shark.checkcd1.exists() && shark.checkcd2.exists() && shark.checkcd3.exists());
//        else return new File(shark.checkcd).exists();
        return new String[]{(new File(shark.checkcd).exists())?"0":"-1"};
//endPRtoupdate^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    }
//endPRauth^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR

  public static String getPrimarySoundDb(String dbs[]){
    if(dbs[0].endsWith(soundPatchExtra))
        return dbs[1];
    else return dbs[0];
  }  
  
  String getstartingcourse() {
    String init = (String)db.find(optionsdb,"start_topic",db.TEXT);
    String courses[] = new String[mcourses.length];
    for (short i = 0; i < mcourses.length; ++i) {
        courses[i] = mcourses[i].getText();
    }
    int start;
    if(init==null || (start = u.findString(courses,init)) < 0) start = 0;
    String ret = (String) JOptionPane.showInputDialog(mainFrame,u.gettext("mdefcourse","defcourse"),null,
                                         JOptionPane.INFORMATION_MESSAGE, null,
                                         courses,courses[start]);
    db.update(optionsdb,"start_topic",ret,db.TEXT);
    return ret;
  }

//startPR2008-08-14^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  class showacl
    extends JFrame {
  BorderLayout layout1 = new BorderLayout();
  JButton exit;
  public showacl() {
    this.setResizable(true);
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    setBounds(new Rectangle(10,10,
          sharkStartFrame.screenSize.width*5/12,
          sharkStartFrame.screenSize.height*13/16));
    this.getContentPane().setLayout(new GridBagLayout());
    GridBagConstraints grid = new GridBagConstraints();
    JPanel mainpan = new JPanel(new GridBagLayout());
    grid.weighty = 1;
    grid.weightx = 1;
    grid.gridx = 0;
    grid.gridy = -1;
    this.setEnabled(true);
    this.setTitle(u.edit(u.gettext("chmodshared", "title"),shark.programName.toLowerCase()));
    setIconImage(sharkStartFrame.sharkicon);
    JButton launchTerminal = new JButton(u.gettext("chmodshared", "terminal"));
    launchTerminal.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        try{
          Runtime.getRuntime().exec(new String[] {"open", "-a", "Terminal"});
        }
        catch (IOException ee){}
        getRootPane().setDefaultButton(exit);
        getRootPane().getDefaultButton().requestFocus();
      }
    });
    JTextPane textPane1 = new textpane();
    textPane1.setEditable(false);
    textPane1.setContentType("text/html");
    textPane1.setText("sudo fsaclctl -p / -e");
    textPane1.setCaretPosition(0);
    textPane1.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
    JTextPane textPane2 = new textpane();
    textPane2.setEditable(false);
    textPane2.setContentType("text/html");
    textPane2.setText("sudo chmod -R +a \"everyone allow delete,chown,list,search,add_file,add_subdirectory,delete_child,file_inherit,directory_inherit\" \""+ sharkStartFrame.sharedPath.getAbsolutePath()+"\"");
    textPane2.setCaretPosition(0);
    textPane2.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
    JTextArea ta1 = new JTextArea();
    ta1.setWrapStyleWord(true);
    ta1.setLineWrap(true);
    ta1.setBorder(BorderFactory.createEmptyBorder());
    ta1.setOpaque(false);
    ta1.setEditable(false);
    ta1.setHighlighter(null);
//    ta1.setText(u.formatCommandSymbol(u.edit(u.gettext("chmodshared", "text1").replace('|', '\n'),shark.programName.toLowerCase())));
    ta1.setText(u.edit(u.gettext("chmodshared", "text1").replace('|', '\n'),shark.programName.toLowerCase()));
    JTextArea ta2 = new JTextArea();
    ta2.setWrapStyleWord(true);
    ta2.setLineWrap(true);
    ta2.setBorder(BorderFactory.createEmptyBorder());
    ta2.setOpaque(false);
    ta2.setEditable(false);
    ta2.setHighlighter(null);
//    ta2.setText(u.formatCommandSymbol(u.gettext("chmodshared", "text2").replace('|', '\n')));
    ta2.setText(u.gettext("chmodshared", "text2").replace('|', '\n'));
    JTextArea ta3 = new JTextArea();
    ta3.setWrapStyleWord(true);
    ta3.setLineWrap(true);
    ta3.setBorder(BorderFactory.createEmptyBorder());
    ta3.setOpaque(false);
    ta3.setEditable(false);
    ta3.setHighlighter(null);
//    ta3.setText(u.formatCommandSymbol(u.gettext("chmodshared", "text3").replace('|', '\n')));
    ta3.setText(u.gettext("chmodshared", "text3").replace('|', '\n'));
    JTextArea ta4 = new JTextArea();
    ta4.setWrapStyleWord(true);
    ta4.setLineWrap(true);
    ta4.setBorder(BorderFactory.createEmptyBorder());
    ta4.setOpaque(false);
    ta4.setEditable(false);
    ta4.setHighlighter(null);
//    ta4.setText(u.formatCommandSymbol(u.gettext("chmodshared", "text4").replace('|', '\n')));
    ta4.setText(u.gettext("chmodshared", "text4").replace('|', '\n'));
    exit = new JButton(u.gettext("finish", "label"));
    exit.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        dispose();
      }
    });
    grid.fill = grid.BOTH;
    grid.weighty = 0;
    mainpan.add(ta1, grid);
    grid.weighty = 1;
    grid.insets = new Insets(10, 20, 10, 20);
    mainpan.add(new u.uuScrollPane(textPane1), grid);
    grid.insets = new Insets(0, 0, 0, 0);
    grid.weighty = 0;
    mainpan.add(ta2, grid);
    grid.fill = grid.NONE;
    grid.insets = new Insets(10, 0, 10, 0);
    mainpan.add(launchTerminal, grid);
    grid.insets = new Insets(0, 0, 0, 0);
    grid.fill = grid.BOTH;
    grid.weighty = 0;
    mainpan.add(ta3, grid);
    grid.weighty = 1;
    grid.insets = new Insets(10, 20, 10, 20);
    mainpan.add(new u.uuScrollPane(textPane2), grid);
    grid.insets = new Insets(0, 0, 0, 0);
    grid.weighty = 0;
    mainpan.add(ta4, grid);
    grid.insets = new Insets(10, 0, 10, 0);
    grid.fill = grid.NONE;
    mainpan.add(exit, grid);
    grid.fill = grid.BOTH;
    grid.weighty = 1;
    grid.insets = new Insets(10, 10, 10, 10);
    getContentPane().add(mainpan, grid);
    getRootPane().setDefaultButton(launchTerminal);
    getRootPane().getDefaultButton().requestFocus();
    setVisible(true);
    validate();
  }
  public void dispose(){
    try{
      Runtime.getRuntime().exec(new String[] {"osascript", "-e", "'tell application \"Terminal\" to quit'"});
    }
    catch (IOException ee){}
    sharkStartFrame.mainFrame.setState(NORMAL);
    u.focusBlock = false;
    super.dispose();
  }

}

  class textpane extends JTextPane {
    public void paint(Graphics g) {
      ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
      super.paint(g);
    }
  }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  static class gameflag {
    boolean needsyllsplit;
    boolean needanysplit;
    boolean needonset;
    boolean needbad;
    boolean usepattern;
    boolean special;
    boolean pairedwords;
    boolean justpairedwords;
    boolean notpairedwords;
    boolean nosingleletters;
    boolean multisyllable;
    boolean spelling;
//    boolean autospell;
    boolean recognition;
    boolean alphabet;
    boolean nottranslations;
    boolean notdefinitions;
    boolean flonly;
    boolean notfl;
    boolean notifdups;
    boolean phonics;
    boolean notphonics;
    boolean phonicsingles;
    boolean phonicsw;
    boolean notblended;
    boolean notphonicsw;
    boolean needpictures;
    boolean needsentences1;
    boolean needsentences3;
    boolean needchunks;
    boolean avwordlen4;
    boolean needshapes;
    boolean notshapes;
//startPR2007-12-20^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
 //   boolean indemo;
    boolean demoexclude;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    boolean owllackpics;
    boolean owllackrecs;
    boolean owllackextrarecs;
    boolean owlneedrec;
    boolean notmultisyllable;

    gameflag() {}
    gameflag(String game) {
      String lists[],values[];                                     //Game has already been saved
      lists = db.dblist(new File[]{sharkStartFrame.publicPath});
      int i,j,k;
      outer:for(k=0;k<lists.length;++k) {                                //For 2.1
        if((values = (String[])db.find(lists[k],game,db.GAME)) != null) {//Condition 2.1.1 - Game is in list
           for(j=0;j<values.length;++j) {                                //For 2.1.1.1
              if(values[j].equalsIgnoreCase("needsyllsplit")) needsyllsplit = true;
              if(values[j].equalsIgnoreCase("needanysplit")) needanysplit = true;
              if(values[j].equalsIgnoreCase("needonset")) needonset = true;
              if(values[j].equalsIgnoreCase("needbad")) needbad = true;
              if(values[j].equalsIgnoreCase("usepattern")) usepattern = true;
              if(values[j].equalsIgnoreCase("special")) special = true;
              if(values[j].equalsIgnoreCase("pairedwords")) pairedwords = true;
              if(values[j].equalsIgnoreCase("justpairedwords")) justpairedwords = true;
              if(values[j].equalsIgnoreCase("notpairedwords")) notpairedwords = true;
              if(values[j].equalsIgnoreCase("nosingleletters")) nosingleletters = true;
              if(values[j].equalsIgnoreCase("multisyllable")) multisyllable = true;
              if(values[j].equalsIgnoreCase("notmultisyllable")) notmultisyllable = true;
              if(values[j].equalsIgnoreCase("spelling")) spelling = true;
//              if(values[j].equalsIgnoreCase("autospell")) autospell = true;
              if(values[j].equalsIgnoreCase("recognition")) recognition = true;
              if(values[j].equalsIgnoreCase("alphabet")) alphabet = true;
              if(values[j].equalsIgnoreCase("nottranslations")) nottranslations = true;
              if(values[j].equalsIgnoreCase("notdefinitions")) notdefinitions = true;
              if(values[j].equalsIgnoreCase("flonly")) flonly = true;
              if(values[j].equalsIgnoreCase("notfl")) notfl = true;
              if(values[j].equalsIgnoreCase("notifdups")) notifdups = true;
              if(values[j].equalsIgnoreCase("phonics")) phonics = true;
              if(values[j].equalsIgnoreCase("notblended")) notblended = true;
              if(values[j].equalsIgnoreCase("notphonics")) notphonics = true;
              if(values[j].equalsIgnoreCase("phonicsingles")) phonicsingles = true;
              if(values[j].equalsIgnoreCase("phonicsw")) phonicsw = true;
              if(values[j].equalsIgnoreCase("notphonicsw")) notphonicsw = true;
              if(values[j].equalsIgnoreCase("needpictures")) needpictures = true;
              if(values[j].equalsIgnoreCase("needsentences1")) needsentences1 = true;
              if(values[j].equalsIgnoreCase("needsentences3")) needsentences3 = true;
              if(values[j].equalsIgnoreCase("needchunks")) needchunks = true;
              if(values[j].equalsIgnoreCase("avwordlen4")) avwordlen4 = true;
              if(values[j].equalsIgnoreCase("needshapes")) needshapes = true;
              if(values[j].equalsIgnoreCase("notshapes")) notshapes = true;
//startPR2007-12-20^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//            if(values[j].equalsIgnoreCase("indemo")) indemo = true;
              if(values[j].equalsIgnoreCase("demoexclude")) demoexclude = true;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
              if(values[j].equalsIgnoreCase("owllackpics")) owllackpics = true;
              if(values[j].equalsIgnoreCase("owlneedrec")) owlneedrec = true;
           }
        }
      }
    }
    static gameflag  get(String game) {
       int i = u.findString(sharkStartFrame.gamename,game);
       if(i>=0) return sharkStartFrame.gameflags[i];
       return null;
    }
  }

  /**
   * <p>Title: WordShark</p>
   * <p>Description: switches students</p>
   * <p>Copyright: Copyright (c) 2003</p>
   * <p>Company: WhiteSpace</p>
   * @author Roger Burton
   * @version 1.0
   */
  class studentmenulistener
      implements ActionListener {
    public short studentno;
    public studentmenulistener(short i) {
      studentno = i;
    }

    public void actionPerformed(ActionEvent e) {
      savesplitwords();
      studentList[currStudent].saveStudent();
      studentList[studentno].finishSignon(false); // not real sign-on
    }
  }

  /**
   * The current students database name is concatenated with the public topic
   * library and then returned. This is done whether or not the current student has topics chosen.
   * @param stu short indicating current student
   * @return Database name concatenated with publicTopicLib
   */
  public static String[] searchList(short stu) {
    short i, j;

    if (studentList == null || stu < 0)
      return new String[0];
    if (!studentList[stu].isnew) {
      return u.addString(new String[] {studentList[stu].name}
                         ,
                         publicTopicLib);
    }
    return publicTopicLib;
  }

  /**
   * The current students database name is concatenated with the public topic library
       * and then returned. This is only done if the current student has topics chosen.
   * @param stu short indicating current student
   * @return Database name concatenated with publicTopicLib
   */
  public static String[] searchList2(short stu) {
    short i, j;
    String ret[] = publicTopicLib;
    if (studentList == null || stu < 0
        )
      return new String[0];
    if (!studentList[stu].isnew
        && !sharkStartFrame.studentList[sharkStartFrame.currStudent].administrator
        && sharkStartFrame.studentList[sharkStartFrame.currStudent].hastopics) {
      return u.addString(new String[] {studentList[stu].name}
                         , ret);
    }
    return ret;
  }

  /**
   * The public game library is returned if student has no games
   * otherwise the student's database concatenated with the publicGameLib is returned.
   * @param stu Current student
   * @return String[] containing games
   */
  public static String[] searchListGame(short stu) {
    short i, j;
    if (studentList == null || stu < 0
        || !sharkStartFrame.studentList[sharkStartFrame.currStudent].hasgames) {
      return publicGameLib;
    }
    if (!studentList[stu].isnew) {
      return u.addString(new String[] {studentList[stu].name}
                         ,
                         publicGameLib);
    }
    return publicGameLib;
  }

  /**
   * Returns the public Text Library
   * @param stu short
   * @return String[] publicTextLib
   */
  public static String[] searchListText(short stu) {
    short i, j;

    if (studentList == null || stu < 0
        || !sharkStartFrame.studentList[sharkStartFrame.currStudent].hastext
        )
      return publicTextLib;
    if (!studentList[stu].isnew) {
      return u.addString(new String[] {studentList[stu].name}
                         ,
                         publicTextLib);
    }
    return publicTextLib;
  }

  /**
   * The required library is added to the students database.
   * @param stu short
   * @param type byte that indicates which library should be added to the students database
   * @return String or String[0]
   */
  public static String[] updateList(short stu, byte type) {
    short i, j;
    String s[];
    student st;
    String[] publics = (type == db.GAME) ? publicGameLib :
        ( (type == db.TEXT) ? publicTextLib : publicTopicLib);

    if (studentList == null || stu < 0 || studentList[stu].isnew)
      return new String[0];
    s = new String[] {
        studentList[stu].name};
    s = u.addString(s, publics);
    return s;
  }
  
  



  void topicListSelectionChanged(TreeSelectionEvent e){
        if(gameicons)return;
        jnode sel = topicList.getSelectedNode();
        if (sel == null)
          return;   //If 1 - Nothing selected
        if(e!=null){
        if((wantplayprogram && u.findString(cl, sel.get())>=0) ||  sel.getLevel()==1){
          topicList.setSelectionPath(e.getOldLeadSelectionPath());
          return;
        }
//startPR2007-11-28^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        if(sel.get().trim().equals("")){
            
          topicList.setSelectionPath(e.getOldLeadSelectionPath());
          return;
        }
      }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2007-11-26^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//        if(topicList.isInitialSelection
//           && studentList[overrideStudent()].currTopic != null
//           && !studentList[overrideStudent()].currTopic.endsWith(sel.get()))
//          topicList.isInitialSelection = false;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2010-05-24^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        topicinfo.setVisible(false);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        if (playingGames) { //If 2 - games are playable
          if (!forcechange) { //If 2.1 - No changes allowed to menu
                                                                                    //  rb 16/3/08  mmmm start
//            if (!sel.isLeaf() && (!wantplayprogram || !forcerevision(sel) || sel.getLevel()<2)/* && !((jnode)sel).inphonics()*/) {  // rb 12/12/07
            if (!sel.isLeaf() && (!wantplayprogram || !forcerevision(sel)
                                  || !sel.dontexpand)) {
                                                                                  //  rb 16/3/08  mmmm end
              usingsuperlist = null;
              specialsuperlist = false;
              if (showteachingnotes != null) { //If 2.1.1.1
                showteachingnotes.setVisible(false); //-there are teaching notes
                showteachingnotes.dispose();
                showteachingnotes = null;
              }
              stopGamepanel();
              bevelPanel5.removeAll();
              gameTree.removeAllChildren(gameTree.root);
              printlist.setEnabled(false);
              bevelPanel2.removeAll();
              addsuperbutton(sel);
              bevelPanel2.validate();
              bevelPanel5.validate();
              bevelPanel2.repaint();
              bevelPanel5.repaint();
            }
            else { //Else 2.1.1
              usingsuperlist = null;
              specialsuperlist = false;
              useselectedlist = false; //Selected node is a leaf
              canshowmarkedgames = false;
              if(e==null)
                setupGametree(false, true);
              else
                setupGametree();
              addteachingnotes(false);
//startPR2005-10-16^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
              if(shark.macOS)
                bevelPanel1.repaint();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            }
          }
        }
        else { //Else 2- Games are not playable
          if (!forcechange) //If 2.1
            topicList.newSelection(); //- no change to menu are occurring
        }
  }
  /**
       * Creates an instance of a topicTree and gives it a TreeSelectionListener and a
   * MouseListener.
   */
  void gettopiclist() {
    topicList = new topicTree();
    topicList.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
    topicList.dbnames = new String[0]; // we need dbnames for revision list generation
//    topicList.setToolTipText(u.gettext("topictree", "tooltip_base"));
    topicList.getSelectionModel().addTreeSelectionListener(new
        TreeSelectionListener() {
      public void valueChanged(TreeSelectionEvent e) {
        topicListSelectionChanged(e);
        setListTexts();
      }
    });
    topicList.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseReleased(MouseEvent e) {
        if (playingGames) {
          return;
        }
        topicList.newSelection();
        if (e.getModifiers() == e.BUTTON3_MASK) {
          topicList.rightClick();
        }
        lastSelectForCopy = topicList;
      }
    });
    topicList.setEditable(false);
  }

  public void setBgMenuSelected(boolean selected) {
    bgcolor.setSelected(selected);
  }

//startPR2010-05-24^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  void getcourselist() {
    courseList = new topicTree();
    courseList.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
    courseList.dbnames = new String[0]; // we need dbnames for revision list generation
//    courseList.setToolTipText(u.gettext("topictree", "tooltip_base"));
    courseList.getSelectionModel().addTreeSelectionListener(new
        TreeSelectionListener() {
      public void valueChanged(TreeSelectionEvent e) {
        jnode sel = courseList.getSelectedNode();
        if(sel==null){
            courseList.setSelectionPath(new TreePath(((jnode)courseList.root.getFirstChild()).getPath()));
            sel = courseList.getSelectedNode();
        }
        if(e!=null && sel.get().trim().equals("")){
            courseList.setSelectionPath(e.getOldLeadSelectionPath());
            return;
        }
        if(pnCourseInfo!=null)pnCourseInfo.setVisible((sel != null));
        if(sel != null){
          String s = sel.get();
          if(btTopicTest!=null)
              btTopicTest.setVisible(s.equals(TopicTest.courseName));
          String s2 = u.gettext("coursedescriptions", s);
          if(s2==null) {
              if(pnCourseInfo!=null)pnCourseInfo.setVisible(false);
          }
          else{
              courseinfo.setTextHtmlFormatted(s2.replace('|', ' '), treefont.deriveFont((float)treefont.getSize()-5));
          }
          setTopicList(sel.get(), courseList.exitValueChanged?topicList.getSelectedNode().get():null);
        }
      }
    });
    courseList.setEditable(false);
  }

  

  public void setTopicList(String course, String currsel){
//    topicList =new topicTree();
//      topicList.model.reload();
     publicExtraCourseLib = db.dblist(sharedPath, extracourses);
     topicList.dbnames = new String[0];
     topicList.root.setIcon(jnode.ROOTTOPICTREE);
     topicList.onlyOneDatabase = "*";
     topicList.onlyshowcourse = course;
     topicList.setup(searchList2(overrideStudent()), false, db.TOPICTREE, true,
              topicTree.MODE_NO_COMPRESS_HEADING, "");
     topicList.model.reload();
     if(currsel==null)
        topicList.setSelectionPath(new TreePath(topicList.root.getFirstLeaf().getPath()));
     else{
//startPR2024-04-05^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR  
//         TreePath path = new TreePath(topicList.find(currsel).getPath());
         TreePath path = new TreePath(topicList.findLeaf(currsel).getPath());
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         topicList.setSelectionPath(path);
         topicList.scrollPathToVisible(path);
     }
     bevelPanel4.validate();
     bevelPanel4.repaint();
  }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR

  boolean addsuperbutton(jnode sel) {
    String s;
    if (sel.getLevel() > 0
        && !sel.get().equals(topicTree.privatelists)
        && !sel.get().equals(topicTree.adminlists)
//        && !((jnode)sel).inphonics()                                // rb 12/12/07
        && (!wantplayprogram || !forcerevision(sel) || !sel.dontexpand)   //  rb 16/3/08  mmmm
        && sel.icon != jnode.icons[jnode.PROJECT]
        && !((jnode)(sel.getParent())).get().equals(topicTree.adminlists)) {

      GridBagConstraints grid1 = new GridBagConstraints();
      grid1.weightx = grid1.weighty = 1;
      grid1.fill = GridBagConstraints.BOTH;
      grid1.gridx = grid1.gridy = 0;
      int resi;
      String course = null;
      if(topicList.root!=null && topicList.root.getChildCount()>0)
          course = ((jnode)topicList.root.getChildAt(0)).get();
      if((resi = topicList.isNodeFastModeable(sel, course))<0) {              // start rb 12/12/07
          if(resi==-1)fasttpna.setText(strnotfastmode );
          else if(resi==-4)fasttpna.setText(strnotfastmodeheading);
          else fasttpna.setText(strnotfastmodeheading );
          bevelPanel2.add(fastmodena, grid1);
          return true;
      }                                         // end rb 12/12/07
      lbfastmlist.setText(sel.get());

if(fminfo.getIcon()==null){
     int sw = sharkStartFrame.mainFrame.getWidth();
     int buttondim = (sw*14/22)/24;
     int buttonimdim =  buttondim- (buttondim/5);
     fminfo.setPreferredSize(new Dimension(buttondim, buttondim));
     fminfo.setMinimumSize(new Dimension(buttondim, buttondim));
     Image im = Toolkit.getDefaultToolkit().getImage(sharkStartFrame.publicPathplus + "sprites" +
                                       sharkStartFrame.separator +
                                       "infoSQ_il48.png");
     ImageIcon iiinfo = new ImageIcon(im.getScaledInstance(buttonimdim,
            buttonimdim, Image.SCALE_SMOOTH));
     fminfo.setIcon(iiinfo);
        }

      fastwordlist.setText(sel.get());
      bevelPanel2.add(fastmodeok, grid1);
      return true;
    }
    return false;
  }
  
//  void setupsuper1() { // superlist unmanaged
//    if(true)  {
//      setupsuper2();
//      return;
//    }
//    savesplitwords();
//    simplesuper=true;
//    spellingonly=false;
//    usingsuperlist=null;
//    if(wantplayprogram) usingsuperlist = new topic((jnode) topicList.getSelectedNode()); //  rb 18/3/08  mmmm
//    else usingsuperlist = new topic(                                                     //  rb 18/3/08  mmmm
//        ( (jnode) topicList.getSelectedNode()).get(),
//        topicList.getPath2( (jnode) topicList.getSelectedNode()));
//    usingsuperlist.teachingnotes =
//        u.splitString(u.gettext("superbutton", "teachingnotes"));
//    sametopic = true;
//    useselectedlist = false;
//    specialsuperlist = true;
//    setupGametree();
//    gameicons = true;
//    switchdisplay();
//    Timer tnTimer = new Timer(1000, new ActionListener() {
//      public void actionPerformed(ActionEvent e) { //Event 7.1.1
//        addteachingnotes(true);
//      }
//    });
//    tnTimer.setRepeats(false);
//    tnTimer.start();
//  }
  void setupsuper2() {    // managed superlist
//    simplesuper = false;
    savesplitwords();
    spellingonly=false;
    usingsuperlist=null;
    if(wantplayprogram)
        usingsuperlist = new topic((jnode) topicList.getSelectedNode()); //  rb 18/3/08  mmmm
    else
        usingsuperlist = new topic(                                                     //  rb 18/3/08  mmmm
        ( (jnode) topicList.getSelectedNode()).get(),
        topicList.getPath2( (jnode) topicList.getSelectedNode()));
    usingsuperlist.teachingnotes =
        u.splitString(u.gettext("superbutton", "teachingnotes2"));
//    sametopic = true;
    useselectedlist = false;
    specialsuperlist = true;
    setupGametree(currPlayTopic!=null && usingsuperlist.name.equals(currPlayTopic.name));
    gameicons = true;
    switchdisplay();
    Timer tnTimer = new Timer(1000, new ActionListener() {
      public void actionPerformed(ActionEvent e) { //Event 7.1.1
        addteachingnotes(true);
      }
    });
    tnTimer.setRepeats(false);
    tnTimer.start();
  }
  /**
   * Creates an instance of a topicTree and gives it a TreeSelectionListener.
   */
  
  void gettopictreelist() {
      gettopictreelist(false);
  }  
  
  void gettopictreelist(boolean topicBuilding) {
           
    topicTreeList = new topicTree();
    topicTreeList.getSelectionModel().addTreeSelectionListener(new
        TreeSelectionListener() {
      public void valueChanged(TreeSelectionEvent e) {
        jnode sel = topicTreeList.getSelectedNode();
        if (sel == null || forcechange)
          return;
        TreePath tps[] = topicTreeList.getSelectionPaths();
        if(tps!=null && tps.length>1){
            populateMarkGamesCodeCombo();
            if(markGamesCodeCombo!=null)markGamesCodeCombo.setVisible(true);
        }
        else{
            if(markGamesCodeCombo!=null)markGamesCodeCombo.setVisible(false);
        }
        mplay.setEnabled(sel.get().startsWith("help_"));
        topicTreeList.newSelection();
        lastSelect = lastSelectForCopy = topicTreeList;
      }
    });
    topicTreeList.setEditable(true);
//startPR2004-09-01^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    // enables exiting screen via the ESC key
    topicTreeList.addKeyListener(new KeyAdapter() {
      public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_ESCAPE)
          setupgames();
        final jnode jn = topicTreeList.getSelectedNode();
        if(jn!=null && e.isControlDown()){
            if(code == KeyEvent.VK_Q){
                if(db.query("publicimage", treepainter_imagelist.IGNOREPREFIX+jn.get(), db.TEXT)>=0){
                    db.delete("publicimage", treepainter_imagelist.IGNOREPREFIX+jn.get(), db.TEXT);
                }
                else{
                    db.update("publicimage", treepainter_imagelist.IGNOREPREFIX+jn.get(), "", db.TEXT);
                }
            }        
            else if(code == KeyEvent.VK_W){
              String ss[] = (String[])db.find("publicimage", treepainter_imagelist.NOTEPREFIX+jn.get(), db.TEXT);
                   stringedit_base se = new stringedit_base("text1", ss, sharkStartFrame.mainFrame, stringedit_base.MODE_NORMAL, false) {
                        public boolean update(String s[]) {
                            boolean allEmpty = true;
                          for(int i = 0; i < s.length; i++){
                              if(!s[i].trim().equals("")){
                                  allEmpty = false;
                                  break;
                              }
                          }
                          if(allEmpty)db.delete("publicimage", treepainter_imagelist.NOTEPREFIX+jn.get(), db.TEXT);
                          else db.update("publicimage", treepainter_imagelist.NOTEPREFIX+jn.get(), s, db.TEXT);
                         return true;
                       }
                   }; 
                   se.staythere = true;

            }
            else if(shark.showPhotos && code == KeyEvent.VK_M){
              movePhoto();
            }
        }
      }
    });
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  }
  
  
  void movePhoto(){
            if(shark.showPhotos){
              if(currPhoto!=null){
                  if(currPhotoPos == CURRPHOTOPOS_NW){
                      currPhotoPos = CURRPHOTOPOS_NE;
                        currPhoto.setLocation(picChoice.getLocation().x,0);                      
                  }else if (currPhotoPos == CURRPHOTOPOS_NE){
                      currPhotoPos = CURRPHOTOPOS_SE;
                      currPhoto.setLocation(picChoice.getLocation().x,
                              sharkStartFrame.screenSize.height - currPhoto.getHeight());
                  }
                  else if(currPhotoPos == CURRPHOTOPOS_SE){
                      currPhotoPos = CURRPHOTOPOS_SW;
                      currPhoto.setLocation(0,
                              sharkStartFrame.screenSize.height - currPhoto.getHeight());                      
                  }
                  else{
                      currPhotoPos = CURRPHOTOPOS_NW;
                      currPhoto.setLocation(0,0);                         
                  }
              }

            }      
  }

  /**
   * When public topics are changed this as called.
   * @param e Action event
   */
  synchronized void PublicTopics_actionPerformed(ActionEvent e) {
    if (!bevelPanel1.isAncestorOf(topicTreeList) || showingPictures || //If 1
        updatingGames || updatingText) { //topic TreeList inherits from bevelPanel1
      closeprev(true); //OR Pictures are shown for words spoken
      updatingTopics = true;
      bevelPanel2.setBorder(null); //OR Games are being updated
      setTitle(topicstitle); //OR Public text is being updated
      forcechange = true;
      print.setEnabled(!shark.production); //used to print full topic list in ascii
      setforedit(true);
      gettopictreelist(true);
      topicTreeList.publicname = u.absoluteToRelative(sharkStartFrame.publicTopicLib[0]);
      topicTreeList.setup(updateList(currStudent, db.TOPIC), true, db.TOPIC, false,
                          "Topic lists for update");
      gettopiclist();
      topicList.onlyOneDatabase = null;
      topicList.setup(searchList(currStudent), false, db.TOPIC, false,
                      "For reference (right-click)");
      JScrollPane scroll1 = u.uScrollPane(topicTreeList);
      JScrollPane scroll2 = u.uScrollPane(topicList);
      grid1.gridx = -1;
      grid1.gridy = 0;
      grid1.weightx = grid1.weighty = 1;
      JPanel pan = new JPanel(new GridBagLayout());
      pan.add(scroll1, grid1);
      pan.add(scroll2, grid1);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2004-09-01^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      // enables exiting screen via the ESC key
      scroll1.addKeyListener(new KeyAdapter() {
        public void keyPressed(KeyEvent e) {
          int code = e.getKeyCode();
          if(code == KeyEvent.VK_ESCAPE)
            sharkStartFrame.mainFrame.setupgames();
        }
      });
      bevelPanel1.addKeyListener(new KeyAdapter() {
        public void keyPressed(KeyEvent e) {
          int code = e.getKeyCode();
          if(code == KeyEvent.VK_ESCAPE)
            sharkStartFrame.mainFrame.setupgames();
        }
      });
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      bevelPanel6.removeAll();
      bevelPanel2.removeAll();
      bevelPanel3.removeAll();
//startPR2008-08-05^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      grid1.weighty = 1;
      grid1.weightx = 1;
//      grid1.weighty = 0;
//      bevelPanel1.add(bevelPanel2, grid1);
      javax.swing.JSplitPane jsp = new javax.swing.JSplitPane(javax.swing.JSplitPane.HORIZONTAL_SPLIT,pan, bevelPanel2);
      grid1.fill = grid1.BOTH;
      bevelPanel1.add(jsp, grid1);
      jsp.setDividerLocation((int)(screenSize.width*2/3));
//      grid1.weighty = 1;
//      grid1.gridy = -1;
//      grid1.gridx = 0;
//      bevelPanel2.add(bevelPanel6, grid1);
//      bevelPanel2.add(bevelPanel3, grid1);
      

      btExcludeWordFromAPTest = new JButton(u.gettext("topictree", "excludedword"));
      btExcludeWordFromAPTest.setForeground(col_ExcludedWords);
      btExcludeWordFromAPTest.setEnabled(false);
      btExcludeWordFromAPTest.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
            jnode jn = currTopicView.getSelectedNode();
            String database = u.absoluteToRelative(publicTopicLib[0]);
            String s = jn.get();
            word w = new word(s, database);
            jnode ttnode = (jnode) topicTreeList.getSelectedNode();
            String path[] = topicTreeList.getAncestors(ttnode, database);        
            Object o[] = topic.getExcludedWord(database, path, w.v());
            int excluded = (int)o[0];
            String sss[][] = null;
            if(excluded >= -1){
                sss = (String[][])o[1];
            }
            if(excluded == -2){
                sss = new String[][]{ path };
            }
            else if (excluded == -1){
                String[][] newArray = Arrays.copyOf(sss, sss.length+1);
                newArray[newArray.length-1] = path;
                sss = newArray;
            }
            else{
                sss = u2_base.removeStringArray(sss, excluded);
            }
            jn.isExcludedWord = excluded<0;
            if(jn.isExcludedWord || (excluded>=0 && sss.length > 0)){
                db.update(database, topic.EXCLUDEDWORDPREFIX+w.v(), sss, db.TEXT);
            }
            else
                db.delete(database, topic.EXCLUDEDWORDPREFIX+w.v(), db.TEXT);
            jn.setIcon();
            currTopicView.repaint();
        }
      });
      JPanel jp  = new JPanel(new GridBagLayout());
      JPanel jp2  = new JPanel(new GridBagLayout());
      int border = 5;//sharkStartFrame.screendim.height/20;
      grid1.insets = new Insets(border,border,border,border);
           grid1.weightx = 1;
           grid1.anchor = grid1.EAST;
           grid1.fill = grid1.NONE;
           
      markGamesCodeCombo = new JComboBox();
      markGamesCodeCombo.setVisible(false);
           
           
       jp.add(markGamesCodeCombo, grid1);          
      jp.add(btExcludeWordFromAPTest, grid1);
      
      
      
      jp.add(btExcludeWordFromAPTest, grid1);
      grid1.anchor = grid1.CENTER;
      grid1.fill = grid1.BOTH;
            grid1.weightx = 1;
      grid1.insets = new Insets(0,0,0,0);
      jp2.add(jp, grid1);
   
      
      javax.swing.JSplitPane jsp2 = new javax.swing.JSplitPane(javax.swing.JSplitPane.VERTICAL_SPLIT,bevelPanel6, bevelPanel3);
      
      
      grid1.gridy = -1;
      grid1.gridx = 0;    
      grid1.weighty = 0;
      bevelPanel2.add(jp2, grid1);
      grid1.weighty = 1;
      
      
      bevelPanel2.add(jsp2, grid1);
      
             grid1.gridy = 0;
      grid1.gridx = -1;  
      jsp2.setDividerLocation((int)(screenSize.height*0.5));
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      bevelPanel1.validate();
      if (updatepath != null) { //If 1.1
        jnode node = topicTreeList.setCurrentTopicPath(updatepath); //There is a node selected and it is not a root node
        if (node != null) { //If 1.1.1
          TreePath tp = new TreePath(node.getPath());
          topicTreeList.setSelectionPath(tp);
          topicTreeList.scrollPathToVisible(tp);
          if (node.isLeaf()) //If 1.1.1.1
            topicTreeList.newSelection();
        }
      }
      if (refpath != null) { //If 1.2
        jnode node = topicList.setCurrentTopicPath(refpath); //There is a node selected, it is not a root node
        if (node != null) { //If 1.2.1
          TreePath tp = new TreePath(node.getPath());
          topicList.setSelectionPath(tp);
          topicList.scrollPathToVisible(tp);
        }
      }
//      keypad.activate(sharkStartFrame.mainFrame,
//                      new char[] { (char) keypad.SHIFT});
      forcechange = false;
//startPR2004-09-01^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      // enables exiting screen via the ESC key
      topicTreeList.requestFocus();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
topicTreeList.newSelection();
    }
  }

  private void populateMarkGamesCodeCombo(){
    markGamesCodeCombo.removeAllItems();
    ActionListener ala[] = markGamesCodeCombo.getActionListeners();
    if(ala.length > 0){
        markGamesCodeCombo.removeActionListener(markGamesCodeCombo.getActionListeners()[0]);
    }
    markGamesCodeCombo.addItem("Select mark games sequence");
    topicTree mgct = new topicTree();
    mgct.publicname=publicMarkGamesCoursesLib[0];
    mgct.setup(new String[]{publicMarkGamesCoursesLib[0]},false,db.TEXT,true, "");
      jnode sequencesNode = mgct.find(topicTree.MARKGAMESSEQUENCES);
      if(sequencesNode != null){
        jnode children[] = sequencesNode.getChildren(); 
        for(int i = 0; i < children.length; i++){
            markGamesCodeCombo.addItem(children[i].get().substring(0, children[i].get().indexOf("=")));
        }  
        markGamesCodeCombo.addActionListener (new ActionListener () {
            public void actionPerformed(ActionEvent e) {
                int h;
                if((h=markGamesCodeCombo.getSelectedIndex())>0){
                    h--;
                    topicTree mgct = new topicTree();
                    mgct.setup(new String[]{publicMarkGamesCoursesLib[0]},false,db.TEXT,true, "");
                    jnode sequencesNode = mgct.find(topicTree.MARKGAMESSEQUENCES);
                    if(sequencesNode != null){
                        jnode children[] = sequencesNode.getChildren(); 
                        String seq  = children[h].get();
                        String codes[] = u.splitString(seq.substring(seq.indexOf("=")+1), ",");
                        TreePath tps[] = topicTreeList.getSelectionPaths();
                        if(tps!=null && tps.length>1){
                            String wordDBName = "publictopics";
                            int index = 0;
                            for(int i = 0; i < tps.length; i++){
                                jnode jn = ((jnode)tps[i].getLastPathComponent());
                                if(!jn.isLeaf())continue;
                                String ts = jn.get();
                                ts = ts.substring(ts.indexOf(topicTree.ISTOPIC)+1);
                                saveTree1 trold = (saveTree1)db.find(wordDBName,ts,db.TOPIC);
                                int topicNoteIndex = -1;
                                int lastIndex = -1;
                                
                                if(trold != null){
                                    boolean found = false;
                                    for(int j = trold.curr.names.length-1; j >= 0; j--){
                                        if(trold.curr.names[j].startsWith("\\")){
                                            topicNoteIndex = j;
                                        }
                                        else if (!trold.curr.names[j].trim().equals("")){
                                            if(lastIndex < 0)
                                                lastIndex = j;
                                        }
                                        if(trold.curr.names[j].startsWith(topic.types[topic.MARKGAMESCODE])){
                                            trold.curr.names[j] = topic.types[topic.MARKGAMESCODE] + codes[index++ % codes.length];
                                            db.update(wordDBName,ts,trold.curr,db.TOPIC); 
                                            found = true;
                                            break;
                                        }
                                    }      
                                    if(!found){
                                        int insert = topicNoteIndex>=0?topicNoteIndex:lastIndex+1;
                                        trold.curr.names = u.addString(trold.curr.names, topic.types[topic.MARKGAMESCODE] + codes[index++ % codes.length], insert);
                                        trold.curr.levels = u.addByte(trold.curr.levels, (byte)1, insert);
                                        db.update(wordDBName,ts,trold.curr,db.TOPIC); 
                                    }
                                }

                            }
                            topicTreeList.clearSelection();
                            topicTreeList.setSelectionPath(tps[tps.length-1]);
                        }                          
                        int k = 0;
                    }      
                    int f = 0;
                }
            }
        });        
      }

  }  
  

  
  
  /**
   * Used when updating - Adds the scrollable topic tree on left of screen.
   * @param topicView Topic to be added
   */
  public void addTopic(topic topicView) {
    spokenWord.flushspeaker(true);
    if(topic.currpic != null) {                      // v5 start rb 7/3/08
        topic.currpic.stop();
        topic.currpic = null;
    }                                                // v5 end rb 7/3/08
    if(topic.currpic2 != null) {                      // v5 start rb 7/3/08
        topic.currpic2.stop();
        topic.currpic2 = null;
    }
    removeTopic();
    currTopicView = topicView;
    if ( (currTopicView.clipboardx = topicclipboard) != null) {
      currTopicView.clipboard = new boolean[topicclipboard.length];
      for (short i = 0; i < topicclipboard.length; ++i)
        currTopicView.clipboard[i] = true;
    }
    
    
    jnode ttnode = (jnode) topicTreeList.getSelectedNode();
    currTopicView.addWordExclusions(topicTreeList.getAncestors(ttnode, u.absoluteToRelative(publicTopicLib[0])));
    bevelPanel6.removeAll();
    bevelPanel6.add(u.uScrollPane(topicView), BorderLayout.CENTER);
    topicView.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseReleased(MouseEvent e) {
        spokenWord.flushspeaker(true);
        if(topic.currpic != null) {                      // v5 start rb 7/3/08
            topic.currpic.stop();
            topic.currpic = null;
        }                                                // v5 end rb 7/3/08
        if(topic.currpic2 != null) {                      // v5 start rb 7/3/08
            topic.currpic2.stop();
            topic.currpic2 = null;
        } 
        currTopicView.selection();
        lastSelect = currTopicView;
        if (e.getModifiers() == e.BUTTON3_MASK) {    // v5 start rb 7/3/08
          currTopicView.rightClickpic(e);
        }                                            // v5 end rb 7/3/08
      }
   });
//startPR2004-09-01^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      // enables exiting screen via the ESC key
      topicView.addKeyListener(new KeyAdapter() {
        public void keyPressed(KeyEvent e) {
          int code = e.getKeyCode();
          if(code == KeyEvent.VK_ESCAPE)
            sharkStartFrame.mainFrame.setupgames();
        }
      });
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    topicView.setForUpdate();
    if (keypad.keypadname != null) {
//startPR2007-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      keypad.usekeypad u = keypad.find(sharkStartFrame.mainFrame);
//      if (u != null) {
//        Font f = u.kk.font;
//        if (!f.getName().equalsIgnoreCase("wordshark")) {
      keypad.usekeypad use = keypad.find(sharkStartFrame.mainFrame);
      if (use != null) {
        Font f = use.kk.font;
        if (!u.isdefaultfont(f.getName())) {
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2007-11-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//          topicView.setFont(new Font(f.getName(), f.getStyle(),topicView.getFont().getSize()));
//          ((treepainter) topicView.getCellRenderer()).setFont(new Font(f.getName(), f.getStyle(), topicView.getFont().getSize()));
          topicView.setFont(f.deriveFont((float)topicView.getFont().getSize()));
          ((treepainter) topicView.getCellRenderer()).setFont(f.deriveFont((float)topicView.getFont().getSize()));
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        }
      }
    }
    bevelPanel2.validate();
  }

  public void setSpellChange(String s, boolean defaultop){
              if (defaultop) {
                 db.delete(optionsdb, "spellchange_", db.TEXT);
              }
              else   {
                if(db.query(optionsdb, "spellchange_", db.TEXT)<0)
                    db.update(optionsdb,"spellchange_",new String[]{s},db.TEXT);
              }
              spellchange.spellchangesetup();
              if(wordTree != null && currPlayTopic != null) {
                wordTree.reset();
                wordTree.redrawing = true;
                wordTree.setup(currPlayTopic,null);
                wordTree.redrawing = false;
              }
              repaint();
  }

  /**
   * Produces little topic at bottom right of screen.
   * @param topicView topic
   */
  public void addTopic2(topic topicView) {
    removeTopic2();
    removeExtra();
    topicView.setEditable(false);
    topicView.updating = false;
    bevelPanel3.add(u.uScrollPane(topicView), grid3);
    currTopicView2 = topicView;
    bevelPanel3.validate();
//startPR2004-09-01^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     // enables exiting screen via the ESC key
     topicView.addKeyListener(new KeyAdapter() {
       public void keyPressed(KeyEvent e) {
         int code = e.getKeyCode();
         if(code == KeyEvent.VK_ESCAPE)
           sharkStartFrame.mainFrame.setupgames();
       }
     });
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  }

  /**
   * Removes the topic options, games and courses from bevelPanel3
   */
  void removeExtra() {
//startPR2009-10-10^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    if (showingOptions || showingGames || showingCourses) {
    if (showingOptions || showingGames || showingCourses || showingMgHeadings || showingMgFormula || showingMgCourse) {
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         if(showingMgFormula){
              if(markgamesformulatree!=null){
                  markgamesformulatree.saveChanges();
                  markgameformulas = getMarkGameFormulas();
              }
              markgamesformulatree = null;
         }
         if(showingMgHeadings){
             if(markgamestree!=null){
                  markgamestree.saveChanges();
                  markgameheadings = getMarkGameHeadings();
             }
             markgamestree = null;
         }
         if(showingMgCourse){
  //           if(markgamescoursetree!=null){
  //                saveMarkCourseChanges();
  //           }
             markgamescoursetree = null;  
             bevelPanel6.removeAll();
         }
      bevelPanel3.removeAll();
//startPR2009-10-10^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      showingOptions = showingGames = showingCourses = false;
      showingOptions = showingGames = showingCourses = showingMgHeadings = showingMgFormula = showingMgCourse = false;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      bevelPanel3.validate();
      bevelPanel3.repaint();
    }
    jnode jn = null;
    if(btExcludeWordFromAPTest!=null){
        btExcludeWordFromAPTest.setEnabled(currTopicView!=null 
                && (jn=currTopicView.getSelectedNode())!=null 
                && topic.getTypePos(jn.get()) == -1
                && jn.getLevel() == 1    );        
    }
  }

   public void saveMarkCourseChanges() {

   //    u.copyfile(publicMarkGamesCoursesLib[0], publicPath);
   //    System.currentTimeMillis()
       jnode codeNodes = sharkStartFrame.mainFrame.markgamescoursetree.find(topicTree.MARKGAMESCODES);
       jnode jns[] = codeNodes.getChildren();
       int codecount = 0;
       for(int i = 0; i < jns.length; i++){
           if(!jns[i].get().trim().equals(""))codecount++;
       }
       codeNodes = sharkStartFrame.mainFrame.markgamescoursetree.find(topicTree.MARKGAMESSEQUENCES);
       jns = codeNodes.getChildren();
       int sequencecount = 0;
       for(int i = 0; i < jns.length; i++){
           if(!jns[i].get().trim().equals(""))sequencecount++;
       }
       if(codecount>0 && sequencecount>0){
           
           
           
           
          boolean done =  markgamescoursetree.saveChanges();
          if(done){
                          Calendar cal = new GregorianCalendar();
            String fname = "publicmarkgamescoursesBackup"+cal.get(Calendar.YEAR)+"-"+String.valueOf(cal.get(Calendar.MONTH)+1)
                     +"-"+zeroLeftFill(cal.get(Calendar.DAY_OF_MONTH), 2)+"_"+zeroLeftFill(cal.get(Calendar.HOUR_OF_DAY), 2)+
                    "h-"+zeroLeftFill(cal.get(Calendar.MINUTE), 2)+"m"+"__"+String.valueOf(System.currentTimeMillis())+".sha";
            String sFolderPath = sharkStartFrame.sharedPathplus+"publicmarkgamescoursesBackups";
            java.io.File f = new java.io.File(sFolderPath);
            if(!f.exists())f.mkdir();
            u.copyfile(new File(publicMarkGamesCoursesLib[0]+".sha"), new java.io.File(sFolderPath+shark.sep+fname));
          }
       }
       else u.okmess(shark.programName, "Problem saving Mark Games", sharkStartFrame.mainFrame);
   } 
    
   String zeroLeftFill(int i, int len)
   {   
       String s = String.valueOf(i);
       while(s.length()<len) s = "0"+s;
       return s;
   }   

  /**
   * Used when updating, removes scrollable topic list
   */
  public void removeTopic() {

    if (currTopicView != null) {
      topicclipboard = currTopicView.clipboardx;
      if (currTopicView.changed)
        currTopicView.save();
      currTopicView = null;
      
    }
    removeExtra();
      bevelPanel6.removeAll();

      bevelPanel6.validate();
      bevelPanel6.repaint();
    
    
    
  }

  /**
   * Removes contents of bevelPanel3
   */
  public void removeTopic2() {
    if (currTopicView2 != null) {
      bevelPanel3.removeAll();
      currTopicView2 = null;
      bevelPanel3.validate();
      bevelPanel3.repaint();
    }
  }

  /**
   *
   */
  public void addGameTree() {
    if (!showingGames) {
      removeTopic2();
      removeExtra();
      bevelPanel3.add(u.uScrollPane(publicGameTree), grid3);
      bevelPanel3.validate();
      showingGames = true;
    }
  }
  
  public void addMarkGameCourses(String courseName) {
      bevelPanel6.removeAll();
      markgamescoursetree = new topicTree();
      markgamescoursetree.publicname=publicMarkGamesCoursesLib[0];
      markgamescoursetree.setup(new String[]{publicMarkGamesCoursesLib[0]},true,db.TEXT,true, "Right-click to specify mark games codes");
      jnode main = (jnode)((jnode)markgamescoursetree.root.getChildAt(0));
      jnode mainChildren[] = main.getChildren();
      
      /*
      for(int i = mainChildren.length-1; i >= 0; i--){
        if(mainChildren[i].get().trim()==""){
            mainChildren[i].removeFromParent();
            mainChildren = u.removeNode(mainChildren, i);
        }
      }
      markgamescoursetree.model.reload();
*/
      if(mainChildren.length < 2){
          jnode jnodesq = new jnode(topicTree.MARKGAMESSEQUENCES);
          jnode jnode2sq = new jnode("example_sequence=");
          jnodesq.addChild(jnode2sq);           
          jnode jn = new jnode(topicTree.MARKGAMESCODES);
 //         main.addChild(jn);  
          jnode jnode2 = new jnode("example_code=");
          jn.addChild(jnode2); 
          markgamescoursetree.model.insertNodeInto(jnodesq, main, 0);
          markgamescoursetree.model.insertNodeInto(jn, main, 0);
          markgamescoursetree.model.reload();           
      }
  
      showingMgCourse = true;
      jnode j = markgamescoursetree.find(topicTree.MARKGAMESCODES);
      if(j != null){
        jnode selpath = (jnode) (j).getLastChild();
        markgamescoursetree.setSelectionPath(new TreePath(selpath.getPath()));
      }
      bevelPanel6.add(u.uScrollPane(markgamescoursetree), BorderLayout.CENTER);
 //     markgamescoursetree.expandAll();
      bevelPanel6.validate();
      bevelPanel6.repaint();      
  }

 public void addGameAndFormulaTree(int type) {
  //   if(!showingMgFormula) {
        removeTopic2();
        removeExtra();
// i == MARKGAMES || i == MARKGAMES2|| i == MARKGAMESCODE
     JTabbedPane jtp = new JTabbedPane();
     JPanel pnGames = new JPanel(new GridBagLayout());
     JPanel pnFormula = new JPanel(new GridBagLayout());
      pnGames.add(u.uScrollPane(publicGameTree),grid3);
      markgamesformulatree =  new topicTree();
      markgamesformulatree.publicname=publicMarkGamesCoursesLib[0];
      markgamesformulatree.setup(new String[]{publicMarkGamesCoursesLib[0]},true,db.TEXT,true, "Right-click to select mark games codes");
      jnode codesNode = markgamesformulatree.find(topicTree.MARKGAMESCODES);
      markgamesformulatree.model.setRoot(codesNode    );
      markgamesformulatree.expandPath(new TreePath( ( (jnode) ( (jnode) markgamesformulatree.root.getFirstChild()).getFirstChild()).getPath()));
      markgamesformulatree.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseReleased(MouseEvent e) {
          if (e.getModifiers() == e.BUTTON3_MASK) {
            currTopicView.addMGFormula();
          }
          lastSelectForCopy = markgamesformulatree;
        }
      });
      pnFormula.add(u.uScrollPane(markgamesformulatree), grid3);
      if(type == topic.MARKGAMES){
          jtp.add("Games Tree", pnGames);
      }
      else if (type == topic.MARKGAMESCODE){
         jtp.add("Mark Games Codes", pnFormula);       
      }
      

      bevelPanel3.add(jtp,grid3);
      bevelPanel3.validate();
      showingMgFormula = true;
      jnode jn;
      if(type == topic.MARKGAMESCODE){
        String selected = currTopicView.getSelectedNode().get();
        String s = selected.substring(selected.indexOf(":")+1);
        jnode codeitems[] = codesNode.getChildren();
        String codeitemstr[] = new String[0];
        for(int i = 0; i < codeitems.length; i++){
            if(codeitems[i].get().trim()!="")
              codeitemstr = u.addString(codeitemstr, codeitems[i].get().substring(0, codeitems[i].get().indexOf("=")));
        }
        if(!s.trim().equals("") && u.findString(codeitemstr, s)<0){
            String mess[] = new String[]{"Mark games code does not exist\n\n"};
            u.okmess(shark.programName, mess, this);
        }        
      }


      /*
      
      
          jnode gnode =  sharkStartFrame.mainFrame.markgamesformulatree.getSelectedNode();
    if(gnode==null)return;
    String name = gnode.get();
    if(name == null) return;
    changed = true;
    jnode sel = getSelectedNode();
    if(name != null && sel != null && gnode.getLevel()==3) {
        sel.setUserObject(types[MARKGAMESCODE]+name.substring(0, name.indexOf("=")));
        model.nodeChanged(sel);        
        this.expandPath(new TreePath(sel.getPath()));
   }
      
      
      String nongames[] = new String[]{};
      if((jn = (jnode)markgamesformulatree.root.getChildAt(0))!=null){
           for(int i = 0; i < jn.getChildCount(); i++){
               jnode jn2 = (jnode)jn.getChildAt(i);
               if(!jn2.get().trim().equals("")){
                   jnode games[] = jn2.getChildren();
                   for(int k = 0; k < games.length; k++){
                       String gamename = games[k].get();
                       if(!gamename.trim().equals(""))
                            if(publicGameTree.findTopic(gamename)==null)
                                if(u.findString(nongames, gamename)<0)
                                    nongames = u.addString(nongames, gamename);
                   }
               }
           }
        }
      if(nongames.length>0){
          String mess[] = new String[]{"The following entries appear in the mark games formulae tree,\nbut are not existing games:\n\n"};
          mess = u.addString(mess, nongames);
          u.okmess(shark.programName, mess, this);
      }
      */
      int h = 0;
    // }
  }

  /**
   *
   */
  public void addCourseList() {
    if (!showingCourses) {
      removeTopic2();
      removeExtra();
      bevelPanel3.add(u.uScrollPane(publicCourseList), grid3);
      bevelPanel3.validate();
      showingCourses = true;
    }
  }
//startPR2009-10-10^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  public void addMGHeadings() {
    if (!showingMgHeadings) {
      removeTopic2();
      removeExtra();
//      gettopictreelist();
      markgamestree =  new topicTree();
      markgamestree.publicname=publicMarkGamesLib;
      markgamestree.setup(new String[]{publicMarkGamesLib},true,db.TEXT,true, "Right-click to select mark games heading");
      markgamestree.expandPath(new TreePath( ( (jnode) ( (jnode) markgamestree.root.getFirstChild()).getFirstChild()).getPath()));
      markgamestree.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseReleased(MouseEvent e) {
          if (e.getModifiers() == e.BUTTON3_MASK) {
            currTopicView.addMGHeading();
          }
          lastSelectForCopy = markgamesformulatree;
        }
      });
      showingMgHeadings = true;
      bevelPanel3.add(u.uScrollPane(markgamestree), grid3);
      bevelPanel3.validate();
    }
  }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR

  /**
   *
   */
  public void addTopicOptions() {
    if (!showingOptions) {
      removeTopic2();
      removeExtra();
      bevelPanel3.add(u.uScrollPane(topicOptions), grid3);
      bevelPanel3.validate();
      showingOptions = true;
    }
    topicOptions.clearSelection();
  }

  /**
   * @param e ActionEvent
   */
  void topicOptions_actionPerformed(ActionEvent e) {
    currTopicView.plugTopic( (String) topicOptions.getSelectedValue());
  }

  /**
   * @param e ActionEvent
   */
  void cut_actionPerformed(ActionEvent e) {
    if (topicTreeList.onlyOneType == db.IMAGE) {
      if (currPicture != null)
        endPicture();
      currPicture = null;
      picclip = topicTreeList.picClip(true);
      return;
    }
     if(lastSelectForCopy == markgamesformulatree){
         markgamesformulatree.cut();
     }
     else if(lastSelectForCopy == markgamestree){
         markgamestree.cut();
     }
     else if(bevelPanel2.getComponentCount() > 0 && lastSelect == currTopicView) {
        currTopicView.cut();
      }

    else if (lastSelect == topicTreeList) {
      topicTreeList.cut();
      copyfrommiddle = false;
    }
  }

  /**
   * @param e Action Event
   */
  void copy_actionPerformed(ActionEvent e) {
    if (topicTreeList.onlyOneType == db.IMAGE) {
      if (currPicture != null)
        currPicture.save();
      picclip = topicTreeList.picClip(false);
      return;
    }
     if(lastSelectForCopy == markgamesformulatree){
         markgamesformulatree.copy();
     }
     else if(lastSelectForCopy == markgamestree){
         markgamestree.copy();
     }
     else if(bevelPanel2.getComponentCount() > 0 && lastSelect == currTopicView) {
        currTopicView.copy();
     }

    else if (lastSelectForCopy == topicTreeList) {
      topicTreeList.copy();
      copyfrommiddle = false;
    }
    else if (lastSelectForCopy == topicList) {
      topicList.copy();
      copyfrommiddle = true;
    }
  }
  void find_actionPerformed(ActionEvent e) {
    if (topicTreeList.onlyOneType == db.IMAGE) {
      if (currPicture != null)
        currPicture.save();
    }
    if (lastSelect==null || lastSelect == topicTreeList) {
      topicTreeList.find();
    }
    else if (lastSelect == currTopicView) {
      currTopicView.find();
    }
  }
  void findnext_actionPerformed(ActionEvent e) {
    if (topicTreeList.onlyOneType == db.IMAGE) {
      if (currPicture != null)
        currPicture.save();
    }
    if (lastSelect == null || lastSelect == topicTreeList) {
      topicTreeList.findnext();
    }
    else if (lastSelect == currTopicView) {
      currTopicView.findnext();
    }
  }
//startPR2009-10-10^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  String[][] getMarkGameHeadings(){
    topicTree markgamestree2 =  new topicTree();
    markgamestree2.publicname=publicMarkGamesLib;
    markgamestree2.isAbsoluteDb = new File(markgamestree2.publicname).isAbsolute();
    markgamestree2.setup(new String[]{publicMarkGamesLib},false,db.TEXT,false, "");
    jnode rootnode = (jnode)markgamestree2.root.getChildAt(0);
    int p = rootnode.getChildCount();
    int i = 0;
    for (i = 0; i < p; i++) {
      if(((jnode)rootnode.getChildAt(i)).get().indexOf("default")>=0){
        break;
      }
    }
    int k = rootnode.getChildAt(i).getChildCount();
    String ss[][] = new String[p][k+1];
    jnode jn;
    loop1:for(i = 0; i < p; i++){
      jn = ((jnode)rootnode.getChildAt(i));
      String s = jn.get();
      for(int n = 0; n < k; n++){
        if(jn.getChildCount()!=k)continue loop1;
        String s2 = ((jnode)jn.getChildAt(n)).get();
        if(n==0){
          ss[i][n]=s;
        }
        ss[i][n+1]= s2.substring(s2.indexOf("=")+1);
      }
    }
    return ss;
  }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR

  public String[][] getMarkGameFormulas(){
    topicTree markgamesformulatree2 =  new topicTree();
    markgamesformulatree2.publicname=sharkStartFrame.publicMarkGamesFormulaLib;
    markgamesformulatree2.isAbsoluteDb = new java.io.File(markgamesformulatree2.publicname).isAbsolute();
    markgamesformulatree2.setup(new String[]{sharkStartFrame.publicMarkGamesFormulaLib},false,db.TEXT,false, "");
    jnode rootnode = (jnode)markgamesformulatree2.root.getChildAt(0);
    int p = rootnode.getChildCount();
    String retf[][] = new String[p][];
    int i = 0;
    for (i = 0; i < p; i++) {
       jnode jnparent = jnparent=(jnode)rootnode.getChildAt(i);
       String ss[] = new String[]{jnparent.get()};
       for (int k = 0; k < jnparent.getChildCount(); k++) {
           ss = u.addString(ss,((jnode)jnparent.getChildAt(k)).get());
       }
       retf[i] = ss;
    }
    return retf;
  }


  /**
   * @param e Action Event
   */
  void paste_actionPerformed(ActionEvent e) {
    if (topicTreeList.onlyOneType == db.IMAGE) { //If 1 - contains images
      if (currPicture != null)
        endPicture(); //If 1.1 - There is a picture
      if (picclip != null) { //If 1.2 - Array of images not empty
        jnode pos = topicTreeList.getSelectedNode();
        if (pos != null) { //If 1.2.1 - There is a node selected
          jnode dbase = (jnode) pos.getParent();
          jnode root = topicTreeList.root;
          boolean first = true;
          if (pos != root && dbase != root) //If 1.2.1.1 - selected node is not the root node AND it's parent is not the root node
            for (short i = 0; i < picclip.length; ++i) //For 1.2.1.1.1
              if (picclip[i] != null) { //If 1.2.1.1.1.1
                String oldname = picclip[i].name;
                picclip[i].database = dbase.get();
                while (db.query(picclip[i].database, picclip[i].name, db.IMAGE) >=
                       0) {
                  picclip[i].name = picclip[i].name + "x"; //While 1.2.1.1.1.2
                }
                picclip[i].changed = true;
                picclip[i].hadrun = true;
                picclip[i].save();
                pos = topicTreeList.addChild(dbase, picclip[i].name, pos);
                if (first) { //If 1.2.1.1.1.3
                  topicTreeList.setSelectionPath(new TreePath(pos.getPath()));
                  newPicture(picclip[i].database, picclip[i].name);
                  first = false;
                }
                picclip[i].name = oldname;
              }
        }
      }
      else
        topicTreeList.paste();
      return;
    }
     if(lastSelectForCopy == markgamesformulatree){
         markgamesformulatree.paste();
     }
     else if(lastSelectForCopy == markgamestree){
         markgamestree.paste();
     }
     else if(bevelPanel2.getComponentCount() > 0 && lastSelect == currTopicView) {
        currTopicView.paste();
     }

    else if (lastSelect == topicTreeList) { //Else if 2
      if (copyfrommiddle)
        topicTreeList.clipboard = topicList.clipboard; //If 2.1
      topicTreeList.paste();
    }
  }

  /**
   * @param e Action Event
   */
  void pasteimage_actionPerformed(ActionEvent e) {
    if (topicTreeList.onlyOneType == db.IMAGE && currPicture != null
        && picclip != null) {
      for (short i = 0; i < picclip.length; ++i) {
        if (picclip[i] != null) {
          currPicture.importimage(picclip[i].name, picclip[i].database);
        }
      }
      return;
    }
  }

  /**
   * Saves recorded words, pictures and topics
   * @param e Action Event
   */
  void save_actionPerformed(ActionEvent e) {
    if (recording != null) {
      recording.save();
      return;
    }
    if (currPicture != null)
      currPicture.save();
    if (isAncestorOf(topicTreeList))
      topicTreeList.saveChanges();
    if (topicList != null) {
      if (updatingGames)
        topicList.setup(this.searchListGame(currStudent), false, db.GAME, false,
                        "For reference (right-click)");
      else
        topicList.setup(topicList.dblist, false, (byte) 0, false,
                        "Public topic lists");
    }
  }

  /**
   * @param e Window event
   */
  void this_windowClosing(WindowEvent e) {
    finalize();
  }

  /**
   * @param e Action event
   */
  void undo_actionPerformed(ActionEvent e) {
    if (bevelPanel2.getComponentCount() > 0 && lastSelect == currTopicView) {
      currTopicView.undo();
    }
    else if (lastSelect == topicTreeList) {
      topicTreeList.undo();
    }
  }

  /**
   */
  public void savesplitwords() {
//    if (currStudent >= 0 && (studentList[currStudent].administrator && !studentList[currStudent].teacher)
//        && wordTree.splitchanged
//        && !wassuperlist
//startPR2004-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//        && u.yesnomess(u.gettext("splitsave","heading"),u.gettext("splitsave","text"))) {
//        && u.yesnomess(u.gettext("splitsave","heading"),u.gettext("splitsave","text"), sharkStartFrame.mainFrame)) {
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      currPlayTopic.savesplitwords(optionsdb, currPlayTopic.splitwords);
//    }
    wordTree.splitchanged = false;
  }

  /**
   *
   */
  synchronized void closeprev(boolean flush) {
      if(flush)
        spokenWord.flushspeaker(true);
    if(topic.currpic != null) {                      // v5 start rb 7/3/08
        topic.currpic.stop();
        topic.currpic = null;
    }                                                // v5 end rb 7/3/08
    if(topic.currpic2 != null) {                      // v5 start rb 7/3/08
        topic.currpic2.stop();
        topic.currpic2 = null;
    }   
              if(currPhoto!=null){
                  currPhoto.stop();
                  currPhoto = null;
              }
    if (showingPictures && currPicture != null) {
      endPicture();
    }
    keypad.deactivate(mainFrame);
    if (showteachingnotes != null) {
      showteachingnotes.setVisible(false);
      showteachingnotes.dispose();
      showteachingnotes = null;
    }
    savesplitwords();
    if (updatingGames){
//      gametitle = null; // force reset game icon screen
      resetGamesScreen = true;
    }
    if (shark.specialfunc) {
      save.setEnabled(false);
      manBar1.remove(men1);
      manBar1.remove(mplay);
    }
//    if (easywordlistpanel != null) {
//      easywordlistpanel.terminate();
//      easywordlistpanel = null;
//      db.closeAll();
//    }
//startPR2008-06-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    if (wordTree != null && wordTree.currpic != null)
//      wordTree.currpic.stop();
    wordlist.removeCurrPic();
//endPRimagesandvids^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    stopGamepanel();
    if (sharkPanel != null) {
      removeKeyListener(signonkey);
      signonkey = null;
      sharkPanel.stop();
      bevelPanel1.remove(sharkPanel);
      sharkPanel = null;
      if(Backup_base.bkupDone != null){
         Backup_base.bkupDone.dispose();
      }
    }
    if (picShow != null) {
      picShow.stop();
      bevelPanel1.remove(picShow);
      picShow = null;
      db.closeAll();
    }
    if (isAncestorOf(markgamestree)) {
      markgamestree.saveChanges();
      markgameheadings = getMarkGameHeadings();
      markgamestree = null;
    }
    if (isAncestorOf(markgamesformulatree)) {
      markgamesformulatree.saveChanges();
      markgameformulas = getMarkGameFormulas();
      markgamesformulatree = null;
    }
    if (isAncestorOf(topicTreeList)) {
      if (!showingPictures && !updatingGames && !updatingText) {
        updatepath = topicTreeList.getCurrentTopicPath();
        refpath = topicList.getCurrentTopicPath();
      }
//      removeTopic();
      topicTreeList.saveChanges();
      db.closeAll();
      topicTreeList = null;
    }
    if (recording != null) {
      spokenWord.recording = false; // just in case
      db.closeAll();
      recording = null;
      spokenWord.currrecord = null;
    }
    if (playingGames) {
      playingGames = false;
      currPlayTopic = null;
      bevelPanel2.removeAll();
      wordTree.model.removeAllElements(); ;
    }
    picclip = null;
    bevelPanel1.removeAll();
    bevelPanel1.validate();
    picControl = null;
    picChoice = null;
    playingGames = false;
    updatingGames = false;
    updatingTopics = false;
    updatingText = false;
    showingPictures = false;
    topicList = null;
    lastrect = -1;
  }

//startPR2010-05-04^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  boolean signon() {
      return signon(null);
  }

  boolean signon(String name) {
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  /**
   * @return true
   */
    closeprev(true);
    setupsharkpanel();
    clearStudentMenu();
    validate();
//startPR2010-05-04^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    student.signOn(sharkPanel);
    buildHelpMenu(true);
    student.signOn(sharkPanel, name);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    return true;
  }
  
  void ImageInfo_actionPerformed(ActionEvent e) {
    setTitle("Image information");
    startimage(u.absoluteToRelative(sharkStartFrame.publicTopicLib[0]), 0);
  }  

  /**
   * @param e Action event
   */
  void record_actionPerformed(ActionEvent e) {
    setTitle(recwordstitle);
    startrecord(u.absoluteToRelative(sharkStartFrame.publicTopicLib[0]), 0);
  }

  void recordfl_actionPerformed(ActionEvent e) {
    setTitle(recwordstitle);
    startrecord(u.absoluteToRelative(sharkStartFrame.publicTopicLib[0]), 6);
  }
  
  
  synchronized void startimage(String db1, int defs) {
    closeprev(true);
    if (shark.specialfunc)
      save.setEnabled(false);
    ImageInfo imaging = new ImageInfo(db1, (byte) defs);
    grid1.gridwidth = 1;
    grid1.gridx = grid1.gridy = 0;
    grid1.weightx = 1;
    bevelPanel1.add(imaging, grid1);
    bevelPanel1.validate();
    bevelPanel1.requestFocus();
  }    
  
  /**
   * Recording words
   * @param db1 String indicating a database
   * @param defs Defines the settings to be used
   */
  synchronized void startrecord(String db1, int defs) {
    closeprev(true);
    if (shark.specialfunc)
      save.setEnabled(false);
    recording = new recordWords(db1, (byte) defs,true);
    grid1.gridwidth = 1;
    grid1.gridx = grid1.gridy = 0;
    grid1.weightx = 1;
    bevelPanel1.add(recording, grid1);
    bevelPanel1.validate();
    bevelPanel1.requestFocus();
  }

  /**
   * @param e Action event
   */
  void choosesprite_actionPerformed(ActionEvent e) {
    if (currStudent >= 0) {
//startPR2004-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      chooseSprite_base cs = new chooseSprite_base();
      chooseSprite_base cs = new chooseSprite_base(sharkStartFrame.mainFrame);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    }
  }

  /**
   * @param e Action event
   */
  void menuItem1_actionPerformed(ActionEvent e) {
//startPR2010-05-04^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    savesplitwords();
//    signon();
     dosignon(null);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR

  }
//startPR2010-05-04^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  public void dosignon(String name){
     savesplitwords();
     signon(name);
  }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR


  /**
   * @param e Action event
   */
  void signof_actionPerformed(ActionEvent e) {
    if (studentList != null && currStudent >= 0) {
      savesplitwords();
      clearStudentMenu();
      studentList[currStudent].signoff(true);
    }
  }

  /**
   * @param e Action event
   */
  void changepassword_actionPerformed(ActionEvent e) {
    student copystu = student.findStudent(studentList[currStudent].name);
    if (copystu != null){
//startPR2004-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//          copystu.changePassword();
      copystu.changePassword(sharkStartFrame.mainFrame);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    }
    studentList[currStudent].password = copystu.password;
    studentList[currStudent].passwordhint = copystu.passwordhint;
  }
  
 public void setCourseMenu(){
    cl = db.listsort(u.absoluteToRelative(sharkStartFrame.publicTopicLib[0]), db.TOPICTREE);
    for(int i=0;i<cl.length;++i) {    // start rb 26-11-07
     if(cl[i].startsWith("  ") ||  !u.displayCourse(cl[i])) {
        cl = u.removeString(cl,i);
        --i;
     }
    }                                 // end rb 26-11-07
    mcourses = new JCheckBoxMenuItem[cl.length];
    restrictcourses.removeAll();
    for (short i = 0; i < cl.length; ++i) {
      restrictcourses.add(mcourses[i] = u.CheckBoxMenuItem2(cl[i]));
      mcourses[i].setState(u.findString(courses, cl[i]) < 0);
      mcourses[i].addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          courses = new String[0];
          String stucourses[] = new String[0];
          boolean active = false;
          for (short i = 0; i < mcourses.length; ++i) {
            if (!mcourses[i].getState()){
              courses = u.addString(courses, mcourses[i].getText());
              if(mcourses[i].isEnabled())
                  stucourses = u.addString(stucourses, mcourses[i].getText());
            }
            if(mcourses[i].getState() && mcourses[i].isEnabled())active = true;
          }
          if(!active){
              ((JCheckBoxMenuItem)e.getSource()).setSelected(true);
              return;
          }
          student.setOption("s_courses", u.combineString(stucourses));
          setupgames();
        }
      });

    }
 }


  public void setupgames() {
      setupgames(false);
  }
  /**
   *
   */
  public void setupgames(boolean samelist) {
      // needed otherwise gameflags hadn't built properly and some games were initially missing
    while(!donegameflags){
        u.pause(50);
    }
    int i, j, k;
    usingsuperlist = null;
    specialsuperlist = false;
    String projectlist[] = null;
    jnode node, parent;
    if(!samelist)
        closeprev(true);
    if(!playingGames) {    // start rb 27/10/06
       studentList[currStudent].doupdates(true,false);
       clearStudentMenu();
       setStudentMenu();
    }                     // end rb 27/10/06
    gettopiclist();
//startPR2010-05-24^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    getcourselist();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    topicList.onlyOneDatabase = "*";
//startPR2010-05-24^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    courseList.onlyOneDatabase = "*";
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    if (currStudent >= 0) //If 1
      studentList[overrideStudent()].gameicons = gameicons; //-There is a current student
    courselist = null;
//startPR2008-09-21^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    boolean overrideProgram = false;   //3333
//endPRv404^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    // because in v4 once you'd been set work you always had 'Which teachers work'
      
//      setteachers();
//startPR2007-12-10^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    boolean restart;
    loop1:do{
      restart = false;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    if (!studentList[currStudent].administrator
//startPR2008-08-29^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        && !studentList[currStudent].teacher
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        && !override() //If 3
        &&
//startPR2008-09-21^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//        !studentList[currStudent].programOverride &&  //2222
        !overrideProgram &&   //3333
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        (projectlist = db.list(studentList[currStudent].name, db.PROGRAM)).length >
        0) {
//startPR2008-09-29^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      String onlyone = topicList.onlyOneDatabase;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      // start rb 27/10/06 -------------------------------------------------------------------
      student stu = studentList[currStudent];
      if(stu.doupdates(true,false)) {  // just in case changed 'workforteacher'
            setStudentMenu();
      }
      // end rb 23/10/06 -------------------------------------------------------------------------
      topicList.root.setIcon(jnode.ROOTTOPICTREE);
      String saveadli[] = student.admintopiclist; // we don't want admin lists
      student.admintopiclist = new String[0];
      if(Demo_base.isDemo){
          String publicT[] = new String[]{shark.publicPath+shark.sep+ u.absoluteToRelative(sharkStartFrame.publicTopicLib[0])};
          topicList.setup(publicT, false, db.TOPICTREE, true, "");
      }
      else
        topicList.setup(new String[0], false, db.TOPICTREE, true, "");
//      make3cols(true);
//startPR2010-05-24^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
 //     courseList.setup(new String[0], false, db.TOPICTREE, true, "");
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      student.admintopiclist = saveadli;
      topicList.onlyOneDatabase = null;
         // start rb 23/10/06 -------------------------------------------------------------------
      program.saveprogram sp[] = new program.saveprogram[projectlist.length];
      String newteachers[] = new String[0];
//startPR2008-09-21^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      String gamech[] = null;
      String gamech5[] = null;
      String coursech[] = null;
      String coursech5[] = null;
      String topicch[] = null;
      String topicch5[] = null;
      String movech5[] = null;
      String headingch[] = null;
      String defaultcourse = null;
      String defaultcourse5 = null;
      String currAdminListText = null;
      String currAdminListText5 = null;
      String oldAdminListText[] = null;
      topicTree topics = null;
//      gamestoplay games = null;
      topicTree topics5 = null;
      gamestoplay games5 = null;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      for (i = 0; i < projectlist.length; ++i) { //for 3.1
        try {                          // start rb 6/2/08
          sp[i] = (program.saveprogram) db.find(studentList[
                                                currStudent].name, projectlist[i],
                                                db.PROGRAM);
        }
        catch (ClassCastException e) {
          sp[i] = null;
        }                              // end rb 6/2/08
//startPR2007-12-10^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        if (sp[i] != null) {
//startPR2008-09-21^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          if(sp[i].version == null || !sp[i].version.equals(shark.versionNoDetailed)){
            if(topics==null){
              String savecourses[] = sharkStartFrame.courses;
              sharkStartFrame.courses = new String[0]; // get full topic tree
              topics = new topicTree();
              topics.onlyOneDatabase = "*";
              topics.root.setIcon(jnode.ROOTTOPICTREE);
              topics.publicname = sharkStartFrame.publicV4TopicsLib;
              topics.isAbsoluteDb = new File(sharkStartFrame.publicV4TopicsLib).isAbsolute();
              topics.setup(new String[]{sharkStartFrame.publicV4TopicsLib}, false, db.TOPICTREE, true, "topic tree");
              topics.setRootVisible(false);
              sharkStartFrame.courses = savecourses; // restore course removals
            }
            if(topics5==null){
              String savecourses[] = sharkStartFrame.courses;
              sharkStartFrame.courses = new String[0]; // get full topic tree
              topics5 = new topicTree();
              topics5.onlyOneDatabase = "*";
              topics5.root.setIcon(jnode.ROOTTOPICTREE);
              topics5.setup(sharkStartFrame.mainFrame.publicTopicLib, false, db.TOPICTREE, true, "topic tree");
              topics5.setRootVisible(false);
              sharkStartFrame.courses = savecourses; // restore course removals
            }
//            if(games==null){
//              games = new gamestoplay();
//              games.setup(new String[]{"v4publicgames"},
//                          true, true, "games", gamestoplay.categories);
//            }
            if(games5==null){
              games5 = new gamestoplay();
              games5.setup(sharkStartFrame.publicGameLib,
                          true, true, "games", gamestoplay.categories);
            }
            if(gamech==null)gamech = u.splitString(u.gettext("v4changes", "games"));
            if(gamech5==null)gamech5 = u.splitString(u.gettext("v5changes", "games"));
            String gs = u.gettext("v5changes", "gamesadd");
            String ga5[] = u.splitString(gs, "||");
            ArrayList gamechadd5 = new ArrayList();
            for(int n = 0; n < ga5.length; n++){
                gamechadd5.add(u.splitString(ga5[n]));
            }
            if(coursech==null)coursech = u.splitString(u.gettext("v4changes", "courses"));
            if(coursech5==null)coursech5 = u.splitString(u.gettext("v5changes", "courses"));
            if(topicch==null)topicch = u.splitString(u.gettext("v4changes", "topics"));
            if(topicch5==null)topicch5 = u.splitString(u.gettext("v5changes", "topics"));
            if(movech5==null)movech5 = u.splitString(u.gettext("v5changes", "move"));
            if(headingch==null)headingch = u.splitString(u.gettext("v4changes", "headings"));
            String hs = u.gettext("v5changes", "headings");
            String ha5[] = u.splitString(hs, "||");
            ArrayList headingch5 = new ArrayList();
            for(int n = 0; n < ha5.length; n++){
                headingch5.add(u.splitString(ha5[n]));
            }

            hs = u.gettext("v5changes", "headings_mult");
            ha5 = u.splitString(hs, "||");
            ArrayList headingmultch5 = new ArrayList();
            for(int n = 0; n < ha5.length; n++){
                headingmultch5.add(u.splitString(ha5[n]));
            }


            if(defaultcourse==null)defaultcourse = (db.listsort(u.absoluteToRelative(sharkStartFrame.publicV4TopicsLib), db.TOPICTREE))[0];
            if(defaultcourse5==null)defaultcourse5 = (db.listsort(u.absoluteToRelative(sharkStartFrame.publicTopicLib[0]), db.TOPICTREE))[0];
            if(currAdminListText==null)currAdminListText = u.gettext("v4changes", "adminlists");
            if(currAdminListText5==null)currAdminListText5 = u.gettext("topics", "adminlists");
            // a list of texts in previous versions for the node that is the parent to
            // the administrator lists.
            if(oldAdminListText==null)oldAdminListText = u.splitString(u.gettext("v4changes", "adminlisttext"));
//            sp[i] = studentList[currStudent].fixsaveprogram4(true, topics, games,
            if((sp[i].version==null || sp[i].version.trim().equals("")) || sp[i].version.substring(0, 1).equals("3"))
                sp[i] = studentList[currStudent].fixsaveprogram4(true, topics,
                   projectlist[i], sp[i], coursech, headingch, topicch, gamech,
                   oldAdminListText, currAdminListText, defaultcourse);
            if((sp[i].version==null || sp[i].version.trim().equals("")) || sp[i].version.substring(0, 1).equals("4"))
                sp[i] = studentList[currStudent].fixsaveprogram5(true, topics5, games5,
                   projectlist[i], sp[i], coursech5, headingch5, headingmultch5, topicch, gamech5, gamechadd5, movech5,
                   currAdminListText5, defaultcourse5);
         }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          Vector invalidTopicNames;
          privateListRecord plr = new privateListRecord();
          if ( (invalidTopicNames = plr.getInvalidTopics(sp[i])) != null) {
            sp[i] = plr.updateSaveProgram(studentList[currStudent].name, projectlist[i], sp[i], invalidTopicNames);
          }
        }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        if (sp[i] == null) { //If 3.1.1
          db.delete(studentList[currStudent].name, projectlist[i], db.PROGRAM);
          projectlist = u.removeString(projectlist, i);
          sp = new program.saveprogram[projectlist.length];
          i=-1;   // start again
        }
        else if((u.findString(student.adminlist, sp[i].teacher) < 0 &&
                u.findString(student.teacherlist, sp[i].teacher) < 0 &&
                u.findString(student.deletedadmins, sp[i].teacher) >= 0) ||
                !studentList[currStudent].isCurrentStuOf(sp[i].teacher))
        {
          db.delete(studentList[currStudent].name, projectlist[i], db.PROGRAM);
          projectlist = u.removeString(projectlist, i);
          sp = new program.saveprogram[projectlist.length];
          i=-1;   // start again
        }
        else {
          newteachers = u.addStringSort(newteachers, sp[i].teacher);
        }
      }
//startPR2007-12-10^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      if(projectlist.length<1){
        restart = true;
//startPR2008-09-29^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        topicList.onlyOneDatabase = onlyone;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        continue loop1;
      }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      if(!u.stringequal(newteachers,studentList[currStudent].teachers)) {
          studentList[currStudent].teachers = newteachers;
          setteachers();
      }
//startPR2008-09-22^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      if(sharkStartFrame.OTHERWORKFORTEACHER.equals(studentList[currStudent].workforteacher)){
        restart = true;
        overrideProgram = true;
//startPR2008-09-29^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        topicList.onlyOneDatabase = onlyone;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        continue;
      }
      if(sp.length==0){
          restart = true;
          continue;
      }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      for (i = 0; i < sp.length; ++i) { //for 3.1
        if(!sp[i].teacher.equals(studentList[currStudent].workforteacher)) continue;
//startPR2008-09-21^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        if(u.findString(studentList[currStudent].programOverride, projectlist[i])>=0){    //3333
          u.okmess(u.gettext("stuolderversion", "heading"),      //3333
                   u.edit(u.gettext("stuolderversion", "message"), new String[]{stu.name, shark.programName, stu.name}),     //3333
                   this);   //3333
          overrideProgram = true;    //3333
          restart = true;     //3333
//startPR2008-09-29^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          topicList.onlyOneDatabase = onlyone;     //3333
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          continue loop1;    //3333
        }  //3333
//endPRv404^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        saveTree1.saveTree2 s[] = program.gettopic(sp[i]);
        boolean mixed[] = program.getmixed(sp[i]);           //  rb 16/3/08  mmmm
        String tp[] = program.gettopicpath(sp[i]);

        parent = topicList.addChild(topicList.root, projectlist[i]);
        parent.setIcon(jnode.PROJECT);
        
//startPR2007-12-10^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//        if (sp[i] != null) {
//          Vector invalidTopicNames;
//          privateListRecord plr = new privateListRecord();
//          if ((invalidTopicNames = plr.getInvalidTopics(sp[i])) != null) {
//            sp[i] = plr.updateSaveProgram(studentList[currStudent].name, projectlist[i], sp[i], invalidTopicNames);
//            if(sp[i] == null){
//              projectlist = null;
//              break;
//            }
//            else{
//              s = program.gettopic(sp[i]);
//              tp = program.gettopicpath(sp[i]);
//            }
//          }
//        }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        if (s == null || s.length == 0) { //If 3.1.2
          parent.dontforcerevision = true;
//startPR2008-02-03^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//          String[] str = db.list("publictopics", db.TOPICTREE);
          String[] str = db.listsort(u.absoluteToRelative(sharkStartFrame.publicTopicLib[0]), db.TOPICTREE);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          for (j = 0; j < str.length; ++j) //for 3.1.2.1
            str[j] = u.absoluteToRelative(sharkStartFrame.publicTopicLib[0]) + topicTree.SEPARATOR + str[j];
          for (j = 0; j < str.length; ++j) { //for 3.1.2.2
            k = parent.getChildCount(); // start pos
            node = topicList.addChild(parent, topicTree.ISPATH + str[j]);
            topicList.expandNode(node, new String[0]);
            for (; k < parent.getChildCount(); ++k) //For 3.1.2.2.1
              courselist = u.addString(courselist,
                                       ( (jnode) parent.getChildAt(k)).get());
          }
        }
        else { //Else 3.1.2
            
          for (j = 0; j < s.length; ++j) { //for 3.1.2.1
            saveTree1 st = new saveTree1(s[j]);
            jnode newnode = new jnode();
            topicList.model.insertNodeInto(newnode, parent, j);
            st.addToTree(topicList, newnode);
            if(u.findString(cl, newnode.get())>=0){
                newnode.dontcollapse =  true;
                topicList.expandPath(new TreePath(newnode.getPath()));
            }
            if(j<=mixed.length-1 && mixed[j]) {
                newnode.dontexpand =  true;
                topicList.setmixed(newnode,u.gettext("stulist_","mixedlist"));
            }   //  rb 16/3/08  mmmm
            String cc[] = u.splitString(tp[j], topicTree.CSEPARATOR);
            if (cc.length > 0)
              courselist = u.addString(courselist, cc[0]);
          }

            
  //        jnode jn2 = (jnode)((jnode)((jnode) topicList.getModel().getRoot()).getChildAt(0)).getChildAt(2);
  //        topicList.expandPath(  new TreePath(jn2 ));
  //        jn2.dontcollapse = true

        }
      }
      if (!wantplayprogram) { //If 3.2
          if(!shark.phonicshark)
             ownWordListEnabled(false, wantplayprogram);
//        easywordlist.setEnabled(false);
//        easyrecord.setEnabled(false);
      }
      wantplayprogram = true;
      topicList.stopatlevel2(u.gettext("stulist_","mixedlist"));
    }
    else { //Else 3
       if((studentList[currStudent].teachers != null &&
               !(studentList[currStudent].workforteacher.equals(sharkStartFrame.OTHERWORKFORTEACHER)) ||
               (projectlist==null || projectlist.length == 0)
               )){
           studentList[currStudent].teachers = null;
           setteachers();
       }
         // end rb 23/10/06 -------------------------------------------------------------------------
      playprogram = null;
      if (wantplayprogram) { //If 3.1
          if(!shark.phonicshark)
              ownWordListEnabled(true, wantplayprogram);
//        easywordlist.setEnabled(true);
//        easyrecord.setEnabled(true);
      }
      wantplayprogram = false;
      topicList.root.setIcon(jnode.ROOTTOPICTREE);
//startPR2010-05-24^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      topicList.setup(searchList2(overrideStudent()), false, db.TOPICTREE, true,
//                      "");
      topicList.setup(searchList2(overrideStudent()), false, db.TOPICTREE, true,
                      "");
      courseList.root.setIcon(jnode.ROOTTOPICTREE);
      courseList.setup(searchList2(overrideStudent()), false, db.TOPICTREE, true, topicTree.MODE_EXPAND_NONE,
                      "");
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      canshowmarkedgames = false;
    }
//startPR2007-12-10^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  }
  while(restart);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    if (!playingGames) { //If 4
//      clearStudentMenu();
//      setStudentMenu();
//    }
    gameTree.removeAllChildren(gameTree.root);
    wordTree.model.removeAllElements();

    grid1.weightx = 1;
    grid1.gridwidth = 1;
    grid1.gridx = -1;
    grid1.gridy = 0;
    stopGamepanel();
    bevelPanel5.removeAll();
//startPR2010-05-24^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    bevelPanel4.removeAll();

//    bevelPanel2.setMinimumSize(new Dimension(screenSize.width * 7 / 24,
//                                             screenSize.height));
//    bevelPanel2.setBorder(BorderFactory.createLineBorder(Color.orange, 2));
    bevelPanel2.setBorder(BorderFactory.createLineBorder(wordlistcolor, 2));
//    bevelPanel2.setMinimumSize(new Dimension(screenSize.width * 7 / 24,
//                                             screenSize.height));
//    bevelPanel2.setMinimumSize(new Dimension(screenSize.width * 7 / 24,
//                                             screenSize.height));
//    bevelPanel2.setPreferredSize(new Dimension(screenSize.width / 3,
//                                             screenSize.height));
//          bevelPanelWords.setPreferredSize(new Dimension(screenSize.width / 3,
 //                                                screenSize.height));
 //               bevelPanel4.setPreferredSize(new Dimension(screenSize.width / 3,
//                                                 screenSize.height));
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    displayhometitle();
    if (gameicons) { //If 5
//startPR2010-05-24^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      bevelPanel5.setPreferredSize(new Dimension(screenSize.width * 2 / 3,
//                                                 screenSize.height));
//      bevelPanel1.add(bevelPanel5, grid1);
//startPR2010-05-24^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
 //     grid1.weightx = 0;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      bevelPanel1.add(bevelPanel2, grid1);
      makegamesscreen();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2007-11-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      if(!Demo_base.isDemo)
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//        setTitle(hometitle2);
    }
    else { //Else 5
//      setTitle(hometitle1);
      gameicons = false;
//startPR2010-05-24^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      bevelPanel5.setPreferredSize(new Dimension(screenSize.width / 3,
//                                                 screenSize.height));
//      grid1.weightx = 1;
//      bevelPanel1.add(bevelPanel4, grid1);
//      grid1.weightx = 0;
//      bevelPanel1.add(bevelPanel2, grid1);
//      grid1.weightx = 1;
//      bevelPanel1.add(bevelPanel5, grid1);
      maketopicscreen();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    }
    forcechange = true;
//startPR2010-05-24^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    bevelPanelCourses.add(choosecourse, BorderLayout.NORTH);
//    JScrollPane clscroll = u.uScrollPane(courseList);
//    clscroll.setBorder(null);
//    bevelPanelCourses.add(courseList, grid1);
//    bevelPanel4.add(clicktoexpand, BorderLayout.NORTH);
//    bevelPanel4.add(clicktoexpand, grid1);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    clicktoexpand.setToolTipText(topicList.getToolTipText());
//    JScrollPane tlscroll = u.uScrollPane(topicList);
//    tlscroll.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
//      public void adjustmentValueChanged(AdjustmentEvent e) {
//          topicList.repaint();
//      }
//    });
//startPR2010-05-24^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    JScrollPane tlscroll = u.uScrollPane(topicList);
//    tlscroll.setBorder(null);
//    topicList.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
//      public void mouseMoved(MouseEvent e) {
//        TreePath tp = topicList.getPathForLocation(e.getX(), e.getY());
//        if (tp != null) {
//          Object oo = tp.getLastPathComponent();
//          if (oo instanceof jnode) {
//            jnode node = (jnode) oo;
//            if (node.type == jnode.COURSE) {
//              ( (treepainter) topicList.getCellRenderer()).setToolTipText(u.
//                  gettext("topictree", node.get()));
//            }
//            else
//               ( (treepainter) topicList.getCellRenderer()).setToolTipText(null);
//          }
//        }
//      }
//    });


//    bevelPanel4.add(tlscroll, BorderLayout.CENTER);

//    bevelPanel4.add(clicktoexpand, BorderLayout.NORTH);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    playingGames = true;
    returnnormal.setEnabled(false);
    if (studentList[overrideStudent()].currTopic != null && 
            !studentList[overrideStudent()].currTopic.trim().equals("") && 
            (node = topicList.setCurrentTopicPath(studentList[overrideStudent()].currTopic)) != null && 
            (node.getLevel() > 1)) {
//startPR2008-09-26^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      if(!selectmixedparent(node)){
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      topicList.setSelectionPath(new TreePath(node.getPath()));
      jnode sel = topicList.getSelectedNode();
      boolean got = false;
      String s;
      if (sel != null && !sel.isLeaf()) {
        if((wantplayprogram && !sel.isLeaf() && sel.getLevel() > 1 && forcerevision(sel))||
              (s = student.optionstring("super1")) != null && s.equals(sel.get()) ||
                (s = student.optionstring("super2")) != null && s.equals(sel.get())
                ){
          setupsuper2();
           got=true;              
        }
      }
      if(!got) {
        student.removeOption("super1");
        student.removeOption("super2");
        if (sel == null || sel.isLeaf()
            || (!wantplayprogram
                || !forcerevision(sel) && sel.getLevel() > 1)
            && !addsuperbutton(sel)
            || wantplayprogram && forcerevision(sel) && sel.getLevel() >= 2) {
          setupGametree(samelist);
        }
        else {
          gameicons = false;
          if(currPlayTopic!=null)
              switchdisplay();
//startPR2010-05-24^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//        bevelPanel5.removeAll();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        }
      }
//startPR2008-09-26^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    }
    else if (wantplayprogram && studentList[overrideStudent()].currTopic != null //Else if 6
             &&
             (node = topicList.setCurrentTopicPath(u.splitString(studentList[currStudent].
        currTopic, topicTree.CSEPARATOR)[0])) != null) {
//startPR2008-09-26^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      if(!selectmixedparent((jnode)node.getFirstLeaf())){
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      topicList.setSelectionPath(new TreePath(node.getFirstLeaf().getPath()));
      setupGametree();
//startPR2008-09-26^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    }
    else {    // student has not got a topic specified
      String defaultcourse = (String)db.find(optionsdb,"start_topic",db.TEXT);
//startPR2007-11-25^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      if(defaultcourse == null && studentList[currStudent].administrator && !studentList[currStudent].teacher) {
//         defaultcourse = getstartingcourse();
//      }
      if(defaultcourse == null){


        db.update(optionsdb, "start_topic", defaultcourse = mcourses[0].getText(), db.TEXT);
      }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      if(defaultcourse != null) {
         node = topicList.setCurrentTopicPath(defaultcourse);
         if(node != null) {
           topicList.setSelectionPath(new TreePath(node.getFirstLeaf().getPath()));
           setupGametree();
           studentList[currStudent].currTopic = topicList.getCurrentTopicPath();
         }
         else if ( (node = (jnode) topicList.root.getFirstLeaf()) != null &&
             node != topicList.root) { //If 6.1
//startPR2008-09-26^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          if(!selectmixedparent(node)){
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          topicList.setSelectionPath(new TreePath(node.getPath()));
          setupGametree();
          studentList[currStudent].currTopic = topicList.getCurrentTopicPath();
//startPR2008-09-26^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        }
     }
      else if ( (node = (jnode) topicList.root.getFirstLeaf()) != null &&
          node != topicList.root) { //If 6.1
//startPR2008-09-26^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        if(!selectmixedparent(node)){
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        topicList.setSelectionPath(new TreePath(node.getPath()));
        setupGametree();
//startPR2008-09-26^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      }
    }
    topicList.setRootVisible(false);
//startPR2010-05-24^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    courseList.setRootVisible(false);

    jnode jn = node;
    jnode par;
    while(jn!=null && (par=(jnode)jn.getParent())!=null && !par.get().trim().equals("")){
        jn = (jnode)jn.getParent();
    }
    if(jn!=null){
        setCourseListSelection(jn.get());
    }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    if(currPlayTopic != null) addsplitwarning();
    forcechange = false;
    if(db.query(studentList[currStudent].name, "sys_deletedstus", db.VECTOR)>=0){
        Object o = db.find(studentList[currStudent].name, "sys_deletedstus",db.VECTOR);
        if(o!=null){
            Vector v = (Vector)o;
            if(v!=null){
                if(v.size()==1){
                    String sv[] = u.splitString((String)v.get(0));
                    String s2 = u.gettext("otherteacherswarn", "sendmess");
                    s2 = s2.replaceFirst("%", sv[0]);
                    s2 = s2.replaceFirst("%", sv[1]);
                    messages.sendMessage(studentList[currStudent].name, shark.programName+" "+shark.versionNo,
                                    s2);
                }
                else if(v.size() > 1)
                {
                    String s2 = u.gettext("otherteacherswarn", "sendmessmulti");
                    s2 = s2.replaceFirst("%", shark.programName) + "\n\n";
                    String orisdelby = u.gettext("otherteacherswarn", "sendmessmulti_delby");
                    for(int p = 0; p < v.size(); p++){
                        String sv[] = u.splitString((String)v.get(p));
                        String delby = orisdelby.replaceFirst("%", sv[0]);
                        delby = delby.replaceFirst("%", sv[1])+"\n";
                        s2 = s2 + delby;
                    }
                    messages.sendMessage(studentList[currStudent].name, shark.programName+" "+shark.versionNo,
                                    s2);
                }
            }
        }
        db.delete(studentList[currStudent].name, "sys_deletedstus",db.VECTOR);
    }

//startPR2007-12-05^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    // to reverse the earlier setting of setFocusableWindowState to false (which
    // enables proper focusing of the sign-in dialogs)
//    if(shark.macOS){
//      if (!sharkStartFrame.mainFrame.isFocusableWindow()){
//        sharkStartFrame.mainFrame.setFocusableWindowState(true);
//        sharkStartFrame.mainFrame.setVisible(true);
//        sharkStartFrame.mainFrame.requestFocusInWindow();
//      }
//    }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    msearch.setVisible(!wantplayprogram);

  }

  public void setCourseListSelection(String s){
        jnode js[] = courseList.root.getChildren();
        for(int ii = 0; ii < js.length; ii++){
            if(s.equals(js[ii].get())){
                courseList.exitValueChanged = true;
                boolean savechange = forcechange;
                forcechange = true;
                courseList.setSelectionPath(new TreePath(js[ii].getPath()));
                forcechange = savechange;
                courseList.exitValueChanged = false;
                break;
            }
        }
  }







  void ownWordListEnabled(boolean enabled, boolean wantplayprogram){
      mownwords.setEnabled(enabled);
      if(!enabled){
          boolean restricted = db.query(optionsdb, "menforceprogram", db.TEXT) >= 0;
          if(restricted)mownwords.setToolTipText(strOwlTooltipNa);
          else{
              mownwords.setToolTipText(u.edit(strOwlTooltipNaFix, strMChoseTeacher, strOtherWork));
          }
      }
      else{
          mownwords.setToolTipText(strOwlTooltip);
      }

      
  }

//startPR2008-09-26^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  boolean selectmixedparent(jnode node) {
    if(wantplayprogram && (node=topicList.getMixedParent(node))!=null){
      topicList.setSelectionPath(new TreePath(node.getPath()));
      setupGametree();
      studentList[currStudent].currTopic = topicList.getCurrentTopicPath();
      return true;
    }
    return false;
  }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR

  boolean forcerevision(jnode node) {
    if(node == null) return false;
    do {
      if(node.dontforcerevision) return false;
    }while((node = (jnode)(node.getParent())) != null);
    return true;
  }
//startPR2010-05-24^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  void makegamesscreen(){
      bevelPanel1.removeAll();
      Dimension d = new Dimension(screenSize.width / 3,
                                                 screenSize.height);
      Dimension d2 = new Dimension(screenSize.width * 2 / 3,
                                                 screenSize.height);
      grid1.weightx = 1;
      bevelPanel5.setPreferredSize(d2);
      bevelPanel5.setMinimumSize(d2);
      bevelPanel2.setPreferredSize(d);
      bevelPanel2.setMinimumSize(d);

      bevelPanel1.add(bevelPanel5, grid1);
      bevelPanel1.add(bevelPanel2, grid1);
      bevelPanel1.validate();
   //   DragPanel  dp = new DragPanel(mainFrame, bevelPanel2); 
  }


  void maketopicscreen(){
      bevelPanel1.removeAll();

      Dimension d = new Dimension(screenSize.width / 3,
                                                 screenSize.height);
      Dimension d2 = new Dimension(screenSize.width*2/3,
                                                 screenSize.height);
      bevelPanelCourses.setPreferredSize(d);
      bevelPanel4.setPreferredSize(wantplayprogram?d2:d);
      bevelPanel2.setPreferredSize(d);
      bevelPanelCourses.setMinimumSize(d);
      bevelPanel4.setMinimumSize(wantplayprogram?d2:d);
      bevelPanel2.setMinimumSize(d);

        bevelPanelCourses.setVisible(!wantplayprogram);

      JScrollPane tlscroll = u.uScrollPane(topicList);
      tlscroll.setBorder(null);
//      topicList.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
//        public void mouseMoved(MouseEvent e) {
//          TreePath tp = topicList.getPathForLocation(e.getX(), e.getY());
//          if (tp != null) {
//            Object oo = tp.getLastPathComponent();
//            if (oo instanceof jnode) {
//              jnode node = (jnode) oo;
//              sharkStartFrame.mainFrame.setTitle(node.get());
//              if (node.type == jnode.COURSE) {
//                ( (treepainter) topicList.getCellRenderer()).setToolTipText(u.
//                  gettext("topictree", node.get()));
//              }
//              else
//               ( (treepainter) topicList.getCellRenderer()).setToolTipText(null);
//            }
//          }
//        }
//      });
      bevelPanel4.removeAll();
      bevelPanelCourses.removeAll();
      GridBagConstraints gbs = new GridBagConstraints();
      gbs.fill = GridBagConstraints.BOTH;
      gbs.weightx = 1;
      gbs.weighty = 0;
      gbs.gridx = 0;
      gbs.gridy = -1;
      bevelPanelCourses.setBackground(Color.white);
      bevelPanel4.setBackground(Color.white);
      bevelPanelCourses.add(choosecourse, gbs);
      bevelPanel4.add(clicktoexpand, gbs);
      gbs.weighty = 1;
      gbs.insets = new Insets(10,0,0,0);
      bevelPanel4.add(tlscroll, gbs);
      JScrollPane clscroll = u.uScrollPane(courseList);
      clscroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
      clscroll.setBorder(null);
      bevelPanelCourses.add(clscroll, gbs);
      gbs.insets = new Insets(0,0,0,0);
      gbs.weighty = 0;
      bevelPanel4.add(topicinfo, gbs);

      pnCourseInfo = new JPanel(new GridBagLayout());
      pnCourseInfo.setBorder(BorderFactory.createEtchedBorder());
      pnCourseInfo.setBackground(courseinfo.getBackground());
      gbs.gridx = -1;
      gbs.gridy = 0;
      courseinfo.setBorder(BorderFactory.createEmptyBorder());
      btTopicTest = u.sharkButton();
      boolean halfcomplete = sharkStartFrame.studentList[sharkStartFrame.currStudent].optionstring2("topictest_starteddate")!=null;
      Image tempim = Toolkit.getDefaultToolkit().getImage(sharkStartFrame.publicPathplus + "sprites" +
                                       sharkStartFrame.separator +
                                       (halfcomplete?"targethalf_il48.png":"target_il48.png"));

      Dimension imageButDim = new Dimension((sharkStartFrame.screenSize.width / 3)/20,
                                                 (sharkStartFrame.screenSize.width / 3)/20);
      ImageIcon saveicon = new ImageIcon(tempim.getScaledInstance((int)imageButDim.getWidth(),
            (int) imageButDim.getHeight(), Image.SCALE_SMOOTH));
      btTopicTest.setToolTipText(u.gettext("topictest", halfcomplete?"bttooltip_incomplete":"bttooltip_complete"));
      u.forcenotes(this, btTopicTest,false,true);
      btTopicTest.setIcon(saveicon);
      
      
      jnode sel = courseList.getSelectedNode();
      if(sel!=null){
          String s = sel.get();
          if(btTopicTest!=null)
              btTopicTest.setVisible(s.equals(TopicTest.courseName));
      }
      
      
      
      btTopicTest.setPreferredSize(new Dimension((int)imageButDim.getWidth()+10,(int)imageButDim.getHeight()+10));
      btTopicTest.setMaximumSize(new Dimension((int)imageButDim.getWidth()+10,(int)imageButDim.getHeight()+10));
      btTopicTest.setMinimumSize(new Dimension((int)imageButDim.getWidth()+10,(int)imageButDim.getHeight()+10));
      btTopicTest.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
            boolean halfcomplete = sharkStartFrame.studentList[sharkStartFrame.currStudent].optionstring2("topictest_starteddate")!=null;
            if(halfcomplete){
                 JButton jb1 = new JButton(u.gettext("topictest", "bt_continue"));
                 jb1.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        TopicTest.listenMessage.returnValue = ListenDialog.YES;
                        TopicTest.listenMessage.dispose();

                    }
                 });
                 JButton jb2 = new JButton(u.gettext("topictest", "bt_restart"));
                 jb2.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        TopicTest.listenMessage.returnValue = ListenDialog.RESTART;
                        TopicTest.listenMessage.dispose();

                    }
                 });
                 JButton jb3 = new JButton(u.gettext("cancel", "label"));
                 jb3.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        TopicTest.listenMessage.returnValue = ListenDialog.CANCEL;
                        TopicTest.listenMessage.dispose();

                    }
                 });
                 String s = sharkStartFrame.studentList[sharkStartFrame.currStudent].optionstring2("topictest_starteddate");
                 TopicTest.listenMessage = new ListenDialog(u.gettext("topictest", "messageboxtitle"), u.gettext("topictest", "mess_middle", s), sharkStartFrame.publicTestSayLib, "mess_middle",
                         sharkStartFrame.mainFrame, 0, new JButton[]{jb1, jb2, jb3});

                 TopicTest.listenMessage.setVisible(true);
                 if(TopicTest.listenMessage.returnValue == ListenDialog.CANCEL){
                     TopicTest.listenMessage = null;
                     return;
                 }
                 else if(TopicTest.listenMessage.returnValue == ListenDialog.RESTART){
                     TopicTest.listenMessage = null;
                     TopicTest.clearprogress();
                     TopicTest.stage = 0;
                 }
                 else if(TopicTest.listenMessage.returnValue == ListenDialog.YES){
                     TopicTest.listenMessage = null;
                     s = sharkStartFrame.studentList[sharkStartFrame.currStudent].optionstring2("topictest_stage");
                     if(s!=null)
                        TopicTest.stage = Integer.parseInt(s);
                     s = sharkStartFrame.studentList[sharkStartFrame.currStudent].optionstring2("topictest_difficulty");
                     if(s!=null)
                        TopicTest.path = Integer.parseInt(s);
                 }
                 TopicTest.listenMessage = null;
            }
            else{
                String s;
                if((s = sharkStartFrame.studentList[sharkStartFrame.currStudent].optionstring2("topictest_completeddate")) != null){

                    // complete test has been done

                     JButton jb1 = new JButton(u.gettext("topictest", "bt_goto"));
                     jb1.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            TopicTest.listenMessage.returnValue = ListenDialog.YES;
                            TopicTest.listenMessage.dispose();
                        }
                     });
                     JButton jb2 = new JButton(u.gettext("topictest", "bt_retake"));
                     jb2.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            TopicTest.listenMessage.returnValue = ListenDialog.RESTART;
                            TopicTest.listenMessage.dispose();
                        }
                     });
                     JButton jb3 = new JButton(u.gettext("cancel", "label"));
                     jb3.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            TopicTest.listenMessage.returnValue = ListenDialog.CANCEL;
                            TopicTest.listenMessage.dispose();
                        }
                     });
                     String ss = sharkStartFrame.studentList[sharkStartFrame.currStudent].optionstring2("topictest_resulttopic");
                     if(ss==null)ss="";
                     TopicTest.listenMessage = new ListenDialog(u.gettext("topictest", "messageboxtitle"), u.edit(u.gettext("topictest", "mess_lasttest"), s, ss), sharkStartFrame.publicTestSayLib, "mess_lasttest",
                             sharkStartFrame.mainFrame, 0, new JButton[]{jb1, jb2, jb3});
                     TopicTest.listenMessage.setVisible(true);
                     if(TopicTest.listenMessage.returnValue == ListenDialog.YES){
                         TopicTest.listenMessage = null;
                         jnode node = sharkStartFrame.mainFrame.topicList.findNode(ss);
                         sharkStartFrame.mainFrame.topicList.setSelectionPath(new TreePath(node.getPath()));
                         sharkStartFrame.mainFrame.gameicons = true;
                         sharkStartFrame.mainFrame.switchdisplay();
                         sharkStartFrame.mainFrame.setupGametree();
                         return;
                     }
                     else if(TopicTest.listenMessage.returnValue == ListenDialog.RESTART)
                     {
                         TopicTest.clearprogress();
                         TopicTest.stage = 0;
                     }
                     else if(TopicTest.listenMessage.returnValue == ListenDialog.CANCEL)
                     {
                         TopicTest.listenMessage = null;
                         return;
                     }
                     TopicTest.listenMessage = null;
                }
                else{
                    // no previous completed test

                     JButton jb1 = new JButton(u.gettext("topictest", "bt_start"));
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
                     TopicTest.listenMessage = new ListenDialog(u.gettext("topictest", "messageboxtitle"), u.gettext("topictest", "mess_start"), sharkStartFrame.publicTestSayLib, "mess_start",
                             sharkStartFrame.mainFrame, 0, new JButton[]{jb1, jb2});

                     TopicTest.listenMessage.setVisible(true);
                     if(TopicTest.listenMessage.returnValue == ListenDialog.CANCEL){
                         TopicTest.listenMessage.dispose();
                         TopicTest.listenMessage = null;
                         return;
                     }
                     TopicTest.stage = 0;
                     TopicTest.path = -1;
                     TopicTest.listenMessage = null;
                }
            }
             
             TopicTest.previousTopic = currPlayTopic;
             TopicTest tt = new TopicTest();
             currPlayTopic = topic.findtopic(tt.getTopic());

     //         setTopicList(TopicTest.courseName, null);
     //         setCourseListSelection(TopicTest.courseName);
     //         currPlayTopic = topic.findtopic(tt.getTopic());
              jnode node = topicList.findNode(currPlayTopic.name);
              topicList.setSelectionPath(new TreePath(node.getPath()));
              mainFrame.setupGametree();
             startgame(gamename[u.findString(sharkStartFrame.mainFrame.gamename, tt.gameName)]);
         }
      });

      gbs.weightx = 1;
      gbs.fill = GridBagConstraints.BOTH;
      JPanel courseinfocontainer = new JPanel(new GridBagLayout());
      courseinfocontainer.setBackground(Color.blue);


      JButton btWordListVideo = u.sharkButton();
      tempim = Toolkit.getDefaultToolkit().getImage(sharkStartFrame.publicPathplus + "sprites" +
                                       sharkStartFrame.separator + "video_il48.png");

      imageButDim = new Dimension((sharkStartFrame.screenSize.width / 3)/20,
                                                 (sharkStartFrame.screenSize.width / 3)/20);
      saveicon = new ImageIcon(tempim.getScaledInstance((int)imageButDim.getWidth(),
            (int) imageButDim.getHeight(), Image.SCALE_SMOOTH));
      btWordListVideo.setToolTipText(u.gettext("videotutorials", "wordlisttooltip"));
      btWordListVideo.setIcon(saveicon);
      btWordListVideo.setPreferredSize(new Dimension((int)imageButDim.getWidth()+10,(int)imageButDim.getHeight()+10));
      btWordListVideo.setMaximumSize(new Dimension((int)imageButDim.getWidth()+10,(int)imageButDim.getHeight()+10));
      btWordListVideo.setMinimumSize(new Dimension((int)imageButDim.getWidth()+10,(int)imageButDim.getHeight()+10));
      btWordListVideo.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
               String base = u.gettext("videotutorials", "basesite", shark.getProgramShortName());
//               new TutorialChoice_base(sharkStartFrame.mainFrame,
//                  new String[]{u.gettext("videotutorials", "coursehelp"),
//                        u.gettext("videotutorials", "chooselist")},
//                  new String[]{base + u.gettext("mvidcoursehelp", "url"),
//                        base + u.gettext("mvidchooselist", "url")});
               new TutorialChoice_base(sharkStartFrame.mainFrame,
                  new String[]{
                        u.gettext("videotutorials", "chooselist")},
                  new String[]{
                        base + u.gettext("mvidchooselist", "url")});
         }
      });
      gbs.weightx = 0;
      gbs.fill = GridBagConstraints.NONE;
      gbs.insets = new Insets(10,10,0,0);
      gbs.anchor = GridBagConstraints.NORTH;
      pnCourseInfo.add(btWordListVideo, gbs);
      gbs.anchor = GridBagConstraints.CENTER;
      gbs.weightx = 1;
      gbs.fill = GridBagConstraints.BOTH;
      gbs.insets = new Insets(0,0,0,0);
      pnCourseInfo.add(courseinfo, gbs);
      gbs.fill = GridBagConstraints.NONE;
      gbs.weightx = 0;
      gbs.insets = new Insets(10,10,10,10);
      if(shark.language.equals(shark.LANGUAGE_EN)){
          gbs.anchor = GridBagConstraints.NORTH;
          gbs.insets = new Insets(10,0,0,10);
          pnCourseInfo.add(btTopicTest, gbs);
          gbs.anchor = GridBagConstraints.CENTER;
      }
      gbs.insets = new Insets(0,0,0,0);
      gbs.weightx = 1;
      gbs.fill = GridBagConstraints.BOTH;

      gbs.gridx = 0;
      gbs.gridy = -1;
      bevelPanelCourses.add(pnCourseInfo, gbs);


      grid1.weightx = 1;
      bevelPanel1.add(bevelPanelCourses, grid1);
      bevelPanel1.add(bevelPanel4 , grid1);
      bevelPanel1.add(bevelPanel2, grid1);
      bevelPanel1.validate();
  }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2007-11-26^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  void setupGametree() {
    setupGametree(false);
  }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          // build list of games for current topic
//startPR2008-08-17^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  void setupGametree(boolean samelist) {
    setupGametree(false, samelist);
  }
  // build list of games for current topic
//  void setupGametree() {
  void setupGametree(boolean phonicschange, boolean samelist) {
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    String s, last = "";
    int i, j, k;
//startPR2008-09-03^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     canshowmarkedgames = false;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2007-11-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    if(Demo_base.isDemo&&Demo_base.finishedSetup)
      Demo_base.blockTeachingNotes = false;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    if (!samelist){
      savesplitwords();
      wordlist.usedefinitions = true;
      wordlist.usetranslations = true;
    }
    student overstu = studentList[overrideStudent()];
    jnode scrolltonode = null;
    forcechange = true;
    if (usingsuperlist != null)
      currPlayTopic = usingsuperlist;
    else if(!samelist){
      specialsuperlist = false;
      jnode node = topicList.getSelectedNode();
      if (wantplayprogram && forcerevision(node)) {
//        while (wantplayprogram && node.getLevel() > 2) {                //  rb 16/3/08  mmmm
//          node = (jnode) (node.getParent());                             //  rb 16/3/08  mmmm
//        }                                                                //  rb 16/3/08  mmmm
        topicList.setSelectionPath(new TreePath(node.getPath()));
        if (node.getLevel() >= 2 && !node.isLeaf()) {   //  rb 16/3/08  mmmm
          currPlayTopic = new topic(node);
          specialsuperlist = true;
//          if (!gameicons && playprogram != null) {              // rb 21/1/08
//            gameicons = true;
//            switchdisplay();
//          }
        }
        else   if(node.getLevel() > 1) {             //  rb 16/3/08  mmmm
          currPlayTopic = topicList.getCurrentTopic();
         // RB 20/1/06 start
          if(currPlayTopic == null) {                             // warning message && remove set work
            if(tryrefresh()) return;
          }
          // RB 20/1/06 end
        }
      }
      else   if(!wantplayprogram || node.getLevel() > 1) {
        if(!samelist)
            currPlayTopic = topicList.getCurrentTopic();
        // RB 20/1/06 start
        if(currPlayTopic == null && !node.inphonics()) {
          if(tryrefresh()) return;
        }
        // RB 20/1/06 end
      }
    }
//startPR2007-02-16^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    if (currPlayTopic != null) {
      if (!currPlayTopic.databaseName.equals(topicTree.publictopics)&&currPlayTopic.getRowCount()>16)
        setMenuForExtended(true);
      else
        setMenuForExtended(false);
    }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    if (currPlayTopic != null && !samelist                      // start rb 17-12-07
         && !wantplayprogram && !phonicschange && currPlayTopic.phonicscourse) {
        student.clearOption("notmarkedgames");                // rb 17-12-07
        showmarkedgames = true;
    }                                      // end  rb 17-12-07
//    sametopic = false;
    usingsuperlist = (currPlayTopic != null && currPlayTopic.superlist) ?
        currPlayTopic : null;
    if (usingsuperlist != null
        && currPlayTopic.superd.usetopic >= 0) {
      currPlayTopic = currPlayTopic.supert[currPlayTopic.superd.usetopic];
    }
//startPR2007-11-26^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    String newname = topicList.getCurrentTopicPath();
//    boolean newtopic = !newname.equals(overstu.currTopic);
//    boolean newtopic = topicList.isInitialSelection||!newname.equals(overstu.currTopic);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    String oldtop = overstu.currTopic;                         // rb 22/02/08  v5
    overstu.currTopic = topicList.getCurrentTopicPath();
    if(!overstu.currTopic.equals(oldtop)) overstu.sametopic=0; // rb 22/02/08  v5
    currCourse = null;
    if (wantplayprogram) { //If 1
      try { //- word lists are restricted
//startPR2007-11-29^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//        jnode node = (jnode) topicList.getLeadSelectionPath().getPathComponent(2);
        jnode node = (jnode) topicList.getSelectedNode();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        jnode n1[] = topicList.root.getChildren(), n2[];
        loop1:for (i = k = 0; i < n1.length; ++i) {
          n2 = n1[i].getChildren();
          for (j = 0; j < n2.length; ++j, ++k) {
            if (node == n2[j]) {
              currCourse = courselist[k];
              break loop1;
            }

          }
        }
      }
      catch (IllegalArgumentException e) {}
    }
    else { //Else 1-Word lists are not restricted.
      currCourse = u.splitString(overstu.currTopic, topicTree.CSEPARATOR)[0];
    }
    if (currPlayTopic != null //If 2 - There is a topic in use
        && currPlayTopic.databaseName.equals(topicTree.publictopics)
        && u.findString(publicCourses, currCourse) < 0) {
      currCourse = publicCourses[1];
    }
    bevelPanel2.removeAll();
//startPR2007-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    setwordfont();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    if (currPlayTopic != null) { //If 3- There is a topic in use
      if(Demo_base.isDemo) currPlayTopic.phonicscourse = (u.findString(Demo_base.phonicstopics, currPlayTopic.name)>=0);
      else currPlayTopic.phonicscourse = u.findString(u.phonicscourses,currCourse) >= 0;
      printlist.setEnabled(true);
//startPR2008-09-03^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      if(!wordTree.redrawing) wordTree.reset();
//      wordTree.setup(currPlayTopic, null);
      if(!samelist){    
          overstu.overriddenProgPhonics = false;
          if(!wordTree.redrawing)
            wordTree.reset();
      }

      playprogram = null;
      jnode sel1 = topicList.getSelectedNode();
      try{playprogram = (program.saveprogram) db.find(studentList[currStudent].
                                                          name,
                                                          playprogramname = topicList.
                                                          findDatabase(sel1), db.PROGRAM);}
      catch(ClassCastException e){playprogram = null;}
       
      
      currPlayTopic.getWords(null,false);// plug topic - needed otherwise problems in addsplits in topic using wrong
                                         // value of usephonics
      setusephonics(phonicschange);// two calls to this otherwise canexend (topic.java) uses wrong value of usephonics
      wordTree.setup(currPlayTopic, null, false, samelist);
      setusephonics(phonicschange);
      currPlayTopic.split4 = wordTree.split4;
      currPlayTopic.unsplit = wordTree.unsplit;

      boolean showstandard = true;
      boolean showextended = true;
      boolean showextendedwhole = true;

//      wordTree.standard.setVisible(true);
//      wordTree.extended.setVisible(true);
//      wordTree.extendedwhole.setVisible(true);
      if(wordlist.usephonics && currPlayTopic.canextend()) {
         if(!currPlayTopic.extendedphonics() ) {
             showstandard = false;
//           wordTree.standard.setVisible(false);
             showextended = false;
//           wordTree.extended.setVisible(false);
             showextendedwhole = false;
//           wordTree.extendedwhole.setVisible(false);
           if(wordTree.wholeextended || wordTree.shuffled) {
             wordTree.standard.setSelected(true);
             wordTree.wholeextended = false;
             wordTree.shuffled = false;
             wordTree.setup(currPlayTopic, null);
           }
         }
      }
      creategametree();
      if(currPlayTopic.phonics && !currPlayTopic.phonicsw) {
      }
      else {
        if (currPlayTopic.splitwords != null //If 3.2
            && currPlayTopic.splitwords.length > 0) { //Words in current topic are split
       //   wordTree.splitCB.setBackground(Color.magenta); //AND there are one or more split words
          wordTree.splitCB.setText(wordTree.splitchanged?wordTree.splitlab3:wordTree.splitlab2);
          wordTree.splitCB.setToolTipText(wordTree.splitchanged?wordTree.splittool3:wordTree.splittool2);
//          if (wordTree.split && !currPlayTopic.superlist &&
//              !wordTree.usephonics) //If 3.2.1
//            bevelPanel2.add(splitwarning, grid2); // - The wordlist wordTree has been split into syllables
        }
        else { //Else 3.2
   //       wordTree.splitCB.setBackground(vpanel3.getBackground()); //-Words in current topic are not split
          wordTree.splitCB.setText(wordTree.splitlab1);
          wordTree.splitCB.setToolTipText(wordTree.splittool1);
        }
      }
      wordTree.showsavesplits();
//startPR2008-08-17^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      wordTree.splitCB.setVisible(!wordTree.usephonics && currPlayTopic.multisyll && usingsuperlist==null);   // rb 13/12/07
      wordTree.splitsPan.setVisible(!wordTree.usephonics);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      byte newph = (byte)(!currPlayTopic.phonics || currPlayTopic.phonicsw && !wordlist.usephonics ? 0
//              : (currPlayTopic.phonicsw ? 2:1));
//      TreeModel tmpgtp = new TreeModel();//  gameTree.getModel();
      boolean someGames = !isGameTreeEmpty();
      gameTree.reduceGames(currPlayTopic);

      
      
      
      
      
      
      gameTree.overrideSuperSpelling = false;
      if(!wantplayprogram)
          stripbannedgames();
      if(isGameTreeEmpty()&& someGames){
         if(usingsuperlist!=null
            && usingsuperlist.superd.last3games.indexOf(topic.SPELLING)<0 && anySpellingExcluded()){
             creategametree();
             topic.superForceSpelling = false;
             gameTree.reduceGames(currPlayTopic);
             topic.superForceSpelling = true;
             gameTree.overrideSuperSpelling = true;
             if(!wantplayprogram)
                  stripbannedgames();
         }          
      }     

//startPR2009-10-10^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      boolean nomarkghead = true;
      int z = 0;
      if(currPlayTopic.markgheading!=null){
        for (z = 0; z < markgameheadings.length; z++) {
          if (currPlayTopic.markgheading.equals(markgameheadings[z][0])) {
            nomarkghead = false;
            break;
          }
        }
      }
      allgamestext1 = nomarkghead?allgamesdefaulttext1:markgameheadings[z][1];
//      showallgames.setText(allgamestext1);
//      showallgames.setToolTipText(nomarkghead?allgamesdefaulttooltip:markgameheadings[z][3]);
      allgamestext2 = nomarkghead?allgamesdefaulttext2:markgameheadings[z][2];
      iconheading = nomarkghead?iconheadingdefault:markgameheadings[z][4];
      iconheading2 = nomarkghead?iconheadingdefault2:markgameheadings[z][5];
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      creategamepanel();  //swapped over
      buildgamepanel1();  //swapped over
      if(!gameTree.root.isLeaf()) {              // rb 13/11/06
       int pos =0;
       if (currPlayTopic.markgames() != null
            && (!wantplayprogram || playprogram.it[playprogram.currstep].games.length==0 && currPlayTopic.markgames() != null)  &&
            usingsuperlist == null) {
          jnode hdg = null;
          String mg[] = currPlayTopic.markgames();
          for (i = 0; i < mg.length; ++i) {
            if (gameTree.find(mg[i]) != null) {
              canshowmarkedgames = true;
//              jnode gg = gameTree.addChild(hdg, mg[i]);
//              gg.setIcon();
            }
          }
          if(hdg!=null) {hdg.setIcon();++pos;}
       }
        if (currPlayTopic.markgames2() != null
            && (!wantplayprogram || playprogram.it[playprogram.currstep].games.length==0 && currPlayTopic.markgames() != null)  &&
            usingsuperlist == null) {
//startPR2009-07-15^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//          jnode hdg = gameTree.addChild(gameTree.root,
//                                        u.gettext("showallgames", "treeheading2"),
//                                        (jnode) (gameTree.root.getChildAt(1)));
          jnode hdg = null;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          String mg2[] = currPlayTopic.markgames2();
          for (i = 0; i < mg2.length; ++i) {
            if (gameTree.find(mg2[i]) != null) {
              canshowmarkedgames = true;
              jnode gg = gameTree.addChild(hdg, mg2[i]);
              if(gg!=null)
                  gg.setIcon();
            }
          }
          if(hdg != null) hdg.setIcon();
        }
//startPR2009-07-24^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//        if(wantplayprogram &&   playprogram.it[playprogram.currstep].games.length==0 && currPlayTopic.markgames() != null) {
//            int from = (currPlayTopic.markgames2() != null) ? 2:1;
//            while (gameTree.root.getChildCount() > from) {
//              ((jnode)(gameTree.root.getChildAt(from))).removeFromParent();
//              gameTree.model.reload();
//            }
//        }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        if (overstu.currGame != null) { //If 3.1 - The override student has a game selected
          jnode node;
          if ( (node = gameTree.findTopic(overstu.currGame)) != null) { //If 3.1.1
            TreePath tp = new TreePath(node.getPath()); //- There is a topic in the gameTree for the overridden student's current game
            gameTree.setSelectionPath(tp);
            gameTree.scrollPathToVisible(tp);
          }
           else { //Else 3.1.1 -There is no topic in the gameTree for the overridden student's current game
            TreePath tp = new TreePath(gameTree.root.getFirstLeaf().getPath());
            gameTree.setSelectionPath(tp);
            gameTree.scrollPathToVisible(tp);
          }
        }
        else { //Else 3.1 - The override student does'nt have a game selected
          jnode node;
          if ( (node = (jnode) gameTree.root.getFirstLeaf()) != null) { //If 3.1.1
            gameTree.setSelectionPath(new TreePath(node.getPath())); //- There is a leaf on the gameTree
          }
        }
      }                        // rb 13/11/06
      grid2.gridy = -1;
      grid2.gridx = 0;
      grid2.weighty = 0;
      JPanel wordlistpan = new JPanel(new GridBagLayout());
      wordlistpan.setBorder(BorderFactory.createLineBorder(Color.lightGray, 3));
      if(wordTree.model.getSize() > 0){
        JTabbedPane jtp =  new JTabbedPane();
        wtab1 = new JPanel(new BorderLayout());
        wtab2 = new JPanel(new BorderLayout());
        wtab3 = new JPanel(new BorderLayout());


//         if(wordTree.areGroupings) {
//            if (wordTree.canextend) {
//               if(!wordTree.nostandard) jtp.addTab(wordlisttab1, wtab1);
//               jtp.addTab(wordlisttab2, wtab2);
//               jtp.addTab(wordlisttab3, wtab3);
//            }
//         }
//         else {
            if (wordTree.canextend) {
               if(!wordTree.nostandard && showstandard) jtp.addTab(wordlisttab1, wtab1);
               if(showextended)jtp.addTab(wordlisttab2, wtab2);
               if(showextendedwhole)jtp.addTab(wordlisttab3, wtab3);
            }
//         }
        
        
        jtp.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent evt) {
                JTabbedPane pane = (JTabbedPane) evt.getSource();
                int sel = pane.getSelectedIndex();
                lasttab = sel;
                if(pane.getTitleAt(sel).equals(wordlisttab1)){
                    wtab1.add(scroller, BorderLayout.CENTER);
                    wordTree.newselection(wordlist.STANDARD);
                    pane.setSelectedIndex(sel);
                }
                else if (pane.getTitleAt(sel).equals(wordlisttab2)){
                    wtab2.add(scroller, BorderLayout.CENTER);
                }
                else if(pane.getTitleAt(sel).equals(wordlisttab3)){
                    wtab3.add(scroller, BorderLayout.CENTER);
                    wordTree.newselection(wordlist.EXTENDEDWHOLE);
                    pane.setSelectedIndex(sel);
                }
            }
        });

        jtp.addMouseListener(new java.awt.event.MouseAdapter() {
          public void mouseReleased(MouseEvent e) {
              JTabbedPane pane = (JTabbedPane) e.getSource();
              int sel = pane.getSelectedIndex();
              if (pane.getTitleAt(sel).equals(wordlisttab2)){
                    refreshExtendedSample();
              }
          }
        });
        wordTree.butRefresh.setVisible(false);

        boolean showtabs = (!currPlayTopic.superlist && jtp.getTabCount()>0);
        Component c = (showtabs?(Component)jtp:(Component)scroller);
        if(showtabs){
            Component c2 = jtp.getComponentAt(0);
            boolean found = false;
            int sel = 0;
            if(c2.equals(wtab1)){
                wtab1.add(scroller, BorderLayout.CENTER);
                sel = wordlist.STANDARD;
                found = true;
            }
            else if(c2.equals(wtab2)){
                wtab2.add(scroller, BorderLayout.CENTER);
                sel = wordlist.EXTENDED;
                found = true;
            }
            else if(c2.equals(wtab3)){
                wtab3.add(scroller, BorderLayout.CENTER);
                sel = wordlist.EXTENDEDWHOLE;
                found = true;
            }
            if(found)
                wordTree.newselection(sel);
            jtp.setSelectedIndex(0);
        }
        if(usingsuperlist!=null){
            JTextPane fmtp = new textpane();
            fmtp.setEditable(false);
            fmtp.setContentType("text/html");
            fmtp.setText(fastmodetitle.replaceAll("%", usingsuperlist.name));
            fmtp.setCaretPosition(0);
            fmtp.setBackground(fastmodecolor_lighter);
            fmtp.setOpaque(true);
            fmtp.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));


            grid2.gridy = -1;
            grid2.gridx = 0;
            grid2.weighty = 0;
            grid2.weightx = 1;
            if(showtabs)grid2.insets = new Insets(0,0,10,0);
            wordlistpan.add(fmtp, grid2);
        }
        else{
            wordlistpan.add(current, grid2);
            JPanel tpan = new JPanel(new GridBagLayout());
            grid2.gridy = 0;
            grid2.gridx = -1;
            grid2.weighty = 0;
            grid2.weightx = 1;
            tpan.add(currentlist, grid2);
            grid2.weightx = 0;
//            JButton jb1 = new JButton("<");
//            JButton jb2 = new JButton(">");
//            jb1.setBackground(Color.orange);
//            jb2.setBackground(Color.orange);
            tpan.add(prevlist, grid2);
            tpan.add(nextlist, grid2);
            grid2.gridy = -1;
            grid2.gridx = 0;
            if(showtabs)grid2.insets = new Insets(0,0,10,0);
            wordlistpan.add(tpan, grid2);
          }
          grid2.weightx = 1;
          grid2.weighty = 0;

  //      wordlistpan.add(c, grid2);
        grid2.insets = new Insets(0,0,0,0);
        JPanel optionsPan = new JPanel(new GridBagLayout());
        JPanel phonicPan = new JPanel(new GridBagLayout());
        JPanel cbPan = new JPanel(new GridBagLayout());
        JPanel butPan = new JPanel(new GridBagLayout());
        grid2.fill = GridBagConstraints.BOTH;
        grid2.weighty = 1;
        JPanel phonicPanInner = new JPanel(new GridBagLayout());
//        grid2.insets = new Insets(wordlist.border,wordlist.border,wordlist.border,wordlist.border);
        phonicPanInner.add(wordlist.phonemes, grid2);
        phonicPanInner.add(wordTree.def, grid2);
        phonicPanInner.add(wordTree.translations, grid2);
        grid2.insets = new Insets(0,0,0,0);
        phonicPanInner.setBackground(Color.gray);
        phonicPanInner.setOpaque(true);
        phonicPan.add(phonicPanInner, grid2);
        phonicPan.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
 //       wordTree.resetSplits();
        grid2.gridx = -1;
        grid2.gridy = 0;
        grid2.anchor = GridBagConstraints.SOUTH;
        grid2.insets = new Insets(wordlist.border,wordlist.border,wordlist.border,0);
        boolean showop = false;
        if(!shark.phonicshark && (!currPlayTopic.phonics || currPlayTopic.phonicsw)) {
            if(!samelist){
                wordlist.usedefinitions = true;
                wordlist.usetranslations = true;  
            }            
//            if (playprogram == null && currPlayTopic.phonicsw && !currPlayTopic.blended && !currPlayTopic.justphonics){
            if (currPlayTopic.phonicsw && !currPlayTopic.blended && !currPlayTopic.justphonics){
                cbPan.add(phonicPan, grid2);
                wordlist.phonemes.setVisible(true);
                wordTree.def.setVisible(false);
                wordTree.translations.setVisible(false);
                wordlist.usedefinitions = false;
                wordlist.usetranslations = false;
                showop = true;
            }
            else if(currPlayTopic.translations) {
                cbPan.add(phonicPan, grid2);
                wordlist.phonemes.setVisible(false);
                wordTree.def.setVisible(false);
                wordTree.translations.setVisible(true);
                wordTree.translations.setSelected(wordlist.usetranslations);
                wordlist.usedefinitions = false;
                showop = true;
            }
            else if(currPlayTopic.definitions){
                cbPan.add(phonicPan, grid2);
                wordlist.phonemes.setVisible(false);
                wordTree.def.setVisible(true);
                wordTree.translations.setVisible(false);
                wordTree.def.setSelected(wordlist.usedefinitions);
                wordlist.usetranslations = false;
                showop = true;
            }
            grid2.anchor = GridBagConstraints.CENTER;
            grid2.insets = new Insets(wordlist.border,wordlist.border,wordlist.border,wordlist.border);        
            if ( (playprogram == null || !wordlist.usephonics) && !currPlayTopic.blended && !currPlayTopic.justphonics) {
                if(!currPlayTopic.phrases && !wordTree.usephonics){
                    cbPan.add(wordTree.splitsPan, grid2);
                    showop = true;
                  }
          }                
        }
        grid2.anchor = GridBagConstraints.CENTER;
        grid2.fill = GridBagConstraints.NONE;
        if(u.screenResWidthMoreThan(1000))
            grid2.insets = new Insets(0,0,wordlist.border,wordlist.border);
        else
            grid2.insets = new Insets(0,0,wordlist.border,0);
        if (usingsuperlist == null) {
          wordTree.setInOrder(currPlayTopic.inorder);
          if (!currPlayTopic.phrases && !wordTree.nostandard) {
            wordTree.butInOrder.setVisible(true);
            butPan.add(wordTree.butInOrder, grid2);
            showop = true;
          }
        }
        
        butPan.add(wordTree.butRefresh, grid2);
        if (!currPlayTopic.phonics || currPlayTopic.phonicsw) {
            if(!currPlayTopic.phrases) {
                butPan.add(wordTree.butHV, grid2);
                showop = true;
            }
        }
        grid2.fill = GridBagConstraints.NONE;
        grid2.anchor = GridBagConstraints.WEST;
        grid2.gridx = -1;
        grid2.gridy = 0;
        grid2.insets = new Insets(0,0,wordlist.border,0);
        optionsPan.add(cbPan, grid2);
        grid2.anchor = GridBagConstraints.SOUTHEAST;
        optionsPan.add(butPan, grid2);
        grid2.anchor = GridBagConstraints.CENTER;
        grid2.fill = GridBagConstraints.BOTH;
        grid2.weighty = 1;
        grid2.gridx = 0;
        grid2.gridy = -1;
        grid2.insets = new Insets(0,0,0,0);
        wordlistpan.add(c, grid2);
        grid2.weighty = 0;
        grid2.insets = new Insets(wordlist.border,0,0,0);
        wordlistpan.add(optionsPan, grid2);
        grid2.insets = new Insets(0,0,0,0);
        optionsPan.setVisible(showop);
      }
      else if(currPlayTopic.specialgame(topic.sentencegames2[0])) {
        mlabel_base wp = new mlabel_base(u.gettext("topics","worded"));
        wp.centre = true;
        wp.setOpaque(false);
        wordlistpan.add(wp,grid2);
        gameTree.expandAll();
      }
      grid2.gridx = 0;
      grid2.gridy = 0;
      grid2.weightx = 1;
      grid2.weighty = 1;
      grid2.fill = GridBagConstraints.BOTH;
      bevelPanel2.add(wordlistpan, grid2);
      grid2.weighty = 0;
      jnode nsel = topicList.getSelectedNode();
      if (nsel != null) { //If 3.3
        jnode pr = (jnode) nsel.getPreviousLeaf(); //There is a selected node
        jnode nx = (jnode) nsel.getNextLeaf();
        if (wantplayprogram || !specialsuperlist){
          if (pr != null || nx != null) { // rb 5/2/08
          vpanel2.removeAll(); //There is a previous or a next leaf for the node
          grid2.gridy = 0;
          grid2.gridx = -1;
//startPR2007-12-20^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          boolean course = false;
          jnode n = nsel;
          if(Demo_base.isDemo){
//startPR2010-05-24^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//            if (pr != null)
//              vpanel2.add(prevlist, grid2); //There is a previous leaf
//            if (nx != null)
//              vpanel2.add(nextlist, grid2); //The next leaf is there
              prevlist.setVisible(pr != null);
              nextlist.setVisible(nx != null);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          }
          else{
            while (!n.equals(pr)) {
              n = (jnode) n.getPreviousNode();
              if (n == null)break;
              if (n.type == jnode.COURSE) {course = true; break; }
            }
//startPR2010-05-24^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//            if (pr != null && !course && !pr.get().trim().equals("")) //If 3.3.1.1
//              vpanel2.add(prevlist, grid2); //There is a previous leaf
            prevlist.setVisible(pr != null && !course && !pr.get().trim().equals(""));
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            course = false;
            n = nsel;
            while (!n.equals(nx)) {
              n = (jnode) n.getNextNode();
              if (n == null)break;
              if (n.type == jnode.COURSE) {course = true; break; }
            }
//startPR2010-05-24^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//            if (nx != null && !course && !nx.get().trim().equals("")) //If 3.3.1.2
//              vpanel2.add(nextlist, grid2); //The next leaf is there.
            nextlist.setVisible(nx != null && !course && !nx.get().trim().equals(""));
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          grid2.gridy = -1;
          grid2.gridx = 0;
          bevelPanel2.add(vpanel2, grid2);

        }
          else{
              prevlist.setVisible(false);
              nextlist.setVisible(false);              
          }
          }

/*
        grid2.insets = new Insets(0,0,0,0);
        Dimension d = new Dimension(screenSize.width / 3,
                                                 screenSize.height/10);
          JPanel jp = new JPanel(new GridBagLayout());
          jp.setBackground(gamescolor);
          jp.setPreferredSize(d);
          jp.setMinimumSize(d);
          grid2.fill = GridBagConstraints.NONE;
          grid2.gridx = 0;
          grid2.gridy = -1;
          jp.add(switchtogames, grid2);
          grid2.fill = GridBagConstraints.BOTH;
          bevelPanel2.add(jp, grid2);
*/


        grid2.insets = new Insets(0,0,0,0);
        Dimension d = new Dimension(screenSize.width / 3,
                                                 screenSize.height/8);
        Dimension d2 = new Dimension(screenSize.width / 5,
                                                 screenSize.height/14);
        JPanel switchtogamespn = new JPanel(new GridBagLayout());
        switchtogamespn.setBackground(gamescolor);
          switchtogamespn.setPreferredSize(d);
          switchtogamespn.setMinimumSize(d);
          grid2.fill = GridBagConstraints.NONE;
          grid2.gridx = 0;
          grid2.gridy = -1;
     //     XrButton_base xb = new XrButton_base("Choose word list");

          switchtogames.setPreferredSize(d2);
          switchtogames.setMinimumSize(d2);
          switchtogamespn.add(switchtogames, grid2);
          grid2.fill = GridBagConstraints.BOTH;
          bevelPanel2.add(switchtogamespn, grid2);



      }
    }
    else { //Else 3 -  No topic in use
      printlist.setEnabled(false);
      gameTree.removeAllChildren(gameTree.root);
      stopGamepanel();
      wordTree.model.removeAllElements();
    }
    bevelPanel2.validate();
    bevelPanel2.repaint();
    bevelPanel1.validate();
    if (gameicons) {
        if(gameheadings==null)gameheadings=new String[0];
        if (gamePanel != null) //If 4
          buildgamepanel2(samelist); // - Game icon screen displayed
        else
          gameicons = false;
    }
    wordTree.setfont();
    if (currPlayTopic != null) { //If 5- There is a topic in use
      if (gameicons) //If 5.1-Game icon screen displayed
        gamePanel.requestFocus();
    }
    TreePath node = topicList.getSelectionPath();
    if (node != null) { //If 6 - There is a selected node
      topicList.scrollPathToVisible(node);
    }
    if (scrolltonode != null) //If 7
      gameTree.scrollPathToVisible(new TreePath(scrolltonode.getPath()));
    if (gameicons) { //If 8 - The icons screen is showing
//      if (!topics.isAncestorOf(mtopictree)) { //If 8.1
//        topics.remove(mgameicons); //mtopictree is not a menu item on the topics menu
//        topics.add(mtopictree);
//        topics.setText(mhome1);
        switchtogames.changeText(switchtogamest);
//        topics.setBackground(Color.orange);
//        topics.setForeground(Color.black);
//startPR2010-03-03^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//        topics.setForeground(u.brown);
//endPRtoupdate^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      }
    }
    else { //Else 8 The game icons screen is not showing
//      if (!topics.isAncestorOf(mgameicons)) { //If 8.1
//        topics.remove(mtopictree); //mgameicons is not a menu item on the topics menu
//        topics.add(mgameicons);
//        topics.setText(mhome2);
//startPR2010-05-24^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        switchtogames.changeText(switchtogamesg);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//        topics.setBackground(Color.blue);
//        topics.setForeground(Color.yellow);
//startPR2010-03-03^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//        topics.setForeground(Color.blue);
//endPRtoupdate^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//        if(gameTree.root.isLeaf() && currPlayTopic !=null) {
 //         u.okmess("nogames", this); // rb 13/11/06 5/12/06
//        }
//      }
    }
    gameTree.setRootVisible(false);
    forcechange = false;
    if (gameicons) { //If 9 Games icons screen is showing
      gamePanel.haltrun = gamePanel.dontstart = false;
      gamePanel.startrunning();
    }
    wassuperlist = (usingsuperlist != null);
//startPR2010-05-24^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    bevelPanel2.validate();
    wordTree.setfont();
    setListTexts();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      if(MYSQLUpload.MYSQLGameFiltering){
          MYSQLUpload.gatherGames(gameTree);
      }
  }

  void setusephonics(boolean phonicschange){
      if(!wantplayprogram) {
        if(currPlayTopic.phonics && !currPlayTopic.phonicsw || currPlayTopic.blended
            || currPlayTopic.justphonics)
            wordTree.usephonics = true;
        else {
           if(!phonicschange && currPlayTopic.phonicsw && currPlayTopic.startphonics && currPlayTopic.phonicscourse)
            wordTree.usephonics = true;
          else if(!phonicschange && currPlayTopic.startnonphonics)
              wordTree.usephonics = false;
          else
              wordTree.usephonics =  currPlayTopic.phonicsw && student.option("s_usephonics");
          wordlist.phonemes.setSelected(wordlist.usephonics);
        }
      }
      else{  
          if (playprogram != null) {
            if(currPlayTopic.phonics && !currPlayTopic.phonicsw || currPlayTopic.blended
                  || currPlayTopic.justphonics) {
                wordTree.usephonics = true;
            }
            else {
     //             boolean tobesel = currPlayTopic.phonicsw && playprogram.it[playprogram.currstep].phonics;
                boolean tobesel =  playprogram.it[playprogram.currstep].phonics;
                if(studentList[currStudent].overriddenProgPhonics){
                    tobesel = !tobesel;
                }
                if(!currPlayTopic.phonicsw)tobesel = false;
                  wordTree.phonemes.setSelected(wordlist.usephonics =  tobesel);
              }
    //        simplesuper = !playprogram.hardsuper;
    //        simplesuper = false;
          }    
      }
  }



  
  public void refreshExtendedSample(){
                    wtab2.add(scroller, BorderLayout.CENTER);
                    wordTree.newselection(wordlist.EXTENDED);      
  }

//startPR2010-05-24^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  void setListTexts(){
      jnode jnsel = null;
      if((jnsel = topicList.getSelectedNode())==null)return;
    String nodetext = spellchange.spellchange(jnsel.get().replaceAll(topicTree.ISTOPIC, ""));
//    nodetext = nodetext.replaceAll("@", "");
    int k;
    if((k=nodetext.indexOf('@'))>=0){
        nodetext = nodetext.substring(0, k);
    }
    currentlist.setText(nodetext);
    if(currentlist.getFontMetrics(currentlist.getFont()).stringWidth(nodetext)>currentlist.getWidth()){
        currentlist.setToolTipText(nodetext);
    }
    else currentlist.setToolTipText(null);
    String sa[] = null;
    if(currPlayTopic!=null && currPlayTopic.teachingnotes!=null && jnsel.isLeaf())
        sa = spellchange.spellchange(currPlayTopic.teachingnotes);
    topicinfo.setVisible(sa!=null);
    topicList.validate();
    if(sa!=null){
        String s = u.combineString(sa).trim();
        String s2 = "";
        for(int i = 0; i < s.length(); i++){
            if(s.charAt(i)=='|')s2 += " ";
//            else if (s.charAt(i)!='^')s2 += s.charAt(i);
            else s2 += s.charAt(i);
        }
        topicinfo.setTextHtmlFormatted(nodetext+"||"+s2.trim(), treefont.deriveFont((float)treefont.getSize()-5));
    }
  }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   //------------------------------------------------------------------------------------------
   // RB 20/1/06 start
                      // this is called when we can't find the currently requested topic
                      // if we are in set work (wantplayprogram) then we give an error message,
                      // and remove set work an then we must rebuild the tree.
                      // otherwise we assume that adminstrators or their lists have changed since our
                      // topic tree was built - so we must rebuild the tree.
                      // return true if we rebuilt the tree(exit at once), else  false (to continue currPlayTopic).

   boolean tryrefresh() {
     if(wantplayprogram) {
       u.okmess("badprogram");
       db.deleteAll(sharkStartFrame.studentList[sharkStartFrame.currStudent].name,db.PROGRAM);
       student.getadmin();   // make sure up to date
       setupgames();
       return true;
     }
     else if(!refreshing) {
       student.getadmin();
       refreshing=true;
       setupgames();
       refreshing=false;
       return true;
     }
     return false;
   }
   // RB 20/1/06 end
  /**
   *Adds a label indicating that splits will show in the game unless the option is turned off.
   */
  void addsplitwarning() {
     wordTree.phonemes.setSelected(wordlist.usephonics);
//     splitwarning.setVisible(!wordTree.savesplits.isVisible());
     if (!currPlayTopic.superlist && wordTree.split && wordTree.splittot > 0 && !wordlist.usephonics) {
//       if (!bevelPanel2.isAncestorOf(splitwarning)) {
        wordTree.splitCB.setText(wordTree.splitchanged?wordTree.splitlab3:wordTree.splitlab2);
        wordTree.splitCB.setToolTipText(wordTree.splitchanged?wordTree.splittool3:wordTree.splittool2);
 //      wordTree.splitCB.setBackground(Color.magenta);
 //       grid2.weighty = 0;
 //       grid2.gridx = 0;
 //       grid2.gridy = 3;
 //       bevelPanel2.add(splitwarning, grid2, 3);
 //       bevelPanel2.validate();
 //       wordTree.setfont();
 //       wordTree.repaint();
 //     }
    }
 //   else {
 //     if (bevelPanel2.isAncestorOf(splitwarning)) {
 //       bevelPanel2.remove(splitwarning);
 //       bevelPanel2.validate();
 //       wordTree.setfont();
 //       wordTree.repaint();
 //     }
 //   }
  }

  void stripbannedgames() {
    student stu = studentList[currStudent];
    int i,j;
    String exs[] = stu.excludegames;
    exs = settings.getAllExcludeGames(exs);
    if (exs != null) {
      jnode c[] = gameTree.root.getChildren();
      for (i = 0; i < c.length; ++i) {
        if (u.findString(exs, c[i].get()) >= 0) {
          jnode parent = (jnode)c[i].getParent();
          gameTree.model.removeNodeFromParent(c[i]);
          if(parent.getChildCount()<1 && parent.getParent()!=null)
            gameTree.model.removeNodeFromParent(parent);
        }
        else {
          jnode c2[] = c[i].getChildren();
          for (j = 0; j < c2.length; ++j) {
            if (u.findString(exs, c2[j].get()) >= 0) {
              jnode parent = (jnode)c2[j].getParent();
              gameTree.model.removeNodeFromParent(c2[j]);
              if(parent.getChildCount()<1 && parent.getParent()!=null)
                gameTree.model.removeNodeFromParent(parent);
            }
          }
        }
      }
    }
  }
  /**
   * Creates the game tree which uses the restricted set of word lists
   * set up by an administrator
   */
  void creategametree() {
    if (wantplayprogram) {
      if(usingsuperlist != null) {
              currPlayTopic.teachingnotes =
                  u.splitString(u.gettext("superbutton", "teachingnotes2"));
              Timer tnTimer = new Timer(1000, new ActionListener() {
                public void actionPerformed(ActionEvent e) { //Event 7.1.1
                  addteachingnotes(true);
                }
              });
              tnTimer.setRepeats(false);
              tnTimer.start();
      }
      String ss[];
      if (playprogram == null || (ss = program.getgames(playprogram)) == null
          || ss.length == 0 || ss[0] == null) {
        gameTree.setup(publicGameLib,
                       true, true, "", currPlayTopic.phrases ? 3 :
                       (currPlayTopic.phonics && !currPlayTopic.phonicsw ? 1
                        : (wordTree.usephonics ? 2 : 0)));
        if(gameTree.root.getChildCount()==1) {
          jnode jj[] = ((jnode)gameTree.root.getChildAt(0)).getChildren();
          gameTree.root.removeAllChildren();
          for(int i=0;i<jj.length;++i)    gameTree.root.addChild(jj[i]);
          gameTree.model.reload();
        }
        stripbannedgames();
      }
      else {
        gameTree.setup(publicGameLib, true, true, "",currPlayTopic.phrases?3:
                       (currPlayTopic.phonics && !currPlayTopic.phonicsw ? 1
                       : (wordTree.usephonics? 2:0)));
        if(gameTree.root.getChildCount()==1) {
         jnode jj[] = ((jnode)gameTree.root.getChildAt(0)).getChildren();
         gameTree.root.removeAllChildren();
         for(int i=0;i<jj.length;++i)    gameTree.root.addChild(jj[i]);
         gameTree.model.reload();
        }
        gameTree.reduceGames(currPlayTopic);
        gameTree.addrecommended(currPlayTopic);
        program.keepselected(gameTree,playprogram,playprogram.currstep);
        stripbannedgames();       // start rb 3/2/08
                                                         // start rb 3/2/08
        if(gameTree.root.isLeaf() || gameTree.root.getChildCount()==1 && gameTree.root.getChildren()[0].isLeaf()) {
           if(studentList[currStudent].excludegames != null) {
             gameTree.setup(publicGameLib, true, true, "",currPlayTopic.phrases?3:
                             (currPlayTopic.phonics && !currPlayTopic.phonicsw ? 1
                             : (wordTree.usephonics? 2:0)));
             if(gameTree.root.getChildCount()==1) {
               jnode jj[] = ((jnode)gameTree.root.getChildAt(0)).getChildren();
               gameTree.root.removeAllChildren();
               for(int i=0;i<jj.length;++i)    gameTree.root.addChild(jj[i]);
               gameTree.model.reload();
             }
              gameTree.reduceGames(currPlayTopic);
              gameTree.addrecommended(currPlayTopic);
              program.keepselected(gameTree,playprogram,playprogram.currstep);
           }
           if(gameTree.root.isLeaf() || gameTree.root.getChildCount()==1 && gameTree.root.getChildren()[0].isLeaf()) {
              if(!(usingsuperlist!=null && usingsuperlist.superd.last3games.indexOf(topic.SPELLING)<0))
                playprogram.it[playprogram.currstep].games = new String[0];
              gameTree.setup(publicGameLib, true, true, "", currPlayTopic.phrases ? 3 :
                            (currPlayTopic.phonics && !currPlayTopic.phonicsw ? 1
                             : (wordTree.usephonics ? 2 : 0)));
             if(gameTree.root.getChildCount()==1) {
               jnode jj[] = ((jnode)gameTree.root.getChildAt(0)).getChildren();
               gameTree.root.removeAllChildren();
               for(int i=0;i<jj.length;++i)    gameTree.root.addChild(jj[i]);
               gameTree.model.reload();
             }
             stripbannedgames();       // start rb 3/2/08
            }
        }                                                   // end rb 3/2/08
      }
    }
    else
      gameTree.setup(searchListGame(overrideStudent()), true, true, "",currPlayTopic.phrases?3:
                        (currPlayTopic.phonics && !currPlayTopic.phonicsw ? 1
                       : (currPlayTopic.phonicsw && wordTree.usephonics? 2:0)));
     if(gameTree.root.getChildCount()==1) {
       jnode jj[] = ((jnode)gameTree.root.getChildAt(0)).getChildren();
       gameTree.root.removeAllChildren();
       for(int i=0;i<jj.length;++i)    gameTree.root.addChild(jj[i]);
       gameTree.model.reload();
     }
     gameTree.root.bgcolor = Color.pink;
  }

  /**
   *
   */
  void creategamepanel() {
    int i;
    if (gameicons) { //If 1 - game icon screen is displayed
      if (gamePanel == null) { //If 1.1
        gamePanel = new runMoversgame(); //-panel containing game icons does'nt exist
        gamePanel.setBackground(Color.white);
        gamePanel.usepool = true;
        gamePanel.addMouseListener(new java.awt.event.MouseAdapter() { 
          public void mouseEntered(MouseEvent e) {
            if (showteachingnotes != null) { 
              showteachingnotes.setVisible(false);
              showteachingnotes.dispose();
              showteachingnotes = null;
            }
          }
        });
        gamePanel.onmainscreen = true;
        gamePanel.savefixed = true;
        gamePanel.dontstart = true;
        gamePanel.start1();
        gamePanel.freeze = true;
        for (i = 0; i < gameiconlist.length; ++i) { //For 1.1.2
          if (gameiconlist[i] != null) //If 1.1.2.1
            gameiconlist[i].frozen = mnomove.getState();
        }
        gamePanel.setSize(new Dimension(screenSize.width * 3 / 8,
                                        screenSize.height));
        gamePanel.setBorder(null);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridx = 0;
        gbc.gridy = -1;
        gbc.insets = new Insets(20,0,20,0);
//        showallgamespanel.setBackground(col1);
//        showallgamespanel.setBorder(BorderFactory.createEmptyBorder());
        gbc.weighty = 0;
        gbc.insets = new Insets(0,0,0,0);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1;
        
    //    dp = new DragPanel(gamePanel);
        
        bevelPanel5.add(gamePanel, gbc);
        gbc.weighty = 0;
 //       bevelPanel5.add(showallgamespanel, gbc);
     //         dp.doLayout();
      }
      else { //Else 1.1- panel containing game icons does'nt exist
        for (i = 0; i < gamePanel.mtot; ++i) { //For 1.1.1
          if (! (gamePanel.m[i] instanceof sharkImage)) //If 1.1.1.1
              if(gamePanel.m[i]!=null)
                gamePanel.m[i].kill = true; // - The mover is not an instance of sharkImage
        }
      }
      gamePanel.dontstart = true;
 //     if (gametitle == null) buildgamepanel1x();
      if (resetGamesScreen) {
          buildgamepanel1x();
          gameTree.reduceGames(currPlayTopic);
      }
    }
    else { //Else 1
      stopGamepanel();
      if (!bevelPanel5.isAncestorOf(gameTree)) { //If 1.1
      }
    }
//    current.setVisible(!gameicons);
  }

  /**
   * @return true if there is a student in the studentList who can
   * override their word list when an administrator is logged on.
   */
  boolean override() {
    for (short i = 0; i < studentList.length; ++i) {
      if (studentList[i].override)
        return true;
    }
    return false;
  }

  /**
   * @return Position in array studentList of a student who can override their wordlists.
   */
  short overrideStudent() {
    for (short i = 0; i < studentList.length; ++i) {
      if (studentList[i].override)
        return i;
    }
    return currStudent;
  }

  /**
   * @param st indicates whether the current student's override is set
   * to true or false
   */
//  void setoverride(boolean st) {
//    for (short i = 0; i < studentList.length; ++i) {
//      studentList[i].override = false;
//    }
//    studentList[currStudent].override = st;
//  }

  public void displayhometitle(){
      if(Demo_base.isDemo)
          setTitle(starttitle = Demo_base.demogettext("democdprogramtitle", "programtitle", true));
      else{
          if (gameicons)
              setTitle(hometitle2);
          else
              setTitle(hometitle1);
      }
  }

  /**
       * Switches the display between the icon's screen and the topic display screen
   */
  public void switchdisplay() {
//startPR2010-05-24^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    bevelPanel1.removeAll();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    if(currStudent >=0) {           // rb 27/10/06
     if(studentList[currStudent].doupdates(true,false)) {
       setStudentMenu();
       setupgames();
     }
   }
    if(usingsuperlist!=null){
        topicListSelectionChanged(null);
    }
   creategamepanel();
   displayhometitle();
    if (gameicons) { //If 1 - game icon screen is displayed
      setTitle(hometitle2);
//startPR2010-05-24^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      bevelPanel5.setPreferredSize(new Dimension(screenSize.width * 2 / 3,
//                                                 screenSize.height));
//      grid1.weightx = 1;
//      bevelPanel1.add(bevelPanel5, grid1);
//      grid1.weightx = 0;
//      bevelPanel1.add(bevelPanel2, grid1);
      makegamesscreen();
      switchtogames.changeText(switchtogamest);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      buildgamepanel2(oriChooseTopicName!=null && oriChooseTopicName.equals(currPlayTopic.name));
      oriChooseTopicName = null;
//      topics.remove(mgameicons);
//      topics.add(mtopictree);
//      topics.setText(mhome1);
//startPR2008-09-25^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      topics.setBackground(Color.orange);
//      topics.setForeground(Color.black);
//      topics.setForeground(u.brown);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      gamePanel.requestFocus();
    }
    else { //Else 1 - game icon screen is not displayed
       oriChooseTopicName = currPlayTopic.name;
//startPR2010-05-24^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       switchtogames.changeText(switchtogamesg);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      setTitle(hometitle1);
      bevelPanel5.setPreferredSize(new Dimension(screenSize.width / 3,
                                                 screenSize.height));
//startPR2010-05-24^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      grid1.weightx = 1;
//      bevelPanel1.add(bevelPanel4, grid1);
//      grid1.weightx = 0;
//      bevelPanel1.add(bevelPanel2, grid1);
//      grid1.weightx = 1;
//      bevelPanel1.add(bevelPanel5, grid1);
      maketopicscreen();
 //     topicListSelectionChanged(null);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      topics.remove(mtopictree);
//      topics.add(mgameicons);
//      topics.setText(mhome2);
//startPR2008-09-25^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      topics.setBackground(Color.blue);
//      topics.setForeground(Color.yellow);
//      topics.setForeground(Color.blue);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    }
    studentList[overrideStudent()].gameicons = gameicons;
    bevelPanel1.validate();
//startPR2004-08-28^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    /*needed so that keyboard shortcuts consistently work on the Macintosh*/
    // if running on a Macintosh
//    if (shark.macOS)
      this.requestFocus();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    if (gameicons) {
      gamePanel.haltrun = gamePanel.dontstart = false;
      gamePanel.startrunning();
    }
//startPR2010-05-24^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    wordTree.setfont();
    bevelPanel1.repaint();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  }

  /**
   * Stops the panel that contains icons for playing games
   */
  void stopGamepanel() {
    if (gamePanel != null) {
      gamePanel.stop();
      bevelPanel5.removeAll();
      gamePanel = null;
    }
  }

  /**
   * <li>called when the program starts to run
   * <li>Sets up the game icons, title, tooltip_base and headings from the parameters that
   * are set within the running program.
   * <li>Sets up the arrays for game icon columns and rows.
       * <li>Causes  "hello " to be said as long as the student has this option chosen
   * and it has'nt been said already in the present program run.
   */
  void buildgamepanel1x() {    // done at start of run
    String s[], iconname;
    short i;
    int gametot;
 //   if(gametitle == null) {
    if(resetGamesScreen) {
      gameheadwidth = mover.WIDTH / 8;
      gamename = publicGameTree.titles();
      gameflags = new gameflag[gamename.length];
      gametot = gamename.length;
      gameiconlist = new sharkImage[gametot];
      resetGamesScreen = false;
//      gametitle = new String[gametot];
//      gamehdno = new short[gametot];
//      headings = new String[gametot];
      gamerect = new Rectangle[gametot];
      gametooltip = new String[gametot];
      for (i = 0; i < gametot; ++i) { //For 1
        gameflags[i] = new gameflag(gamename[i]);
        if ( (s = publicGameTree.getparms(gamename[i])) != null //If 1.1 - There are parameters set for the particular game
            && s.length > 0 //AND the length of those parameter is greater than 0
            && (iconname = publicGameTree.getparm(s, "icon")) != null) { //AND the icon parameter is set
          gameiconlist[i] = new sharkImage("i_" + iconname,false);
 //         gametitle[i] = publicGameTree.getparm(s, "icontitle");
          if (shark.macOS) {
            if (! (publicGameTree.getparm(s, "tooltip_mac").equals(""))) {
//              String tempString = u.formatCommandSymbol(publicGameTree.getparm(s, "tooltip_mac"));
              String tempString = publicGameTree.getparm(s, "tooltip_mac");
              gametooltip[i] = tempString;
            }
            else {
              gametooltip[i] = publicGameTree.getparm(s, "tooltip");
            }
          }
          // if running on Windows
          else {
            gametooltip[i] = publicGameTree.getparm(s, "tooltip");
          }
        }
      }
      donegameflags = true;
      
    }
    if (wanthello) { //If 7 - "Hello" is said when program starts to run
      Timer hTimer = new Timer(1000, new ActionListener() { //Listener 7.1
        public void actionPerformed(ActionEvent e) { //Event 7.1.1
//          String hellos[] = db.list(publicSoundLib[0], db.WAV, "hello");
          String hellos[] = db.list(getPrimarySoundDb(publicSoundLib), db.WAV, "hello");          
          if (hellos != null && hellos.length > 0) { //If 7.1.1.1
            spokenWord.findandsay(hellos[u.rand(hellos.length)]); // - There is a "hello" recorded in the publicSoundLib
          }
        }
      });
      hTimer.setRepeats(false);
      hTimer.start();
    }
    wanthello = false;
  }

  
  void buildgamepanel1() {
 //   if (gametitle == null) buildgamepanel1x();
    if(resetGamesScreen) 
        buildgamepanel1x();
    String headings[];
    gameheadings =  null;
    short i, j, k;
    int gametot;
    byte gamerow[];
    byte gamecol[];
    int sortorder[];
    gametot = gamename.length;
    gamerow = new byte[gametot];
    gamecol = new byte[gametot];
//    int gameww[] = new int[gametot];
    sortorder = new int[gametot];
    headings = new String[gametot];
    gameactive = new boolean[gametot];
    gamerect = new Rectangle[gametot];   // rb 4/12/07
    lastrect = -1;                                      // rb 4/12/07
            // the rest varies according to non-phonics/phonics sounds/phonics words
    if(currPlayTopic == null || currPlayTopic.phrases) phtype=3;
    else phtype = (byte)(currPlayTopic == null || !currPlayTopic.phonics || currPlayTopic.phonicsw && !wordlist.usephonics ? 0
              : (currPlayTopic.phonicsw ? 2:1));
    jnode n;
    String lasth = null,na,pa;
    int r=0,c=0,wwtot = 0;
    int sw = screenSize.width*2/3;
    headinggames = new ArrayList();
       if(!gameTree.root.isLeaf()) for(n = (jnode)gameTree.root.getFirstLeaf();n!=null;n=(jnode)n.getNextLeaf()) {
          na = n.get();
          pa = ((jnode)n.getParent()).get();
          if((pa == null || pa.trim().equals(""))){
              gamestoplay gameTree2 = new gamestoplay();
              gameTree2.setup(publicGameLib, true, true, "",phtype);
              jnode n2;
              if(!gameTree2.root.isLeaf()) {
                  for(n2 = (jnode)gameTree2.root.getFirstLeaf();n2!=null;n2=(jnode)n2.getNextLeaf()) {
                    if(!na.equals(n2.get()) || n2.getParent()==gameTree2.root) continue;
 //                   if( n2.getParent()==gameTree2.root) continue;
                    i = u.findString(gamename,na);
                    pa = ((jnode)n2.getParent()).get();
                    break;
                  }
              }
          }
          i = u.findString(gamename,na);
          if(i<0)
              continue;
//          if(gametitle[i] != null)
//              gameww[i]= Math.max(mover.WIDTH/8,treefontm.stringWidth(gametitle[i]+" ")*mover.WIDTH/sw);
          gameactive[i]= true;
          if(i>=0) {
              boolean found = false;
              for(int p = 0; p < headinggames.size();p++){
                  String ss[] = (String[])headinggames.get(p);
                  if(ss[0].equals(pa)){
                    found = true;
                    ss = u.addString(ss, na);
                    headinggames.set(p, ss);
                  }
              }
              if(!found)
                  headinggames.add(new String[]{pa, na});
             headings[i] = pa;
             if(lasth!=null) {
//               if (!lasth.equals(pa) || wwtot + gameww[i] > mover.WIDTH-gameheadwidth) { ++r; c = 0;wwtot =0; }
               if (!lasth.equals(pa)) { ++r; c = 0; }
               else {++c;}
             }
             lasth=pa;
//             wwtot+=gameww[i];
             gamerow[i] = (byte)r;
             gamecol[i] = (byte)c;
             sortorder[i] = (int) gamerow[i] * 100 + gamecol[i];
          }
       }
    short o[] = u.getorder(sortorder), headtot = 0;
    String lasthead = "";
    for (i = 0; i < gametot; ++i) { //For 2
      j = o[i];
//      if (headings[j] != null && gametitle[j] != null) { //If 2.1 - There is a title to go under the icon
      if (headings[j] != null) { //If 2.1 - There is a title to go under the icon
        if (gameactive[j] && !headings[j].equalsIgnoreCase(lasthead)) { //If 2.1.1
          lasthead = headings[j]; // - i is 0
          ++headtot; //  OR the heading being considered is not the same as the one considered before.
        }
      }
    }
    if(headtot == 0)return;
    gameheadings = new String[headtot];
    lasthead = "";
    for (i = 0, k = 0; i < gametot; ++i) { //For 4
      j = o[i];
 //     if (gameactive[j] && headings[j] != null && gametitle[j] != null) { //If 4.1 There is a title to go under the icon
      if (gameactive[j] && headings[j] != null) { //If 4.1 There is a title to go under the icon
        if (!headings[j].equalsIgnoreCase(lasthead)) { //If 4.1.1 - i equal to 0
          lasthead = gameheadings[k] = headings[j]; //OR the heading being considered is not the same as the one considered before.
          ++k;
        }
      }
    }
    System.gc();
  }

  /**
   *  <li>called when program already running
   *  <li>Creates movers for the game icons - both those which are active and inactive.
   */
  void buildgamepanel2(boolean samelist) {
    gamePanel.reset();
    headfont[0] = treefont;
    titfont[0] = treefont;
    int i, j;
    gamePanel.mtot = 0;
    // special screen for superlist restriction to spelling games
    gamePanel.clearWholeScreen =true;
markg = markg2 = null;
if(wantplayprogram  && !(playprogram.it[playprogram.currstep].games.length==0 && currPlayTopic.markgames() != null)) {
  String ss[] = gameTree.titlesinorder();
  for(i=0;i<ss.length;++i) {
    if((j=u.findString(gamename,ss[i])) >= 0 && (markg==null || !u.inlist(markg,j))) {
       markg = u.addint(markg,j);
       if(markg.length > 16) {
          markg = null;
          break;
       }
     }
  }
}
if(currPlayTopic != null && currPlayTopic.markgames() != null
       && (showmarkedgames && (!wantplayprogram || (playprogram!=null && playprogram.it[playprogram.currstep].games.length==0)))) {
  String mg[] = currPlayTopic.markgames();
  for (i = 0; i < mg.length; ++i) {
    if (gameTree.find(mg[i]) != null
        && (j = u.findString(gamename, mg[i])) >= 0
        && !student.banned(gamename[j])) {
      markg = u.addint(markg, j);
    }
  }
  if(currPlayTopic.markgames2() != null) {
    String mg2[] = currPlayTopic.markgames2();
    for (i = 0; i < mg2.length; ++i) {
      if (gameTree.find(mg2[i]) != null
          && (j = u.findString(gamename, mg2[i])) >= 0
          && !student.banned(gamename[j])
          && (markg==null || !u.inlist(markg, j))
          ) {
        markg2 = u.addint(markg2, j);
      }
    }
  }
}
    float arcsize = 0.08f;
    int backgroundw = (int)(mover.WIDTH*1.2);
    tabpanetop = (int)(mover.HEIGHT/7);
    int tabwidth = mover.WIDTH/8;
    int starttab = tabwidth/8;
    int tabno = (markg!=null)?gameheadings.length+2:gameheadings.length;
    int availabletabspace = mover.WIDTH - starttab - ((int)(backgroundw*arcsize)/2);
    gamePanel.removeAllMovers();
    gamePanel.mtot = 0;
    tabs.clear();
    int ht2 = tabpanetop*20/48;
    mover.gamesTab gt;
    tabstrings = new String[]{};
    gamestabfont = null;
    spellingonly =(usingsuperlist!=null
//       && !simplesuper
       && usingsuperlist.superd.last3games.indexOf(topic.SPELLING)<0);
    int activetab = 0;
     if(gameheadings.length > 0){
         tabwidth = Math.min(tabwidth, availabletabspace/tabno);
    if(!(wantplayprogram && playprogram.it[playprogram.currstep].games.length>0)){
        if(markg!=null)tabstrings = u.addString(tabstrings, str_recommended);
    }
        tabstrings = u.addString(tabstrings, u.halveText(gameheadings));
        if(!spellingonly && tabstrings.length>1 && 
             !gameTree.overrideSuperSpelling)
            tabstrings = u.addString(tabstrings, str_allavailable);
   
    tabrect = new Rectangle[tabstrings.length];
    String stab = studentList[currStudent].currTab;
    for(int k = 0; k < tabstrings.length; k++){
        gamePanel.addMover((gt=new mover.gamesTab(
            tabwidth, ht2,
            gamePanel.getBackground(), col1, col2, gettabcolor(tabstrings[k]), tabstrings[k])),
             starttab, tabpanetop-ht2);
        tabrect[k] = new Rectangle(starttab, tabpanetop-ht2, tabwidth, ht2);
        tabs.add(gt);
        if(stab!=null && stab.equals(u.combineString(u.splitString(tabstrings[k]), " ")) &&
               ( markg==null || samelist))
            activetab = k;
        starttab += tabwidth;
    }
    for(int k = 0; k < tabs.size(); k++){
        gt = (mover.gamesTab)tabs.get(k);
        gt.setActive(k==0?true:false);
    }
  }
    gamePanel.addMover(new mover.roundrectMover(backgroundw,mover.HEIGHT, col1,  col1, arcsize),
             (mover.WIDTH)-(int)(mover.WIDTH*1.2),
            tabpanetop);
    int ht3 = tabpanetop*17/48;
    gamePanel.addMover(new mover.rectMover(new Rectangle(mover.WIDTH,ht3),
           topbarcol, topbarcol),0,0);
    // set all as the default tab
    if(wantplayprogram && playprogram.it[playprogram.currstep].games.length>0)
        activetab=tabstrings.length-1;
    changetab(activetab);
    int ht4 = ht3/6;
    currUserPic = new mover.picandname(studentList[currStudent].name,
            gamePanel,
            ht3-ht4,mover.picandname.MODE_CURRUSER);
    gamePanel.addMover(currUserPic,ht4/2,ht4/2);
    if(sharkStartFrame.studentList.length>1){
        sharedp = new sharedplay(0.5f, ht3-ht4);
        backsharerect = new mover.roundrectMover(1, 1, topbarcol, topbarcol,15);
        sharedp.viewshares = false;
        backsharemargin = ht3/2;
        int xx = ht4/2 + currUserPic.x + currUserPic.w + (ht3/2);
        gamePanel.addMover(backsharerect, xx-backsharemargin, 0);
        gamePanel.addMover(sharedp,xx,ht4/2);
        for(int n = 0; sharewith!=null && n < sharewith.size(); n++){
              String ss[] = (String[])sharewith.get(n);
              if(ss[0].equals(studentList[currStudent].name)){
                 sharedp.change(ss[1], mover.picandname.MODE_OTHERUSERCHOSEN);
                  break;
              }
        }
    }
    if(isGameTreeEmpty()) {              // rb 13/11/06  5/12/06
        int w = mover.WIDTH*7/12;
         mover.simpletextmover tt = new mover.simpletextmover(u.gettext("nogames","message"),w,mover.HEIGHT/4);
         tt.bg = sharkStartFrame.col1;       
         tt.fg = Color.white;
//         gamePanel.addMover(tt,mover.WIDTH/8,mover.HEIGHT/4);
         gamePanel.addMover(tt,(mover.WIDTH/2)-(w/2),tabpanetop+((mover.HEIGHT-tabpanetop)/2)-mover.HEIGHT/4);
    }
  }



  boolean isGameTreeEmpty(){
      return (gameTree.root.isLeaf() && currPlayTopic !=null);
  }

  /**
   * @param e Action event
   */
  void studentrecord_actionPerformed(ActionEvent e) {
//startPR2007-03-06^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    new student.showStudentRecord(studentList[currStudent]);
    new student.showStudentRecord(sharkStartFrame.mainFrame, studentList[currStudent]);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  }

  Color gettabcolor(String s){
      s = s.replace('|', ' ');
          for(int k = 0; k < tabcolors.length; k++){
              String s2 = tabcolors[k].substring(0, tabcolors[k].indexOf(":"));
               if(s2.equals(s)){
                   s2 = tabcolors[k].substring(tabcolors[k].indexOf(":")+1);
                   if(s2.equals("x_COL1"))return new Color(255,0,0);  // red
                   else if(s2.equals("x_COL2"))return new Color(0,0,255);  // darker blue
                   else if(s2.equals("x_COL3"))return new Color(142,160,250); // light blue
                   else if(s2.equals("x_COL4"))return new Color(128,0,0);  //brown
                   else if(s2.equals("x_COL5"))return new Color(255,243,1);  // yellow
                   else if(s2.equals("x_COL6"))return new Color(255,130,5);  // orange
                   else if(s2.equals("x_COL7"))return new Color(102,204,51); // green
                   else if(s2.equals("x_COL8"))return new Color(205,172,129);  // light brown
              }
          }
      return null;
  }


  /**
   * Finds and replaces words in the topics.
   * <li>Displays a dialogue which is used to input a word to be found in the topics
   * <li>Displays a dialogue which is used to input a word to replace the chosen word
   * <li>Displays a message indicating which topics have had words replaced
   */
  void findreplace() {
    String find = JOptionPane.showInputDialog("Find:"), ss;
    if (find == null || find.length() == 0) //If 1
      return; //Nothing has been entered in the dialogue box
    int i, j, k, len = find.length(), changetot = 0, topictot = 0;
    saveTree1 st;
    String t[] = db.list(topic.publictopics, db.TOPIC);
    if (find.equalsIgnoreCase("strip")) {
      int spacetot = 0;
      loopi:for (i = 0; i < t.length; ++i) { //For 2
        int stripspaces = 0;
        st = (saveTree1) db.find(topic.publictopics, t[i], db.TOPIC);
        for (j = 0; j < st.curr.names.length; ++j) {
          if (st.curr.names[j].length() == 0 ||
              st.curr.names[j].charAt(0) == '\\'
              || st.curr.names[j].charAt(0) == topicTree.ISTOPIC.charAt(0)
              || st.curr.names[j].charAt(0) == topicTree.ISPATH.charAt(0)
              )
            continue;
          ss = u.stripspaces2(st.curr.names[j]);
//          if(ss.indexOf('=')>=0 && ss.indexOf('/')>=0 && ss.indexOf(' ')<0) ss = u.stringreplace(ss,'/',u.phonicsplit);
          if (ss != st.curr.names[j]) {
            ++stripspaces;
            ++spacetot;
            st.curr.names[j] = ss;
          }
        }
        if (stripspaces > 0) { //If 5.2
          db.update(topicTree.publictopics, t[i], st.curr, db.TOPIC); //A topic has been changed
        }
      }
//startPR2004-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//         u.okmess("Search for spaces in topics", spacetot + " spaces found");
         u.okmess("Search for spaces in topics", spacetot + " spaces found", sharkStartFrame.mainFrame);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      return;
    }
    boolean onlylistgames =  false;
    onlylistgames = u.yesnomess("Is a game list?", "Is this change just for game lists|i.e. markgames, notgames etc?", sharkStartFrame.mainFrame);
    boolean isgamechange =  false;
    if(!onlylistgames)
        isgamechange = u.yesnomess("Is a game?", "Is this a game name change?|(i.e. will affect teaching notes and markgames, notgames etc)", sharkStartFrame.mainFrame);
    boolean visualon = u.yesnomess("See?", "Do you want to see (and be able to decide on) the change as we go along?", sharkStartFrame.mainFrame);

    String exceptionList[] = null;
//    String exceptionList[] = new String[]{"find picture (for sentence)","find picture (from written)","find picture (phonics)", "find picture (vocabulary)" };
    String names[] = new String[0];
    loopi:for (i = 0; i < t.length; ++i) { //For 2
      st = (saveTree1) db.find(topic.publictopics, t[i], db.TOPIC);
      for (j = 0; j < st.curr.names.length; ++j) { //For 2.1
          
          if(st.curr.names[j].startsWith(topic.types[topic.GAMEOPTIONS])){
              if(st.curr.names[0].equals("alphabet sounds mixed")){
                  int h;
                   h = 0;
              }
            if(st.curr.names[j].startsWith(topic.types[topic.GAMEOPTIONS]+"maze alter")){
                int h;
                 h = 0;
            }
          }
          
     //           for picking up Ruth's mistyping of “/•�? instead of  “•/�?.
//                if(st.curr.names[j].indexOf("/"+u.phonicsplits)>=0){
//                    System.out.println(st.curr.names[0]);
//                    System.out.println(st.curr.names[j]);
//                    System.out.println("    ");
//              }
          
          /*
if(st.curr.names[j].indexOf("*")>=0){    // look for the letter pattern filters
    
    char c[] = st.curr.names[j].toCharArray();
    for(int aa = 0; aa < c.length; aa++){
        if(Character.isUpperCase(c[aa])){
            int g;
             g  = 0;
       //      System.out.println(st.curr.names[0] + "            " + st.curr.names[j]);
        }
    }
       
        }
           */
          
          
          
        if(st.curr.names[j].startsWith(topic.types[topic.GAMEOPTIONS])){
            int kk = st.curr.names[j].indexOf(',');      
            if((st.curr.names[j].substring(topic.types[topic.GAMEOPTIONS].length(), kk).indexOf(find)) < 0)
                continue;
        }
          
        if ( (k = st.curr.names[j].indexOf(find)) >= 0) { //If 2.1.1
          if(exceptionList!=null){
              for(int p = 0; p < exceptionList.length; p++){
                   if(st.curr.names[j].substring(k).startsWith(exceptionList[p]))
                       continue loopi;
              }
          }
          if(isgamechange &&
                  (
                  !st.curr.names[j].startsWith("\\") &&
                  !(st.curr.levels[j] == 1 && st.curr.names[j].startsWith("Games:")) &&
                  !(st.curr.levels[j] == 1 && st.curr.names[j].startsWith("Mark games:")) &&
                  !(st.curr.levels[j] == 1 && st.curr.names[j].startsWith("Game options:")) &&
                  !(st.curr.levels[j] == 1 && st.curr.names[j].startsWith("Mark games 2:")) &&
                  !(st.curr.levels[j] == 1 && st.curr.names[j].startsWith("Not games:"))
                  )
                  )
              continue loopi;
          if(onlylistgames){
              if((
                      !(st.curr.levels[j] == 1 && st.curr.names[j].startsWith("Games:")) &&
                      !(st.curr.levels[j] == 1 && st.curr.names[j].startsWith("Mark games:")) &&
                      !(st.curr.levels[j] == 1 && st.curr.names[j].startsWith("Game options:")) &&
                      !(st.curr.levels[j] == 1 && st.curr.names[j].startsWith("Mark games 2:")) &&
                      !(st.curr.levels[j] == 1 && st.curr.names[j].startsWith("Not games:"))
                      )
                      ){
                  continue loopi;
              }
              else {
                      char after = 'a';
                      boolean atend = false;
                      if(st.curr.names[j].length()<=k+find.length())
                          atend = true;
                      else{
                          after = st.curr.names[j].charAt(k+find.length());
                      }
                     if(!atend && after!=',' && after!='^')
                          continue loopi;
                      if(k<0){
                          char before = st.curr.names[j].charAt(k-1);
                          if(before!=':' && before!=',')
                              continue loopi;
                  }
              }
          }
          if(visualon){
                boolean wantchange =u.yesnomess("Confirm?", "Do you want this change?||"+st.curr.names[j], sharkStartFrame.mainFrame);
 //               System.out.println(st.curr.names[j] + "     "  +   st.curr.names[0]  );
                if(!wantchange)continue loopi;
          }

          names = u.addString(names, t[i]); //The chosen word is in this topic
          continue loopi;
        }
      }
    }
    if (names.length == 0) { //If 3
//startPR2004-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//        u.okmess("Search for :"+find + " in topics", "Not found");
        u.okmess("Search for :"+find + " in topics", "Not found", sharkStartFrame.mainFrame);//-Chosen word was not found and so there is nothing in names
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      return; // there is nothing in names
    }
    else { //Else 3 -Chosen word was found and so
      u.showlist("Occurrences of " + find, names); // there is something in names
    }
    String rep = JOptionPane.showInputDialog("Replace with:");
    if (rep == null) //If 4 - Nothing entered into the "Replace with" dialogue
      return;
    t = names;
    for (i = 0; i < t.length; ++i) { //For 5
      boolean changed = false;
      st = (saveTree1) db.find(topic.publictopics, t[i], db.TOPIC);
      loopo: for (j = 0; j < st.curr.names.length; ++j) { //For 5.1
        if(st.curr.names[j].startsWith(topic.types[topic.GAMEOPTIONS])){
            int kk = st.curr.names[j].indexOf(',');      
            String sgo = st.curr.names[j].substring(topic.types[topic.GAMEOPTIONS].length(), kk);
            if(sgo.indexOf(find)>=0){
                sgo = sgo.replace(find, rep);
                st.curr.names[j] = topic.types[topic.GAMEOPTIONS]+sgo+st.curr.names[j].substring(kk);
                changed = true;
                ++changetot;                
            }   

        }   
        else if ( (k = st.curr.names[j].indexOf(find)) >= 0) { //If 5.1.1
          st.curr.names[j] = st.curr.names[j].substring(0, k) //There is an occurance of the input (find)in the array
              + rep
              + ( ( (k += len) < st.curr.names[j].length()) ?
                 st.curr.names[j].substring(k) : "");
          changed = true;
          ++changetot;
        }
      }
      if (changed) { //If 5.2
        db.update(topicTree.publictopics, t[i], st.curr, db.TOPIC); //A topic has been changed
        topictot++;
      }
    }
    u.okmess("Topic find and replace", "Changed "
//startPR2004-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//       + String.valueOf(changetot) + " lines in " + String.valueOf(topictot) + " topics");
       + String.valueOf(changetot) + " lines in " + String.valueOf(topictot) + " topics", sharkStartFrame.mainFrame);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  }
  
  
  
  
  /**
   * Various options are offered for printing: -
   * <li>The topic can be printed
   * <li>The topic tree can be printed
   * <li>The word list can be printed
   * @param e Action event
   */
  

  void print_actionPerformed(ActionEvent e) {

    if(!playingGames && !showingPictures ) {
        new TopicsPrint(topicTreeList);
    }  

      
      /*

    String outputFolderPlus = sharkStartFrame.sharedPathplus+ToolsOnlineResources.outputFolder+shark.sep;
    new File(outputFolderPlus).mkdir();
    saveTree1 st;
    int i,j,k,m,n;
    if(!playingGames && !showingPictures ) {                               //If 1
     jnode sel[] = topicTreeList.getSelectedNodes();//The games are not playable AND pictures will not show when words are spoken
     if(sel==null || sel.length==0)                                      //If 1.1
       return;                                                     //There are no selected nodes
     
     
     
     /// something that helped find headings / sentences?
     boolean thething = false;
     
     
     
     
     if(!thething && u.yesnomess("Ascii listing of tree",                               //If 1.2
//startPR2004-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//                    "Save TREE into file called 'topictree' ??")) {
       "Save TREE into file called 'topictree' ??", sharkStartFrame.mainFrame)) { //The file is to be saved
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       String s;
       boolean includeWords = u.yesnomess("Ascii listing of tree",                 
                "Include standard and extended words?", sharkStartFrame.mainFrame);
       if(sel != null && sel.length >0) try {//If 1.2.1 - TopicTreeList has a selected node
         FileOutputStream f = new FileOutputStream(outputFolderPlus+"topictree.txt");
         for(n=0;n<sel.length;++n) {                                      //For 1.2.1.1
             f.write((byte)'\r');
             f.write((byte)'\n');
             f.write(("----------------  " +  sel[n].get() + "  ----------------").getBytes());
             f.write((byte)'\r');
             f.write((byte)'\n');
             f.write((byte)'\r');
             f.write((byte)'\n');
             st = new saveTree1(topicTreeList,sel[n]);
             for(j=1;j<st.curr.names.length;++j) {                        //For 1.2.1.1.1
                s = st.curr.names[j];
                for(k=0;k<st.curr.levels[j];++k) {                        //For 1.2.1.1.1.1
                   f.write((byte)' ');
                   f.write((byte)' ');
                }
                while((m = s.indexOf(topicTree.ISPATH))>=0)             //While 1.2.1.1.1.2
                       s = s.substring(0,m) + ">>" + ((m<s.length()-1)?s.substring(m+1):"");
                while((m = s.indexOf(topicTree.ISTOPIC))>=0)            //While 1.2.1.1.1.3
                       s = s.substring(0,m) + ">>" + ((m<s.length()-1)?s.substring(m+1):"");
                f.write(s.getBytes());
                f.write((byte)'\r');
                f.write((byte)'\n');
                if(includeWords && s.startsWith(">>")){
                    topic t =  new topic(u.absoluteToRelative(sharkStartFrame.publicTopicLib[0]),
                                   s.substring(2),null,null);
                    if(t!=null){
                        t.getWords(null,false);
                        word stand[] = t.getAllWords(false);
                        word ext[] = t.getAllWords(true);
                        String standardwords[] = new String[]{};
                        for(int aa = 0; aa < stand.length; aa++){
                            String sss = stand[aa].v();
                            if(u.findString(standardwords, sss)<0 && !sss.startsWith("\\"))
                                standardwords = u.addString(standardwords, stand[aa].v());
                        }                    
                        String extendedwords[] = new String[]{};
                        for(int aa = 0; aa < ext.length; aa++){
                            String sss = ext[aa].v();
                            if(u.findString(standardwords, sss)<0 && u.findString(extendedwords, sss)<0 && !sss.startsWith("\\"))
                                extendedwords = u.addStringSort(extendedwords, ext[aa].v());
                        }             
                        for(int aa = 0; aa < standardwords.length; aa++){
                            for(k=0;k<st.curr.levels[j]+1;++k) {
                                f.write((byte)' ');
                                f.write((byte)' ');
                            }
                            f.write(standardwords[aa].getBytes());      
                            f.write((byte)'\r');
                            f.write((byte)'\n');                        
                        } 
                        for(int aa = 0; aa < extendedwords.length; aa++){
                            for(k=0;k<st.curr.levels[j]+1;++k) {
                                f.write((byte)' ');
                                f.write((byte)' ');
                            }
                            if(aa==0){
                              f.write((byte)'*');
                              f.write((byte)'\r');
                              f.write((byte)'\n');
                              for(k=0;k<st.curr.levels[j]+1;++k) {
                                  f.write((byte)' ');
                                  f.write((byte)' ');
                              }
                            }                        
                            f.write((byte)' ');
                            f.write((byte)' ');
                            f.write(extendedwords[aa].getBytes());
                            f.write((byte)'\r');
                            f.write((byte)'\n');
                        }                    
                    
                    }
                }  
             }
             f.write((byte)'\r');
             f.write((byte)'\n');
         }
         f.close();
       }
       catch(IOException e1) {
          return;
       }
     }
     else if(thething || u.yesnomess("Ascii listing of whole topics",             //Else if 1.2
//startPR2004-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//                         "Save TOPICS into file called 'topics' ?")) {
       "Save TOPICS into file called 'topics' ?", sharkStartFrame.mainFrame)) {//Topic is to be saved
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       boolean standard = false,teachnotes = false,extended = false,buckets = false;
       boolean simple=false,hard=false,simplecross=false;
//startPR2008-06-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       boolean cutphonics = false;
       boolean pairs=true,brackets=true,notgames=true,markgames=true,startphonics=true,startnonphonics=true,
           gameoptions=true,phonicdistractors=true,justphonics=true,homophones=true,selecton=false,
           allornone=true,blends=true,removeexclamation=false,selectgroups=true,inorder=true, onlystandard = false;
              if(!thething &&  u.yesnomess("Ascii listing of whole topics",
                       "Print only standard list of words for each topic ?", sharkStartFrame.mainFrame)) {
         markgames=false; startphonics=false;
         brackets=false;
         standard = true;
         pairs=false;
         notgames=false;
         gameoptions=false;
         phonicdistractors=false;
         justphonics=false;
         cutphonics = true;
         homophones=false;
         extended = false;
         allornone=false;
         blends=false;
         removeexclamation=true;
         startnonphonics=false;
         selectgroups=false;
         onlystandard = true;
         inorder=false;
       }       
       else if(!thething &&  u.yesnomess("Ascii listing of whole topics",
                       "Print a SIMPLE list of words for each topic ?", sharkStartFrame.mainFrame)) {
         markgames=false; startphonics=false;
         brackets=false;
         standard = true;
         pairs=false;
         notgames=false;
         gameoptions=false;
         phonicdistractors=false;
         justphonics=false;
         cutphonics = true;
         homophones=false;
         extended = true;
         allornone=false;
         blends=false;
         removeexclamation=true;
         startnonphonics=false;
         selectgroups=false;
         inorder=false;
       }
       else{
           if(!thething){
               if(u.yesnomess("Ascii listing of whole topics","Save STANDARD words?", sharkStartFrame.mainFrame))//If 1.2.1
                 standard = true;                                        // - Standard words to be saved.
               if(u.yesnomess("Ascii listing of whole topics","Save EXTENDED words?", sharkStartFrame.mainFrame))//If 1.2.2
                 extended = true;                                        // - Extended words to be saved.
               if(u.yesnomess("Ascii listing of whole topics","Save SIMPLE CROSSWORD sentences?", sharkStartFrame.mainFrame))//If 1.2.3
                 simplecross = true;                                          // - Simple sentences to be saved.
               if(u.yesnomess("Ascii listing of whole topics","Save CROSSWORD 1 sentences?", sharkStartFrame.mainFrame))//If 1.2.3
                 simple = true;                                          // - crossword 1 sentences to be saved.
               if(u.yesnomess("Ascii listing of whole topics","Save CROSSWORD 2 sentences?", sharkStartFrame.mainFrame))//If 1.2.4
                 hard = true;                                            // - Hard sentences to be saved.
               if(u.yesnomess("Ascii listing of whole topics","Save words under 'Games:' headings?", sharkStartFrame.mainFrame))
                 buckets = true;                        //If 1.3.5 - words saved under games headings
               if(u.yesnomess("Ascii listing of whole topics","Save teaching notes?", sharkStartFrame.mainFrame))//If 1.2.6
                 teachnotes = true;                                      // - Teaching notes saved
           }
           else{
                standard = false;
                extended = false;
                simplecross = false;
                simple = false;
                hard = false;
                buckets = false;
                markgames = false;
                notgames = false;
                gameoptions = false;
                teachnotes = false; 
           }
       }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       String s;
       if(sel != null && sel.length >0) try {    //If 1.2.7 -  There is a selected node
         FileOutputStream f = new FileOutputStream(sharedPathplus+"topics.txt");
//---------------------------thethingstart--------------------------------------      
         // for thething
            String badSentences[] = new String[]{}; 
            String simpleSentArray[] = new String[]{};   
            String simpleSentArrayTopics[] = new String[]{};
            String standSentArray[] = new String[]{};
            String standSentArrayTopics[] = new String[]{};
            String headingArray[] =new String[]{};
            String headingArrayTopics[] =new String[]{};      
            String captionsArray[] =new String[]{};
            String captionsArrayTopics[] =new String[]{};
            String lastgames = null;
            String okheadinggames[] = new String[]{"pattern", "snakes + ladders", "helicopter"};
            String head = "Heading:";
            simplecross = true;
            simple  = true;
            buckets  = true;            
//---------------------------thethingend----------------------------------------          
         for(n=0;n<sel.length;++n) {                                      //For 1.2.7.1
           topic topics[] = topicTreeList.getTopics(sel[n]);
           for(i=0; i<topics.length; ++i) {                               //For 1.2.7.1.1
//---------------------------thethingstart-------------------------------------- 
        word w[] = topics[i].getAllWords(true, true);
        if(topics[i].phrases){
                   for(int jj = 0; jj < w.length; jj++){
                       String sword = w[jj].value;
                       
                       if(u.findString(captionsArray, sword) < 0){
                        captionsArray = u.addString(captionsArray, sword);
                        captionsArrayTopics = u.addString(captionsArrayTopics, topics[i].name);                             
                       }
                   } 
        }
//---------------------------thethingend----------------------------------------                    
             f.write((byte)'\r');
             f.write((byte)'\n');
             f.write(("----------------  " +  topics[i].name + "  ----------------").getBytes());
             f.write((byte)'\r');
             f.write((byte)'\n');
             f.write((byte)'\r');
             f.write((byte)'\n');
             st = new saveTree1(topics[i],topics[i].root);
// start rb 11/6/05 -------------------------------------------------------------------------------------------------------
//startPR2008-06-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
             int p;
             String standardlist[] = new String[]{};
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
             for(j=1;j<st.curr.names.length;++j) {                        //For 1.2.7.1.1.1
                 
               boolean badsentence = false; 
               if(st.curr.levels[j]==1) {                                    //If 1.2.7.1.1.1.1
//startPR2008-06-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                 if(st.curr.names[j].startsWith("Select words:")){
                   selecton = true;
                 }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                 if(st.curr.names[j].startsWith("\\")) {
                   if(!teachnotes)  continue;
                 }
                 else if(
//startPR2008-06-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                      selecton||
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                      !buckets && st.curr.names[j].startsWith("Games:")
                         && !st.curr.names[j].startsWith("Games:simple crossword")
                         && !st.curr.names[j].startsWith("Games:crossword 1")
                         && !st.curr.names[j].startsWith("Games:crossword 2")
                      || !simplecross && st.curr.names[j].startsWith("Games:simple crossword")
                      || !simple && st.curr.names[j].startsWith("Games:crossword 1")
                      || !hard && st.curr.names[j].startsWith("Games:crossword 2")
//startPR2008-06-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                      || !pairs && st.curr.names[j].startsWith("Pair:")
                      || !brackets && st.curr.names[j].startsWith("(") && st.curr.names[j].endsWith(")")
                      || !notgames && st.curr.names[j].startsWith("Not games:")
                      || !markgames && st.curr.names[j].startsWith("Mark games")
                      || !startphonics && st.curr.names[j].startsWith("Startphonics:")
                      || !gameoptions && st.curr.names[j].startsWith("Game options:")
                      || !phonicdistractors && st.curr.names[j].startsWith("Phonicdistractors:")
                      || !justphonics && st.curr.names[j].startsWith("Justphonics:")
                      || !homophones && st.curr.names[j].startsWith("Homophones:")
                      || !allornone && st.curr.names[j].startsWith("All or none:")
                      || !blends && st.curr.names[j].startsWith("Blends:")
                      || !startnonphonics && st.curr.names[j].startsWith("Startnonphonics:")
                      || !selectgroups && st.curr.names[j].startsWith("Select groups:")
                      || !inorder && st.curr.names[j].startsWith("Inorder:")
                      || (
                         onlystandard && 
                         (st.curr.names[j].startsWith("APNotInTest")
                         || st.curr.names[j].startsWith("APNotInUnitOrTest")
                         || st.curr.names[j].startsWith("APPriority1")
                         || st.curr.names[j].startsWith("APPriority2")
                         || st.curr.names[j].startsWith("Revise:"))
                         )
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                      || !standard
                         && (j==st.curr.names.length-1
                             || st.curr.levels[j+1]==1
                             || st.curr.names[j].startsWith("Pair:")
                      || !extended  && !st.curr.names[j].startsWith("Games:")
                            && !st.curr.names[j].startsWith("Pair:")
                            && j<st.curr.names.length-1 && st.curr.levels[j+1]>1)) {
//startPR2008-06-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//                    while(++j<st.curr.names.length && st.curr.levels[j]>1);//While 1.2.7.1.1.1.1.1
                    String extendedwords[] = new String[]{};
                    if(!selectgroups && st.curr.names[j].startsWith("Select groups:"))
                      while(++j<st.curr.names.length && st.curr.levels[j]>1);
                    else{
                      while (++j < st.curr.names.length && st.curr.levels[j] > 1) {
                        if (selecton) {
                          if (
                              !st.curr.names[j].startsWith("Select words:") &&
                              !st.curr.names[j].startsWith(topicTree.ISPATH) &&
                       //       !st.curr.names[j].startsWith(topicTree.ISTOPIC) &&
                              !st.curr.names[j].startsWith(">>") &&
                              !st.curr.names[j].startsWith("Pair:") &&
                              !st.curr.names[j].startsWith("Phonicdistractors:") &&
                              !st.curr.names[j].startsWith("*") &&
                              !st.curr.names[j].startsWith("Courses:") &&
                              !st.curr.names[j].startsWith("All or none:")
                              ) {
//---------------------------thethingstart-------------------------------------- 
                              // pick up the other lists referenced in the extended list
                            if(st.curr.names[j].trim().startsWith(topicTree.ISTOPIC)){
                                topic reftop = topic.findtopic(st.curr.names[j]);
                                word ww[] = reftop.getAllWords(reftop.wantextend);
                                for(int jj = 0; jj < ww.length; jj++){
                                   s = ww[jj].value;
                                   if (s.indexOf(u.phonicsplit) >= 0) {
                                     if ( (m = s.indexOf("=")) >= 0) s = s.substring(0, m);
                                     s = u.strip(s, u.phonicsplit);
                                   }
                                   if (cutphonics && (p = s.indexOf("=")) >= 0) {
                                     s = s.substring(0, p);
                                   }
                                   if(u.findString(standardlist, s)<0)
                                     extendedwords = u.addStringSort(extendedwords, s);                                    
                                }
                            }
                            else{                              
//---------------------------thethingend---------------------------------------- 
                            if(st.curr.names[j].trim().startsWith("Select groups:")){
                              while (++j < st.curr.names.length && st.curr.levels[j] > 1);
                              break;
                            }
                            s = st.curr.names[j];
                            if (s.indexOf(u.phonicsplit) >= 0) {
                              if ( (m = s.indexOf("=")) >= 0) s = s.substring(0, m);
                              s = u.strip(s, u.phonicsplit);
                            }
                            if (cutphonics && (p = s.indexOf("=")) >= 0) {
                              s = s.substring(0, p);
                            }
                            if(u.findString(standardlist, s)<0)
                              extendedwords = u.addStringSort(extendedwords, s);
                          }
                        }
                      }
//---------------------------thethingstart-------------------------------------- 
                      }
//---------------------------thethingend---------------------------------------- 
                      for(p = 0; extended && p < extendedwords.length; p++){
                        if(p==0){
                          f.write((byte)' ');
                          f.write((byte)' ');
                          f.write((byte)'*');
                          f.write((byte)'\r');
                          f.write((byte)'\n');
                        }
                        f.write((byte)' ');
                        f.write((byte)' ');
                        f.write((byte)' ');
                        f.write((byte)' ');
                        f.write(extendedwords[p].getBytes());
                        f.write((byte)'\r');
                        f.write((byte)'\n');
                      }
                      selecton=false;
                    }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                    --j;
                    continue;
                 }
                }
                s = st.curr.names[j];
//---------------------------thethingstart-------------------------------------- 
                if(s.indexOf("Games:")>=0){
                    lastgames = s;
                }
                boolean isSimpleSentence = lastgames.indexOf(topic.sentencegames2[2])>=0;
                boolean isNormalSentence = lastgames.indexOf(topic.sentencegames2[0])>=0;
                boolean isHardSentence = lastgames.indexOf(topic.sentencegames2[1])>=0;
//---------------------------thethingend---------------------------------------- 
                if((simple||simplecross) && !hard && !teachnotes && !standard && !extended
                   && s.indexOf("Games:") < 0                              //If 1.2.7.1.1.1.1.2
                   && (new sentence(s,null)).type  != sentence.SIMPLECLOZE
                          &&     (new sentence(s,null)).type  != sentence.CLOZE  
                       && (isSimpleSentence || isNormalSentence)
                        ) {
                        String str = "Topic:   " +  topics[i].name +    "     BS:    "+s;
                        if(u.findString(badSentences, str)<0)
                            badSentences = u.addString(badSentences, str);
                      badsentence = true; 
                      f.write(("not cloze").getBytes());
                      f.write((byte)'\r');
                      f.write((byte)'\n');
                }
                if(!simple && !simplecross && hard && !teachnotes && !standard && !extended//If 1.2.7.1.1.1.1.3
                   && (new sentence(s,null)).type  == sentence.SIMPLECLOZE) {
                      noise.beep();
                      f.write(("not hard").getBytes());
                      f.write((byte)'\r');
                      f.write((byte)'\n');
                }
                for(k=0;k<st.curr.levels[j];++k) {                        //For 1.2.7.1.1.1.1.4
                   f.write((byte)' ');
                   f.write((byte)' ');
                }
                while((m = s.indexOf(topicTree.ISPATH))>=0)             //While 1.2.7.1.1.1.1.5
                       s = s.substring(0,m) + ">>" + ((m<s.length()-1)?s.substring(m+1):"");
                while((m = s.indexOf(topicTree.ISTOPIC))>=0)            //While 1.2.7.1.1.1.1.6
                       s = s.substring(0,m) + ">>" + ((m<s.length()-1)?s.substring(m+1):"");
                if(s.indexOf(u.phonicsplit)>=0) {
                   if((m = s.indexOf("="))>=0) s = s.substring(0,m);
                   s = u.strip(s,u.phonicsplit);
                }
//startPR2008-06-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                if(cutphonics && (p=s.indexOf("="))>=0){
                  s=s.substring(0,p);
                }
                if(removeexclamation && ((p=s.indexOf("!"))>=0)){
                  s=s.substring(0,p);
                }
                standardlist = u.addString(standardlist, s);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//---------------------------thethingstart-------------------------------------- 
       //         sentence sent = (new sentence(s,null));
                if(simplecross && isSimpleSentence)//    (sent.type  == sentence.SIMPLECLOZE))
                    if(u.findString(simpleSentArray, s)<0 && !badsentence){
                        simpleSentArray = u.addString(simpleSentArray, s);
                        simpleSentArrayTopics = u.addString(simpleSentArrayTopics, topics[i].name);
                    }
                if(simple &&  isNormalSentence)
                    if(u.findString(standSentArray, s)<0 && !badsentence){
                        standSentArray = u.addString(standSentArray, s);
                        standSentArrayTopics = u.addString(standSentArrayTopics, topics[i].name);   
                    }
                if(buckets){
                    boolean fd = false;
                    for(int nd = 0; nd < okheadinggames.length; nd++){
                        if(lastgames.indexOf(okheadinggames[nd])>=0){
                            fd = true;
                            break;
                        }
                    }
                    if(s.startsWith(head) && fd){
                        String str = s.substring(head.length()).trim();
                        if( u.findString(headingArray, str)<0){
                            headingArray = u.addString(headingArray, str);
                            headingArrayTopics = u.addString(headingArrayTopics, topics[i].name);
                        }
                    }
                }
//---------------------------thethingend---------------------------------------- 
                f.write(s.getBytes());
                f.write((byte)'\r');
                f.write((byte)'\n');
             }
// end rb 11/6/05 -------------------------------------------------------------------------------------------------------
             f.write((byte)'\r');
             f.write((byte)'\n');
           }
         }
//---------------------------thethingstart-------------------------------------- 
//            }
//---------------------------thethingend---------------------------------------- 
         f.close();
//---------------------------thethingstart--------------------------------------
    //      t.writeFile(Tools.fileSentenceOutput, simpleSentArray);
    //     t.writeFile(Tools.fileHeadingOutput, headingArray);
    //     if(simpleSentArray.length>0 && standSentArray.length>0 && headingArray.length>0)
         ToolsOnlineResources tor = new ToolsOnlineResources();
            tor.writeTopicsJson2(ToolsOnlineResources.fileTopics,headingArray, headingArrayTopics, simpleSentArray, simpleSentArrayTopics, standSentArray, standSentArrayTopics,
                    captionsArray, captionsArrayTopics);
     //  t.writeTopicsJson2(Tools.fileTopics, keyArray, headingArray, headingArrayTopics );
//---------------------------thethingend---------------------------------------- 
       }
       catch(IOException e1) {
          return;
       }
     }
     else if(u.yesnomess("Ascii listing of errors",                        //If 1.3
//startPR2004-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//                         "Save ERRORS in 'publictopics' into file called 'topicerrors' ??")) {
                           "Save ERRORS in 'publictopics' into file called 'topicerrors' ??", sharkStartFrame.mainFrame)) {
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       String s;
        try {                                                             //try 1.3.1
             FileOutputStream f = new FileOutputStream(sharedPathplus+"topicerrors.txt");
             f.write((byte)'\r');
             f.write((byte)'\n');
             f.write(("----------------  " +  "bad references in TOPIC TREE" + "  ----------------").getBytes());
             f.write((byte)'\r');
             f.write((byte)'\n');
             f.write((byte)'\r');
             f.write((byte)'\n');
             st = new saveTree1(topicTreeList,topicTreeList.root);
             String wantedtopics[] = new String[0];
//startPR2008-01-03^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
             String badcourses[] = new String[]{};
             String badheadingrefs[] = new String[]{};
             Vector badnotgames = new Vector();
             String badmarkgames[] = new String[]{};
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
             for(i=1;i<st.curr.names.length;++i) {                         //For 1.3.1.1
                s = st.curr.names[i];
                if(s.indexOf(topicTree.ISTOPIC)==0) {                       //If 1.3.1.1.1
                   int pos = s.indexOf(topicTree.SEPARATOR);
                   if(pos>0                                                //If 1.3.1.1.1.1
                      && db.query(s.substring(1,pos),s.substring(pos+1),db.TOPIC)<0
                      || pos < 0  && db.query(topicTree.publictopics,s.substring(1),db.TOPIC)<0) {
                      String ss = s;
                      j = i;
                      short lev = st.curr.levels[i];
                      while(j>0) {                                      //While 1.3.1.1.1.1.1
                         while(--j>=0 && st.curr.levels[j] >= lev);     //While 1.3.1.1.1.1.1.1
                         if(j>=0) {                                        //If 1.3.1.1.1.1.1.1.1
                            ss = st.curr.names[j]+"/"+ss;
                            lev = st.curr.levels[j];
                         }
                      }
                      f.write(ss.getBytes());
                      f.write((byte)'\r');
                      f.write((byte)'\n');
                   }
                   else if(pos < 0)                                        //If 1.3.1.1.1.1
                        wantedtopics = u.addString(wantedtopics,s.substring(1));
                }
             }
             f.write((byte)'\r');
             f.write((byte)'\n');
             f.write(("----------------  " +  "unreferenced TOPICS" + "  ----------------").getBytes());
             f.write((byte)'\r');
             f.write((byte)'\n');
             f.write((byte)'\r');
             f.write((byte)'\n');
             String list[] = db.list(topicTree.publictopics, db.TOPIC);
             boolean hadmess = false,wantdelete=false;
             for(i=0;i<list.length;++i) {                                 //For 1.3.1.2
               if(u.findString(wantedtopics,list[i])<0 && !list[i].equalsIgnoreCase("garbage")) {                  //If 1.3.1.2.1
                 if(!hadmess)  {
                   wantdelete = u.yesnomess("Unreferenced topics",
                       "Do you want to DELETE all unreferenced topics ?");
                   hadmess = true;
                 }
                 f.write(list[i].getBytes());
                 if(wantdelete)  f.write(("      ***deleted***").getBytes());
                 f.write((byte)'\r');
                 f.write((byte)'\n');
                 if(wantdelete) db.delete(topicTree.publictopics,list[i], db.TOPIC);
               }
             }
             f.write((byte)'\r');
             f.write((byte)'\n');
             f.write(("----------------  " +  "invalid TOPICS within topics" + "  ----------------").getBytes());
             f.write((byte)'\r');
             f.write((byte)'\n');
             f.write((byte)'\r');
             f.write((byte)'\n');
             looplist:for(i=0;i<list.length;++i) {                                 //For 1.3.1.3
               boolean mustrewrite=false;
               st = (saveTree1)db.find(topicTree.publictopics,
                      list[i],
                      db.TOPIC);
               if(st==null)continue looplist;
               for(j=1;j<st.curr.names.length;++j) {                      //For 1.3.1.3.1
                 s = st.curr.names[j];
//startPR2008-01-03^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                 if(s.indexOf(topic.types[topic.COURSES])>=0){
                   String s1 = s.substring(s.indexOf(":")+1);
                   String coursenames[] = u.splitString(s1, ',');
                   String badcoursenames[] = new String[]{};
                   for(int p = 0; p < coursenames.length; p++){
                     if(u.findString(publicCourses, coursenames[p])<0){
                       badcoursenames = u.addString(badcoursenames, coursenames[p]);
                     }
                   }
                   if(badcoursenames.length>0)
                     badcourses = u.addString(badcourses, st.curr.names[0]+"   ---   " + u.combineString(badcoursenames, ","));
                 }
                 if(s.indexOf(topicTree.ISPATH)==0){
                   if(topicTreeList.root.find(s)==null){
                     badheadingrefs = u.addString(badheadingrefs, st.curr.names[0]+"   ---   " + s);
                   }
 
                 }
                 if(s.indexOf(topic.types[topic.MARKGAMES])>=0||s.indexOf(topic.types[topic.MARKGAMES2])>=0){
                   String s1 = s.substring(s.indexOf(":"));
                   String gamenames[] = u.splitString(s1, ',');
                   loop1:for(int a = 0; a < gamenames.length; a++){
                     int p;
                     if((p=gamenames[a].indexOf(':'))>=0)
                       gamenames[a] = gamenames[a].substring(p+1);
                     if((p=gamenames[a].indexOf('^'))>=0)
                       gamenames[a] = gamenames[a].substring(0, p);
                     if(gamenames[a].trim().equals(""))continue loop1;
                     if (publicGameTree.find(gamenames[a]) == null) {
                       badmarkgames = u.addString(badmarkgames, s.substring(0, s.indexOf(":")+1) +"  "+st.curr.names[0]+"   ---   " + gamenames[a]);
                     }
                   }
                 }
                 if(s.indexOf(topic.types[topic.NOTGAMES])>=0){
                   String s1 = s.substring(s.indexOf(":"));
                   String gamenames[] = u.splitString(s1, ',');
                   loop1:for(int a = 0; a < gamenames.length; a++){
                     int p;
                     if((p=gamenames[a].indexOf(':'))>=0){
                       gamenames[a] = gamenames[a].substring(p + 1);
                     }
                     if((p=gamenames[a].indexOf('^'))>=0)
                       gamenames[a] = gamenames[a].substring(0, p);
                     if(gamenames[a].trim().equals(""))continue loop1;
                     if (publicGameTree.find(gamenames[a]) == null) {
                       boolean added = false;
                       String str;
                       for(int b = 0; b < badnotgames.size();b++){
                         if((str=((String)badnotgames.get(b))).startsWith(gamenames[a]+"|")){
                           badnotgames.set(b, str + "|" + st.curr.names[0]);
                           added = true;
                           break;
                         }
                       }
                       if(!added)
                         badnotgames.add(gamenames[a] + "|" + st.curr.names[0]);
                     }
                   }
                 }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                 if(s.indexOf(topicTree.ISTOPIC)==0) {                     //If 1.3.1.3.1.1
                   if(s.charAt(s.length()-1)=='+')                        //If 1.3.1.3.1.1.1
                     s = s.substring(0, s.length() - 1);
                   int pos = s.indexOf(topicTree.SEPARATOR);   
                   if(pos>0 && db.query(s.substring(1,pos),s.substring(pos+1),db.TOPIC)<0
                      || pos < 0  && db.query(topicTree.publictopics,s.substring(1),db.TOPIC)<0) {
                      if(s.charAt(s.length()-1) == ' ') {                  //If 1.3.1.3.1.1.2.1
                                                                        //While 1.3.1.3.1.1.2.1.1
                        while(s.charAt(s.length()-1) == ' ') s = s.substring(0,s.length()-1);
                        if(pos>0 && db.query(s.substring(1,pos),s.substring(pos+1),db.TOPIC)>=0
                               || pos < 0                                  //If 1.3.1.3.1.1.2.1.1.1
                               && db.query(topicTree.publictopics,s.substring(1),db.TOPIC)>=0) {
                              st.curr.names[j]=s;
                              mustrewrite=true;
                              s = s + "  (changed)";
                        }
                      }
                      f.write((list[i]+"   ---   "+ s).getBytes());
                      f.write((byte)'\r');
                      f.write((byte)'\n');
                   }
                 }
               }
               if(mustrewrite)                                             //If 1.3.1.3.2
                 db.update(topicTree.publictopics,list[i],st.curr,db.TOPIC);
             }
//startPR2008-01-03^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
             if(badcourses.length>0){
               f.write( (byte) '\r');
               f.write( (byte) '\n');
               f.write( ("----------------  " + "invalid COURSE REFERENCES within topics" + "  ----------------").getBytes());
               f.write( (byte) '\r');
               f.write( (byte) '\n');
               f.write( (byte) '\r');
               f.write( (byte) '\n');
               f.write( (byte) '\r');
               f.write( (byte) '\n');
             }
             for(int p = 0; p < badcourses.length; p++){
               f.write( (badcourses[p]).getBytes());
               f.write( (byte) '\r');
               f.write( (byte) '\n');
             }

             if(badheadingrefs.length>0){
               f.write( (byte) '\r');
               f.write( (byte) '\n');
               f.write( ("----------------  " + "invalid HEADING REFERENCES within topics" + "  ----------------").getBytes());
               f.write( (byte) '\r');
               f.write( (byte) '\n');
               f.write( (byte) '\r');
               f.write( (byte) '\n');
               f.write( (byte) '\r');
               f.write( (byte) '\n');
             }
             for(int p = 0; p < badheadingrefs.length; p++){
               f.write( (badheadingrefs[p]).getBytes());
               f.write( (byte) '\r');
               f.write( (byte) '\n');
             }

             if(badmarkgames.length>0){
               f.write( (byte) '\r');
               f.write( (byte) '\n');
               f.write( ("----------------  " + "invalid MARK GAMES within topics" + "  ----------------").getBytes());
               f.write( (byte) '\r');
               f.write( (byte) '\n');
               f.write( (byte) '\r');
               f.write( (byte) '\n');
               f.write( (byte) '\r');
               f.write( (byte) '\n');
             }
             for(int p = 0; p < badmarkgames.length; p++){
               f.write( (badmarkgames[p]).getBytes());
               f.write( (byte) '\r');
               f.write( (byte) '\n');
             }
             if(badnotgames.size()>0){
               f.write( (byte) '\r');
               f.write( (byte) '\n');
               f.write( ("----------------  " + "invalid NOT GAMES within topics" + "  ----------------").getBytes());
               f.write( (byte) '\r');
               f.write( (byte) '\n');
               f.write( (byte) '\r');
               f.write( (byte) '\n');
             }
             for(int p = 0; p < badnotgames.size(); p++){
               String ss[] = u.splitString((String)badnotgames.get(p));
               for(int r = 0; r < ss.length; r++){
                 if(r==0){
                   f.write( (byte) '\r');
                   f.write( (byte) '\n');
                   f.write(("Invalid reference to: \""+ss[r]+"\" in the following topics:").getBytes());
                   f.write( (byte) '\r');
                   f.write( (byte) '\n');
                 }
                 else{
                   f.write((ss[r]).getBytes());
                   f.write( (byte) '\r');
                   f.write( (byte) '\n');
                 }
               }
             }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
           f.write((byte)'\r');
             f.write((byte)'\n');
             f.close();
       }
       catch(IOException e1) {
          return;
       }
     }

    }
*/
  }

  
  /**
   *
   */
  synchronized void updategames() {
    closeprev(true);
         setTitle(gamestitle);
         setforedit(false);
         gettopictreelist();
         topicTreeList.publicname = "publicgames";
         topicTreeList.setup(updateList(currStudent,db.GAME),
                             true,db.GAME,false, "Topic lists for update");
         gettopiclist();
         topicList.onlyOneDatabase = null;
         topicList.setup(this.searchListGame(currStudent),
                         false,db.GAME,false,"For reference (right-click)");
         updatingGames = true;
         grid1.gridheight = 1;
         grid1.weighty = 1;
         grid1.weightx = 1;
         grid1.gridwidth = 1;
         grid1.gridx = grid1.gridy = 0;
         bevelPanel1.add(u.uScrollPane(topicTreeList), grid1);
         grid1.gridx = 1;
         bevelPanel1.add(u.uScrollPane(topicList), grid1);
 //        keypad.activate(sharkStartFrame.mainFrame,new char[] {(char)keypad.SHIFT});
         bevelPanel1.validate();
         
 //         topicTreeList.expandAll((TreeNode)topicTreeList.getModel().getRoot());
//startPR2004-09-01^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         // enables exiting screen via the ESC key
         topicTreeList.requestFocus();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  }
  /**
   *
   */
  synchronized void updatetext() {
    closeprev(true);
//startPR2007-11-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    if(!Demo_base.isDemo)
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         setTitle(texttitle);
         setforedit(false);
         gettopictreelist();
         topicTreeList.publicname=u.absoluteToRelative(publicTextLib[0]);
         if(Demo_base.isDemo){
             String str[] = new String[] {
                 shark.publicPath+shark.sep+"publictext",
                 shark.publicPath+shark.sep+"publictextdemo"};
             topicTreeList.setup(str, true, db.TEXT, false,
                                 "Text items for update");
         }
         else
             topicTreeList.setup(publicTextLib,true,db.TEXT,false, "Text items for update");
         updatingText = true;
         grid1.gridheight = 1;
         grid1.weighty = 0;
         grid1.weightx = 0;
         grid1.gridwidth = 1;
         grid1.gridx = 0;
         grid1.gridy = -1;
         JPanel jp = new JPanel(new GridBagLayout());
         jp.setBackground(Color.yellow);
         publictextSearch = new JTextField(20);
         JButton jb = new JButton(u.gettext("search", "label"));

         jb.addActionListener(new java.awt.event.ActionListener() {
           public void actionPerformed(ActionEvent e) {
               String s = publictextSearch.getText();
               if(s.trim().equals(""))return;
               topicTreeList.expandAtString(s);
           }
         });


         grid1.fill = GridBagConstraints.NONE;
         grid1.insets = new Insets(5,5,5,5);
         jp.add(publictextSearch, grid1);
         grid1.insets = new Insets(5,0,5,5);
         jp.add(jb, grid1);
         jp.setMinimumSize(new Dimension(sharkStartFrame.mainFrame.getWidth()/5,
                 sharkStartFrame.mainFrame.getHeight()));


         grid1.weightx = 1  ;
         grid1.insets = new Insets(0,0,0,0);
         grid1.fill = GridBagConstraints.BOTH;
         grid1.gridx = -1;
         grid1.gridy = 0;
         grid1.weightx = 1;
         grid1.weighty = 1;
         bevelPanel1.add(u.uScrollPane(topicTreeList), grid1);
         grid1.weightx = 0;
         bevelPanel1.add(jp, grid1);
//         keypad.activate(sharkStartFrame.mainFrame,new char[] {(char)keypad.SHIFT});
         bevelPanel1.validate();
//startPR2004-09-01^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         // enables exiting screen via the ESC key
         topicTreeList.requestFocus();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         // helps when doing KDiff with publictexts
//         topicTreeList.expandAll((TreeNode)topicTreeList.getModel().getRoot());
  }
  /**
   * Sets up the add/change pictures display - This is where images are altered
   * and new images are made.
   * @param e Action event
   */
  synchronized void copysay() {
         closeprev(true);
         setTitle(saytitle);
         setforedit(false);
         gettopictreelist();
//         topicTreeList.setExpandByDefault(false);
//         topicTreeList.publicname="publicsay1";
         String s[] = new String[] {studentList[currStudent].name};
         s = u.addString(s, publicSoundLib);
         s = u.addString(s, publicSentLib);
         s = u.addString(s, publicSent2Lib);
         s = u.addString(s, publicSent3Lib);
         s = u.addString(s, publicSent4Lib);
         topicTreeList.setup(s,true,db.WAV,false, "speech items for copying and deleting");
         updatingText = true;
         grid1.gridheight = 1;
         grid1.weighty = 1  ;
         grid1.weightx = 1  ;
         grid1.gridwidth = 1;
         grid1.gridx = grid1.gridy = 0;
         bevelPanel1.add(u.uScrollPane(topicTreeList), grid1);
         bevelPanel1.validate();
//startPR2004-08-03^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         // enables exiting screen via the ESC key
         topicTreeList.requestFocus();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  }
  synchronized void pictures_actionPerformed(ActionEvent e) {
    closeprev(true);
    setTitle(pictitle);
    printlist.setEnabled(false);
    showingPictures = true;
    setforedit(false);
    gettopictreelist();
    
    keypad.activate(sharkStartFrame.mainFrame,new char[] {(char)keypad.SHIFT});  
    topicTreeList.setCellRenderer(new treepainter_imagelist(publicPhotoNames));
    topicTreeList.setup(sharkImage.imagelibup(),true,db.IMAGE,true,"List of pictures");

    if(imageNew != null) {
      topicTreeList.setbg("publicimage2", imageNew, Color.orange);
      topicTreeList.setbg("publicimagew", imageNew, Color.orange);
    }
    if(imageChanged != null) {
      topicTreeList.setbg("publicimage2", imageChanged, Color.yellow);
      topicTreeList.setbg("publicimagew", imageChanged, Color.yellow);
    }
    picChoice = new JList();
    picChoice.setBorder(BorderFactory.createLineBorder(Color.black));
    picChoice.addMouseListener(new java.awt.event.MouseAdapter() {
         public void mouseReleased(MouseEvent e) {
            if(currPicture != null) {
               int sel = picChoice.getSelectedIndex();
               if(sel >= 0) {
                  currPicture.newChoice(sel);
               }
            }
         }
      });
      picControl = new sharklist() {
          public void newselection() {
            if(currPicture != null) {
               currPicture.newControl();
            }
          }
          public void datachange() {
            if(currPicture != null) {
               currPicture.changeControl(picControl);
            }
         }
     };
    picShow = new runMovers();
    picShow.canquiesce = false;
    picShow.onmainscreen=true;
    picShow.addKeyListener( new KeyAdapter()   {
      public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if(code >= KeyEvent.VK_F2 && code <= KeyEvent.VK_F12) {
          currPicture.keyhit(e);
        }
        else if(picShow != null && !picShow.mouseOutside)  {
               picShow.keypressed(e);
          }
      }
      public void keyTyped(KeyEvent e) {
          if(picShow != null && !picShow.mouseOutside)  {
               picShow.keytyped(e);
          }
      }
     });
//startPR2004-09-01^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     // enables exiting screen via the ESC key
     picChoice.addKeyListener(new KeyAdapter() {
       public void keyPressed(KeyEvent e) {
         int code = e.getKeyCode();
         if(code >= KeyEvent.VK_F2 && code<= KeyEvent.VK_F12)
           currPicture.keyhit(e);
         if(code == KeyEvent.VK_ESCAPE)
           sharkStartFrame.mainFrame.setupgames();
       }
     });
     picControl.addKeyListener(new KeyAdapter() {
       public void keyPressed(KeyEvent e) {
         int code = e.getKeyCode();
         if(code >= KeyEvent.VK_F2 && code <= KeyEvent.VK_F12)
           currPicture.keyhit(e);
         if(code == KeyEvent.VK_ESCAPE)
           sharkStartFrame.mainFrame.setupgames();
       }
     });
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    grid1.gridwidth = 1;
    grid1.weighty = 1;
    grid1.gridx = -1;grid1.gridy = 0;
    
    
    boolean doFindTopic = imageToTopicList.size()>0;
    
    if(!doFindTopic && imageToTopicList.size()==0){
        doFindTopic = u.yesnomess(shark.programName, "Do you want to use 'Find Topic'?||(this takes some time to set up)", sharkStartFrame.mainFrame);
    }
    
    
    JScrollPane  pscroll = u.uScrollPane(topicTreeList);
    pscroll.setMinimumSize(new Dimension(screenSize.width*4/20,screenSize.height));
    picChoice.setMinimumSize(new Dimension(screenSize.width*3/20,screenSize.height));
    picControl.setPreferredSize(new Dimension(screenSize.width*3/20,screenSize.height));
    picControl.setMinimumSize(new Dimension(screenSize.width*3/20,screenSize.height));
    JPanel showMain = null;
    if(!doFindTopic){
        
        
        
        picShow.setMinimumSize(new Dimension(screenSize.width/2,screenSize.height));    
    }
    else{
        topdb = u.absoluteToRelative(sharkStartFrame.publicTopicLib[0]);
        topicListForImages = db.list(topdb,db.TOPIC);  
        btFindTopic = new JButton("Find Topic");
            btFindTopic.addActionListener(new java.awt.event.ActionListener() {
              public void actionPerformed(ActionEvent e) {
                  jnode jn = topicTreeList.getSelectedNode();
                 String s[] = getTopics(jn, false); 
                if(s.length == 0)
                  u.okmess(u.gettext("rec_","listtopicsh"),u.gettext("rec_","listtopicsn"), sharkStartFrame.mainFrame);
                else 
                  showlist(u.gettext("rec_","listtopicsh"),s);   

              }
            });   
            btFindTopic.setEnabled(false);
        showMain = new JPanel(new GridBagLayout());
        showMain.setMinimumSize(new Dimension(screenSize.width/2,screenSize.height));
        grid1.weighty = 1;
        grid1.gridx = 0;
        grid1.gridy = -1;
        grid1.fill = GridBagConstraints.BOTH;
        showMain.add(picShow, grid1);
        grid1.weighty = 0;
        grid1.fill = GridBagConstraints.NONE;
        grid1.insets = new Insets(20,20,20,20);
        showMain.add(btFindTopic, grid1);
        grid1.insets = new Insets(0,0,0,0);
        grid1.fill = GridBagConstraints.BOTH;
        grid1.weighty = 1;
        grid1.gridx = -1;
        grid1.gridy = 0;   
    }
    grid1.weightx = 4;
    bevelPanel1.add(pscroll, grid1);
    grid1.weightx = 3;
    bevelPanel1.add(picChoice,grid1);
    bevelPanel1.add(picControl, grid1);
    grid1.weightx = 10;
    if(doFindTopic)
        bevelPanel1.add(showMain, grid1);
    else
    bevelPanel1.add(picShow, grid1);
    bevelPanel1.validate();
     
    if(doFindTopic && imageToTopicList.size()==0){
               Thread imSearchThread = new Thread(new doImageTopicSearch());
               imSearchThread.start();
    }

    picShow.start1();
//startPR2004-09-01^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    // enables exiting screen via the ESC key
    picChoice.requestFocus();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  }
  
  
  
 void showlist(String title,String list[]) {new showlist(title,list);}

 class showlist
//startPR2004-08-27^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     extends JDialog {
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR

     JList toplist;
     JDialog thisd;
     
   public  showlist(String title,String list[]) {
       super(sharkStartFrame.mainFrame);
       thisd = this;
//startPR2004-08-27^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    this.setResizable(false);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    setBounds(new Rectangle(sharkStartFrame.screenSize.width/3,
                            sharkStartFrame.screenSize.height/4,
                            sharkStartFrame.screenSize.width/3,
                            sharkStartFrame.screenSize.height/2));
    this.getContentPane().setLayout(new GridBagLayout());
    this.setEnabled(true);
    this.setTitle(title);
    toplist = new JList(list);
    GridBagConstraints grid = new GridBagConstraints();
    grid.fill = GridBagConstraints.BOTH;
    grid.weightx = 1;
    grid.weighty = 1;
    grid.gridx = 0;
    grid.gridy = -1;    
    
    JPanel pan = new JPanel(new GridBagLayout());
    JButton button = new JButton(u.gettext("rec_", "gotolist"));
    button.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
          Object o = toplist.getSelectedValue();
          if(o==null)return;
          String s = (String)o;
          sharkStartFrame.mainFrame.PublicTopics.doClick();
          jnode node = sharkStartFrame.mainFrame.topicTreeList.findTopic(s, true);
          if(node != null) {
            TreePath tp;
            sharkStartFrame.mainFrame.topicTreeList.setSelectionPath(tp = new TreePath(node.getPath()));
            sharkStartFrame.mainFrame.topicTreeList.scrollPathToVisible(tp);
          }
          thisd.dispose();
      }
    });
    grid.insets = new Insets(5,5,5,5);
    pan.add(button, grid);
    grid.insets = new Insets(0,0,0,0);
    this.getContentPane().add(new JScrollPane(toplist), grid);
    grid.weighty = 0;
    grid.fill = GridBagConstraints.NONE;
    this.getContentPane().add(pan, grid); 
    
    setVisible(true);
    validate();
  }
 }  
  
  public String[] getTopics(jnode jn, boolean justOne){
              int i = 0;
              // find the right publicimage library
               for(i = 0; i < topicTreeList.root.getChildren().length; i++){
                  if(((jnode)topicTreeList.root.getChildAt(i)).isNodeDescendant(jn)){
                      break;
                  }
              }
               if(i<0 || i >= imageToTopicList.size())return null;
               
                String[] s= new String[0];    
                String sss[][] = (String[][])imageToTopicList.get(i);
                // find if this image is used in any of the topics
                for(int k = 0; k < sss.length; k++){        
                    if(u.findString(sss[k],jn.get()) >= 0 && u.findString(s, topicListForImages[k]) < 0){
                        s = u.addString(s,topicListForImages[k]);        
                        if(justOne)return new String[]{topicListForImages[k]};
                    }
            }     
                if(s.length==0)return null;
                return s;
  }
  
  
public class doImageTopicSearch implements Runnable{

    public doImageTopicSearch(){

    }

  public void run(){                            
    String topdb = u.absoluteToRelative(sharkStartFrame.publicTopicLib[0]);
    String topicListForImages[] = db.list(topdb,db.TOPIC);
    ProgressMonitor pm = new ProgressMonitor(sharkStartFrame.mainFrame,"Set-up for images","Scanning lists",0,topicListForImages.length);
      short sep,i,j;
      int pp;
      topic top;
      word w[];
      int ii;
      for(int k = 0; k < topicTreeList.root.getChildren().length; k++){
          imageToTopicList.add(new String[topicListForImages.length][]);
      }
      for(ii = 0; ii < topicListForImages.length; ++ii) {
          pm.setProgress(ii);
          top = new topic(topdb, topicListForImages[ii], null, null);
          if(top == null) continue;
    //      if(!top.initfinished) top.getWords(null,false);
          w = top.getAllWords(true);
          word wsent[] = u.addWords(top.getSpecials(new String[]{topic.sentencegames2[0]}), 
                                    top.getSpecials(new String[]{topic.sentencegames2[2]}));
          
           for(int k = 0; k < imageToTopicList.size(); k++){
               jnode libnodes[]= topicTreeList.root.getChildren()[k].getChildren();
               for(int p = 0; p < libnodes.length; p++){
                   String imname = libnodes[p].get().toLowerCase();
                    for(j=0;j<w.length;++j) {
                        String vstr = w[j].vpic().toLowerCase();
                        if(vstr!=null){
                            if(vstr.equalsIgnoreCase(imname)){
                                String[][] tp = (String[][])imageToTopicList.get(k);
                                if(u.findString(tp[ii], imname)<0) {
                                    tp[ii] = u.addString(tp[ii],imname);
                                    imageToTopicList.set(k, tp);
                                }
                            }
                        }
                    } 
                    
                    for(j=0;j<wsent.length;++j) {
                        String vstr = wsent[j].value.toLowerCase();
                        int leftb;
                        int rightb;
                        if((leftb=vstr.lastIndexOf('{'))>0 && (rightb=vstr.lastIndexOf('}'))>0 )
                            vstr = vstr.substring(leftb+1, rightb);

                        if(vstr.equals(imname)){
                            String[][] tp = (String[][])imageToTopicList.get(k);
                            if(u.findString(tp[ii], imname)<0) {
                                tp[ii] = u.addString(tp[ii],imname);
                                imageToTopicList.set(k, tp);
                            }
                        }

                    } 
               }
            }          
      }
      
      pm.close();
    }
  }
    
  
  /**
   * Sets up a picture in the database with the name passed as a parameter
   * @param database Where image is to be found
   * @param name Name of image
   */
  public void setupPicture(String database, String name) {
    if(currPicture != null)    endPicture();
    currPicture = new sharkImage(database,name,false,true);
    currPicture.listControls(picControl);
    currPicture.choice(picChoice);
    picShow.moverwantskey = currPicture;
    picShow.addMover(currPicture,0,0);
          if(shark.showPhotos){
              if(currPhoto!=null){
                  currPhoto.stop();
                  currPhoto = null;
              }
            int w1 = sharkStartFrame.screenSize.width * 3 / 8;
            int h1 = sharkStartFrame.screenSize.height / 3;
            int h = u.getPhotographIndex(name);
            if ((h=u.findString(sharkStartFrame.publicPhotoNames, name))>=0){
                  Image im1 = Toolkit.getDefaultToolkit().getImage(sharkStartFrame.publicPhotoNamesFolder
                          + sharkStartFrame.separator + sharkStartFrame.publicPhotoNamesPlusExt[h]); 
                  sharkImage im2 = new sharkImage(im1, name);
                  if(im2!=null){
                      im2.isimport = true;
                       currPhoto = new wordlist.wordpicture(sharkStartFrame.mainFrame,name,im2, picControl.getLocation().x,0,w1,h1, true);     
                       currPhotoPos = CURRPHOTOPOS_NE;
                        currPhoto.setLocation(picChoice.getLocation().x,0);                     
                  }             
            }                
          }
  }
  /**
   * Closes and saves the picture
   */
  void endPicture() {
    currPicture.save();
//     db.closeAll();
     cancelPicture();
  }
  /**
   * Closes and does not save the picture
   */
  void cancelPicture() {
    if(picShow != null) {
        picShow.removeMover(currPicture);
     }
     picControl.clear();
     picChoice.setListData(new String[0]);
     currPicture = null;
  }
  /**
   * Sets up a new picture
   * @param database Where the picture is to be found
   * @param name Name of the picture
   */
  public void newPicture(String database, String name) {
      newPicture(database, name, null);
  }
  
  public void newPicture(String database, String name, jnode jn) {
     if(btFindTopic != null && jn !=null)btFindTopic.setEnabled(getTopics(jn, true) != null);
    if(currPicture != null) endPicture();
    if(name != null && !name.equals(""))
        setupPicture(database,name);
  }
  /**
   * Renames a picture in the database or else produces an appropriate message
   * telling the user why the picture was not renamed
   * @param database Database used
   * @param oldname Name of picture before changes were made
   * @param newname Current name of selected picture
   * @param node The currently selected node
   * @return True when picture is set up in the database and otherwise false
   */
  public boolean newPicture(String database, String oldname, String newname,jnode node) {
    if(newname.equals(oldname))                                          //If 1
      return true; // The current picture name is the same as the previously selected picture name
    if(newname == null || newname.equals("")) {                            //If 2
          noise.beep();                       //There is no name for the currently selected node
//startPR2004-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//          u.okmess("Renaming picture", "Cannot rename to nothing");
              u.okmess("Renaming picture", "Cannot rename to nothing", sharkStartFrame.mainFrame);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          topicTreeList.startEditingAtPath(new TreePath(node.getPath()));
          return false;
    }
    else if(oldname != null && !oldname.equals("")) { //Else if 2 - There is an name for the picture previously selected - so rename
       if(db.query(database,newname,db.IMAGE) >= 0) {                      //If 2.1
          noise.beep();         //There is already an image in the database with the chosen name
//startPR2004-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//          u.okmess("Renaming picture", "Already exists");
            u.okmess("Renaming picture", "Already exists", sharkStartFrame.mainFrame);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          topicTreeList.startEditingAtPath(new TreePath(node.getPath()));
          return false;
       }
       if(db.query(database,oldname,db.IMAGE) >= 0) {
         if (!db.rename(database, oldname, newname, db.IMAGE)) { //If 2.2
           noise.beep(); //The image is not be renamed
//startPR2004-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//           u.okmess("Renaming picture", "Rename failed");
            u.okmess("Renaming picture", "Rename failed", sharkStartFrame.mainFrame);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
           topicTreeList.startEditingAtPath(new TreePath(node.getPath()));
           return false;
         }
       }
       currPicture.name = newname;
    }
    else {                                                               //Else 2 - new picture
       if(db.query(database,newname,db.IMAGE) >= 0) {                      //If 2.1
          noise.beep();         // - There is already an image in the database with the new name
//startPR2004-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//          u.okmess("New picture", "Picture already exists");
            u.okmess("New picture", "Picture already exists", sharkStartFrame.mainFrame);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          topicTreeList.startEditingAtPath(new TreePath(node.getPath()));
          return false;
       }
    }
    setupPicture(database,newname);
    return true;
  }

  /**
   * Returns the maximum number of users allowed to use the license
   * @param serial Comes from the input stream
   * @param school Comes from the input stream
   * @param key Key to be used
   * @return The maximum number of users allowed or else -1
   */
  int getusers(String serial, String school, String key ) {
    int i, maxusers, k,m;
    int j = Integer.parseInt(serial.substring(u.scanfor(serial,u.numbers)));
    Random rr = new Random(j);
    long lg = 0;
    String s = shark.decrypt1(key,serial + school);
    if(s==null) {
      return -1;
    }
    maxusers = i = Integer.parseInt(s.substring(6));
    while(--i > 0) {
      lg = rr.nextLong();
    }
    String s2 = new String(new char [] {(char)((lg>>40) & 255),
                                    (char)((lg>>32) & 255),
                                    (char)((lg>>24) & 255),
                                    (char)((lg>>16) & 255),
                                    (char)((lg>>8) & 255),
                                    (char)(lg & 255) });
    if(!s2.equals(s.substring(0,6))) {
          return -1;
    }
    return maxusers;
  }
  /**
   * <li>Displays a different picture depending on whether the current topic has less
   *  than 4 splits or has all the words split.
   *  li<If the words in the topic are all split then returns true.
   * @param game  The name of a game
   * @return True if the words are split
   */
  boolean oksplit(String game) {
        if (currPlayTopic.phonicsw && wordlist.usephonics) return true;
        if(!currPlayTopic.allowed(game,"split4")) {   //Current topic has less than 4 words split
           wordTree.setsplit();
           int wi = screenSize.width*7/24;
           int hi = screenSize.height*5/12;
           wordTree.putPicture(wordlist.model.getSize()>4 ? "help_split4":"help_split4x",
           u.gettext("split","title1"),false,bevelPanel2.getLocationOnScreen().x-wi*16/15,
                                        screenSize.height/3-hi/2,wi,hi);

           return false;
        }
//        else if(!currPlayTopic.fl &&  !currPlayTopic.allowed(game,"unsplit")) {
//           wordTree.setsplit();
//           int wi = screenSize.width*7/24;
//           int hi = screenSize.height*5/12;
//           wordTree.putPicture("help_unsplit",u.gettext("split", "title2"),false,bevelPanel2.getLocationOnScreen().x-wi*16/15,
//                                        screenSize.height/3-hi/2,wi,hi);
//           return false;
//        }
//startPR2008-09-03^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        if(currPlayTopic.multisyll && gameflag.get(game).multisyllable && !wordTree.splitCB.isSelected())
          wordTree.splitCB.doClick();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        return true;
  }
  /**
   * <li>Starts the new game.
   * <li>Closes teaching notes if open.
   * <li>Stops the wordlist's current picture if it is being displayed.
   * @param name Name of game to start
   */
  void startgame(String name) {
    if(gametot > 0) {                                                     //If 3
         noise.beep();                                         //There is a game running
         return;
    }
    if(gamePanel != null) {
      gamePanel.removeMouseMotionListener(gamePanel.li1);
      gamePanel.removeMouseListener(gamePanel.li2);
      gamePanel.stoprun=true;
      gamePanel.setVisible(false);
      gamePanel.mouseOutside=true;
      if (gamePanel.tooltipmover1 != null) {
        gamePanel.removeMover(gamePanel.tooltipmover1);
        gamePanel.tooltipmover1 = null;
      }
    }
     if(showteachingnotes != null) {                                       //If 1
       showteachingnotes.setVisible(false);                                    //There are teaching notes
       showteachingnotes.dispose();
       showteachingnotes = null;
     }
//startPR2008-06-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//     if(wordTree.currpic != null)                                         //If 2
//       wordTree.currpic.stop();                              //The wordtree's picture is showing
     wordlist.removeCurrPic();
//endPRimagesandvids^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   spokenWord.flushspeaker(true);
   if(refreshprog())                                                    //If 4
     return;                                          //Calling refreshprog()sets up the games

                  // if superlist and special game, replace currplaytopic by a
                  // random one from the list
     if(currPlayTopic.superlist) {
       gameflag ff = gameflag.get(name);
       if(ff.special ||  ff.pairedwords || ff.needbad || ff.needsentences1 || ff.needsentences3 || ff.needchunks) {
          superswap = currPlayTopic;
          currPlayTopic = currPlayTopic.topicforspecial(ff.needsentences1 || ff.needchunks ? topic.sentencegames2[0]
                                                            :(ff.needsentences3? topic.sentencegames2[2]:name));
              wordTree.reset();
              wordTree.splitCB.setSelected(false);
              currPlayTopic.splitwords = null;
              wordTree.redrawing = true;
              wordTree.setup(currPlayTopic,null);
              wordTree.redrawing = false;
       }
//startPR2008-08-20^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//  else {
//    if(wordTree.unsplit || !ff.needanysplit  && !ff.needsyllsplit) {
//         wordTree.reset();
//         wordTree.splitCB.setSelected(false);
//         currPlayTopic.splitwords = null;
//         wordTree.redrawing = true;
//         wordTree.setup(currPlayTopic, null);
//         wordTree.redrawing = false;
//    }
//   }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     }
     else   {
        options.gamestart(currPlayTopic,name);     // handle preset games options
     }
   //  new runningGame(name, wordTree, currPlayTopic.name,false);
     
//     if((!wordTree.shuffled||currPlayTopic.revision) && !wordTree.wholeextended && !currPlayTopic.specialgame(name)) {     //If 5
     if(!wordTree.wholeextended && !currPlayTopic.specialgame(name)) {
         new runningGame(name, wordTree, currPlayTopic.name,false);// The wordlist is not fully extended
     }                                                      //AND the game is not a special game
     else {                                                              //Else 5
       wordlist wordtemp = new wordlist();                //The wordlist is fully extended
       wordtemp.keepGroupings = wordTree.keepGroupings;   //AND the game is a special game
       wordtemp.shuffled = true;
       wordtemp.keepGroupings = true;
//       wordtemp.wholeextended = wordTree.wholeextended;
       if(wordTree.wholeextended) {                                       //If 5.1
         wordtemp.shuffled = true;                           // The wordlist is fully extended
         wordtemp.keepGroupings = true;
       }   
       wordtemp.setup(currPlayTopic,new String[]{name});
       new runningGame(name, wordtemp, currPlayTopic.name,false);
     }
     lastgamestart = System.currentTimeMillis();
  }


  void changetab(int index) {
      wordlist.removeCurrPic();
      int i;
      int games[] = null;
      Color gcolours[] = null;
      int mgames[] = null;
      Color mgcolours[] = null;
      int m2games[] = null;
      Color mg2colours[] = null;
      if(initialGamesHelp!=null){
          gamePanel.removeMover(initialGamesHelp);
          initialGamesHelp = null;
      }
      if(recommendtm!=null){
          gamePanel.removeMover(recommendtm);
          recommendtm = null;
      }
      if(alsorecommendtm!=null){
          gamePanel.removeMover(alsorecommendtm);
          alsorecommendtm = null;
      }
      if(supertm!=null){
          gamePanel.removeMover(supertm);
          supertm = null;
      }
      if(nextpagetm!=null){
          gamePanel.removeMover(nextpagetm);
          nextpagetm = null;
      }
      if(prevpagetm!=null){
          gamePanel.removeMover(prevpagetm);
          prevpagetm = null;
      }

      if(index==0 && studentList[currStudent].currTab==null && (
              studentList[currStudent].studentrecord == null ||
              studentList[currStudent].studentrecord.length<1)){
          studentList[currStudent].firstEverRun = true;
          initialGamesHelp = new mover.textMover(
                  u.gettext("gameicons", "label"),
                        mover.WIDTH*8/10,
                         treefontm.getHeight(),
                         Color.white,
                         col1,
                         new Font[]{treefont});
          gamePanel.addMover(initialGamesHelp, (mover.WIDTH-initialGamesHelp.w)/2,
                  mover.HEIGHT - (mover.HEIGHT*1/15));
      }
      boolean recommended = false;
      boolean all = false;
      looptab: for(i = 0; index >=0 &&  i < tabs.size(); i++){
          if(i == index){
              mover.gamesTab gt = (mover.gamesTab)tabs.get(i);
              if(str_allavailable.equals(u.combineString(gt.text)))
                 all = true;
              if(str_recommended.equals(u.combineString(gt.text)))
                 recommended = true;
          }
      }
      String combtext = null;
      for(i = 0; index >=0 && i < tabs.size(); i++){
          mover.gamesTab gt2 = (mover.gamesTab)tabs.get(i);
          gt2.setActive(i==index);
          if(i==index){
              combtext = u.combineString(gt2.text, " ");
              studentList[currStudent].currTab = combtext;
          }
      }
      if(index<0){
          all = true;
      }
      else if(combtext == null) return;
      for(int k = 0; k < headinggames.size(); k++){
          String s[] = (String[])headinggames.get(k);
          Color currcol = gettabcolor(s[0]);
          for(int p=1;p<s.length;p++) {
             int h = u.findString(gamename, s[p]);
             if(h >= 0){
                 boolean add = false;
                 if(all){
                     add =true;
                 }
                 else {
                     if(recommended){
                          if(markg!=null && u.inlist(markg, h)){
                             if(mgames==null)mgames = new int[]{h};
                             else mgames = u.addint(mgames, h);
                             if(mgcolours==null)mgcolours= new Color[]{currcol};
                             else mgcolours = u.addcolor(mgcolours, currcol);
                         }
                         if(markg2!=null && u.inlist(markg2, h)){
                             if(m2games==null)m2games = new int[]{h};
                             else m2games = u.addint(m2games, h);
                             if(mg2colours==null)mg2colours= new Color[]{currcol};
                             else mg2colours = u.addcolor(mg2colours, currcol);
                         }
                     }
                     else if(s[0].equals(combtext)) {
                         add =true;
                     }
                 }
                 if(add){
                     if(games==null)games = new int[]{h};
                     else games = u.addint(games, h);
                     if(gcolours==null)gcolours= new Color[]{currcol};
                     else gcolours = u.addcolor(gcolours, currcol);
                 }
             }
          }
      }
      Color retcolmg[] = null;
      Color retcolmg2[] = null;
      if(recommended){
          loop1:for(int n = 0; markg!=null && n < markg.length; n++){
              for(int m = 0; m < mgames.length; m++){
                 if(markg[n]==mgames[m]){
                    if(retcolmg==null)retcolmg = new Color[]{mgcolours[m]};
                    else retcolmg = u.addcolor(retcolmg, mgcolours[m]);
                    continue loop1;
                 }
              }
          }
          loop1:for(int n = 0; markg2!=null && n < markg2.length; n++){
              for(int m = 0; m < m2games.length; m++){
                 if(markg2[n]==m2games[m]){
                    if(retcolmg2==null)retcolmg2 = new Color[]{mg2colours[m]};
                    else retcolmg2 = u.addcolor(retcolmg2, mg2colours[m]);
                    continue loop1;
                 }
              }
          }
     }
      int maxno = 42;
      int effectivemaxno = 35;
      boolean overtwopages = false;
      if(games!=null){
          overtwopages = games.length > maxno;
      }
      if(all && overtwopages){
          if(alltype == 0)alltype = 1;
          if(alltype == 1){
              int hgames[] = new int[effectivemaxno];
              System.arraycopy(games,0,hgames,0,effectivemaxno);
              Color hgcolours[] = new Color[effectivemaxno];
              System.arraycopy(gcolours,0,hgcolours,0,effectivemaxno);
              games = hgames;
              gcolours = hgcolours;
          }
          else if(alltype == 2){
              int rem = games.length - effectivemaxno;
              int hgames[] = new int[rem];
              System.arraycopy(games,effectivemaxno,hgames,0,rem);
              Color hgcolours[] = new Color[rem];
              System.arraycopy(gcolours,effectivemaxno,hgcolours,0,rem);
              games = hgames;
              gcolours = hgcolours;
          }
      }
      else{
          alltype = 0;
      }
      layoutgames(games, gcolours, recommended?markg:null, recommended?retcolmg:null,
              recommended?markg2:null, recommended?retcolmg2:null, overtwopages);
  }


    void layoutgames(int[] games, Color[] gcols, int[]mg, Color[] mgcols, int[] mg2, Color[] mg2cols, boolean overtwopages) {

        if(gameheadings.length == 0){
            return;
        }
        int rowno = 6;
        
        int colno = 7;

        int allh = mover.HEIGHT-tabpanetop;
        int giconh = (allh/rowno)*15/18;//     mover.HEIGHT*5/34;
        int gicongap = giconh*5/32;
        int curry;
        int ms[];
        float upalittle = 0.4f;  // 0.5f is vertically in the middle of the screen
        
        if(mg!=null){
            if(mg2==null)
                ms = mg;
            else
                ms = mg.length > mg2.length ? mg : mg2;
        }
        else
            ms = games;
        if(ms==null)return;
        int gtotal = ms.length;
        int cols;
        int rows;
        int rem;
        cols = (overtwopages || gtotal>36)?7:6;
        if(gtotal<=cols){
            cols = gtotal;
            rows = 1;
        }
        else{
            rows = gtotal/cols;
            rem  = gtotal%cols;
            if(rem!=0)rows++;
            if(rem==1){
                if(gtotal<30){
                    cols--;
                    rows = gtotal/cols;
                    rem = gtotal%cols;
                    if(rem!=0)rows++;
                }
                else{
                    cols++;
                    rows = gtotal/cols;
                    rem = gtotal%cols;
                    if(rem!=0)rows++;
                }
            }
        }
        int xxx = (mover.WIDTH - (colno*giconh) - ((colno-1)*gicongap))/2;
        int iconx = (mover.WIDTH - (cols*giconh) - ((cols-1)*gicongap))/2;
        int currx = iconx;


        gamePanel.removeGamesIcons();

        gamerect = new Rectangle[gamename.length];
        int oriy;
        int yyy;
        boolean firstpage = false;
        if(overtwopages && rows==5){
            firstpage = true;
            rows = 6;
        }
        if(games!=null){           
            oriy = curry = (int)((allh-((rows*giconh) +((rows-1)*gicongap)))*upalittle) + (firstpage?gicongap:0);
            yyy = (int)((allh-((rowno*giconh) +((rowno-1)*gicongap)))*upalittle);
            for(int i = 0; i < games.length; i++){
                int r = (i+1)%cols;
                Rectangle rect = new Rectangle(currx,curry+ tabpanetop,giconh,giconh);
                gamerect[games[i]] = rect;
                new GamesIcon_base(gamePanel,
                     rect,
                      gameiconlist[games[i]],
                      gamename[games[i]], col2, gcols[i], true, Demo_base.isDemo && (gameflag.get(gamename[games[i]])).demoexclude);
//                        gameiconlist[games[i]].h = rect.height;
//        gameiconlist[games[i]].w = rect.width;
//                gamePanel.addMover(gameiconlist[games[i]], rect.x, rect.y);
                currx = iconx +(r*(giconh+gicongap));
                if(i!=0 && r==0){
                    curry+=giconh+gicongap;
                    currx = iconx;
                }
            }
            pagerect = null;

            if(spellingonly){
               if(!gameTree.overrideSuperSpelling){
                    supertm = new mover.formattedtextmover(u.gettext("superbutton","spellingonly"),
                            Color.white,
                             treefont.deriveFont((float)treefont.getSize()+4),
                             gamePanel,
                             false,
                             Font.BOLD);
//                   gamePanel.addMover(supertm, iconx, tabpanetop + (oriy/2) - (supertm.h/2));
                   gamePanel.addMover(supertm, Math.min(iconx, ((mover.WIDTH/2)-supertm.w/2)), tabpanetop + (oriy/2) - (supertm.h/2));
               }
            }
            else if(alltype > 0){
                if(alltype == 1){
                    nextpagetm = new mover.formattedtextmover(strtabnextpage,
                            new Color(70,70,70),
                             sharkStartFrame.treefont.deriveFont((float)treefont.getSize()+4),
                             gamePanel,
                             false,
                             Font.BOLD);
                    nextpagetm.handcursor = true;
                    int movx = xxx + (giconh*colno) + (gicongap*(colno-1)) - nextpagetm.w - gicongap;
                    int movy = tabpanetop + yyy + (giconh*(rowno-1)) + (gicongap*(rowno-2)) + giconh/2;
                   gamePanel.addMover(nextpagetm,
                           movx,
                           movy
                           );
                   pagerect = new Rectangle(movx, movy, nextpagetm.w, nextpagetm.h);
                }
                else if(alltype == 2){
                    prevpagetm = new mover.formattedtextmover(strtabprevpage,
                            new Color(70,70,70),
                             treefont.deriveFont((float)treefont.getSize()+4),
                             gamePanel,
                             false,
                             Font.BOLD);
                    prevpagetm.handcursor = true;
                    int movx = xxx + (giconh*colno) + (gicongap*(colno-1)) - prevpagetm.w - gicongap;
                    int movy = tabpanetop + yyy + (giconh*(rowno-1)) + (gicongap*(rowno-2)) + giconh/2;
                   gamePanel.addMover(prevpagetm,
                           movx,
                           movy
                           );
                   pagerect = new Rectangle(movx, movy, prevpagetm.w, prevpagetm.h);
                }

            }
        }
        else if(mg!=null){
            int texth = giconh/2;
            int row2 = -1;
            int rem2 = -1;
            int mgh = texth+(rows*giconh)+((rows-1)*gicongap);
            int mgh2 = -1;
            if(mg2!=null){
                row2 = mg2.length/cols;
                rem2 = mg2.length%cols;
                if(rem2>0)row2++;
                mgh2 = texth + (row2*giconh)+((row2-1)*gicongap);
                int totcontent = mgh+mgh2+texth;
                curry = tabpanetop + (int)((allh-totcontent)*upalittle);
            }
            else{
                curry = tabpanetop + (int)((allh-mgh)*upalittle);
            }
            recommendtm = new mover.formattedtextmover2(iconheading,
                    Color.white,
                     sharkStartFrame.treefont.deriveFont((float)sharkStartFrame.treefont.getSize()+4),
                     gamePanel,
                     false,
                     Font.BOLD);
            gamePanel.addMover(recommendtm, currx, curry);
            curry+=texth;
            for(int i = 0; i < mg.length; i++){
                int r = (i+1)%cols;
                Rectangle rect = new Rectangle(currx,curry,giconh,giconh);
                gamerect[mg[i]] = rect;
                new GamesIcon_base(gamePanel,
                     rect,
                      gameiconlist[mg[i]],
                      gamename[mg[i]], col2, mgcols[i], true, Demo_base.isDemo && (gameflag.get(gamename[mg[i]])).demoexclude);
                currx = iconx +(r*(giconh+gicongap));
                if(i!=0 && i!=mg.length-1  && r==0){
                    curry+=giconh+gicongap;
                    currx = iconx;
                }
            }
            if(mg2!=null){
                currx = iconx;
                curry+=giconh+texth;
                alsorecommendtm = new mover.formattedtextmover2(iconheading2,
                    Color.white,
                    sharkStartFrame.treefont.deriveFont((float)sharkStartFrame.treefont.getSize()+4),
                    gamePanel,
                    false,
                     Font.BOLD);
                gamePanel.addMover(alsorecommendtm, currx, curry);
                curry+=texth;
                for(int i = 0; i < mg2.length; i++){
                    int r = (i+1)%cols;
                    Rectangle rect = new Rectangle(currx,curry,giconh,giconh);
                    gamerect[mg2[i]] = rect;
                    new GamesIcon_base(gamePanel,
                        rect,
                        gameiconlist[mg2[i]],
                        gamename[mg2[i]], col2, mg2cols[i], true, Demo_base.isDemo && (gameflag.get(gamename[mg2[i]])).demoexclude);
                    currx = iconx +(r*(giconh+gicongap));
                    if(i!=0 && r==0){
                        curry+=giconh+gicongap;
                        currx = iconx;
                    }
                }
            }
        }

    }



  /**
   * Games are set up if: -
   * <li>This is a network version
   * <li>The current student is not an administrator
   * <li>There is a student in the student list who can override their lists
   * <li>"_refresh" occurrs in the student's database
   * @return true if games are set up
   */
  public boolean refreshprog() {
     if(!studentList[currStudent].administrator && !override()
            && studentList[currStudent].doupdates(true,false)) {
        setStudentMenu();
        setupgames();
        return true;
     }
     return false;
  }
//startPR2007-02-16^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  void setPrintMenu(boolean isListExtended){
    if(isListExtended){
      printlist.add(mprintflash2);
      printlist.addSeparator();
      printlist.add(mprint1);
      printlist.add(mprintex);
      printlist.addSeparator();
      printlist.add(mprintfg);
      printMenuSetForExtended = true;
    }
    else{
      printlist.add(mprintflash);
      printlist.add(mprintflash2);
      printlist.addSeparator();
      printlist.add(mprint1);
      printlist.add(mprint2);
      printlist.add(mprint4);
      printlist.add(mprint8);
      printlist.addSeparator();
      printlist.add(mprintfg);
      printMenuSetForExtended = false;
    }
  }

  public void setMenuForExtended(boolean isListExtended){
    boolean changed = false;
    if(isListExtended){
      if(!printMenuSetForExtended){
        printlist.removeAll();
        setPrintMenu(true);
        changed = true;
      }
    }
    else{
      if(printMenuSetForExtended){
        printlist.removeAll();
        setPrintMenu(false);
        changed = true;
      }
    }
    if (changed)
      printlist.validate();
  }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2008-08-20^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  // the words change after each game unless splits are in use and
  // a splitting game has been played
  public void superSetup(String name){
    if(currPlayTopic.superlist) {
      gameflag ff = gameflag.get(name);
      if(wordTree.unsplit || !ff.needanysplit  && !ff.needsyllsplit){
        wordTree.reset();
        wordTree.splitCB.setSelected(false);
        currPlayTopic.splitwords = null;
        wordTree.redrawing = true;
        wordTree.setup(currPlayTopic, null);
        wordTree.redrawing = false;
      }
    }
  }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  /**
   * <p>Title: WordShark</p>
   * <p>Description:  </p>
   * <p>Copyright: Copyright (c) 2003</p>
   * <p>Company: WhiteSpace</p>
   * @author Roger Burton
   * @version 1.0
   */
  class runMoversgame extends runMovers {
     public void mouseclick(int x,int y) {                              //Event 1
       wordlist.removeCurrPic();
       if(sharedp!=null && sharedp.viewshares)return;
       int i,j;
       int mx = x * mover.WIDTH/screenwidth;
       int my = y * mover.HEIGHT/screenheight;
       if(tabrect==null)return;
       for(i=0;i<tabrect.length;++i) {                           //For 1.1
           if(tabrect[i] != null           //If 1.1.1 - The game is active
              && tabrect[i].x <= mx                //AND has a game rectangle
              && tabrect[i].y <= my                //AND the mouse is in the game rectangle.
              && tabrect[i].x + tabrect[i].width >= mx
              && tabrect[i].y + tabrect[i].height >= my) {
                changetab(i);
           }
       }
       if(pagerect != null
              && pagerect.x <= mx
              && pagerect.y <= my
              && pagerect.x + pagerect.width >= mx
              && pagerect.y + pagerect.height >= my) {
               if(alltype==1)alltype=2;
               else if (alltype==2)alltype=1;
               changetab(-1);
       }
        for(i=0;i<gamerect.length;++i) {                           //For 1.1
           if(gamerect[i] != null           //If 1.1.1 - The game is active
              && gamerect[i].x <= mx                //AND has a game rectangle
              && gamerect[i].y <= my                //AND the mouse is in the game rectangle.
              && gamerect[i].x + gamerect[i].width >= mx
              && gamerect[i].y + gamerect[i].height >= my) {
          if(System.currentTimeMillis() > lastgamestart+2000) {
               if(oksplit(gamename[i]) && (!Demo_base.isDemo || !(gameflag.get(gamename[i])).demoexclude)
                      && (!(gameflag.get(gamename[i])).owllackpics && !(gameflag.get(gamename[i])).owllackrecs && 
                       !(gameflag.get(gamename[i])).owllackextrarecs)
                       ) {
                     Point pt = getLocationOnScreen();           //All words in topic are split
                     mouseonscreenx = x+pt.x;
                     mouseonscreeny = y+pt.y;
                     startgame(gamename[i]);
                     mouseOutside = true;
                  }
                  return;
               }
           }
        }
     }
     public void mousemoved(int x,int y) {   
        super.mousemoved(x,y);
        if(sharkStartFrame.mainFrame.mwanthelp.isSelected() || (currPlayTopic != null && currPlayTopic.ownlist)){
            boolean overanyrect = false;
//            for(short i=0;i<gametitle.length;++i) {
            for(short i=0;i<gamename.length;++i) {
                 if(gamerect[i] != null 
                     && gamerect[i].x <= mousex
                     && gamerect[i].y <= mousey
                     && gamerect[i].x + gamerect[i].width >= mousex
                     && gamerect[i].y + gamerect[i].height >= mousey) {
                    overanyrect = true;
                  int tooltipdelay = 0;  
                  if( ( lastrect != i && lastrect>0 && gamerect.length > gamerect.length  &&  gamerect[lastrect]!=null &&
                         (lastrect<0 || gamerect[i].x != gamerect[lastrect].x
                                   || gamerect[i].y != gamerect[lastrect].y)
                        || gamePanel.tooltipmover1==null)){
                  lastrect = i;
                  String s = null;
                  if(Demo_base.isDemo && gameflags[i].demoexclude){
                      s = Demo_base.demogettext("democd", "gamenotin", false);
                      s += "|_______________________________________|" +
                      gametooltip[i];
                  }
                  else{
                     boolean ownlist = currPlayTopic.databaseName.equals(studentList[currStudent].name);
                     boolean showinfo = false;
                     if(gameflags[i].owllackextrarecs && gameflags[i].owllackpics)
                      {
                          String ss = strrecordingtype = strrecordingtype.replaceFirst("%", currPlayTopic.definitions?strdefinition:strtranslation);
                          String stext = ownlist?str_gamewarnaddpicrec:str_gamewarnaddpicrec2;
                          s = stext = stext.replaceFirst("@", ss);
                          if(ownlist)s+="|"+str_gamewarownlist;
                          s += "|_______________________________________||" +
                          gametooltip[i];
                          showinfo = true;
                      }                      
                     else if(gameflags[i].owllackpics){
                          s = ownlist?str_gamewarnaddpic:str_gamewarnaddpic2;
                          if(currPlayTopic.databaseName.equals(studentList[currStudent].name))s+="|"+str_gamewarownlist;
                          s += "|_______________________________________||" +
                          gametooltip[i];
                          showinfo = true;
                      }
                     else if(gameflags[i].owllackextrarecs)
                      {
                          String ss = strrecordingtype = strrecordingtype.replaceFirst("%", currPlayTopic.definitions?strdefinition:strtranslation);
                          String stext = ownlist?str_gamewarnaddextrarec:str_gamewarnaddextrarec2;
                          s = stext = stext.replaceFirst("@", ss);
                          if(currPlayTopic.databaseName.equals(studentList[currStudent].name))s+="|"+str_gamewarownlist;
                          s += "|_______________________________________||" +
                          gametooltip[i];
                          showinfo = true;
                      }
                      else if(gameflags[i].owllackrecs)
                      {
                          s = ownlist?str_gamewarnaddrec:str_gamewarnaddrec2;
                          if(currPlayTopic.databaseName.equals(studentList[currStudent].name))s+="|"+str_gamewarownlist;
                          s += "|_______________________________________||" +
                          gametooltip[i];
                          showinfo = true;
                      }
                      else if(sharkStartFrame.mainFrame.mwanthelp.isSelected())
                          s = gametooltip[i];
                      if(showinfo)
                          tooltipdelay = -tooltipmoverdelay+50;
                  }
                  if(s!=null)
                      new tooltipmover(s, gamerect[i].x, gamerect[i].y,
                         gamerect[i].x + gamerect[i].width,
                         gamerect[i].y + gamerect[i].height,
                         tooltipdelay);
                  return;
                 }
              }
            }
             if(!overanyrect){
                gamePanel.removeTooltip();
                
             }
        }
     }


  

  }



  /**
   * <p>Title: WordShark</p>
   * <p>Description: Frame used to input adjustments to the volume of
   * beeps, groans, squeeks and plops.</p>
   * <p>Copyright: Copyright (c) 2003</p>
   * <p>Company: WhiteSpace</p>
   * @author Sara
   * @version 1.0
   */
  void  addadjustbeepvol(String students1[],JDialog ww) {
    new adjustbeepvol(screenSize.width / 4, screenSize.height / 4,
                           screenSize.width / 3, screenSize.height / 3, students1, ww,false,false,null );
  }
  void  addadjustbeepvol(String students1[],JDialog ww,boolean xbeepvol,boolean xnogroan,student firststu) {
    new adjustbeepvol(screenSize.width / 4, screenSize.height / 4,
                           screenSize.width / 3, screenSize.height / 3, students1, ww,xbeepvol,xnogroan,firststu);
  }
  void  addadjustbeepvol(String students1[]) {
    new adjustbeepvol(screenSize.width / 4, screenSize.height / 4,
                           screenSize.width / 3, screenSize.height / 3, students1);
  }



 class mainsettings extends JDialog {
     JDialog settingsjd;

    public mainsettings() {
       super(sharkStartFrame.mainFrame);
       settingsjd = this;
       getContentPane().setLayout(new GridBagLayout());
       setTitle(u.gettext("adminsettings", "title"));
       int sw = sharkStartFrame.mainFrame.getWidth();
       int sh = sharkStartFrame.mainFrame.getHeight();
       int sw2 = sharkStartFrame.mainFrame.getWidth()*3/4;
       int sh2 = sharkStartFrame.mainFrame.getHeight()*3/4;
//       setBounds((sw-sw2)/2, (sh-sh2)/2, sw2, sh2);
       setBounds(u2_base.adjustBounds(new Rectangle((sw-sw2)/2, (sh-sh2)/2, sw2, sh2)));
       setModal(true);
       GridBagConstraints grid = new GridBagConstraints();
       grid.weightx = 0;
       grid.fill = GridBagConstraints.BOTH;
       JPanel titlebar = new JPanel(new GridBagLayout());
       settings sets = new settings(this, true, false);
       grid.weighty = 0;
       titlebar.setBackground(Color.gray);
       grid.insets = new Insets(0,10,0,0);
       JLabel title = new JLabel(u.gettext("admintitles", "unisettings"));
       title.setForeground(Color.white);
              grid.gridx  = -1;
       grid.gridy = 0;
       titlebar.add(title, grid);
       grid.insets = new Insets(0,10,0,0);
     titlebar.add(sets.hlsignon, grid);
     grid.insets = new Insets(0,0,0,0);
     titlebar.add(sets.hlnoise, grid);
     titlebar.add(sets.hlfont, grid);
     titlebar.add(sets.hlpicture, grid);
     titlebar.add(sets.hlkeypad, grid);
     titlebar.add(sets.hlreward, grid);
     titlebar.add(sets.hlgame, grid);
     titlebar.add(sets.hlcourse, grid);
     titlebar.add(sets.hlmisc, grid);
     titlebar.add(sets.hluserrecords, grid);
     grid.weightx = 1;
     JPanel filler = new JPanel();
     filler.setOpaque(false);
     titlebar.add(filler, grid);
       grid.insets = new Insets(0,0,0,0);
       grid.gridx  = 0;
       grid.gridy = -1;
       getContentPane().add(titlebar, grid);

       JPanel buttonpan = new JPanel(new GridBagLayout());
       buttonpan.setBorder(BorderFactory.createEtchedBorder());
       JButton closebt = u.sharkButton();
       closebt.setText(u.gettext("close", "label"));

       closebt.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               settingsjd.dispose();
           }
       });
       grid.fill = GridBagConstraints.NONE;
       grid.insets = new Insets(10,0,10,0);
       buttonpan.add(closebt, grid);
       grid.insets = new Insets(0,0,0,0);
        grid.fill = GridBagConstraints.BOTH;
       grid.weighty = 1;
       getContentPane().add(sets, grid);
       grid.weighty = 0;
       getContentPane().add(buttonpan, grid);
       setVisible(true);
     }
  }





public class ImportMess extends IntroFrame_base {
  String[] args;
  GridBagConstraints grid = new GridBagConstraints();
  ButtonGroup bg = new ButtonGroup();
  JRadioButton r1;
  JRadioButton r2;
  IntroFrame_base thisjd;
  byte state;
  static final byte DECLINE = 0;
  static final byte ACCEPT = 1;
  JButton ok;
  String filename;
  JPanel pnProgressBar;
  JLabel lbChecking;




  public ImportMess(String sts) {
    super(true);
    filename = sts;
    grid.weighty = 1;
    grid.weightx = 1;
    grid.fill = GridBagConstraints.NONE;
    grid.gridx = 0;
    grid.gridy = -1;
    thisjd = this;
    closeonexit = false;
    String s3 = u.gettext("importchoice", "text");
    s3 = s3.replaceFirst("%", shark.programName);
    String s4 = filename.substring(0, filename.lastIndexOf(shark.sep));
    String sss[] = s3.split("%");
    s4 = sss[0]+ s4+sss[1];
    lbChecking = new JLabel(  u.convertToHtml(s4, true) );
    JPanel textPanel = new JPanel(new GridBagLayout());
    textPanel.setPreferredSize(new Dimension(this.getWidth()/2,this.getHeight()/2));
    grid.fill = GridBagConstraints.NONE;


    pnProgressBar = new JPanel(new GridBagLayout());
    JProgressBar pBar = new JProgressBar();
    pBar.setIndeterminate(true);
    pnProgressBar.setVisible(false);

    grid.anchor = GridBagConstraints.CENTER;
    pnProgressBar.add(new JLabel(u.gettext("importchoice", "importing")), grid);
    grid.insets = new Insets(border,0,0,0);
    pnProgressBar.add(pBar, grid);
    grid.insets = new Insets(0,0,0,0);


    grid.fill = GridBagConstraints.NONE;
    grid.anchor = GridBagConstraints.CENTER;
    textPanel.add(lbChecking, grid);
    textPanel.add(pnProgressBar, grid);

    grid.fill = GridBagConstraints.NONE;
    grid.anchor = GridBagConstraints.EAST;
    grid.insets = new Insets(border,0,0,0);
    grid.insets = new Insets(0,0,0,0);
    grid.anchor = GridBagConstraints.CENTER;

    grid.fill = GridBagConstraints.NONE;
    JPanel choicePanel = new JPanel(new GridBagLayout());
    r1 = new JRadioButton(u.gettext("importchoice", "choice1"));
    r2 = new JRadioButton(u.gettext("importchoice", "choice2"));
    bg.add(r1);
    bg.add(r2);
    r1.setOpaque(false);
    r2.setOpaque(false);
    r2.setSelected(true);
    grid.anchor = GridBagConstraints.WEST;
    choicePanel.add(r1, grid);
    choicePanel.add(r2, grid);
    grid.anchor = GridBagConstraints.CENTER;
    ok = new JButton(u.gettext("ok", "label"));

    ok.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if(r1.isSelected()){
          ok.setEnabled(false);
          pnProgressBar.setVisible(true);
          lbChecking.setVisible(false);
          r1.setVisible(false);
          r2.setVisible(false);
          CopyFiles cf = new CopyFiles(filename);
          new Thread(cf).start();
        }
        else thisjd.dispose();
      }
    });

    r1.setSelected(true);
    JPanel buttonPanel = new JPanel(new GridBagLayout());
    grid.gridx = -1;
    grid.gridy = 0;
    grid.insets = new Insets(0,0,0,0);
    buttonPanel.add(ok, grid);
    grid.gridx = 0;
    grid.gridy = -1;
    JPanel contentPanel = new JPanel(new GridBagLayout());
    contentPanel.add(textPanel, grid);
    contentPanel.add(choicePanel, grid);
    contentPanel.add(buttonPanel, grid);
    grid.fill = GridBagConstraints.BOTH;
    addToBase(contentPanel, grid);
    setTitle(u.gettext("importchoice", "title"));
    setColor();
    this.setVisible(true);
  }

    private class CopyFiles implements Runnable {
        String from;

        public CopyFiles(String s) {
            from = s;
        }

        public void copyDirectory(File srcPath, File dstPath)
                                   throws IOException{
            if (srcPath.isDirectory()){
                if (!dstPath.exists()){
                    dstPath.mkdir();
                }
                String files[] = srcPath.list();
                for(int i = 0; i < files.length; i++){
                    copyDirectory(new File(srcPath, files[i]),
                         new File(dstPath, files[i]));
                }
            }
            else{
                if(!srcPath.exists()){
                    return;
                }
                else
                {
                    InputStream in = new FileInputStream(srcPath);
                    OutputStream out = new FileOutputStream(dstPath);
                    // Transfer bytes from in to out
                    byte[] buf = new byte[1024];
                    int len;
                    while ((len = in.read(buf)) > 0) {
                        out.write(buf, 0, len);
                    }
                    in.close();
                    out.close();
                    u.setNewFilePermissions(dstPath);
                }
            }
            
        }

        public void run() {
            try {
                File f = new File(from);
                f.mkdirs();
                if (!f.exists()) {
                    return;
                }
                copyDirectory(f, sharkStartFrame.sharedPath);
                thisjd.dispose();
            } catch (Exception b) {
            }
        }
    }
}


class rewardGamesDialog extends JDialog{
   Font plainfont = sharkStartFrame.treefont.deriveFont(Font.PLAIN);
   Font smallerplainfont = plainfont.deriveFont((float)plainfont.getSize()-1);
   JCheckBox mrewards[];
   JCheckBox mrewardfreqs[];
   JCheckBox mnoreward;
   JDialog thisdia = this;
   
    public rewardGamesDialog(String excluded[]){
        super(sharkStartFrame.mainFrame);
        this.setModal(true);
        this.setResizable(false);
        int w = sharkStartFrame.mainFrame.getWidth();
        int h = sharkStartFrame.mainFrame.getHeight();
        int ww;
        int hh;
        if(!u.screenResWidthMoreThan(1000)){
            ww = w*10/20;
            hh = h*10/20;
        }
        else if (!u.screenResWidthMoreThan(1300)){
            ww = w*9/20;
            hh = h*9/20;           
        }
        else {
            ww = w*8/20;
            hh = h*8/20;           
        }
//        this.setBounds((w-ww)/2, (h-hh)/2, ww, hh);
        this.setBounds(u2_base.adjustBounds(new Rectangle((w-ww)/2, (h-hh)/2, ww, hh)));
        String title = u.gettext("adminsettings", "reward");
        this.setTitle(title);

        GridBagConstraints grid = new GridBagConstraints();
        grid.fill = GridBagConstraints.NONE;
        grid.insets = new Insets(10,10,10,10);
        JPanel rewardpn = new JPanel(new GridBagLayout());
        JPanel rewardfreqpn = new JPanel(new GridBagLayout());
        JPanel rewardpn2 = new JPanel(new GridBagLayout());
        JPanel rewardfreqpn2 = new JPanel(new GridBagLayout());
        rewardpn2.add(rewardpn, grid);
        rewardfreqpn2.add(rewardfreqpn, grid);
        grid.insets = new Insets(0,0,0,0);
        rewardpn2.setBorder(BorderFactory.createEtchedBorder());
        rewardfreqpn2.setBorder(BorderFactory.createEtchedBorder());
        String rw[] = sharkStartFrame.rw;
        

    mrewards = new JCheckBox[rw.length];
    int half = rw.length/2;
    int xx = 0;
    int yy = 0;
    boolean newcol = false;
    grid.anchor = GridBagConstraints.WEST;
    for (int i = 0; i < rw.length; ++i) {
         grid.gridx = xx;
         grid.gridy = yy++;
         if(i>3 && !newcol){
             newcol = true;
             xx++;
             yy = 0;
         }         
        rewardpn.add(mrewards[i] = new JCheckBox(rw[i]), grid);
        mrewards[i].setToolTipText(u.gettext("msetrewards","tooltip"));
        mrewards[i].setFont(smallerplainfont);
        mrewards[i].setEnabled(u.findString(excluded, rw[i]) < 0);
        mrewards[i].setSelected(mrewards[i].isEnabled() && u.findString(okrewards, mrewards[i].getText()) >= 0);
        mrewards[i].addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          String okrewards2[] = new String[0];
          String okrewards3[] = new String[0];
          String ss[] = null;
          String s = student.optionstring("s_okrewards");               // start rb 16/2/06
          if(s!=null) ss = u.splitString(s);
          int visno = 0;
          for (short i = 0; i < mrewards.length; ++i) {
            if (mrewards[i].isVisible()){
                visno++;
                if(mrewards[i].isSelected())
                    okrewards2 = u.addString(okrewards2, mrewards[i].getText());
            }
            else if(ss==null || u.findString(ss, mrewards[i].getText())>=0){
                okrewards3 = u.addString(okrewards3, mrewards[i].getText());
            }
          }
          if(okrewards2.length < 3) {
            okrewards2 = new String[0];
            for (short i = 0; i < mrewards.length; ++i) {
              if(mrewards[i].getActionListeners()[0] == this)  mrewards[i].setSelected(true);
              if (mrewards[i].isVisible() && mrewards[i].isSelected())  okrewards2 = u.addString(okrewards2, mrewards[i].getText());
            }
          }
          if(visno<4)return;
          okrewards = okrewards2;
          if(okrewards.length == mrewards.length)
              student.removeOption("s_okrewards");
          else student.setOption("s_okrewards", u.combineString(u.addString(okrewards, okrewards3)));
        }
      });
    }        
 
     grid.gridx = 0;
     grid.gridy = -1;
     mnoreward = new JCheckBox(u.gettext("mnoreward", "label"));
     mnoreward.setFont(smallerplainfont);
     mnoreward.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        // start rb 27/10/06
       if(mnoreward.isSelected()) {
         int i;
         studentList[currStudent].setOption("noreward");
         for (i = 0; i < 4; ++i) {
           mrewardfreqs[i].setSelected(false);
         }
       }
       else {
         mnoreward.setSelected(true);   // can only be set on
       }
      }
    });

    mrewardfreqs = new JCheckBox[] {new JCheckBox(u.gettext("mreward1", "label")),new JCheckBox(u.gettext("mreward2", "label")),new JCheckBox(u.gettext("mreward3", "label")), new JCheckBox(u.gettext("mreward4", "label"))};
    short i;
    for (i = 0; i < 4; ++i) {
      mrewardfreqs[i].setFont(smallerplainfont);
      mrewardfreqs[i].addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          int i;
          for (i = 0; i < 4; ++i) {
             if(mrewardfreqs[i].getActionListeners()[0] == this) {
                 mrewardfreqs[i].setSelected(true);
                 if(currStudent>=0)studentList[currStudent].rewardfreq = (byte)i;
                   // start  rb 27/10/06
                 mnoreward.setSelected(false);
                 studentList[currStudent].clearOption("noreward");
                  // end  rb 27/10/06
             }
             else mrewardfreqs[i].setSelected(false);
          }
        }
      });
      rewardfreqpn.add(mrewardfreqs[i], grid);
    }
    rewardfreqpn.add(mnoreward); // rb 27/10/06    
     mnoreward.setSelected(studentList[currStudent].option("noreward"));
     for(i=0;i<4;++i){
        mrewardfreqs[i].setSelected(!mnoreward.isSelected() && i == studentList[currStudent].rewardfreq); 
     }
     rewardfreqpn.add(mnoreward, grid);
     grid.gridx = 0;
     grid.gridy = -1;
     JPanel rewardpn3 = new JPanel(new GridBagLayout());
     JPanel rewardfreqpn3 = new JPanel(new GridBagLayout());
     grid.weighty = 0;
     JLabel rew3 = new JLabel(u.convertToHtml(u.gettext("adminsettings", "rewardchoice2")));
     JLabel rewf3 = new JLabel(u.convertToHtml(u.gettext("adminsettings", "rewardfreq")));
     rew3.setFont(smallerplainfont);
     rewf3.setFont(smallerplainfont);
     grid.insets = new Insets(0,0,10,0);
     rewardpn3.add(rew3, grid);
     rewardfreqpn3.add(rewf3, grid);
     grid.insets = new Insets(0,0,0,0);
     grid.weighty = 1;
     grid.insets = new Insets(0,0,0,20);
     rewardpn3.add(rewardpn2, grid);
     grid.insets = new Insets(0,0,0,0);
     rewardfreqpn3.add(rewardfreqpn2, grid);
     grid.gridx = -1;
     grid.gridy = 0;
     grid.anchor = GridBagConstraints.CENTER;
     JPanel rewardmainpn = new JPanel(new GridBagLayout());
     JPanel rewardmainpn2 = new JPanel(new GridBagLayout());
     rewardmainpn2.add(rewardpn3, grid);
     rewardmainpn2.add(rewardfreqpn3, grid);
     grid.weightx = 1;
     grid.fill = GridBagConstraints.BOTH;
     grid.gridx = 0;
     grid.gridy = -1;
     JLabel settingstitlelabel = new JLabel(title+":");
     settingstitlelabel.setForeground(Color.gray);
     grid.insets = new Insets(0,0,0,0);
     grid.weighty = 0;
     rewardmainpn.add(rewardmainpn2, grid);
     grid.weightx = 1;
     grid.gridx = -1;
     grid.gridy = 0;
     grid.weighty = 1;
     grid.fill = GridBagConstraints.NONE;
     grid.weightx = 0;
     this.getContentPane().setLayout(new GridBagLayout());
     grid.insets = new Insets(0,0,20,0);
     grid.gridx = 0;
     grid.gridy = -1;
     grid.insets = new Insets(0,0,0,0);
     this.getContentPane().add(rewardmainpn, grid);
     JButton jbOk = new JButton(u.gettext("OK", "label"));
     jbOk.addActionListener(new java.awt.event.ActionListener() {
           public void actionPerformed(ActionEvent e) {
             thisdia.dispose();
           }
     });
     grid.weighty = 0;
     grid.insets = new Insets(10,0,10,0);
     this.getContentPane().add(jbOk, grid);
     this.setVisible(true);
    }
    }





 class stupicpref
    extends JDialog {
    public int screenwidth;
    public int screenheight;
    GridBagLayout layout1 = new GridBagLayout();
    GridBagConstraints grid = new GridBagConstraints();

    public  stupicpref() {
       super(sharkStartFrame.mainFrame,true);
     setResizable(false);
     final stupicpref thisa = this;
     setIconImage(sharkicon);
     setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);


     int sw =  screenSize.width/2;
     int sh =  screenSize.height*5/12;
//     setBounds(new Rectangle((screenSize.width-sw)/2, (screenSize.height-sh)/2,sw,sh));
     setBounds(u2_base.adjustBounds(new Rectangle((screenSize.width-sw)/2, (screenSize.height-sh)/2,sw,sh)));
     getContentPane().setLayout(layout1);
     setEnabled(true);
     setTitle(u.gettext("picpreferences", "title"));

     grid.gridx = 0;
     grid.gridy = -1;
     grid.gridheight = 1;
     grid.weighty = 1;
     grid.weightx = 1;

     JButton exit = u.Button("vol_exit");
     exit.addActionListener(new java.awt.event.ActionListener() {
           public void actionPerformed(ActionEvent e) {
             thisa.dispose();
           }
     });
     picturepreferences pp = new picturepreferences(this, true, null, false);
     getContentPane().add(pp,grid);
    getContentPane().add(exit,grid);
    pp.setup();
    setVisible(true);
    validate();
     }


 
 }


public class doZipShared implements Runnable{
    boolean anon;

    public doZipShared(boolean anonymous){
        anon = anonymous;
    }
  public void run(){
                student.signoffAll();
                Calendar cal = new GregorianCalendar();
                String month = String.valueOf(cal.get(Calendar.MONTH) + 1);
                if(month.length()==1)month = "0"+month;
                String datename = "_"+cal.get(Calendar.YEAR)+"-"+month
                    +"-"+cal.get(Calendar.DAY_OF_MONTH)+"_"+cal.get(Calendar.HOUR_OF_DAY)+"h"+cal.get(Calendar.MINUTE)+"m";
                String maccode= "";
                try{
                    String t;
                    if((t=u2_base.getFirstMacAddressCode())!=null){
                        maccode = "_"+t;  
                    }
                }
                catch(Exception e){}
                u.zipFolder(sharkStartFrame.sharedPath.getAbsolutePath(),
                        u.getDesktopPath()
                        +shark.sep+shark.programName.toLowerCase()+"-shared"+maccode+datename+".zip",
                        false, anon);
                sharkStartFrame.mainFrame.finalize();
  }
}




 class adjustbeepvol
//startPR2004-08-27^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    extends JDialog {
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    public int screenwidth;
    public int screenheight;
    GridBagLayout layout1 = new GridBagLayout();
    GridBagConstraints grid = new GridBagConstraints();
    JSlider opView =  new JSlider(JSlider.HORIZONTAL, 0, 7, noise.beepvol);
    String students[];
    u.my3wayCheckBoxMenuItem nogroan;    // rb 27/10/06
    boolean xnogroan,xbeepvol;
    student firststu;
    public  adjustbeepvol(int x1,int y1,int w1, int h1,String students1[]) {
//startPR2004-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       super(sharkStartFrame.mainFrame,true);   // PR 13/11/06
       common(x1,y1,w1,h1,students1);
     }
     public  adjustbeepvol(int x1,int y1,int w1, int h1,String students1[], JDialog ww,
                           boolean xbeepvol1,boolean xnogroan1,student firststu1) {
//startPR2004-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       super(ww,true);        // pr 13/11/06
       xbeepvol = xbeepvol1;
       xnogroan = xnogroan1;
       firststu = firststu1;
       common(x1,y1,w1,h1,students1);
     }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2004-08-27^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     void common(int x1,int y1,int w1, int h1,String students1[]) {
     setResizable(false);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     final adjustbeepvol thisa = this;
     students = students1;
     setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
     opView.setToolTipText(u.gettext("vol_slider","tooltip"));
     int bv = -1;                       // start  rb 15/1/07
     if(xbeepvol)  {
       opView.setValue(bv=firststu.optionval2("s_beepvol"));
       opView.setBackground(Color.yellow);
     }
     else if(students != null && students.length>0 && firststu != null)
       opView.setValue(bv=firststu.optionval2("s_beepvol"));
     else opView.setValue(bv=student.optionval("s_beepvol"));
     if(bv<0) opView.setValue(settings.DEFAULTBEEPVOL);              // end  rb 15/1/07
     setIconImage(sharkicon);
     setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
//     setBounds(new Rectangle(x1, y1,w1,h1));
     setBounds(u2_base.adjustBounds(new Rectangle(x1, y1,w1,h1)));
     getContentPane().setLayout(layout1);
     setEnabled(true);
//startPR2007-05-17^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//     setTitle("Adjust volume of beeps, groans etc");
     setTitle(u.gettext("vol", "title"));
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     opView.setMajorTickSpacing(1);
     opView.addChangeListener(new ChangeListener() {
        public void stateChanged(ChangeEvent e) {
          if(xbeepvol)
            opView.setBackground(getContentPane().getBackground());
          if (students != null) { // v4
            student.queueupdate(students, new String[]{"beepvol",String.valueOf(opView.getValue())});
          }
          else {
            noise.beepvol = (byte) opView.getValue();
            student.setOption("s_beepvol", (short) noise.beepvol); // v4
            noise.setnew();
          }
        }
     });
     grid.gridx = 0;
     grid.gridy = -1;
     grid.gridheight = 1;
     grid.weighty = 1;
     grid.weightx = 1;
     getContentPane().add(opView, grid);
     JButton sample = u.Button("vol_test");
     sample.addActionListener(new java.awt.event.ActionListener() {
           public void actionPerformed(ActionEvent e) {
              byte save = noise.beepvol;                   // rb 15/1/07
              noise.beepvol = (byte) opView.getValue();    // rb 15/1/07
              noise.setnew();                              // rb 15/1/07
              noise.beep();
              u.pause(200);
              noise.groan();
              u.pause(200);
              noise.squeek();
              u.pause(200);
              noise.plop();
              noise.beepvol = save;                      // rb 15/1/07
              noise.setnew();                            // rb 15/1/07
           }
     });
     getContentPane().add(sample,grid);
     JButton exit = u.Button("vol_exit");
     exit.addActionListener(new java.awt.event.ActionListener() {
           public void actionPerformed(ActionEvent e) {
             thisa.dispose();
           }
     });
//startPR2004-09-03^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     // enables exiting screen via the ESC key
     opView.addKeyListener(new KeyAdapter() {
       public void keyPressed(KeyEvent e) {
         int code = e.getKeyCode();
         if(code == KeyEvent.VK_ESCAPE)
           thisa.dispose();
       }
     });
     sample.addKeyListener(new KeyAdapter() {
       public void keyPressed(KeyEvent e) {
         int code = e.getKeyCode();
         if(code == KeyEvent.VK_ESCAPE)
           thisa.dispose();
       }
     });
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      // rb start 27/10/06
     nogroan = u.get3wayCheckBoxMenuItem("nogroan");
     if(students == null) {
       nogroan.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(ActionEvent e) {
           if(nogroan.state != u.ALL) nogroan.setState(u.ALL);
             else  nogroan.setState(u.NONE);
           if (noise.nogroan = nogroan.state == u.ALL)
             student.setOption("s_nogroan");
           else
             student.clearOption("s_nogroan");
         }
       });
     }
     else {
       nogroan.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(ActionEvent e) {
           if(nogroan.state != u.ALL) nogroan.setState(u.ALL);
             else  nogroan.setState(u.NONE);
           if (noise.nogroan = nogroan.state == u.ALL)
                     student.queueupdate(students,new String[]{"nogroan","true"});
            else     student.queueupdate(students,new String[]{"nogroan"});
         }
       });
     }
     if(xnogroan) {
       nogroan.setState(u.SOME);
     }
     else nogroan.setState(students == null || students.length==0 ||firststu==null?
                             (student.option("s_nogroan")?u.ALL:u.NONE)
                              :(firststu.option2("s_nogroan")?u.ALL:u.NONE));
     nogroan.setBorder(null);
     getContentPane().add(nogroan,grid);
    // end rb 27/10/06
    getContentPane().add(exit,grid);
    setVisible(true);
    validate();
  }
 }
}




/**
* <p>Title: WhiteSpace</p>
* <p>Description: Listener for cut menu item</p>
* <p>Copyright: Copyright (c) 2003</p>
* <p>Company: WhiteSpace</p>
* @author Roger Burton
* @version 1.0
*/
class sharkStartFrame_cut_actionAdapter implements java.awt.event.ActionListener{
  sharkStartFrame adaptee;
  sharkStartFrame_cut_actionAdapter(sharkStartFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
//startPR2004-10-23^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    if(!sharkStartFrame.macBlock){
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      adaptee.cut_actionPerformed(e);
//startPR2004-10-23^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      sharkStartFrame.macBlock = false;
//    }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  }
}
/**
 * <p>Title: WhiteSpace</p>
 * <p>Description: Listener for copy menu item</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: WhiteSpace</p>
 * @author roger Burton
 * @version 1.0
 */
class sharkStartFrame_copy_actionAdapter implements java.awt.event.ActionListener{
  sharkStartFrame adaptee;
  sharkStartFrame_copy_actionAdapter(sharkStartFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
//startPR2004-10-23^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    if(!sharkStartFrame.macBlock){
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      adaptee.copy_actionPerformed(e);
//startPR2004-10-23^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      sharkStartFrame.macBlock = false;
//    }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  }
}
/**
 * <p>Title: WhiteSpace</p>
 * <p>Description: Listener for paste menu item</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: WhiteSpace</p>
 * @author Roger Burton
 * @version 1.0
 */
class sharkStartFrame_paste_actionAdapter implements java.awt.event.ActionListener{
  sharkStartFrame adaptee;
  sharkStartFrame_paste_actionAdapter(sharkStartFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
//startPR2004-10-23^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    if(!sharkStartFrame.macBlock){
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      adaptee.paste_actionPerformed(e);
//startPR2004-10-23^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      sharkStartFrame.macBlock = false;
//    }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  }
}
class sharkStartFrame_find_actionAdapter implements java.awt.event.ActionListener{
  sharkStartFrame adaptee;
  sharkStartFrame_find_actionAdapter(sharkStartFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.find_actionPerformed(e);
  }
}
class sharkStartFrame_findnext_actionAdapter implements java.awt.event.ActionListener{
  sharkStartFrame adaptee;
  sharkStartFrame_findnext_actionAdapter(sharkStartFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.findnext_actionPerformed(e);
  }
}
/**
 * <p>Title: WordShark</p>
 * <p>Description: Listener for paste image menu item</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: WhiteSpace</p>
 * @author Roger Burton
 * @version 1.0
 */
class sharkStartFrame_pasteimage_actionAdapter implements java.awt.event.ActionListener{
  sharkStartFrame adaptee;
  sharkStartFrame_pasteimage_actionAdapter(sharkStartFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.pasteimage_actionPerformed(e);
  }
}
/**
 * <p>Title: WordShark</p>
 * <p>Description: Listener for save menu item</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: WhiteSpace</p>
 * @author Roger Burton
 * @version 1.0
 */
class sharkStartFrame_save_actionAdapter implements java.awt.event.ActionListener{
  sharkStartFrame adaptee;
  sharkStartFrame_save_actionAdapter(sharkStartFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.save_actionPerformed(e);
  }
}
/**
 * <p>Title: WordShark</p>
 * <p>Description: Listener for undo menu item</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: WhiteSpace</p>
 * @author Roger Burton
 * @version 1.0
 */
class sharkStartFrame_undo_actionAdapter implements java.awt.event.ActionListener{
  sharkStartFrame adaptee;
  sharkStartFrame_undo_actionAdapter(sharkStartFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
//startPR2004-10-23^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    if(!sharkStartFrame.macBlock){
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      adaptee.undo_actionPerformed(e);
//startPR2004-10-23^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      sharkStartFrame.macBlock = false;
//    }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  }
}

class sharkStartFrame_ImageInfo_actionAdapter implements java.awt.event.ActionListener{
  sharkStartFrame adaptee;
  sharkStartFrame_ImageInfo_actionAdapter(sharkStartFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.ImageInfo_actionPerformed(e);
  }
}

/**
 * <p>Title: WordShark</p>
 * <p>Description: Listener for record menu item</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: WhiteSpace</p>
 * @author Roger Burton
 * @version 1.0
 */
class sharkStartFrame_record_actionAdapter implements java.awt.event.ActionListener{
  sharkStartFrame adaptee;
  sharkStartFrame_record_actionAdapter(sharkStartFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.record_actionPerformed(e);
  }
}

class sharkStartFrame_recordfl_actionAdapter implements java.awt.event.ActionListener{
  sharkStartFrame adaptee;
  sharkStartFrame_recordfl_actionAdapter(sharkStartFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.recordfl_actionPerformed(e);
  }
}

/**
 * <p>Title: WordShark</p>
 * <p>Description: Listener for choosesprite menu item</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: WhiteSpace</p>
 * @author Roger Burton
 * @version 1.0
 */
class sharkStartFrame_choosesprite_actionAdapter implements java.awt.event.ActionListener{
  sharkStartFrame adaptee;
  sharkStartFrame_choosesprite_actionAdapter(sharkStartFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.choosesprite_actionPerformed(e);
  }
}

class copyProtect_Work implements Runnable{
  public void run(){
    sharkStartFrame.mainFrame.checkcd();
  }
}

/**
 * <p>Title: WordShark</p>
 * <p>Description: Listener for changepassword menu item</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: WhiteSpace</p>
 * @author Roger Burton
 * @version 1.0
 */
class sharkStartFrame_changepassword_actionAdapter implements java.awt.event.ActionListener{
  sharkStartFrame adaptee;
  sharkStartFrame_changepassword_actionAdapter(sharkStartFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.changepassword_actionPerformed(e);
  }
}
/**
 * <p>Title: WordShark</p>
 * <p>Description: Listener for print menu item</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: WhiteSpace</p>
 * @author Roger Burton
 * @version 1.0
 */
class sharkStartFrame_print_actionAdapter implements java.awt.event.ActionListener {
  sharkStartFrame adaptee;
  sharkStartFrame_print_actionAdapter(sharkStartFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.print_actionPerformed(e);
  }
}
/**
 * <p>Title: WordShark</p>
 * <p>Description: Listener for pictures menu item</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: WhiteSpace</p>
 * @author Roger Burton
 * @version 1.0
 */
class sharkStartFrame_pictures_actionAdapter implements java.awt.event.ActionListener {
  sharkStartFrame adaptee;
  sharkStartFrame_pictures_actionAdapter(sharkStartFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.pictures_actionPerformed(e);
  }
}
