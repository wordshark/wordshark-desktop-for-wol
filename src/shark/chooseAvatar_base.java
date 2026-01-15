package shark;
import java.awt.*;
import javax.swing.*;
import java.util.*;


public class chooseAvatar_base extends chooser_base {
   int w = sharkStartFrame.screenSize.width/20 ;
   int h = sharkStartFrame.screenSize.width/20;

   Vector ims = new Vector();
   String imsname[]= new String[0];
   JDialog parentdia;
   JFrame parentframe;

    public chooseAvatar_base (JDialog owner) {
       super(u.gettext("chooseicon","heading"), owner);
       parentdia = owner;
       addConstructor();
    }

    public chooseAvatar_base (JFrame owner) {
       super(u.gettext("chooseicon","heading"), owner);
       parentframe = owner;
       addConstructor();
    }

     private void addConstructor(){
         this.setTitle(u.gettext("chooseavatar", "title"));
        String ss[] = db.list(sharkStartFrame.publicAvatarLib, db.PICTURE);
        byte buf[];
        for(int i=0;i<ss.length;++i) {
            if ((buf = (byte[]) db.find(sharkStartFrame.publicAvatarLib, ss[i], db.PICTURE)) != null) {
                 Image im = sharkStartFrame.t.createImage(buf);
                 ims.add(buf);
                 imsname = u.addString(imsname, ss[i]);
                 add(im,ss[i],w,h);
               }
       }
       showit();
     }

   public void selected(int item) {
      if(parentdia!=null)
        ((admin)parentdia).iconchosen((byte[])ims.get(item), imsname[item]);
      else if(parentframe !=null){
        ((PickPicture)parentframe).iconchosen((byte[])ims.get(item), imsname[item]);
      }
      mainPanel.stop();
      dispose();
   }
}
