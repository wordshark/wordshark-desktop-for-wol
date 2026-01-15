
package shark.games;

import java.util.*;

import java.awt.*;

import shark.*;
import shark.sharkGame.*;
import shark.spokenWord.*;

public class moving extends sharkGame {//SS 03/12/05
  moveButton b[];
  short btot,curr;
  int wordWidth,wordHeight,buttonHeight;
  Random rr = new Random();
  boolean doneall;
  int dir;     // 1 - random, 2,3 - horx,  4,5 - vert
  boolean showpicture = !specialprivateon;
  Color stubgcolor;

  public moving() {
    String sbg = student.optionstring("bgcolor");
    if(sbg != null) {
        stubgcolor = new Color(Integer.parseInt(sbg));
        makeBackground(true);
    }
    errors = true;
    gamescore1 = true;
    peeps = true;
    listen= true;
    peep = true;
    wantspeed = true;
    clickenddrag=false;
    mouseDownDelay = false;
    help(phonics && !phonicsw?"help_movingph3":(specialprivateon?"help_movingdef":"help_moving"));
    rgame.options |= word.CENTRE;
    optionlist = new String[] {"moving_dir"};
    dir = student.optionval("moving_dir");
    btot = (short)rgame.w.length;
    b = new moveButton[btot*2];
//    clickonrelease = true;
    buildTopPanel();
    setupButtons();
    gamePanel.clearWholeScreen = true;
    currword =  rgame.w[0];
    new  spokenWord.whenfree(600) {
         public void action() {
             if(phonics && !phonicsw) {
               spokenWord.sayPhonicsWord(currword,500,false,false,!singlesound);
             }
             else {
               rgame.w[0].say();
               if(showpicture)
                new showpicture();
             }
         }
    };
    markoption();
    bringCurrToTop();
  }
  //--------------------------------------------------------------


 public void restart() {
   dir = student.optionval("moving_dir");
   for(short i=0;i<btot;++i) {
     b[i].bouncer = (dir<=1);
     addMover(b[i], Math.max(0,Math.min(mover.WIDTH-b[i].w,b[i].x)),   Math.max(0,Math.min(mover.HEIGHT-b[i].h,b[i].y)));
     b[i].setmoving();
  }
  if(phonics && !phonicsw) {
    spokenWord.sayPhonicsWord(currword, 500, false, false, !singlesound);
  }
  else {
    rgame.w[curr].say();
    if(showpicture)
       new showpicture();
  }
   markoption();
   bringCurrToTop();
 }
 
 void bringCurrToTop(){
    for(short i=0;i<b.length;++i) {
        if(b[i]==null)continue;
        if(isWordCorrect(b[i].wordnumber))
            gamePanel.bringtotop(b[i]);    
    }
 }
  
 boolean isWordCorrect(int wordnumber){
     try{
        return (currword.v().equals(rgame.w[wordnumber].v()) ||
               phonics && !singlesound && (!currword.homophone || currword.nohomo()) && currword.samephonics(rgame.w[wordnumber]));
     }
     catch(Exception e){return false;}
 }  
 
 //----------------------------------------------------------
  void setupButtons() {
    int maxwidth = mover.WIDTH/8;
    short order[] = u.shuffle(u.select(btot,btot));
    sizeFont();

    for(short i=0;i<btot;++i) {
       b[i] = new moveButton(order[i], gamePanel,-1,-1);
    }
  }
  //-------------------------------------------------------------
  public void sizeFont() {
      wordfont = null;
      String s;
      int maxwidth = sharkStartFrame.currPlayTopic.phrases ? mover.WIDTH/2:mover.WIDTH/6;
      int maxheight = mover.HEIGHT/8;

      for(short i=0;i<btot;++i){
         s = rgame.w[i].v();
         wordfont = sizeFont(wordfont,s, maxwidth, maxheight);
      }
      wordHeight = metrics.getHeight();
      buttonHeight = Math.max(mover.HEIGHT/10,wordHeight * mover.HEIGHT/screenheight * 2);
  }
  //-------------------------------------------------------------
   class moveButton extends mover {
      public int wordnumber;
      int cr,cg,cb;
      Color color;
      public moveButton(short wordnum1, runMovers p,int xx, int yy) {
         super(false);
         wordnumber = wordnum1;
         h = buttonHeight;
         String s = rgame.w[wordnumber].v();
         FontMetrics m = getFontMetrics(wordfont);
         w = m.stringWidth(s+"    ")*mover.WIDTH/screenwidth;
         if(stubgcolor==null) color = new Color(cr=(int)u.rand((short)256),cg=(int)u.rand((short)256),cb=(int)u.rand((short)256));
         else color = stubgcolor;
         im = p.createImage(imwidth=w*screenwidth/mover.WIDTH,
                            imheight=h*screenheight/mover.HEIGHT);

         Graphics g = im.getGraphics();
//startPR2007-10-19^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         g.setColor(color);
         g.fillRect(0 ,0, imwidth, imheight);
         g.setFont(wordfont);
         Rectangle r = new Rectangle(0,0,imwidth,imheight);
         u.buttonBorder(g,r,color,!mouseDown);
         if(stubgcolor==null) 
            g.setColor((cr+cg+cb>128*3)?Color.black:Color.white);
         else 
            g.setColor(Color.black);
         rgame.w[wordnumber].paint(g,r,(byte)word.CENTRE);
         bouncer = (dir<=1);
         if(xx<0)
            p.addMover(this,u.rand(mover.WIDTH - w),u.rand(mover.HEIGHT - h));
         else  p.addMover(this, Math.min(xx, mover.WIDTH-w), Math.min(mover.HEIGHT-h,yy));
         setmoving();
       }
       //---------------------------------------------------------
       public boolean endmove()   {
         int i;
         if(dir == 2 || dir == 3) {
            x=tox = -w;
            for(i=0;i<b.length;++i) {
               if(b[i] !=null && b[i] != this && b[i].x < 0 && b[i].y < y+h && b[i].y+b[i].h > y)  {
                  if(b[i].y > h && (u.rand(2)==1 || b[i].y+b[i].h > mover.HEIGHT-h)) y = toy = b[i].y - h;
                  else y=toy=b[i].y+b[i].h;
               }
            }
            setmoving();
          }
          else if(dir == 4 || dir == 5) {
             y=toy = -h;
             for(i=0;i<b.length;++i) {
                if(b[i] != null && b[i] != this && b[i].y < 0 && b[i].x < x+w && b[i].x+b[i].w > x)  {
                   if(b[i].x > w && (u.rand(2)==1 || b[i].x + b[i].w > mover.WIDTH-w)) x = tox = b[i].x - w;
                   else x=tox=b[i].x+b[i].w;
                }
             }
             setmoving();
          }
         return false;
       }
       //-----------------------------------------------------------
       void setmoving() {
         if(dir==2) moveto(mover.WIDTH,  y, speed1(mover.WIDTH-x));
         else if(dir==3) moveto(mover.WIDTH,  y, speed2(mover.WIDTH-x));
         else if(dir==4) moveto(x,mover.HEIGHT, speed1(mover.HEIGHT - y));
         else if(dir==5) moveto(x,mover.HEIGHT, speed2(mover.HEIGHT - y));
         else  {
            double a = (rr.nextFloat() * 2 * Math.PI);
            float rad = (mover.WIDTH + mover.HEIGHT);
            moveto(x + (int) (rad * Math.cos(a)),y + (int) (rad * Math.sin(a)), getspeed());
         }
       }
       //-----------------------------------------------------------------------------------------
       public void mouseClicked(int x, int y) {
       short i ;
//        if(currword.v().equals(rgame.w[wordnumber].v()) ||
//           phonics && !singlesound && (!currword.homophone || currword.nohomo()) && currword.samephonics(rgame.w[wordnumber])) {
       if(isWordCorrect(wordnumber)){
          for(i=0; i < btot; ++i)
            if(b[i]==this)
              break;
          System.arraycopy(b,i+1,b,i,b.length-1-i);
          --btot;
          spokenWord.flushspeaker(true);
          zapp(this, x, y, false);
          u.pause(1000);
//startPR2005-04-18^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            if(Demo_base.isDemo){
              if (Demo_base.demoIsReadyForExit(0)) return;
            }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          gamescore(1);
          if(btot == 0) {
             score(gametot1);
             exit(1000);
          }
          else {
                if(delayedflip && !completed){
                  flip();
                }

             if(doneall || ++curr >= rgame.w.length) {
               doneall = true;
               curr = (short)b[u.rand(btot)].wordnumber;
             }
             currword =  rgame.w[curr];
             if(phonics && !phonicsw) {
               spokenWord.sayPhonicsWord(currword, 500, false, false, !singlesound);
             }
             else {
               rgame.w[curr].say();
               if(showpicture)
                   new showpicture();
             }
          }
        }
        else {
           error(currword.v());
           gamescore(-1);
           noise.groan();
           if(b.length > btot) {
             gamescore( -1);
              b[btot++] = new moveButton((short)wordnumber,gamePanel,x,y);
           }
        }
        bringCurrToTop();
     }
  }
  //--------------------------------------------------------------------
  public void listen1() {
    if(phonics && !phonicsw && currword != null) {
      spokenWord.sayPhonicsWord(currword, 200, false,true,!singlesound);
      gamePanel.startrunning();
      gamePanel.requestFocus();
    }
    else super.listen1();
  }
  //------------------------------------------------------
  public void badclick(int xx, int yy) {
     noise.groan();
     gamescore(-1);
     if(b.length > btot) {
         gamescore(-1);
         b[btot++] = new moveButton(curr,gamePanel, xx*mover.WIDTH/screenwidth,yy*mover.HEIGHT/screenheight);
     }
  }
  public void newspeed() {
//     for (short i = 0; i<btot; ++i) {
//        b[i].ti = getspeed();
//     }
//     restart(false);
     for(short i=0;i<btot;++i)
        b[i].setmoving();
  }
  int getspeed() {
    return (5000 + u.rand((short)8000)) * 32 / (1+8*speed);
  }
  int speed1(int dist) {
    return dist * 600 *(12-speed) / mover.WIDTH;
  }
  int speed2(int dist) {
    return dist * (400 + u.rand(400)) *(12-speed) / mover.WIDTH;
  }
}
