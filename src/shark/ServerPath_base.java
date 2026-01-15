package shark;

import java.io.*;
import javax.swing.*;

public class ServerPath_base {
  int attempt = 0;

  public ServerPath_base(String resource, String outputPath) {
    File f;
    String outputFile = outputPath.concat(resource);
    while(attempt<2){
      try {
        if (! (f = new File(outputPath)).exists()) {
          f.mkdirs();
        }
        if (!(f = new File(outputFile)).exists()) {
          InputStream input = getClass().getResourceAsStream("/" + resource);
          OutputStream output = new FileOutputStream(outputFile);
          try {
//startPR2010-04-25^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//            String cmds[] = new String[] {"chmod", "666", f.getAbsolutePath()};
              String chstr;
              if(f.isDirectory())chstr = "777";
              else chstr = "666";
              String cmds[] = new String[] {"chmod", chstr, f.getAbsolutePath()};
//endPRtoupdate^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            Runtime.getRuntime().exec( (cmds));
          } catch (Exception e) {}
          byte[] temp = new byte[128];
          while (input.available() > 0) {
            int nb = input.read(temp);
            output.write(temp, 0, nb);
          }
          input.close();
          output.close();
        }
      } catch (Exception e) {
        if(attempt>0){
//          JOptionPane.showMessageDialog(null, new String[] {"Error code 13: cannot create " + resource,
//                                        "Please ensure that you have Read and Write permission to the following folder:",
//                                        outputPath}, shark.programName, JOptionPane.INFORMATION_MESSAGE);
          OptionPane_base.getErrorMessageDialog(null, 13, "Cannot create " + resource+"|"+"Please ensure that you have Read and Write permission to the following folder:|"+outputPath, OptionPane_base.ERRORTYPE_EXIT);
        }
      }
      attempt++;
    }
  }
}
