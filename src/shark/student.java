
package shark;

import java.io.*;
import java.text.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;
import java.nio.channels.*;
//startPR2004-07-08^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
import java.awt.print.*;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR

public class student{
  static final short MAXSTUDENTRECORD = 1000,ARCHIVEAFTER=50;
  public static boolean isAdministrator;
  public static String adminlist[],admintopiclist[],teacherlist[],alllist[]; //rb 6/2/06
  public static String adminstudents[][];   // rb 8/2/06  list for each member of admin list    xxxx
  public static String deletedadmins[];
  // saved as savestudent class ------------------------------
  public String name;
  boolean isnew=true;
  public String spritename;
  boolean administrator;
  boolean teacher;               //rb 6/2/06  -  ready for v4
  protected String password;
  public String passwordhint;
  protected topicPlayed studentrecord[] = new topicPlayed[0];
  protected String[] students;
  public String currTopic;
  public String currTab;
      // start rb 23/10/06
  String workforteacher,teachers[];
      // end rb 23/10/06
  String version;            // rb 2/2/08
  char which;
  int sametopic;
  long lastplayed;

  FileOutputStream fflock;
//startPR2006-11-22^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  static String lockmess;//, lockmess2;
  static boolean lockmess2;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  static FileOutputStream fslotlock;  // file to lock for user slot on network
  static FileLock slotlock;
  static int slotnum;
//startPR2010-05-04^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  // keeps track of whether student's '.nlock' file is locked (for mac networks)
//  private static boolean macSlotLock = false;
  static boolean macSlotLock = false;
  // keeps track of whether student's '.lock' file is locked (for mac networks)
//  private boolean macLocked = false;
  public boolean macLocked = false;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  // end rb 22/1/06
  /**
   * Game currently selected
   */
  public String currGame;
  /**
   * Contains options for student e.g sprite choice,speed games etc.
   */
  String[] options;
//  static  javax.swing.Timer timer,timer2;
  static fish mainfish;
  static student newstu;
  static String gotstuname;
  boolean wantpics,wantpicsgames,wantsigns,wantrealpics,wantfingers,wantfingersall,picbg,nospiders;
  String picprefs[];
//startPR2008-06-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  boolean wantsignvids;
//endPRimagesandvids^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2008-08-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  boolean isfromnewerversion;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2008-09-21^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//  boolean programOverride;   //2222
  String programOverride[];    //3333
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  boolean gameicons;
  String besttime[],excludegames[];
  static byte rewardfreqno = 3;
  byte rewardfreq = (byte)(shark.phonicshark?rewardfreqno:0);
  Color printfg;
  static  javax.swing.Timer timer2;
  static byte MARGIN=2;
   static long lastflip;
//startPR2010-05-04^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//  static sharkImageSay inithelp,inithelp2;
  
  static runMovers fishpanel;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  /**
   * When true the student can change their word lists as long as the administrator
   * is logged on at the same time.
   */
  boolean override;
//startPR2005-11-29^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  // the student who has accidently been setup as administrator
  static student wrongAdmin = null;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  static int cry[] = {2000,33333,12121,23424,333,3455,32534,6567,3432,234,897};
  //-------------------------------------------------------
  static String sp = u.gettext("so","speed")+ ' ';
  static String er = u.gettext("so","errors");
  static String pe = u.gettext("so","peeps");
  static String ti = u.gettext("so","time")+' ';
  static String sc = u.gettext("so","score")+' ';
  static String archives = u.gettext("sturec_", "archive");
  static String arcend = u.gettext("sturec_", "arcend");
  static String recend = u.gettext("sturec_", "recend");

  static sharkImage tick = sharkImage.random("tick_"), cross = sharkImage.random("cross_");
  FileLock lock;


  /**
   * Contains the student's score for the current session only
   */
  public short totscore;
  public Date signonDate,signoffDate;
  int gamesforreward;
  static boolean v4message;
  static int v4errtot;
  static boolean skipv4chek;
  boolean delarchive,addedarchive;                  // rb 22/2/08   v5
  public boolean overriddenProgPhonics;
  static String signonname = null;
  
  
  static topicTree topics4;
  static topicTree topics5;
  static gamestoplay games5;
  
  static String gamech[] = null;
  static String gamech5[] = null;
  static String gs = null;
  static String ga5[] = null;
  static ArrayList gamechadd5 = null;
  static String coursech[] = null;  
  static String coursech5[] = null; 
  static String topicch[] = null;
  static String topicch5[] = null;
  static String movech5[] = null;
  static String headingch[] = null;
  static ArrayList headingch5;
  static String defaultcourse;
  static String defaultcourse5;
  static String currAdminListText;
  static String currAdminListText5;
  static String oldAdminListText[];
  static ArrayList headingmultch5;
  public boolean firstEverRun = false;
  public boolean patchNotified = false;
  

  public static class programitem  implements Serializable{
      static final long serialVersionUID = 8369073967857812438L;
      String topics[] = new String[0];
      saveTree1.saveTree2 trees[] = new saveTree1.saveTree2[0];
      String games[] = new String[0];
      short maxerrors = 2, mustcomplete = 1;
      boolean phonics;
  }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2010-05-04^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2004-10-20^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//  /**
//   * The sign-on panel
//   */
//  static public signonpanel signpanel;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  boolean hasimages,hastext,hassay,hastopics,hasgames;
  static int kw=0,kh=0;
  public boolean okPassword(String trypass) {
     return(password==null 
             || trypass.equalsIgnoreCase(decrypt(password))
                    || trypass.equalsIgnoreCase(password)
                    || (decrypt(password)!=null && trypass.equalsIgnoreCase(decrypt(password).replaceAll(" ", "")))
                    || trypass.equalsIgnoreCase(password.replaceAll(" ", ""))
//startPR2005-05-25^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                    || trypass.equals(getForgottenPassword(trypass, newstu.name)));
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  }
//startPR2005-05-25^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  private String getForgottenPassword(String passEntered, String pass){
    if(pass.length()==0)
      return " ";
    while(pass.length() < 5){
      pass = pass.concat(String.valueOf(pass.charAt(pass.length() - 1)));
    }
    if(pass.length() > 5)
      pass = pass.substring(pass.length()-5,pass.length());
    pass = pass.toLowerCase();
    String num = "";
    for(int i = 0; i < pass.length(); i++){
      num =  num.concat(String.valueOf(Character.getNumericValue(pass.charAt(i))));
    }
    num = num.replaceAll("-", "");
    long p = Long.parseLong(num);
    p = p + 14 * 4535;
    num = String.valueOf(p);
    if(num.length() > 5)
      num = num.substring(num.length()-5,num.length());
    // check to see if code entered is for accidental child administrator
    if(passEntered.length()>5){
      String ch = passEntered.substring(5,6);
      if (num.equals(passEntered.substring(0, 5))&&(ch.equals("0")) && newstu.isAdministrator && !newstu.teacher) {
        wrongAdmin = newstu;
        return num.concat(ch);
      }
    }
    return num;
  }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR

  public student(String name1) {
     name = name1;
     wantpics=wantpicsgames=wantsigns=wantrealpics=gameicons=true;
//startPR2008-06-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     wantsignvids=true;
//endPRimagesandvids^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2008-06-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//     version = shark.versionNo;    // rb 5/2/08
     version = shark.versionNoDetailed;    // rb 5/2/08
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     which = 'W';                  // rb 5/2/08
   }
   public student() {
     wantpics=wantpicsgames=wantsigns=wantrealpics=gameicons=true;
//startPR2008-06-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     wantsignvids=true;
//endPRimagesandvids^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2008-06-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//     version = shark.versionNo;    // rb 5/2/08
      version = shark.versionNoDetailed;    // rb 5/2/08
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     which = 'W';                  // rb 5/2/08
   }
//startPR2006-04-30^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   /**
    * Cross-platform way of checking if the student's '.lock' file has a lock on it
    *
    * @return boolean true if there is an existing lock.
    */
   public boolean isLocked() {
//startPR2010-03-15^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      if(shark.macOS && shark.network){
      if(MacLock.DOJNI)
//endPRtoupdate^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       return macLocked;
     else
       return lock != null;

   }
   /**
    * Cross-platform way of checking if the student's '.nlock' file has a lock on it
    *
    * @return boolean true if there is an existing lock.
    */
   private static boolean isSlotLocked() {
//startPR2010-03-15^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      if(shark.macOS && shark.network){
      if(MacLock.DOJNI)
//endPRtoupdate^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       return macSlotLock;
     else
       return slotlock != null;
   }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         // rb 6/2/06  start of new admin list routines
   //-----------------------------------------------------
   public static void getadmin() {   // just get current admin lists
       skipv4chek = true;
       getadmin((byte)0,null, null, false, null);
       skipv4chek = false;
   }
   //-----------------------------------------------------
   public static void checkadmin() {
     getadmin((byte)1,null, null, false, null);                 // get and check for new/deleted students
   }
   
   public static void checkadmin(String deletedadmin) {
     getadmin((byte)1,null, null, false, deletedadmin);                 // get and check for new/deleted students
   }   
   //-----------------------------------------------------
   public static void checkadmin(student stu) {
       getadmin((byte)2,stu,null,false, null);                 // get and check particular student
   }
   public static void checkadmin(student changedtoadmin, boolean stuadminchange) {
       getadmin((byte)2,changedtoadmin, changedtoadmin, stuadminchange, null);
   }
  //-----------------------------------------------------
   public static void getadmin(byte type, student stu, student changedtoadmin, boolean stuadminchange, String deletedadmin) { // 0 =just get, 1 = get & check  + extra check for stu if non-null
    long len;
    boolean ok = true;
    File f = new File(sharkStartFrame.sharedPathplus + "admin_lists");
    RandomAccessFile ff = null;
    FileLock lock = null;
    boolean rebuild = false,changed = false;
    if(f.exists()) {
      try {
//startPR2006-08-19^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        if(Demo_base.isDemo)
          ff = new RandomAccessFile(f, "r");
        else
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          ff = new RandomAccessFile(f, "rw");
//startPR2006-04-30^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2010-03-15^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      if(shark.macOS && shark.network){
      if(MacLock.DOJNI){
//endPRtoupdate^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          // new file need to lock onto for mac networks
          if(MacLock.lock(sharkStartFrame.sharedPathplus+"admin_lists",MacLock.LOCKEXTENSION, null, true)<0){
            u.okmess(shark.programName, "irrecoverable IO error in admin");
            System.exit(0);
          }
        }
        else{
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2006-08-19^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        if(!Demo_base.isDemo)
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2007-11-19^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//          lock = ff.getChannel().lock();
        {
          FileChannel fc = ff.getChannel();
          // IOException needs to be caught on Linux cifs networks
          try {lock = fc.lock(); } catch (IOException e) {
          }
//startPR2010-03-18^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          catch (OverlappingFileLockException e) {
          }
//endPRtoupdate^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2006-04-30^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        byte buf[] = new byte[ (int) ff.length()];
        ff.read(buf);
        ObjectInputStream oo = new ObjectInputStream(new ByteArrayInputStream(buf));
        adminlist = (String[]) oo.readObject();
        admintopiclist = (String[]) oo.readObject();
        teacherlist = (String[]) oo.readObject();
        alllist = (String[]) oo.readObject();
        adminstudents = (String[][]) oo.readObject();   // rb 9/2/06  list for each member of admin list
        if(adminStusHasDuplicates(adminstudents))rebuild = true;
        deletedadmins = (String[]) oo.readObject();
      }
      catch (IOException e) {
        rebuild = true;
      }
      catch (ClassNotFoundException e) {
        rebuild = true;
      }
    }
    else {
      try {
        f.createNewFile();
        ff = new RandomAccessFile(f, "rw");
//startPR2009-08-02^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        u2_base.setNewFilePermissions(f);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2006-04-30^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//        lock = ff.getChannel().lock();
//startPR2010-03-15^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      if(shark.macOS && shark.network){
      if(MacLock.DOJNI){
//endPRtoupdate^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          if (MacLock.lock(sharkStartFrame.sharedPathplus + "admin_lists",
                           MacLock.LOCKEXTENSION, null, true) < 0) {
            u.okmess(shark.programName, "irrecoverable IO error in admin");
            System.exit(0);
          }
        }
        else
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2007-11-19^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//            lock = ff.getChannel().lock();
        {
            FileChannel fc = ff.getChannel();
            // IOException needs to be caught on Linux cifs networks
            try {lock = fc.lock(); } catch (IOException e) {}
//startPR2010-03-18^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            catch (OverlappingFileLockException e) {}
//endPRtoupdate^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        rebuild = true;
      }
      catch (IOException e) {
        u.okmess(shark.programName, "irrecoverable IO error in admin");
        System.exit(0);
      }
    }
    if(rebuild) {   // complete rebuild required
       adminlist = new String[0];   // drop thru to here if error
       teacherlist = new String[0];
       admintopiclist = new String[0];
       alllist = new String[0];
       adminstudents = new String[0][];  // 9/2/06
       deletedadmins = new String[0];
    }
    if(rebuild || type > 0) {
      changed = checklists(changedtoadmin, stuadminchange, deletedadmin);
    }
    if(stu != null) {
      changed |= stu.checkstu();
    }
    if(changed) {
      try {
        ByteArrayOutputStream outb = new ByteArrayOutputStream();
        ObjectOutputStream oo = new ObjectOutputStream(outb);
        for(int i = 0; i < deletedadmins.length; i++){
            int k;
            if((k=u.findString(adminlist, deletedadmins[i]))>=0)
                u.removeString(adminlist, k);
            if((k=u.findString(teacherlist, deletedadmins[i]))>=0)
                u.removeString(teacherlist, k);
        }
        oo.writeObject(adminlist);
        oo.writeObject(admintopiclist);
        oo.writeObject(teacherlist);
        oo.writeObject(alllist);
        oo.writeObject(adminstudents);    //  9/2/06
        oo.writeObject(deletedadmins);
        ff.seek(0);
        ff.write(outb.toByteArray());
        ff.setLength(outb.size());
      }
      catch (IOException e) {
      }
    }
    try {
//startPR2006-04-30^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      if(lock != null)
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        lock.release();
//startPR2009-08-19^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2010-03-15^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      if(shark.macOS && shark.network){
      if(MacLock.DOJNI){
//endPRtoupdate^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//        MacLock.unlock(sharkStartFrame.sharedPathplus + "admin_lists", MacLock.LOCKEXTENSION);
        MacLock.delete(sharkStartFrame.sharedPathplus + "admin_lists", MacLock.LOCKEXTENSION);
      }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      ff.close();
    }
    catch (IOException e) {
    }
    isAdministrator = adminlist.length > 0;
  }
    //-----------------------------------------------------
  public static boolean checklists(student statuschanged, boolean stuadminchange, String deleteadmin) {       // recalc the admin lists - return true if changed
//      student teacher;
      student ss;
//      String name;
      String list[] = db.dblistnames(sharkStartFrame.sharedPath);
//      String[] tt = alllist;
      boolean changed = false;
      if(deleteadmin!=null){
          if(u.findString(deletedadmins, deleteadmin)<0){
              changed = true;
              u.addStringSort(deletedadmins, deleteadmin);
          }
      }
      int i,j,k;
      for(i=j=0; i<alllist.length || j<list.length; ) {
        if(i >= alllist.length) k = 1;  // new
        else if(j >= list.length) k = -1;  // removed
        else {
            // both to lower case as otherwise k can be +ve rather than 
            // -ve (and vice versa?) - if one name starts with caps and the other doesn't.
            k = alllist[i].toLowerCase().compareTo(list[j].toLowerCase());
        }
        if(statuschanged!=null){
            changed = true;
            if(statuschanged.teacher){
                if(u.findString(teacherlist, statuschanged.name)<0)
                    u.addStringSort(teacherlist, statuschanged.name);
                int m;
                if((m=u.findString(deletedadmins, statuschanged.name))>=0) u.removeString(deletedadmins, m);
                u.removeString(adminlist, statuschanged.name);
            }
            else if(statuschanged.administrator){
                if(u.findString(adminlist, statuschanged.name)<0)
                    u.addStringSort(adminlist, statuschanged.name);
                int m;
                if((m=u.findString(deletedadmins, statuschanged.name))>=0) u.removeString(deletedadmins, m);
                u.removeString(teacherlist, statuschanged.name);
            }
            else{
                u.removeString(adminlist, statuschanged.name);
                u.removeString(teacherlist, statuschanged.name);
                if(stuadminchange)
                    admintopiclist = u.removeString(admintopiclist, statuschanged.name);
            }
            if(stuadminchange)
                delete_adminstu_statuschange(statuschanged);
        }
        if (k > 0) {    // new one
          changed = true;
          skipv4chek = true;
          if ( (ss = getStudent(list[j])) != null)  {
            ss.checkstu();
          }
          skipv4chek = false;
           ++j;
        }
        else if (k < 0) {    // deleted
          changed = true;
          adminlist = u.removeString(adminlist, alllist[i]);
          teacherlist = u.removeString(teacherlist, alllist[i]);
          admintopiclist = u.removeString(admintopiclist, alllist[i]);
          delete_adminstu(alllist[i]);   // rb 9/2/06
          ++i;
        }
        else {++i;++j;}
      }
      alllist = list;
      return changed;
  }
  
  static boolean adminStusHasDuplicates(String sss[][]){
      String ss[] = new String[]{};
      for(int i = 0; sss!=null && i < sss.length; i++){
          if(u.findString(ss, sss[i][0])>=0)
              return true;
          ss = u.addString(ss, sss[i][0]);
      }
      return false;
  }  
  
  boolean checkstu() {   // check that all lists are correct for student
    int i;
    boolean changed = false;
    if (administrator) {
      if (teacher) {
        if((i=u.findString(adminlist,name)) >= 0) {adminlist = u.removeString(adminlist, i);changed=true;}
        if(u.findString(teacherlist,name) < 0) {
            teacherlist = u.addStringSort(teacherlist, name);changed=true;
            int m;
            if((m=u.findString(deletedadmins, name))>=0) u.removeString(deletedadmins, m);
        } 
      }
      else {
        if((i=u.findString(teacherlist,name)) >= 0) {teacherlist = u.removeString(teacherlist, i);changed=true;}
        if(u.findString(adminlist,name) < 0) {
            adminlist = u.addStringSort(adminlist, name);changed=true;
            int m;
            if((m=u.findString(deletedadmins, name))>=0) u.removeString(deletedadmins, m);
        }
        
      }
      if(hastopics) {
        if(u.findString(admintopiclist,name) < 0) {admintopiclist = u.addStringSort(admintopiclist, name);changed=true;}
      }
      else {
        if((i = u.findString(admintopiclist,name)) >= 0) {admintopiclist = u.removeString(admintopiclist, i);changed=true;}
      }
      if(students != null && students.length >0) {   // start rb 9/2/06
          if( check_adminstu(u.addString(new String[]{name},students))) changed = true;
      }
      else {
          if(delete_adminstu(name)) changed = true;
      }                                                    // end rb 9/2/06
    }
    else {
      if((i=u.findString(adminlist,name)) >= 0) {adminlist = u.removeString(adminlist, i);changed=true;}
      if((i=u.findString(teacherlist,name)) >= 0) {teacherlist = u.removeString(teacherlist, i);changed=true;}
      if((i=u.findString(admintopiclist,name)) >= 0) {admintopiclist = u.removeString(admintopiclist, i);changed=true;}
      if(delete_adminstu(name)) changed = true;   // rb 9/2/06
    }
    return changed;
  }
  // rb 6/2/06  end of new admin list routines
  //--------------------------------------------------------// start rb 9/2/06
  static boolean delete_adminstu(String name) {
    for(int m = 0; m<adminstudents.length;++m) {
       if(adminstudents[m][0].equals(name))  {
         String[][] aa = new String[adminstudents.length-1][];
         System.arraycopy(adminstudents,0,aa,0,m);
         if(m<adminstudents.length-1)  System.arraycopy(adminstudents,m+1,aa,m,adminstudents.length-1-m);
         adminstudents = aa;
         return true;
        }
    }
    return false;
  }


  static boolean delete_adminstu_statuschange(student stnt) {
    boolean changed = false;
    loop1: for(int m = 0; m<adminstudents.length;++m) {
        if(adminstudents[m][0].equals(stnt.name))  {
          String[][] aa = new String[adminstudents.length-1][];
          System.arraycopy(adminstudents,0,aa,0,m);
          if(m<adminstudents.length-1)  System.arraycopy(adminstudents,m+1,aa,m,adminstudents.length-1-m);
          adminstudents = aa;
          changed = true;
          continue loop1;
        }
        String aa[] = adminstudents[m];
        if(u.findString(aa, stnt.name)>=0){
            changed = true;
            aa = u.removeString2(aa, stnt.name);
            adminstudents[m] = aa;
        }
    }
    return changed;
  }

  static boolean check_adminstu(String[] list) {
    for(int m = 0; m<adminstudents.length;++m) {
       if(adminstudents[m][0].equals(list[0]))  {
           if(u.equalStrings(adminstudents[m],list)) return false;   // not changed
           adminstudents[m] = list;
           return true;
       }
    }
    String[][] aa = new String[adminstudents.length+1][];
    System.arraycopy(adminstudents,0,aa,0,adminstudents.length);
    aa[adminstudents.length] = list;
    adminstudents = aa;
    return true;
  }
  //-------------------------------------------------------   // end  rb 9/2/06
  
//  boolean makeadmin() {
//     if(!isAdministrator) {
//       String title = u.gettext("so","title2");
//       if(JOptionPane.showConfirmDialog(sharkStartFrame.mainFrame,
//                 u.splitString(u.gettext("so","admin",name)),
//                 title,
//                 JOptionPane.YES_NO_OPTION,
//                 JOptionPane.QUESTION_MESSAGE)
//                                == JOptionPane.YES_OPTION) {
//           if(password==null || password.length()==0) {
//               String op[] =  new String[]{u.gettext("so","ok")};
//               JOptionPane getpw = new JOptionPane(
//                  u.gettext("so", "passwordx"),
//                  JOptionPane.PLAIN_MESSAGE,
//                  0,
//                  null,op,op[0]);
//               getpw.setWantsInput(true);
//startPR2004-10-20^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//               Dialog dialog = getpw.createDialog(sharkStartFrame.mainFrame,u.gettext("beadmin","heading"));
//               JDialog dialog = getpw.createDialog(sharkStartFrame.mainFrame,u.gettext("beadmin","heading"));
//               dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//               while(true) {
//startPR2004-10-20^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//                 // stops exiting via the Escape key
//                 u.blockEscape = true;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//                 dialog.setVisible(true);
//startPR2004-10-20^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//                 u.blockEscape = false;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//                 String input = (String)getpw.getInputValue();
//                 while(input.length() > 0 && input.charAt(input.length()-1) == ' ')
//                      input = input.substring(0,input.length()-1);
//                 if(input.length() > 0) {
//                    password= encrypt(input);
//                    break;
//                 }
//              }
//           }
//           administrator = true;
//           isAdministrator = true;
//           if(isnew) {
//              String dbpath = sharkStartFrame.sharedPath.toString();
//              String dbname = dbpath + sharkStartFrame.sharedPath.separatorChar+name;
//              db.create(dbname);
//           }
//           saveStudent();
//           checkadmin(this);  //rb 6/2/06
//           return true;
//        }
//        else return false;
//    }
//    return true;
//  }
  //-----------------------------------------------------------
  boolean isOver(String stuname) {
     short i;
     student stu;
     if(students != null) {
        for(i = 0;i<students.length;++i) {
          if(students[i].equalsIgnoreCase(stuname)) return true;
          stu = findStudent(students[i]);
          if(stu != null && stu.isOver(stuname)) return true;
        }
     }
     return false;
  }
  //-----------------------------------------------------------
  static student[] addStudent(student sa[], student s) {
     if(sa == null) return new student[] {s};
     short len = (short)sa.length;
     student news[] = new student[len+1];
     System.arraycopy(sa,0,news,0,len);
     news[len] = s;
     return news;
 }
  //-----------------------------------------------------------
  public static student findStudent(String name) {
    student stu = getStudent(name);
    if(stu != null) {
       return(stu);
    }
    return null;
  }
  //--------------------------------------------------------------
  public boolean isSignedOn() {
     short i,j;
     if(sharkStartFrame.studentList == null) return false;
     for(i=0; i<sharkStartFrame.studentList.length; ++i) {
        if(sharkStartFrame.studentList[i].name.equalsIgnoreCase(name)
  )
               return true;
     }
     return false;
  }

  public student getSignedOn() {
     short i,j;
     if(sharkStartFrame.studentList == null) return null;
     for(i=0; i<sharkStartFrame.studentList.length; ++i) {
        if(sharkStartFrame.studentList[i].name.equalsIgnoreCase(name)
  )
               return sharkStartFrame.studentList[i];
     }
     return null;
  }

  //--------------------------------------------------------------
  public static boolean isSignedOn(String db1) {
     short i,j;
     if(sharkStartFrame.studentList == null) return false;
     for(i=0; i<sharkStartFrame.studentList.length; ++i) {
        if(sharkStartFrame.studentList[i].name.equalsIgnoreCase(db1))
               return true;
     }
     return false;
  }
  //--------------------------------------------------------------
  static student getSignedOn(String name) {
     short i,j;
     if(sharkStartFrame.studentList == null) return null;
     for(i=0; i<sharkStartFrame.studentList.length; ++i) {
        if(sharkStartFrame.studentList[i].name.equalsIgnoreCase(name))
               return sharkStartFrame.studentList[i];
     }
     return null;
  }
  //--------------------------------------------------------------
  public void removeStudent(String sname) {
      if(students == null || students.length ==0) return;
      int len = students.length,i;
      for(i=0;i<len;++i) {
          if(students[i].equalsIgnoreCase(sname)) {
             String news[] = new String[len-1];
             System.arraycopy(students,0,news,0,i);
             if(len>i-1) System.arraycopy(students,i+1,news,i,len-i-1);
             students = news;
             return;
         }
     }
 }

  public boolean isCurrentStuOf(String teacher) {
      if(administrator)return false;
      for(int i = 0; i < adminstudents.length; i++){
          if(adminstudents[i][0].equals(teacher)){
              if(u.findString(adminstudents[i], name)>=0)return true;
          }
      }
      return false;
 }
  //-------------------------------------------------------------
  public void signoff(boolean setup) {
    short i, j;
    JOptionPane getpw;
    JDialog dialog;
    u.showhelp help = null;
    sharkGame.otherplayer = null;
    if(sharkStartFrame.sharewith!=null){
        for(int k = sharkStartFrame.sharewith.size()-1; k >= 0 ; k--){
            String ss[] = (String[])sharkStartFrame.sharewith.get(k);
            if(u.findString(ss, name)>=0){
                sharkStartFrame.sharewith.remove(k);
            }
        }
    }
    signoffDate = new Date();
    clearOption2("notmarkedgames");   // rb 10-12-07
//startPR2004-10-05^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    for(i = startrecord ;i < studentrecord.length;++i)
    for (i = (short) (studentrecord.length - 1);
         i >= 0 && studentrecord[i].dateoff == null; --i) {
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      studentrecord[i].dateoff = signoffDate;
    }
//startPR2004-10-05^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    if (isnew) {
      String title = u.gettext("so", "title", name);
      if (!isAdministrator &&
          JOptionPane.showConfirmDialog(sharkStartFrame.mainFrame,
                                        u.gettext("so", "save", name),
                                        title,
                                        JOptionPane.YES_NO_OPTION,
                                        JOptionPane.QUESTION_MESSAGE)
          == JOptionPane.YES_OPTION) {
        String op[] = new String[] {
            u.gettext("so", "ok"), u.gettext("so", "nopass")};
        getpw = new JOptionPane(
            u.gettext("so", "password"),
            JOptionPane.QUESTION_MESSAGE,
            0,
            null, op, op[0]);
        getpw.setWantsInput(true);
//startPR2004-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//            dialog = getpw.createDialog(null,title);
        dialog = getpw.createDialog(sharkStartFrame.mainFrame, title);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        if (sharkStartFrame.mainFrame.mwanthelp.isSelected())
          help = new u.showhelp(u.gettext("so", "passtooltip")); ;
        while (true) {
          dialog.setVisible(true);
          Object result = getpw.getValue();
          String input = (String) getpw.getInputValue();
          if (result == op[1])
            break;
          if (input.length() > 0) {
            if (help != null) {
              help.dispose();
              help = null;
            }
            password = encrypt(input);
            break;
          }
        }
        if (help != null) {
          help.dispose();
          help = null;
        }
        String dbpath = sharkStartFrame.sharedPath.toString();
        String dbname = dbpath + sharkStartFrame.sharedPath.separatorChar + name;
        db.create(dbname);
        saveStudent();
        checkadmin(this);  //rb 6/2/06
      }
    }
    else {
      updatestudent();
    }
//startPR2006-04-30^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    if (lock != null) {
    if (isLocked()) {
//startPR2010-03-15^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      if(shark.macOS && shark.network){
      if(MacLock.DOJNI){
//endPRtoupdate^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        macLocked = false;
        MacLock.delete(sharkStartFrame.sharedPathplus + name, ".lock");
        MacLock.delete(sharkStartFrame.sharedPathplus + name, MacLock.LOCKEXTENSION);
      }
      else{
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        try {
          lock.release();
          lock = null;
          fflock.close();
          fflock = null;
          new File(sharkStartFrame.sharedPathplus + name + ".lock").delete();
        }
        catch (IOException e) {
        }
//startPR2006-04-30^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    }
    // start rb 22/1/06
//startPR2006-08-19^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    if(sharkStartFrame.studentList.length==1 && slotlock != null) {
    if((sharkStartFrame.studentList==null ||sharkStartFrame.studentList.length==1) && isSlotLocked()) {
//startPR2010-03-15^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      if(shark.macOS && shark.network){
      if(MacLock.DOJNI){
//endPRtoupdate^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        macSlotLock = false;
        MacLock.delete(sharkStartFrame.sharedPathplus + "user"+String.valueOf(slotnum + 1), ".nlock");
        new File(sharkStartFrame.sharedPathplus + "user"+String.valueOf(slotnum + 1) + ".nlock2").delete();
      }
      else{
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        try {
          slotlock.release();
          slotlock = null;
          fslotlock.close();
          fslotlock = null;
          new File(sharkStartFrame.sharedPathplus + "user" + String.valueOf(slotnum+1) + ".nlock").delete();
          new File(sharkStartFrame.sharedPathplus + "user" + String.valueOf(slotnum+1) + ".nlock2").delete();
        }
        catch (IOException e) {
        }
//startPR2006-04-30^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    }
    // end rb 22/1/06
    for (i = 0; sharkStartFrame.studentList!=null && i < sharkStartFrame.studentList.length; ++i) {
      if (sharkStartFrame.studentList[i] == this) {
        student newstu[] = new student[sharkStartFrame.studentList.length - 1];
        if (i > 0)
          System.arraycopy(sharkStartFrame.studentList, 0, newstu, 0, i);
        if (i < sharkStartFrame.studentList.length - 1)
          System.arraycopy(sharkStartFrame.studentList, i + 1, newstu, i,
                           sharkStartFrame.studentList.length - i - 1);
        sharkStartFrame.studentList = newstu;
        if (sharkStartFrame.currStudent == i) {
          sharkStartFrame.currStudent = (short) Math.min(i,
              sharkStartFrame.studentList.length - 1);
        }
        else if (sharkStartFrame.currStudent > i) {
          --sharkStartFrame.currStudent;
        }
        if (sharkStartFrame.studentList.length == 0)
          sharkStartFrame.currStudent = -1;

        if(sharkStartFrame.currStudent >= 0)
            sharkStartFrame.resourcesdb = sharkStartFrame.resourcesPlus+
                sharkStartFrame.studentList[sharkStartFrame.currStudent].name +
                sharkStartFrame.resourcesFileSuffix;
        if (setup) {
          if (sharkStartFrame.currStudent >= 0)
            sharkStartFrame.studentList[sharkStartFrame.currStudent].
                finishSignon(true);
          else {
            sharkStartFrame.mainFrame.clearStudentMenu();
            javax.swing.Timer tt = new javax.swing.Timer(200,
                new ActionListener() {
              public void actionPerformed(ActionEvent e) {
                sharkStartFrame.mainFrame.signon();
              }
            }
            );
            tt.setRepeats(false);
            tt.start();
//                 sharkStartFrame.mainFrame.signon();
          }
        }
        break;
      }
    }
    if (help != null) {
      help.dispose();
      help = null;
    }
  }
  boolean updatestudent() {    // start rb 27/10/06
        boolean ret = doupdates(true,true);
//        db db1 = db.get(name,false);   // keep db1 open and locked throughout this method
//        if(db1 == null)
//          return false;
//        savestudent copystu = (savestudent)db1.find("student",db.STUDENT);
//        if(copystu==null) return false;
//        excludegames = copystu.excludegames;  // these may have been updated by admin
//        password = copystu.password;
//        studentrecord = copystu.studentrecord;   // moved for v4
//       if(sr != null) {
//          studentrecord = new topicPlayed[copystu.studentrecord.length + 1];
//          System.arraycopy(copystu.studentrecord, 0, studentrecord, 0, copystu.studentrecord.length);
//          studentrecord[studentrecord.length - 1] = sr;
//          if (studentrecord.length > MAXSTUDENTRECORD) {
//             topicPlayed sr2[] = new topicPlayed[MAXSTUDENTRECORD];
//             System.arraycopy(studentrecord, studentrecord.length - MAXSTUDENTRECORD, sr2, 0, MAXSTUDENTRECORD);
//             studentrecord = sr2;
//           }
//        }
        return ret;
  }
  //   v4 - queue an update to a student
  static void queueupdate(String stu[], String val[]) {
     int i;
     String s = String.valueOf(System.currentTimeMillis());
     s = "_refresh" + "00000000000000000000".substring(20-s.length())+ s;  // right-justify

     for(i=0;i<stu.length;++i) {
       db.update(stu[i],s,val,db.TEXT);
     }
  }
  //--------------------------------------------------------------
  void addtolist() {
     if(sharkStartFrame.studentList != null) {
        student newlist[] = new student[sharkStartFrame.studentList.length+1];
        System.arraycopy(sharkStartFrame.studentList,0,newlist,0,sharkStartFrame.studentList.length);
        newlist[sharkStartFrame.studentList.length] = this;
        sharkStartFrame.studentList = newlist;
     }
     else sharkStartFrame.studentList = new student[] {this};
  }
  static String encrypt(String s) {
     int len = s.length();
     if(len==0) return s;
     char ss[] = new char[len];
     for(int i=0;i<len;++i) ss[i] = (char)(s.charAt(((i&1)==0)?(i/2):(len-i/2-1))^cry[i%cry.length]);
     return String.valueOf(ss);
  }
  static String decrypt(String s) {
     int len = s.length();
     if(len==0) return s;
     char ret[] = new char[len];
     for(short i=0;i<len;++i) ret[((i&1)==0)?(i/2):(len-i/2-1)] = (char)( s.charAt(i)^cry[i%cry.length]);
     return String.valueOf(ret);
   }
   //-------------------------------------------------------------------------------



   public program.saveprogram fixsaveprogram4(
       boolean simplescan,
       topicTree topics,
//       gamestoplay games,
       String spname,
       program.saveprogram p,
       String coursech[],
       String headingch[],
       String topicch[],
       String gamech[],
       String oldAdminListText[],
       String currAdminListText,
       String defaultcourse) {
     boolean changed;
     boolean invalidwork = false;
     changed = false;
     int k, m, n, q;
     program.programitem it;




     for (k = 0; k < p.it.length; ++k) {       // scan steps
       it = p.it[k];
       // if student has been asssigned all word lists from v3 - give the
       // mixed list of first course in the topic tree.
       if(it.topics.length==0){
         jnode node = topics.getNode(defaultcourse);
         it.topics = u.addString(it.topics,topics.savePath(node));
         saveTree1.saveTree2 nt[] = new saveTree1.saveTree2[it.trees.length + 1];
         System.arraycopy(it.trees, 0, nt, 0, it.trees.length);
         it.trees = nt;
         it.trees[it.trees.length - 1] = (new saveTree1(topics, node)).curr;
         changed = true;
       }
       topicscan:for (m = 0; m < it.topics.length; ++m) {
         String ss = it.topics[m];
          boolean oldadminlist = false;
          for(int y = 0; y < oldAdminListText.length; y++){
            if(ss.startsWith(oldAdminListText[y]+topicTree.CSEPARATOR)){
              oldadminlist=true;
              break;
            }
          }
          if (ss.indexOf(topicTree.ISTOPIC) < 0 || oldadminlist) { // check only public topics
           jnode jj;
           if ((jj=(jnode)topics.expandPath(ss)) == null) {
             // adjust the path to assigned admin lists.
             // substistute the new name for the parent node to
             // the administrator lists
             if(oldadminlist){
               String adminname = null;
               String sstemp = ss;
               int i1,i2;
               i1 = sstemp.indexOf(topicTree.ISTOPIC);
               i2 = sstemp.indexOf(topicTree.ISPATH);
               if(i1+1<i2)
                 adminname = sstemp.substring(i1+1,i2);
               sstemp = sstemp.substring(i1);
               if(adminname!=null){
                 sstemp = currAdminListText + topicTree.CSEPARATOR + adminname + topicTree.CSEPARATOR +sstemp;
                 ss = it.topics[m] = sstemp;
                 changed = true;
                 jnode jn;
                 if ((jn = topics.expandPath(ss)) != null){
                   it.trees[m] = (new saveTree1(topics, jn)).curr;
                   continue topicscan;
                 }
               }
             }
             String ss2[] = u.splitString(ss, topicTree.CSEPARATOR);
             for (n = 0; n < coursech.length; n += 2) {
               if (ss2[0].equals(coursech[n])) {
                 ss2[0] = coursech[n + 1];
                 ss = it.topics[m] = u.combineString(ss2, new String(new char[] {topicTree.CSEPARATOR}));
                 changed = true;
                  jnode jn;
                  if ((jn = topics.expandPath(ss)) != null){
                    it.trees[m] = (new saveTree1(topics, jn)).curr;
                    continue topicscan;
                  }
                 break;
               }
             }
             int last = ss2.length - 1;
             if (last > 0) {
               for (n = 0; n < topicch.length; n += 2) {
                 if (ss2[last].equals(topicch[n])) {
                   ss2[last] = topicch[n + 1];
                   ss = it.topics[m] = u.combineString(ss2, new String(new char[] {topicTree.CSEPARATOR}));
                   changed = true;
                    jnode jn;
                    if ((jn = topics.expandPath(ss)) != null){
                      it.trees[m] = (new saveTree1(topics, jn)).curr;
                      continue topicscan;
                    }
                   break;
                 }
               }
               String origss2[] = new String[ss2.length];
               System.arraycopy(ss2,0,origss2,0,ss2.length);
               for (n = 0; n < headingch.length; n += 3) {
                 if (ss2[0].equals(headingch[n])) {
                    for (q = 1; q < ss2.length; ++q) {
                     if (origss2[q].equals(headingch[n + 1])) {
                       ss2[q] = headingch[n + 2];
                       ss = it.topics[m] = u.combineString(ss2, new String(new char[] {topicTree.CSEPARATOR}));
                       changed = true;
                        jnode jn;
                        if ((jn = topics.expandPath(ss)) != null){
                          it.trees[m] = (new saveTree1(topics, jn)).curr;
                          continue topicscan;
                        }
                       // in this case, continue scan to see if further change necessary (for moved topic);
                     }
                   }
                 }
               }
             }
             // drop thru - message if required
               if (!simplescan && topics.expandPath(ss) == null) {
                invalidwork = true;
                if(v4message){
                  String top = ss.substring(ss.lastIndexOf(topicTree.CSEPARATOR) + 1);
                  ++v4errtot;
                  u.okmess(u.gettext("stucorruptt", "heading"),
                           u.edit(u.gettext("stucorruptt", "message"),
                                  new String[] {name, spname, String.valueOf(k), top}), sharkStartFrame.mainFrame);
                }
              }
           }
           else if(!jj.isLeaf()){
             it.trees[m] = (new saveTree1(topics,jj)).curr;
             changed = true;
           }
         }
       } // end of topicscan
       gamescan:for (m = 0; m < it.games.length; ++m) {
         String ss = it.games[m];
         if (ss.indexOf(topicTree.CSEPARATOR) >= 0) {
           ss = ss.substring(ss.lastIndexOf(topicTree.CSEPARATOR) + 1);
           it.games[m] = ss;
           changed = true;
         }
         if(ss.equals("publicgames") || ss.equals("public game trees")) {
           it.games = u.removeString(it.games,m);
           --m;
           changed=true;
           continue gamescan;
         }
         for (n = 0; n < gamech.length; n += 2) {
           if (ss.equals(gamech[n])) {
             ss = gamech[n + 1];
             it.games[m] = ss;
             changed = true;
             break;
           }
         }
//         if (!simplescan && games.findNode(ss) == null) {
//           invalidwork = true;
//           if(v4message){
//             u.okmess(u.gettext("stucorruptg", "heading"),
//                      u.edit(u.gettext("stucorruptg", "message"),
//                             new String[] {name, spname, String.valueOf(k), ss}), sharkStartFrame.mainFrame);
//             ++v4errtot;
//           }
//         }
       } // end of gamescan
     }   // end of scanning steps
//startPR2008-09-21^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     if(simplescan){
       p.version = null;
       db.update(name, spname, p, db.PROGRAM);
     }
     else{
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2008-09-21^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      if (isfromnewerversion && invalidwork)      //2222
//        programOverride = true;             //2222
//      if (changed && !programOverride) {    //2222

     boolean blockupdate = false;        //3333
     if(isfromnewerversion&&invalidwork){       //3333
       if (programOverride == null) programOverride = new String[] {spname};    //3333
       else programOverride = u.addString(programOverride, spname);   //3333
       blockupdate = true;    //3333
     }     //3333
     if (changed && !blockupdate) {     //3333
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2008-09-21^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//     db.update(name, pl[j], p, db.PROGRAM);
         p.version = null;
        db.update(name, spname, p, db.PROGRAM);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     }




//startPR2008-09-21^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    }
    return p;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   }
   

   public program.saveprogram fixsaveprogram5(
       boolean simplescan,
       topicTree topics,
       gamestoplay games,
       String spname,
       program.saveprogram p,
       String coursech[],
       ArrayList headingch,
       ArrayList headingmultch,
       String topicch[],
       String gamech[],
       ArrayList gameadds,
       String movech[],
       String currAdminListText,
       String defaultcourse) {
       boolean changed;
       boolean invalidwork = false;
       changed = false;
       int k, m, n, q;
       program.programitem it;

     for (k = 0; k < p.it.length; ++k) {
       it = p.it[k];
       // if student has been asssigned all word lists from v4 - give the
       // mixed list of first course in the topic tree.
       if(it.topics.length==0){
         jnode node = topics.getNode(defaultcourse);
         it.topics = u.addString(it.topics,topics.savePath(node));
         saveTree1.saveTree2 nt[] = new saveTree1.saveTree2[it.trees.length + 1];
         System.arraycopy(it.trees, 0, nt, 0, it.trees.length);
         it.trees = nt;
         it.trees[it.trees.length - 1] = (new saveTree1(topics, node)).curr;
         changed = true;
       }
       topicscan:for (m = 0; m < it.topics.length; ++m) {
         String ss = it.topics[m];
//          boolean oldadminlist = false;
//          for(int y = 0; y < oldAdminListText.length; y++){
//            if(ss.startsWith(oldAdminListText[y]+topicTree.CSEPARATOR)){
//              oldadminlist=true;
//              break;
//            }
//          }
//          if (ss.indexOf(topicTree.ISTOPIC) < 0 || oldadminlist) { // check only public topics
          if (ss.indexOf(topicTree.ISTOPIC) < 0) { // check only public topics
           jnode jj;
           if ((jj=(jnode)topics.expandPath(ss)) == null) {
             // adjust the path to assigned admin lists.
             // substistute the new name for the parent node to
             // the administrator lists
//             if(oldadminlist){
//               String adminname = null;
//               String sstemp = ss;
//               int i1,i2;
//               i1 = sstemp.indexOf(topicTree.ISTOPIC);
//               i2 = sstemp.indexOf(topicTree.ISPATH);
//               if(i1+1<i2)
//                 adminname = sstemp.substring(i1+1,i2);
//               sstemp = sstemp.substring(i1);
//               if(adminname!=null){
//                 sstemp = currAdminListText + topicTree.CSEPARATOR + adminname + topicTree.CSEPARATOR +sstemp;
//                 ss = it.topics[m] = sstemp;
//                 changed = true;
//                 jnode jn;
//                 if ((jn = topics.expandPath(ss)) != null){
//                   it.trees[m] = (new saveTree1(topics, jn)).curr;
//                   continue topicscan;
//                 }
//               }
//             }

             // Moves
             for (n = 0; n < movech.length; n += 2) {
               String pathpart = u.replaceEvery(movech[n], "^^", String.valueOf(topicTree.CSEPARATOR));
               if (ss.equals(pathpart)) {
                 ss = it.topics[m] = u.replaceEvery(movech[n+1], "^^", String.valueOf(topicTree.CSEPARATOR));
                 changed = true;
                  jnode jn;
                  if ((jn = topics.expandPath(ss)) != null){
                    it.trees[m] = (new saveTree1(topics, jn)).curr;
                    continue topicscan;
                  }
                 break;
               }
             }


             String ss2[] = u.splitString(ss, topicTree.CSEPARATOR);

             // Multiple headings

               for (n = 0; n < headingmultch.size(); n ++) {
                   String heading[]= (String[])headingmultch.get(n);
                     if (ss2[0].equals(heading[0])) {
                        for (n = 1; n < heading.length; n += 2) {
                            String pathpart = u.replaceEvery(heading[n], "^^", String.valueOf(topicTree.CSEPARATOR));
                            int j;
                            if((j=ss.indexOf(pathpart))>=0){
                                String replacestr = u.replaceEvery(heading[n+1], "^^", String.valueOf(topicTree.CSEPARATOR));
                                ss = it.topics[m] = ss.substring(0, j) + replacestr + ss.substring(j+pathpart.length());
                                ss2 = u.splitString(ss, topicTree.CSEPARATOR);
                                changed = true;
                                jnode jn;
                                if ((jn = topics.expandPath(ss)) != null){
                                    it.trees[m] = (new saveTree1(topics, jn)).curr;
                                    continue topicscan;
                                }
                            }
                       }
                   }
               }


             // Courses
             for (n = 0; n < coursech.length; n += 2) {
               if (ss2[0].equals(coursech[n])) {
                 ss2[0] = coursech[n + 1];
                 ss = it.topics[m] = u.combineString(ss2, new String(new char[] {topicTree.CSEPARATOR}));
                 changed = true;
                  jnode jn;
                  if ((jn = topics.expandPath(ss)) != null){
                    it.trees[m] = (new saveTree1(topics, jn)).curr;
                    continue topicscan;
                  }
                 break;
               }
             }



             int last = ss2.length - 1;
             // Word lists
             if (last > 0) {
               for (n = 0; n < topicch.length; n += 2) {
                   if (ss2[last].equals(topicch[n])) {
                   ss2[last] = topicch[n + 1];
                   ss = it.topics[m] = u.combineString(ss2, new String(new char[] {topicTree.CSEPARATOR}));
                   changed = true;
                    jnode jn;
                    if ((jn = topics.expandPath(ss)) != null){
                      it.trees[m] = (new saveTree1(topics, jn)).curr;
                      continue topicscan;
                    }
                   break;
                 }
               }
               String origss2[] = new String[ss2.length];
               System.arraycopy(ss2,0,origss2,0,ss2.length);


               // Red headings
               for (n = 0; n < headingch.size(); n ++) {
                   String heading[]= (String[])headingch.get(n);
                     if (ss2[0].equals(heading[0])) {
                        for (n = 1; n < heading.length; n += 2) {
                            for (q = 1; q < ss2.length; ++q) {
                                 if (origss2[q].equals(heading[n])) {
                             //      ss2[q] = heading[n + 1];
                                   ss2[q] = u.replaceEvery(heading[n + 1], "^^", String.valueOf(topicTree.CSEPARATOR));
                                   ss = it.topics[m] = u.combineString(ss2, new String(new char[] {topicTree.CSEPARATOR}));
                                   changed = true;
                                    jnode jn;
                                    if ((jn = topics.expandPath(ss)) != null){
                                      it.trees[m] = (new saveTree1(topics, jn)).curr;
                                      continue topicscan;
                                    }
                                 }
                          }
                       }
                     }
               }
             }
             // drop thru - message if required
//startPR2008-09-21^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//            if (topics.expandPath(ss) == null) {
               if (!simplescan && topics.expandPath(ss) == null) {
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                invalidwork = true;
                if(v4message){
                    String top = ss.substring(ss.lastIndexOf(topicTree.CSEPARATOR) + 1);
                  ++v4errtot;
                  u.okmess(u.gettext("stucorruptt", "heading"),
                           u.edit(u.gettext("stucorruptt", "message"),
                                  new String[] {name, spname, String.valueOf(k), top}), sharkStartFrame.mainFrame);
                }
              }
           }
           else if(!jj.isLeaf()){
             it.trees[m] = (new saveTree1(topics,jj)).curr;
             changed = true;
           }
         }
       } // end of topicscan
       gamescan:for (m = 0; m < it.games.length; ++m) {
         String ss = it.games[m];
         if (ss.indexOf(topicTree.CSEPARATOR) >= 0) {
           ss = ss.substring(ss.lastIndexOf(topicTree.CSEPARATOR) + 1);
           it.games[m] = ss;
           changed = true;
         }
         if(ss.equals("publicgames") || ss.equals("public game trees")) {
           it.games = u.removeString(it.games,m);
           --m;
           changed=true;
           continue gamescan;
         }
         for (n = 0; n < gameadds.size(); n ++) {
            String gameslist[]= (String[])gameadds.get(n);
            if (ss.equals(gameslist[0])) {
                for (n = 1; n < gameslist.length; n ++) {
                    String addedgame = gameslist[n];
                    if(u.findString(it.games, addedgame) < 0){
                        it.games = u.addString(it.games, addedgame);
                    }
                    changed = true;
                }
            }
         }
         for (n = 0; n < gamech.length; n += 2) {
           if (ss.equals(gamech[n])) {
             ss = gamech[n + 1];
             if(u.findString(it.games, ss) < 0){
                 it.games = u.addString(it.games, ss);
             }
             changed = true;
             break;
           }
         }
         if (!simplescan && games.findNode(ss) == null) {
           invalidwork = true;
           if(v4message){
             u.okmess(u.gettext("stucorruptg", "heading"),
                      u.edit(u.gettext("stucorruptg", "message"),
                             new String[] {name, spname, String.valueOf(k), ss}), sharkStartFrame.mainFrame);
             ++v4errtot;
           }
         }
       } // end of gamescan
     }   // end of scanning steps
//startPR2008-09-21^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     if(simplescan){
       p.version = shark.versionNoDetailed;
       db.update(name, spname, p, db.PROGRAM);
     }
     else{
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2008-09-21^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      if (isfromnewerversion && invalidwork)      //2222
//        programOverride = true;             //2222
//      if (changed && !programOverride) {    //2222

     boolean blockupdate = false;        //3333
     if(isfromnewerversion&&invalidwork){       //3333
       if (programOverride == null) programOverride = new String[] {spname};    //3333
       else programOverride = u.addString(programOverride, spname);   //3333
       blockupdate = true;    //3333
     }     //3333
     if (changed && !blockupdate) {     //3333
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2008-09-21^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//     db.update(name, pl[j], p, db.PROGRAM);
         p.version = shark.versionNoDetailed;
        db.update(name, spname, p, db.PROGRAM);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     }




//startPR2008-09-21^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    }
    return p;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   }    



   static class verifyPassword
     extends JDialog {
     JPanel panel1=new JPanel();
     JPanel panel2=new JPanel();
     GridBagConstraints grid1 = new GridBagConstraints();
     JLabel labwrong = new JLabel();
     JPasswordField pass = new JPasswordField();

     boolean gotname;
     JDialog thisd;
     public boolean passwordok;
     String userpass;

     verifyPassword(student stu) {
        super(sharkStartFrame.mainFrame);
        if(stu.password==null)return;
        thisd = this;
        setResizable(false);
        setModal(true);
        userpass = student.decrypt(stu.password) ;

        passwordok = false;
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        int width = sharkStartFrame.screenSize.width*4/9;
        int height = sharkStartFrame.screenSize.height/6;
//      setBounds(new Rectangle((sharkStartFrame.screenSize.width-width)/2,
//                              (sharkStartFrame.screenSize.height-height)/2,
//                              width,height
//                              ));
    setBounds(u2_base.adjustBounds(new Rectangle((sharkStartFrame.screenSize.width-width)/2,
                              (sharkStartFrame.screenSize.height-height)/2,
                              width,height)));
      setTitle(u.gettext("verifypassword","title"));
      getContentPane().setLayout(new GridBagLayout());
      panel2.setLayout(new GridBagLayout());
      JLabel lab1 = new JLabel();
      lab1.setText(u.gettext("verifypassword","pass"));
        labwrong.setText(u.gettext("verifypassword","incorrect"));
        labwrong.setForeground(thisd.getContentPane().getBackground());
     JButton ok = new JButton(u.gettext("ok", "label"));
     JButton cancelbut = new JButton( u.gettext("cancel", "label"));
      ok.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               if(userpass.equalsIgnoreCase(String.valueOf(pass.getPassword()))){
                   passwordok = true;
                   thisd.dispose();
               }
                else{
                   labwrong.setForeground(Color.red);
                   labwrong.repaint();
                }
          }
      });

      cancelbut.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
              thisd.dispose();
           }
      });
      grid1.gridx = -1;
      grid1.gridy = 0;
      grid1.weightx = 1;
      grid1.weighty = 0;
      grid1.fill = GridBagConstraints.NONE;
      if(shark.macOS){
        panel2.add(cancelbut, grid1);
        panel2.add(ok, grid1);
      }
      else{
        panel2.add(ok, grid1);
        panel2.add(cancelbut, grid1);
      }
      grid1.gridx = 0;
      grid1.gridy = -1;
      grid1.weightx = 1;
      grid1.weighty = 0;

grid1.fill = GridBagConstraints.NONE;

  //    grid1.fill = GridBagConstraints.VERTICAL;
      grid1.gridx = 0;
      grid1.gridy = -1;
      grid1.weightx = 1;
      grid1.weighty = 1;
     getContentPane().add(lab1,grid1);
     getContentPane().add(pass,grid1);
     grid1.weighty = 0;
      getContentPane().add(labwrong,grid1);
      grid1.weighty = 1;
      grid1.fill = GridBagConstraints.HORIZONTAL;
      getContentPane().add(panel2,grid1);
     

      pass.setColumns(20);
      pass.setMinimumSize(pass.getPreferredSize());
      pass.requestFocus();

      
      panel2.getRootPane().setDefaultButton(ok);

      validate();
      setVisible(true);



   }
    }





        // scan and fix projects (set work) for student
   public void fixv4() {
       // change own word lists to the new format
     u.updateUser(name);
     String pl[];
//startPR2008-09-21^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     if ((pl = db.list(name, db.PROGRAM)).length >  0) {
//      if (((pl = db.list(name, db.PROGRAM)).length >  0)&&!programOverride) {
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2008-09-21^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//       int i, j, k, m, n, q,ii;
        int j;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       program.saveprogram p;
//startPR2008-09-21^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//       program.programitem it;
//       boolean changed;
//       String courses[] = sharkStartFrame.mainFrame.publicCourses;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       boolean setup = topics4==null;
       String savecourses[] = sharkStartFrame.courses;
       if(setup){
           sharkStartFrame.courses = new String[0]; // get full topic tree
           topics4 = new topicTree();
           topics4.onlyOneDatabase = "*";
           topics4.root.setIcon(jnode.ROOTTOPICTREE);
           topics4.publicname = sharkStartFrame.publicV4TopicsLib;
           topics4.isAbsoluteDb = new File(sharkStartFrame.publicV4TopicsLib).isAbsolute();
           topics4.setup(new String[]{sharkStartFrame.publicV4TopicsLib}, false, db.TOPICTREE, true, "topic tree");
           topics4.setRootVisible(false);
           topics5 = new topicTree();
           topics5.onlyOneDatabase = "*";
           topics5.root.setIcon(jnode.ROOTTOPICTREE);
           topics5.setup(sharkStartFrame.mainFrame.publicTopicLib, false, db.TOPICTREE, true, "topic tree");
           topics5.setRootVisible(false);
           sharkStartFrame.courses = savecourses; // restore course removals
           games5 = new gamestoplay();
           games5.setup(sharkStartFrame.publicGameLib,
                       true, true, "games", gamestoplay.categories);
           gamech = u.splitString(u.gettext("v4changes", "games"));
           gamech5 = u.splitString(u.gettext("v5changes", "games"));
           String gs = u.gettext("v5changes", "gamesadd");
           String ga5[] = u.splitString(gs, "||");
           gamechadd5 = new ArrayList();
           for(int n = 0; n < ga5.length; n++){
               gamechadd5.add(u.splitString(ga5[n]));
           }
           coursech = u.splitString(u.gettext("v4changes", "courses"));
           coursech5 = u.splitString(u.gettext("v5changes", "courses"));
           topicch = u.splitString(u.gettext("v4changes", "topics"));
           topicch5 = u.splitString(u.gettext("v5changes", "topics"));
           movech5 = u.splitString(u.gettext("v5changes", "move"));
           headingch = u.splitString(u.gettext("v4changes", "headings"));
           String hs = u.gettext("v5changes", "headings");
           String ha5[] = u.splitString(hs, "||");
           headingch5 = new ArrayList();
           for(int n = 0; n < ha5.length; n++){
               headingch5.add(u.splitString(ha5[n]));
           }
           hs = u.gettext("v5changes", "headings_mult");
           ha5 = u.splitString(hs, "||");
           headingmultch5 = new ArrayList();
           for(int n = 0; n < ha5.length; n++){
               headingmultch5.add(u.splitString(ha5[n]));
           }
           defaultcourse = (db.listsort(u.absoluteToRelative(sharkStartFrame.publicV4TopicsLib), db.TOPICTREE))[0];
           defaultcourse5 = (db.listsort(u.absoluteToRelative(sharkStartFrame.publicTopicLib[0]), db.TOPICTREE))[0];
           currAdminListText = u.gettext("v4changes","adminlists");
           currAdminListText5 = u.gettext("topics","adminlists");
           // a list of texts in previous versions for the node that is the parent to
           // the administrator lists.
           oldAdminListText = u.splitString(u.gettext("v4changes", "adminlisttext"));           
           
       }


//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2008-09-21^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//       boolean invalidwork = false;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       projectscan:for (j = 0; j < pl.length; ++j) {
         try {                                                    // start rb 6/2/08
           p = (program.saveprogram) db.find(name, pl[j], db.PROGRAM);
         }
         catch (ClassCastException e) {
            db.delete(name, pl[j], db.PROGRAM);   // remove faulty program
            if (v4message) {                             //  start rb 6/2/08 v4.2
              ++v4errtot;
              u.okmess(u.gettext("stucorruptp", "heading"),
                       u.edit(u.gettext("stucorruptp", "message"),
                              new String[] {name, pl[j]}), sharkStartFrame.mainFrame);
            }
            continue;                                    //  end rb 6/2/08 v4.2
         }                                                          // end rb 6/2/08
         if(p == null || p.it == null) {
             db.delete(name, pl[j], db.PROGRAM);   //  start rb 6/2/08 v4.2    remove faulty program
             if (v4message) {                             // start
               ++v4errtot;
               u.okmess(u.gettext("stucorruptp", "heading"),
                        u.edit(u.gettext("stucorruptp", "message"),
                               new String[] {name, pl[j]}), sharkStartFrame.mainFrame);
             }                                 //  end rb 6/2/08 v4.2
             continue;
         }
//startPR2008-09-21^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//         changed = false;
//          p = fixsaveprogram4(false, topics, games,
         if((p.version==null || p.version.trim().equals("")) || p.version.substring(0, 1).equals("3"))
            p = fixsaveprogram4(false, topics4,
                             pl[j], p, coursech, headingch, topicch, gamech,
                             oldAdminListText, currAdminListText, defaultcourse);
         if((p.version==null || p.version.trim().equals("")) || p.version.substring(0, 1).equals("4"))
            p = fixsaveprogram5(false, topics5, games5,
                             pl[j], p, coursech5, headingch5, headingmultch5, topicch5, gamech5, gamechadd5, movech5,
                             currAdminListText5, defaultcourse5);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       }     // end of scanning projects
     }
   }


   //-------------------------------------------------------------------------------
        // scan and fix all students and administrators
   public static synchronized void fixstudents() {
     FileOutputStream corrupt = null;
     int i,j,k,m,n;
     String name;
     String s[] = db.dblistnames(sharkStartFrame.sharedPath);
     String corruptname = u.gettext("stucorrupt","corruptname") + ".txt";
     v4errtot = 0;
     studentscan: for(i=0;i<s.length;++i) {
          student st;
          v4message=true;
          st = student.getStudent(name = s[i]);
          v4message=true;
          if(st == null) {
            st = new student();
            st.name = s[i];
            st.saveStudent(s[i]);
            u.okmess(u.gettext("stucorrupt", "heading"),
                     u.gettext("stucorrupt", "message", s[i]),sharkStartFrame.mainFrame);
            ++v4errtot;
            v4message=true;                                    // rb 6/2/08 v4.2 check the projects
            st.fixv4();                                        // rb 6/2/08 v4.2
            v4message=true;                                    // rb 6/2/08 v4.2
            continue studentscan;
          }
          if(st.which  == 'N') {
           db.destroy(name);                                   // rb 6/2/08 v4.2 ensure completely fresh
           st = new student();
           st.name = s[i];
           st.saveStudent(s[i]);
           u.okmess(u.gettext("stucorruptws", "heading"),
                    u.edit(u.gettext("stucorruptws", "message"), s[i], shark.otherProgram)
                    ,sharkStartFrame.mainFrame);
           ++v4errtot;
           continue studentscan;
         }
    }
     if(v4errtot > 0) {
       u.okmess("stuiscorrupt");
     }
     else {
        u.okmess("stucorruptok",sharkStartFrame.mainFrame);
     }
   }
   /*
   public static class specs extends JDialog  {
     mlabel_base deleteafter = u.mlabel_base("stu_deleteafter");
     JSpinner spin;

     JPanel jspin = new JPanel(new GridBagLayout());
     JPanel jdel = new JPanel(new GridBagLayout());
     JCheckBox autodelete = u.CheckBox("stu_autodelete");
     mbutton archive = u.mbutton("stu_archive");
     mlabel_base lab = new mlabel_base(" ");
     mlabel_base warnlab = u.mlabel_base("stu_recwarning");
     specs() {
       String months = (String)db.find(sharkStartFrame.optionsdb,"keeprecordsfor_", db.TEXT);
       int monthval = months==null ? 15 : Integer.parseInt(months);
       spin =  new JSpinner(new SpinnerNumberModel(monthval,0,120,1));
       warnlab.setText(u.edit(warnlab.getText(),sharkStartFrame.sharedPathplus + u.gettext("sturec_", "folder")));
       autodelete.setSelected(db.query(sharkStartFrame.optionsdb,"keepallrecords_",db.TEXT) < 0);
       autodelete.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            if(autodelete.isSelected()) db.delete(sharkStartFrame.optionsdb,"keepallrecords_",db.TEXT);
            else db.update(sharkStartFrame.optionsdb,"keepallrecords_","",db.TEXT);
            jspin.setVisible(autodelete.isSelected());
            requestFocus();
          }

       });
       spin.addChangeListener(new ChangeListener() {
         public void stateChanged(ChangeEvent e) {
             int val = ((SpinnerNumberModel)(spin.getModel())).getNumber().intValue();
             db.update(sharkStartFrame.optionsdb,"keeprecordsfor_",String.valueOf(val), db.TEXT);
         }
       });
       
       archive.addActionListener(new ActionListener() {
          String start;
          public void actionPerformed(ActionEvent e) {
            if(!u.yesnomess("stu_archiveq")) return;
            int i, j;
            String archiving = u.gettext("sturec_","archiving");
            File f1 = new File(sharkStartFrame.sharedPath, u.gettext("sturec_", "folder"));
            if (!f1.exists())
              f1.mkdir();
            for (i = 0; i < alllist.length; ++i) {
              student stu = findStudent(alllist[i]);
              if (stu != null) {
                lab.setText(u.edit(archiving, stu.name));
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
                    return file.getName().startsWith(start) && file.getName().endsWith(recend);
                  }
                });
                if (list.length > 0) {
                  topicPlayed gp = getfirst(list[0]);
                  if (gp != null) {
                    first = gp.date; firstgp = gp; }
                  if (last == null) {
                    gp = getfirst(list[list.length - 1]);
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
                  for (j = 0; j < list.length; ++j) {
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
            lab.setText(u.gettext("sturec_", "archivedone"));
          }
       });
       this.getContentPane().setLayout(new GridBagLayout());
       GridBagConstraints grid = new GridBagConstraints();
       grid.fill = GridBagConstraints.NONE;
       grid.gridheight = 1;
       grid.weighty = 1;
       grid.weightx = 1;

       grid.gridx = -1;
       grid.gridy = 0;
       jspin.add(deleteafter,grid);
       jspin.add(spin,grid);
       jspin.setVisible(autodelete.isSelected());

       grid.gridx = 0;
       grid.gridy = -1;
       this.setTitle(u.gettext("stu_archive","heading"));
       jdel.add(autodelete,grid);
       jdel.add(jspin,grid);
       jdel.setBorder(BorderFactory.createEtchedBorder(Color.red,Color.pink));
       this.getContentPane().add(jdel,grid);
       this.getContentPane().add(archive,grid);
       this.getContentPane().add(lab,grid);
       this.getContentPane().add(warnlab,grid);
       this.setEnabled(true);
       this.setVisible(true);
       this.setResizable(false);
       this.setBounds(new Rectangle(sharkStartFrame.mainFrame.getBounds().width/8,
                                    sharkStartFrame.mainFrame.getBounds().height/4,
                                    sharkStartFrame.mainFrame.getBounds().width*3/4,
                                    sharkStartFrame.mainFrame.getBounds().height/2));
       this.validate();
     }
     //--------------------------------------------------------------------------------------------
        // returns first record for month
     

   }
   */
    public static topicPlayed getfirst(File f) {
       try{
         FileInputStream fis = new FileInputStream(f);
         ObjectInputStream ois = new ObjectInputStream(fis);
         topicPlayed tt[] = (topicPlayed[])ois.readObject();
         ois.close();
         fis.close();
         if(tt.length>0) return tt[0];
       }
       catch(OptionalDataException e){}
       catch(IOException e){
         return null;
       }
       catch(ClassNotFoundException e) {
          return null;
       }
       return null;
     }   
  //-------------------------------------------------------------
  public static class showStudentRecord
//startPR2004-08-11^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    extends JDialog
      implements Printable {
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//   String delqueue[][]=new String[0][];   // rb 27/10/06     queue of dates/times of sessions to delete
   GridBagLayout layout1 = new GridBagLayout();
   GridBagLayout layout2 = new GridBagLayout();
   GridBagConstraints grid = new GridBagConstraints();
   JLabel lab1 = new JLabel();
   mlabel_base lab2 = new mlabel_base(" ");
   JPanel panel1= new JPanel(),panel2= new JPanel();
   JButton printbutton, xmlbutton, xmlbutton2, unisettings;
   JButton deletebutton = u.Button("sturec_delete");
   JButton deleteallbutton = u.Button("sturec_deleteall");
   JButton undobutton = u.Button("sturec_undodelete");
   JButton exitbutton = u.Button("sturec_exit");
   JList sessions = new JList();
   topicPlayed undo[];
   playeditemlist items = new playeditemlist();
   String stulist[];
   Vector vv,v;
   int sessionsel[];
   int i;
//startPR2004-08-11^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   JDialog thisframe = this;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   student stu;
   boolean multi;
   String start;
   String belongto;
//startPR2004-10-05^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   /**
    * The total number for pages required to print the requested information.
    */
   int totPages = 0;
   /**
    * When this variable is not equal to the 'a' variable in the print method,
    * the print method is being called for an image buffer's graphics context
    * rather than an actual page's graphics context.
    */
   int lastPage = -1;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       // start rb 23/10/06
//   JCheckBox allrecords = u.CheckBox("stu_allrecords");
   String signedon = sharkStartFrame.studentList[sharkStartFrame.currStudent].name;
   String wantworkfor = signedon;
//   JLabel showworkfor;
   boolean cntl;
   boolean otherWork = false;
//   boolean allwork;
//   JRadioButton rbAllWork;
//   JRadioButton rbCurrWork;
//   ButtonGroup bg = new ButtonGroup();
//   ItemListener bglisten = new java.awt.event.ItemListener() {
//      public void itemStateChanged(ItemEvent e) {
//        if(!multi) {
//            addSessions();
//            j = sessions.getModel().getSize();
//            if(j>0) {
//                --j;
//                sessions.setSelectedIndex(j);
//                sessions.ensureIndexIsVisible(j);
//            }
//            else  sessions.clearSelection();
//        } 
//      }
//   };
   // end rb 23/10/06
   Font plainfont = sharkStartFrame.treefont.deriveFont(Font.PLAIN);
   Font smallerplainfont = plainfont.deriveFont((float)plainfont.getSize()-1);
   Font evensmallerplainfont = plainfont.deriveFont((float)plainfont.getSize()-2);
   Font bigfont = sharkStartFrame.treefont.deriveFont((float)sharkStartFrame.treefont.getSize()+4);
   int sw = sharkStartFrame.mainFrame.getWidth();
   int sh = sharkStartFrame.mainFrame.getHeight();
   int buttondim = (sw*14/22)/24;
   int buttonimdim =  buttondim- (buttondim/5);
   showStudentRecord  ssr = this;
   JLabel lbFilterGame = new JLabel();
   JLabel lbFilterList = new JLabel();
   String filterGame;
   String filterList;
   Calendar calendarLimit;
   String period_strings[] = u.splitString(u.gettext("records", "periods"));
   String period_ints[] = u.splitString(u.gettext("records","periods_int"));
   String prefix = u.gettext("records", "workprefix") + " ";
   String strAll = u.gettext("all", "label");
   JComboBox cbWork;
   JComboBox cbPeriod;
   String worklast;
   String periodlast;
   String wordlistlast;
   String gamelast;
   String strAllLists =  u.gettext("records", "alllists");
   String strAllGames = u.gettext("records", "allgames");
   JComboBox cbWordLists;
   JComboBox cbGames;
   int b1 = 5;
   int b2 = 10;
   String chosenLists[] = new String[]{};
   String chosenGames[] = new String[]{};
   boolean stuselected[];
   String strnosessions = u.gettext("sturec_","nosessions");
   String strnosessions_cr = u.gettext("sturec_","nosessions_cr");
   JRadioButton rbAll;
   JRadioButton rbYou;
   boolean lastradioval;
   String strsturecfolder = u.gettext("sturec_", "folder");
   String strsturecfile = u.gettext("sturec_", "file");
   JPanel pnSessionList;
   JPanel pnSessionNone;
   JButton btReset;
   String okTopicNames[];
   JButton jbGames, jbLists;
   Color headingColor = UIManager.getDefaults().getColor("List.selectionBackground");
   int scrollBarWidth = ((Integer)UIManager.get("ScrollBar.width")).intValue();
        int lastheading = 0;
        int headingcount = 0;
   int userperiod;


//startPR2007-03-06^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//   showStudentRecord(String stulist1[],String belongto1) {
   showStudentRecord(JDialog jd, String stulist1[],String belongto1) {
     super(jd);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      stulist=stulist1;
      stuselected = new boolean[stulist.length];
      belongto = belongto1;
      setsessions();
      sessions.setCellRenderer(new itempainter());
      lab1.setText(u.gettext("sturec_","h1multi"));
      multi = true;
      this.setTitle(u.gettext("sturec_","titlemulti",belongto));
//      printbutton = u.mbutton("sturec_printmulti");
//      xmlbutton = u.mbutton("sturec_xmlmulti");
//      xmlbutton.setText(u.edit(xmlbutton.getText(),belongto));
//      xmlbutton2 = u.mbutton("sturec_xmlmulti2");
      init2();
      setup();
      javax.swing.Timer testTimer = new javax.swing.Timer(10000,new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            setsessions();
         }
      });
      testTimer.setRepeats(true);
      testTimer.start();
   }

//startPR2007-03-06^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//   showStudentRecord(student stu1) {
   showStudentRecord(JDialog jd, student stu1) {
     super(jd);
     stu = stu1;
     init2();
     init();
   }
   showStudentRecord(JFrame jf, student stu1) {
     super(jf);
     stu = stu1;
     init2();
     init();
   }
   void init2(){
      userperiod = optionval("period_speed");
      if(userperiod<0)userperiod=2;
     Image im = Toolkit.getDefaultToolkit().getImage(sharkStartFrame.publicPathplus + "sprites" +
                                       sharkStartFrame.separator +
                                       "print_il48.png");
     ImageIcon iiinfo = new ImageIcon(im.getScaledInstance(buttonimdim,
            buttonimdim, Image.SCALE_SMOOTH));
     printbutton = u.sharkButton();
     printbutton.setToolTipText(u.gettext("sturec_tooltips", "print"));
     printbutton.setPreferredSize(new Dimension(buttondim, buttondim));
     printbutton.setMinimumSize(new Dimension(buttondim, buttondim)); 
     printbutton.setIcon(iiinfo);
     xmlbutton = u.sharkButton();
     xmlbutton.setToolTipText(u.gettext("sturec_tooltips", "export"));
     xmlbutton.setPreferredSize(new Dimension(buttondim, buttondim));
     xmlbutton.setMinimumSize(new Dimension(buttondim, buttondim));
     im = Toolkit.getDefaultToolkit().getImage(sharkStartFrame.publicPathplus + "sprites" +
                                       sharkStartFrame.separator +
                                       "export_il48.png");
     iiinfo = new ImageIcon(im.getScaledInstance(buttonimdim,
            buttonimdim, Image.SCALE_SMOOTH));
     xmlbutton.setIcon(iiinfo);
     xmlbutton2 = u.sharkButton();
     xmlbutton2.setToolTipText(u.gettext("sturec_tooltips", "export"));
     xmlbutton2.setPreferredSize(new Dimension(buttondim, buttondim));
     xmlbutton2.setMinimumSize(new Dimension(buttondim, buttondim));
     xmlbutton2.setIcon(iiinfo);
     unisettings = u.sharkButton();
     unisettings.setToolTipText(u.gettext("sturec_tooltips", "settings"));
     unisettings.setPreferredSize(new Dimension(buttondim, buttondim));
     unisettings.setMinimumSize(new Dimension(buttondim, buttondim));
     im = Toolkit.getDefaultToolkit().getImage(sharkStartFrame.publicPathplus + "sprites" +
                                       sharkStartFrame.separator +
                                       "type_il48.png");
     iiinfo = new ImageIcon(im.getScaledInstance(buttonimdim,
            buttonimdim, Image.SCALE_SMOOTH));
     unisettings.setIcon(iiinfo);
   }
   void init(){
//    stu = stu1;
       sessions.setCellRenderer(new itempainter2());
//startPR2008-07-16^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    // set blank menu bar for Macs
    if(shark.macOS){
      sharkStartFrame.mainFrame.setJMenuBar(new javax.swing.JMenuBar());
    }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      // start rb 23/10/06
    wantworkfor=null;
    if(stu.name.equalsIgnoreCase(signedon)) {
       stu.doupdates(true,false);   
    }
    stu.addarchive();
    /*
       if(stu.workforteacher != null) {
         for (i = stu.studentrecord.length-1; i >=0; --i) {
            if(stu.studentrecord[i].workforteacher == null || stu.studentrecord[i].workforteacher.trim().equals(""))
                otherWork = true;
            else if(stu.studentrecord[i].workforteacher.equalsIgnoreCase(stu.workforteacher)) {
                wantworkfor = stu.workforteacher;
            }
            else
                otherWork = true;
         }
       }
    }
    else {
      stu.doupdates(false,false);
      for (i = stu.studentrecord.length-1; i >=0; --i) {
         if(stu.studentrecord[i].workforteacher == null || stu.studentrecord[i].workforteacher.trim().equals(""))
          otherWork = true;
         else if(stu.studentrecord[i].workforteacher.equalsIgnoreCase(signedon)) {
             wantworkfor = signedon;
         }
         else 
             otherWork = true;
      }
    }*/
          // end rb 23/10/06
    lab1.setText(u.gettext("sturec_","h1",stu.name));
    this.setTitle(u.gettext("sturec_","title",stu.name));
//startPR2004-07-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   
       // if running on a Macintosh
//       if (shark.macOS) {
//         printbutton = u.mbutton("sturec_print_mac");
//       }
       // if running on Windows
//       else {
//         printbutton = u.mbutton("sturec_print");
//       }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    xmlbutton = u.mbutton("sturec_xml");
    setup();
    /*
    this.addWindowListener(new java.awt.event.WindowAdapter() {  // start rb 27/10/06
      public void windowClosed(WindowEvent e) {
         if(delqueue.length >0) {
           if(stu.name.equalsIgnoreCase(signedon)) stu.saveStudent();
           else for(int i=0;i<delqueue.length;++i)
              student.queueupdate(new String[]{stu.name}, u.addString(new String[]{"delsturec"}, delqueue[i]));
         }
      }
    });*/
    addWindowListener(new java.awt.event.WindowAdapter() {  // start rb 27/10/06
      public void windowClosed(WindowEvent e) {
         int currsel = cbPeriod.getSelectedIndex();
         if(currsel != userperiod){
            userperiod = currsel;
            setOption("period_speed",currsel);
          }
      }
    });
   }
   //-------------------------------------------------------------------------------------------
   synchronized void setsessions() {    // set up list for multi
      int i, j=0,k;
      int sel = sessions.getSelectedIndex();
      int sels[] = null;
      savestudent rec;
      Vector vv2 = new Vector();
      if(vv == null) {
         vv = new Vector();
         for(i=0;i<stulist.length;++i) vv.add(stulist[i]);
      }
      topicPlayed it[];
      sessionsel = new int[]{};
      int sect = -1;
      sels = new int[]{sel};
      for(i=0;i<vv.size();++i) {
         if(vv.get(i) instanceof nameandrec && ((nameandrec)vv.get(i)).name==null ) {
            continue;
         }
         else {
           rec = (savestudent) db.find(stulist[j], "student", db.STUDENT);
           if (rec == null)       continue;
           it = rec.studentrecord;
           it = addarchive(stulist[j], it); 
//           if (sel == i) {
     //      if (stuselected[j]) {   
         //    if (sel == i) {
         //       sect = j;
                
         //    }
 //            sessionsel = vv2.size();
             for (k = it.length - 1; k >= 0; --k) {
                 if(!isTopicPlayedOK(it[k]))it = u.removeTopicPlayed(it, k);
             } 
             if (it.length > 0) {
                for (k = it.length - 1; k >= 0; --k) {
                 if (!it[k].date.equals(it[it.length - 1].date)) {
                  break;
                 }
                }
                ++k;
                if(stuselected[j] && sels!=null && !u.inlist(sels, vv2.size())) 
                    sels = u.addint(sels, vv2.size());
                vv2.add(stulist[j] + "       -      " + editsession(it[k]));
                int ii = vv2.size();
                for (;k< it.length;++k) {
                   if(stuselected[j] && sels!=null && !u.inlist(sels, vv2.size())) 
                       sels = u.addint(sels, vv2.size());
                   vv2.add(ii, new nameandrec(null, it[k],stulist[j]));
                }
             }
             else {
                 student user = student.findStudent(stulist[j]);
                 topicPlayed rtt[] = getLatestFileTPs(user);
                 if(rtt==null){
                    vv2.add(stulist[j] + "       -      " + strnosessions);
                 }
                 else{
                    if(sect == j && sels!=null && !u.inlist(sels, vv2.size())) {
                        sels = u.addint(sels, vv2.size());
                    }
                    vv2.add(stulist[j] + "       -      " + editsession(rtt[0]));
                    int ii = vv2.size();
                    for (int y = 0;y< rtt.length;++y) {
                        if(sect == j && sels!=null && !u.inlist(sels, vv2.size())) 
                            sels = u.addint(sels, vv2.size());
                        vv2.add(ii, new nameandrec(null, rtt[y],stulist[j]));
                    }                              
                 }                                          
             }
       //    }
      //     else  if(it.length > 0 && it[it.length-1].dateoff == null)
      //               vv2.add(new nameandrec(stulist[j],it[it.length-1]));  // add last for active session
      //     else vv2.add(stulist[j]);
           ++j;    // advance in student list
         }
      }
      sessions.setListData(vv=vv2);
  //    sessions.setSelectedIndex(sessionsel);
      if(sels!=null){
          sessionsel = sels;
          sessions.setSelectedIndices(sels);
      }
      boolean found = false;
      for(int n = 0; n < vv2.size(); n++){
          Object o = vv2.get(n);
          if(!(o instanceof String) || !((String)o).endsWith(strnosessions))found = true;
      }
      if(printbutton!=null)printbutton.setEnabled(found);
   }
   
   topicPlayed[] getLatestFileTPs(student stdnt){
                    // look in rec and arc files
                    File f = new File(sharkStartFrame.sharedPath, strsturecfolder);
                    if (f.exists()) {
                          start = strsturecfile;
                          start = u.edit(start.substring(0,5), stdnt.name, shark.USBprefix);
                          File list[] = f.listFiles(new FileFilter() {
                             public boolean accept(File file) {
                                return file.getName().startsWith
                                        (start);
                             }
                          });          
                          Vector v = null;
                          if(list!=null)v = addMonths2(list, stdnt, true);
                          // find most recent session;
                          Date mostrecent  = null;
                          int mostrecentint = -1;
                          for(int n = 0; v!=null && n < v.size(); n++){
                              showMonth sm = (showMonth)v.get(n);
                              topicPlayed tt[] = gettp(sm.f);
                              if(tt != null && tt[0] != null) 
                                  if(mostrecent==null || tt[0].date.before(mostrecent)){
                                      mostrecent = tt[0].date;
                                      mostrecentint = n;
                                  }
                          }
                          topicPlayed rtt[] = null;
                          if(mostrecentint>=0){
                              showMonth sm = (showMonth)v.get(mostrecentint);
                              topicPlayed tt[] = getalltp(sm.f);
                              for(int p = 0; p < tt.length; p++){
                                  if (tt[0].date.equals(tt[p].date)) {
                                      if(rtt==null)rtt = new topicPlayed[]{tt[p]};
                                      else rtt = u.addTopicPlayed(rtt, tt[p]);
                                  } 
                                  else 
                                      break;
                              }                      
                          }
                          return rtt;                          
                    }  
       return null;
   }
   
   
   void setup() {
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
//startPR2004-07-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       // if running on a Macintosh
       if (shark.macOS) {
         lab2.setText(u.gettext("sturec_", "h2_mac"));
       }
       // if running on Windows
       else {
         lab2.setText(u.gettext("sturec_", "h2"));
       }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    this.setEnabled(true);
    this.setVisible(true);
    this.setResizable(false);
    this.setBounds(sharkStartFrame.mainFrame.getBounds());
//startPR2009-08-19^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    this.addComponentListener(new ComponentAdapter() {
      public void componentMoved(ComponentEvent e) {
        removeComponentListener(this);
        setBounds(sharkStartFrame.mainFrame.getBounds());
        validate();
        addComponentListener(this);
      }
    });
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2007-03-06^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    // enables exiting screen via the ESC key
//    addWindowListener(new java.awt.event.WindowAdapter() {
//      public void windowActivated(WindowEvent e) {
//        requestFocus();
//      }
//    });
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    addKeyListener(new KeyAdapter() {
          public void keyPressed(KeyEvent e) {
              int code = e.getKeyCode();
              if(code == KeyEvent.VK_ESCAPE)
                dispose();
          }
    });
    this.getContentPane().setLayout(layout1);
    panel1.setLayout(layout2);
    grid.fill = GridBagConstraints.BOTH;
    panel1.setPreferredSize(new Dimension(sharkStartFrame.screenSize.width/10,sharkStartFrame.mainFrame.getSize().height));
    grid.gridx = -1;
    grid.gridy = 0;
    grid.gridheight = 1;
    grid.weighty = 1;
    grid.weightx = 1;
    getContentPane().add(panel1,grid);
    grid.insets = new Insets(0,0,0,0);
    grid.gridwidth = 7;
    grid.weightx = 7;
    if(!multi) getContentPane().add(new JScrollPane(items),grid);
    grid.gridwidth = 1;
    grid.weightx = 1;
    grid.gridx = 0;
    grid.gridy = -1;
    JPanel pnLabel = new JPanel(new GridBagLayout());
    grid.fill = GridBagConstraints.NONE;
    pnLabel.add(lab1,grid);    
    grid.fill = GridBagConstraints.BOTH;
    grid.weighty = 0;
    panel1.add(pnLabel,grid);
    lab1.setText( "<html><br>" +  lab1.getText() + "<br><br></html>"  );
    pnLabel.setBackground(sharkStartFrame.cream);
    pnLabel.setOpaque(true); 
    JPanel pnSessionButtons = new JPanel(new GridBagLayout());
    grid.fill = GridBagConstraints.BOTH;
    grid.weightx = 0;
    grid.insets = new Insets(0, 0, 0, 0);
    pnSessionButtons.add(printbutton, grid);
    grid.insets = new Insets(b2, 0, 0, 0);
    pnSessionButtons.add(xmlbutton, grid);
    student st = sharkStartFrame.studentList[sharkStartFrame.currStudent];
    if(st.administrator && !st.teacher)
    pnSessionButtons.add(unisettings, grid);
    grid.insets = new Insets(0, 0, 0, 0);
    grid.weightx = 1;
    int boxBorderSize = 1;
    Color boxBorderColor = Color.gray;
    if(!multi){
        grid.fill = GridBagConstraints.NONE;
        grid.gridx = -1;
        grid.gridy = 0;
        String ss[] = null;
        for(int n = 0; n <student.adminlist.length; n++){
            String adm = student.adminlist[n];
            if(stu.isCurrentStuOf(adm)){
                adm = prefix + adm;
                if(ss==null)ss = new String[]{adm};
                else ss = u.addString(ss, adm);
            }
        }
        JPanel pnFilter = new JPanel(new GridBagLayout());
        JPanel pnFilter2 = new JPanel(new GridBagLayout());
        jbLists = u.sharkButton();
        Image im = Toolkit.getDefaultToolkit().getImage(sharkStartFrame.publicPathplus + "sprites" +
                                       sharkStartFrame.separator +
                                       "search_il48.png");
        ImageIcon iiinfo = new ImageIcon(im.getScaledInstance(buttonimdim,
            buttonimdim, Image.SCALE_SMOOTH));
        jbLists.setIcon(iiinfo);
        jbLists.setPreferredSize(new Dimension(buttondim, buttondim));
        jbLists.setMinimumSize(new Dimension(buttondim, buttondim));
        jbLists.addActionListener( new java.awt.event.ActionListener() {
           public void actionPerformed(ActionEvent e) {
               okTopicNames = null;
               getAllTopics();
               new findword(ssr, sharkStartFrame.mainFrame, findword.TYPE_GETCOURSELISTNAME, okTopicNames);
           }
        }); 
        jbGames = u.sharkButton();
        jbGames.setIcon(iiinfo);
        jbGames.setPreferredSize(new Dimension(buttondim, buttondim));
        jbGames.setMinimumSize(new Dimension(buttondim, buttondim));
        jbGames.addActionListener( new java.awt.event.ActionListener() {
           public void actionPerformed(ActionEvent e) {
               new findword(ssr, sharkStartFrame.mainFrame, findword.TYPE_GETGAMENAME);
           }
        });  
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = -1;  
        gbc.gridx = 0;
        JPanel pnFilterSurround = new JPanel(new GridBagLayout());
        gbc.weightx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        pnFilterSurround.add(new JLabel(u.gettext("records", "view")+ ":"), gbc);
        gbc.anchor = GridBagConstraints.CENTER; 
        gbc.weightx = 0;
        grid.fill = GridBagConstraints.NONE;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(0, 0, b1, b2);
        pnFilter.add(new JLabel(u.gettext("records", "records")+ ":"), gbc);
        gbc.insets = new Insets(0, 0, b1, 0);
        gbc.anchor = GridBagConstraints.CENTER;
        JLabel lbAll = null;
        if(ss==null){
            lbAll = new JLabel(strAll);
            lbAll.setPreferredSize(new Dimension(lbAll.getPreferredSize().width, buttondim));
            lbAll.setBorder(BorderFactory.createLineBorder(boxBorderColor, boxBorderSize));
        }
        ss = u.addString(ss, strAll, 0);        
        cbWork = new JComboBox(ss);
        cbWork.setPreferredSize(new Dimension(cbWork.getPreferredSize().width, buttondim));
        cbWork.setBorder(BorderFactory.createLineBorder(boxBorderColor, boxBorderSize));
        cbWork.addItemListener(new ItemListener() {
          public void itemStateChanged(ItemEvent e) {
            String s = (String)((JComboBox)e.getSource()).getSelectedItem();
            if(s==null || s.equals(worklast))return;
            worklast = s;
            if(s.equals(strAll)){
                wantworkfor = null;
            }
            else if(s.startsWith(prefix)){
                wantworkfor = s.substring(prefix.length());
            }
            buildSessions(); 
          }
        }); 
        cbWork.setSelectedItem(null);
//        if(stu.workforteacher!=null && !stu.workforteacher.trim().equals("")){
//            cbWork.setSelectedItem(prefix+stu.workforteacher);
//        }
//        else{
            cbWork.setSelectedItem(strAll);
//        }
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        pnFilter.add(((lbAll==null)?(JComponent)cbWork:(JComponent)lbAll), gbc);
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(0, 0, b1, b2);
        pnFilter.add(new JLabel(u.gettext("records", "period")+ ":"), gbc);
        gbc.insets = new Insets(0, 0, b1, 0);
        gbc.anchor = GridBagConstraints.CENTER;
        cbPeriod = new JComboBox(period_strings);
        cbPeriod.addItemListener(new ItemListener() {
          public void itemStateChanged(ItemEvent e) {
            String s = (String)((JComboBox)e.getSource()).getSelectedItem();
            if(s==null || s.equals(periodlast))return;
            periodlast = s;
            int k = u.findString(period_strings, s);
            if(k<0)return;
            k = Integer.parseInt(period_ints[k]);
            setPeriod(k);
            buildSessions(); 
          }
        });
        cbPeriod.setPreferredSize(new Dimension(cbPeriod.getPreferredSize().width, buttondim));
        cbPeriod.setBorder(BorderFactory.createLineBorder(boxBorderColor, boxBorderSize));
        cbPeriod.setSelectedItem(null);
        cbPeriod.setSelectedItem(period_strings[userperiod]);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        pnFilter.add(cbPeriod, gbc);
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(0, 0, b1, b2);
        pnFilter.add(new JLabel(u.gettext("records", "list")+ ":"), gbc);
        gbc.insets = new Insets(0, 0, b1, 0);
               
        cbWordLists = new JComboBox();
        cbWordLists.setPreferredSize(new Dimension(cbWordLists.getPreferredSize().width, buttondim));
        cbWordLists.setBorder(BorderFactory.createLineBorder(boxBorderColor, boxBorderSize));
        cbWordLists.addItemListener(new ItemListener() {
          public void itemStateChanged(ItemEvent e) {
            String s = (String)((JComboBox)e.getSource()).getSelectedItem();
            if(s==null || s.equals(wordlistlast))return;
            wordlistlast = s;
            doFilter(s, findword.TYPE_GETLISTNAME);
//            buildSessions();
          }
        });

        cbGames = new JComboBox();
        cbGames.setPreferredSize(new Dimension(cbGames.getPreferredSize().width, buttondim));
        cbGames.setBorder(BorderFactory.createLineBorder(boxBorderColor, boxBorderSize));
        cbGames.addItemListener(new ItemListener() {
          public void itemStateChanged(ItemEvent e) {
            String s = (String)((JComboBox)e.getSource()).getSelectedItem();
            if(s==null || s.equals(gamelast))return;
            gamelast = s;
            doFilter(s, findword.TYPE_GETGAMENAME);
//            buildSessions();
          }
        });
        cbWordLists.setVisible(false);
        cbGames.setVisible(false);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST; 
        gbc.fill = GridBagConstraints.HORIZONTAL;
        pnFilter.add(cbWordLists, gbc);
        pnFilter.add(lbFilterList, gbc);
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER; 
        gbc.gridx = 2;
        gbc.insets = new Insets(0, b1, b1, 0);
        pnFilter.add(jbLists, gbc);
        gbc.insets = new Insets(0, 0, b1, 0);
        gbc.gridy = 3;
        gbc.gridx = 0;   
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(0, 0, 0, b2);
        pnFilter.add(new JLabel(u.gettext("records", "game")+ ":"), gbc);
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.anchor = GridBagConstraints.CENTER;           
        gbc.gridx = 1;  
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        pnFilter.add(cbGames, gbc);
        pnFilter.add(lbFilterGame, gbc);
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 2;
        gbc.insets = new Insets(0, b1, 0, 0);
        pnFilter.add(jbGames, gbc);
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.gridy = 4;
        gbc.gridx = 1;
        gbc.insets = new Insets(b1, 0, 0, 0);
        btReset = new JButton(u.gettext("reset", "label"));
        btReset.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               if(cbGames!=null)cbGames.setSelectedItem(strAllGames);
               if(cbWordLists!=null)cbWordLists.setSelectedItem(strAllLists);
               if(cbPeriod!=null){
                    cbPeriod.setSelectedItem(period_strings[userperiod]);
               }
               if(cbWork!=null)cbWork.setSelectedItem(strAll);
               buildSessions();
          }
        });
        pnFilter.add(btReset, gbc);
        gbc.insets = new Insets(0, 0, 0, 0);
        lbFilterList.setPreferredSize(new Dimension(lbFilterList.getPreferredSize().width, buttondim));
        lbFilterList.setBorder(BorderFactory.createLineBorder(boxBorderColor, boxBorderSize));
        lbFilterList.setText(strAllLists);
        lbFilterGame.setPreferredSize(new Dimension(lbFilterGame.getPreferredSize().width, buttondim));
        lbFilterGame.setBorder(BorderFactory.createLineBorder(boxBorderColor, boxBorderSize));
        lbFilterGame.setText(strAllGames);
        pnFilter2.setBorder(BorderFactory.createEtchedBorder());
        grid.gridx = 0;
        grid.gridy = -1;
        grid.fill = GridBagConstraints.HORIZONTAL;
        JPanel pnFilterMain = new JPanel(new GridBagLayout());

        grid.gridx = -1;
        grid.gridy = 0;
        grid.weightx = 1;
        grid.insets = new Insets(b1, b1, b1, b1);
        pnFilter2.add(pnFilter, grid);
        grid.insets = new Insets(0, 0, 0, 0);
        pnFilterMain.add(pnFilter2, grid);
        grid.weightx = 0;
        grid.anchor = GridBagConstraints.CENTER; 
        
        grid.gridx = 0;
        grid.gridy = -1;   
        
        grid.insets = new Insets(b2, b2, 0, b2);
        panel1.add(pnFilterSurround, grid);
        grid.insets = new Insets(0, b2, 0, b2);
        panel1.add(pnFilterMain, grid);
        grid.insets = new Insets(0, 0, 0, 0);
    }
    grid.fill = GridBagConstraints.BOTH;
    pnSessionList = new JPanel(new GridBagLayout());
    grid.weightx = 1;
    grid.weighty = 1;
    grid.insets = new Insets(0, 0, 0, 0);
    pnSessionList.add(new JScrollPane(sessions), grid);
    grid.insets = new Insets(0, 0, 0, 0);
    grid.gridx = -1;
    grid.gridy = 0;  
    grid.weighty = 1;
    JPanel pnSessions = new JPanel(new GridBagLayout());
    pnSessionNone = new JPanel(new GridBagLayout());

    JLabel nosess = new JLabel(u.convertToHtml(strnosessions_cr, true));
    nosess.setForeground(Color.gray);

    JLabel nosess2 = new JLabel(u.convertToHtml(u.gettext("records", "suggestion"), true));
    nosess2.setForeground(Color.gray);    
    nosess2.setFont(smallerplainfont);
    
    
    pnSessionNone.setBorder(BorderFactory.createEtchedBorder());
    pnSessionNone.setBackground(Color.white);
    pnSessionNone.setOpaque(true);
    grid.fill = GridBagConstraints.NONE;
    grid.weighty = 0;
        grid.gridx = 0;
    grid.gridy = -1; 
    pnSessionNone.add(nosess, grid);
    grid.insets = new Insets(b2, 0, 0, 0);
    pnSessionNone.add(nosess2, grid);
    grid.insets = new Insets(0, 0, 0, 0);
    grid.fill = GridBagConstraints.BOTH;
    grid.gridx = -1;
    grid.gridy = 0;  
    grid.weighty = 1;
    grid.insets = new Insets(b2, b2, 0, b2);
 //   grid.weighty = 0;
    pnSessions.add(pnSessionList, grid); 
    if(!multi)
        pnSessions.add(pnSessionNone, grid);
    grid.insets = new Insets(0, 0, 0, 0);
    grid.weighty = 1;
    if(!multi){
        grid.weightx = 0;
        grid.fill = GridBagConstraints.NONE;
        grid.anchor = GridBagConstraints.NORTH;
        grid.insets = new Insets(b2, 0, 0, b2);
        pnSessions.add(pnSessionButtons, grid);
        grid.insets = new Insets(0, 0, 0, 0);
        grid.weightx = 1;
        grid.fill = GridBagConstraints.BOTH;
    }
    else{
        JPanel pnFilterOptions = new JPanel(new GridBagLayout());
        rbAll = new JRadioButton(u.gettext("records", "viewall"));
        rbYou = new JRadioButton(u.gettext("records", "viewyou"));
        ButtonGroup bg = new ButtonGroup();
        bg.add(rbAll);
        bg.add(rbYou);
        ItemListener bglisten = new java.awt.event.ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                boolean currradioval = rbAll.isSelected();     
                if(lastradioval == currradioval)return;
                lastradioval = currradioval;
                if(!currradioval){
                    wantworkfor = sharkStartFrame.studentList[sharkStartFrame.currStudent].name;
                }
                else{
                    wantworkfor = null;
                }
                setsessions();
            }
        };
        
        rbAll.addItemListener(bglisten);
        rbYou.addItemListener(bglisten);
        rbAll.setSelected(true);
        grid.weighty = 0;
        grid.gridx = 0;
        grid.gridy = -1;
        
        pnFilterOptions.add(rbAll, grid);
        pnFilterOptions.add(rbYou, grid);
   
        
        grid.insets = new Insets(b2, b2, 0, 0);
        panel1.add(pnFilterOptions, grid);
        grid.insets = new Insets(0, 0, 0, 0);
        grid.weighty = 1;
        
    }
    grid.gridx = 0;
    grid.gridy = -1;
    panel1.add(pnSessions, grid);
    grid.fill = GridBagConstraints.NONE;
    grid.gridheight = 1;
    grid.weighty = 0;       
    if(!multi) {
      lab2.setFont(plainfont);
      grid.anchor = GridBagConstraints.WEST;
      grid.insets = new Insets(0, b1, 0, 0);
      panel1.add(lab2, grid);
      grid.insets = new Insets(0, 0, 0, 0);
      grid.anchor = GridBagConstraints.CENTER;     
//      if(wantworkfor != null && otherWork) {     // rb 23/10/06 
//          JPanel rPanel = new JPanel(new GridBagLayout());
//          rPanel.setBorder(BorderFactory.createEtchedBorder());       
//          grid.weighty = 0;
//          grid.anchor = GridBagConstraints.CENTER;
//          grid.weighty = 1;
//          panel1.add(rPanel, grid);
//      }   
      grid.insets = new Insets(10, 0, 20, 0);
      panel1.add(exitbutton, grid);
      grid.insets = new Insets(0, 0, 0, 0);
    }
    else {
       JPanel mpp = new JPanel(new GridBagLayout());
       panel1.add(mpp,grid);
       grid.gridx = -1;
       grid.gridy = 0;  
       grid.insets = new Insets(20, 10, 20, 0);
       mpp.add(printbutton,grid);
//       if(!multi) {
//         mpp.add(xmlbutton, grid);
//       mpp.add(xmlbutton2, grid);
     //  mpp.add(unisettings, grid);
//       }
       mpp.add(exitbutton,grid);
    }
    if(multi){ sessions.addMouseListener(new MouseAdapter() {
        
          public void mousePressed(MouseEvent e) {

              String s = "";
              Object o = sessions.getSelectedValue();
              if(o instanceof String){
                  s = (String)
                          sessions.getSelectedValue();
//                  if(s.indexOf(strnosessions)>=0)return;
              }
              else if(o instanceof nameandrec) {
                  nameandrec tt = (nameandrec)o;
                  s = tt.name;
                  if(s==null){
                      s=tt.owner;
                  }
                  if(s==null){
                      s="";
                  }
          // nameandrec tt = (nameandrec)o;
          // topicPlayed t = tt.tp;){
                  
              }
        //      if(sessions.getSelectedIndex() == sessionsel)
        //          sessions.clearSelection();
              int k;
              if((k = s.indexOf("       -      "))>=0){
                  s = s.substring(0 , k);
              }
              stuselected = new boolean[stuselected.length];
              if((k = u.findString(stulist, s))>=0){
                 stuselected[k] = !stuselected[k];
              }
    //          setsessions();

         //     if(u.inlist(sessionsel, sessions.getSelectedIndex()))  
                  sessions.clearSelection();
              setsessions();
            }
    });
    }
    else {
      sessions.addMouseListener(new MouseAdapter() {
          public void mousePressed(MouseEvent e) {
            int sel = sessions.getSelectedIndex();
            cntl = (e.getModifiersEx() & e.CTRL_DOWN_MASK) != 0 ;
            if(cntl) return;
            if(sessions.getSelectedValue() instanceof showMonth) {
              showMonth sm = (showMonth)sessions.getSelectedValue();
              if(!sm.expanded) {
                topicPlayed tt[] = sm.current?stu.studentrecord.clone() : getalltp(sm.f);
//                if(sm.current){
                    tt = sortToTime(tt);
//                }
                 for(int j = tt.length -1; j >= 0; j--){
                     if(!isTopicPlayedOK(tt[j]))
                        tt = u.removeTopicPlayed(tt, j);
                 }
                if(tt.length > 0) {
                  topicPlayed tt1 = tt[0];
                  int tot = 0;
                  int j = 0;
                  for (i = j =0; i < tt.length; ++i) {
                    if (!tt1.date.equals(tt[i].date)) {
                      showSession ss = new showSession(tt1, tot);
                      if(!sm.current) ss.f = sm.f;
                      v.add(sel + ++j, ss);
                      tot = 0;
                      tt1 = tt[i];
                    }
                    tot += tt[i].score;
                  }
                  showSession ss = new showSession(tt1, tot);
                  if(!sm.current) ss.f = sm.f;
                  v.add(sel + ++j, ss);
                  sessions.setListData(v);
                }
                sm.expanded = true;
             }
              else {
                while(sel+1<v.size() && v.get(sel+1) instanceof showSession) v.remove(sel+1);
                sessions.setListData(v);
                sm.expanded = false;
              }
            }
            refresh();
          }
      });
      sessions.addListSelectionListener(new ListSelectionListener() {
        public void valueChanged(ListSelectionEvent e) {
          Object sel[] = sessions.getSelectedValues();
          items.v.removeAllElements();
        lastheading = 0;
        headingcount = 0;
          for (short i = 0; i < sel.length; ++i) {
            if (multi) {
              additems( (String) sel[i]);
              if (items.v.size() == 0) {
                items.setListData(new String[] {"----   " + stu.name + u.gettext("so", "nogames")});
                return;
              }
            }
            else
                if (sel[i] instanceof showMonth && cntl) {
               showMonth sm = (showMonth) sel[i];
               if(!sm.current) {
                 topicPlayed tt[] = getalltp(sm.f);
                 if(tt != null && tt.length>0)
                     items.showItems(getalltp(sm.f));
               }
               else if (stu.studentrecord.length>0){
                   topicPlayed tt1[] = (topicPlayed[])stu.studentrecord.clone();
                   tt1 = sortSessionsToTime(tt1);
                   items.showItems(tt1);
               }
            }
            else {
              if (sel[i] instanceof showSession) {
                showSession ss = (showSession) sel[i];
                if(ss.f !=null) items.showItems(stu, ss.rec.date,
                                  ss.rec.dateoff,getalltp(ss.f));
                else items.showItems(stu, ss.rec.date,
                                 ss.rec.dateoff,stu.studentrecord);
              }
           }
          }
          items.setListData(items.v);
        }
      });
    }
//startPR2004-08-03^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      // enables exiting screen via the ESC key
      sessions.addKeyListener(new KeyAdapter() {
        public void keyPressed(KeyEvent e) {
          int code = e.getKeyCode();
          if (code == KeyEvent.VK_ESCAPE) {
            dispose();
          }
        }
      });
      if(!multi) {
        items.addKeyListener(new KeyAdapter() {
          public void keyPressed(KeyEvent e) {
            int code = e.getKeyCode();
            if (code == KeyEvent.VK_ESCAPE) {
              dispose();
            }
          }
        });
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        /*
        deletebutton.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            deleteSessions();
//startPR2004-08-03^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            // enables exiting screen via the ESC key
            requestFocus();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          }
        });*/
        /*
        undobutton.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            undodelete();
            requestFocus();
          }
        });
         */
         /*
        deleteallbutton.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            deleteAllSessions();
//startPR2004-08-03^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            // enables exiting screen via the ESC key
            requestFocus();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          }
        });*/
      }
//      allrecords.addActionListener(new ActionListener() {  // rb 23/10/06
//           public void actionPerformed(ActionEvent e) {
//             if(!multi) {
//               addSessions();
//               j = sessions.getModel().getSize();
//               if(j>0) {
//                --j;
//                sessions.setSelectedIndex(j);
//                sessions.ensureIndexIsVisible(j);
//               }
//               else  sessions.clearSelection();
//               showworkfor.setVisible(!allrecords.isSelected());
//             }
//          }
//      });
   exitbutton.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
             dispose();
          }
      });
    printbutton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
           if(multi) {
             items.v.removeAllElements();
             for(short i = 0; i<stulist.length; ++i) {
                 additems(stulist[i]);
             }
             items.setListData(items.v);
           }
           printStudentRecord(thisframe);
//startPR2004-08-03^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            // enables exiting screen via the ESC key
            requestFocus();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        }
    });
    unisettings.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            new userRecordSettings(thisframe);
            requestFocus();
        }
    });
    xmlbutton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
           xmlStudentRecord(thisframe,false);
//startPR2004-08-03^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            // enables exiting screen via the ESC key
            requestFocus();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        }
    });
    if(xmlbutton2 != null) xmlbutton2.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          xmlStudentRecord(thisframe,true);
           requestFocus();
       }
   });
//   if(!multi)
//       this.addWindowListener(new java.awt.event.WindowAdapter() {
//        public void windowClosing(WindowEvent e) {
//        }
//     });
   
    if(!multi) {
       buildSessions(); 
    }
     validate();
//     if(okTopicNames==null){
//         if(jbGames!=null)jbGames.setEnabled(false);
//         if(jbLists!=null) jbLists.setEnabled(false);
//     }
  }


  void getAllTopics(){
      int len = sessions.getModel().getSize();
      for(int k = 0; k < len; k++){
          Object sel = sessions.getModel().getElementAt(k);
            if (sel instanceof showMonth) {
               showMonth sm = (showMonth) sel;
               if(!sm.current) {
                 getalltp(sm.f);;
               }
               else if (stu.studentrecord.length>0){
                  for(int p = 0; p < stu.studentrecord.length; p++)
                       isTopicPlayedOK(stu.studentrecord[p]);
               }
            }
            else {
              if (sel instanceof showSession) {
                showSession ss = (showSession) sel;
                if(ss.f !=null) getalltp(ss.f);
                else {
                    for(int p = 0; p < stu.studentrecord.length; p++)
                       isTopicPlayedOK(stu.studentrecord[p]);
                }
              }
           }

      }
   }
  //-------------------------------------------------------------

   void filterChanged(){
       boolean changed = false;
       if(cbGames!=null && cbGames.isShowing() && !strAllGames.equals(cbGames.getSelectedItem()))changed=true;
       if(cbWordLists!=null && cbWordLists.isShowing() && !strAllLists.equals(cbWordLists.getSelectedItem()))changed=true;
       if(cbPeriod!=null &&  !period_strings[userperiod].equals(cbPeriod.getSelectedItem()))changed=true;
       if(cbWork!=null && cbWork.isShowing() && !strAll.equals(cbWork.getSelectedItem()))changed=true;
       if(btReset!=null){
           btReset.setEnabled(changed);
       }
   }

   /*
   boolean hasFilterChanged(){
                boolean changed = false;
                if(stu.workforteacher!=null && !stu.workforteacher.trim().equals("")){
                    if(cbWork!=null && !cbWork.getSelectedItem().equals(prefix+stu.workforteacher)) changed = true;
                }
                else if(cbWork!=null && !cbWork.getSelectedItem().equals(strAll)){
                    changed = true;
                }
                if(cbPeriod!=null && cbPeriod.getSelectedIndex()!=0){
                    changed = true;
                }
                if(filterList!=null){
                    changed = true;
                }
                if(filterGame!=null){
                    changed = true;
                }
       return changed;
   }
   */
  void additems(String name) {
     student stu1 = findStudent(name);
     if(stu1==null)return;
     topicPlayed tpd[] = stu1.studentrecord;
     for (int k = tpd.length - 1; k >= 0; --k) {
         if(!isTopicPlayedOK(tpd[k]))tpd = u.removeTopicPlayed(tpd, k);
     }
     if(tpd.length==0){
         tpd = getLatestFileTPs(stu1);
     }
     if (tpd!=null && tpd.length > 0 ) {
         for(i =  tpd.length-1;i>=0;--i) {
//            if(wantworkfor != null  && !allwork // rb 23/10/06
//               && (tpd[i].workforteacher == null
//                                      || wantworkfor != null && !wantworkfor.equalsIgnoreCase(tpd[i].workforteacher))) continue;
            if(tpd[i].date != tpd[tpd.length-1].date) break;
         }
         items.showItems(stu1, tpd[i+1].date,
                      tpd[tpd.length-1].dateoff,tpd);
    }
  }
  //--------------------------------------------------------------
  void setPeriod(int k){
            if(k<0)
                calendarLimit = null;
            else {
                calendarLimit = Calendar.getInstance();
                calendarLimit.setTime(new Date());
                calendarLimit.add(Calendar.MONTH, -k);
            }
  }
  
  void doFilter(String name, int type) {
      if(name==null)return;
      if(type==findword.TYPE_GETLISTNAME){
          int k = name.indexOf("@");
          if(k > 0)
              name = name.substring(0, k);
          lbFilterList.setText(name);
          if(name.equals(strAllLists))filterList = null;
          else filterList = name;
          if(u.findString(chosenLists, name)<0 && !name.equals(strAllLists)){
              chosenLists = u.addString(chosenLists, name);
              u.sort(chosenLists);
          }
          cbWordLists.removeAllItems();
          for(int i = chosenLists.length-1; i >= 0; i--){
              cbWordLists.insertItemAt(chosenLists[i], 0);
          }
          cbWordLists.insertItemAt(strAllLists, 0);
          cbWordLists.setSelectedItem(name);
          cbWordLists.setVisible(true);
          lbFilterList.setVisible(false);          
      }
      else if(type==findword.TYPE_GETGAMENAME){
          lbFilterGame.setText(name);
          if(name.equals(strAllGames))filterGame = null;
          else filterGame = name;
          if(u.findString(chosenGames, name)<0 && !name.equals(strAllGames)){
              chosenGames = u.addString(chosenGames, name);
              u.sort(chosenGames);
          }
          cbGames.removeAllItems();
          for(int i = chosenGames.length-1; i >= 0; i--){
              cbGames.insertItemAt(chosenGames[i], 0);
          }
          cbGames.insertItemAt(strAllGames, 0);
          cbGames.setSelectedItem(name);
          cbGames.setVisible(true); 
          lbFilterGame.setVisible(false);             
      }
      panel1.revalidate();
      buildSessions(); 

  }
  /*
  void buildSessions(){
        if(!multi) {
            addSessions();
            j = sessions.getModel().getSize();
            if(j>0) {
                --j;
                sessions.setSelectedIndex(j);
                sessions.ensureIndexIsVisible(j);
            }
            else  sessions.clearSelection();
        } 
  }
  */
  void buildSessions(){
      File f = new File(sharkStartFrame.sharedPath, strsturecfolder);
      if (f.exists()) {
          start = strsturecfile;
          start = u.edit(start.substring(0,5), stu.name, shark.USBprefix);
          File list[] = f.listFiles(new FileFilter() {
             public boolean accept(File file) {
                return file.getName().startsWith
                        (start);
             }
          });          
          if(list.length == 0 || !addMonths(list)) 
              addSessions();
      }
      else 
          addSessions();
//      j = sessions.getModel().getSize();
//      if(j>0) {
//       --j;
       sessions.setSelectedIndex(0);
          sessions.ensureIndexIsVisible(0);
       if(!(sessions.getSelectedValue() instanceof showSession))
           sessions.clearSelection();
//      }
//      else
//          sessions.clearSelection();
      filterChanged();
      refresh();
  }
  
  void addSessions() {
    v = new Vector();
    Date lastdate = null;
//    int totscore = 0;
    int i,j,tot;
//    if (shark.macOS) {
//      lab2.setText(u.gettext("sturec_", "h2_mac"));
//    }
//    // if running on Windows
//    else {
//      lab2.setText(u.gettext("sturec_", "h2"));
//    }

    for(i=0;i<stu.studentrecord.length;++i) {
//       if(wantworkfor != null  && !allwork  // rb 23/10/06
//          && (stu.studentrecord[i].workforteacher == null
//                                  || wantworkfor != null && !wantworkfor.equalsIgnoreCase(stu.studentrecord[i].workforteacher))) continue;
       if(!isTopicPlayedOK(stu.studentrecord[i]))continue;
       if(lastdate==null || !stu.studentrecord[i].date.equals(lastdate)) {
          lastdate =  stu.studentrecord[i].date; 
//          if(!isTopicPlayedOK(stu.studentrecord[i])) continue;          
          for(j=i,tot = 0; j < stu.studentrecord.length
                        && stu.studentrecord[j].date.equals(lastdate);++j) {
//              if(wantworkfor != null  && !allwork  // rb 23/10/06
//                     && (stu.studentrecord[j].workforteacher == null
//                             || wantworkfor != null && !wantworkfor.equalsIgnoreCase(stu.studentrecord[j].workforteacher))) continue;
              if(!isTopicPlayedOK(stu.studentrecord[j]))continue;
             tot += stu.studentrecord[j].score;
          }
          v.add(new showSession(stu.studentrecord[i],tot));
       }
    }
    v = sortToTime(v);
    sessions.setListData(v);
    refresh();
  }

  // newest first
  Vector sortToTime(Vector v){
      if((v==null  || v.size()==0 ||!(v.get(0) instanceof showSession)) || (!(v.get(v.size()-1) instanceof showSession)))
          return v;
      showSession s1 = (showSession)v.get(0);
      showSession s2 = (showSession)v.get(v.size()-1);
      if(s1==null || s1.rec==null || s2==null || s2.rec==null)
          return v;
      if(s1.rec.date.before(s2.rec.date))
          Collections.reverse(v);
      return v;
  }

  topicPlayed[] sortToTime(topicPlayed[] tt){
      topicPlayed s1 = tt[0];
      topicPlayed s2 = tt[tt.length-1];
      if(s1==null || s2==null)
          return tt;
      if(s1.date.before(s2.date)){
           topicPlayed tt1[] = tt.clone();
           int index = tt1.length-1;
           for(int n = 0; n < tt1.length; n++){
              tt[n] = tt1[index--];
           }
      }
      return tt;
  }

  topicPlayed[] sortSessionsToTime(topicPlayed[] tt){
      topicPlayed tt1[] = tt.clone();
      topicPlayed res[] = new topicPlayed[tt1.length];
      ArrayList resal = new ArrayList();
      int headingcounter = 0;
      for(int ii = res.length-1; ii >= 0; ii--){
          if(!(ii == res.length-1 || tt1[ii].date.equals(tt1[ii+1].date))){
            headingcounter = (res.length-1-ii);
          }
          resal.add(headingcounter, tt1[ii]);
      }
      for(int ii = 0; ii < resal.size(); ii++){
        res[ii] = (topicPlayed)resal.get(ii);
      }
      return res;
  }

  
  void refresh(){
    if(v.isEmpty()){
        if(pnSessionList!=null)pnSessionList.setVisible(false);
        if(pnSessionNone!=null)pnSessionNone.setVisible(true);
//        lab2.setText( u.gettext("so","nosessions"));
        lab2.setForeground(sharkStartFrame.defaultbg);
    }
    else{
        if(pnSessionList!=null)pnSessionList.setVisible(true);
        if(pnSessionNone!=null)pnSessionNone.setVisible(false);
        lab2.setForeground(Color.black);
    }
    boolean some = sessions.getModel().getSize()>0;
    if(jbGames!=null)jbGames.setEnabled(some);
    if(jbLists!=null) jbLists.setEnabled(some);
  }
  
  //--------------------------------------------------------------
  topicPlayed lastMonth(student user) {
//   Date lastdate = null;
//    int totscore = 0;
    int i,j;//,tot;
    for(i=0;i<user.studentrecord.length;++i) {
//       if(wantworkfor != null  && !allwork  // rb 23/10/06
//          && (user.studentrecord[i].workforteacher == null
//                                  || wantworkfor != null && !wantworkfor.equalsIgnoreCase(user.studentrecord[i].workforteacher))) continue;
       if(!isTopicPlayedOK(user.studentrecord[i]))continue;
       return user.studentrecord[i];
    }
    return null;
  }
  //--------------------------------------------------------------
  
  Vector addMonths2(File list[], student user, boolean justfromfiles) {
    v = new Vector();
//    Date lastdate = null;
//    int totscore = 0;
    int i,j;//,tot;
    for(i=list.length-1;i>=0;i--) {
      topicPlayed tt[] = gettp(list[i]);
      if(tt != null && tt[0] != null) 
          v.add(new showMonth(tt[0], tt[1], list[i]));
    }
    if(v.isEmpty()) 
        return null;
    else {
      if(!justfromfiles){
          topicPlayed tt = lastMonth(user);
          if(tt != null && isTopicPlayedOK(tt))  {
            showMonth sm = new showMonth(tt, null, null);
            sm.current = true;
            v.add(0, sm);
          }
      }
      return v;
    }
  }    

  boolean addMonths(File list[]) {
     Vector v = addMonths2(list, stu, false);
     if(v==null)return false;
     else{
      sessions.setListData(v);
      refresh();
      return true;         
     }
  }
  
  
  
  //----------------------------------------------------------
  boolean isTopicPlayedOK(topicPlayed tt) {
     Calendar calendar = Calendar.getInstance();
     calendar.setTime(tt.date);     
     String s = wantworkfor;
     if(s!=null && s.startsWith(prefix)){
        s = s.substring(prefix.length());
     }
     boolean isok = (calendarLimit==null || !calendar.before(calendarLimit)) && 
             (s==null || (tt.workforteacher!=null && tt.workforteacher.equals(s))) &&
             (filterList==null || tt.topic.equals(filterList)) &&
             (filterGame==null || tt.game.equals(filterGame));
     if(isok){
         if(okTopicNames==null)okTopicNames = new String[]{tt.topic};
         else {
             if(u.findString(okTopicNames, tt.topic)<0)
                okTopicNames = u.addString(okTopicNames, tt.topic);
         }         
     }
     return isok;
  }  
  
  
  public void cancelStudentRecord() {
    sessions = null;
    items = null;
  }
  //--------------------------------------------------------- start rb 27/10/06
  /*
  void undodelete() {
//    db db1 = db.get(stu.name,true);   // keep db1 open and locked throughout this method
//    savestudent copystu = (savestudent)db1.find("student",db.STUDENT);
//    if(copystu==null) return;
//    stu.excludegames = copystu.excludegames;
//    stu.password = copystu.password;
     stu.studentrecord = undo;
     String dd[][] = new String[delqueue.length-1][];
     System.arraycopy(delqueue,0,dd,0,delqueue.length-1);
     delqueue = dd;
//     db1.update("student",copystu,db.STUDENT);
//     db1.close();
     undo = null;
     undobutton.setEnabled(false);
     lab2.setText(u.gettext("sturec_","h2"));
     addSessions();
  }
   */
  //---------------------------------------------------------
  /*
  void deleteSessions() {
//     db db1 = db.get(stu.name,true);   // keep db1 open and locked throughout this method
//     savestudent copystu = (savestudent)db1.find("student",db.STUDENT);
//     if(copystu==null) return;
     undo = stu.studentrecord;
     undobutton.setEnabled(true);
//     stu.excludegames = copystu.excludegames;
//     stu.password = copystu.password;
//     stu.studentrecord = copystu.studentrecord;
     Object sel[] = sessions.getSelectedValues();
     int selindex[] = sessions.getSelectedIndices();
     short i,j,lastsel = (short)sessions.getLeadSelectionIndex();
     topicPlayed tp[] =  new topicPlayed[sel.length];
     String dd[][] = new String[delqueue.length+1][];
     System.arraycopy(delqueue,0,dd,0,delqueue.length);
     delqueue = dd;
     delqueue[delqueue.length-1] = new String[0];
     Date lastdate = null;
     for(i=0;i<sel.length;++i) {
        tp[i] = ((showSession)sel[i]).rec;
        delqueue[delqueue.length-1] = u.addString(delqueue[delqueue.length-1],tp[i].date.toString());
     }
     for(i=0;i<sel.length;++i) {
        for(j=0;j<stu.studentrecord.length;++j) {
           if(stu.studentrecord[j].date.equals(tp[i].date)) {
              topicPlayed sr[] = new topicPlayed[stu.studentrecord.length - 1];
              if(j>0) System.arraycopy(stu.studentrecord,0,sr,0,j);
              if(j<stu.studentrecord.length-1)
                    System.arraycopy(stu.studentrecord,j+1,sr,j,
                                             stu.studentrecord.length-1-j);
              stu.studentrecord = sr;
              --j;
           }
           stu.delarchive = true;   // remind to rewrite archive    rb 22/2/08  v5
        }
        if(lastsel > selindex[i]) --lastsel;
     }
//     copystu.studentrecord = stu.studentrecord;
//     db1.update("student",copystu,db.STUDENT);
//     db1.close();
     addSessions();
     if((j=(short)sessions.getModel().getSize()) > 0) {
       sessions.setSelectedIndex(j = (short)Math.min(lastsel,j-1));
       sessions.ensureIndexIsVisible(j);
     }
     else  {
         sessions.clearSelection();
     }
  }
  //---------------------------------------------------------
  void deleteAllSessions() {
//startPR2004-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//     if (JOptionPane.showConfirmDialog(null,
     if(JOptionPane.showConfirmDialog(sharkStartFrame.mainFrame,
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      u.gettext("sturec_deleteall","mess"),
                                     u.gettext("sturec_deleteall","title"),
                                     JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE)
                                  == JOptionPane.YES_OPTION) {
//        db db1 = db.get(stu.name,true);   // keep db1 open and locked throughout this method
//        savestudent copystu = (savestudent)db1.find("student",db.STUDENT);
//        if(copystu==null) return;
        undo = stu.studentrecord;
        undobutton.setEnabled(true);
//        stu.excludegames = copystu.excludegames;
//        stu.password = copystu.password;
        String dd[][] = new String[delqueue.length+1][];
        System.arraycopy(delqueue,0,dd,0,delqueue.length);
        delqueue = dd;
        delqueue[delqueue.length-1] = new String[]{"all"};
        stu.studentrecord = new topicPlayed[0];
//        db1.update("student",copystu,db.STUDENT);
//        db1.close();
        addSessions();
        sessions.repaint();
        stu.delarchive = true;   // remind to rewrite archive    rb 22/2/08  v5
     }
  }
   */
  //----------------------------------------------------------     end rb 27/10/06
    void xmlStudentRecord(JDialog jf, boolean all) {             // v5 start rb 14/3/08
      topicPlayed tp[] = null;
      int i,j;
      File out1 = xmlwhere();
      if(out1 == null) return;
      FileOutputStream out;
      try{
        out = new FileOutputStream(out1);
        out.write(("<?"+u.gettext("sturec_xml", "title")+"?>").getBytes());
      }
      catch(IOException e1) {
         return;
      }
      if(multi) {
        for(i = 0; i<stulist.length; ++i) {
          student stu = student.findStudent(stulist[i]);
          if(stu != null) {
            stu.addarchive();
            tp = new topicPlayed[0];
            int tot=0;
            if (!all) {
              for (j = 0; j < stu.studentrecord.length; ++j) {
                 if(belongto.equals(stu.studentrecord[j].workforteacher)) ++tot;
              }
              if(tot>0) {
                topicPlayed tp1[] = new topicPlayed[tp.length + tot];
                System.arraycopy(tp, 0, tp1, 0, tp.length);
                tot=tp.length;
                for (j = 0; j < stu.studentrecord.length; ++j) {
                   if(belongto.equals(stu.studentrecord[j].workforteacher)) {
                     tp1[tot] = stu.studentrecord[j];
                     ++tot;
                   }
                }
                tp = tp1;
              }
            }
            else {
              topicPlayed tp1[] = new topicPlayed[tp.length + stu.studentrecord.length];
              System.arraycopy(tp, 0, tp1, 0, tp.length);
              System.arraycopy(stu.studentrecord, 0, tp1, tp.length, stu.studentrecord.length);
              tp = tp1;
            }
            xmlwritestu(tp,out,stu);
          }
        }
      }

      else
        xmlwritestu(seltopics(),out,stu);
//      else {
//        stu.addarchive();
//        int tot=0;
//        tp = new topicPlayed[0];
//        if (!all) {
//          for (j = 0; j < stu.studentrecord.length; ++j) {
//             if(belongto.equals(stu.studentrecord[j].workforteacher)) ++tot;
//          }
//          if(tot>0) {
//            tp = new topicPlayed[tot];
//            tot=0;
//            for (j = 0; j < stu.studentrecord.length; ++j) {
//               if(belongto.equals(stu.studentrecord[j].workforteacher)) {
//                 tp[tot] = stu.studentrecord[j];
//                 ++tot;
//               }
//            }
//          }
//        }
//        else {
//           tp = stu.studentrecord;
//        }
//      }
//      xmlwritestu(tp,out,stu);
      try {out.close();}
      catch(IOException e1) {
         return;
      }
    }
    topicPlayed[] seltopics() {
       int i,j, tot = 0;
       for(i=0;i<items.v.size();++i) {
          if (items.v.elementAt(i) instanceof topicPlayed) ++tot;
       }
       topicPlayed tp[] = new topicPlayed[tot];
       for(i=j=0;i<items.v.size();++i) {
          if (items.v.elementAt(i) instanceof topicPlayed) tp[j++] = (topicPlayed)items.v.elementAt(i);
       }
       return tp;
    }
    File xmlwhere() {
          int i;
          String filename, dirname;
          if(db.query(sharkStartFrame.studentList[sharkStartFrame.currStudent].name,"xml_filename",db.TEXT) >=0) {
              filename = (String) db.find(sharkStartFrame.studentList[sharkStartFrame.currStudent].name,
                                                "xml_filename", db.TEXT);
          }
          else filename = u.gettext("sturec_xml", "filename")+".xml";
          if(db.query(sharkStartFrame.studentList[sharkStartFrame.currStudent].name,"xml_dirname",db.TEXT) >=0) {
              dirname = (String) db.find(sharkStartFrame.studentList[sharkStartFrame.currStudent].name,
                                                "xml_dirname", db.TEXT);
          }
          else dirname = null;
          JFileChooser fc = dirname==null?new JFileChooser():new JFileChooser(dirname);
          fc.setSelectedFile(new File(filename));
          fc.setFileFilter(new javax.swing.filechooser.FileFilter() {
            public boolean accept(File f) {
              if (! (f.getName().endsWith(".xml")) && !f.isDirectory()) {
                return false;
              }

              return true;
            }
            public String getDescription() {
              return ".xml";
            }
          });

          int returnVal = fc.showSaveDialog(thisframe);
          if (returnVal == fc.APPROVE_OPTION) {
            File newf = fc.getSelectedFile();
            String newfile = newf.getName();
            String newdir = newf.getParent();
            if(!newfile.equals(filename))
              db.update(sharkStartFrame.studentList[sharkStartFrame.currStudent].name,"xml_filename",newfile,db.TEXT);
            if(!newdir.equals(dirname))
              db.update(sharkStartFrame.studentList[sharkStartFrame.currStudent].name,"xml_dirname",newdir,db.TEXT);
            return newf;
          }
          else {
            return null;
          }
    }
    void xmlwritestu(topicPlayed tp[], FileOutputStream out, student stu) {  //OUTOFSYNC - all xml stuff?
      String program = u.gettext("sturec_xml", "program");
      String student = u.gettext("sturec_xml", "student");
      String name = u.gettext("sturec_xml", "name");
      String session = u.gettext("sturec_xml", "session");
      String date = u.gettext("sturec_xml", "date");
      String time = u.gettext("sturec_xml", "time");
      String topic = u.gettext("sturec_xml", "topic");
      String game = u.gettext("sturec_xml", "game");
      String errortot = u.gettext("sturec_xml", "errortot");
      String peeptot = u.gettext("sturec_xml", "peeptot");
      String score = u.gettext("sturec_xml", "score");
      String error = u.gettext("sturec_xml", "error");
      String peep = u.gettext("sturec_xml", "peep");
      String duration = u.gettext("sturec_xml", "duration");
      String speed = u.gettext("sturec_xml", "speed");
      String incomplete = u.gettext("sturec_xml", "incomplete");
      String sharedwith = u.gettext("sturec_xml", "sharedwith");
//      String shuffled = u.gettext("sturec_xml", "shuffled");
      String gamename = u.gettext("sturec_xml", "gamename");
      String wordlistname = u.gettext("sturec_xml", "wordlistname");
      String incompletevalue = u.gettext("sturec_xml", "incompletevalue");
      
      try {
        out.write( ("<" + program + ">").getBytes());
        out.write( ("<" + student + " " + name +"=\""+ stu.name + "\" >").getBytes());
        Date lastdate = null;
        String lasttopic = "";
        int ii;
        for(i=0;i<tp.length;) {
          out.write( ("<" + session
                      + " " + date + "=\"" + xmldate(tp[i]) + "\" "
                      + time + "=\"" + xmltime(tp[i]) + "\" >"
                      ).getBytes());
          lastdate = tp[i].date;
          for (; i < tp.length && tp[i].date.equals(lastdate); ) {
            String topicnamei = u.convertForXML(tp[i].topic);
            out.write( ("<" + topic + " " + wordlistname + "=\"" + topicnamei + "\" >").getBytes());
            lasttopic = topicnamei;
            for (; i < tp.length && u.convertForXML(tp[i].topic).equals(lasttopic); ) {
                out.write( ("<" + game + " " + gamename + "=\"" + u.convertForXML(tp[i].game) + "\" >").getBytes());
                if (tp[i].sharedwith != null)
                  out.write(xmldata(sharedwith, tp[i].sharedwith));
                if (tp[i].incomplete)
//                  out.write(xmldata(incomplete, ""));
                    out.write(xmldata(incomplete, incompletevalue));
                if (tp[i].errorList != null) {
                  out.write(xmldata(errortot, String.valueOf(tp[i].errorList.length)));
                  out.write(xmldata(error, u.combineString(tp[i].errorList, " ")));
                }
                if (tp[i].peepList != null) {
                  out.write(xmldata(peeptot, String.valueOf(tp[i].peepList.length)));
                  out.write(xmldata(peep, u.combineString(tp[i].peepList, " ")));
                }
                out.write(xmldata(score, String.valueOf(tp[i].score)));
                out.write(xmldata(duration, String.valueOf(tp[i].time)));
                if (tp[i].speed >= 0)
                  out.write(xmldata(speed, String.valueOf(tp[i].speed)));
                out.write( ("</" + game + ">").getBytes());
                ii = i + 1;
                if(ii < tp.length && u.convertForXML(tp[ii].topic).equals(lasttopic)
                         && (tp[ii].date.equals(lastdate))
                        ){
                    i++;
                }
                else break;
            }
            out.write( ("</" + topic + ">").getBytes());
            ii = i + 1;
            if(ii < tp.length && tp[ii].date.equals(lastdate))i++;
            else break;
          }
          out.write( ("</" + session + ">").getBytes());
          ii = i + 1;
          if(ii<tp.length)i++;
          else break;
        }
        out.write( ("</" + student  + ">").getBytes());
        out.write( ("</" + program + ">").getBytes());
      }
      catch(IOException e1) {
         return;
      }

    }
    String xmltime(topicPlayed tp) {
      return new mySimpleDateFormat("HH:mm",tp.timezone).format(tp.date);
    }
    String xmldate(topicPlayed tp) {
      return new mySimpleDateFormat("EEE dd MMM yyyy",tp.timezone).format(tp.date);
    }
    byte[] xmldata(String name, String val) {
      int i;
      while((i=val.indexOf('&'))>= 0) val = val.substring(0,i) + "&amp;" + val.substring(i+1);
      while((i=val.indexOf('>'))>= 0) val = val.substring(0,i) + "&gt;" + val.substring(i+1);
      while((i=val.indexOf('<'))>= 0) val = val.substring(0,i) + "&lt;" + val.substring(i+1);
      while((i=val.indexOf('\''))>= 0) val = val.substring(0,i) + "&apos;" + val.substring(i+1);
      while((i=val.indexOf('"'))>= 0) val = val.substring(0,i) + "&aquot;" + val.substring(i+1);
      return ("<" + name + ">" + val + "</"+name+">").getBytes();
    }
 //-------------------------------------------------------------------- v5 end rb 14/3/08
//startPR2004-10-05^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    void printStudentRecord(JDialog jf) {
      if(items.getModel().getSize() == 0) return;
      if (sessions.getSelectedIndices().length != 0 || multi) {
        PrinterJob printJob = PrinterJob.getPrinterJob();
        printJob.setJobName(u.gettext("so","printrec"));
        PageFormat pageFormat = printJob.defaultPage();
        pageFormat.setOrientation(pageFormat.PORTRAIT);
        printJob.setPrintable(this, pageFormat);
        if (printJob.printDialog()) {
            try {
              printJob.print();
            }
            catch (Throwable t) {}
        }
      }
    }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2004-10-05^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  public int print(Graphics g, PageFormat f, int a) throws PrinterException {
//startPR2006-04-06^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      if(items.getModel().getSize()==0) return Printable.NO_SUCH_PAGE;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      g.translate( (int) f.getImageableX(), (int) f.getImageableY());
      Dimension dd = new Dimension( (int) f.getImageableWidth(),
                                   (int) f.getImageableHeight());
      int w = dd.width;
      int h = dd.height;
      int xx = 0;
      int yy = 0;
      Date d = new Date();
      String sr[] = u.splitString(u.gettext("so", "reportline"));
//startPR2004-10-21^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      int blankSpace = 50;
      String blankString = "";
      if(stu!=null){
          for(int i = stu.name.length(); i < blankSpace; i++)
            blankString = blankString.concat(" ");
      }
      String h1 = sr[0] + (multi?belongto:stu.name) + blankString + sr[1];
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      Font f1 = sizeFont(g, h1 + "999" + sr[2] + "999  ", w);
      FontMetrics m1 = g.getFontMetrics();
      Font f2 = sizeFont(g, "xxxxxxxxxx", w / 10);
      FontMetrics m2 = g.getFontMetrics();
      int onechar = m2.charWidth(' ');
      int ih = m2.getHeight(), hi;
      int step = w / 10 - 1;
      int x1 = 0;
      int x2 = x1 + step * 5;
      int x3 = x1 + step * 7;
      int x4 = x1 + step * 8;
      int x5 = x1 + step * 9;
      int y = h + 1, ydiff = -m2.getMaxAscent() + ih / 2; // force heading
      int x;
      topicPlayed t = null;
      String s, s1 = null, s2 = null;
      short i, j;
      setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
      int pageCounter = 0;

      //find the total number of pages needed
      //only do this once and not when this method is being called for an image
      //buffer's graphics context
      if((totPages == 0)&&(a == lastPage)){
        for (i = 0; i < items.getModel().getSize(); ++i) {
          Object o = items.getModel().getElementAt( (int) i);
          hi = ih;
          if (o instanceof topicPlayed) {
            t = (topicPlayed) o;
            if (t.errorList != null)
              hi += ih;
            if (t.peepList != null)
              hi += ih;
          }
          else
            ih = hi;
            // if the current list entry is off the bottom of the current page
          if (y + hi > h - m2.getHeight()) {
            // record the total number of pages of the print
            totPages++;
            y = m1.getHeight() * 2 + m2.getMaxAscent();
          }
          x = x1;
          if (o instanceof topicPlayed) {
            s1 = null;
            s2 = null;
            s = t.topic;
            if (t.sharedwith != null)                                  // v5  rb 9/3/08
              s = u.gettext("so", "sharedwith", t.sharedwith) + s;     // v5  rb 9/3/08
            if (t.incomplete)
              s = u.gettext("so", "incomplete") + s;
//            if (t.shuffled)
//              s = s + u.gettext("so", "shuffled");
            x += m2.stringWidth(s) + onechar * 3;
            x += m2.stringWidth(t.game) + onechar * 3;
            if (t.speed >= 0) {
              x += m2.stringWidth(s);
            }
            x += m2.stringWidth(s);
            x = w - onechar;
            y += ih;
            if (t.errorList != null) {
              s1 = er;
              for (j = 0; j < t.errorList.length; ++j) {
                s1 = s1 + " " + t.errorList[j];
              }
              x = w - m2.stringWidth(s1) - onechar * 2;
            }
            if (t.peepList != null) {
              if (t.peepList[0].indexOf(':') < 0)
                s2 = pe;
              else s2 = "";
              for (j = 0; j < t.peepList.length; ++j) {
                s2 = s2 + " " + t.peepList[j];
              }
              x = Math.min(x, w - m2.stringWidth(s2) - onechar * 2);
            }
            x = Math.max(x, onechar);
            if (s1 != null) {
              y += ih;
            }
            if (s2 != null) {
              y += ih;
            }
          }
          else {
            s = (String) o;
            y += ih;
          }
        }
        ih = m2.getHeight();
        y = h + 1;
      }

      // end the print if it has been completed
       if((a >= totPages)&&(totPages != 0)){
         lastPage = -1;
         totPages = 0;
         return NO_SUCH_PAGE;
       }

     // allows skipping of the buffer's graphics context
     if (a > lastPage) {
       lastPage = a;
       return Printable.PAGE_EXISTS;
     }

      // draw the printed pages
      // iterate through all student record list entries
      for (i = 0; i < items.getModel().getSize(); ++i) {
        Object o = items.getModel().getElementAt( (int) i);
        hi = ih;        if (o instanceof topicPlayed) {
          t = (topicPlayed) o;
          if (t.errorList != null)
            hi += ih;
          if (t.peepList != null)
            hi += ih;
        }
        else
          ih = hi;
          // if the current list entry is off the bottom of the current page
        if (y + hi > h - m2.getHeight()) {
          // if the current page of the print is equal to the page currently
           // being built clear the contents of the previous page
          if (pageCounter == a) {
            g.setColor(Color.white);
            g.fillRect(0, 0, g.getClipBounds().width,
                       g.getClipBounds().height);
            g.setColor(Color.black);
          }
          //if current page is finished being constructed stop drawing onto the
          // printing Graphics object

          // end of printing for current page
          if (pageCounter > a) {
            //end of current page
            break;
          }
          pageCounter++;
          y = m1.getHeight() * 2 + m2.getMaxAscent();
          g.setFont(f2);
          g.setColor(Color.black);
        }
        x = x1;
        if (o instanceof topicPlayed) {
          s1 = null;
          s2 = null;
          s = t.topic;
          if (t.sharedwith != null)                                     // v5  rb 9/3/08
            s = u.gettext("so", "sharedwith", t.sharedwith) + s;        // v5  rb 9/3/08
          if (t.incomplete)
            s = u.gettext("so", "incomplete") + s;
//          if (t.shuffled)
//            s = s + u.gettext("so", "shuffled");
          g.drawString(s, xx + x1, yy + y);
          x += m2.stringWidth(s) + onechar * 3;
          g.drawString(t.game, xx + (x = Math.max(x2, x)), yy + y);
          x += m2.stringWidth(t.game) + onechar * 3;
          if (t.speed >= 0) {
            g.drawString(s = sp + String.valueOf(t.speed),
                         xx + (x = Math.max(x, x3)), yy + y);
            x += m2.stringWidth(s);
          }
          g.drawString(s = ti + edittime(t.time),
                       xx + Math.max(x, x4), yy + y);
          x += m2.stringWidth(s);
          g.drawString(s = sc + String.valueOf(t.score),
                       xx + Math.max(x, x5), yy + y);
          x = w - onechar;
          y += ih;
          if (t.errorList != null) {
            s1 = er;
            for (j = 0; j < t.errorList.length; ++j) {
              s1 = s1 + " " + t.errorList[j];
            }
            x = w - m2.stringWidth(s1) - onechar * 2;
          }
          if (t.peepList != null) {
            if (t.peepList[0].indexOf(':') < 0)
              s2 = pe;
            else s2 = "";
            for (j = 0; j < t.peepList.length; ++j) {
              s2 = s2 + " " + t.peepList[j];
            }
            x = Math.min(x, w - m2.stringWidth(s2) - onechar * 2);
          }
          x = Math.max(x, onechar);
          if (s1 != null) {
            g.drawRect(xx + x - onechar, yy + y - m2.getMaxAscent(), w - x, ih);
            g.drawString(s1, xx + x, yy + y);
            y += ih;
          }
          if (s2 != null) {
            g.drawRect(xx + x - onechar, yy + y - m2.getMaxAscent(), w - x - 1,
                       ih);
            g.drawString(s2, xx + x, yy + y);
            y += ih;
          }
        }
        else {
          s = (String) o;
          g.drawString(s, xx + (x = (w - m2.stringWidth(s)) / 2), yy + y);
          g.drawLine(xx, yy + y + ydiff, xx + x - 1, yy + y + ydiff);
          g.drawLine(xx + x + m2.stringWidth(s), yy + y + ydiff, xx + w - 1,
                     yy + y + ydiff);
          y += ih;
        }
      }

      // draw heading
      g.setFont(f1);
//startPR2004-10-21^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      // was sr[4]
      s = h1 + String.valueOf(a + 1) + sr[2] + String.valueOf(totPages);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      g.drawString(s, xx + (w - m1.stringWidth(s)) / 2, yy + m1.getHeight());

      return Printable.PAGE_EXISTS;
    }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  //--------------------------------------------------------------
  String edittime(int ti) {
     String s = String.valueOf(ti/60) + ":";
     ti%=60;
     if(ti<10) s += '0';
     return s + String.valueOf(ti);
  }
  //--------------------------------------------------------------
   public Font sizeFont(Graphics g,String s, int w) {
//startPR2007-11-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//        Font f = new Font(sharkStartFrame.wordfont.getName(), sharkStartFrame.wordfont.getStyle(),40);
        Font f = sharkStartFrame.wordfont.deriveFont((float)40);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        g.setFont(f);
        FontMetrics m = g.getFontMetrics();
        while(f.getSize() > 8 && m.stringWidth(s) > w) {
//startPR2007-11-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//            f = new Font(f.getName(), f.getStyle(), f.getSize()-1);
          f =  f.deriveFont((float)f.getSize()-1);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            g.setFont(f);
            m = g.getFontMetrics();
        }
        return f;
  }
  //--------------------------------------------------------------------
  String editsession(topicPlayed tt) {
    String ss[] = u.splitString(u.gettext("so","sessions"));
    String ssact[] = u.splitString(u.gettext("so","sessionsact"));
    Date doff = tt.dateoff, d = tt.date;
    if(doff==null) {
      return ssact[1] +
      ssact[2]
       + (new mySimpleDateFormat("HH:mm",tt.timezone)).format(d) + ssact[4]
       + (new mySimpleDateFormat("EEE dd MMM yyyy",tt.timezone)).format(d)
       + (tt.teacher?ssact[5]:"");

    }
    else {
      return  ss[1] +
            ss[2]
            + (new mySimpleDateFormat("HH:mm",tt.timezone)).format(d) + ss[3]
            + (new mySimpleDateFormat("HH:mm",tt.timezone)).format(doff) + ss[4]
            + (new mySimpleDateFormat("EEE dd MMM yyyy",tt.timezone)).format(d)
            + (tt.teacher ? ss[5] : "");
    }

  }
  //-----------------------------------------------------------
  
  class userRecordSettings extends JDialog{
      
      JDialog thisjd = this;
      
      public userRecordSettings(JDialog owner){
          super(owner);
          this.setModal(true);
          this.setLayout(new GridBagLayout());
          this.setTitle(u.gettext("adminsettings", "userrecords"));
          int sw = sharkStartFrame.mainFrame.getWidth();
          int sh = sharkStartFrame.mainFrame.getHeight();
          int ww = sw*3/6;
          int hh = sh*15/24;
//          this.setBounds((sw-ww)/2, (sh-hh)/2, ww, hh);
          this.setBounds(u2_base.adjustBounds(new Rectangle((sw-ww)/2, (sh-hh)/2, ww, hh)));
          GridBagConstraints grid = new GridBagConstraints();
          grid.gridx = 0;
          grid.gridy = -1;
          grid.weightx = 1;
          grid.weighty = 1;
          grid.fill = GridBagConstraints.BOTH;
          JPanel jp = new settings.pnUserRecords();
          getContentPane().add(jp,grid);
          
          JButton btOK = new JButton(u.gettext("ok", "label"));
          btOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               thisjd.dispose();
            }
          });
          grid.weighty = 1;
          getContentPane().add(jp,grid);
          grid.weighty = 0;
          grid.fill = GridBagConstraints.NONE;
          grid.insets = new Insets(10,0,10,0);
          getContentPane().add(btOK,grid);
          this.setVisible(true);
      }
  }
  
  
  class playeditemlist extends JList {
      Vector v = new Vector();
      public playeditemlist() {
         super();
         setCellRenderer(new itempainter());
      }
      public void showItems(student stu1, Date d,Date doff,topicPlayed[] tt) {
        boolean first = true;
        String ss[] = u.splitString(u.gettext("so","sessions"));
        String ssact[] = u.splitString(u.gettext("so","sessionsact"));
        for(int i=tt.length-1; i >= 0; i--) {
//          if(wantworkfor != null  && !allwork  // rb 23/10/06
//             && (tt[i].workforteacher == null
//                                     || wantworkfor != null && !wantworkfor.equalsIgnoreCase(tt[i].workforteacher))) continue;
           if(!isTopicPlayedOK(tt[i]))continue;
           if(first) {
              first = false;
              if(doff==null) {
                v.add((multi?(stu1.name + ssact[0]):ssact[1]) +
                ssact[2]
                 + (new mySimpleDateFormat("HH:mm",tt[i].timezone)).format(d) + ssact[4]
                 + (new mySimpleDateFormat("EEE dd MMM yyyy",tt[i].timezone)).format(d)
                 + (tt[i].teacher?ssact[5]:""));

              }
              else {
                v.add( (multi ? (stu1.name + ss[0]) : ss[1]) +
                      ss[2]
                      + (new mySimpleDateFormat("HH:mm",tt[i].timezone)).format(d) + ss[3]
                      + (new mySimpleDateFormat("HH:mm",tt[i].timezone)).format(doff) + ss[4]
                      + (new mySimpleDateFormat("EEE dd MMM yyyy",tt[i].timezone)).format(d)
                      + (tt[i].teacher ? ss[5] : ""));
              }
            }
            if(tt[i].date.equals(d)) {
               v.add(tt[i]);
            }
         }
      }
      public void showItems(topicPlayed[] tt) {
        boolean first = true;
        String ss[] = u.splitString(u.gettext("so","sessions"));
//        String ssact[] = u.splitString(u.gettext("so","sessionsact"));
        int orilastheading = v.size();
        headingcount = 0;
        for(short i=0; i < tt.length;++i) {
//          if(wantworkfor != null  && !allwork  // rb 23/10/06
//             && (tt[i].workforteacher == null
//             || wantworkfor != null && !wantworkfor.equalsIgnoreCase(tt[i].workforteacher))) continue;
           if(!isTopicPlayedOK(tt[i]))continue;
           if(first || !tt[i].date.equals(tt[i-1].date)) {
              lastheading = orilastheading + i;
              headingcount++;
              first = false;
              v.add( (multi ? (stu.name + ss[0]) : ss[1]) +
                      ss[2]
                      + (new mySimpleDateFormat("HH:mm",tt[i].timezone)).format(tt[i].date) + ss[3]
                      + (tt[i].dateoff == null?"":(new mySimpleDateFormat("HH:mm",tt[i].timezone)).format(tt[i].dateoff)) + ss[4]
                      + (new mySimpleDateFormat("EEE dd MMM yyyy",tt[i].timezone)).format(tt[i].date)
                      + (tt[i].teacher ? ss[5] : ""));
            }
            v.add(lastheading+headingcount, tt[i]);
         }
      }
  }

  //-----------------------------------------------------------
  class showSession {
     topicPlayed rec;
     int score;
     File f;
     showSession(topicPlayed top,int score1) {
        rec = top;
        score = score1;
     }
     public String toString() {
       String ret =
         new mySimpleDateFormat("EEE dd MMM yyyy  HH:mm",rec.timezone).format(rec.date);
       if(score > 0) ret += " (" + sc + " " + String.valueOf(score) + ")";
         return ret;
     }
  }
  //-----------------------------------------------------------
  class showMonth {
     String narr;
     File f;
     boolean expanded,current;
//     String prefix = " --- ";
//     String suffix = " ---";
     String prefix = " "+u.phonicsplit+" ";
     String suffix = "";
     showMonth(topicPlayed tt,topicPlayed last,File f1) {
        f = f1;
        if(f != null && f.getName().endsWith(arcend)) {
           narr = prefix + new mySimpleDateFormat("MMM yyyy",tt.timezone).format(tt.date) + " - "
                         + new mySimpleDateFormat("MMM yyyy",last.timezone).format(last.date) + " " + archives + suffix;
        }
        else narr = prefix + new mySimpleDateFormat("MMM yyyy",tt.timezone).format(tt.date) + suffix;
     }
     public String toString() {
         return narr;
     }
  }
  //--------------------------------------------------------------------------------------------
     // returns first record for month for required teacher
  topicPlayed[] gettp(File f) {
    topicPlayed ret[] = new topicPlayed[2];
    int i;
    FileInputStream fis = null;
    ObjectInputStream ois =null;
    try{
      fis = new FileInputStream(f);
      ois = new ObjectInputStream(fis);
      boolean archive = f.getName().endsWith(arcend);
      while(true) {
         topicPlayed tt[] = (topicPlayed[])ois.readObject();
//         for(int j = tt.length -1; j >= 0; j--){
//             if(!isTopicPlayedOK(tt[j]))
//                tt = u.removeTopicPlayed(tt, j);
//         }
//         for(i=0;i<tt.length;++i) {
         for(i=tt.length-1;i >= 0;i--) {
//             if (wantworkfor != null && !allwork
//               && tt[i].workforteacher == null
//               || wantworkfor != null && !wantworkfor.equalsIgnoreCase(tt[i].workforteacher))
//             continue;
           if(!isTopicPlayedOK(tt[i]))continue;    
           if (!archive)
             return new topicPlayed[] {tt[i], tt[i]};
           else {
             if (ret[0] == null) ret[0] = tt[i];
             ret[1] = tt[i];
           }
         }
      }
    }
    catch(OptionalDataException e){
    }
    catch(IOException e){
    }
    catch(ClassNotFoundException e) {
    }
    if(ois != null) try {
        ois.close();
        fis.close();
    }
    catch(IOException e){
    }
    return ret;
  }
   //--------------------------------------------------------------------------------------------
     // returns all records for month for required teacher
  topicPlayed[] getalltp(File f) {
    topicPlayed ret[] = new topicPlayed[1000];
    int tot=0;
    FileInputStream fis = null;
    ObjectInputStream ois =null;
    try{
      fis = new FileInputStream(f);
      ois = new ObjectInputStream(fis);
      while (true) {
          topicPlayed tt[] = (topicPlayed[]) ois.readObject();
//         for(int j = tt.length -1; j >= 0; j--){
//             if(!isTopicPlayedOK(tt[j]))
//                tt = u.removeTopicPlayed(tt, j);
//         }
//          for(i=0;i<tt.length;++i) {
//          for(i=tt.length-1;i >= 0;i--) {
          for(int k=0;k<tt.length;k++) {
//            if (wantworkfor != null && !allwork
//                && tt[i].workforteacher == null
//                || wantworkfor != null && !wantworkfor.equalsIgnoreCase(tt[i].workforteacher))
//              continue;
            if(!isTopicPlayedOK(tt[k]))continue;
            if (tot >= ret.length) {
              topicPlayed ret2[] = new topicPlayed[tot + 1000];
              System.arraycopy(ret, 0, ret2, 0, tot);
              ret = ret2;
            }
            ret[tot++] = tt[k];
          }
        }
    }
    catch(OptionalDataException e){}
    catch(IOException e){}
    catch(ClassNotFoundException e) {}
    if(ois != null) try {
        ois.close();
        fis.close();
    }
    catch(IOException e){
    }
    topicPlayed ret2[] = new topicPlayed[tot];
    System.arraycopy(ret,0,ret2,0,tot);
    return ret2;
  }
  //-----------------------------------------------------------
   public class itempainter2 extends JLabel implements ListCellRenderer {
      Object o;
      Font smallfont;
      FontMetrics sm;
      boolean selected;
      int w,h;
      Color col;
      itempainter2() {setOpaque(true);}

    FontMetrics m;
    public Component getListCellRendererComponent( JList list,Object oo,
          int index,boolean isSelected,boolean cellhasFocus) {
       o = oo;
       selected = isSelected;
       col = list.getSelectionBackground();
       this.setBackground(isSelected?col:Color.white);
       if(o instanceof showMonth) { 
           this.setForeground(Color.blue);
           setText(((showMonth)o).narr);
        }
       else if(o instanceof showSession){
           this.setForeground(Color.black);
          setText(((showSession)o).toString());
       }
       
       return this;
    }
    /*
    public void paint(Graphics g) {
        ( (Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
          Rectangle r = getBounds();
          if(m==null) {
            m = getFontMetrics(getFont());
          }
          int y1 = m.getMaxAscent();
        if(o instanceof showMonth) {            // rb 15/5/08
           g.setColor(Color.blue);
           g.drawString(((showMonth)o).narr,MARGIN,y1);
        }
        else  if(o instanceof showSession) {
            this.setBackground(selected?col:Color.white);
            g.setColor(Color.black);
            g.drawString(((showSession)o).toString(),MARGIN,y1);
        }
     }
*/
   }  
  
  
   class itempainter extends JLabel implements ListCellRenderer {
      Object o;
      Font smallfont;
      FontMetrics sm;
      boolean selected;
      int w,h;
      Color greentext = sharkStartFrame.fastmodecolor;

      itempainter() {setOpaque(true);}
      public Component getListCellRendererComponent( JList list,Object oo,
            int index,boolean isSelected,boolean cellhasFocus) {
         w = list.getWidth();
         h=list.getHeight();
         o = oo;
         selected=isSelected;
         return this;
      }
      public void paint(Graphics g) {
         g.setFont(getFont());
         FontMetrics m = g.getFontMetrics();
         int y1 = m.getMaxAscent();
         int ydiff = - m.getMaxAscent() + m.getHeight()/2;
         int onechar = m.charWidth(' ');
         int step,x1,x2,x3,x4,x5,x6=0,x,xx;
         if(!multi) {
           step = (w - MARGIN - MARGIN) / 8;
           x1 = MARGIN;
           x2 = x1 + step * 4;
           x3 = x1 + step * 5 + onechar * 3;
           x4 = x1 + step * 6;
           x5 = x1 + step * 7;
           x = x1;
         }
         else {
           step = (w - MARGIN - MARGIN) / 13;
           x = MARGIN;
           x1 = MARGIN+step;
           x2 = x1 + step * 4;
           x3 = x1 + step * 6;
           x4 = x1 + step * 7;
           x5 = x1 + step * 8;
           x6 = x1 + step * 9;
         }
         String s, s1=null,s2=null;
         int i,j;
         if(multi) {
           if(selected){// || o instanceof nameandrec && ((nameandrec)o).name==null ) {
             g.setColor(sessions.getSelectionBackground());
             g.fillRect(0,0,w,h);
           }
         }
         else g.clearRect(0,0,w,h);
         if(o instanceof String) {
            if(multi) {
              g.setColor(Color.black);
              g.drawString((String)o, x, y1);
            }
            else {
              g.setColor(headingColor);
              g.fillRect(0, 0, w, h);
              g.setColor(Color.black);
              s = (String) o;
              g.drawString(s, x = (w - m.stringWidth(s)) / 2, y1);
              g.drawLine(x1, y1 + ydiff, x - 1, y1 + ydiff);
              g.drawLine(x + m.stringWidth(s), y1 + ydiff, w - MARGIN - 1, y1 + ydiff);
            }
         }
         else if(o instanceof topicPlayed) {
            g.setColor(Color.white);
            g.fillRect(0,0,w,h);
            g.setColor(Color.lightGray);
            g.drawLine(0,0,w,0);
            topicPlayed t = (topicPlayed)o;
            s = t.topic;
            if (t.sharedwith != null)                                   // v5  rb 9/3/08
              s = u.gettext("so", "sharedwith", t.sharedwith) + s;      // v5  rb 9/3/08
            if(t.incomplete) s = u.gettext("so","incomplete")+s;
            g.setColor(t.teacher?Color.blue:Color.black);
            if(x1+ m.stringWidth(s)+ onechar > x2) {
                int points;
                if(smallfont==null) {
//startPR2007-11-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//                  smallfont = new Font(getFont().getName(),getFont().getStyle(), points=getFont().getSize()-1);
                    smallfont =  getFont().deriveFont((float)(points=getFont().getSize()-1));
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                  sm = getFontMetrics(smallfont);
                }
                else points = smallfont.getSize();
                while( x1+ sm.stringWidth(s)+ onechar > x2) {
//startPR2007-11-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//                  smallfont = new Font(getFont().getName(),getFont().getStyle(),--points);
                  smallfont = getFont().deriveFont((float)--points);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                  sm = getFontMetrics(smallfont);
                }
                g.setFont(smallfont);
                g.drawString(s,x1,y1);
                x += sm.stringWidth(s)+ onechar;
                g.setFont(getFont());
            }
            else {
               g.drawString(s,x1,y1);
               x += m.stringWidth(s)+ onechar*3;
            }
            g.drawString(t.game,x = Math.max(x2,x),y1);
            x += m.stringWidth(t.game) + onechar*3;
            if(t.speed>=0) {
              g.drawString(s = sp + String.valueOf(t.speed), x = Math.max(x, x3),
                           y1);
              x += m.stringWidth(s);
            }
            g.drawString(s=ti + edittime(t.time),
                           Math.max(x,x4),y1);
            x += m.stringWidth(s);
            g.drawString(s=sc + String.valueOf(t.score),
                           Math.max(x,x5),y1);
            x =   w - MARGIN-scrollBarWidth;
            if(t.peepList != null) {
               if(t.peepList[0].indexOf(':') < 0)
                   s2  =  pe;
               else s2 = "";
               for(i=0;i<t.peepList.length;++i) {
                   s2 =  s2 + " " + t.peepList[i];
               }
               x =   Math.min(x, w - MARGIN -scrollBarWidth - m.stringWidth(s2) - onechar);
            }
            x = Math.max(x,onechar);
            if(t.errorList != null) {
              y1 += m.getHeight();
              int xerr = w-MARGIN-scrollBarWidth;
              for(i=t.errorList.length-1;i>=0;--i) {
                String ss = ' '+t.errorList[i]+' ';
                xerr -= (j = m.stringWidth(ss));
                if(xerr<0) break;
//                g.setColor(Color.red);
//                g.fillRect(xerr,y1 - m.getMaxAscent(),j,m.getHeight());
                g.setColor(Color.red);
                g.drawString(ss,xerr, y1);
                xerr-=m.charWidth(' ')*2;
              }
              xerr -= (j = m.stringWidth(er));
              if(xerr>0) {
                g.setColor(Color.red);
                g.drawString(er, xerr, y1);
              }
            }
           if(s2 != null) {
              y1 += m.getHeight();
//              g.setColor(Color.green);
//              g.fillRect(x-onechar, y1 - m.getMaxAscent(),m.stringWidth(s2)+(onechar * 2),m.getHeight());
              g.setColor(greentext);
              g.drawString(s2,x,y1);
           }
        }
        else if(o instanceof showMonth) {            // rb 15/5/08
           g.setColor(Color.magenta);
           g.drawString(((showMonth)o).narr,x,y1);
        }
        else if(o instanceof nameandrec) {
           nameandrec tt = (nameandrec)o;
           topicPlayed t = tt.tp;
           s = t.topic;
           g.setColor(Color.black);
           if(tt.name != null) {
              g.drawString(tt.name,x,y1);
              x1 = Math.max(x + m.stringWidth(tt.name+"  "), x1);
           }
           if (t.sharedwith != null) {    // v5 start rb 9/3/08
             g.setColor(Color.magenta);
             String ss =  u.gettext("so", "sharedwith", t.sharedwith) + s;
             g.drawString(ss,x1,y1);
             x1 += m.stringWidth(ss)+ onechar;
          }                                // v5 end rb 9/3/08
           if(t.incomplete) {
             g.setColor(Color.red);
             String ss = u.gettext("so", "incomplete");
             g.drawString(ss,x1,y1);
             x1 += m.stringWidth(ss)+ onechar;
           }
           g.setColor(Color.blue);
           if(x1+ m.stringWidth(s)+ onechar > x2) {
               int points;
               if(smallfont==null) {
//startPR2007-11-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//                 smallfont = new Font(getFont().getName(),getFont().getStyle(),points=getFont().getSize()-1);
                   smallfont = getFont().deriveFont((float)(points=getFont().getSize()-1));
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                 sm = getFontMetrics(smallfont);
               }
               else points = smallfont.getSize();
               while( x1+ sm.stringWidth(s)+ onechar > x2) {
//startPR2007-11-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//                  smallfont = new Font(getFont().getName(), getFont().getStyle(), --points);
                 smallfont = getFont().deriveFont((float)--points);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                   sm = getFontMetrics(smallfont);
               }
               g.setFont(smallfont);
               g.drawString(s,x1,y1);
               x += sm.stringWidth(s)+ onechar;
               g.setFont(getFont());
           }
           else {
              g.drawString(s,x1,y1);
              x += m.stringWidth(s)+ onechar*3;
           }
           g.setColor(Color.magenta.darker());
           g.drawString(t.game,x = Math.max(x2,x),y1);
           x += m.stringWidth(t.game) + onechar*3;
           g.setColor(Color.cyan.darker());
           if(t.speed>=0) {
             g.drawString(s = sp + String.valueOf(t.speed), x = Math.max(x, x3),
                          y1);
             x += m.stringWidth(s);
           }
           g.setColor(Color.orange.darker());
           g.drawString(s=ti + edittime(t.time),
                          Math.max(x,x4),y1);
           x += m.stringWidth(s);
           g.setColor(Color.blue);
          g.drawString(s=sc + String.valueOf(t.score),
                          Math.max(x,x5),y1);

           x =   w - MARGIN -scrollBarWidth;
           if(t.errorList != null) {
             for(i=t.errorList.length-1;i>=0;--i) {
               String ss = t.errorList[i];
               x -= (j = m.stringWidth(ss));
               if(x<x6) break;
//               g.setColor(Color.red);
//               g.fillRect(x,y1 - m.getMaxAscent(),j,m.getHeight());
               g.setColor(Color.red);
               g.drawString(ss,x, y1);
               x-=m.charWidth(' ')*2;
             }
             x -= (j = m.stringWidth(er));
             if(x>x6) {
               g.setColor(Color.red);
               g.drawString(er, x, y1);
             }
           }
           if(t.peepList != null) {
              for(i=t.peepList.length-1;i>=0;--i) {
                 String ss =  t.peepList[i];
                 x -= (j = m.stringWidth(ss));
                 if(x<x6) break;
//                 g.setColor(Color.green);
//                 g.fillRect(x,y1 - m.getMaxAscent(),j,m.getHeight());
                 g.setColor(greentext);
                 g.drawString(ss,x, y1);
                 x-=m.charWidth(' ')*2;
              }
              if(t.peepList[0].indexOf(':') < 0) {
                x -= (j = m.stringWidth(pe));
                if (x > x6) {
                  g.setColor(greentext);
                  g.drawString(pe, x, y1);
                }
              }
           }
        }
      }
      public Dimension getPreferredSize() {
         FontMetrics m = getFontMetrics(getFont());
         int gh =  m.getHeight();
         int h =  MARGIN*2 + gh, h1=h;
         if(o instanceof String || o instanceof nameandrec) h = gh;
         else {
            topicPlayed t = (topicPlayed)o;
            if(t.errorList != null) h += gh;
            if(t.peepList != null) h += gh;
//            if(t.testresults != null) h += gh*t.testresults.length;
         }
         return  new Dimension(w, h);
     }
   }}   // end of student display class
    
  

  
  
//------------------------------------------------------------
public boolean changePassword(Object owner) {
    /*
   String op[] =  administrator || teacher?
                   u.splitString(u.gettext("changepass_","okcan")):
                   u.splitString(u.gettext("changepass_","oknop"));
   JOptionPane getpw;
   if(administrator|| teacher){
     getpw= new JOptionPane(
     u.gettext("changepass_",(password==null)?"nopass":"adminnewpass"),
     JOptionPane.PLAIN_MESSAGE, 0, null, op, op[0]);
   }
   else{
     getpw= new JOptionPane(
     u.gettext("changepass_",(password==null)?"nopass":"newpass"),
     JOptionPane.PLAIN_MESSAGE, 0, null, op, op[0]);
   }
   getpw.setWantsInput(true);
   JDialog dialog;
   if(owner instanceof sharkStartFrame){
      dialog = getpw.createDialog((JFrame)owner, u.gettext("changepass_", "heading"));
   }
   else{
      dialog = getpw.createDialog((JDialog)owner, u.gettext("changepass_", "heading"));
   }
   getpw.setInputValue("");
   
     */
    while(true) {
        int nodetype;
        if(teacher)nodetype = jnode.SUBADMIN;
        else if(administrator)nodetype = jnode.TEACHER;
        else nodetype = jnode.STUDENT; 
        newUser nu;
        if(owner instanceof JFrame)
            nu = new newUser(NewUser_base.MODE_PASSWORDONLY_RETURNVAL, (JFrame)owner, name, nodetype);
        else
            nu = new newUser(NewUser_base.MODE_PASSWORDONLY_RETURNVAL, (JDialog)owner, name, nodetype);
        if(administrator || teacher) {
            if(nu.returnpass!=null){
                password= encrypt(nu.returnpass);
                passwordhint = nu.returnpasswordhint;
                saveStudent();                   // rb 27/10/06
                return true;
            }            
//         if(result==null || result == op[1] || input == "")
//           return false;
//         else if(input.length() > 0)  {
//            password= encrypt(input);
//            saveStudent();                   // rb 27/10/06
//            return true;
//         }
            else if(nu.exitval == 0)
                JOptionPane.showMessageDialog(sharkStartFrame.mainFrame,u.gettext("changepass_","adminmust"),u.gettext("changepass_","heading"),JOptionPane.WARNING_MESSAGE);
            else return false;
        }
      else {                       // start rb 27/10/06
         if(nu.returnpass==null) {
           if(owner instanceof sharkStartFrame){
             password = null;
             saveStudent();
           }
           else {
               queueupdate(new String[]{name},new String[]{"password"});
               queueupdate(new String[]{name},new String[]{"passwordhint"});
           }
           return true;
         }
         else{
           if(owner instanceof sharkStartFrame){
             password = encrypt(nu.returnpass);
             passwordhint = nu.returnpasswordhint;
             saveStudent();
           }
           else {
               queueupdate(new String[]{name},new String[]{"password",nu.returnpass});
               if(nu.returnpasswordhint==null)
                   queueupdate(new String[]{name},new String[]{"passwordhint"});
               else
                   queueupdate(new String[]{name},new String[]{"passwordhint",nu.returnpasswordhint});
           }
           return true;             
         }
//         if(result == op[2] || result==null) return false;
//         else if(input.length() > 0) {
//           if(owner instanceof sharkStartFrame){
//             password = encrypt(input);
//             saveStudent();
//           }
//           else queueupdate(new String[]{name},new String[]{"password",input});
//           return true;
//         }
      }                              // end rb 27/10/06
   }
}
// void savepass() {   // save password on disk
//   db db1 = db.get(name,true);   // keep db1 open and locked throughout this method
//   savestudent copystu = (savestudent)db1.find("student",db.STUDENT);
//   if(copystu==null) return;
//   copystu.password = password;
//   db1.update("student",copystu,db.STUDENT);
//   db1.close();
// }
// void savexgames() {   // save excludegames disk
//   db db1 = db.get(name,true);   // keep db1 open and locked throughout this method
//   savestudent copystu = (savestudent)db1.find("student",db.STUDENT);
//   if(copystu==null) return;
//   copystu.excludegames = excludegames;
//   db1.update("student",copystu,db.STUDENT);
//   db1.update("_refresh", "", db.TEXT);
//   db1.close();
// }
 //-------------------------------------------------------------
 boolean getlock() {  //  start rb 8/2/06 used by admin to reserve for rename or delete
   try {
//startPR2006-04-30^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2010-03-15^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      if(shark.macOS && shark.network){
      if(MacLock.DOJNI){
//endPRtoupdate^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       if (MacLock.lock(sharkStartFrame.sharedPathplus + name, ".lock", null, false) < 0)
         macLocked = false;
       else macLocked = true;
     }
     else{
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2009-08-02^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//       fflock = new FileOutputStream(sharkStartFrame.sharedPathplus + name + ".lock");
       File f = new File(sharkStartFrame.sharedPathplus + name + ".lock");
       fflock = new FileOutputStream(f.getAbsolutePath());
       u2_base.setNewFilePermissions(f);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2007-11-19^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//       lock = fflock.getChannel().tryLock();
       // IOException needs to be caught on Linux cifs networks
       try{lock = fflock.getChannel().tryLock();}catch(IOException e){}
//startPR2010-03-18^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       catch (OverlappingFileLockException e) {}
//endPRtoupdate^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2006-04-30^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     }
//     if(lock == null) {
     if(lock == null && fflock != null) {
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       fflock.close();
       fflock = null;
     }
   }
   catch(IOException e) {

   }
//startPR2006-04-30^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//   return lock != null;
   return isLocked();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
 }
 void releaselock() {
//startPR2006-04-30^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//   if (lock != null) {
   if (isLocked()) {
//startPR2010-03-15^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      if(shark.macOS && shark.network){
      if(MacLock.DOJNI){
//endPRtoupdate^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       macLocked = false;
       MacLock.delete(sharkStartFrame.sharedPathplus + name, ".lock");
       MacLock.delete(sharkStartFrame.sharedPathplus + name, MacLock.LOCKEXTENSION);
     }
     else{
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       try {
         lock.release();
         lock = null;
         fflock.close();
         fflock = null;
         new File(sharkStartFrame.sharedPathplus + name + ".lock").delete();
       }
       catch (IOException e) {
       }
//startPR2006-04-30^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   }
 }                 // end rb 8/2/06
// static void tempsturemovelocks(student stu) {
//stu.signoff(false);
// }

 //-------------------------------------------------------------
 static boolean getlock(student stu, String db1,boolean mess) {  // replaced rb 22/1/06
   lockmess = null;
//startPR2006-11-22^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//   lockmess2 = null;
   lockmess2 = false;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   try {
//startPR2006-04-30^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2010-03-15^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      if(shark.macOS && shark.network){
      if(MacLock.DOJNI){
//endPRtoupdate^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       if(MacLock.lock(sharkStartFrame.sharedPathplus + stu.name , ".lock", null, false)<0)
         stu.macLocked = false;
       else
         stu.macLocked = true;
     }
     else{
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2009-08-02^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//       stu.fflock = new FileOutputStream(sharkStartFrame.sharedPathplus + stu.name + ".lock");
       File f = new File(sharkStartFrame.sharedPathplus + stu.name + ".lock");
       stu.fflock = new FileOutputStream(f.getAbsolutePath());
       u2_base.setNewFilePermissions(f);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2007-11-19^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//               stu.lock = stu.fflock.getChannel().tryLock();
       // IOException needs to be caught on Linux cifs networks
       try{
           stu.lock = stu.fflock.getChannel().tryLock();
       } 
       catch(IOException e){

       }
//startPR2010-03-18^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       catch (OverlappingFileLockException e) {

       }
//endPRtoupdate^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2006-04-30^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     }
//      if(stu.lock == null) {
     if(stu.lock == null && !stu.macLocked) {
       if(stu.fflock != null){
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         stu.fflock.close();
         stu.fflock = null;
//startPR2006-04-30^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       if (mess)    u.okmess("", u.gettext("so", "already", stu.name));
       else         lockmess = u.gettext("so", "already", stu.name);
     }
   }
   catch(IOException e) {
   }
//startPR2006-04-30^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//   if(stu.lock != null)  {
   if(stu.isLocked())  {  // OK - check not exceding max users for network
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2008-10-08^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//     if (shark.network) {
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       int i, j, limit;
       Calendar cc;
//startPR2006-04-30^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//       if (slotlock == null) {
       if (!isSlotLocked()) {
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         limit = sharkStartFrame.users + 1;
         for (i = 0; i < limit; ++i) {
           try {
//startPR2006-04-30^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2010-03-15^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      if(shark.macOS && shark.network){
            if(MacLock.DOJNI){
//endPRtoupdate^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
               if(MacLock.lock(sharkStartFrame.sharedPathplus +"user" + String.valueOf(i + 1), ".nlock", null, false)>=0){
                 macSlotLock = true;
               }
               else macSlotLock = false;
             }
             else{
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2009-08-02^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//               fslotlock = new FileOutputStream(sharkStartFrame.sharedPathplus +
//                                                "user" + String.valueOf(i + 1) +
//                                                ".nlock");
               File f = new File(sharkStartFrame.sharedPathplus + "user" + String.valueOf(i + 1) + ".nlock");
               fslotlock = new FileOutputStream(f.getAbsolutePath());
               u2_base.setNewFilePermissions(f);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2007-11-19^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//               slotlock = fslotlock.getChannel().tryLock();
               // IOException needs to be caught on Linux cifs networks
              try{ slotlock = fslotlock.getChannel().tryLock();} catch(IOException e){}
//startPR2010-03-18^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
              catch (OverlappingFileLockException e) {}
//endPRtoupdate^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2006-04-30^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
             }
//             if (slotlock != null) {
             if(isSlotLocked()){
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
               slotnum = i;
               break;
             }
             else {
//startPR2006-04-30^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
               if(fslotlock!=null){
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                 fslotlock.close();
                 fslotlock = null;
//startPR2006-04-30^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
               }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
             }
           }
           catch (IOException e) {
           }
         }
       }
//startPR2006-04-30^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//       if (slotlock != null) {
       if (isSlotLocked()) {
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         try {
           String compname = u.getComputerName();
           String s = (compname==null?"":compname+ "  ")
                + System.getProperty("user.name")
               + "  (";
           if(sharkStartFrame.studentList != null)
               for (j = 0; j < sharkStartFrame.studentList.length; ++j) {
             s += sharkStartFrame.studentList[j].name + ",";
           }
           s += stu.name + ")";
//startPR2009-08-02^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//           if(shark.macOS && shark.network){
//             // must close - otherwise sometimes stays around on macs
//             FileOutputStream fos = new FileOutputStream(sharkStartFrame.sharedPathplus + "user" + String.valueOf(slotnum+1) + ".nlock2");
//             fos.write(s.getBytes());
//             fos.close();
//           }
//           else{
//             new FileOutputStream(sharkStartFrame.sharedPathplus + "user" + String.valueOf(slotnum+1) + ".nlock2").write(s.getBytes());
//           }
             File f = new File(sharkStartFrame.sharedPathplus + "user" + String.valueOf(slotnum+1) + ".nlock2");
//startPR2010-03-15^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      if(shark.macOS && shark.network){
            if(MacLock.DOJNI){
//endPRtoupdate^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
               // must close - otherwise sometimes stays around on macs
               FileOutputStream fos = new FileOutputStream(f.getAbsolutePath());
               fos.write(s.getBytes());
               fos.close();
             }
             else{
               FileOutputStream ffod = new FileOutputStream(f.getAbsolutePath());
               ffod.write(s.getBytes());
               ffod.close();
             }
             u2_base.setNewFilePermissions(f);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         }
         catch (IOException e) {
         }
       }
       else {
         try {
//startPR2006-11-22^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//            String mm = u.edit(u.gettext(stu.administrator || stu.teacher ? "toomanyusers":"toomanyusers2","message"),
//                    new String[]{String.valueOf(sharkStartFrame.users),sharkStartFrame.school,sharkStartFrame.serial});
//startPR2008-10-08^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//           if (mess)
//             new userLimitWarning(sharkStartFrame.mainFrame);
           if(mess){
             if(shark.network) {
               new userLimitWarning(sharkStartFrame.mainFrame);
             }
             else{
//startPR2009-05-12^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//               u.okmess(shark.programName, u.gettext("errorcodes", "errorcode12"), sharkStartFrame.mainFrame);
//               u.okmess(shark.programName, u.gettext("errorcodes", "errorcode12", shark.programName), sharkStartFrame.mainFrame);
               OptionPane_base.getErrorMessageDialog(sharkStartFrame.mainFrame, 12, u.gettext("errorcodes", "errorcode12", shark.programName), OptionPane_base.ERRORTYPE_EXIT);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
             }
           }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//           else lockmess2 = mm;
           else lockmess2 = true;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2006-04-30^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
           //  to unlock and delete the lock file that shows that the user is signed on
//startPR2010-03-15^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      if(shark.macOS && shark.network){
           if(MacLock.DOJNI){
//endPRtoupdate^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
             stu.macLocked = false;
             MacLock.delete(sharkStartFrame.sharedPathplus + stu.name, ".lock");
           }
           else{
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
             stu.lock.release();
             stu.lock = null;
             stu.fflock.close();
             stu.fflock = null;
             new File(sharkStartFrame.sharedPathplus + stu.name + ".lock").delete();
//startPR2006-04-30^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
           }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         }
         catch (IOException e) {
         }
       }
//startPR2008-10-08^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//     }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   }
//startPR2006-04-30^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//   return stu.lock != null;
   return stu.isLocked();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
 }
//-------------------------------------------------------------
public static void quikSignon(String db1) {
   student stu = getStudent(db1);
   if(stu == null || !getlock(stu,db1,true)) return;
//startPR2008-09-21^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//   if(!doForwardsCompatibilityCheck(stu, sharkStartFrame.mainFrame))
//     return;
//   doForwardsCompatibilityCheck(stu, sharkStartFrame.mainFrame);   //2222
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   stu.doupdates(true,false);      // rb 27/10/06
   stu.addtolist();
   stu.finishSignon(true);
}
//startPR2008-09-21^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
// public static boolean doForwardsCompatibilityCheck(student stu, Object owner) {   //2222
//   u.focusBlock=true;  //2222
//   boolean res = true;  //2222
//   if(stu.programOverride){  //2222
//     res = false;  //2222
//     u.okmess(u.gettext("stuolderversion", "heading"),  //2222
//              u.edit(u.gettext("stuolderversion", "message"), new String[]{stu.name, shark.programName, stu.name}),  //2222
//              owner);   //2222
//   }  //2222
//   u.focusBlock=false;  //2222
//   return res;  //2222
// }  //2222
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
 //-------------------------------------------------------------
 public static boolean autoSignon(String db1) {    // at start of run, using user logged on to computer
     return autoSignon(db1, false);
 }
 
 public static boolean autoSignon(String db1, boolean allowTeachers) {    // at start of run, using user logged on to computer
    student stu = getStudent(db1);
    if(stu == null || (!ChangeScreenSize_base.isActive && (stu.administrator  || stu.teacher)) || stu.which == 'N' || !getlock(stu,db1,true)) return false;
//startPR2008-09-21^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    if(!doForwardsCompatibilityCheck(stu, sharkStartFrame.mainFrame))
//      return false;
//    doForwardsCompatibilityCheck(stu, sharkStartFrame.mainFrame);  //2222
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    stu.doupdates(true,false); // rb 27/10/06
    sharkStartFrame.mainFrame.buildgamepanel1();
    stu.addtolist();
    stu.finishSignon(true);
    return true;
 } 
 
  //-------------------------------------------------------------
//startPR2010-05-04^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
 public static boolean signOn(runMovers fishpanel1) {
     return signOn(fishpanel1, null);
 }

  public static boolean signOn(runMovers fishpanel1, String name) {
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     short i;
     short fishtot = (short) (5 + u.rand(8));
//startPR2010-05-04^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//     fishpanel=fishpanel1;
     fishpanel=(runMovers)fishpanel1;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     fishpanel.setFocusTraversalKeysEnabled(false);
//     fishpanel.setBackground(new Color(0,0,160));
     fishpanel.gradientBg = new java.awt.GradientPaint(
             0,
             0,
             new Color(89,89,255),
             sharkStartFrame.screenSize.width/6,
             sharkStartFrame.screenSize.height*10/8,
             new Color(0,0,50),
             true);

     fishpanel.clearWholeScreen = true;
     fishpanel.addMouseListener(new MouseAdapter() {
        public void mousePressed(MouseEvent me) {
            sharkStartFrame.mainFrame.requestFocusInWindow();
        }
     });
//startPR2007-12-05^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      if(shark.macOS)
//        sharkStartFrame.mainFrame.setFocusableWindowState(false);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//     String defkey;
//     kw=kh=0;
//     if((defkey = u.gettext("keypad_","signon")) != null && isAdministrator) {
//     if((defkey = u.gettext("keypad_","signon")) != null) {
//         keypad.keypadname = defkey;
//startPR2010-05-04^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//         keypad.activate(sharkStartFrame.mainFrame,new char[] {(char)keypad.SHIFT,' ',(char)keypad.BACKSPACE,(char)keypad.ENTER});
 ///          keypad.activate(sharkStartFrame.mainFrame,new char[] {(char)keypad.SHIFT,' ',(char)keypad.BACKSPACE,(char)keypad.ENTER}, keypad.BOTTOMLEFT);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//         kw = keypad.keypadwidth(sharkStartFrame.mainFrame);
//         kh = keypad.keypadheight(sharkStartFrame.mainFrame);
//     }
     gotstuname = null;
     fishtot = (short) (10 + u.rand(8));
     for(i=0;i<fishtot;++i) {
           simplefish sf = new simplefish(simplefish.TYPE_NORMAL);
           sf.setup(mover.WIDTH/10 + u.rand(mover.WIDTH/10) ,
              fishpanel, false);
           fishpanel.addMover(sf,sf.x,sf.y);
     }
     fishpanel.start1();
     fishpanel.freeze = false;
//startPR2010-05-04^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//     javax.swing.Timer timer = new javax.swing.Timer(1000,new ActionListener() {
//             public void actionPerformed(ActionEvent e) {
//                     while(fishpanel.screenwidth == 0) u.pause(200);
//                      inithelp = new sharkImageSay("help_initial",false);
//                      inithelp.cansay=true;
//                     inithelp.w  = mover.WIDTH/4;
//                      inithelp.h = mover.HEIGHT;
//                      inithelp.adjustSize(fishpanel.screenwidth, fishpanel.screenheight);
//                      fishpanel.addMover(inithelp,mover.WIDTH-inithelp.w,
//                                         Math.min(mover.HEIGHT*7/8,mover.HEIGHT-kh*mover.HEIGHT/fishpanel.screenheight) - inithelp.h);
                      // if running on a Macintosh
//                      if (shark.macOS)
//                        inithelp2 = new sharkImageSay("help_initial2_mac", false);
                        // if running on Windows
//                      else
//                        inithelp2 = new sharkImageSay("help_initial2", false);
//                      inithelp2.cansay=true;
//                      inithelp2.w  = mover.WIDTH/4;
//                      inithelp2.h = mover.HEIGHT/6;
//                      inithelp2.adjustSize(fishpanel.screenwidth, fishpanel.screenheight);
//                      fishpanel.addMover(inithelp2,0,0);
//                      int w2 = mover.WIDTH - kw*mover.WIDTH/fishpanel.screenwidth;
//                      if(sharkStartFrame.licence != null) {
//                        String ss = u.combineString(u.splitString(sharkStartFrame.licence,'\n'),"|");
//                        mover.simpletextmover mstm = new mover.simpletextmover(
//                            ss,
//                            w2,
//                            mover.HEIGHT / 8,
//                            Color.yellow);
//                            fishpanel.addMover(mstm,
//                                          kw*mover.WIDTH/fishpanel.screenwidth,
//                                           mover.HEIGHT * 7 / 8);
//                      }
//               }
//     });
//     timer.setRepeats(false);
//     timer.start();



     String defkey;
     kw=kh=0;
//     if((defkey = u.gettext("keypad_","signon")) != null && isAdministrator) {
     if((defkey = u.gettext("keypad_","signon")) != null) {
         keypad.keypadname = defkey;
//startPR2010-05-04^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//         keypad.activate(sharkStartFrame.mainFrame,new char[] {(char)keypad.SHIFT,' ',(char)keypad.BACKSPACE,(char)keypad.ENTER});
           keypad.activate(sharkStartFrame.mainFrame,new char[] {(char)keypad.SHIFT,' ',(char)keypad.BACKSPACE,(char)keypad.ENTER}, keypad.BOTTOMLEFT);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         kw = keypad.keypadwidth(sharkStartFrame.mainFrame);
         kh = keypad.keypadheight(sharkStartFrame.mainFrame);
     }


     javax.swing.Timer timer = new javax.swing.Timer(1000,new ActionListener() {
             public void actionPerformed(ActionEvent e) {
                 while(fishpanel.screenwidth == 0) u.pause(200);
                 boolean wantside = db.query(sharkStartFrame.optionsdb, "nosideprompt", db.TEXT) < 0;
                  String ss = null;
                  if(shark.licenceType.equals(shark.LICENCETYPE_NETWORK)){
                      if (sharkStartFrame.licence != null) {
                          ss = u.combineString(u.splitString(sharkStartFrame.licence, '\n'), "|");
                      }                      
                  }
                  else if (shark.licenceType.equals(shark.LICENCETYPE_NETWORKACTIVATION)){
                      if(sharkStartFrame.school!=null){
                            ss = u.edit(u.gettext("webauthenticate", "signonidentity"),
                                  new String[]{shark.programName + " " + shark.versionNo,
                                      sharkStartFrame.school, String.valueOf(sharkStartFrame.users)});
                      }                   
                  }
                  else if(shark.licenceType.equals(shark.LICENCETYPE_USB_HOME) || shark.singledownload){
                      ss = shark.getVersionType(shark.singledownload?shark.LICENCETYPE_STANDALONEACTIVATION_HOME:shark.licenceType, false);                     
                  }                  
                  else if(shark.licenceType.equals(shark.LICENCETYPE_STANDALONEACTIVATION) && !shark.singledownload){ 
                      String all = u.edit(u.gettext("webauthenticate", "signonidentity"),
                              new String[]{shark.programName + " " + shark.versionNo,
                                 sharkStartFrame.school, String.valueOf(sharkStartFrame.users)});
                      ss = all.substring(0, all.indexOf("|") + 1) + " ";
                  }      
                  SignOn_base sob = new SignOn_base();
                  sob.setup(true, signonname!=null, false, signonname, wantside);
                  if (ss != null) {
                      int w2 = mover.WIDTH - kw * mover.WIDTH / fishpanel.screenwidth;
                      int addx = 0;
                      if (wantside) {
                          w2 = w2 - SignOn_base.sidebarw;
                          int w3 = w2 * 19 / 20;
                          addx = (w2-w3)/2;
                          w2 = w3;
                      }
                      mover.simpletextmover mstm = new mover.simpletextmover(
                              ss,
                              sob.mainTBRect.width,
                              sob.mainTBRect.height,
                              Color.yellow);
                      fishpanel.addMover(mstm,
                              sob.mainTBRect.x,
                              mover.HEIGHT * 7 / 8);
                  }
                 
                 signonname = null;
               }
     });
     timer.setRepeats(false);
     timer.start();
     signonname = name;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     return true;
  }
  //-----------------------------------------------------------
  static void cancelSignon()  {
//startPR2006-04-30^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    if (newstu != null && newstu.lock != null) {
    if (newstu != null && newstu.isLocked()) {
//startPR2010-03-15^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      if(shark.macOS && shark.network){
      if(MacLock.DOJNI){
//endPRtoupdate^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        newstu.macLocked = false;
        MacLock.delete(sharkStartFrame.sharedPathplus + newstu.name, ".lock");
        MacLock.delete(sharkStartFrame.sharedPathplus + newstu.name, MacLock.LOCKEXTENSION);
      }
      else{
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        try {
          newstu.lock.release();
          newstu.lock = null;
          newstu.fflock.close();
          newstu.fflock = null;
          new File(sharkStartFrame.sharedPathplus + newstu.name + ".lock").delete();
        }
        catch (IOException e) {
        }
//startPR2006-04-30^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    }
      if(sharkStartFrame.studentList == null || sharkStartFrame.currStudent < 0) {
//        newstu = null;
//        gotstuname = null;
//startPR2010-05-04^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//             inithelp=null;
//        fishpanel.removeAllMovers();
//        signOn(fishpanel);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      }
      else {
        sharkStartFrame.mainFrame.setupgames();
        newstu = null;
        gotstuname = null;
        fishpanel = null;
//startPR2010-05-04^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//             inithelp=null;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        keypad.deactivate(sharkStartFrame.mainFrame);
      }
   }
   /**
    * @param newsign True if student has not signed on before and false if they have.
    */
   void finishSignon(boolean newsign)  {
       short i,j;
  //     wordlist.usedefinitions = true;
  //     wordlist.usetranslations = true;
       keypad.deactivate(sharkStartFrame.mainFrame);
       signonDate = new Date();
        if(studentrecord != null && newsign) {
            totscore=0;
        }
        if(spritename == null) {
           spritename = "x_aaa";
        }
        sharkStartFrame.currStudent = studentno();

        if(sharkStartFrame.currStudent >= 0)
            sharkStartFrame.resourcesdb = sharkStartFrame.resourcesPlus+
                sharkStartFrame.studentList[sharkStartFrame.currStudent].name +
                sharkStartFrame.resourcesFileSuffix;


        if(!sharkStartFrame.mainFrame.override()) {
//startPR2008-09-21^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          if(!administrator && !teacher &&  db.list(name, db.PROGRAM).length > 0)  // rb  21/1/08
//             if(!administrator && !teacher &&  db.list(name, db.PROGRAM).length > 0 && !programOverride)
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
               gameicons = sharkStartFrame.mainFrame.gameicons = true;              // rb  21/1/08
           else sharkStartFrame.mainFrame.gameicons = gameicons;                    // rb  21/1/08
           sharkStartFrame.mainFrame.setupgames();
           javax.swing.Timer timer = new javax.swing.Timer(200,new ActionListener() {
             public void actionPerformed(ActionEvent e) {
                Thread  showMessThread = new Thread(new showMessages_Work());
                showMessThread.start();
             }
           });
           timer.setRepeats(false);
           timer.start();
        }
        else {
          sharkStartFrame.mainFrame.setStudentMenu();
          sharkStartFrame.mainFrame.setupgames();
        }
        gotstuname = null;
        if(checkstu()) checkadmin(this);   // rb 6/2/06
        sharkStartFrame.settitles();
        sharkStartFrame.mainFrame.displayhometitle();
        
        
/*
            Image pimage = null;
            byte buf[];
            String s = "Alexander A Murphy";
            MediaTracker tracker=new MediaTracker(sharkStartFrame.mainFrame);
            if ( (buf = (byte[]) db.find(s + File.separator + s + PickPicture.dbsuffix, PickPicture.ownpic, db.PICTURE)) != null) {
               image = sharkStartFrame.t.createImage(buf);
               tracker.addImage(image,1);
            }
  //          else{
  //              image = sharkStartFrame.t.createImage(sharkStartFrame.publicPathplus + "sprites" +
  //                              File.separator + "anon.gif");
  //              anon = true;
  //              tracker.addImage(image,1);
  //          }
            try
            {
                tracker.waitForAll();
            }
            catch (InterruptedException ie)
            {
            }
            sharkStartFrame.mainFrame.setIconImage(image);

       String s4 = sharkStartFrame.studentList[sharkStartFrame.currStudent].name;
       String sp = "   ";
       sharkStartFrame.mainFrame.setTitle(s4 + sp + "using" + sp + "WORDSHARK 5.01"+ sp + sp + "-"+sp+sp+"icons for playing games" +sp+"-"+sp+"current word list");
  */
   }
   

  //--------------------------------------------------------------
  short studentno() {
     for(short i=0; i<sharkStartFrame.studentList.length;++i) {
        if(sharkStartFrame.studentList[i] == this) return i;
     }
     return -1;
  }
  //------------------------------------------------------------
  public static void signoffAll() {
     short i;
     if(sharkStartFrame.studentList == null) return;
     while(sharkStartFrame.studentList.length > 0) {
           sharkStartFrame.studentList[0].signoff(false);
     }
  }
//startPR2010-02-02^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  static simpleStu_base getSimpleStu(String dbase) {
     Object oo;
     oo = db.find(dbase, "student", db.STUDENT);
     simpleStu_base spl = new simpleStu_base();
     if(oo != null && oo instanceof savestudent) {
       savestudent ss = ((savestudent)oo);
       spl.type = 0;
       try{
           if(ss.administrator)spl.type = 1;
       }
       catch(Exception e){}
       try{
           if(shark.wantTeachers){
               student stu = new student();
               stu.options = ss.options;
               if(stu.option2("s_teacher")) {spl.type = 2;}
           }
       }
       catch(Exception e){}
       spl.name = dbase;
       spl.password = ss.password;
       spl.passwordhint = ss.passwordhint;
     }
     return spl;
  }
//endPRtoupdate^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR

  //-----------------------------------------------------------------------------------------
  static student getStudent(String dbase) {
     Object oo;
     oo = db.find(dbase, "student", db.STUDENT);
     if(oo != null && oo instanceof savestudent) {
       return getStudent((savestudent)oo);
     }
     return null;
  }
  static student getStudent(savestudent ss) {
        int i;
        String dbase = ss.name;
        student stu = new student();
        stu.isnew = false;
        stu.name = ss.name;
        stu.spritename = ss.spritename;
        stu.administrator = ss.administrator;
        stu.password = ss.password;
        stu.passwordhint = ss.passwordhint;
        stu.studentrecord = ss.studentrecord;
        stu.students = ss.students;
        stu.currTopic = ss.currTopic;
        stu.currTab = ss.currTab;
        stu.lastplayed = ss.lastplayed;
        stu.currGame = ss.currGame;
        stu.options = ss.options;
        stu.gameicons = ss.gameicons;
        stu.wantpics = ss.wantpics;
        stu.wantpicsgames = ss.wantpicsgames;
        stu.nospiders = ss.nospiders;
        stu.picbg = ss.picbg;
        stu.wantrealpics = !ss.notwantrealpics;
//startPR2008-06-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        stu.wantsignvids = ss.wantsignvids;
//endPRimagesandvids^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        stu.wantsigns = ss.wantsigns;
        stu.wantfingers = ss.wantfingers;
        int stuver = stu.stuRelativeVersion(ss.version, shark.versionNoDetailed);
        if(stuver>0) stu.isfromnewerversion = true;
        else if(stuver<0){
            u.updateUser(stu.name);
        }
        stu.version = ss.version;
        stu.which = ss.which;
        stu.sametopic = ss.sametopic;
             // these 3.7 attributes are stored in options
        String s;
        if(stu.option2("s_teacher")) {stu.teacher = true;}
        if(stu.option2("s_wantfingersall")) stu.wantfingersall = true;
        else stu.wantfingersall = ss.wantfingersall;
        if((s=stu.optionstring2("s_picpref"))!= null)
            stu.picprefs = u.splitString(s);
        else stu.picprefs = ss.picprefs;
        if((s=stu.optionstring2("s_workforteacher"))!= null)
            stu.workforteacher = s;
        if((s=stu.optionstring2("s_teachers"))!= null)
            stu.teachers = u.splitString(s);
        if((s=stu.optionstring2("s_besttime")) != null)
                stu.besttime = u.splitString(s);
        else    stu.besttime = ss.besttime;
        if((s = stu.optionstring2("s_excludegames")) != null)
              stu.excludegames = u.splitString(s);
        else
            stu.excludegames = ss.excludegames;
        if(stu.excludegames!=null && sharkStartFrame.rw!=null){
            String sa[] = settings.getAllExcludeGames(stu.excludegames);
            int okrew = 0;
            for(int i1 = 0; i1 < sharkStartFrame.rw.length; i1++){
                if(u.findString(sa, sharkStartFrame.rw[i1])<0){
                    okrew++;
                }
            }
            // remove reward games from excluded list because under 3 left
            if(okrew<3){
                for(int i2 = stu.excludegames.length-1; i2 >= 0; i2--){
                    if(u.findString(sharkStartFrame.rw, stu.excludegames[i2])>=0){
                       stu.excludegames = u.removeString(stu.excludegames, i2);
                    }
                }
                stu.saveStudent();
            }
        }
        if((stu.rewardfreq = (byte) stu.optionval2("s_rewardfreq")) < 0){
            if(shark.phonicshark)
                stu.rewardfreq = rewardfreqno;
            else
                stu.rewardfreq = ss.rewardfreq;
        }
        if((i = stu.optionint2("s_printfg")) >= 0)  stu.printfg = new Color(i);
        else   stu.printfg = ss.printfg;
        stu.hasimages = db.anyof(dbase,db.IMAGE);
        stu.hassay = db.anyof(dbase,db.WAV);
        stu.hastopics = db.anyof(dbase,db.TOPIC);
        if (shark.network) {
           db.close(dbase);
        }
//startPR2008-06-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//        if(ss.version == null || !ss.version.equals(shark.versionNo) || v4message)  {
        if(ss.version == null || !ss.version.equals(shark.versionNoDetailed) || v4message)  {
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          if(!skipv4chek) {
            stu.fixv4();
//startPR2008-09-21^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//            if(!stu.isfromnewerversion)
//            if(!stu.programOverride)    //2222
            if(stu.programOverride==null)   //3333
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2008-06-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//            stu.version = shark.versionNo;
              stu.version = shark.versionNoDetailed;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2008-08-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            if(stu.which != 'N')
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
              stu.which = 'W';                // rb 5/2/08
//startPR2008-06-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//            if(!v4message && (stu.administrator || stu.teacher))
            if(!v4message && (stu.administrator || stu.teacher) &&
               (ss.version==null||!(u.splitString(ss.version,'.')[0]).equals((u.splitString(stu.version,'.'))[0])))
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2006-08-21^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//            messages.sendMessage(stu.name,shark.programName+" "+shark.versionNo,u.gettext("newversion","message"));
            {
              if(db.query(stu.name, "newversion", db.MESSAGE) < 0)
                messages.sendMessage(stu.name, shark.programName+" "+shark.versionNo,
                                     u.gettext("newversion", "message"),
                                     "newversion");
            }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         }
//startPR2008-09-21^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//         if(!stu.isfromnewerversion)
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          stu.saveStudent();
        }
//startPR2008-11-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        if(stu.administrator || stu.teacher && sharkStartFrame.expiry!=null){
          try{
            String k = sharkStartFrame.expiry.replaceAll("/","");
            int cals[] = new int[]{Integer.parseInt(k.substring(4)),
                Integer.parseInt(k.substring(2,4)),
                Integer.parseInt(k.substring(0,2))};
            Calendar calexpiry = Calendar.getInstance();
            calexpiry.set(cals[0], cals[1]-1, cals[2]);
            long dif = calexpiry.getTimeInMillis() - Calendar.getInstance().getTimeInMillis();
            if(dif>0){
                calexpiry.setTimeInMillis(dif);
                int dfint = calexpiry.get(Calendar.DAY_OF_YEAR)-1;
                String stime = String.valueOf(dfint);
                if(dfint<23 && dfint>0 && !WebAuthenticate_base.doneExpiryWarn){
                    WebAuthenticate_base.doneExpiryWarn = true;
                  if(dfint==1)
                      messages.sendMessage(stu.name, shark.programName + " " + shark.versionNo, u.gettext("expirywarn", "mess_s", shark.programName), "expirywarn");
                  else
                      messages.sendMessage(stu.name, shark.programName + " " + shark.versionNo, u.edit(u.gettext("expirywarn", "mess_p"), shark.programName, stime), "expirywarn");
                }
            }
          }
          catch(Exception e){}
        }
//endPRauth^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        return stu;
  }
  /**
   * Saves a subset of the a student object's attributes
   * @return True if database is updated otherwise false
   */
    boolean saveStudent() {
      return saveStudent(name);
    }
    boolean saveStudent(String dbname) {
      //--------------------------------------------------------------------------------
             // save 3.7 options
        if(wantfingersall)   setOption2("s_wantfingersall");
        else clearOption2("s_wantfingersall");
        if(besttime != null) setOption2("s_besttime",u.combineString(besttime));
        if(excludegames != null)
            setOption2("s_excludegames",u.combineString(excludegames));
        else removeOption2("s_excludegames");
        if(picprefs != null)
            setOption2("s_picpref",u.combineString(picprefs));
        else removeOption2("s_picpref");
        if(!shark.phonicshark || rewardfreq!=3)
            setOption2("s_rewardfreq",rewardfreq);
        else
            removeOption2("s_rewardfreq");
        if(printfg != null) setOption2("s_printfg", printfg.getRGB());
        if(teacher) setOption2("s_teacher");
        else clearOption2("s_teacher");
           // end of 3.7 options
           // start rb 23/10/06
        if (workforteacher != null) setOption2("s_workforteacher", workforteacher);
        else    removeOption2("s_workforteacher");
        if(teachers != null)
            setOption2("s_teachers", u.combineString(teachers));
        else
            removeOption2("s_teachers");
           // end rb 23/10/06
        savestudent stu  = new savestudent();
        stu.name = stu.dbname = name;
        stu.spritename = spritename;
        stu.administrator = administrator;
        stu.password = password;
        stu.passwordhint = passwordhint;
        stu.lastplayed = lastplayed;
        stu.studentrecord = studentrecord;
        stu.students = students;
        stu.currTopic = currTopic;
        stu.currTab = currTab;
        stu.currGame = currGame;
        stu.options = options;
        stu.besttime = besttime;
//        stu.nospiders = nospiders;
        stu.excludegames = excludegames;
        stu.picprefs = picprefs;
        stu.rewardfreq = rewardfreq;
        stu.printfg = printfg;
        stu.gameicons = gameicons;
        stu.picbg = picbg;
        stu.nospiders = nospiders;
        stu.wantpics = wantpics;
        stu.wantpicsgames = wantpicsgames;
        stu.notwantrealpics = !wantrealpics;
//startPR2008-06-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        stu.wantsignvids = wantsignvids;
//endPRimagesandvids^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        stu.wantsigns = wantsigns;
        stu.wantfingers = wantfingers;
        stu.wantfingersall = wantfingersall;
        stu.version = version;
        stu.which = which;
        stu.sametopic = sametopic;
        //-------------------------------------- start rb 13/5/08 ---------v5------------------------
        //               if new month then monthly archive
        if(studentrecord.length ==1) addarchive();
        if(studentrecord.length>1) {
          String olddate  = new mySimpleDateFormat("yyyy-MM",studentrecord[0].timezone).format(studentrecord[0].date);
          String newdate  = new mySimpleDateFormat("yyyy-MM",studentrecord[studentrecord.length-1].timezone).format(studentrecord[studentrecord.length-1].date);
          if (!olddate.equals(newdate)) {
            File f = new File(sharkStartFrame.sharedPath, u.gettext("sturec_", "folder"));
            if (!f.exists())
              f.mkdir();
            addarchive();
            int i;
            for (i = 0; i < studentrecord.length; ++i) {
              olddate = new SimpleDateFormat("yyyy-MM").format(studentrecord[i].date);
              int oldstart = i;
              if(olddate.equals(newdate))
                  break;
              File f2 = new File(f, u.edit(u.gettext("sturec_", "file"), new String[]{name, shark.USBprefix, olddate}));
              try {
                ObjectOutputStream f3 = new ObjectOutputStream(new FileOutputStream(f2));
                for (; i < studentrecord.length; ++i) {
                  String newdate1 = new SimpleDateFormat("yyyy-MM").format(studentrecord[i].date);
                  if (!newdate1.equals(olddate)) {
                    topicPlayed tt[] = new topicPlayed[i-oldstart];
                    System.arraycopy(studentrecord,oldstart,tt,0,i-oldstart);
                    f3.writeObject(tt);
                    --i;
                    break;
                  }
                }
              } catch (IOException e) {
                return false;
              }
            }

            try{
//                topicPlayed tt[] = new topicPlayed[] {studentrecord[studentrecord.length - i]};
                topicPlayed tt[] = new topicPlayed[studentrecord.length - i];
                System.arraycopy(studentrecord,i,tt,0,tt.length);
                stu.studentrecord = studentrecord = tt;
            }
            catch(Exception rr){
                stu.studentrecord = studentrecord = new topicPlayed[] {};
            }
            db db1 = db.get(dbname, true); // ensure lock
            db1.delete("archive", db.VECTOR);
            delarchive = false;

            if (db.query(sharkStartFrame.optionsdb, "keepallrecords_", db.TEXT) < 0) {
              String months = (String)db.find(sharkStartFrame.optionsdb,"keeprecordsfor_", db.TEXT);
              int monthval = months==null ? 15 : Integer.parseInt(months);
              Calendar c = new GregorianCalendar();
              c.setTime(new Date());
              c.add(c.MONTH, -monthval);
              String earlydate = new SimpleDateFormat("yyyy-MM").format(c.getTime());
              String earlysave = u.edit(u.gettext("sturec_", "file"), new String[]{name, shark.USBprefix, earlydate});
              String earlystart = earlysave.substring(0, earlysave.length() - earlydate.length());
              File list[] = f.listFiles();
              for (i = 0; i < list.length; ++i) {
                if (!list[i].getName().endsWith(arcend)
                       && list[i].getName().startsWith(earlystart) && list[i].getName().compareTo(earlysave) < 0)
                  list[i].delete();
              }
            }
          }
        }
        //-------------------------------------end   rb 13/5/08 ---------v5------------------------
        //-------------------------------------- rb 21/2/08 ---- v5 ----------------------
        //               if more than ARCHIVEAFTER student records then archive them
        if( studentrecord.length > ARCHIVEAFTER || delarchive) {
             int start,end;
             if(delarchive) {   // this is set if a session was deleted
               addarchive();
               db db1 = db.get(dbname, true); // ensure lock
               db1.delete("archive", db.VECTOR);
               delarchive = false;
             }
             for(end = studentrecord.length-1; end >= 0 && studentrecord[end].dateoff == null;--end);
             if(++end>0) {    // check there is a finished session to save
               db db1 = db.get(dbname, true); // ensure lock
               topicPlayed sr[] = (topicPlayed[]) db1.find("archive", db.VECTOR);
               if(sr == null) {
                 sr = new topicPlayed[0];    // no archive yet
                 start  = 0;
               }
               else {
                 Date enddate = sr[sr.length-1].date;  // remove overlap
                 for(start = 0; start < end && studentrecord[start].date.compareTo(enddate) <= 0;++start);
               }
               if(end > start) {                                               // check if already all archived
                 topicPlayed sr2[] = new topicPlayed[sr.length + end-start];   // no - add new records
                 System.arraycopy(sr, 0, sr2, 0, sr.length);
                 System.arraycopy(studentrecord, start, sr2, sr.length, end-start);
                 while(sr2.length > MAXSTUDENTRECORD) {  // reduce in size if necessary
                    for(start = 1; start<sr2.length && sr2[start].dateoff.equals(sr2[0].dateoff);++start);
                    topicPlayed sr3[] = new topicPlayed[sr.length -start];
                    System.arraycopy(sr2, start, sr3, 0, sr.length - start);
                    sr2 = sr3;
                 }
                 db1.update("archive", sr2, db.VECTOR);
               }
               topicPlayed sr3[] = new topicPlayed[studentrecord.length - end]; // reduce save
               System.arraycopy(studentrecord, end, sr3, 0, studentrecord.length - end);
               stu.studentrecord = sr3;
             }
        }
        //---------------------------------------------------------------------------------------
        return db.update(dbname, "student", stu, db.STUDENT);
  }
  //-------------------------------------- rb 21/2/08 ---- v5 ----------------------
  void addarchive() {
    if(addedarchive) return;
    addedarchive = true;
    topicPlayed sr[] = (topicPlayed[]) db.find(name,"archive", db.VECTOR);
    if(sr == null || sr.length == 0) return;
    int start, end = studentrecord.length;
    Date enddate = sr[sr.length-1].date;  // remove overlap
    for(start = 0; start < studentrecord.length && studentrecord[start].date.compareTo(enddate) <= 0;++start);
    if(end > start) {                                               // check if already all archived
      topicPlayed sr2[] = new topicPlayed[sr.length + end - start];
      System.arraycopy(sr, 0, sr2, 0, sr.length);
      System.arraycopy(studentrecord, start, sr2, sr.length, end - start);
      studentrecord = sr2;
    }
    else studentrecord = sr;
  }
  
  static topicPlayed[] addarchive(String stud, topicPlayed currrec[]) {
 //   if(addedarchive) return;
 //   addedarchive = true;
    topicPlayed sr[] = (topicPlayed[]) db.find(stud,"archive", db.VECTOR);
    if(sr == null || sr.length == 0) return currrec;
    int start, end = currrec.length;
    Date enddate = sr[sr.length-1].date;  // remove overlap
    for(start = 0; start < currrec.length && currrec[start].date.compareTo(enddate) <= 0;++start);
    if(end > start) {                                               // check if already all archived
      topicPlayed sr2[] = new topicPlayed[sr.length + end - start];
      System.arraycopy(sr, 0, sr2, 0, sr.length);
      System.arraycopy(currrec, start, sr2, sr.length, end - start);
      currrec = sr2;
    }
    else currrec = sr;
    return currrec;
  } 
  
  //------------------------------------------------------------
            //  test if games option exists for current student
  public static boolean option(String name) {
    return  sharkStartFrame.studentList[sharkStartFrame.currStudent].option2(name);
  }
  boolean option2(String name) {
     if(options == null) return false;
     short i;
     for(i = 0;i<options.length;++i) {
        if(options[i].equals(name)) return true;
     }
     return false;

  }
   //------------------------------------------------------------
            //  return value of games option for current student
  public static short optionval(String name) {
    return sharkStartFrame.studentList[sharkStartFrame.currStudent].optionval2(name);
  }
  short optionval2(String name) {
    if(options == null) return -1;
     short i;
     String namep = name+"=";
     short len = (short)namep.length();

     for(i = 0;i<options.length;++i) {
        if(options[i].startsWith(namep))
           return Short.valueOf(options[i].substring(len)).shortValue();
     }
     return -1;
  }
  //------------------------------------------------------------
           //  return value of games option for current student
 public static int optionint(String name) {
   return sharkStartFrame.studentList[sharkStartFrame.currStudent].optionint2(name);
 }
 int optionint2(String name) {
    if(options == null) return -1;
    short i;
    String namep = name+"=";
    short len = (short)namep.length();

    for(i = 0;i<options.length;++i) {
       if(options[i].startsWith(namep))
          return Integer.parseInt(options[i].substring(len));
    }
    return -1;
 }
   //------------------------------------------------------------
            //  return value of games option for current student
  public static String optionstring(String name) {
     return sharkStartFrame.studentList[sharkStartFrame.currStudent].optionstring2(name);
  }
 String optionstring2(String name) {
     if(options == null) return null;
     short i;
     String namep = name+"=";
     short len = (short)namep.length();

     for(i = 0;i<options.length;++i) {
        if(options[i].startsWith(namep))
           return options[i].substring(len);
     }
     return null;
  }
  //------------------------------------------------------------
  public static void setOption(String name) {
    sharkStartFrame.studentList[sharkStartFrame.currStudent]. setOption2(name);
  }
  void setOption2(String name) {
     if(options == null)
          options = new String[] {name};
     else if(!option2(name))
         options = u.addString(options, name);
 }
 //--------------------------------------------------------------------
 static int getBestTime(String gamename,String topicname) {
    int ret;
    String s= gamename + "=";
    student stu = sharkStartFrame.studentList[sharkStartFrame.currStudent];
    if(stu.besttime==null || !stu.besttime[0].equalsIgnoreCase(topicname)) {
      stu.besttime = new String[]{topicname};
       return -1;
    }
    for(int i=1;i<stu.besttime.length;++i) {
        if(stu.besttime[i].startsWith(s)) return Integer.parseInt(stu.besttime[i].substring(s.length()));
    }
    return -1;
 }
 //--------------------------------------------------------------------
 static void setBestTime(String gamename,String topicname,int time) {
    String s= gamename + "=";
    student stu = sharkStartFrame.studentList[sharkStartFrame.currStudent];
    if(stu.besttime==null || !stu.besttime[0].equalsIgnoreCase(topicname)) {
      stu.besttime = new String[]{topicname};
    }
    for(int i=1;i<stu.besttime.length;++i) {
        if(stu.besttime[i].startsWith(s)) {
          int j = Integer.parseInt(stu.besttime[i].substring(s.length()));
          if(time<j) stu.besttime[i] = s + String.valueOf(time);
        }
    }
    stu.besttime = u.addString(stu.besttime, s + String.valueOf(time));
 }
 //--------------------------------------------------------------------
 static boolean banned(String gamename) {
   student stu = sharkStartFrame.studentList[sharkStartFrame.currStudent];
    String ss[] = stu.excludegames;
    ss = settings.getAllExcludeGames(ss);
    if(ss == null) return false;
    if(u.findString(ss,gamename) >= 0) return true;
    jnode node =sharkStartFrame.mainFrame.publicGameTree.find(gamename);
    if(node != null && u.findString(ss,((jnode)node.getParent()).get()) >= 0) return true;
    return false;
 }
 //--------------------------------------------------------------------
 static Color printfg() {
    student stu = sharkStartFrame.studentList[sharkStartFrame.currStudent];
    if(stu.printfg == null) return Color.black;
    else return stu.printfg;
 }
  //------------------------------------------------------------
  public static void clearOption(String name) {
    sharkStartFrame.studentList[sharkStartFrame.currStudent].clearOption2(name);
  }
  void clearOption2(String name) {
     if(option2(name)) {
        int len = options.length;
        for(short i = 0;i<len;++i) {
           if(options[i].equals(name)) {
              String s[] = new String[len-1];
              System.arraycopy(options,0,s,0,i);
              System.arraycopy(options,i+1,s,i,len-1-i);
              options = s;
              break;
           }
        }
     }
  }


//------------------------------------------------------------
 public static void setOption(String name,boolean bool ) {
   if(bool) setOption(name); else clearOption(name);
 }
  //------------------------------------------------------------
  public static void setOption(String name, int value) {
    sharkStartFrame.studentList[sharkStartFrame.currStudent].setOption2( name, value);
  }
  void setOption2(String name, int value) {
     String namep = name + "=" + String.valueOf(value);
     int op = -1;
     if(options == null)
          options = new String[] {namep};
     else if((op = optionint2(name))==-1)
           options = u.addString(options, namep);
     else if(op != value) {
        String namee = name + "=";
        short len = (short)namee.length();
        for(short i = 0;i<options.length;++i) {
           if(options[i].startsWith(namee)) {
             options[i] = namep;
              return;
           }
        }
     }
  }
  //------------------------------------------------------------
  public static void setOption(String name, short value) {
    sharkStartFrame.studentList[sharkStartFrame.currStudent].setOption2( name, value);
  }
  void setOption2(String name, short value) {
     String namep = name + "=" + String.valueOf(value);
     short op = -1;
     if(options == null)
          options = new String[] {namep};
     else if((op = optionval2(name))==-1)
           options = u.addString(options, namep);
     else if(op != value) {
        String namee = name + "=";
        short len = (short)namee.length();
        for(short i = 0;i<options.length;++i) {
           if(options[i].startsWith(namee)) {
             options[i] = namep;
              return;
           }
        }
     }
  }
  //------------------------------------------------------------
  public static void removeOption(String name) {
    sharkStartFrame.studentList[sharkStartFrame.currStudent].removeOption2(name);
  }
  void removeOption2(String name) {
     String namee = name + "=";
     String op;
     if(options == null) return;
     int len = options.length;
     for(short i = 0;i<len;++i) {
        if(options[i].startsWith(namee))  {
              String s[] = new String[len-1];
              System.arraycopy(options,0,s,0,i);
              System.arraycopy(options,i+1,s,i,len-1-i);
              options = s;
              break;
        }
     }
  }
  //------------------------------------------------------------
  public static void setOption(String name, String value) {
      sharkStartFrame.studentList[sharkStartFrame.currStudent].setOption2(name, value);
  }
  void setOption2(String name, String value) {
     String namep = name + "=" + value;
     String op;
     if(options == null)
          options = new String[] {namep};
     else if((op = optionstring2(name))==null)
           options = u.addString(options, namep);
     else if(!op.equals(value)) {
        String namee = name + "=";
        short len = (short)namee.length();
        for(short i = 0;i<options.length;++i) {
           if(options[i].startsWith(namee)) {
              options[i] = namep;
              return;
           }
        }
     }
  }
  //--------------------------------------------------------------------
  static byte getSpeed(String gamename) {
     short ret;
     if((ret =  optionval(gamename+"_speed")) >= 1) return (byte)ret;
     else return 5;
  }
  //--------------------------------------------------------------------
  static void setSpeed(String gamename,byte val) {
     setOption(gamename+"_speed",(short)val);
  }
  //-------------------------------------------------------------------------
  boolean doupdates(boolean update, boolean force) {
    if(isnew)
        return false;
    Object o;
    String s[];
    boolean ret = false;
    db db1 = db.get(name,true);
    String list[] = db1.list(db.TEXT,"_refresh");
    if(list==null || list.length==0) {
      if(update && force)
          saveStudent();
      else db1.close();
      return false;
    }
        // make sure current stu is up to date
    for(int i=0;i<list.length;++i) {
         if((o = db1.find(list[i],db.TEXT)) != null && o instanceof String[]) {
             s = (String[])o;
             if(s[0].equals("noreward")) {    //  start rb 27/10/06
                 setOption2("noreward");
                 ret = true;
              }
             else if(s[0].equals("rewardfreq")) {
               rewardfreq  =  (byte)Integer.parseInt(s[1]);
               setOption2("s_rewardfreq",rewardfreq);
               clearOption2("noreward");
               ret = true;
             }
             else if(s[0].equals("okrewards")) {
               if(s.length == 1)
                     removeOption2("s_okrewards");
               else setOption2("s_okrewards", s[1]);
               ret = true;
             }
             else if(s[0].equals("excludeallrewardgames")) {
               if(s.length ==  1) clearOption2("s_excludeallrewardgames");
               else setOption2("s_excludeallrewardgames");
               ret = true;
             }
             else if(s[0].equals("mteachnotes")) {
               if(s.length !=  1) clearOption2("noteachnotes");
               else setOption2("noteachnotes");
               ret = true;
             }
             else if(s[0].equals("nopeep")) {
               if(s.length ==  1) clearOption2("s_nopeep");
               else setOption2("s_nopeep");
               ret = true;
             }
             else if(s[0].equals("nogroan")) {
               if(s.length ==  1) clearOption2("s_nogroan");
               else setOption2("s_nogroan");
               ret = true;
             }
//startPR2008-06-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//             else if(s[0].equals("mwantsignvids")) {
//               wantsignvids = s.length>1;
//               ret = true;
//             }
//endPRimagesandvids^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
             else if(s[0].equals("mwantsigns")) {
               wantsigns = s.length>1;
               ret = true;
             }
             else if(s[0].equals("mwantrealpics")) {
               wantrealpics = s.length>1;
               ret = true;
             }
             else if(s[0].equals("mwantfingers")) {
               wantfingersall = s.length>1;
               if(wantfingersall)   setOption2("s_wantfingersall");
               else clearOption2("s_wantfingersall");
               ret = true;
             }
             else if(s[0].equals("mpicpref")) {
                if(s.length>1) {
                   picprefs = u.removeString(s,0);
                 }
                 else {
                   picprefs = null;
                 }
               if(picprefs!=null)
                   setOption2("s_picpref", u.combineString(picprefs));
               else
                   removeOption2("s_picpref");
               ret = true;
             }
//             else if(s[0].equals("courses")) {
//               if(s.length ==  1) removeOption2("s_courses");
//               else setOption2("s_courses");
//               ret = true;
//             }
             else if(s[0].equals("courses")) {
                 if(s.length<2)setOption2("s_courses", "");
                 else
               setOption2("s_courses", s[1]);
               ret = true;
             }

             else if(s[0].equals("beepvol")) {
              setOption2("s_beepvol",s[1]);
              byte bv = (byte)student.optionval("s_beepvol");
              if (bv >= 0) noise.beepvol = bv;
              noise.setnew();
              ret = true;
             }
             else if(s[0].equals("wordfont")) {
               if(s.length ==  1) removeOption2("s_wordfont");
               else setOption2("s_wordfont",s[1]);
               ret = true;
             }
             else if(s[0].equals("treefont")) {
               if(s.length ==  1) removeOption2("s_treefont");
               else setOption2("s_treefont",s[1]);
               ret = true;
             }
             else if(s[0].equals("workforteacher")) {   // start rb 23/10/06
               if(teachers != null && teachers.length > 1 && (workforteacher==null || !workforteacher.equals(s[1]))
                     && u.findString(teachers,s[1])>=0)  {
                  workforteacher = s[1];
                  ret = true;
               }
             }   // end rb 23/10/06
             else if(s[0].equals("keypad")) {   // start rb 27/10/06
                if(s.length > 1) {
                  keypad.keypadname = s[1];
                  setOption2("keypad", s[1]);
               }
                else {
                  removeOption2("keypad");
                }
                 ret = true;
             }
             else if(s[0].equals("password")) {
                 if(s.length>1) {
                   password= encrypt(s[1]);
                   }
                  else {
                    password = null;
                  }
                  ret = true;
             }
             else if(s[0].equals("passwordhint")) {
                 if(s.length>1) {
                   passwordhint=s[1];
                   }
                  else {
                    passwordhint = null;
                  }
                  ret = true;
             }
             else if(s[0].equals("bgcolor")) {
                if(s.length>1) {
                   setOption2("bgcolor",s[1]);
                 }
                 else {
                   removeOption2("bgcolor");
                 }
                 ret = true;
             }
             else if(s[0].equals("delsturec")) {
               addarchive();                            // rb 22/02/08  v5
               if(s[1].equals("all")) {
                 studentrecord = new topicPlayed[0];
                }
                else {
                   int tot=0;
                   for(int j=0;j<studentrecord.length;++j) {
                    if(u.findString(s, studentrecord[j].date.toString()) < 0) {
                       studentrecord[tot++] = studentrecord[j];
                    }
                  }
                  if(tot<studentrecord.length) {
                     topicPlayed sr[] = new topicPlayed[tot];
                     System.arraycopy(studentrecord,0,sr,0,tot);
                     studentrecord = sr;
                  }
                }
                ret = true;
                delarchive = true;           // rb 22/2/08   v5
            }
            else if(s[0].equals("excludegames")) {
                if(s.length>1) {
                   excludegames = u.removeString(s,0);
                 }
                 else {
                   excludegames = null;
                 }
                 if(excludegames!=null)   setOption2("s_excludegames", u.combineString(excludegames));
                 else removeOption2("s_excludegames");
                 ret = true;
             }                                 // end rb 27/10/06
          }
          if(update) db1.delete(list[i],db.TEXT);
      }
      if(update && (ret||force)) {
//        savestudent copystu = (savestudent) db1.find("student", db.STUDENT);
//        if (copystu == null)
//          return false;
//        excludegames = copystu.excludegames; // these may have been updated by admin
//        password = copystu.password;
//        studentrecord = copystu.studentrecord;
        saveStudent(); // this will close and unlock student database
      }
      else db1.close();
      return true;  // must return true - we also came here if work was assigned
  }

  //-----------------------------------------------------------------
  static class adminlist
//startPR2004-08-27^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      extends JDialog {
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   public int screenwidth;
   public int screenheight;
   BorderLayout layout1 = new BorderLayout();
   JList jj = new JList();
   String js[];
//startPR2008-08-05^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   JDialog thisadminlist = this;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR

//startPR2004-10-20^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   public adminlist(JFrame owner){
     super(owner);
     addConstructor();
   }
   public adminlist(JDialog owner){
     super(owner);
     addConstructor();
   }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR

//startPR2004-07-30^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   private void addConstructor(){
//startPR2008-06-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     u.focusBlock = true;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2004-08-27^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    this.setFocusableWindowState(false);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//     setBounds(new Rectangle(sharkStartFrame.screenSize.width/12,
//          sharkStartFrame.screenSize.height/8,
//          sharkStartFrame.screenSize.width/3,
//          sharkStartFrame.screenSize.height*3/4));
        setBounds(u2_base.adjustBounds(new Rectangle(sharkStartFrame.screenSize.width/12,
          sharkStartFrame.screenSize.height/8,
          sharkStartFrame.screenSize.width/3,
          sharkStartFrame.screenSize.height*3/4)));
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    this.addWindowListener(new java.awt.event.WindowAdapter() {
        public void windowDeactivated(WindowEvent e) {
           if(sharkStartFrame.currStudent>=0) dispose();
        }
//startPR2004-10-20^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        public void windowClosed(WindowEvent e) {
          if(sharkStartFrame.currStudent<0)
            // prevents exiting via the escape key from the sign on dialog
            u.blockEscape = true;
//startPR2007-09-03^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//            // allows focus back on the sign on dialog
//          u.focusBlock = false;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     });
//startPR2008-08-05^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     jj.addListSelectionListener(new ListSelectionListener(){
       public void valueChanged (ListSelectionEvent e){
         String s = String.valueOf(jj.getSelectedValue());
         if(s.trim().equals("")){
             jj.clearSelection();
             return;
         }
         student stu = getStudent(s);
         if(stu==null)return;
         student stu2;
         if(sharkStartFrame.studentList!=null &&
            sharkStartFrame.currStudent >= 0 &&
            sharkStartFrame.studentList.length>sharkStartFrame.currStudent &&
            sharkStartFrame.studentList[sharkStartFrame.currStudent].name.equals(stu.name)){
             thisadminlist.dispose();
             return;
         }
         else{
             if((stu2=stu.getSignedOn())!=null){
                  sharkStartFrame.mainFrame.savesplitwords();
                  stu2.finishSignon(false); // not real sign-on
             }
             else{
               thisadminlist.dispose();
               sharkStartFrame.mainFrame.dosignon(s);
             }
         }
         
/*
         if(student.newstu!=null && student.newstu.name.equals(stu.name)){
             thisadminlist.dispose();
             return;
         }
         if(!stu.isSignedOn()){
           thisadminlist.dispose();
           sharkStartFrame.mainFrame.dosignon(s);
         }
 *
 */
       }
     });
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2004-09-01^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     // enables exiting screen via the ESC key
     jj.addKeyListener(new KeyAdapter() {
       public void keyPressed(KeyEvent e) {
         int code = e.getKeyCode();
         if(code == KeyEvent.VK_ESCAPE)
           dispose();
       }
     });
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    this.getContentPane().setLayout(layout1);
    this.setEnabled(true);
    this.setTitle(u.gettext("madminlist","title"));
    buildadmins();
    this.getContentPane().add(new JScrollPane(jj),BorderLayout.CENTER);
    setVisible(true);
    validate();
   }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2008-06-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   public void dispose(){
     u.focusBlock = false;
     super.dispose();
   }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR

   void buildadmins() {
    int i;
    student stu;
    getadmin();                                           // refresh admin list
    if(adminlist.length==0 && teacherlist.length==0) jj.setListData(new String[] {u.gettext("madminlist","none")});
    else {
      String ll[] = new String[0];
      if (adminlist.length > 0) {
        if(teacherlist.length>0) ll = u.addString(ll, u.gettext("madminlist", "admin"));
        ll = u.addString(ll,adminlist);
        if (teacherlist.length > 0) {
          ll = u.addString(ll, u.gettext("madminlist", "teachers"));
          ll = u.addString(ll, teacherlist);
        }
        // to stop clicks below the admin at the bottom of the list selecting the
        // admin at the bottom of the list.
        jj.setListData( u.addString(ll, " "));
      }
    }
   }
  }
  //-----------------------------------------------------------------
  
  public class showMessages_Work implements Runnable{
      
      int MONTHREMINDERDELAY = 12;
      
      public void run(){
        if(firstEverRun){
                 new TutorialChoice_base(sharkStartFrame.mainFrame,
                  new String[]{u.gettext("videotutorials", "gettingstarted", shark.programName)},
                  new String[]{u.gettext("videotutorials", "basesite", shark.getProgramShortName()) + u.gettext("mvidgettingstarted", "url")},
                       true, true);
                 firstEverRun = false;
        }
        else if(shark.patching && (administrator && !teacher) && !patchNotified){
            /*
            String httpaddress = u.gettext("webauthenticate", "httpaddress");
            String str_serviceaccess = u.gettext("webauthenticate", "serviceaccess");        
            WebCall_base wc;
            Thread aThread = new Thread(
                  wc = new WebCall_base(httpaddress, WebAuthenticate_base.servicePatch, "Service1.asmx",
                  str_serviceaccess,
                  "GetPatchNo",
                  new String[]{"currno", "ver", "type"},
                  new String[]{shark.versionNoDetailed,
                  shark.ACTIVATE_PREFIX, shark.licenceType}, 2000));*/
            
            String httpaddress = u.gettext("webauthenticate", "httpaddress");
            String str_serviceaccess = u.gettext("webauthenticate", "serviceaccess");
            WebCall_base wc;
            String methods[];
            String values[];
            String methodname;
            if(shark.licenceType.equals(shark.LICENCETYPE_NETWORKACTIVATION)){
                methodname = "GetPatchNoNetType";
                String networkExtra = (shark.network_serverside?"Server":"Client")+(shark.network_RM?"RM":"");
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
            Thread aThread = new Thread(
                  wc = new WebCall_base(httpaddress, WebAuthenticate_base.servicePatch, "Service1.asmx",
                  str_serviceaccess,
                  methodname,
                  methods,
                  values, 2000));            
            aThread.start();
            while(aThread.isAlive()){
                u.pause(200);
            }
            if(wc.returnval!=null){
                // "2" for critical patch available.
                if(wc.returnval.equals("2") && db.query(sharkStartFrame.optionsdb, "critcalpatchnoask", db.TEXT) < 0){  
                    patchNotified = true;
                    OptionPane_base.doPatch(sharkStartFrame.mainFrame, OptionPane_base.PATCH_CRITICAL);
                }
                // "1" for patch available, 
                else if (wc.returnval.equals("1") && db.query(sharkStartFrame.optionsdb, "reminderpatchnoask", db.TEXT) < 0){
                    // check for overdue patch notification
                    Calendar cnow = Calendar.getInstance();
                    Calendar c = Calendar.getInstance();
                    boolean sameversiontoolong = false;
                    boolean toorecentlyreminded = false;
                    String sl[] = (String[])db.find(sharkStartFrame.optionsdb, "startofcurrentversion", db.TEXT);
                    if(sl!=null){
                        c.setTimeInMillis(Long.parseLong(sl[0]));
                        c.add(Calendar.MONTH, MONTHREMINDERDELAY);
                        sameversiontoolong = cnow.after(c);
                    }
                    sl = (String[])db.find(sharkStartFrame.optionsdb, "lastpatchreminderdate", db.TEXT);
                    if(sl!=null){
                        c.setTimeInMillis(Long.parseLong(sl[0]));
                        c.add(Calendar.MONTH, MONTHREMINDERDELAY);
                        toorecentlyreminded = cnow.before(c);
                    }
                    if(sameversiontoolong && !toorecentlyreminded){
                        patchNotified = true;
                        OptionPane_base.doPatch(sharkStartFrame.mainFrame, OptionPane_base.PATCH_REMINDER);
                    }   
                }
            }
        }
        if(shark.licenceType.equals(shark.LICENCETYPE_NETWORKACTIVATION) && administrator &&
              WebAuthenticateNetwork_base.overrideState == WebAuthenticateNetwork_base.OVERRIDESTATE_ALERT_RED){
//                u.okmess(shark.programName, u.gettext("webauthnet", "overrideendwarning_adminpopup"), sharkStartFrame.mainFrame);
                 OptionPane_base.getErrorMessageDialog(sharkStartFrame.mainFrame, 98, u.gettext("webauthnet", "overrideendwarning_adminpopup"),
                     OptionPane_base.ERRORTYPE_NOEXIT); 
        }
        while (db.anyof(name, db.MESSAGE)) {
             new messages("get");
        }        
      }
  }  
  
  
  static class signonlist
//startPR2004-08-27^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      extends JDialog {
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   public int screenwidth;
   public int screenheight;
   BorderLayout layout1 = new BorderLayout();
   jnode root=new jnode(u.gettext("so","selstu"));
   DefaultTreeModel model = new DefaultTreeModel(root);
   JTree studenttc = new JTree(model);
   int i,j;
   student teacher = sharkStartFrame.studentList[sharkStartFrame.currStudent];
   signonlist thissignon = this;
   boolean signingon;
   public signonlist(){
//startPR2004-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     super(sharkStartFrame.mainFrame);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2004-08-27^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    this.setResizable(false);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     studenttc.setUI(new javax.swing.plaf.metal.MetalTreeUI());
     studenttc.putClientProperty("JTree.lineStyle","Angled");
//startPR2006-11-17^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     studenttc.setRowHeight(studenttc.getFontMetrics(studenttc.getFont()).getHeight());
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//     setBounds(new Rectangle(sharkStartFrame.screenSize.width/2,
//          sharkStartFrame.screenSize.height/8,
//          sharkStartFrame.screenSize.width/3,
//          sharkStartFrame.screenSize.height*3/4));
     setBounds(u2_base.adjustBounds(new Rectangle(sharkStartFrame.screenSize.width/2,
          sharkStartFrame.screenSize.height/8,
          sharkStartFrame.screenSize.width/3,
          sharkStartFrame.screenSize.height*3/4)));
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    if(teacher.students == null) {dispose(); return;}
    this.addWindowListener(new java.awt.event.WindowAdapter() {
      public void windowDeactivated(WindowEvent e) {
//startPR2008-07-16^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//         if(!signingon)
        if(!shark.macOS && !signingon)
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
           thissignon.dispose();
      }
      public void windowActivated(WindowEvent e) {
         signingon=false;
      }
    });
//startPR2004-09-01^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      // enables exiting screen via the ESC key
      studenttc.addKeyListener(new KeyAdapter() {
          public void keyPressed(KeyEvent e) {
              int code = e.getKeyCode();
              if(code == KeyEvent.VK_ESCAPE)
                dispose();
          }
      });
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    studenttc.setCellRenderer(new treepainter());
    root.setIcon(jnode.TEACHER);
    studenttc.setExpandsSelectedPaths(true);
//startPR2008-01-29^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    buildstutree(studenttc,root, teacher.students,true);
    buildstutree(studenttc,root, teacher.students,true, false);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    this.getContentPane().setLayout(layout1);
    this.setEnabled(true);
    this.setTitle(u.gettext("so","selstutitle"));
    this.getContentPane().add(new JScrollPane(studenttc),BorderLayout.CENTER);
    setVisible(true);
    if(!root.isLeaf()) {
       studenttc.setSelectionPath(new TreePath(((jnode)root.getChildAt(0)).getPath()));
    }
    validate();
//startPR2007-12-20^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    studenttc.clearSelection();
//    studenttc.addMouseListener(new MouseAdapter() {
//      public void mouseReleased(MouseEvent e) {
   studenttc.getSelectionModel().addTreeSelectionListener(new TreeSelectionListener() {
      public void valueChanged(TreeSelectionEvent e) {
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        jnode sel = (jnode)studenttc.getLastSelectedPathComponent(),node;
        if(sel != null && sel != root) {
           if(!sel.isLeaf()) {
              if(System.currentTimeMillis() > lastflip+600) {
                 TreePath p = new TreePath(sel.getPath());
                 if(studenttc.isExpanded(p))  studenttc.collapsePath(p);
                 else {
                   studenttc.expandPath(p);
                   studenttc.scrollPathToVisible(p.pathByAddingChild(sel.getFirstChild()));
                 }
              }
          }
           else {
              signingon = true;
              quikSignon(sel.get());
              node = (jnode)sel.getParent();
              model.removeNodeFromParent(sel);
              if(node != root && node.isLeaf())
                     model.removeNodeFromParent(node);
              if(root.isLeaf())  thissignon.dispose();
              else thissignon.setVisible(true);
           }
        }
      }
    });
    studenttc.addTreeExpansionListener(new TreeExpansionListener() {
         public void treeCollapsed(TreeExpansionEvent e) {lastflip = System.currentTimeMillis();}
         public void treeExpanded(TreeExpansionEvent e) {lastflip = System.currentTimeMillis();}
    });
   }
  }
//startPR2008-01-29^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//   static void buildstutree(JTree tree,jnode node, String[] s, boolean signon) {
      static void buildstutree(JTree tree,jnode node, String[] s, boolean signon, boolean showEmptyClasses) {
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    int i,j;
    if(s==null) return;
    String slist[] = db.dblistnames(sharkStartFrame.sharedPath);
    jnode addtonode=node;
    loop1: for(i=0;i<s.length;++i) {
       if(s[i] == null) continue;
       if(s[i].length()>0 && s[i].charAt(0) == topicTree.ISTOPIC.charAt(0)) {
          if(s[i].length() > 1) {
//startPR2008-01-29^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            if(s.length>i+1 && s[i+1].equals(topicTree.ISTOPIC) && !showEmptyClasses) continue;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            jnode newnode = new jnode(s[i].substring(1));
            newnode.setIcon(jnode.GROUP);
            newnode.color = sharkStartFrame.fastmodecolor;
            ((DefaultTreeModel)tree.getModel()).insertNodeInto(newnode,addtonode,addtonode.getChildCount());
            addtonode=newnode;
          }
          else addtonode = (jnode)addtonode.getParent();
          if(addtonode ==null) addtonode = node;   // rb 8/2/02
          continue;
       }
             // check not signed on
       if(signon) for(j=0;j<sharkStartFrame.studentList.length;++j) {
          if(s[i].equalsIgnoreCase(sharkStartFrame.studentList[j].name))
                continue loop1;
       }
       if(u.findString(slist,s[i])>= 0) {
          jnode newnode = new jnode(s[i]);
          newnode.setIcon(jnode.STUDENT);
          newnode.color = Color.black;
          ((DefaultTreeModel)tree.getModel()).insertNodeInto(newnode,addtonode,addtonode.getChildCount());
       }
    }
   }
   //--------------------------------------------------------------
//   static void seticon(jnode node,student stu) {
//      if(stu.group) {
//         node.setIcon(jnode.GROUP);
//         node.color = Color.red;
//      }
//      if(stu.teacher){
//        node.setIcon(jnode.SUBADMIN);
//      }
//      else if(stu.administrator) {
//        node.setIcon(jnode.TEACHER);
//      }
//      else {
//         node.setIcon(jnode.STUDENT);
//      }
//      node.color = Color.black;
//   }
//startPR2010-05-04^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  /*
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   static class signonpanel
//startPR2004-08-27^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     extends JDialog {
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     JPanel panel1=new JPanel();
     JPanel panel2=new JPanel();
     GridBagConstraints grid1 = new GridBagConstraints();
     JButton ok = u.Button("signon_ok");
     JButton cancel = u.Button("signon_cancel");
     JTextField in = new JTextField(20);
     JPasswordField pass = new JPasswordField();
     JLabel lab1 = new JLabel();
     mlabel_base lab2 = new mlabel_base("");
     boolean gotname;
//startPR2008-08-05^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//     signonpanel() {
       signonpanel(String namegiven) {
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2004-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        super(sharkStartFrame.mainFrame);
//startPR2007-11-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//        lab1.setFont(new Font(lab1.getFont().getName(),lab1.getFont().getStyle(),lab1.getFont().getSize()*3/2));
        lab1.setFont(lab1.getFont().deriveFont((float)lab1.getFont().getSize()*3/2));
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        ok.setFont(lab1.getFont());
        cancel.setFont(lab1.getFont());
        in.setFont(lab1.getFont());
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2004-08-27^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        this.setResizable(false);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
      setBounds(new Rectangle(sharkStartFrame.screenSize.width/4,
                              sharkStartFrame.screenSize.height*2/5,
                              sharkStartFrame.screenSize.width/2,
                              sharkStartFrame.screenSize.height/5));
      setTitle(u.gettext("so","sotitle"));
      this.getContentPane().setLayout(new GridBagLayout());
      panel1.setLayout(new GridBagLayout());
      panel2.setLayout(new GridBagLayout());
      lab1.setText(u.gettext("so","soname"));
      lab1.setForeground(Color.black);
      lab2.setForeground(Color.red);
      grid1.fill = GridBagConstraints.NONE;
      grid1.gridx = 0;
      grid1.gridy = -1;
      grid1.weightx = 1;
      grid1.weighty = 1;
      panel1.add(lab1,grid1);
      panel1.add(in,grid1);
      panel1.add(lab2,grid1);
      grid1.gridx = -1;
      grid1.gridy = 0;
      grid1.weightx = 1;
      grid1.weighty = 1;
      grid1.fill = GridBagConstraints.NONE;
//startPR2005-01-28^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      if(shark.macOS){
        panel2.add(cancel, grid1);
        panel2.add(ok, grid1);
      }
      else{
        panel2.add(ok, grid1);
        panel2.add(cancel, grid1);
      }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      grid1.gridx = 0;
      grid1.gridy = -1;
      grid1.weightx = 1;
      grid1.weighty = 10;
      grid1.fill = GridBagConstraints.BOTH;
      this.getContentPane().add(panel1,grid1);
      grid1.weighty = 1;
      this.getContentPane().add(panel2,grid1);
//startPR2005-10-16^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      if(shark.macOS)
        panel2.getRootPane().setDefaultButton(ok);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      in.setColumns(20);
      in.setMinimumSize(in.getPreferredSize());
      in.requestFocus();
      validate();
      setVisible(true);

      ok.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               okorenter();
//startPR2004-10-21^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
               u.blockEscape = false;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          }
      });
      in.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               okorenter();
//startPR2004-10-21^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
               u.blockEscape = false;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          }
      });
//startPR2004-09-01^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      // enables exiting screen via the ESC key
      in.addKeyListener(new KeyAdapter() {
        public void keyPressed(KeyEvent e) {
          int code = e.getKeyCode();
          if(code == KeyEvent.VK_ESCAPE){
            cancelSignon();
            dispose();
          }
        }
      });
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      pass.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               okorenter();
//startPR2004-10-21^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
               u.blockEscape = false;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          }
      });
      cancel.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
              cancelSignon();
              dispose();
           }
      });
      addKeyListener(new KeyAdapter() {
          public void keyPressed(KeyEvent e) {
              int code = e.getKeyCode();
              if(code == KeyEvent.VK_ESCAPE) {cancelSignon();dispose();}
          }
      });

      this.addWindowListener(new java.awt.event.WindowAdapter() {
        public void windowClosing(WindowEvent e) {
          cancelSignon();
          dispose();
        }
         public void windowDeactivated(WindowEvent e) {
//startPR2004-10-20^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
           if(!u.focusBlock)
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
             toFront();
        }
//startPR2004-10-20^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        public void windowActivated(WindowEvent e) {
          if(pass.isValid()) pass.requestFocus();
          else in.requestFocus();
       }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      });
//startPR2008-08-05^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      if(namegiven!=null){
        in.setText(namegiven);
        okorenter();
        u.blockEscape = false;
      }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2009-08-19^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      // needed for Mac OS X 10.5
//      if(pass.isValid()){
//        pass.requestFocus();
//      }
//      else in.requestFocus();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   }
   void okorenter() {
      lab2.setForeground(Color.red);  // these have somtimes been changed
      lab2.setOpaque(false);
      if(!gotname) {
          String name = in.getText();
          name = u.stripspaces2(name);
          if(name.length() == 0)   {noise.beep();  return;}
          newstu = findStudent(name);
          //  start rb 6/2/06 --------------------------------
          if(newstu == null && db.exists(name)) {
              newstu = new student();
              newstu.name = name;
              newstu.saveStudent();
              checkadmin(newstu);
              setVisible(false);
              u.okmess(u.gettext("stucorrupt", "heading"),
                       u.gettext("stucorrupt", "message", name));
          }
          //  end rb 6/2/06 --------------------------------
         if(newstu != null ) {
           //  start rb 6/2/06 --------------------------------
           if(newstu.which == 'N') {
               db.destroy(name);                    //  rb 6/2/08 v4.2   // make sure totally new
               newstu = new student();
               newstu.name = name;
               newstu.saveStudent();
               checkadmin(newstu);
               setVisible(false);
               u.okmess(u.gettext("stucorruptns", "heading"),
                        u.gettext("stucorruptns", "message", name));
           }
           //  end rb 6/2/06 --------------------------------
             sharkStartFrame.newuser = false;
             gotstuname = null;
//startPR2006-04-06^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
             if(wrongAdmin != null) {
               lab2.setText(u.gettext("so","wrongadminexists"));
               return;
             }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
             if(newstu.isSignedOn()) {
               lab2.setText(name + u.gettext("so","isalready"));
               return;
             }
             //start rb 22/1/06
             if(!getlock(newstu,newstu.name,false)) {
                 if(lockmess != null) {
                   lab2.setText(lockmess);
                   if(lockmess.indexOf('|') >= 0) // see if enlargemant needed for more lines
                       setBounds(new Rectangle(sharkStartFrame.screenSize.width*3/8,
                                           sharkStartFrame.screenSize.height*2/5,
                                           sharkStartFrame.screenSize.width/2,
                                           sharkStartFrame.screenSize.height*2/5));
                   validate();
                 }
//startPR2006-11-22^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//               else if(lockmess2 != null) {
                 else if(lockmess2) {
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                   setVisible(false);
//startPR2008-10-08^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//                    new userLimitWarning(student.signpanel);
                   if(shark.network)
//startPR2010-05-04^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//                     new userLimitWarning(student.signpanel);
                     new userLimitWarning(sharkStartFrame.mainFrame);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                   else{
//startPR2009-05-12^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//                    u.okmess(shark.programName, u.gettext("errorcodes", "errorcode12"), student.signpanel);
//startPR2010-05-04^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//                     u.okmess(shark.programName, u.gettext("errorcodes", "errorcode12", shark.programName), student.signpanel);
                     u.okmess(shark.programName, u.gettext("errorcodes", "errorcode12", shark.programName), sharkStartFrame.mainFrame);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                     sharkStartFrame.mainFrame.finalize();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                   }
                   setVisible(true);
                 }
                 return;
             }
             //end rb 22/1/06
             newstu.doupdates(true,false);     // rb 27/10/06
             if(newstu.password != null && newstu.password.length() > 0) {
//startPR2008-08-05^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//               fishpanel.removeMover(inithelp);
//startPR2010-05-04^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//                 if(fishpanel!=null) fishpanel.removeMover(inithelp);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                lab1.setText(u.gettext("so","enterpass"));
                lab2.setText("");
                pass.setEchoChar('*');
                panel1.remove(in);
                panel1.remove(lab2);
                grid1.fill = GridBagConstraints.NONE;
                panel1.add(pass,grid1);
                panel1.add(lab2,grid1);
                pass.setColumns(12);
//startPR2006-04-06^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                pass.setMinimumSize(pass.getPreferredSize());
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                pass.requestFocus();
                validate();
                repaint();
                gotname = true;
                return;
             }
             else newstu.addtolist();
          }
          else {   // new (temp)student
//startPR2006-09-28^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            checkadmin();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2005-11-29^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//             if(isAdministrator) {
                   if(isAdministrator && wrongAdmin == null) {
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                 if(gotstuname == null || !gotstuname.equals(name)) {
                    if(db.query(sharkStartFrame.optionsdb,"mnotemp",db.TEXT )>=0) {
                      lab2.setText(name + u.gettext("so","notfound"));  return;
                    }
                    if(name.length() > 30)   {lab2.setText(u.gettext("so","toolong"));  return;} // rb 20/11/06
                    lab2.setText(u.gettext("so","newtemp",name));
                    lab2.setBackground(Color.white);
                    lab2.setForeground(Color.black);
                    lab2.setOpaque(true);
                    gotstuname = new String(name);
                    setBounds(new Rectangle(sharkStartFrame.screenSize.width/8,
                                            sharkStartFrame.screenSize.height*2/5,
                                            sharkStartFrame.screenSize.width/2,
                                            sharkStartFrame.screenSize.height*2/5));
                    validate();
                    return;
                 }
              }
              //start rb 22/1/06
              newstu = new student(name);
              if(!getlock(newstu,newstu.name,false)) {
                  if(lockmess != null) {
                    lab2.setText(lockmess);
                    if(lockmess.indexOf('|') >= 0) // see if enlargemant needed for more lines
                        setBounds(new Rectangle(sharkStartFrame.screenSize.width*3/8,
                                            sharkStartFrame.screenSize.height*2/5,
                                            sharkStartFrame.screenSize.width/2,
                                            sharkStartFrame.screenSize.height*2/5));
                    validate();
                  }
//startPR2006-11-22^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//               else if(lockmess2 != null) {
                  else if(lockmess2) {
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                    setVisible(false);
//startPR2008-10-08^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//                    new userLimitWarning(student.signpanel);
                    if(shark.network)
//startPR2010-05-04^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//                      new userLimitWarning(student.signpanel);
                      new userLimitWarning(sharkStartFrame.mainFrame);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                    else{
//startPR2009-05-12^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//                    u.okmess(shark.programName, u.gettext("errorcodes", "errorcode12"), student.signpanel);
//startPR2010-05-04^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//                     u.okmess(shark.programName, u.gettext("errorcodes", "errorcode12", shark.programName), student.signpanel);
                     u.okmess(shark.programName, u.gettext("errorcodes", "errorcode12", shark.programName), sharkStartFrame.mainFrame);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                     sharkStartFrame.mainFrame.finalize();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                   }
                    setVisible(true);
                  }
                  return;
              }
              sharkStartFrame.newuser = true;
              newstu.addtolist();
              //end rb 22/1/06
                 // offer to make administrator
              String title;
              dispose();
//startPR2004-10-20^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    //              if(!isAdministrator() &&
//                JOptionPane.showConfirmDialog(sharkStartFrame.mainFrame,
//                    u.splitString(u.gettext("so","admin",name)),
//                    title = getTitle(),
//                    JOptionPane.YES_NO_OPTION,
//                    JOptionPane.QUESTION_MESSAGE)
//                                  == JOptionPane.YES_OPTION) {
//                  String op[] =  new String[]{u.gettext("so","ok")};
//                  u.showhelp help=null;
//                  JOptionPane getpw = new JOptionPane(
//                          u.gettext("so", "passwordx"),
//                          JOptionPane.PLAIN_MESSAGE,
//                          0,
//                          null,op,op[0]);
//                  getpw.setWantsInput(true);
//startPR2004-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//                  Dialog dialog = getpw.createDialog(null,title);
//                        Dialog dialog = getpw.createDialog(sharkStartFrame.mainFrame,title);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//                  if(sharkStartFrame.mainFrame.mwanthelp.isSelected())
//                            help = new u.showhelp(u.gettext("so","passtooltipx"));
//                  while(true) {
//                     dialog.setVisible(true);
//                     String input = (String)getpw.getInputValue();
//                     while(input.length() > 0 && input.charAt(input.length()-1) == ' ')
//                             input = input.substring(0,input.length()-1);
//                     if(input.length() > 0) {
//                        if(help != null) {
//                           help.dispose();
//                           help=null;
//                        }
//                        newstu.password= encrypt(input);
//                        break;
//                     }
//                  }
//                  newstu.administrator = true;
//                  isAdministrator = true;
//                  String dbpath = sharkStartFrame.sharedPath.toString();
//                  newstu.dbname = dbpath + sharkStartFrame.sharedPath.separatorChar+name;
//                  db.create(newstu.dbname);
//                  newstu.saveStudent();
//                  setup();
//              }


             // stops exiting via the Escape key
             u.blockEscape = true;
             int result = 0;
//startPR2005-11-29^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//             if (!isAdministrator) {
                if (!isAdministrator || (wrongAdmin != null)) {
                  if(wrongAdmin == null){
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
               result = JOptionPane.showConfirmDialog(sharkStartFrame.mainFrame,
                   u.splitString(u.gettext("so", "admin", name)),
                   title = getTitle(),
                   JOptionPane.YES_NO_OPTION,
                   JOptionPane.QUESTION_MESSAGE);
//startPR2005-11-29^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                }
                else{
                  title = getTitle();
                }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
               // stops this window being closed without assigning a password
               //to the administrator
               while (result == JOptionPane.CLOSED_OPTION) {
                 result = JOptionPane.showConfirmDialog(sharkStartFrame.mainFrame,
                     u.splitString(u.gettext("so", "admin", name)),
                     title = getTitle(),
                     JOptionPane.YES_NO_OPTION,
                     JOptionPane.QUESTION_MESSAGE);

               }
               if (result == JOptionPane.YES_OPTION) {
                 String op[] = new String[] {
                     u.gettext("so", "ok")};
                 u.showhelp help = null;
                 JOptionPane getpw = new JOptionPane(
                     u.gettext("so", "passwordx"),
                     JOptionPane.PLAIN_MESSAGE, 0, null, op, op[0]);
                 getpw.setWantsInput(true);
//startPR2004-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//                  Dialog dialog = getpw.createDialog(null,title);
                 JDialog dialog = getpw.createDialog(sharkStartFrame.mainFrame,
                                                     title);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2004-10-20^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                 dialog.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                 if (sharkStartFrame.mainFrame.mwanthelp.isSelected())
                   help = new u.showhelp(u.gettext("so", "passtooltipx"));
                 while (true) {
//startPR2004-10-21^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                   if(shark.macOS && dialog!=null)
                     dialog.dispose();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                   dialog.setVisible(true);
                   String input = (String) getpw.getInputValue();
                   while (input.length() > 0 &&
                          input.charAt(input.length() - 1) == ' ')
                     input = input.substring(0, input.length() - 1);
                   if (input.length() > 0) {
                     if (help != null) {
                       help.dispose();
                       help = null;
                     }
                     newstu.password = encrypt(input);
                     break;
                   }
                 }
                 newstu.administrator = true;
                 isAdministrator = true;
                 String dbpath = sharkStartFrame.sharedPath.toString();
                 String dbname = dbpath + sharkStartFrame.sharedPath.separatorChar +
                     name;
                 db.create(dbname);
                 newstu.isnew = false;
                 newstu.saveStudent();
//startPR2005-11-29^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                 // change wrongAdmin from adminitrator to student and signoff
                 if(wrongAdmin != null){
                   wrongAdmin.administrator = false;
                   wrongAdmin.saveStudent();
//startPR2006-04-30^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//                   if (wrongAdmin.lock != null) {
                   if (wrongAdmin.isLocked()) {
//startPR2010-03-15^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      if(shark.macOS && shark.network){
                    if(MacLock.DOJNI){
//endPRtoupdate^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                       wrongAdmin.macLocked = false;
                       MacLock.delete(sharkStartFrame.sharedPathplus + wrongAdmin.name, ".lock");
                       MacLock.delete(sharkStartFrame.sharedPathplus + wrongAdmin.name,  MacLock.LOCKEXTENSION);
                     }
                     else{
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                       try {
                         wrongAdmin.lock.release();
                         wrongAdmin.lock = null;
//startPR2006-04-30^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                         wrongAdmin.fflock.close();
                         wrongAdmin.fflock = null;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                         new File(sharkStartFrame.sharedPathplus + wrongAdmin.name + ".lock").delete();
                       }
                       catch (IOException e) {
                       }
//startPR2006-04-30^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                     }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                   }
//startPR2006-04-06^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                   checkadmin(wrongAdmin);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                   wrongAdmin = null;
                 }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//                 setup();
//startPR2006-04-06^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                 checkadmin();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
               }
             }
             u.blockEscape = false;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          }
        }
        else {
           if(newstu.okPassword(String.valueOf(pass.getPassword()))) {
//startPR2005-11-29^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
             if(wrongAdmin != null){
               fishpanel.removeAllMovers();
//startPR2010-05-04^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//             inithelp=null;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
               dispose();
               student.signOn(student.fishpanel);
               return;
             }
             else
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
               newstu.addtolist();
           }
           else  {lab2.setText(u.gettext("so","wrong"));noise.beep();return;}
        }
        // RB 20/1/06 start
        fishpanel=null;
//startPR2010-05-04^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//             inithelp=null;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        dispose();
//startPR2008-09-21^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//        doForwardsCompatibilityCheck(newstu, signpanel);    //2222
//endPRv404^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2006-11-22^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            // start rb 22/1/06
                 // sometimes a 'too many users' warning message must be displayed
//      if(lockmess2 != null) u.okmess(u.gettext("toomanyuserswarning","heading"),lockmess2,sharkStartFrame.mainFrame);
           // end rb 22/1/06
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        newstu.finishSignon(true);
        // RB 20/1/06 end
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
}
//startPR2010-05-04^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  */
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2008-08-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2008-09-21^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  // returns false if student version is later than current ver
//  boolean isVersionAllowed(String stuver, String currver){
  int stuRelativeVersion(String stuver, String currver){
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    if(stuver==null)return -1;
    String stus[] = u.splitString(stuver, '.');
    String currs[] = u.splitString(currver, '.');
    int i;
    try{
      if (stus.length <= currs.length) {
        for (i = 0; i < stus.length; i++) {
          int s = Integer.parseInt(stus[i]);
          int c = Integer.parseInt(currs[i]);
          if(s>c)return 1;
          if(c>s)return -1;
        }
        return 0;
      }
      else {
        // stu ver is longer than current ver
        for (i = 0; i < currs.length; i++) {
          int c = Integer.parseInt(currs[i]);
          int s = Integer.parseInt(stus[i]);
          if(s>c)return 1;
          if(c>s)return -1;
        }
        return 0;
      }
    }
    catch(NumberFormatException e){
      return -1;
    }
  }

//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR 
 class newUser extends NewUser_base {
 
   newUser(int type, JFrame owner, String nam, int nodetype1) {
       super(type, owner, nam, nodetype1);
   }
   newUser(int type, JDialog owner, String nam, int nodetype1) {
       super(type, owner, nam, nodetype1);
   }
   public void okpressed() {
       super.okpressed();
       if(exit)return;
       if((addpasswordcb.isVisible() && addpasswordcb.isSelected()) ||
           (!addpasswordcb.isVisible() && passwordtf.isVisible())){
           returnpass = pass;
           returnpasswordhint = passwordhint;
       }   
       exitval = 0;
       dispose();
   }
 }  
  
  
static class sharkImageSay extends sharkImage {
    sharkImageSay(String name, boolean q) {
       super(name,true);
    }
   //----------------------------------------------------------------
    public void mouseClicked(int mx,int my) {       //  overwrite
        saytext(mx,my);
    }
}
  static class nameandrec {
     String name;
     topicPlayed tp;
     String owner;
     nameandrec(String name1, topicPlayed topicPlayed1) {
        name = name1;
        tp = topicPlayed1;
     }     
     
     nameandrec(String name1, topicPlayed topicPlayed1, String owner1) {
        name = name1;
        tp = topicPlayed1;
        owner = owner1;
     }
  }
// ---------------------------------------------------------------------added rb 22/1/06
  public static class userlist extends JDialog {
    userlist() {
      super(sharkStartFrame.mainFrame);
//startPR2008-06-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      u.focusBlock = true;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      JList uu = new JList();
      String ss[] = getUserList();
      if(ss.length == 0) {
        uu.setListData(new String[] {u.gettext("muserlist","nousers")});
      }
      else uu.setListData(ss);
      setTitle(u.gettext("muserlist","title"));
       getContentPane().setBackground(runMovers.tooltipbg);
//       int w = sharkStartFrame.mainFrame.getSize().width*2/3;
//       int h = sharkStartFrame.mainFrame.getSize().height;
//       setBounds(0,0,w,h);
       
//     setBounds(new Rectangle(sharkStartFrame.screenSize.width/12,
//          sharkStartFrame.screenSize.height/8,
//          sharkStartFrame.screenSize.width/2,
//          sharkStartFrame.screenSize.height*3/4));
     setBounds(u2_base.adjustBounds(new Rectangle(sharkStartFrame.screenSize.width/12,
          sharkStartFrame.screenSize.height/8,
          sharkStartFrame.screenSize.width/2,
          sharkStartFrame.screenSize.height*3/4)));   

//startPR2004-08-27^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       //was true
       this.setResizable(false);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
       this.addWindowListener(new java.awt.event.WindowAdapter() {
           public void windowDeactivated(WindowEvent e) {
              if(sharkStartFrame.currStudent>=0) dispose();
           }
//startPR2004-10-20^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
           public void windowClosed(WindowEvent e) {
             if(sharkStartFrame.currStudent<0)
               // prevents exiting via the escape key from the sign on dialog
               u.blockEscape = true;
//startPR2007-09-03^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//            // allows focus back on the sign on dialog
//          u.focusBlock = false;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR

           }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        });
//startPR2004-09-01^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        // enables exiting screen via the ESC key
        uu.addKeyListener(new KeyAdapter() {
          public void keyPressed(KeyEvent e) {
            int code = e.getKeyCode();
            if(code == KeyEvent.VK_ESCAPE)
              dispose();
          }
        });
       getContentPane().setLayout(new BorderLayout());
       getContentPane().add( new JScrollPane(uu),BorderLayout.CENTER);
       validate();
       setVisible(true);
    }
//startPR2008-06-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    public void dispose(){
      u.focusBlock = false;
      super.dispose();
    }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    
    public static String[] getUserList(){
      int i, len;
      String ss[] = new String[0];
      byte wk[] = new byte[1000];
      for (i = 0; i < sharkStartFrame.users+1; ++i) {
//startPR2006-08-19^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        File ff = new File(sharkStartFrame.sharedPathplus+"user" + String.valueOf(i + 1) +".nlock");
        File ff2 = new File(sharkStartFrame.sharedPathplus + "user" + String.valueOf(i + 1) +".nlock2");
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2010-03-15^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      if(shark.macOS && shark.network){
      if(MacLock.DOJNI){
//endPRtoupdate^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          if(ff.exists()){
            if(MacLock.lock(sharkStartFrame.sharedPathplus +"user" + String.valueOf(i + 1), ".nlock", null, false) < 0){
              try {
                len = new FileInputStream(ff2).read(wk);
                ss = u.addString(ss, new String(wk, 0, len));
              }
              catch (IOException e) {
                e.printStackTrace();
              }
            }
          }
        }
        else{
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          try {
            if(ff.exists()) {
              FileOutputStream slot = new FileOutputStream(ff);
//startPR2007-11-19^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//              FileLock lock = slot.getChannel().tryLock();
              FileLock lock = null;
              // IOException needs to be caught on Linux cifs networks
              try{lock = slot.getChannel().tryLock();}catch (IOException e) {} // test if in use
//startPR2010-03-18^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
              catch (OverlappingFileLockException e) {}
//endPRtoupdate^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
              if (lock == null) {
                len =new FileInputStream(ff2).read(wk);
                ss = u.addString(ss,new String(wk,0,len));
              }
              else lock.release();
              slot.close();
            }
          }
          catch (IOException e) {
          }
//startPR2006-04-30^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      }
      return ss;
    }
    
  }
//startPR2006-11-22^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    static class userLimitWarning extends JDialog {
      String shortmess = u.edit(u.gettext("toomanyusers","shortmess"),
                    new String[]{sharkStartFrame.treefont.getName(),
                    String.valueOf(sharkStartFrame.users),sharkStartFrame.school,
                    sharkStartFrame.serial});
      String longmess = u.edit(u.gettext("toomanyusers","longmess"),
                    new String[]{sharkStartFrame.treefont.getName(),
                    String.valueOf(sharkStartFrame.users),sharkStartFrame.school,
                    sharkStartFrame.serial});
      userLimitWarning(JDialog jd) {
        super(jd, true);
        init();
      }
      userLimitWarning(JFrame jf) {
        super(jf, true);
        init();
      }
      void init(){
        setTitle(u.gettext("toomanyusers","heading"));
        setResizable(false);
        setSize(500, 380);
        setLocation((sharkStartFrame.screenSize.width-getWidth())/2,
                    (sharkStartFrame.screenSize.height-getHeight())/2);
        Container cp = getContentPane();
        cp.setBackground(Color.white);
        final JEditorPane p = new JEditorPane("text/html", shortmess);
        p.setEditable(false);
        p.setMargin(new Insets(15,40,15,40));
        p.addHyperlinkListener( new HyperlinkListener() {
          public void hyperlinkUpdate(HyperlinkEvent e) {
            if (e.getEventType().equals(HyperlinkEvent.EventType.ACTIVATED)) {
//startPR2009-09-04^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
              String m = "mailto:";
              String ss = e.getDescription();
              if(ss.indexOf(m)>=0)
                u.launchMailto(ss);
              else
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
              p.setText(longmess);
            }
          }
        });
        cp.add(new JScrollPane(p));
        validate();
        setVisible(true);
      }
    }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  public static boolean admin() {
     return sharkStartFrame.studentList[sharkStartFrame.currStudent].administrator;
  }
  public static boolean teacher() {
     return sharkStartFrame.studentList[sharkStartFrame.currStudent].teacher;
  }
}
class mySimpleDateFormat extends SimpleDateFormat {
   mySimpleDateFormat(String ff,TimeZone tz) {
      super(ff);
      if(tz != null) setTimeZone(tz);
   }
}
