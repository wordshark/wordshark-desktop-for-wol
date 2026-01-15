package shark.games;

import java.awt.*;
import java.awt.event.*;

import shark.*;

public class suffixes extends  sharkGame {
  static final byte SUFFCONS = 1;
  static final byte CONS2 = 2;
  static final byte W = 3;
  static final byte LONGVOWEL = 4;
  static final byte CG = 5;
  static final byte MUSTDOUBLE = 6;
  static final byte MUSTSTRIP = 7;
  static final byte LONGVOWELE = 8;
  static final byte NOSHOW = 9;
  static final short FLIPTIME = 2000;
  static final short GOODTIME = 3000;
  static final Color scorecolors[] = new Color[] {
                                           new Color(200,200,200),
                                           new Color(0,128,0),
                                           new Color(64,0,128),
                                           new Color(128,64,0),
                                           new Color(192,0,64),
                                           new Color(255,0,0) };
   String words[],databases[];
   short wtot,wordsuff[];
   String suffs[],results[],bad[];
   suffc sufflist[];
   boolean leavealoneb[];
   short sufftot;
   short across,down;
   int acrossw, downh;
   wordc wc[];
   int suffwidth = mover.WIDTH/8, suffheight;
   short wordnum=-1;            // currently selected word number
   boolean gotword;
   short donetot;
   Font scorefont;
   long lastchange;
   byte lasterrtype;

   public suffixes() {
      String su,s;
      short tot,i,j;
      int k,m,n;
      errors = true;
      gamescore1 = true;
//    peeps = true;
      listen= true;
//    peep = true;
//    wantspeed = false;
      forceSharedColor = true;
      gamePanel.setBackground(background = Color.gray);
      help("help_addsuff1");
      buildTopPanel();
      short o[] = u.shuffle(u.select((short)rgame.w.length,(short)rgame.w.length));
      tot = (short)Math.min(9,o.length);
      words = new String[tot];
      results = new String[tot];
      bad = new String[tot];
      databases = new String[tot];
      wordsuff = new short[tot];
      leavealoneb = new boolean[tot];
      suffs = new String[tot+1];
      loop1:for(i=0;i<o.length && wtot < tot;++i) {
         s = rgame.w[o[i]].value;
         if((j = (short)s.indexOf('+')) < 0 || j > s.length()-2)
                  continue loop1;
         if(s.charAt(j+1)=='+') {
            leavealoneb[wtot]=true;
            su = s.substring(j+2);
         }
         else su = s.substring(j+1);
         if((k=su.indexOf('=')) >= 0) {
           if((m = su.indexOf('(',k)) >= 0 && (n =su.indexOf(')',m)) >= 0) {
             bad[wtot] = su.substring(m + 1, n );
             results[wtot] = su.substring(k + 1,m);
           }
           else results[wtot] = su.substring(k + 1);
           su = su.substring(0, k);
         }
         databases[wtot] = rgame.w[o[i]].database;
         words[wtot++] = s.substring(0, j);
         for(j=0;j<sufftot;++j) {
            if(suffs[j].equals(su)) {
               wordsuff[wtot-1] = j;
               continue loop1;
            }
         }
         wordsuff[wtot-1] = sufftot;
         suffs[sufftot++] = su;
         if(results[wtot-1] == null) {
           if(su.equals("s")) suffs[sufftot++] = "es";
           else if(su.equals("es")) suffs[sufftot++] = "s";
         }
      }
      wc = new wordc[wtot];
      if(wtot == 9)  across = down = 3;
      else  {down = 2; across = (short)((wtot+1)/2);}
      acrossw = (mover.WIDTH - suffwidth - across*(mover.WIDTH*2/screenwidth))/across;
      downh = (mover.HEIGHT - down*(mover.HEIGHT*2/screenheight))/down;
      scorefont = sizeFont(null," time: xxxxx secs ",mover.WIDTH/across,mover.HEIGHT/down/3);
      wordfont = null;
      for(i=0;i<wtot;++i) {
          wordfont=sizeFont(wordfont,words[i]+suffs[wordsuff[i]]+"ww",acrossw,downh);
      }
      for(i=0;i<sufftot;++i) {
          wordfont=sizeFont(wordfont,suffs[i]+"-", suffwidth,mover.HEIGHT/sufftot);
      }
      suffheight = metrics.getHeight()*mover.HEIGHT/screenheight;
      sufflist = new suffc[sufftot];
      for(i=0;i<wtot;++i) {
         addMover(wc[i] = new wordc(i), (i%across)*(acrossw+mover.HEIGHT*2/screenwidth),
                                  (i/across)*(downh+mover.HEIGHT*2/screenheight));
      }
      u.sort(suffs,sufftot);
      for(i=0;i<sufftot;++i) {
         sufflist[i] = new suffc(i);
      }
   }
   //----------------------------------------------------------
   void addsuff() {
      short i;
      for(i=0;i<sufftot;++i) {
         gamePanel.finishwith(sufflist[i]);
         addMover(sufflist[i], sufflist[i].startx,sufflist[i].starty);
      }
  }
   public boolean click(int x, int y) {
      if(!gotword) {
         for(short i=0;i<wtot;++i) {
             if(!wc[i].done && wc[i].mouseOver) {
                 wc[i].mouseClick(x,y);
                 help("help_addsuff2");
                 return true;
             }
         }
      }
      else if(wordnum>=0 && wc[wordnum].active) {
         wc[wordnum].mouseClick(x,y);
         return true;
      }
      return false;
   }
   //-------------------------------------------------------------
   void endmessage() {
      short i,j;
      String message;
      if(rgame==null) return;
      int wordtot = wc.length;
      if(gametot1 < wordtot) message = rgame.getParm("tryagain");
      else if(gametot1 < wordtot*2) message = rgame.getParm("good");
      else if(gametot1 < wordtot*3) message = rgame.getParm("verygood");
      else     message = rgame.getParm("wonderful");
      message =  rgame.getParm("totscore")+" "+String.valueOf(gametot1)+"  -  "+message;
      clearexit = true;
      showmessage(message,mover.WIDTH/6,mover.HEIGHT/16,mover.WIDTH*5/6,mover.HEIGHT/5);
      score(gametot1/4);
      this.exitbutton(mover.HEIGHT*3/4);
   }
   //-----------------------------------------------------------
   class suffc extends mover {
      String val;
      int startx,starty;
      suffc(short suffno) {
         super(false);
         val = suffs[suffno];
         w = suffwidth;
         h = suffheight;
         startx = mover.WIDTH-suffwidth;
         starty = suffno * suffheight + mover.HEIGHT*2/screenheight;
      }
      public void mouseClicked(int x, int y) {
         short i;
         if(gamePanel.showSprite && !gamePanel.movesWithMouse(this) && wordnum>=0) {
            if(val.equals(wc[wordnum].suff)) {
                clickonrelease = true;
                gamePanel.attachToMouse(this);
                help("help_addsuff3");
                for(i=0;i<sufftot;++i) {
                   if(sufflist[i] != this) {
                     removeMover(sufflist[i]);
                   }
                }
            }
            else {
               noise.groan();
               error();
               ++wc[wordnum].errors;
            }
         }
         else if (gamePanel.movesWithMouse(this)) {
            if(wordnum>=0 && wc[wordnum].testfitclick(x,y)) {
                gamePanel.finishwith(this);
            }

         }
      }
      public void paint(Graphics g,int x, int y, int w, int h) {
         g.setFont(wordfont);
         if(!gamePanel.movesWithMouse(this)) {
            g.setColor(yellow());
            g.fillRect(x,y,w,h);
            g.setColor(Color.black);
            g.drawString("-"+val, x, y + metrics.getMaxAscent());
         }
         else {
            int xx = metrics.charWidth('-');
            if(wordnum>=0 && wc[wordnum].testfit(super.x*screenwidth/mover.WIDTH + xx,super.y*screenheight/mover.HEIGHT)) {
               gamePanel.finishwith(this);
            }
            else {
               g.setColor(Color.black);
               g.drawString(val, x+xx, y + metrics.getMaxAscent());
            }
         }
      }
   }
   //-----------------------------------------------------------
   class wordc extends mover {
      boolean active,done,leavealone;
      long startscratch;
      boolean q_double,no_double,no_double_longvowel,no_double_cons,no_double_2cons,no_double_w;
      boolean q_strip_e, keep_e_cons, keep_e_cg,keep_e_long;
      boolean q_y_to_i,keep_y;
      boolean q_i_to_y,keep_i;
      boolean q_f_to_v,q_f_to_v2,keep_f;
      boolean altselected,oldselected, e_stripped, doubled, notsure;
      byte showerror;
      short thisnum;
      boolean marked;
      byte score;
      int endx=-1,endy;
      int errors;
      long starttime,elapsed;
      u.star stars[] =  new u.star[5];

      // where value is given after '='
      boolean result_given;
      boolean q2_double, want_double, is_doubled,is_doubled_last;
      int doublepos;
      boolean q2_lose, want_lose, is_lost, is_lost_last ;
      int losepos;
      boolean q2_change, want_change, is_changed, is_changed_last;
      int changepos;
      String startfroms;
      char startfrom[];
      //---------------------------------


      String val,suff,valsuff,result;
      Color color = u.backgroundcolor(null);
      Color color2 = u.backgroundcolor(null);
      String altchar;
      String lastcs;
      Image scratchimage;
      short oflip[];

      wordc(short wnum) {
          super(false);
          thisnum = wnum;
          val = words[wnum];
          suff = suffs[wordsuff[wnum]];
          leavealone = leavealoneb[wnum];
          if(results[wnum] != null) {
            startfrom = (startfroms=val+suff).toCharArray();
            settype_result( (val + suff).toCharArray(), results[wnum].toCharArray(),
                           bad[wnum] != null ? bad[wnum].toCharArray() : null);
            result = results[wnum];
          }
          else settype();
          w = acrossw;
          h = downh;
      }
      void settype_result(char s[],char want[],char bad[]) {  // determine what to do when result is given
        int i,j,k;
        result_given = true;
        if(bad != null) {
          getdiff(s,bad,false);
        }
        getdiff(s,want,true);
      }
      void getdiff(char s[],char s2[],boolean good) {
        int i,j,k;
          for (i = j = 0; i < s.length && j < s2.length; ++i,++j) {
            if (s[i] != s2[j]) {
              if(i>0 && j>0 && s2[j] == s2[j-1] && s2[j-1] == s[i-1]) { // check for doubling
                q2_double = true;
                doublepos = i-1;
                if(good) want_double = true;
                --i;
              }
              else if (u.vowels.indexOf(s[i]) >= 0
                       || i<s.length-1 && new String(s,i+1,s.length-i-1).equals(new String(s2,j,s2.length-j))) {
                            // check for losing a vowel (or consonent if rest is the same)
                q2_lose = true;
                losepos = i;
                if(good) want_lose = true;
                --j;
              }
              else {    // assume change
                q2_change = true;
                changepos = i;
                altchar = new String(s2,j,1);
                if(good) want_change = true;
              }
            }
          }
      }
      void settype() {
         int len = val.length();
         char last = val.charAt(len-1);
         char last2 = val.charAt(len-2);
         lastcs = val.substring(len-1);
         char suff1 = suff.charAt(0);
         if(u.vowelsy.indexOf(last) <  0) {
           q_double = true;
           if(leavealone) no_double= true;
           else if( u.vowelsy.indexOf(suff1) < 0) no_double=no_double_cons = true;
           else if(u.vowels.indexOf(last2) < 0) no_double=no_double_2cons = true;
           else if(len == 2 || u.vowels.indexOf(val.charAt(len-3)) >= 0)
               no_double=no_double_longvowel = true;
           else if(last == 'w' || last == 'x'|| last == 's') no_double=no_double_w = true;
         }
         if(last == 'e' && last2 == 'i') {
            q_i_to_y = true;
            altchar = "y";
            if(suff1 != 'i') keep_i = true;
         }
         if(last == 'e') {
             q_strip_e = true;
             if (u.vowelsy.indexOf(suff1) < 0)  keep_e_cons = true;
             else if("cg".indexOf(last2) >= 0 && "aou".indexOf(suff1) >=0)
                          keep_e_cg = true;
             else if(len == 2
                     || suff1 != 'e'
                         && u.vowelsy.indexOf(last2) >= 0
                         && (!q_i_to_y || keep_i))
                 keep_e_long = true;
         }
         if(last == 'y') {
            q_y_to_i = true;
            altchar = "i";
            if(suff1 == 'i' || u.vowels.indexOf(last2) >=  0) keep_y = true;
         }
         if(last=='f') {
           q_f_to_v = true;
           altchar = "v";
           if(!suff.equals("es")) keep_f = true;
         }
         if(last2=='f' && last=='e') {
           q_f_to_v2 = true;
           altchar = "v";
           if(!suff.equals("s")) keep_f = true;
         }
         valsuff = val.substring(0,len-2);
         if(q_i_to_y && !keep_i) valsuff +=  "y";
         else if(q_f_to_v2 && !keep_f) valsuff +=  "v";
         else valsuff = valsuff+val.substring(len-2,len-1);
         if(q_y_to_i && !keep_y) valsuff += "i";
         else if(q_f_to_v && !keep_f) valsuff += "v";
         else if(!q_strip_e || keep_e_cons || keep_e_cg || keep_e_long)
                                       valsuff += lastcs;
         if(q_double && !no_double) valsuff+=lastcs;
         valsuff += suff;
      }
      boolean testfit(int xin, int yin) {
         int ww = metrics.stringWidth(val+suff+"w");
         int wh = metrics.getHeight();
         int wx = (x+w/2)*screenwidth/mover.WIDTH - ww/2 + metrics.stringWidth(val);
         int wy = (y+h/2)*screenheight/mover.HEIGHT - wh/2;
         boolean ret = Math.abs(xin - wx) < metrics.stringWidth("w")/2
              && Math.abs(yin - wy) < wh/2;
         if(ret) {
            mx = xin - gamePanel.mousexs;
            my = yin - gamePanel.mouseys;
            active = true;
            keepMoving = true;
            ended=false;
            noise.plop();
            help("help_addsuff4");
            lastchange = gtime;
         }
         return ret;
      }
      boolean testfitclick(int xin, int yin) {
         int ww = metrics.stringWidth(val+suff+"w");
         int wh = metrics.getHeight();
         int wx = (x+w/2)*screenwidth/mover.WIDTH - ww/2 + metrics.stringWidth(val);
         int wy = (y+h/2)*screenheight/mover.HEIGHT - wh/2;
         boolean ret = Math.abs(xin - wx) < metrics.stringWidth("ww")
              && Math.abs(yin - wy) < wh;
         if(ret) {
            mx = xin - gamePanel.mousexs;
            my = yin - gamePanel.mouseys;
            active = true;
            keepMoving = true;
            ended=false;
            noise.plop();
            help("help_addsuff4");
            lastchange = gtime;
         }
         return ret;
      }
      //-------------------------------------------------
      byte errortype() {
          if(q_double) {
             if(doubled) {
                 if(no_double_cons) return SUFFCONS;
                 if(no_double_2cons) return CONS2;
                 if(no_double_w) return W;
                 if(no_double_longvowel) return LONGVOWEL;
                 else if(no_double) return NOSHOW;
             }
             else if(!no_double) return MUSTDOUBLE;
         }
         if (q_strip_e) {
            if(e_stripped) {
               if(keep_e_cons) return SUFFCONS;
               if(keep_e_cg)   return CG;
               if(keep_e_long)   return LONGVOWELE;
            }
            else if(!keep_e_cons && !keep_e_cg&& !keep_e_long) return MUSTSTRIP;
         }
         if(q_y_to_i && (keep_y && altselected || !keep_y && oldselected)
                || q_i_to_y && (keep_i && altselected || !keep_i && oldselected)
                || (q_f_to_v || q_f_to_v2) && (keep_f && altselected || !keep_f && oldselected)) {
             return NOSHOW;
         }
         return 0;
      }
      void mouseClick(int x, int y) {
         if(active) {
          if(gtime < lastchange) return;
          if(result_given) {
             if(q2_double && notsure) {
               noise.beep();
               return;
             }
             if(q2_double && want_double != is_doubled
                || q2_lose && want_lose != is_lost
                || q2_change && want_change != is_changed) {
               noise.groan();
               error();
               ++errors;
               return;
             }
           }
           else {
             if (q_double && notsure) {
               noise.beep();
               return;
             }
             if ( (showerror = errortype()) != 0) {
               noise.groan();
               error();
               ++errors;
               return;
             }
           }
           clickonrelease = false;
           elapsed = (gtime - starttime);
           if (elapsed > GOODTIME * 4)
             score = 1;
           else if (elapsed < 0)
             score = 5;
           else
             score = (byte) (5 - elapsed / GOODTIME);
           score = (byte) Math.max(0, score - errors);
           gamescore(score);
           active = false;
           startscratch = gtime;
           showerror = 0;
           gamePanel.showSprite = true;
           if (++donetot >= wtot) {
             javax.swing.Timer etimer = new javax.swing.Timer(FLIPTIME * 3 / 2,
                 new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                      endmessage();
                    }
                 }
             );
             etimer.setRepeats(false);
             etimer.start();
             help(null);
           }
           else {
        if(delayedflip && !completed){
          flip();
        }
             gotword = false;
             help("help_addsuff5");
           }
           wordnum = -1;
//startPR2005-04-18^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
           if(Demo_base.isDemo){
             if (Demo_base.demoIsReadyForExit(0)) return;
           }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        }
        else if(gamePanel.showSprite && !done && startscratch == 0 && wordnum<0) {
            wordnum = thisnum;
            gotword = true;
            if(result_given) {
              currword = new word(result, databases[wordnum]);
              currword.say();
            }
            else {
              currword = new word(valsuff, databases[wordnum]);
              currword.say();
            }
            addsuff();
            starttime = gtime;
        }
      }
      public void paintscore(Graphics g,int x, int y, int w, int h) {
         int yy=y,dy,adjy,i;
         String s;
         g.setColor(scorecolors[score]);
         g.fillRect(x,y,w,h);
         dy = h/((errors>0)?6:5);
         if(errors>0) g.setColor(Color.yellow);
         else g.setColor(Color.white);
         g.setFont(scorefont);
         FontMetrics mm = g.getFontMetrics();
         adjy = dy/2 - mm.getHeight()/2 + mm.getMaxAscent();
          if(errors > 0) {
            s = String.valueOf(errors) + " "+rgame.getParm((errors==1)?"mistake":"mistakes");
            g.drawString(s,x+w/2-mm.stringWidth(s)/2,yy+adjy);
            yy+=dy;
         }
         s = rgame.getParm("time") + ' '+String.valueOf(elapsed/1000) + ' '+rgame.getParm("secs");
         g.drawString(s,x+w/2-mm.stringWidth(s)/2,yy+adjy);
         yy+=dy;
         int ti = Math.min(12,(int)(elapsed/1000));
         int bw = Math.min(w/12-2,dy);
         int xx = x + w/2 - ti*(bw+2)/2;
         g.setColor(Color.black);
         for(i=0;i<ti;++i) {
            g.fillRect(xx+i*(bw+2), yy,bw,bw);
         }
          yy+=dy*2;
         g.setColor(white());
         s = rgame.getParm("score")+"  " + String.valueOf(score);
         g.drawString(s,x+w/2-mm.stringWidth(s)/2,yy+adjy);
         yy+=dy;
         xx = x + w/2 - score*w/10;
         for(i=0;i<score;++i) {
            if(stars[i] == null) stars[i] = new u.star(w/5*mover.WIDTH/screenwidth,dy*mover.HEIGHT/screenheight,Color.yellow);
            stars[i].paint(g, xx+i*w/5, yy,w/5,dy);
         }
      }
      public void paint(Graphics g,int x, int y, int w, int h) {
         if(active && result_given) {
           paint2(g,x,y,w,h);
           return;
         }
         int ww = metrics.stringWidth(val+suff+"w");
         int wh = metrics.getHeight();
         int wx = x+w/2-ww/2,wwx = wx;
         int wy = y+h/2-wh/2;
         int len = val.length();
         int yextra = metrics.getMaxAscent();
         String s;
         int i,j,suffx;
         int last3x= wwx + ((len > 2) ?
                metrics.stringWidth(val.substring(0,len-3))
                 : 0);
         int last2x=wwx+metrics.stringWidth(val.substring(0,len-2));
         int last1x=wwx+metrics.stringWidth(val.substring(0,len-1));
         g.setFont(wordfont);
         if(active) {
            int mmx = mx + gamePanel.mousexs;
            int mmy = my + gamePanel.mouseys;
            g.setColor(color);
            g.fillRect(x,y,w,h);
            g.setColor(Color.black);
            if(q_f_to_v2 || q_i_to_y) {
               g.drawString(s=val.substring(0,len-2),wx,wy+yextra);
               wx += metrics.stringWidth(s);
               int wwy = Math.max(wy, Math.min(wy+wh,mmy));
               g.drawString(val.substring(len-2,len-1),wx,wwy+yextra);
               g.drawString(altchar,wx,wwy-wh+yextra);
               wx += metrics.stringWidth(altchar);
               last1x = wx;
               altselected = wwy > wy+wh/2;
               oldselected = wwy < wy+wh/2;
            }
            else {
               g.drawString(s=val.substring(0,len-1),wx,wy+yextra);
               wx += metrics.stringWidth(s);
            }
            if(q_f_to_v || q_y_to_i) {
               last1x = wx;
               int wwy = Math.max(wy, Math.min(wy+wh,mmy));
               g.drawString(lastcs,wx,wwy+yextra);
               g.drawString(altchar,wx,wwy-wh+yextra);
               wx += metrics.stringWidth(altchar);
               altselected = wwy > wy+wh/2;
               oldselected = wwy < wy+wh/2;
            }
            else if(q_strip_e &&  mmx < wx + metrics.charWidth('e')/2) {
               e_stripped = true;
            }
            else {
               e_stripped = false;
               g.drawString(lastcs,wx,wy+yextra);
            }
            wx += (j=metrics.stringWidth(lastcs));
            int minx = q_strip_e? (wx - j):wx;
            if(q_double) {
               if(mmx > wx) {
                  g.drawString(lastcs, Math.min(wx,mmx-j), wy+yextra);
                  doubled = mmx > wx + j*2/3;
                  notsure = !doubled && mmx > wx + j/3;
                  wx += j;
               }
               else {doubled = false; notsure = false;}
            }
            g.drawString(suff, suffx = Math.min(wx,Math.max(minx,mmx)),wy+yextra);
            g.setColor((u.rand(2)==0)?white():Color.black);
            if(showerror != 0) {
              switch(showerror) {
                case SUFFCONS:
                   g.drawString(suff.substring(0,1),suffx,wy+yextra); break;
                case MUSTSTRIP:
                   if(!e_stripped)g.drawString(val.substring(len-1),last1x,wy+yextra); break;
                case CONS2:
                   g.drawString(val.substring(len-2),last2x,wy+yextra); break;
                case W :
                   g.drawString(val.substring(len-1),last1x,wy+yextra); break;
                case LONGVOWEL:
                   g.drawString(val.substring(len-3,len-1),last3x,wy+yextra); break;
                case LONGVOWELE:
                   if(len>2)g.drawString(val.substring(len-2),last2x,wy+yextra); break;
                case CG:
                   g.drawString(val.substring(len-2,len-1),last2x,wy+yextra); break;
                case MUSTDOUBLE:
                   if(!doubled) {
                      int x1 = suffx + metrics.charWidth(suff.charAt(0))/2;
                      int x2 = last2x + metrics.charWidth(val.charAt(len-2))/2;
                      g.drawLine(x2,wy,x2,wy-wh/3);
                      g.drawLine(x2,wy-wh/3,x1,wy-wh/3);
                      g.drawLine(x1,wy,x1,wy-wh/3);
                      g.drawString(suff.substring(0,1),suffx,wy+yextra);
                      g.drawString(val.substring(len-2,len-1),last2x,wy+yextra);
                   }
            }
         }
         byte newerrtype = errortype();
         if(newerrtype != lasterrtype) {
           lastchange = gtime;
           lasterrtype = newerrtype;
         }
      }
      else if(startscratch > 0 ) {
         paintscore(g,x,y,w,h);
         if(gtime > startscratch+FLIPTIME) {
             startscratch = 0;
             scratchimage = null;
             oflip = null;
             done = true;
             keepMoving = false;
         }
         else {
            int xx,yy,wsc;
            if(scratchimage == null) {
               scratchimage = createImage(w,h);
               Graphics scratchg = scratchimage.getGraphics();
               scratchg.setColor(color);
               scratchg.setFont(wordfont);
               scratchg.fillRect(0,0,w,h);
               scratchg.setColor(Color.black);
               scratchg.drawString(result_given?result:valsuff, wx-x, wy+yextra-y);
               oflip = u.shuffle(u.select((short)h,(short)h));
             }
             int lim = h-(int)(h*(gtime-startscratch)/FLIPTIME);
             for(i=0;i<lim;++i) {
               g.drawImage(scratchimage, x, y+oflip[i], x+w,y+oflip[i]+1,
                              0,oflip[i],w,oflip[i]+1,null);
             }
         }
      }
      else if(done) {
            paintscore(g,x,y,w,h);
      }
      else {
            g.setColor(color);
            g.fillRect(x,y,w,h);
            g.setColor(Color.black);
            g.drawString(val,wx,wy+yextra);
      }
   }

     // special paint for foreign versions (where result is given)
   public void paint2(Graphics g,int x, int y, int w, int h) {
      int ww = metrics.stringWidth(startfroms+"w");
      int wh = metrics.getHeight();
      int wx = x+w/2-ww/2,wwx = wx;
      int wy = y+h/2-wh/2;
      int len = startfrom.length,clen;
      int yextra = metrics.getMaxAscent();
      String s;
      int i,j,suffx;
      g.setFont(wordfont);
      int mmx = mx + gamePanel.mousexs;
      int mmy = my + gamePanel.mouseys;
      int wwy = Math.max(wy, Math.min(wy+wh,mmy));
      g.setColor(color);
      g.fillRect(x,y,w,h);
      g.setColor(Color.black);
      for(i=0;i<len;++i) {
        clen = metrics.charWidth(startfrom[i]);
         if(q2_change && i == changepos) {
            g.drawChars(startfrom,i,1,wx,wwy+yextra);
            g.drawString(altchar,wx,wwy-wh+yextra);
            is_changed = wwy > wy+wh/2;
            wx += Math.max(clen, metrics.stringWidth(altchar));
         }
         else if(q2_lose && i==losepos &&  mmx < wx + metrics.charWidth(startfrom[i])/4) {
            is_lost = true;
         }
         else if(q2_lose && i==losepos) {
            is_lost = false;
            g.setClip(wx,0,mmx-wx,screenheight);
            g.drawChars(startfrom,i,1,wx,wy+yextra);
            g.setClip(null);
            wx += Math.min(clen, mmx-wx);
         }
         else {
           g.drawChars(startfrom,i,1,wx,wy+yextra);
           wx += clen;
         }
         if(q2_double && i == doublepos) {
            if(mmx > wx) {
              j = Math.min(clen,mmx-wx);
              g.drawChars(startfrom,i,1,wx+j-clen,wy+yextra);
              is_doubled = mmx > wx + j*2/3;
              notsure = !is_doubled && mmx > wx + j/3;
              wx += j;
            }
            else {is_doubled = false; notsure = false;}
         }
      }
      if(is_doubled != is_doubled_last || is_lost != is_lost_last || is_changed != is_changed_last) {
        lastchange = gtime;
        is_doubled_last= is_doubled;
        is_lost_last = is_lost;
        is_changed_last = is_changed;
      }
   }
}

}
