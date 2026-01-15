package shark;
import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.*;

public class chooseBackground_base extends chooser_base {
   int w = sharkStartFrame.screenSize.width/20 ;
   int h = sharkStartFrame.screenSize.width/20;

   Vector ims = new Vector();
   String imsname[]= new String[0];
   JDialog parentdia;
   JFrame parentframe;
   ArrayList cols;
   ArrayList names;
   int custom;
   int none;


    public chooseBackground_base (JDialog owner) {
       super(u.gettext("chooseicon","heading"), owner);
       parentdia = owner;
       addConstructor();
    }

    public chooseBackground_base (JFrame owner) {
       super(u.gettext("chooseicon","heading"), owner);
       parentframe = owner;
       addConstructor();
    }

     private void addConstructor(){
        this.setTitle(u.gettext("chooseavatar", "title"));
        this.setModal(true);
     this.addWindowListener(new java.awt.event.WindowAdapter() {
       public void windowClosing(WindowEvent e) {
           super.windowClosing(e);
           noneselected();
       }
        public void windowDeactivated(WindowEvent e) {
            super.windowDeactivated(e);

        }
     });
        int count = 0;
        String ptext = u.gettext("bgcolor", "colour"+String.valueOf(count));
        cols = new ArrayList();
        names = new ArrayList();

        while(ptext!=null){
           String s[] = u.splitString(ptext);
  //         Color tcol = new Color(Integer.parseInt(s[1]), Integer.parseInt(s[2]), Integer.parseInt(s[3]));
           Color tcol = u.parseStringtoColor(s[1]);
  //         add(s[0],tcol,w,h);
           if(tcol.equals(Color.white))
               tcol = sharkStartFrame.defaultbg;
           cols.add(tcol);
           names.add(s[0]);
           count++;
           ptext = u.gettext("bgcolor", "colour"+String.valueOf(count));
        }        
        String longest = "";
        for(int i = 0; i < names.size(); i++){
            String s = (String)names.get(i);
            if(s.length()>longest.length())longest = s;
        }
        for(int i = 0; i < names.size(); i++){
            add((String)names.get(i),(Color)cols.get(i),w,h, longest);
        }
        custom = cols.size()-2;
        none = cols.size()-1;
       showit();
     }

   public void selected(int item) {
       Color newColor = null;
       if(item==custom){
          newColor = JColorChooser.showDialog(sharkStartFrame.mainFrame,
              u.gettext("bgcolor", "label"),
              Color.white);
       }
       else if(item==none){
          newColor = null;
       }
       else{
          newColor = (Color)cols.get(item);
       }
       if (newColor != null) {
//startPR2013-02-20^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          sharkStartFrame.mainFrame.wordTree.setBackground(newColor);
          wordlist.bgcoloruse = newColor;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         student.setOption("bgcolor", String.valueOf(newColor.getRGB()));
       }
       else {
           noneselected();
       }
      mainPanel.stop();
      dispose();
   }
   
   void noneselected(){
         sharkStartFrame.mainFrame.setBgMenuSelected(false);
         sharkStartFrame.mainFrame.wordTree.setBackground(wordlist.wlbgcol);
         wordlist.bgcoloruse = wordlist.wlbgcol;
         student.removeOption("bgcolor");       
   }
}
