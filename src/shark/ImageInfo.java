/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */package shark;

import javax.swing.tree.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import java.lang.ref.SoftReference.*;
import java.io.File;
import javax.swing.event.*;
import javax.swing.text.*;
import static shark.wordlist.current;

public class ImageInfo  extends JPanel implements Runnable {
   static final short MAXNAMELEN = 35;
//startPR2004-09-01^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    /**
     * The maximum length of name of sound that can be displayed in the JTextField
     * when running on a Macintosh
     */
    static final short MACMAXNAMELEN = 28;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   static final short MAXNAMELEN2 = 20;
   String soundDBName;
   byte savedwords;
   static String[] images_1 = null;
   static String[] images_2 = new String[0];
   static String[] images_3 = new String[0];
   static String[] images_4 = new String[0];
   String alreadywords[] = new String[0];
   static String[] notyetwords_1 = new String[0]; 
 //  static String[] notyetwords_2 = new String[0]; 
 //  static String[] notyetwords_3 = new String[0]; 
   String notyetwords2[] = new String[0];
   String extrawords[] = new String[0];
//startPR2009-02-25^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   String phonicsoundswords[] = new String[0];
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   JList full_1= new JList(),already =new JList(),notyet_1 =new JList(),extra =new JList();
   JList full_2= new JList(),notyet_2 =new JList();
   JList full_3= new JList(),notyet_3 =new JList();
   JList full_4= new JList();
//startPR2009-02-25^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   JList phonicsounds= new JList();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   JList selectedlist,selectedlist3;
   int wordtot_1;
   int wordtot_2;
   int wordtot_3;
   int wordtot_4;
   String currword,prevword;
   boolean changingword, doingRecording;
   spokenWord currSpokenWord;
//   JTextField recordName = new JTextField();
   JTextField defname;
   JTextField extraInfo;
   JLabel maintitle = u.label("rec_main");
   JLabel wordtitle = u.label("rec_curr");
 //  JButton startRecording = u.Button("rec_start");


   String startText2 = u.gettext("rec_start","label2");
   String startText3 = u.gettext("rec_start","label3");
   String startText4 = u.gettext("rec_start","label4");
   String startText5 = u.gettext("rec_start","label5");
//   JButton listen = u.Button("rec_listen");
   JButton listen_curr = u.sharkButton();
   JButton listen_proposed = u.sharkButton();


   String ignoreText2 = u.gettext("rec_ignore","label2");


   String saveText2 = u.gettext("rec_save","label2");


   JButton whattopic = u.Button("rec_topic");

   JButton exitbutton = u.Button("rec_exit");


   JLabel oldsound = u.label("rec_oldsound");
   JLabel newsound = u.label("rec_newsound");
   String inpub = "<<"+u.gettext("rec_","inpub");
   String phonics = u.gettext("rec_","phonics");

   boolean isNewWord,isOldWord,definitions,definitions2,sentences;
   String oldtitle;
   JPanel panel1 = new JPanel(new BorderLayout());
   JPanel panel1a = new JPanel(new GridBagLayout());
   JPanel panel2 = new JPanel();
   JPanel panel3 = new JPanel();
   JPanel panel4 = new JPanel();
   JPanel panel5 = new JPanel();
   JPanel panel6;

   JLabel label2 = new JLabel("Image files");

   GridBagLayout gridBagLayout0 = new GridBagLayout();
   GridBagLayout gridBagLayout1 = new GridBagLayout();
   GridBagLayout gridBagLayout2 = new GridBagLayout();
   GridBagLayout gridBagLayout3 = new GridBagLayout();
   GridBagLayout gridBagLayout4 = new GridBagLayout();
   GridBagLayout gridBagLayout5 = new GridBagLayout();
   GridBagConstraints grid1 = new GridBagConstraints();
   String[] topicList;
   static String[][] topicwords;
   ButtonGroup bg = new ButtonGroup();
   
   runMovers mainPanel = new runMovers();
   String duplicates[];
   JList duplicateList = new JList();
   String allwordImages[];
   static String lonelyimages_1[] = new String[]{};
   static String lonelyimages_2[] = new String[]{};
   static String lonelyimages_3[] = new String[]{};
   static String lonelyimages_4[] = new String[]{};
   JTabbedPane jtp = new JTabbedPane();
 //  static String[] allwords_1 = new String[]{};
   JList jlFindTopic = new JList();
   String imlibs[] = new String[]{"publicimage", "publicimageWORDS", "publicimageSENT", "publicimageX"};
   int imlib_publicimage = 0;
   int imlib_publicimageWORDS = 1;   
   int imlib_publicimageSENT = 2;
   int imlib_publicimageX = 3;
JCheckBox cbShowUnderscores;  
String lastMiddleList[] = null;
static boolean bWantUnderscores = true;
   
   
   
   boolean ispublic,sent1,sent3,auto, say3;
//startPR2010-05-28^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//   boolean sent4;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  //----------------------------------------------------------
   ItemListener bglisten = new java.awt.event.ItemListener() {
       public void itemStateChanged(ItemEvent e) {
          int i = -1;
          if(rb_1.isSelected()) { 
              i = 0;
          }
          else if(rb_2.isSelected()) {
              i = 1;
          }
          else if(rb_3.isSelected()) {
              i = 2;
          }
          
          doRBChange(i);
//startPR2009-02-25^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       }
   };
   
   void doRBChange(int i){
       String s =  getTabString();
          if(i == 0) {  
                panel1.removeAll();
                lastMiddleList = duplicates;
                duplicateList.setListData(doUnderscores(duplicates));
                panel1.add(u.uScrollPane(duplicateList),BorderLayout.CENTER);    
                panel1.validate();
          }
          else if(i == 1) {
               panel1.removeAll();
          //     if(s.equals(imlibs[imlib_publicimage]))
               lastMiddleList = notyetwords_1;
                   panel1.add(u.uScrollPane(new JList(doUnderscores(notyetwords_1))),BorderLayout.CENTER);
          //     else if(s.equals(imlibs[imlib_publicimageWORDS]))
          //         panel1.add(u.uScrollPane(new JList(notyetwords_2)),BorderLayout.CENTER);
          //     else if(s.equals(imlibs[imlib_publicimageSENT]))
          //         panel1.add(u.uScrollPane(new JList(notyetwords_3)),BorderLayout.CENTER);     
               panel1.validate();
          }
          else if(i == 2) {
               panel1.removeAll();
               if(s.equals(imlibs[imlib_publicimage])){
                   lastMiddleList = lonelyimages_1;
                   panel1.add(u.uScrollPane(new JList(doUnderscores(lonelyimages_1))),BorderLayout.CENTER); 
               }
               else if(s.equals(imlibs[imlib_publicimageWORDS])){
                   lastMiddleList = lonelyimages_2;
                   panel1.add(u.uScrollPane(new JList(doUnderscores(lonelyimages_2))),BorderLayout.CENTER); 
               }
               else if(s.equals(imlibs[imlib_publicimageSENT])){
                   lastMiddleList = lonelyimages_3;
                   panel1.add(u.uScrollPane(new JList(doUnderscores(lonelyimages_3))),BorderLayout.CENTER); 
               }
               else if(s.equals(imlibs[imlib_publicimageX])){
                   lastMiddleList = lonelyimages_4;
                   panel1.add(u.uScrollPane(new JList(doUnderscores(lonelyimages_4))),BorderLayout.CENTER); 
               }
               panel1.validate();
          }     
          cbShowUnderscores.setEnabled(i!=1);
       
   }
   

   String[] doUnderscores(String ss[]){
       boolean wantunderscores = cbShowUnderscores.isSelected();
       if(wantunderscores)return ss;
       String ret[] = new String[]{};
       for(int i = 0; i < ss.length; i++){
           if(ss[i].indexOf('_')<0)
               ret = u.addString(ret, ss[i]);
       }
       if(ret.length == 0)return null;
       return ret;
   }
   
   void checkall() {
       int i;
       for(i=0;i<alreadywords.length;++i) {
           already.setSelectedIndex(i);
           already.scrollRectToVisible(already.getCellBounds(i,i));
           new word(alreadywords[i],sharkStartFrame.getPrimarySoundDb(sharkStartFrame.publicSoundLib)).say();
       }
   }
  //------------------------------------------------------------------
   JRadioButton rb_2 = u.RadioButton("rec_full",bglisten);
   JRadioButton rb_1 = u.RadioButton("rec_notyet",bglisten);
   JRadioButton rb_3 = u.RadioButton("rec_extra",bglisten);
//startPR2009-02-25^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   JRadioButton buttonphonicsounds = u.RadioButton("rec_phonicsounds",bglisten);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR

   public ImageInfo(String wordsfrom,byte defs) {

       
       jtp.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent evt) {
                if (rb_3.isSelected()){
                    doRBChange(2);
                } 
            }
        });
               
               
               
      spokenWord.recording = false;
      spokenWord.autoboost = false;
whattopic.setText("Go to selected list");
        rb_1.setText("Image names duplicated between image files    *1");
      rb_2.setText("Words without images");  
      rb_3.setText("Images with no matches to words    *2");
    //     label2.setText(u.gettext("rec_already","label2"));

      
      
        
      
      
//startPR2010-05-28^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//  if(defs==6) sent4=true;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2004-09-01^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        // enables exiting screen via the ESC key
        addKeyListener(new KeyAdapter() {
          public void keyPressed(KeyEvent e) {
            int code = e.getKeyCode();
            if(code == KeyEvent.VK_ESCAPE)
              sharkStartFrame.mainFrame.setupgames();
          }
        });
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
setButtons();
      Thread runthread = new Thread(this);
      runthread.start();
//      setup();
//      validate();
   }
   
  void getImagesFromTopic(word w[], word wsents[], int topicIndex){
//      if(true)return;
  

                       
       for(int j=0;j<w.length;++j) {
          word wrd = w[j];
          String imname = wrd.vpic();
          boolean found = false;
          for(int k = 0; k < sharkStartFrame.publicImageLib.length; k++){
                String imagelib = sharkStartFrame.publicImageLib[k];
                imagelib = imagelib.substring(imagelib.lastIndexOf(File.separatorChar)+1);
                sharkImage im= sharkImage.find(imname,  imageLibStringToInt(imagelib));
                if(im!=null){
                    addimage(imname, topicIndex, imagelib);
                    found = true;
                }
           }
          if(!found){
              addimagenotyet(wrd.v());
          }
       }
       for(int j=0;wsents!=null && j<wsents.length;++j) {
           sentence sent = new sentence(wsents[j].value,wsents[j].database);
           if(sent != null){
               if(sent.picture!=null){
                   boolean found = false;
                   for(int k = 0; k < sharkStartFrame.publicImageLib.length; k++){
                    String imagelib = sharkStartFrame.publicImageLib[k];
                    imagelib = imagelib.substring(imagelib.lastIndexOf(File.separatorChar)+1);
                    sharkImage im= sharkImage.find(sent.picture.name,  imageLibStringToInt(imagelib));
                    if(im!=null){
                        addimage(sent.picture.name, topicIndex, imagelib);
                        found = true;
                    }
                   }
                   if(!found){
                        addimagenotyet(sent.picture.name);                       
                   }
               }
           }           
                             
            
    }       
      

  } 
   
   //------------------------------------------------------
  public void run() {
      
      

        String wordDBName = "publictopics";
        topicList = db.list(wordDBName,db.TOPIC);
        ProgressMonitor pm = new ProgressMonitor(sharkStartFrame.mainFrame,shark.programName,"Image scanning",0,topicList.length+100);
        
        
        soundDBName = "";
        ispublic = true;

        short sep,i,j;
        int pp;
        topic top;

        
        boolean firstin = images_1 == null;
        if(firstin){
            topicwords = new String[topicList.length][];
            for(i = 0; firstin  &&  i < topicList.length; ++i) {
                if(images_1 == null)images_1 = new String[0];
                 if(topicList[i].equalsIgnoreCase("garbage")) continue;
                 
                  top = new topic(wordDBName, topicList[i], null, null);
                  if(top == null) continue;            
        if(top.name.indexOf("method of travel")>=0){
           int g;
           g = 8;
       }                     
                  word wsents[] = top.getSpecials(topic.sentencegames2);
                    if(!top.initfinished) top.getWords(null,false);
                    word w[] = top.getAllWords(true);
                 //       for(int k = 0; k < sharkStartFrame.publicImageLib.length; k++){
                 //           String imagelib = sharkStartFrame.publicImageLib[k];
                 //           imagelib = imagelib.substring(imagelib.lastIndexOf(File.separatorChar)+1);
                    
                
                    
                  getImagesFromTopic(w, wsents, i);
                  if(pm.isCanceled()) return;

                   //     }     

                pm.setProgress(i);
          }
        }

        
      duplicates = findDuplicateImages();
      duplicateList.setListData(duplicates);
      

      
      
      if(firstin){
          String dbpics[] = db.list(imlibs[imlib_publicimage], db.IMAGE);
            for(int k = 0; k < dbpics.length; k++){
                if(dbpics[k].trim().equals(""))continue;
                if(u.findString(images_1, dbpics[k])<0){
                    if(u.findString(lonelyimages_1, dbpics[k])<0)
                       lonelyimages_1 =  u.addStringSort(lonelyimages_1, dbpics[k]);
                    images_1 = u.addStringSort(images_1, dbpics[k]);
                }
            }  
          dbpics = db.list(imlibs[imlib_publicimageWORDS], db.IMAGE);
            for(int k = 0; k < dbpics.length; k++){
                if(dbpics[k].trim().equals(""))continue;
                if(u.findString(images_2, dbpics[k])<0){
                    if(u.findString(lonelyimages_2, dbpics[k])<0)
                       lonelyimages_2 =  u.addStringSort(lonelyimages_2, dbpics[k]);
                    images_2 = u.addStringSort(images_2, dbpics[k]);
                }
            }   
          dbpics = db.list(imlibs[imlib_publicimageSENT], db.IMAGE);
            for(int k = 0; k < dbpics.length; k++){
                if(dbpics[k].trim().equals(""))continue;
                if(u.findString(images_3, dbpics[k])<0){
                    if(u.findString(lonelyimages_3, dbpics[k])<0)
                       lonelyimages_3 =  u.addStringSort(lonelyimages_3, dbpics[k]);
                    images_3 = u.addStringSort(images_3, dbpics[k]);
                }
            }  
          dbpics = db.list(imlibs[imlib_publicimageX], db.IMAGE);
            for(int k = 0; k < dbpics.length; k++){
                if(dbpics[k].trim().equals(""))continue;
                if(u.findString(images_4, dbpics[k])<0){
                    if(u.findString(lonelyimages_4, dbpics[k])<0)
                       lonelyimages_4 =  u.addStringSort(lonelyimages_4, dbpics[k]);
                    images_4 = u.addStringSort(images_4, dbpics[k]);
                }
            }  
          /*
        for(int k = 0; k < allwordImages.length; k++){
            if(u.findString(allwords_1, allwordImages[k])<0){
                if(u.findString(lonelyimages_1, allwordImages[k])<0)
                    u.addStringSort(lonelyimages_1, allwordImages[k]);
            }
        }
            */
      }

      
    
      wordtot_1 = images_1.length;
      wordtot_2 = images_2.length;
      wordtot_3 = images_3.length;
      wordtot_4 = images_4.length;
      if(pm.isCanceled()) return;
      full_1.setListData(images_1);
      full_2.setListData(images_2);
      full_3.setListData(images_3);
      full_4.setListData(images_4);
      if(pm.isCanceled()) return;
      

      full_1.addListSelectionListener(new ListSelectionListener(){
            public void valueChanged (ListSelectionEvent e){
                
                String ss = getTabString();
                 
                 showImage(sharkImage.find((String)((JList)e.getSource()).getSelectedValue(), imageLibStringToInt(ss)));
                 setupFindTopicList();
                 
            }
      });
      full_2.addListSelectionListener(new ListSelectionListener(){
            public void valueChanged (ListSelectionEvent e){
                 String ss = getTabString();
showImage(sharkImage.find((String)((JList)e.getSource()).getSelectedValue(), imageLibStringToInt(ss)));
setupFindTopicList();
            }
      });
      full_3.addListSelectionListener(new ListSelectionListener(){
            public void valueChanged (ListSelectionEvent e){
                 String ss = getTabString();
showImage(sharkImage.find((String)((JList)e.getSource()).getSelectedValue(), imageLibStringToInt(ss)));
setupFindTopicList();
            }
      });
       full_4.addListSelectionListener(new ListSelectionListener(){
            public void valueChanged (ListSelectionEvent e){
                 String ss = getTabString();
showImage(sharkImage.find((String)((JList)e.getSource()).getSelectedValue(), imageLibStringToInt(ss)));
setupFindTopicList();
            }
      });     
      
       pm.setProgress( topicList.length+100);
       

      pm.close();
      full_1.clearSelection();
      already.clearSelection();
      notyet_1.clearSelection();
      extra.clearSelection();
//startPR2009-02-25^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      phonicsounds.clearSelection();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      bg.add(rb_1);
      bg.add(rb_2);
      bg.add(rb_3);
//startPR2009-02-25^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      bg.add(buttonphonicsounds);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      panel1a.setBorder(BorderFactory.createLineBorder(label2.getForeground(),2));
      panel1a.setBackground(new Color(255,192,192));
      clearButtons();
      grid1.gridy = -1;
      grid1.gridx = 0;
      grid1.weightx = grid1.weighty = 1;
      grid1.fill = GridBagConstraints.NONE;
      grid1.gridheight = 1;
      setLayout(gridBagLayout0);
      panel2.setLayout(gridBagLayout2);
      panel3.setLayout(gridBagLayout3);
      panel4.setLayout(gridBagLayout4);
      panel5.setLayout(gridBagLayout5);
      grid1.weighty = 0;
      grid1.insets = new Insets(20,0,20,0);
      panel2.add(label2,grid1);
      grid1.insets = new Insets(0,0,0,0);
      grid1.fill = GridBagConstraints.BOTH;
      JLabel jlab = new JLabel( u.convertToHtml("*1  Only applies to the three main image files|*2 Image file specific"));
      jlab.setFont(jlab.getFont().deriveFont((float)jlab.getFont().getSize()-7));
      panel1a.add( jlab ,grid1);
      Color c = label2.getForeground();
      rb_1.setForeground(c);
      rb_2.setForeground(c);
      rb_3.setForeground(c);
//startPR2009-02-25^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      buttonphonicsounds.setForeground(c);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      panel1a.add(rb_1,grid1);
      panel1a.add(rb_2,grid1);
      panel1a.add(rb_3,grid1);
//startPR2009-02-25^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      if(soundDBName.equals(sharkStartFrame.getPrimarySoundDb(sharkStartFrame.publicSoundLib)))
        panel1a.add(buttonphonicsounds,grid1);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      panel3.add(panel1a,grid1);
      grid1.gridheight = 15;
      grid1.weighty = 15;
      grid1.fill = GridBagConstraints.BOTH;
      
      
           
      jtp.add(imlibs[imlib_publicimage], u.uScrollPane(full_1));
      jtp.add(imlibs[imlib_publicimageWORDS], u.uScrollPane(full_2)); 
      jtp.add(imlibs[imlib_publicimageSENT], u.uScrollPane(full_3)); 
      
      
      for(int p = 0; p < sharkStartFrame.publicImageLib.length; p++){
          if(sharkStartFrame.publicImageLib[p].endsWith(imlibs[imlib_publicimageX])){
              jtp.add(imlibs[imlib_publicimageX], u.uScrollPane(full_4)); 
              break;
          }
      }
      
      panel2.add(jtp,grid1);
      
      
      
      
      
      
      panel3.add(panel1,grid1);
      grid1.weighty = 1;
      grid1.fill = GridBagConstraints.NONE;
      JPanel cnPan = new JPanel(new GridBagLayout());
      cbShowUnderscores = new JCheckBox("Include underscore images");
      cbShowUnderscores.setSelected(bWantUnderscores);
      cbShowUnderscores.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
            bWantUnderscores = cbShowUnderscores.isSelected();
          int i = -1;
          if(rb_1.isSelected()) { 
              i = 0;
          }
          else if(rb_2.isSelected()) {
              i = 1;
          }
          else if(rb_3.isSelected()) {
              i = 2;
          }
          
          doRBChange(i);
        }
      });      
      
      
      cnPan.add(cbShowUnderscores, grid1);
      
      panel3.add(cnPan,grid1);
      
      grid1.fill = GridBagConstraints.BOTH;
      rb_1.setSelected(true);
      grid1.weightx = grid1.weighty = 1;
      grid1.gridheight = 1;
      grid1.gridy = 0;
      grid1.gridx = -1;
       panel2.setPreferredSize(new Dimension(sharkStartFrame.screenSize.width*7/20,sharkStartFrame.screenSize.height));
       panel3.setPreferredSize(new Dimension(sharkStartFrame.screenSize.width*6/20,sharkStartFrame.screenSize.height));
       panel4.setPreferredSize(new Dimension(sharkStartFrame.screenSize.width*7/20,sharkStartFrame.screenSize.height));
 //      oldpatt.setPreferredSize(new Dimension(sharkStartFrame.screenSize.width/3,sharkStartFrame.screenSize.height/16));
 //      newpatt.setPreferredSize(new Dimension(sharkStartFrame.screenSize.width/3,sharkStartFrame.screenSize.height/16));
 //      oldpatt.setMinimumSize(new Dimension(sharkStartFrame.screenSize.width/3,sharkStartFrame.screenSize.height/16));
 //      newpatt.setMinimumSize(new Dimension(sharkStartFrame.screenSize.width/3,sharkStartFrame.screenSize.height/16));
      add( panel2,grid1);
      add( panel3,grid1);
      add( panel4,grid1);
      grid1.fill = GridBagConstraints.NONE;
      grid1.gridx=0;
      grid1.gridy = -1;

      JPanel tPan = new JPanel(new GridBagLayout());
      
      grid1.fill = GridBagConstraints.BOTH;
      panel4.add(tPan, grid1);
      grid1.fill = GridBagConstraints.NONE;
      
     // maintitle.setText("Image");
   //  tPan.add(maintitle,grid1);
      
      panel5.add(wordtitle,grid1);
      grid1.fill = GridBagConstraints.HORIZONTAL;
      grid1.weightx = 1;  
      grid1.insets = new Insets(0,10,0,10);
//startPR2004-09-01^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      
   //   panel5.add(panel4, grid1);
   //   panel5.setBorder(BorderFactory.createEtchedBorder(Color.orange, Color.red));



    JPanel border = new JPanel(new BorderLayout());
//startPR2008-06-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    border.setBorder(BorderFactory.createLineBorder(im.getcolor(0).darker(),2));
    border.setBorder(BorderFactory.createLineBorder(Color.lightGray,2));
//endPRimagesandvids^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    border.add(mainPanel, BorderLayout.CENTER);
    mainPanel.setBackground(Color.white);
    
      mainPanel.setPreferredSize(new Dimension(sharkStartFrame.screenSize.width*5/20,sharkStartFrame.screenSize.height/5));
      mainPanel.setMaximumSize(new Dimension(sharkStartFrame.screenSize.width*5/20,sharkStartFrame.screenSize.height/5));
      mainPanel.setMinimumSize(new Dimension(sharkStartFrame.screenSize.width*5/20,sharkStartFrame.screenSize.height/5));
    mainPanel.dontrun = true;

 mainPanel.setBorder(BorderFactory.createLineBorder(Color.lightGray,2));
    
      
      
      grid1.fill = GridBagConstraints.NONE;
      grid1.insets = new Insets(0,0,0,0);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR

      grid1.fill = GridBagConstraints.BOTH;
       grid1.weighty = 1;
      tPan.add(mainPanel,grid1);
      grid1.fill = GridBagConstraints.NONE;

grid1.weighty = 0;
grid1.fill = GridBagConstraints.BOTH;
grid1.insets = new Insets(40,0,10,0);
grid1.weighty = 1;
      tPan.add( new JScrollPane(  jlFindTopic),grid1);
      grid1.weighty = 0;
      jlFindTopic.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      jlFindTopic.setPreferredSize(new Dimension(sharkStartFrame.screenSize.width*5/20,sharkStartFrame.screenSize.height/5));
      jlFindTopic.setMaximumSize(new Dimension(sharkStartFrame.screenSize.width*5/20,sharkStartFrame.screenSize.height/5));
      jlFindTopic.setMinimumSize(new Dimension(sharkStartFrame.screenSize.width*5/20,sharkStartFrame.screenSize.height/5));
      grid1.fill = GridBagConstraints.NONE;
      whattopic.setEnabled(false);
      grid1.insets = new Insets(0,0,0,0);
      tPan.add(whattopic,grid1);
      grid1.insets = new Insets(40,0,40,0);
      tPan.add(exitbutton,grid1);
grid1.insets = new Insets(0,0,0,0);
      JPanel boo = new JPanel(new GridBagLayout());
      JPanel pnoldpatt = new JPanel(new GridBagLayout());
      JPanel pnnewpatt = new JPanel(new GridBagLayout());

        grid1.gridx=-1;
        grid1.gridy = 0;
        grid1.fill = GridBagConstraints.BOTH;
        grid1.weightx = 0;
        pnoldpatt.add(listen_curr, grid1);
        grid1.weightx = 1;

        
        grid1.weightx = 0;
        pnnewpatt.add(listen_proposed, grid1);
        grid1.weightx = 1;


grid1.fill = GridBagConstraints.NONE;
        grid1.gridx=0;
        grid1.gridy = -1;
      grid1.insets = new Insets(0,10,0,10);

      grid1.fill = GridBagConstraints.NONE;
      grid1.gridx=-1;
      grid1.gridy = 0;

      grid1.fill = GridBagConstraints.HORIZONTAL;
grid1.insets = new Insets(0,0,0,0);

      boo.setBorder(BorderFactory.createLineBorder(Color.black,2));


      int  buttondim = (sharkStartFrame.mainFrame.getWidth()*14/22)/24;
      int buttonimdim =  buttondim- (buttondim/5);
      listen_curr.setPreferredSize(new Dimension(buttondim, buttondim));
      listen_curr.setMinimumSize(new Dimension(buttondim, buttondim));
      listen_proposed.setPreferredSize(new Dimension(buttondim, buttondim));
      listen_proposed.setMinimumSize(new Dimension(buttondim, buttondim));



      whattopic.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(ActionEvent e) {
          Object o = jlFindTopic.getSelectedValue();
          if(o==null)return;
          String s = (String)o;
          sharkStartFrame.mainFrame.PublicTopics.doClick();
          jnode node = sharkStartFrame.mainFrame.topicTreeList.findTopic(s, true);
          if(node != null) {
            TreePath tp;
            sharkStartFrame.mainFrame.topicTreeList.setSelectionPath(tp = new TreePath(node.getPath()));
            sharkStartFrame.mainFrame.topicTreeList.scrollPathToVisible(tp);
          }

         }
      });
      exitbutton.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(ActionEvent e) {
            sharkStartFrame.mainFrame.setupgames();
         }
      });
/*
      full_1.addMouseListener(new MouseAdapter() {
         public void mouseReleased(MouseEvent e) {
            selectedlist = full_1;
            wordfromlist1();
         }
      });
      already.addMouseListener(new MouseAdapter() {
         public void mouseReleased(MouseEvent e) {
            selectedlist = already;
            wordfromlist2();
        }
      });
      notyet_1.addMouseListener(new MouseAdapter() {
         public void mouseReleased(MouseEvent e) {
            selectedlist = notyet_1;
           wordfromlist3();
        }
      });*/
      extra.addMouseListener(new MouseAdapter() {
         public void mouseReleased(MouseEvent e) {
            selectedlist = extra;
            wordfromlist4();
        }
      });
//startPR2009-02-25^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      phonicsounds.addMouseListener(new MouseAdapter() {
        public void mouseReleased(MouseEvent e) {
          selectedlist = phonicsounds;
          wordfromlist5();
        }
      });
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2004-09-01^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        // enables exiting screen via the ESC key
      /*
        selectedlist3.addKeyListener(new KeyAdapter() {
          public void keyPressed(KeyEvent e) {
            int code = e.getKeyCode();
            if(code == KeyEvent.VK_ESCAPE)
              sharkStartFrame.mainFrame.setupgames();
          }
        });
        full_1.addKeyListener(new KeyAdapter() {
          public void keyPressed(KeyEvent e) {
            int code = e.getKeyCode();
            if(code == KeyEvent.VK_ESCAPE)
              sharkStartFrame.mainFrame.setupgames();
          }
        });
        already.addKeyListener(new KeyAdapter() {
          public void keyPressed(KeyEvent e) {
            int code = e.getKeyCode();
            if(code == KeyEvent.VK_ESCAPE)
              sharkStartFrame.mainFrame.setupgames();
          }
        });
        notyet_1.addKeyListener(new KeyAdapter() {
          public void keyPressed(KeyEvent e) {
            int code = e.getKeyCode();
            if(code == KeyEvent.VK_ESCAPE)
              sharkStartFrame.mainFrame.setupgames();
          }
        });
        extra.addKeyListener(new KeyAdapter() {
          public void keyPressed(KeyEvent e) {
            int code = e.getKeyCode();
            if(code == KeyEvent.VK_ESCAPE)
              sharkStartFrame.mainFrame.setupgames();
          }
        });*/
//startPR2009-02-25^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        phonicsounds.addKeyListener(new KeyAdapter() {
          public void keyPressed(KeyEvent e) {
            int code = e.getKeyCode();
            if(code == KeyEvent.VK_ESCAPE)
              sharkStartFrame.mainFrame.setupgames();
          }
        });
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        /*
        rb_2.addKeyListener(new KeyAdapter() {
          public void keyPressed(KeyEvent e) {
            int code = e.getKeyCode();
            if(code == KeyEvent.VK_ESCAPE)
              sharkStartFrame.mainFrame.setupgames();
          }
        });
        rb_1.addKeyListener(new KeyAdapter() {
          public void keyPressed(KeyEvent e) {
            int code = e.getKeyCode();
            if(code == KeyEvent.VK_ESCAPE)
              sharkStartFrame.mainFrame.setupgames();
          }
        });
        rb_3.addKeyListener(new KeyAdapter() {
          public void keyPressed(KeyEvent e) {
            int code = e.getKeyCode();
            if(code == KeyEvent.VK_ESCAPE)
              sharkStartFrame.mainFrame.setupgames();
          }
        });
 */
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      changingword = false;
      validate();
//startPR2004-09-01^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       // enables exiting screen via the ESC key
       requestFocus();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      
      return;
   }
    //------------------------------------------------------
  void setupFindTopicList(){
      String[] s= new String[0];
      
      
      
      String ss =  getTabString();
      if(ss.equals(imlibs[imlib_publicimage])){
        currword = (String)full_1.getSelectedValue();
      }
      else if(ss.equals(imlibs[imlib_publicimageWORDS])){
        currword = (String)full_2.getSelectedValue();    
      }     
      else if(ss.equals(imlibs[imlib_publicimageSENT])){
        currword = (String)full_3.getSelectedValue();     
      }
      else if(ss.equals(imlibs[imlib_publicimageX])){
        currword = (String)full_4.getSelectedValue();     
      }
      for(int i=0;i<topicwords.length;++i) {
        if(u.findString(topicwords[i],currword) >= 0)
            s = u.addString(s,topicList[i]);
      }
      jlFindTopic.setListData(s);
      if(s.length>0){
          jlFindTopic.setSelectedIndex(0);
      }
      whattopic.setEnabled(s.length>0);
//      if(s.length == 0)
//         u.okmess(u.gettext("rec_","listtopicsh"),u.gettext("rec_","listtopicsn"), sharkStartFrame.mainFrame);
//      else showlist(u.gettext("rec_","listtopicsh"),s);
  }
  
  
  void setupword(String s) {
      setupword(s, true);
  }
  
  void showImage(sharkImage im){
      mainPanel.removeAllMovers();
      if(im==null)return;
      
     im.w = mover.WIDTH;
    im.h = mover.HEIGHT;
     int w1 = mainPanel.getWidth();
     int h1 = mainPanel.getHeight();
     im.adjustSize(w1,h1);
    im.w = mover.WIDTH;
    im.h = mover.HEIGHT;
    mainPanel.addMover(im,0,0);
mainPanel.start1();
  }
  
  
  
  String[] findDuplicateImages(){
      String[] dups = new String[]{};
      allwordImages = images_1.clone();
      for(int i = 0; i < images_2.length; i++){
          if(u.findString(allwordImages, images_2[i])>=0)
              if(u.findString(dups, images_2[i])<0)
                   dups = u.addStringSort(dups, images_2[i]);
          if(u.findString(allwordImages, images_2[i])<0)
             allwordImages = u.addStringSort(allwordImages, images_2[i]);
      }
      for(int i = 0; i < images_3.length; i++){
          if(u.findString(allwordImages, images_3[i])>=0)
              if(u.findString(dups, images_3[i])<0)
                   dups = u.addStringSort(dups, images_3[i]);
          if(u.findString(allwordImages, images_3[i])<0)
             allwordImages = u.addStringSort(allwordImages, images_3[i]);
      }    
      return dups;
  }

   void setupword(String s, boolean settext) {
 //      if(s.equals(lasttext))return;
       

      if(currSpokenWord != null) {
         spokenWord.flushspeaker(true);
         currSpokenWord = null;
      }
      currSpokenWord = new spokenWord(s,definitions2?sharkStartFrame.sharedPathplus + soundDBName: soundDBName ,false);
      isOldWord =  (db.query(soundDBName,s,db.WAV) >= 0);
      changingword = true;
//startPR2004-09-01^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        // if running on Windows - the JTextField automatically sizes to selected item
 //       if (!shark.macOS) {
 //         recordName.setColumns(0);
  //      }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2004-09-01^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          // if running on a Macintosh
 //         if (shark.macOS) {
 //           if (s.length() > MACMAXNAMELEN) {
 //             recordName.setText(s.substring(0, MACMAXNAMELEN) + "...");
 //           }
 //           else {

 //           }
 //         }
 //         else{
  //          if (s.length() > MAXNAMELEN) {
  //            recordName.setText(s.substring(0, MAXNAMELEN) + "...");
  //          }
  //          else {
  //            recordName.setText(s);
  //          }
  //        }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      changingword = false;
      isNewWord = false;
      currword = s;

      if(isOldWord) {
         currSpokenWord.decomp();
         currSpokenWord.say();

         if(definitions) {
           String extra = (String)db.find(soundDBName,s,db.TEXT);
           if(extra != null) {
               extraInfo.setText(extra);
           }
           else extraInfo.setText("");
         }
      }
      else {
        if(definitions) extraInfo.setText("");

      }
      setsel(already,alreadywords);
      setnotyet2();
//startPR2009-02-25^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      setsel(selectedlist3,selectedlist3==notyet?notyetwords2:(selectedlist3==full?words:extrawords));
      setsel(selectedlist3, (selectedlist3==phonicsounds)?phonicsoundswords:   (   selectedlist3==notyet_1?notyetwords2:(selectedlist3==full_1?images_1:extrawords)     )  );
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      setButtons();
   }
   //---------------------------------------------------------------
   void setsel(JList jj,String[] li) {
                int sel;
                if(currword != null && currword.length()>0
                   && (sel = u.findString(li,currword))>=0) {
                    jj.setSelectedIndex(sel);
                    jj.scrollRectToVisible(jj.getCellBounds(sel,sel));
                }
                else jj.clearSelection();
   }
   //------------------------------------------------------
   void setButtons() {
       String s = getTabString();
       if(s!=null){
        if(s.equals(imlibs[imlib_publicimage])){
          whattopic.setEnabled(full_1.getSelectedIndex()>=0);          
        }
        else if(s.equals(imlibs[imlib_publicimageWORDS])){
            whattopic.setEnabled(full_2.getSelectedIndex()>=0);      
        }
        else if(s.equals(imlibs[imlib_publicimageSENT])){
            whattopic.setEnabled(full_3.getSelectedIndex()>=0);      
        }
        else if(s.equals(imlibs[imlib_publicimageX])){
            whattopic.setEnabled(full_4.getSelectedIndex()>=0);      
        }
       }



  //    listen.setEnabled(isOldWord | isNewWord);   //enabled

      int sel = full_1.getSelectedIndex();
      if(sel >= 0) {
         full_1.ensureIndexIsVisible(sel);
      }
      else {
         sel = already.getSelectedIndex();
         if(sel >= 0) {
            already.ensureIndexIsVisible(sel);
         }
         else {
            sel = notyet_1.getSelectedIndex();
            if(sel >= 0) {
               notyet_1.ensureIndexIsVisible(sel);
            }
         }
      }
   }
   //------------------------------------------------------
   public void clearButtons() {
       String s =  getTabString();
       if(s!=null){
        if(s.equals(imlibs[imlib_publicimage])){
          whattopic.setEnabled(full_1.getSelectedIndex()>=0);          
        }
        else        if(s.equals(imlibs[imlib_publicimageWORDS])){
            whattopic.setEnabled(full_2.getSelectedIndex()>=0);      
        }
        else        if(s.equals(imlibs[imlib_publicimageSENT])){
            whattopic.setEnabled(full_3.getSelectedIndex()>=0);      
        }
        else        if(s.equals(imlibs[imlib_publicimageX])){
            whattopic.setEnabled(full_4.getSelectedIndex()>=0);      
        }
       }



  }
     //-----------------------------------------------------
   void addimage(String s,int topicnum, String imageLib) {       
      if(imageLib.equals(imlibs[imlib_publicimage])){
        images_1 = u.addStringSort(images_1,s); 
        if(topicnum>=0) 
            topicwords[topicnum] = u.addString(topicwords[topicnum],s);
      }
      else if(imageLib.equals(imlibs[imlib_publicimageWORDS])){       
        images_2 = u.addStringSort(images_2,s);
        if(topicnum>=0) 
            topicwords[topicnum] = u.addString(topicwords[topicnum],s);          
      }
      else if(imageLib.equals(imlibs[imlib_publicimageSENT])){
        images_3 = u.addStringSort(images_3,s);
        if(topicnum>=0) 
            topicwords[topicnum] = u.addString(topicwords[topicnum],s);          
      }
      else if(imageLib.equals(imlibs[imlib_publicimageX])){
        images_4 = u.addStringSort(images_4,s);
        if(topicnum>=0) 
            topicwords[topicnum] = u.addString(topicwords[topicnum],s);          
      }
   }
   
   int imageLibStringToInt(String imageLib){
       if(imageLib.equals(imlibs[imlib_publicimage])){
        return 0;
      }
      else if(imageLib.equals(imlibs[imlib_publicimageSENT])){
        return 1;          
      }
      else if(imageLib.equals(imlibs[imlib_publicimageWORDS])){
        return 2;         
      }
      else if(imageLib.equals(imlibs[imlib_publicimageX])){
        return 3;         
      } 
       return -1;
   }
   
   String getTabString(){
       int i  = jtp.getSelectedIndex();
       if(i == 0){
        return imlibs[imlib_publicimage];
      }
      else if(i == 1){
        return imlibs[imlib_publicimageWORDS];        
      } 
      else if(i == 2){
        return imlibs[imlib_publicimageSENT];        
      }
      else if(i == 3){
        return imlibs[imlib_publicimageX];        
      }
       return null;
   }   
   
   
   void addimagenotyet(String s) {
     //  if(ss.equals(imlibs[imlib_publicimage])){
          if(u.findString(notyetwords_1, s)<0)
            notyetwords_1 = u.addStringSort(notyetwords_1,s); 
      // }
   //    else if(ss.equals(imlibs[imlib_publicimageWORDS])){
  //        if(u.findString(notyetwords_2, s)<0)
   //         notyetwords_2 = u.addStringSort(notyetwords_2,s); 
    //   }
     //  else if(ss.equals(imlibs[imlib_publicimageSENT])){
      //    if(u.findString(notyetwords_3, s)<0)
      //      notyetwords_3 = u.addStringSort(notyetwords_3,s); 
     ///  }
   }   
   
   
   
   //--------------------------------------------------------------------
   public void save() {
//      db.closeAll();
   }
   //-----------------------------------------------------------------
   void wordfromlist1() {
      int sel = full_1.getSelectedIndex();
      if(sel >= 0) {
          setupword(images_1[sel]);
      }
   }
   //-----------------------------------------------------------------
   void wordfromlist2() {
      int sel= already.getSelectedIndex();
      if(sel >=0) {
        if(definitions2) {
          int i;
          String deflist[] = db.dblist(sharkStartFrame.sharedPath, sharkStartFrame.definitions);
          for (i = 0; i < deflist.length; ++i) {
             if (db.query(sharkStartFrame.sharedPathplus + deflist[i], alreadywords[sel], db.WAV)>=0) {
                 soundDBName = deflist[i];
                 defname.setText(soundDBName);
                 break;
             }
          }
        }
        setupword(alreadywords[sel]);
       }
   }
   //-----------------------------------------------------------------
   void wordfromlist3() {
      int sel= notyet_1.getSelectedIndex();
      if(sel >=0) {
          setupword(notyetwords_1[sel]);
      }
   }
   //-----------------------------------------------------------------
   void wordfromlist4() {
      int sel= extra.getSelectedIndex();
      if(sel >=0) {
          setupword(extrawords[sel]);
       }
   }
//startPR2009-02-25^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    void wordfromlist5() {
      int sel= phonicsounds.getSelectedIndex();
      if(sel >=0) {
        setupword(phonicsoundswords[sel]);
      }
    }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   //-----------------------------------------------------------------
   boolean isnextwordfromlist() {
      int sel;
      if(selectedlist==full_1) {
         sel = full_1.getSelectedIndex();
         if(sel >= 0 && sel < images_1.length-1) return true;
      }
      else if(selectedlist==already) {
         sel = already.getSelectedIndex();
         if(sel >= 0) {
            if(sel < alreadywords.length
               && currword != null
               &&   !alreadywords[sel].equalsIgnoreCase(currword)) {
                return true;
            }
            if(sel < alreadywords.length-1) return true;
         }
         else if(selectedlist==notyet_1){
            sel = notyet_1.getSelectedIndex();
            if(sel >= 0) {
               if(sel <notyetwords_1.length
                  && currword != null
                  && !notyetwords_1[sel].equalsIgnoreCase(currword)){
                        return true;
               }
               if(sel < notyetwords_1.length-1) return true;
            }
         }
//startPR2009-02-25^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//         else {
//            sel = extra.getSelectedIndex();
//            if(sel >= 0) {
//               if(sel <extrawords.length
//                  && currword != null
//                  && !extrawords[sel].equalsIgnoreCase(currword)){
//                        return true;
//               }
//               if(sel < extrawords.length-1) return true;
//            }
//         }
         else if(selectedlist==extra) {
           sel = extra.getSelectedIndex();
           if(sel >= 0) {
             if(sel <extrawords.length
                && currword != null
                && !extrawords[sel].equalsIgnoreCase(currword)){
               return true;
             }
             if(sel < extrawords.length-1) return true;
           }
         }
         else {
           sel = extra.getSelectedIndex();
           if(sel >= 0) {
             if(sel <phonicsoundswords.length
                && currword != null
                && !phonicsoundswords[sel].equalsIgnoreCase(currword)){
               return true;
             }
             if(sel < phonicsoundswords.length-1) return true;
           }
         }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      }
      return false;
   }
   //-------------------------------------------------------------------
   void setnotyet2() {
      if(ispublic)   {notyetwords2=notyetwords_1;return;}
      notyetwords2 = new String[notyetwords_1.length];
      for(int i=0;i<notyetwords_1.length;++i) {
        notyetwords2[i] = notyetwords_1[i];
        if(db.query(sharkStartFrame.getPrimarySoundDb(sharkStartFrame.publicSoundLib),notyetwords_1[i],db.WAV) >= 0 || db.query(sharkStartFrame.getPrimarySoundDb(sharkStartFrame.publicSoundLib),notyetwords_1[i]+"~",db.WAV) >= 0)
               notyetwords2[i] += inpub;
      }
   }
   //-----------------------------------------------------------------
   boolean nextwordfromlist() {
      int sel;
      if(selectedlist==full_1) {
         sel = full_1.getSelectedIndex();
         if(sel < images_1.length-1) {
            full_1.setSelectedIndex(sel+1);
            setupword(images_1[sel+1]);
            return true;
         }
      }
      else if(selectedlist==already) {
         sel = already.getSelectedIndex();
         if(sel >=0) {
            if(sel < alreadywords.length
               && currword != null
               &&   !alreadywords[sel].equalsIgnoreCase(currword)){
                setupword(alreadywords[sel]);
                return true;
            }
            if(sel < alreadywords.length-1) {
               already.setSelectedIndex(sel+1);
               setupword(alreadywords[sel+1]);
               return true;
            }
         }
      }
      else if(selectedlist==notyet_1) {
            sel = notyet_1.getSelectedIndex();
            if(sel >=0) {
               if(sel <notyetwords_1.length
                  && currword != null
                  && !notyetwords_1[sel].equalsIgnoreCase(currword)){
                   setupword(notyetwords_1[sel]);
                   return true;
               }
               if(sel < notyetwords_1.length-1) {
                  notyet_1.setSelectedIndex(sel+1);
                  setupword(notyetwords_1[sel+1]);
                  return true;
               }
            }
      }
      return false;
   }
   //------------------------------------------------------------------
   void deleteword() {
      db.delete(soundDBName,currword,db.WAV);
      already.setListData(alreadywords=u.removeString(alreadywords,currword));
      extra.setListData(extrawords=u.removeString(extrawords,currword));
//startPR2009-02-25^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      phonicsounds.setListData(phonicsoundswords=u.removeString(phonicsoundswords,currword));
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      if(u.query(images_1,currword)) {
        notyetwords_1=u.addStringSort(notyetwords_1,currword);
        setnotyet2();
        notyet_1.setListData(notyetwords2);
      }
      already.clearSelection();
      selectedlist3.clearSelection();
   }
   //------------------------------------------------------------------
   void savenewword(String currword) {
      already.setListData(alreadywords=u.addStringSort(alreadywords,currword));
      if(u.query(notyetwords_1,currword)) {
         int sel= notyet_1.getSelectedIndex();
         int old = u.findString(notyetwords_1,currword);
         notyetwords_1 = u.removeString(notyetwords_1,currword);
         setnotyet2();
         notyet_1.setListData(notyetwords2);
         if(sel >=0) {
            if (old >sel) --old;
            notyet_1.setSelectedIndex(old);
         }
      }
      if(!u.query(images_1,currword)) {
         extra.setListData(extrawords=u.addStringSort(extrawords,currword));
      }
   }

   //-----------------------------------------------------------
 void showlist(String title,String list[]) {new showlist(title,list);}

 class showlist
//startPR2004-08-27^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     extends JDialog {
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR

     JList toplist;
     JDialog thisd;
     
   public  showlist(String title,String list[]) {
       thisd = this;
//startPR2004-08-27^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    this.setResizable(false);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
//    setBounds(new Rectangle(sharkStartFrame.screenSize.width/3,
//                            sharkStartFrame.screenSize.height/4,
//                            sharkStartFrame.screenSize.width/3,
//                            sharkStartFrame.screenSize.height/2));
    setBounds(u2_base.adjustBounds(new Rectangle(sharkStartFrame.screenSize.width/3,
                            sharkStartFrame.screenSize.height/4,
                            sharkStartFrame.screenSize.width/3,
                            sharkStartFrame.screenSize.height/2)));
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

                          // start rb 8/2/06
   class notyetpainter extends JLabel implements ListCellRenderer {  // for student list
      notyetpainter() {setOpaque(true);}
      Object o;
      FontMetrics m;
      public Component getListCellRendererComponent( JList list,Object oo,
            int index,boolean isSelected,boolean cellhasFocus) {
         o = oo;
         this.setBackground(isSelected?list.getSelectionBackground():Color.white);
         setText((String)o);
         return this;
      }
      public void paint(Graphics g) {
            Rectangle r = getBounds();
            String s2 = null;
            if(m==null) {
              m = getFontMetrics(getFont());
            }
            g.setFont(getFont());
            g.setColor(getBackground());
            g.fillRect(0,0,r.width,r.height);
            g.setColor(getForeground());
            String s = (String) o;

            int i, curr = 0;
            if((i=s.indexOf("<<"))>=0)   {
              s2 = s.substring(i+2);
              s = s.substring(0,i);
            }
            g.setColor(getForeground());
            g.drawString(s,0,r.height/2 - m.getHeight()/2 + m.getAscent());
            curr = m.stringWidth(s+"  ");
            if(s.endsWith("~")) {
               g.setColor(Color.red);
               g.drawString(phonics,curr,r.height/2 - m.getHeight()/2 + m.getAscent());
               curr += m.stringWidth(phonics+"  ");
            }
            if(s2 != null) {
              g.setColor(Color.blue);
              g.drawString(s2, curr,r.height/2 - m.getHeight()/2 + m.getAscent());
            }
       }
   }

    class keydoc extends PlainDocument {
     JTextField thisTextField;
     
     keydoc(JTextField ow) {
       super();
       thisTextField = ow;
     }
     public void insertUpdate(AbstractDocument.DefaultDocumentEvent chng, AttributeSet attr) {
         if(changingword) return;
         setupword(thisTextField.getText(),false);
     }
     public void removeUpdate(AbstractDocument.DefaultDocumentEvent chng) {
         if(changingword) return;
         setupword(thisTextField.getText(),false);
     }
   }

}

