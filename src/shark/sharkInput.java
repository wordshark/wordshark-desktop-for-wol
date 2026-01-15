package shark;
import java.awt.*;
import java.awt.event.*;

// a member of this class should be attached to a mover

public class sharkInput  {
   String buttons[];
   public char echoChar;            // overriding display char (passwords only)
   public String val = new String(""),label,spacefor;
   public String model;
   public String splits;   // model with slashes to divide
   byte currpos=-1,currph=0;
   public Font f, flabel,fb, fixfont;
   public FontMetrics m;
   public short maxlen;
   int oldw,bw,bh;
   public sharkAction action;
   public long showmodel=-1;
   public boolean insert=false,showchar,dontcompare,leftjustify;
   public boolean redraw,newlabel,renew,ended,ending,cantgoback;
   int bx[],by;
   int showx,showy;
   String showtext;
   short errortot;
   char badchar;
   public long startafter;
   public int slashat=-1;
   word currword, allwords[];
   int lettergap;

   public String letterlist[];
   letter letters[];
   runMovers rm;
   int valx,valy,vgap, tilewidth;
   public int allowy, toplet;
   sharkInput thisi = this;
   long wantend;
   sharkImage tick;
   boolean caps;
   String phonemes[];
   public boolean singlesound;
   int putbacky1,putbacky2;
   public int lettervalx,lettervaly;

   public Color background =  Color.black;
   public Color labelcolor = Color.yellow;
   public Color textcolor = Color.white;
   public Color buttoncolor = Color.black, buttonbkcolor = Color.cyan;
   public Color modelcolor = Color.yellow;
   public Color cursorcolor = Color.yellow;
//startPR2004-09-15^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    boolean isSpecialVowel[];
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    public int wantenddelay = 1500;
    public int overrideTextHeight = -1;
         Color  col_split_lighter = new Color(165,165,165);
         Color col_split_darker = new Color(130,130,130);


   public sharkInput(sharkAction a) {     // no label
      action = a;
   }
   public void fixyellow(sharkGame gg) {
     labelcolor=modelcolor=cursorcolor=gg.yellow();
     textcolor=gg.white();
   }
   public void paint(Graphics g, int x, int y, int w, int h,int screenheight) {
       Color savecolor = g.getColor();
       Font savefont = g.getFont();
       int x1,y1,yextra;
       String val2 = val;

       int totlines = (1 +((label!=null)?1:0) +  ((model != null)?1:0));
       int yinc = h/totlines;
       short i;
       int vx;
       if(shark.macOS) ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

       if(letterlist != null && val.length()  == 0) {
       }
       if(currpos < 0) {
          currpos = 0;
          if(model != null && model.length() > val.length() && val.length()>0) {
             if( val.charAt(0) == model.charAt(0))
                                       currpos=(byte)val.length();
             else  {
               currpos = 0;
               insert = true;
             }
          }
       }
       if (fixfont != null) f= flabel =fixfont;
       else if(f == null || w != oldw || renew) {
         renew = false;
         String s[] = null;
         if(label != null) s = u.addString(s,label);
         if(splits != null) s = u.addString(s,splits);
         else if(model != null) s = u.addString(s,model);
         else if(spacefor != null) s = u.addString(s,spacefor);
         else s = u.addString(s,val+"  ");
         if(buttons != null) {
            String s1 = new String("");
            int inc = w / buttons.length;
            bw = inc*4/5;
            by = y+h;
            bh = Math.min(screenheight-by-4,yinc);
            bx = new int[buttons.length];
            x1 = x+(w - bw * buttons.length) / (buttons.length+1);
            for(i=0;i<buttons.length;++i) {
               s1 = s1 + buttons[i] + "    ";
               bx[i] =  x1;
               x1 +=  inc;
            }
            fb = sharkGame.sizeFont(g,buttons,bw,yinc);
         }
         flabel = f = sharkGame.sizeFont(g,s,w,h/totlines);
         oldw=w;
      }
      if(newlabel) {
         newlabel = false;
         flabel = sharkGame.sizeFont(f,g,label,w,h/totlines);
      }
      y1 = valy = y;
//      g.setColor(background);
//      g.fillRect(x,y,w,h);
     if(label != null) {
         g.setFont(flabel);
         m = g.getFontMetrics();
         yextra = (m.getHeight() + m.getAscent() - m.getDescent())/2;
         g.setColor(labelcolor);
         g.drawString(label,x+(w-m.stringWidth(label))/2,y1+yextra);
         y1 += yinc;
      }
      g.setFont(f);
      m = g.getFontMetrics();
      int textheight;
      if(overrideTextHeight<0)textheight = m.getHeight();
      else textheight = overrideTextHeight;
      yextra = (textheight + m.getAscent() - m.getDescent())/2;
      int defwidth =  m.charWidth((echoChar == 0)?'n':echoChar);
      if(leftjustify) vx = x;
      else {
        if (splits != null)
          vx = x + (w - m.stringWidth(splits)) / 2;
        if (model != null)
          vx = x + (w - m.stringWidth(model)) / 2;
        else if (val.length() > 0)
          vx = x + (w - m.stringWidth(val)) / 2;
        else
          vx = x + w / 2 - defwidth;
      }
      if(vgap> 0 ) vx -= vgap*rm.screenwidth/mover.WIDTH;
      valx = vx;
      if(showmodel != -1) {
         if(showmodel < sharkGame.gtime()) {
            showmodel = -1;
         }
         else {
            g.setColor(modelcolor);
            if(splits != null) {
               String s[] = u.simplesplit(null,splits,false), ss;
               int sx = vx;
               for(i=0;i<s.length;++i) {
                  g.drawString(s[i],sx,y1+yextra);
                  sx += m.stringWidth(s[i]+"/");
               }
            }
            else g.drawString(model,vx,y+h/2-textheight+yextra);
         }
         redraw = true;
      }
      if(showchar) {
        g.setColor(modelcolor);
        if (model.length() <= currpos) {
            String strpeep = u.gettext("u_",letterlist!=null?"clicktick":u.pressenter);
            if(strpeep!=null)
                g.drawString(strpeep,
                       vx + m.stringWidth(model)/2 - m.stringWidth(u.pressenter)/2,
                       y1 + yextra);
        }
        else if(letterlist !=null && showx > 0) {
           if(showtext != null) {
             g.drawString(showtext,
                           showx*rm.screenwidth/mover.WIDTH - m.stringWidth(showtext)/2,
                           showy*rm.screenheight/mover.HEIGHT + m.getAscent());

           }
           else g.drawString(model.substring(currpos, currpos + 1),
                         showx*rm.screenwidth/mover.WIDTH - m.stringWidth(model.substring(currpos,currpos+1))/2,
                         showy*rm.screenheight/mover.HEIGHT + m.getAscent());
            if (u.uppercase.indexOf(model.charAt(currpos)) >= 0) {
              g.drawString(u.gettext("u_", "capital"),
                           showx*rm.screenwidth/mover.WIDTH + m.stringWidth(model.substring(currpos,currpos+1))/2,
                           showy*rm.screenheight/mover.HEIGHT + m.getAscent());
            }
            else if (u.uppercase.indexOf(model.charAt(currpos)) < 0
                     && u.uppercase.indexOf(badchar) >= 0) {
              g.drawString(u.gettext("u_", "notcapital"),
                           showx*rm.screenwidth/mover.WIDTH + m.stringWidth(model.substring(currpos,currpos+1))/2,
                           showy*rm.screenheight/mover.HEIGHT + m.getAscent());
            }
        }
        else {
          if (splits != null) {
            String s[] = u.simplesplit(null, splits, false);
            short k = currpos;
            int sx = vx;
            for (i = 0; i < s.length; ++i) {
              if (k < s[i].length()) {
                g.drawString(s[i].substring(k, k + 1),
                             sx + m.stringWidth(s[i].substring(0, k)),
                             y1 + yextra);
                if (u.uppercase.indexOf(model.charAt(currpos)) >= 0) {
                  g.drawString(u.gettext("u_", "capital"),
                               sx + m.stringWidth(s[i].substring(0, k + 1)),
                               y1 + yextra);
                }
                else if (u.uppercase.indexOf(model.charAt(currpos)) < 0
                         && u.uppercase.indexOf(badchar) >= 0) {
                  g.drawString(u.gettext("u_", "notcapital"),
                               sx + m.stringWidth(s[i].substring(0, k + 1)),
                               y1 + yextra);
                }
                break;
              }
              sx += m.stringWidth(s[i] + "/");
              k -= s[i].length();
            }
          }
          else {
            g.drawString(model.substring(currpos, currpos + 1),
                         vx + m.stringWidth(model.substring(0, currpos)),
                         y1 + yextra);
            if (u.uppercase.indexOf(model.charAt(currpos)) >= 0) {
              g.drawString(u.gettext("u_", "capital"),
                           vx + m.stringWidth(model.substring(0, currpos + 1)),
                           y1 + yextra);
            }
            else if (u.uppercase.indexOf(model.charAt(currpos)) < 0
                     && u.uppercase.indexOf(badchar) >= 0) {
              g.drawString(u.gettext("u_", "notcapital"),
                           vx + m.stringWidth(model.substring(0, currpos + 1)),
                           y1 + yextra);
            }
          }
        }
      }
      if(model != null)  y1 += yinc;
      if(sharkStartFrame.currPlayTopic.phonics && !sharkStartFrame.currPlayTopic.phonicsw) {
        if (model != null && (i = (short) model.indexOf('-')) >= 0 && i >= currpos && letterlist==null) {
          g.setColor(textcolor);
          g.drawString("-", vx + Math.max(defwidth, m.stringWidth(model.substring(0, i))), y1 + yextra);
        }
        else if (model == null && slashat > 0 && slashat >= currpos) {
            g.setColor(textcolor);
            g.drawString("-", vx + defwidth*slashat, y1 + yextra);
        }
      }
      if(splits != null && splits.indexOf('/') >= 0) {
//startPR2004-09-15^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        isSpecialVowel = new boolean[splits.length()];
        isSpecialVowel = u.findSpecialVowels(splits);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         String s[] = u.simplesplit(null,splits,false), ss;
//startPR2004-09-15^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
           /*build a string array to match the s string array in which a 't' character
            corresponds to a letter that is part of a special vowel combination and an
            'f' character corresponds to a letter that is not*/
           String is[] = new String[s.length];
           for(int p=0; p<s.length; p++){
             is[p] = "";
           }
           int counter = 0;
           for(int n = 0; n< splits.length(); n++){
             if(splits.charAt(n) == '/'){
               counter++;
             }
             else{
               char ch;
               if(isSpecialVowel[n] == false){
                 ch = 'f';
               }
               else{
                 ch = 't';
               }
               is[counter] = is[counter].concat(String.valueOf(ch));
             }
           }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         short k=0;
         int sx = vx;
         for(i=0;i<s.length;++i) {
             ((Graphics2D)g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f));
              for(short j=0;j<s[i].length();++j) {
                if(u.vowels.indexOf(s[i].charAt(j)) >= 0
//startPR2004-09-15^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                   // colour if this character is part of a special vowel combination
                   ||is[i].charAt(j) == 't'
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                  || (i!=0 && j!=0) && s[i].charAt(j) == 'y'){
   //                  g.setColor(Color.pink.darker());
                    g.setColor(col_split_lighter);
                  }
                else {
  //                  g.setColor(Color.magenta.darker());
                    g.setColor(col_split_darker);
                }

                g.fillRect(sx+m.stringWidth(s[i].substring(0,j))+1,
                           y1,m.charWidth(s[i].charAt(j))-1,m.getHeight());
              }
             ((Graphics2D)g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
             if(val.length() > k) {
                 g.setColor(textcolor);
                 g.drawString(ss = val.substring(k,Math.min(k+s[i].length(),val.length())),sx,y1+yextra);
                 vx = sx +  m.stringWidth(ss);
             }
             k+=s[i].length();
             sx += m.stringWidth(s[i]+"/");
             if(k==val.length()) vx=sx;
          }
      }
      else if(val.length()>0 && letterlist==null) {
         g.setColor(textcolor);
         if(echoChar == 0) {
            if(rm != null && rm.mousemovertot > 0) {
               int vx1 = vx;
               int pp = (rm.mousemovers[0].x + rm.mousemovers[0].w -lettergap) *rm.screenwidth/mover.WIDTH;
               for(i=0;i<val.length();++i) {
                  if(val.charAt(i) == ' ');
//                    g.drawRect(vx1+1,y1+1, m.charWidth(' ')-2,m.getHeight()-2);
                  else g.drawString(val.substring(i,i+1),vx1,y1+yextra);
                  vx1 += m.charWidth(val.charAt(i));
               }

            }
            else if(val.indexOf(' ') >=0) {
              int vx1 = vx;
              for(i=0;i<val.length();++i) {
                 if(val.charAt(i) == ' ');
//                   g.drawRect(vx1+1,y1+1, m.charWidth(' ')-2,m.getHeight()-2);
                 else g.drawString(val.substring(i,i+1),vx1,y1+yextra);
                 vx1 += m.charWidth(val.charAt(i));
              }
            }
            else {
              g.drawString(val,vx,y1+yextra);
            }
            vx += m.stringWidth(val.substring(0,Math.min(currpos,val.length())));
         }
         else  {
            val2 =  new String("");
            for(i=0;i<val.length();++i) val2= val2+String.valueOf(echoChar);
            g.drawString(val2,vx=x+(w-m.stringWidth(val2))/2,y1+yextra);
            vx += m.stringWidth(val2.substring(0,Math.min(val2.length(),currpos)));
         }
      }
      currpos = (byte)Math.min(currpos,val.length());
//      if(sharkGame.gtime() > startafter
//          && (model == null || dontcompare || val.length() < model.length())) {
      if(sharkGame.gtime() > startafter && !ended && letterlist==null) {
           g.setColor(cursorcolor);
           if (currpos == val.length()) {
             if (splits != null)
               g.drawRect(vx, y1, m.charWidth(model.charAt(Math.max(0, currpos - 1))), textheight - 1);
             else
               g.drawRect(vx, y1, defwidth, textheight - 1);
           }
           else
             if (insert) {
               g.drawArc(vx - defwidth, y1, defwidth, textheight - 1, -90, 180);
               g.drawArc(vx, y1, defwidth, textheight - 1, 90, 180);
               g.drawLine(vx - defwidth / 2, y1, vx + defwidth / 2, y1);
               g.drawLine(vx - defwidth / 2, y1 + textheight - 1, vx + defwidth / 2, y1 + textheight - 1);
             }
             else {
               g.drawRect(vx, y1, m.charWidth( (echoChar == 0) ? val.charAt(currpos) : echoChar), textheight - 1);
             }
      }
      valy = y1;
      if(letterlist !=null && letters == null &&  sharkGame.gtime() > startafter) {
        if(phonemes != null) addphonemes(x, y + h / 2 - textheight, w, textheight * 2);
        else addletters(x, y + h / 2 - textheight, w, textheight * 2);
      }
      y1 += yinc;
      if(buttons != null) {
         x1 = (w - bw * buttons.length) / (buttons.length+1);
         g.setFont(fb);
         m = g.getFontMetrics();
         for(i=0;i<buttons.length;++i) {
            x1 = bx[i];
            g.setColor(buttonbkcolor);
            g.fillRect(x1,y1,bw,bh);
            u.buttonBorder(g, new Rectangle(x1,y1,bw,bh),buttonbkcolor,true);
            g.setColor(buttoncolor);
            g.drawString(buttons[i],x1 + (bw-m.stringWidth(buttons[i]))/2,
                                    y1 + (bh + m.getAscent()-m.getDescent())/2);
         }
      }
      if( sharkGame.gtime() > startafter && letterlist != null && val.length()  == 0) {
          g.setColor(Color.red);
          int xx = lettervalx*rm.screenwidth/mover.WIDTH;
          g.drawRect(xx, valy - m.getHeight()/2, Math.max(m.stringWidth(model+" "), (x+w/2 - xx)*2), m.getHeight()*2);

        }
   }
   public boolean keypressed(KeyEvent e) {    // return true if needs redraw
      if(ended || sharkGame.gtime() < startafter) return false;
      short i;
      int code = e.getKeyCode();
      if(letterlist != null && code != KeyEvent.VK_ESCAPE && code != KeyEvent.VK_F1) return false;
      switch(code) {
         case KeyEvent.VK_INSERT: insert = !insert; return true;
         case KeyEvent.VK_ESCAPE: action.escape(); return false;
         case KeyEvent.VK_F1: action.f1(); return false;
         case KeyEvent.VK_ENTER: action.enter(); return false;
         case KeyEvent.VK_DELETE:
               if(currpos < val.length()) {
                  val = val.substring(0,currpos)+val.substring(currpos+1);
                  return true;
               }
               return false;
         case KeyEvent.VK_BACK_SPACE:
               if(!cantgoback && currpos > 0 && val.length()>0) {
                  --currpos;
                  val = val.substring(0,currpos)+val.substring(currpos+1);
                  return true;
               }
              return false;
         case KeyEvent.VK_LEFT:
               if(!cantgoback && currpos > 0) {
                  --currpos;
                  return true;
               }
               return false;
         case KeyEvent.VK_RIGHT:
               if(currpos < val.length()){
                   ++currpos;
                  return true;
               }
               return false;
         case KeyEvent.VK_HOME: if(!cantgoback) currpos=0;  return true;
         case KeyEvent.VK_END: if(!cantgoback) currpos = (byte)val.length(); return true;
      }
      return false;
   }
   public boolean keytyped(KeyEvent e) {    // return true if needs redraw
     if(letterlist != null) return false;
     if (ended || sharkGame.gtime() < startafter)
       return false;
     short i;
     char key = (char) e.getKeyChar();
     if(key == KeyEvent.VK_BACK_SPACE || key == KeyEvent.VK_ENTER || key == KeyEvent.VK_DELETE || key == KeyEvent.VK_ESCAPE) return false;
     if(!dontcompare && model != null) {
        if(model.length()>currpos && key == model.charAt(currpos)) {
           val = val + String.valueOf((char)key);
           ++currpos;
           if(currpos < model.length() && sharkStartFrame.currPlayTopic.phonics && !sharkStartFrame.currPlayTopic.phonicsw
                &&  model.charAt(currpos) == '-') {
               val += "-";
               ++currpos;
           }
           errortot=0;
           showchar = false;
           badchar =0;
//           if(sharkStartFrame.currPlayTopic.phonics && model.length()==1 && currpos == model.length()) {
//             action.end();
//             ended = true;
//           }
           return(true);
        }
        if (key != KeyEvent.CHAR_UNDEFINED) {
           action.error();
           if(++errortot > 2) {
              showchar = true;
              badchar = key;
              return true;
           }
        }
        return false;
     }
     if(key != KeyEvent.CHAR_UNDEFINED) {
        currpos = (byte)Math.min(val.length(),currpos);
        if(maxlen > 0 && val.length() >= maxlen
             && (insert || currpos == val.length())) {
             noise.beep();
             return false;
        }
        if(currpos == val.length())
               val = val + String.valueOf((char)key);
        else if(insert)
            val = val.substring(0,currpos) + String.valueOf((char)key)+val.substring(currpos);
        else val = val.substring(0,currpos) + String.valueOf((char)key)+val.substring(currpos+1);
        ++currpos;
        if(sharkStartFrame.currPlayTopic.phonics && !sharkStartFrame.currPlayTopic.phonicsw) {
           if (model != null && model.charAt(currpos) == '-') {
             val += "-";
             ++currpos;
           }
           else if (model == null && currpos==slashat) {
               val += "-";
              ++currpos;
           }
       }
        return(true);
     }
     return false;
   }
   public void mouseClick(int mx, int my) {

      if(buttons != null && my > by && my < by+bh)  for(short i=0;i<buttons.length;++i) {
         if(mx > bx[i] && mx < bx[i] + bw) action.button((byte)i);
      }
      else action.mouseClick();
   }
   //-------------------------------------------------------------------------------------------------
   public void setletterlist(word ww[], word w, word w2) {
      int i,j;
      allwords = ww;
      String s = w.v();
      letterlist = new String[u.lowercase.length()];
      for(i=0;i<u.lowercase.length();++i) letterlist[i] = u.lowercase.substring(i,i+1);
      for(i=0;i<s.length();++i) {
        letterlist = u.addStringSort(letterlist, s.substring(i, i + 1).toLowerCase());
      }
//      if(w2 != null) {  //if converting, we may need more
//          s = w2.v();
//          String testlist[] = new String[letterlist.length];
//          System.arraycopy(letterlist,0,testlist,0,letterlist.length);
//          for(i=0;i<s.length();++i) {
//             if((j = u.findString(testlist,s.substring(i,i+1))) >= 0 ) testlist = u.removeString(testlist,s.substring(i,i+1));
//             else   letterlist = u.addString(letterlist,s.substring(i,i+1));
//
//          }
//      }
      String ss[] = new String[0];
      for(j=0;j<ww.length;++j) {
        s = ww[j].v();
        for (i = 0; i < s.length(); ++i) {
          ss = u.addStringSort(ss, s.substring(i, i + 1).toLowerCase());
          if (Character.isUpperCase(s.charAt(i)))  caps = true;
        }
      }
//      int o[] = u.shuffle(u.select(ss.length,ss.length));
//      int want = w2 == null ? 8 : 7 + w2.v().length();
//      for(i=0;letterlist.length < want && i<o.length;++i) {
//        if(u.findString(letterlist,ss[o[i]].toLowerCase())<0 ) letterlist = u.addString(letterlist, ss[o[i]].toLowerCase());
//      }
//      u.shuffle(letterlist);
//      if(dontcompare || !(sharkStartFrame.currPlayTopic.phonics && model.length()==1))
            letterlist = u.addString(letterlist,new String(new char[]{(char)keypad.ENTER}));
      tick = sharkImage.random("tick_");
   }
   //-------------------------------------------------------------------------------------------------
   public void setphonemelist(word ww[], word w) {
      int i,j;
      phonemes = w.phsegs();
      currword = w;
      allwords = ww;
      String s[] = w.phsegs();
      letterlist = new String[0];
      for(i=0;i<s.length;++i) {
        if(s[i].length()>0) letterlist = u.addString(letterlist, s[i].toLowerCase());
      }
      String ss[] = new String[0];
      for(j=0;j<ww.length;++j) {
        s = ww[j].phsegs();
        for (i = 0; i < s.length; ++i) {
          if(s[i].length()>0) {
            ss = u.addStringSort(ss, s[i].toLowerCase());
            if (Character.isUpperCase(s[i].charAt(0))) caps = true;
          }
        }
      }
      int o[] = u.shuffle(u.select(ss.length,ss.length));
      int want =  8 ;
      for(i=0;letterlist.length < want && i<o.length;++i) {
        if(u.findString(letterlist,ss[o[i]].toLowerCase())<0 ) letterlist = u.addString(letterlist, ss[o[i]].toLowerCase());
      }
      u.shuffle(letterlist);
      letterlist = u.addString(letterlist,new String(new char[]{(char)keypad.ENTER}));
      tick = sharkImage.random("tick_");
   }
   //-----------------------------------------------------------------------------------------------
   void addphonemes(int x, int y, int w, int h) {
      rm = runningGame.currGameRunner.game.gamePanel;
      rm.showSprite = true;
      vgap = m.charWidth('w')* mover.WIDTH/rm.screenwidth;
      vgap = vgap/2 + u.rand(vgap/2);
      x = x*mover.WIDTH/rm.screenwidth;
      y = y*mover.HEIGHT/rm.screenheight;
      w = w*mover.WIDTH/rm.screenwidth;
      h = h*mover.HEIGHT/rm.screenheight;
      int i,j,ww=0;
      lettergap = m.charWidth('w')*mover.WIDTH/rm.screenwidth/2;
      int num;
      num = caps?letterlist.length*2-1:letterlist.length;
      letters = new letter[num];
      for(i=0;i<letterlist.length;++i) {
         letters[i] = new letter(letterlist[i],rm.screenwidth,rm.screenheight);
         if(caps && !letters[i].cret ) {
           String s = letterlist[i].substring(0,1).toUpperCase() + letterlist[i].substring(1);
           letters[i + letterlist.length] = new letter(s, rm.screenwidth, rm.screenheight);
         }
         if(!letters[i].cret) ww += letters[i].w;
      }
      for(i=0;i<phonemes.length;++i) {
         for(j=0;j<letters.length;++j) {
            if(letters[j].s.equalsIgnoreCase(phonemes[i])) {tilewidth += letters[j].w; break;}
         }
      }
      int av = tilewidth/model.length();
      lettervalx = x+w/2 - (tilewidth + av + u.rand(av*3/2))/2;
      lettervaly = valy*mover.HEIGHT/rm.screenheight;
      int xx = Math.max(0,Math.min(mover.WIDTH-ww, x + w/2 - ww/2));
      int hh = letters[0].h,bandh = hh*3;
      int yy1,yy2,yy3=0;
      if(y+h/2 > (caps? mover.HEIGHT/2:mover.HEIGHT*3/4)) {
        if(caps) {
           yy1 = Math.max(hh,y/2 - bandh);
           yy2 = y/2;
           yy3 = yy2 + (yy2-yy1);
        }
        else {
          yy1 = Math.max(hh,y/2 - bandh/2);
          yy2 = y/2 + (y/2 - yy1);
        }
        putbacky1 = 0;
        putbacky2 = y;
      }
      else {
        if(caps) {
           yy1 = Math.max(y+h+hh,(y+h+mover.HEIGHT)/2 - bandh);
           yy2 = (y+h+mover.HEIGHT)/2;
           yy3 = yy2 + (yy2-yy1);
        }
        else {
          yy1 = Math.max(y+h+hh,(y+h+mover.HEIGHT)/2 - bandh/2);
          yy2 = (y+h+mover.HEIGHT)/2 + ((y+h+mover.HEIGHT)/2 - yy1);
        }
        putbacky1 = y+h;
        putbacky2 = mover.HEIGHT;
      }
      int yy,lastyy1=0,lastyy2=0;
      for(i=0;i<letterlist.length;++i) {
         if(letters[i].cret) {
           yy = caps?yy2-hh/2 : (yy1+yy2)/2-hh/2;
         }
         else do{
           yy = yy1 + u.rand(yy2-yy1-hh);
         }
         while(i>0 && Math.abs(yy-lastyy1) < (yy2-yy1-hh)/3);
         yy = Math.min(yy,mover.HEIGHT - letters[i].h);
         lastyy1 = yy;
         if(letters[i].cret) {
           rm.addMover(letters[i], letters[i].startx = Math.min(mover.WIDTH-letters[i].w, (x+w/2)*2 - lettervalx) ,
                       letters[i].starty = valy*mover.HEIGHT/rm.screenheight);
         }
         else {
           rm.addMover(letters[i], letters[i].startx = xx, letters[i].starty = yy);
         }
         if(caps && !letters[i].cret) {
           do{
             yy = yy2 + u.rand(yy3-yy2-hh);
           }
           while(i>0 && Math.abs(yy-lastyy2) < (yy3-yy2-hh)/3);
           lastyy2 = yy;
           if(isInList(letters[i + letterlist.length].s))
               rm.addMover(letters[i + letterlist.length], letters[i + letterlist.length].startx = xx,
                           letters[i + letterlist.length].starty = yy );
         }
         xx += letters[i].w;
      }
      if(!cantgoback) {
         int currvalx = lettervalx;
         for(i=0; i<val.length(); ++i) {
           for(j=0;j<letters.length;++j) {
              if(letters[j].s.equals(val.substring(i,i+1))) {
                letters[j].x = letters[j].tox = currvalx;
                currvalx += letters[j].w;
                letters[j].y = letters[j].toy = lettervaly;
                break;
              }
           }
         }
      }
   }

   boolean isInList(String s) {
       word w[] =runningGame.currGameRunner.w;
       for(int i = 0; i < w.length; i++){
           if(w[i].v().indexOf(s)>=0)return true;
       }
        return false;
   }

   //-----------------------------------------------------------------------------------------------
       // add all letters using whole width of screen
   void addletters(int x, int y, int w, int h) {
     int y1, y2,i,j,x1=0,wi=mover.WIDTH;
     rm = runningGame.currGameRunner.game.gamePanel;
     rm.showSprite = true;
     int tilesacross = letterlist.length -1;
     tilewidth = mover.WIDTH / tilesacross;
     while(Math.max(m.charWidth('w'),m.charWidth('W')) > tilewidth*rm.screenwidth/mover.WIDTH) {
//startPR2007-11-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//            f  = new Font(f.getName(),f.getStyle(),f.getSize()-1);
           f  =  f.deriveFont((float)f.getSize()-1);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        m = rm.getFontMetrics(f);
     }
     if(Math.max(m.charWidth('w'),m.charWidth('W')) < tilewidth*rm.screenwidth/mover.WIDTH*3/4) {
          tilewidth = Math.max(m.charWidth('w'),m.charWidth('W'))*mover.WIDTH/rm.screenwidth*4/3;
          wi = tilewidth*tilesacross;
          x1 = (mover.WIDTH-wi)/2;
     }
     int tileheight = m.getHeight()*mover.HEIGHT/rm.screenheight;
     if(caps) {y1 = mover.HEIGHT - tileheight*5;y2 = mover.HEIGHT - tileheight*5/2;}
       else {y1 = mover.HEIGHT - tileheight*3; y2 = mover.HEIGHT - tileheight;}
      y1 -= allowy;
      y2 -= allowy;
      toplet = y1;
      int num = caps?letterlist.length*2-1:letterlist.length;
      letters = new letter[num];
      lettergap = m.charWidth('w')*mover.WIDTH/rm.screenwidth/2;
//      lettervalx = (x+w/2)*mover.WIDTH/rm.screenwidth - tilewidth*(model.length() + 1 +  u.rand(2))/2;
      int xcalc = (x+w/2)*mover.WIDTH/rm.screenwidth - tilewidth*(model.length() + 1 +  u.rand(2))/2;
      lettervalx = Math.min(xcalc, mover.WIDTH - lettergap*4 - tilewidth*model.length());
      lettervaly = valy*mover.HEIGHT/rm.screenheight;
      for(i=0;i<letterlist.length;++i) {
         letters[i] = new letter(letterlist[i],rm.screenwidth,rm.screenheight);
         if(letters[i].cret) {
           letters[i].w = tilewidth*2;
           letters[i].h = tileheight;
           rm.addMover(letters[i], letters[i].startx = Math.min(mover.WIDTH-letters[i].w, (x+w/2)*mover.WIDTH/rm.screenwidth*2 - lettervalx) ,
                       letters[i].starty = valy*mover.HEIGHT/rm.screenheight);
         }
         else {
           letters[i].w = tilewidth;
           letters[i].h = tileheight;
           rm.addMover(letters[i], letters[i].startx = x1 + wi * i / tilesacross,
                       letters[i].starty = y1 + (int) ( (y2 - y1) * Math.sin(Math.PI * i / tilesacross)));
         }
         if(caps && !letters[i].cret ) {
           letters[i + letterlist.length] = new letter(letterlist[i].toUpperCase(), rm.screenwidth, rm.screenheight);
           letters[i + letterlist.length].w = tilewidth;
           letters[i + letterlist.length].h = tileheight;
           rm.addMover(letters[i + letterlist.length], letters[i + letterlist.length].startx = x1 + wi*i/tilesacross,
                       letters[i + letterlist.length].starty = y1 + tileheight*3/2 + (int)((y2-y1)*Math.sin(Math.PI * i / tilesacross)));
         }
       }
       putbacky1 = y+h;
       putbacky2 = mover.HEIGHT;
       if(!cantgoback) {
          int currvalx = lettervalx;
          for(i=0; i<val.length(); ++i) {
            for(j=0;j<letters.length;++j) {
               if(letters[j].startx==letters[j].x && letters[j].starty==letters[j].y
                  && letters[j].s.equals(val.substring(i,i+1))) {
                 letters[j].x = letters[j].tox = currvalx;
                 currvalx += letters[j].w;
                 letters[j].y = letters[j].toy = lettervaly;
                 letters[j].extraletter();
                 break;
               }
            }
          }
       }
   }
   //----------------------------------------------------
   int[] lettersinword() {
     int yy = lettervaly;
     int i,j,ret[]=new int[0];
      loopi:for(i=0;i<letters.length;++i) {
         if((letters[i].startx != letters[i].x || letters[i].starty != letters[i].y)
           && letters[i].y+letters[i].h > lettervaly &&  letters[i].y < lettervaly +letters[i].h && !rm.movesWithMouse(letters[i])) {
            for(j=0;j<ret.length;++j) {
               if(letters[i].x < letters[ret[j]].x) {
                  ret = u.addint(ret,i,j);
                  continue loopi;
               }
            }
            ret = u.addint(ret,i);
         }
      }
      return ret;
   }
   int fitletters(letter letter) {
     int i,j=-1,k,let[] = lettersinword();
     int w = letter.w, x= letter.x;
     for(i=0;i<let.length;++i) {
        j = let[i];
        if(x+w < letters[j].x + letters[j].w) break;
     }
     if(j>=0 && letters[j].magice) {
        if(x > letters[j].x && x+w < letters[j].x + letters[j].w ) return j;
     }
     int xx = (i==0?lettervalx:letters[let[i-1]].x + letters[let[i-1]].w) + w;
     for (k = i; k < let.length; ++k) {
         j = let[k];
         letters[j].moveto(Math.min(xx, mover.WIDTH - letters[j].w), lettervaly, 200);
         xx += letters[j].w;
     }
     return -1;
   }
   String calcval() {
     int i,j,let[] = lettersinword();
     int xx = lettervalx;
     String ret = "";
     for(i=0;i<let.length;++i) {
        j = let[i];
        ret += letters[j].s;
        if(i>0 && letters[let[i-1]].magice) {
            letters[j].moveto(letters[let[i-1]].mid() - letters[j].w/2,lettervaly,200);
        }
        else {
          letters[j].moveto(xx, lettervaly, 200);
          xx += letters[j].w;
        }
     }
     return ret;
   }
   public boolean correct_phonemes() {
     int i,let[] = lettersinword();
     if (let.length != phonemes.length) return false;
     if(singlesound) return sameval(model,val);
     if(let.length == 1) {
       if(letters[let[0]].s.equals(phonemes[0])) {return true;}
       String phonic1 = spokenWord.findPhonicsBit(letters[let[0]].s,allwords);
       String phonic2 = spokenWord.findPhonicsBit(phonemes[0],allwords);
       return phonic1.equals(phonic2);
     }
     for(i=0;i<let.length;++i) if(!letters[let[i]].s.equals(phonemes[i])) {return false;}
     return true;
   }
   boolean sameval(String s1,String s2) {
     if(phonemes != null && !singlesound) {
        return (s1.equals(phonemes[currph]));
     }
      int i = s1.indexOf('-');
      if(i<0) return s1.equals(s2);
      else return s1.substring(0,i).equals(s2.substring(0,i)) && s1.substring(i+1).equals(s2.substring(i+1));
   }
   //=============================================================================================
   class letter extends mover {
       String s;
       int startx,starty;
       boolean cret,upper,wasover;
       long saying;
       boolean magice;
       int isover;
       String gapletter = "-";

       letter(String s1, int sw, int sh) {
          s = s1;
          magice = s.endsWith("-e");
          w = m.stringWidth(s1.toLowerCase())*mover.WIDTH/sw + (magice?lettergap*3:lettergap);
          h = m.getHeight()*mover.HEIGHT/sh;
          cret = s.charAt(0) == keypad.ENTER;
          if(cret) w = lettergap*4;
          upper = Character.isUpperCase(s.charAt(0));
       }
       public void paint(Graphics g, int x1, int y1, int w1, int h1) {
         boolean saying1;
         while(m.getHeight() > h1) {
//startPR2007-11-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//            f  = new Font(f.getName(),f.getStyle(),f.getSize()-1);
           f  = f.deriveFont((float)f.getSize()-1);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
           m = rm.getFontMetrics(f);
         }
         if(shark.macOS) ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
         if(saying != 0 && System.currentTimeMillis() >= saying) saying=0;
         if(thisi.ended) kill = true;
         if(wantend !=0 && System.currentTimeMillis() >wantend) {
            wantend = 0;
            thisi.ended = true;
            action.end();
            kill=true;
          }
          int i,j,k;
          if(!cantgoback && rm.movesWithMouse(this)) {
            int startvalx = valx * mover.WIDTH / rm.screenwidth;
            int endvalx = startvalx + m.stringWidth(val) * mover.WIDTH / rm.screenwidth;
            if(rm.mousey > lettervaly && rm.mousey < lettervaly+h || Math.abs(y-lettervaly) < h/2) {
              isover = fitletters(this);
              wasover = true;
            }
            else if(wasover) {wasover=false;calcval();}
          }
          if(!thisi.ending) {
            g.setColor(saying!=0?Color.pink:(cret?Color.white:wordlist.bgcoloruse));
            g.fillRect(x1, y1, w1, h1);
            if (mouseOver) {
              g.setColor(Color.red);
              g.drawRect(x1, y1, w1, h1);
              g.drawRect(x1 + 1, y1 + 1, w1 - 2, h1 - 2);
            }
            else {
              g.setColor(Color.black);
              g.drawRect(x1, y1, w1, h1);
            }
          }
          g.setFont(f);
          g.setColor(thisi.ending?(cret?Color.white:wordlist.bgcoloruse):Color.black);

          if(cret) tick.paint(g,x1,y1,w1,h1);
          else {
            if(magice) {
               int xx = x1 + w1 - lettergap*rm.screenwidth/mover.WIDTH/2 - m.charWidth('e');
               if(thisi.ending) {
                 String s1 = s.substring(0, s.length() - 2) + gapletter;
                 xx = x1 + w1/2 - m.stringWidth(s)/2 - lettergap*rm.screenwidth/mover.WIDTH + m.stringWidth(s1);
               }
               int yy =y1 + h1/2-m.getHeight()/2+m.getAscent();
               g.drawString("e",xx,yy);
               if(!thisi.ending || val.indexOf('-') > 0) {
                 xx -= lettergap*rm.screenwidth/mover.WIDTH + m.charWidth('-');
                 g.drawString("-", xx, yy);
                 xx -= lettergap * rm.screenwidth / mover.WIDTH + m.stringWidth(s.substring(0, s.length() - 2));
               }
               else {
                 xx -=  m.stringWidth(gapletter) + m.stringWidth(s.substring(0, s.length() - 2));
               }
               g.drawString(s.substring(0,s.length()-2),xx, yy);
            }
            else {
              if(upper && !thisi.ending) g.drawString(s,x1+w1/2-m.stringWidth(s)/2, y1 + h1/2-m.getHeight()/2+m.getAscent());
              else g.drawString(s,x1+w1/2-m.stringWidth(s)/2, y1 + h1/2-m.getHeight()/2+m.getAscent());
            }
          }
       }
       public void mouseClicked(int x1, int y1) {
          int i,j;
          if(cret) {
            if(dontcompare
                || cantgoback && val.length() == model.length()
                || !cantgoback && (val.equals(model) || ((phonemes!=null || currword.phonics) && correct_phonemes())))  {
               if(dontcompare) action.enter();
               else isend();
             }
             else {
                action.error();
                if(phonemes!=null) {
                   int let[] = lettersinword();
                   if (let.length >0) new ssay(let);
                }
             }
             return;
          }
          int endvalx = lettervalx + m.stringWidth(model != null?model:val) + vgap*2;
          int endvaly = lettervaly + m.getHeight()* mover.HEIGHT/rm.screenheight;
          int currvalx = lettervalx;
          for(i=0;i<letters.length;++i) {
            if(letters[i]!=this && letters[i].y == lettervaly
               && (letters[i].startx != letters[i].x || letters[i].starty != letters[i].y)) {
              if(val.endsWith("-e") && letters[i].magice)
                currvalx = Math.max(currvalx, letters[i].x + letters[i].w
                                    - (m.charWidth('e') + m.charWidth('-')/2) * mover.WIDTH/rm.screenwidth - lettergap*3/2 - w/2);
              else currvalx = Math.max(currvalx, letters[i].x + letters[i].w);
            }
          }
          if (!rm.movesWithMouse(this)) {
             if (!cantgoback || y != lettervaly)  rm.moveWithMouse(this);
          }
          else if(rm.mousey > lettervaly && rm.mousey < lettervaly +h
                                                || Math.abs(y - lettervaly) < h)  {
            if(cantgoback) {
              if(s.length()+currpos > model.length()
                 || !sameval(s,model.substring(currpos,currpos+s.length()))) {
                 action.error();
                 rm.drop(this);
                 if(phonemes ==null) removedupletter();
                 moveto(startx,starty,600);
                 if(++errortot > 2) {
                    showchar = true;
                    badchar = s.charAt(0);
                    showx = fromx+w/2;
                    showy = fromy - m.getHeight()
                               *mover.HEIGHT/rm.screenheight;
                    showtext = null;
                    if(phonemes != null) {
                       showtext = phonemes[currph];
                    }
                 }
              }
              else {
                errortot = 0;
                showchar = false;
                badchar = 0;
                if(phonemes==null) extraletter();
                if (val.length() == 0) {
                  rm.drop(this);
                  moveto(lettervalx, lettervaly, 500);
                  val += s;
                  currph = 1;
                  if(magice) currpos=1;
                  else currpos = (byte)s.length();
//                  if (!dontcompare && sharkStartFrame.currPlayTopic.phonics && model.length() == 1 && currpos == model.length()) {
//                    isend();
//                  }
                }
                else
                  if (val.length() > 0 && (Math.abs(x - currvalx) < w*2
                                           || Math.abs(rm.mousex - currvalx + w / 2) < w*2)) {
                    if(val.endsWith("-e")) {
                       val = val.substring(0,currpos) + s + "e";
                       currpos = (byte)val.length();
                    }
                    else {
                      val += s;
                      if(magice) ++currpos;
                      else currpos+=s.length();
                    }
                    ++currph;
                    rm.drop(this);
                    moveto(currvalx, lettervaly, 500);
                  }
                  else {
                    if(cantgoback) noise.beep();
                    else action.error();
                  }
              }
            }
            else {
                rm.drop(this);
                if(phonemes==null) extraletter();
                val=calcval();
            }
          }
          else if(rm.mousey >= putbacky1 && rm.mousey <= putbacky2
                   || y  >= putbacky1 && y+h <= putbacky2 ) {
             rm.drop(this);
             if(phonemes ==null) {
                removedupletter();
                moveto(startx,starty,600);
             }
             else {
               startx = x;
               starty = y;
             }
             val=calcval();
          }
        }
        void isend() {
          int i,j;
          wantend = System.currentTimeMillis()+1500;
          ending = true;
          int let[] = lettersinword();
          for(i=0;i<letters.length;++i) {
            if(!u.inlist(let,i)) letters[i].kill = true;
          }
          int currvalx = valx* mover.WIDTH/rm.screenwidth;
          for(i=0;i<let.length;++i) {
             j = let[i];
             if(i>0 && letters[let[i-1]].magice) {
               currvalx += (m.stringWidth(letters[let[i-1]].s) - m.charWidth('-') + m.stringWidth(letters[j].s))* mover.WIDTH / rm.screenwidth;
               letters[j].moveto(currvalx
                                 - (m.charWidth('e') + m.stringWidth(letters[j].s)) * mover.WIDTH / rm.screenwidth
                                       - (letters[j].w - m.stringWidth(letters[j].s) * mover.WIDTH / rm.screenwidth) / 2,
                                 lettervaly, 500);
                letters[let[i-1]].gapletter = letters[j].s;
             }
             else {
               int ww = m.stringWidth(letters[j].s) * mover.WIDTH / rm.screenwidth;
               if(letters[j].magice) ww += lettergap*2;
               letters[j].moveto(currvalx - (letters[j].w-ww)/2, lettervaly, 500);
               if(!letters[j].magice) currvalx += ww;
             }
           }
        }
        void say(int chain[]) {
          if(!spokenWord.sayPhonicsBit(s, new word[]{currword}))
            spokenWord.sayPhonicsBit(s, allwords);
          saying = spokenWord.endsay2+400;
          if(chain.length > 0) new ssay(chain);
        }
        int mid() {
           return tox + w - lettergap*3/2 - (m.charWidth('e') + m.charWidth('-')/2)*mover.WIDTH/rm.screenwidth;
        }
        void extraletter() {
           letter nletters[] = new letter[letters.length+1];
           System.arraycopy(letters,0,nletters,0,letters.length);
           letters = nletters;
           letter le = new letter(s,rm.screenwidth,rm.screenheight);
           le.startx = startx;
           le.starty = starty;
           le.w = w;
           le.h = h;
           letters[letters.length-1] = le;
           rm.addMover(le,startx,starty);
        }
        void removedupletter() {
           int i;;
           for(i=0;i<letters.length;++i) {
             if (letters[i] != this
                 && letters[i].startx == startx && letters[i].starty == starty
                 && letters[i].startx == letters[i].x && letters[i].starty == letters[i].y) {
               letters[i].kill = true;
               letter nletters[] = new letter[letters.length - 1];
               System.arraycopy(letters, 0, nletters, 0, i);
               if(i<letters.length-1) System.arraycopy(letters, i+1, nletters, i, letters.length-i-1);
               letters = nletters;
               break;
             }
           }
        }
    }
    class ssay extends spokenWord.whenfree {
       int chain[];
       ssay(int chain1[]) {
          super(0);
          chain = chain1;
        }
        public void action() {
          letters[chain[0]].say(u.removeint(chain,0));
        }
    }
}
