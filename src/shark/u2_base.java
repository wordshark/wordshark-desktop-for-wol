package shark;

import java.io.*;
import javax.swing.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.w3c.dom.*;
import javax.xml.parsers.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.text.*;
import java.util.*;
import java.util.regex.*;
import java.util.UUID;



public class u2_base{

    private static String lasttext[],lasttextkey=new String("");
  static int menuItemWidth = 55;
  static int menuItemHeight;
  static MenuElement lastMenuElement;
  static String saXmlFileName = "SA83PFB.xml";
  static String wholekey = "38I7PEH96VGA4SR5N0LFW2UQJ1YKBMXOCTZD";  
    
  
  public static Object[] addObject(Object[] sa, Object s) {
     if(sa == null) return new Object[]{s};
//     short len = (short)sa.length;
     int len = (int)sa.length;
     Object news[] = new Object[len+1];
     System.arraycopy(sa,0,news,0,len);
     news[len] = s;
     return news;
 }    
  
  public static Object[] addObjects(Object sa[], Object s[]) {
     if(sa == null) return s;
     if(s==null) return sa;
     short len = (short)sa.length;
     short len2 = (short)s.length;
     Object news[] = new Object[len+len2];
     System.arraycopy(sa,0,news,0,len);
     System.arraycopy(s,0,news,len,len2);
     return news;
 }
  
  public static String[] addString(String[] sa, String s) {
     if(sa == null) return new String[]{s};
//     short len = (short)sa.length;
     int len = (int)sa.length;
     String news[] = new String[len+1];
     System.arraycopy(sa,0,news,0,len);
     news[len] = s;
     return news;
 }    
  
  
  public static selectGameDetails[] addSelectGameDetails(selectGameDetails[] sa, selectGameDetails s) {
     if(sa == null) return new selectGameDetails[]{s};
//     short len = (short)sa.length;
     int len = (int)sa.length;
     selectGameDetails news[] = new selectGameDetails[len+1];
     System.arraycopy(sa,0,news,0,len);
     news[len] = s;
     return news;
 }   
  
    
  
  public static int adjustMaxFontSizeToResolution(double fsize){
      int BASERESOLUTIONHEIGHT = 1350;     
      if(!ChangeScreenSize_base.isActive)
        return (int)(java.awt.Toolkit.getDefaultToolkit().getScreenSize().getHeight() * fsize / BASERESOLUTIONHEIGHT);   
      else
        return (int)(sharkStartFrame.screenSize.getHeight() * fsize / BASERESOLUTIONHEIGHT);
  }
  
  // needed when the screen is resized and then able to be REPOSITIONED
  public static java.awt.Rectangle adjustBounds(java.awt.Rectangle r){
      if(ChangeScreenSize_base.isActive && sharkStartFrame.mainFrame!=null && sharkStartFrame.mainFrame.isShowing()){
          try{
            return new java.awt.Rectangle((int)r.getX()+sharkStartFrame.mainFrame.getLocationOnScreen().x, 
                    (int)r.getY()+sharkStartFrame.mainFrame.getLocationOnScreen().y, 
                    (int)r.getWidth(), (int)r.getHeight());
          }
          catch(Exception e){}
      }
      return r;
  }  
  
  public static String combineString(String s[]) {
    if(s==null || s.length == 0) return "";
    if(s.length == 1) return s[0];
    String ss = new String(s[0]);
    for(short i=1;i<s.length;++i) {
       ss = ss + "|" + s[i];
    }
    return ss;
 }

 public static String combineString(String s[],String ch) {
    if(s==null || s.length == 0) return "";
    if(s.length == 1) return s[0];
    String ss = new String(s[0]);
    for(short i=1;i<s.length;++i) {
       ss = ss + ch + s[i];
    }
    return ss;
 }
 
  public static short findString(String[] sa, String s) {
     return findString(sa, s, false);
 } 
 
 public static short findString(String[] sa, String s, boolean caterfornulls) {
     short i;
     if(sa == null) return -1;
     short len = (short)sa.length;
     for(i=0;i<len;++i) {
        if(caterfornulls && sa[i]==null)continue;
        if(sa[i].equalsIgnoreCase(s))  return i;
     }
     return -1;
 } 

    
 
   public static final String getAppDataFolder(){
       String DeactivationPath = null;
        if (!shark.macOS) {
            String root = getRoot(sharkStartFrame.publicPathplus);
            File f = new File((DeactivationPath = root + "ProgramData"));
            if (!f.exists()) {
                DeactivationPath = root + "Documents and Settings" + shark.sep + "All Users" + shark.sep + "Application Data";
            }
        } else {
            DeactivationPath =  shark.sep + "Library" + shark.sep + "Preferences";
            if (shark.language.equals(shark.LANGUAGE_NL)) {
                if(!(new File(DeactivationPath)).exists()){
                    DeactivationPath =  shark.sep + "Bibliotheek" + shark.sep + "Preferences";
                }
            }
        }
        return DeactivationPath + shark.sep;
   }
   
    

 
     public static final String getFirstMacAddressCode() throws IOException {
         String ss[] = getMacAddresses();
         if(ss== null || ss.length==0)return null;
         WebAuthenticateBase_base wab = new WebAuthenticateBase_base();
         return wab.makeCode(wab.responseuserkey, ss[0], 3);
    } 
 
     public static final String[] getMacAddresses() throws IOException {
        try {
            if (!shark.macOS) {
                return windowsParseMacAddresses(windowsRunIpConfigCommand(), false);
            } else {
                return osxParseMacAddresses(osxRunIfConfigCommand());
            }
        } catch (ParseException ex) {
            ex.printStackTrace();
            throw new IOException(ex.getMessage());
        }
    }
     
    public static String getRoot(String path) {
        if (!shark.macOS) {
            File f = new File(path);
            f.lastModified();
            while (f.getParentFile() != null) {
                f = f.getParentFile();
            }
            return f.getAbsolutePath();
        } else {
            return path.split("/")[0];
        }
    }     
    
    
    
   public static final String getSecretFolderName(){
        String wsfolder = swapString("WHITESPACELTD", "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789", u2_base.wholekey);
        return wsfolder + shark.sep
                + swapString(shark.programName.toUpperCase() + shark.versionNo, "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789", u2_base.wholekey);
   }  
   
   public static String getUUID(){
       return String.valueOf(UUID.randomUUID());
   }
   
 
    public static String gettext(String publicPathPlus, String key,String subkey) {
       int i,j;
       if(!key.equals(lasttextkey)) {
         lasttext = (String[])db.find(publicPathPlus+"publictext",key,db.TEXT);
         lasttextkey = key;
       }
       if(lasttext != null) for(i=0;i<lasttext.length;++i) {
          if((j = lasttext[i].indexOf('=')) == subkey.length()
              && lasttext[i].substring(0,j).equalsIgnoreCase(subkey)
              && j < lasttext[i].length()-1){
             String str = lasttext[i].substring(j + 1);
             return str;
          }
       }
       return null;
    }

    public static String gettext(String publicPathPlus, String key,String subkey,String insert) {
       String s = gettext(publicPathPlus, key,subkey);
       int i;
       if(s!=null) {
          if((i=s.indexOf("%")) >=0)
            return s.substring(0,i)+insert+s.substring(i+1);
          else return
              s+" "+insert;
       }
       return s;
    }


  static String getXMLAttribute(String filePath, String value){
    try {
      Document doc = null;
      Node n;
      Element e;
      File file = new File(filePath);
      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      DocumentBuilder db = dbf.newDocumentBuilder();
      dbf.setValidating(false);
      doc = db.parse(file);
      n = (Node) doc.getDocumentElement();
      e = (Element) n;
      return e.getAttribute(value);
    }
    catch (Exception trans) {return null;}
  }
    
 public static String getXMLElement(String filePath, String element){
    if(filePath == null)return null;  
    Document doc = null;
    File file = new File(filePath);
    try {
      if (!file.canWrite() && shark.macOS) {
          setNewFilePermissions(file, true);
      }
      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      DocumentBuilder db = dbf.newDocumentBuilder();
      dbf.setValidating(false);
      doc = db.parse(file);
      NodeList elementName = doc.getElementsByTagName(element);
      Element firstNameElement = (Element)elementName.item(0);
      return firstNameElement.getTextContent();
    }
    catch (Exception trans) {
      return null;
    }
 }
  
 public static String[] getXMLElements(String filePath, String elements[]){
    if(filePath == null)return null;
    if(filePath == null || elements==null || elements.length==0) return null;
    Document doc = null;
    File file = new File(filePath);
    try {
      if (!file.canWrite() && shark.macOS) {
          setNewFilePermissions(file, true);
      }
      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      DocumentBuilder db = dbf.newDocumentBuilder();
      dbf.setValidating(false);
      doc = db.parse(file);
      String ret[] = null;
      for(int i = 0; i < elements.length; i++){
        NodeList elementName = doc.getElementsByTagName(elements[i]);
        Element firstNameElement = (Element)elementName.item(0);
        if(ret == null)ret = new String[]{firstNameElement.getTextContent()};
        else ret = addString(ret, firstNameElement.getTextContent());
      }
      return ret;
    }
    catch (Exception trans) {
      return null;
    }
 }
 
 
    final static boolean isMacAddress(String macAddressCandidate) {
        if (macAddressCandidate == null) {
            return false;
        }
        boolean ret = false;
        //    Pattern macPattern = Pattern.compile(".*((:?[0-9a-f]{2}[-:]){5}[0-9a-f]{2}).*", Pattern.CASE_INSENSITIVE);
        Pattern macPattern;
        if (shark.network) {
            macPattern = Pattern.compile("[0-9a-f][0-9a-f][0-9a-f][0-9a-f][0-9a-f][0-9a-f][0-9a-f][0-9a-f][0-9a-f][0-9a-f][0-9a-f][0-9a-f]", Pattern.CASE_INSENSITIVE);
        } else {
            macPattern = Pattern.compile("[0-9a-f][0-9a-f][-][0-9a-f][0-9a-f][-][0-9a-f][0-9a-f][-][0-9a-f][0-9a-f][-][0-9a-f][0-9a-f][-][0-9a-f][0-9a-f]", Pattern.CASE_INSENSITIVE);
        }
        Matcher matcher = macPattern.matcher(macAddressCandidate);
        if (matcher.matches()) {
            ret = true;
        }
        macPattern = Pattern.compile("[0-9a-f][0-9a-f][:][0-9a-f][0-9a-f][:][0-9a-f][0-9a-f][:][0-9a-f][0-9a-f][:][0-9a-f][0-9a-f][:][0-9a-f][0-9a-f]", Pattern.CASE_INSENSITIVE);
        matcher = macPattern.matcher(macAddressCandidate);
        if (matcher.matches()) {
            ret = true;
        }
        return ret;
    } 
    
  public static void launchFile(String url, boolean isvideo){
    try{
      String[] cmd = null;
      if(shark.macOS)
        cmd = new String[] {"open", url};
      else{
        File f = null;
        if(isvideo){
          for (int k = 0; k < u.uppercase.length(); k++) {
            String ss = String.valueOf(u.uppercase.charAt(k));
            f = new File(ss + ":\\Program Files\\Windows Media Player\\wmplayer.exe");
            if (f.exists()) {
              cmd = new String[] {f.getAbsolutePath(), "/Play", url};
              break;
            }
          }
        }
        if(f==null || !f.exists()){
          cmd = new String[] {"cmd", "/c", "start", url, url};
        }
      }
      if(cmd!=null)Runtime.getRuntime().exec(cmd);
    }
    catch(Exception e){e.printStackTrace();}
  }  
  
  
  public static void launchExplorer(String tofile){
                            try {
                        if(shark.macOS)
                          Runtime.getRuntime().exec(new String[]{"open",tofile});
                        else
                          Runtime.getRuntime().exec(new String[]{"explorer.exe",tofile});
                      }
                      catch(Exception ee){}
  }
  
  public static void launchWebSite(String url){
    launchWebSite(url, null);
  }  
  
  public static void launchWebSite(String url, String errormess){
    try {
      if (shark.macOS) {
          String http = "http://";
          if(!url.startsWith(http))url = http+url;
          java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
      }
      else if (shark.linuxOS){
        String[] browsers = {"firefox", "opera", "konqueror", "epiphany", "mozilla", "netscape" };
        String browser = null;
        for (int count = 0; count < browsers.length && browser == null; count++)
          if (Runtime.getRuntime().exec(new String[] {"which", browsers[count]}).waitFor() == 0)
            browser = browsers[count];
        if (browser == null)
          throw new Exception("Could not find web browser");
        else
          Runtime.getRuntime().exec(new String[] {browser, url});
      }
      else {
        Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url);
      }
    }
    catch (Exception e) {
      okmess(shark.programName, errormess);
    }
  }    
  
 

      
  
  public static void okmess(String s, Object owner) {
      if(owner instanceof sharkStartFrame)
        JOptionPane.showMessageDialog((JFrame)owner,splitString(u.gettext(s,"message")),u.gettext(s,"heading"),JOptionPane.INFORMATION_MESSAGE);
      else
        JOptionPane.showMessageDialog((JDialog)owner,splitString(u.gettext(s,"message")),u.gettext(s,"heading"),JOptionPane.INFORMATION_MESSAGE);
  }  
    

    final static String[] osxParseMacAddresses(String ipConfigResponse) throws
            ParseException {
        StringTokenizer tokenizer = new StringTokenizer(ipConfigResponse, "\n");
        String macadds[] = new String[]{};
        while (tokenizer.hasMoreTokens()) {
            String line = tokenizer.nextToken().trim();
            // see if line contains MAC address
            int macAddressPosition = line.indexOf("ether");
            if (macAddressPosition != 0) {
                continue;
            }
            String macAddressCandidate = line.substring(macAddressPosition + 6).trim();
            if (isMacAddress(macAddressCandidate)) {
                macadds = u.addString(macadds, macAddressCandidate);
            }
        }
        return macadds;
    }  
  
    final static String osxRunIfConfigCommand() throws IOException {
        Process p = Runtime.getRuntime().exec("ifconfig");
        InputStream stdoutStream = new BufferedInputStream(p.getInputStream());
        StringBuffer buffer = new StringBuffer();
        for (;;) {
            int c = stdoutStream.read();
            if (c == -1) {
                break;
            }
            buffer.append((char) c);
        }
        String outputText = buffer.toString();
        stdoutStream.close();
        return outputText;
    }    
  
  public static String[] removeString(String[] sa, int i) {
     if(sa == null) return null;
     short len = (short)sa.length;
     if(len<=i) return sa;
     String news[] = new String[len-1];
     System.arraycopy(sa,0,news,0,i);
     if(i<len-1) System.arraycopy(sa,i+1,news,i,len-i-1);
     return news;
 }
  
  public static String[][] removeStringArray(String[][] sa, int i) {
     if(sa == null) return null;
     short len = (short)sa.length;
     if(len<=i) return sa;
     String news[][] = new String[len-1][];
     System.arraycopy(sa,0,news,0,i);
     if(i<len-1) System.arraycopy(sa,i+1,news,i,len-i-1);
     return news;
 }  
  
  public static word[][] removeWordArray(word[][] sa, int i) {
     if(sa == null) return null;
     short len = (short)sa.length;
     if(len<=i) return sa;
     word news[][] = new word[len-1][];
     System.arraycopy(sa,0,news,0,i);
     if(i<len-1) System.arraycopy(sa,i+1,news,i,len-i-1);
     return news;
 }  

  public static void restart_Mac(String path){
    if(shark.macOS){
      try {
        Runtime.getRuntime().exec(new String[] {"open", path});
      }
      catch (Exception ee) {ee.printStackTrace();}
    }
  }  
  
  public static boolean screenResWidthMoreThan(int i){
      return sharkStartFrame.screendim.width>i;
  }

  public static boolean screenResHeightMoreThan(int i){
      return sharkStartFrame.screendim.height>i;
  }

 public static void setNewFilePermissions(File database){
    setNewFilePermissions(database, false);
 }

 public static void setNewFilePermissions(File database, boolean waitforchmod){
    try {
        String cmds[];
        if(shark.macOS){
            String chstr;
            if(database.isDirectory())chstr = "777";
            else chstr = "666";
            cmds = new String[] {"chmod", chstr, database.getAbsolutePath()};
        }
        else{
            cmds = new String[] {"CACLS",database.getAbsolutePath(),"/E", "/T", "/P","Users:F"};
        }
        Process p = Runtime.getRuntime().exec( (cmds));
        if(waitforchmod)p.waitFor();
    } catch (Exception e) {
    }
 }
 
 public static void setupMenuItemHeight(){
    menuItemHeight = (int)(new JMenuItem("dummy").getPreferredSize().getHeight() * 1.33f);
}
 
  public static int getMenuItemHeight(){
     JMenuItem jmi = new JMenuItem("dummy");
    return (int)(jmi.getPreferredSize().getHeight() * 1.33f);
}

 public static boolean setXMLAttribute(String filePath, String attribute, String value){
    Document doc = null;
    Node n;
    Element e;
    File file = new File(filePath);
    try {
      if (!file.canWrite() && shark.macOS) {
          setNewFilePermissions(file, true);
      }
      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      DocumentBuilder db = dbf.newDocumentBuilder();
      dbf.setValidating(false);
      doc = db.parse(file);
      n = (Node) doc.getDocumentElement();
      e = (Element) n;
      e.setAttribute(attribute, value);
      TransformerFactory tf = TransformerFactory.newInstance();
      Transformer transformer = tf.newTransformer();
      transformer.transform(new DOMSource(doc),
                            new StreamResult(new FileOutputStream(file, false)));
      return true;
    }
    catch (Exception trans) {
      return false;
    }
 }

 public static boolean setXMLElement(String filePath, String element, String value){
    if(filePath == null)return false;
    Document doc = null;
    Node n;
    File file = new File(filePath);
    try {
      if (!file.canWrite() && shark.macOS) {
          setNewFilePermissions(file, true);
      }
      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      DocumentBuilder db = dbf.newDocumentBuilder();
      dbf.setValidating(false);
      doc = db.parse(file);
      NodeList elementName = doc.getElementsByTagName(element);
      Element firstNameElement = (Element)elementName.item(0);
      firstNameElement.setTextContent(value);
      TransformerFactory tf = TransformerFactory.newInstance();
      Transformer transformer = tf.newTransformer();
      transformer.transform(new DOMSource(doc),
                            new StreamResult(new FileOutputStream(file, false)));
      return true;
    }
    catch (Exception trans) {
      return false;
    }
 }
 
 public static boolean setXMLElements(String filePath, String elements[], String values[]){
    if(filePath == null)return false;
    if((values==null || values.length==0)||(elements==null || elements.length==0)) return false;
    if(values.length!=elements.length)return false;
    Document doc = null;
    Node n;
    File file = new File(filePath);
    try {
      if (!file.canWrite() && shark.macOS) {
          setNewFilePermissions(file, true);
      }
      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      DocumentBuilder db = dbf.newDocumentBuilder();
      dbf.setValidating(false);
      doc = db.parse(file);
      for(int i = 0; i < elements.length; i++){
        NodeList elementName = doc.getElementsByTagName(elements[i]);
        Element firstNameElement = (Element)elementName.item(0);
        firstNameElement.setTextContent(values[i]);          
      }
      TransformerFactory tf = TransformerFactory.newInstance();
      Transformer transformer = tf.newTransformer();
      transformer.transform(new DOMSource(doc),
                            new StreamResult(new FileOutputStream(file, false)));
      return true;
    }
    catch (Exception trans) {
      return false;
    }
 } 

  
  public static String[] splitString(String s) {
    if(s==null) return new String[0];
    return splitString(s,'|');
  }

 public static String[] splitString(String s,char splitat) {
     return splitString(s,splitat, false);
 }

  public static String[] splitString(String s,char splitat, boolean removeEmpty) {  // split string
    if(s==null) return new String[0];
     int i=0, j=0,tot=0;
     String out[];
     while((j = (short)s.substring(i).indexOf(splitat)) >= 0) {++tot; i=(short)(i+j+1);}
     out = new String[tot+1];
     i = tot = 0;
     while((j = (short)s.substring(i).indexOf(splitat)) >= 0) {
        out[tot++] = s.substring(i,i+j);
        i=(short)(i+j+1);
     }
     out[tot] = s.substring(i);
     if(removeEmpty){
         for(i = out.length-1; i >=0; i--){
            if(out[i].trim().equals(""))out = removeString(out, i);
         }
     }
     return(out);
  }
  
    public static String swapString(String s, String from, String to) {
        char[] c = s.toCharArray();
        char[] c2 = to.toCharArray();
        String ret = "";
        for (int i = 0; i < c.length; i++) {
            int j;
            if (((j = from.indexOf(c[i]))) < 0) {
                return null;
            }
            ret = ret + c2[j];
        }
        return ret;
    }    
  
  public static void unZip(ZipFile zipFile, String destinationDir){
      if(!destinationDir.endsWith(shark.sep))destinationDir = destinationDir + shark.sep;
try {
			Enumeration<?> enu = zipFile.entries();
			while (enu.hasMoreElements()) {
				ZipEntry zipEntry = (ZipEntry) enu.nextElement();
				String name = zipEntry.getName();
				File file = new File(destinationDir+name);
				if (name.endsWith(shark.sep)) {
					file.mkdirs();
					continue;
				}

				File parent = file.getParentFile();
				if (parent != null) {
					parent.mkdirs();
				}

				InputStream is = zipFile.getInputStream(zipEntry);
				FileOutputStream fos = new FileOutputStream(file);
				byte[] bytes = new byte[1024];
				int length;
				while ((length = is.read(bytes)) >= 0) {
					fos.write(bytes, 0, length);
				}
				is.close();
				fos.close();

			}
			zipFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}      
      
  }
  
    static final String[] windowsParseMacAddresses(String ipConfigResponse, boolean dowritelog) throws
            ParseException {
        String macs[] = new String[]{};
        StringTokenizer tokenizer = new StringTokenizer(ipConfigResponse, "\n");
        while (tokenizer.hasMoreTokens()) {
            String line = tokenizer.nextToken().trim();
            // see if line contains MAC address
            int macAddressPosition = line.indexOf(":");
            if (macAddressPosition <= 0) {
                continue;
            }
            String macAddressCandidate = line.substring(macAddressPosition + 1).trim();
            
            if (dowritelog) {
                writelog(new String[]{macAddressCandidate});
            }
            if (isMacAddress(macAddressCandidate)) {
                if (dowritelog) {
                    writelog(new String[]{"accepted: " + macAddressCandidate});
                }
                if (u.findString(macs, macAddressCandidate) < 0) {
                    macs = u.addString(macs, macAddressCandidate);
                }
                continue;
            }
        }
        return macs;
    }  
  
    final static String windowsRunIpConfigCommand() throws IOException {
        Process p = Runtime.getRuntime().exec("ipconfig /all");
        InputStream stdoutStream = new BufferedInputStream(p.getInputStream());
        
        StringBuffer buffer = new StringBuffer();
        for (;;) {
            int c = stdoutStream.read();
            if (c == -1) {
                break;
            }
            buffer.append((char) c);
        }
        String outputText = buffer.toString();
        stdoutStream.close();
        return outputText;
    }  
    
    
    
    static public void clearFile(String path) {
        try{
            FileWriter fwOb = new FileWriter(path, false); 
            PrintWriter pwOb = new PrintWriter(fwOb, false);
            pwOb.flush();
            pwOb.close();
            fwOb.close();
        }
        catch(Exception e){}
    }
    
    static public void writeFile(String path, String data[]){
//        boolean donefirst = false;
        for(int i = 0; i < data.length; i++) {
            
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path, true))) {
            String s;
            
            
            s = data[i];
            bw.write(s);
//            bw.write((donefirst?",":"")+"\""+s+"\"");
 //           donefirst = true;
            bw.newLine();
            bw.flush();
        } catch(IOException ex) {
        int o;
        o = 9;
        }  
    }
    }    
    
    static void writelog(String s[]) {
        PrintWriter write = null;
        try {
            File home = new File(sharkStartFrame.sharedPathplus + "activation.log");
            write = new PrintWriter(new FileWriter(home, true));
            Calendar cal = new GregorianCalendar();
            String date = String.valueOf(cal.get(Calendar.DAY_OF_MONTH)) + "/"
                    + String.valueOf(cal.get(Calendar.MONTH) + 1) + "/"
                    + String.valueOf(cal.get(Calendar.YEAR))
                    + "  "
                    + String.valueOf(cal.get(Calendar.HOUR_OF_DAY)) + ":"
                    + String.valueOf(cal.get(Calendar.MINUTE)) + ":"
                    + String.valueOf(cal.get(Calendar.SECOND)) + ":"
                    + String.valueOf(cal.get(Calendar.MILLISECOND));
            for (int i = 0; i < s.length; i++) {
                write.println(date + "  " + s[i]);
            }
        } catch (Exception e) {
        } finally {
            if (write != null) {
                write.close();
            }
        }
    }
}
