/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package shark;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.Timer;

/**
 *
 * @author White Space
 */
public class CountdownDialog_base extends JDialog {

    String s1 = u.gettext("countdown","messclose_p");
    String s2 = u.gettext("countdown","messclose_s");
    JLabel countlabel;
    int countdown = 15;
    Timer testTimer;
    int border = 5;

    public CountdownDialog_base(String strmess1, String strmess2){
        super();
        int width = sharkStartFrame.mainFrame.getWidth()*9/20;
        int height = sharkStartFrame.mainFrame.getHeight()*3/10;
//        setBounds(
//                (sharkStartFrame.mainFrame.getWidth()-width)/2,
//                (sharkStartFrame.mainFrame.getHeight()-height)/2,
//                width,
//                height);
        setBounds(u2_base.adjustBounds(new Rectangle((sharkStartFrame.mainFrame.getWidth()-width)/2,
                (sharkStartFrame.mainFrame.getHeight()-height)/2,
                width,
                height)));
        
        getContentPane().setLayout(new GridBagLayout());
        GridBagConstraints grid = new GridBagConstraints();
        setTitle(u.gettext("patchmess","heading"));
        countlabel = new JLabel((s1.replaceAll("%", String.valueOf(countdown))));
        testTimer = (new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(countdown==0){
                    System.exit(0);
                }
                else if(countdown==1) countlabel.setText(s2.replaceAll("%", String.valueOf(countdown)));
                else countlabel.setText(s1.replaceAll("%", String.valueOf(countdown)));
                countdown--;
            }
        }));
        testTimer.setRepeats(true);
        testTimer.start();
        JButton yes = new JButton(u.gettext("yes","label"));
        JButton no = new JButton(u.gettext("no","label"));
        grid.weighty = 0;
        grid.weightx = 1;
        grid.gridx = 0;
        grid.gridy = -1;
        JPanel messpan = new JPanel(new GridBagLayout());
        messpan.add(new JLabel(strmess1), grid);
        messpan.add(new JLabel(strmess2), grid);
        grid.gridx = -1;
        grid.gridy = 0;
        grid.weighty = 1;
        JPanel butpan = new JPanel(new GridBagLayout());
        grid.insets = new Insets(border, border, border, border);
        if(shark.macOS){
            butpan.add(no, grid);
            butpan.add(yes, grid);
        }
        else{
            butpan.add(yes, grid);
            butpan.add(no, grid);
        }
        yes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                u.launchWebSite("www.wordshark.net/Patching/"+shark.ACTIVATE_PREFIX+shark.licenceType+"Patch.aspx");
                System.exit(0);
            }
        });
        no.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        grid.gridx = 0;
        grid.gridy = -1;
        grid.weighty = 1;
        grid.insets = new Insets(border, border, border, border);
        getContentPane().add(messpan, grid);
        grid.insets = new Insets(border, border, 0, border);
        getContentPane().add(butpan, grid);
        grid.insets = new Insets(border, border, border, border);
        getContentPane().add(countlabel, grid);
        this.getRootPane().setDefaultButton(no);
        setModal(true);
        setVisible(true);
    }
}
