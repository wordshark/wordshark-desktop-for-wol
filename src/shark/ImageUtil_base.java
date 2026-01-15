/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package shark;

import java.awt.*;
import java.awt.image.*;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.*;
import java.util.*;
import java.io.*;
import javax.media.jai.*;
/**
 *
 * @author MacBook Air
 */
public class ImageUtil_base {

    // between 0 (poor) and 1 (good)
    float jpgQuality = 0.3f;
    
    public byte[] compressToBytes(File f){
        try{
          int maxDim = 600;
          RenderedOp source2 = JAI.create("fileload", f.getAbsolutePath());
          float w = source2.getWidth();
          int h = source2.getHeight();
          BufferedImage bi = source2.getAsBufferedImage();
          float wid = (float) w / h;
          float hei = (float) h / w;
          Image photo;
          if (h > w) {
            photo = bi.getScaledInstance( (int) (maxDim / hei), maxDim,
                                         Image.SCALE_SMOOTH);
          }
          else {
            photo = bi.getScaledInstance(maxDim, (int) (maxDim / wid),
                                         Image.SCALE_SMOOTH);
          }
          BufferedImage b = new BufferedImage(photo.getWidth(null),
                                                photo.getHeight(null),
                                                BufferedImage.TYPE_INT_RGB);
          Graphics2D g2 = b.createGraphics();
          g2.drawImage(photo, null, null);
          ByteArrayOutputStream osByteArray = new ByteArrayOutputStream();
          ImageOutputStream outputStream = ImageIO.createImageOutputStream(osByteArray);
          ImageWriter writer = null;
          Iterator iter = ImageIO.getImageWritersByFormatName("jpg");
          if (iter.hasNext()) {
            writer = (ImageWriter)iter.next();
          }
          ImageWriteParam iwparam = new JPEGImageWriteParam(Locale.getDefault()) {
             public void setCompressionQuality(float quality) {
                if (quality < 0.0F || quality > 1.0F) {
                    throw new IllegalArgumentException("Quality out-of-bounds!");
                }
                this.compressionQuality = quality;
            } 
          };
          iwparam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT) ;
          iwparam.setCompressionQuality(jpgQuality);
          writer.setOutput(outputStream);
          writer.write(null, new IIOImage(b, null, null), iwparam);
          outputStream.flush();
          outputStream.close();
          bi.flush();
          return osByteArray.toByteArray();
        }
        catch(Exception e){}
        return null;
    }
    
}
