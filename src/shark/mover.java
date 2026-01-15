package shark;
import java.awt.image.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.TextAttribute;
import java.text.AttributedString;

public class mover  {
   public static final int WIDTH = 0x00004000;
   public static final int HEIGHT = 0x00004000;
   /**
    * The saved image for this mover.
    */
   public Image save;
   Graphics saveg;
   int savex,savey,savew,saveh;
   public Image im;
   public String imname;
   int times[];
   long changetime;
   byte currim;
   /**
    * Physical width of the mover
    */
   public int w;

   /**
    * Physical height of the mover.
    */
   public int h;
   public int imwidth,imheight;
   public int x, y;
   /**
    * True if the mover needs to be saved
    */
   public boolean mustSave;
   /**
    * True if the mover should not be removed.
    */
   public boolean dontclear;
   public boolean dontgrabmouse;
   /**
    * When true the mover bounces off the edges of the screen.
    */
   public boolean bouncer;
   public int mx,my;
   public Rectangle moveWithin;
   /**
    * The mover moves while this is true.
    */
   public boolean keepMoving;
   public boolean noSpriteIfOver;
   public boolean mouseSensitive;
   /**
    * Input associated with a particular mover.
    */
   public sharkInput input;
//startPR2010-05-04^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   public mover input2;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   /**
    * Contains headings from current wordlist
    */
   public String sayit;
   /**
    * Rectangle that contains text that can be spoken.
    */
   public Rectangle sayitrect;
   /**
    * x coordinate a mover has moved from
    */
   public int fromx;
   /**
    * y coordinate a mover has moved from
    */
   public int fromy;
   /**
    * x coordinate a mover has moved to
    */
   public int tox;
   /**
    * y coordinate a mover has moved to
    */
   public int toy;
   /**
    * time for move (Sets speed mover moves at).
    */
//startPR2008-08-29^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   public int fromw;
   public int fromh;
   public int tow;
   public int toh;
   public Rectangle zoomrect1;
   public Rectangle zoomrect2;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   public int ti;
   long starttime;
   /**
    * When true the mover is not moving and so does'nt need redrawing.
    */
   public boolean ended;
   /**
    * Temporary mover - once it stops it's movement it is killed (removed from array of movers).
    */
   public boolean temp;
   /**
    * The mover is to be removed from the array at an appropriate point in the execution
    */
   public boolean kill;
   /**
    * True if the mover has been drawn
    */
   public boolean drawn;
   public boolean  mouseOver, mouseDown, prepared;
   /**
    * This references the runMover object that owns the particular mover.
    */
   public runMovers manager;
   public boolean dontwait;
   public boolean handcursor = false;
   public boolean gameicon = false;
   public sharkImage sharkim;
   public boolean isGamesIconPart = false;
   public String hyperlink = null;
   
   

   //--------------------------------------------------
   public mover(boolean saveit) {
     mustSave =  saveit;
   }
   public mover() {}
//--------------------------------------------------
   public mover(Image im1, int screenwidth,int screenheight) {
      mustSave =  true;
      do {
         imwidth  = im1.getWidth(sharkStartFrame.mainFrame);
      }while (imwidth < 0);
      imheight = im1.getHeight(sharkStartFrame.mainFrame);
      w = (imwidth+1) * mover.WIDTH / screenwidth ;
      h = (imheight+1) * mover.HEIGHT / screenheight ;
      im = im1;
   }
//--------------------------------------------------
   public mover(Image im1, int screenwidth,int screenheight,Color c) {
      mustSave = true;
      do {
         imwidth  = im1.getWidth(sharkStartFrame.mainFrame);
      }while (imwidth < 0);
      imheight = im1.getHeight(sharkStartFrame.mainFrame);
      w = (imwidth+1) * mover.WIDTH / screenwidth;
      h = (imheight+1) * mover.HEIGHT / screenheight;
      transparentImage(im1,c);
   }
//---------------------------------------copy of another----
   public mover(mover m) {
       w = m.w;
       h = m.h;
       imwidth = m.imwidth;
       imheight = m.imheight;
       mustSave = m.mustSave;
       im = m.im;
   }
//------------------------ download image file
//   public mover(File dir, String name,  int width, int height,int screenwidth,int screenheight,boolean saveit) {
//       mustSave = saveit;
//      imagedets(dir,name,width,height,screenwidth,screenheight);
//   }
   //---------------------------------------------------------------
   Image adjustimage(Image im, int width, int height) {
        do {
           imwidth = im.getWidth(null);
        }while(imwidth <= 0);
        imheight = im.getHeight(null);
        if(imwidth > width-1 || imheight > height-1) {
           if(width*imheight > height*imwidth) {
              imwidth = (height-1)*imwidth/imheight;
              imheight = height-1;
           }
           else {
              imheight = (width-1)*imheight/imwidth;
              imwidth = width-1;
           }
           return(im.getScaledInstance(imwidth,imheight,Image.SCALE_DEFAULT));
        }
        else return im;
   }
   //----------------------------------------------
   public boolean ismoving() {
     return ti != 0  && sharkGame.gtime() < starttime+ti && (x != tox || y != toy);
   }
  //---------------------------------------
//startPR2008-08-29^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  public void zoomto(Rectangle r, int time) {
    fromx = x;
    fromy = y;
    fromw = w;
    fromh = h;
    tox = r.x;
    toy = r.y;
    tow = r.width;
    toh = r.height;
    ti = time;
    starttime = sharkGame.gtime();
    ended = false;
  }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   public void moveto(int x1, int y1, int time) {
     fromx = x;
     fromy = y;
     tox = x1;
     toy = y1;
     ti = time;
     starttime = sharkGame.gtime();
     ended = false;
   }
   //---------------------------------------
   public void setto(int x1, int y1) {
     fromx = tox = x = x1;
     fromy = toy = y = y1;
     ended = false;
   }
   /**
    * Overridden by games so that images of movers are changed appropriately in them.
    * @param currtime The current time
    */
  public void changeImage(long currtime) {
  }
  //-------------------------------------------------------------
  public void stop() {
     fromx = tox = x;
     fromy = toy = y;
     ended = false;
  }

  //-------------------------routines to override---------------------------------
  public void setSize(int containerWidth, int containerHeight) {}
  public void paint(Graphics g, int x, int y, int w, int h) {
       if(im != null) {
           int i = 0;
           if(imwidth > w || imheight > h) {
              im = adjustimage(im,w,h);
           }
           g.drawImage(im, x + w/2 - imwidth/2, y+h/2-imheight/2, null);
       }
  }
  public void mouseClicked(int x, int y) {};
  public void mouseUp(int x, int y) {};
  public boolean isOver(int x,int y) { return true;}
  //----------------------------------------------------------
  class transparentFilter extends RGBImageFilter {
     public int tcolor;
     public transparentFilter(Color color ) {
        super();
        tcolor = color.getRGB() & 0x00ffffff;
        canFilterIndexColorModel = true;
     }
     public int filterRGB(int x, int y, int rgb) {
        if((rgb & 0x00ffffff) == tcolor) return 0;
        else return rgb;
     }
  }
  public void transparentImage(Image im1,Color c) {
     Toolkit t =  Toolkit.getDefaultToolkit();
     im =  t.createImage(
                      new FilteredImageSource(im1.getSource(),
                      new transparentFilter(c)));
  }
  //---------------------------------------------------------
  public Point futurepos(int time1) {
     long elapsed = sharkGame.gtime() + time1 - starttime;
     if(!bouncer && elapsed > ti)  return null;
     else {
        int x1 = (int)((fromx*(ti-elapsed) + tox*elapsed) /ti);
        int y1 = (int)((fromy*(ti-elapsed) + toy*elapsed) /ti);
        if(x1<0 || x1+w>mover.WIDTH || y1<0 || y1+h>mover.HEIGHT)
                return null;
        return new Point(x,y);
     }
  }
  //-------------------------------------------------------------------
                   // rotate and convert a point about a centre
   public static Point rotate(int x, int y, int cx, int cy, double angle,int sw, int sh) {
      int sin = (int)(Math.sin(angle) * 1000);
      int cos = (int)(Math.cos(angle) * 1000);

      return new Point(
        cx*sw/mover.WIDTH + (int)(((long)(x-cx)*sw*cos/mover.WIDTH - (long)(y-cy)*sh*sin/mover.HEIGHT)/1000),
        cy*sh/mover.HEIGHT + (int)(((long)(x-cx)*sw*sin/mover.WIDTH + (long)(y-cy)*sh*cos/mover.HEIGHT)/1000));
   }
   //--------------------------------------------------------------
//startPR2010-05-04^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   public static class formattedtextmover extends mover {
     Font f;
     FontMetrics m;
     int  yinc;
     String message[];
     Color fg = Color.black;
     public Color bg;
     boolean centre;
     int longestline = 0;
     AttributedString as;
     boolean underlined;
     public String mess;
     Color col;
     Font fw;
     runMovers r;
     int fontstyle;

     public formattedtextmover(String mess1, Color col1, Font fw1, runMovers r1, boolean centered1, int fontstyle1, boolean underlined1) {
         mess = mess1;
         col = col1;
         fw = fw1;
         r = r1;
         centre = centered1;
         fontstyle = fontstyle1;
         underlined = underlined1;
         init();
     }
     
     public formattedtextmover(String mess1, Color col1, Font fw1, runMovers r1, boolean centered1, int fontstyle1) {
         mess = mess1;
         col = col1;
         fw = fw1;
         r = r1;
         centre = centered1;
         fontstyle = fontstyle1;
         init();
     }
     
     void init(){
       f = fw.deriveFont(fontstyle);
       m = r.getFontMetrics(fw);
       message = u.splitString(mess);
       int k;
       for(int i=0;i<message.length;++i) {
         if((k=m.stringWidth(message[i]))>longestline)
             longestline = k;
       }
       if( r.screenwidth==0)u.pause(50);
       w = longestline* mover.WIDTH/r.screenwidth;
       h =  m.getAscent()+(m.getHeight()*message.length)*mover.HEIGHT/r.screenheight;
       fg=col;
       if(underlined){
           as = new AttributedString(mess);
           as.addAttribute(TextAttribute.FONT, f);
           as.addAttribute(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON, 0,
           mess.length());   
       }
     }

     public void paint(Graphics g,int x1, int y1, int w1, int h1) {
       int i;
       if(bg != null) {
         g.setColor(bg);
         g.fillRect(x1,y1,w1,h1);
      }
       g.setColor(fg);

       g.setFont(f);
       int p;
       for(i=0;i<message.length;++i) {
         if(centre){
             int u = m.stringWidth(message[i]);
             int j = (w1 - u)/2;
             j = Math.max(j, j*-1);
             p = x1 + j;
         }
         else
             p = x1;
         ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
         if(as!=null)
            ((Graphics2D)g).drawString(as.getIterator(),p, y1+m.getAscent() + i*m.getHeight());  
         else
            g.drawString(message[i],p, y1+m.getAscent() + i*m.getHeight());
         ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
       }
     }
   }

   public static class formattedtextmover2 extends mover {
     Font f;
     FontMetrics m;
     int  yinc;
     String message[];
     Color fg = Color.black;
     public Color bg;
     boolean centre;
     int longestline = 0;

     public formattedtextmover2(String mess, Color col, Font fw, runMovers r, boolean centred, int fontstyle) {
       centre = centred;
       f = fw.deriveFont(fontstyle);
       m = r.getFontMetrics(fw);
       message = u.splitString(mess);
       int k;
       for(int i=0;i<message.length;++i) {
         if((k=m.stringWidth(message[i]))>longestline)
             longestline = k;
       }
       w = longestline;
       h =  m.getAscent()+(m.getHeight()*message.length);
       fg=col;
     }

     public void paint(Graphics g,int x1, int y1, int w1, int h1) {
       int i;
       if(bg != null) {
         g.setColor(bg);
         g.fillRect(x1,y1,w1,h1);
      }


      g.setColor(fg);
      g.setFont(f);
      int p;
      for(i=0;i<message.length;++i) {
         if(centre){
             int u = m.stringWidth(message[i]);
             int j = (w1 - u)/2;
             j = Math.max(j, j*-1);
             p = x1 + j;
         }
         else
             p = x1;
         ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
         g.drawString(message[i],p, y1+m.getAscent() + i*m.getHeight());
         ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
       }
     }
   }


   public static class picandname extends mover {
     int  yinc;
     Color fg = Color.white;
     public Color bg;
     int longestline = 0;
     int ih;
     int iw;
     int tih;
     int tiw;
     Image image;
     public String text;
     Font f1 = sharkStartFrame.treefont;
     FontMetrics f1m;
     int lastscrw =-1;
     runMovers rm;
     int gap;
     int mode;
     float alpha = 0.5f;
     public boolean viewshares = false;
     public String temptext = null;
     Image baseimage;
     Image drawim = null;
     Image lastdrawim = null;
     int iiw,iih, iix, iiy;
     int lasty;
     String ssharewith = u.gettext("sharedplay", "sharewith");
     static final int MODE_CURRUSER = 0;
     static final int MODE_OTHERUSER = 1;
     static final int MODE_USERTOCHOOSE = 2;
     static final int MODE_OTHERUSERCHOSEN = 3;
     static final int MODE_NOSHARE = 4;
     static final int MODE_OTHERUSERCHOSENMOVING = 5;

     public picandname(String mess, runMovers r,  int height, int type) {
       mode  = type;
       text = mess;
       rm = r;
       f1m = rm.getFontMetrics(f1);
       gap = f1m.stringWidth(" ");
       h = height;
       iiw = iih = iix = iiy = -1;   
       if(mode==MODE_OTHERUSER)
           w = h;
       else
           w = ((gap + f1m.stringWidth(text))* mover.WIDTH/rm.screenwidth)+ h;
       if(mode==MODE_USERTOCHOOSE||mode==MODE_NOSHARE||mode==MODE_OTHERUSER)this.handcursor = true;
       setuppic(text);
     }

     void setuppic(String name) {
            byte buf[];
            MediaTracker tracker=new MediaTracker(rm);  
            baseimage = sharkStartFrame.t.createImage(sharkStartFrame.publicPathplus + "sprites" +
                                shark.sep +  "signonAdminDouble_il96.png");
            tracker.addImage(baseimage,2);
            if(mode != MODE_OTHERUSER){
                if(sharkStartFrame.allowStuImportPics_Icon && (buf = (byte[]) db.find(text, PickPicture.ownpicstuset, db.PICTURE)) != null) {
                   image = sharkStartFrame.t.createImage(buf);
                   tracker.addImage(image,1);
                }
                else if ( (buf = (byte[]) db.find(text, PickPicture.ownpic, db.PICTURE)) != null) {
                   image = sharkStartFrame.t.createImage(buf);
                   tracker.addImage(image,1);
                }
                else{
                    String imtext;
                    if(u.findString(student.adminlist, text)>=0 || u.findString(student.teacherlist, text)>=0)
                        imtext = "signonAdminLight_il96.png";
                    else 
                        imtext = "signonStudLight_il96.png";                    
                    image = sharkStartFrame.t.createImage(sharkStartFrame.publicPathplus + "sprites" +
                                    shark.sep + imtext);
                    tracker.addImage(image,1);
                }
            }
            try
            {
                tracker.waitForAll();
            }
            catch (InterruptedException ie){}
            if(mode == MODE_OTHERUSER){
                image = baseimage;
            }
            ih = image.getHeight(null);
            iw = image.getWidth(null);
            if(baseimage!=null){
                tih = baseimage.getHeight(null);
                tiw = baseimage.getWidth(null);
            }
     }

     public void refresh() {
        setuppic(sharkStartFrame.studentList[sharkStartFrame.currStudent].name);
     }

     public void change(String name, int type) {
         text = name;
         mode = type;
         setuppic(text);
     }

     public void reset2() {
        mode = MODE_OTHERUSER;
        text = ssharewith;
     }

     public void paint(Graphics g,int x1, int y1, int w1, int h1) {
       Graphics2D g2d = (Graphics2D) g;
       int curiw = -1;
       int curih = -1;
       float curralpha;
       String currtext = text;
       if((mode == MODE_OTHERUSER || mode == MODE_OTHERUSERCHOSEN) ){
           if(viewshares)currtext = ssharewith;
           else if(mouseOver && !viewshares)currtext = null;
       }

       if(mode == MODE_NOSHARE)drawim=null;
       else if(mode == MODE_OTHERUSERCHOSENMOVING || 
               mode == MODE_OTHERUSER || 
               viewshares || 
               mode == MODE_OTHERUSER || 
               mode == MODE_OTHERUSERCHOSEN)
       {
        drawim = baseimage;
        curiw = tiw;
        curih = tih;
        curralpha = alpha;
       }
       else {
        drawim = image;
        curiw = iw;
        curih = ih;
        curralpha = 1.0f;
       }

       if(mode == MODE_OTHERUSERCHOSEN || drawim == baseimage)
         w = h;
       else
         w = ((gap + f1m.stringWidth(text))* mover.WIDTH/rm.screenwidth)+ h;
  
       
//       if(mode == MODE_MODE_OTHERUSERCHOSEN){
//           curralpha = 1.0f;
//       }
//       else
       if(mode == MODE_OTHERUSERCHOSENMOVING || ((mode == MODE_OTHERUSERCHOSEN || mode == MODE_OTHERUSER) && !mouseOver && !viewshares))
       {
        curralpha = alpha;
       }
       else {
        curralpha = 1.0f;
       }

       if(drawim!=null){
           if(lasty!=y1 || lastdrawim != drawim ||  lastscrw!=rm.screenwidth ){
              int sq = h1;
              if(curih>curiw){
                  iih = sq;
                  iiw = sq*curiw/curih;
                  iix = x1 +((sq-iiw)/2);
              }
              else{
                  iiw = sq;
                  iix = x1;
                  iih = sq*curih/curiw;
              }
              if(curih>curiw){
                  iiy = y1;
              }
              else{
                  iiy = y1 + ((sq-iih)/2);
              }
              if(mode==MODE_OTHERUSER || text==null)
                w = iiw* mover.WIDTH/rm.screenwidth;
              else
                w =  (gap + f1m.stringWidth(text) + iiw)* mover.WIDTH/rm.screenwidth;
              lasty=y1;
              lastscrw=rm.screenwidth;
          }
          if(curralpha!=1.0f)
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,curralpha));
          g2d.drawImage(drawim, iix, iiy, iiw, iih, null);
          if(curralpha!=1.0f)
              g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
         }

      if(bg != null) {
         g2d.setColor(bg);
         g2d.fillRect(x1,y1,w1,h1);
      }
      g2d.setColor(Color.darkGray);

      g2d.setFont(f1);
      if(currtext!=null && (mode!=MODE_OTHERUSER || viewshares) ){
          g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
          g2d.drawString(currtext, x1 + (h1/2)+(iiw/2)  + gap, y1 + (int)h1/2+ (int)f1m.getAscent()/3  );
          g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
      }
      lastdrawim = drawim;

     }
   }


//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   public static class simpletextmover extends mover {
     Font f;
     FontMetrics m;
     int  yinc;
     String message[];
     Color fg = Color.black;
     public Color bg;
     public simpletextmover(String mess, int w1, int h1) {
       w = w1;
       h = h1;
       message = u.splitString(mess);
     }
     public simpletextmover(String mess, int w1, int h1,Color col) {
       w = w1;
       h = h1;
       message = u.splitString(mess);
       fg=col;
     }
     public simpletextmover(String mess, int w1, int h1,Color col, Font fnt, runMovers rm ) {
       h = h1;
       message = u.splitString(mess);
       fg=col;
       f = fnt;
       m =  rm.getGraphics().getFontMetrics(f);
       w = m.stringWidth(mess)*mover.WIDTH/rm.screenwidth;
     }
     public void paint(Graphics g,int x1, int y1, int w1, int h1) {
       int i;
       if(bg != null) {
         g.setColor(bg);
         g.fillRect(x1,y1,w1,h1);
      }
      if (f == null) {
         f = sharkGame.sizeFont(message, w1, h1);
         m = g.getFontMetrics(f);
       }
       yinc = h1/message.length/2 -  m.getHeight()/2 +  m.getAscent();
       g.setColor(fg);
       g.setFont(f);
       for(i=0;i<message.length;++i) {
//startPR2010-05-04^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         g.drawString(message[i],
                 x1+w1/2-m.stringWidth(message[i])/2,
                 y1 + h1/message.length*i+yinc);
//startPR2010-05-04^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       }
     }
   }
     //--------------------------------------------------------------
   public static class textMover extends mover {
      String[] message;
      Color color,colorbg;
      public int linedepth;
      public boolean onlyifmouseover;
      boolean visible;
      Font[] font;
      Font font2;
      Color csurround;
      public textMover(String mess,int wi, int hi,Color col,Color bg, Font[] font1) {
         super(false);
         message = u.splitString(mess);
         color = col;
         colorbg = bg;
         w = wi;
         h = hi;
         font = font1;
      }
      public textMover(String mess,int wi, int hi,Color col,Color bg, Font font1, String hyperlinktext) {
         super(false);
         if(hyperlinktext!=null){
           hyperlink = hyperlinktext;
           handcursor = true;            
         }
         message = u.splitString(mess);
         color = col;
         colorbg = bg;
         w = wi;
         h = hi;
         font2 = font1;
      } 
      public textMover(String mess,int wi, int hi,Color col,Color bg, Font[] font1,Color surround) {
         super(false);
         message = u.splitString(mess);
         color = col;
         colorbg = bg;
         csurround = surround;
         w = wi;
         h = hi;
         font = font1;
      }
      public textMover(String mess,int wi, int hi,Color col,Color bg,int linedepth1,Font[] font1) {
         super(false);
         linedepth = linedepth1;
         message = u.splitString(mess);
         color = col;
         colorbg = bg;
         w = wi;
         h = hi;
         font = font1;
      }
     public void paint(Graphics g,int x, int y, int w1, int h1) {
       FontMetrics m;
//       if(font[0] == null) {
//          if(linedepth == 0) {
//             font[0] = sharkGame.sizeFont(g,message,w1,h1);
//          }
//          else {
//             font[0] = sharkGame.sizeFont(g,message,w1*4/3,linedepth*manager.screenheight/mover.HEIGHT*message.length);
//          }
//       }
//       else g.setFont(font[0]);
       if(onlyifmouseover) {
          if(visible != mouseOver & sharkStartFrame.wanthelp) manager.copyall = true;
          if(!mouseOver || !sharkStartFrame.wanthelp) {visible = false;return;}
       }
       visible = true;
       if(font2 !=null){
           if(!g.getFont().equals(font2)){
             g.setFont(font2);
           }
           m = g.getFontMetrics(font2);
       }
       else{
        g.setFont(sharkStartFrame.treefont);
        m =  sharkStartFrame.treefontm;       // rb 7/5/08
       }
       if(colorbg != null) {
          int width=0;
          for(short i=0; i<message.length;++i) {
             width = Math.max(width, m.stringWidth(message[i]+"  "));
          }
          g.setColor(colorbg);
          g.fillRect(x+w1/2-width/2,y,width,h1);
          if(csurround != null) {
             g.setColor(csurround);
             g.fillRect(x+w1/2-width/2,y,2,h1);
             g.fillRect(x+w1/2-width/2+width-2,y,2,h1);
          }
       }
       g.setColor(color);
       x += w1/2;
       y += Math.max(0, h1/2 - m.getHeight()*message.length/2 + m.getMaxAscent());
       ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
       for(short i=0; i<message.length;++i) {
          int xx;
          g.drawString(message[i], xx=Math.max(2,x - m.stringWidth(message[i])/2),y);
          if(this instanceof flashtextMover) {
           g.drawLine(xx, y+2, xx + m.stringWidth(message[i]),y+2);
          }
         if(hyperlink!=null){
             g.drawLine(xx=Math.max(2,x - m.stringWidth(message[i])/2),y+3 , xx+m.stringWidth(message[i]) , y+3);
         }
          y += m.getHeight();
       }
       ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
     }
  }
   //----------------------------------------------------------------
   public static class flashtextMover extends textMover {
       public flashtextMover(String mess,int wi, int hi,Color col,Color bg,Font[] font1,Color surround) {
           super(mess,wi,hi,col,bg,font1,surround);
           keepMoving=true;
       }
   }
   //--------------------------------------------------------------
   public static class rectMover extends mover {
      Rectangle p;
      Color color,colorbg;
      public rectMover(Rectangle pp,Color col,Color bg) {
         super(false);
         dontclear = true;
         color = col;
         colorbg = bg;
         p = pp;
         w = p.width;
         h = p.height;
     }
     public void paint(Graphics g,int x, int y, int w1, int h1) {
       g.setColor(colorbg);
       g.fillRect(x,y,w1,h1);
       g.setColor(color);
       g.drawRect(x,y,w1,h1);
     }
  }
//startPR2010-05-04^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   public static class rectTransparentMover extends mover {
      Rectangle p;
      Color colorbg;
      float f;
      public rectTransparentMover(Rectangle pp,Color bg, float fl) {
         super(false);
         f = fl;
         dontclear = true;
         colorbg = bg;
         p = pp;
         w = p.width;
         h = p.height;
     }
     public void paint(Graphics g,int x, int y, int w1, int h1) {
       ((Graphics2D)g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, f));
       g.setColor(colorbg);
       g.fillRect(x,y,w1,h1);
       ((Graphics2D)g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
     }
  }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  //--------------------------------------------------------------
  public static class roundrectMover extends mover {
     Color color,colorbg;
     int cor =-1;
     int lastcor;
     float arcw;
     int arcint=-1;

     public roundrectMover(int ww, int hh ,Color col,Color bg, float cornerw) {
        super(false);
        arcw = cornerw;
        dontclear = true;
        color = col;
        colorbg = bg;
        w = ww;
        h = hh;
    }

      public roundrectMover(int ww, int hh ,Color col,Color bg, int corner) {
        super(false);
        arcint = corner;
        dontclear = true;
        color = col;
        colorbg = bg;
        w = ww;
        h = hh;
    }

    public void paint(Graphics g,int x, int y, int w1, int h1) {
    //    x=x-1;
    //    y=y-1;
     //   w1=w1+2;
      //  h1=h1+2;
        Graphics2D g2d = (Graphics2D)g;
        if(arcint<0 && cor!=lastcor){
            cor = (int)(w1*arcw);
            lastcor = cor;
        }
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
      g.setColor(colorbg);
      if(arcint>0)
        g.fillRoundRect(x,y,w1,h1,arcint,arcint);
      else
        g.fillRoundRect(x,y,w1,h1,cor,cor);
      g.setColor(color);
      if(arcint>0)
        g.fillRoundRect(x,y,w1,h1,arcint,arcint);
      else
        g.drawRoundRect(x,y,w1,h1,cor,cor);
      g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);
    }
 }

  public static class gamesTab extends mover {
     Color activecolor;
     Color inactivecolor;
     Color tabcolor;
     Color color;
     Color bgcolor;
     int cor =-1;
     int lastcor;
     float arcw;
     Polygon poly;
     public String text[];
     int lastw1 = -1;
     Font tempf2;
     

        int tborder = -1;
        int tx1 = -1;
        int ty1 = -1;
        int tw1 = -1;

        int tsq = -1;
        int strx;
        int stry;
        int th;
        int bandh;
        Point p[] = null;
        

     public gamesTab(int ww, int hh ,Color bgcol, Color activecol, Color inactivecol, Color tabcol, String s) {
        super(false);
        dontclear = true;
        activecolor = activecol;
        inactivecolor = inactivecol;
        tabcolor = tabcol;
        bgcolor = bgcol;
        color = activecolor;
        text = u.splitString(s);
        w = ww;
        h = hh;
        handcursor = true;
    }
     public void setActive(boolean active){
         color = active?activecolor:inactivecolor;
     }

    public void paint(Graphics g,int x, int y, int w1, int h1) {
        Graphics2D g2d = (Graphics2D)g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        int arcd = (int)w1/9;
        if(arcd%2!=0)arcd+=1;
        int arcr = arcd/2;

        if(sharkStartFrame.gamestabfont == null || w1!=lastw1){
            tsq = w1-arcr;
            tborder = tsq/30;
            tx1 = x+((w1-tsq)/2)+tborder +(arcr/2) ;
            ty1 = y+tborder + arcr;
            tw1 = tsq-(tborder*2)- arcr;
            bandh = th/10;
        }
        if(sharkStartFrame.gamestabfont == null || w1!=lastw1){
            String s[] = sharkStartFrame.tabstrings;
            tempf2 = sharkStartFrame.treefont.deriveFont((float)40);
            FontMetrics fm2 = sharkStartFrame.mainFrame.gamePanel.getFontMetrics(tempf2);

//            gamestoplay games = new gamestoplay();   // todo - shouldn't be calling this in paint
//            games.setup(sharkStartFrame.publicGameLib,
//                   false,true,"root",gamestoplay.categories);
            String sst[] = new String[]{};
            jnode jns[] = ((jnode)((jnode)sharkStartFrame.mainFrame.publicGameTree.getModel().getRoot()).getChildAt(0)).getChildren();
            for(int i = 0; i < jns.length; i++){
                jnode jns2[] = jns[i].getChildren();
                for(int k = 0; k < jns2.length; k++){
                    String st[] =  u.splitString( u.halveText(   jns2[k].get())   );
                    sst = u.addString(sst, st);
                }
            }


            sst = u.addString(sst, u.addString(sst, u.halveText( sharkStartFrame.str_recommended)));
            sst = u.addString(sst, u.addString(sst, u.halveText( sharkStartFrame.str_allavailable)));


            int longest = -1;
            String longeststr = null;
            int j;
//            for(int i = 0; i < s.length; i++){
//                String ss[];
//                ss = u.splitString(s[i]);
                for(int k = 0; k < sst.length; k++){
                    if((j=fm2.stringWidth(sst[k]))>longest){
                        longest = j;
                        longeststr = sst[k];
                    }
                }
//            }
            while(fm2.stringWidth(longeststr)>tw1){
                tempf2 = tempf2.deriveFont((float)(tempf2.getSize()-1));
                fm2 = sharkStartFrame.mainFrame.gamePanel.getFontMetrics(tempf2);
            }
            sharkStartFrame.gamestabfont = tempf2;
            p = new Point[text.length];

            int yy = y - fm2.getDescent()+((fm2.getDescent()+fm2.getAscent())/2);
            int yy1 = yy+arcr;
            int yy2 = yy+h1;
            int yy3 = yy2-yy1;
           int gap = fm2.getHeight()/2;
            for(int i = 0; i < text.length; i++){
                int yy4 = yy1+(yy3/2);
                if(text.length>1){
                    
                    if(i==0) yy4 -= gap;
                    else yy4 += gap;
                }
//                else yy4 -= gap;
                p[i] = new Point(tx1+((tw1-fm2.stringWidth(text[i]))/2), yy4);
            }
            lastw1 = w1;
        }


        g.setColor(bgcolor);
        g.fillRect(x, y, w1, h1);
        g.drawRect(x, y, w1, h1);
        g.setColor(color);
        g.fillRect(x, y+h1-arcr, w1, arcr);
        g.drawRect(x, y+h1-arcr, w1, arcr);
        g.fillRect(x+arcr, y+arcr, w1-arcd, h1-arcr);
        g.drawRect(x+arcr, y+arcr, w1-arcd, h1-arcr);
        g.setColor(bgcolor);
        

        g.fillArc(x+w1-arcr+1, y+h1-arcd, arcd, arcd, 180, 90);
     //   g.drawArc(x+w1-arcr, y+h1-arcd, arcd, arcd, 180, 90);
        g.fillArc(x-arcr, y+h1-arcd, arcd, arcd, 270, 90);
     //   g.drawArc(x-arcr, y+h1-arcd, arcd, arcd, 270, 90);

        g.setColor(tabcolor==null?color:tabcolor);
        g.fillArc(x+arcr, y, arcd, arcd, 90, 90);
        g.drawArc(x+arcr, y, arcd, arcd, 90, 90);
        g.fillRect(x+arcd, y, w1-(arcd*2), arcr);
        g.drawRect(x+arcd, y, w1-(arcd*2), arcr);
        g.fillArc(x+w1-arcd-arcr, y, arcd, arcd, 0, 90);
        g.drawArc(x+w1-arcd-arcr, y, arcd, arcd, 0, 90);
        g.drawLine(x+arcr, y+arcr, x+arcr+w1-arcd, y+arcr);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);
        g.setColor(Color.black);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            if(p!=null){
                for(int i = 0; i < text.length; i++){
                    g2d.setFont(sharkStartFrame.gamestabfont);
                    g2d.drawString(text[i], p[i].x, p[i].y);
                }
            }
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);


    }
 }
  //---------------------------------------------------------
  public boolean endmove()   {return false;} // return true to get rid of it
  public void keyhit(KeyEvent e) {}
  }
