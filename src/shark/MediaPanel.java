package shark;

     // A JWindow that plays media from a URL
     import java.net.URL;
     import java.awt.*;
     import java.awt.event.*;
     import javax.media.*;
     import java.io.*;
     import javax.swing.*;


     public class MediaPanel extends JWindow
     {
       public static boolean isPlayingVideo = false;
       Player mediaPlayer;
       sharkImage im;
       int x, y, w, h;
       int layouttype;
       public static int ALIGNLEFT = 0;
       public static int ALIGNCENTER = 1;
       Component com = null;
       Component video;
       public static String fileExt = ".avi";

       public MediaPanel(Window parent, sharkImage simage,int x1,int y1,int w1, int h1)
       {
         super(parent);
         isPlayingVideo = true;
         this.setVisible(false);
         layouttype = ALIGNLEFT;
         x=x1;y=y1;w=w1;h=h1;
         im=simage;
         init();

       }

       public MediaPanel(Window parent,sharkImage simage,int xx, int yy, int ww, int hh, int type, Component c)
       {
         super(parent);
         isPlayingVideo = true;
         this.setVisible(false);
         layouttype = type;
         x=xx; y=yy; w=ww; h=hh;
         com = c;
         im=simage;
         init();
       }

       void init(){
         getContentPane().setLayout( new BorderLayout() ); // use a BorderLayout
         URL mediaURL=  null;
         if(im.isvideo)mediaURL =im.signvideo;
      try
      {
                  mediaPlayer = Manager.createRealizedPlayer( mediaURL );
                  video = mediaPlayer.getVisualComponent();
                  if ( video != null )
                    getContentPane().add( video, BorderLayout.CENTER ); // add video component
                  video.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseReleased(MouseEvent e) {
                      stopall();
                    }
                  });
                  addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseReleased(MouseEvent e) {
                      stopall();
                    }
                  });
                  Dimension size2 = video.getPreferredSize();
                  size2=size2;
                     // start playing the media clip
                } // end try
                catch ( NoPlayerException noPlayerException )
                {
                  System.err.println( "No media player found" );
                } // end catch
                catch ( CannotRealizeException cannotRealizeException )
                {
                  System.err.println( "Could not realize media player" );
                } // end catch
                catch ( IOException iOException )
                {
                  System.err.println( "Error reading from the source" );
                } // end catch
                mediaPlayer.addControllerListener(new ControllerListener()
                {
                  public void controllerUpdate(ControllerEvent event)
                  {
                    if(event instanceof EndOfMediaEvent)
                    {
                      stopall();

                    }
                  }
                });
                adjustbounds();
                makevisible();
       }
       void adjustbounds() {
         runMovers rm;
         Dimension viddim = video.getPreferredSize();
         int vidw = (int)viddim.getWidth();
         int vidh = (int)viddim.getHeight();
         if(com instanceof runMovers){
           rm = (runMovers) com;
           Point p = com.getLocationOnScreen();
           int rmx = (int)p.getX();
           int rmy = (int)p.getY();
           int finw = w*rm.screenwidth/mover.WIDTH;
           int finh = h*rm.screenheight/mover.HEIGHT;
           if(finw*vidh >  vidw*finh) {
             finw = (vidw * finh / vidh + 1);
           }
           else {
             finh = (vidh * finw / vidw + 1);
           }
           finw = finw * mover.WIDTH/rm.screenwidth;
           finh = finh * mover.HEIGHT/rm.screenheight;
           if(layouttype == ALIGNCENTER){
             setBounds(Math.max(0,Math.min(mover.WIDTH-finw,x - finw/2))*rm.screenwidth/mover.WIDTH +rmx,
                       Math.max(0,Math.min(mover.HEIGHT-finh,y - finh/2))*rm.screenheight/mover.HEIGHT+rmy,
                       finw*rm.screenwidth/mover.WIDTH , finh*rm.screenheight/mover.HEIGHT );
           }
           else if (layouttype == ALIGNLEFT){
             setBounds(Math.max(0, Math.min(mover.WIDTH - finw, x)) * rm.screenwidth /
                       mover.WIDTH+rmx,
                       Math.max(0, Math.min(mover.HEIGHT - finh, y)) * rm.screenheight /
                       mover.HEIGHT+rmy,
                       finw*rm.screenwidth/mover.WIDTH , finh*rm.screenheight/mover.HEIGHT );
           }
         }
         else {
           int finw = w;
           int finh = h;
           int xextra = 0;
           int yextra = 0;
           if(finw*vidh > vidw*finh) {
             finw = (vidw * finh / vidh + 1);
             xextra = ((x-finw)/2);
           }
           else {
             finh = (vidh * finw / vidw + 1);
             yextra = ((h-finh)/2);
           }
           setBounds(x + xextra, y + yextra, finw , finh);
         }
       }
       void stopall() {
         mediaPlayer.stop();
         mediaPlayer.close();
         isPlayingVideo = false;
         dispose();
         wordlist.currpic = null;
         wordlist.currpic2 = null;
         System.gc();

       }
       void startplaying() {
         mediaPlayer.start();
       }
       void makevisible() {
         setVisible(true);
         startplaying();
       }
   }

