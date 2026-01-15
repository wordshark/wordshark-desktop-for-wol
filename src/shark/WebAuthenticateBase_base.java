/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package shark;

import java.text.*;
import java.util.*;
import javax.swing.text.AbstractDocument.*;
/**
 *
 * @author MacBook Air
 */
public class WebAuthenticateBase_base {
    
      
    // only use these two for numbers
    //30 long  : removed  'K' and 'M'  K at beginning of response means Expiry    
    final String responseuserkey = "BTG8ESV6P23HLDZR7N5U9FCWYAJ4QX";
    final String responserealkey = "387PEH96A4SR5N0LFW2QJ1KBMXCTZD"; // 30 long  -  as real key but no Y and G
    // numbers: all
    // letters: no IOUV
    final String realkey = "387PEH96GA4SR5N0LFW2QJ1YKBMXCTZD"; //32 long  // don't have numbers on last two chars
    // numbers: no 1 0
    // letters: no I O
    final String userkey = "BTG8ESVM6P23HLDZR7N5U9FCWYAJK4QX";
    
    public String DeactivationNoKey = "47G7Q16";
    
    final int chopped16[] = new int[]{12, 7, 8, 2, 14, 5, 0, 6, 11, 3, 4, 1, 10, 13, 9, 15};

    final int chopped20[] = new int[]{19, 7, 9, 15, 2, 16, 8, 4, 13, 6, 10, 17, 0, 3, 14, 1, 12, 5, 11, 18};

    final int chopped15[] = new int[]{7, 9, 1, 4, 12, 13, 5, 0, 11, 14, 8, 2, 10, 6, 3};    
    
    final static String key = "38I7dSsw5N7a2";
    
    
    
    
    public void doSetXMLElementValue(String filePath, String element, String value){
        if(value==null || value.trim().equals(""))value="";
        value = encrypt(value, key);
        u.setXMLElement(filePath, element, value);
    }    
    
    public String doGetXMLElementValue(String filePath, String element){
        String s = u.getXMLElement(filePath, element);
        if(s==null)return null;
        s = decrypt1(s, key);
        if(s!=null && s.trim().equals(""))s = null;
        return s;
    }    
    
    public void doSetXMLElementValues(String filePath, String elements[], String values[]){
        if((values==null || values.length==0)||(elements==null || elements.length==0)) return;
        if(values.length!=elements.length)return;
        for(int i = 0; i < elements.length; i++){
            values[i] = encrypt(values[i], key);
        }
        u.setXMLElements(filePath, elements, values);
    }

    public String[] doGetXMLElementValues(String filePath, String elements[]){
        String s[] = u.getXMLElements(filePath, elements);
        for(int i = 0; i < s.length; i++){
            if(s[i]!=null){
                s[i] = decrypt1(s[i], key);
                if(s[i]!=null && s[i].trim().equals(""))s[i] = null;
            }
        }
        return s;
    }         
    
    
    public String[] findPossibleResults(String macs[], String request, String DeactivationNo) {
        return findPossibleResults(macs, request, DeactivationNo, null);
    }

    public String[] findPossibleResults(String macs[], String request, String DeactivationNo, String sharedMins) {
        String res[] = new String[]{};
        if(sharedMins==null)sharedMins = "";
          for (int i = 0; i < macs.length; i++) {
            res = u.addString(res, encrypt(macs[i], request+DeactivationNo+sharedMins));
          }
        return res;
    }
        
    public String encrypt(String arg, String key) {
        try{
            char kk[] = key.toCharArray();
            char aa[] = (addchek(arg)).toCharArray();
            short k = 0;
            int len2 = kk.length;
            short i;
            for (i = 0; i < len2; ++i) {
                k += kk[i];
            }
            for (i = 0; i < aa.length; ++i) {
                aa[i] ^= (char) ((k + i * 3 + kk[i % len2]) & 0x00ff);
            }
            return tohex(String.valueOf(aa));
        }
        catch(Exception e){}
        return null;
    }
    
//    public String GetDeactivationFolder() { 
//       return GetDeactivationFolder2() + shark.sep + 
//               swapString("WHITESPACELTD", "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789", wholekey);
//    }
    
  

    public String tohex(String from_s) {
        try{
            int i;
            int len = from_s.length();
            String to = "";
            char from[] = from_s.toCharArray();
            for (i = 0; i < len; ++i) {
                to +=  String.valueOf((char) ('A' + ((from[i] >> 4) & 0x0f)));
                to +=  String.valueOf((char) ('A' + (from[i] & 0x0f)));
            }
            return to;
        }
        catch(Exception e){}
        return null;
    }

    public String dehex(String arg) {
        try{
            if ((arg.length() % 2) != 0) {
                return "XXXXXXX";
            }
            char s[] = new char[arg.length() / 2];
            for (short i = 0; i < arg.length(); i += 2) {
                s[i / 2] = (char) (((arg.charAt(i) - 'A') << 4) + (arg.charAt(i + 1) - 'A'));
            }
            return new String(s);
        }
        catch(Exception e){}
        return null;
    }


    public String addchek(String ss) {
        try{
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
        catch(Exception e){}
        return null;
    }

    public String decrypt1(String arg, String key) {
        try{
            arg = dehex(arg);
            char kk[] = key.toCharArray();
            char aa[] = arg.toCharArray();
            short k = 0;
            int len2 = kk.length;
            short i;
            for (i = 0; i < len2; ++i) {
                k += kk[i];
            }
            for (i = 0; i < aa.length; ++i) {
                aa[i] ^= (char) ((k + i * 3 + kk[i % len2]) & 0x00ff);
            }
            return stripchek(new String(aa));
        }
        catch(Exception e){}
        return null;
    }

    public String stripchek(String ss) {
        try{
            int hash = 0;
            char cc[] = ss.toCharArray();
            int len = cc.length, i;
            for (i = 0; i < len - 4; ++i) {
                hash += (cc[i] * i) + ((cc[i] * (i + 1)) << 8) + ((cc[i] * (i + 2)) << 16) + ((cc[i] * (i + 3)) << 24);
            }
            int hash2 = (((int) cc[len - 4]) << 24) | (((int) cc[len - 3]) << 16) | (((int) cc[len - 2]) << 8) | ((int) cc[len - 1]);
            if (hash != hash2) {
                return null;
            }
            return ss.substring(0, len - 4);
        }
        catch(Exception e){}
        return null;
    }
    
    
    public String unmakeCode2(String posschars, String key, String s) {
        try {
            int k;
            if ((k = Integer.parseInt(s)) < 0) {
                return String.valueOf(k);
            }
        }
        catch (Exception e) {}
        long totm = 0;
        for (int k = 0; k < key.length(); k++) {
            totm += getNumber(key, key+key.charAt(k), k);
        }
        int i = (int) (totm % posschars.length());
        String key2 = posschars.substring(i) + posschars.substring(0, i);
        String ss = u2_base.swapString(s, key2, posschars);
        return ss;
    }

    public String makeCode2(String posschars, String key, String s) {
        long totm = 0;
        for (int k = 0; k < key.length(); k++) {
            totm += getNumber(key, key+key.charAt(k), k);
        }
        int i = (int) (totm % posschars.length());
        String key2 = posschars.substring(i) + posschars.substring(0, i);
        String ss = u2_base.swapString(s, posschars, key2);
        return ss;
    }
    
    public String makeCode(String key, String s, int codelength) {
        String ret = "";
        char possibles[] = key.toCharArray();
        for (int k = 0; k < codelength; k++) {
            int l = getNumber(key, s, k);
            ret = ret.concat(String.valueOf(possibles[l % possibles.length]));
        }
        return ret;
    }
    
    public int getNumber(String key, String s, int num) {
        String ss = "";
        for(int j = 0; j < s.length(); j++){
          String extra = String.valueOf(num)+String.valueOf(j);
          ss += encrypt(s+extra, key+extra);
        }
        int l = 0;
        for (int p = 0; p < ss.length(); p++) {
            l += ss.charAt(p);
        }
        return l;
    }
    
    public String unChop16(String s) {
        String res = "";
        for (int i = 0; i < s.length(); i++) {
            for (int k = 0; k < chopped16.length; k++) {
                if (i == chopped16[k]) {
                    res = res + s.charAt(k);
                    break;
                }
            }
        }
        return res;
    }

    public String chop15(String s) {
        String res = "";
        for (int i = 0; i < chopped15.length; i++)
        {
            res = res + String.valueOf(s.charAt(chopped15[i]));
        }
        return res;
    }
    
    public String unChop15(String s) {
        String res = "";
        for (int i = 0; i < s.length(); i++) {
            for (int k = 0; k < chopped15.length; k++) {
                if (i == chopped15[k]) {
                    res = res + s.charAt(k);
                    break;
                }
            }
        }
        return res;
    }

    public String chop20(String s) {
        String res = "";
        for (int i = 0; i < chopped20.length; i++)
        {
            res = res + String.valueOf(s.charAt(chopped20[i]));
        }
        return res;
    }
    
    public String unChop20(String s) {
        String res = "";
        for (int i = 0; i < s.length(); i++) {
            for (int k = 0; k < chopped20.length; k++) {
                if (i == chopped20[k]) {
                    res = res + s.charAt(k);
                    break;
                }
            }
        }
        return res;
    }    
       
    
    public boolean isNonDeactivatable(String response) {
        boolean ret;
        if (response == null) {
            ret = false;
        }
        else if (response.charAt(9) != 'K') {
            ret = false;
        }
        else ret = true;
        sharkStartFrame.nondeactivatable = ret;
        return ret;
    }   
    public boolean isSplitLicence(String response) {
        boolean ret;
        if (response == null) {
            ret = false;
        }
        else if (response.charAt(18) != 'K') {
            ret = false;
        }
        else ret = true;
        sharkStartFrame.splitlicence = ret;
        return ret;
    }    
    
    public boolean hasExpired(String response) {
        if (response.charAt(0) != 'K') {
            return false;
        }
        try {
            String resshort = response.substring(10, 16);
            resshort = u2_base.swapString(resshort, responseuserkey, responserealkey);
            // try and see if expiry makes sense
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            SimpleDateFormat sdf2 = new SimpleDateFormat("ddMMyyyy");
            Date expires = sdf.parse("20" + resshort);
            sharkStartFrame.expiry = sdf2.format(expires);
            if ((new Date()).getTime() > expires.getTime()) {
                return true;
            }
        } catch (ParseException e) {
            return false;
        }
        return false;
    }    
    
    public boolean isExpiry(String response) {
        if (response == null) {
            return false;
        }
        if (response.charAt(0) != 'K') {
            return false;
        }
        try {
            String resshort = response.substring(10, 16);
            resshort = u2_base.swapString(resshort, responseuserkey, responserealkey);
            if (resshort == null) {
                return false;
            }
            // try and see if expiry makes sense
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            SimpleDateFormat sdf2 = new SimpleDateFormat("ddMMyyyy");
            Date d = sdf.parse("20" + resshort);
            sharkStartFrame.expiry = sdf2.format(d);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }    

    public void getlicenceinfo(String s) {
        if(!isCorrectMajorVersion(s)){
            // "Licence Key is not for this product."
            OptionPane_base.getErrorMessageDialog(sharkStartFrame.mainFrame, 73, 
                    u.gettext("errorcodes", "errorcode73"), OptionPane_base.ERRORTYPE_EXIT);
        }
        if(shark.licenceType.equals(shark.LICENCETYPE_STANDALONEACTIVATION) && isSingle(s, true)){
//            shark.licenceType = shark.LICENCETYPE_SINGLE;
            shark.singledownload=true;
            shark.setMaxUsers();
        }
        if(!isNetworkCorrect(s) || 
                (shark.licenceType.equals(shark.LICENCETYPE_STANDALONEACTIVATION)&&(!isMultSingle(s, true)&&!isSingle(s, true)))){
            // "Licence Key is not for this product type."
            OptionPane_base.getErrorMessageDialog(sharkStartFrame.mainFrame, 74, 
                    u.gettext("errorcodes", "errorcode74"), OptionPane_base.ERRORTYPE_EXIT);            
        }
        if (shark.licenceType.equals(shark.LICENCETYPE_STANDALONEACTIVATION)) {
            sharkStartFrame.users = 1;
        } else if (shark.network) {
            sharkStartFrame.users = getUsers(s);
            sharkStartFrame.serial = getSerial(s);
        } else {
            sharkStartFrame.users = 1;
        }
    }
    
    private boolean isCorrectMajorVersion(String s) {
        return shark.ACTIVATE_PREFIX.equals(s.substring(4, 6));
    }            
    
    private boolean isNetworkCorrect(String s) {
        return shark.network==(s.charAt(0) == 'N');
    }

    public boolean isMultSingle(String s, boolean real) {
        if (!real) {
            s = unChop16(s);
            s = u2_base.swapString(s, userkey,realkey);
        }
        return s.charAt(0) == 'M';
    }

    public boolean isSingle(String s, boolean real) {
        if(s==null)return false;
        if (!real) {
            s = unChop16(s);
            s = u2_base.swapString(s, userkey, realkey);
        }
        return s.charAt(0) == 'S';
    }

    public int getUsers(String s) {
        return Integer.parseInt(s.substring(1, 4));
    }    
    
    public String getSerial(String s) {
        return s.substring(4, 11);
    }
}
