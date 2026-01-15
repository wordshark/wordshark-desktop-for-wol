package shark;
import java.io.*;
//startPR2004-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
import javax.swing.*;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR

public class chooseSprite_base extends chooser_base {
   int w = sharkStartFrame.screenSize.width/20 ;
   int h = sharkStartFrame.screenSize.width/20;
   String spriteDir[]= new String[0];
   String spriteName[]= new String[0], s;

//startPR2004-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    public chooseSprite_base () {
       super(u.gettext("choosesprite","heading"));
       addConstructor();
    }
    public chooseSprite_base (JFrame owner) {
       super(u.gettext("choosesprite","heading"), owner);
       addConstructor();

    }
    public chooseSprite_base (JDialog owner) {
       super(u.gettext("choosesprite","heading"), owner);
       addConstructor();
    }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2004-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     private void addConstructor(){
       String s[];
       File d;
       short i,j=0,k;

       if(sharkStartFrame.currStudent>=0) {
          s = db.list(sharkStartFrame.studentList[sharkStartFrame.currStudent].name,db.IMAGE,"x");
          for(j= 0; j < s.length; ++j) {
             spriteDir = u.addString(spriteDir,sharkStartFrame.studentList[sharkStartFrame.currStudent].name);
             spriteName = u.addString(spriteName, s[j]);
          }
       }
       for(i=0; i<sharkStartFrame.publicImageLib.length;++i)  {
          s = db.list(sharkStartFrame.publicImageLib[i],db.IMAGE,"x_");
          loop: for(j= 0; j < s.length; ++j) {
             for(k=0;k<spriteName.length;++k)
                if(spriteName[k].equalsIgnoreCase(s[j])) continue loop;
             spriteDir = u.addString(spriteDir,sharkStartFrame.publicImageLib[i]);
             spriteName = u.addString(spriteName, s[j]);
          }
       }
       for(i=0;i<spriteDir.length;++i) {
           add(spriteDir[i],spriteName[i],w,h);
       }
       showit();
     }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   //---------------------------------------------------------------------
   static class selectsprite implements FilenameFilter {
      public boolean accept(File dir, String name) {
         return name.length() > 6
            && name.substring(name.length() - 4).equalsIgnoreCase(".gif");
      }
   }
   //------------------------------------------------------------
   public void selected(int item) {
      if(sharkStartFrame.currStudent>=0) {
          sharkStartFrame.studentList[sharkStartFrame.currStudent].spritename = spriteName[item];
      }
      mainPanel.stop();
      dispose();
   }
}
