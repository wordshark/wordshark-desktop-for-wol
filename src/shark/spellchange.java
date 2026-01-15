package shark;

import java.util.*;

public class spellchange{
  static String name, from[], to[],rfrom[],rto[];
//startPR2007-02-16^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  // changing any instance of a letter of group of letters within a word.
  static String allfrom[], allto[];
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  static int tot;
  public static boolean started,active;

  public static String[] getlists() {
    String ss[] = (String[])db.find(sharkStartFrame.publicTextLib[0],"spellchange_",db.TEXT);
    if(ss==null) return null;
    String ret[] = new String[ss.length+1];
    ret[0] = u.gettext("spellchange","default");
    for(int i=0;i<ss.length;++i)  {
      ret[i+1] = ss[i].substring(0,ss[i].indexOf('='));
    }
    return ret;
  }

  public static void spellchangesetup() {
     int i, j;
     active=false;
     started=true;
     if(db.query(sharkStartFrame.optionsdb, "spellchange_", db.TEXT)<0) {
//        String ss[];
//        if(Locale.getDefault().getCountry().toString() == "US"
//           && (ss=getlists()) != null && u.findString(ss,"North American")>=0 ) {
//          db.update(sharkStartFrame.optionsdb, "spellchange_",new String[]{"North American"},
//                                 db.TEXT);
//        }
//        else
         name = null;
         return;
     }
     name = ( (String[]) db.find(sharkStartFrame.optionsdb, "spellchange_",
                                 db.TEXT))[0];
     if (name != null) {
       String s[] = u.splitString(u.gettext("spellchange_", name));
       if (s == null) {
         from = to = null;
//startPR2007-02-16^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         allfrom = allto = null;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         active = false;
       }
       else {
         active=true;
         from = new String[tot=s.length];
         to = new String[tot];
//startPR2007-02-16^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         allfrom = allto = new String[]{};
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         for (i = 0; i < tot; ++i) {
           j = s[i].indexOf('=');
           if (j > 0 && j < s[i].length() - 1) {
             from[i] = s[i].substring(0, j);
             to[i] = s[i].substring(j + 1);
           }
//startPR2007-02-16^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
           if(from[i].charAt(0)=='*' && from[i].charAt(from[i].length()-1) == '*'&&
              to[i].charAt(0)=='*' && to[i].charAt(to[i].length()-1)== '*'
              ){
             allfrom = u.addString(allfrom, from[i].substring(1, from[i].length()-1));
             allto = u.addString(allto, to[i].substring(1, to[i].length()-1));
           }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         }
       }
       int o[] = u.getorder(from);
       String newfrom[]=new String[tot], newto[] = new String[tot];
       for(i=0;i<tot;++i) {
          newfrom[i] = from[o[i]];
          newto[i] = to[o[i]];
       }
       from = newfrom;
       to = newto;
       o = u.getorder(to);
       rfrom=new String[tot];
       rto = new String[tot];
       for(i=0;i<tot;++i) {
          rfrom[i] = from[o[i]];
          rto[i] = to[o[i]];
       }
      }
   }
   public static String reverse(String s) {
     if(!active) return s;
     int i;
     i = Arrays.binarySearch(rto,s);
     if(i >=0)   return rfrom[i];
     return s;
   }
   public static String spellchange(String s) {
     if(!active) return s;
     int i,n;
     boolean changed=false;
     String s2[] = u.splitintowords(s);
     for(i=0;i<s2.length;++i) {
       n = Arrays.binarySearch(from,s2[i].toLowerCase());
       if(n>=0) {
          if(Character.isUpperCase(s2[i].charAt(0)))
             s2[i] = Character.toUpperCase(to[n].charAt(0)) + to[n].substring(1);
          else   s2[i] = to[n];
          changed = true;
       }
     }
     if(changed) {
       s = s2[0];
       for(i=1;i<s2.length;++i) s += s2[i];
     }
 //startPR2007-02-16^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     for(int j = 0; j < allfrom.length; j++){
          s= s.replaceAll(allfrom[j], allto[j]);
     }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     return s;
   }
   public static String[] spellchange(String s[]) {
     if(!active) return s;
     int i;
     String ss,ret[]=s;
     for (i = 0; i < s.length; ++i) {
       if((ss = spellchange(s[i])) != s[i]) {
         if(ret==s) {
           ret = new String[s.length];
           System.arraycopy(s,0,ret,0,s.length);
         }
         ret[i] = ss;
       }
     }
     return ret;
   }
   public static void spellchange(word ww) {
     if(!active) return;
     int i,j,k,m,n;
     String s = ww.v();
     i = Arrays.binarySearch(from,s);
     if(i >=0) {
       if(s.equals(from[i])) {
         String s2 = to[i];
         ww.value = "";
         int len = Math.max(ww.oldvalue.length(), s2.length());
         for (i = j = k = m = 0; i < len; ) {
           if (ww.oldvalue.length()>i && (j>=s.length() || ww.oldvalue.charAt(i) != s.charAt(j))) {
             ww.value += ww.oldvalue.charAt(i); // special character inserted
             ++i;
           }
           else if (s.length()>m && (k<s2.length() && ( s2.charAt(k) == s.charAt(m) ||
                                         s2.length() - k == s.length() - m))) {
             ww.value += s2.charAt(k); // replaced character
             ++i; ++j; ++m; ++k;
           }
           else if (s2.length()>k && (s2.length() - k > s.length() - m)) {
             ww.value += s2.charAt(k); // char inserted
             ++k;
           }
           else {
             ++m; ++i; ++j; // char removed
           }
         }
         return;
       }
//startPR2007-02-16^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       for(int h = 0; h < allfrom.length; h++){
         ww.value = s.replaceAll(allfrom[h], allto[h]);
         return;
       }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     }
   }
}
