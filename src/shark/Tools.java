/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package shark;


import javax.swing.tree.*;
import shark.program.programitem;
import java.util.*;
import java.io.*;
import org.apache.commons.io.FileUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author MacBook Air
 */
public class Tools {
    String[] allTopicPathsForSetWork;
    saveTree1.saveTree2 nt[];
    
    
    public void getSyllables(){
        String ss[] = u.readFile("C:\\jshark-shared2\\publictopicsPrint\\words.txt");
        
        
        for(int i = 0; i < ss.length; i++){
          System.out.print(  String.valueOf(u.syllabletot(ss[i])));
        }
        
        int g;
         g = 0;
        
    }
    
    
    
    public void adjustPublicSplits(){
        String ss[] = db.list(sharkStartFrame.publicSplitsLib[0], db.TEXT);
        String remove[] = new String[]{"(19) List for years 3 & 4 - suffixes","alternatives to -tion 2","building words 1c","ch as /k/ and /sh/ needs update","ei ey sounding /ay/ needs update"};
        String renameOld[] = new String[]{"building words 2c","building words 3c","building words 4c","building words 5c", "split digraph words with -ed"};
        String renameNew[]= new String[]{"doubles 2","doubles 3","doubles 4","doubles 5","split digraphs with -ed"};
        
        for(int i = 0; i < remove.length; i++){
            db.delete(sharkStartFrame.publicSplitsLib[0], remove[i], db.TEXT);
        }
        for(int i = 0; i < renameOld.length; i++){
            db.rename(sharkStartFrame.publicSplitsLib[0], renameOld[i], renameNew[i], db.TEXT);
        }
    }      
    
    
    public void publicSplitsContents(){
        String ss[] = db.list(sharkStartFrame.publicSplitsLib[0], db.TEXT);
        for(int i = 0; i < ss.length; i++){
            Object o  = db.find(sharkStartFrame.publicSplitsLib[0], ss[i], db.TEXT);
            
            try{
                String s2[] = (String [])o;
                if(db.find("publictopics", ss[i], db.TOPIC)==null){
                       System.out.println("*******************************************************************NOT FOUND"); 
                }
                System.out.println(ss[i]);
                for(int k = 0; k < s2.length; k++){
                   System.out.println(s2[k]);
                }
                System.out.println("  ");
            }catch(Exception ee){

            }
        }
    }    
    
    public void noOfWordListsPerUnit(String targetCourse){
        topicTree topics = new topicTree();
        String alltopics = u.gettext("simprog_all","alltopics");
        topics.onlyOneDatabase = "*";
        topics.dbnames = new String[0]; // we need dbnames for revision list generation
        topics.setEditable(false);
        topics.getSelectionModel().setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
        topics.root.setIcon(jnode.ROOTTOPICTREE);
        topics.setRootVisible(false);
        topics.setup(new String[]{"publictopics"},false,db.TOPICTREE,true,alltopics);
        jnode jnCourse = topics.find(targetCourse);
        jnode units[] = jnCourse.getChildren();
        int totTopics = 0;
        for(int i = 0; i < units.length; i++){
            totTopics += units[i].getChildCount();
        }
        System.out.println(String.valueOf((float)((float)totTopics/(float)units.length)));
    }     
    
    
    /*
    Print out a list of word list names which are present in targetCourse, but
    which do not appear in excludedCourses
    */
    public void listWordLists(String targetCourse, String excludedCourses[]){
        topicTree topics = new topicTree();
        String alltopics = u.gettext("simprog_all","alltopics");
        topics.onlyOneDatabase = "*";
        topics.dbnames = new String[0]; // we need dbnames for revision list generation
        topics.setEditable(false);
        topics.getSelectionModel().setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
        topics.root.setIcon(jnode.ROOTTOPICTREE);
        topics.setRootVisible(false);
        topics.setup(new String[]{"publictopics"},false,db.TOPICTREE,true,alltopics);
        jnode jnCourse = topics.find(targetCourse);
        if(jnCourse.getLevel()!=1){
            u.okmess("problem with getting target course", sharkStartFrame.mainFrame);
            return;
        }
        topic tempt[] = topics.getTopics(jnCourse);
        String targetTopicNames[] = new String[]{};
        for(int i = 0; i < tempt.length; i++){
            if(u.findString(targetTopicNames, tempt[i].name)<0){
                targetTopicNames = u.addString(targetTopicNames, tempt[i].name);
            }
        }
        String exclusionTopics[] = new String[]{};
        for(int i = 0; i < excludedCourses.length; i++){
            jnode jnExclusion = topics.find(excludedCourses[i]);
            if(jnCourse.getLevel()!=1){
                u.okmess("problem with getting exclusion courses", sharkStartFrame.mainFrame);
                return;
            }            
            tempt = topics.getTopics(jnExclusion);
            for(int k = 0; k < tempt.length; k++){
                if(u.findString(exclusionTopics, tempt[k].name)<0){
                    exclusionTopics = u.addString(exclusionTopics, tempt[k].name);
                }
            }            
        }
        String out[] = new String[]{};
        for(int i = 0; i < targetTopicNames.length; i++){
            if(u.findString(exclusionTopics, targetTopicNames[i])>=0)continue;
            out = u.addString(out, targetTopicNames[i]);
        }
        u2_base.writeFile("C:\\Users\\paulr\\Documents\\out.txt", out);
        int g;
         g=  0;
        
//        topics.getTopics(jnCourse)

        
        /*
        nt = new saveTree1.saveTree2[]{};
        searchTree(topics, topics.root);
        program.saveprogram sp = new  program.saveprogram();
//        sp.version = "";  ??
        sp.teacher = "dummy";
        sp.it = new programitem[1];
        sp.it[0] = new programitem();
        sp.it[0].topics = allTopicPathsForSetWork;
        sp.it[0].games = new String[0];
        sp.it[0].mixed = new boolean[0];
        sp.it[0].trees = nt;
        db.update(user,"dummywork",sp,db.PROGRAM);        
        */
    }    
    
    
    public void checkExcludedWords(){
        String ss[] = db.list("publictopics", db.TEXT);
        for(int i = 0; i < ss.length; i++){
            Object o = db.find("publictopics", ss[i], db.TEXT);
            String sss[][] = null;
            if(o instanceof String[][]){
                sss = (String[][])db.find("publictopics", ss[i], db.TEXT);
            }

            int g;
             g = 0;
        }
    }
    
    public void moveWSImages(){
        String ss[] = u.readFile("C:\\Users\\paulr\\Documents\\NetBeansProjects\\jbproject6_3\\allwspublicimage.txt");
        ToolsOnlineResources tor = new ToolsOnlineResources();
        
        for(int i = 0; i < ss.length; i++){
            ss[i] = tor.charToAscii(ss[i]);
        }
        String src = "E:\\Shark Screen Shots\\outputpublicimageSENT";
        String dest = "E:\\Shark Screen Shots\\outputpublicimage";
        String ssf[] = new File(src).list();
        for(int i = 0; i < ssf.length; i++){
            String s = ssf[i].substring(0, ssf[i].lastIndexOf("."));
            try{
                Integer.parseInt(s.substring(s.length() - 4));
                s = s.substring(0, s.length() - 5);
            }
            catch(Exception ee){
                
            }
            if(u.findString(ss, s)>=0){
                try{
                    FileUtils.moveFile(new File(src+"\\"+ssf[i]), new File(dest+"\\"+ssf[i]));
                }
                catch(Exception ed){
                    int k;
                    k = 0;
                }
            }

        }  
    }  
    
    public void checkFinalWSImages(){
        String ss[] = u.readFile("E:\\Shark Screen Shots\\all.txt");
        String sout = "E:\\Shark Screen Shots\\output\\";
  //      String checkfor[] = new String[]{"cheese^.png","claw.gif","dogs.gif","france^.png","healthy^.png","lesson^schoolwork.png","mouth.gif","sundays only@@.png","yes go^.png"};
        String checkfor[] = new String[]{"cheese^","claw","dogs","france^","healthy^","lesson^schoolwork","mouth","sundays only@@","yes go^"};
        ToolsOnlineResources tor = new ToolsOnlineResources();
        String file[] = new String[]{};
        for(int i = 0; i < ss.length; i++){
           String s = tor.charToAscii(ss[i]);
           
           if(u.findString(checkfor, s)>=0){
               System.out.println(s);
           }
            if((new File(sout+s+".gif")).exists()){
                if(u.findString(file, s+".gif")>=0){
                    int g;
                     g = 0;
                //     System.out.println(s+".gif");
                }
                file = u.addString(file, s+".gif");
                continue;
            }
            if((new File(sout+s+".png")).exists()){
                if(u.findString(file, s+".png")>=0){
                    int g;
                     g = 0;
              //       System.out.println(s+".png");
                }
                file = u.addString(file, s+".png");
                continue;
            }
            System.out.println(ss[i] + "    "  + s);
        }  
    }    
    
    public String getTopicSettingsJson(String keys[], String values[]){
        JSONObject objectmain = new JSONObject();
        for(int i = 0; i < keys.length; i++){
            objectmain.put(keys[i], values[i]);            
        }
        return objectmain.toJSONString();
    }

    
    public void saveSetWorkWithAllPossibilities(String user){
        topicTree topics = new topicTree();
        String alltopics = u.gettext("simprog_all","alltopics");
        topics.onlyOneDatabase = "*";
        topics.dbnames = new String[0]; // we need dbnames for revision list generation
        topics.setEditable(false);
        topics.getSelectionModel().setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
        topics.root.setIcon(jnode.ROOTTOPICTREE);
        topics.setRootVisible(false);
        topics.setup(new String[]{sharkStartFrame.publicV4TopicsLib},false,db.TOPICTREE,true,alltopics);
        allTopicPathsForSetWork = new String[]{};
        nt = new saveTree1.saveTree2[]{};
        searchTree(topics, topics.root);
        program.saveprogram sp = new  program.saveprogram();
//        sp.version = "";  ??
        sp.teacher = "dummy";
        sp.it = new programitem[1];
        sp.it[0] = new programitem();
        sp.it[0].topics = allTopicPathsForSetWork;
        sp.it[0].games = new String[0];
        sp.it[0].mixed = new boolean[0];
        sp.it[0].trees = nt;
        db.update(user,"dummywork",sp,db.PROGRAM);
    }
    
    
    public void testSounds(){
    
    
    
    for (int n = 0 ; n < sharkStartFrame.publicSoundLib.length; n++){
        String dblist[] = db.list(sharkStartFrame.publicSoundLib[n], db.WAV);
        for (int k = 0 ; k < dblist.length; k++){
            spokenWord currSpokenWord = new spokenWord(dblist[k], sharkStartFrame.publicSoundLib[n] ,false);
            currSpokenWord.decomp();
            if(currSpokenWord.data == null || currSpokenWord.data.length==0){
                System.out.println(sharkStartFrame.publicSoundLib[n] + "   " + dblist[k]);
            }
        }
    }
    for (int n = 0 ; n < sharkStartFrame.publicSentLib.length; n++){
        String dblist[] = db.list(sharkStartFrame.publicSentLib[n], db.WAV);
        for (int k = 0 ; k < dblist.length; k++){
            spokenWord currSpokenWord = new spokenWord(dblist[k], sharkStartFrame.publicSentLib[n] ,false);
            currSpokenWord.decomp();
            if(currSpokenWord.data == null || currSpokenWord.data.length==0){
                System.out.println(sharkStartFrame.publicSentLib[n] + "   " + dblist[k]);
            }
        }        
    }
    for (int n = 0 ; n < sharkStartFrame.publicSent2Lib.length; n++){
        String dblist[] = db.list(sharkStartFrame.publicSent2Lib[n], db.WAV);
        for (int k = 0 ; k < dblist.length; k++){
            spokenWord currSpokenWord = new spokenWord(dblist[k], sharkStartFrame.publicSent2Lib[n] ,false);
            currSpokenWord.decomp();
            if(currSpokenWord.data == null || currSpokenWord.data.length==0){
                System.out.println(sharkStartFrame.publicSent2Lib[n] + "   " + dblist[k]);
            }
        }          
    }
    for (int n = 0 ; n < sharkStartFrame.publicSent3Lib.length; n++){
        String dblist[] = db.list(sharkStartFrame.publicSent3Lib[n], db.WAV);
        for (int k = 0 ; k < dblist.length; k++){
            spokenWord currSpokenWord = new spokenWord(dblist[k], sharkStartFrame.publicSent3Lib[n] ,false);
            currSpokenWord.decomp();
            if(currSpokenWord.data == null || currSpokenWord.data.length==0){
                System.out.println(sharkStartFrame.publicSent3Lib[n] + "   " + dblist[k]);
            }
        }          
    }
    for (int n = 0 ; n < sharkStartFrame.publicSent4Lib.length; n++){
        String dblist[] = db.list(sharkStartFrame.publicSent4Lib[n], db.WAV);
        for (int k = 0 ; k < dblist.length; k++){
            spokenWord currSpokenWord = new spokenWord(dblist[k], sharkStartFrame.publicSent4Lib[n] ,false);
            currSpokenWord.decomp();
            if(currSpokenWord.data == null || currSpokenWord.data.length==0){
                System.out.println(sharkStartFrame.publicSent4Lib[n] + "   " + dblist[k]);
            }
        }          
    }    
    
    }      
    
    
    public void makeWordExclusionPublicFile(){
            int EXLUSION_COURSE = 0;
    int EXLUSION_TOPIC = 1;
      int EXLUSION_WORD = 2;    
 String EXLUSION_WSCOURSE = "Wordshark course";
     String EXCLUSIONS[][] = new String[][]{
new String[]{EXLUSION_WSCOURSE, "introduce blends", "cap"},
new String[]{EXLUSION_WSCOURSE, "introduce blends", "cab"},
new String[]{EXLUSION_WSCOURSE, "introduce blends", "dip"},
new String[]{EXLUSION_WSCOURSE, "introduce blends", "fat"},
new String[]{EXLUSION_WSCOURSE, "introduce blends", "fog"},
new String[]{EXLUSION_WSCOURSE, "introduce blends 2", "pan"},
new String[]{EXLUSION_WSCOURSE, "introduce blends 2", "pod"},
new String[]{EXLUSION_WSCOURSE, "introduce blends 2", "sick"},
new String[]{EXLUSION_WSCOURSE, "introduce blends 2", "tin"},
new String[]{EXLUSION_WSCOURSE, "-nd", "and"},
new String[]{EXLUSION_WSCOURSE, "-ing", "king"},
new String[]{EXLUSION_WSCOURSE, "-ng", "king"},
new String[]{EXLUSION_WSCOURSE, "longer words - adding -ed", "jump"},
new String[]{EXLUSION_WSCOURSE, "longer words - adding -ed", "help"},
new String[]{EXLUSION_WSCOURSE, "longer words - adding -ed", "wish"},
new String[]{EXLUSION_WSCOURSE, "longer words - adding -ed", "land"},
new String[]{EXLUSION_WSCOURSE, "longer words - adding -ed", "end"},
new String[]{EXLUSION_WSCOURSE, "-ee -eep -eet", "see"},
new String[]{EXLUSION_WSCOURSE, "-ee -eep -eet", "tree"},
new String[]{EXLUSION_WSCOURSE, "-ee- mixed", "week"},
new String[]{EXLUSION_WSCOURSE, "-oo- as in 'book'  (southern English)", "book"},
new String[]{EXLUSION_WSCOURSE, "-oo- as in 'book'  (southern English)", "look"},
new String[]{EXLUSION_WSCOURSE, "-oo- as in 'book'  (southern English)", "good"},
new String[]{EXLUSION_WSCOURSE, "-ar-", "car"},
new String[]{EXLUSION_WSCOURSE, "-or-", "for"},
new String[]{EXLUSION_WSCOURSE, "-ir-", "girl"},
new String[]{EXLUSION_WSCOURSE, "wor-", "word"},
new String[]{EXLUSION_WSCOURSE, "a-e (adding e)", "mat"},
new String[]{EXLUSION_WSCOURSE, "a-e (adding e)", "fat"},
new String[]{EXLUSION_WSCOURSE, "a-e (adding e)", "tap"},
new String[]{EXLUSION_WSCOURSE, "i-e (adding e)", "hid"},
new String[]{EXLUSION_WSCOURSE, "i-e (adding e)", "kit"},
new String[]{EXLUSION_WSCOURSE, "i-e (adding e)", "bit"},
new String[]{EXLUSION_WSCOURSE, "-ice", "ice"},
new String[]{EXLUSION_WSCOURSE, "o-e (adding e)", "hop"},
new String[]{EXLUSION_WSCOURSE, "o-e (adding e)", "rod"},
new String[]{EXLUSION_WSCOURSE, "o-e (adding e)", "not"},
new String[]{EXLUSION_WSCOURSE, "o-e mixed", "home"},
new String[]{EXLUSION_WSCOURSE, "-ain -ail", "train"},
new String[]{EXLUSION_WSCOURSE, "-ain -ail", "rain"},
new String[]{EXLUSION_WSCOURSE, "final -ay", "day"},
new String[]{EXLUSION_WSCOURSE, "final -ay", "way"},
new String[]{EXLUSION_WSCOURSE, "final -ay", "play"},
new String[]{EXLUSION_WSCOURSE, "final -ay", "say"},
new String[]{EXLUSION_WSCOURSE, "-ee- mixed", "week"},
new String[]{EXLUSION_WSCOURSE, "-ee- mixed", "feel"},
new String[]{EXLUSION_WSCOURSE, "-ee- mixed", "feet"},
new String[]{EXLUSION_WSCOURSE, "-eam -eat", "team"},
new String[]{EXLUSION_WSCOURSE, "-ea- mixed", "red"},
new String[]{EXLUSION_WSCOURSE, "ee/ea", "tree"},
new String[]{EXLUSION_WSCOURSE, "ee/ea", "feel"},
new String[]{EXLUSION_WSCOURSE, "-igh  -ight", "high"},
new String[]{EXLUSION_WSCOURSE, "-igh  -ight", "night"},
new String[]{EXLUSION_WSCOURSE, "-igh  -ight", "light"},
new String[]{EXLUSION_WSCOURSE, "final -y as long i", "my"},
new String[]{EXLUSION_WSCOURSE, "final -y as long i", "by"},
new String[]{EXLUSION_WSCOURSE, "final -y as long i", "fly"},
new String[]{EXLUSION_WSCOURSE, "igh, i-e, y mixed", "bike"},
new String[]{EXLUSION_WSCOURSE, "-oa- mixed", "boat"},
new String[]{EXLUSION_WSCOURSE, "-oa- mixed", "road"},
new String[]{EXLUSION_WSCOURSE, "final -ow as long o", "snow"},
new String[]{EXLUSION_WSCOURSE, "final -ow as long o", "grow"},
new String[]{EXLUSION_WSCOURSE, "final -ow as long o", "low"},
new String[]{EXLUSION_WSCOURSE, "final  -ow  -own", "show"},
new String[]{EXLUSION_WSCOURSE, "final  -ow  -own", "blow"},
new String[]{EXLUSION_WSCOURSE, "final  -ow  -own", "grow"},
new String[]{EXLUSION_WSCOURSE, "final  -ow  -own", "throw"},
new String[]{EXLUSION_WSCOURSE, "oa/ow", "boat"},
new String[]{EXLUSION_WSCOURSE, "oa/ow", "low"},
new String[]{EXLUSION_WSCOURSE, "oa, ow, o-e mixed", "boat"},
new String[]{EXLUSION_WSCOURSE, "oa, ow, o-e mixed", "coat"},
new String[]{EXLUSION_WSCOURSE, "final -ew", "new"},
new String[]{EXLUSION_WSCOURSE, "final -ew", "few"},
new String[]{EXLUSION_WSCOURSE, "ue, ew, u-e mixed", "clue"},
new String[]{EXLUSION_WSCOURSE, "ue, ew, u-e mixed", "true"},
new String[]{EXLUSION_WSCOURSE, "ue, ew, u-e mixed", "blue"},
new String[]{EXLUSION_WSCOURSE, "ue, ew, u-e mixed", "few"},
new String[]{EXLUSION_WSCOURSE, "-oo- as in 'food'", "food"},
new String[]{EXLUSION_WSCOURSE, "-oo- as in 'food'", "moon"},
new String[]{EXLUSION_WSCOURSE, "long/short vowel - choose k or ck", "back"},
new String[]{EXLUSION_WSCOURSE, "long/short vowel - choose k or ck", "clock"},
new String[]{EXLUSION_WSCOURSE, "o sounding /u/", "come"},
new String[]{EXLUSION_WSCOURSE, "ea as in 'head'", "head"},
new String[]{EXLUSION_WSCOURSE, "-rr- after short vowel", "carry"},
new String[]{EXLUSION_WSCOURSE, "-ear - 2 alternative sounds", "ear"},
new String[]{EXLUSION_WSCOURSE, "-ear - 2 alternative sounds", "dear"},
new String[]{EXLUSION_WSCOURSE, "-ear - 2 alternative sounds", "year"},
new String[]{EXLUSION_WSCOURSE, "-air", "air"},
new String[]{EXLUSION_WSCOURSE, "-air", "hair"},
new String[]{EXLUSION_WSCOURSE, "-air", "chair"},
new String[]{EXLUSION_WSCOURSE, "ou", "out"},
new String[]{EXLUSION_WSCOURSE, "ou", "found"},
new String[]{EXLUSION_WSCOURSE, "-ow- as in 'how'", "cow"},
new String[]{EXLUSION_WSCOURSE, "-ow- as in 'how'", "how"},
new String[]{EXLUSION_WSCOURSE, "-ow- as in 'how'", "now"},
new String[]{EXLUSION_WSCOURSE, "-ow- as in 'how'", "town"},
new String[]{EXLUSION_WSCOURSE, "-ow- as in 'how'", "down"},
new String[]{EXLUSION_WSCOURSE, "-oi-", "oil"},
new String[]{EXLUSION_WSCOURSE, "-oi-", "boil"},
new String[]{EXLUSION_WSCOURSE, "-oi-", "coin"},
new String[]{EXLUSION_WSCOURSE, "-oi-", "join"},
new String[]{EXLUSION_WSCOURSE, "oi/oy", "boy"},
new String[]{EXLUSION_WSCOURSE, "oi/oy", "toy"},
new String[]{EXLUSION_WSCOURSE, "prefix a-", "above"},
new String[]{EXLUSION_WSCOURSE, "prefix a-", "about"},
new String[]{EXLUSION_WSCOURSE, "prefix a-", "away"},
new String[]{EXLUSION_WSCOURSE, "plurals -es", "boxes"},
new String[]{EXLUSION_WSCOURSE, "plurals -es", "wishes"},
new String[]{EXLUSION_WSCOURSE, "-le 1", "apple"},
new String[]{EXLUSION_WSCOURSE, "-le 3", "table"},
new String[]{EXLUSION_WSCOURSE, "-aw", "saw"},
new String[]{EXLUSION_WSCOURSE, "-aw", "draw"},
new String[]{EXLUSION_WSCOURSE, "-ear- 3 alternative sounds", "hear"},
new String[]{EXLUSION_WSCOURSE, "-ear- 3 alternative sounds", "earn"},
new String[]{EXLUSION_WSCOURSE, "-ear- 3 alternative sounds", "learn"},
new String[]{EXLUSION_WSCOURSE, "-ear- 3 alternative sounds", "earth"},
new String[]{EXLUSION_WSCOURSE, "ei ey sounding /ay/", "eight"},
new String[]{EXLUSION_WSCOURSE, "wr- silent w", "write"},
new String[]{EXLUSION_WSCOURSE, "introduce contractions/short forms", "I am"},
new String[]{EXLUSION_WSCOURSE, "introduce contractions/short forms", "I will"},
new String[]{EXLUSION_WSCOURSE, "introduce contractions/short forms", "do not"},
new String[]{EXLUSION_WSCOURSE, "introduce contractions/short forms", "cannot"},
new String[]{EXLUSION_WSCOURSE, "150 homophones - see extended list", "see"},
new String[]{EXLUSION_WSCOURSE, "150 homophones - see extended list", "sea"},
new String[]{EXLUSION_WSCOURSE, "change y to i rule before adding suffix", "cry"},
new String[]{EXLUSION_WSCOURSE, "change y to i rule before adding suffix", "story"},
new String[]{EXLUSION_WSCOURSE, "change y to i rule before adding suffix", "silly"},
new String[]{EXLUSION_WSCOURSE, "change y to i rule before adding suffix", "hungry"},
new String[]{EXLUSION_WSCOURSE, "suffix  -ed -ing drop e", "hope"},
new String[]{EXLUSION_WSCOURSE, "suffix  -ed -ing drop e", "smile"},
new String[]{EXLUSION_WSCOURSE, "suffix y drop e", "smoke"},
new String[]{EXLUSION_WSCOURSE, "suffix y drop e", "bone"},
new String[]{EXLUSION_WSCOURSE, "suffix y drop e", "stone"},
new String[]{EXLUSION_WSCOURSE, "suffix y drop e", "grease"},
new String[]{EXLUSION_WSCOURSE, "suffix -ed -ing -y with doubling", "hop"},
new String[]{EXLUSION_WSCOURSE, "suffix -ed -ing -y with doubling", "plan"},
new String[]{EXLUSION_WSCOURSE, "suffix -ed -ing -y with doubling", "mud"},
new String[]{EXLUSION_WSCOURSE, "-ture", "picture"},
new String[]{EXLUSION_WSCOURSE, "-ture", "adventure"},
new String[]{EXLUSION_WSCOURSE, "-tion 2", "information"},
new String[]{EXLUSION_WSCOURSE, "-ous", "famous"},
new String[]{EXLUSION_WSCOURSE, "ph as /f/", "photo"},
new String[]{EXLUSION_WSCOURSE, "ph as /f/", "phone"},
new String[]{EXLUSION_WSCOURSE, "ph as /f/", "alphabet"},
     };
     
         db.create(sharkStartFrame.publicPathplus + "publicapexcludedwords");
 /*
        for(int i = 0; i < EXCLUSIONS.length; i++){
            if(EXCLUSIONS[i][EXLUSION_COURSE].equals(course)){
                if(EXCLUSIONS[i][EXLUSION_TOPIC].equals(topic)){
                    if(EXCLUSIONS[i][EXLUSION_WORD].equals(word)){
                        return true;
                    }    
                }                
            }
        } 
 */
     
    }
    
    public void findPublicSplitTopicErrors(){
        
        String ss[] = (String[])db.list("publicsplits", db.TEXT);
        String ss2[] = new String[]{};
        outer: for(int i = 0; i < ss.length; i++){
            if(ss[i].indexOf("startof")>=0){
                int h;
                h = 9;
            }
            Object o = db.find("publicsplits", ss[i], db.TEXT);
            if(!(o instanceof String[]))continue outer;
            String sss[] = (String[]) o;
            for(int k = 0; k < sss.length; k++){
                if(sss[k].indexOf('/')<0)continue outer;
            }
            ss2 = u.addString(ss2, ss[i]);
        }        
        ss = ss2;
        
        
        topicTree topics = new topicTree();
        topics.onlyOneDatabase = "*";
        topics.dbnames = new String[0]; // we need dbnames for revision list generation
        topics.setEditable(false);
        topics.getSelectionModel().setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
        topics.root.setIcon(jnode.ROOTTOPICTREE);
        topics.setRootVisible(false);
        topics.setup(new String[]{"publictopics"},false,db.TOPICTREE,true,"anything");        
        jnode wordsharknode = (jnode)topics.root.getChildAt(0);
        String res[] = new String[]{};
        for(int i = 0; i < ss.length; i++){
            if(topic.find(wordsharknode, ss[i])==null){
                if(u.findString(res, ss[i])<0){
                    res = u.addString(res, ss[i]);
                }
            }
        }
        int g; 
         g = 0;
         for(int i = 0; i < res.length; i++){
             System.out.println(res[i]);
         }
        
        
        /*
        topicTree topics = new topicTree();
        String alltopics = u.gettext("simprog_all","alltopics");
        topics.onlyOneDatabase = "*";
        topics.dbnames = new String[0]; // we need dbnames for revision list generation
        topics.setEditable(false);
        topics.getSelectionModel().setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
        topics.root.setIcon(jnode.ROOTTOPICTREE);
        topics.setRootVisible(false);
        topics.setup(new String[]{sharkStartFrame.publicV4TopicsLib},false,db.TOPICTREE,true,alltopics);
        allTopicPathsForSetWork = new String[]{};
        nt = new saveTree1.saveTree2[]{};
        searchTree(topics, topics.root);
        program.saveprogram sp = new  program.saveprogram();
//        sp.version = "";  ??
        sp.teacher = "p";
        sp.it = new programitem[1];
        sp.it[0] = new programitem();
        sp.it[0].topics = allTopicPathsForSetWork;
        sp.it[0].games = new String[0];
        sp.it[0].mixed = new boolean[0];
        sp.it[0].trees = nt;
        db.update(user,"dummywork",sp,db.PROGRAM);
                */
    }        
    
//------------------------------------------------------------------------------    
    
  void searchTree(topicTree tt, jnode node) {
         for(Enumeration e = node.children();e.hasMoreElements();) {
            node = (jnode)e.nextElement();
            String nval = node.get();
            String savepath = tt.savePath(node);
            if(u.findString(allTopicPathsForSetWork, savepath)<0 && !savepath.trim().equals("")){
                allTopicPathsForSetWork = u.addString(allTopicPathsForSetWork, savepath);
                nt = u.addsaveTree2(nt, (new saveTree1(tt, node)).curr);
            }
            if(nval.startsWith( String.valueOf(topicTree.ISTOPIC)))
                nval = nval.substring(1);
                if(!node.isLeaf())
                              { 
                                  searchTree(tt, node);
                              }
            }
  }
  
  
  
  
  public void removeMarkGames(){
        String wordDBName = "publictopics";
        String[] topicList = db.list(wordDBName,db.TOPIC);
        for(int i = 0; i < topicList.length; i++){
            saveTree1 trold = (saveTree1)db.find(wordDBName,topicList[i],db.TOPIC);
            boolean changed = false;
            for(int j = trold.curr.names.length-1; j >= 0; j--){
                if(trold.curr.names[j].startsWith("Mark games 2")){
//                    trold.curr.names = u.removeString(trold.curr.names, j);
//                    trold.curr.names[j] = "Mark games code:";
//                    changed = true; 
                    trold.curr.names = u.removeString(trold.curr.names, j);
                    trold.curr.levels = u.removeByte(trold.curr.levels, j);
                    changed = true; 
                }
                else if(trold.curr.names[j].startsWith("Mark games")){
                    /*
                    String target = "Mark games:";
                    if(!target.equals(trold.curr.names[j])){
                        trold.curr.names[j] = target;
                        changed = true;              
                    }
*/
                    trold.curr.names = u.removeString(trold.curr.names, j);
                    trold.curr.levels = u.removeByte(trold.curr.levels, j);
                    changed = true;  
                }
                else if(trold.curr.names[j].startsWith("Mark games code")){
                    /*
                    String target = "Mark games:";
                    if(!target.equals(trold.curr.names[j])){
                        trold.curr.names[j] = target;
                        changed = true;              
                    }
*/
                    trold.curr.names = u.removeString(trold.curr.names, j);
                    trold.curr.levels = u.removeByte(trold.curr.levels, j);
                    changed = true;  
                }
            }
            if(changed){
                db.update(wordDBName,topicList[i],trold.curr,db.TOPIC);                
            }
        }
  }
  
  
  
    public void fillMarkGamesFromFile(){
        String wordDBName = "publictopics";
        String[] topicList = db.list(wordDBName,db.TOPIC);
        String ss[] = u.readFile("C:\\Users\\paulr\\Desktop\\PUBLICTOPICS\\markgs.txt");
        for(int i = 0; i < topicList.length; i++){
            saveTree1 trold = (saveTree1)db.find(wordDBName,topicList[i],db.TOPIC);
            loop1:for(int j = 0; j < topicList.length; j++){
                String s = ss[j];
                String sl[] = u.splitString(s);
                if(sl[0].equals(trold.curr.names[0])){
                    int addAt = 0;
                    for(int k = 0; k < trold.curr.names.length; k++){
                        if(trold.curr.names[k].startsWith("\\")){
                            addAt = k;
                            break;
                        }
                    }
                    if(addAt == 0)addAt = trold.curr.names.length;
                    boolean changed = false;
                    if(!sl[1].equals("null") && !sl[2].equals("null")){
                        trold.curr.names = u.addString(trold.curr.names, sl[2], addAt);
                        trold.curr.levels = u.addByte(trold.curr.levels, (byte)1, addAt);
                        trold.curr.names = u.addString(trold.curr.names, sl[1], addAt);
                        trold.curr.levels = u.addByte(trold.curr.levels, (byte)1, addAt);
                        changed = true;
                    }
                    else if(!sl[1].equals("null")){
                        trold.curr.names = u.addString(trold.curr.names, sl[1], addAt);  // Mark games:
                        trold.curr.levels = u.addByte(trold.curr.levels, (byte)1, addAt);      
                        changed = true;
                    }
                    else if(!sl[2].equals("null")){
                        trold.curr.names = u.addString(trold.curr.names, sl[2], addAt); // Mark games code:
                        trold.curr.levels = u.addByte(trold.curr.levels, (byte)1, addAt); 
                        changed = true;
                    }
                    if(changed) db.update(wordDBName,topicList[i],trold.curr,db.TOPIC);    
                    continue loop1;
                }
            }
        }
  }
  
}


