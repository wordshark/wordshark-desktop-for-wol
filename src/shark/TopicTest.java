/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package shark;

import javax.swing.tree.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;
/**
 *
 * @author Paul Rubie
 */
public class TopicTest {
    
    // make sure usingsuperlist is false ??

    public static int stage = -1;
    public static boolean passed = true;
    public static boolean reachedEnd = false;
    public static topic previousTopic = null;
    public static String previousCourse = null;
    public static String started = null;
    public static int path = -1;
    public static final int PATH_EASY = 0;
    public static final int PATH_HARD = 1;

    public static ListenDialog listenMessage;
    public static String courseName = "Wordshark course";
    public String gameName = "spelling test";
    
    public static final int EASYSTAGEMAX = 5;
    public static final int HARDSTAGEMAX = 7;

    String tnameA = "Test A";
    String tname_e1 = "Spell Set 1";
    String tname_e2 = "Spell Set 2";
    String tname_e3 = "Spell Set 3";
    String tname_e4 = "Spell Sets 4 & 5";
    String tname_e5 = "Spell Sets 6 & 7";

    String tname_h1 = "Spell Sets 8 & 9";
    String tname_h2 = "Spell Sets 10 & 11";
    String tname_h3 = "Spell Sets 12 & 13";
    String tname_h4 = "Spell Sets 14 & 15";
    String tname_h5 = "Spell Sets 16 & 17";
    String tname_h6 = "Spell Sets 18 & 19";
    String tname_h7 = "Spell Sets 20 & 21";

    String tres_e1 = "introduce a";      // first list under 1)
    String tres_e2 = "sh";      // first list under 2)
    String tres_e3 = "introduce blends";    // first list under 3)
    String tres_e4 = "longer words with -ing";    // first list under 4)
    String tres_e5 = "-ar-";   // first list under 6)
    String tres_e6 = "wa-";    // first list under 7)

    String tres_h1 = "introduce long vowels with magic e";    // first list under 8)
    String tres_h2 = "-oat";   // first list under 10)
    String tres_h3 = "past tense -ed";              //"-ed sounding t/ d/ id";   // first list under 12)
    String tres_h4 = "ce ci cy";    // first list under 14)
    String tres_h5 = "kn- gn- silent k,g";     // first list under 16)
    String tres_h6 = "homophones -ail -ale";     // first list under 18)
    String tres_h7 = "-ture";     // first list under 20)
    String tres_h8 = "count syllables";     // first list under 22)



    public String getTopic(){
        switch(stage) {
            case 0:return tnameA;
            case 1:return path == PATH_EASY ? tname_e1 : tname_h1;
            case 2:return path == PATH_EASY ? tname_e2 : tname_h2;
            case 3:return path == PATH_EASY ? tname_e3 : tname_h3;
            case 4:return path == PATH_EASY ? tname_e4 : tname_h4;
            case 5:return path == PATH_EASY ? tname_e5 : tname_h5;
            case 6:return tname_h6;
            case 7:return tname_h7;
        }
        return null;
    }
    
    public String getResultTopic(){
        switch(stage-1) {
            case 1:return path == PATH_EASY ? tres_e1 : tres_h1;
            case 2:return path == PATH_EASY ? tres_e2 : tres_h2;
            case 3:return path == PATH_EASY ? tres_e3 : tres_h3;
            case 4:return path == PATH_EASY ? tres_e4 : tres_h4;
            case 5:return path == PATH_EASY ? (passed?tres_e6:tres_e5) : tres_h5;
            case 6:return tres_h6;
            case 7:return passed?tres_h8:tres_h7;
        }
        return null;
    }
    
    static void reset(){
        stage = -1;
        previousTopic = null;
        path = -1;
        passed = true;
        reachedEnd = false;
    }

    public static void escapeout(){
        saveprogress(null);
        sharkStartFrame.mainFrame.currPlayTopic = previousTopic;
        reset();
        jnode node = sharkStartFrame.mainFrame.topicList.findNode(sharkStartFrame.mainFrame.currPlayTopic.name);
        sharkStartFrame.mainFrame.topicList.setSelectionPath(new TreePath(node.getPath()));
        sharkStartFrame.mainFrame.gameicons = false;
        sharkStartFrame.mainFrame.setupGametree();
        
        
        Image tempim = Toolkit.getDefaultToolkit().getImage(sharkStartFrame.publicPathplus + "sprites" +
                                           sharkStartFrame.separator +
                                           "targethalf_il48.png");
        Dimension imageButDim = new Dimension((sharkStartFrame.screenSize.width / 3)/20,
                                                     (sharkStartFrame.screenSize.width / 3)/20);
        ImageIcon saveicon = new ImageIcon(tempim.getScaledInstance((int)imageButDim.getWidth(),
              (int) imageButDim.getHeight(), Image.SCALE_SMOOTH));
        sharkStartFrame.mainFrame.btTopicTest.setToolTipText(u.gettext("topictest", "bttooltip_incomplete"));
        sharkStartFrame.mainFrame.btTopicTest.setIcon(saveicon);
    }
    
    public void doResult(){
      String res = getResultTopic();
      saveprogress(res);
      jnode node = sharkStartFrame.mainFrame.topicList.findNode(res);
      reset();
      sharkStartFrame.mainFrame.topicList.setSelectionPath(new TreePath(node.getPath()));       
      sharkStartFrame.mainFrame.gameicons = true;
      sharkStartFrame.mainFrame.switchdisplay();
      sharkStartFrame.mainFrame.setupGametree(); 
      Image tempim = Toolkit.getDefaultToolkit().getImage(sharkStartFrame.publicPathplus + "sprites" +
                                           sharkStartFrame.separator +
                                           "target_il48.png");
      Dimension imageButDim = new Dimension((sharkStartFrame.screenSize.width / 3)/20,
                                                   (sharkStartFrame.screenSize.width / 3)/20);
      ImageIcon saveicon = new ImageIcon(tempim.getScaledInstance((int)imageButDim.getWidth(),
            (int) imageButDim.getHeight(), Image.SCALE_SMOOTH));
      sharkStartFrame.mainFrame.btTopicTest.setToolTipText(u.gettext("topictest", "bttooltip_complete"));
      sharkStartFrame.mainFrame.btTopicTest.setIcon(saveicon);
    }

    public static void saveprogress(String result){
        Calendar cal = new GregorianCalendar();
        String day = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
        if(day.length()==1)day = "0"+ day;
        String date = day + "/" +
            String.valueOf(cal.get(Calendar.MONTH)+1) + "/" +
            String.valueOf(cal.get(Calendar.YEAR));
        if(result!=null){
            student.setOption("topictest_completeddate", date);
            student.setOption("topictest_resulttopic", result);
            student.removeOption("topictest_stage");
            student.removeOption("topictest_difficulty");
            student.removeOption("topictest_starteddate");
        }
        else{
            student.removeOption("topictest_completeddate");
            student.removeOption("topictest_resulttopic");
            student.setOption("topictest_stage", String.valueOf(stage));
            student.setOption("topictest_difficulty", String.valueOf(path));
            if(sharkStartFrame.studentList[sharkStartFrame.currStudent].optionstring2("topictest_starteddate")==null)
                student.setOption("topictest_starteddate", date);
        }
    }

    public static void clearprogress(){
        reset();
            student.removeOption("topictest_stage");
            student.removeOption("topictest_difficulty");
            student.removeOption("topictest_starteddate");
            student.removeOption("topictest_completeddate");
            student.removeOption("topictest_resulttopic");
    }
}
