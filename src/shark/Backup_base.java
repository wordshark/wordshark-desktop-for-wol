package shark;

import java.io.*;
import java.io.FileFilter;
import java.text.*;
import java.util.*;
import java.util.Timer;
import java.nio.channels.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

/**
 * <p>Backup: </p>
 * <p>Creates a tabbed pane for backing up .sha files and restoring them: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>White Space: </p>
 * @S Stewart
 * @version 1.0
 */

public class Backup_base {
//startSS2008-04-21^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
  /**
   * Holds the date of directories containing file for restoration
   */
  public long[] dateMilliSecs;
//endSS^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
//startSS2008-04-15^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
  /**
   * true if the user has cancelled the restore otherwise false
   */
  boolean cancelRestore = false;
//startPR2008-04-20^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR

  // not needed

//  /**
//   * False if the "yes" button has been pressed and so the "yesall" button is disabled
//   * i.e. each restore file is being restored individually
//   */
//  boolean blYesAll = true;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//endSS^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
  /**
  * True if chooser wants to restore all - otherwise false
  */
  boolean restoreAll = false;

  /**
   * names of files chosen to be restored
   */
  String[] restoreFiles;
//startPR2008-04-20^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR

  // I found this a bit confusing and I don't think it's needed. We can just
  // use the exists flag.

//  /**
//   * Allows all chosen files to overwrite those of same name when a restore is done
//   */
//  private boolean doRestoreAll = false;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  /**
   * The file that may be overwritten when a restore is done
   */
  private File duplicateFile;
  /**
   * The value currently displayed by the spinner in the schedule panel
   */
  private int currSpinnerValue;
//SS2008-03-20^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
  /**
   * The no of one off backups to be done - displayed by the spinner on the backup panel
   */
  private static int currNoOneOffBkps;
  /**
   * The no of scheduled backups to be done - displayed by the spinner on the
   * scheduled backup panel
   */
  private static int currNoSchBkps;
  /**
   * The no of backups to be done either scheduled or one off
   */
 // private static int currNoBkps;
//SSend^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS

  private static ScheduledBackup scheduledBkup;
  /**
   * Array containing names of files restored
   */
  String[] restoreDone;
  /**
   * Array containing names of files that couldn't be restored
   */
  String[] restoreNotDone;

  OneOffBackup oneOffBkup;
  /**
   * Appears whenever a back up has been done.
   */
  public static JWindow bkupDone;
  /**
   * the dialog for making choices about backups
   */
  public static BkupGUI GUI;
  String currStudent;
//startPR2008-04-20^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR

  // keeps track of how far we've cycled through the restoreFiles array
  // means that we can use the "yes to all" button at any time

  int restoreIndex;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2010-03-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  progress_base p;
//  String defaultBU = shark.licenceType.equals(shark.LICENCETYPE_USB)?
//      getDefaultPath()+File.separator+shark.companyName+File.separator + shark.programName+" "+shark.versionNo+" "+"USB"+File.separator +shark.usbSerial:
//      sharkStartFrame.sharedPath.toString();
  boolean isusb = shark.licenceType.equals(shark.LICENCETYPE_USB);
  String defaultBU =
      getDefaultPath()+File.separator+shark.companyName+File.separator + shark.programName+" "+shark.versionNo +
      shark.getVersionType(shark.licenceType) +
      (isusb?File.separator +shark.usbSerial:"");

//endPRtoupdate^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  int backupFileType = -1;
  static final int BU_FILETYPE_NORMAL = 0;
  static final int BU_FILETYPE_RESOURCE = 1;
  static final int BU_FILETYPE_RECORD = 2;
  ArrayList archives = new ArrayList();
  String arc = u.gettext("sturec_", "arcend");
  String rec = u.gettext("sturec_", "recend");

  /**
   * creates objects to deal with one off and scheduled backups. Then does a scheduled
   * backup if this is needed or produces the GUI for choosing the various back up
   * options.
   * @param wantGUI True if the GUI is to be displayed, false if no GUI is required.
   * When false checks to see if a scheduled back up needs to be done.
   */
  public Backup_base(boolean wantGUI) {
    scheduledBkup = new ScheduledBackup();
//SS2008-03-31^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
    //scheduledBkup.initialise();
//SSend^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
    oneOffBkup = new OneOffBackup();
    if(!wantGUI){
      scheduledBkup.BackupFiles("scheduled");
    }
    if (wantGUI) {
      setupArchives();   
      GUI = new BkupGUI();
    }
  }
  static String WS = u.gettext("backup_","WS");
//startPR2009-08-19^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  static File tempFile = new File(sharkStartFrame.sharedPath +
                                 File.separator +
                                 WS+"Temp" + ".lock"); //file acts as flag if present then db in use
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2008-04-08^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  static String tempDeleteFileName = "~"+WS+"DelTemp";
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  String username2;


  private void setupArchives(){
        File[] ff = (new File(sharkStartFrame.sharedPathplus+sharkStartFrame.recordsPlus)).listFiles(new FileFilter() {
        public boolean accept(File file) {
          return (file.getName()).endsWith(arc);}
        });
        for(int i = 0; ff!=null && i < ff.length; i++){
            String ss = ff[i].getName();
            int k = ss.indexOf(" - ");
            String sname = ss.substring(0, k);
            boolean found = false;
            for(int j = 0; j < archives.size(); j++){
                UserArchives ua = (UserArchives)archives.get(j);
                if(ua.user.equals(sname)){
                    ua.addArchive(ss);
                    archives.set(j, ua);
                    found = true;
                    break;
                }
            }
            if(!found)archives.add(new UserArchives(sname, ss));
        }
  }

   private UserArchives findArchive(String name){
           for(int j = 0; j < archives.size(); j++){
                UserArchives ua = (UserArchives)archives.get(j);
                if(ua.user.equals(name)){
                    return (UserArchives)archives.get(j);
                }
            }
           return null;
  } 
  
  
  /**
   * creates an array of the relevent files to be restored. Checks each file to see
   * whether it is present in the directory where the program looks for them.
   * @param restoreType Indicates which files are to be restored e.g the student
   * files or the extracourse file.
   */
  private void restore(String restoreType) {

    /**
     * indicates whether a particular file has been restored
     */
    boolean restored;
    // String temp = (String) GUI.dirList.getSelectedValue();
   // String date = temp.substring(temp.length() - 17, temp.length());
    //date = date.replace(':','-');
    /**
     * Directory containing files to be restored
     */
    restoreDone = new String[0];
    restoreNotDone = new String[0];
    File restoreDir = new File(Backup_base.scheduledBkup.restoreFrom.getFile().getAbsolutePath());
    restoreFiles = new String[GUI.studentList.getModel().getSize()];
    Object[] selected = GUI.studentList.getSelectedValues();
    if (restoreType.equals("students")) {
      for (int i = 0; i < selected.length; i++) {
        if ( selected[i] != null)
          //u.addString(restoreFiles,(String) selected[i] + ".sha");
          restoreFiles[i] = (String) selected[i] + ".sha";
      }
    }
    if (restoreType.equals("courses")) {
      File[] children = restoreDir.listFiles();
      int count = 0;
      for (int i = 0; i < children.length; i++) {
//SS2008-03-26^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
        if (children[i].getName().startsWith(sharkStartFrame.extracourses)) {
//SSend^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
          restoreFiles[count] = children[i].getName();
          count++;
        }
      }
    }
    if (restoreType.equals("keypad")) {
      File[] children = restoreDir.listFiles();
      int count = 0;
      for (int i = 0; i < children.length; i++) {
        if (children[i].getName().startsWith("keypad")) {
          restoreFiles[count] = children[i].getName();
          count++;
        }
      }
    }
//startPR2008-04-20^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    for (int i = 0; i < restoreFiles.length; i++) {
    for (restoreIndex = 0; restoreIndex < restoreFiles.length; restoreIndex++) {
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startSS2008-04-15^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
      if (!cancelRestore){
//endSS^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
        String name = "";
        if (!restoreAll) {
          restored = false;
//startPR2008-04-20^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//          if (restoreFiles[i] != null) { //Go through each chosen file
          if (restoreFiles[restoreIndex] != null) {
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            if (restoreFiles[0] == null) {
//startPR2008-04-20^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR

              // this was giving me a NullPointerException as GUI.overWrite hadn't
              // been initialized.

//              JOptionPane.showMessageDialog(GUI.overWrite,  // not initialized yet
              JOptionPane.showMessageDialog(((GUI.overWrite==null)?GUI.bkupMain:GUI.overWrite),
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                                            u.gettext("backup_noFile", "label"));
            }
            final File restoreFile = new File(restoreDir + File.separator +
//startPR2008-04-20^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//                                              restoreFiles[i]);
                                              restoreFiles[restoreIndex]);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            final boolean exists = (new File(sharkStartFrame.sharedPath +
                                             File.separator +
//startPR2008-04-20^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//                                              restoreFiles[i])).exists();
                                              restoreFiles[restoreIndex])).exists();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2008-04-20^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR

            // moved to lower down

//            if (exists) {
//              duplicateFile = new File(sharkStartFrame.sharedPath +
//                                       File.separator +
//                                              restoreFiles[i]);
//              doRestoreAll = true;
//            }


//           if (!doRestoreAll) {
           if (!exists) {

             // if the file doesn't exist in the shared folder we don't need to
             // close anything or delete anything

//             db.closeAll();
//             if (exists)
//               duplicateFile.delete();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startSS2008-04-15^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
//startPR2008-04-20^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//              restored = restoreFile(restoreFile);
              restored = restoreFile(restoreFile, null);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
              if (restoreFile.getName().endsWith(".sha")) {
                int end = restoreFile.getName().length() - 4;
                name = restoreFile.getName().substring(0, end);
              }
//startPR2008-04-20^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR

              // don't want dialog if restored ok - annoying to have to click
              // so many times if used in large school

//              if (restored) {
//                String message = name + u.gettext("backup_movecomplete", "label");
//                JOptionPane.showMessageDialog(GUI.overWrite, message);
//              }
//              else {
//                String message = name + u.gettext("backup_nomove", "label");
//                JOptionPane.showMessageDialog(GUI.overWrite, message);
//              }
              if (!restored) {
                JOptionPane.showMessageDialog(((GUI.overWrite==null)?GUI.bkupMain:GUI.overWrite),
                                              name + u.gettext("backup_nomove", "label"));
              }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
              GUI.restoration1.dispose();
//startSS2008-04-21^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
//The dialog was throwing a null pointer exception.
              if (GUI.overWrite != null){
//endSS^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
                  GUI.overWrite.dispose();
              }
//endSS^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
            }
//startPR2008-04-20^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//            if (doRestoreAll) {
            else {
              duplicateFile = new File(sharkStartFrame.sharedPath +
                                       File.separator + restoreFiles[restoreIndex]);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
              GUI.overWrite = GUI.addDialog(true);
              JPanel buttonPanel5 = new JPanel();
              if (restoreFile.getName().endsWith(".sha")) {
                int start = 0;
                int end = restoreFile.getName().length() - 4;
                name = restoreFile.getName().substring(start, end);
              }
              JLabel ask = new JLabel(name + " " +
                                      u.gettext("backup_overwrite", "label")); //"**** is already present. Do you wish to overwrite?"
//startSS2008-04-15^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
              JButton cancel = u.mbutton("cancel");
              cancel.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(ActionEvent e) {
                   cancelRestore = true;
                   GUI.overWrite.dispose();
                }
              });
//endSS^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
              final JButton yesAll = u.mbutton("backup_yesAll");
//startPR2008-04-20^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR

              // this button can stay enabled the whole time.

//              yesAll.setEnabled(blYesAll);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
              yesAll.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(ActionEvent e) {
//startPR2008-04-20^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR

                  // I'm not sure that this should be in - maybe we should discuss?

//                  db.closeAll();


                  // restore the users not yet dealt with

//                  restoreAll(restoreFiles);
                  String news[] = new String[restoreFiles.length-restoreIndex];
                  System.arraycopy(restoreFiles,restoreIndex,news,0,restoreFiles.length-restoreIndex);
                  restoreAll(news);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                  GUI.overWrite.dispose();
                  restoreAll = true;
//startPR2008-04-20^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//                  doRestoreAll = true;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                }
              });
//startPR2008-04-20^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//              Object[] values = GUI.dirList.getSelectedValues();
//              if (values.length <= 0) {
//                yesAll.setEnabled(false);
//              }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
              JButton yes = u.mbutton("backup_yes");
              yes.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(ActionEvent e) {
//startSS2008-04-15^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
//startPR2008-04-20^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//                  blYesAll = false;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                  boolean restored = false;
                  String name = "";
//startPR2008-04-20^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR

                  // I'm not sure that this should be in - maybe we should discuss?

//                  db.closeAll();


                  // leave deleting until last minute

//                  if (exists)
//                    duplicateFile.delete();
//                  restored = restoreFile(restoreFile);
                  restored = restoreFile(restoreFile, duplicateFile);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                  if (restoreFile.getName().endsWith(".sha")) {
                    int end = restoreFile.getName().length() - 4;
                    name = restoreFile.getName().substring(0, end);
                  }
//startPR2008-04-20^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR

                  // too many messages

//                  if (restored) {
//                    String message = name + u.gettext("backup_movecomplete", "label");
//                    JOptionPane.showMessageDialog(GUI.overWrite, message);
//                  }
//                  else {
                  if (!restored) {
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                    String message = name + u.gettext("backup_nomove", "label");
                    JOptionPane.showMessageDialog(GUI.overWrite, message);
                  }
                  GUI.restoration1.dispose();
                  GUI.overWrite.dispose();
//endSS^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
                }
              });

              JButton no = u.mbutton("backup_no");
              no.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(ActionEvent e) {
                  GUI.overWrite.dispose();
                }
              });
//startSS2008-04-15^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
              JButton[] buttons = {yes, cancel, no, yesAll};
              Dimension buttonSize = GUI.setCommonSize(buttons);
              yes.setPreferredSize(buttonSize);
              cancel.setPreferredSize(buttonSize);
              no.setPreferredSize(buttonSize);
              yesAll.setPreferredSize(buttonSize);
//endSS^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
              GridBagLayout gridBagLayout5 = new GridBagLayout();
              GridBagConstraints constraints5 = new GridBagConstraints();
              GUI.overWrite.setTitle(u.gettext("backup_restore", "label"));
      //        GUI.overWrite.setBounds(GUI.w / 16, GUI.h / 16, GUI.w * 3 / 8,
      //                                GUI.h * 1 / 4);
              GUI.overWrite.setBounds(u2_base.adjustBounds(new Rectangle(GUI.w / 16, GUI.h / 16, GUI.w * 3 / 8,
                                      GUI.h * 1 / 4)));
              
              GUI.overWrite.getContentPane().setLayout(gridBagLayout5);

              buttonPanel5.setLayout(gridBagLayout5);

              constraints5.insets = new Insets(0, 5, 0, 5);
              constraints5.ipadx = 40;
              constraints5.gridx = 0;
              buttonPanel5.add(yes, constraints5);
//startSS2008-04-15^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
              constraints5.ipadx = 0;
              constraints5.gridx = 1;
              buttonPanel5.add(yesAll, constraints5);

              constraints5.ipadx = 45;
              constraints5.gridx = 2;
              buttonPanel5.add(no, constraints5);

              constraints5.ipadx = 15;
              constraints5.gridx = 3;
              buttonPanel5.add(cancel, constraints5);
//endSS^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
              constraints5.insets = new Insets(0, 0, 0, 0);
              constraints5.anchor = constraints5.NORTH;
              constraints5.fill = constraints5.NONE;
              constraints5.ipadx = 0;
              GUI.overWrite.getContentPane().add(ask, constraints5);

              constraints5.gridy = 1;
              constraints5.anchor = constraints5.SOUTH;
              constraints5.insets = new Insets(20, 0, 0, 0);
              GUI.overWrite.getContentPane().add(buttonPanel5, constraints5);

              GUI.overWrite.setVisible(true);
            }
          }
        }
      }
    }
//startPR2008-04-20^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    if (!cancelRestore) u.okmess(shark.programName, u.gettext("backup_movecomplete","label"), sharkStartFrame.mainFrame);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  }

//startSS2008-04-15^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
  /**
   * Restores all files passed to it, provides a message saying which files have
   * been restored and which not
   * @param filesToMove The files to be restored
   */
  private void restoreAll(String[] filesToMove){
    int end = 0;
    String message = "";
    String notRestored = "";
    for (int i = 0; i < filesToMove.length; i++){
      File restoreDir = new File(Backup_base.scheduledBkup.restoreFrom.getFile().getAbsolutePath());
      final File restoreFile = new File(restoreDir + File.separator +
                                  filesToMove[i]);
      final boolean exists = (new File(sharkStartFrame.sharedPath +
                                 File.separator +
                                 restoreFiles[i])).exists();
      if (exists) {
        duplicateFile = new File(sharkStartFrame.sharedPath +
                           File.separator +
//startPR2008-04-20^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR

                           // wrong array

//                           restoreFiles[i]);
                           filesToMove[i]);


          // leave deleting until last minute

//        duplicateFile.delete();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      }
      boolean restored = false;
//startPR2008-04-20^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      restored = restoreFile(restoreFile);
      restored = restoreFile(restoreFile, exists?duplicateFile:null);


      // I think curly brackets were needed for the inner ifs?
      // Also a message for each successful restore is too many and not needed

//      if (restored){
//        if (restoreFile.getName().endsWith(".sha"))
//          end = restoreFile.getName().length() - 4;
//          String name = restoreFile.getName().substring(0, end);
//          message = message  + name + ", " ;
//      }
//      else{
//        if (restoreFile.getName().endsWith(".sha"))
//          notRestored = "\n";
//          end = restoreFile.getName().length() - 4;
//          String name = restoreFile.getName().substring(0, end);
//          notRestored = notRestored + name  + ", ";
//      }

      // instead show a message for every non-restored user - shouldn't be many

      if(!restored && restoreFile.getName().endsWith(".sha")){
        u.okmess(shark.programName, restoreFile.getName().substring(0, restoreFile.getName().length()-4)+
                 " "+u.gettext("backup_nomove", "label"));
      }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    }
//startPR2008-04-20^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    if (message.length() > 2){
//      message = message.substring(0,message.length()-2);
//      message = message + u.gettext("backup_movecomplete", "label");
//    }
//    if(notRestored.length() > 2){
//      notRestored = notRestored.substring(0,message.length()-2);
//      notRestored = notRestored + u.gettext("backup_nomove", "label");
//    }
//    message = message + notRestored;
//    JOptionPane.showMessageDialog(GUI.overWrite, message);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    GUI.restoration1.dispose();
  }
  //endSS^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS

  /**
   * Restores a particular file so it is in the home directory available for use.
   * @param fileToMove The file to be restored
   * @param fileToDelete The file that should be deleted first. Can be null.
   * @return boolean which is true if file has been restored otherwise is false
   */
  
  
  

  private boolean restoreFile(File fileToMove, File fileToDelete) {
    boolean restored = false;
    // check if these users are presently signed on - if they are then don't overwrite
    student stu = student.getStudent(fileToMove.getName().substring(0,fileToMove.getName().length()-4));
    if(stu!=null && !stu.getlock()) return false;
    if(fileToDelete!=null && fileToDelete.exists())
        fileToDelete.delete();
    restored = u.copyfile(fileToMove, new File(sharkStartFrame.sharedPathplus+fileToMove.getName()));
    
    String recordFiles[] = new String[]{};
    
    // restore the resources file too
    if(fileToDelete!=null){
        String resToDelete = fileToDelete.getParentFile().getAbsolutePath()+shark.sep+sharkStartFrame.resourcesPlus+convertNameToResources(fileToDelete.getName());
        File rftd = new File(resToDelete);
        if(rftd.exists()) rftd.delete();        
    }
   
        // don't restore if .rec is already there or if covered by a .arc file
        // find dates covered by arc files
     username2 = stu.name;
        File[] ff = (new File(fileToMove.getParentFile().getAbsolutePath()+shark.sep+sharkStartFrame.recordsPlus)).listFiles(new FileFilter() {
            public boolean accept(File file) {
              String s = file.getName();
              return s.startsWith(username2+" - "+shark.USBprefix) && (s.endsWith(rec) || s.endsWith(arc));
            }
        });
        UserArchives ua = findArchive(stu.name);
        for(int i = 0; ff!=null && i < ff.length; i++){
           if(!new File(sharkStartFrame.sharedPathplus+sharkStartFrame.recordsPlus+ff[i].getName()).exists()){
               if((ff[i].getName()).endsWith(arc)){
                   recordFiles = u.addString(recordFiles, ff[i].getAbsolutePath());
               }
               else{         
                   String s = ff[i].getName();
                   int len = s.length();
                   s = s.substring(len - 11 , len-4);
                   SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM");
                   Date d = null;
                   try{
                      d = sdf.parse(s);
                   }
                   catch(ParseException e){}
                   if(d!=null && (ua==null || !ua.isDateCovered(d)))recordFiles = u.addString(recordFiles, ff[i].getAbsolutePath());
               }
           }
        }    
    if(!shark.numbershark){
        String resToMove = fileToMove.getParentFile().getAbsolutePath()+shark.sep+sharkStartFrame.resourcesPlus+convertNameToResources(fileToMove.getName());
        File rftm = new File(resToMove);
        if(rftm.exists()){
            String resdir = sharkStartFrame.sharedPathplus+sharkStartFrame.resourcesPlus;
            File fresdir;
            if(!((fresdir = new File(resdir)).exists()))
                fresdir.mkdir();
            String resToMoveTo = resdir+convertNameToResources(fileToMove.getName());
            u.copyfile(rftm, new File(resToMoveTo)); 
        }
    }
    if(recordFiles.length>0){
        for(int i = 0; i < recordFiles.length; i++){
            String recdir = sharkStartFrame.sharedPathplus+sharkStartFrame.recordsPlus;
            File frecdir;
            if(!((frecdir = new File(recdir)).exists()))
                frecdir.mkdir();
            int k = recordFiles[i].lastIndexOf(shark.sep);
            if(k>=0)
                u.copyfile(new File(recordFiles[i]), new File(recdir+recordFiles[i].substring(k+1)));             
        }
    }


    



    
    if(stu!=null) stu.releaselock();
    return restored;
  }
  
    static private String convertNameToResources(String s) {
        String suffix = "-rs";
        String res = null;
        int k;
        if((k = s.lastIndexOf('.'))>=0){
            res = s.substring(0,k)+suffix+s.substring(k);
        }
        return res;   
    }

    static private String getUserNameFromFile(String s) {
        String res = null;
        int k;
        if((k = s.lastIndexOf('.'))>=0){
            res = s.substring(0,k);
        }
        return res;
    }

    /**
   * Used to ensure that string passed can be formatted as a date
   * @param strDate
   * @return true if date is a date otherwise false
   */
  private static boolean isDate(String strDate){
    boolean isDate = true;
    Format formatter = new SimpleDateFormat("dd-MM-yy HH-mm-ss");
    try {
      Date date = (Date) formatter.parseObject(strDate);
    } catch (ParseException ex) {
      isDate = false;
    }
    return isDate;
  }

  private class BkupGUI{
    private JComboBox location1;
    private JComboBox location2;
    private JComboBox location3;
//SS2008-03-24^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
    private JComboBox location4;
//SSend^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
//startPR2010-03-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    private JComboBox location5;
//endPRtoupdate^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    private int w = sharkStartFrame.mainFrame.getSize().width;
    private int h = sharkStartFrame.mainFrame.getSize().height;
    /**
     * The dialog in which the tabbed pane is placed
     */
    private BkupDialogue bkupMain;
    private JTabbedPane jtp;
    private JPanel backupPanel = new JPanel();
    private JPanel schedulePanel = new JPanel();
    private JPanel restorePanel = new JPanel();
//SS2008-03-24^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
    private JPanel deletePanel = new JPanel();
//end^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
//startPR2010-03-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    private JPanel stickPanel = new JPanel(new GridBagLayout());
//endPRtoupdate^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    /**
     * used if a single student needs to be restored
     */
    private JDialog backup1;
    /**
     * Pops up once a directory has been chosen to restore settings from and the restor
     * button is pressed.
     */
    private JDialog restoration1;
    /**
     * Pops up once a particular backup has been chosen and displays the student .sha files
     * there
     */
    private JDialog restoration2;
    /**
     * Used to list file system for the directory chooser
     */
//startPR2007-04-23^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    DirectoryChooser dc;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    private JDialog overWrite;
    /**
     * List of student files which can be restored from the chosen directory.
     */
    private JList dirList;
    private JList studentList;
    /**
     * Used to display help messages
     */
    private JDialog help;
    /**
     * Names of student files which have been individually selected for backup
     */
    String[] selectedNames;
//startPR2008-04-08^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    JLabel label5;
    String backupsizetext = u.gettext("backup_size", "label") + " ";
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2010-03-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    File usbRoot;
    File to;
    int countfiles = 0;
//endPRtoupdate^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    boolean directRestoreDir;

    public BkupGUI() {
      bkupMain = new BkupDialogue(sharkStartFrame.mainFrame,
                             u.gettext("backup_mainTitle", "label"));
      currStudent = sharkStartFrame.studentList[sharkStartFrame.
          currStudent].name;

      //Set up the tabbed pane
//      bkupMain.setBounds(w / 8, h / 8, w * 5/8, h * 1/2);
      bkupMain.setBounds(u2_base.adjustBounds(new Rectangle(w / 8, h / 8, w * 5/8, h * 1/2)));
      JPanel topPanel = new JPanel();
      topPanel.setLayout(new BorderLayout());
      bkupMain.getContentPane().add(topPanel);

//Create the tab pages
//startPR2010-03-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      if(isusb)
        createStickPanel();
//endPRtoupdate^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      createBackupPanel();
      createSchedulePanel();
      createRestorePanel();
//startPR2010-03-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      if(!isusb)
//endPRtoupdate^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//SS2008-03-24^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
        createDeletePanel();
//SSend^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS

      jtp = new JTabbedPane();
//startPR2010-03-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      if(shark.licencetype == shark.TYPE_USB){
//        jtp.addTab(u.gettext("backupUSB", "tabtitle"), stickPanel);
//      }
//endPRtoupdate^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      jtp.addTab(u.gettext("backup_Title", "label"), backupPanel);
//      jtp.addTab(u.gettext("backup_schedule", "label"), schedulePanel);
      jtp.addTab(u.gettext("backup_restore", "label")+"           ", restorePanel);
//startPR2010-03-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      if(!isusb)
//endPRtoupdate^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//SS2008-03-24^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
        jtp.addTab(u.gettext("backup_delete", "label"), deletePanel);
//SSend^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS

// Register a change listener
      jtp.addChangeListener(new ChangeListener() {
        // This method is called whenever the selected tab changes
        public void stateChanged(ChangeEvent evt) {
          JTabbedPane pane = (JTabbedPane) evt.getSource();
          // Get current tab
          int sel = pane.getSelectedIndex();
          if (0 <= sel && sel >= pane.getTabCount()) {
//            bkup.setTitle(jtp.getTitleAt(sel));
          }
        }
      });

      topPanel.add(jtp, BorderLayout.CENTER);
      bkupMain.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
      bkupMain.validate();
//startPR2005-12-31^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      bkupMain.setSize(((int)Math.max(w*5/8, bkupMain.getPreferredSize().getWidth())), bkupMain.getHeight());
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      bkupMain.setVisible(true);
    }

//startPR2010-03-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR

      private void createStickPanel() {
          stickPanel = new JPanel(new GridBagLayout());
          GridBagConstraints grid1 = new GridBagConstraints();
          grid1.gridx = 0;
          grid1.gridy = -1;
          grid1.weightx = 1;
          grid1.weighty = 0;
          grid1.fill = grid1.NONE;
          JPanel browsePan = new JPanel(new GridBagLayout());
          location5 = new JComboBox();
          location5.setBorder(BorderFactory.createEtchedBorder());
          scheduledBkup.addDir(location5);
          if (location5.getItemCount() == 0) {
              Calendar cal = Calendar.getInstance();
              String month = String.valueOf(cal.get(Calendar.MONTH)+1);
              if(month.length()==1)month = "0"+month;
              String date = String.valueOf(cal.get(Calendar.DAY_OF_MONTH))+"-"+
                     month+"-"+
                     String.valueOf(cal.get(Calendar.YEAR));

              location5.addItem(defaultBU + File.separator+
                     u.gettext("backupUSB", "wholebackupfolder")+" "+date);
          }
          grid1.insets = new Insets(5, 0, 5, 0);
          browsePan.add(location5, grid1);
          JButton browse = new JButton(u.gettext("browse", "label"));
          browse.addActionListener(new java.awt.event.ActionListener() {
              public void actionPerformed(ActionEvent e) {
                createDirectoryChooser("stick");
              }
          });

          JButton backupstick = new JButton("Backup");
          backupstick.addActionListener(new java.awt.event.ActionListener() {
              public void actionPerformed(ActionEvent e) {
                  try {
                      Thread myThread;
                      p = new progress_base(bkupMain, shark.programName,
                              u.gettext("backupUSB", "copying"),
                              new Rectangle(sharkStartFrame.screenSize.width / 4,
                              sharkStartFrame.screenSize.height * 2 / 5,
                              (sharkStartFrame.screenSize.width / 2),
                              (sharkStartFrame.screenSize.height / 4)), false);
                      myThread = new Thread(new StickBackup(String.valueOf(location5.getSelectedItem())));
                      myThread.start();
                      to = new File(String.valueOf(location5.getSelectedItem()));
                      if(!shark.macOS){
                          usbRoot = new File(sharkStartFrame.sharedPathplus.substring(0,3));
                      }
                      else{
                          String sep = File.separator;
                          usbRoot = new File(sep + "Volumes" + sep + shark.programName.toUpperCase() + sep);
                      }
                      new Thread(new StickTime()).start();
                  } catch (Exception ex) {
                  }
              }
          });

          JButton cancel = new JButton("Cancel");
          cancel.addActionListener(new java.awt.event.ActionListener() {
              public void actionPerformed(ActionEvent e) {
                  bkupMain.dispose();
              }
          });

          JButton bthelp = new JButton("Help");
          bthelp.addActionListener(new java.awt.event.ActionListener() {
              public void actionPerformed(ActionEvent e) {
                createHelpDialog(bkupMain, "backupHelp_stick");
              }
          });

          JButton[] buttons = {backupstick, cancel, bthelp};
          Dimension buttonSize = setCommonSize(buttons);
          backupstick.setPreferredSize(buttonSize);
          backupstick.setMinimumSize(buttonSize);
          cancel.setPreferredSize(buttonSize);
          cancel.setMinimumSize(buttonSize);
          bthelp.setPreferredSize(buttonSize);
          bthelp.setMinimumSize(buttonSize);
          grid1.insets = new Insets(20, 0, 0, 0);
          browsePan.add(browse, grid1);
          browsePan.setBorder(BorderFactory.createEtchedBorder());
          JPanel buttonPanel = new JPanel(new GridBagLayout());
          grid1.gridx = -1;
          grid1.gridy = 0;
          grid1.insets = new Insets(0, 10, 0, 10);
          buttonPanel.add(backupstick, grid1);
          buttonPanel.add(cancel, grid1);
          buttonPanel.add(bthelp, grid1);
          grid1.gridx = 0;
          grid1.gridy = -1;
          JLabel label1 =  new JLabel(u.gettext("backupUSB", "dostick1"));
          grid1.insets = new Insets(0, 0, 20, 0);
          stickPanel.add(label1, grid1);
          grid1.insets = new Insets(0, 0, 0, 0);
          grid1.ipady = 0;
          grid1.ipadx = 0;
          grid1.ipady = 25;
          grid1.ipadx = 20;
          stickPanel.add(browsePan, grid1);
           grid1.insets = new Insets(20, 0, 0, 0);
          stickPanel.add(buttonPanel, grid1);
      }

    private class StickBackup implements Runnable {
        String to;

        public StickBackup(String s) {
            to = s;
        }

        public void run() {
            try {
                File f = new File(to);
                f.mkdirs();
                if (!f.exists()) {
                    u.okmess(shark.programName, u.gettext("backupUSB", "nocreate"));
                    return;
                }
                File usbRoot;
                String cmds[];
                if(shark.macOS){
                    String sep = File.separator;
                    String usbstick = sep + "Volumes" + sep + shark.programName.toUpperCase() + sep;
                    usbRoot = new File(usbstick);
                    File ff[] = usbRoot.listFiles();
                    for(int i = 0; i < ff.length; i++){
                        cmds = new String[]{
                            "cp", "-r", ff[i].getAbsolutePath(), to};
                        Runtime.getRuntime().exec((cmds));
                    }
                }
                else{
                    usbRoot = new File(sharkStartFrame.sharedPathplus.substring(0, 3));
                    cmds = new String[]{
                        "xcopy", usbRoot.getAbsolutePath(),
                        to, "/e", "/h", "/y", "/c"};
                    Runtime.getRuntime().exec((cmds));
                }
            } catch (Exception b) {
            }
        }
    }


    private class StickTime implements Runnable {
        public void run() {
            try {
                while (true) {
                    Thread.currentThread().sleep(1000);
                    if (usbRoot.listFiles().length == to.listFiles().length) {
                        if(!shark.macOS){
                            File fs[] = to.listFiles();
                            for (int i = 0; i < fs.length; i++){
                                String cmds[] = new String[]{
                                    "attrib", "-H", fs[i].getAbsolutePath()};
                                Process proc = Runtime.getRuntime().exec((cmds));
                                proc.waitFor();
                            }
                        }
                        p.dispose();
                        p = null;
                        break;
                    }
                }
            } catch (Exception ex) {
            }
        }
    }
//endPRtoupdate^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR

    private void createBackupPanel() {
      JPanel browsePanel1 = new JPanel();
//startSS2008-03-19^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
      JPanel deletePanel = new JPanel();
      if (currNoOneOffBkps == 0){
        currNoOneOffBkps = 5;
      }
      SpinnerNumberModel model = new SpinnerNumberModel(currNoOneOffBkps, 1, 1000, 1);
      final JSpinner deleteNo = new JSpinner(model);
      final ButtonGroup deleteBkUps = new ButtonGroup();
      JRadioButton deleteAllBkUps = new JRadioButton(u.gettext("backup_deletion", "label"),true);
      JRadioButton saveBkUps = new JRadioButton(u.gettext("backup_saveBackups","label"));

      ChangeListener deleteListener = new ChangeListener() {
        public void stateChanged(ChangeEvent e) {
          if (deleteNo.isEnabled()) {
              deleteNo.setEnabled(false);
          }
          else{
              deleteNo.setEnabled(true);
          }
        }
      };
      deleteAllBkUps.addChangeListener(deleteListener);

      deleteNo.addChangeListener(new ChangeListener() {
        public void stateChanged(ChangeEvent evt) {
          currNoOneOffBkps = ( (Number) ( ( (JSpinner) evt.getSource()).
                                   getValue())).intValue();
        }
      });

      deleteBkUps.add(deleteAllBkUps);
      deleteBkUps.add(saveBkUps);
//endSS^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
      JPanel buttonPanel1 = new JPanel();
      location1 = new JComboBox();
      JButton browse1 = new JButton(u.gettext("backup_browse", "label"));
      JLabel label1 = new JLabel(u.gettext("backup_location", "label"));
      JButton bkupAll = new JButton(u.gettext("backup_bkupall", "label"));
      bkupAll.setText(u.gettext("backup_bkupall", "label"));
      JButton bkupStu = new JButton(u.gettext("backup_bkupstu", "label"));
      bkupStu.setText(u.gettext("backup_bkupstu", "label"));
      JButton cancel1 = new JButton(u.gettext("yesnocancel", "cancel"));
      final JButton help1 = new JButton(u.gettext("backup_help", "label"));
      GridBagLayout gridBagLayout1 = new GridBagLayout();
      backupPanel.setLayout(gridBagLayout1);
      browsePanel1.setLayout(gridBagLayout1);
//startSS2008-03-19^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
      deletePanel.setLayout(gridBagLayout1);
//endSS^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS

      buttonPanel1.setLayout(gridBagLayout1);

      oneOffBkup.addDir(location1);
      location1.setBorder(BorderFactory.createEtchedBorder());
      if (location1.getItemCount() == 0) {
//startPR2010-03-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//        location1.addItem(sharkStartFrame.sharedPath + File.separator +
//                          WS+"backups");
        location1.addItem(defaultBU + File.separator+WS+"backups");
//endPRtoupdate^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      }
      location1.setEditable(false);

      label1.setText(u.gettext("backup_location", "label"));
      JButton[] buttons = {bkupAll,cancel1,help1,bkupStu };
      Dimension buttonSize = setCommonSize(buttons);
      bkupAll.setPreferredSize(buttonSize);
      bkupAll.setMinimumSize(buttonSize);
      bkupAll.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          String location = (String)location1.getSelectedItem();
          Bckup.chosenFile = new File(location);
          oneOffBkup.BackupFiles("oneOff");
          bkupMain.dispose();
        }
      });
      bkupStu.setPreferredSize(buttonSize);
      bkupStu.setMinimumSize(buttonSize);
      bkupStu.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          createBackupDialog1();
        }
      });

      cancel1.setPreferredSize(buttonSize);
      cancel1.setMinimumSize(buttonSize);
      cancel1.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          bkupMain.dispose();
        }
      });

      help1.setPreferredSize(buttonSize);
      help1.setMinimumSize(buttonSize);
      help1.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
//startPR2005-12-31^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//            createHelpDialog("backupHelp_oneOff");
              createHelpDialog(bkupMain, "backupHelp_oneOff");
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        }
      });
      browse1.setPreferredSize(new Dimension( (int) browse1.getPreferredSize().
                                             getWidth(),
                                             (int) location1.getPreferredSize().
                                             getHeight()));
      browse1.addActionListener(new java.awt.event.
                                ActionListener() {
        public void actionPerformed(ActionEvent e) {
          createDirectoryChooser("backup");
        }
      });

      GridBagConstraints constraints1 = new GridBagConstraints();
      constraints1.insets = new Insets(0, 10, 0, 10);
      constraints1.fill = GridBagConstraints.NONE;
      buttonPanel1.add(bkupAll, constraints1);
      buttonPanel1.add(bkupStu, constraints1);
      buttonPanel1.add(cancel1, constraints1);
      buttonPanel1.add(help1, constraints1);
      constraints1.insets = new Insets(0,0,0,0);

      constraints1.anchor = GridBagConstraints.WEST;
      constraints1.insets = new Insets(5,5,5,5);
      browsePanel1.add(location1, constraints1);

      constraints1.ipadx = 0;
      constraints1.ipady = 0;
      constraints1.gridy = 1;
      constraints1.anchor = GridBagConstraints.CENTER;
      browsePanel1.add(browse1, constraints1);
      browsePanel1.setBorder(BorderFactory.createEtchedBorder());

//startSS2008-03-19^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
      //Adds facility to limit number of one off backups
//      constraints1.anchor = GridBagConstraints.NORTHWEST;
//      constraints1.ipadx = 10;
//     constraints1.ipady = 6;
//      constraints1.gridy = 0;
//      deletePanel.add(deleteAllBkUps, constraints1);

//      constraints1.gridx = deleteAllBkUps.RIGHT;
//      deletePanel.add(deleteNo);

//      constraints1.gridx = 0;
//      constraints1.gridy = 1;
//      deletePanel.add(saveBkUps, constraints1);
//      deletePanel.setBorder(BorderFactory.createEtchedBorder());

//      constraints1.insets = new Insets(20, 0, 0, 0);
//      constraints1.gridwidth = 4;
//      constraints1.fill = constraints1.HORIZONTAL;
//      constraints1.gridx = 0;
//      constraints1.gridy = 0;
//      backupPanel.add(deletePanel, constraints1);
//endSS^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
      constraints1.gridx = 0;
      constraints1.gridy = -1;
      constraints1.weighty = 0;
      constraints1.fill = GridBagConstraints.HORIZONTAL;
      constraints1.anchor = GridBagConstraints.WEST;
      JPanel mpan = new JPanel(new GridBagLayout());
      mpan.add(label1, constraints1);
      constraints1.anchor = GridBagConstraints.CENTER;
      mpan.add(browsePanel1,constraints1);
      constraints1.fill = GridBagConstraints.BOTH;
      constraints1.weighty = 1;
      constraints1.weightx = 1;
      backupPanel.add(mpan, constraints1);
      backupPanel.add(buttonPanel1, constraints1);
    }

    private void createSchedulePanel() {
 //startSS2008-03-19^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
      JPanel deletePanel = new JPanel();
      if (currNoSchBkps == 0){
        currNoSchBkps = 5;
      }
      SpinnerNumberModel model = new SpinnerNumberModel(currNoSchBkps, 1, 1000, 1);
      final JSpinner deleteNo = new JSpinner(model);
      final ButtonGroup deleteBkUps = new ButtonGroup();
      final JRadioButton deleteAllBkUps = new JRadioButton(u.gettext("backup_deletion", "label"),true);
      final JRadioButton saveBkUps = new JRadioButton(u.gettext("backup_saveBackups","label"));

      ChangeListener deleteListener = new ChangeListener() {
        public void stateChanged(ChangeEvent e) {
          if (deleteNo.isEnabled()) {
              deleteNo.setEnabled(false);
          }
          else{
              deleteNo.setEnabled(true);
          }
        }
      };
      deleteAllBkUps.addChangeListener(deleteListener);

      deleteNo.addChangeListener(new ChangeListener() {
        public void stateChanged(ChangeEvent evt) {
          currNoSchBkps = ( (Number) ( ( (JSpinner) evt.getSource()).
                                   getValue())).intValue();
        }
      });

      deleteBkUps.add(deleteAllBkUps);
      deleteBkUps.add(saveBkUps);
//endSS^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS

      final JPanel buttonPanel2 = new JPanel();
      final JPanel browsePanel2 = new JPanel();
      location2 = new JComboBox();
      final JButton browse2 = new JButton(u.gettext("backup_browse", "label"));
      final JLabel label2 = new JLabel(u.gettext("backup_location", "label"));
 //     final JLabel labelActive;
      final JButton OK2 = new JButton(u.gettext("backup_OK", "label"));
      final JButton cancel2 = new JButton(u.gettext("yesnocancel", "cancel"));
      JButton help2 = new JButton(u.gettext("backup_help", "label"));
      final JButton deactivate = new JButton(u.gettext("backup_deactivate","label"));
      deactivate.setText(u.gettext("backup_deactivate","label"));
      final JButton active = new JButton(u.gettext("backup_activate","label"));
      active.setText(u.gettext("backup_activate","label"));
      final JSpinner chooseDays;
      final JLabel label3 = new JLabel(u.convertToHtml(u.gettext("backup_days", "label")));
      GridBagLayout gridBagLayout2 = new GridBagLayout();
      schedulePanel.setLayout(gridBagLayout2);
      buttonPanel2.setLayout(gridBagLayout2);
      browsePanel2.setLayout(gridBagLayout2);
//startSS2008-03-19^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
      deletePanel.setLayout(gridBagLayout2);
//endSS^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS

      int min = 1;
      int max = 9;
      int step = 1;
      if (scheduledBkup.schedule == null || scheduledBkup.schedule[1].equals("inactive")) {
        currSpinnerValue = 7;
      }
      else{
        currSpinnerValue = Integer.parseInt(scheduledBkup.schedule[2]);
      }
      if (currSpinnerValue < min || currSpinnerValue > max) {
        currSpinnerValue = 7;
      }
      SpinnerModel model2 = new SpinnerNumberModel(currSpinnerValue, min, max,
                                                  step);
      chooseDays = new JSpinner(model2);

      /*
      if (Bckup.schedule != null && Bckup.schedule[0].equals("inactive")) {
//        labelActive = new JLabel(u.gettext("backup_notactive", "label"));
        scheduledBkup.active = false;
        browse2.setEnabled(false);
        location2.setEnabled(false);
        OK2.setEnabled(false);
        cancel2.setEnabled(false);
        chooseDays.setEnabled(false);
        label2.setEnabled(false);
        label3.setEnabled(false);
        deleteAllBkUps.setEnabled(false);
        deleteNo.setEnabled(false);
        saveBkUps.setEnabled(false);
      }
      else {
 //       labelActive = new JLabel(u.gettext("backup_active", "label"));
        scheduledBkup.active = true;
      }
       */
      scheduledBkup.active = false;

      chooseDays.setPreferredSize(new Dimension(w/25, h/25));
      chooseDays.addChangeListener(new ChangeListener() {
        public void stateChanged(ChangeEvent evt) {
          currSpinnerValue = ( (Number) ( ( (JSpinner) evt.getSource()).
                                         getValue())).intValue();
        }
      });

      location2.setBorder(BorderFactory.createEtchedBorder());
      scheduledBkup.addDir(location2);
      if (location2.getItemCount() == 0) {
//startPR2010-03-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//        location2.addItem(sharkStartFrame.sharedPath + File.separator +
//                          WS+"backups");
        location2.addItem(defaultBU + File.separator+WS+"backups");
//endPRtoupdate^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      }

      JButton[] buttons = {browse2,OK2,cancel2,help2,deactivate,active};
      Dimension buttonSize = setCommonSize(buttons);
      OK2.setPreferredSize(buttonSize);

      OK2.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          int nowno = ((Number)(deleteNo.getValue())).intValue();
          if(currNoOneOffBkps != nowno){
            String bb = String.valueOf(nowno);
            String aa[] = new String[]{bb, bb};
            db.update(sharkStartFrame.optionsdb, "backup_numbackups", aa,  db.TEXT);
          }
          scheduledBkup.newSchedule();
          bkupMain.dispose();
        }
      });

//      browse2.setPreferredSize(buttonSize);
      browse2.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          createDirectoryChooser("schedule");
        }
      });

//      labelActive.setForeground(Color.red);

      cancel2.setPreferredSize(buttonSize);
      cancel2.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          bkupMain.dispose();
        }
      });

      help2.setPreferredSize(buttonSize);
      help2.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
//startPR2005-12-31^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//          createHelpDialog("backupHelp_schedule");
          createHelpDialog(bkupMain, "backupHelp_schedule");
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        }
      });

      deactivate.setPreferredSize(buttonSize);
      deactivate.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
//          labelActive.setText(u.gettext("backup_notactive", "label"));
          buttonPanel2.add(active);
          buttonPanel2.remove(deactivate);
          browse2.setEnabled(false);
          location2.setEnabled(false);
          cancel2.setEnabled(false);
          chooseDays.setEnabled(false);
          label2.setEnabled(false);
          label3.setEnabled(false);
          scheduledBkup.active = false;
          deleteAllBkUps.setEnabled(false);
          deleteNo.setEnabled(false);
          saveBkUps.setEnabled(false);
        }
      });

      active.setPreferredSize(buttonSize);
      active.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
//          labelActive.setText(u.gettext("backup_active", "label"));
          buttonPanel2.add(deactivate);
          buttonPanel2.remove(active);
          scheduledBkup.active = true;
          browse2.setEnabled(true);
          location2.setEnabled(true);
          OK2.setEnabled(true);
          cancel2.setEnabled(true);
          chooseDays.setEnabled(true);
          label2.setEnabled(true);
          label3.setEnabled(true);
          deleteAllBkUps.setEnabled(true);
          deleteNo.setEnabled(true);
          saveBkUps.setEnabled(true);
        }
      });

      GridBagConstraints constraints2 = new GridBagConstraints();
      constraints2.fill = GridBagConstraints.NONE;
      constraints2.anchor = GridBagConstraints.CENTER;
      constraints2.weightx = 0;
      constraints2.weighty = 0;
      constraints2.gridy = -1;
      constraints2.gridx = 0;
      constraints2.insets = new Insets(5,5,5,5);
      browsePanel2.add(location2,constraints2);
      constraints2.insets = new Insets(0,5,5,5);
      browsePanel2.add(browse2,constraints2);
      constraints2.insets = new Insets(5,5,5,5);
      browsePanel2.setBorder(BorderFactory.createEtchedBorder());
      JPanel mpan = new JPanel(new GridBagLayout());
      mpan.add(label2, constraints2);
      mpan.add(browsePanel2, constraints2);
      constraints2.insets = new Insets(0,0,0,0);
      deleteAllBkUps.setText(u.convertToHtml(deleteAllBkUps.getText()));
      constraints2.gridy = 0;
      constraints2.gridx = -1;
      JPanel delete_top = new JPanel(new GridBagLayout());
      delete_top.add(deleteAllBkUps, constraints2);
      delete_top.add(deleteNo);
      constraints2.gridy = -1;
      constraints2.gridx = 0;
      constraints2.anchor = GridBagConstraints.WEST;
      deletePanel.add(delete_top, constraints2);
      deletePanel.add(saveBkUps, constraints2);
      constraints2.anchor = GridBagConstraints.CENTER;
      deletePanel.setBorder(BorderFactory.createEtchedBorder());
      constraints2.gridy = 0;
      constraints2.gridx = -1;
      JPanel pnspinnerdays = new JPanel(new GridBagLayout());
      pnspinnerdays.add(label3, constraints2);
      constraints2.insets = new Insets(0,10,0,0);
      pnspinnerdays.add(chooseDays, constraints2);
      constraints2.insets = new Insets(0,0,0,0);
      constraints2.weightx = 1;
      JPanel pnSpinners = new JPanel(new GridBagLayout());
      pnSpinners.add(pnspinnerdays, constraints2);
      pnSpinners.add(deletePanel, constraints2);
      constraints2.weightx = 0;
      GridBagConstraints constrButton = new GridBagConstraints();
      constrButton.insets = new Insets(0,5,0,5);
      constraints2.gridx = -1;
      buttonPanel2.add(OK2, constrButton);
      buttonPanel2.add(cancel2, constrButton);
      buttonPanel2.add(help2, constrButton);
      if (scheduledBkup.active)
        buttonPanel2.add(deactivate, constrButton);
      else
        buttonPanel2.add(active, constrButton);
      constraints2.gridy = -1;
      constraints2.gridx = 0;
      constraints2.weightx = 1;
      constraints2.weighty = 1;
      constraints2.fill = GridBagConstraints.BOTH;
      constrButton.insets = new Insets(0,0,0,0);
      JPanel pnMain = new JPanel(new GridBagLayout());
      pnMain.add(mpan, constraints2);
      pnMain.add(pnSpinners, constraints2);
      pnMain.add(buttonPanel2, constraints2);
      constrButton.insets = new Insets(5,5,5,5);
      constraints2.fill = GridBagConstraints.VERTICAL;
      schedulePanel.add(pnMain, constraints2);
    }

    private void createRestorePanel() {
      JPanel browsePanel3 = new JPanel();
      JPanel buttonPanel3 = new JPanel();
      JLabel label4 = new JLabel(u.gettext("backup_settings", "label"));
      JButton browse3 = new JButton(u.gettext("backup_browse", "label"));
      JButton cancel3 = new JButton(u.gettext("yesnocancel", "cancel"));
      JButton help3 = new JButton(u.gettext("backup_help", "label"));
      JButton OK = new JButton(u.gettext("backup_OK", "label"));
      location3 = new JComboBox();
      GridBagLayout gridBagLayout3 = new GridBagLayout();
      restorePanel.setLayout(gridBagLayout3);
      browsePanel3.setLayout(gridBagLayout3);
      buttonPanel3.setLayout(gridBagLayout3);

      location3.setBorder(BorderFactory.createEtchedBorder());
      oneOffBkup.addDir(location3);
      scheduledBkup.addDir(location3);
      if (location3.getItemCount() == 0) {
//startPR2010-03-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//        location3.addItem(sharkStartFrame.sharedPath + File.separator +
//                          WS+"backups");
        location3.addItem(defaultBU + File.separator+WS+"backups");
//endPRtoupdate^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      }

      label4.setText(u.gettext("backup_selectfile", "label"));

      JButton[] buttons = {
          OK, cancel3, browse3, help3};
      Dimension buttonSize = setCommonSize(buttons);
      browse3.setPreferredSize(buttonSize);
      browse3.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          createDirectoryChooser("restore");
        }
      });

      cancel3.setPreferredSize(buttonSize);
      cancel3.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          bkupMain.dispose();
        }
      });

      help3.setPreferredSize(buttonSize);
      help3.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
//startPR2005-12-31^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//            createHelpDialog("backupHelp_restore1");
          createHelpDialog(bkupMain, "backupHelp_restore1");
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        }
      });

      OK.setText(u.gettext("backup_OK", "label"));
      OK.setPreferredSize(buttonSize);
      OK.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          createRestoreDialog1();
        }
      });

      GridBagConstraints constraints3 = new GridBagConstraints();
      constraints3.fill = GridBagConstraints.HORIZONTAL;
      constraints3.ipadx = constraints3.ipady = 0;
      constraints3.insets = new Insets(0, 5, 0, 5);
      constraints3.anchor = GridBagConstraints.WEST;
      buttonPanel3.add(OK, constraints3);
      constraints3.anchor = GridBagConstraints.CENTER;
      buttonPanel3.add(cancel3, constraints3);
      constraints3.anchor = GridBagConstraints.EAST;
      buttonPanel3.add(help3, constraints3);

      
      
      constraints3.insets = new Insets(5,5,5,5);
      
      constraints3.anchor = GridBagConstraints.WEST;
      constraints3.fill = GridBagConstraints.NONE;
      constraints3.ipadx = 10;
      constraints3.ipady = 6;
      browsePanel3.add(location3, constraints3);
      constraints3.ipady = 0;
      constraints3.ipadx = 0;

      constraints3.gridy = 1;
      constraints3.anchor = GridBagConstraints.CENTER;
      browsePanel3.add(browse3, constraints3);
      browsePanel3.setBorder(BorderFactory.createEtchedBorder());
     
      constraints3.gridx = 0;
      constraints3.gridy = -1;
      constraints3.weighty = 0;
      constraints3.fill = GridBagConstraints.HORIZONTAL;
      constraints3.anchor = GridBagConstraints.WEST;
      JPanel mpan = new JPanel(new GridBagLayout());
      mpan.add(label4, constraints3);
      constraints3.anchor = GridBagConstraints.CENTER;
      mpan.add(browsePanel3,constraints3);
      constraints3.fill = GridBagConstraints.BOTH;
      constraints3.weighty = 1;
      constraints3.weightx = 1;
      restorePanel.add(mpan, constraints3);
      restorePanel.add(buttonPanel3, constraints3);      
      
      
    }
//startPR2008-04-08^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    void setSizeLabelText(){
      if(label5!=null) label5.setText(backupsizetext + scheduledBkup.currBackupSize());
    }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//SS2008-03-26^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
    /**
     * Provides an interface for finding and deleting a particular backup
     */
    private void createDeletePanel(){
      JPanel browsePanel3 = new JPanel();
      JPanel buttonPanel3 = new JPanel();
      JLabel label4 = new JLabel(u.gettext("backup_settings", "label"));
//startPR2008-04-08^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      String backupSize = Backup.scheduledBkup.currBackupSize();
//      JLabel label5 = new JLabel(u.gettext("backup_size", "label") + " " + backupSize);
      label5 = new JLabel();
      setSizeLabelText();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      JButton browse3 = new JButton(u.gettext("backup_browse", "label"));
      JButton undo = new JButton(u.gettext("undo", "label"));
//startPR2008-06-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      JButton close = new JButton(u.gettext("backup_close", "label"));
      JButton close = new JButton(u.gettext("close", "label"));
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      JButton delete = new JButton(u.gettext("delete", "label"));
      location4 = new JComboBox();
      GridBagLayout gridBagLayout3 = new GridBagLayout();
      deletePanel.setLayout(gridBagLayout3);
      browsePanel3.setLayout(gridBagLayout3);
      buttonPanel3.setLayout(gridBagLayout3);

      location4.setBorder(BorderFactory.createEtchedBorder());
      Dimension dim = new Dimension(bkupMain.getWidth()-20,70);
      location4.setMaximumSize(dim);
//SS2008-03-28^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
      scheduledBkup.addFile(location4);
//SS2008-03-31^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
      if (location4.getItemCount() == 0){
        location4.setVisible(false);
      }
      else{
        location4.setVisible(true);
      }
//SSend^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
//SSend^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
      label4.setText(u.gettext("backup_deletefile", "label"));
      JButton[] buttons = {
          delete, undo, browse3, close};
      Dimension buttonSize = setCommonSize(buttons);
      browse3.setPreferredSize(buttonSize);
      browse3.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          createDirectoryChooser("delete");

        }
      });

      undo.setPreferredSize(buttonSize);
      undo.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
//SS2008-04-01^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
          boolean undone = scheduledBkup.undoDelete();
          if (undone){
//SSend^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
//SS2008-03-31^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
            //location4.removeAll();
            scheduledBkup.addFile(location4);
            if (location4.getItemCount() == 0) {
              location4.setVisible(false);
            }
            else {
              location4.setVisible(true);
            }
           //bkupMain.dispose();
//SSend^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
           }
        }
      });

      close.setPreferredSize(buttonSize);
      close.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          bkupMain.dispose();
        }
      });

      delete.setText(u.gettext("delete", "label"));
      delete.setPreferredSize(buttonSize);
      delete.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
           scheduledBkup.deleteBackup();
//SS2008-03-31^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
           scheduledBkup.addFile(location4);
           if (location4.getItemCount() == 0){
             location4.setVisible(false);
           }
           else{
             location4.setVisible(true);
           }
//SSend^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
        }
      });

      GridBagConstraints constraints3 = new GridBagConstraints();
      constraints3.fill = GridBagConstraints.HORIZONTAL;
      constraints3.weightx = 0;
      constraints3.weighty = 0;

      constraints3.gridy = 0;
      constraints3.gridx = -1;

      constraints3.insets = new Insets(0, 5, 0, 5);
      constraints3.anchor = GridBagConstraints.WEST;
      buttonPanel3.add(delete, constraints3);
      constraints3.anchor = GridBagConstraints.CENTER;
      buttonPanel3.add(undo, constraints3);
      constraints3.anchor = GridBagConstraints.EAST;
      buttonPanel3.add(close, constraints3);
      constraints3.anchor = GridBagConstraints.CENTER;
      constraints3.fill = GridBagConstraints.HORIZONTAL;
      constraints3.gridy = -1;
      constraints3.gridx = 0;
      constraints3.insets = new Insets(5, 5, 5, 5);
      browsePanel3.add(label4, constraints3);
      browsePanel3.add(location4, constraints3);
      constraints3.insets = new Insets(5, 5, 10, 5);
      browsePanel3.add(browse3, constraints3);
      constraints3.insets = new Insets(0, 0, 0, 0);
      browsePanel3.setBorder(BorderFactory.createEtchedBorder());
      constraints3.fill = GridBagConstraints.NONE;
      constraints3.weighty = 1;
      constraints3.weightx = 1;
      deletePanel.add(browsePanel3, constraints3);
      deletePanel.add(buttonPanel3, constraints3);        
      
    }
//SSend^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS

    /**
     * Creates a directory finder providing a GUI and implementation for finding
     *  directories only
     * @param caller shows which panel called the method eg backup, schedule or restore
     */
    private void createDirectoryChooser(final String caller) {
//startPR2007-04-23^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2010-03-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      File  defaultDir = new File(sharkStartFrame.sharedPath + File.separator +
//                                  WS+"backups");
      File  defaultDir = new File(defaultBU+File.separator+WS+"backups");
//endPRtoupdate^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      String ddpath = null;
      defaultDir.mkdir();
      if(defaultDir.exists())
        ddpath = defaultDir.getAbsolutePath();
      JFileChooser fc = new JFileChooser(ddpath);
      fc.setAcceptAllFileFilterUsed(false);
      fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
      fc.setFileFilter(new javax.swing.filechooser.FileFilter() {
        public boolean accept(File f) {
          if (!(f.isDirectory()) || f.getName().equals("Resources")) {
            return false;
          }
          return true;
        }
        public String getDescription() {
           return u.gettext("alldirectories", "label");
        }
      });
      int returnVal = fc.showOpenDialog(bkupMain);
      if (returnVal == fc.APPROVE_OPTION) {
        String path = fc.getSelectedFile().getAbsolutePath();
          if (caller.equals("backup")) {
            location1.addItem(path);
            location1.setSelectedItem(path);
          }
          else if (caller.equals("schedule")) {
            location2.addItem(path);
            location2.setSelectedItem(path);
          }
          else if (caller.equals("restore")) {
            location3.addItem(path);
            location3.setSelectedItem(path);
          }
          else if (caller.equals("delete")) {
            location4.addItem(path);
            location4.setSelectedItem(path);
//SS2008-04-01^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
            location4.setVisible(true);
//SSend^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
          }
//startPR2010-03-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          else if (caller.equals("stick")) {
            location5.addItem(path);
            location5.setSelectedItem(path);
          }
//endPRtoupdate^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      }
    }

    private void createBackupDialog1(){
      GUI.bkupMain.setVisible(false);
      backup1 = GUI.addDialog(true);
//startPR2005-12-31^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      backup1.addWindowListener(new java.awt.event.WindowAdapter() {
        public void windowClosing(WindowEvent e) {
          backup1.dispose();
          if (help != null) {
            help.dispose();
          }
          bkupMain.setVisible(true);
        }
      });
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      GridBagLayout gridBagLayout = new GridBagLayout();
//startPR2008-04-08^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      JLabel label5 = new JLabel(u.gettext("backup_bkupselection", "label"));
      mlabel_base label5 = u.mlabel(shark.macOS?"backup_bkupselection_mac":"backup_bkupselection");
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      JButton selectAll = u.Button("backup_selectall");
      JButton selectNone = u.Button("backup_selectnone");
      final JButton bkupStudent = u.mbutton("backup_bkupstudent");
      final JButton help2 = new JButton(u.gettext("backup_help", "label"));
      JButton exit = new JButton(u.gettext("backup_exit", "label"));
      JPanel buttonPanel = new JPanel();
      buttonPanel.setLayout(gridBagLayout);
      bkupStudent.setEnabled(false);
      backup1.setTitle(u.gettext("backup_bkupstu", "label"));
 //     backup1.setBounds(w / 16, h / 16, w * 1 / 2, h * 5 / 8);
      backup1.setBounds(u2_base.adjustBounds(new Rectangle(w / 16, h / 16, w * 1 / 2, h * 5 / 8)));
      backup1.getContentPane().setLayout(gridBagLayout);

  //    FileFilter filter = new FileFilter() {
 //       public boolean accept(File file) {
//SS2008-04-03^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
 //         String[] names = db.dblistnames(sharkStartFrame.sharedPath);
 //         boolean accept = false;
 //         for (int i = 0; i < names.length;i++){
  //          if(file.getName().endsWith(names[i] + ".sha" )) {
  //            accept = true;
  //            if(file.getName().endsWith(currStudent + ".sha")||
  //               file.getName().endsWith("admin.sha")||
  //               file.getName().endsWith(sharkStartFrame.extracourses + ".sha")||
  //               file.getName().regionMatches(true,file.getName().length()-17,sharkStartFrame.extracourses,0,12)||
  //               file.getName().endsWith("options.sha")||
  //               file.getName().endsWith("keypad.sha")||
  //               file.getName().endsWith("plistrec.sha")){
  //              accept = false;
  //            }
  //          }
  //        }
//SSend^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
//          return accept;
//        }
 //     };
      String[] studentFiles = db.dblistnames(sharkStartFrame.sharedPath);
      File[] children = new File[studentFiles.length];
      for (int i = 0; i < studentFiles.length; i++){
        children[i] = new File(studentFiles[i]);
      }


      if (children == null || children.length == 0) {
        String[] message = {
            u.gettext("backup_noBackup", "label")};
        studentList = new JList(message);
        studentList.setEnabled(false);
//        OK.setEnabled(false);
      }
      else {
        String[] childNames = new String[children.length];
        String[]  backupDir = new String[children.length];
        for (int i = 0; i < children.length; i++) {
          String name = children[i].getName();
          backupDir[i] = name.substring(0,name.length());
        }
        studentList = new JList(backupDir);
      }
      studentList.setSelectionMode(ListSelectionModel.
                                   MULTIPLE_INTERVAL_SELECTION);
      JScrollPane sp = new JScrollPane(studentList);
      sp.setBorder(BorderFactory.createEtchedBorder());
      sp.setPreferredSize(new Dimension(w * 5 / 16, h * 7 / 16));

      studentList.addListSelectionListener(new javax.swing.event.
                                           ListSelectionListener() {
        public void valueChanged(ListSelectionEvent evt) {
          if (studentList.isSelectionEmpty()) {
            bkupStudent.setEnabled(false); //can only restore students if one is chosen
          }
          else {
            bkupStudent.setEnabled(true);
          }
        }
      });

      JButton[] buttons = {
          selectAll, selectNone, bkupStudent, exit, help2};
      Dimension buttonSize = setCommonSize(buttons);

      selectAll.setPreferredSize(buttonSize);
      selectAll.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          if (studentList.isEnabled()) {
            int start = 0;
            int end = studentList.getModel().getSize() - 1;
            if (end >= 0) {
              studentList.setSelectionInterval(start, end);
            }
          }
        }
      });
      selectNone.setPreferredSize(buttonSize);
      selectNone.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          studentList.clearSelection();
        }
      });
      bkupStudent.setPreferredSize(buttonSize);
      bkupStudent.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          if (help != null) {
            help.dispose();
          }
          backup1.dispose();
          String location = (String) location1.getSelectedItem();
          Bckup.chosenFile = new File(location);
          Object[] Names = studentList.getSelectedValues();
          selectedNames = new String[Names.length];
          for(int i = 0; i <Names.length; i++){
            selectedNames[i] =  (String)Names[i] + ".sha";
          }
          oneOffBkup.BackupFiles("oneOffSelected");
        }
      });
      exit.setPreferredSize(buttonSize);
      exit.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          backup1.dispose();
          if (help != null) {
            help.dispose();
          }
          bkupMain.setVisible(true);
        }
      });
      help2.setPreferredSize(buttonSize);
      help2.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
//startPR2005-12-31^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          if(shark.macOS){
//            createHelpDialog("backupHelp_backup2_mac");
            createHelpDialog(backup1, "backupHelp_backup2_mac");
          }
          else{
//            createHelpDialog("backupHelp_backup2");
            createHelpDialog(backup1, "backupHelp_backup2");
          }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        }
      });

      GridBagConstraints constraints = new GridBagConstraints();
      constraints.fill = constraints.HORIZONTAL;
      constraints.insets = new Insets(10, 15, 10, 15);

      constraints.gridy = 0;
      buttonPanel.add(selectAll, constraints);
      constraints.gridy = 1;
      buttonPanel.add(selectNone, constraints);
      constraints.gridy = 2;
      buttonPanel.add(bkupStudent, constraints);
      constraints.gridy = 3;
      buttonPanel.add(exit, constraints);
      constraints.gridy = 4;
      buttonPanel.add(help2, constraints);

      constraints.gridy = 0;
      constraints.gridx = 0;
      constraints.ipadx = constraints.ipady = 0;
      constraints.anchor = constraints.NORTHWEST;
      constraints.insets = new Insets(0, 0, 10, 0);
      backup1.getContentPane().add(label5, constraints);

      constraints.anchor = constraints.SOUTHWEST;
      constraints.gridy = 1;
      constraints.fill = constraints.BOTH;
      constraints.insets = new Insets(0, 0, 0, 0);
      backup1.getContentPane().add(sp, constraints);

      constraints.gridx = 1;
      constraints.gridy = 1;
      constraints.gridheight = 2;
      backup1.getContentPane().add(buttonPanel, constraints);

      backup1.setVisible(true);
    }

/**
     * creates a dialog that offers options available when restoring i.e. which files
     * to restore.
     */
    private void createRestoreDialog1() {
      GUI.bkupMain.setVisible(false);
      restoration1 = GUI.addDialog(true);
//startPR2005-12-31^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      restoration1.addWindowListener(new java.awt.event.WindowAdapter() {
        public void windowClosing(WindowEvent e) {
          GUI.bkupMain.setVisible(true);
          restoration1.dispose();
          if( help != null){
            help.dispose();
          }
        }
      });
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      GridBagLayout gridBagLayout4 = new GridBagLayout();
      JLabel label4 = new JLabel(u.gettext("backup_selectbackup", "label"));
      final JButton OK = new JButton(u.gettext("backup_OK","label"));
      JButton help4 = new JButton(u.gettext("backup_help", "label"));
      JButton exit = new JButton(u.gettext("backup_exit", "label"));
      JPanel buttonPanel4 = new JPanel();
      buttonPanel4.setLayout(gridBagLayout4);

      restoration1.setTitle(u.gettext("backup_restore", "label"));
//      restoration1.setBounds(w / 16, h / 16, w * 1 / 2, h * 5 / 8);
      restoration1.setBounds(u2_base.adjustBounds(new Rectangle(w / 16, h / 16, w * 1 / 2, h * 5 / 8)));
      restoration1.getContentPane().setLayout(gridBagLayout4);

      File dir = new File( (String) location3.getSelectedItem());
      File[] children;
      FilenameFilter filter = new FilenameFilter() {
        public boolean accept(File dir, String name) {
          boolean accept = false;
          String [] children = dir.list();
          for(int i = 0; i < children.length; i++){
            //Test to see if "WS" is followed by a date(this is a backup file)
            int index = name.indexOf(WS);
            if(index != -1){
//startSS2008-04-21^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
//ensure that the name is sufficiently long to be a date
              if (name.length() >= index + 19){
//endSS^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
                String date4 = name.substring(index + 2, index + 19);
                if (isDate(date4)) {
                  accept = true;
                }
              }
            }
          }
          return accept;
        }
      };
      
      boolean isFolderValidBackup = true;
      
      if (dir.isDirectory()) {

        String[] children2 = dir.list(filter);
        if(children2!=null){
            Backup_base.scheduledBkup.backupDirs = new SingleBackup[children2.length];
            dateMilliSecs = new long[children2.length];// array of dates in millisecs for sorting
            Date date = new Date();
            Format formatter = new SimpleDateFormat("dd-MM-yy HH-mm-ss");
            Calendar calendar = new GregorianCalendar();
            for (int i = 0; i < children2.length; i++) {
              String type;
              if (children2[i].startsWith("oneOff")){
                type = "oneOff";
              }
              else{
                type = "scheduled";
              }
              String path = dir.getAbsolutePath() + File.separator + children2[i];
              Backup_base.scheduledBkup.backupDirs[i] = new SingleBackup(type, path);
              boolean isDate = isDate(Backup_base.scheduledBkup.backupDirs[i].getDate());//Cannot parse a file that is'nt a date
                if (isDate){
                  try {
                    date = (Date) formatter.parseObject(Backup_base.scheduledBkup.backupDirs[i].getDate());
                  } catch (ParseException ex) {
                 }
                }
              calendar.setTime(date);
              dateMilliSecs[i] = calendar.getTimeInMillis();
            }
            Arrays.sort(dateMilliSecs); //sort into date order using milliseconds
        }
        else isFolderValidBackup = false;
      }
      else{
       Backup_base.scheduledBkup.backupDirs = new SingleBackup[0];
      }
      
      if (isFolderValidBackup&&(Backup_base.scheduledBkup.backupDirs == null ||Backup_base.scheduledBkup.backupDirs.length == 0)) {
          Backup_base.scheduledBkup.backupDirs = new SingleBackup[]{new SingleBackup(dir.getName().toLowerCase().startsWith("oneOff".toLowerCase())?"oneOff":"scheduled", dir.getAbsolutePath())};
          restoration1.dispose();
          createRestoreDialog2();
          return;
      }
      
      
      

      if (Backup_base.scheduledBkup.backupDirs == null ||Backup_base.scheduledBkup.backupDirs.length == 0) {
        String[] message = {u.gettext("backup_noBackup", "label")};
        dirList = new JList(message);
        dirList.setEnabled(false);
        OK.setEnabled(false);
      }
      else {

        String[] backupDir = new String[Backup_base.scheduledBkup.backupDirs.length];
//SS2008-04-21^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
        for (int i = 0; i < dateMilliSecs.length; i++) {
          Date d = new Date(dateMilliSecs[i]);
          Format formatter = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
          String date = formatter.format(d);
          backupDir[i] = u.gettext("backup_copy", "label") + "  " + date ; //"Copy taken on: -"
//SSend
//SSend^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
        }
        dirList = new JList(backupDir);
      }
      dirList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      JScrollPane sp = new JScrollPane(dirList);
      sp.setBorder(BorderFactory.createEtchedBorder());
      sp.setPreferredSize(new Dimension(w * 7/16, h * 3/8));

      dirList.addListSelectionListener(new javax.swing.event.
                                           ListSelectionListener() {
        public void valueChanged(ListSelectionEvent evt) {
          if (dirList.isSelectionEmpty()) {
            OK.setEnabled(false); //can only restore students if one is chosen
          }
          else {
            OK.setEnabled(true);
          }
        }
      });


      JButton[] buttons = {OK,exit,help4};
      Dimension buttonSize = setCommonSize(buttons);
      OK.setPreferredSize(buttonSize);
      OK.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          if (help != null) {
            help.dispose();
          }
          createRestoreDialog2();
        }
      });
      OK.setEnabled(false);

      exit.setPreferredSize(buttonSize);
      exit.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          GUI.bkupMain.setVisible(true);
          restoration1.dispose();
          if( help != null){
            help.dispose();
          }
        }
      });
      help4.setPreferredSize(buttonSize);
      help4.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
//startPR2005-12-31^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//          createHelpDialog("backupHelp_restore2");
          createHelpDialog(restoration1, "backupHelp_restore2");
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        }
      });

      GridBagConstraints constraints4 = new GridBagConstraints();

      constraints4.gridy = 0;
      constraints4.gridx = 0;
      constraints4.anchor = constraints4.NORTHWEST;
      constraints4.insets = new Insets(0, 0, 10, 0);
      restoration1.getContentPane().add(label4, constraints4);

      constraints4.anchor = constraints4.SOUTHWEST;
      constraints4.gridy = 1;
      constraints4.fill = constraints4.BOTH;
      constraints4.insets = new Insets(0, 0, 0, 0);
      restoration1.getContentPane().add(sp, constraints4);

      constraints4.insets = new Insets(0,10,0,10);
      constraints4.gridx = constraints4.gridy = 0;
      constraints4.fill = GridBagConstraints.HORIZONTAL;
      constraints4.anchor = GridBagConstraints.WEST;
      buttonPanel4.add(OK, constraints4);

      constraints4.gridx = 1;
      constraints4.anchor = GridBagConstraints.CENTER;
      buttonPanel4.add(help4, constraints4);

      constraints4.gridx = 2;
      constraints4.anchor = GridBagConstraints.EAST;
      buttonPanel4.add(exit, constraints4);

      constraints4.gridy = 3;
      constraints4.gridx = 0;
      constraints4.gridwidth = 3;
      constraints4.insets = new Insets(10, 0, 0, 0);
      restoration1.getContentPane().add(buttonPanel4, constraints4);

      restoration1.setVisible(true);
    }

    /**
     * Used to ensure that string passed can be formatted as a date
     * @param strDate
     * @return
     */
    private boolean isDate(String strDate){
      boolean isDate = true;
      Format formatter = new SimpleDateFormat("dd-MM-yy HH-mm-ss");
      try {
        Date date = (Date) formatter.parseObject(strDate);
      } catch (ParseException ex) {
        isDate = false;
      }
      return isDate;
    }

    private void createRestoreDialog2() {
      restoration1.setVisible(false);
      restoration2 = GUI.addDialog(true);
//startPR2005-12-31^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      restoration2.addWindowListener(new java.awt.event.WindowAdapter() {
        public void windowClosing(WindowEvent e) {
          restoration2.dispose();
          if (help != null) {
            help.dispose();
          }
          if(!directRestoreDir)
            restoration1.setVisible(true);
          else GUI.bkupMain.setVisible(true);
        }
      });
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      GridBagLayout gridBagLayout5 = new GridBagLayout();
//startPR2008-04-21^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      JLabel label5 = new JLabel(u.gettext("backup_selectstudent", "label"));
      mlabel_base label5 = u.mlabel(shark.macOS?"backup_selectstudent_mac":"backup_selectstudent");
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      JButton selectAll = u.Button("backup_selectall");
      JButton selectNone = u.Button("backup_selectnone");
      final JButton restoreStudent = u.mbutton("backup_restorestudent");
      final JButton restoreCourses = u.mbutton("backup_restorecourses");
      final JButton restoreKeypad = u.mbutton("backup_restorekeypad");
      restoreKeypad.setEnabled(false);
      restoreCourses.setEnabled(false);
      JButton help5 = new JButton(u.gettext("backup_help", "label"));
      JButton exit = new JButton(u.gettext("backup_exit", "label"));
      JPanel buttonPanel5 = new JPanel();
      buttonPanel5.setLayout(gridBagLayout5);
      restoreStudent.setEnabled(false);
      restoration2.setTitle(u.gettext("backup_restore", "label"));
//      restoration2.setBounds(w / 16, h / 16, w * 1 / 2, h * 5 / 8);
      restoration2.setBounds(u2_base.adjustBounds(new Rectangle(w / 16, h / 16, w * 1 / 2, h * 5 / 8)));
      restoration2.getContentPane().setLayout(gridBagLayout5);
//startSS2008-04-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
      directRestoreDir = dirList==null;
      String sel = !directRestoreDir?(String) dirList.getSelectedValue():Backup_base.scheduledBkup.backupDirs[0].file.getName();
      String path = "";
      String type = "";
      String format = "dd-MM-yy HH-mm-ss";
      
//      String directory = sel.substring(18);
      String directory = sel;
//startSS2008-04-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
//ensure the directory will have right placemarkers and so will match an existing file
      directory = directory.replace('/','-');
      directory = directory.replace(':','-');
//endSS^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
      Format formatter = new SimpleDateFormat(format);
      try{
          if(directory.length()>format.length()){
              String s = directory.substring(directory.length()-format.length());
              Date d = (Date)formatter.parseObject(s);
              directory = s;
          }
      }catch(ParseException e){}

//      String directory = sel.substring(sel.length()-format.length());

      for (int i = 0; i < Backup_base.scheduledBkup.backupDirs.length; i++){
        String name = Backup_base.scheduledBkup.backupDirs[i].getName();
        int index = name.indexOf(WS)+2;
        name = name.substring(index);
        if(name.equals(directory)){
          path =  Backup_base.scheduledBkup.backupDirs[i].getFile().getAbsolutePath();
          type =  Backup_base.scheduledBkup.backupDirs[i].getType();
        }
      }

      Backup_base.scheduledBkup.restoreFrom = new SingleBackup(type, path);
      String dirName = location3.getSelectedItem() + File.separator + Backup_base.scheduledBkup.restoreFrom.getName();
//endSS^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
      File dir = !directRestoreDir?new File(dirName):Backup_base.scheduledBkup.backupDirs[0].file;
//SS2008-04-08^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
      FileFilter filter = new FileFilter() {
      public boolean accept(File file) {
      boolean accept = false;
        int index = file.getName().indexOf(sharkStartFrame.extracourses);
        if (index != -1) {
          accept = true;
        }
          return accept;
        }
      };
      File[] extraCourses = dir.listFiles(filter);
      for (int i = 0; i < extraCourses.length; i++){
        if (extraCourses[i].exists()){
          restoreCourses.setEnabled(true);
        }
      }
      String[] studentFiles = db.dblistnames(dir);
//startPR2008-04-20^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR

      // this was giving me an ArrayIndexOutOfBoundsException. I think it always
      // would unless the current user happened to be last in the list.

//      File[] children = new File[studentFiles.length - 1];
//      for (int i = 0; i < studentFiles.length; i++){
//        if (!(studentFiles[i].equals(currStudent))){
//          path = dir.getAbsolutePath() + File.separator + studentFiles[i];
//          children[i] = new File(path);
//        }
//      }
      File[] children = new File[]{};
      for (int i = 0; i < studentFiles.length; i++){
        if (!(studentFiles[i].equals(currStudent))){
          children = u.addFile(children, new File(dir.getAbsolutePath() + File.separator + studentFiles[i]));
        }
      }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//SSend^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
      if (children == null || children.length == 0) {
        String warning =  u.gettext("backup_noRestore1", "label") +
          currStudent + u.gettext("backup_noRestore2", "label");
        String[] message = {warning};
        studentList = new JList(message);
        studentList.setEnabled(false);
        restoreStudent.setEnabled(false);
      }
      else {
        String[] childNames = new String[children.length];
        String[] backupDir = new String[children.length];
        for (int i = 0; i < children.length; i++) {
          childNames[i] = children[i].getName();
          String studentName = childNames[i].substring(0,
//SS2008-04-03^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
              childNames[i].length());
//SSend^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
          backupDir[i] = studentName;
        }
        boolean empty = true;
        for(int i = 0; i < backupDir.length; i++){
           if(backupDir[i] != null){
             empty = false;
           }
        }
        if (empty == true) {
          String warning =  u.gettext("backup_noRestore1", "label") +
            currStudent + u.gettext("backup_noRestore2", "label");
          String[] message = {warning};
          studentList = new JList(message);
          studentList.setEnabled(false);
          restoreStudent.setEnabled(false);
        }
        else{
          studentList = new JList(backupDir);
        }
      }
      studentList.setSelectionMode(ListSelectionModel.
                                   MULTIPLE_INTERVAL_SELECTION);
      JScrollPane sp2 = new JScrollPane(studentList);
      sp2.setBorder(BorderFactory.createEtchedBorder());
 //     sp2.setPreferredSize(new Dimension(w * 5 / 16, h * 7 / 16));
//startPR2005-12-31^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
 //     sp2.setMinimumSize(new Dimension(w * 5 / 16, h * 7 / 16));
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      studentList.addListSelectionListener(new javax.swing.event.
                                           ListSelectionListener() {
        public void valueChanged(ListSelectionEvent evt) {
          if (studentList.isSelectionEmpty()) {
            restoreStudent.setEnabled(false); //can only restore students if one is chosen
          }
          else {
            restoreStudent.setEnabled(true);
          }
        }
      });

      JButton[] buttons = {
          selectAll, selectNone, restoreStudent, restoreCourses,restoreKeypad, exit, help5};
      Dimension buttonSize = setCommonSize(buttons);

//      selectAll.setPreferredSize(buttonSize);
      selectAll.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          if (studentList.isEnabled()) {
            int start = 0;
            int end = studentList.getModel().getSize() - 1;
            if (end >= 0) {
              studentList.setSelectionInterval(start, end);
            }
          }
        }
      });
//      selectNone.setPreferredSize(buttonSize);
      selectNone.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          studentList.clearSelection();
        }
      });
//      restoreCourses.setPreferredSize(buttonSize);
      restoreCourses.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          if (help != null) {
            help.dispose();
          }
          restoration2.dispose();
          restore("courses");
        }
      });
//      restoreKeypad.setPreferredSize(buttonSize);
      restoreKeypad.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          if (help != null) {
            help.dispose();
          }
          restoration2.dispose();
          restore("keypad");
        }
      });
//      restoreStudent.setPreferredSize(buttonSize);
      restoreStudent.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          if (help != null) {
            help.dispose();
          }
          restoration2.dispose();
          restore("students");
        }
      });
//      exit.setPreferredSize(buttonSize);
      exit.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          restoration2.dispose();
          if (help != null) {
            help.dispose();
          }
//          restoration1.setVisible(true);
          if(!directRestoreDir)
            restoration1.setVisible(true);
          else GUI.bkupMain.setVisible(true);          
          
        }
      });
//      help5.setPreferredSize(buttonSize);
      help5.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
//startPR2006-04-30^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//          createHelpDialog("backupHelp_restore3");
          if(shark.macOS)
            createHelpDialog(restoration2, "backupHelp_restore3_mac");
          else
            createHelpDialog(restoration2, "backupHelp_restore3");
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        }
      });

      GridBagConstraints constraints5 = new GridBagConstraints();
      constraints5.fill = constraints5.HORIZONTAL;
      constraints5.gridy = -1;
      constraints5.gridx = 0;
      constraints5.weighty = 0;
      constraints5.insets = new Insets(0, 0, 10, 0);
      buttonPanel5.add(selectAll, constraints5);
      buttonPanel5.add(selectNone, constraints5);
      buttonPanel5.add(restoreStudent, constraints5);
      buttonPanel5.add(restoreCourses, constraints5);
      dirName = location3.getSelectedItem() + File.separator + WS + directory;
      if (db.exists(dirName + File.separator + "keypad")) {
        String kp[] = db.list(dirName + File.separator + "keypad", db.SAVEKEYPAD);
        if (kp.length > 0) {
          restoreKeypad.setEnabled(true);
          buttonPanel5.add(restoreKeypad, constraints5);
        }
      }
      buttonPanel5.add(exit, constraints5);
      constraints5.insets = new Insets(0, 0, 0, 0);
      buttonPanel5.add(help5, constraints5);

      constraints5.fill = GridBagConstraints.BOTH;
      constraints5.gridy = 0;
      constraints5.gridx = -1;
      constraints5.weightx = 1;
      constraints5.weighty = 1;
      JPanel pnLower = new JPanel(new GridBagLayout());
      constraints5.insets = new Insets(0, 0, 0, 10);
      pnLower.add(sp2, constraints5);
      constraints5.insets = new Insets(0, 0, 0, 0);
      constraints5.weightx = 0;
      constraints5.weighty = 0;
      constraints5.fill = GridBagConstraints.HORIZONTAL;
      constraints5.anchor = GridBagConstraints.NORTH;
      pnLower.add(buttonPanel5, constraints5);
      constraints5.anchor = GridBagConstraints.CENTER;
      constraints5.fill = GridBagConstraints.BOTH;
      constraints5.weighty = 1;
      
      constraints5.weightx = 1;

      constraints5.gridy = -1;
      constraints5.gridx = 0;
      constraints5.weighty = 0;
      constraints5.insets = new Insets(20, 20, 10, 20);
      restoration2.getContentPane().add(label5, constraints5);
      constraints5.weighty = 1;
      constraints5.insets = new Insets(0, 20, 20, 20);
      restoration2.getContentPane().add(pnLower, constraints5);
      restoration2.setVisible(true);

    }

//startPR2005-12-31^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    public void createHelpDialog(String text){
    public void createHelpDialog(JDialog owner, String text){
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      if(help == null || !help.isShowing()){
        GridBagLayout gridBagLayout = new GridBagLayout();
//startPR2005-12-31^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//        help = GUI.addDialog(false);
        help = GUI.addDialog(owner, false);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        help.setDefaultCloseOperation(help.DISPOSE_ON_CLOSE);
        help.setTitle(u.gettext(text, "heading"));
//startPR2006-04-30^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//        mlabel message = u.mlabel(text);
        JTextArea message = new JTextArea(u.combineString(u.splitString(u.gettext(text, "label")),"\n"));
        message.setLineWrap(true);
        message.setWrapStyleWord(true);
        message.setEditable(false);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//        help.setBounds(w / 16, h / 16, w * 1 / 2, h * 1 / 2);
        help.setBounds(u2_base.adjustBounds(new Rectangle(w / 16, h / 16, w * 1 / 2, h * 1 / 2)));
        help.getContentPane().setLayout(gridBagLayout);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
//startPR2006-04-30^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.fill = constraints.BOTH;
//        help.getContentPane().add(message, constraints);
        help.getContentPane().add(new JScrollPane(message), constraints);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        help.setVisible(true);
      }
    }

    private Dimension setCommonSize(JButton[] buttons){
      Dimension size = new Dimension();
      for(int i = 0; i<buttons.length; i++){
        if(buttons[i].getPreferredSize().getWidth() > size.getWidth()){
          size.width = (int)buttons[i].getPreferredSize().getWidth();
        }
        if (buttons[i].getPreferredSize().getHeight() > size.getHeight()) {
          size.height = (int)buttons[i].getPreferredSize().getHeight() + h/100;
        }
      }
      return size;
    }

    public void dispose() {
      bkupMain.dispose();
    }

    public JDialog addDialog(boolean modal) {
      JDialog d = new JDialog(bkupMain, modal);
      return d;
    }
//startPR2005-12-31^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    public JDialog addDialog(JDialog dia, boolean modal) {
      JDialog d = new JDialog(dia, modal);
      return d;
    }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    //Used so that additional functionality is added when the main JDialog closes
    private class BkupDialogue extends JDialog{
      BkupDialogue(JFrame frame,String title){
        super(frame,title);
      }

      public void dispose(){//This is called when the backup facility is closed
        //Get rid of unwanted "~WSDelTemp"
//startPR2008-04-08^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//        File deleteDir = new File(sharkStartFrame.sharedPath + File.separator + "~WSDelTemp");
        File deleteDir = new File(sharkStartFrame.sharedPathplus + tempDeleteFileName
                                  + sharkStartFrame.studentList[sharkStartFrame.currStudent].name);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        boolean deleted = Bckup.deleteDir(deleteDir);
        Backup_base.scheduledBkup.endBackup();//tidy up and save necessary details
        super.dispose();
      }
    }
  }

  /**
   * Parent class for the two backup classes scheduledBackup and OneoffBackup. This
   * class is responsible for any functions that are common to both subclasses
   */
  private static class Bckup extends JDialog {
    /**
     * Holds the names of backups currently stored. These back ups are
     * part of a schedule.
     */
    private String scheduledBackups[] = new String[0];
    /**
     * The first string is the location for scheduled backups to be done in
     * The second string is the date the last scheduled backup was done on
     * The third string is the number of days between backups
     */
    protected static String schedule[] = new String[5];
//startSS2008-03-21^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
    /**
     * Holds a temporary record of the number of strings to be backed up
     */
    private String noBackups[] = new String[0];
//endSS^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
//startSS2008-04-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
    /**
     * Used to hold current backups when a restoration is done.
     */
    SingleBackup[] backupDirs;
    /**
     * The file that is going to be restored and put back into use
     */
    SingleBackup restoreFrom;
//endSS^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS

    /**
     * Holds the names of backups currently stored. These back ups have not been
     * part of a schedule.
     */

    //private String oneOffBackups[] = new String[0];
    /**
     * The total number of scheduled backups that are present
     */
    private int numberscheduledBkups;
    /**
     * The total number of one off backups that are present.
     */
    //private int numberOneOffBkups;
    /**
     * File specifying the users choice of directory where a back up should be done
     * (Set in the directory chooser)
     */
    static File chosenFile;
    /**
     * Locks a file so that only one backup can be updating options database at a time
     */
    private FileLock lock;
    /**
     * The chosen directory where backups are to be done
     */
    private File backupFile;
//startPR2008-04-10^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    String newversiontext = u.gettext("backup_", "newversion");
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    String username;

    public Bckup() {
      //SS2008-03-21^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
      //Find number of backups that the user has set to be kept
      try{
        noBackups = (String[]) db.find(sharkStartFrame.optionsdb, "backup_numbackups",
                                               db.TEXT);
      }
      catch(ClassCastException e){
//        System.out.println(e.getMessage());
      }
      if (noBackups == null){
        currNoOneOffBkps = 5;
        currNoSchBkps = 5;
      }
      else{
        if (noBackups[0] == null) {
          currNoOneOffBkps = 5;
        }
        else {
          currNoOneOffBkps = Integer.parseInt(noBackups[0]);
        }
        if (noBackups[1] == null) {
          currNoSchBkps = 5;
        }
        else {
          currNoSchBkps = Integer.parseInt(noBackups[1]);
        }
      }
//SSend^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
      //If there are no scheduled backups stored then this a new installation - set the default
      //backup options of 7 day gap.
       scheduledBackups = (String[]) db.find(sharkStartFrame.optionsdb,
                                              "backup_scheduledbkups",
                                              db.TEXT);
//SS2008-04-02^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
        if (scheduledBackups != null){
          scheduledBackups = filesExist(scheduledBackups); //Check files haven't been deleted by a user
        }
//SSend^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
      schedule = (String[]) db.find(sharkStartFrame.optionsdb,
                                    "backup_schedule",
                                    db.TEXT);
      //oneOffBackups = (String[]) db.find(sharkStartFrame.optionsdb, "backups",
     //                                    db.TEXT);
     // if (oneOffBackups != null) {
//SS2008-03-25^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
     //   for (int i = 0; i < oneOffBackups.length; i++){//Get rid of any null values
     //       if (oneOffBackups[i] == null){
      //         oneOffBackups = u.removeString(oneOffBackups,i);
     //          i--;
     //       }
    //    }
//SSend^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
    //    for (int i = 0; i < oneOffBackups.length; i++) {
   //       if (oneOffBackups[i] != null)
    //        numberOneOffBkups++; //find how many file names are in the array
   //     }
   //   }
      if (scheduledBackups != null) {
        for (int i = 0; i < scheduledBackups.length; i++) {
          if (scheduledBackups[i] != null)
            numberscheduledBkups++; //find how many file names are in the array
        }
      }
    }

    /**
     * Indicates whether a back up can be done at present,If one can opens a file that acts as a flag
     * indicating to other users that they cannot use the database at present. ALWAYS USE THE CLOSEGUARD
     * METHOD WHEN THE CRITICAL ACCESS HAS FINISHED. This is so that the temporary file created
     * is deleted.
     * @return True if a backup should be done false if not
     */
    boolean openGuard() {
      boolean backup = false;
//startPR2009-08-19^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      File tempFile = new File(sharkStartFrame.sharedPath +
//                                File.separator +
//                                WS+"Temp" + ".lock"); //file acts as flag if present then db in use
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2006-04-30^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2010-03-15^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      if(shark.macOS && shark.network){
      if(MacLock.DOJNI){
//endPRtoupdate^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          if(MacLock.lock(sharkStartFrame.sharedPath+File.separator+WS+"Temp", ".lock", null, false)>=0)
            backup = true;
          else
            backup = false;
        }
        else{
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          try{
            FileOutputStream f = new FileOutputStream(tempFile);
//startPR2009-08-02^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            u.setNewFilePermissions(tempFile);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            lock =  f.getChannel().tryLock();
            backup = true;
          }
          catch(IOException e){
            backup = false;
          }
//startPR2010-03-18^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          catch (OverlappingFileLockException e) {backup = false;}
//endPRtoupdate^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2006-04-30^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        return backup;
      }
    /**
     * Removes filelock so allowing access for another backup to be done
     */
    void closeGuard(){
//startPR2006-04-30^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2010-03-15^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      if(shark.macOS && shark.network){
      if(MacLock.DOJNI){
//endPRtoupdate^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2009-08-19^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//        MacLock.unlock(sharkStartFrame.sharedPath+File.separator+WS+"Temp", ".lock");
        MacLock.delete(sharkStartFrame.sharedPath+File.separator+WS+"Temp", ".lock");
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      }
      else{
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        try {
          if (lock != null) {
            lock.release();
//startPR2009-08-19^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            tempFile.delete();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          }
//startPR2009-08-19^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//        } catch (IOException e) {}
        } catch (Exception e) {}
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2006-04-30^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    }
    /**
     * Sets up the directory in which backups are to be done
     * @param type "oneOff" if a one off backup is being done otherwise "scheduled"
     */
    public void setBkupDir(String type) {
      Date d = new Date(); //todays date - used to name new folder
      Format formatter = new SimpleDateFormat("dd-MM-yy HH-mm-ss");
      String date = formatter.format(d);
      if (type == "scheduled"){
        backupFile = new File(chosenFile.getAbsolutePath() + File.separator /*+ WS +
                      "backups" */+ File.separator + WS + date);
      }
      if (type == "oneOff"){
        backupFile = new File(chosenFile.getAbsolutePath() + File.separator  + "Oneoff" /*+ WS +
                                              "backups" + File.separator*/ + WS + date);
      }

    }

    /**
     * Backs up all .sha files to a folder named with today's date and the current time.
     * If no path has been specified this is done in the sharkshared folder on the
     * C drive otherwise it is done to the specified folder. This folder is recorded
     * in the student's options database.
     * @param type Type of backup e.g. scheduled, oneOff,initial
     */
    void BackupFiles(String type) {
      boolean openGuard = openGuard();
      if (openGuard) { //Access to options database is available
        boolean goAhead = true;
        if (type =="scheduled"){
            goAhead = scheduledBkup.scheduledBackup();
        }
        if(goAhead){
          int oneOffLength;
          int SchLength;
          //if (oneOffBackups !=null){
         //   oneOffLength = oneOffBackups.length;
         // }
        //  else{
        //    oneOffLength = 0;
        //  }
          if (scheduledBackups != null){
            SchLength = scheduledBackups.length;
          }
          else{
            SchLength = 0;
          }
          String bkupFiles[] = new String[ SchLength + 1];
          int numberBkups = 0;
          String backupType = "";
         // if ((type.equalsIgnoreCase("oneOff")  || type.equalsIgnoreCase("oneOffSelected"))) { //get array of back up files in use
          //    numberBkups = numberOneOffBkups;
          //    currNoBkps =  currNoOneOffBkps;
          //    if(oneOffBackups != null){
          //      System.arraycopy(oneOffBackups, 0, bkupFiles, 0,
          //                       Math.min(oneOffBackups.length, bkupFiles.length));
          //    }
         // }

          if ((type.equalsIgnoreCase("scheduled") || type.equalsIgnoreCase("newSchedule" ))){//get array of back up files in use
            numberBkups = numberscheduledBkups;
            backupType = "Scheduled";
            if (scheduledBackups != null){
              System.arraycopy(scheduledBackups, 0, bkupFiles, 0,
                   Math.min(scheduledBackups.length, bkupFiles.length));
            }
          }
          if (chosenFile == null) {
//SS2008-03-31^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
            if (schedule != null && (type == "scheduled" || type == "newSchedule")){
//SSend^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
              if (schedule[0] != null &&
                  (type == "scheduled" || type == "newSchedule")) {
                String location = schedule[0];
                chosenFile = new File(location);
              }
              else {
                chosenFile = new File(sharkStartFrame.sharedPath + File.separator +
                                      WS + "backups");
              }
//SS2008-03-31^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
            }
            else{
              chosenFile = new File(sharkStartFrame.sharedPath +
                      File.separator +
                      WS + "backups");
            }
//SSend^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
          }
          if (type == "newSchedule" ||type == "scheduled"){
            setBkupDir("scheduled");
          }
          else{
            setBkupDir("oneOff");
          }
          boolean ADriveThere = true;
          boolean dirsMade = false;
          if(chosenFile.getPath() != null){
            if (chosenFile.getPath().startsWith("A:\\")) { //if A drive wanted make sure it exists
              File ADrive = new File(chosenFile.getPath() + WS + "backups");
              ADriveThere = ADrive.mkdir(); //Automatically displays option pane if no disk inserted
              if (ADriveThere)
                ADrive.delete();
            }
          }

          if (ADriveThere) { //If A drive was needed and exists make all directories
            dirsMade = backupFile.mkdirs();
          }
          else {
            if (type == "newSchedule"||type == "oneOff") {
              JOptionPane.showMessageDialog(GUI.bkupMain,
                                            u.gettext("backup_noADrive", "label"),
                                            u.gettext("backup_writingError", "label"),
                                            JOptionPane.INFORMATION_MESSAGE);
            }
          }
          if (dirsMade && ADriveThere) { //The directories required have been successfully set up
            //record backup file in optionsdb - if are too many files stored then remove earliest
            //and append current file location.
//SS2008-03-28^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
            if ((numberBkups >= currNoSchBkps) && (type == "scheduled" || type == "newSchedule")) {
//SSend^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
              while (numberBkups >= currNoSchBkps) {
                String path = bkupFiles[0];
                File oldestBackup = new File(path);
                File[] oldFiles = oldestBackup.listFiles();
                if (oldFiles != null) {
                  for (int i = 0; i < oldFiles.length; i++) { //delete files in oldest back up folder
                    oldFiles[i].delete();
                  }
                }
                boolean deleted = oldestBackup.delete();
               // if (deleted) {
                  for (int i = 0; i < numberBkups; i++) {
                    bkupFiles[i] = bkupFiles[i + 1];
                  }

               // }
                numberBkups--;
                bkupFiles[numberBkups] = backupFile.getPath();
              }
            }
//SS2008-03-25^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
            else {
              bkupFiles = u.addString(bkupFiles, backupFile.getPath());
              for (int i = 0; i < bkupFiles.length; i++){//Get rid of any null values
                  if (bkupFiles[i] == null){
                     bkupFiles = u.removeString(bkupFiles,i);
                     i--;
                  }
              }
            }
//SSend^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
            //if (type == "oneOff" || type == "oneOffSelected") {
              //db.update(sharkStartFrame.optionsdb, "backups", bkupFiles,
              //          db.TEXT);
           // }
            if (type == "scheduled" || type == "newSchedule") {
              scheduledBackups = new String[bkupFiles.length];
              System.arraycopy(bkupFiles, 0, scheduledBackups, 0,
                 Math.min(scheduledBackups.length, bkupFiles.length));
              Backup_base.scheduledBkup.SetOptionsScheduledBkups(scheduledBackups);
              //db.update(sharkStartFrame.optionsdb, "backup_scheduledbkups",
              //          bkupFiles, db.TEXT);
            }
            File[] f = new File[sharkStartFrame.sharedPath.listFiles().length];
            if(type == "oneOffSelected"){
              //get all selected files
              String[] names = GUI.selectedNames;
              for(int i = 0; i < names.length; i++){
                f[i] = new File(sharkStartFrame.sharedPath, names[i]);
              }
            }
            else{
              f = sharkStartFrame.sharedPath.listFiles(); //get current .sha files
            }
            
            String resdir = sharkStartFrame.sharedPathplus+sharkStartFrame.resourcesPlus;
//            File resf[] = new File[f.length];
            File resf[] = new File[]{};
            File resc[] = new File[]{};
            File ftemp;
            for (int i = 0; i < f.length; i++) {
              if(f[i].isDirectory())continue;
              String resstr = resdir + convertNameToResources(f[i].getName());
              if((ftemp = new File(resstr)).exists()){
                resf = u.addFile(resf, ftemp);
              }        
              File recf = new File(sharkStartFrame.sharedPathplus+sharkStartFrame.recordsPlus);
              username = getUserNameFromFile(f[i].getName());
              if(username!=null){
                  File list[] = recf.listFiles(new FileFilter() {
                    public boolean accept(File file) {
                      return file.getName().startsWith(username+" - "+shark.USBprefix);
                    }
                  });
                  for(int j = 0; list!=null && j < list.length; j++){
                      resc = u.addFile(resc, list[j]);
                  }
                }
            }
            boolean error = true;
            for (int i = 0; i < f.length; i++) {
              if(f[i] != null){
                error = BkupFile(f[i]);
              }
            }
            for (int i = 0; i < resf.length; i++) {
              if(resf[i] != null){
                error = BkupFile(resf[i], BU_FILETYPE_RESOURCE);
              }
            }         
            for (int i = 0; i < resc.length; i++) {
              if(resc[i] != null){
                error = BkupFile(resc[i], BU_FILETYPE_RECORD);
              }
            }
            if (!error) {
              if(!shark.production){
                bkupDone(type);
              }
            }
          }
        }
      }
      closeGuard();
//SS2008-06-21^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
        //Writes the number of backups that the user wishes to keep to the options
        //database
//        String oldNoBkups[] = new String[2];
//        try{
//          oldNoBkups = (String[]) db.find(sharkStartFrame.optionsdb, "backup_numbackups",
//                                          db.TEXT);
//        }
//        catch(ClassCastException e){
//          System.out.println(e.getMessage());
//       }
 //      if (oldNoBkups != null){
 //        oldNoBkups[0] = Integer.toString(currNoOneOffBkps);
 //        oldNoBkups[1] = Integer.toString(currNoSchBkps);
 //      }
 //      else{
 //        oldNoBkups = new String[2];
 //        oldNoBkups[0] = "5";
 //        oldNoBkups[1] = "5";// this used to be  oldNoBkups[1] = "5";  - could have caused our corruption problems?
 //      }
 //        boolean updated = false;

         //  something odd
//         updated = db.update(sharkStartFrame.optionsdb, "backup_numbackups", oldNoBkups,
//                             db.TEXT);
//         try {
//           String NoBackups[] = new String[2];
//           NoBackups = (String[]) db.find(sharkStartFrame.optionsdb, "backup_numbackups",
//                                          db.TEXT);
//           NoBackups = NoBackups;
//         } catch (ClassCastException e) {
 //          System.out.println(e.getMessage());
 //        }
//SSend^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
    }

    

    
    
    private boolean BkupFile(File f) {
        return BkupFile(f, BU_FILETYPE_NORMAL);
    }
    /**
     * Saves the file passed to it
     * @param f File to be saved
     * @return True if file was saved, false if not.
     */
    private boolean BkupFile(File f, int type) {
      boolean error = false; //indicates that an error has occurred
      if (f.getName().endsWith(".sha") || type==BU_FILETYPE_RECORD) {
        try {
          InputStream is = new FileInputStream(f);

          // Create the byte array to hold the data
          byte[] bytes = new byte[ (int) f.length()];

          // Read in the bytes
          int offset = 0;
          int numRead = 0;
          while (offset < bytes.length
                &&
                 (numRead = is.read(bytes, offset,
                                    bytes.length - offset)) >=
                 0) {
            offset += numRead;
          }

          // Ensure all the bytes have been read in
          if (offset < bytes.length) {
            error = true;
            throw new IOException(u.gettext("backup_noRead", "label") + " " +
                                 f.getName());
          }

          // Close the input stream and return bytes
          is.close();

          //Open and write to output stream
//startPR2009-08-02^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//          FileOutputStream output = new FileOutputStream(backupFile +
//              File.separator + f.getName());
          File f2;
          if(type==BU_FILETYPE_RESOURCE){
              String resfolder = backupFile + File.separator + sharkStartFrame.resourcesPlus;
              File ft;
              if(!((ft=new File(resfolder)).exists()))
                  ft.mkdir();
              f2 = new File(resfolder + f.getName());
          }
          else if(type == BU_FILETYPE_RECORD)
          {
              String recfolder = backupFile + File.separator + sharkStartFrame.recordsPlus;
              File ft;
              if(!((ft=new File(recfolder)).exists()))
                  ft.mkdir();
              f2 = new File(recfolder + f.getName());
          }
          else
              f2 = new File(backupFile + File.separator + f.getName());
          FileOutputStream output = new FileOutputStream(f2.getAbsolutePath());
          u.setNewFilePermissions(f2);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          output.write(bytes);
          output.flush();
          output.close();
        }
        catch (Exception ex) {
          System.err.println(ex);
          if(shark.testing)
            JOptionPane.showMessageDialog(this,
//startPR2008-04-08^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//                                        "Error writing to backup file",
//                                        "File output error",
                                        u.gettext("backup_writeerror", "label"),
                                        u.gettext("backup_writeerror", "heading"),
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                                        JOptionPane.ERROR_MESSAGE);
          error = true;
        }
      }
      return error;
    }

    /**
     * displays a message saying that a back up has been done
     * @param type This dictates how the message is displayed
     */
    public void bkupDone(String type) {
      bkupDone = new JWindow(sharkStartFrame.mainFrame);
      GridBagLayout grid = new GridBagLayout();
      bkupDone.getContentPane().setLayout(grid);
      GridBagConstraints constraints = new GridBagConstraints();
      mlabel_base text;
      int w = sharkStartFrame.mainFrame.getSize().width;
      int h = sharkStartFrame.mainFrame.getSize().height;
      bkupDone.setSize(w / 4, h / 6);
      if(type == "scheduled"){
        text = u.mlabel("backup_complete2");
      }
      else if(type == "newSchedule"){
        bkupDone.setSize(w * 5/8, h / 6);
        text = u.mlabel("backup_complete1");
      }
      else {
        text = u.mlabel("backup_complete3");
      }
      bkupDone.getContentPane().add(text, constraints);
      if (type.equalsIgnoreCase("scheduled") ||
          type.equalsIgnoreCase("newSchedule")) {
        bkupDone.setLocation( (w - w / 60) - bkupDone.getWidth(), h / 12);
        bkupDone.setVisible(true);
          bkupDone.setLocation(w / 50, bkupDone.getY() + h / 17);
          int numberMilliseconds = 3000;
          Date timeToRun = new Date(System.currentTimeMillis() +
                                    numberMilliseconds);

          Timer timer = new Timer();
          timer.schedule(new TimerTask() {
            public void run() {
              bkupDone.dispose();
            }
          }
          , timeToRun);
      }
      else {
        bkupDone.setLocation(w / 50, bkupDone.getY() + h / 9);
        bkupDone.setVisible(true);
        int numberMilliseconds = 4000;
        Date timeToRun = new Date(System.currentTimeMillis() +
                                  numberMilliseconds);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
          public void run() {
            bkupDone.dispose();
          }
        }

        , timeToRun);
      }
    }

    /**
     * Adds dirctory names to the combo box passed. These can be either from previous
     * scheduled backups if that parameter passed is "scheduled, or from unscheduled
     * backups if the parameter passed is "oneOff"
     * @param combo The combo box to have items added to it.
     * @param type The type of file names to be added to the combo box - see above
     * @return The filled combo box - if files names were found to add to it otherwise
     * the combo box in the same state as passed
     */
    public JComboBox addDir(JComboBox combo, String type) {
      String bkupFiles[];
//SS2008-03-28^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
      int oneOffLength = 0;

   //   if (oneOffBackups != null){
    //    oneOffLength = oneOffBackups.length;
    //  }
      int schLength = 0;
      if (scheduledBackups != null){
        schLength = scheduledBackups.length;
      }
      bkupFiles = new String[Math.max(oneOffLength,schLength)];
//SSend^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
   //   if (type.equalsIgnoreCase("oneOff") && oneOffBackups != null) {
   //     System.arraycopy(oneOffBackups, 0, bkupFiles, 0,
   //                      Math.min(oneOffBackups.length, bkupFiles.length));
   //   }
     /* else */if(type.equalsIgnoreCase("scheduled") && scheduledBackups != null) {
        System.arraycopy(scheduledBackups, 0, bkupFiles, 0,
                         Math.min(scheduledBackups.length, bkupFiles.length));
      }
      if (bkupFiles != null){
        for (int i = 0; i < bkupFiles.length; i++) { //populate location1
          if (bkupFiles[i] != null) {
            File currFile = new File(bkupFiles[i]);
            String parent = currFile.getParent();
//startPR2008-01-17^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            if (parent != null)

//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
              combo.addItem(parent);
          }
        }
      }
      int count = 0;
      while (count < combo.getItemCount()) { //Ensure there are no duplicate folders shown in combo box
        for (int i = count + 1; i < combo.getItemCount(); i++) {
          if (combo.getItemAt(count).equals(combo.getItemAt(i))) {
            while (combo.getItemAt(count).equals(combo.getItemAt(i))) {
              combo.removeItemAt(i);
            }
          }
        }
        count++;
      }
      if(type == "scheduled"){
        for (int i = 0; i < combo.getItemCount(); i++) {
          if(schedule != null){//a schedule has been set up
            if (combo.getItemAt(i).equals(schedule[0])) {
              combo.setSelectedIndex(i);
            }
          }
        }
      }
      return combo;
    }

//SS2008-03-28^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
    /**
     * Adds files to the combo box passed
     */
   public JComboBox addFile(JComboBox combo, String type){
     String bkupFiles[];
     boolean filesThere = true;
     if(type.equalsIgnoreCase("scheduled") && (scheduledBackups != null) ){
        bkupFiles = new String[scheduledBackups.length];
     }
   //  else if(type.equalsIgnoreCase("oneOff") && (oneOffBackups != null) ){
   //     bkupFiles = new String[oneOffBackups.length];
   //  }
   else{
     bkupFiles = new String[0];
     filesThere = false;
   }
     if(filesThere){
     //  if (type.equalsIgnoreCase("oneOff") && oneOffBackups != null) {
     //    bkupFiles = new String[oneOffBackups.length];
     //    System.arraycopy(oneOffBackups, 0, bkupFiles, 0,
     //                     Math.min(oneOffBackups.length, bkupFiles.length));
     //  }
    //   else
         if (type.equalsIgnoreCase("scheduled") && scheduledBackups != null) {
           System.arraycopy(scheduledBackups, 0, bkupFiles, 0,
                            Math.min(scheduledBackups.length, bkupFiles.length));
//SS2008-04-02^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
   //        if (bkupFiles.length != 0){
   //           bkupFiles = filesExist(bkupFiles);
   //        }
//SSend^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
         }
         for (int i = 0; i < bkupFiles.length; i++) { //populate location1
           if (bkupFiles[i] != null) {
             File currFile = new File(bkupFiles[i]);
             combo.addItem(currFile.getAbsolutePath());
           }
         }
         int count = 0;
         while (count < combo.getItemCount()) { //Ensure there are no duplicate folders shown in combo box
          for (int i = count + 1; i < combo.getItemCount(); i++) {
           if (combo.getItemAt(count).equals(combo.getItemAt(i))) {
             while (combo.getItemAt(count).equals(combo.getItemAt(i))) {
               combo.removeItemAt(i);
             }
           }
         }
         count++;
       }
     }

     return combo;
   }
//SSend^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
//SS2008-04-02^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
   /**
    * Checks for the existance of backup files to ensure all those passed are present
    * @param filenames String array of backup file paths to see if they are present
    * @return String array without those paths that don't exist.
    */
   public String[] filesExist(String[] filenames){
     String[] filesPresent = new String[filenames.length];
     System.arraycopy(filenames, 0, filesPresent, 0,
                   Math.min(filenames.length, filesPresent.length));
     for (int i = 0; i < filenames.length; i++){
       if (filenames[i] != null){
         File file = new File(filenames[i]);
         if (!file.exists()) {
           filesPresent = u.removeString(filesPresent, i);
          // scheduledBackups = u.removeString(filesPresent, filenames[i]);
         }
       }
     }
     return filesPresent;
   }
//SSend^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
//SS2008-03-26^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
    /**
     * moves a backup directory that is to be deleted to "~WSDelTemp".
     * It deletes any directory currently in "~WSDeletedBackup". This allows an
     * undo to be performed
     */
    public void deleteBackup() {
      String fileName;
      String dirPath = (String)GUI.location4.getSelectedItem();
//startPR2008-04-08^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      String currstu = sharkStartFrame.studentList[sharkStartFrame.currStudent].name;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      if (dirPath != null){
        if (dirPath.length() > 19){
          fileName = dirPath.substring(dirPath.length() - 19);
        }
        else {
          fileName = "badName";
        }
        if (fileName.startsWith(WS)) {
          //Delete the file selected for deletion the time before
//startPR2008-04-08^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//          File deleteDir = new File(sharkStartFrame.sharedPath + File.separator + "~WSDelTemp");
          File deleteDir = new File(sharkStartFrame.sharedPathplus + tempDeleteFileName + currstu);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          boolean deleted = deleteDir(deleteDir);
          //ensure that the list of files persistantly stored is updated
        //  if (oneOffBackups != null) {
        //    String oneOff[] = new String[oneOffBackups.length + 1];
        //    System.arraycopy(oneOffBackups, 0, oneOff, 0,
        //                     Math.min(oneOffBackups.length, oneOff.length));
        //    for (int i = 0; i < oneOff.length; i++) {
        //      if (dirPath.equalsIgnoreCase(oneOff[i])) {
        //        int tempLength = oneOff.length - i;
        //        for (int j = i; j < tempLength - 1; j++) {
        //          oneOff[j] = oneOff[j + 1];
        //        }
        //        db.update(sharkStartFrame.optionsdb, "backups", oneOff,
        //                  db.TEXT);
        //      }
        //    }
        //  }

          if (scheduledBackups != null) {
            String scheduled[] = new String[scheduledBackups.length + 1];
            System.arraycopy(scheduledBackups, 0, scheduled, 0,
                             Math.min(scheduledBackups.length, scheduled.length));
            for (int i = 0; i < scheduled.length; i++) {
              if (dirPath.equalsIgnoreCase(scheduled[i])) {
                int tempLength = scheduled.length - i;
                for (int j = i; j < tempLength - 1; j++) {
                  scheduled[j] = scheduled[j + 1];
                }

                System.arraycopy(scheduled, 0, scheduledBackups, 0,
                                 Math.min(scheduledBackups.length, scheduled.length));
               // db.update(sharkStartFrame.optionsdb, "backup_scheduledbkups",
               //           scheduled, db.TEXT);

              }
            }
          }
          File moveDir = new File(dirPath);
          // File (or directory) with new name
//startPR2008-04-08^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//          String name = sharkStartFrame.sharedPath + File.separator + "~WSDelTemp";
          String name = sharkStartFrame.sharedPathplus + tempDeleteFileName + currstu;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          (new File(name)).mkdir();
          File file2 = new File(name + File.separator + dirPath.substring(dirPath.length() - 19));
          // Rename file (or directory) i.e move it
          boolean success = moveDir.renameTo(file2);
          if (!success) {
            JOptionPane.showMessageDialog(this,
//startPR2008-04-08^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//                                          "Could not delete file",
//                                          "File deletion error",
                                          u.gettext("backup_deleteerror", "label"),
                                          u.gettext("backup_deleteerror", "heading"),
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                                          JOptionPane.ERROR_MESSAGE);

          }
//startPR2008-04-08^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          else{
            GUI.setSizeLabelText();
          }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//SS2008-03-31^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
          //GUI.bkupMain.dispose();
//SSend^^^^^^1^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
        }
        else {
          JOptionPane.showMessageDialog(GUI.bkupMain, u.gettext("backup_noDelete", "label"));
        }
      }
      else{
        JOptionPane.showMessageDialog(GUI.bkupMain, u.gettext("backup_notFile", "label"));
      }
    }

    /**
     * Undoes previous delete by reinstating the file deleted from ~WSDelTemp
     */
    public boolean undoDelete() {
      boolean undone = false;
//startPR2008-04-08^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//          File undoDir = new File(sharkStartFrame.sharedPath + File.separator + "~WSDelTemp");
      String currstu = sharkStartFrame.studentList[sharkStartFrame.currStudent].name;
      File undoDir = new File(sharkStartFrame.sharedPathplus + tempDeleteFileName + currstu);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      if (undoDir.exists()){
        String name[] = undoDir.list();
        int result = 0;
        for (int i = 0; i < name.length; i++) {
          result = JOptionPane.showConfirmDialog(sharkStartFrame.mainFrame,
                                                 u.gettext("backup_undodelete1", "label") + " " + name[i] + "?",
                                                 u.gettext("backup_undodelete2", "label"), JOptionPane.YES_NO_OPTION,
                                                 JOptionPane.QUESTION_MESSAGE);
          if (result == 0) { //yes do undo operation
            File undoneFile = new File(sharkStartFrame.sharedPath + File.separator + WS + "backups"
                                       + File.separator + name[i]);
//startPR2008-04-08^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//            File undoFile = new File(sharkStartFrame.sharedPath + File.separator + "~WSDelTemp"
            File undoFile = new File(sharkStartFrame.sharedPathplus + tempDeleteFileName + currstu
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                                     + File.separator + name[i]);
//SS2008-04-01^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
            undone = undoFile.renameTo(undoneFile);
            if(undone){
//startPR2008-04-08^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
              GUI.setSizeLabelText();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
              scheduledBackups = u.addString(scheduledBackups, undoneFile.getPath());
              for (int j = 0; j < scheduledBackups.length; j++) { //Get rid of any null values
                if (scheduledBackups[j] == null) {
                  scheduledBackups = u.removeString(scheduledBackups, j);
                  j--;
                }
              }
            }
//SSend^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
          }
        }
      }
      else{
//startPR2008-04-08^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//         JOptionPane.showMessageDialog(GUI.bkupMain, "Cannot undo");
          JOptionPane.showMessageDialog(GUI.bkupMain, u.gettext("backup_undoerror", "label"));
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      }
//SS2008-04-01^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
      return undone;
//SSend^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS

    }

  /**
   * Deletes all files and subdirectories under dir.
   * Returns true if all deletions were successful.
   * If a deletion fails, the method stops attempting to delete and returns false.
   */
  public static boolean deleteDir(File dir) {
    if (dir.isDirectory()) {
        String[] children = dir.list();
        for (int i=0; i<children.length; i++) {
            boolean success = deleteDir(new File(dir, children[i]));
            if (!success) {
                return false;
            }
        }
    }
    // The directory is now empty so delete it
    return dir.delete();
  }

  /**
   * Finds the size of all backup files currently existing.
   * @return size of files
   */
  public String currBackupSize(){
//startPR2008-03-27^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    float size = 0; //holds the size in bytes of all files
//    if (scheduledBackups != null && oneOffBackups != null){
//      for (int i = 0; i < scheduledBackups.length; i++) {
//        if (scheduledBackups[i] != null) {
//          File bkup = new File(scheduledBackups[i]);
//          size = size + size(bkup);
//        }
//      }
//      for (int i = 0; i < oneOffBackups.length; i++) {
//        if (oneOffBackups[i] != null) {
//          File bkup = new File(oneOffBackups[i]);
//          size = size + size(bkup);
//        }
//      }
//      size = size / 1024 * 100;
//      size = size / 100;
//      String Kbytes = Float.toString(size);
//      Kbytes = Kbytes.substring(0, Kbytes.indexOf(".") + 3);
//      return Kbytes + "Kb";
//    }
//    else{
//      return "0 Kb";
//    }
    String location = null;
    if(schedule!=null && schedule.length>0)
      location = schedule[0];
    File f;
    if(!(location !=null && ((f = new File(location)).exists()))){
      f= new File(sharkStartFrame.sharedPathplus + File.separator+u.gettext("backup_",WS)+"backups");
    }
    float size = 0; //holds the size in bytes of all files
    if(f.exists()){
      File[] ff = f.listFiles(new FileFilter() {
        public boolean accept(File file) {
//startPR2008-04-10^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//          return file.getName().startsWith(WS);}});
          String s = newversiontext;
          String ss = s.substring(s.indexOf("%")+1);
          return (s=file.getName()).startsWith(WS)&&s.indexOf(ss)<0;}});
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      for(int i = 0; ff!=null && i < ff.length; i++){
        size += size(ff[i]);
      }
      if(size>0){
//startPR2008-04-10^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//        size = size / 1024;
//        String Kbytes = Float.toString(size);
//        return Kbytes.substring(0, Kbytes.indexOf(".") + 3) + "Kb";
        size = (size / 1024) / 1000;
        return String.valueOf(new DecimalFormat("0.##").format((double)size) + " MB");
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      }
    }
    return "0 Kb";
  }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR

  /**
   * Gives the size of the file passed to it in bytes
   * @param file File to be measured
   * @return Size of file in bytes
   */
  public long size(File file) {
    if (file.isFile())
      return file.length();
      File[] files = file.listFiles();
      long size = 0;
      if (files != null) {
        for (int i = 0; i < files.length; i++){
          size += size(files[i]);
        }
      }
      return size;
    }
    public void SetOptionsScheduledBkups(String[] bkups){
      db.update(sharkStartFrame.optionsdb, "backup_scheduledbkups",
          bkups, db.TEXT);
    }
//SSend^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
    /**
     * Saves details of backups done to the options database. Only called when the
     * backup GUI is closed.
     */
    public void endBackup(){
      openGuard();
      Backup_base.scheduledBkup.SetOptionsScheduledBkups(this.scheduledBackups);
      closeGuard();
    }


  }

//startPR2010-03-17^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//  private class OneOffBackup extends Bckup{
  public class OneOffBackup extends Bckup{
//endPRtoupdate^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    public OneOffBackup(){
      super();
    }

    /**
     * Adds all directories oneOff backups have been done to that are still recorded
     * @param combo JComboBox to be filled
     * @return Filled JComboBox
     */
    public JComboBox addDir(JComboBox combo){
      combo = super.addDir(combo, "oneOff");
      return combo;
    }
//SS2008-03-28^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
    public JComboBox addFile(JComboBox combo){
      combo = super.addFile(combo, "oneOff");
      return combo;
    }
  }
//SSend^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS

  private class ScheduledBackup extends Bckup{
    /**
     * true if a schedule has been set to do backups to
     */
    boolean active;
    public ScheduledBackup() {
      super();
    }

//SS2008-03-31^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS

    /**
     * Sets up a new schedule and does the first backup
     * @param initialise True if this is a completely new set up that needs to be
     * initialised i.e there are no options set yet
     */
    private void newSchedule() {
      String[] schedule;
      if(active){
        //get location for scheduled backups
        String location = (String) GUI.location2.getSelectedItem();
//        if(location.endsWith("backups")){
//           location = location.substring(0,location.length() - 10);
//        }
        chosenFile = new File(location);
//get date for 1atest backup
        Calendar d = new GregorianCalendar();
        long da = (long) (d.getTimeInMillis());
        String date = Long.toString(da);
//get number of days between backups
        String gap = String.valueOf(currSpinnerValue);
        schedule = new String[] {
            location, date, gap};
//Do backup
        super.BackupFiles("newSchedule");
        openGuard();
        db.update(sharkStartFrame.optionsdb, "backup_schedule", schedule,
                  db.TEXT);
        closeGuard();
      }
      else{
        //put null string into options db
        Bckup.schedule = null;
        String[] update;
        schedule = new String[] {
            "inactive", "inactive", "inactive"};
        db.update(sharkStartFrame.optionsdb, "backup_schedule", schedule,
                  db.TEXT);
      }
    }


    /**
     * Returns true if a back up needs to be done for the current schedule
     * @return True if backup needs to be done.
     */
    public boolean scheduledBackup() {
      boolean bkUp = false;
      if(schedule != null){
        if (!schedule[0].equals("inactive")) { //a schedule has been set up
          int gap = Integer.parseInt(schedule[2]);
          Calendar lastBkupDate = new GregorianCalendar();
          long d = Long.parseLong(schedule[1]);
          lastBkupDate.setTimeInMillis(d);
          lastBkupDate.add(lastBkupDate.DAY_OF_MONTH, gap); //Add "gap" days to the date of last backup
          Calendar today = new GregorianCalendar();
          if ( (today.getTime().after(lastBkupDate.getTime())) ||
              (today.getTime().equals(lastBkupDate.getTime()))) {
            bkUp = true;
            long da = (long) (today.getTimeInMillis());
            String now = Long.toString(da);
            String[] schedule2 = new String[] {
                schedule[0], now, schedule[2]};
            db.update(sharkStartFrame.optionsdb, "backup_schedule", schedule2,
                      db.TEXT);
          }
        }
      }
      return bkUp;
    }
//SS2008-03-28^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
    /*
     * Adds all files, scheduled backups have been done to, that are still recorded
     * @param combo JComboBox to be filled
     * @return Filled JComboBox
     */
    public JComboBox addFile(JComboBox combo) {
      combo.removeAllItems();
      combo = super.addFile(combo, "scheduled");
      return combo;
    }

//SSend^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
    /*
     * Adds all directories, scheduled backups have been done to, that are still recorded
     * @param combo JComboBox to be filled
     * @return Filled JComboBox
     */
    public JComboBox addDir(JComboBox combo) {
      combo = super.addDir(combo, "scheduled");
      return combo;
    }

  }
//startPR2007-04-23^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//  /**
//   * <p>Description: Provides functionality for choosing a directory via a tree structure</p>
//   * @author Sara Stewart
//   * @version 1.0
//   */
//  private static class DirectoryChooser extends JTree implements TreeSelectionListener, MouseListener {
//    private static FileSystemView fsv = FileSystemView.getFileSystemView();
//
//    public DirectoryChooser() {
//      this(null);
//    }
//
//    public DirectoryChooser(File dir) {
//      super(new DirNode(fsv.getRoots()[0]));
//startPR2006-11-17^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      setRowHeight(getFontMetrics(getFont()).getHeight());
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      getSelectionModel().setSelectionMode(TreeSelectionModel.
//                                           SINGLE_TREE_SELECTION);
//      setSelectedDirectory(dir);
//      addTreeSelectionListener(this);
//      addMouseListener(this);
//    }
//
//    public void setSelectedDirectory(File dir) {
//      if (dir == null) {
//        dir = fsv.getDefaultDirectory();
//      }
//      setSelectionPath(mkPath(dir));
//    }
//
//    public File getSelectedDirectory() {
//      DirNode node = (DirNode) getLastSelectedPathComponent();
//      if (node != null) {
//        File dir = node.getDir();
//        if (fsv.isFileSystem(dir)) {
//          return dir;
//        }
//      }
//      return null;
//    }
//
//    public void addActionListener(ActionListener l) {
//      listenerList.add(ActionListener.class, l);
//    }
//
//    public void removeActionListener(ActionListener l) {
//      listenerList.remove(ActionListener.class, l);
//    }
//
//    public ActionListener[] getActionListeners() {
//      return (ActionListener[]) listenerList.getListeners(ActionListener.class);
//    }
//
//        /*--------------implement treeSelectionListener interface-------------------*/
//
//    public void valueChanged(TreeSelectionEvent ev) {
//      File oldDir = null;
//      TreePath oldPath = ev.getOldLeadSelectionPath();
//      if (oldPath != null) {
//        oldDir = ( (DirNode) oldPath.getLastPathComponent()).getDir();
//        if (!fsv.isFileSystem(oldDir)) {
//          oldDir = null;
//        }
//      }
//      File newDir = getSelectedDirectory();
//      firePropertyChange("selectedDirectory", oldDir, newDir);
//    }
//
//        /*-----------------------Implement mouse interface--------------------------*/
//
//    public void mousePressed(MouseEvent e) {
//      if (e.getClickCount() == 2) {
//        TreePath path = getPathForLocation(e.getX(), e.getY());
//        if (path != null && path.equals(getSelectionPath()) &&
//            getSelectedDirectory() != null) {
//          fireActionPerformed("dirSelected", e);
//        }
//      }
//    }
//
//    public void mouseReleased(MouseEvent e) {}
//
//    public void mouseClicked(MouseEvent e) {}
//
//    public void mouseEntered(MouseEvent e) {}
//
//    public void mouseExited(MouseEvent e) {}
//
//        /*-----------------------Private Section-------------------------------------*/
//
//    private TreePath mkPath(File dir) {
//      DirNode root = (DirNode) getModel().getRoot();
//      if (root.getDir().equals(dir)) {
//        return new TreePath(root);
//      }
//      TreePath parentPath = mkPath(fsv.getParentDirectory(dir));
//       if(parentPath==null) return new TreePath(root);
//      DirNode parentNode = (DirNode) parentPath.getLastPathComponent();
//      Enumeration enumeration = parentNode.children();
//      while (enumeration.hasMoreElements()) {
//        DirNode child = (DirNode) enumeration.nextElement();
//        if (child.getDir().equals(dir)) {
//          return parentPath.pathByAddingChild(child);
//        }
//      }
//      return null;
//    }
//
//    private void fireActionPerformed(String command, InputEvent evt) {
//      ActionEvent e =
//          new ActionEvent(this, ActionEvent.ACTION_PERFORMED,
//                          command, evt.getWhen(), evt.getModifiers());
//      ActionListener[] listeners = getActionListeners();
//      for (int i = listeners.length - 1; i >= 0; i--) {
//        listeners[i].actionPerformed(e);
//      }
//    }
//
//    private static class DirNode
//        extends DefaultMutableTreeNode {
//      DirNode(File dir) {
//        super(dir);
//      }
//
//      public File getDir() {
//        return (File) userObject;
//      }
//
//      public int getChildCount() {
//        populateChildren();
//        return super.getChildCount();
//      }
//
//      public Enumeration children() {
//        populateChildren();
//        return super.children();
//      }
//
//      public boolean isLeaf() {
//        return false;
//      }
//
//      private void populateChildren() {
//        if (children == null) {
//          File[] files = fsv.getFiles(getDir(), true);
//          Arrays.sort(files);
//          for (int i = 0; i < files.length; i++) {
//            File f = files[i];
//            if (fsv.isTraversable(f).booleanValue()) {
//              insert(new DirNode(f), (children == null) ? 0 : children.size());
//            }
//          }
//        }
//      }
//
//      public String toString() {
//        return fsv.getSystemDisplayName(getDir());
//      }
//
//      public boolean equals(Object o) {
//        return (o instanceof DirNode &&
//                userObject.equals( ( (DirNode) o).userObject));
//      }
//    }
//  }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR

//SS2008-04-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
  /**
 * This is used only by restore at the moment. This represents a single backup
 */
private class SingleBackup{
  /**
   * The type of backup e.g. oneOff or scheduled
   */
  String type;
  /**
   * The file where backup is
   */
  File file;

  SingleBackup(String type, String path){
    this.type = type;
    file = new File(path);
  }

  /**
   *
   * @return Date if found or else null
   */
  public String getDate(){
    String date;
    String name = file.getName();
    int index = name.indexOf(WS) +2;
    if (index == -1){
      date = null;
    }
    else{
      date = name.substring(index);
    }
    boolean isDate = isDate(date);
    if (isDate){
      return date;
    }
    else{
      return null;
    }
  }

  public String getName(){
      return file.getName();
  }

  public String getType(){
      return type;
  }

  public File getFile(){
     return file;
  }
}


private class UserArchives{
  Date startdates[] = new Date[]{};
  Date enddates[] = new Date[]{};
  String user;
  
  UserArchives(String username, String archiveName){
      user = username;
      processArchive(archiveName);
  }
  
  public void addArchive(String archiveName){
      processArchive(archiveName);
  }
  
  private void processArchive(String s){
      int len = s.length();
      s = s.substring(len - 22 , len-4);
      SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM");
      try{
          Date d1 = sdf.parse(s.substring(0, 7));
          Date d2 = sdf.parse(s.substring(s.length()-7));
          startdates = u.addDate(startdates, d1);
          enddates = u.addDate(enddates, d2);
      }
      catch(ParseException e){}
  }
  
  public boolean isDateCovered(Date d){              
      for(int i = 0; i < startdates.length; i++){
          if((!d.before(startdates[i])) && (!d.after(enddates[i])))return true;
      }
      return false;
  }

  
}

//SSend^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
//startPR2010-04-16^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    String getDefaultPath() {
        String ret = null;
//        String sep = File.separator;
        if(shark.macOS){
            ret = shark.getProgramDataPath();
        }
        else{
            /*
            String ss = System.getProperty("user.home");
            String s = ss;
            s = s.substring(0, s.lastIndexOf(sep));
            File f;
            if (((f = new File(s + sep + "Public"))).exists()) {
                ret = f.getAbsolutePath();
            } else if (((f = new File(s + sep + "All Users"))).exists()) {
                ret = f.getAbsolutePath();
            } else {
                ret = ss;
            }
             */
            JFileChooser fr = new JFileChooser();
            javax.swing.filechooser.FileSystemView fw = fr.getFileSystemView();
            ret = fw.getDefaultDirectory().getAbsolutePath();
        }
        return ret;
    }
//endPRtoupdate^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
}
