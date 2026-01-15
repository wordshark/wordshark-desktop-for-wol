package shark;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class chooser_base
//startPR2004-08-27^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  extends JDialog {
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   public int screenwidth;
   public int screenheight;
   BorderLayout layout1 = new BorderLayout();
   public  runMovers mainPanel;
   public Color background;
   int buttontot;
   sharkImage c[] = new sharkImage[10],c2[];
   chooser_base thisframe=this;
//startPR2005-10-16^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   public static boolean active;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    Font labelfont;
    FontMetrics fm;;

//startPR2004-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   public chooser_base(String title){
     this.setTitle(title);
     addConstructor();
   }
   public chooser_base(String title, JFrame owner){
     super(owner);
     this.setTitle(title);
     addConstructor();
   }
   public chooser_base(String title, JDialog owner){
     super(owner);
     this.setTitle(title);
     addConstructor();
   }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2004-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   private void addConstructor(){
//startPR2008-06-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     if (shark.macOS && runningGame.currGameRunner!=null && runningGame.currGameRunner.game!=null
         && runningGame.currGameRunner.game.gamePanel!=null)
       runningGame.currGameRunner.game.gamePanel.setCursor(Cursor.getDefaultCursor());
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2004-08-27^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     this.setResizable(false);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
     if(!ChangeScreenSize_base.isActive){
        setBounds(new Rectangle(sharkStartFrame.screenSize.width/6,
              sharkStartFrame.screenSize.height/6,
              sharkStartFrame.screenSize.width*2/3,
              sharkStartFrame.screenSize.height*2/3));
     }
     else{
        setBounds(new Rectangle(sharkStartFrame.mainFrame.getLocation().x+sharkStartFrame.originalbounds.width/6,
              sharkStartFrame.mainFrame.getLocation().y+sharkStartFrame.originalbounds.height/6,
              sharkStartFrame.originalbounds.width*2/3,
              sharkStartFrame.originalbounds.height*2/3));
     }
     this.addWindowListener(new java.awt.event.WindowAdapter() {
       public void windowClosing(WindowEvent e) {
//startPR2005-10-16^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          active = false;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          mainPanel.stop();
       }
        public void windowDeactivated(WindowEvent e) {
//startPR2005-10-16^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          active = false;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          mainPanel.stop();
          thisframe.dispose();
        }
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
     setVisible(true);
     validate();
     screenwidth = mainPanel.getSize().width;
     screenheight = mainPanel.getSize().height;
     background = mainPanel.getBackground();
//startPR2005-10-16^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     active = true;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
 }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2008-06-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    public void dispose(){
      super.dispose();
      if(shark.macOS && runningGame.currGameRunner !=null && runningGame.currGameRunner.game!=null){
        runningGame.currGameRunner.game.setsprite();
        if (runningGame.currGameRunner.game.gamePanel != null) {
          runningGame.currGameRunner.game.gamePanel.refreshat = System.currentTimeMillis() + 2000;
        }
      }
    }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  //------------------------------------------------------------
  public void add(String dir, String name,int width, int height) {
     if(name.equals("x_aaa"))  {width = -1; height = -1;}
     if(buttontot >= c.length) {
        c2 = new chooseButton[c.length+10];
        System.arraycopy(c,0,c2,0,c.length);
        c=c2;
     }
     c[buttontot] = new chooseButton(dir,name,width,height,buttontot);
     buttontot++;
  }


  public void add(Image im, String name,int width, int height) {
     if(buttontot >= c.length) {
        c2 = new chooseButton[c.length+10];
        System.arraycopy(c,0,c2,0,c.length);
        c=c2;
     }
     c[buttontot] = new chooseButton(im,name,width,height,buttontot);
     buttontot++;
  }

  public void add(String name, Color col, int width, int height, String longestlabel) {
     if(buttontot >= c.length) {
        c2 = new chooseButtonBlank[c.length+10];
        System.arraycopy(c,0,c2,0,c.length);
        c=c2;
     }
     c[buttontot] = new chooseButtonBlank(name,col,width,height,buttontot, longestlabel);
     buttontot++;
  }

  //----------------------------------------------------------
  public void showit() {
    if(buttontot == 0) return;
    short rows = (short)Math.sqrt(buttontot);
    short perRow = (short)(buttontot / rows);
    short rowtot;
    short extralist[] = u.select(rows,(short)(buttontot - rows*perRow));
    short extras = (short)extralist.length;
    short i,j,k;
    short gwidth = (short)(perRow * (perRow+1));
    int buttonWidth = mover.WIDTH/Math.max(6,perRow+1);
    int buttonHeight = mover.HEIGHT/Math.max(5,rows);

    mainPanel.start1();
    for(i=0,k = 0;k<buttontot;++i){
       rowtot = perRow;
       for(j=0;j<extralist.length;++j) {
           if(extralist[j]==i) {++rowtot; break;}
       }
       for(j=0;j<rowtot;++j, ++k) {
          c[k].x = mover.WIDTH * (j*2+1) / rowtot /2 - buttonWidth/2;
          c[k].y = mover.HEIGHT * (i*2+1) / rows /2 - buttonHeight/2;
          c[k].w =  buttonWidth;
          c[k].h =  buttonHeight;
          mainPanel.addMover(c[k], c[k].x, c[k].y);
       }
    }
//    mainPanel.start1();
  }
  //-------------------------------------------------------------
  class chooseButton extends sharkImage {
      public int itemnumber,innerw,innerh;
      public chooseButton(String dir, String name,int width, int height,int ino) {
         super(dir,name,false,false);
         itemnumber = ino;
         innerw = width;
         innerh = height;

         mouseSensitive = true;
         if(name.equals("x_aaa")) setControl("show");
      }

      public chooseButton(Image im, String name,int width, int height,int ino) {
         super(im, name);
         itemnumber = ino;
         innerw = width;
         innerh = height;
         mouseSensitive = true;
      }
 
      public void paint(Graphics g,int x, int y, int w, int h) {
         if(innerw<0) innerw=w;
         if(innerh<0) innerh=h;
         Rectangle r = new Rectangle(x,y,w,h);
         Font f = g.getFont();
         Color c = g.getColor();
         if(mouseOver) {
           g.setColor(Color.lightGray);
           g.fillRect(r.x ,r.y, r.width, r.height);
           u.buttonBorder(g,r,background,!mouseDown);
         }
         super.paint(g,x+w/2-innerw/2,y+h/2-innerh/2,innerw,innerh);
      }
      public void mouseClicked(int x, int y) {
         selected(itemnumber);
     }
  }

  class chooseButtonBlank extends sharkImage {
      public int itemnumber,innerw,innerh;
      Color colour;
      String label;
 //     Font f1;
 //     FontMetrics fm;
      int textw = -1;
      String longest;
      int halfh = -1;

      public chooseButtonBlank(String name, Color col, int width, int height,int ino, String longesttext) {
          // "bgcolor"  - not important - just stop pictures being shown if name is same as an image
         super("bgcolor"+name, false);
         label = name;
         itemnumber = ino;
         longest = longesttext;
         colour = col;
         innerw = width;
         innerh = height;
         mouseSensitive = true;
      }

      public void paint(Graphics g,int x, int y, int w, int h) {
         if(labelfont==null){
            labelfont = sharkStartFrame.treefont.deriveFont((float)160);
            fm = mainPanel.getFontMetrics(labelfont);
            int border = w/3;
            while(fm.stringWidth(longest)>(w-border)){
                labelfont = labelfont.deriveFont((float)labelfont.getSize()-1);
                fm = mainPanel.getFontMetrics(labelfont);
            }
         }
         if(halfh<0)halfh = fm.getAscent()/4;
         if(textw<0)textw = fm.stringWidth(label);
         if(innerw<0) innerw=w;
         if(innerh<0) innerh=h;
         Rectangle r = new Rectangle(x,y,w,h);
         g.setColor(colour);
         g.fillRect(r.x ,r.y, r.width, r.height);
         if(mouseOver) {

           u.buttonBorder(g,r,background,!mouseDown);
         }
         g.setFont(labelfont);
         g.setColor(Color.darkGray);
         g.drawString(label,
                 x+((w-textw)/2),
                 y+(h/2)+halfh);
         super.paint(g,x+w/2-innerw/2,y+h/2-innerh/2,innerw,innerh);
      }
      public void mouseClicked(int x, int y) {
         selected(itemnumber);
     }
  }


  //---------------------------------- to be overridden
  public void selected(int i) {dispose();}
}
