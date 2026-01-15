package shark;

import java.io.*;
import java.text.*;
import java.util.*;
import java.util.regex.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.print.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;
import javax.swing.text.AbstractDocument.*;

public class WebAuthenticate_base {
    
    static final public String servicePatch = "Services/PatchService";
    static final public String serviceActivation = "Services/ActivationService";
    static final public String activationServiceName = "Service1.asmx";
    int schoolcharlen = 25;
    int postcodecharlen = 20;
    int addnotecharlen = 40;
    static boolean dowritelog = false;
    final String computeriduserkey = "B43HLqrstnpZR72xN5VM6PycYAJKfghESDkdeUTGwQXab89FCWmz";
    final String computeridrealkey = "q18fwJKyz4QXabgh9FWBp5UHcdC0ATP23kmnSExLDZR7NeVM6rst";  //as computeriduserkey key but no Y and G
    private String maccodesautolist = "";
    private String manualMac = "";
    boolean gotConnection = false;
    private String realmacs[];
    String str_httpactivation = u.gettext("webauthenticate", "httpactivation", shark.programName.toLowerCase()) + "/"; 
//    String str_serviceaccess = u.gettext("webauthenticate", "serviceaccess");
    String httpsaddress = u.gettext("webauthenticate", "httpsaddress");
    final int RESPONSE = 0;
    final int REQUEST = 1;
    final int SCHOOL = 2;
    final int LOCATION = 3;
    final int NOTE = 4;
    final int INSTALLNO = 5;
    static String updates[] = new String[6];
    static String macupdate[];
    static String versionName = null;
    String str_offline = u.gettext("webauthenticate", "offline");
    String deactivationFolderName;
    String DeactivationPath;
    public String ConfigFilePath;
    String ConfigFilePath_old;
    final String BLANK = " ";
    int currno = -1;
    NetClient_base nc = null;
    Thread activateThread = null;
    int maximumConn = 5;
    public static String nettime;
    public static String netmacs;
    static String logfile = "_acf";
    final static String key = "38I7dSsw5N7a2";
    final static int ERRORNOUPDATE = -1;
    final static int ERRORNOUPDATEONEXISTING = -2;
    final static int ERRORNOFINDLICENCE = -3;
    final static int ERRORVOID = -4;
    final static int ERRORNOFREESPACES = -5;
    final static int ERRORINCORRECTINPUT = -6;
    final static int ERRORNOSUPPORT = -7;
    final static int ERRORBADRESPONSE = -8;
    final static int ERRORISMASTER = -9;
    final static int ERRORBEENVOIDED = -10;
    final static int ERRORINSTALLNOERROR = -11;
    final static int ERRORBEENDEACTIVATED = -12;
    final static int ERROREXPIRYOLD = -13;
    final static int ERRORUNSPECIFIED = -75;
    final static int RESPONSELENGTH = 20;
    final static int REQUESTLENGTH = 16; 
    final static String ELEMENT_REQUEST = "wa_request";    
    final static String ELEMENT_RESPONSE = "wa_response";    
    final static String ELEMENT_SCHOOL = "wa_school";    
    final static String ELEMENT_LOCATION = "wa_location";    
    final static String ELEMENT_NOTE = "wa_addnote";
    final static String ELEMENT_INSTALLNO = "wa_installno";
    final static String ELEMENT_STORE = "wa_store";
    final static String ELEMENT_DEACTIVATIONKEY = "wa_deactivationkey";
    final static String ELEMENT_CONFIGFILEPATH = "wa_cfp";
    final String SPACES = "    ";
    String strinstallno = null;
    String serverMacFile = "3B79Q21.txt";
    String serverDeactivationFile = "47G7Q16.txt";
    String configElementName = "wa_cfp";
    // 10 mins
    long serviceMacTick = 600000;
    public static boolean doneExpiryWarn = false;
    public static int waittime = 15000;
    int timertime = 200;
    int waitforoffline = 2000;
    public WebAuthenticateBase_base wab = new WebAuthenticateBase_base();
    String xmlContents = "<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"no\"?><data version=\"1.0\"><config><wa_request/><wa_response/><wa_school/><wa_location/><wa_addnote/><wa_installno/><wa_deactivationkey/><wa_store/><wa_cfp/></config></data>";
    String xmlConfigContents = "<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"no\"?><data version=\"1.0\"><config><" + configElementName + "/></config></data>";
    public boolean xmlInitallyMissing;
    String errorColor = "Red";
    
    public WebAuthenticate_base() {
        String sp = File.separator;
        deactivationFolderName = u2_base.getSecretFolderName();        
        xmlInitallyMissing = !((new File(sharkStartFrame.sharedPathplus + u2_base.saXmlFileName))).exists();
        String maccodes[];        
        try {            
            if (!shark.network) {
                DeactivationPath = u2_base.getAppDataFolder();
                File f = new File(DeactivationPath);
                if (!f.exists()) {
                    String pub = sharkStartFrame.publicPathplus;
                    String end = shark.programName.toLowerCase() + "-public" + sp;
                    String config = shark.programName.toLowerCase() + "-config";
                    DeactivationPath = pub.substring(0, pub.length() - end.length()) + config;
                    f = new File(DeactivationPath);
                    if (!f.exists()) {
                        f.mkdirs();
                    }
                    if (!f.exists()) {
                        OptionPane_base.getErrorMessageDialog(null, 71, null, OptionPane_base.ERRORTYPE_EXIT);
                    }                    
                }
                ConfigFilePath_old = DeactivationPath + sp + deactivationFolderName + sp + "config.dat";                
                ConfigFilePath = DeactivationPath + sp + deactivationFolderName + sp + "config.xml";                
                f = new File(DeactivationPath + sp + deactivationFolderName + sp);
                if (!f.exists()) {
                    f.mkdirs();
                }
                f = new File(ConfigFilePath);
                if (!f.exists()) {
                    writeFile(ConfigFilePath, xmlConfigContents);
                    u.setNewFilePermissions(f);
                }
            }
            
            
      //      autoConvertExpiry("01-01-2017", "FQESVYAPRJD9GBFW", "esgd", "9");
            getstoremaninstallnos();
            boolean macprob = false;
            try {
                realmacs = u2_base.getMacAddresses();
                if (dowritelog) {
                    u2_base.writelog(realmacs);
                }
            } catch (Exception e) {
                e.printStackTrace();
                macprob = true;
            }
            if (macprob || realmacs == null || realmacs.length == 0) {
                if (shark.network) {
                    JOptionPane.showMessageDialog(sharkStartFrame.mainFrame,
                            u.gettext("webauthenticate", "netservercontitle"),
                            u.gettext("webauthenticate", "noidtitle"),
                            JOptionPane.WARNING_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(sharkStartFrame.mainFrame,
                            u.gettext("webauthenticate", "noidmess"),
                            u.gettext("webauthenticate", "noidtitle"),
                            JOptionPane.WARNING_MESSAGE);
                }
                System.exit(0);
            }
            maccodes = computer_macCodes(realmacs);
            if (dowritelog) {
                u2_base.writelog(new String[]{"Final list of computer codes:  "});
            }
            if (dowritelog) {
                u2_base.writelog(maccodes);
            }
            
            for (int i = 0; i < maccodes.length; i++) {
                maccodesautolist += maccodes[i];
            }
            if (maccodes.length == 0) {
                JOptionPane.showMessageDialog(null, new String[]{"Cannot identify computer"}, shark.programName, JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
            }
            String strmac = "";
            if (maccodes.length == 1) {
                strmac = maccodes[0] + maccodes[0] + maccodes[0];
            } else if (maccodes.length == 2) {
                strmac = maccodes[0] + maccodes[1] + maccodes[0];
            } else {
                strmac = maccodes[0] + maccodes[1] + maccodes[2];
            }
            manualMac = strmac;
            if (dowritelog) {
                u2_base.writelog(new String[]{"Auto codes:  " + maccodesautolist});
            }
            if (dowritelog) {
                u2_base.writelog(new String[]{"Manual codes:  " + manualMac});
            }
        } catch (Exception e) {
            OptionPane_base.getErrorMessageDialog(null, 72, null, OptionPane_base.ERRORTYPE_EXIT);
        }
    }
    
    private String getDisplayId(String request, String id, int deactivationno) {
        int t = deactivationno + 1;
        t = t % wab.responseuserkey.length();
        String responsechar = wab.responseuserkey.substring(t, t + 1);
        id += responsechar;
        String strmac_check = wab.makeCode(wab.responseuserkey, id + request, 2);
        id += strmac_check;
        id = doIKCInstNo(id);
        id = wab.chop15(id);
        return id.substring(0, 5) + "|" + id.substring(5, 10) + "|" + id.substring(10);
    }    
    
    private String doIKCInstNo(String mac) {
        String instno = mac.substring(12, 13);
        int p = instno.charAt(0);
        int j = computeriduserkey.length();
        p = p - j;
        int k = Math.max(p, p * -1);
        if (k != 0) {
            k = j % k;
        }
        String truk = computeriduserkey.substring(k) + computeriduserkey.substring(0, k);
        String res = u2_base.swapString(mac, computeriduserkey, truk);
        return res.substring(0, 12) + instno + res.substring(13);
    }
    

    
    public IntroFrame_base makeUI(boolean upgrade) {
        AuthUI aui = new AuthUI(upgrade);
        return aui;
    }
    
    public IntroFrame_base makeExpiryUI() {
        removeExpiry reui = new removeExpiry(true);
        return reui;
    }

    // response here is the one stored in options
    public boolean tryAuthenticate(String request, String response) {
        String currMacCombinations[] = wab.findPossibleResults(realmacs, request, String.valueOf(currno));
        String tempss[] = null;
        String temps = wab.doGetXMLElementValue(sharkStartFrame.sharedPathplus + u2_base.saXmlFileName, ELEMENT_STORE);
        if (temps != null) {
            tempss = u.splitString(temps);
        }
        String storedMacCominations[] = tempss;
        wab.isNonDeactivatable(response);
        wab.isSplitLicence(response);
        while (wab.hasExpired(response)) {
            removeExpiry re = new removeExpiry(true);
            while (re.running) {
                u.pause(1000);
            }
            response = wab.doGetXMLElementValue(sharkStartFrame.sharedPathplus + u2_base.saXmlFileName, ELEMENT_RESPONSE);
        }
        //check if same type of program and same major version as before
        String realrequest = wab.unChop16(u2_base.swapString(request, wab.userkey, wab.realkey));
        if (!shark.ACTIVATE_PREFIX.equals(realrequest.substring(4, 6))) {
            return false;
        }
        int t = shark.network ? 0 : 1;
        int t2 = -1;
        switch (realrequest.charAt(0)) {
            case 'N':
                t2 = 0;
                break;
            case 'M':
                t2 = 1;
                break;
            case 'S':
                t2 = 2;
                break;
        }
        if (t == 0 && t2 != 0) {
            return false;
        }
        if (t == 1 && (t2 < 1 || t2 > 2)) {
            return false;
        }
        for (int i = 0; currMacCombinations != null && i < currMacCombinations.length; i++) {
            if (u.findString(storedMacCominations, currMacCombinations[i]) >= 0) {
                wab.getlicenceinfo(realrequest);
                return true;
            }
        }
        return false;
    }
    
    
    public void doClear() {
        sharkStartFrame.expiry = null;
        String elements[] = new String[]{ELEMENT_REQUEST, ELEMENT_INSTALLNO, ELEMENT_SCHOOL, ELEMENT_LOCATION, ELEMENT_NOTE, ELEMENT_STORE, ELEMENT_RESPONSE, ELEMENT_CONFIGFILEPATH};
        String values[] = new String[elements.length];
        wab.doSetXMLElementValues(sharkStartFrame.sharedPathplus + u2_base.saXmlFileName, elements, values);
    }
    
    public void updateToXML() {
        String trequest = (String) db.find(sharkStartFrame.optionsdb, ELEMENT_REQUEST, db.TEXT);
        String tresponse = (String) db.find(sharkStartFrame.optionsdb, ELEMENT_RESPONSE, db.TEXT);
        String tstore = u.combineString((String[]) db.find(sharkStartFrame.optionsdb, "wa_possiblemacs", db.TEXT));        
        String tschool = (String) db.find(sharkStartFrame.optionsdb, ELEMENT_SCHOOL, db.TEXT);        
        String tpostcode = (String) db.find(sharkStartFrame.optionsdb, ELEMENT_LOCATION, db.TEXT);        
        String tnote = (String) db.find(sharkStartFrame.optionsdb, ELEMENT_NOTE, db.TEXT);        
        String tinstallno = (String) db.find(sharkStartFrame.optionsdb, ELEMENT_INSTALLNO, db.TEXT);            
        String elements[] = null;
        String values[] = null;             
        if (trequest != null) {
            if (elements == null) {
                elements = new String[]{ELEMENT_REQUEST};
            } else {
                elements = u.addString(elements, ELEMENT_REQUEST);
            }
            if (values == null) {
                values = new String[]{trequest};
            } else {
                values = u.addString(values, trequest);
            }            
        }
        if (tresponse != null) {
            if (elements == null) {
                elements = new String[]{ELEMENT_RESPONSE};
            } else {
                elements = u.addString(elements, ELEMENT_RESPONSE);
            }
            if (values == null) {
                values = new String[]{tresponse};
            } else {
                values = u.addString(values, tresponse);
            }            
        }        
        if (tstore != null) {
            if (elements == null) {
                elements = new String[]{ELEMENT_STORE};
            } else {
                elements = u.addString(elements, ELEMENT_STORE);
            }
            if (values == null) {
                values = new String[]{tstore};
            } else {
                values = u.addString(values, tstore);
            }            
        }        
        if (tschool != null) {
            if (elements == null) {
                elements = new String[]{ELEMENT_SCHOOL};
            } else {
                elements = u.addString(elements, ELEMENT_SCHOOL);
            }
            if (values == null) {
                values = new String[]{tschool};
            } else {
                values = u.addString(values, tschool);
            }            
        }        
        if (tpostcode != null) {
            if (elements == null) {
                elements = new String[]{ELEMENT_LOCATION};
            } else {
                elements = u.addString(elements, ELEMENT_LOCATION);
            }
            if (values == null) {
                values = new String[]{tpostcode};
            } else {
                values = u.addString(values, tpostcode);
            }            
        }
        if (tnote != null) {
            if (elements == null) {
                elements = new String[]{ELEMENT_NOTE};
            } else {
                elements = u.addString(elements, ELEMENT_NOTE);
            }
            if (values == null) {
                values = new String[]{tnote};
            } else {
                values = u.addString(values, tnote);
            }            
        }
        if (tinstallno != null) {
            if (elements == null) {
                elements = new String[]{ELEMENT_INSTALLNO};
            } else {
                elements = u.addString(elements, ELEMENT_INSTALLNO);
            }
            if (values == null) {
                values = new String[]{tinstallno};
            } else {
                values = u.addString(values, tinstallno);
            }            
        }
        elements = u.addString(elements, ELEMENT_CONFIGFILEPATH);
        values = u.addString(values, ConfigFilePath);        
        if (elements != null) {
            wab.doSetXMLElementValues(sharkStartFrame.sharedPathplus + u2_base.saXmlFileName, elements, values);
        }        
    }
    
    public void doUpdate(boolean auto) {
        deleteTemp();
        File f;
        String elements[] = new String[]{ELEMENT_REQUEST, ELEMENT_RESPONSE, ELEMENT_STORE, ELEMENT_INSTALLNO, ELEMENT_DEACTIVATIONKEY, ELEMENT_CONFIGFILEPATH};
        String values[] = new String[]{updates[REQUEST], updates[RESPONSE], u.combineString(macupdate), updates[INSTALLNO], null, ConfigFilePath};        
        if (updates[SCHOOL] != null) {
            elements = u.addString(elements, ELEMENT_SCHOOL);
            values = u.addString(values, updates[SCHOOL]);            
        }        
        if (updates[LOCATION] != null) {
            elements = u.addString(elements, ELEMENT_LOCATION);
            values = u.addString(values, updates[LOCATION]);            
        }
        if (updates[NOTE] != null) {
            elements = u.addString(elements, ELEMENT_NOTE);
            values = u.addString(values, updates[NOTE]);
        }
        wab.doSetXMLElementValues(sharkStartFrame.sharedPathplus + u2_base.saXmlFileName, elements, values);
        f = new File(sharkStartFrame.sharedPathplus + "deactivation.txt");
        if (f.exists()) {
            f.delete();
        }        
    }
    
    public void doDisable() {
        String elements[] = new String[]{ELEMENT_RESPONSE, ELEMENT_REQUEST, ELEMENT_STORE, ELEMENT_SCHOOL, ELEMENT_LOCATION, ELEMENT_NOTE, ELEMENT_INSTALLNO, ELEMENT_CONFIGFILEPATH};
        String values[] = new String[elements.length];        
        wab.doSetXMLElementValues(sharkStartFrame.sharedPathplus + u2_base.saXmlFileName, elements, values);
    }
    
    public class AuthUI extends IntroFrame_base {

        boolean needGetSchoolPostcode = false;
        boolean isnetextrausers;
        JButton offline;
        JButton ok;
        JTextField compid1;
        IntroFrame_base thisjd;
        GridBagConstraints grid = new GridBagConstraints();
        javax.swing.Timer activateTimer;      
        long timerOverdue = -1;
        JTextField key_1request1;
        JTextField key_1request2;
        JTextField key_1request3;
        JTextField key_1request4;
        JTextField check_request1;
        JTextField check_request2;
        JTextField check_request3;
        JTextField check_request4;
        JTextField key_1response1;
        JTextField key_1response2;
        JTextField key_1response3;
        JTextField key_1response4;
        JTextField key_1response5;
        JPanel requestPanel = new JPanel(new GridBagLayout());
        JPanel responsePanel = new JPanel(new GridBagLayout());
        JPanel reqPanel = new JPanel(new GridBagLayout());
//        JPanel checkPanel = new JPanel(new GridBagLayout());
        JPanel successPanel = new JPanel(new GridBagLayout());
        JPanel manualPanel = new JPanel(new GridBagLayout());
        JPanel checkInputPanel = new JPanel(new GridBagLayout());
        JPanel schooldetailsPanel = new JPanel(new GridBagLayout());
        JPanel tryConnection = new JPanel(new GridBagLayout());
        JPanel nofreePanel = new JPanel(new GridBagLayout()); 
        JTextField tfschoolname;
        JTextField check_school;
        JTextField check_postcode;
        JTextField tfpostcode;
        JTextPane inputwarnlabel1;
        // for school name and postcode turn all into lowercase and strip off anything that isn't a letter or number
        String strschoolname = null;
        String strpostcode = null;
        String strresponse_user = null;
        String strresponse = null;
        String strrequest = null;
        String straddnote = null;
        boolean okpressed = false;
        String str_ok = u.gettext("OK", "label");
        String str_cancel = u.gettext("cancel", "label");
        String str_notauth1 = u.gettext("webauthenticate", "notauthenticate1");
        String str_notauth2 = u.gettext("webauthenticate", "notauthenticate2");
        String str_notauth3 = u.gettext("webauthenticate", "notauthenticate3");
        String str_tryauth = u.gettext("webauthenticate", "tryauthenticate");
        String str_4chars = u.gettext("webauthenticate", "enter4characters");
        String str_nonote = u.gettext("webauthenticate", "nonote");
        String str_tryconnect = u.gettext("webauthenticate", "tryconnect");
        String str_authenticatesite = u.gettext("webauthenticate", "authenticatesite");
        String str_authenticatesite_display = (shark.language.equals(shark.LANGUAGE_NL) ? u.gettext("webauthenticate", "authenticatesite_display") : str_authenticatesite);
        String str_viewsite = u.gettext("webauthenticate", "viewsite");
        String str_viewsite_display = (shark.language.equals(shark.LANGUAGE_NL) ? u.gettext("webauthenticate", "viewsite_display") : str_viewsite);
        String str_enterlicencedetails = u.gettext("webauthenticate", "enterlicencedetails");
        String str_pleasevisit = u.gettext("webauthenticate", "pleasevisit");
        String str_school = u.gettext("webauthenticate", "school");
        String str_postcode = u.gettext("webauthenticate", "postcode");
        String str_enterresponse = u.gettext("webauthenticate", "enterresponse");
        boolean hasschool = false;
        JTextField tfAddNote;
        JPanel addNotePanel;
        JLabel addNoteMess;
        JLabel lbInstallWebNote;
        JTextPane hyper2;
        JLabel lbInstallTrack;
        JButton cancel;
        String webadd;
        boolean gotcon;
        String errorcode = "";
        String strredo = u.gettext("webauthenticate", "redomess");
        JPanel checkReqPanel = new JPanel(new GridBagLayout());
        IntroFrame_base thisif = this;
        String manpanNoteStr = u.gettext("webauthenticate", "listmessNote") + SPACES;
        String manpanSchoolStr = u.gettext("webauthenticate", "listmessSchool") + SPACES;
        String manpanPostcodeStr = u.gettext("webauthenticate", "listmessPostcode") + SPACES;
        JLabel manpanLbComputerID = new JLabel();
        String manpanLbComputerStr = u.gettext("webauthenticate", "listmessComputerID") + SPACES;
        String manpanReqStr;
        JTextPane hyperl;
        JPanel compidpanel;
        JButton aprint;
        
        JPanel progressBarPanelRetry;
        final int STATE_ACTIVATE1 = 0;
        final int STATE_NOFREESPACES = 1;
        final int STATE_CONNECTIONFAILED = 2;
        final int STATE_INPUTCHECKEDOK = 3;
        final int STATE_SUCCESS = 4;
        final int STATE_STARTMANUALACTIVATION = 5;
        final int STATE_SCHOOLDETAILS = 6;
        final int STATE_TRYCONNECTION = 7;
        final int STATE_CHECKDETAILS = 8;
        int state = STATE_ACTIVATE1;
        
        AuthUI(boolean upgrade) {
            super(true);
            try {
                if (!upgrade) {
                    doClear();
                }
                isnetextrausers = upgrade;
                addWindowListener(new WindowAdapter() {
                    public void windowOpened(WindowEvent e) {
                        key_1request1.requestFocus();
                    }
                });                
                if (isnetextrausers) {
                    closeonexit = false;
                }
                lbInstallWebNote = new JLabel(u.gettext("webauthenticate", "installno_M"));
                tfAddNote = new JTextField("", addnotecharlen);
                inputwarnlabel1 = new JTextPane();
                inputwarnlabel1.setBorder(BorderFactory.createEmptyBorder());                
                inputwarnlabel1.setContentType("text/html");
                inputwarnlabel1.setText(BLANK);                
                inputwarnlabel1.setOpaque(false);
                inputwarnlabel1.setEditable(false);
                inputwarnlabel1.addHyperlinkListener(new HyperlinkListener() {
                    public void hyperlinkUpdate(HyperlinkEvent e) {
                        if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                            String s = e.getDescription();
                            String ss[] = u.splitString(s);
                            if (ss.length > 1) {
                                OptionPane_base.getErrorMessageDialog(thisframe, Float.parseFloat(ss[0]), ss[1], OptionPane_base.ERRORTYPE_NOEXIT);
                            } else {
                                OptionPane_base.getErrorMessageDialog(thisframe, Float.parseFloat(ss[0]), null, OptionPane_base.ERRORTYPE_NOEXIT);
                            }
                        }
                    }
                });                    
                tfschoolname = new JTextField("", schoolcharlen);
                tfpostcode = new JTextField("", postcodecharlen);
                compid1 = new JTextField();
                check_request1 = new JTextField("", 4);
                check_request1.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        if (!check_request2.getText().trim().equals("")) {
                            return;
                        }
                        if (!check_request3.getText().trim().equals("")) {
                            return;
                        }
                        if (!check_request4.getText().trim().equals("")) {
                            return;
                        }
                        if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_V) {
                            String s[] = u.getSystemClipboard();
                            if (s != null && s.length == 1) {
                                String r = "";
                                for (int i = 0; i < s[0].length(); i++) {
                                    char c = s[0].charAt(i);
                                    if (Character.isLetterOrDigit(c)) {
                                        r = r + String.valueOf(c);
                                    }
                                }
                                if (r.length() == 16) {
                                    check_request1.setText(r.substring(0, 4));
                                    check_request2.setText(r.substring(4, 8));
                                    check_request3.setText(r.substring(8, 12));
                                    check_request4.setText(r.substring(12));
                                }
                            }
                        }
                    }
                });
                check_request2 = new JTextField("", 4);
                check_request3 = new JTextField("", 4);
                check_request4 = new JTextField("", 4);    
                check_request1.setDocument(new keydoc3(check_request1, check_request2, 4));
                check_request2.setDocument(new keydoc3(check_request2, check_request3, 4));
                check_request3.setDocument(new keydoc3(check_request3, check_request4, 4));
                check_request4.setDocument(new keydoc3(check_request4, null, 4));  
                check_school = new JTextField("", schoolcharlen);
                check_postcode = new JTextField("", postcodecharlen);     
                key_1request1 = new JTextField("", 4);
                key_1request1.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        if (!key_1request2.getText().trim().equals("")) {
                            return;
                        }
                        if (!key_1request3.getText().trim().equals("")) {
                            return;
                        }
                        if (!key_1request4.getText().trim().equals("")) {
                            return;
                        }
                        if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_V) {
                            String s[] = u.getSystemClipboard();
                            if (s != null && s.length == 1) {
                                String r = "";
                                for (int i = 0; i < s[0].length(); i++) {
                                    char c = s[0].charAt(i);
                                    if (Character.isLetterOrDigit(c)) {
                                        r = r + String.valueOf(c);
                                    }
                                }
                                if (r.length() == 16) {
                                    key_1request1.setText(r.substring(0, 4));
                                    key_1request2.setText(r.substring(4, 8));
                                    key_1request3.setText(r.substring(8, 12));
                                    key_1request4.setText(r.substring(12));
                                }
                            }
                        }
                    }
                });
                key_1request2 = new JTextField("", 4);
                key_1request3 = new JTextField("", 4);
                key_1request4 = new JTextField("", 4);
                key_1response1 = new JTextField("", 4);
                key_1response1.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        if (!key_1response2.getText().trim().equals("")) {
                            return;
                        }
                        if (!key_1response3.getText().trim().equals("")) {
                            return;
                        }
                        if (!key_1response4.getText().trim().equals("")) {
                            return;
                        }
                        if (!key_1response5.getText().trim().equals("")) {
                            return;
                        }
                        if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_V) {
                            String s[] = u.getSystemClipboard();
                            if (s != null && s.length == 1) {
                                String r = "";
                                for (int i = 0; i < s[0].length(); i++) {
                                    char c = s[0].charAt(i);
                                    if (Character.isLetterOrDigit(c)) {
                                        r = r + String.valueOf(c);
                                    }
                                }
                                if (r.length() == 20) {
                                    key_1response1.setText(r.substring(0, 4));
                                    key_1response2.setText(r.substring(4, 8));
                                    key_1response3.setText(r.substring(8, 12));
                                    key_1response4.setText(r.substring(12, 16));
                                    key_1response5.setText(r.substring(16));
                                }
                            }
                        }
                    }
                });
                key_1response2 = new JTextField("", 4);
                key_1response3 = new JTextField("", 4);
                key_1response4 = new JTextField("", 4);
                key_1response5 = new JTextField("", 4);
                compid1.setEditable(false);
                grid.weighty = 1;
                grid.weightx = 1;
                grid.fill = GridBagConstraints.NONE;
                grid.gridx = -1;
                grid.gridy = 0;
                requestPanel.add(key_1request1, grid);
                requestPanel.add(new JLabel("-"), grid);
                requestPanel.add(key_1request2, grid);
                requestPanel.add(new JLabel("-"), grid);
                requestPanel.add(key_1request3, grid);
                requestPanel.add(new JLabel("-"), grid);
                requestPanel.add(key_1request4, grid);
                key_1request1.setDocument(new keydoc3(key_1request1, key_1request2, 4));
                key_1request2.setDocument(new keydoc3(key_1request2, key_1request3, 4));
                key_1request3.setDocument(new keydoc3(key_1request3, key_1request4, 4));
                key_1request4.setDocument(new keydoc3(key_1request4, null, 4, true, new JTextField[]{key_1request1, key_1request2, key_1request3, key_1request4}));
                JPanel buttonPanel = new JPanel(new GridBagLayout());
                offline = new JButton(str_offline);
                ok = new JButton(str_ok);
                cancel = new JButton(str_cancel);
                offline.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if (needSchool(strrequest, false)) {
                            state = STATE_SCHOOLDETAILS;
                        } else {
                            state = STATE_STARTMANUALACTIVATION;
                        }
                        changePanel();
                    }
                });
                activateTimer = new javax.swing.Timer(0, new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String res = null;
                        if (timerOverdue < 0) {
                            timerOverdue = System.currentTimeMillis() + waittime;
                        }
                        boolean overdue = System.currentTimeMillis() > timerOverdue;
                        if (activateTimer.getDelay() == timertime && (!activateThread.isAlive() || overdue)) {
                            timerOverdue = -1;
                            activateTimer.setDelay(0);
                            int k;
                            res = nc.returnval;
                            if (overdue) {
                                k = -300;
                            } else {
                                k = autoAuthenticateReturn(res);
                            }
                            nc = null;
                            activateThread = null;
                            activateTimer.stop();
                            if (state == STATE_ACTIVATE1) {
                                if (overdue) {
                                    k = -1;
                                }
                                if (k >= 0) {
                                    state = STATE_SUCCESS;
                                    return;
                                } else if (k == ERRORNOFREESPACES) {
                                    state = STATE_NOFREESPACES;
                                } else if (k == -1) {
                                    state = STATE_CONNECTIONFAILED;
                                } else {
                                    String s = "";
                                    if (k != ERRORVOID) {
                                        s = str_notauth2;
                                    }
                                    inputwarnlabel1.setText(u.getHTMLWithFontCSS(UIManager.getFont("Label.font"), getErrorCodeText(str_notauth2, k), errorColor));
                                    state = STATE_ACTIVATE1;
                                }
                                changePanel();
                            } else if (state == STATE_TRYCONNECTION) {
                                if (overdue) {
                                    k = -1;
                                }
                                if (k >= 0) {
                                    state = STATE_SUCCESS;
                                } else if (k == ERRORNOFREESPACES) {
                                    state = STATE_NOFREESPACES;
                                } else if (k == -1) {
                                    state = STATE_CONNECTIONFAILED;
                                } else {
                                    inputwarnlabel1.setText(u.getHTMLWithFontCSS(UIManager.getFont("Label.font"), getErrorCodeText(str_notauth2, k), errorColor));
                                    state = STATE_ACTIVATE1;
                                }
                                changePanel();
                            }
                        } else if (activateTimer.getDelay() == 0) {
                            if (state == STATE_ACTIVATE1) {
                                inputwarnlabel1.setText(u.getHTMLWithFontCSS(UIManager.getFont("Label.font"), str_tryauth, errorColor));
                            }
                            autoAuthenticate();
                            activateTimer.setDelay(timertime);
                        }
                    }
                });
                ok.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        errorcode = "";
                        int k = updateinput();
                        okpressed = true;
                        inputwarnlabel1.setText(BLANK);
                        switch (state){
                            case STATE_ACTIVATE1:
                                if (k < 0) {
                                    if (k == -2) {
                                        inputwarnlabel1.setText(u.getHTMLWithFontCSS(UIManager.getFont("Label.font"), str_notauth3, errorColor));
                                    }
                                    if (k == -3) {
                                        inputwarnlabel1.setText(u.getHTMLWithFontCSS(UIManager.getFont("Label.font"), str_4chars, errorColor));
                                    } else {
                                        inputwarnlabel1.setText(u.getHTMLWithFontCSS(UIManager.getFont("Label.font"), str_notauth2, errorColor));
                                    }
                                    return;
                                }
                                if (!shark.singledownload && tfAddNote.getText().trim().equals("")) {
                                    inputwarnlabel1.setText(u.getHTMLWithFontCSS(UIManager.getFont("Label.font"), str_nonote, errorColor));
                                    return;
                                }                            
                                state = STATE_TRYCONNECTION;
                                changePanel();
                                activateTimer.start();                                
                                break;
                            case STATE_STARTMANUALACTIVATION:
                                ok.setText(u.gettext("retry", "label"));
                                if (k < 0) {
                                    inputwarnlabel1.setText(u.getHTMLWithFontCSS(UIManager.getFont("Label.font"), str_4chars, errorColor));
                                    return;
                                } else {
                                    inputwarnlabel1.setText(BLANK);
                                }
                                // if authenticated
                                if ((k = manualAuthenticate()) >= 0) {
                                    state = STATE_SUCCESS;
                                    changePanel();
                                } else {
                                    errorcode = getErrorCodeText(str_notauth1, k);
                                    inputwarnlabel1.setText(u.getHTMLWithFontCSS(UIManager.getFont("Label.font"), errorcode, errorColor));
                                }                                
                                break;  
                            case STATE_CHECKDETAILS:
                                if (k < 0) {
                                    if (k == -2) {
                                        inputwarnlabel1.setText(u.getHTMLWithFontCSS(UIManager.getFont("Label.font"), str_notauth3, errorColor));
                                    }
                                    if (k == -3) {
                                        inputwarnlabel1.setText(u.getHTMLWithFontCSS(UIManager.getFont("Label.font"), str_4chars, errorColor));
                                    } else {
                                        inputwarnlabel1.setText(u.getHTMLWithFontCSS(UIManager.getFont("Label.font"), str_notauth2, errorColor));
                                    }
                                    return;
                                }
                                inputwarnlabel1.setText(BLANK);
                                changePanel();                              
                                break;
                            case STATE_SCHOOLDETAILS:
                                if (!goodInput()) {                                
                                    check_request1.setText(key_1request1.getText());
                                    check_request2.setText(key_1request2.getText());
                                    check_request3.setText(key_1request3.getText());
                                    check_request4.setText(key_1request4.getText());
                                    check_school.setText(tfschoolname.getText());
                                    check_postcode.setText(tfpostcode.getText());                                
                                    state = STATE_CHECKDETAILS;
                                } else {
                                    state = STATE_STARTMANUALACTIVATION;
                                }
                                changePanel();                              
                                break;
                            case STATE_SUCCESS:
                                end();                              
                                break;
                            case STATE_NOFREESPACES:
                                System.exit(0);                           
                                break;
                        }
                    }
                });
                cancel.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        thisjd.dispose();
                    }
                });
                JPanel schoolpanel = new JPanel(new GridBagLayout());
                JPanel postcodepanel = new JPanel(new GridBagLayout());
                JLabel schoolnamelabel = new JLabel(str_school + SPACES);
                JLabel postcodelabel = new JLabel(str_postcode + SPACES);
                grid.gridx = -1;
                grid.gridy = 0;
                String temps1 = (String) db.find(sharkStartFrame.optionsdb, "temp_school", db.TEXT);
                String temps2 = (String) db.find(sharkStartFrame.optionsdb, "temp_postcode", db.TEXT);
                if (temps1 != null) {
                    strschoolname = temps1;
                    tfschoolname.setText(temps1);
                }
                if (temps2 != null) {
                    strpostcode = temps2;
                    tfpostcode.setText(temps2);
                }
                schoolpanel.add(schoolnamelabel, grid);
                schoolpanel.add(tfschoolname, grid);
                grid.gridx = -1;
                grid.gridy = 0;
                postcodepanel.add(postcodelabel, grid);
                postcodepanel.add(tfpostcode, grid);
                grid.gridx = 0;
                grid.gridy = -1;
                grid.insets = new Insets(0, 0, border * 2, 0);
                schooldetailsPanel.add(new JLabel(u.gettext("webauthenticate", "pleaseenter")), grid);
                grid.insets = new Insets(0, 0, border, 0);
                grid.anchor = GridBagConstraints.WEST;
                schooldetailsPanel.add(schoolpanel, grid);
                schooldetailsPanel.add(postcodepanel, grid);
                grid.anchor = GridBagConstraints.CENTER;
                aprint = new JButton(u.gettext("printtext", "label"));
                final String manpanReqStr = shark.singledownload ? u.gettext("webauthenticate", "listmessLK_home") : u.gettext("webauthenticate", "listmessLK_school");
                aprint.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        int listcount = 1;
                        printDetails pr = new printDetails(
                                str_enterlicencedetails,
                                u.edit(manpanReqStr, String.valueOf(listcount++)),
                                null,
                                shark.singledownload?null:u.edit(manpanSchoolStr, String.valueOf(listcount++)) + SPACES + strschoolname,
                                null,
                                (straddnote == null || straddnote.trim().equals("")) ? null : u.edit(manpanNoteStr, String.valueOf(listcount++)) + SPACES + straddnote,
                                null,
                                u.edit(manpanLbComputerStr, String.valueOf(listcount++)),
                                compid1.getText().replaceAll("-", "").replaceAll(" ", ""),
                                webadd, printDetails.TYPEAUTH);
                        pr.printD();
                    }
                });
                aprint.setVisible(false);
                grid.gridx = -1;
                grid.gridy = 0;
                grid.insets = new Insets(0, 0, 0, border);
                if (shark.macOS) {
                    buttonPanel.add(cancel, grid);
                    buttonPanel.add(aprint, grid);
                    buttonPanel.add(ok, grid);
                    buttonPanel.add(offline, grid);
                } else {
                    buttonPanel.add(offline, grid);
                    buttonPanel.add(ok, grid);
                    buttonPanel.add(aprint, grid);
                    buttonPanel.add(cancel, grid);
                }
                grid.gridx = 0;
                grid.gridy = 0;
                addNotePanel = new JPanel(new GridBagLayout());
                addNotePanel.add(new JLabel(u.gettext("webauthenticate", "entercomment")), grid);
                tfAddNote.setDocument(new maxLengthDocument(addnotecharlen));
                grid.gridx = 1;
                grid.insets = new Insets(0, 0, border, 0);
                addNotePanel.add(tfAddNote, grid);
                grid.gridy = 1;
                grid.insets = new Insets(0, 0, 0, 0);
                addNoteMess = new JLabel(u.toHtml(u.gettext("webauthenticate", "installcommenthelp")));
                addNotePanel.add(addNoteMess, grid);
                grid.gridx = 0;
                grid.gridy = -1;
                grid.insets = new Insets(0, 0, border, 0);
                reqPanel.add(new JLabel(u.gettext("webauthenticate", "enterrequest")), grid);
                reqPanel.add(new JLabel(u.gettext("webauthenticate", "remprinted")), grid);
                reqPanel.add(requestPanel, grid);
                temps2 = (String) db.find(sharkStartFrame.optionsdb, "temp_note", db.TEXT);
                if (temps2 != null) {
                    straddnote = temps2;
                    tfAddNote.setText(temps2);
                }
                grid.gridx = -1;
                grid.gridy = 0;
                grid.gridx = 0;
                grid.gridy = -1;
                reqPanel.add(addNotePanel, grid);
                addNotePanel.setVisible(false);
                hyperl = new JTextPane();
                hyperl.setBorder(BorderFactory.createEmptyBorder());
                hyperl.setContentType("text/html");
                webadd = str_httpactivation.replaceFirst("http://", "") + str_authenticatesite_display;
                hyperl.setEditable(false);
                hyperl.setCursor(new Cursor(Cursor.HAND_CURSOR));
                hyperl.setOpaque(false);
                hyperl.addHyperlinkListener(new HyperlinkListener() {
                    public void hyperlinkUpdate(HyperlinkEvent e) {
                        if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                            String s = e.getDescription();
                            if (strschoolname != null) {
                                s = s.replaceFirst("%%%", u.convertForURL(strschoolname));
                            }
                            s = s.replaceFirst("%%%", u.convertForURL(strrequest));
                            u.launchWebSite(s);
                        }
                    }
                });
                key_1response1.setDocument(new keydoc3(key_1response1, key_1response2, 4));
                key_1response2.setDocument(new keydoc3(key_1response2, key_1response3, 4));
                key_1response3.setDocument(new keydoc3(key_1response3, key_1response4, 4));
                key_1response4.setDocument(new keydoc3(key_1response4, key_1response5, 4));
                key_1response5.setDocument(new keydoc3(key_1response5, null, 4));
                grid.gridx = -1;
                grid.gridy = 0;
                grid.insets = new Insets(0, 0, 0, 0);
                responsePanel.add(key_1response1, grid);
                responsePanel.add(new JLabel("-"), grid);
                responsePanel.add(key_1response2, grid);
                responsePanel.add(new JLabel("-"), grid);
                responsePanel.add(key_1response3, grid);
                responsePanel.add(new JLabel("-"), grid);
                responsePanel.add(key_1response4, grid);
                responsePanel.add(new JLabel("-"), grid);
                responsePanel.add(key_1response5, grid);
                JProgressBar pBarRetry;
                pBarRetry = new JProgressBar();
                pBarRetry.setIndeterminate(true);
                progressBarPanelRetry = new JPanel(new GridBagLayout());
                grid.gridx = 0;
                grid.gridy = -1;
                JLabel retrywarnlabel;
                retrywarnlabel = new JLabel(str_tryconnect);
                retrywarnlabel.setForeground(Color.red);
                progressBarPanelRetry.add(retrywarnlabel, grid);
                progressBarPanelRetry.add(new JLabel(BLANK), grid);
                progressBarPanelRetry.add(pBarRetry, grid);
                progressBarPanelRetry.add(new JLabel(BLANK), grid);
                grid.gridx = -1;
                grid.gridy = 0;
                progressBarPanelRetry.setVisible(false);
                JTextPane tpTryConnect;
                tpTryConnect = new JTextPane();                
                tpTryConnect.setBorder(BorderFactory.createEmptyBorder());                
                tpTryConnect.setContentType("text/html");
                if (shark.singledownload) {
                    tpTryConnect.setText(u.getHTMLWithFontCSS(UIManager.getFont("Label.font"), u.gettext("webauthenticate", "tryconnectmess_home")));
                } else {
                    tpTryConnect.setText(u.getHTMLWithFontCSS(UIManager.getFont("Label.font"), u.gettext("webauthenticate", "tryconnectmess_school")));
                }
                tpTryConnect.setOpaque(false);
                tpTryConnect.setEditable(false);
                tpTryConnect.addHyperlinkListener(new HyperlinkListener() {
                    public void hyperlinkUpdate(HyperlinkEvent e) {
                        if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                            String s = e.getDescription();
                            switch (s){
                                case "0":
                                    updateinput();
                                    state = STATE_TRYCONNECTION;
                                    changePanel();
                                    activateTimer.start(); 
                                    break;
                                case "1":
                                    new ProxySettings(thisjd);
                                    break;
                                case "2":
                                    String s1;
                                    if (shark.macOS) {
                                        s1 = u.gettext("webauthenticate", "helpmessmac", str_offline);
                                    } else {
                                        s1 = u.edit(u.gettext("webauthenticate", "helpmess"),
                                                new String[]{
                                                    System.getProperty("java.home") + File.separator + "bin" + File.separator + "javaw.exe",
                                                    str_offline});
                                    }                                
                                    showtext(thisjd, u.gettext("webauthenticate", "helptitle"), s1); 
                                    break;
                            }
                        }
                    }
                });
                
                tryConnection.add(tpTryConnect, grid);
                grid.insets = new Insets(0, 0, 0, 0);
                grid.insets = new Insets(0, 0, 0, 0);
                grid.gridx = 0;
                grid.gridy = -1;
                successPanel.add(new JLabel(u.gettext("webauthenticate", "success")), grid);
                successPanel.add(new JLabel(BLANK), grid);
                successPanel.add(lbInstallWebNote, grid);
                hyper2 = new JTextPane();
                hyper2.setBorder(BorderFactory.createEmptyBorder());
                hyper2.setContentType("text/html");
                hyper2.setEditable(false);
                hyper2.setCursor(new Cursor(Cursor.HAND_CURSOR));
                hyper2.setOpaque(false);
                hyper2.addHyperlinkListener(new HyperlinkListener() {
                    public void hyperlinkUpdate(HyperlinkEvent e) {
                        if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                            u.launchWebSite(e.getDescription());
                        }
                    }
                });
                successPanel.add(hyper2, grid);
                grid.gridx = 0;
                grid.gridy = -1;
                nofreePanel.add(new JLabel(u.gettext("webauthenticate", "allinstalled")), grid);
                grid.gridx = -1;
                grid.gridy = 0;
                compidpanel = new JPanel(new GridBagLayout());
                compidpanel.add(manpanLbComputerID, grid);
                compidpanel.add(compid1, grid);
                compidpanel.setOpaque(false);
                grid.gridx = -1;
                grid.gridy = 0;
                checkReqPanel.add(new JLabel(u.gettext("webauthenticate", "request") + SPACES), grid);
                checkReqPanel.add(check_request1, grid);
                checkReqPanel.add(new JLabel("-"), grid);
                checkReqPanel.add(check_request2, grid);
                checkReqPanel.add(new JLabel("-"), grid);
                checkReqPanel.add(check_request3, grid);
                checkReqPanel.add(new JLabel("-"), grid);
                checkReqPanel.add(check_request4, grid);
                JPanel checkSchoolPanel = new JPanel(new GridBagLayout());
                grid.gridx = -1;
                grid.gridy = 0;
                checkSchoolPanel.add(new JLabel(str_school + SPACES), grid);
                checkSchoolPanel.add(check_school, grid);
                JPanel checkPostcodePanel = new JPanel(new GridBagLayout());
                checkPostcodePanel.add(new JLabel(str_postcode + SPACES), grid);
                checkPostcodePanel.add(check_postcode, grid);
                grid.gridx = 0;
                grid.gridy = -1;
                grid.insets = new Insets(0, 0, border * 2, 0);
                checkInputPanel.add(new JLabel(u.gettext("webauthenticate", "pleaseenter")), grid);
                grid.insets = new Insets(0, 0, border, 0);
                checkInputPanel.add(checkReqPanel, grid);
                checkInputPanel.add(checkSchoolPanel, grid);
                checkInputPanel.add(checkPostcodePanel, grid);
                grid.insets = new Insets(0, 0, 0, 0);
                grid.gridx = -1;
                grid.gridy = 0;
                manualPanel.setVisible(false);
                JPanel contentpanes = new JPanel(new GridBagLayout());
                contentpanes.add(reqPanel, grid);
                contentpanes.add(schooldetailsPanel, grid);
                contentpanes.add(manualPanel, grid);
                contentpanes.add(checkInputPanel, grid);
                contentpanes.add(tryConnection, grid);
                contentpanes.add(progressBarPanelRetry, grid);
                contentpanes.add(successPanel, grid);
                contentpanes.add(nofreePanel, grid);
                grid.gridx = -1;
                grid.gridy = 0;
                int sh = (int) sharkStartFrame.screenSize.getHeight();
                int warnLbTop = sh * 1 / 60;
                int warnLbBottom = sh * 2 / 60;                
                grid.insets = new Insets(warnLbTop, 0, warnLbBottom, 0);
                JPanel inputwarnpanel = new JPanel(new GridBagLayout());
                inputwarnpanel.add(new JLabel(" "), grid);
                inputwarnpanel.add(inputwarnlabel1, grid);
                inputwarnpanel.add(new JLabel(" "), grid);
                grid.insets = new Insets(0, 0, 0, 0);
                grid.gridx = 0;
                grid.gridy = -1;
                addToBase(contentpanes, grid);
                grid.weighty = 0;
                addToBase(inputwarnpanel, grid);
                grid.insets = new Insets(border, 0, 0, 0);
                addToBase(buttonPanel, grid);
                successPanel.setVisible(false);
                tryConnection.setVisible(false);
                offline.setVisible(false);
                schooldetailsPanel.setVisible(false);
                checkInputPanel.setVisible(false);
                nofreePanel.setVisible(false);
                getRootPane().setDefaultButton(ok);
                ok.requestFocus();
                key_1request1.requestFocus();
                thisjd = this;
                String title = u.gettext("webauthenticate", "title");
                String dk;
                if ((dk = wab.doGetXMLElementValue(sharkStartFrame.sharedPathplus + u2_base.saXmlFileName, ELEMENT_DEACTIVATIONKEY)) != null && !dk.trim().equals("")) {
                    title += SPACES + "(" + dk + ")";
                }
                setTitle(title);
                setColor();
                setVisible(true);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error in activation initialization", shark.programName, JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
            }
        }
        
        void setRequest(String s) {
            strrequest = s;
            String display_mac = getDisplayId(strrequest, manualMac, currno);
            String ss[] = u.splitString(display_mac, '|');
            compid1.setText(ss[0] + " - " + ss[1] + " - " + ss[2]);
        }
        
        void setHyperlinkText(boolean hasschool) {
            String display_mac = getDisplayId(strrequest, manualMac, currno);
            String ss[] = u.splitString(display_mac, '|');
            String note = null;
            if (straddnote != null && !straddnote.trim().equals("")) {
                note = u.convertForURL(straddnote);
            }
            // www.wordshark.co.uk/Activation/SignIn?Activate
            String strurl = buildURL(
                    str_httpactivation,
                    u.gettext("webauthenticate", "redirectsite"),
                    str_authenticatesite,
                    hasschool?"%%%":null,
                    "%%%",
                    null,
                    ss[0] + ss[1] + ss[2],
                    note);
            hyperl.setText(u.getHTMLWithFontCSS(UIManager.getFont("Label.font"), "<a href=" + strurl + ">" + webadd + "</a>"));
        }       
        
        private boolean goodInput() {
            boolean problem = false;
            String rs = null;
            String s = null;
            try {
                rs = wab.unChop16(strrequest);
                rs = u2_base.swapString(rs, wab.userkey, wab.realkey);
                s = getSchoolCode(strschoolname, strpostcode);
            } catch (Exception ex) {
                problem = true;
            }
            // the input of school/postcode/licence key is incorrect
            return (!(problem || rs == null || s == null || !s.equals(rs.substring(11))));
        }
        
        private void changePanel() {
            switch (state) {
                case STATE_ACTIVATE1:
                    progressBarPanelRetry.setVisible(false);
                    reqPanel.setVisible(true);
                    cancel.setVisible(true);                    
                    ok.setVisible(true);                    
                    break;
                case STATE_NOFREESPACES:
                    progressBarPanelRetry.setVisible(false);
                    nofreePanel.setVisible(true);
                    reqPanel.setVisible(false);
                    cancel.setVisible(false);                    
                    tryConnection.setVisible(false);
                    offline.setVisible(false);
                    ok.setVisible(true);                    
                    break;
                case STATE_TRYCONNECTION:
                    ok.setVisible(false);
                    progressBarPanelRetry.setVisible(true);
                    reqPanel.setVisible(false);
                    tryConnection.setVisible(false);
                    offline.setVisible(false);                    
                    break;
                case STATE_CONNECTIONFAILED:
                    tryConnection.setVisible(true);
                    ok.setVisible(false);
                    offline.setVisible(true);
                    reqPanel.setVisible(false);
                    progressBarPanelRetry.setVisible(false);
                    break;
                case STATE_SUCCESS:
                    progressBarPanelRetry.setVisible(false);
                    reqPanel.setVisible(false);
                    tryConnection.setVisible(false);
                    cancel.setVisible(false);
                    offline.setVisible(false);
                    ok.setVisible(true);
                    ok.setText(str_ok);
                    getRootPane().setDefaultButton(ok);
                    successPanel.setVisible(true);
                    inputwarnlabel1.setText(BLANK);
                    setSuccessLabel();
                    closeonexit = false;                    
                    aprint.setVisible(false);
                    manualPanel.setVisible(false);
                    this.setColor();                    
                    break;
                case STATE_STARTMANUALACTIVATION:
                    buildManualPanel();
                    reqPanel.setVisible(false);
                    tryConnection.setVisible(false);
                    offline.setVisible(false);
                    aprint.setVisible(true);
                    ok.setVisible(true);
                    getRootPane().setDefaultButton(ok);
                    inputwarnlabel1.setText(BLANK);
                    schooldetailsPanel.setVisible(false);
                    key_1response1.requestFocus();
                    manualPanel.setVisible(true);                    
                    break;
                case STATE_SCHOOLDETAILS:
                    reqPanel.setVisible(false);
                    tryConnection.setVisible(false);
                    offline.setVisible(false);
                    ok.setVisible(true);
                    getRootPane().setDefaultButton(ok);
                    schooldetailsPanel.setVisible(true);
                    tfschoolname.requestFocus();
                    aprint.setVisible(false);
                    manualPanel.setVisible(false);
                    this.setColor();
                    break;
                case STATE_CHECKDETAILS:
                    addNotePanel.setVisible(false);
                    schooldetailsPanel.setVisible(false);
                    checkInputPanel.setVisible(true);                    
                    if (goodInput()) {
                        checkInputPanel.setVisible(false);
                        key_1response1.requestFocus();
                        inputwarnlabel1.setText(BLANK);                        
                        aprint.setVisible(true);
                        manualPanel.setVisible(false);
                        this.setColor();                        
                        state = STATE_STARTMANUALACTIVATION;
                        changePanel();
                    } else {
                        inputwarnlabel1.setText(u.getHTMLWithFontCSS(UIManager.getFont("Label.font"), strredo, errorColor));
                    }
                    break;
            }
            okpressed = false;
        }
        
        private void buildManualPanel() {
            manualPanel.removeAll();
            manpanReqStr = shark.singledownload ? u.gettext("webauthenticate", "listmessLK_home") : u.gettext("webauthenticate", "listmessLK_school");
            grid.gridx = -1;
            grid.gridy = 0;
            grid.insets = new Insets(0, 0, 0, 0);
            JPanel mpSchoolPan;
            JPanel mpPostcodePan;
            JPanel mpNotePan;
            mpSchoolPan = new JPanel(new GridBagLayout());
            mpSchoolPan.setOpaque(false);
            JTextField manpanFtSchool = new JTextField();
            manpanFtSchool.setEditable(false);
            JLabel manpanLbSchool = new JLabel();
            mpSchoolPan.add(manpanLbSchool, grid);
            mpSchoolPan.add(manpanFtSchool, grid);
            mpPostcodePan = new JPanel(new GridBagLayout());
            mpPostcodePan.setOpaque(false);
            JTextField manpanFtPostcode = new JTextField();
            manpanFtPostcode.setEditable(false);
            JLabel manpanLbPostcode = new JLabel();
            mpPostcodePan.add(manpanLbPostcode, grid);
            mpPostcodePan.add(manpanFtPostcode, grid);
            mpNotePan = new JPanel(new GridBagLayout());
            mpNotePan.setOpaque(false);
            JTextField manpanFtNote = new JTextField();
            manpanFtNote.setEditable(false);
            JLabel manpanLbNote = new JLabel();
            mpNotePan.add(manpanLbNote, grid);
            mpNotePan.add(manpanFtNote, grid);            
            int listcount = 1;
            JLabel manpanLbReq = new JLabel();
            manpanLbReq.setText(u.edit(manpanReqStr, String.valueOf(listcount++)));
            boolean showpostcode = false;
            boolean shownote = false;
            if (!shark.singledownload) {
                manpanLbSchool.setText(u.edit(manpanSchoolStr, new String[]{String.valueOf(listcount++)}));
                manpanFtSchool.setText(strschoolname);
                shownote = (straddnote != null && !straddnote.trim().equals(""));
                if (showpostcode) {
                    manpanLbPostcode.setText(u.edit(manpanPostcodeStr, new String[]{String.valueOf(listcount++)}));
                    manpanFtPostcode.setText(strpostcode);
                }
                if (shownote) {
                    manpanLbNote.setText(u.edit(manpanNoteStr, new String[]{String.valueOf(listcount++)}));
                    manpanFtNote.setText(straddnote);
                }
            }
            manpanLbComputerID.setText(u.edit(manpanLbComputerStr, String.valueOf(listcount++)));
            JPanel pnGoTO = new JPanel(new GridBagLayout());
            pnGoTO.setOpaque(false);
            grid.gridx = -1;
            grid.gridy = 0;
            grid.insets = new Insets(0, 0, 0, border);
            pnGoTO.add(new JLabel(str_pleasevisit), grid);
            pnGoTO.add(hyperl, grid);
            int vertgapbig = border * 12 / 5;
            int vertsmall = border / 2;
            int vertgap = border * 7 / 10;            
            grid.gridx = 0;
            grid.gridy = -1;
            grid.insets = new Insets(0, 0, vertsmall, 0);
            manualPanel.add(pnGoTO, grid);
            grid.insets = new Insets(0, 0, vertgapbig, 0);
            manualPanel.add(new JLabel(u.gettext("webauthenticate", "sepdevice")), grid);
            grid.insets = new Insets(0, 0, vertgap, 0);
            manualPanel.add(new JLabel(str_enterlicencedetails), grid);
            grid.insets = new Insets(0, 0, vertgap, 0);
            JPanel pnLK = new JPanel(new GridBagLayout());
            pnLK.setOpaque(false);
            if (shark.singledownload) {
                grid.gridx = -1;
                grid.gridy = 0;
                grid.insets = new Insets(0, 0, vertgap, border);
                pnLK.add(manpanLbReq, grid);
                grid.insets = new Insets(0, 0, vertgap, 0);
                JTextField tfLK = new JTextField();
                tfLK.setEditable(false);
                String lkwh = strrequest.substring(0, 4) + " - "
                        + strrequest.substring(4, 8) + " - "
                        + strrequest.substring(8, 12) + " - "
                        + strrequest.substring(12);
                tfLK.setText(lkwh);
                pnLK.add(tfLK, grid);
            } else {
                pnLK.add(manpanLbReq, grid);
            }            
            grid.gridx = 0;
            grid.gridy = -1;            
            manualPanel.add(pnLK, grid);
            if (!shark.singledownload) {
                manualPanel.add(mpSchoolPan, grid);
                if (showpostcode) {
                    manualPanel.add(mpPostcodePan, grid);
                }
                if (shownote) {
                    manualPanel.add(mpNotePan, grid);
                }
            }
            grid.insets = new Insets(0, 0, vertgapbig, 0);
            manualPanel.add(compidpanel, grid);
            grid.insets = new Insets(0, 0, vertsmall, 0);
            manualPanel.add(new JLabel(str_enterresponse), grid);
            grid.insets = new Insets(0, 0, 0, 0);
            responsePanel.setOpaque(false);
            manualPanel.add(responsePanel, grid);            
        }
        
        private int checkUserOK(String s) {
            char c[] = s.toCharArray();
            for (int i = 0; i < c.length; i++) {
                if (wab.userkey.indexOf(String.valueOf(c[i])) < 0) {
                    return -1;
                }
            }
            //check if same type of program and same major version as before
            String realrequest = wab.unChop16(u2_base.swapString(s, wab.userkey, wab.realkey));
            if (!shark.ACTIVATE_PREFIX.equals(realrequest.substring(4, 6))) {
                return -2;
            }
            int t = shark.network ? 0 : 1;
            int t2 = (realrequest.charAt(0) == 'N') ? 0 : 1;
            if (t != t2) {
                return -2;
            }
            setRequest(s);
            return 0;
        }
        
        public void end() {
            doend(isnetextrausers);
            dispose();
        }
        
        public int updateinput() {
            String a1, a2, a3, a4, a5;
            int h;
            if (state == STATE_ACTIVATE1) {
                if (checkinput(requestPanel) && (a1 = key_1request1.getText()).length() == 4
                        && (a2 = key_1request2.getText()).length() == 4
                        && (a3 = key_1request3.getText()).length() == 4
                        && (a4 = key_1request4.getText()).length() == 4) {
                    if ((h = checkUserOK(a1 + a2 + a3 + a4)) < 0) {
                        return h;
                    }
                    hasschool = needSchool(strrequest, false);
                    String s;
                    straddnote = ((s = tfAddNote.getText().trim()).equals("")) ? null : s;
                    setHyperlinkText(hasschool);
                    db.update(sharkStartFrame.optionsdb, "temp_note", straddnote, db.TEXT);
                } else {
                    return -3;
                }
            } else if (state == STATE_STARTMANUALACTIVATION) {
                if (checkinput(responsePanel) && (a1 = key_1response1.getText()).length() == 4
                        && (a2 = key_1response2.getText()).length() == 4
                        && (a3 = key_1response3.getText()).length() == 4
                        && (a4 = key_1response4.getText()).length() == 4
                        && (a5 = key_1response5.getText()).length() == 4) {
                    strresponse_user = a1 + a2 + a3 + a4 + a5;
                    strresponse = wab.unmakeCode2(wab.userkey, manualMac, wab.unChop20(strresponse_user));
                } else {
                    return -3;
                }
            } else if (state == STATE_CHECKDETAILS) {
                if (checkinput(checkReqPanel) && (a1 = check_request1.getText()).length() == 4
                        && (a2 = check_request2.getText()).length() == 4
                        && (a3 = check_request3.getText()).length() == 4
                        && (a4 = check_request4.getText()).length() == 4) {
                    setRequest(a1 + a2 + a3 + a4);
                    strschoolname = check_school.getText().trim();
                    strpostcode = check_postcode.getText().trim();
                    db.update(sharkStartFrame.optionsdb, "temp_school", strschoolname, db.TEXT);
                    db.update(sharkStartFrame.optionsdb, "temp_postcode", strpostcode, db.TEXT);
                } else {
                    return -3;
                }
            } else if (state == STATE_SCHOOLDETAILS) {
                a1 = tfschoolname.getText().trim();
                if (a1.equals("")) {
                    return -1;
                } else {
                    strschoolname = a1;
                    strpostcode = tfpostcode.getText().trim();
                    db.update(sharkStartFrame.optionsdb, "temp_school", strschoolname, db.TEXT);
                    db.update(sharkStartFrame.optionsdb, "temp_postcode", strpostcode, db.TEXT);
                }
            }
            return 0;
        }
        
        private int manualAuthenticate() {
            int t = currno + 1;
            t = t % wab.responseuserkey.length();
            String computerResponseCode = buildcomputerresponse(strrequest, manualMac, strschoolname,
                    strpostcode, wab.responseuserkey.substring(t, t + 1));
            if (checkResponseKey(computerResponseCode, strresponse) == 0) {
                int k;
                if ((k = doAuthenticate(false, strresponse)) >= 0) {
                    return 0;
                } else {
                    return k;
                }
            }
            return ERRORBADRESPONSE;
        }
        
        private void autoAuthenticate() {
            authenticationService(strrequest, getPlatform(), straddnote);
        }
        
        private int autoAuthenticateReturn(String ss) {
            if (ss == null) {
                return -1;
            }
            String split[] = ss.split("::");
            int k;
            try {
                if (((k = Integer.parseInt(split[0]))) < 0) {
                    return k;
                }
            } catch (Exception e) {
            }
            ss = wab.unmakeCode2(wab.userkey, maccodesautolist, split[0]);
            if (split.length > 1) {
                if (split.length > 1 && !split[1].equals("")) {
                    strschoolname = split[1];
                }
                if (split.length > 2 && !split[2].equals("")) {
                    strpostcode = split[2];
                }
            }
            String tempss = ss.substring(6, 9);
            strinstallno = String.valueOf(Integer.parseInt(u2_base.swapString(tempss, wab.responseuserkey, wab.responserealkey)));
            k = doAuthenticate(true, ss);
            if (k < 0) {
                return k;
            }
            return 0;
        }
        
        private void checkResponseWarnings(String response) {
            wab.isNonDeactivatable(response);
            wab.isSplitLicence(response);
            if (wab.hasExpired(response)) {
                closeonexit = false;
                dispose();
            }
        }

        // when authentication first achieved
        private int doAuthenticate(boolean auto, String response) {
            doClear();
            wab.doSetXMLElementValue(sharkStartFrame.sharedPathplus + u2_base.saXmlFileName, ELEMENT_DEACTIVATIONKEY, null);
            if (response != null && response.length() == RESPONSELENGTH) {
                String userreq = wab.unChop16(u2_base.swapString(strrequest, wab.userkey, wab.realkey));
                wab.getlicenceinfo(userreq);
                checkResponseWarnings(response);
                updates[RESPONSE] = response;
                updates[REQUEST] = strrequest;
                macupdate = wab.findPossibleResults(realmacs, strrequest, String.valueOf(currno));
                if (strschoolname != null) {
                    updates[SCHOOL] = strschoolname;
                }
                if (strpostcode != null) {
                    updates[LOCATION] = strpostcode;
                }
                updates[NOTE] = straddnote;
                updates[INSTALLNO] = strinstallno;
                doUpdate(auto);
                return 0;
            }
            return -1;
        }


        private void setSuccessLabel() {
            if (wab.isMultSingle(strrequest, false) && !sharkStartFrame.splitlicence) {
                // www.wordshark.co.uk/ACtivation/SignIn?ViewSchoolEtc
                String strurl = buildURL(
                        str_httpactivation,
                        u.gettext("webauthenticate", "redirectsite"),
                        str_viewsite,
                        strschoolname!=null?u.convertForURL(strschoolname):null,
                        u.convertForURL(strrequest),
                        null, null, null);
                // www.wordshark.co.uk/Activation/View
                String webadd2 = str_httpactivation.replaceFirst("http://", "") + str_viewsite_display;
                hyper2.setText(u.getHTMLWithFontCSS(UIManager.getFont("Label.font"), "<a href=" + strurl + ">" + webadd2 + "</a>"));
            } else {
                lbInstallWebNote.setVisible(false);
                hyper2.setVisible(false);
                if (lbInstallTrack != null) {
                    lbInstallTrack.setVisible(false);
                }
            }
        }
        
        private boolean checkinput(JPanel jp) {
            Component c[] = jp.getComponents();
            JTextField tf;
            boolean isok = true;
            for (int i = 0; i < c.length; i++) {
                if (c[i] instanceof JTextField) {
                    tf = (JTextField) c[i];
                    String s;
                    if ((s = tf.getText()).length() == tf.getColumns()) {
                        if (s.indexOf(BLANK) >= 0) {
                            isok = false;
                            break;
                        }
                    } else {
                        isok = false;
                        break;
                    }
                }
            }
            if (okpressed && !isok) {
                inputwarnlabel1.setText(u.getHTMLWithFontCSS(UIManager.getFont("Label.font"), str_4chars, errorColor));
            } else {
                inputwarnlabel1.setText(BLANK);
            }
            return isok;
        }
        
        class keydoc3 extends keydoc {
            boolean completeSignal = false;
            JTextField textfs[];
            int maxchars;
            
            keydoc3(JTextField ow, JTextField ne, int chars) {
                super(ow, ne, chars);
                maxchars = chars;
            }
            
            keydoc3(JTextField ow, JTextField ne, int chars, boolean doCompleteSignal, JTextField[] tfs) {
                super(ow, ne, chars);
                completeSignal = doCompleteSignal;
                maxchars = chars;
                textfs = tfs;
            }
            
            void setwarning(boolean enforce) {
                if (enforce) {
                    inputwarnlabel1.setText(u.getHTMLWithFontCSS(UIManager.getFont("Label.font"), str_4chars, errorColor));
                } else if (checkinput((JPanel) owner.getParent())) {
                    inputwarnlabel1.setText(BLANK);
                }
            }
            
            protected void removeUpdate(DefaultDocumentEvent chng) {
                super.removeUpdate(chng);
                setwarning(true);
            }
            
            public void insertString(int o, String s, AttributeSet a) {
                super.insertString(o, s, a);
                setwarning(false);
                if (completeSignal && isMulitLicence()) {
                    addNotePanel.setVisible(true);
                }
            }
            
            boolean isMulitLicence() {
                if (textfs == null) {
                    return false;
                }
                String lk = "";
                for (int i = 0; i < textfs.length; i++) {
                    String text = textfs[i].getText();
                    if (text.length() != maxchars) {
                        return false;
                    }
                    lk += text;
                }
                shark.singledownload = wab.isSingle(lk, false);
                return wab.isMultSingle(lk, false);
            }
        }
        
        public class maxLengthDocument extends PlainDocument {
            
            int maxChars;
            
            public maxLengthDocument(int max) {
                maxChars = max;
            }
            
            public void insertString(int offset, String s, AttributeSet a) throws BadLocationException {
                if (getLength() + s.length() > maxChars) {
                    Toolkit.getDefaultToolkit().beep();
                    return;
                }
                super.insertString(offset, s, a);
            }
        }
    }
    
    private String getSchoolCode(String school, String postcode) {
        String sptext = school;
        if (postcode != null) {
            sptext = sptext + postcode;
        }
        // make sure all characters are in realkey
        sptext = sptext.replace('I', 'Q');
        sptext = sptext.replace('O', 'J');
        sptext = sptext.replace('U', 'Z');
        sptext = sptext.replace('V', 'X');
        return wab.makeCode(wab.realkey, sptext, 5);
    }
    
    private String buildcomputerresponse(String request, String maclist, String school, String postcode, String maninstallno) {
        String computerResponseCode = wab.makeCode(wab.responseuserkey, wab.encrypt(request + maninstallno, maclist), 20);
        if (school != null) {
            String buildSchoolPostcode = "";
            buildSchoolPostcode = getSchoolCode(school, postcode);
            buildSchoolPostcode = u2_base.swapString(buildSchoolPostcode, wab.realkey, wab.userkey);
            computerResponseCode = computerResponseCode.substring(0, 1) + buildSchoolPostcode + computerResponseCode.substring(6);
        }
        return computerResponseCode;
    }
    
    private void authenticationService(String request, String platform, String addnote) {
        String compname = u.getComputerName();
        webCall(httpsaddress,
                "RequestAuthentication",
                new String[]{"LicenceKey", "Platform", "Note", "Name", "MacAddresses"},
                new String[]{request, platform, addnote, compname, wab.encrypt(u.combineString(realmacs, ","), request)},
                NetClient_base.POST);
    }
    
    private void disableLicence(String request, String maccode, String licenceNo) {
        webCall(httpsaddress,
                "DisableLicence",
                new String[]{"LicenceKey", "MacCode", "InstallNo"},
                new String[]{request, maccode, licenceNo},
                NetClient_base.POST);
    }
    
    private void autoConvertExpiry(String currExpires, String request, String maccode, String licenceNo) {
      // if ok the returns Response Code
        // if not ok returns false
        // if no connection returns null
        webCall(httpsaddress,
                "ConvertExpiry",
                new String[]{"ExpiryDate", "LicenceKey", "MacCode", "InstallNo", "ManInstallNo"},
                new String[]{currExpires, request, maccode, licenceNo, null},
                NetClient_base.GET);
    }

// real - has the string already been chopped and swap to get real characters (not user characters)
    private boolean needSchool(String s, boolean real) {
        if (!real) {
            s = wab.unChop16(s);
            s = u2_base.swapString(s, wab.userkey, wab.realkey);
        }
        return (s.charAt(0) == 'N' || s.charAt(0) == 'M');
    }
    
    public boolean isSingle(String s, boolean real) {
        return wab.isSingle(s, real);
    }
    
    private void deleteTemp() {
        db.delete(sharkStartFrame.optionsdb, "temp_postcode", db.TEXT);
        db.delete(sharkStartFrame.optionsdb, "temp_school", db.TEXT);
        db.delete(sharkStartFrame.optionsdb, "temp_note", db.TEXT);
    }
    
    void getstoremaninstallnos() {
        File f;
        if (DeactivationPath == null || !(f = new File(DeactivationPath)).exists()) {
            OptionPane_base.getErrorMessageDialog(null, 71, null, OptionPane_base.ERRORTYPE_EXIT);
        }
        if (ConfigFilePath == null || !(f = new File(ConfigFilePath)).exists()) {
            OptionPane_base.getErrorMessageDialog(null, 71, null, OptionPane_base.ERRORTYPE_EXIT);
        }
        if (xmlInitallyMissing) {
            if (ConfigFilePath_old != null && (new File(ConfigFilePath_old)).exists()) {
                String ss[] = u.readFile(ConfigFilePath_old);
                if (ss != null && ss.length > 0) {
                    try {
                        wab.doSetXMLElementValue(ConfigFilePath, configElementName, wab.encrypt(wab.decrypt1(ss[0], wab.DeactivationNoKey), wab.DeactivationNoKey));
                    } catch (Exception e) {
                    }
                }
            }            
        }
        String ss = wab.doGetXMLElementValue(ConfigFilePath, configElementName);
//            String s[] = u.readFile(ConfigFilePath);
        if (ss != null) {
            try {
                currno = Integer.parseInt(wab.decrypt1(ss, wab.DeactivationNoKey));
            } catch (Exception e) {
                currno = Integer.parseInt(ss);
            }
        } else {
            currno = 0;
            String n = String.valueOf(currno);
//                while(n.length()<3)n= "0"+n;
            wab.doSetXMLElementValue(ConfigFilePath, configElementName, wab.encrypt(n, wab.DeactivationNoKey));
//                writeFile(ConfigFilePath, wab.encrypt(n, wab.DeactivationNoKey));
        }
    }
    
    private void autodisable() {
//        disableLicence((String) db.find(sharkStartFrame.optionsdb, "wa_request", db.TEXT),
//                maccodesautolist,
//                (String) db.find(sharkStartFrame.optionsdb, "wa_installno", db.TEXT));       
        String ss[] = wab.doGetXMLElementValues(sharkStartFrame.sharedPathplus + u2_base.saXmlFileName, new String[]{ELEMENT_REQUEST, ELEMENT_INSTALLNO});
        disableLicence(ss[0], maccodesautolist, ss[1]);        
        
    }
    
    private boolean enddisable() {
        if (shark.network) {
            String enparam = serverDeactivationFile.substring(0, serverDeactivationFile.indexOf('.'));
            String s[] = u.readFile(sharkStartFrame.sharedPathplus + serverDeactivationFile);
            if (s == null || s.length < 1) {
                return false;
            }
            int currnotemp = -1;
            String en = wab.decrypt1(s[0], enparam);
            try {
                currnotemp = Integer.parseInt(en);
            } catch (Exception e) {                
            }
            if (currnotemp >= 0) {
                currnotemp++;
            }
            String no = String.valueOf(currnotemp);
            while (no.length() < 5) {
                no = "0" + no;
            }
            return writeFile(sharkStartFrame.sharedPathplus + serverDeactivationFile, wab.encrypt(no, enparam));
        } else {
            if (!(new File(sharkStartFrame.optionsdb + ".sha").exists())) {
                return false;
            }
//            String s[] = u.readFile(ConfigFilePath);
            String s = wab.doGetXMLElementValue(ConfigFilePath, configElementName);            
            if (s == null) {
                return false;
            }
            int i;
            try {
                i = Integer.parseInt(wab.decrypt1(s, wab.DeactivationNoKey));
            } catch (Exception e) {
                i = Integer.parseInt(s);
            }
            i++;
//            writeFile(ConfigFilePath, wab.encrypt(String.valueOf(i), wab.DeactivationNoKey));
            wab.doSetXMLElementValue(ConfigFilePath, configElementName, wab.encrypt(String.valueOf(i), wab.DeactivationNoKey));
            return true;
        }
    }
    
    public boolean writeFile(String path, String i) {
        PrintWriter write = null;
        try {
            write = new PrintWriter(new FileWriter(path, false));
            File cff = new File(path);
            if (!cff.exists()) {
                cff.createNewFile();
                u.setNewFilePermissions(cff);
            }
            if (!cff.exists()) {
                u.okmess("Error", "Null File");
                System.exit(0);
            }
            write.println(i);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (write != null) {
                write.close();
            }
        }        
        return true;
    }
    
    public void storeresponsedisable() {
        new printDeactivate();
    }
    
    public void doexpirydisable() {
        removeExpiry re = new removeExpiry(false);
        re.closeonexit = false;
    }
    
    public String webCall(String url_, String method, String[] paramnames, String[] paramvalues, String httpType) {
        activateThread = new Thread(nc = new NetClient_base(url_, method, paramnames, paramvalues, waittime, httpType));
        activateThread.start();
        return "";
    }
    /*
     private boolean isExpiry2(String response) {
     if (response == null) {
     return false;
     }
     if (response.charAt(0) != 'K') {
     return false;
     }
     try {
     String resshort = response.substring(10, 16);
     resshort = wab.swapString(resshort, wab.responseuserkey, wab.responserealkey);
     if (resshort == null) {
     return false;
     }
     // try and see if expiry makes sense
     SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
     sdf.parse("20" + resshort);
     return true;
     } catch (ParseException e) {
     return false;
     }
     }
     */
    

    
    public String buildURL(String mainsite, String mainpage, String page, String est, String lk, String deactivekey, String activationid, String activationnote) {
        String baseURL = mainsite + mainpage;
        String strReturn = null;
        String skey;
        if (page != null) {
            skey = "page=";
            if (strReturn == null) {
                strReturn = "?";
            } else {
                strReturn = strReturn + "&";
            }
            strReturn = strReturn + skey + page;
        }
        if (est != null) {
            skey = "est=";
            if (strReturn == null) {
                strReturn = "?";
            } else {
                strReturn = strReturn + "&";
            }
            strReturn = strReturn + skey + est;
        }
        if (lk != null) {
            skey = "lk=";
            if (strReturn == null) {
                strReturn = "?";
            } else {
                strReturn = strReturn + "&";
            }
            strReturn = strReturn + skey + lk;
        }
        if (deactivekey != null) {
            skey = "da=";
            if (strReturn == null) {
                strReturn = "?";
            } else {
                strReturn = strReturn + "&";
            }
            strReturn = strReturn + skey + deactivekey;
        }
        if (activationid != null) {
            skey = "aid=";
            if (strReturn == null) {
                strReturn = "?";
            } else {
                strReturn = strReturn + "&";
            }
            strReturn = strReturn + skey + activationid;
        }
        if (activationnote != null) {
            skey = "ant=";
            if (strReturn == null) {
                strReturn = "?";
            } else {
                strReturn = strReturn + "&";
            }
            strReturn = strReturn + skey + activationnote;
        }
        
        if (strReturn == null) {
            return baseURL;
        } else {
            return baseURL + strReturn;
        }
    }
    

    
    public void doend(boolean upgrade) {
        if (shark.network || shark.licenceType.equals(shark.LICENCETYPE_STANDALONEACTIVATION)) {
            if (!shark.singledownload) {
                if (updates[SCHOOL] != null) {
                    sharkStartFrame.school = updates[SCHOOL];
                } else {
                    //               sharkStartFrame.school = (String) db.find(sharkStartFrame.optionsdb, "wa_school", db.TEXT);
                    sharkStartFrame.school = wab.doGetXMLElementValue(sharkStartFrame.sharedPathplus + u2_base.saXmlFileName, ELEMENT_SCHOOL);
                }
                if (sharkStartFrame.school == null) {
                    System.exit(0); // problem - school should be present
                }
                if (updates[LOCATION] != null) {
                    sharkStartFrame.location = updates[LOCATION];
                } else {
                    //                sharkStartFrame.location = ((String) db.find(sharkStartFrame.optionsdb, "wa_postcode", db.TEXT));
                    sharkStartFrame.location = wab.doGetXMLElementValue(sharkStartFrame.sharedPathplus + u2_base.saXmlFileName, ELEMENT_LOCATION);
                }
                if (sharkStartFrame.location == null) {
                    sharkStartFrame.location = "";
                }
            }
            if (upgrade) {
                String s = sharkStartFrame.mainFrame.getTitle();
                boolean t1 = s.equals(sharkStartFrame.hometitle1);
                sharkStartFrame.settitles();
                if (t1) {
                    sharkStartFrame.mainFrame.setTitle(sharkStartFrame.hometitle1);
                } else {
                    sharkStartFrame.mainFrame.setTitle(sharkStartFrame.hometitle2);
                }
            }
        }
    }
    
    private String[] computer_macCodes(String[] mac) {
        String ret[] = new String[mac.length];
        for (int i = 0; i < mac.length; i++) {
            ret[i] = wab.makeCode(computeriduserkey, mac[i], 4);
        }
        return ret;
    }
    

    

    


    // get the Windows Volume Serial Number
    final static String getSerialNo() {
        try {
            Process p = Runtime.getRuntime().exec(new String[]{"cmd.exe", "/C", "vol"});
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
            if (dowritelog) {
                u2_base.writelog(new String[]{outputText});
            }
            stdoutStream.close();
            int ii;
            String label = null;
            try {
                String ss[] = u.splitString(outputText, "is ");
                for (int i = 0; i < ss.length; i++) {
                    if (ss[i].length() < 9) {
                        continue;
                    }
                    String s = ss[i].substring(0, 9);
                    Pattern volPattern = Pattern.compile("[0-9a-f][0-9a-f][0-9a-f][0-9a-f][-][0-9a-f][0-9a-f][0-9a-f][0-9a-f]", Pattern.CASE_INSENSITIVE);
                    Matcher matcher = volPattern.matcher(s);
                    if (matcher.matches()) {
                        label = s;
                    }
                }
                return label;
            } catch (Exception e) {
                return null;
            }
        } catch (Exception ee) {
            return null;
        }
    }
    

    

    
    private String getPlatform() {
        if (shark.linuxOS) {
            return "2";
        } else if (shark.macOS) {
            return "1";
        } else {
            return "0";
        }
    } 
    
    private int checkResponseKey(String comp, String user) {
        if (comp == null || user == null || comp.length() != RESPONSELENGTH || user.length() != RESPONSELENGTH) {
            return ERRORBADRESPONSE;
        }
        String cs[] = new String[]{
            comp.substring(0, 1), // [0]    expiry sig
            comp.substring(1, 6), // [1]
            comp.substring(6, 9), // [2]    install no
            comp.substring(9, 10), // [3]   non-deactivatable sig
            comp.substring(10, 16), // [4]  the expiry date
            comp.substring(16, 18), // [5]  checksum
            comp.substring(18, 19), // [6]  split sig
            comp.substring(19)};  // [7]
        String us[] = new String[]{
            user.substring(0, 1),
            user.substring(1, 6),
            user.substring(6, 9),
            user.substring(9, 10),
            user.substring(10, 16),
            user.substring(16, 18),
            user.substring(18, 19),
            user.substring(19)};
        if (!shark.singledownload){
            int p = -1;
            try {
                // get install number
                p = Integer.parseInt(u2_base.swapString(us[2], wab.responseuserkey, wab.responserealkey));
                strinstallno = String.valueOf(p);
            } catch (NumberFormatException e) {
                p = -1;
            }
            if (p < 0) {
                return ERRORBADRESPONSE;
            }
        }

        // do checksum
        String first = user.substring(0, 16);
        String second = user.substring(18);
        String excheck = wab.makeCode(wab.responseuserkey, first + second, 2);
        if (!excheck.equals(user.substring(16, 18))) {
            return ERRORBADRESPONSE;
        }
        // ignore the school code for Home Download
        if (shark.singledownload){
            us[1] = cs[1];
        }
        // ignore the install no
        us[2] = cs[2];
        // ignore the checksum
        us[5] = cs[5];
        if (wab.isNonDeactivatable(user)) {
            //ignore the nondeactivation signature
            us[3] = cs[3];
        }
        if (wab.isSplitLicence(user)) {
            //ignore the split signature
            us[6] = cs[6];
        }
        if (wab.isExpiry(user)) {
            if (wab.hasExpired(user)) {
                return ERROREXPIRYOLD;
            }
            //ignore the expiry signature
            us[0] = cs[0];
            //ignore the expiry date
            us[4] = cs[4];
        }
        user = us[0] + us[1] + us[2] + us[3] + us[4] + us[5] + us[6] + us[7];
        return user.equals(comp)?0:ERRORBADRESPONSE;
    }
    
    public class removeExpiry extends IntroFrame_base {
        
        GridBagConstraints grid = new GridBagConstraints();
        JFrame thisf = this;
        JPanel pnText1 = new JPanel(new GridBagLayout());
        JPanel pnText2 = new JPanel(new GridBagLayout());
        JPanel pnText3 = new JPanel(new GridBagLayout());
        String str_ok = u.gettext("ok", "label");
        JButton okbutton = new JButton(str_ok);
        JButton cancelbutton = new JButton(u.gettext("cancel", "label"));
        JButton noconnbutton = new JButton(str_offline);
        JTextField in_rtf1 = new JTextField("", 4);
        JTextField in_rtf2 = new JTextField("", 4);
        JTextField in_rtf3 = new JTextField("", 4);
        JTextField in_rtf4 = new JTextField("", 4);
        JTextField in_rtf5 = new JTextField("", 4);
        JLabel reqwarn = new JLabel(BLANK);
        JPanel successPanel = new JPanel(new GridBagLayout());
        JPanel failurePanel = new JPanel(new GridBagLayout());
        javax.swing.Timer tnTimer;
        JLabel retrywarnlabel;
        String str_tryconnect = u.gettext("webauthenticate", "tryconnect");
        String str_enterlicencedetails = u.gettext("webauthenticate", "enterlicencedetails");
        String str_enterresponse = u.gettext("webauthenticate", "enterresponse");
        String str_convertsite = u.gettext("webauthenticate", "convertsite");
        String str_convertsite_display = (shark.language.equals(shark.LANGUAGE_NL) ? u.gettext("webauthenticate", "convertsite_display") : str_convertsite);
        JPanel manPanel = new JPanel(new GridBagLayout());
        JTextField compid1;
        String webadd;
        javax.swing.Timer activateTimer;
        long timerOverdue = -1;
        JLabel lbFailure;
        String strfailure = u.gettext("removeexpiry_", "failure");
        String noestablish = u.gettext("removeexpiry_", "noestablish");
        JLabel lbSuccess;
        String strsuccess = u.gettext("removeexpiry_", "success");
        String strnochange = u.gettext("removeexpiry_", "nochange");
        JButton print;
        JLabel lbIKC;
        JLabel lbInstallNo;
        String manpanLbComputerStr = u.gettext("webauthenticate", "listmessComputerID") + SPACES;
        String manpanInstallNoStr = u.gettext("removeexpiry_", "supplyinstno") + SPACES;
        boolean trytoconvert;
        JFrame thisjd;
        boolean isstartup;
        String startupExpiry = sharkStartFrame.expiry;
        JTextPane helphyperlink;
        String manpanReqStr = (shark.singledownload ? u.gettext("webauthenticate", "listmessLK_home") : u.gettext("webauthenticate", "listmessLK_school")) + SPACES;
        JPanel progressBarPanelRetry;
        String ss[] = null;
        int state;
        final int STATE_CONNECTIONFAILED = 0;
        final int STATE_MANUAL = 1;
        final int STATE_SUCCESS = 2;
        final int STATE_FAILURE = 3;
        JLabel warnLabel = new JLabel();
        String strBadResponse = u.gettext("webauthenticate", "responseerror");
        String str_4chars = u.gettext("webauthenticate", "enter4characters");
        String str_AlreadyExpired = u.gettext("removeexpiry_", "alreadyexpired");
         
        removeExpiry(boolean onstartup) {
            super(false);
            
            in_rtf1.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    if (!in_rtf2.getText().trim().equals("")) {
                        return;
                    }
                    if (!in_rtf3.getText().trim().equals("")) {
                        return;
                    }
                    if (!in_rtf4.getText().trim().equals("")) {
                        return;
                    }
                    if (!in_rtf5.getText().trim().equals("")) {
                        return;
                    }
                    if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_V) {
                        String s[] = u.getSystemClipboard();
                        if (s != null && s.length == 1) {
                            String r = "";
                            for (int i = 0; i < s[0].length(); i++) {
                                char c = s[0].charAt(i);
                                if (Character.isLetterOrDigit(c)) {
                                    r = r + String.valueOf(c);
                                }
                            }
                            if (r.length() == 20) {
                                in_rtf1.setText(r.substring(0, 4));
                                in_rtf2.setText(r.substring(4, 8));
                                in_rtf3.setText(r.substring(8, 12));
                                in_rtf4.setText(r.substring(12, 16));
                                in_rtf5.setText(r.substring(16));
                            }
                        }
                    }
                }
            });
            
            isstartup = onstartup;
            thisjd = this;
            if (isstartup) {
                u2_base.setupMenuItemHeight();
                cancelbutton.setText(u.gettext("exit", "label"));
                JMenu jm = u.Menu("file");
                jm.setToolTipText(null);
                JMenuItem mconvertexpiry = u.MenuItem("mconvertexpiry");
                jm.add(mconvertexpiry);
                JMenuItem mconvertexpiryreset = u.MenuItem("mconvertexpiryreset");
                jm.add(mconvertexpiryreset);
                mconvertexpiry.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        progressBarPanelRetry.setVisible(true);
                        failurePanel.setVisible(false);
                        activateTimer.start();
                        setJMenuBar(null);
                    }
                });
                mconvertexpiryreset.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        doClear();
                        System.exit(0);
                    }
                });
                JMenuBar manBar = new JMenuBar();
                manBar.add(jm);
                this.setJMenuBar(manBar);                
                
            }
            trytoconvert = !onstartup;
            retrywarnlabel = new JLabel(BLANK);
            in_rtf1.setEditable(true);
            in_rtf2.setEditable(true);
            in_rtf3.setEditable(true);
            in_rtf4.setEditable(true);
            in_rtf5.setEditable(true);
            in_rtf1.setDocument(new keydoc4(in_rtf1, in_rtf2, 4));
            in_rtf2.setDocument(new keydoc4(in_rtf2, in_rtf3, 4));
            in_rtf3.setDocument(new keydoc4(in_rtf3, in_rtf4, 4));
            in_rtf4.setDocument(new keydoc4(in_rtf4, in_rtf5, 4));
            in_rtf5.setDocument(new keydoc4(in_rtf5, null, 4));
            reqwarn.setForeground(Color.red);
            ss = wab.doGetXMLElementValues(sharkStartFrame.sharedPathplus + u2_base.saXmlFileName, new String[]{ELEMENT_REQUEST, ELEMENT_INSTALLNO});

            setTitle(u.gettext("mconvertexpiry", "label"));
            if(isstartup){
                shark.singledownload = wab.isSingle(ss[0], false);
                setTitle(getTitle() + "       -       " + u.gettext("webauthenticate", "request") + "   " + ss[0].substring(0, 4) + " - " +
                        ss[0].substring(4, 8) + " - " +
                        ss[0].substring(8, 12) + " - " +
                       ss[0].substring(12)
                );
            }            
            activateTimer = new javax.swing.Timer(0, new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (timerOverdue < 0) {
                        timerOverdue = System.currentTimeMillis() + waittime;
                    }
                    boolean overdue = System.currentTimeMillis() > timerOverdue;
                    if (activateTimer.getDelay() == 0) {
                        SimpleDateFormat sdf2 = new SimpleDateFormat("ddMMyyyy");
                        Date expires;
                        try {
                            expires = sdf2.parse(sharkStartFrame.expiry);
                        } catch (Exception ee) {
                            return;
                        }
                        String date = sdf2.format(expires);
                        date = date.substring(0, 2) + "-" + date.substring(2, 4) + "-" + date.substring(4);
                        autoConvertExpiry(date, ss[0], maccodesautolist, ss[1]);                        
                        activateTimer.setDelay(timertime);
                    }
                    else if (activateTimer.getDelay() == timertime && (!activateThread.isAlive() || overdue)) {
                        progressBarPanelRetry.setVisible(false);
                        noconnbutton.setVisible(true);
                        timerOverdue = -1;
                        activateTimer.setDelay(0);
                        String res = nc.returnval;
                        if (overdue) {
                            res = null;
                        }
                        int resno = 1;
                        if (res != null) {
                            try {
                                resno = Integer.parseInt(res);
                            } catch (Exception ee) {
                            }
                        }
                        helphyperlink.setVisible(false);
                        if (res == null) {
                            // no connection
                            helphyperlink.setVisible(true);
                            lbFailure.setText(noestablish);
                            cancelbutton.setVisible(true);
                            noconnbutton.setVisible(true);
                            failurePanel.setVisible(true);
                            state = STATE_FAILURE;
                        } else if (resno < 0) {
                            lbFailure.setText(strfailure);
                            cancelbutton.setVisible(true);
                            noconnbutton.setVisible(false);
                            failurePanel.setVisible(true);
                        } else {
                            res = wab.unmakeCode2(wab.userkey, maccodesautolist, res);
                            dosuccess(res, isExpiryNewer(res));
                        }
                        nc = null;
                        activateThread = null;
                        activateTimer.stop();
                    } 
                }
            });
            
            cancelbutton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    thisf.dispose();
                    if (isstartup) {
                        System.exit(0);
                    }
                }
            });

            noconnbutton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    failurePanel.setVisible(false);
    //                retrybutton.setVisible(false);
                    noconnbutton.setVisible(false);
//                  helpbutton.setVisible(false);
                    cancelbutton.setVisible(true);
                    okbutton.setVisible(true);
                    manPanel.setVisible(true);
                    state = STATE_MANUAL;
                    print.setVisible(true);
                    in_rtf1.requestFocus();
                }
            });
            
            okbutton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    reqwarn.setText(BLANK);
                    if (state == STATE_SUCCESS || state == STATE_FAILURE) {
                        thisf.dispose();
                    } else if (state == STATE_MANUAL) {
                        String userres = in_rtf1.getText() + in_rtf2.getText() + in_rtf3.getText() + in_rtf4.getText() + in_rtf5.getText();
                        if (userres.length() != RESPONSELENGTH) {
                            warnLabel.setText(str_4chars);
                            return;
                        }
                        userres = wab.unChop20(userres);
                        userres = wab.unmakeCode2(wab.userkey, manualMac, userres);
                        int t = currno + 1;
                        t = t % wab.responseuserkey.length();                  
                        String ss2[] = wab.doGetXMLElementValues(sharkStartFrame.sharedPathplus + u2_base.saXmlFileName, new String[]{ELEMENT_REQUEST, ELEMENT_SCHOOL, ELEMENT_LOCATION});
                        String res = buildcomputerresponse(ss2[0],
                                manualMac, ss2[1], ss2[2], wab.responseuserkey.substring(t, t + 1));
                        int k;
                        if ((k=checkResponseKey(res, userres))<0) {
                            if(k == ERROREXPIRYOLD)
                                warnLabel.setText(str_AlreadyExpired);
                            else 
                                warnLabel.setText(strBadResponse);  
                        } else {
                            cancelbutton.setVisible(false);
                            manPanel.setVisible(false);
                            dosuccess(userres, isExpiryNewer(userres));
                        }
                    }
                }
            });
            grid.gridx = -1;
            grid.gridy = 0;
            grid.weighty = 0;
            grid.weightx = 0;
            grid.fill = GridBagConstraints.NONE;
            grid.weightx = 1;
            grid.weighty = 1;
            JPanel pkPanel = new JPanel(new GridBagLayout());
            pkPanel.add(in_rtf1, grid);
            pkPanel.add(new JLabel("-"), grid);
            pkPanel.add(in_rtf2, grid);
            pkPanel.add(new JLabel("-"), grid);
            pkPanel.add(in_rtf3, grid);
            pkPanel.add(new JLabel("-"), grid);
            pkPanel.add(in_rtf4, grid);
            pkPanel.add(new JLabel("-"), grid);
            pkPanel.add(in_rtf5, grid);
            grid.gridx = 0;
            grid.gridy = -1;
                JProgressBar pBarRetry;
                pBarRetry = new JProgressBar();
                pBarRetry.setIndeterminate(true);
                progressBarPanelRetry = new JPanel(new GridBagLayout());
                grid.gridx = 0;
                grid.gridy = -1;
                JLabel retrywarnlabel;
                retrywarnlabel = new JLabel(str_tryconnect);
                retrywarnlabel.setForeground(Color.red);
                progressBarPanelRetry.add(retrywarnlabel, grid);
                progressBarPanelRetry.add(new JLabel(BLANK), grid);
                progressBarPanelRetry.add(pBarRetry, grid);
                progressBarPanelRetry.add(new JLabel(BLANK), grid);
                grid.gridx = -1;
                grid.gridy = 0;
                progressBarPanelRetry.setVisible(true);            
            grid.insets = new Insets(0, 0, border, 0);
            grid.insets = new Insets(0, 0, 0, 0);
            grid.gridx = 0;
            grid.gridy = -1;
            noconnbutton.setVisible(false);
            okbutton.setVisible(false);
            grid.gridx = -1;
            grid.gridy = 0;
            manPanel.setVisible(false);
            JPanel compidpanel = new JPanel(new GridBagLayout());       
            compid1 = new JTextField();
            String display_mac = getDisplayId(wab.doGetXMLElementValue(sharkStartFrame.sharedPathplus + u2_base.saXmlFileName, ELEMENT_REQUEST), manualMac, currno);    
            compid1.setEditable(false);
            String ss2[] = u.splitString(display_mac, '|');
            compid1.setText(ss2[0] + " - " + ss2[1] + " - " + ss2[2]);
            grid.insets = new Insets(0, 0, 0, 0);
            lbIKC = new JLabel();
            compidpanel.add(lbIKC, grid);
            compidpanel.add(compid1, grid);
            grid.gridx = 0;
            grid.gridy = -1;
            print = new JButton(u.gettext("printtext", "label"));
            // www.wordshark.co.uk/Activation/Convert
            webadd = str_httpactivation.replaceFirst("http://", "") + str_convertsite_display;
            print.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    int listcount = 1;
                    printDetails pr = new printDetails(
                            str_enterlicencedetails,
                            u.edit(manpanReqStr, String.valueOf(listcount++)),
                            shark.singledownload?ss[0]:null,
                            null,
                            null,
                            null,
                            sharkStartFrame.splitlicence || shark.singledownload ? null : u.edit(manpanInstallNoStr, String.valueOf(listcount++)),
                            u.edit(manpanLbComputerStr, String.valueOf(listcount++)),
                            compid1.getText().replaceAll("-", "").replaceAll(" ", ""), webadd, printDetails.TYPECONVERT);
                    pr.printD();
                }
            });
            print.setVisible(false);
            JTextPane hyperl = new JTextPane();
            hyperl.setBorder(BorderFactory.createEmptyBorder());
            hyperl.setContentType("text/html");
            String school = null;
            if(!shark.singledownload)
                school = wab.doGetXMLElementValue(sharkStartFrame.sharedPathplus + u2_base.saXmlFileName, ELEMENT_SCHOOL);
            // www.wordshark.co.uk/Activation/SignIn?ConvertSchool
               
            String strurl = buildURL(
                    str_httpactivation,
                    u.gettext("webauthenticate", "redirectsite"),
                    str_convertsite,
                    school!=null?u.convertForURL(school):null,
                    ss[0],
                    null, ss2[0] + ss2[1] + ss2[2], null);
            hyperl.setText(u.getHTMLWithFontCSS(UIManager.getFont("Label.font"), "<a href=" + strurl + ">" + webadd + "</a>"));
            hyperl.setEditable(false);
            hyperl.setCursor(new Cursor(Cursor.HAND_CURSOR));
            hyperl.setFont(f);
            hyperl.setOpaque(false);
            hyperl.addHyperlinkListener(new HyperlinkListener() {
                public void hyperlinkUpdate(HyperlinkEvent e) {
                    if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                        u.launchWebSite(e.getDescription());
                    }
                }
            });
            
            grid.gridx = -1;
            grid.gridy = 0;
            JPanel instnopanel = new JPanel(new GridBagLayout());
            grid.insets = new Insets(0, 0, 0, 0);
            lbInstallNo = new JLabel();
            instnopanel.add(lbInstallNo, grid);
            JTextField tfinst = new JTextField(wab.doGetXMLElementValue(sharkStartFrame.sharedPathplus + u2_base.saXmlFileName, ELEMENT_INSTALLNO), 3);
            tfinst.setEditable(false);
            instnopanel.add(tfinst, grid);         
            grid.gridx = -1;
            grid.gridy = 0;
            JPanel pnGoTo = new JPanel(new GridBagLayout());
            grid.insets = new Insets(0, 0, 0,border);
            pnGoTo.add(new JLabel(u.gettext("webauthenticate", "pleasevisit")), grid);
            grid.insets = new Insets(0, 0, 0, 0);
            pnGoTo.add(hyperl, grid);
            grid.gridx = 0;
            grid.gridy = -1;            
            grid.insets = new Insets(0, 0, border*2, 0);
            manPanel.add(pnGoTo, grid);
            grid.insets = new Insets(0, 0, border, 0);
            int listcount = 1;
            manPanel.add(new JLabel(str_enterlicencedetails), grid);
            grid.gridx = -1;
            grid.gridy = 0;  
            JPanel pnLK = new JPanel(new GridBagLayout());
            grid.insets = new Insets(0, 0, 0, 0);
            pnLK.add(new JLabel(u.edit(manpanReqStr, String.valueOf(listcount++))), grid);
            if(shark.singledownload){
                JTextField tfLK = new JTextField(ss[0].substring(0, 4) + " - " + ss[0].substring(4, 8)+ " - " + ss[0].substring(8, 12)+ " - " + ss[0].substring(12));
                tfLK.setEditable(false);
                pnLK.add(tfLK, grid);   
                
            }
            grid.gridx = 0;
            grid.gridy = -1;       
            grid.insets = new Insets(0, 0, border, 0);
            manPanel.add(pnLK, grid);
            if (!sharkStartFrame.splitlicence && !shark.singledownload) {
                manPanel.add(instnopanel, grid);
            }
            manPanel.add(compidpanel, grid);
             grid.insets = new Insets(border, 0, border, 0);
            manPanel.add(new JLabel(str_enterresponse), grid);
            grid.insets = new Insets(0, 0, border, 0);
            manPanel.add(pkPanel, grid);
            if (!sharkStartFrame.splitlicence && !shark.singledownload) {
                lbInstallNo.setText(u.edit(manpanInstallNoStr, String.valueOf(listcount++)));
            }            
            lbIKC.setText(u.edit(manpanLbComputerStr, String.valueOf(listcount++)));
            lbFailure = new JLabel(BLANK);
            helphyperlink = new JTextPane();
            helphyperlink.setBorder(BorderFactory.createEmptyBorder());
            helphyperlink.setContentType("text/html");   
                if (shark.singledownload) {
                    helphyperlink.setText(u.getHTMLWithFontCSS(UIManager.getFont("Label.font"), u.gettext("removeexpiry_", "tryconnectmess_home")));
                } else {
                    helphyperlink.setText(u.getHTMLWithFontCSS(UIManager.getFont("Label.font"), u.gettext("removeexpiry_", "tryconnectmess_school")));
                }
            helphyperlink.setEditable(false);
            helphyperlink.setCursor(new Cursor(Cursor.HAND_CURSOR));
            helphyperlink.setOpaque(false);
                helphyperlink.addHyperlinkListener(new HyperlinkListener() {
                    public void hyperlinkUpdate(HyperlinkEvent e) {
                        if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                            String s = e.getDescription();
                            switch (s){
                                case "0":
                                    activateTimer.start();
                                    failurePanel.setVisible(false);
                                    progressBarPanelRetry.setVisible(true);
                                    noconnbutton.setVisible(false);
                                    break;
                                case "1":
                                    new ProxySettings(thisjd);
                                    break;
                                case "2":
                                    String s1;
                                    if (shark.macOS) {
                                        s1 = u.gettext("webauthenticate", "helpmessmac", str_offline);
                                    } else {
                                        s1 = u.edit(u.gettext("webauthenticate", "helpmess"),
                                                new String[]{
                                                    System.getProperty("java.home") + File.separator + "bin" + File.separator + "javaw.exe",
                                                    str_offline});
                                    }                                
                                    showtext(thisjd, u.gettext("webauthenticate", "helptitle"), s1); 
                                    break;
                            }
                        }
                    }
                });
            
            
            lbFailure.setForeground(Color.red);
            grid.gridx = 0;
            grid.gridy = -1;
            grid.insets = new Insets(0, 0, border, 0);
            SimpleDateFormat sdf2 = new SimpleDateFormat("ddMMyyyy");
            Date expires = new Date();
            try {
                expires = sdf2.parse(sharkStartFrame.expiry);
            } catch (Exception e) {
            }
            Date now = new Date();
            String mess;
            if (now.after(expires)) {
                mess = u.gettext("removeexpiry_", "mess1past");
            } else {
                mess = u.gettext("removeexpiry_", "mess1future");
            }
            failurePanel.add(new JLabel(u.edit(mess, shark.programName,
                    sharkStartFrame.expiry.substring(0, 2) + "/"
                    + sharkStartFrame.expiry.substring(2, 4) + "/"
                    + sharkStartFrame.expiry.substring(4))), grid);
            failurePanel.add(new JLabel(BLANK), grid);
            grid.insets = new Insets(0, 0, border * 5 / 2, 0);
            failurePanel.add(lbFailure, grid);
            grid.insets = new Insets(0, 0, border, 0);
            failurePanel.add(helphyperlink, grid);
            if (!trytoconvert) {
                lbFailure.setVisible(false);
                helphyperlink.setVisible(false);
                progressBarPanelRetry.setVisible(false);
            } else {
                failurePanel.setVisible(false);
            }
            grid.gridx = 0;
            grid.gridy = -1;
            lbSuccess = new JLabel();
            successPanel.add(lbSuccess, grid);
            successPanel.setVisible(false);
            JPanel pnButtons = new JPanel(new GridBagLayout());
            grid.gridx = -1;
            grid.gridy = 0;
            grid.insets = new Insets(0, 0, 0, border);
            if (shark.macOS) {
                pnButtons.add(cancelbutton, grid);
                pnButtons.add(print, grid);
                pnButtons.add(noconnbutton, grid);
                grid.insets = new Insets(0, 0, 0, 0);
                pnButtons.add(okbutton, grid);
            } else {
                pnButtons.add(okbutton, grid);
                pnButtons.add(noconnbutton, grid);
                pnButtons.add(print, grid);
                grid.insets = new Insets(0, 0, 0, 0);
                pnButtons.add(cancelbutton, grid);
            }
            getRootPane().setDefaultButton(okbutton);
            grid.insets = new Insets(0, 0, 0, 0);
            grid.weightx = 1;
            grid.fill = GridBagConstraints.NONE;
            grid.gridx = 0;
            grid.gridy = -1;
            JPanel contentPanes = new JPanel(new GridBagLayout());
            contentPanes.add(progressBarPanelRetry);
            contentPanes.add(manPanel);
            contentPanes.add(successPanel);
            contentPanes.add(failurePanel);
            addToBase(contentPanes, grid);
            grid.weighty = 0;
            warnLabel.setForeground(Color.red);
            warnLabel.setText(BLANK);
            grid.gridx = -1;
            grid.gridy = 0;
            int sh = (int) sharkStartFrame.screenSize.getHeight();
            int warnLbTop = sh * 1 / 60;
            int warnLbBottom = sh * 2 / 60;                
            grid.insets = new Insets(warnLbTop, 0, warnLbBottom, 0);
            JPanel inputwarnpanel = new JPanel(new GridBagLayout());
            inputwarnpanel.add(new JLabel(" "), grid);
            inputwarnpanel.add(warnLabel, grid);
            inputwarnpanel.add(new JLabel(" "), grid);            
            grid.insets = new Insets(0, 0, 0, 0);
            grid.gridx = 0;
            grid.gridy = -1;
            addToBase(inputwarnpanel, grid);
            grid.insets = new Insets(border, 0, 0, 0);
            addToBase(pnButtons, grid);
            setColor();
            setVisible(true);
            if (trytoconvert) {
                activateTimer.start();
                noconnbutton.setVisible(false);
            }
        }
        
        private void dosuccess(String res, boolean isexpirynewer) {
            warnLabel.setText(BLANK);
            if (isexpirynewer) {
                wab.doSetXMLElementValue(sharkStartFrame.sharedPathplus + u2_base.saXmlFileName, ELEMENT_RESPONSE, res);
                // sharkStartFrame.expiry set in isExpiry
                boolean exp = false;
                if (!(exp = wab.isExpiry(res))) {
                    sharkStartFrame.expiry = null;
                }
                wab.isNonDeactivatable(res);
                wab.isSplitLicence(res);
                if (sharkStartFrame.hometitle1 != null) {
                    String title = sharkStartFrame.mainFrame.getTitle();
                    boolean home1 = title.indexOf(sharkStartFrame.hometitle1) >= 0;
                    sharkStartFrame.settitles();
                    if (home1) {
                        sharkStartFrame.mainFrame.setTitle(sharkStartFrame.hometitle1);
                    } else {
                        sharkStartFrame.mainFrame.setTitle(sharkStartFrame.hometitle2);
                    }
                }
                if (!exp) {
                    sharkStartFrame.mainFrame.removedExpiryChangeTitle();
                }
                lbSuccess.setText(strsuccess);

            } else {
                lbSuccess.setText(strnochange);
            }
            okbutton.setVisible(true);
            noconnbutton.setVisible(false);
            cancelbutton.setVisible(false);
            print.setVisible(false);
            successPanel.setVisible(true);
            state = STATE_SUCCESS;
            closeonexit = false;
        }
        
        class keydoc4 extends keydoc {
            
            keydoc4(JTextField ow, JTextField ne, int chars) {
                super(ow, ne, chars);
            }
            
            protected void removeUpdate(DefaultDocumentEvent chng) {
                super.removeUpdate(chng);
                reqwarn.setText(BLANK);
            }
            
            public void insertString(int o, String s, AttributeSet a) {
                super.insertString(o, s, a);
                reqwarn.setText(BLANK);
            }
        }
        
        private boolean isExpiryNewer(String response) {
            try {
                SimpleDateFormat sdf2 = new SimpleDateFormat("ddMMyyyy");
                Date currexpires = sdf2.parse(startupExpiry);
                Date newexpires = null;
                if (response == null) {
                    return false;
                }
                if (response.charAt(0) != 'K') {
                    return true;
                }
                try {
                    String resshort = response.substring(10, 16);
                    resshort = u2_base.swapString(resshort, wab.responseuserkey, wab.responserealkey);
                    if (resshort == null) {
                        return false;
                    }
                    // try and see if expiry makes sense
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                    newexpires = sdf.parse("20" + resshort);
                } catch (ParseException e) {
                    return false;
                }
                if (newexpires.getTime() > currexpires.getTime()) {
                    return true;
                }
            } catch (ParseException e) {
                return false;
            }
            return false;
        }
        
    }
    
    class ProxySettings
            extends JDialog {
        
        JTextField tfPort;
        JTextField tfServerName;
        JTextField tfUserName;
        JPasswordField tfPassword;
        int b = 10;
        int b2 = 5;
        JDialog pdia;
        int fullh;
        int fullw;
        
        public ProxySettings(JFrame owner) {
            super(owner);
            fullh = owner.getHeight();
            fullw = owner.getWidth();
            init();
        }
        
        public ProxySettings(JDialog owner) {
            super(owner);
            fullh = ((showtext) owner).ownerframe.getHeight();
            fullw = ((showtext) owner).ownerframe.getWidth();
            init();
        }        
        
        void init() {
            pdia = this;
            int thish;
            int thisw;
            if (!u.screenResWidthMoreThan(1200)) {
                thish = fullh * 11 / 18;
                thisw = fullw * 6 / 9;
            } else if (!u.screenResWidthMoreThan(1400)) {
                thish = fullh * 9 / 18;
                thisw = fullw * 4 / 9;
            } else {
                thish = fullh * 7 / 18;
                thisw = fullw * 3 / 9;
            }
            this.setBounds((fullw - thisw) / 2, (fullh - thish) / 2, thisw, thish);
            this.setTitle(u.gettext("webproxy", "title"));
            this.getContentPane().setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.weightx = 1;
            gbc.weighty = 0;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.gridx = 0;
            gbc.gridy = -1;
            JPanel jpServerName = new JPanel(new GridBagLayout());
            JLabel jlServerName = new JLabel(u.gettext("webproxy", "server"));
            tfServerName = new JTextField();
            jpServerName.add(jlServerName, gbc);
            jpServerName.add(tfServerName, gbc);
            JPanel jpPort = new JPanel(new GridBagLayout());
            JLabel jlPort = new JLabel(u.gettext("webproxy", "port"));
            tfPort = new JTextField();
            jpPort.add(jlPort, gbc);
            jpPort.add(tfPort, gbc);
            gbc.gridx = -1;
            gbc.gridy = 0;            
            JPanel topp = new JPanel(new GridBagLayout());
            gbc.weightx = 7;
            gbc.insets = new Insets(0, 0, 0, b);
            topp.add(jpServerName, gbc);
            gbc.weightx = 2;
            gbc.insets = new Insets(0, 0, 0, 0);
            topp.add(jpPort, gbc);
            gbc.weightx = 1;
            
            JCheckBox jcbAuth = new JCheckBox(u.gettext("webproxy", "authlb"));
            jcbAuth.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (((JCheckBox) e.getSource()).isSelected()) {
                        tfPassword.setEnabled(true);
                        tfUserName.setEnabled(true);
                    } else {
                        tfPassword.setEnabled(false);
                        tfUserName.setEnabled(false);
                        tfPassword.setText("");
                        tfUserName.setText("");
                        WebCall_base.proxyUserName = null;
                        WebCall_base.proxyPassword = null;
                    }
                }
            });            
            
            gbc.gridx = 0;
            gbc.gridy = -1;
            JPanel jpUserPassword = new JPanel(new GridBagLayout());
            JLabel jlUserName = new JLabel(u.gettext("webproxy", "username"));
            tfUserName = new JTextField();
            jpUserPassword.add(jlUserName, gbc);
            jpUserPassword.add(tfUserName, gbc);
            JLabel jlPassword = new JLabel(u.gettext("webproxy", "password"));
            tfPassword = new JPasswordField();
            jpUserPassword.add(jlPassword, gbc);
            jpUserPassword.add(tfPassword, gbc);
            if (WebCall_base.proxyUserName == null) {
                tfPassword.setEnabled(false);
                tfUserName.setEnabled(false);                
                WebCall_base.proxyPassword = null;
            } else {
                jcbAuth.setSelected(true);
                tfPassword.setEnabled(true);
                tfUserName.setEnabled(true);                
            }
            
            JPanel jpButtons = new JPanel(new GridBagLayout());
            JButton jbOk = new JButton(u.gettext("OK", "label"));
            JButton jbCancel = new JButton(u.gettext("cancel", "label"));
            jbOk.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String s;
                    if (!(s = tfServerName.getText().trim()).equals("")) {
                        WebCall_base.proxyServerName = s;
                    }
                    if (!(s = tfPort.getText().trim()).equals("")) {
                        try {
                            int i = Integer.parseInt(s);
                            WebCall_base.proxyPort = i;
                        } catch (Exception ee) {
                        };
                    }                    
                    if (!(s = tfUserName.getText().trim()).equals("")) {
                        WebCall_base.proxyUserName = s;
                    }
                    if (!(s = String.valueOf(tfPassword.getPassword()).trim()).equals("")) {
                        WebCall_base.proxyPassword = s;
                    }
                    pdia.dispose();
                }
            });            
            
            jbCancel.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    pdia.dispose();
                }
            });
            
            if (WebCall_base.proxyPassword != null) {
                tfPassword.setText(WebCall_base.proxyPassword);
            }
            if (WebCall_base.proxyUserName != null) {
                tfUserName.setText(WebCall_base.proxyUserName);
            }
            try {
                if (WebCall_base.proxyPort >= 0) {
                    tfPort.setText(String.valueOf(WebCall_base.proxyPort));
                }
            } catch (Exception ee) {
            }
            if (WebCall_base.proxyServerName != null) {
                tfServerName.setText(WebCall_base.proxyServerName);
            }
            
            gbc.gridx = -1;
            gbc.gridy = 0;            
            gbc.insets = new Insets(0, 0, 0, b);
            jpButtons.add(shark.macOS ? jbCancel : jbOk, gbc);
            gbc.insets = new Insets(0, 0, 0, 0);
            jpButtons.add(shark.macOS ? jbOk : jbCancel, gbc);
            
            gbc.gridx = 0;
            gbc.gridy = -1;
            gbc.insets = new Insets(b, b, b, b);
            this.getContentPane().add(topp, gbc);
            gbc.insets = new Insets(0, b * 2, b2, b);
            this.getContentPane().add(jcbAuth, gbc);
            gbc.insets = new Insets(0, b * 2, 0, b);
            this.getContentPane().add(jpUserPassword, gbc);
            
            gbc.insets = new Insets(0, b, 0, 0);
            JPanel needhelpPn = new JPanel(new GridBagLayout());
            gbc.anchor = GridBagConstraints.WEST;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.weightx = 1;
            JLabel lbNeedHelp = new JLabel(u.convertToHtml(u.gettext("webproxy", "contacttech")));
            lbNeedHelp.setFont(jlServerName.getFont().deriveFont((float) jlServerName.getFont().getSize() - 2));
            
            needhelpPn.add(lbNeedHelp, gbc);
            
            gbc.fill = GridBagConstraints.BOTH;
            gbc.insets = new Insets(0, 0, 0, 0);
            gbc.weighty = 1;
            this.getContentPane().add(needhelpPn, gbc);
            gbc.weighty = 0;
            gbc.insets = new Insets(0, b * 2, 0, b);
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.weightx = 1;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            
            gbc.weightx = 1;
            gbc.weighty = 0;
            gbc.insets = new Insets(0, b, b, b);
            this.getContentPane().add(jpButtons, gbc);
            
            this.validate();
            this.setVisible(true);            
        }
        
        public void dispose() {
            u.focusBlock = false;
            super.dispose();
        }
    }    
    
    void showtext(JFrame jf, String title, String list) {
        new showtext(jf, title, list);
    }
    
    class showtext
            extends JDialog {

        JDialog thisjd = this;
        public JFrame ownerframe;
        BorderLayout layout1 = new BorderLayout();

        public showtext(JFrame jf, String title, String list) {
            super(jf);
            ownerframe = jf;
            u.focusBlock = true;
            this.setResizable(false);
            setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            
            int w = jf.getWidth() / 2;
            int h = jf.getHeight() / 2;
            int fullh = jf.getHeight();
            int fullw = jf.getWidth();
            
            setBounds(new Rectangle((fullw / 2) - (w / 2), (fullh / 2) - (h / 2), w, h));
            this.getContentPane().setLayout(new GridBagLayout());
            GridBagConstraints grid = new GridBagConstraints();
            grid.weightx = 1;
            grid.weighty = 1;
            grid.gridx = 0;
            grid.gridy = -1;
            grid.fill = GridBagConstraints.BOTH;
            
            this.setEnabled(true);
            this.setTitle(title);
            JTextPane textPane = new textpane();
            textPane.setEditable(false);
            textPane.setContentType("text/html");
//     textPane.setText(list);
            textPane.setText(u.getHTMLWithFontCSS(UIManager.getFont("Label.font"), list));
            textPane.setCaretPosition(0);
            textPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            
            JPanel bpan = new JPanel(new GridBagLayout());
            JButton btok = new JButton(u.gettext("OK", "label"));
            btok.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    thisjd.dispose();
                }
            });
            grid.fill = GridBagConstraints.NONE;
            grid.insets = new Insets(10, 10, 10, 10);
            bpan.add(btok, grid);
            grid.insets = new Insets(0, 0, 0, 0);
            
            grid.fill = GridBagConstraints.BOTH;
            
            getContentPane().add(new u.uuScrollPane(textPane), grid);
            grid.weighty = 0;
            getContentPane().add(bpan, grid);
            
            textPane.addHyperlinkListener(new HyperlinkListener() {
                public void hyperlinkUpdate(HyperlinkEvent e) {
                    if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                        new ProxySettings(thisjd);
                    }
                }
            });
            setVisible(true);
            validate();
        }

        public void dispose() {
            u.focusBlock = false;
            super.dispose();
        }
        
        class textpane extends JTextPane {

            public void paint(Graphics g) {
                ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                super.paint(g);
            }
        }
    }    
    
    public class printDeactivate extends IntroFrame_base implements Printable {
        
        GridBagConstraints grid = new GridBagConstraints();
        String deactivationkey = "";
        JLabel lbTryConnect = new JLabel(BLANK);
        String tryconnect = u.gettext("webauthenticate", "tryconnect");
        JFrame thisf = this;
        JPanel pnAutoDeactivate = new JPanel(new GridBagLayout());
        JPanel pnAutoFailed = new JPanel(new GridBagLayout());
        JPanel pnAutoSuccess = new JPanel(new GridBagLayout());
        JPanel pnManualDeactivate = new JPanel(new GridBagLayout());
        String str_ok = u.gettext("OK", "label");
        JButton deactivatebutton = new JButton(u.gettext("deactivate_", "deactivate"));
        JButton cancelbutton = new JButton(u.gettext("cancel", "label"));
        String strnoconnectbutton = u.gettext("deactivate_", "offline");
        JButton noconnectbutton = new JButton(strnoconnectbutton);
        JButton printbutton = new JButton(u.gettext("printtext", "label"));
        String s1 = u.gettext("deactivate_", "mess1");
        String s2;
        String str_enterlicencedetails = u.gettext("webauthenticate", "enterlicencedetails");
        JTextField mantfSchool;
//        String schoolname = (String) db.find(sharkStartFrame.optionsdb, "wa_school", db.TEXT);
        String ssDetails[] = wab.doGetXMLElementValues(sharkStartFrame.sharedPathplus + u2_base.saXmlFileName, new String[]{ELEMENT_REQUEST, ELEMENT_SCHOOL});
        String schoolname = ssDetails[1];
        String title = shark.programName + BLANK + shark.versionNo + BLANK + u.gettext("deactivate_", "deactivation");
        String pk1;
        String pk2;
        String pk3;
        String pk4;
        String dk1;
        String dk2;
        String dk3;
        String dk4;
        String dk5;
//        String productkey = (String) db.find(sharkStartFrame.optionsdb, "wa_request", db.TEXT);
        String productkey = ssDetails[0];
        JTextField in_rtf1 = new JTextField("", 4);
        JTextField in_rtf2 = new JTextField("", 4);
        JTextField in_rtf3 = new JTextField("", 4);
        JTextField in_rtf4 = new JTextField("", 4);
        String strreqwarn = u.gettext("deactivate_", "reqwarn");
        JTextPane reqwarn = new JTextPane();
        javax.swing.Timer activateTimer;
        long timerOverdue = -1;
//        JProgressBar pBar;
//        JProgressBar pBar2;
        String footer = u.gettext("webauthenticate", "printfooter");
//        String str_Lk = u.gettext("webauthenticate", "listmessLK")+BLANK;
        String str_school = u.gettext("webauthenticate", "listmessSchool") + BLANK;
        String str_Dk = u.gettext("deactivate_", "mess2") + BLANK;
        JFrame thisjd;
        JTextPane tpContact;
        String str_Lk;
        JTextPane tpTryConnect;     
        String str_4chars = u.gettext("webauthenticate", "enter4characters");
        JPanel pkPanel = new JPanel(new GridBagLayout());
        boolean okpressed = false;
        int state;
        final int STATE_SUCCESS = 0;
        final int STATE_MANUAL = 1;
        final int STATE_AUTODEACTIVATE = 2;
        final int STATE_AUTOFAILED = 3;
        JPanel progressBarPanelRetry;
        String str_tryconnect = u.gettext("webauthenticate", "tryconnect");
        String strlicencekey = u.gettext("webauthenticate", "request");
        String strSuccessDescr = u.convertToHtml(u.gettext("deactivate_", "success", shark.programName + " " + shark.versionNo), true);
        String download1 = u.gettext("deactivate_", "download1");
        String downloadlink = u.edit(u.gettext("deactivate_", "downloadlink"), 
                new String[]{shark.programName.toLowerCase(), shark.PUBLICPREFIX+shark.versionNo+shark.getPublicProductSuffix()});
        
        printDeactivate() {
            super(false);
            thisjd = this;
            state = STATE_AUTODEACTIVATE;
            
            
            
            addWindowListener(new WindowAdapter() {
                public void windowOpened(WindowEvent e) {
                    in_rtf1.requestFocus();
                }
            });            
            in_rtf1.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    if (!in_rtf2.getText().trim().equals("")) {
                        return;
                    }
                    if (!in_rtf3.getText().trim().equals("")) {
                        return;
                    }
                    if (!in_rtf4.getText().trim().equals("")) {
                        return;
                    }
                    if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_V) {
                        String s[] = u.getSystemClipboard();
                        if (s != null && s.length == 1) {
                            String r = "";
                            for (int i = 0; i < s[0].length(); i++) {
                                char c = s[0].charAt(i);
                                if (Character.isLetterOrDigit(c)) {
                                    r = r + String.valueOf(c);
                                }
                            }
                            if (r.length() == 16) {
                                in_rtf1.setText(r.substring(0, 4));
                                in_rtf2.setText(r.substring(4, 8));
                                in_rtf3.setText(r.substring(8, 12));
                                in_rtf4.setText(r.substring(12));
                            }
                        }
                    }
                }
            });
            
            pk1 = productkey.substring(0, 4);
            pk2 = productkey.substring(4, 8);
            pk3 = productkey.substring(8, 12);
            pk4 = productkey.substring(12);
            String instno = wab.doGetXMLElementValue(sharkStartFrame.sharedPathplus + u2_base.saXmlFileName, ELEMENT_INSTALLNO);
            while (instno.length() < 3) {
                instno = "0" + instno;
            }
            String manualMac2 = wab.makeCode2(computeridrealkey, productkey, u2_base.swapString(manualMac, computeriduserkey, computeridrealkey));
            deactivationkey = manualMac2 + instno;
            String typecheck = wab.makeCode(computeridrealkey, deactivationkey, 2);
            deactivationkey += u.generateRandom(computeridrealkey, 3) + typecheck;
            deactivationkey = u2_base.swapString(deactivationkey, computeridrealkey, computeriduserkey);
            deactivationkey = wab.chop20(deactivationkey);
            
            dk1 = deactivationkey.substring(0, 4);
            dk2 = deactivationkey.substring(4, 8);
            dk3 = deactivationkey.substring(8, 12);
            dk4 = deactivationkey.substring(12, 16);
            dk5 = deactivationkey.substring(16);
            String deactivationkey2 = deactivationkey;
            deactivationkey = dk1 + "-" + dk2 + "-" + dk3 + "-" + dk4 + "-" + dk5;
            closeonexit = false;
            
            in_rtf1.setEditable(true);
            in_rtf2.setEditable(true);
            in_rtf3.setEditable(true);
            in_rtf4.setEditable(true);
            in_rtf1.setDocument(new keydoc2(in_rtf1, in_rtf2, 4));
            in_rtf2.setDocument(new keydoc2(in_rtf2, in_rtf3, 4));
            in_rtf3.setDocument(new keydoc2(in_rtf3, in_rtf4, 4));
            in_rtf4.setDocument(new keydoc2(in_rtf4, null, 4));
            
            reqwarn.setBorder(BorderFactory.createEmptyBorder());            
            reqwarn.setContentType("text/html");
            reqwarn.setText(BLANK);            
            reqwarn.setOpaque(false);
            reqwarn.setEditable(false);
            reqwarn.addHyperlinkListener(new HyperlinkListener() {
                public void hyperlinkUpdate(HyperlinkEvent e) {
                    if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                        String s = e.getDescription();
                        String ss[] = u.splitString(s);
                        if (ss.length > 1) {
                            OptionPane_base.getErrorMessageDialog(thisframe, Float.parseFloat(ss[0]), ss[1], OptionPane_base.ERRORTYPE_NOEXIT);
                        } else {
                            OptionPane_base.getErrorMessageDialog(thisframe, Float.parseFloat(ss[0]), null, OptionPane_base.ERRORTYPE_NOEXIT);
                        }
                    }
                }
            });

            int listcount = 1;
            
            str_Lk = shark.singledownload ? u.gettext("webauthenticate", "listmessLK_home") : u.gettext("webauthenticate", "listmessLK_school") + BLANK;
            str_Lk = u.edit(str_Lk, String.valueOf(listcount++));
            
            if (!shark.singledownload) {
                str_school = u.edit(str_school, String.valueOf(listcount++));
            }
            str_Dk = u.edit(str_Dk, String.valueOf(listcount++));
            
            String dk = u.gettext("webauth", "deactivationkey");
            setTitle(u.gettext("deactivate_", "deactivation"));
            
            noconnectbutton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    pnAutoFailed.setVisible(false);
                    pnManualDeactivate.setVisible(true);
                    printbutton.setVisible(true);
                    noconnectbutton.setVisible(false);
                    cancelbutton.setVisible(true);
                    cancelbutton.setText(str_ok);
                    state = STATE_MANUAL;
                    doDeactivate();
                }
            });
            cancelbutton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    thisf.dispose();
                    if (closeonexit) {
                        doDispose();
                    }
                }
            });
            printbutton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    printD();
                }
            });
            deactivatebutton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if(!shark.singledownload ){
                        int k = updateinput();
                        okpressed = true;
                        reqwarn.setText(BLANK);
                        if (k < 0) {
                            reqwarn.setText(u.getHTMLWithFontCSS(UIManager.getFont("Label.font"), str_4chars, errorColor));
                            return;
                        }                    
                        String enteredreq = in_rtf1.getText() + in_rtf2.getText() + in_rtf3.getText() + in_rtf4.getText();
                        if (!enteredreq.equals(productkey)) {
                            reqwarn.setText(u.getHTMLWithFontCSS(UIManager.getFont("Label.font"), strreqwarn, errorColor));
                            return;
                        } 
                        reqwarn.setText(BLANK);
                    }
                    progressBarPanelRetry.setVisible(true);
                    pnAutoDeactivate.setVisible(false);
                    deactivatebutton.setVisible(false);
                    activateTimer.start();
                }
            });

            activateTimer = new javax.swing.Timer(0, new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (timerOverdue < 0) {
                        timerOverdue = System.currentTimeMillis() + waittime;
                    }
                    boolean overdue = System.currentTimeMillis() > timerOverdue;
                    if (activateTimer.getDelay() == timertime && (!activateThread.isAlive() || overdue)) {
                        timerOverdue = -1;
                        activateTimer.setDelay(0);
                        boolean res;
                        String errortext = "";
                        if (nc.returnval == null) {
                            res = false;
                        } else if (nc.returnval.equals("1")) {
                            res = true;
                        } else {
                            int errorint = 0;
                            try {
                                errorint = Integer.parseInt(nc.returnval);
                                errortext = getErrorCodeText(u.gettext("deactivate_", "cannot"), errorint);
                            } catch (Exception ex) {
                            }
                            res = false;
                        }
                        if (overdue) {
                            res = false;
                        }
                        nc = null;
                        activateThread = null;
                        activateTimer.stop();
                        tpTryConnect.setVisible(true);
                        lbTryConnect.setText(BLANK);
                        noconnectbutton.setVisible(true);
                        if (res) {
                            doDeactivate();
                            pnAutoDeactivate.setVisible(false);
                            pnAutoSuccess.setVisible(true);
                            cancelbutton.setText(str_ok);
                            cancelbutton.setVisible(true);
          //                  deactivatebutton.setVisible(false);
                            tpContact.setVisible(false);
                            reqwarn.setText(BLANK);
                            noconnectbutton.setVisible(false);
                            pnAutoFailed.setVisible(false);
                            printbutton.setVisible(true);
                            state = STATE_SUCCESS;
                        } else {
                            reqwarn.setText(u.getHTMLWithFontCSS(UIManager.getFont("Label.font"), errortext, errorColor));
                            pnAutoDeactivate.setVisible(false);
                            pnAutoFailed.setVisible(true);
         //                   deactivatebutton.setVisible(false);
                            noconnectbutton.setVisible(true);
                            state = STATE_AUTOFAILED;
                        }
                        progressBarPanelRetry.setVisible(false);
                    } else if (activateTimer.getDelay() == 0) {
                        autodisable();
                        activateTimer.setDelay(timertime);
                    }
                    
                }
            });
            
            JTextPane hyper2 = new JTextPane();
            hyper2.setBorder(BorderFactory.createEmptyBorder());
            hyper2.setContentType("text/html");
            hyper2.setEditable(false);
            hyper2.setCursor(new Cursor(Cursor.HAND_CURSOR));
            hyper2.setOpaque(false);
            hyper2.addHyperlinkListener(new HyperlinkListener() {
                public void hyperlinkUpdate(HyperlinkEvent e) {
                    if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                        u.launchWebSite(e.getDescription());
                    }
                }
            });
            String deactivate = u.gettext("webauthenticate", "deactivatesite");
            String deactivate_display = (shark.language.equals(shark.LANGUAGE_NL) ? u.gettext("webauthenticate", "deactivatesite_display") : deactivate);
            // www.wordshark.co.uk/Deactivation/Deactivate
            String str_viewsite = u.gettext("webauthenticate", "httpactivation", shark.programName.toLowerCase()) + "/" + deactivate_display;
            s2 = str_viewsite.replaceFirst("http://", "");
            
            String ss[] = wab.doGetXMLElementValues(sharkStartFrame.sharedPathplus + u2_base.saXmlFileName, new String[]{ELEMENT_REQUEST, ELEMENT_SCHOOL});
            String req = ss[0];
            String school = shark.singledownload?null:ss[1];
            // www.wordshark.co.uk/Deactivation/SignIn?DeactivateSchoolEtc
            String strurl = buildURL(
                    str_httpactivation,
                    u.gettext("webauthenticate", "redirectsite"),
                    deactivate,
                    school!=null?u.convertForURL(school):null,
                    u.convertForURL(req),
                    deactivationkey2, null, null);
            String hypertext = u.getHTMLWithFontCSS(UIManager.getFont("Label.font"), "<a href=" + strurl + ">" + s2 + "</a>");
            hyper2.setText(hypertext);
            
            JPanel pnButtons = new JPanel(new GridBagLayout());
            
            grid.gridx = -1;
            grid.gridy = 0;
            grid.weighty = 0;
            grid.weightx = 0;
            grid.fill = GridBagConstraints.NONE;
            

            pkPanel.add(in_rtf1, grid);
            pkPanel.add(new JLabel("-"), grid);
            pkPanel.add(in_rtf2, grid);
            pkPanel.add(new JLabel("-"), grid);
            pkPanel.add(in_rtf3, grid);
            pkPanel.add(new JLabel("-"), grid);
            pkPanel.add(in_rtf4, grid);
            
            if (shark.macOS) {
                grid.insets = new Insets(0, border, 0, border);
                pnButtons.add(printbutton, grid);
                pnButtons.add(cancelbutton, grid);
                grid.insets = new Insets(0, 0, 0, border);
                pnButtons.add(noconnectbutton, grid);
                pnButtons.add(deactivatebutton, grid);
                grid.insets = new Insets(0, 0, 0, 0);
                //             pnButtons.add(retrybutton, grid);
            } else {
                grid.insets = new Insets(0, border, 0, border);
                //             pnButtons.add(retrybutton, grid);
                pnButtons.add(deactivatebutton, grid);
                grid.insets = new Insets(0, 0, 0, border);
                pnButtons.add(noconnectbutton, grid);
                pnButtons.add(cancelbutton, grid);
                grid.insets = new Insets(0, 0, 0, 0);
                pnButtons.add(printbutton, grid);
            }
            noconnectbutton.setVisible(false);
            grid.weightx = 1;
            grid.gridx = -1;
            grid.gridy = 0;
            JTextField rtf1 = new JTextField(pk1);
            JTextField rtf2 = new JTextField(pk2);
            JTextField rtf3 = new JTextField(pk3);
            JTextField rtf4 = new JTextField(pk4);
            rtf1.setEditable(false);
            rtf2.setEditable(false);
            rtf3.setEditable(false);
            rtf4.setEditable(false);
            
            grid.gridx = -1;
            grid.gridy = 0;
            
            grid.gridx = -1;
            grid.gridy = 0;
            
            JPanel pnDeactivate = new JPanel(new GridBagLayout());
            JTextField dtf1 = new JTextField(dk1 + " - " + dk2 + " - " + dk3 + " - " + dk4 + " - " + dk5);
            dtf1.setEditable(false);

            grid.insets = new Insets(0, 0, 0, 0);
            pnDeactivate.add(new JLabel(str_Dk), grid);
            pnDeactivate.add(dtf1, grid);

  //          pBar = new JProgressBar();
  //          pBar.setIndeterminate(true);
  //          pBar.setVisible(false);
   //         JPanel progressBarPanel = new JPanel(new GridBagLayout());
    //        grid.gridx = -1;
    //        grid.gridy = 0;
    //        progressBarPanel.add(new JLabel(BLANK), grid);
    //        progressBarPanel.add(pBar, grid);
    //        progressBarPanel.add(new JLabel(BLANK), grid);
            
            grid.gridx = 0;
            grid.gridy = -1;
            
            StringBuffer style = new StringBuffer("font-family:" + sharkStartFrame.treefont.getFamily() + ";");
            style.append("font-weight:" + "bold" + ";");
            style.append("font-size:" + sharkStartFrame.treefont.getSize() + "pt;");

            grid.insets = new Insets(0, 0, border * 2, 0);
            
            
            
            
            pnAutoSuccess.setVisible(false);


                            grid.weightx = 0;                           
                            grid.fill = GridBagConstraints.NONE;
                            grid.insets = new Insets(0, 0, border, 0);
                            pnAutoSuccess.add(new JLabel(strSuccessDescr), grid);
                            JPanel pnLKs = new JPanel(new GridBagLayout());
                            pnLKs.setOpaque(false);
                            grid.gridx = -1;
                            grid.gridy = 0;                            
                            JTextField tf = new JTextField(productkey.substring(0, 4) + " - "
                                    + productkey.substring(4, 8) + " - "
                                    + productkey.substring(8, 12) + " - "
                                    + productkey.substring(12));
                            tf.setEditable(false);
                            JTextPane tpDownload = new JTextPane();                            
                            tpDownload.setOpaque(false);
                            tpDownload.setEditable(false);
                            tpDownload.setBorder(BorderFactory.createEmptyBorder());
                            tpDownload.setContentType("text/html");

                            tpDownload.setText(u.convertToHtml(u.getHTMLWithFontCSS(UIManager.getFont("Label.font"), u.edit(u.gettext("deactivate_", "download"), new String[]{download1, downloadlink, downloadlink}))));
                            tpDownload.addHyperlinkListener(new HyperlinkListener() {
                                public void hyperlinkUpdate(HyperlinkEvent e) {
                                    if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                                        u.launchWebSite(e.getDescription());
                                    }
                                }
                            });                            
                            grid.insets = new Insets(0, 0, 0, border);
                            JLabel lbLK = new JLabel(strlicencekey);
                            lbLK.setOpaque(false);
                            pnLKs.add(lbLK, grid);
                            pnLKs.add(tf, grid);
             grid.gridx = 0;
            grid.gridy = -1;                        
                            grid.insets = new Insets(0, 0, border * 3, 0);
                            pnAutoSuccess.add(pnLKs, grid);
                            pnAutoSuccess.add(tpDownload, grid);                
            
            grid.weightx = 1;
            
            pnAutoDeactivate.add(new JLabel(u.convertToHtml(u.gettext("deactivate_", "instr"), true)), grid);            
            if (!shark.singledownload) {
                pnAutoDeactivate.add(new JLabel(u.gettext("deactivate_", "instr2")), grid);                
                pnAutoDeactivate.add(pkPanel, grid);
            }
            JLabel jliw = new JLabel(u.convertToHtml(u.edit(u.gettext("deactivate_", "instrwarn"), shark.programName, shark.programName)));
            jliw.setFont(jliw.getFont().deriveFont((float) jliw.getFont().getSize() - 2));
            pnAutoDeactivate.add(jliw, grid);
            grid.insets = new Insets(0, 0, 0, 0);
            pnAutoDeactivate.add(new JLabel(BLANK), grid);
            grid.gridx = -1;
            grid.gridy = 0;            
            mantfSchool = new JTextField();
            mantfSchool.setEditable(false);
            mantfSchool.setText(schoolname);
            JPanel schoolpan = new JPanel(new GridBagLayout());
            schoolpan.add(new JLabel(str_school), grid);
            schoolpan.add(mantfSchool, grid);
            
            grid.gridx = 0;
            grid.gridy = -1;
            
            grid.gridx = 0;
            grid.gridy = -1;
            
            tpTryConnect = new JTextPane();            
            tpTryConnect.setBorder(BorderFactory.createEmptyBorder());            
            tpTryConnect.setContentType("text/html");
            if (shark.singledownload) {
                tpTryConnect.setText(u.getHTMLWithFontCSS(UIManager.getFont("Label.font"), u.gettext("deactivate_", "tryconnectmess_home")));
            } else {
                tpTryConnect.setText(u.getHTMLWithFontCSS(UIManager.getFont("Label.font"), u.gettext("deactivate_", "tryconnectmess_school")));
            }
            
            tpTryConnect.setOpaque(false);
            tpTryConnect.setEditable(false);
            tpTryConnect.addHyperlinkListener(new HyperlinkListener() {
                public void hyperlinkUpdate(HyperlinkEvent e) {
                    if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                        String s = e.getDescription();
                        switch (s){
                            case "0":
                                progressBarPanelRetry.setVisible(true);
                                activateTimer.start();
                                tpTryConnect.setVisible(false);
    //                            lbTryConnect.setText(tryconnect);
                                noconnectbutton.setVisible(false);
                                break;
                            case "1":
                                new ProxySettings(thisjd);
                                break;
                            case "2":
                                String s1;
                                if (shark.macOS) {
                                    s1 = u.gettext("deactivate_", "helpmessmac", str_offline);
                                } else {
                                    s1 = u.edit(u.gettext("deactivate_", "helpmess"),
                                            new String[]{
                                                System.getProperty("java.home") + File.separator + "bin" + File.separator + "javaw.exe",
                                                str_offline});
                                }                            
                                showtext(thisjd, u.gettext("deactivate_", "helptitle"), s1);
                                break;
                        }
                    }
                }
            });
            grid.insets = new Insets(0, 0, 0, 0);
            pnAutoFailed.add(tpTryConnect, grid);
            pnAutoFailed.add(lbTryConnect, grid);
    //        pnAutoFailed.add(new JLabel(BLANK), grid);
    //        pnAutoFailed.add(progressBarPanel2, grid);
            pnAutoFailed.setVisible(false);
            grid.gridx = 0;
            grid.gridy = -1;
            
            JPanel pnGoTO = new JPanel(new GridBagLayout());
            grid.insets = new Insets(0, 0, 0, border);
            pnGoTO.add(new JLabel(s1), grid);
            pnGoTO.add(hyper2, grid);            
            grid.gridx = -1;
            grid.gridy = 0;            
            int vertgapbig = border * 12 / 5;
            int vertgap = border * 7 / 10;            
            grid.gridx = 0;
            grid.gridy = -1;
            grid.insets = new Insets(0, 0, vertgap, 0);
            pnManualDeactivate.add(pnGoTO, grid);
            grid.insets = new Insets(0, 0, vertgapbig, 0);
            pnManualDeactivate.add(new JLabel(u.gettext("webauthenticate", "sepdevice")), grid);
            grid.insets = new Insets(0, 0, vertgap, 0);
            pnManualDeactivate.add(new JLabel(str_enterlicencedetails), grid);
            grid.insets = new Insets(0, 0, vertgap, 0);
            JPanel pnLK = new JPanel(new GridBagLayout());
            if (shark.singledownload) {
                grid.gridx = -1;
                grid.gridy = 0;
                grid.insets = new Insets(0, 0, 0, border);
                pnLK.add(new JLabel(str_Lk), grid);
                grid.insets = new Insets(0, 0, 0, 0);
                JTextField tfLK = new JTextField();
                tfLK.setEditable(false);
                String lkwh = productkey.substring(0, 4) + " - "
                        + productkey.substring(4, 8) + " - "
                        + productkey.substring(8, 12) + " - "
                        + productkey.substring(12);
                tfLK.setText(lkwh);
                pnLK.add(tfLK, grid);
            } else {
                pnLK.add(new JLabel(str_Lk), grid);
            }            
            grid.insets = new Insets(0, 0, vertgap, 0);
            grid.gridx = 0;
            grid.gridy = -1;
            
            pnManualDeactivate.add(pnLK, grid);
            if (!shark.singledownload) {
                pnManualDeactivate.add(schoolpan, grid);
            }
            pnManualDeactivate.add(pnDeactivate, grid);
            pnManualDeactivate.setVisible(false);
            printbutton.setVisible(false);
            

            lbTryConnect.setForeground(Color.red);
            
            StringBuffer style2 = new StringBuffer("font-family:" + sharkStartFrame.treefont.getFamily() + ";");
            style.append("font-weight:" + "normal" + ";");
            style.append("font-size:" + (sharkStartFrame.treefont.getSize() - 4) + "pt;");
            
            String url = u.gettext("msupport", "site");
            String url_display = u.gettext("msupport", "site_display");
            
            tpContact = new JTextPane();
            tpContact.setBorder(BorderFactory.createEmptyBorder());
            tpContact.setContentType("text/html");
            
            tpContact.setText(u.getHTMLWithFontCSS(UIManager.getFont("Label.font"), "<html><body style=\"" + style + "\">" + "<a href=\"" + url + "\">" + url_display + "</a>" + "</body></html>"));
            tpContact.setEditable(false);
            tpContact.setCursor(new Cursor(Cursor.HAND_CURSOR));
            tpContact.setOpaque(false);
            tpContact.addHyperlinkListener(new HyperlinkListener() {
                public void hyperlinkUpdate(HyperlinkEvent e) {
                    if (e.getEventType().equals(HyperlinkEvent.EventType.ACTIVATED)) {
                        String ss = e.getDescription();
                        u.launchWebSite(ss);
                    }
                }
            });            
            grid.gridx = 0;
            grid.gridy = -1;
            
                JProgressBar pBarRetry = new JProgressBar();
                pBarRetry.setIndeterminate(true);
                progressBarPanelRetry = new JPanel(new GridBagLayout());
                JLabel retrywarnlabel = new JLabel(str_tryconnect);
                retrywarnlabel.setForeground(Color.red);
                progressBarPanelRetry.add(retrywarnlabel, grid);
                progressBarPanelRetry.add(new JLabel(BLANK), grid);
                progressBarPanelRetry.add(pBarRetry, grid);
                progressBarPanelRetry.add(new JLabel(BLANK), grid);
                grid.gridx = -1;
                grid.gridy = 0;
                progressBarPanelRetry.setVisible(false);            
            
            
            
            
            grid.anchor = GridBagConstraints.EAST;
            grid.fill = GridBagConstraints.NONE;
            grid.weighty = 0;
            addToBase(tpContact, grid);

                JPanel contentpanes = new JPanel(new GridBagLayout());

                grid.anchor = GridBagConstraints.CENTER;
                grid.weighty = 1;
                contentpanes.add(progressBarPanelRetry, grid);
                contentpanes.add(pnAutoDeactivate, grid);
                contentpanes.add(pnAutoSuccess, grid);
                contentpanes.add(pnAutoFailed, grid);
                contentpanes.add(pnManualDeactivate, grid);
                grid.gridx = -1;
                grid.gridy = 0;
                int sh = (int) sharkStartFrame.screenSize.getHeight();
                int warnLbTop = sh * 1 / 60;
                int warnLbBottom = sh * 2 / 60;                
                grid.insets = new Insets(warnLbTop, 0, warnLbBottom, 0);
                JPanel warnpanel = new JPanel(new GridBagLayout());
                warnpanel.add(new JLabel(" "), grid);
                warnpanel.add(reqwarn, grid);
                warnpanel.add(new JLabel(" "), grid);
                grid.insets = new Insets(0, 0, 0, 0);
                grid.gridx = 0;
                grid.gridy = -1;
                addToBase(contentpanes, grid);
                grid.weighty = 0;
                addToBase(warnpanel, grid);
                grid.insets = new Insets(border, 0, 0, 0);
                addToBase(pnButtons, grid);            

            deactivationkey = dk + deactivationkey;
            setColor();
            getRootPane().setDefaultButton(deactivatebutton);
            setVisible(true);
        }
     /*   
        void tryingConnectionUI(boolean trying) {
            tpTryConnect.setVisible(!trying);
 //           pBar2.setVisible(trying);
            lbTryConnect.setText(trying ? tryconnect : BLANK);
            noconnectbutton.setVisible(!trying);
        }
     */   
        public int updateinput() {
                if (!(checkinput(pkPanel) && (in_rtf1.getText()).length() == 4
                        && (in_rtf2.getText()).length() == 4
                        && (in_rtf3.getText()).length() == 4
                        && (in_rtf4.getText()).length() == 4) ){
                    return -1;
                }
            return 0;
        }    
        
        private boolean checkinput(JPanel jp) {
            Component c[] = jp.getComponents();
            JTextField tf;
            boolean isok = true;
            for (int i = 0; i < c.length; i++) {
                if (c[i] instanceof JTextField) {
                    tf = (JTextField) c[i];
                    String s;
                    if ((s = tf.getText()).length() == tf.getColumns()) {
                        if (s.indexOf(BLANK) >= 0) {
                            isok = false;
                            break;
                        }
                    } else {
                        isok = false;
                        break;
                    }
                }
            }
            if (okpressed && !isok) {
                reqwarn.setText(u.getHTMLWithFontCSS(UIManager.getFont("Label.font"), str_4chars, errorColor));
            } else {
                reqwarn.setText(BLANK);
            }
            return isok;
        }     
        

        
        public void dispose() {
            if (closeonexit) {
                doDisable();
            }
            super.dispose();
        }
        
        private void doDeactivate() {
            wab.doSetXMLElementValue(sharkStartFrame.sharedPathplus + u2_base.saXmlFileName, ELEMENT_DEACTIVATIONKEY, deactivationkey);
            PrintWriter write = null;
            File home = new File(sharkStartFrame.sharedPathplus + "deactivation.txt");
            try {
                if (!home.exists()) {
                    home.createNewFile();
                    u.setNewFilePermissions(home);
                }
                write = new PrintWriter(new FileWriter(home, false));
                write.println(deactivationkey);
            } catch (IOException ex) {
                ex.printStackTrace();
            } finally {
                if (write != null) {
                    write.close();
                }
            }
            try {
                enddisable();
            } catch (Exception e) {
            }
            doClear();
            closeonexit = true;
        }
        
        private void doDispose() {
            sharkStartFrame.mainFrame.finalize();
        }
        
        boolean printD() {
            PrinterJob printJob = PrinterJob.getPrinterJob();
            printJob.setJobName(u.gettext("deactivate_", "printjob"));
            PageFormat pageFormat = printJob.defaultPage();
            pageFormat.setOrientation(PageFormat.PORTRAIT);
            printJob.setPrintable(this, pageFormat);
            if (printJob.printDialog()) {
                try {
                    printJob.print();
                    return true;
                } catch (Throwable t) {
                }
            }
            return false;
        }
        
        public int print(Graphics g, PageFormat f, int a) throws PrinterException {
            
            if (a == 0) {
                if(state == STATE_MANUAL){
                    g.translate((int) f.getImageableX(), (int) f.getImageableY());
                    Dimension dd = new Dimension((int) f.getImageableWidth(),
                            (int) f.getImageableHeight());
                    int w = dd.width;
                    int h = dd.height;


                    Font f1 = sizeFont(g, s1+s1, w);
                    Font f4 = f1.deriveFont((float)f1.getSize()-2);
                    g.setFont(f4);
                    FontMetrics m4 = g.getFontMetrics();

                    g.drawString(footer, (dd.width - m4.stringWidth(footer)) / 2, h - m4.getHeight());


                    int xx = 0;
                    int yy = 0;                

                    g.setFont(f1);
                    FontMetrics m1 = g.getFontMetrics();
                    int miniborder = 4;
                    int h1 = m1.getHeight();
                    int xx2;
                    yy += m1.getHeight() * 16 / 10;

                    g.drawString(title, (((int) f.getImageableWidth()) / 2) - (m1.stringWidth(title) / 2), yy);
                    yy += m1.getHeight() * 3;

                    g.drawString(s1, xx, yy);
                    yy += m1.getHeight() * 16 / 10;
                    g.drawString(s2, xx, yy);
                    yy += m1.getHeight() * 3;
                    g.drawString(str_enterlicencedetails, xx, yy);
                    yy += m1.getHeight() * 2;

                    g.drawString(str_Lk, xx, yy);
                    if(schoolname!=null){
                        String s4 = str_school + schoolname;
                        yy += m1.getHeight() * 2;
                        g.drawString(s4, xx, yy);
                    }
                    yy += (m1.getHeight() + (2 * miniborder)) * 16 / 10;
                    g.drawString(str_Dk, xx, yy);
                    int stw2;
                    stw2 = m1.stringWidth(dk1);
                    xx2 = xx + m1.stringWidth(str_Dk);

                    g.drawRect(xx2, yy - m1.getAscent() - miniborder, stw2 + (miniborder * 2), h1 + (miniborder * 2));
                    g.drawString(dk1, xx2 + miniborder, yy);
                    xx2 += stw2 + (miniborder * 3);
                    g.drawString("-", xx2, yy);
                    xx2 += m1.stringWidth("-") + miniborder;

                    stw2 = m1.stringWidth(dk2);
                    g.drawRect(xx2, yy - m1.getAscent() - miniborder, stw2 + (miniborder * 2), h1 + (miniborder * 2));
                    g.drawString(dk2, xx2 + miniborder, yy);
                    xx2 += stw2 + (miniborder * 3);
                    g.drawString("-", xx2, yy);
                    xx2 += m1.stringWidth("-") + miniborder;

                    stw2 = m1.stringWidth(dk3);
                    g.drawRect(xx2, yy - m1.getAscent() - miniborder, stw2 + (miniborder * 2), h1 + (miniborder * 2));
                    g.drawString(dk3, xx2 + miniborder, yy);
                    xx2 += stw2 + (miniborder * 3);
                    g.drawString("-", xx2, yy);
                    xx2 += m1.stringWidth("-") + miniborder;

                    stw2 = m1.stringWidth(dk4);
                    g.drawRect(xx2, yy - m1.getAscent() - miniborder, stw2 + (miniborder * 2), h1 + (miniborder * 2));
                    g.drawString(dk4, xx2 + miniborder, yy);
                    xx2 += stw2 + (miniborder * 3);
                    g.drawString("-", xx2, yy);
                    xx2 += m1.stringWidth("-") + miniborder;

                    stw2 = m1.stringWidth(dk5);
                    g.drawRect(xx2, yy - m1.getAscent() - miniborder, stw2 + (miniborder * 2), h1 + (miniborder * 2));
                    g.drawString(dk5, xx2 + miniborder, yy);
                }
                if(state == STATE_SUCCESS){
                    g.translate((int) f.getImageableX(), (int) f.getImageableY());
                    Dimension dd = new Dimension((int) f.getImageableWidth(),
                            (int) f.getImageableHeight());
                    int w = dd.width;
                    int h = dd.height;

                    Font f1 = sizeFont(g, s1+s1, w);
                    Font f4 = f1.deriveFont((float)f1.getSize()-2);
                    g.setFont(f4);
                    FontMetrics m4 = g.getFontMetrics();

                    g.drawString(footer, (dd.width - m4.stringWidth(footer)) / 2, h - m4.getHeight());


                    int xx = 0;
                    int yy = 0;                

                    g.setFont(f1);
                    FontMetrics m1 = g.getFontMetrics();
                    int miniborder = 4;
                    int h1 = m1.getHeight();
                    int xx2;
                    yy += m1.getHeight() * 16 / 10;
                    String tit = shark.programName + " " + shark.versionNo;
                    g.drawString(tit, (((int) f.getImageableWidth()) / 2) - (m1.stringWidth(tit) / 2), yy);
                    yy += m1.getHeight() * 3;
                    String lk = strlicencekey  +  "  " + productkey;
                    

                    g.drawString(strlicencekey   +  "  ", xx, yy);
                    
                    
                    int stw2;
                    String lk1 =  productkey.substring(0, 4);
                    String lk2 =  productkey.substring(4, 8);
                    String lk3 =  productkey.substring(8, 12);
                    String lk4 =  productkey.substring(12);
                           
                    stw2 = m1.stringWidth(lk1);
                    xx2 = xx + m1.stringWidth(strlicencekey  +  "  ");

                    g.drawRect(xx2, yy - m1.getAscent() - miniborder, stw2 + (miniborder * 2), h1 + (miniborder * 2));
                    g.drawString(lk1, xx2 + miniborder, yy);
                    xx2 += stw2 + (miniborder * 3);
                    g.drawString("-", xx2, yy);
                    xx2 += m1.stringWidth("-") + miniborder;

                    stw2 = m1.stringWidth(lk2);
                    g.drawRect(xx2, yy - m1.getAscent() - miniborder, stw2 + (miniborder * 2), h1 + (miniborder * 2));
                    g.drawString(lk2, xx2 + miniborder, yy);
                    xx2 += stw2 + (miniborder * 3);
                    g.drawString("-", xx2, yy);
                    xx2 += m1.stringWidth("-") + miniborder;

                    stw2 = m1.stringWidth(lk3);
                    g.drawRect(xx2, yy - m1.getAscent() - miniborder, stw2 + (miniborder * 2), h1 + (miniborder * 2));
                    g.drawString(lk3, xx2 + miniborder, yy);
                    xx2 += stw2 + (miniborder * 3);
                    g.drawString("-", xx2, yy);
                    xx2 += m1.stringWidth("-") + miniborder;

                    stw2 = m1.stringWidth(lk4);
                    g.drawRect(xx2, yy - m1.getAscent() - miniborder, stw2 + (miniborder * 2), h1 + (miniborder * 2));
                    g.drawString(lk4, xx2 + miniborder, yy);
                    xx2 += stw2 + (miniborder * 3);
       //             g.drawString("-", xx2, yy);
       //             xx2 += m1.stringWidth("-") + miniborder;
                    
                    yy += (m1.getHeight() + (2 * miniborder)) * 16 / 10;
                    
                    g.drawString(download1, xx, yy);
                    yy += m1.getHeight() * 16 / 10;
                    g.drawString(downloadlink, xx, yy);

                }                
                return Printable.PAGE_EXISTS;
            } else {
                return Printable.NO_SUCH_PAGE;
            }
        }
        
        public Font sizeFont(Graphics g, String s, int w) {
            Font f1 = g.getFont().deriveFont((float) 40);
            g.setFont(f1);
            FontMetrics m = g.getFontMetrics();
            while (f1.getSize() > 8 && m.stringWidth(s) > w) {
                f1 = f1.deriveFont((float) f1.getSize() - 1);
                g.setFont(f1);
                m = g.getFontMetrics();
            }
            return f1;
        }
        
        class keydoc2 extends keydoc {
            
            keydoc2(JTextField ow, JTextField ne, int chars) {
                super(ow, ne, chars);
            }
            
            protected void removeUpdate(DefaultDocumentEvent chng) {
                super.removeUpdate(chng);
                reqwarn.setText(BLANK);
            }
            
            public void insertString(int o, String s, AttributeSet a) {
                super.insertString(o, s, a);
                reqwarn.setText(BLANK);
            }
        }
    }


    private String getErrorCodeText(String premess, int i) {
        String errno = null;        
        String errmess = null;
        if (i == ERRORNOFINDLICENCE) {
            errno = "20";
            errmess = u.gettext("errorcodes", "errorcode" + errno);
        } else if (i == ERRORNOUPDATEONEXISTING) {
            errno = "21";
            errmess = u.gettext("errorcodes", "errorcode" + errno);
        } else if (i == ERRORNOUPDATE) {
            errno = "22";
            errmess = u.gettext("errorcodes", "errorcode" + errno);
        } else if (i == ERRORBADRESPONSE) {
            errno = "23";
        } else if (i == ERRORVOID) {
            errno = "24";
            errmess = u.gettext("errorcodes", "errorcode" + errno);
        } else if (i == ERRORISMASTER) {
            errno = "25";
            errmess = u.gettext("errorcodes", "errorcode" + errno);
        } else if (i == ERRORBEENVOIDED) {
            errno = "26";
            errmess = u.gettext("errorcodes", "errorcode" + errno);
        } else if (i == ERRORINSTALLNOERROR) {
            errno = "27";
            errmess = u.gettext("errorcodes", "errorcode" + errno);
        } else if (i == ERRORBEENDEACTIVATED) {
            errno = "28";
            errmess = u.gettext("errorcodes", "errorcode" + errno);
        } else if (i == ERRORINCORRECTINPUT) {
            errno = "29";
            errmess = u.gettext("errorcodes", "errorcode" + errno);
        } else if (i == ERRORNOSUPPORT) {
            errno = "40";
            errmess = u.gettext("errorcodes", "errorcode" + errno);
        } 
        else if (i == ERRORUNSPECIFIED) {
            errno = "75";
            errmess = u.gettext("errorcodes", "errorcode" + errno);
        }else {
            return String.valueOf(i);
        }
        if (errmess != null) {
            return doAHref(premess, new String[]{errno, errmess});
        } else {
            return doAHref(premess, new String[]{errno});
        }
    }
    
    String doAHref(String premess, String[] info) {
        String no = String.valueOf(info[0]);
        String errmess = u.gettext("webauthenticate", "errorcode");
        String s = (premess == null ? "" : premess) + "&nbsp;<a href=";
        s += "'" + no + (info.length > 1 ? "|" + info[1] : "") + "'" + " style=\"color: blue;\">";
        s += errmess + "&nbsp;" + no;
        s += "</a>";        
        return doHTML(s, errorColor);
    }
    
    String doHTML(String s) {
        return doHTML(s, null);
    }    
    
    String doHTML(String s, String c) {
        JLabel lbTemplate = new JLabel();
        Font font = lbTemplate.getFont();
        StringBuffer style = new StringBuffer("font-family:" + font.getFamily() + ";");
        style.append("font-weight:" + (font.isBold() ? "bold" : "normal") + ";");
        style.append("font-size:" + font.getSize() + "pt;");        
        if (c != null) {
            style.append("color: " + c + ";");
        }        
        return "<html><body style=\"" + style + "\">"
                + s
                + "</body></html>";
    }    
    
    public class printDetails implements Printable {
        String lk;
        String print1;
        String print2;
        String print3a;
        String print3b = null;
        String print3c = null;
        String print3d = null;
        String print3e;
        String printIKC;
        String print5;
        String printinstno = null;
        String webaddress;
        static final int TYPEAUTH = 0;
        static final int TYPECONVERT = 1;
        int t;
        String indent = "    ";
        String spaces2 = "   ";
        String footer = u.gettext("webauthenticate", "printfooter");
        
        printDetails(String providelb,
                String provideReq,
                String request,
                String provideSchool,
                String providePostcode,
                String provideNote,
                String provideInstallNo,
                String provideIKC,
                String IKC,
                String website, int type) {
            lk = request;
            t = type;
            webaddress = indent + website;
            print1 = u.gettext(gettype(type), "print1", shark.programName + BLANK + shark.versionNo);
            print2 = providelb;
            print3a = indent + provideReq;
            if (provideSchool != null) {
                print3b = indent + provideSchool;
            }
            if (providePostcode != null) {
                print3c = indent + providePostcode;
            }
            if (provideNote != null) {
                print3d = indent + provideNote;
            }
            print3e = indent + provideIKC;
            printIKC = IKC;
            if (provideInstallNo != null) {
                printinstno = indent + provideInstallNo
                        + //                  (String) db.find(sharkStartFrame.optionsdb, "wa_installno", db.TEXT);
                        wab.doGetXMLElementValue(sharkStartFrame.sharedPathplus + u2_base.saXmlFileName, ELEMENT_INSTALLNO);
            }
            print5 = u.gettext(gettype(type), "print2");
        }
        
        private String gettype(int type) {
            if (type == TYPEAUTH) {
                return "webauthenticate";
            } else if (type == TYPECONVERT) {
                return "removeexpiry_";
            } else {
                return "";
            }
        }
        

      
        
        boolean printD() {
            PrinterJob printJob = PrinterJob.getPrinterJob();
            printJob.setJobName(u.gettext("webauthenticate", "printjob"));
            PageFormat pageFormat = printJob.defaultPage();
            pageFormat.setOrientation(PageFormat.PORTRAIT);
            printJob.setPrintable(this, pageFormat);
            if (printJob.printDialog()) {
                try {
                    printJob.print();
                    return true;
                } catch (Throwable t2) {
                    t2=t2;
                }
            }
            return false;
        }
        
        public int print(Graphics g, PageFormat f, int a) throws PrinterException {
            if (a == 0) {
                g.translate((int) f.getImageableX(), (int) f.getImageableY());
                Dimension dd = new Dimension((int) f.getImageableWidth(),
                        (int) f.getImageableHeight());
                int w = dd.width;
                int h = dd.height;
                int miniborder = 4;
                int stw2;
                int xx2;
                int xx = 0;
                int yy = 0;
                Font f1 = sizeFont(g, print1, w);
                Font f4 = f1.deriveFont((float)f1.getSize()-2);
                g.setFont(f4);
                FontMetrics m4 = g.getFontMetrics();
                g.drawString(footer, (dd.width - m4.stringWidth(footer)) / 2, h - m4.getHeight());                
                g.setFont(f1);
                FontMetrics m1 = g.getFontMetrics();
                int h1 = m1.getHeight();
                yy += m1.getHeight() * 16 / 10;
                g.drawString(print1, xx, yy);
                yy += m1.getHeight() * 16 / 10;
                g.drawString(webaddress, xx, yy);
                yy += m1.getHeight() * 16 / 10;
                yy += m1.getHeight() * 16 / 10;
                g.drawString(print2, xx, yy);
                yy += m1.getHeight() * 16 / 10;
                g.drawString(print3a, xx, yy);
                if(lk!=null){
                    stw2 = m1.stringWidth(print3a);
                    xx2 = xx + m1.stringWidth(print3a);
                    
                    String lk1 = lk.substring(0,4);
                    String lk2 = lk.substring(4,8);
                    String lk3 = lk.substring(8,12);
                    String lk4 = lk.substring(12);

            
                    stw2 = m1.stringWidth(lk1);
                    g.drawRect(xx2, yy - m1.getAscent() - miniborder, stw2 + (miniborder * 2), h1 + (miniborder * 2));
                    g.drawString(lk1, xx2 + miniborder, yy);
                    xx2 += stw2 + (miniborder * 3);
                    g.drawString("-", xx2, yy);
                    xx2 += m1.stringWidth("-") + miniborder;                    
                    
                    
                    stw2 = m1.stringWidth(lk2);
                    g.drawRect(xx2, yy - m1.getAscent() - miniborder, stw2 + (miniborder * 2), h1 + (miniborder * 2));
                    g.drawString(lk2, xx2 + miniborder, yy);
                    xx2 += stw2 + (miniborder * 3);
                    g.drawString("-", xx2, yy);
                    xx2 += m1.stringWidth("-") + miniborder;
                    
                    stw2 = m1.stringWidth(lk3);
                    g.drawRect(xx2, yy - m1.getAscent() - miniborder, stw2 + (miniborder * 2), h1 + (miniborder * 2));
                    g.drawString(lk3, xx2 + miniborder, yy);
                    xx2 += stw2 + (miniborder * 3);
                    g.drawString("-", xx2, yy);
                    xx2 += m1.stringWidth("-") + miniborder;

                    stw2 = m1.stringWidth(lk4);
                    g.drawRect(xx2, yy - m1.getAscent() - miniborder, stw2 + (miniborder * 2), h1 + (miniborder * 2));
                    g.drawString(lk4, xx2 + miniborder, yy);                   
                }
                if (printinstno != null) {
                    yy += m1.getHeight() * 16 / 10;
                    g.drawString(printinstno, xx, yy);
                }
                if (print3b != null) {
                    yy += m1.getHeight() * 16 / 10;
                    g.drawString(print3b, xx, yy);
                }
                if (print3c != null) {
                    yy += m1.getHeight() * 16 / 10;
                    g.drawString(print3c, xx, yy);
                }
                if (print3d != null) {
                    yy += m1.getHeight() * 16 / 10;
                    g.drawString(print3d, xx, yy);
                }
                yy += m1.getHeight() * 16 / 10;
                g.drawString(print3e, xx, yy);
                

             
                
                
                
                String ps1 = printIKC.substring(0, 5);
                String ps2 = printIKC.substring(5, 10);
                String ps3 = printIKC.substring(10);
                
                stw2 = m1.stringWidth(ps1);
                xx2 = xx + m1.stringWidth(print3e);
                
                g.drawRect(xx2, yy - m1.getAscent() - miniborder, stw2 + (miniborder * 2), h1 + (miniborder * 2));
                g.drawString(ps1, xx2 + miniborder, yy);
                xx2 += stw2 + (miniborder * 3);
                g.drawString("-", xx2, yy);
                xx2 += m1.stringWidth("-") + miniborder;
                
                stw2 = m1.stringWidth(ps2);
                g.drawRect(xx2, yy - m1.getAscent() - miniborder, stw2 + (miniborder * 2), h1 + (miniborder * 2));
                g.drawString(ps2, xx2 + miniborder, yy);
                xx2 += stw2 + (miniborder * 3);
                g.drawString("-", xx2, yy);
                xx2 += m1.stringWidth("-") + miniborder;
                
                stw2 = m1.stringWidth(ps3);
                g.drawRect(xx2, yy - m1.getAscent() - miniborder, stw2 + (miniborder * 2), h1 + (miniborder * 2));
                g.drawString(ps3, xx2 + miniborder, yy);
                
                yy += m1.getHeight() * 16 / 10;
                yy += m1.getHeight() * 16 / 10;
                g.drawString(print5, xx, yy);
                yy += m1.getHeight();
                h1 = m1.getHeight() * 13 / 10;
                int w1 = (h1 * 6) / 8;
                int w2 = w1 * 4;
                int gap = h1 / 3;
                int ymid = yy + (h1 / 2);
                g.drawRect(xx, yy, w2, h1);
                g.drawLine(xx + w2 + (gap / 2), ymid, xx + w2 + (gap / 2) + gap, ymid);
                xx = xx + w2 + gap + gap;
                g.drawRect(xx, yy, w2, h1);
                g.drawLine(xx + w2 + (gap / 2), ymid, xx + w2 + (gap / 2) + gap, ymid);
                xx = xx + w2 + gap + gap;
                g.drawRect(xx, yy, w2, h1);
                g.drawLine(xx + w2 + (gap / 2), ymid, xx + w2 + (gap / 2) + gap, ymid);
                xx = xx + w2 + gap + gap;
                g.drawRect(xx, yy, w2, h1);
                g.drawLine(xx + w2 + (gap / 2), ymid, xx + w2 + (gap / 2) + gap, ymid);
                xx = xx + w2 + gap + gap;
                g.drawRect(xx, yy, w2, h1);
                
                return Printable.PAGE_EXISTS;
            } else {
                return Printable.NO_SUCH_PAGE;
            }
        }
        
        public Font sizeFont(Graphics g, String s, int w) {
            Font f = g.getFont().deriveFont((float) 40);
            g.setFont(f);
            FontMetrics m = g.getFontMetrics();
            while (f.getSize() > 8 && m.stringWidth(s) > w) {
                f = f.deriveFont((float) f.getSize() - 1);
                g.setFont(f);
                m = g.getFontMetrics();
            }
            return f;
        }
    }
    
    class keydoc extends PlainDocument {
        
        JTextField owner, next;
        int charno;
        
        keydoc(JTextField ow, JTextField ne, int chars) {
            super();
            charno = chars;
            owner = ow;
            next = ne;
        }
        
        protected void removeUpdate(DefaultDocumentEvent chng) {
            super.removeUpdate(chng);
        }
        
        public void insertString(int o, String s, AttributeSet a) {
            s = s.toUpperCase().trim();
            try {
                if (getLength() < charno) {
                    super.insertString(o, s, a);
                }
            } catch (BadLocationException e) {
            }
            if (o == charno - 1 && getLength() == charno) {
                if (next != null) {
                    next.requestFocus();
                }
            }
        }
    }
}
