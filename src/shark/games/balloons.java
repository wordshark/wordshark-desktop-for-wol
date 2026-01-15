package shark.games;

import java.awt.*;
import java.awt.event.*;
import shark.*;

public class balloons extends sharkGame {//SS 03/12/04
  short wordtot, curr = -1;
  divword currdiv;
  word words[];
  part1 leftpart, rightpart;
  static final short MAXWORDS = 6;
  static final short MINWORDS = 4;
  static final short MAXSEGSINWORDS = 16;
  static final short BALLOONTIMEMIN = 8000;
  static final short BALLOONTIMEMAX = 16000;
  static final short BALLOONINT = 2000;
  static final short ZAPTIME = 500;
  static final byte balloontype[] = new byte[] {0, 0, 0, 0, 1, 1, 1, 2, 2, 3};
  static final byte balloonscores[] = new byte[] {1, 2, 3, 5};
  final Color ballooncolors[] = new Color[] {Color.blue, Color.magenta, yellow(),
      Color.red};

  long lastballoon;
  int oldsplitx;
  sharkImage balloonim[] = sharkImage.findall("balloon_");
  markword markwords[];
  String seglist[] = new String[0];
  int leftend;
  boolean hadclick;
  boolean wanttest = false;

  public balloons() {
    boolean ok;
    errors = true;
    gamescore1 = true;
//    peeps = true;
//    listen= true;
//    peep = true;
    wantspeed=true;
//    gamePanel.setBackground(u.darkcolor());
    wantSprite = false;
    wordfont = null;
    ok = setupWords();
    help("help_balloons");
    buildTopPanel();
    gamePanel.clearWholeScreen = true;
    clickonrelease=true;
    if(!ok) return;
    if(phonicsw) {
       markwords = new markword[words.length];
       for(int i=0;i<words.length;++i) {
         markwords[i] = new markword(i);
         String ss[] = markwords[i].segs;
          for(int j=0;j<ss.length;++j) {
            if (u.findString(seglist, ss[j]) < 0) {
              seglist = u.addString(seglist, ss[j]);
            }
          }
       }
       u.shuffle(seglist);
       nextsound();
    }
    else {
      if(gamescore2 && curr >= 0) flipstudent(true);
      nextword();
      gamePanel.showSprite = false;
    }
 }
 //-------------------------------------------------------------
 boolean setupWords() {
    short i,j,k;
    int x1=0,y1=0;
    String s;
    word wd[] = sharkStartFrame.mainFrame.wordTree.words;
    short  o[] = u.shuffle(u.select((short)wd.length,(short)wd.length));
    words = new word[Math.min(MAXWORDS, o.length)];
    for(i=0;i<o.length && wordtot<MAXWORDS; ++i) {
       if(wd[o[i]].value.indexOf('/') >= 0) {
                  words[wordtot++] = wd[o[i]];
       }
    }
    if(wordtot < MINWORDS) {
       fatalerror(rgame.getParm("notenough"));
       return false;
    }
    wordfont = sizeFont(null,"wwwwwwwwwww",mover.WIDTH/2,mover.HEIGHT/12);
    return true;
 }
 //-------------------------------------------------------------
 void nextword() {
    if(++curr < wordtot) {
       String model;
       model = words[curr].vsplit();
       currdiv = new divword(model,words[curr].v());
        if(delayedflip && !completed){
          flip();
        }
    }
    else if(!completed) {
       if(gametot1 > 4) {
          score(gametot1/2);
//startPR2006-12-08^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//          showmessage("Score " + String.valueOf(gametot1),mover.WIDTH/6,mover.HEIGHT/16,mover.WIDTH*5/6,mover.HEIGHT/5);
          showmessage(u.edit(rgame.getParm("score"), String.valueOf(gametot1)),mover.WIDTH/6,mover.HEIGHT/16,mover.WIDTH*5/6,mover.HEIGHT/5);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       }
       exitbutton(mover.HEIGHT*4/5);
    }
 }
 //-------------------------------------------------------------
 void nextsound() {
    if(++curr < seglist.length) {
       spokenWord.sayPhonicsBit(seglist[curr],words);
    }
    else if(!completed) {
       if(gametot1 > 4) {
          score(gametot1/2);
          showmessage("Score " + String.valueOf(gametot1),mover.WIDTH/6,mover.HEIGHT/16,mover.WIDTH*5/6,mover.HEIGHT/5);
       }
       exitbutton(mover.HEIGHT*4/5);
    }
 }
 //-----------------------------------------------------------------
 public boolean click(int x, int y){
    if(completed || fatal) return false;
    hadclick = true;
    return true;
 }
 //--------------------------------------------------------------
 public void afterDraw(long t){
    int i;
    Rectangle r;
    if(hadclick) {
      hadclick = false;
      if(leftpart == null && rightpart == null ) {
        if (phonicsw) {
          for (i = 0; i < markwords.length; ++i) {
            if (markwords[i].currseg >= 0) {
              markwords[i].test();
              break;
            }
          }
        }
        else
          if (curr < wordtot
              && currdiv != null && !currdiv.finished
//              && currdiv.fixval != null && currdiv.fixval.indexOf('/') > 0) { 
              && currdiv.val != null && currdiv.val.indexOf('/') > 0) {  
//            currdiv.val = currdiv.fixval;
             wanttest = true;      
          }
      }
    }
    if(leftpart != null) {
       int x1 = leftpart.x*screenwidth/mover.WIDTH;
       int y1 = (leftpart.y+leftpart.h/2)*screenheight/mover.HEIGHT;
       if(leftpart.x > (phonicsw?leftend : currdiv.x)) {
          if(rightpart.gotback)   removeparts();
          else {
             leftpart.gotback = true;
             leftpart.moveto(phonicsw?leftend:currdiv.x, leftpart.y, 100);
          }
       }
       else for(i=0;i<gamePanel.mtot;++i) {
          if(gamePanel.m[i] instanceof balloon) {
             balloon b = (balloon) gamePanel.m[i];
             if(b.popstart > 0) continue;
             r = b.image.getRect(0);
             if(r==null) continue;
             if(y1 > r.y && y1 < r.y+r.height
                  && r.x < screenwidth/2 && x1 < r.x + r.width) {
                b.pop();
                gamescore(b.score);
             }
          }
       }
    }
    if(rightpart != null) {
       int x1 = (rightpart.x+rightpart.w)*screenwidth/mover.WIDTH;
       int y1 = (rightpart.y+rightpart.h/2)*screenheight/mover.HEIGHT;
       if(rightpart.x <oldsplitx) {
          if(leftpart.gotback)  removeparts();
          else {
             rightpart.gotback = true;
             rightpart.moveto(oldsplitx,rightpart.y,100);
          }
       }
       else for(i=0;i<gamePanel.mtot;++i) {
          if(gamePanel.m[i] instanceof balloon) {
             balloon b = (balloon) gamePanel.m[i];
             if(b.popstart > 0) continue;
             r = b.image.getRect(0);
             if(r==null) continue;
             if(y1 > r.y && y1 < r.y+r.height
                 && r.x > screenwidth/2 && x1 > r.x) {
                b.pop();
                gamescore(b.score);
            }
          }
       }
    }
    if(t - lastballoon > BALLOONINT) {
       for(i=0;i<gamePanel.mtot;++i) {
          if(gamePanel.m[i] instanceof balloon) {
             balloon b = (balloon) gamePanel.m[i];
             if(b.y < -b.h || b.popstart>0 && t>b.popstart+400) {
                 removeMover(b);
             }
          }
       }
       if(curr<wordtot) new balloon();
       lastballoon = t;
    }
 }
 //--------------------------------------------------------------
 void removeparts() {
    removeMover(leftpart);
    removeMover(rightpart);
    leftpart = rightpart = null;
    if(phonicsw || currdiv.complete()) {
       javax.swing.Timer tt = new javax.swing.Timer(600,
          new ActionListener() {
             public void actionPerformed(ActionEvent e){
               if(phonicsw) nextsound();
               else {
                 removeMover(currdiv);
                 nextword();
               }
             }
          });
       tt.setRepeats(false);
       tt.start();
    }
//startPR2005-04-18^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    if(Demo_base.isDemo){
      if (Demo_base.demoIsReadyForExit(0)) return;
    }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
 }
 //-------------------------------------------------------------
 class divword extends mover {
    String val,currval,model,fixval;
    short modellen,splitafter;
    boolean finished;
    int lastpos;
    long lasttime;
    divword(String withsplits, String withoutsplits) {
       super(false);
       val = currval = withoutsplits;
       model = withsplits;
       modellen = (short)model.length();
       w = metrics.stringWidth(model)*mover.WIDTH/screenwidth;
       h = metrics.getHeight()*mover.HEIGHT/screenheight;
       x = mover.WIDTH/2 - w/2;
       addMover(this,x,Math.max(0,gamePanel.mousey - h*3/2));
    }
    boolean test() {
       short len = (short)val.length(),nextvalid=0;
       int i,j;
       char c1,c2=' ';
//       for(i=j=0;i<len;++i,++j) {                     // start rb 15/1/08
//          c1 = val.charAt(i);
//          c2 = model.charAt(j);
//          if(c1 == '/') {
//             if(c2 != '/' && c2 != '_' || j < nextvalid) {
//               if(gtime > gamePanel.mousepressedat + 500) return false;  //ignore error if caused by release after drag
//                error();
//                noise.groan();
//                return false;
//             }
//             else {
//                boolean ready = false;
//                for (nextvalid=(short)(j+1); nextvalid<modellen; ++nextvalid) {
//                   c2 = model.charAt(nextvalid);
//                   if(u.vowelsy.indexOf(c2) >= 0) {
//                      ready=true;
//                   }
//                   else if (c2 == '/' || ready && c2 == '_') break;
//                }
//             }
//          }
//          else if(c2 == '/' || c2 == '_') ++j;
//       }
       
       for(i=j=0;i<splitafter;++i,++j) {
         c2 = model.charAt(j);
         if (c2 == '/' && val.charAt(i) != '/') ++j;
       }
       c2 = model.charAt(j);
       if(c2 != '/' || i<val.length()-1 && val.charAt(i+1)=='/') {
               if(gtime > gamePanel.mousepressedat + 500) return false;  //ignore error if caused by release after drag
                error();
                noise.groan();
                return false;
       }                                    // end    rb 15/1/08

       String s;
       leftend = x;
       leftpart = new part1(currval.substring(0,splitafter));
       addMover(leftpart,x-1,y);
       leftpart.moveto(x-mover.WIDTH/2,y,ZAPTIME);
       rightpart = new part1(s=currval.substring(splitafter));
       addMover(rightpart,oldsplitx = x+metrics.stringWidth(s)*mover.WIDTH/screenwidth+1,y);
       rightpart.moveto(x+mover.WIDTH/2,y,ZAPTIME);
       currval = val;
       return true;
    }
    boolean complete() {
       if(!val.equals(model)) return false;
       finished=true;
       return true;
     }
      
    
     public void paint(Graphics g,int x1, int y1, int w1, int h1) {
        int i,j, dy = metrics.getMaxAscent();
        if(leftpart != null || rightpart != null) return;
        g.setFont(wordfont);
        if(!mouseOver) y = Math.max(0,gamePanel.mousey - h*3/2);
        y1 = y*screenheight/mover.HEIGHT;
        if(!finished) {
           g.setColor(Color.black);
           for(i=1,j=x1;i<currval.length()-1;++i) {
              j += metrics.charWidth(currval.charAt(i));
              if(gamePanel.mousexs < j+ metrics.charWidth(currval.charAt(i+1)))  break;
           }
           if(i == lastpos) {
//             if(gtime > lasttime + 500) {
             if(gtime > lasttime) {  // problems?
 //              fixval = currval.substring(0, i) + "/" + currval.substring(i);
           //    splitafter = (short)i;
             }
           }
           else   {
              lasttime = gtime;
              lastpos = i;
           }
           splitafter = (short)i;
           val = currval.substring(0,i) + "/" + currval.substring(i);
           
           if(wanttest){
               wanttest = false;
               test();     
           }
           
//startPR2004-09-15^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
              boolean isSpecialVowel[]= new boolean[val.length()];
              isSpecialVowel = u.findSpecialVowels(val);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR

           int splitafter2 = (short)i;
           if((rgame.options & word.VOWELS) != 0) {
              for(i=0;i<val.length();++i) {
                 if(i==splitafter2) g.setColor(Color.white);
                 else if(i==0 && val.charAt(i)=='y'
                         || u.vowelsy.indexOf(val.charAt(i)) < 0 )
                            g.setColor(Color.black);
                 else g.setColor(Color.red);
//startPR2004-09-15^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                      if(isSpecialVowel[i])
                        g.setColor(Color.red);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                 g.drawString(val.substring(i,i+1),x1,y1+dy);
                 x1 += metrics.charWidth(val.charAt(i));
              }
           }
           else {
              g.setColor(Color.black);
              g.drawString(val, x1, y1+dy);
              g.setColor(white());
              g.drawString("/",x1+metrics.stringWidth(val.substring(0,splitafter2)),y1+dy);
           }
        }
        else {
           g.setColor(white());
           g.drawString(val, x1, y1+dy);
        }
     }
  }
  //--------------------------------------------------------------------------------
                 // phonics mover
  class markword extends mover {
     String val;
     word wd;
     String segs[];
     int currseg;
     markword(int pos) {
        super(false);
        wd = words[pos];
        segs= wd.segments();
        val = wd.v();
        w = metrics.stringWidth(val)*mover.WIDTH/screenwidth;
        h = metrics.getHeight()*mover.HEIGHT/screenheight;
        x = mover.WIDTH/2 - w/2;
        addMover(this,x,mover.HEIGHT/2 - h*words.length/2 + pos*h);
     }
     boolean test() {
        short len = (short)val.length(),nextvalid=0;
        int i,j;
        char c1,c2;
        if(currseg < 0) return false;
        if(segs[currseg].equals(seglist[curr])) {
          int splitat = Math.max(1,Math.min(segs.length-1,currseg));
          String s1="",s2="";
          for(i=0;i<splitat;++i) s1 += segs[i];
          for(;i<segs.length;++i) s2 += segs[i];
          leftpart = new part1(s1);
          leftend = x;
          addMover(leftpart, x - 1, y);
          rightpart = new part1(s2);
          addMover(rightpart,(oldsplitx = x +  metrics.stringWidth(s2) * mover.WIDTH / screenwidth) + 1, y);
          leftpart.moveto(x - mover.WIDTH / 2, y, ZAPTIME);
          rightpart.moveto(x + mover.WIDTH / 2, y, ZAPTIME);
          return true;
         }
         else {
           spokenWord.sayPhonicsBit(segs[currseg],words);
           return false;
         }
     }
      public void paint(Graphics g,int x1, int y1, int w1, int h1) {
         int i,j, dy = metrics.getMaxAscent();
         if(leftpart != null || rightpart != null) return;
         g.setFont(wordfont);
         if(!mouseOver) {
            g.setColor(Color.black);
            g.drawString(val, x1, y1+dy);
            currseg = -1;
         }
         else {
            int xx = x1;
            for(i=0;i<segs.length;++i) {
               j = metrics.stringWidth(segs[i]);
               if (gamePanel.mousexs > xx && gamePanel.mousexs <= xx + j) {
                 g.setColor(white());
                 currseg = i;
               }
               else g.setColor(Color.black);
               g.drawString(segs[i],xx,y1+dy);
               xx += j;
            }
         }
      }
   }
  //---------------------------------------------------------------
  class part1 extends mover {
     String val;
     boolean gotback;
     part1(String val1) {
        super(false);
        val = val1;
        bouncer = true;
        w = metrics.stringWidth(val)*mover.WIDTH/screenwidth;
        h = metrics.getHeight()*mover.HEIGHT/screenheight;
     }
     public void paint(Graphics g,int x1, int y1, int w1, int h1) {
        g.setFont(wordfont);
        g.setColor(Color.black);
        g.drawString(val, x1, y1+metrics.getMaxAscent());
     }
  }
  //-----------------------------------------------------------------
  class balloon extends mover {
     byte type = balloontype[u.rand(balloontype.length)];
     byte score = balloonscores[type];
     String chscore = u.numbers.substring(score,score+1);
     Color color = ballooncolors[type];
     sharkImage image;
     long popstart;

     balloon() {
        super(false);
        int i,j,xx=0,dx;
        sharkImage im1 = balloonim[u.rand(balloonim.length)];
        image = new sharkImage(im1.database,im1.name,false,false);
        w = mover.WIDTH/12 + u.rand(mover.WIDTH/12);
        h = mover.HEIGHT/3;
        image.adjustSize(screenwidth,screenheight);
        image.recolor(Color.black,color);
        x = ((u.rand(2) > 0)?(mover.WIDTH*3/4):0) + u.rand(mover.WIDTH/4-w);
        addMover(this,x, mover.HEIGHT);
        moveto(x,-h,(BALLOONTIMEMIN + u.rand(BALLOONTIMEMAX-BALLOONTIMEMIN))*5/speed);
     }
     void pop() {
        image.setControl("pop");
        popstart = gtime;
        noise.pop();
     }
     public void paint(Graphics g,int x1, int y1, int w1, int h1) {
        int i,j;
        if(metrics == null) return;
        image.paint(g,x1,y1,w1,h1);
        g.setFont(wordfont);
        Rectangle r = image.getRect(0);

        g.setColor(popstart>0?white():Color.black);
        g.drawString(chscore,r.x+r.width/2-metrics.stringWidth(chscore)/2,
                  r.y+r.height/2-metrics.getHeight()/2+metrics.getMaxAscent());
     }
  }
}
