/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package shark;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
/**
 *
 * @author Paul Rubie
 */
public class TutorialChoice_base extends JDialog{
    String filepath[];
    String texts[];
    ArrayList a;
    JDialog thisd = this;
    boolean showclose = true;
    static boolean running = false;
    boolean alwaysShow = false;
    static long launchedAt;
    
    public TutorialChoice_base(JDialog jd, String[] descriptions, String[] launchpaths){
      super(jd);
      texts = descriptions;
      filepath = launchpaths;
      init();
    }
    public TutorialChoice_base(JFrame jf, String[] descriptions, String[] launchpaths){
      super(jf);
      texts = descriptions;
      filepath = launchpaths;
      init();
    }
    public TutorialChoice_base(JFrame jf, String[] descriptions, String[] launchpaths, boolean displayClose, boolean alwaysshow){
      super(jf);
      alwaysShow = alwaysshow;
      texts = descriptions;
      filepath = launchpaths;
      showclose = displayClose;
      init();
    }
    void init(){
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setModal(true);
  //      this.addWindowListener(new java.awt.event.WindowAdapter() {
  //        public void windowDeactivated(WindowEvent e) {
  //             thisd.dispose();
  //        }
  //      });
        setTitle(u.gettext("videotutorials", "title"));
        if(running || (System.currentTimeMillis()<launchedAt+1000)){
            this.dispose();
            return;
        }
        launchedAt = System.currentTimeMillis();
        if(!alwaysShow && texts.length==1){
            u.launchWebSite(filepath[0]);
            this.dispose();
            return;
        }
        
        
        running = true;
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                running = false;
            }
            public void windowClosed(WindowEvent e) {
                running = false;
            }
        });        
        setResizable(false);
        int w = sharkStartFrame.mainFrame.getSize().width;
        int h = sharkStartFrame.mainFrame.getSize().height;
        int w1 = w*1/2;
        int h1 = h*1/2;
        int sw = sharkStartFrame.mainFrame.getWidth();
        int buttondim = (sw*14/22)/24;
        int buttonimdim =  buttondim- (buttondim/5);
        Image im = Toolkit.getDefaultToolkit().getImage(sharkStartFrame.publicPathplus + "sprites" +
                                           sharkStartFrame.separator +
                                           "video_il48.png");
        a = new ArrayList();
        for(int vidno = 0; texts!=null && vidno < texts.length; vidno++){
             JButton but_vids = u.sharkButton();
             but_vids.setPreferredSize(new Dimension(buttondim, buttondim));
             but_vids.setMinimumSize(new Dimension(buttondim, buttondim));
             but_vids.addActionListener(new ActionListener() {
               public void actionPerformed(ActionEvent e) {
                   for(int i = 0; i < a.size(); i++){
                       if(e.getSource().equals(a.get(i))){
//                           u.launchFile(filepath[i], true);
                           u.launchWebSite(filepath[i]);
                           if(texts.length==1)thisd.dispose();
                           break;
                       }
                   }  
               }
             });
             but_vids.setIcon(new ImageIcon(im.getScaledInstance(buttonimdim,
                buttonimdim, Image.SCALE_SMOOTH)));
             but_vids.setOpaque(false);
             a.add(vidno, but_vids);
        }
//        this.setBounds((w-w1)/2, (h-h1)/2, w1,h1);
        this.setBounds(u2_base.adjustBounds(new Rectangle((w-w1)/2, (h-h1)/2, w1,h1)));
        this.getContentPane().setLayout(new GridBagLayout());
        this.getContentPane().setBackground(Color.white);
        GridBagConstraints grid = new GridBagConstraints();

        grid.weightx = 0;
        grid.weighty = 0;
        int y1 = 0;
        grid.gridy = y1;
        JPanel pn = new JPanel(new GridBagLayout());
        pn.setOpaque(false);
        grid.fill = GridBagConstraints.BOTH;
        for(int i = 0; texts!=null && i < a.size(); i++){
            int bottominset = 20;
            grid.insets = new Insets(0,0,bottominset,10);
            JButton jb = (JButton)a.get(i);
            grid.gridx = 0;
            grid.fill = GridBagConstraints.NONE;
            pn.add(jb, grid);
            grid.fill = GridBagConstraints.BOTH;
            grid.gridx = 1;
            grid.insets = new Insets(0,0,10,0);
            pn.add(new JLabel(texts[i]), grid);
            grid.gridy = ++y1;
        }

        grid.weighty = 1;
        JPanel buttonpanel = new JPanel(new GridBagLayout());
        buttonpanel.setOpaque(false);
        JButton close = u.sharkButton();
        close.setText(u.gettext("close", "label"));
        close.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                thisd.dispose();
            }
        });
        close.setOpaque(false);
        buttonpanel.add(close, grid);
        grid.gridx = 0;
        grid.gridy = -1;
        grid.weighty = 1;
        this.getContentPane().add(pn, grid);
        grid.weighty = 0;
        if(showclose)         
            this.getContentPane().add(buttonpanel, grid);
        setVisible(true);
    }
}
