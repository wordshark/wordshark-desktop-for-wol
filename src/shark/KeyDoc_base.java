/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package shark;

import java.lang.ref.SoftReference.*;
import javax.swing.text.*;
import javax.swing.*;
import java.lang.ref.SoftReference.*;
/**
 *
 * @author MacBook Air
 */
  class KeyDoc_base extends PlainDocument {
     int maxchars;
     
     static final int MAXNAMECHARS = 30;
     static final int MAXNAMECHARSPASSHINT = 55;
     
     
     KeyDoc_base(int max) {
       super();
       maxchars=max;
     }
     
     public void insertString(int o, String s, AttributeSet a) {
       if(o>=maxchars){noise.beep(); return;}
       int i = u.scanfor(s,u.notAllowedInFileNames);
       if(i>=0) {noise.beep(); return;}
       try{super.insertString(o,s,a);}
       catch(BadLocationException e) {}
     }
   }
