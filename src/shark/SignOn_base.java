/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package shark;

import java.awt.event.*;
import java.io.*;

import java.awt.*;

/**
 *
 * @author White Space
 */
public class SignOn_base{

  final int SCREEN_NONE = -1;
  final int SCREEN_TEMP = 0;
  final int SCREEN_NOADMIN = 1;
  static int sidebarw = mover.WIDTH*3/10;
  public int sideno = 8;
  public String sidenames[] = new String[sideno];
  public boolean wantside;
  public int hsidemargin = mover.HEIGHT/9;
  public int hsideh = mover.HEIGHT-(hsidemargin*2);
  public float alpha = 0.15f;
  float buttonalpha = 0.82f;
  
  public Point sideposyodd[]= new Point[sideno-1];
  public Point sideposyeven[]= new Point[sideno];
  public Point sidepoints[];
  namesq movers[];
  int movetime = 300;
  long endmove;
  int individualh = (hsideh*8/10) / sideno;
  int roundcorners = 15;
  namesq mainsi;
  backgroundsq sback;
  String names[];
  String allnames[];
  boolean wantupbut = false;
  boolean wantdownbut = false;
  boolean movingup;
  String lastnames[];
  boolean moving = false;
  movesq up;
  movesq down;
  mover.simpletextmover stmentername;
  mover.rectTransparentMover sidebar;
  mover.simpletextmover stmenterpass;
  mover.simpletextmover stmenterpassconfim;
  mover.simpletextmover stmenterpasshint;
  mover.simpletextmover stMaxUserWarn;
  mover.formattedtextmover stmtemp;
  mover.formattedtextmover stmmessage;
 
  mover.formattedtextmover lbHideHint;   
  mover.formattedtextmover lbShowHint;  
  mover.formattedtextmover lbForgottenPassword; 
  backgroundsq backsq;
  backgroundsq messsq;
//  sharedplay sharedp;
  butsq canbut;
  butsq okbut;
  butsq flashcancelbut;
  butsq flashokbut;
  butsq flashyesbut;
  butsq flashnobut;
  String strcancel = u.gettext("cancel", "label");
  String strok = u.gettext("ok", "label");
  String strno = u.gettext("no", "label");
  String stryes = u.gettext("yes", "label");
  Font butFont = null;
  Font labelFont = null;
  boolean gotname;
  boolean passwordhint;
  int whichscreen = SCREEN_NONE;
  boolean newadmin = false;
  String passwordhintstr = u.gettext("so", "passwordhintlabel")+" ";
  String passwordstr = u.gettext("so", "passwordlabel")+" ";
  
  String passwordconfirmstr = u.gettext("so", "confirmpassword")+" ";
  String namestr =u.gettext("so", "namelabel")+" ";
  int gaph = -1;
  int gapw = -1;
  int signsqx = -1;
  int signsqy = -1;
  Color bluecolordarker = new Color(0, 0, 160).darker();
  Color bluecolor = new Color(0, 0, 160);
  student.sharkImageSay inithelp2;
  static int maxchar = 30;
  static int maxcharpasshint = 45;
  mover.simpletextmover netWarnMess;
  namesq passconfirm;
  String newAdminPassword;
  String onlyOneAdmin = null;
  public Rectangle mainTBRect = null;
  
  void setbutfont(){
       int textw;
       int w = sidebarw*5/20;
       Font f = sharkStartFrame.treefont.deriveFont((float)40);
       textw = w*5/8;
       FontMetrics fm = student.fishpanel.getFontMetrics(f);
       while(fm.stringWidth(strcancel)> textw* student.fishpanel.screenwidth/mover.WIDTH   ){
            f = f.deriveFont((float)(f.getSize()-1));
            fm = student.fishpanel.getFontMetrics(f);
       }
      butFont = f;
  }

  public void setup(boolean ori, boolean dopass, boolean dopassconfirm, boolean side){
      setup(ori, dopass, dopassconfirm, null, wantside);
  }

  public void setup(boolean ori, boolean dopass, boolean dopassconfirm, String name, boolean side){
      if(student.fishpanel==null)return;
        names = db.dblistnames(sharkStartFrame.sharedPath);
        
       
        
        
        
        for(int i =0; sharkStartFrame.studentList!=null && i < sharkStartFrame.studentList.length; i++){
            if(u.findString(names, sharkStartFrame.studentList[i].name)>=0)
                names = u.removeString2(names,sharkStartFrame.studentList[i].name );
        }
        if((db.query(sharkStartFrame.optionsdb, "signondisplaynoadmins", db.TEXT) >= 0)){
            for(int i =0; student.adminlist!=null && i < student.adminlist.length; i++){
                if(u.findString(names, student.adminlist[i])>=0)
                    names = u.removeString2(names,student.adminlist[i] );
            }        
            for(int i =0; student.teacherlist!=null && i < student.teacherlist.length; i++){
                if(u.findString(names, student.teacherlist[i])>=0)
                    names = u.removeString2(names,student.teacherlist[i] );
            }      
        }
        

        
        
      wantside = side;
      if(ori){
          setbutfont();
      }
      else{
        mainsi.clear();
        if(stmenterpass!=null)
            student.fishpanel.removeMover(stmenterpass);
        
        if(stmenterpassconfim!=null){
            student.fishpanel.removeMover(stmenterpassconfim);
        }

        if(passconfirm!=null){
            student.fishpanel.removeMover(passconfirm);
        }        
         if(stMaxUserWarn!=null)
            student.fishpanel.removeMover(stMaxUserWarn);       
        if(stmenterpasshint!=null)
            student.fishpanel.removeMover(stmenterpasshint);
        if(lbForgottenPassword!=null && student.fishpanel.isMover(lbForgottenPassword))
            student.fishpanel.removeMover(lbForgottenPassword);
        setHint(false);
      }
      
      if(mainsi==null) mainsi = new namesq("", true, null, false, 1.0f, 0);
       if(signsqx<0){
        signsqx = mover.WIDTH / 2 - mainsi.w / 2;
        signsqy = (mover.HEIGHT / 2 - (mainsi.h / 2));
      }     
      
      java.util.ArrayList al = u.restrictedUserCount(sharkStartFrame.sharedPath);
      if(al!=null){
        int adminNo = (int)al.get(0);
        int stuNo = (int)al.get(1);
        if(adminNo > shark.maxUsers_Admins || stuNo > shark.maxUsers_Students){
            onlyOneAdmin = (String)al.get(2);
            addMaxUserWarning(shark.maxUsers_Admins, shark.maxUsers_Students, onlyOneAdmin);
        }
      }          

       if(onlyOneAdmin!=null){
           for(int i = names.length-1; i >=0 ; i--){
               if(!names[i].equalsIgnoreCase(onlyOneAdmin))
                names = u.removeString2(names,names[i] );
           }
       } 
      allnames = names;
 
      
        
   
      
      
      
      

      if(stmentername==null)
          stmentername = new mover.simpletextmover(
                  namestr,
                  mover.WIDTH * 1 / 10,
                  mover.HEIGHT / 8,
                  Color.white);

//      if(sharedp==null && sharkStartFrame.studentList !=null &&
//              sharkStartFrame.studentList.length >= 1){
//            sharedp = new sharedplay(0.5f);
//      }
      

      int confirmaddy = dopassconfirm?  mainsi.h + gaph  :0;
      if(dopassconfirm){

            stmenterpassconfim = new mover.simpletextmover(
                passwordconfirmstr,
                mover.WIDTH * 2 / 15,
                mover.HEIGHT / 8 + confirmaddy,
                Color.white, stmentername.f, student.fishpanel);
            
            
   
            student.fishpanel.addMover(stmenterpassconfim,
                signsqx - (stmenterpassconfim.w),
                (mover.HEIGHT / 2 - (stmenterpassconfim.h / 2)) + confirmaddy);
            
 
               
            
      }
      
      if(student.wrongAdmin!=null)wantside = false;
      if(mainsi!=null && (
              !student.fishpanel.isMover(mainsi) || student.wrongAdmin!=null)  ){
          student.fishpanel.addMover(mainsi,
              signsqx,
              signsqy);
      }
      if(netWarnMess!=null && student.fishpanel.isMover(netWarnMess)){
          student.fishpanel.removeMover(netWarnMess);
      }

      if(canbut==null) canbut = new butsq(strcancel, buttonalpha);
      if(okbut==null) okbut = new butsq(strok, buttonalpha);
      if(flashcancelbut==null) flashcancelbut  = new butsq(strcancel, buttonalpha);
      if(flashokbut==null) flashokbut  = new butsq(strok, buttonalpha);
      if(flashyesbut==null) flashyesbut  = new butsq(stryes, buttonalpha);
      if(flashnobut==null) flashnobut  = new butsq(strno, buttonalpha);
      if(backsq==null) backsq = new backgroundsq(mover.WIDTH / 2, mover.HEIGHT / 2, Color.white, 1.0f);
      
      if(gaph<0){
        gaph = (mainsi.h * 1 / 5);
        gapw = (((mainsi.h * 1 / 5)*student.fishpanel.screenheight/student.fishpanel.screenwidth));
      }
      if(up==null) up = new movesq(true, buttonalpha);
      if(down==null) down = new movesq(false, buttonalpha);

      
      if(shark.macOS){
          if (!student.fishpanel.isMover(canbut)) {
                  student.fishpanel.addMover(canbut,
                          signsqx  + mainsi.w - (canbut.w*2)-gapw,
                          signsqy + mainsi.h + gaph + confirmaddy);
          }
          if (!student.fishpanel.isMover(okbut)) {
                  student.fishpanel.addMover(okbut,
                          signsqx + mainsi.w - canbut.w,
                          signsqy + mainsi.h + gaph + confirmaddy);
          }
      }
      else{
              if (okbut!=null && !student.fishpanel.isMover(okbut)) {
                  student.fishpanel.addMover(okbut,
                          signsqx  + mainsi.w - (canbut.w*2)-gapw,
                          signsqy + mainsi.h + gaph + confirmaddy);
              }
              if (!student.fishpanel.isMover(canbut)) {
                  student.fishpanel.addMover(canbut,
                          signsqx + mainsi.w - canbut.w,
                          signsqy + mainsi.h + gaph + confirmaddy);
              }
      }
      if(name!=null){
           gotname = false;
          if(mainsi.nameentered(name)<0){
              gotname = false;
              setup(true, false, false, null, wantside);
              return;
          }
      } 
      
      if(dopassconfirm){

          passconfirm = new namesq("", true, mainsi, false, 1.0f, 0);
          student.fishpanel.addMover(passconfirm,
                    signsqx,
                    signsqy + mainsi.h + gaph);    
          passconfirm.input2 = null;
          mainsi.partnersq = passconfirm;
          mainsi.activepartner = true;
          mainsi.input2 = mainsi;
   
         
                  
     
 //        if(stmentername==null)
 //         stmentername = new mover.simpletextmover(
 //                 namestr,
 //                 mover.WIDTH * 1 / 10,
 //                 mover.HEIGHT / 8,
 //                 Color.white);
 //           if(student.wrongAdmin!=null)wantside = false;
 //           if(mainsi!=null && (
    //                !student.fishpanel.isMover(mainsi) || student.wrongAdmin!=null)  ){
          
  //          }
      }
      mainTBRect = new Rectangle(mainsi.x, mainsi.y, mainsi.w, mainsi.h);
                
      if(shark.licenceType.equals(shark.LICENCETYPE_NETWORKACTIVATION) &&
              WebAuthenticateNetwork_base.overrideState == WebAuthenticateNetwork_base.OVERRIDESTATE_ALERT_RED){
                showNetworkWarning();
      }
//      if(sharedp!=null){
//          student.fishpanel.addMover(sharedp,
//                      gapw,
//                      gaph);
//      }
//      else{
//          if (shark.macOS) {
//              inithelp2 = new student.sharkImageSay("help_initial2_mac", false);
//          } else {
//              inithelp2 = new student.sharkImageSay("help_initial2", false);
//          }
//          inithelp2.cansay = true;
 //         inithelp2.w = mover.WIDTH / 4;
 //         inithelp2.h = mover.HEIGHT / 6;
 //         inithelp2.adjustSize(student.fishpanel.screenwidth, student.fishpanel.screenheight);
 //         student.fishpanel.addMover(inithelp2, 0, 0);
//      }

      if(!dopass){
          if(!student.fishpanel.isMover(stmentername)){
            student.fishpanel.addMover(stmentername,
                signsqx - stmentername.w,
                (mover.HEIGHT / 2 - (stmentername.h / 2)));
          }
      }
      else{
           addPassword(student.newstu.name, dopassconfirm);
      }
      if (names.length > 0 && wantside) {
          if(!student.fishpanel.isMover(sidebar) && !dopass){
            Rectangle rect = new Rectangle(0, 0, sidebarw, mover.HEIGHT);
            student.fishpanel.addMover(sidebar = new mover.rectTransparentMover(rect,
                  new Color(0,0,50), alpha),
                  mover.WIDTH - sidebarw, 0);
          }
          int xx = mover.WIDTH - (sidebarw / 2) -
                  mainsi.noneditablew / 2;

          // rect from top of top button to TOP of bottom button
          int u = (((sideno)*(hsideh / sideno))-individualh);
          int nno;
          if(sideno%2==0)nno = sideno;
          else nno = sideno-1;

          int gap = (hsideh-(sideno*individualh))/(sideno-1);
          int hh = (individualh*(sideno-1))+((sideno-2)*gap);
          int hh2 = mover.HEIGHT/2-((hh)/2);
            
          for (int i = 0; i < sideno; i++) {
            sideposyeven[i] = new Point(xx, hsidemargin + (i* (u / (nno-1))));
            if(i < (sideno-1)){
                sideposyodd[i] = new Point(xx, hh2);
                hh2+=individualh+gap;
            }
          }
          if(!student.fishpanel.isMover(down)){
            student.fishpanel.addMover(down,
              mover.WIDTH - (sidebarw / 2) - down.w / 2,
              (((hsidemargin/2)-(down.h/2))) );
          }
          if(!student.fishpanel.isMover(up)){
            student.fishpanel.addMover(up,
              mover.WIDTH - (sidebarw / 2) - up.w / 2,
              mover.HEIGHT - hsidemargin+ (((hsidemargin/2)-(up.h/2))) );
          }
       lastnames = null;
       if(name==null){
         arrange(names);
       }
      }
//      sback.moveto(stmentername.x - gapw*2, mainsi.y - gaph*2, 0);
//      sback.w = ((mainsi.x+mainsi.w)-stmentername.x)+gapw*4;
//      sback.h =((okbut.y+okbut.h)-mainsi.y)+gaph*4;
      sharkStartFrame.mainFrame.setTitle(sharkStartFrame.starttitle);
//      sharkStartFrame.mainFrame.requestFocus();
      sharkStartFrame.mainFrame.requestFocusInWindow();

  }
 

    public void arrange(String[] names) {
        if(lastnames!=null &&  java.util.Arrays.equals(lastnames, names))
            return;
        lastnames = names;
        up.show = names.length>sideno;
        down.show = names.length>sideno;
        removeSideMovers();
        int thissideno;
        if (names.length >= sideno || names.length % 2 == 0) {
            sidepoints = sideposyeven;
            thissideno = sideno;
        } else {
            sidepoints = sideposyodd;
            thissideno = sideno-1;
        }
        wantupbut = false;
        wantdownbut = names.length>sideno;

        movers = new namesq[sidepoints.length];
        int extra = 0;
        int len = names.length;
        if(len < thissideno)
            extra = (thissideno - len)/2;

        SignOn_base.namesq sq;
        int max = Math.min(extra+len, thissideno);
        int index = 0;

        for (int i = extra; i < max; i++) {
            String n = names[index++];
            sq = new namesq(n, false, null, false, buttonalpha, 1);
            movers[i] = sq;
            if(i<sideno){
                student.fishpanel.addMover(sq,
                    sidepoints[i].x, sidepoints[i].y);
            }
        }
    }

    public void getNewNames() {
        if(!wantside || gotname)return;
        String s = mainsi.text.trim();
        String ss[] = new String[]{};
        if(!s.equals("")){
            for(int i = 0; i < names.length; i++){
                if(names[i].toLowerCase().startsWith(s.toLowerCase()))
                    ss = u.addString(ss, names[i]);
            }
        }
        else ss = names;
        allnames = ss;
        arrange(ss);
    }

    public int findNextName(String s, boolean up) {
        int i = u.findString(allnames, s);
        int k;
        if(up)k = 1;
        else k = -1;
        i+=k;
        if(i>allnames.length-1 || i <0)return -1;
        return i;
    }

    public void startSideMove(long time, boolean up){
        for (int i = 0; i < movers.length; i++) {
            namesq m = (namesq) movers[i];
            if (m != null) {
                movingup = up;
                m.move(up);
                moving = true;
            }
        }
        endmove = time + movetime;
    }

    public void removeSideMovers(){
        for (int i = 0; movers!=null && i < movers.length; i++) {
            if(movers[i]!=null)
                student.fishpanel.removeMover(movers[i]);
        }
    }

    void showNetworkWarning(){
        if(netWarnMess==null || !student.fishpanel.isMover(netWarnMess)){
            String mess = u.gettext("webauthnet", "overrideendwarning");
            int w = mover.WIDTH / 4;
            int h = mover.HEIGHT / 5;
            String hyperlink = u.gettext("webauthnet", "hyperlink");
            String hyperlink_display = u.gettext("webauthnet", "hyperlink_display");
            
            netWarnMess = new mover.simpletextmover(
                     mess,
                      w,
                      h,
                      Color.white);
              
            Font f = sharkGame.sizeFont(u.splitString(mess)  , w*student.fishpanel.screenwidth/mover.WIDTH, 
                     h*student.fishpanel.screenheight/mover.HEIGHT);
            
            
            FontMetrics fm = student.fishpanel.getGraphics().getFontMetrics(f);
            mover netWarnMess2  = new mover.textMover(
                  hyperlink_display,
                      w,
                      fm.getAscent()*mover.HEIGHT/student.fishpanel.screenheight,
                         Color.white,
                         null,
                         f,hyperlink);
             
             
             student.fishpanel.addMover(netWarnMess,
                      mover.WIDTH/20,
                      mover.HEIGHT/20);  
             student.fishpanel.addMover(netWarnMess2,
                      mover.WIDTH/20,
                      mover.HEIGHT/20+netWarnMess.h+(fm.getLeading()*mover.HEIGHT/student.fishpanel.screenheight)); 
        }
    }
    
    
     void changescreen(int i, boolean on){
         whichscreen = i;
         String text = mainsi.text;
         if(text!=null)text = text.trim();
         if(i==SCREEN_TEMP){
             if(on){
                student.fishpanel.removeMover(stmentername);
                student.fishpanel.removeMover(canbut);
                student.fishpanel.removeMover(okbut);
                student.fishpanel.removeMover(mainsi);
                student.fishpanel.removeMover(sidebar);
                student.fishpanel.removeMover(up);
                student.fishpanel.removeMover(down);
                for(int ii = 0 ; movers!=null && ii < movers.length; ii++){
                    if(movers[ii]!=null)student.fishpanel.removeMover(movers[ii]);
                }
                keypad.deactivate(sharkStartFrame.mainFrame);

                int textmargin = mover.WIDTH/30;
                stmtemp = new mover.formattedtextmover(
                  u.gettext("so","newtemp",text),
                  Color.black, sharkStartFrame.treefont, student.fishpanel, false, Font.BOLD);
                backsq.w = stmtemp.w+(textmargin*2);
                backsq.h = stmtemp.h+(textmargin*2);
                student.fishpanel.addMover(backsq,
                        (mover.WIDTH/2)-(backsq.w/2),
                        (mover.HEIGHT/2)-(backsq.h/2));
                student.fishpanel.addMover(stmtemp,
                        (mover.WIDTH/2)-(backsq.w/2)+textmargin,
                        (mover.HEIGHT/2)-(backsq.h/2)+textmargin);
              
                butsq leftm;
                butsq rightm;
                if(!shark.macOS){
                    leftm = flashokbut;
                    rightm = flashcancelbut;
                }
                else{
                    leftm = flashcancelbut;
                    rightm = flashokbut;
                }
                student.fishpanel.addMover(rightm,
                        backsq.x + backsq.w - (backsq.w*1/3)-(rightm.w/2),
                        backsq.y + backsq.h + (rightm.h/2));
                student.fishpanel.addMover(leftm,
                        backsq.x + (backsq.w*1/3)-(rightm.w/2),
                        backsq.y + backsq.h + (leftm.h/2));   
             }
             else{
                 whichscreen = SCREEN_NONE;
                 gotname = false;
                student.fishpanel.removeMover(backsq);
                student.fishpanel.removeMover(stmtemp);
                student.fishpanel.removeMover(flashokbut);
                student.fishpanel.removeMover(flashcancelbut);
                setup(false,false, false, wantside);
//                if((u.gettext("keypad_","signon")) != null && student.isAdministrator)
                if((u.gettext("keypad_","signon")) != null)
                    keypad.activate(sharkStartFrame.mainFrame,new char[] {' ',(char)keypad.BACKSPACE,(char)keypad.ENTER}, keypad.BOTTOMLEFT);
             }
         }
         else if(i==SCREEN_NOADMIN){
             if(on){
                student.fishpanel.removeMover(stmentername);
                student.fishpanel.removeMover(canbut);
                student.fishpanel.removeMover(okbut);
                student.fishpanel.removeMover(mainsi);
                student.fishpanel.removeMover(sidebar);
                student.fishpanel.removeMover(up);
                student.fishpanel.removeMover(down);

                int textmargin = mover.WIDTH/30;
                stmtemp = new mover.formattedtextmover(
                  u.edit(u.gettext("so", "admin"), text, shark.programName),
                  Color.black, sharkStartFrame.treefont, student.fishpanel, false, Font.BOLD);

                backsq.w = stmtemp.w+(textmargin*2);
                backsq.h = stmtemp.h+(textmargin*2);
                student.fishpanel.addMover(backsq,
                        (mover.WIDTH/2)-(backsq.w/2),
                        (mover.HEIGHT/2)-(backsq.h/2));

                student.fishpanel.addMover(stmtemp,
                        (mover.WIDTH/2)-(backsq.w/2)+textmargin,
                        (mover.HEIGHT/2)-(backsq.h/2)+textmargin);

                butsq leftm;
                butsq rightm;
                if(!shark.macOS){
                    leftm = flashyesbut;
                    rightm = flashnobut;
                }
                else{
                    leftm = flashnobut;
                    rightm = flashyesbut;
                }
                student.fishpanel.addMover(rightm,
                        backsq.x + backsq.w - (backsq.w*1/3)-(rightm.w/2),
                        backsq.y + backsq.h + (rightm.h/2));
                student.fishpanel.addMover(leftm,
                        backsq.x + (backsq.w*1/3)-(rightm.w/2),
                        backsq.y + backsq.h + (leftm.h/2));
             }
             else{
                whichscreen = SCREEN_NONE;
                student.fishpanel.removeMover(backsq);
                student.fishpanel.removeMover(stmtemp);
                student.fishpanel.removeMover(flashyesbut);
                student.fishpanel.removeMover(flashnobut);
                setup(false, true, true, wantside);
             }
         }
     }

     void end(){
        student.fishpanel=null;
        student.newstu.addtolist();
        student.newstu.finishSignon(true);
     }

     void addPassword(String name, boolean plusconfirm){
        if(stmenterpass==null){
            stmenterpass = new mover.simpletextmover(
                passwordstr,
                mover.WIDTH * 2 / 15,
                mover.HEIGHT / 8,
                Color.white);
        }
        if(!student.fishpanel.isMover(stmenterpass)){
            student.fishpanel.addMover(stmenterpass,
                signsqx - stmenterpass.w,
                (mover.HEIGHT / 2 - (stmenterpass.h / 2)));
          }
        addPasswordHintOptions(name);
        if(shark.licenceType.equals(shark.LICENCETYPE_NETWORKACTIVATION) && student.newstu.administrator &&
                WebAuthenticateNetwork_base.overrideState == WebAuthenticateNetwork_base.OVERRIDESTATE_ALERT_ORANGE){
                     showNetworkWarning();
        }
     }
   
     
     
     void addMaxUserWarning(int maxAdmins, int maxStus, String adminname){
        showerror2(u.edit(u.gettext("maxusers", "signonwarn"), new String[]{adminname, String.valueOf(maxAdmins), String.valueOf(maxStus)}));
     }     
          
     
     void addPasswordHint(){
        if(stmenterpasshint==null){
            
            stmenterpasshint = new mover.simpletextmover(
                passwordhintstr,
                mover.WIDTH * 2 / 15,
                mover.HEIGHT / 8,
                Color.white);
        }
        if(!student.fishpanel.isMover(stmenterpasshint)){
            student.fishpanel.addMover(stmenterpasshint,
                signsqx - stmenterpasshint.w,
                (mover.HEIGHT / 2 - (stmenterpass.h / 2)));
          }
     }     
     
     void addPasswordHintOptions(String name){
        int inti =signsqy+mainsi.h+okbut.h+(gaph*2);
        if(student.newstu !=null || student.newstu.passwordhint != null){
            if(lbShowHint==null){    
                lbShowHint = new hyperlink(
                    u.gettext("so", "showhint"),
                        Color.lightGray,
                        sharkStartFrame.treefont.deriveFont((float)sharkStartFrame.treefont.getSize()-2),
                        student.fishpanel,
                        false,
                        Font.BOLD, true);
            }  
            if(lbHideHint==null){    
                lbHideHint = new hyperlink(
                    u.gettext("so", "hidehint"),
                        Color.lightGray,
                        sharkStartFrame.treefont.deriveFont((float)sharkStartFrame.treefont.getSize()-2),
                        student.fishpanel,
                        false,
                        Font.BOLD, true);
            }
        }
        else{
            lbShowHint = lbHideHint = null;
        }
        setHint(false);
        if(!newadmin){
            if(lbForgottenPassword==null){    
                lbForgottenPassword = new hyperlink(
                    u.gettext("so", "forgottenpassword"),
                        Color.lightGray,
                        sharkStartFrame.treefont.deriveFont((float)sharkStartFrame.treefont.getSize()-2),
                        student.fishpanel,
                        false,
                        Font.BOLD, true);
            }
            if(!student.fishpanel.isMover(lbForgottenPassword)){
                student.fishpanel.addMover(lbForgottenPassword,
                        signsqx  + mainsi.w - lbForgottenPassword.w,
                        inti+(gaph*2));
            }        
        }
     }     
     

     void setHint(boolean showhint){
         if(student.fishpanel==null)return;
         if(student.newstu ==null || student.newstu.passwordhint == null){
            if(student.fishpanel.isMover(lbShowHint))
                student.fishpanel.removeMover(lbShowHint); 
            if(student.fishpanel.isMover(lbHideHint))
                student.fishpanel.removeMover(lbHideHint);
            return;
         }
             
        int inti =signsqy+mainsi.h+okbut.h+(gaph*2);
        if(showhint){
            if(student.fishpanel.isMover(lbShowHint))
                student.fishpanel.removeMover(lbShowHint);
            if(lbHideHint!=null && !student.fishpanel.isMover(lbHideHint))
                student.fishpanel.addMover(lbHideHint,
                          signsqx  + mainsi.w - lbHideHint.w,
                        inti); 
            
            String passwordhint1 = student.newstu.passwordhint;
            passwordhint1 = passwordhint1.replaceAll("|", "");
            // halve the text if long
            if(passwordhint1.length()>30){
                int half = passwordhint1.length()/2;
                String ss[] = passwordhint1.split(" ");
                for(int i = ss.length-1; i >= 0; i--){
                    ss[i] = ss[i].trim();
                    if(ss[i].length()==0)ss = u.removeString(ss, i);
                }
                int count = 0;
                String ret = "";
                for(int i = 0; i < ss.length; i++){
                    count+=ss[i].length();
                    ret+=ss[i] + " ";
                    if(count>half && ret.indexOf("|")<0){
                        ret+="|";
                    }
                }  
                passwordhint1 = ret.trim();
            }
            showerror(passwordhint1, true);              
        }
        else{
            if(student.fishpanel.isMover(lbHideHint))
                student.fishpanel.removeMover(lbHideHint);
            if(lbShowHint!=null && !student.fishpanel.isMover(lbShowHint))
                student.fishpanel.addMover(lbShowHint,
                    signsqx  + mainsi.w - lbShowHint.w,
                    inti);         
        }
     }
     
  
     void showerror(String s){
        setHint(false);
        showerror2(s);
     }     
     
     void showerror(String s, boolean blockhint){
         if(!blockhint)
            setHint(false);
        showerror2(s);
     }
     
     void showerror2(String s){
        removeerror();     
    //    boolean addtext = false;
      //  if(!student.fishpanel.isMover(stmmessage)){
            stmmessage = new mover.formattedtextmover(s,
            Color.white, sharkStartFrame.treefont, student.fishpanel, true, Font.BOLD);
   //         addtext = true;
     //   }     
        int mainsqheight = mainsi.h;
        int b = mainsqheight/4;
        int textheight = stmmessage.h;
        int h = Math.max(mainsqheight, textheight+b);
        int inti =signsqy-h-(gaph);
        
        messsq = new backgroundsq(mainsi.w, h, bluecolor, 0.8f);
        if(!student.fishpanel.isMover(messsq)){
            student.fishpanel.addMover(messsq,
                signsqx,
                inti);
        }
     //   if(addtext){
            student.fishpanel.addMover(stmmessage,
                signsqx + (messsq.w-stmmessage.w)/2,
                inti+(messsq.h-stmmessage.h)/2);            
    //    }
     }     

     void removeerror(){
      if(student.fishpanel.isMover(stmmessage))
        student.fishpanel.removeMover(stmmessage);
      if(student.fishpanel.isMover(messsq))
        student.fishpanel.removeMover(messsq);
     }

  public class namesq extends namesquare_base {

     String underlyingpass = "";
     javax.swing.Timer t;
     int pointerflashtime = 400;
    
     namesq(String s, boolean edit, namesquare_base secondedit, boolean activepartner, float alpha, int type) {
        super(s, student.fishpanel, edit, secondedit, activepartner, alpha, type, roundcorners, (int)(sidebarw*8/10), individualh);
    
        t = (new javax.swing.Timer(pointerflashtime, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                timeron ^= 1;
            }
        }));
        t.setRepeats(true);
        t.start();
     }

     public void mouseClicked(int x, int y) {
//         if(mode==MODE_SHARERESET){
//             sharedp.reset();
//         }
//         else
//             if(mode==MODE_SHARECHOSEN){
//             return;
//         }
 //        else if(mode==MODE_SHARETOCHOOSE){
  //           sharedp.chosen(text);
 //        }
//         else{
            removeerror();
            if(!editable){
                nameentered(text);
            }
            
            if(partnersq!=null){
                input2 = (namesquare_base)this;
                ((namesquare_base)this).activepartner = true;
                ((namesquare_base)this).partnersq.activepartner = false;
                ((namesquare_base)this).partnersq.input2 = null;

            }
//         }
     };

    int findPos(namesq nsq) {
        for(int i = 0; i < movers.length; i++){
            if(movers[i].equals(nsq))return i;
        }
        return -1;
    }

     void clear() {
        underlyingpass = text = "";
        pointerx = 0;
     }

     void doenter(){
        if(!gotname)
          nameentered(mainsi.text.trim());
        else if(passwordhint)
          passwordhintentered();
        else{
          int k = passwordentered();
          if(k>=0){
                mainsi.input2 = mainsi;
                mainsi.partnersq = null;
          }
        }
     }

      void newadminok(String name) {
          student.newstu.administrator = true;
          student.isAdministrator = true;
          String dbpath = sharkStartFrame.sharedPath.toString();
          String dbname = dbpath + File.separatorChar + name;
          db.create(dbname);
          student.newstu.isnew = false;
          student.newstu.saveStudent();

          // change wrongAdmin from adminitrator to student and signoff
          if (student.wrongAdmin != null) {
              student.wrongAdmin.administrator = false;
              student.wrongAdmin.teacher = false;
              student.wrongAdmin.saveStudent();
              if (student.wrongAdmin.isLocked()) {
                  if(MacLock.DOJNI){
                      student.wrongAdmin.macLocked = false;
                      MacLock.delete(sharkStartFrame.sharedPathplus + student.wrongAdmin.name, ".lock");
                      MacLock.delete(sharkStartFrame.sharedPathplus + student.wrongAdmin.name, MacLock.LOCKEXTENSION);
                  } else {
                      try {
                          student.wrongAdmin.lock.release();
                          student.wrongAdmin.lock = null;
                          student.wrongAdmin.fflock.close();
                          student.wrongAdmin.fflock = null;
                          new File(sharkStartFrame.sharedPathplus + student.wrongAdmin.name + ".lock").delete();
                      } catch (IOException e1) {
                      }
                  }
              }
              student.checkadmin(student.wrongAdmin);
              student.wrongAdmin = null;
          }
          student.checkadmin();
      }

     public int passwordentered(){
         if(mainsi.underlyingpass.length()==0){noise.beep();return -1;}
         if(student.fishpanel.isMover(passconfirm)){
             if(!mainsi.underlyingpass.equalsIgnoreCase(passconfirm.underlyingpass)){
                 showerror2(u.gettext("so", "passwordmatchwarn"));
                 passconfirm.activepartner = true;
                 passconfirm.partnersq.activepartner = false;
                 
                 passconfirm.partnersq.input2 = null;
                 passconfirm.input2 = passconfirm;
                 noise.beep();
                 
                 
                 
                 return -1;
             }
             if(stmenterpassconfim!=null){
                student.fishpanel.removeMover(stmenterpassconfim);
             }
            student.fishpanel.removeMover(passconfirm);
         }
         if(newadmin){
            newAdminPassword = mainsi.underlyingpass;
            student.newstu.password = student.encrypt(mainsi.underlyingpass);
            newadminok(student.newstu.name);
    //        end();
            if(stmenterpass!=null)
                student.fishpanel.removeMover(stmenterpass);
            addPasswordHint();
            mainsi.clear();
            passwordhint = true;
            showerror(u.gettext("so", "passwordhintdesc", u.gettext("cancel", "label")));
            return 0;
         }
         if(student.newstu.okPassword(mainsi.underlyingpass)) {
             if(student.wrongAdmin != null){
                student.fishpanel.removeMover(stmentername);
                student.fishpanel.removeMover(sidebar);
                student.fishpanel.removeMover(up);
                student.fishpanel.removeMover(down);
                student.fishpanel.removeMover(stmmessage);
                student.fishpanel.removeMover(canbut);
                student.fishpanel.removeMover(okbut);
               // else the ok and cancel buttons don't appear
               u.pause(200);
                setup(false,false, false, wantside);
                gotname = false;
                newadmin = true;
               return 0;
             }
 //            else
 //              student.newstu.addtolist();
           }
           else  {
             showerror(u.gettext("so","wrong"));
             mainsi.clear();
             noise.beep();return 0;
           }
           end();
           return -1;
     }
     
     public void passwordhintentered(){
        if(mainsi.text.length()==0){noise.beep();return;}
        if(mainsi.text.equalsIgnoreCase(newAdminPassword)){
            showerror2(u.gettext("so", "hintwarn"));
            noise.beep();
            return;
        }
        student.newstu.passwordhint = mainsi.text;
        end();
     }


      public int nameentered(String name) {
          boolean istemp = false;
          if(!gotname || student.wrongAdmin!=null) {
            if(name.length()==0){noise.beep();return -1;}
            name = u.stripspaces2(name);
            if(onlyOneAdmin!=null && !name.equalsIgnoreCase(onlyOneAdmin)){
                noise.beep();return -1;
            }

            // is user corrupt
            student.newstu = student.findStudent(name);
            if(student.newstu == null && db.exists(name)) {
              student.newstu = new student();
              student.newstu.name = name;
              student.newstu.saveStudent();
              student.checkadmin(student.newstu);
              u.okmess(u.gettext("stucorrupt", "heading"),
                       u.gettext("stucorrupt", "message", name));
              return -1;
            }

         if(student.newstu != null ) {
           if(student.newstu.which == (shark.numbershark?'W':'N')) {
               //  rb 6/2/08 v4.2   // make sure totally new
               db.destroy(name);
               student.newstu = new student();
               student.newstu.name = name;
               student.newstu.saveStudent();
               student.checkadmin(student.newstu);
               u.okmess(u.gettext("stucorruptws", "heading"),
                      u.edit(u.gettext("stucorruptws", "message"), name, shark.otherProgram));
               return -1;
           }
             sharkStartFrame.newuser = false;
             student.gotstuname = null;
             if(student.wrongAdmin != null) {
                 showerror(u.gettext("so","wrongadminexists"));
                return -1;
             }
             if(student.newstu.isSignedOn()) {
                 showerror("'"+name+"'" + u.gettext("so","isalready"));
                return -1;
             }
             //start rb 22/1/06
             if(!student.getlock(student.newstu,student.newstu.name,false)) {
                 if(student.lockmess != null) {
                     showerror(student.lockmess);
                 }
                 else if(student.lockmess2) {
                   if(shark.network)
                     new student.userLimitWarning(sharkStartFrame.mainFrame);
                   else{
    //                 u.okmess(shark.programName, u.gettext("errorcodes", "errorcode12", shark.programName), sharkStartFrame.mainFrame);
                     OptionPane_base.getErrorMessageDialog(sharkStartFrame.mainFrame, 12, u.gettext("errorcodes", "errorcode12", shark.programName), OptionPane_base.ERRORTYPE_EXIT);
                   }
                 }
                 return -1;
             }
  //           student.newstu.doupdates(true,false);
             if((student.newstu.password != null && student.newstu.password.length() > 0)) {
                student.fishpanel.removeMover(stmentername);
                student.fishpanel.removeMover(sidebar);
                student.fishpanel.removeMover(up);
                student.fishpanel.removeMover(down);
                removeSideMovers();
                addPassword(name, false);
                gotname = true;
                mainsi.clear();
                return 0;
             }
             else {
 //                student.newstu.addtolist();
                 end();
                 return 0;
             }
          }
         else if (  student.wrongAdmin != null  ) {
                 student.newstu = new student();
               student.newstu.name = name;
                student.fishpanel.removeMover(stmentername);
                student.fishpanel.removeMover(sidebar);
                student.fishpanel.removeMover(up);
                student.fishpanel.removeMover(down);
                removeSideMovers();
                addPassword(name, false);
                gotname = true;
                mainsi.clear();
                return 0;
          }
          else {   // new (temp)student
            student.checkadmin();
            student.newstu = new student(name);
            sharkStartFrame.newuser = true;
              
            if(student.isAdministrator && student.wrongAdmin == null) {
                 if(student.gotstuname == null || !student.gotstuname.equals(name)) {
                    if(!shark.wantTemporaryStudents || db.query(sharkStartFrame.optionsdb,"mnotemp",db.TEXT )>=0) {
                        showerror("'"+name+"'" + u.gettext("so","notfound"));
                        return -1;
                    }
                    gotname = true;
                    istemp = true;
                    student.gotstuname = text;
                    changescreen(SCREEN_TEMP, true);
     //               return 0;
                 }
              }
              
              if(!student.getlock(student.newstu,student.newstu.name,false)) {
                  if(student.lockmess != null) {
                      showerror(student.lockmess);
                  }
                  else if(student.lockmess2) {
                    if(shark.network)
                      new student.userLimitWarning(sharkStartFrame.mainFrame);
                    else{
       //              u.okmess(shark.programName, u.gettext("errorcodes", "errorcode12", shark.programName), sharkStartFrame.mainFrame);
                     OptionPane_base.getErrorMessageDialog(sharkStartFrame.mainFrame, 12, u.gettext("errorcodes", "errorcode12", shark.programName), OptionPane_base.ERRORTYPE_EXIT);
                   }
                  }
              }
                 // offer to make administrator
               if (!student.isAdministrator || (student.wrongAdmin != null)) {
                  if(student.wrongAdmin == null){
                      gotname = true;
                      changescreen(SCREEN_NOADMIN, true);
                      return -1;
                }
               }
             }
          }
          if(!istemp)
              end();
          return 0;
     }

     public void move(boolean up) {
         int dpos;
         int pos = findPos(this);
         if(up)dpos = pos - 1;
         else dpos = pos + 1;
         if(dpos < 0){
             student.fishpanel.removeMover(this);
             return;
         }
         else if(dpos >= sideno){
             student.fishpanel.removeMover(this);
             return;
         }
         moveto(sidepoints[dpos].x, sidepoints[dpos].y, movetime);
     }

     public boolean keypressed(KeyEvent e) {
         javax.swing.JFrame jf = ((javax.swing.JFrame)e.getSource());
         boolean ftk = jf.getFocusTraversalKeysEnabled();
         
         jf.setFocusTraversalKeysEnabled(false);
         long l = System.currentTimeMillis();
         int code = (char) e.getKeyCode();
                
         if(code == KeyEvent.VK_TAB){
             if (this.partnersq!=null){
                 this.partnersq.activepartner = true;
                 this.activepartner = false;
                 this.input2 = null;
                 this.partnersq.input2 = this.partnersq;         
             }
             return false;
         }
         
 //        jf.setFocusTraversalKeysEnabled(ftk);
         
         if (code == KeyEvent.VK_LEFT && pointerx>0)pointerx--;
         else if (code == KeyEvent.VK_RIGHT){
             if(pointerx<text.length())
                pointerx++;
             else if(pointerx==text.length()){
                 int count = 0;
                 namesq singlemover = null;
                 for(int i = 0; i < movers.length; i++){
                     if(movers[i]!=null){
                         count++;
                         singlemover = movers[i];
                     }
                 }
                 if(count==1 && singlemover!=null){
                     mainsi.text = singlemover.text;
                     underlyingpass = mainsi.text;
                    pointerx = mainsi.text.length();
                }
             }
         }
         else if (code == KeyEvent.VK_DOWN && endmove<l && !moving && up.show && up.enabled)
             startSideMove(l, true);
         else if (code == KeyEvent.VK_UP && endmove<l && !moving && down.show && down.enabled)
             startSideMove(l, false);
         else if (code == KeyEvent.VK_ENTER){
             doenter();
         }
         else if (code == KeyEvent.VK_END){
             pointerx = mainsi.text.length();
         }
         else if (code == KeyEvent.VK_HOME){
             pointerx = 0;
         }
         else return false;
        return true;
     }

     public boolean keytyped(KeyEvent e) {
        char key = (char) e.getKeyChar();
        if(key=='\n')return false;
        if(key== KeyEvent.VK_TAB)return false;
        char hiddenkey = key;
        if(u.notAllowedInFileNames.indexOf(key) >= 0)return false;
        if(!newadmin)
            removeerror();
        boolean enteringpassword = (gotname && !passwordhint);
        boolean isspace = key==' ';
        if(enteringpassword)
            key = '*';
        if(hiddenkey=='\b' ){
            if(text.length()>0 && pointerx >0){
                text = text.substring(0, pointerx-1)+text.substring(pointerx);
                underlyingpass = underlyingpass.substring(0, pointerx-1)+underlyingpass.substring(pointerx);
                pointerx--;
                getNewNames();
            }
        }
        else if ((int)hiddenkey==e.VK_DELETE){
            if(text.length()>0 && pointerx>=0 && pointerx<=text.length()-1){
                text = text.substring(0, pointerx)+text.substring(pointerx+1);
                underlyingpass = underlyingpass.substring(0, pointerx)+underlyingpass.substring(pointerx+1);
                getNewNames();
            }
        }
        else if (text.length()<(passwordhint?maxcharpasshint:maxchar)){
//            if(key==' ' && text.trim().equals(""))return false;
            if(isspace){
               if(enteringpassword)return false;
               if(text.trim().equals(""))return false;            
            }
            text = text.substring(0, pointerx)+key+text.substring(pointerx);
            underlyingpass = underlyingpass.substring(0, pointerx)+hiddenkey+underlyingpass.substring(pointerx);
            pointerx++;
            getNewNames();
        }
 
        while(text.length()>0 &&  text.charAt(0)==' '){
            text = text.substring(1);
            underlyingpass = underlyingpass.substring(1);
        }
        return true;
    }


  }

  
  
  
  
  public class hyperlink extends mover.formattedtextmover {
     hyperlink(String mess1, Color col1, Font fw1, runMovers r1, boolean centered1, int fontstyle1, boolean underlined1) {
        super(mess1, col1, fw1, r1, centered1, fontstyle1, underlined1);
        this.handcursor = true;
     }

     public void mouseClicked(int x, int y) {
         removeerror();
         while((student.fishpanel.isMover(messsq)))
             u.pause(10);
         if(this.equals(lbShowHint)){
            setHint(true);    
         }
         else if(this.equals(lbHideHint)){
            setHint(false);
         }
         else if(this.equals(lbForgottenPassword)){
            showerror(student.newstu.administrator?u.gettext("so", "passwordsupport_admin", shark.programName):u.gettext("so", "passwordsupport_stu", shark.programName));
         }
     };
  }  
  
  
  
  
  
  public class butsq extends mover {
    String text;
    FontMetrics fm;
    Font f;
    int textwidth;
    float fl;

     butsq(String s, float alpha) {
        super(false);
        fl = alpha;
        text = s;
        w = sidebarw*5/20;
        h = individualh*5/8;
        textwidth = student.fishpanel.getFontMetrics(butFont).stringWidth(s);
        this.handcursor = true;
     }

     public void mouseClicked(int x, int y) {
         removeerror();
             if(text.equals(strok)){
             if(whichscreen==SCREEN_TEMP){
 //                student.newstu.addtolist();
                 mainsi.nameentered(mainsi.text);
             }
             else if(whichscreen==SCREEN_NONE)
                 mainsi.doenter();
         }
         else if(text.equals(strno)){
            if (whichscreen==SCREEN_NOADMIN)
               end();
         }
         else if(text.equals(stryes)){
            if (whichscreen==SCREEN_NOADMIN){
                newadmin = true;
                changescreen(SCREEN_NOADMIN, false);
            }
         }
         else if(text.equals(strcancel)){
             if(student.fishpanel.isMover(stmtemp)){
                 if(student.newstu!=null)student.newstu.releaselock();
                 changescreen(SCREEN_TEMP, false);
             }
             else if(passwordhint){
                 end();
             }
//             else if(newadmin){
//                 gotname = false;
//                 mainsi.clear();
//             }
             else{
                student.cancelSignon();
                gotname = false;
                student.newstu = null;
                student.gotstuname = null;      
                
                
                
                
                
                setup(false, false, false, wantside);
             }
         }
     };

     public void paint(Graphics g,int x1, int y1, int w1, int h1) {
        Font tempf = g.getFont();
        Graphics2D g2d = ((Graphics2D)g);
        if(fl!=1.0f && !mouseOver)
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, fl));
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setColor(Color.white);
        g.fillRoundRect(x1, y1, w1, h1, roundcorners, roundcorners);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);
        if(fl!=1.0f)
           g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        g.setColor(Color.black);
        g.setFont(butFont);
        g.drawString(text, x1+((w1-(textwidth))/2), y1+(h1/2)+(g.getFontMetrics().getAscent()/3));
        g.setFont(tempf);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
     }
  }

  public class backgroundsq extends mover {
      Color col;
      float f;

    backgroundsq(int width, int height, Color c, float alpha) {
        super(false);
        col = c;
        f = alpha;
        w = width;
        h = height;
     }
     public void paint(Graphics g,int x1, int y1, int w1, int h1) {
        Graphics2D g2d = ((Graphics2D)g);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        if(f!=1.0f)
           g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, f));
        g.setColor(col);
        g.fillRoundRect(x1, y1, w1, h1, roundcorners, roundcorners);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);
        if(f!=1.0f)
           g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
     }
  }

  public class movesq extends mover {
    FontMetrics fm;
    Font f;
    int textw;
    boolean up;
    Polygon p = null;
    public boolean show;
    public boolean enabled;
    float fl;

     movesq(boolean moveup, float alpha) {
        super(false);
        fl = alpha;
        up = moveup;
        w = sidebarw*7/20;
        h = individualh*5/8;

     }

     public void changeImage(long time) {
         // end of a move
         if(moving && endmove<time){
             int k;
             if (movingup) {
                 for (int i = 0; i < movers.length; i++) {
                     k = i + 1;
                     if (k >= 0 && k <= movers.length - 1) {
                         movers[i] = movers[k];
                     }
                 }
             } else {
                 for (int i = movers.length-1; i >=0; i--) {
                     k = i - 1;
                     if (k >= 0 && k <= movers.length - 1) {
                         movers[i] = movers[k];
                     }
                 }
             }

             k = movingup?sideno-1:0;
             mover mov = movers[k];
             String n = ((namesq)mov).text;
             int j = findNextName(n, movingup);
             if(j>=0){
                 n = allnames[j];
                 namesq sq = new namesq(n, false, null, false, buttonalpha, 1);
                 movers[k] = sq;
                 student.fishpanel.addMover(sq,sidepoints[k].x, sidepoints[k].y);
             }
             if(allnames.length<=sideno){
                wantdownbut = wantupbut = false;      
             }
             else{
                if(movingup){
                    wantdownbut = j < allnames.length-1;
                    wantupbut = true;
                }
                else{
                    wantupbut = j > 0;
                    wantdownbut = true;
                }
             }
             moving = false;
         }
         enabled = (up&&wantdownbut)||(!up&&wantupbut);

            this.handcursor = this.enabled;

         if (mouseDown && mouseOver && endmove<time  && !moving && show && enabled) {
             startSideMove(time, up);
         }
     }

     public void paint(Graphics g,int x1, int y1, int w1, int h1) {
        if(!show)return;
        Graphics2D g2d = ((Graphics2D)g);
        if(fl!=1.0f && (!mouseOver || !enabled))
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, fl));
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        if(enabled)g.setColor(Color.white);
        else g.setColor(Color.darkGray);
        g.fillRoundRect(x1, y1, w1, h1, roundcorners, roundcorners);
        g.setColor(Color.black);
         if (p == null) {
             int h2 = (h1*5/10);
             int x2 = x1+(w1/2);
             p = new Polygon();
             if (!up) {
                 p.addPoint(x2 - (h2), y1+h1-((h1-h2)/2));
                 p.addPoint(x2 + (h2), y1+h1-((h1-h2)/2));
                 p.addPoint(x2, y1+((h1-h2)/2));
             } else {
                 p.addPoint(x2 - (h2),y1+((h1-h2)/2));
                 p.addPoint(x2 + (h2),y1+((h1-h2)/2));
                 p.addPoint(x2, y1+h1-((h1-h2)/2));
             }
         }
         g.fillPolygon(p);
        if(fl!=1.0f)
           g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);
     }
  }
}