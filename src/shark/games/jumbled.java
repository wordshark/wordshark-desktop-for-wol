package shark.games;

import java.awt.*;

import shark.*;

public class jumbled extends sharkGame {//SS03/12/05
  static final short MAXWORDS = 6;
  short wordtot;
  short o[];
  boolean split;
  String words[];
  word wlist[];
  boolean used[];
  letter letters[];
  int letterwidth,letterheight;
  short lettertot;
  Color startcolor[];
  short carryletter = -1;
  Rectangle wordbounds[];
  bkcolor colors[];
  boolean working;
  short done;
  int downx,downy;
  long downtime;
  int finishwith=-1;
  boolean saying;
  short letterlist[],letterlisttot;
  int spaces = 0;

  public jumbled() {
    errors = false;
    gamescore1 = true;
    peeps = true;
    listen= true;
    peep = true;
    this.wantSprite = false;
    forceSharedColor = true;
//    wantspeed=true;
//    gamePanel.setBackground(u.darkcolor());
    split = ((rgame.options & word.SPLIT) != 0);
    help("help_jumbled");
    setupWords();
    gamePanel.clearWholeScreen = true;
    gamePanel.setTouchDragGame();
    buildTopPanel();
 }

 //--------------------------------------------------------------
 public void afterDraw(long t){
   int i,j,k;
   if (finishwith>=0) {
     i=finishwith;
     finishwith=-1;
     used[i] = true;
     for (k = 0; k < letterlisttot; ++k) {
       j = letterlist[k];
       letters[j].dead = true;
     }
     wlist[i].say();
     new  spokenWord.whenfree(0) {
       public void action() {
         int k,j;
         for (k = 0; k < letterlisttot; ++k) {
           j = letterlist[k];
           letters[j].temp = true;
           letters[j].moveto(letters[j].xx + mover.WIDTH, letters[j].yy, 3000);
         }
         for(int i=0;i<lettertot;++i) {
            if(letters[i].temp || letters[i].dead) {
               System.arraycopy(letters,i+1,letters,i,--lettertot-i);
               --i;
            }
         }
         saying = false;
//startPR2005-04-18^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         if (Demo_base.isDemo) {
           if (Demo_base.demoIsReadyForExit(0))
           return;
         }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         gamescore(1);
         gamePanel.copyall = true;
         if (++done >= wordtot) {
           score(gametot1);
           exit(2000);
         }
         else{
            if(delayedflip && !completed){
               flip();
            }
         }
       }
     };
   }
 }
 //-------------------------------------------------------------
 void setupWords() {
    short ii,i,j,k,m;
    String s;
    short maxlen = 0;
    wordtot = (short)Math.min(rgame.w.length,MAXWORDS);
    words = new String[wordtot];
    wlist = new word[wordtot];
    wordbounds = new Rectangle[wordtot];
    colors = new bkcolor[wordtot];
    used = new boolean[wordtot];
    startcolor = new Color[wordtot];
    short down,across;
    int width;
    int x,y;
    short repeats,repeats2,o2[];
    o=u.select((short)rgame.w.length,wordtot);

    for(ii=0;ii<wordtot;++ii) {
       i = o[ii];
       wlist[ii] = rgame.w[i];
       words[ii] = split?rgame.w[i].vsplit(): rgame.w[i].v();
       startcolor[ii] = u.brightcolor();
       maxlen = (short)Math.max(maxlen, words[ii].length());
       lettertot +=  words[ii].length();
    }
    if(wordtot<=3 || maxlen>12) {down=wordtot;across=1;}
    else for(down = 2;down*(across = (short)(down*2*screenwidth/screenheight)) < wordtot;++down);
    letters = new letter[lettertot];
    wordfont = sizeFont(null,"m",mover.WIDTH/across/maxlen*3/2,mover.HEIGHT/down/3);
    FontMetrics fm = getFontMetrics(wordfont);
    letterwidth = fm.charWidth('m')*mover.WIDTH/screenwidth;
    letterheight = (fm.getHeight()+2)*mover.HEIGHT/screenheight;
    int wordheight =  mover.HEIGHT/down;
    int wordwidth = mover.WIDTH/across;
    o = u.shuffle(u.select(wordtot,wordtot));
    short row,col,len;
    int wx,wy;
    Color color1=u.backgroundcolor(null),color2=u.backgroundcolor(new Color[]{color1});

    short lose = (short)(down*across - wordtot);
    short a[] = new short[down];
    for(i=0;i<down;++i) a[i] = across;
    if(lose>0) {
       short ol[] = u.shuffle(u.select(down,down));
       for(i=0;i<lose;++i) --a[ol[i%down]];
    }

    for(ii=0,row = 0,col=0,k=0;ii<wordtot;++ii) {
       i = o[ii];
       len = (short)words[i].length();
       wordwidth = mover.WIDTH/a[row];
       wordbounds[i] = new Rectangle(col*wordwidth, row*wordheight,wordwidth,wordheight);
       colors[i] = new bkcolor((((row+col)&1)==0)?color1:color2,wordbounds[i]);
       wx = col*wordwidth + wordwidth/2 - letterwidth*len/2;
       wy = row*wordheight + wordheight/2 - letterheight/2;
       do {o2 = u.shuffle(u.select(len,len));}
                                 while(u.inorder(o2));
       for(j=0;j<len; ++j) {
         if(words[i].charAt(o2[j]) != ' ')
              addMover(letters[k] = new letter(words[i], words[i].charAt(o2[j]),i,k),wx,wy-letterheight+u.rand(letterheight*2));
          ++k;
          wx+=letterwidth;
       }
       if(++col >= a[row]) {++row;col=0;}
    }
    for(i=0; i<lettertot;++i) {
       if(letters[i] == null) {
         letter letters2[] = new letter[lettertot - 1];
         System.arraycopy(letters,0,letters2,0,i);
         System.arraycopy(letters,i+1,letters2,i,lettertot-i-1);
         letters = letters2;
         --lettertot;
         --i;
         spaces++;
       }
    }
 }
 //---------------------------------------------------------------
 public boolean click(int x1,  int y1)  {
   short i,j;
   String s;
   if(saying) return true;
   if(working) return true;
   working = true;
   if(carryletter < 0) {
     for(i=0;i<lettertot;++i) {
        if(letters[i].mouseOver && !letters[i].dead) {
           downx = x1;
           downy = y1;
           downtime=gtime;
           gamePanel.attachToMouse(letters[i]);
           carryletter = i;
           working = false;
           return true;
        }
     }
     for(i=0;i<lettertot;++i) {
        if(gamePanel.mousey >= letters[i].y
            && gamePanel.mousey <= letters[i].y + letters[i].h
            && gamePanel.mousex >= letters[i].x && gamePanel.mousex <= letters[i].x + letterwidth*2
            && !letters[i].dead) {
           downx = x1;
           downy = y1;
           downtime=gtime;
           gamePanel.attachToMouse(letters[i]);
           carryletter = i;
           working = false;
           return true;
        }
     }
   }
   else {
      if(wordbounds[letters[carryletter].wordno].contains(x1,y1)) {
         gamePanel.drop(letters[carryletter]);
         adjust(carryletter);
         carryletter = -1;
      }
      else noise.beep();
   }
   working = false;
   return true;
 }
 //---------------------------------------------------------
 short[] hsort(short first,short  last) {
    short tot = (short) (last-first);
    short i,j,temp,ret[] = u.select(tot,tot);
    for(i=0;i<tot;++i) ret[i]+=first;
    for(i=0;i<tot;++i) {
       for(j=(short)(i+1); j < tot;++j) {
          if(letters[ret[i]].x+letters[ret[i]].w/2
               > letters[ret[j]].x+letters[ret[j]].w/2) {
              temp =  ret[j];
              ret[j] = ret[i];
              ret[i] = temp;
          }
       }
    }
    return ret;
 }
 //--------------------------------------------------------
 void adjust(short curr) {
    short i,j,k,right=-1,left=-1;
    short first=-1,last=lettertot;
    Rectangle r = wordbounds[letters[curr].wordno];
    for(i=0;i<lettertot;++i) {
       if(letters[curr].wordno == letters[i].wordno) {
          if(first<0) first = i;
          letters[i].xx = Math.max(r.x,Math.min(r.x+r.width-letters[i].w, letters[i].x));
          letters[i].yy = Math.max(r.y,Math.min(r.y+r.height-letters[i].h, letters[i].y));
       }
       else if(first>=0) {last=i; break;}
    }
    short o[] = hsort(first,last);
    for(i=0;i<o.length;++i) if(o[i] == curr) break;
    short curro = i;
    for(j=(short)(curro+1);j<o.length;++j)
       if(overlap2(o[j],curr)) {right = o[j]; break;}
    for(j=(short)(curro-1);j>=0;--j)
       if(overlap2(o[j],curr)) {left = o[j]; break;}
    if(left>=0 || right >= 0) {
       if(left >= 0
           && (right < 0
               || Math.abs(letters[left].yy - letters[curr].yy )
                  < Math.abs(letters[right].yy - letters[curr].yy )))
            letters[curr].yy = letters[left].yy;
       else letters[curr].yy = letters[right].yy;
    }
    for(j=(short)(curro-1);j>=0;--j) {
       for(k=(short)(j+1);k<=curro;++k) {
          if(!overlapx(o[k],o[j])) break;
          if(overlapy(o[k],o[j])) {
              letters[o[j]].xx = Math.max(r.x,letters[o[k]].xx - letters[o[j]].w);
          }
       }
    }
    for(i=0;i<o.length;++i) {
       for(j=(short)(i+1);j<o.length;++j) {
          if(!overlapx(o[i],o[j])) break;
          if(overlapy(o[i],o[j])) {
             letters[o[j]].xx = Math.min(r.x+r.width - letters[o[j]].w,
                                    letters[o[i]].xx + letters[o[i]].w);
          }
       }
    }
    for(i=(short)(o.length-1);i>=0;--i) {
       for(j=(short)(i-1);j>=0;--j) {
          if(!overlapx(o[i],o[j])) break;
          if(overlapy(o[i],o[j])) {
             letters[o[j]].xx = Math.max(r.x,
                               letters[o[i]].xx - letters[o[j]].w);
          }
       }
    }
    for(i=first;i<last;++i)   {
          if(letters[i].yy != letters[i].y
                  ||   letters[i].xx != letters[i].x) {
             letters[i].moveto(letters[i].xx,letters[i].yy,400);
          }
    }
                  // check for completed word
    String s = String.valueOf(letters[curr].ch);
    String pword = letters[curr].parentWord;
    String sepwords[] = pword.split(" ");
    int spacepos[] = null;
    if(sepwords.length>1){
        int n = 0;
        for(int p = 0; p < sepwords.length; p++){
            if(spacepos==null)spacepos = new int[]{};
            n += sepwords[p].length();
            spacepos = u.addint(spacepos, n);
            n++;
        }
    }
    letterlist = new short[lettertot+spaces];
    letterlist[0] = curr;
    letterlisttot = 1;

    if(spaces>0) {
      s = "";
      for(i = 1,j=0; i<o.length; ++i,++j) {
        if(overlap2(o[i],o[j])) {
            if(s.length()==0) {
                s = String.valueOf(letters[o[j]].ch);
                letterlist[letterlisttot++] = o[j];
            }
            if(pword.startsWith(s) && spacepos!=null && u.inlist(spacepos, s.length()) && spacebetween(o[i],o[j])) {
                s = s + ' ' + String.valueOf(letters[o[i]].ch);
            }
            else{  
                s = s + String.valueOf(letters[o[i]].ch);
            }
            letterlist[letterlisttot++] = o[i];
       }
       else if(pword.startsWith(s) && spacepos!=null && u.inlist(spacepos, s.length()) && spacebetween(o[i],o[j])) {
            if(s.length()==0) {
              s = String.valueOf(letters[o[j]].ch);
              letterlist[letterlisttot++] = o[j];
            }
            s = s + ' ' + String.valueOf(letters[o[i]].ch);
            letterlist[letterlisttot++] = o[i];
        }
        else {s = "";letterlisttot=0;}
      }
    }
    else {
      for (i = (short) (curro - 1), j = curro;
           i >= 0 && overlap2x(o[i], o[j]); --i) {
        if (overlap2(o[i], o[j])) {
          s = String.valueOf(letters[o[i]].ch) + s;
          j = i;
          letterlist[letterlisttot++] = o[i];
        }
      }
      for (i = (short) (curro + 1), j = curro;
           i < o.length && overlap2x(o[i], o[j]); ++i) {
        if (overlap2(o[i], o[j])) {
          s = s + String.valueOf(letters[o[i]].ch);
          j = i;
          letterlist[letterlisttot++] = o[i];
        }
      }
    }
    if(s.length() == letters[curr].wordlen)
      for(i=0;i<wordtot;++i) {
       if(!used[i] && words[i].equals(s)) {
          finishwith = i;
          saying = true;
          break;
       }
    }
 }
 //--------------------------------------------------------
 boolean overlapx(short i, short j) {
    return letters[i].xx + letters[i].w > letters[j].xx
            && letters[j].xx + letters[j].w > letters[i].xx;
 }
 //--------------------------------------------------------
 boolean overlapy(short i, short j) {
    return  letters[i].yy + letters[i].h > letters[j].yy
            && letters[j].yy + letters[j].h > letters[i].yy;
 }
 //--------------------------------------------------------
 boolean overlap2(short i, short j) {
    return letters[i].xx + letters[i].w > letters[j].xx - letterwidth*7/8
            && letters[j].xx + letters[j].w > letters[i].xx -letterwidth*7/8
            && Math.abs(letters[i].yy - letters[j].yy) <letterheight/2;
 }
 //--------------------------------------------------------
 boolean overlap2x(short i, short j) {
    return letters[i].xx + letters[i].w > letters[j].xx - letterwidth*7/8
            && letters[j].xx + letters[j].w > letters[i].xx -letterwidth*7/8;
 }
 //--------------------------------------------------------
 boolean spacebetween(short i, short j) {
    return letters[i].xx - (letters[j].xx + letters[j].w) >=  letterwidth*4/16
            && letters[i].xx - (letters[j].xx + letters[j].w) <= letterwidth*3
            && Math.abs(letters[i].yy - letters[j].yy) <letterheight/2;
 }
 //-------------------------------------------------------
   public void listen1() {
     setword();
     super.listen1();
   }
 //-------------------------------------------------------
   public void peep1() {
     setword();
     super.peep1();
   }
 //--------------------------------------------------------------
 void setword() {
    short i;
    if(carryletter >= 0) {
       if(!used[letters[carryletter].wordno])
          currword = wlist[letters[carryletter].wordno];
       else {
          for(i=0;i<wordtot;++i) {
             if(!used[i] && words[i].indexOf(letters[carryletter].ch) >= 0) {
                 currword =  wlist[i];
             }
          }
       }
    }
    else {
       for(i=0;i<wordtot;++i) {
          if(!used[i])  currword =  wlist[i];
       }
    }
 }

 //--------------------------------------------------------------
 class letter extends mover {
    char ch;
    short thisnum;
    short wordno,wordlen;
    boolean dead;
    int xx,yy;
    String parentWord;

    public letter(String word, char ch1,short wordno1,short currnum) {
       super(true);
       parentWord = word;
       ch = ch1;
       wordno = wordno1;
       wordlen = (short) words[wordno].length();
       thisnum = currnum;
       FontMetrics m =getFontMetrics(wordfont);
       w = (m.charWidth(ch)+2)*mover.WIDTH/screenwidth;
       h = letterheight;
    }
    public void paint(Graphics g, int x, int y, int w, int h) {
       short i;

       g.setColor(gamePanel.movesWithMouse(this)? Color.red:Color.black);
       g.setFont(wordfont);
       FontMetrics m = g.getFontMetrics();
       g.drawString(String.valueOf(ch), x + w/2 - m.charWidth(ch)/2,
                     y + h/2 - m.getHeight()/2 + m.getMaxAscent());
    }
//    public void mouseUp(int x1, int y1) {
//      int xx = x1*mover.WIDTH/screenwidth;
//      int yy = y1*mover.HEIGHT/screenheight;
//       if(carryletter >= 0 && ((downx - xx)*(downx-xx)+(downy-yy)*(downy-yy) > w*h
//           || gtime - downtime > 1000))
//         click(xx,yy);
//    }
  }
  //----------------------------------------------------------------------
  class bkcolor extends mover {
     Color color;
     Rectangle r;
     bkcolor(Color color1,Rectangle r1) {
        color = color1;
        r = r1;
        w = r1.width;
        h = r1.height;
        addMover(this,r1.x,r1.y);
        gamePanel.puttobottom(this);
     }
      public void paint(Graphics g, int x, int y, int w, int h) {
        g.setColor(color);
        g.fillRect(x,y,w,h);
      }
  }
}
