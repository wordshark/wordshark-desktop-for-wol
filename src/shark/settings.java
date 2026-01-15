/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package shark;

import java.lang.ref.SoftReference.*;
import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;
/**
 *
 * @author Paul Rubie
 */
public class settings extends JPanel{

   int b1 = 5;
   int b2 = 10;
   Font plainfont = sharkStartFrame.treefont.deriveFont(Font.PLAIN);
   Font smallerplainfont = plainfont.deriveFont((float)plainfont.getSize()-1);
   Font largerplainfont = plainfont.deriveFont((float)plainfont.getSize()+2);
   JButton btnoise;
     JButton btmisc;
     JButton btuserrecords;
     JButton btsignon;
     JButton btreward;
     JButton btcourse;
     JButton btfont;
     JButton btkeypad;
     JButton btpicture;
     JButton btgame;

     JLabel settingstitlelabel;
     String strsettings = u.gettext("admintitles", "settings");
     String strunisettings = u.gettext("admintitles", "unisettings");
     boolean universal = false;
     JPanel pnclicktoset;
     JPanel pnmisc;
     JPanel pnuserrecords;

     JPanel pnnoise;
     JPanel pnsignon;
     JPanel pnreward;
     JPanel pncourse;
     JPanel pnfont;
     JPanel pnkeypad;
     JPanel pnpicture;
     JPanel pngame;
     JCheckBox cbdisplaynoadmins;
         JCheckBox cbhello;
         JCheckBox cbtemp;
         JCheckBox cbdisplaynames;
         JCheckBox cbautosignon;
         JButton setupExceptions;
         JCheckBox cbautocreate;
         JLabel lbautoexclusioninfo;
         JLabel lbautocreateinfo;
         JLabel lbdisplaynamesadmins;


         u.my3wayCheckBox mcourses[];
         String defaulthhiddencourses;
         final static String ALLCOURSES = "0";
JComboBox  defcourse;
    int usertype = -1;
    final int usertype_YOU = 0;
    final int usertype_OTHERUSER = 1;
    final int usertype_GROUP = 2;
    student stu;
    GridBagConstraints grid;
    
    JButton resetbt;
    u.my3wayCheckBox mnokeypad;
    u.my3wayCheckBox mkeypads[];
   public String students[];
   student studentlist[];  // rb 14/11/06
   public student firststu;
   public boolean xrewardfreq,xkeypad,xteachnotes,xrewards,xbgcolor,xpeep,xcourses,xnogroan,xbeepvol,xwordfont,xtreefont;
   public boolean xwantsigns,xwantfingers,xwantrealpics,xwantsignvids;
   public boolean xexcludegames,xpicpref,xexcluderewardgames,xexcludeallrewardgames;
   JPanel pncoursedefault;
   String defaultspell;
   JPanel regionalspellpn;
   JPanel stumiscpn;
               u.my3wayCheckBox mpeep;
               u.my3wayCheckBox mpeepuni;
//            u.my3wayCheckBox mteachnotes;
      u.my3wayCheckBox bypasswork;
            u.my3wayCheckBox bgcolor;
            JDialog owner;
            JButton colbt;
            JLabel coleglb;
                        JLabel lbgamefont;
            JLabel lbtreefont;
            JButton gamefontdefault;
            JButton treefontdefault;
               Font originalgameFont;
               Font currgamefont;
               Font originaltreeFont;
               
               u.my3wayCheckBox menforce;
               u.my3wayCheckBox monsetrime;
               u.my3wayCheckBox mrewardexclude;

               gamestoplay gameTree;
               gamestoplay gameTreeMain;
               JList gameheadlist;
               String currexcludes[];
               String stucurrexcludes[];
             JButton btexclude;
            JButton btundoexclude;
 JList gameexcluded;
u.my3wayCheckBox malwayspatch;



u.my3wayCheckBox mrewards[];
u.my3wayCheckBox mrewardfreqs[];
String okrewards[];
u.my3wayCheckBox mnoreward;
JSlider opView;
u.my3wayCheckBox nogroan;
u.my3wayCheckBox nostuimports_icon;
u.my3wayCheckBox nostuimports_ownwords;
JPanel jpicnostupn;
JPanel jpicnostupnownwords;
picturepreferences picpref;
String rewardtitle = u.gettext("stulist_", "rewards");
public String nodename;
JButton importbt;
     String strnoise = u.gettext("adminsettings", "noise");
     String strmisc = u.gettext("adminsettings", "misc");
     String struserrecords = u.gettext("adminsettings", "userrecords");
     String strsignon = u.gettext("adminsettings", "signon");
     String strreward = u.gettext("adminsettings", "reward");
     String strcourse = u.gettext("adminsettings", "course");
     String strfont = u.gettext("adminsettings", "font");
     String strkeypad = u.gettext("adminsettings", "keypad");
     String strpicture = u.gettext("adminsettings", "picture");
     String strgame = u.gettext("adminsettings", "game");
     String titlestrings[] = new String[]{strnoise,strmisc, strsignon,strreward,strcourse,strfont,strkeypad,strpicture,strgame};

   public JPanel hlnoise = new JPanel(new GridBagLayout());
   public JPanel hlmisc = new JPanel(new GridBagLayout());
   public JPanel hluserrecords = new JPanel(new GridBagLayout());
   public JPanel hlsignon = new JPanel(new GridBagLayout());
   public JPanel hlreward = new JPanel(new GridBagLayout());
   public JPanel hlcourse = new JPanel(new GridBagLayout());
   public JPanel hlfont = new JPanel(new GridBagLayout());
   public JPanel hlkeypad = new JPanel(new GridBagLayout());
   public JPanel hlpicture = new JPanel(new GridBagLayout());
   public JPanel hlgame = new JPanel(new GridBagLayout());
   JButton treefontchange;
JLabel lbtreefontmess;
String strnoneselected = u.gettext("adminsettings", "nonesel");
String strmixed = u.gettext("adminsettings", "mixedsel");
JPanel onsetrimepn;
JPanel enforcepn;
JPanel peepunipn;
JPanel pnpatch;
JPanel pncoursebordered;
JPanel excludegamespan2;
JPanel excludegamespan;
//JPanel jblnpic;
JPanel jpicpn;
JPanel unipiccontent;

JLabel clicktoset;
String strclickttoset_stu = u.gettext("adminsettings", "clicktoset_stu");
String strclickttoset_group = u.gettext("adminsettings", "clicktoset_group");
String strclickttoset_uni = u.gettext("adminsettings", "clicktoset_uni");
String strclickttoset_own = u.gettext("adminsettings", "clicktoset_own");
        ImageIcon iimisc;
        ImageIcon iiuserrecords;
        ImageIcon iinoise;
        ImageIcon iisignon;
        ImageIcon iireward;
        ImageIcon iicourse;
        ImageIcon iifont;
        ImageIcon iikeypad;
        ImageIcon iipicture;
        ImageIcon iigame;

        ImageIcon iimiscoff;
        ImageIcon iiuserrecordsoff;
        ImageIcon iinoiseoff;
        ImageIcon iisignonoff;
        ImageIcon iirewardoff;
        ImageIcon iicourseoff;
        ImageIcon iifontoff;
        ImageIcon iikeypadoff;
        ImageIcon iipictureoff;
        ImageIcon iigameoff;
        boolean isInAdmin;

        String gameheading[];
        
        JPanel rewardmainpn;
        JLabel norewardslb;
        String strexcluededrewards = u.gettext("adminsettings", "rewardsexcluded");
        String xstrexcluededrewards = u.gettext("adminsettings", "xrewardsexcluded");
        boolean hasMinRewardWarn = false;
        boolean blockrewardexclude;
        JPanel pnrewardwarn;
        JPanel lbpn;
final static int DEFAULTBEEPVOL = 2; // was 4


     public settings(JDialog jd, boolean global, boolean isadmin){
         super();
         universal = global;
         owner = jd;
         isInAdmin = isadmin;
         init();
         set2(global);
         
     }

    public settings(JDialog jd, boolean isadmin){
        super();
        owner = jd;
        isInAdmin = isadmin;
        init();
    }
    
    public settings(){

    }    

    void init(){

     int sw = sharkStartFrame.mainFrame.getWidth();
     int buttondim = (sw*14/22)/24;
     int buttonimdim = buttondim- (buttondim/5);
     Image im = Toolkit.getDefaultToolkit().getImage(sharkStartFrame.publicPathplus + "sprites" +
                                       sharkStartFrame.separator +
                                       "misc_il48.png");
     iimisc = new ImageIcon(im.getScaledInstance(buttonimdim,
            buttonimdim, Image.SCALE_SMOOTH));
     im = Toolkit.getDefaultToolkit().getImage(sharkStartFrame.publicPathplus + "sprites" +
                                       sharkStartFrame.separator +
                                       "miscGREY_il48.png");
     iimiscoff = new ImageIcon(im.getScaledInstance(buttonimdim,
            buttonimdim, Image.SCALE_SMOOTH));

     btmisc = u.sharkButton();
     btmisc.setIcon(iimiscoff);
     
     
     
     im = Toolkit.getDefaultToolkit().getImage(sharkStartFrame.publicPathplus + "sprites" +
                                       sharkStartFrame.separator +
                                       "studentrec_il48.png");
     iiuserrecords = new ImageIcon(im.getScaledInstance(buttonimdim,
            buttonimdim, Image.SCALE_SMOOTH));
     im = Toolkit.getDefaultToolkit().getImage(sharkStartFrame.publicPathplus + "sprites" +
                                       sharkStartFrame.separator +
                                       "studentrecGREY_il48.png");
     iiuserrecordsoff = new ImageIcon(im.getScaledInstance(buttonimdim,
            buttonimdim, Image.SCALE_SMOOTH));

     btuserrecords = u.sharkButton();
     btuserrecords.setIcon(iiuserrecordsoff);
     
     
     
     im = Toolkit.getDefaultToolkit().getImage(sharkStartFrame.publicPathplus + "sprites" +
                                       sharkStartFrame.separator +
                                       "speakerON_il48.png");
     iinoise = new ImageIcon(im.getScaledInstance(buttonimdim,
            buttonimdim, Image.SCALE_SMOOTH));
     im = Toolkit.getDefaultToolkit().getImage(sharkStartFrame.publicPathplus + "sprites" +
                                       sharkStartFrame.separator +
                                       "speakerONGREY_il48.png");
     iinoiseoff = new ImageIcon(im.getScaledInstance(buttonimdim,
            buttonimdim, Image.SCALE_SMOOTH));
     btnoise = u.sharkButton();

     btnoise.setIcon(iinoiseoff);
     im = Toolkit.getDefaultToolkit().getImage(sharkStartFrame.publicPathplus + "sprites" +
                                       sharkStartFrame.separator +
                                       "sign_il48.png");
     iisignon = new ImageIcon(im.getScaledInstance(buttonimdim,
            buttonimdim, Image.SCALE_SMOOTH));
     im = Toolkit.getDefaultToolkit().getImage(sharkStartFrame.publicPathplus + "sprites" +
                                       sharkStartFrame.separator +
                                       "signGREY_il48.png");
     iisignonoff = new ImageIcon(im.getScaledInstance(buttonimdim,
            buttonimdim, Image.SCALE_SMOOTH));
     btsignon = u.sharkButton();
     btsignon.setIcon(iisignonoff);
     im = Toolkit.getDefaultToolkit().getImage(sharkStartFrame.publicPathplus + "sprites" +
                                       sharkStartFrame.separator +
                                       "reward_il48.png");
     iireward = new ImageIcon(im.getScaledInstance(buttonimdim,
            buttonimdim, Image.SCALE_SMOOTH));
     im = Toolkit.getDefaultToolkit().getImage(sharkStartFrame.publicPathplus + "sprites" +
                                       sharkStartFrame.separator +
                                       "rewardGREY_il48.png");
     iirewardoff = new ImageIcon(im.getScaledInstance(buttonimdim,
            buttonimdim, Image.SCALE_SMOOTH));
     btreward = u.sharkButton();
     btreward.setIcon(iirewardoff);
     im = Toolkit.getDefaultToolkit().getImage(sharkStartFrame.publicPathplus + "sprites" +
                                       sharkStartFrame.separator +
                                       "course_il48.png");
     iicourse = new ImageIcon(im.getScaledInstance(buttonimdim,
            buttonimdim, Image.SCALE_SMOOTH));
     im = Toolkit.getDefaultToolkit().getImage(sharkStartFrame.publicPathplus + "sprites" +
                                       sharkStartFrame.separator +
                                       "courseGREY_il48.png");
     iicourseoff = new ImageIcon(im.getScaledInstance(buttonimdim,
            buttonimdim, Image.SCALE_SMOOTH));
     btcourse = u.sharkButton();
     btcourse.setIcon(iicourseoff);
     im = Toolkit.getDefaultToolkit().getImage(sharkStartFrame.publicPathplus + "sprites" +
                                       sharkStartFrame.separator +
                                       "font_il48.png");
     iifont = new ImageIcon(im.getScaledInstance(buttonimdim,
            buttonimdim, Image.SCALE_SMOOTH));
     im = Toolkit.getDefaultToolkit().getImage(sharkStartFrame.publicPathplus + "sprites" +
                                       sharkStartFrame.separator +
                                       "fontGREY_il48.png");
     iifontoff = new ImageIcon(im.getScaledInstance(buttonimdim,
            buttonimdim, Image.SCALE_SMOOTH));
     btfont = u.sharkButton();
     btfont.setIcon(iifontoff);
     im = Toolkit.getDefaultToolkit().getImage(sharkStartFrame.publicPathplus + "sprites" +
                                       sharkStartFrame.separator +
                                       "keypad_il48.png");
     iikeypad = new ImageIcon(im.getScaledInstance(buttonimdim,
            buttonimdim, Image.SCALE_SMOOTH));
     im = Toolkit.getDefaultToolkit().getImage(sharkStartFrame.publicPathplus + "sprites" +
                                       sharkStartFrame.separator +
                                       "keypadGREY_il48.png");
     iikeypadoff = new ImageIcon(im.getScaledInstance(buttonimdim,
            buttonimdim, Image.SCALE_SMOOTH));
     btkeypad = u.sharkButton();
     btkeypad.setIcon(iikeypadoff);
     im = Toolkit.getDefaultToolkit().getImage(sharkStartFrame.publicPathplus + "sprites" +
                                       sharkStartFrame.separator +
                                       "picopts_il48.png");
     iipicture = new ImageIcon(im.getScaledInstance(buttonimdim,
            buttonimdim, Image.SCALE_SMOOTH));
     im = Toolkit.getDefaultToolkit().getImage(sharkStartFrame.publicPathplus + "sprites" +
                                       sharkStartFrame.separator +
                                       "picoptsGREY_il48.png");
     iipictureoff = new ImageIcon(im.getScaledInstance(buttonimdim,
            buttonimdim, Image.SCALE_SMOOTH));
     btpicture = u.sharkButton();
     btpicture.setIcon(iipictureoff);
     im = Toolkit.getDefaultToolkit().getImage(sharkStartFrame.publicPathplus + "sprites" +
                                       sharkStartFrame.separator +
                                       "gamesOFF_il48.png");
     iigame = new ImageIcon(im.getScaledInstance(buttonimdim,
            buttonimdim, Image.SCALE_SMOOTH));
     im = Toolkit.getDefaultToolkit().getImage(sharkStartFrame.publicPathplus + "sprites" +
                                       sharkStartFrame.separator +
                                       "gamesOFFGREY_il48.png");
     iigameoff = new ImageIcon(im.getScaledInstance(buttonimdim,
            buttonimdim, Image.SCALE_SMOOTH));
     btgame = u.sharkButton();
     btgame.setIcon(iigameoff);



     JLabel lbtempinfo = u.infoLabel(owner, u.gettext("cbtemporarystu", "tooltip", shark.programName), !u.screenResWidthMoreThan(1200));
     JLabel lbdisplaynamesinfo = u.infoLabel(owner, u.gettext("cbnamechoice", "tooltip", shark.programName), !u.screenResWidthMoreThan(1200));
     lbdisplaynamesadmins = u.infoLabel(owner, u.gettext("cbdisplaysignonadminsinfo", "tooltip"), true);
     JLabel lbautosignoninfo = u.infoLabel(owner, u.gettext("cbautosignon", "tooltip", shark.programName), !u.screenResWidthMoreThan(1200));
     lbautocreateinfo = u.infoLabel(owner, u.gettext("cbautocreate", "tooltip", shark.programName), !u.screenResWidthMoreThan(1200));
     lbautoexclusioninfo = u.infoLabel(owner, u.gettext("btautoexceptions", "info"), !u.screenResWidthMoreThan(1200));



     btnoise.setToolTipText(strnoise);
     btmisc.setToolTipText(strmisc);
     btuserrecords.setToolTipText(struserrecords);
     btsignon.setToolTipText(strsignon);
     btreward.setToolTipText(strreward);
     btcourse.setToolTipText(strcourse);
     btfont.setToolTipText(strfont);
     btkeypad.setToolTipText(strkeypad);
     btpicture.setToolTipText(strpicture);
     btgame.setToolTipText(strgame);

     
     
     Dimension d = new Dimension(buttondim, buttondim);
     btnoise.setPreferredSize(d);
     btnoise.setMinimumSize(d);
     btmisc.setPreferredSize(d);
     btmisc.setMinimumSize(d);
     btuserrecords.setPreferredSize(d);
     btuserrecords.setMinimumSize(d);
     btsignon.setPreferredSize(d);
     btsignon.setMinimumSize(d);
     btreward.setPreferredSize(d);
     btreward.setMinimumSize(d);
     btcourse.setPreferredSize(d);
     btcourse.setMinimumSize(d);
     btfont.setPreferredSize(d);
     btfont.setMinimumSize(d);
     btkeypad.setPreferredSize(d);
     btkeypad.setMinimumSize(d);
     btpicture.setPreferredSize(d);
     btpicture.setMinimumSize(d);
     btgame.setPreferredSize(d);
     btgame.setMinimumSize(d);



int hlborderh =  10;
   int hlborderw = u.screenResWidthMoreThan(1000)?hlborderh:3;
   d = new Dimension(buttondim+hlborderw, buttondim+hlborderh);
     hlnoise.setPreferredSize(d);
     hlnoise.setMinimumSize(d);
     hlmisc.setPreferredSize(d);
     hlmisc.setMinimumSize(d);
     hluserrecords.setPreferredSize(d);
     hluserrecords.setMinimumSize(d);     
     hlsignon.setPreferredSize(d);
     hlsignon.setMinimumSize(d);
     hlreward.setPreferredSize(d);
     hlreward.setMinimumSize(d);
     hlcourse.setPreferredSize(d);
     hlcourse.setMinimumSize(d);
     hlfont.setPreferredSize(d);
     hlfont.setMinimumSize(d);
     hlkeypad.setPreferredSize(d);
     hlkeypad.setMinimumSize(d);
     hlpicture.setPreferredSize(d);
     hlpicture.setMinimumSize(d);
     hlgame.setPreferredSize(d);
     hlgame.setMinimumSize(d);



     btnoise.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               setIcons(btnoise);
            setPanel(pnnoise);
            settingstitlelabel.setText(strnoise);
          }
     });
     btmisc.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               setIcons(btmisc);
            setPanel(pnmisc);
            settingstitlelabel.setText(strmisc);
          }
     });
     btuserrecords.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               setIcons(btuserrecords);
            setPanel(pnuserrecords);
            settingstitlelabel.setText(struserrecords);
          }
     });
     btsignon.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               setIcons(btsignon);
            setPanel(pnsignon);
            settingstitlelabel.setText(strsignon);
          }
     });
     btreward.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               setIcons(btreward);
                setPanel(pnreward);
                settingstitlelabel.setText(strreward);
          }
     });
     btcourse.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               setIcons(btcourse);
                setPanel(pncourse);
                settingstitlelabel.setText(strcourse);
          }
     });
     btfont.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               setIcons(btfont);
            setPanel(pnfont);
            settingstitlelabel.setText(strfont);
          }
     });
     btkeypad.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               setIcons(btkeypad);
            setPanel(pnkeypad);
            settingstitlelabel.setText(strkeypad);
          }
     });
     btpicture.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               setIcons(btpicture);
            setPanel(pnpicture);
            settingstitlelabel.setText(strpicture);
          }
     });
     btgame.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               setIcons(btgame);
            setPanel(pngame);
            settingstitlelabel.setText(strgame);
          }
     });

     clicktoset = new JLabel();
     clicktoset.setFont(smallerplainfont);
     pnclicktoset = new JPanel(new GridBagLayout());
     pnnoise = new JPanel(new GridBagLayout());
     pnmisc = new JPanel(new GridBagLayout());
     pnuserrecords = new JPanel(new GridBagLayout());
     pnsignon = new JPanel(new GridBagLayout());
     pnreward = new JPanel(new GridBagLayout());
     pncourse = new JPanel(new GridBagLayout());
     pnfont = new JPanel(new GridBagLayout());
     pnkeypad = new JPanel(new GridBagLayout());
     pnpicture = new JPanel(new GridBagLayout());
     pngame = new JPanel(new GridBagLayout());


     JPanel pnmisccontent = new JPanel(new GridBagLayout());
     JPanel pnnoisecontent = new JPanel(new GridBagLayout());
     JPanel pnsignoncontent = new JPanel(new GridBagLayout());
     JPanel pnrewardcontent = new JPanel(new GridBagLayout());
     JPanel pncoursecontent = new JPanel(new GridBagLayout());
     JPanel pnfontcontent = new JPanel(new GridBagLayout());
     JPanel pnkeypadcontent = new JPanel(new GridBagLayout());
     JPanel pnpicturecontent = new JPanel(new GridBagLayout());
     JPanel pngamecontent = new JPanel(new GridBagLayout());


         grid = new GridBagConstraints();
         this.setLayout(new GridBagLayout());
         grid.fill = GridBagConstraints.NONE;
         grid.anchor = GridBagConstraints.CENTER;
         grid.weightx = 1;
         grid.weighty = 0;
         grid.insets = new Insets(0,0,0,0);
         grid.gridx = 0;
         grid.gridy = -1;

         pnclicktoset.add(clicktoset, grid);
         grid.anchor = GridBagConstraints.WEST;
         grid.anchor = GridBagConstraints.CENTER;
         grid.insets = new Insets(0,0,0,0);
         grid.weighty = 1;
         grid.fill = GridBagConstraints.BOTH;
         pnnoise.add(pnnoisecontent, grid);
         pnmisc.add(pnmisccontent, grid);
         pnuserrecords.add(new pnUserRecords(), grid);
         pnsignon.add(pnsignoncontent, grid);
         pnreward.add(pnrewardcontent, grid);

        JScrollPane sjpcourse = new JScrollPane(pncoursecontent);
         sjpcourse.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
         sjpcourse.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
         sjpcourse.setBorder(BorderFactory.createEmptyBorder());
         pncourse.add(sjpcourse, grid);
         
   //      pncourse.add(pncoursecontent, grid);
         pnfont.add(pnfontcontent, grid);
         pnkeypad.add(pnkeypadcontent, grid);
         pnpicture.add(pnpicturecontent, grid);
         pngame.add(pngamecontent, grid);


         grid.fill = GridBagConstraints.BOTH;
         JPanel settingstitlepan = new JPanel(new GridBagLayout());
         JPanel settingscontentpan = new JPanel(new GridBagLayout());
         settingstitlelabel = new JLabel();
         settingstitlelabel.setForeground(Color.darkGray);

         Font setf = settingstitlelabel.getFont();
         settingstitlelabel.setFont(setf.deriveFont((float)setf.getSize()+3));
         
         
//      int longest = -1;
//    for(int i = 0; i < titlestrings.length; i++){
//        int k = sharkStartFrame.treefontm.stringWidth(titlestrings[i]);
//        if(k>longest)longest = k;
//    }
//    JPanel fillerpn = new JPanel(new GridBagLayout());
//    fillerpn.setPreferredSize(new Dimension(longest, 1));
//    fillerpn.setMinimumSize(new Dimension(longest, 1));
          grid.gridx = 0;
         grid.gridy = -1;
                   grid.anchor = GridBagConstraints.CENTER;
         grid.fill = GridBagConstraints.NONE;
         lbpn = new JPanel(new GridBagLayout());
lbpn.setOpaque(false);
//fillerpn.setOpaque(false);
grid.insets = new Insets(7,5,5,5);
         lbpn.add(settingstitlelabel, grid);
//         lbpn.add(fillerpn, grid);



         grid.gridx = -1;
         grid.gridy = 0;
         grid.anchor = GridBagConstraints.WEST;
         grid.fill = GridBagConstraints.NONE;
         
         grid.weightx = 0;
         grid.insets = new Insets(5,10,5,10);
 //        settingstitlepan.add(lbpn, grid);

        grid.insets = new Insets(5,10,5,10);
      

     hlsignon.add(btsignon, grid);
     hlnoise.add(btnoise, grid);
    hlfont.add(btfont, grid);
      hlpicture.add(btpicture, grid);
     hlkeypad.add(btkeypad, grid);
     
        hlreward.add(btreward, grid);
     hlgame.add(btgame, grid);
     hlcourse.add(btcourse, grid);
     hlmisc.add(btmisc, grid);
     hluserrecords.add(btuserrecords, grid);
    grid.insets = new Insets(0,0,0,5);

/*
         settingstitlepan.add(hlsignon, grid);
          settingstitlepan.add(hlnoise, grid);
         settingstitlepan.add(hlfont, grid);
            settingstitlepan.add(hlpicture, grid);
             settingstitlepan.add(hlkeypad, grid);
             settingstitlepan.add(hlreward, grid);
             settingstitlepan.add(hlgame, grid);
         settingstitlepan.add(hlcourse, grid);
         settingstitlepan.add(hlmisc, grid);
*/
         
grid.insets = new Insets(0,0,0,0);
         grid.fill = GridBagConstraints.BOTH;
         grid.weightx = 1;
         JPanel pnBlank = new JPanel(new GridBagLayout());
         pnBlank.setOpaque(false);
         settingstitlepan.add(pnBlank, grid);



         cbhello = u.CheckBox("cbhello");
         cbtemp = u.CheckBox("cbtemporarystu");
         cbdisplaynames = u.CheckBox("cbnamechoice");
         cbautosignon = u.CheckBox("cbautosignon");
         cbautocreate = u.CheckBox("cbautocreate");
         cbautosignon.setToolTipText(null);
         cbautocreate.setToolTipText(null);
         cbdisplaynames.setToolTipText(null);
         cbtemp.setToolTipText(null);
         cbdisplaynoadmins = u.CheckBox("cbdisplaysignonadmins");


         
         setupExceptions = u.sharkButton("btautoexceptions");
         setupExceptions.setFont(smallerplainfont);
    //     setupExceptions.setText("cbautoexceptions", "label");

         cbdisplaynoadmins.setFont(smallerplainfont);
         cbdisplaynoadmins.setSelected(db.query(sharkStartFrame.optionsdb, "signondisplaynoadmins", db.TEXT) < 0);
         cbdisplaynoadmins.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            JCheckBox cb = (JCheckBox)e.getSource();
            if (!cb.isSelected())
              db.update(sharkStartFrame.optionsdb, "signondisplaynoadmins", new String[] {""}
                        , db.TEXT);
            else
              db.delete(sharkStartFrame.optionsdb, "signondisplaynoadmins", db.TEXT);
            }
        }); 
         
         cbhello.setFont(smallerplainfont);
         cbtemp.setFont(smallerplainfont);
         cbdisplaynames.setFont(smallerplainfont);
         cbautosignon.setFont(smallerplainfont);
         cbautocreate.setFont(smallerplainfont);

         cbhello.setSelected(db.query(sharkStartFrame.optionsdb, "nohello", db.TEXT) < 0);
         cbhello.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            if (!cbhello.isSelected())
              db.update(sharkStartFrame.optionsdb, "nohello", new String[] {""}
                        , db.TEXT);
            else
              db.delete(sharkStartFrame.optionsdb, "nohello", db.TEXT);
            }
        });

        cbtemp.setSelected(db.query(sharkStartFrame.optionsdb, "mnotemp", db.TEXT) < 0);
         cbtemp.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            if (cbtemp.isSelected())
                db.delete(sharkStartFrame.optionsdb, "mnotemp", db.TEXT);
            else
                db.update(sharkStartFrame.optionsdb, "mnotemp", new String[] {""}, db.TEXT);
           }
        });


        cbdisplaynames.setSelected(db.query(sharkStartFrame.optionsdb, "nosideprompt", db.TEXT) < 0);
         cbdisplaynames.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            if (cbdisplaynames.isSelected()){
                db.delete(sharkStartFrame.optionsdb, "nosideprompt", db.TEXT);
            }
            else{
                db.update(sharkStartFrame.optionsdb, "nosideprompt", new String[] {""}, db.TEXT);
            }
            cbdisplaynoadmins.setEnabled(cbdisplaynames.isSelected());
            lbdisplaynamesadmins.setEnabled(cbdisplaynames.isSelected());
           }
        });


        cbautosignon.setSelected(db.query(sharkStartFrame.optionsdb,"autosignon_",db.TEXT )>=0);
        
        cbautosignon.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            cbautocreate.setEnabled(cbautosignon.isSelected());
            lbautocreateinfo.setEnabled(cbautosignon.isSelected());
    //        lbautocreateinfo.setVisible(cbautosignon.isSelected());
            if(cbautosignon.isSelected())
                db.update(sharkStartFrame.optionsdb,"autosignon_",new String[]{""},db.TEXT);
            else {
                db.delete(sharkStartFrame.optionsdb,"autosignon_",db.TEXT);
                db.delete(sharkStartFrame.optionsdb,"mautosigncreatestu_",db.TEXT);
                cbautocreate.setSelected(false);
            }
            setupExceptions.setEnabled(cbautosignon.isSelected());
            lbautoexclusioninfo.setEnabled(cbautosignon.isSelected());
           }
        });

       cbautocreate.setSelected(db.query(sharkStartFrame.optionsdb,"mautosigncreatestu_",db.TEXT) >= 0);
       cbautocreate.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
              if(cbautocreate.isSelected())
                  db.update(sharkStartFrame.optionsdb,"mautosigncreatestu_",new String[]{""},db.TEXT);
              else db.delete(sharkStartFrame.optionsdb,"mautosigncreatestu_",db.TEXT);
           }
        });
       
         setupExceptions.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String exclusions[] = (String[])db.find(sharkStartFrame.optionsdb, "autosignonexclusions",db.TEXT);
                stringedit_base se = new stringedit_base(u.gettext("adminsettings", "autoexclusionsheading"), exclusions!=null?exclusions:new String[]{}, owner, stringedit_base.MODE_EXCLUDEAUTOSIGNON, false) {
                    public boolean update(String s[]) {
                        if(s==null || s.length==0)
                            db.delete(sharkStartFrame.optionsdb, "autosignonexclusions", db.TEXT);
                        else
                            db.update(sharkStartFrame.optionsdb, "autosignonexclusions", s, db.TEXT);
                        return true;
                    }
                };
                se.ignorexclick = true;
                se.staythere = true;              
          }
        });       




        // sign-on settings content
         grid.fill = GridBagConstraints.NONE;
         grid.weighty = 0;
        JPanel pntemp = new JPanel(new GridBagLayout());
        JPanel pndisplaynames = new JPanel(new GridBagLayout());
        JPanel pnautosignon = new JPanel(new GridBagLayout());
        JPanel pnautocreate = new JPanel(new GridBagLayout());
        JPanel pnautoexceptions = new JPanel(new GridBagLayout());
        JPanel pndisplayadmins = new JPanel(new GridBagLayout());
        grid.gridx=-1;
        grid.gridy= 0;
        grid.insets = new Insets(0,0,0,0);
         pntemp.add(cbtemp, grid);
         grid.insets = new Insets(0,10,0,0);
         pntemp.add(lbtempinfo, grid);
         grid.insets = new Insets(0,0,0,0);
         pndisplaynames.add(cbdisplaynames, grid);
         grid.insets = new Insets(0,10,0,0);
         pndisplaynames.add(lbdisplaynamesinfo, grid);
         grid.insets = new Insets(0,0,0,0);
         pndisplayadmins.add(cbdisplaynoadmins, grid);
         grid.insets = new Insets(0,10,0,0);
         pndisplayadmins.add(lbdisplaynamesadmins, grid);
         grid.insets = new Insets(0,0,0,0);
         pnautosignon.add(cbautosignon, grid);
         grid.insets = new Insets(0,10,0,0);
         pnautosignon.add(lbautosignoninfo, grid);
         grid.insets = new Insets(0,0,0,0);
         pnautocreate.add(cbautocreate, grid);
         grid.insets = new Insets(0,10,0,0);
         pnautocreate.add(lbautocreateinfo, grid);
         grid.insets = new Insets(0,0,0,0);
        pnautoexceptions.add(setupExceptions, grid);
        grid.insets = new Insets(0,10,0,0);
        pnautoexceptions.add(lbautoexclusioninfo, grid);
        
        grid.gridx=0;
        grid.gridy= -1;
        grid.fill = GridBagConstraints.NONE;
        grid.anchor = GridBagConstraints.WEST;
        grid.insets = new Insets(0,0,10,0);
        JPanel signontop = new JPanel(new GridBagLayout());
        JPanel signonbottom = new JPanel(new GridBagLayout());
        JPanel signonwhole = new JPanel(new GridBagLayout());
        JPanel signonsidedisplay = new JPanel(new GridBagLayout());
        signontop.setBorder(BorderFactory.createEtchedBorder());
        signonsidedisplay.setBorder(BorderFactory.createEtchedBorder());
        signonbottom.setBorder(BorderFactory.createEtchedBorder());

        int brd = 10;
        int brdy = 10;
        grid.insets = new Insets(brd,brd,brdy,brd);
        signontop.add(cbhello,grid);
        if(shark.wantTemporaryStudents){ 
            grid.insets = new Insets(0,brd,brdy,brd);
            signontop.add(pntemp,grid);
        }
        grid.insets = new Insets(0,brd,brd,brd);
//        signontop.add(pndisplaynames,grid);
        signonsidedisplay.add(pndisplaynames,grid);
        grid.insets = new Insets(0,brd+20,brd,brd);
        signonsidedisplay.add(pndisplayadmins,grid);

        grid.insets = new Insets(brd,brd,brdy,brd);
        signonbottom.add(pnautosignon,grid);
        grid.insets = new Insets(0,brd+20,brdy,brd);
        signonbottom.add(pnautoexceptions,grid);
        grid.insets = new Insets(0,brd+20,brd,brd);
        signonbottom.add(pnautocreate,grid);


        grid.insets = new Insets(0,0,0,0);
        grid.anchor = GridBagConstraints.CENTER;
        grid.fill = GridBagConstraints.HORIZONTAL;
        grid.weightx = 1;
        signonwhole.add(signontop,grid);
        grid.insets = new Insets(10,0,0,0);
        signonwhole.add(signonsidedisplay,grid);
        grid.insets = new Insets(10,0,0,0);
        signonwhole.add(signonbottom,grid);
        grid.insets = new Insets(0,0,0,0);
        grid.fill = GridBagConstraints.NONE;
        grid.weightx = 0;
        pnsignoncontent.add(signonwhole, grid);




         // course settings content



         JPanel pncourseouter = new JPanel(new GridBagLayout());
         pncoursebordered = new JPanel(new GridBagLayout());
         JPanel pncourseinner= new JPanel(new GridBagLayout());
         pncoursedefault = new JPanel(new GridBagLayout());
        defcourse = new JComboBox();
        defcourse.setFont(smallerplainfont);
        grid.gridx=-1;
        grid.gridy= 0;
        grid.weightx = 1;
        grid.fill = GridBagConstraints.NONE;
        grid.anchor = GridBagConstraints.WEST;
        JLabel defcourselb = new JLabel(u.gettext("adminsettings", "defaultcourselabel"));
        defcourselb.setFont(smallerplainfont);
         pncoursedefault.add(defcourselb, grid);
         grid.anchor = GridBagConstraints.EAST;
         pncoursedefault.add(defcourse, grid);
          grid.weightx = 1;
        grid.gridx=0;
        grid.gridy= -1;



         grid.anchor = GridBagConstraints.CENTER;
         pncoursecontent.add(pncourseouter, grid);
         grid.anchor = GridBagConstraints.WEST;
         grid.fill = GridBagConstraints.HORIZONTAL;
         grid.insets = new Insets(0,0,0,0);
         pncourseouter.add(pncoursebordered, grid);
         grid.fill = GridBagConstraints.NONE;
         grid.insets = new Insets(30,0,0,0);
         grid.fill = GridBagConstraints.HORIZONTAL;
         grid.insets = new Insets(30,0,20,0);
         pncourseouter.add(pncoursedefault, grid);
         grid.insets = new Insets(30,0,0,0);
          grid.fill = GridBagConstraints.NONE;
    resetbt = new JButton(u.gettext("reset", "label"));
    String cl[] = null;
    cl = db.listsort(u.absoluteToRelative(sharkStartFrame.publicTopicLib[0]), db.TOPICTREE);
    for(int i=0;i<cl.length;++i) {
     if(cl[i].startsWith("  ") ||   ( !universal && !u.displayCourse(cl[i]))) {
        cl = u.removeString(cl,i);
        --i;
     }
    }
    int ycounter = 0;
    grid.anchor = GridBagConstraints.WEST;
    grid.gridx=0;
    grid.gridy= ycounter;
    grid.weighty = 1;
    grid.fill = GridBagConstraints.BOTH;
    grid.weightx = 1;
    String okcourses[] = new String[]{};
    mcourses = new u.my3wayCheckBox[cl.length];
     ycounter++;
        JLabel lb = new JLabel(u.gettext("adminsettings", "setcourselb"));
        lb.setFont(smallerplainfont);
  //      grid.insets = new Insets(10,0,20,0);
        grid.insets = new Insets(0,0,0,0);
        grid.gridx = -1;
        grid.gridy = 0;
        grid.fill = GridBagConstraints.NONE;
                grid.weightx = 0;
                grid.weighty = 0;
        JPanel titlepn = new JPanel(new GridBagLayout());
        titlepn.add(lb, grid);
        grid.weightx = 1;
        grid.insets = new Insets(0,10,0,0);
        titlepn.add(u.infoLabel(owner, u.gettext("adminsettings", "courseinfo"), false), grid);
        grid.gridx = 0;
        grid.gridy = 1;
        grid.fill = GridBagConstraints.BOTH;
        grid.weightx = 1;
        grid.weighty = 1;
        grid.insets = new Insets(0,0,10,0);
        pncourseinner.add(titlepn, grid);
        grid.insets = new Insets(0,0,0,0);
        defcourse.removeAllItems();
        for (short i = 0; i < cl.length; ++i) {
            ycounter++;
            grid.gridy= ycounter;
            
             pncourseinner.add(mcourses[i] = u.get3wayCheckBox2(cl[i]), grid);
             mcourses[i].setFont(smallerplainfont);
              if(mcourses[i].state==u.ALL){
                  defcourse.addItem(cl[i]);
                  okcourses = u.addString(okcourses, cl[i]);
              }
          mcourses[i].addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                u.my3wayCheckBox curr = ( (u.my3wayCheckBox) e.getSource());

                if(universal){
                   if(curr.state==u.ALL)curr.setState(u.NONE);
                   else if(curr.state==u.NONE)curr.setState(u.ALL);
                }

                boolean active = false;
                for(int i = 0; i < mcourses.length; i++){
                    if(mcourses[i].state==u.SOME||(mcourses[i].state==u.ALL && mcourses[i].isEnabled()))active = true;
                }
                if(!active){
                    curr.setState(u.ALL);
                    return;
                }
                String save[] = null;
                String okc[] = new String[]{};
                String notokc[] = new String[]{};

                for(int i = 0; i < mcourses.length; i++){
                    boolean sel = mcourses[i].state==u.ALL;
                    String str = mcourses[i].getText();

                    if(sel){
                        okc = u.addString(okc, str);
                    }
                    else {
                        if(mcourses[i].isEnabled())
                            notokc = u.addString(notokc, str);
                        if(save==null)save = new String[]{};
                        save = u.addString(save, str);
                    }
                }
                if(universal){
                    String currsave = null;
                    if(save!=null)currsave = u.combineString(save);
                    if(currsave!=null){
                        if(currsave.equals(defaulthhiddencourses)){
                            db.delete(sharkStartFrame.optionsdb, "universalhiddencourses", db.TEXT);
                        }
                        else{
                            db.update(sharkStartFrame.optionsdb, "universalhiddencourses", currsave, db.TEXT);
                            sharkStartFrame.courses = u.splitString(currsave);
                        }
                    }
                    else{
                        db.update(sharkStartFrame.optionsdb, "universalhiddencourses", ALLCOURSES, db.TEXT);
                        sharkStartFrame.courses = new String[0];
                    }
                    String prevsel = (String)defcourse.getSelectedItem();
                    defcourse.removeAllItems();
                    for(int i = 0; i < okc.length; i++){
                        defcourse.addItem(okc[i]);
                    }
                    if(u.findString(okc, prevsel)>=0)
                        defcourse.setSelectedItem(prevsel);
                    else defcourse.setSelectedIndex(0);
                }
                else{
                   if(curr.state == u.SOME) {
                     for (short i = 0; i < mcourses.length; ++i) 
                         if(!mcourses[i].isEnabled())mcourses[i].setState(u.NONE);
                        else mcourses[i].setState(u.ALL);
                   }
                   else if(curr.state != u.ALL) curr.setState(u.ALL);
                   else curr.setState(u.NONE);
                   String courses[] = new String[0];
                   for (short i = 0; i < mcourses.length; ++i) {
                     if (mcourses[i].state != u.ALL)
                       courses = u.addString(courses, mcourses[i].getText());
                   }
                   if(courses.length == mcourses.length) {      // start rb 18/1/08
                      curr.setState(u.ALL);
                   }
                   else student.queueupdate(students, new String[]{"courses", u.combineString(courses)});// end rb 18/1/08                    
                }
                if(universal || usertype==usertype_YOU){
                    sharkStartFrame.mainFrame.setCourseMenu();
                    sharkStartFrame.mainFrame.setupgames();
                }
            }
          });
        if(i==cl.length-1){
            grid.gridx=1;
            grid.insets = new Insets(0,40,0,0);
            pncourseinner.add(resetbt, grid);
        }
        }
        grid.insets = new Insets(10,10,10,10);
        pncoursebordered.add(pncourseinner, grid);




        //keypad settings content
        if(!universal){
             String kp[] = u.addString(db.list(sharkStartFrame.publicKeypadLib,
                                               db.SAVEKEYPAD),
                                       db.list(sharkStartFrame.sharedPathplus + "keypad", db.SAVEKEYPAD));
            String skp[] =  u.splitString(u.gettext("keypad_","hide"));
            if (kp != null && skp !=null) {
                for (int i = 0; i < skp.length; i++) {
                    if((u.findString(kp, skp[i]))>=0)
                        kp = u.removeString2(kp,skp[i]);
                }
            }
             mkeypads = new u.my3wayCheckBox[kp.length];
             grid.gridy= ycounter = 0;
             JPanel keypadpn = new JPanel(new GridBagLayout());
             grid.weighty = 0;
             grid.insets = new Insets(10,10,10,10);
             grid.fill = GridBagConstraints.NONE;
             if (kp != null) { //If 1
               u.sort(u.stripdup(kp));
               for (int i = 0; i < kp.length; ++i) {
                 u.my3wayCheckBox mm = mkeypads[i] = u.get3wayCheckBox2(kp[i]);
                 mm.setFont(plainfont);
                keypadpn.add(mm, grid);
                grid.gridy= ++ycounter;
                 mm.addActionListener(new ActionListener() {
                   public void actionPerformed(ActionEvent e) {
                     u.my3wayCheckBox currkeypad = ( (u.my3wayCheckBox) e.getSource());
                     if (currkeypad.state != u.ALL) { //If 1.1.1
                       student.queueupdate(students,new String[]{"keypad",currkeypad.getText()});
                       setkeydis(currkeypad.getText());
                     }
                 }
                 });
               }
               mnokeypad = u.get3wayCheckBox("mnokeypad");
               mnokeypad.setFont(plainfont);
               grid.gridy= ++ycounter;
               keypadpn.add(mnokeypad, grid);


               grid.anchor = GridBagConstraints.CENTER;
               pnkeypadcontent.add(keypadpn, grid);
               mnokeypad.addActionListener(new ActionListener() {
                 public void actionPerformed(ActionEvent e) {
                   if(mnokeypad.state != u.ALL) {
                      mnokeypad.setState(u.ALL);
                      for (int i = 0; i < mkeypads.length; ++i) { //for 1.1
                        if (mkeypads[i].state > u.NONE) {
                          mkeypads[i].setState(u.NONE);
                        }
                      }
                      student.queueupdate(students, new String[] {"keypad"});
                   }
                 }
              });
             }
        }
     defcourse.addItemListener(new ItemListener() {
      public void itemStateChanged(ItemEvent e) {
            String s = (String)((JComboBox)e.getSource()).getSelectedItem();
            if(!universal || s==null)return;
            if(!defcourse.isShowing())return;
            String init = (String)db.find(sharkStartFrame.optionsdb,"start_topic",db.TEXT);
            if(init==null || !init.equals(s)){
                db.update(sharkStartFrame.optionsdb, "start_topic", s, db.TEXT);
            }
      }
    });
     resetbt.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               if(universal){
                    for(int i = 0; i < mcourses.length; i++){
                        mcourses[i].setState(  (u.findString(u.defaulthiddencourses, mcourses[i].getText())<0)?u.ALL:u.NONE );
        //                mcourses[i].setState(u.ALL);
                    }
                    db.delete(sharkStartFrame.optionsdb, "universalhiddencourses", db.TEXT);
                    sharkStartFrame.mainFrame.setupgames();
               }
               else{
                    for(int i = 0; i < mcourses.length; i++){
                        if(!mcourses[i].isEnabled())continue;
                            mcourses[i].setState(u.ALL);
                    }
                    if(students!=null && students.length>0)
                        student.queueupdate(students, new String[]{"courses",""});
               }
          }
     });




     // misc setting content

            regionalspellpn = new JPanel(new GridBagLayout());
            JPanel regradiospn = new JPanel(new GridBagLayout());
            String spellch[] = spellchange.getlists();
            ButtonGroup bg2 = new ButtonGroup();
               ItemListener bglisten2 = new java.awt.event.ItemListener() {
                public void itemStateChanged(ItemEvent e) {
                    if(universal){
                        JRadioButton jrb = (JRadioButton)e.getSource();
                        if(!jrb.isSelected())return;
                        String s = jrb.getText();
                        sharkStartFrame.mainFrame.setSpellChange(s, defaultspell.equals(s));
                    }
                }
              };
            grid.fill = GridBagConstraints.NONE;
            grid.gridx = 0;
            grid.gridy = -1;
            grid.weighty = 0;
            grid.anchor = GridBagConstraints.WEST;
            for(int i = 0; i < spellch.length; i++){
                if(i==0)defaultspell = spellch[i];
                JRadioButton lang = new JRadioButton(spellch[i]);
                lang.setFont(plainfont);
                bg2.add(lang);
                if(spellchange.name==null){
                    if(i==0)lang.setSelected(true);
                }
                else if(spellchange.name.equals(spellch[i]))
                    lang.setSelected(true);
                grid.insets = new Insets(0,10,0,10);
                regradiospn.add(lang,grid);
                lang.addItemListener(bglisten2);
            }
            grid.insets = new Insets(10,10,10,10);
            JLabel spelllb = new JLabel(u.gettext("adminsettings", "spelllb"));
            spelllb.setFont(plainfont);
            grid.gridx = -1;
            grid.gridy = 0;
            grid.anchor = GridBagConstraints.CENTER;
            JPanel pnmisc2 = new JPanel(new GridBagLayout());
            regionalspellpn.add(spelllb,grid);
            regionalspellpn.add(regradiospn,grid);
            regionalspellpn.setBorder(BorderFactory.createEtchedBorder());
            grid.gridx = 0;
            grid.gridy = -1;

            malwayspatch = u.get3wayCheckBox("mupdatescheckall");
            malwayspatch.setFont(plainfont);
            malwayspatch.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    u.my3wayCheckBox cb = ((u.my3wayCheckBox)e.getSource());
                     if (cb.state != u.ALL)   {
                       db.update(sharkStartFrame.optionsdb, "updatescheckall", new String[] {""}, db.TEXT);
                       cb.setState(u.ALL);
                     }
                     else    {
                       db.delete(sharkStartFrame.optionsdb, "updatescheckall", db.TEXT);
                       cb.setState(u.NONE);
                     }
                }
            });



            menforce = u.get3wayCheckBox("menforceprogram");
            menforce.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    u.my3wayCheckBox cb = ((u.my3wayCheckBox)e.getSource());
                     if (cb.state != u.ALL)   {
                       cb.setState(u.ALL);
                          db.update(sharkStartFrame.optionsdb, "menforceprogram", new String[] {""}
                                    , db.TEXT);
                     }
                     else    {
                        db.delete(sharkStartFrame.optionsdb, "menforceprogram", db.TEXT);
                       cb.setState(u.NONE);
                     }
                     sharkStartFrame.mainFrame.whichteacherlast = -1;
                }
            });
            menforce.setFont(plainfont);



         //   pnmisccontent.add(regionalspellpn,grid);
            pnpatch = new JPanel(new GridBagLayout());
            pnpatch.setBorder(BorderFactory.createEtchedBorder());
            grid.anchor = GridBagConstraints.WEST;
            grid.gridx = -1;
            grid.gridy = 0;
            grid.insets = new Insets(10,10,10,10);
            grid.weightx = 0;
            pnpatch.add(malwayspatch, grid);
            grid.insets = new Insets(20,10,20,10);
            pnpatch.add(u.infoLabel(owner, u.gettext("adminsettings", "patchinfo", shark.programName), true), grid);
            grid.insets = new Insets(0,0,0,0);
            grid.weightx = 1;
            pnpatch.add(new JPanel(), grid);

            grid.insets = new Insets(10,10,10,10);
//            grid.anchor = GridBagConstraints.CENTER;


            grid.gridx = -1;
            grid.gridy = 0;
            grid.weightx = 0;
            enforcepn = new JPanel(new GridBagLayout());
            enforcepn.setBorder(BorderFactory.createEtchedBorder());
            grid.insets = new Insets(10,10,10,10);
            enforcepn.add(menforce, grid);
            grid.insets = new Insets(0,10,0,0);

            enforcepn.add(u.infoLabel(owner, u.gettext("adminsettings", "gameenforceinfo"), false), grid);
            grid.insets = new Insets(0,0,0,0);
            grid.weightx = 1;
            enforcepn.add(new JPanel(), grid);
            
            grid.gridx = 0;
            grid.gridy = -1;
            
            grid.anchor = GridBagConstraints.CENTER;
            grid.fill = GridBagConstraints.HORIZONTAL;
            grid.insets = new Insets(10,0,10,0);
            pnmisc2.add(regionalspellpn, grid);
            grid.insets = new Insets(0,0,0,0);

 //           pnmisc2.add(pnpatch, grid);

  //          grid.insets = new Insets(20,0,0,0);


            if(!shark.phonicshark){
                mpeepuni = u.get3wayCheckBox("mpeep");
                mpeepuni.setFont(plainfont);

                peepunipn = new JPanel(new GridBagLayout());
                peepunipn.setBorder(BorderFactory.createEtchedBorder());
                grid.insets = new Insets(20,10,20,10);
                peepunipn.add(mpeepuni, grid);
                grid.insets = new Insets(0,0,0,0);


                pnmisc2.add(peepunipn, grid);
            }

            grid.insets = new Insets(10,0,0,0);

            pnmisc2.add(enforcepn, grid);

            grid.insets = new Insets(0,10,0,10);
            grid.fill = GridBagConstraints.NONE;
            pnmisccontent.add(pnmisc2,grid);
            stumiscpn = new JPanel(new GridBagLayout());
            if(!shark.phonicshark){
                mpeep = u.get3wayCheckBox("mpeep");
                mpeep.setFont(plainfont);
            }



//            mteachnotes = u.get3wayCheckBox("mteachnotes");
//            mteachnotes.setFont(plainfont);
            bypasswork = u.get3wayCheckBox("moverride");
            bypasswork.setFont(plainfont);

            bgcolor = u.get3wayCheckBox("bgcolor");
            bgcolor.setFont(plainfont);
            bgcolor.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    u.my3wayCheckBox cb = ((u.my3wayCheckBox)e.getSource());
                     if(cb.state != u.ALL) {
                        cb.setState(u.ALL);
                        colbt.setEnabled(true);
                     }
                     else {
                       student.queueupdate(students, new String[] {"bgcolor"});
                       cb.setState(u.NONE);
                       coleglb.setBackground(sharkStartFrame.defaultbg);
                       colbt.setEnabled(false);
                     }
                }
            });
            JPanel bgcolpn = new JPanel(new GridBagLayout());
            coleglb = new JLabel("       ");
//            coleglb.setBackground(Color.red);
            coleglb.setOpaque(true);
            colbt = u.sharkButton();
            colbt.setText(u.gettext("select", "label"));
            colbt.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    Color newColor = JColorChooser.showDialog(owner,
                                           bgcolor.getText(),
                                            Color.white);
                    if(newColor != null) {
                        String ss = String.valueOf(newColor.getRGB());
                       student.queueupdate(students,new String[]{"bgcolor",ss});
                       if(ss != null) {
                            Color cc = new Color(Integer.parseInt(ss));
                            coleglb.setBackground(cc);
                        }                       
                    }
                    else {
                      student.queueupdate(students,new String[]{"bgcolor"});
                        bgcolor.setState(u.NONE);
                        student.queueupdate(students,new String[]{"bgcolor"});
                        coleglb.setBackground(sharkStartFrame.defaultbg);
                     }

                }
            });
            grid.gridx = -1;
            grid.gridy = 0;
            grid.insets = new Insets(0,0,0,0);
            bgcolpn.add(bgcolor, grid);
            grid.insets = new Insets(0,10,0,0);
            bgcolpn.add(coleglb, grid);
            bgcolpn.add(colbt, grid);
            grid.insets = new Insets(0,0,0,0);
            grid.gridx = 0;
            grid.gridy = -1;
            if(!shark.phonicshark){
                mpeep.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        u.my3wayCheckBox cb = ((u.my3wayCheckBox)e.getSource());
                         if (cb.state != u.ALL)   {
                           student.queueupdate(students, new String[] {"nopeep"});
                           cb.setState(u.ALL);
                         }
                         else    {
                           student.queueupdate(students, new String[] {"nopeep", "true"});
                           cb.setState(u.NONE);
                         }
                    }
                });
                mpeepuni.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        u.my3wayCheckBox cb = ((u.my3wayCheckBox)e.getSource());
                         if (cb.state != u.ALL)   {
                           cb.setState(u.ALL);
                            db.delete(sharkStartFrame.optionsdb, "uninopeep", db.TEXT);
                         }
                         else    {
                           cb.setState(u.NONE);
                              db.update(sharkStartFrame.optionsdb, "uninopeep", new String[] {""}
                                        , db.TEXT);
                         }
                         sharkStartFrame.mainFrame.whichteacherlast = -1;
                    }
                });
            }
//            mteachnotes.addActionListener(new java.awt.event.ActionListener() {
//                public void actionPerformed(ActionEvent e) {
//                    u.my3wayCheckBox cb = ((u.my3wayCheckBox)e.getSource());
//                     if (cb.state != u.ALL)   {
//                       student.queueupdate(students, new String[] {"mteachnotes", "true"});
//                       cb.setState(u.ALL);
//                     }
//                     else   {
//                       student.queueupdate(students, new String[] {"mteachnotes"});
//                       cb.setState(u.NONE);
//                     }
//                }
//            });
            bypasswork.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(ActionEvent e) {
                     if (bypasswork.state != u.ALL)   {
                       bypasswork.setState(u.ALL);
                     }
                     else    {
                       bypasswork.setState(u.NONE);
                     }
                    boolean st = bypasswork.state==u.ALL;
                    for (short i = 0; i < sharkStartFrame.studentList.length; ++i) {
                      sharkStartFrame.studentList[i].override = false;
                    }
                    sharkStartFrame.studentList[sharkStartFrame.currStudent].override = st;
                }
            });
            grid.fill = GridBagConstraints.NONE;
            grid.anchor = GridBagConstraints.WEST;
            if(shark.phonicshark){
                grid.insets = new Insets(0,10,0,10);
                stumiscpn.add(bypasswork, grid);            
            }
            else{
                grid.insets = new Insets(0,10,10,10);
                stumiscpn.add(bypasswork, grid);
                grid.insets = new Insets(0,10,0,10);
                stumiscpn.add(mpeep, grid);                
            }
//            stumiscpn.add(mteachnotes, grid);

            stumiscpn.add(bgcolpn, grid);
            grid.anchor = GridBagConstraints.CENTER;
            pnmisccontent.add(stumiscpn,grid);


            // font settings content
            
            JPanel gamefontpn = new JPanel(new GridBagLayout());
 
            JLabel lbgamefontmess = new JLabel(u.gettext("adminsettings", "gamefontlb"));
            lbtreefontmess = new JLabel(u.gettext("adminsettings", "treefontlb"));
            lbgamefont = new JLabel();
            lbtreefont = new JLabel();
            gamefontdefault = u.sharkButton();
            gamefontdefault.setText(u.gettext("adminsettings", "usedefault"));
            gamefontdefault.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if(universal){
                        db.delete(sharkStartFrame.optionsdb, "wordfont", db.TEXT);
                        db.delete(sharkStartFrame.optionsdb, "wordfontinfant", db.TEXT);
                        sharkStartFrame.mainFrame.setwordfont();
                        setFont(true);
                    }
                     else{
                        student.queueupdate(students, new String[] {"wordfont"});
                        student.queueupdate(students, new String[] {"wordfontinfant"});
                        set(students, nodename, universal);
                        getsettings();
                        setFont(true);
                     }
                }
            });
            treefontdefault = u.sharkButton();
            treefontdefault.setText(u.gettext("adminsettings", "usedefault"));
            treefontdefault.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    treefontdefault.setEnabled(false);
                    db.delete(sharkStartFrame.optionsdb, "treefont", db.TEXT);
                    u.defaultfont();
                    lbtreefont.setText(sharkStartFrame.treefont.getName());
                    lbtreefont.setFont(sharkStartFrame.treefont);
                    u.okmess(u.gettext("treefont", "heading"),
                             u.gettext("treefont", "message"), owner);
                }
            });
            JButton gamefontchange = u.sharkButton();
            gamefontchange.setText(u.gettext("change", "label"));
            gamefontchange.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if(universal){
                        sharkStartFrame.mainFrame.setadmingamefont();
//startPR2012-06-16^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//                        lbgamefont.setText(sharkStartFrame.wordfont.getName());
//                        lbgamefont.setFont(sharkStartFrame.wordfont);
//                        gamefontdefault.setEnabled(!sharkStartFrame.wordfont.equals(sharkStartFrame.defaultfontname));
                                Font f;
                        String fontdet[] = (String[]) db.find(sharkStartFrame.optionsdb, "wordfont", db.TEXT);
                        if (fontdet != null
                            &&
                          (f =  u.fontFromString(fontdet[0], Integer.parseInt(fontdet[1]), sharkStartFrame.BASICFONTPOINTS)) != null) {
                                        lbgamefont.setText(f.getName());
                                        lbgamefont.setFont(f);
                                        gamefontdefault.setEnabled(!f.equals(sharkStartFrame.defaultfontname));
                        }
                        else{
                                        lbgamefont.setText(sharkStartFrame.defaultfontname.getName());
                                        lbgamefont.setFont(sharkStartFrame.defaultfontname);
                                        gamefontdefault.setEnabled(false);                            
                        }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                    }
                     else{
                         new FontChooser(u.gettext("choosegamefont","label"),originalgameFont==null?sharkStartFrame.defaultfontname:originalgameFont,
                                         sharkStartFrame.defaultfontname,
                                         null, null, false, true, true, owner){
                           public void update() { //OVERRIDE FONTCHOOSER.UPDATE()
                             if (! (chosenfont.equals(originalgameFont))) { //ONLY UPDATE THINGS IF THE FONT HAS CHANGED.
                               if(chosenfont.getFontName().equalsIgnoreCase(sharkStartFrame.defaultfontname.getFontName())){
                                 student.queueupdate(students, new String[] {"wordfont"});
                               }
                               else
                                 student.queueupdate(students, new String[] {"wordfont", u.combineString(new String[] {chosenfont.getName(),
                                   String.valueOf(chosenfont.getStyle())})});
                                        lbgamefont.setText(chosenfont.getName());
                                        lbgamefont.setFont(chosenfont);
                                        gamefontdefault.setEnabled(!chosenfont.equals(sharkStartFrame.defaultfontname));
                             }
                           }
                         };
                         setVisible(true);
                       }
                }
            });
            treefontchange = u.sharkButton();
            treefontchange.setText(u.gettext("change", "label"));
            treefontchange.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    new FontChooser(u.gettext("choosemenufont", "label"),
                                    sharkStartFrame.treefont,
                                    FontChooser.defaultTreeFont,
                                    null, null, true, false, false, owner) {
                      public void update() { //OVERRIDE FONTCHOOSER.UPDATE()
                        if (!(chosenfont.equals(originaltreeFont))) { //ONLY UPDATE THINGS IF THE FONT HAS CHANGED.
                            if(chosenfont.equals(FontChooser.defaultTreeFont))
                            db.delete(sharkStartFrame.optionsdb, "treefont", db.TEXT);
                          else
                            db.update(sharkStartFrame.optionsdb, "treefont",
                                    new String[] {chosenfont.getName(),
                                    String.valueOf(chosenfont.getStyle()),
                                    String.valueOf(Math.min(FontChooser.pointSizeMax, chosenfont.getSize()))}
                                    , db.TEXT);
                          u.defaultfont();
//startPR2012-06-16^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                          lbtreefont.setText(sharkStartFrame.treefont.getName());
                          lbtreefont.setFont(sharkStartFrame.treefont);
                          treefontdefault.setEnabled(!sharkStartFrame.treefont.equals(FontChooser.defaultTreeFont));
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                          u.okmess(u.gettext("treefont", "heading"),
                                   u.gettext("treefont", "message"), owner);
                        }
                      }
                    };
                }
            });
            grid.gridx = 1;
            grid.gridy = 0;
            grid.weightx = 1;
            grid.weighty = 1;
            grid.fill = GridBagConstraints.HORIZONTAL;
            grid.anchor = GridBagConstraints.WEST;
            lbgamefontmess.setFont(smallerplainfont);
            lbtreefontmess.setFont(smallerplainfont);
            treefontchange.setFont(plainfont);
            treefontdefault.setFont(plainfont);
            gamefontchange.setFont(plainfont);
            gamefontdefault.setFont(plainfont);
            grid.fill = GridBagConstraints.BOTH;
            grid.anchor = GridBagConstraints.WEST;
            grid.insets = new Insets(0,10,0,0);
            grid.gridx = 0;
            gamefontpn.add(lbgamefontmess,grid);
            grid.gridy = 1;
            grid.insets = new Insets(0,30,0,0);
            gamefontpn.add(lbgamefont,grid);
            grid.gridx = 1;
            grid.insets = new Insets(0,30,0,0);
            gamefontpn.add(gamefontchange,grid);
            grid.insets = new Insets(0,10,0,0);
            grid.gridx = 2;
            gamefontpn.add(gamefontdefault,grid);
            grid.gridx = 0;
            grid.gridy = 2;
            grid.insets = new Insets(30,10,0,0);
            gamefontpn.add(lbtreefontmess,grid);
            grid.insets = new Insets(0,30,0,0);
            grid.gridy = 3;
            gamefontpn.add(lbtreefont,grid);
            grid.gridx = 1;
            grid.insets = new Insets(0,30,0,0);
            gamefontpn.add(treefontchange,grid);
            grid.insets = new Insets(0,10,0,0);
            grid.gridx = 2;
            gamefontpn.add(treefontdefault,grid);
            grid.fill = GridBagConstraints.NONE;
            grid.gridx = 0;
            grid.gridy = -1;
            grid.weightx = 0;
            grid.weighty = 0;
            grid.insets = new Insets(0,0,0,0);
            pnfontcontent.add(gamefontpn, grid);
            grid.weightx = 1;
            grid.weighty = 1;


            // exclude games settings content
            if(!shark.phonicshark){
                monsetrime = u.get3wayCheckBox("mnotwantonsetrime");
                monsetrime.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        u.my3wayCheckBox cb = ((u.my3wayCheckBox)e.getSource());
                         if (cb.state != u.ALL)   {
                           cb.setState(u.ALL);
                          db.update(sharkStartFrame.optionsdb, "notwantonsetrime", new String[] {""}
                                    , db.TEXT);
                         }
                         else{
                           cb.setState(u.NONE);
                           db.delete(sharkStartFrame.optionsdb, "notwantonsetrime", db.TEXT);
                         }
                        sharkStartFrame.mainFrame.setupgames();
                    }
                });
            }
            mrewardexclude = u.get3wayCheckBox("mrewardexclude");
            mrewardexclude.setText(u.convertToHtml(mrewardexclude.getText()));
            mrewardexclude.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    xexcludeallrewardgames = false;
                    u.my3wayCheckBox cb = ((u.my3wayCheckBox)e.getSource());     
                         if (cb.state != u.ALL)   {
                           cb.setState(u.ALL);
                           if(universal)
                               db.update(sharkStartFrame.optionsdb, "notwantrewards", new String[] {""}
                                    , db.TEXT);
                           else{
                               student.queueupdate(students, new String[] {"excludeallrewardgames", "true"});
                               if(firststu != null)
                                    firststu.doupdates(false, false);
                               for(int i=1;i<students.length; ++i) {
                                    studentlist[i] = student.getStudent(students[i]);
                                    studentlist[i].doupdates(false,false);   // do a mock update to pick up changes
                                }
                           }
                           setGames();

                         }
                         else{
                           cb.setState(u.NONE);
                           if(universal)
                               db.delete(sharkStartFrame.optionsdb, "notwantrewards", db.TEXT);
                           else {
                               student.queueupdate(students, new String[] {"excludeallrewardgames"});
                               if(firststu != null)
                                    firststu.doupdates(false, false);
                               for(int i=1;i<students.length; ++i) {
                                    studentlist[i] = student.getStudent(students[i]);
                                    studentlist[i].doupdates(false,false);   // do a mock update to pick up changes
                                }
                           }
                           setGames();

                         }
                    sharkStartFrame.mainFrame.setupgames();
                }
            });

            JLabel lexclude = new JLabel(u.gettext("mexclude", "label"));
            excludegamespan2 = new JPanel(new GridBagLayout());
            excludegamespan = new JPanel(new GridBagLayout());


  //          excludegamespan2.setBorder(BorderFactory.createEtchedBorder());
            grid.gridx = -1;
            grid.gridy = 0;
            grid.weighty = 0;

            
            mrewardexclude.setFont(smallerplainfont);
            if(!shark.phonicshark){
                monsetrime.setFont(plainfont);
                onsetrimepn = new JPanel(new GridBagLayout());
                grid.insets = new Insets(0,0,0,0);
                onsetrimepn.add(monsetrime, grid);
                onsetrimepn.add(u.infoLabel(owner, u.gettext("adminsettings", "gameonsetrimeinfo")), grid);
            }
            grid.gridx = 0;
            grid.gridy = -1;
     //       if(sharkStartFrame.screendim.height>1000)
     //           grid.insets = new Insets(20,10,30,10);
    //        else
            grid.anchor = GridBagConstraints.WEST;
            
            if(!shark.phonicshark){
                grid.insets = new Insets(10,10,0,10);
                pngamecontent.add(onsetrimepn, grid);
            }
  //           if(sharkStartFrame.screendim.height>1000) 
  //              grid.insets = new Insets(10,10,20,10);
 //           else

  //              grid.insets = new Insets(10,10,10,10);
  //          pngamecontent.add(mrewardexclude, grid);


            grid.weighty = 1;
            grid.fill = GridBagConstraints.BOTH;



            grid.insets = new Insets(10,10,10,10);
            pngamecontent.add(excludegamespan2, grid);
            grid.insets = new Insets(0,0,0,0);
            JPanel jpexc1 = new JPanel(new GridBagLayout());
            JPanel jpexc2 = new JPanel(new GridBagLayout());
            JPanel jpexc3 = new JPanel(new GridBagLayout());
            grid.weighty =1;
            grid.fill = GridBagConstraints.BOTH;
            gameheadlist = new JList();
            gameheadlist.setForeground(Color.blue);
            gameexcluded = new JList();
            gameexcluded.setCellRenderer(new listpainter(jnode.icons[jnode.GAME], jnode.icons[jnode.GAMEBLANK],
                    new String[]{strnoneselected,strmixed })
                     );

            gameTreeMain = new gamestoplay();
            gameTreeMain.setup(sharkStartFrame.publicGameLib,
                         false, true,
                         "",gamestoplay.categories);
             jnode jj1 = ((jnode)((jnode)gameTreeMain.getModel().getRoot()).getChildAt(0)).addChild(new jnode(rewardtitle));
             for(int i=0;i<sharkStartFrame.rw.length;++i) {
               jj1.addChild(new jnode(sharkStartFrame.rw[i]));
             }
             gameTreeMain.model.reload();

            gameTree = new gamestoplay();
            gameTree.setup(sharkStartFrame.publicGameLib,
                         false, true,
                         "",gamestoplay.categories);
            gameTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
             jj1 = ((jnode)((jnode)gameTree.getModel().getRoot()).getChildAt(0)).addChild(new jnode(rewardtitle));
             for(int i=0;i<sharkStartFrame.rw.length;++i) {
               jj1.addChild(new jnode(sharkStartFrame.rw[i]));
             }
             gameTree.model.reload();



            gameTree.getSelectionModel().addTreeSelectionListener(new TreeSelectionListener() {
                 public void valueChanged(TreeSelectionEvent e) {
                     jnode jn = gameTree.getSelectedNode();
                     if(jn!=null){
                         String s = jn.get();
                         // check enough reward games left
                         blockrewardexclude = false;
                         boolean iblockrewardexclude = false;
                         if (u.findString(sharkStartFrame.rw, s)>=0){
                           jnode noderwards[] = ((jnode)((jnode)gameTreeMain.root.getLastChild()).getLastChild()).getChildren();
                           int activerewards = 0;
                           for(int i =  0; i < noderwards.length; i++){
                               String ss = noderwards[i].get();
                               if(u.findString((universal||xexcludegames)?currexcludes:stucurrexcludes, ss)<0 ){
                                  activerewards++;
                               }
                           }
                           if(activerewards<4) iblockrewardexclude = true;
                           if(activerewards<5) blockrewardexclude = true;
                         }                   
                         if(!iblockrewardexclude && (u.findString(universal?currexcludes:( u.addString(stucurrexcludes, currexcludes)  ), s)>=0)){
                             gameTree.clearSelection();
                             btexclude.setEnabled(false);
                         }
                         else
                            btexclude.setEnabled(!iblockrewardexclude && jn!=null && jn.getLevel()!=2 && jn.isLeaf());
                     }
                     else btexclude.setEnabled(false);
                }
            });
            jnode jns[] = ((jnode)((jnode)gameTree.getModel().getRoot()).getChildAt(0)).getChildren();
            gameheading = new String[]{};
            for(int i = 0; i < jns.length; i++){
                gameheading = u.addString(gameheading, jns[i].get());
            }

            String gameheading2[] = (String[])gameheading.clone();

            if(db.query(sharkStartFrame.optionsdb, "notwantrewards", db.TEXT) >= 0)
                gameheading2 = u.removeString(gameheading2, gameheading2.length-1);


            gameheadlist.setListData(gameheading2);
            gameheadlist.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            gameheadlist.addListSelectionListener(new ListSelectionListener() {
                public void valueChanged(ListSelectionEvent e) {
                    if(gameheadlist.getSelectedIndex()<0)return;
                    gameTree.setup(sharkStartFrame.publicGameLib,
                                 false, true,
                                 "",gamestoplay.categories);
                    if(db.query(sharkStartFrame.optionsdb, "notwantrewards", db.TEXT) < 0){
                        jnode jj1 = ((jnode)((jnode)gameTree.getModel().getRoot()).getChildAt(0)).addChild(new jnode(rewardtitle));
                        for(int i=0;i<sharkStartFrame.rw.length;++i) {
                            jj1.addChild(new jnode(sharkStartFrame.rw[i]));
                        }
                    }
                    gameTree.model.reload();
                        jnode newroot = (jnode)((jnode)((jnode)gameTree.getModel().getRoot()).getChildAt(0)).getChildAt(gameheadlist.getSelectedIndex());
                        gameTree.root = newroot;
                        gameTree.root.dontcollapse = true;
                        DefaultTreeModel dtm = new DefaultTreeModel(newroot);
                        dtm.reload();
                        gameTree.setModel(dtm);
         

                    }
                });
            gameexcluded.addListSelectionListener(new ListSelectionListener() {
                public void valueChanged(ListSelectionEvent e) {
                    JList jl = (JList)e.getSource();
                    String s;
                    boolean nonesel = false;
                    if((s=(String)jl.getSelectedValue())!=null){
                        if(s.equals(strnoneselected) || s.equals(strmixed)){
                            nonesel = true;
                        }
                        else{
                            ListCellRenderer lp = gameexcluded.getCellRenderer();
                            if(lp!=null){
                                int uu;
                                int ii[] = ((listpainter)gameexcluded.getCellRenderer()).disableditems;
                                if(ii != null && (uu=jl.getSelectedIndex())>=0 && u.inlist(ii, uu))
                                    nonesel = true;
                            }
                        }
                    }
                    else nonesel = true;
                    if(!nonesel)
                        btundoexclude.setEnabled(jl.getModel().getSize()>0);
                    else {
                        btundoexclude.setEnabled(false);
                        gameexcluded.clearSelection();
                    }

                }
            });

            int xgpanh = gameheadlist.getFixedCellHeight() * 10;

 //                int sw = sharkStartFrame.mainFrame.getWidth();
 //    treepan.setPreferredSize(new Dimension(sw*8/22, sh));
 //    treepan.setMinimumSize(new Dimension(sw*8/22, sh));
 //    rightpan.setPreferredSize(new Dimension((rightwidth=sw*14/22), sh));
 //    rightpan.setMinimumSize(new Dimension(rightwidth, sh));

            
            excludegamespan2.setPreferredSize(new Dimension(sw*14/22, xgpanh));
            excludegamespan2.setMinimumSize(new Dimension(sw*14/22, xgpanh));


            gameexcluded.setFont(plainfont);
            gameheadlist.setFont(plainfont);
            grid.fill = GridBagConstraints.BOTH;
            grid.insets = new Insets(0,0,0,0);
            gameexcluded.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            JLabel jpexc1lb = new JLabel(u.convertToHtml(u.gettext("adminsettings", "selgameheadings")));
            jpexc1lb.setFont(smallerplainfont);
            grid.weighty = 0;
            grid.insets = new Insets(0,0,10,0);
            jpexc1.add(jpexc1lb, grid);
            grid.insets = new Insets(0,0,0,0);
            JScrollPane js1 = new JScrollPane(gameheadlist);
            js1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            js1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            grid.weighty = 1;
            jpexc1.add(js1, grid);
            grid.weighty = 0;
            jpexc1.add(mrewardexclude, grid);
            JLabel jpexc2lb = new JLabel(u.convertToHtml(u.gettext("adminsettings", "selgame")));
            jpexc2lb.setFont(smallerplainfont);
            grid.insets = new Insets(0,0,10,0);
            grid.weighty = 0;
            jpexc2.add(jpexc2lb, grid);
            grid.insets = new Insets(0,0,0,0);
            grid.weighty = 1;
            JScrollPane js2 = new JScrollPane(gameTree);
            js2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            js2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            jpexc2.add(js2, grid);
            gameTree.setCellRenderer(new gametreepainter());
            JLabel jpexc3lb = new JLabel(u.convertToHtml(u.gettext("adminsettings", "excludegames")));
            jpexc3lb.setFont(smallerplainfont);
            grid.insets = new Insets(0,0,10,0);
            grid.weighty = 0;
            jpexc3.add(jpexc3lb, grid);
            grid.insets = new Insets(0,0,0,0);
            JScrollPane js3 = new JScrollPane(gameexcluded);
            js3.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            js3.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            grid.weighty = 1;
            jpexc3.add(js3, grid);
            grid.weighty = 0;
            btexclude = u.sharkButton();
            btundoexclude = u.sharkButton();
            btexclude.setEnabled(false);
            btundoexclude.setEnabled(false);
             btexclude.setPreferredSize(new Dimension(buttondim, buttondim));
             btexclude.setMinimumSize(new Dimension(buttondim, buttondim));
             btundoexclude.setPreferredSize(new Dimension(buttondim, buttondim));
             btundoexclude.setMinimumSize(new Dimension(buttondim, buttondim));
            im = Toolkit.getDefaultToolkit().getImage(sharkStartFrame.publicPathplus + "sprites" +
                                           sharkStartFrame.separator +
                                           "right_il48.png");
            ImageIcon ii = new ImageIcon(im.getScaledInstance(buttonimdim,
                buttonimdim, Image.SCALE_SMOOTH));
            btexclude.setIcon(ii);
            im = Toolkit.getDefaultToolkit().getImage(sharkStartFrame.publicPathplus + "sprites" +
                                           sharkStartFrame.separator +
                                           "left_il48.png");
            ii = new ImageIcon(im.getScaledInstance(buttonimdim,
                buttonimdim, Image.SCALE_SMOOTH));
            btundoexclude.setIcon(ii);
            btexclude.addActionListener(new ActionListener() {
                   public void actionPerformed(ActionEvent e) {
                       jnode jn = gameTree.getSelectedNode();
                       if(jn==null)return;
                       String s = jn.get();
                       if(!universal && u.findString(stucurrexcludes, s)<0 && u.findString(currexcludes, s)<0)
                           stucurrexcludes = u.addString(stucurrexcludes, s);
                       String list[] = new String[]{};
                       for(int i = 0; i < gameexcluded.getModel().getSize(); i++){
                          list = u.addString(list, (String)gameexcluded.getModel().getElementAt(i));
                       }
                       list = u.addString(list, s);
                       xexcludegames = false;
                       setupexcluded(list, true);
                       if(firststu != null)
                            firststu.doupdates(false, false);
                       gameexcluded.setSelectedValue(s, true);
                       btexclude.setEnabled(false);
                       gameTree.repaint();
                       setRewards();
                       if(blockrewardexclude){
                           if(!hasMinRewardWarn){
                              u.okmess(shark.programName, u.gettext("adminsettings", "minrewardwarn", u.gettext("mrewardexclude", "label")));
                              hasMinRewardWarn = true;
                           }
                       }
                }
             });
             btundoexclude.addActionListener(new ActionListener() {
                   public void actionPerformed(ActionEvent e) {
                     int selints[] = gameexcluded.getSelectedIndices();
                     int lowestint = gameexcluded.getModel().getSize();
                     for(int i = 0; selints!=null && i < selints.length; i++){
                        String s = (String)gameexcluded.getModel().getElementAt(selints[i]);
                        if(u.findString(stucurrexcludes, s)>=0)
                           stucurrexcludes = u.removeString2(stucurrexcludes, s);
                        if(selints[i]<lowestint)lowestint = selints[i];
                     }
                     lowestint = Math.min(gameexcluded.getModel().getSize(), lowestint);
                    String list[] = new String[]{};
                    for(int i = 0; i < gameexcluded.getModel().getSize(); i++){
                       if(u.inlist(selints, i))
                           continue;
                       list = u.addString(list, (String)gameexcluded.getModel().getElementAt(i));
                    }
                    setupexcluded(list, true);
                    if(firststu != null)
                        firststu.doupdates(false, false);
                    if(gameexcluded.getModel().getSize()>=0){
                        gameexcluded.setSelectedIndex(Math.max(0, lowestint - 1));
                     }
                    btundoexclude.setEnabled(gameexcluded.getSelectedIndex()>=0);
                    gameTree.repaint();
                    setRewards();
                  }
             });

            JPanel butpn = new JPanel(new GridBagLayout());
            grid.fill = GridBagConstraints.NONE;
            grid.weightx = 0;
            grid.insets = new Insets(0,0,10,0);
            butpn.add(btexclude, grid);
            grid.insets = new Insets(0,0,0,0);
            butpn.add(btundoexclude, grid);
            grid.weightx = 1;
            grid.gridx = -1;
            grid.gridy = 0;
            grid.fill = GridBagConstraints.BOTH;
            JPanel excludelower = new JPanel(new GridBagLayout());

            jpexc1.setPreferredSize(new Dimension((sw*14/22)/3, xgpanh));
            jpexc1.setMinimumSize(new Dimension((sw*14/22)/3, xgpanh));
            jpexc2.setPreferredSize(new Dimension((sw*14/22)/3, xgpanh));
            jpexc2.setMinimumSize(new Dimension((sw*14/22)/3, xgpanh));
            jpexc3.setPreferredSize(new Dimension((sw*14/22)/4, xgpanh));
            jpexc3.setMinimumSize(new Dimension((sw*14/22)/3, xgpanh));

            grid.weighty = 1;
             grid.weightx = 0;
            grid.insets = new Insets(0,0,0,10);
            grid.weightx =30;
            excludelower.add(jpexc1, grid);
  //          grid.weightx = 0;
            excludelower.add(jpexc2, grid);
            grid.weightx = 0;
            excludelower.add(butpn, grid);
            grid.weightx = 30;
            excludelower.add(jpexc3, grid);
            grid.weightx = 1;
            grid.gridx = 0;
            grid.gridy = -1;
            grid.weighty = 0;
            grid.insets = new Insets(0,0,0,0);
            grid.gridx = -1;
            grid.gridy = 0;
            JPanel excludepn = new JPanel(new GridBagLayout());
            grid.fill = GridBagConstraints.VERTICAL;
            grid.weightx = 0;
            grid.anchor = GridBagConstraints.WEST;
            excludepn.add(lexclude, grid);
            grid.insets = new Insets(0,10,0,0);
            excludepn.add(u.infoLabel(owner, u.gettext("adminsettings", "gameexclude")), grid);
            grid.insets = new Insets(0,0,10,0);
            grid.weightx = 1;
            excludepn.add(new JPanel(), grid);
            grid.fill = GridBagConstraints.BOTH;
            grid.weightx = 1;
            grid.gridx = 0;
            grid.gridy = -1;
            excludegamespan.add(excludepn, grid);
            grid.weighty = 1;
            grid.insets = new Insets(0,0,0,0);
            excludegamespan.add(excludelower, grid);
            grid.gridx = 0;
            grid.gridy = -1;




            // reward game settings content
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
     mrewards = new u.my3wayCheckBox[rw.length];

     int half = rw.length/2;
     int xx = 0;
     int yy = 0;
     boolean newcol = false;
     grid.anchor = GridBagConstraints.WEST;
     for (int i = 0; i < rw.length; ++i) {
         grid.gridx = xx;
         grid.gridy = yy++;
         if(newcol)grid.insets = new Insets(0,0,0,0);
         if(i>=half && !newcol){
             newcol = true;
             xx++;
             yy = 0;
             grid.insets = new Insets(0,0,0,0);
         }
       rewardpn.add(mrewards[i] = u.get3wayCheckBox2(rw[i]), grid);
 //      mrewards[i].setToolTipText(u.gettext("msetrewards","tooltip"));
       mrewards[i].setFont(plainfont);
       mrewards[i].addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(ActionEvent e) {
           String okrewards2[] = new String[0];
           u.my3wayCheckBox curr = ( (u.my3wayCheckBox) e.getSource());
           if(curr.state == u.SOME) {
             for (short i = 0; i < mrewards.length; ++i) {
                 if(mrewards[i].isEnabled())
                   mrewards[i].setState(u.ALL);
             }
           }
           else if(curr.state != u.ALL) curr.setState(u.ALL);
           else curr.setState(u.NONE);
           for (short i = 0; i < mrewards.length; ++i) {
             if (mrewards[i].state == u.ALL) {
               okrewards2 = u.addString(okrewards2, mrewards[i].getText());
              }
           }
           if(okrewards2.length < 3) {
             curr.setState(u.ALL);
             okrewards2 = new String[0];
             for (short i = 0; i < mrewards.length; ++i) {
              if (mrewards[i].state == u.ALL) {
                okrewards2 = u.addString(okrewards2, mrewards[i].getText());
               }
             }
           }
           okrewards = okrewards2;
           if(okrewards.length == mrewards.length)
               student.queueupdate(students, new String[]{"okrewards"});
           else 
               student.queueupdate(students, new String[]{"okrewards", u.combineString(okrewards)});
         }
       });
     }

     grid.insets = new Insets(0,0,0,0);
     grid.gridx = 0;
     grid.gridy = -1;
     mrewardfreqs = new u.my3wayCheckBox[] {u.get3wayCheckBox("mreward1"),u.get3wayCheckBox("mreward2"),u.get3wayCheckBox("mreward3"),u.get3wayCheckBox("mreward4")};
     mnoreward = u.get3wayCheckBox("mnoreward");
     mnoreward.setFont(plainfont);
     mnoreward.addActionListener(new java.awt.event.ActionListener() {
       public void actionPerformed(ActionEvent e) {
         if (mnoreward.state != u.ALL)  {
           student.queueupdate(students, new String[] {"noreward", "true"});
           for (int i = 0; i < 4; ++i) {
             mrewardfreqs[i].setState(u.NONE);
           }
           mnoreward.setState(u.ALL);
         }
       }
     });

     for (int i = 0; i < 4; ++i) {
         mrewardfreqs[i].setFont(plainfont);
       mrewardfreqs[i].addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(ActionEvent e) {
           int i;
           for (i = 0; i < 4; ++i) {
              if(mrewardfreqs[i].getActionListeners()[0] == this) {
                  mrewardfreqs[i].setState(u.ALL);
                  student.queueupdate(students, new String[]{"rewardfreq",String.valueOf (i)});
                  mnoreward.setState(u.NONE);
              }
              else mrewardfreqs[i].setState(u.NONE);
           }
         }
       });
       rewardfreqpn.add(mrewardfreqs[i], grid);
     }
     rewardfreqpn.add(mnoreward, grid);






         grid.gridx = 0;
     grid.gridy = -1;
     JPanel rewardpn3 = new JPanel(new GridBagLayout());
     JPanel rewardfreqpn3 = new JPanel(new GridBagLayout());

     grid.weighty = 0;
     JLabel rew3 = new JLabel(u.gettext("adminsettings", "rewardchoice"));
     JLabel rewf3 = new JLabel(u.gettext("adminsettings", "rewardfreq"));
     rew3.setFont(plainfont);
     rewf3.setFont(plainfont);
     grid.insets = new Insets(10,0,0,0);
     rewardpn3.add(rew3, grid);
     rewardfreqpn3.add(rewf3, grid);
     grid.insets = new Insets(10,0,10,0);
     grid.weighty = 1;
     rewardpn3.add(rewardpn2, grid);
     rewardfreqpn3.add(rewardfreqpn2, grid);

     grid.gridx = -1;
     grid.gridy = 0;
     grid.anchor = GridBagConstraints.CENTER;



     
     rewardmainpn = new JPanel(new GridBagLayout());
     JPanel rewardmainpn2 = new JPanel(new GridBagLayout());
     
     
     rewardmainpn2.add(rewardpn3, grid);
     rewardmainpn2.add(rewardfreqpn3, grid);
     
     
     
     
     grid.weightx = 1;
     grid.fill = GridBagConstraints.BOTH;
     rewardmainpn.add(rewardmainpn2, grid);
     
     
     grid.insets = new Insets(0,0,0,0);

     grid.weightx = 0;
     grid.weighty = 0;
     grid.gridx = 0;
     grid.gridy = -1;

     JLabel rewardwarnlb = new JLabel(u.convertToHtml(u.gettext("adminsettings", "rewardwarn")));
     rewardwarnlb.setFont(smallerplainfont);
     pnrewardwarn = new JPanel(new GridBagLayout());
     pnrewardwarn.add(rewardwarnlb, grid);
     grid.weightx = 1;
     grid.weighty = 0;
     grid.insets = new Insets(10,0,10,0);
     rewardmainpn.add(pnrewardwarn, grid);
     
     
     
     grid.gridx = -1;
     grid.gridy = 0;
     grid.weighty = 1;
     pnrewardcontent.add(rewardmainpn, grid);
     
     norewardslb = new JLabel(strexcluededrewards);
     norewardslb.setFont(smallerplainfont);
     grid.fill = GridBagConstraints.NONE;
     pnrewardcontent.add(norewardslb, grid);
     rewardson(true);
     grid.weightx = 0;


     // noise settings content

    opView =  new JSlider(JSlider.HORIZONTAL, 0, 7, noise.beepvol);
     opView.setToolTipText(u.gettext("vol_slider","tooltip"));
     opView.setMajorTickSpacing(1);
     opView.addChangeListener(new ChangeListener() {
        public void stateChanged(ChangeEvent e) {
//          if(xbeepvol)
//            opView.setBackground(sharkStartFrame.defaultbg);
           if(!opView.isShowing())return;
          if (students != null) {
            student.queueupdate(students, new String[]{"beepvol",String.valueOf(opView.getValue())});
          }
          else {
            noise.beepvol = (byte) opView.getValue();
            student.setOption("s_beepvol", (short) noise.beepvol);
            noise.setnew();
          }
        }
     });
    JButton sample = u.Button("vol_test");
     sample.addActionListener(new java.awt.event.ActionListener() {
           public void actionPerformed(ActionEvent e) {
              byte save = noise.beepvol;
              noise.beepvol = (byte) opView.getValue();
              noise.setnew();
              noise.beep();
              u.pause(200);
              noise.groan();
              u.pause(200);
              noise.squeek();
              u.pause(200);
              noise.plop();
              noise.beepvol = save;
              noise.setnew();
           }
     });
     grid.gridx=-1;
     grid.gridy=0;
     nogroan = u.get3wayCheckBox("nogroan");
     nogroan.setFont(plainfont);
       nogroan.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(ActionEvent e) {
           if(nogroan.state != u.ALL) nogroan.setState(u.ALL);
             else  nogroan.setState(u.NONE);
           if (noise.nogroan = nogroan.state == u.ALL)
                     student.queueupdate(students,new String[]{"nogroan","true"});
            else     student.queueupdate(students,new String[]{"nogroan"});
         }
       });
     
     nogroan.setBorder(null);

     grid.fill = GridBagConstraints.NONE;
     grid.gridx=-1;
     grid.gridy=0;
     JPanel samplepn = new JPanel(new GridBagLayout());
     JPanel noisepn = new JPanel(new GridBagLayout());
     JLabel vollb = new JLabel(u.gettext("adminsettings", "vol"));
     vollb.setFont(plainfont);
     samplepn.add(vollb, grid);
     samplepn.add(opView, grid);
     samplepn.add(sample, grid);
     grid.gridx=0;
     grid.gridy=-1;
     
     noisepn.add(samplepn,grid);
     grid.insets = new Insets(20,0,0,0);
     grid.anchor = GridBagConstraints.CENTER;
     noisepn.add(nogroan,grid);
     
     grid.insets = new Insets(0,0,0,0);
     pnnoisecontent.add(noisepn, grid);



     // picture settings content
       importbt = u.sharkButton();
       importbt.setText(u.gettext("import", "label"));
       importbt.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
            doimport();
          }
        });

    nostuimports_icon = u.get3wayCheckBox("nostuimportsicon");
       nostuimports_icon.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(ActionEvent e) {
           if(nostuimports_icon.state != u.ALL) {
               nostuimports_icon.setState(u.ALL);
               db.delete(sharkStartFrame.optionsdb, "nostuimportsicon", db.TEXT);
               sharkStartFrame.allowStuImportPics_Icon = true;
           }
           else  {
              nostuimports_icon.setState(u.NONE);
              db.update(sharkStartFrame.optionsdb, "nostuimportsicon", new String[] {""}, db.TEXT);
              sharkStartFrame.allowStuImportPics_Icon = false;
           }
         }
       });

    nostuimports_ownwords = u.get3wayCheckBox("nostuimportsownwords");
       nostuimports_ownwords.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(ActionEvent e) {
           if(nostuimports_ownwords.state != u.ALL) {
               nostuimports_ownwords.setState(u.ALL);
               db.delete(sharkStartFrame.optionsdb, "nostuimportsownwords", db.TEXT);
               sharkStartFrame.allowStuImportPics_OwnWords = true;
           }
           else  {
              nostuimports_ownwords.setState(u.NONE);
              db.update(sharkStartFrame.optionsdb, "nostuimportsownwords", new String[] {""}, db.TEXT);
              sharkStartFrame.allowStuImportPics_OwnWords = false;
           }
         }
       });


       importbt.setFont(plainfont);
       nostuimports_icon.setFont(smallerplainfont);
       nostuimports_ownwords.setFont(smallerplainfont);
       grid.fill = GridBagConstraints.NONE;
       grid.anchor = GridBagConstraints.CENTER;

        grid.weightx=0;
        grid.weighty= 0;
        JLabel jl1 = new JLabel(u.gettext("adminsettings", "importpics"));
        grid.insets = new Insets(0,0,0,0);
        jl1.setFont(smallerplainfont);
        JPanel jblnpic = new JPanel(new GridBagLayout());
        grid.insets = new Insets(0,0,10,10);

        grid.gridx=-1;
        grid.gridy= 0;
        grid.insets = new Insets(10,10,10,10);
        JPanel pnbutimport = new JPanel(new GridBagLayout());
        pnbutimport.add(importbt, grid);
        pnbutimport.add(u.infoLabel(owner, u.gettext("adminsettings", "importinfo", shark.programName)), grid);
        grid.gridx=0;
        grid.gridy= -1;
        grid.anchor = GridBagConstraints.NORTHWEST;
        grid.weightx=1;
        grid.weighty= 0;
        JPanel jblnpic2 = new JPanel(new GridBagLayout());

        grid.insets = new Insets(10,10,0,10);
        jblnpic2.add(jl1, grid);
        grid.insets = new Insets(0,0,0,0);
        jblnpic2.add(pnbutimport, grid);
         grid.weighty= 1;
        jblnpic2.add(new JPanel(), grid);

        picpref = new picturepreferences(owner, false, this, universal);

        grid.gridx=-1;
        grid.gridy= 0;
        jpicnostupn = new JPanel(new GridBagLayout());
        jpicnostupnownwords = new JPanel(new GridBagLayout());
        grid.anchor = GridBagConstraints.CENTER;
        grid.fill = GridBagConstraints.NONE;
        grid.weightx=0;
        grid.insets = new Insets(0,0,0,10);
        jpicnostupn.add(nostuimports_icon, grid);

        jpicnostupn.add(u.infoLabel(owner, u.gettext("adminsettings", "nostuimportinfoicons"), !u.screenResWidthMoreThan(1200)), grid);

        jpicnostupnownwords.add(nostuimports_ownwords, grid);
        jpicnostupnownwords.add(u.infoLabel(owner, u.gettext("adminsettings", "nostuimportinfowords"), !u.screenResWidthMoreThan(1200)), grid);

        JPanel import2pn = new JPanel(new GridBagLayout());
        JButton importbt_uni = u.sharkButton();
        importbt_uni.setText(u.gettext("import", "label"));
        importbt_uni.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
            doimport();
          }
        });
        importbt_uni.setFont(plainfont);


        JLabel jl2 = new JLabel(u.gettext("adminsettings", "importpics"));
        jl2.setFont(smallerplainfont);

        import2pn.add(jl2, grid);
        import2pn.add(importbt_uni, grid);
        import2pn.add(u.infoLabel(owner, u.gettext("adminsettings", "importinfo", shark.programName)), grid);



        grid.gridx=-1;
        grid.gridy= 0;
        grid.weightx=1;
        jpicpn = new JPanel(new GridBagLayout());
        grid.anchor = GridBagConstraints.NORTHWEST;
        jblnpic.setBorder(BorderFactory.createEtchedBorder());


        grid.insets = new Insets(0,0,0,0);
        grid.fill = GridBagConstraints.VERTICAL;
        grid.weightx=1;
        grid.weighty= 1;
        grid.anchor = GridBagConstraints.NORTH;
        jblnpic.add(jblnpic2, grid);
        



        grid.gridx=0;
        grid.gridy= -1;


        unipiccontent = new JPanel(new GridBagLayout());
        grid.fill = GridBagConstraints.NONE;
        grid.anchor = GridBagConstraints.WEST;
                grid.weightx=0;
        grid.weighty= 0;

        JPanel unioptions = new JPanel(new GridBagLayout());
        grid.insets = new Insets(0,0,20,0);
//        unioptions.add(import2pn, grid);
        unioptions.add(jpicnostupn, grid);
        unioptions.add(jpicnostupnownwords, grid);
         grid.anchor = GridBagConstraints.CENTER;

        unipiccontent.add(unioptions, grid);



        grid.insets = new Insets(0,0,0,0);
        grid.weightx=1;
        grid.weighty= 1;
        grid.gridx=-1;
        grid.gridy= 0;
        grid.anchor = GridBagConstraints.NORTH;
        grid.fill = GridBagConstraints.BOTH;
 //       jpicpn.add(jblnpic, grid);   // import 1
        grid.insets = new Insets(0,0,0,0);
        jpicpn.add(picpref, grid);
        grid.insets = new Insets(10,10,10,10);
        pnpicturecontent.add(jpicpn, grid);
        pnpicturecontent.add(unipiccontent, grid);



        grid.weightx=1;
        grid.weightx= 1;


        grid.gridx=0;
        grid.gridy= -1;
        cbdisplaynoadmins.setEnabled(cbdisplaynames.isSelected());
        lbdisplaynamesadmins.setEnabled(cbdisplaynames.isSelected());
        cbautocreate.setEnabled(cbautosignon.isSelected());
        lbautocreateinfo.setEnabled(cbautosignon.isSelected());
        setupExceptions.setEnabled(cbautosignon.isSelected());
        lbautoexclusioninfo.setEnabled(cbautosignon.isSelected());
         grid.fill = GridBagConstraints.BOTH;
//         settingstitlelabel.setBackground(Color.gray);
         grid.insets = new Insets(0,0,0,0);

         
              int sh = sharkStartFrame.mainFrame.getHeight();
         JPanel topfiller = new JPanel();
         topfiller.setPreferredSize(new Dimension(1, sh/14));
          topfiller.setMinimumSize(new Dimension(1, sh/14));
         JPanel bottomfiller = new JPanel();
         bottomfiller.setPreferredSize(new Dimension(1, sh/30));
          bottomfiller.setMinimumSize(new Dimension(1, sh/30));
           grid.weighty = 0;
         grid.insets = new Insets(0,0,5,0);
         settingstitlepan.add(lbpn, grid);
           grid.insets = new Insets(0,0,0,0);
          add(settingstitlepan, grid);
         grid.weighty = 1;
         add(settingscontentpan, grid);
         grid.weighty = 0;
         if(sharkStartFrame.screendim.height>1000)
           add(bottomfiller, grid);

          grid.weighty = 1;

         settingscontentpan.add(pnclicktoset, grid);
         settingscontentpan.add(pnnoise, grid);
         settingscontentpan.add(pnmisc, grid);
         settingscontentpan.add(pnuserrecords, grid);
         settingscontentpan.add(pnsignon, grid);
         settingscontentpan.add(pnreward, grid);
         settingscontentpan.add(pncourse, grid);
         settingscontentpan.add(pnfont, grid);
         settingscontentpan.add(pnkeypad, grid);
         settingscontentpan.add(pnpicture, grid);
         settingscontentpan.add(pngame, grid);
         if(universal)
             refresh();
         setPanel(pnclicktoset);
    }

    void rewardson(boolean rewardson){
        rewardmainpn.setVisible(rewardson);
        norewardslb.setVisible(!rewardson);
        if(!rewardson){
            if(xexcludeallrewardgames)
                norewardslb.setText(xstrexcluededrewards);
            else 
                norewardslb.setText(strexcluededrewards);
        }
        else{
            rewardmainpn.setVisible(true);
        }
        rewardmainpn.repaint();
    }

    void doimport(){
            PickPicture pp = new PickPicture(universal,students,nodename, false);
                   
             sharkStartFrame.mainFrame.setState(sharkStartFrame.mainFrame.ICONIFIED);
    }

    void setupexcluded(String s[], boolean update){
        if((s==null || s.length==0) && (universal || !xexcludegames)){
            s = new String[]{strnoneselected};
        }
        if(s.length >= 1){
            s = u.removeString2(s, strnoneselected);
            s = u.removeString2(s, strmixed);
        }
        if(!universal && xexcludegames){
            if(s==null)s = new String[]{strmixed};
            else s = u.addString(s, strmixed);
        }
        if(update){
            String nonglobalexcludes[] = null;
            for(int i = 0; !universal && i < s.length; i++){
                if(s[i].equals(strmixed)||s[i].equals(strnoneselected))continue;
                if(u.findString(currexcludes, s[i])<0){
                    if(nonglobalexcludes==null)nonglobalexcludes = new String[]{s[i]};
                     else nonglobalexcludes = u.addString(nonglobalexcludes, s[i]);
                }
            }
            String toupdate[] = s;
            while (u.findString(toupdate, strmixed)>=0)
                toupdate = u.removeString2(toupdate, strmixed);
            while (u.findString(toupdate, strnoneselected)>=0)
                toupdate = u.removeString2(toupdate, strnoneselected);
                dosetexcludes(toupdate, nonglobalexcludes);
            gameTree.clearSelection();
        }


        if(db.query(sharkStartFrame.optionsdb, "notwantrewards", db.TEXT) >= 0 ||
               (!universal && (xexcludeallrewardgames || firststu.option2("s_excludeallrewardgames"))) ){
                for(int i = 0; i < sharkStartFrame.rw.length; i++){
                    if(u.findString(s, sharkStartFrame.rw[i])>=0){
                       s = u.removeString2(s, sharkStartFrame.rw[i]);
                    }
                }
        }

        gameexcluded.setListData(s);
        if(!universal){
            int is[] = new int[]{};
            for(int i = 0; s!=null && i<s.length; i++){
                if(u.findString(currexcludes, s[i])>=0){
                    is = u.addint(is, i);
                }
            }

            ListCellRenderer lp = gameexcluded.getCellRenderer();
            if(lp!=null){
                ((listpainter)gameexcluded.getCellRenderer()).disableditems = is;
            }
        }
    }

    void dosetexcludes(String s[], String[] nonunis){
        if(universal){
            if(s!=null && s.length==0){
                s=null;
                currexcludes = sharkStartFrame.mainFrame.universalExcludedGames = null;
                db.delete(sharkStartFrame.optionsdb, "uniexcludegames", db.TEXT);
            }
            else{
                currexcludes = sharkStartFrame.mainFrame.universalExcludedGames = s;
                db.update(sharkStartFrame.optionsdb, "uniexcludegames", u.combineString(s), db.TEXT);
            }
            sharkStartFrame.mainFrame.setupgames();
        }
        else{
            if(nonunis!=null){
                student.queueupdate(students,u.addString(new String[]{"excludegames"},nonunis));
            }
            else
                student.queueupdate(students, new String[]{"excludegames"});
        }
    }


    void setkeydis(String kpname) {
     if(kpname == null) {
       mnokeypad.setState(u.ALL);
       for (int i = 0; i < mkeypads.length; ++i) {
         mkeypads[i].setState(u.NONE);
       }
     }
     else {
       mnokeypad.setState(u.NONE);
       for (int i = 0; i < mkeypads.length; ++i) {
         mkeypads[i].setState(mkeypads[i].getText().equals(kpname)?u.ALL:u.NONE);
       }
     }
   }

    void sethighlight(JPanel p){
        if(p.equals(pnnoise)||p.equals(pnmisc)||p.equals(pnuserrecords)
                ||p.equals(pnsignon)||p.equals(pnreward)||p.equals(pncourse)||p.equals(pnfont)
                ||p.equals(pnkeypad)||p.equals(pnpicture)||p.equals(pngame)){
                 lbpn.setBackground(Color.white);
                 lbpn.setOpaque(true);
        }
        Color col1 = isInAdmin?admin.worksettingpancolor:Color.gray;
        Color col2 = Color.white;
        hlnoise.setBackground(p.equals(pnnoise)?col2:col1);
        hlmisc.setBackground(p.equals(pnmisc) ? col2 : col1);
        hluserrecords.setBackground(p.equals(pnuserrecords) ? col2 : col1);
        hlsignon.setBackground(p.equals(pnsignon) ? col2 : col1);
        hlreward.setBackground(p.equals(pnreward) ? col2 : col1);
        hlcourse.setBackground(p.equals(pncourse) ? col2 : col1);
        hlfont.setBackground(p.equals(pnfont) ? col2 : col1);
        hlkeypad.setBackground(p.equals(pnkeypad) ? col2 : col1);
        hlpicture.setBackground(p.equals(pnpicture) ? col2 : col1);
        hlgame.setBackground(p.equals(pngame) ? col2 : col1);
    }

   void setPanel(JPanel p){
       pnclicktoset.setVisible(p.equals(pnclicktoset));
       pnnoise.setVisible(p.equals(pnnoise));
       pnmisc.setVisible(p.equals(pnmisc));
       pnuserrecords.setVisible(p.equals(pnuserrecords));
       pnsignon.setVisible(p.equals(pnsignon));
       pnreward.setVisible(p.equals(pnreward));
       pncourse.setVisible(p.equals(pncourse));
       pnfont.setVisible(p.equals(pnfont));
       pnkeypad.setVisible(p.equals(pnkeypad));
       pnpicture.setVisible(p.equals(pnpicture));
       pngame.setVisible(p.equals(pngame));
       sethighlight(p);
    }

    void setGames(){
    //    !xexcludeallrewardgames && !firststu.option2("s_excludeallrewardgames")
        boolean withrewards;
        mrewardexclude.setVisible(true);
        boolean wantrewards_uni = db.query(sharkStartFrame.optionsdb, "notwantrewards", db.TEXT) < 0;
        if(universal){
            mrewardexclude.setState(!wantrewards_uni?u.ALL:u.NONE);
            withrewards = wantrewards_uni;
        }
        else{
            boolean wantrewards = !firststu.option2("s_excludeallrewardgames");
            mrewardexclude.setVisible(wantrewards_uni);
            mrewardexclude.setState(xexcludeallrewardgames?u.SOME:(!wantrewards?u.ALL:u.NONE));
            withrewards = !xexcludeallrewardgames && !firststu.option2("s_excludeallrewardgames") && wantrewards;
        }
        
        rewardson(withrewards);
        if(withrewards){
            int i = gameheadlist.getSelectedIndex();
            gameheadlist.setListData(gameheading);
            gameheadlist.setSelectedIndex(i);    
        }
        else{
            int i = gameheadlist.getSelectedIndex();
            String gameheading2[] = (String[])gameheading.clone();
            gameheading2 = u.removeString(gameheading2, gameheading2.length-1);
            gameheadlist.setListData(gameheading2);
            gameheadlist.setSelectedIndex(Math.min(i, gameheadlist.getModel().getSize()-1));           
        }
            
        if(!shark.phonicshark){
            monsetrime.setState(db.query(sharkStartFrame.optionsdb, "notwantonsetrime", db.TEXT) >= 0?u.ALL:u.NONE);
            onsetrimepn.setVisible(universal);
        }
         ListCellRenderer lp = gameexcluded.getCellRenderer();
         if(lp!=null){
                ((listpainter)gameexcluded.getCellRenderer()).disableditems = null;
         }
        gameheadlist.setSelectedIndex(0);

        
        grid.fill = GridBagConstraints.BOTH;
        grid.anchor = GridBagConstraints.WEST;
        grid.weighty = 1;
        grid.weightx = 1;
        grid.gridx = 0;
        grid.gridy = -1;
        excludegamespan2.removeAll();
      if(universal || sharkStartFrame.screendim.height>800){

            grid.insets = new Insets(10,10,10,10);
            excludegamespan2.setBorder(BorderFactory.createEtchedBorder());
            excludegamespan2.add(excludegamespan, grid);
        }
        else{
            grid.insets = new Insets(0,0,0,0);
            excludegamespan2.setBorder(BorderFactory.createEmptyBorder());
            excludegamespan2.add(excludegamespan, grid);
        }




            String exstr;
            if((exstr = (String)db.find(sharkStartFrame.optionsdb, "uniexcludegames", db.TEXT))!=null)
                currexcludes = u.splitString(exstr);
            else
                currexcludes = new String[]{};
            String oks[] = null;
            for(int i = 0; currexcludes!=null && i < currexcludes.length; i++){
                if(gameTreeMain.find(currexcludes[i])!=null){
                    if(oks==null) oks = new String[]{currexcludes[i]};
                    else oks = u.addString(oks, currexcludes[i]);
                }
            }
            if(!universal && xexcludegames){
               currexcludes = new String[]{strmixed};
            }
            else if(oks == null)
                currexcludes = new String[]{strnoneselected};
            else
                currexcludes = oks;


        stucurrexcludes = new String[0];
        if(universal || xexcludegames){
            setupexcluded(currexcludes, false);
        }
         else{
            oks = null;
            for(int i = 0; firststu.excludegames!=null && i < firststu.excludegames.length; i++){
                if(gameTreeMain.find(firststu.excludegames[i])!=null
                        && u.findString(currexcludes, firststu.excludegames[i])<0){
                    if(oks==null) oks = new String[]{firststu.excludegames[i]};
                    else oks = u.addString(oks, firststu.excludegames[i]);
                }
            }

            stucurrexcludes = u.addString(currexcludes, oks);
            setupexcluded(stucurrexcludes,false);
         }
    }

   void setFont(boolean redo){
       if(!universal){
            if(xwordfont){
                lbgamefont.setText(u.gettext("adminsettings", "mixed"));
                lbgamefont.setFont(largerplainfont);
                originalgameFont = null;
                gamefontdefault.setEnabled(true);
            }
            else {
                String ss =  firststu.optionstring2("s_wordfont");
                Font f;
                String fontdet[];
                if(!redo && ss != null) {
                    fontdet = u.splitString(ss);
                    gamefontdefault.setEnabled(true);
                }
                else {
                    fontdet = (String[]) db.find(sharkStartFrame.optionsdb, "wordfont", db.TEXT);
                    gamefontdefault.setEnabled(false);
                }
                if (fontdet == null
                   ||
                   (f = u.fontFromString(fontdet[0], Integer.parseInt(fontdet[1]), sharkStartFrame.BASICFONTPOINTS)) == null){
                    f =  sharkStartFrame.defaultfontname;
                    gamefontdefault.setEnabled(false);
                }
                originalgameFont = f;
                lbgamefont.setText(originalgameFont.getName());
                lbgamefont.setFont(originalgameFont);
            }
       }
       else{
           Font f;
           String fontdet[] = fontdet = (String[]) db.find(sharkStartFrame.optionsdb, "wordfont", db.TEXT);
           if (fontdet == null
                   ||
                   (f = u.fontFromString(fontdet[0], Integer.parseInt(fontdet[1]), sharkStartFrame.BASICFONTPOINTS)) == null){
                        f =  sharkStartFrame.defaultfontname;
           }
           originalgameFont = f;
           lbgamefont.setText(originalgameFont.getName());
           lbgamefont.setFont(originalgameFont);
           gamefontdefault.setEnabled(!originalgameFont.equals(sharkStartFrame.defaultfontname));
       }
        originaltreeFont = sharkStartFrame.treefont;
        lbtreefont.setText(originaltreeFont.getName());
        lbtreefont.setFont(originaltreeFont);
        treefontdefault.setEnabled(!originaltreeFont.equals(FontChooser.defaultTreeFont));
   }

   void setPictures(){
       jpicnostupn.setVisible(universal);
       jpicnostupnownwords.setVisible(universal);
       unipiccontent.setVisible(universal);
 //      importbt.setVisible(universal);
       jpicpn.setVisible(!universal);
       picpref.setVisible(!universal);
       if(universal){
            nostuimports_icon.setState(db.query(sharkStartFrame.optionsdb, "nostuimportsicon", db.TEXT) >= 0?u.NONE:u.ALL);
            nostuimports_ownwords.setState(db.query(sharkStartFrame.optionsdb, "nostuimportsownwords", db.TEXT) >= 0?u.NONE:u.ALL);
       }
       else{
            picpref.setup();
       }
   }

   void setMisc(){
       bypasswork.setVisible(!universal && firststu.name.equals(sharkStartFrame.studentList[sharkStartFrame.currStudent].name));
       if(!universal){
           if(!shark.phonicshark){
               boolean unipeep_no = db.query(sharkStartFrame.optionsdb, "uninopeep", db.TEXT) >= 0;
               mpeep.setEnabled(!unipeep_no);
               if(unipeep_no){
                  mpeep.setState(u.NONE);
               }
               else
                  mpeep.setState(xpeep?u.SOME:(!firststu.option2("s_nopeep")?u.ALL:u.NONE));
           }
//            mteachnotes.setState(xteachnotes?u.SOME:!firststu.option2("noteachnotes")?u.ALL:u.NONE);
            bypasswork.setState(sharkStartFrame.studentList[sharkStartFrame.currStudent].override?u.ALL:u.NONE);
            String ss = null;
            bgcolor.setState(xbgcolor?u.SOME:((ss=firststu.optionstring2("bgcolor"))==null?u.NONE:u.ALL));
            colbt.setEnabled(bgcolor.state == u.ALL || bgcolor.state == u.SOME);
            if(bgcolor.state == u.NONE)
                coleglb.setBackground(sharkStartFrame.defaultbg);
            else if(bgcolor.state == u.SOME)
                coleglb.setBackground(Color.gray);
            else if(ss != null) {
                 Color cc = new Color(Integer.parseInt(ss));
                coleglb.setBackground(cc);
            }
       }
       else{
           if(!shark.phonicshark)
               mpeepuni.setState(db.query(sharkStartFrame.optionsdb, "uninopeep", db.TEXT) < 0?u.ALL:u.NONE);
           menforce.setState(db.query(sharkStartFrame.optionsdb, "menforceprogram", db.TEXT) >= 0?u.ALL:u.NONE);
           colbt.setEnabled(bgcolor.isSelected());
           malwayspatch.setState(db.query(sharkStartFrame.optionsdb, "updatescheckall", db.TEXT) >= 0?u.ALL:u.NONE);
       }
       if(!shark.phonicshark)   
           peepunipn.setVisible(universal);
       enforcepn.setVisible(universal);
       pnpatch.setVisible(false);
   }

   void setKeypad(){
       for (int i = 0; i < mkeypads.length; ++i) { //for 1.1
         if(xkeypad)
             mkeypads[i].setState(u.SOME);
       }
       if(!xkeypad) setkeydis(firststu.optionstring2("keypad"));
       else mnokeypad.setState(u.SOME);
   }

    void setCourses(){
        pncoursedefault.setVisible(universal);
//        if(universal)
            pncoursebordered.setBorder(BorderFactory.createEtchedBorder());
//        else pncoursebordered.setBorder(null);

        String hidden[] = getUniversalHiddenCourses();
        String okcourses[] = new String[]{};
        String stunoncourses[] = new String[]{};
        if(!universal && firststu!=null){
            String s = firststu.optionstring2("s_courses");
            if(s != null){
                stunoncourses = u.addString(stunoncourses, u.splitString(s));
            }
            stunoncourses = u.addString(stunoncourses, hidden);
        }
        
    //    defcourse.geti

        defcourse.removeAllItems();
        for (short i = 0; i < mcourses.length; ++i) {
            String text = mcourses[i].getText();
            if(!universal)
                mcourses[i].setState(xcourses?u.SOME:(u.findString(stunoncourses, text) < 0?u.ALL:u.NONE));
            else{
                mcourses[i].setState((u.findString(hidden, text) < 0 && u.findString(stunoncourses, text) < 0)?u.ALL:u.NONE);
            }
            if(!universal && hidden!=null){
              mcourses[i].setEnabled(u.findString(hidden, text) < 0);
            }
            else if(universal)
                mcourses[i].setEnabled(true);

            if(mcourses[i].state == u.ALL){
              defcourse.addItem(mcourses[i].getText());
              okcourses = u.addString(okcourses, text);
          }
        }
        String startcourse = (String)db.find(sharkStartFrame.optionsdb, "start_topic", db.TEXT);
        int k;
        if(startcourse!=null && (k=u.findString(okcourses, startcourse))>=0){
            defcourse.setSelectedIndex(k);
        }
    }

    void setNoise(){
       int bv = -1;
       if(xbeepvol)  {
           opView.setValue(bv=DEFAULTBEEPVOL);
  //         opView.setBackground(Color.yellow);
       }
       else
           opView.setValue(bv=firststu.optionval2("s_beepvol"));
       if(bv<0) opView.setValue(DEFAULTBEEPVOL);
         if(xnogroan) {
           nogroan.setState(u.SOME);
         }
         else nogroan.setState(firststu.option2("s_nogroan")?u.ALL:u.NONE);

    }

    void setIcons(JButton jb){
         btsignon.setIcon( jb!=null &&  jb.equals(btsignon)?iisignon:iisignonoff);
         btnoise.setIcon(jb!=null &&  jb.equals(btnoise)?iinoise:iinoiseoff);
         btfont.setIcon(jb!=null &&  jb.equals(btfont)?iifont:iifontoff);
         btpicture.setIcon(jb!=null &&  jb.equals(btpicture)?iipicture:iipictureoff);
         btkeypad.setIcon(jb!=null &&  jb.equals(btkeypad)?iikeypad:iikeypadoff);
         btreward.setIcon(jb!=null &&  jb.equals(btreward)?iireward:iirewardoff);
         btgame.setIcon(jb!=null &&  jb.equals(btgame)?iigame:iigameoff);
         btcourse.setIcon(jb!=null &&  jb.equals(btcourse)?iicourse:iicourseoff);
         btmisc.setIcon(jb!=null &&  jb.equals(btmisc)?iimisc:iimiscoff);
         btuserrecords.setIcon(jb!=null &&  jb.equals(btuserrecords)?iiuserrecords:iiuserrecordsoff);
    }


    void setRewards(){
       if(universal)return;
       
       boolean selffound = false;
       for(int i = 0; i < students.length; i++){
           if(students[i].equals(sharkStartFrame.studentList[sharkStartFrame.currStudent].name)){
               selffound = true;
               break;
           }
       }
       
       pnrewardwarn.setVisible(!selffound);
       
       boolean excludeallrewards = xexcludeallrewardgames || firststu.option2("s_excludeallrewardgames");
       rewardson(!excludeallrewards);
       
       okrewards = sharkStartFrame.rw;
       String okrewardchoices[] =  okrewards;
       String excl[] = settings.getAllExcludeGames(firststu.excludegames);
       if(xexcluderewardgames){
           String stuexcludes[] = new String[0];
           for(int i = 0; i < studentlist.length; i++){
               if(studentlist[i]!=null)
                   stuexcludes = u.addString(stuexcludes, studentlist[i].excludegames);
           }
           for(int i = okrewards.length-1; i >=0; i--){
                if(u.findString(stuexcludes, okrewards[i])>=0){
                    okrewards = u.removeString(okrewards, i);
                }
           }
       }
       else{
           
           String sr = firststu.optionstring2("s_okrewards");
           if(sr!=null)
               okrewardchoices = u.splitString(sr);
           String allowedrewards[] = new String[0];
           for(int i = okrewards.length-1; i >=0; i--){
               if(u.findString(excl, okrewards[i])>=0){
                   okrewards = u.removeString2(okrewards, okrewards[i]);
               }
               else allowedrewards = u.addString(allowedrewards, okrewards[i]);
           }
           for(int i = okrewardchoices.length-1; i >=0; i--){
               if(u.findString(okrewards, okrewardchoices[i])<0){
                   okrewardchoices = u.removeString2(okrewardchoices, okrewardchoices[i]);
               }
           }
           if(okrewardchoices.length<3){
               // not 3 left - remove reward game preferences
               firststu.queueupdate(students, new String[]{"okrewards"});
               okrewardchoices = okrewards;
           }
           for(int i = okrewards.length-1; i >=0; i--){
               if(u.findString(firststu.excludegames, okrewards[i])>=0){
                   okrewards = u.removeString2(okrewards, okrewards[i]);
               }
           }
           if(okrewards.length<3){
               // not 3 left - remove personally excluded reward games
//               firststu.removeOption2("s_excludegames");
                for(int i2 = firststu.excludegames.length-1; i2 >= 0; i2--){
                    if(u.findString(sharkStartFrame.rw, firststu.excludegames[i2])>=0){
                       firststu.excludegames = u.removeString(firststu.excludegames, i2);
                    }
                }
               firststu.queueupdate(students, u.addString(new String[]{"excludegames"},firststu.excludegames));
               okrewards = allowedrewards;
           }
        }
       
       

       
       for (int i = 0; i < sharkStartFrame.rw.length; ++i) {
           if(u.findString(okrewards, sharkStartFrame.rw[i])<0){
               mrewards[i].setState(u.NONE);
               mrewards[i].setEnabled(false);
           }
           else{
               mrewards[i].setState((xrewards||xexcluderewardgames)?u.SOME:(u.findString(okrewardchoices, sharkStartFrame.rw[i])>=0 ? u.ALL:u.NONE));
               mrewards[i].setEnabled(true);
           }
       }
       for (int i = 0; i < 4; ++i) {
           if(xrewardfreq) mrewardfreqs[i].setState(u.SOME);
           else if(firststu.option2("noreward")) mrewardfreqs[i].setState(u.NONE);
           else mrewardfreqs[i].setState(i == firststu.rewardfreq?u.ALL:u.NONE);
       }
       mnoreward.setState(xrewardfreq?u.SOME:(firststu.option2("noreward")?u.ALL:u.NONE));
    }


   void refresh(){
       
//String strclickttoset_stu = u.gettext("adminsettings", "clicktoset_stu");
//String strclickttoset_group = u.gettext("adminsettings", "clicktoset_group");
//String strclickttoset_uni = u.gettext("adminsettings", "clicktoset_uni");
//String strclickttoset_own = u.gettext("adminsettings", "clicktoset_own");       
       
       if(universal){
           clicktoset.setText(strclickttoset_uni);
       }
       else if(students!=null && students.length>1){
           clicktoset.setText(strclickttoset_group);
       }
       else if(firststu.name.equals(sharkStartFrame.studentList[sharkStartFrame.currStudent].name)){
           clicktoset.setText(strclickttoset_own);
       }
       else{
           clicktoset.setText(strclickttoset_stu);
       }
        setCourses();
        setMisc();
        setFont(false);
        setGames();
        setPictures();
        if(!universal){
            setKeypad();
            setRewards();
            setNoise();
       }
        setIcons(null);
    }


   public void set(){
       set(null);
   }
   
   public  void set(student stuAlreadyGot){
        if(students!=null && students.length>0){
           if(stuAlreadyGot!=null && stuAlreadyGot.name.equals(students[0]))firststu = stuAlreadyGot;
           else firststu =student.findStudent(students[0]);
           if(firststu==null)
               return;
           if(firststu.name.equals(sharkStartFrame.studentList[sharkStartFrame.currStudent].name))
               usertype=  usertype_YOU;
           if(students.length==1) studentlist = new student[]{firststu}; // rb 14/11/06
           else {
             studentlist = new student[students.length];
             studentlist[0] = firststu;
             for(int i=1;i<students.length; ++i) {
                if(stuAlreadyGot!=null && stuAlreadyGot.name.equals(studentlist[i]))studentlist[i] = stuAlreadyGot;
                else studentlist[i] = student.getStudent(students[i]);                  
 //               studentlist[i] = student.getStudent(students[i]);
                if(studentlist[i]!=null)
                    studentlist[i].doupdates(false,false);   // do a mock update to pick up changes
             }
           }
           getsettings();
        }
        refresh();
    }


   public  void set(String stus[], String name, boolean uni){
       students = stus;
       nodename = name;
       set2(uni);
        set();
    }
   
   public  void set(String stus[], String name, boolean uni, student stuAlreadyGot){
       students = stus;
       nodename = name;
       set2(uni);
        set(stuAlreadyGot);
    }   

    void set2(boolean uni){
        universal = uni;
        hlsignon.setVisible(uni);
        hlkeypad.setVisible(!uni);
        hlreward.setVisible(!uni && db.query(sharkStartFrame.optionsdb, "notwantrewards", db.TEXT) < 0);
        regionalspellpn.setVisible(uni && shark.language.equals(shark.LANGUAGE_EN));
        stumiscpn.setVisible(!uni);
        malwayspatch.setVisible(uni);
        hlnoise.setVisible(!uni);
//        hlmisc.setVisible(!universal || shark.language.equals(shark.LANGUAGE_EN));
        hlmisc.setVisible(true);
        hluserrecords.setVisible(uni);

//        settingstitlelabel.setText(uni?strunisettings:strsettings);
        settingstitlelabel.setText(" ");
        treefontchange.setVisible(uni);
        lbtreefontmess.setVisible(uni);
        lbtreefont.setVisible(uni);
        treefontdefault.setVisible(uni);
        
        setPanel(pnclicktoset);
    }

   static String[] getUniversalHiddenCourses() {
    String hidden[] = null;
    String defaulthhiddencourses = (String)db.find(sharkStartFrame.optionsdb, "universalhiddencourses", db.TEXT);
    if(defaulthhiddencourses==null || defaulthhiddencourses.trim().equals(""))
        hidden = u.defaulthiddencourses;
    else if(defaulthhiddencourses.equals(settings.ALLCOURSES)){
        hidden = null;
    }
    else hidden = u.splitString(defaulthhiddencourses);
    return hidden;
  }
   
   void getsettings() {
     String s0,s1;
     xpicpref=xexcludegames=xexcluderewardgames=xexcludeallrewardgames=xrewardfreq=xkeypad=xteachnotes=xrewards=xbgcolor=xpeep=xcourses=xnogroan=xbeepvol=xwordfont=xtreefont=xwantsigns=xwantfingers=xwantrealpics=xwantsignvids=false;
     for(int i=1;i<studentlist.length;++i) {
       if(studentlist[i] == null) continue;
       if(studentlist[i].option2("noreward") != studentlist[0].option2("noreward")
          || !studentlist[i].option2("noreward")
               && studentlist[i].rewardfreq != studentlist[0].rewardfreq) xrewardfreq = true;
       if(!u.equalStrings(studentlist[i].optionstring2("bgcolor"),studentlist[0].optionstring2("bgcolor")))
                                             xbgcolor = true;
       if(!u.equalStrings(studentlist[i].optionstring2("s_courses"),studentlist[0].optionstring2("s_courses")))
                                            xcourses = true;
       if(!u.equalStrings(studentlist[i].optionstring2("s_picpref"),studentlist[0].optionstring2("s_picpref")))
                                            xpicpref = true;

       String excludegs = studentlist[i].optionstring2("s_excludegames");
       String excludegs_first = studentlist[0].optionstring2("s_excludegames");
       String arr_excludegs[] = null;
       String arr_excludegs_first[] = null;


       if(excludegs!=null)arr_excludegs = u.splitString(excludegs);
       if(arr_excludegs!=null){
           for(int j = arr_excludegs.length-1; j >= 0; j--){
                if( u.findString(sharkStartFrame.rw, arr_excludegs[j])<0){
                     arr_excludegs = u.removeString2(arr_excludegs, arr_excludegs[j]);
                }
           }
       }
       if(excludegs_first!=null)arr_excludegs = u.splitString(excludegs_first);
       if(arr_excludegs_first!=null){
           for(int j = arr_excludegs_first.length-1; j >= 0; j--){
                if( u.findString(sharkStartFrame.rw, arr_excludegs_first[j])<0){
                     arr_excludegs_first = u.removeString2(arr_excludegs_first, arr_excludegs_first[j]);
                }
           }
       }
         
       if(!u.equalStrings(arr_excludegs,arr_excludegs_first))
            xexcluderewardgames = true;


       if(!u.equalStrings(excludegs,excludegs_first))
                                            xexcludegames = true;
       if(!u.equalStrings(studentlist[i].optionstring2("s_okrewards"),studentlist[0].optionstring2("s_okrewards")))
                                                                            xrewards = true;
       if(!u.equalStrings(studentlist[i].optionstring2("keypad"),studentlist[0].optionstring2("keypad")))
                                         xkeypad = true;
       if(!u.equalStrings(studentlist[i].optionstring2("s_wordfont"),studentlist[0].optionstring2("s_wordfont")))
                                         xwordfont = true;
      if(!u.equalStrings(studentlist[i].optionstring2("s_treefont"),studentlist[0].optionstring2("s_treefont")))
                                         xtreefont = true;
      if(studentlist[i].option2("noteachnotes") != studentlist[0].option2("noteachnotes")) xteachnotes = true;
      if(studentlist[i].option2("s_nopeep") != studentlist[0].option2("s_nopeep")) xpeep = true;
      if(studentlist[i].option2("s_excludeallrewardgames") != studentlist[0].option2("s_excludeallrewardgames")) xexcludeallrewardgames = true;
      if(studentlist[i].optionval2("s_beepvol") != studentlist[0].optionval2("s_beepvol")) xbeepvol = true;
      if(studentlist[i].option2("s_nogroan") != studentlist[0].option2("s_nogroan")) xnogroan = true;
      if(!shark.numbershark) {
         if(studentlist[i].wantsignvids != studentlist[0].wantsignvids) xwantsignvids = true;
         if(studentlist[i].wantsigns != studentlist[0].wantsigns) xwantsigns = true;
         if(studentlist[i].wantrealpics != studentlist[0].wantrealpics) xwantrealpics = true;
         if(studentlist[i].option2("s_wantfingersall") != studentlist[0].option2("s_wantfingersall"))
                                      xwantfingers = true;
      }
     }
   }

static String[] getAllExcludeGames(String[] studentexcludes){
     if(sharkStartFrame.mainFrame.universalExcludedGames!=null){
         if(studentexcludes==null)return sharkStartFrame.mainFrame.universalExcludedGames;
         else return  u.addString(sharkStartFrame.mainFrame.universalExcludedGames, studentexcludes);
     }
     return studentexcludes;
}


public class gametreepainter extends treepainter {
      public gametreepainter() {
         setFont(normalfont);
         setOpaque(true);
      }
      public Component getTreeCellRendererComponent(JTree tree,
                  Object o,boolean selected,boolean expanded,boolean leaf,
                  int row,boolean hasfocus) {
          super.getTreeCellRendererComponent(tree,o,selected,expanded,leaf,row,hasfocus);
          if(o == null) return this;
          jnode node = ((jnode)o);
          if(node!=null) {
              node.dontexpand = false;
             if (u.findString((universal||xexcludegames)?currexcludes:stucurrexcludes, node.get())>=0 ){
                 setForeground(Color.lightGray);
                 setIcon(jnode.icons[jnode.GAMEBLANK]);
                 if(!node.dontcollapse && !node.isLeaf())
                     node.dontexpand = true;
             }
           }
          return this;
      }
  }


public static class pnUserRecords extends JPanel {
    
   String deletelast;
   Font plainfont = sharkStartFrame.treefont.deriveFont(Font.PLAIN);
   Font smallerplainfont = plainfont.deriveFont((float)plainfont.getSize()-1);
   int iimonths[];
   String ssmonths[];
   
      public pnUserRecords() {
          super();
          setLayout(new GridBagLayout());
          GridBagConstraints grid = new GridBagConstraints();
          grid.gridx = -1;
          grid.gridy = 0;
          grid.weightx = 0;
          grid.weighty = 0;
          grid.fill = GridBagConstraints.NONE;
          JLabel jlDeleteWarn = new JLabel(u.convertToHtml(u.gettext("records", "archivemess1")+"&nbsp;&nbsp;&nbsp;"+ u.edit( u.gettext("records", "archivemess2"),sharkStartFrame.sharedPathplus+u.gettext("sturec_", "folder"), shark.programName)));
          jlDeleteWarn.setFont(smallerplainfont);
          ssmonths = u.splitString(u.gettext("records", "delrecperiods"));
          String ss[] = u.splitString(u.gettext("records", "delrecperiods_int"));
          iimonths = new int[]{};
          for(int i = 0; i < ss.length; i++){
              iimonths = u.addint(iimonths, Integer.parseInt(ss[i]));
          }
                    
          JComboBox combo = new JComboBox(ssmonths);
            combo.addItemListener(new ItemListener() {
              public void itemStateChanged(ItemEvent e) {
                String s = (String)((JComboBox)e.getSource()).getSelectedItem();
                if(s==null || s.equals(deletelast))return;
                deletelast = s;
                int i = ((JComboBox)e.getSource()).getSelectedIndex();
                if(i==0){
                    if(db.query(sharkStartFrame.optionsdb,"keepallrecords_",db.TEXT)>=0)
                        db.delete(sharkStartFrame.optionsdb,"keepallrecords_",db.TEXT);
                }
                else{
                    String months = (String)db.find(sharkStartFrame.optionsdb,"keeprecordsfor_", db.TEXT);
                    String sm = String.valueOf(findSelectedIndex(i));
                    if(!sm.equals(months))
                        db.update(sharkStartFrame.optionsdb,"keeprecordsfor_", sm, db.TEXT);
                }
            }
          });  
          String months = (String)db.find(sharkStartFrame.optionsdb,"keeprecordsfor_", db.TEXT);
          combo.setSelectedItem(null);
          int monthval = months==null ? 0 : Integer.parseInt(months);
          if(monthval<0)monthval = 0;
          combo.setSelectedIndex(findSelectedIndex(monthval));  
          JLabel jlArchiveDesc = new JLabel(u.convertToHtml(u.gettext("records", "archivemess3", shark.programName)));
          jlArchiveDesc.setFont(smallerplainfont);            
          JPanel pnMain = new JPanel(new GridBagLayout()); 
          JPanel pnMain2 = new JPanel(new GridBagLayout()); 
          JPanel pnMainSurround = new JPanel(new GridBagLayout()); 
          grid.gridx = 0;
          grid.gridy = 0;
          grid.anchor = GridBagConstraints.EAST; 
          grid.insets = new Insets(0,0,0,15);
          pnMain.add(new JLabel(u.convertToHtml(u.gettext("delete", "label")+":")), grid);
          grid.insets = new Insets(0,0,0,0);
          grid.anchor = GridBagConstraints.CENTER; 
          grid.gridx = 1;
          pnMain.add(combo, grid);
          grid.gridy = 1;
          grid.gridx = 0;
          JButton jbArchive = new JButton(u.gettext("records", "archive"));
          
     jbArchive.addActionListener( new java.awt.event.ActionListener() {
         String start;
           public void actionPerformed(ActionEvent e) {
            if(!u.yesnomess("stu_archiveq")) return;
            int i, j;
  //          String archiving = u.gettext("sturec_","archiving");
            File f1 = new File(sharkStartFrame.sharedPath, u.gettext("sturec_", "folder"));
            if (!f1.exists())
              f1.mkdir();
            for (i = 0; i < student.alllist.length; ++i) {
              student stu = student.findStudent(student.alllist[i]);
              if (stu != null) {
    //            lab.setText(u.edit(archiving, stu.name));
                stu.addarchive();
                Date last = null, first = new Date();
                topicPlayed firstgp = null, lastgp = null;
                if (stu.studentrecord.length > 0) {
                  first = stu.studentrecord[0].date;
                  firstgp = stu.studentrecord[0];
                  last = stu.studentrecord[stu.studentrecord.length - 1].date;
                  lastgp = stu.studentrecord[stu.studentrecord.length - 1];
                }
                start = u.gettext("sturec_", "file");
                start = u.edit(start.substring(0, start.length() - 5), stu.name, shark.USBprefix);
                File list[] = f1.listFiles(new FileFilter() {
                  public boolean accept(File file) {
                    return file.getName().startsWith(start) && file.getName().endsWith(student.recend);
                  }
                });
                if (list.length > 0) {
                  topicPlayed gp = student.getfirst(list[0]);
                  if (gp != null) {
                    first = gp.date; firstgp = gp; }
                  if (last == null) {
                    gp = student.getfirst(list[list.length - 1]);
                    if (gp != null) {
                      last = gp.date; lastgp = gp; }
                  }
                }
                if (last == null)
                  continue;
                File f2 = new File(f1,u.edit(u.gettext("sturec_", "filearchive"), new String[] {
                                          stu.name,
                                          shark.USBprefix,
                                          new mySimpleDateFormat("yyyy-MM", firstgp.timezone).format(first),
                                          new mySimpleDateFormat("yyyy-MM", lastgp.timezone).format(last)}));
                try {
                  ObjectOutputStream f3 = new ObjectOutputStream(new FileOutputStream(f2));
//                  for (j = 0; j < list.length; ++j) {
//                  for (j = list.length-1; j >= 0; j--) {
                  for(j = 0; j < list.length; j++){
                     FileInputStream fis = new FileInputStream(list[j]);
                     ObjectInputStream ois = new ObjectInputStream(fis);
                     topicPlayed[] tt = (topicPlayed[]) ois.readObject();
                     ois.close();
                     fis.close();
                     f3.writeObject(tt);
                  }
                  if (stu.studentrecord.length > 0) {
                    f3.writeObject(stu.studentrecord);
                    student.queueupdate(new String[]{stu.name}, u.addString(new String[]{"delsturec"},  new String[]{"all"}));
                  }
                  f3.close();
                  for (j = 0; j < list.length; ++j) {
                     new FileOutputStream(list[j]).close();
                     if(!list[j].delete()) {
                       list[j].deleteOnExit();
                    }
                 }
                }
                catch (IOException e1) {
                }
                catch(ClassNotFoundException e2) {
                }
              }
            }
            u.okmess(shark.programName, u.gettext("records", "complete"));
   //         lab.setText(u.gettext("sturec_", "archivedone"));
           }
     });          
          
          
          
          
          
          grid.insets = new Insets(15,0,0,0);
          grid.anchor = GridBagConstraints.EAST; 
          grid.insets = new Insets(15,0,0,15);
          pnMain.add(new JLabel(u.convertToHtml(u.gettext("records", "archive")+":")), grid);
          grid.insets = new Insets(0,0,0,0);
          grid.anchor = GridBagConstraints.CENTER; 
          grid.insets = new Insets(0,0,0,0);
          grid.gridx = 1;
          grid.fill = GridBagConstraints.HORIZONTAL;
          grid.insets = new Insets(15,0,0,0);
          pnMain.add(jbArchive, grid);
          grid.insets = new Insets(0,0,0,0);
          grid.fill = GridBagConstraints.NONE;
          grid.gridx = 0;
          grid.gridy = -1;  
          grid.fill = GridBagConstraints.BOTH;  
          grid.insets = new Insets(5,5,5,5);
          pnMain2.add(pnMain, grid);
          grid.insets = new Insets(0,5,5,5);
          pnMain2.add(jlArchiveDesc, grid);
          grid.fill = GridBagConstraints.NONE;          
          pnMain2.setBorder(BorderFactory.createEtchedBorder());
          grid.anchor = GridBagConstraints.WEST; 
          pnMainSurround.add(new JLabel(u.gettext("records", "reclab")),grid);
          grid.anchor = GridBagConstraints.CENTER; 
          pnMainSurround.add(pnMain2, grid);
          this.add(pnMainSurround, grid);
          this.add(jlDeleteWarn, grid);
          
          
          
          
          
          
          
      }
     
      
      int findSelectedIndex(int k){
          for(int i = 0; i < iimonths.length; i++){
              if(iimonths[i]==k){
                  return i;
              }
          }
          return -1;
      }
      
  }
}
