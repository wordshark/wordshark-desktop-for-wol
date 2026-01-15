
package shark.games;

import java.util.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
//startPR2004-10-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
import java.awt.print.*;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR

import shark.*;
import shark.spokenWord.*;


public class sentences extends sharkGame {
  static final String underscores = "________________________________";
  int elx,ely,across,down;
  crossword c;
  int cwidth,cheight;
  short cluev=-1,clueh=-1,crossstart[];
  String words[] = new String[0];
  word saywords[] = new word[0];
  short sentnos[] = new short[0];
  static String usedSentences[] = new String[0];
  static final short WANTSENT = 4;
  boolean alreadyfinished[] = new boolean[WANTSENT];
  sharkImage tick;
  mover clickonnext;
  static String acrosstext = u.gettext("u_","across");
  static String downtext = u.gettext("u_","down");
  mbutton justcloze,justtype,justhidden,justjumbled;
  boolean dontsee,donthear;
  class letterpos {
     char c;
     short x,y;
     boolean filled,marked;
     letterpos(char c1,short x1,short y1) {c=c1;x=x1;y=y1;}
  }
  letterpos b[];
  short btot;
  int exity;
  class ww {
     String w;
     short sentenceno,wordno;
     letterpos let;  // starting letterpos
     boolean vert;
  }
  ww wordlist1[];
  short wtot;
  sentence[] s ;

  public sentences() {
    short i,j,k;
    s = rgame.s;
    bgavoid = new Color[] {Color.yellow};
    clickonrelease = true;
    wantsPrint = true;
    for(i=0; i<s.length; ++i) {
       if(s[i].type != sentence.SIMPLECLOZE && s[i].type != sentence.CLOZE) {
          wantsPrint = false;
          break;
       }
       if(s[i].type == sentence.SIMPLECLOZE && s[i].oneletter) {  // strip those with one-letter answers // start rb 16/1/08
          sentence ss[] = new sentence[s.length - 1];
          System.arraycopy(s,0,ss,0,i);
          System.arraycopy(s,i+1,ss,i,ss.length-i);
          s = rgame.s = ss;
          --i;
       }                                                         // end rb 16/1/08
    }
    if(!wantsPrint) {          // harder crossword
       sentence s1[] = rgame.s;   // point to sentences
          // see if enough to allow just jumbled, tracking or typeing
       int jumbled = 0,hidden=0,type=0,cloze=0;
       for(i=0;i<s1.length;++i) {
         if(s1[i].type == sentence.JUMBLED) ++jumbled;
         else if(s1[i].type == sentence.TYPE) ++type;
         else if(s1[i].type == sentence.CLOZE) ++cloze;
         else if(s1[i].type == sentence.HIDDEN) ++hidden;
       }
       int ex = 0;
       if(cloze>=2) {
         justcloze = new mbutton(rgame.getParm("justcloze"));
         justcloze.addActionListener(new ActionListener(){         //Listener 3.1
            public void actionPerformed(ActionEvent e) {
              setup2(sentence.CLOZE);
            }
         });
         ++ex;
       }
       if(type>=1) {
         justtype = new mbutton(rgame.getParm("justtype"));
         justtype.addActionListener(new ActionListener(){         //Listener 3.1
            public void actionPerformed(ActionEvent e) {
              setup2(sentence.TYPE);
            }
         });
         ++ex;
       }
       if(hidden>=1) {
         justhidden = new mbutton(rgame.getParm("justhidden"));
         justhidden.addActionListener(new ActionListener(){         //Listener 3.1
            public void actionPerformed(ActionEvent e) {
              setup2(sentence.HIDDEN);
            }
         });
         ++ex;
       }

       if(jumbled>=1) {
         justjumbled = new mbutton(rgame.getParm("justjumbled"));
         justjumbled.addActionListener(new ActionListener(){         //Listener 3.1
           public void actionPerformed(ActionEvent e) {
             setup2(sentence.JUMBLED);
           }
        });
        ++ex;
       }
       if(ex>0) {
         extrabuttons = new mbutton[ex];
         i = 0;
         if (justjumbled != null)        extrabuttons[i++] = justjumbled;
         if (justtype != null)           extrabuttons[i++] = justtype;
         if (justhidden != null)         extrabuttons[i++] = justhidden;
         if (justcloze != null)          extrabuttons[i++] = justcloze;
       }
       s = new sentence[WANTSENT];
       short gotsent = 0;
       short hadtype = 0;
       short o[] = u.shuffle(u.select((short)s1.length,(short)s1.length));
       for(i=0; i<o.length && gotsent<WANTSENT; ++i) {
          j = o[i];
          if(u.findString(usedSentences,s1[j].val) < 0
                          && (hadtype & s1[j].type) == 0) {
               s[gotsent++] = s1[j];
               hadtype |= s1[j].type;
          }
       }
       short lasto = i;
       if(gotsent<WANTSENT) {   // not enough - recycle used of missing types
          outerloop:for(i=0; i<usedSentences.length && gotsent<WANTSENT; ++i) {
            for(j=0; j<s1.length && gotsent<WANTSENT; ++j) {
               if (s1[j].val.equals(usedSentences[i])
                     && (hadtype & s1[j].type) == 0) {
                  for(k=0;k<gotsent;++k) {
                     if(s[k] == s1[j]) continue outerloop;
                  }
                  s[gotsent++] = s1[j];
                  hadtype |= s1[j].type;
                  continue outerloop;
               }
            }
          }
       }
       if(gotsent<WANTSENT) {   // not enough - fill up with any not used yet
         outerloop:for (i = 0; i < o.length && gotsent < WANTSENT; ++i) {
           j = o[i];
           if(u.findString(usedSentences,s1[j].val) < 0) {
             for (k = 0; k < gotsent; ++k) {
               if (s[k] == s1[j])
                 continue outerloop;
             }
             s[gotsent++] = s1[j];
           }
         }
       }
       if(gotsent<WANTSENT) {   // not enough - fill up with any at all
         outerloop:for (i = 0; i < o.length && gotsent < WANTSENT; ++i) {
           j = o[i];
             for (k = 0; k < gotsent; ++k) {
               if (s[k] == s1[j])
                 continue outerloop;
             }
             s[gotsent++] = s1[j];
         }
       }
       if(gotsent<WANTSENT) {
          sentence ss[] = new sentence[gotsent];
          System.arraycopy(s,0,ss,0,gotsent);
          s = ss;
       }
     }
     else {   // simple crossword - give option to not see sentence
        optionlist = new String[]{"crossword1-seehear"};
        switch(options.optionval("crossword1-seehear")) {
               case 1:donthear = true; break;
               case 2:dontsee = true; break;
        }
     }
    errors = true;
    gamescore1 = true;
    listen= true;
    peeps = !wantsPrint;
    peep = !wantsPrint;
    buildTopPanel();
    markoption();
    crossstart = new short[s.length];
    for(i=0; i<s.length; ++i) {
       short sofar = crossstart[i] = (short)words.length;
       words = u.addString(words,s[i].crosswords);
       saywords = u.addWords(saywords,s[i].crosswd);
       short newsentnos[] = new short [words.length];
       System.arraycopy(sentnos,0,newsentnos,0,sofar);
       sentnos = newsentnos;
       for(j=sofar;j<words.length;++j) sentnos[j] = i;
       s[i].manager = gamePanel;
       if(wantsPrint && dontsee) s[i].dontsee = true; // just for cloze   // just for cloze
       if(wantsPrint && donthear) s[i].donthear = true; // just for cloze   // just for cloze
    }
    wtot = (short)words.length;
    setup1();
 }
 //--------------------------------------------------------------
 public void restart() {
   int i;
   gamePanel.removeAllMovers();
   dontsee=donthear=false;
   switch(options.optionval("crossword1-seehear")) {
          case 1:donthear = true; break;
          case 2:dontsee = true; break;
   }
   markoption();
   for(i=0; i<s.length; ++i) {
       s[i].finished = false;
       s[i].dontsee = wantsPrint && dontsee; // just for cloze
       s[i].donthear = wantsPrint && donthear;
   }
   setup1();
 }
    //-------------------------------------------------------------
 void setup1() {
    short repeats = 0;
    letterpos[] bestb=null;
    ww bestwordlist[] = null;

    short bestbtot=0;
    int bestw=0,neww;
    cheight = screenheight;
    cwidth = screenwidth/3;
    while(++repeats<10) {
       neww = buildsquare();
       if(neww > bestw) {
          bestw = neww;
          bestb=b;
          bestbtot=btot;
          bestwordlist = wordlist1;
          if(neww > screenheight/16) break;
       }
    }
    b=bestb;
    btot=bestbtot;
    wordlist1 = bestwordlist;
    across = maxx(b,btot);
    down = maxy(b,btot);
    sizes();
    c =new crossword();
    addMover(c, mover.WIDTH-c.w, (mover.HEIGHT-c.h)/2 );
    clickonnext = showmessage(rgame.getParm("clickonnext"),c.x/3,mover.HEIGHT*7/8,c.x,mover.HEIGHT,yellow());
    setlisten(false);
    if(!wantsPrint) setpeep(false);
    help(wantsPrint?"help_simplesent":"help_hardsent");
  }
  //---------------------------------------------------------------------
     // set up with only selected type of sentence

  void setup2(int type) {
    int i,j,got=0;
    super.restart();
    cluev=-1;clueh=-1;
    gamePanel.removeAllMovers();
    sentence s1[] = rgame.s;   // point to sentences
    for(i=0;i<s1.length;++i) {
      if(s1[i].type == type) {
        ++got;
      }
    }
    s = new sentence[got];
    for(i=got=0;i<s1.length;++i) {
      if(s1[i].type == type) {
        s[got++] = s1[i];
      }
    }
    crossstart = new short[s.length];
    words = new String[0];
    saywords = new word[0];
    for(i=0; i<s.length; ++i) {
       short sofar = crossstart[i] = (short)words.length;
       words = u.addString(words,s[i].crosswords);
       saywords = u.addWords(saywords,s[i].crosswd);
       short newsentnos[] = new short [words.length];
       System.arraycopy(sentnos,0,newsentnos,0,sofar);
       sentnos = newsentnos;
       for(j=sofar;j<words.length;++j) sentnos[j] = (short)i;
       s[i].manager = gamePanel;
    }
    wtot = (short)words.length;
    setup1();
    if(got==1) {
      c.mouseClicked(-2,-2);
    }
  }
  //---------------------------------------------------------------
  void removeextrabuttons() {
    if(extrabuttons != null) {
      for(int i=0;i<extrabuttons.length;++i) {
        topPanel.remove(extrabuttons[i]);
      }
      extrabuttons = null;
    }
  }
  //------------------------------------------------------------
  void printsetup(int w, int h) {
    short repeats = 0;
    letterpos[] bestb=null;
    short bestbtot=0;
    int bestw=0,neww;
    ww bestwordlist[] = null;
    cheight = h;
    cwidth = w;
    while(++repeats<10) {
       neww = buildsquare();
       if(neww > bestw) {
          bestw = neww;
          bestb=b;
          bestbtot=btot;
          bestwordlist = wordlist1;
          if(neww > h/16) break;
       }
    }
    b=bestb;
    btot=bestbtot;
    wordlist1 = bestwordlist;
    across = maxx(b,btot);
    down = maxy(b,btot);

  }
  //-------------------------------------------------------------
  public void sizes() {
     int elsize = Math.min(cheight/down,
                             cwidth/across);
     int fsiz,sqstepy;
//startPR2007-11-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//     wordfont = new Font(sharkStartFrame.wordfont.getName(),
//                       sharkStartFrame.wordfont.getStyle(),fsiz=50);
     wordfont = sharkStartFrame.wordfont.deriveFont((float)(fsiz=50));
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     metrics = getFontMetrics(wordfont);
     while(true) {
        sqstepy = Math.max(metrics.getHeight(), u.getadvance(metrics));
        if(sqstepy > elsize) {
//startPR2007-11-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//           wordfont = new Font(wordfont.getName(), wordfont.getStyle(), --fsiz);
          wordfont = wordfont.deriveFont((float)--fsiz);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
           metrics = getFontMetrics(wordfont);
        }
        else break;
     }
     elx = elsize*mover.WIDTH/screenwidth;
     ely = elsize*mover.WIDTH/screenheight;
     originalWordfont = wordfont;
     originalScreenwidth = screenwidth;
     originalScreenheight = screenheight;
    }
  //--------------------------------------------------------------
  int buildsquare () {
     wordlist1 = new  ww[wtot = (short)words.length];
     String s;
     short x,y,i,j,k,m,n,jj,kk,mm,b2tot,startp;
     short fromx,tox,fromy,toy,bestx=0,besty=0;
     int size,maxsize=0,across,down;
     short minxb,maxxb,minyb,maxyb,adjx,adjy,minyb2,minxb2,widthb2,heightb2;
     letterpos let[][] = new letterpos[wtot][wtot*20], b2[];
     short blocktot=0,curr[] = new short[wtot];
     short o[] = u.shuffle(u.select(wtot,wtot)),o2[],os[],ob[],ox[],oy[],slen;

      //   fit words together
     loop1:for(i=0;i<wtot;++i) {
        wordlist1[i] = new ww();
        wordlist1[i].w = words[o[i]];
        wordlist1[i].sentenceno = sentnos[o[i]];
        wordlist1[i].wordno = o[i];

        s = words[o[i]];
        slen = (short)s.length();
        if(slen < 2) continue;
        os = u.shuffle(u.select(slen,slen));
        ob = u.shuffle(u.select(blocktot, blocktot));
        for(jj=0;jj<blocktot;++jj){
           j = ob[jj];
           o2 = u.shuffle(u.select(curr[j], curr[j]));
           for(kk=0;kk<curr[j];++kk) {
              k = o2[kk];
              for(mm=0;mm<slen;++mm) {
                 m = os[mm];
                 if(s.charAt(m) == let[j][k].c) {
                    if(letterfitsh(s,m,let[j],k,curr[j])) {
                       if(m==0) startp = k;
                       else startp = curr[j];
                       for(n=0;n<slen;++n) {
                          if(n != m) let[j][curr[j]++]
                              = new letterpos(s.charAt(n),(short)(let[j][k].x-m+n),let[j][k].y);
                       }
                       wordlist1[i].let = let[j][startp];
                       wordlist1[i].vert = false;
                        continue loop1;
                    }
                    if(letterfitsv(s,m,let[j],k,curr[j])) {
                       if(m==0) startp = k;
                       else startp = curr[j];
                       for(n=0;n<slen;++n) {
                          if(n != m) let[j][curr[j]++]
                              = new letterpos(s.charAt(n),let[j][k].x,(short)(let[j][k].y-m+n));
                       }
                       wordlist1[i].let = let[j][startp];
                       wordlist1[i].vert = true;
                       continue loop1;
                    }
                 }
              }
           }
        }
          // cannot attach to old block - create new
         if(u.rand(2) == 0) {
           for(n=0;n<slen;++n) {
              let[blocktot][curr[blocktot]++]
                              = new letterpos(s.charAt(n),n,(short)0);
           }
           wordlist1[i].let = let[blocktot][0];
           wordlist1[i].vert = false;
         }
        else {
           for(n=0;n<slen;++n) {
              let[blocktot][curr[blocktot]++]
                              = new letterpos(s.charAt(n),(short)0,n);
           }
           wordlist1[i].let = let[blocktot][0];
           wordlist1[i].vert = true;
        }
        ++blocktot;
     }
          // amalgamate blocks by fitting as best as possible
     ob = u.shuffle(u.select(blocktot, blocktot));
     b = let[ob[0]];
     btot = curr[ob[0]];
     if(blocktot==1) {
        maxsize = Math.min(cwidth/(maxx(b,btot)-minx(b,btot)),
                           cheight/(maxy(b,btot)-miny(b,btot)));
     }
     for(jj=1;jj<blocktot;++jj) {
         b2 = let[ob[jj]];
         b2tot = curr[ob[jj]];
         minxb = minx(b,btot);
         minyb = miny(b,btot);
         minxb2 = minx(b2,b2tot);
         minyb2 = miny(b2,b2tot);
         maxxb = maxx(b,btot);
         maxyb = maxy(b,btot);
         widthb2 = (short)(maxx(b2,b2tot) - minxb2);
         heightb2 = (short)(maxy(b2,b2tot) - minyb2);

         fromx = (short)(minxb - widthb2-1);
         tox = (short)(maxxb + 1);
         fromy = (short)(minyb - heightb2-1);
         toy = (short)(maxyb + 1);
         ox = u.shuffle(u.select(fromx, tox, (short)(tox-fromx)));
         oy = u.shuffle(u.select(fromy, toy, (short)(toy-fromy)));
         maxsize = 0;
         for(x=0;x<ox.length;++x) {
            adjx = (short)(ox[x] - minxb2) ;
            loop2:for(y=0;y<oy.length;++y)  {
               adjy = (short)(oy[y] - minyb2);
               across = Math.max(ox[x]+widthb2,maxxb)- Math.min(ox[x],minxb);
               down = Math.max(oy[y]+widthb2,maxyb)- Math.min(oy[y],minyb);
               size = Math.min(cwidth/across, cheight/down);

               if(size <= maxsize) continue loop2;
               for (k=0; k <b2tot; ++k) {
                  for (m=0; m <btot; ++m) {
                     if(Math.abs(b2[k].x +adjx - b[m].x)
                        + Math.abs(b2[k].y +adjy - b[m].y) < 2)
                           continue  loop2;
                  }
               }
               maxsize = size;
               bestx = adjx;
               besty = adjy;
            }
         }
         for(i=0;i<b2tot;++i) {
            b[btot++] = b2[i];
            b2[i].x+=bestx;
            b2[i].y+=besty;
         }
     }
     adjx = (short)-minx(b,btot);
     adjy = (short)-miny(b,btot);
     for(i=0;i<btot;++i) {
        b[i].x += adjx;
        b[i].y += adjy;
     }
     return maxsize;
  }
  //-------------------------------------------------------------
  boolean letterfitsh(String s, short spos, letterpos[] block, short bpos,short bmax) {
     int x = block[bpos].x-spos, y = block[bpos].y;
     if(!isfree(x-1,y,block,bmax)
      || !isfree(x+s.length(),y,block,bmax)) return false;
     for(short i=-1;i<s.length()+1;++i) {
           if(i != spos
               && (!isfree(x+i,y,block,bmax)
                || !isfree(x+i,y-1,block,bmax)
                || !isfree(x+i,y+1,block,bmax)))
           return false;
     }
     return true;
  }
  //-------------------------------------------------------------
  boolean letterfitsv(String s, short spos, letterpos[] block, short bpos,short bmax) {
     int x = block[bpos].x, y = block[bpos].y-spos;
     if(!isfree(x,y-1,block,bmax)
      || !isfree(x,y+s.length(),block,bmax)) return false;
     for(short i=-1;i<s.length()+1;++i) {
        if(i != spos
               && (!isfree(x,y+i,block,bmax)
                || !isfree(x+1,y+i,block,bmax)
                || !isfree(x-1,y+i,block,bmax)))
           return false;
     }
     return true;
  }
  //-------------------------------------------------------------
  boolean isfree(int x, int y, letterpos[] block, short bmax) {
     for(short i=0;i<bmax;++i) {
        if(block[i].x == x && block[i].y == y) return false;
     }
     return true;
  }
  //-------------------------------------------------------------
  letterpos findb(int x, int y) {
     for(short i=0;i<btot;++i) {
        if(b[i].x == x && b[i].y == y) return b[i];
     }
     return null;
  }
  //-------------------------------------------------------------
  short findclue(letterpos lp, boolean vert) {
     for(short i=0;i<wtot;++i) {
        if(wordlist1[i].let == lp && wordlist1[i].vert == vert) return wordlist1[i].sentenceno;
     }
     noise.beep();
     return -1;
  }
  //-------------------------------------------------------------
  short maxx(letterpos[] block, short bmax) {
     short x = block[0].x;
     for(short i=1;i<bmax;++i) {
        if(block[i].x > x) x = block[i].x;
     }
     return (short)(x+1);
  }
  //-------------------------------------------------------------
  short minx(letterpos[] block, short bmax) {
     short x = block[0].x;
     for(short i=1;i<bmax;++i) {
        if(block[i].x< x) x = block[i].x;
     }
     return x;
  }
  //-------------------------------------------------------------
  short maxy(letterpos[] block, short bmax) {
     short y =  block[0].y;
     for(short i=1;i<bmax;++i) {
        if(block[i].y > y) y = block[i].y;
     }
     return (short)(y+1);
  }
  //-------------------------------------------------------------
  short miny(letterpos[] block, short bmax) {
     short y =  block[0].y;
     for(short i=1;i<bmax;++i) {
        if(block[i].y < y) y = block[i].y;
     }
     return y;
  }
  //-------------------------------------------------------------
 public void sparefunc() {    // after doing something in sentence
   boolean finished = true,wantclickonnext = false;
   int i,j,x,y;
//   if(clueh >= 0 && s[clueh].cancel) {
//      s[clueh].cancel = false;
//      removeMover(s[clueh]);
//      clueh =- 1;
//      for(i=0; i<btot;++i)  b[i].marked = false;
//      wantclickonnext=false;
//   }
//   if(cluev >= 0 && s[cluev].cancel) {
//      s[cluev].cancel = false;
//      removeMover(s[cluev]);
//      cluev =- 1;
//      for(i=0; i<btot;++i)  b[i].marked = false;
//      wantclickonnext=true;
//   }
     if(s==null) return;
     letterpos lp;
     ww w1=null;
     if((clueh >= 0 && s[clueh].hadkeyhit
         ||    cluev >= 0 && s[cluev].hadkeyhit) && gamePanel.isMover(clickonnext))
        removeMover(clickonnext);
     if((clueh >= 0 && s[clueh].finished
        ||    cluev >= 0 && s[cluev].finished) && tick == null) {
        tick = sharkImage.random("tick_");
        if(tick.getcolor(0).equals(Color.black)) tick.recolor(0,Color.red);    // rb 31.12.07
        tick.w = c.x/2;
        tick.h = mover.HEIGHT/3;
        tick.adjustSize(screenwidth,screenheight);
        addMover(tick,c.x/2, mover.HEIGHT-tick.h-clickonnext.h);
        gamePanel.puttobottom(tick);
        wantclickonnext=true;
        clickonrelease = true;
//        setlisten(false);
  //startPR2005-04-18^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          if(Demo_base.isDemo){
            if(s.length < 5)
              Demo_base.demoIsReadyForExit(-1);
            else
              Demo_base.demoIsReadyForExit(0);
          }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR

        if(!wantsPrint) setpeep(false);
        help(wantsPrint?"help_simplesent":"help_hardsent");
     }
     for(i = 0;i<s.length;++i) {
        if(!s[i].finished) finished=false;
        else if(!wantsPrint && !alreadyfinished[i]) { // hard crossword only
           usedSentences = u.addString(u.removeString(usedSentences,s[i].val),s[i].val);
           if(usedSentences.length > 20) usedSentences = u.removeString(usedSentences,0);
           alreadyfinished[i] = true;
        }
        if(s[i].doneword >= 0) {
           exity = s[i].exity;
           for(j=0;j<wordlist1.length;++j) {
              w1 = wordlist1[j];
              if(w1.wordno == s[i].doneword+crossstart[i]) break;
           }
           currword =  saywords[w1.wordno];
           if(s[i].type != sentence.JUMBLED && !donthear) {
              new spokenWord.whenfree(0) {
                 public void action() {currword.say();}
              };
           }
           if(w1 != null && w1.let != null)
             for(x = w1.let.x, y =  w1.let.y;
                 (lp = findb(x,y)) != null; ) {
               lp.filled = true;
               if(w1.vert) ++y; else ++x;
           }
//           c.ended = false;
           s[i].doneword = -1;
        }
     }
     if(finished && !completed) {
        score(gametot1);
        removeMover(clickonnext);
        removeextrabuttons();
        exitbutton(mover.WIDTH/3,mover.HEIGHT*7/8);
     }
     else if(wantclickonnext && !gamePanel.isMover(clickonnext)) {
       if(delayedflip && !completed){
           flip();
       }
        addMover(clickonnext, c.x - clickonnext.w, mover.HEIGHT - clickonnext.h);
        help(wantsPrint?"help_simplesent":"help_hardsent");
//        setlisten(false);
        if(!wantsPrint) setpeep(false);
     }
     c.ended = false;
   }
   public void peep1() {
     if(clueh >= 0 || cluev >= 0) {
        short i = (clueh>=0)?clueh:cluev;
        if(s[i].type == sentence.TYPE || s[i].type == sentence.PICK) {
           sentence.endpeep = gtime() + 2000;
           ++peeptot;
           peepsView.setText(String.valueOf(peeptot));
           peepsView.setFont(sharkStartFrame.treefont);
           studentrecord.peepList = u.addString(studentrecord.peepList,"whole sentence");
        }
     }
   }
   public void listen1() {
     if(clueh >= 0 || cluev >= 0) {
        short i = (clueh>=0)?clueh:cluev;
        if(s[i].type == sentence.TYPE || s[i].type == sentence.PICK
                                      || s[i].type == sentence.CLOZE
                                      || s[i].type == sentence.SIMPLECLOZE) {
           s[i].readsentence();
        }
        if(s[i].type == sentence.JUMBLED) {
          s[i].jumbledread();
        }
     }
   }
  //-------------------------------------------------------------
  class crossword extends mover {
      Color bg = u.darkcolor();
      int startx,starty;
      int elw,elh;
      public crossword()  {
         super(false);
         mouseSensitive = true;
         w = across*elx;
         h = down*ely;
      }
      public void paint(Graphics g,int x, int y, int w, int h) {
         if(clueh >= 0 && !s[clueh].finished && s[clueh].picture != null) {
           s[clueh].picture.paint(g, x, y, w, h);
           keepMoving = true;
         }
         else if(cluev >= 0 && !s[cluev].finished && s[cluev].picture != null) {
           s[cluev].picture.paint(g, x, y, w, h);
           keepMoving = true;
         }
         else {
           keepMoving = false;
           int i, j, x1, y1;
           elw = w / across;
           elh = h / down;
           int dy = Math.max(1, elh / 2 - metrics.getHeight() / 2) +
               metrics.getMaxAscent();
           g.setColor(bg);
           g.fillRect(x, y, w, h);
           startx = (x += (w - across * elw) / 2);
           starty = (y += (h - down * elh) / 2);
           g.setFont(wordfont);
           for (i = 0; i < btot; ++i) {
             x1 = x + (b[i].x) * elw;
             y1 = y + (b[i].y) * elh;
             g.setColor(b[i].marked ? Color.yellow : Color.white);
             g.fillRect(x1, y1, elw, elh);
             if (b[i].filled) {
               g.setColor(Color.black);
               g.drawChars(new char[] {b[i].c}, 0, 1,
                           x1 + (elw - metrics.charWidth(b[i].c)) / 2,
                           y1 + dy);
             }
             g.setColor(bg);
             g.drawRect(x1, y1, elw, elh);
           }
         }
      }
      //---------------------------------------------------------
                           // mouse down
      public void mouseClicked(int xm,int ym) {
         int i,j;
         mover currmov = null;

         if(tick != null) {removeMover(tick); tick = null;}
         if(gamePanel.isMover(clickonnext)) removeMover(clickonnext);
         if(!gamePanel.showSprite) {
            if(clueh >= 0) {s[clueh].mouseClicked(xm,ym);}
            else if(cluev >= 0) {s[cluev].mouseClicked(xm,ym);}

            return;
         }

         if(clueh >= 0) currmov = s[clueh];
         if(cluev >= 0) currmov = s[cluev];
//         if(clueh >= 0) removeMover(s[clueh]);
//         if(cluev >= 0) removeMover(s[cluev]);

         ended = false;
         cluev = clueh = -1;
         if(xm == -2) {   // special call to autostart
           mx = b[0].x;
           my = b[0].y;
         }
         else {
           mx = Math.max(0, Math.min(across - 1, (gamePanel.mousexs - startx) / elw));
           my = Math.max(0, Math.min(down - 1, (gamePanel.mouseys - starty) / elh));
           removeextrabuttons();
         }
         for(i=0; i<btot;++i)  b[i].marked = false;
         for(i=0; i<btot ;++i) {
            if(b[i].x == mx && b[i].y == my) {
               boolean needhorz = false;
               int horzstart = mx,horzend=mx;
               for(j = mx-1; !isfree(j,my,b,btot);--j) {
                  horzstart = j;
                  if(!findb(j,my).filled) needhorz = true;
               }
               for(j = mx; !isfree(j,my,b,btot);++j) {
                  horzend =j;
                  if(!findb(j,my).filled) needhorz = true;
               }
               if(needhorz && horzstart!=horzend) {
                  for(j=horzstart;j<=horzend;++j) findb(j,my).marked = true;
                  cluev = findclue(findb(horzstart,my),false);
                  break;
               }
               boolean needvert = false;
               int vertstart = my,vertend=my;
               for(j = my-1; !isfree(mx,j,b,btot);--j) {
                  vertstart = j;
                  if(!findb(mx,j).filled) needvert = true;
               }
               for(j = my; !isfree(mx,j,b,btot);++j) {
                  vertend =j;
                  if(!findb(mx,j).filled) needvert = true;
               }
               if(needvert && vertstart!=vertend) {
                  for(j=vertstart;j<=vertend;++j)
                                 findb(mx,j).marked = true;
                  clueh = findclue(findb(mx,vertstart),true);
                  break;
                }
            }
         }
         if(clueh >= 0) {
            if(s[clueh] != currmov) {
               spokenWord.flushspeaker(true);
               keypadDeactivate();
               if(currmov != null) removeMover(currmov);
               s[clueh].manager = gamePanel;
               s[clueh].setup(mover.WIDTH - c.w - mover.WIDTH/screenwidth,
                           mover.HEIGHT*7/8,
                           (s[clueh].type == sentence.SIMPLECLOZE)? (u.notlen1(wordlist.getNormalWordlist())):null);
               addMover(s[clueh],0,0);
            }
         }
         else if(cluev >= 0) {
            if(s[cluev] != currmov) {
              spokenWord.flushspeaker(true);
              keypadDeactivate();
              if(currmov != null) removeMover(currmov);
              s[cluev].manager = gamePanel;
              s[cluev].setup(mover.WIDTH - c.w- mover.WIDTH/screenwidth,
                            mover.HEIGHT*7/8,
                           (s[cluev].type == sentence.SIMPLECLOZE)?(u.notlen1(wordlist.getNormalWordlist())):null);
              addMover(s[cluev],0,0);
            }
         }
         else  if(currmov != null) removeMover(currmov);
      }
   }
   public void print() {
//startPR2004-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      u.macBlock = true;
//     new printframe();
      new printframe(this);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   }
   class printframe
//startPR2004-10-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         extends JDialog implements Printable{
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     String h1a = u.gettext("printing","footer");
     boolean exitloop=false;
     boolean repeat=false;
     Image im;
     ScrollPane imdd;
     letterpos[]  saveb = new letterpos[b.length];
     ww savewordlist1[] = new ww[wordlist1.length];
     String listwords[];
     short savebtot = btot,savewtot=wtot;
     int savech = cheight;
     int savecw = cwidth;
     int saveacross = across;
     int savedown = down;
//startPR2004-08-27^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       // was JFrame
       JDialog thisf = this;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     Dimension di;
     int w,h,xx,yy;
     int x,y,x1,y1,x2,y2,fsiz;
     short i,j,k;
     Date d = new Date();
     Graphics g;
     String h1,h2;
     Canvas imd;
     Font f1,f2,f3;
     FontMetrics m1,m2,m3;
//startPR2004-10-06^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      PageFormat pageFormat;
      PrinterJob pJob;
      PageFormat tempPF;
      boolean shouldPrint = false;
       /**
        * allows skipping of the buffer's graphics context during mac printing
        */
       private int lastPage = -1;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2004-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//     printframe() {
         printframe(Dialog owner){
           super(owner);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2004-08-27^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
           this.setResizable(false);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        System.arraycopy(b,0,saveb,0,b.length);
        System.arraycopy(wordlist1,0,savewordlist1,0,wordlist1.length);
        word ww[] = wordlist.getNormalWordlist();
        listwords = new String[ww.length];
        for(i=0;i<ww.length;++i) listwords[i] = ww[i].v();
        gamePanel.currgame.setEnabled(false);
//startPR2004-10-06^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//        Dimension di = pj.getPageDimension();
          pJob = PrinterJob.getPrinterJob();
          pJob.setJobName(u.gettext("printing","crossword"));
          pageFormat = pJob.defaultPage();
          pageFormat.setOrientation(pageFormat.PORTRAIT);
          pJob.setPrintable(this, pageFormat);
          if (pJob.printDialog()) {
              shouldPrint = true;
          }
          else{
              restore();
              dispose();
              gamePanel.currgame.setEnabled(true);
              u.macBlock = false;
              return;
          }
          int tempw = (int) pageFormat.getWidth();
          int temph = (int) pageFormat.getHeight();
          di = new Dimension((int) pageFormat.getWidth(),
                              (int) pageFormat.getHeight());
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        im = sharkStartFrame.mainFrame.createImage(di.width,di.height);
        g=im.getGraphics();
//startPR2004-10-06^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        w = (int)pageFormat.getImageableWidth();
        h = (int)pageFormat.getImageableHeight();
        xx = (tempw - w)/2;
        yy = (temph - h)/2;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        String ss;
        BorderLayout layout0 = new BorderLayout();
        GridBagLayout layout1 = new GridBagLayout();
        GridBagLayout layout2 = new GridBagLayout();
        GridBagConstraints grid = new GridBagConstraints();

        h1 = u.gettext("printing","header",spellchange.spellchange(rgame.topicName));

        setTitle(u.gettext("printing","preview"));
        getContentPane().setLayout(layout1);
        setBounds(sharkStartFrame.mainFrame.getBounds());
        imdd = new ScrollPane();
        imd = new Canvas() {
            public synchronized void paint(Graphics g) {
              super.paint(g);
              g.drawImage(im,0,0,null);
           }
        };
        imd.setBackground(Color.white);
        imdd.add(imd);
        grid.gridx = 0;
        grid.gridy = -1;
        grid.weighty = 1;
        grid.weightx = 1;
        grid.fill = GridBagConstraints.VERTICAL;
        getContentPane().add(imdd,grid);
        Panel pp = new Panel();
//startPR2004-10-06^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//        Button ok = new Button("   "+u.gettext("printing","OK")+"   ");
          JButton ok = new JButton("   "+u.gettext("printing","OK")+"   ");
          if(!shark.macOS)
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        ok.setBackground(Color.orange);
        ok.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
//startPR2004-10-06^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//               Dimension di = pj.getPageDimension();
//               int hi = di.height;
//               int wi = di.width;
//               g  = pj.getGraphics();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
               fillimage(false);
//startPR2004-10-06^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//               g.dispose();
//               pj.end();
//               pj.finalize();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
               restore();
               thisf.dispose();
               gamePanel.currgame.setEnabled(true);
//startPR2004-10-06^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
               u.macBlock = false;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            }
        });
//startPR2004-10-06^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//        Button bad = new Button(u.gettext("printing","change"));
          JButton bad = new JButton(u.gettext("printing","change"));
          if(!shark.macOS)
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        bad.setBackground(Color.orange);
        bad.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               fillimage(true);
            }
        });
//startPR2004-10-06^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   //       Button cancel = new Button(u.gettext("printing","cancel"));
          JButton cancel = new JButton(u.gettext("printing","cancel"));
          if(!shark.macOS)
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        cancel.setBackground(Color.orange);
        cancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               restore();
               dispose();
               gamePanel.currgame.setEnabled(true);
//startPR2004-10-06^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
               u.macBlock = false;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            }
        });
        pp.setLayout(layout2);
        grid.gridx = 0;
        grid.gridy = -1;
        grid.fill = GridBagConstraints.NONE;
        pp.add(ok,grid);
        pp.add(bad,grid);
        pp.add(cancel,grid);
        //imdd.setSize(sharkStartFrame.mainFrame.getSize().width*7/8,
        //   sharkStartFrame.mainFrame.getSize().height);
        imdd.setSize(di.width,sharkStartFrame.mainFrame.getSize().height);
        imd.setSize(di.width,di.height);
        grid.gridx = 1;
        grid.gridy = 0;
        grid.fill = GridBagConstraints.BOTH;
        getContentPane().add(pp,grid);
//startPR2004-10-06^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//        fillimage(true);
          setVisible(true);
          fillimage(true);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        addWindowListener(new java.awt.event.WindowAdapter() {
          public void windowClosing(WindowEvent e) {
              restore();
              gamePanel.currgame.setEnabled(true);
//startPR2004-10-06^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                  u.macBlock = false;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          }
        });
     }
 //startPR2004-10-06^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      public int print(Graphics g, PageFormat f, int a) throws PrinterException {
        g.translate((int)f.getImageableX(), (int) f.getImageableY());

        // if reached end of print
        if (a > 0) {
          return NO_SUCH_PAGE;
        }
        // allows skipping of the buffer's graphics context
        if (a != lastPage) {
          lastPage = a;
          return Printable.PAGE_EXISTS;
        }

        short number,numbers[];
        int pyy=0;
        int pxx=0;
        int pw=w;
        int ph=h;
          int px=0;
          int py=0;
         g.setColor(Color.white);
         g.fillRect(0,0,(int)f.getImageableWidth(),(int)f.getImageableHeight());
         across = maxx(b,btot);
         down = maxy(b,btot);
         f1 = sizeFont(g,new String[]{h1},pw,ph/20);
         m1 = g.getFontMetrics();

                       // font2 for numbers in square
         f2 = sizeFont(g,new String[]{"1"},pw/across, ph/3/down/2);
         m2 = g.getFontMetrics();

                       //font3 for clues
         String clues[] = new String[wordlist1.length];
         for(i=0;i<wordlist1.length;++i) {
            clues[i] = new String();
         }
         short cluetot =(short) s.length;
         int len = 0;
         letterpos lp;
         number = 0;
         numbers = new short[wordlist1.length];
         for(i=0;i<down;++i) { // assign crossword position nos
           for(j=0;j<across;++j) {
             if((lp = findb(j,i)) != null) {
                boolean gotone = false;
                for(k=0;k<wordlist1.length;++k) {
                   if(wordlist1[k].let == lp) {
                       if(!gotone) ++number;
                       numbers[k] = number;
                       gotone = true;
                   }
                }
             }
          }
         }
         boolean aresplits=false;
         for(i=0; i<s.length; ++i) {
//startPR2008-10-06^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//          if(s[i].type == sentence.SIMPLECLOZE) {
           if(s[i].type == sentence.SIMPLECLOZE || s[i].type == sentence.CLOZE) {
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            String first = null;
            int lastlinenum = 0;
            boolean hadsplit=false;
            for(j=0;j<s[i].segs.length;++j) {
               if(s[i].segs[j].line > lastlinenum) {
                  if(!hadsplit)    {clues[i] += " "; hadsplit=true;}
                  else {
                    aresplits = true;
                    clues[i] = clues[i] + "|     ";
                  }
                  lastlinenum = s[i].segs[j].line;
               }
               if(s[i].segs[j].firstchoice) {
                  clues[i] = clues[i] + underscores.substring(0,Math.min(underscores.length(),s[i].segs[j].val.length()*2));
                  for(k=0;k<wordlist1.length;++k) {
                     if(wordlist1[k].sentenceno == i && wordlist1[k].w.equalsIgnoreCase(s[i].segs[j].val)) {
                        if(first==null) {
                           first = String.valueOf(numbers[k]) + " " + (wordlist1[k].vert?downtext:acrosstext);
                           clues[i] = (wordlist1[k].vert?"V":"H") + String.valueOf(numbers[k]) + ". " + clues[i];
                        }
                        else {
                           if(cluetot<clues.length) clues[cluetot++] = (wordlist1[k].vert?"V":"H") + String.valueOf(numbers[k]) + ". "
                                   + "See " + first + ".";
                           clues[i] = clues[i] + "(" + String.valueOf(numbers[k]) + " " + (wordlist1[k].vert?downtext:acrosstext)+ ")";
                        }
                     }
                  }
               }
               else if(!s[i].segs[j].choice) {
                  clues[i] =  clues[i] +  s[i].segs[j].val;
               }
            }
         }
         }
         if(aresplits) {
           String clues2[] = new String[0];
           for(i=0; i<clues.length; ++i) {
             clues2 = u.addString(clues2,u.splitString(clues[i]));
           }
           clues=clues2;
         }

        // start drawing  -- put heading first
         g.setFont(f1);
         g.setColor(Color.black);
         g.drawString(h1,(pw - g.getFontMetrics(f1).stringWidth(h1))/2,py+g.getFontMetrics(f1).getMaxAscent());
         py += m1.getHeight();

                 // draw crossword ----------------------------------
         g.setFont(f2);
         int dy = ph/3/down;
         int dx = dy;
         int sy=py+dy,sx = pxx+(pw - dx*across)/2;
         g.setColor(new Color(196,196,196));   // fill holes
         for(i=0;i<down;++i) {
          py += dy;
          px = sx;
          for(j=0;j<across;++j, px+= dx) {
            if((lp=findb(j,i)) == null) {
               g.fillRect(px,py,dx,dy);
            }
          }
         }
         g.setColor(Color.black);
         for(i=0,py=sy-dy;i<down;++i) {
          py += dy;
          px = sx;
          for(j=0;j<across;++j, px+= dx) {
            if((lp=findb(j,i)) != null) {
               g.drawRect(px,py,dx,dy);
               for(k=0;k<wordlist1.length; ++k) {
                  if(wordlist1[k].let == lp) {
                     g.drawString(String.valueOf(numbers[k]), px+dx/16, py+dy/16+m2.getMaxAscent());
                     break;
                  }
               }
            }
          }
         }
         g.drawRect(sx,sy,dx*across,dy*down);
                  // draw clues -----------------------------
         py += dy*2;
         int gap = (ph-py)/(clues.length+4);
         f3 = sizeFont(g, clues, pw, gap*clues.length);
         m3 = g.getFontMetrics(f3);
         g.setFont(f3);

         int ii[] = new int[clues.length];
         for(i=0; i<clues.length; ++i) {
            if(clues[i].charAt(1) == ' ') {
                       ii[i] = ii[i-1] + 1;
                       continue;
            }
            if(clues[i].charAt(0) == 'V') ii[i] = 0x01000000;
            if(clues[i].charAt(2) != '.')  ii[i] += (clues[i].charAt(1)<<16 + clues[i].charAt(2))*8;
            else  ii[i] +=  clues[i].charAt(1)*8;
         }
         short o[] = u.getorder(ii);
         g.drawString(acrosstext.toUpperCase(),pxx,py);
         py += gap;
         boolean hadvert = false;
         for(i=0;i<clues.length;++i, py+=gap) {
          if(!hadvert && clues[o[i]].charAt(0) == 'V') {
            hadvert = true;
            g.drawString(downtext.toUpperCase(),pxx,py);
            py += gap;
          }
          g.drawString(clues[o[i]].substring(1),pxx,py);
         }
                // put words
         py += gap;
         int xxx = pxx;
         o = u.shuffle(u.select((short)words.length,(short)words.length));
         String s,s2;
         for(i=0;i<words.length;++i) {
           s = words[o[i]];
           if((j=u.findString(listwords,s))>=0) s = listwords[j];
           s2 = "/"+s;
           if(xxx + m3.stringWidth(s2) > pw) {xxx=pxx;py +=gap;}
           if(xxx>pxx) {
               g.drawString(s2,xxx,py);
                xxx += m3.stringWidth(s2);
           }
           else  {
              g.drawString(s,xxx,py);
              xxx += m3.stringWidth(s);
            }
         }
                // put bottom line

         Font f4 = sizeFont(g,new String[]{h1a},pw,ph/50);
         g.drawString(h1a,pxx, (int)f.getImageableHeight() - g.getFontMetrics(f4).getMaxDescent());
        return Printable.PAGE_EXISTS;
      }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     void restore() {
               b = new letterpos[saveb.length];
               System.arraycopy(saveb,0,b,0,saveb.length);
               wordlist1 = new ww[savewordlist1.length];
               System.arraycopy(savewordlist1,0,wordlist1,0,savewordlist1.length);
               btot = savebtot;
               wtot = savewtot;
               cheight = savech;
               cwidth = savecw;
               across = saveacross;
               down = savedown;
               gamePanel.invalidate();
     }
     void  fillimage(boolean change) {
       short number,numbers[];
       x=0;y=yy;
        g.setColor(Color.white);
        g.fillRect(0,0,im.getWidth(null),im.getHeight(null));
        if(change) printsetup(w,h/2);
        across = maxx(b,btot);
        down = maxy(b,btot);
        f1 = sizeFont(g,new String[]{h1},w,h/20);
        m1 = g.getFontMetrics();
                      // font2 for numbers in square
        f2 = sizeFont(g,new String[]{"1"},w/across, h/3/down/2);
        m2 = g.getFontMetrics();

                      //font3 for clues
        String clues[] = new String[wordlist1.length];
        for(i=0;i<wordlist1.length;++i) {
           clues[i] = new String();
        }
         short cluetot =(short) s.length;
        int len = 0;
        letterpos lp;
        number = 0;
        numbers = new short[wordlist1.length];
        for(i=0;i<down;++i) { // assign crossword position nos
          for(j=0;j<across;++j) {
            if((lp = findb(j,i)) != null) {
               boolean gotone = false;
               for(k=0;k<wordlist1.length;++k) {
                  if(wordlist1[k].let == lp) {
                      if(!gotone) ++number;
                      numbers[k] = number;
                      gotone = true;
                  }
               }
            }
         }
        }
        boolean aresplits=false, nomainlist = false;
        for(i=0; i<s.length; ++i) {
         if(s[i].type == sentence.SIMPLECLOZE || s[i].type == sentence.CLOZE) {
           String first = null;
           int lastlinenum = 0;
           boolean hadsplit=false;
           String cluelist[] = null;
           for(j=0;j<s[i].segs.length;++j) {
              if(s[i].segs[j].line > lastlinenum) {
                 if(!hadsplit)    {clues[i] += " "; hadsplit=true;}
                 else {
                   aresplits = true;
                   clues[i] = clues[i] + "|     ";
                 }
                 lastlinenum = s[i].segs[j].line;
              }
              if(s[i].segs[j].firstchoice) {
                 clues[i] = clues[i] + underscores.substring(0,Math.min(underscores.length(),s[i].segs[j].val.length()*2));
                 for(k=0;k<wordlist1.length;++k) {
                    if(wordlist1[k].sentenceno == i && wordlist1[k].w.equalsIgnoreCase(s[i].segs[j].val)) {
                       if(first==null) {
                          first = String.valueOf(numbers[k]) + " " + (wordlist1[k].vert?downtext:acrosstext);
                          clues[i] = (wordlist1[k].vert?"V":"H") + String.valueOf(numbers[k]) + ". " + clues[i];
                       }
                       else {
                          if(cluetot<clues.length) clues[cluetot++] = (wordlist1[k].vert?"V":"H") + String.valueOf(numbers[k]) + ". "
                                  + "See " + first + ".";
                          clues[i] = clues[i] + "(" + String.valueOf(numbers[k]) + " " + (wordlist1[k].vert?downtext:acrosstext)+ ")";
                       }
                    }
                 }
              }
              else if(!s[i].segs[j].choice) {
                 if(cluelist != null) {
                   clues[i] = clues[i] + '(' + u.combineString(u.shuffle(cluelist),"/") + ')';
                   cluelist = null;
                 }
                 clues[i] =  clues[i] +  s[i].segs[j].val;
             }
              if(s[i].type == sentence.CLOZE && s[i].segs[j].choice) {
                cluelist = u.addString(cluelist, s[i].segs[j].val);
                nomainlist = true;
              }
           }
           if(cluelist != null) {
              clues[i] = clues[i] + '(' + u.combineString(u.shuffle(cluelist),"/") + ')';
              cluelist = null;
           }
         }
        }
        if(aresplits) {
          String clues2[] = new String[0];
          for(i=0; i<clues.length; ++i) {
            clues2 = u.addString(clues2,u.splitString(clues[i]));
          }
          clues=clues2;
        }

       // start drawing  -- put heading first

        g.setFont(f1);
        g.setColor(Color.black);
        g.drawString(h1,xx+(w - m1.stringWidth(h1))/2,y);
        y += m1.getHeight();

                // draw crossword ----------------------------------
        g.setFont(f2);
        int dy = h/3/down;
        int dx = dy;
        int sy=y+dy,sx = xx+(w - dx*across)/2;
        g.setColor(new Color(196,196,196));   // fill holes
        for(i=0;i<down;++i) {
         y += dy;
         x = sx;
         for(j=0;j<across;++j, x+= dx) {
           if((lp=findb(j,i)) == null) {
              g.fillRect(x,y,dx,dy);
           }
         }
        }
        g.setColor(Color.black);
        for(i=0,y=sy-dy;i<down;++i) {
         y += dy;
         x = sx;
         for(j=0;j<across;++j, x+= dx) {
           if((lp=findb(j,i)) != null) {
              g.drawRect(x,y,dx,dy);
              for(k=0;k<wordlist1.length; ++k) {
                 if(wordlist1[k].let == lp) {
                    g.drawString(String.valueOf(numbers[k]), x+dx/16, y+dy/16+m2.getMaxAscent());
                    break;
                 }
              }
           }
         }
        }
        g.drawRect(sx,sy,dx*across,dy*down);
                 // draw clues -----------------------------
        y += dy*2;
        int gap = (h-y)/(clues.length+4);
        f3 = sizeFont(g, clues, w, gap*clues.length);
        m3 = g.getFontMetrics(f3);
        g.setFont(f3);

        int ii[] = new int[clues.length];
        for(i=0; i<clues.length; ++i) {
           if(clues[i].charAt(1) == ' ') {
                      ii[i] = ii[i-1] + 1;
                      continue;
           }
           if(clues[i].charAt(0) == 'V') ii[i] = 0x01000000;
           if(clues[i].charAt(2) != '.')  ii[i] += (clues[i].charAt(1)<<16 + clues[i].charAt(2))*8;
           else  ii[i] +=  clues[i].charAt(1)*8;
        }
        short o[] = u.getorder(ii);
        g.drawString(acrosstext.toUpperCase(),xx,y);
        y += gap;
        boolean hadvert = false;
        for(i=0;i<clues.length;++i, y+=gap) {
         if(!hadvert && clues[o[i]].charAt(0) == 'V') {
           hadvert = true;
           g.drawString(downtext.toUpperCase(),xx,y);
           y += gap;
         }
         g.drawString(clues[o[i]].substring(1),xx,y);
        }
               // put words
        y += gap;
        int xxx = xx;
        o = u.shuffle(u.select((short)words.length,(short)words.length));
        String s,s2;
        if(!nomainlist) for(i=0;i<words.length;++i) {
          s = words[o[i]];
          if((j=u.findString(listwords,s))>=0) s = listwords[j];
          s2 = "/"+s;
          if(xxx + m3.stringWidth(s2) > w) {xxx=xx;y +=gap;}
          if(xxx>xx) {
              g.drawString(s2,xxx,y);
               xxx += m3.stringWidth(s2);
          }
          else  {
             g.drawString(s,xxx,y);
             xxx += m3.stringWidth(s);
           }
        }
               // put bottom line

        Font f4 = sizeFont(g,new String[]{h1a},w,h/50);
        FontMetrics m4 = g.getFontMetrics();
        g.drawString(h1a,xx, yy + h - m4.getHeight());
        thisf.validate();
        imd.paint(imd.getGraphics());
//startPR2004-10-06^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          if((!change)&&(shouldPrint)){
            try {
              pJob.print();
            }
            catch (Throwable t) {}
          }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      }
    }
}
