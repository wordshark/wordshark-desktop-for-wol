package shark;


public class runningGame {
  /**
   * Used to reference the current instance of runningGame
   */
   public static runningGame currGameRunner;

   Class gameClass;
   sharkGame game;
 //  public String gamename, shortgamename;
   public String gamename;
   /**
    * parameter name(These parameters are set from within the
    * program while it is running).
    */
   String index[];
   /**
    * Value for a particular parameter(These parameters are set from within the
    * program while it is running).
    */
   String value[];
   short indextot;
   static boolean isrewardgame;
   /**
    * Contains the list of words currently being used to play games.
    */
   public word[] w;
   public sentence[] s;
   public int sentDistractorNo = -1;
   public int options;
   public String topicName;
   public wordlist fullwordlist;
   /**
    * Starts the game
    * <li>Gets the game's parameters
    * <li>Alters the wordlist as specified by these parameters
    * <li>Creates a new instance of sharkGame
    * @param name Name of the game to start
    * @param ww Word list to be used
    * @param topicname1 Currently selected topic
    * @param isreward True if it is a reward game
    */
   public runningGame(String name, wordlist ww, String topicname1,boolean isreward){
      isrewardgame = isreward;
      short i,k,len;
      topicName = topicname1;
      String lists[];
      /**
       * Used to contain the parameters for a particular game. These are set from
       * within the running program.
       */
      String values[];
      String id=null;
      short overstu = sharkStartFrame.mainFrame.overrideStudent();
      gamename = name;
      fullwordlist = ww;
      boolean ispaired = false;
      if(!isreward)                                                       //If 1
        sharkStartFrame.studentList[overstu].currGame = name;   //The game is not a reward game
      lists = sharkStartFrame.searchListGame(overstu);
      outer:for(i=0;i<lists.length;++i) {                                 //For 2
         if((values = (String[])db.find(lists[i],name,db.GAME)) != null) { //If 2.1
            index = new String[values.length];//game's parameters are found (lists[i])
            value = new String[values.length];
            indextot = 0;
            for(k = 0;k<values.length;++k) {                              //For 2.1.1
               len = (short)values[k].indexOf("=");     //Iterate through parameters in the game
               if(len >= 0) {                                              //If 2.1.1.1
                  index[indextot] = values[k].substring(0,len);     //There is an "=" sign
                  value[indextot++] = values[k].substring(len+1);
               }
               else {                                                    //Else 2.1.1.1
                  index[indextot] = values[k];                       //There is not an "=" sign
                  value[indextot++] = "";
               }
            }
            id = getParm("id");
            if(id == null) {                                               //If 2.1.2
//startPR2004-12-01^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                  if(!Demo_base.isDemo){
                    u.okmess( // There is no "id" parameter
                                u.gettext("game_", "heading"), u.gettext("game_", "noid", name),
                                sharkStartFrame.mainFrame);
                                }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                return ;
            }
            else {                                   //Else 2.1.2 - There is an "id" parameter
               try {                                                      //Try 2.1.2.1
                  gameClass = Class.forName("shark.games."+id);
               }
               catch(ClassNotFoundException e) {                        //catch 2.1.2.1
                  try {                                                   //Try 2.1.2.2
                     gameClass = Class.forName("shark."+id);
                  }
                  catch(ClassNotFoundException e2) {                    //catch 2.1.2.2
//startPR2004-12-01^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                    if(!Demo_base.isDemo){
                      u.okmess( // There is no "id" parameter
                      u.gettext("game_", "heading"),
                      u.gettext("game_","notonsys",name+"(id="+id+")"),
                      sharkStartFrame.mainFrame);
                      }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR

                     return ;
                  }
               }
                break outer;
             }
          }
      }
//      if((shortgamename = getParm("icontitle")) == null) {                 //If 3
//        shortgamename = gamename;                           //-There is an "icontitle" parameter
//      }
      if(getParm("needsentences3") != null && !sharkStartFrame.currPlayTopic.phrases) {
         w = u.shuffle(ww.getphrases(sharkStartFrame.currPlayTopic.getSpecials(new String[]{topic.sentencegames2[2]})));
         sentDistractorNo = sharkStartFrame.currPlayTopic.sentenceDistractorNo;
         if(getParm("needpictures") != null){
             for(i=0;i<w.length;++i) {
                if(w[i].picture == null) {w = u.removeword(w,i); --i;}
             }
         }
      }
      else if(getParm("sentences") != null) {          //If 4-There is a "sentences" parameter
          s = ww.getsentences(sharkStartFrame.currPlayTopic.getSpecials(new String[]{name}));
          sentDistractorNo = sharkStartFrame.currPlayTopic.sentenceDistractorNo;
      }
      else if(getParm("usepattern") == null && getParm("useheadings")==null) {//Else if 4
         if(getParm("usebadwords") != null){                               //If 4.1
           w = ww.getBadList();
         }
         else if(getParm("pairedwords") != null){                     //Else if 4.1
           w = ww.getPairedList(gamename);
           ispaired = true;
         }
         else if(getParm("nosamespell") != null){                     //Else if 4.1
           w = ww.getNoSameSpell();
         }
         else {
           if(getParm("nosinglesoundwords") != null && (sharkStartFrame.currPlayTopic != null && sharkStartFrame.currPlayTopic.phonicsw)){                 //Else if 4.1
             w = ww.getNoSingles();
           }
           else if(getParm("nosingleletters") != null){                 //Else if 4.1
             w = ww.getNoSingles();
           }
          else     {
             w = ww.getList();
             if(sharkStartFrame.currPlayTopic != null  // only allow 1 of 'sayfull' sounds if not singlesound
                && sharkStartFrame.currPlayTopic.phonics && !sharkStartFrame.currPlayTopic.phonicsw
                && !sharkStartFrame.currPlayTopic.singlesound) {
                  String gotfullsay = null;
                  for(i=0;i<w.length;++i) {
                    String ph1[] = w[i].phonics();
                    if (ph1.length > 0) {
                      String ph = ph1[0];
                      if (u.findString(spokenWord.sayfull, ph) >= 0) {
                        if (gotfullsay != null) {
                          if (!gotfullsay.equals(ph)) {
                            word ret2[] = new word[w.length - 1];
                            System.arraycopy(w, 0, ret2, 0, i);
                            if (i < ret2.length)
                              System.arraycopy(w, i + 1, ret2, i, ret2.length - i);
                            w = ret2;
                            --i;
                          }
                        }
                        else
                          gotfullsay = ph;
                      }
                    }
                  }
             }
             if(getParm("needpictures") != null){                                 //If 4.3
               for(i=0;i<w.length;++i) {                                     //For 4.3.1
                  if(w[i].bad || !sharkImage.exists(w[i].vpic(), sharkStartFrame.currPlayTopic)) {
                       word ret2[] = new word[w.length - 1];                   //If 4.3.1.1
                       System.arraycopy(w,0,ret2,0,i);
                       if(i<ret2.length)                                      //If 4.3.1.1.1
                         System.arraycopy(w, i + 1, ret2, i, ret2.length - i);
                       w = ret2;
                       --i;
                   }
                }
                if(w.length<4) {
                  word[] extras  = sharkStartFrame.currPlayTopic.getAllWords(false);
                  outer: for (i = 0; i < extras.length && w.length < 4; ++i) { //For 4.3.1
                    if (!extras[i].bad && sharkImage.exists(extras[i].vpic(), sharkStartFrame.currPlayTopic)
                         && (!sharkStartFrame.currPlayTopic.phonicsw || !ww.usephonics || extras[i].value.indexOf('=') >= 0)) {
                       for(int j=0;j<w.length;++j) {
                          if(w[j].v().equals(extras[i].v())) continue outer;
                       }
                       w = u.addWords(w,extras[i]);
                    }
                  }
                }
               w = u.shuffle(w);
            }
           }
           if(w.length <= 4 && w.length > 0 && getParm("notifdups") == null)
                 w = u.augmentlist(w,
                      getParm("spelling")!=null && !(ww.usephonics && sharkStartFrame.currPlayTopic.singlesound));
         }
         if(!sharkStartFrame.currPlayTopic.inorder && !ispaired) w = u.shuffle(w);
         if(getParm("needonset") != null){                                 //If 4.3
            for(i=0;i<w.length;++i) {                                     //For 4.3.1
 //              if (w[i].v().length()==1 || u.vowels.indexOf(w[i].v().charAt(0)) >= 0) {
               if (w[i].v().length()==1 || u.vowels.indexOf(w[i].v().charAt(0)) >= 0 || u.syllabletot(w[i].v())!=1) {
                   word ret2[] = new word[w.length - 1];                   //If 4.3.1.1
                   System.arraycopy(w,0,ret2,0,i);
                   if(i<ret2.length)                                      //If 4.3.1.1.1
                     System.arraycopy(w, i + 1, ret2, i, ret2.length - i);
                   w = ret2;
                   --i;
               }
            }
         }
         if(w.length == 0 && !isreward) {
                u.okmess(
            u.gettext("game_","heading"),
//startPR2004-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//            u.gettext("game_","nowords",name));
                    u.gettext("game_","nowords",name), sharkStartFrame.mainFrame);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            return ;
         }
      }
      
      // take out words that have no recordings - though needed.
      if(sharkStartFrame.currPlayTopic.ownlist && !isreward){
          sharkStartFrame.gameflag ff = sharkStartFrame.gameflag.get(gamename);
          if(ff!=null && ff.owlneedrec){
              String wordswithrecs[] = new String[]{};
              boolean specialprivateon = (wordlist.usetranslations &&
                    sharkStartFrame.currPlayTopic.translations) || (wordlist.usedefinitions && sharkStartFrame.currPlayTopic.definitions);
              if(!specialprivateon){
                String publicrecs[] = new String[]{};
                for(int n = 0; n < sharkStartFrame.publicSoundLib.length; n++){
//                    publicrecs = u.addString(publicrecs, db.list(sharkStartFrame.publicSoundLib[0], db.WAV));
                    publicrecs = u.addString(publicrecs, db.list(sharkStartFrame.getPrimarySoundDb(sharkStartFrame.publicSoundLib), db.WAV));
                }                     
                loop1:for(i = 0; i < w.length; i++){
                    if(u.findString(publicrecs, w[i].v())>=0){
                        wordswithrecs = u.addString(wordswithrecs, w[i].v());
                    }
                    else{
                        for(int n = 0; sharkStartFrame.currPlayTopic.recs!=null && n< sharkStartFrame.currPlayTopic.recs.length; n++){
                            if(sharkStartFrame.currPlayTopic.recs[n] != null && sharkStartFrame.currPlayTopic.recs[n].name.equals(w[i].v())){
                                wordswithrecs = u.addString(wordswithrecs, w[i].v());
                                continue loop1;
                            }
                        }
                    }
                }
              }
              else{
                  loop1:for(i = 0; i < w.length; i++){
//                      String s = ((jnode)sharkStartFrame.currPlayTopic.root.getChildAt(i)).get();
                      for(int n = 0; sharkStartFrame.currPlayTopic.xrecs!=null && n< sharkStartFrame.currPlayTopic.xrecs.length; n++){
                         if(sharkStartFrame.currPlayTopic.xrecs[n] != null && sharkStartFrame.currPlayTopic.xrecs[n].name.equals(w[i].v())){
                            wordswithrecs = u.addString(wordswithrecs, w[i].v());
                            continue loop1;
                         }
                      }
                   }
              }

              for(i=0;i<w.length;++i) {                                     //For 4.3.1
                  if(u.findString(wordswithrecs, w[i].v())<0){
                      word ret2[] = new word[w.length - 1];                   //If 4.3.1.1
                         System.arraycopy(w,0,ret2,0,i);
                         if(i<ret2.length)                                      //If 4.3.1.1.1
                           System.arraycopy(w, i + 1, ret2, i, ret2.length - i);
                         w = ret2;
                         --i;
                  }
              }
          }
      }
      options = ww.paintOptions();
      if(!sharkStartFrame.currPlayTopic.inorder)                                                     //If 5
        options |= word.SHUFFLED;
      try {                                                               //Try 6
          if((sharkStartFrame.currPlayTopic.definitions && wordlist.usedefinitions) ||
           (sharkStartFrame.currPlayTopic.translations &&  wordlist.usetranslations)){
              for(int n = 0; n < w.length; n++){
                  w[n].spokenword = null;
              }
          }
         currGameRunner = this;
//startPR2008-06-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         // set blank menu bar for Macs during games
         if(shark.macOS){
           sharkStartFrame.mainFrame.setJMenuBar(new javax.swing.JMenuBar());
         }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         game = (sharkGame) gameClass.newInstance();
         if(game.stop) return;
         game.isreward = isreward;
//         if(!isreward){
//            if(sharkGame.lastEducationalGame==null){
//                sharkGame.lastEducationalGame = new ArrayList();
//                sharkGame.lastEducationalGame.add(name);
//                sharkGame.lastEducationalGame.add(ww);
//                sharkGame.lastEducationalGame.add(topicname1);
//                sharkGame.lastEducationalGame.add(new Integer(-1));
//            }
//            else{
//                if(!((String)sharkGame.lastEducationalGame.get(0)).equals(name))
//                  sharkGame.lastEducationalGame.set(3,new Integer(-1));
//                sharkGame.lastEducationalGame.set(0, name);
//                sharkGame.lastEducationalGame.set(1, ww);
//                sharkGame.lastEducationalGame.set(2, topicname1);
//            }
//         }
         ++sharkStartFrame.gametot;
      }
      catch (InstantiationException e) {                                //Catch 6
         u.okmess(
          u.gettext("game_","heading"),
//startPR2004-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//          u.gettext("game_","invclass",name+"(id="+id+")"));
             u.gettext("game_","invclass",name+"(id="+id+")"), sharkStartFrame.mainFrame);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         return ;
      }
      catch (IllegalAccessException e) {                                //Catch 6
          u.okmess(
            "Request for game",
//startPR2004-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//            "Game <"+name+"> (id="+id+") access denied");
                  "Game <"+name+"> (id="+id+") access denied", sharkStartFrame.mainFrame);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         return ;
      }
      return ;
   }
   /**
    * Returns the value associated with the parameter name. These parameters are
    * set from within the program when it is running.
    * @param s Parameter name that is to be found
    * @return The value for the name if it is found or else null
    */
   public String getParm(String s) {
      for(short i=0 ; i<indextot; ++i) {
         if(index[i].equalsIgnoreCase(s)) return value[i] ;
      }
      return null;
   }
}
