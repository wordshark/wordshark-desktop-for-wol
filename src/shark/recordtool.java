/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package shark;

import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.*;
import java.util.*;
/**
 *
 * @author Paul Rubie
 */
     class recordtool{


   Font plainfont = sharkStartFrame.treefont.deriveFont(Font.PLAIN);
   Font smallerplainfont = plainfont.deriveFont((float)plainfont.getSize()-1);
   static final short MAXNAMELEN = 35;
   static final short MACMAXNAMELEN = 28;
   static final short MAXNAMELEN2 = 20;

        public spokenWord currSpokenWord;
        public spokenWord currSpokenWord2;
  //      boolean isNewWord,isOldWord;
        boolean definitions,definitions2,sentences;
   boolean changingword, doingRecording;
      boolean ispublic,sent1,sent3,auto, say3;
           ImageIcon recplayii;
     ImageIcon recrecii;
     ImageIcon recsaveii;
     ImageIcon recstopii;
 //    ImageIcon listenii;
     ImageIcon delii;
     String dbname = sharkStartFrame.studentList[sharkStartFrame.currStudent].name;
   static int boostervals[] = new int[] {50,75,100,125,150,200,300,400,500};
   JSlider  booster;
   JPanel recpanel1;
   JPanel recpanel2;
    JPanel controlpanel = new JPanel(new GridBagLayout());
   JPanel listenpanel = new JPanel(new GridBagLayout());
   String strcolon = ":";
   String strrecfor = u.gettext("recordtool", "soundfor");
   String strrecfor_trans = u.gettext("recordtool", "soundfortrans");
   String strtranslation = u.gettext("recordtool", "translation");
   String strdefinition = u.gettext("recordtool", "definition");
   String strtrans = u.gettext("recordtool", "inlang");
   String strtrans_trans = u.gettext("recordtool", "inlangtrans");
   String str2title = u.gettext("recordtool", "title2");
   JLabel lbcurrrec;
   ItemListener bglisten = new java.awt.event.ItemListener() {
             public void itemStateChanged(ItemEvent e) {
                JRadioButton jrb = ((JRadioButton)e.getSource());
                  auto = jrb.isSelected();
                  if(auto) {
                      student.setOption("rec_auto");
                  }
                  else {
                      student.clearOption("rec_auto");
                  }
                  booster.setEnabled(!auto);
             }
         };
         String strinputvol = u.gettext("recordtool", "inputvol");
         String strsavedsound = u.gettext("recordtool", "saved");
         String strtrim = u.gettext("recordtool", "markers");
         String strrecsound = u.gettext("recordtool", "recnew");
        String currword;
        int sw = sharkStartFrame.mainFrame.getWidth();
        int sh = sharkStartFrame.mainFrame.getHeight();
                    int buttondim = (sw*14/22)/24;
        int buttonimdim =  buttondim- (buttondim/5);
        boolean normalactive = true;
        int listtype;
        String currlang[];
        JDialog parent;
        



        public recordtool(JDialog owner, short type){
            parent = owner;
        spokenWord.currrectool = this;
        spokenWord.recording = false;
        spokenWord.autoboost = false;
        spokenWord.currrecord = null;
        getbooster();
 
          Image im = Toolkit.getDefaultToolkit().getImage(sharkStartFrame.publicPathplus + "sprites" +
                                           sharkStartFrame.separator +
                                           "save_il48.png");
         recsaveii = new ImageIcon(im.getScaledInstance(buttonimdim,
                buttonimdim, Image.SCALE_SMOOTH));
        im = Toolkit.getDefaultToolkit().getImage(sharkStartFrame.publicPathplus + "sprites" +
                                           sharkStartFrame.separator +
                                           "play_il48.png");
         recplayii = new ImageIcon(im.getScaledInstance(buttonimdim,
                buttonimdim, Image.SCALE_SMOOTH));
        im = Toolkit.getDefaultToolkit().getImage(sharkStartFrame.publicPathplus + "sprites" +
                                           sharkStartFrame.separator +
                                           "record_il48.png");
         recrecii = new ImageIcon(im.getScaledInstance(buttonimdim,
                buttonimdim, Image.SCALE_SMOOTH));
        im = Toolkit.getDefaultToolkit().getImage(sharkStartFrame.publicPathplus + "sprites" +
                                           sharkStartFrame.separator +
                                           "stop_il48.png");
         recstopii = new ImageIcon(im.getScaledInstance(buttonimdim,
                buttonimdim, Image.SCALE_SMOOTH));

        im = Toolkit.getDefaultToolkit().getImage(sharkStartFrame.publicPathplus + "sprites" +
                                           sharkStartFrame.separator +
                                           "speakerON_il48.png");
 //        listenii = new ImageIcon(im.getScaledInstance(buttonimdim,
 //               buttonimdim, Image.SCALE_SMOOTH));

        im = Toolkit.getDefaultToolkit().getImage(sharkStartFrame.publicPathplus + "sprites" +
                                           sharkStartFrame.separator +
                                           "deleteON_il48.png");
         delii = new ImageIcon(im.getScaledInstance(buttonimdim,
                buttonimdim, Image.SCALE_SMOOTH));



 
        }

        public void haltRecording(){
            if(normalactive){
                ((recpan)recpanel1).haltrec();
            }
            else{
                ((recpan)recpanel2).haltrec();
            }
        }


        public void changeType(short type){
            listtype = type;
        }

        public void changeLang(String lang[]){
            currlang = lang;
        }

        public void setupSoundW(byte data1[]){
            if(normalactive){
                ((recpan)recpanel1).setupSound(data1);
//                ((recpan)recpanel1).soundw.setup(data1);
 //               ((recpan)recpanel1).lbmove.setForeground(Color.black);
            }
            else{
                ((recpan)recpanel2).setupSound(data1);
//                ((recpan)recpanel2).soundw.setup(data1);
  //              ((recpan)recpanel2).lbmove.setForeground(Color.black);
            }
            
        }
        public void setupSoundW(byte data1[],int len){
            if(normalactive){
                ((recpan)recpanel1).setupSound(data1,len);
 //           ((recpan)recpanel1).soundw.setup(data1,len);
 //           ((recpan)recpanel1).lbmove.setForeground(Color.black);
            }
            else{
                ((recpan)recpanel2).setupSound(data1,len);
//                ((recpan)recpanel2).soundw.setup(data1,len);
//                ((recpan)recpanel2).lbmove.setForeground(Color.black);
            }
        }
        public void focusSound(){
            if(normalactive)
            recpanel1.requestFocus();
            else
            recpanel2.requestFocus();
        }

        public JPanel makeRecPanel1(){
            return recpanel1 = new recpan(true);
        }

        public JPanel makeRecPanel2(){
            return recpanel2 = new recpan(false);
        }


        public JPanel makeControlPanel(){
            GridBagConstraints g = new GridBagConstraints();
            g.fill = GridBagConstraints.BOTH;
            g.weightx = 1;
            g.weighty = 1;
            g.gridx = 0;
            g.gridy = -1;
            JPanel jprad = new JPanel(new GridBagLayout());
            g.weightx = 0;
            g.gridx = 0;
            ButtonGroup bgr = new ButtonGroup();
            JRadioButton rbauto = new JRadioButton(u.gettext("recordtool", "auto"));
            bgr.add(rbauto);
            rbauto.addItemListener(bglisten);
            JRadioButton rbmanual = new JRadioButton(u.gettext("recordtool", "manual"));
            bgr.add(rbmanual);
    //        rbmanual.addItemListener(bglisten);
            jprad.add(rbauto, g);
            jprad.add(rbmanual, g);
            auto = false;//student.option("rec_auto");
            if(auto) rbauto.setSelected(true);
            else rbmanual.setSelected(true);
            JPanel jpslider = new JPanel(new GridBagLayout());
            JPanel jpguide = new JPanel(new GridBagLayout());
            g.gridx = -1;
            g.gridy = 0;
            g.weightx = 1;
            g.fill = GridBagConstraints.NONE;
            g.anchor = GridBagConstraints.WEST;
            JLabel lowlb = new JLabel(u.gettext("recordtool", "low"));
            lowlb.setFont(smallerplainfont);
            jpguide.add(lowlb, g);
            g.anchor = GridBagConstraints.CENTER;
            JLabel midlb = new JLabel(u.gettext("recordtool", "mid"));
            midlb.setFont(smallerplainfont);
            jpguide.add(midlb, g);
            g.anchor = GridBagConstraints.EAST;
            JLabel highlb = new JLabel(u.gettext("recordtool", "high"));
            highlb.setFont(smallerplainfont);
            jpguide.add(highlb, g);
            
            g.weightx = 1;
            g.gridx = 0;
            g.gridy = -1;
            g.fill = GridBagConstraints.HORIZONTAL;
            g.anchor = GridBagConstraints.CENTER;
            jpslider.add(new JLabel(strinputvol), g);
            g.insets = new Insets(5,0,5,0);
            jpslider.add(booster, g);

            g.gridx = 0;
            g.gridy = -1;
 //           controlpanel.add(jprad, g);
            g.insets = new Insets(0,50,0,50);
            g.fill = GridBagConstraints.BOTH;
            controlpanel.add(jpslider, g);
            controlpanel.add(jpguide, g);
            return controlpanel;
        }


   public void setupword(String s) {
/*
       spokenWord.stopspeaker();
       spokenWord.closespeaker() ;
       if(currSpokenWord!=null)
         currSpokenWord.stopRecording();
       if(currSpokenWord2!=null)
         currSpokenWord2.stopRecording();
       spokenWord.info = null;
       spokenWord.endsay = 0;
spokenWord.endsay2 = 0;
spokenWord.opentime = 0;
spokenWord.notrim2 = true;
*/


       currword = s;
       currSpokenWord = new spokenWord(currword,sharkStartFrame.resourcesdb ,false);
       currSpokenWord2 = new spokenWord(currword,sharkStartFrame.resourcesdb ,false);
       ((recpan)recpanel1).setup();
       ((recpan)recpanel2).setup();

   }

   void getbooster() {
      int v;
      String vol[]= (String[])db.find(sharkStartFrame.optionsdb,"rec_boost",db.TEXT);
      if(vol==null || vol.length==0) v = 2;
      else v = Integer.parseInt(vol[0]);
      spokenWord.boost = boostervals[v];
//startPR2004-08-27^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          // if running on a Macintosh
 //         if (shark.macOS) {
 //           boosterlabel.setText(u.gettext("rec_booster_mac","label",String.valueOf(spokenWord.boost)+'%'));
   //       }
   //       // if running on Windows
   //       else {
   //         boosterlabel.setText(u.gettext("rec_booster","label",String.valueOf(spokenWord.boost)+'%'));
   //       }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      booster = new JSlider(0,boostervals.length-1,v);
      booster.addChangeListener(new ChangeListener() {
         public void stateChanged(ChangeEvent e){
           int v;
            spokenWord.boost = boostervals[v=booster.getValue()];
//startPR2004-08-27^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            // if running on a Macintosh
//            if (shark.macOS) {
 //             boosterlabel.setText(u.gettext("rec_booster_mac","label",String.valueOf(spokenWord.boost)+'%'));
//            }
            // if running on Windows
//            else {
 //             boosterlabel.setText(u.gettext("rec_booster","label",String.valueOf(spokenWord.boost)+'%'));
//            }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            db.update(sharkStartFrame.optionsdb,"rec_boost",
                      new String[]{String.valueOf(v)},db.TEXT);
         }
       });
   }

   
   
     class recpan extends JPanel {
         JLabel title = new JLabel();
         boolean normalwords;
         public JLabel lbmove;
         reccontrol controlExisting;
         reccontrol controlNew;

         public recpan(boolean normal){
            normalwords = normal;
            setLayout(new GridBagLayout());
            GridBagConstraints g = new GridBagConstraints();
            g.fill = GridBagConstraints.BOTH;
            g.weightx = 1;
            g.weighty = 1;
            g.gridx = -1;
            g.gridy = 0;
            JPanel savedpn = new JPanel(new GridBagLayout());
            JLabel sslb = new JLabel(strsavedsound);
            sslb.setFont(plainfont);
            g.insets = new Insets(0,0,0,5);
            g.weightx = 0;
            savedpn.add(sslb, g);
            g.weightx = 1;
            g.insets = new Insets(0,0,0,0);
            savedpn.add(controlExisting = new reccontrol(normal, true, this, null), g);
            g.insets = new Insets(0,0,0,0);
            g.weightx = 0;
            g.gridx = 0;
            g.anchor = GridBagConstraints.CENTER;
            g.fill = GridBagConstraints.NONE;
            lbmove = new JLabel(strtrim);
            lbmove.setFont(smallerplainfont);
            lbmove.setForeground(sharkStartFrame.defaultbg);
            g.anchor = GridBagConstraints.WEST;
            g.fill = GridBagConstraints.BOTH;
            g.gridx = 0;
            g.gridy = -1;
            g.insets = new Insets(0,0,5,0);
            add(title, g);
            g.insets = new Insets(0,0,0,0);
            g.weightx = 1;
            g.fill = GridBagConstraints.BOTH;
            g.insets = new Insets(0,0,5,0);
            add(savedpn, g);
            g.fill = GridBagConstraints.NONE;
            g.insets = new Insets(0,0,0,0);
            JPanel lbPn = new JPanel(new GridBagLayout());
            g.gridx = -1;
            g.gridy = 0;            
            JLabel rnslb = new JLabel(strrecsound);
            rnslb.setFont(plainfont);
            g.weightx = 0;
            lbPn.add(rnslb, g);
            JPanel lbpanel = new JPanel(new GridBagLayout());
            g.fill = GridBagConstraints.NONE;
            lbpanel.add(lbmove, g);
            g.weightx = 1;
            g.fill = GridBagConstraints.HORIZONTAL;
            lbPn.add(lbpanel, g);
            g.weightx = 1;
            g.gridx = 0;
            g.gridy = -1;
            g.fill = GridBagConstraints.HORIZONTAL;
            add(lbPn, g);
            g.fill = GridBagConstraints.HORIZONTAL;
            add(controlNew = new reccontrol(normal, false, this, controlExisting), g);
         }

         public void dolisten(boolean normal){
             spokenWord sw = controlExisting.dolisten(normal);
             if(sw!=null)sw.say();
         }

         public void setupSound(byte data1[]){
             controlNew.setupSound(data1);
    //         lbmove.setForeground(Color.black);
         }          
         
         
         public void setupSound(byte data1[],int len){
             controlNew.setupSound(data1, len);
     //        lbmove.setForeground(Color.black);
         }                  
         
         public void haltrec(){
             controlNew.haltrec();
         }         
         
         public void swchanged(){
             if(controlNew.soundw.showTrim &&  controlNew.soundw.data!=null || 
                     controlExisting.soundw.showTrim &&  controlExisting.soundw.data!=null ){
                 lbmove.setForeground(Color.black);
             }
             else lbmove.setForeground(sharkStartFrame.defaultbg);
         }
     
         

         
         
         public void setup(){
             if(normalwords){
                 if(listtype == OwnWordLists.TYPE_TRANSLATIONS){
                    String ss = strrecfor_trans.replaceFirst("%", currword);
                    String lang = "";
                    if(currlang!=null)
                        lang = currlang[0];
                    ss += " "+strtrans.replaceFirst("%", lang);
                    title.setText(ss);
                 }
                 else
                    title.setText(strrecfor.replaceFirst("%", currword));
             }
             else{
                String ss = "";
                if(listtype == OwnWordLists.TYPE_TRANSLATIONS){
                    ss = str2title.replaceFirst("%", strtranslation);
                    String lang = "";
                    if(currlang!=null)
                        lang = currlang[1];
                    ss =  u.convertToHtml( ss.replaceFirst("%", currword)+" "+strtrans_trans.replaceFirst("%", lang));
//                    ss += strcolon;
                }
                else if(listtype == OwnWordLists.TYPE_DEFINITIONS){
                    ss = str2title.replaceFirst("%", strdefinition);
                    ss = ss.replaceFirst("%", currword);
                    ss += strcolon;
                }
                title.setText(ss);
             }
             lbmove.setForeground(sharkStartFrame.defaultbg);
             controlNew.setup();
             controlExisting.setup();
         }
     }

     
     
     class reccontrol extends JPanel {
         JButton listen = u.sharkButton();
         JButton delete = u.sharkButton();
         JButton recstop = u.sharkButton();
         JButton play = u.sharkButton();
         JButton save = u.sharkButton();
         public soundwave soundw = new soundwave();
         boolean normalwords;
         JProgressBar pBar;
         String rectooltip = u.gettext("recordtool", "rec_tooltip");
         String playtooltip = u.gettext("recordtool", "play_tooltip");
         reccontrol thiscontrol = this;
        public spokenWord currSpokenWord_existing;
        public spokenWord currSpokenWord2_existing;
        boolean isexisting;
        reccontrol ctrlRefreshOnSave;
        boolean soundIsUsers = true;
 //       boolean showingTrimLines = false;
        recpan owner;

         public reccontrol(boolean normal, boolean existing, recpan owningpn, reccontrol refreshOnSave){
             isexisting = existing;
             owner = owningpn;
             ctrlRefreshOnSave = refreshOnSave;
            recstop.setToolTipText(rectooltip);
            play.setToolTipText(playtooltip);
            normalwords = normal;
            listen.setPreferredSize(new Dimension(buttondim, buttondim));
            listen.setMinimumSize(new Dimension(buttondim, buttondim));
            delete.setPreferredSize(new Dimension(buttondim, buttondim));
            delete.setMinimumSize(new Dimension(buttondim, buttondim));
            recstop.setPreferredSize(new Dimension(buttondim, buttondim));
            recstop.setMinimumSize(new Dimension(buttondim, buttondim));
            play.setPreferredSize(new Dimension(buttondim, buttondim));
            play.setMinimumSize(new Dimension(buttondim, buttondim));
            save.setPreferredSize(new Dimension(buttondim, buttondim));
            save.setMinimumSize(new Dimension(buttondim, buttondim));
            listen.setIcon(recplayii);
            delete.setIcon(delii);
            recstop.setIcon(recrecii);
            play.setIcon(recplayii);
            save.setIcon(recsaveii);
            save.setEnabled(false);
            play.setEnabled(false);

            pBar = new JProgressBar();
            pBar.setIndeterminate(true);
            pBar.setVisible(false);
            pBar.setForeground(Color.red);
            setLayout(new GridBagLayout());
            GridBagConstraints g = new GridBagConstraints();
            g.fill = GridBagConstraints.BOTH;
            g.weighty = 1;
            g.gridx = -1;
            g.gridy = 0;
            g.insets = new Insets(0,0,0,0);
            JPanel jp = new JPanel(new GridBagLayout());
            g.weightx = 0;
            if(!isexisting) jp.add(recstop, g);
            jp.add(isexisting?listen:play, g);
            g.weightx = 1;
            jp.add(pBar, g);
            jp.add(soundw, g);
            g.weightx = 0;
            jp.add(save, g);
            if(isexisting) jp.add(delete, g);
            g.weightx = 1;
            g.insets = new Insets(0,0,0,0);
            add(jp, g);            
  
     soundw.addMouseListener(new MouseAdapter() {
        public void mouseReleased(MouseEvent me) {
            if(isexisting){
               save.setEnabled(soundIsUsers && (
                       
                   (soundw.start > 0 || soundw.end != (soundw.data.length)
                                  || soundw.beepx.length>0)
                       
                       )); 
            }
            
        }
     });                    
                    
                    
            save.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if(isexisting){
   //                     if(normalwords) savedata = currSpokenWord_existing.data;
   //                     else savedata = currSpokenWord2_existing.data;
                        if(normalwords){
                              if(soundw.start > 0 || soundw.end != currSpokenWord_existing.data.length
                                       || soundw.beepx.length > 0)
                                currSpokenWord_existing.data = soundw.getsound();
                        }
                        else{
                              if(soundw.start > 0 || soundw.end != currSpokenWord2_existing.data.length
                                       || soundw.beepx.length > 0)
                                currSpokenWord2_existing.data = soundw.getsound();

                        }
                        if(normalwords){
                           xres xrc = new xres(currSpokenWord_existing.name);
                           xrc.rec = currSpokenWord_existing.save2();
                           for(int i = 1; OwnWordLists.workingList!=null && OwnWordLists.workingList.names!=null && i < OwnWordLists.workingList.names.length; i++){
                               if(currSpokenWord_existing.name.equals(OwnWordLists.workingList.names[i])){
                                   OwnWordLists.workingList.recs[i] = xrc.rec;
                                   boolean found = false;
                                   for(int n = 0; OwnWordLists.workingList.names!=null && n < OwnWordLists.currrecs.size(); n++){
                                       if(((OwnWordLists.recitem)OwnWordLists.currrecs.get(n)).name.equals(OwnWordLists.workingList.names[i])){
                                           found = true;
                                           break;
                                       }
                                   }
                                   if(!found) OwnWordLists.currrecs.add(new OwnWordLists.recitem(OwnWordLists.workingList.names[i], OwnWordLists.workingList.recs[i]));   
                                   break;
                               }
                           }                        
     //                      currSpokenWord_existing.data =savedata;  // restore
                        }
                        else{
                            xres xrc = new xres(currSpokenWord2_existing.name);
                            xrc.rec = currSpokenWord2_existing.save2();
                            for(int i = 1; OwnWordLists.workingList!=null && OwnWordLists.workingList.names!=null &&i < OwnWordLists.workingList.names.length; i++){
                               if(currSpokenWord2_existing.name.equals(OwnWordLists.workingList.names[i])){
                                   OwnWordLists.workingList.extrarecs[i] = xrc.rec;
                                   break;
                               }
                           }                        
    //                       currSpokenWord2_existing.data =savedata;  // restore
                        }                        
                    }
                    else{
                        byte savedata[];
                        if(normalwords) savedata = currSpokenWord.data;
                        else savedata = currSpokenWord2.data;
                        if(normalwords){
                              if(soundw.start > 0 || soundw.end != currSpokenWord.data.length
                                       || soundw.beepx.length > 0)
                                currSpokenWord.data = soundw.getsound();
                        }
                        else{
                              if(soundw.start > 0 || soundw.end != currSpokenWord2.data.length
                                       || soundw.beepx.length > 0)
                                currSpokenWord2.data = soundw.getsound();
                        }
                        if(normalwords){
                           xres xrc = new xres(currSpokenWord.name);
                           xrc.rec = currSpokenWord.save2();
                           for(int i = 1; OwnWordLists.workingList!=null && OwnWordLists.workingList.names!=null && i < OwnWordLists.workingList.names.length; i++){
                               if(currSpokenWord.name.equals(OwnWordLists.workingList.names[i])){
                                   OwnWordLists.workingList.recs[i] = xrc.rec;
                                   boolean found = false;
                                   for(int n = 0; OwnWordLists.workingList.names!=null && n < OwnWordLists.currrecs.size(); n++){
                                       if(((OwnWordLists.recitem)OwnWordLists.currrecs.get(n)).name.equals(OwnWordLists.workingList.names[i])){
                                           found = true;
                                           break;
                                       }
                                   }
                                   if(!found) OwnWordLists.currrecs.add(new OwnWordLists.recitem(OwnWordLists.workingList.names[i], OwnWordLists.workingList.recs[i]));   
                                   break;
                               }
                           }                        
                           currSpokenWord.data =savedata;  // restore
                        }
                        else{
                            xres xrc = new xres(currSpokenWord2.name);
                            xrc.rec = currSpokenWord2.save2();
                            for(int i = 1; OwnWordLists.workingList!=null && OwnWordLists.workingList.names!=null &&i < OwnWordLists.workingList.names.length; i++){
                               if(currSpokenWord2.name.equals(OwnWordLists.workingList.names[i])){
                                   OwnWordLists.workingList.extrarecs[i] = xrc.rec;
                                   break;
                               }
                           }                        
                           currSpokenWord2.data =savedata;  // restore
                        }
                    }
                    save.setEnabled(false);
                    play.setEnabled(false);
                    listen.setEnabled(true);
                    if(ctrlRefreshOnSave!=null){
                        ctrlRefreshOnSave.dolisten(normalwords);
                    }
                    else delete.setEnabled(true);
                    if(normalwords) thiscontrol.setupSound(isexisting?currSpokenWord_existing.data:currSpokenWord.data);
                    else thiscontrol.setupSound(isexisting?currSpokenWord2_existing.data:currSpokenWord2.data);
                    if(!isexisting)clearSound();
                    ((OwnWordLists)parent).setbuttons();
                    ((OwnWordLists)parent).refreshwords();
                }
             });
            play.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    spokenWord sw = isexisting?(normalwords?currSpokenWord_existing:currSpokenWord2_existing):(normalwords?currSpokenWord:currSpokenWord2);
                    if(soundw.start > 0 || soundw.end != (sw.data.length)
                                  || soundw.beepx.length>0) {
                            spokenWord.say(soundw.getsound());
                            return;
                    }
                    sw.say();
                }
             });
            recstop.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                        if(!doingRecording) {
                        ((OwnWordLists)parent).stopWordsEdit();
                        recstop.setToolTipText(null);
                            clearSound();
                            normalactive = normalwords;
                            recstop.setIcon(recstopii);
                           if(spokenWord.mike != null)
                               return;
                            String ss;
                           if(normalwords){
                            ss = currSpokenWord.name;
                            currSpokenWord.autoboost = auto;
                           }
                           else{
                            ss = currSpokenWord2.name;
                            currSpokenWord2.autoboost = auto;
                           }
                           if(ss.length() > MAXNAMELEN){
                               if(normalwords)
                              new u.infotextpane(u.gettext("rec_","recsentence"),ss,recpanel1.getWidth()/6,recpanel1.getY() + recpanel1.getHeight()/4,
                                                        recpanel1.getWidth()/2,recpanel1.getHeight()/2);
                               else
                               new u.infotextpane(u.gettext("rec_","recsentence"),ss,recpanel2.getWidth()/6,recpanel2.getY() + recpanel2.getHeight()/4,
                                                        recpanel2.getWidth()/2,recpanel2.getHeight()/2);
                           }
                           doingRecording = true;
                           whilerecording(true);
                           if(normalwords) currSpokenWord.startRecording();
                           else currSpokenWord2.startRecording();
                           save.setEnabled(false);
                           play.setEnabled(false);
                        }
                        else {
                            haltrec();
                        }
                }
             });
            delete.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if(normalwords){                        
                        for(int i = 1; OwnWordLists.workingList!=null && OwnWordLists.workingList.names!=null && i < OwnWordLists.workingList.names.length; i++){
                            if((OwnWordLists.workingList.names[i].equals(currword)) 
 //                                   && OwnWordLists.workingList.recs[i].equals(currSpokenWord_existing.save2())
                                    ){
                                for(int j = 0; j < OwnWordLists.currrecs.size(); j++){
                                    if(((OwnWordLists.recitem)OwnWordLists.currrecs.get(j)).name.equals(OwnWordLists.workingList.names[i])){
                                        OwnWordLists.currrecs.remove(j);
                                    }
                                }
                                OwnWordLists.workingList.recs[i] = null;   
                                ((OwnWordLists)parent).setbuttons();
                                break;                                
                            }
                        }                        
                    }
                    else{                        
                        for(int i = 1; OwnWordLists.workingList!=null && OwnWordLists.workingList.names!=null && i < OwnWordLists.workingList.names.length; i++){
                            if((OwnWordLists.workingList.names[i].equals(currword))){
                                OwnWordLists.workingList.extrarecs[i] = null;
                                ((OwnWordLists)parent).setbuttons();
                                break;                                
                            }
                        }                                
                    }
                    delete.setEnabled(false);
//                    boolean enable = db.query(sharkStartFrame.publicSoundLib[0], currword, db.WAV) >= 0;
                    boolean enable = db.query(sharkStartFrame.getPrimarySoundDb(sharkStartFrame.publicSoundLib), currword, db.WAV) >= 0;
                    listen.setEnabled(enable);
                    if(enable){
                       dolisten(normalwords);   
                    }
                    else{
                        clearSound();
                    }
                    ((OwnWordLists)parent).refreshwords();
                }
             });
            listen.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                 spokenWord sw = normalwords?currSpokenWord_existing:currSpokenWord2_existing;
                 if(sw==null)return;
                 if(soundw.start > 0 || soundw.end != (sw.data.length)
                                  || soundw.beepx.length>0) {
                    spokenWord.say(soundw.getsound());
                    return;
                 }
                 sw.say();   
                }
             });
         }

         public spokenWord dolisten(boolean normal){
             
             soundIsUsers = true;
               if(normal){                        
                        for(int i = 1; OwnWordLists.workingList!=null && OwnWordLists.workingList.names!=null && i < OwnWordLists.workingList.names.length; i++){
                            if((OwnWordLists.workingList.names[i].equals(currword))
                                    && OwnWordLists.workingList.recs[i] != null){
                              if(currSpokenWord_existing != null) {
                                spokenWord.flushspeaker(true);
                                currSpokenWord_existing = null;
                              }
                              currSpokenWord_existing = new spokenWord(currword,sharkStartFrame.resourcesdb ,false);
                              currSpokenWord_existing.decomp(OwnWordLists.workingList.recs[i]);
                              soundw.showTrim = true;
                              setupSound(currSpokenWord_existing.data);
                              delete.setEnabled(true);
                              listen.setEnabled(true);
                              return currSpokenWord_existing;//.say();                           
                            }
                        }                         
                       
         for(int i = 0; i < sharkStartFrame.publicSoundLib.length; i++){
                      if (db.query(sharkStartFrame.publicSoundLib[i], currword, db.WAV) >= 0) {
                         if(currSpokenWord_existing != null) {
                            spokenWord.flushspeaker(true);
                            currSpokenWord_existing = null;
                         }
                         currSpokenWord_existing = new spokenWord(currword,sharkStartFrame.publicSoundLib[i] ,false);
                         currSpokenWord_existing.decomp();
                         soundw.showTrim = false;
                         setupSound(currSpokenWord_existing.data);
                         soundIsUsers = false;
                         delete.setEnabled(false);
                         listen.setEnabled(true);
                         return currSpokenWord_existing;
                     }        
                   }                         

                  }
                  else{                      
                        for(int i = 1; OwnWordLists.workingList!=null && OwnWordLists.workingList.names!=null && i < OwnWordLists.workingList.names.length; i++){
                            if((OwnWordLists.workingList.names[i].equals(currword)) &&
                                    OwnWordLists.workingList.extrarecs[i]!=null
                                    ){
                              if(currSpokenWord2_existing != null) {
                                spokenWord.flushspeaker(true);
                                currSpokenWord2_existing = null;
                              }
                              currSpokenWord2_existing = new spokenWord(currword,sharkStartFrame.resourcesdb ,false);
                              currSpokenWord2_existing.decomp(OwnWordLists.workingList.extrarecs[i]);
                              setupSound(currSpokenWord2_existing.data);
                              delete.setEnabled(true);
                              listen.setEnabled(true);
                              return currSpokenWord2_existing;                        
                            }
                        }                        
                  }
               listen.setEnabled(false);
               delete.setEnabled(false);
               return null;
         }

         void whilerecording(boolean start){
             pBar.setVisible(start);
             soundw.setVisible(!start);
         }

         public void haltrec(){
                         recstop.setToolTipText(rectooltip);
                           recstop.setIcon(recrecii);
                           if(normalwords) currSpokenWord.stopRecording();
                           else currSpokenWord2.stopRecording();
                           doingRecording=false;
                           save.setEnabled(true);
                           play.setEnabled(true);
                           whilerecording(false);
         }

         public void clearSound(){
             soundw.clear();
             owner.swchanged();
             
         }            
         
         public void setupSound(byte data1[]){
             soundw.setup(data1);
             owner.swchanged();
         }          
         
         
         public void setupSound(byte data1[],int len){
             soundw.setup(data1,len);
             owner.swchanged();
         }      
         
         public void setup(){
             boolean soundexists = false;
             boolean programsoundexists = false;
             if(normalwords){
                for(int i = 0; i < sharkStartFrame.publicSoundLib.length; i++){
                    if (db.query(sharkStartFrame.publicSoundLib[i], currword, db.WAV) >= 0) {
                       programsoundexists = true;
                       break;
                    }          
                }                
                for(int i = 1; OwnWordLists.workingList!=null && OwnWordLists.workingList.names!=null && i < OwnWordLists.workingList.names.length; i++){
                    if((OwnWordLists.workingList.names[i].equals(currword))){
                       soundexists = OwnWordLists.workingList.recs[i]!=null;
                       break;                                
                    }
                }              
             }
             else{                
                for(int i = 1; OwnWordLists.workingList!=null && OwnWordLists.workingList.names!=null && i < OwnWordLists.workingList.names.length; i++){
                    if((OwnWordLists.workingList.names[i].equals(currword))){
                       soundexists = OwnWordLists.workingList.extrarecs[i]!=null;
                       break;                                
                    }
                }                    
             }
             boolean enable = programsoundexists || soundexists;
             listen.setEnabled(enable);
             if(enable && isexisting){
                dolisten(normalwords);
             }
             else{
                clearSound();
             }
             delete.setEnabled(soundexists);
             play.setEnabled(false);
 //            if(!isexisting)soundw.clear();
         }
     }     
     
     
     }