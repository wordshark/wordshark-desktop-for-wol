package shark;

import java.awt.*;
import java.awt.event.*;

public class sentence extends mover {
   public static final String separators = " ,.;:|?!*/[]{}()\"~�";
   public static final String makesentence = " ,.;:|?!\"~#";
   public static final String TEST_PREFIX = "@=";
   public String database;
   public static long endpeep;
   public short type;
   static final short MAXBETWEEN = 4;
   public static final short SIMPLECLOZE = 0;
   public static final short CLOZE = 1;
   public static final short HIDDEN = 2;
   public static final short TYPE = 4;
   public static final short JUMBLED = 8;
   public static final short PICK = 16;

   public seg segs[] = new seg[0], hidden[], select[], jumbled[],markedseg;
   public String chunks[];
   public String val;  //original line
   String sayval;  //original line - no full stop
   short segpos,typingpos=-1,foundpos=-1,linetot,errorchars;
   char errorchval;
   long lasterrtime,lasttyped,starttimepick;
   int yinc,ystart;
   static final byte WORD = 0;
   static final byte BADWORD = 1;
   static final byte PUNC = 2;
   static final byte LISTCHOICE = 3;
   short maxextra = 4;
   public Font font;
   public FontMetrics metrics;
   public String crosswords[] = new String[0];
   public word crosswd[] = new word[0];
   public short doneword = -1; // number of word (in crossword) just finished;
   public boolean finished,hadkeyhit,cancel;
   public int exity,finishedy;
   int hiddenyinc;
   int oldscreenwidth;
   int downx,downy;
   long downtime;
   int lastclickx,lastclicky;
   public sharkImage picture;
   public boolean dontsee,donthear;
   public boolean oneletter;                   // rb 16/1/08
   boolean wide;
   public boolean nohelp;
   word savewordlist[];

   public sentence(String v, String database1)  {
      super(false);
      short i;
      int j,k;
      char ch;
      database = database1;
      keepMoving = true;
      sayval = v;
      if((j = v.indexOf('{')) >= 0 && (k = v.indexOf('}',j+1)) > 0) {
        picture = new sharkImage(v.substring(j+1,k),false);
        if(picture.p == null) picture = null;                           // v5  rb 7/3/08
        v = v.substring(0,j) + v.substring(k+1);
      }
      i = (short)v.length();
      if(sentence.separators.indexOf(ch = v.charAt(i-1)) < 0
           || ch == '/')
                v = v + ".";
      if(v.indexOf('[') >= 0) type = TYPE;
      else if(v.indexOf('~') >= 0) type = JUMBLED;
      else if(v.indexOf('/') >= 0) {
          type = CLOZE;
          if(v.startsWith("!")) { v=v.substring(1); dontsee = true;}
      }
      else type = HIDDEN;
      segs = splitup(val=v);
      if(type == CLOZE) {
         for(i=0;i<segs.length;++i) {
            if(segs[i].listchoice) {
              type = SIMPLECLOZE;
              if(segs[i].len < 2) {          // start rb 16/1/08
                  oneletter = true;
              }                              // end rb 16/1/08
              break;
            }
         }
      }
      if(sharkStartFrame.currPlayTopic != null) extractCrosswords();
   }
   public sentence(String v)  {   // special for single word
      super(false);
      short i;
      int j,k;
      char ch;
      keepMoving = true;
      sayval = v;
      if((j = v.indexOf('{')) >= 0 && (k = v.indexOf('}',j+1)) > 0) {
        picture = new sharkImage(v.substring(j+1,k),false);
        if(picture.p == null) picture = null;    // v5  rb 7/3/08
        v = v.substring(0,j) + v.substring(k+1);
      }
      i = (short)v.length();
      if(v.indexOf('[') >= 0) type = TYPE;
      else if(v.indexOf('~') >= 0) type = JUMBLED;
      else if(v.indexOf('/') >= 0) type = CLOZE;
      else type = HIDDEN;
      segs = splitup(val=v);
      if(type == CLOZE) {
         for(i=0;i<segs.length;++i) {
            if(segs[i].listchoice) {
                type = SIMPLECLOZE;
                break;}
         }
      }
      if(sharkStartFrame.currPlayTopic != null) extractCrosswords();
   }
   //------------------------------------------------------------------
  void extractCrosswords() {
      short i,j;
      String s;
      word[] list = sharkStartFrame.currPlayTopic.getAllWords(true);
      String origwords[] = new String[list.length];
      for(i=0;i<list.length;++i)    
          origwords[i] = list[i].v();
      for(i=0;i<segs.length;++i) {
         if(segs[i].goodword
             && (type == HIDDEN || type == JUMBLED || segs[i].typein
                  || segs[i].firstchoice || type==PICK && !segs[i].extra))  {
           segs[i].crosswordno = (short)crosswords.length;
           s = stripquotes(segs[i].val);
           if((j=u.findString(origwords,s))>=0
                && !s.equals(origwords[j]))
               s = origwords[j];
           crosswords = u.addString(crosswords,s);
           crosswd = u.addWords(crosswd,segs[i].wd);
//         if(crosswords.length == 1) crosswords[0] = crosswords[0].toLowerCase();
         }
      }
   }
   //------------------------------------------------------------------
         // sets SIMPLE CLOZE ready to run for 'addletters' game
   public Rectangle setup(int width, int height, int maxlen, String wantword) {
     Rectangle ret = null;
     segpos = 0;
     cancel = false;
     short slen = 0;
     int i, j, k, ypos, linelen, linecount, gap;
     int sw = manager.screenwidth;
     int sh = manager.screenheight;
     linetot = (short) (segs[segs.length - 1].line*2 + 1); // allow blank line between each
     w = width;
     h = height;
     oldscreenwidth = manager.screenwidth;
     int ww = width*manager.screenwidth/mover.WIDTH;
     font = sizeFont(null, "aaaaaaaaaaaaa", width, height/linetot);
     for (j = 0; j < linetot; ++j) { // get font size
         String s = "";
         int extra = 0;
         for (i = 0; i < segs.length; ++i) {
           if (segs[i].line == j) {
             if (segs[i].firstchoice) {
               if(segs[i].val.equalsIgnoreCase(wantword)) extra = maxlen;
               else {
                   s = s + segs[i].val;
                   segs[i].choice =segs[i].firstchoice = false;
               }
             }
             else    if (!segs[i].choice)    s = s + segs[i].val;
           }
         }
         while(metrics.stringWidth(s) + u.getadvance(metrics)*extra > ww) {
//startPR2007-11-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//           font = new Font(font.getName(),font.getStyle(),font.getSize()-1);
                 font = font.deriveFont((float)font.getSize()-1);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
           metrics = manager.getFontMetrics(font);
         }
     }
     yinc = Math.min(metrics.getHeight() * mover.HEIGHT / sh * 5 / 4,
                     height / linetot);
     ystart = (height - yinc * linetot) / 2;
     for (j = 0; j < linetot; ++j) {
       linelen = 0;
       for (i = 0; i < segs.length; ++i) {
           if (segs[i].line == j) {
             if (segs[i].firstchoice) {
               if(segs[i].val.equalsIgnoreCase(wantword)) linelen += u.getadvance(metrics)*maxlen;
            }
             else {
               if (!segs[i].choice) {
                 linelen += metrics.stringWidth(segs[i].val);
               }
           }
         }
       }
       linelen = (width - linelen * mover.WIDTH / manager.screenwidth) /2;
       for (i = 0; i < segs.length; ++i) {
           if (segs[i].line == j) {
             if (segs[i].firstchoice) {
               if(segs[i].val.equalsIgnoreCase(wantword)) {
                 int wi = u.getadvance(metrics)*maxlen*mover.WIDTH/manager.screenwidth;
                 ret = new Rectangle(linelen, ystart + yinc * segs[i].line * 2,wi,yinc);
                 segs[i].slotwidth = wi;
                 linelen += wi;
               }
             }
             else
               if (!segs[i].choice) {
                 segs[i].x = linelen;
                 segs[i].y = ystart + yinc * segs[i].line * 2;
                 linelen +=
                     (segs[i].slotwidth = metrics.stringWidth(segs[i].val) *
                      mover.WIDTH / manager.screenwidth);
               }
           }
         }
     }
     return ret;
   }
   //------------------------------------------------------------------
         // sets ready to run for crossword
   public void setup(int width, int height, word[] wordlist) {
      segpos = 0;
      cancel = false;
      short slen=0;
      int i,j,k,ypos,linelen,linecount,gap;
      int sw = manager.screenwidth;
      int sh = manager.screenheight;
      wide = width==mover.WIDTH;
      String wordlistv[]=null;
      w = width;
      h = height;
      savewordlist = wordlist;
      
      int answerno = -1;

      if(type==SIMPLECLOZE) {
          
          
         
         if(manager.currgame.rgame.sentDistractorNo>0){
            answerno = manager.currgame.rgame.sentDistractorNo;
         }
         else{
            answerno = wordlist.length;
         }          
          
          
            wordlistv = new String[answerno];
            for(i=0;i<answerno;++i) wordlistv[i] = wordlist[i].v();
      }
      font = sizeFont(null, val, width, height/(linetot+2));
      oldscreenwidth = manager.screenwidth;
      foundpos = doneword = typingpos = -1;
      manager.showSprite = true;

      if(type==HIDDEN) {
         manager.currgame.setpeep(false);
         manager.currgame.setlisten(false);
         manager.currgame.help("help_tracking");
         String garbage[];
//         font=null;
         short order[];
         short orderno=0;
         topic t  = new topic(u.absoluteToRelative(sharkStartFrame.publicTopicLib[0]),"garbage",null,null);
         if(t != null) {
            word[] garbagew = t.getAllWordsNoSent();
            garbage = new String[garbagew.length];
            for(i=0;i<garbagew.length;++i) garbage[i] = garbagew[i].v();
         }
         else {
            garbage = new String[] {"aa","zuu","eeigh"};
         }
         order = u.shuffle(u.select((short)garbage.length, (short)garbage.length));
         seg[] oldsegs = segs;
         segs = new seg[0];
         for(i=0;i<=oldsegs.length;++i) {
            if(i==oldsegs.length || oldsegs[i].goodword) {
               for(j=u.rand(MAXBETWEEN+1);j>0;--j) {
                  addseg(new seg(garbage[order[(orderno++)%order.length]],BADWORD,false,0));
               }
               if(i<oldsegs.length)  addseg(new seg(oldsegs[i]));
            }
         }
         hidden = segs;
         segs = oldsegs;
         short linetot = (short)Math.max(1,(short)Math.sqrt(hidden.length));
         int chartot=0;
         for(i=0;i<hidden.length;++i) {
            chartot += hidden[i].val.length() + 1;
         }
         int perline = chartot/linetot;
         short currline = 0;
         for(i=0,chartot=0;i<hidden.length;++i) {
             if(chartot + hidden[i].val.length()/2 > perline*(currline+1)
                                      && currline<linetot-1) {
                ++currline;
             }
             hidden[i].line = currline;
             hidden[i].startpos = i;
             hidden[i].tracking = true;
             chartot+= hidden[i].val.length()+1;
         }
         for(i=j=0;i<linetot;++i) {
             String s = null;
             for(;j<hidden.length && hidden[j].line == i;++j) {
                if (s == null) s = hidden[j].val;
                else  s = s + " " + hidden[j].val;
             }
             if(s != null) font = sizeFont(font, s, width*7/8, height/(linetot+2));
         }
         hiddenyinc = height/(linetot+2);
         for(i=j=0;i<linetot;++i) {
             linelen = 0;
             linecount = 0;
             k = j;
             for(;j<hidden.length && hidden[j].line == i;++j) {
                linelen += metrics.stringWidth(hidden[j].val);
                ++linecount;
             }
             gap = (width*7/8 - linelen*mover.WIDTH/sw)/Math.max(1,linecount-1);
             linelen=width/16;
             for(;k<j;++k) {
                hidden[k].x = linelen;
                hidden[k].y = i*hiddenyinc;
                linelen += (hidden[k].slotwidth=metrics.stringWidth(hidden[k].val)*mover.WIDTH/sw)+gap;
             }
         }
         linelen = (width - metrics.stringWidth(val)*mover.WIDTH/sw)/2;
         for(i=0;i<segs.length;++i) {
            segs[i].y = height - height/(linetot+2);
            segs[i].x = linelen;
            linelen += metrics.stringWidth(segs[i].val)*mover.WIDTH/sw;
            segs[i].dontshow = true;
         }
         nextfind();
         exity = height*linetot/(linetot+2);
      }
      else if(type == TYPE) {
         manager.currgame.setpeep(true);
         manager.currgame.setlisten(true);
         manager.currgame.help("help_type");
         manager.moverwantskey = this;
         int textlen=0;
         linetot = 2;
         font = sizeFont(null,val,width,height/2);
         for(i=0;i<segs.length;++i) {
            textlen += (segs[i].slotwidth = metrics.stringWidth(segs[i].val)*mover.WIDTH/sw);
         }
         textlen = (width - textlen)/2;
         for(i=0;i<segs.length;++i) {
             segs[i].y = height/2;
             segs[i].x = textlen;
             textlen += segs[i].slotwidth;
         }
         keypad.activate(manager.currgame,keypad.addmore(new char[] {(char)keypad.SHIFT,' '},
                                            segs));
         nexttypingpos();
         exity = height*7/8;
      }
      else if(type == JUMBLED) {
         manager.currgame.setpeep(false);
         manager.currgame.setlisten(true);
         manager.currgame.clickonrelease = false;
          manager.currgame.help("help_jumbledsent");
         short tot=0;
         for(i=0;i<segs.length;++i)
            if(!segs[i].val.equals(" ")) ++tot;
         jumbled = new seg[tot];
         tot=0;
         for(i=0;i<segs.length;++i) {
            if(!segs[i].val.equals(" "))
               jumbled[tot++] = segs[i];
         }
         linelen=0;
         for(i=0;i<jumbled.length;++i) {
            linelen += metrics.stringWidth(jumbled[i].val)*mover.WIDTH/sw;
         }
         gap = (width-linelen)/(tot+1);
         linelen = Math.max(0,gap);
         ypos = height/2;
         short order[] = u.shuffle(u.select(tot, tot));
         for(short ii=0;ii<jumbled.length;++ii) {
            i = order[ii];
            jumbled[i].y = ypos;
            jumbled[i].x = linelen;
            linelen += metrics.stringWidth(jumbled[i].val)*mover.WIDTH/sw + gap;
         }
         exity = height*7/8;
      }
      else if(type == PICK) {
         manager.currgame.setpeep(true);
         manager.currgame.setlisten(true);
         manager.currgame.help("help_cloze");
         String s = new String();
         short tot=0;
         for(i=0;i<segs.length;++i) {
            if(!segs[i].punc ) ++tot;
            if(!segs[i].extra) {
               s = s + segs[i].val;
            }
         }
         linetot = (short)(tot/4 + 3);
         font = sizeFont(null, s, width, height/(linetot));
         linelen = 0;
         for(i=0;i<segs.length;++i) {
            if(segs[i].punc )  segs[i].x = linelen;
            if(!segs[i].extra) {
               linelen += metrics.stringWidth(segs[i].val)*mover.WIDTH/sw;
            }
         }
         int starty = Math.max(0, height - metrics.getHeight()*mover.WIDTH/sw*3/2*tot)/2;
         int incy = (height-starty*2) /linetot;
         short o[] = u.shuffle(u.select((short)segs.length,(short)segs.length));
         for(i=0,tot=0;i<segs.length;++i) {
            if(!segs[o[i]].punc ) {
                segs[o[i]].line = tot/4;
                 ++tot;
            }
            else {
                segs[o[i]].line = linetot - 1;
                segs[o[i]].dontshow = true;
            }
            segs[o[i]].y = starty + incy*segs[o[i]].line;
         }
         finishedy = starty + incy*(linetot-1);
         linelen = 0;
         for(j=0;j<linetot-2;++j) {
            linecount = 0;
            linelen = 0;
            for(i=0;i<segs.length;++i) {
                if(segs[o[i]].line == j) {
                   linelen += metrics.stringWidth(segs[o[i]].val)*mover.WIDTH/sw;
                   ++linecount;
                }
            }
            if(linecount == 0) continue;
            gap = (width - linelen)/linecount ;
            linelen = gap/2;
            for(i=0;i<segs.length;++i) {
                if(segs[o[i]].line == j) {
                   segs[o[i]].x = linelen;
                   linelen += metrics.stringWidth(segs[o[i]].val)*mover.WIDTH/sw + gap;
                }
            }
         }
         nextfind();
         exity = height*7/8;
      }
      else if(type == SIMPLECLOZE || type == CLOZE) {
         if(type!=SIMPLECLOZE)
         manager.currgame.setpeep(false);
         manager.currgame.setlisten(!donthear);
         if(!nohelp) manager.currgame.help(donthear?"help_clozesee":"help_cloze");
         int  perline = wide?8:4,addlines=1;

         
         if(type==SIMPLECLOZE) {
           for(i=0;i<answerno;++i) {
               if(wordlistv[i].length() > 6) perline = wide?5:3; break;
            }
            addlines = (answerno+perline-1)/perline;
            perline = (answerno+addlines-1)/addlines;
         }
         linetot = (short)(segs[segs.length-1].line + ((type==CLOZE)?1:addlines) + (wide?2:3));
         font = sizeFont(null,"aaaaaaaaaaaaa",width,height);
         if(type==SIMPLECLOZE) {
   //         slen = (short)wordlist.length;
            slen = (short)answerno;
            select = new seg[slen];
            do{
                short o[] = u.shuffle(u.select((short)wordlist.length,slen ));            
                for(i=0;i<slen;++i) {
                   select[i] = new seg(wordlist[o[i]].vsay(),WORD,false,linetot-1-i/perline);
                   select[i].y = height * select[i].line / linetot;
                }
            }
            while( !includesTargetWord(segs, select));
         }
                // size font for CLOZE choices
        else   for(i=0;i<segs.length;++i) {
           if(segs[i].firstchoice) {
              String ss[] = buildsellist(i);
              if(ss.length>0) {
                 String s = ss[0];
                 for(j=1;j<ss.length;++j) s = s + "  " + ss[j];
                 font = sizeFont(font,s,width,height/linetot);
              }
           }
        }
        for(j=0; j<linetot; ++j) { // get font size
            String s = new String();
            if(j > segs[segs.length-1].line+1) { // choices
              if(type == CLOZE) break;
              for(i=0;i<slen;++i) {
                 if(select[i].line == j)  {
                    if(s==null) s = select[i].val;
                    else        s = s + "  " + select[i].val;
                 }
              }
            }
            else   for(i=0;i<segs.length;++i) {
                if(segs[i].line == j)  {
                   if(segs[i].firstchoice) {
                      if(s==null) s = u.longest((type==CLOZE)?buildsellist(i):wordlistv);
                      else        s = s +  u.longest((type==CLOZE)?buildsellist(i):wordlistv);
                   }
                   else if(!segs[i].choice) {
                      if(s==null) s = segs[i].val;
                      else        s = s + segs[i].val;
                   }
                }
            }
            if(s != null) {
                font = sizeFont(font,s,width,height/linetot);
            }
         }
         yinc = Math.min(metrics.getHeight()*mover.HEIGHT/sh*5/4,
                              height/linetot);
         ystart = (height-yinc*linetot)/2;
         for(j=0; j<linetot; ++j) {
            linelen = 0;
            linecount = 0;
            if(j > segs[segs.length-1].line+1) for(i=0;i<slen;++i) {
                 if(select[i].line == j)  {
                    linelen += metrics.stringWidth(select[i].val);
                    ++linecount;
                 }
            }
            else  {
              for(i=0;i<segs.length;++i) {
                if(segs[i].line == j)  {
                   if(segs[i].firstchoice) {
                      linelen += metrics.stringWidth( u.longest((type==CLOZE)?buildsellist(i):wordlistv));
                   }
                   else if(!segs[i].choice) {
                      linelen += metrics.stringWidth(segs[i].val);
                   }
                }
              }
            }
            gap = (width-linelen*mover.WIDTH/manager.screenwidth)/(linecount+1);
            linelen = gap/2;
            if(j > segs[segs.length-1].line+1) for(i=0;i<slen;++i) {
                 if(select[i].line == j)  {
                    select[i].x = linelen;
                    select[i].y = ystart+yinc*select[i].line;
                    linelen += (select[i].slotwidth=metrics.stringWidth(select[i].val)*mover.WIDTH/sw) + gap;
                    select[i].slotheight = metrics.getHeight()*mover.HEIGHT/sh;
                 }
            }
            else   for(i=0;i<segs.length;++i) {
                if(segs[i].line == j)  {
                   segs[i].x = linelen;
                   segs[i].y = ystart+yinc*segs[i].line;
                   if(segs[i].firstchoice) {
                      int ww = metrics.stringWidth(u.longest((type==CLOZE)?buildsellist(i):wordlistv))*mover.WIDTH/manager.screenwidth;
                      segs[i].slotwidth = ww;
                      linelen += ww;
                   }
                   else if(!segs[i].choice) {
                      linelen += (segs[i].slotwidth=metrics.stringWidth(segs[i].val)*mover.WIDTH/manager.screenwidth);
                   }
                }
            }
         }
         nextfind();
         exity = height*segs[segs.length-1].line/linetot;
      }
      if(type==TYPE || type==PICK || type == SIMPLECLOZE || type == CLOZE) {
         if(!donthear) readsentence();
         starttimepick = spokenWord.endsay;
      }
   }
   //--------------------------------------------------------------
   
   boolean includesTargetWord(seg main[], seg sg[]){
        for(int i=0;i<main.length;i++) {
            if(main[i].firstchoice){
                for(int j=0;j<sg.length;j++) {
                    if(main[i].val.equalsIgnoreCase(sg[j].val)){
                        return true;
                    }
                }                
            }
        }       
        return false;
   }
   
   
   public void readsentence() {
      short i;
      boolean oksay = sharkStartFrame.currPlayTopic.fl ?spokenWord.findandsaysentence3(sayval)
                                                              : spokenWord.findandsaysentence2(sayval);
      if(!oksay) {
            String s = new String();
            for(i=0;i<segs.length;++i) {
               if(segs[i].firstchoice && foundpos <= segs[i].startpos) s = s + " %% ";   // add beep
               else if(segs[i].goodword && (!segs[i].choice || segs[i].firstchoice)
                       && !segs[i].extra)
                     s = s +  " " + segs[i].wd.vsay();
            }
            spokenWord.findandsay(s);
      }
   }
   //--------------------------------------------------------------
   void buildselect(short curr) {
      short i,len,o[];
      int textlen=0;
      for(i = (short)(curr+1); i < segs.length && segs[i].choice && !segs[i].firstchoice; ++i);
      select = new seg[len=(short)(i-curr)];
      o = u.shuffle(u.select(len,len));
      for(i = 0; i < len; ++i) {
         select[o[i]] = new seg(segs[curr+i].wd.vsay(),WORD,false,linetot-1);
         select[o[i]].y = ystart + (linetot-1)*yinc;
         textlen += (select[o[i]].slotwidth = metrics.stringWidth(segs[curr+i].val)*mover.WIDTH/manager.screenwidth);
      }
      int gap = (w - textlen)/ (len+1);
      textlen = gap/2;
      for(i = 0; i < len; ++i) {
          select[i].x = textlen;
          textlen += gap+select[i].slotwidth;
      }
   }
   //--------------------------------------------------------------
   String[] buildsellist(int curr) {
      short i,len;
      for(i = (short)(curr+1); i < segs.length && segs[i].choice && !segs[i].firstchoice; ++i);
      String ret[] = new String[len=(short)(i-curr)];
      for(i = 0; i < len; ++i) ret[i] = segs[i+curr].val;
      return ret;
   }
   //--------------------------------------------------------------
   seg addseg(seg s) {
      int len = segs.length;
      seg ret[] = new seg[len+1];
      System.arraycopy(segs,0,ret,0,len);
      ret[len] = s;
      segs = ret;
      return s;
   }
   //-------------------------------------------------------------
   public String[] getWordList() {
      short i,j,tot=0;
      for(i=0;i<segs.length;++i) {
            if(segs[i].goodword) ++tot;
      }
      String words[] = new String[tot];
      tot=0;
      for(i=0;i<segs.length;++i) {
            if(segs[i].goodword) words[tot++] = stripquotes(segs[i].val);
      }
      return words;
   }
   //-------------------------------------------------------------------
    public String[] getAnswerList() {
      short i,j,tot=0;
      for(i=0;i<segs.length;++i) {
            if(segs[i].firstchoice) ++tot;
      }
      String words[] = new String[tot];
      tot=0;
      for(i=0;i<segs.length;++i) {
            if(segs[i].firstchoice) words[tot++] = stripquotes(segs[i].val);
      }
      return words;
   }
    public String[] getAnswerListWithAts() {
      short i,j,tot=0;
      for(i=0;i<segs.length;++i) {
            if(segs[i].firstchoice) ++tot;
      }
      String words[] = new String[tot];
      tot=0;
      for(i=0;i<segs.length;++i) {
            if(segs[i].firstchoice) words[tot++] = stripquotes(segs[i].wd.value);
      }
      return words;
   }
        
    public String[] getAllAnswerList() {
      short i,j,tot=0;
      for(i=0;i<segs.length;++i) {
            if(segs[i].choice) ++tot;
      }
      String words[] = new String[tot];
      tot=0;
      for(i=0;i<segs.length;++i) {
            if(segs[i].choice) words[tot++] = stripquotes(segs[i].val);
      }
      return words;
   }    
    
    public String[] getAnswerList(int index) {
      short i,j,tot=0;
      short start = 0;
      int currIndex = 0;
      int endIndex = segs.length;
      boolean lastWasTarget = false;
      for(i=0;i<segs.length;++i) {
            if(!segs[i].choice && lastWasTarget) {
                currIndex++;
                if(currIndex <= index){
                    start = i;
                }
                else {
                    endIndex=i;
                    break;
                }
            }
            lastWasTarget = segs[i].choice;
      }      
      for(i=start;i<endIndex;++i) {
            if(segs[i].choice) ++tot;
      }
      String words[] = new String[tot];
      tot=0;
      for(i=start;i<endIndex;++i) {
            if(segs[i].choice) words[tot++] = stripquotes(segs[i].val);
      }
      return words;
   }    
    
   //-------------------------------------------------------------
   String stripquotes(String s) {
      int len = s.length();
      if(len>0 && s.charAt(0) =='\'') s = s.substring(1);
      len = s.length();
      if(len>1 && s.charAt(len-1) =='\'') s = s.substring(0,len-1);
      return s;
   }
   //--------------------------------------------------------------------
   void nexttypingpos() {
         short i;
          ++typingpos;
         for(i=0;i<segs.length;++i) {
            if(segs[i].typein) {
               if(segs[i].startpos >= typingpos) {
                  typingpos = (short)segs[i].startpos;
                  return;
               }
               else   if(segs[i].startpos+segs[i].len > typingpos) return;
               else if(!segs[i].finished) {
                  segs[i].finished  = true;
                  if(segs[i].goodword) {
                     manager.currgame.gamescore(1);
                     doneword = segs[i].crosswordno;
                  }
               }
            }
         }
         finished = true;
         manager.currgame.keypadDeactivate();
         manager.moverwantskey = null;
    }
   //--------------------------------------------------------------------
   void nextfind() {
      if(type == HIDDEN) {
         for(++foundpos;foundpos<hidden.length;++foundpos) {
           if(hidden[foundpos].goodword) return;
         }
      }
      else if(type == PICK) {
         for(++foundpos;foundpos<segs.length;++foundpos) {
           if(segs[foundpos].goodword && !segs[foundpos].extra) return;
           else segs[foundpos].dontshow = false;
         }
      }
      else {
         for(++foundpos;foundpos<segs.length;++foundpos) {
            if(segs[foundpos].firstchoice) {
               if(type==CLOZE) buildselect(foundpos);
               return;
            }
         }
      }
      finished = true;
   }
   //---------------size font-----------------------
   Font sizeFont(Font f1, String s, int w1, int h1) {
        f1 = sharkGame.adjustFont(f1,s,w1*manager.screenwidth/mover.WIDTH,
                                      h1*manager.screenheight/mover.HEIGHT);
        if(f1.getSize() > sharkStartFrame.MAXFONTPOINTS_2)
//startPR2007-11-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//          f1 = new Font(f1.getName(),f1.getStyle(),40);
          f1 = f1.deriveFont((float)sharkStartFrame.MAXFONTPOINTS_2);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        metrics = manager.getFontMetrics(f1);
        return f1;
   }
    //---------------------------------------------------------------------
   seg[] splitup(String s) {
      int i,curr=0,j,k,line=0;
      int tsegmax = 100;
      int segtot = 0;
      char choice=0,ch;
      short len = (short)s.length();
      if(len>2 && s.charAt(len-1) == ']'
           && sentence.separators.indexOf(ch = s.charAt(len-2)) < 0)
          s = s.substring(0,len-1) + ".]";
      else if(sentence.separators.indexOf(ch = s.charAt(len-1)) < 0)
           s=s+".";
      else if(ch == '}') {
         if ((i = s.indexOf('{')) > 0
               && sentence.separators.indexOf(s.charAt(i-1)) < 0)  {
             s = s + ".";
         }
      }
      boolean typing=false, hadslash=false, startingslash=false, pickextra = false;
      int chunkdiv[] = null;
      while(curr < len) {
         ch = s.charAt(curr);
         if(ch == '�') {
           chunkdiv = u.addint(chunkdiv, segs.length);
           addseg(new seg(" ",PUNC,typing,line));
           ++curr;
           continue;
         }
         if(ch == '/' && (curr == 0 || sentence.separators.indexOf(s.charAt(curr-1)) >= 0)) {
             startingslash = true;
             ++curr;
             continue;
         }
         if(sentence.separators.indexOf(ch) < 0) {
            i = curr;
            if(startingslash) {
               i = s.indexOf('/',i);
               if(i<0) i = s.length();
            }
            else while(++i < len
                 && separators.indexOf(s.charAt(i))<0);
            seg ss = addseg(new seg(s.substring(curr,i),WORD,typing,line, curr, i));
            if(curr == 0) ss.startingseg = true;
            if(hadslash) ss.choice = true;
            hadslash = false;
            if(pickextra) ss.extra = true;
            curr = i;
            if(!ss.choice && curr<len && s.charAt(curr) == '/') {
               ss.firstchoice = ss.choice = true;
                  // '/' at and of word
               if(curr>=len-1
                    || sentence.separators.indexOf(s.charAt(curr+1)) >= 0) {
                  ss.listchoice = true;  // use current list
                  startingslash = false;
                  ++curr;
               }

            }
            else if(startingslash && curr<len && s.charAt(curr) == '/' ) {
               if(curr>=len-1
                    || sentence.separators.indexOf(s.charAt(curr+1)) >= 0) {
                  startingslash = false;
                  ++curr;
               }
            }
            continue;
         }
         else if(ch == '|') {
            ++curr;
            ++line;
         }
         else if(ch == '/') {
            ++curr;
            hadslash = true;
      }
         else if(ch == '[') {
            ++curr;
            typing = true;
         }
         else if(ch == '{') {
            ++curr;
            pickextra = true;
         }
         else if(ch == ']') {
            ++curr;
            typing = false;
         }
         else if(ch == '}') {
            ++curr;
            pickextra = false;
         }
         else if(ch == '~') {   // doesnt matter where it is
            ++curr;
         }
         else if(!pickextra) {
            addseg(new seg(s.substring(curr,curr+1),PUNC,typing,line,curr,curr+1));
            ++curr;
         }
         else ++curr;
      }
      if(chunkdiv != null) {
         chunks = new String[chunkdiv.length+1];
         chunks[0] = "";
         for(i=j=0;j<segs.length;++j) {
            if(i<chunkdiv.length && j >= chunkdiv[i]) chunks[++i] = "";
            else if((!segs[j].choice || segs[j].firstchoice)
                    && !(j == segs.length-1 && segs[j].punc)) chunks[i] = chunks[i]+segs[j].val;
         }
      }
      return segs;
   }
   //------------------------------------------------------------
   public void paint2(Graphics g, int xx, int yy, int ww, int hh) {
      int i;
      if(oldscreenwidth != manager.screenwidth) {
//startPR2007-11-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//        font = new Font(font.getName(),font.getStyle(),font.getSize()*manager.screenwidth/oldscreenwidth);
        font = font.deriveFont((float)font.getSize()*manager.screenwidth/oldscreenwidth);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        metrics = manager.getFontMetrics(font);
        oldscreenwidth = manager.screenwidth;
      }
      g.setFont(font);
      for(i=0;i<segs.length;++i) {
         if(!segs[i].choice) g.drawString(segs[i].val,
                                          xx + segs[i].x *manager.screenwidth/mover.WIDTH,
                                          yy + (segs[i].y + yinc/2)*manager.screenheight/mover.HEIGHT
                                             - metrics.getHeight()/2 + metrics.getAscent() );
      }
   }
   //------------------------------------------------------------
//startPR2010-02-24^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//   public void paint(Graphics g, int xx, int yy, int ww, int hh) {
   public void paint(Graphics g, int xx, int yy, int ww, int hh) {
       paint(g, xx, yy, ww, hh, false);
   }

   public void paint(Graphics g, int xx, int yy, int ww, int hh, boolean nodraw) {
//endPRtoupdate^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      short i,pickup = -1;
      if(oldscreenwidth != manager.screenwidth) {
//startPR2007-11-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//        font = new Font(font.getName(),font.getStyle(),font.getSize()*manager.screenwidth/oldscreenwidth);
        font = font.deriveFont((float)font.getSize()*manager.screenwidth/oldscreenwidth);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        metrics = manager.getFontMetrics(font);
        oldscreenwidth = manager.screenwidth;
      }
//startPR2009-11-13^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      if(jumbled != null) {
      if(jumbled != null && !manager.dragging) {
//endPRwin7^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         int dist = 0x7fffffff,dist1,dx,dy;
         for(i=0;i<jumbled.length;++i) {
            dx = manager.mousexs - (xx+(jumbled[i].x + jumbled[i].slotwidth/2)*manager.screenwidth/mover.WIDTH);
            dy = manager.mouseys - (yy+(jumbled[i].y + jumbled[i].slotheight/2)*manager.screenheight/mover.HEIGHT);
            dist1 = dx*dx+dy*dy;
            if(dist1 < dist) {dist=dist1;markedseg = jumbled[i];}
            if(jumbled[i].pickedup) pickup = i;
         }
         if(pickup >= 0) {
            markedseg = jumbled[pickup];
         }
         else if(manager.mousex > w) markedseg = null;
         if(finished) markedseg=null;
//startPR2010-02-24^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         if(!nodraw)
//endPRtoupdate^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         for(i=0;i<jumbled.length;++i) jumbled[i].paint(g,xx,yy,ww,hh);
         return;
      }
      if(hidden != null) {
         int mouserow = (manager.mousey - hidden[0].y) / hiddenyinc;
         int prevx = 0;
         for(i=0;i<hidden.length;++i) {
            if(mouserow == hidden[i].y/hiddenyinc) {
               markedseg = hidden[i];
               if(i<hidden.length-1 && hidden[i].y==hidden[i+1].y)
                    prevx = (hidden[i].x+hidden[i].slotwidth + hidden[i+1].x)/2;
               else break;
               if(manager.mousex <= prevx) break;
            }
          }
//startPR2010-02-24^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         if(!nodraw)
//endPRtoupdate^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          for(i=0;i<hidden.length;++i) hidden[i].paint(g,xx,yy,ww,hh);
      }
      if(select != null && !finished) {
         if(manager.mousex < w) {
            int dist = 0x7fffffff,dist1,dx,dy;
            for(i=0;i<select.length;++i) {
               dx = manager.mousexs - (xx+(select[i].x + select[i].slotwidth/2)*manager.screenwidth/mover.WIDTH);
               dy = manager.mouseys - (yy+(select[i].y + select[i].slotheight/2)*manager.screenheight/mover.HEIGHT);
               dist1 = dx*dx+dy*dy;
               if(dist1 < dist) {dist=dist1;markedseg = select[i];}
            }
         }
         else markedseg = null;
//startPR2010-02-24^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         if(!nodraw)
//endPRtoupdate^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         for(i=0;i<select.length;++i) select[i].paint(g,xx,yy,ww,hh);
      }
      if(type == PICK && !finished) {
//startPR2010-02-24^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         if(!nodraw){
//endPRtoupdate^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          if(endpeep > sharkGame.gtime()) {
           String s = new String();
           for(i=0;i<segs.length;++i) {
              if(!segs[i].extra) s = s + segs[i].val;
           }
           g.setFont(font);
           g.setColor(Color.green);
           g.drawString(s,xx,yy+hh/2);
           return;
         }
//startPR2010-02-24^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         }
//endPRtoupdate^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         int dist = 0x7fffffff,dist1,dx,dy;
          for(i=0;i<segs.length;++i) {
              if(segs[i].y < finishedy) {
                 dx = manager.mousexs - (xx+(segs[i].x + segs[i].slotwidth/2)*manager.screenwidth/mover.WIDTH);
                 dy = manager.mouseys - (yy+(segs[i].y + segs[i].slotheight/2)*manager.screenheight/mover.HEIGHT);
                 dist1 = dx*dx+dy*dy;
                 if(dist1 < dist) {dist=dist1;markedseg = segs[i];}
              }
          }
      }
//startPR2010-02-24^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      if(!dontsee) for(i=0;i<segs.length;++i) segs[i].paint(g,xx,yy,ww,hh);
      if(!nodraw && !dontsee) for(i=0;i<segs.length;++i) segs[i].paint(g,xx,yy,ww,hh);
//endPRtoupdate^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   }
//   //-------------------------------------------------------------------
//   public void mouseUp(int x1, int y1) {
//     if (type != JUMBLED ||  markedseg == null || finished ||  !markedseg.pickedup) return;
//     int xx = x1*mover.WIDTH/manager.screenwidth;
//     int yy = y1*mover.HEIGHT/manager.screenheight;
//      if((downx - xx)*(downx-xx)+(downy-yy)*(downy-yy) > w*h
//          || sharkGame.gtime() - downtime > 1000)
//        mouseClicked(xx,yy);
//   }
//startPR2010-02-24^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^Pr
    public void mouseUp(int xm, int ym) {
        if (type == JUMBLED) {
            if (manager.dragging &&  downtime+100<sharkGame.gtime() && markedseg != null) {
                markedseg.pickedup = false;
                manager.showSprite = true;
            }
            if (markedseg!=null && !markedseg.pickedup) {
                if (inorder() && !finished) {
                    reorder(true);
                    finished = true;
                    for (int i = 0; i < jumbled.length; ++i) {
                        manager.currgame.gamescore(1);
                        doneword = jumbled[i].crosswordno;
                        redrawc();
                    }
                    readsentence();
                }
            }
        }
    }
//endPRtoupdate^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   //------------------------------------------------------------
   public void mouseClicked(int xm,int ym) {
//startPR2009-11-13^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//     Graphics g = manager.getGraphics();
//     paint(g, x, y, h, w);
//endPRwin7^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2010-02-24^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     Graphics g = manager.getGraphics();
     int x2 = x * manager.screenwidth / mover.WIDTH;
     int y2 = y * manager.screenheight / mover.HEIGHT;
     int w2 = w * manager.screenwidth / mover.WIDTH;
     int h2 = h * manager.screenheight / mover.HEIGHT;
     paint(g, x2, y2, h2, w2, true);
//endPRtoupdate^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       short i;
      if(type==CLOZE || type == SIMPLECLOZE) {
         if(markedseg != null && !finished && !cancel) {
            spokenWord.flushspeaker(true);
            if(markedseg.val.equalsIgnoreCase(segs[foundpos].val)) {
               manager.currgame.gamescore(1);
               segs[foundpos].finished = true;
               doneword = segs[foundpos].crosswordno;
               markedseg = null;
               nextfind();
               redrawc();
            }
            else {
                markedseg.wd.say();
 //               cancel = true;
                new spokenWord.whenfree(100) {
                  public void action() {
                    noise.groan();
                    manager.currgame.error(segs[foundpos].val);
                    new spokenWord.whenfree(100) {
                      public void action() {
                        boolean savedonthear =donthear;
                        if(!dontsee) donthear = true;
                        setup(w, h, savewordlist);
                        donthear = savedonthear;
                        manager.clearall();
                      }
                    };
                  }
                };
            }
         }
      }
      if(type==PICK) {
         if(markedseg != null && !finished) {
            if(markedseg.val.equals(segs[foundpos].val)) {
               markedseg.y = finishedy;
               markedseg.x = 0;
               for(i=0;i<segs.length;++i) {
                  if(segs[i].finished || segs[i].punc && segs[i].startpos<foundpos) {
                      markedseg.x += metrics.stringWidth(segs[i].val)*mover.WIDTH/manager.screenwidth;
                  }
               }
               manager.currgame.gamescore(1);
               markedseg.finished = true;
               doneword = segs[foundpos].crosswordno;
               markedseg = null;
               nextfind();
               redrawc();
            }
            else {
                markedseg.wd.say();
//                spokenWord.findandsay(markedseg.val);
                noise.groan();
                manager.currgame.error();
            }
         }
      }
      else if(type==HIDDEN) {
         if(markedseg != null && !finished) {
            if(markedseg == hidden[foundpos]) {
               manager.currgame.gamescore(1);
               hidden[foundpos].finished = true;
               doneword = hidden[foundpos].crosswordno;
               markedseg = null;
               nextfind();
               redrawc();
               for(i=0;i<segs.length;++i) {
                  if(segs[i].goodword && segs[i].dontshow) {
                     segs[i].dontshow = false;
                     if(!finished) break;
                  }
                  segs[i].dontshow = false;
               }
            }
            else if(markedseg.goodword) {
               noise.beep();
            }
            else {
                noise.groan();
                manager.currgame.error();
            }
         }
      }
      else if(type==JUMBLED) {
        if(markedseg == null || markedseg != null && !markedseg.pickedup) {
          for (i = 0; i < jumbled.length; ++i) {
            if (manager.mousex >= x + jumbled[i].x
                && manager.mousex <= x + jumbled[i].x + jumbled[i].slotwidth
                && manager.mousey >= y + jumbled[i].y
                && manager.mousey <= y + jumbled[i].y + jumbled[i].slotheight) {
              markedseg = jumbled[i];
              break;
            }
          }
        }
//startPR2009-11-13^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//        if(markedseg != null && !finished ) {
        if(markedseg != null && !finished && !manager.dragging) {
//endPRwin7^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            if(markedseg.pickedup) {
               markedseg.pickedup = false;
               manager.showSprite = true;
               lastclickx = manager.mousex;
               lastclicky = manager.mousey;
               reorder(false);
//startPR2010-02-24^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//               if(inorder()) {
//                  reorder(true);
//                  finished = true;
//                  for(i=0;i<jumbled.length;++i) {
//                     manager.currgame.gamescore(1);
//                     doneword = jumbled[i].crosswordno;
//                     redrawc();
//                  }
//                  readsentence();
//               }
//endPRtoupdate^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            }
            else {
               if(markedseg.wd != null) markedseg.wd.say();
               manager.showSprite = false;
               markedseg.pickedup = true;
               mx = (markedseg.x+x)*manager.screenwidth/mover.WIDTH - manager.mousexs;
               my = (markedseg.y+y)*manager.screenheight/mover.HEIGHT - manager.mouseys;
               downx = manager.mousex;
               downy = manager.mousey;
               downtime=sharkGame.gtime();
            }
         }
      }
   }
   public void redrawc() {
      if(manager != null &&  manager.currgame != null)
          manager.currgame.sparefunc();
   };
   //----------------------------------------------------------------
   boolean inorder() {
      int i,j,ii[] = new int[jumbled.length];
      int y = jumbled[0].y;
      int dy = metrics.getHeight()*mover.HEIGHT/manager.screenheight*2/3;
      for(i=0;i<jumbled.length;++i) {
         if(Math.abs(jumbled[i].y - y) > dy ) return false;
         y = jumbled[i].y;
         ii[i] = jumbled[i].x;
      }
      short o[] = u.getorder(ii);
      for(i=j=0;i<segs.length;++i) {
         if(!segs[i].val.equals(" ")
            &&  !segs[i].val.equals(jumbled[o[j++]].val)) return false;
      }
      return true;
   }
   //----------------------------------------------------------------
   public  void jumbledread() {    // read left to right
      int i,j,ii[] = new int[jumbled.length];
      for(i=0;i<jumbled.length;++i) {
         ii[i] = jumbled[i].x;
      }
      short o[] = u.getorder(ii);
      for(i=j=0;i<o.length;++i) {
            j = o[i];
            if(jumbled[j].wd != null) {
               jumbled[j].saying=true;
               ended=false;
               jumbled[j].wd.say();
               spokenWord.waitforsay();
               jumbled[j].saying=false;
               ended=false;
         }
       }
   }
   //----------------------------------------------------------------
   void reorder(boolean ending) {
      int ox[] = new int[jumbled.length];
      int i,sel=0,len,xx;
      int y = jumbled[0].y;
      int dy = metrics.getHeight()*mover.HEIGHT/manager.screenheight*2/3;
      for(i=0;i<jumbled.length;++i) {
         ox[i] = jumbled[i].x + metrics.stringWidth(jumbled[i].val)*mover.WIDTH/manager.screenwidth/2;
      }
      short o[] = u.getorder(ox);
      for(i=0;i<jumbled.length;++i) {
         if(jumbled[o[i]] == markedseg) {sel = i; break;}
      }
      for(i = sel+1;i < o.length;++i) {
         if(Math.abs(jumbled[o[i]].y-jumbled[o[i-1]].y) > dy) break;
         xx =  jumbled[o[i-1]].x
              + metrics.stringWidth(jumbled[o[i-1]].val+" ")*mover.WIDTH/manager.screenwidth;
         if (jumbled[o[i]].x < xx || ending) {
              jumbled[o[i]].x = xx;
              jumbled[o[i]].y = jumbled[o[i-1]].y;
              if(jumbled[o[i]].x  + (len=metrics.stringWidth(jumbled[o[i]].val+" ")*mover.WIDTH/manager.screenwidth) > w) {
                   sel = i;
                   jumbled[o[i]].x = w - len;
              }
         }
         else break;
      }
      for(i = sel-1;i >= 0;--i) {
         if(Math.abs(jumbled[o[i]].y-jumbled[o[i+1]].y) > dy) break;
         xx =  jumbled[o[i+1]].x
              - metrics.stringWidth(jumbled[o[i]].val+" ")*mover.WIDTH/manager.screenwidth;
         if (jumbled[o[i]].x > xx || ending) {
              jumbled[o[i]].x = xx;
              jumbled[o[i]].y = jumbled[o[i+1]].y;
              if(jumbled[o[i]].x < 0)  {
                 sel = i;
                 jumbled[o[i]].x = 0;
              }
         }
         else break;
      }
      for(i = sel+1;i < o.length;++i) {
         if(Math.abs(jumbled[o[i]].y-jumbled[o[i-1]].y) > dy) break;
         xx =  jumbled[o[i-1]].x
              + metrics.stringWidth(jumbled[o[i-1]].val+" ")*mover.WIDTH/manager.screenwidth;
         if (jumbled[o[i]].x < xx || ending) {
           if(ending && i==o.length-1 && jumbled[i].punc) xx-=metrics.charWidth(' ')*mover.WIDTH/manager.screenwidth;
              jumbled[o[i]].x = xx;
         }
         else break;
      }
   }
   //----------------------------------------------------------------
   public void keyhit(KeyEvent e) {
      short i;
      if(type==TYPE) {
         char key = (char)e.getKeyChar();
         if(key == KeyEvent.CHAR_UNDEFINED) return;

         for(i=0;i<segs.length;++i) {
            if(typingpos >= segs[i].startpos && typingpos < segs[i].startpos+segs[i].len) {
               if(key==segs[i].val.charAt(typingpos - segs[i].startpos)) {
                  errorchars = 0;
                  errorchval = 0;
                  lasttyped = sharkGame.gtime();
                  nexttypingpos();
                  hadkeyhit = true;
                  redrawc();
                  hadkeyhit=false;
               }
               else if(errorchval != key
                     || sharkGame.gtime() > lasttyped+1500) {
                  noise.groan();
                  manager.currgame.error();
                  ++errorchars;
                  errorchval = key;
                  lasttyped = sharkGame.gtime();
               }
               break;
            }
         }
      }
   }
   //------------------------------------------------------------
   public class seg {
      word wd;
      public String val;
      short len,crosswordno=-1;
      public boolean goodword,punc,badword,choice,firstchoice,typein,listchoice,extra;            //WORD, BADWORD, PUNC
      boolean typing,pickedup,saying,dontshow,finished,tracking;
      boolean startingseg;
      int x,y,i,startpos,slotwidth,slotheight;
      public int line;
      public int pos1 = -1;
      public int pos2 = -1;
      
      seg(String s, byte ty, boolean typein1,int line1) {
        String sq;
        typein = typein1;
        line = line1;
        if(ty==PUNC) punc=true;
        else if(ty==WORD) goodword=true;
        else if(ty==BADWORD) badword=true;
        else if(ty==LISTCHOICE) listchoice=true;
        if(ty == WORD && (i = s.indexOf('@')) >= 0)
             val = s.substring(0,i);
        else val = s;
        val = spellchange.spellchange(val);
        startpos = segpos;
        len = (short)val.length();
        if(type == CLOZE || type == SIMPLECLOZE || type == PICK) ++segpos;
        else segpos += len;
        if(ty == WORD || ty==LISTCHOICE) {
            wd = new word(sq=stripquotes(s),database);
        }                
                
      }

      seg(String s, byte ty, boolean typein1,int line1, int p1, int p2) {
        String sq;
        typein = typein1;
        line = line1;
        pos1 = p1;
        pos2 = p2;
        if(ty==PUNC) punc=true;
        else if(ty==WORD) goodword=true;
        else if(ty==BADWORD) badword=true;
        else if(ty==LISTCHOICE) listchoice=true;
        if(ty == WORD && (i = s.indexOf('@')) >= 0)
             val = s.substring(0,i);
        else val = s;
        val = spellchange.spellchange(val);
        startpos = segpos;
        len = (short)val.length();
        if(type == CLOZE || type == SIMPLECLOZE || type == PICK) ++segpos;
        else segpos += len;
        if(ty == WORD || ty==LISTCHOICE) {
            wd = new word(sq=stripquotes(s),database);
        }
      }
      seg(seg ss) {    // copy (only of goodword) - used by 'hidden'
        typein = ss.typein;
        line = ss.line;
        goodword=true;
        val = stripquotes(ss.val);
        wd = ss.wd;
        crosswordno = ss.crosswordno;
      }
      
      public void paint(Graphics g, int xx, int yy, int ww, int hh) {
        int x1 = xx + x*manager.screenwidth/mover.WIDTH;
        int y1 = yy + y*manager.screenheight/mover.HEIGHT;
        String s;
        int i,width,height = metrics.getHeight(),dy = metrics.getAscent();
        if(dontshow) return;
        g.setColor(Color.black);
        g.setFont(font);
        if(type == TYPE) {
           if(endpeep > sharkGame.gtime()) {
              g.setColor(Color.green);
              g.drawString(val,x1,y1+dy-metrics.getHeight());
              g.setColor(Color.black);
           }
           else if(manager.currgame!=null && !manager.currgame.completed && typingpos >= startpos && typingpos < startpos+len) {
              char ch = val.charAt(typingpos-startpos);
              if(errorchars > 2
                 || (ch=='.' || ch == '?') && sharkGame.gtime()-lasttyped > 2000) {
                 g.setColor(Color.green);
                 if(ch == ' ') {
                    String mess = manager.currgame.rgame.getParm("typespace");
                    g.drawString(mess,
                       Math.max(0,Math.min(xx+ww-metrics.stringWidth(mess),x1+metrics.stringWidth(val.substring(0,typingpos-startpos)))),
                       y1+dy-metrics.getHeight());
                 }
                 else if(ch == '.') {
                    String mess = manager.currgame.rgame.getParm("typefullstop");
                    g.drawString(mess,
                       Math.max(0,x1+metrics.stringWidth(val.substring(0,typingpos-startpos+1))-metrics.stringWidth(mess)),
                       y1+dy-metrics.getHeight());
                 }
                 else if(ch == '?') {
                    String mess = manager.currgame.rgame.getParm("typequestion");
                    g.drawString(mess,
                       Math.max(0,x1+metrics.stringWidth(val.substring(0,typingpos-startpos+1))-metrics.stringWidth(mess)),
                       y1+dy-metrics.getHeight());
                 }
                 else g.drawString(val.substring(typingpos-startpos,typingpos-startpos+1),
                       Math.max(0,x1+metrics.stringWidth(val.substring(0,typingpos-startpos))),
                       y1+dy-metrics.getHeight());
                 if(u.uppercase.indexOf(ch) >= 0) {
                    g.drawString(manager.currgame.rgame.getParm("capital"),
                       x1+metrics.stringWidth(val.substring(0,typingpos-startpos+1)),
                       y1+dy-metrics.getHeight());
                 }
                 if(u.uppercase.indexOf(ch)< 0 && u.uppercase.indexOf(errorchval) >= 0) {
                    g.drawString(manager.currgame.rgame.getParm("notcapital"),
                       x1+metrics.stringWidth(val.substring(0,typingpos-startpos+1)),
                       y1+dy-metrics.getHeight());
                 }

              }
              g.setColor(Color.black);
           }
           if(typein) {
              if(typingpos >= startpos + len) {
                 g.setColor(yellow());
              }
              else {
                 if(typingpos > startpos) {
                    g.drawString(s = val.substring(0,typingpos-startpos),x1,y1+dy);
                    x1 += metrics.stringWidth(s);
                 }
                 g.setColor(yellow());
                 for(i=Math.max(0,typingpos-startpos); i < len;++i) {
                       width = metrics.charWidth(val.charAt(i));
                       if(startpos+i == typingpos)
                            g.drawRect(x1+1,y1,width-1,height);
                       else if(goodword) g.fillRect(x1+1,y1,width-1,height);
                       x1 += width;
                 }
                 return;
              }
           }
        }
        else if(tracking) {
           if (foundpos > startpos && goodword)  g.setColor(yellow());
           else if(markedseg == this && manager.mousex < w)  g.setColor(white());
        }
        else if(type == JUMBLED) {
           if(saying) {
              g.setColor(yellow());
              g.drawRect(xx,yy,ww,hh);
           }
           if((manager.mousex  != lastclickx || manager.mousey != lastclicky)
               && markedseg == this && !pickedup) {
             g.setColor(white());
             g.drawRect(x1-metrics.charWidth(' ')/2,y1,metrics.stringWidth(val+" "),metrics.getHeight());
             g.setColor(Color.black);
           }
           if(pickedup)  g.setColor(white());
           if(pickedup) {
            x1 = manager.mousexs+mx;
            x1 = Math.max(xx,Math.min(x1,ww - metrics.stringWidth(markedseg.val)));
            x = (x1-xx)*mover.WIDTH/manager.screenwidth;
            y1 = manager.mouseys+my;
            y1 = Math.max(yy,Math.min(y1,hh - metrics.getHeight()));
            y = (y1-yy)*mover.HEIGHT/manager.screenheight;
          }
        }
        else if(type == CLOZE || type == SIMPLECLOZE) {
           if(firstchoice) {
              if (foundpos == startpos) {
                 if(markedseg != null)  {
                    g.setColor(yellow());
                    g.drawString(cap(markedseg.val),x1,y1+dy);
                    return;
                 }
                 else {
                    g.setColor(yellow());
                    g.fillRect(x1,y1, slotwidth*manager.screenwidth/mover.WIDTH, height);
                 }
              }
              else if(foundpos<startpos) {
                 g.setColor(yellow());
                 g.fillRect(x1,y1+dy, slotwidth*manager.screenwidth/mover.WIDTH, 2);
              }
              else {
                    g.setColor(foundpos>=segs.length?yellow():yellow().darker());
                    g.drawString(val,x1,y1+dy);
              }
              return;
           }
           else if(choice) return;
           if(markedseg == this)  g.setColor(yellow());
        }
        else if(type == PICK) {
           if(sharkGame.gtime() < starttimepick) return;
           if(markedseg == this)  g.setColor(yellow());
        }
        g.drawString(val,x1,y1+dy);
        if(saying) {
              g.setColor(white());
              g.drawRect(x1,y1,metrics.stringWidth(val),height);
        }
      }
      String cap(String s) {
         if(startingseg && u.uppercase.indexOf(val.charAt(0)) >= 0)
            return s.substring(0,1).toUpperCase()+s.substring(1);
         else return s;
      }
   }
   Color yellow() {
    return u.tooclose2(manager.getBackground(),Color.yellow)?Color.orange:Color.yellow;
   }
   Color white() {
    return u.tooclose(manager.getBackground(),Color.white)?Color.magenta:Color.white;
   }
   //---------------------------------------------------------------
   public static boolean isSentence(String s) {
      for(short i=0;i<s.length();++i) {
         if(makesentence.indexOf(s.charAt(i)) >= 0) return true;
      }
      return false;
   }
   //---------------------------------------------------------------
   public static boolean isSimpleSentence(String s) {
      int i=0;
      while((i=s.indexOf('/',i)) >= 0) {
        if (i == s.length() - 1
            || sentence.separators.indexOf(s.charAt(i + 1)) >= 0)
                   return true;
        else ++i;
      }
      return false;
   }
   //-------------------------------------------------------------------
   public static sentence findsentencefor(String s) {
     sentence sen[];
     int i;
     if(sharkStartFrame.currPlayTopic != null ) {
       sen = sharkStartFrame.mainFrame.wordTree.getsentences(sharkStartFrame.currPlayTopic.getSpecials(topic.sentencegames2));
       for(i = 0; i<sen.length;++i) {
         if(sen[i].type == sentence.SIMPLECLOZE
                && u.findString(sen[i].getAnswerList(),s) >= 0) {
             return sen[i];
         }
       }
     }
     return null;
   }
   //------------------------------------------------------------------
   public String stripcloze() {
     String ss="";
     int i,j;
     for(i = 0; i<segs.length;++i) {
       if(!segs[i].choice || segs[i].firstchoice)     ss = ss + segs[i].val;
     }
     return ss;
   }
   
   public String stripclozereplacewildcard() {
     String ss="";
     int i,j;
     String wildcard = "*";
     int lastline = 0;
     for(i = 0; i<segs.length;++i) {
       if(lastline<segs[i].line){
           ss = ss + "|";
            lastline = segs[i].line;
       }
       if(!segs[i].choice){
           ss = ss + segs[i].val;
       }
       else if(segs[i].firstchoice) {
           ss = ss + wildcard;
       }
     }
     return ss;
   }  
}
