package shark;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.tree.*;
//startPR2004-10-06^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
import java.awt.print.*;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
public class wordlist
//startPR2004-10-06^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ ^^^^^^^^^^^^^^^^^^^^^^^^PR
    extends JList
    implements Printable {
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   boolean keepGroupings,splitting;
   public boolean shuffled,redrawing,nostandard;
   public int printtype;
//   public static Color wlbgcol = Color.white;
   public static Color wlbgcol = new Color(248,248,255);
//startPR2013-02-20^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   public static Color bgcoloruse = wlbgcol;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   public static final byte FLASH = 0;
   public static final byte FLASH2 = 1;
   public static final byte PRINT1 = 2;
   public static final byte PRINT2 = 3;
   public static final byte PRINT4 = 4;
   public static final byte PRINT8 = 5;
//startPR2007-02-16^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   // for extended lists
   public static final byte PRINTEX = 6;
   Font f3 = null;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR

   /**
    * True when the wordlist is fully extended
    */
   public boolean wholeextended;
   /**
    * True when words have been split into syllables
    */
   public boolean split;
   boolean highlight;
   boolean vowels;
   public boolean shapes;
   boolean unsplit,split4,arePairs,areCompanions,areSingles;
   public boolean areGroupings;
   public boolean canextend;

   static final byte STANDARD = 0;
   static final byte EXTENDED = 1;
   static final byte EXTENDEDWHOLE = 2;

   boolean changing = true;
   int wordtot;
   byte ordersel;
   public short order[];
   public word[] words, originalwords;
   static wordlist current;
   static  DefaultListModel model = new DefaultListModel();
   static MouseListener orderaction = new java.awt.event.MouseAdapter() {
          public void mouseReleased(MouseEvent e) {
             if(current != null && !current.changing)
                 current.newselection(current.orderCB.getselected());
          }
   };
//   public static JCheckBox vowelsCB;
   /**
    * For adding splits
    */
   public static JCheckBox splitCB;
   public static JPanel splitsPan = new JPanel(new GridBagLayout());
 //  public static mbutton savesplits  = u.mbutton("savesplits");
   public static JCheckBox phonemes;
//   public static JCheckBox inorder;
   public static JRadioButton standard;
   public static JRadioButton extended;
   public static JRadioButton extendedwhole;
 //  public static mbutton removesplits = u.mbutton("removesplits");
   static String existingword = u.gettext("uwl_","existingword");
//startPR2006-09-11^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   static String existingsound = u.gettext("uwl_","existingsound");
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   public orderPanel orderCB;
   /**
    * Currently displayed image
    */
//startPR2008-06-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    /**
     * Currently displayed image
     */
//    static wordpicture currpic;
    static JWindow currpic;
    static JWindow currpic2;
//endPRimagesandvids^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR

   word selword;
   static final Color selbg = new Color(0,0,128);
   static final int MARGIN = 4;
   static String yoursound,yoursound2;
   static String nosound,nosound2;
//startPR2006-08-22^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   // message for phonics lists when a sound hasn't been allocated to each split
   static String nophonics;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   static String formaterror;
   static String ispicture;
   static String issign;
   public static boolean phonicsounds,phonicswords,singlesound,usephonics,forcephonics;
   public static boolean usetranslations,usedefinitions;
   boolean temp,splitchanged;
   boolean phonicsw;
   word lastword;
   /**
    * Number of words in the word list that have been split
    */
   short splittot;
   static String splitlab1,splitlab2,splitlab3,splittool1,splittool2,splittool3;
   static boolean changedfont;
   boolean rightclick;
   String name;
   Font font;
   String extrainf;
   Object extrainfo;
   Font extrafont;
   FontMetrics extram;
//startPR2004-10-06^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    /**
     * allows skipping of the buffer's graphics context during mac printing
     */
    private int lastPage = -1;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   Window parent = sharkStartFrame.mainFrame;
   MouseMotionListener mousemotion =  new MouseMotionAdapter()   {
            public void mouseMoved(MouseEvent e) {
               int pos = locationToIndex(e.getPoint());
               int old = getSelectedIndex();
               if(pos != old) {
                 if(currpic != null) {
                    if(currpic instanceof wordpicture){
                        wordpicture wpic = ((wordpicture) currpic);
                        if(wpic.closeOnWordListEnter)
                            removeCurrPic();
                    }
                    else{
                        removeCurrPic();
                    }
                 }
                 if(currpic2 != null) {
                    if(currpic2 instanceof wordpicture){
                        wordpicture wpic = ((wordpicture) currpic);
                        if(wpic.closeOnWordListEnter)
                            removeCurrPic();
                    }
                    else{
                        removeCurrPic();
                    }
                 }
                 setSelectedIndex(pos);
               }
               if(splitting) buildtempsplit(pos,e.getX());
          }
          public void mouseDragged(MouseEvent e) {
             mouseMoved(e);
           }
   };
//startPR2007-07-12^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   static String phonicsplit = String.valueOf(u.phonicsplit);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2008-06-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   public boolean saywords = true;
//endPRimagesandvids^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   
  ImageIcon inorderonicon;
  ImageIcon warn;
  ImageIcon inorderofficon;
  ImageIcon refreshicon;
  ImageIcon vowelsofficon;
  ImageIcon vowelsonicon;
  
  
  ImageIcon devsplitsofficon;
  ImageIcon devsplitsonicon;  
  
  Dimension imageButDim;

  JPanel splitsUpper = new JPanel(new GridBagLayout());
  static int border = 5;
  public JButton butHV;
  JButton butInOrder;
  JButton butRefresh;
  public boolean highlightvowels = false;
  public boolean keepinorder = false;
  public JCheckBox def;
  public JCheckBox translations;
  JButton butSaveSplits;
  JButton butRemoveSplits;
  JButton butDevSplits;
  public student ownliststu;
  
  public String printWords[];  
  static boolean splitsInDevMode = false;


   public wordlist() {
      if(current == null) {
         setModel(model);

         setCellRenderer(new itempainter());
         setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        splitsPan.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
        imageButDim = new Dimension((sharkStartFrame.screenSize.width / 3)/20,
                                                 (sharkStartFrame.screenSize.width / 3)/20);
         JPanel splitsLower = new JPanel(new GridBagLayout());
         splitsLower.setOpaque(false);
         butSaveSplits = u.sharkButton();
         butSaveSplits.setToolTipText(u.gettext("wl_splitsave", "tooltip"));
         Toolkit t = Toolkit.getDefaultToolkit();
         Image tempim = t.getImage(sharkStartFrame.publicPathplus + "sprites" +
                                       sharkStartFrame.separator +
                                       "savedisc_il48.png");
        ImageIcon saveicon = new ImageIcon(tempim.getScaledInstance( (int)imageButDim.getWidth(),
            (int) imageButDim.getHeight(), Image.SCALE_SMOOTH));

        tempim = t.getImage(sharkStartFrame.publicPathplus + "sprites" +
                                       sharkStartFrame.separator +
                                       "inorderON_il48.png");
        inorderonicon = new ImageIcon(tempim.getScaledInstance( (int)imageButDim.getWidth(),
            (int) imageButDim.getHeight(), Image.SCALE_SMOOTH));
        tempim = t.getImage(sharkStartFrame.publicPathplus + "sprites" +
                                       sharkStartFrame.separator +
                                       "inorderOFF_il48.png");
        inorderofficon = new ImageIcon(tempim.getScaledInstance( (int)imageButDim.getWidth(),
            (int) imageButDim.getHeight(), Image.SCALE_SMOOTH));

        tempim = t.getImage(sharkStartFrame.publicPathplus + "sprites" +
                                       sharkStartFrame.separator +
                                       "refresh.png");
        refreshicon = new ImageIcon(tempim.getScaledInstance( (int)imageButDim.getWidth(),
            (int) imageButDim.getHeight(), Image.SCALE_SMOOTH));


        tempim = t.getImage(sharkStartFrame.publicPathplus + "sprites" +
                                       sharkStartFrame.separator +
                                       "vowelsOFF_il48.png");
        vowelsofficon = new ImageIcon(tempim.getScaledInstance( (int)imageButDim.getWidth(),
            (int) imageButDim.getHeight(), Image.SCALE_SMOOTH));
        tempim = Toolkit.getDefaultToolkit().getImage(sharkStartFrame.publicPathplus + "sprites" +
                                       sharkStartFrame.separator +
                                       "vowelsON_il48.png");
        vowelsonicon = new ImageIcon(tempim.getScaledInstance( (int)imageButDim.getWidth(),
            (int) imageButDim.getHeight(), Image.SCALE_SMOOTH));

        tempim = t.getImage(sharkStartFrame.publicPathplus + "sprites" +
                                       sharkStartFrame.separator +
                                       "deleteON_il48.png");
        warn = new ImageIcon(tempim.getScaledInstance( (int)imageButDim.getWidth(),
            (int) imageButDim.getHeight(), Image.SCALE_SMOOTH));
        
        
        if(!shark.production){
            tempim = t.getImage(sharkStartFrame.publicPathplus + "sprites" +
                                           sharkStartFrame.separator +
                                           "splitDevModeOFF_il48.png");
            devsplitsofficon = new ImageIcon(tempim.getScaledInstance( (int)imageButDim.getWidth(),
                (int) imageButDim.getHeight(), Image.SCALE_SMOOTH));
            tempim = t.getImage(sharkStartFrame.publicPathplus + "sprites" +
                                           sharkStartFrame.separator +
                                           "splitDevModeON_il48.png");
            devsplitsonicon = new ImageIcon(tempim.getScaledInstance( (int)imageButDim.getWidth(),
                (int) imageButDim.getHeight(), Image.SCALE_SMOOTH));

        }


        butRemoveSplits = u.sharkButton();
        butRemoveSplits.setToolTipText(u.gettext("wl_splitremove", "tooltip"));
        butRemoveSplits.setIcon(warn);
        int borderd = border*2;
        butRemoveSplits.setPreferredSize(new Dimension((int)imageButDim.getWidth()+borderd,(int)imageButDim.getHeight()+borderd));
        butSaveSplits.setPreferredSize(new Dimension((int)imageButDim.getWidth()+borderd,(int)imageButDim.getHeight()+borderd));
        butSaveSplits.setIcon(saveicon);        
        if(!shark.production){
            butDevSplits = u.sharkButton();
            butDevSplits.setToolTipText(u.gettext("wl_devsplits", "tooltip"));
            butDevSplits.setIcon(devsplitsofficon);        
            butDevSplits.setPreferredSize(new Dimension((int)imageButDim.getWidth()+borderd,(int)imageButDim.getHeight()+borderd));
        }
        butHV = u.sharkButton();
        butHV.setToolTipText(u.gettext("wl_vowels", "tooltip"));
        butHV.setIcon(vowelsofficon);
        butInOrder = u.sharkButton();
        butInOrder.setToolTipText(u.gettext("wl_inorder", "tooltip"));
        butInOrder.setIcon(inorderofficon);

        butInOrder.setPreferredSize(new Dimension((int)imageButDim.getWidth()+borderd,(int)imageButDim.getHeight()+borderd));
        butHV.setPreferredSize(new Dimension((int)imageButDim.getWidth()+borderd,(int)imageButDim.getHeight()+borderd));
        butInOrder.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               JButton jb = (JButton)e.getSource();
               boolean on = false;
               if(jb.getIcon().equals(inorderonicon)){
                   jb.setIcon(inorderofficon);
               }
               else{
                   jb.setIcon(inorderonicon);
                   on = true;
               }
               sharkStartFrame.currPlayTopic.inorder = on;
          }
       });

        butRefresh = u.sharkButton();
//        butRefresh.setToolTipText(u.gettext("wl_inorder", "tooltip"));
        butRefresh.setIcon(refreshicon);
        butRefresh.setPreferredSize(new Dimension((int)imageButDim.getWidth()+borderd,(int)imageButDim.getHeight()+borderd));
        butRefresh.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
                  newselection(1);
          }
       });

        butHV.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
            JButton jb = (JButton)e.getSource();
            boolean on = false;
            if(jb.getIcon().equals(vowelsonicon)){
                jb.setIcon(vowelsofficon);
            }
            else{
                jb.setIcon(vowelsonicon);
                on = true;
            }
            if(current.vowels != on) {
                current.vowels = on;
            }
            current.repaint();
          }
       });

        butSaveSplits.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
            sharkStartFrame.currPlayTopic.savesplitwords( sharkStartFrame.optionsdb,  sharkStartFrame.currPlayTopic.splitwords);
            splitchanged = false;
            butSaveSplits.setVisible(false);
  //          sharkStartFrame.mainFrame.splitwarning.setVisible(true);
          }
       });

        butRemoveSplits.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
            if(u.yesnomess("qremovesplits")) {
               sharkStartFrame.currPlayTopic.splitwords = null;
               splittot = 0;
               sharkStartFrame.currPlayTopic.removesplitwords(sharkStartFrame.optionsdb);
               splitchanged = false;
               unsplit = true;
               butRemoveSplits.setVisible(false);
               butSaveSplits.setVisible(false);
               splitCB.setText(splitlab1);
 //              sharkStartFrame.mainFrame.splitwarning.setVisible(true);
               redrawing=true;
//               sharkStartFrame.mainFrame.sametopic = true;
               sharkStartFrame.mainFrame.setupGametree(true);
               sharkStartFrame.mainFrame.addsplitwarning();
               font=null;
               redrawing=false;
               repaint();
            }
          }
       });
        
        if(!shark.production){
            butDevSplits.addActionListener(new ActionListener() {
               public void actionPerformed(ActionEvent e) {
                    JButton jb = (JButton)e.getSource();
                    boolean on = false;
                    if(jb.getIcon().equals(devsplitsonicon)){
                        jb.setIcon(devsplitsofficon);
                    }
                    else{
                        jb.setIcon(devsplitsonicon);
                        on = true;
                    }
                    splitsInDevMode = on;
                    
                    
                    sharkStartFrame.currPlayTopic.getSplits();
                    sharkStartFrame.mainFrame.setupGametree(false, true);
                    
         //           addsplits();
         //           repaint();
     //               butSaveSplits.setVisible(false);
                    splitsPan.setBackground(on?Color.red:sharkStartFrame.defaultbg);
                    splitCB.setForeground(on?Color.white:Color.black);
              }
           });
        }



        def = u.CheckBox("usedefinitions");
        translations = u.CheckBox("usetranslations");
        def.setOpaque(false);
        def.setForeground(Color.white);

        translations.setOpaque(false);
        translations.setForeground(Color.white);

        def.setVisible(false);
        translations.setVisible(false);


        def.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            usedefinitions = def.isSelected();
             DefaultListModel model = (DefaultListModel)getModel();
             for(int i = 0; i <model.getSize(); i++){
                 final Object o =  model.getElementAt(i);
                 if(o instanceof word) {
                    ( (word)o).spokenword = null;
                 }
             }
             redrawing=true;
 //            sharkStartFrame.mainFrame.sametopic = true;
             sharkStartFrame.mainFrame.setupGametree(true, true);
             redrawing=false;
             repaint();
          }
        });
        translations.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            usetranslations = translations.isSelected();
             DefaultListModel model = (DefaultListModel)getModel();
             for(int i = 0; i <model.getSize(); i++){
                 final Object o =  model.getElementAt(i);
                 if(o instanceof word) {
                    ( (word)o).spokenword = null;
                 }
             }
             redrawing=true;
//             sharkStartFrame.mainFrame.sametopic = true;
             sharkStartFrame.mainFrame.setupGametree(true, true);
             redrawing=false;
             repaint();
          }
        });

         splitCB = u.CheckBox("wl_split");
         splitCB.setOpaque(false);
         splitCB.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
             usephonics = false;
             phonemes.setSelected(false);
             student.clearOption("s_usephonics");
             phonemes.setSelected(false);
             splitsUpper.setVisible(splitCB.isSelected());
             if((current.split = splitCB.isSelected())
                && (sharkStartFrame.studentList[sharkStartFrame.currStudent].administrator
                     || !sharkStartFrame.currPlayTopic.alreadysplit)) {
                current.splitting = true;
             }
             else {
                current.splitting = false;
             }
              redrawing=true;
//              sharkStartFrame.mainFrame.sametopic = true;
//startPR2008-08-17^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//             sharkStartFrame.mainFrame.setupGametree(true);
              sharkStartFrame.mainFrame.setupGametree(true, true);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
              sharkStartFrame.mainFrame.addsplitwarning();
              font=null;
              redrawing=false;
              repaint();
           }
        });
//startPR2007-11-19^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//        savesplits.setBackground(Color.red);
//        savesplits.setForeground(Color.white);
 //       if(!shark.macOS)
 //         savesplits.setForeground(Color.white);
  //      savesplits.setBackground(Color.red);
  //      savesplits.setOpaque(true);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
 //       savesplits.addActionListener(new ActionListener() {
 //        public void actionPerformed(ActionEvent e) {
 //           sharkStartFrame.currPlayTopic.savesplitwords( sharkStartFrame.optionsdb,  sharkStartFrame.currPlayTopic.splitwords);
 //           splitchanged = false;
 //           savesplits.setVisible(false);
//            sharkStartFrame.mainFrame.splitwarning.setVisible(true);
//          }
 //       });
 //       removesplits.setOpaque(true);
//startPR2007-11-19^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//        removesplits.setBackground(Color.red);
//        removesplits.setForeground(Color.white);
 //         if(!shark.macOS)
 //           removesplits.setForeground(Color.white);
  //        removesplits.setBackground(Color.red);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  //      removesplits.addActionListener(new ActionListener() {
  //       public void actionPerformed(ActionEvent e) {
  //          if(u.yesnomess("qremovesplits")) {
  //             sharkStartFrame.currPlayTopic.splitwords = null;
  //             splittot = 0;
  //             sharkStartFrame.currPlayTopic.removesplitwords(sharkStartFrame.optionsdb);
  //             splitchanged = false;
  //             unsplit = true;
  //             removesplits.setVisible(false);
  //             savesplits.setVisible(false);
  //             splitCB.setText(splitlab1);
 //              sharkStartFrame.mainFrame.splitwarning.setVisible(true);
  //             redrawing=true;
//               sharkStartFrame.mainFrame.sametopic = true;
  //             sharkStartFrame.mainFrame.setupGametree(true);
  //             sharkStartFrame.mainFrame.addsplitwarning();
  ////             font=null;
  //             redrawing=false;
  //             repaint();
  //          }
  //        }
  //      });


          GridBagConstraints grid2 = new GridBagConstraints();
         grid2.fill = GridBagConstraints.NONE;
        grid2.gridx = -1;
        grid2.gridy = 0;
        splitsUpper.add(butSaveSplits, grid2);
        grid2.insets = new Insets(0,border,0,0);
        splitsUpper.add(butRemoveSplits, grid2);
        splitsUpper.setOpaque(false);
        
        grid2.insets = new Insets(0,0,0,0);
        splitsLower.add(splitCB, grid2);
        
        if(!shark.production){
            grid2.insets = new Insets(0,border,0,0);
            splitsLower.add(butDevSplits, grid2);
            grid2.insets = new Insets(0,0,0,0);
        }
        
    
        
     //   butSaveSplits.setVisible(false);
        splitsUpper.setVisible(false);

        grid2.gridx = 0;
        grid2.gridy = -1;
        grid2.anchor = GridBagConstraints.NORTHEAST;
        grid2.insets = new Insets(border,border,0,border);
        splitsPan.add(splitsUpper, grid2);
        grid2.anchor = GridBagConstraints.CENTER;
        grid2.insets = new Insets(border,border,border,border);
        splitsPan.add(splitsLower, grid2);
        grid2.gridx = -1;
        grid2.gridy = 0;



 //       phonemes = new JCheckBox("Use phonics");
        phonemes = u.CheckBox("wl_phonemes");
        phonemes.setOpaque(false);
        phonemes.setForeground(Color.white);
        
        phonemes.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
              if(sharkStartFrame.playprogram!=null){
                  boolean phon = sharkStartFrame.playprogram.it[sharkStartFrame.playprogram.currstep].phonics;
                  sharkStartFrame.studentList[sharkStartFrame.currStudent].overriddenProgPhonics = phonemes.isSelected() != phon;
              }

               if(usephonics = phonemes.isSelected()) {
                 student.setOption("s_usephonics");
               }
               else {
                 student.clearOption("s_usephonics");
               }
               current.split = false;
               current.splitting = false;
               splitCB.setSelected(false);
               redrawing=true;
//               sharkStartFrame.mainFrame.sametopic = true;
//startPR2007-11-26^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//               sharkStartFrame.mainFrame.setupGametree();
               sharkStartFrame.mainFrame.setupGametree(true, true);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
               redrawing=false;
               repaint();
          }
        });
        splitlab1 = splitCB.getText();
        splitlab2 = u.gettext("wl_split2","label");
        splitlab3 = u.gettext("wl_split3","label");
        splittool1 = splitCB.getToolTipText();
        splittool2 = u.gettext("wl_split2","tooltip");
        splittool3 = u.gettext("wl_split3","tooltip");
//        inorder = u.CheckBox("wl_inorder");
//        inorder.addActionListener(new ActionListener() {
//          public void actionPerformed(ActionEvent e) {
//            sharkStartFrame.currPlayTopic.inorder = inorder.isSelected();
//          }
//        });
        standard =  u.RadioButton("wl_standard",orderaction);
        extended =  u.RadioButton("wl_extended",orderaction);
        extendedwhole =  u.RadioButton("wl_extendedwhole",orderaction);
//        addMouseMotionListener(mousemotion);
//startPR2007-11-26^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//        current=this;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      }
      else {
         temp = true;
         setModel(new DefaultListModel());
         setCellRenderer(new itempainter());
         setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      }
      removeMouseMotionListener(mousemotion);
      addMouseMotionListener(mousemotion);
//startPR2007-11-26^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR

      // 'current' was never being changed from the sharkStartFrame wordlist.
      // This was causing problems for the wordlists eg in the search screen.

      current=this;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      ordersel = 0;
      addMouseListener(new java.awt.event.MouseAdapter() {
             public void mouseReleased(MouseEvent e) {
                 rightclick = (e.getModifiers() == e.BUTTON3_MASK);
                 mouseclick(locationToIndex(e.getPoint()));
             }
             public void mouseExited(MouseEvent e) {
                  if(splitting) {
                     cleartempsplit();
                  }
//startPR2008-06-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//     if(currpic != null) currpic.stop();
//     currpic = null;
                  removeCurrPic();
//endPRimagesandvids^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
             }
      });  
//startPR2013-02-20^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//         setBackground(wlbgcol);
         setBackground(bgcoloruse);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         setOpaque(true);    
   }

  public void setHighlightVowel(boolean on) {
     if(!on){
        butHV.setIcon(vowelsofficon);
     }
     else{
        butHV.setIcon(vowelsonicon);
     }
     current.vowels = on;
     current.repaint();
  }

  public void setInOrder(boolean on) {
     if(!on){
        butInOrder.setIcon(inorderofficon);
     }
     else{
        butInOrder.setIcon(inorderonicon);
     }
     sharkStartFrame.currPlayTopic.inorder = on;
  }
   /**
    * <li>Sets flags indicating that splitting has occurred.
    * <li>Repaints the component.
    */
  void showsavesplits() {
    boolean old = butSaveSplits.isVisible();
    butSaveSplits.setVisible(splitchanged
            && splitCB.isSelected()
                          && sharkStartFrame.currStudent >= 0
                          && (sharkStartFrame.studentList[sharkStartFrame.currStudent].administrator
                              && !sharkStartFrame.studentList[sharkStartFrame.currStudent].teacher)
                          && !sharkStartFrame.mainFrame.wassuperlist);
 //   sharkStartFrame.mainFrame.splitwarning.setVisible(false);
    boolean old2 = butRemoveSplits.isVisible();
    butRemoveSplits.setVisible(split &&   sharkStartFrame.currPlayTopic.splitwords != null
                                   &&  sharkStartFrame.currPlayTopic.splitwords.length > 0
                          && sharkStartFrame.currStudent >= 0
                          && (sharkStartFrame.studentList[sharkStartFrame.currStudent].administrator
                              && !sharkStartFrame.studentList[sharkStartFrame.currStudent].teacher)
                          && !sharkStartFrame.mainFrame.wassuperlist
                          && !usephonics);
 //   sharkStartFrame.mainFrame.splitwarning.setVisible(false);
    splitsUpper.setVisible(butSaveSplits.isVisible() || butRemoveSplits.isVisible());
//    if(!splitsUpper.isVisible()){
//        splitCB.setSelected(false);
//        splitCB.setText("Add splits");
//    }
    if(old != butSaveSplits.isVisible() || old2 != butRemoveSplits.isVisible())  {
      sharkStartFrame.mainFrame.bevelPanel2.validate();
      setfont();
      repaint();
    }
   }
   void setsplit() {
     if(!split) {
           split = splitting = true;
           splitCB.setSelected(true);
           repaint();
      }
   }
   /**
    * Removes any changes associated with a wordlist
    */
   public void reset() {
     splitchanged = shuffled = splitting = split = highlight = vowels = wholeextended
            = keepGroupings = nostandard = false;
     ordersel = 0;
   }
   //--------------------------------------------------------
   public void setup(topic tt, String g[]) {
     setup(tt,g,false, false);
   }

   public void setup(topic tt, String g[],boolean finding){
       setup(tt,g,false, false);
   }
   //--------------------------------------------------------
   public void setup(topic tt, String g[],boolean finding, boolean samelist) {
       // needed otherwise tt.notphonics doesn't give correct result.
       tt.getWords(null,false);  // complete initialization
       if(tt.notphonics){
          wordlist.usephonics = false;
          tt.phonicsw = tt.phonics = false;       
       }
      extrainf = null;
      canextend = tt.canextend();
      name = tt.name;
      forcephonics = tt.justphonics;
      if(!redrawing && !samelist) {
         areGroupings = areSingles = false;
         if(!temp) setHighlightVowel(false);//    vowelsCB.setSelected(false);
         if(!temp) splitCB.setSelected(false);
         if(!tt.superlist) {
           words = tt.getWords(null, canextend);
           phonicsw = tt.phonicsw;
           for (short i = 0; i < words.length; ++i) {
             if (!canextend && words[i].companions == 0)
               areSingles = true;
             if (words[i].companions > 0 && (!areSingles || !words[i].paired)) {
               areGroupings = true;
               break;
             }
           }
         }
         else {
           phonicsw = tt.phonicsw;
           areGroupings = false;
           areSingles = true;
         }
      }
      if(words == null || !samelist){
          words = tt.getWords(g,canextend && (shuffled || wholeextended));
          words = u.stripdups(words);                              // rb 24/1/07
      }
      singlesound = tt.singlesound;
      if(canextend && words.length==0) {
        extended.setSelected(true);
        shuffled = true;
        words = tt.getWords(null,true);
        nostandard = true;
      }
      if(tt.revision)nostandard = true;
//startPR2007-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      if(!temp && (changedfont || sharkStartFrame.wordfont.getName().equalsIgnoreCase("wordshark"))) {
//         if(!tt.okforwordfont()) {
//           if (!changedfont) {
//             sharkStartFrame.mainFrame.wordfont = new Font("SansSerif",Font.PLAIN,
//                                                       sharkStartFrame.BASICFONTPOINTS);
//             changedfont = true;
//           }
//         }
//         else if(changedfont) {
      if(!temp && (changedfont || u.isdefaultfont(sharkStartFrame.wordfont.getName()))) {
        if(changedfont) {
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          sharkStartFrame.mainFrame.setwordfont();
          changedfont=false;
        }
      }
      wordtot = words.length;
      changing = true;
      arePairs = areCompanions = areSingles = false;
 //     boolean arenonpairs = false;
      for(short i=0;i<words.length;++i) {
         if(words[i].companions>0) {
//            if(!words[i].paired) arenonpairs = true;
//            else arePairs = true;
            if(words[i].paired) arePairs = true;
         }
         else if(words[i].companions<=0)   areSingles = true;
         if(words[i].companions > 0)
                          areCompanions = true;
      }
      if(!redrawing && !temp) {
         orderCB = new orderPanel();
      }
      order = u.select((short)wordtot,(short)wordtot);
      buildTree(finding);
      if(words.length!=wordtot){
          wordtot =  words.length;
          order = u.select((short)wordtot,(short)wordtot);
      }
      if(!finding) {
         addsplits();
         if(!temp) sharkStartFrame.mainFrame.scroller.setVerticalScrollBarPolicy(wholeextended?JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED:JScrollPane.VERTICAL_SCROLLBAR_NEVER);
      }
      ensureIndexIsVisible(0);
      changing=false;
    }
   //----------------------------------------------------
   
   public void setTopicName(String nam) {
       name = nam;
   }
   
   public boolean setfont() {
     Dimension d = temp?this.getParent().getSize():sharkStartFrame.mainFrame.scroller.getSize();
     if(d.height <= 0) return false;
     int ht,h;
     int w = d.width;
     DefaultListModel model = (DefaultListModel)getModel();
     int tot = model.getSize();
     int ct = wholeextended?Math.min(12,tot):tot;
     if(ct==0) ct = 8;
     setFixedCellHeight(ht = d.height/ct);
     h = ht*7/8;
     FontMetrics m;
     int points = sharkStartFrame.MAXFONTPOINTS_2;
//startPR2007-11-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//     String name = sharkStartFrame.wordfont.getName();
//     int style = sharkStartFrame.wordfont.getStyle();
//     font = new Font(name,style,points);
     font = sharkStartFrame.wordfont.deriveFont((float)points);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     m = getFontMetrics(font);
     while(m.getHeight() > h) {
//startPR2007-11-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//       font = new Font(name,style,--points);
       font = sharkStartFrame.wordfont.deriveFont((float)--points);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        m = getFontMetrics(font);
     }
     int usewidth = w-MARGIN*2;
     if(wholeextended && model.getSize() > 12) {
        usewidth -= sharkStartFrame.mainFrame.scroller.getVerticalScrollBar().getPreferredSize().width;
     }
     for(short i=0; i < model.getSize(); ++i) {
        while(m.stringWidth(((word)model.get(i)).vsplit()) > usewidth) {
//startPR2007-11-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//       font = new Font(name,style,--points);
          font = sharkStartFrame.wordfont.deriveFont((float)--points);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          m = getFontMetrics(font);
        }
     }
     setFont(font);
     return true;
    }
   //-----------------------------------------------------
   void buildTree(boolean finding) {
      short i,j;
 //     DefaultListModel model8 = ((DefaultListModel)this.getModel());
      clearSelection();
      if(wordtot==0) wordtot = words.length;
      ((DefaultListModel)this.getModel()).removeAllElements();
      if(wholeextended) {
//         word words1[] = sharkStartFrame.currPlayTopic.getAllWords(true);
//         for(i=0;i<words1.length;++i) {
//             model.addElement(words1[i]);
//         }
         words = sharkStartFrame.currPlayTopic.getAllWords(true);
         originalwords = new word[0];
         for(i=0;i<words.length;++i) {
             boolean phonicsprogram = sharkStartFrame.playprogram!=null && sharkStartFrame.playprogram.it[sharkStartFrame.playprogram.currstep].phonics;
             if(sharkStartFrame.currPlayTopic.phonicsw
                && (((student.option("s_usephonics")&& (!(parent instanceof findword))) ||phonicsprogram) || sharkStartFrame.currPlayTopic.justphonics)
                && words[i].value.indexOf('=')<0) continue;
             ((DefaultListModel)this.getModel()).addElement(words[i]);
             originalwords = u.addWords(originalwords, new word(words[i]));
         }
      }
      else {
         order = sortwords(false);
         originalwords = new word[0];
         for(i=0;i<wordtot;++i) {
            j  = order[i];
            boolean phonicsprogram = sharkStartFrame.playprogram!=null && sharkStartFrame.playprogram.it[sharkStartFrame.playprogram.currstep].phonics;
            if(!finding && sharkStartFrame.currPlayTopic.phonicsw
               && (((student.option("s_usephonics")&& (!(parent instanceof findword)))||phonicsprogram) || sharkStartFrame.currPlayTopic.justphonics)
               && words[j].value.indexOf('=')<0) continue;
            if(!words[j].bad && words[j].value.indexOf('*') < 0
                 && (!areSingles || !words[j].paired)) {
                ((DefaultListModel)this.getModel()).addElement(words[j]);
                originalwords=u.addWords(originalwords, new word(words[j]));
           }
         }
      }

   }
   //-------------------------------------------------------------
   public word[] getvisiblewords() {
      int si = model.getSize();
      word ww[] = new word[si];
//startPR2008-08-29^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      for(short i=0;i<si;++i) ww[i] = (word)model.getElementAt(i);
      for(short i=0;i<si;++i){
        ww[i] = new word((word)model.getElementAt(i));
        if(!splitCB.isSelected())
          ww[i].value = ww[i].value.replaceAll("/","");
      }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      return ww;
   }
   //------------------------------------------------------------
   short[] sortwords(boolean forceshuffle) {
      short len = (short)wordtot;
      short order1[] = new short[len];
      System.arraycopy(order,0,order1,0,len);
      if(!shuffled && !forceshuffle)  java.util.Arrays.sort(order1);
      else if(!keepGroupings)  u.shuffle(order1);
      else {
         short i,j,k;
         short groups[], groupnum=0;
         if(areCompanions) {
            groups = new short[len];
            for(i=0;i<wordtot;++i) {
                // groupnum was getting one too big if a companion was on the last in the list
//                groups[groupnum++] = i;
//                if(words[i].companions > 0)  {
//                   i += words[i].companions;
//                }
                groups[groupnum] = i;
                if(words[i].companions <=0 || i+words[i].companions<wordtot)
                    groupnum++;
                if(words[i].companions > 0)  {
                   i += words[i].companions;
                }
            }
         }
         else {
            groups = u.select((short)wordtot,(short)wordtot);
            groupnum = (short)wordtot;
         }
         u.shuffle(groups,(short)0,groupnum);
         for(i=j=0;i<groupnum;++i) {
            short savej = j;
            order1[j++] = groups[i];
            if(words[groups[i]].companions > 0)  {
               for(k=0;k<words[groups[i]].companions;++k)
                       order1[j++]=(short)(groups[i]+k+1);
               if(!words[groups[i]].paired)
                      u.shuffle(order1,savej,j);
            }
         }
      }
      return order1;
   }
   //--------------------------------------------------------------
//startPR2004-10-06^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    void printlist(String name, byte type) {
     lastPage = -1;
     printtype = type;
     PrinterJob pJob = PrinterJob.getPrinterJob();
     pJob.setJobName(u.gettext("printing","wordlist",name));
     PageFormat pageFormat = pJob.defaultPage();
     if(printtype == FLASH || printtype == PRINT2 || printtype==PRINT8)
       pageFormat.setOrientation(pageFormat.LANDSCAPE);
     else pageFormat.setOrientation(pageFormat.PORTRAIT);
     pJob.setPrintable(this, pageFormat);
     if (pJob.printDialog()) {
       try {
         pJob.print();
       }
       catch (Throwable t) {}
     }
   }

   public int print(Graphics g, PageFormat f, int a) throws PrinterException {
     boolean isphonics = phonicsw && usephonics;
     int wordNo =  printWords==null?model.getSize():printWords.length;
     int across=1, down=1, pages=1,aa,dd;
//startPR2007-02-16^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      int wordsperpage=-1;
      boolean overMultPages = false;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     switch (printtype) {
       case FLASH:
         pages = wordNo;
         break;
       case FLASH2:
         across=2;
         down = (wordNo+1)/2;
         break;
       case PRINT1:
         break;
       case PRINT2:
         across=2;
         break;
       case PRINT4:
         across=2;
         down=2;
         break;
       case PRINT8:
         across=4;
         down=2;
         break;
//startPR2007-02-16^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       case PRINTEX:
         wordsperpage = 10;
         int i = wordNo;
         if(i<=wordsperpage){
           pages = 1; break;
         }
         overMultPages = true;
         pages = i/wordsperpage;
         if(wordNo%wordsperpage>0)pages++;
         break;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     }

     // if reached end of print
     if (a >= pages) {
       return NO_SUCH_PAGE;
     }

     // allows skipping of the buffer's graphics context
//     if (a != lastPage) {
     if (lastPage == -1) {
       lastPage = a;
       return Printable.PAGE_EXISTS;
     }

     g.translate( (int) f.getImageableX(), (int) f.getImageableY());
     Dimension di = new Dimension( (int) f.getImageableWidth(),
                                  (int) f.getImageableHeight());
     int gapx=0,gapy=0;
     int w = di.width / across;
     int h = di.height/down;
     int wf=w,hf=h;
     if(across>1 || down >1) {
        g.setColor(Color.black);
        g.drawRect(0,0,w*across,h*down);
        for(int i=1;i<across;++i) {
           g.drawLine(w*i,0,w*i,h*down);
        }
        for(int i=1;i<down;++i) {
           g.drawLine(0,h*i,w*across,h*i);
        }
     }
//startPR2007-02-16^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      int tot = model.getSize();
      int tot;
      if(!overMultPages)tot = wordNo;
      else{
        int k;
        if(a==pages-1 && (k=wordNo%wordsperpage)!=0)tot = k;
        else tot = wordsperpage;
      }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     if(across>1 || down>1) {wf = w*15/16; gapx = (w-wf)/2; hf = h*15/16; gapy = (h-hf)/2;}
     if(printtype==FLASH) {
         g.setColor(student.printfg());
       if(isphonics) {
         String s = printWords==null?((word)model.get(a)).phsplit():printWords[a];
         Font f2 = sharkGame.sizeFontbig(new String[] {s}, wf, hf*3/4);
         FontMetrics m2 = g.getFontMetrics(f2);
         g.setFont(f2);
         ( (word) model.get(a)).paint(g,new Rectangle(w/2-m2.stringWidth(s)/2,0,m2.stringWidth(s),h),word.PHONICSPLIT+word.CENTRE);
       }
       else {
         String s = printWords==null?(split ?
             ( ( (word) model.get(a)).vsplit()) : ( ( (word) model.get(a)).v())):printWords[a];
         Font f2 = sharkGame.sizeFontbig(new String[] {s}, wf, hf);
         FontMetrics m2 = g.getFontMetrics(f2);
         g.setFont(f2);
         g.drawString(s, w / 2 - m2.stringWidth(s) / 2, h / 2 - m2.getHeight() / 2 + m2.getAscent());
       }
       return Printable.PAGE_EXISTS;
     }
     String items[] = new String[tot];
     short i, j, k;
//startPR2007-02-16^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//     for (i = 0; i < tot; ++i)
//       items[i] = split ?
//           ( ( (word) model.get(i)).vsplit()) : ( ( (word) model.get(i)).v());
      for (i = 0; i < tot; ++i){
        if(!overMultPages)
          items[i] = printWords==null?(isphonics? ((word)model.get(i)).phsplit()
              : (  split ?
              (((word)model.get(i)).vsplit()) : ( ( (word) model.get(i)).v()))):printWords[i];
        else
          items[i] = printWords==null?(isphonics? ((word)model.get(i+ (a*wordsperpage))).phsplit()
              : ( split ?
              (((word)model.get(i + (a*wordsperpage))).vsplit()) :
              (((word)model.get(i + (a*wordsperpage))).v()) )):printWords[i];
      }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     if(printtype==FLASH2) {
         g.setColor(student.printfg());
       if(isphonics) {
         Font f2 = sharkGame.sizeFontbig(items, wf, hf / 2*3/4);
         FontMetrics m2 = g.getFontMetrics(f2);
         g.setFont(f2);
         for (i = 0; i < tot; ++i) {
           word ww = (word) model.get(!overMultPages ? i : i + (a * wordsperpage));
           ww.paint(g,new Rectangle((i / down) * w + w / 2 - m2.stringWidth(items[i]) / 2,
                        (i % down) * h, m2.stringWidth(items[i]), h),word.PHONICSPLIT+word.CENTRE);
         }
       }
       else {
         Font f2 = sharkGame.sizeFontbig(items, wf, hf / 2);
         FontMetrics m2 = g.getFontMetrics(f2);
         g.setFont(f2);
         for (i = 0; i < tot; ++i)
           g.drawString(items[i], (i / down) * w + w / 2 - m2.stringWidth(items[i]) / 2,
                        (i % down) * h + h / 2 - m2.getHeight() / 2 + m2.getAscent());
       }
       return Printable.PAGE_EXISTS;
     }
     String h1 = spellchange.spellchange(name);
//     String h2 = u.gettext("printing", printtype>=PRINT4 ? "footer2":"footer");

     Font f1 = sharkGame.sizeFont(new String[] {h1}
                                  , wf, hf/12);
     FontMetrics m1 = g.getFontMetrics(f1);
//     Font f4 = sharkGame.sizeFont(new String[] {h2}
//                                  , w, m1.getHeight() * 7 / 8);
//     FontMetrics m4 = g.getFontMetrics(f4);
     int coltot = tot / 16 + 1;
     int percol = (tot+coltot-1) / coltot;
//startPR2007-02-16^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    Font f2 = sharkGame.sizeFont( items, wf / coltot,
//                                 (hf - m1.getHeight() * 3/2 ) *
//                                 tot / percol);
//startPR2007-11-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      Font fextended = new Font(sharkStartFrame.wordfont.getName(), sharkStartFrame.wordfont.getStyle(),12);
     Font fextended = sharkStartFrame.wordfont.deriveFont((float)12);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      FontMetrics m3 = g.getFontMetrics(fextended);
      String ss = u.edit(u.gettext("printing", "multpagetitle"),
                                String.valueOf(a + 1), String.valueOf(pages));
      int p = m3.getHeight() * 3/2;
      Font f2;
      int yyinc = (hf-(overMultPages?p:0) - m1.getHeight() * 3/2 )/ percol;
      if(isphonics) {
        f2 = sharkGame.sizeFont(items, wf / coltot, yyinc * tot  * 2/3);
      }
      else  f2 = sharkGame.sizeFont( items, wf / coltot,yyinc * tot );
      if(overMultPages){
        if(f3==null)f3 = f2;
        else f2 = f3;
      }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     FontMetrics m2 = g.getFontMetrics(f2);
     int wx = wf/coltot/2;
     for(i=0;i<tot;++i) {
       wx = Math.max(0,Math.min(wx,wf/coltot/2 - m2.stringWidth(items[i])/2));
     }

     for(dd=0;dd<across;++dd) {
        for(aa=0;aa<across;++aa) {
          int xx = w*aa + gapx;
          int yy = h*dd + gapy;
          int x = 0, y = yy, x1, y1, x2, y2, fsiz;
          y += m1.getMaxAscent();
          y = yy;
//startPR2007-02-16^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          g.setColor(student.printfg());
          if(overMultPages){
            g.setFont(fextended);
            int len2  = m3.stringWidth(ss);
            g.drawString(ss, wf - len2, y + m3.getAscent());
            y += p;
          }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          g.setFont(f1);
//startPR2007-02-16^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//          g.setColor(student.printfg());
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          int len  = m1.stringWidth(h1);
          g.drawString(h1, xx + (w - len) / 2, y + m1.getAscent());
          g.fillRect(xx + (w - len) / 2, y+m1.getHeight(),len,2);
          y += m1.getHeight() * 3/2;

          g.setFont(f2);
          int inc = m2.getHeight();
          if(isphonics) inc*=4/3;
          int yinc = m2.getMaxAscent();
          for (i = 0; i < tot; ++i) {
            if(isphonics) {
              word ww = (word) model.get(!overMultPages ? i : i + (a * wordsperpage));
              ww.paint(g,new Rectangle( xx + wx + (i / percol) * (wf/coltot),
                           y + (i % percol) * yyinc, m2.stringWidth(items[i]), yyinc),word.PHONICSPLIT+word.CENTRE);
            }
            else g.drawString(items[i], xx + wx + (i / percol) * (wf/coltot),
                         y + yinc + (i % percol) * inc);
          }
//          g.setFont(f4);
//          g.drawString(h2, xx +w/2-m4.stringWidth(h2)/2, yy + h - m4.getHeight()+m4.getAscent());
        }
     }
     return Printable.PAGE_EXISTS;
   }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      //-----------------------------------------------------------
   void buildtempsplit(int sel,int mousex) {
     boolean needpaint = false;
     if(sel< 0) {return;}
     word w;
     DefaultListModel model = (DefaultListModel)getModel();
     for(int i=0;i<model.getSize();++i) {
        Object o =  model.getElementAt(i);
        if(o instanceof word) {
            w = (word)o;
            if(sel != i) {
                if( w.tempsplit != null) {
                   w.tempsplit = null;
                   needpaint = true;
                }
            }
            else {
                if(font != null) needpaint = w.buildsplit(MARGIN, mousex, getFontMetrics(font));
            }
        }
     }
     if(needpaint) repaint();
   }
      //-----------------------------------------------------------
   void cleartempsplit() {
     boolean needpaint = false;
     word w;
     DefaultListModel model = (DefaultListModel)getModel();
     for(int i=0;i<model.getSize();++i) {
        Object o =  model.getElementAt(i);
        if(o instanceof word) {
            w = (word)o;
            if( w.tempsplit != null) {
                   w.tempsplit = null;
                   needpaint = true;
            }
        }
     }
     if(needpaint) repaint();
   }
      //-----------------------------------------------------------
   void mouseclick(final int sel) {
     if(sel< 0) {return;}
     DefaultListModel model = (DefaultListModel)getModel();
     final Object o =  model.getElementAt(sel);
     if(o instanceof word) {
       selword = (word) o;
       int i,j;
       if (splitting && selword.tempsplit != null) {
         selword.value = selword.tempsplit;
         if ( (i = selword.value.indexOf("//")) >= 0 && ((j = selword.value.indexOf('=')) < 0 || i<j))
           selword.value = selword.value.substring(0, i) +
               selword.value.substring(i + 2);
         selword.tempsplit = null;
         changewords(selword.value);
         setsplitflags();
         sharkStartFrame.mainFrame.addsplitwarning();
         setfont();
         repaint();
         return;
       }
       if (spokenWord.isfree() && (lastword == null || lastword.curr2 < 0 )) {
         new spokenWord.whenfree(0) {
           public void action() {
//startPR2008-06-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//             if (sharkStartFrame.gametot > 0 || !isVisible())
             if (!saywords||(sharkStartFrame.gametot > 0 || !isVisible()))
//endPRimagesandvids^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
               return;
             try {
               Point r = indexToLocation(sel);
               Point pscroll = indexToLocation(getFirstVisibleIndex());
               Point pos = getLocationOnScreen();
               lastword = (word) o;
               if (!usephonics ||  !spokenWord.sayPhonicsWord( (word) o, 500, true, true,!singlesound,current)) {
//                 if(rightclick) ( (word) o).defsay(); else ( (word) o).say();
                  if((sharkStartFrame.currPlayTopic.definitions && wordlist.usedefinitions) ||
           (sharkStartFrame.currPlayTopic.translations &&  wordlist.usetranslations)){
     //                 ( (word) o).spokenword = null;
                      ( (word) o).sayandextra();
                  }
                  else{
                      ( (word) o).say();
                  }
                 if (spokenWord.extrainf != null) {
                   extrainf = spokenWord.extrainf;
                   extrainfo = o;
                   repaint(); // add extrainf under word
                 }
               }
               // in Dutch we want the publictext 'phonicshomos' to show for blends as well as single sounds.
               if (shark.language.equals(shark.LANGUAGE_NL) || (! ( ((word) o).phonics && !((word) o).phonicsw &&  !singlesound))) {
                 int picwidth = sharkStartFrame.screenSize.width / 4;
                 int picheight = sharkStartFrame.screenSize.height / 3;
                 addPicture( ( (word) o).vpic(), pos.x - picwidth * 33 / 32,
                            Math.max(0,
                                     Math.min(sharkStartFrame.screenSize.height -
                                              picheight,
                                              r.y - pscroll.y + pos.y -
                                              picheight / 2 + getFixedCellHeight())),
                            picwidth, picheight);
                 repaint();
               }
             } catch (IllegalComponentStateException e) {}
           }};
       }
     }
  }
  //----------------------------------------------------------
  void changewords(String s) {
    short i;
    String ss = u.stripslashes(s);
    splitchanged = true;
    for(i=0;i<words.length;++i) {
      if(u.stripslashes(words[i].value).equals(ss)) words[i].value = new String(s);
    }
    if(sharkStartFrame.currPlayTopic.splitwords == null) {
       sharkStartFrame.currPlayTopic.splitwords = new String[]{new String(s)};
       showsavesplits();
       return;
    }
    for(i=0;i<sharkStartFrame.currPlayTopic.splitwords.length;++i) {
        if(u.stripslashes(sharkStartFrame.currPlayTopic.splitwords[i]).equals(ss)) {
          sharkStartFrame.currPlayTopic.splitwords[i] = new String(s);
          showsavesplits();
          return;
        }
    }
    sharkStartFrame.currPlayTopic.splitwords = u.addString(sharkStartFrame.currPlayTopic.splitwords,s);
    showsavesplits();
  }
  //----------------------------------------------------------
  void addsplits() {
      DefaultListModel model = (DefaultListModel)getModel();
      String ss;
      int i,j;
      word ww;
      if(phonicsw && wordlist.usephonics)return;
      for (i = 0; i < model.getSize(); ++i) {
        model.setElementAt(ww = words[i] = new word(originalwords[i]), i);
        words[i].value = ss = u.stripslashes(ww.value);
        if (sharkStartFrame.currPlayTopic.splitwords != null) {
          for (j = 0; j < sharkStartFrame.currPlayTopic.splitwords.length; ++j) {
//startPR2008-08-05^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            String s1;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            if (u.stripslashes(sharkStartFrame.currPlayTopic.splitwords[j]).equals(ss)) {
              words[i].value = new String(sharkStartFrame.currPlayTopic.splitwords[j]);
              break;
            }
//startPR2008-08-05^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            else if ((s1 = convertSplitsFromPreV4(ww,sharkStartFrame.currPlayTopic.splitwords[j]))!=null)
            {
              words[i].value = s1;
              sharkStartFrame.currPlayTopic.splitwords[j] = s1;
              break;
            }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          }
        }
      }
      setsplitflags();
  }
//startPR2008-08-05^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  String convertSplitsFromPreV4(word w, String splitw){
    if(!w.phonicsw)return null;
    if(splitw.indexOf(u.phonicsplit)>=0)return null;

    char c[] = w.value.toCharArray();
    int lastsplitwindex = 0;
    int oldlastsplitwindex = 0;
    boolean isalreadysplit = false;
    int lastletter = 1;
    for(int i = 0; i < c.length; i++){
      if(c[i]!='/'&&c[i]!=u.phonicsplit){
        lastsplitwindex=findNextChar(splitw, c[i], lastsplitwindex);
        if (lastsplitwindex>=0){
          if((splitw.substring(oldlastsplitwindex, lastsplitwindex)).indexOf('/')>=0 && !isalreadysplit){
            c = u.addchar(c, '/', lastletter+1);
            i++;
          }
          oldlastsplitwindex=lastsplitwindex=lastsplitwindex+1;
        }
        else if(lastsplitwindex==-1){
          return null;
        }
        else if(lastsplitwindex==-2){
          return String.valueOf(c);
        }
        isalreadysplit = false;
        lastletter = i;
      }
      else if(c[i]=='/')isalreadysplit = true;
    }
    return null;
  }

  int findNextChar(String w, char ch, int lastindex){
    char c[] = w.toCharArray();
    int i;
    for(i = lastindex; i < c.length; i++){
      if(c[i]!='/'){
        if(ch!=c[i]) return -1;
        return i;
      }
    }
    return -2;
  }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  //--------------------------------------------------------
  void setsplitflags() {   // set up  split flags
     int i,j,unsplittot = 0;
     splittot = 0;
     split4 = false;
     if(sharkStartFrame.currPlayTopic.phonics  && !sharkStartFrame.currPlayTopic.phonicsw
        || sharkStartFrame.currPlayTopic.phonicsw && student.option("s_usephonics")) {
        return;    // leave as is with phonics splits
     }
     word ww;
     for(i=0;i<model.getSize();++i)  {
        ww = (word)model.getElementAt(i);
        boolean doignore =false;
        for(int k = 0; k <u.ignore.length; k++){
           if(ww.v().startsWith(u.ignore[k]))
               doignore =true;
        }
        String ss[];
        if(doignore && (ss = u.splitString(ww.v(), ' ')).length==2){
            if(ss[1].length()>=ss[0].length())
                j = u.syllabletot(ss[1]);
            else
                j = u.syllabletot(ss[0]);
        }
        else
            j = u.syllabletot(ww.v());
        if(ww.value.indexOf('/') >= 0) {
           ++splittot;
        }
        else if(j > 1){
            ++unsplittot;
        }
     }
     unsplit  = (unsplittot > 4 || splittot >0 && unsplittot > 0);
     if(splittot >= 4 || splittot == model.getSize()) {
       split4 = true;
     }
     sharkStartFrame.currPlayTopic.split4 = split4;
     sharkStartFrame.currPlayTopic.unsplit = unsplit;
  }
  //-----------------------------------------------------------
  class itempainter extends JLabel implements ListCellRenderer {
     itempainter() {setOpaque(true);}
     Object o;
     boolean selected;
     public Component getListCellRendererComponent( JList list,Object oo,
           int index,boolean isSelected,boolean cellhasFocus) {
        o = oo;
        this.setForeground(isSelected?Color.white:Color.black);
//        this.setBackground(isSelected?selbg:Color.white);

//startPR2013-02-20^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//         this.setBackground(isSelected?selbg:wlbgcol);
         this.setBackground(isSelected?selbg:bgcoloruse);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        selected = isSelected;
        return this;
     }
     public void paint(Graphics g) {
        if(o instanceof word) {
           Rectangle r = getBounds();
           if(font==null) {
              if (!setfont()) return;
           }
//startPR2005-02-26^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
           if(r.height < getFixedCellHeight()){
             return;
           }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
           g.setColor(getBackground());
           g.fillRect(0,0,r.width,r.height);
           g.setColor(getForeground());
           if(extrainf != null && extrainfo == o) {
                if(!selected)  extrainf = null;
                else {
                  if (extrafont == null) {
                    extrafont = font;
                    extram = g.getFontMetrics(font);
                    while (extram.getHeight() * 2 > r.height) {
//startPR2007-11-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//                      extrafont = new Font(extrafont.getName(), extrafont.getStyle(),
//                                           extrafont.getSize() - 1);
                      extrafont = extrafont.deriveFont((float)extrafont.getSize()-1);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                      extram = g.getFontMetrics(extrafont);
                    }
                  }
                  g.setFont(extrafont);
                  ( (word) o).paint(g, new Rectangle(MARGIN, 0, r.width - MARGIN * 2,
                                                  r.height / 2), paintOptions());
                  g.drawString(extrainf, MARGIN,
                               r.height * 3 / 4 - extram.getHeight() / 2 +
                               extram.getAscent());
                  return;
                }
           }
           g.setFont(font);
//startPR2007-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
           ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR

           
           
           if(!((word)o).paint(g,new Rectangle(MARGIN,0,r.width-MARGIN*2,r.height),paintOptions())
                 && font.getSize()>8 ) {
//startPR2007-11-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//             font = new Font(font.getName(),font.getStyle(), font.getSize()-1);
                font = font.deriveFont((float)font.getSize()-1);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
             paint(g);
             this.getTopLevelAncestor().repaint();
             return;
           }

           
        }
        else super.paint(g);
     }
  }
  //----------------------------------------------------------
  public int paintOptions() {
        int ret = word.HIGHLIGHT;
        if(vowels) ret |= word.VOWELS;
        if(split)  ret |= word.SPLIT;
        if(phonicsw && usephonics)  ret |= word.PHONICSPLIT;
        if(shapes)  ret |= word.SHAPE;
        return ret;
  }
  //---------------------------------------------------------
  public word[] getList() {   // exclude bad words
     short i,j=0,len = (short)wordtot,newlen=0;
     short order1[] = wholeextended?sortwords(false):order;
     for(i=0;i<len;++i) {
        if(sharkStartFrame.currPlayTopic.phonicsw
           && (student.option("s_usephonics") || sharkStartFrame.currPlayTopic.justphonics)
           && words[i].value.indexOf('=')<0) continue;
        if(!words[i].bad && words[i].value.indexOf('*')<0
//                                && words[i].value.indexOf(' ')<0
                                && words[i].value.indexOf('{')<0
               && (!areSingles || !words[i].paired)
        ) ++newlen;
     }
     word ret[] = new word[newlen];
     for(i=0;i<len;++i) {
        if(sharkStartFrame.currPlayTopic.phonicsw
           && (student.option("s_usephonics") || sharkStartFrame.currPlayTopic.justphonics)
           && words[order1[i]].value.indexOf('=')<0) continue;
        if(!words[order1[i]].bad && words[order1[i]].value.indexOf('*')<0
//                                && words[order1[i]].value.indexOf(' ')<0
                                && words[order1[i]].value.indexOf('{')<0
                                && (!areSingles || !words[order1[i]].paired)
            )ret[j++] = new word(words[order1[i]]);
     }
     return ret;
   }
  //---------------------------------------------------------
  public word[] getNoSameSpell() {   // exclude bad words and words spelt same
      word ww[] = getList();
      int i,j;
      loop:for(i=0; i<ww.length;++i) {
         if(ww[i].value.indexOf('@')>0) {
            for(j=0;j<i;++j) {
               if(ww[i].v().equals(ww[j].v())) {
                  word www[] = new word[ww.length-1];
                  System.arraycopy(ww,0,www,0,i);
                  if(i<ww.length-1) System.arraycopy(ww,i+1,www,i,www.length-i);
                  ww = www;
                  --i;
                  continue loop;
               }
            }
         }
      }
      return ww;
  }
  //---------------------------------------------------------
  public word[] getNoSingles() {   // exclude bad words and single letters
     short i,j=0,len = (short)wordtot,newlen=0;
     short order1[] = wholeextended?sortwords(false):order;
     for(i=0;i<len;++i) {
        if(sharkStartFrame.currPlayTopic.phonicsw
           && (student.option("s_usephonics") || sharkStartFrame.currPlayTopic.justphonics)
           && words[i].value.indexOf('=')<0) continue;
        if(!words[i].bad && words[i].value.indexOf('*')<0
//                                && words[i].value.indexOf(' ')<0
                                && words[i].value.indexOf('{')<0
               && (!usephonics || !words[i].onephoneme)
               && (!areSingles || !words[i].paired)
               && words[i].v().length() > 1
        ) ++newlen;
     }
     word ret[] = new word[newlen];
     for(i=0;i<len;++i) {
        if(sharkStartFrame.currPlayTopic.phonicsw
           && (student.option("s_usephonics") || sharkStartFrame.currPlayTopic.justphonics)
           && words[order1[i]].value.indexOf('=')<0) continue;
        if(!words[order1[i]].bad && words[order1[i]].value.indexOf('*')<0
//                                && words[order1[i]].value.indexOf(' ')<0
                                && words[order1[i]].value.indexOf('{')<0
                                && (!areSingles || !words[order1[i]].paired)
                                && (!usephonics || !words[i].onephoneme)
                         && words[i].v().length() > 1
            )ret[j++] = new word(words[order1[i]]);
     }
     return ret;
   }
  //---------------------------------------------------------
  public word[] getExtendedList() {   // exclude bad words
     short i,j=0,len = (short)words.length,newlen=0;
     for(i=0;i<len;++i) {
        if(!words[i].bad && words[i].value.indexOf('*')<0
//                                && words[i].value.indexOf(' ')<0
                                && words[i].value.indexOf('{')<0
        ) ++newlen;
     }
     word ret[] = new word[newlen];
     for(i=0;i<len;++i) {
        if(!words[i].bad && words[i].value.indexOf('*')<0
//                                && words[i].value.indexOf(' ')<0
                                && words[i].value.indexOf('{')<0)
            ret[j++] = new word(words[i]);
     }
     return ret;
   }
  //---------------------------------------------------------
  public  static String[] getheadings(word[][] w) {
     String s[] = new String[w.length];
     for(short i=0;i<w.length;++i) {
        s[i] = w[i][0].heading.substring(8); // strip 'Heading:'

     }
     return s;
  }
  //---------------------------------------------------------
  public static String getHeading(word[] w) {
     for(short i=0;i<w.length;++i) {
        if(w[i].heading != null)
          return w[i].heading.substring(8); // strip 'Heading:'
     }
    return sharkStartFrame.currPlayTopic.headings[0];
  }
  //---------------------------------------------------------
  public word[] getAllWordsBoth() {
     return sharkStartFrame.currPlayTopic.getAllWordsBoth();
   }
  //---------------------------------------------------------
  public word[] getBadList() {   // include bad words
     return u.addWords(sharkStartFrame.currPlayTopic.getAllWords(false), // get extended list
                       sharkStartFrame.currPlayTopic.getBadWords());
   }
  //---------------------------------------------------------
  public word[] getBadList2() {   // include bad words
     return u.addWords(sharkStartFrame.currPlayTopic.getAllWords(true), // get extended list
                       sharkStartFrame.currPlayTopic.getBadWords());
   }
  //---------------------------------------------------------
  public String[] getPatterns() {   // get all patterns
     short i,j=0,len = (short)wordtot,newlen=0;

     for(i=0;i<len;++i) {
        if(words[i].value.indexOf('*')>=0
//                                && words[i].value.indexOf(' ')<0
                                && words[i].value.indexOf('{')<0) ++newlen;
     }
     String ret[] = new String[newlen];
     for(i=0;i<len;++i) {
        if(words[i].value.indexOf('*')>=0
//                                && words[i].value.indexOf(' ')<0
                                && words[i].value.indexOf('{')<0)
            ret[j++] = words[i].value;
     }
     return ret;
   }
  //---------------------------------------------------------
  public word[] getPairedList(String game) {   // paired (from standard list if not extended
     short i,j=0,len = (short)wordtot,newlen=0;
     boolean kg = keepGroupings;
     keepGroupings = true;
     short order1[] = sortwords(!shuffled && !sharkStartFrame.currPlayTopic.inorder);
     word wd[] = words;
     for(i=0;i<len;++i) {
        if(words[i].companions == 1) {newlen+=2;++i;}
     }
     if(shuffled && newlen < 6) {
        words = sharkStartFrame.currPlayTopic.getWords(null,false);
        len = (short)(words.length);
        order1 = u.select(len,len);
        newlen=0;
         for(i=0;i<len;++i) {
           if(words[i].companions == 1) {newlen+=2;++i;}
        }
     }
     word ret[] = new word[newlen];
     for(i=0;i<len;++i) {
        if(words[order1[i]].companions == 1) {
            ret[j++] = new word(words[order1[i]]);
            ret[j++] = new word(words[order1[++i]]);
        }
     }
     keepGroupings = kg;
     words = wd;
     return ret;
   }
   //------------------------------------------------------------
  public sentence[] getsentences(word[] ww) {
     short i,len = (short)ww.length;
     sentence ret[] = new sentence[len];
     for(i=0;i<len;++i) {
            ret[i] = new sentence(ww[i].value,ww[i].database);
     }
     return ret;
  }
  //------------------------------------------------------------
 public static word[] getphrases(word[] ww) {
    short i,len = (short)ww.length;
    word ret[] = new word[0];
    for(i=0;i<len;++i) {
      sentence sent = new sentence(ww[i].value,ww[i].database);
      String ss = sent.stripcloze();
      if (ss!=null) {
        ret = u.addWords(ret, new word(sent.stripcloze(), sent.database));
        if(sent.picture != null) ret[ret.length - 1].picture = sent.picture.name;
      }
    }
    return ret;
 }
   //----------------------------------------------------------
                           // words with and without pattern
  public word[] getPatternList(String game,short with,short without) {
                       // use extended list to supply words
     word ret1[] = sharkStartFrame.currPlayTopic.getHeadedWords2(new String[]{game});
     word ret[]   = new word[with+without];
     short want = (short)(with+without);
     short o[] = u.shuffle(u.select(want,want)),curr=0;
     String list[] = null;
     short i,j;

     short len = (short)ret1.length;
     for(i=0;i<len && with+without>0;++i) {
        if(ret1[i].pat != null) {
           if(with>0) {
              ret[o[curr++]] = ret1[i];
              --with;
           }
        }
        else if(without>0) {
           ret[o[curr++]] = ret1[i];
           --without;
        }
     }
     for(i=0;i<curr && with+without>0;++i) {
        if(ret[o[i]].pat != null) {
           if(with>0) {
              ret[o[curr++]] = ret[o[i]];
              --with;
           }
        }
        else if(without>0) {
              ret[o[curr++]] = ret[o[i]];
              --without;
        }
     }
     if(with+without>0) {
         word ret2[] = new word[curr];
         for(i=0,j=0;i<ret.length;++i) {
            if(ret[i] != null) ret2[j++] = ret[i];
         }
         return ret2;
     }
     return ret;
  }
  //-----------------------------------------------------------
  public static short fits(String ss, String p) {
     String s = ss.toLowerCase();
     short i,j,k,plen = (short)p.length(), slen = (short)s.length();
     String s2,p2;
     char ch;
     if(p.charAt(0) == '*' && p.charAt(plen-1) == '*') {
        j=0;
        p2=p.substring(1,plen-1);
        while((j = (short)s.indexOf(p2,j)) >=0) {
          if(p.length() == 3 && s.length() > j+1 && s.charAt(j+1) == p.charAt(1))
            j+=2;
          else return (short) (j);
        }
        if(p2.indexOf('-') < 0 &&  p2.indexOf('*') < 0) return -1;
     }
     for(i=0;i<plen && i<slen;++i) {
        ch = p.charAt(i);
        if(ch == '-' && u.vowels.indexOf(s.charAt(i)) >= 0) return -1;
        if(ch != '-' && ch != s.charAt(i)) {
           if(ch == '*') {
              if(i==plen-1) return 0;
              p2 = p.substring(i+1);
              ch = p2.charAt(0);
              s2 = s.substring(i);
              if(ch == '-') {
                 for(j=0;j<s2.length();++j)
                 if ((k = fits(s2.substring(j), p2)) >= 0) return (short)(i+j+k);
              }
              else while ((j = (short)s2.indexOf(ch)) >=0) {
                 if((k=fits(s2.substring(j), p2))>=0) return (short)(s.length()-s2.length()+j+k);
                 s2 = s2.substring(j+1);
              }
           }
           return -1;
        }
     }
     if(i<plen-1 ||  p.charAt(plen-1) != '*' && slen != plen
           ) return -1;
     else return 0;
   }
  public static boolean[] fitsPattern(String s, String p, boolean b[], int counter) {
     short i,j,k,plen = (short)p.length(), slen = (short)s.length();
     char ch,chp,chl;
     String low = s.toLowerCase();
     boolean paton = false;
     if(p.length()==0){return null;}
     short start =  fits(s,p);
     if(start >= 0) {
        if(start > 0)  {
  //         g.drawString(s.substring(0,start),x,y);
   //         x += m.stringWidth(s.substring(0,start));
            counter +=start;
        }
        for(i=start,j=0;i<slen;++i) {
           ch = s.charAt(i);
           chl = low.charAt(i);
           chp = p.charAt(j);
               // pick up 2nd occurrence of pattern
           if(j == plen-1 && chp=='*' && fits(s.substring(i+1),p)>=0) {
  //           g.setColor(c1);
 //            g.drawChars(new char[]{ch},0,1,x,y);
               b[counter++] = true;
//             x += m.charWidth(ch);
             fitsPattern(s.substring(i+1),p,b,counter);
             return b;
           }
           if(chp =='*'
               && j < plen-1
               && fits(s.substring(i),p.substring(j+1))>=0) {
                  ++j;
                chp = p.charAt(j);
           }
           if(chl == chp) {
 //              g.setColor(c2);
               paton = true;
               ++j;
           }
           else {
  //           g.setColor(c1);
               paton = false;
             if(chp == '-') {
                   ++j;
             }
           }
           b[counter++] = paton;
       //    g.drawChars(new char[]{ch},0,1,x,y);
     //      x += m.charWidth(ch);
        }
      }
     return b;
   }
  //-----------------------------------------------------------
  public static void paintpat(String s, String p, Graphics g,
                                Color c1, Color c2, int x, int y) {
     short i,j,k,plen = (short)p.length(), slen = (short)s.length();
     char ch,chp,chl;
     String low = s.toLowerCase();
     FontMetrics m = g.getFontMetrics();
     g.setColor(c1);
     if(p.length()==0)   {g.drawString(s,x,y); return;}
     short start =  fits(s,p);
     if(start < 0) {
        g.drawString(s,x,y);
     }
     else {
        if(start > 0)  {
            g.drawString(s.substring(0,start),x,y);
            x += m.stringWidth(s.substring(0,start));
        }
        for(i=start,j=0;i<slen;++i) {
           ch = s.charAt(i);
           chl = low.charAt(i);
           chp = p.charAt(j);
               // pick up 2nd occurrence of pattern
           if(j == plen-1 && chp=='*' && fits(s.substring(i+1),p)>=0) {
             g.setColor(c1);
             g.drawChars(new char[]{ch},0,1,x,y);
             x += m.charWidth(ch);
             paintpat(s.substring(i+1),p,g,c1,c2,x,y);
             return;
           }
           if(chp =='*'
               && j < plen-1
               && fits(s.substring(i),p.substring(j+1))>=0) {
                  ++j;
                chp = p.charAt(j);
           }
           if(chl == chp) {
               g.setColor(c2);
               ++j;
           }
           else {
             g.setColor(c1);
             if(chp == '-') {
                   ++j;
             }
           }
           g.drawChars(new char[]{ch},0,1,x,y);
           x += m.charWidth(ch);
        }
      }
   }
   //--------------------------------------------------------------
   public static short patternlen(String p) {
      short i,len=0;
      for(i=0;i<p.length();++i) {
         if(p.charAt(i) != '*') ++len;
      }
      return len;
   }
   //-----------------------------------------------------------------
   public  boolean addPicture(String name,int x1,int y1,int w1,int h1) {
      sharkImage im;
//startPR2008-06-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      if(currpic != null) currpic.stop();
//      currpic = null;
      removeCurrPic();
      boolean ok = false;
//endPRimagesandvids^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      if(sharkStartFrame.studentList[sharkStartFrame.currStudent].wantpics){
         if((im=sharkImage.find(name)) != null)    {
//startPR2008-06-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            if(im.isvideo){
              if(!MediaPanel.isPlayingVideo)
                currpic = new MediaPanel(parent, im, x1, y1, h1, w1);
            }
            else
//endPRimagesandvids^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
             currpic = new wordpicture(parent,name,im,
                            x1,y1,h1,w1);
            ok = true;
         }
         if(shark.showPhotos){
            int h = u.getPhotographIndex(name);
                  if (h>=0){
                        Image im1 = Toolkit.getDefaultToolkit().getImage(sharkStartFrame.publicPhotoNamesFolder
                                + sharkStartFrame.separator + sharkStartFrame.publicPhotoNamesPlusExt[h]); 
                        sharkImage im2 = new sharkImage(im1, name);
                        im2.isimport = true;       
                        currpic2 = new wordpicture(parent,name,im2,
                                       x1-w1,y1,h1,w1, true);
                        currpic2.getContentPane().setBackground(Color.white);
                         ok = true;
                  }              
         }
     }
     return ok;
  }
  /**
   * Changes the current picture to the one with the name passed.
   * @param name Name for picture
   * @param title Parameter not used
   * @param x1 Coordinate
   * @param y1 Coordinate
   * @param w1 Coordinate
   * @param h1 Coordinate
   * @return true if new picture has been created.
   */
   
  public boolean putPicture(String name,String title,int x1,int y1,int w1,int h1) {
      return putPicture(name,title,true,x1,y1,w1,h1);
 }   
   
  public boolean putPicture(String name,String title, boolean exitOnEnterExit,int x1,int y1,int w1,int h1) {
     sharkImage im;
     sharkImage im2;
//startPR2008-06-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//     if(currpic != null) currpic.stop();
//     currpic = null;
     removeCurrPic();
//endPRimagesandvids^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     boolean ok = false;
     if((im=sharkImage.find(name)) != null)    {
        currpic = new wordpicture(parent,name,im,exitOnEnterExit,
                       x1,y1,h1,w1);
        currpic.getContentPane().setBackground(Color.white);
        ok = true;
    }
     if(shark.showPhotos){
        int h = u.getPhotographIndex(name);
              if (h>=0){
                    Image im1 = Toolkit.getDefaultToolkit().getImage(sharkStartFrame.publicPhotoNamesFolder
                            + sharkStartFrame.separator + sharkStartFrame.publicPhotoNamesPlusExt[h]); 
                    im2 = new sharkImage(im1, name);
                    im2.isimport = true;       
                    currpic2 = new wordpicture(parent,name,im2,exitOnEnterExit,
                                   x1-w1,y1,h1,w1, true);
                    currpic2.getContentPane().setBackground(Color.white);
                     ok =  true;
              }           
     }
    return ok;
 }

 public static class wordpicture extends JWindow {
   public int screenwidth;
   public int screenheight;
   BorderLayout layout1 = new BorderLayout();
   public  runMovers mainPanel;
   public Color background;
   KeyListener kk;
   boolean closeOnEnterExit = true;
   boolean closeOnWordListEnter = true;
   sharkImage im;
   int x1,y1,w1,h1;
   boolean photo = false;
   //   wordpicture thisframe = this;

   
  public  wordpicture(Window parent, String title,sharkImage im1, boolean closeOnEnterExit1, int xx1,int yy1,int ww1, int hh1, boolean photo1) {
      super(parent);
      closeOnEnterExit = closeOnEnterExit1;
      closeOnWordListEnter = closeOnEnterExit;
      im = im1;
      x1 = xx1;
      y1 = yy1;
      w1 = ww1;
      h1 = hh1;
      photo = photo1;
      init();
  }      
   
   
  public  wordpicture(Window parent, String title,sharkImage im1, boolean closeOnEnterExit1, int xx1,int yy1,int ww1, int hh1) {
      super(parent);
      closeOnEnterExit = closeOnEnterExit1;
      closeOnWordListEnter = closeOnEnterExit;
      im = im1;
      x1 = xx1;
      y1 = yy1;
      w1 = ww1;
      h1 = hh1;
      init();
  } 
  
  public  wordpicture(Window parent, String title,sharkImage im1,int xx1,int yy1,int ww1, int hh1, boolean photo1) {
    super(parent);
    im = im1;
    x1 = xx1;
    y1 = yy1;
    w1 = ww1;
    h1 = hh1;
    photo = photo1;
    init();
  } 
   
  public  wordpicture(Window parent, String title,sharkImage im1,int xx1,int yy1,int ww1, int hh1) {
    super(parent);
    im = im1;
    x1 = xx1;
    y1 = yy1;
    w1 = ww1;
    h1 = hh1;
    init();
  }
  
  void init(){
    addMouseListener(new java.awt.event.MouseAdapter() {
         public void mouseEntered(MouseEvent e) {
             if(closeOnEnterExit)
                removeCurrPic();
         }
         public void mouseExited(MouseEvent e) {
             if(closeOnEnterExit)
                 removeCurrPic();
         }
       });

    this.addWindowListener(new java.awt.event.WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        stopall();
      }
       public void windowDeactivated(WindowEvent e) {
         stopall();
       }
    });
//startPR2004-08-27^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      // if running on a Macintosh
      if (shark.macOS) {
        addKeyListener(new java.awt.event.KeyAdapter() {
          public void keyPressed(KeyEvent e) {
             int code = e.getKeyCode();
             if(code == KeyEvent.VK_T && e.isMetaDown()) {
                if(sharkStartFrame.mainFrame.playingGames && sharkStartFrame.mainFrame.gameicons)
                      sharkStartFrame.mainFrame.gameicons = false;
                      sharkStartFrame.mainFrame.switchdisplay();
             }
             else if(code == KeyEvent.VK_G && e.isMetaDown()) {
                if(sharkStartFrame.mainFrame.playingGames && !sharkStartFrame.mainFrame.gameicons)
                   sharkStartFrame.mainFrame.gameicons = true;
                   sharkStartFrame.mainFrame.switchdisplay();
             }
             else if(code == KeyEvent.VK_F && e.isMetaDown()) {
                  if( sharkStartFrame.mainFrame.playingGames)
//startPR2004-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//                    new findword(null);
                    new findword(null, sharkStartFrame.mainFrame);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
             }
             stopall();
          }
        });
      }
      // if running on Windows
      else {
        addKeyListener(new java.awt.event.KeyAdapter() {
          public void keyPressed(KeyEvent e) {
             int code = e.getKeyCode();
             if(code == KeyEvent.VK_F11) {
                if(sharkStartFrame.mainFrame.playingGames && sharkStartFrame.mainFrame.gameicons)
                      sharkStartFrame.mainFrame.gameicons = false;
                      sharkStartFrame.mainFrame.switchdisplay();
             }
             else if(code == KeyEvent.VK_F12) {
                if(sharkStartFrame.mainFrame.playingGames && !sharkStartFrame.mainFrame.gameicons)
                   sharkStartFrame.mainFrame.gameicons = true;
                   sharkStartFrame.mainFrame.switchdisplay();
             }
             else if(code == KeyEvent.VK_F9) {
                  if( sharkStartFrame.mainFrame.playingGames)
//startPR2004-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//                    new findword(null);
                    new findword(null, sharkStartFrame.mainFrame);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
             }
             stopall();

          }
        });
      }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    mainPanel = new runMovers();
    JPanel border = new JPanel(new BorderLayout());
//startPR2008-06-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    border.setBorder(BorderFactory.createLineBorder(im.getcolor(0).darker(),2));
    border.setBorder(BorderFactory.createLineBorder(im.isimport?Color.lightGray:im.getcolor(0).darker(),2));
//endPRimagesandvids^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    border.add(mainPanel, BorderLayout.CENTER);
    this.getContentPane().setLayout(layout1);
    this.getContentPane().add(border, BorderLayout.CENTER);
    background = mainPanel.getBackground();
    mainPanel.dontrun = true;
    im.w = mover.WIDTH;
    im.h = mover.HEIGHT;
    im.adjustSize(w1,h1);
    int neww = w1*im.w/mover.WIDTH+4,
         newh = h1*im.h/mover.HEIGHT+4;
    im.w = mover.WIDTH;
    im.h = mover.HEIGHT;
    if(current != null) {              // v5 start rb 6/3/08
      try {
        Point p = current.getLocationOnScreen();
        setBounds(x1 + w1 - neww,
                  Math.max(p.y, Math.min(p.y + current.getHeight() - newh, y1 + h1 / 2 - newh / 2)),
                  neww, newh);
      }
      catch(IllegalComponentStateException e) {
        setBounds(  Math.max( x1 + w1 - neww, 0)     ,
                   Math.max(0, Math.min(sharkStartFrame.screenSize.height - newh, y1 + h1 / 2 - newh / 2)),
                   neww, newh);
      }
    }
    else {
      setBounds(x1 + w1 - neww,
                Math.max(0, Math.min(sharkStartFrame.screenSize.height - newh, y1 + h1 / 2 - newh / 2)),
                 neww, newh);
    }                                 // v5 end rb 6/3/08
    if(im.hasbackgroundpoly()) mainPanel.setBackground(background = im.getcolor(0));
    mainPanel.addMover(im,0,0);
    setVisible(true);
    validate();
    mainPanel.start1();
  }

  public void close(){
      removeCurrPic();
  }

  void stop() {
        stopall();
  }
  void stopall() {
        if(mainPanel != null) {
           mainPanel.stop();
           remove(mainPanel);
           mainPanel = null;
           dispose();
        }
  }
//  protected void finalize() {
//      int i=4;
//      return;
//  }
 }


  //---------------------------------------------------------

  public static void removeCurrPic(){
     if(currpic != null) {    //The wordtree's picture is showing
        if(currpic instanceof wordpicture){
            ((wordpicture) currpic).stop();
        }
        else if(currpic instanceof MediaPanel){
            ((MediaPanel)currpic).stopall();
        }
        currpic = null;
     }
     if(currpic2 != null) {    //The wordtree's picture is showing
        if(currpic2 instanceof wordpicture){
            ((wordpicture) currpic2).stop();
        }
        else if(currpic2 instanceof MediaPanel){
            ((MediaPanel)currpic2).stopall();
        }
        currpic2 = null;
     }
  }


  public static word[] getNormalWordlist() {
     short i;
     boolean aresingles=false;
     word s[]=new word[0];
     word w[] = topic.onlywords(sharkStartFrame.currPlayTopic.getWords(null,false));
     for(i=0;i<w.length;++i) {
        if(!w[i].paired) {aresingles = true; break;}
     }
     for(i=0;i<w.length;++i) {
        if(!aresingles || !w[i].paired) s = u.addWords(s,w[i]);
     }
     return(s);
   }
  
   public void newselection(int sel) {
               if(changing) return;
                extrainf = null;
                ordersel = (byte) sel;
                if(sel==STANDARD) {
                   shuffled = keepGroupings = wholeextended =false;
                   if(butInOrder!=null)butInOrder.setVisible(true);
                   if(butRefresh!=null)butRefresh.setVisible(false);
                }
                else if(sel == EXTENDEDWHOLE) {
                   shuffled = keepGroupings = false;
                   wholeextended = true;
                   if(butInOrder!=null)butInOrder.setVisible(false);
                   if(butRefresh!=null)butRefresh.setVisible(false);
                }
                else {
                   shuffled = true;
                   wholeextended = false;
                   keepGroupings = (areGroupings && sel == 1);
                   if(butInOrder!=null)butInOrder.setVisible(false);
                   if(butRefresh!=null)butRefresh.setVisible(true);
                 }
//startPR2007-02-16^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                 sharkStartFrame.mainFrame.setMenuForExtended(wholeextended);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                if(canextend) {
                   redrawing = true;
                   setup(sharkStartFrame.currPlayTopic,null, false, false);
//                   sharkStartFrame.mainFrame.clearsavemenu();
                   redrawing = false;
                }
                else {
                  buildTree(false);
                  addsplits();
                }
                font = null;
   }
   JPanel getorderPanel() {return new orderPanel();}
   class orderPanel extends JPanel {
      ButtonGroup bg = new ButtonGroup();
      GridBagLayout gb = new GridBagLayout();
      GridBagConstraints gr = new GridBagConstraints();

      orderPanel() {
         setBorder(BorderFactory.createEtchedBorder());
         setLayout(gb);
         gr.gridx = 0;
         gr.gridy = -1;
         gr.weightx = gr.weighty = 1;
         gr.fill = GridBagConstraints.BOTH;
         if(areGroupings) {
            if (canextend) {
               if(!nostandard) add(standard,gr);
               add(extended,gr);
               add(extendedwhole,gr);
            }
         }
         else {
            if (canextend) {
               if(!nostandard) add(standard,gr);
               add(extended,gr);
               add(extendedwhole,gr);
            }
         }
         for(short i=0;i<getComponentCount();++i) {
            bg.add((JRadioButton)getComponent(i));
         }
         if(getComponentCount() >0) ((JRadioButton)getComponent(0)).setSelected(true);
      }
      int getselected() {
         for(short i=0;i<getComponentCount();++i) {
            if(((JRadioButton)getComponent(i)).isSelected())
              if(getComponent(i) == standard)                return ordersel = STANDARD;
              else if(getComponent(i) == extended)                return ordersel = EXTENDED;
              else if(getComponent(i) == extendedwhole)           return ordersel = EXTENDEDWHOLE;
         }
         return -1;
      }
      void setselected(int i) {
         bg.setSelected((ButtonModel)getComponent(ordersel=(byte)i),true);
      }
   }
}

