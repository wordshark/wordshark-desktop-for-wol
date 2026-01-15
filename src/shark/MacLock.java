package shark;

import java.awt.Component.*;
import java.io.*;
import java.util.*;

public class MacLock {
  /**
   * Native method for opening and locking a file.
   *
   * @param xchararray char[] The path of the file to be locked.
   * @param xchararray2 char[] The the text to write to the file.
   * @return int The file descriptor associated with this file.
   */
  private static native int writeMacLockToStdout(byte[] xchararray, byte[] xchararray2);
  /**
   * Native method for unlocking a file.
  *
   * @param xfd The file descriptor associated with the file.
   * @return int Returns a positive int if unlocking has been successful.
   */
  private static native int writeMacLockToStdoutUnlock(int xfd);
  /**
   * Stores the paths of locked files with their file descriptors (which are
   * needed for unlocking)
   */
  private static Vector fdRecord = new Vector();
  /**
   * File extention for a lock file when the program writes to it directly. As
   * a file can't be locked natively and then writen to from Java this
   * extra file is required.
   */
  public static final String LOCKEXTENSION = ".lk";
//startPR2010-03-15^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  public static final boolean DOJNI =
          ((shark.macOS && shark.network)||(shark.macOS && shark.licenceType.equals(shark.LICENCETYPE_USB)));
//endPRtoupdate^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR

  /**
   * Loads the native library
   */
  public static void ldLibrary(){
    System.loadLibrary("MacLock");
  }

  /**
   * Attempts to get a lock on a file
   *
   * @param path String The path of the file to be locked including file name
   * but excluding the file extention.
   * @param extention String The extension of the lock file, eg ".lock"
   * @param writeText String Text to be written to the file. Null if no text.
   * @param wait boolean True if this method does wait for file to become available,
   * false if it does not.
   * @return int An int of zero or above indicates successful locking.
   */
  public static int lock(String path, String extention, String writeText, boolean wait) {
    File f = new File(path+extention);
    if(!f.exists()){
      try{
        // file can't be opened from native code, as it thinks the
        // wrong process has got hold of it.
        FileOutputStream fos = new FileOutputStream(f);
//startPR2009-08-02^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        u2_base.setNewFilePermissions(f);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        fos.close();
      }
      catch(Exception e){e.printStackTrace();}
    }
    try{
      int i;
      if(wait){
        while(((i=writeMacLockToStdout(f.getAbsolutePath().getBytes(),
              ((writeText==null)?null:(writeText.getBytes())))) < 0)){
          Thread.sleep(200);
          if(!f.exists()){
            try{
              FileOutputStream fos = new FileOutputStream(f);
//startPR2009-08-02^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
              u2_base.setNewFilePermissions(f);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
              fos.close();
            }
            catch(Exception e){e.printStackTrace();}
          }
        }
      }
      else{
        i=writeMacLockToStdout(f.getAbsolutePath().getBytes(),
          ((writeText==null)?null:(writeText.getBytes())));
      }
      // unable to get lock
      if(i < 0)return -1;
      vAdd(f.getAbsolutePath(), String.valueOf(i));
      return 0;
    }
    catch(Exception ee){ee.printStackTrace();}
    return -1;
  }

  /**
   * Removes the lock previously obtained on a file.
   *
   * @param path String The path of the file with the lock to be removed.
   * @param extention String The extension of the file with the lock to be removed.
   * @return int An int of zero or above indicates successful unlocking.
   */
  public static int unlock(String path, String extention) {
    int ii = vGet(path+extention);
    int r = -1;
    if((ii >= 0)&&(new File(path+extention).exists())){
      if ( (r = writeMacLockToStdoutUnlock(ii)) != -1){
        vRemove(path + extention);
      }
    }
    return r;
  }

  /**
   * Deletes the file that had a lock previously obtained on it. Unlocks the file
   * if necessary.
   *
   * @param path String The path of the file to be deleted.
   * @param ext String The extension of the file to be deleted.
   */
  public static void delete(String path, String ext) {
    File macf = new File(path+ext);
    if(macf.exists()){
      unlock(path, ext);
      macf.delete();
    }
  }


  /**
   * Stores the file descriptor with the path of the relevant file
   *
   * @param path The path of the file.
   * @param fd String The file descriptor for the file
   * @return int An int of zero or above indicates the file path and the file
   * descriptor have been successfully stored.
   */
  private static int vAdd(String path, String fd) {
    boolean found = false;
    for(int k = 0; k < fdRecord.size(); k++){
      String ss = (String)fdRecord.elementAt(k);
      if(ss.length() >= path.length()){
        if (ss.subSequence(0, path.length()).equals(path)) {
          found = true;
        }
      }
    }
    if(!found){
      fdRecord.add(path+":"+fd);
      return 0;
    }
    else return -1;
  }

  /**
   * Removes the file descriptor entry that has been stored for a particular file.
   * @param filepath The path of the file.
   * @return int An int of zero or above indicates the record of the file path
   * and the file descriptor have been successfully removed.
   */
  private static int vRemove(String filepath) {
    for(int k = 0; k < fdRecord.size(); k++){
      String ss = (String)fdRecord.elementAt(k);
      if(ss.length() >= filepath.length()){
        if (ss.subSequence(0, filepath.length()).equals(filepath)) {
          fdRecord.removeElementAt(k);
          return 0;
        }
      }
    }
    return -1;
  }

  /**
   * Get the file decriptor for an open file.
   *
   * @param filepath The path of the file
   * @return int The file descriptor for the file.
   */
  private static int vGet(String filepath){
    int i = -1;
    String ss ="";
    for(int k = 0; k < fdRecord.size(); k++){
      try{
         ss = (String) fdRecord.elementAt(k);
         if(ss.length() >= filepath.length()){
           if (ss.subSequence(0, filepath.length()).equals(filepath)) {
             i = Integer.parseInt(String.valueOf(ss.subSequence(ss.indexOf(":") + 1, ss.length())));
           }
         }
      }
      catch(Exception e ){
        u.okmess(ss, filepath);
        e.printStackTrace();}
    }
    return i;
  }
}
