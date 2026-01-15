/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package shark;


import java.nio.channels.*;
import java.io.*;
import java.util.*;


/**
 *
 * @author MacBook Air
 */
public class Diagnostics_base {
    private FileLock lock;
    FileOutputStream fos;
    static String errors[];
    String earlyCourses[] = new String[]{"publictopics2","publictopics3","publictopics4",
        "publictopics5","publictopics6","publictopics7","publictopics8","publictopics9"};

    public void doTest() {
//      testtext(sharkStartFrame.publicTextLib[0], "T:\\W\\NetBeansProjects\\jnumbershark_\\Release\\Numbershark 5\\numbershark-public\\publictext");

    }

    public void testtext(String n1,String n2) {
        testtext2(n1,n2, false);
        testtext2(n2,n1, true);
    }


   public void testtext2(String n1,String n2, boolean append) {
     String ss2[] = db.list(n2, db.TEXT);
     String ss[] = db.list(n1, db.TEXT);
     try {
       FileOutputStream f = new FileOutputStream(sharkStartFrame.sharedPathplus +
                                                 "testtext.txt", append);
       f.write( (byte) '\r');
       f.write( (byte) '\n');
       f.write( ("----------------  " + "Main file: " + n1 + "  ----------------").getBytes());
       f.write( (byte) '\r');
       f.write( (byte) '\n');
       f.write( (byte) '\r');
       f.write( (byte) '\n');
       boolean started = false;
       for (int i = 0; i < ss2.length; ++i) {
           if(u.findString(ss, ss2[i])<0){
                if(!started){
                    started = true;
                    f.write("ABSENT KEYS:".getBytes());
                    f.write( (byte) '\r');
                    f.write( (byte) '\n');
                }
                f.write(( "    " + ss2[i]).getBytes());
                f.write( (byte) '\r');
                f.write( (byte) '\n');
           }
       }
       f.write( (byte) '\r');
       f.write( (byte) '\n');
       started = false;
       for (int i = 0; i < ss.length; ++i) {
           if(u.findString(ss2, ss[i])>=0){
             String t1[] = (String[]) db.find(n1, ss[i], db.TEXT);
             String t2[] = (String[]) db.find(n2, ss[i], db.TEXT);
               jloop:for (int j = 0; j < t2.length; ++j) {
                 int k = t2[j].indexOf('=');
                 if (k > 0) {
                   String sub = t2[j].substring(0, k + 1);
                   boolean found = false;
                   for (int m = 0; m < t1.length; ++m) {
                     if (t1[m].startsWith(sub)){
                         found = true;
                     }
                   }
                   if(!found){
                     if(!started){
                        started = true;
                        f.write((ss[i]).getBytes());
                        f.write( (byte) '\r');
                        f.write( (byte) '\n');
                     }
                     f.write(("    ABSENT: " + t2[j]).getBytes());
                     f.write( (byte) '\r');
                     f.write( (byte) '\n');
                   }
                 }
               }
               jloop2:for (int j = 0; j < t1.length; ++j) {
                 int k = t1[j].indexOf('=');
                 if (k > 0) {
                   String sub = t1[j].substring(0, k + 1);
                   for (int m = 0; m < t2.length; ++m) {
                     if (t2[m].startsWith(sub)){
                         if(!t2[m].equals(t1[j])){
                             if(!started){
                                 started = true;
                                 f.write((ss[i]).getBytes());
                                 f.write( (byte) '\r');
                                 f.write( (byte) '\n');
                             }
                             String sstar[] = u.splitString(t1[j], '%');
                             String sstar2[] = u.splitString(t2[m], '%');
                             if(sstar.length!=sstar2.length)
                                 f.write((" %% DIFFERENT: " + t2[m]).getBytes());
                             else
                                 f.write(("    DIFFERENT: " + t2[m]).getBytes());
                             f.write( (byte) '\r');
                             f.write( (byte) '\n');
                         }
                     }
                   }
                 }
               }
             }
           started = false;
       }
       f.write( (byte) '\r');
       f.write( (byte) '\n');
       f.write( (byte) '\r');
       f.write( (byte) '\n');
       f.write( (byte) '\r');
       f.write( (byte) '\n');
       f.write( (byte) '\r');
       f.write( (byte) '\n');
       f.close();
     }catch (IOException e) {}
   }


    public boolean createSoundExtraPatches(File oldf, File newf){
        String sold = oldf.getAbsolutePath();
        String snew = newf.getAbsolutePath();
        sold = sold.substring(0, sold.length()-4);
        snew = snew.substring(0, snew.length()-4);
        String ssold[] = db.list(sold, db.WAV);
        String ssnew[] = db.list(snew, db.WAV);
        String nameold = oldf.getName();
        String justnameold = nameold.substring(0, nameold.length()-4);
        String resultsdb = sharkStartFrame.sharedPathplus+"soundextraresults"+shark.sep+justnameold+sharkStartFrame.mainFrame.soundPatchExtra;
        File resultsfile = new File(resultsdb+".sha");
        boolean added = false;
        // any changed ones
        for(int i = 0; i < ssold.length; i++){
            byte bold[] =  db.findwav(sold, ssold[i]);
            byte bnew[] =  db.findwav(snew, ssold[i]);
            if(bnew==null)continue;
            sharkStartFrame.mainFrame.setTitle(justnameold + " - Check for changed item: no " + String.valueOf(i+1) + " of " + String.valueOf(ssold.length) + " : "+ (i<ssnew.length?ssnew[i]:"NULL"));
            if (!Arrays.equals(bold, bnew))
            {
                makeParentFolder(resultsfile);
                added = true;
                db.updatewav(resultsdb, ssold[i], db.findwav(snew, ssold[i]));
            }
        }
        // all the new ones
        for(int i = 0; i < ssnew.length; i++){
           sharkStartFrame.mainFrame.setTitle(justnameold + " - Check for new item: "+ ssnew[i]);
            if(u.findString(ssold, ssnew[i])<0){
                makeParentFolder(resultsfile);
                added = true;
                db.updatewav(resultsdb, ssnew[i], db.findwav(snew, ssnew[i]));

            }
        }
        return added;
    }

    void makeParentFolder(File resultsfile){
        try{
            if(!resultsfile.exists()){
                resultsfile.getParentFile().mkdirs();
            }
        }
        catch(Exception e){}
    }


    public boolean needFixExtraCourses(){
        String lastextracourses = (String)db.find(sharkStartFrame.optionsdb, "lastextracourses", db.TEXT);
        if(lastextracourses==null)lastextracourses="";
        String currextracourses = "";
        for(int i = 0; i < sharkStartFrame.publicExtraCourseLib.length; i++){
            long l = (new File(sharkStartFrame.sharedPathplus+sharkStartFrame.publicExtraCourseLib[i]+".sha")).lastModified();
            currextracourses += sharkStartFrame.publicExtraCourseLib[i]+String.valueOf(l);
        }
        if(!lastextracourses.equals(currextracourses)){
            db.update(sharkStartFrame.optionsdb, "lastextracourses", currextracourses, db.TEXT); 
            return true;
        }
        String users[] = db.dblistnames(sharkStartFrame.sharedPath);
        for(int i = 0; i < earlyCourses.length; i++){
            if(u.findString(users, earlyCourses[i])>=0){
                return true;
            }
        }
        return false;                
    }  
    
    public File[] needFixEarlyExtraCourses(){
        File fs[] = new File[]{};
        for(int i = 0; i < earlyCourses.length; i++){
            File f = new File(sharkStartFrame.sharedPathplus+earlyCourses[i]+".sha");
            if(f.exists()){
                fs = u.addFile(fs, f);
            }
        }
        return fs;
    }   

    
   public synchronized void fixcourses() {
       try{
         String topicch[] = u.splitString(u.gettext("v4changes", "topics"));
         String topicch5[] = u.splitString(u.gettext("v5changes", "topics"));
         topicTree maintopics = new topicTree();
         maintopics.onlyOneDatabase = "*";
         maintopics.root.setIcon(jnode.ROOTTOPICTREE);
         student.admintopiclist = new String[0];
         maintopics.setup(new String[] {u.absoluteToRelative(sharkStartFrame.publicTopicLib[0])}, false, db.TOPICTREE, true, "");
         for (int i = 0; i < sharkStartFrame.publicExtraCourseLib.length; i++) {
           topicTree topics = new topicTree();
           String database = sharkStartFrame.sharedPathplus + sharkStartFrame.publicExtraCourseLib[i];
           if (!new File(database + ".sha").exists())continue;
           topics.onlyOneDatabase = database;
           topics.distribute = true;
           topics.root.setIcon(jnode.ROOTTOPICTREE);
           topics.setup(new String[] {database}, true, db.TOPICTREE, false, database);
           
           docoursefix(topics, maintopics, topicch, topicch5);
           
           

         }
       }
       catch(Exception e){}
     }    
   
   
   topicTree docoursefix(topicTree topics, topicTree maintopics, String topicch[], String topicch5[]){
           jnode courses[] = topics.root.getChildren();
           loopcourse:for (int k = 0; k < courses.length; k++) {
             boolean changed = false;
             jnode node;
             jnode coursenode = (jnode) courses[k];
             loop1:for(Enumeration en = coursenode.depthFirstEnumeration();en.hasMoreElements();) {       
                 node = (jnode)en.nextElement();
                 if(node.get().trim().equals("") || !node.isLeaf())continue loop1;
                   String s = node.get();
                   if (!s.startsWith(topicTree.ISTOPIC) || s.indexOf(topicTree.ISPATH) >= 0)continue loop1;
                   if(s.startsWith(topicTree.ISTOPIC))
                       s = s.substring(1);
                   if (maintopics.findNode(s) == null) {
                     for (int n = 0; n < topicch.length; n += 2) {
                       if (s.equals(topicch[n])) {
                           // set anyway so v4 to v5 changes can happen
                         s = topicch[n + 1];
                         if (maintopics.findNode(topicch[n + 1]) != null) {
                           node.setUserObject(topicTree.ISTOPIC + topicch[n + 1]);
                           changed = true;
                           continue loop1;
                         }
                         break;
                       }
                     }
                     for (int n = 0; n < topicch5.length; n += 2) {
                       if (s.equals(topicch5[n])) {
                         if (maintopics.findNode(topicch5[n + 1]) != null) {
                           node.setUserObject(topicTree.ISTOPIC + topicch5[n + 1]);
                           changed = true;
                           continue loop1;
                         }
                         break;
                       }
                     }                   
                     changed = true;
                     node.removeFromParent();
                   }
             }
             if (changed) {
                saveTree1 st = new saveTree1(topics, coursenode);
                db.update(topics.onlyOneDatabase , courses[k].get(), st.curr, db.TOPICTREE);
             }
           }       
           return topics;
   }
   
   
   public synchronized void fixearlycourses(File f[]) {
       String strpublictopics2 =u.gettext("backup_", "publictopics2");
       String bkupprefix = u.gettext("backup_", "WS");
       String[] o = (String[]) db.find(sharkStartFrame.optionsdb, "backup_schedule", db.TEXT);
       for(int p = 0; p < f.length; p++){
           if(!f[p].exists())continue;
           String dbPath = f[p].getAbsolutePath();
           String dbName = f[p].getName();
           if(dbPath.endsWith(".sha"))dbPath = dbPath.substring(0, dbPath.length()-4);
           if(dbName.endsWith(".sha"))dbName = dbName.substring(0, dbName.length()-4);
           File lockFile = new File(sharkStartFrame.sharedPathplus+dbName+".lock");
           if(!openGuard(lockFile)){
               closeGuard(lockFile);
               continue;
           }
           // backup the publictopics2 file

           String location;
           if (o != null && o.length > 0 && o[0] != null) location = o[0];
           else location = sharkStartFrame.sharedPathplus + File.separator + bkupprefix + "backups";
           File ff = new File(location + File.separator + bkupprefix + strpublictopics2);
           File dest = new File(ff.getAbsolutePath() + File.separator + f[p].getName());
           if(ff.exists() || ff .mkdirs()) {
               if(!dest.exists()) u.copyfile(f[p], dest);
           }
           else {
               closeGuard(lockFile);
               continue;
           }
           String targetdb = "extracourses1";
           // do the backwards compatibility on it.
    //       String topicch[] = u.splitString(u.gettext("v4changes", "topics"));
    //       String topicch5[] = u.splitString(u.gettext("v5changes", "topics"));
    //       topicTree maintopics = new topicTree();
    //       maintopics.onlyOneDatabase = "*";
    //       maintopics.root.setIcon(jnode.ROOTTOPICTREE);
    //       student.admintopiclist = new String[0];
    //       maintopics.setup(new String[] {"publictopics"}, false, db.TOPICTREE, true, "");


           topicTree oldtopics = new topicTree();
           oldtopics.setup(new String[] {dbPath}, false, db.TOPICTREE, true, "");
    //       oldtopics = docoursefix(oldtopics, maintopics, topicch, topicch5, false);

           // find existing course names in extracourses
           String existingExtraCourses[] = new String[]{};
           for (int i = 0; i < sharkStartFrame.publicExtraCourseLib.length; i++) {
               topicTree topics = new topicTree();
               String database = sharkStartFrame.sharedPathplus + sharkStartFrame.publicExtraCourseLib[i];
               if (!new File(database + ".sha").exists())continue;
               topics.onlyOneDatabase = database;
               topics.distribute = true;
               topics.root.setIcon(jnode.ROOTTOPICTREE);
               topics.setup(new String[] {database}, true, db.TOPICTREE, false, database);
               jnode children[] = topics.root.getChildren();
               for(int j = 0; j < children.length; j++){
                   String coursename = children[j].get();
                   if(u.findString(existingExtraCourses, coursename)<0 && !coursename.trim().equals(""))
                       existingExtraCourses = u.addString(existingExtraCourses, coursename);
               }
           }
           jnode courses[] = oldtopics.root.getChildren()[0].getChildren();

           // copy across any recordings
           if(!shark.numbershark){
               String recordings[] = db.list(dbName,db.WAV);
                for(int i=0;i<recordings.length;++i) {
                    if(db.findwav(targetdb, recordings[i])==null){
                      byte tr[] = (byte[])db.findwav(dbName,recordings[i]);
                      db.updatewav(targetdb,recordings[i],tr);
                    }
                }
           }


           String extracourses1 = topicTree.ISTOPIC+targetdb+topicTree.ISPATH;
           String searchfor = topicTree.ISTOPIC+dbName+topicTree.ISPATH;
           for(int i = 0; i < courses.length; i++){
               String s = courses[i].get();

               //change course names if necessary - because of duplication.
               String s1 = u.duplicateNameCheck(s, existingExtraCourses);
               if(!s.equals(s1)){
                   ((jnode)oldtopics.root.getChildAt(i)).setUserObject(s1);
               }

               // change the database name in topic refs (e.g. from publictopics2 to extracourses1)
               jnode node;
               jnode coursenode = (jnode) courses[i];
               loop1:for (node = (jnode) coursenode.getFirstLeaf(); node != null; node = (jnode) node.getNextLeaf()) {
                   String snode = node.get();
                   int k;
                   if (!shark.numbershark && (k=snode.indexOf(topicTree.ISPATH)) >= 0){
                       String topicname = snode.substring(k+1);
                       if(snode.indexOf(searchfor)>=0){
                           node.setUserObject(snode.replaceAll(searchfor, extracourses1));
                       }
                       //copy across the own word lists
                       saveTree1 trold = (saveTree1)db.find(dbName,topicname,db.TOPIC);
                       if(trold!=null)
                            db.update(targetdb,topicname,trold.curr,db.TOPIC);
                   }
                   else{
                       node.setUserObject(topicTree.ISTOPIC+snode);
                   }
               }
               saveTree1 st = (saveTree1)db.find(targetdb, s1, db.TOPICTREE);
               saveTree1.saveTree2 tosave = (new saveTree1(oldtopics,courses[i])).curr;
               if(st!=null){
           //        String currcourses[] = db.list(targetdb, db.TOPICTREE);
          //         s1 = u.duplicateNameCheck(s1, currcourses);
                   tosave.names[0] = s1; 
                   tosave = u.combineSaveTree2(tosave, st.curr);
               }
               // save to extracourses1
               db.update(targetdb, s1, tosave,  db.TOPICTREE);
           }
           f[p].delete();
           closeGuard(lockFile);
       }
   }
   
   
    boolean openGuard(File file) {
      boolean backup = false;
      if(MacLock.DOJNI){
        String fp = file.getAbsolutePath();
        String extension = ".lock";
        backup = (MacLock.lock(fp.substring(0, fp.length()-extension.length()), extension, null, false)>=0);
      }
      else{
        try{
            fos = new FileOutputStream(file);
            u2_base.setNewFilePermissions(file);
            lock =  fos.getChannel().tryLock();
            backup = true;
          }
          catch(IOException e){
            backup = false;
          }
          catch (OverlappingFileLockException e) {backup = false;}
      }
      return backup;
    }

    void closeGuard(File file){
      if(MacLock.DOJNI){
        String fp = file.getAbsolutePath();
        String extension = ".lock";
        MacLock.delete(fp.substring(0, fp.length()-extension.length()), ".lock");
      }
      else{
        try {
          if (lock != null) {
            lock.release();
            lock = null;
            fos.close();
            fos = null;
            file.delete();
          }
        } catch (Exception e) {}
      }
    }   
}
