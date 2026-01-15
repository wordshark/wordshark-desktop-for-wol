package shark;

import java.awt.*;
import java.awt.image.*;

public class word {
  public String value,pat,tempsplit;
  public String oldvalue;              // value before spelling change
  public boolean homophone,nohomo;
  public boolean paired;
  public boolean bad;        // also used to show sentence in recordwords
  public String heading;
  public byte companions;
  public String database;   // if non-null holds dataset for spoken word
  public spokenWord spokenword;
  static long lastsay;
  long lastsaythis;
  String specialvowels[] = u.splitString(u.gettext("specialvowels","combo"));
  public boolean phonics,phonicsw,onephoneme;
  public int phonicsmagic[];
  public boolean phonicsilent[];
  public int phonicsplitlist[];
  spokenWord sw2[];    // phonics to be spoken and shown
  public int curr2=-1;
  Component repaint;
  boolean sayingextra;
  public String picture;   // use this in place of picture with name same as word#
  public String dbprivate;

  public static int SPLIT = 1;
  public static int PHONICSPLIT = 128;
  public static int HIGHLIGHT = 2;
  public static int VOWELS = 4;
  public static int SHAPE = 8;
  public static int CENTRE = 16;
  public static int SHUFFLED = 32;
  public static int ADDMAGIC = 64;

  public word(String val,String database1) {
    if (val.length() > 2 && val.charAt(0) == '('
        && val.charAt(val.length() - 1) == ')') {
      val = val.substring(1, val.length() - 1);
      bad = true;
    }
    value = oldvalue = new String(val);
    if (database1 != null && !database1.equalsIgnoreCase(sharkTree.publictopics)){
//      database =  sharkStartFrame.resourcesPlus+ database1+sharkStartFrame.resourcesFileSuffix;
      if(u.findString(sharkStartFrame.publicExtraCourseLib, database1)<0)
        dbprivate = sharkStartFrame.resourcesPlus+database1+sharkStartFrame.resourcesFileSuffix;
      else 
        dbprivate = database1;
      database = database1;
    }
    if (spellchange.active)
      spellchange.spellchange(this);
    phonics = val.indexOf('+') < 0 && val.indexOf('=') >= 0;
    phonicsw = phonics &&  val.indexOf(u.phonicsplit) >= 0;
    onephoneme = phonics && (val.indexOf(u.phonicsplit) > val.indexOf('=') || !phonicsw);
    if(phonicsw) {
      phonicsmagic = isphonicsmagic();
      phonicsilent = isphonicsilent();
      phonicsplitlist = phsplitlist();
    }
  }
  public word(word w) {
     value = new String(w.value);
     oldvalue = new String(w.oldvalue);
     database = w.database;
     dbprivate = w.dbprivate;
     spokenword = w.spokenword;
     homophone = w.homophone;
     paired = w.paired;
     bad = w.bad;
     phonics = w.phonics;
     phonicsw = w.phonicsw;
     phonicsmagic = w.phonicsmagic;
     phonicsilent = w.phonicsilent;
     phonicsplitlist = w.phonicsplitlist;
     onephoneme = w.onephoneme;
     companions = w.companions;
  }
  public boolean split() {
    return (value.indexOf('=') < 0 && value.indexOf('/') >= 0);
  }
  public String toString() {return vsplit();}
  public String v() {  //   return normal string
    short i;
    String s = ((i=(short)value.indexOf('@'))>0) ? value.substring(0,i):new String(value);
    if((i=(short)s.indexOf('='))>0) s = s.substring(0,i);
    if((i=(short)s.indexOf('~'))>0) s = s.substring(0,i);
    if((i=(short)s.indexOf('!'))>0) s = s.substring(0,i);
    while((i = (short)s.indexOf('[')) >= 0) s = s.substring(0,i)+s.substring(i+1);
    while((i = (short)s.indexOf(']')) >= 0) s = s.substring(0,i)+s.substring(i+1);
    while((i = (short)s.indexOf('/')) >= 0) s = s.substring(0,i)+s.substring(i+1);
    while((i = (short)s.indexOf(u.phonicsplit)) >= 0) s = s.substring(0,i)+s.substring(i+1);
    while((i = (short)s.indexOf('_')) >= 0) s = s.substring(0,i)+s.substring(i+1);
    return s;
  }
  public String vhi() {  //   return string with hilights
    short i;
    String s = ((i=(short)value.indexOf('@'))>0) ? value.substring(0,i):new String(value);
    if((i=(short)s.indexOf('='))>0) s = s.substring(0,i);
    if((i=(short)s.indexOf('~'))>0) s = s.substring(0,i);
    if((i=(short)s.indexOf('!'))>0) s = s.substring(0,i);
    while((i = (short)s.indexOf('/')) >= 0) s = s.substring(0,i)+s.substring(i+1);
    while((i = (short)s.indexOf(u.phonicsplit)) >= 0) s = s.substring(0,i)+s.substring(i+1);
    while((i = (short)s.indexOf('_')) >= 0) s = s.substring(0,i)+s.substring(i+1);
    return s;
  }
  public String vsay() {  //   for speech - includes @ + letters after it - but stops before @@
    short i,j;
    String s = new String(oldvalue);
    if((i=(short)s.indexOf('='))>0) s = s.substring(0,i);
    if((j=(short)s.indexOf("@@")) > 0) s = s.substring(0,j);
    while((i = (short)s.indexOf('[')) >= 0){
      s = s.substring(0, i) + s.substring(i + 1);
    }
    while((i = (short)s.indexOf(']')) >= 0){
      s = s.substring(0, i) + s.substring(i + 1);
    }
    while((i = (short)s.indexOf('/')) >= 0){
      s = s.substring(0, i) + s.substring(i + 1);
    }
    while((i = (short)s.indexOf(u.phonicsplit)) >= 0){
      s = s.substring(0, i) + s.substring(i + 1);
    }
    while((i = (short)s.indexOf('_')) >= 0){
      s = s.substring(0, i) + s.substring(i + 1);
    }
    return s;
  }
  public String vpic() {  //   for picture - includes @ + letters after it - does NOT stop before @@
    if(picture != null) return picture;
    short i,j;
    String s = new String(oldvalue);
    String keepsuffix = "";
//    if(oldvalue.endsWith("@@w"))keepsuffix = "@@w";
    int k;
    if((k=oldvalue.indexOf("@@"))>=0){
        keepsuffix = oldvalue.substring(k);
    }
    else if((k=oldvalue.indexOf("@"))>=0){
         keepsuffix = oldvalue.substring(k);
    }
    if((i=(short)s.indexOf('='))>0) {
      s = s.substring(0, i);
    }
    if((i=(short)s.indexOf('!'))>0) {
      s = s.substring(0, i);
    }
    while((i = (short)s.indexOf('[')) >= 0){
      s = s.substring(0, i) + s.substring(i + 1);
    }
    while((i = (short)s.indexOf(']')) >= 0){
      s = s.substring(0, i) + s.substring(i + 1);
    }
    while((i = (short)s.indexOf('/')) >= 0){
      s = s.substring(0, i) + s.substring(i + 1);
    }
    while((i = (short)s.indexOf('_')) >= 0){
      s = s.substring(0, i) + s.substring(i + 1);
    }
    while((i = (short)s.indexOf(u.phonicsplit)) >= 0){
      s = s.substring(0, i) + s.substring(i + 1);
    }
    if(!s.endsWith(keepsuffix)){
        s = s + keepsuffix;
    }
    return wordlist.usephonics && phonics && !phonicsw && value.indexOf('!')<0 ? s+'~' : s;
  }
  public String vsplit() {  //   return split (/ only) string
    short i;
    String s = ((i=(short)value.indexOf('@'))>0) ? value.substring(0,i):new String(value);
    if((i=(short)s.indexOf('='))>0) s = s.substring(0,i);
    if((i=(short)s.indexOf('!'))>0) s = s.substring(0,i);
    while((i = (short)s.indexOf('[')) >= 0) s = s.substring(0,i)+s.substring(i+1);
    while((i = (short)s.indexOf(']')) >= 0) s = s.substring(0,i)+s.substring(i+1);
    while((i = (short)s.indexOf(u.phonicsplit)) >= 0) s = s.substring(0,i)+s.substring(i+1);
    return s;
  }
  public String phsplit() {  //   return split (phonicsplit only) string
    short i;
    String s = ((i=(short)value.indexOf('@'))>0) ? value.substring(0,i):new String(value);
    if((i=(short)s.indexOf('='))>0) s = s.substring(0,i);
    if((i=(short)s.indexOf('!'))>0) s = s.substring(0,i);
    while((i = (short)s.indexOf('[')) >= 0) s = s.substring(0,i)+s.substring(i+1);
    while((i = (short)s.indexOf(']')) >= 0) s = s.substring(0,i)+s.substring(i+1);
    while((i = (short)s.indexOf('/')) >= 0) s = s.substring(0,i)+s.substring(i+1);
    return s;
  }
  public String vsplit2() {  //   return split (/ only) string
    short i;
    String s = ((i=(short)value.indexOf('@'))>0) ? value.substring(0,i):new String(value);
    if((i=(short)s.indexOf('='))>0) s = s.substring(0,i);
    if((i=(short)s.indexOf('!'))>0) s = s.substring(0,i);
    while((i = (short)s.indexOf('[')) >= 0) s = s.substring(0,i)+s.substring(i+1);
    while((i = (short)s.indexOf(']')) >= 0) s = s.substring(0,i)+s.substring(i+1);
    while((i = (short)s.indexOf(u.phonicsplit)) >= 0) s = s.substring(0,i)+s.substring(i+1);
    return u.combineString(u.splitString(s,'/')," ");
  }
  public String phsplit2() {  //   return split (/ only) string
    short i;
    String s = ((i=(short)value.indexOf('@'))>0) ? value.substring(0,i):new String(value);
    if((i=(short)s.indexOf('='))>0) s = s.substring(0,i);
    if((i=(short)s.indexOf('!'))>0) s = s.substring(0,i);
    while((i = (short)s.indexOf('[')) >= 0) s = s.substring(0,i)+s.substring(i+1);
    while((i = (short)s.indexOf(']')) >= 0) s = s.substring(0,i)+s.substring(i+1);
    while((i = (short)s.indexOf('/')) >= 0) s = s.substring(0,i)+s.substring(i+1);
    return u.combineString(u.splitString(s,u.phonicsplit)," ");
  }
  public boolean defsay() {
     long t = System.currentTimeMillis();
     spokenWord.extrainf = null;
     if(spokenWord.findandsaydef(vsay())) {
        lastsay = 0;
        return true;
     }
     if(say()) {
        lastsay = lastsaythis = spokenword.endsay;
        return true;
     }
     return false;
  }

  public boolean sayandextra() {
     spokenWord.extrainf = null;
     boolean dobeep = true;
     if(spokenword == null) {
         spokenWord.getSpokenWord(this);
         if(spokenword == null){
             dobeep = false;
             spokenWord.getSpokenWord(this, true);
         }
     }
     if(spokenword != null && spokenword.data!=null) {
         if(dobeep && !spokenword.extraadded)
            spokenword.addextra(vsay(), dbprivate!=null);
        spokenword.say();
        lastsay = 0;
        return true;
     }
     return false;
  }

  public boolean say() {
     spokenWord.extrainf = null;
     if(spokenword == null) {
       if (phonics && wordlist.usephonics) {
         if(phonicsw){
             spokenword = spokenWord.getPhonicsWhole(this);
             if(homophone){
                 spokenword.addhomo(vsay());
             }
         }
         else {
           if (sharkStartFrame.currPlayTopic != null)
             spokenWord.sayPhonicsWord(this, 500, false, false, !sharkStartFrame.currPlayTopic.singlesound);
           else  spokenWord.sayPhonicsWord(this, 500, false, false, false);
         }
       }
       else
           spokenWord.getSpokenWord(this);
     }
     if(spokenword != null) {
        spokenword.say();
        lastsay = 0;
        return true;
     }
     return false;
  }
  public boolean ismagicsyl(int syl) {
    if(phonicsmagic != null) {
      int i;
      for (i = 0; i < phonicsmagic.length; ++i) {
        if (syl + i == phonicsmagic[i])     return true;
      }
    }
    return false;
  }
  public boolean samephonics(word w) {
    int p1=value.indexOf('=');
    int p2=w.value.indexOf('=');
    return p1>0 && p2>0 && value.substring(p1+1).equals(w.value.substring(p2+1));
  }
  public int fullval(int which) {   // returns actual phonics position from one with magic e phonics removed
    int i,j;
    String s[];
    if ((i = value.indexOf('=')) > 0) {
        if(onephoneme)  return which;
        else {
          s = u.splitString(value.substring(i + 1), u.phonicsplit);
          for (i=j=0; i<s.length && j<which; ++i) {
             if(s[i].length() > 0) ++j;
          }
          if(i<s.length && s[i].length()==0) ++i;
          return i;
        }
    }
    else return which;
  }
   //-------------- paint with options

  public boolean paint(Graphics g, Rectangle r, int options) {
//startPR2004-09-15^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    String ss;
    char s[] = (ss = (tempsplit == null)?value:tempsplit).toCharArray();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    Color  saveColor = g.getColor();
    FontMetrics m = g.getFontMetrics();
    int i,j,k;
    int x = r.x;
//startPR2007-11-26^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR

    // for the Sassoon Primary Infant font particularly the descent of some
    // characters eg 'j' were overlapping the oval that lights up when the sound
    // is read.

//    int y = Math.max(0,r.y + r.height/2 - m.getDescent()/2)  + m.getAscent()/2;
    int y = (wordlist.current.usephonics&&value.indexOf(u.phonicsplits)>=0)?r.y + r.height/2:Math.max(0,r.y + r.height/2 - m.getDescent()/2)  + m.getAscent()/2;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    boolean split = ((options &  SPLIT) != 0);
    boolean phonicsplit = ((options &  PHONICSPLIT) != 0);
    boolean highlight =   ((options & HIGHLIGHT) != 0);
    boolean wantvowels =  ((options & VOWELS) != 0);
    boolean shape =  ((options & SHAPE) != 0);
    boolean high = false, ishigh = false;
    int phgap = m.getHeight()/4;
    int ctot=0,lastpsplit=0,lastpsplitx;
    int slen = s.length;
//startPR2004-09-15^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    boolean isSpecialVowel[]= new boolean[ss.length()];
    if(wantvowels)
      isSpecialVowel = u.findSpecialVowels(ss);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    boolean specialhigh[] = new boolean[slen];
//    if(wantvowels) for(i=0;i<specialvowels.length;++i) {
//      j = 0;
//      while((j = ss.indexOf(specialvowels[i],j)) >= 0) {
//        for(k=0;k<specialvowels[i].length();++k) specialhigh[j+k]=true;
//        ++j;
//      }
//    }
    int phno = 0 ,lastphsylsplit;
    int x1=x,x2;
    if((options & CENTRE) != 0) {
      x += r.width / 2 - m.stringWidth(phonicsplit ? phsplit() : (split ? vsplit2() : v())) / 2;
      if(phonicsplitlist != null) x -= (phonicsplitlist.length-1)*m.charWidth('/')/2;
    }
    lastpsplitx = lastphsylsplit = x;
    if(shape) {
        drawshape(g,x,y);
    }
    else  for(i=0;i<slen;++i)  {
        if( s[i] == '@') break;
        if( s[i] == '!') break;
        if( s[i] == '=') break;
        if( s[i] == '~') break;
        if( s[i] == '/') {
          if(split && i<slen-1 && s[i+1] == '/') {
             g.setColor(Color.magenta);
             g.drawChars(s,i,1,x,y);
             g.setColor(saveColor);
             x += m.charWidth(s[i]);
             ++i;
             continue;
          }
          if(!split) continue;
       }
       if(s[i] == u.phonicsplit) {
         ++phno;
         if (!phonicsplit)     continue;
         if(!sayingextra && !phonicsilent[phno-1] && (curr2 < 0 || phno-1 <= curr2)) {
           if(curr2 >=0 && (sw2==null || curr2 < sw2.length-1)) g.setColor(Color.red);
           int midx = (x + lastpsplitx) / 2;
           int midy = y + m.getMaxDescent() + phgap / 2 + 1;
           if (ctot - lastpsplit == 1) {
             int rad = phgap / 2;
             ( (Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//startPR2007-11-26^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//           g.fillOval(midx - rad, midy - rad, rad * 2, rad * 2);
              g.fillOval(midx - rad, midy, rad * 2, rad * 2);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
             ( (Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
           }
           else {
             int ww = (x - lastpsplitx);
             int hh = Math.max(2, phgap / 2);
//startPR2007-11-26^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//             g.fillRect(midx - ww / 2, midy - hh / 2, ww, hh);
               g.fillRect(midx - ww / 2, midy+hh/2, ww, hh);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
           }
           g.setColor(saveColor);
        }
         if(phonicsplitlist != null && phonicsplitlist.length>0 && u.inlist(phonicsplitlist,phno)
           && phno != phonicsplitlist[phonicsplitlist.length-1]) {
          if(sayingextra && phno==curr2+1) {
            int midx = (x + lastphsylsplit) / 2;
            int midy = y + m.getMaxDescent() + phgap / 2 + 1;
            int ww = (x - lastphsylsplit);
            int hh = Math.max(2, phgap / 2);
            g.setColor(Color.red);
            g.fillRect(midx - ww / 2, midy - hh / 2, ww, hh);
            g.setColor(saveColor);
          }
          x += phgap;
          g.drawString("/",x, y);
          x+=m.charWidth('/');
          lastphsylsplit = x+phgap;
         }
        x += phgap;
        lastpsplitx = x;
        lastpsplit = ctot;
        continue;
       }
       if(s[i] == '_') continue;
       if(s[i] == '[') {
          if (highlight) high = true;
          continue;
       }
       if(s[i] == ']') {
          high = false;
          continue;
       }
//startPR2004-09-15^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//       if(high || wantvowels && (u.vowels.indexOf(s[i])>=0 || i>0 && s[i] == 'y')) {
       if(high || wantvowels && (u.vowels.indexOf(s[i])>=0 || i>0 && s[i] == 'y'
          || isSpecialVowel[i])) {
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//       if(high || wantvowels && (u.vowels.indexOf(s[i])>=0 || i>0 && s[i] == 'y'
//          || specialhigh[i])) {
          g.setColor(Color.red);
          ishigh = true;
        }
        else if(ishigh) {
          g.setColor(saveColor);
          ishigh = false;
        }
        if((options & ADDMAGIC) == 0         // m iss char if incomplete magic-e drawing (ie last sound not in yet)
           || s[i+3] != '=') {
          g.drawChars(s, i, 1, x, y);
          ++ctot;
        }
        if(phonicsmagic != null && !shape && s[i] != u.phonicsplit && wordlist.usephonics) {
          for(int mpos=0;mpos<phonicsmagic.length;++mpos) {
            if(phno == phonicsmagic[mpos]-2) x1  = x + m.charWidth(s[i])/2;
            else if(phno == phonicsmagic[mpos]) {
              x2 = x + m.charWidth(s[i])/2;
              g.setColor(Color.magenta);
              int y1 = y - m.getAscent() - 3;
              int y2 = y - m.getAscent() * 5 / 6;
              g.drawPolyline(new int[] {x1, x1, x2, x2}, new int[] {y2, y1, y1, y2}, 4);
              int miny = Math.max(y1 + 1, 0);
              g.drawPolyline(new int[] {x1 + 1, x1 + 1, x2 - 1, x2 - 1}, new int[] {y2, miny, miny, y2}, 4);
              g.setColor(saveColor);
            }
          }
        }
        x += m.charWidth(s[i]);
     }
     if(!sayingextra && phonicsplit && phonicsilent != null) {
       if(!phonicsilent[phno] && (curr2<0 || phno <= curr2)) {
         if(curr2 >= 0 && (sw2==null || curr2 < sw2.length-1)) g.setColor(Color.red);
         int midx = (x + lastpsplitx) / 2;
         int midy = y + m.getMaxDescent() + phgap / 2 + 1;
         if (ctot - lastpsplit == 1) {
           int rad = phgap / 2;
           ( (Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//startPR2007-11-26^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//           g.fillOval(midx - rad, midy - rad, rad * 2, rad * 2);
             g.fillOval(midx - rad, midy, rad * 2, rad * 2);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
           ( (Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
         }
         else {
           int ww = (x - lastpsplitx);
           int hh = Math.max(2, phgap / 2);
//startPR2007-11-26^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//           g.fillRect(midx - ww / 2, midy - hh / 2, ww, hh);
             g.fillRect(midx - ww / 2, midy+hh/2, ww, hh);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         }
         g.setColor(saveColor);
       }
     }
     if(sayingextra && phonicsplit && phonicsplitlist != null && phonicsplitlist.length>0 && phonicsplitlist[phonicsplitlist.length-1] == curr2+1) {
        int midx = (x + lastphsylsplit) / 2;
        int midy = y + m.getMaxDescent() + phgap / 2 + 1;
        int ww = (x - lastphsylsplit);
        int hh = Math.max(2, phgap / 2);
        g.setColor(Color.red);
        g.fillRect(midx - ww / 2, midy - hh / 2, ww, hh);
        g.setColor(saveColor);
      }
     if(phonics && (options & PHONICSPLIT) != 0 && (options & CENTRE) == 0) {
       if(x < r.x+r.width*7/16 && m.getHeight() < r.height * 2/3) {
         g.setColor(saveColor);
         paint(g, new Rectangle(r.x + r.width / 2 + m.charWidth('.'),
                                r.y, r.width / 2, r.height), (byte) (options & ~PHONICSPLIT));
       }
       else return false;
     }
     g.setColor(saveColor);
     return true;
  }
   //-------------- split word at mouse

  public boolean buildsplit(int x1, int mousex, FontMetrics m) {
    char s[] = value.toCharArray();
    int slashwidth = m.charWidth('/');
    int i;
    int x = x1;
    for(i=0;i<s.length;++i)  {
       if( s[i] == '@') break;
       if(s[i]=='=') break;
       if( s[i] == '_') continue;
       if(s[i] == ']' || s[i] == '[')   continue;
       if(x>x1 && mousex < x + slashwidth) {
//startPR2008-08-05^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//         String news = value.substring(0,i) + '/' + value.substring(i);
         int p = i;
         if(value.substring(0,i).endsWith(u.phonicsplits))p--;
         String news = value.substring(0,p) + '/' + value.substring(p);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          if(tempsplit == null || !news.equals(tempsplit)) {
              tempsplit = news;
              return true;
          }
          else return false;
       }
       x += m.charWidth(s[i]);
     }
     if(tempsplit != null) {
        tempsplit = null;
        return true;
     }
     return false;
  }
  //------------------------------------------------------------
   public static int getMidline(Graphics g1) {
      FontMetrics m = g1.getFontMetrics();
      int w=m.charWidth('n'), h  = m.getHeight(), baseline = (h - m.getDescent() + m.getAscent())/2;
      Image im = sharkStartFrame.mainFrame.createImage(w,h);
      Graphics g = im.getGraphics();
      int p[] = new int[w*h], i,j,black = Color.black.getRGB();
      PixelGrabber pg = new PixelGrabber(im,0,0,w,h,p,0,w);

      g.setFont(g1.getFont());
      g.setColor(Color.white);
      g.fillRect(0,0,w,h);
      g.setColor(Color.black);
      g.drawString("n",0,baseline);
      try{pg.grabPixels();}
      catch(InterruptedException e) {}
      for(i=0; i < baseline; ++i) {
        for(j=0; j < w; ++j) {
            if(p[i*w+j] == black) return i;
        }
      }
      return i;
   }
  //---------------------------------------------------------------
  public int begintop(Graphics g1, String s) {
      FontMetrics m = g1.getFontMetrics();
      int w = m.stringWidth(s), h  = m.getHeight(), baseline = (h - m.getDescent() + m.getAscent())/2;
      int midline = getMidline(g1);
      Image im = sharkStartFrame.mainFrame.createImage(w,h);
      Graphics g = im.getGraphics();
      int p[] = new int[w*h], i,j,black = Color.black.getRGB();
      PixelGrabber pg = new PixelGrabber(im,0,0,w,midline,p,0,w);
      int left = w;

      g.setFont(g1.getFont());
      g.setColor(Color.white);
      g.fillRect(0,0,w,h);
      g.setColor(Color.black);
      g.drawString(s,0,baseline);
      try{pg.grabPixels();}
      catch(InterruptedException e) {}
      for(i=0; i < midline; ++i) {
        for(j=0; j < w; ++j) {
            if(p[i*w+j] == black) {left = Math.min(left,j); break;}
        }
      }
      return left;
   }
  //---------------------------------------------------------------
  public int beginbottom(Graphics g1, String s) {
      FontMetrics m = g1.getFontMetrics();
      int w = m.stringWidth(s), h  = m.getHeight(), baseline = (h - m.getDescent() + m.getAscent())/2;
      Image im = sharkStartFrame.mainFrame.createImage(w,h);
      Graphics g = im.getGraphics();
      int p[] = new int[w*h], i,j,black = Color.black.getRGB();
      PixelGrabber pg = new PixelGrabber(im,0,baseline+1,w,h-baseline-1,p,0,w);
      int left = w;

      g.setFont(g1.getFont());
      g.setColor(Color.white);
      g.fillRect(0,0,w,h);
      g.setColor(Color.black);
      g.drawString(s,0,baseline);
      try{pg.grabPixels();}
      catch(InterruptedException e) {}
      for(i=0; i < h-baseline-1;++i) {
        for(j=0; j < w; ++j) {
            if(p[i*w+j] == black) {left = Math.min(left,j); break;}
        }
      }
      return left;
   }
  //---------------------------------------------------------------
  public int freetop(Graphics g1, String s) {
      FontMetrics m = g1.getFontMetrics();
      int w = m.stringWidth(s), h  = m.getHeight(), baseline = (h - m.getDescent() + m.getAscent())/2;
      int midline = getMidline(g1);
      Image im = sharkStartFrame.mainFrame.createImage(w,h);
      Graphics g = im.getGraphics();
      int p[] = new int[w*h], i,j,black = Color.black.getRGB();
      PixelGrabber pg = new PixelGrabber(im,0,0,w,midline,p,0,w);
      int left = w;

      g.setFont(g1.getFont());
      g.setColor(Color.white);
      g.fillRect(0,0,w,h);
      g.setColor(Color.black);
      g.drawString(s,0,baseline);
      try{pg.grabPixels();}
      catch(InterruptedException e) {}
      for(i=0; i < midline; ++i) {
        for(j=0; j < w; ++j) {
            if(p[i*w+j] == black) {return i;}
        }
      }
      return i;
   }
  //---------------------------------------------------------------
  public int freebottom(Graphics g1, String s) {
      FontMetrics m = g1.getFontMetrics();
      int w = m.stringWidth(s), h  = m.getHeight(), baseline = (h - m.getDescent() + m.getAscent())/2;
      Image im = sharkStartFrame.mainFrame.createImage(w,h);
      Graphics g = im.getGraphics();
      int p[] = new int[w*h], i,j,black = Color.black.getRGB();
      PixelGrabber pg = new PixelGrabber(im,0,baseline+1,w,h-baseline-1,p,0,w);
      int left = w;

      g.setFont(g1.getFont());
      g.setColor(Color.white);
      g.fillRect(0,0,w,h);
      g.setColor(Color.black);
      g.drawString(s,0,baseline);
      try{pg.grabPixels();}
      catch(InterruptedException e) {}
      for(i=0; i < h-baseline-1;++i) {
        for(j=0; j < w; ++j) {
            if(p[(h-baseline-2-i)*w+j] == black) {return i;}
        }
      }
      return i;
   }
    //---------------------------------------------------------------
  static int getleft(int left,int right,int w) {
     int right1 = Math.min(w-1, (left+right+w/2)/2);
     return Math.max(1, right1 - w/2);
  }
  //---------------------------------------------------------------
  static int getright(int left,int right,int w) {
     int left1 = Math.max(1, (left+right-w/2)/2);
     return Math.min(w-1, left1 + w/2);
  }
  //------------------------------------------------------------
  public static boolean[][] getpix(String s,Font f,FontMetrics m,int baseline) {
      int w=m.stringWidth(s), h  = m.getHeight();
      boolean ret[][] = new boolean[w][h];
      Image im = sharkStartFrame.mainFrame.createImage(w,h);
      Graphics g = im.getGraphics();
      int p[] = new int[w*h], i, j, k;
      PixelGrabber pg = new PixelGrabber(im,0,0,w,h,p,0,w);

      g.setFont(f);
      g.setColor(Color.white);
      g.fillRect(0,0,w,h);
      g.setColor(Color.black);
      g.drawString(s,0,baseline);
      try{pg.grabPixels();}
      catch(InterruptedException e) {}
      for(i=0; i < h; ++i) {
         for(j=0; j < w; ++j) {
            ret[j][i] = ((p[i*w+j] & 0x00ffffff) == 0);
         }
      }
      return ret;
  }
  //------------------------------------------------------------
  static void makeshape(Graphics g1, char c,int midline, int left[], int right[]) {
      boolean capital = (u.lowercase.indexOf(c) < 0);
      FontMetrics m = g1.getFontMetrics();
      int w=m.charWidth(c), h  = m.getHeight(), baseline = (h - m.getDescent()+m.getAscent())/2;
      Image im = sharkStartFrame.mainFrame.createImage(w,h);
      Graphics g = im.getGraphics();
      int p[] = new int[w*h], i, j, k;
      int leftx,rightx,rightest = 0;
      PixelGrabber pg = new PixelGrabber(im,0,0,w,h,p,0,w);
      boolean topheavy = false;

      g.setFont(g1.getFont());
      g.setColor(Color.white);
      g.fillRect(0,0,w,h);
      g.setColor(Color.black);
      g.drawChars(new char[] {c},0,1,0,baseline);
      try{pg.grabPixels();}
      catch(InterruptedException e) {}
      for(i=0; i < h; ++i) {
         left[i] = -1; right[i] = -1;
         for(j=0; j < w; ++j) {
            if((p[i*w+j] & 0x00ffffff) == 0) {
               if(left[i] < 0) left[i] = j;
               right[i] = j+1;
               rightest = Math.max(rightest,j+1);
            }
         }
      }
      w = rightest;
      if(capital) {
         int leftest = rightest;
         for(i=0; i < h; ++i) {if(left[i]>=0) leftest = Math.min(leftest,left[i]);}
         for(i=0; i < h; ++i) {
            if(left[i]>=0) {left[i]=leftest;right[i]=rightest;}
         }
         return;
      }
                    // below baseline
      for(i=h-1; i >=baseline; --i) if(left[i] >=0) break;
      if(i>baseline) {
         for(j=i; j >=baseline; --j)  {
            if(right[j] - left[j] > w/2) {
               if(right[i] - left[i] < w-2) {
                  for(k=i;k>j;--k) {
                     left[k] = 1;
                     right[k] = w-1;
                  }
               }
               left[j] = 1;
               right[j] = w-1;
            }
            else {
               left[j] = getleft(left[j],right[j],w);
               right[j] = getright(left[j],right[j],w);
            }
         }
      }
             // from top down
      for(i=0; i < midline; ++i) if(left[i] >=0) break;
      if(i<midline) {
         leftx = w; rightx = 0;
         for(j=i; j < midline; ++j) {
            leftx = Math.min(leftx, left[j]);
            rightx = Math.max(rightx, right[j]);
            if(left[j] < 0) {     // dot or accent
                for(k=i;k<j;++k) {
                  if(rightx > w/2) right[k] = w-1;
                  else             right[k] = w/2;
                  if(leftx < w/2) left[k] = 1;
                  else            left[k] =w/2;
                }
                leftx = w; rightx = 0;
                topheavy = false;
                while(++j < midline && left[j] < 0);
                --j;
                break;
            }
            else  {       // stem
               if(rightx-leftx >= w/2) {
                  if(j < midline-2)   {
                     topheavy = true;
                  }
               }
            }
         }
         if(leftx<w) {
           for(k=i;k<midline;++k) {
              if(topheavy || c == 'l') {
                  left[k]= 1;
                  right[k] = w-1;
              }
              else if(rightx+leftx > w+2){
                 right[k] = w-1;
                 left[k] = w/2;
              }
              else {
                 right[k] = w/2;
                 left[k] = 1;
              }
           }
         }
      }
      if(topheavy) {
         for(i=midline;i<baseline;++i) {
            if(right[i] - left[i] >= w/2) {
               right[i] = w-1;
               left[i] = 1;
            }
            else {
               if(right[i]+left[i] < w+2) {
                   right[i] = w/2;
                   left[i] = 1;
               }
               else {
                  right[i] = getright(left[i],right[i],w);
                  left[i] = getleft(left[i],right[i],w);
               }
            }
         }
      }
      else {
         rightx = 0;
         leftx = w;
         for(i=midline;i<baseline;++i) {
            rightx = Math.max(rightx, right[i]);
            leftx =  Math.min(leftx,left[i]);
         }
         rightx = Math.min(w-1,rightx);
         if(leftx<w/2) leftx = 1;
         if(leftx>w/2) leftx = w/2;
         for(i=midline;i<baseline;++i) {
            right[i] = rightx;
            left[i] = leftx;
         }
      }
  }
  //------------------------------------------------------------
  public void drawshape(Graphics g1, int x1, int y) {
     FontMetrics m = g1.getFontMetrics();
     int h = m.getHeight();
     int baseline = m.getAscent();
     int i,j, x=x1;
     int left[]= new int[h],right[]= new int[h];
     int midline = getMidline(g1);
     char c[] = v().toCharArray();
     for(i=0;i<c.length;++i) {
        makeshape(g1,c[i],midline,left,right);
        for(j=0;j<h;++j) {
           if(left[j]>=0) g1.drawLine(x+left[j],y-baseline+j-3,x+right[j],y-baseline+j-3);
        }
        x += m.charWidth(c[i]);
     }
  }
  //------------------------------------------------------------
  public static void drawwordshape(Graphics g1, String v, int x1, int y) {
     FontMetrics m = g1.getFontMetrics();
     int h = m.getHeight();
     int baseline = m.getAscent();
     int i,j, x=x1;
     int left[]= new int[h],right[]= new int[h];
     int midline = getMidline(g1);
     char c[] = v.toCharArray();
     for(i=0;i<c.length;++i) {
        makeshape(g1,c[i],midline,left,right);
        for(j=0;j<h;++j) {
           if(left[j]>=0) g1.drawLine(x+left[j],y-baseline+j-3,x+right[j],y-baseline+j-3);
        }
        x += m.charWidth(c[i]);
     }
  }
  //------------------------------------------------------------
  public static void drawshape(Graphics g1, char c, int x1, int y) {
     FontMetrics m = g1.getFontMetrics();
     int h = m.getHeight();
     int baseline = m.getAscent();
     int i,j, x=x1;
     int left[]= new int[h],right[]= new int[h];
     int midline = getMidline(g1);
     makeshape(g1,c,midline,left,right);
     for(j=0;j<h;++j) {
        if(left[j]>=0) g1.drawLine(x+left[j],y-baseline+j-3,x+right[j],y-baseline+j-3);
     }
  }
  //------------------------------------------------------------
  public boolean sameshape(Graphics g1,word w) {
     FontMetrics m = g1.getFontMetrics();
     int h = m.getHeight();
     String s1 = v(), s2 = w.v();
     if(s1.equals(s2)) return true;
     if(s1.length() != s2.length()) return false;

     int i,j;
     int left1[]= new int[h],right1[]= new int[h],
          left2[]= new int[h], right2[]= new int[h];
     int midline = getMidline(g1);

     for(i=0;i<s1.length();++i) {
        makeshape(g1,s1.charAt(i),midline,left1,right1);
        makeshape(g1,s2.charAt(i),midline,left2,right2);
        for(j=0; j<h; ++j) {
           if(Math.abs(left1[j] - left2[j]) > 1 || Math.abs(right1[j] - right2[j]) > 2) return false;
        }
     }
     return true;
  }
  /**
   * @param list The words to be compared.
   * @return The length of the biggest word in the list passed.
   */
  public static short maxlen(word[] list) {
     short len = 0 ;
     for(short i=0;i<list.length;++i) {
        len = (short)Math.max(len,list[i].v().length());
     }
     return len;
  }
  //-------------------------------------------------------------
  public short segtot() {
     short ret=1;
     int i;
     String s = value;
     while((i = s.indexOf('/')) > 0) {
       ++ret;
       s = s.substring(i+1);
     }
     return ret;
  }
  //-------------------------------------------------------------
  public short phsegtot() {
     short ret=1;
     int i;
     String s = value;
     while((i = s.indexOf(u.phonicsplit)) > 0) {
       ++ret;
       s = s.substring(i+1);
     }
     return phonicsmagic != null?(short)(ret-phonicsmagic.length):ret;
  }
  //-------------------------------------------------------------------------
  public boolean nohomo() {
    if(nohomo) return true;
    if(database != null && db.query(database,vsay()+"=",db.WAV) < 0) {nohomo = true;return true;}
    return false;
  }
  //----------------------------------------------------------------------------
  public int[] isphonicsmagic() {  // returns seg numbers of magic e.
    int i;
    int ret[] = null;
    String vv = u.strip(value,'/');
    i = vv.indexOf('=');
    String[] seg = u.splitString(vv.substring(0,i),u.phonicsplit);
    String[] sounds = u.splitString(vv.substring(i+1),u.phonicsplit);
    for(i=0;i<seg.length;++i) {
       if(seg[i].equals("e") && (i>=sounds.length || sounds[i].length()==0)) ret = u.addint(ret,i);
    }
    return ret;
  }
  //----------------------------------------------------------------------------
  public boolean[] isphonicsilent() {  // checks if any silent phoneme - sound given as '-' or magic e as empty
    int i;
    String vv = u.strip(value,'/');
    i = vv.indexOf('=');
    String[] seg = u.splitString(vv.substring(0,i),u.phonicsplit);
    String[] sounds = u.splitString(vv.substring(i+1),u.phonicsplit);
    boolean ret[] = new boolean[seg.length];
    for(i=0;i<seg.length;++i) {
       if(i>=sounds.length || sounds[i].equals("-") || sounds[i].length()==0)  ret[i] = true;
    }
    return ret;
  }
  //----------------------------------------------------------------------------
  public String[] phonics() {
    int i;
    if ((i = value.indexOf('=')) > 0) {
        if(onephoneme) {
          if (value.endsWith(u.phonicsplits))
              return new String[] {value.substring(i+1, value.length()-1)};
          else return new String[] {value.substring(i + 1)};

        }
        else {

          String wvalue = value.substring(i + 1);
          int p;
          if((p = wvalue.indexOf('@'))>0)wvalue = wvalue.substring(0, p);

          String ret[] = u.splitStringi(wvalue, u.phonicsplit);
          for (i=0;i<ret.length;++i) {
             if(ret[i].startsWith("/")) ret[i] = ret[i].substring(1);
          }
          return ret;
        }
    }
    else return new String[0];
   }
    //----------------------------------------------------------------------------
    public String[] phonicsall() {   // includes empty ones and leave phonics split markers /
      int i;
      if ((i = value.indexOf('=')) > 0) {
          if(onephoneme)  return u.splitString(value.substring(i+1,value.length()-1),u.phonicsplit);
          else return u.splitString(value.substring(i+1),u.phonicsplit);
      }
      else return new String[0];
  }
  //----------------------------------------------------------------------------
  public String[] segments() {   // individual letters
       int i;
       String s = v();
       String ret[] = new String[s.length()];
       for(i=0;i<s.length();++i) ret[i] = s.substring(i,i+1);
       return ret;
   }
   //-------------------------------------------------------------------
    public String[] phaddsegs(String s1[], boolean nodups) {
       int i;
       String s[] = u.splitString(phsplit(),u.phonicsplit);
       if(phonicsmagic != null) {
         for(i=0;i<phonicsmagic.length;++i) {
           s = u.removeString(s, phonicsmagic[i]-i);
           s[phonicsmagic[i]-2-i] += "-e";
         }
       }
       for(i=0;i<s.length;++i) if(s[i].startsWith("/")) s[i] = s[i].substring(1);
       for(i=0;i<s.length;++i) {
          s1 = nodups?u.addStringSortCaps(s1, s[i]):u.addString(s1, s[i]);
        }
       return  s1;
    }
    //-------------------------------------------------------------------
     public String[] phsegs() {
        int i;
        String s[] = u.splitString(phsplit(),u.phonicsplit);
        if(phonicsmagic != null) {
          for(i=0;i<phonicsmagic.length;++i) {
            s = u.removeString(s, phonicsmagic[i]-i);
            s[phonicsmagic[i]-2-i] += "-e";
          }
        }
        return  s;
     }
     //----------------------------------------------------------------------------
     public int[] phsplitlist() {  // return end phonics positions of phonics segments
       int i, ret[] = new int[0];
       String s[] = phonicsall();
       for(i=0;i<s.length;++i) if(s[i].startsWith("/")) ret = u.addint(ret,i);
       if(ret.length>0) ret = u.addint(ret,s.length);
       return ret;
     }
     //--------------------------------------------------------------------------
        // get part of multi-syllable phonics word for speaking
     public String phpart(int phpos) {
        String s1[] = phonics();
        String s = "";
        if(s1.length==0)return s;
        int sp[] = phonicsplitlist;
        int i;
        for(i=0;i<sp.length;++i) if(phpos==sp[i]) break;
        if(i==sp.length-1) phpos = s1.length;
        boolean first=true;
        for(i=(i==0?0:sp[i-1]); i < phpos;++i) {
            if(!first)  s += u.phonicsplits;
            first = false;
            s += s1[i];
        }
         return s;
     }
     //--------------------------------------------------------------------------
     void sayandmark(spokenWord sw[], Component jc) {
      int totlen = 0,i;
      sw2 = sw;
      curr2 = -1;
      repaint = jc;
      new sayandmark(0);
     }
     class sayandmark extends spokenWord.whenfree {
        sayandmark(int after) {
           super(after);
        }
        public void action() {
          do {
             ++curr2;
             if(!sayingextra && u.inlist(phonicsplitlist, curr2) && phpart(curr2).indexOf(u.phonicsplit) >=0) {
               sayingextra = true;
               repaint.repaint();
               spokenWord sw;
               if(!spokenWord.findandsay1(phpart(curr2)+"~~")
                    && !spokenWord.findandsay1(phpart(curr2)+"~")) spokenWord.findandsay1(phpart(curr2));
               --curr2;
               new sayandmark(300);
               return;
            }
            sayingextra = false;
          } while(curr2 < sw2.length && sw2[curr2] == null);
          if(curr2 < sw2.length) {
            repaint.repaint();
            sw2[curr2].say();
            new sayandmark(0);
          }
          else {
            curr2 =-1;
            repaint.repaint();
          }
        }
     }
}

