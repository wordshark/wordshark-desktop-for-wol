package shark;
import java.awt.*;
import shark.games.findsound;

public class card extends mover {
   public static sharkImage backimage;
   public static sharkImage eye = new sharkImage("eye_",false);
   public static sharkImage ear = getear();
   static sharkImage getear() {
      sharkImage ret = new sharkImage(findsound.ear,false);
      ret.recolor(ret.getcolor(0),Color.yellow);
      return ret;
   }
   public sharkImage bigfrontimage, frontimage,extraimage;
   public boolean faceup,alreadyTurned,changecolor,noimage;
   public word value;
   public int numericvalue;
   public Font f;
   public Color color=Color.black, colorback1,colorback2;
//startPR2008-08-05^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//   public byte options;
    public byte optionsval;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   public boolean saying;
   static Color turnedbackcolor,backcolor;

   public card() { super(); }
   public void paint(Graphics g, int x, int y, int w, int h) {
//      keepMoving = false;
       if(shark.macOS) ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
      if(faceup) {
         g.setColor(wordlist.bgcoloruse);
         g.fillRoundRect(x,y,w,h,w/8,h/8);
         if(bigfrontimage != null) {
            bigfrontimage.paint(g, x+3,y+3,w-6,h-6);
         }
         else if(frontimage != null) {
            frontimage.paint(g, x+w/4,y+h/4,w/2,h/2);
         }
         else if(value != null) {
            g.setFont(f);
            g.setColor(Color.black);
//startPR2008-08-05^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//            value.paint(g,new Rectangle(x,y,w,h),options);
            value.paint(g,new Rectangle(x,y,w,h),optionsval);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         }
         if(saying) {
            g.setColor(Color.red);
            g.drawRoundRect(x,y,w-1,h-1,w/8,h/8);
            g.drawRoundRect(x+1,y+1,w-3,h-3,w/8,h/8);
            g.drawRoundRect(x+2,y+2,w-5,h-5,w/8,h/8);
         }
         else {
            g.setColor(Color.black);
            g.drawRoundRect(x,y,w-1,h-1,w/8,h/8);
         }
      }
      else {
         if(!noimage) {
            Color savecolor = backimage.getcolor(0);
            Color backcolx = (!changecolor || !alreadyTurned)?backcolor:turnedbackcolor;
            backimage.recolor(0,backcolx);
            g.setColor(backcolx);
            g.fillRoundRect(x,y,w,h,w/8,h/8);
            if(extraimage != null) {
              extraimage.paint(g,x+w/3,y+h/8,w/3,h*3/4);
              keepMoving = extraimage.canmove;
             }
            else backimage.paint(g,x+1,y+1,w-2,h-2);
            backimage.recolor(0,savecolor);
          }
         else {
              g.setColor(backimage.getcolor(0));
              g.fillRoundRect(x,y,w,h,w/8,h/8);
         }
         g.setColor(Color.black);
         g.drawRoundRect(x,y,w-1,h-1,w/8,h/8);
       }
   }
   //--------------------------------------------------------
   public void say() {
     int x1 = x*manager.screenwidth/WIDTH;
     int y1 = y*manager.screenheight/HEIGHT;
     int w1 = w*manager.screenwidth/WIDTH;
     int h1 = h*manager.screenheight/HEIGHT;
     manager.showSprite = false;
     saying = true;
     ended=false;
     value.say();
     new  spokenWord.whenfree(0) {
         public void action() {
            if(manager != null) {
               manager.showSprite = true;
               saying=false;
               ended=false;
            }
         }
     };
   }
   //---------------------------------------------------------------
   public static void setback(String optname) {
    String s;
    int i,j;
    if((s = options.optionstring(optname)) != null
                                 && (i = s.indexOf('^')) >= 0
           && !(backimage = new sharkImage(s.substring(0,i),s.substring(i+1),false,false)).newf
             && (backcolor = backimage.getcolor(0)) != null) {
           backimage.roundrect = true;
           turnedbackcolor = u.mix(backcolor,Color.black,50,100);
           return;
   }
    else backimage = new sharkImage("cardback_1",false);
    backcolor = backimage.getcolor(0);
    turnedbackcolor = u.mix(backcolor,Color.black,50,100);
    backimage.roundrect = true;
 }
}
