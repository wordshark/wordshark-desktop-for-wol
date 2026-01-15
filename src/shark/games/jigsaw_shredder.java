package shark.games;

import java.awt.*;

import shark.*;
import shark.sharkGame.*;
import shark.spokenWord.*;

public class jigsaw_shredder extends sharkGame {//SS 03/12/05
  static final short MAXWORDS = 6;
  static final short MAXWORDSONSETRIME = 10;
  static final short MINWORDS = 1;
  short MAXSEGSINWORDS = 16;
  piece p[];
  shape s[];
  short ptot,stot,curr,starts,ends;
  int col1tot,col2tot,col1width,col2width,shapeheight,textheight,jigheight;
  int wordWidth,wordHeight,extray;
  boolean split,free,phonics;
  int xt1, xt2, xt3, xb1, xb2, xb3, y1, y2, y3, y4, y5, y6, y7, pad;
  word words[];
  String wordbuild[][];
  String currbuild[];
  short nextbuild;
  short wtot;
  short shapeorder[];
  Color jigColor,pickupColor;
  String sofar, sofar2, sofar3;
  int currendx, currendy;
  movingpiece piece1;
  piece pickedupPiece;
  boolean gotall, gotwholelist;
  grinder gr;
  showpicture showpic;
//  boolean fl = sharkStartFrame.currPlayTopic.fl && !specialprivateoff;
  boolean magice;
  char sep = phonicsw?u.phonicsplit:'/';
  long nextword;
//startPR2008-08-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  Color transparentbackground = new Color(background.getRed(),background.getGreen(),background.getBlue(),0);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  public boolean showpicture = !specialprivateon;

  public jigsaw_shredder() {
    int c1,c2,c3,cm;
    if(rgame.getParm("phonicsw") != null) {
        phonics = true;
        split = true;
        MAXSEGSINWORDS = 34;
        help(blended? "help_jigsaw1ph":"help_jigsaw1");
    }
    else if(rgame.getParm("split") != null) {
       split = true;
        help("help_jigsaw1");
    }
     else if(rgame.getParm("free") != null) {
        free = true;
        help("help_jigsaw1a");
     }
     else  help("help_jigsaw1");

     pictureat = 3;
    errors = true;
    gamescore1 = true;
//    if(!free && (!fl  || specialprivateon)) peeps = true;
//    if(!free && (!fl  || specialprivateon)) listen= true;
//    if(!free && (!fl  || specialprivateon)) peep = true;

//    if(!free && (!fl || specialprivate)) peeps = true;
//    if(!free && (!fl || specialprivate)) listen= true;
//    if(!free && (!fl || specialprivate))peep = true;
    if(!free) peeps = true;
    if(!free) listen= true;
    if(!free) peep = true;

    cm = u.rand(3);
    if(cm==0) c1 = 180; else c1 = u.rand(64);
    if(cm==1) c2 = 180; else c2 = u.rand(64);
    if(cm==2) c3 = 180; else c3 = u.rand(64);
    jigColor = new Color(c1,c2,c3);
    if(cm==0) pickupColor = new Color(255,
                            jigColor.getGreen(),
                            jigColor.getBlue());
    else if(cm==1) pickupColor = new Color(jigColor.getRed(),
                            255,
                            jigColor.getBlue());
    else  pickupColor = new Color(jigColor.getRed(),
                            jigColor.getGreen(),255);
    if(!setupParts((short)0)) return;
    gamePanel.dontstart = true;
    buildTopPanel();
    sizeFont();
    buildSplit();
    if(!phonics) addShapes();
    if(free) {
       int maxw=0;
       for(short i=0;i<ptot;++i) {
          maxw = Math.max(maxw, pwidth(p[i]));
       }
       gr = new grinder(Math.max(col1width+col2width,maxw*5/3),
                  mover.HEIGHT/2,gamePanel, 0, mover.HEIGHT/2, this);
    }
    addPieces(false);
    gamePanel.dontstart = false;
    if(!free) {
       currword =  words[0];
       currbuild = wordbuild[0];
       nextbuild = 0;
       inactivate();
 //      if(!fl || specialprivate) new  spokenWord.whenfree(600) {
       new  spokenWord.whenfree(600) {
         public void action() {
           if(phonics) spokenWord.sayPhonicsWhole(words[0]);
           else words[0].say();
         }
       };
       if(showpicture)
           showpic = new showpicture();
    }
  }
  public void windowclose() {
     if(gr != null) gr.stop();
     super.windowclose();
  }
   public void afterDraw(long t){
     if(nextword != 0 && gtime>nextword) {
        nextword = 0;
        gamePanel.showSprite = true;
        if(free && freeend() || !free && ++curr >= wtot) {
           score(gametot1);
           exit(1000);
        }
        else {

            if(delayedflip && !completed){
               flip();
            }
            sofar = null;
            sofar2 = sofar3 = null;
            gotall = false;
            currword = null;
            for(short i=0;i<ptot;++i) {
               if(p[i].pickedup
                  && (!free
                     || needed(p[i]))) {
                  addMover(p[i],p[i].x,p[i].y);
                  p[i].pickedup = false;
               }
            }
         }
         if(!free)  {
            addPieces(true); // reposition them
            if(currword!=null) return;
            currword =  words[curr];
//            if(!fl || specialprivate) {
              if(phonics) spokenWord.sayPhonicsWhole(currword);
              else currword.say();
//          }
            currbuild = wordbuild[curr];
            nextbuild = 0;
            help(blended? "help_jigsaw1ph":"help_jigsaw1");
            inactivate();
            u.pause(1000);
            if(showpicture)
                showpic = new showpicture();
            gamePanel.clearall();
         }
         else help("help_jigsaw1a");
     }
     if(free && gr != null) gr.resize();
   };
   //--------------------------------------------------------------------
   public void listen1() {
     if(phonics && currword != null) {
       spokenWord.sayPhonicsWhole(currword);
       gamePanel.startrunning();
       gamePanel.requestFocus();
     }
     else super.listen1();
   }
  //-----------------------------------------------------------
  int stringwidth(String s) {
     return metrics.stringWidth(s)*mover.WIDTH/screenwidth;
  }
  //------------------------------------------------------------
  int getheight() {
     return metrics.getHeight()*mover.HEIGHT/screenheight;
  }
  //------------------------------------------------------------
  void buildSplit() {
    Graphics g = getGraphics();
    g.setFont(wordfont);
    jigheight = textheight*4/3;
    extray = (jigheight - textheight)/2;
    int midline = words[0].getMidline(g)*mover.HEIGHT/screenheight + extray;
    int baseline = metrics.getMaxAscent()*mover.HEIGHT/screenheight + extray;
    int begintop = pad*4, beginbottom = pad*4;
    int freetop=midline, freebottom = jigheight-baseline, minwidth = pad*4;

    y6 = jigheight;
    y3 = y6/4;
    y2 = midline *3/4;
    y1 = y2/2;
    y4 = y6 - (y6-baseline) *3/4;
    y5 = (y4+y6)/2;
    for(short i=0; i < ptot; ++i) {
       if(!p[i].start) {
          beginbottom = Math.min(beginbottom,words[0].beginbottom(g,p[i].val)* mover.WIDTH/screenwidth);
          begintop = Math.min(begintop,words[0].begintop(g,p[i].val)* mover.WIDTH/screenwidth);
          minwidth =  Math.min(minwidth, metrics.stringWidth(p[i].val)*mover.WIDTH/screenwidth);
       }
    }
    if(begintop < pad) {
       for(short i=0; i < ptot; ++i) {
          if(!p[i].start) {
             freetop = Math.min(freetop,words[0].freetop(g,p[i].val)* mover.HEIGHT/screenheight);
          }
       }
       begintop = minwidth;
       y2 = freetop-2+extray;
       y1 = y2/2;
    }
    if(begintop > pad*2) begintop = pad*2 + u.rand(begintop - pad*2);
    xt1 = begintop*2/5;
    xt2 = begintop*4/5;
    xt3 = begintop;

    if(beginbottom < pad) {
       for(short i=0; i < ptot; ++i) {
          if(!p[i].start) {
             freebottom = Math.min(freebottom,words[0].freebottom(g,p[i].val)* mover.HEIGHT/screenheight);
          }
       }
       beginbottom = minwidth;
       y4 = y6-freebottom+2-extray;
       y5 = (y6+y4)/2;
    }

    if(beginbottom > pad*2) beginbottom = pad*2 + u.rand(beginbottom - pad*2);
    xb1 = beginbottom*2/5;
    xb2 = beginbottom*4/5;
    xb3 = beginbottom;
  }
  //------------------------------------------------------------
                   // add splitter line for onset & rime
  void drawSplitr(Polygon po, int x, int y) {
     Polygon po2 = new Polygon();
     drawSplit(po2,x,y);
     for(int i=po2.npoints-1;i>=0;--i) {
        po.addPoint(po2.xpoints[i],po2.ypoints[i]);
     }
  }
  //------------------------------------------------------------
                   // add splitter line for onset & rime
  void drawSplit(Polygon po, int x, int y) {
     int xxb1 = x + xb1*screenwidth/mover.WIDTH;
     int xxb2 = x + xb2*screenwidth/mover.WIDTH;
     int xxb3 = x + xb3*screenwidth/mover.WIDTH;
     int xxt1 = x + xt1*screenwidth/mover.WIDTH;
     int xxt2 = x + xt2*screenwidth/mover.WIDTH;
     int xxt3 = x + xt3*screenwidth/mover.WIDTH;
     int yy1 = y + y1*screenheight/mover.HEIGHT;
     int yy2 = y + y2*screenheight/mover.HEIGHT;
     int yy3 = y + y3*screenheight/mover.HEIGHT;
     int yy4 = y + y4*screenheight/mover.HEIGHT;
     int yy5 = y + y5*screenheight/mover.HEIGHT;
     int yy6 = y + y6*screenheight/mover.HEIGHT;

     yy1 = Math.max(3,yy1);
     yy5 = Math.min(yy5, yy6-2);

     if(xxt1 == xxt3) {
        po.addPoint(yy1, y);
        po.addPoint(x, yy1);
        po.addPoint(x, yy2);
     }
     else {
        u.drawcurve(po, xxt3, y, Math.PI/2, xxt2, yy2,Math.PI);
        u.drawcurve(po, xxt2, yy2, Math.PI, xxt1, yy1, Math.PI);
        u.drawcurve(po, xxt1, yy1, Math.PI, x,yy2, Math.PI/2);
     }
     po.addPoint(x, yy4);
     if(xxb1 == xxb3) {
        po.addPoint(x, yy5);
        po.addPoint((yy6-yy5), yy6);
     }
     else {
        u.drawcurve(po, x, yy4, Math.PI/2,xxb1,yy5, (double)0);
        u.drawcurve(po, xxb1, yy5, (double)0, xxb2, yy4, (double)0);
        u.drawcurve(po, xxb2, yy4, (double)0, xxb3, yy6, Math.PI/2);
     }
  }
  //------------------------------------------------------------
  void addShapes() {
     word ws;
     int x=pad*2, y = textheight/2;
     s = new shape[wtot];
     for(short i = 0; i<wtot; ++i) {
        ws = words[shapeorder[i]];
        s[stot] = new shape(ws, stringwidth(ws.v())+2,getheight());
        addMover(s[stot++], x, y);
        if( i == col1tot-1) {y = textheight/2;  x = col1width+pad*2; }
        else y += shapeheight;
     }
  }
  //------------------------------------------------------------
  boolean setupParts(short exclude) {
     String s,part;
     short splitat = 1, i,j,k;
     word words1[];
     int wordtot1=0;
      ptot=wtot=0;
      starts=ends=0;
      if(!split) {
        if(gotwholelist) {
          if(free)
           words1 = u.shuffle(sharkStartFrame.mainFrame.wordTree.getBadList2());
          else
           words1 =  u.shuffle(sharkStartFrame.currPlayTopic.getAllWords(true));
          words = new word[words1.length];
        }
        else {
           words = new word[rgame.w.length];
           words1 = rgame.w;
        }
        wordtot1 = words1.length;
        p = new piece[(i=(short)(words1.length))*2];
        wordbuild = new String[i][];
     }
     else {
        word wd[] = sharkStartFrame.mainFrame.wordTree.getvisiblewords();
        short  o[] = u.shuffle(u.select((short)wd.length,(short)wd.length));
        if(sharkStartFrame.mainFrame.currPlayTopic.inorder)  o = u.select((short)wd.length,(short)wd.length);
        words1 = new word[Math.min(MAXWORDS, o.length)];
        short segsofar = 0;
        for(i=0;i<o.length && wordtot1<MAXWORDS && segsofar < MAXSEGSINWORDS; ++i) {
          if(!phonicsw && wd[o[i]].value.indexOf('/') >= 0) {
                 words1[wordtot1++] = wd[o[i]];
                 segsofar += wd[o[i]].segtot();
          }
          else if(phonicsw && wd[o[i]].value.indexOf(u.phonicsplit) >= 0 && !wd[o[i]].onephoneme) {
                 words1[wordtot1++] = wd[o[i]];
                 segsofar += wd[o[i]].phsegtot();
          }
        }
        if(wordtot1 < MINWORDS && segsofar < MAXSEGSINWORDS) {
           fatalerror(rgame.getParm("notenough"));
           return false;
        }
        for(i=0,j=0;i< wordtot1;++i) {
           part = phonicsw?words1[i].phsplit():words1[i].vsplit();
           ++j;
           while((k = (short)part.indexOf(sep)) > 0) {
              part = part.substring(k+1);
              ++j;
           }
        }
        p = new piece[j];
        wordbuild = new String[i][];
        words = new word[wordtot1];
     }
     for(i=0;i< wordtot1;++i) {
        if(!split)  {
           s = words1[i].v();
           splitat = 0;
 //          if(s.length()<2 || u.vowels.indexOf(s.charAt(0)) >= 0) continue;
           if(s.length()<2 || u.vowels.indexOf(s.charAt(0)) >= 0 || u.syllabletot(s)!=1) continue;
           if(s.substring(0,2).equalsIgnoreCase("qu")){
              splitat = 2;
           }
           else for(j=1;j<Math.min(5,s.length());++j) {
              if(u.vowelsy.indexOf(s.charAt(j))>=0) {
                 splitat = j;
                 break;
              }
           }
           if(splitat != 0) {
              if(!words1[i].bad) {
                 words[wtot] = words1[i];
                 wordbuild[wtot++] = new String[] {s.substring(0,splitat),s.substring(splitat)};
              }
              part = s.substring(0, splitat);
              p[ptot++] = new piece(part,true,false,jigColor);
              ++starts;
              part = s.substring(splitat);
              p[ptot++] = new piece(part,false,true,jigColor);
              ++ends;
              if(gotwholelist && wtot >  (MAXWORDSONSETRIME-1)) break;
           }
        }
        else {             // split as marked
           short isword = -1;
           s = phonicsw?words1[i].phsplit():words1[i].vsplit();
           if((splitat = (short)s.indexOf(sep)) <  0) splitat = (short)s.length();
           if(!words1[i].bad) {
              words[isword=wtot] = words1[i];
              wordbuild[wtot++] = new String[] {s.substring(0,splitat)};
           }
           part = s.substring(0,splitat);
           if(splitat < s.length())      s = s.substring(splitat+1);
           else                          s = "";
           p[ptot++] = new piece(part,true,false,jigColor);
           ++starts;
           if (words[i].ismagicsyl(2)) {
             magice = true;
             p[ptot-1].connectto = new piece("e", false, s.length()<=5, jigColor);
           }
           int sylpos = 1;
           boolean justlostmagic = false;
           if(s.length()>0) {
             while ( (splitat = (short) s.indexOf(sep)) > 0) {
               part = s.substring(0, splitat);
               if (isword >= 0)
                 wordbuild[isword] = u.addString(wordbuild[isword], part);
               s = s.substring(splitat + 1);
               p[ptot] = new piece(part, false, false, jigColor);
               if (words[i].ismagicsyl(sylpos+2)) {
                 magice = true;
                 p[ptot].connectto = new piece("e", false, s.length()<=5, jigColor);
               }
               else if(words[i].ismagicsyl(sylpos+1)) {
                        p[ptot - 1].connectskip = p[ptot];
               }
               else if(words[i].ismagicsyl(sylpos) && !justlostmagic) {
                  justlostmagic = true;
                  if (isword >= 0)
                     wordbuild[isword] = u.removeString(wordbuild[isword], wordbuild[isword].length-1);
                  --sylpos;
               }
               else justlostmagic = false;
               ++sylpos;
               ++ptot;
             }
             p[ptot++] = new piece(s, false, true, jigColor);
             ++ends;
           }
           if(isword>=0) wordbuild[isword] = u.addString(wordbuild[isword],s);
        }
     }
     for(i=0; i<ptot; ++i) {  // eliminate dups
       for(j=(short)(i+1); j<ptot; ++j) {
         if (p[j].val.equals(p[i].val) && p[j].end == p[i].end &&
             p[j].start == p[i].start
             && canremove(j)
             && (p[i].connectto == null && p[j].connectto == null
                 || p[i].connectto != null && p[j].connectto != null && p[i].connectto.val.equals(p[j].connectto.val))) {
             if(p[j].end) --ends;
             if(p[j].start) --starts;
             if(--ptot>j) System.arraycopy(p,j+1,p,j,ptot-j);
               --j;
         }
       }
     }

     if(free  && ends > 1 && !gotwholelist
             && sharkStartFrame.mainFrame.wordTree.canextend
             && !sharkStartFrame.mainFrame.wordTree.shuffled
                   && !sharkStartFrame.mainFrame.wordTree.wholeextended) {
         gotwholelist = true;
         return setupParts((short)0);
     }
     if(!split && !free && !gotwholelist
          && sharkStartFrame.mainFrame.wordTree.canextend
          && (wtot < 6
                 && (sharkStartFrame.mainFrame.wordTree.shuffled
                    || sharkStartFrame.mainFrame.wordTree.wholeextended)
             || wtot < 4 && !sharkStartFrame.mainFrame.wordTree.shuffled
                   && !sharkStartFrame.mainFrame.wordTree.wholeextended )) {
         gotwholelist = true;
         return setupParts((short)0);
     }
     if(ends>1 && free) {   // exclude starts which don't fit all ends
        if(exclude > 0) {
          k=exclude;
          short oo[] = u.shuffle(u.select(ptot,ptot));
          for(i=0;i<ptot && k>0;++i) {
            j = oo[i];
            if(p[j].end) {
                if(--ptot>j)   System.arraycopy(p,j+1,p,j,ptot-j);
                oo = u.shuffle(u.select(ptot,ptot));
                --k;
                --ends;
                i=-1;
            }
          }
        }
        loop1:for(i=0;i<ptot;++i) {
          if(p[i].start) {
            loop2:for(j=0;j<ptot;++j) {
                if(!p[j].end) continue;
                s = p[i].val + p[j].val;
                for(k=0;k<wordtot1;++k) {
                   if(words1[k].v().equals(s)) {continue loop2;}
                }
                if(--ptot>i) System.arraycopy(p,i+1,p,i,ptot-i);
               --i;
               --starts;
               continue loop1;
            }
          }
        }
        loop1a:for(k=0;k<wtot;++k) {  // exclude unwanted words
           for(i=0;i<ptot;++i) {
             if(p[i].start) {
                for(j=0;j<ptot;++j) {
                   if(!p[j].end) continue;
                   s = p[i].val + p[j].val;
                   if(words[k].v().equals(s)) continue loop1a;
                }
             }
           }
           if(--wtot>k) {
               System.arraycopy(words,k+1,words,k,wtot-k);
               System.arraycopy(wordbuild,k+1,wordbuild,k,wtot-k);
           }
           --k;
        }
        if(wtot == 0 || wtot > 16) {
           if(ends > 1) setupParts((short)(exclude+1));
           else fatalerror("Unable to play this game");
        }
     }
     shapeorder = u.shuffle(u.select(wtot,wtot));
     return true;
  }
  //-------------------------------------------------------------
  boolean canremove(int test) {   // returns true if there are already enough pieces for any word
    int i;
    int got = 0,max = 0,sofar = 0;
    if(p[test].start || p[test].end) return true;
    for(i=0;i<ptot;++i) {
      if(p[i].start || p[i].end) got = 0;
      else if(p[i].val.equals(p[test].val)) {
          ++got;
          max = Math.max(got, max);
          if (i < test)   ++sofar;
      }
    }
    return (sofar >= max) ;
  }
  void inactivate_all() {
    int i;
    for(i=0;i<ptot;++i) {
     p[i].inactive = true;
    }
  }
  //--------------------------------- ---------------------------
  void inactivate() {
    if(!free && split && !phonics) {
        int i,j=0;
        for(i=0;i<nextbuild;++i) j += currbuild[i].length();
        String v = currword.v().substring(j);
        String next = currbuild[nextbuild];
        boolean start = (nextbuild==0);
        boolean end = currword.v().equals(v+next);
        for(i=0;i<ptot;++i) {
           piece pp = p[i];
           pp.inactive = (!pp.val.equals(next) && u.startswith(v,pp.val) );  // rb 24/5/07
        }
        gamePanel.copyall = true;
     }
  }
  //------------------------------------------------------------
  void addPieces(boolean repeating) {
     int ystep = 1;
     int ytot = (mover.HEIGHT-jigheight)/ystep;
     int x1 = col1width + col2width+pad*6, i, x, y;
     int w,h;
     int x2 =  x1 + Math.max((mover.WIDTH-x1)/5,(mover.WIDTH-x1)*starts/ptot);
     int x3 =  mover.WIDTH - Math.max((mover.WIDTH-x1)/5,(mover.WIDTH-x1)*ends/ptot);
     for(i=0;i<ptot;++i) {                       //  rb 16/3/08  mmmm start
       h = p[i].h = jigheight;
       w = p[i].w = pwidth(p[i]);
     }                                            //  rb 16/3/08  mmmm end
     for(i=0;i<ptot;++i) {
          if(p[i].start
               && !fitpart(i,ystep,ytot*3/4,x1,x2)
               && !fitpart(i,ystep,ytot*3/4,x1,(x2+mover.WIDTH)/2)
               && !fitpart(i,ystep,ytot,x1,mover.WIDTH)
             || p[i].end
               && !fitpart(i,ystep,ytot,x3,mover.WIDTH)
               && !fitpart(i,ystep,ytot,(x1+mover.WIDTH)/2,mover.WIDTH)
               && !fitpart(i,ystep,ytot,x1,mover.WIDTH)
             || !p[i].end && !p[i].start
               && !fitpart(i,ystep,ytot,x2,x3)
               && !fitpart(i,ystep,ytot,(x1+x2)/2,(x3+mover.WIDTH)/2)
               && !fitpart(i,ystep,ytot,x1,mover.WIDTH)) {
           if(ystep ==1 ) {
              ystep = jigheight+mover.HEIGHT*2/screenheight;
              ytot = mover.HEIGHT/ystep;
              i = 0;
              continue;
           }
           else {
              forcePieces(x1);
              break;
           }
        }
     }
     if(repeating)   for(i=0;i<ptot;++i)  {p[i].ended = false; p[i].inactive = false;}
     else for(i=0;i<ptot;++i) addMover(p[i],p[i].tox,p[i].toy);
  }
  boolean fitpart(int pn, int ystep, int ytot, int x1, int x2) {
     short repeats=0;
     piece pp = p[pn];
     int i,x,y;
     outer: while(repeats < 500) {
        y = ystep * u.rand(ytot);
        x = x1 + u.rand(x2 - x1 - pp.w);
        if (free)  {
           if(x <= gr.tox + gr.w + pad
              && x + pp.w >= gr.tox - pad
              && y <= gr.toy + gr.h+1
              && y + pp.h >= gr.toy-6) continue;
        }
        for(i=0;i < pn; ++i) {
           if(x <= p[i].tox + p[i].w + wordWidth
              && x + pp.w >= p[i].tox - wordWidth
              && y <= p[i].toy + p[i].h+1
              && y + pp.h >= p[i].toy-1) {
             ++repeats;
             continue outer;
           }
        }
        pp.tox = x;
        pp.toy = y;
        return true;
     }
     return false;
  }
  void forcePieces(int xstart) {
     int ygap = mover.HEIGHT/screenheight;
     short i,percol = (short)(mover.WIDTH / (jigheight+ygap));
     int offset[] = new int[percol];
     piece pp;
     int y1=0,y2=0,y,x1;
     short pporder[] = u.shuffle(u.select(ptot,ptot));
     short porder[] = new short[pporder.length];
     short j = 0, k=(short)(ptot-1);
     ygap =  mover.HEIGHT/percol - jigheight;
     for(i=0;i<ptot;++i) {
        if(p[pporder[i]].start) porder[j++] = pporder[i];
        else if(p[pporder[i]].end) porder[k--] = pporder[i];
     }
     if(j<k) for(i=0;i<ptot;++i) {
       if(!p[pporder[i]].start && !p[pporder[i]].end)
                                 porder[j++] = pporder[i];
     }
     for(i=0;i<ptot;++i) {
        pp = p[porder[i]];
        if(i >= percol) {
           for(j=0,k=0; j<percol; ++j) {
              if(offset[j] < offset[k]) k = j;
           }
           pp.tox = xstart+offset[k];
           pp.toy = ygap/2+k*(jigheight+ygap);
           offset[k] += pp.w + pad;
        }
        else {
           pp.toy = ygap/2+i*(jigheight+ygap);
           pp.tox = xstart;
           offset[i]=pp.w+pad;
        }
     }
  }
  //-------------------------------------------------------------
  void setshapelist() {
     short i;
     int allowedheight;

     textheight = getheight();
     if(free) allowedheight =  (mover.HEIGHT-textheight) /2;
     else     allowedheight =  mover.HEIGHT -textheight;
     shapeheight = Math.max(textheight, allowedheight/wtot);
     col1tot = allowedheight/shapeheight;
     col2tot = wtot - col1tot;
     col1width = col2width = 0;

     for(i=0; i<wtot; ++i) {
        if(i < col1tot)
           col1width = Math.max(col1width , stringwidth(words[shapeorder[i]].v()));
        else
           col2width = Math.max(col2width , stringwidth(words[shapeorder[i]].v()));
     }
     col1width += pad*3;
     col2width *= 4/3;
  }
  //-------------------------------------------------------------
  public int pwidth(piece p) {
     int w = stringwidth(p.val);
     if(p.start) w += pad*2;
     if (!p.end) {
        if(y4 >= 0)   w += Math.max(xt3,xb3);
        else          w += pad*4;
        if(phonics) w += pad;
     }
     else w += pad*2;
     if(p.connectto != null) {
       w += stringwidth(p.connectskip.val) + stringwidth(p.connectto.val);
     }
     return w + (mover.WIDTH+screenwidth-1)/screenwidth;
  }
  //-------------------------------------------------------------
  public void sizeFont() {
     Toolkit t  = Toolkit.getDefaultToolkit();
     int i;
     FontMetrics m;
      wordfont = null;
      String s;
      int maxwidth = mover.WIDTH/4;
      int maxheight = mover.HEIGHT/6;
      long area;

      maxheight = Math.min(maxheight,mover.HEIGHT*2/(wtot+1));

      while(true) {
         wordfont = sizeFont(wordfont,words[0].v(), maxwidth, maxheight);
         m = getFontMetrics(wordfont);
         pad = m.charWidth('o')* mover.WIDTH/screenwidth/2;
         setshapelist();
         area = 0;
         for(i=0;i<ptot;++i){
            area += pwidth(p[i]) *  shapeheight * 4/3;
         }
         if(area*3/2 < (long)(mover.WIDTH-col1width-col2width-pad*6)*mover.HEIGHT)
                 break;
         --maxwidth;
     }
     wordWidth=0;
     for(i=0;i<words.length;++i) {
         if(words[i] != null) wordWidth = Math.max(wordWidth,m.stringWidth(words[i].v()+"  ")*mover.WIDTH/screenwidth);
     }
  }
  //--------------------------------------------------------------
  void cancel(String text) {
     error(text);
     gamescore(-1);
     noise.groan();
     gamePanel.drop(piece1);
     piece1.temp = true;
     piece1.moveto(pickedupPiece.x, pickedupPiece.y, 200);
     u.pause(200);
     removeMover(piece1);
     pickedupPiece.pickedup = false;
     addMover(pickedupPiece,pickedupPiece.x, pickedupPiece.y);
     sofar = null;
     sofar2 = sofar3 = null;
     gotall = false;
     currword = null;
     piece1 = null;
     pickedupPiece = null;
     gamePanel.showSprite = true;
     help(free?"help_jigsaw1a":(blended? "help_jigsaw1ph":"help_jigsaw1"));
  }
  //--------------------------------------------------------------
  boolean wanted(piece pp) {
    String news;
    for(short i = 0;i<ptot;++i) {
       if(pp.end && !p[i].end)  news = p[i].val+pp.val;
       else if(pp.start && !p[i].start)  news = pp.val+p[i].val;
       else continue;
       for(short j=0;j<stot;++j) {
           if(news.equals(s[j].wd.v())) {
               return true;
           }
        }
    }
    return false;
  }
  //--------------------------------------------------------------
  boolean needed(piece pp) {
    String news;
    if(free && pp.end)
       return true;
    for(short i = 0;i<ptot;++i) {
       if(pp.end && !p[i].end)  news = p[i].val+pp.val;
       else if(pp.start && !p[i].start)  news = pp.val+p[i].val;
       else continue;
       for(short j=0;j<stot;++j) {
           if(!s[j].gotword  && news.equals(s[j].wd.v())) {
               return true;
           }
        }
    }
    return false;
  }
  //--------------------------------------------------------------
  public synchronized boolean click(int x,  int y)  {
     short i,j;
     String news;
     if(sofar != null) {
       if(!gotall) {
         if(free) {
            if(gr.inside(piece1.x+piece1.w/2,piece1.y+piece1.h)) {
               if(!piece1.notwanted) {
                       cancel(piece1.val);
                       return true;
               }
               else {
                  gamePanel.finishwith(piece1);
                  gr.add(piece1.im, piece1.x,piece1.y );
                  sofar = null;
                  sofar2 = sofar3 = null;
                  piece1 = null;
                  pickedupPiece = null;
                  gamePanel.showSprite = true;
                  if(freeend()) {
                      score(gametot1);
                      exit(3000);
                   }
                   else {
                        flip();
                       help("help_jigsaw1a");
                   }
                   gamePanel.clearall();
               }
            }
         }
         boolean ok = false;                          //  rb 16/3/08  mmmm
         String err = null;                           //  rb 16/3/08  mmmm
         boolean needbeep = false;                     //  rb 16/3/08  mmmm
         for(i = 0;i<ptot;++i) {
           if((Math.abs(x + currendx - pad - p[i].x ) <= pad
               || piece1 != null && piece1.connect != null && x < p[i].x && x+currendx > p[i].x + p[i].w*screenwidth/mover.WIDTH
               || x>p[i].x && x<p[i].x+p[i].w)
             &&  Math.abs(y + currendy - p[i].y) <= jigheight/2 && gamePanel.isMover(p[i])) {
              if(p[i].start) {needbeep=true;  continue;}        //  rb 16/3/08  mmmm
              news = sofar + p[i].val;
              if(piece1 !=null && piece1.connect != null) news += piece1.connect;
              if(free) {
                 for(j=0;j<stot;++j) {
                    if(news.equals(s[j].wd.v())) {
                         if(s[j].gotword) {noise.beep(); return true;}
                         currword = s[j].wd;
                         s[j].gotword = true;
                         currbuild = null;
                         currword.say();
                         if(showpicture)
                             showpic = new showpicture();
                         break;
                    }
                 }
                 if(j==stot) {
                    cancel(news);
                    return true;
                 }
                 ok = true;               //  rb 16/3/08  mmmm
              }
              if(free ||  currword != null && p[i].end && news.equals(currword.v())  && piece1.connect == null
                      ||   currbuild != null  && check(p[i])) {
                 ++nextbuild;
//                 if(phonics) spokenWord.sayPhonicsBit(currword,u.count(sofar2,'/'));
                 sofar2 = sofar2 + sep + p[i].val;
                 sofar3 = sofar3 + sep + p[i].val;
                 int which = u.count(sofar2,u.phonicsplit);
                 if(currword.ismagicsyl(which+1))
                    sofar3 = sofar3 + sep + "e";
                 sofar = news;
                 if(phonics) spokenWord.sayPhonicsBit(currword,which);
                 spokenWord.sayPhonicsSyl(currword,currword.fullval(which+1));
                 if(free && p[i].end || !free && sofar.equals(currword.v())) {
                     gotall = true;
//                    if(!free && ((fl && !specialprivate) || phonics)){
                    if(!free && (phonics)){
                      if (phonics) {
                        gamescore(1);
//startPR2007-12-20^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                        if(Demo_base.isDemo){
                          if (Demo_base.demoIsReadyForExit(0)) return true;
                        }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                        new spokenWord.whenfree(200) {
                          public void action() {
                            if(phonics) spokenWord.sayPhonicsWhole(currword);
                            else currword.say();
                            new spokenWord.whenfree(200) {
                              public void action() {
                                piece1.moveto(0, curr * shapeheight, 300);
                                nextword = gtime + 500;
                              }
                            };
                          }
                        };
                      }
                      else
                        currword.say();
                    }
                    if(!phonics) help(blended?"help_jigsaw3bl":"help_jigsaw3");
                  }
                  ok = true;                     //  rb 16/3/08  mmmm
                  movingpiece mm = buildMovingPiece(piece1,p[i]);
                  gamePanel.finishwith(piece1);
                  if((phonicsw) && gotall) {
                    gamePanel.finishwith(mm);
                    addMover(mm,piece1.x,piece1.y);
                    piece1 = mm;
                    inactivate_all();
                 }
                  else {
                    piece1 = mm;
                    if ( (!split || gotall || !currbuild[nextbuild].equals(p[i].val))) {
                       removeMover(p[i]);
                       p[i].pickedup = true;
                     }
                     if (!gotall)                       inactivate();
                   }
              }
              else {
                   err=currword.v();                  //  rb 16/3/08  mmmm   start
              }
           }
         }
         if(!ok && err != null) {
           if(!free) error(currword.v());
           gamescore(-1);
           noise.groan();
           new spokenWord.whenfree(400) {
             public void action() {
                gamePanel.copyall = true;
             }
           };
         }
         else if(!ok && needbeep) {
           noise.beep();
         }                                      //  rb 16/3/08  mmmm   end
       }
       else if(piece1 != null) {                 // putting over shape
          x = piece1.x + pad;
          y = piece1.y + extray;
          for(i=0;i<stot;++i) {
             if(s[i].got == null  && (Math.abs(s[i].x - x) < pad
                  && Math.abs(s[i].y - y) < shapeheight/2
                  || s[i].mouseOver)) {
                Graphics g = gamePanel.getGraphics();
                g.setFont(wordfont);
                if(currword != null &&  currword.sameshape(g,s[i].wd)) {
                    noise.fitshape();
                    if(free && showpic != null) {
                       gamePanel.removeMover(showpic);
                       showpic = null;
                    }
                    s[i].got = currword;
                    s[i].ended = false;
                    removeMover(piece1);
                     gamePanel.finishwith(piece1);
                     piece1=null;
                     gamescore(1);
//startPR2005-04-18^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                     if(Demo_base.isDemo){
                       if (Demo_base.demoIsReadyForExit(0)) return true;
                     }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                     gamePanel.clearall();
                     nextword = gtime + 300;
                }
                else {
                   noise.beep();
                }
                break;
             }
          }
          if(piece1 != null) {
            piece1.mx = - piece1.w/2;
            piece1.my = - piece1.h;
          }
       }
       return true;
     }
     return false;
  }
  void getnextword() {

  }
  //-------------------------------------------------------------------
  boolean check(piece pp) {
    boolean ph = currword.ismagicsyl(nextbuild+2);
     if(nextbuild >= currbuild.length || !currbuild[nextbuild].equals(pp.val)) return false;
     if(!ph)  {
       return pp.connectto == null && (nextbuild == currbuild.length-1 && pp.end
                                      || nextbuild != currbuild.length-1 && !pp.end);
     }
     else {
       if(pp.connectto != null)   return true;
       else  return  false;
     }
  }
  //-----------------------------------------------------------
  boolean freeend() {
     for(short i=0;i<ptot;++i) {
//startPR2008-08-05^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//        if(!p[i].pickedup) {
          if(!p[i].pickedup || (p[i].start && needed(p[i]))) {
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
           if(p[i].start && starts > 1) return false;
//startPR2008-08-05^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//           if(p[i].end && ends > 1)     return false;
              if(p[i].end && ends > 1) continue;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        }
     }
     return true;
  }
  //-----------------------------------------------------------
  movingpiece buildMovingPiece(movingpiece ppm,piece pp) {
     int w= metrics.stringWidth(sofar)
            + (pad*3 + (pp.end?pad*2:Math.max(xt3,xb3)))*screenwidth/mover.WIDTH;
     int cx=0,spad=0;
     if(pp.connectto != null) {
       spad = pad*screenwidth/mover.WIDTH;
       cx =  metrics.stringWidth(sofar) + metrics.stringWidth(pp.connectskip.val) + spad*3;
       w = cx + pp.connectto.w*screenwidth/mover.WIDTH;
     }
     int h=pp.h*screenheight/mover.HEIGHT;
     int x = (ppm == null)?pp.x:ppm.x;
     int y = (ppm == null)?pp.y:ppm.y;
     Image im =  gamePanel.createImage(w,h);
     Graphics g = im.getGraphics();
//startPR2008-08-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//     g.setColor(background);
      g.setColor(transparentbackground);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     g.fillRect(0,0,pp.w-1,pp.h-1);
     g.setColor(pickupColor);
     g.setFont(wordfont);
     paintpiece2(g,w,h,pp);
     if(pp.connectto != null) {
       g.setColor(pickupColor);
       g.drawLine(spad*3,0,cx+spad*2,0);
       g.drawLine(spad*3,1,cx+spad*2,1);
       g.drawLine(spad*3,h-2,cx+spad*2,h-2);
       g.drawLine(spad*3,h-1,cx+spad*2,h-1);
       paintpiece(g,cx,0,pp.connectto);
     }
     movingpiece mp = new movingpiece(x,y,im,screenwidth,screenheight);
     if(pp.connectto != null) {
       mp.connect = pp.connectto.val;
     }
     mp.val = (ppm!=null?ppm.val:"")+pp.val;
     return mp;
  }
  //-----------------------------------------------------------
  void paintpiece(Graphics g, int x, int y, piece p) {
         int hh = p.h*screenheight/mover.HEIGHT;
         int ww = p.w*screenwidth/mover.WIDTH;
         int len = metrics.stringWidth(p.val);
         int xtext=x, x1t,x1b,x2t,x2b;
         int y2 = y+hh-1;
         int spad = pad*screenwidth/mover.WIDTH;
         Polygon po = new Polygon();
         ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
         if(shark.macOS) ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

         if(p.start) {
            xtext = x+spad*2;
            po.addPoint(x,y);
            drawSplit(po, xtext+len, y);
            po.addPoint(x,y2+1);
            g.fillPolygon(po);
            g.setColor(jigColor.darker());
            g.fillRect(x,y,spad,y2-y);
         }
         else if(p.end ) {
            x2t = x+len + spad*2;
            drawSplit(po,x,y);
            po.addPoint(x2t,y2+1);
            po.addPoint(x2t,y);
            g.fillPolygon(po);
            g.setColor(jigColor.darker());
            g.fillRect(x2t-spad,y,spad,y2-y);
         }
         else {
            drawSplit(po,x,y);
            drawSplitr(po,x+len+(phonics?spad:0),y);
            g.fillPolygon(po);
         }
         g.setColor(Color.white);
         g.drawString(p.val, x + (p.start?spad*3/2:(phonics ?spad/2:0)), (y2+y-metrics.getDescent()+metrics.getAscent())/2);
         ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);
    }
   //-----------------------------------------------------------
    void paintpiece2(Graphics g, int ww, int hh, piece pp) {
         int len = metrics.stringWidth(sofar);
         int xtext, x1t,x1b,x2t,x2b;
         int y2 = hh-1;
         int spad = pad*screenwidth/mover.WIDTH;
         Polygon po = new Polygon();
         ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);

         xtext = spad*2;
         po.addPoint(0,0);
         if(pp.end || gotall) {
            po.addPoint(spad*4+len,0);
            po.addPoint(spad*4+len,y2+1);
         }
         else drawSplit(po, xtext+len, 0);
         po.addPoint(0,y2+1);
         g.fillPolygon(po);
         g.setColor(jigColor.darker());
         g.fillRect(0,0,spad,hh);
         if(pp.end || gotall) g.fillRect(spad*3+len,0,spad,hh);
         g.setColor(Color.white);
         g.drawString(sofar,spad*2, (y2-metrics.getDescent()+metrics.getAscent())/2);

         String s = sofar3;
         int i,j,x=spad*2;
         while((i=s.indexOf(sep)) >= 0) {
            po = new Polygon();
            drawSplit(po, x += metrics.stringWidth(s.substring(0,i)), 0);
            for(j=0;j<po.npoints-1;++j) {
               g.drawLine(po.xpoints[j],po.ypoints[j],
                     po.xpoints[j+1],po.ypoints[j+1]);
            }
            s = s.substring(i+1);
         }
         ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);
   }
    //-------------------------------------------------------------
   class piece extends mover {
      boolean start,end,pickedup,inactive;
      int offset;
      piece connectto,connectskip;
      String val;
      int valstart, valend;
      Color color;

      public piece(String s, boolean start1, boolean end1, Color c1) {
         super(false);
         start = start1;
         end = end1;
         val = s;
         color = c1;
      }
      public void paint(Graphics g,int x1, int y1, int w1, int h1) {
        if(inactive) return;
         g.setFont(wordfont);
         g.setColor(jigColor);
         paintpiece(g,x1,y1,this);
         if(connectto != null) {
           int spad = pad*screenwidth/mover.WIDTH;
           int cx = x1 + metrics.stringWidth(val) + metrics.stringWidth(connectskip.val)+spad*2;
           connectto.h=h;
           connectto.w = pwidth(connectto);
           g.setColor(jigColor);
           g.drawLine(x1+spad*3,y1,cx+spad*2,y1);
           g.drawLine(x1+spad*3,y1+1,cx+spad*2,y1+1);
           g.drawLine(x1+spad*3,y1+h1-1,cx+spad*2,y1+h1-1);
           g.drawLine(x1+spad*3,y1+h1-2,cx+spad*2,y1+h1-2);
           paintpiece(g,cx,y1,connectto);
         }
      }
      public void mouseClicked(int mx, int my) {
        short i;
        if(inactive) return;
        if(sofar == null) {
           if(!free && !start) {noise.beep();return;}
           if(free && (start ||!wanted(this))
               ||  start && currbuild != null
                && val.equals(currbuild[0])) {
              nextbuild = 1;
              currendx = 0;
              sofar3 = sofar2 = sofar = val;
              removeMover(this);
              if(!free && sofar.equals(currword.v())) {
                end=true;
                piece1 = buildMovingPiece(null,this);
                piece1.notwanted = !wanted(this);
                piece1.val = this.val;
                gamePanel.drop(piece1);
                piece1.x = piece1.tox = x;
                piece1.y = piece1.toy = y;
                new spokenWord.whenfree(200) {
                  public void action() {
                    spokenWord.sayPhonicsWhole(currword);
                    new spokenWord.whenfree(200) {
                      public void action() {
                        piece1.moveto(0, curr * shapeheight, 300);
                        nextword = gtime + 500;
                      }
                    };
                  }
                };
                return;
              }
              piece1 = buildMovingPiece(null,this);
              piece1.notwanted = !wanted(this);
              piece1.val = this.val;
              pickedup = true;
              pickedupPiece = this;
              if(phonics) help(magice?"help_jigsaw2ph2":(blended? "help_jigsaw2phbl":"help_jigsaw2ph"));
              else help(free?"help_jigsaw2a":"help_jigsaw2");
              inactivate();
              if(phonics) spokenWord.sayPhonicsBit(currword,0);
           }
           else {
                   if(!free) error(currword.v());
                   gamescore(-1);
                   noise.groan();
                   new spokenWord.whenfree(400) {
                     public void action() {
                        gamePanel.copyall = true;
                     }
                   };
           }
        }
     }
  }
  //-------------------------------------------------------------
   class shape extends mover {
      public word wd;
      public word got;
      boolean gotword;   // set when this word is found
      public shape(word w1,int width,int height) {
         super(false);
         wd = w1;
         h = height;
         w = width;
      }
       public void paint(Graphics g,int x, int y, int w, int h) {
         Rectangle r = new Rectangle(x,y,w,h);
         Font f = g.getFont();
         Color c = g.getColor();
         g.setFont(wordfont);
         g.setColor(Color.black);
         wd.paint(g,r,word.SHAPE);
         if(got != null) {
            g.setColor(Color.white);
            got.paint(g,r,(byte)0);
         }
         g.setFont(f);
         g.setColor(c);
      }
  }
  //-------------------------------------------------------------
   class movingpiece extends mover {
      boolean notwanted;
      String connect;
      String val;
      int lastw;
      public movingpiece(int x,int y,Image im1,int sw, int sh) {
//startPR2008-08-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      super(im1,sw,sh,background);
        super(im1,sw,sh,transparentbackground);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         mx = x - gamePanel.mousex;
         my = y - gamePanel.mousey;
         gamePanel.moveWithMouse(this);
         if(!free && showpic != null) {
            gamePanel.removeMover(showpic);
            showpic = null;
         }
         currendx = mx + (w - Math.max(xt3,xb3));
         currendy = my;
         dontwait = true;
         lastw = screenwidth;
       }
       public void paint(Graphics g,int x, int y, int w, int h) {
         if(lastw != screenwidth && !gotall && gamePanel.movesWithMouse(this)) {
            gamePanel.finishwith(this);
            --curr;
            nextword = gtime;
            return;
         }
         if(gotall
               && !phonics && piece1.x < col1width + col2width + pad) {
               Font f = g.getFont();
               Color c = g.getColor();
               int yy =  y + (h-metrics.getDescent()+metrics.getAscent())/2;
               g.setFont(wordfont);
               g.setColor(Color.white);
               g.drawString(sofar,x+pad*screenwidth/mover.WIDTH,yy);
               g.setFont(f);
               g.setColor(c);
         }
         else super.paint(g,x,y,w,h);
      }
   }
}

