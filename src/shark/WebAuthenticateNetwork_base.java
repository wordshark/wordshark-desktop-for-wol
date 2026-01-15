/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package shark;

import javax.swing.text.AbstractDocument.*;
import org.w3c.dom.*;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import javax.xml.parsers.*;
import java.text.*;
import java.io.*;
import java.util.*;

/**
 *
 * @author MacBook Air
 */
public class WebAuthenticateNetwork_base {
    WebAuthenticateBase_base wab = new WebAuthenticateBase_base();
    String diagnosticsFolder = "Diagnostics";
    String diagnosticsClientsFolder = "Clients";
    String overrideFolderName = "Config";
    
    public String xmlFileName = "D9SLD83.xml";
    String overrideManualFileName = "ocodemanual.txt";
    String overrideAutoFileName = "ocodeauto.txt";
    String sharkOverrideAutoFileName = "preocodeauto.txt";    
    String overrideXMLBackupFileName = "oxmlbu.txt";
    String xmlKey_DeactivationNo = "na_47G7Q16";
    String xmlKey_ComputerID = "na_3B79Q21";
    String xmlKey_ComputerIDStore = "na_store";
    public String xmlKey_LicenceKey = "na_request";
    String xmlKey_ResponseKey = "na_response";
    String xmlKey_ActivationNote = "na_addnote";
    String xmlKey_School = "na_school";
    String xmlKey_Location = "na_location";
    String xmlKey_DeactivationKey = "na_deactivationkey";
    String xmlKey_OCode = "na_ocode";
    
    String XmlElements[] = new String[]{xmlKey_LicenceKey, xmlKey_ResponseKey,
                    xmlKey_School, xmlKey_Location, xmlKey_DeactivationKey, xmlKey_DeactivationNo, 
                    xmlKey_ComputerID, xmlKey_ComputerIDStore};
    
    public String str_Request = null;
    
    Document xmlDoc;
    static final int OVERRIDESTATE_NONE = 0;
    public static int overrideState = OVERRIDESTATE_NONE;
    static final int OVERRIDESTATE_ALERT_YELLOW = 1;
    // just admins after typing name at sign on screen
    // 45 days before end date
    static final int OVERRIDESTATE_ALERT_ORANGE = 2;
    // show at beginning of  sign on screen to all
    // pop up for administrators
    // 30 days before end date
    static final int OVERRIDESTATE_ALERT_RED = 3;
//    static final int OVERRIDOVERRIDESTATE_MANUAL = 4;
    static final int OVERRIDOVERRIDESTATE_ELAPSED = 4;
    
    final int daystogo_enterredzone = 25;
    final int daystogo_enterorangezone = 36;
    final int daystogo_enteryellowzone = 39;
    static Date overrideEndDate = null;
    
    
    static final String ERROR_EXPIRED = "42";    
      
    
    public String[] getNetDetails() {
        xmlDoc = null;
        overrideEndDate = null;
        try{
            overrideState = checkForOverride();
            File xmlFile = new File(sharkStartFrame.sharedPathplus+xmlFileName);
            if(!xmlFile.exists())return doReturn(new String[]{"59", null});
            xmlDoc = getXMLDocument(xmlFile);
            if(xmlDoc==null)return doReturn(new String[]{"56", null});
            String mess[] = new String[2];
            String xmlresult[] = getXMLElement(xmlDoc, xmlKey_LicenceKey);
            if(xmlresult[1]!=null) return doReturn(new String[]{xmlresult[1], null});
            String request = xmlresult[0];
            xmlresult = getXMLElement(xmlDoc, xmlKey_ResponseKey);
            if(xmlresult[1]!=null) return doReturn(new String[]{xmlresult[1], null});
            String response = xmlresult[0];
            boolean schoolmissing = false;
            boolean dotitles = false;
            if(sharkStartFrame.school==null){
                xmlresult = getXMLElement(xmlDoc, xmlKey_Location);
                if(xmlresult[1]!=null) return doReturn(new String[]{xmlresult[1], null});
                if(((xmlresult[0])!=null && !xmlresult[0].trim().equals(""))){
                    sharkStartFrame.location = xmlresult[0];
                }
                else sharkStartFrame.location = "";
                xmlresult = getXMLElement(xmlDoc, xmlKey_School);
                if(xmlresult[1]!=null) return doReturn(new String[]{xmlresult[1], null});
                if(((xmlresult[0])!=null && !xmlresult[0].trim().equals(""))){
                    sharkStartFrame.school = xmlresult[0];
                    dotitles = true;
                }
                else {
                    schoolmissing = true;
                }
            }
            if(request!=null){
                try{
                    String realrequest = wab.unChop16(u2_base.swapString(request, wab.userkey, wab.realkey));
                    sharkStartFrame.users = wab.getUsers(realrequest);
                }
                catch(Exception ee){}
            }
            if(request == null && response == null) mess = doReturn(new String[]{"70", u.gettext("errorcodes", "errorcode70")});
            else if(request == null) mess = doReturn(new String[]{"51", u.gettext("errorcodes", "errorcode51")});
            else if(response == null) mess = doReturn(new String[]{"54", u.gettext("errorcodes", "errorcode54")});
            else if (schoolmissing) mess = doReturn(new String[]{"53", u.gettext("errorcodes", "errorcode53")});
            else{
                mess = tryAuthenticate(request, response);
            }
//            // we want the correct details to show if possible even if an error has happened.
            if(dotitles){
//                if(sharkStartFrame.users == 0 && request!=null){
//                    String realrequest = wab.unChop16(wab.swapString(request, wab.userkey, wab.realkey));
//                    getlicenceinfo1(request, realrequest);
//                }
                sharkStartFrame.settitles();
            }
            return mess;
        }
        catch(Exception e){
            return doReturn(new String[]{"60", null});
        }
    }    
    
    private String[] tryAuthenticate(String request, String response){
        String xmlresult[] = getXMLElement(xmlDoc, xmlKey_DeactivationNo);
        if(xmlresult[1]!=null) return doReturn(new String[]{xmlresult[1], null});
        String xmldnoval = xmlresult[0];
        if(xmldnoval==null) return doReturn(new String[]{"52", u.gettext("errorcodes", "errorcode52")});
        String deactivationNo = wab.decrypt1(xmldnoval, wab.DeactivationNoKey); 
        if(deactivationNo==null) return doReturn(new String[]{"65", u.gettext("errorcodes", "errorcode65")});
        String currComputerIDs[] = null;
        int counter = 0;
        while (currComputerIDs ==null && counter++ < 5){
            xmlresult = getXMLElement(xmlDoc, xmlKey_ComputerID);
            if(xmlresult[1]!=null) return doReturn(new String[]{xmlresult[1], null});
            String xmlsidval = xmlresult[0];
            currComputerIDs = getMacAddresses(xmlsidval);
            if(currComputerIDs == null){
                u.pause(100);
            }
        }
        if(currComputerIDs==null) return doReturn(new String[]{"66", u.gettext("errorcodes", "errorcode66")});
        String currMacCombinations[] = wab.findPossibleResults(currComputerIDs, request, deactivationNo, java7_base.GetMins(sharkStartFrame.sharedPath));
        xmlresult = getXMLElement(xmlDoc, xmlKey_ComputerIDStore);
        if(xmlresult[1]!=null) return doReturn(new String[]{xmlresult[1], null});
        String xmlidval = xmlresult[0];
        if(xmlidval==null) return doReturn(new String[]{"55", u.gettext("errorcodes", "errorcode55")});
        String storedMacCominations[] = u.splitString(xmlidval);
        wab.isNonDeactivatable(response);
        wab.isSplitLicence(response);
        if (wab.hasExpired(response)) {
            return doReturn(new String[]{ERROR_EXPIRED, u.gettext("errorcodes", "errorcode42")});
        }
        String realrequest = wab.unChop16(u2_base.swapString(request, wab.userkey, wab.realkey));
        if(realrequest!=null){
            if (!shark.ACTIVATE_PREFIX.equals(realrequest.substring(4, 6))) {
                return doReturn(new String[]{"67", u.gettext("errorcodes", "errorcode67")});
            }
            int t2 = (realrequest.charAt(0) == 'N') ? 0 : 1;
            int t = shark.network ? 0 : 1;
            if (t != t2) {
                return doReturn(new String[]{"68", u.gettext("errorcodes", "errorcode68")});
            }
            getlicenceinfo1(request, realrequest);
        }
        for (int i = 0; currMacCombinations!=null && i <  currMacCombinations.length; i++) {
            if (u.findString(storedMacCominations, currMacCombinations[i]) >= 0) {
                writePreOverride();
                return doReturn(new String[]{"0", null});
            }
        }
        return doReturn(new String[]{"69", u.gettext("errorcodes", "errorcode69")});
    }

    private String[] doReturn(String[] ss){
        boolean writtenErrorLog = false;
        if(!"0".equals(ss[0]) && overrideState == OVERRIDESTATE_NONE){
            writtenErrorLog = checkSharkOverride(ss);
        }
        if(overrideState>0) {
           if(!writtenErrorLog)writeLogErrorCode(ss);
           if(overrideState!=OVERRIDOVERRIDESTATE_ELAPSED){
               try{
                   String overss[] = u.readFile(sharkStartFrame.sharedPathplus+overrideFolderName+shark.sep+overrideXMLBackupFileName); 
                   String s = wab.decrypt1(overss[0], GetMinCreatedOfThisAndParent(sharkStartFrame.sharedPathplus+overrideFolderName));
                   String stextvals[] = u.splitString(s);
                   sharkStartFrame.school = stextvals[u.findString(XmlElements, xmlKey_School)];
                   sharkStartFrame.location = stextvals[u.findString(XmlElements, xmlKey_Location)];
                   String request = stextvals[u.findString(XmlElements, xmlKey_LicenceKey)];
                   String realrequest = wab.unChop16(u2_base.swapString(request, wab.userkey, wab.realkey));
                   sharkStartFrame.users = wab.getUsers(realrequest);
                   getlicenceinfo1(request, realrequest);     
                   sharkStartFrame.settitles();
                   return new String[]{"0", null};
               }
               catch(Exception e){}
           }
           return ss;
        }
        else return ss;
    }
    
    private void writeLogErrorCode(String ss[]){
           try{
               String compname = u.getComputerName();
               String path = sharkStartFrame.sharedPathplus+
                        diagnosticsFolder;
               File f = new File(path);
               if(!f.exists()){
                   f.mkdirs();
                   u.setNewFilePermissions(f);
               }
               path = path + shark.sep + diagnosticsClientsFolder;
               f = new File(path);
               if(!f.exists()){
                   f.mkdirs();
                   u.setNewFilePermissions(f);
               }
               path = path + shark.sep + (compname==null?"":compname);
               f = new File(path);
               if(!f.exists()){
                   f.mkdirs();
                   u.setNewFilePermissions(f);
               }
               path = path + shark.sep + shark.programName+"ErrorCodes.log";
               boolean alreadyexists = (f = new File(path)).exists();
               PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(path, true)));
               Date d = new Date();
               out.println(d.toString() + "    Error code: " + String.valueOf(ss[0]+ "   " + (ss[1]==null?"":" "+ss[1])));
               out.close();
               if(!alreadyexists)u.setNewFilePermissions(f);
           }
           catch(Exception e){}        
    }
    
    public boolean checkSharkOverride(String ss[]){
        try{
            String ssSharkAuto[] = u.readFile(sharkStartFrame.sharedPathplus+overrideFolderName+shark.sep+sharkOverrideAutoFileName);
            overrideState = checkForOverride(ssSharkAuto[0]);
            if(overrideState != OVERRIDESTATE_NONE){
                writeLogErrorCode(ss);
                return true;
            }
        }
        catch(Exception e){}
        return false;
    }

    public boolean isInOverride(String ss[]){
        if(ss[0].equals(ERROR_EXPIRED))return false;
        return (WebAuthenticateNetwork_base.overrideState != WebAuthenticateNetwork_base.OVERRIDESTATE_NONE &&
                  WebAuthenticateNetwork_base.overrideState != WebAuthenticateNetwork_base.OVERRIDOVERRIDESTATE_ELAPSED);
    }
    
    private void writePreOverride(){
        try{
            String path = sharkStartFrame.sharedPathplus+overrideFolderName;
            File f = new File(path);
            if(!f.exists()){
                f.mkdirs();
                u.setNewFilePermissions(f);
            }
            String ssAuto[] = u.readFile(sharkStartFrame.sharedPathplus+overrideFolderName+shark.sep+sharkOverrideAutoFileName);
            String s = GetMinCreatedOfThisAndParent(sharkStartFrame.sharedPathplus+overrideFolderName); 
            Date datenow = new Date();
            String dnow = (new SimpleDateFormat("yyMMdd")).format(datenow);
            String existingdate = null;
            try{
                existingdate = wab.decrypt1(ssAuto[0], s);
            }
            catch(Exception e){}
            if(existingdate==null || !existingdate.equals(dnow)){
               path = path+shark.sep+sharkOverrideAutoFileName;
               Calendar expirecal = Calendar.getInstance();        
               expirecal.setTime(datenow);
               expirecal.add(Calendar.DAY_OF_MONTH, daystogo_enteryellowzone);
               boolean alreadyexists = (f = new File(path)).exists();
               PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(path, false)));
               out.println(wab.encrypt((new SimpleDateFormat("yyMMdd")).format(expirecal.getTime()), s));
               out.close();
               if(!alreadyexists)u.setNewFilePermissions(f);                
            }
        }
        catch(Exception e){}
    }
    

    private int checkForOverride(){
        String ssManual[] = u.readFile(sharkStartFrame.sharedPathplus+overrideFolderName+shark.sep+overrideManualFileName);
        String ssAuto[] = u.readFile(sharkStartFrame.sharedPathplus+overrideFolderName+shark.sep+overrideAutoFileName);
        int isoverriding = OVERRIDESTATE_NONE;
        if(ssManual!=null && ssManual.length>0){
            isoverriding = checkForOverride(ssManual[0]);
//            if(isoverriding>0)isoverriding = OVERRIDOVERRIDESTATE_MANUAL;
        }
        if(isoverriding==OVERRIDESTATE_NONE && ssAuto!=null && ssAuto.length>0){
            isoverriding = checkForOverride(ssAuto[0]);
        }        
        return isoverriding;
    }

    private int checkForOverride(String overridevalue){
        return checkForOverride(overridevalue, false);
    }    
    
    private int checkForOverride(String overridevalue, boolean alreadyDecrypted){
        if(overridevalue!=null){
            try{
                /*
                 * end date firstly set to 120 days
                 * if not resolved within 90 days (i.e. 30 days before end) - go into URGENT - RED
                 * if not resolved within 75 days (i.e. 45 days before end) - go into NON_URGENT - ORANGE
                 * if not resolved (i.e. 120 days before end) - YELLOW
                 */   
                String sdate;
                if(!alreadyDecrypted){
                    String s = GetMinCreatedOfThisAndParent(sharkStartFrame.sharedPathplus+overrideFolderName);
                    if(s==null)return OVERRIDESTATE_NONE;
                    sdate = wab.decrypt1(overridevalue, s);
                }
                else sdate = overridevalue;
                Date d = (new SimpleDateFormat("yyMMdd")).parse(sdate);
                overrideEndDate = (Date)d.clone();
                Calendar enddate = Calendar.getInstance();
                enddate.setTime(d);
                Calendar dnow = Calendar.getInstance();
                if(dnow.after(enddate))return OVERRIDOVERRIDESTATE_ELAPSED;
                enddate.add(Calendar.DAY_OF_MONTH, -daystogo_enterredzone); 
                if(dnow.after(enddate))return OVERRIDESTATE_ALERT_RED;
                enddate.add(Calendar.DAY_OF_MONTH, -(daystogo_enterorangezone-daystogo_enterredzone));
                if(dnow.after(enddate))return OVERRIDESTATE_ALERT_ORANGE;
                enddate.add(Calendar.DAY_OF_MONTH, -(daystogo_enteryellowzone-daystogo_enterorangezone));
                if(dnow.after(enddate))return OVERRIDESTATE_ALERT_YELLOW;
            }
            catch(Exception e){
            }            
        }
        return OVERRIDESTATE_NONE;
    }
    

    
    private String[] getMacAddresses(String xmlsidval){
        if(xmlsidval==null) return null;
        String ret[] = u.splitString(xmlsidval);
        for(int i = ret.length-1; i >= 0; i--){
            String decryp = decryptServerMac(ret[i]);
            if(decryp==null)ret = u.removeString(ret, i);
            else ret[i] = decryp;
        }  
        if(ret!=null && ret.length==0) ret = null;
        return ret;
    }    
    
    private String decryptServerMac(String s){
        //get current time to nearest 10 mins
        Calendar cal = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
        int min = cal.get(Calendar.MINUTE);
        String day = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
        if(day.length()==1)day = "0"+day;
        // rounds down to nearest 10 mins
        int min2 = (int)Math.ceil(min/10)*10;
        min2 = min2-min;
        cal.add(Calendar.MINUTE, min2);
        // allow 20 mins either way
        for(int i = 0; i < 5; i++){
            if(i == 1)cal.add(Calendar.MINUTE, 10);
            else if(i == 2) cal.add(Calendar.MINUTE, 10);
            else if(i == 3) cal.add(Calendar.MINUTE, -30);
            else if(i == 4) cal.add(Calendar.MINUTE, -10);
            String hour = String.valueOf(cal.get(Calendar.HOUR_OF_DAY));
            if(hour.length()==1)hour = "0"+hour;
            String minute = String.valueOf(cal.get(Calendar.MINUTE));
            if(minute.equals("0"))minute = "00";
            String currtime = day+hour+minute;
            String ret = null;
            try{
                ret=wab.decrypt1(s, currtime);
            }
            catch(Exception ee){}
            if (isSID(ret)) {
                return ret;
            }
        }
        return null;
    }

    private void getlicenceinfo1(String userrequest, String realrequest){
        try{
            str_Request = userrequest;
            wab.getlicenceinfo(realrequest);
        }
        catch(Exception e){}
    }
    
    private boolean isSID(String SID) {
        if(SID == null || SID.trim().equals(""))return false;
        return true;
    }
    
    public Document getXMLDocument(File file) {
        Document doc = null;
        if(!file.exists())return null;
        try {
          DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
          DocumentBuilder db = dbf.newDocumentBuilder();
          dbf.setValidating(false);
          doc = db.parse(file);
          return doc;
        }
        catch (Exception trans) {}
        return null;
    }    

    public String[] getXMLElement(Document doc, String key) {
        String answer = null;
        try {
            NodeList nodeList = doc.getElementsByTagName(key);
            if(nodeList==null || nodeList.getLength()==0)
                  return new String[]{null, "57"};
            for (int i = 0; i < nodeList.getLength(); i++) {
              Node n = nodeList.item(i);
              Node actualNode = n.getFirstChild();
              if (actualNode != null) {
                  answer =actualNode.getNodeValue();
                  if(answer!=null){
                    answer = answer.trim();
                    if(answer.equals(""))answer = null;
                    break;
                  }
              }
            }
          return new String[]{answer, null};
        }
        catch (Exception trans) {
            return new String[]{null, "56"};
        }
    }    


     private String GetMinCreatedOfThisAndParent(String path) {
        try {
            File configFolder = new File(path);
            File sharedFolder = configFolder.getParentFile();
            return java7_base.GetMins(configFolder) + java7_base.GetMins(sharedFolder);
        }
        catch(Exception e){}
        return null;
    }
/*
    private String GetMins(File f) {
        try {
            Process proc =
                    Runtime.getRuntime().exec("cmd /c dir \""+f.getAbsolutePath()+"\" /tc");
            BufferedReader br =
                    new BufferedReader(
                    new InputStreamReader(proc.getInputStream()));
            String data = "";
            for (int i = 0; i < 6; i++) {
                data = br.readLine();
            }
            StringTokenizer st = new StringTokenizer(data);
            String date = st.nextToken();//Get date
            String time = st.nextToken();//Get time
            return u.splitString(time, ':')[1].substring(0,2);
        } catch (Exception e) {
        }
        return null;
    }
*/

}
