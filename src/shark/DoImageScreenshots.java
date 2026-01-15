/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shark;


import javax.swing.Timer;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.util.Calendar;
import javax.imageio.*;
import javax.imageio.metadata.*;
import javax.imageio.stream.*;
import java.awt.image.*;
import java.util.Iterator;
import java.io.IOException;
import javax.imageio.ImageIO;

import java.io.File;



/**
 *
 * @author paulr
 */
  public  class DoImageScreenshots implements Runnable{
     
      // 529 x 454 pixels
      // background colour F3F3F3
      static int gifFrameRate = 120;
      static int timeFactor = 17;
      static int minFrames = 16;
      static int maxGifLength = 7000;
      static int workingFramerate;
      
      
      static int forceImLibrary = -1;

      sharkImage im;
      static String  sname;
      static String  soriname;
      static String  sdatabase;
      sharkGame.showpicture sp;
      runMovers rm;
      Rectangle rect;
 //     SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd hh mm ss a");
 //     boolean complete = false;
 //     long maxend;
      int counter = 1;
      Calendar now = Calendar.getInstance();
      boolean running = true;
      Timer tnTimer;
      
      static String logFile = "D:\\WordsharkImageScreenshots\\log.txt";
      static String mainFolder = "D:\\WordsharkImageScreenshots";
      
      static String toDoIWSJsonPath = sharkStartFrame.sharedPathplus+ToolsOnlineResources.outputFolder+shark.sep+"Create_Needed_IWS"+ToolsOnlineResources.jsonext; 
      static String toDoIPHOTOJsonPath = sharkStartFrame.sharedPathplus+ToolsOnlineResources.outputFolder+shark.sep+"Create_Needed_IPHOTO"+ToolsOnlineResources.jsonext; 
 //     static String mainFolder = "C:\\Shark Screen Shots";
 //     static String workingFolder = "Stage 1";
   //   static String resultFolder = "Stage 2";
      static String fileStillExtension = "png";
      static String fileAniExtension = "gif";      
      
      
      public DoImageScreenshots(String db, String name, sharkGame.showpicture si, runMovers runm, Rectangle r){
          soriname = name;
          sdatabase = db;
          ToolsOnlineResources tor = new ToolsOnlineResources();
          sname = tor.charToAscii(name);
          sp = si;
          im = sp.im1;
          rm = runm;
          rect = r; 
    }
      

      
    public void run(){
        long next = 0;
        java.lang.ThreadGroup tg = new java.lang.ThreadGroup("ParentThreadGroup"); 
        boolean doneonce = false;  
        do {
     
            long l = System.currentTimeMillis();
            if(l>=next && (im.isanimated || !doneonce) && rm.isMover(sp)){
                try{
                    Thread imThread = new Thread(tg, new doImage2(sname, rect, im.isanimated?counter++:-1, rm));
                    imThread.start();
                }
                catch(Exception g){} 
                next = l + workingFramerate;
            }     
                    
            doneonce = true;
                    
        }
        while(((rm.isMover(sp) && im.isanimated)||tg.activeCount()>0));
        if(!im.isanimated)
            im.done = true;
        int g;
        g =9;
    }  
    
    
    
    
    
  class doImage2 implements Runnable{
      
      String sname;
      Rectangle rect;
 //     SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd hh mm ss a");
      int counter;
      Robot robot = null;
      Calendar now = Calendar.getInstance();
      runMovers panel;
 //     static String mainFolder = "C:\\Users\\paulr\\Documents\\Shark Screen Shots";


      
      public doImage2(String name, Rectangle r, int count, runMovers rm){
          sname = name;
          counter = count;
          rect = r;
          panel = rm;
          
      }
      
      public void run(){
          try{
              robot = new Robot();
              if(panel.mtot> 0){
                BufferedImage screenShot = robot.createScreenCapture(rect);
     //           sname = sname.replace("?", "");
                String folder = mainFolder+"\\";
                new File(folder).mkdirs();
                ImageIO.write(screenShot, fileStillExtension, new File(folder+sname+(counter>=0?("_"+getStringNo(counter)):"") + ".png"));
      //          ImageIO.write(screenShot, fileStillExtension, new File(folder+sname+ ".png"));
                 
              }
          }
          catch(Exception g){
               int gg;
                 gg = 8;          
          }
      }
      
      private String getStringNo(int c){
          String s = String.valueOf(c);
          while(s.length() < 4){
              s = "0"+s;
          }
          return s;
      }
}  
  
  
  
class GifSequenceWriter {
  protected ImageWriter gifWriter;
  protected ImageWriteParam imageWriteParam;
  protected IIOMetadata imageMetaData;
  
  /**
   * Creates a new GifSequenceWriter
   * 
   * @param outputStream the ImageOutputStream to be written to
   * @param imageType one of the imageTypes specified in BufferedImage
   * @param timeBetweenFramesMS the time between frames in miliseconds
   * @param loopContinuously wether the gif should loop repeatedly
   * @throws IIOException if no gif ImageWriters are found
   *
   * @author Elliot Kroo (elliot[at]kroo[dot]net)
   */
  public GifSequenceWriter(
      ImageOutputStream outputStream,
      int imageType,
      int timeBetweenFramesMS,
      boolean loopContinuously) throws IIOException, IOException {
    // my method to create a writer
    gifWriter = getWriter(); 
    imageWriteParam = gifWriter.getDefaultWriteParam();
    ImageTypeSpecifier imageTypeSpecifier =
      ImageTypeSpecifier.createFromBufferedImageType(imageType);

    imageMetaData =
      gifWriter.getDefaultImageMetadata(imageTypeSpecifier,
      imageWriteParam);

    String metaFormatName = imageMetaData.getNativeMetadataFormatName();

    IIOMetadataNode root = (IIOMetadataNode)
      imageMetaData.getAsTree(metaFormatName);

    IIOMetadataNode graphicsControlExtensionNode = getNode(
      root,
      "GraphicControlExtension");

    graphicsControlExtensionNode.setAttribute("disposalMethod", "none");
    graphicsControlExtensionNode.setAttribute("userInputFlag", "FALSE");
    graphicsControlExtensionNode.setAttribute(
      "transparentColorFlag",
      "FALSE");
    graphicsControlExtensionNode.setAttribute(
      "delayTime",
      Integer.toString(timeBetweenFramesMS / 10));
    graphicsControlExtensionNode.setAttribute(
      "transparentColorIndex",
      "0");

    IIOMetadataNode commentsNode = getNode(root, "CommentExtensions");
    commentsNode.setAttribute("CommentExtension", "Created by MAH");

    IIOMetadataNode appEntensionsNode = getNode(
      root,
      "ApplicationExtensions");

    IIOMetadataNode child = new IIOMetadataNode("ApplicationExtension");

    child.setAttribute("applicationID", "NETSCAPE");
    child.setAttribute("authenticationCode", "2.0");

    int loop = loopContinuously ? 0 : 1;

    child.setUserObject(new byte[]{ 0x1, (byte) (loop & 0xFF), (byte)
      ((loop >> 8) & 0xFF)});
    appEntensionsNode.appendChild(child);

    imageMetaData.setFromTree(metaFormatName, root);

    gifWriter.setOutput(outputStream);

    gifWriter.prepareWriteSequence(null);
  }
  
  public void writeToSequence(RenderedImage img) throws IOException {
    gifWriter.writeToSequence(
      new IIOImage(
        img,
        null,
        imageMetaData),
      imageWriteParam);
  }
  
  /**
   * Close this GifSequenceWriter object. This does not close the underlying
   * stream, just finishes off the GIF.
   */
  public void close() throws IOException {
    gifWriter.endWriteSequence();    
  }

  /**
   * Returns the first available GIF ImageWriter using 
   * ImageIO.getImageWritersBySuffix("gif").
   * 
   * @return a GIF ImageWriter object
   * @throws IIOException if no GIF image writers are returned
   */
  private ImageWriter getWriter() throws IIOException {
    Iterator<ImageWriter> iter = ImageIO.getImageWritersBySuffix("gif");
    if(!iter.hasNext()) {
      throw new IIOException("No GIF Image Writers Exist");
    } else {
      return iter.next();
    }
  }

  /**
   * Returns an existing child node, or creates and returns a new child node (if 
   * the requested node does not exist).
   * 
   * @param rootNode the <tt>IIOMetadataNode</tt> to search for the child node.
   * @param nodeName the name of the child node.
   * 
   * @return the child node, if found or a new node created with the given name.
   */
  private IIOMetadataNode getNode(
      IIOMetadataNode rootNode,
      String nodeName) {
    int nNodes = rootNode.getLength();
    for (int i = 0; i < nNodes; i++) {
      if (rootNode.item(i).getNodeName().compareToIgnoreCase(nodeName)
          == 0) {
        return((IIOMetadataNode) rootNode.item(i));
      }
    }
    IIOMetadataNode node = new IIOMetadataNode(nodeName);
    rootNode.appendChild(node);
    return(node);
  }
}  
    
    
     
}