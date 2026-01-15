/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package shark;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;
import java.util.*;

/**
 *
 * @author Paul Rubie
 */
public class steppedmanaged extends JDialog {

    JDialog owner;
    int margin = 5;
    Color cream = sharkStartFrame.cream;
    static Color steppedcol = new Color(153,102,153);
    static Color inactivecol = Color.lightGray;
    boolean showstus;
    Font plainfont = sharkStartFrame.treefont.deriveFont(Font.PLAIN);
    student teacher;
    JList jlsteps;
    JList jltopics;
    JList jlgames;
    
    JPanel panelgames = new JPanel(new GridBagLayout());
    String stepitem = u.gettext("steppedprogram", "item");
    JCheckBox cbphonics;
        program.saveprogram currprogram;
        dndTree_base stutree;
    dragger_base dragger;
        JList jlstepnames;
        JList jlstandardnames;

        static boolean stepactive = false;
        boolean isdragging = false;
        admin adm;
        static String activatedName = null;
        static program.saveprogram activatedProgram = null;
        JDialog thisd;
        static Color standardprogcol = new Color(65,105,225);
        JButton btsaveas;
        JButton btdelete;
        JTabbedPane jtp;
        Component complastclicked  = null;
        ArrayList standardprograms;
        JPanel pnstep;
        JLabel lbsteps;
        JLabel lbtopics;
        JLabel lbgames;
        JPanel cpnproglist;
        JButton removework;
        JLabel lballgames;
        JButton btrename;
        JButton btedit;
        JButton btnew;
        DefaultTreeModel stumodel;
        String currstu[];
        boolean isstepped;
        int assignmentview = -1;
        static final int assignment_UNI = 0;
        static final int assignment_CURR = 1;
        boolean assignview = false;
        sharkTree assignmenttree;
        String assigndivider = ":   ";
        JPanel colpnproglist = new JPanel(new GridBagLayout());
        JPanel colpnproglist_blank = new JPanel(new GridBagLayout());
        JLabel lbWorkTitle = new JLabel(u.convertToHtml(u.gettext("adminmanagework", "titledetails") + "|&nbsp" ));
        TreeSelectionListener asstsl;
        String str_setby = u.gettext("adminmanagework", "setby")+" ";
        String strmixedlists = u.gettext("stulist_", "mixedlist");



    steppedmanaged(JDialog parent, String title1, student admin, String stu[], boolean stepped, boolean withstus) {
        super(parent);
        setTitle(title1);
        adm = (admin)parent;
        teacher = admin;
        currstu = stu;
        isstepped = stepped;
        thisd = this;
        showstus = withstus;
        init();
    }

    steppedmanaged(JDialog parent, String title1, student admin, String stu[], boolean stepped, boolean withstus, int assignmentoverview) {
        super(parent);
        setTitle(title1);
        adm = (admin)parent;
        teacher = admin;
        currstu = stu;
        isstepped = stepped;
        thisd = this;
        showstus = withstus;
        assignmentview = assignmentoverview;
        if(assignmentoverview>=0)assignview = true;
        init();
    }

    void init() {
        GridBagConstraints grid = new GridBagConstraints();
        JPanel cpnsteppedlist = new JPanel(new GridBagLayout());
        JPanel cpnstulist = new JPanel(new GridBagLayout());
        cpnproglist = new JPanel(new GridBagLayout());
        JPanel pnmain = new JPanel(new GridBagLayout());
        getContentPane().setLayout(new GridBagLayout());

     int sw = sharkStartFrame.mainFrame.getWidth();
     int sh = sharkStartFrame.mainFrame.getHeight();

 //      if(assignmentview>=0){
 //        cpnstulist.setPreferredSize(new Dimension(sw*24/33, sh));
 //        cpnstulist.setMinimumSize(new Dimension(sw*24/33, sh));
 //      }
 //      else{
         cpnstulist.setPreferredSize(new Dimension(sw*20/66, sh));
         cpnstulist.setMinimumSize(new Dimension(sw*20/66, sh));
 //       }
     cpnsteppedlist.setPreferredSize(new Dimension(sw*25/66, sh));
     cpnsteppedlist.setMinimumSize(new Dimension(sw*25/66, sh));
     cpnproglist.setPreferredSize(new Dimension(sw*21/66, sh));
     cpnproglist.setMinimumSize(new Dimension(sw*21/66, sh));

    cpnproglist.setEnabled(false);
    

       if(assignview){
          cpnsteppedlist.setVisible(false);
       }
       setprogdetails(!assignview);

    addWindowListener(new java.awt.event.WindowAdapter() {
      public void windowClosing(WindowEvent e) {
            adm.updateonactivate = admin.ACTIVATEWORK;
      }
      public void windowActivated(WindowEvent e) {
                String ss[] = db.list(teacher.name,db.PROGRAM);
                String newsteps[] = new String[]{};
                for(int k = 0; k < ss.length; k++){
                    program.saveprogram program = (program.saveprogram)db.find(teacher.name, ss[k], db.PROGRAM);
                    if(program!=null){
                        if(!program.simple){
                            newsteps = u.addString(newsteps, ss[k]);
                        }
                    }
                }
          if(stepactive){
            if(activatedName!=null){
                jlstepnames.setListData(newsteps);
                jlstepnames.setSelectedValue(activatedName, true);
                activatedName = null;
            }
            else if(jlstepnames.getModel().getSize()>0)
                jlstepnames.setSelectedIndex(0);
          }
          else{
            if(activatedName!=null){
                jlstandardnames.setSelectedValue(activatedName, true);
                int k = jlstandardnames.getSelectedIndex();
                if(activatedProgram!=null){
                    standardprograms.set(k, activatedProgram);
                    activatedProgram = null;
                }
                activatedName = null;
                standardchanged(jlstandardnames);
            }
            else if(jlstandardnames.getModel().getSize()>0)
                jlstandardnames.setSelectedIndex(0);
         }
        }
    });

        grid.fill = GridBagConstraints.BOTH;
        grid.gridx = -1;
        grid.gridy = 0;
        grid.weightx = 1;
        grid.weighty = 1;
        grid.insets = new Insets(0,0,0,0);


        JPanel hpnproglist = new JPanel(new GridBagLayout());
        hpnproglist.setBackground(cream);
        hpnproglist.setOpaque(true);
        grid.gridx = -1;
        grid.gridy = 0;
        grid.weighty = 1;
        grid.insets = new Insets(margin,margin,margin,0);
        hpnproglist.add(lbWorkTitle, grid);
        lbWorkTitle.setVisible(false);
        grid.insets = new Insets(0,0,0,0);

        colpnproglist.setOpaque(false);


        pnstep = new JPanel(new GridBagLayout());
        JPanel pntopics = new JPanel(new GridBagLayout());
        JPanel pngames = new JPanel(new GridBagLayout());
        JPanel pnstep2 = new JPanel(new GridBagLayout());
        JPanel pntopics2 = new JPanel(new GridBagLayout());
        JPanel pngames2 = new JPanel(new GridBagLayout());

        grid.gridx = 0;
        grid.gridy = -1;

        grid.insets = new Insets(0,margin,0,margin);
        pnstep.add(pnstep2, grid);
        pntopics.add(pntopics2, grid);
        pngames.add(pngames2, grid);


        lbsteps = new JLabel(u.gettext("adminmanagework", "step1"));
        lbtopics = new JLabel(u.gettext("adminmanagework", "selwordlist"));
        lbgames = new JLabel(u.gettext("adminmanagework", "selgames"));
        jlsteps = new JList();
        jltopics = new JList();
        jlgames = new JList();


        jlsteps.addListSelectionListener(new ListSelectionListener(){
            public void valueChanged (ListSelectionEvent e){
                int k =((JList)e.getSource()).getSelectedIndex();
                if(k<0)return;
                jltopics.setListData(getTopicList(currprogram.it[k]));
                cbphonics.setSelected(currprogram.it[k].phonics);
                lballgames.setVisible(currprogram.it[k].games.length==0);
                panelgames.setVisible(currprogram.it[k].games.length!=0);
                jlgames.setListData(currprogram.it[k].games);
            }
        });
        jltopics.setCellRenderer(new itempainter());
        jlsteps.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jltopics.addListSelectionListener(new ListSelectionListener(){
            public void valueChanged (ListSelectionEvent e){
                jltopics.clearSelection();
            }
        });
        jlgames.addListSelectionListener(new ListSelectionListener(){
            public void valueChanged (ListSelectionEvent e){
                jlgames.clearSelection();
            }
        });
        cbphonics = new JCheckBox(u.gettext("simprog_", "phonicson"));
        cbphonics.setEnabled(false);


        jlsteps.setBackground(sharkStartFrame.defaultbg);
        jlsteps.setOpaque(true);
        jlgames.setBackground(sharkStartFrame.defaultbg);
        jltopics.setBackground(sharkStartFrame.defaultbg);
        jltopics.setOpaque(true);

        lbtopics.setForeground(Color.white);
        lbgames.setForeground(Color.white);
        lbsteps.setForeground(Color.white);

        lbtopics.setOpaque(true);
        lbgames.setOpaque(true);
        lbsteps.setOpaque(true);


        grid.weighty = 0;
        grid.insets = new Insets(0,0,0,0);
        pnstep2.add(lbsteps, grid);
        grid.insets = new Insets(0,0,0,0);
        grid.weighty = 1;
        pnstep2.add(new JScrollPane(jlsteps), grid);
        grid.insets = new Insets(0,0,0,0);

        grid.weighty = 0;
        pntopics2.add(lbtopics, grid);
        grid.insets = new Insets(0,0,0,0);
        grid.weighty = 1;
        pntopics2.add(new JScrollPane(jltopics), grid);
        
        grid.insets = new Insets(0,0,0,0);
        grid.weighty = 0;
        pntopics2.add(cbphonics, grid);
        grid.insets = new Insets(0,0,0,0);


        lballgames = new JLabel(u.gettext("adminmanagework", "allgames"));
        lballgames.setForeground(Color.gray);

        grid.weighty = 0;
        pngames2.add(lbgames, grid);
        grid.weighty = 1;
        grid.insets = new Insets(0,0,0,0);

        grid.fill = GridBagConstraints.VERTICAL;
        pngames2.add(lballgames, grid);
        grid.fill = GridBagConstraints.BOTH;
        JScrollPane jlgamesscroll = new JScrollPane(jlgames);
        panelgames.add(jlgamesscroll, grid);
        pngames2.add(panelgames, grid);
        grid.insets = new Insets(0,0,0,0);

        grid.weighty = 1;
        grid.insets = new Insets(0,0,margin,0);
        colpnproglist.add(pnstep, grid);
        colpnproglist.add(pntopics, grid);
        grid.insets = new Insets(0,0,0,0);
        colpnproglist.add(pngames, grid);
        pnstep.setOpaque(false);
        pntopics.setOpaque(false);
        pngames.setOpaque(false);

        grid.weighty = 0;
        grid.insets = new Insets(0,0,0,0);
        cpnproglist.add(hpnproglist, grid);
        grid.weighty = 1;
        grid.insets = new Insets(margin,margin,margin,margin);
        cpnproglist.add(colpnproglist, grid);
        cpnproglist.add(colpnproglist_blank, grid);


        JPanel hpnsteppedlist = new JPanel(new GridBagLayout());
        hpnsteppedlist.setBackground(cream);
        hpnsteppedlist.setOpaque(true);
        grid.gridx = -1;
        grid.gridy = 0;
        grid.weighty = 1;
        grid.insets = new Insets(margin,margin,margin,0);
        String strstandard = u.gettext("adminwork", "standard");
//        String strstepped = u.gettext("adminwork", "stepped_plural");
        String strstepped = u.gettext("adminwork", "stepped");
        if(showstus)
            hpnsteppedlist.add(new JLabel(u.convertToHtml(u.gettext("adminmanagework", "assignwork"))), grid);
        else{
            hpnsteppedlist.add(new JLabel(u.convertToHtml(u.gettext("adminmanagework", "assignwork2", isstepped?strstepped:strstandard) + "|&nbsp" )), grid);
        }
        grid.insets = new Insets(0,0,0,0);
        JPanel cntpnsteppedlist = new JPanel(new GridBagLayout());

        grid.gridx = 0;
        grid.gridy = -1;
        btrename = u.sharkButton();
        btrename.setText(u.gettext("rename", "label"));
        btsaveas = u.sharkButton();
        btsaveas.setText(u.gettext("saveas", "label"));
        btdelete = u.sharkButton();
        btdelete.setText(u.gettext("delete", "label"));
        btnew = u.sharkButton();
        btnew.setText(u.gettext("new", "label"));
        btnew.setToolTipText(u.gettext("adminmanagework", "newtooltip"));
        btedit = u.sharkButton();
        btedit.setText(u.gettext("edit", "label"));


        btrename.setFont(plainfont);
        btsaveas.setFont(plainfont);
        btdelete.setFont(plainfont);
        btnew.setFont(plainfont);
        btedit.setFont(plainfont);

        btrename.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               String prevname = stepactive?(String)jlstepnames.getSelectedValue():(String)jlstandardnames.getSelectedValue();
               int standardi = -1;
               if(!stepactive)standardi = jlstandardnames.getSelectedIndex();
               if(prevname == null)return;
               String name = null;
               jnode affected[] = null;
               program.saveprogram prevprogram = null;
               if(stepactive){
                    prevprogram = (program.saveprogram)db.find(teacher.name, prevname, db.PROGRAM);
               }
                    jnode node2 = (jnode)stumodel.getRoot();
                    for(Enumeration en = node2.depthFirstEnumeration();en.hasMoreElements();) {
                        jnode node = (jnode)en.nextElement();
                        if(node.equals(node2))continue;
                        String stustring = node.get();
                        String user = null;
                        int k;
                        if((k=stustring.indexOf(topicTree.ISTOPIC))>0){
                            user = stustring.substring(0, k);
                        }
                        String course = null;
                        if(k>=0){
                            course = stustring.substring(k+1);
                            if((k=course.indexOf("{"))>=0){
                                course = course.substring(0, k);//+"["+teacher.name+"]";
                            }
                        }
                        if(course!=null){
                           if(prevname.equals(course)){
                              if(prevprogram ==null)
                                prevprogram = (program.saveprogram)db.find(user, prevname+"["+teacher.name+"]", db.PROGRAM);
                              if(prevprogram ==null)
                                  prevprogram = (program.saveprogram)db.find(user, prevname, db.PROGRAM);
                                if(affected == null)affected = new jnode[]{};
                                affected = u.addnode(affected, node);
                           }
                        }
                    }
               if(prevprogram==null)return;
               String op[] =  new String[]{u.gettext("ok","label"),u.gettext("cancel","label")};
               JOptionPane getpw = new JOptionPane(
                  u.gettext("steppedprogram","entername"),
                  JOptionPane.PLAIN_MESSAGE,
                  0,
                  null,op,op[0]);
               getpw.setWantsInput(true);
                JTextField jtf = u.getjtextfieldcomp(getpw);
                if(jtf!=null){
                    jtf.setText(prevname);
                    String str1 = u.gettext("steppedprogram","enternametitle");
                    JDialog dialog = getpw.createDialog(sharkStartFrame.mainFrame,str1);
                    dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
                    while(true) {
                        jtf.selectAll();
                        keypad.dofullscreenkeypad(dialog, true);
                        dialog.setVisible(true);
                        keypad.dofullscreenkeypad(dialog, false);
                        Object result = getpw.getValue();
                        if(result == null) return;
                        if(result instanceof String){
                            int k = u.findString(op,(String)result);
                            if(k==0){ // ok
                                String input = (String)getpw.getInputValue();
                                while(input.length() > 0 && input.charAt(input.length()-1) == ' ')
                                    input = input.substring(0,input.length()-1);
                                String proglist[] = db.list(teacher.name,db.PROGRAM);
                                boolean already = u.findString(proglist, input)>=0;
                                if((!already) && program.programnamecheck(input)){
                                    name = input;
                                    break;
                                }
                                dialog.setVisible(false);
                                if(already)
                                    u.okmess(u.gettext("steppedprogram","alreadyusedtitle"), u.gettext("steppedprogram","alreadyused"), thisd);
                            }
                            else if(k==1){//cancel
                                dialog.dispose();
                                break;
                            }
                        }
                        else dialog.setVisible(false);
                    }
                }
                if(name == null)return;
                String newlist[] = new String[]{};
                if(stepactive){
                    db.delete(teacher.name, prevname, db.PROGRAM);
                    db.update(teacher.name, name, prevprogram, db.PROGRAM);       
                    String ss[] = db.list(teacher.name,db.PROGRAM);
 //                   String newsteps[] = new String[]{};
                    for(int k = 0; k < ss.length; k++){
                        program.saveprogram program = (program.saveprogram)db.find(teacher.name, ss[k], db.PROGRAM);
                        if(program!=null){
                            if(!program.simple){
                                newlist = u.addString(newlist, ss[k]);
                            }
                        }
                    }
                    jlstepnames.setListData(newlist);
                    activatedName = name;
                }
                else{
                   
                   ListModel lm = jlstandardnames.getModel();
                   for(int i =0; i < lm.getSize(); i++){
                       if(i != standardi)
                           newlist = u.addString(newlist, (String)lm.getElementAt(i));
                       else{
                           newlist = u.addString(newlist, name);
                       }
                   }

                }



                   jlstandardnames.setListData(newlist);
                   activatedName = name;
                   for(int k = 0; k < affected.length; k++){
                       String st = affected[k].get();
                        int h;
                        String user = "";
                        if((h=st.indexOf(topicTree.ISTOPIC))>0){
                            user = st.substring(0, h);
                        }
                        String course = "";
                        if(h>=0){
                            course = st.substring(h+1);
                            if((h=course.indexOf("{"))>=0){
                                course = course.substring(0, h);
                            }
                        }
                        db.delete(user, prevname+"["+teacher.name+"]", db.PROGRAM);
                        db.update(user, name+"["+teacher.name+"]", prevprogram, db.PROGRAM);
                       affected[k].setUserObject(user+topicTree.ISTOPIC+name+(stepactive?"{1}":"{0}"));
                   }
                   treereload(stutree);




          }
        });
        btsaveas.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               String prevname = stepactive?(String)jlstepnames.getSelectedValue():(String)jlstandardnames.getSelectedValue();
               if(prevname == null)return;
               String name = null;
               program.saveprogram prevprogram = (program.saveprogram)db.find(teacher.name, prevname, db.PROGRAM);
               if(prevprogram==null)return;
               String op[];
               String strcancel = u.gettext("cancel","label");
               if(shark.macOS) op =  new String[]{strcancel, u.gettext("ok","label")};
               else  op =  new String[]{u.gettext("ok","label"), strcancel};
               JOptionPane getpw = new JOptionPane(
                  u.gettext("steppedprogram","entername"),
                  JOptionPane.PLAIN_MESSAGE,
                  0,
                  null,op,op[0]);
               getpw.setWantsInput(true);
                JTextField jtf = u.getjtextfieldcomp(getpw);
                if(jtf!=null){
                    jtf.setText(prevname);
                    String str1 = u.gettext("steppedprogram","enternametitle");
                    JDialog dialog = getpw.createDialog(sharkStartFrame.mainFrame,str1);
                    dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
                    while(true) {
                        jtf.selectAll();
                        keypad.dofullscreenkeypad(dialog, true);
                        dialog.setVisible(true);
                        keypad.dofullscreenkeypad(dialog, false);
                        if(strcancel.equals(getpw.getValue())){
                            break;
                        }
                        String input = (String)getpw.getInputValue();
                        while(input.length() > 0 && input.charAt(input.length()-1) == ' ')
                            input = input.substring(0,input.length()-1);

                        String proglist[] = db.list(teacher.name,db.PROGRAM);
                        boolean already = u.findString(proglist, input)>=0;
                        if((!already) && program.programnamecheck(input)){
                            name = input;
                            break;
                        }
                        dialog.setVisible(false);
                        if(already)
                            u.okmess(u.gettext("steppedprogram","alreadyusedtitle"), u.gettext("steppedprogram","alreadyused"), thisd);
                    }
                }
                if(name == null)return;
                db.update(teacher.name, name, prevprogram, db.PROGRAM);
                String ss[] = db.list(teacher.name,db.PROGRAM);
                String newsteps[] = new String[]{};
                String newstandards[] = new String[]{};
                for(int k = 0; k < ss.length; k++){
                    program.saveprogram program = (program.saveprogram)db.find(teacher.name, ss[k], db.PROGRAM);
                    if(program!=null){
                        if(program.simple){
                            if(u.findString(newstandards, ss[k])<0){
                                newstandards = u.addString(newstandards, ss[k]);
                            }
                        }
                        else {
                            newsteps = u.addString(newsteps, ss[k]);
                        }
                    }
                }
                if(stepactive){
                    jlstepnames.setListData(newsteps);
                    activatedName = name;
                }
                else{
                    jlstandardnames.setListData(newstandards);
                    activatedName = name;
                }
          }
        });
        btedit.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               String s = stepactive?(String)jlstepnames.getSelectedValue():(String)jlstandardnames.getSelectedValue();
               if(s == null)return;
               if(stepactive){
                   new simpleprogram(thisd, teacher.name, null,false, false,
                         s, adm, true, true);
               }
               else{
                   String students[] = null;
                    jnode node2 = (jnode)stumodel.getRoot();
                    for(Enumeration en = node2.depthFirstEnumeration();en.hasMoreElements();) {
                        jnode node = (jnode)en.nextElement();
                        if(node.equals(node2))continue;
                        String stustring = node.get();
                        String user = null;
                        int k;
                        if((k=stustring.indexOf(topicTree.ISTOPIC))>0){
                            user = stustring.substring(0, k);
                        }
                        String course = null;
                        if(k>=0){
                            course = stustring.substring(k+1);
                            if((k=course.indexOf("{"))>=0){
                                course = course.substring(0, k);
                            }
                        }
                       if(course!=null){
                           if(s.equals(course)){
                               if(students==null)students= new String[]{};
                               students = u.addString(students, user);
                           }
                        }
                    }
                    new simpleprogram(thisd, teacher.name, students, false, false,
                         s, adm, false, true);
               }
          }
        });
        btdelete.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               if(stepactive){
                   String s = (String)jlstepnames.getSelectedValue();
                   int index = jlstepnames.getSelectedIndex();
                   if(s == null)return;
                   if(!u.yesnomess(shark.programName, u.gettext("adminmanagework","delstepped"), thisd))return;
                   db.delete(teacher.name, s, db.PROGRAM);
                   String ss[] = new String[]{};
                   ListModel lm = jlstepnames.getModel();
                   for(int i =0; i < lm.getSize(); i++){
                       if(i != index)
                        ss = u.addString(ss, (String)lm.getElementAt(i));
                   }
                   jlstepnames.setListData(ss);
                   jlstepnames.setSelectedIndex( Math.max(0, index-1));


                    jnode node2 = (jnode)stumodel.getRoot();
                    boolean changed = false;
                    for(Enumeration en = node2.depthFirstEnumeration();en.hasMoreElements();) {
                        jnode node = (jnode)en.nextElement();
                        if(node.equals(node2))continue;
                        String stustring = node.get();

                        String user = null;
                        int k;
                        if((k=stustring.indexOf(topicTree.ISTOPIC))>0){
                            String dels = stustring.substring(k+1);
                            if(dels.startsWith(s)){
                                user = stustring.substring(0, k);
                                dels = dels.substring(0, dels.lastIndexOf('{'));
                                db.delete(user, dels+"["+teacher.name+"]", db.PROGRAM);
                                db.delete(user, dels+teacher.name, db.PROGRAM); /// in case of older program??
                                node.setUserObject(user);
                                changed = true;
                            }
                        }
                    }
                    if(changed)
                        treereload(stutree);
               }
          }
        });
        btnew.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               new simpleprogram(thisd, teacher.name, null,false, false,
                            null,
                            adm, true, true);
          }
        });

        jlstandardnames = new JList();
    //    jlstandardnames.setBackground(steppedcol);
   //     Color selcol = stepactive?steppedcol:standardprogcol;
        jlstepnames = new JList();
   //     jlstepnames.setBackground(standardprogcol);
                JPanel standardpn;
        JPanel steppedpn= new JPanel(new GridBagLayout());
        standardpn = new JPanel(new GridBagLayout());
        grid.insets = new Insets(margin,margin,margin,margin);
        standardpn.setBackground(Color.white);
        steppedpn.setBackground(Color.white);
standardpn.add(jlstandardnames, grid);
standardpn.setBorder(BorderFactory.createLineBorder(standardprogcol, 4));
steppedpn.add(jlstepnames, grid);
        steppedpn.setBorder(BorderFactory.createLineBorder(steppedcol, 4));
        grid.insets = new Insets(0,0,0,0);
        String ss[] = db.list(teacher.name,db.PROGRAM);
        String newsteps[] = new String[]{};
        String newstandards[] = new String[]{};
        for(int k = 0; k < ss.length; k++){
            if(ss[k].equals("uninitializedValue"))
                continue;
            program.saveprogram program = (program.saveprogram)db.find(teacher.name, ss[k], db.PROGRAM);
            if(program!=null){
                if(program.simple) {
                    if(u.findString(newstandards, ss[k])<0){
                    newstandards = u.addString(newstandards, ss[k]);
                    }
                }
                else {
                    newsteps = u.addString(newsteps, ss[k]);
                }
            }
        }

        jlstepnames.setListData(newsteps);
        jlstepnames.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jlstandardnames.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        jlstepnames.addListSelectionListener(new ListSelectionListener(){
            public void valueChanged (ListSelectionEvent e){
                stepchanged((JList)e.getSource());

            }
        });

        jlstandardnames.addListSelectionListener(new ListSelectionListener(){
            public void valueChanged (ListSelectionEvent e){
                standardchanged((JList)e.getSource());

            }
        });
        JPanel cntpnsteppedbuttons = new JPanel(new GridBagLayout());

        cntpnsteppedlist.setBackground(Color.lightGray);
        cntpnsteppedlist.setOpaque(true);
        cntpnsteppedbuttons.setBackground(Color.lightGray);
        cntpnsteppedbuttons.setOpaque(true);

        grid.fill = GridBagConstraints.HORIZONTAL;
        grid.anchor = GridBagConstraints.NORTH;
        grid.weighty = 0;
        grid.insets = new Insets(50,margin,0,margin);
        cntpnsteppedbuttons.add(btnew, grid);
        grid.insets = new Insets(margin,margin,0,margin);
        cntpnsteppedbuttons.add(btedit, grid);
        cntpnsteppedbuttons.add(btrename, grid);
        cntpnsteppedbuttons.add(btsaveas, grid);
        cntpnsteppedbuttons.add(btdelete, grid);
        grid.weighty = 1;
        JPanel blankpan = new JPanel(new GridBagLayout());
        blankpan.setOpaque(false);
        cntpnsteppedbuttons.add(blankpan, grid);

        grid.weighty = 1;
        grid.anchor = GridBagConstraints.CENTER;
        grid.fill = GridBagConstraints.BOTH;
        grid.gridx = -1;
        grid.gridy = 0;
        cntpnsteppedlist.setBorder(BorderFactory.createEtchedBorder());
        grid.insets = new Insets(0,0,0,0);

        
        jtp =  new JTabbedPane();
        jtp.setFont(plainfont);
        jtp.addTab(strstandard, standardpn);
        jtp.addTab(strstepped, steppedpn);
        

        jtp.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent evt) {
                JTabbedPane pane = (JTabbedPane) evt.getSource();
                
                
                
                
                stepactive = pane.getSelectedIndex()==((standardprograms.size()==0)? 0: 1);

   
                if(jlstepnames.getSelectedIndex()<0 && jlstepnames.getModel().getSize()>0)
                    jlstepnames.setSelectedIndex(0);
                Color selcol = stepactive?steppedcol:standardprogcol;
                
                
                if(stepactive && jlstepnames.getModel().getSize()==0)selcol = inactivecol;
                else if (!stepactive && jlstandardnames.getModel().getSize()==0)selcol = inactivecol;
                setrightpancol(selcol);

                if(stepactive){
                    stepchanged(jlstepnames);
                }
                else{
                    standardchanged(jlstandardnames);
                }
            }
        });

        grid.insets = new Insets(margin,0,0,0);
        cntpnsteppedlist.add(jtp, grid);
        grid.insets = new Insets(0,0,0,0);
        if(showstus){
            grid.weightx = 0;
            cntpnsteppedlist.add(cntpnsteppedbuttons, grid);
            grid.weightx = 1;
        }
        JButton okbutton = u.sharkButton("simprog_exit");
//        okbutton.setForeground(Color.white);
//        okbutton.setBackground(Color.gray);
//        okbutton.setOpaque(true);
        okbutton.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               String prog = stepactive?(String)jlstepnames.getSelectedValue():(String)jlstandardnames.getSelectedValue();
               if(prog == null)return;
               int sel = stepactive?jlstepnames.getSelectedIndex():jlstandardnames.getSelectedIndex();
               program.saveprogram sp = null;
               if(stepactive){
                   sp = (program.saveprogram)db.find(teacher.name, prog, db.PROGRAM);
               }
               else{
                    sp = (program.saveprogram)standardprograms.get(sel);
               }
               if(currstu == null)return;
               if(sp==null)return;
               for(int i = 0; i < currstu.length; i++){
                   String progs[] = db.list(currstu[i],db.PROGRAM);
                   for(int k = 0; k < progs.length; k++)
                   {
//                       if(progs[k].endsWith("["+teacher.name+"]"))
                       if(u.CaseInsensitiveEndsWith(progs[k], "["+teacher.name+"]"))
                           db.delete(currstu[i],progs[k],  db.PROGRAM);
                   }
                   db.update(currstu[i], prog+"["+teacher.name+"]", sp, db.PROGRAM);
               }
                adm.updateonactivate = admin.ACTIVATEWORK;
               thisd.dispose();
          }
        });
        JButton cancelbutton = u.sharkButton("simprog_cancel");
 //       cancelbutton.setForeground(Color.white);
//        cancelbutton.setBackground(Color.gray);
 //       cancelbutton.setOpaque(true);
        cancelbutton.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               thisd.dispose();
          }
        });
        grid.gridx = -1;
        grid.gridy = 0;

        

        
        

        int top =showstus?margin:0;
        int right  = !showstus?margin:0;

        JPanel exitbuttons2 = null;
        if(showstus){
            exitbuttons2 = new JPanel(new GridBagLayout());
    //        grid.insets = new Insets(top,margin,margin,margin/2+1);
            JButton exitbt = u.sharkButton();
            exitbt.setText(u.gettext("exit", "label"));
            if(!shark.macOS){
                exitbt.setBackground(Color.red);
                exitbt.setForeground(Color.white);
            }
            else{
                exitbt.setForeground(Color.red);
            }
            grid.fill = GridBagConstraints.NONE;
            grid.insets = new Insets(margin*2,0,margin*2,0);
            exitbuttons2.add(exitbt, grid);
            grid.insets = new Insets(0,0,0,0);
            grid.fill = GridBagConstraints.BOTH;
            exitbt.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               adm.updateonactivate = admin.ACTIVATEWORK;
                   thisd.dispose();
          }
        });
        }


 grid.fill = GridBagConstraints.NONE;
        JPanel exitbuttons = new JPanel(new GridBagLayout());
        exitbuttons.setOpaque(true);
        exitbuttons.setBackground(Color.lightGray);
        int margin2 = margin*3;
        grid.insets = new Insets(margin2,top,margin2,0);
 //           grid.insets = new Insets(top,margin,margin,margin/2+1);
            exitbuttons.add(shark.macOS?cancelbutton:okbutton, grid);
  //          grid.insets = new Insets(top,margin/2+1,margin,right);
            exitbuttons.add(shark.macOS?okbutton:cancelbutton, grid);


             grid.fill = GridBagConstraints.BOTH;
        grid.gridx = 0;
        grid.gridy = -1;
        grid.weighty = 0;
        grid.insets = new Insets(0,0,0,0);
        cpnsteppedlist.add(hpnsteppedlist, grid);
        grid.weighty = 1;
        grid.insets = new Insets(margin,margin,margin,margin);
        grid.weighty = 1;
        cpnsteppedlist.add(cntpnsteppedlist, grid);
        if(!showstus){
            grid.weighty = 0;
            grid.insets = new Insets(0,0,0,0);
            cpnsteppedlist.add(exitbuttons, grid);
        }
        String standardprogs[] = new String[]{};
        String curruser = sharkStartFrame.studentList[sharkStartFrame.currStudent].name;
        if(assignview){
            assignmenttree = new sharkTree();
            String students[] = null;
            if(assignmentview == assignment_UNI ){
                students = db.dblistnames(sharkStartFrame.sharedPath);
                for(int j = students.length-1; j >= 0; j--){
                    if(u.findString(student.teacherlist, students[j])>=0){
                        students = u.removeString2(students, students[j]);
                    }
                    else if(u.findString(student.adminlist, students[j]) >= 0)
                    {
                        students = u.removeString2(students, students[j]);
                    }
                }
            }
            else if(assignmentview == assignment_CURR)
            {
                 loopini: for(int i=0;i<student.adminstudents.length;++i) {
                    for(int j=1; student.adminstudents[i] != null &&  j<student.adminstudents[i].length;++j) {
                        if (student.adminstudents[i][0].equalsIgnoreCase(curruser)){
                             if(student.adminstudents[i].length>1){
                                 String allusers[] = db.dblistnames(sharkStartFrame.sharedPath);
                                 for(int n = 1; n < student.adminstudents[i].length; n++){
                                     if( u.findString(allusers, student.adminstudents[i][n])>=0){
                                         if(students==null)students = new String[]{student.adminstudents[i][n]};
                                         else students = u.addString(students, student.adminstudents[i][n]);
                                     }
                                 }
                             }
                             break loopini;
                        }
                   }
                }
            }
            for(int j = students.length-1; j >= 0; j--){
                if(db.list(students[j], db.PROGRAM).length==0){
                    students = u.removeString2(students, students[j]);
                }
            }
            if(students==null){
                // error message
            }
            else{

     //           assignmenttree.setRootVisible(false);
                assignmenttree.root.setUserObject(u.gettext("adminmanagework", "adminmanagework"));
                assignmenttree.root.color = Color.black;
                assignmenttree.root.forceColor = true;
                assignmenttree.setCellRenderer(new treepainter2_base(assignmenttree, (int)cpnstulist.getPreferredSize().getWidth(), null, standardprogcol, steppedcol));
                for(int i = 0; i < students.length; i++){
                   jnode jn = new jnode(students[i]);
                   jn.setIcon(jnode.STUDENT);
                   assignmenttree.root.addChild(jn);
                }
                for(int i = 0; i < assignmenttree.root.getChildCount(); i++){
                   jnode usernode = ((jnode)assignmenttree.root.getChildAt(i));
                   String user = usernode.get();
                   String progs[] = db.list(user, db.PROGRAM);
                   if(progs!=null){
                       // put current stu at the top of the list
                       int p = -1;
                       for(int j = 0; progs.length>1 && j < progs.length; j++){
                           if(progs[j].endsWith("["+curruser+"]")){
                               p =j;
                               break;
                           }
                           else if(progs[j].endsWith(curruser)){
                               p =j;
                               break;
                           }
                       }
                       if(p>=0){
                           String yourprog = progs[p];
                           String t1[] = u.removeString(progs, p);
                           progs = u.addString(t1, yourprog, 0);
                       }
                       String users[] = null;
                        for(int j = 0; j < progs.length; j++){
                            String prog = progs[j];
                            String progname;
                            String adminname =null;
                            if(progs[j].indexOf("[")>=0){
                                int b1 = prog.lastIndexOf('[');
                                int b2 = prog.lastIndexOf(']');
                                try{
                                    adminname = prog.substring(b1+1, b2);
                                    progname = prog.substring(0, b1).trim();
                                }
                                catch(Exception ee){continue;}
                            }
                            else{
                                progname = prog;
                                if(users==null){
                                    users = db.dblistnames(sharkStartFrame.sharedPath);
                                }
                                for(int k = 0; k < users.length; k++){
                                    if(prog.endsWith(users[k])){
                                        adminname = users[k];
                                        break;
                                    }
                                }
                                if(adminname==null)continue;
                            }
                            program.saveprogram sp = (program.saveprogram)db.find(user, prog, db.PROGRAM);
                            if(u.findString(student.adminlist, sp.teacher) < 0 && 
                                    u.findString(student.teacherlist, sp.teacher) < 0 && 
                                    u.findString(student.deletedadmins, sp.teacher) >=0 ){
                                continue;
                            }
                            jnode jn = new jnode(str_setby+adminname + assigndivider +topicTree.ISTOPIC+progname+(sp.simple?"{0}":"{1}"));
//                            if(u.findString(student.adminlist, adminname)>=0)
//                                jn.setIcon(sp.simple?jnode.STANDARDPROGRAM:jnode.STEPPEDPROGRAM);
//                            else if(u.findString(student.teacherlist, adminname)>=0)
                            jn.setIcon(sp.simple?jnode.STANDARDPROGRAM:jnode.STEPPEDPROGRAM);
                            usernode.addChild(jn);
                        }
                    }
                }
                
                // remove stus with no valid set work
                jnode jjs[] = assignmenttree.root.getChildren();
                for(int i = jjs.length-1; i >= 0; i--){
                    if(jjs[i].getChildCount()==0)assignmenttree.root.remove(i);
                }
                if(assignmenttree.root.getChildCount()==0){
                    u.okmess(shark.programName, u.gettext("adminmanagework","nowork"), adm);
                    this.dispose();
                    return;
                }


                assignmenttree.model.reload();
                assignmenttree.expandAll(assignmenttree.root);

        assignmenttree.getSelectionModel().setSelectionMode(DefaultTreeSelectionModel.SINGLE_TREE_SELECTION);

         assignmenttree.getSelectionModel().addTreeSelectionListener((asstsl = new
            TreeSelectionListener() {
              public void valueChanged(TreeSelectionEvent e) {
                  jnode jn = assignmenttree.getSelectedNode();
                   if(jn ==null || jn.getLevel()==1){
                       setprogdetails(false);
//                       if(e.getOldLeadSelectionPath()!=null){
//                           assignmenttree.removeTreeSelectionListener(asstsl);
//                            assignmenttree.setSelectionPath(e.getOldLeadSelectionPath());
//                            assignmenttree.addTreeSelectionListener(asstsl);
//                       }
                      return;
                   }
                  setprogdetails(true);
                  String s = jn.get();
                  if(s.startsWith(str_setby))s = s.substring(str_setby.length());
                  String admin=null;
                  String progname=null;
                  try{
                      int k = s.indexOf(topicTree.ISTOPIC);
                      admin = s.substring(0, s.indexOf(assigndivider));
                      progname = s.substring(k+1, s.lastIndexOf('{'));
                      String stu = ((jnode)jn.getParent()).get();
                      program.saveprogram sp = (program.saveprogram)db.find(stu, progname+"["+admin+"]", db.PROGRAM);
                      if(sp==null){
                          sp = (program.saveprogram)db.find(stu, progname+admin, db.PROGRAM);
                      }
                      if(sp==null){
                          sp = (program.saveprogram)db.find(stu, progname, db.PROGRAM);
                      }
                      setprogdetails(sp!=null);
                       setassignments(sp);

                  }
                  catch(Exception en){
//                      if(e.getOldLeadSelectionPath()!=null){
//                           assignmenttree.removeTreeSelectionListener(asstsl);
//                            assignmenttree.setSelectionPath(e.getOldLeadSelectionPath());
//                            assignmenttree.addTreeSelectionListener(asstsl);
//                      }
                      setprogdetails(false);
                      return;
                  }
              }
         }));

            }
        }
        else{
            jnode currentnode = new jnode( u.gettext("admintree", "current"));
            DefaultTreeModel usermodel = new DefaultTreeModel(currentnode);
            stutree = new dropTree_base(usermodel, currentnode, false);
            stutree.addTreeWillExpandListener(new TreeWillExpandListener() {
                public void treeWillCollapse(TreeExpansionEvent ev) throws ExpandVetoException {
                    if(true)
                        throw new ExpandVetoException(ev);
                }
                public void treeWillExpand(TreeExpansionEvent ev) throws ExpandVetoException{}
            });
         stutree.expandPath(new TreePath(((jnode)usermodel.getRoot()).getPath()));
         stutree.getSelectionModel().setSelectionMode(DefaultTreeSelectionModel.SINGLE_TREE_SELECTION);
         stutree.setCellRenderer(new treepainter2_base(stutree, (int)cpnstulist.getPreferredSize().getWidth(), null, standardprogcol, steppedcol));
         stutree.getSelectionModel().addTreeSelectionListener((new
            TreeSelectionListener() {
              public void valueChanged(TreeSelectionEvent e) {
                  removework.setEnabled(workToRemove()!=null);
                  if(stutree.getSelectionCount()>0  && !isdragging){
                      String s = ((jnode)stutree.getSelectionPath().getLastPathComponent()).get();
                       int k;
                       boolean simple = false;
                       if((k=s.lastIndexOf("{"))>=0 && s.charAt(k+2)=='}'){
                           if(s.charAt(k+1)=='0')simple = true;
                           else if(s.charAt(k+1)=='1')simple = false;
                           s = s.substring(0, k);
                           if((k=s.indexOf(topicTree.ISTOPIC))>0){
                            s = s.substring(k+1);
                          }
                       }
                       else{
                           return;
                       }
                       if(simple){
                            ListModel lm = jlstandardnames.getModel();
                            for(int i =0; i < lm.getSize(); i++){
                                if(s.equals((String)lm.getElementAt(i))){
                                    jlstandardnames.setSelectedIndex(i);
                                    jtp.setSelectedIndex (0);
                                    break;
                                }
                            }
                       }
                        else{
                            ListModel lm = jlstepnames.getModel();
                            for(int i =0; i < lm.getSize(); i++){
                                if(s.equals((String)lm.getElementAt(i))){
                                    jlstepnames.setSelectedIndex(i);
                                    jtp.setSelectedIndex(jtp.getTabCount()==1?0:1);
                                    break;
                                }
                            }
                        }
                  }
              }
         }));
           stutree.addMouseListener(new java.awt.event.MouseAdapter() {
              public void mouseReleased(MouseEvent e) {
                  TreePath tp = stutree.getSelectionPath();

                  if(tp==null)return;
                  jnode jn = (jnode)tp.getLastPathComponent();
                  int j[] = stutree.getSelectionRows();
                  String newcourse;
                  if(stepactive) newcourse = (String)jlstepnames.getSelectedValue();
                  else newcourse = (String)jlstandardnames.getSelectedValue();
                  if(newcourse==null)return;
                  int listindex = jlstandardnames.getSelectedIndex();
                  program.saveprogram setprogram = null;
                  if(stepactive){
                      setprogram = (program.saveprogram)db.find(teacher.name, newcourse, db.PROGRAM);
                  }
                  else if(standardprograms.size()>0){
                      setprogram = (program.saveprogram)standardprograms.get(listindex);
                  }
                  if(setprogram==null)return;
                  if(dragger.droppedon != null && dragger.droppedon == stutree
                   ) {
                      dragger.droppedon = dragger.draggedfrom = null;
                      jnode jns[] = new jnode[]{};
                      if(jn.type == jnode.GROUP){
                        jnode realch[] = null;
                        for(Enumeration en = jn.depthFirstEnumeration();en.hasMoreElements();) {
                            jnode node = (jnode)en.nextElement();
                            if(node.equals(jn))continue;
                            if(node.type!= jnode.GROUP){
                                if(realch==null)realch = new jnode[]{node};
                                else realch = u.addnode(realch, node);
                            }
                        }
                        jns = u.addnode(jns, realch);
                      }
                      else{
                        jns = u.addnode(jns, jn);
                      }
                      for(int i = 0; i < jns.length; i++){
                          String s = jns[i].get();
                          String user = s;
                          int k;
                          if((k=s.indexOf(topicTree.ISTOPIC))>0){
                            user = s.substring(0, k);
                          }
                          jns[i].setUserObject(user+topicTree.ISTOPIC+newcourse+(stepactive?"{1}":"{0}"));
                          jns[i].setIcon(stepactive?jnode.STEPPEDPROGRAM:jnode.STANDARDPROGRAM);
                          String ss[] = db.list(user, db.PROGRAM);
                          for(int n = 0; n < ss.length; n++){
                            if(ss[n].endsWith("["+teacher.name+"]") || ss[n].endsWith(teacher.name)){
                                db.delete(user,ss[n], db.PROGRAM);
                            }
                          }
                          db.update(user, newcourse+"["+teacher.name+"]", setprogram, db.PROGRAM);
                      }
                      treereload(stutree);
                      stutree.setSelectionRow(j[0]);

                  // warning dialogs if already has set work
               }

               }
              public void mousePressed(MouseEvent e) {
                  complastclicked  = stutree;
              }
           });


        currentnode.setIcon(jnode.MULTISTU);
        currentnode.dontcollapse = true;
        currentnode.okdrag = false;

        student.buildstutree(stutree, currentnode, teacher.students, false, false);

        stumodel = (DefaultTreeModel)stutree.getModel();
        jnode node2 = (jnode)stumodel.getRoot();
        node2.icon =  null;
        standardprograms = new ArrayList();

        for(Enumeration e = node2.depthFirstEnumeration();e.hasMoreElements();) {
            jnode node = (jnode)e.nextElement();
            if(node.equals(node2))continue;
            String psname = program.getProgramName(node.get(), teacher.name);
            if(psname!=null){
                program.saveprogram spret = (program.saveprogram)db.find(node.get(), psname, db.PROGRAM);
                if(spret!=null){
                    String programname = psname;
                    if(psname.indexOf("[")>=0)
                        programname = psname.substring(0, psname.indexOf("["));
                    if(spret.simple){
                        if(u.findString(standardprogs, programname)<0){
                            standardprograms.add(spret);
                            standardprogs = u.addString(standardprogs, programname);
                        }
                    }
                    if(node.type!=jnode.GROUP){
                        String add = (spret.simple ? "{0}":"{1}");
                        node.setUserObject(node.get()+ topicTree.ISTOPIC + programname + add);
                        node.setIcon(spret.simple?jnode.STANDARDPROGRAM:jnode.STEPPEDPROGRAM);
                    }
                }
                else node.setIcon(jnode.BLANK);
            }
            else node.setIcon(jnode.BLANK);
        }
        
        boolean doexit = false;
        if(!showstus && (isstepped && newsteps.length==0 || !isstepped && standardprogs.length==0)){
            doexit = true;
        }
        else if((    standardprogs.length==0 && newsteps.length==0)){
            doexit = true;
        }
        if(doexit){
            u.okmess(shark.programName, u.gettext("adminmanagework","nowork2"), adm);
            this.dispose();
            return;
        }

        jlstandardnames.setListData(standardprogs);
        treereload(stutree);

        }

         jlstandardnames.addMouseListener( new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                complastclicked  = jlstandardnames;
                int sel = jlstandardnames.getSelectedIndex();
                if(sel >= 0) {
                    if(e.getClickCount() == 2) {
                    }
                    else{
                        jnode jns[] = null;
                        for(int i = 0; i < stutree.getRowCount(); i++){
                            TreePath tn = (TreePath)stutree.getPathForRow(i);
                            if(tn!=null){
                               jnode j = (jnode)tn.getLastPathComponent();
                               if(jns == null) jns = new jnode[]{j};
                               else jns = u.addnode(jns, j);                               
                            }
                        }
                    if(jns!=null){
                        
                        jlstandardnames.setEnabled(false);
                        stutree.selectNearestNodes = jns;
                        dragger.startmotion(jlstandardnames, e);
                        }
                    }
                }
            }
            public void mouseReleased(MouseEvent e) {
                if(!isdragging)
           stutree.clearSelection();
                isdragging = false;
                jlstandardnames.setEnabled(true);
                dragger.stopmotion(jlstepnames, e);
            }
        });
         jlstepnames.addMouseListener( new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                complastclicked  = jlstepnames;
                int sel = jlstepnames.getSelectedIndex();
                if(sel >= 0) {
                    if(e.getClickCount() == 2) {
                    }
                    else{
                        jnode jns[] = null;
                        for(int i = 0; i < stutree.getRowCount(); i++){
                            TreePath tn = (TreePath)stutree.getPathForRow(i);
                            if(tn!=null){
                               jnode j = (jnode)tn.getLastPathComponent();
                               if(jns == null) jns = new jnode[]{j};
                               else jns = u.addnode(jns, j);                               
                            }
                        }
                    if(jns!=null){
                        
                        jlstepnames.setEnabled(false);
                        stutree.selectNearestNodes = jns;
                        dragger.startmotion(jlstepnames, e);
                        }
                    }
                }
            }
            public void mouseReleased(MouseEvent e) {
                if(!isdragging)
                   stutree.clearSelection();
                isdragging = false;
               jlstepnames.setEnabled(true);
                dragger.stopmotion(jlstepnames, e);
            }
        });
        jlstepnames.addMouseMotionListener( new MouseMotionAdapter() {
          public void mouseDragged(MouseEvent e) {
              isdragging = true;
           Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(new MouseEvent(dragger, e.getID(), e.getWhen(),
                                           e.getModifiers(), e.getX(), e.getY(),
                                           e.getClickCount(), e.isPopupTrigger(),
                                           e.getButton()));
          }
        });
        jlstandardnames.addMouseMotionListener( new MouseMotionAdapter() {
          public void mouseDragged(MouseEvent e) {
              isdragging = true;
           Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(new MouseEvent(dragger, e.getID(), e.getWhen(),
                                           e.getModifiers(), e.getX(), e.getY(),
                                           e.getClickCount(), e.isPopupTrigger(),
                                           e.getButton()));
          }
        });
        jlstepnames.setCellRenderer(new stujpainter());
        jlstandardnames.setCellRenderer(new stujpainter());



        JPanel hpnstulist = new JPanel(new GridBagLayout());
        hpnstulist.setBackground(cream);
        hpnstulist.setOpaque(true);
        grid.gridx = 0;
        grid.gridy = -1;
        
        grid.insets = new Insets(0,0,0,0);



        JPanel keypanel = new JPanel(new GridBagLayout());
        JLabel keystep = new JLabel(strstepped);
        keystep.setFont(plainfont);
        JLabel keystandard = new JLabel(strstandard);
        keystandard.setFont(plainfont);
        keystep.setIcon(new ImageIcon(sharkStartFrame.publicPathplus + "sprites" +
                                          sharkStartFrame.separator +
                                          "steppedprog.png"));
        keystandard.setIcon(new ImageIcon(sharkStartFrame.publicPathplus + "sprites" +
                                          sharkStartFrame.separator +
                                          "standardprog.png"));

        keypanel.add(keystandard, grid);
        grid.insets = new Insets(margin,0,0,0);
        keypanel.add(keystep, grid);
        grid.insets = new Insets(0,0,0,0);
        removework = u.sharkButton();
        removework.setEnabled(false);
        removework.setText(u.gettext("adminmanagework", "remove"));
        removework.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               jnode jns[];
               if((jns = workToRemove())!=null){
                   removeWork(jns);
               }
          }
        });
        if(!assignview){
            if(isstepped || (showstus&&standardprograms.size()==0) ){
                jlstepnames.setSelectedIndex(0);
                jtp.remove(0);
                stepactive = true;
                setrightpancol(steppedcol);
                if(isstepped)
                    keypanel.setVisible(false);
            }
            else{
                if(!showstus  && !isstepped){
                    if(jtp.getTabCount()>1)jtp.remove(1);
                    if(standardprogs.length==0){
                        u.okmess(shark.programName, u.gettext("adminmanagework", "noexisting"));
                        thisd.dispose();
                        return;
                    }
                }
                stepactive =  false;
                setrightpancol(standardprogcol);
                btsaveas.setEnabled(false);
                btdelete.setEnabled(false);
                keypanel.setVisible(true);
            }
        }
        jtp.setSelectedIndex(0);

        JPanel bpanel = new JPanel(new GridBagLayout());
        grid.gridx = -1;
        grid.gridy = 0;
        grid.insets = new Insets(margin*2,margin,margin*2,margin);
        bpanel.add(keypanel, grid);
        grid.insets = new Insets(0,0,0,0);
        grid.fill = GridBagConstraints.NONE;
        grid.anchor = GridBagConstraints.EAST;
        grid.insets = new Insets(margin*2,0,margin*2,margin);
        if(!assignview)
            bpanel.add(removework, grid);
        grid.insets = new Insets(0,0,0,0);
        grid.fill = GridBagConstraints.BOTH;
        grid.anchor = GridBagConstraints.CENTER;


        grid.gridx = 0;
        grid.gridy = -1;
        grid.insets = new Insets(margin,margin,margin,0);
        String stit;
        if(assignview){
            if(assignmentview == assignment_UNI)
                stit = u.convertToHtml(u.gettext("adminmanagework", "titleassign_uni", shark.programName) + "|&nbsp" );
            else
                stit = u.convertToHtml(u.gettext("adminmanagework", "titleassign_cur") + "|&nbsp" );
        }
        else
            stit = u.convertToHtml(u.gettext("adminmanagework", "titlestus") + "|&nbsp" );
        hpnstulist.add(new JLabel(stit), grid);
        grid.insets = new Insets(0,0,0,0);
        hpnstulist.add(bpanel, grid);
        grid.gridx = 0;
        grid.gridy = -1;
        grid.weighty = 1;
        grid.weighty = 0;
        cpnstulist.add(hpnstulist, grid);
        grid.weighty = 1;
        JScrollPane scroll;
        if(assignview)
            scroll =  new JScrollPane(assignmenttree);
        else    
            scroll =  new JScrollPane(stutree);
        scroll.setBorder(BorderFactory.createEtchedBorder());
        grid.insets = new Insets(0,margin,0,0);
        cpnstulist.add(scroll, grid);
        grid.insets = new Insets(0,0,0,0);
        if(showstus){
            grid.weighty = 0;
            cpnstulist.add(exitbuttons2, grid);
        }

        grid.gridx = -1;
        grid.gridy = 0;
        grid.weighty = 1;
        grid.insets = new Insets(0,0,0,0);

        
        if(assignmenttree!=null){
            if(assignmenttree.getSelectionCount()==0){
                jnode firstleaf = (jnode)((jnode)assignmenttree.getModel().getRoot()).getFirstLeaf();
                if(firstleaf!=null)
                assignmenttree.setSelectionPath(new TreePath(firstleaf.getPath()));
            }
        }
        
        cpnstulist.setBorder(BorderFactory.createLineBorder(Color.gray, 4));
        cpnsteppedlist.setBorder(BorderFactory.createLineBorder(Color.lightGray, 4));
    //   cpnproglist.setBorder(BorderFactory.createLineBorder(Color.darkGray, 4));
        if(showstus)
            pnmain.add(cpnstulist, grid);
        pnmain.add(cpnsteppedlist, grid);
        pnmain.add(cpnproglist, grid);
        grid.insets = new Insets(0,0,0,0);
        getContentPane().add(pnmain, grid);
        dragger = new dragger_base(this, new JComponent[]{jlstepnames, jlstandardnames}, new JComponent[]{stutree});
        dragger.setActiveDestinations(new JComponent[]{stutree});
        dragger.mode = new int[]{dragger.MODE_SELNEARESTDIR};
        setBounds(sharkStartFrame.mainFrame.getBounds());
        this.addComponentListener(new ComponentAdapter() {
          public void componentMoved(ComponentEvent e) {
            removeComponentListener(this);
            setBounds(sharkStartFrame.mainFrame.getBounds());
            validate();
            addComponentListener(this);
          }
        });
        validate();
        
        setEnabled(true);
        setVisible(true);
        requestFocus();
    }
    
    jnode[] workToRemove(){
        TreePath tp = stutree.getSelectionPath();
        if(tp==null) return null;
        jnode jn = (jnode)tp.getLastPathComponent();
//        jnode jns[] = u.addnode(new jnode[]{jn}, jn.getChildren());
        jnode jns[] = u.addnode(new jnode[]{jn}, stutree.getallnodes(jn));
        for(int i = jns.length-1; i >= 0; i--){
            if(jns[i].get().indexOf(topicTree.ISTOPIC)<0)
                jns = u.removeNode(jns, i);
        }
        return jns.length==0?null:jns;
    }    
    
    void removeWork(jnode jns[]){
        if(jns==null)return;
        String mess;
        if(jns.length==1){
            String str_stu = u.gettext("adminmanagework_removewarn", "stu");
            int k;
            String str_stuname = jns[0].get();
            if((k=str_stuname.indexOf(topicTree.ISTOPIC))>=0){
                str_stuname = str_stuname.substring(0, k);
            }
            str_stu = str_stu.replaceFirst("%", str_stuname);
            mess = str_stu;
        }
        else{
            String str_group = u.gettext("adminmanagework_removewarn", "group");
            String str_all = u.gettext("adminmanagework_removewarn", "all");
            TreePath tp = stutree.getSelectionPath();
            if(tp==null)return;
            jnode jn = (jnode)tp.getLastPathComponent();           
            mess = jn.equals(stutree.getModel().getRoot()) ?str_all:str_group;
        }
        if(!u.yesnomess(u.gettext("adminmanagework_removewarn", "title"), mess, thisd)){
            return;
        }
        for(int i = 0; i < jns.length; i++){
               String s = jns[i].get();
               String user = s;
               int k;
               if((k=s.indexOf(topicTree.ISTOPIC))>0){
                user = s.substring(0, k);
               }
               jns[i].setUserObject(user);
               jns[i].setIcon(jnode.BLANK);
               String ss[] = db.list(user, db.PROGRAM);
               for(int n = 0; n < ss.length; n++){
//                    if(ss[n].endsWith("["+teacher.name+"]") || ss[n].endsWith(teacher.name)){
                    if(u.CaseInsensitiveEndsWith(ss[n], "["+teacher.name+"]") || 
                            u.CaseInsensitiveEndsWith(ss[n], teacher.name)){
                        db.delete(user,ss[n], db.PROGRAM);
                    }
               }              
        }
        treereload(stutree); 
    }    

    void setprogdetails(boolean on){
        lbWorkTitle.setVisible(on);
        colpnproglist.setVisible(on);
        colpnproglist_blank.setVisible(!on);
    }

    void standardchanged(JList jl){
                int progindex = jl.getSelectedIndex();
                String progname = (String)jl.getSelectedValue();
                btedit.setEnabled(progname!=null);
                btrename.setEnabled(progname!=null);  
                btsaveas.setEnabled(stepactive && progname!=null);
                btdelete.setEnabled(stepactive && progname!=null);
                btnew.setEnabled(stepactive);
                if(progname==null){
                    return;
                }
                currprogram = (program.saveprogram)standardprograms.get(progindex);
                if(currprogram==null)return;
                jltopics.setListData(getTopicList(currprogram.it[0]));
                cbphonics.setSelected(currprogram.it[0].phonics);
                lballgames.setVisible(currprogram.it[0].games.length==0);
                panelgames.setVisible(currprogram.it[0].games.length!=0);
                jlgames.setListData(currprogram.it[0].games);
    }
    
    String[] getTopicList(program.programitem pi){
         String ss[] = new String[]{};
         for(int p = 0; pi!=null && pi.topics!=null && p < pi.topics.length; p++){
              String s = pi.topics[p];
              ss = u.addString(ss,  s.substring(s.lastIndexOf(topicTree.CSEPARATOR)+1) + 
                      (pi.mixed != null && pi.mixed[p]?"  "+strmixedlists:""));
         }    
         return ss;
    }    

    void stepchanged(JList jl){
           String progname = (String)jl.getSelectedValue();
                btedit.setEnabled(progname!=null);
                btrename.setEnabled(progname!=null);
                 btsaveas.setEnabled(stepactive && progname!=null);
                btdelete.setEnabled(stepactive && progname!=null);
                btnew.setEnabled(stepactive);
                if(progname==null){
                    return;
                }
                currprogram = (program.saveprogram)db.find(teacher.name, progname, db.PROGRAM);
                String ss[] = new String[]{};
                for(int i =0; i < currprogram.it.length; i++){
                    ss = u.addString(ss, stepitem+" "+String.valueOf(i+1));
                }
                jlsteps.setListData(ss);
                jlsteps.setSelectedIndex(0);
               
    }


    void setrightpancol(Color col){
        lbtopics.setBackground(col);
        lbgames.setBackground(col);
        lbsteps.setBackground(col);
        cpnproglist.setBackground(col);
        pnstep.setVisible(stepactive);
        cbphonics.setVisible(!inactivecol.equals(col));
        if(inactivecol.equals(col)){
            jltopics.setListData(new String[]{});
            jlsteps.setListData(new String[]{});
            jlgames.setListData(new String[]{});
        }
    }

    void setassignments(program.saveprogram sp){
        if(sp==null)return;
        Color col = sp.simple?standardprogcol:steppedcol;
        lbtopics.setBackground(col);
        lbgames.setBackground(col);
        lbsteps.setBackground(col);
        cpnproglist.setBackground(col);
        pnstep.setVisible(!sp.simple);
        cbphonics.setVisible(true);
        currprogram = sp;
        String ss[];
        lballgames.setVisible(sp.it[0].games.length==0);
        if(!sp.simple){
            ss = new String[]{};
            for(int i =0; i < sp.it.length; i++){
                ss = u.addString(ss, stepitem+" "+String.valueOf(i+1));
            }
            jlsteps.setListData(ss);
            jlsteps.setSelectedIndex(0);
        }
        else{
            jltopics.setListData(getTopicList(sp.it[0]));
            cbphonics.setSelected(sp.it[0].phonics);
            panelgames.setVisible(sp.it[0].games.length!=0);
            jlgames.setListData(sp.it[0].games);
        }
    }


void treereload(JTree tree){
    DefaultTreeModel model = (DefaultTreeModel)tree.getModel();
    model.reload();
    expandAll( tree, (TreeNode)model.getRoot(), new TreePath((TreeNode)model.getRoot()));
    
}

void expandAll( JTree tree, TreeNode node, TreePath path ) {
    tree.expandPath( path );
    int i = node.getChildCount( );
    for ( int j = 0; j< i; j++ ) {
        TreeNode child = node.getChildAt( j );
        expandAll( tree, child , path.pathByAddingChild( child ) );
    }
}


  public static class stujpainter extends JLabel implements ListCellRenderer {  // for student list
     stujpainter() {setOpaque(true);}
     String o;
     FontMetrics m;
     public Component getListCellRendererComponent( JList list,Object oo,
           int index,boolean isSelected,boolean cellhasFocus) {
        o = (String)oo;

        Color defaultcol = (stepactive?steppedcol:standardprogcol);
        this.setBackground(isSelected?list.getSelectionBackground():Color.white);
        if(o.startsWith(topicTree.ISPATH)) {
          setForeground(Color.red);
          setText(u.stringreplace(o.substring(1),topicTree.ISPATH.charAt(0),'/'));
        }
        else {
          setForeground(defaultcol);
          setText(o);
        }
        if(!list.isEnabled()){
          setForeground(Color.lightGray);
        }
        setForeground(defaultcol);
        return this;
     }
  }

    class itempainter extends JLabel implements ListCellRenderer {
     itempainter() {setOpaque(true);}
     Object o;
     FontMetrics m;
     public Component getListCellRendererComponent( JList list,Object oo,
           int index,boolean isSelected,boolean cellhasFocus) {
        o = oo;
  //      this.setBackground(isSelected?jlgames.getSelectionBackground():Color.white);
        String s = (String)o;
        int n;
        if((n=s.indexOf('@'))>=0)s = s.substring(0,n);
        setText(s);
        return this;
     }
  }

}