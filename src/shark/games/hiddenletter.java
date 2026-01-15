package shark.games;

import java.awt.*;

import shark.*;
import shark.sharkGame.*;

public class hiddenletter extends sharkGame {
  hidden hiddench;
  short wordtot;
  boolean split;
  String words[];
  Color startcolor[];
  letter letters[];
  int letterwidth,letterheight;
  short lettertot;
  movingchar carry;
  slidingchoice sliding;
  Font slidingf;
  FontMetrics slidingm;
  int slidingw;
  Color slidingcol=Color.black,slidingcol2=Color.red;
  short currpos;
  short currstart=-1;
  Font hiddenfont;
  boolean ending,hadtry;
  short badletter;
  int minx;
  String lastletters;
  int maxw;
  boolean showpicture = !specialprivateon;

  public hiddenletter() {
    errors = true;
    peeps = true;
    wantspeed = false;
    listen= true;
    peep = true;
    clickonrelease = true;
    pictureat = 3;
    split = ((rgame.options & word.SPLIT) != 0);
    help((specialprivateon?"help_hidden1def":"help_hidden1"));
    gamePanel.clearWholeScreen=true;
    setupWords();
    buildTopPanel();
    gamePanel.detectEnterSpace = true;
 }
 //-------------------------------------------------------------
 void removeletters(short lno) {
 boolean left =  (u.rand(2) != 0);
 for(short i=lno;i<lno +letters[lno].wordlen;++i) {
    letters[i].mustSave = true;
    letters[i].temp = true;
    letters[i].moveto(left? (letters[i].x-mover.WIDTH): (letters[i].x + mover.WIDTH),
                           letters[i].y,3000);
    letters[i].dead = true;
 }
 currword=null;
 gamePanel.clearall();
 if(!ending) keypad.activate(this,null);
 }
 //-------------------------------------------------------------
 boolean anyleft() {
    letter l;
    short i,j;
    for(i = (short)(gamePanel.mtot-1); i>=0; --i) {
        if(gamePanel.m[i] instanceof letter
                && !(l = (letter)gamePanel.m[i]).dead) return true;
    }
    return false;
 }
 //-------------------------------------------------------------
 boolean removetopmost() {
    letter l;
    short i,j;
    for(i = (short)(gamePanel.mtot-1); i>=0; --i) {
        if(gamePanel.m[i] instanceof letter
                && !(l = (letter)gamePanel.m[i]).dead) {
           for(j = l.thisnum; j>=0 && letters[j].wordno==l.wordno;--j);
           removeletters((short)(j+1));
           return true;
        }
    }
    return false;
 }
 //-------------------------------------------------------------
 void setupWords() {
    short i,j,k,ii;
    String s;
    short maxlen = 0;
    minx = mover.WIDTH;
    wordtot = (short)rgame.w.length;
    words = new String[wordtot];
    startcolor = new Color[wordtot];
    short down =  (short)Math.max(1,Math.min(wordtot/2, 8));
    short across = (short)Math.max((wordtot+down-1)/down, 3);
    int width;
    int x,y;
    short repeats,repeats2;

    char  hiddenc = u.lowercase.charAt(u.rand(u.lowercase.length()));
    hiddenfont = bigcharFont(hiddenc,screenwidth/2,screenheight/2);
    FontMetrics m = getFontMetrics(hiddenfont);
    addMover(hiddench = new hidden(hiddenc,m.charWidth('m')*mover.WIDTH/screenwidth,m.getHeight()*mover.HEIGHT/screenheight),
                 mover.WIDTH/2 - mover.WIDTH*m.charWidth(hiddenc)/screenwidth/2,
                 mover.HEIGHT/2 - mover.HEIGHT*m.getHeight()/screenheight/2);


    for(i=0;i<wordtot;++i) {
       words[i] = rgame.w[i].v();
       startcolor[i] = u.darkcolor();
       maxlen = (short)Math.max(maxlen, words[i].length());
       lettertot +=  words[i].length();
    }
    letters = new letter[lettertot];
    wordfont = bigcharFont('m',
            screenwidth*37/40/maxlen,
//            Math.min(screenwidth/2,hiddench.w*screenwidth/mover.WIDTH/maxlen*3/2),
            hiddench.h*screenheight/mover.HEIGHT/Math.max(1,Math.min(4,wordtot-1)));
    m = getFontMetrics(wordfont);
    letterwidth = m.charWidth('m')*mover.WIDTH/screenwidth;
    letterheight = (m.getHeight()+4)*mover.HEIGHT/screenheight;

    int yy[] = new int[wordtot];
    short repeats3=0;
    loop1:while(++repeats3<1000) {
       yy[0] = hiddench.y;
       if (wordtot>1) yy[1] = hiddench.y+hiddench.h-letterheight;
       for(i=2;i<wordtot;++i) {
          yy[i] = hiddench.y + u.rand(Math.max(1,hiddench.h-letterheight));
       }
       java.util.Arrays.sort(yy);
       for(i=1;i<wordtot;++i) {
          if(yy[i] > yy[i-1]+letterheight) continue loop1;
       }
       break;
    }
    if(repeats3>=1000) {
       for(i=0;i<wordtot;++i) {
          yy[i] = hiddench.y + hiddench.h/wordtot*i;
       }

    }
    short o[] = u.shuffle(u.select(wordtot,wordtot));
    u.shuffle(yy);
    for(ii=0,k=0;ii<wordtot;++ii) {
       i = o[ii];
       width = words[i].length() * (letterwidth);
       if(width>hiddench.w)
          x = hiddench.x - u.rand(width-hiddench.w);
       else x = hiddench.x + u.rand(Math.max(1,hiddench.w - width));
       x = Math.max(mover.WIDTH/80,Math.min(mover.WIDTH-width-mover.WIDTH/16,x));
       minx = Math.min(x,minx);
       y = yy[ii];
       for(j=0;j<words[i].length(); ++j) {
           addMover(letters[k] = new letter(words[i].charAt(j),i,k), x,y);
           ++k;
           x+=letterwidth;
       }
    }
 }
 //---------------------------------------------------------------
 public boolean click(int x1,  int y1)  {
   short i,j;
   String s;
   if(ending) return true;
   if(sliding!=null){
    sliding.okToClick = false;
    while(!sliding.okToClick){
        u.pause(10);
    }
   }
   if(carry != null) {
      int x = carry.x, y = carry.y;
      for(i = currstart;
           i<currstart+letters[currstart].wordlen;
           ++i) {
         if(!letters[i].gotletter
             && Math.abs(letters[i].x - x) < letterwidth/3
             && Math.abs(letters[i].y - y) < letterheight/3 ) {
             if(letters[i].ch == carry.ch) {
                letters[i].gotletter = true;
                letters[i].ended = false;
                gamePanel.finishwith(carry);
                carry = null;
                nextletter();
             }
             else  {
                error(currword.v());
                noise.groan();
             }
             return true;
         }
      }
   }
   else if(sliding != null) {
      char ch;
      if((ch=sliding.value) != 0) {
         if(ch == letters[i=currpos].ch) {
             letters[i].gotletter = true;
             letters[i].ended = false;
             removeMover(sliding);
             sliding = null;
             u.pause(1000);
//startPR2005-04-18^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
             if(Demo_base.isDemo){
               if (Demo_base.demoIsReadyForExit(0)) return true;
             }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
             removeletters(currstart);
             flip();
             currstart = -1;
             gamePanel.clearall();
             if(anyleft()) help((specialprivateon?"help_hidden1def":"help_hidden1"));
             else help("help_hidden4");
             hadtry = false;
          }
          else {
             error(currword.v());
             noise.groan();
          }
     }
      else noise.beep();
   }
   else {            // clicking to select
      for(i=0;i<lettertot;++i) {
         if(!letters[i].dead && !letters[i].gotletter && letters[i].mouseOver) {
            for(j=(short)(i-1);j>=0 && letters[j].wordno == letters[i].wordno;--j);
            currstart = (short)(j+1);
            for(j=currstart;j<currstart+letters[currstart].wordlen;++j)
                gamePanel.bringtotop(letters[j]);
            gamePanel.clearall();
             (currword = rgame.w[letters[i].wordno]).say();
            help("help_hidden2");
            keypad.deactivate(this);
            if(showpicture)
                new showpicture();
            nextletter();
            return true;
         }
      }
      noise.beep();
   }
   return true;
 }
 //---------------------------------------------------------------
 public void keyhit(char key) {
    short i;
    short score1;
    if(u.lowercase.indexOf(key) < 0 || ending ) return;
    keypad.deactivate(this);
    if(sliding != null || carry != null || hadtry) {noise.beep(); return;}
    if(key == hiddench.ch) {
      String message;
      ending = true;
      while(removetopmost()) u.pause(500);
      u.pause(1000);
      switch(badletter) {
         case 0:
            score1 = (short)Math.max(2,10-errortot);
            message = rgame.getParm("firsttime")+" "+String.valueOf(score1);
            score(score1);
            break;
         case 1:
            score1 = (short)Math.max(1,5-errortot);
            message = rgame.getParm("secondtime")+" "+String.valueOf(score1);
            score(score1);
            break;
         default:
            score1 = (short)Math.max(0,2-errortot);
            message = rgame.getParm("thirdtime")+" "+String.valueOf(score1);
            score(score1);
            break;
      }
      showmessage(message,0,mover.WIDTH*7/8, mover.WIDTH,mover.HEIGHT);
      exitbutton(mover.HEIGHT/16);
    }
    else {
      if(anyleft())hadtry = true;
      ++badletter;
      noise.groan();
      flash(u.splitString(u.edit(rgame.getParm("badletter"), String.valueOf(key))), 3000);
    }
 }
 //--------------------------------------------------------------
 boolean nextletter() {
    String s = new String(),s2;
    short i,j,tot=0;
    char ch;
    short len = letters[currstart].wordlen;
    short o[] = u.shuffle(u.select(len,len));
    short oktot=0,ok=-1;

    for(i = 0; i < len; ++i) {
       if(!letters[currstart+i].gotletter && letters[currstart+i].ch != ' ') {
          ++tot;
          if(u.letters.indexOf(letters[currstart+i].ch) >= 0)
                                   {++oktot;ok = (short)(currstart+i);}
       }
    }

    if(tot==0) {
        u.pause(1000);
        removeletters(currstart);
        currstart = -1;
        gamePanel.clearall();
        if(anyleft()) help((specialprivateon?"help_hidden1def":"help_hidden1"));
        else help("help_hidden4");
        hadtry = false;
        gamePanel.showSprite = true;
        return false;   // end of word
    }
    for(i = 0; i < len; ++i) {
       if(!letters[currstart+o[i]].gotletter  && letters[currstart+o[i]].ch != ' ') {
         currpos = (short)(currstart+o[i]);
         if(oktot<=1 && ok==currpos && tot>1) continue; // dont use up the only letter
         if(tot>1 || tot==1 && u.letters.indexOf(letters[currpos].ch) < 0) {
            addMover(carry = new movingchar(letters[currpos].ch),0,0);
            gamePanel.showSprite = false;
            gamePanel.mousemoved((minx - carry.w)*screenwidth/mover.WIDTH, (letters[currpos].y+letters[currpos].h/2)*screenheight/mover.HEIGHT);
         }
         else {
             if(u.uppercase.indexOf(letters[currpos].ch) >= 0)
               sliding = new  slidingchoice(u.uppercase);
             else  sliding = new  slidingchoice(u.lowercase);
             gamePanel.mousemoved(0,0);
             help("help_hidden3");
             gamePanel.showSprite = true;
         }
         return true;
       }
    }
    return false; // end of word
 }
 //--------------------------------------------------------------
 class hidden extends mover {
    char ch;
    public hidden(char ch1,int w1, int h1) {
       super(false);
       ch = ch1;
       w = w1;
       h = h1;
    }
     public void paint(Graphics g,int x, int y, int w, int h) {
         if(shark.macOS) ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
       Font f = g.getFont();
       Color c = g.getColor();
       g.setFont(hiddenfont);
       FontMetrics m = g.getFontMetrics();
       g.setColor(Color.black);
       y += Math.max(0,h/2 - m.getHeight()/2 + m.getMaxAscent());
       g.drawString(String.valueOf(ch), x + w/2 - m.charWidth(ch)/2,y);
       g.setFont(f);
       g.setColor(c);
     }
 }
 //--------------------------------------------------------------
 class letter extends mover {
    char ch;
    short wordno,wordlen,thisnum;
    boolean gotletter,dead;

    public letter(char ch1,short wno,short currnum) {
       super(false);
       wordno = wno;
       ch = ch1;
       thisnum = currnum;
       w = letterwidth;
       h = letterheight;
       wordlen = (short)words[wno].length();
    }
    public void paint(Graphics g,int x, int y, int w, int h) {
        if(shark.macOS) ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
       Font f = g.getFont();
       Color c = g.getColor();
       short i;

       if(currstart!=-1 && wordno==letters[currstart].wordno || dead) {
          g.setColor(white());
          g.fillRect(x,y,w,h);
          g.setColor(Color.lightGray);
          g.drawRect(x,y,w,h);
       }
       else {
          g.setColor(startcolor[wordno]);
          g.fillRect(x,y,w,h);
          g.setColor(white());
          g.drawRect(x,y,w,h);
          if(thisnum > 0 && wordno == letters[thisnum-1].wordno)
             g.drawLine(x,y,x,y+h);
          if(thisnum < lettertot-1 && wordno == letters[thisnum+1].wordno)
             g.drawLine(x+w,y,x+w,y+h-1);
       }
       if(gotletter || dead) {
          g.setFont(wordfont);
          FontMetrics m = g.getFontMetrics();
          g.setColor(Color.black);
          g.drawString(String.valueOf(ch), x + w/2 - m.charWidth(ch)/2,
                     y + h/2 - m.getHeight()/2 + m.getMaxAscent());
       }
       else if(currstart!=-1 && wordno==letters[currstart].wordno && sliding != null && sliding.value!=0
                    && ch != ' ') {
         g.setFont(wordfont);
         FontMetrics m = g.getFontMetrics();
         g.setColor(Color.black);
         char ch2 = sliding.value;
         g.drawString(String.valueOf(ch2), x + w/2 - m.charWidth(ch2)/2,
                    y + h/2 - m.getHeight()/2 + m.getMaxAscent());
       }
       g.setFont(f);
       g.setColor(c);
    }
  }
  //-------------------------------------------------------------
  class movingchar extends mover {
     char ch;

     public movingchar(char c) {
        super(true);
        FontMetrics m = getFontMetrics(wordfont);
        ch=c;
        mx = -(letterwidth/2);
        my = -letterheight/2;
        w = letterwidth;
        h = letterheight;
        gamePanel.moveWithMouse(this);
     }
     public void paint(Graphics g,int x, int y, int w, int h) {
         if(shark.macOS) ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
       g.setColor(Color.black);
       if(ch == ' ') g.drawRect(x,y,w,h);
       else {
          g.setFont(wordfont);
          FontMetrics m = g.getFontMetrics();
          x += letterwidth * screenwidth/mover.WIDTH/2;
          y += Math.max(0, h/2 - m.getHeight()/2 + m.getMaxAscent());
          g.drawString(String.valueOf(ch), x - m.charWidth(ch)/2,y);
       }
     }
   }
   class slidingchoice extends mover {
     char value = 0;
     String letterstring1;
     char lets[];
     char maxch;
     public boolean okToClick;
     
     slidingchoice(String letterstring) {
       letterstring1 = letterstring;
       lets = letterstring.toCharArray();
       if(slidingf == null || screenwidth != slidingw || !letterstring.equals(lastletters)) {
         lastletters = letterstring;
         slidingf = sizeFont(new String[] {letterstring1}
                             , screenwidth, letters[currpos].h*screenheight/mover.HEIGHT);
         slidingm = getFontMetrics(slidingf);
         maxw=0;
         for(int i = 0;i<lets.length;++i) {
           if(slidingm.charWidth(lets[i]) > maxw) {
             maxw = slidingm.charWidth(lets[i]);
             maxch = lets[i];
           }
         }
         slidingf = sizeFont(new String[] {new String(new char[]{maxch})}
                             , screenwidth/lets.length, letters[currpos].h*screenheight/mover.HEIGHT);
         slidingm = getFontMetrics(slidingf);
         maxw = slidingm.charWidth(maxch);
         slidingcol = white();
         slidingw=screenwidth;
       }
       mouseSensitive=true;
       noSpriteIfOver = true;
       w = mover.WIDTH;
       h = Math.min(letters[currpos].h, slidingm.getHeight()*2*mover.HEIGHT/screenheight);
       addMover(this,0,letters[currpos].y+h);
     }
     public void paint(Graphics g, int x, int y, int w, int h) {
       int i,xx,yy;
       if(slidingf == null || screenwidth != slidingw) {
         slidingf = sizeFont(new String[] {letterstring1}
                             , screenwidth, letters[currpos].h*screenheight/mover.HEIGHT);
         slidingm = getFontMetrics(slidingf);
         maxw=0;
         for(i = 0;i<lets.length;++i) {
           if(slidingm.charWidth(lets[i]) > maxw) {
             maxw = slidingm.charWidth(lets[i]);
             maxch = lets[i];
           }
         }
         slidingf = sizeFont(new String[] {new String(new char[]{maxch})}
                             , screenwidth/lets.length, letters[currpos].h*screenheight/mover.HEIGHT);
         slidingm = getFontMetrics(slidingf);
         maxw = slidingm.charWidth(maxch);
         slidingcol = white();
         slidingw=screenwidth;
       }
       g.setFont(slidingf);
       xx=x+w/2-lets.length*maxw/2;
       g.setColor(Color.black);
       g.fillRect(xx,y,maxw*lets.length,h);
       yy = y+h/2-slidingm.getHeight()/2+slidingm.getAscent();
       g.setColor(Color.white);
       boolean got=false;
       for(i=0;i<lets.length;++i) {
         if(!got && gamePanel.mousexs> 0 && gamePanel.mousexs < xx+maxw) {
           value = lets[i];
           g.setColor(Color.white);
           g.fillRect(xx,y,maxw,h);
           g.setColor(Color.black);
           g.drawChars(lets,i,1,xx+maxw/2-slidingm.charWidth(lets[i])/2,yy);
           g.setColor(Color.white);
           got = true;
         }
         else g.drawChars(lets,i,1,xx+maxw/2-slidingm.charWidth(lets[i])/2,yy);
         xx+=maxw;
       }
       okToClick = true;
     }
   }
}
