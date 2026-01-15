/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package shark;

import java.io.File;
import java.util.Calendar;

/**
 *
 * @author Paul
 */
public class java7_base {
    
  public static String GetMins(File f){                
    try{            
        java.nio.file.Path p = java.nio.file.Paths.get(f.getAbsolutePath());
        java.nio.file.attribute.BasicFileAttributes view
           = java.nio.file.Files.getFileAttributeView(p, java.nio.file.attribute.BasicFileAttributeView.class).readAttributes();
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(view.creationTime().toMillis());
        String min = String.valueOf(cal.get(Calendar.MINUTE));
        while (min.length()<2)min = "0"+min; 
        return min;
    }
    catch(Exception ee){}
    return null;
 }       
}
