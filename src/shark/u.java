package shark;

import java.io.*;
import java.util.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
//startPR2007-03-05^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
import javax.swing.tree.*;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR

public class u{
  public static final String million = u.gettext("u_","million");
  public static final String thousand = u.gettext("u_","thousand");
  public static final String hundred = u.gettext("u_","hundred");
  public static final String and = u.gettext("u_","and");
  public static final String a = u.gettext("u_","a");
  public static final String[] phonicscourses = u.splitString(u.gettext("coursespecifics","phonicscourses"));
  public static final String[] defaulthiddencourses = u.splitString(u.gettext("coursespecifics","hiddencourses"));
  public static final String[] nonfastmodecourses = u.splitString(u.gettext("coursespecifics","nonfastmodecourses"));
  public static final String to99[] = u.splitString(u.gettext("u_","to99"),' ');
  public static char phonicsplit = (char)183;
  public static String phonicsplits = new String(new char[]{phonicsplit});
  public static final String vowels = "aeiouAEIOU";
  public static final String vowelsy = "aeiouyAEIOUY";
//startPR2004-09-15^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  public static final String specialVowels[] = u.splitString(u.gettext("specialvowels","combo"));
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  public static final String normal = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ'";
  public static final String normalhyphen = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ'-/";
  public static final String letters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
  public static final String lettersnumbers = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
  public static final String lettershyphen = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ-";
  public static final String lettershyphenslash = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ-/";
  public static final String lowercase = "abcdefghijklmnopqrstuvwxyz";
  public static final String uppercase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
  public static final String numbers = "0123456789";
//startPR2009-09-05^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  public static final String notAllowedInFileNames = "/?*:|<>\\\"";
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//  public static final String[] picSearchAdds = new String[]{"@@","^","@","@@2"};
  public static final String[] picSearchAdds = new String[]{"@@","@","@@2"};
  public static final String ignore[] = u.splitString(u.gettext("ignoreforimage", "ignore"));
  public static final String options = "options";
  public static final char mult = '\u00d7';
  public static final char div  = '\u00f7';
  public static final char commac = ',';
  public static final String pressenter = u.gettext("u_","pressenter");
  public static final String statuses[] = u.splitString(u.gettext("adminstatus", "statuses").replaceFirst("%", shark.programName));
  public static final int STATUS_ADMIN = 0;
  public static final int STATUS_SUBADMIN = 1;
  public static final int STATUS_STUDENT = 2;
  public static final int STATUS_MULTIPLESTUDENT = 3;
  public static final int STATUS_GROUP = 4;
  public static final int STATUS_UNIVERSAL = 5;
  public static final int STATUS_CURRENT = 6;
//startPR2004-10-19^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  // when true blocks exiting a window via the Escape key
  public static boolean blockEscape;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2004-09-13^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    // a flag used to stop commands related to focus
    public static boolean focusBlock = false;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR

//  public static final String choosefont = "Choose font";
  public static Random rr = new Random();
  static final ImageIcon icons[] = new ImageIcon[] {
      new ImageIcon(sharkStartFrame.publicPathplus + "sprites" +   sharkStartFrame.separator + "none.gif"),
      new ImageIcon(sharkStartFrame.publicPathplus + "sprites" +   sharkStartFrame.separator + "some.gif"),
      new ImageIcon(sharkStartFrame.publicPathplus + "sprites" +  sharkStartFrame.separator + "all.gif")};

  static final int NONE=0;
  static final int SOME=1;
  static final int ALL=2;

  public static Image ear, eye;
  public static ImageIcon eari,eyei;
  public static Color brown = new Color(162,75,0);
  public static ProgressMonitor pm;
  public static long pmstart,pmtime;
//  public static Timer pmt;
  public static String  pmname;
  static String togetherlist[] = {"ch","sh","th","ph","ck","qu","gh"};
  static String prefix[] = {"de","un","pre","for","re","dis","up","im","in","bi","tri","cir","tele","auto","ex"};
  static String suffix[] = {"ful","ed","ish","ing","est","ist","able","ible","ly","less","ment","some","ness","tion","ture"};
  static String splitdone[];
  public static String lasttext[],lasttextkey=new String("");
  static long nextgc;
  static public String diclist[];
//startPR2004-10-06^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  public static boolean macBlock = false;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//  static int menuItemWidth = 55;
//  static int menuItemHeight;
//  static MenuElement lastMenuElement;
//startPR2009-08-19^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      might be useful for something
//  public static Component[] findComponents(Component comp, Class c) {
//    ArrayList list = new ArrayList();
//    findComponents(comp, c, list);
//    return (Component[])list.toArray(new Component[list.size()]);
//  }
//  private static void findComponents(Component comp, Class c, java.util.List list) {
//    if(c.isAssignableFrom(comp.getClass())) {
//      list.add(comp);
//    }
//    if(comp instanceof Container) {
//      Component[] comps = ((Container)comp).getComponents();
//      for(int i = 0; i < comps.length; i++) {
//        findComponents(comps[i], c, list);
//      }
//    }
//  }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR

    public static String getHTMLWithFontCSS(Font font, String mess) {
        return getHTMLWithFontCSS(font, mess, null);
    }     
    
    public static String getHTMLWithFontCSS(Font font, String mess, String colorName) {
        StringBuffer style = new StringBuffer("font-family:" + font.getFamily() + ";");
        
        style.append("font-weight:" + ((font.getStyle()==Font.BOLD)?"bold":"normal") + ";");
        if(colorName!=null)
            style.append("color:" + colorName + ";");
        style.append("font-size:" + String.valueOf(font.getSize())    + "pt;");
        return "<html><body style=\"" + style + "\">"
            + mess
            + "</body></html>";
    } 
  
    public static boolean CaseInsensitiveEndsWith(String main, String sub) {
        if(main == null || sub == null)return false;
        main = main.toLowerCase();
        sub = sub.toLowerCase();
        return main.endsWith(sub);
    }      
    
    public static int CaseInsensitiveGetIndexOf(String main, String sub) {
        if(main == null || sub == null)return -1;
        main = main.toLowerCase();
        sub = sub.toLowerCase();
        return main.indexOf(sub);
    }     
  
  
  public static String absoluteToRelative(String s){
      // for files in the usb updates folder we still need absolute path
      if(sharkStartFrame.updateFile==null || !s.startsWith(sharkStartFrame.updateFile.getAbsolutePath())){
          File f;
          if((f=new File(s)).isAbsolute())
              return f.getName();
      }
      return s;
  }

  public static boolean screenResWidthMoreThan(int i){
      return u2_base.screenResWidthMoreThan(i);
  }

  public static boolean screenResHeightMoreThan(int i){
      return u2_base.screenResHeightMoreThan(i);
  }
  
  public static Color parseStringtoColor(String hexstring){ 
      int i = Integer.parseInt(hexstring.substring(1),16); 
      Color color = new Color(i); 
      return color; 
  }  

  public static String getComputerName(){
        String compname = null;
        try{
            compname = java.net.InetAddress.getLocalHost().getHostName();
        }
        catch(java.net.UnknownHostException e){compname = null;}
        catch(Exception e){compname = null;}
        return compname;
  }

      static String getDesktopPath (){
          javax.swing.filechooser.FileSystemView filesys = javax.swing.filechooser.FileSystemView.getFileSystemView();
          return filesys.getHomeDirectory().getAbsolutePath()+(shark.macOS?(shark.sep+"Desktop"):""); 
      }
      
  public static void zipFolder(String src,  String dest, boolean includeParentSrcFolder, boolean anonymous){
            try {
              java.util.zip.ZipOutputStream zos = new java.util.zip.ZipOutputStream(new FileOutputStream(dest));
              zipDir(src, includeParentSrcFolder, anonymous, zos);
              zos.close();
            }
            catch (Exception ee) {
              ee.printStackTrace();
            }
  }


  static void zipDir(String dir2zip, boolean includeParentSrcFolder, boolean anonymous, java.util.zip.ZipOutputStream zos) {
    String users[] = null;
    if(anonymous){
        users = db.dblistnames(sharkStartFrame.sharedPath);
    }
    try {
      //create a new File object based on the directory we
      File zipDir = new File(dir2zip);
      //get a listing of the directory content
      String[] dirList = zipDir.list();
      byte[] readBuffer = new byte[2156];
      int bytesIn = 0;
      //loop through dirList, and zip the files
      loop1: for (int i = 0; i < dirList.length; i++) {
        File f = new File(zipDir, dirList[i]);
        String name = f.getName();
        //System.out.println(zipDir+"\t"+dirList[i]);
        //System.out.println(f.getAbsolutePath());
        if (f.isDirectory()) {
            if(anonymous){
              if(name.equalsIgnoreCase(sharkStartFrame.mainFrame.recordsFolderName))continue;
              if(name.equalsIgnoreCase(sharkStartFrame.mainFrame.resourcesFolderName))continue;
              if(name.equalsIgnoreCase(Backup_base.WS+"backups"))continue;
            }

          // System.out.println(f.getName() +" is a dir");
          //if the File object is a directory, call this
          //function again to add its content recursively
          String filePath = f.getPath();
          zipDir(filePath, includeParentSrcFolder, anonymous, zos);
          //loop again
          continue;
        }
        //if we reached here, the File object f was not a directory
        //create a FileInputStream on top of f
        if(anonymous){
            if(name.equalsIgnoreCase("admin_lists"))continue;
            if(name.endsWith(".lock"))continue;
            if(name.endsWith(".nlock"))continue;
            if(name.endsWith(".nlock2"))continue;
            if(name.equalsIgnoreCase("plistrec.sha"))continue;
            for(int j = 0; users!=null && j < users.length; j++){
                if(name.equalsIgnoreCase(users[j]+".sha"))
                    continue loop1;
            }
        }

        FileInputStream fis = new FileInputStream(f);
        //create a new zip  entry
        // System.out.println(f.getPath().substring(f.getPath().indexOf(File.separatorChar) ));
        java.util.zip.ZipEntry anEntry;
        String entrystr =  f.getPath().substring(f.getPath().
            indexOf(File.separatorChar) + 1);
        if(includeParentSrcFolder) anEntry = new java.util.zip.ZipEntry(entrystr);
        else anEntry = new java.util.zip.ZipEntry(entrystr.substring(entrystr.
            indexOf(File.separatorChar) + 1));

        //place the zip entry in the ZipOutputStream object
        zos.putNextEntry(anEntry);
        //now write the content of the file to the ZipOutputStream
        while ( (bytesIn = fis.read(readBuffer)) != -1) {
          zos.write(readBuffer, 0, bytesIn);
        }
        //close the Stream
        fis.close();
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  
  public static void updateUser(String name){
               String ss[] = db.list(name, db.WAV);
               String sstopics[] = db.list(name, db.TOPIC);            
               String resdb = sharkStartFrame.resourcesPlus+
                name + sharkStartFrame.resourcesFileSuffix;

               for(int i = 0; sstopics!=null && i < sstopics.length; i++){
                   saveTree1 newsave = (saveTree1)db.find(name,sstopics[i],db.TOPIC);
                   if(newsave==null || newsave.curr==null || newsave.curr.names == null)continue;
                   saveTreeWordList workingList = (saveTreeWordList)db.find(resdb,sstopics[i],db.TOPIC);
                   if(workingList == null || !Arrays.equals(newsave.curr.names, workingList.names)){
                       workingList = new saveTreeWordList();
                       workingList.names = newsave.curr.names;
                       workingList.recs = new byte[newsave.curr.names.length][];
                       workingList.adminlist = true;
                       workingList.type = OwnWordLists.TYPE_NORMAL;
                       workingList.levels = newsave.curr.levels;
                   }
                   workingList.adminlist = true;
                   if(workingList.recs==null || workingList.recs.length!=workingList.names.length)
                       workingList.recs = new byte[newsave.curr.names.length][];
                   boolean update = false;
                   for(int k = 0;  k < workingList.names.length; k++){
                       if(workingList.recs[k] == null && u.findString(ss, workingList.names[k])>=0){
                           workingList.recs[k] = db.findwav(name, workingList.names[k]);
                           update = true;
                       }
                   }
                   if(update){
                        sharkTree tt = new sharkTree();
                        tt.set(tt.root,sstopics[i]);
                        for(int k=1;k<newsave.curr.names.length;++k) {
                           tt.addChild(tt.root,newsave.curr.names[k]);
                        }
                        saveTree1 st = new saveTree1(tt,tt.root);
                        st.curr.adminlist = true;
                        st.curr.levels = newsave.curr.levels;
                        st.curr.type = OwnWordLists.TYPE_NORMAL;
                        db.update(name, sstopics[i], st.curr,db.TOPIC);
                        db.update(resdb, sstopics[i], workingList, db.TOPIC);
                   }
               }

               for(int i = 0; ss!=null && i < ss.length; i++){
                 byte b[] = db.findwav(name, ss[i]);
                 if(b!=null){
                     db.delete(name, ss[i], db.WAV);
                 }
               }
      /*
      String topicnames[] = db.list(name, db.TOPIC);
      for(int i = 0; topicnames!=null && i < topicnames.length; i++){
          saveTreeWordList stwl = new saveTreeWordList();
          saveTree1 newsave = (saveTree1)db.find(name,topicnames[i],db.TOPIC);
          stwl.names = newsave.curr.names;
          stwl.levels = newsave.curr.levels;
          stwl.adminlist = newsave.curr.adminlist;
          stwl.extrarecs = new byte[stwl.names.length][];
          stwl.recs = new byte[stwl.names.length][];
          stwl.pics = new byte[stwl.names.length][];
          if(newsave != null) {
              boolean doneone = false;
              for(int j = 0; newsave.curr.names!=null && j < newsave.curr.names.length; j++){
                 byte b[] = db.findwav(name, newsave.curr.names[i]);
                 if(b!=null){
                     doneone = true;
                     db.updatewav(sharkStartFrame.resourcesdb, OwnWordLists.POOLPREFIX+newsave.curr.names[i], b);
                     stwl.recs[i] = b;
                     db.delete(name, newsave.curr.names[i], db.WAV);
                 }
              }
              if(doneone)
                  db.update(sharkStartFrame.resourcesdb, topicnames[i], stwl, db.TOPIC);
          }

      }
       */

  }

  public static String[] getSystemClipboard(){
    java.awt.datatransfer.Transferable t = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
    try {
        if (t != null && t.isDataFlavorSupported(java.awt.datatransfer.DataFlavor.stringFlavor)) {
            String text = (String)t.getTransferData(java.awt.datatransfer.DataFlavor.stringFlavor);
            String ss[] = u.splitString(text, "\n");
            for(int i = ss.length - 1; i >= 0; i--){
                ss[i] = ss[i].trim();
                if(ss[i].equals(""))ss = u.removeString(ss, i);
            }
            return ss;
        }
    } catch (java.awt.datatransfer.UnsupportedFlavorException e) {
    } catch (IOException e) {
    }
    return null;
  }


  

  
  
      public static String[] readFile(String path){
        String ret[] = null;
        try{
     //       FileInputStream fstream = new FileInputStream(path);
     //       DataInputStream in = new DataInputStream(fstream);
    //        BufferedReader br = new BufferedReader(new InputStreamReader(in));
           BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path), "iso-8859-1"));
           String str;
           while ((str = br.readLine()) != null)   {
               if(ret==null)ret=new String[]{};
 //              ret = u.addString(ret, str);
               ret = u.addString(ret, str.replace("Ã‚Â·", "Â·"));
            }
            br.close();
        }catch (Exception e){
        }
        return ret;
    }


public static String convertForURL(String s)
{
   return s.replaceAll(" ", "%20");
}
//startPR2007-09-03^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  public static void launchWebSite(String url){
    u2_base.launchWebSite(url, gettext("launchweb", "error"));
  }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2007-10-30^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  public static void launchFile(String url, boolean isvideo){
    u2_base.launchFile(url, isvideo);
  }

  public static void launchMailto(String url){
    try{
      if (shark.macOS) {
        Runtime.getRuntime().exec("open mailto:" + url);
      }
      else {
        Runtime.getRuntime().exec(
            "rundll32.exe url.dll,FileProtocolHandler " + url);
      }
    }
    catch(Exception e){
      e.printStackTrace();
    }
  }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2007-11-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  public static boolean isdefaultfont(String fname) {
    if (fname.equalsIgnoreCase(sharkStartFrame.defaultfontname.getName()))
      return true;
    else if (fname.equalsIgnoreCase(sharkStartFrame.defaultinfantfontname.getName()))
      return true;
    return false;
  }
  
   public static int isNameADuplicate(String s){
       if(s.charAt(s.length()-1)!=')')return -1;
       int k = s.lastIndexOf("(");
       String num = null;
       int ret = -1;
       if(k>=0 && k<s.length()-1){
           num = s.substring(k+1, s.length()-1);
       }
       if(num==null)return -1;
       try{
           ret = Integer.parseInt(num);
       }
       catch(Exception e){
           return -1;
       }
       return ret;
   }  
  
  public static Font fontFromString(String s, int p , float fl){
    for(int i = 0; i < sharkStartFrame.importedfonts.length; i++){
      Font f = sharkStartFrame.importedfonts[i];
      if(s.equalsIgnoreCase(f.getName()))
        return f.deriveFont(p, fl);
    }
    return new Font(s, p, (int)fl);
  }
 public static Font[] addFont(Font fa[], Font f) {
    short len = (short)fa.length;
    Font newf[] = new Font[len+1];
    System.arraycopy(fa,0,newf,0,len);
    newf[len] = f;
    return newf;
}
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2007-11-19^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  // changes the string into html that sets the font to either a smaller or a bigger size
  // than the normal font.
  public static String setHtmlFontSize(String s, Font f, int pts, int pts2) {
    String htmlBeginBig = "<font face='"+String.valueOf(f.getName())+"' style='font-size:"+String.valueOf(pts2)+"px'>";
    String htmlBeginSmall = "<font face='"+String.valueOf(f.getName())+"' style='font-size:"+String.valueOf(pts)+"px'>";
    String htmlEnd = "</font>";
    while(s.indexOf(":s:")>=0){
      s = s.replaceFirst(":s:", htmlBeginSmall);
      s = s.replaceFirst(":s:", htmlEnd);
    }
    while(s.indexOf(":b:")>=0){
      s = s.replaceFirst(":b:", htmlBeginBig);
      s = s.replaceFirst(":b:", htmlEnd);
    }
    return s;
 }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2008-08-14^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  public static String convertForXML(String s) {
    String res = "";
    for(int i = 0; i < s.length(); i++){
      if(s.charAt(i)=='&')res=res.concat("&amp;");
      else res=res.concat(String.valueOf(s.charAt(i)));
    }
    return res.trim();
 }

  public static String convertToCR(String s) {
    String res = "";
    for(int i = 0; i < s.length(); i++){
      if(s.charAt(i)=='|')res=res.concat("\n");
      else res=res.concat(String.valueOf(s.charAt(i)));
    }
    return res;
 }

  public static String convertToDoubleCR(String s) {
    String res = "";
    for(int i = 0; i < s.length(); i++){
      if(s.charAt(i)=='|')res=res.concat("\n\n");
      else res=res.concat(String.valueOf(s.charAt(i)));
    }
    return res;
 }

  public static String convertToHtml(String s) {
    String res = "";
    for(int i = 0; i < s.length(); i++){
      if(s.charAt(i)=='|')res=res.concat("<br>");
      else res=res.concat(String.valueOf(s.charAt(i)));
    }
    res="<html>"+res;
    res=res+"</html>";
    return res;
 }

  public static String convertToHtml(String s, boolean center) {
    String res = "";
    for(int i = 0; i < s.length(); i++){
      if(s.charAt(i)=='|')res=res.concat("<br>");
      else res=res.concat(String.valueOf(s.charAt(i)));
    }
    res="<html><center>"+res;
    res=res+"</center></html>";
    return res;
 }
  
   public static String convertToHtml(String s, boolean center, Font f) {
    String res = "";
    for(int i = 0; i < s.length(); i++){
      if(s.charAt(i)=='|')res=res.concat("<br>");
      else res=res.concat(String.valueOf(s.charAt(i)));
    }
    
    
//    String ft = (f==null?"":"<font face=\""+f.getFontName()+"\" size=\""+f.getSize()+"\">");
    String ft = (f==null?"":"<p style=\"font: "+f.getSize()+"pt "+f.getFontName()+"\">");   
    
    res="<html><center>" +ft+res;
    ft = (f==null?"":"</p>");
    res=res+ft+"</center></html>";
    return res;
 }
   
  public static String convertToInnerHtml(String s) {
    String res = "";
    for(int i = 0; i < s.length(); i++){
      if(s.charAt(i)=='|')res=res.concat("<br>");
      else res=res.concat(String.valueOf(s.charAt(i)));
    }
    return res;
 }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2004-09-15^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  /**
   * Returns a boolean array which corresponds to the characters of a String. Each
   * index is <code>true</code> if the corresponding letter is part of special
   * vowel combinations - i.e. in some languages a particular combination of characters
   * are considered a vowel. Any '/' characters breaking up the letter combinations
   * are disregarded.
   *
   * @param word  the String in which the special vowel combinations are searched for
   * @return  a <code>boolean array</code> in which each index is <code>true</code>
   * if it corresponds to a character which is part of a special vowel combination
   */
  public static boolean[] findSpecialVowels(String word){
    boolean withSlash[] = new boolean[word.length()];
    /*remove the '/' characters so that the special vowel combinations can be searched
      for*/
    String deslashed = word.replaceAll("/", "");
    boolean withoutSlash[] = new boolean[deslashed.length()];
    // find the special vowel combinations
    for(int i=0;i<u.specialVowels.length;++i) {
      int j = 0;
      while ((j = deslashed.indexOf(specialVowels[i], j)) >= 0) {
        for (int k = 0; k < u.specialVowels[i].length(); ++k)
          withoutSlash[j + k] = true;
        ++j;
      }
    }
    int counter = 0;
    /*replace the '/' characters with corresponding <code>false</code> items in
     the returned <code>boolean array</code>*/
    for(int i = 0; i<word.length(); i++){
      if(word.charAt(i) == '/'){
        counter++;
        withSlash[i] = false;
      }
      else{
        withSlash[i] = withoutSlash[i - counter];
      }
    }
    return withSlash;
  }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
 public static JTextField getjtextfieldcomp(Component comp) {
      JTextField c = null;
      Component[] children = ((Container) comp).getComponents();
      for (int i = 0; i < children.length; ++i){
          if(children[i] instanceof JTextField)
              return (JTextField)children[i];
          c = getjtextfieldcomp(children[i]);
          if(c !=null)return c;
      }
      return c;
  }

 public static String halveText(String s, int minsplit){
     int k;
     if((k=s.indexOf('('))>=0){
         return s.substring(0, k).trim()+"|"+s.substring(k).trim();
     }
     if(s.length() < minsplit)return s;
     String ret;
          int len = s.length();
          int orilen = len;
          int half = len / 2;
           int plusgap = 0;
           int minusgap = 0;
           len = half;
           boolean spacefound = false;
           if(s.charAt(len)==' '){
                   spacefound= true;
                   ret = s.substring(0, len).trim()+"|"+s.substring(len).trim();
           }
           else{
               while(len< orilen){
                   if(s.charAt(len)==' '){
                           spacefound= true;
                           break;
                    }
                    len++;
                    plusgap++;
               }
               len = half;
               while(len>=0){
                   if(s.charAt(len)==' '){
                    spacefound= true;
                    break;
                   }
                   len--;
                   minusgap++;
               }
               if(!spacefound)ret = s.trim();
                  else {
                       if(plusgap <= minusgap)
                      {
                           k = half+plusgap;
                           ret = s.substring(0, k).trim()+"|"+s.substring(k).trim();
                      }
                      else{
                           k = half-minusgap;
                           ret = s.substring(0, k).trim()+"|"+s.substring(k).trim();
                      }
              }
          }
           return ret;
 }

 public static String halveText(String s){
    return halveText(s, -1);
 }


 public static String[] halveText(String s[]){
      String ret[] = new String[s.length];
      for(int i = 0; i < s.length; i++){
        ret[i] = halveText(s[i]);
      }
      return ret;
 }
//startPR2004-09-15^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  /**
   * Returns a boolean array which corresponds to the characters of a String. Each
   * index is <code>true</code> if the corresponding letter is part of special
   * vowel combinations - i.e. in some languages a particular combination of characters
   * are considered a vowel. Any '/' characters breaking up the letter combinations
   * are disregarded.
   *
   * @param word  the String in which the special vowel combinations are searched for
   * @return  a <code>boolean array</code> in which each index is <code>true</code>
   * if it corresponds to a character which is part of a special vowel combination
   */
  public static boolean[] findSVPH(String word){
//    boolean withSlash[] = new boolean[word.length()];
    /*remove the '/' characters so that the special vowel combinations can be searched
      for*/
    String deslashed = word.replaceAll("/", "");
    boolean withoutSlash[] = new boolean[deslashed.length()];
    // find the special vowel combinations
    for(int i=0;i<u.specialVowels.length;++i) {
      int j = 0;
      while ((j = deslashed.indexOf(specialVowels[i], j)) >= 0) {
        for (int k = 0; k < u.specialVowels[i].length(); ++k)
          withoutSlash[j + k] = true;
        ++j;
      }
    }
    return withoutSlash;
  }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    static class textpane extends JTextPane {
        public void paint(Graphics g) {
            ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            super.paint(g);
        }
    }
//startPR2008-11-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  public static String toHtml(String s) {
    String ret = "";
    int k = s.length();
    char c;
    for(int i = 0; i < k; i++){
      if((c=s.charAt(i))=='|')
        ret+="<br>";
      else
        ret+=String.valueOf(c);
    }
    ret = "<html>"+ret+"</html>";
    return ret;
 }

public static String generateRandom(String key, int no){
  String s = "";
  int len = key.length();
  for (int k = 0; k < no; k++)
  {
//    int r = rr.nextInt();
//    int i = r % key.length();
//    i = Math.max(i, i*-1);
//    s = s + key.substring(i, i+1);

    int r = rr.nextInt();
    int i;
    s = s + key.substring((i=Math.max(r, r * -1) % len), i+1);
  }
  return s;
}
public static String[] splitString(String s,String splitat) {  // split string
   if(s==null) return new String[0];
   int slen = splitat.length();
   short i=0, j=0,tot=0;
   String out[];
   while((j = (short)s.substring(i).indexOf(splitat)) >= 0)
                     {++tot; i=(short)(i+j+slen);}
   out = new String[tot+1];
   i = tot = 0;
   while((j = (short)s.substring(i).indexOf(splitat)) >= 0) {
      out[tot++] = s.substring(i,i+j);
      i=(short)(i+j+slen);
   }
   out[tot] = s.substring(i);
   return(out);
}
//endPRauth^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  public static int select(Object[] in, Object[] out, int want) {
     int sel[] = new int[want];
     short i,j;


     if(want > in.length) want = in.length;
     outer: for(i = 0; i < want; ++i) {
        sel[i] = Math.abs(rr.nextInt()) % in.length;
        for(j = 0; j < i; ++j) {
           if (sel[i] == sel[j]) {--i; continue outer;}
        }
        out[sel[i]] = in[i];
     }
     return want;
  }
  //----------------random number to given max
  public static short rand(short max) {
     return (short) Math.abs(rr.nextInt() % max);
  }
  //----------------random number to given max
  public static int rand(int max) {
     return (int) Math.abs(rr.nextInt() % max);
  }
  //----------------random number to given max
  public static double rand(double max) {
     return rr.nextDouble() * max;
  }
  //----------------random number to given max
  public static float randangle() {
     return (float)(rr.nextFloat() * 2 * Math.PI);
  }
  //----------------random numbers to given max in order
  public static short[] select(short tot, short want) {
     short ret[];
     short i,j,k,sel;

     if(want >= tot) {
        ret = new short[tot];
        for(i=0;i<tot;++i)
          ret[i] = i;
     }
     else {
        ret = new short[want];
        i=0;
        outer:while(i < want) {
           sel = (short) (Math.abs(rr.nextInt()) % tot);
           for(j = 0; j < i; ++j) {
              if(ret[j] == sel) continue outer;
              else if(ret[j] > sel) {
                 for(k=i;k>j;--k) ret[k] = ret[k-1];
                 break;
              }
            }
            ret[j] = sel;
            ++i;
        }
     }
     return ret;
  }
  //----------------random numbers to given max in order
  public static int[] select(int tot, int want) {
     int ret[];
     int i,j,k,sel;

     if(want >= tot) {
        ret = new int[tot];
        for(i=0;i<tot;++i)  ret[i]=i;
     }
     else {
        ret = new int[want];
        i=0;
        outer:while(i < want) {
           sel = (int) (Math.abs(rr.nextInt()) % tot);
           for(j = 0; j < i; ++j) {
              if(ret[j] == sel) continue outer;
              else if(ret[j] > sel) {
                 for(k=i;k>j;--k) ret[k] = ret[k-1];
                 break;
              }
            }
            ret[j] = sel;
            ++i;
        }
     }
     return ret;
  }
  //----------------random numbers to given max in order with min  dist
  public static int[] select(int tot, int min, short want) {
     int ret[];
     short i,j,k;
     int sel;

        ret = new int[want];
        i=0;
        outer:while(i < want) {
           sel = (int) (Math.abs(rr.nextInt()) % tot);
           for(j = 0; j < i; ++j) {
              if(Math.abs(ret[j]- sel) < min) continue outer;
              else if(ret[j] > sel) {
                 for(k=i;k>j;--k) ret[k] = ret[k-1];
                 break;
              }
            }
            ret[j] = sel;
            ++i;
        }
        return ret;
  }
  //----------------random numbers in range in order
  public static short[] select(short min, short max, short want) {
     short ret[];
     short i,j,k,sel;
     short tot = (short)(max-min);

     if(want >= tot) {
        ret = new short[tot];
        for(i=0;i<tot;++i)  ret[i]=(short)(min+i);
     }
     else {
        ret = new short[want];
        i=0;
        outer:while(i < want) {
           sel = (short) (min + (Math.abs(rr.nextInt()) % tot));
           for(j = 0; j < i; ++j) {
              if(ret[j] == sel) continue outer;
              else if(ret[j] > sel) {
                 for(k=i;k>j;--k) ret[k] = ret[k-1];
                 break;
              }
            }
            ret[j] = sel;
            ++i;
        }
     }
     return ret;
  }
  //------------------------------------------------------------
  public static short[] shuffle(short[] list) {
     short num = (short) list.length;
     short i,j,temp;
     for(i=0;i<num;++i) {
        j = (short) (Math.abs(rr.nextInt()) % num);
        temp = list[j];
        list[j] = list[i];
        list[i] = temp;
     }
     return list;
  }
  //------------------------------------------------------------
  public static String[] shuffle(String[] list) {
     short num = (short) list.length;
     short i,j;
     String temp;
     for(i=0;i<num;++i) {
        j = (short) (Math.abs(rr.nextInt()) % num);
        temp = list[j];
        list[j] = list[i];
        list[i] = temp;
     }
     return list;
  }
  //------------------------------------------------------------
  public static word[] shuffle(word[] list) {
     short num = (short) list.length;
     short i,j;
     word temp;
     for(i=0;i<num;++i) {
        j = (short) (Math.abs(rr.nextInt()) % num);
        temp = list[j];
        list[j] = list[i];
        list[i] = temp;
     }
     return list;
  }
  //------------------------------------------------------------
  public static boolean inorder(short[] list) {
     short num = (short) list.length;
     short i;
     for(i=0;i<num-1;++i) {
        if(list[i] > list[i+1]) return false;
     }
     return true;
  }
  //------------------------------------------------------------
  public static int[] shuffle(int[] list) {
     short num = (short) list.length;
     short i,j;
     int temp;
     for(i=0;i<num;++i) {
        j = (short) (Math.abs(rr.nextInt()) % num);
        temp = list[j];
        list[j] = list[i];
        list[i] = temp;
     }
     return list;
  }
  //------------------------------------------------------------
  public static short[] shuffle(short[] list,short start, short end) {
     short num = (short) (end-start);
     short i,j,temp;
     for(i=start;i<end;++i) {
        j = (short) (Math.abs(rr.nextInt()) % num + start);
        temp = list[j];
        list[j] = list[i];
        list[i] = temp;
     }
     return list;
  }
  //------------------------------------------------------------
  public static short[] getorder(int[] list) {
     short num = (short) list.length;
     short o[] = select(num,num);
     short i,j;
     short temp;
     for(i=0;i<num;++i) {
        for(j=(short)(i+1);j<num;++j) {
           if(list[o[j]] < list[o[i]]) {
              temp = o[j];
              o[j] = o[i];
              o[i] = temp;
           }
        }
     }
     return o;
  }
   
   public static String stripTxtTrailingTabs(String str){
       while(str.endsWith("\t")){
           str = str.substring(0, str.length()-1);
       }
       return str;
   } 
  
   
   public static String sentenceConvertBreaks(String str){
       String ss[] = u.splitString(str);
       for(int i = 0; i < ss.length; i++){
           ss[i] = ss[i].trim();
       }
       return u.combineString(ss, " ");
   } 
   
    public static String sentenceAddBreaks(String str, int maxChars){
        while(str.endsWith("\t")){
           str = str.substring(0, str.length()-1);
        }        
        sentence sent = (new sentence(str,null));
        String wildcardSent = sent.stripclozereplacewildcard();
        String sentBrokenUp[] = u.splitString(wildcardSent, "*");        
        int i = 0;
        String ret = "";
        String answers[] = new String[0];
        String answersStars[] = new String[0];
        for(i = 0; i < sentBrokenUp.length; i++){
            int maxAnsChars = -1;
            boolean last = i == sentBrokenUp.length-1;
            if(!last){
                String answerList[] = sent.getAnswerList(i);
                for(int j = 0; j < answerList.length; j++){
                   maxAnsChars = Math.max(maxAnsChars, answerList[j].length());
                }
                String stars = getCharsOfLength('*', maxAnsChars);
                ret += sentBrokenUp[i] + stars;   
                answers = u.addString(answers, answerList.length==1?answerList[0]:u.combineString(answerList, "/"));
                answersStars = u.addString(answersStars, stars);
            }
            else{
                ret += sentBrokenUp[i];
            }
        }     
        int currIndex = 0;
        int currPos = 0;
        String finalStr = "";
        while(currIndex < ret.length()){
            try{
                String t = ret.substring(currIndex, Math.min(currIndex + maxChars,ret.length()));
                if(ret.length() > currIndex + maxChars){
                    if(ret.charAt(currIndex + maxChars) != ' '){
                        String ss[] = u.splitString(t, " ");
                        int endChunkLen = ss[ss.length-1].length();
                        try{
                            t = t.substring(0, t.length()-endChunkLen);
                        }
                        catch(Exception e1){}                 
                    }
                }
                if(answersStars.length >  currPos && t.indexOf(answersStars[currPos]) >= 0){
                    String orit = t;
                    String tempT = t;
                    while(answersStars.length > currPos && tempT.indexOf(answersStars[currPos])>=0){
                       int p = t.indexOf(answersStars[currPos]);
                       t = t.substring(0, p) + answers[currPos] + t.substring(p+answersStars[currPos].length());
                       tempT = t;
                       currPos++;
                    }
                    finalStr += t.trim()+"|";
                    currIndex += orit.length();
                }    
                else{
                    finalStr += t.trim()+"|";
                    currIndex += t.length();
                }
            }
            catch(Exception e){
                finalStr = str;
            }       
        }
        finalStr = finalStr.trim();
        if(finalStr.endsWith("|"))
            finalStr = finalStr.substring(0, finalStr.length()-1);
        return finalStr;
   }   
   
 public static String getCharsOfLength(char c, int len){
    String ret = "";
    for(int i = 0; i < len; i++){
        ret+=String.valueOf(c);
    }
    return ret;
 }    
    
    
 public static int getPhotographIndex(String name){
         int h;
        char snotallowed[] = new char[]{'<','>',':','\"','\\', '/', '|', '?', '*'};
        for(int ic = 0; ic < snotallowed.length; ic++){
            if(name.indexOf(snotallowed[ic])>=0)
                name = name.replace(String.valueOf(snotallowed[ic]), "["+String.valueOf((int)snotallowed[ic])+"]");
        }
          if ((h=u.findString(sharkStartFrame.publicPhotoNames, name))>=0){
              return h;
          }
          return -1;
 }  
 
  public static String getPhotoNameInWordsharkTestCourse(String imageText){
         /*
        horrid@@dream - want to return the whole text
        ceiling@@  - want to return the whole text
        wishing@@wish - if a photo exists called wishing@@wish return 'wishing@@wish'
            if not return 'wish'
        */
                boolean hasPhoto = u.getPhotographIndex(imageText)>=0;
                int t;
                if(!hasPhoto && (t=imageText.indexOf("@@"))>=0){
                    if(t<imageText.length()-"@@".length()){
                        return  imageText.substring(t+"@@".length());
                    }
                }
                return imageText;
 }
  
  //------------------------------------------------------------
  public static int[] getorder(String[] list) {
     int num =  list.length;
     int o[] = select(num,num);
     int i,j;
     int temp;
     for(i=0;i<num;++i) {
        for(j=(short)(i+1);j<num;++j) {
           if(list[o[j]].compareTo(list[o[i]]) < 0) {
              temp = o[j];
              o[j] = o[i];
              o[i] = temp;
           }
        }
     }
     return o;
  }
  //---------------------------------------------------------------
  static Comparator comparator1 = new Comparator() {
       public int compare(Object o1, Object o2)  {
         return ((String)o1).toLowerCase().compareTo(((String)o2).toLowerCase());
       }
   };
  static Comparator comparator1date = new Comparator() {
       public int compare(Object o1, Object o2)  {
         return (exdate((String)o1).compareTo(exdate((String)o2)));
       }
   };
   static String exdate(String s) {
    int i,j;
      if(s.indexOf('/') < 0 || s.indexOf(':') < 0) return s;
      String day = "",year="",month="",hour ="",mins="";
      for(i = (j = s.indexOf('/'))-1;i>=0 && s.charAt(i)>='0' && s.charAt(i)<='9';--i)
          day =  s.substring(i,i+1)+day;
      if(day.length()<2) day= "0"+day;
      for(i = (j = s.indexOf('/',j+1))-1;i>=0 && s.charAt(i)>='0' && s.charAt(i)<='9';--i)
          month =  s.substring(i,i+1)+month;
      if(month.length()<2) month= "0"+month;
      for(i = j+1; i<s.length() && s.charAt(i)>='0' && s.charAt(i)<='9';++i)
          year =  year + s.substring(i,i+1);
      for(i = (j = s.indexOf(':'))-1;i>=0 && s.charAt(i)>='0' && s.charAt(i)<='9';--i)
          hour =  s.substring(i,i+1)+hour;
      if(hour.length()<2) hour = "0"+hour;
      for(i = j+1; i<s.length() && s.charAt(i)>='0' && s.charAt(i)<='9';++i)
          mins =  mins + s.substring(i,i+1);
      if(mins.length()<2) mins = "0"+mins;
      return year+month+day+hour+mins+s;
   }
   static Comparator comparator2 =   new Comparator() {
       public int compare(Object o1, Object o2)  {
           return ((String)o1).toLowerCase().compareTo((String)o2);
       }
   };
   static Comparator comparatorCaps =   new Comparator() {
       public int compare(Object o1, Object o2)  {
           int ret = ((String)o1).toLowerCase().compareTo(((String)o2).toLowerCase());
           if(ret==0) return ((String)o1).compareTo((String)o2);
           return ret;
       }
   };
   //-------------------------------------------------------------------
  public static void sort(String s[]) {
     Arrays.sort(s,comparator1);
  }
   //-------------------------------------------------------------------
  public static void sortdate(String s[]) {
     Arrays.sort(s,comparator1date);
  }
  //-------------------------------------------------------------------
  public static void sort(String s[],int tot) {
     Arrays.sort(s,0,tot,comparator1);
  }
  //------------------------------------------------------------
  public int[] sortorder(String list[]) {
     int num = list.length,out[] = u.select(num,num);
     int i,j;
     int temp;
     for(i=0;i<num;++i) {
        for(j=i+1;j<num;++j) {
           if(list[out[j]].toLowerCase().compareTo(list[out[i]].toLowerCase()) < 0) {
              temp = out[j];
              out[j] = out[i];
              out[i] = temp;
           }
        }
     }
     return out;
  }
  //-----------------------------------------------------------
//  public static short[] sorted(short tot,BinaryPredicate bp) {
//     if(tot==0) return new short[0];
//     short ret[] = new short[tot], w[] = select(tot,tot);
//     Range r = Sorting.iterSort(new ShortIterator(w,0), new ShortIterator(w,tot),bp);
 //    short i=0;
//     ForwardIterator it = r.begin;
//     for(i=0;i<tot;++i) {
//        ret[i] = ((Short)it.get()).shortValue();
//        it.advance();
//     }
//     return ret;
//  }
  //------------------------------------------------------------
  public static Object[] combine(Object o[][], Object out[]) {
     short i,otot=0,cum=0;
     for(i=0;i<o.length;++i) otot += o[i].length;
     out = (Object[])java.lang.reflect.Array.newInstance(out.getClass(),otot);
     if(otot>0) for(i=0;i<o.length;++i) {
        System.arraycopy(o[i],0,out,cum,o[i].length);
        cum += o[i].length;
     }
     return out;
  }
  public static Object[] combine(Object o1[],Object o2[]) {
     short i,otot=0,cum=0;
     Object out[] = new Object[o1.length + o2.length];
     System.arraycopy(o1,0,out,0,o1.length);
     System.arraycopy(o2,0,out,o1.length,o2.length);
     return out;
  }
  
  public static spokenWord[] addSpokenWord(spokenWord[] sa, spokenWord s) {
     if(sa == null) return new spokenWord[]{s};
     short len = (short)sa.length;
     spokenWord news[] = new spokenWord[len+1];
     System.arraycopy(sa,0,news,0,len);
     news[len] = s;
     return news;
 }  
  
  
  public static saveTree1.saveTree2[] addsaveTree2(saveTree1.saveTree2[] sa, saveTree1.saveTree2 s) {
     if(sa == null) return new saveTree1.saveTree2[]{s};
     short len = (short)sa.length;
     saveTree1.saveTree2 news[] = new saveTree1.saveTree2[len+1];
     System.arraycopy(sa,0,news,0,len);
     news[len] = s;
     return news;
 }  
  
  public static String replaceEvery(String s, String match, String replacement) {
    if(s==null)return s;
    int k;
    while((k=s.indexOf(match))>=0){
        s = s.substring(0, k)+replacement+s.substring(k+match.length());
    }
    return s;
  }    
  
  public static String[] addString(String[] sa, String s) {
     return u2_base.addString(sa, s);
 }
  public static String[] addString(String[] sa, String s,int pos) {
     if(sa == null) return new String[]{s};
     short len = (short)sa.length;
     String news[] = new String[len+1];
     System.arraycopy(sa,0,news,0,pos);
     if(pos<len) System.arraycopy(sa,pos,news,pos+1,len-pos);
     news[pos] = s;
     return news;
 }
  
  public static int getAddStringSortIndex(String[] sa, String s) {
     int i;
     if(sa == null) return -1;
     short len = (short)sa.length;
     i = Arrays.binarySearch(sa,s,comparator1);
     if(i>=0) return -1;
     i = -i-1;
     return i;
 }   
  
  public static String[] addStringSort(String[] sa, String s) {
     int i;
     if(sa == null) return new String[]{s};
     short len = (short)sa.length;
     i = Arrays.binarySearch(sa,s,comparator1);
     if(i>=0) return sa;
     i = -i-1;
     String news[] = new String[len+1];
     System.arraycopy(sa,0,news,0,i);
     news[i] = s;
     if(i<len) System.arraycopy(sa,i,news,i+1,len-i);
     return news;
 }
 public static String[] addStringSortCaps(String[] sa, String s) {
    int i;
    if(sa == null) return new String[]{s};
    short len = (short)sa.length;
    i = Arrays.binarySearch(sa,s,comparatorCaps);
    if(i>=0)   return sa;
    i = -i-1;
    String news[] = new String[len+1];
    System.arraycopy(sa,0,news,0,i);
    news[i] = s;
    if(i<len) System.arraycopy(sa,i,news,i+1,len-i);
    return news;
}
 
  public static student[] addStudent(student[] sa, student s) {
     if(sa == null) return new student[]{s};
     short len = (short)sa.length;
     student news[] = new student[len+1];
     System.arraycopy(sa,0,news,0,len);
     news[len] = s;
     return news;
 } 
 
public static String[] removeString(String[] sa, String s) { // assumes sa in order
   int i;
   if(sa == null) return null;
   i = Arrays.binarySearch(sa,s,comparator1);
   if(i<0) return sa;
   short len = (short)sa.length;
   String news[] = new String[len-1];
   System.arraycopy(sa,0,news,0,i);
   if(i<len-1) System.arraycopy(sa,i+1,news,i,len-i-1);
   return news;
}
        // out of order string
 public static String[] removeString2(String[] sa, String s) {   // sa can be out of order
    if(sa == null) return null;
    int i = findString(sa,s);
    if(i>=0) return removeString(sa,i);
    else return sa;
}
 public static word[] notlen1(word[] sa) {
    int i;
    if(sa == null) return null;
    short len = (short)sa.length;
    for(i=0;i<len;++i) {
       if(sa[i].v().length() < 2)  {
         sa = removeword(sa,i);
         --len;
         --i;
       }
    }
    return sa;
 }
//startPR2008-06-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
 public static File[] removeFile(File[] fa, int i) {
    if(fa == null) return null;
    short len = (short)fa.length;
    if(len<=i) return fa;
    File news[] = new File[len-1];
    System.arraycopy(fa,0,news,0,i);
    if(i<len-1) System.arraycopy(fa,i+1,news,i,len-i-1);
    return news;
}
//endPRimagesandvids^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
 
  public static jnode[] removeNode(jnode[] n, int i) {
    if(n == null) return null;
    short len = (short)n.length;
    if(len<=i) return n;
    jnode news[] = new jnode[len-1];
    System.arraycopy(n,0,news,0,i);
    if(i<len-1) System.arraycopy(n,i+1,news,i,len-i-1);
    return news;
}
 
   public static TreePath[] removeTreePaths(TreePath[] ta, int i) {
    if(ta == null) return null;
    short len = (short)ta.length;
    if(len<=i) return ta;
    TreePath news[] = new TreePath[len-1];
    System.arraycopy(ta,0,news,0,i);
    if(i<len-1) System.arraycopy(ta,i+1,news,i,len-i-1);
    return news;
 }  
 
   public static topicPlayed[] removeTopicPlayed(topicPlayed[] ta, int i) {
    if(ta == null) return null;
    short len = (short)ta.length;
    if(len<=i) return ta;
    topicPlayed news[] = new topicPlayed[len-1];
    System.arraycopy(ta,0,news,0,i);
    if(i<len-1) System.arraycopy(ta,i+1,news,i,len-i-1);
    return news;
 }     
   
  public static String[] removeString(String[] sa, int i) {
     return u2_base.removeString(sa, i);
 }
 public static int[] removeint(int[] sa, int i) {
    if(sa == null) return null;
    short len = (short)sa.length;
    if(len<=i) return sa;
    int news[] = new int[len-1];
    System.arraycopy(sa,0,news,0,i);
    if(i<len-1) System.arraycopy(sa,i+1,news,i,len-i-1);
    return news;
}
  public static byte[] removeByte(byte[] sa, int i) {
     if(sa == null) return null;
     short len = (short)sa.length;
     if(len<=i) return sa;
     byte news[] = new byte[len-1];
     System.arraycopy(sa,0,news,0,i);
     if(i<len-1) System.arraycopy(sa,i+1,news,i,len-i-1);
     return news;
 }
  public static char[] removeChar(char[] sa, int i) {
     if(sa == null) return null;
     short len = (short)sa.length;
     if(len<=i) return sa;
     char news[] = new char[len-1];
     System.arraycopy(sa,0,news,0,i);
     if(i<len-1) System.arraycopy(sa,i+1,news,i,len-i-1);
     return news;
 }
  public static word[] removeword(word[] sa, int i) {
     if(sa == null) return null;
     short len = (short)sa.length;
     if(len<=i) return sa;
     word news[] = new word[len-1];
     System.arraycopy(sa,0,news,0,i);
     if(i<len-1) System.arraycopy(sa,i+1,news,i,len-i-1);
     return news;
 }
  
 public static short[] findAllStrings(String[] sa, String s) {
     short i;
     short ii[] = new short[]{};
     if(sa == null) return null;
     short len = (short)sa.length;
     for(i=0;i<len;++i) {
        if(sa[i].equalsIgnoreCase(s))  ii = addshort(ii, i);
     }
     return ii.length==0?null:ii;
 }   
  
 /**
  * Finds the position of string s in array sa[] ignoring whether characters are upper
  * or lower case.
  * @param sa
  * @param s
  * @return If sa is empty then -1 is returned otherwise a short is returned
  * indicating the position of string s in the array sa.
  */
 public static short findString(String[] sa, String s) {
     return u2_base.findString(sa, s);
 }
//startPR2007-03-05^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
 static JTree jt2;
 public static TreePath[] sortPathSelections(JTree jt, TreePath[] tp) {
//startPR2009-08-20^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   if(tp==null||tp.length==0)return null;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   jt2 = jt;
   Arrays.sort(tp, new Comparator() {
     public int compare(Object o1, Object o2) {
       int jn = jt2.getRowForPath( (TreePath) o1);
       int vjn = jt2.getRowForPath( (TreePath) o2);
       if (jn < vjn)  return -1;
       else   if (jn == vjn)   return 0;
       else   return 1;
     }
   });
   return tp;
 }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
 public static boolean equalStrings(String sa, String s) {
   return sa==null && s == null
       || sa != null && s != null && sa.equalsIgnoreCase(s);
 }
  public static boolean equalStrings(String sa[], String s[]) {
     short i;
     if(sa == null || s ==null) return false;
     short len = (short)sa.length;
     if(s.length  != len) return false;
     for(i=0;i<len;++i) {
        if(!sa[i].equalsIgnoreCase(s[i]))  return false;
     }
     return true;
 }
  public static boolean equalStringsExact(String sa[], String s[]) {
     short i;
     if(sa == null || s ==null) return false;
     short len = (short)sa.length;
     if(s.length  != len) return false;
     for(i=0;i<len;++i) {
        if(!sa[i].equals(s[i]))  return false;
     }
     return true;
 }
  public static String[] copyStrings(String s[]) {
     int len = s.length;
     String ss[] = new String[len];
     for(int i=0;i<len;++i) {
        ss[i] = new String(s[i]);
     }
     return ss;
 }
  public static boolean startswith(String s1, String s2) {
     short i;
     int len1 = s1.length();
     int len2 = s2.length();
     return (len2 <= len1
             && s1.substring(0,len2).equalsIgnoreCase(s2));
  }
  /**
   * Takes two strings and, as long as they are not empty, returns a single string
   * containing the contents of both.
   * @param sa
   * @param s
   * @return
   */
  public static String[] addString(String sa[], String s[]) {
     if(sa == null) return s;
     if(s==null) return sa;
     short len = (short)sa.length;
     short len2 = (short)s.length;
     String news[] = new String[len+len2];
     System.arraycopy(sa,0,news,0,len);
     System.arraycopy(s,0,news,len,len2);
     return news;
 }
  
  
  
  
  public static saveTree1.saveTree2 combineSaveTree2(saveTree1.saveTree2 sa, saveTree1.saveTree2 s) {
     if(sa == null || s==null) return sa;
     if(!sa.names[0].equals(s.names[0])) return sa;
     if(sa.names.length != s.levels.length) return sa;
     for(int i = 1; i < s.names.length; i++){
         sa.names = u.addString(sa.names, s.names[i]);
         sa.levels = u.addByte(sa.levels, s.levels[i]);
     }
     return sa;
 }    
 /**
  * String is returned with each element of the array passed combined and delimeted by an'|'
  * @param s
  * @return
  */
 public static String combineString(String s[]) {
    return u2_base.combineString(s);
 }
 public static String combineString(String s[],String ch) {
    return u2_base.combineString(s, ch);
 }
 public static String stringreplace(String s, char old, char newc) {
   int i;
   while ((i=s.indexOf(old) )>= 0)
     s = s.substring(0,i) + newc + s.substring(i+1);
   return s;
 }
 /**
  * Strips duplicates from the string passed
  * @param s
  * @return
  */
 public static String[] stripdup(String s[]) {
    if(s==null) return new String[0];
    int i,j,len = s.length;
    for(i=0;i<len;++i) {
       for(j=0;j<i;++j) {
          if(s[i].equalsIgnoreCase(s[j])) {
             System.arraycopy(s,i+1,s,i,--len-i);
             --i;
             break;
          }
       }
    }
    if(len<s.length) {
       String ss[] = new String[len];
       System.arraycopy(s,0,ss,0,len);
       return ss;
    }
    return s;
 }
 public static Graphics[] addGraphics(Graphics[] sa, Graphics s) {
     if(sa == null) return new Graphics[]{s};
     short len = (short)sa.length;
     Graphics news[] = new Graphics[len+1];
     System.arraycopy(sa,0,news,0,len);
     news[len] = s;
     return news;
 }
  public static File[] addFile(File sa[], File s) {
     short len = (short)sa.length;
     File news[] = new File[len+1];
     System.arraycopy(sa,0,news,0,len);
     news[len] = s;
     return news;
 }
  public static word[] stripdups(word sa[]) {
     short i,j;
     short len = (short)sa.length;
     loop1:for(i = 0; i<len; ++i) {
        if(sa[i].companions > 0) {i += sa[i].companions; continue;}
        for(j = 0; j<i ; ++j) {
           if(sa[i].v().equals(sa[j].v())) {
             if(i<--len) System.arraycopy(sa,i+1,sa,i,len - i );
              else break loop1;
              j=-1;
           }
        }
     }
     if(len < sa.length) {
        word neww[] = new word[len];
        System.arraycopy(sa,0,neww,0,len);
        return neww;
     }
     return sa;
 }
                // turn off companions and strip dups
  public static word[] stripdups2(word sa[]) {
     short i,j;
     short len = (short)sa.length;
     loop1:for(i = 0; i<len; ++i) {
        sa[i].companions = 0;
        for(j = 0; j<i ; ++j) {
           if(sa[i].v().equals(sa[j].v())) {
              if(i<--len) System.arraycopy(sa,i+1,sa,i,len - i );
              else break loop1;
              j=-1;
           }
        }
     }
     if(len < sa.length) {
        word neww[] = new word[len];
        System.arraycopy(sa,0,neww,0,len);
        return neww;
     }
     return sa;
 }
  public static word[] addWords(word sa[], word s) {
     short len = (short)sa.length;
     word news[] = new word[len+1];
     System.arraycopy(sa,0,news,0,len);
     news[len] = s;
     return news;
 }
 public static word[] augmentlist(word w[],boolean spelling) {  // for length <= 4 only
   int want,i;
   if (spelling) want = w.length == 3 ? 6 :4;
   else want = w.length == 4 ? 8 :6;
   word neww[] = new word[want];
   for (i = 0; i < want; ++i)    neww[i] = new word(w[i % w.length]);
   return neww;
 }
 
   public static int inintlist(int sa[], int s) {
     for(int i=0;i<sa.length;++i) if(sa[i] == s) return i;
     return -1;
 }
   
  public static boolean inlist(int sa[], int s) {
     for(int i=0;i<sa.length;++i) if(sa[i] == s) return true;
     return false;
 }
  
  
  public static int findIndexOfIntInArray(int sa[], int s) {
    int index = -1;
    for (int i = 0; i < sa.length; i++) {
        if (sa[i] == s) {
            index = i;
            break;
        }
    }
    return index;
 }  


  public static Point[] addpoint(Point sa[], Point s) {
     if(sa == null) return new Point[]{s};
     short len = (short)sa.length;
     Point news[] = new Point[len+1];
     System.arraycopy(sa,0,news,0,len);
     news[len] = s;
     return news;
 }
  public static Color[] addcolor(Color sa[], Color s) {
     if(sa == null) return new Color[]{s};
     short len = (short)sa.length;
     Color news[] = new Color[len+1];
     System.arraycopy(sa,0,news,0,len);
     news[len] = s;
     return news;
 }
  
  public static float[] addfloat(float sa[], float s) {
     if(sa == null) return new float[]{s};
     short len = (short)sa.length;
     float news[] = new float[len+1];
     System.arraycopy(sa,0,news,0,len);
     news[len] = s;
     return news;
 }


  public static int[] addint(int sa[], int s) {
     if(sa == null) return new int[]{s};
     short len = (short)sa.length;
     int news[] = new int[len+1];
     System.arraycopy(sa,0,news,0,len);
     news[len] = s;
     return news;
 }
  
  public static boolean[] addboolean(boolean sa[], boolean s) {
     if(sa == null) return new boolean[]{s};
     short len = (short)sa.length;
     boolean news[] = new boolean[len+1];
     System.arraycopy(sa,0,news,0,len);
     news[len] = s;
     return news;
 }  

  public static int[] addint(int sa[], int s[]) {
     if(sa == null) return s;
     if(s==null) return sa;
     short len = (short)sa.length;
     short len2 = (short)s.length;
     int news[] = new int[len+len2];
     System.arraycopy(sa,0,news,0,len);
     System.arraycopy(s,0,news,len,len2);
     return news;
 }

 public static int[] addint(int[] sa, int s,int pos) {
    if(sa == null) return new int[]{s};
    short len = (short)sa.length;
    int news[] = new int[len+1];
    System.arraycopy(sa,0,news,0,pos);
    if(pos<len) System.arraycopy(sa,pos,news,pos+1,len-pos);
    news[pos] = s;
    return news;
}
 public static char[] addchar(char sa[], char s) {
    if(sa == null) return new char[]{s};
    short len = (short)sa.length;
    char news[] = new char[len+1];
    System.arraycopy(sa,0,news,0,len);
    news[len] = s;
    return news;
}
//startPR2008-08-05^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  public static char[] addchar(char[] sa, char s,int pos) {
     if(sa == null) return new char[]{s};
     short len = (short)sa.length;
     char news[] = new char[len+1];
     System.arraycopy(sa,0,news,0,pos);
     if(pos<len) System.arraycopy(sa,pos,news,pos+1,len-pos);
     news[pos] = s;
     return news;
   }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  public static xres[] addxres(xres sa[], xres s) {
     if(sa == null) return new xres[]{s};
     short len = (short)sa.length;
     xres news[] = new xres[len+1];
     System.arraycopy(sa,0,news,0,len);
     news[len] = s;
     return news;
 }

  public static jnode[] addnode(jnode sa[], jnode s) {
     if(sa == null) return new jnode[]{s};
     short len = (short)sa.length;
     jnode news[] = new jnode[len+1];
     System.arraycopy(sa,0,news,0,len);
     news[len] = s;
     return news;
 }
 public static jnode[] addnode(jnode sa[], jnode s[]) {
     short len = (short)sa.length;
     short len2 = (short)s.length;
     if(s==null || len2==0) return sa;
     if(sa==null || len==0) return s;
     jnode news[] = new jnode[len+len2];
     System.arraycopy(sa,0,news,0,len);
     System.arraycopy(s,0,news,len,len2);
     return news;
 }
  public static short[] addshort(short sa[], short s) {
     if(sa == null) return new short[]{s};
     short len = (short)sa.length;
     short news[] = new short[len+1];
     System.arraycopy(sa,0,news,0,len);
     news[len] = s;
     return news;
 }
 public static word[] addWords(word sa[], word s[]) {
     short len = (short)sa.length;
     short len2 = (short)s.length;
     word news[] = new word[len+len2];
     System.arraycopy(sa,0,news,0,len);
     System.arraycopy(s,0,news,len,len2);
     return news;
 }
//startPR2008-08-29^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
 public static mover[] addmover(mover[] sa, mover s) {
    short len = (short)sa.length;
    mover news[] = new mover[len+1];
    System.arraycopy(sa,0,news,0,len);
    news[len] = s;
    return news;
}
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2008-06-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
 public static sharkImage[] addsharkImage(sharkImage[] sa, sharkImage s) {
    short len = (short)sa.length;
    sharkImage news[] = new sharkImage[len+1];
    System.arraycopy(sa,0,news,0,len);
    news[len] = s;
    return news;
}
//endPRimagesandvids^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  public static topic[] addTopic(topic[] sa, topic s) {
     short len = (short)sa.length;
     topic news[] = new topic[len+1];
     System.arraycopy(sa,0,news,0,len);
     news[len] = s;
     return news;
 }
  public static topicPlayed[] addTopicPlayed(topicPlayed[] sa, topicPlayed s) {
     short len = (short)sa.length;
     topicPlayed news[] = new topicPlayed[len+1];
     System.arraycopy(sa,0,news,0,len);
     news[len] = s;
     return news;
 }  
//startPR2007-05-17^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  public static TreePath[] addTreePaths(TreePath[] sa, TreePath[] s) {
     if(sa == null) return s;
     short len = (short)sa.length;
     short len2 = (short)s.length;
     TreePath news[] = new TreePath[len+len2];
     System.arraycopy(sa,0,news,0,len);
     System.arraycopy(s,0,news,len,len2);
     return news;
 }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
 public static byte[] addByte(byte sa[], byte s) {
    if(sa == null) return new byte[]{s};
    short len = (short)sa.length;
    byte news[] = new byte[len+1];
    System.arraycopy(sa,0,news,0,len);
    news[len] = s;
    return news;
}  

 public static byte[] addByte(byte ba[], byte b, int pos) {
     if(ba == null) return new byte[]{b};
     short len = (short)ba.length;
     byte news[] = new byte[len+1];
     System.arraycopy(ba,0,news,0,pos);
     if(pos<len) System.arraycopy(ba,pos,news,pos+1,len-pos);
     news[pos] = b;
     return news;
}   
  
  public static Date[] addDate(Date[] sa, Date s) {
     short len = (short)sa.length;
     Date news[] = new Date[len+1];
     System.arraycopy(sa,0,news,0,len);
     news[len] = s;
     return news;
 }  
  public static Image[] addImage(Image[] sa, Image s) {
     short len = (short)sa.length;
     Image news[] = new Image[len+1];
     System.arraycopy(sa,0,news,0,len);
     news[len] = s;
     return news;
 }
  
  
  public static JCheckBox[] addJCheckBox(JCheckBox[] sa, JCheckBox s) {
     short len = (short)sa.length;
     JCheckBox news[] = new JCheckBox[len+1];
     System.arraycopy(sa,0,news,0,len);
     news[len] = s;
     return news;
 }  

  
 public static boolean copyfile(File from, File to) {
    FileInputStream in ;
    FileOutputStream out;
    byte[] b = new byte[100000];
    int len;
    try {
          in = new FileInputStream(from);
          out = new FileOutputStream(to);
//startPR2009-08-02^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          u2_base.setNewFilePermissions(to);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          while((len = in.read(b)) > 0) out.write(b,0,len);
          out.close(); in.close();
    }
    catch(IOException h) {
        return false;
    }
    return true;
  }
 public static void setNewFilePermissions(File database){
    u2_base.setNewFilePermissions(database);
 }
  /**
   * Pauses the thread for the specified period
   * @param t Time the thread is paused for.
   */
  public static void pause(int t) {
     if(t<=0) return;
     try { Thread.sleep(t); } catch (InterruptedException e) {}
  }
  public static void freeze(int t) {
     long ti = t + System.currentTimeMillis();
     while(System.currentTimeMillis() < ti);
  }
  /**
   * Draws a raised or sunken bevel border for a button.
   * @param g Graphics object to be used.
   * @param r Rectangle to be used.
   * @param c Colour to be used.
   * @param up True if button is to appear raised otherwise if button is to appear
   * sunken this is false.
   */
  public static void buttonBorder(Graphics g, Rectangle r, Color c, boolean up) {
     int w = Math.min(8,Math.max(1, r.width / 20));
     g.setColor(new Color(Math.min(255,c.getRed()+30), Math.min(255,c.getGreen()+30),
                          Math.min(255,c.getBlue()+30)));
     for(int i=0;i<w;++i) {
        g.draw3DRect(r.x+i, r.y+i, r.width-i*2-1, r.height-i*2-1, up);
     }
  }
    //------------------------------------------------------------
  public static Polygon drawcurve(Polygon po, int x1, int y1, double g1, int x2, int y2, double g2) {
    double dist = Math.sqrt((double)(x2-x1)*(x2-x1) + (y2-y1)*(y2-y1))/2;
     dist = Math.max(Math.abs(x2-x1),Math.abs(y2-y1));
     drawcurve(po,x1,y1,g1,x2,y2,g2,(int)Math.max(1,dist/2));
     return po;
  }
    //------------------------------------------------------------
  public static void drawcurve(Polygon po, int x1, int y1, double g1, int x2, int y2, double g2,int steps) {
     double dist = Math.sqrt((double)(x2-x1)*(x2-x1) + (y2-y1)*(y2-y1))/2;
     dist = Math.max(Math.abs(x2-x1),Math.abs(y2-y1));
//     int steps = (int)Math.max(1,dist/2);
     int i,x=x1,y=y1,xn,yn;
     double xinc1 = dist * Math.cos(g1)/steps;
     double yinc1 = dist * Math.sin(g1)/steps;
     double xinc2 = dist * Math.cos(g2)/steps;
     double yinc2 = dist * Math.sin(g2)/steps;
     double f1,f2,xn1,xn2,yn1,yn2;
     if(po.npoints==0 || po.xpoints[po.npoints-1] != x1
                         || po.ypoints[po.npoints-1] != y1)
                 po.addPoint(x1,y1);
     for(i = 1; i<steps;++i) {
        f1 = (double)(steps-i)/steps;
        f2 = (double)i/(steps);
        xn1 = x1 + xinc1*i;
        xn2 = x2 - xinc2*(steps-i);
        yn1 = y1 + yinc1*i;
        yn2 = y2 - yinc2*(steps-i);
        x = (int)(xn1*f1 + xn2*f2);
        y = (int)(yn1*f1 + yn2*f2);
        if(po.xpoints[po.npoints-1] != x
                         || po.ypoints[po.npoints-1] != y)
             po.addPoint(x,y);
     }
     if(po.xpoints[po.npoints-1] != x2
                         || po.ypoints[po.npoints-1] != y2)
           po.addPoint(x2,y2);
  }
  
   public static String duplicateNameCheck(String s2, String ss[]){
        String s = s2;
        int counter = 1;
        while (u.findString(ss, s)>=0){
            int k = isNameADuplicate(s);
            if(k>0){
                counter = k+1;
                s = s.substring(0, s.lastIndexOf('(')-1);
            }
            s = s +" ("+String.valueOf(counter)+")";
            counter++;
        }
        return s;
   }  
  
  public static double adjangle(double a) {
       while(a > Math.PI*2) a -= Math.PI*2;
       while(a < Math.PI*2) a += Math.PI*2;
       return a;
  }
  public static Color brightcolor() {
     final short tot = 510;
     short col[]= new short[3];
     col[0] = 255;
     col[1] = (short)u.rand(256);
     col[2] = (short)(tot-col[0] -col[1]);
     short o[] = u.shuffle(u.select((short)3,(short)3));
     return new Color(col[o[0]],col[o[1]],col[o[2]]);
  }
   //      return new Color(164+u.rand(92),164+u.rand(92),164+u.rand(92));
   public static Color verybrightcolor(Color avoid) {
      final short tot = 510;
      short col[]= new short[3];
      int c1 = avoid.getGreen();
      int c2 = avoid.getRed();
      int c3 = avoid.getBlue();
      while(true) {
         col[0] = 255;
         col[1] = (short)(128+u.rand(128));
         col[2] = 128;
         short o[] = u.shuffle(u.select((short)3,(short)3));
         if  (Math.abs(c1 - col[o[1]])
              + Math.abs(c2 - col[o[0]])
              + Math.abs(c3 - col[o[2]]) < 150) continue;


         return new Color(col[o[0]],col[o[1]],col[o[2]]);
      }
   }
   //      return new Color(164+u.rand(92),164+u.rand(92),164+u.rand(92));
  public static Color darkcolor() {
     final short tot = 128;
     short col[]= new short[3];
     col[0] = (short)(tot/2+u.rand(tot/2));
     col[1] = (short)u.rand(Math.max(1,tot-col[0]));
     col[2] = (short)(tot-col[0] -col[1]);
     short o[] = u.shuffle(u.select((short)3,(short)3));
     return new Color(col[o[0]],col[o[1]],col[o[2]]);
  }
  
  public static Color backgroundcolor(Color avoid[]) {
      return backgroundcolor2(avoid, false);
  }

  public static Color backgroundcolor(Color avoid[], boolean ignoreSetting) {
      return backgroundcolor2(avoid, ignoreSetting);
  }

  public static Color backgroundcolor2(Color avoid[], boolean ignoreSetting) {
     
      final int firstint = 190; // was 190
      final int secondint = 64;//  was 64
      final int thirdint = 128; //  was 128
      
     final short tot = firstint;
     short col[]= new short[3];
     Color cc;
     String ss = student.optionstring("bgcolor");
     if(ss != null) {
         cc = new Color(Integer.parseInt(ss));
         if(ignoreSetting)
             avoid = new Color[]{cc};
         else{
             if(avoid != null) {
               for(short i=0;i<avoid.length;++i)
                  if(tooclose(avoid[i],cc)) return cc.darker();
             }
             return cc;
         }
     }
     loop: while(true) {
        if(avoid == null || !tooclose(avoid[0],Color.yellow)) {
           col[0] = (short)(secondint+u.rand(secondint));
           col[1] = (short)u.rand(tot-col[0]);
           col[2] = (short)(tot-col[0] -col[1]);
           short o[] = u.shuffle(u.select((short)3,(short)3));
           cc = new Color(
                 Math.max(Math.min(255,     thirdint+col[o[0]]      ),0),
                 Math.max(Math.min(255,       thirdint+col[o[1]]    ),0),
                 Math.max(Math.min(255,       thirdint+col[o[2]]    ),0)
                   );
        }
        else {
           col[0] = (short)(secondint+u.rand(secondint));
           col[1] = (short)(tot-col[0]);
           short o[] = u.shuffle(u.select((short)2,(short)2));
           cc = new Color(
                Math.max(Math.min(255,   thirdint+col[o[0]]       ),0),
                   0,
               Math.max(Math.min(255,    thirdint+col[o[1]]       ),0)
                   );
        }
        if(avoid != null) {
           for(short i=0;i<avoid.length;++i)
              if(tooclose(avoid[i],cc)) continue loop;
        }
        if(shark.testing){
            float lightness = 0.0f;
            if(sharkStartFrame.studentList[sharkStartFrame.currStudent].name.equalsIgnoreCase("Nancy100"))
                lightness = 0.0f;
            else if(sharkStartFrame.studentList[sharkStartFrame.currStudent].name.equalsIgnoreCase("Nancy90"))
                lightness = 0.1f;   
            else if(sharkStartFrame.studentList[sharkStartFrame.currStudent].name.equalsIgnoreCase("Nancy80"))
                lightness = 0.2f; 
            else if(sharkStartFrame.studentList[sharkStartFrame.currStudent].name.equalsIgnoreCase("Nancy70"))
                lightness = 0.3f;
            else if(sharkStartFrame.studentList[sharkStartFrame.currStudent].name.equalsIgnoreCase("Nancy60"))
                lightness = 0.4f;
            else if(sharkStartFrame.studentList[sharkStartFrame.currStudent].name.equalsIgnoreCase("Nancy50"))
                lightness = 0.5f;
            else if(sharkStartFrame.studentList[sharkStartFrame.currStudent].name.equalsIgnoreCase("Nancy40"))
                lightness = 0.6f;
            else if(sharkStartFrame.studentList[sharkStartFrame.currStudent].name.equalsIgnoreCase("Nancy30"))
                lightness = 0.7f;
            else if(sharkStartFrame.studentList[sharkStartFrame.currStudent].name.equalsIgnoreCase("Nancy20"))
                lightness = 0.8f;
            else if(sharkStartFrame.studentList[sharkStartFrame.currStudent].name.equalsIgnoreCase("Nancy10"))
                lightness = 0.9f;
            else if(sharkStartFrame.studentList[sharkStartFrame.currStudent].name.equalsIgnoreCase("Nancy0"))
                lightness = 1.0f;
            else return cc;
            return Lighten(cc, lightness);
        }
        else return cc;
     }
  }
  
  
  public static Color Lighten(Color inColor, float correctionFactor)
{
float red = (255 - inColor.getRed()) * correctionFactor + inColor.getRed();
float green = (255 - inColor.getGreen()) * correctionFactor + inColor.getGreen();
float blue = (255 - inColor.getBlue()) * correctionFactor + inColor.getBlue();
return new Color((int)red, (int)green, (int)blue);
}
  
  
  public static Color fairlydarkcolor() {
     final short tot = 172;
     short col[]= new short[3];
     col[0] = (short)(tot/2+u.rand(tot/2));
     col[1] = (short)u.rand(Math.max(1,tot-col[0]));
     col[2] = (short)(tot-col[0] -col[1]);
     short o[] = u.shuffle(u.select((short)3,(short)3));
     return new Color(col[o[0]],col[o[1]],col[o[2]]);
  }
  public static Color eyecolor() {
     return new Color(u.rand(128),u.rand(128),128+u.rand(128));
  }
  public static Color watercolor() {
     return new Color(0,130+u.rand(40),180+u.rand(40));
  }
  public static Color redcolor() {
     return new Color(255,u.rand(256),0);
  }
  public static Color bluecolor() {
     return new Color(0,u.rand(128),255);
  }
  public static Color lightbluecolor() {
     return new Color(128+u.rand(64),128+u.rand(64),255);
  }

  public static Color lighter(Color col, float factor) {
	return new Color(Math.max(   Math.min((int)(col.getRed()  *factor), 255), 0),
			 Math.max(   Math.min((int)(col.getGreen()*factor), 255), 0),
			 Math.max(   Math.min((int)(col.getBlue() *factor), 255), 0));
  }

  public static Color darker(Color col, float factor) {
	return new Color(Math.max(   Math.min((int)(col.getRed()  *factor), 255), 0),
			 Math.max(   Math.min((int)(col.getGreen()*factor), 255), 0),
			 Math.max(   Math.min((int)(col.getBlue() *factor), 255), 0));
  }

  public static Color graycolor() {
     int i = 96 + rand(128);
     return new Color(i,i,i);
  }
  public static Color mouthcolor() {
     return new Color(128+u.rand(128),u.rand(64),u.rand(64));
  }
  public static Color haircolor() {
     return new Color(+u.rand(128),u.rand(48),u.rand(48));
  }
  public static Color fishcolor() {
      int red,blue,green;
      do {
        blue =  u.rand(128);
        red = u.rand(256);
        green = u.rand(256);
      }while(red+green+blue > 600 || red+green+blue < 320 || red+green < 220);
      return new Color(red,green,blue);
  }
  public static Color fishcolor(Color bg) {
      Color c;
      do {
        c = fishcolor();
      }while(tooclose(c,bg));
      return c;
  }
  public static boolean tooclose(Color color1,Color color2) {
    if(color1==null) return false;
    if(color2==null) return false;
    return  Math.abs(color1.getGreen() - color2.getGreen()) < 64
        && Math.abs(color1.getRed() - color2.getRed()) < 64
        && Math.abs(color1.getBlue() - color2.getBlue()) < 64 ;
  }
  /**
   * Compares two colours and returns true if their hue is too close to each other.
   * @param color1 Colour to be compared
   * @param color2 Colour to be compared
   * @return True if the difference between the two colours is too close
   */
  public static boolean tooclose2(Color color1,Color color2) {
    return  Math.abs(color1.getGreen() - color2.getGreen())
        + Math.abs(color1.getRed() - color2.getRed())
        + Math.abs(color1.getBlue() - color2.getBlue()) < 256 ;
  }
  public static Color mix(Color color1,Color color2,int time, int tottime) {
    if(tottime<time) return color2;
    int t2 = tottime - time;
    int r = (color1.getRed()*t2 + color2.getRed()*time)/tottime;
    int g = (color1.getGreen()*t2 + color2.getGreen()*time)/tottime;
    int b = (color1.getBlue()*t2 + color2.getBlue()*time)/tottime;
    return  new Color( Math.min(255, Math.max(0, r)),
                       Math.min(255, Math.max(0, g)),
                       Math.min(255, Math.max(0, b)));
  }
  //-----------------------------------------------------------
  public static int mindist(Polygon p, int x, int y) {
     int i,j,tot = p.npoints,px[]=p.xpoints,py[]=p.ypoints;
     int d = 0x7fffffff,d1;
     for(i=0; i<tot;++i) {
       if((d1 = (px[i]-x)*(px[i]-x)
                   +  (py[i]-y)*(py[i]-y))<d) d =d1;
     }
     return d;
  }
  //-----------------------------------------------------------
  public static int maxdist(Polygon p, int x, int y) {
     int i,j,tot = p.npoints,px[]=p.xpoints,py[]=p.ypoints;
     int d = 0,d1;
     for(i=0; i<tot;++i) {
       if((d1 = (px[i]-x)*(px[i]-x)
                   +  (py[i]-y)*(py[i]-y))>d) d =d1;
     }
     return d;
  }
  //-----------------------------------------------------------
  public static Polygon multx(Polygon p, int mu, int di) {
     int i,tot = p.npoints,px[]=new int[tot],py[]=new int[tot];
     for(i=0; i<tot;++i) {
       px[i] = p.xpoints[i] * mu / di;
       py[i] = p.ypoints[i];
     }
     return  new Polygon(px,py,tot);

  }
  //-----------------------------------------------------------
  public static int closest(Polygon p, int x, int y) {
     int i,ret=0,tot = p.npoints,px[]=p.xpoints,py[]=p.ypoints;
     int d = 0x7fffffff,d1;
     for(i=0; i<tot;++i) {
       if((d1 = (px[i]-x)*(px[i]-x)
               +  (py[i]-y)*(py[i]-y))<d)  {d = d1; ret = i; }
     }
     return ret;
  }
  //------------------------------------------------------------
  public static Polygon randpolygon(int x1, int y1, int rad) {
     Polygon p = new Polygon();
     int tot = Math.min(rad*3,1000);
     int r=rad/2+u.rand(rad/2);
     short start = u.rand((short)tot);
     for(short i=start;i<tot+start;++i) {
        r = r - 2 + u.rand(5);
        r = Math.max(rad/2,Math.min(rad,r));
        p.addPoint(x1+(int)(r*Math.cos(Math.PI*i*2/tot)),
                           y1+(int)(r*Math.sin(Math.PI*i*2/tot)));
     }
     return p;
  }
  public static String stripslashes(String ss) {
    String s = new String(ss);
    int i;
    while((i = (short)s.indexOf('/')) >= 0) s = s.substring(0,i)+s.substring(i+1);
    return s;
  }
  public static String strip(String ss, char ch) {
    String s = new String(ss);
    int i;
    while((i = (short)s.indexOf(ch)) >= 0) s = s.substring(0,i)+s.substring(i+1);
    return s;
  }
  public static String stripspaces(String s) {
     int len = s.length()-1;
     int dec;
     while(len >= 0  && ((dec=s.charAt(len)) == ' ' || isUnknownWhiteSpace(dec))) --len;
     if(len == s.length()-1) return s;
     else return s.substring(0,len+1);
  }
  public static String stripspaces2(String s) {
     while (s.length()>0 && (s.charAt(0) == ' ' || isUnknownWhiteSpace((int)s.charAt(0)))) s = s.substring(1);
     return stripspaces(s);
  }
  
  public static String stripspaces3(String s) {
     s = stripspaces2(s);
     char c[] = s.toCharArray();
     for(int i = 1; i < c.length-1; i++){
         if(isUnknownWhiteSpace((int)c[i])){
             c[i]=' ';
         }
     }
     return String.valueOf(c);
  }

  // strange characters being picked up from Excel that are not being recognised as white space
  static boolean isUnknownWhiteSpace(int n){
      return (n==160) || (n<32);
  }
  //------------------------------------------------------------
  static boolean crossoverat(Polygon p, int i, int j) {
           int x1 = p.xpoints[i];
           int y1 = p.ypoints[i];
           int x2 = p.xpoints[i+1];
           int y2 = p.ypoints[i+1];
           int x3 = p.xpoints[j];
           int y3 = p.ypoints[j];
           int x4 = p.xpoints[j+1];
           int y4 = p.ypoints[j+1];
            return Math.max(x1,x2) >=  Math.min(x3,x4)
              && Math.max(y1,y2) >=  Math.min(y3,y4)
              && Math.min(x1,x2) <=  Math.max(x3,x4)
              && Math.min(y1,y2) <=  Math.max(y3,y4)
              && ((x1-x3)*(y4-y3) - (y1-y3)*(x4-x3))
                 * ((x2-x3)*(y4-y3) - (y2-y3)*(x4-x3)) <= 0
              && ((x3-x1)*(y2-y1) - (y3-y1)*(x2-x1))
                 * ((x4-x1)*(y2-y1) - (y4-y1)*(x2-x1)) <= 0;
 }
  //------------------------------------------------------------
  static boolean crosses(Polygon p) {
     if(p.npoints <= 8) return false;
     int x1,x2,y1,y2;
     int x3 = p.xpoints[p.npoints-2];
     int y3 = p.ypoints[p.npoints-2];
     int x4 = p.xpoints[p.npoints-1];
     int y4 = p.ypoints[p.npoints-1];
     for(int i = 0; i < p.npoints-8; ++i) {
        x1 = p.xpoints[i];
        y1 = p.ypoints[i];
        x2 = p.xpoints[i+1];
        y2 = p.ypoints[i+1];
        if(Math.max(x1,x2) >=  Math.min(x3,x4)
              && Math.max(y1,y2) >=  Math.min(y3,y4)
              && Math.min(x1,x2) <=  Math.max(x3,x4)
              && Math.min(y1,y2) <=  Math.max(y3,y4)
              && ((x1-x3)*(y4-y3) - (y1-y3)*(x4-x3))
                 * ((x2-x3)*(y4-y3) - (y2-y3)*(x4-x3)) <= 0
              && ((x3-x1)*(y2-y1) - (y3-y1)*(x2-x1))
                 * ((x4-x1)*(y2-y1) - (y4-y1)*(x2-x1)) <= 0) return true;
     }
     return false;
  }
  //------------------------------------------------------------
  public static Polygon clean(Polygon p) {
     int i,j, k;
     outer: for(i=2;i<p.npoints-1;++i) {
        for(j=0;j<i-2;++j) {
           if(crossoverat(p,i,j)) {
              if(i-j < p.npoints/2) {
                 Polygon pnew = new Polygon();
                 for(k=0;k<=j;++k) pnew.addPoint(p.xpoints[k], p.ypoints[k]);
                 for(k=i+1;k<p.npoints;++k) pnew.addPoint(p.xpoints[k], p.ypoints[k]);
                 p = pnew;
                 i=j+1;
                 continue outer;
              }
              else {
                 Polygon pnew = new Polygon();
                 for(k=j+1;k<=i;++k) pnew.addPoint(p.xpoints[k], p.ypoints[k]);
                 return pnew;
              }
           }
        }
     }
     return p;
  }
  //------------------------------------------------------------
  public static Polygon clean2(Polygon p,int minmin) {
     int i,j, k,m,had;
     for(i=0;i<p.npoints;++i) {
        for(j=(i+1)%p.npoints;j != i;j=(j+1)%p.npoints) {
           if((p.xpoints[i] - p.xpoints[j])*(p.xpoints[i] - p.xpoints[j])
               + (p.ypoints[i] - p.ypoints[j])*(p.ypoints[i] - p.ypoints[j])
                   > minmin) break;
        }
        for(k=(i-1+p.npoints)%p.npoints;k != j;k=(k-1+p.npoints)%p.npoints) {
           if((p.xpoints[i] - p.xpoints[k])*(p.xpoints[i] - p.xpoints[k])
               + (p.ypoints[i] - p.ypoints[k])*(p.ypoints[i] - p.ypoints[k])
                   > minmin) break;
        }
        for(m=(j+1)%p.npoints,had=0;m != k; m=(m+1)%p.npoints,++had) {
           if((p.xpoints[i] - p.xpoints[m])*(p.xpoints[i] - p.xpoints[m])
               + (p.ypoints[i] - p.ypoints[m])*(p.ypoints[i] - p.ypoints[m])
                   < minmin) {
                Polygon pnew = new Polygon();
                if(had >= p.npoints/2)
                    for(k=i;k != m;k=(k+1)%p.npoints) pnew.addPoint(p.xpoints[k], p.ypoints[k]);
                else for(k=m;k != i;k=(k+1)%p.npoints) pnew.addPoint(p.xpoints[k], p.ypoints[k]);
                return pnew;
           }
        }
     }
     return p;
  }
  //------------------------------------------------------------
  public static Polygon[] splitAtCrossover(Polygon p1) {
     Polygon p, pg[] = new Polygon[] {p1};
     int i,j, k;

     p = pg[0];
     for(i=2;i<p.npoints-1;++i) {
        for(j=0;j<i-2;++j) {
           if(crossoverat(p,i,j)) {
               Polygon pgnew[] = new Polygon[pg.length+1];
               System.arraycopy(pg,0,pgnew,0,pg.length);
               pg=pgnew;
               pg[pg.length-1] = extract(p,j+1,i);
               pg[0] = p = extract(p,0,j,i+1,p.npoints);
               i = j+1;
           }
        }
     }
     return pg;
  }
  //----------------------------------------------------------
  public static Polygon extract(Polygon p, int from, int to) {
     int i;
     Polygon p2 = new Polygon();
     for(i=from;i<=to;++i) {
        p2.addPoint(p.xpoints[i],p.ypoints[i]);
     }
     return p2;
  }
  //----------------------------------------------------------
  public static Polygon extract(Polygon p, int from1, int to1,int from2,int to2) {
     int i;
     Polygon p2 = new Polygon();
     for(i=from1;i<=to1;++i) {
        p2.addPoint(p.xpoints[i],p.ypoints[i]);
     }
     for(i=from2;i<=to2;++i) {
        p2.addPoint(p.xpoints[i],p.ypoints[i]);
     }
     return p2;
  }
  //-------------------------------------------------------------
  public static long mindist2(Polygon p, int x, int y) {
     int i,j,tot = p.npoints,px[]=p.xpoints,py[]=p.ypoints;
     long d = dist(x,y,px[0],py[0],px[tot-1],py[tot-1]), d1;
     for(i=0; i<tot-1;++i) {
       if((d1 = dist(x,y,px[i],py[i],px[i+1],py[i+1])) < d) d =d1;
     }
     return d;

  }
  //---------------------------------------------------------------
  // centre of circle thru 3 points
  // (x-x1)*(x-x1) + (y-y1)*(y-y1) = (x-x2)*(x-x2) + (y-y2)*(y-y2)
  //  x*x - 2x.x1 + x1*x1 + y*y + 2y*y1 + y1*y1 = x*x - 2x.x2 + x2*x2 + y*y + 2y*y2 + y2*y2
  //                     2x(x1-x2) + 2y(y1-y2) = x1*x1 + y1*y1 - x2*x2 - y2*y2;
  // and similarly       2x(x1-x3) + 2y(y1-y3) = x1*x1 + y1*y1 - x3*x3 - y3*y3;
  //  so that 2x * ((x1-x2)*(y1-y3) - (x1-x3)*(y1-y2)) = (x1*x1+y1*y1-x2*x2-y2*y2)*(y1-y3) - (x1*x1+y1*y1-x3*x3-y3*y3)*(y1-y2)
  // and      2y * ((y1-y2)*(x1-x3) - (y1-y3)*(x1-x2)) = (x1*x1+y1*y1-x2*x2-y2*y2)*(x1-x3) - (x1*x1+y1*y1-x3*x3-y3*y3)*(x1-x2)

  static public Point circlecentre(int x1,int y1,int x2,int y2, int x3, int y3) {
    int topx = (x1*x1+y1*y1-x2*x2-y2*y2)*(y1-y3) - (x1*x1+y1*y1-x3*x3-y3*y3)*(y1-y2);
    int botx = 2 * ((x1-x2)*(y1-y3) - (x1-x3)*(y1-y2));
    int topy = (x1*x1+y1*y1-x2*x2-y2*y2)*(x1-x3) - (x1*x1+y1*y1-x3*x3-y3*y3)*(x1-x2);
    int boty = 2 * ((y1-y2)*(x1-x3) - (y1-y3)*(x1-x2));
    if (botx == 0 || boty == 0) return null;
    else return new Point(topx/botx,topy/boty);
  }
  //------------------------------------------------------------------
      // reflects x,y across line x1,y1 to x2,y2
  static public Point reflect(int x, int y, int x1,int y1,int x2, int y2) {
     Point p = closest(x,y,x1,y1,x2,y2);
     return new Point(p.x*2-x,p.y*2-y);
  }
  
  static boolean displayCourse(String courseName){
    // is course name a default hidden course (by the program)
    if(u.findString(u.defaulthiddencourses, courseName)<0)return true;      
    String universalhiddencourses = (String)db.find(sharkStartFrame.optionsdb, "universalhiddencourses", db.TEXT);
    if(universalhiddencourses == null || universalhiddencourses.length()==0)return false;
    // array of course names which are excluded via universal settings
    String ssuniversalhiddencourses[] = u.splitString(universalhiddencourses);
    return u.findString(ssuniversalhiddencourses, courseName) < 0;    
  }  
  
  
  //------------------------------------------------------------
       // dist (squared) of (x,y) from line between two points
  static long dist(int x, int y, int x1, int y1, int x2, int y2) {
     long i = (y-y2)*(x1-x2) - (y1-y2)*(x-x2);
     return  i*i/((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2));
  }
  //------------------------------------------------------------
       //  closest point on line
  public static Point closest(int  x, int y, int x1, int y1, int x2, int y2) {
     int d = (int)Math.sqrt((x-x2)*(x-x2)+(y-y2)*(y-y2) - dist(x,y,x1,y1,x2,y2));
     double a = Math.atan2(y1-y2,x1-x2);
     return new Point(x2+(int)(d*Math.cos(a)), y2+(int)(d*Math.sin(a)));
  }
  //------------------------------------------------------------
       // signed dist of (x,y) from line between two points
  public static int signeddist(int x, int y, int x1, int y1, int x2, int y2) {
     long i = (long)(y-y1)*(x2-x1) - (long)(y2-y1)*(x-x1);
     int dist = (int)Math.sqrt(i*i/((long)(x2-x1)*(x2-x1)+(long)(y2-y1)*(y2-y1)));
     return (i > 0)?dist:-dist;
  }
  //------------------------------------------------------------
       // check if (x,y) is over line between two points
  public static boolean overline(int x, int y, int x1, int y1, int x2, int y2) {
     int d1 = (x-x2)*(x-x2) + (y-y2)*(y-y2);
     int d2 = (x-x1)*(x-x1) + (y-y1)*(y-y1);
     int d3 = (x1-x2)*(x1-x2) + (y1-y2)*(y1-y2);
     return  d1 < d3+d2 && d2 < d3+d1;
  }
  //------------------------------------------------------------
       // check if two lines cross
  public static boolean linescross(int x1, int y1, int x2, int y2,int x3,int y3,int x4,int y4) {
     return (Math.max(x1,x2) >=  Math.min(x3,x4)
              && Math.max(y1,y2) >=  Math.min(y3,y4)
              && Math.min(x1,x2) <=  Math.max(x3,x4)
              && Math.min(y1,y2) <=  Math.max(y3,y4)
              && ((long)(x1-x3)*(y4-y3) - (y1-y3)*(x4-x3))
                 * ((long)(x2-x3)*(y4-y3) - (y2-y3)*(x4-x3)) <= 0
              && ((long)(x3-x1)*(y2-y1) - (y3-y1)*(x2-x1))
                 * ((long)(x4-x1)*(y2-y1) - (y4-y1)*(x2-x1)) <= 0);
  }
  //------------------------------------------------------------
       // check 2 points on same side of line between two points
       // line is (x3,y3 -> x4,y4)
  public static boolean sameside(int x1, int y1, int x2, int y2,int x3,int y3,int x4,int y4) {
     return ((long)(x1-x3)*(y4-y3) - (y1-y3)*(x4-x3))
                 * ((x2-x3)*(y4-y3) - (y2-y3)*(x4-x3)) >= 0;
  }
  //-------------------------------------------------------------
        // build 'explosion' type polygon
  public static Polygon explode(int x, int y, int min, int max) {
     return explode(x,y,min,max,max);
  }
  public static Polygon explode(int x, int y, int minx, int maxx,int maxy) {
     Polygon p = new Polygon();
     double a, limit,inc = Math.PI/32,
               ratio = (double)maxy/Math.max(1,maxx);
     boolean flip=false;
     int minchange = (maxx-minx)/4;
     int rad = minx + u.rand(Math.max(1,maxx-minx));
     a =  Math.PI*2*u.rand(256)/256;
     limit = a + Math.PI*2 - inc/2;

     for(; a<=limit; flip = !flip,
                           a += inc+inc*u.rand(256)/256) {
        if(flip)  rad += minchange + u.rand(Math.max(1,maxx - rad -minchange));
        else   rad -= minchange + u.rand(Math.max(1,rad - minx - minchange));
        p.addPoint((int)(x+rad*Math.cos(a)),
                         (int)(y+rad*Math.sin(a)*ratio));
     }
     return p;
  }
  //-----------------------------------------------------------
  static public Polygon wavyline(int x1,int y1,int len,double a, int dmin, int dmax) {
     int dy=-4+u.rand(9),y=0,x;
     Polygon p = new Polygon();
     dmax *= 10;
     dmin *= 10;
     int cosx = (int)(Math.cos(a) * 1000);
     int sinx = (int)(Math.sin(a) * 1000);

     for(x=0; x<len; ++x) {
        if(dy >= 4 || dy > -4 && u.rand(2) != 0) --dy;
        else ++dy;
        y += dy;
        if(y > dmax) {y = dmax;dy = -1;}
        if(y < dmin) {y = dmin;dy = 1;}
        p.addPoint(x1+x*cosx/1000-y*sinx/10/1000, y1+x*sinx/1000+y*cosx/10/1000);
     }
     return p;
  }
  
      static void writeLogToDesktop(String marker,String text){
        try(FileWriter fw = new FileWriter(getDesktopPath ()+shark.sep+"error30issue.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {
            String t = String.valueOf(System.currentTimeMillis());
            out.println(t + "   MARKER " + marker+ "     " + text);
        } catch (IOException e) {
            //exception handling left as an exercise for the reader
        }
      }
  //----------------------------------------------------------
  static public String longest(String[] s) {
     if(s==null || s.length==0) return(" ");
//startPR2007-11-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//     FontMetrics fm = sharkStartFrame.mainFrame.getFontMetrics(new Font(sharkStartFrame.wordfont.getName(),sharkStartFrame.wordfont.getStyle(),30));
       FontMetrics fm = sharkStartFrame.mainFrame.getFontMetrics(sharkStartFrame.wordfont.deriveFont((float)30));
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     int len =0, len2;
     String ret=null;
     for(short i=0;i<s.length;++i) {
        if((len2=fm.stringWidth(s[i])) > len) {
           ret = s[i];
           len = len2;
        }
     }
     return ret;
  }
  //------------------------------------------------------------
  static public Dimension getdim(String[] s,FontMetrics fm, int margin) {
     int len =0, len2;
     String ret=null;
     for(short i=0;i<s.length;++i) {
        if((len2=fm.stringWidth(s[i])) > len) {
           len = len2;
        }
     }
     return new Dimension(len + margin*2,s.length * fm.getHeight() + margin*2);
  }
  //------------------------------------------------------------
  static public Dimension getdim(String s,FontMetrics fm, int margin) {
     return new Dimension(fm.stringWidth(s) + margin*2, fm.getHeight()*2);
  }
  //------------------------------------------------------------
  static public Dimension getdim2(String[] s,FontMetrics fm, int margin) {
     int len =0, len2;
     for(short i=0;i<s.length;++i) {
        if((len2=fm.stringWidth(s[i])) > len) {
           len = len2;
        }
     }
     return new Dimension(len + margin*2,s.length * fm.getHeight());
  }
  //------------------------------------------------------------
  static public Dimension getdim2(String[] s,FontMetrics fm[], int margin) {
     int len =0, len2;
     for(short i=0;i<s.length;++i) {
        if((len2=stringWidth(fm, s[i])) > len) {
           len = len2;
        }
     }
     return new Dimension(len + margin*2,s.length * fm[0].getHeight());
  }
  //------------------------------------------------------------
   static public Dimension getdim(word[] s,FontMetrics fm, int margin) {
      int len =0, len2;
      for(short i=0;i<s.length;++i) {
         if((len2=fm.stringWidth(s[i].v())) > len) {
            len = len2;
         }
      }
      return new Dimension(len + margin*2,s.length * fm.getHeight());
   }
  //--------------------------------------------------------------


   static double getDistanceBetween(Point p1, Point p2) {
      return Math.sqrt(Math.pow((p2.getX() - p1.getX()), 2) + Math.pow((p2.getY() - p1.getY()), 2));
   }
   
                // width with italics & bold
  static int stringWidth(FontMetrics fm[],String s) {
    int i;
    if((i=s.indexOf('^')) < 0) return fm[0].stringWidth(s);
    else {
      int len=0,offset = 0,slen = s.length(),tot,j;
      do {
         len += fm[0].stringWidth(s.substring(offset,i));
         tot=1;
         offset=i;
         while(++offset<slen && s.charAt(offset)=='^') ++tot;
         tot = Math.min(tot,fm.length-1);
         if(offset<slen) {
            j =  s.indexOf('^',offset);
            if(j<0) return len + fm[tot].stringWidth(s.substring(offset));
            else len += fm[tot].stringWidth(s.substring(offset,j));
            offset = j+1;
         }
      } while(offset<slen && (i=s.indexOf('^',offset)) >= 0);
      if(offset<slen) return len + fm[0].stringWidth(s.substring(offset));
      else return len;
    }
  }
  //--------------------------------------------------------------
                // display with italics & bold
  static void drawString(Graphics g,Font f[], FontMetrics fm[], String s, int x,int y) {
    int i;
    if((i=s.indexOf('^')) < 0) {g.setFont(f[0]);g.drawString(s,x,y); return;}
    else {
      int offset = 0,slen = s.length(),tot,j;
      String ss;
      do {
         g.setFont(f[0]);
         g.drawString(ss=s.substring(offset,i),x,y);
         x += fm[0].stringWidth(ss);
         tot=1;
         offset=i;
         while(++offset<slen && s.charAt(offset)=='^') ++tot;
         tot = Math.min(tot,fm.length-1);
         if(offset<slen) {
            g.setFont(f[tot]);
            j =  s.indexOf('^',offset);
            if(j<0) {
              g.drawString(s.substring(offset),x,y);
              if(tot>1) g.drawLine(x,y+2,x+fm[tot].stringWidth(ss),y+2);
              return;
            }
            g.drawString(ss=s.substring(offset,j),x,y);
            if(tot>1) g.drawLine(x,y+2,x+fm[tot].stringWidth(ss),y+2);
            x += fm[tot].stringWidth(ss);
            offset = j+1;
         }
      } while(offset<slen && (i=s.indexOf('^',offset)) >= 0);
      if(offset<slen) {
         g.setFont(f[0]);
         g.drawString(s.substring(offset),x,y);
      }
    }
  }
  //----------------------------------------------------------
     // draw box with message m
  static public void pointto(Graphics g, int x, int y, String m) {
     Font savefont = g.getFont();
     Color savecolor = g.getColor();
     int xb,yb;
     g.setFont(sharkStartFrame.wordfont);
     FontMetrics fm = g.getFontMetrics();
     int w1 = fm.stringWidth(m);
     int h = fm.getHeight() * 3/2;
     int dx = fm.stringWidth("aaa");
     int w = w1+dx;
     if(y > h*2) yb = y - h*2;
     else yb = y+h;

     xb =  Math.max(dx, x - w - dx);
     g.setColor(Color.white);
     g.fillRect(xb,yb,w,h);
     int xx = Math.max(x-dx, 0);
     int yy = yb;
     if(yb<y) yy += h;
     g.fillPolygon(new int[] {x,xx,xx-dx}, new int[] {y,yy,yy}, 3);
     g.setColor(Color.black);
     g.drawString( m, xb + (w-w1)/2, yb + (h - fm.getHeight())/2+fm.getAscent());
     g.setFont(savefont);
     g.setColor(savecolor);
  }
  //-------------------------------------------------------------
  public static void loadsystemgif() {
     String dir =  sharkStartFrame.publicPathplus + "sprites" + sharkStartFrame.separator;
     Toolkit t =Toolkit.getDefaultToolkit();
     ear = t.getImage(dir+"ear.png");
     eye = t.getImage(dir+"eye.png");
     eari  = new ImageIcon(u.ear.getScaledInstance(-1,sharkStartFrame.screenSize.height/20,Image.SCALE_SMOOTH));
     eyei  = new ImageIcon(u.eye.getScaledInstance(-1,sharkStartFrame.screenSize.height/20,Image.SCALE_SMOOTH));
  }
  //-------------------------------------------------------------
  public static Dimension imagefit(Image im,int w, int h) {
     int iw,ih;
     while((iw = im.getWidth(sharkStartFrame.mainFrame)) < 0
              || (ih = im.getHeight(sharkStartFrame.mainFrame)) < 0) pause(200);
     if((long)w*ih <= (long)h*iw) return new Dimension(w, (int)((long)w*ih/iw));
     else             return new Dimension((int)((long)h*iw/ih),h);
  }
  /**
   * Splits the string passed at '|'
   * @param s
   * @return
   */
  public static String[] splitString(String s) {
    return u2_base.splitString(s);
  }

  public static String[] splitString(String s, char splitat, boolean removeEmpty) {  // split string
      return u2_base.splitString(s, splitat, removeEmpty);
  }

  //--------------------------------------------------------------
  public static String[] centreString(String s[], FontMetrics m) {  // split string at '|'
    if(s==null) return new String[0];
    int space = m.charWidth(' ');
    int i,j,w = 0;
    for(i=0;i<s.length;++i) {
       w = Math.max(w,m.stringWidth(s[i]));
    }
    for(i=0;i<s.length;++i) {
       if(s[i].length()==0) continue;
       j = (w-m.stringWidth(s[i]))/space/2;
       while (j-- > 0)  s[i] = ' ' + s[i];
    }
    return s;
  }
  //--------------------------------------------------------------
  public static String centreString(String s, FontMetrics m) {  // split string at '|'
    if(s==null) return new String();
    return combineString(centreString(splitString(s),m));
  }
//startPR2008-06-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  public static String splitStringToHtml(String s) {
    return s.replaceAll("\\|","<br>");
  }
//endPRimagesandvids^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  //--------------------------------------------------------------
  public static String[] splitString(String s,char splitat) {  // split string
    return u2_base.splitString(s, splitat);
  }
  //--------------------------------------------------------------
  public static String[] splitStringi(String s,char splitat) {  // split string and ignore empty strings
    if(s==null) return new String[0];
     short i=0, j=0,tot=0;
     String out[];
     while((j = (short)s.substring(i).indexOf(splitat)) >= 0) {if(j>0) ++tot; i=(short)(i+j+1);}
     out = new String[tot+1];
     i = tot = 0;
     while((j = (short)s.substring(i).indexOf(splitat)) >= 0) {
        if(j>0) out[tot++] = s.substring(i,i+j);
        i=(short)(i+j+1);
     }
     out[tot] = s.substring(i);
     return(out);
  }
  //--------------------------------------------------------------
  public static String[] splitPhrase(String s) {  // split string
    if(s==null) return new String[0];
     short i=0, j=0,tot=0,splitat = ' ';
     while(s.endsWith("?") || s.endsWith("!") || s.endsWith(".")|| s.endsWith(" "))
                                                             s = s.substring(0,s.length()-1);
     String out[];
     while((j = (short)s.substring(i).indexOf(splitat)) >= 0) {++tot; i=(short)(i+j+1);}
     out = new String[tot+1];
     i = tot = 0;
     while((j = (short)s.substring(i).indexOf(splitat)) >= 0) {
        out[tot++] = s.substring(i,i+j).toLowerCase();
        i=(short)(i+j+1);
     }
     out[tot] = s.substring(i);
     return(out);
  }
  //----------------------------------------------------------------------------
  static public String[] splitintowords(String s) {
    int i=0,j=0,last=0,len=s.length();
    String ret[]=new String[0];
    while(i<len) {
       while (i<len && letters.indexOf(s.charAt(i))>=0) ++i;
       if(i>last) ret = addString(ret,s.substring(last,i));
       last = i;
       while (i<len && letters.indexOf(s.charAt(i))<0) ++i;
       if(i>last) ret = addString(ret,s.substring(last,i));
       last = i;
    }
    return ret;
  }
  //--------------------------------------------------------------
  public static String endPhrase(String s) {  // split string
     if(s==null) return "";
     int i = s.length()-1;
     while(i>=0 && s.charAt(i) == '?' || s.charAt(i) == '!' || s.charAt(i) == '.' || s.charAt(i) == ' ') --i;
     return s.substring(i+1);
  }
  //--------------------------------------------------------------
  public static int count(String s,char splitat) {  // scount occurences
     if(s==null) return 0;
     int i=0, j=0,tot=0;
     while((j = s.indexOf(splitat,i)) >= 0) {++tot; i=j+1;}
     return tot;
  }
  //---------------------------------------------------------------
  public static String splitup(String ss) {
     short splits,i,j,conslen;
     int syls = syllabletot(ss);
     short start = 0;
     String addsuff = null;
     if(syls < 2) {
        return ss;
     }
     String s = new String(ss);
     if(syls > 1 && s.indexOf('/') < 0) {
        for(i=0;i<prefix.length;++i) {
           j = (short)prefix[i].length();
           if(s.length()>j && prefix[i].equals(s.substring(0,j))) {
              s  = s.substring(0,j) + "/" + s.substring(j);
              start = (short)(j+1);
              break;
           }
        }
        for(i=0;i<suffix.length;++i) {
           j = (short)(s.length()-suffix[i].length());
           if(j>0 && suffix[i].equals(s.substring(j))) {
              if(s.charAt(j-1) != '/') {
                  s  = s.substring(0,j);
                  addsuff = suffix[i];
              }
              break;
           }
        }
     }
     loop1: for(i=start;i<s.length()-1 && (j=scanfor(s.substring(i),vowelsy)) >= 0; ++i) {
        i += j;
        if(i==0 && s.charAt(0)=='y') ++i;
        if(i>0 && s.substring(i-1,i+1).equalsIgnoreCase("qu")) ++ i;
        if(i<s.length()-1 && vowelsy.indexOf(s.charAt(++i)) >= 0) {
           ++i;
        }
        if(i >= s.length()) break loop1;
              // scan consonents
        for (j=i;j<s.length() && vowelsy.indexOf(s.charAt(j)) < 0;++j);
           //consonent at end or followed by just 'e' - exit can break before
        if (j >= s.length()  || j == s.length()-1 && s.charAt(j) == 'e') break loop1;
           // single consonent - can break before or after
        conslen = (short)(j-i);
        if(conslen == 1 || together(s.substring(i,j)))  {
             s = s.substring(0,i)+"_"+s.substring(i);
             i = (short)(j+1);
             s = s.substring(0,i)+"_"+s.substring(i);
             continue loop1;
        }
            // 2 consonants - split between or after
        else if(conslen == 2) {
           ++i;
           s = s.substring(0,i)+"_"+s.substring(i);
           i+=2;
           s = s.substring(0,i)+"_"+s.substring(i);
        }
            // more than 2 consonents
        else {
           if(together(s.substring(i,i+2))) {
              i+=2;
              s = s.substring(0,i)+"_"+s.substring(i);
              i = j;
           }
           else if(j>i+1 && together(s.substring(i+1,j))) {
              ++i;
              s = s.substring(0,i)+"_"+s.substring(i);
              i = j;
           }
           else {
              ++i;
              s = s.substring(0,i)+"_"+s.substring(i);
              i+=2;
              s = s.substring(0,i)+"_"+s.substring(i);
              i = (short)(j+1);
           }
        }
     }
     if(addsuff != null) return s + "/" + addsuff;
     return s;

  }
  //---------------------------------------------------------------
  public static String[] addsplits(String s1[],String ss,boolean nodups) {
     short splits,i,j,conslen;
     splitdone = new String[0];
     String s = new String(ss),s2[];
     if((i=(short)s.indexOf('@'))>0) s = s.substring(0,i);
     if(scanfor(s,"_/") >= 0) {
        s2 = allsplits(s1,s,nodups);
        splitdone = null;
        return s2;
     }
     if(syllabletot(s) < 2) {
        return addString(s1,s);
     }
     s2 = allsplits(s1,splitup(s),nodups);
     splitdone = null;
     return s2;
  }
  //--------------------------------------------------------------
  static boolean together(String s) {
     for(short i=0;i<togetherlist.length;++i) {
        if(s.equals(togetherlist[i])) return true;
     }
     return false;
  }
  //--------------------------------------------------------------
  public static short syllabletot(String s) {
     int i,j;
     short tot=0;
     if((i=s.indexOf('@')) > 0) return syllabletot(s.substring(0,i));
     if((i=s.indexOf('=')) > 0) return syllabletot(s.substring(0,i));
     s = u.strip(s,u.phonicsplit);
     s = u.strip(s,'/');
     int len=s.length();
     if(len == 1) return 1;
     for(i=(s.charAt(0)=='y')?1:0; i<len && (j=scanfor(s.substring(i),vowelsy)) >= 0;) {
        i+=j;
        if(i >= len-1) {
           if(s.charAt(i) =='e'
              && (s.length() < 4 || s.charAt(i-1) != 'l'
                   || i-2>=0 && vowelsy.indexOf(s.charAt(i-2))>=0)) {
              if (tot == 0) return(1);
              else return tot;
           }
           else return (short)(tot+1);
        }
        else {
           ++tot;
           while(++i<len && vowelsy.indexOf(s.charAt(i)) >=0);
        }
     }
     return tot;

  }
  //---------------------------------------------------------------
  public static String[] allsplits(String s1[], String s,boolean nodups) {
     int i;
     for(i=0;i<splitdone.length;++i) {
        if(s.equals(splitdone[i])) return s1;
     }
     splitdone = addString(splitdone,s);
     String[] ss = s1;
     if((i = scanfor(s,"_/")) >= 0) {
        ss = nodups?addStringSort(ss, s.substring(0,i)):addString(ss, s.substring(0,i));
        if(i < s.length()-1) {
           ss = allsplits(ss, removefromstart(s.substring(i+1)),nodups);
           s = s.substring(0,i) + s.substring(i+1);
           for(;i<s.length();++i) {
              if(vowels.indexOf(s.charAt(i))>=0
                 && !s.substring(i-1,i+1).equalsIgnoreCase("qu")) break;
              if("_/".indexOf(s.charAt(i)) >= 0) {
                  ss = nodups?addStringSort(ss, s.substring(0,i)):addString(ss, s.substring(0,i));
                  if(i == s.length()-1) break;
                  ss = allsplits(ss, removefromstart(s.substring(i+1)),nodups);
                  s = s.substring(0,i) + s.substring(i+1);
                  --i;
              }
           }
        }
     }
     else ss = nodups?addStringSort(ss, s):addString(ss,s);
     return ss;
  }
  //---------------------------------------------------------------
  public static String[] splitsingles(String s) {
     int i;
     String[] ss = new String[s.length()];
     for(i=0;i<s.length();++i)     ss[i] = s.substring(i,i+1);
     return  ss;
  }
  //---------------------------------------------------------------
  public static String[] simplesplit(String s1[], String s,boolean nodups) {
     int i;
     String[] ss = s1;
     while((i = s.indexOf('/')) >= 0) {
        ss = nodups?addStringSortCaps(ss, s.substring(0,i)):addString(ss, s.substring(0,i));
        s = s.substring(i+1);
     }
     return  nodups?addStringSortCaps(ss, s):addString(ss,s);
  }
   //---------------------------------------------------------------
  static String removefromstart(String s) {
     int i;
     for(i=0; i<s.length(); ++i) {
        if(vowels.indexOf(s.charAt(i))>=0
          && (i == 0  || !s.substring(i-1,i+1).equalsIgnoreCase("qu"))) break;
        if("_/".indexOf(s.charAt(i)) >= 0) {
           if(i == s.length()-1) return s.substring(0,i);
           s = s.substring(0,i) + s.substring(i+1);
           --i;
        }
     }
     return s;
  }
  //-------------------------------------------------------------
  public static short scanfor(String source,String oneof) {
     for(short i =0; i<source.length(); ++i) {
        if(oneof.indexOf(source.charAt(i)) >= 0) return i;
     }
     return -1;
  }
  //-------------------------------------------------------------
  public static short scanfornot(String source,String oneof) {
     for(short i =0; i<source.length(); ++i) {
        if(oneof.indexOf(source.charAt(i)) < 0) return i;
     }
     return -1;
  }
  //---------------------------------------------------------
  public static Point rotate(int x, int y, int centrex , int centrey, double angle) {
      return new Point(
        centrex + (int)((x-centrex)*Math.cos(angle)-(y-centrey)*Math.sin(angle)),
        centrey + (int)((x-centrex)*Math.sin(angle)+(y-centrey)*Math.cos(angle)));
   }
  //---------------------------------------------------------
  public Point rotate(int x, int y, int centrey , int centrex, int angle) {
      int sin = (int)(Math.sin(angle) * 1000);
      int cos = (int)(Math.cos(angle) * 1000);

      return new Point(
                     centrex + ((x-centrex)*cos-(y-centrey)*sin)/1000,
                     centrey + ((x-centrex)*sin+(y-centrey)*cos)/1000);
  }
   //---------------------------------------------------------------------
   public static void drawarm (Graphics g, int x1,int y1, int x2, int y2, int len,boolean elbowback) {
      int dy = y2-y1;
      int dx = x2-x1;
      double a1 = Math.atan2(dy,dx);
      double a2 = Math.acos(Math.sqrt((double)dx*dx+dy*dy)/len);
      if(elbowback)   a2 = -a2;
      int x3 = x1 + (int)(len/2*Math.cos(a1+a2));
      int y3 = y1 + (int)(len/2*Math.sin(a1+a2));
      int thick = len/6;
      int dx1 = (int)(len/8*Math.cos(Math.PI/2+a1+a2));
      int dy1 = (int)(len/8*Math.sin(Math.PI/2+a1+a2));
      int dx3 = (int)(len/9*Math.cos(Math.PI/2+a1));
      int dy3 = (int)(len/9*Math.sin(Math.PI/2+a1));
      int dx2 = (int)(len/10*Math.cos(Math.PI/2+a1-a2));
      int dy2 = (int)(len/10*Math.sin(Math.PI/2+a1-a2));
      g.fillPolygon(new int[] { x1-dx1,x1+dx1,x3+dx3,x2+dx2,x2-dx2,x3-dx3},
                    new int[] { y1-dy1,y1+dy1,y3+dy3,y2+dy2,y2-dy2,y3-dy3},6);
   }
   //--------------------------------------------------------------
   public static void mem() {
//startPR2004-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      JOptionPane.showMessageDialog(null,
        JOptionPane.showMessageDialog(sharkStartFrame.mainFrame,
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       "Free: "+ String.valueOf(Runtime.getRuntime().freeMemory())
                                 +"  Total: "+ String.valueOf(Runtime.getRuntime().totalMemory()),
                       "Memory",JOptionPane.INFORMATION_MESSAGE);
   }
   //--------------------------------------------------------------
   public static void okmess(String s) {
       JOptionPane.showMessageDialog(null,splitString(u.gettext(s,"message")),u.gettext(s,"heading"),JOptionPane.INFORMATION_MESSAGE);
   }

    public static void okmess(String s, String ss[], Object owner) {
      if(owner instanceof JFrame)
        JOptionPane.showMessageDialog((JFrame)owner,ss,s,JOptionPane.INFORMATION_MESSAGE);
      else
        JOptionPane.showMessageDialog((JDialog)owner,ss,s,JOptionPane.INFORMATION_MESSAGE);
    }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   //--------------------------------------------------------------
 //startPR2004-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    public static void okmess(String s, Object owner) {
      if(owner instanceof sharkStartFrame)
        JOptionPane.showMessageDialog((JFrame)owner,splitString(u.gettext(s,"message")),u.gettext(s,"heading"),JOptionPane.INFORMATION_MESSAGE);
      else
        JOptionPane.showMessageDialog((JDialog)owner,splitString(u.gettext(s,"message")),u.gettext(s,"heading"),JOptionPane.INFORMATION_MESSAGE);
    }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2007-11-30^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//   public static void okmess(String s, Object owner, Font htmlsizefont, int smallerfnt, int biggerfnt) {
//     if(owner instanceof sharkStartFrame)
//       JOptionPane.showMessageDialog((JFrame)owner,splitString(u.setHtmlFontSize(u.gettext(s,"message"),htmlsizefont,smallerfnt, biggerfnt)),u.gettext(s,"heading"),JOptionPane.INFORMATION_MESSAGE);
//     else
//       JOptionPane.showMessageDialog((JDialog)owner,splitString(u.setHtmlFontSize(u.gettext(s,"message"),htmlsizefont,smallerfnt, biggerfnt)),u.gettext(s,"heading"),JOptionPane.INFORMATION_MESSAGE);
//   }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   //--------------------------------------------------------------
   public static void okmess(String h, String s,JFrame over) {
      JOptionPane.showMessageDialog(over,splitString(s),h,JOptionPane.INFORMATION_MESSAGE);
   }
   //--------------------------------------------------------------
   public static void okmess(String h, String s) {
      JOptionPane.showMessageDialog(null,splitString(s),h,JOptionPane.INFORMATION_MESSAGE);
   }
   //--------------------------------------------------------------
//startPR2004-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    public static void okmess(String h, String s, Object owner) {
      if(owner instanceof sharkStartFrame)
        JOptionPane.showMessageDialog((JFrame)owner,splitString(s),h,JOptionPane.INFORMATION_MESSAGE);
      else
        JOptionPane.showMessageDialog((JDialog)owner,splitString(s),h,JOptionPane.INFORMATION_MESSAGE);
    }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   /**
    * Displays and gets the input from a dialogue box. Does not use u.getext().
    * @param h Title for the dialogue
    * @param s The string to be displayed
    * @return Boolean indicating whether the answer is yes or no
    */
   public static boolean yesnomess(String h, String s) {
      int result = JOptionPane.showConfirmDialog(null,splitString(s),h,
             JOptionPane.YES_NO_OPTION,
             JOptionPane.QUESTION_MESSAGE);
      return (result ==JOptionPane.YES_OPTION);
   }
   //--------------------------------------------------------------
 //startPR2004-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    public static boolean yesnomess(String h, String s, Object owner) {
      int result;
      if(owner instanceof sharkStartFrame){
        result = JOptionPane.showConfirmDialog((JFrame)owner,
                                               splitString(s), h,
                                               JOptionPane.YES_NO_OPTION,
                                               JOptionPane.QUESTION_MESSAGE);
      }
      else if(owner instanceof JFrame){
        result = JOptionPane.showConfirmDialog((JFrame)owner,
                                               splitString(s), h,
                                               JOptionPane.YES_NO_OPTION,
                                               JOptionPane.QUESTION_MESSAGE);
      }
      else{
        result = JOptionPane.showConfirmDialog((JDialog)owner,
                                               splitString(s), h,
                                               JOptionPane.YES_NO_OPTION,
                                               JOptionPane.QUESTION_MESSAGE);

      }
       return (result ==JOptionPane.YES_OPTION);
    }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   //--------------------------------------------------------------
   public static boolean yesnomess(String s) {
      int result = JOptionPane.showConfirmDialog(null,
             splitString(gettext(s,"message")),gettext(s,"heading"),
             JOptionPane.YES_NO_OPTION,
             JOptionPane.QUESTION_MESSAGE);
      return (result ==JOptionPane.YES_OPTION);
   }
   //--------------------------------------------------------------
 //startPR2004-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    public static boolean yesnomess(String s, Object owner) {
       int result;
       if(owner instanceof sharkStartFrame){
         result = JOptionPane.showConfirmDialog((JFrame)owner,
                                                splitString(gettext(s, "message")),
                                                gettext(s, "heading"),
                                                JOptionPane.YES_NO_OPTION,
                                                JOptionPane.QUESTION_MESSAGE);
       }
       else{
         result = JOptionPane.showConfirmDialog((JDialog)owner,
                                                splitString(gettext(s, "message")),
                                                gettext(s, "heading"),
                                                JOptionPane.YES_NO_OPTION,
                                                JOptionPane.QUESTION_MESSAGE);

       }
       return (result ==JOptionPane.YES_OPTION);
    }

//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   //--------------------------------------------------------------
   public static int yesnocancel(String h, String s) {
      int result = JOptionPane.showConfirmDialog(null,splitString(s),h,
             JOptionPane.YES_NO_CANCEL_OPTION,
             JOptionPane.QUESTION_MESSAGE);
      if(result == JOptionPane.NO_OPTION) return 1;
      if(result == JOptionPane.YES_OPTION) return 0;
      return -1;
   }
   //--------------------------------------------------------------
//startPR2004-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    public static int yesnocancel(String h, String s, Object owner) {
      int result;
      if(owner instanceof sharkStartFrame){
        result = JOptionPane.showConfirmDialog((JFrame)owner,
                                                   splitString(s), h,
                                                   JOptionPane.YES_NO_CANCEL_OPTION,
                                                   JOptionPane.QUESTION_MESSAGE);
      }
      else{
        result = JOptionPane.showConfirmDialog((JDialog)owner,
                                                   splitString(s), h,
                                                   JOptionPane.YES_NO_CANCEL_OPTION,
                                                   JOptionPane.QUESTION_MESSAGE);
      }
       if(result == JOptionPane.NO_OPTION) return 1;
       if(result == JOptionPane.YES_OPTION) return 0;
       return -1;
    }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    public static int okcancel(String h, String s, Object owner) {
      int result;
      if(owner instanceof JFrame){
        result = JOptionPane.showConfirmDialog((JFrame)owner,
                                                   splitString(s), h,
                                                   JOptionPane.OK_CANCEL_OPTION,
                                                   JOptionPane.QUESTION_MESSAGE);
      }
      else{
        result = JOptionPane.showConfirmDialog((JDialog)owner,
                                                   splitString(s), h,
                                                   JOptionPane.OK_CANCEL_OPTION,
                                                   JOptionPane.QUESTION_MESSAGE);
      }
       return result;
    }    
   //--------------------------------------------------------------
   public static int choose(String h, String s, String[] vals) {
     JOptionPane get = new JOptionPane(
               splitString(s),
               JOptionPane.QUESTION_MESSAGE,
               0,
               null,vals,vals[0]);
     JDialog dialog = get.createDialog(null,h);
//startPR2009-07-27^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//     if(shark.macOS && (get.getRootPane().getDefaultButton()!=null))
//       get.getRootPane().getDefaultButton().requestFocus();
     JButton jb;
     if(shark.macOS && (jb=get.getRootPane().getDefaultButton())!=null)
       jb.requestFocus();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     dialog.setVisible(true);
     return findString(vals,(String)get.getValue());
   }
   //--------------------------------------------------------------
//startPR2004-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     public static int choose(String h, String s, String[] vals, Object owner) {
       if(owner instanceof sharkStartFrame){
         JOptionPane get = new JOptionPane(
             splitString(s),
             JOptionPane.QUESTION_MESSAGE,0,null, vals, vals[0]);
         JDialog dialog = get.createDialog((JFrame)owner, h);
//startPR2009-07-27^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//         if(shark.macOS && (get.getRootPane().getDefaultButton()!=null))
//           get.getRootPane().getDefaultButton().requestFocus();
         JButton jb;
         if(shark.macOS && (jb=get.getRootPane().getDefaultButton())!=null)
           jb.requestFocus();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         dialog.setVisible(true);
//startPR2006-12-05^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         dialog.dispose();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         return findString(vals, (String) get.getValue());
       }
       else{
         JOptionPane get = new JOptionPane(
              splitString(s),
              JOptionPane.QUESTION_MESSAGE, 0, null, vals, vals[0]);
          JDialog dialog = get.createDialog((JDialog)owner, h);
//startPR2009-07-27^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//          if(shark.macOS && (get.getRootPane().getDefaultButton()!=null))
//            get.getRootPane().getDefaultButton().requestFocus();
           JButton jb;
           if(shark.macOS && (jb=get.getRootPane().getDefaultButton())!=null)
             jb.requestFocus();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          dialog.setVisible(true);
//startPR2006-12-05^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
           dialog.dispose();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          return findString(vals, (String) get.getValue());
       }
     }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   //--------------------------------------------------------------
   public static String[] randword(int tot, int minlen,int maxlen) {
      String s[] = new String[tot],ws[];
      short o[];
      int i,j,upto,repeats=0,got=0;
      while (got < tot && ++repeats < 1000) {
          i = u.rand(lowercase.length());
          ws = db.list(sharkStartFrame.getPrimarySoundDb(sharkStartFrame.publicSoundLib),db.WAV,lowercase.substring(i,i+1));
          upto = Math.min(tot,got + tot/(2+(u.rand(3))));
          o = u.shuffle(u.select((short)ws.length,(short)ws.length));
          thisloop: for(i=0;got<upto && i<o.length;++i)  {
             if(ws[o[i]].length() >= minlen
                    && ws[o[i]].length() <= maxlen
                    && onlyletters(ws[o[i]]) ) {
                for (j = 0; j<got; ++j)
                    if (s[j].equals(ws[o[i]])) continue thisloop;
                s[got++] = ws[o[i]];
             }
          }
      }
      if(got<tot) {
         String s2[] = new String[got];
         System.arraycopy(s,0,s2,0,got);
         return s2;
      }
      return s;
   }
   //-----------------------------------------------------------------
   public static boolean onlyletters(String s) {
      for(short i=0;i<s.length();++i) {
         if(letters.indexOf(s.charAt(i)) < 0) return false;
      }
      return true;
   }
   //-----------------------------------------------------------------
   public static boolean onlylettersnumbers(String s) {
      for(short i=0;i<s.length();++i) {
         if(lettersnumbers.indexOf(s.charAt(i)) < 0) return false;
      }
      return true;
   }
   //-----------------------------------------------------------------
   public static boolean onlylower(String s) {
      for(short i=0;i<s.length();++i) {
         if(lowercase.indexOf(s.charAt(i)) < 0) return false;
      }
      return true;
   }

   public static String[] filterIrregularWords(String s[]) {
     for(int i = s.length-1; i >= 0; i--){
        // Algorithm for 3 letter words – all three letter words that have a
        // CVC (consonant-vowel-consonant) pattern  BUT exclude those that
        //  END with the letters r,  w  or  y.         
         if(s[i].length()==3){
             if(!("RrWwYy".indexOf(s[i].charAt(2))<0 &&
                     "Ww".indexOf(s[i].charAt(0))<0 &&
                     (vowels.indexOf(s[i].charAt(0))<0 && letters.indexOf(s[i].charAt(0))>=0) &&
                     vowels.indexOf(s[i].charAt(1))>=0 &&
                     (vowels.indexOf(s[i].charAt(2))<0 && letters.indexOf(s[i].charAt(2))>=0))){   
                 s = u.removeString2(s, s[i]);   
             }
         }
     }
     return s;
   }
   
    
    public static ArrayList restrictedUserCount(File sharedPath) {
        return restrictedUserCount(sharedPath, null, false, false);
    }
    
    // 0 is admin count
    // 1 is student count
    // 2 is alphabetically first admin
   public static ArrayList restrictedUserCount(File sharedPath, File sharedPath2, boolean cleanall, boolean overwrite2) {
       ArrayList al = null;
       if(shark.licenceLimitsUsers()){
            String ss[] = db.dblistnames(sharedPath);
            String ss2[] = null;
            if(sharedPath2 != null){
                ss2 = db.dblistnames(sharedPath2);
            }
            int kadmin = 0;
            int kstu = 0;
            String alphafirstadmin =null;
            for(int i = 0; i < ss.length; i++){
                int ADMIN = 0;
                int STUDENT = 1;
                int copytype2 = -1;
                if(ss2!=null && u.findString(ss2, ss[i]) >=0){
                    Object oo2;
                    oo2 = db.find(sharedPath2+shark.sep+ss[i], "student", db.STUDENT);    
                    if(oo2 != null && oo2 instanceof savestudent) {
                        savestudent sst2 = ((savestudent)oo2); 
                        copytype2 = sst2.administrator?ADMIN:STUDENT;
                    }
                }
                Object oo;
                oo = db.find(sharedPath+shark.sep+ss[i], "student", db.STUDENT);
                if(oo != null && oo instanceof savestudent) {
                  savestudent sst = ((savestudent)oo);  
                    if(sst.administrator) {
                        if(alphafirstadmin == null || ss[i].compareTo(alphafirstadmin)<0)
                           alphafirstadmin = ss[i];
                        if(!cleanall && ss2!=null){
                            if(overwrite2)kadmin++;
                            else if(copytype2 == ADMIN){
                                kadmin++;
                            }
                        }
                        else{
                            kadmin++;
                        }  
                    }
                    else {
                        if(!cleanall && ss2!=null){
                            if(overwrite2)kstu++;
                            else if(copytype2 == STUDENT){
                                kstu++;
                            }
                        }
                        else{
                            kstu++;
                        }                           
                    }                   
                }
            }
            al = new ArrayList();
            al.add(kadmin);
            al.add(kstu);
            al.add(alphafirstadmin);
       }
        return al;
  }     

   //---------------------------------------------------------------------------
   public static void rough(Graphics g, int x1,int y1, int x2, int y2, Color color)   {
      int avx = (x1+x2)/2, width = Math.max(4,x2-x1), avy = (y1+y2)/2, depth = y2-y1, y;
      if(depth<2 || width<2) return;
      int xs = (avx - width*3/16 + rand(width/2)) * 16;
      int xe = xs +  rand(width*4);
      int inc = width*16/depth;
      g.setColor(color);

      avx *= 16; x1 *= 16; x2 *= 16;
      for(y=y1;y<y2;++y) {
         if(y==avy) inc = -inc;
         xs -= inc - 64 + u.rand(129);
         xs = Math.max(x1,Math.min(avx,xs));
         xe += inc - 64 + u.rand(129);
         xe = Math.min(x2,Math.max(xs+16,xe));
         g.drawLine(xs/16,y,xe/16-1,y);
      }
   }
   public static int LCM(int n1, int n2) {
      int i = Math.max(n1,n2);
      if(n2 == 0 || n1 ==0 ) return Math.max(1,i);
      for(; i < n1*n2; ++i) {
         if(i%n1 == 0 && i%n2 == 0) return i;
      }
      return i;
   }
   public static boolean query(String[] list,String s) {
      String namef = s.toLowerCase();
      return ( java.util.Arrays.binarySearch(list,namef,comparator2) >= 0);
   }
   public static void diclist() {
     if (diclist == null) {
       String[] ss = spellchange.spellchange(db.list(sharkStartFrame.getPrimarySoundDb(sharkStartFrame.publicSoundLib), db.WAV));
       int dictot = 0, i;
       for (i = 0; i < ss.length; ++i) {
         if (ss[i].length() > 1 && u.onlylower(ss[i])) {
           ss[dictot++] = ss[i];
         }
       }
       diclist = new String[dictot];
       System.arraycopy(ss, 0, diclist, 0, dictot);
     }
   }
   public static String[] embedded(String in[]) {
      diclist();
      int i,j;
      String lookin[] = new String[in.length], ret[] = new String[0], c;
      for(i=0;i<in.length;++i)  {lookin[i] = in[i].toLowerCase();}
      int m;
      for(i=0;i<diclist.length;++i) {
        for (m = 0; m < lookin.length; ++m) {
          if (diclist[i].length() < lookin[m].length()
              &&  (j=lookin[m].indexOf(diclist[i])) >= 0
              &&  (j>0 || !lookin[m].equals(diclist[i]+"s"))) {
            ret = addString(ret, diclist[i]);
            break;
          }
        }
      }
      return ret;
   }
   public static String[] canbemade(String in[]) {  // can be made from letters  - including embedded words
      diclist();
      int i,j,k,m,len[] = new int[in.length];
      String lookin[] = new String[in.length], ret[] = new String[0], c;
      for(i=0;i<in.length;++i)  {lookin[i] = in[i].toLowerCase();len[i]=lookin[i].length();}
      loop1: for(i=0;i<diclist.length;++i) {
        if(u.findString(lookin, diclist[i].toLowerCase()) >= 0) continue;
         loop2:for (m = 0; m < lookin.length; ++m) {
          if (diclist[i].length() <= len[m] && !diclist[i].equals(lookin[m])
                 && !(diclist[i]+"s").equals(lookin[m])
                 &&     (k = lookin[m].indexOf(diclist[i].charAt(0))) >= 0) {
            c = lookin[m].substring(0, k) + lookin[m].substring(k + 1);
            for (j = 1; j < diclist[i].length(); ++j) {
              if ( (k = c.indexOf(diclist[i].charAt(j))) >= 0) {
                c = c.substring(0, k) + c.substring(k + 1);
              }
              else
                continue loop2;  // try next word in given list
            }
            ret = addString(ret, diclist[i]);
            continue loop1;  // go to next dictionary word
          }
        }
      }
      return ret;
   }
   public static boolean phonicscourse(String t) {    // see if path gives phonics course
      int i = t.indexOf(topicTree.CSEPARATOR);
      return i>0 && findString(phonicscourses,t.substring(0,i)) >= 0;
   }
   public static boolean phonicscourse(jnode jj) {    // see if node in phonics course
      jnode pa = jj;
      while((pa = (jnode)pa.getParent()) != null) {
         if(findString(phonicscourses,pa.get()) >= 0) return true;
      }
      return false;
   }
   public static  JScrollPane uScrollPane(Component tree) {
      return new uuScrollPane(tree);
   }
   static class uuScrollPane extends JScrollPane {
        Component jtree;
        uuScrollPane(Component tree) {
            super(tree);
            jtree = tree;
            getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
              public void adjustmentValueChanged(AdjustmentEvent e) {
                  jtree.repaint();
              }
            });
//startPR2008-06-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            getHorizontalScrollBar().addAdjustmentListener(new AdjustmentListener() {
              public void adjustmentValueChanged(AdjustmentEvent e) {
                jtree.repaint();
              }
            });
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        }
   }
   public static abstract class editnumber  extends  JPanel {
     JSpinner  jsp;
     SpinnerNumberModel mod;
     editnumber(String lab1,int min1,int max1,int curr) {
        this.setBorder(BorderFactory.createEtchedBorder());
        setLayout(new BorderLayout());
        add( new mlabel_base(lab1),BorderLayout.NORTH) ;
        jsp = new JSpinner(mod = new SpinnerNumberModel(curr,min1,max1,1));
        jsp.addChangeListener(new ChangeListener() {
           public void stateChanged(ChangeEvent e) {
             vsignal(mod.getNumber().intValue());
           }
         }
        );
        add(jsp, BorderLayout.CENTER);
     }
     void endedit() {
       vsignal(mod.getNumber().intValue());
     }
     int getvalue() {
         return mod.getNumber().intValue();
     }
     void setvalue(int v) {
         jsp.setValue(new Integer(v));
    }
     public abstract void vsignal(int value);
   }
   public static ProgressMonitor startmonitor(String name,String text) {
      short time;
      endmonitor();
      pmname = name;
      pm = new ProgressMonitor(sharkStartFrame.mainFrame,text,"Please wait",0,1000);
      pmstart = System.currentTimeMillis();
      if((pmtime = student.optionval(name)*100) <= 0) pmtime = 5000;
      pm.setMillisToPopup(200);
      return pm;
   }
   public static void monitor() {
      if(pm != null)  {
         if (pm.isCanceled()) {pm.close();pmname=null;return;}
         else {
                long time =  System.currentTimeMillis()-pmstart;
                if(time > pmtime*19/20) pm.setProgress(950);
                else                    pm.setProgress((int)(time*1000/pmtime));
         }
      }
   }
   public static void endmonitor() {
      student.setOption(pmname,(short)((System.currentTimeMillis()-pmstart)/100));
      if(pm != null) {
         pm.close();
         pm = null;
         pmname = null;
      }
   }
   //-------------- paint with options

  public static String striphighlights(String value) {
     if(value.indexOf('[') >=0) {
        String s = new String(value);
        int i;
        while((i = s.indexOf('[')) >= 0 || (i = s.indexOf(']')) >= 0)
            s = s.substring(0,i) + s.substring(i+1);
        return s;
     }
     return value;
  }
   //-------------- paint with options

  public static void painthighlight(String value,Graphics g,int x, int y) {
      if(shark.macOS) ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    char s[] = value.toCharArray();
    Color  saveColor = g.getColor();
    FontMetrics m = g.getFontMetrics();
    int i;
    y += m.getMaxAscent();
    for(i=0;i<s.length;++i)  {
       if(s[i] == '[') {
          g.setColor(Color.red);
          continue;
       }
       if(s[i] == ']') {
          g.setColor(saveColor);
          continue;
       }
        g.drawChars(s,i,1,x,y);
        x += m.charWidth(s[i]);
     }
     g.setColor(saveColor);
  }
  //------------------------------------------------------------
  public static  int getint(String ss) {
     int i,j,k,num=0;
     for(i=0;i<ss.length() && ss.charAt(i)==' ';++i);
     for(j=i;j<ss.length() && (k=numbers.indexOf(ss.charAt(j))) >= 0;++j)
        num = num*10 + k;
     return num;
  }
  //---------------------------------------------------------------
  public static JLabel label(String key) {
     JLabel jb = new JLabel(gettext(key,"label"));
     String s = gettext(key,"tooltip");
     if(s != null) {
         jb.setToolTipText(s);
         jb.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent me) {
                tooltip_base.on((JComponent)me.getComponent(), me);
            }
            public void mouseExited(MouseEvent me) {
                tooltip_base.off();
            }
         });
     }
     return jb;
  }
  //---------------------------------------------------------------
  public static mlabel_base mlabel(String key) {
     mlabel_base jb = new mlabel_base(gettext(key,"label"));
     String s = gettext(key,"tooltip");
     if(s != null) {
 //startPR2004-08-28^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        if (shark.macOS) {
          s = formatMacMenu(s);
//          s = formatCommandSymbol(s);
        }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       jb.setToolTipText(s);
         jb.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent me) {
                tooltip_base.on((JComponent)me.getComponent(), me);
            }
            public void mouseExited(MouseEvent me) {
                tooltip_base.off();
            }
         });
     }
     return jb;
  }
  //---------------------------------------------------------------
  public static JButton Button(String key) {
     JButton jb = new JButton(gettext(key,"label"));
     String s = gettext(key,"tooltip");
     if(s != null) {
         jb.setToolTipText(s);
         jb.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent me) {
                tooltip_base.on((JComponent)me.getComponent(), me);
            }
            public void mouseExited(MouseEvent me) {
                tooltip_base.off();
            }
         });
     }
     jb.setFocusable(false);
     return jb;
  }
  //---------------------------------------------------------------
  public static mbutton mbutton(String key) {
     mbutton jb = new mbutton(gettext(key,"label"));
     String s = gettext(key,"tooltip");
     if(s != null) {
         jb.setToolTipText(s);
         jb.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent me) {
                tooltip_base.on((JComponent)me.getComponent(), me);
            }
            public void mouseExited(MouseEvent me) {
                tooltip_base.off();
            }
         });
     }
     jb.setFocusable(false);
     return jb;
  }
  //---------------------------------------------------------------
  public static JCheckBox CheckBox(String key,ItemListener li) {
     JCheckBox jb = new JCheckBox(gettext(key,"label"));
     String s = gettext(key,"tooltip");
     if(s != null) {
         jb.setToolTipText(s);
         jb.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent me) {
                tooltip_base.on((JComponent)me.getComponent(), me);
            }
            public void mouseExited(MouseEvent me) {
                tooltip_base.off();
            }
         });
     }
     jb.addItemListener(li);
     jb.setFocusable(false);
     return jb;
  }
  //---------------------------------------------------------------
  public static JCheckBox CheckBox(String key) {
     JCheckBox jb = new JCheckBox(gettext(key,"label"));
     String s = gettext(key,"tooltip");
     if(s != null) {
         jb.setToolTipText(s);
         jb.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent me) {
                tooltip_base.on((JComponent)me.getComponent(), me);
            }
            public void mouseExited(MouseEvent me) {
                tooltip_base.off();
            }
         });     
     }
     return jb;
  }

  public static JRadioButton RadioButton(String key) {
     String s_text =  gettext(key,"label");
     if(s_text.indexOf('|')>=0)
         s_text = u.convertToHtml(s_text);
     JRadioButton jb = new JRadioButton(s_text);
     String s = gettext(key,"tooltip");
     if(s != null) jb.setToolTipText(s);
     jb.setOpaque(false);
     jb.setFocusable(false);
     return jb;
  }
  //---------------------------------------------------------------
  public static JRadioButton RadioButton(String key,Object li) {
     String s_text =  gettext(key,"label");
     if(s_text.indexOf('|')>=0)
         s_text = u.convertToHtml(s_text);
     JRadioButton jb = new JRadioButton(s_text);
     String s = gettext(key,"tooltip");
     if(s != null) jb.setToolTipText(s);
     if(li instanceof ItemListener) jb.addItemListener((ItemListener)li);
     else if(li instanceof MouseListener) jb.addMouseListener((MouseListener)li);
     jb.setOpaque(false);
     jb.setFocusable(false);
     return jb;
  }
  //---------------------------------------------------------------
  public static JMenuItem MenuItem(String key) {
     myJMenuItem jb = new myJMenuItem(gettext(key,"label"));
//     if(!shark.macOS){
//         jb.setMinimumSize(new Dimension((int)jb.getPreferredSize().getWidth()+menuItemWidth, menuItemHeight));
//         jb.setPreferredSize(new Dimension((int)jb.getPreferredSize().getWidth()+menuItemWidth, menuItemHeight));
//      }
     String s = gettext(key,"tooltip");
     if(s != null) {
//startPR2004-08-28^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        if (shark.macOS){
          s = formatMacMenu(s);
//          s = formatCommandSymbol(s);
        }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       jb.setToolTipText(s);
       jb.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent me) {
                tooltip_base.on((JComponent)me.getComponent(), me);
            }
            public void mouseExited(MouseEvent me) {
                tooltip_base.off();
            }
        });
     }
     return jb;
  }
  
  
  public static class myJMenuItem extends JMenuItem {
      
      public myJMenuItem(String s){
          super(s);
      }
      public Dimension getPreferredSize() {
         if(!shark.macOS){
            return new Dimension((int)super.getPreferredSize().getWidth()+u2_base.menuItemWidth, u2_base.menuItemHeight);
        }
         else return super.getPreferredSize();
      }
  }  
  //---------------------------------------------------------------

  public static JMenuItem MenuItem(String key,KeyStroke accel) {
      return MenuItem(key,accel, false);
  }

  public static JMenuItem MenuItem(String key,KeyStroke accel, boolean mainBar) {
     myJMenuItem jb = new myJMenuItem(gettext(key,"label"));
//     if(!shark.macOS){
//         jb.setMinimumSize(new Dimension((int)jb.getPreferredSize().getWidth()+menuItemWidth, menuItemHeight));
//         jb.setPreferredSize(new Dimension((int)jb.getPreferredSize().getWidth()+menuItemWidth, menuItemHeight));
//      }
     jb.setAccelerator(accel);
     String s = gettext(key,"tooltip");
     if(s != null) {
//startPR2004-08-28^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         if (shark.macOS){
           s = formatMacMenu(s);
//           s = formatCommandSymbol(s);
         }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       jb.setToolTipText(s);
       jb.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent me) {
                tooltip_base.on((JComponent)me.getComponent(), me);
            }
            public void mouseExited(MouseEvent me) {
                tooltip_base.off();
            }
        });
     }
     return jb;
  }
  //---------------------------------------------------------------
  public static JMenu Menu(String key) {
      return Menu(key, null, false);
  }

  public static JMenu Menu(String key, boolean mainBar) {
      return Menu(key, null, mainBar);
  }
  
  public static JMenu Menu(String key, String editString, boolean mainBar) {
      String str;
      if(editString!=null)str = key.replaceFirst("%", editString);
      else str = gettext(key,"label");
      myJMenu jb = new myJMenu(str, mainBar);
  //   if(!mainBar){
 //    if(!shark.macOS){
 //        jb.setMinimumSize(new Dimension((int)jb.getPreferredSize().getWidth()+(mainBar?0:menuItemWidth), menuItemHeight));
 //        jb.setPreferredSize(new Dimension((int)jb.getPreferredSize().getWidth()+(mainBar?0:menuItemWidth), menuItemHeight));
 //     }
 //   }
     String s = gettext(key,"tooltip");
     if(s != null) {
//startPR2004-08-28^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         if (shark.macOS){
           s = formatMacMenu(s);
//           s = formatCommandSymbol(s);
         }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       jb.setToolTipText(s);
     }     
     if(mainBar){
       jb.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {
                JMenu thismenu = ((JMenu)me.getSource());
                if(thismenu.getToolTipText()!=null)
                    tooltip_base.on((JComponent)me.getComponent(), me);
            }
            public void mouseEntered(MouseEvent me) {
                JMenu thismenu = ((JMenu)me.getSource());
                if(thismenu.getToolTipText()!=null)
                    tooltip_base.on((JComponent)me.getComponent(), me);
            }
            public void mouseExited(MouseEvent me) {
                JMenu thismenu = ((JMenu)me.getSource());
                if(thismenu.getToolTipText()!=null)
                    tooltip_base.off();
            }
        });
     }
     else{
       jb.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent me) {
                JMenu thismenu = ((JMenu)me.getSource());
                if(thismenu.getToolTipText()!=null)
                    tooltip_base.on((JComponent)me.getComponent(), me);

            }
            public void mouseExited(MouseEvent me) {
                JMenu thismenu = ((JMenu)me.getSource());
                if(thismenu.getToolTipText()!=null)
                    tooltip_base.off();
            }
        });
     }
     return jb;
  }

  public static class myJMenu extends JMenu {
      boolean mainBar;
      
      public myJMenu(String s, boolean mainbar){
          super(s);
          mainBar = mainbar;
      }
      public Dimension getPreferredSize() {
         if(!shark.macOS){
            return new Dimension((int)super.getPreferredSize().getWidth()+(mainBar?0:u2_base.menuItemWidth), u2_base.menuItemHeight);
        }
         else return super.getPreferredSize();
      }
  }
  
  public static class my3wayCheckBox extends JCheckBox {
        MenuElement mm[];
        int state;
        public my3wayCheckBox(String s,String t) {
          super(s);
          this.setOpaque(false);
          if(t != null) {
              setToolTipText(t);
          }
          addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent me) {
                tooltip_base.on((JComponent)me.getComponent(), me);
            }
            public void mouseExited(MouseEvent me) {
                tooltip_base.off();
            }
              public void mousePressed(MouseEvent e) {
                if(mm==null) {
                  mm = MenuSelectionManager.defaultManager().getSelectedPath();
                }
             }
          });
          addActionListener(new java.awt.event.ActionListener() {
             public void actionPerformed(ActionEvent e) {
               if(mm==null || mm.length == 0) return;
                if(mm.length>3) {
                    JPopupMenu jp = ((JPopupMenu)getParent());
                    if(jp != null) {
                      jp.setVisible(false);
                      jp.setVisible(true);
                      MenuSelectionManager.defaultManager().setSelectedPath(mm);
                      jp.setVisible(false);
                      jp.setVisible(true);
                    }
                 }
                 MenuSelectionManager.defaultManager().setSelectedPath(mm);
             }
         });
        }
        public void setState(int st) {
           setIcon(icons[state = st]);
        }
   }


  public static my3wayCheckBox  get3wayCheckBox(String s) {
    return new my3wayCheckBox(u.gettext(s,"label"),u.gettext(s,"tooltip"));
  }
  public static my3wayCheckBox  get3wayCheckBox2(String s) {
    return new my3wayCheckBox(s,null);
  }
  //-----------------------------------------------------------------------
  public static jumpToSlider  jumpToSlider(int orientation, int min, int max, int value, int baseval, int rangeInPercent) {
    return new jumpToSlider(orientation, min,  max, value, baseval, rangeInPercent);
  }
  public static class jumpToSlider extends JSlider {
      int defaultno;
      int percentrange;
      boolean block = false;
      int range;

        public jumpToSlider(int orientation, int min, int max, int value, int baseval, int rangeInPercent) {
          super(orientation, min, max, value);
          range = max - min;
          defaultno = baseval;
          percentrange = rangeInPercent;
             addChangeListener(new ChangeListener() {
                public void stateChanged(ChangeEvent e) {
                    if(block)return;
                    if( getValueIsAdjusting())return;
                    JSlider js = (JSlider)e.getSource();
                    float val = js.getValue();
                    float d = (((float)percentrange/(float)100*(float)range)/2);
                    if(val > defaultno-d && val < defaultno+d){
                        block =true;
                        js.setValue(defaultno);
                        block =false;
                    }
                }
          });
      }
   }

  public static my3wayCheckBoxMenuItem  get3wayCheckBoxMenuItem(String s) {
    return new my3wayCheckBoxMenuItem(u.gettext(s,"label"),u.gettext(s,"tooltip"));
  }
  public static my3wayCheckBoxMenuItem  get3wayCheckBoxMenuItem2(String s) {
    return new my3wayCheckBoxMenuItem(s,null);
  }
  //-----------------------------------------------------------------------
  public static class my3wayCheckBoxMenuItem extends JMenuItem {
        MenuElement mm[];
        int state;
        public my3wayCheckBoxMenuItem(String s,String t) {
          super(s);
          if(!shark.macOS){
     this.setMinimumSize(new Dimension((int)this.getPreferredSize().getWidth()+u2_base.menuItemWidth, u2_base.menuItemHeight));
     this.setPreferredSize(new Dimension((int)this.getPreferredSize().getWidth()+u2_base.menuItemWidth, u2_base.menuItemHeight));
            }
          if(t != null) {
              setToolTipText(t);
          }
          addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent me) {
                tooltip_base.on((JComponent)me.getComponent(), me);
            }
            public void mouseExited(MouseEvent me) {
                tooltip_base.off();
            }
              public void mousePressed(MouseEvent e) {
                if(mm==null) {
                  mm = MenuSelectionManager.defaultManager().getSelectedPath();
                }
             }
          });
          addActionListener(new java.awt.event.ActionListener() {
             public void actionPerformed(ActionEvent e) {
               if(mm==null || mm.length == 0) return;
                if(mm.length>3) {
                    JPopupMenu jp = ((JPopupMenu)getParent());
                    if(jp != null) {
                      jp.setVisible(false);
                      jp.setVisible(true);
                      MenuSelectionManager.defaultManager().setSelectedPath(mm);
                      jp.setVisible(false);
                      jp.setVisible(true);
                    }
                 }
                 MenuSelectionManager.defaultManager().setSelectedPath(mm);
             }
         });
        }
        public void setState(int st) {
           setIcon(icons[state = st]);
        }
   }
  //---------------------------------------------------------------

  public static JCheckBoxMenuItem CheckBoxMenuItem(String key) {
      return CheckBoxMenuItem_Common(key, false, false);

  }
  public static JCheckBoxMenuItem CheckBoxMenuItem(String key, boolean wantIconSpacer) {
    return CheckBoxMenuItem_Common(key, wantIconSpacer, false);
  }

  public static JCheckBoxMenuItem CheckBoxMenuItem(String key, boolean wantIconSpacer, boolean wantDefaultHeight) {
    return CheckBoxMenuItem_Common(key, wantIconSpacer, wantDefaultHeight);
  }
  
  public static JCheckBoxMenuItem CheckBoxMenuItem_Common(String key, boolean wantIconSpacer, boolean wantDefaultHeight) {
     myCheckBoxMenuItem jb = new myCheckBoxMenuItem(gettext(key,"label"), wantIconSpacer, wantDefaultHeight);

     String s = gettext(key,"tooltip");
     if(s != null) {
 //startPR2004-08-28^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         if (shark.macOS){
           s = formatMacMenu(s);
//           s = formatCommandSymbol(s);
         }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       jb.setToolTipText(s);
       jb.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent me) {
                tooltip_base.on((JComponent)me.getComponent(), me);
            }
            public void mouseExited(MouseEvent me) {
                tooltip_base.off();
            }
        });
     }
     return jb;
  }


  //---------------------------------------------------------------
  public static JCheckBoxMenuItem CheckBoxMenuItem2(String key) {
     myCheckBoxMenuItem jb = new myCheckBoxMenuItem(key);
     return jb;
  }



  public static JButton sharkButton() {
     JButton jb = new JButton();
       jb.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent me) {
                tooltip_base.on((JComponent)me.getComponent(), me);
            }
            public void mouseExited(MouseEvent me) {
                tooltip_base.off();
            }
            public void mousePressed(MouseEvent e) {
                tooltip_base.off();
            }
        });
     return jb;
  }

  public static JLabel infoLabel(Window owner, String s) {
      return infoLabel(owner, s, false, null);
  }  
  
  public static JLabel infoLabel(String s) {
      return infoLabel(null, s, false, null);
  }

  public static JLabel infoLabel(String s, Color bgcolor) {
      return infoLabel(null, s, false, bgcolor);
  }

  public static JLabel infoLabel(String s, boolean left) {
      return infoLabel(null, s, left, null);
  }
  
  public static JLabel infoLabel(Window owner, String s, boolean left) {
      return infoLabel(owner, s, left, null);
  }

  public static JLabel infoLabel(Window owner, String s, boolean left, Color bgcolor) {
     int sw = sharkStartFrame.mainFrame.getWidth();
     int buttondim = (sw*14/22)/24;
     int buttonimdim =  buttondim- (buttondim/3);
     Image im = Toolkit.getDefaultToolkit().getImage(sharkStartFrame.publicPathplus + "sprites" +
                                       sharkStartFrame.separator +
                                       "infoCIRCLE_il48.png");
     ImageIcon iigame = new ImageIcon(im.getScaledInstance(buttonimdim,
            buttonimdim, Image.SCALE_SMOOTH));
     JLabel jb = new JLabel();
     jb.setIcon(iigame);
     jb.setToolTipText(s);
     u.forcenotes(owner, jb,left,false);
     if(bgcolor!=null){
         jb.setBackground(bgcolor);
         jb.setOpaque(true);
     }
     jb.addMouseListener(new MouseAdapter() {
          public void mouseEntered(MouseEvent me) {
              
                tooltip_base.on((JComponent)me.getComponent(), me);
          }
          public void mouseExited(MouseEvent me) {
              tooltip_base.off();
          }
      });
     return jb;
  }

  public static JButton sharkButton(String key) {
     JButton jb = new JButton(gettext(key, "label"));
     jb.setToolTipText(gettext(key, "tooltip"));
       jb.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent me) {
                tooltip_base.on((JComponent)me.getComponent(), me);
            }
            public void mouseExited(MouseEvent me) {
                tooltip_base.off();
            }
        });
     return jb;
  }
  //-----------------------------------------------------------------------
//startPR2004-08-27^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  /**
   * Formats strings as follow: '|' characters are replaced by a space and '^'
   * character are ignored. This formatting is needed when the Macintosh menu
   * bar is being used.
   *
   * @param  the string which the unwanted characters are removed from.
   * @return  the formatted string.
   */
  private static String formatMacMenu(String s){
      if (s != null) {
        char c;
        String str = "";
        for (int i = 0; i < s.length(); i++) {
          c = s.charAt(i);
          if (c == '|')
            str = str.concat(" ");
          else if (c != '^')
            str = str.concat(String.valueOf(c));
        }
        s = str;
        return s;
      }
      else
        return null;
  }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  
  public static String formatTextforUpload(String s, int uploadMode){
      if (s != null) {
        char c;
        String str = "";
        if(uploadMode == MYSQLUpload.MODE_VIA_DUMP){
            for (int i = 0; i < s.length(); i++) {
              c = s.charAt(i);
              if (c == '\'')
                str = str.concat("**1**");
              else if (c == '"'){
                str = str.concat("**4**");
              }
              else if (c == '+'){
                str = str.concat("**3**");
              }
              else if (c == '&'){
                str = str.concat("**2**");
              }
              else 
                str = str.concat(String.valueOf(c));
            }
        }
        else{
            str = s;
        }
        str = str.replaceAll("    ", " ");
        str = str.replaceAll("   ", " ");
        str = str.replaceAll("  ", " ");
        s = str;
        return s.trim();
      }
      else
        return null;
  }  
    
  
  //-----------------------------------------------------------------------
//startPR2004-08-27^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  /**
   * Foramts a string so that any '@' character is replaced by the four-leaved
   * clover symbol that represents the Macintosh Command key.
   *
   * @param  the string in which to replace '@' symbols with the Command key
   * symbol
   * @return  the string after the '@' symbols have been replaced with Command
   * key symbols
   */
  public static String formatCommandSymbol(String s){
      if (s != null) {
        char c = '\u2318';
        String t = String.valueOf(c);
        String str = "";
        for (int i = 0; i < s.length(); i++){
          if(s.charAt(i) == '@')
            str = str.concat(t);
          else
            str = str.concat(String.valueOf(s.charAt(i)));
        }
        s = str;
        return s;
      }
      else
        return null;
  }
  //------------------------------------------------------------
  public static double normalrange(double a) {
     while(a < -Math.PI)  a += Math.PI*2;
     while(a > Math.PI)  a -= Math.PI*2;
     return a;
  }
     // test if 'a' in acute angle between a1 and a2
     // all assumed to be in range -PI to +PI
   public static boolean isbetween(double a, double a1, double a2) {
     if(a1<a2) {
       if (a2 - a1 <= Math.PI)
         return a>=a1 && a <= a2;
       else
         return a<=a1 || a >= a2;
     }
     else {
       if (a1 - a2 <= Math.PI)
         return a>=a2 && a <= a1;
       else
         return a<=a2 || a >= a1;
     }
  }
     // all assumed to be in range -PI to +PI
   public static double anglebetween(double a1, double a2) {
     if(a1<a2) {
       if (a2 - a1 <= Math.PI)
         return a2-a1;
       else
         return Math.PI*2 - (a2-a1);
     }
     else {
       if (a1 - a2 <= Math.PI)
         return a1-a2;
       else
         return Math.PI*2 - (a1-a2);
     }
  }
     // all assumed to be in range -PI to +PI (a1 is 'from')
   public static double signedanglebetween(double a1, double a2) {
     if(a1<a2) {
       if (a2 - a1 <= Math.PI)
         return a2-a1;
       else
         return -(Math.PI*2 - (a2-a1));
     }
     else {
       if (a1 - a2 <= Math.PI)
         return -(a1-a2);
       else
         return Math.PI*2 - (a1-a2);
     }
  }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  //-----------------------------------------------------------------------
public static void setupMenuItemHeight(){
    u2_base.setupMenuItemHeight();
}


    public static String setTextHtmlFormattedForUpload(String s, int uploadMode){
        String open = uploadMode == MYSQLUpload.MODE_VIA_DUMP ? "££" : "<";
        String close = uploadMode == MYSQLUpload.MODE_VIA_DUMP ? "&&" : ">";
        char c[] = s.toCharArray();
        String ret = "";
        for(int i = 0; i < c.length; i++){
            if(c[i]=='|') ret = ret+open+"br"+close;
            else ret = ret+String.valueOf(c[i]);
        }
        int k;
        while((k=ret.indexOf("^^^"))>=0){
            String temp = ret.substring(k+3);
            ret = ret.substring(0, k)+open+"i"+close+open+"b"+close;
            k = temp.indexOf("^");
            if(k>=0)
                ret = ret + temp.substring(0, k)+open+"/b"+close+open+"/i"+close+temp.substring(k+1);
        }
        while((k=ret.indexOf("^^"))>=0){
            String temp = ret.substring(k+2);
            ret = ret.substring(0, k)+open+"b"+close;
            k = temp.indexOf("^");
            if(k>=0)
                ret = ret + temp.substring(0, k)+open+"/b"+close+temp.substring(k+1);
        }
        while((k=ret.indexOf("^"))>=0){
            String temp = ret.substring(k+1);
            ret = ret.substring(0, k)+open+"i"+close;
            k = temp.indexOf("^");
            if(k>=0)
                ret = ret + temp.substring(0, k)+open+"/i"+close+temp.substring(k+1);
        }
        return ret;
    }
    
    
    public static String setTextHtmlFormattedForUpload2(String s, int uploadMode){
        if(uploadMode != MYSQLUpload.MODE_VIA_DUMP){
            return s;
        }
        String open = "<";
        String close = ">";
        char c[] = s.toCharArray();
        String ret = "";
        for(int i = 0; i < c.length; i++){
            if(c[i]=='|') ret = ret+open+"br"+close;
            else ret = ret+String.valueOf(c[i]);
        }
        int k;
        while((k=ret.indexOf("^^^"))>=0){
            String temp = ret.substring(k+3);
            ret = ret.substring(0, k)+open+"i"+close+open+"b"+close;
            k = temp.indexOf("^");
            if(k>=0)
                ret = ret + temp.substring(0, k)+open+"/b"+close+open+"/i"+close+temp.substring(k+1);
        }
        while((k=ret.indexOf("^^"))>=0){
            String temp = ret.substring(k+2);
            ret = ret.substring(0, k)+open+"b"+close;
            k = temp.indexOf("^");
            if(k>=0)
                ret = ret + temp.substring(0, k)+open+"/b"+close+temp.substring(k+1);
        }
        while((k=ret.indexOf("^"))>=0){
            String temp = ret.substring(k+1);
            ret = ret.substring(0, k)+open+"i"+close;
            k = temp.indexOf("^");
            if(k>=0)
                ret = ret + temp.substring(0, k)+open+"/i"+close+temp.substring(k+1);
        }
        return ret;
    }


 public static String getXMLElement(String filePath, String element){
    return u2_base.getXMLElement(filePath, element);
 }
 
 public static String[] getXMLElements(String filePath, String elements[]){
    return u2_base.getXMLElements(filePath, elements);
 } 

 public static boolean setXMLElement(String filePath, String element, String value){
    return u2_base.setXMLElement(filePath, element, value);
 }
 
 public static boolean setXMLElements(String filePath, String elements[], String values[]){
    return u2_base.setXMLElements(filePath, elements, values);
 } 

 public static boolean setXMLAttribute(String filePath, String attribute, String value){
    return u2_base.setXMLAttribute(filePath, attribute, value);
 }


  public static class myCheckBoxMenuItem extends JCheckBoxMenuItem {
        MenuElement mm[];
        public boolean stayAfterClick = true;
        String s;
        boolean iconSpacer;
        boolean defaultheight = false;

        myCheckBoxMenuItem(String ss) {
            super(ss);
            s =ss;
            init();
        }

        myCheckBoxMenuItem(String ss, boolean wantIconSpacer) {
            super(ss);
            s =ss;
            iconSpacer = wantIconSpacer;
            init();
        }
        myCheckBoxMenuItem(String ss, boolean wantIconSpacer, boolean wantDefaultHeight) {
          super(ss);
          s =ss;
          iconSpacer = wantIconSpacer;
          defaultheight = wantDefaultHeight;
          init();

        }

        void init(){
          if(iconSpacer)
              setIcon(jnode.icons[jnode.BLANK]);
          // if the checkbox is at the top of a menu all those menuitems under it have misaligned text
          if(!defaultheight && !shark.macOS){
              setMinimumSize(new Dimension((int)this.getPreferredSize().getWidth()+u2_base.menuItemWidth, u2_base.menuItemHeight));
              setPreferredSize(new Dimension((int)this.getPreferredSize().getWidth()+u2_base.menuItemWidth, u2_base.menuItemHeight));
          }
          addMouseListener(new MouseAdapter() {
              public void mousePressed(MouseEvent e) {
                if(mm==null) {
                  mm = MenuSelectionManager.defaultManager().getSelectedPath();
                }
             }
          });
          addActionListener(new java.awt.event.ActionListener() {
             public void actionPerformed(ActionEvent e) {
                if(mm==null) return;
                if(!stayAfterClick){
                    stayAfterClick = true;
                    MenuSelectionManager.defaultManager().clearSelectedPath();
                    return;
                }
                if(mm.length>3) {
                    JPopupMenu jp = ((JPopupMenu)getParent());
                    if(jp != null) {
                      jp.setVisible(false);
                      jp.setVisible(true);
                      MenuSelectionManager.defaultManager().setSelectedPath(mm);
                      jp.setVisible(false);
                      jp.setVisible(true);
                    }
                 }
                  MenuSelectionManager.defaultManager().setSelectedPath(mm);
             }
         });
        }
   }

  public static class myCheckBoxMenuItem2 extends JCheckBoxMenuItem {
        myCheckBoxMenuItem2(String s) {
            super(s);
              if(!shark.macOS){
                  setMinimumSize(new Dimension((int)this.getPreferredSize().getWidth()+u2_base.menuItemWidth, u2_base.menuItemHeight));
                  setPreferredSize(new Dimension((int)this.getPreferredSize().getWidth()+u2_base.menuItemWidth, u2_base.menuItemHeight));
              }

        }
   }

   public static class raisedborder extends LineBorder {
     Color color1;
     int thick1;
     raisedborder(Color col,int thick) {
       super(col,thick);
       thick1=thick;
       color1= new Color(Math.min(255,col.getRed()+30), Math.min(255,col.getGreen()+30),
                          Math.min(255,col.getBlue()+30));

     }
     public void paintBorder(Component c,Graphics g,int x,int y,int width,int height) {
       g.setColor(color1);
       for(int i=0;i<thick1;++i) {
         g.draw3DRect(x + i, y + i, width - i * 2 - 1, height - i * 2 - 1, true);
       }
     }
   }
  //---------------------------------------------------------------
  public static JCheckBoxMenuItem CheckBoxMenuItem(String key,KeyStroke accel) {
     myCheckBoxMenuItem jb
         = new myCheckBoxMenuItem(gettext(key,"label"));
     jb.setAccelerator(accel);
     String s = gettext(key,"tooltip");
     if(s != null){
//startPR2004-08-28^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        if (shark.macOS){
          s = formatMacMenu(s);
//          s = formatCommandSymbol(s);
        }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       jb.setToolTipText(s);
     }
     return jb;
  }
  //---------------------------------------------------------------
  public static String edit(String s,int insert1,int insert2) {
     return edit(s,String.valueOf(insert1), String.valueOf(insert2));
  }

  public static String edit(String s,String insert) {
     int i;
     if((i=s.indexOf("%")) >=0) return s.substring(0,i)+insert+s.substring(i+1);
     else return s+" "+insert;
  }
  //---------------------------------------------------------------
  public static String edit(String s,int in[]) {
     int i, j=0;
     while((i=s.indexOf("%")) >=0   && j < in.length) {
       s = s.substring(0, i) + String.valueOf(in[j++]) + s.substring(i + 1);
     }
     return s;
  }
  //---------------------------------------------------------------
  public static String edit(String s,String in[]) {
     int i, j=0;
     while((i=s.indexOf("%")) >=0   && j < in.length) {
       s = s.substring(0, i) + in[j++] + s.substring(i + 1);
     }
     return s;
  }
  //---------------------------------------------------------------
  public static String edit(String s,String insert,String insert2) {
     return edit(edit(s,insert),insert2);
  }
  //---------------------------------------------------------------
  public static String gettext(String key,String subkey,String insert) {
     String s = gettext(key,subkey);
     int i;
     if(s!=null) {
        if((i=s.indexOf("%")) >=0) return s.substring(0,i)+insert+s.substring(i+1);
        else return s+" "+insert;
     }
     return s;
  }
  //---------------------------------------------------------------
  public static String gettext(String key,String subkey) {
     int i,j;
     if(!key.equals(lasttextkey)) {
      String lists[] = sharkStartFrame.searchListText(sharkStartFrame.currStudent);
      for(i=0;i<lists.length;++i) {
         if((lasttext = (String[])db.find(lists[i],key,db.TEXT)) != null)
              break;
      }
      if(i==lists.length) {lasttextkey = "";return null;}
      lasttextkey = key;
     }
     if(lasttext != null)
      for(i=0;i<lasttext.length;++i) {
        if((j = lasttext[i].indexOf('=')) == subkey.length()
            && lasttext[i].substring(0,j).equalsIgnoreCase(subkey)
            && j < lasttext[i].length()-1){
//startPR2004-08-28^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            String str = lasttext[i].substring(j + 1);
            // if running on a Macintosh
//            if (shark.macOS) {
//              str = formatCommandSymbol(str);
//            }
            if(spellchange.active && (subkey.equals("label")||subkey.equals("tooltip"))) return spellchange.spellchange(str);
            return str;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         }
     }
     return null;
  }

  static String getXMLAttribute(String filePath, String value){
    return u2_base.getXMLAttribute(filePath, value);
  }

  //-------------------------------------------------------------------
  public static String numberinwords(int num) {
      if(num<100) return to99[num];
      else if(num<1000) {
         if(num%100==0) return to99[num/100]+ ' '+hundred;
         else return to99[num/100]+ ' '+hundred + ' '+ and + ' ' + to99[num%100];
      }
      else if(num<1000000) {
         if(num%1000==0) return numberinwords(num/1000)+' '+thousand;
         else if(num%1000 < 100) return u.numberinwords(num/1000) + ' ' + thousand +" " + and + ' ' + u.numberinwords(num%1000);
         else return u.numberinwords(num/1000) + ' ' + thousand +", " + u.numberinwords(num%1000);
      }
      else {
         if(num%1000000==0) return numberinwords(num/1000000)+' '+ million;
         else if(num%1000000 < 100) return numberinwords(num/1000000) + ' ' + million +" " + and + ' '+u.numberinwords(num%1000000);
         else return numberinwords(num/1000000) + ' ' + million +", " + u.numberinwords(num%1000000);
      }
  }
  //-------------------------------------------------------------------
  public static String numberinwords(String num) {
     try {
        return numberinwords(Integer.parseInt(num));
     } catch(NumberFormatException e) { return "";}
  }
  //---------------------------------------------------------
  public static class showhelp
//startPR2004-08-27^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    extends JDialog {
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      String mm[];
      FontMetrics m;
      Dimension dim;
      Rectangle r = new Rectangle();
      JLabel panel = new JLabel() {
         public void paint(Graphics g) {
            g.getClipBounds(r);
            g.setColor(runMovers.tooltipbg);
            g.fillRect(r.x,r.y,r.width,r.height);
            g.setFont(runMovers.tooltipfont);
            g.setColor(runMovers.tooltipfg);
            int y=r.y+m.getMaxAscent()+(r.height-m.getHeight()*mm.length)/2;
            for(short i=0;i<mm.length;++i) {
              g.drawString(mm[i],r.x+4,y);
              y += m.getHeight();
            }
         }
      };
      Color bg = new Color(128,128,255);
      public  showhelp(String message) {
//startPR2004-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        super(sharkStartFrame.mainFrame);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2004-08-27^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        this.setResizable(false);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        mm = u.splitString(message);
        this.setTitle(u.gettext("u_","help"));
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        m = getFontMetrics(runMovers.tooltipfont);
        dim = u.getdim(mm,m,4);
//        setBounds(new Rectangle((sharkStartFrame.screenSize.width-dim.width)/2,
//                        sharkStartFrame.screenSize.height*7/8-dim.height,
//                        dim.width + sharkStartFrame.screenSize.height/20,
//                        dim.height+sharkStartFrame.screenSize.height/16));
        setBounds(u2_base.adjustBounds(new Rectangle((sharkStartFrame.screenSize.width-dim.width)/2,
                        sharkStartFrame.screenSize.height*7/8-dim.height,
                        dim.width + sharkStartFrame.screenSize.height/20,
                        dim.height+sharkStartFrame.screenSize.height/16)));
        panel.setBackground(bg);
        this.getContentPane().add(panel);
        this.setVisible(true);
      }
  }
  //----------------------------------------------------------------------
//  public static void forcegc(int waittime) {
//    if(System.currentTimeMillis() > nextgc) {
//       nextgc = System.currentTimeMillis() + 300000;
//       new  spokenWord.whenfree(waittime) {
//          public void action() {
//              if(spokenWord.isfree()) System.gc();
//              nextgc = System.currentTimeMillis() + 30000;
//          }
//       };
//    }
//  }
  //----------------------------------------------------------------
          // non-modal information panel
 public static class info
//startPR2004-08-27^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  extends JDialog {
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   GridBagLayout layout1 = new GridBagLayout();
   GridBagConstraints grid1 = new GridBagConstraints();

  public info(String title,String s,int x1,int y1,int w1, int h1) {
//startPR2004-08-27^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    this.setResizable(false);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    setBounds(new Rectangle(x1, y1,w1,h1));
    this.addWindowListener(new java.awt.event.WindowAdapter() {
       public void windowDeactivated(WindowEvent e) {
         dispose();
       }
    });
    this.getContentPane().setLayout(layout1);
    this.setEnabled(true);
    this.setTitle(title);

    grid1.weightx = grid1.weighty = 1;
    grid1.fill = GridBagConstraints.NONE;
    grid1.gridx = 0;
    grid1.gridy = -1;

    String ss[] = splitString(s);
    for(short i=0;i<ss.length;++i)
       this.getContentPane().add(new JLabel(ss[i]), grid1);
    setVisible(true);
    validate();
  }
 }

 public static class infotextpane
//startPR2004-08-27^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  extends JDialog {
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   GridBagLayout layout1 = new GridBagLayout();
   GridBagConstraints grid1 = new GridBagConstraints();
   JTextPane ta;

  public infotextpane(String title,String s,int x1,int y1,int w1, int h1) {
//startPR2004-08-27^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    this.setResizable(false);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    setBounds(new Rectangle(x1, y1,w1,h1));
    this.addWindowListener(new java.awt.event.WindowAdapter() {
       public void windowDeactivated(WindowEvent e) {
         dispose();
       }
    });
    this.getContentPane().setLayout(layout1);
    this.setEnabled(true);
    this.setTitle(title);

    grid1.weightx = grid1.weighty = 1;
    grid1.fill = GridBagConstraints.BOTH;
    grid1.gridx = 0;
    grid1.gridy = -1;

    ta = new JTextPane();
    ta.setEditable(false);
    s = convertToDoubleCR(s);
    ta.setText(s);
 //   for(short i=0;i<ss.length;++i)
    this.getContentPane().add(ta, grid1);
    setVisible(true);
    validate();
  }
 }


 public static class star extends mover {
    double a;
    Color c;
    public star(int wi, int hi, Color col) {
       super(false);
       c = col;
       a = u.rand(Math.PI*2/5);
       w = wi;
       h = hi;
    }
    public void paint(Graphics g,int x, int y, int w1, int h1) {
       double aa;
       Polygon p = new Polygon();
       int mx = x+w1/2;
       int my = y+h1/2;
       int rad = Math.min(h1,w1)/2,i;

       for(aa=a,i=0; i<5; ++i, aa += Math.PI*2/5) {
          p.addPoint(mx+(int)(rad*Math.cos(aa)), my + (int)(rad*Math.sin(aa)));
          p.addPoint(mx+(int)(rad/3*Math.cos(aa+Math.PI/5)), my + (int)(rad/3*Math.sin(aa+Math.PI/5)));
       }
       g.setColor(c);
       g.fillPolygon(p);
    }
  }
public static String addsuffix(String val, String suff) {
         boolean q_double=false,no_double=false,no_double_longvowel=false,no_double_cons=false,no_double_2cons=false,no_double_w=false;
         boolean q_strip_e=false, keep_e_cons=false, keep_e_cg=false,keep_e_long=false;
         boolean q_y_to_i=false,keep_y=false;
         boolean q_i_to_y=false,keep_i=false;
         boolean q_f_to_v=false,q_f_to_v2=false,keep_f=false;
         String altchar, valsuff;
         String lastcs;
         int len = val.length();
         char last = val.charAt(len-1);
         char last2 = val.charAt(len-2);
         lastcs = val.substring(len-1);
         char suff1 = suff.charAt(0);
         if(u.vowelsy.indexOf(last) <  0) {
           q_double = true;
           if( u.vowelsy.indexOf(suff1) < 0) no_double=no_double_cons = true;
           else if(u.vowels.indexOf(last2) < 0) no_double=no_double_2cons = true;
           else if(len == 2 || u.vowels.indexOf(val.charAt(len-3)) >= 0)
               no_double=no_double_longvowel = true;
           else if(last == 'w' || last == 'x'|| last == 's') no_double=no_double_w = true;
         }
         if(last == 'e' && last2 == 'i') {
            q_i_to_y = true;
            altchar = "y";
            if(suff1 != 'i') keep_i = true;
         }
         if(last == 'e') {
             q_strip_e = true;
             if (u.vowelsy.indexOf(suff1) < 0)  keep_e_cons = true;
             else if("cg".indexOf(last2) >= 0 && "aou".indexOf(suff1) >=0)
                          keep_e_cg = true;
             else if(len == 2
                     || suff1 != 'e'
                         && u.vowelsy.indexOf(last2) >= 0
                         && (!q_i_to_y || keep_i))
                 keep_e_long = true;
         }
         if(last == 'y') {
            q_y_to_i = true;
            altchar = "i";
            if(suff1 == 'i' || u.vowels.indexOf(last2) >=  0) keep_y = true;
         }
         if(last=='f') {
           q_f_to_v = true;
           altchar = "v";
           if(!suff.equals("es")) keep_f = true;
         }
         if(last2=='f') {
           q_f_to_v2 = true;
           altchar = "v";
           if(!suff.equals("s")) keep_f = true;
         }
         valsuff = val.substring(0,len-2);
         if(q_i_to_y && !keep_i) valsuff +=  "y";
         else if(q_f_to_v2 && !keep_f) valsuff +=  "v";
         else valsuff = valsuff+val.substring(len-2,len-1);
         if(q_y_to_i && !keep_y) valsuff += "i";
         else if(q_f_to_v && !keep_f) valsuff += "v";
         else if(!q_strip_e || keep_e_cons || keep_e_cg || keep_e_long)
                                       valsuff += lastcs;
         if(q_double && !no_double) valsuff+=lastcs;
         valsuff += suff;
         return valsuff;
  }
//---------------------------------------------------------------

public static void defaultfont() {   // changed for v4
    defaultfont(false);
}


public static void defaultfont(boolean ignoreCurrScreenSize) {
     String s1,s[];
     Font oriuitreefont = UIManager.getFont("Tree.font");
     if(FontChooser.defaultTreeFont==null || ignoreCurrScreenSize)
         FontChooser.defaultTreeFont = oriuitreefont.deriveFont(Font.BOLD, ignoreCurrScreenSize?((float)getminimumfontsize(oriuitreefont.deriveFont((float)8))):((float)getminimumfontsize(oriuitreefont)));
//     if(FontChooser.defaultTreeFont==null){
       // needs extra step for Mac Leopard
//       Font f = UIManager.getFont("Tree.font");
//       FontChooser.defaultTreeFont = new Font(uitreefont.getName(), Font.BOLD, uitreefont.getSize());
//     }
    // if students had set menu font in a previous version - automatically change to
    // the default, otherwise they are stuck with it in this version.
     if(sharkStartFrame.currStudent >= 0 && (s1 = student.optionstring("s_treefont")) != null)
       student.removeOption("s_treefont");
     s = (String[]) db.find(sharkStartFrame.optionsdb, "treefont", db.TEXT);
     if(!u.adminfontexists("treefont", s)) s = null;
     UIDefaults ui = UIManager.getDefaults();
     Font fo2;
     if(s != null) {
       fo2 = u.fontFromString(s[0],Integer.parseInt(s[1]),Integer.parseInt(s[2]));
     }
     else {
       fo2 =  FontChooser.defaultTreeFont;
     }
     sharkStartFrame.treefont = fo2;
     sharkStartFrame.treefontm = sharkStartFrame.mainFrame.getFontMetrics(sharkStartFrame.treefont);  // rb 7/5/08
     if(fo2 != oriuitreefont || s != null) {
        Enumeration keys = ui.keys();
        while(keys.hasMoreElements()) {
           String ss = keys.nextElement().toString();
           if(ss.indexOf(".font")>0){
               UIManager.put(ss,fo2);
           }
        }
     }
}
//startPR2007-11-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     public static int getminimumfontsize(Font f){
       FontMetrics m = sharkStartFrame.mainFrame.getFontMetrics(f);
       int points = f.getSize();
       Font fo2 = f;
//startPR2007-12-06^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       while(m.getHeight() < sharkStartFrame.screenSize.height / 44 && points < FontChooser.pointSizeMax) {
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//          fo2 = new Font(fo.getName(),fo.getStyle() | fo.BOLD, ++points);
         fo2 = f.deriveFont((float)++points);
         m = sharkStartFrame.mainFrame.getFontMetrics(fo2);
       }
       return fo2.getSize();
     }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2008-06-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     public static boolean stufontexists(String dbkey, String fontdet){
       String fd[] = u.splitString(fontdet);
       if(fd==null||(fd.length>0&&!fontexists(fd[0]))){
         student.removeOption(dbkey);
         return false;
       }
       return true;
     }

     public static boolean adminfontexists(String dbkey, String[] fontdet){
       if(fontdet==null||(fontdet.length>0&&!fontexists(fontdet[0]))){
         if(db.query(sharkStartFrame.optionsdb, dbkey, db.TEXT)>=0)
             db.delete(sharkStartFrame.optionsdb, dbkey, db.TEXT);
         return false;
       }
       return true;
     }

     static boolean fontexists(String fontname){
       String ss[] = new String[]{};
       for(int i = 0; i<sharkStartFrame.importedfonts.length;i++)
         ss = u.addString(ss, sharkStartFrame.importedfonts[i].getName());
       GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();//GET NATIVE FONTS
       Font systemFonts[] = e.getAllFonts();
//startPR2012-06-16^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//       String fontnames[] = new String [systemFonts.length];
//       for(int i = 0; i < fontnames.length; i++){
//         fontnames[i] = systemFonts[i].getName();
//       }
       String fontnames[] = new String []{};
       for(int i = 0; i < systemFonts.length; i++){
          String fname = systemFonts[i].getName();
          fontnames = u.addString(fontnames, fname);
          int k;
          if((k=fname.indexOf('.'))>=0){
              fontnames = u.addString(fontnames, fname.substring(0, k));
          }
       }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       ss = u.addString(ss, fontnames);
       return u.findString(ss, fontname)>=0;
     }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//---------------------------------------------------------------
public static void resetdefaultfont() {
     UIDefaults ui = UIManager.getDefaults();
     Font fo, fo2;
     fo = fo2 = (Font)ui.get("Tree.font");
     FontMetrics m = sharkStartFrame.mainFrame.getFontMetrics(fo);
     int points = fo.getSize();
     while(m.getHeight() < sharkStartFrame.screenSize.height / 40 && points < FontChooser.pointSizeMax) {
//startPR2007-11-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//          fo2 = new Font(fo.getName(),fo.getStyle() | fo.BOLD, ++points);
               fo2 = fo.deriveFont(fo.getStyle() | fo.BOLD, (float)++points);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          m = sharkStartFrame.mainFrame.getFontMetrics(fo2);
     }
     Enumeration keys = ui.keys();
     while(keys.hasMoreElements()) {
           String ss = keys.nextElement().toString();
           if(ss.indexOf(".font")>0)
                      UIManager.put(ss,fo2);
     }
}
   //-----------------------------------------------------------------
   public static String[] importtext() {
      JFileChooser fc = new JFileChooser();
        fc.setFileFilter(new javax.swing.filechooser.FileFilter() {
          public boolean accept(File f) {
            String s = f.getName();
            return f.isDirectory() || s.endsWith(".csv") || s.endsWith(".CSV");
          }
          public String getDescription() {
            return u.gettext("csvimport", "chooserdesc");
          }
        });
      int returnVal = fc.showOpenDialog(null);
      String s[] = new String[0];
      if(returnVal == fc.APPROVE_OPTION) {
          File fname = fc.getSelectedFile().getAbsoluteFile();
          String ssarr[] = u.readFile(fname.getAbsolutePath());
          for(int i = 0; i < ssarr.length; i++){
              String s1[] = u.splitString(ssarr[i], ',');
              for(int j = 0; j < s1.length; j++){
                  String s2 = s1[j].trim();
                  if(s2.length()>0 && u.findString(s, s2)<0)
                    s = u.addString(s, s2);
              }
          }
       }
       return s;
     }
//startPR2010-02-02^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     public static Vector vetfromothershark(File dir) {
        String base = dir.getAbsolutePath()+File.separator;
        String sha = ".sha";
        String thisusers[] = db.dblistnames(sharkStartFrame.sharedPath);
        String sflist[] = db.dblistnames(dir);
        String allusers[] = new String[]{};
        for (int k = 0; k < sflist.length; k++) {
            String name = sflist[k];
            if (u.findString(thisusers, name) >= 0) {
                continue;
            }
            allusers = u.addString(allusers, sflist[k]);
        }
        Vector v = new Vector();
        for (int k = 0; k < allusers.length; k++) {
            String name = allusers[k];
            u.copyfile(new File(base+name+sha), new File(sharkStartFrame.sharedPathplus+name+sha));
            v.add(student.getSimpleStu(name));
            (new File(sharkStartFrame.sharedPathplus+name+sha)).delete();
        }
        return v;
      }






public static class doGetStusFromOtherShark implements Runnable{
    Vector retval = null;

  public void run(){
        File ppd = null;
        File root = null;
        int count = 0;
        File file;
        String shared = gettext("stulist_import2", "searchforfolder");
        String shared2[] = u.splitString(gettext("stulist_import2", "allowfolders"));
        if(shared2 == null || shared2.length <2)shared2= new String[]{shared};
        Vector ss;
        if (!shark.macOS) {
            file = sharkStartFrame.sharedPath;
            while (file.getParentFile() != null) {
                count++;
                file = file.getParentFile();
                if (count == 2) {
                    ppd = file;
                }
                root = file;
            }

            for(int i = 0; i < shared2.length; i++){
              count = 1;
              String text = "";
              ArrayList retvalarr = new ArrayList();
              while ((text = gettext("stulist_import2", "appdatapath" + String.valueOf(count), String.valueOf(root.getPath().charAt(0)))) != null) {
                file = new File(text);
                if (file.isDirectory()) {
                    ss = findsharedfilesfromparent(file, shark.otherProgram, shared2[i]);
                    if(ss!=null){
//                                retval = ss;
//                                return;
                               retvalarr.add(ss);
                    }
                }
                count++;
               }
                    for(int k = 0; k < retvalarr.size(); k++){
                        Vector vt = (Vector)retvalarr.get(k);
                        if(vt!=null && vt.get(0) instanceof String){
                            if(((String)vt.get(0)).indexOf(shark.getVersionType(shark.licenceType))>0){
                                retval = vt;
                                return;                                
                            }
                        }
                    }
                    if(retvalarr.size()>0){
                        retval = (Vector)retvalarr.get(0);
                        return;
                    }

                // find shared folder in the same folder.
                ss = findsharedfilesfromparent(sharkStartFrame.sharedPath.getParentFile(), null, shared2[i]);
                if (ss != null) {
                        retval = ss;
                        return;
                }

                if (ppd != null || root != null) {
                    // find shared folder on the same level.
                    ss = findsharedfilesfromparent(ppd, shark.otherProgram, shared2[i]);
                    if (ss != null) {
                        retval = ss;
                        return;
                    }
                }

                // find a shared in Program Files.
                file = new File(root.getAbsolutePath() + "Program Files");
                ss = findsharedfilesfromparent(file, shark.otherProgram, shared2[i]);
                if (ss != null) {
                        retval = ss;
                        return;
                }

                // find a shared in Program Files - in the root
                file = new File(root.getAbsolutePath() + "Program Files");
                ss = findsharedfilesfromparent(file, null, shared2[i]);
                if (ss != null) {
                         retval = ss;
                        return;
                }

                // find a shared in Program Files.
                file = new File(root.getAbsolutePath() + "Program Files (x86)");
                ss = findsharedfilesfromparent(file, shark.otherProgram, shared2[i]);
                if (ss != null) {
                        retval = ss;
                        return;
                }

                // find a shared in Program Files (x86) - in the root
                file = new File(root.getAbsolutePath() + "Program Files (x86)");
                ss = findsharedfilesfromparent(file, null, shared2[i]);
                if (ss != null) {
                        retval = ss;
                        return;
                }
            }

            // find shared folder at path specified in publictext.
            count = 1;
            String text = "";
            while ((text = gettext("stulist_import2", "path" + String.valueOf(count), String.valueOf(root.getPath().charAt(0)))) != null) {
                file = new File(text);
                if (file.isDirectory()) {
                    ss = findsharedfilesfromparent(file, null, null);
                    if(ss!=null){
                        retval = ss;
                        return;
                    }
                }
                count++;

            }
        } else {
            // find shared folder on the same level.
            file = new File(System.getProperty("user.dir")).getParentFile();
            ss = findsharedfilesfromparent(file, shark.otherProgram, shared);
            if (ss != null) {
                        retval = ss;
                        return;
            }

            // find a shared in /Applications/
            ss = findsharedfilesfromparent(new File("/Applications/"), shark.otherProgram, shared);
            if (ss != null) {
                        retval = ss;
                        return;
            }

            // find a shared in /Library/Application Support/White Space Ltd
            ss = findsharedfilesfromparent(new File(shark.sep+"Library"+shark.sep+"Application Support"+shark.sep+shark.companyName+shark.sep), shark.otherProgram, shared);
            if (ss != null) {
                        retval = ss;
                        return;
            }
        }
  }
}




    static File[] getchilddirscontaining(File parent, String contains) {
        if (parent == null) {
            return null;
        }
        final String s = contains;
        File f[] = parent.listFiles(new FileFilter() {
            public boolean accept(File f) {
                boolean isdir = f.isDirectory();
                if(s==null)return isdir;
                else return isdir && f.getName().indexOf(s) >= 0;
            }
        });
        if (f!=null && f.length > 0) {
            return f;
        }
        return null;
    }

    static Vector findsharedfilesfromparent(File parentdir, String mainfolder, String shared) {
        File fa1[];
        if(shared == null)return vetfromothershark(parentdir);
        if(mainfolder != null){
            fa1 = getchilddirscontaining(parentdir, mainfolder+shark.getVersionType(null));
            if(fa1==null) fa1 = getchilddirscontaining(parentdir, mainfolder);
        }
        else
            fa1 = new File[]{parentdir};
        if (fa1 == null) {
            return null;
        }
        File fa2[];
        for (int i = 0; i < fa1.length; i++) {
            fa2 = getchilddirscontaining(fa1[i], shared);
            if (fa2 != null && fa2[0] != null && fa2[0].isDirectory()) {
                Vector v = vetfromothershark(fa2[0]);
                v.add(0, fa2[0].getAbsolutePath());
                return v;
            }
        }
        return null;
    }

     public static Vector getstulist(String name[],File  dir) {
       int i;
       if(dir == null) dir = new File(sharkStartFrame.sharedPathplus.substring(0,3));
       if(u.findString(name,dir.getName())>=0){
         return vetfromothershark(dir);
       }
       else {
         File ff[] = dir.listFiles(new FileFilter() {
                   public boolean accept(File f) {
                     return f.isDirectory();
                   }
         });
         if(ff==null) return null;
         Vector retv;
         for(i=0;i<ff.length;++i) {
            if((retv = getstulist(name,ff[i])) != null) {
              return retv;
            }
         }
       }
       return null;
     }
//endPRtoupdate^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR



  static class list
      extends JDialog {
   public int screenwidth;
   public int screenheight;
   BorderLayout layout1 = new BorderLayout();
   JList jj = new JList();
   String js[];
   JDialog thisadminlist = this;

   public list(JFrame owner, Rectangle r){
     super(owner);
    this.setResizable(false);
     setBounds(r);
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    this.addWindowListener(new java.awt.event.WindowAdapter() {
        public void windowDeactivated(WindowEvent e) {
           if(sharkStartFrame.currStudent>=0) dispose();
        }
     });
    this.getContentPane().setLayout(layout1);
    this.setEnabled(true);
    this.setTitle(u.gettext("madminlist","title"));
    this.getContentPane().add(new JScrollPane(jj),BorderLayout.CENTER);
    setVisible(true);
    validate();
   }
  }
//----------------------------------------------------------------
 static void showlist(String title,String list[]) {new showlist(title,list);}
 public static int getadvance(FontMetrics m) {
    int i,ret=0;
    for(i=0;i<letters.length();++i) ret = Math.max(ret,m.charWidth(letters.charAt(i)));
    return ret;
 }

 static class showlist
//startPR2004-08-27^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     extends JDialog {
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   BorderLayout layout1 = new BorderLayout();

   public  showlist(String title,String list[]) {
//startPR2004-08-27^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    this.setResizable(false);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    setBounds(new Rectangle(sharkStartFrame.screenSize.width/3,
                            sharkStartFrame.screenSize.height/4,
                            sharkStartFrame.screenSize.width/3,
                            sharkStartFrame.screenSize.height/2));
    this.getContentPane().setLayout(layout1);
    this.setEnabled(true);
    this.setTitle(title);
    this.getContentPane().add(new JScrollPane(new JList(list)), BorderLayout.CENTER);
    setVisible(true);
    validate();
  }
 }


//startPR2008-01-23^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2009-12-14^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
// static void showtext(String title,String list, Rectangle rect) {new showtext(title,list,rect);}
 static void showtext(JFrame jf, String title,String list, Rectangle rect) {new showtext(jf, title,list,rect);}
//endPRauth^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
 static void showtext(JFrame jf, String title,String list, Rectangle rect, Color bgcol, JButton[] buttons1) {new showtext(jf, title,list,rect, bgcol, buttons1);}
 
 static class showtext
//startPR2009-12-14^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//     extends JFrame {
     extends JDialog {
//endPRauth^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//   GridBagLayout layout1 = new GridBagLayout();
   Rectangle rect;
   String title1;
   String list;
   Color bgcolour;
   JButton buttons[];
//startPR2009-12-14^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//   public showtext(String title,String list, Rectangle rect) {
   public showtext(JFrame jf, String title,String list1, Rectangle rect1) {
     super(jf);
     rect = rect1;
     title1 = title;
     list = list1;
     init();
   }
     
     
   public showtext(JFrame jf, String title,String list1, Rectangle rect1, Color bgcol, JButton[] buttons1) { 
       super(jf);
       rect = rect1;
       title1 = title;
       list = list1;
       bgcolour = bgcol;
       buttons = buttons1;
       init();
   }
   
   void init(){
//endPRauth^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2008-06-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     focusBlock = true;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     this.setResizable(false);
     setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
     setBounds(rect);
     this.getContentPane().setLayout(new GridBagLayout());
     GridBagConstraints grid = new GridBagConstraints();
     grid.fill = GridBagConstraints.BOTH;
     grid.gridx = 0;
     grid.gridy = -1;
     
     
     this.setEnabled(true);
     this.setTitle(title1);
     
//startPR2009-12-14^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//     setIconImage(sharkStartFrame.sharkicon);
//endPRauth^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     JTextPane textPane = new textpane();
     textPane.setEditable(false);
     textPane.setContentType("text/html");
     textPane.setText(list);
     if(bgcolour!=null)
         textPane.setBackground(bgcolour);
     textPane.setCaretPosition(0);
     textPane.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
     u.uuScrollPane scroll = new u.uuScrollPane(textPane);
     scroll.setBorder(BorderFactory.createEmptyBorder());
     grid.weightx = 1;
     grid.weighty = 1;     
     JPanel mainpn = new JPanel(new GridBagLayout());
     mainpn.add(scroll, grid);
     grid.insets = new Insets(0,0,10,0);
     this.getContentPane().add(mainpn, grid);
     if(buttons!=null){
         grid.gridx = 0;
         grid.gridy = -1;        
         grid.weighty = 0;
         grid.fill = GridBagConstraints.HORIZONTAL;
         JPanel pnButtons = new JPanel(new GridBagLayout());
         grid.insets = new Insets(0,0,10,0);
         this.getContentPane().add(pnButtons, grid);
         grid.gridx = -1;
         grid.gridy = 0;   
         grid.fill = GridBagConstraints.NONE;
         for(int i = 0; i < buttons.length; i++){
             if(i<buttons.length-1)grid.insets = new Insets(0,0,0,10);
             else grid.insets = new Insets(0,0,0,0);
             pnButtons.add(buttons[i], grid);
         }
     }
     textPane.addHyperlinkListener(new HyperlinkListener() {
        public void hyperlinkUpdate(HyperlinkEvent e) {
            if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                u.launchWebSite(e.getDescription());
            }
        }
     });
     setVisible(true);
     validate();
  }
//startPR2008-06-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   public void dispose(){
     u.focusBlock = false;
     super.dispose();
   }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  class textpane extends JTextPane {
    public void paint(Graphics g) {
      ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
      super.paint(g);
    }
  }
 }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
 public static class chooselist
//startPR2004-08-27^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     extends JDialog {
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   BorderLayout layout1 = new BorderLayout();
   JList jj;
   int rep[];

   public  chooselist(String title,String list[],int[] reply) {
//startPR2004-08-27^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    this.setResizable(false);
    rep = reply;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    setBounds(new Rectangle(sharkStartFrame.screenSize.width/3,
                            sharkStartFrame.screenSize.height/4,
                            sharkStartFrame.screenSize.width/3,
                            sharkStartFrame.screenSize.height/2));
    this.getContentPane().setLayout(layout1);
    this.setEnabled(true);
    this.setTitle(title);
    this.getContentPane().add(new JScrollPane(jj = new JList(list)), BorderLayout.CENTER);
    this.setModal(true);
//    jj.setSelectedIndex(reply[0]);
    jj.addListSelectionListener(new ListSelectionListener() {
              public void valueChanged(ListSelectionEvent e) {
                rep[0] = jj.getSelectedIndex();
                dispose();
                try {
                  finalize();
                }
                catch(Throwable th) {}
              }
    });
    validate();
    setVisible(true);
  }
 }
 static class shownotes extends JWindow {
   BorderLayout layout1 = new BorderLayout();

   public  shownotes(String list[],String title,int x1, int y1, boolean left) {
    int margin = 6;
    JPanel pan = new JPanel();
    pan.setLayout(new BorderLayout());
    mlabel_base jj= new mlabel_base(u.combineString(list),true);
    JLabel lab = null;
    if(title != null) {
      lab = new JLabel(title);
      lab.setBackground(sharkStartFrame.teachbgcolor);
      lab.setForeground(runMovers.tooltipfg);
      lab.setOpaque(true);
      lab.setHorizontalAlignment(lab.CENTER);
    }
    Font tooltipfont = UIManager.getFont("ToolTip.font");
    jj.setBackground(sharkStartFrame.teachbgcolor);
    jj.setForeground(runMovers.tooltipfg);
    jj.setFont(runMovers.tooltipfont);
    pan.setBorder(BorderFactory.createLineBorder(runMovers.tooltipfg));
    jj.setBorder(BorderFactory.createLineBorder(runMovers.tooltipfg));
    FontMetrics m = getFontMetrics(tooltipfont);
    Dimension d = jj.getPreferredSize();
    d.width += 4;
    d.height += 4;
    if(title != null) {
       d.width = Math.max(d.width, m.stringWidth(title)+margin*2);
       d.height += m.getHeight();
    }
    setBounds(new Rectangle(left?(x1-d.width):Math.min(x1,sharkStartFrame.screenSize.width-d.width),
                            y1,
                            d.width,
                            d.height));
    this.getContentPane().setLayout(layout1);
    this.setEnabled(true);
    if(title != null) pan.add(lab, BorderLayout.NORTH);
    pan.add(jj, BorderLayout.CENTER);
    this.getContentPane().add(pan, BorderLayout.CENTER);
    setVisible(true);
    validate();
  }
 }
 static class showbuttonnotes extends JWindow {
   BorderLayout layout1 = new BorderLayout();
   JWindow thiswin = this;
   String list;
   int x1;
   int y1;
   boolean left;
   boolean top;
   
   public  showbuttonnotes(Window owner, String list1,int x11, int y11, boolean left1,boolean top1) {
    super(owner);
    list = list1;
    x1 = x11;
    y1 = y11;
    left = left1;
    top = top1;
    init();
   }
   public  showbuttonnotes(String list1,int x11, int y11, boolean left1,boolean top1) {
    list = list1;
    x1 = x11;
    y1 = y11;
    left = left1;
    top = top1;
    init();
  }
   
   void init(){
    int margin = 6;
    JPanel pan = new JPanel();
    pan.setLayout(new BorderLayout());
    mlabel_base jj= new mlabel_base(list,true);
    JLabel lab = null;
    Font tooltipfont = UIManager.getFont("ToolTip.font");
    jj.setBackground(sharkStartFrame.textbgcolor);
    jj.setForeground(runMovers.tooltipfg);
    jj.setFont(runMovers.tooltipfont);
    pan.setBorder(BorderFactory.createLineBorder(runMovers.tooltipfg));
    jj.setBorder(BorderFactory.createLineBorder(runMovers.tooltipfg));
    FontMetrics m = getFontMetrics(tooltipfont);
    Dimension d = jj.getPreferredSize();
    d.width += 4;
    d.height += 4;
    setBounds(new Rectangle(
            top ? Math.max(0,Math.min(x1- d.width/2,sharkStartFrame.screenSize.width-d.width-m.getMaxAdvance()))
                              : (left?(x1-d.width):Math.min(x1,sharkStartFrame.screenSize.width-d.width-m.getMaxAdvance())),
                            top ? y1 - d.height
                             : Math.min(y1, sharkStartFrame.screenSize.height - d.height-m.getHeight()),
   

            d.width,
                            d.height));
    this.getContentPane().setLayout(layout1);
    this.setEnabled(true);
    pan.add(jj, BorderLayout.CENTER);
    this.getContentPane().add(pan, BorderLayout.CENTER);
    setVisible(true);
    
    validate();       
   }
   
 }
  //----------------------------------------------------------
  static class transparentFilter extends RGBImageFilter {
     public int tcolor;
     public transparentFilter(Color color) {
        super();
        tcolor = color.getRGB() & 0x00ffffff;
        canFilterIndexColorModel = true;
     }
     public int filterRGB(int x, int y, int rgb) {
        if((rgb & 0x00ffffff) == tcolor) return 0;
        else return rgb;
     }
  }
  public  static Image transparentImage(Image im1,Color c) {
     Toolkit t =  Toolkit.getDefaultToolkit();
     return  t.createImage(
                      new FilteredImageSource(im1.getSource(),
                                        new transparentFilter(c)));
  }
  //---------------------------------------------------------------------
  static boolean stringequal(String a[], String b[]) {
    if(a==null) return b == null;
    if(b==null) return false;
    if(a.length != b.length) return false;
    for(int i=0;i<a.length;++i) {
       if(!a[i].equals(b[i])) return false;
    }
    return true;
  }
                  // force tooltip_base whenever mouse goes over component
   public static void forcenotes(Window owner, JComponent jj, boolean left,boolean top) {
       uMouseAdapter mm = new uMouseAdapter(owner);
       mm.tooltip = jj.getToolTipText();
       mm.leftpopup = left;
       mm.jj = jj;
       jj.setToolTipText(null);
       jj.addMouseListener(mm);
  }
}
class uMouseAdapter extends MouseAdapter {
  JComponent jj;
  u.showbuttonnotes popup;
  String tooltip;
  boolean leftpopup,toppopup;
  long lastexit;
  Window owningWindow;
  
  public uMouseAdapter(Window owner){
      owningWindow = owner;
  }
   
  public void mouseEntered(MouseEvent e) {
      if(!jj.isEnabled())return;
    if(System.currentTimeMillis() - lastexit > 100 && popup == null) {
      Point pt = jj.getLocationOnScreen();
      if(owningWindow!=null)
          popup = new u.showbuttonnotes(owningWindow, tooltip,
                                         toppopup? pt.x + jj.getWidth()/2
                                           :(leftpopup ? pt.x : (pt.x + jj.getWidth())),
                                         pt.y, leftpopup,toppopup);
      else
          popup = new u.showbuttonnotes(tooltip,
                                         toppopup? pt.x + jj.getWidth()/2
                                           :(leftpopup ? pt.x : (pt.x + jj.getWidth())),
                                         pt.y, leftpopup,toppopup); 
      try{
          if(jj.getHierarchyListeners().length==0)
            jj.addHierarchyListener(new HierarchyListener() {
             public void hierarchyChanged(HierarchyEvent e) {
               if(popup != null) {
                 popup.dispose();
                 popup = null;
                 lastexit = System.currentTimeMillis();
               }
               jj.removeHierarchyListener(this);
            }
          });
      }
      catch(Exception er){}
    }
  }
  public void mouseExited(MouseEvent e) {
    if(popup != null) {
      popup.dispose();
      popup = null;
      lastexit = System.currentTimeMillis();
    }
  }
  public void mousePressed(MouseEvent e) {
    if(jj instanceof JButton  && popup != null) {
      popup.dispose();
      popup = null;
    }
  }

}
class uScrollPane extends JScrollPane {
     Component jtree;
     uScrollPane(Component tree) {
         super(tree);
         jtree = tree;
         getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
           public void adjustmentValueChanged(AdjustmentEvent e) {
               jtree.repaint();
           }
         });
     }
}
//startPR2010-05-24^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
class uEditorPane extends JEditorPane {
    Insets ins;
    Font f;
     uEditorPane(Insets i) {
        super("text/html" , null);
        ins = i;
        setEditable(false);
        setBorder(BorderFactory.createEtchedBorder());
        setOpaque(true);
        setBackground(sharkStartFrame.teachbgcolor);
        setMargin(new Insets(10,10,10,10));
        f = UIManager.getFont("ToolTip.font").deriveFont(Font.PLAIN);
     }
    public Insets getInsets()
    {
        return ins;
    }
    public void setTextHtmlFormatted(String s, Font f){
        StringBuffer style = new StringBuffer("font-family:" + f.getFamily() + ";");
        style.append("font-weight:" + "normal" + ";");
        style.append("font-size:" + f.getSize() + "pt;");
        s = "<html><body style=\"" + style + "\">"+ s+ "</body></html>";
        char c[] = s.toCharArray();
        String ret = "";
        for(int i = 0; i < c.length; i++){
            if(c[i]=='|') ret = ret+"<br>";
            else ret = ret+String.valueOf(c[i]);
        }
        int k;
        while((k=ret.indexOf("^^^"))>=0){
            String temp = ret.substring(k+3);
            ret = ret.substring(0, k)+"<b><i>";
            k = temp.indexOf("^");
            if(k>=0)
                ret = ret + temp.substring(0, k)+"</b></i>"+temp.substring(k+1);
        }
        while((k=ret.indexOf("^^"))>=0){
            String temp = ret.substring(k+2);
            ret = ret.substring(0, k)+"<b>";
            k = temp.indexOf("^");
            if(k>=0)
                ret = ret + temp.substring(0, k)+"</b>"+temp.substring(k+1);
        }
        while((k=ret.indexOf("^"))>=0){
            String temp = ret.substring(k+1);
            ret = ret.substring(0, k)+"<i>";
            k = temp.indexOf("^");
            if(k>=0)
                ret = ret + temp.substring(0, k)+"</i>"+temp.substring(k+1);
        }
        setText(ret);
    }

    public void paint(Graphics g) {
      ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
      super.paint(g);
    }

}





//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR