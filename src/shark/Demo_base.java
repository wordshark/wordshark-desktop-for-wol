package shark;

import java.awt.*;
import javax.swing.*;
import javax.swing.Timer;
/**
 * <p>Title: Your Product Name</p>
 * <p>Description: Your description</p>
 * <p>Copyright: Copyright (c) 1997</p>
 * <p>Company: Your Company</p>
 * @author Your Name
 * @version 1.0
 */


public class Demo_base {

  /**
   * true when the current version is a demo version
   */
  public static boolean isDemo = false;


  public static boolean showWordListInfo = false;


  public static String phonicstopics[] = null;
  /**
   * The number of 'turns' that have been completed in a demo game.
   */
  public static int demoTurnsCount = 0;

  /**
   * The number of reward games that have been played on this demo run.
   */
  public static int demoCount = 0;

  /**
   * The number of 'turns' wanted in a demo game before exiting out of it and
   * returning to the main screen.
   */
  public static int demoTurnsWanted = 2;

  static boolean isDialogOpen;
  public static final int DIALOGCENTER = 0;
  public static final int DIALOGWORDLISTRIGHT = 1;
  public static Color titleColor = new Color(0, 0, 128);

  public static boolean finishedSetup = false;
  public static JButton activeListen;
  public ImageIcon speakOn;
  public ImageIcon speak;
  public Timer listenColor;
  public static boolean shownRewardInfo = false;
  public static boolean shownHelpInfo = false;
  public static boolean shownListInfo = false;
  public static boolean blockTeachingNotes = true;

  public Demo_base() {

  }
  public void makeDialog(String t, String mt, String searchStr, JFrame jf, int pos, boolean but, boolean wantarr){
  }

  public void makeDialog(String t, String mt, String searchStr, JDialog jd, int pos, boolean but, boolean wantarr){
  }


  /**
   *
   * In the demo version this method is executed at the end of each game 'turn',
   * e.g. spelling one word. If the number of turns has reached the number wanted
   * the game exits.
   *
   * @param extra   The adjustment to the number of 'turns' required in a game.
   * eg a value of 1 will increase the number of 'turns' before exiting by one,
   * a value of -1 will decrease the number of 'turns' before exiting by one, a
   * value of 0 will result in no change.
   * @return true if the number of wanted 'turns' have been completed, false it not.
   */
  public static boolean demoIsReadyForExit(int extra) {
    return false;
  }
//startPR2007-12-20^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  public static boolean demoIsReadyForExit(int extra, boolean sooner) {
    return false;
  }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR

  public static void demoGameExit(){
  }

  /**
   * Gets publictext strings. Carriage returns are represented with either '\n'
   * or '|' characters.
   *
   * @param key  The main key of the publictext entry.
   * @param subkey  The subkey of the publictext entry.
   * @param convert  If true a carriage return is represented by '/n'. If false
   * a carriage return is represented by a '|' character.
   * @return  the publictext entry.
   */
  public static String demogettext(String key, String subkey, boolean convert) {
    return null;
  }
}

