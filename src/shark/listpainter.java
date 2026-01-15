/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package shark;

import java.awt.*;
import javax.swing.*;
import java.lang.ref.SoftReference.*;
/**
 *
 * @author White Space
 */
     public class listpainter extends DefaultListCellRenderer {
        ImageIcon imageIconDisabled=null;
        Image imageDisabled=null;
        final ImageIcon imageIcon;
        Image image;
        Dimension dimension;
        ImageIcon finalIcon;
        ImageIcon finalIconDisabled;
        public int disableditems[] =null;
        String exception[];
        double lasth = -1;
        double hh=0;
        double ww=0;  
        
        public listpainter(ImageIcon ii) {
            super();
            imageIcon = ii;
            image = imageIcon.getImage();
        }
        public listpainter(ImageIcon ii, ImageIcon iiDisabled, String exceptions[]) {
            super();
            imageIcon = ii;
            exception = exceptions;
            image = imageIcon.getImage();
            imageIconDisabled = iiDisabled;
            imageDisabled = imageIconDisabled.getImage();
        }


	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            dimension = this.getPreferredSize();
            if(lasth!=dimension.height){
                hh = dimension.getHeight();
                ww = (hh / imageIcon.getIconHeight()) * imageIcon.getIconWidth();
                image = image.getScaledInstance((int)ww, (int)hh, Image.SCALE_SMOOTH);
                lasth = dimension.height;
                finalIcon =  new ImageIcon(image);
                if(disableditems!=null){
                    if(u.inlist(disableditems, index)){
                        imageDisabled = imageDisabled.getScaledInstance((int)ww, (int)hh, Image.SCALE_SMOOTH);
                        finalIconDisabled =  new ImageIcon(imageDisabled);
                    }
                }
            }
            if(u.findString(exception, (String)value)>=0){
                setIcon(null);
                return this;
            }
            else{
                setIcon(finalIcon);
            }
            if(disableditems!=null){
                if(u.inlist(disableditems, index)){
                    setForeground(Color.gray);
                    setIcon(finalIconDisabled);
                }
            }
            return this;
	}
}
