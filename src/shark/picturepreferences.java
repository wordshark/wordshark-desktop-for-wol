/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package shark;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.lang.ref.SoftReference.*;
import javax.swing.event.*;
/**
 *
 * @author Paul Rubie
 */
public class picturepreferences extends JPanel{
    boolean vert;
    boolean you;
    JList piclist;
    u.my3wayCheckBox showpic;
    u.my3wayCheckBox showsigns;
    u.my3wayCheckBox showfingerspellings;
    settings sets;
    boolean wantpicchoices = !shark.language.equals(shark.LANGUAGE_NL);
    String imtypes[] = new String[]{u.gettext("showpictures", "name"),
            u.gettext("showsigns", "name"),
            u.gettext("showfingerspellings", "name")};
//    public static String defaultprefs[] = shark.language.equals(shark.LANGUAGE_NL)? new String[]{"0","-1","-1"}:new String[]{"0","1","-1"};
    public static String defaultprefs[] = shark.language.equals(shark.LANGUAGE_NL)? new String[]{"0","-1","-1"}:new String[]{"0","-1","-1"};
    String strnopics = u.gettext("adminsettings", "nopictures");
    String strmixed = u.gettext("adminsettings", "mixedsettings");
    JButton up;
    JButton down;
    student onestu;

    boolean universal;

    JDialog owner;
    Font smallerplainfont = sharkStartFrame.treefont.deriveFont(Font.PLAIN, (float)sharkStartFrame.treefont.getSize()-1 );

    
    public picturepreferences(JDialog owner1, boolean vertical, settings setngs, boolean global){
        owner = owner1;
//        vert = vertical;
        vert = false;
        sets = setngs;
        universal = global;
     init();
    }    
    
    public picturepreferences(boolean vertical, settings setngs, boolean global){
//        vert = vertical;
        vert = false;
        sets = setngs;
        universal = global;
     init();
    }
        
        
    void init(){     

        if(sets==null)
            onestu = sharkStartFrame.studentList[sharkStartFrame.currStudent];
        this.setLayout(new GridBagLayout());
        GridBagConstraints grid = new GridBagConstraints();


  //      this.setBackground(Color.orange);
  //      this.setOpaque(true);


        showpic = u.get3wayCheckBox("showpictures");
        showpic.setToolTipText(null);
        showpic.setFont(smallerplainfont);
        showsigns = u.get3wayCheckBox("showsigns");
        showsigns.setToolTipText(null);
        showsigns.setFont(smallerplainfont);
        showfingerspellings = u.get3wayCheckBox("showfingerspellings");
        showfingerspellings.setToolTipText(null);
        showfingerspellings.setFont(smallerplainfont);

        int sw = sharkStartFrame.mainFrame.getWidth();
        int buttondim = (sw*14/22)/24;
        int buttonimdim =  buttondim- (buttondim/5);



        JPanel picpn = new JPanel(new GridBagLayout());
        JPanel signspn = new JPanel(new GridBagLayout());
        JPanel fingerpn = new JPanel(new GridBagLayout());
              JLabel typemess = new JLabel();
     typemess.setText( u.convertToHtml(u.gettext("picpreferences", "typemess")));
     typemess.setFont(smallerplainfont);
        grid.gridx = -1;
        grid.gridy = 0;

        grid.weighty = 0;
        grid.fill = GridBagConstraints.NONE;
        grid.anchor = GridBagConstraints.NORTHWEST;




        grid.weightx =0;
        picpn.add(showpic, grid);
        grid.insets = new Insets(0,10,0,0);
  //      grid.anchor = GridBagConstraints.CENTER;
        picpn.add(u.infoLabel(owner, u.gettext("showpictures", "tooltip"), (!vert&&!wantpicchoices)), grid);
        grid.anchor = GridBagConstraints.NORTHWEST;
        if(wantpicchoices){
        grid.weightx =1;
        picpn.add(new JPanel(), grid);
        }
        grid.insets = new Insets(0,0,0,0);
        grid.weightx =0;
        signspn.add(showsigns, grid);
        grid.insets = new Insets(0,10,0,0);
  //      grid.anchor = GridBagConstraints.CENTER;
        signspn.add(u.infoLabel(owner, u.gettext("showsigns", "tooltip")), grid);
        grid.anchor = GridBagConstraints.NORTHWEST;
        grid.weightx =1;
        signspn.add(new JPanel(), grid);
        grid.insets = new Insets(0,0,0,0);
        grid.weightx =0;
        fingerpn.add(showfingerspellings, grid);
        grid.insets = new Insets(0,10,0,0);
//        grid.anchor = GridBagConstraints.CENTER;
        fingerpn.add(u.infoLabel(owner, u.gettext("showfingerspellings", "tooltip")), grid);
        grid.anchor = GridBagConstraints.NORTHWEST;
        grid.weightx =1;
        fingerpn.add(new JPanel(), grid);
        grid.insets = new Insets(0,0,0,0);
        grid.gridx = 0;
        grid.gridy = -1;
        JPanel showpan = new JPanel(new GridBagLayout());
        grid.fill = GridBagConstraints.HORIZONTAL;
        if(wantpicchoices){
            grid.insets = new Insets(0,10,10,0);
            showpan.add(typemess, grid);
        }
        if(wantpicchoices)
            grid.insets = new Insets(0,10,0,10);
        else
            grid.insets = new Insets(10,0,0,0);

        grid.anchor = GridBagConstraints.NORTH;
        if(!wantpicchoices){
            grid.weighty =1;
            grid.fill = GridBagConstraints.NONE;
        }
        else{
        grid.weighty =0;
        grid.fill = GridBagConstraints.BOTH;
        }

        showpan.add(picpn, grid);


        if(wantpicchoices){
 //        showpan.add(signspn, grid);
         showpan.add(fingerpn, grid);
         grid.weighty =1;
         showpan.add(new JPanel(), grid);
        }



     showpic.addActionListener(new java.awt.event.ActionListener() {
       public void actionPerformed(ActionEvent e) {
            setcbchange((u.my3wayCheckBox)e.getSource(), sharkImage.TYPE_PICTURE);
       }
     });
     showsigns.addActionListener(new java.awt.event.ActionListener() {
       public void actionPerformed(ActionEvent e) {
        setcbchange((u.my3wayCheckBox)e.getSource(), sharkImage.TYPE_SIGN);
       }
     });

     showfingerspellings.addActionListener(new java.awt.event.ActionListener() {
       public void actionPerformed(ActionEvent e) {
        setcbchange((u.my3wayCheckBox)e.getSource(), sharkImage.TYPE_FINGER);
       }
     });


        piclist = new JList();
        piclist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            piclist.addListSelectionListener(new ListSelectionListener() {
                public void valueChanged(ListSelectionEvent e) {
                    JList jl = (JList)e.getSource();
                    int count = jl.getModel().getSize();
                    int sel = jl.getSelectedIndex();
                    up.setEnabled(count>1 && sel>=0 && sel>0);
                    down.setEnabled(count>1 && sel>=0 && sel<count-1);
                }
            });
        

            JLabel prefmess = new JLabel();
     prefmess.setText( u.convertToHtml(u.gettext("picpreferences", "mess"))        );
prefmess.setFont(smallerplainfont);
        up = u.sharkButton();
        down = u.sharkButton();
         up.setEnabled(false);
         down.setEnabled(false);
        up.setPreferredSize(new Dimension(buttondim, buttondim));
        up.setMinimumSize(new Dimension(buttondim, buttondim));
        down.setPreferredSize(new Dimension(buttondim, buttondim));
        down.setMinimumSize(new Dimension(buttondim, buttondim));


        Image im = Toolkit.getDefaultToolkit().getImage(sharkStartFrame.publicPathplus + "sprites" +
                                       sharkStartFrame.separator +
                                       "up_il48.png");
        ImageIcon iiinfo = new ImageIcon(im.getScaledInstance(buttonimdim,
            buttonimdim, Image.SCALE_SMOOTH));
        up.setIcon(iiinfo);
        im = Toolkit.getDefaultToolkit().getImage(sharkStartFrame.publicPathplus + "sprites" +
                                       sharkStartFrame.separator +
                                       "down_il48.png");
        iiinfo = new ImageIcon(im.getScaledInstance(buttonimdim,
            buttonimdim, Image.SCALE_SMOOTH));
        down.setIcon(iiinfo);

     down.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               ListModel dlm = (ListModel)piclist.getModel();
               int len = dlm.getSize();
               int k = piclist.getSelectedIndex();
               if(k<0 || k>=len-1)return;

               String s[] = new String[]{};
               for(int i = 0; i < dlm.getSize(); i++){
                   if(i!=k) s = u.addString(s, (String)dlm.getElementAt(i));
               }
               s = u.addString(s, (String)dlm.getElementAt(k), k+1);
               setlist(s, null);
               setprefchange(s);
               piclist.setSelectedIndex(k+1);
          }
     });
     up.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               ListModel dlm = (ListModel)piclist.getModel();
               int k = piclist.getSelectedIndex();
               if(k<=0)return;

               String s[] = new String[]{};
               for(int i = 0; i < dlm.getSize(); i++){
                   if(i!=k) s = u.addString(s, (String)dlm.getElementAt(i));
               }
               s = u.addString(s, (String)dlm.getElementAt(k), k-1);
               setlist(s, null);
               setprefchange(s);
               piclist.setSelectedIndex(k-1);
          }
     });
     grid.insets = new Insets(0,0,0,10);
     JPanel butpn = new JPanel(new GridBagLayout());
     grid.gridx = 0;
     grid.gridy = -1;
     butpn.add(up, grid);
     grid.insets = new Insets(10,0,0,10);
     butpn.add(down, grid);
     grid.insets = new Insets(0,0,0,0);       
     grid.gridx = -1;
     grid.gridy = 0;
     grid.fill = GridBagConstraints.BOTH;
     JPanel listpn = new JPanel(new GridBagLayout());
     grid.weightx = 1;
     grid.weighty = 1;
     listpn.add(piclist, grid);
     grid.weightx = 0;
     grid.fill = GridBagConstraints.HORIZONTAL;
     grid.insets = new Insets(0,10,0,0);
     listpn.add(butpn, grid);
     grid.weightx = 1;
     grid.insets = new Insets(0,0,0,0);
     grid.gridx = -1;
     grid.gridy = 0;
     grid.anchor = GridBagConstraints.WEST;
     JPanel prefpn = new JPanel(new GridBagLayout());
     JPanel prefpnmess = new JPanel(new GridBagLayout());
     grid.anchor = GridBagConstraints.NORTHWEST;
     prefpnmess.add(prefmess, grid);
     grid.insets = new Insets(0,10,0,10);
     grid.weightx = 0;


     prefpnmess.add(u.infoLabel(owner, u.gettext("picpreferences", "info", shark.programName), vert?false:true), grid);

     grid.weightx = 1;
     grid.insets = new Insets(0,0,0,0);
     grid.gridx = 0;
     grid.gridy = -1;
     grid.weighty = 0;
     grid.weightx = 1;
     grid.fill = GridBagConstraints.BOTH;
     grid.insets = new Insets(0,0,10,0);
     grid.anchor = GridBagConstraints.WEST;
     grid.weightx = 1;



     prefpn.add(prefpnmess, grid);
     grid.insets = new Insets(0,0,0,0);
     grid.weighty = 1;

     grid.anchor = GridBagConstraints.CENTER;
     prefpn.add(listpn, grid);
     grid.fill = GridBagConstraints.NONE;
     if(!vert && wantpicchoices){
            setBorder(BorderFactory.createEtchedBorder());
     }
     grid.gridx = -1;
     grid.gridy = 0;
     grid.weighty = 1;
     grid.weightx = 1;

     
     grid.anchor = GridBagConstraints.NORTH;
      grid.fill = GridBagConstraints.BOTH;
     if(wantpicchoices){         
        grid.insets = new Insets(10,0,10,0);
     }
     else{
          grid.insets = new Insets(0,0,0,0);
          if(!vert)
         showpan.setBorder(BorderFactory.createEtchedBorder());
     }
     add(showpan, grid);



     if(wantpicchoices){
         if(vert)
            grid.insets = new Insets(10,40,10,0);
         else
            grid.insets = new Insets(10,10,10,0);
        grid.weighty = 1;
        grid.weightx = 1;
        grid.fill = GridBagConstraints.BOTH;
        add(prefpn, grid);
      }
    }

    public void setup(){
        String s[] = sets!=null?sets.firststu.picprefs:onestu.picprefs;
        String ss[] = null;
        if(s!=null){
            ss = s;
        }
        else {
            ss= defaultprefs;
        }
        int p = Integer.parseInt(ss[sharkImage.TYPE_PICTURE]);
        showpic.setState(sets!=null&&sets.xpicpref?u.SOME:(p<0?u.NONE:u.ALL));
        p = Integer.parseInt(ss[sharkImage.TYPE_SIGN]);
        showsigns.setState(sets!=null&&sets.xpicpref?u.SOME:(p<0?u.NONE:u.ALL));
        p = Integer.parseInt(ss[sharkImage.TYPE_FINGER]);
        showfingerspellings.setState(sets!=null&&sets.xpicpref?u.SOME:(p<0?u.NONE:u.ALL));
        if(sets!=null&&sets.xpicpref)
            setlist(new String[]{strmixed}, "init");
        else{
            int k;
            String data[] = null;
            int count = 0;
            for(int i = 0; i < ss.length; i++){
                k = u.findString(ss, String.valueOf(count));
                count++;
                if(k<0)continue;
                if(data==null)data = new String[]{imtypes[k]};
                else data = u.addString(data, imtypes[k]);
            }
            setlist(data, "init");
        }
    }


    void setlist(String s[], String toselect){
        if(s==null || s.length==0)piclist.setListData(new String[]{strnopics});
        else {
            piclist.setListData(s);
        }
        if(toselect!=null){
            if(toselect.equals("init"))
                piclist.setSelectedIndex(0);
            else if(u.findString(s, toselect) >= 0) piclist.setSelectedValue(toselect, true);
            else if(s.length>0) piclist.setSelectedIndex(0);
        }
        else if(s!=null && s.length>0)
            piclist.setSelectedIndex(0);
    }


    void setprefchange(String s[]){
        String prefs = "";
        for(int i = 0; i < imtypes.length; i++){
            int k;
            if((k=u.findString(s, imtypes[i]))>=0){
                prefs += String.valueOf(k);
            }
            else{
                prefs += "-1";
            }
            if(i < imtypes.length-1)prefs+="|";
        }
        setdb(prefs);

    }

    void setwantchange(String s[]){
        String prefs = "";
        for(int i = 0; i < imtypes.length; i++){
            int k;
            if(s!=null && (k=u.findString(s, imtypes[i]))>=0){
                prefs += String.valueOf(k);
            }
            else{
                prefs += "-1";
            }
            if(i < imtypes.length-1)prefs+="|";
        }
        setdb(prefs);

    }


    void setcbchange(u.my3wayCheckBox cb, int iis){
         String ss[] = null;
         ListModel lm = piclist.getModel();
         for(int i = 0; i < lm.getSize(); i++){
             String element = (String)lm.getElementAt(i);
             if(element.equals(strnopics)||element.equals(strmixed))continue;
             if(ss==null)ss = new String[]{element};
              else ss = u.addString(ss, element);
         }
         if (cb.state != u.ALL) {
           cb.setState(u.ALL);
           int pos= iis;
           pos = Math.max(0, pos);
           if(ss== null){
               ss = new String[]{};
               pos = 0;
           }
           else pos = Math.min(pos, ss.length);
           ss = u.addString(ss, imtypes[iis], pos);
         }
         else {
           cb.setState(u.NONE);
           if(ss== null)return;
           ss = u.removeString2(ss, imtypes[iis]);
         }

        if(showpic.state==u.SOME)showpic.setState(u.NONE);
         if(showsigns.state==u.SOME)showsigns.setState(u.NONE);
         if(showfingerspellings.state==u.SOME)showfingerspellings.setState(u.NONE);

         setlist(ss, imtypes[iis]);
         setwantchange(ss);
    }


    void setdb(String s){
        if(s.equals(u.combineString(defaultprefs))){
            if(sets!=null)
                student.queueupdate(sets!=null?sets.students:new String[]{onestu.name}, new String[] {"mpicpref"});
            else
                onestu.picprefs = null;

        }
        else{
            if(sets!=null)
                student.queueupdate(sets!=null?sets.students:new String[]{onestu.name}, u.addString(new String[] {"mpicpref"},u.splitString(s)));
            else
                onestu.picprefs = u.splitString(s);

        }
    }
}
