// database management for speech and wordlists

package shark;
import java.io.*;
import java.util.*;
import shark.sharkImage.saveSharkImage;
import shark.findword.indexclass;
import shark.program.saveprogram;
import java.nio.channels.*;

/**
 * <p>Title: WordShark</p>
 * <p>Description: Contains a list of databases each identified using the user
 * name or another name for example "public images". Each item in the database has a
 * type name. There are a limited number of types specified. Databases are checked
 * periodically (on closure?) and compacted if necessary.</p>
 * <p>Copyright: Copyright (c) 1997</p>
 * <p>Company: Your Company</p>
 * @author Your Name
 * @version 1.0
 */


public class db {
   static boolean forcecompress = false;
//startPR2006-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//   private static class dbaseIndex implements Serializable{
     public static class dbaseIndex implements Serializable{
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     static final long serialVersionUID = -7378091254357504162L;
     long pos;
     int len;
     byte type;
     String name;
   }

  File database,database2;
   RandomAccessFile f;
   dbaseIndex index[];
   long indexpos;                          //  rb v4.2
   int indexlen;                            //  rb v4.2
   long alreadycompressedpos;               //  rb v4.2
   public int indexTot;
   public static final byte TOPIC = 1;  // 'type' in dbaseIndex
   public static final byte WAV = 2;
   public static final byte STUDENT = 3;
   public static final byte TOPICTREE = 4;
   public static final byte GAMETREE = 5;
   public static final byte GAME = 6;
   public static final byte IMAGE = 7;
   public static final byte WORDSEARCH = 8;
   public static final byte PROGRAM = 10;
   public static final byte TEXT = 11;
   public static final byte SAVESUM = 12;
   public static final byte SAVEKEYPAD = 13;
   public static final byte MESSAGE = 14;
   public static final byte SAVEMESSAGE = 15;
   public static final byte VECTOR = 17;
//startPR2008-06-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   public static final byte PICTURE = 18;
//endPRimagesandvids^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   public static final byte PICTUREPLIST = 19;
   public static final byte TOPICPLIST = 20;

   boolean newfile;
   public boolean ok,needcompress;
   String id,name,tempname;
   boolean updating,changed,needsaveindex;
   char savetype;
   dbaseIndex saveindex;
   int savenum;
   public static db openlist[] = new db[0];
   boolean sharedfile = false;
   FileLock lock;
   static long lastPublicTopicsSave = 0;
   static String alternativeShared = null;

   public synchronized static db get(String namedd,boolean update) {
      if(namedd == null) return null;
      String name = namedd.toLowerCase();
      short i, num = (short)openlist.length;
      db dbnew;
      int free = -1;
      for(i = 0;i < num; ++i) {
         if(openlist[i] == null) {if(free==-1) free=i;continue;}
         if(openlist[i].id.equalsIgnoreCase(name) || openlist[i].name.equalsIgnoreCase(name)) {  // rb 20/5/07
            if(!openlist[i].updating && update) {
               try {
                  openlist[i].f.close();
                  openlist[i].f = new RandomAccessFile(openlist[i].database,"rw");
               }
               catch(IOException e) {
                  openlist[i].f = null;
              }
               if(openlist[i].f==null) {
                  try {
                     openlist[i].f = new RandomAccessFile(openlist[i].database,"r");
                  }
                  catch(IOException e) {
                     openlist[i].ok = false;
                     return null;
                  }
                  catch  (SecurityException e) {
                     openlist[i].ok = false;
                     return null;
                  }
               }
               openlist[i].updating = true;
             }
             return openlist[i];
         }
      }
      dbnew  = new db(namedd,update);
      if(dbnew == null) return null;
      if(dbnew.ok)  {
         if(free != -1) {openlist[free] = dbnew;}
         else {
            db db1[] = new db[num+1];
            if(num>0) System.arraycopy(openlist, 0, db1, 0,num);
            openlist = db1;
            openlist[num] = dbnew;
         }
         return dbnew;
      }
      if(dbnew.lock != null) {
        try {
          dbnew.lock.release();
        }
        catch (IOException ee) {
        }
        dbnew.lock = null;
      }
//startPR2009-08-19^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          try{
//startPR2010-03-15^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      if(shark.macOS && shark.network){
          if(MacLock.DOJNI)
//endPRtoupdate^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//        MacLock.unlock(dbnew.name, MacLock.LOCKEXTENSION);
              MacLock.delete(dbnew.name, MacLock.LOCKEXTENSION);
          }
          catch(Exception e){}
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      return null;
   }
   public static void closeAll() {
      for(short i = 0;i<openlist.length;++i) {
         if(openlist[i] != null) openlist[i].close();
      }
      openlist = new db[0];
   }
   //---------------------------------------------------------------------
   public db(String databasei,boolean update)
   {
   ObjectInputStream fIndex;
   int i,len,inc;
   long pos;
   File ff;

   updating = update;
   if(!(new File(databasei)).isAbsolute()) {
      if((ff = new File(sharkStartFrame.publicPath,databasei+".sha")).exists())
             database = ff;
      else if((ff = new File(sharkStartFrame.sharedPath,databasei+".sha")).exists()
                  || update) {
             database = ff;
             sharedfile = true;
      }
      else if(alternativeShared!=null && ((ff = new File(alternativeShared,databasei+".sha")).exists()
                  || update)) {
             database = ff;
             sharedfile = true;
      }
      else database = new File(sharkStartFrame.publicPath,databasei+".sha");
   }
   else {
      database = new File(databasei+".sha");
//startPR2007-07-12^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      if(!preSetup){
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        if(sharkStartFrame.sharedPathplus != null)
          sharedfile = databasei.toLowerCase().startsWith(sharkStartFrame.sharedPathplus.toLowerCase());
        else
          sharedfile = (databasei.indexOf(shark.sharedPath) == 0);
//startPR2007-07-12^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   }
   if(sharedfile) updating = true;
//startPR2006-08-19^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   if(Demo_base.isDemo) updating = false;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   name = database.getAbsolutePath();
   name = name.substring(0,name.length()-4);
   id = database.getName();
   id = id.substring(0,id.length()-4);
   ok = true;
   outloop: while(true) try {
      if(database.exists())  {
        f = new RandomAccessFile(database,updating?"rw":"r");
        if(sharedfile) {
//startPR2006-08-19^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2010-03-15^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      if(shark.macOS && shark.network){
          if(MacLock.DOJNI)
//endPRtoupdate^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            MacLock.lock(name , MacLock.LOCKEXTENSION, null, true);
          else if (!Demo_base.isDemo)
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2007-11-19^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//            lock = f.getChannel().lock();
          {
            FileChannel fc = f.getChannel();
            // IOException needs to be caught on Linux cifs networks
            try {
                lock = fc.lock(); 
            } catch (IOException e) {}
//startPR2010-03-18^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            catch (OverlappingFileLockException e) {}
//endPRtoupdate^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          // start rb 2/3/07 --  removed rb v4.2---------------------------------------------------------
          // if crash in middle of compress, restore from copy
//          File fi = new  File(name+".shc");
//          if(fi.exists()) {
//            RandomAccessFile ft = new RandomAccessFile(fi,"r");
//            ft.seek(0);
//            long leni = ft.length(),tot=0;
//            int bufflen = (int)Math.min(leni,1000000), lenr;
//            byte buff[] = new byte[bufflen];
//            f.seek(0);
//            f.setLength(0);
//            while(tot<leni) {
//              lenr = ft.read(buff);
//              f.write(buff,0,lenr);
//              tot += lenr;
//              f.setLength(tot);
//            }
//            ft.close();
//            ft = null;
//            fi.delete();
//            f.seek(0);
//          }
          // end rb 2/3/07 -----------------------------------------------------------
        }
        long flen = f.length();
        if(flen<16) {
          if(sharedfile) {
            setindexdetails(0,0,0);
            return;
          }
          else {
            f.close();
            ok = false;
            return;
          }
        }
        getindexdetails();                                    // rb 4.2
        pos = indexpos;                                       // rb 4.2
        len = indexlen;                                       // rb 4.2
        if(pos<0 || len<0 || pos+len>flen || indexTot*16 > len) {
          if(updating && sharedfile) {
            setindexdetails(0,0,0);
          }
          else {
            f.close();
            ok = false;
            return;
          }
        }
        index = new dbaseIndex[indexTot];
        if(pos > 0) {
           f.seek(pos);
           byte buf[] = new byte[len];
           f.read(buf);
           inc = (int)f.getFilePointer();
           InputStream s = new ByteArrayInputStream(buf);
           fIndex = new ObjectInputStream(s);
           for(i = 0;i<indexTot;++i) {
              index[i] = (dbaseIndex) fIndex.readObject();
           }
        }

      }
      else {               // new database
         if(updating) {
//startPR2008-06-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            if(sharedfile){
              String dbs = database.getAbsolutePath().substring(sharkStartFrame.sharedPathplus.length());
              if (dbs.indexOf(sharkStartFrame.separator) >= 0) {
                File bb = new File(name.substring(0, name.lastIndexOf(sharkStartFrame.separator)));
                bb.mkdirs();
              }
            }
//endPRimagesandvids^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            index = new dbaseIndex[0];
            f = new RandomAccessFile(database,"rw");
//startPR2009-08-02^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            u2_base.setNewFilePermissions(database);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            setindexdetails(indexpos=pos=(long)0,indexlen=len=(int)0,indexTot);  // rb v 4.2
            index = new dbaseIndex[0];
         }
         else {ok=false; return;}
      }
      return;
   }
//startPR2007-05-17^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   catch(FileNotFoundException e) {
     if(sharedfile){
//startPR2010-04-22^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//       try {
//         if(!shark.macOS){
//         String cmds[] = new String[] {"CACLS",shark.sharedPath,"/E", "/T", "/P","Users:F"};
//         Runtime.getRuntime().exec((cmds));
//         }
//        }
//       catch(Exception e2){}
        u2_base.setNewFilePermissions(sharkStartFrame.sharedPath);
//endPRtoupdate^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2008-08-14^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//       u.okmess(shark.programName, u.gettext("errorcodes", "errorcode10", sharkStartFrame.sharedPath.getAbsolutePath()));
//       String errorcode10 = u.gettext("errorcodes", "errorcode10", sharkStartFrame.sharedPath.getAbsolutePath());
        OptionPane_base.getErrorMessageDialog(null, 10, u.gettext("errorcodes", "errorcode10", sharkStartFrame.sharedPath.getAbsolutePath()), OptionPane_base.ERRORTYPE_EXIT);
//startPR2009-08-02^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//       if(shark.macOS)errorcode10 = errorcode10.concat(u.edit(u.gettext("errorcodes", "macwarn10"),new String[]{shark.programName,u.gettext("managestudent","label"),u.gettext("macl","label"),u.gettext("advanced","label")}));
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//       u.okmess(shark.programName,u.convertToHtml(errorcode10));
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     }
   }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   catch(IOException e) {
     try {f.close();}catch(IOException e2) {}
     ok = false;
     return;
   }
   catch(ClassNotFoundException e) {
     if(updating && sharedfile) {
          setindexdetails(indexpos=pos=(long)0,indexlen=len=(int)0,indexTot=0);  // rb v 4.2
         index = new dbaseIndex[0];
     }
     else {
       try {f.close();}catch(IOException e2) {}
       ok = false;
       return;
     }
   }
   }
   //---------------------------------------------------------------------
   public void finalize()
   {
   dbaseIndex index2 = new dbaseIndex();
   int ilen;

   if(!ok)  return;
   if(updating && changed || forcecompress) {
      if(needsaveindex) {
        if(saveIndex(-1)) {                      // rb v4.2
          needcompress = true;
        }
      }
      if(needcompress||forcecompress) {   //if(saveIndex(f)) {
         compress();
      }
   }
   if(lock != null) {
       try {
         lock.release();
       }
       catch(IOException ee) {
       }
       lock = null;
   }
//startPR2009-08-19^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   try{
//startPR2010-03-15^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      if(shark.macOS && shark.network){
      if(MacLock.DOJNI)
//endPRtoupdate^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//     MacLock.unlock(name, MacLock.LOCKEXTENSION);
     MacLock.delete(name, MacLock.LOCKEXTENSION);
   }
   catch(Exception e){}
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   try{ f.close(); }
   catch(IOException e) { }
   for(int i=0;i<openlist.length;++i)
       if(openlist[i] != null && openlist[i] == this) {openlist[i] = null;break;}
   }
   //------------------------------------------------------------
   void tclose() {
      if(saveIndex(-1)) {                       // rb v4.2
         needcompress = true;
         try {
            f.close();
            f = new RandomAccessFile(database,"rw");
         }
         catch(FileNotFoundException e) {close();}
         catch(IOException e) {close();}
      }
      needsaveindex = false;
   }
   //--------------------------------------------------------------------  // start rb v4.2 ==============
   long findslot(int len) {               // find where to write data
     int i;
     dbaseIndex index2[] = sortonpos();
     long lastpos = 16;
     for(i = 0;i<index2.length;++i) {
        if(index2[i].pos > 0  && index2.length > 0) {
           if (index2[i].pos - lastpos >= len) return lastpos;
           lastpos = index2[i].pos + index2[i].len;
        }
     }
     return lastpos;
   }
   dbaseIndex[] needtomove() {             // for compression, find data that needs moving and position to move to
     int i;
     dbaseIndex index2[] = sortonpos(), index3[];
     long lastpos = 16;
     int want[] = new int[0];
     for(i = 0;i<index2.length;++i) {     // scan to index or first out-of-place one
        if (index2[i].name == null || index2[i].pos != lastpos)  break;
        lastpos = index2[i].pos + index2[i].len;
     }
     alreadycompressedpos = lastpos;
     for(;i<index2.length;++i)   if(index2[i].name != null && index2[i].pos>0 && index2[i].len > 0)  want = u.addint(want, i);
     index3 = new dbaseIndex[want.length];
     for(i = 0;i< want.length;++i)     index3[i] = index2[want[i]];
     return index3;
   }
   dbaseIndex[] sortonpos() {   // sort index into position-in-database order
      dbaseIndex index2[] = new dbaseIndex[indexTot+1];
      dbaseIndex lookup = new dbaseIndex();
      lookup.pos = indexpos;
      lookup.len = indexlen;
      index2[0] = lookup;
      System.arraycopy(index,0,index2,1,indexTot);
      Arrays.sort(index2,comparator1);
      return index2;
   }
   static Comparator comparator1 = new Comparator() {
        public int compare(Object o1, Object o2)  {
          if(((dbaseIndex)o1).pos < ((dbaseIndex)o2).pos) return -1;
          else if(((dbaseIndex)o1).pos > ((dbaseIndex)o2).pos) return 1;
          else return 0;
        }
    };
    void setindexdetails(long pos,int len,int tot) {
      try {
        ByteArrayOutputStream outb = new ByteArrayOutputStream(16);
        DataOutputStream outBuf = new DataOutputStream(outb);
        outBuf.writeLong(indexpos=pos);
        outBuf.writeInt(indexlen=len);
        outBuf.writeInt(indexTot =tot);
        f.seek((long)0);
        f.write(outb.toByteArray());
      }
      catch(IOException e) { }
    }
    void getindexdetails() {
      try {
        byte buf[] = new byte[16];
        f.seek((long)0);
        f.read(buf);
        ByteArrayInputStream inb = new ByteArrayInputStream(buf);
        DataInputStream inBuf = new DataInputStream(inb);
        indexpos = inBuf.readLong();
        indexlen = inBuf.readInt();
        indexTot = inBuf.readInt();
      }
      catch(IOException e) {
        indexpos = -1;
        indexlen = 0;
      }
    }
    void compress() {  // compress if necessary
       long totused = 16 + indexlen, wasted = 0;
       int i,j;
       byte buf[] = null;
       for(i = 0;i<indexTot;++i) {
          totused += index[i].len;
       }
       try {
         wasted = f.length() - totused;
       }
       catch(IOException e) {return;}
       if (wasted > totused  * 4 / 3 || wasted > 5000 || forcecompress)  {  // rb 21/11/06
         dbaseIndex index2[] = needtomove();
         try {
           long currpos = f.length();
           for (i = 0; i < index2.length; ++i) {   // rewrite at end;
                     dbaseIndex dbi = index2[i];
                     f.seek(dbi.pos);
                     buf = new byte[dbi.len];
                     f.read(buf);
                     f.seek(currpos);
                     f.write(buf);
                     dbi.pos = currpos;
                     currpos += dbi.len;
           }
           saveIndex(f.length());                     // save index at end
           currpos = alreadycompressedpos;                    // copy back into new positions
           for (i = 0; i < index2.length; ++i) {
             dbaseIndex dbi = index2[i];
             f.seek(dbi.pos);
             buf = new byte[dbi.len];
             f.read(buf);
             f.seek(currpos);
             f.write(buf);
             dbi.pos = currpos;
             currpos += dbi.len;
           }
           saveIndex(currpos);                     // save index at end of moved ones
           f.setLength(currpos+indexlen);
         }
         catch(IOException e) {return;}
       }
    }      // end rb v4.2 =======================================================================
   //-------------------------------------------------------------
   boolean saveIndex(long saveat) {           // start rb v4.2
      long pos;
      int ilen;
      try{
         ByteArrayOutputStream outb = new ByteArrayOutputStream(100000);
         ObjectOutputStream outBuf  = new ObjectOutputStream(outb);
         for(int i = 0;i<indexTot;++i) {
            outBuf.writeObject(index[i]);
         }
         byte buf[] = outb.toByteArray();
         ilen = buf.length;
         pos = saveat>0 ? saveat : findslot(ilen);
         f.seek(pos);
         f.write(buf);
         setindexdetails(pos,ilen,indexTot);
      }
      catch(IOException e) {return false;}
      return true;
   }                                          // end v4.2
   //-------------------------------------------------------------
   public static String typeString(byte type) {
      switch(type) {
         case TOPIC: return("topic");
         case WAV : return("spoken word");
         case STUDENT : return("student");
         case TOPICTREE : return("topic tree");
         case GAMETREE : return("game tree");
         case GAME: return("game");
         case IMAGE : return("pictures");
         case WORDSEARCH : return("word search");
         case PROGRAM: return("program");
         case TEXT: return("text");
      }
      return null;
   }
//startPR2007-11-19^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//   public static boolean same(String db1, String db2,String name, byte type) {
//     try {
//      db dba= get(db1,false);
//      db dbb= get(db2,false);
//      int num1 = dba.query(name,type);
//      int num2 = dbb.query(name,type);
//      if(num1<0 || num2 < 0 || dba.index[num1].len != dbb.index[num2].len) return false;
//      byte[] buf1 = new byte[dba.index[num1].len];
//      dba.f.seek(dba.index[num1].pos);
//      dba.f.read(buf1);
//      byte[]buf2 = new byte[dbb.index[num2].len];
//      dbb.f.seek(dba.index[num2].pos);
//      dbb.f.read(buf2);
//      return(Arrays.equals(buf1,buf2));
//     }
//     catch(IOException e) {
//      return false;
//     }
//   }
   // need to get finish with one database before starting on the other because otherwise
   // can cause an OutOfMemory Exception.
    public static boolean same(String db1, String db2,String name, String name2, byte type, boolean compress) {
       try {
         byte buf1[];
         byte buf2[];
         int len1;
         int len2;
         db dbb= get(db2,false);
         try {dbb.lock.release();dbb.lock=null;}catch(Exception e2) {}
         int num2 = dbb.query(name2,type);
         if(num2<0)return false;
         len2 = dbb.index[num2].len;
         buf2 = new byte[dbb.index[num2].len];
         dbb.f.seek(dbb.index[num2].pos);
         dbb.f.read(buf2);
         if(compress)  dbb.compress();
         dbb.close();
         dbb = null;
         db dba= get(db1,false);
         try {dba.lock.release();dba.lock=null;}catch(Exception e2) {}
         int num1 = dba.query(name,type);
         if(num1<0)return false;
         len1 = dba.index[num1].len;
         if(len1 != len2) return false;
         buf1 = new byte[dba.index[num1].len];
         dba.f.seek(dba.index[num1].pos);
         dba.f.read(buf1);
         if(compress)dba.compress();
         dba.close();
         dba = null;
         return(Arrays.equals(buf1,buf2));
       }
       catch(IOException e) {
        return false;
       }
     }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   //-------------------------------------------------------------   // removed rb v4.2
//   void compress() {  // compress if necessary
//      long totused = 0,maxpos = 16;
//      byte buf[];
//      File fi = new File(name + ".shb");
//      RandomAccessFile ft = null;
//      for(int i = 0;i<indexTot;++i) {
//         if(index[i].pos != 0 && maxpos < index[i].pos + index[i].len)
//              maxpos = index[i].pos + index[i].len;
//         totused += index[i].len;
//      }
//      if (maxpos > (totused + 16) * 4 / 3  || forcecompress && maxpos > totused+16)  {  // rb 21/11/06
//         try {
//             ft = new RandomAccessFile(fi,"rw");
//             ft.seek((long)0);
//             f.writeLong(indexpos=(long)0);     // rb v4.2
//             f.writeInt(indexlen=(int)0);       // rb v4.2
//             ft.writeInt(indexTot);
//             for(int i = 0;i<indexTot;++i) {
//               if(index[i].pos != 0) {
//                  buf = new byte[index[i].len];
//                  f.seek(index[i].pos);
//                  f.read(buf);
//                  index[i].pos = ft.getFilePointer();
//                  ft.write(buf);
//               }
//            }
//            if(saveIndex(ft)) {
//              if(sharedfile) {
//                // start rb 2/3/07 -----------------------------------------------------------
//                ft.close();
//                ft = null;
//                File fi2 = fi;
//                fi = new File(name + ".shc");
//                fi2.renameTo(fi);
//                ft = new RandomAccessFile(fi,"r");
//                ft.seek(0);
//                long len = ft.length(),tot=0;
//                int bufflen = (int)Math.min(len,1000000), lenr;
//                byte buff[] = new byte[bufflen];
//                f.seek(0);
//                f.setLength(0);
//                while(tot<len) {
//                  lenr = ft.read(buff);
//                  f.write(buff,0,lenr);
//                  tot += lenr;
//                  f.setLength(tot);
//                }
//                // end rb 2/3/07 -----------------------------------------------------------
//                ft.close();
//                ft = null;
//                fi.delete();
//              }
//              else {
//                ft.close();
//                ft = null;
//                f.close();
//                f = null;
//                database.delete();
//                fi.renameTo(database);
//                f = new RandomAccessFile(database, "rw");
//              }
//            }
//            else {
//               ft.close();
//            }
//         }
//         catch(IOException e){
//            try{if(ft != null) ft.close();} catch(IOException ioe) {}
//            return;
//         }
//      }
//   }
   //---------------------------------------------------------------------
   public static void close(String dbname) {
      db db1 = get(dbname,false);
      if(db1 != null) db1.close();
   }
   //---------------------------------------------------------------------
      // to invoke, use just name, not full path
   public static void rename(String dbname,String newname) {
      db db1 = get(dbname,false);
      if(db1 != null) {
        File oldfile = db1.database;
        String s = db1.database.getAbsolutePath();
        s = s.substring(0,s.lastIndexOf(File.separatorChar)+1);
        db1.close();
        oldfile.renameTo(new File(s+newname+".sha"));
      }
   }

   public void close() {finalize(); ok = false;}

   //---------------------------------------------------------------------

   static byte getType(Object o) {
      if(o instanceof topic)   return TOPIC;
      if(o instanceof student) return STUDENT;
      if(o instanceof saveTree1) return TOPICTREE;
      if(o instanceof saveSharkImage) return IMAGE;
      if(o instanceof indexclass) return WORDSEARCH;
      if(o instanceof saveprogram) return PROGRAM;
      return 0;
   }

   /**
  * Find in database
  * @param dbname The database name
  * @param name Item to be found
  * @param type The type of the item
  * @return buffer or null
  */
   public static Object find(String dbname,String name, byte type) {
      db db1 = get(dbname,false);
      if(db1 == null) return null;
      Object o = db1.find(name,type);
      if(db1.sharedfile) db1.close();
      return o;
   }
//startPR2009-12-23^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  public static Object find(String dbname,String[] name, byte type) {
     db db1 = get(dbname,false);
     if(db1 == null) return null;
     Object o = null;
     for(int i = 0; i < name.length; i++){
       o = db1.find(name[i],type);
       if(o!=null)
         break;
     }
     if(db1.sharedfile) db1.close();
     return o;
  }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR

   public Object find(String name, byte type)  {
     int num = query(name,type);
     if(num<0) return null;
     return find(num);
   }
   //---------------------------------------------------------------------
          // find wav file in database
          // returns buffer or null
   public static byte[] findwav(String dbname,String name) {
      db db1 = get(dbname,false);
      if(db1 == null) return null;
      byte[]  ret = db1.findwav(name);
      if(db1.sharedfile) db1.close();
      return ret;
   }
   public byte[] findwav(String name)  {
   int num = query(name,WAV);
   if(num<0) return null;
   return findwav(num);
   }
    /**
    * test for presence in database open just for reading
    * @param dbname Database in which to look
    * @param name Item to look for
    * @param type Tupe of the item being looked for
    * @return returns -1 if database not found else rel number
    */
    public static int query(String dbname,String name, byte type) {
      db db1 = get(dbname,false);
      if(db1 == null) return -1;
      int ret =  db1.query(name,type);
      if(db1.sharedfile) db1.close();
      return ret;
   }
   public int query(String name, byte type){
     dbaseIndex d = new dbaseIndex();
     d.name = name.toLowerCase();
     d.type = type;
     if(index == null || index.length == 0) return -1;
     return java.util.Arrays.binarySearch(index,d,comp2);
   }
   //--------------------------------------------------------------------
   static Comparator comp2 = new Comparator() {
       public int compare(Object o1, Object o2)  {
         int i = ((dbaseIndex)o1).name.toLowerCase().compareTo(((dbaseIndex)o2).name);
         if(i==0) {
            if(((dbaseIndex)o1).type<((dbaseIndex)o2).type) return -1;
            if(((dbaseIndex)o1).type>((dbaseIndex)o2).type) return 1;
            return 0;
         }
         else return i;
       }
    };
   //---------------------------------------------------------------------
          // find by rel number in database open just for reading
          // returns -1 off end  0=not found  else length read
   public static Object find(String dbname,int num) {
      db db1 = get(dbname,false);
      if(db1 == null) return null;
      Object o = db1.find(num);
      if(db1.sharedfile) db1.close();
      return o;
   }

//startPR2004-08-27^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     /*synchronized needed for some problem found on the Mac. eg unwanted game icon*/
    public synchronized Object find(int num)
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   {
   byte buf[];
   try{
     if(index[num].len == 0)
       return null;
     buf = new byte[index[num].len];
     f.seek(index[num].pos);
     f.read(buf);
   }
   catch(IOException e){
     return null;
   }
   if(buf !=null) {
      try {
          InputStream s = new ByteArrayInputStream(buf);
          ObjectInputStream sr = new ObjectInputStream(s);
          Object o = sr.readObject();
          if(o instanceof saveTree1.saveTree2) return new saveTree1((saveTree1.saveTree2)o);
          return o;
      }
      catch(StreamCorruptedException e) {
         return null;
      }
      catch(IOException e) {
        return null;
      }
      catch(ClassNotFoundException e) {
         return null;
      }
//startPR2008-08-05^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      catch(NegativeArraySizeException e) {
         return null;
      }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    }
   else return null;
  }
   //---------------------------------------------------------------------
          // find by rel number in database open just for reading
          // returns -1 off end  0=not found  else length read
   public static byte[] findwav(String dbname,int num) {
      db db1 = get(dbname,false);
      if(db1 == null) return null;
      byte[] o = db1.findwav(num);
      if(db1.sharedfile) db1.close();
      return o;
   }
   public byte[] findwav(int num)
   {
   byte buf[];
   try{
     if(index[num].len == 0) return null;
     buf = new byte[index[num].len];
     f.seek(index[num].pos);
     f.read(buf);
   }catch(IOException e){return null;}
   return buf;
  }
  public static String[] listsort(String dbname, byte type) {
     String o[] =  db.list(dbname,type);
     if(type == db.TOPICTREE) {
       String o2[] = (String[]) find(dbname,"order",db.TEXT);
       if(o2 != null && o != null && o2.length == o.length) return o2;
     }
     if(type == db.GAMETREE) {
       String o2[] = (String[]) find(dbname,"gorder",db.TEXT);
       if(o2 != null && o != null && o2.length == o.length) return o2;
     }
     return o;
  }
  /**
   *
   * @param dbname
   * @param type
   * @return Array containing the contents of database which are of the specified type
   */
  public static String[] list(String dbname, byte type) {
      db db1 = get(dbname,false);
      if(db1 == null) return (new String[0]);
      String o[] =  db1.list(type);
      if(db1.sharedfile) db1.close();
      return o;
   }
   /**
    *
    * @param type
    * @return Array containing the contents of database which are of the specified type
    */
   public String[] list(byte type)
   {
   int tot=0;
   String[]  s;
   for(int i = 0;i<indexTot;++i) {
       if(index[i].type == type) ++tot;
   }
   s = new String[tot];
   tot=0;
   for(int i = 0;i<indexTot;++i) {
      if(index[i].type == type) s[tot++]=index[i].name;
   }
   return s;
   }
   /**
    * Searches for the type passed and returns true if found.
    * @param dbname Name of database to be searched.
    * @param type Type being searched for.
    * @return True if type found otherwise false.
    */
   public static boolean anyof(String dbname, byte type) {
      db db1 = get(dbname,false);
      if(db1 == null)
        return false;
      boolean ret = db1.anyof(type);
      if(db1.sharedfile) db1.close();
      return ret;
   }
   public boolean anyof(byte type)
   {
   int tot=0;
   String[]  s;
   for(int i = 0;i<indexTot;++i) {
       if(index[i].type == type) return true;
   }
   return false;
   }

   public static String[] list(String dbname, byte type,String start) {
      db db1 = get(dbname,false);
      if(db1 == null) return (new String[0]);
      String o[] = db1.list(type,start);
      if(db1.sharedfile) db1.close();
      return o;
   }
   public String[] list(byte type,String start) {
      int tot=0,i;
      String s[],startl = start.toLowerCase();
      int len = start.length();

      int startpos = query(startl,type);
      if(startpos<0) startpos = -startpos-1;
      for(i = startpos;i<indexTot;++i) {
        if(index[i].name.length() < len || !index[i].name.substring(0,len).toLowerCase().equals(startl)) break;
        if(index[i].type == type) ++tot;
      }
      int endpos = i;
      s = new String[tot];
      tot=0;
      for(i = startpos;i<endpos;++i) {
        if(index[i].type == type)
               s[tot++] = index[i].name;
      }
      return s;
   }
  //---------------------------------------------------------------------
       // change key on named entry
   public static boolean rename(String dbname,String oldname,String newname, byte type) {
      db db1 = get(dbname,true);
      if(db1 == null) return false;
      boolean o = db1.rename(oldname,newname,type);
      if(db1.sharedfile) db1.close();
      else db1.tclose();
      return o;
   }

   public boolean rename(String oldname,String newname,byte type)  {
   if(oldname.equals(newname)) return false;
   int num1 = query(oldname,type);
   int num2 = query(newname,type);
   dbaseIndex d;
   if(num1<0) return false;
   if(oldname.equalsIgnoreCase(newname)) {
      index[num1].name = newname;
   }
   else {
     if (num2 >= 0)   return false;
     d = index[num1];
     d.name = new String(newname);
     num2 = -num2 - 1;
     if (num2 < num1) {
       System.arraycopy(index, num2, index, num2 + 1, num1 - num2);
       index[num2] = d;
     }
     else
       if (num2 > num1) {
         System.arraycopy(index, num1 + 1, index, num1, num2 - num1 - 1);
         index[num2 - 1] = d;
       }
   }
   needsaveindex = changed = true;
   if(type == TOPIC || type == TOPICTREE) {
      deleteAll(WORDSEARCH);
   }
   return true;
   }
  //---------------------------------------------------------------------
       // add or change index entry - replaces if alphabetic and already there
   public static boolean updatewav(String dbname,String name,byte[] o) {
      db db1 = get(dbname,true);
      if(db1 == null) return false;
      boolean ret = db1.updatewav(name,o);
      if(db1.sharedfile) db1.close();
      else db1.tclose();
      return ret;
   }
   public boolean updatewav(String name, byte[] o)
   {
   byte type = WAV;
   boolean xnewf = false;
   int i,num = indexTot;
   int j;
   byte[] buf = null;

   num = query(name,type);
   if(num<0) {xnewf = true; num = -num - 1;}

   if(xnewf) {
            //  enlarge index if necessary
       savetype = 'd';
       savenum = num;
       dbaseIndex index2[] = new dbaseIndex[indexTot+1];
       System.arraycopy(index,0,index2,0,num);
       if(num<indexTot) System.arraycopy(index,num,index2,num+1,indexTot-num);
       index = index2;
       ++indexTot;
   }
   else {
     savetype = 'u';
     savenum = num;
     saveindex = index[num];
   }
   index[num] =  new dbaseIndex();
   index[num].name = new String(name);
   if(o!=null) index[num].len = o.length;
   index[num].type = type;
   if(index[num].len > 0 && o != null) try{
     f.seek(index[num].pos = findslot(o.length));                 // rb v4.2
     f.write(o);
   }
   catch(IOException e){
      return false;
   }
   else index[num].pos = 0;
   needsaveindex = changed = true;
   return true;
   }
  //---------------------------------------------------------------------
   
public static class backUpPublicTopics implements Runnable{
  public void run(){
       lastPublicTopicsSave = System.currentTimeMillis();
         progress_base p = new progress_base(sharkStartFrame.mainFrame, shark.programName,
                                           "Backing up",
                                           new java.awt.Rectangle(sharkStartFrame.mainFrame.getWidth()/8,
                                                         sharkStartFrame.mainFrame.getHeight()*1/5,
                                                         (sharkStartFrame.mainFrame.getWidth()/2),
                                                         (sharkStartFrame.mainFrame.getHeight()/5)));
       Calendar cal = new GregorianCalendar();
       String fname = "publictopicsBackup"+cal.get(Calendar.YEAR)+"-"+String.valueOf(cal.get(Calendar.MONTH)+1)
                +"-"+zeroLeftFill(cal.get(Calendar.DAY_OF_MONTH), 2)+"_"+zeroLeftFill(cal.get(Calendar.HOUR_OF_DAY), 2)+
               "h-"+zeroLeftFill(cal.get(Calendar.MINUTE), 2)+"m"+"__"+String.valueOf(System.currentTimeMillis())+".sha";
       String sFolderPath = sharkStartFrame.sharedPathplus+"publictopicsBackups";
       java.io.File f = new java.io.File(sFolderPath);
       String bckups[] = f.list();
       if(bckups!=null && bckups.length > 9){
           Arrays.sort(bckups);
           new java.io.File(sFolderPath+shark.sep+bckups[0]).delete();
       }
       if(!f.exists())f.mkdir();
       u.copyfile(new java.io.File(sharkStartFrame.publicPathplus+"publictopics.sha"), new java.io.File(sFolderPath+shark.sep+fname));
       p.disposeIn(500);
  }
  
   String zeroLeftFill(int i, int len)
   {   
       String s = String.valueOf(i);
       while(s.length()<len) s = "0"+s;
       return s;
   }   
}   
   
   
       // add or change index entry - replaces if alphabetic and already there
   public static boolean update(String dbname,String name,Object o, byte type) {
      if(!shark.production && type == db.TOPIC && (lastPublicTopicsSave==0 || (System.currentTimeMillis()-lastPublicTopicsSave > 10000 ))) {
         Thread bupt = new Thread(new backUpPublicTopics());
         bupt.start();
         while(bupt.isAlive())
            u.pause(100);
      }
      db db1 = get(dbname,true);
      if(db1 == null) return false;
      boolean ret =  db1.update(name,o,type);
      if(db1.sharedfile) db1.close();
      else db1.tclose();
      return ret;
   }
   public boolean update(String name,  Object o, byte type)
   {
     boolean xnewf = false;
     int i, num = indexTot;
     int j;
     byte[] buf = null;
     num = query(name,type);
     if(num<0) {
       xnewf = true; num = -num - 1;
     }
     if(xnewf) {
            //  enlarge index if necessary
       savetype = 'd';
       savenum = num;
       dbaseIndex index2[] = new dbaseIndex[indexTot+1];
       System.arraycopy(index,0,index2,0,num);
       if(num<indexTot) System.arraycopy(index,num,index2,num+1,indexTot-num);
       index = index2;
       ++indexTot;
   }
   else {
     savetype = 'u';
     savenum = num;
     saveindex = index[num];
   }
   index[num] =  new dbaseIndex();
   index[num].name = new String(name);
   if(o != null) {
      try {
         ByteArrayOutputStream s = new ByteArrayOutputStream();
         ObjectOutputStream sr = new ObjectOutputStream(s);
         sr.writeObject(o);
         buf = s.toByteArray();
         index[num].len = buf.length;
         index[num].type = (type>0)?type:getType(o);
      }
      catch(IOException e) {
         return false;
      }
    }
   else          {index[num].len = 0; index[num].type = 0;}
   if(index[num].len > 0 && buf != null) try{
     long newpos = findslot(buf.length);      //  rb v4.2
     f.seek(newpos);                          //  rb v4.2
     index[num].pos = newpos;                 //  rb v4.2
     f.write(buf);
   }
   catch(IOException e){
      return false;
   }
   else index[num].pos = 0;
   needsaveindex = changed = true;
   if(type == TOPIC || type == TOPICTREE) {
      deleteAll(WORDSEARCH);
   }
   return true;
   }
   //---------------------------------------------------------------------
   public static void delete(String dbname,int num) {
      db db1 = get(dbname,true);
      if(db1 == null) return;
      db1.delete(num);
      if(db1.sharedfile) db1.close();
      else db1.tclose();
   }
   public void delete(int num)
   {
   --indexTot;
   saveindex = index[num];
   byte deltype = index[num].type;
   savenum = num;
   savetype = 'a';
   dbaseIndex index2[] = new dbaseIndex[indexTot];
   System.arraycopy(index,0,index2,0,num);
   if(num<indexTot) System.arraycopy(index,num+1,index2,num,indexTot-num);
   index = index2;
   needsaveindex = changed = true;
   if(deltype == TOPIC || deltype == TOPICTREE) {
      deleteAll(WORDSEARCH);
   }
   }
   //---------------------------------------------------------------------
   public static void delete(String dbname,String name, byte type) {
      db db1 = get(dbname,true);
      if(db1 == null) return;
      db1.delete(name,type);
      if(db1.sharedfile) db1.close();
      else db1.tclose();
   }
   public void delete(String name,byte type)
   {
   int i = query(name,type);
   if(i>=0) delete(i);
   }
   //---------------------------------------------------------------------
   public static void deleteAll(String dbname, byte type) {
      db db1 = get(dbname,true);
      if(db1 == null) return;
      db1.deleteAll(type);
      if(db1.sharedfile) db1.close();
   }
   public void deleteAll(byte type)
   {
   String todel[] = list(type);
   for(short i=0;i<todel.length;++i) {
      delete(todel[i],type);
   }
   }
   //---------------------------------------------------------------------
   public static void deleteAll(String dbname, byte type, String start) {
      db db1 = get(dbname,true);
      if(db1 == null) return;
      db1.deleteAll(type,start);
      if(db1.sharedfile) db1.close();
   }
   public void deleteAll(byte type, String start)
   {
   String todel[] = list(type,start);
   for(short i=0;i<todel.length;++i) {
      delete(todel[i],type);
   }
   }
   //---------------------------------------------------------------------
   public static void deleteAll(String dbname) {
      db db1 = get(dbname,true);
      if(db1 == null) return;
      db1.indexTot = 0;
      db1.index = new dbaseIndex[0];
      db1.needsaveindex = db1.changed = true;
      if(db1.sharedfile) db1.close();
      else db1.tclose();
   }
   //---------------------------------------------------------------------
  private static class selectdb implements FilenameFilter {
     public boolean accept(File dir, String name) {
//startPR2006-04-30^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       name = name.toLowerCase();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        return name.length() > 4
          && name.substring(name.length() - 4).equalsIgnoreCase(".sha")
          && (sharkStartFrame.extracourses == null
               ||  name.length() < sharkStartFrame.extracourses.length()+4
               || !name.substring(0,sharkStartFrame.extracourses.length()).equalsIgnoreCase(sharkStartFrame.extracourses))
//startPR2009-07-17^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//           && name.indexOf("keypad.") != 0
//           && name.indexOf("definitions") != 0
//           && name.indexOf("plistrec.") != 0
//           && name.indexOf("plistRec.") != 0
//           && name.indexOf("KEYPAD.") != 0
//           && name.indexOf("DEFINITIONS") != 0
//           && name.indexOf("PLISTREC.") != 0
//           && name.indexOf("OPTIONS.") != 0
//           && name.indexOf("options.") != 0;
           && name.indexOf("publictopics2.") != 0
           && name.indexOf("keypad.") != 0
           && name.indexOf("definitions") != 0
           && name.indexOf("plistrec.") != 0
           && !name.startsWith("~")
//startPR2009-11-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
           && name.indexOf(WebAuthenticate_base.logfile.toLowerCase()+".") != 0
//endPRauth^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
           && name.indexOf("options.") != 0;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     }
  }
  //---------------------------------------------------------------------
 private static class selectdb2 implements FilenameFilter {
    String want; int wantl;
    selectdb2(String starts) {
      super();
      want = starts;
      wantl = want.length();
    }
    public boolean accept(File dir, String name) {
       return name.length() > 4+wantl
         && name.substring(name.length() - 4).equalsIgnoreCase(".sha")
         && name.substring(0,wantl).equalsIgnoreCase(want);
    }
 }
   //------------------------------------------------------------
       // excludes special public files
   public static String[] dblistx(File dir[]) {
     int tot=0;
     String[] s = dblist(dir);
     for(short i=0;i<s.length;++i)  {
        s[i] = s[i].toLowerCase();
        if(s[i].indexOf("publicsay") >= 0
           || s[i].indexOf("publicimage") >= 0) {
           ++tot;
        }
     }
     if(tot== 0) return(s);
     String ret[] = new String[s.length-tot];
     int j=0;
     for(short i=0;i<s.length;++i) {
        if(s[i].indexOf("publicsay") < 0
               && s[i].indexOf("publicimage") < 0 ) {
           ret[j++] = s[i];
        }
     }
     return ret;
   }
   //------------------------------------------------------------
   public static String[] dblist(File dir,String startwith) {
      String s[] = new String[0];
      if(dir.isDirectory()) {
            s = dir.list(new selectdb2(startwith));
            for(short j = 0; j < s.length; ++j) {
                s[j] = s[j].substring(0,s[j].length()-4);
            }
      }
      return s;
   }
   //------------------------------------------------------------
   public static String[] dblistnames(File dir) {
      String s[] = new String[0];
      if(dir.isDirectory()) {
            s = dir.list(new selectdb());
            for(short j = 0; j < s.length; ++j) {
                s[j] = s[j].substring(0,s[j].length()-4);
            }
      }
      return s;
   }
   /**
    * If there is a database in the files passed then returns it's path
    * otherwise this creates a new database file at the location indicated
    * @param dir Array of files
    * @return Path and directory of db
    */
   public static String[] dblist(File dir[]) {
      String ret[]= new String[0],ret2[];
      String s[];
      for(short i = 0;i<dir.length; ++i) {
         if(dir[i].isDirectory()) {                             //File is a directory
            s = dir[i].list(new selectdb());
            for(short j = 0; j < s.length; ++j) {
                s[j] = (new File(dir[i],s[j].substring(0,s[j].length()-4))).getAbsolutePath();
            }
            ret2 = new String[ret.length + s.length];
            System.arraycopy(ret,0,ret2,0,ret.length);
            System.arraycopy(s,0,ret2,ret.length,s.length);
            ret = ret2;
         }
         else {
            String nn = dir[i].toString();
            File f = new File(nn  + ".sha");
            if(f.isFile()) ret = u.addString(ret,nn);
         }
      }
      return ret;
   }
   //------------------------------------------------------------
   public static boolean isPublic(String d) {
      return  d.toLowerCase().indexOf("public") >= 0;
   }
   //-------------------------------------------------------------
   public static boolean exists(String databasei) {
      if(!(new File(databasei)).isAbsolute()) {
         if((new File(sharkStartFrame.publicPath,databasei+".sha")).exists())
             return true;
         else if((new File(sharkStartFrame.sharedPath,databasei+".sha")).exists())
             return true;
         else return false;
      }
      else return((new File(databasei+".sha")).exists());
   }
   //-------------------------------------------------------------
   public static boolean create(String name1) {
      db d1 = new db(name1,true);
      if(!d1.ok) {
        u.okmess(u.gettext("db_createfailed","heading"),
                 u.gettext("db_createfailed","message",
//startPR2004-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//                           name1.substring(0,name1.lastIndexOf(sharkStartFrame.separator))));
                           name1.substring(0,name1.lastIndexOf(sharkStartFrame.separator))), sharkStartFrame.mainFrame);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        return false;
      }
      d1.close();
      return true;
   }
   public static void destroy(String name1) {
      close(name1);
      if(!(new File(name1)).isAbsolute()) {
         (new File(sharkStartFrame.sharedPath,name1+".sha")).delete();
       }
       else (new File(name1+".sha")).delete();

   }
   public static void mergetext(String n1,String n2) {
     String ss2[] = db.list(n2, db.TEXT), t1[],t2[],t3[];
     int i,j,k,m;
     try {
       FileOutputStream f = new FileOutputStream(sharkStartFrame.sharedPathplus +
                                                 "textchanges.txt");
       f.write( (byte) '\r');
       f.write( (byte) '\n');
       f.write( ("----------------  " + "changes to PUBLICTEXT" +
                 "  ----------------").getBytes());
       f.write( (byte) '\r');
       f.write( (byte) '\n');
       f.write( (byte) '\r');
       f.write( (byte) '\n');
       for (i = 0; i < ss2.length; ++i) {
         if (db.query(n1, ss2[i], db.TEXT) < 0) {
           db.update(n1, ss2[i], db.find(n2, ss2[i], db.TEXT), db.TEXT);
           f.write(("NEW: " + ss2[i]).getBytes());
           f.write( (byte) '\r');
           f.write( (byte) '\n');
         }
         else {
           t1 = (String[]) db.find(n1, ss2[i], db.TEXT);
           t2 = (String[]) db.find(n2, ss2[i], db.TEXT);
           int oldlen = t1.length;
           jloop:for (j = 0; j < t2.length; ++j) {
             k = t2[j].indexOf('=');
             if (k > 0) {
               String sub = t2[j].substring(0, k + 1);
               for (m = 0; m < t1.length; ++m) {
                 if (t1[m].startsWith(sub))
                   continue jloop;
               }
             }
             t1 = u.addString(t1, t2[j]);
           }
           if (t1.length > oldlen) {
             db.update(n1, ss2[i], t1, db.TEXT);
             f.write(("ADDED TO: " + ss2[i]).getBytes());
             f.write( (byte) '\r');
             f.write( (byte) '\n');
           }
         }
       }
       f.close();
     }catch (IOException e) {}
   }
   public static String[] findtext(String database,String want) {
     String ss2[] = db.list(database, db.TEXT), s[],ret[]=new String[0];
     int i,j,k,m;
     for (i = 0; i < ss2.length; ++i) {
       s = (String[]) db.find(database, ss2[i], db.TEXT);
       for (j = 0; j < s.length; ++j) {
         if(s[j].indexOf(want)>=0) {ret = u.addString(ret,ss2[i]);break;}
       }
     }
     return ret;
   }
   public static void mergeimage(String n1,String n2) {
     String ss2[] = db.list(n2, db.IMAGE), t1[],t2[],t3[];
     int i;
     boolean all = !u.yesnomess("image merge","Add only SPECIAL images with '_' in the name?");
     try {
       FileOutputStream f = new FileOutputStream(sharkStartFrame.sharedPathplus +
                                                 "imagechanges.txt");
       f.write( (byte) '\r');
       f.write( (byte) '\n');
       f.write( ("----------------  " + (all?"new images in PUBLICIMAGE":"new special images in PUBLICIMAGE")+
                 "  ----------------").getBytes());
       f.write( (byte) '\r');
       f.write( (byte) '\n');
       f.write( (byte) '\r');
       f.write( (byte) '\n');
       for (i = 0; i < ss2.length; ++i) {
         if ((all || ss2[i].indexOf('_')>=0) && db.query(n1, ss2[i], db.IMAGE) < 0) {
           db.update(n1,ss2[i], db.find(n2, ss2[i], db.IMAGE), db.IMAGE);
           f.write(ss2[i].getBytes());
           f.write( (byte) '\r');
           f.write( (byte) '\n');
         }
       }
       f.close();
      }catch (IOException e) {}
   }
   public static void mergeimage23() {  // merge (replace) publicimagew into publicimage2
     String ss2[] = db.list("publicimagew", db.IMAGE), t1[],t2[],t3[];
     int i;
     sharkStartFrame.imageNew = null;
     sharkStartFrame.imageChanged = null;
     try {
       FileOutputStream f = new FileOutputStream(sharkStartFrame.sharedPathplus +
                                                 "imagechanges.txt");
       f.write( (byte) '\r');
       f.write( (byte) '\n');
       f.write( ("----------------  " + ("new/changed images in PUBLICIMAGE2")+
                 "  ----------------").getBytes());
       f.write( (byte) '\r');
       f.write( (byte) '\n');
       f.write( (byte) '\r');
       f.write( (byte) '\n');
       for (i = 0; i < ss2.length; ++i) {
           boolean newi = db.query("publicimage2", ss2[i], db.IMAGE) < 0;
//startPR2007-11-19^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//           if(newi || !same("publicimage2","publicimagew",ss2[i],db.IMAGE)) {
                 if(newi || !same("publicimage2","publicimagew",ss2[i],ss2[i],db.IMAGE, false)) {
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
             db.update("publicimage2", ss2[i],
                       db.find("publicimagew", ss2[i], db.IMAGE), db.IMAGE);
             f.write((ss2[i] + (newi ? "    NEW":"")).getBytes());
             f.write( (byte) '\r');
             f.write( (byte) '\n');
             if(newi) sharkStartFrame.imageNew = u.addString(sharkStartFrame.imageNew,ss2[i]);
             else     sharkStartFrame.imageChanged = u.addString(sharkStartFrame.imageChanged,ss2[i]);
           }
       }
       f.close();
      }catch (IOException e) {}
   }
//startPR2008-06-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   public static void mergeallimages(String combo) {
       boolean completeSearch = false;  // if true doesn't match name - checks every pic
       
       
     sharkStartFrame.mainFrame.setTitle(" ");
     String titles = sharkStartFrame.mainFrame.getTitle();
     db.create(sharkStartFrame.sharedPathplus+combo);
     String files[] = new File(sharkStartFrame.sharedPathplus+"mergeimages").list();
     int counter;
     int tot = files.length;
     String filename;
     ArrayList addedImages = new ArrayList();
     ArrayList addedNames= new ArrayList();
     loop:for(int p = 0; p < files.length; p++){
       String lib = new File(files[p]).getName();
       filename = lib;
       counter = 0;
       if(lib.endsWith(".sha")) lib = lib.substring(0,  lib.length()-4);
       else continue loop;
       lib = "mergeimages"+File.separator+lib;
       String ss2[] = db.list(lib, db.IMAGE);
       int i;
       loop1:for (i = 0; i < ss2.length; ++i) {
         boolean add = true;
   //     String imagenam = ss2[i].toLowerCase();
         String imagenam = ss2[i];
         while(imagenam.endsWith(u.phonicsplits)&&imagenam.length()>1)
           imagenam=imagenam.substring(0,imagenam.length()-1);
         for(int k = 0; k < addedImages.size(); k++){
             String comp1 = (String)addedNames.get(k);
             String comp2 = imagenam;
             while(comp1.endsWith(u.phonicsplits)&&comp1.length()>1)
               comp1=comp1.substring(0,comp1.length()-1);
             while(comp2.endsWith(u.phonicsplits)&&comp2.length()>1)
               comp2=comp2.substring(0,comp2.length()-1);
             if(!completeSearch){
                 if(comp1.equalsIgnoreCase(comp2)){
                     // true if a difference
                     if(!sharkImage.addnew((sharkImage.saveSharkImage)addedImages.get(k),
                                           (sharkImage.saveSharkImage)db.find(lib,ss2[i],db.IMAGE))){
                           add = false;
                           continue loop1;
                      }
                 }
             } 
             else{
                  if(!sharkImage.addnew((sharkImage.saveSharkImage)addedImages.get(k),
                                           (sharkImage.saveSharkImage)db.find(lib,ss2[i],db.IMAGE))){
                         if(addedNames.get(k).equals(imagenam)){
                           add = false;
                           continue loop1;
                         }
                  }                 
             }
         }
         while(db.query(combo, imagenam, db.IMAGE)>=0)
           imagenam=imagenam+u.phonicsplits;
         if(add) {
           sharkImage.saveSharkImage sii = (sharkImage.saveSharkImage)db.find(lib, ss2[i], db.IMAGE);
           addedImages.add(sii); 
           addedNames.add(imagenam); 
           db.update(combo, imagenam, sii, db.IMAGE);
           sharkStartFrame.mainFrame.setTitle("Merging image "+String.valueOf((counter++)+1)+" of \""+filename+"\" (file "+
                                              String.valueOf(p+1)+" of "+String.valueOf(tot)+")");
         }
       }
     }
     String ss[] = db.list(combo,db.IMAGE);
     u.okmess(shark.programName, "Completed! "+String.valueOf(ss.length)+" images in finished file", sharkStartFrame.mainFrame);
     sharkStartFrame.mainFrame.setTitle(titles);
   }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR

   public static void mergetopics(String n1,String n2) {
     boolean xxxx = u.yesnomess("Merging publictopics2 into publictopics",
                                "Replace all topics that have a word 'xxxx' in publictopics2 ?",sharkStartFrame.mainFrame);
     boolean replace = xxxx?false:u.yesnomess("Merging publictopics2 into publictopics",
                                "Replace all topics used in publictopics2 ?",sharkStartFrame.mainFrame);
     String ss2[] = db.list(n2, db.TOPICTREE);
     int i;
     if(!xxxx) for (i = 0; i < ss2.length; ++i) {
       if ( db.query(n1, ss2[i], db.TOPICTREE) < 0) {
         Object o = db.find(n2, ss2[i], db.TOPICTREE);
         if(o instanceof saveTree1)
           db.update(n1,ss2[i], ((saveTree1)o).curr, db.TOPICTREE);
         else db.update(n1,ss2[i], o, db.TOPICTREE);
       }
     }
     ss2 = db.list(n2, db.TOPIC);
     String sss[] = null;
     if(xxxx) {
       try {
          FileOutputStream f = new FileOutputStream(sharkStartFrame.sharedPathplus +
                                                    "topicchanges.txt");
          f.write( (byte) '\r');
          f.write( (byte) '\n');
          f.write( ("----------------  " + ("topics added or replaced in PUBLICTOPICS")+
                    "  ----------------").getBytes());
          f.write( (byte) '\r');
          f.write( (byte) '\n');
          f.write( (byte) '\r');
          f.write( (byte) '\n');
          for (i = 0; i < ss2.length; ++i) {
            saveTree1 st = (saveTree1) db.find(n2, ss2[i], db.TOPIC);
            int j = u.findString(st.curr.names, "xxxx");
            if (j >= 0) {
              st.curr.names = u.removeString(st.curr.names, j);
              st.curr.levels = u.removeByte(st.curr.levels, j);
              db.update(n2, ss2[i], st.curr, db.TOPIC);
              boolean new1 = db.query(n1, ss2[i], db.TOPIC) < 0;
              db.update(n1, ss2[i], st.curr, db.TOPIC);
              f.write( (new1 ? ss2[i] + "(new)" : ss2[i]).getBytes());
              f.write( (byte) '\r');
              f.write( (byte) '\n');
            }
          }
           f.close();
       }catch (IOException e) {}
     }
     else {
       if (replace) {
         topic topics[] = sharkStartFrame.mainFrame.topicTreeList.getTopics(n2);
         sss = new String[topics.length];
         for (i = 0; i < topics.length; ++i) {
           sss[i] = topics[i].name;
         }
       }
       for (i = 0; i < ss2.length; ++i) {
         if (!replace && db.query(n1, ss2[i], db.TOPIC) < 0
             || replace && u.findString(sss, ss2[i]) >= 0) {
           Object o = db.find(n2, ss2[i], db.TOPIC);
           if (o instanceof saveTree1)
             db.update(n1, ss2[i], ( (saveTree1) o).curr, db.TOPIC);
           else
             db.update(n1, ss2[i], o, db.TOPIC);
         }
       }
     }
     db.close(n1);
   }
   public static void mergewav(String n1,String n2) {
     String ss2[] = db.list(n2, db.WAV), t1[],t2[],t3[];
     int i;
     for (i = 0; i < ss2.length; ++i) {
       if (db.query(n1, ss2[i], db.WAV) < 0) {
         db.updatewav(n1,ss2[i], db.findwav(n2, ss2[i]));
       }
     }
   }
}
