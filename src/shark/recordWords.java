package shark;

import javax.swing.tree.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import java.lang.ref.SoftReference.*;
import java.io.File;
import javax.swing.event.*;
import javax.swing.text.*;

public class recordWords  extends JPanel implements Runnable {
    
    
   static final boolean PUBLIC_SENT1_REDUCE_TO_JUST_HELIPATTERN_GAMES = false;
    
   static final short MAXNAMELEN = 35;
//startPR2004-09-01^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    /**
     * The maximum length of name of sound that can be displayed in the JTextField
     * when running on a Macintosh
     */
    static final short MACMAXNAMELEN = 28;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   static final short MAXNAMELEN2 = 20;

   String wordDBName, soundDBName;
   byte savedwords;
   String words[] = new String[0];
   String alreadywords[] = new String[0];
   String notyetwords[] = new String[0];
   String notyetwords2[] = new String[0];
   String extrawords[] = new String[0];
//startPR2009-02-25^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   String phonicsoundswords[] = new String[0];
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   JList full= new JList(),already =new JList(),notyet =new JList(),extra =new JList();
//startPR2009-02-25^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   JList phonicsounds= new JList();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   JList selectedlist,selectedlist3;
   int wordtot;
   String currword,prevword;
   boolean changingword, doingRecording;
   spokenWord currSpokenWord;
   JTextField recordName = new JTextField();
   JTextField defname;
   JTextField extraInfo;
   JLabel maintitle = u.label("rec_main");
   JLabel wordtitle = u.label("rec_curr");
   JButton startRecording = u.Button("rec_start");
   JCheckBox autorec = u.CheckBox("rec_auto");
   String startText1 = startRecording.getText();
   String startText2 = u.gettext("rec_start","label2");
   String startText3 = u.gettext("rec_start","label3");
   String startText4 = u.gettext("rec_start","label4");
   String startText5 = u.gettext("rec_start","label5");
//   JButton listen = u.Button("rec_listen");
   JButton listen_curr = u.sharkButton();
   JButton listen_proposed = u.sharkButton();
   JButton ignore = u.Button("rec_ignore");
   String ignoreText1 = ignore.getText();
   String ignoreText2 = u.gettext("rec_ignore","label2");
   JButton save = u.Button("rec_save");
   String saveText1 = save.getText();
   String saveText2 = u.gettext("rec_save","label2");
   JButton prevWord2 = u.Button("rec_prevsaved");
   JButton deleteWord = u.Button("rec_del");
   JButton whattopic = u.Button("rec_topic");
   String deleteText = deleteWord.getText();
   JButton exitbutton = u.Button("rec_exit");
   JButton beepbutton = u.Button("rec_beep");
   JButton rembeepbutton = u.Button("rec_rembeep");
   JLabel oldsound = u.label("rec_oldsound");
   JLabel newsound = u.label("rec_newsound");
   String inpub = "<<"+u.gettext("rec_","inpub");
   String phonics = u.gettext("rec_","phonics");
   soundwave oldpatt = new soundwave();
   soundwave newpatt = new soundwave();
   boolean isNewWord,isOldWord,definitions,definitions2,sentences;
   String oldtitle;
   JPanel panel1 = new JPanel(new BorderLayout());
   JPanel panel1a = new JPanel(new GridBagLayout());
   JPanel panel2 = new JPanel();
   JPanel panel3 = new JPanel();
   JPanel panel4 = new JPanel();
   JPanel panel5 = new JPanel();
   JPanel panel6;

   JLabel label2 = u.label("rec_already");

   GridBagLayout gridBagLayout0 = new GridBagLayout();
   GridBagLayout gridBagLayout1 = new GridBagLayout();
   GridBagLayout gridBagLayout2 = new GridBagLayout();
   GridBagLayout gridBagLayout3 = new GridBagLayout();
   GridBagLayout gridBagLayout4 = new GridBagLayout();
   GridBagLayout gridBagLayout5 = new GridBagLayout();
   GridBagConstraints grid1 = new GridBagConstraints();
   String[] topicList;
   String[][] topicwords;
   ButtonGroup bg = new ButtonGroup();
   JSlider  booster;
   mlabel_base boosterlabel = new mlabel_base("xx");
   static int boostervals[] = new int[] {50,75,100,125,150,200,300,400,500};
   boolean ispublic,sent1,sent3,sent4,auto, say3;
   boolean wantUI;
//startPR2010-05-28^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//   boolean sent4;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  //----------------------------------------------------------
   ItemListener bglisten = new java.awt.event.ItemListener() {
       public void itemStateChanged(ItemEvent e) {
          int sel;
          if(buttonnotyet.isSelected()) {
             if(!panel1.isAncestorOf(notyet)) {
                selectedlist3 = notyet;
                panel1.removeAll();
                panel1.add(u.uScrollPane(notyet),BorderLayout.CENTER);
                panel1.validate();
                setsel(notyet,notyetwords);
             }
          }
          else if(buttonfull.isSelected()) {
             if(!panel1.isAncestorOf(full)) {
                selectedlist3 = full;
                panel1.removeAll();
                panel1.add(u.uScrollPane(full),BorderLayout.CENTER);
                panel1.validate();
                setsel(full,words);
 //               checkall();    /// special check of all
             }
          }
          else if(buttonextra.isSelected()) {
             if(!panel1.isAncestorOf(extra)) {
                 
       
                selectedlist3 = extra;
                panel1.removeAll();
                panel1.add(u.uScrollPane(extra),BorderLayout.CENTER);
                panel1.validate();
                setsel(extra,extrawords);
             
   /*
                for(int i =0; i < extrawords.length; i++){
                   if(extrawords[i].indexOf("_")>=0)continue;
                   db.delete(soundDBName, extrawords[i], db.WAV);
                }
                 */
//startPR2008-10-01^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                  // useful when stripping for the BDA DEMO
//                for(int i = 0; i < extra.getModel().getSize(); i++){
//                  String s = (String)extra.getModel().getElementAt(i);
//                 db.delete(soundDBName,s,db.WAV);
//                }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
             }
          }
//startPR2009-02-25^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          else if(buttonphonicsounds.isSelected()) {
            if(!panel1.isAncestorOf(phonicsounds)) {
              selectedlist3 = phonicsounds;
              panel1.removeAll();
              panel1.add(u.uScrollPane(phonicsounds),BorderLayout.CENTER);
              panel1.validate();
              setsel(phonicsounds,phonicsoundswords);
            }
          }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       }
   };
   void checkall() {
       int i;
       for(i=0;i<alreadywords.length;++i) {
           already.setSelectedIndex(i);
           already.scrollRectToVisible(already.getCellBounds(i,i));
           new word(alreadywords[i],sharkStartFrame.getPrimarySoundDb(sharkStartFrame.publicSoundLib)).say();
       }
   }
  //------------------------------------------------------------------
   JRadioButton buttonfull = u.RadioButton("rec_full",bglisten);
   JRadioButton buttonnotyet = u.RadioButton("rec_notyet",bglisten);
   JRadioButton buttonextra = u.RadioButton("rec_extra",bglisten);
//startPR2009-02-25^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   JRadioButton buttonphonicsounds = u.RadioButton("rec_phonicsounds",bglisten);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR

   public recordWords(String wordsfrom,byte defs, boolean doUI) {
       // def == 0    - publicsay1
       // def == 6    - publicsay3
       // def == 3    - publicsent1
       // def == 2    - publicsent2
       // def == 5    - publicsent3
       // def == 7    - publicsent4    // Wordshark Test sentences
       
       
       
      wantUI = doUI;
      spokenWord.currrecord = this;
      spokenWord.recording = false;
      spokenWord.autoboost = false;
      oldpatt.owner = this;
      if(defs == 1) definitions = true;
      else if(defs == 4) definitions = definitions2 = true;
//startPR2010-05-28^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      else if(defs == 2 || defs == 3 || defs == 5 || defs == 7) {
//      else if(defs == 2 || defs == 3 || defs == 5|| defs == 6) {
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         sentences = true;
      }
      if(defs==6)say3 = true;
      else if(defs==3) sent1=true;
      else if(defs==5) sent3=true;
      else if(defs==7) sent4=true;
      if(sentences || sent4){
         oldtitle = sharkStartFrame.mainFrame.getTitle();
         buttonfull.setText(u.gettext("rec_full","label2"));
         buttonnotyet.setText(u.gettext("rec_notyet","label2"));
         buttonextra.setText(u.gettext("rec_extra","label2"));
         label2.setText(u.gettext("rec_already","label2"));          
      }
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
      wordDBName = wordsfrom;
      getbooster();
      Thread runthread = new Thread(this);
      runthread.start();
//      setup();
//      validate();
   }
   //------------------------------------------------------
  public void run() {
      topicList = db.list(wordDBName,db.TOPIC);
      topicwords = new String[topicList.length][];
      ProgressMonitor pm = new ProgressMonitor(sharkStartFrame.mainFrame,u.gettext("rec_","scanningh"),u.gettext("rec_","scanning"),0,1000);
      soundDBName = wordDBName;
      if(definitions2) {
        soundDBName = sharkStartFrame.definitions+"1";
        ispublic = true;
      }
      else if(sent1) {
        soundDBName = "public" +( sentences?"sent1":(definitions?"def1":"say1"));
        ispublic = true;
        sent1 = true;
     }
     else if(sent3) {
       soundDBName = "publicsent3";
       ispublic = true;
    }
     else if(sent4) {
       soundDBName = "publicsent4";
       ispublic = true;
    }
//startPR2010-05-28^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//     else if(sent4) {
//       soundDBName = "public" + "sent4";
//       ispublic = true;
//    }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      else if((new File(soundDBName)).getName().equalsIgnoreCase(sharkTree.publictopics)) {
         soundDBName = "public" +( sentences?"sent2":(definitions?"def1":"say"));
         if(!sentences&&!definitions)
            soundDBName = soundDBName + (say3?"3":"1");
         ispublic = true;
      }
      else if(soundDBName.indexOf(sharkTree.publictopics) < 0) {
         buttonfull.setText(u.gettext("rec_full","label3"));
         buttonnotyet.setText(u.gettext("rec_notyet","label3"));
         buttonextra.setText(u.gettext("rec_extra","label3"));
      }
      if (!ispublic)  {
        notyet.setCellRenderer(new notyetpainter());
        full.setCellRenderer(new notyetpainter());
      }
      short sep,i,j,k;
      int pp;
      topic top;
      word w[];
//startPR2009-02-25^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      String singlephonicsounds[] = new String[]{};
      String sayname[] = new String[]{};
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      topicloop:for(i = 0; i < topicList.length; ++i) {
          if(topicList[i].equalsIgnoreCase("Magnetism & Electromagnets")){
              int g = 9;
              g =0;
          }
          if(topicList[i].equalsIgnoreCase("garbage")) continue;
            top = new topic(wordDBName, topicList[i], null, null);
            if(top == null) continue;
            if(!top.initfinished) top.getWords(null,false);
            
            if(sent4){
                String ss[] = top.getTestSentences(sentence.TEST_PREFIX);
                for(int ii = 0; ss!=null && ii < ss.length; ii++){
                    addword(ss[ii],i);
                }
                continue topicloop;
            }            
            w = top.getAllWordsAndSent(PUBLIC_SENT1_REDUCE_TO_JUST_HELIPATTERN_GAMES);
           if(say3 && !top.fl)
                continue;
           if(!sentences && top.fl && !say3)
                continue;
//           System.out.println("----------------------------------");
//           for(j=0;j<w.length;++j) {
//               System.out.println(w[j].value);
//           }
            for(j=0;j<w.length;++j) {
                if(!sent4 && w[j].value.startsWith(sentence.TEST_PREFIX))continue;
                if(w[j].value.indexOf("batch.")>=0){
                    int g; 
                    g  = 0;
                } 
                if(w[j].v().indexOf("b·ur·n·t=b·er·n·t")>=0){
                    int g; 
                    g  = 0;
                }       
                if(w[j].v().indexOf("gets a percentage on all it")>=0){
                    int g; 
                    g  = 0;
                }   
//startPR2010-05-28^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//                boolean simplesent = sentence.isSimpleSentence(w[j].value);
//                if(sent4 && sentence.isSentence(w[j].value) && simplesent){
//                    addword(w[j].value, i);
//                    continue;
//                }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2009-02-25^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
              if(!w[j].value.endsWith("~~") && w[j].value.endsWith("~")){
                if(w[j].value.indexOf("!")>=0)
                  singlephonicsounds = u.addStringSort(singlephonicsounds, w[j].value);
                else
                  sayname = u.addStringSort(sayname, w[j].value);
              }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
               if(w[j].bad) {      // used to show sentence
                  if(sentences || !ispublic) {
                     if(w[j].value.charAt(0) == ':' && w[j].value.charAt(1) != '=') {   // heading
                         if(sent1||!ispublic){
                             addword(w[j].value.substring(1),i);
                         }
                     }
                     else if( !sent1 && (w[j].value.indexOf('/')>=0 || w[j].value.indexOf('[')>=0 || w[j].value.indexOf('~')>=0)) {
                       if(sent3 && top.fl || !sent3 && !top.fl) addword(w[j].value, i);
//startPR2010-05-28^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//                       else if(sent4 && sentence.isSimpleSentence(w[j].value))
//                           addword(w[j].value, i);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//                       if((pp=db.query("publicsent1",w[j].value,db.WAV)) >= 0) {
//                           db.updatewav("publicsent2",w[j].value,
//                                        db.findwav("publicsent1",pp));
//                              db.delete("publicsent1",pp);
//                       }
                     }
                  }
                  if(!sentences && !definitions && w[j].value.charAt(0) != ':'){
            //       && !sentence.isSimpleSentence(w[j].value)) {
//                 ) {
                      
                    if(!sentence.isSimpleSentence(w[j].value)) {
                        String wds[] = (new sentence(w[j].value, w[j].database)).getWordList();
                        for(k=0;k<wds.length;++k){
                            addword(wds[k],i);
                        }
                    }
                    
                    sentence sent = new sentence(w[j].value,null);
                    for(int m = 0; m < sent.segs.length; m++){
                        String sssegs[] = sent.buildsellist(m);
                        for(int n = 0; sssegs.length > 1 && n < sssegs.length && !sssegs[n].trim().equals(""); n++){
                            addword(sssegs[n],i);
                        }                                
                    }
                    
                    String ret[] =  sent.getAnswerList();
                    if(ret!=null){
                        for(k=0;k<ret.length;++k){
                            addword(ret[k],i);
                        }                        
                    }
                  }
               }
               else if(!sentences){
                 if(w[j].value.endsWith("~~"))
                     addword(w[j].value,i);
                  else if(!sentences){
                    addword(w[j].vsay(),i);
                  }
               }
            }
            if(pm.isCanceled()) return;
            pm.setProgress(700*i/topicList.length);
      }
      int g;
      g = 0;
      if(!PUBLIC_SENT1_REDUCE_TO_JUST_HELIPATTERN_GAMES){
      if(sentences && sent1) {
         String ss[] = sharkImage.alltext("help_");
         for(i=0;i<ss.length;++i)
             addword(ss[i],-1);
      }
      if(ispublic && sentences && sent1) {
            String games[] = db.list(sharkStartFrame.mainFrame.publicGameLib[0],db.GAME);
            String values[],s2,s3,nn;
            int len,len2;
            for(j=0;j<games.length;++j) {
  //startPR2008-10-01^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
              // useful when stripping for the BDA DEMO
  //            sharkStartFrame.gameflag gf = new sharkStartFrame.gameflag(games[j]);
  //            if(gf.demoexclude) continue;
  //endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
              if((values = (String[])db.find(sharkStartFrame.mainFrame.publicGameLib[0],games[j],db.GAME)) != null) {
                for(k = 0;k<values.length;++k) {
                  len = (short)values[k].indexOf("=");
                  if(len >= 0) {
                      s2 = values[k].substring(len+1);
                      nn = values[k].substring(0,len);
                      if(nn.equalsIgnoreCase("tooltip")
                         || nn.equalsIgnoreCase("tooltiph")
                         || nn.equalsIgnoreCase("id")
                         || nn.equalsIgnoreCase("icon")
                         || nn.equalsIgnoreCase("options")
  //                       || nn.equalsIgnoreCase("icontitle")
  //                       || nn.equalsIgnoreCase("icontitle2")
                         || nn.equalsIgnoreCase("iconheading")
                         || nn.equalsIgnoreCase("iconrow")
                         || nn.equalsIgnoreCase("iconcol")
                         || nn.equalsIgnoreCase("title")
                             ) continue;
                      while(s2.length()>0 && u.lettersnumbers.indexOf(s2.charAt(0)) < 0)
                          s2 = s2.substring(1);
                      while((len2 = s2.indexOf('%')) >=0) {
                        s3 = u.stripspaces(s2.substring(0,len2));
                        if(s3.length() > 0) addword(s3,-1);
                        s2 = s2.substring(len2+1);
                        while(s2.length()>0 && u.lettersnumbers.indexOf(s2.charAt(0)) < 0)
                          s2 = s2.substring(1);
                      }
                      if(s2.length() > 0) addword(s2,-1);
                  }
               }
              }
            }
         }
      }
      wordtot = words.length;
      if(pm.isCanceled()) return;
      full.setListData(words);
      if(pm.isCanceled()) return;
      pm.setProgress(900);
      if(definitions2) {
        String deflist[] = db.dblist(sharkStartFrame.sharedPath,sharkStartFrame.definitions);
        if(deflist.length > 0) {
          for(i=0;i<deflist.length;++i)  {
            alreadywords = u.addString(alreadywords, db.list(sharkStartFrame.sharedPathplus+deflist[i], db.WAV));
          }
          Arrays.sort(alreadywords);
          already.setListData(alreadywords);
        }
      }
      else if(db.exists(soundDBName) && !PUBLIC_SENT1_REDUCE_TO_JUST_HELIPATTERN_GAMES) {
        alreadywords = db.list(soundDBName, db.WAV);
        if(!soundDBName.startsWith("public")) {
           for (i = 0; i < alreadywords.length; ++i) {
              if(alreadywords[i].startsWith("best_")) {alreadywords = u.removeString(alreadywords,i); --i;}
           }
        }
         already.setListData(alreadywords);
      }
      else db.get(sharkStartFrame.publicPathplus+soundDBName, true);
      

      if(wantUI){
      int comp;
      i=0;j=0;
      pm.setProgress(950);
      while(i<wordtot && j < alreadywords.length) {
         comp = words[i].toLowerCase().compareTo(alreadywords[j].toLowerCase());
         if(comp<0){
             notyetwords=u.addString(notyetwords,words[i++]);
         }
         else if(comp==0) {++i;++j;}
         else {
            String s = alreadywords[j];
            int len = s.length();
            if(len>0 && (s.charAt(len-1) != '='
                              || u.findString(alreadywords,s.substring(0,len-1))<0))
               extrawords = u.addString(extrawords,alreadywords[j]);
            ++j;
         }
      }
 
   
        if(soundDBName.equals("publicsay3")){
            for(int ii = notyetwords.length-1; ii >=0 ; ii--){
                if((notyetwords[ii].endsWith("~~") || notyetwords[ii].endsWith("~")) && db.query("publicsay1", notyetwords[ii], db.WAV)>=0)
                    notyetwords = u.removeString(notyetwords, ii);
            }
        }
      
      if(i<wordtot) {
         String ss[] = new String[notyetwords.length+wordtot-i];
         System.arraycopy(notyetwords,0,ss,0,notyetwords.length);
         System.arraycopy(words,i,ss,notyetwords.length,wordtot-i);
         notyetwords = ss;
      }
      else if(j<alreadywords.length) {
         String ss[] = new String[extrawords.length+alreadywords.length-j];
         System.arraycopy(extrawords,0,ss,0,extrawords.length);
         System.arraycopy(alreadywords,j,ss,extrawords.length,alreadywords.length-j);
         extrawords = ss;
      }
/*
      // remove recorded, not used words;
      for(int ii = 0; ii < extrawords.length; ii++){
              if(soundDBName.equals("publicsay1") && extrawords[ii].indexOf("hello")>=0 || extrawords[ii].indexOf("_")>=0){
                  continue;
              }
              db.delete(soundDBName, extrawords[ii], db.WAV);
              System.out.println("deleted unused:  "+extrawords[ii]);
      }      
*/
/*
      // fill to-be-recorded words with dummy recording;
      String skip[] = new String[] {"ar", "eu", "igh", "oe"};
      for(int ii = 0; ii < notyetwords.length; ii++){
          if(u.findString(skip, notyetwords[ii])>=0)
              continue;
          byte placeholder_sound[] = db.findwav("publicsent1", "placeholder_beep");
          db.updatewav(soundDBName, notyetwords[ii], placeholder_sound);
          System.out.println("added placeholder sound  "+notyetwords[ii]);
      } 
*/
      
      setnotyet2();
      notyet.setListData(notyetwords2);
      
      
      
      if(PUBLIC_SENT1_REDUCE_TO_JUST_HELIPATTERN_GAMES){
          String sent1[] = db.list(soundDBName, db.WAV);
          for(int ii = 0; ii < sent1.length; ii++){
              if(u.findString(words, sent1[ii])<0){
                  db.delete(soundDBName, sent1[ii], db.WAV);
                 System.out.println("deleted:  "+sent1[ii]);
              }
              else{
                  int f;
    //              System.out.println("saved:  "+sent1[ii]);
                   f = 0;
              }
          }         
          System.exit(0);
      }      
      
      
      
      
      
      extra.setListData(extrawords);
//startPR2009-02-25^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      phonicsoundswords = u.addString(sayname, singlephonicsounds);
      phonicsounds.setListData(phonicsoundswords);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      selectedlist3 = notyet;
      pm.close();
      full.clearSelection();
      already.clearSelection();
      notyet.clearSelection();
      extra.clearSelection();
//startPR2009-02-25^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      phonicsounds.clearSelection();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      bg.add(buttonnotyet);
      bg.add(buttonfull);
      bg.add(buttonextra);
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
      panel2.add(label2,grid1);
      grid1.fill = GridBagConstraints.BOTH;
      panel1a.add(u.label("rec_label1a"),grid1);
      Color c = label2.getForeground();
      buttonnotyet.setForeground(c);
      buttonfull.setForeground(c);
      buttonextra.setForeground(c);
//startPR2009-02-25^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      buttonphonicsounds.setForeground(c);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      panel1a.add(buttonnotyet,grid1);
      panel1a.add(buttonfull,grid1);
      panel1a.add(buttonextra,grid1);
//startPR2009-02-25^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      if(soundDBName.equals(sharkStartFrame.getPrimarySoundDb(sharkStartFrame.publicSoundLib)))
        panel1a.add(buttonphonicsounds,grid1);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      panel3.add(panel1a,grid1);
      grid1.gridheight = 15;
      grid1.weighty = 15;
      grid1.fill = GridBagConstraints.BOTH;
      panel2.add(u.uScrollPane(already),grid1);
      panel3.add(panel1,grid1);
      buttonnotyet.setSelected(true);
      grid1.weightx = grid1.weighty = 1;
      grid1.gridheight = 1;
      grid1.gridy = 0;
      grid1.gridx = -1;
       panel2.setPreferredSize(new Dimension(sharkStartFrame.screenSize.width/3,sharkStartFrame.screenSize.height));
       panel3.setPreferredSize(new Dimension(sharkStartFrame.screenSize.width/3,sharkStartFrame.screenSize.height));
       panel4.setPreferredSize(new Dimension(sharkStartFrame.screenSize.width/3,sharkStartFrame.screenSize.height));
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
      if(definitions2) {
        maintitle.setText(u.gettext("rec_","definitions2"));
        JPanel pti = new JPanel(new GridBagLayout());
        grid1.gridx=-1;
        grid1.gridy = 0;
        pti.add(maintitle,grid1);
        defname = new JTextField(soundDBName,18);
        defname.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
             String s = defname.getText();
             if(!s.startsWith(sharkStartFrame.definitions)) {
               u.okmess("rec_defnameerror");
               defname.setText(soundDBName);
             }
             else soundDBName = s;
          }
        });
        defname.addFocusListener(new FocusAdapter() {
         public void focusLost(FocusEvent e) {
             String s = defname.getText();
             if(!s.startsWith(sharkStartFrame.definitions)) {
               u.okmess("rec_defnameerror");
               defname.setText(soundDBName);
             }
             else soundDBName = s;
          }
        });
        defname.setColumns(18);
 //       defname.setMinimumSize(recordName.getPreferredSize());
        pti.add(defname,grid1);
        grid1.gridx=0;
        grid1.gridy = -1;
        panel4.add(pti,grid1);
      }
      else {
        maintitle.setText(u.edit(maintitle.getText(),
             sentences?u.gettext("rec_","sentences"):(definitions?u.gettext("rec_","definitions"):u.gettext("rec_","words")),
             soundDBName));
         panel4.add(maintitle,grid1);
      }
      panel5.add(wordtitle,grid1);
      grid1.fill = GridBagConstraints.HORIZONTAL;
      grid1.weightx = 1;  
      grid1.insets = new Insets(0,10,0,10);
//startPR2004-09-01^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        // if running on a Macintosh
        if (shark.macOS) {
          panel5.add(recordName, grid1);
          if(definitions) {
            extraInfo  = new JTextField();
            extraInfo.setColumns(18);
            panel6 = new JPanel(new GridBagLayout());
            JLabel exlab = u.label("rec_extrainfo");
            exlab.setForeground(Color.red);
            panel6.add(exlab, grid1);
            panel6.add(extraInfo, grid1);
          }
          wordtitle.setForeground(Color.red);
          grid1.fill = GridBagConstraints.HORIZONTAL;
          grid1.insets = new Insets(0,10,0,10);
          panel4.add(panel5, grid1);
          if(definitions)  panel4.add(panel6, grid1);
          grid1.fill = GridBagConstraints.NONE;
        }
        else{
          if(definitions) {
            extraInfo  = new JTextField();
            extraInfo.setColumns(30);
            panel6 = new JPanel(new GridBagLayout());
            panel6.setBorder(BorderFactory.createEtchedBorder(Color.orange, Color.red));
            JLabel exlab = u.label("rec_extrainfo");
            panel6.add(exlab, grid1);
            panel6.add(extraInfo, grid1);
          }
        grid1.insets = new Insets(0,0,0,0);
          panel5.add(recordName, grid1);
          panel5.setBorder(BorderFactory.createEtchedBorder(Color.orange, Color.red));
          grid1.insets = new Insets(0,10,0,10);
          panel4.add(panel5, grid1);
          if(definitions)  panel4.add(panel6, grid1);
          grid1.fill = GridBagConstraints.NONE;
          
        }
      grid1.insets = new Insets(0,0,0,0);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      panel4.add(startRecording,grid1);
      panel4.add(autorec,grid1);
      panel4.add(save,grid1);
//      panel4.add(listen,grid1);
      panel4.add(ignore,grid1);
      panel4.add(prevWord2,grid1);
      panel4.add(deleteWord,grid1);
      panel4.add(whattopic,grid1);
      panel4.add(exitbutton,grid1);
      if(sentences || definitions) {
          panel4.add(beepbutton,grid1);
          panel4.add(rembeepbutton,grid1);
      }
      JPanel boo = new JPanel(new GridBagLayout());
      JPanel pnoldpatt = new JPanel(new GridBagLayout());
      JPanel pnnewpatt = new JPanel(new GridBagLayout());

        grid1.gridx=-1;
        grid1.gridy = 0;
        grid1.fill = GridBagConstraints.BOTH;
        grid1.weightx = 0;
        pnoldpatt.add(listen_curr, grid1);
        grid1.weightx = 1;
        pnoldpatt.add(oldpatt, grid1);
        
        grid1.weightx = 0;
        pnnewpatt.add(listen_proposed, grid1);
        grid1.weightx = 1;
        pnnewpatt.add(newpatt, grid1);

grid1.fill = GridBagConstraints.NONE;
        grid1.gridx=0;
        grid1.gridy = -1;
      grid1.insets = new Insets(0,10,0,10);
      grid1.fill = GridBagConstraints.HORIZONTAL;
      panel4.add(boo,grid1);
      grid1.fill = GridBagConstraints.NONE;
      panel4.add(oldsound,grid1);
      grid1.fill = GridBagConstraints.HORIZONTAL;
      panel4.add(pnoldpatt,grid1);
      grid1.fill = GridBagConstraints.NONE;
      panel4.add(newsound,grid1);
      grid1.fill = GridBagConstraints.HORIZONTAL;
      panel4.add(pnnewpatt,grid1);
      grid1.fill = GridBagConstraints.NONE;
      grid1.gridx=-1;
      grid1.gridy = 0;
      boo.add(boosterlabel,grid1);
      grid1.fill = GridBagConstraints.HORIZONTAL;
grid1.insets = new Insets(0,0,0,0);
      boo.add(booster,grid1);
      boo.setBorder(BorderFactory.createLineBorder(Color.black,2));

      recordName.addFocusListener(new FocusAdapter() {
         public void focusLost(FocusEvent e) {
           if(changingword) return;
           String newword = recordName.getText();
           if(newword.length() > 0) setupword(newword);
           full.clearSelection();
           already.clearSelection();
           notyet.clearSelection();
         }
      });
      recordName.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            if(changingword) return;
            String newword = recordName.getText();
            if(newword.length() > 0) setupword(newword);
            full.clearSelection();
            already.clearSelection();
            notyet.clearSelection();
         }
      });

      autorec.setSelected(auto = student.option("rec_auto"));
      autorec.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          auto = autorec.isSelected();
          if(auto) student.setOption("rec_auto");
          else student.clearOption("rec_auto");
          booster.setEnabled(auto);
        }
      });

      startRecording.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(ActionEvent e) {
            if(!recordName.getText().equals(currword) && currword.length()<MAXNAMELEN) {
//startPR2004-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//               u.okmess("rec_endingedit");
                    u.okmess("rec_endingedit", sharkStartFrame.mainFrame);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
               String newword = recordName.getText();
               if(newword.length() > 0) setupword(newword);
               full.clearSelection();
               already.clearSelection();
               notyet.clearSelection();
               return;
            }
            if(!doingRecording) {
               if(spokenWord.mike != null) return;
               String ss = currSpokenWord.name;
               currSpokenWord.autoboost = auto;
               if(ss.length() > MAXNAMELEN)
                  new u.infotextpane(u.gettext("rec_","recsentence"),ss,getWidth()/6,getY() + getHeight()/4,
                                            getWidth()/2,getHeight()/2);
               doingRecording = true;
               currSpokenWord.startRecording();
               if(doingRecording) clearButtons();
            }
            else {
               startRecording.setEnabled(false);
               currSpokenWord.stopRecording();
               doingRecording=false;
               if(sentences) sharkStartFrame.mainFrame.setTitle(oldtitle);
            }
         }
      });
      ignore.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(ActionEvent e) {
            nextwordfromlist();
            setButtons();
//startPR2004-09-01^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            // enables exiting screen via the ESC key
            requestFocus();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         }
      });/*
      listen.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(ActionEvent e) {
            if(isNewWord) {
              if(newpatt.start > 0 || newpatt.end != currSpokenWord.data.length
                      || newpatt.beepx.length>0) {
                 spokenWord.say(newpatt.getsound());
                 return;
              }
            }
            else if(isOldWord) {
              if(oldpatt.start > 0 || oldpatt.end != currSpokenWord.data.length
                       || oldpatt.beepx.length>0) {
                 spokenWord.say(oldpatt.getsound());
                 return;
              }
            }
           currSpokenWord.say();
//startPR2004-09-01^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
           // enables exiting screen via the ESC key
           requestFocus();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         }
      });*/
      int  buttondim = (sharkStartFrame.mainFrame.getWidth()*14/22)/24;
      int buttonimdim =  buttondim- (buttondim/5);
      listen_curr.setPreferredSize(new Dimension(buttondim, buttondim));
      listen_curr.setMinimumSize(new Dimension(buttondim, buttondim));
      listen_proposed.setPreferredSize(new Dimension(buttondim, buttondim));
      listen_proposed.setMinimumSize(new Dimension(buttondim, buttondim));
      Image im = Toolkit.getDefaultToolkit().getImage(sharkStartFrame.publicPathplus + "sprites" +
                                       sharkStartFrame.separator +
                                       "play_il48.png");
      ImageIcon iiinfo = new ImageIcon(im.getScaledInstance(buttonimdim,
            buttonimdim, Image.SCALE_SMOOTH));
      listen_curr.setIcon(iiinfo);
      listen_proposed.setIcon(iiinfo);
      listen_curr.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(ActionEvent e) {
           if(oldpatt.start > 0 || oldpatt.end != currSpokenWord.data.length
                       || oldpatt.beepx.length>0) {
                 spokenWord.say(oldpatt.getsound());
                 return;
           }
           currSpokenWord.say();
           requestFocus();
         }
      });
      listen_proposed.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(ActionEvent e) {
              if(newpatt.start > 0 || newpatt.end != currSpokenWord.data.length
                      || newpatt.beepx.length>0) {
                 spokenWord.say(newpatt.getsound());
                 return;
              }
           currSpokenWord.say();
           requestFocus();
         }
      });



      save.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(ActionEvent e) {
            byte savedata[] = currSpokenWord.data;
            if(isNewWord) {
              if(newpatt.start > 0 || newpatt.end != currSpokenWord.data.length
                       || newpatt.beepx.length > 0) {
                 currSpokenWord.data = newpatt.getsound();
               }
            }
            else {
              if(oldpatt.start > 0 || oldpatt.end != currSpokenWord.data.length
                       || oldpatt.beepx.length > 0)
                currSpokenWord.data = oldpatt.getsound();
            }
            currSpokenWord.save();
            if(definitions) {
               String extra = (String)extraInfo.getText();
               if(extra.length()==0) db.delete(soundDBName,currSpokenWord.name,db.TEXT);
               else db.update(soundDBName,currSpokenWord.name,extra,db.TEXT);
            }
            currSpokenWord.data =savedata;  // restore
            if(++savedwords >= 5)  {
//               db.closeAll();   // make sure - only temp ??????
               savedwords = 0;
            }
            prevword = currword;
            boolean wasoldword = isOldWord;
            nextwordfromlist();
            setButtons();
            if(!wasoldword) savenewword(prevword);   // adjust lists
//startPR2004-09-01^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                // enables exiting screen via the ESC key
                requestFocus();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         }
      });
      prevWord2.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(ActionEvent e) {
            if(prevword != null) {
               setupword(prevword);
               prevword= null;
            }
//startPR2004-09-01^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
           // enables exiting screen via the ESC key
           requestFocus();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         }
      });
      deleteWord.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(ActionEvent e) {
            deleteword();
            clearButtons();

      newpatt.clear();
      oldpatt.clear();
            currword = null;
//startPR2004-09-01^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            // enables exiting screen via the ESC key
            requestFocus();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         }
      });
      whattopic.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(ActionEvent e) {
            String[] s= new String[0];
            for(int i=0;i<topicwords.length;++i) {
               if(u.findString(topicwords[i],currword) >= 0)
                   s = u.addString(s,topicList[i]);
            }
            if(s.length == 0)
//startPR2004-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//              u.okmess(u.gettext("rec_","listtopicsh"),u.gettext("rec_","listtopicsn"));
              u.okmess(u.gettext("rec_","listtopicsh"),u.gettext("rec_","listtopicsn"), sharkStartFrame.mainFrame);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            else showlist(u.gettext("rec_","listtopicsh"),s);
//startPR2004-09-01^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                // enables exiting screen via the ESC key
//                requestFocus();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         }
      });
      exitbutton.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(ActionEvent e) {
            sharkStartFrame.mainFrame.setupgames();
         }
      });
      beepbutton.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(ActionEvent e) {
//startPR2004-09-01^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
           if(currword != null){
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
             if(isNewWord) newpatt.addbeep();
             else if(isOldWord) oldpatt.addbeep();
             setButtons();
//startPR2004-09-01^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
           }
           // enables exiting screen via the ESC key
            requestFocus();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         }
      });
      rembeepbutton.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(ActionEvent e) {
//startPR2004-09-01^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
           if(currword != null){
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
             if(isNewWord) newpatt.removebeep();

             else if(isOldWord) oldpatt.removebeep();
             setButtons();
//startPR2004-09-01^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
              // enables exiting screen via the ESC key
            }
            requestFocus();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         }
      });
      full.addMouseListener(new MouseAdapter() {
         public void mouseReleased(MouseEvent e) {
            selectedlist = full;
            wordfromlist1();
         }
      });
      already.addMouseListener(new MouseAdapter() {
         public void mouseReleased(MouseEvent e) {
            selectedlist = already;
            wordfromlist2();
        }
      });
      notyet.addMouseListener(new MouseAdapter() {
         public void mouseReleased(MouseEvent e) {
            selectedlist = notyet;
           wordfromlist3();
        }
      });
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
        selectedlist3.addKeyListener(new KeyAdapter() {
          public void keyPressed(KeyEvent e) {
            int code = e.getKeyCode();
            if(code == KeyEvent.VK_ESCAPE)
              sharkStartFrame.mainFrame.setupgames();
          }
        });
        full.addKeyListener(new KeyAdapter() {
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
        notyet.addKeyListener(new KeyAdapter() {
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
        });
//startPR2009-02-25^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        phonicsounds.addKeyListener(new KeyAdapter() {
          public void keyPressed(KeyEvent e) {
            int code = e.getKeyCode();
            if(code == KeyEvent.VK_ESCAPE)
              sharkStartFrame.mainFrame.setupgames();
          }
        });
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        buttonfull.addKeyListener(new KeyAdapter() {
          public void keyPressed(KeyEvent e) {
            int code = e.getKeyCode();
            if(code == KeyEvent.VK_ESCAPE)
              sharkStartFrame.mainFrame.setupgames();
          }
        });
        buttonnotyet.addKeyListener(new KeyAdapter() {
          public void keyPressed(KeyEvent e) {
            int code = e.getKeyCode();
            if(code == KeyEvent.VK_ESCAPE)
              sharkStartFrame.mainFrame.setupgames();
          }
        });
        buttonextra.addKeyListener(new KeyAdapter() {
          public void keyPressed(KeyEvent e) {
            int code = e.getKeyCode();
            if(code == KeyEvent.VK_ESCAPE)
              sharkStartFrame.mainFrame.setupgames();
          }
        });
        recordName.addKeyListener(new KeyAdapter() {
          public void keyPressed(KeyEvent e) {
            int code = e.getKeyCode();
            if(code == KeyEvent.VK_ESCAPE)
              sharkStartFrame.mainFrame.setupgames();
          }
        });
        recordName.setDocument(new keydoc(recordName));
        booster.addKeyListener(new KeyAdapter() {
          public void keyPressed(KeyEvent e) {
            int code = e.getKeyCode();
            if(code == KeyEvent.VK_ESCAPE)
              sharkStartFrame.mainFrame.setupgames();
          }
        });
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      changingword = false;
      validate();
//startPR2004-09-01^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       // enables exiting screen via the ESC key
       requestFocus();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      }
      return;
   }
    //------------------------------------------------------
  void setupword(String s) {
      setupword(s, true);
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
      if(settext)
              recordName.setText(s);
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
      newpatt.clear();
      if(isOldWord) {
         currSpokenWord.decomp();
         currSpokenWord.say();
         oldpatt.setup(currSpokenWord.data);
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
        oldpatt.clear();
      }
      setsel(already,alreadywords);
      setnotyet2();
//startPR2009-02-25^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      setsel(selectedlist3,selectedlist3==notyet?notyetwords2:(selectedlist3==full?words:extrawords));
      setsel(selectedlist3, (selectedlist3==phonicsounds)?phonicsoundswords:   (   selectedlist3==notyet?notyetwords2:(selectedlist3==full?words:extrawords)     )  );
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
      startRecording.setEnabled(currword != null && currword.length() > 0);
      autorec.setEnabled(true);
      booster.setEnabled(!auto);
      if(isNewWord)      startRecording.setText(startText2);
      else if(isOldWord) startRecording.setText(startText3);
      else if(currword != null && currword.length() > 0)
                              startRecording.setText(startText4);
      else               startRecording.setText(startText1);
      whattopic.setEnabled(currword != null && currword.length() > 0);

      ignore.setEnabled(isnextwordfromlist());
      if(isNewWord) ignore.setText(ignoreText1);
      else          ignore.setText(ignoreText2);
      if(!isnextwordfromlist()) save.setText(saveText2);
      else                   save.setText(saveText1);
      if(currword.length() > MAXNAMELEN2)
         deleteWord.setText(u.edit(deleteText,currword.substring(0,MAXNAMELEN2)+"..."));
      else deleteWord.setText(u.edit(deleteText,currword));
  //    listen.setEnabled(isOldWord | isNewWord);   //enabled
      listen_proposed.setEnabled(isNewWord);
      listen_curr.setEnabled(isOldWord || isNewWord);
      


      save.setEnabled(isNewWord
            || isOldWord && (oldpatt.start != 0 || oldpatt.end != currSpokenWord.data.length));
      prevWord2.setEnabled(prevword!=null);
      deleteWord.setEnabled(isOldWord);
      beepbutton.setEnabled(isOldWord || isNewWord);
      rembeepbutton.setEnabled(isNewWord && newpatt.beepx.length>0
           || !isNewWord && isOldWord && oldpatt.beepx.length>0);
//      databaseName.setEditable(!isNewWord);
      recordName.setEditable(!isNewWord);

      int sel = full.getSelectedIndex();
      if(sel >= 0) {
         full.ensureIndexIsVisible(sel);
      }
      else {
         sel = already.getSelectedIndex();
         if(sel >= 0) {
            already.ensureIndexIsVisible(sel);
         }
         else {
            sel = notyet.getSelectedIndex();
            if(sel >= 0) {
               notyet.ensureIndexIsVisible(sel);
            }
         }
      }
   }
   //------------------------------------------------------
   public void clearButtons() {
      if(doingRecording) {
        if(auto)         startRecording.setEnabled(false);
        else {
          startRecording.setText(startText5);
          startRecording.setEnabled(true);
        }
        autorec.setEnabled(false);
      }
      else {
        startRecording.setEnabled(false);
        autorec.setEnabled(true);
      }
      ignore.setEnabled(false);
//      listen.setEnabled(false);  //enabled
      listen_proposed.setEnabled(false);
      listen_curr.setEnabled(false);

      save.setEnabled(false);
      prevWord2.setEnabled(false);
      deleteWord.setEnabled(false);
      whattopic.setEnabled(false);
      recordName.setEditable(true);
  }
     //-----------------------------------------------------
   void addword(String s,int topicnum) {
       if(s.indexOf("+")>=0){
           int h = 0;
       }
 //      if(s.indexOf("qdc")>=0){
 //      if(s.equals("I want to act on the stage/cage/page")){
 //          int h;
 //          h = 9;
  //     }
//    if(!s.endsWith("@nosound")){
      words = u.addStringSort(words,s);
      if(topicnum>=0) topicwords[topicnum] = u.addString(topicwords[topicnum],s);        
 //   }

   }
   //--------------------------------------------------------------------
   public void save() {
//      db.closeAll();
   }
   //-----------------------------------------------------------------
   void wordfromlist1() {
      int sel = full.getSelectedIndex();
      if(sel >= 0) {
          setupword(words[sel]);
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
      int sel= notyet.getSelectedIndex();
      if(sel >=0) {
          setupword(notyetwords[sel]);
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
      if(selectedlist==full) {
         sel = full.getSelectedIndex();
         if(sel >= 0 && sel < words.length-1) return true;
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
         else if(selectedlist==notyet){
            sel = notyet.getSelectedIndex();
            if(sel >= 0) {
               if(sel <notyetwords.length
                  && currword != null
                  && !notyetwords[sel].equalsIgnoreCase(currword)){
                        return true;
               }
               if(sel < notyetwords.length-1) return true;
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
      if(ispublic)   {notyetwords2=notyetwords;return;}
      notyetwords2 = new String[notyetwords.length];
      for(int i=0;i<notyetwords.length;++i) {
        notyetwords2[i] = notyetwords[i];
        if(db.query(sharkStartFrame.getPrimarySoundDb(sharkStartFrame.publicSoundLib),notyetwords[i],db.WAV) >= 0 || db.query(sharkStartFrame.getPrimarySoundDb(sharkStartFrame.publicSoundLib),notyetwords[i]+"~",db.WAV) >= 0)
               notyetwords2[i] += inpub;
      }
   }
   //-----------------------------------------------------------------
   boolean nextwordfromlist() {
      int sel;
      if(selectedlist==full) {
         sel = full.getSelectedIndex();
         if(sel < words.length-1) {
            full.setSelectedIndex(sel+1);
            setupword(words[sel+1]);
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
      else if(selectedlist==notyet) {
            sel = notyet.getSelectedIndex();
            if(sel >=0) {
               if(sel <notyetwords.length
                  && currword != null
                  && !notyetwords[sel].equalsIgnoreCase(currword)){
                   setupword(notyetwords[sel]);
                   return true;
               }
               if(sel < notyetwords.length-1) {
                  notyet.setSelectedIndex(sel+1);
                  setupword(notyetwords[sel+1]);
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
      if(u.query(words,currword)) {
        notyetwords=u.addStringSort(notyetwords,currword);
        setnotyet2();
        notyet.setListData(notyetwords2);
      }
      already.clearSelection();
      selectedlist3.clearSelection();
   }
   //------------------------------------------------------------------
   void savenewword(String currword) {
      already.setListData(alreadywords=u.addStringSort(alreadywords,currword));
      if(u.query(notyetwords,currword)) {
         int sel= notyet.getSelectedIndex();
         int old = u.findString(notyetwords,currword);
         notyetwords = u.removeString(notyetwords,currword);
         setnotyet2();
         notyet.setListData(notyetwords2);
         if(sel >=0) {
            if (old >sel) --old;
            notyet.setSelectedIndex(old);
         }
      }
      if(!u.query(words,currword)) {
         extra.setListData(extrawords=u.addStringSort(extrawords,currword));
      }
   }
   void getbooster() {
      int v;
      String vol[]= (String[])db.find(sharkStartFrame.optionsdb,"rec_boost",db.TEXT);
      if(vol==null || vol.length==0) v = 2;
      else v = Integer.parseInt(vol[0]);
      spokenWord.boost = boostervals[v];
      boosterlabel.setText(u.gettext("rec_booster","label",String.valueOf(spokenWord.boost)+'%'));
      booster = new JSlider(0,boostervals.length-1,v);
      booster.addChangeListener(new ChangeListener() {
         public void stateChanged(ChangeEvent e){
           int v;
            spokenWord.boost = boostervals[v=booster.getValue()];
            boosterlabel.setText(u.gettext("rec_booster","label",String.valueOf(spokenWord.boost)+'%'));
            db.update(sharkStartFrame.optionsdb,"rec_boost",
                      new String[]{String.valueOf(v)},db.TEXT);
         }
       });
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
     public void insertUpdate(DefaultDocumentEvent chng, AttributeSet attr) {
         if(changingword) return;
         setupword(thisTextField.getText(),false);
     }
     public void removeUpdate(DefaultDocumentEvent chng) {
         if(changingword) return;
         setupword(thisTextField.getText(),false);
     }
   }

}


