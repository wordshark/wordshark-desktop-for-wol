
package shark;

import java.io.*;
import java.text.*;
import javax.swing.*;
import java.util.*;
import javax.swing.text.AbstractDocument.*;
import java.io.File;
/**
 * <p>Title: Shark </p>
 * <p>Execution starts here, class <code>shark</code>:-
 * <li>Contains main().
 * <li>Flags for asceraining whether network, production and/or special functions are on.
 * <li>Creates a new instance of class sharkStartFrame </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: White Space Ltd</p>
 * @author Roger Burton
 * @version 1.0
 */

public class shark {
  static final String LICENCETYPE_DVD = "CD";
  static final String LICENCETYPE_STANDALONEACTIVATION = "SA";
  static final String LICENCETYPE_NETWORK = "NW";
  static final String LICENCETYPE_NETWORKACTIVATION = "NA";
  static final String LICENCETYPE_USB = "UB";
  static final String LICENCETYPE_USB_HOME = "UH";

  static final String LICENCETYPE_STANDALONEACTIVATION_HOME = "SH";  
  static final String LICENCENAME_CD_EN = " DVD";
  static final String LICENCENAME_STANDALONEACTIVATIONHOME_EN = " Home Download";
  static final String LICENCENAME_STANDALONEACTIVATION_EN = " Download";
  static final String LICENCENAME_STANDALONEACTIVATIONPREVIOUS_EN = " Stand-alone";
  static final String LICENCENAME_NETWORK_EN = " Network";
  static final String LICENCENAME_NETWORKACTIVATION_EN = " Network";
  static final String LICENCENAME_USB_EN = " USB";
  static final String LICENCENAME_USBHOME_EN = " Home USB";
  static final String LICENCENAME_CD_NL = "";
  static final String LICENCENAME_STANDALONEACTIVATIONHOME_NL = " Home Download";
  static final String LICENCENAME_STANDALONEACTIVATION_NL = " Download";
  static final String LICENCENAME_NETWORK_NL = " Network";
  static final String LICENCENAME_NETWORKACTIVATION_NL = " Network";
  static final String LICENCENAME_USB_NL = " USB";
  static final String LICENCENAME_USBHOME_NL = " Home USB";

  static final String LANGUAGE_EN = "en";
  static final String LANGUAGE_NL = "nl";
  static String publicPath;
  static String sharedPath;
  static String checkcd;
  public static boolean phonicshark = false;
  /**
   * True if a network is in use.
   */
  static boolean network=false;
  // remember complie 1.7 for networks, 1.6 for others.
  // leave java7_base in for networks, comment out for others
  
  static boolean network_serverside=false;
  static boolean network_RM = !network_serverside && network && false;
  
  /**
   * True when testing is not being done.
   */
  static boolean production=false;
  /**
   *
   * Allows alteration 
   * of wordshark-public files
   */
  static boolean specialfunc=true;

  static boolean doTopicIndex = false; 
  public static boolean showPhotos;
  public static boolean wantPublicSplits = true;
  public static boolean doImageScreenshots = false;
  
  static String licenceType = LICENCETYPE_DVD;
  static String language = LANGUAGE_EN;
  public static String versionNo = "5";
  public static String versionNoDetailed = "6.128.00";
  public static String versionLatestCritical = "6.100.00";
  static boolean testing=true;

  static boolean macusb2 = true;
  static boolean ruthTest = false;
  static boolean paulTest = false;
  static int maxUsers_Students;
  static int maxUsers_Admins;
  public static String mainFolder = "";
  static String sep = File.separator;
  static String otherProgram=(language==LANGUAGE_EN)?"Numbershark":"Cijferhaai";
  static String jarName = "wordshark.jar";
  public static String PUBLICPREFIX = "WS";
  static String companyName = "White Space Ltd";
  
  public static String PUBLICSUFFIX_NETWORK = "N";
  public static String PUBLICSUFFIX_STANDALONEHOMEACTIVATION = "Home";
  public static String PUBLICSUFFIX_STANDALONEACTIVATION = "SA";    
  
//  static boolean singleuserMult;
  public static String ACTIVATE_PREFIX = language.equals(LANGUAGE_NL)?"DC": (phonicshark?"PC":"WC");
  static String USBprefix = language.equals(LANGUAGE_NL)?"WH":(phonicshark?"PS":"WS");
  
  static int HIGHUSERS = 9999999;
  static int restrictedHomeNoStudents = 3;
  static int restrictedHomeNoAdmins = 2;
  static int MAXUSERS_STANDALONEACTIVATIONHOME_STUDENT = restrictedHomeNoStudents;
  static int MAXUSERS_STANDALONEACTIVATIONHOME_ADMIN = restrictedHomeNoAdmins;
  static int MAXUSERS_STANDALONEACTIVATION_STUDENT = HIGHUSERS;
  static int MAXUSERS_STANDALONEACTIVATION_ADMIN = HIGHUSERS;
  static int MAXUSERS_USB_STUDENT = HIGHUSERS;
  static int MAXUSERS_USB_ADMIN = HIGHUSERS;
  static int MAXUSERS_USBHOME_STUDENT = restrictedHomeNoStudents;
  static int MAXUSERS_USBHOME_ADMIN = restrictedHomeNoAdmins;
  static int MAXUSERS_DVD_STUDENT  = HIGHUSERS;
  static int MAXUSERS_DVD_ADMIN = HIGHUSERS;
  static int MAXUSERS_NETWORK_STUDENT = HIGHUSERS;
  static int MAXUSERS_NETWORK_ADMIN = HIGHUSERS;
  static int MAXUSERS_NETWORKACTIVATION_STUDENT = HIGHUSERS;
  static int MAXUSERS_NETWORKACTIVATION_ADMIN = HIGHUSERS;    
  
  static boolean debug=false;
  static boolean showshared=true;
  /**
  * True when an expiry date has been set
  */
  static boolean expires=false;
  /**
  * expiryDate[0] is the day
  * expiryDate[1] is the month
  * expirtDate[2] is the year
  */
  static int expiryDate[]= new int[]{1,3,2008};
  /**
   * The release CD's label. Needed for Macintosh
   */
  private static String CdLabel;
  public static boolean singledownload=false;
//  static final String LICENCETYPE_SINGLE = "SA";  // DO NOT SET THIS 
  /**
   * True when running on a Macintosh operating system
   */
  public static boolean macOracleJava = true;
  public static boolean macOS =  macOracleJava?System.getProperty("os.name").toLowerCase().startsWith("mac os x"):System.getProperty("mrj.version") != null;
  public static boolean linuxOS = System.getProperty("os.name").toLowerCase().indexOf("linux") >= 0;
  public static String programName;
  static boolean numbershark = false;
  


  
  public static String xmlNetworkFile;
  public static String linuxMainFolderPath;
  public static boolean wantTutVids = !phonicshark;
//   public static int tooltipdelay = 650;
  public static int tooltipdelay = 750;
  static boolean patching = !network_RM;
  static String usb2VolName = USBprefix+"-SHARED";
  static String usbSerial;
  static boolean usbTest = false;
  static boolean wantTeachers = network;
  static boolean wantTemporaryStudents=false;
  static String photoFolderPath;
  static String showPhotoPrefix = "photos:";

  //Construct the application
  /**
   * Class constructor, starts construction of whole application:-
   * <li>Gives the UIManager user interface settings
   * <li>Creates new instance of <code>sharkStartFrame</code>.
   * @throws ClassNotFoundException if UIManager class not found.
   */
  public shark() {
    try  {
       String mytooltip = "tooltip";
       UIManager.put("ToolTipUI",mytooltip);
       UIManager.put(mytooltip, Class.forName("shark.tooltip_base"));
       String mytree = "tree";
       UIManager.put("TreeUI",mytree);
       UIManager.put(mytree, Class.forName("javax.swing.plaf.metal.MetalTreeUI"));
       java.awt.Color selcol = new java.awt.Color(204,204,255);
       UIManager.getDefaults().put("Tree.selectionBackground", selcol);
       UIManager.getDefaults().put("List.selectionBackground", selcol);
       UIManager.put("Tree.collapsedIcon", new javax.swing.plaf.IconUIResource(new ImageIcon(java.awt.Toolkit.getDefaultToolkit().createImage(getClass().getResource("/treePlus_il16.png"))) ));
       UIManager.put("Tree.expandedIcon", new javax.swing.plaf.IconUIResource(new ImageIcon(java.awt.Toolkit.getDefaultToolkit().createImage(getClass().getResource("/treeMinus_il16.png"))) ));
    }
    catch (ClassNotFoundException e){
        noise.beep();
    };
    ToolTipManager.sharedInstance().setReshowDelay(tooltipdelay);
    ToolTipManager.sharedInstance().setDismissDelay(60000);
    ToolTipManager.sharedInstance().setInitialDelay(tooltipdelay);
    if(debug)okmess(programName+" Debug", "3");
    u2_base.setupMenuItemHeight();
    new sharkStartFrame();
    
  }


  /**
   * main() - creates instance of shark and sets global flags.
   * @param args
   */
  static public void main(String[] args) {
    String stickID = "@2B93C79G43DJ7V@".toUpperCase();
    stickID = stickID.replaceAll("@", "");
    if(licenceType.equals(LICENCETYPE_USB)){
        if(args.length==1 && args[0].equals("usbtest")){
            try {
            BufferedWriter out = new BufferedWriter(new FileWriter(System.getProperty("user.dir")+File.separator+"store"+File.separator+"usbtest.txt"));
            out.write(stickID);
            out.close();
            }
            catch (IOException e)
            { }
            System.exit(0);
            return;
        }
    }
    if(macOS && licenceType.equals(LICENCETYPE_NETWORKACTIVATION))
        licenceType = LICENCETYPE_NETWORK;
    setMaxUsers();
    programName="";
    if(language.equals(LANGUAGE_EN)) programName = phonicshark?"Phonicshark":"Wordshark";
    else if(language.equals(LANGUAGE_NL)) programName = "Woordenhaai";
    CdLabel = programName.toUpperCase()+versionNo.toUpperCase();
    mainFolder = mainFolder + getVersionType(licenceType);
    mainFolder = programName+" "+versionNo+mainFolder;
    String ch = (new SimpleDateFormat(shark.licenceType.equals(shark.LICENCETYPE_NETWORK)?"ddyyMM":"yyMMdd")).format(new Date());
    if(network && new File(args[0]).isDirectory()) ch = null;
    if(!macOS && production && ch != null) { // adjust in case time zone problem
      if(!new File(decrypt(dehex(args[0]),ch)).isDirectory()) {
        Calendar d = new GregorianCalendar();
        d.add(d.DATE,1);
        ch = (new SimpleDateFormat(network?"ddyyMM":"yyMMdd")).format(d.getTime());
        if(!new File(decrypt(dehex(args[0]),ch)).isDirectory()) {
          d.add(d.DATE,-2);
          ch = (new SimpleDateFormat(network?"ddyyMM":"yyMMdd")).format(d.getTime());
          if(!new File(decrypt(dehex(args[0]),ch)).isDirectory()) {
             ch = (new SimpleDateFormat(network?"ddyyMM":"yyMMdd")).format(new GregorianCalendar().getTime());
                          // no good - just continue with old one
          }
        }
      }
    }
    showPhotos = false; //args[args.length-1].startsWith(showPhotoPrefix);
    
    if(licenceType.equals(LICENCETYPE_USB)){
        for(int i = 0; i < args.length; i++){
            if(args[i].equals("specialfunc"))specialfunc=true;
            else if(args[i].equals("debug")) debug = true;
//            else if(args[i].equals("showshared")) showshared = true;
            else if(args[i].equalsIgnoreCase("usbtest"))usbTest=true;
        }
    }
    else{
        if (args.length == 4) {
          String endparam = (isLicenceActivated()||ch==null||macOS?args[args.length-1]:decrypt(dehex(args[args.length-1]),ch));
          if(endparam.equalsIgnoreCase("specialfunc"))
            specialfunc=true;
          else if(endparam.equalsIgnoreCase("debug"))
            debug=true;
//          else if(endparam.equalsIgnoreCase("showshared"))
//            showshared=true;
           else {
//              okmess(programName.toUpperCase(),"Error code 5: Incorrect runtime parameter for "+programName.toUpperCase());
              if(!showPhotos)OptionPane_base.getErrorMessageDialog(null, 5, "Incorrect runtime parameter for "+programName.toUpperCase(), OptionPane_base.ERRORTYPE_EXIT);
           }
        }
        else if (production && args.length !=3) {
//              okmess(programName.toUpperCase(),"Error code 6: Incorrect parameters for "+programName.toUpperCase());
              OptionPane_base.getErrorMessageDialog(null, 6, "Incorrect parameters for "+programName.toUpperCase(), OptionPane_base.ERRORTYPE_EXIT);
        }
      }

    if(showPhotos)photoFolderPath = args[args.length-1].substring(showPhotoPrefix.length());
    if(production && linuxOS){
//      okmess(programName.toUpperCase(),"Error code 14: This version of "+shark.programName+"cannot be run on Linux");
      OptionPane_base.getErrorMessageDialog(null, 14, "This version of "+shark.programName+"cannot be run on Linux", OptionPane_base.ERRORTYPE_EXIT);
    }
    if(debug)okmess(programName+" Debug", "1");
    if(production && licenceType.equals(LICENCETYPE_DVD)) {
      if(macOS){
          checkcd = sep+"Volumes"+sep+CdLabel+sep+programName+" "+versionNo + getVersionType(licenceType)+sep+programName+" "+versionNo+getVersionType(licenceType)+".app";
          if(!new File(checkcd).exists()) {
            OptionPane_base.getErrorMessageDialog(null, 1, programName+" cannot be run without the DVD in place", OptionPane_base.ERRORTYPE_EXIT);
          }
      }
      else{
        checkcd = decrypt(dehex(args[2]), ch) +  ":"+ sep +programName+versionNo+"l.exe";
      }
    }
    if(licenceType.equals(LICENCETYPE_USB)){
        WebAuthenticateBase_base wab = new WebAuthenticateBase_base();
//        usbSerial = wab.makeCode(wab.wholekey, stickID, 6);
        usbSerial = wab.makeCode(u2_base.wholekey, stickID, 6);
        publicPath = "null";
        sharedPath = "null";
        if(macOS){
            MacLock.ldLibrary();
            try{
                Vector possibleSerials = osxGetUSBSerialCommand();
                for(int i = 0; i < possibleSerials.size(); i++){
                    String scomp = ((String)possibleSerials.get(i)).toUpperCase();
                    if(isUSBSerialOk(stickID, scomp)){
                        if(macusb2){
                            File f;
                            if(!(f = new File(sep + "Volumes" + sep + usb2VolName + sep + programName.toLowerCase()+"-shared")).exists()){
                                f.mkdir();
                            }
                            sharedPath = sep + "Volumes" + sep + usb2VolName + sep +programName.toLowerCase()+"-shared";
                            publicPath = twoPartitionGetPublicPath();
                        }
                        else{
                            sharedPath = getLocalPathForFolder(programName.toLowerCase()+"-shared");
                            String pub = sharedPath.substring(0, sharedPath.indexOf("-shared"));
                            publicPath = pub.substring(0, pub.lastIndexOf(File.separator)+1)+programName + " " + versionNo+
                                    File.separator + programName.toLowerCase()+"-public"+File.separator;
                        }
                        break;
                    }
                }
            }
            catch(Exception ee){}
        }
        else{
            String passedid = decrypt(dehex(args[2]),ch);
            String passedid2 = passedid;
            if(passedid2.startsWith("@"))passedid2 = passedid2.substring(1);
            if(passedid2.endsWith("@"))passedid2 = passedid2.substring(0, passedid2.length()-1);
            if(isUSBSerialOk(stickID, passedid2)){
                publicPath = !production?args[0]:decrypt(dehex(args[0]),ch+passedid);
                sharedPath = !production?args[1]:decrypt(dehex(args[1]),ch+passedid);
            }
        }
      }
    else{
    if(macOS || linuxOS){
      if(network){
        String xmlfile = "ServerPath.xml";
        if(macOS){
          MacLock.ldLibrary();
          xmlNetworkFile = getLocalPathForFolder(programName.toLowerCase()+"-config");
          new ServerPath_base(xmlfile, xmlNetworkFile);
          xmlNetworkFile = xmlNetworkFile.concat(xmlfile);
        }
        else{
          String etcfile = programName.toLowerCase()+"-"+versionNo+File.separator+xmlfile;
          //for Librus micros
          if((new File("/boot/rootcopy/etc/"+etcfile).exists()))
            xmlNetworkFile = "/boot/rootcopy/etc/"+etcfile;
          //for normal Linux
          else if((new File("/etc/"+etcfile).exists()))
            xmlNetworkFile = "/etc/"+etcfile;
          else {
            okmess(programName, "Cannot find xml file");
            System.exit(0);
          }
          String pathName = shark.class.getName().replace('.', '/') + ".class";
          ClassLoader loader = shark.class.getClassLoader();
          java.net.URL url= (loader != null) ? loader.getResource(pathName):ClassLoader.getSystemResource(pathName);
          String s = url.getPath();
          String s2 = shark.programName.toLowerCase()+"-"+shark.versionNo+File.separator;
          linuxMainFolderPath = s.substring(s.indexOf(File.separator), s.indexOf(s2)+s2.length());
        }
          // if first run
        if (((sharedPath = u2_base.getXMLAttribute(xmlNetworkFile, "serverpath")).equals("null")) ||
                (sharedPath==null) ||
                (!new File(sharedPath).exists())){
          chooseshared_base cs = new chooseshared_base(false);
          cs.setVisible(true);
          sharedPath = u2_base.getXMLAttribute(xmlNetworkFile, "serverpath");
          if(sharedPath==null) sharedPath="";
        }
      }
      else{
          String dir_ws = getProgramDataPath() + sep + companyName;
          String dir_progfolder = dir_ws + sep + programName + " " + versionNo + LICENCENAME_STANDALONEACTIVATIONPREVIOUS_EN;
          sharedPath = dir_progfolder + sep + programName.toLowerCase() + "-shared";
          String dir_tmp = sharedPath+sep+"tmp";
          File sh;
          if(!isLicenceActivated()){
              if (!new File(dir_tmp).exists()) {
                  mkFolder(dir_ws);
                  if(!(new File(dir_ws)).exists()){
                    String mess;
                    if(language.equals("nl")) mess = "Plaatsen van de shared folder (met gebruikersgegevens).\nEr kan om een beheerders wachtwoord gevraagd worden.";
                    else  mess = "Setting up the shared folder.\nYou may be asked for an adminstrator password.";
                    okmess(programName.toUpperCase(), mess);
                    createSharedMac(dir_ws);
                  }
                  mkFolder(dir_progfolder);
                  mkFolder(sharedPath);
                  mkFolder(dir_tmp);
                  sh = new File(dir_tmp);
                  if (!sh.exists()) {
//                    okmess(programName.toUpperCase(), "Error code 18: Mac initialization error\nFor the first run of the program please use an administrator account.");
                    OptionPane_base.getErrorMessageDialog(null, 18, "Mac initialization error\nFor the first run of the program please use an administrator account.", OptionPane_base.ERRORTYPE_EXIT);
                  }
              }
              if (!(sh = new File(dir_tmp+sep+"cp001")).exists()) {
                try {
                    sh.createNewFile();
                    u2_base.setNewFilePermissions(sh, true);
                } catch (Exception e) {}
              }
          }
          else {
              String DPath = getDeactivationFolder();
              if (!(sh = new File(sharedPath)).exists()) {
                  mkFolder(dir_ws);
                  if(!(sh = new File(dir_ws)).exists()){
                    String mess;
                    if(language.equals("nl")) mess = "Plaatsen van de shared folder (met gebruikersgegevens).\nEr kan om een beheerders wachtwoord gevraagd worden.";
                    else  mess = "Setting up the shared folder.\nYou may be asked for an adminstrator password.";
                    okmess(programName.toUpperCase(), mess);
                    String arguments[];
                    if(!new File(DPath).exists())
                        arguments = new String[]{sh.getAbsolutePath(), DPath};
                    else arguments = new String[]{sh.getAbsolutePath()};
                    createSharedMac(arguments);
                  }
                  mkFolder(dir_progfolder);
                  mkFolder(sharedPath);
                  if (!(new File(sharedPath)).exists()) {
//                    okmess(programName.toUpperCase(), "Error code 18: Mac initialization error\nFor the first run of the program please use an administrator account");
                    OptionPane_base.getErrorMessageDialog(null, 18, "Mac initialization error\nFor the first run of the program please use an administrator account.", OptionPane_base.ERRORTYPE_EXIT);
                  }
              }
              if(!new File(DPath).exists())
                createSharedMac(DPath);
          }
      }
      publicPath = getLocalPathForFolder(programName.toLowerCase()+"-public");
    }
    else{
        if(!isLicenceActivated()){
            publicPath = (!production||network)?args[0]:decrypt(dehex(args[0]),ch);
            sharedPath = (!production||network)?args[1]:decrypt(dehex(args[1]),ch);
        }
        else{
            publicPath = args[0];
            sharedPath = args[1];
        }
    }
    }
     
    if(debug)okmess(programName+" Debug", "2");
    File shareddir = new File(sharedPath);
    boolean publicexists = new File(publicPath).isDirectory();
    boolean sharedexists = shareddir.isDirectory();
    String dbug = "";
    if(debug){
      dbug+="|Info:|"+String.valueOf(sharedPath)+"|"+String.valueOf(publicPath);
    }
    String subcode = "";
    if(!publicexists && !sharedexists) subcode = ".1";
    else if(!sharedexists) subcode = ".2";
    else if(!publicexists) subcode = ".3";
    if(!subcode.equals("")){
      if(production) {
        if(!network){
            if(licenceType.equals(LICENCETYPE_USB))
//                okmess(programName.toUpperCase(), "Error code 15: Please refer to the "+programName+" "+versionNo+" USB instruction manual");
                OptionPane_base.getErrorMessageDialog(null, 15, "Please refer to the "+programName+" "+versionNo+" USB instruction manual.", OptionPane_base.ERRORTYPE_EXIT);
            else
//                okmess(programName.toUpperCase(),"<html>Error code 7"+subcode+": Unable to find a "+programName+" directory. Please contact support."+dbug+"</html>");
                OptionPane_base.getErrorMessageDialog(null, Float.parseFloat("7"+subcode), "Unable to find a "+programName+" directory. Please contact support."+dbug, OptionPane_base.ERRORTYPE_EXIT);
        }
        else 
//            okmess(programName.toUpperCase(),"<html>Error code 8"+subcode+": Unable to find a "+programName+" directory. Please contact support."+dbug+"</html>");
            OptionPane_base.getErrorMessageDialog(null, Float.parseFloat("8"+subcode), "Unable to find a "+programName+" directory. Please contact support."+dbug, OptionPane_base.ERRORTYPE_EXIT);
      }
      if(!macOS){
        publicPath = args[0];
        sharedPath = args[1];
      }
    }
    if(production && shareddir.getPath().indexOf("-shared")<0){
      if(network)
//        okmess(programName.toUpperCase(),"<html>Error code 9:<br><br>Working directory is not correctly set to "+programName.toLowerCase()+"-shared folder.<br>Please refer to network install booklet.<br></html>");
        OptionPane_base.getErrorMessageDialog(null, 9, "Working directory is not correctly set to "+programName.toLowerCase()+"-shared folder.|Please refer to network install booklet.", OptionPane_base.ERRORTYPE_EXIT);
      else
//        okmess(programName.toUpperCase(),"<html>Error code 9:<br><br>"+programName+" needs to be installed by a computer administrator.<br>Working directory is not correctly set to "+programName.toLowerCase()+"-shared folder.<br></html>");
        OptionPane_base.getErrorMessageDialog(null, 9, programName+" needs to be installed by a computer administrator.<br>Working directory is not correctly set to "+programName.toLowerCase()+"-shared folder.", OptionPane_base.ERRORTYPE_EXIT);
    }
    if(macOS && production && 
//              !network && isLicenceStandard()){
        licenceType.equals(LICENCETYPE_DVD)){
      try {
        String s = getLocalPathForApp("Contents/cp001.app");
        File f = new File(sharedPath+sep+"tmp"+sep+"cp001");
        //clear the file
        FileOutputStream fos = new FileOutputStream(f);
        fos.close();
        Process p = Runtime.getRuntime().exec(new String[] {"open",s});
        p.waitFor();
      }
      catch (Exception ee) {ee.printStackTrace();}
    }
    if(expires){
      Calendar now = Calendar.getInstance();
      Calendar expiresOn = Calendar.getInstance();
      expiresOn.set(expiryDate[2], expiryDate[1]-1, expiryDate[0]);
      if (now.after(expiresOn)) {
        okmess(programName.toUpperCase(),
             "This version of "+programName+" has now expired");
        System.exit(0);
      }
      else{
        okmess(programName.toUpperCase(),
               "This version of "+programName+" expires on " + expiryDate[0] + "/" +
               expiryDate[1] + "/" + expiryDate[2]);
      }
    }
    new shark();
  }

  public static String getVersionType(String t) {
      return getVersionType(t, true);
  }  
  
  public static String getVersionType(String t, boolean wantLeadingSpace) {
    if(t==null)t = licenceType;
    String ret = null;
    if(t.equals(LICENCETYPE_STANDALONEACTIVATION_HOME)){
        if(language.equals(LANGUAGE_NL)) ret = LICENCENAME_STANDALONEACTIVATIONHOME_NL;
        else ret = LICENCENAME_STANDALONEACTIVATIONHOME_EN;                  
    }
    else if(t.equals(LICENCETYPE_STANDALONEACTIVATION)){
        if(language.equals(LANGUAGE_NL)) ret = LICENCENAME_STANDALONEACTIVATION_NL;
        else ret = LICENCENAME_STANDALONEACTIVATION_EN;            
    }
    else if(t.equals(LICENCETYPE_DVD))
    {
        if(language.equals(LANGUAGE_NL)) ret = LICENCENAME_CD_NL;
        else ret = LICENCENAME_CD_EN;
    }
    else if(t.equals(LICENCETYPE_NETWORK))
    {
        if(language.equals(LANGUAGE_NL)) ret = LICENCENAME_NETWORK_NL;
        else ret = LICENCENAME_NETWORK_EN;
    }
    else if(t.equals(LICENCETYPE_NETWORKACTIVATION))
    {
        if(language.equals(LANGUAGE_NL)) ret = LICENCENAME_NETWORKACTIVATION_NL;
        else ret = LICENCENAME_NETWORKACTIVATION_EN;
    }
    else if(t.equals(LICENCETYPE_USB))
    {
        if(language.equals(LANGUAGE_NL)) ret = LICENCENAME_USB_NL;
        else ret = LICENCENAME_USB_EN;
    }
    else if(t.equals(LICENCETYPE_USB_HOME))
    {
        if(language.equals(LANGUAGE_NL)) ret = LICENCENAME_USBHOME_NL;
        else ret = LICENCENAME_USBHOME_EN;
    }    
    else ret = null;
    if(ret != null && !wantLeadingSpace) ret = ret.trim();
    return ret;
  }


  static String toret(char[] aa) {
    char fullstr2[] =  "6HaLF8thwGARC2U0uZSM1mcyOIJvYdKzo3nlQVex7T9ikbN".toCharArray();
    int len = aa.length;
    int len2 = fullstr2.length;
    short i;
    for(i=0;i<len;i++){
      int y = 4687-aa[i];
      int yneg = y*-1;
      int j;
      if(yneg>y)j=yneg;
      else j=y;
      aa[i] = fullstr2[j%(len2)];
    }
    return String.valueOf(aa);
  }

  static char[] _encrypt(char[] aa) {
    String ch = (new java.text.SimpleDateFormat("yyMMdd")).format(new Date());
    int len = aa.length;
    char kk[] = ch.toCharArray();
    short k=0;
    short i;
    for(i=0;i<6;++i) {
      k += kk[i];
    }
    for(i=0;i<len;++i) {
      int jh = k + (i*kk[i%6]) & 0x00ff;
      if(jh>110)jh = (jh%110);
      if(jh==0)jh = 6;
      aa[i] = (char)jh;
    }
    return aa;
  }
//endPRtoupdate^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  /**
   * Changes parameter from hex into a string suitable for decrypting
   * @param arg String containing one of the parameters passed into the main method
   * @return String ready for decryption
   */
  static String dehex(String arg) {
     if((arg.length() % 2) != 0) return "XXXXXXX";
     char s[] = new char[arg.length()/2];
     for(short i=0;i<arg.length();i+=2) {
        s[i/2] = (char)(((arg.charAt(i)-'A')<<4) + (arg.charAt(i+1)-'A'));
     }
     return new String(s);
  }
  /**
   * Decrypts the argument passed to it.
   * @param arg String to be decrypted.
   * @param key The key used.
   * @return String with decrypted arguments.
   */
  static String decrypt(String arg,String key) {
     char kk[] = key.toCharArray();
     char aa[] = arg.toCharArray();
     short k=0;
     short i;
//startPR2010-01-29^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//     for(i=0;i<6;++i) k += kk[i];
//     for(i=0;i<aa.length;++i) aa[i] ^= (char)((k + i*3 + kk[i%6]) & 0x00ff);
     for(i=0;i<key.length();++i) k += kk[i];
     for(i=0;i<aa.length;++i) aa[i] ^= (char)((k + i*3 + kk[i%key.length()]) & 0x00ff);
//endPRtoupdate^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     return new String(aa);
  }
  /**
   * Decrypts the argument passes to it and then calls <code>stripchek</code>
   * to strip any check digits.
   * @param arg String to be decrypted
   * @param key
   * @return String with decrypted arguments and no check digits.
   */
  static String decrypt1(String arg,String key) {
     char kk[] = key.toCharArray();
     char aa[] = arg.toCharArray();
     short k=0;
     int len2 = kk.length;
     short i;
     for(i=0;i<len2;++i) k += kk[i];
     for(i=0;i<aa.length;++i) aa[i] ^= (char)((k + i*3 + kk[i%len2]) & 0x00ff);
     return stripchek(new String(aa));
  }
  /**
   * Strips the check digits off the end of the string.(check digits are used to
   * stop people tampering with the string).
   * @param ss String passed for removal of chck bits.
   * @return String ss without the check bits.
   */
  static String stripchek(String ss) {
     int hash=0;
     char cc[] = ss.toCharArray();
     int len = cc.length,i;
     for(i=0;i<len-4;++i) hash += (cc[i]*i) + ((cc[i]*(i+1))<<8) + ((cc[i]*(i+2))<<16)
                                  + ((cc[i]*(i+3))<<24);
     int hash2 = (((int)cc[len-4])<<24)
                 | (((int)cc[len-3])<<16)
                 | (((int)cc[len-2])<<8)
                 | ((int)cc[len-1]);
     if (hash  != hash2) return null;
     return ss.substring(0,len-4);
  }
  static void okmess(String h,String s) {
    JOptionPane.showMessageDialog(null,new String[]{s},h,JOptionPane.INFORMATION_MESSAGE);
  }
  
   static boolean licenceLimitsUsers(){
      return maxUsers_Students!=HIGHUSERS;
  }
   
  static String getPublicProductSuffix(){
      switch (licenceType){
          case LICENCETYPE_STANDALONEACTIVATION : 
              if(shark.singledownload){
                return PUBLICSUFFIX_STANDALONEHOMEACTIVATION;
              }
              else{
                return PUBLICSUFFIX_STANDALONEACTIVATION;
              }
          case LICENCETYPE_NETWORKACTIVATION: 
              return PUBLICSUFFIX_NETWORK;
      } 
      return "";
  }   
  
  static void setMaxUsers(){
      switch (licenceType){
          case LICENCETYPE_STANDALONEACTIVATION : 
              if(shark.singledownload){
                maxUsers_Students = MAXUSERS_STANDALONEACTIVATIONHOME_STUDENT;
                maxUsers_Admins = MAXUSERS_STANDALONEACTIVATIONHOME_ADMIN;
              }
              else{
                maxUsers_Students = MAXUSERS_STANDALONEACTIVATION_STUDENT;
                maxUsers_Admins = MAXUSERS_STANDALONEACTIVATION_ADMIN;
              }
              break;
          case LICENCETYPE_DVD: 
              maxUsers_Students = MAXUSERS_DVD_STUDENT;
              maxUsers_Admins = MAXUSERS_DVD_ADMIN;
              break;
          case LICENCETYPE_USB: 
              maxUsers_Students = MAXUSERS_USB_STUDENT;
              maxUsers_Admins = MAXUSERS_USB_ADMIN;
              break;
          case LICENCETYPE_USB_HOME: 
              maxUsers_Students = MAXUSERS_USBHOME_STUDENT;
              maxUsers_Admins = MAXUSERS_USBHOME_ADMIN;
              break;
          case LICENCETYPE_NETWORK: 
              maxUsers_Students = MAXUSERS_NETWORK_STUDENT;
              maxUsers_Admins = MAXUSERS_NETWORK_ADMIN;
              break;
          case LICENCETYPE_NETWORKACTIVATION: 
              maxUsers_Students = MAXUSERS_NETWORKACTIVATION_STUDENT;
              maxUsers_Admins = MAXUSERS_NETWORKACTIVATION_ADMIN;
              break;
      }      
  }  
   

  static String getLocalPathForFolder(String s){
    if(macOS){
        if(macOracleJava){
            try{
                return getAppBundlePath(true)+sep+s+sep;
            }
            catch(Exception e){
                return System.getProperty("user.dir")+File.separator+s+File.separator;
            }
        }
        else
            return System.getProperty("user.dir")+File.separator+s+File.separator;
    }
    else if (linuxOS){
      return shark.linuxMainFolderPath+s+File.separator;
    }
    return null;
  }
  
  
  static String getLocalPathForApp(String s){
    if(macOS){
        if(macOracleJava){
            try{
//                String ss = System.getProperty("java.library.path");
//                String appstr = ".app";
//                ss = ss.substring(0, ss.indexOf(appstr)+ appstr.length());
//                return ss+sep+s+sep;
                return getAppBundlePath(false)+sep+s+sep;
            }
            catch(Exception e){
                return null;
            }
        }
        else
            return System.getProperty("user.dir") +"/"+programName+" "+versionNo + getVersionType(licenceType)+".app"+sep;
    }
    return null;
  }  
  
  static String getAppBundlePath(boolean getParent){
      String ret = null;
      String strapp = ".app";
      try{
          // File f = new File(MyClass.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
      //  String path = shark.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        File f = new File(java.net.URLDecoder.decode(shark.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath(), "UTF-8"));
        String ss = f.getAbsolutePath();
        ret = ss.substring(0, ss.indexOf(strapp)+strapp.length());
        if(getParent){
            f = new File(ret);
            ret = f.getParent(); 
        }
      }
      catch(Exception ee){
          // the old way we used to do it
          String ss = System.getProperty("java.library.path");
          ss = ss.substring(0, ss.indexOf(strapp)+ strapp.length());
          if(getParent){
             File f = new File(ss);
             ret = f.getParent(); 
          }
      }
      return ret;
  }   

   static Vector osxGetUSBSerialCommand() throws IOException {
        Process p = Runtime.getRuntime().exec("system_profiler SPUSBDataType");
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
        StringTokenizer tokenizer = new StringTokenizer(outputText, "\n");
        Vector lines = null;
        while (tokenizer.hasMoreTokens()) {
            String line = tokenizer.nextToken().trim();
            // see if line contains MAC address
            String searchString = "Serial Number: ";
            int position = line.indexOf(searchString);
            if (position < 0) {
                continue;
            }
            String candidate = line.substring(searchString.length()).trim();
            if(lines==null) lines = new Vector();
            lines.add(candidate);
        }
        return lines;
    }

  /*
   * there have been usbs that:
   * 1) on PC, depending on which port it's plugged into have one character differing in id.
   * 2) newer (10.4 is ok, 10.7 not) Macs ignore leading zeros eg they read "912041344485648" whereas it was "0912041344485648" on PC.
   */
  static boolean isUSBSerialOk(String hardcoded,String passed) {
      if(usbTest){
          WebAuthenticateBase_base wab = new WebAuthenticateBase_base();
          try {
              Calendar cal = new GregorianCalendar();
              String month = String.valueOf(cal.get(Calendar.MONTH) + 1);
              if(month.length()==1)month = "0"+month;
              String filename = "USB_"+usbSerial+"_"+cal.get(Calendar.YEAR)+"-"+month
                +"-"+cal.get(Calendar.DAY_OF_MONTH)+"_"+cal.get(Calendar.HOUR_OF_DAY)+"h"+cal.get(Calendar.MINUTE)+"m";
              javax.swing.filechooser.FileSystemView filesys = javax.swing.filechooser.FileSystemView.getFileSystemView();
              BufferedWriter out = new BufferedWriter(new FileWriter(filesys.getHomeDirectory().getAbsolutePath() + sep + filename + ".txt"));
              out.write(wab.encrypt(hardcoded+ " " + passed, "usbtest"));
              out.close();
          }
          catch (IOException e)
          { }
      }
      String hardvariations[];
      if(shark.macOS){
          String hardcoded2 = hardcoded;
          while(hardcoded2.startsWith("0"))hardcoded2 = hardcoded2.substring(1);
          if(hardcoded2.length()==0)return false;
          if(hardcoded.equals(hardcoded2)) hardvariations = new String[]{hardcoded};
          else hardvariations = new String[]{hardcoded, hardcoded2};
      }
      else{
          hardvariations = new String[]{hardcoded};
      }
      for(int i = 0; i < hardvariations.length; i++){
          if(isUSBSerialOk2(hardvariations[i].toCharArray(), passed.toCharArray()))return true;
      }
    return false;
  }

  static boolean isUSBSerialOk2(char hardcoded[],char passed[]) {
      try{
          int errorno = 0;
          for(int i = 0; i < hardcoded.length; i++){
              if(hardcoded[i]!=passed[i])errorno++;
          }
          return errorno<2;
      }
      catch(Exception e){}
      return false;
  }

  public static boolean isLicenceStandard() {
      return (licenceType.equals(LICENCETYPE_DVD) ||
              licenceType.equals(LICENCETYPE_NETWORK));
  }
  public static boolean isLicenceActivated() {
      return (licenceType.equals(LICENCETYPE_STANDALONEACTIVATION) ||
//              licenceType.equals(LICENCETYPE_SINGLE)||
              licenceType.equals(LICENCETYPE_NETWORKACTIVATION));
  }

  static void createSharedMac(String path){
    if(!macOS)return;
    try{
        String cmds[] = new String[]{"osascript","-e","do shell script \"mkdir '"+path+"'\" with administrator privileges\ndo shell script \"chmod 777 '"+path+"'\" with administrator privileges"};
        Process p = Runtime.getRuntime().exec(cmds);
        p.waitFor();
    }
    catch(Exception e){}
  }

  static void createSharedMac(String path[]){
    if(!macOS)return;
    try{
        String instr = "";
        for(int i = 0; i < path.length; i++){
            instr += "do shell script \"mkdir '"+path[i]+"'\" with administrator privileges\ndo shell script \"chmod 777 '"+path[i]+"'\" with administrator privileges";
            if(i < path.length - 1)instr += "\n";
        }
        String cmds[] = new String[]{"osascript",
            "-e",
            instr
        };
        Process p = Runtime.getRuntime().exec(cmds);
        p.waitFor();
    }
    catch(Exception e){}
  }

  static void mkFolder(String s){
    File sh;
      if(!(sh = new File(s)).exists()) {
        if(sh.mkdir()){
          try {
            // needs to be 777 on Mac folders - 666 goes funny.
            String cmds[] = new String[]{"chmod", "-R", "777", sh.getAbsolutePath()};
            Process p = Runtime.getRuntime().exec((cmds));
            p.waitFor();
          } catch (Exception e) {}
       }
     }
  }
 
  static String getProgramDataPath(){
      if(macOS) return sep + "Library" + sep + "Application Support";
      else return System.getenv("PROGRAMDATA");
  }

  static String makeCode(String key, String s, int codelength) {
        String ret = "";
        char possibles[] = key.toCharArray();
        for (int k = 0; k < codelength; k++) {
            String ss = (encrypt(s + String.valueOf(k), key, true));
            int l = 0;
            for (int p = 0; p < ss.length(); p++) {
                l += ss.charAt(p);
            }
            l = Math.max(l, l * -1);
            ret = ret.concat(String.valueOf(possibles[l % possibles.length]));
        }
        return ret;
  }

  static String getProgramShortName() {
        return ACTIVATE_PREFIX+licenceType;
  }

    static String encrypt(String arg, String key, boolean addcheck) {
        char kk[] = key.toCharArray();
        char aa[] = ((addcheck)?addchek(arg):arg).toCharArray();
        short k = 0;
        int len2 = kk.length;
        short i;
        for (i = 0; i < len2; ++i) {
            k += kk[i];
        }
        for (i = 0; i < aa.length; ++i) {
            aa[i] ^= (char) ((k + i * 3 + kk[i % len2]) & 0x00ff);
        }
        return new String(aa);
    }

    static String addchek(String ss) {
        int hash = 0;
        char cc[] = ss.toCharArray();
        int len = cc.length, i;
        for (i = 0; i < len; ++i) {
            hash += (cc[i] * i) + ((cc[i] * (i + 1)) << 8) + ((cc[i] * (i + 2)) << 16) + ((cc[i] * (i + 3)) << 24);
        }
        return ss + new String(new char[]{(char) ((hash >> 24) & 255),
                    (char) ((hash >> 16) & 255),
                    (char) ((hash >> 8) & 255),
                    (char) (hash & 255)});
    }

  static String twoPartitionGetPublicPath(){
      publicPath = sep + "Volumes" + sep + programName.toUpperCase() + sep + programName + " " + versionNo+
                                    File.separator + programName.toLowerCase()+"-public"+File.separator;
      if(new File(publicPath).exists())return publicPath;
      String defaultVolName = "Untitled";
      String retVal = publicPath;
      String addon = "";
      int count = 0;
      while(count < 10 && (retVal==null || !new File(retVal).isDirectory())){
         retVal = sep + "Volumes" + sep + defaultVolName+addon + sep + programName + " " + versionNo+
                                    File.separator + programName.toLowerCase()+"-public"+File.separator;
         count++;
         addon = " " + String.valueOf(count);
      }
      return retVal;
  }

  static String getDeactivationFolder(){
      return sep + "Library" + sep + "Preferences" + sep +
              swapString("WHITESPACELTD", "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789", "38I7PEH96VGA4SR5N0LFW2UQJ1YKBMXOCTZD");
  }

    static String swapString(String s, String from, String to) {
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
}

