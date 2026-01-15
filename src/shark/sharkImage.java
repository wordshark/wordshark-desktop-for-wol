package shark;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.Arrays;
//startPR2008-06-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
import java.net.*;
//endPRimagesandvids^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR

public class sharkImage extends mover{
   static int ocos[] = getocos();
   static int osin[] = getosin();
   static final int BASEU=0x00004000;
   static final short FACTOR=1024;

   static final char LINE = 0;
   static final char RECTANGLE = 1;
   static final char RECTFILL = 2;
   static final char OVAL = 3;
   static final char OVALFILL = 4;
   static final char POLYLINE = 5;
   static final char POLYGON = 6;
   static final char POLYFILL = 7;
   static final char TEXT = 8;
   static final char ARCFILL = 9;
   static final char FUNCTION = 10;
   static final char MOVE = 11;
   static final char COPY = 12;
   static final char ROTATE = 13;
   static final char ATTACH = 14;
   static final char COLOR = 15;
   static final char GETOLDCOLOR = 16;
   static final char RECOLOR = 17;
   static final char SMOOTH = 18;
   static final char THICKEN = 19;
   static final char UNTHICKEN = 20;
   static final char SPLITINTOPOLY = 21;
   static final char UNDO = 22;
   static final char REDO = 23;
   static final char RUN = 24;
   static final char NOACTIVECON = 25;
   static final char TOFRONT = 26;
   static final char TOBACK = 27;
   static final char LEFTTORIGHT = 28;
   static final char NEXT = 29;
   static final char PREV = 30;
   static final char RESIZE = 31;
   static final char RESIZE2 = 32;
   static final char CLIP = 33;
   static final char MINSIZE = 34;

   static final char ENDTYPES = 10;
//startPR2009-05-12^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR

//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR

   static class cache {
      String search,name[],database[];
   }
   public static cache cc[] = new cache[0];

   static class scanned implements Comparable{
      String name,path;
      public int compareTo(Object o) {
         return name.compareTo(((scanned)o).name);
      }
   }
   static int lastfunc;
   static scanned scannedlist[] = getscanned();
   static int scantot;

   int nodedist, movelist[],rotlist[];
   static String choicenarr[] = {"line", "rectangle", "filled rectangle",
                                   "oval", "filled oval",
                                   "poly-line", "polygon", "filled polygon",
                                   "text","filled arc","function (eg clock)",
                                   "MOVE",
                                   "COPY",
                                   "ROTATE",
                                   "ATTACH TO PART",
                                   "GET NEW COLOUR",
                                   "GET OLD COLOR",
                                   "RE-COLOUR PART",
                                   "SMOOTH POLYGON",
                                   "THICKER",
                                   "LESS THICK",
                                   "SPLIT INTO POLYGON",
                                   "UNDO",
                                   "REDO",
                                   "RUN & SIZE",
                                   "NO ACTIVE CONTROL",
                                   "TOWARDS FRONT",
                                   "TOWARDS BACK",
                                   "LEFT TO RIGHT",
                                   "NEXT PART",
                                   "PREV PART",
                                   "MOVE & RESIZE", "STRETCH","CLIP", "MINSIZE"};
//startPR2009-09-28^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//   static String[] functions = new String[]{"clock","arrow"};
   static String[] functions = new String[]{"clock","arrow", "pasttense", "homonym"};
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   saveSharkImage im;
   public Image tifimage;
//startPR2008-06-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   URL signvideo;
   boolean isvideo = false;
   boolean isimport = false;
//endPRimagesandvids^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   public String name,database;
   boolean newf;
   imageControl c[];
   imagePart p[];
   imagePart mousepart;
                 // current vals
   long currtime;
   public double angle;                     // rotation about centre
   public boolean lefttoright,upsidedown;   // transpositions
   public boolean distort,rotates;
   public int midx,midy,realmidx,realmidy;
   int sina,cosa;
   public int endw,startw=BASEU,endh,starth=BASEU;
   public boolean canmove,roundrect,cansay;
                // building
   boolean building,moving,rotating,attaching,running;
   boolean changed,hadrun;
   short currChoice,activecontrol=-1;
   Color currColor = Color.black;
   imagePart currPart,attachPart;
//   short lconpart = -2;
   short mouseNode=-1;
   Point arrowpoint;
   Point oldNode;
   Point startMouse;    // used when moving
   short activelist[];

   short mousecontrol=-1;
   int mousex1,mousey1,mousex2,mousey2;
   int mouseend,mousecurr;
   boolean resizing,mustresize;
   public int newrectx1,newrectx2,newrecty1,newrecty2;
   boolean marking,ismark;
   public int markx1,markx2,marky1,marky2;
   boolean edittext;
   int textx,texty;

   short currpno;
   static sharklist lcon;
   static JList lchoice;
   static String lastname,lastdatabase;
   boolean newselection;
   short markedforsay=-1,newmarkedforsay;
   String spellchanged[][],spellchangedto[][];
//   char grouptot;
   ByteArrayOutputStream undo[],redo[];
   short currundo = -1,currredo = -1;
   Font fixedfont;
   boolean icon,help,gotfixedfont;
   Font savedfont;
   public boolean ghost,darker,black;

   // external requests
   boolean keyhit;
   KeyEvent keye;
   boolean newchoice;
   int curr;
   boolean mouseclick;
   boolean changecontrol;
   boolean wantimport;
   String importname, importdb;
   boolean insetup;
   public boolean frozen;
   sharkImage list[];               // list of images for finger spelling
   long blankend,listend;
   int currlist,lastlist = -1;
   boolean doneonepass;
   Point extrapoly[];
   java.util.Vector remember;

   saveSharkImage gotimport;

   boolean tablet;
   short lastchoice;
   static JColorChooser colorchooser;
   JDialog colordialog;
   JDialog editfunc;
   Font funcf;
   FontMetrics funcm;
   String funcmess;
   public boolean antialias =  shark.doImageScreenshots?true:false;

   static final int TYPE_PICTURE = 0;
   static final int TYPE_SIGN = 1;
   static final int TYPE_FINGER = 2;

   public boolean univseralImport =false;
   public int hitspread;
   
   public boolean initdrawn = false;
   public java.util.ArrayList icgroups;
   boolean done = false;
   boolean loopgif = true;
   boolean isanimated = false;   





   public sharkImage(String name1,boolean mustsave) {
      super();
      short i,j;
      name = name1;
      icon = name.length() > 2 && name.substring(0,2).equalsIgnoreCase("i_");
      help = name.length() > 5 && name.substring(0,5).equalsIgnoreCase("help_");
      w = h = BASEU;
      keepMoving = true;
//startPR2009-12-23^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      String ss[] = doIgnore(name1);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      if(sharkStartFrame.currStudent < 0
         || !sharkStartFrame.studentList[sharkStartFrame.currStudent].hasimages
//startPR2009-12-23^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//          || (im = (saveSharkImage)db.find(database=sharkStartFrame.studentList[sharkStartFrame.currStudent].name,name1,db.IMAGE)) == null) {
             || (im = (saveSharkImage)db.find(database=sharkStartFrame.studentList[sharkStartFrame.currStudent].name,ss,db.IMAGE)) == null) {
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         for(i = 0;i < sharkStartFrame.publicImageLib.length;++i) {
//startPR2009-12-23^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//            if((im = (saveSharkImage)db.find(database = sharkStartFrame.publicImageLib[i],name1,db.IMAGE)) != null) {
                if((im = (saveSharkImage)db.find(database = sharkStartFrame.publicImageLib[i],ss,db.IMAGE)) != null) {
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
               setup();
               return;
            }
         }
      }
      if(im != null) {
               setup();
               return;
      }
      if(scannedlist.length>0 && (tifimage = findscanned(name1)) != null)  {
         return;
      }
      newf = true;
      c = new imageControl[0];
      p = new imagePart[1];
      im = new saveSharkImage();
      database = sharkStartFrame.publicImageLib[0];

   }
   public sharkImage(String name1,String db1) {
     super();
     short i,j;
     name = name1;
     w = h = BASEU;
     keepMoving = true;
//startPR2009-12-23^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      if((im = (saveSharkImage)db.find(database = db1,name1,db.IMAGE)) != null) {
        if((im = (saveSharkImage)db.find(database = db1,doIgnore(name1),db.IMAGE)) != null) {
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       setup();
     }
   }




   public boolean notfound() {
     return newf;
   }
   public sharkImage(String database1, String name1,boolean mustsave,boolean build1) {
      super();
      short i,j;
      name = name1;
      database = database1;
      building = build1;
      if(building) {
//        remember = (java.util.Vector) db.find(sharkStartFrame.studentList[
//                                              sharkStartFrame.currStudent].dbname,
//                                              "image_remember", db.TEXT);
        if (colorchooser==null) {
          colorchooser = new JColorChooser(Color.black);
        }
        colordialog = JColorChooser.createDialog(sharkStartFrame.mainFrame,
                       "Choose a new color", true,
                       colorchooser,
                       new ActionListener() {
                           public void actionPerformed(ActionEvent e) {
                             currColor = colorchooser.getColor();
                           }
                       },
                       new ActionListener() {
                           public void actionPerformed(ActionEvent e) {
                             colorchooser.setColor(currColor);
                           }
                        });
      }
      icon = !building && name.length() > 2 && name.substring(0,2).equalsIgnoreCase("i_");
      help = name.length() > 5 && name.substring(0,5).equalsIgnoreCase("help_");
      currtime = sharkGame.gtime();
      w = h = BASEU;
      keepMoving = true;
//startPR2009-12-23^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      if ((im = (saveSharkImage) db.find(database1, name1, db.IMAGE)) != null) {
      if ((im = (saveSharkImage) db.find(database1, doIgnore(name1), db.IMAGE)) != null) {
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         if(shark.doImageScreenshots && im!=null && im.controls != null && im.controls.length >0){ 
             for(int g = 0; g < im.controls.length; g++){
                 im.controls[g].min = im.controls[g].min * DoImageScreenshots.timeFactor; 
                 im.controls[g].max = im.controls[g].max * DoImageScreenshots.timeFactor; 
             }
         }
        if (building) { // special--------------------------------
          loop:for (i = 0; i < im.parts.length; ++i) {
            if (im.parts[i].controlno >= 0) {
              for (j = 0; j < im.parts.length; ++j) {
                if (i != j && im.parts[j].group == im.parts[i].group
                    && im.parts[j].controlno < 0) {
                  continue loop;
                }
              }
              saveImagePart newPart = copySavePart(im.parts[i], im.parts[i].group);
              newPart.controlno = -1;
            }
          }
        }
        setup();
        return;
      }
      newf = true;
      c = new imageControl[0];
      p = new imagePart[0];
      im = new saveSharkImage();
   }
   //------------------------------------------------------------------
   public sharkImage(sharkImage imcopy, boolean mustsave) {
      super();
      short i,j;
      name = imcopy.name;
      icon = name.length() > 2 && name.substring(0,2).equalsIgnoreCase("i_");
      help = name.length() > 5 && name.substring(0,5).equalsIgnoreCase("help_");
      database = imcopy.database;
      currtime = sharkGame.gtime();
      w = h = BASEU;
      keepMoving = true;
      im = imcopy.im;
      if(imcopy.tifimage != null){
          tifimage = imcopy.tifimage;
          keepMoving = false;
      }
      else setup();
   }
   //------------------------------------------------------------------
  public sharkImage(saveSharkImage ims) {
     super();
     short i,j;
     im=ims;
     currtime = sharkGame.gtime();
     w = h = BASEU;
     keepMoving = true;
     setup();
  }
  //---------------------------------------------------------------------
   public sharkImage(Image img, String name1) {
      tifimage = img;
      name = name1;
      w = h = BASEU;
   }
//startPR2008-06-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   public sharkImage(URL vid, String name1) {
     signvideo = vid;
     isvideo = true;
     name = name1;
     w = h = BASEU;
   }
//endPRimagesandvids^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   //--------------------------------------------------------------------
   public int width() {
       return im.x2 - im.x1;
   }
   //--------------------------------------------------------------------
   public int height() {
       return im.y2 - im.y1;
  }
   public int getx1() {
       return im.x1;
   }
   public int getx2() {
       return im.x2;
   }
  //--------------------------------------------------------------------
   void setup() {
      short i,j,maxpg=-1;
      starth = startw = BASEU;
      char pos=0;
      insetup=true;
      if(currPart != null) {
        pos = currPart.group;
      }
      c =  new imageControl[im.controls.length];
      if(c.length>0) canmove = true;
     for(j=0;j<c.length;++j) {
         c[j] = new imageControl(j);
      }
      activecontrol = (short)Math.min(activecontrol,c.length-1);
      for(j=0;j<im.parts.length;++j)
         maxpg = (short)Math.max(im.parts[j].group, maxpg);
      if(building) {
         gloop:for(i=0;i<=maxpg;++i)  {
             for(j=0;j<im.parts.length;++j) {
                if(im.parts[j].group == i) continue gloop;
             }
             --maxpg;
             for(j=0;j<im.parts.length;++j) {
                if(im.parts[j].group > i) --im.parts[j].group;
                if(im.parts[j].attachno > i) --im.parts[j].attachno;
             }
             --i;
         }
      }
      p =  new imagePart[maxpg+1];
      for(j=0;j<=maxpg;++j) {
          p[j] = new imagePart((char)j);
          p[j].getparts();   // get parts without controls
      }
      if(building) {
         if(activelist != null) {
            for(j=0;j<activelist.length;++j) {
               if(activelist[j] < c.length) c[activelist[j]].activate();
            }
         }
         setcurrpart(pos);
      }
      else for(j=0;j<c.length;++j) {
         c[j].qactivate();
      }
//      if(building && lcon != null) setselected();
      if(building && !running && lcon != null) {
         if(activecontrol < 0)   lcon.clearSelect();
         else lcon.select(new int[] {activecontrol});
      }
      insetup=false;
   }
   //------------------------------------------------------------------
   void buildactivelist() {
      short i,j,atot=0;
      for(i=0;i<c.length;++i)   if(c[i].active) ++atot;
      activelist = new short[atot];
      atot = 0;
      for(i=0;i<c.length;++i) {
         if(c[i].active) activelist[atot++] = i;
      }
   }
   //------------------------------------------------------------------
   void setselected() {
      short i,j,atot=0;
      int alist[];
      for(i=0;i<c.length;++i)   if(c[i].active) ++atot;
      alist = new int[atot];
      atot = 0;
      for(i=0;i<c.length;++i) {
         if(c[i].active) alist[atot++] = i;
      }
      int list[] =lcon.selection();
      if(list.length != atot) {lcon.select(alist); return;}
      for(i=0;i<list.length;++i) {
         if(list[i] != alist[i]) {
            lcon.select(alist);
            return;
         }
      }
   }
   //------------------------------------------------------------------
   public void save() {
      int i,j;
      if(editfunc != null) {editfunc.dispose(); editfunc = null;}
      if(!changed) return;
      if(currPart != null && mouseNode >= 0) endPart();
      if(!hadrun) {  // if needs to re-size do auto
        int w1 = manager.getWidth(), h1 = manager.getHeight();
        Graphics g = manager.getGraphics();
//        manager.haltrun = true;
//        u.pause(200);
        startrunning();
        if(c.length==0) paint(g,0,0,w1,h1);
        else for(i=0;i<c.length;++i) {
          lcon.setSelectionRow(i);
          paint(g,0,0,w1,h1);
        }
        running = false;
      }
      db.update(database, name, im, db.IMAGE);
      sharkStartFrame.studentList[sharkStartFrame.currStudent].hasimages
            = db.anyof(sharkStartFrame.studentList[sharkStartFrame.currStudent].
                       name, db.IMAGE);
   }
   //-----------------------------------------------------------------
   public void paint(Graphics g, int x1, int y1, int w1, int h1) {
//startPR2008-06-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     if(isvideo)return;
//endPRimagesandvids^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     Graphics2D gg2d = (Graphics2D)g;
     gg2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
     if(antialias){
       gg2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
     }

      Shape saveclip = null;
      boolean gotclip=false;
      if(list != null) {           // list for finger spelling
        if(blankend != 0) {
          if(sharkGame.gtime()<blankend) return;
          blankend = 0;
        }
        while(list[currlist] == null) currlist = (currlist+1)%list.length;
        if(lastlist != currlist) {
            if(list[currlist].im.controls.length>0)
                       list[currlist].setControl(list[currlist].im.controls[0].name);
            if(lastlist>=0 && list[lastlist].name.equals(list[currlist].name)) {  // pause if a letter is repeated
               blankend =  sharkGame.gtime() + 300;
               lastlist  = currlist;
               return;
            }
            lastlist  = currlist;
        }
        if(list[currlist].c.length == 0 || list[currlist].c[list[currlist].c.length-1].active) {
          if (listend == 0)           listend = sharkGame.gtime() + (list[currlist].c.length == 0?800:400);
          else if(sharkGame.gtime() > listend) {
            listend = 0;
            if(list[currlist].im.controls.length>0)
                       list[currlist].setControl(list[currlist].im.controls[0].name);
            if (++currlist >= list.length) {
              blankend = sharkGame.gtime() + 800;
              doneonepass = true;
              currlist = 0;
              return;
            }
            else if(list[currlist] == null) {
              blankend =  sharkGame.gtime() + 800;
              return;
            }
          }
        }
        list[currlist].paint(g,x1,y1,w1,h1);
        return;
      }
      if(tifimage != null) {
         g.drawImage(tifimage,x1,y1,w1,h1,null);
         return;
      }
      newmarkedforsay = -1;
      if(building) {
         if(running && mustresize) {
            startchange();
            int xx1 = Math.min(newrectx1,newrectx2);
            int xx2 = Math.max(newrectx1,newrectx2);
            int yy1 = Math.min(newrecty1,newrecty2);
            int yy2 = Math.max(newrecty1,newrecty2);
            int newmidx = ((xx1+xx2)/2-(x1+w1/2)) * BASEU / manager.screenmax;
            int newmidy = ((yy1+yy2)/2-(y1+h1/2)) * BASEU/ manager.screenmax;
            int oldmidx = (im.x1+im.x2)/2;
            int oldmidy = (im.y1+im.y2)/2;
            int dx = (newmidx - oldmidx);
            int dy = (newmidy - oldmidy);
            int neww = (xx2-xx1) * BASEU / manager.screenmax;
            int oldw =  im.x2 - im.x1;
            int newh = (yy2-yy1) * BASEU / manager.screenmax;
            int oldh =  im.y2 - im.y1;
            int i,j;
            for(i=0;i<im.parts.length;++i) {
               if(im.parts[i].attachno < 0) {
                  im.parts[i].x = im.parts[i].x*neww/oldw + dx;
                  im.parts[i].y = im.parts[i].y*newh/oldh + dy;
               }
               else {
                  im.parts[i].x = im.parts[i].x*neww/oldw;
                  im.parts[i].y = im.parts[i].y*newh/oldh;
               }
               for(j=0;j<im.parts[i].node.length;++j) {
                  if(im.parts[i].node[j].x > BASEU*4)
                       im.parts[i].node[j].x = (im.parts[i].node[j].x - BASEU*8)*neww/oldw +  BASEU*8;
                  else im.parts[i].node[j].x = im.parts[i].node[j].x*neww/oldw;
                  im.parts[i].node[j].y = im.parts[i].node[j].y*newh/oldh;
               }
            }
            resizing = mustresize = false;
            if(activecontrol<0)  activelist = null;
            else activelist = new short[] {activecontrol};
            lcon.multiselect(true);
            setup();
            im.x1 = im.y1 = BASEU;
            im.x2 = im.y2 = -BASEU;
            im.radius = 0;
          }
          if(!running && currPart != null && mouseNode >= 0 && oldNode == null &&  currPart.ispoly()
                && mouseDown && (tablet || sharkGame.gtime() - manager.mousepressedat > 500)) {
                   saveImagePart pp = im.parts[currPart.frompart];
                   Point mp = mousePoint();
                   int xx = pp.node[pp.node.length-1].x;
                   int yy = pp.node[pp.node.length-1].y;
                   if(xx != mp.x || yy != mp.y) {
                     Point newp[] = new Point[pp.node.length + 1];
                     System.arraycopy(pp.node, 0, newp, 0, pp.node.length);
                     pp.node = newp;
                     mouseNode = (short) (pp.node.length - 1);
                     newp[mouseNode] = mp;
                   }
          }
          else if(!running && currPart != null && mouseNode >= 0 && oldNode != null &&  currPart.ispoly()
                && mouseDown && sharkGame.gtime() - manager.mousepressedat > 500) {
                   saveImagePart pp = im.parts[currPart.frompart];
                   Point mp = new Point( mousePoint().x - currPart.actualx(),mousePoint().y - currPart.actualy());
                   if(extrapoly==null) {
                     extrapoly = new Point[] {oldNode};
                     im.parts[currPart.frompart].node[mouseNode] = oldNode;
                   }
                   int xx =extrapoly[extrapoly.length-1].x;
                   int yy = extrapoly[extrapoly.length-1].y;
                   if(xx != mp.x || yy != mp.y) {
                     Point newp[] = new Point[extrapoly.length + 1];
                     System.arraycopy(extrapoly, 0, newp, 0, extrapoly.length);
                     newp[newp.length-1] = mp;
                     extrapoly = newp;
                   }
         }
         else if(extrapoly != null && oldNode != null && currPart != null &&  currPart.ispoly()) {
           saveImagePart pp = im.parts[currPart.frompart];
           int i,j,from=0,to=0,dist=0x7fffffff, dist2,lastx = extrapoly[extrapoly.length-1].x, lasty =  extrapoly[extrapoly.length-1].y;
           for(i=0;i<pp.node.length;++i) {
             if(pp.node[i] == oldNode) from = i;
             dist2 =  (lastx-pp.node[i].x)* (lastx-pp.node[i].x) +( lasty-pp.node[i].y)* (lasty-pp.node[i].y);
             if(dist2<dist) {to = i;dist=dist2;}
           }
           if(activecontrol < 0 && currPart.controlledby == -1 ) {
             if (from <= to) {
               Point newp[] = new Point[pp.node.length - Math.abs(from - to) +
                  extrapoly.length];
               System.arraycopy(pp.node, 0, newp, 0, from);
               System.arraycopy(extrapoly, 0, newp, from, extrapoly.length);
               System.arraycopy(pp.node, to, newp, from + extrapoly.length,
                                pp.node.length - to);
               pp.node = newp;
            }
             else { // crosses starting point
               Point newp[] = new Point[from-to + extrapoly.length];
               System.arraycopy( extrapoly, 0, newp, 0, extrapoly.length);
               System.arraycopy( pp.node, to, newp, extrapoly.length, from-to);
               pp.node = newp;
             }
           }
           else{
             int nodes = (to+pp.node.length-from)%pp.node.length;
             for(i=0;i<nodes;++i) {
               pp.node[(from+i)%pp.node.length] = extrapoly[Math.min(extrapoly.length-1,(extrapoly.length*i+nodes/2)/nodes)];
             }
           }
           extrapoly = null;
           mouseNode = -1;
         }
         else extrapoly = null;
         if (keyhit) dokeyhit();
         if(newchoice) donewchoice();
         if(mouseclick) domouseclick();
         if(changecontrol) dochangecontrol();
         if(wantimport) doimportimage();
      }
      int i,j;
      boolean again = true;
      if(angle != 0) {
         sina = (int)(Math.sin(angle)*FACTOR);
         cosa = (int)(Math.cos(angle)*FACTOR);
      }
      realmidx = midx = x1+w1/2;
      realmidy = midy = y1+h1/2;
      if(building) {
         if(newselection) {
            int sel[] = lcon.selection();
            if(running) {
               for(i=0;i<sel.length;++i) {
                  if(sel[i]<c.length && !c[sel[i]].active) c[sel[i]].activate();
               }
            }
            else {
                if(sel.length>0 && !lcon.isEditing()
                              && sel[0] != activecontrol) {
                   cancel();  // in case doing something
                   activecontrol = (short)sel[0];
                   activelist = new short[] {activecontrol};
                   setup();
                }
            }
            newselection = false;
         }
         if(!manager.mouseOutside && this.mouseOver) manager.requestFocus();
         nodedist = manager.screenmax/40;
         endh = endw = manager.screenmax;
      }
      else {
         int ww = im.x2 - im.x1;
         int hh = im.y2 - im.y1;

         if(im.clip != null) {
            ww = im.clip.width;
            hh = im.clip.height;
          }
         startw = ww;
         endw = w1;
         starth = hh;
         endh = h1;
         if(im.clip != null) {
            ww = im.clip.width;
            hh = im.clip.height;
          }

         if(rotates) {
            endh = endw = Math.min(w1,h1)-2;
            startw = starth = im.radius*2;
         }
         else if(!distort) {
            if(w1*hh >  ww*h1) {
               endw = h1;
               startw = hh;
            }
            else {
              endh = w1;
              starth = ww;
            }
         }
         if(im.clip == null) {
           midx -= (im.x1 + im.x2) * endw / startw / 2;
           midy -= (im.y1 + im.y2) * endh / starth / 2;
         }
         else  {  // clipping
            saveclip = g.getClip();
            gotclip=true;
            midx -= (im.clip.x + im.clip.width/2) * endw / startw;
            midy -= (im.clip.y + im.clip.height/2) * endh / starth;
            int ww1 = im.clip.width*endw/startw;
            int hh1 = im.clip.height*endh/starth;
            g.setClip(x1+w1/2 - ww1/2,y1+h1/2-hh1/2,ww1,hh1);
        }
      }
      currpno=0;
      currtime = sharkGame.gtime();

      if((!building || running) && !frozen) for(i=0;i<c.length;++i)
                   if(c[i].active) c[i].next();     // advance controls

      if(currPart != null)   currPart = p[currPart.group];
      if(building && running) {
          short tot=0;
          boolean want = false;

          for(i=0;i<c.length;++i) if(c[i].active) ++tot;
          int newsel[] = new int[tot];
          tot=0;
          for(i=0;i<c.length;++i) if(c[i].active) newsel[tot++] = i;
          int sel[] = lcon.selection();
          if(sel.length == newsel.length) {
             for(i=0;i<tot;++i) if(newsel[i] != sel[i]) {
                want = true;
                break;
             }
          }
          else want = true;
          if(want) lcon.select(newsel);
      }

      for(i=0;i<p.length;++i){
//startPR2004-07-12^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        if (p[i] != null) {
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          p[i].setupok = false;
//startPR2004-07-12^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        }
        else {
          if(gotclip) g.setClip(saveclip);
          return;
        }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      }
      short repeats=0;
      while(again && ++repeats<p.length+2) {
         again = false;
         for(i=0;i<p.length;++i) {
            if(!p[i].setCurr()) again = true;  // position
         }
      }
      savedfont = null;
      for(i=0;i<p.length;++i) {
        if(p[i].frompart >= 0 && im.parts[p[i].frompart].type <100)
                      p[i].paint(g);
      }
      if(building) {
         if(!running) {
           if(extrapoly != null) {
             Polygon newpoly = currPart.getp(extrapoly);
             g.setColor(u.rand(2)==0?Color.white:Color.black);
             g.drawPolyline(newpoly.xpoints,newpoly.ypoints,newpoly.npoints);
           }
           if(attaching && currPart != attachPart) {
               attachPart.paintnodes(g);
             }
            else if(currPart != null) {
               if(mouseNode < 0 ) currPart.paintnodes(g);
               g.setColor(Color.black);
               g.setFont(sharkStartFrame.wordfont);
               FontMetrics m = g.getFontMetrics();
               String partno = "Part no: " + String.valueOf((int)currPart.group);
               g.drawString(partno,0,m.getAscent());
            }
            if(ismark || marking) {
               if(marking) {
                  markx2 = manager.mousexs;
                  marky2 = manager.mouseys;
               }
               g.setColor((u.rand(2)==0)?Color.white:Color.red);
               int xx1 = Math.min(markx1,markx2);
               int xx2 = Math.max(markx1,markx2);
               int yy1 = Math.min(marky1,marky2);
               int yy2 = Math.max(marky1,marky2);
               g.drawRect(xx1,yy1,xx2-xx1,yy2-yy1);
            }
            if(help) {
               g.setColor((u.rand(2)==0)?Color.white:Color.green);
               g.drawRect(midx -sharkStartFrame.screenSize.width/8, 0,sharkStartFrame.screenSize.width/4,manager.screenheight-1);
            }
         }
         else {       // show sizings
            int xx1 = midx + im.x1*endw/startw ;
            int xx2 = midx + im.x2*endw/startw;
            int yy1 = midy + im.y1*endh/starth ;
            int yy2 = midy + im.y2*endh/starth ;
            g.setColor(Color.white);
            g.drawRect(xx1,yy1,xx2-xx1,yy2-yy1);
            int diam = im.radius*endw/startw ;
            g.drawOval((xx1+xx2)/2-diam, (yy1+yy2)/2-diam,diam*2,diam*2);
            if(resizing) {
               newrectx2 = manager.mousexs;
               newrecty2 = manager.mouseys;
               g.setColor((u.rand(2)==0)?Color.white:Color.black);
               xx1 = Math.min(newrectx1,newrectx2);
               xx2 = Math.max(newrectx1,newrectx2);
               yy1 = Math.min(newrecty1,newrecty2);
               yy2 = Math.max(newrecty1,newrecty2);
               g.drawRect(xx1,yy1,xx2-xx1,yy2-yy1);
            }
            if(!help) {    // show picture or icon to size
              if(name.length()>2 && name.substring(0,2).equals("i_") && sharkStartFrame.mainFrame.gamerect[0] != null) {
                 endw = sharkStartFrame.mainFrame.gamerect[0].height*sharkStartFrame.screenSize.width*2/3/mover.WIDTH;
                 endh = sharkStartFrame.mainFrame.gamerect[0].width*sharkStartFrame.screenSize.height*2/3/mover.HEIGHT;
              }
              else {
                if(im.minsize != null) {
                  endw = im.minsize.width * sharkStartFrame.screenSize.height/BASEU;
                  endh =  im.minsize.height * sharkStartFrame.screenSize.height/BASEU;
                }
                else {
                   endw = sharkStartFrame.screenSize.width / 4;
                   endh = sharkStartFrame.screenSize.height / 3;
                 }
              }
              int ww = startw;
              int hh = starth;
              startw = im.x2 - im.x1;
              starth = im.y2 - im.y1;
              if(endw*starth >  endh*startw) {
                endw = (startw * endh / starth + 1);
              }
              else {
                endh = (starth * endw / startw + 1);
              }
              realmidx = midx = x1 + endw/2 - (im.x1+im.x2)*endw/startw/2;
              realmidy = midy = y1 + endh/2 - (im.y1+im.y2)*endh/starth/2;
              running=building=false;
              g.setColor(Color.white);
              g.fillRect(x1,y1,x1+endw,y1+endh);

              for(i=0;i<p.length;++i) p[i].setupok = false;
              repeats=0;
              again = true;
              while(again && ++repeats<p.length+2) {
                 again = false;
                 for(i=0;i<p.length;++i) {
                    if(!p[i].setCurr()) again = true;  // position
                 }
              }
              for(i=0;i<p.length;++i) {
                if(p[i].frompart >= 0 && im.parts[p[i].frompart].type <100)
                   p[i].paint(g);
              }
              running=building=true;
              startw = ww;
              starth = hh;
            }
         }
         if(im.clip != null) {
           g.setColor(Color.blue);
           g.drawRect(im.clip.x * manager.screenmax / BASEU + manager.screenwidth/2,
               im.clip.y * manager.screenmax / BASEU + manager.screenheight/2,
               im.clip.width * manager.screenmax / BASEU,
               im.clip.height * manager.screenmax / BASEU);
         }
         if(im.minsize != null) {
            g.setColor(Color.green);
            g.drawRect(im.minsize.x * manager.screenmax / BASEU + manager.screenwidth/2,
                im.minsize.y * manager.screenmax / BASEU + manager.screenheight/2,
                im.minsize.width * manager.screenmax / BASEU,
                im.minsize.height * manager.screenmax / BASEU);
          }
      }
      else {
        if (icon && fixedfont != null)
          gotfixedfont = true;
      }
      if(gotclip) g.setClip(saveclip);
      markedforsay = newmarkedforsay;
      if(building && !running && currPart != null && im.parts[currPart.frompart].type == FUNCTION
         && editfunc==null) {
         if(funcmess==null) {
           funcmess = u.gettext("imagefunc", "text");
           funcf = sharkGame.sizeFont(new String[] {funcmess}, manager.screenwidth, manager.screenheight / 10);
           funcm = manager.getFontMetrics(funcf);
         }
         g.setFont(funcf);
         g.setColor(Color.black);
         g.drawString(funcmess,manager.screenwidth/2 - funcm.stringWidth(funcmess)/2,manager.screenheight - funcm.getDescent());
      }
      gg2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
      gg2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);     
      initdrawn = true;
   }
   //------------------------------------------------------------------
         // see if any part is over
   public boolean covers(int x, int y) {
      for(short i=0;i<p.length;++i) {
         if(p[i].frompart >= 0 && im.parts[p[i].frompart].type <100) {
            saveImagePart pp = im.parts[p[i].frompart];
            switch(pp.type) {
               case POLYGON:
               case POLYFILL:
               case POLYLINE:
                   if(p[i].getp(p[i].drawnode).contains(x,y)) return true;
                    break;
               case RECTANGLE:
               case RECTFILL:
               case ARCFILL:
               case TEXT:
               case FUNCTION:
               case LINE:
                  if(p[i].getp(new Point[] {p[i].drawnode[0],new Point(p[i].drawnode[1].x,p[i].drawnode[0].y),
                                  p[i].drawnode[1],new Point(p[i].drawnode[0].x,p[i].drawnode[1].y) })
                        .contains(x,y))  return true;
                  break;
               case OVAL:
               case OVALFILL:
                  if(p[i].poval(p[i].drawnode).contains(x,y)) return true;
                  break;
            }
         }
      }
      return false;
   }

   public boolean covers(Rectangle r) {
      for(short i=0;i<p.length;++i) {
         if(p[i].frompart >= 0 && im.parts[p[i].frompart].type <100) {
            saveImagePart pp = im.parts[p[i].frompart];
            switch(pp.type) {
               case POLYGON:
               case POLYFILL:
               case POLYLINE:
                   if(p[i].getp(p[i].drawnode).intersects(r)) return true;
                    break;
               case RECTANGLE:
               case RECTFILL:
               case ARCFILL:
               case TEXT:
               case LINE:
                  if(p[i].getp(new Point[] {p[i].drawnode[0],new Point(p[i].drawnode[1].x,p[i].drawnode[0].y),
                                  p[i].drawnode[1],new Point(p[i].drawnode[0].x,p[i].drawnode[1].y) })
                        .intersects(r))  return true;
                  break;
               case OVAL:
               case OVALFILL:
                  if(p[i].poval(p[i].drawnode).intersects(r)) return true;
                  break;
            }
         }
      }
      return false;
   }

   //------------------------------------------------------------------
          // see if any part is over
    public boolean intersects(Rectangle r) {
       for(short i=0;i<p.length;++i) {
          if(p[i].frompart >= 0 && im.parts[p[i].frompart].type <100) {
             saveImagePart pp = im.parts[p[i].frompart];
             switch(pp.type) {
                case POLYGON:
                case POLYFILL:
                case POLYLINE:
                    if(p[i].getp(p[i].drawnode).intersects(r)) return true;
                     break;
                case RECTANGLE:
                case RECTFILL:
                case ARCFILL:
                case TEXT:
                case FUNCTION:
                case LINE:
                   if(p[i].getp(new Point[] {p[i].drawnode[0],new Point(p[i].drawnode[1].x,p[i].drawnode[0].y),
                                   p[i].drawnode[1],new Point(p[i].drawnode[0].x,p[i].drawnode[1].y) })
                         .intersects(r))  return true;
                   break;
                case OVAL:
                case OVALFILL:
                   if(p[i].poval(p[i].drawnode).intersects(r)) return true;
                   break;
             }
          }
       }
       return false;
    }
   //------------------------------------------------------------------
         // test part within rect
   boolean partisinside(int pno, int x1, int y1, int x2, int y2) {
      Rectangle r = null;
      if(p[pno].frompart >= 0 && im.parts[p[pno].frompart].type <100) {
            saveImagePart pp = im.parts[p[pno].frompart];
            switch(pp.type) {
               case POLYGON:
               case POLYFILL:
               case POLYLINE:
                    r = p[pno].getp(p[pno].drawnode).getBounds();
                    break;
               case FUNCTION:
                    r = p[pno].imfunc.getBounds();
                    if(r!=null) break;  // if null, assume that just noraml bounds are used
               case RECTANGLE:
               case RECTFILL:
               case TEXT:
               case LINE:
               case ARCFILL:
                  r = p[pno].getp(new Point[] {p[pno].drawnode[0],new Point(p[pno].drawnode[1].x,p[pno].drawnode[0].y),
                                  p[pno].drawnode[1],new Point(p[pno].drawnode[0].x,p[pno].drawnode[1].y) })
                        .getBounds();
                 break;
               case OVAL:
               case OVALFILL:
                  r = p[pno].poval(p[pno].drawnode).getBounds();
                  break;
             }
            if (r != null && r.x >= x1 && r.x+r.width <= x2
                         && r.y >= y1 && r.y+r.height <= y2) {
               return true;
            }
      }
      return false;
   }
   //------------------------------------------------------------------
   void setcurrpart(char pos) {
      short i,j;
      for(i=0;i<p.length;++i) {
         if(p[j=(short)((i+pos)%p.length)].frompart>=0)
                                   {currPart = p[j];  return;}
      }
      currPart = null;
   }
   //---------------------------------------------------------------
   void removePart(int pno) {
      short i;
      char oldgroup = im.parts[pno].group;
      short oldcontrol = im.parts[pno].controlno;

      saveImagePart pp[] = new  saveImagePart[im.parts.length-1];
      System.arraycopy(im.parts,0,pp,0,pno);
      if(pno < im.parts.length-1)
         System.arraycopy(im.parts,pno+1,pp,pno,
                                    im.parts.length-pno-1);
      im.parts = pp;

      if(oldcontrol < 0)
        for(i=0; i<im.parts.length; ++i) {   // empty group - clear attached parts
          if(im.parts[i].group == oldgroup || im.parts[i].attachno == oldgroup) {
            removePart(i);
            i = -1;  // restart loop in case other parts removed
         }
      }
    }
    //------------------------------------------------------------------
    void cleargroups() {      // clear empty groups
      int i,j;
      imagePart imp[];
      partscan: for(i=0; i<p.length;++i) {
         for(j=0;j<im.parts.length;++j) {
            if(im.parts[j].group == i) continue partscan;
         }
                    // no parts for this
         imp = new imagePart[p.length-1];
         System.arraycopy(p,0,imp,0,i);
         if(i < p.length-1)
            System.arraycopy(p,i+1,imp,i,p.length-i-1);
         p = imp;

         for(j=0;j<p.length;++j) {
             if(p[j].group > i) --p[j].group;
         }
         for(j=0;j<im.parts.length;++j) {
            if(im.parts[j].group > i) --im.parts[j].group;
            if(im.parts[j].attachno > i) --im.parts[j].attachno;
         }
         --i;
      }
    }
   //--------------------------------------------------------------
   void removeNode() {
      saveImagePart pp = im.parts[currPart.frompart];
      Point nn[] = new Point[pp.node.length-1];
      System.arraycopy(pp.node,0,nn,0,mouseNode);
      System.arraycopy(pp.node,mouseNode+1,nn,mouseNode,pp.node.length-1-mouseNode);
      pp.node = nn;
      endPart();
   }
   //----------------------------------------------------------------------
   public void setupclicktarget(mover m, int spread){
       m.sharkim = this;
       hitspread = spread;
   }


   void cancel() {
       extrapoly = null;
       if(currPart != null) {
         if (mouseNode >= 0) {
           if (oldNode != null) {
             im.parts[currPart.frompart].node[mouseNode] = oldNode;
             mouseNode = -1;
             arrowpoint = null;
             oldNode = null;
           }
           else if (currPart.ispoly() &&
                    im.parts[currPart.frompart].node.length > 3) {
             removeNode();
             mouseNode = -1;
             arrowpoint = null;
           }
           else
             undo();
         }
         else if (moving || attaching || rotating) {
           undo();
         }
       }
    }
   //--------------------------------------------------------------
   public void keyhit(KeyEvent e1)  {
      keye = e1;
      keyhit = true;
  }
  void dokeyhit() {
      KeyEvent e =  keye;
      keye = null;
      keyhit = false;
      char key = (char)e.getKeyChar();
      int code = e.getKeyCode();
      if(running) return;
      if(code == KeyEvent.VK_ESCAPE) {
         if(edittext) edittext = false;
         else cancel();
      }
      else if(!edittext && code == KeyEvent.VK_DELETE ) {
         if(currPart != null) {
            if(mouseNode >= 0 && currPart.ispoly() && oldNode != null) {
               startchange();
               if(!addnewforcontrol()) return;
               removeNode();
            }
            else if(mouseNode >= 0) noise.beep();
            else {
               startchange();
               if(!addnewforcontrol()) return;
               char pos = currPart.group;
               if(activecontrol>=0) {
                  if(im.parts[currPart.frompart].type >=100)
                               im.parts[currPart.frompart].type -=100;
                  else  im.parts[currPart.frompart].type +=100;
               }
               else removePart(currPart.frompart);
               buildactivelist();
               setup();
               setcurrpart(pos);
            }
         }
      }
      else if(code == KeyEvent.VK_F2) { // tablet - end or start
        if(!tablet) {
          tablet = true;
          if(currChoice == -1)
              lastchoice = (short)POLYFILL;
          else lastchoice = currChoice;
        }
        if (mouseNode >= 0) cancel(); // end polygon
        else if(currChoice == -1)  lchoice.setSelectedIndex(currChoice = lastchoice);
        else  clearchoice();
      }
      else if(code == KeyEvent.VK_F3) { // tablet - new color
        cancel();
        colorchooser.setColor(currColor);
        colordialog.setVisible(true);
      }
      else if(code == KeyEvent.VK_F4) { // prev part
        cancel();
        clearchoice();
        endPart();
        currPart = prev(currPart);
      }
      else if(code == KeyEvent.VK_F5) { // next part
        cancel();
        clearchoice();
        endPart();
        currPart = next(currPart);
      }
      else if(code == KeyEvent.VK_F6) { // UNDO
        newChoice(UNDO);
      }
      else if(code == KeyEvent.VK_F7) { // Add new control (like typing '=');
         String slist[] = lcon.getdata();
         int sel[] = lcon.getSelectionRows(),newsel;
         if(slist.length==0) {
           slist = new String[]{"c.1(4<8)"};
           lcon.putdata(slist);
           lcon.setSelectionRow(0);
         }
         else {
           if (sel != null && sel[0] < slist.length) {
             newsel = sel[0] + 1;
             slist = u.addString(slist, "=", sel[0] + 1);
           }
           else {
             newsel = slist.length;
             slist = u.addString(slist, "=");
           }
           lcon.putdata(slist);
           lcon.setSelectionRow(newsel);
           docopycontrol();
         }
      }
      else if(code == KeyEvent.VK_F8) { // save/retrieve colors and images
        new saver();
      }
      else if(!edittext && code == KeyEvent.VK_RIGHT
              && (currPart != null  && mouseNode >= 0 || moving)) {
         if(arrowpoint == null) arrowpoint = new Point(manager.mousexs,manager.mouseys);
         arrowpoint.x = Math.max(0,++arrowpoint.x);
      }
      else if(!edittext && code == KeyEvent.VK_LEFT
               && (currPart != null  && mouseNode >= 0 || moving)) {
          if(arrowpoint == null) arrowpoint = new Point(manager.mousexs,manager.mouseys);
          arrowpoint.x = Math.min(manager.screenwidth,--arrowpoint.x);
      }
      else if(!edittext && code == KeyEvent.VK_UP
               && (currPart != null  && mouseNode >= 0 || moving)) {
         if(arrowpoint == null) arrowpoint = new Point(manager.mousexs,manager.mouseys);
         arrowpoint.y = Math.max(0,--arrowpoint.y);
      }
      else if(!edittext && code == KeyEvent.VK_DOWN
               && (currPart != null  && mouseNode >= 0 || moving)) {
         if(arrowpoint == null) arrowpoint = new Point(manager.mousexs,manager.mouseys);
         arrowpoint.y = Math.min(manager.screenheight,arrowpoint.y+1);
      }
      else if(!edittext && code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_LEFT && !edittext) {
         if(editfunc != null) {editfunc.dispose(); editfunc = null;}
         if(attaching) {
            do {
               if(code == KeyEvent.VK_LEFT) attachPart = prev(attachPart);
               else                attachPart = next(attachPart);
            } while(attachPart == currPart || attachPart.isattached(currPart.group));
         }
         else if(currPart != null) {
            if(mouseNode >= 0) noise.beep();
            else {
               endPart();
               if(code == KeyEvent.VK_LEFT) currPart = prev(currPart);
               else                currPart = next(currPart);
            }
         }
      }
      else if(currPart != null && currPart.frompart>=0
                  && im.parts[currPart.frompart].type == FUNCTION && code == KeyEvent.VK_SPACE) {
              saveTextImage tx = (saveTextImage) im.parts[currPart.frompart];
              setupfunc(currPart);
              startchange();
              if(!addnewforcontrol()) return;
              editfunc = currPart.imfunc.edit(new saveTextImage[] {tx});

      }
      else if(currPart != null && currPart.frompart>=0
                  && im.parts[currPart.frompart].type == TEXT) {
         if(!edittext) {
            startchange();
            if(!addnewforcontrol()) return;
            edittext=true;
            textx = texty = 0;
            saveTextImage tx = (saveTextImage) im.parts[currPart.frompart];
            if(tx.text == null || tx.text.length==0) {
               tx.text = new String[] {""};
            }

         }
         saveTextImage tx = (saveTextImage) im.parts[currPart.frompart];
         if(code == KeyEvent.VK_BACK_SPACE) {
           if(--textx < 0) {
              if(texty>0) {
                 --texty;
                 tx.text[texty] = tx.text[texty] + tx.text[texty+1];
                 tx.text = u.removeString(tx.text,texty+1);
                 textx = tx.text[texty].length();
              }
              else textx=0;
           }
           else  tx.text[texty]
             =  tx.text[texty].substring(0,textx) + tx.text[texty].substring(textx+1);
         }
         else if(code == KeyEvent.VK_END) {
            texty = tx.text.length-1;
            textx = tx.text[texty].length();
         }
         else if(code == KeyEvent.VK_HOME) {
            texty = textx = 0;
         }
         else if(code == KeyEvent.VK_RIGHT) {
            if(++textx > tx.text[texty].length()) {
               if(++texty >= tx.text.length) {--texty;--textx;}
               else textx = 0;
            }
         }
         else if(code == KeyEvent.VK_LEFT) {
            if(--textx < 0) {
               if(--texty < 0) {++texty; textx = 0;}
               else textx = tx.text[texty].length();
            }
         }
         else if(code == KeyEvent.VK_ENTER) {
            tx.text = u.addString(tx.text, tx.text[texty].substring(textx), texty+1);
            tx.text[texty] = tx.text[texty].substring(0,textx);
            ++texty;
            textx = 0;
         }
         else if(code == KeyEvent.VK_DELETE) {
            if(textx == tx.text[texty].length()) {
                if (texty < tx.text.length-1) {
                    tx.text[texty] = tx.text[texty]+tx.text[texty+1];
                    tx.text = u.removeString(tx.text,texty+1);
                }
            }
            else {
               tx.text[texty] = tx.text[texty].substring(0,textx) + tx.text[texty].substring(textx+1);
            }
         }
         else if(key != KeyEvent.CHAR_UNDEFINED) {
            tx.text[texty] =  tx.text[texty].substring(0,textx)
                                   + String.valueOf(key)
                                   + tx.text[texty].substring(textx);
            ++textx;
         }
      }
   }
   //-------------------------------------------------------------------
   imagePart next(imagePart imp) {
      if(p.length==0) return null;
      imagePart  newp = imp;
      do {
         if(newp.group >= p.length-1) newp = p[0];
         else                        newp = p[newp.group+1];
      }while((newp.frompart<0 ) && newp !=  imp );
      return (newp.frompart<0 )?null:newp;
   }
   imagePart prev(imagePart imp) {
      if(p.length==0) return null;
      imagePart  newp = imp;
      do {
         if(newp.group == 0 ) newp = p[p.length-1];
         else                 newp = p[newp.group-1];
      }while((newp.frompart<0 ) && newp !=  imp);
       return (newp.frompart<0 )?null:newp;
  }
  boolean setupfunc(imagePart ip) {
    saveTextImage tx = (saveTextImage) im.parts[ip.frompart];
    if(tx.text == null) {
      int reply[] = new int[]{lastfunc};
      new u.chooselist("Choose function",functions,reply);
      tx.text = new String[] {functions[lastfunc=reply[0]]};
    }
    if(ip.imfunc == null) {
      Class funcClass;
      try {                                                      //Try 2.1.2.1
         funcClass = Class.forName("shark."+tx.text[0]);
       }
       catch(ClassNotFoundException ee) {
            try {                                                      //Try 2.1.2.1
               funcClass = Class.forName("shark."+tx.text[0]+"_base");
             }
             catch(ClassNotFoundException eex) {
                return false;
             }
    //      return false;
       }

       try {                                                               //Try 6
          ip.imfunc = (imagefunc) funcClass.newInstance();
        }
       catch (InstantiationException ee) {                                //Catch 6
         return false;
       }
       catch (IllegalAccessException ee) {                                //Catch 6
         return false;
      }
      ip.imfunc.setup(tx.text);
     }
     return true;
  }
  //------------------------------------------------------------------
  public boolean hasbackgroundpoly() {
     int i,j;
//startPR2008-06-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     if(im==null)return false;
//endPRimagesandvids^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     for(i=0;i<im.parts.length;++i) {
       if (im.parts[i].type == POLYFILL && im.parts[i].group == 0 && im.parts[i].controlno == -1
           && im.parts[i].node.length == 4) {
         Polygon pp = new Polygon();
         for (j = 0; j < im.parts[i].node.length; ++j) {
           int xx = (im.parts[i].node[j].x > BASEU * 4) ? (im.parts[i].node[j].x - BASEU * 8) : im.parts[i].node[j].x;
           pp.addPoint(xx, im.parts[i].node[j].y);
         }
         Rectangle rr = pp.getBounds();
         return rr.width >= (im.x2 - im.x1)*19/20 && rr.height >= (im.y2 - im.y1)*19/20;
       }
     }
     return false;
  }
//startPR2008-06-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  static public boolean addnew(sharkImage.saveSharkImage si1, sharkImage.saveSharkImage si2) {
    if(si1==null || si2==null)return false;
    if(si1.parts.length<1 || si2.parts.length<1)return false;
    if(si1.clip!=null && si2.clip!=null)
      if(!si1.clip.equals(si2.clip))return true;
    if(si1.minsize!=null && si2.minsize!=null)
      if(!si1.minsize.equals(si2.minsize))return true;
    if(si1.controls.length!=si2.controls.length)return true;
    for(int i = 0; i<si1.controls.length; i++){
      sharkImage.saveImageControl sic1 = (sharkImage.saveImageControl)si1.controls[i];
      sharkImage.saveImageControl sic2 = (sharkImage.saveImageControl)si2.controls[i];
      if(sic1.max!=sic2.max)return true;
      if(sic1.min!=sic2.min)return true;
      if(!sic1.name.equals(sic2.name))return true;
      if(sic1.nextcontrol!=null && sic2.nextcontrol!=null)
        if(!sic1.nextcontrol.equals(sic2.nextcontrol))return true;
    }
    if(si1.parts.length!=si2.parts.length)return true;
    for(int i = 0; i<si1.parts.length; i++){
      sharkImage.saveImagePart sip1 = (sharkImage.saveImagePart)si1.parts[i];
      sharkImage.saveImagePart sip2 = (sharkImage.saveImagePart)si2.parts[i];
      if(sip1.a!=sip2.a)return true;
      if(sip1.attachno!=sip2.attachno)return true;
      if(!(sip1.color==null && sip2.color==null)){
        if(sip1.color ==null || sip2.color ==null)return true;
      }

      if(sip1.controlno!=sip2.controlno)return true;
      if(sip1.group!=sip2.group)return true;
      if(sip1.type!=sip2.type)return true;
      if(sip1.x!=sip2.x)return true;
      if(sip1.y!=sip2.y)return true;
      if(!(sip1.node==null && sip1.node==null)){
        if(sip1.node ==null || sip2.node ==null)return true;
        if(sip1.node.length!=sip2.node.length)return true;
      }
      if(!Arrays.equals(sip1.node,sip2.node))return true;
    }
    return false;
  }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   //--------------------------------------------------------------
   public static class saveSharkImage  implements Serializable {
      static final long serialVersionUID = -1627841784987345959L;
      int x1= -BASEU/2,x2=BASEU/2,y1=-BASEU/2,y2=BASEU/2;
      int radius = BASEU/2;
      Rectangle clip;
      Rectangle minsize;
      saveImagePart parts[] = new saveImagePart[0];
      saveImageControl controls[] = new saveImageControl[0];

   }
   public static class saveImagePart  implements Serializable{
     static final long serialVersionUID = -2085575937492572148L;
         char type;
         char group;   // group no of this part
         Color color;
         Point node[];
         double a;
         short controlno=-1;   //subject to control no
         short attachno=-1;    // attached to part no
         int x,y;           // where attached
   }
   public static class saveImageControl  implements Serializable {
         static final long serialVersionUID = 6822533245199201822L;
         String name,nextcontrol;
         int max, min;
   }
   public static class saveTextImage extends saveImagePart  implements Serializable {
     static final long serialVersionUID = 7929749189952136726L;
         String text[];
   }
  //----------------------------------------------------------------
   
   class imagePart {   // one for each part group
      short frompart=-1,topart=-1;   // mutation details
      long start,end;
      char group;
      short controlledby = -1;
      Point drawnode[],tempnode[];

      int ax,ay;           // current x,y
      double a;            // current orientation

      int ox,oy;           // actual x,y
      int cos,sin;
      double aa;
      boolean setupok;
      Color color;
      Font font;
      FontMetrics m;
      int lastheight,lastwidth;
      imagefunc imfunc;

      public imagePart(char gno) {
         group = gno;
         for(short i = 0;i<im.parts.length;++i) {
            if(im.parts[i].group == group && im.parts[i].controlno >=0) {
                controlledby = im.parts[i].controlno;
                break;
            }
         }
      }
      void init() {
        if(frompart < 0) return;
        saveImagePart f = im.parts[frompart];
        a = f.a;
        ax = f.x;
        ay = f.y;
        if(im.parts[frompart].type == FUNCTION
           && imfunc != null
           && ((saveTextImage)im.parts[frompart]).text != null)
          imfunc.setup(((saveTextImage)im.parts[frompart]).text);
     }
      void endmove() {
         short i;
         Point mousepos = mousePoint();
         im.parts[frompart].x += mousepos.x - startMouse.x;
         im.parts[frompart].y += mousepos.y - startMouse.y;
         ax += mousepos.x - startMouse.x;
         ay += mousepos.y - startMouse.y;
      }
      Point avPoint() {
         saveImagePart pp = im.parts[frompart];
         int xtot=0,ytot=0;
         for(short i=0;i<pp.node.length;++i) {
            int xx = (pp.node[i].x > BASEU*4) ? (pp.node[i].x - BASEU*8) : pp.node[i].x;
            xtot += ax +(int)(xx*Math.cos(aa) - pp.node[i].y*Math.sin(aa));
            ytot += ay +(int)(xx*Math.sin(aa) + pp.node[i].y*Math.cos(aa));
         }
         return new Point(xtot/pp.node.length, ytot/pp.node.length);
      }
      boolean isattached(char testgroup) {
         for(short i=0;i<im.parts.length;++i) {
            if(im.parts[i].group == group && im.parts[i].attachno >= 0) {
                if (im.parts[i].attachno == testgroup) return true;
                if (p[im.parts[i].attachno].isattached(testgroup)) return true;
            }
         }
         return false;
      }
       boolean anyunattached() {
         for(short i=0;i<im.parts.length;++i) {
            if(im.parts[i].group != group && !p[im.parts[i].group].isattached(group))
                 return true;
         }
         return false;
      }
      void moveattach(Point to) {
        saveImagePart pp = im.parts[frompart];
        int x1,y1,i,xx;
        boolean attchange = false;
        for(i=0;i<pp.node.length;++i) {
           xx = (pp.node[i].x > BASEU*4) ? (pp.node[i].x - BASEU*8) : pp.node[i].x;
           x1 = ax +(int)(xx*Math.cos(aa) - pp.node[i].y*Math.sin(aa)) - to.x;
           y1 = ay +(int)(xx*Math.sin(aa) + pp.node[i].y*Math.cos(aa)) - to.y;
           if(pp.node[i].x > BASEU*4)
              pp.node[i].x = (int)(x1*Math.cos(-aa) - y1*Math.sin(-aa)) + BASEU*8;
           else pp.node[i].x = (int)(x1*Math.cos(-aa) - y1*Math.sin(-aa));
           pp.node[i].y = (int)(x1*Math.sin(-aa) + y1*Math.cos(-aa));
         }
         for(i=0;i<im.parts.length;++i) {
            if(im.parts[i].attachno==group) {
               xx =  im.parts[i].x;
               x1 = ax +(int)(xx*Math.cos(aa) - im.parts[i].y*Math.sin(aa)) - to.x;
               y1 = ay +(int)(xx*Math.sin(aa) + im.parts[i].y*Math.cos(aa)) - to.y;
               im.parts[i].x = (int)(x1*Math.cos(-aa) - y1*Math.sin(-aa));
               im.parts[i].y = (int)(x1*Math.sin(-aa) + y1*Math.cos(-aa));
               attchange = true;
            }
         }
         pp.x = ax = to.x;
         pp.y = ay = to.y;
         if(attchange) {
               endPart();
               char pos = currPart.group;
               buildactivelist();
               setup();
               setcurrpart(pos);
         }
      }
      boolean setCurr() {    // set curr compromise between from and to
         int i;
         long xcurrtime,xstart,xend;
         if(frompart<0) {setupok = true; return true;}
         short at = im.parts[frompart].attachno;
         if(at >= 0  && !p[at].setupok) return false;
         setupok = true;
         color =  im.parts[frompart].color;
         if(frompart != topart && !frozen) {
            if(mousecontrol>=0 && im.parts[frompart].controlno == mousecontrol) {
                xcurrtime = mousedist();
                xstart = 0;
                xend = 1000;
            }
            else {
               xcurrtime = currtime;
               xstart = start;
               xend = end;
            }
            saveImagePart f = im.parts[frompart], t = im.parts[topart];
            if(f.color != t.color) color = u.mix(f.color,t.color,(int)(xcurrtime-xstart),(int)(xend-xstart));
            if(tempnode == null || tempnode.length != f.node.length)
                      tempnode = new Point[f.node.length];
            for (i=0;i<f.node.length;++i) {
                tempnode[i] = new Point((int)((f.node[i].x*(xend-xcurrtime) + t.node[i].x*(xcurrtime-xstart))/(xend-xstart)),
                                      (int)((f.node[i].y*(xend-xcurrtime) + t.node[i].y*(xcurrtime-xstart))/(xend-xstart)));
            }
            a = midangle(f.a, t.a, (int)(xcurrtime-xstart), (int)(xend-xstart));
            ax =(int)((f.x*(xend-xcurrtime) + t.x*(xcurrtime-xstart))/(xend-xstart));
            ay =(int)((f.y*(xend-xcurrtime) + t.y*(xcurrtime-xstart))/(xend-xstart));
            drawnode = tempnode;
            if(im.parts[frompart].type == FUNCTION && imfunc!=null
                && ((saveTextImage)im.parts[frompart]).text !=null
                && !((saveTextImage)im.parts[frompart]).text.equals(((saveTextImage)im.parts[topart]).text)) {
               imfunc.interpolate( ((saveTextImage)im.parts[frompart]).text, ((saveTextImage)im.parts[topart]).text,
                                   xstart,xend,currtime);

            }
         }
         else drawnode = im.parts[frompart].node;
         if(at >= 0) {
            ox = p[at].getx(new Point(ax,ay));
            oy = p[at].gety(new Point(ax,ay));
            aa = a + p[at].aa;
         }
         else {
           ox = midx + ax*endw/startw;
           oy = midy + ay*endh/starth;
           aa = a;
         }
         if(building)  {
            if(rotating   && (currPart == this && rotlist==null
                   ||  rotlist!=null  && u.inlist(rotlist,(int)group))) {
               if(rotlist == null) aa += rotangle();
               else {
                   double aaa;
                   aa += (aaa = p[rotlist[0]].rotangle());
                   if(this != p[rotlist[0]]) {
                      int dx = ax -  p[rotlist[0]].ax;
                      int dy = ay -  p[rotlist[0]].ay;
                      double dd = Math.sqrt(dx*dx + dy*dy);
                      double aaa2 = Math.atan2(dy,dx) + aaa;
                      ox =  midx + (int)((p[rotlist[0]].ax + dd * Math.cos(aaa2))*endw/startw) ;
                      oy =  midy + (int)((p[rotlist[0]].ay + dd * Math.sin(aaa2))*endh/starth) ;
                   }
               }
            }
            else if(moving && (currPart == this && movelist==null
                   ||  movelist!=null  && u.inlist(movelist,(int)group))) {
               Point mousepos = mousePoint();
               ox +=  (mousepos.x - startMouse.x) * manager.screenmax/BASEU ;
               oy +=  (mousepos.y - startMouse.y) * manager.screenmax/BASEU ;
            }
         }
         cos = (int)(Math.cos(aa)*FACTOR);
         sin = (int)(Math.sin(aa)*FACTOR);
         return true;
      }
      double rotangle() {
         Point mousepos = mousePoint();
         return  Math.atan2(mousepos.y - ay,mousepos.x - ax)
               - Math.atan2(startMouse.y - ay,startMouse.x -ax);
      }
      void getparts() {
         short i;
         for(i=0;i<im.parts.length;++i) {
            if(im.parts[i].group == group && im.parts[i].controlno <0) {
               frompart = topart = i;
               init();
               break;
            }
         }
      }
      boolean ispoly() {
            switch(im.parts[frompart].type) {
               case POLYGON:  case POLYLINE: case POLYFILL: return true;
            }
            return false;
      }
      void detach() {
         if(im.parts[frompart].attachno >= 0) {
            im.parts[frompart].a = a = aa;
            im.parts[frompart].x = ax = actualx();
            im.parts[frompart].y = ay = actualy();
            im.parts[frompart].attachno = -1;
         }
      }
      void attach() {
            im.parts[frompart].a = a = a - attachPart.aa;
            int x1 = (actualx() - attachPart.actualx());
            int y1 = (actualy() - attachPart.actualy());

            im.parts[frompart].x = ax = (int)(x1*Math.cos(-attachPart.aa) - y1*Math.sin(-attachPart.aa));
            im.parts[frompart].y = ay = (int)(x1*Math.sin(-attachPart.aa) + y1*Math.cos(-attachPart.aa));
            im.parts[frompart].attachno = (short)attachPart.group;
      }
      int actualx() {
         return  (ox-manager.screenwidth/2)*BASEU/manager.screenmax;
      }
      int actualy() {
         return  (oy-manager.screenheight/2)*BASEU/manager.screenmax;
      }

      boolean ismissing() {
         return frompart < 0 || im.parts[frompart].controlno >= 0
                  && !c[im.parts[frompart].controlno].active
             || im.parts[frompart].attachno >= 0
                  && group != im.parts[frompart].attachno
                  && p[im.parts[frompart].attachno].ismissing();
      }
      void paint(Graphics g)  {
//startPR2012-06-15^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         Rectangle speakerRect = null;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         int x1=0,x2=0,y1=0,y2=0,i;
         int xx1,yy1,ww1,hh1,thickness;
         short pos,pos2;
         saveTextImage tx;
         Polygon newpoly=null;
         if(building && (ismissing()
             || drawnode ==null || drawnode.length<2 || drawnode[0]==null || drawnode[1]==null)) return;
         if(building && !running && !moving && currPart == this && mouseNode >= 0 && extrapoly == null) {
           x1 = mousePoint().x - actualx();
           y1 = mousePoint().y - actualy();
           boolean smoothing = (im.parts[frompart].node[mouseNode].x > BASEU*4);
           im.parts[frompart].node[mouseNode].x
                = (int)(x1*Math.cos(-aa) - y1*Math.sin(-aa));
           if(smoothing) im.parts[frompart].node[mouseNode].x += BASEU*8;
           im.parts[frompart].node[mouseNode].y
                = (int)(x1*Math.sin(-aa) + y1*Math.cos(-aa));
         }
         saveImagePart pp = im.parts[frompart];
         g.setColor(black?Color.black:(darker?color.darker():color));
         if(pp.type != TEXT) savedfont = null;
         if(pp.attachno<-1) thickness = (-pp.attachno)*endw/startw;
         else thickness = 0;
         switch(pp.type) {
            case LINE:
               if(thickness>1)
                 g.fillPolygon(newpoly = thickline(getxx(drawnode[0]), getyy(drawnode[0]),
                        getxx(drawnode[1]), getyy(drawnode[1]),thickness));
               else g.drawLine(x1=getxx(drawnode[0]), y1=getyy(drawnode[0]),
                        x2=getxx(drawnode[1]), y2=getyy(drawnode[1]));
               break;
            case POLYLINE:
               newpoly = getp(drawnode);
               if(thickness>1)
                  g.fillPolygon(thickpolyline(newpoly,thickness));
               else g.drawPolyline(newpoly.xpoints,newpoly.ypoints,newpoly.npoints);
               break;
            case POLYGON:
               if(thickness>1)
                  g.fillPolygon(thickpoly(newpoly = getp(drawnode),thickness));
               else g.drawPolygon(newpoly = getp(drawnode));
               break;
            case POLYFILL:
               if(drawnode == null) {
                   noise.beep();
               }
               newpoly = getp(drawnode);
               g.drawPolygon(newpoly);
               if(!ghost) g.fillPolygon(newpoly);
                break;
            case RECTANGLE:
               if(angle == 0 && aa == 0) {
                  x1 = getxx(drawnode[0]);
                  y1 = getyy(drawnode[0]);
                  x2 = getxx(drawnode[1]);
                  y2 = getyy(drawnode[1]);
                  if(thickness>1) {
                     xx1=Math.min(x1,x2);
                     yy1=Math.min(y1,y2);
                     ww1=Math.abs(x2-x1);
                     hh1=Math.abs(y2-y1);
                     for(i=0;i < thickness;++i)
                       g.drawRect(xx1+i,yy1+i, ww1-i*2,hh1-i*2);
                  }
                  else g.drawRect(Math.min(x1,x2),Math.min(y1,y2), Math.abs(x2-x1),Math.abs(y2-y1));
               }
               else {
                 newpoly = getp(new Point[] {drawnode[0],new Point(drawnode[1].x,drawnode[0].y),
                                               drawnode[1],new Point(drawnode[0].x,drawnode[1].y) });
                 if(thickness>1)
                     g.fillPolygon(thickpoly(newpoly,thickness));
                 else  g.drawPolygon(newpoly);
               }
               break;
            case RECTFILL:
               if(angle == 0 && aa == 0) {
                  x1 = getxx(drawnode[0]);
                  y1 = getyy(drawnode[0]);
                  x2 = getxx(drawnode[1]);
                  y2 = getyy(drawnode[1]);
                  int xx = Math.min(x1,x2);
                  int yy = Math.min(y1,y2);
                  int ww = Math.abs(x2-x1);
                  int hh = Math.abs(y2-y1);
                  if(roundrect && group == 0) {
                     if(ghost) g.drawRoundRect(xx,yy,ww,hh,ww/8,hh/8);
                     else g.fillRoundRect(xx,yy,ww,hh,ww/8,hh/8);
                  }
                  else {
                     g.drawRect(xx,yy,ww, hh);
                     if(!ghost) g.fillRect(xx,yy, ww, hh);
                  }
               }
               else {
                  newpoly = getp(new Point[] {drawnode[0],new Point(drawnode[1].x,drawnode[0].y),
                                                drawnode[1],new Point(drawnode[0].x,drawnode[1].y) });
                  g.drawPolygon(newpoly);
                  if(!ghost) g.fillPolygon(newpoly);
               }
               break;
            case ARCFILL:
                  x1 = getxx(drawnode[0]);
                  y1 = getyy(drawnode[0]);
                  x2 = getxx(drawnode[1]);
                  y2 = getyy(drawnode[1]);
                  int x3 = getxx(drawnode[2]);
                  int y3 = getyy(drawnode[2]);
                  int rad = (int)Math.sqrt((x2-x1)*(x2-x1) + (y2-y1)*(y2-y1));
                  int a1 = ((int)(Math.atan2(y1-y2,x2-x1) * 180 / Math.PI) + 360)%360;
                  int a2 = (int)(Math.atan2(y1-y3,x3-x1) * 180 / Math.PI) - a1;
                  while(a2<0) a2 += 360;
                  if(lefttoright) a2 = -(360-a2);
                  x2 = x1+rad; y2=y1+rad;
                  x1 -= rad; y1-=rad;
                  if(ghost) g.drawArc(x1,y1,rad*2,rad*2,a1,a2);
                  else g.fillArc(x1,y1,rad*2,rad*2,a1,a2);
                  break;
            case OVAL:
               if(angle == 0 && aa == 0) {
                  x1 = getxx(drawnode[0]);
                  y1 = getyy(drawnode[0]);
                  x2 = getxx(drawnode[1]);
                  y2 = getyy(drawnode[1]);
                  if(thickness>1) {
                    xx1=Math.min(x1,x2);
                    yy1=Math.min(y1,y2);
                    ww1=Math.abs(x2-x1)/2;
                    hh1=Math.abs(y2-y1)/2;
                    Polygon p = new Polygon();
                    addarc(p,xx1+ww1, yy1+hh1,ww1,hh1,0,64);
                    addarc(p,xx1+ww1, yy1+hh1,ww1-thickness,hh1-thickness,64,0);
                    g.fillPolygon(p);
                  }
                  else g.drawOval(Math.min(x1,x2),Math.min(y1,y2), Math.abs(x2-x1),Math.abs(y2-y1));
               }
               else {
                  if(thickness>1) {
                    g.fillPolygon(thickpoly(newpoly =  poval(drawnode),thickness));
                  }
                  else    g.drawPolygon(newpoly = poval(drawnode));
               }
               break;
            case OVALFILL:
               if(angle == 0 && aa == 0) {
                  x1 = getxx(drawnode[0]);
                  y1 = getyy(drawnode[0]);
                  x2 = getxx(drawnode[1]);
                  y2 = getyy(drawnode[1]);
                  if(ghost)g.drawOval(Math.min(x1,x2), Math.min(y1,y2), Math.abs(x2-x1),Math.abs(y2-y1));
                  else g.fillOval(Math.min(x1,x2), Math.min(y1,y2), Math.abs(x2-x1),Math.abs(y2-y1));
               }
               else {
                  newpoly = poval(drawnode);
                  g.drawPolygon(newpoly);
                  if(!ghost) g.fillPolygon(newpoly);
               }
               break;
             case FUNCTION:
               if(!(im.parts[frompart] instanceof saveTextImage)) break;
               tx = (saveTextImage)im.parts[frompart];
               if(setupfunc(this)) {
                   x1 = getxx(drawnode[0]);
                   y1 = getyy(drawnode[0]);
                   x2 = getxx(drawnode[1]);
                   y2 = getyy(drawnode[1]);
                   if(imfunc.keepnodeorder())
                     imfunc.paint(g,x1, y1, x2-x1,y2-y1);
                   else imfunc.paint(g,Math.min(x1,x2), Math.min(y1,y2), Math.abs(x2-x1),Math.abs(y2-y1));
                }
                 break;
            case TEXT:
               if(!(im.parts[frompart] instanceof saveTextImage)) break;
               tx = (saveTextImage)im.parts[frompart];
               String[] text =  tx.text;
               if(text == null || text.length == 0 || text.length == 1 && text[0].length()==0) {
                   if(building)  text =  new String[] {"text"};
                   else break;
               }
               if(!building && spellchange.active) {
                  boolean got=false;
                  if(spellchanged!=null) for(i=0;i<spellchanged.length;++i) {
                    if(text == spellchanged[i]) {got=true; text = spellchangedto[i]; break;}
                  }
                  if(!got) {
                    if(spellchanged == null) {
                      spellchanged = new String[][] {text};
                      spellchangedto = new String[][] {text = spellchange.spellchange(text)};
                    }
                    else {
                      String sc[][]=new String[spellchanged.length+1][];
                      String scto[][]=new String[spellchanged.length+1][];
                      System.arraycopy(spellchanged,0,sc,0,spellchanged.length);
                      System.arraycopy(spellchangedto,0,scto,0,spellchangedto.length);
                      sc[spellchanged.length]  = text;
                      scto[spellchanged.length] = text = spellchange.spellchange(text);
                      spellchanged = sc;
                      spellchangedto = scto;
                    }
                  }
               }
               int fx = Math.min(x2=getxx(drawnode[1]),x1=getxx(drawnode[0]));
               int fy = Math.min(y2=getyy(drawnode[1]),y1=getyy(drawnode[0]));
               int fwidth,fheight;
               if(aa + angle != 0) {
                 fwidth = Math.abs(drawnode[0].x-drawnode[1].x)*endw/startw;
                 fheight = Math.abs(drawnode[0].y-drawnode[1].y)*endh/starth;
               }
               else {
                 fwidth = Math.abs(x2 - x1);
                 fheight = Math.abs(y2 - y1);
               }
               int points;
               if(icon && text[0].startsWith("\"")) {
                 points = 8;
                 font = sharkStartFrame.treefont;
                 do {
                   font = font.deriveFont((float)(points += 4));
                   m = sharkStartFrame.mainFrame.getFontMetrics(font);
                 }
                 while (m.getHeight() < fheight
                        && points < 80);
                 do {
                   font = font.deriveFont((float)--points);
                   m = sharkStartFrame.mainFrame.getFontMetrics(font);
                 }
                 while (m.getHeight() * text.length > fheight && points > 4);
                 while (points > 4 && m.stringWidth(text[0]) > fwidth) {
                     font = font.deriveFont((float)--points);
                     m = sharkStartFrame.mainFrame.getFontMetrics(font);
                 }
               }
               else if(savedfont != null) {
                  font = savedfont;
                  m  = sharkStartFrame.mainFrame.getFontMetrics(font);
               }
               else if(!gotfixedfont ) {
//startPR2007-11-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//                  int style;
//                  String fname;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                  if(font == null || building || lastheight != fheight || lastwidth != fwidth) {
                    if (icon && fixedfont != null) {
                      points = (font = fixedfont).getSize() + 1;
                    }
                    else {
//startPR2007-10-10^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//                     font = sharkStartFrame.treefont;
                      font = help?sharkStartFrame.gamehelpfont:sharkStartFrame.treefont;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                      points = 8;
//startPR2007-11-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//                      style = font.getStyle();
//                      fname = font.getName();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                      do {
//startPR2007-11-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//                        font = new Font(fname, style, points += 4);
                        font = font.deriveFont((float)(points += 4));
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                        m = sharkStartFrame.mainFrame.getFontMetrics(font);
                      }
                      while (m.getHeight() * text.length < fheight
                             && points < 400);
                    }
//startPR2007-11-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//                    style = font.getStyle();
//                    fname = font.getName();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                    do {
//startPR2007-11-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//                        font = new Font(fname, style, --points);
                      font = font.deriveFont((float)--points);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                      m = sharkStartFrame.mainFrame.getFontMetrics(font);
                    }
                    while (m.getHeight() * text.length > fheight && points > 4);
                    for (i = 0; i < text.length; ++i) {
                      while (points > 4 && m.stringWidth(text[i]) > fwidth) {
//startPR2007-11-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//                        font = new Font(fname, style, --points);
                        font = font.deriveFont((float)--points);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                        m = sharkStartFrame.mainFrame.getFontMetrics(font);
                      }
                    }
                    lastheight = fheight;
                    lastwidth = fwidth;
                  }
                  if(icon) fixedfont = font;
                  if(!help) savedfont = font;
               }
               else {
                  font = fixedfont;
                  m = sharkStartFrame.mainFrame.getFontMetrics(font);
               }
               int lineht = m.getHeight();
               g.setFont(font);
               if(cansay && !manager.mouseOutside && manager.mousexs > fx
                         && manager.mousexs < fx + fwidth
                         && manager.mouseys > fy
                         && manager.mouseys < fy + fheight) {
                  g.setColor(Color.red);
//startPR2012-06-15^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                  if((!(text!=null && text.length==1 && text[0].length()<3)) && runningGame.currGameRunner!=null && runningGame.currGameRunner.game!=null){
                       speakerRect = new Rectangle(fx+2, fy+2, runningGame.currGameRunner.game.speakerIm.getWidth(null),
                       runningGame.currGameRunner.game.speakerIm.getHeight(null));
                   
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                      g.drawRect(fx,fy,fwidth,fheight);
                      g.drawRect(fx+1,fy+1,fwidth-2,fheight-2);
                      newmarkedforsay = (short)this.group;
                  }
//                  g.setColor(manager.getBackground());
//                  runMovers.earim.lefttoright=true;
//                  g.fillRect(fx+fwidth-runMovers.earimw*2, fy+fheight-1, runMovers.earimw,3);
//                  runMovers.earim.paint(g,fx+fwidth-runMovers.earimw*2,fy+fheight-runMoversearimh/2,
//                         runMovers.earimw, runMovers.earimh);
                  g.setColor(color);
               }
               if(edittext && currPart == this) {
                  g.setColor((u.rand(2)==0)?Color.white:Color.green);
                  int ym = fy + (fheight - lineht*text.length)/2+ texty*lineht;
                  int xm = fx + (fwidth - m.stringWidth(text[texty]))/2
                     + m.stringWidth(text[texty].substring(0,textx));
                  g.drawLine(xm-2,ym,xm+3,ym);
                  g.drawLine(xm-2,ym+lineht,xm+3,ym+lineht);
                  g.drawLine(xm,ym,xm,ym+lineht);
                  g.setColor(color);
               }
               if(aa + angle != 0) {
                 int midx = (x1+x2)/2;
                 int midy = (y1+y2)/2;
                 int wi = Math.abs(drawnode[0].x-drawnode[1].x)*endw/startw;
                 int hi = Math.abs(drawnode[0].y-drawnode[1].y)*endh/starth;
                 java.awt.geom.AffineTransform saveAT = ((Graphics2D)g).getTransform();
                 ((Graphics2D)g).rotate(aa+angle,midx,midy);
                 fy = midy-hi/2 + (fheight - lineht*text.length)/2 + m.getAscent();
                 for(i=0;i<text.length;++i,fy += lineht)  {
                    g.drawString(text[i],midx - m.stringWidth(text[i])/2,fy);
                 }
                  ((Graphics2D)g).setTransform(saveAT);
               }
               else {
                 fy += (fheight - lineht * text.length) / 2 + m.getAscent();
                 for (i = 0; i < text.length; ++i, fy += lineht) {
                   g.drawString(text[i], fx + (fwidth - m.stringWidth(text[i])) / 2,
                                fy);
                 }
               }
//startPR2012-06-15^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
              if(speakerRect!=null){
                  float curralpha = 0.8f;
                  Graphics2D g2d = (Graphics2D) g;
                  g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,curralpha));
                  g2d.drawImage(runningGame.currGameRunner.game.speakerIm, speakerRect.x, speakerRect.y, speakerRect.width, speakerRect.height, null);
                  g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
               }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
               break;
          }
         if(running && pp.type <= ENDTYPES) {
           Rectangle r = null;
            if(pp.type == FUNCTION) {
              r = imfunc.getBounds();
            }
            else if(newpoly != null) {
               r = newpoly.getBounds();
            }
            if(r != null) {
               x1  = r.x-midx-1;
               y1  = r.y-midy-1;
               x2  = x1 + r.width+2;
               y2  = y1 + r.height+2;
            }
            else {
               if(x1<x2) {
                  x1  = x1-midx-1;
                  x2  = x2-midx+1;
               }
               else {
                  int oldx2=x2;
                  x2  = x1-midx+1;
                  x1  = oldx2-midx-1;
               }
               if(y1<y2) {
                  y1  = y1-midy-1;
                  y2  = y2-midy+1;
               }
               else {
                  int oldy2 = y2;
                  y2  = y1-midy+1;
                  y1  = oldy2-midy-1;
               }
            }
            im.x1 = Math.min(im.x1,x1*startw/endw);
            im.y1 = Math.min(im.y1,y1*starth/endh);
            im.x2 = Math.max(im.x2,x2*startw/endw);
            im.y2 = Math.max(im.y2,y2*starth/endh);
            int x3,y3;
            x3 = (im.x1 +  im.x2)/2*endw/startw;
            y3 = (im.y1 +  im.y2)/2*endh/starth;
            if(newpoly != null) {
               im.radius = Math.max(im.radius,
                   (int)Math.sqrt((double)u.maxdist(newpoly,midx +x3,midy +y3)+1)*startw/endw );
            }
            else {
               im.radius = Math.max(im.radius,
                       ((int)Math.sqrt((double)((x1-x3)*(x1-x3)+(y1-y3)*(y1-y3)))+1)*startw/endw);
               im.radius = Math.max(im.radius,
                       ((int)Math.sqrt((double)((x2-x3)*(x2-x3)+(y2-y3)*(y2-y3)))+1)*startw/endw);
               if(pp.type != LINE) {
                  im.radius = Math.max(im.radius,
                       ((int)Math.sqrt((double)((x2-x3)*(x2-x3)+(y1-y3)*(y1-y3)))+1)*startw/endw);
                  im.radius = Math.max(im.radius,
                       ((int)Math.sqrt((double)((x1-x3)*(x1-x3)+(y2-y3)*(y2-y3)))+1)*startw/endw);
               }
            }
        }
      }
      void paintnodes(Graphics g) {
         short i;
         int x,y,x2,y2;
         if(drawnode ==null || drawnode.length<2 || drawnode[0]==null || drawnode[1]==null) return;
         Color normalcol=(u.rand(2)==0)?Color.white:Color.black;
         Point pt;
        if(ispoly() && drawnode.length > 4) {
          g.setColor(u.rand(2)==0 ? Color.red:Color.white);
          if(drawnode[0].x > BASEU*4)   {
             pt=new Point(drawnode[0].x - BASEU*8,drawnode[0].y);
             x = getxx(pt);
             y = getyy(pt);
          }
          else   {
             x = getxx(drawnode[0]);
             y = getyy(drawnode[0]);
          }
          if(drawnode[1].x > BASEU*4)   {
             pt=new Point(drawnode[1].x - BASEU*8,drawnode[1].y);
             x2 = getxx(pt);
             y2= getyy(pt);
          }
          else   {
             x2 = getxx(drawnode[1]);
             y2 = getyy(drawnode[1]);
          }
           double a = Math.atan2(y2-y,x2-x);
           int rad = manager.screenwidth/40;
           int hx = x + (int)(rad*2*Math.cos(a));
           int hy = y + (int)(rad*2*Math.sin(a));
           g.drawLine(x,y,hx,hy);
           int hx1 = hx - (int)(rad*Math.cos(a+Math.PI/8));
           int hy1 = hy - (int)(rad*Math.sin(a+Math.PI/8));
           int hx2 = hx - (int)(rad*Math.cos(a-Math.PI/8));
           int hy2 = hy - (int)(rad*Math.sin(a-Math.PI/8));
           g.fillPolygon(new int[]{hx,hx1,hx2}, new int[]{hy,hy1,hy2},3);
         }
         g.setColor(normalcol);
         for(i=0;i<drawnode.length;++i) {
            if(drawnode[i].x > BASEU*4)   {
               g.setColor((u.rand(3)==0)?Color.yellow:Color.red);
               pt=new Point(drawnode[i].x - BASEU*8,drawnode[i].y);
               x = getxx(pt);
               y = getyy(pt);
            }
            else   {
               g.setColor(normalcol);
               x = getxx(drawnode[i]);
               y = getyy(drawnode[i]);
            }
            if(ispoly()) {
              g.fillOval(x - 1, y - 1, 3, 3);
            }
            else {
              g.drawLine(x - nodedist / 2, y, x + nodedist / 2, y);
              g.drawLine(x, y - nodedist / 2, x, y + nodedist / 2);
            }
            if(drawnode[i].x > BASEU*4)   {
               int newdist = nodedist/3;
               g.drawLine(x-newdist,y-newdist,x+newdist,y+newdist);
               g.drawLine(x+newdist,y-newdist,x-newdist,y+newdist);
            }
         }
      }
      int getx(Point pt) {
         return ox + (pt.x*cos/FACTOR - pt.y*sin/FACTOR)*endw/startw;
      }
      int gety(Point pt) {
         return oy + (pt.x*sin/FACTOR + pt.y*cos/FACTOR)*endh/starth;
      }
      int getxx(Point pt) {
         int x1;
         if(angle != 0) {
            x1 = realmidx + (getx(pt)-realmidx)*cosa/FACTOR - (gety(pt)-realmidy)*sina/FACTOR;
         }
         else  x1 = getx(pt);
         return lefttoright ? (realmidx*2 - x1) :  x1;
      }
      int getyy (Point pt) {
         int y1;
         if(angle != 0) {
            y1 = realmidy + (getx(pt)-realmidx)*sina/FACTOR + (gety(pt)-realmidy)*cosa/FACTOR;
         }
         else  y1 = gety(pt);
         return upsidedown ? (midy*2 - y1) :  y1;
      }

      Polygon getp(Point pt[]) {
         Polygon p = new Polygon();
         short i;
         Point pt1;
         boolean smoothing=false;
         for(i =0; i<pt.length; ++i) {
             if(pt[i].x > BASEU*4){
               smoothing=true;
               pt1 = new Point(pt[i].x - BASEU*8, pt[i].y);
               p.addPoint(getxx(pt1), getyy(pt1));
            }
            else p.addPoint(getxx(pt[i]), getyy(pt[i]));
         }
         if(smoothing) {
            Polygon p2 = new Polygon();
            for (i=0;i<pt.length;++i) {
              if( pt[i].x > BASEU*4 || pt[(i+1)%p.npoints].x > BASEU*4)  {
                 u.drawcurve(p2, p.xpoints[i], p.ypoints[i],
                          Math.atan2(p.ypoints[(i+1)%p.npoints] - p.ypoints[(i-1+p.npoints)%p.npoints],
                                     p.xpoints[(i+1)%p.npoints] - p.xpoints[(i-1+p.npoints)%p.npoints]),
                                  p.xpoints[(i+1)%p.npoints], p.ypoints[(i+1)%p.npoints],
                          Math.atan2(p.ypoints[(i+2)%p.npoints] - p.ypoints[i],
                                     p.xpoints[(i+2)%p.npoints] - p.xpoints[i]));

              }
              else if(p.xpoints[(i-1+p.npoints)%p.npoints] < BASEU*4)
                  p2.addPoint(p.xpoints[i],p.ypoints[i]);
            }
            return p2;
         }
         return p;
      }
       Polygon poval(Point pt[]) {
         int x = (pt[0].x + pt[1].x)/2;
         int y = (pt[0].y + pt[1].y)/2;
         int a = Math.abs(pt[0].x - pt[1].x)/2;
         int b = Math.abs(pt[0].y - pt[1].y)/2;
         int n = 64/Math.min(64,Math.max(1,(a+b)/2));
         Point ptn = new Point(0,0);
         Polygon p = new Polygon();
         for(short i = 0; i<64; i+=n) {
            ptn.x = x+a*ocos[i]/FACTOR;
            ptn.y = y+b*osin[i]/FACTOR;
            p.addPoint(getxx(ptn), getyy(ptn));
         }
         return p;
      }
   }
   //------------------------------------------------------------------
   class imageControl {       // one for each group
      long start, end;
      short cno,nextc;
      boolean active;

      public imageControl(short tno) {
        cno = tno;
       }
      void qactivate() {
        imageControl nxt = this;
        if(!im.controls[cno].name.equalsIgnoreCase("normal"))
             for(short i=0;i<c.length;++i) {
           if(samecgroup(i) && c[i].active) {
                  return;    // first member of group already active
           }
        }
        activate();
      }
      void next() {
         if(end != 0  && currtime > end) {
//            short i,j;
            deactivate();
            c[nextc].activate();
           if(shark.doImageScreenshots && isanimated){
               if(nextc < cno){  
                int i;
                for(i = 0; i < icgroups.size(); i++){
                    int[] icg = (int[])icgroups.get(i);
                    if(icg!=null && u.inlist(icg, cno)){
                        icgroups.set(i, null);
                        break;
                    }
                }
                boolean allnull = true;
                for(i = 0; i < icgroups.size(); i++){
                    if(icgroups.get(i) != null){
                        allnull = false;
                        break;
                    }
                }   
                if(allnull)
                    done = true;
               }
               else if(im.controls.length == cno+2 && im.controls[im.controls.length-1].max == 0  && im.controls[im.controls.length-1].min == 0 ){
                    loopgif = false;
                    done = true;
               }
           }            
            if(manager != null && nextc == 0 && name.equals("x_aaa"))
              manager.copyall = true;
         }
      }
      imageControl getnext() {
         short i;
         if(im.controls[cno].nextcontrol != null) {
            for(i=0; i<c.length; ++i) {
               if(im.controls[i].name.equalsIgnoreCase(im.controls[cno].nextcontrol))
                 return c[i];
            }
         }
         else {
            i = (short)((cno+1)%c.length);
            while(!samecgroup(i)) {
               i = (short)((i+1)%c.length);
            }
            return c[i];
         }
         return this;
      }
      boolean samecgroup(short cno1) {
         String name1 = im.controls[cno].name;
         String name2 = im.controls[cno1].name;
         int p1=name1.indexOf('.');
         int p2=name2.indexOf('.');
         return     p1<0 && p2 < 0
                 || p1==p2
                     && name1.substring(0,p1).equals(name2.substring(0,p2));
      }
      void deactivate() {
         short j;
         for(j=0;j<p.length;++j) {
            if(p[j].frompart >= 0
                    && im.parts[p[j].frompart].controlno == cno) {
               if(building && p[j] == currPart) endPart();
               p[j].frompart = -1;
            }
         }
         active = false;
      }
      void deactivateOthers() {
         for(short i=0; i<c.length; ++i)   {
            if(i != cno && c[i].active && samecgroup(i)) {
               c[i].deactivate();
            }
         }
      }
      void activate() {
         short i,j,k;
         if (building) {
            deactivateOthers();
         }
         active = true;
         start=end=0;
         if(im.controls[cno].max != 0 && (!building || running)) {
            start = currtime;
            end = start + im.controls[cno].min*100
               + u.rand(Math.max(1,im.controls[cno].max*100 - im.controls[cno].min*100)) ;
            nextc = getnext().cno;
         }
         else if( cno == mousecontrol)
           nextc = getnext().cno;

         for(i=0;i<p.length;++i) {
             if((j=p[i].controlledby) >= 0 && samecgroup(j)) {
                j = findpart(i,cno);
                imagePart pp = p[i];
                pp.frompart = pp.topart = j;
                pp.start = start;
                pp.end = end;
                if(end != 0 || cno == mousecontrol) {
                  k = findpart(i,nextc);
                  if(im.parts[k].type ==  im.parts[j].type
                     && im.parts[k].node.length == im.parts[j].node.length) {
                      pp.topart = k;
                  }
                }
                if(pp.frompart == pp.topart){
                   pp.init();
                   if(im.parts[pp.frompart].type == FUNCTION
                      && pp.imfunc != null
                      && ((saveTextImage)im.parts[pp.frompart]).text != null)
                     pp.imfunc.setup(((saveTextImage)im.parts[pp.frompart]).text);
                }
             }
         }
      }
   }
   //-------------------------------------------------------------
   double midangle(double a1, double a2, int r1, int rtot) {
     double aa = a2-a1 +  Math.PI*4;
     while(aa>Math.PI*2) aa -= Math.PI*2;
     if(aa <= Math.PI)   return a1 + aa*r1/rtot;
     else  return a1 - (Math.PI*2 - aa)*r1/rtot;
   }
   //--------------------------------------------------------------
   short totframes(char pno) {
      short ptot = 0;
      for(short i=0;i<im.parts.length;++i) {
         if(im.parts[i].group == pno) ++ptot;
      }
      return ptot;
   }
   //--------------------------------------------------------------
   short totControls(short cno) {
     short ctot = 1,i;
     for(i=0;i<c.length;++i) {
        if(c[i].samecgroup(cno)) ++ctot;
     }
     return ctot;
   }
   //----------------------------------------------------------------
   static int[] getocos() {
     int ret[] = new int[65];
     for(short i=0; i<65; ++i) {
        ret[i] = (int)(FACTOR*Math.cos(Math.PI*i/32));
     }
     return ret;
   }
   static int[] getosin() {
     int ret[] = new int[65];
     for(short i=0; i<65; ++i) {
        ret[i] = (int)(FACTOR*Math.sin(Math.PI*i/32));
     }
     return ret;
   }
   //------------------------------------------------------------------
       // returns new setting for list ctl
   void newChoice(int curr1)  {
      curr = curr1;
      edittext = false;
      newchoice = true;
   }
   void donewchoice() {
      newchoice = false;
      short i,j;
      boolean gotit;
      if(!running && activecontrol >= 0 && (curr <= ENDTYPES || curr == COPY || curr == ATTACH)) {
           cancel();
           activecontrol = -1;
           activelist = null;
           setup();
      }
      if(running) {
         if(curr != RUN)  {  noise.beep(); }
         else {
           choicenarr[RUN] = "RUN & SIZE";
//           lchoice.setListData(choicenarr);
//           lchoice.setFixedCellHeight(-1);
           running = false;
           resizing = mustresize = false;
           if(activecontrol<0)  activelist = null;
           else activelist = new short[] {activecontrol};
           lcon.multiselect(false);
           setup();
//           for(i=0;i<p.length;++i) { p[i].end = 0; p[i].topart = p[i].frompart; p[i].init(); }
         }
         currChoice = (short)-1;
      }
      else if(curr == COLOR) {
        colorchooser.setColor(currColor);
         colordialog.setVisible(true);
         currChoice = (short)-1;
      }
      else if(curr == GETOLDCOLOR) {
          if(currPart != null && currPart.frompart>=0) {
             currColor = im.parts[currPart.frompart].color;
          }
          currChoice = (short)-1;
      }
      else if(curr == RECOLOR) {
          if(currPart != null && currPart.frompart>=0) {
             startchange();
             if(!addnewforcontrol()) return;
             im.parts[currPart.frompart].color = currColor;
             if(activecontrol < 0) {
               for (i = 0; i < im.parts.length; ++i) {
                 if (im.parts[i].group == currPart.group) {
                    im.parts[i].color = currColor;
                 }
               }
             }
          }
         currChoice = (short)-1;
      }
      else if(curr == THICKEN) {
          if(currPart != null && currPart.frompart>=0
              && im.parts[currPart.frompart].attachno < 0
              && (im.parts[currPart.frompart].type == OVAL
               || im.parts[currPart.frompart].type == LINE
               || im.parts[currPart.frompart].type == POLYLINE
               || im.parts[currPart.frompart].type == POLYGON
               || im.parts[currPart.frompart].type == RECTANGLE)) {
             startchange();
             if(!addnewforcontrol()) return;
             im.parts[currPart.frompart].attachno -= BASEU/manager.screenmax;
          }
         currChoice = (short)-1;
      }
      else if(curr == UNTHICKEN) {
          if(currPart != null && currPart.frompart>=0
              && im.parts[currPart.frompart].attachno < -1
              && (im.parts[currPart.frompart].type == OVAL
               || im.parts[currPart.frompart].type == LINE
               || im.parts[currPart.frompart].type == POLYLINE
               || im.parts[currPart.frompart].type == POLYGON
               || im.parts[currPart.frompart].type == RECTANGLE)) {
             startchange();
             if(!addnewforcontrol()) return;
             im.parts[currPart.frompart].attachno +=  BASEU/manager.screenmax;
          }
         currChoice = (short)-1;
      }
      else if(curr == UNDO) {
         if(currPart != null && mouseNode >= 0)
            cancel();
         else {undo();}
         currChoice = (short)-1;
      }
      else if(curr == REDO) {
          if(currPart != null && (rotating  || mouseNode >= 0 || moving ))
                 noise.beep();
          else {redo(); currChoice = (short)-1; }
      }
      else if(curr == RUN) {
         if(currPart != null && (mouseNode >= 0 || moving ||rotating)) noise.beep();
         else {
            startrunning();
         }
         currChoice = (short)-1;
      }
      else if(curr == NOACTIVECON) {
         cancel();
         activecontrol = -1;
         lcon.clearSelect();
         currChoice = (short)-1;
      }
      else if(curr == TOFRONT) {
        if(currPart == null || mouseNode >= 0 || moving ||rotating
                  || currPart.frompart < 0
                  || currPart.group == p.length-1
                 )noise.beep();
        else {
           startchange();
           if(!addnewforcontrol()) return;
           j = (short)currPart.group;
           for(i=0;i<im.parts.length;++i) {
              if(im.parts[i].group == j) im.parts[i].group = (char)(j+1);
              else if(im.parts[i].group == j+1) im.parts[i].group = (char)j;
              if(im.parts[i].attachno == j) im.parts[i].attachno = (short)(j+1);
              else if(im.parts[i].attachno == j+1) im.parts[i].attachno = (short)j;
           }
           buildactivelist();
           setup();
           setcurrpart((char)(j+1));
        }
        currChoice = (short)-1;
      }
      else if(curr == TOBACK) {
        if(currPart == null || mouseNode >= 0 || moving ||rotating
                  || currPart.frompart < 0
                  || currPart.group == 0
                 )noise.beep();
        else {
           startchange();
           if(!addnewforcontrol()) return;
           j = (short)currPart.group;
           for(i=0;i<im.parts.length;++i) {
              if(im.parts[i].group == j) im.parts[i].group = (char)(j-1);
              else if(im.parts[i].group == j-1) im.parts[i].group = (char)j;
              if(im.parts[i].attachno == j) im.parts[i].attachno = (short)(j-1);
              else if(im.parts[i].attachno == j-1) im.parts[i].attachno = (short)j;
           }
           buildactivelist();
           setup();
           setcurrpart((char)(j-1));
        }
        currChoice = (short)-1;
      }
      else if(curr == LEFTTORIGHT) {
        startchange();
        for(i=0;i<im.parts.length;++i) {
            im.parts[i].x =  - im.parts[i].x;
            im.parts[i].a = -im.parts[i].a;
            for(j=0;j<im.parts[i].node.length;++j) {
             if(im.parts[i].node[j].x > BASEU*4)
                   im.parts[i].node[j].x = - (im.parts[i].node[j].x - BASEU*8) +  BASEU*8;
              else im.parts[i].node[j].x =   - im.parts[i].node[j].x;
            }
        }
        setup();
        currChoice = (short)-1;
      }
      else if(curr == SPLITINTOPOLY) {
        if(currPart == null || mouseNode >= 0 || moving || rotating || activecontrol >= 0
                  || currPart.frompart < 0 ) noise.beep();
        else if(im.parts[currPart.frompart].type == OVAL
             || im.parts[currPart.frompart].type == OVALFILL
             || im.parts[currPart.frompart].type == RECTANGLE
             || im.parts[currPart.frompart].type == RECTFILL) {
           startchange();
           switch(im.parts[currPart.frompart].type) {
             case OVAL:
               im.parts[currPart.frompart].type = POLYGON;
               im.parts[currPart.frompart].node = poval2(im.parts[currPart.frompart].node);
               break;
             case OVALFILL:
               im.parts[currPart.frompart].type = POLYFILL;
               im.parts[currPart.frompart].node = poval2(im.parts[currPart.frompart].node);
               break;
             case RECTANGLE:
               im.parts[currPart.frompart].type = POLYGON;
               im.parts[currPart.frompart].node = new Point[]{im.parts[currPart.frompart].node[0],
                   new Point(im.parts[currPart.frompart].node[1].x, + im.parts[currPart.frompart].node[0].y),
                   im.parts[currPart.frompart].node[1],
                   new Point(im.parts[currPart.frompart].node[0].x, + im.parts[currPart.frompart].node[1].y)};
               break;
             case RECTFILL:
               im.parts[currPart.frompart].type = POLYFILL;
               im.parts[currPart.frompart].node = new Point[]{im.parts[currPart.frompart].node[0],
                   new Point(im.parts[currPart.frompart].node[1].x, + im.parts[currPart.frompart].node[0].y),
                   im.parts[currPart.frompart].node[1],
                   new Point(im.parts[currPart.frompart].node[0].x, + im.parts[currPart.frompart].node[1].y)};
               break;
           }
           char oldgroup = im.parts[currPart.frompart].group;
           short oldcontrol = im.parts[currPart.frompart].controlno;
           if(oldcontrol < 0)
             for(i=0; i<im.parts.length; ++i) {   // empty group - clear attached parts
               if(i != currPart.frompart && im.parts[i].group == oldgroup || im.parts[i].attachno == oldgroup) {
                 im.parts[i].type = im.parts[currPart.frompart].type;
                 im.parts[i].node = new Point[im.parts[currPart.frompart].node.length];
                 for(j=0;j<im.parts[i].node.length;++j)
                   im.parts[i].node[j] = new Point(im.parts[currPart.frompart].node[j].x,
                                                           im.parts[currPart.frompart].node[j].y);
               }
           }
        }
        else noise.beep();
       currChoice = (short)-1;
      }
      else if(curr == NEXT) {
         if(attaching) do {
             attachPart = next(attachPart);
         }while(attachPart == currPart || attachPart.isattached(currPart.group));
         else if(currPart != null) currPart = next(currPart);
         currChoice = (short)-1;
      }
      else if(curr == PREV) {
         if(attaching)  do {
            attachPart = prev(attachPart);
         }while(attachPart == currPart || attachPart.isattached(currPart.group));
         else if(currPart != null) currPart = prev(currPart);
         currChoice = (short)-1;
      }
      else if(curr == RESIZE || curr == RESIZE2) {
        if(ismark && !running && im.radius != 0) {
          startchange();
          doresize(im, curr != RESIZE);
          ismark=false;
          if(activecontrol<0)  activelist = null;
          else activelist = new short[] {activecontrol};
          lcon.multiselect(true);
          setup();
          im.x1 = im.y1 = BASEU;
          im.x2 = im.y2 = -BASEU;
          im.radius = 0;
        }
        else u.okmess("Move and resize","To use this feature, do 'run & size', 'stop running', then mark new area using right-click");
        currChoice = (short)-1;
      }
      else if(curr == CLIP) {
        if(!ismark && !running && im.clip != null) {
          startchange();
          im.clip = null;
          ismark = false;
         }
        else if (ismark && !running) {
          startchange();
          int xx1 = Math.min(markx1,markx2);
          int xx2 = Math.max(markx1,markx2);
          int yy1 = Math.min(marky1,marky2);
          int yy2 = Math.max(marky1,marky2);
          im.clip = new Rectangle((xx1-manager.screenwidth/2)* BASEU / manager.screenmax,
                                  (yy1-manager.screenheight/2)* BASEU / manager.screenmax,

(xx2-xx1)* BASEU / manager.screenmax,
                                  (yy2-yy1)* BASEU / manager.screenmax);
          ismark=false;
        }
        else u.okmess("CLIP","To use this feature, first mark area using right-click");
        currChoice = (short)-1;
      }
      else if(curr == MINSIZE) {
        if(!ismark && !running && im .minsize != null) {
          startchange();
          im.minsize = null;
          ismark = false;
         }
        else if (ismark && !running) {
          startchange();
          int xx1 = Math.min(markx1,markx2);
          int xx2 = Math.max(markx1,markx2);
          int yy1 = Math.min(marky1,marky2);
          int yy2 = Math.max(marky1,marky2);
          im.minsize = new Rectangle((xx1-manager.screenwidth/2)* BASEU / manager.screenmax,
                                  (yy1-manager.screenheight/2)* BASEU / manager.screenmax,
                                  (xx2-xx1)* BASEU / manager.screenmax,
                                  (yy2-yy1)* BASEU / manager.screenmax);
          ismark=false;
        }
        else u.okmess("MINSIZE","To use this feature, first mark area using right-click");
        currChoice = (short)-1;
      }
      else{
         lastchoice = currChoice = (short)curr;
      }
      if(currChoice < 0) clearchoice();
   }
   void clearchoice() {
      lchoice.clearSelection();
      currChoice = (short)-1;
   }
   //-------------------------------------------------------------------
   void doresize(saveSharkImage im, boolean stretch) {    // also used to resize before paste into picture
     int i,j;
     int xx1 = Math.min(markx1,markx2);
     int xx2 = Math.max(markx1,markx2);
     int yy1 = Math.min(marky1,marky2);
     int yy2 = Math.max(marky1,marky2);
     int newmidx = ((xx1+xx2)/2-manager.screenwidth/2) * BASEU / manager.screenmax;
     int newmidy = ((yy1+yy2)/2-manager.screenheight/2) * BASEU/ manager.screenmax;
     int oldmidx = (im.x1+im.x2)/2;
     int oldmidy = (im.y1+im.y2)/2;
     int dx = (newmidx - oldmidx);
     int dy = (newmidy - oldmidy);
     int neww = (xx2-xx1) * BASEU / manager.screenmax;
     int oldw =  im.x2 - im.x1;
     int newh = (yy2-yy1) * BASEU / manager.screenmax;
     int oldh =  im.y2 - im.y1;
     if(!stretch) {
       if(neww*oldh > newh/oldw) neww = oldw * newh / oldh;
         else newh = oldh *neww / oldw;
     }
     for(i=0;i<im.parts.length;++i) {
        if(im.parts[i].attachno < 0) {
           im.parts[i].x = (im.parts[i].x-oldmidx)*neww/oldw + newmidx;
           im.parts[i].y = (im.parts[i].y-oldmidy)*newh/oldh + newmidy;
        }
        else {
           im.parts[i].x = im.parts[i].x*neww/oldw;
           im.parts[i].y = im.parts[i].y*newh/oldh;
        }
        for(j=0;j<im.parts[i].node.length;++j) {
           if(im.parts[i].node[j].x > BASEU*4)
                im.parts[i].node[j].x = (im.parts[i].node[j].x - BASEU*8)*neww/oldw +  BASEU*8;
           else im.parts[i].node[j].x = im.parts[i].node[j].x*neww/oldw;
           im.parts[i].node[j].y = im.parts[i].node[j].y*newh/oldh;
        }
     }
   }
   //-----------------------------------------------------------------
   saveImagePart getpart(char pno,short cno) {
      for(short i = 0; i<im.parts.length; ++i) {
          if(im.parts[i].group == pno &&im.parts[i].controlno == cno) return im.parts[i];
      }
      return null;
   }
   //-----------------------------------------------------------------
   void startrunning() {
      int i;
      changed = true;
      hadrun = true;
      endPart();
      im.x1 = im.y1 = BASEU;
      im.x2 = im.y2 = -BASEU;
      im.radius = 0;
      running = true;
      for(i=0;i<c.length;++i) c[i].active = false;
      for(i=0;i<c.length;++i) {
         c[i].qactivate();
      }
      choicenarr[RUN] = "STOP RUNNING";
//      lchoice.setListData(choicenarr);
//      lchoice.setFixedCellHeight(-1);
      lcon.multiselect(true);
   }
   //------------------------------------------------------------------
   void newControl() {
        edittext = false;
        newselection = true;
   }
   //------------------------------------------------------------------
   void changeControl(sharklist lc)  {
      edittext = false;
      if(!insetup) changecontrol = true;
   }
   void dochangecontrol() {
      changecontrol = false;
      String s,slist[] = lcon.getdata();
      saveImageControl newc[] = new saveImageControl[slist.length],co;
      short ctot = 0;
      int startmin=0,startmax=0,startnext=0,openb,closeb,i,j,k,limit;
      int error=-1;
      char pos = (currPart != null)?currPart.group:0;

      if(docopycontrol()) {
         setup();               // refresh it
         setcurrpart(pos);
         return;
      }
      startchange();
      buildactivelist();           // just in case needed
      outerloop:for(j=0;j<slist.length;++j) {
         s = slist[j];
         startmin=startmax=startnext=0;
         co = new saveImageControl();
         co.name = s;
         if(s.length() >  0) {
            if ((openb = s.indexOf('(')) == 0)  {
               error = 0;  break outerloop;
            }
            else if(openb > 0) {
               co.name = s.substring(0,openb);
               if((closeb = s.indexOf(')')) > openb) {
                  startmin = openb+1;
                  for(i=startmin; i<s.length(); ++i) {
                     if((startmin>0 || startmax>0) && s.charAt(i) >= '0' && s.charAt(i) <= '9') continue;
                     if(startmin > 0) {
                        if(i>startmin) {
                           co.max = co.min
                              = (new Integer(s.substring(startmin,i))).intValue();
                           startmin = 0;
                           if(s.charAt(i) == '<')    startmax = i + 1;
                           else if(s.charAt(i) == u.commac)    startnext = i + 1;
                           else if(s.charAt(i) == ')')    break;
                           else {error = j;  break; }
                        }
                        else {error = j;  break outerloop; }
                     }
                     else if(startmax>0) {
                        if( i>startmax) {
                           co.max = (new Integer(s.substring(startmax,i))).intValue();
                           startmax = 0;
                           if(s.charAt(i) == u.commac)    startnext = i + 1;
                           else if(s.charAt(i) == ')')    break;
                          else {error = j;  break outerloop; }
                        }
                        else {error = j;  break outerloop; }
                     }
                     else if(startnext>0) {
                        if(s.charAt(i) == ')') {
                           if( i>startnext) {
                              co.nextcontrol = s.substring(startnext,i);
                              startnext = 0;
                              break;
                           }
                           else {error = j;  break outerloop; }
                        }
                     }
                  }
               }
            }
            newc[ctot++] = co;
         }
      }
      if(error >= 0) {
         noise.beep();
         if(error<lcon.count()) lcon.startEdit(error);
      }
      else {
         if(ctot < newc.length) {
             saveImageControl newcx[] = new saveImageControl[ctot];
             System.arraycopy(newc,0,newcx,0,ctot);
             newc = newcx;
         }
         if(ctot == im.controls.length) {}     // no action if same len
         else if(ctot < im.controls.length) {   // one deleted
            activelist=null;
            for(i=0;i<ctot;++i) {
               if(!newc[i].name.equals(im.controls[i].name)) {
                  break;
               }
            }
            for(j=0;j<im.parts.length;++j) {  // remove parts controlled by it
               if(im.parts[j].controlno == i) {
                  removePart(j);
                  --j;
               }
            }
            for(j=0;j<im.parts.length;++j) {  // adjust part control nos
               if(im.parts[j].controlno > i) --im.parts[j].controlno;
            }
         }
         else {             // extra added
            activelist=null;
            for(i=0;i<c.length;++i) {
               if(!newc[i].name.equals(im.controls[i].name)) {
                  break;
               }
            }
            if(i<c.length) {
               for(j=0;j<im.parts.length;++j) {  // adjust part control nos
                  if(im.parts[j].controlno >= i) ++im.parts[j].controlno;
               }
            }
         }
      }
      if(error<0 && im.controls != newc) {
         im.controls = newc;
      }
      setup();               // refresh it
      setcurrpart(pos);
   }
   //------------------------------------------------------------------------
      // if '=' control, copy one over it into it
   boolean docopycontrol() {
      int i, j, k, num1=-1, num2 = -1;
      String sstart=null,ssend=null;
      boolean got=false;
      String s,slist[] = lcon.getdata();
      if(slist.length == 0) return false;
      for(i=1;i<slist.length;++i) {
         if(slist[i].equals("=")) {got=true; break;}
      }
      if(!got) return false;
      slist[i] = new String(slist[i-1]);
      if((j=slist[i].indexOf('.')) >= 0) {
         sstart = slist[i].substring(0, ++j);
         String ss = slist[i].substring(j);
         k = ss.indexOf('(');
         if(k>0) {
            ssend = ss.substring(k);
            ss = ss.substring(0,k);
         }
         else ssend = "";
         try {
           num1 = Integer.parseInt(ss);
         }
         catch (NumberFormatException n) {
             num1 = -1;
         }
         if(i<slist.length-1
              && slist[i+1].length() >= j
              && slist[i].substring(0,j).equals(slist[i+1].substring(0,j))) {
            ss = slist[i+1].substring(j);
            k = ss.indexOf('(');
            if(k>0) ss = ss.substring(0,k);
            try {
              num2 = Integer.parseInt(ss);
            }
            catch (NumberFormatException n) {
                num2 = -1;
            }
         }
      }
      if(num1>=0 && num2 != num1+1) {
         slist[i] = sstart+String.valueOf(num1+1) + ssend;
      }
      else if(num1>=0) {
         slist[i] = sstart+String.valueOf(num1*10+1) + ssend;
      }
      insetup = true;
      lcon.putdata(slist);
      insetup = false;
      dochangecontrol();
      for(j=0;j<im.parts.length;++j) {  // adjust part control nos
         if(im.parts[j].controlno == i-1) {
             saveImagePart pp = copySavePart(im.parts[j],im.parts[j].group);
             pp.controlno = (short)i;
         }
      }
      lcon.select(new int[] {activecontrol=(short)i});
      return true;
   }
   //------------------------------------------------------------------
   public void choice(JList lch) {
      lchoice =lch;
      choicenarr[RUN] = "RUN & SIZE";
      lchoice.setListData(choicenarr);
//      lchoice.setFixedCellHeight(-1);
      clearchoice();
   }
   //------------------------------------------------------------------
   public void listControls(sharklist lc) {
      short i,j;
      if(lc == null) return;
      insetup=true;
      lc.clear();
      for(i=0; i<c.length;++i) {
         if(im.controls[i].max > 0) {
            String s = im.controls[i].name + "(" + String.valueOf(im.controls[i].min);
            if(im.controls[i].max != im.controls[i].min)
                s = s + "<" + String.valueOf(im.controls[i].max);
            if(im.controls[i].nextcontrol != null)
                s = s + "," + im.controls[i].nextcontrol;
            s = s + ")";
            lc.addItem(s);
         }
         else lc.addItem(new String(im.controls[i].name));
      }
      lcon = lc;
      lcon.multiselect(false);
      lcon.clearSelect();
      insetup=false;
   }
   //---------------------------------------------------------------
   public void mouseClicked(int x1, int x2)  {
      edittext = false;
      if(running) {
         if(!resizing) {
           newrectx1 = manager.mousexs;
           newrecty1 = manager.mouseys;
           resizing = true;
         }
         else {
           newrectx2 = manager.mousexs;
           newrecty2 = manager.mouseys;
           mustresize = true;
           resizing = false;
         }
      }
      else if(manager.rightclick) {
         if(!marking)  {
            markx1 =  manager.mousexs;
            marky1 =  manager.mouseys;
            marking = true;
         }
         else  {
            markx2 =  manager.mousexs;
            marky2 =  manager.mouseys;
            ismark = true;
            marking = false;
         }
         return;
      }
      marking = false;
      if(!building) return;
      mouseclick = true;
   }
   void domouseclick() {
      mouseclick = false;
      if(editfunc != null) {editfunc.dispose(); editfunc = null;}
      if(running || !building) return;
      if(currChoice >= 0 && currChoice <= ENDTYPES
             && (currPart == null || mouseNode <0) && !moving && !rotating) {
             startPart();          // new part
             clearchoice();
      }
      else if(currPart != null && attaching) {
         if(attachPart == currPart) noise.beep();
         else {
            currPart.endmove();
            currPart.attach();
            endPart();    //save it
            moving = attaching = false;
            attachPart = null;
         }
      }
      else if((currPart != null || movelist != null) && moving) {
         if(movelist != null) {
            for(short i=0; i<movelist.length;++i) {
               if(movelist[i] != currPart.frompart) {
                  imagePart save = currPart;
                  currPart = p[movelist[i]];
                  if(addnewforcontrol()) currPart.endmove();
                  currPart = save;
               }
               else  p[movelist[i]].endmove();
            }
         }
         else currPart.endmove();
         endPart();    //save it
         moving = false;
         arrowpoint = null;
         movelist = null;
       }
      else if((currPart != null || rotlist != null) && rotating) {
         Point mousepos = mousePoint();
         if(rotlist != null) {
             double aaa = p[rotlist[0]].rotangle();
             for(short i=0; i<rotlist.length;++i) {
               if(rotlist[i] != currPart.frompart) {
                  imagePart save = currPart;
                  currPart = p[rotlist[i]];
                  if(addnewforcontrol()) {
                      currPart.a += aaa;
                      im.parts[currPart.frompart].a += aaa;
                      if(currPart != p[rotlist[0]]) {
                         int dx = currPart.ax -  p[rotlist[0]].ax;
                         int dy = currPart.ay -  p[rotlist[0]].ay;
                         double dd = Math.sqrt(dx*dx + dy*dy);
                         double aaa2 = Math.atan2(dy,dx) + aaa;
                         currPart.ax = im.parts[currPart.frompart].x = (int)((p[rotlist[0]].ax + dd * Math.cos(aaa2))) ;
                         currPart.ay = im.parts[currPart.frompart].y =  (int)((p[rotlist[0]].ay + dd * Math.sin(aaa2))) ;
                      }
                  }
                  currPart = save;
               }
               else  {
                   p[rotlist[i]].a += currPart.rotangle();
                   im.parts[p[rotlist[i]].frompart].a += currPart.rotangle();
               }
            }
         }
         else {
            currPart.a += currPart.rotangle();
            im.parts[currPart.frompart].a += currPart.rotangle();
         }
         endPart();    //save it
         rotating = false;
         rotlist = null;
       }
      else if(currPart != null && mouseNode >= 0) {
            boolean wasnew = (oldNode == null);
            endPart();
//           if(im.parts[currPart.frompart].attachno < 0)
//                 currPart.moveattach(currPart.avPoint());
            if(wasnew &&  currPart.ispoly()) {
                  saveImagePart pp = im.parts[currPart.frompart];
                  Point newp[] = new Point[pp.node.length+1];
                  System.arraycopy(pp.node,0,newp,0,pp.node.length);
                  pp.node = newp;
                  mouseNode = (short)(pp.node.length-1);
                  newp[mouseNode] = mousePoint();
                  arrowpoint = null;
                  oldNode = null;
            }
      }
      else if(currPart != null && currChoice == ATTACH ) {
         if(p.length < 2 || activecontrol >= 0) noise.beep();
         else if(totframes(currPart.group) > 1) {
           Timer etimer = new Timer(100,
           new ActionListener() {
             public void actionPerformed(ActionEvent e){
                 u.okmess("Attempt to attach",
//startPR2004-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//                    "You cannot attach a part that is already moving");
                    "You cannot attach a part that is already moving", sharkStartFrame.mainFrame);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
             }
           });
           etimer.setRepeats(false);
           etimer.start();
        }
         else if(!currPart.anyunattached()) {
           Timer etimer = new Timer(100,
           new ActionListener() {
             public void actionPerformed(ActionEvent e){
//startPR2004-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//                 u.okmess("Attempt to attach","There in no free part to attach this to");
                    u.okmess("Attempt to attach","There is no free part to attach this to", sharkStartFrame.mainFrame);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
             }
           });
           etimer.setRepeats(false);
           etimer.start();
         }
         else {
            startchange();
            if(!addnewforcontrol()) return;
            if(im.parts[currPart.frompart].attachno >= 0) {
               attachPart = p[im.parts[currPart.frompart].attachno];
               currPart.detach();
            }
            else attachPart =  (currPart);
            if(attachPart == null) {noise.beep(); return;}
            currPart.moveattach(mousePoint());
            moving = attaching = true;
            mouseNode = -1;
            arrowpoint = null;
            startMouse = mousePoint();
         }
         clearchoice();
      }
      else if(currPart != null && mouseNode < 0 && (currChoice < 0 || currChoice==SMOOTH)) {
         if((mouseNode = mouseOverNode(currPart)) >= 0) {
            startchange();
            if(!addnewforcontrol()) {mouseNode = -1; arrowpoint = null;return;}
            oldNode = new Point(im.parts[currPart.frompart].node[mouseNode]);
            if(currChoice == SMOOTH) {
               if(currPart.ispoly()) {
                  if(im.parts[currPart.frompart].node[mouseNode].x > BASEU*4)
                     im.parts[currPart.frompart].node[mouseNode].x -= BASEU*8;
                  else  im.parts[currPart.frompart].node[mouseNode].x += BASEU*8;
               }
               clearchoice();
            }
         }
      }
      else if(currPart != null && mouseNode < 0) {
         if(currChoice == MOVE) {
               startchange();
               if(!addnewforcontrol()) return;
               if( activecontrol < 0 && totframes(currPart.group)==1) currPart.detach();
               if(ismark) {
                  movelist = null;
                  char groupt =  (char)p.length;
                  for(short i=0;i<p.length;++i) {
                     if(im.parts[p[i].frompart].attachno < 0
                             && partisinside(i,markx1,marky1,markx2,marky2)) {
                        movelist = u.addint(movelist,i);
                     }
                  }
                  if(movelist == null) {
                     noise.beep();
                  }
               }
               if(movelist != null || !ismark) {
                  moving = true;
                  mouseNode = -1;
                  arrowpoint = null;
                  startMouse = mousePoint();
               }
         }
         else if(currChoice == COPY &&  activecontrol < 0) {
               startchange();
               char group =  (char)p.length;
               short newpart =  (short)im.parts.length;
               if(ismark) {
                  movelist = null;
                  char groupt =  (char)p.length;
                  for(short i=0;i<group;++i) {
                     if(im.parts[p[i].frompart].attachno < 0
                           && partisinside(i,markx1,marky1,markx2,marky2)) {
                        movelist = u.addint(movelist,groupt);
                        clonePart(p[i].frompart,groupt,(short)-1);
                        for(short j=0;j<im.parts.length;++j)
                           groupt = (char)Math.max(im.parts[j].group, groupt);
                        ++groupt;
                     }
                  }
                  if(movelist == null) {
                     noise.beep();
                  }
               }
               else {
                  clonePart(currPart.frompart,group,im.parts[currPart.frompart].attachno);
                  if(im.parts[currPart.frompart].attachno >= 0) {
                     im.parts[newpart].a = currPart.aa;
                     im.parts[newpart].x = currPart.actualx();
                     im.parts[newpart].y = currPart.actualy();
                     im.parts[newpart].attachno = -1;
                  }
               }
               if(movelist != null || !ismark) {
                  buildactivelist();
                  setup();
                  setcurrpart(group);
                  moving = true;
                  mouseNode = -1;
                  arrowpoint = null;
                  startMouse = mousePoint();
               }
         }
         else if(currChoice == ROTATE) {
               startchange();
               if(!addnewforcontrol()) return;
               if(ismark) {
                  rotlist = null;
                  char groupt =  (char)p.length;
                  for(short i=0;i<p.length;++i) {
                     if(im.parts[p[i].frompart].attachno < 0
                             && partisinside(i,markx1,marky1,markx2,marky2)) {
                        rotlist = u.addint(rotlist,i);
                     }
                  }
                  if(rotlist == null) {
                     noise.beep();
                  }
               }
               if(rotlist != null || !ismark) {
                  rotating = true;
                  startMouse = mousePoint();
                  mouseNode = -1;
                  arrowpoint = null;
               }
         }
         clearchoice();
      }
      ismark = false;
   }
   //-------------------------------------------------------------
        // mouse addr to logical image point
   Point mousePoint() {
       if(arrowpoint != null) {
          return new Point((arrowpoint.x-manager.screenwidth/2)*BASEU/manager.screenmax,
             (arrowpoint.y-manager.screenheight/2)*BASEU/manager.screenmax);
       }
       return new Point((manager.mousexs-manager.screenwidth/2)*BASEU/manager.screenmax,
       (manager.mouseys-manager.screenheight/2)*BASEU/manager.screenmax);
   }
   //-------------------------------------------------------------
        // mouse addr to logical image point
   short mouseOverNode(imagePart imp) {
      short i;
      int x,y;
      int mindist = nodedist*nodedist, dist, mindistnode = -1;
      int   xm =  manager.mousexs;
      int   ym =  manager.mouseys;
      Point pt;
      for(i=0;i<im.parts[imp.frompart].node.length;++i) {
         if(im.parts[imp.frompart].node[i].x > BASEU*4) {
            pt = new Point(im.parts[imp.frompart].node[i].x-BASEU*8,im.parts[imp.frompart].node[i].y);
            x = imp.getx(pt);
            y = imp.gety(pt);
         }
         else {
            x = imp.getx(im.parts[imp.frompart].node[i]);
            y = imp.gety(im.parts[imp.frompart].node[i]);
         }
         if((dist=(x-xm)*(x-xm) + (y-ym)*(y-ym)) <= mindist) {
            mindist = dist;
            mindistnode = i;
         }
      }
      if(mindistnode >= 0) return (short) mindistnode;
      else return -1;
   }
   //----------------------------------------------------------------
   short findpart(short groupno, short controlno) {
      for(short i = 0; i<im.parts.length;++i) {
         if(im.parts[i].group == groupno && im.parts[i].controlno==controlno)
            return i;
      }
      for(short i = 0; i<im.parts.length;++i) {
         if(im.parts[i].group == groupno && im.parts[i].controlno<0)
            return i;
      }
      return -1;
   }
   //---------------------------------------------------------------
   Point[] copynodes(Point node[]) {
      Point ret[] = new Point[node.length];
      for(short i=0; i<node.length; ++i) {
         ret[i] = new Point(node[i]);
      }
      return ret;
   }
    //-------------------------------------------------------------
   short addSavePart(imagePart imp) {
      short ct = (short)im.parts.length;
      saveImagePart sp[] = new  saveImagePart[ct+1];
      System.arraycopy(im.parts,0,sp,0,ct);
      im.parts = sp;
      im.parts[ct] = currChoice==TEXT ||currChoice==FUNCTION ? new saveTextImage()
                                           :new saveImagePart();
      im.parts[ct].x = imp.ax;
      im.parts[ct].y = imp.ay;
      im.parts[ct].group = imp.group;
      return ct;
   }
    //-------------------------------------------------------------
   saveImagePart copySavePart(saveImagePart imp,char newgroup) {
      short ct = (short)im.parts.length;
      saveImagePart sp[] = new  saveImagePart[ct+1];
      System.arraycopy(im.parts,0,sp,0,ct);
      im.parts = sp;
      if(imp.type == TEXT ||imp.type==FUNCTION ) {
           im.parts[ct] = new saveTextImage();
           String ss[] = new String[((saveTextImage)imp).text.length];
           System.arraycopy(((saveTextImage)imp).text, 0,ss,0,ss.length);
           ((saveTextImage)im.parts[ct]).text = ss;
      }
      else im.parts[ct] = new saveImagePart();
      im.parts[ct].type = imp.type;
      im.parts[ct].color = imp.color;
      im.parts[ct].node = copynodes(imp.node);
      im.parts[ct].controlno = imp.controlno;
      im.parts[ct].x = imp.x;
      im.parts[ct].y = imp.y;
      im.parts[ct].a = imp.a;
      im.parts[ct].group = newgroup;
      im.parts[ct].attachno = imp.attachno;
      return im.parts[ct];
   }
   //-------------------------------------------------------------
   void startPart() {
      startchange();
      char group = (char)p.length;
      imagePart pp[] =  new imagePart[group+1];
      System.arraycopy(p,0,pp,0,group);
      p = pp;
      currPart = p[group] = new imagePart(group);

      mouseNode = 1;
      arrowpoint = null;
      oldNode = null;
      currPart.frompart = currPart.topart = addSavePart(currPart);
      if(currChoice == ARCFILL)
         im.parts[currPart.frompart].node = new Point[] {mousePoint(),new Point(mousePoint().x+mover.WIDTH/6,mousePoint().y-mover.HEIGHT/6),
                       new Point(mousePoint().x+mover.WIDTH/6,mousePoint().y+mover.HEIGHT/6)};
      else im.parts[currPart.frompart].node = new Point[] {mousePoint(),mousePoint()};
      im.parts[currPart.frompart].color = currColor;
      im.parts[currPart.frompart].type = (char)currChoice;
   }
   //--------------------------------------------------------------
                   // clone part plus any parts attached to it
   void clonePart(short oldpartno, char newgroup, short newattach) {
      saveImagePart oldPart = im.parts[oldpartno];
      char oldgroup = oldPart.group;
      short i,j;
      saveImagePart newPart;
      short nextgroup = (short)(newgroup+1);
      short nextgroup1 = nextgroup;

      newPart = copySavePart(oldPart,newgroup);
      newPart.attachno = newattach;

      for(j=0;j<p.length;++j) {
         for(i=0;i<im.parts.length;++i) {
            if(im.parts[i].group == j && im.parts[i].attachno == oldgroup) {
               clonePart(i, (char)(nextgroup),(short)newgroup);
               nextgroup1 = (short)(nextgroup+1);
            }
         }
         nextgroup = nextgroup1;
      }
   }
  //----------------------------------------------------------
   void endPart() {
      if(!building || running) return;

      mouseNode = -1;
      arrowpoint = null;
      oldNode = null;
   }
   //------------------------------------------------------------------
   boolean addnewforcontrol() {
      short i;
      if(activecontrol >= 0 && im.parts[currPart.frompart].controlno != activecontrol) {
         if(im.parts[currPart.frompart].controlno < 0
              && ((i = currPart.controlledby) < 0 || c[i].samecgroup(activecontrol))) {
            saveImagePart newpart = copySavePart(im.parts[currPart.frompart],im.parts[currPart.frompart].group);
            newpart.controlno = activecontrol;
            currPart.topart = currPart.frompart = (short)(im.parts.length-1);
            if(currPart.controlledby < 0) currPart.controlledby = activecontrol;
         }
         else {
           noise.beep();
           Timer etimer = new Timer(100,
           new ActionListener() {
             public void actionPerformed(ActionEvent e){
//startPR2004-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//               u.okmess("Adjusting part while CONTROL active", "Current part is assigned to different control");
               u.okmess("Adjusting part while CONTROL active", "Current part is assigned to different control", sharkStartFrame.mainFrame);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
             }
           });
           etimer.setRepeats(false);
           etimer.start();
           return false;
        }
      }
      return true;
   }
   //-----------------------------------------------------------------
   void startchange() {
      if(undo == null) undo  =  new ByteArrayOutputStream[20];
      if(redo == null) redo  =  new ByteArrayOutputStream[5];
      currundo =  (short)((currundo+1)%undo.length);
      undo[currundo] =  new ByteArrayOutputStream();
      try {
         ObjectOutputStream sr = new ObjectOutputStream(undo[currundo]);
         sr.writeObject(im);
      }
      catch(java.io.IOException e) {
         return;
      }
      for(short i=0;i<redo.length;++i) redo[i] = null;
      currredo = -1;
      changed = true;
      hadrun = false;
   }
   //-----------------------------------------------------------------
   void saveredo() {
      currredo =  (short)((currredo+1)%redo.length);
      redo[currredo] =  new ByteArrayOutputStream();
      try {
         ObjectOutputStream sr = new ObjectOutputStream(redo[currredo]);
         sr.writeObject(im);
      }
      catch(java.io.IOException e) {};
   }
   //-----------------------------------------------------------------
   void undo() {
      if(currundo < 0 || undo[currundo] == null) {noise.beep();   return;}
      saveredo();
      try {
         ObjectInputStream sr = new ObjectInputStream(new ByteArrayInputStream(undo[currundo].toByteArray()));
         im = (saveSharkImage)sr.readObject();
      }
      catch(java.io.IOException e) {}
      catch(ClassNotFoundException e) {}
      undo[currundo] = null;
      if(currundo>0) --currundo;
      else currundo = (short)(undo.length-1);
      char pos = currPart != null ? currPart.group : 0;
      mouseNode = -1;
      rotating=attaching=moving=false;
      arrowpoint = null;
      movelist = null;
      rotlist = null;
      buildactivelist();
      setup();
      listControls(lcon);   // in case controls changed
      setcurrpart(pos);
   }
   //-----------------------------------------------------------------
   void redo() {
      if(currredo<0 ||redo[currredo] == null) {noise.beep();   return;}
      currundo =  (short)((currundo+1)%undo.length);
      undo[currundo] = redo[currredo];
      try {
         ObjectInputStream sr = new ObjectInputStream(new ByteArrayInputStream(redo[currredo].toByteArray()));
         im = (saveSharkImage)sr.readObject();
      }
      catch(java.io.IOException e) {}
      catch(ClassNotFoundException e) {}
      redo[currredo] = null;
      if(currredo>0) --currredo;
      else currredo = (short)(redo.length-1);
      char pos = currPart != null ? currPart.group : 0;
      mouseNode = -1;
      arrowpoint = null;
      buildactivelist();
      setup();
      listControls(lcon);   // in case controls changed
      setcurrpart(pos);
   }
   //-----------------------------------------------------------------
   short relnum(saveImagePart pp) {
      for(short i=0;i<im.parts.length;++i) if(pp==im.parts[i]) return(i);
      return(-1);
   }
   //----------------------------------------------------------------
   public boolean isActiveControl(String name) {
      for(short i=0;i<c.length;++i) {
         if(im.controls[i].name.equalsIgnoreCase(name)) {
             return c[i].active;
         }
      }
      return false;              // not found
   }
   //----------------------------------------------------------------
   public boolean midcontrol() {
      return c.length>0 && !c[0].active && !c[c.length-1].active;
   }
   //----------------------------------------------------------------
   public boolean setControl(String name) {
      for(short i=0;c!=null && i<c.length;++i) {
         if(im.controls[i].name.equalsIgnoreCase(name)) {
             if(!c[i].active) {
                c[i].deactivateOthers();
                currtime = sharkGame.gtime();
                c[i].activate();
             }
             return true;
         }
      }
      return false;              // not found
   }
   //------------------------------------------------------------------
   public boolean setControl(String name,int time) {
      short i,j,k;
      long timenow = sharkGame.gtime();
      saveImagePart newpart;

      for(i=0;i<c.length;++i) {
         if(im.controls[i].name.equalsIgnoreCase(name)) {
             if(!c[i].active) {
                for(j=0;j<c.length;++j) {
                   if(i!=j && c[j].active && c[j].samecgroup(i))  {
                      c[j].start = timenow;
                      c[j].end = timenow + time;
                      c[j].nextc = i;
                      for(k=0;k<p.length;++k) {
                         if(p[k].frompart>=0
                            && im.parts[p[k].frompart].controlno == j
                            && (newpart = getpart((char)k,i)) != null
                            && im.parts[p[k].frompart].type == newpart.type
                            && im.parts[p[k].frompart].node.length == newpart.node.length) {
                               p[k].topart = relnum(newpart);
                               p[k].start = timenow;
                               p[k].end = timenow+time;
                         }
                      }
                      return true;
                   }
                }
                c[i].activate();
             }
             return true;
         }
      }
      return false;              // not found
   }
   //-----------------------------------------------------------------
   //-----------------------------------------------------------------
   public static sharkImage[] findall(String search) {
      sharkImage im[];
      int i,j;
      int nums[] =  new int[sharkStartFrame.publicImageLib.length+1];
      String s[] = new String[0];
      if(sharkStartFrame.currStudent >= 0 && sharkStartFrame.studentList[sharkStartFrame.currStudent].hasimages) {
            s = db.list(sharkStartFrame.studentList[sharkStartFrame.currStudent].name, db.IMAGE,search);
            nums[0] =  s.length;
      }
      for(j=0;j<sharkStartFrame.publicImageLib.length;++j)  {
            s = u.addString(s,db.list(sharkStartFrame.publicImageLib[j], db.IMAGE,search));
            nums[1+j]=s.length;
      }
      im = new sharkImage[s.length];
      for(i=0;i<s.length;++i) {
         if(i<nums[0]) im[i] = new sharkImage(sharkStartFrame.studentList[sharkStartFrame.currStudent].name,s[i],false,false);
         else for(j=0;j<sharkStartFrame.publicImageLib.length;++j) {
             if(i<nums[j+1]) {
                im[i] = new sharkImage(sharkStartFrame.publicImageLib[j],s[i],false,false);
                break;
             }
         }
      }
      if(scannedlist.length > 0) return addscannedall(search,im);
      return im;
   }
   //-----------------------------------------------------------------
   public static String[] findlist2(String search) {
      int i,j;
      int nums[] =  new int[sharkStartFrame.publicImageLib.length+1];
      String s[] = new String[0];
      if(sharkStartFrame.currStudent >= 0 && sharkStartFrame.studentList[sharkStartFrame.currStudent].hasimages) {
            s = db.list(sharkStartFrame.studentList[sharkStartFrame.currStudent].name, db.IMAGE,search);
      }
      for(j=0;j<sharkStartFrame.publicImageLib.length;++j)  {
            s = u.addString(s,db.list(sharkStartFrame.publicImageLib[j], db.IMAGE,search));
      }
      return s;
   }
   //-----------------------------------------------------------------
   static cache findlist(String search) {
      int i,j;
      for(i=0;i<cc.length;++i) {
         if(cc[i].search == search) return cc[i];
      }
      cache cnew = new cache();
      cnew.search = search;
      cnew.name = new String[0];
      cnew.database = new String[0];
      i = 0;
      if(sharkStartFrame.currStudent >= 0   && sharkStartFrame.studentList[sharkStartFrame.currStudent].hasimages) {
          cnew.name = u.addString(cnew.name,db.list(sharkStartFrame.studentList[sharkStartFrame.currStudent].name, db.IMAGE,search));
          while(cnew.database.length<cnew.name.length)
              cnew.database=u.addString(cnew.database,sharkStartFrame.studentList[sharkStartFrame.currStudent].name);
      }
      for(j=0;j<sharkStartFrame.publicImageLib.length;++j)  {
          cnew.name = u.addString(cnew.name,db.list(sharkStartFrame.publicImageLib[j], db.IMAGE,search));
          while(cnew.database.length<cnew.name.length)
              cnew.database=u.addString(cnew.database,sharkStartFrame.publicImageLib[j]);
      }
      if(cc == null) cc = new cache[] {cnew};
      else {
         cache newcc[]  = new cache[cc.length+1];
         System.arraycopy(cc,0,newcc,0,cc.length);
         newcc[cc.length] = cnew;
         cc = newcc;
      }
      return cnew;
   }
   //-----------------------------------------------------------------
   public static sharkImage[] randomset(String[] search) {
      sharkImage im[] = new sharkImage[search.length];
      int i,j;
      cache list = findlist(search[0]);
      if(list.name.length == 0) return null;
      int num = u.rand(list.name.length);
      im[0] = new sharkImage(list.database[num],list.name[num],false,false);
      for(i=1;i<im.length;++i) {
         list = findlist(search[i]);
         j = Math.min(num,list.name.length-1);
         if(j<0) return null;
         im[i] = new sharkImage(list.database[j],list.name[j],false,false);
      }
      return im;
   }
    //-----------------------------------------------------------------
   public static sharkImage[] randomimages(String search,short count) {
      sharkImage im[] = new sharkImage[count];
      int i,j;
      cache list = findlist(search);
      if(list.name.length == 0) return null;
      short o[] = u.shuffle(u.select((short)list.name.length,count));
      for(i=j=0;i<count;++i,++j) {
         if(j>=o.length) {
           if(j%o.length == 0) o = u.shuffle(u.select((short)list.name.length,count));
           im[i] = new sharkImage(im[o[j%o.length]],false);
         }
         else im[i] = new sharkImage(list.database[o[j]],list.name[o[j]],false,false);
      }
      return im;
   }
   //-----------------------------------------------------------------
   public static sharkImage random(String search) {
      int i,j;
      cache list = findlist(search);
      if(list.name.length == 0) return null;
      int num = u.rand(list.name.length);
      return new sharkImage(list.database[num],list.name[num],false,false);
   }
   //-----------------------------------------------------------------
//startPR2008-06-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//   public static sharkImage find(String name) {

   public static boolean findQuery(String dbname,  String name, boolean justpics) {
     return find2Query(name, null, justpics, false, dbname, (sharkStartFrame.currPlayTopic!=null && sharkStartFrame.currPlayTopic.ownlist));
   }

 //  public static boolean findQuery(String dbname,  String name, boolean justpics, boolean ownwordlists) {
 //    return find2Query(name, null, justpics, false, dbname, ownwordlists);
 //  }

   public static sharkImage find(String dbname, String name, boolean justpics, boolean ownwordlists) {
     return find2(name, null, justpics, false, dbname, ownwordlists);
   }

   public static sharkImage find(String name) {
     return find2(name, null, false, false, null, (sharkStartFrame.currPlayTopic!=null && sharkStartFrame.currPlayTopic.ownlist));
   }
   
   public static sharkImage find(String name, int specificImLib) {
     return find2(name, null, false, false, null, (sharkStartFrame.currPlayTopic!=null && sharkStartFrame.currPlayTopic.ownlist), specificImLib);
   }   

   public static sharkImage find(String name, boolean justpics, boolean justuniversal, boolean ownwordlists) {
     return find2(name, null, justpics, false, null, ownwordlists);
   }   
   
   public static sharkImage find(String name, boolean justpics, boolean justuniversal) {
     return find2(name, null, justpics, justuniversal, null, (sharkStartFrame.currPlayTopic!=null && sharkStartFrame.currPlayTopic.ownlist));
   }

   public static sharkImage find(String name, student stu, boolean justpics) {
     return find2(name, stu, justpics, false, null, (sharkStartFrame.currPlayTopic!=null && sharkStartFrame.currPlayTopic.ownlist));
   }

    public static sharkImage find(String name, student stu, boolean justpics, boolean justuniversal) {
     return find2(name, stu, justpics, justuniversal, null, (sharkStartFrame.currPlayTopic!=null && sharkStartFrame.currPlayTopic.ownlist));
   }
    
   public static sharkImage find2(String name, student student, boolean justpics, boolean justuniversal, String dbname, boolean ownwordlists) {
           return find2(name, student, justpics, justuniversal, dbname, ownwordlists, -1);
   }

   public static sharkImage find2(String name, student student, boolean justpics, boolean justuniversal, String dbname, boolean ownwordlists, int specificImLib) {
  //    if(sharkStartFrame.currPlayTopic!=null && sharkStartFrame.currPlayTopic.ownlist)ownwordlists = true;
       if(name==null)return null;
      if(u.ignore.length>0){
        for(int i = 0; i < u.ignore.length; i++){
          if(name.startsWith(u.ignore[i])){
            name = name.substring(u.ignore[i].length());
            break;
          }
        }
      }
      sharkImage im;
      if (student == null && sharkStartFrame.currStudent < 0) {
          if(specificImLib<0){
            for(short j=0;j<sharkStartFrame.publicImageLib.length;++j) {
               if(!(im =  new sharkImage(sharkStartFrame.publicImageLib[j], name, false,false)).newf)
                 return im;
            }
          }
          else{
            if(!(im =  new sharkImage(sharkStartFrame.publicImageLib[specificImLib], name, false,false)).newf)
              return im;
          }
         return null;
      }
      if (student == null) 
          student = sharkStartFrame.studentList[sharkStartFrame.currStudent];
      String prefs[];
      // otherwise games might not work if user didn't want pictures
      if(student.picprefs!=null && name.indexOf('_')<0 && !justpics){
          prefs = student.picprefs;
          
          // as sign pictures have come out - remove this pref from stu
          if(prefs!=null && prefs.length>1)
              prefs[1]="-1";

      }
      else prefs = picturepreferences.defaultprefs;
      boolean allowimports = true;
      saveTreeWordList ownWordListSaveTree = getSaveTreeWordList(allowimports, ownwordlists, student);
      for(int i = 0; i < picturepreferences.defaultprefs.length; i++){
          sharkImage sifind;
          // get imported image from user's resources file if there
          if(ownWordListSaveTree !=null && allowOwnWords(student)){
             sifind = getSaveTreeWordListImage(name, ownWordListSaveTree);
             if(sifind!=null)return sifind;
          }          
          for(int j = 0; ownwordlists && name.indexOf('@')<0 && name.indexOf('^')<0 && j < u.picSearchAdds.length; j++){
              sifind = find3(name+u.picSearchAdds[j], student, u.findString(prefs, String.valueOf(i)), justpics, justuniversal, dbname, ownWordListSaveTree, allowimports, specificImLib);
              if(sifind!=null)return sifind;
          }
          sifind = find3(name, student, u.findString(prefs, String.valueOf(i)), justpics, justuniversal, dbname, ownWordListSaveTree, allowimports, specificImLib);
          if(sifind!=null)return sifind;
      }
      return null;
   }


   public static String[] findAll(String name, student student, boolean justpics, boolean justuniversal, String dbname, boolean ownwordlists) {
      if(name==null)return null;
      if(u.ignore.length>0){
        for(int i = 0; i < u.ignore.length; i++){
          if(name.startsWith(u.ignore[i])){
            name = name.substring(u.ignore[i].length());
            break;
          }
        }
      }
      sharkImage im;
      if (student == null && sharkStartFrame.currStudent < 0) {
         for(short j=0;j<sharkStartFrame.publicImageLib.length;++j) {
            if(!(im =  new sharkImage(sharkStartFrame.publicImageLib[j], name, false,false)).newf)
              return null;
         }
         return null;
      }
      if (student == null) 
          student = sharkStartFrame.studentList[sharkStartFrame.currStudent];
      String prefs[];
      // otherwise games might not work if user didn't want pictures
      if(student.picprefs!=null && name.indexOf('_')<0 && !justpics){
          prefs = student.picprefs;
          
          // as sign pictures have come out - remove this pref from stu
          if(prefs!=null && prefs.length>1)
              prefs[1]="-1";

      }
      else prefs = picturepreferences.defaultprefs;
      boolean allowimports = false;
      String siarr[] = new String[]{};
      for(int i = 0; i < picturepreferences.defaultprefs.length; i++){
          sharkImage sifind;
          for(int j = 0; ownwordlists && name.indexOf('@')<0 && name.indexOf('^')<0 && j < u.picSearchAdds.length; j++){
              sifind = find3(name+u.picSearchAdds[j], student, u.findString(prefs, String.valueOf(i)), justpics, justuniversal, dbname, null, allowimports, -1);
              if(sifind!=null)siarr = u.addString(siarr, name+u.picSearchAdds[j]);
          }
          sifind = find3(name, student, u.findString(prefs, String.valueOf(i)), justpics, justuniversal, dbname, null, allowimports, -1);
          if(sifind!=null)siarr = u.addString(siarr, name);
          if(siarr.length>0)return siarr;        
      }
      return null;       
   }  
   
   
   
   public static boolean find2Query(String name, student student, boolean justpics, boolean justuniversal, String dbname, boolean ownwordlists) {
//       if(sharkStartFrame.currPlayTopic!=null && sharkStartFrame.currPlayTopic.ownlist)ownwordlists = true;
       if(name==null)return false;
      if(u.ignore.length>0){
        for(int i = 0; i < u.ignore.length; i++){
          if(name.startsWith(u.ignore[i])){
            name = name.substring(u.ignore[i].length());
            break;
          }
        }
      }
      sharkImage im;
      if (student == null && sharkStartFrame.currStudent < 0) {
         for(short j=0;j<sharkStartFrame.publicImageLib.length;++j) {
            if(!(im =  new sharkImage(sharkStartFrame.publicImageLib[j], name, false,false)).newf)
              return true;
         }
         return false;
      }
      if (student == null)
          student = sharkStartFrame.studentList[sharkStartFrame.currStudent];
      String prefs[];
      if(student.picprefs!=null && name.indexOf('_')<0 && !justpics)
          prefs = student.picprefs;
      else prefs = picturepreferences.defaultprefs;
      for(int i = 0; i < picturepreferences.defaultprefs.length; i++){
         boolean found = findQuery(name, student, u.findString(prefs, String.valueOf(i)), justpics, justuniversal, dbname, ownwordlists);
          for(int j = 0; ownwordlists && !found && name.indexOf('@')<0 && name.indexOf('^')<0 && j < u.picSearchAdds.length; j++){
              found = findQuery(name+u.picSearchAdds[j], student, u.findString(prefs, String.valueOf(i)), justpics, justuniversal, dbname, ownwordlists);
              if(found)return true;
          }
         if(found)return true;
      }
      return false;
   }
 
   
public static sharkImage find3(String name, student stu, int type, boolean justpics, boolean justuniversal, String dbname, saveTreeWordList ownWordListSaveTree, boolean allowimports, int specificImLib) {
    if(type<0)return null;
    if(name==null)return null;
    sharkImage im;
    if(type==sharkImage.TYPE_PICTURE){
          if (stu.hasimages
              && ! (im = new sharkImage(stu.name, name, false, false)).newf)
              return im;
          byte buf[];
                  
          
          if (sharkStartFrame.currStudent >= 0) {

            if (dbname!=null){
               if((buf = (byte[]) db.find(dbname, name, db.PICTUREPLIST)) != null){
                 sharkImage si = new sharkImage(sharkStartFrame.t.createImage(buf), name);
                 si.isimport = true;
                 return si;
               }
               else return null;
            }

            boolean allowicons = (sharkStartFrame.allowStuImportPics_Icon || stu.administrator);
            if (name.startsWith(PickPicture.ownpic)){
               if(allowicons && (buf = (byte[]) db.find(stu.name, PickPicture.ownpicstuset, db.PICTURE)) != null){
                 sharkImage si = new sharkImage(sharkStartFrame.t.createImage(buf), name);
                 si.isimport = true;
                 return si;
               }
               if((buf = (byte[]) db.find(stu.name, PickPicture.ownpic, db.PICTURE)) != null){
                 sharkImage si = new sharkImage(sharkStartFrame.t.createImage(buf), name);
                 si.isimport = true;
                 return si;
               }
            }

            // if is user word list
            if(ownWordListSaveTree !=null){                
                if(ownWordListSaveTree.preferredPic == null) ownWordListSaveTree.preferredPic = new String[ownWordListSaveTree.names.length];
                for(int i = 1; ownWordListSaveTree.preferredPic!=null && i< ownWordListSaveTree.names.length && i< ownWordListSaveTree.preferredPic.length; i++){
                    for(int j = 0; j < u.picSearchAdds.length; j++){
                        if(name.endsWith(u.picSearchAdds[j]) &&  name.substring(0, name.length()-u.picSearchAdds[j].length()).equals(ownWordListSaveTree.names[i])){
                            if(ownWordListSaveTree.preferredPic[i] != null){
                                name = ownWordListSaveTree.preferredPic[i];
                            }
                        }                                
                    }
                }
            }

            String nam = name;
            int k;
            if((k=name.indexOf("@@"))>=0){
                nam = name.substring(0, k);
            }
//            if (!justuniversal && allow  && (buf = (byte[]) db.find(sharkStartFrame.resourcesPlus+ stu.name + sharkStartFrame.resourcesFileSuffix, nam, db.PICTURE)) != null) {
//              sharkImage si = new sharkImage(sharkStartFrame.t.createImage(buf), nam);
//              si.isimport = true;
//              return si;
//            }
            if (allowimports &&  (buf = (byte[]) db.find(PickPicture.universalImageFile, nam, db.PICTURE)) != null) {
              sharkImage si = new sharkImage(sharkStartFrame.t.createImage(buf), nam);
              si.univseralImport =true;
              si.isimport = true;
              return si;
            }
          }
          if(allowimports && scannedlist.length>0 ) {
            Image tifim;
            if ( (tifim = findscanned(name)) != null) {
              return im = new sharkImage(tifim, name);
            }
          }
           if ( name.endsWith("@@w") &&   (buf = (byte[]) db.find(sharkStartFrame.publicWidgitLib, name.substring(0, name.indexOf("@@w")), db.PICTURE)) != null) {
              MediaTracker tracker = new MediaTracker(sharkStartFrame.mainFrame);
              Image image1 = sharkStartFrame.t.createImage(buf);
              tracker.addImage(image1,1);
              try {tracker.waitForAll();}
              catch (InterruptedException ie){}

              sharkImage si = new sharkImage(sharkStartFrame.t.createImage(buf), name);
              return si;
            }          
           
           if(specificImLib < 0){
            for (short j = 0; j < sharkStartFrame.publicImageLib.length; ++j) {
              if (! (im = new sharkImage(sharkStartFrame.publicImageLib[j], name, false, false)).
                  newf){
                  if(!shark.production)MYSQLUpload.currImageDb = sharkStartFrame.publicImageLib[j];
                return im;
              }
            }
           }
           else{
               if (! (im = new sharkImage(sharkStartFrame.publicImageLib[specificImLib], name, false, false)).
                  newf){
                   if(!shark.production)MYSQLUpload.currImageDb = sharkStartFrame.publicImageLib[specificImLib];
                return im;
               }
           }
            if(name.endsWith("~")) {
              String s = u.gettext("phonicshomos",name.substring(0,name.length()-1));
              if(s != null) {
                  if(specificImLib < 0){
                    for (short j = 0; j < sharkStartFrame.publicImageLib.length; ++j) {
                      if (! (im = new sharkImage(sharkStartFrame.publicImageLib[j], s, false, false)).
                          newf){
                          if(!shark.production)MYSQLUpload.currImageDb = sharkStartFrame.publicImageLib[j];
                        return im;
                      }
                    }
                  }
                  else{
                   if (! (im = new sharkImage(sharkStartFrame.publicImageLib[specificImLib], s, false, false)).
                      newf){
                       if(!shark.production)MYSQLUpload.currImageDb = sharkStartFrame.publicImageLib[specificImLib];
                    return im;
                   }
                  }
              }
              
            }
    }
    else if(type==sharkImage.TYPE_SIGN){
      if (!justpics) {
          if(specificImLib < 0){
            for(short j=0;j<sharkStartFrame.publicSignLib.length;++j) {
               if(!(im =  new sharkImage(sharkStartFrame.publicSignLib[j], name, false,false)).newf)
                 return im;
            }
          }
          else{
              if(!(im =  new sharkImage(sharkStartFrame.publicSignLib[specificImLib], name, false,false)).newf)
              return im;            
          }
      }
    }
    else if(type==sharkImage.TYPE_FINGER){
        // not found - use list for finger spelling
      if (name.indexOf('_') < 0 && !justpics) {
        sharkImage mainim = null;
        for (int i = 0; i < name.length(); ++i) {
          if (! (im = new sharkImage(sharkStartFrame.publicFingerLib[0],
                                     name.charAt(i) + "_", false, false)).newf) {
            if (mainim == null) {
              mainim = new sharkImage(im,false);
              mainim.list = new sharkImage[name.length()];
            }
            mainim.list[i] = im;
          }
        }
        if (mainim != null)
          return mainim;
      }
    }
    return null;
}


    public static saveTreeWordList getSaveTreeWordList(boolean allowimports, boolean ownwordlists, student stu) {
        boolean extracourse = false;
        Object o = null;
        boolean allowownwords = allowOwnWords(stu);
        if (allowimports
                && (OwnWordLists.workingList != null
                || ((sharkStartFrame.currPlayTopic != null && (extracourse = u.findString(sharkStartFrame.publicExtraCourseLib, sharkStartFrame.currPlayTopic.databaseName) >= 0)) || (ownwordlists && allowownwords && sharkStartFrame.currPlayTopic != null && sharkStartFrame.currPlayTopic.ownlist)))) {
            String db2 = extracourse ? sharkStartFrame.currPlayTopic.databaseName : sharkStartFrame.resourcesPlus + sharkStartFrame.currPlayTopic.databaseName + sharkStartFrame.resourcesFileSuffix;

            if (OwnWordLists.workingList != null) {
                o = OwnWordLists.workingList;
            } else {
                o = db.find(db2,
                        sharkStartFrame.currPlayTopic.name, db.TOPIC);
            }
        }
        return (o instanceof saveTreeWordList) ? ((saveTreeWordList) o) : null;
    }

    public static sharkImage getSaveTreeWordListImage(String name, saveTreeWordList ownWordListSaveTree) {
        byte buf[];
        for (int i = 1; ownWordListSaveTree.pics != null && i < ownWordListSaveTree.names.length && i < ownWordListSaveTree.pics.length; i++) {
            if (ownWordListSaveTree.names[i].equals(name)) {
                if (ownWordListSaveTree.pics[i] != null) {
                    buf = ownWordListSaveTree.pics[i];
                    if (buf == null) {
                        buf = (byte[]) db.find(
                                sharkStartFrame.currPlayTopic.databaseName,
                                name, db.PICTUREPLIST);
                    }
                    if (buf != null) {
                        MediaTracker tracker = new MediaTracker(sharkStartFrame.mainFrame);
                        Image image1 = sharkStartFrame.t.createImage(buf);
                        tracker.addImage(image1, 1);
                        try {
                            tracker.waitForAll();
                        } catch (InterruptedException ie) {
                        }
                        sharkImage si = new sharkImage(image1, name);
                        si.isimport = true;
                        return si;
                    }
                }
            }
        }
        return null;
    }

static boolean allowOwnWords(student stu) {
    return (sharkStartFrame.allowStuImportPics_OwnWords || stu.administrator);
}




public static boolean findQuery(String name, student student, int type, boolean justpics, boolean justuniversal, String dbname, boolean ownwordlists) {
    if(type<0)return false;
    if(name==null)return false;
    sharkImage im;
    if(type==sharkImage.TYPE_PICTURE){
          if (student.hasimages
              && ! (im = new sharkImage(student.name, name, false, false)).newf)
              return true;
          byte buf[];
          if (sharkStartFrame.currStudent >= 0) {
            student stu = student;

            if (dbname!=null){
               if(db.query(dbname, name, db.PICTUREPLIST)>=0){
                 return true;
               }
            }
            boolean allowicons = (sharkStartFrame.allowStuImportPics_Icon || student.administrator);
            boolean allowownwords = (sharkStartFrame.allowStuImportPics_OwnWords || student.administrator);
            if (name.startsWith(PickPicture.ownpic)){
               if(allowicons &&(db.query(stu.name, PickPicture.ownpicstuset, db.PICTURE)>=0)){
                 return true;
               }
               if(db.query(stu.name, PickPicture.ownpic, db.PICTURE)>=0){
                 return true;
               }
            }          
            // if is user word list
            boolean extracourse = false;
            if((sharkStartFrame.currPlayTopic!=null && (extracourse = u.findString(sharkStartFrame.publicExtraCourseLib, sharkStartFrame.currPlayTopic.databaseName)>=0 )) ||   (ownwordlists && allowownwords &&  sharkStartFrame.currPlayTopic!=null && sharkStartFrame.currPlayTopic.ownlist)){
                String db2 = extracourse?sharkStartFrame.currPlayTopic.databaseName:sharkStartFrame.resourcesPlus+sharkStartFrame.currPlayTopic.databaseName+sharkStartFrame.resourcesFileSuffix;   
                Object o;
                if(OwnWordLists.workingList!=null) 
                    o = OwnWordLists.workingList;
                else o = db.find(db2,
                         sharkStartFrame.currPlayTopic.name,db.TOPIC);
                if(o instanceof saveTreeWordList){
                    saveTreeWordList stwl = (saveTreeWordList)o;
                     for(int i = 1; stwl!=null && stwl.pics!=null && i< stwl.names.length && i< stwl.pics.length; i++){
                         if(stwl.names[i].equals(name) && stwl.pics[i] != null){
                             buf = stwl.pics[i];
                             if(buf==null)
                             buf = (byte[]) db.find(
                                    sharkStartFrame.currPlayTopic.databaseName,
                                    name, db.PICTUREPLIST);
                             if (allowownwords &&  buf!=null) {
                               return true;
                            }
                         }
                     } 
                      for(int i = 1; stwl.preferredPic!=null && i< stwl.names.length && i< stwl.preferredPic.length; i++){
                            for(int j = 0; j < u.picSearchAdds.length; j++){
                                if(name.endsWith(u.picSearchAdds[j]) &&  name.substring(0, name.length()-u.picSearchAdds[j].length()).equals(stwl.names[i])){
                                    if(stwl.preferredPic[i] != null){
                                       name = stwl.preferredPic[i];
                                   }
                                }                                
                            }
                      }
                }
            }

            String nam = name;
            int k;
            if((k=name.indexOf("@@"))>=0){
                nam = name.substring(0, k);
            }
//            if (!justuniversal && allow  && (db.query(sharkStartFrame.resourcesPlus+ stu.name + sharkStartFrame.resourcesFileSuffix, nam, db.PICTURE)>=0)) {
//              return true;
//            }
            if (db.query(PickPicture.universalImageFile, nam, db.PICTURE)>=0) {
              return true;
            }
          }
          if(scannedlist.length>0 ) {
            Image tifim;
            if ( (tifim = findscanned(name)) != null) {
              im = new sharkImage(tifim, name);
              if(im!=null)return true;
            }
          }
           if ( name.endsWith("@@w") &&   (db.query(sharkStartFrame.publicWidgitLib, name.substring(0, name.indexOf("@@w")), db.PICTURE)>=0)) {
              return true;
            }
            for (short j = 0; j < sharkStartFrame.publicImageLib.length; ++j) {
 //             if (! (im = new sharkImage(sharkStartFrame.publicImageLib[j], name, false, false)).
 //                 newf)
                if(db.query(sharkStartFrame.publicImageLib[j], name, db.IMAGE) >= 0)
                return true;
            }
            if(name.endsWith("~")) {
              String s = u.gettext("phonicshomos",name.substring(0,name.length()-1));
              if(s != null) {
                for (short j = 0; j < sharkStartFrame.publicImageLib.length; ++j) {
//                  if (! (im = new sharkImage(sharkStartFrame.publicImageLib[j], s, false, false)).
//                      newf)
                    if(db.query(sharkStartFrame.publicImageLib[j], name, db.IMAGE) >= 0)
                    return true;
                }
              }
            }
    }
    else if(type==sharkImage.TYPE_SIGN){
      if (!justpics) {
         for(short j=0;j<sharkStartFrame.publicSignLib.length;++j) {
//            if(!(im =  new sharkImage(sharkStartFrame.publicSignLib[j], name, false,false)).newf)
            if(db.query(sharkStartFrame.publicSignLib[j], name, db.IMAGE) >= 0)
              return true;
         }
      }
    }
    else if(type==sharkImage.TYPE_FINGER){
        // not found - use list for finger spelling
      if (name.indexOf('_') < 0 && !justpics) {
        sharkImage mainim = null;
        return true;
      }
    }
    return false;
}

   //---------------------------------------------------------------------
   public static String[] imagelibs() {
       String s[];
       if(sharkStartFrame.currStudent >= 0  &&  sharkStartFrame.studentList[sharkStartFrame.currStudent].hasimages
          && !sharkStartFrame.studentList[sharkStartFrame.currStudent].isnew) {
          s = new String[sharkStartFrame.publicImageLib.length+1];
          int currpos = 1;
          s[0] = sharkStartFrame.studentList[sharkStartFrame.currStudent].name;
          System.arraycopy(sharkStartFrame.publicImageLib,0,s,currpos,sharkStartFrame.publicImageLib.length);
          System.arraycopy(sharkStartFrame.publicSignLib,0,s,currpos+=sharkStartFrame.publicImageLib.length,sharkStartFrame.publicSignLib.length);
          System.arraycopy(sharkStartFrame.publicFingerLib,0,s,currpos+=sharkStartFrame.publicSignLib.length,sharkStartFrame.publicFingerLib.length);
          return s;
       }
       else return sharkStartFrame.publicImageLib;
  }
  //---------------------------------------------------------------------
  public static String[] imagelibup() {
      String s[];
      if(sharkStartFrame.currStudent >= 0
         && sharkStartFrame.studentList[sharkStartFrame.currStudent].name != null) {
         s = new String[sharkStartFrame.publicImageLib.length+sharkStartFrame.publicSignLib.length+sharkStartFrame.publicFingerLib.length+1];
         int currpos = 1;
         s[0] = sharkStartFrame.studentList[sharkStartFrame.currStudent].name;
         System.arraycopy(sharkStartFrame.publicImageLib,0,s,currpos,sharkStartFrame.publicImageLib.length);
         System.arraycopy(sharkStartFrame.publicSignLib,0,s,currpos+=sharkStartFrame.publicImageLib.length,sharkStartFrame.publicSignLib.length);
         System.arraycopy(sharkStartFrame.publicFingerLib,0,s,currpos+=sharkStartFrame.publicSignLib.length,sharkStartFrame.publicFingerLib.length);
         return s;
      }
      else return sharkStartFrame.publicImageLib;
 }
  public void adjustSize(int screenwidth,int screenheight) {
//startPR2008-06-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      if(isvideo)return;
//endPRimagesandvids^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      int ww,hh;
      int w1 = w *screenwidth/mover.WIDTH;
      int h1 = h *screenheight/mover.HEIGHT;
      if(list != null) {    // list for finger spelling - no change
        return;
      }
      if(tifimage == null && im.minsize != null) {
        ww = im.minsize.width * sharkStartFrame.mainFrame.screenSize.height/ BASEU;
        hh = im.minsize.height * sharkStartFrame.mainFrame.screenSize.height/ BASEU;
        if(ww > w1 || hh > h1) {
          w = ww *mover.WIDTH/screenwidth;
          h = hh *mover.HEIGHT/screenheight;
          return;
        }
      }
      if(tifimage != null) {
        while(true) {
          hh = tifimage.getHeight(null);
          ww = tifimage.getWidth(null);
          if(hh<=0 || ww <= 0) u.pause(200);
          else break;
        }
      }
      else if(im.clip != null) {
        ww = im.clip.width;
        hh = im.clip.height;
      }
      else {
         ww = (im.x2 - im.x1);
         hh = (im.y2 - im.y1);
      }
      if(w1*hh >  ww*h1) {
            w = (ww * h1 / hh + 1) * mover.WIDTH/screenwidth;
      }
      else {
            h = (hh * w1 / ww + 1) * mover.HEIGHT/screenheight;
      }
  }
  public Color getcolor(int gno) {
      if(tifimage != null) return Color.black;
//startPR2008-06-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      if(isvideo) return Color.black;
//endPRimagesandvids^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      for(short i=0;i<im.parts.length;++i) {
          if(im.parts[i].group == gno) return im.parts[i].color;
      }
      return null;
  }
  public void recolor(Color from,Color color) {
      for(short i=0;i<im.parts.length;++i) {
          if(im.parts[i].color.equals(from)) im.parts[i].color = color;
      }
  }
  public void recolor(int gno,Color color) {
      for(short i=0;i<im.parts.length;++i) {
          if(im.parts[i].group == gno) im.parts[i].color = color;
      }
  }
  //-------------------------------------------------------------------
  void importimage(String name, String database) {
     importname = name; importdb = database;
     wantimport = true;
  }
  //-------------------------------------------------------------------
  void doimportimage() {
     int i,j,k;
     String name = importname;
     String database = importdb;
     importname = null;
     importdb = null;
     wantimport = false;
     saveSharkImage imp = gotimport!=null?gotimport:(saveSharkImage)db.find(database,name,db.IMAGE);
     gotimport = null;
     if(imp != null) {
         if(ismark) {
            doresize(imp,false);
            ismark = false;
         }
         startchange();
         for(i=0;i<imp.controls.length;++i) {
             for(j=0;j<im.controls.length;++j) {
                if(imp.controls[i].name.equalsIgnoreCase(im.controls[j].name)
                   || (k=imp.controls[i].name.indexOf(".")) >= 0
                      && im.controls[j].name.indexOf(".")==k
                      && imp.controls[i].name.substring(0,k).equalsIgnoreCase(im.controls[j].name.substring(0,k))) {
                  for(j=0;j<imp.controls.length;++j) {
                     if(imp.controls[j].nextcontrol != null
                          && imp.controls[j].nextcontrol.equalsIgnoreCase(imp.controls[i].name)) {
                        imp.controls[j].nextcontrol = "x" +  imp.controls[j].nextcontrol;
                     }
                  }
                  imp.controls[i].name = "x"+imp.controls[i].name;
                  j=-1;
                }
            }
         }
         saveImagePart newp[] = new saveImagePart[im.parts.length + imp.parts.length];
         saveImageControl newc[] = new saveImageControl[im.controls.length+imp.controls.length];
         System.arraycopy(im.parts,0,newp,0,im.parts.length);
         System.arraycopy(imp.parts,0,newp,im.parts.length,imp.parts.length);
         for(i=im.parts.length;i< newp.length;++i) {
            newp[i].group += p.length;
            if(newp[i].attachno >=0) newp[i].attachno += p.length;
            if(newp[i].controlno>=0) newp[i].controlno += im.controls.length;
         }
         System.arraycopy(im.controls,0,newc,0,im.controls.length);
         System.arraycopy(imp.controls,0,newc,im.controls.length,imp.controls.length);
         im.controls = newc;
         im.parts = newp;
         activecontrol = -1;
         activelist = null;
         setup();
         listControls(lcon);
     }
  }
  //-------------------------------------------------------------
  public void fixtomouse(short gno) {
     int i,gone=-1;
     if(gno < 0) {       // free from mouse
        if(mousecontrol >= 0) {
           gone=-1;
           imageControl next = c[mousecontrol].getnext();
           next.activate();
           for(i=0;i<p.length;++i) {
              if(im.parts[p[i].topart].controlno == mousecontrol) {
                 gone = (int)((p[i].end-p[i].start)* (1000-mousedist())/1000);
                 p[i].start -=  gone;
                 p[i].end -=  gone;
              }
           }
           if(gone >=0) {
              next.start -= gone;
              next.end -= gone;
           }
           mousecontrol = -1;
        }
        return;
     }                 // tie to mouse
     mousecontrol = im.parts[p[gno].frompart].controlno;
     if(mousecontrol >= 0) {
        imageControl c1 = c[mousecontrol];
        mousecontrol = im.parts[p[gno].frompart].controlno;
        mousex1 = manager.mousexs;
        mousey1 = manager.mouseys;
        c1.activate();
        int nodex = manager.mousexs - p[gno].getxx(im.parts[p[gno].frompart].node[0]);
        int nodey = manager.mouseys - p[gno].getyy(im.parts[p[gno].frompart].node[0]);
        short savepart = p[gno].frompart;
        p[gno].frompart = p[gno].topart;
        p[gno].init();
        p[gno].setCurr();
        mousex2 = nodex + p[gno].getxx(im.parts[p[gno].topart].node[0]);
        mousey2 = nodey + p[gno].getyy(im.parts[p[gno].topart].node[0]);
        p[gno].frompart = savepart;
        p[gno].init();
     }
  }
  //-------------------------------------------------------------------
  public int mousedist() {
     int d1 = (int)Math.sqrt((manager.mousexs - mousex1)*(manager.mousexs - mousex1) + (manager.mouseys - mousey1)*(manager.mouseys - mousey1));
     int d2 = (int)Math.sqrt((manager.mousexs - mousex2)*(manager.mousexs - mousex2) + (manager.mouseys - mousey2)*(manager.mouseys - mousey2));
     if(d1+d2 == 0) return 0;
     return 1000*d1/(d1+d2);
  }
  //--------------------------------------------------------------------
  public Polygon getPolygon(int gno) {
     if(gno > p.length || gno < 0) gno = p.length-1;
     return p[gno].getp(p[gno].drawnode);
  }
  //--------------------------------------------------------------------
  public Rectangle getRect(int gno) {
     if(gno >= p.length || gno < 0) gno = p.length-1;
     imagePart pp = p[gno];
     if(pp.drawnode == null) return null;
            int x1 = pp.getxx(pp.drawnode[0]);
            int y1 = pp.getyy(pp.drawnode[0]);
            int x2 = pp.getxx(pp.drawnode[1]);
            int y2 = pp.getyy(pp.drawnode[1]);
            return new Rectangle(Math.min(x1,x2), Math.min(y1,y2),Math.abs(x1-x2), Math.abs(y1-y2));
  }
  //----------------------------------------------------------------------
  public void saytext(int mx, int my) {
     imagePart pp;
     if(markedforsay>=0 && spokenWord.isfree()) {
         pp = p[markedforsay];
         if(pp.frompart >= 0 && im.parts[pp.frompart].type == TEXT) {
             String s = u.combineString(((saveTextImage)im.parts[pp.frompart]).text);
             if(!spokenWord.findandsaysentence(s))   spokenWord.findandsay(s);
         }
     }
  }
  
  public String getPartString() {
      if(currPart!=null)
        return u.combineString(((saveTextImage)im.parts[currPart.frompart]).text);
      else return null;
  }
  //----------------------------------------------------------------------
     // all text strings over 2 chars long in publicimage database
     //  - used by recordwords for help_
  static String[] alltext(String search) {
     String list[],s[]=null;
     int i,j,k;
     saveSharkImage pp;

     for(j=0;j<sharkStartFrame.publicImageLib.length;++j)  {
       list = db.list(sharkStartFrame.publicImageLib[j], db.IMAGE,search);
       for(i=0; i<list.length; ++i) {
          pp = (saveSharkImage) db.find(sharkStartFrame.publicImageLib[j],
                                                     list[i], db.IMAGE);
          for(k=0; k<pp.parts.length; ++k) {
              String dd;
             if(pp.parts[k].type == TEXT){
                s = u.addString(s,(dd=u.combineString(((saveTextImage)pp.parts[k]).text)));
//         if(dd.indexOf("A word is spoken for|you (the active student)|to find on your card")>=0){
//       if(s.equals("A blend is spoken.|Listen to it, then work|out the first sound.")){
//           int h;
//           h = 9;
//       }
              }



          }
       }
     }
     return s;
  }
  //----------------------------------------------------------------------
  public static Polygon thickpoly(Polygon p,int thick) {
     int oldpoints = p.npoints,x1,y1,oldx=0,oldy=0,dx,dy;
     p.addPoint(p.xpoints[0],p.ypoints[0]);
     double a,aa,lastgrad = Math.atan2(p.ypoints[0]-p.ypoints[1],
                                    p.xpoints[0]-p.xpoints[1]);
     for(int i=oldpoints;i>0;--i) {
        a = Math.atan2(p.ypoints[i-1]-p.ypoints[i] ,p.xpoints[i-1]-p.xpoints[i]);
        aa = (lastgrad+Math.PI+a)/2;
        dx = (int)(thick*Math.cos(aa));
        dy = (int)(thick*Math.sin(aa));
        x1 = p.xpoints[i]+dx;
        y1 = p.ypoints[i]+dy;
        if(i==oldpoints) {
           if(!p.contains(x1,y1)) {x1 -= dx*2; y1 -= dy*2;}
        }
        else if (!u.sameside(x1,y1,oldx,oldy,
               p.xpoints[i+1],p.ypoints[i+1], p.xpoints[i], p.ypoints[i]))
                   {x1 -= dx*2; y1 -= dy*2;}
        p.addPoint(oldx=x1,oldy=y1);
        lastgrad =  a;
     }
     p.addPoint(p.xpoints[oldpoints+1],p.ypoints[oldpoints+1]);
     return p;
  }
  //----------------------------------------------------------------------
  public static Polygon thickpolyline(Polygon p,int thick) {
     int oldpoints = p.npoints,x1,y1,oldx=0,oldy=0,dx,dy;
     double a,aa,lastgrad = Math.atan2(p.ypoints[0]-p.ypoints[1],
                                    p.xpoints[0]-p.xpoints[1]);
     double origgrad = lastgrad;
     for(int i=oldpoints-1; i>=0; --i) {
        if(i==0) a = origgrad;
        else a = Math.atan2(p.ypoints[i-1]-p.ypoints[i] ,p.xpoints[i-1]-p.xpoints[i]);
        aa = (lastgrad+Math.PI+a)/2;
        dx = (int)(thick*Math.cos(aa));
        dy = (int)(thick*Math.sin(aa));
        x1 = p.xpoints[i]+dx;
        y1 = p.ypoints[i]+dy;
        if(i==oldpoints-1) {
           if(!p.contains(x1,y1)) {x1 -= dx*2; y1 -= dy*2;}
        }
        else if (!u.sameside(x1,y1,oldx,oldy,
               p.xpoints[i+1],p.ypoints[i+1], p.xpoints[i], p.ypoints[i]))
                   {x1 -= dx*2; y1 -= dy*2;}
        p.addPoint(oldx=x1,oldy=y1);
        lastgrad =  a;
     }
     return p;
  }
  //----------------------------------------------------------------------
  public static Polygon thickline(int x1,int y1,int x2,int y2,int thick) {
     double a = Math.atan2(y2-y1,x2-x1) + Math.PI/2;
     double xx = thick*Math.cos(a);
     int dx = (int)(xx/2);
     int dx2 = (xx<0) ? (int)((xx-1)/2) : (int)((xx+1)/2);
     xx = thick*Math.sin(a);
     int dy = (int)(xx/2);
     int dy2 = (xx<0) ? (int)((xx-1)/2) : (int)((xx+1)/2);
     return  new Polygon(new int[]{x1-dx,x1+dx2,x2+dx2,x2-dx},
                         new int[] {y1-dy,y1+dy2,y2+dy2,y2-dy},4);
  }
  //----------------------------------------------------------------------
     // a useful public routine (used by snakes&ladders)
  public static void addarc(Polygon p,int x, int y, int a, int b, int from,int to) {
         int n = 64/Math.min(64,Math.max(1,(a+b)/2)),i;
         if(from<to)
                for(i = from; i<=to; i+=n)  p.addPoint(x+a*ocos[i%64]/FACTOR, y+b*osin[i%64]/FACTOR);
          else  for(i = from; i>=to; i-=n)  p.addPoint(x+a*ocos[i%64]/FACTOR, y+b*osin[i%64]/FACTOR);
  }

  static scanned[] getscanned() {
    scannedlist = new scanned[0];
    getscanned(sharkStartFrame.publicPathplus+"images");
    if(scantot < scannedlist.length) {
          scanned nn[] =  new scanned[scantot];
          System.arraycopy(scannedlist,0,nn,0,scantot);
          scannedlist = nn;
   }
   if(scantot>0) Arrays.sort(scannedlist);
   return scannedlist;
  }
  static void getscanned(String ff) {
      File f = new File(ff);
      if(f.isDirectory()) {
        String ss[] = f.list();
        for(int i=0;i<ss.length;++i) getscanned(ff+sharkStartFrame.separator+ss[i]);
      }
      else {
        String n = f.getName();
        int i;
        if((i=n.indexOf('.'))>0) {
           if(scantot >= scannedlist.length) {
             scanned nn[] =  new scanned[scantot+50];
             System.arraycopy(scannedlist,0,nn,0,scantot);
             scannedlist = nn;
           }
           scannedlist[scantot] = new scanned();
           scannedlist[scantot].name = n.substring(0,i);
           scannedlist[scantot].path = f.getAbsolutePath();
           ++scantot;
        }
      }
  }
  static Image findscanned(String name) {
    scanned s = new scanned();
    s.name = name;
    int ss = Arrays.binarySearch(scannedlist,s);
    if(ss >= 0) return sharkStartFrame.t.createImage(scannedlist[ss].path);
    else return null;
  }
  static boolean queryscanned(String name) {
    scanned s = new scanned();
    s.name = name;
    int ss = Arrays.binarySearch(scannedlist,s);
    return ss >= 0;
  }
  static sharkImage[] addscannedall(String search,sharkImage already[]) {
    int i,j,tot=0;
    sharkImage newim[];
    for(i=0;i<scannedlist.length;++i) {
        if(scannedlist[i].name.toLowerCase().indexOf(search) ==0) ++tot;
    }
    if(tot > 0) {
       newim = new sharkImage[already.length+tot];
       System.arraycopy(already,0,newim,0,already.length);
       for(i=0, j = already.length; i<scannedlist.length; ++i) {
          if(scannedlist[i].name.toLowerCase().indexOf(search) == 0)
            newim[j++] = new sharkImage(sharkStartFrame.t.createImage(scannedlist[i].path),scannedlist[i].name);
       }
       return newim;
    }
    else return already;
  }
  static String[] names(sharkImage im[]) {
     int i;
     String nn[] = new String[im.length];
     for (i=0;i<nn.length;++i) nn[i] = im[i].name;
     return nn;
  }
  static Point[] poval2(Point pt[]) {
     int x = (pt[0].x + pt[1].x)/2;
     int y = (pt[0].y + pt[1].y)/2;
     int a = Math.abs(pt[0].x - pt[1].x)/2;
     int b = Math.abs(pt[0].y - pt[1].y)/2;
     int n = 64;
     Point ptn[] = new Point[n];
     for(short i = 0,j=0; i<n; ++i,j+=64/n) {
        ptn[i] =new Point( x+a*ocos[j]/FACTOR, y+b*osin[j]/FACTOR);
     }
     return ptn;
  }
  //========================================================================================================
  class saver extends JDialog {
    public int screenwidth;
    public int screenheight;
    int selected = -1;
    BorderLayout layout1 = new BorderLayout();
    public runMovers mainPanel;
    public Color background;
    chooseButton c[] = new chooseButton[10], c2[];
    saver thisframe = this;
    JButton use = new JButton("Use");
    JButton remove= new JButton("Remove");
    JButton savecolor= new JButton("Save color");
    JButton saveimage= new JButton("Save image");
    JButton exit = new JButton("Exit");
    Object oo = db.find(sharkStartFrame.studentList[sharkStartFrame.currStudent].name, "saveimage_", db.VECTOR);
    java.util.Vector vv = oo == null ? new java.util.Vector() : (java.util.Vector)oo;
    chooseButton cc[];

    saver(){
      super(sharkStartFrame.mainFrame);
      this.setTitle("Saved colours and images - to use one of them, click to select and then click 'Use'");
      this.setResizable(false);
      setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
      setBounds(new Rectangle(sharkStartFrame.screenSize.width/6,
            sharkStartFrame.screenSize.height/6,
            sharkStartFrame.screenSize.width*2/3,
            sharkStartFrame.screenSize.height*2/3));
      this.addWindowListener(new java.awt.event.WindowAdapter() {
        public void windowClosing(WindowEvent e) {
           mainPanel.stop();
        }
//         public void windowDeactivated(WindowEvent e) {
//           mainPanel.stop();
//           thisframe.dispose();
//         }
      });
//startPR2004-09-01^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    // enables exiting screen via the ESC key
    addKeyListener(new KeyAdapter() {
      public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_ESCAPE){
          mainPanel.stop();
          thisframe.dispose();
        }
      }
    });
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      this.getContentPane().setLayout(layout1);
      this.setEnabled(true);
//startPR2004-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    this.setTitle(title);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      mainPanel = new runMovers();
      this.getContentPane().add(mainPanel, BorderLayout.CENTER);
      background = mainPanel.getBackground();
      use.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          if(selected >= 0) {
            Object oo = vv.get(selected);
            if(oo instanceof Color) {
                currColor = (Color)oo;
            }
            else if(oo instanceof saveSharkImage) {
              java.util.Vector dup = (java.util.Vector)db.find(sharkStartFrame.studentList[sharkStartFrame.currStudent].name, "saveimage_", db.VECTOR);
              wantimport = true;
              gotimport = (saveSharkImage)dup.get(selected);
            }
            dispose();
          }
        }
      });
      remove.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            if(selected>=0) {
              vv.remove(selected);
              if(selected > vv.size()-1) --selected;
              save();
            }
        }
      });
      savecolor.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            if(selected>=0)        vv.add(selected,currColor);
            else vv.add(currColor);
             save();
        }
      });
      saveimage.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            if(selected>=0)        vv.add(selected,im);
            else vv.add(im);
            save();
        }
      });
      exit.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            dispose();
          }
      });
      JPanel butt = new JPanel(new GridBagLayout());
      GridBagConstraints grid1 = new GridBagConstraints();
      grid1.fill = GridBagConstraints.NONE;
      grid1.gridx = -1;
      grid1.gridy = 0;
      grid1.weightx = 1;
      grid1.weighty = 1;
      butt.add(use,grid1);
      butt.add(remove,grid1);
      butt.add(savecolor,grid1);
      butt.add(saveimage,grid1);
      butt.add(exit,grid1);
      getContentPane().add(butt,BorderLayout.SOUTH);
      addim();
      setVisible(true);
      validate();
      mainPanel.start1();
  }
  void save() {
    db.update(sharkStartFrame.studentList[sharkStartFrame.currStudent].name,  "saveimage_", vv,db.VECTOR);
    addim();
  }
  void addim() {
    int i,j;
    cc = new chooseButton[j = vv.size()];
    int dim = (int)Math.ceil(Math.sqrt(j));
    dim = Math.max(dim,3);
    int ww = mover.WIDTH/dim;
    int hh = mover.HEIGHT/dim;
    mainPanel.removeAllMovers();
    for(i=0;i<vv.size();++i) {
      cc[i] = new chooseButton(i,vv.get(i));
      cc[i].w = ww*3/4;
      cc[i].h = hh*3/4;
      mainPanel.addMover(cc[i], i%dim*ww + ww/8, i/dim*hh + hh/8);
    }
  }
  //-------------------------------------------------------------
  class chooseButton extends mover {
      Object oo;
      int itemnumber;
      sharkImage im2;
      public chooseButton(int ino, Object o) {
         itemnumber = ino;
         oo = o;
         keepMoving=true;
         if(oo instanceof saveSharkImage) {
            im2 = new sharkImage((saveSharkImage)o);
         }
     }
      public void paint(Graphics g,int x, int y, int w, int h) {
         if(oo instanceof Color) {
           g.setColor((Color)oo);
           g.fillRect(x+4,y+4,w-8,h-8);
         }
         else if(oo instanceof saveSharkImage) {
           im2.paint(g,x,y,w,h);
         }
         if(selected==itemnumber) {
           g.setColor(Color.red);
           g.drawRect(x,y,w,h);
           g.drawRect(x+1,y+1,w-2,h-2);
         }
      }
      public void mouseClicked(int x, int y) {
         selected=itemnumber;
     }
  }
  }
//startPR2009-12-23^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   private String[] doIgnore(String name) {
     String res[] = new String[] {name};
     String extra = null;
     for (int i = 0; i < u.ignore.length; i++) {
       if (name.startsWith(u.ignore[i]) && (name.length() > u.ignore[i].length()))
         extra = name.substring(u.ignore[i].length());
     }
     if (extra != null) res = u.addString(res, extra);
     return res;
   }

  public static boolean exists(String name, topic t) {
    if(exists2(name))return true;
    if(u.ignore.length>0){
      for(int i = 0; i < u.ignore.length; i++){
        if(name.startsWith(u.ignore[i])){
          name = name.substring(u.ignore[i].length());
          if(exists2(name, t))return true;
        }
      }
    }
    return false;
  }   
   
  public static boolean exists(String name) {
    return exists(name, null);
  }
  
  /*
            if(allowownwords &&  ( sharkStartFrame.currPlayTopic!=null && sharkStartFrame.currPlayTopic.ownlist)){
                 saveTreeWordList stwl =
                         (saveTreeWordList)db.find(sharkStartFrame.resourcesdb,sharkStartFrame.currPlayTopic.name,db.TOPIC);
                 for(int i = 1; stwl!=null && stwl.pics!=null && i< stwl.names.length && i< stwl.pics.length; i++){
                     if(stwl.names[i].equals(name) && stwl.pics[i] != null){
                         buf = stwl.pics[i];
                         if(buf==null)
                         buf = (byte[]) db.find(
                                sharkStartFrame.currPlayTopic.databaseName,
                                name, db.PICTUREPLIST);
                         if (allowownwords &&  buf!=null) {
                           return true;
                        }
                     }
                 }  
            }   * 
   */

  public static boolean exists2(String name) {
    return exists2(name, null);
  }  
  
  public static boolean exists2(String name, topic t) {
            if( sharkStartFrame.currPlayTopic!=null && sharkStartFrame.currPlayTopic.ownlist){
                 saveTreeWordList stwl =
                         (saveTreeWordList)db.find(sharkStartFrame.resourcesdb,sharkStartFrame.currPlayTopic.name,db.TOPIC);
                 for(int i = 1; stwl!=null && stwl.pics!=null && i< stwl.names.length && i< stwl.pics.length; i++){
                     if(stwl.names[i].equals(name) && stwl.pics[i] != null){
                         byte[] buf;
                         buf = stwl.pics[i];
                         if(buf==null)
                         buf = (byte[]) db.find(
                                sharkStartFrame.currPlayTopic.databaseName,
                                name, db.PICTUREPLIST);
                         if (buf!=null) {
                           return true;
                        }
                     }
                 }  
            }
    for(int j = 0;j < sharkStartFrame.publicImageLib.length;++j) {
       if(db.query(sharkStartFrame.publicImageLib[j], name, db.IMAGE) >= 0) return true;
    }
    if(name.endsWith("~")) {
      String s = u.gettext("phonicshomos",name.substring(0,name.length()-1));
      if(s != null) {
        for (short j = 0; j < sharkStartFrame.publicImageLib.length; ++j) {
          if(db.query(sharkStartFrame.publicImageLib[j], s, db.IMAGE) >= 0) return true;
         }
      }
    }
    if(findQuery(sharkStartFrame.resourcesdb, name, true))return true;
    return scannedlist.length>0 && queryscanned(name);
  }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  public static boolean hassign(String name) {
    for(int j = 0;j < sharkStartFrame.publicSignLib.length;++j) {
       if(db.query(sharkStartFrame.publicSignLib[j], name, db.IMAGE) >= 0) return true;
    }
    return false;
  }
  public static interface imagefunc {
    public abstract boolean keepnodeorder();
    public abstract void setup(String s[]);
    public abstract void paint(Graphics g, int x1, int y1, int w1, int h1);
    public abstract JDialog edit(saveTextImage ti[]);
    public abstract void interpolate(String from[], String to[], long start, long end,long currtime);
    public abstract Rectangle getBounds();              // return null if just normal rectangle used to draw
  }
}


