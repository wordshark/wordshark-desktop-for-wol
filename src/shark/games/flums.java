package shark.games;

import java.awt.*;

import shark.*;
import shark.sharkGame.*;

public class flums extends sharkGame {     //SS 03/12/05
  Font f;
  FontMetrics m;
  int cwidth,lastwidth;
  int wordtot;
  int currlen;
  int curr = -1;
  boolean wantnext;
  long nextword = gtime;
  wordc words[];
  int maxw = mover.WIDTH/2;
  int added;
  clue clue;
  letter letter;
  String totlist[] = new String[0];
  String totsoundlist[] = new String[0];
  boolean haderr;
  int tothappy,tothaderr;
  static final int JUMPTIME = 800;
  static final int TOTMOVE = 800;
  String needsave;
  boolean multisyll;
  boolean showpicture = !specialprivateon;

  public flums() {
    int i;
    errors = true;
    gamescore1 = true;
    peeps = false;
    listen = true;
    peep = false;
    wantspeed=false;
    gamePanel.clearWholeScreen = true;
         //    clickonrelease=true;
    buildTopPanel();
//startPR2007-12-31^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    gamePanel.showSprite = false;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    help("help_flums");
  }
  public void afterDraw(long t){
    int i;
    if(words ==null) {
      rgame.w = u.stripdups(rgame.w);
      wordtot = rgame.w.length;
      if(wordtot>4) {        // keep to max of w words
        int want = Math.min(4, (wordtot+1)/2);
        int o[] = u.shuffle(u.select(wordtot, wordtot));
        String lasttime = student.optionstring("flums_lasttime"), ss[];
        if(lasttime != null) {
          ss = u.splitString(lasttime);
          if(ss.length>0 && !ss[0].equals(rgame.topicName)) ss = new String[0];
          else ss = u.removeString(ss,0);
        }
        else ss = new String[0];
        String thistime[] = new String[0];
        word[] newwords = new word[0];
        for (i = 0; i < wordtot && newwords.length<want; ++i) {
            if (u.findString(ss, rgame.w[o[i]].value) < 0) {
              newwords = u.addWords(newwords, rgame.w[o[i]]);
              thistime = u.addString(thistime, rgame.w[o[i]].value);
            }
        }
        needsave = u.combineString(u.addString(new String[]{rgame.topicName},thistime));  // will save at end
        wordtot = newwords.length;
        rgame.w = newwords;
      }
      f = sizeFont(rgame.w,screenwidth,screenheight*3/4);
      m = getFontMetrics(f);
      words = new wordc[wordtot];
      for(i=0;i<wordtot;++i)  {
        words[i] = new wordc(rgame.w[i], i);
        if(u.syllabletot(rgame.w[i].v()) > 1) multisyll = true;
      }
    }
    if(completed) return;
    if(wantnext) {
            if(delayedflip && !completed){
               flip();
            }
      haderr = false;
      wantnext = false;
      if (curr >= 0 && ++words[curr].currpos < words[curr].o.length) {
          String csound = words[curr].csounds==null?null:words[curr].csounds[words[curr].o[words[curr].currpos]];
        new letter(words[curr].c[words[curr].o[words[curr].currpos]],
                csound,
                   words[curr].word.ismagicsyl(words[curr].o[words[curr].currpos] +2));
       }
      else {
        if (curr >= 0) {
          gamescore(2);
          words[curr].done = true;
          words[curr].moveto(mover.WIDTH/2*(curr%2), (m.getHeight()*5/4 + m.getHeight()*2*(curr/2))*mover.HEIGHT/screenheight,500);
          words[curr].word.say();
//startPR2007-12-20^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          if(Demo_base.isDemo){
            if (Demo_base.demoIsReadyForExit(0)) return;
          }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          nextword = Math.max(spokenWord.endsay2+500,gtime+500);
        }
        if (clue != null) {
          removeMover(clue); clue = null;
        }
      }
    }
    if(nextword != 0  && gtime > nextword) {
        nextword = 0;
        if (++curr >= wordtot) {
          score(gametot1);
          String mess = "";
          if(tothappy==0) mess = rgame.getParm("happy0");
          if(tothappy==1) mess = rgame.getParm("happy1");
          else if(tothappy>1) mess = u.edit(rgame.getParm("happy"),String.valueOf(tothappy));
          if(tothaderr==1) mess += "|" + rgame.getParm("haderr1");
          else if(tothaderr>1) mess += "|" +  u.edit(rgame.getParm("haderr"),String.valueOf(tothaderr));
          if(tothaderr == 0) mess += "|"+rgame.getParm("brilliant");
          else if(tothaderr < 2) mess += "|"+rgame.getParm("good");
          showmessage(mess,0,(m.getHeight() + m.getHeight()*2*((wordtot+1)/2))*mover.HEIGHT/screenheight,
                              mover.WIDTH*3/4, mover.HEIGHT);
          exitbutton(mover.WIDTH *13/16, mover.HEIGHT*3/4);
//startPR2007-12-31^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          gamePanel.showSprite = true;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          if(needsave != null) student.setOption("flums_lasttime",needsave);  // save list of words done this time
          wantnext = false;
          return;

        }
        else {
          sentence sen = null;
          if(multisyll)
            sen = sentence.findsentencefor(words[curr].wd) ;
          if(sen != null) {
            clue = new clue(sen);
          }
          else {
            sharkImage im = sharkImage.find(words[curr].word.vpic());
            if(im != null && showpicture) {
              clue = new clue(im, words[curr].word.v());
              words[curr].word.say();
           }
            else {
              clue = new clue(words[curr].word);
              words[curr].word.say();
            }
          }
          currlen = words[curr].wd.length();
          Rectangle rr = clue.s.setup(clue.im != null?clue.w/2:clue.w, clue.h, currlen,
                                      words[curr].wd);
          wordfont = clue.s.font;
          metrics = getFontMetrics(wordfont);
          cwidth = u.getadvance(metrics);
          if(wordfont.getSize() < f.getSize()) {
           f = wordfont;
           m = clue.s.metrics;
           for(i=0;i<words.length;++i) {
              if(words[i].done) words[i].y = words[i].toy = (m.getHeight()+ m.getHeight()*2*(i/2))*mover.HEIGHT/screenheight;
           }
          }
          words[curr].w = rr.width;
          words[curr].h = rr.height;
          words[curr].addbad();
          addMover(words[curr], clue.im != null?rr.x+clue.w/2:rr.x, clue.y + rr.y);
          wantnext = true;
        }
    }
  }
  public void listen1() {
     if(clue != null) {
        if(!clue.realsentence) words[curr].word.say();
        else clue.s.readsentence();
     }
  }
  class letter extends mover {
     char  c[];
     String s;
     String ssound;
     showpicture sp;
     boolean magice;
     letter(String ss, String sssound, boolean magice1) {
       magice = magice1;
       s = ss;
       ssound = sssound;
       c = ss.toCharArray();
       int i,j,tot=0,tot2=0;
       if(magice)
         w = cwidth*3*mover.WIDTH/screenwidth;
       else w = cwidth*c.length*mover.WIDTH/screenwidth;
       h = metrics.getHeight()*mover.HEIGHT/screenheight;
       gamePanel.moveWithMouse(this);
       my = -h/2;
       mx = -cwidth*mover.WIDTH/screenwidth/2;
       letter = this;
       gamePanel.mousemoved(words[curr].x*screenwidth/mover.WIDTH,
                            (words[curr].y+words[curr].h*3/2)*screenheight/mover.HEIGHT);    // for touch screen
       if(phonics && ! words[curr].word.onephoneme) {
         new spokenWord.whenfree(500) {
           public void action() {
             if(magice) 
                 spokenWord.sayPhonicsBit(words[curr].word,words[curr].o[words[curr].currpos]);
             else if(words[curr].offsets[words[curr].o[words[curr].currpos]]>=0) {   //  in word
               spokenWord.sayPhonicsBit(words[curr].word,words[curr].o[words[curr].currpos]);
             }
             else 
                 spokenWord.sayPhonic(ssound==null?s:ssound);
           }
         };
       }
     }
     public void mouseClicked(int xx, int yy) {
        if(words[curr].fits(s,magice)) {
          gamePanel.finishwith(this);
        }
     };
     public void paint(Graphics g, int x1, int y1, int w1, int h1) {
         if(shark.macOS) ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
       int i, xx = x1, ww = cwidth;
        g.setColor(Color.black);
        g.setFont(wordfont);
        for(i=0;i<c.length;++i) {
          g.drawChars(c, i, 1, xx+ww/2-metrics.charWidth(c[i])/2,
                          y1 + h1 / 2 - metrics.getHeight() / 2 + metrics.getAscent());
          xx += ww;
        }
        if(magice) g.drawString("e",  xx + ww + ww/2-metrics.charWidth('e')/2,
                          y1 + h1 / 2 - metrics.getHeight() / 2 + metrics.getAscent());
     }
  }
  class wordc extends mover {
     word word;
     String wd;
     char cc[];
     String c[];
     String csounds[];
     int o[],currpos=-1,offsets[];
     int p,pos=1;
     int badtot;
     long saying;
     boolean got[],done,notpresplit;
     flum flums[] = new flum[0];
     int lost;
     wordc(word wr, int pp) {
       int i,ii,j;
       p = pp;
       word = wr;
       wd = wr.v();
       currlen = wd.length();
       cc = wd.toCharArray();
       c =  phonicsw?u.splitString(wr.phsplit(),u.phonicsplit):u.splitString(wr.vsplit(),'/');
       csounds = phonicsw?wr.phonics():null;
       offsets = new int[c.length];
       if(c.length <= 1) {
         notpresplit = true;
         String s = wd;
         offsets = u.select(s.length(), s.length());
         c = new String[offsets.length];
         for(i=0;i<offsets.length;++i) {
           c[i] = s.substring(offsets[i],offsets[i]+1);
         }
       }
       else {
         for(i=j=0;i<c.length;++i) {
          offsets[i] = j;
          j += c[i].length();
        }
      }
      if(wr.phonicsmagic != null) {
         for(i=0;i<c.length;++i) {
           if(wr.ismagicsyl(i)) {
              c = u.removeString(c,i);
              offsets = u.removeint(offsets,i);
           }
         }
       }
       for(i=0;i<c.length;++i) {
         while(c[i].startsWith(" ")) {
           ++offsets[i];
           c[i] = c[i].substring(1);
         }
         while(c[i].endsWith(" ")) {
           c[i] = c[i].substring(0,c[i].length()-1);
         }
         if(c[i].length()==0) {
           c = u.removeString(c,i);
           offsets = u.removeint(offsets,i);
           --i;
           continue;
         }
         String sss[] = u.splitString(c[i],' ');
         if(sss.length>1) {
            c[i] = sss[0];
            int off = offsets[i] + c[i].length()+1;
            int pos = i;
            for(j=1;j<sss.length;++j) {
               if (sss[j].length()>0) {
                 c = u.addString(c, sss[j],++pos);
                 offsets = u.addint(offsets,off,pos);
               }
               off += sss[j].length()+1;
            }
         }
       }
       o = u.shuffle(u.select(c.length,c.length));
       for(i=0;i<c.length;++i) {
          int k = u.getAddStringSortIndex(totlist,c[i]);
          if(k>=0){
            totlist = u.addString(totlist,c[i], k);
            if(csounds!=null)
              totsoundlist = u.addString(totsoundlist,csounds[i], k);
          }
       }
       got = new boolean[currlen];

       if(notpresplit) {
         int needlet = 5 + u.rand(2);
         if(c.length>needlet) currpos = c.length - needlet -1;
       }
       else if(o.length ==5) currpos = 0;
       else if(o.length >5) currpos = 1;
       if(currpos >= 0) {
         for (ii=0;ii<=currpos;++ii) {
           int pos = offsets[o[ii]];
           for (i = pos; i < pos + c[o[ii]].length(); ++i)    got[i] = true;
           if(word.ismagicsyl(o[ii]+2)) got[pos + c[o[ii]].length()+1] = true;
         }
       }
     }
     void addbad() {
       int i, repeats=0;
       int wantbad = 1 + u.rand(3), totbad = wantbad;
       loop1:while(wantbad > 0 && ++repeats < 1000) {
          int poso = currpos + 1 + u.rand(c.length-(currpos+1));
          int o2[] = u.shuffle(u.select(totlist.length,totlist.length));
          for(i=0;i<o2.length;++i) {   // try for one that fits
            if(wd.toLowerCase().indexOf(totlist[o2[i]].toLowerCase())<0
              && canfit(totlist[o2[i]].length(),i)) {
              if(poso == c.length) {
                  --poso;
              }
              o = u.addint(o,c.length,poso);
              c = u.addString(c,totlist[o2[i]].toLowerCase());
              if(csounds!=null)
                  csounds = u.addString(csounds,totsoundlist[o2[i]].toLowerCase());
              offsets = u.addint(offsets,-1);
              ++badtot;
              --wantbad;
              new flum(this,totbad);
              continue loop1;
            }
          }
          for(i=0;i<o2.length;++i) {   // no good - get any one.
            if(wd.toLowerCase().indexOf(totlist[o2[i]].toLowerCase())<0) {
              o = u.addint(o,c.length,poso);
              c = u.addString(c,totlist[o2[i]].toLowerCase());
              if(csounds!=null)
                  csounds = u.addString(csounds,totsoundlist[o2[i]].toLowerCase());
              offsets = u.addint(offsets,-1);
              ++badtot;
              --wantbad;
              new flum(this,totbad);
              continue loop1;
            }
          }
       }
     }
     boolean canfit(int len, int pos) {     // see if room for field of given len left after pos placements
        int i,j;
        boolean arefree[] = new boolean[cc.length];
        for(i=pos;i<o.length;++i) {
           if(offsets[o[i]]<0) continue;
           for(j = offsets[o[i]];j < offsets[o[i]] + c[o[i]].length();++j) arefree[j] = true;
        }
        int free=0,maxfree = 0;
        for(i=0;i<arefree.length;++i) {
           if(arefree[i]) if(++free >= len) return true;
           else free= 0;
        }
        return false;
     }
     boolean fits(String ch,boolean magice) {
        int i,j,k;
        if(!overword() && overflum()<0) return false;
        if(offsets[o[currpos]]<0) {   // not in word - must be over bad flum
             if((i=overflum()) >= 0) {
                  flums[i].bad = false;
//                  flums[i].ok = false;
                  flums[i].im.setControl(haderr?"sad":"ok");
                  if(haderr) flums[i].sad = true;
                  if(!haderr) ++tothappy;
                  else ++tothaderr;
                  gamePanel.finishwith();
                  wantnext = true;
                  return true;
             }
              else if(overword()) {
                noise.groan();
                haderr = true;
                error(words[curr].word.v());
                shiftflum();
                return false;
              }
              else {
                 return false;
              }
         }
         else if((i=overflum()) >= 0) {  // over bad flum when actually OK
             noise.groan();
             error(words[curr].word.v());
             shiftflum();
             haderr = true;
             return false;
         }
         int cw = cwidth*mover.WIDTH/screenwidth;
         int lw = letter.w;
         int pos;
         if(gamePanel.mousex > x+cw/8 && gamePanel.mousex + lw -cw < x + w - cw/8
             && (gamePanel.mousex - x)%cw >cw/8 && (gamePanel.mousex - x)%cw <= cw*7/8)
             pos = (gamePanel.mousex - x)/cw;
         else {
           noise.beep();
           return false;
         }
//         int pos = (gamePanel.mousex + cw/2 - x) / cw;
         if(!overword() || pos<0 || pos > currlen-ch.length() || magice &&  pos+2 > currlen-ch.length()) {
           noise.beep();
           return false;
         }
         for(i=pos;i<pos + ch.length();++i) {
            if(got[i]) {
              noise.beep();
              return false;
            }
         }
         if(magice &&  got[pos+2]) {
            noise.beep();
            return false;
         }
         if(pos > cc.length - ch.length() ||!ch.equals(wd.substring(pos, pos+ch.length()))
            || phonics && !words[curr].word.onephoneme && !phonemefits(words[curr].word, pos, words[curr].word.phonics()[words[curr].o[words[curr].currpos]])
            || magice &&  !words[curr].word.ismagicsyl(o[currpos]+2))  {
           if(!haderr) noise.groan();
           error(words[curr].word.v());
           haderr = true;
           shiftflum();
           return false;
         }
         gamePanel.finishwith();
         for(i=pos;i<pos + ch.length();++i) {             // ok
            got[i] = true;
         }
         if(magice) got[pos+2]=true;
         if(!phonics && offsets[o[currpos]] != pos) {              // not the expected place
           String newc[] = new String[0];
           int newoffsets[] = new int[0];
           for (i = currpos; i < o.length; ++i) {
             if(offsets[o[i]] < 0) {
               newc = u.addString(newc, c[o[i]]);
               newoffsets = u.addint(newoffsets, -1);
             }
             else {
               int from = offsets[o[i]], startedat = from;
               int to = from + c[o[i]].length();
               for (j = from; j < to; ++j) {
                 if (got[j]) {
                   if (j > from) {
                     newc = u.addString(newc, wd.substring(from, j));
                     newoffsets = u.addint(newoffsets, from);
                   } while (++j < to && got[j]) {}
                   ;
                   from = j;
                 }
               }
               if (to > from) {
                 newc = u.addString(newc, wd.substring(from, to));
                 newoffsets = u.addint(newoffsets, from);
               }
             }
           }
           offsets=newoffsets;
           c = newc;
           currpos=-1;
           o = u.shuffle(u.select(c.length,c.length));
         }
         wantnext = true;
         new flum(this,haderr);
         return true;
     }
     boolean phonemefits(word wd, int pos, String ph) {
       String phseg[] = wd.phsegs();
       int i,j, sofar=0;
       for(i=0;i<phseg.length;++i) {
          if(pos < sofar) return false;
          if(pos==sofar) {
              return wd.phonics()[i].equals(ph);
          }
          if(wd.ismagicsyl(i+2)) sofar += 1;
          else if (wd.ismagicsyl(i+1)) sofar += 2;
          else sofar += phseg[i].length();
       }
       return false;
     }
     int overflum() {
       for(int i=0; i<flums.length; ++i) {
         if (flums[i].bad && (letter.x <= x+flums[i].x + flums[i].w
                              &&  letter.x + letter.w>= x+flums[i].w
                              && Math.abs(letter.y  + letter.h/2  - (y - flums[i].h/2))< flums[i].h/3
                             || gamePanel.mousex > x + flums[i].x
                                 && gamePanel.mousex < x + flums[i].x+flums[i].w
                                 && Math.abs(gamePanel.mousey  - (y - flums[i].h/2))< flums[i].h/3)) return i;
       }
       return -1;
    }
    boolean overword() {
      int allow = cwidth*mover.WIDTH/screenwidth/2;
      int allowy = h/6;
      return letter.x >= x - allow && letter.x+letter.w <= x+w+allow
              && letter.y > y - allowy && letter.y + letter.h < y+h+allowy
              || mouseOver;
    }
    void shiftflum() {
      int i;
      boolean left;
      if(flums.length==0) return;
      while(flums[0].bad && flums[0].jumping==0) {
          flum thisflum = flums[0];
          flums[0].jumping = gtime;
          System.arraycopy(flums,1,flums,0,flums.length-1);
          flums[flums.length-1] = thisflum;
      }
    }
    int flumw() {
      int i,len=0;
      for(i=0;i<flums.length;++i) len += flums[i].w;
      return len;
    }
    public void paint(Graphics g, int x1, int y1, int w1, int h1) {
        if(shark.macOS) ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
       if(done) {
         g.setColor(Color.black);
         g.setFont(f);
         g.drawString(wd,x1,y1+h1/2-m.getHeight()/2+m.getAscent());
         drawflums(g,x1,y1+h1/2-m.getHeight()/2);
         return;
       }
       if(clue != null) {
         wordfont = clue.s.font;
         metrics = clue.s.metrics;
         if(screenwidth != lastwidth) cwidth = u.getadvance(metrics);
         lastwidth = screenwidth;
       }
       int i, j, xx = x1, ww = cwidth;

       g.setFont(wordfont);
       g.setColor(yellow());
       g.fillRect(xx, y1, cwidth*currlen, h1);
       for(i=0;i<currlen;++i,xx+=ww) {
         g.setColor(background);
         g.drawLine(xx+ww-1,y1,xx+ww-1,y1+h1);
         g.drawLine(xx+ww,y1,xx+ww,y1+h1);
         g.setColor(Color.black);
         if(got[i]) g.drawChars(cc,i,1,xx+ww/2-metrics.charWidth(cc[i])/2,
                                y1+h1/2-metrics.getHeight()/2 + metrics.getAscent());
       }
       g.setColor(yellow());
       g.drawRect(x1,y1,cwidth*currlen,h1);
       drawflums(g,x1,y1);
     }
     void setflumx() {
       int i,xx = 0;
       for(i=0;i<flums.length;++i) {
         if(flums[i].jumping == 0)    flums[i].x = flums[i].tox = xx;
         else flums[i].tox = xx;
         xx += flums[i].w;
       }
     }
     int flumx(flum ff) {
       int i,xx = 0;
       for(i=0;i<flums.length && flums[i] != ff;++i) {
         xx += flums[i].w;
       }
       return xx;
     }
     void drawflums(Graphics g,int x1,int y1) {
        int i;
        for(i=0;i<flums.length;++i) {
          if (flums[i].jumping != 0) {
            if (gtime > flums[i].jumping + JUMPTIME) {
              flums[i].jumping = 0;
              flums[i].x = flums[i].tox;
            }
            else {
              int xx = flums[i].x +
                  (int) ( (gtime - flums[i].jumping) * (flums[i].tox - flums[i].x) / JUMPTIME);
              int yy = (int) (h * 2 *
                                Math.sin(Math.PI * (gtime - flums[i].jumping) / JUMPTIME));
              flums[i].paint(g, x1 + xx * screenwidth / mover.WIDTH,
                             y1 - (yy + flums[i].h) * screenheight / mover.HEIGHT,
                             flums[i].w * screenwidth / mover.WIDTH,
                             flums[i].h * screenheight / mover.HEIGHT);
              continue;
            }
          }
          flums[i].paint(g, x1 + flums[i].x * screenwidth / mover.WIDTH,
                         y1 - flums[i].h * screenheight / mover.HEIGHT,
                         flums[i].w * screenwidth / mover.WIDTH,
                         flums[i].h * screenheight / mover.HEIGHT);
        }
     }
  }
  class clue extends mover {
    sentence s;
    sharkImage im;
    boolean realsentence;
    clue(sentence s1) {
      realsentence = true;
      listenButton.setVisible(true);
      w = mover.WIDTH;
      h = mover.HEIGHT / 2;
      s = s1;
      s.manager = gamePanel;
      y = (m.getHeight() + (curr + 1) / 2 * m.getHeight() * 2) * mover.HEIGHT /
          screenheight;
      addMover(this, 0, y);
    }
    clue(sharkImage im1, String ss) {
       im  = im1;
       listenButton.setVisible(true);
       s = new sentence("/"+ss+"/");
       w = mover.WIDTH;
       h = mover.HEIGHT / 2;
       s.manager = gamePanel;
       y = (m.getHeight() + (curr + 1) / 2 * m.getHeight() * 2) * mover.HEIGHT /
           screenheight;
       addMover(this, 0, y);
    }
    clue(word word) {
       listenButton.setVisible(true);
       s = new sentence("/"+word.v()+"/");
       w = mover.WIDTH;
       h = mover.HEIGHT / 2;
       s.manager = gamePanel;
       y = (m.getHeight() + (curr + 1) / 2 * m.getHeight() * 2) * mover.HEIGHT /
           screenheight;
       addMover(this, 0, y);
    }
    public void paint(Graphics g, int x1, int y1, int w1, int h1) {
      if(im != null) {
         if(im.tifimage!=null){
            int xx1 = x1;
            int yy1 = y1+h1/4;
            int ww1 =  w1/2;
            int hh1 = h1*3/4;
            int ih = im.tifimage.getHeight(null);
            int iw = im.tifimage.getWidth(null);
            int imw = ww1;
            int imh = hh1;
            imw = ((iw*hh1/ih));
            if(imw > ww1){
                imw = ww1;
                imh = ((ih*ww1/iw));
            }
            xx1 = xx1+(ww1-imw)/2;
            yy1 = yy1+(hh1-imh)/2;
            im.paint(g,xx1,yy1,imw,imh);
         }
         else{
             im.paint(g,x1,y1+h1/4,w1/2,h1*3/4);
         }
         g.setColor(Color.black);
         s.paint2(g,x1+w1/2,y1,w1/2,h1);
      }
      else {
        g.setColor(Color.black);
        s.paint2(g, x1, y1, w1, h1);
      }
    }
  }
  class flum extends mover {
    sharkImage im;
    int myword = curr;
    long lastt = gtime;
    boolean bad, ok, sad, moving,gotall;
    wordc word;
    int relx;
    int maxw = cwidth* mover.WIDTH / screenwidth * 3 / 2;
    int lasttot;
    long jumping;
    int excurr=-1,excurrletter;
    long startmove;
    flum(wordc word1,int tot) {   // bad flum
       word = word1;
       bad = true;
       im = sharkImage.random("flums_bad_");
       im.h = word.h;
       im.w = maxw;
       im.adjustSize(screenwidth,screenheight);
       w = im.w;
       h = im.h;
       x = tox = word.flums.length * w ;
       flum newflum[] = new flum[word.flums.length+1];
       System.arraycopy(word.flums,0,newflum,0,word.flums.length);
       newflum[word.flums.length] = this;
       word.flums = newflum;
       lasttot = word.flums.length;
     }
     flum(wordc word1,boolean haderr) {
       sad = haderr;
       word = word1;
       im = sharkImage.random("flums_good_");
       im.h = word.h;
       im.w = maxw;
       im.adjustSize(screenwidth,screenheight);
       w = im.w;
       h = im.h;
       int i=u.rand(word.flums.length+1),xx = 0;
       flum newflum[] = new flum[word.flums.length+1];
       System.arraycopy(word.flums,0,newflum,0,i);
       newflum[i] = this;
       if(i<word.flums.length)   System.arraycopy(word.flums,i,newflum,i+1,word.flums.length-i);
       im.setControl(haderr?"sad":"ok");
       if(!haderr) ++tothappy;
       else ++tothaderr;
       word.flums = newflum;
       lasttot = word.flums.length;
       word.setflumx();
     }
     public void paint(Graphics g, int x1, int y1, int w1, int h1) {
       ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
       im.paint(g,x1,y1,w1,h1);
       ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);
      }
  }
}
