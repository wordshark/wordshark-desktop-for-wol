  package shark.games;

import java.awt.*;

import shark.*;
import shark.sharkGame.*;
  import java.awt.event.*;

public class findsound extends sharkGame {
  short sharktot=3,curr,wordtot;
  face faces[] = new face[sharktot];
  mover chosenfish;
  int totsharks = 3;
  long wantnext,nextsay;
  int lastsay = -1, lastfacesay = -1;
  word words[];
  boolean saysplit;
  int currsyllable=0;
  String[] vowels=new String[0],cons = new String[0];
  showword showword;
  int totsyl, MAXSYL = 16;
  public static sharkImage ear = new sharkImage("listen2_",false);
  boolean listened;
  Font fok;
  FontMetrics fokm;
  int lastwo;
  String ok = rgame.getParm("ok");
  Font wfont,wfont3;
  FontMetrics wm,wm3;
  head heads[][];
  mid mids[][][];
  tail tails[][];
  tank tank;
  fmover flastsay, flastsay2;
  fokbutton fokb[] = new fokbutton[totsharks];
  boolean clickontank;
  hole holes[];
  int holetot;
  fmover wholes[][];
  long wantcombine;
  long stoptime;
  Color colors[] = new Color[] { Color.red,Color.green,Color.cyan,Color.magenta,Color.yellow, Color.yellow};
  int win;
  boolean hard,easy,noanimals;
  boolean flonly = rgame.getParm("flonly") != null;

  String ss[];
  long showfaces,restoreshowword;
//startPR2008-11-03^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  boolean mouthingheads;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  String imagetype;
  int lastCharacter = -1;
  boolean blockFaceSay = false;
javax.swing.Timer spokenFreeTimer;

  public findsound() {
    errors = true;
    gamescore1 = true;
    peeps = false;
    listen = phonicsw;
    peep = false;
    curr = 0;
    words = rgame.w;
    gamePanel.clearWholeScreen = true;
    gamePanel.dontstart = true;
    saysplit = rgame.getParm("saysplit") != null;
    forceSharedColor = true;
    wantspeed = saysplit;
    deaths = false;
    clickenddrag = false;
    if(student.optionstring("bgcolor")==null)
        gamePanel.setBackground(u.darkcolor());
    if(!saysplit && !flonly && phonicsw) {
      optionlist = new String[] {"findsound-hard","findsound-noanimals"};
      easy = options.optionval("findsound-hard") == 1;
      hard = options.optionval("findsound-hard") == 3;
      noanimals = options.option("findsound-noanimals");
      gamePanel.simpleoptions = true;
    }
    else {
      optionlist = new String[] {"findsound-fish", "findsound-robots", "findsound-faces", "findsound-hats"};
      groupedoptions = optionlist;
      setupCharacters();
    }
    buildTopPanel();
//startPR2008-11-03^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    setup();
//    if(!phonicsw && words.length<6) {
//      word newwords[] = new word[6];
//      for(int i=0;i<newwords.length;++i) newwords[i] = words[i%words.length];
//      words = u.shuffle(newwords);
//    }
//    if(flonly) help("help_findsound1fl");
//    else if(!phonicsw) help("help_findsound1");
//    else help(saysplit?(blended ?"help_findsounds2" :"help_findsounds")
//              :(blended ?"help_findsound2":"help_findsound" + (noanimals?"ear":"")));
//    if(saysplit) {
//      for(int i=0;i<words.length;++i) words[i].homophone = false;  // no homophones in blend sounds
//      setupSharks();
//    }
//    else if(flonly) {
//        setupSharks();
//    }
//    else {
//      int i,j;
//     for(i=0; i<words.length;++i) {
//       String ssw[] = words[i].phonics();
//       for(j=0;j<ssw.length;++j) {
//         if(ssw[j].equals("-")) {
//            String ph = "-" + words[i].phsegs()[j];
// //           if (u.findString(cons, ph) < 0)    cons = u.addString(cons, ph);
//         }
//         else if(u.vowels.indexOf(ssw[j].charAt(0))>=0) {
//           if(u.findString(vowels,ssw[j]) < 0) vowels = u.addString(vowels,ssw[j].toLowerCase());
//         }
//         else {
//           if(u.findString(cons,ssw[j]) < 0) cons = u.addString(cons,ssw[j].toLowerCase());
//         }
//       }
//     }
//     if(!phonicsw) showword = new showword();
//     if(phonicsw || flonly) {
//       setupWords();
//       setupNext();
//     }
//     else  setupNext2();
//    }
//    markoption();
//    gamePanel.dontstart = false;
//    if(showword !=null) showword.moveto(mover.WIDTH/2 - showword.w/2,showword.y,1000);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  }
//startPR2008-11-03^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  public void setup() {
    if(!phonicsw && words.length<6) {
      word newwords[] = new word[6];
      for(int i=0;i<newwords.length;++i) newwords[i] = words[i%words.length];
      words = u.shuffle(newwords);
    }
    if(flonly) help("help_findsound1fl");
    else if(!phonicsw) help("help_findsound1");
    else help(saysplit?(blended ?"help_findsounds2" :"help_findsounds")
              :(blended ?"help_findsound2":"help_findsound" + (noanimals?"ear":"")));
    if(saysplit) {
      for(int i=0;i<words.length;++i) words[i].homophone = false;  // no homophones in blend sounds
      setupSharks();
    }
    else if(flonly) {
        setupSharks();
    }
    else {
      int i,j;
     for(i=0; i<words.length;++i) {
       String ssw[] = words[i].phonics();
       for(j=0;j<ssw.length;++j) {
         if(ssw[j].equals("-")) {
            String ph = "-" + words[i].phsegs()[j];
 //           if (u.findString(cons, ph) < 0)    cons = u.addString(cons, ph);
         }
         else if(u.vowels.indexOf(ssw[j].charAt(0))>=0) {
           if(u.findString(vowels,ssw[j]) < 0) vowels = u.addString(vowels,ssw[j].toLowerCase());
         }
         else {
           if(u.findString(cons,ssw[j]) < 0) cons = u.addString(cons,ssw[j].toLowerCase());
         }
       }
     }
     if(!phonicsw) showword = new showword();
     if(phonicsw || flonly) {
       setupWords();
       setupNext();
     }
     else  setupNext2();
    }
    markoption();
    gamePanel.dontstart = false;
    if(showword !=null) showword.moveto(mover.WIDTH/2 - showword.w/2,showword.y,1000);

  }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  
  public void restart() {
    if(!saysplit && !flonly && phonicsw) {
      easy = options.optionval("findsound-hard") == 1;
      hard = options.optionval("findsound-hard") == 3;
      noanimals = options.option("findsound-noanimals");
    }
    else {
      setupCharacters();
      setup();
      gamePanel.refreshat = gtime+100;
    }    
//    if(flonly)mouthingheads = true;
    markoption();
    if(!phonicsw) help("help_findsound1");
    else help(saysplit?(blended ?"help_findsounds2" :"help_findsounds")
              :(blended ?"help_findsound2":"help_findsound" + (noanimals?"ear":"")));
  }
  //--------------------------------------------------------------
  void setupSharks() {
      blockFaceSay = true;
   int i,j;
   int o[] = u.shuffle(u.select(words.length,sharktot));  // select 3 words randomly
   if(!u.inlist(o,curr)) o[u.rand(o.length)] = curr;      // but one must be this
   if(flonly) {
     for(i=0;i<o.length;++i){    // check no dups
      if(o[i]!=curr && words[o[i]].v().equals(words[curr].v())){
        boolean got = false;
        int o2[] = u.shuffle(u.select(words.length,words.length));
        for(j=0;j<o2.length;++j) {
          if (!u.inlist(o, o2[j]) && !words[o2[j]].v().equals(words[curr].v())) {
            o[i] = o2[j];
            got = true;
            break;
          }
        }
        if(!got) {o = u.removeint(o,i); --i;}

     }
    }
   }
   else for(i=0;i<o.length;++i){    // check
     if(o[i]!=curr && words[o[i]].samephonics(words[curr])){
       do {
          j = u.rand(words.length) ;
       }while(u.inlist(o,j) || words[j].samephonics(words[curr]));
       o[i] = j;
     }
   }
   gamePanel.removeAllMovers();
   setupCharacters();
   listened=false;
//startPR2008-11-03^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   short k[] =  null;
   if(mouthingheads) k = u.shuffle(u.select((short)1,
                                   (short)(sharkImage.findall(imagetype).length+1),
                                   (short)o.length));
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   for(i=0;i<o.length;++i) {
//startPR2008-11-03^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     if(mouthingheads)
       faces[i] = new face(i,o[i], k[i]);
     else
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       faces[i] = new face(i,o[i]);
    }
    currword = words[curr];
    if(flonly) {
      showpicture ss = new showpicture(mover.WIDTH/4, mover.HEIGHT/2, mover.WIDTH/2, mover.HEIGHT/2);
      ss.dontremove = true;
      blockFaceSay = false;
    }
    else if(phonicsw) {
//      spokenWord.sayPhonicsWord(currword, 500, false, false,true);
        new  spokenWord.whenfree(1000) {
         public void action() {
           spokenWord.sayPhonicsWord(currword, 500, false, false,true);
           spokenFreeTimer  = new javax.swing.Timer(1000,new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(spokenWord.isfree()) {
                    blockFaceSay = false;
                    spokenFreeTimer.stop();
                }
            }
           });   
           spokenFreeTimer.start();
           
         }
       };
    }
    lastsay = -1;
  }
  //--------------------------------------------------------------
  void setupWords() {  // separate syllables
    short i, j, k,m,n, modelh=0;
    gamePanel.allclicks = true;
    heads = new head[words.length][];
    mids = new mid[words.length][][];
    tails = new tail[words.length][];
    wholes = new fmover[words.length][];
    tank = new tank();
    holes = new hole[words.length];
    wfont = sizeFont(words,screenwidth/6,tank.y*screenheight/mover.HEIGHT/8*words.length);
    wm = getFontMetrics(wfont);
    loopo: for (k = 0; k < words.length; ++k) {
      String syl[] = words[k].phonics();
      String show[] =  u.splitString(words[k].phsplit(),u.phonicsplit);
      if(words[k].phonicsmagic != null) {
        for(i=0;i<show.length;++i) {
          if(words[k].ismagicsyl(i)) {
             show = u.removeString(show,i);
          }
        }
      }
      String ch;
      fmover fm[] = new fmover[syl.length];
      ss = new String[sharktot];
      heads[k] = new head[sharktot];
      mids[k] = new mid[Math.max(0,syl.length - 2)][sharktot];
      if(syl.length>1) tails[k] = new tail[sharktot];
      for (m = 0; m < syl.length; ++m) {
        boolean vowel = u.vowels.indexOf(syl[m].charAt(0)) >= 0;
        int pos;
        if(syl[m].equals("-")) {
          vowel=false;
          pos = 0;
          if(m==0) {
            heads[k] = new head[] {new head(show[m], "-" + show[m], sharkImage.random("findsound_head_"))};
            fm[0] = heads[k][0];
          }
          else if (m < syl.length - 1) {
              mids[k][m - 1] = new mid[] {new mid(show[m], "-" + show[m], heads[k][modelh], sharkImage.random("findsound_mid_"), false)};
              fm[m] = mids[k][m-1][0];
          }
          else {
            tails[k] = new tail[] {new tail(show[m], "-" + show[m], heads[k][modelh], sharkImage.random("findsound_head_"), false)};
            fm[m] = tails[k][0];
          }
          continue;
        }
        else pos = u.findString(vowel ? vowels : cons, syl[m]);
        int o[] = u.shuffle(u.select(vowel ? vowels.length : cons.length,
                                     sharktot));
//        if(!vowel && words[k].ismagicsyl(m+1)) {   // if consonant before magic e don't use silent phoneme
//          for(i=0;i<o.length;++i) {
//             if(cons[o[i]].startsWith("-")) {
//                int add=0;
//                do{
//                  add = u.rand(cons.length);
//                } while(cons[add].startsWith("-") || u.inlist(o,add));
//                o[i] = add;
//             }
//          }
//        }
        String special[]=null;
        if(sharkStartFrame.currPlayTopic.phonicdistractors != null) {
          for (i = 0; i < sharkStartFrame.currPlayTopic.phonicdistractors.length; ++i) {
            if (sharkStartFrame.currPlayTopic.phonicdistractors[i].startsWith(syl[m] + ',')) {
              special = u.splitString(sharkStartFrame.currPlayTopic.phonicdistractors[i], ',');
              pos = 0;
              o = u.shuffle(u.select(special.length, sharktot));
              break;
            }
          }
        }
        if (!u.inlist(o, pos))
          o[u.rand(o.length)] = pos;
        for (i = 0; i < o.length; ++i)
          ss[i] = special != null ? special[o[i]] : (vowel ? vowels[o[i]] : cons[o[i]]);
        int oldlen = o.length;
        if (oldlen < sharktot) {
          for (i = (short) oldlen; i < sharktot; ++i)
            ss[i] = "";
          o = u.shuffle(u.select(u.lowercase.length(), u.lowercase.length()));
          for (i = 0; i < o.length && oldlen < sharktot; ++i) {
            ch = u.lowercase.substring(o[i], o[i] + 1);
            if (u.findString(ss, ch) < 0
                &&
                (vowel && u.vowels.indexOf(ch) >= 0 ||
                 !vowel && u.vowels.indexOf(ch) < 0)) {
              ss[oldlen++] = ch;
            }
          }
        }
        u.shuffle(ss);
        if (m == 0) {
          sharkImage ims[] = sharkImage.randomimages("findsound_head_",sharktot);
          for (i = 0; i < sharktot; ++i) {
            heads[k][i] = new head(ss[i],show[m],ims[i]);
            if (ss[i].equalsIgnoreCase(syl[m]) || syl[m].equals("-") && ss[i].equals("-"+show[m])) {
              modelh = i;
              fm[0] = heads[k][i];
            }
          }
        }
        else if (m < syl.length - 1) {
            sharkImage ims[] = sharkImage.randomimages("findsound_mid_",sharktot);
            for (i = 0; i < sharktot; ++i) {
              mids[k][m - 1][i] = new mid(ss[i], show[m], heads[k][modelh],ims[i],words[k].ismagicsyl(m+1));
              if (ss[i].equalsIgnoreCase(syl[m]) || syl[m].equals("-") && ss[i].equals("-"+show[m])) {
                fm[m] = mids[k][m-1][i];
              }
             }
        }
        else {
          sharkImage ims[] = sharkImage.randomimages("findsound_tail_", sharktot);
          for (i = 0; i < sharktot; ++i) {
            tails[k][i] = new tail(ss[i], show[m], heads[k][modelh], ims[i],words[k].ismagicsyl(m+1));
            if (ss[i].equalsIgnoreCase(syl[m]) || syl[m].equals("-") && ss[i].equals("-"+show[m])) {
              fm[m] = tails[k][i];
            }
          }
        }
      }
      loop1: for(m=0;m<k;++m) {
         if(wholes[m].length != fm.length) continue loop1;
         for(n=0;n<wholes[m].length;++n) {
            if(!wholes[m][n].im.name.equals(fm[n].im.name)) continue loop1;
         }
         --k;
         continue loopo;
      }
      wholes[k] = fm;
      holes[holetot] =  new hole(fm);
      ++holetot;
    }
  }
  //-------------------------------------------------------------------------
  void  showholes() {
    int i,j;
    for (i = 0; i < holetot; ++i) {
      holes[i].rrx.setcurr(holes[i].tox = holes[i].x =  u.rand(mover.WIDTH - holes[i].w));
      holes[i].rry.setcurr(holes[i].toy = holes[i].y =  tank.y + u.rand(tank.h - holes[i].h));
      if(!hard) {
        int repeats=0;
        outer: while(++repeats < 100) {
          for (j = 0; j < i; ++j) {
            if (holes[j].x <= holes[i].x + holes[i].w && holes[j].x + holes[j].w >= holes[i].x
                && holes[j].y <= holes[i].y + holes[i].h*3/4 && holes[j].y + holes[j].h*3/4 >= holes[i].y) {
              holes[i].rrx.setcurr(holes[i].tox = holes[i].x = u.rand(mover.WIDTH - holes[i].w));
              holes[i].rry.setcurr(holes[i].toy = holes[i].y = tank.y + u.rand(tank.h - holes[i].h));
              continue outer;
            }
          }
          break;
        }
      }
      if (!gamePanel.isMover(holes[i]))
        addMover(holes[i],holes[i].x,   holes[i].y);
      gamePanel.puttobottom(holes[i]);
    }
    gamePanel.puttobottom(tank);
    help(hard?"help_findsoundm":"help_findsoundm2");
  }
  //--------------------------------------------------------------
  void setupNext() {  // separate syllables
   short i,j;
   String ch;
   currword = words[curr];

   if(!phonicsw) gamePanel.removeAllMovers();
   listened=false;
   showfaces = gtime + 1000;
   if(flonly) {
      showpicture ss = new showpicture(mover.WIDTH/4, mover.HEIGHT/2, mover.WIDTH/2, mover.HEIGHT/2);
      ss.dontremove = true;
   }
   else {
     if (!phonicsw) {
       addMover(showword, showword.x, showword.y);
       showword.x = showword.tox = -showword.w;
       showword.moveto(mover.WIDTH / 2 - showword.w / 2,
                       mover.HEIGHT * 3 / 4 - showword.h / 2, 1000);
     }
     if (currsyllable == 0)
       spokenWord.sayPhonicsWhole(currword);
   }
  }
  //--------------------------------------------------------------
  void setupNext2() {  // for single sounds
   short i,j;
   String ch;
   ss = new String[3];
   currword = words[curr];
   String syl = currword.phonics()[currsyllable];
   boolean vowel = u.vowels.indexOf(syl.charAt(0))>=0;
   int pos = u.findString(vowel?vowels:cons,syl);
   int o[] = u.shuffle(u.select(vowel?vowels.length:cons.length,sharktot));
   if(!u.inlist(o,pos)) o[u.rand(o.length)] = pos;
   for(i=0;i<o.length;++i) ss[i] = vowel?vowels[o[i]]:cons[o[i]];
   int oldlen = o.length;
   if(oldlen<sharktot) {
     for(i=(short)oldlen;i<sharktot;++i) ss[i] = "";
     o = u.shuffle(u.select(u.lowercase.length(),u.lowercase.length()));
     for(i=0;i<o.length && oldlen <sharktot;++i) {
       ch = u.lowercase.substring(o[i],o[i]+1);
       if(u.findString(spokenWord.sayfull,ch)>=0) continue;    // avoid c,k
       if(u.findString(ss,ch) < 0
            && (vowel && u.vowels.indexOf(ch) >=0 || !vowel && u.vowels.indexOf(ch) <0)) {
          ss[oldlen++] =ch;
        }
     }
     u.shuffle(ss);
   }

   gamePanel.removeAllMovers();
   setupCharacters();
   listened=false;
   addMover(showword,showword.x,showword.y);
   
   showfaces = gtime + 1000;
     showword.x = showword.tox = -showword.w;
     showword.moveto(mover.WIDTH / 2 - showword.w / 2,
                     mover.HEIGHT * 3 / 4 - showword.h / 2, 1000);
//   if(currsyllable==0) spokenWord.sayPhonicsWhole(currword);
     new line();
  }
  
  void setupCharacters(){
     //   short ov = options.optionval(optionlist[0]);
      for(int i=0;i<optionlist.length;++i)
        student.setOption(optionlist[i]);
      int allowed[] = new int[]{};
      for (int i = 0; i < optionlist.length; ++i){
          if(options.option(optionlist[i])){
              allowed = u.addint(allowed, i);
          }
      }
 //     int ov = u.rand(allowed.length);
      int ov = -1;
      while (ov <0 || (lastCharacter == ov && allowed.length > 1)){
         ov = u.rand(allowed.length);      
         ov = allowed[ov];
      }
      lastCharacter = ov;
      mouthingheads = ( ov != 3);
      switch(ov) {
          case 0:imagetype = "blendfish_";break;
          case 1:imagetype = "blendrobot_";break;
          case 2:imagetype = "blendface_";break;
      }
  }

  //--------------------------------------------------------------
  public void afterDraw(long t) {
    int i,j;
    if(showfaces != 0 && gtime >showfaces) {
      blockFaceSay = false;
      showfaces = 0;
      if(phonicsw) {
        int  across = 2 + mids[curr].length;
        int xx = mover.WIDTH/across/2 + mover.WIDTH/across*currsyllable;
        int yy = tank.y / sharktot /2 - mover.HEIGHT/20;
        fmover fm;

        if(currsyllable==0 && heads[curr].length ==1) {
          addMover(fm = heads[curr][0], xx - heads[curr][0].w / 2, tank.y/2 - heads[curr][0].h / 2);
          fm.got=true;
          ++currsyllable;
          if(currsyllable >= currword.phonics().length) {
             showholes();
             clickontank = true;
             wantcombine = gtime + 600;
          }
          else 
              wantnext = gtime + 200;
        }
        else if(currsyllable>0 &&currsyllable < across-1 && mids[curr][currsyllable-1].length ==1) {
               fm = mids[curr][currsyllable-1][0];
               fm.got=true;
               int oldw = fm.w;
               int newy = fm.y;
               Image temp = createImage(fm.w*screenwidth/mover.WIDTH, fm.h*screenheight/mover.HEIGHT);
               Graphics g = temp.getGraphics();
               fm.im.paint(g, 0, 0, fm.w*screenwidth/mover.WIDTH, fm.h*screenheight/mover.HEIGHT);
               fm.rr = fm.im.getRect(0);
               fm.h = fm.head.rr.height * fm.h / fm.rr.height;
               fm.w = fm.head.rr.height * fm.w / fm.rr.height;
               fm.im.recolor(1,fm.head.im.getcolor(3));
               fm.im2.recolor(1,fm.head.im.getcolor(3));
               if(!(fm instanceof tail) || mids[curr].length==0) gamePanel.bringtotop(fm.head);
               newy = fm.head.rr.y*mover.HEIGHT/screenheight
                        - (fm.rr.y*mover.HEIGHT/screenheight - fm.toy) * fm.head.rr.height / fm.rr.height;
               if(noanimals) newy = tank.y/2 - fm.h/2;
               addMover(fm , xx - fm.w / 2,  newy);
               ++currsyllable;
               if(currsyllable >= currword.phonics().length) {
                  showholes();
                  clickontank = true;
                  wantcombine = gtime + 600;
               }
               else
                   wantnext = gtime + 200;
        }
        else if(currsyllable == across-1 && tails[curr].length==1) {
            fm = tails[curr][0];
            fm.got=true;
            int oldw = fm.w;
            int newy = fm.y;
            Image temp = createImage(fm.w*screenwidth/mover.WIDTH, fm.h*screenheight/mover.HEIGHT);
            Graphics g = temp.getGraphics();
            fm.im.paint(g, 0, 0, fm.w*screenwidth/mover.WIDTH, fm.h*screenheight/mover.HEIGHT);
            fm.rr = fm.im.getRect(0);
             fm.h = fm.head.rr.height * fm.h / fm.rr.height;
             fm.w = fm.head.rr.height * fm.w / fm.rr.height;
             fm.im.recolor(1,fm.head.im.getcolor(3));
             fm.im2.recolor(1,fm.head.im.getcolor(3));
             if(!(fm instanceof tail) || mids[curr].length==0) gamePanel.bringtotop(fm.head);
             newy = fm.head.rr.y*mover.HEIGHT/screenheight
                      - (fm.rr.y*mover.HEIGHT/screenheight - fm.toy) * fm.head.rr.height / fm.rr.height;
             if(noanimals) newy = tank.y/2 - fm.h/2;
             addMover(fm , xx - fm.w / 2,  newy);
             ++currsyllable;
             if(currsyllable >= currword.phonics().length) {
                showholes();
                clickontank = true;
                wantcombine = gtime + 600;
             }
             else 
                 wantnext = gtime + 200;
        }
        else {
          for (i = 0; i < sharktot; ++i) {
            if (currsyllable == 0)
              addMover(fm = heads[curr][i], xx - heads[curr][i].w / 2, yy - heads[curr][i].h / 2);
            else
              if (currsyllable < across - 1)
                addMover(fm = mids[curr][currsyllable - 1][i], xx - mids[curr][currsyllable - 1][i].w / 2,
                         yy - mids[curr][currsyllable - 1][i].h / 2);
              else
                addMover(fm = tails[curr][i], xx - tails[curr][i].w / 2, yy - tails[curr][i].h / 2);
            fokb[i] = new fokbutton(fm);
            yy += tank.y / sharktot;
          }
          help(saysplit ? (blended ? "help_findsounds2" : "help_findsounds")
               : blended ? "help_findsound2" : "help_findsound");
        }
      }
      else if(flonly) {
        setupSharks();
      }
      else{
        short k[] =  null;
        if(mouthingheads) k = u.shuffle(u.select((short)1,
                                   (short)(sharkImage.findall(imagetype).length+1),
                                   sharktot));          
        for(i=0;i<sharktot;++i) {
            if(mouthingheads)
                faces[i] = new face(i,ss[i], k[i]);
            else
                faces[i] = new face(i,ss[i]); 
        }
      }
    }
    if(wantcombine != 0) {
      if(gtime > wantcombine) {
        wantcombine = 0;
        int ww = 0;
        for (i = 0; i < wholes[curr].length; ++i) {
          ww += wholes[curr][i].rr.width * mover.WIDTH / screenwidth;
        }
        int xx = mover.WIDTH / 2 - ww / 2;
        int yy = tank.y / 2;
        for (i = 0; i < wholes[curr].length; ++i) {
          wholes[curr][i].moveto(xx - wholes[curr][i].offset * mover.WIDTH / screenwidth, wholes[curr][i].y, 600);
          xx += wholes[curr][i].rr.width * mover.WIDTH / screenwidth;
        }
        if (!hard) {
          loop1:for (i = 0; i < holes.length; ++i) {
            if (!holes[i].full && holes[i].fm.length == wholes[curr].length) {
              for (j = 0; j < holes[i].fm.length; ++j) {
                if (!holes[i].fm[j].im.name.equals(wholes[curr][j].im.name))       continue loop1;
              }
              hole hh = holes[i].newfullhole();
              hh.x = gamePanel.mousex - hh.w/2;
              hh.y = gamePanel.mousey - hh.h/2;
              hh.dontgrabmouse = true;
              if(easy) {
                 addMover(hh,mover.WIDTH/2-hh.w/2,tank.y/2 - hh.h/2);
                 hh.moveto(holes[i].x,holes[i].y,1000);
                 hh.temp = true;
                 holes[i].mouseClicked(0,0);
              }
              else gamePanel.attachToMouse(hh);
              break loop1;
            }
          }
        }
      }
    }
    if (stoptime != 0 && gtime > stoptime) {
     clickontank = false;
     gamePanel.finishwith();
     wantnext = gtime-1;
     stoptime = 0;
     for(i=0; i<wholes[curr].length; ++i) wholes[curr][i].kill = true;
     for(i=0; i<holes.length; ++i) holes[i].kill = true;
   }
   if(wantnext != 0) {
      if(gtime > wantnext) {
//startPR2007-12-20^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        if(Demo_base.isDemo)
          if (Demo_base.demoIsReadyForExit(0, true)) return;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        wantnext = 0;
//        if(!fl && !saysplit) { 
        if(!flonly && !saysplit) { 
          if(currsyllable >= currword.phonics().length) {
            totsyl += words[curr].phonics().length;
            if (++curr < words.length && totsyl < MAXSYL) {
              if(delayedflip && !completed){
                  flip();
              }   
              currsyllable = 0;
            }
            else {
              score(gametot1);
               if(phonicsw) {
                String mess = "";
                if(hard) {
                   if (win >= words.length * 2)
                     mess = rgame.getParm("wonderful");
                   else      if (win >= words.length)          mess = rgame.getParm("good");
                   int max = student.optionint("findsound_max");
                   if(!rgame.topicName.equals(student.optionstring("findsound_topic"))) max = 0;
                   if (max <= 0)      {
                     mess += u.edit(rgame.getParm("yougot"), new int[] {win, max});
                                 student.setOption("findsound_topic",rgame.topicName);
                   }
                   else if (max == win)   mess += u.edit(rgame.getParm("yougoteqbest"), new int[] {win, max});
                   else if (max < win) {
                     mess += u.edit(rgame.getParm("yougotbest"), new int[] {max = win});
                     student.setOption("findsound_max", win);
                     student.setOption("findsound_topic",rgame.topicName);
                   }
                   else mess += u.edit(rgame.getParm("bestis"), new int[] {win, max});
                   showmessage (mess,  mover.WIDTH / 4, mover.HEIGHT/8, mover.WIDTH*3/4,mover.HEIGHT*3/8);
                }
                clearexit = true;
                exitbutton(mover.HEIGHT / 2);
              }
              else exit(1000);
              return;
            }
          }
          if(phonicsw || flonly)        setupNext();
          else setupNext2();
        }
        else if (++curr < words.length) {
          if(delayedflip && !completed){
              flip();
          }
          setupSharks();
        }
        else {
          score(gametot1);
         exit(1000);
          return;
       }

      }
    }
    else if(nextsay != 0 && gtime > nextsay) {
       nextsay = 0;
      if(lastsay >= 0) {
        faces[lastsay].saying = false;
        lastsay = -1;
      }
    }
     if(faces[0] != null) {
       boolean gotover = false;
       for (i = 0; i < sharktot; ++i) {
         if (faces[i].mouseOver) {

           gotover = true;
           if (lastsay == -1 && i != lastfacesay && !blockFaceSay) {
             faces[i].said = true;
             lastfacesay = i;
             faces[i].say();
           }
           break;
         }
       }
       if(!gotover) lastfacesay = -1;
     }
     else {
       boolean gotover = false;
       for(i=0;i<gamePanel.mtot;++i) {
          if(!clickontank && gamePanel.m[i] instanceof fmover && gamePanel.m[i].mouseOver) {
             gotover = true;
             fmover fm = (fmover)gamePanel.m[i];
             if(flastsay2 == null || flastsay2 != fm) {
               fm.say();
               flastsay2 = fm;
               fm.said = true;
             }
          }
        }
        if(!gotover) flastsay2 = null;
     }
   }
  //--------------------------------------------------------------------
  public void listen1() {
      nextsay = 0;
      spokenWord.flushspeaker(true);
      if(lastsay >= 0) {
         faces[lastsay].saying = false;
         lastsay = -1;
       }
       if(saysplit) spokenWord.sayPhonicsWord(currword,50+(8-speed)*100,false,true,true);
       else  spokenWord.sayPhonicsWhole(currword);
 }
  //-------------------------------------------------------
  
  
  class face extends mover {
      int pos,listnum;
      boolean saying,happy,sad;
      Color col;
      int mw,ew,eh,ex,ey,my;
      int earw,earh;
      boolean roundhat = u.rand(2) == 0;
      int hath,hatw, brimw;
      String syl;
      long hatoff;
      boolean upside;
      boolean said;
//startPR2008-11-03^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      sharkImage im = null;
      public face(int listi, int pos1, int noim) {
        int i;
        pos = pos1;
        listnum=listi;
        im = sharkImage.random(imagetype+String.valueOf(noim));
        setup(listi);
      }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      public face(int listi,int pos1) {
        int i;
        pos = pos1;
        listnum=listi;
        setup(listi);
      }
      public face(int listi,String syl1) {
         int i;
         syl = syl1;
         listnum=listi;
         setup(listi);
      }      
      public face(int listi,String syl1, int noim) {
         int i;
         syl = syl1;
         listnum=listi;
         im = sharkImage.random(imagetype+String.valueOf(noim));
         setup(listi);
      }
      void setup(int listi) {
//startPR2008-11-03^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        if(im==null){
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        int i;
        w = mover.WIDTH / (10 + u.rand(6));
        h = mover.WIDTH * mover.HEIGHT / w/100;
        x = mover.WIDTH/sharktot/2 +  mover.WIDTH/sharktot*listi - w/2;
        y = mover.HEIGHT/2 - (flonly?h + mover.HEIGHT/6:h);
        loop1:while (true) {
          col = u.brightcolor().darker();
          for (i = 0; i < listi; ++i) {
            if (u.tooclose(col,faces[i].col))    continue loop1;
          }
          break;
        }
        mw = w/3 + u.rand(w/3);
        ew = w/8 + u.rand(w/8);
        eh = h/8 + u.rand(h/8);
        ex = w/8 + u.rand(w/8);
        ey = -u.rand(h/4);
        earw = w/8 + u.rand(w/8);
        earh = h/4 + u.rand(h);
        hath = h/4 + u.rand(h/4);
        hatw = h/4 + u.rand(w/2);
        brimw = hatw + w/8 + u.rand(w/2);
        w += earw;
        my = h/8 + u.rand(h/8);
//startPR2008-11-03^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      }
      else{
 //       im.h = mover.HEIGHT/2;
        im.h = mover.HEIGHT*2/7;
        im.w = mover.WIDTH/(sharktot+2);
        im.adjustSize(screenwidth, screenheight);
        im.setControl("normal");
        w = im.w;
        h = im.h;
        x = mover.WIDTH/sharktot/2 +  mover.WIDTH/sharktot*listi - w/2;
        y = mover.HEIGHT/2 - (flonly?h + mover.HEIGHT/6:h);
      }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      addMover(this, x, y);
      new okbutton(this);
    }
     void say() {
//startPR2008-11-03^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       if(!spokenWord.isfree())return;
       if(im!=null)im.setControl("speak");
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       spokenWord.flushspeaker(true);
       if(lastsay >= 0) {
         faces[lastsay].saying = false;
         lastsay = -1;
       }
       saying = true;
       if(flonly){
           words[pos].say();
       }
       else{
           if(syl!=null) {
              spokenWord sw = spokenWord.findandreturn(syl + '~', currword.database);
              if (sw != null)  sw.say();
           }
           else
               spokenWord.sayPhonicsWhole(words[pos]);
       }
       lastsay = listnum;
       nextsay = Math.max(spokenWord.endsay2,gtime+1000);
     }
//     public void mouseClicked(int mx, int my) {
//       say();
//       said = true;
//     }
     public void test() {
       int i;
       if(!said || !listened || wantnext != 0) return;
      nextsay = 0;
      spokenWord.flushspeaker(true);
      if(syl!=null && syl.equals(currword.phonics()[currsyllable]) || syl==null && pos==curr) {
          blockFaceSay = true;
        hatoff=gtime;
        for(i=0;i<faces.length;++i) {
          if(faces[i] != this) faces[i].sad = true;
        }
        gamescore(1);
        ++currsyllable;
        wantnext = gtime + 1000;
        if(currsyllable >= currword.phonics().length) {
            if(showword != null) showword.moveto(mover.WIDTH, showword.y, 2000);
            wantnext = gtime + 2000;
        }
        else {
          wantnext = gtime + 3000;
        }
        saying = true;
        happy=true;
//startPR2008-11-03^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       if(im!=null)im.setControl("correct");
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      }
      else {
        gamePanel.currgame.error(currword.v());
        gamescore(-1);
//        say();
        noise.groan();
//        spokenWord.sayPhonicsWord(currword, 500,true,false,true);
//        if(saysplit)--curr;
//        wantnext = spokenWord.endsay2+2000;
        sad = true;
        upside=true;
//startPR2008-11-03^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       if(im!=null)im.setControl("wrong");
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      }
    }
    public void paint(Graphics g, int x1i,int y1,int w1i, int h1) {
//startPR2008-11-03^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      if (im == null) {
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      int i;
      int earw1 = earw*screenwidth/mover.WIDTH;
      int earh1 = earh*screenheight/mover.HEIGHT;
      int hatw1 = hatw*screenwidth/mover.WIDTH;
      int hath1 = hath*screenheight/mover.HEIGHT;
      int brimw1 = brimw*screenwidth/mover.WIDTH;
      int w1 = w1i - earw1;
      int x1 = x1i + earw1/2;
      int mw1 = mw*screenwidth/mover.WIDTH;
      int mh1 = saying && !sad || happy? h1/3 : h1/12;
      int my1 = y1+h1/2 + my*screenheight/mover.HEIGHT - mh1/2;
      int mx1 = x1+w1/2 - mw1/2;
      int ew1 = ew*screenwidth/mover.WIDTH;
      int eh1 = eh*screenheight/mover.HEIGHT;
      int ex1 = x1+w1/2 - ex*screenwidth/mover.WIDTH - ew1/2;
      int ex2 = x1+w1/2 + ex*screenwidth/mover.WIDTH - ew1/2;
      int ey1 = y1+h1/2 + ey*screenheight/mover.HEIGHT - eh1/2 - (saying?h1/6:0);
      int eary = Math.min(h1+y1-earh1, y1+h1/2-earh1/2);
      int haty = saying && !sad && wantnext==0? y1-h1/8 : y1 + h1/32;
      if(hatoff != 0) {
        double gg = 2.0*(haty-hath1)/500/500;
        if(gtime-hatoff>1000) {hatoff = 0;}
        else haty -=  (int)(gg*500*(gtime-hatoff) - gg*(gtime-hatoff)*(gtime-hatoff)/2);
      }
      g.setColor(col);
      g.fillOval(x1,y1,w1,h1);
      g.fillOval(x1i,eary,earw1,earh1);
      g.fillOval(x1i+w1i-earw1,eary,earw1,earh1);
      g.setColor(col.darker());
      if(upside) {
        if (roundhat) {
          g.fillArc(x1 + w1 / 2 - hatw1 / 2, haty - hath1*3, hatw1, hath1 * 2, 0, -180);
        }
        else
          g.fillRect(x1 + w1 / 2 - hatw1 / 2, haty - hath1*2, hatw1, hath1);
        g.fillRect(x1+w1/2-brimw1/2, haty  - hath1*2, brimw1,3);
      }
      else {
        if (roundhat) {
          g.fillArc(x1 + w1 / 2 - hatw1 / 2, haty - hath1, hatw1, hath1 * 2, 0, 180);
        }
        else
          g.fillRect(x1 + w1 / 2 - hatw1 / 2, haty - hath1, hatw1, hath1);
        g.fillRect(x1+w1/2-brimw1/2, haty , brimw1,3);
    }
      g.setColor(Color.red);
      if(sad) {
          g.fillArc(mx1,my1,mw1,mh1*2,0,180);
          g.setColor(col);
          g.fillArc(mx1,my1+mh1/2+1,mw1,mh1,0,180);
      }
      else if(happy) {
        g.fillArc(mx1,my1-mh1,mw1,mh1*2,0,-180);
        g.setColor(col);
        g.fillArc(mx1,my1-mh1+mh1/2-1,mw1,mh1,0,-180);
      }
      else g.fillOval(mx1,my1,mw1,mh1);
      g.setColor(Color.blue);
      g.fillOval(ex1,ey1,ew1,eh1);
      g.fillOval(ex2,ey1,ew1,eh1);
//startPR2008-11-03^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      }
      else {
        im.paint(g, x1i,y1,w1i,h1);
      }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    }
  }
  class showword extends mover {
    Font f;
    FontMetrics m;
    int lastw;
    showword() {
      w = mover.WIDTH/3;
      h = mover.HEIGHT/4;
      addMover(this,-w,mover.HEIGHT*5/6-h/2);
    }
    public void paint(Graphics g, int x1,int y1,int w1, int h1) {
      if(!saysplit || !phonicsw) {
        String s = currword.v();
        String ss[] = u.splitString(currword.vsplit(),'/');
        if(lastw != w1) {
          f = sizeFont(new String[]{s},w1,h1);
          m = getFontMetrics(f);
          lastw = w1;
        }
        g.setFont(f);
        g.setColor(Color.white);
        if (currword.ismagicsyl(currsyllable + 1)) {
          currword.paint(g, new Rectangle(x1, y1, w1, h1), (byte)(word.ADDMAGIC + word.CENTRE));
        }
        else if (currword.ismagicsyl(currsyllable)) {
          currword.paint(g, new Rectangle(x1, y1, w1, h1), (byte)(word.CENTRE));
        }
        else if(!phonicsw) {
          g.drawString(s, x1+w1/2-m.stringWidth(s)/2,y1+h1/2-m.getHeight()/2+m.getAscent());
       }
        else {
          int i,pos=0;
          for(i=0;i<currsyllable;++i) pos += ss[i].length();
          String sss = s.substring(0,pos);
          if(currsyllable<ss.length) sss += "?";
          g.drawString(sss, x1+w1/2-m.stringWidth(s)/2,y1+h1/2-m.getHeight()/2+m.getAscent());
        }
      }
    }
  }
  class showword2 extends mover {
   Font f;
   FontMetrics m;
   int lastw;
   long start = gtime;
   String s = currword.v();
   word wd = currword;
   showword2(int listnum) {
     w = mover.WIDTH/3;
     h = mover.HEIGHT/4;
     addMover(this,faces[listnum].x+faces[listnum].w/2-w/2,mover.HEIGHT*3/4-h/2);
   }
   public void paint(Graphics g, int x1,int y1,int w1, int h1) {
       if(shark.macOS) ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
     if(lastw != w1) {
       f = sizeFont(new String[]{s},w1,h1);
       m = getFontMetrics(f);
       lastw = w1;
     }
     if(start!=0 && gtime > start+1000) {
       moveto(mover.WIDTH,y,1000);
       temp=true;
       start=0;
     }
     g.setFont(f);
     g.setColor(Color.black);
     if (wd.phonicsmagic != null) {
       wd.paint(g, new Rectangle(x1, y1, w1, h1), (byte)(word.CENTRE));
     }
     else {
       g.drawString(s, x1+w1/2-m.stringWidth(s)/2,y1+h1/2-m.getHeight()/2+m.getAscent());
    }
   }
 }
 class okbutton extends mover {
    face face;
    long pressed;
    Color bg;
    okbutton(face face1) {
      face=face1;
      bg = Color.yellow;
      w = mover.WIDTH/12;
      h = mover.HEIGHT/16;
      addMover(this,face.x + face.w/2-w/2,  face.y+face.h + h/8);
    }
    public void paint(Graphics g, int x1,int y1,int w1, int h1) {
      if(!face.said) return;
      if(shark.macOS) ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
      if(fok == null || lastwo != w1) {
         fok = sizeFont(new String[]{ok},w1+3/4,h1*3/4);
         fokm = getFontMetrics(fok);
         lastwo = w1;
      }
      g.setColor(bg);
      g.fillRect(x1,y1,w1,h1);
      g.setFont(fok);
      g.setColor(Color.black);
      g.drawString(ok,x1+w1/2-fokm.stringWidth(ok)/2,y1+h1/2+fokm.getAscent()/2);
      u.buttonBorder(g,new Rectangle(x1,y1,w1,h1),bg,gtime-pressed>500);
    }
    public void mouseClicked(int mx, int my) {
      if(!face.said) return;
      pressed=gtime;
      listened = true;
      face.test();
       if(lastsay >= 0) {
         faces[lastsay].saying = false;
         lastsay = -1;
       }
    }
 }
 class fokbutton extends mover {
    fmover fm;
    long pressed;
    Color bg;
    fokbutton(fmover fm1) {
      fm = fm1;
      bg = Color.yellow;
      w = mover.WIDTH/12;
      h = mover.HEIGHT/16;
      addMover(this,fm.x + fm.w/2-w/2,  fm.y+fm.h + h/8);
    }
    public void paint(Graphics g, int x1,int y1,int w1, int h1) {
      if(!fm.said) return;
      if(shark.macOS) ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
      if(fok == null || lastwo != w1) {
         fok = sizeFont(new String[]{ok},w1+3/4,h1*3/4);
         fokm = getFontMetrics(fok);
         lastwo = w1;
      }
      g.setColor(bg);
      g.fillRect(x1,y1,w1,h1);
      g.setFont(fok);
      g.setColor(Color.black);
      g.drawString(ok,x1+w1/2-fokm.stringWidth(ok)/2,y1+h1/2+fokm.getAscent()/2);
      u.buttonBorder(g,new Rectangle(x1,y1,w1,h1),bg,gtime-pressed>500);
    }
    public void mouseClicked(int mx, int my) {
      if(!fm.said) return;
      if(clickontank) return;
      pressed=gtime;
      listened = true;
      fm.test();
    }
 }
// class listenbutton2 extends mover {
//    long pressed;
//    Color bg;
//    listenbutton2() {
//       bg = Color.yellow;
//      w = mover.WIDTH/6;
//      h = mover.HEIGHT/8;
//      addMover(this,mover.WIDTH/2 -w/2,  mover.HEIGHT*15/16 - h);
//    }
//    public void paint(Graphics g, int x1,int y1,int w1, int h1) {
//      g.setColor(bg);
//      g.fillRect(x1,y1,w1,h1);
//      ear.paint(g,x1+w1/8,y1+h1/8,w1*3/4,h1*3/4);
//      u.buttonBorder(g,new Rectangle(x1,y1,w1,h1),bg,gtime-pressed>500);
//    }
//    public void mouseClicked(int mx, int my) {
//      pressed=gtime;
//      listened = true;
//      listen1();
//    }
// }
 //======================================================================================
 class line extends mover {
//    Color col = gamePanel.getBackground().brighter();
    Color col;

    line() {
      w = mover.WIDTH;
      h = mover.HEIGHT/3;
      if(student.optionstring("bgcolor")==null)
          col = gamePanel.getBackground().brighter();
      else
          col = u.darker(gamePanel.getBackground(), 0.7f);
      addMover(this,0,mover.HEIGHT-h);
      gamePanel.puttobottom(this);
    }
    public void paint(Graphics g, int x1,int y1,int w1, int h1) {
       g.setColor(col);
       g.fillRect(x1,y1,w1,h1);
    }
 }
 //-----------------------------------------------------------------------------------
 class fmover extends mover {
   String s,show;
   Color col;
   boolean got, said, saying;
   sharkImage im, im2;
   Rectangle rr,rr2;
   head head;
   int w2,h2,y2,actw;
   boolean magic_e,silent;
   int offset, offset2;
//   public void mouseClicked(int mx, int my) {
//     if(clickontank) return;
//     say();
//     said = true;
//   }
   void say() {
     if(silent) return;
     spokenWord.flushspeaker(true);
     if(flastsay != null) {
       flastsay.saying = false;
       flastsay.im.setControl("normal");
     }
     saying = true;
     im.setControl("move");
     spokenWord sw = spokenWord.findandreturn(s + '~', currword.database);
     if(sw != null) sw.say();
     flastsay = this;
     nextsay = Math.max(spokenWord.endsay2,gtime+1000);
   }
   public void test() {
     int i;
     if(!said || !listened || wantnext != 0) return;
    nextsay = 0;
    spokenWord.flushspeaker(true);
    if(s.equalsIgnoreCase(currword.phonics()[currsyllable])
            || s.startsWith("-") && s.substring(1).equalsIgnoreCase(currword.phsegs()[currsyllable])) {
      gamescore(1);
      got = true;
      spokenWord.sayPhonicsSyl(currword,currword.fullval(currsyllable+1));
      if(!(this instanceof head)) {
        int oldw = w;
        int newy = y;
         h = head.rr.height * h / rr.height;
         w = head.rr.height * w / rr.height;
         im.recolor(1,head.im.getcolor(3));
         im2.recolor(1,head.im.getcolor(3));
         if(!(this instanceof tail) || mids[curr].length==0) gamePanel.bringtotop(head);
         newy = head.rr.y*mover.HEIGHT/screenheight
                  - (rr.y*mover.HEIGHT/screenheight - toy) * head.rr.height / rr.height;
         if(noanimals) newy = tank.y/2 - h/2;
         moveto(tox + (oldw-w)/2, newy, 600);
      }
      else moveto(tox, tank.y/2 - h/2, 600);
      if(fokb[0] != null) {
         for(i=0;i<sharktot;++i) {
            removeMover(fokb[i]);
            if(!fokb[i].fm.got) removeMover(fokb[i].fm);
            fokb[i] = null;
         }
     }
     ++currsyllable;
      if(currsyllable >= currword.phonics().length) {
        showholes();
        clickontank = true;
         wantcombine = gtime + 600;
      }
      else
          wantnext = gtime + 200;
      saying = true;
      im.setControl("move");
    }
    else {
      gamePanel.currgame.error(currword.v());
      gamescore(-1);
//      say();
      noise.groan();
//      if(saysplit)--curr;
//      if(!phonicsw) wantnext = spokenWord.endsay2+2000;
    }
  }
  public void paint(Graphics g, int x1,int y1,int w1, int h1) {
     if(saying && gtime > spokenWord.endsay2) im.setControl("normal");
     if(noanimals) {
       if(!got) ear.paint(g, x1+w1/4, y1+h1/4, w1/2, h1/2);
       rr = new Rectangle(x1+w1/4, y1+h1/4, w1/2, h1/2);
     }
     else  {
//startPR2007-12-04^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       im.paint(g, x1, y1, w1, h1);
//startPR2007-12-04^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       rr = im.getRect(0);
     }
     offset = rr.x - x1;
     if(got) {
       g.setFont(wfont);
       g.setColor(noanimals?Color.white:Color.black);
       if(this instanceof tail || this instanceof head && wholes[curr].length==2 && wholes[curr][1].got) {
         int i;
         for(i=0; i<wholes[curr].length; ++i) { // do old ones again so not covered
           int len = wm.stringWidth(wholes[curr][i].show());
           Rectangle rr1 = wholes[curr][i].rr;
           g.drawString(wholes[curr][i].show(),
                          rr1.x + rr1.width / 2 - len / 2,
                        rr1.y + rr1.height / 2 - wm.getHeight() / 2 +
                        wm.getAscent());
         }
       }
       else if(!wholes[curr][wholes[curr].length-1].got) {
           int len = wm.stringWidth(show());
           g.drawString(show(),
                        Math.max(x1,
                                 Math.min(x1 + w1 - len,
                                          rr.x + rr.width / 2 - len / 2)),
                        rr.y + rr.height / 2 - wm.getHeight() / 2 +
                        wm.getAscent());
         }
    }
    else if(magic_e) {
        g.setFont(wfont);
        g.setColor(Color.black);
        int len = wm.stringWidth("-e");
        g.drawString("-e", Math.max(x1,Math.min(x1+w1-len,rr.x + rr.width/2 - len/2)), rr.y + rr.height/2 - wm.getHeight()/2 + wm.getAscent());
    }
//    if(silent) {
//      g.setFont(wfont);
//      int len = wm.stringWidth(show);
//      int ww = len*3;
//      int hh = wm.getHeight();
//      g.setColor(Color.black);
//      g.drawString(show(),
//                   x1+w1/2-len/2,
//                   rr.y + rr.height / 2 - wm.getHeight() / 2 +
//                   wm.getAscent());
//    }
  }
  public void paint2(Graphics g, int x1,int y1,int w1, int h1) {
    im2.black = true;
    im2.paint(g,x1,y1,w1,h1);
    im2.black = false;
    rr2 = im2.getRect(0);
    offset2 = rr2.x - x1;
  }
  String show() {
    if(show.startsWith("-")) return show.substring(1);
    else return show;
  }
  public void paint3(Graphics g, int x1,int y1,int w1, int h1) {
    im2.paint(g,x1,y1,w1,h1);
    rr2 = im2.getRect(0);
    offset2 = rr2.x - x1;
  }
 }
   //-----------------------------------------------------------------------------------
 class head extends fmover {
   head(String ss,String show1, sharkImage im1) {
      s  = ss;
      show=show1;
      w = mover.WIDTH/6;
//startPR2007-12-04^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      w = mover.WIDTH/6;
//      h = mover.HEIGHT/6;
       h = tank.y/6;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      im = im1;
      im2 = new sharkImage(im,false);
      im.h = h;
      im.w = w;
      im.adjustSize(screenwidth,screenheight);
      w = im.w;
      h = im.h;
      if(ss.startsWith("-")) {
        silent = said = got = true;
        if(ss.length()>1) show = ss.substring(1);
      }
    }
  }
 //-----------------------------------------------------------------------------------
 class mid extends fmover {
   mid(String ss,String show1, head head1,sharkImage im1, boolean magice) {
     head = head1;
     s = ss;
     show=show1;
     w = mover.WIDTH / 8;
     h = head.h;
     im = im1;
     im2 = new sharkImage(im,false);
     im.h = h;
     im.w = w;
     im.adjustSize(screenwidth,screenheight);
     w = im.w;
     h = im.h;
     magic_e = magice;
     if(ss.startsWith("-")) {
       silent = said = true;
       if(ss.length()>1) show = ss.substring(1);
     }
     if (magic_e)  show += "e";
   }
 }
 //-----------------------------------------------------------------------------------
 class tail extends fmover {
   tail(String ss,String show1, head head1,sharkImage im1, boolean magice) {
     head = head1;
     s = ss;
     show = show1;
     w = mover.WIDTH / 8;
     im = im1;
     im2 = new sharkImage(im, false);
     im.h = head.h;
     im.w = w;
     im.adjustSize(screenwidth, screenheight);
     w = im.w;
     h = im.h;
     magic_e = magice;
     if (magic_e)  show += "e";
     if(ss.startsWith("-")) {
       silent = said = true;
       if(ss.length()>1) show = ss.substring(1);
     }
   }
 }
 class tank extends mover {
    Color col = new Color(204,255,234);
    long nexttime;
    tank() {
       h = mover.HEIGHT/4;
       w = mover.WIDTH;
       addMover(this,0,mover.HEIGHT - h);
    }
    public void paint(Graphics g, int x1,int y1,int w1, int h1) {
       g.setColor(col);
       g.fillRect(x1,y1,w1,h1);
    }
 }
 class hole extends mover {
     fmover fm[];
     boolean full;
     randrange_base rrx =  new randrange_base(0, mover.WIDTH - mover.WIDTH/8 , mover.WIDTH/10);
     randrange_base rry =  new randrange_base(tank.y,  mover.HEIGHT - tank.h/3, mover.HEIGHT/10);
     String s;
     int oldw;
     int lastx = -1, totw;
     boolean toright;
     hole(fmover fm1[]) {
        int i;
        fm = fm1;
        s = "";
        w = 0;
        h = tank.h/3;
        for(i=0;i<fm.length;++i) {
           s += fm[i].show();
           w += fm[i].w * h / fm[i].h ;
        }
     }
     public void changeImage(long currtime) {
         int lastx = x;
         if(hard || completed) {
           x = tox = rrx.next(currtime);
           y = toy = rry.next(currtime);
         }
         if(completed && !full) kill = true;
     }
     public void paint(Graphics g, int x1,int y1,int w1, int h1) {
        if(!hard && !full && !clickontank && !completed) return;
//startPR2007-12-04^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        int i,ww,xx=x1;
        if(oldw==0) {
          int maxabove=0, maxbelow= 0;
          g.setClip(0,0,0,0);
          for(i=0; i<fm.length; ++i) {
                fm[i].im.paint(g, 0, 0,
                              fm[i].w * screenwidth / mover.WIDTH,
                              fm[i].h * screenheight / mover.HEIGHT);
               fm[i].rr = fm[i].im.getRect(0);
               if(fm[i].rr.height ==0) continue;
               if (i > 0)
                 oldw += fm[i].w * fm[0].rr.height / fm[i].rr.height;
               else
                 oldw = fm[i].w;
               int yy = fm[i].rr.y * fm[0].rr.height / fm[i].rr.height *
                   mover.HEIGHT / screenheight;
               maxabove = Math.max(maxabove, yy);
               maxbelow = Math.max(maxbelow,
                                   fm[i].h * fm[0].rr.height / fm[i].rr.height -
                                   yy);

          }
          g.setClip(null);
          if(maxbelow+maxabove > h* w/oldw) {
              oldw = w  *  (maxbelow+maxabove)/h;
          }
          totw = 0;
          for(i=0; i<fm.length; ++i) {
            if(fm[i].rr.height ==0) continue;
            fm[i].w2 = fm[i].w * fm[0].rr.height / fm[i].rr.height * w/oldw;
            fm[i].h2 = fm[i].h * fm[0].rr.height / fm[i].rr.height * w/oldw;
            fm[i].y2 = (maxabove - fm[i].rr.y * fm[0].rr.height / fm[i].rr.height*mover.HEIGHT/screenheight) * w/oldw;
            if(i==0) totw  = fm[i].actw = fm[0].w2 - (fm[0].rr.x + fm[0].rr.width)*mover.WIDTH/screenwidth * w/oldw;
            else if(i<fm.length-1) totw +=  (fm[i].actw =fm[i].w2 - (fm[i].rr.x + fm[i].rr.width) *mover.WIDTH/screenwidth
                                                        * fm[0].rr.height / fm[i].rr.height * w/oldw);
            else totw += (fm[i].actw =(fm[i].rr.x + fm[i].rr.width) *mover.WIDTH/screenwidth
                                                        * fm[0].rr.height / fm[i].rr.height * w/oldw);
          }
        }
        xx = x1 + w1/2 - totw* screenwidth / mover.WIDTH/2;
        if(lastx>=0 && (completed || hard) && (lastx < x1 || lastx == x1 && toright)) {
//startPR2007-12-04^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
           toright = true;
           for (i = fm.length-1; i >= 0; --i) {
             fm[i].im2.lefttoright = true;
             if (full && (hard  || !clickontank || gamePanel.movesWithMouse(this))) {
              fm[i].paint3(g, xx-fm[i].offset2, y1 + fm[i].y2 * screenheight / mover.HEIGHT,
                           ww = fm[i].w2 * screenwidth / mover.WIDTH,
                           fm[i].h2 * screenheight / mover.HEIGHT);
              w = Math.max(w, (xx-fm[i].offset2)*mover.WIDTH/screenwidth + fm[i].w2 - x);
            }
            else
              fm[i].paint2(g, xx-fm[i].offset2, y1 + fm[i].y2 * screenheight / mover.HEIGHT,
                           ww = fm[i].w2 * screenwidth / mover.WIDTH,
                           fm[i].h2 * screenheight / mover.HEIGHT);
            fm[i].im2.lefttoright = false;
            xx = fm[i].rr2.x + fm[i].rr2.width;
           }
        }
        else {
          toright = false;
          for (i = 0; i < fm.length; ++i) {
            if (full && (hard || !clickontank || gamePanel.movesWithMouse(this))) {
              fm[i].paint3(g, xx-fm[i].offset2, y1 + fm[i].y2 * screenheight / mover.HEIGHT,
                           ww = fm[i].w2 * screenwidth / mover.WIDTH,
                           fm[i].h2 * screenheight / mover.HEIGHT);
              w = Math.max(w, (xx-fm[i].offset2)*mover.WIDTH/screenwidth + fm[i].w2 - x);
            }
            else
              fm[i].paint2(g, xx-fm[i].offset2, y1 + fm[i].y2 * screenheight / mover.HEIGHT,
                           ww = fm[i].w2 * screenwidth / mover.WIDTH,
                           fm[i].h2 * screenheight / mover.HEIGHT);
            xx = fm[i].rr2.x + fm[i].rr2.width;
          }
        }
        lastx = x1;
        if(full) {
          int start = fm[toright?fm.length-1:0].rr2.x;
          if (wfont3 == null) {
            wfont3 = sizeFont(new String[] {s}, (xx-start) * 7 / 8, h1 / 2);
            wm3 = getFontMetrics(wfont3);
          }
          g.setFont(wfont3);
          g.setColor(Color.black);
          g.drawString(s, (start + xx) / 2 - wm3.stringWidth(s) / 2,
                       y1 + h1 / 2 - wm3.getHeight() / 2 + wm3.getAscent());
        }
//startPR2007-12-04^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     }
     hole newfullhole() {
       int i;
       fmover fmx[] = new fmover[fm.length];
        for(i=0;i<fm.length;++i) {
          fmx[i] = new fmover();
          fmx[i].s = fm[i].s;
          fmx[i].show = fm[i].show();
          fmx[i].col = fm[i].col;
          fmx[i].got = fm[i].got;
          fmx[i].im = new sharkImage(fm[i].im, false);
          fmx[i].im2 = new sharkImage(fm[i].im2, false);
          fmx[i].im2.setControl("move");
          fmx[i].head = fm[i].head;
          fmx[i].magic_e = fm[i].magic_e;
          fmx[i].w = fm[i].w;
          fmx[i].h = fm[i].h;
        }
        hole hh = new hole(fmx);
        hh.full = true;
        return hh;
     }
     public void mouseClicked(int mx, int my) {
       int i;
       boolean bad = false;
       if (clickontank && !full) {
         if(fm.length != wholes[curr].length) bad = true;
         else for(i=0;i<fm.length;++i) {
            if(!fm[i].im.name.equals(wholes[curr][i].im.name)) {bad = true; break;}
         }
         if (!bad) {
           if(hard) {
             hole hh = newfullhole();
             hh.rrx.setcurr(x);
             hh.rry.setcurr(y);
             addMover(hh, x, y);
             gamescore(1);
           }
           else {
             full = true;
           }
           dontgrabmouse = true;
           stoptime = gtime+3000;
           ++win;
           if(hard) {
             showholes(); // reposition them
           }
           else {
             clickontank = false;
             gamePanel.finishwith();
             if(easy) 
                 wantnext = gtime + 2000;
             else wantnext = gtime;
             stoptime = 0;
             for(i=0;i<wholes[curr].length;++i) wholes[curr][i].kill = true;
            }
         }
         else if(!hard) {
            noise.beep();
         }
         else {
               clickontank = false;
               wantnext = gtime;
               stoptime = 0;
               for(i=0;i<wholes[curr].length;++i) wholes[curr][i].kill = true;
               for(i=0;i<holes.length;++i) holes[i].kill = true;
         }
       }
     }
 }
}
