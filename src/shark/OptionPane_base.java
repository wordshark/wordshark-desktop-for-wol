/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package shark;


import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
/**
 *
 * @author MacBook Air
 */
public class OptionPane_base {

    static String ok = shark.language.equals(shark.LANGUAGE_EN)?"OK":"OK";
    static String retry = shark.language.equals(shark.LANGUAGE_EN)?"Retry":"Opnieuw";
    static String cancel = shark.language.equals(shark.LANGUAGE_EN)?"Cancel":"Annuleren";
    static String errorurl = shark.language.equals(shark.LANGUAGE_EN)?"http://www.wordshark.co.uk/Errors?error=":
            "http://www.wordshark.co.uk/ErrorsNL?error=";
    static String errorcodetext = shark.language.equals(shark.LANGUAGE_EN)?"Error code ":"Error code ";
    
    public static final int PATCH_CRITICAL = 0;
    public static final int PATCH_REMINDER = 1;
    public static final int PATCH_USER_NOCONNECT = 2;
    public static final int PATCH_USER_NOUPDATES = 3;
    public static final int PATCH_USER_UPDATE = 4;
    public static final int PATCH_USER_CRITICALBLOCK = 5;
    static boolean errorMessageShowing = false;
    public static final int ERRORTYPE_EXIT = 0;
    public static final int ERRORTYPE_NOEXIT = 1;
    public static final int ERRORTYPE_CANCELEXIT = 2;
    public static final int INPUT_LAUNCHMAIL = 0;
    public static final int INPUT_LAUNCHWEBPAGE = 1;
    static final String DEFAULTFONTSIZE = "14";



    public static String[] getInputMessageDialog(Window owner, String title,  String message, String vals[], final int type){
//       String fontSize = "14";
        String fontSize = String.valueOf(sharkStartFrame.treefont==null?DEFAULTFONTSIZE:sharkStartFrame.treefont.getSize());
       JLabel label = new JLabel();
       Font font = label.getFont();
       StringBuffer style = new StringBuffer("font-family:" + font.getFamily() + ";");
       style.append("font-weight:" + "normal" + ";");
       style.append("font-size:" + fontSize + "pt;");
       message = u.convertToInnerHtml(message);
       JEditorPane ep = new JEditorPane("text/html", "<html><body style=\"" + style + "\">"
            + message
            + "</body></html>");
       ep.addHyperlinkListener(new HyperlinkListener()
       {
           public void hyperlinkUpdate(HyperlinkEvent e)
           {
               if (e.getEventType().equals(HyperlinkEvent.EventType.ACTIVATED)){
                   if(type == INPUT_LAUNCHMAIL)
                     u.launchMailto("mailto:"+e.getDescription());
               }
           }
       });
       ep.setEditable(false);
       ep.setBackground(label.getBackground());
       Object[] params = new Object[]{ep};
       JOptionPane getpw = new JOptionPane(params, JOptionPane.PLAIN_MESSAGE, 0, null, vals, vals[0]);
       JDialog dialog;
       if(owner==null) dialog = getpw.createDialog(null, shark.programName+ " " + shark.versionNo);
       else dialog = getpw.createDialog(owner, shark.programName+ " " + shark.versionNo);
       getpw.setWantsInput(true);
       getpw.setInputValue("");
       dialog.pack();
       dialog.setVisible(true);
       return new String[]{(String)getpw.getValue(), (String)getpw.getInputValue()};
    }


    public static void getErrorMessageDialog(Window owner, float errno,  String message, int errortype){
        errorMessageDialogInit(owner, errno, message, errortype);
    }
    
    static void errorMessageDialogInit(Window owner, float errno,  String message, int errortype){
       if(errorMessageShowing)return;
       String REDIRECTION_DISPLAY = errorurl;         
//       String fontSize = "14";
       String fontSize = String.valueOf(sharkStartFrame.treefont==null?DEFAULTFONTSIZE:sharkStartFrame.treefont.getSize());
       String errorCode = errorcodetext;
       JLabel label = new JLabel();
       Font font = label.getFont();

       // create some css from the label's font
//        StringBuffer style = new StringBuffer("font-family:" + font.getFamily() + ";");
//        style.append("font-weight:" + (font.isBold() ? "bold" : "normal") + ";");
//        style.append("font-size:" + font.getSize() + "pt;");
        StringBuffer style = new StringBuffer("font-family:" + font.getFamily() + ";");
        style.append("font-weight:" + "normal" + ";");
        style.append("font-size:" + fontSize + "pt;");
        String res = "";
        if(errno>0)
            res = errorCode+getStringFromFloat(errno);
        if(message!=null){
            if(errno>0)
                res += "<br><br>";
            for(int i = 0; message!=null && i < message.length(); i++){
               if(message.charAt(i)=='|')res=res.concat("<br>");
               else res=res.concat(String.valueOf(message.charAt(i)));
            }
        }
        if(errno>0){
            String hyperlink = REDIRECTION_DISPLAY+getStringFromFloat(errno);
            res+="<br><br>";
            String ss = hyperlink;
            String start = "http://";
            if(ss.startsWith(start))ss = ss.substring(start.length());
            res+="<a href="+hyperlink+">"+ss+"</a>";
        }
        res+="<br><br>";
        
        // html content
        JEditorPane ep = new JEditorPane("text/html", "<html><body style=\"" + style + "\">"
            + res
            + "</body></html>");

       ep.addHyperlinkListener(new HyperlinkListener()
       {
           public void hyperlinkUpdate(HyperlinkEvent e)
           {
               if (e.getEventType().equals(HyperlinkEvent.EventType.ACTIVATED))
                   u2_base.launchWebSite(e.getDescription());
           }
       });
       ep.setEditable(false);
       ep.setBackground(label.getBackground());
             
       String vals[];
       if(errortype!=ERRORTYPE_CANCELEXIT) vals = new String[]{ok};
       else {
           if(shark.macOS)vals = new String[]{cancel,retry};  
           else vals = new String[]{retry,cancel};    
       }
       Object[] params = new Object[]{ep};
       JOptionPane get = new JOptionPane(params, JOptionPane.WARNING_MESSAGE, 0, null, vals, vals[0]);
       JDialog dialog;
       if(owner==null) dialog = get.createDialog(null, shark.programName+ " " + shark.versionNo); 
       else dialog = get.createDialog(owner, shark.programName+ " " + shark.versionNo); 
       JButton jb;
       if(shark.macOS && (jb=get.getRootPane().getDefaultButton())!=null)
            jb.requestFocus();
       errorMessageShowing = true;
       dialog.setVisible(true);
       Object result = get.getValue();
       errorMessageShowing = false;
       dialog.dispose(); 
       if((errortype==ERRORTYPE_CANCELEXIT && (result==null || result.equals(cancel))) || errortype==ERRORTYPE_EXIT){
           try{
              sharkStartFrame.mainFrame.finalize();
           }
           catch(Exception ee){
              System.exit(100);
           }
       }
    }
    
    public static void doPatch(Window owner, int type){
       boolean wantlink = shark.patching && (type==PATCH_CRITICAL || type==PATCH_REMINDER || type==PATCH_USER_NOCONNECT || type==PATCH_USER_CRITICALBLOCK || (type==PATCH_USER_UPDATE && shark.network));
       boolean wantcheckbox = type==PATCH_CRITICAL || type==PATCH_REMINDER;
       boolean wantcancel = false;//type==PATCH_CRITICAL || type==PATCH_REMINDER;
       final boolean hyperlinkAttemptClose = !(type==PATCH_USER_UPDATE && shark.network);
       String netside = "";
       if(shark.licenceType.equals(shark.LICENCETYPE_NETWORKACTIVATION)){
           netside = shark.network_serverside?u.gettext("patchmess","server"):u.gettext("patchmess","client");
       }
       String hyperlink = u.gettext("patchmess", "http")+
               shark.getProgramShortName()+ netside + (shark.network_RM?"RM":"")+(shark.macOS?u.gettext("patchmess","webfolder_mac"):"");       
//       hyperlink = hyperlink.replaceFirst("%", shark.getProgramShortName()+ netside + (shark.network_RM?"RM":""));
       if(type==PATCH_USER_UPDATE && !shark.network){
           boolean shutdown = u.yesnomess(u.gettext("patchmess","closedowntitle", shark.programName), u.gettext("patchmess","closedown").replaceAll("%", shark.programName),
                   sharkStartFrame.mainFrame);
           u2_base.launchWebSite(hyperlink);
           if(shutdown)sharkStartFrame.mainFrame.finalize();
           return;
       }
       String s1 = shark.network?"net":"sin";
       String s2 = "";
       if(type==PATCH_CRITICAL )s2 = "critical";
       else if(type==PATCH_REMINDER )s2 = "timed";
       else if(type==PATCH_USER_NOCONNECT )s2 = "noconnect";
       else if(type==PATCH_USER_NOUPDATES )s2 = "noupdate";
       else if(type==PATCH_USER_UPDATE )s2 = "userupdate";
       else if(type==PATCH_USER_CRITICALBLOCK )s2 = "criticalblock";

       
       String message = u.gettext("patchmess_special",s1+s2);
       message = u.replaceEvery(message, "|", "<br>");
       message = message.replaceAll("%", shark.programName + " " + shark.versionNo);
       message = u.replaceEvery(message, "^", shark.versionNoDetailed);
       
       message = message.replaceFirst("~", u.gettext("helpmenu", "signon"));
       message = message.replaceFirst("~", u.gettext("mupdatescheck", "label"));
       
       
//       String fontSize = "14";
       String fontSize = String.valueOf(sharkStartFrame.treefont==null?DEFAULTFONTSIZE:sharkStartFrame.treefont.getSize());
       JLabel label = new JLabel();
       Font font = label.getFont();

       // create some css from the label's font
//        StringBuffer style = new StringBuffer("font-family:" + font.getFamily() + ";");
//        style.append("font-weight:" + (font.isBold() ? "bold" : "normal") + ";");
//        style.append("font-size:" + font.getSize() + "pt;");
        StringBuffer style = new StringBuffer("font-family:" + font.getFamily() + ";");
        style.append("font-weight:" + "normal" + ";");
        style.append("font-size:" + fontSize + "pt;");
        
        if(wantlink){
            String ss = hyperlink;
            String start = "http://";
            if(ss.startsWith(start))ss = ss.substring(start.length());
            ss="<a href="+hyperlink+">"+ss+"</a>";
            message = message.replaceFirst("##", ss);
        }
        
        JCheckBox checkbox = null;
        if(wantcheckbox){
            checkbox = new JCheckBox(u.gettext("dontshow", "label"));
            checkbox.setFont(checkbox.getFont().deriveFont(Font.PLAIN));
            checkbox.setSelected(false);
        }
        
        message+="<br><br><br>";
        // html content
        JEditorPane ep = new JEditorPane("text/html", "<html><body style=\"" + style + "\">"
            + message
            + "</body></html>");

       ep.addHyperlinkListener(new HyperlinkListener()
       {
           public void hyperlinkUpdate(HyperlinkEvent e)
           {
               if (e.getEventType().equals(HyperlinkEvent.EventType.ACTIVATED)){
                  JDialog jd =  ((JDialog)((JEditorPane)e.getSource()).getTopLevelAncestor());
                  jd.setVisible(false);
                  boolean shutdown = false;
                  if(hyperlinkAttemptClose){
                      shutdown = u.yesnomess(u.gettext("patchmess","closedowntitle", shark.programName),  u.gettext("patchmess","closedown").replaceAll("%", shark.programName),
                        sharkStartFrame.mainFrame);
                   }
                  u2_base.launchWebSite(e.getDescription());
                  jd.dispose();
                  if(shutdown)sharkStartFrame.mainFrame.finalize();
               }
           }
       });
       ep.setEditable(false);
       ep.setBackground(label.getBackground());
             
       String[] vals;
       if(!wantcancel){
           String str;
           if(type==PATCH_CRITICAL || type==PATCH_REMINDER)vals = new String[]{u.gettext("close", "label")};
           else vals = new String[]{u.gettext("OK", "label")};
       }
       else{
           if(!shark.macOS) vals = new String[]{u.gettext("OK", "label"),u.gettext("cancel", "label")};
           else vals = new String[]{u.gettext("cancel", "label"),u.gettext("OK", "label")};
       }
       Object[] params;
       if(wantcheckbox) params= new Object[]{ep, checkbox};
       else params= new Object[]{ep};
       int messtype = JOptionPane.INFORMATION_MESSAGE;
       if(type==PATCH_CRITICAL)messtype = JOptionPane.WARNING_MESSAGE;
       
       JOptionPane get = new JOptionPane(params, messtype, 0, null, vals, vals[0]);
       JDialog dialog;
       if(owner==null) dialog = get.createDialog(null, shark.programName); 
       else dialog = get.createDialog(owner, shark.programName); 
       JButton jb;
       if(shark.macOS && (jb=get.getRootPane().getDefaultButton())!=null)
            jb.requestFocus();
       dialog.setVisible(true);
       if(type==PATCH_USER_CRITICALBLOCK){
           try{
              sharkStartFrame.mainFrame.finalize();
           }
           catch(Exception ee){
              System.exit(100);
           }
           return;
       }
       if(wantcheckbox && checkbox.isSelected()){
           if(type==PATCH_REMINDER) {
               db.update(sharkStartFrame.optionsdb,"reminderpatchnoask",new String[] {""}, db.TEXT);
               db.update(sharkStartFrame.optionsdb, "lastpatchreminderdate", 
                     new String[] {String.valueOf(System.currentTimeMillis())}, db.TEXT);
           }
           if(type==PATCH_CRITICAL) db.update(sharkStartFrame.optionsdb,"critcalpatchnoask",new String[] {""}, db.TEXT);
       }
       dialog.dispose();            

    }
    
    static String getStringFromFloat(float f){
        String s = String.valueOf(f);
        String strend = ".0";
        if(s.endsWith(strend))s=s.substring(0, s.length() - strend.length());
        return s;
    }    
    
    
}
