/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shark;
import java.util.*;
import java.io.*;
import org.apache.commons.io.*;
import java.util.regex.*;
import com.csvreader.*;
import java.awt.image.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;
import java.io.IOException;
import java.awt.*;
import javax.imageio.ImageIO;
import java.io.File;

import com.mpatric.mp3agic.*;
/**
 *
 * @author paulr
 */
public class ToolsOnlineResources {
   
    /*
    Tools t = new Tools();  
    t.findPublicSplitTopicErrors();
    t.charToAscii("cdd");
    t.writePublicTopicsJson();pho
    String ss = t.findJson(fileSounds, "publicsay1", "apple");
    t.saveAllRecordings();    
    t.saveAllImages(); 
    t.doRenameSoundFiles();
    t.getSoundFilesJson();
    t.writeOutJsonRecordings(fileSounds, null, new String[]{"publicsay1", "publicsay3", "publicsent1", "publicsent2", "publicsent3"}, false, false, true);      
    t.writeOutJsonImage(fileImages, new String[]{"publicimage", "publicimageWORDS", "publicimageSENT"}, false, true); 
    t.copyFilesAddPrefix();   
    t.checkRecordings();
    t.gretchenNotUsed();
    t.gretchenNotDone();
    t.getAspectRatioExtremes();
    t.findDuplicateImageFiles();
    t.copyRenameRecordings(Tools.fileSounds, new String[]{"gamerecordings", "publicsay1", "publicsay3", "publicsent1", "publicsent2", "publicsent3"});
    t.addPrefixesToImages();
    t.amalgamateFolders();
    t.folderiseAnimations();
    t.renameSkipping();
    t.renameWithZeros();
    */

    
    
    /*
  sound files - sounds recorded but not used were removed except for publisay1:
        exam
        hello
        hello1
        hello2
        hell3
        hello4
        brilliant
        good
        score
    */
    static String  htmlext = ".html";    
       static String  csvext = ".csv";    
       static String jsonext = ".json";       
       static String  gifext = ".gif";
       static String  pngext = ".png";
       static String  txtext = ".txt";
       static String  mp3ext = ".mp3";
       static String  wavext = ".wav";
       static String  jpgext = ".jpg";
    static String strMapping = "Mapping";
    
    final String SENTTARGETTYPE = "SENTTARGETTYPE";     
    final String SENTDISTRACTORTYPE = "SENTDISTRACTORTYPE";     
    final String SENTENCESIMPLE_TYPE = "SENTENCESIMPLE";  
    final String SENTENCECLOZE_TYPE = "SENTENCECLOZE";   
    final String SENTENCEGARBAGE_TYPE = "SENTENCEGARBAGE";   
    final String SENTENCEDICTATION_TYPE = "SENTENCEDICTATION";   
    final String SENTENCEJUMBLED_TYPE = "SENTENCEJUMBLED"; 
    final String SENTENCECLOZEWITHDISTRACTORS_TYPE = "SENTENCECLOZEWITHDISTRACTORS";    
    public static String pre_word = "WORD";
    public static String pre_topicRecording = "TOPIC";
    public static String pre_phonicSyllable = "PSYL";
    public static String pre_phonicSound = "SOUND";
    public static String pre_letterName = "LNAME";
    public static String pre_homophoneExplanation = "HOMO";
    public static String pre_sentence = "SENT";
    public static String pre_sentencesimple = "SSENT";
    public static String pre_sentencetest = "TSENT";
    public static String pre_gameMessage = "GAME";
    public static String pre_nonsenseWord = "NONSE";
    public static String pre_nonWord = "NONW";
    public static String pre_sentencesimplefull = "SSENTFULL";
    public static String pre_cap = "CAP";
    public static String pre_system = "SYS";
//    public static String pre_temp = "TEMP";
    public static String pre_wordshark_images = "IWS";    
    public static String pre_photographs = "IPHOTO";   
    
    
//    static String TOBERECORDEDS3PATH= "temp/TEMP_ToBeRecorded.mp3";
    
    
    public static String[] maintainCasePrefixes = new String[]{pre_gameMessage, pre_system};
    
    
    
    int maxChars = 20;
    String sprefixes[] = new String[]{
        pre_word, 
        pre_phonicSound, 
        pre_phonicSyllable, 
        pre_nonWord, 
        pre_nonsenseWord, 
        pre_letterName, 
        pre_homophoneExplanation, 
        pre_topicRecording, 
        pre_sentence, 
        pre_sentencesimple, 
        pre_sentencesimplefull, 
        pre_sentencetest, 
        pre_cap, 
        pre_system, 
        pre_gameMessage};  
    boolean prefixes_isUnique[] = new boolean[]{
        false,   // word
        false,      // phonicsounds
        false,      // phonicsyllables
        false,      // non word
        false,      // nonsense word
        false,      // letter name
        false,      // homophone explanation
        true,       // topic
        true,       // sentence
        true,       // simple sentence
        true,       // simple sentence full
        true,       // test sentence
        false,      // captions
        true,       // system
        true        // game
    };
    
    
    public static String desktopName = "desktopName";
    public static String isWordshark = "isWordshark";
    public static String database = "database";
    public static String currentlyUsed = "currentlyUsed";
    public static String fileID = "fileID";    
    public static String sharkImageID = "sharkImageID";    
    public static String onlineName = "onlineName";
    public static String fullSentence = "fullSentence";  
    public static String elements = "elements";  
    public static String vocab = "vocab"; 
    public static String word = "word"; 
    public static String s3key = "s3key";
    public static String  animated = "animated";    
    String gameRecordings = "gamerecordings";
    public static String simpleSent = "simpleSent";  
    public static String simpleSentFull = "simpleSentFull";  
    public static String standSent = "standSent"; 
    public static String headings = "headings";    
    public static String topicName = "topicName";
    public static String caption = "caption";
    String setences1 = "crosswords1type";
    String letterPatternHeadings = "letterPatternHeadings";
    String publicsay1 = "publicsay1";
    String publicsent1 = "publicsent1";
    
    
    public static String[] recordingFiles = new String[]{"gamerecordings", "publicsay1", "publicsay3", "publicsent1", "publicsent2", "publicsent3"};
    
 
    private static final int DETAILS_NAME = 0;
    private static final int DETAILS_ID = 1;
    private static final int DETAILS_ISANIMATED = 2;
    
    public static int CSV_MODE_VOICEARTIST = 0;
    public static int CSV_MODE_COMMUNISIS = 1;
    
    public class CsvStruct {
        public String word;
        public String s3key;
        public String filename;
        public String desktopname;
        public String desktopdb;
        public String fullsentence;
        public boolean isvocab;
        public boolean isanimated;        
        static final String type_word = "Word/Item";
        static final String type_s3key = "S3Key";
        static final String type_filename = "FileName";
        static final String type_desktopname = "DesktopName";
        static final String type_desktopdb = "DesktopDB";
        static final String type_fullsent = "FullSentence";
        static final String type_isvocab = "IsVocab";
        static final String type_isanimated = "IsAnimated";
        String mixed = "mixed";
        
        public CsvStruct(String pword,String ps3key,String pfilename,String pdesktopname,String pdesktopdb,String pfullsent, boolean pisvocab){
            word = pword;
            s3key = ps3key;
            filename = pfilename;
            desktopname = pdesktopname;
            if(pdesktopdb.trim().equals(""))desktopdb = mixed;
            else desktopdb = pdesktopdb;
            fullsentence = pfullsent;
            isvocab = pisvocab;            
        }
        public CsvStruct(String pword,String ps3key,String pfilename,String pdesktopname,String pdesktopdb,boolean pisvocab, boolean pisanimated){
            word = pword;
            s3key = ps3key;
            filename = pfilename;
            desktopname = pdesktopname;
            desktopdb = pdesktopdb;
            isvocab = pisvocab;    
            isanimated = pisanimated;
        }
    }    
    /*
        String systemRecordings[][] = new String[][]{
            new String[]{
                "publicsay1", "hello", getRecordingS3Name("publicsay1", "hello", null, pre_system),
                "publicsay1", "hello1", getRecordingS3Name("publicsay1", "hello1", null, pre_system),
                "publicsay1", "hello2", getRecordingS3Name("publicsay1", "hello2", null, pre_system),
                "publicsay1", "hello3", getRecordingS3Name("publicsay1", "hello3", null, pre_system),
                "publicsay1", "hello4", getRecordingS3Name("publicsay1", "hello4", null, pre_system)
            }
        }; 
    */    
        
        String systemRecordings[][] = new String[][]{
            new String[]{publicsay1, "hello", pre_system},
                new String[]{publicsay1, "hello1", pre_system},
                new String[]{publicsay1, "hello2", pre_system},
                new String[]{publicsay1, "hello3", pre_system},
                new String[]{publicsay1, "hello4", pre_system}
        };         

        String sgameecordings[][] = new String[][]{
            new String[]{publicsay1, "change_to", pre_gameMessage},
                new String[]{publicsay1, "click_tick", pre_gameMessage},
                new String[]{publicsay1, "press_enter", pre_gameMessage},
                new String[]{publicsent1, "- try again", pre_gameMessage},
                new String[]{publicsent1, "% out of %", pre_gameMessage},
                new String[]{publicsent1, "(That is the same as last time)", pre_gameMessage},
                new String[]{publicsent1, "(Your best was %)", pre_gameMessage},
                new String[]{publicsent1, "|That is the most balls you have ever got in the bowl !", pre_gameMessage},
                new String[]{publicsent1, "|That is the same as your best number of balls in the bowl.", pre_gameMessage},
                new String[]{publicsent1, "|Your best number of balls in the bowl was %.", pre_gameMessage},
                new String[]{publicsent1, "9 skittles down - good - score %", pre_gameMessage},
                new String[]{publicsent1, "A good try|You got one across", pre_gameMessage},
                new String[]{publicsent1, "All animals safe !! Well done.", pre_gameMessage},
                new String[]{publicsent1, "All down - hooray !!! - score %", pre_gameMessage},
                new String[]{publicsent1, "Brilliant ! You got them all", pre_gameMessage},
                new String[]{publicsent1, "Brilliant !|% out of %", pre_gameMessage},
                new String[]{publicsent1, "BRILLIANT !|You got them all across safely|Score %", pre_gameMessage},
                new String[]{publicsent1, "Choose an animal and click to start the race.|Pick the winner - win %.|Second prize is %.", pre_gameMessage},
                new String[]{publicsent1, "Click on an unfilled slot", pre_gameMessage},
                new String[]{publicsent1, "Click on any sentence or waveform|to hear it again", pre_gameMessage},
                new String[]{publicsent1, "Click on any sound or waveform|to hear it again", pre_gameMessage},
                new String[]{publicsent1, "Click on any word or waveform|to hear it again", pre_gameMessage},
                new String[]{publicsent1, "Click on the red button, pause, then say the|SEPARATE SOUNDS followed by the WHOLE WORD ", pre_gameMessage},
                new String[]{publicsent1, "Click on the red button, pause,|then say the sentence.", pre_gameMessage},
                new String[]{publicsent1, "Click on the red button, pause,|then say the word", pre_gameMessage},
                new String[]{publicsent1, "Click on the red button,|pause, then say the sound", pre_gameMessage},
                new String[]{publicsent1, "Cross the river by riding on the crocodiles.|Click on a crocodile to ride it or to stop it eating you.|Click on the far bank to jump off when you get close.|Get all three men over - score %|Get two men over - score %.", pre_gameMessage},
                new String[]{publicsent1, "Did you get it right ?", pre_gameMessage},
                new String[]{publicsent1, "Find the symbol and click on it.", pre_gameMessage},
                new String[]{publicsent1, "Find the word and click on it.", pre_gameMessage},
                new String[]{publicsent1, "Getting better !! ", pre_gameMessage},
                new String[]{publicsent1, "Getting better !!|(Your last was %) ", pre_gameMessage},
                new String[]{publicsent1, "good", pre_gameMessage},
                new String[]{publicsent1, "Good - you saved", pre_gameMessage},
                new String[]{publicsent1, "GOOD - Your score is %", pre_gameMessage},
                new String[]{publicsent1, "GOOD !  You got % balls in the bowl and scored %.", pre_gameMessage},
                new String[]{publicsent1, "GOOD ! - you got|all the treasures except one", pre_gameMessage},
                new String[]{publicsent1, "Good !|% out of %", pre_gameMessage},
                new String[]{publicsent1, "GOOD !|You got two across safely|Score %", pre_gameMessage},
                new String[]{publicsent1, "Hard luck !!!!!", pre_gameMessage},
                new String[]{publicsent1, "Knock down 9 - score %", pre_gameMessage},
                new String[]{publicsent1, "Knock down all 10  - score %", pre_gameMessage},
                new String[]{publicsent1, "No mistakes at all !!|BRILLIANT !!!", pre_gameMessage},
                new String[]{publicsent1, "Only one mistake !|VERY GOOD !", pre_gameMessage},
                new String[]{publicsent1, "Only two mistakes !|GOOD !", pre_gameMessage},
                new String[]{publicsent1, "pairs of animals", pre_gameMessage},
                new String[]{publicsent1, "Position the spring left or right.|Pull down to compress it and then release the ball.|Score %|for each ball that lands in the bowl.", pre_gameMessage},
                new String[]{publicsent1, "Pull ball down across line.|Aim it and click to release.|", pre_gameMessage},
                new String[]{publicsent1, "Say the sentence then click OK", pre_gameMessage},
                new String[]{publicsent1, "Say the SEPARATE SOUNDS, followed by|the WHOLE WORD. Then click OK. ", pre_gameMessage},
                new String[]{publicsent1, "Say the sound then click OK", pre_gameMessage},
                new String[]{publicsent1, "Say the word then click OK", pre_gameMessage},
                new String[]{publicsent1, "Score %", pre_gameMessage},
                new String[]{publicsent1, "Second - win %", pre_gameMessage},
                new String[]{publicsent1, "skittles down. Hard luck !! ", pre_gameMessage},
                new String[]{publicsent1, "Sorry - all the balls missed the bowl.|Better luck next time.", pre_gameMessage},
                new String[]{publicsent1, "Sorry - you didn't get any across.|The crocodiles were very difficult this time.", pre_gameMessage},
                new String[]{publicsent1, "That is the same as last time", pre_gameMessage},
                new String[]{publicsent1, "THE WINNER - win %", pre_gameMessage},
                new String[]{publicsent1, "There are", pre_gameMessage},
                new String[]{publicsent1, "Time's up -", pre_gameMessage},
                new String[]{publicsent1, "Total score:", pre_gameMessage},
                new String[]{publicsent1, "Try again", pre_gameMessage},
                new String[]{publicsent1, "Type the symbol then press ENTER.  Use the Backspace key if you need to.", pre_gameMessage},
                new String[]{publicsent1, "Type the word, then press ENTER.  Use the Backspace key if you need to.", pre_gameMessage},
                new String[]{publicsent1, "very good !", pre_gameMessage},
                new String[]{publicsent1, "VERY GOOD|You caught % monsters", pre_gameMessage},
                new String[]{publicsent1, "Well done.You got one ball in the bowl and scored %.", pre_gameMessage},
                new String[]{publicsent1, "WONDERFUL - Your score is %", pre_gameMessage},
                new String[]{publicsent1, "WONDERFUL !!! ", pre_gameMessage},
                new String[]{publicsent1, "WONDERFUL !!|you got all the treasures", pre_gameMessage},
                new String[]{publicsent1, "WONDERFUL|You caught % monsters !!!", pre_gameMessage},
                new String[]{publicsent1, "words in the list.|Remember as many as you can.|When you are ready, start building them.|Press ENTER at the end of each word.", pre_gameMessage},
                new String[]{publicsent1, "words in the list.|Remember as many as you can.|When you are ready, start typing them.|Press ENTER at the end of each word.", pre_gameMessage},
                new String[]{publicsent1, "You came fifth.|Better luck next time!", pre_gameMessage},
                new String[]{publicsent1, "You came fourth.|Better luck next time!", pre_gameMessage},
                new String[]{publicsent1, "You came last.|Better luck next time!", pre_gameMessage},
                new String[]{publicsent1, "You came second|Good - score %", pre_gameMessage},
                new String[]{publicsent1, "You came third|Well done - score %", pre_gameMessage},
                new String[]{publicsent1, "You caught % monsters", pre_gameMessage},
                new String[]{publicsent1, "You didn't save any animals !!!", pre_gameMessage},
                new String[]{publicsent1, "You got", pre_gameMessage},
                new String[]{publicsent1, "You got % treasures|The octopus got %", pre_gameMessage},
                new String[]{publicsent1, "You saved only", pre_gameMessage},
                new String[]{publicsent1, "You saved only one pair of animals - try again", pre_gameMessage},
                new String[]{publicsent1, "You tried hard but didn't get any treasures.|Do try again !", pre_gameMessage},
                new String[]{publicsent1, "You tried hard but got only one treasure.|Do try again !", pre_gameMessage},
                new String[]{publicsent1, "You won !!!!|Wonderful - score %", pre_gameMessage},
                new String[]{publicsent1, "Your best was", pre_gameMessage}
        };  
    
        
    static String jsonFolder = "json";
    static String readJsonFolder = sharkStartFrame.publicPathplus+jsonFolder+shark.sep;   
    static String readJsonRecording = sharkStartFrame.publicPathplus+jsonFolder+shark.sep+"recordings"+jsonext;   
    static String readPrevJsonRecording = sharkStartFrame.publicPathplus+jsonFolder+shark.sep+"prev"+shark.sep+"recordings"+jsonext; // the previous i.e. currently live recordings json file   
    static String filePublicImages = sharkStartFrame.publicPathplus+jsonFolder+shark.sep+"images"+jsonext; // contains the old names and new names of the images  
    static String readPrevJsonImages = sharkStartFrame.publicPathplus+jsonFolder+shark.sep+"prev"+shark.sep+"images"+jsonext;
    
    
    
    
    static String outputFolder = "publictopicsPrint";
 //   static String fileImageNames = sharkStartFrame.sharedPathplus+outputFolder+shark.sep+"imagenames"+jsonext; // contains the old names and new names of the images
    static String fileImages = sharkStartFrame.sharedPathplus+outputFolder+shark.sep+"images"+jsonext; // contains the old names and new names of the images
    static String fileSounds = sharkStartFrame.sharedPathplus+outputFolder+shark.sep+"recordings"+jsonext;   // contains the old names and new names of only those recordings that are wanted in the online version
    static String fileSounds2 = sharkStartFrame.sharedPathplus+outputFolder+shark.sep+"recordings2"+jsonext;
    static String fileTopics = sharkStartFrame.sharedPathplus+outputFolder+shark.sep+"topics"+jsonext;    // contains sentences wanted, and letter pattern headings wanted.
    static String fileHeadingOutput = sharkStartFrame.sharedPathplus+outputFolder+shark.sep+"headings"+txtext;    // contains sentences wanted, and letter pattern headings wanted.
    static String fileSimpleSentOutput = sharkStartFrame.sharedPathplus+outputFolder+shark.sep+"simplesent"+txtext;    // contains sentences wanted, and letter pattern headings wanted.
    static String fileSentenceOutput = sharkStartFrame.sharedPathplus+outputFolder+shark.sep+"sentence"+txtext;    // contains sentences wanted, and letter pattern headings wanted.
    static String fileDummyRecordings = sharkStartFrame.sharedPathplus+outputFolder+shark.sep+"dummyRecordings"+txtext; // dummy files of 'ToBeRecorded' need to be produced for these recordings
  
         String imageIWSPoolPath = "D:\\S3syncImages\\iws";
      static  String photoPoolPath = "D:\\Dropbox\\PhotosMatchedResources";
    
    static String fileRecordingsBase = "C:\\Recordings\\";
    static String fileRecordingsSubFolderFiles = "Files\\";
    static String fileRecordingsSubFolderLists = "Lists\\";
    static String fileRecordingsSubFolderOutput = "Output\\";
    
    
   
    
    
    /*
topic-specific game recordings (e.g. Find)
phonic syllables (~~)
phonic sound - phoneme (~)
words
letter names (!1~)
homophone explanation (pre_word=)
sentences
sentences simple
game message (_) ??
consonant blend (!!)  - treat as   (!1)
nonsense words (!)
non-words - put into a list as a pre_word suffix ending or letter pairs?  (!1)   
    */
   
    String[] allTopicPathsForSetWork;
    saveTree1.saveTree2 nt[];
    
    
    
    
  public void renameRecordingstoZeros(){ 
     String src = "C:\\Users\\paulr\\Desktop\\New folder (11)";
     String output = "C:\\Users\\paulr\\Desktop\\New folder (12)";    
     String ss[] = (new File(src)).list();
    for(int ii = 0; ii < ss.length; ii++){
        String s = ss[ii];
                    String from = src+"\\"+s;              
                    String to =output+"\\"+getNameWithZeros(s);
        try {
            FileUtils.copyFile(new File(from), new File(to));
        } catch (Exception e) { 
            e.printStackTrace();
        }
    }
 }     
    
    
  public void renameRecordings2(){ 
     String src = "C:\\Users\\paulr\\Desktop\\New folder (12)";
     String output = "C:\\Users\\paulr\\Desktop\\New folder (13)";   
        String ssnames[] = u.readFile("C:\\Users\\paulr\\Desktop\\list.txt");  
     String ss[] = (new File(src)).list();
     
             
     JSONParser parser = new JSONParser();
     JSONArray results = null;
     JSONArray jsonRecResults = null;
     try{

            JSONObject json = (JSONObject)parser.parse(new FileReader(readJsonRecording));
            jsonRecResults = (JSONArray) json.get(elements); 
     }
     catch(Exception e){}
     

     loop1: for(int ii = 0; ii < ssnames.length; ii++){
        String s = ssnames[ii];
        try {
            for(int i = 0; i < jsonRecResults.size(); i++) {
                JSONObject p = (JSONObject) jsonRecResults.get(i);
                String currUsed = (String)p.get(currentlyUsed);
                if(currUsed!=null  && currUsed.equals("false"))continue;
                if(!((String)p.get("onlineName")).startsWith("WORD_"))continue;
                String word = (String)p.get("word");     
                if(word.equals(s)){
                    String from = src+"\\"+toZeroNumber(String.valueOf(ii+1))+".mp3";
                    String to =output+"\\"+((String)p.get("onlineName"));
                    int x = 1;
                    while(new File(to).exists()){
                        to =output+"\\"+((String)p.get("onlineName")+String.valueOf(x));
                        x++;
                    }
                    FileUtils.copyFile(new File(from), new File(to));
                    continue loop1;
                }
            }            
        } catch (Exception e) { 
            e.printStackTrace();
        }
    }
 }     
    
  
   public void renameRecordings3(){ 
     String src = "C:\\Users\\paulr\\Documents\\New folder\\";
     String output = "C:\\Users\\paulr\\Documents\\New folder Out\\";   
     String text = "C:\\Users\\paulr\\Documents\\New folder Txt\\names.txt"; 
     String ss[] = u.readFile(text);
     int count = 1;
     String preFile = getNumberWithZeros(String.valueOf(count), 2)+"-"+String.valueOf(count)+mp3ext;
     while(new File(src+preFile).exists()){
        File fsrc = new File(src+preFile);
        File fdest = new File(output+ss[count-1]+mp3ext);
        try{
            FileUtils.copyFile(fsrc, fdest);
        }
        catch(Exception e){
            int g;
            g = 0;
        }
        count++;
        preFile = getNumberWithZeros(String.valueOf(count), 2)+"-"+String.valueOf(count)+mp3ext;
     }
 }   
  
   
    public void renameRuthRecordings(){
        String number = "39";
        String srcList =  "D:\\Ruth's Recordings\\"+number+"\\list.txt";
        String srcFilePath =  "D:\\Ruth's Recordings\\"+number+"\\amplified";
        String srcFileZeroedPath =  "D:\\Ruth's Recordings\\"+number+"\\zeroed";
        String outputFilePath =  "D:\\Ruth's Recordings\\"+number+"\\output";
        renameFileNameWithZeros(srcFilePath, srcFileZeroedPath);
        String ss[] = u.readFile(srcList);
        File ff[] = new File(srcFileZeroedPath).listFiles();
        for(int i = 0; i < ff.length; i++){
            u.copyfile(ff[i], new File(outputFilePath+"//"+ss[i]+".mp3"));
        }
        int g;
        g = 0;
    }    
        
    
       public void renameFileNameWithZeros(String srcStr, String destStr){
        File src = new File(srcStr);
        File ff[] = src.listFiles();
        for(int i = 0; i < ff.length; i++){
   //        new File(destStr+ff[i].getName()).mkdir();
    //        File ff2[] = ff[i].listFiles();
     //       for(int k = 0; k < ff2.length; k++){
                try{
                    FileUtils.copyFile(ff[i], new File(destStr+shark.sep+getNameWithZeros(ff[i].getName()))); 
                }
                catch(Exception ee){
                    int h;
                    h = 0;
                }                
      //      }            
        }

    }     
    
    
    public void renameWithZeros(){
        String srcStr = fileRecordingsBase+"Files2\\publicsent2";
        String destStr = fileRecordingsBase+"Files3\\publicsent2";
        File src = new File(srcStr);
        File ff[] = src.listFiles();
        for(int i = 0; i < ff.length; i++){
   //        new File(destStr+ff[i].getName()).mkdir();
    //        File ff2[] = ff[i].listFiles();
     //       for(int k = 0; k < ff2.length; k++){
                try{
                    FileUtils.copyFile(ff[i], new File(destStr+shark.sep+getNameWithZeros(ff[i].getName()))); 
                }
                catch(Exception ee){
                    int h;
                    h = 0;
                }                
      //      }            
        }

    }    
    
    
    public void writeOutSentences(){
        JSONParser parser = new JSONParser();
        JSONArray jsonssent3 = null;
        try {
            JSONObject json = (JSONObject)parser.parse(new FileReader("C:\\jshark-shared2\\publictopicsPrint\\topics.json"));
            jsonssent3 = (JSONArray) json.get(elements);

        } catch (Exception e) {
            e.printStackTrace();
        }   
        for(int i = 0; i < jsonssent3.size(); i++) {
            JSONObject p = (JSONObject) jsonssent3.get(i);
            String mainrec = (String)p.get("simpleSentFull");
            if(mainrec==null)continue;

            System.out.println(mainrec);
        }      
        
    }
    
    
    
    public void writeOutSentences2(){
        JSONParser parser = new JSONParser();
        JSONArray jsonssent3 = null;
        CsvWriter csvRecmmendedOutput = null; 
        String target = "simpleSent";
        File ff = null;
        try {
            JSONObject json = (JSONObject)parser.parse(new FileReader("C:\\jshark-shared2\\publictopicsPrint\\topics.json"));
            jsonssent3 = (JSONArray) json.get(elements);
            ff = new File("C:\\jshark-shared2\\publictopicsPrint\\"+target+".csv");
            csvRecmmendedOutput = new CsvWriter(new OutputStreamWriter(new FileOutputStream(ff), "windows-1252"), ',');        
            csvRecmmendedOutput.write("Sentence");
            csvRecmmendedOutput.write("Word list name");
            csvRecmmendedOutput.endRecord();

  
            for(int i = 0; i < jsonssent3.size(); i++) {
                JSONObject p = (JSONObject) jsonssent3.get(i);
                String mainrec = (String)p.get(target);
                String wordlist = (String)p.get("topicName");
                if(mainrec==null)continue;
                csvRecmmendedOutput.write(mainrec);
                csvRecmmendedOutput.write("\""+wordlist+"\"");
                csvRecmmendedOutput.endRecord();                                        
                
            }      
            csvRecmmendedOutput.close();
        } catch (Exception e) {
            e.printStackTrace();
        }         
      
        
    }    
    
    
    /*
    So this twice, second time, swap the json files - 1 becomes 2
    */
    public void compareJsonFiles(String jsonPath1, String jsonPath2){
        JSONParser parser = new JSONParser();
        JSONArray json1 = null;
        JSONArray json2 = null;
        try {
            JSONObject jsonOb1 = (JSONObject)parser.parse(new FileReader(jsonPath1));
            JSONObject jsonOb2 = (JSONObject)parser.parse(new FileReader(jsonPath2));
            json1 = (JSONArray) jsonOb1.get(elements);
            json2 = (JSONArray) jsonOb2.get(elements);
            try {
                outer: for(int i = 0; i < json1.size(); i++) {
                    JSONObject p = (JSONObject) json1.get(i);
                    secondloop : for(int j = 0; j < json2.size(); j++) {
                        JSONObject p2 = (JSONObject) json2.get(j);
                        Set<String> keys = p.keySet();
                        Object o[] = keys.toArray();
                        for(int k = 0; k < o.length; k++){
                            String key = (String)o[k];
                            if(!p2.containsKey(key)){
                                continue secondloop;
                            }
                            String val = (String)p.get(key);
                            String val2 = (String)p2.get(key);
                            if(!(val == null && val2 == null)){
                                if( val == null ||  !val.equals(val2)){
                                    continue secondloop;    
                                }                      
                            }
                        }
                        continue outer;  // p == p2
                    }
                    System.out.println("No match for json1: " + (String)p.get(onlineName));
                }      
            } catch (Exception e) {
                e.printStackTrace();
            }               
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }         
              
    }
    
    
    
    public void compareSSent(){
        String ssent3 = "C:\\jshark-shared2\\publictopicsPrint\\topics.json";
        String ssent1 = "C:\\jshark-shared2\\publictopicsPrint\\recordings.json";
  //      String ssent2 = "C:\\jshark-shared2\\publictopicsPrint\\MappingSSENTFULL.json";
        JSONParser parser = new JSONParser();
        JSONArray jsonssent3 = null;
        JSONArray jsonssent = null;
   //     JSONArray jsonssentfull = null;
        try {
            JSONObject json = (JSONObject)parser.parse(new FileReader(ssent3));
            jsonssent3 = (JSONArray) json.get(elements);

        } catch (Exception e) {
            e.printStackTrace();
        }     
        try {
            JSONObject json = (JSONObject)parser.parse(new FileReader(ssent1));
            jsonssent = (JSONArray) json.get(elements);

        } catch (Exception e) {
            e.printStackTrace();
        }
  //      JSONParser parser2 = new JSONParser();
 //       try {
  //          JSONObject json = (JSONObject)parser.parse(new FileReader(ssent2));
  //          jsonssentfull = (JSONArray) json.get(elements);
//
  //      } catch (Exception e) {
  //          e.printStackTrace();
   //     }
        int fullcounter = 0;
        int beepcounter = 0;
        for(int i = 0; i < jsonssent3.size(); i++) {
            JSONObject p = (JSONObject) jsonssent3.get(i);
            String mainrec = (String)p.get("simpleSentFull");
            if(mainrec==null)continue;
            sentence sentpl = (new sentence(mainrec,null));
            
            String matchfull = sentpl.stripcloze().toLowerCase();
        //    if(matchfull.endsWith("."))matchfull = matchfull.substring(0, matchfull.length()-1);
            String matchbeep = sentpl.val.toLowerCase(); 
            if(matchbeep.endsWith("."))matchbeep = matchbeep.substring(0, matchbeep.length()-1);
            
            String prefix = "ssent/SSENT";
            boolean found = false;
            for(int j = 0; j < jsonssent.size(); j++) {
                JSONObject p1 = (JSONObject) jsonssent.get(j);
                String s = (String)p1.get("s3key");
                if(!s.startsWith(prefix))continue;
                

                s = (String)p1.get("desktopName");
                
                if(s.indexOf("work is good")>=0){
                    int g;
                    g = 0;
                }
                if(matchbeep.equalsIgnoreCase(s)){
                    found = true;
                }
            }
            if(!found)
                System.out.println(prefix+":    "+matchbeep);
            else beepcounter++;
            prefix = "ssentfull/SSENTFULL";
            found = false;
            for(int j = 0; j < jsonssent.size(); j++) {
                JSONObject p1 = (JSONObject) jsonssent.get(j);
                String s = (String)p1.get("s3key");
                if(!s.startsWith(prefix))continue;
                s = (String)p1.get("desktopName");
                if(matchfull.equalsIgnoreCase(s)){
                    found = true;
                }
            }
            if(!found)
                System.out.println(prefix+":    "+matchfull);
            else fullcounter++;
        }        
        
        int g; 
        g = 0;
        
        
        /*
        String ssent1 = "C:\\jshark-shared2\\publictopicsPrint\\MappingSSENT.json";
        String ssent2 = "C:\\jshark-shared2\\publictopicsPrint\\MappingSSENTFULL.json";
        JSONArray jsonssent = null;
        JSONArray jsonssentfull = null;
        JSONParser parser = new JSONParser();
        try {
            JSONObject json = (JSONObject)parser.parse(new FileReader(ssent1));
            jsonssent = (JSONArray) json.get(elements);

        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONParser parser2 = new JSONParser();
        try {
            JSONObject json = (JSONObject)parser.parse(new FileReader(ssent2));
            jsonssentfull = (JSONArray) json.get(elements);

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        for(int i = 0; i < jsonssent.size(); i++) {
            JSONObject p = (JSONObject) jsonssent.get(i);
            String s_full = (String)p.get(CsvStruct.type_fullsent);
            boolean found = false;
            for(int j = 0; j < jsonssentfull.size(); j++) {
                JSONObject pf = (JSONObject) jsonssentfull.get(j);
                String s_fullf = (String)pf.get(CsvStruct.type_fullsent);
                if(s_fullf.equals(s_full)){
                    found = true;
                    break;
                }
                
            }
            if(!found)
                System.out.println(s_full);
        }
      
        
        int g;
        g = 0;
        
        */
    }
    
    
    
    public void getAspectRatioExtremes(){
        String paths[] = new String[]{"G:\\S3syncImages\\iphoto", "G:\\S3syncImages\\iws"};
        String names[] = new String[]{};
        String d[] = new String[]{};
        for(int i = 0; i < paths.length; i++){
            File fs[] = new File(paths[i]).listFiles();
            for(int k = 0; k < fs.length; k++){
                try{
                    BufferedImage bimg = ImageIO.read(fs[k]);
                    int width          = bimg.getWidth();
                    int height         = bimg.getHeight();
                    names = u.addString(names, fs[k].getName());
                    float f = (float)width / height;
                    String s = String.valueOf(f);
                    d = u.addString(d, s);
                }
                catch(Exception ee){
                    int g;
                     g = 0;
                }
            }
        }
java.util.List<ArrayList<String>> namesAndNumbers = new ArrayList<ArrayList<String>>();

        for(int i = 0; i < names.length; i++){
            namesAndNumbers.add(new ArrayList<String>(Arrays.asList(d[i], names[i])));
        }        

        Collections.sort(namesAndNumbers, new Comparator<ArrayList<String>>() {    
                @Override
                public int compare(ArrayList<String> o1, ArrayList<String> o2) {
                    float f1 = Float.parseFloat(o1.get(0));
                    float f2 = Float.parseFloat(o2.get(0));
                    if(f1 == f2)return 0;
                    return f1 > f2?1:-1;
                }               
        });
        System.out.println(namesAndNumbers);
        
        
        for(int i = 0; i < namesAndNumbers.size(); i++){
            System.out.println(((ArrayList)namesAndNumbers.get(i)).get(1));
        }            
        
    }    
    
    public String getNameWithZeros(String name){
        int k = name.indexOf('.');
        String pre = name.substring(0, k);
   //     pre = pre.substring(2);
        while (pre.length()<5)
            pre = "0"+pre;
        return pre + name.substring(k);
    }
    
    public String getNumberWithZeros(String num, int len){
        while (num.length()<len)
            num = "0"+num;
        return num;
    }    
    
    
    public String charToAscii(String s){
        String ret = "";
        char c[] = s.toCharArray();
        for(int i = 0; i < c.length; i++){
            if(c[i] == 58){  // ':'
                ret += "[58]";
            }
            else if(c[i] == 63){  // '?'
                ret += "[63]";
            }
            else{
                ret += c[i];
            }
            
        }
        if(ret.equals(""))return null;
        return ret;
    }
    
    public String unCharToAscii(String s){
        s = s.replace("[58]", String.valueOf((char)58));
        return s.replace("[63]", String.valueOf((char)63));
    }
    
    /*
    Goes through a folder of images finding which are grouped as animations and
    copies them to grouped subfolders.
    */
    public void folderiseAnimations(){
        String srcstr = "G:\\Shark Screen Shots\\DEST-Anim\\";
        File src = new File(srcstr);
        String deststr = "G:\\Shark Screen Shots\\DEST-AnimFOLDERS\\";
        File dest = new File(deststr);
        dest.mkdir();
        final Pattern p = Pattern.compile(".*_\\d\\d\\d\\d"+pngext);
        File[] ff = (src).listFiles(new FileFilter() {
        public boolean accept(File file) {
            return p.matcher(file.getName()).find();
        }
        });
        for(int i = 0; ff!=null && i < ff.length; i++){
            String folderString = deststr+ff[i].getName().substring(0, ff[i].getName().lastIndexOf("_"));
            new File(folderString).mkdir();
            try{
                FileUtils.copyFile(new File(srcstr+ff[i].getName()), new File(folderString+"\\"+ff[i].getName())); 
            }
            catch(Exception ee){
                int h;
                h = 0;
            }  
        }
    }
    /*
    public void saveAllImageNames(){
        String dbs[] = new String[]{ "publicimage", "publicimageSENT"};
                String ssFile[] = new String[]{};
        String ssDesktop[] = new String[]{};
        String ssOnlineMinusExt[] = new String[]{};
        String ssDatabase[] = new String[]{}; 
        String ssOnline[] = new String[]{};
        String ssOnlineFileOnly[] = new String[]{};      
        
        String vocabdbs[] = new String[]{"publicimageSENT"};
        String ssWordshark[] = new String[]{};
        String ssVocabulary[] = new String[]{};
        String ssPlain[] = new String[]{};        
        String ssIsAnimated[] = new String[]{};
        String pref = "IWS_";
            for(int i = 0; i < dbs.length; i++){
                ssOnlineFileOnly = new String[]{};
                String ss[] = db.list(dbs[i], db.IMAGE);
                for(int k = 0; k < ss.length; k++){                    
                   if(ss[k].trim().equals(""))
                       continue;
                   String strwithoutprefix = getImageS3Name(dbs[i], ss[k], ssOnline, pref);
                   String str = pref+strwithoutprefix;
                   if(str == null){
                       continue;
                   }
                   ssDatabase = u.addString(ssDatabase, dbs[i]);
                   ssDesktop = u.addString(ssDesktop, ss[k]);
                  // ssOnlineMinusExt = u.addString(ssOnlineMinusExt,  str.substring(0, str.lastIndexOf(".")));
                   ssOnline = u.addString(ssOnline,  str);
               }          
         //     writeFile("C:\\Users\\paulr\\Documents\\Text\\"+dbs[i]+".txt", ssOnlineFileOnly);            
            }       
            
            writeImagesJson(fileImageNames, ssDesktop, ssDatabase, ssOnline, null, null, null, null);
    }
    */
    
    
    
    public void replaceFileIDInIWSJson(){
        String outPath = sharkStartFrame.sharedPathplus+outputFolder+shark.sep+"images2"+jsonext;
        boolean changed = false;
        
        JSONParser parser = new JSONParser();
        JSONObject json = null;
        JSONArray prevRecResults = null;
        try{
            json = (JSONObject)parser.parse(new FileReader(filePublicImages));
            prevRecResults = (JSONArray) json.get(elements);
        }
        catch(Exception e){
            int h; 
            h = 0;
        }    
        String ssFileIDs[] = new String[]{};
        String[] ssFileNames = new String[]{};
        File listPublicWordsharkImageFiles[] =  new File("D:\\S3syncImages\\iws").listFiles();
        for(int i = 0; i < listPublicWordsharkImageFiles.length; i++) {
            ssFileIDs = u.addString(ssFileIDs, getImageFileID(listPublicWordsharkImageFiles[i].getAbsolutePath()));
            ssFileNames = u.addString(ssFileNames, listPublicWordsharkImageFiles[i].getName());
        }
        for(int i = 0; i < prevRecResults.size(); i++) {
            JSONObject p = (JSONObject) prevRecResults.get(i);
            String isW = (String)p.get(isWordshark);
            if(!isW.equals("1"))continue;
            if(!((String)p.get(currentlyUsed)).equals("true"))continue;

            
            String onlinename = (String)p.get(onlineName);
       //     String currUsed = (String)p.get(currentlyUsed);
            String fID = (String)p.get(fileID);
 //           if(currUsed.equals("true")){  
        //        if((fID == null || fID.trim().equals(""))){
                    int k;
                    if((k=u.findString(ssFileNames, onlinename))>=0){
 if(fID==null){
     System.out.println(onlinename);
 }
                        if(fID==null || !fID.equals(ssFileIDs[k])){
                            p.put(fileID, ssFileIDs[k]); 
                            prevRecResults.set(i, p);
                            changed = true;
                        }

                    }
        //        }   
 //           }
        }
        if(changed){
            JSONObject objectmain = new JSONObject();
            objectmain.put(elements, prevRecResults);          
            try (FileWriter file = new FileWriter(outPath)) {
                file.write(objectmain.toJSONString());
                file.flush();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }    
    
    /*
    Checks the just produced images json file
    */
    public void addFileIDToIWSJson(){
        String outPath = sharkStartFrame.sharedPathplus+outputFolder+shark.sep+"images2"+jsonext;
        boolean changed = false;
        
        JSONParser parser = new JSONParser();
        JSONObject json = null;
        JSONArray prevRecResults = null;
        try{
            json = (JSONObject)parser.parse(new FileReader(filePublicImages));
            prevRecResults = (JSONArray) json.get(elements);
        }
        catch(Exception e){
            int h; 
            h = 0;
        }    
        String ssFileIDs[] = new String[]{};
        String[] ssFileNames = new String[]{};
        File listPublicWordsharkImageFiles[] =  new File(imageIWSPoolPath).listFiles();
        for(int i = 0; i < listPublicWordsharkImageFiles.length; i++) {
            ssFileIDs = u.addString(ssFileIDs, getImageFileID(listPublicWordsharkImageFiles[i].getAbsolutePath()));
            ssFileNames = u.addString(ssFileNames, listPublicWordsharkImageFiles[i].getName());
        }
        for(int i = 0; i < prevRecResults.size(); i++) {
            JSONObject p = (JSONObject) prevRecResults.get(i);
            String isW = (String)p.get(isWordshark);
            if(!isW.equals("1"))continue;
            String onlinename = (String)p.get(onlineName);
            String currUsed = (String)p.get(currentlyUsed);
            String fID = (String)p.get(fileID);
            if(currUsed.equals("true")){  
                if((fID == null || fID.trim().equals(""))){
                    int k;
                    if((k=u.findString(ssFileNames, onlinename))>=0){
                        p.put(fileID,ssFileIDs[k]); 
                        prevRecResults.set(i, p);
                        changed = true;
                    }
                    else
                        System.out.println("No file ID:  "+onlinename);
                }   
            }
        }
        if(changed){
            JSONObject objectmain = new JSONObject();
            objectmain.put(elements, prevRecResults);          
            try (FileWriter file = new FileWriter(outPath)) {
                file.write(objectmain.toJSONString());
                file.flush();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    private ArrayList getImageFileDimensions(String path){
        ArrayList al = null;
        try{
            BufferedImage image = ImageIO.read(new File(path));
            float imW = image.getWidth();
            float imH = image.getHeight();
            al = new ArrayList();
            al.add((int)imW);
            al.add((int)imH);
        }
        catch(Exception e){
        }
        return al;
    }
    
    public void checkAllImages(String path){
        String outPath;
        if(path==null)outPath = sharkStartFrame.sharedPathplus+outputFolder+shark.sep+"images"+jsonext;
        else outPath = path;
        JSONParser parser = new JSONParser();
        JSONObject json = null;
        JSONArray prevRecResults = null;
        try{
            json = (JSONObject)parser.parse(new FileReader(outPath));
            prevRecResults = (JSONArray) json.get(elements);
        }
        catch(Exception e){
            int h; 
            h = 0;
        }    
        String ssFileIDs[] = new String[]{};
         String[] ssFileNames = new String[]{};
        File listPublicWordsharkImageFiles[] =  new File(imageIWSPoolPath).listFiles();
        for(int i = 0; i < listPublicWordsharkImageFiles.length; i++) {
            ssFileIDs = u.addString(ssFileIDs, getImageFileID(listPublicWordsharkImageFiles[i].getAbsolutePath()));
            ssFileNames = u.addString(ssFileNames, listPublicWordsharkImageFiles[i].getName());
        }
        String checkForRepeatOnlineName[] = new String[]{};
        for(int i = 0; i < prevRecResults.size(); i++) {
            JSONObject p = (JSONObject) prevRecResults.get(i);
            String onlinename = (String)p.get(onlineName);
            String currUsed = (String)p.get(currentlyUsed);
            String sharkID = (String)p.get(sharkImageID);
            String fID = (String)p.get(fileID);
            String isW = (String)p.get(isWordshark);
            String isAni = (String)p.get(animated);
            if(onlinename.indexOf("IWS_a_piece2")>=0){
                int h; 
                h = 0;
            }
            
          //                          p.put(sharkImageID, details[DETAILS_ID]);
            //            p.put(fileID, getImageFileID(imagePoolPath+"\\"+onName));
            if(currUsed.equals("true")){
                if(isW.equals("1")){
                    int k;
                    if((k=u.findString(ssFileNames, onlinename))>=0){
                       ArrayList arrl = getImageFileDimensions( listPublicWordsharkImageFiles[k].getAbsolutePath());   
                       if(((int)arrl.get(0))!=529 || (int)arrl.get(1)!=529){
                           System.out.println("Incorrect dimensions:  "+onlinename);
                       }
                    }
                    else System.out.println("Can't find file:  "+onlinename);
                }                    
                
                
                if(u.findString(checkForRepeatOnlineName, onlinename)>=0){
                    System.out.println("duplicate OnlineName:  "+onlinename);
                }
                else{
                    checkForRepeatOnlineName = u.addString(checkForRepeatOnlineName, onlinename);
                }
                if(isW.equals("1") && (sharkID == null || sharkID.trim().equals(""))){
                    System.out.println("No shark ID:  "+onlinename);
                }    
                if((fID == null || fID.trim().equals(""))){
                    System.out.println("No file ID:  "+onlinename);
                }   
                if(isAni.equals("1") && !onlinename.endsWith(".gif")){
                    System.out.println("gif expected:  "+onlinename);
                }   
                if(isAni.equals("0") && !onlinename.endsWith(".png")){
                    System.out.println("gif expected:  "+onlinename);
                }   
                if(isW.equals("1") && u.findString(ssFileIDs, fID)<0){
                    System.out.println("Unknown file ID:  "+onlinename);
                }
            }
        }
    }    
    
    /*
    
    for any new Wordshark images - at this stage there's no image file and so no fileID 
    
    
    then add the non existing, needed iWS images into the Create_Needed_IWS json file
    then do the DoImageScreenshots.java
    then convert down to 529x529 with tor.makeTransparentBG_PNGs()
    then giffingMakeGifs(true, true)
    then renameToOnlineNameImages(true);
    
    ***don't forget to update the images.json with the iws online files when they are produced.
    
    
    
    rename the needed iPHOTO images into the Create_Needed_IPHOTO json file
    copy ALL with online name with lookupAndCopyToOnlinePhoto
    then convert down to 529x529 with tor.makeTransparentBG_PNGs()
    
      
    
    */
    public void saveAllImages(){
        boolean firstEverRun = false;
        String vocabdbs[] = new String[]{"publicimageSENT"};
        String dbs[] = new String[]{ "publicimage", "publicimageSENT"};

        String outPath = sharkStartFrame.sharedPathplus+outputFolder+shark.sep+"images"+jsonext;
        String ssOnlineMinusExt[] = new String[]{};
        String createWordsharks[] = new String[]{};
        String createPhotos[] = new String[]{};
        //get file ids of Wordshark images
        String ssFileIDs[] = new String[]{};
        String[] ssFilePaths = new String[]{};
        File listPublicImageFiles[] =  new File(imageIWSPoolPath).listFiles();
        for(int i = 0; i < listPublicImageFiles.length; i++) {
            ssFileIDs = u.addString(ssFileIDs, getImageFileID(listPublicImageFiles[i].getAbsolutePath()));
            ssFilePaths = u.addString(ssFilePaths, listPublicImageFiles[i].getAbsolutePath());
        }        
   
        JSONParser parser = new JSONParser();
        JSONObject json = null;
        JSONArray prevRecResults = null;
        try{
            json = (JSONObject)parser.parse(new FileReader(readPrevJsonImages));
            prevRecResults = (JSONArray) json.get(elements);
        }
        catch(Exception e){
            int h; 
            h = 0;
        }
        for(int i = 0; i < prevRecResults.size(); i++) {
            JSONObject p = (JSONObject) prevRecResults.get(i);
            String onlinename = (String)p.get(onlineName);
            int kk;
            if((kk=onlinename.lastIndexOf('.'))==onlinename.length()-3-1){
                onlinename = onlinename.substring(0, kk);
            }
            if(!firstEverRun) {
            if(u.findString(ssOnlineMinusExt, onlinename)>=0){
            }
                ssOnlineMinusExt = u.addString(ssOnlineMinusExt, onlinename);
            }
            
            p.put(currentlyUsed,"false"); 
            prevRecResults.set(i, p);
        }                
        JSONArray arrayElementOneArray = (JSONArray)prevRecResults.clone();
        
        // do the Wordshark images
        String pref = pre_wordshark_images+"_";  
        for(int k = 0; k < dbs.length; k++) {
            String listPublicImage[] =  db.list(dbs[k], db.IMAGE);
            innerloop : for(int i = 0; i < listPublicImage.length; i++) {
                String details[];
                if(listPublicImage[i].equalsIgnoreCase("claw") && k == 1){
                    int g; 
                    g = 0;
                }    
                if((details = saveAllImages_GetWordsharkImDetails(listPublicImage[i], dbs[k]))==null)continue;                  
                if(details[DETAILS_NAME].equalsIgnoreCase("fire")){
                    int g; 
                    g = 0;
                }                
                // temporary first time look up
                if(firstEverRun){
                    int id2 = findJsonIndex(arrayElementOneArray, 
                            new String[]{desktopName, database, isWordshark, animated, currentlyUsed}, 
                            new String[]{details[DETAILS_NAME], dbs[k], "1", details[DETAILS_ISANIMATED], "false"});
                    if(id2>=0){
                        JSONObject p = (JSONObject) arrayElementOneArray.get(id2);
                        // check to see if old image and new have same isAnimated - if not, ignore.
                        String isAnim = (String)p.get(animated);
                        if(isAnim.equals(details[DETAILS_ISANIMATED])){
                            String onName = (String)p.get(onlineName);
                            String str = onName.substring(0, onName.lastIndexOf("."));
                            ssOnlineMinusExt = u.addString(ssOnlineMinusExt,  str);
                            p.put(currentlyUsed, "true");
                            p.put(sharkImageID, details[DETAILS_ID]);
                            p.put(fileID, getImageFileID(imageIWSPoolPath+"\\"+onName));
                            arrayElementOneArray.set(id2, p);
           //                 System.out.println("Wordshark image found, FIRST RUN: "+sname);
                            continue innerloop;
                        }
                    } 
                    System.out.println("Wordshark image NOT FOUND IN FIRST RUN: "+details[DETAILS_NAME]);
                }                
                int id = findJsonIndex(arrayElementOneArray, 
                        new String[]{desktopName, sharkImageID, currentlyUsed, isWordshark}, 
                        new String[]{details[DETAILS_NAME], details[DETAILS_ID], "false", "1"});
                // is there a previous record based on sharkImageID
                if(id>=0){
                    JSONObject p = (JSONObject) arrayElementOneArray.get(id);
                    String fileid = (String)p.get(fileID); 
                    // is there a file id?
                    if(fileid != null){
                        int ind = u.findString(ssFileIDs, fileid);
                        // use details
                        if(ind >= 0 && new File(ssFilePaths[ind]).exists()){
                            p.put(currentlyUsed,"true");
                            String onName = (String)p.get(onlineName);
                            String str = onName.substring(0, onName.lastIndexOf("."));
                            ssOnlineMinusExt = u.addString(ssOnlineMinusExt, str);
                            arrayElementOneArray.set(id, p);
       //                     System.out.println("Wordshark image FOUND: "+sname);
                            continue innerloop;
                        }  
                        else{
                            System.out.println("Wordshark image. File doesn't exist:   "+ (ind>=0?ssFilePaths[ind]:(String)p.get(desktopName)) );
                        }
                    }   
                }
            }
        }
        // create new entries / s3names
        for(int k = 0; k < dbs.length; k++) {
            String listPublicImage[] =  db.list(dbs[k], db.IMAGE);
            innerloop : for(int i = 0; i < listPublicImage.length; i++) {
                String details[];
                if((details = saveAllImages_GetWordsharkImDetails(listPublicImage[i], dbs[k]))==null)continue;                  
                if(details[DETAILS_NAME].equalsIgnoreCase("claw")){
                    int g; 
                    g = 0;
                }                               
                int id = findJsonIndex(arrayElementOneArray, 
                        new String[]{desktopName, sharkImageID, currentlyUsed, isWordshark}, 
                        new String[]{details[DETAILS_NAME], details[DETAILS_ID], "true", "1"});
                // skip past those that have been dealt with above
                if(id>=0){
                    continue;
                }
                // first run - new image - not found - don't want to continue becuase it needs to be added to json
                // if it was in previous, found - do want to contiue, othetwise end up with double
                
                 // currentlyused worry
                System.out.println("Wordshark image, CREATE NEW: "+details[DETAILS_NAME]);

                JSONObject obSub = new JSONObject();
                String strwithoutprefix = getImageS3Name(dbs[k], details[DETAILS_NAME], ssOnlineMinusExt, pref);
                String str = pref+strwithoutprefix;
                ssOnlineMinusExt = u.addString(ssOnlineMinusExt,  str);
                obSub.put(isWordshark, "1");
                obSub.put(word, getPlainString(details[DETAILS_NAME], pref));
                obSub.put(database, dbs[k]);
                obSub.put(vocab, (u.findString(vocabdbs,dbs[k])>=0 || details[DETAILS_NAME].indexOf("@@")>=0)?"1":"0");
                obSub.put(desktopName, details[DETAILS_NAME]);
                obSub.put(currentlyUsed,"true");
                obSub.put(animated, details[DETAILS_ISANIMATED]);
                String onlinenam = str+((details[DETAILS_ISANIMATED]=="1")?gifext:pngext);
                obSub.put(onlineName, onlinenam);
                obSub.put(s3key, str.substring(0, str.indexOf("_")).toLowerCase()+"/"+onlinenam);
                obSub.put(sharkImageID, details[DETAILS_ID]);
                arrayElementOneArray.add(obSub);
                if(u.findString(createWordsharks, details[DETAILS_NAME])<0)
                    createWordsharks = u.addString(createWordsharks, onlinenam);

            }
        }
            
        // do the photograph images       
        pref = pre_photographs+"_";
        listPublicImageFiles =  new File(photoPoolPath).listFiles();
        for(int i = 0; i < listPublicImageFiles.length; i++) {
            if(listPublicImageFiles[i].getName().endsWith(gifext)){
                int h;
                h = 0;
            }
            if(!listPublicImageFiles[i].getName().endsWith(pngext) && !listPublicImageFiles[i].getName().endsWith(gifext))continue;
            String details[];
            if((details = saveAllImages_GetPhotoDetails(listPublicImageFiles[i]))==null)continue;              
            // FIRST time only look in previous results and see if there's a match of desktopName and isWordshark=false and animated
            if(firstEverRun){
                if(details[DETAILS_NAME].equalsIgnoreCase("finish")){
                    int g; 
                    g = 0;
                }                 
                int id = findJsonIndex(arrayElementOneArray, 
                        new String[]{desktopName, isWordshark, animated, currentlyUsed}, 
                        new String[]{details[DETAILS_NAME], "0", details[DETAILS_ISANIMATED], "false"});
                if(id>=0){
                    // if match - use
                    JSONObject p = (JSONObject) arrayElementOneArray.get(id);   
                    String onName = (String)p.get(onlineName);
                    String str = onName.substring(0, onName.lastIndexOf("."));
                    ssOnlineMinusExt = u.addString(ssOnlineMinusExt,  str);
                    p.put(currentlyUsed,"true");
                    p.put(fileID, details[DETAILS_ID]);
                    arrayElementOneArray.set(id, p);
     //               System.out.println("Photo image found, FIRST RUN: "+fileName);
                    continue;
                }    
                System.out.println("Photo image NOT FOUND IN FIRST RUN: "+details[DETAILS_NAME]);
            }
            // look for match of fileID and desktopName
            int id = findJsonIndex(arrayElementOneArray, 
                new String[]{fileID, desktopName, currentlyUsed}, 
                new String[]{details[DETAILS_ID], details[DETAILS_NAME], "false"});                
            if(id>=0){
                // if match - use
                JSONObject p = (JSONObject) arrayElementOneArray.get(id);  
                String onName = (String)p.get(onlineName);
                String str = onName.substring(0, onName.lastIndexOf("."));
                ssOnlineMinusExt = u.addString(ssOnlineMinusExt, str);
                p.put(currentlyUsed,"true");
                arrayElementOneArray.set(id, p);
       //         System.out.println("Photo image FOUND: "+fileName);
                continue;
            }    
            System.out.println("Photo image NOT FOUND: "+details[DETAILS_NAME]);
        }                        
        for(int i = 0; i < listPublicImageFiles.length; i++) {
            if(!listPublicImageFiles[i].getName().endsWith(pngext) && !listPublicImageFiles[i].getName().endsWith(gifext))continue;
            if(listPublicImageFiles[i].getName().endsWith(gifext)){
                int g;
                g  = 0;
            }
            String details[];
            if((details = saveAllImages_GetPhotoDetails(listPublicImageFiles[i]))==null)continue; 
            if(details[DETAILS_NAME].equals("@")){
                continue;
            }
            int id = findJsonIndex(arrayElementOneArray, 
                new String[]{desktopName, fileID, currentlyUsed, isWordshark}, 
                new String[]{details[DETAILS_NAME], details[DETAILS_ID] ,"true", "0"});
                if(details[DETAILS_NAME].equalsIgnoreCase("nose_2")){
                    int g; 
                    g = 0;
                }  
            // skip past those that have been dealt with above
            if(id>=0){
                continue;
            }
            // add with desktopName taken from file name
            // mark as currently used
            JSONObject obSub = new JSONObject();
            String strwithoutprefix = getImageS3Name(null, details[DETAILS_NAME], ssOnlineMinusExt, pref);
            String str = pref+strwithoutprefix;
            ssOnlineMinusExt = u.addString(ssOnlineMinusExt,  str);  
            obSub.put(isWordshark, "0");
            obSub.put(currentlyUsed,"true");
            obSub.put(animated, details[DETAILS_ISANIMATED]);
            obSub.put(fileID, details[DETAILS_ID]);
            obSub.put(word, getPlainString(details[DETAILS_NAME], pref));
            obSub.put(desktopName, details[DETAILS_NAME]);
            String str2 = str+((details[DETAILS_ISANIMATED]=="1")?gifext:pngext);
            obSub.put(onlineName, str2); 
            obSub.put(s3key, str2.substring(0, str2.indexOf("_")).toLowerCase()+"/"+str2);
            arrayElementOneArray.add(obSub);
            System.out.println("Photo image, CREATE NEW: "+details[DETAILS_NAME]);
            if(u.findString(createPhotos, details[DETAILS_NAME])<0)
               createPhotos = u.addString(createPhotos, details[DETAILS_NAME]+(details[DETAILS_ISANIMATED].equals("1")?gifext:pngext));
        }

        // write out
        System.out.println("write out");
        JSONObject objectmain = new JSONObject();
        objectmain.put(elements, arrayElementOneArray);          
        try (FileWriter file = new FileWriter(outPath)) {
            file.write(objectmain.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }  
         JSONArray OneArray = new JSONArray();
          for(int i = 0; i < createWordsharks.length; i++){
              OneArray.add(createWordsharks[i]);        
          }
          try (FileWriter file = new FileWriter(DoImageScreenshots.toDoIWSJsonPath)) {
             file.write(OneArray.toJSONString());
             file.flush();
          } catch (IOException e) {
                e.printStackTrace();
          }        
        
         OneArray = new JSONArray();
          for(int i = 0; i < createPhotos.length; i++){
              OneArray.add(createPhotos[i]);        
          }
          try (FileWriter file = new FileWriter(DoImageScreenshots.toDoIPHOTOJsonPath)) {
             file.write(OneArray.toJSONString());
             file.flush();
          } catch (IOException e) {
                e.printStackTrace();
          }

        System.out.println("-----------------------------------------------------------");
        checkAllImages(null);
    }
    
    private String[] saveAllImages_GetWordsharkImDetails(String sname, String db){
        String ret[] = new String[3];
        sname = sname.trim().toLowerCase();
        if(sname.trim().equals(""))return null;
        if(sname.indexOf("_")>=0)return null;
        int libNo = -1;
        for(int i = 0; i < sharkStartFrame.publicImageLib.length; i++){
            String s = sharkStartFrame.publicImageLib[i];
            s = s.substring(s.lastIndexOf(shark.sep)+1);
            if(s.equals(db)){
                libNo = i;
            }
        }
        sharkImage im1 = sharkImage.find(sname, libNo);  
        ret[DETAILS_NAME] = sname;
        ret[DETAILS_ISANIMATED] = (im1.c.length>1 && (im1.im.controls[0].min>0 || im1.im.controls[0].max>0))?"1":"0";
        ret[DETAILS_ID] =  getSaveSharkImageID(im1.im);
        return ret;
    }
    
    
    private String[] saveAllImages_GetPhotoDetails(File file){
        String ret[] = new String[3];
        String fileName = file.getName();
        if(fileName.startsWith("."))return null;
        String filePath = file.getAbsolutePath();
        ret[DETAILS_ID] =  getImageFileID(filePath);
        int k;
        String ext = "."+fileName.substring((k=fileName.lastIndexOf("."))+1);
        ret[DETAILS_ISANIMATED] = ext.equals(gifext)?"1":"0";
        fileName  = fileName.substring(0, k).toLowerCase();   
        ret[DETAILS_NAME] = unCharToAscii(fileName);  
        return ret;
    }

    /*
    
    //IMAGE PHASE 1
    // make sure the text files of pubilcImage and publicImageSent are up to date
    // before doing this
    
    // goes through the list of images in the publicimage files  FROM A TEXT FILE??
    
    public void saveAllImages(String poolPhotoPath, boolean wantOutput){
        String dbs[] = new String[]{ "publicimage", "publicimageSENT"};
        
        String ssFile[] = new String[]{};
        String ssDesktop[] = new String[]{};
        String ssOnlineMinusExt[] = new String[]{};
        String ssOnline[] = new String[]{};    
        String vocabdbs[] = new String[]{"publicimageSENT"};
        String ssWordshark[] = new String[]{};
        String ssVocabulary[] = new String[]{};
        String ssPlain[] = new String[]{};        
        String ssIsAnimated[] = new String[]{}; 
        
        // do IWS stuff
        // String pref = "IWS_";
        String allImages[] = u.readFile("C:\\Users\\paulr\\Documents\\NetBeansProjects\\jbproject6_3\\allwspublicimage.txt");
        String allImagesSent[] = u.readFile("C:\\Users\\paulr\\Documents\\NetBeansProjects\\jbproject6_3\\allwspublicimageSENT.txt");
        
        String databs[][] = new String[][]{allImages, allImagesSent};
        
        
        String imagePoolPath = "C:\\Users\\paulr\\Dropbox\\PhotographicResourcesForOnlineIWSSrc\\publicimage\\";
        String imagePoolPathSent = "C:\\Users\\paulr\\Dropbox\\PhotographicResourcesForOnlineIWSSrc\\publicimageSENT\\";
        String finalImagePoolPath = "E:\\Shark Screen Shots\\outputOnlineNamed\\";
        String listPublicImage[] =  db.list(dbs[0], db.IMAGE);
        String listPublicImageSENT[] =  db.list(dbs[1], db.IMAGE);
        for(int k = 0; k < databs.length; k++){
            for(int i = 0; i < databs[k].length; i++){
                String image = databs[k][i].toLowerCase();

                if(image.trim().equals(""))
                    continue;
                String s = charToAscii(image);
                boolean animated = false;
                String pref = pre_wordshark_images + "_";
                if((new File((k==0?imagePoolPath:imagePoolPathSent)+s+gifext)).exists())animated = true;
                else if((new File((k==0?imagePoolPath:imagePoolPathSent)+s+pngext)).exists())animated = false;
                else{
                    u.okmess(shark.programName, "problem1");
                }
                String currdb = k==0?dbs[0]:dbs[1];
                String strwithoutprefix = getImageS3Name(currdb, image, ssOnlineMinusExt, pref);
                String str = pref+strwithoutprefix;
                if(str == null){
                    continue;
                }
                ssFile = u.addString(ssFile, currdb);
                ssDesktop = u.addString(ssDesktop, image);
                ssOnlineMinusExt = u.addString(ssOnlineMinusExt,  str);
                ssOnline = u.addString(ssOnline,  str+(animated?gifext:pngext));
                ssVocabulary = u.addString(ssVocabulary, (u.findString(vocabdbs,currdb)>=0 || image.indexOf("@@")>=0)?"1":"0");
                ssWordshark = u.addString(ssWordshark, ("1"));
                ssPlain = u.addString(ssPlain,  getPlainString(image, pref));
                ssIsAnimated = u.addString(ssIsAnimated,animated?"1":"0");  
                if(wantOutput){
                    try{
                        u.copyfile(animated?new File((k==0?imagePoolPath:imagePoolPathSent)+s+gifext):new File((k==0?imagePoolPath:imagePoolPathSent)+s+pngext), new File(finalImagePoolPath+str+(animated?gifext:pngext)));
                    }
                    catch(Exception exp){
                        u.okmess(shark.programName, "problem3");
                    }
                }
            }
        }
     
            String pref = pre_photographs + "_";
            String exts[] = new String[]{pngext};
            String imagePool[] = new File(poolPhotoPath).list();
            for(int i = 0; i < dbs.length; i++){
                String ss[] = i==0?listPublicImage:listPublicImageSENT;
                for(int k = 0; k < ss.length; k++){                    
                   if(ss[k].trim().equals(""))
                       continue;
               //    String strwithoutprefix = getImageS3Name(dbs[i], ss[k], ssOnline, pref);
                   String strwithoutprefix = getImageS3Name(dbs[i], ss[k], ssOnlineMinusExt, pref);
                   String str = pref+strwithoutprefix;
                   if(str == null){
                       continue;
                   }
                   String foundext = null;
                   for(int n = 0; n < exts.length; n++){
                       String tosearch = charToAscii(ss[k]);      // need to change this when the photos get renamed to IPHOTO_
                       if(u.findString(imagePool, tosearch+exts[n])>=0){
                           str+=exts[n];
                           foundext = exts[n];
                           break;
                       }
                   }
                   if(foundext == null)
                       continue;
                   
                   ssFile = u.addString(ssFile, dbs[i]);
                   ssDesktop = u.addString(ssDesktop, ss[k]);
                   ssOnlineMinusExt = u.addString(ssOnlineMinusExt,  str.substring(0, str.lastIndexOf(".")));
                   ssOnline = u.addString(ssOnline,  str);
                   ssVocabulary = u.addString(ssVocabulary, (u.findString(vocabdbs,dbs[i])>=0 || ss[k].indexOf("@@")>=0)?"1":"0");
                   ssWordshark = u.addString(ssWordshark, ("0"));
                   ssPlain = u.addString(ssPlain,  getPlainString(ss[k], pref));
                   ssIsAnimated = u.addString(ssIsAnimated,  (foundext.indexOf("gif")>=0)?"1":"0");  
               }                      
            }
 
        writeImagesJson(fileImages, ssDesktop, ssFile, ssOnline, ssVocabulary, ssWordshark, ssPlain, ssIsAnimated);
        int g;
        g = 0;
    }
    */

    
    
    public void lookupAndCopyToOnlinePhoto(boolean specificOnes){
        // convert from desktopName to onlineName
        JSONParser parser = new JSONParser();
        JSONObject json = null;
        JSONArray results = null;
        String onlineName = null;
        try{
            json = (JSONObject)parser.parse(new FileReader(filePublicImages));
            results = (JSONArray) json.get(elements);                  
        }
        catch(Exception e){
            int h; 
            h = 0;
        }
        String out = "D:\\WordsharkImagePhotoOUTRenamed";
        String ss[] = null;
        if(specificOnes) ss = getToDoList(DoImageScreenshots.toDoIPHOTOJsonPath, true);
        else{
            File listPublicImageFiles[] =  new File(photoPoolPath).listFiles();
            for(int i = 0; i < listPublicImageFiles.length; i++) {
                String fileName = listPublicImageFiles[i].getName();
                if(fileName.endsWith(".ini"))continue;  //Desktop.ini
                if(fileName.startsWith("."))continue;  //.dropbox
                fileName = unCharToAscii(fileName);
 //               fileName = fileName.substring(0, fileName.lastIndexOf(".")).toLowerCase();
                fileName = fileName.toLowerCase();
   //     fileName =  adjustName(fileName);
                
                if(fileName.trim().equals(""))continue;
                ss = u.addString(ss, fileName);
            }
        }
        String outfiles[] = new String[]{};
        for(int i = 0 ; i < ss.length; i++){
            String ext = ss[i].substring(ss[i].lastIndexOf("."));
            ss[i] = ss[i].substring(0, ss[i].lastIndexOf("."));
            String an = charToAscii(ss[i]);
            File from = new File(photoPoolPath+shark.sep+an+ext);
            boolean exists = from.exists();
            // check with capitalised inital letter
            if(!exists) {
                from = new File(photoPoolPath+shark.sep+String.valueOf(an.charAt(0)).toUpperCase() + an.substring(1)  +ext);
                exists = from.exists();
            }
            if(exists){
                // convert from desktopName to onlineName

                    int k = findJsonIndex(results, new String[]{desktopName, isWordshark, currentlyUsed}, new String[]{ss[i], "0", "true"});
                    if(k < 0){
                        System.out.println("NOT IN THE IMAGES JSON:  "+ss[i]);
                        continue;
                    }
                    JSONObject p = (JSONObject) results.get(k);
                    onlineName = (String)p.get(ToolsOnlineResources.onlineName);                    
                    if(u.findString(outfiles, out+shark.sep+onlineName)<0){
                        outfiles = u.addString(outfiles, out+shark.sep+onlineName);
                    }
                    else{
                        int g;
                        g = 0;
                     
                    }
                u.copyfile(from, new File(out+shark.sep+onlineName));
            }
            else
                System.out.println("PHOTO FILE NOT FOUND: "+from.getAbsolutePath());
        }
    }
    
     public void checkResourcesExists(boolean doImage, boolean doRecordings){
        String srcBaseRecordingPath = "E:\\S3syncRecordings\\";
        String srcBaseImagePath = "E:\\S3syncImages\\";
        JSONParser parser = new JSONParser();
        JSONObject jsonIm = null;
        JSONArray resultsIm = null;
        JSONObject jsonRec = null;
        JSONArray resultsRec = null;
        try{
            jsonIm = (JSONObject)parser.parse(new FileReader(filePublicImages));
            resultsIm = (JSONArray) jsonIm.get(elements);
        }
        catch(Exception e){
            int h; 
            h = 0;
        }
       try{
            jsonRec = (JSONObject)parser.parse(new FileReader(readJsonRecording));
            resultsRec = (JSONArray) jsonRec.get(elements);
        }
        catch(Exception e){
            int h; 
            h = 0;
        }
        for(int k = 0; k < resultsIm.size(); k++) {
            JSONObject p = (JSONObject) resultsIm.get(k);
            if(((String)p.get(currentlyUsed)).equals("false"))continue;
            String key = ((String)p.get(s3key)).replace("/", "\\");

            if(!new File(srcBaseImagePath+key).exists()){
                System.out.println("Doesn't exist -IM - :  "+srcBaseImagePath+key);
            }
        }
        for(int k = 0; k < resultsRec.size(); k++) {
            JSONObject p = (JSONObject) resultsRec.get(k);
            if(((String)p.get(currentlyUsed)).equals("false"))continue;
            String key = ((String)p.get(s3key)).replace("/", "\\");
            if(key.indexOf("game\\GAME")>0)continue;
            if(!new File(srcBaseRecordingPath+key).exists()){
                System.out.println("Doesn't exist - REC - :  "+srcBaseRecordingPath+key);
            }
        }                    
 }    
    /*
 public void checkImagesExists(){
    String srcPath = "F:\\IWS_Images_Pool_Converted\\ALLOUT_OUTPUT";
        JSONParser parser = new JSONParser();
        JSONObject json = null;
        JSONArray results = null;
        try{
            json = (JSONObject)parser.parse(new FileReader(filePublicImages));
            results = (JSONArray) json.get(elements);
        }
        catch(Exception e){
            int h; 
            h = 0;
        }
        File ff[] = new File(srcPath).listFiles();
          for(int i = 0; i < ff.length; i++){     
            boolean found =false; 
            String nam = ff[i].getName();
            if(nam.startsWith("."))continue;
            String name = nam.substring(0, nam.lastIndexOf(".")); 
            inner: for(int k = 0; k < results.size(); k++) {
                JSONObject p = (JSONObject) results.get(k);
                String olName = ((String)p.get(onlineName));
                if(!olName.startsWith(pre_wordshark_images))continue inner;
                String inStr = charToAscii((olName).toLowerCase());                  
 //               if(name.equalsIgnoreCase(inStr)){
                if(name.equalsIgnoreCase(inStr.substring(0, inStr.indexOf(".")))){
                    found =true;
                    break;
                }
            }
            if(!found){
                System.out.println(ff[i].getName());
                int g; 
                 g  = 0;
            }
        }           
 }    
    */
     
     
     
 public void renameToOnlineNameImages(boolean doIWS){ 
     String srcPath = "D:\\WordsharkImageScreenshotsOUTGiffed";
 //   String srcPath = "D:\\OneDrive\\Desktop\\ok";
         String descPath = "D:\\WordsharkImageScreenshotsOUTRenamed";
        JSONParser parser = new JSONParser();
        JSONObject json = null;
        JSONArray results = null;
        try{
            json = (JSONObject)parser.parse(new FileReader(filePublicImages));
            results = (JSONArray) json.get(elements);
        }
        catch(Exception e){
            int h; 
            h = 0;
        }
        File ff[] = new File(srcPath).listFiles();
          for(int i = 0; i < ff.length; i++){     
            boolean found =false; 
            String nam = ff[i].getName();
            if(nam.startsWith("."))continue;
            String name = nam.substring(0, nam.lastIndexOf(".")); 
            inner: for(int k = 0; k < results.size(); k++) {
                JSONObject p = (JSONObject) results.get(k);
                String currUsed = ((String)p.get(currentlyUsed));
                if(currUsed.equals("false"))continue;
                String olName = ((String)p.get(onlineName));
                
                String deskName = ((String)p.get(desktopName));
                if(doIWS && !olName.startsWith(pre_wordshark_images))continue inner;
                if(!doIWS && olName.startsWith(pre_wordshark_images))continue inner;
                String inStr = charToAscii((deskName).toLowerCase());                  
                if(name.equalsIgnoreCase(inStr)){
                    found =true;
                    try{
                       FileUtils.copyFile(ff[i], new File(descPath+shark.sep+olName)); 
                        int f;
                        f = 0;
                    }
                    catch(Exception ee){
                        int h;
                        h = 0;
                    }
                    break;
                }
            }
            if(!found){
                System.out.println(ff[i].getName());
                int g; 
                 g  = 0;
            }
        }      
 }          
     
    /*
 public void renamePhotoToOnlineNameImages(){ 
     String ss[] = getToDoList(DoImageScreenshots.toDoIPHOTOJsonPath, true);
         String descPath = "D:\\PhotosRenamed";
         
        JSONParser parser = new JSONParser();
        JSONObject json = null;
        JSONArray results = null;
        try{
            json = (JSONObject)parser.parse(new FileReader(filePublicImages));
            results = (JSONArray) json.get(elements);
        }
        catch(Exception e){
            int h; 
            h = 0;
        }
          for(int i = 0; i < ss.length; i++){     
            boolean found =false; 
            String nam = ss[i];
            if(nam.startsWith("."))continue;
            String name = nam.substring(0, nam.lastIndexOf(".")); 
            inner: for(int k = 0; k < results.size(); k++) {
                JSONObject p = (JSONObject) results.get(k);
                String currUsed = ((String)p.get(currentlyUsed));
                if(currUsed.equals("false"))continue;
                String olName = ((String)p.get(onlineName));
                
                String deskName = ((String)p.get(desktopName));
                if(olName.startsWith(pre_wordshark_images))continue inner;
                String inStr = charToAscii((deskName).toLowerCase());                  
                if(name.equalsIgnoreCase(inStr)){
                    found =true;
                    try{
                       FileUtils.copyFile(new File( photoPoolPath  + shark.sep +ss[i]), new File(descPath+shark.sep+olName)); 
                        int f;
                        f = 0;
                    }
                    catch(Exception ee){
                        int h;
                        h = 0;
                    }
                    break;
                }
            }
            if(!found){
                System.out.println(ss[i]);
                int g; 
                 g  = 0;
            }
        }      
 }     
    
    */
    
 public void renameRecordings(){ 
     String src = fileRecordingsBase+"Files3\\publicsent2\\";
     String output = fileRecordingsBase+"Files4\\publicsent2\\";   
     String basename= "S-";
     

     int count = 1;
     int countoutstart = 10614;
  //   String ss[] = (new File(src)).list();

     while((new File(src+basename+toZeroNumber(String.valueOf(count))+mp3ext)).exists()){
        File fsrc = new File(src+basename+toZeroNumber(String.valueOf(count))+mp3ext);
        File fdest = new File(output+basename+toZeroNumber(String.valueOf(countoutstart))+mp3ext);
        try{
            FileUtils.copyFile(fsrc, fdest);
        }
        catch(Exception e){
            int g;
            g = 0;
        }
        count++;
        countoutstart++;
     }
 }   



 
 private String toZeroNumber(String s){
     while(s.length()<5){
         s = "0"+s;
     }
     return s;
 }
 
 
 public void organiseSoundlessRecordings(){
    String ss[] = u.readFile(fileRecordingsBase+"torecord.txt");
    for(int i = 0; i < ss.length; i++){
        String s = ss[i];
        int k = s.indexOf("    ");
        if(k>=0){
            String s1 = s.substring(0, k);
            k = Integer.parseInt(String.valueOf(s.charAt(s.length()-1)));
            s = s1;
            String dbname = recordingFiles[k];
            u2_base.writeFile(fileRecordingsBase+dbname+txtext, new String[]{s1});
        }
        else{
            int g;
             g = 0;
        }
    }
 }
 
 /*
 Read list of numerical mp3s
 Read list of desktop names
 For each of the recording types, read that mapping json, copy and name files to output folder

   public void copyRenameRecordings3(String dbs[]){
        JSONParser parser = new JSONParser();
        String basesrclists = fileRecordingsBase+fileRecordingsSubFolderLists;
        String basesrcfiles = fileRecordingsBase+fileRecordingsSubFolderFiles;
        String baseoutput = fileRecordingsBase+fileRecordingsSubFolderOutput;
        String srcListPaths[][] = new String[dbs.length][];
        File srcFilePaths[][] = new File[dbs.length][];
        for(int i = 0; i < srcListPaths.length; i++) {
            srcListPaths[i] = u.readFile(basesrclists+dbs[i]+txtext);
            srcFilePaths[i] = new File(basesrcfiles+dbs[i]).listFiles();                
        }
        
        try {
            for(int i = 0; i < sprefixes.length; i++) {
                JSONObject json = (JSONObject)parser.parse(new FileReader("C:\\jshark-shared2\\publictopicsPrint\\"+strMapping+sprefixes[i]+jsonext));
                JSONArray results = (JSONArray) json.get(elements); 
                for(int j = 0; j < results.size(); j++) {   
                    JSONObject p = (JSONObject) results.get(j);
                    String desktopname = (String)p.get(CsvStruct.type_desktopname);
                    String desktopdb = (String)p.get(CsvStruct.type_desktopdb);
                    String s = (String)p.get(CsvStruct.type_filename);
                    int k;
                    if((k=s.indexOf('.'))>=0)
                        s = s.substring(0, k);
                    int dbi= u.findString(dbs, desktopdb);
                            if(dbi<0){
                                dbi = 0; // GAME
                            }
                    int f = u.findString(srcListPaths[dbi], desktopname); 
                    if(f < 0){
                        System.out.println(desktopname + "    " + String.valueOf(dbi));
                        continue;
                    }
                    File scrFile = srcFilePaths[dbi][f];
                    if(!scrFile.exists())
                        continue;
                    File parentOutput = new File(baseoutput);
                    parentOutput.mkdirs();
                    // find the recording and copy rename it
                    File topRecording = new File(baseoutput+s+mp3ext);

                    try{
                        if(!topRecording.exists()){
                            if(s.indexOf('_')<0){
                                int h;
                                 h = 0;
                            }
                            String pref = s.substring(0, s.indexOf('_'));
                            (new File(baseoutput+pref)).mkdir();
                            File topRecording2 = new File(baseoutput+pref+shark.sep+s+mp3ext);
                            FileUtils.copyFile(scrFile, topRecording2); 
                        }
                    }
                    catch(Exception ee){
                        int h;
                        h = 0;
                    }                      
                }     
            }
            
            
            
            
            

        }
        catch(Exception e){
            int g;
             g = 0;
        }
   }
     
  */
 
   public void makeListOfUnrecorded(){
       // in the form of            contradictory    1  -the number being the database, i.e. publicsay1
       String ss[] = u.readFile(fileRecordingsBase+"torecord"+txtext);
   }
 
 
     /*
   
   PURPOSE - to rename the wordshark recordings to their online names - 
   online names determined by recordings.json
   
   
  Read list of numerical mp3s
 Read list of desktop names
 read recordings json, copy and name files to output folder 
   IGNORES recordings which aren't present in Desktop names in current sha files
   
   
   
   */
   public void copyRenameRecordings2(String dbs[]){
        JSONParser parser = new JSONParser();
 //       String jsonpath = "C:\\jshark-shared2\\publictopicsPrint\\recordings"+jsonext;
        String basesrclists = fileRecordingsBase+fileRecordingsSubFolderLists;
        String basesrcfiles = fileRecordingsBase+fileRecordingsSubFolderFiles;
        String baseoutput = fileRecordingsBase+fileRecordingsSubFolderOutput;



        
        
        String srcListPaths[][] = new String[dbs.length][];
        File srcFilePaths[][] = new File[dbs.length][];
        for(int i = 0; i < srcListPaths.length; i++) {
                srcListPaths[i] = u.readFile(basesrclists+dbs[i]+txtext);
                for(int j = 0; j < srcListPaths[i].length; j++) {
                    srcListPaths[i][j] = srcListPaths[i][j].replace("ï»¿", "");
                    srcListPaths[i][j] = srcListPaths[i][j].replace("Â·", "·");
                    srcListPaths[i][j] = srcListPaths[i][j].replace("â", "'");
                    srcListPaths[i][j] = srcListPaths[i][j].replace("Â£", "£");
                    srcListPaths[i][j] = srcListPaths[i][j].replace("Â²", "²");
                    srcListPaths[i][j] = srcListPaths[i][j].replace("Â½", "½");
                    srcListPaths[i][j] = srcListPaths[i][j].replace("Â³", "³");
                    srcListPaths[i][j] = srcListPaths[i][j].replace("Â¾", "¾");
                    srcListPaths[i][j] = srcListPaths[i][j].replace("â", "⅞");
                    
                    
                }
                srcFilePaths[i] = new File(basesrcfiles+dbs[i]).listFiles();                

        }
        
        try {

            
            JSONObject json = (JSONObject)parser.parse(new FileReader(fileSounds));
            JSONArray results = (JSONArray) json.get(elements); 
            for(int i = 0; i < results.size(); i++) {
                JSONObject p = (JSONObject) results.get(i);
                String sdb = (String)p.get(database);
                boolean mixeddb = sdb.trim().equals("");
                if(mixeddb)sdb = gameRecordings;
                else if(!sdb.equals("") && u.findString(dbs, sdb)<0){
                    continue;
                }
                String s;
                if((s=(String)p.get(onlineName))==null || s.trim().equals("")){
                    continue;
                }
                String desktopname;
                if((desktopname=(String)p.get(desktopName))==null || desktopname.trim().equals("")){
                    continue;
                }
                int dbi= u.findString(dbs, sdb);                        
                int f = u.findString(srcListPaths[dbi], desktopname); 
                if(f < 0){
                    System.out.println(desktopname + "    " + String.valueOf(dbi));
                    continue;
                }
                File scrFile = srcFilePaths[dbi][f];
                if(!scrFile.exists()){
                    System.out.println("File doesn't exist:   "+scrFile.getAbsolutePath());
                    continue;
                }
                File parentOutput = new File(baseoutput);
                parentOutput.mkdirs();
                // find the recording and copy rename it
                File topRecording = new File(baseoutput+s+mp3ext);
                try{
                    if(!topRecording.exists()){
                        String pref = s.substring(0, s.indexOf('_'));
                        (new File(baseoutput+pref)).mkdir();
                        File topRecording2 = new File(baseoutput+pref+shark.sep+s);
                        FileUtils.copyFile(scrFile, topRecording2); 
                    }
                }
                catch(Exception ee){
                    int h;
                    h = 0;
                }  
           /*
                // make subfolder with online name
                File subFolder = new File((baseoutput+s).toLowerCase());
                subFolder.mkdirs();
                // fill with text file of Desktop name
                String strsubfolder = subFolder.getAbsolutePath()+(subFolder.getAbsolutePath().endsWith(shark.sep)?"":shark.sep);
                writeFile(strsubfolder+s+textext, new String[]{desktopname});
             
                // add recording to this too
                try{
                    File lowerRecording = new File(strsubfolder+sdb+"_"+s+ext);
                    if(!lowerRecording.exists())
                        FileUtils.copyFile(scrFile, lowerRecording);
                    else{
                        int g;
                        g = 0;
                    }
                }
                catch(Exception ee){
                    int h;
                    h = 0;
                } 
                */               
            }
        }
        catch(Exception e){
            int g;
             g = 0;
        }
   }
    
    
   /*
   public void copyRenameRecordings(String jsonpath, String dbs[]){
        JSONParser parser = new JSONParser();
        String basesrclists = fileRecordingsBase+fileRecordingsSubFolderLists;
        String basesrcfiles = fileRecordingsBase+fileRecordingsSubFolderFiles;
        String baseoutput = fileRecordingsBase+fileRecordingsSubFolderOutput;
        String ext = ".mp3";
        
        
        String srcListPaths[][] = new String[dbs.length+1][];
        File srcFilePaths[][] = new File[dbs.length+1][];
        for(int i = 0; i < srcListPaths.length; i++) {
            if(i==srcListPaths.length-1){
                srcListPaths[i] = readJsonList(basesrclists+gameRecordings+"\\"+gameRecordings+jsonext);// readFile(basesrclists+gameRecordings+"\\"+gameRecordings+textext);
                srcFilePaths[i] = new File(basesrcfiles+gameRecordings).listFiles();                
            }
            else{
                srcListPaths[i] = readJsonList(basesrclists+dbs[i]+"\\"+dbs[i]+jsonext);// readFile(basesrclists+dbs[i]+"\\"+dbs[i]+textext);
                srcFilePaths[i] = new File(basesrcfiles+dbs[i]).listFiles();                
            }
        }
        
        try {
            JSONObject json = (JSONObject)parser.parse(new FileReader(jsonpath));
            JSONArray results = (JSONArray) json.get(elements); 
            for(int i = 0; i < results.size(); i++) {
                JSONObject p = (JSONObject) results.get(i);
                String sdb = (String)p.get(database);
                boolean mixeddb = sdb.trim().equals("");
                if(mixeddb)sdb = gameRecordings;
                else if(!sdb.equals("") && u.findString(dbs, sdb)<0){
                    continue;
                }
                String s;
                if((s=(String)p.get(onlineName))==null || s.trim().equals("")){
                    continue;
                }
                String desktopname;
                if((desktopname=(String)p.get(desktopName))==null || desktopname.trim().equals("")){
                    continue;
                }
                int dbi= u.findString(dbs, sdb);                        
                int f = u.findString(srcListPaths[dbi], desktopname); 
                if(f < 0){
                    System.out.println(desktopname + "    " + String.valueOf(dbi));
                    continue;
                }
                File scrFile = srcFilePaths[dbi][f];
                if(!scrFile.exists())
                    continue;
                File parentOutput = new File(baseoutput);
                parentOutput.mkdirs();
                // find the recording and copy rename it
                File topRecording = new File(baseoutput+s+ext);
     
                try{
                    if(!topRecording.exists())
                        FileUtils.copyFile(scrFile, topRecording); 
                }
                catch(Exception ee){
                    int h;
                    h = 0;
                }  
        
                // make subfolder with online name
                File subFolder = new File((baseoutput+s).toLowerCase());
                subFolder.mkdirs();
                // fill with text file of Desktop name
                String strsubfolder = subFolder.getAbsolutePath()+(subFolder.getAbsolutePath().endsWith(shark.sep)?"":shark.sep);
                writeFile(strsubfolder+s+txtext, new String[]{desktopname});
                
                // add recording to this too
                try{
                    File lowerRecording = new File(strsubfolder+sdb+"_"+s+ext);
                    if(!lowerRecording.exists())
                        FileUtils.copyFile(scrFile, lowerRecording);
                    else{
                        int g;
                        g = 0;
                    }
                }
                catch(Exception ee){
                    int h;
                    h = 0;
                }                
            }
        }
        catch(Exception e){
            int g;
             g = 0;
        }
   }
 */
       
private String[] readJsonList(String path){
        JSONParser parser = new JSONParser();
        String res[] = new String[]{};
        try {
            JSONObject json = (JSONObject)parser.parse(new FileReader(path));
            JSONArray results = (JSONArray) json.get(elements); 
            for(int i = 0; i < results.size(); i++) {
                JSONObject p = (JSONObject) results.get(i);
                String desktopname;
                if((desktopname=(String)p.get(desktopName))==null || desktopname.trim().equals("")){
                    continue;
                }
                res = u.addString(res, desktopname);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(res.length==0)return null;
        return res;
}
   

/*
Use to add the prefix of the online name to e.g. Gretchen's images
*/

public void addPrefixesToImages(){
    String filePath = "G:\\Images\\Wordshark images\\Shrunk\\animation\\";
    String prFolder = "Prefixed\\";
    String prefix = pre_wordshark_images+"_";
    File mainf = new File(filePath);
    File f[] = mainf.listFiles();
    File mainDest = new File(filePath+prFolder);
    mainDest.mkdir();
    for(int i = 0; i < f.length; i++){
         try{
               FileUtils.copyFile(f[i], new File(filePath+prFolder+ prefix + f[i].getName())); 
         }
         catch(Exception ee){
               int h;
               h = 0;
         }            
    }
    
    
    
}
    

public void makeDummyRecordings(){
        String baseS3RecordingsFolder = "D:\\S3syncRecordings\\";
        String dummyOutputFolder = "D:\\DummyOutput\\";
        File dummySoundFile = new File("D:\\toberecorded.mp3");
        JSONParser parser = new JSONParser();
        try {
            JSONObject json2 = (JSONObject)parser.parse(new FileReader(readJsonRecording));       
            JSONArray recResults = (JSONArray) json2.get(elements); 
            for(int i = 0; i <recResults.size(); i++) {
                JSONObject p = (JSONObject) recResults.get(i);
                String bn = (String)p.get(currentlyUsed);
                if(bn.equals("false"))continue;
                String k = (String)p.get(s3key);
                String kpc = k.replace("/", "\\");
                if(!(new File(baseS3RecordingsFolder+kpc)).exists()){
                    String subfolder = k.substring(0, k.indexOf("/"));
                    try{
                        (new File(dummyOutputFolder+subfolder)).mkdirs();
                          FileUtils.copyFile(dummySoundFile, new File(dummyOutputFolder+kpc)); 
                    }
                    catch(Exception ee){
                          int h;
                          h = 0;
                    }                     
                }
               
            }
        }        
        catch (Exception e) {
            e.printStackTrace();
        }            
    
}

/*
   public void makeDummyRecordings(String srcFile, String destListFilePath, String destOutputPath){
       String list[] = u.readFile(destListFilePath);
       File sFile = new File(srcFile);
       for(int i = 0; i < list.length; i++){
         try{

             FileUtils.copyFile(sFile, new File(destOutputPath+list[i]));
         }
         catch(Exception ee){
               int h;
               h = 0;
         }           
       }       
   }
   
   
   public void makeFindDummyRecordings(String mp3Folder){
       File sFile = new File(mp3Folder);  // parent folder contain sub folders of recordings types
        JSONParser parser = new JSONParser();
        JSONArray jsons = null;
        try {
            JSONObject json = (JSONObject)parser.parse(new FileReader(readJsonRecording));
            jsons = (JSONArray) json.get(elements);

        } catch (Exception e) {
            e.printStackTrace();
        }           
       File ffs[] = sFile.listFiles();  
       for(int i = 0; i < ffs.length; i++){
           File sub = ffs[i];
           System.out.println("-------------------------");
           System.out.println(sub.getName());
           System.out.println("-------------------------");
           File subffs[] = sub.listFiles();
            for(int j = 0; j < subffs.length; j++){
                String name = subffs[j].getName();
                String s = findJsonRecordingAnyDbFromOnlineName(jsons, name);
                System.out.println(s);
            }            
       }       
   }   
           
       */    
   
   
/*
Looks at the recordings.json file and writes out to a text file the online names
of the needed recordings (for non-sentences this avoids duplications. It wouldn't work for sentences
as these are of the format SENT_TargetWord - therefore duplicates [i.e. SENT_TargetWord, SENT_TargetWord_2 etc] 
are not redundant)
   
   + copies the files into subfolders (named with prefix)
*/
   public void writeOutJsonRecordings(String filePath, String key, String db[], boolean doFileCopy, boolean doOut, boolean forVoiceArtist){
       String srcPath = "G:\\OnlineNameRecordingsBucket\\";
       String destPath = "C:\\jshark-shared\\publictopicsPrint\\";
       String destTextPath = "C:\\jshark-shared\\publictopicsPrint\\";
       String csvPath = "C:\\jshark-shared\\publictopicsPrint\\"+strMapping;

       
        JSONParser parser = new JSONParser();
        try {
            JSONObject json = (JSONObject)parser.parse(new FileReader(filePath));
            JSONArray results = (JSONArray) json.get(elements); 
            String done[] = new String[]{};
            for(int pf = 0; pf < sprefixes.length; pf++){
                String res[] = new String[]{};
                ArrayList alCsv = new ArrayList();
                for(int i = 0; i < results.size(); i++) {
                    JSONObject p = (JSONObject) results.get(i);
                    if(key !=null && (String)p.get(key)==null){
                        continue;
                    }
                  
                    String sdb = (String)p.get(database);
                    if(!sdb.equals("") && u.findString(db, sdb)<0){
                        continue;
                    }
                    String s;
                    if((s=(String)p.get(onlineName))==null || !s.startsWith(sprefixes[pf]+"_")){
                        continue;
                    }
                    if(((String)p.get(currentlyUsed)).equals("false")){
                        System.out.println("Not currently used recording:  "+s);
                        continue;
                    }  
                    // has the same onlinename already been done? if so skip this one as we don't want duplicates
                    if(u.findString(done, s)>=0)continue;
                    done = u.addString(done, s);
                    /*
                    if(s.startsWith(pre_phonicSound)){
                        int g;
                        g = 0;
                    }
                    if(!s.startsWith(pre_sentence) && !s.startsWith(pre_sentencesimple) && !s.startsWith(pre_sentencesimplefull)){
                        int l = s.lastIndexOf("_");
                        if(l>0){
                            String slast = s.substring(l+1);
                            String sdesk = (String)p.get(desktopName);
                            try{
                   //             Integer.parseInt(slast);
                                if((getFromJSONArray(results, key, sdb, s.substring(0, l), db, i, sdesk))){
                                    continue;
                                }
                            }
                            catch(Exception e){}
                        }
                    }
               */

                    res = u.addStringSort(res, s);
                    if(doOut)
                        alCsv.add(new CsvStruct((String)p.get(word), sprefixes[pf].toLowerCase()+"/"+s, 
                            s, (String)p.get(desktopName), sdb,  (String)p.get(fullSentence)   ,((String)p.get(vocab)).equals("1")?true:false));

                    if(doFileCopy){
                        try{
                              String subFolderStr = destPath+(sprefixes[pf].toLowerCase()+shark.sep);
                              File folder = new File(subFolderStr);
                              if(!folder.exists())folder.mkdir();
                              File fsrc = new File(srcPath+s);
                              FileUtils.copyFile(fsrc, new File(subFolderStr+s)); 
                        }
                        catch(Exception ee){
                              int h;
                              h = 0;
                        }             
                    }
                } 
                
                if(doOut){                   
                    //do json
                    JSONArray arrayElementOneArray = new JSONArray();
	                for(int i = 0; i < alCsv.size(); i++){
                               JSONObject obSub = new JSONObject();
                               CsvStruct csvs = (CsvStruct)alCsv.get(i);
                                obSub.put(CsvStruct.type_word, csvs.word);
                                obSub.put(CsvStruct.type_s3key, csvs.s3key);
                                obSub.put(CsvStruct.type_desktopname, csvs.desktopname);
                                obSub.put(CsvStruct.type_filename, csvs.filename);
                                obSub.put(CsvStruct.type_desktopdb, csvs.desktopdb);
                                obSub.put(CsvStruct.type_fullsent, csvs.fullsentence);
                                obSub.put(CsvStruct.type_isvocab, csvs.isvocab?"true":"false");
                                arrayElementOneArray.add(obSub);
	                }
                        JSONObject objectmain = new JSONObject();
                        objectmain.put(elements, arrayElementOneArray);          
                        try (FileWriter file = new FileWriter(csvPath+sprefixes[pf]+jsonext)) {

                            file.write(objectmain.toJSONString());
                            file.flush();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        jsonToHTMLRecordings("C:\\jshark-shared2\\publictopicsPrint\\", "C:\\jshark-shared2\\publictopicsPrint\\");
        int e;
        e = 9;
    }  
   
   
   
   public void textFilesToJason(){
       String path = "C:\\Users\\paulr\\Desktop\\jsonCatching\\";
       String letterNo = "4";
       String fileSuffix[] = new String[]{"-1","-2","-3","-4"};
       String jsonEles[] = new String[]{"sort_ag","sort_hn","sort_ot","sort_uz"};
       JSONObject obSub = new JSONObject();
       for(int i = 0; i < fileSuffix.length; i++){
           String ss[] = u.readFile(path+letterNo+fileSuffix[i]+txtext);
           JSONArray arrayElementOneArray = new JSONArray();
           for(int j = 0; j < ss.length; j++){
                arrayElementOneArray.add(ss[j]); 
           }  
           obSub.put(jsonEles[i], arrayElementOneArray);
       }          
        try (FileWriter file = new FileWriter(path+letterNo+jsonext)) {
            file.write(obSub.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
   }
   
   public void jsonToHTMLRecordings(String srcParent, String outputParent){  
       jsonToHTMLRecordings(srcParent, outputParent, null, false);
 
   }
   
   /*
   used for giving to the voice artists
   
   filterByDummyRecordingsPath = the files created for the new recordings. These are the only ones we want
   */
   public void jsonToHTMLRecordings(String srcParent, String outputParent, String filterByDummyRecordingsPath, boolean doYellowForSpaces){  
 ///      String suffix_mess = u.gettext("sentrecord4", "spelltheword");
       String testSentDesk1 = "Word";
       String testSentDesk2 = "Sentence";
       String testSentDesk3 = "Instruction";
  //     String hyperlinkTitle = "Link";
  //     String hyper = "=HYPERLINK(\"DesktopMP3s\\\"&B%&\".mp3\", \"play file\")";
       String td1 = "<td>";
       String td1_yellow = "<td style=\"background-color:yellow;\">";
       
       String td2 = "</td>";
       String tr1 = "<tr>";
       String tr2 = "</tr>";
       String beepText = "beep";
       boolean wantRecordingNumbering = true;
       boolean wantSpacesColoured = true;
       
       for(int j = 0; j < sprefixes.length; j++){  
           int recordingNumberingStart = 1;
           boolean testSents = sprefixes[j].equals(pre_sentencetest);
           boolean insertBeeps = sprefixes[j].equals(pre_sentence) || sprefixes[j].equals(pre_sentencesimple);
       //    boolean wantSoundFile = sprefixes[j].equals(pre_phonicSyllable) || sprefixes[j].equals(pre_phonicSound);
	            try {
                        
                        if(filterByDummyRecordingsPath != null){
                            File dfile = new File(filterByDummyRecordingsPath + "\\" + sprefixes[j].toLowerCase());
                            if(!dfile.exists())
                                continue;
                        }
                        String cnt = "<html><table>";
                        File ff = new File(outputParent+strMapping+sprefixes[j]+htmlext);
	                boolean newfile = !ff.exists();
	                if (newfile){
	                    ff.getParentFile().mkdir();
	                    ff.createNewFile();
	                }
                        cnt+=tr1;
                        
                        
                        if(wantRecordingNumbering){
                            cnt+= td1+td2;
                        }
                        if(!testSents)
                            cnt+= td1+CsvStruct.type_desktopname+td2;
                        else{
                            cnt+= td1+testSentDesk1+td2;
                            cnt+= td1+testSentDesk2+td2;
                            cnt+= td1+testSentDesk3+td2;
                        }
                        cnt+= td1+CsvStruct.type_filename+td2;
     //                   if(wantSoundFile){
    //                        cnt+= td1+hyperlinkTitle+td2;
     //                   }
	                cnt+=tr2;
	                                      
                        JSONParser parser = new JSONParser();
                        File file;
                        if(!((file=new File(srcParent+strMapping+sprefixes[j]+jsonext)).exists()))continue;
                        JSONObject json = (JSONObject)parser.parse(new FileReader(file.getAbsolutePath()));
                        JSONArray results = (JSONArray) json.get(elements);                        
                        for(int i = 0; i < results.size(); i++) {
                            JSONObject p = (JSONObject) results.get(i);
                            String filename = (String)p.get(CsvStruct.type_filename);
                            if(filterByDummyRecordingsPath != null){
                                File dfile = new File(filterByDummyRecordingsPath + "\\" + sprefixes[j].toLowerCase() + "\\" + filename);
                                if(!dfile.exists())continue;
                            }
                            
                            
                            
                            String desktop_word = (String)p.get(CsvStruct.type_desktopname);
                            
                            
                            
                            
                            cnt+=tr1;
                            
                            
                            if(wantRecordingNumbering){
                                cnt+= td1+String.valueOf(recordingNumberingStart).toString()+td2;
                                recordingNumberingStart++;
                            }
                            
                            if(insertBeeps){
                                sentence sent = new sentence(desktop_word);
                                
                                // one way to produce asterisks (so that I can remove the duplicates. i.e. add dittos)
                                // the other way for the voice artist - i.e. with the target word in square brackets
                                
                                String cl = sent.stripclozereplacewildcard();
                                String answers[] = sent.getAnswerList();
                                for(int m = 0; m < answers.length; m++){
                                    int k = cl.indexOf("*");
                                    cl = cl.substring(0, k) + "<b><i>["+answers[m]+"]</b></i>" + cl.substring(k+1);
                                }                             
                                desktop_word = cl;
                                /*
                                desktop_word = sent.stripclozereplacewildcard().replace("*", "<b><i>"+beepText+"</b></i>");
                               */ 
                                
                            }                            
                            if(!testSents){
                                String scurr = (desktop_word.contains(" ")&&doYellowForSpaces)?td1_yellow:td1;
                                cnt+= scurr+desktop_word+td2;
                            }
                            else{
                                int left=desktop_word.indexOf('[')+1;
                                int right= desktop_word.indexOf(']');
                                String w = desktop_word.substring(left, right);
                                String formattedWord = "<b><i>"+w+"</b></i>";
                                
                                cnt+= td1+formattedWord+td2;
                                String sent;
                                String sentword;
                                if(desktop_word.startsWith("[")){
                                    sentword = w.substring(0, 1).toUpperCase() + w.substring(1);
                                }
                                else{
                                    sentword = w;
                                }
                                sent = desktop_word.substring(0, left-1);
                                sent += "<b><i>"+sentword+"</b></i>";
                                sent += desktop_word.substring(right+1);        
                                sent = formatSentence(sent);
                                cnt+= td1+sent+td2;
   //                             cnt+= td1+suffix_mess.replaceAll("%", formattedWord)+td2;
                                cnt+= td1+td2;
                            }               
                            
                            cnt+= td1+filename.substring(0, filename.lastIndexOf("."))+td2;
        //                    if(wantSoundFile){
        //                        cnt+= td1+hyper.replaceAll("%", String.valueOf(i+2))+td2;
        //                    }          
        
        
        
        
                            
        
	                    cnt+=tr2;                           
                        }                        
                        cnt += "</html></table>";
                        cnt = substituteHtmlChars(cnt);
                        PrintWriter writer = new PrintWriter(outputParent+strMapping+sprefixes[j]+htmlext, "windows-1252");
                        writer.println(cnt);
                        writer.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
                    catch (Exception e) {
	                e.printStackTrace();
                    }        
      }                    
                    
                    
   }   
   
//   
   String substituteHtmlChars(String s){
       s = s.replace("⅕", "&#8533;");
       s = s.replace("⅗", "&#8535;");
       return s;
   }
 
   String formatSentence(String s){
      String beginFormat = "<b><i>";
      String ssall[] = new String[]{};
      String ss[] = u.splitString(s, '.');
      String ret;
      for(int i = 0; ss.length>1 && i < ss.length; i++){
          if(ss[i].trim().equals(""))continue;
          String s1 = ss[i].trim();
          if(s1.startsWith(beginFormat)){
              s1 = s1.substring(0, beginFormat.length()) + s1.substring(beginFormat.length(), beginFormat.length()+1).toUpperCase() +  s1.substring(beginFormat.length()+1);
          }
          else
              s1 = s1.substring(0, 1).toUpperCase() + s1.substring(1);
          ssall = u.addString(ssall, s1+".");
      }
      ret = u.combineString(ssall," ");
      if(ret.trim().equals(""))ret = s;
      ss = u.splitString(ret, '?');
      for(int i = 0; ss.length>1 &&  i < ss.length; i++){
          if(ss[i].trim().equals(""))continue;
          String s1 = ss[i].trim();
          if(s1.startsWith(beginFormat)){
              s1 = s1.substring(0, beginFormat.length()) + s1.substring(beginFormat.length(), beginFormat.length()+1).toUpperCase() +  s1.substring(beginFormat.length()+1);
          }
          else
              s1 = s1.substring(0, 1).toUpperCase() + s1.substring(1);
          ssall = u.addString(ssall, s1+"?");
      }
      ret = u.combineString(ssall," ");
      if(ret.trim().equals(""))ret = s;
      ss = u.splitString(ret, '!');
      for(int i = 0; ss.length>1 && i < ss.length; i++){
          if(ss[i].trim().equals(""))continue;
          String s1 = ss[i].trim();
          if(s1.startsWith(beginFormat)){
              s1 = s1.substring(0, beginFormat.length()) + s1.substring(beginFormat.length(), beginFormat.length()+1).toUpperCase() +  s1.substring(beginFormat.length()+1);
          }
          else
              s1 = s1.substring(0, 1).toUpperCase() + s1.substring(1);
          ssall = u.addString(ssall, s1+"!");
      }
      ret = u.combineString(ssall," ");  
      if(ret.trim().equals(""))ret = s;
      return ret;
   }
   
   
   public void jsonToCSVRecordings(String srcParent, String outputParent){  
       String suffix_mess = u.gettext("sentrecord4", "spelltheword");
       String testSentDesk1 = "Word";
       String testSentDesk2 = "Sentence";
       String testSentDesk3 = "Instruction";
       String hyperlinkTitle = "Link";
       String hyper = "=HYPERLINK(\"DesktopMP3s\\\"&B%&\".mp3\", \"play file\")";
       for(int j = 0; j < sprefixes.length; j++){  
           boolean testSents = sprefixes[j].equals(pre_sentencetest);
           boolean wantSoundFile = sprefixes[j].equals(pre_phonicSyllable) || sprefixes[j].equals(pre_phonicSound);
	            try {
	                // use FileWriter constructor that specifies open for appending
                        File ff = new File(outputParent+strMapping+sprefixes[j]+csvext);
	                boolean newfile = !ff.exists();
	                if (newfile){
	                    ff.getParentFile().mkdir();
	                    ff.createNewFile();
	                }
                        CsvWriter csvOutput = new CsvWriter(new OutputStreamWriter(new FileOutputStream(ff), "windows-1252"), ',');	
	                // if the file didn't already exist then we need to write out the header line
	                if (newfile)
	                {
                            if(!testSents)
                                csvOutput.write(CsvStruct.type_desktopname);
                            else{
                                csvOutput.write(testSentDesk1);
                                csvOutput.write(testSentDesk2);
                                csvOutput.write(testSentDesk3);
                            }
	                    csvOutput.write(CsvStruct.type_filename);
                            if(wantSoundFile){
                                csvOutput.write(hyperlinkTitle);
                            }
	                    csvOutput.endRecord();
	                }                        
                        JSONParser parser = new JSONParser();
                        File file;
                        if(!((file=new File(srcParent+strMapping+sprefixes[j]+jsonext)).exists()))continue;
                        JSONObject json = (JSONObject)parser.parse(new FileReader(file.getAbsolutePath()));
                        JSONArray results = (JSONArray) json.get(elements);                        
                        for(int i = 0; i < results.size(); i++) {
                            JSONObject p = (JSONObject) results.get(i);
                            String desktop_word = (String)p.get(CsvStruct.type_desktopname);
                            if(!testSents)
                                csvOutput.write(desktop_word);
                            else{
                
                                int left=desktop_word.indexOf('[')+1;
                                int right= desktop_word.indexOf(']');
                                String w = desktop_word.substring(left, right);
                                String formattedWord = "<b><i>"+w+"</b></i>";
                                
                                
                                csvOutput.write("<html>"+w+"</html>");
                       //         csvOutput.write("hello");
                                String sent;
                                String sentword;
                                if(desktop_word.startsWith("[")){
                                    sentword = w.substring(0, 1).toUpperCase() + w.substring(1);
                                }
                                else{
                                    sentword = w;
                                }
                                sent = desktop_word.substring(0, left);
                                sent += "<b><i>"+sentword+"</b></i>";
                                sent += desktop_word.substring(right+1);        
                                sent = sent.trim()+sent.substring(0, 1).toUpperCase() + sent.substring(1);
                                csvOutput.write("<html>"+sent+"</html>");
                        //        csvOutput.write("there");
                                csvOutput.write("<html>"+suffix_mess.replaceAll("%", formattedWord)+"</html>");
                          //       csvOutput.write("<html>matey</html>");
                                
                            }                            
	                    csvOutput.write((String)p.get(CsvStruct.type_filename));
                            if(wantSoundFile){
                                csvOutput.write(hyper.replaceAll("%", String.valueOf(i+2)));
                            }                            
	                    csvOutput.endRecord();                            
                        }                        
	                csvOutput.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
                    catch (Exception e) {
	                e.printStackTrace();
                    }        
      }                    
                    
                    
   }
   
   
   
   
    public void writeOutJsonImage(String filePath, String db[], boolean doTextFiles, boolean doCSVOut){


      String csvPath = "G:\\DONESOUNDS\\MappingTableImages";
       
       
        JSONParser parser = new JSONParser();
        try {
            JSONObject json = (JSONObject)parser.parse(new FileReader(filePath));
            String res[] = new String[]{};
            ArrayList alCsv = new ArrayList();
            JSONArray results = (JSONArray) json.get(elements);
                for(int i = 0; i < results.size(); i++) {
                    JSONObject p = (JSONObject) results.get(i);
                    String sdb = (String)p.get(database);
                    if(u.findString(db, sdb)<0){
                        continue;
                    }
                    String s;
                    if((s=(String)p.get(onlineName))==null)
                        continue;
                    res = u.addString(res, s);
                    if(doCSVOut){
                        boolean isanimated = ((String)p.get(animated)).equals("1");
                        alCsv.add(new CsvStruct((String)p.get(word), pre_wordshark_images.toLowerCase()+"/"+s+(isanimated?gifext:pngext), 
                            s+(isanimated?gifext:pngext), (String)p.get(desktopName), sdb, ((String)p.get(vocab)).equals("1")?true:false, isanimated));
                    }
                }
                if(doTextFiles) writeFile("C:\\Users\\paulr\\Documents\\Text\\z"+"images"+txtext, res);
                
                
                if(doCSVOut){
                    File ff = new File(csvPath+pre_wordshark_images+csvext);
	            try {
	                // use FileWriter constructor that specifies open for appending
	                boolean newfile = !ff.exists();
	                if (newfile){
	                    ff.getParentFile().mkdir();
	                    ff.createNewFile();
	                }
	
	                CsvWriter csvOutput = new CsvWriter(new OutputStreamWriter(new FileOutputStream(ff), "windows-1252"), ',');		
	                // if the file didn't already exist then we need to write out the header line
	                if (newfile)
	                {
	                    csvOutput.write(CsvStruct.type_word);
	                    csvOutput.write(CsvStruct.type_s3key);
	                    csvOutput.write(CsvStruct.type_filename);
	                    csvOutput.write(CsvStruct.type_desktopname);
	                    csvOutput.write(CsvStruct.type_desktopdb);
                            csvOutput.write(CsvStruct.type_isvocab);
                            csvOutput.write(CsvStruct.type_isanimated);
	                    csvOutput.endRecord();
	                }	
	                for(int i = 0; i < alCsv.size(); i++){
                            CsvStruct csvs = (CsvStruct)alCsv.get(i);
	                    csvOutput.write(csvs.word);
	                    csvOutput.write(csvs.s3key);
	                    csvOutput.write(csvs.filename);
	                    csvOutput.write(csvs.desktopname);
	                    csvOutput.write(csvs.desktopdb);
	                    csvOutput.write(csvs.isvocab?"true":"false");
                            csvOutput.write(csvs.isanimated?"true":"false");
	                    csvOutput.endRecord();
	                }            

	                csvOutput.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }                        
                    }                 
                
                
        } catch (Exception e) {
            e.printStackTrace();
        }
        int e;
        e = 9;
    }
    
    
    public void copyFilesAddPrefix(){
        String strSrc = "G:\\GretchenPhotographPoolOUT\\";
        String strDest = "G:\\GretchenPhotographPoolOUTNAMED\\";
        
        File ff[] = new File(strSrc).listFiles();
        for(int i = 0; i < ff.length; i++){
          try{
               FileUtils.copyFile(ff[i], new File(strDest +pre_photographs +"_" +ff[i].getName())); 
         }
         catch(Exception ee){
               int h;
               h = 0;
         }              
        }
         
    }
    
    
    
    public void gretchenNotUsed(){
        String strSrc = "C:\\Users\\paulr\\Dropbox\\PhotographicResourcesForOnline\\";
        String strDest = sharkStartFrame.sharedPathplus+outputFolder+"\\";
  //      String pref = "IPHOTO_";
        String pref = "";
        JSONParser parser = new JSONParser();
        JSONObject json = null;
        JSONArray results = null;
        try{
            json = (JSONObject)parser.parse(new FileReader(fileImages));
            results = (JSONArray) json.get(elements);
        }
        catch(Exception e){
            int h; 
            h = 0;
        }
        File ff[] = new File(strSrc).listFiles();
        String notuseds[] = new String[]{};
        for(int i = 0; i < ff.length; i++){
            String name = ff[i].getName(); 
            if(name.endsWith(".ini"))continue;  //Desktop.ini
            name = name.substring(pref.length(), name.lastIndexOf('.')).toLowerCase();
            boolean found = false;
            for(int k = 0; k < results.size(); k++) {
                JSONObject p = (JSONObject) results.get(k);
                String thisStr = charToAscii(((String)p.get(desktopName)).toLowerCase());
                if(name.equalsIgnoreCase(thisStr)){
                    found = true;
                    break;
                }
            }
            if(!found)notuseds = u.addString(notuseds, name);
        }
        
        writeFile(strDest+"notuseds"+txtext, notuseds);
    }    
    
   
    public void gretchenNotDone(){
        String strSrc = "C:\\Users\\paulr\\Dropbox\\PhotographicResourcesForOnline\\";
        String strDest = "G:\\";
//        String pref = "IPHOTO_";
        String pref = "";
        JSONParser parser = new JSONParser();
        JSONObject json = null;
        JSONArray results = null;
        try{
            json = (JSONObject)parser.parse(new FileReader(fileImages));
            results = (JSONArray) json.get(elements);
        }
        catch(Exception e){
            int h; 
            h = 0;
        }
        File ff[] = new File(strSrc).listFiles();
        String notdone[] = new String[]{};
        String notdonedbs[] = new String[]{};
        for(int k = 0; k < results.size(); k++) {
            JSONObject p = (JSONObject) results.get(k);
            String thisStr = ((String)p.get(desktopName)).toLowerCase();
            if(thisStr.indexOf("_")>=0)continue;
            boolean found = false;
            for(int i = 0; i < ff.length; i++){
                String name = ff[i].getName();   
                name = name.substring(pref.length(), name.lastIndexOf('.')).toLowerCase();
                if(name.equalsIgnoreCase(thisStr)){
                    found = true;
                    break;
                }
            }    
            if(!found){
                notdone = u.addString(notdone, thisStr);
                notdonedbs = u.addString(notdonedbs, ((String)p.get(database)).toLowerCase());
            }
        }        
        writeFile(strDest+"notdone"+txtext, notdone);   
        writeFile(strDest+"notdonedbs"+txtext, notdonedbs); 
    }     
   
   
   private boolean getFromJSONArray(JSONArray results, String key, String dbase, String target, String db[], int avoid, String desktpName){
        for(int pf = 0; pf < sprefixes.length; pf++){
            innerloop: for(int i = 0; i < results.size(); i++) {
                if(i == avoid) continue;
                JSONObject p = (JSONObject) results.get(i);
                if(key !=null && (String)p.get(key)==null){
                    continue innerloop;
                }
                String sdb = (String)p.get(database);
                if(!sdb.equals("") && u.findString(db, sdb)<0){
                    continue innerloop;
                }
                String s;
                if((s=(String)p.get(onlineName))==null || !s.startsWith(sprefixes[pf]+"_")){
                    continue innerloop;
                }
                if(!((String)p.get(desktopName)).toLowerCase().equals(desktpName.toLowerCase())){
                    continue innerloop;
                }
                if(s.equals(target)){
                    return true;
                }
            }
        }
        return false;
   }
   
   
   
   public void renameSkipping(){
      String src = "G:\\Sounds\\publicsent2\\recordingsbase\\";
      String dest = "G:\\Sounds\\publicsent2\\recordings\\";
      String ext = ".mp3";
 
      int counter = 1;
      int counter2 = 1;
      File fl = null;
      while ((fl=new File(src+"S-"+String.valueOf(counter2)+ext)).exists()){
         try{
               FileUtils.copyFile(fl, new File(dest+"S-"+String.valueOf(counter)+ext)); 
         }
         catch(Exception ee){
               int h;
               h = 0;
         }         
          if(counter == 4305)counter++;
          counter++;
        counter2++;
      }   
   }
   
   /*
   public void moveImageFiles(){
       String list[] = u.readFile("C:\\jshark-shared2\\publictopicsPrint\\notuseds.txt");
       String fromDir = "C:\\Users\\paulr\\Dropbox\\PhotographicResourcesForOnline\\";
       
       
       for(int i = 0; i < list.length; i++){
         try{

             
              FileUtils.moveFileToDirectory(new File(fromDir+list[i]+jpgext), new File("C:\\Users\\paulr\\Dropbox\\PhotographicResourcesForOnlineUmatched"), false);
         }
         catch(Exception ee){
               int h;
               h = 0;
         }           
       }
   }
   */
   
   
   public void findDuplicateImageFiles(){
      String src = "G:\\GretchenPhotograph\\";
      String dest = "G:\\";
      File file = new File(src);
      File ff[] = file.listFiles();
      String duplist[] = new String[]{};
      mainloop:for(int i = 0; i < ff.length; i++){
        innerloop:for(int k = 0; k < ff.length; k++){
            if(i == k) continue innerloop;
            try{
                if(FileUtils.contentEquals(ff[i], ff[k])){
                    if(u.findString(duplist, ff[i].getName())<0){
                        duplist = u.addStringSort(duplist, ff[i].getName());
                        continue mainloop;
                    }
                }
            }
            catch(Exception e){
                int h;
                 h= 0;
            }
        }          
      } 
      writeFile(dest+"duplicates"+txtext, duplist);   
   }   
   
   
   
   /*
Used when recordings have been made with Audicity that are in sperate files (therefore separate numbering).
   This combines them into one folder and resets the numbering
   */
   
    public void amalgamateFolders(){
        String folders[] = new String[]{
            "G:\\Sounds\\publicsent2\\1\\",
            "G:\\Sounds\\publicsent2\\2\\",
            "G:\\Sounds\\publicsent2\\3\\"
        };
        String outputfolder = "G:\\Sounds\\publicsent2\\result\\";
        int counter = 1;
        String template = "S-%.mp3";
        
        
        for(int i = 0; i < folders.length; i++){
            
            File f[] = new File(folders[i]).listFiles();
            int counter2 = 0;
            File fl = null;
            while ((fl=new File(folders[i]+template.replace("%", String.valueOf(counter2)))).exists() || counter2<2){
                if(fl.exists()){
                    try{
                        FileUtils.copyFile(fl, new File(outputfolder+template.replace("%", String.valueOf(counter++)))); 
                    }
                    catch(Exception ee){
                                int h;
                                h = 0;
                    }                    
                }
                counter2++;
            }           
        }
    }   
   
   
    

    
    /*
    Retrives online name from json in exchange for db name and desktop name
   
    public String findJsonImage(String filePath, String datab, String desktop){
        JSONParser parser = new JSONParser();
        try {
            JSONObject json = (JSONObject)parser.parse(new FileReader(filePath));
            JSONArray results = (JSONArray) json.get(elements);
            return findJsonImage(results, datab, desktop);     
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
     */
    public String findJsonImage(JSONArray results, String datab, String desktop, boolean wantWordshark){
        desktop = desktop.toLowerCase();
        try {
            for(int i = 0; i < results.size(); i++) {
                JSONObject p = (JSONObject) results.get(i);
                String currUsed = (String)p.get(currentlyUsed);
                if(currUsed!=null  && currUsed.equals("false"))continue;
                boolean found = false;
                String db = (String)p.get(database); // not all photos have pulbicimage equilvalents and therefore won't have an associated db
                if(db!=null && datab!=null){
                    String ss[] = u.splitString(db);
                    for(int j = 0; j < ss.length; j++){
                            if((ss[j]).equals(datab)){
                                 found = true;
                                 break;   
                            }  
                    }                
                }
                else found = true;
                if(found && ((String)p.get(desktopName)).equals(desktop.toLowerCase()) && ((String)p.get(isWordshark)).equals(wantWordshark?"1":"0"))
                    return (String)p.get(onlineName);
            }            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }    
    
      public  String[] getToDoList(String path1, boolean isPhoto){
        String ret[] = new String[]{};
        JSONParser parser = new JSONParser();
        try {
            if(!isPhoto){

                    JSONArray jsonarr = (JSONArray)parser.parse(new FileReader(path1));
                    JSONObject json = (JSONObject)parser.parse(new FileReader(ToolsOnlineResources.filePublicImages));
                    JSONArray jsonarrimages =  (JSONArray) json.get(ToolsOnlineResources.elements);
                    for(int i = 0; i < jsonarr.size(); i++){
                        String name = ((String)jsonarr.get(i));
                        int k = findJsonIndex(jsonarrimages, 
                                new String[]{ToolsOnlineResources.onlineName, isWordshark, currentlyUsed}, 
                                new String[]{name, "1", "true"});
                        if(k < 0){
                            System.out.println("NOT IN THE IMAGES JSON:  "+name);
                            continue;
                        }
                        JSONObject p = (JSONObject) jsonarrimages.get(k);
                        ret = u.addString(ret, (String)p.get(ToolsOnlineResources.desktopName));
                    }

            }
            else{
                JSONArray jsonarr = (JSONArray)parser.parse(new FileReader(path1));
                    for(int i = 0; i < jsonarr.size(); i++){
                        String name = ((String)jsonarr.get(i));
                        ret = u.addString(ret, name);
                    }
            }
        
        } catch (Exception e) {
            e.printStackTrace();
        }       
        return ret;     
      }    
    
    
    public int findJsonIndex(JSONArray results, String key[], String value[]){
        try {
            for(int i = 0; i < results.size(); i++) {
                int got = 0;
                JSONObject p = (JSONObject) results.get(i);
                String currUsed = (String)p.get(currentlyUsed);
//                if(currUsed!=null  && currUsed.equals("false"))continue;
                for(int k = 0; k < key.length; k++) {
                    String db = (String)p.get(key[k]); 
                    
                    if(db!=null){
                        if(db.equals(value[k])){
                            got++;
                        }                
                    }                    
                }
                if(got==key.length)
                    return i;
            }            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }    
    
    
    /*
    Reeza wanted csv of image online name, width and height 
    */
    public String jsonImageToCsv(){
        String imageFolders[] = new String[]{"C:\\Users\\paulr\\Dropbox\\PhotographicResourcesForOnline\\", "C:\\Users\\paulr\\Dropbox\\PhotographicResourcesForOnlineUmatched\\"};
        String output = sharkStartFrame.publicPathplus+"csv\\photosdim.csv";
        String names[] = new String[]{};
        String widths[] = new String[]{};
        String heights[] = new String[]{};
        JSONArray results = null;
        JSONParser parser = new JSONParser();
         try{
            JSONObject json = (JSONObject)parser.parse(new FileReader(sharkStartFrame.publicPathplus+"json\\images.json"));
            results = (JSONArray) json.get(elements);
        }
        catch(Exception e){
            int h; 
            h = 0;
        }
        
        try {
            loop1: for(int i = 0; i < results.size(); i++) {
                JSONObject p = (JSONObject) results.get(i);
                if(!((String)p.get(s3key)).startsWith(pre_photographs.toLowerCase())) continue;
                String ss = (String)p.get(desktopName);
                String ssonline = (String)p.get(onlineName);
                for(int j = 0; j < imageFolders.length; j++){
                    File f = new File(imageFolders[j]+ss+jpgext);
                    if(f.exists()){
                        try{
                            BufferedImage bimg = ImageIO.read(f);
                            int width          = bimg.getWidth();
                            int height         = bimg.getHeight();
                            names =u.addString(names, ssonline);
                            widths =u.addString(widths, String.valueOf(width));
                            heights =u.addString(heights, String.valueOf(height));
                            continue loop1;
                        }
                        catch(Exception ee){
                            int g;
                             g = 0;
                        }
                        break;                           
                    }
                }                
            }            
        } catch (Exception e) {
            e.printStackTrace();
        }
                        try{
                        File ff = new File(output);
	                boolean newfile = !ff.exists();
	                if (newfile){
	                    ff.getParentFile().mkdir();
	                    ff.createNewFile();
	                }
                        CsvWriter csvOutput = new CsvWriter(new OutputStreamWriter(new FileOutputStream(ff), "windows-1252"), ',');	
	                // if the file didn't already exist then we need to write out the header line
                        csvOutput.write("Name");
                        csvOutput.write("Width");
	                csvOutput.write("Height");
	                csvOutput.endRecord();                       
                        for(int i = 0; i < names.length; i++) {
	                    csvOutput.write(names[i]);
                            csvOutput.write(widths[i]);
                            csvOutput.write(heights[i]);
	                    csvOutput.endRecord();                            
                        }                        
	                csvOutput.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
                    catch (Exception e) {
	                e.printStackTrace();
                    }          
        
        
        return null;
    }     

    
    /*
    Retrives online name from json in exchange for db name and desktop name
    */  
    /*
    public String findJsonRecording(String filePath, String datab, String desktop){
        JSONParser parser = new JSONParser();
        try {
            JSONObject json = (JSONObject)parser.parse(new FileReader(filePath));
            JSONArray jsonRecResults = (JSONArray) json.get(elements);
            return findJsonRecording(jsonRecResults, datab, desktop);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;          
    }
    */
    public String findJsonRecording(JSONArray jsonRecResults, String datab, String desktop){
        desktop = desktop.toLowerCase();
        try {
            for(int i = 0; i < jsonRecResults.size(); i++) {
                JSONObject p = (JSONObject) jsonRecResults.get(i);
                String currUsed = (String)p.get(currentlyUsed);
                if(currUsed!=null  && currUsed.equals("false"))continue;
                boolean found = false;
                String ss[] = u.splitString((String)p.get(database));
                for(int j = 0; j < ss.length; j++){
                        if((ss[j]).equals(datab)){
                             found = true;
                             break;   
                        }  
                }                
                if(((String)p.get(desktopName)).equals(desktop) && found)
                    return (String)p.get(onlineName);
            }            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    
    public boolean findJsonRecording(JSONArray jsonRecResults, String online){
        try {
            for(int i = 0; i < jsonRecResults.size(); i++) {
                JSONObject p = (JSONObject) jsonRecResults.get(i);
                String currUsed = (String)p.get(currentlyUsed);
                if(currUsed!=null  && currUsed.equals("false"))continue;
                String ss = (String)p.get(onlineName);     
                if(online.equals(ss))
                    return true;
            }            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    
    public JSONObject getJsonRecording(JSONArray jsonRecResults, String online){
        try {
            for(int i = 0; i < jsonRecResults.size(); i++) {
                JSONObject p = (JSONObject) jsonRecResults.get(i);
                String currUsed = (String)p.get(currentlyUsed);
                if(currUsed!=null  && currUsed.equals("false"))continue;
                String ss = (String)p.get(onlineName);     
                if(online.equals(ss))
                    return p;
            }            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
/*
    public String findJsonRecordingAnyDbFromOnlineName(JSONArray jsonRecResults, String online){
        try {
            for(int i = 0; i < jsonRecResults.size(); i++) {
                JSONObject p = (JSONObject) jsonRecResults.get(i);                
                if(((String)p.get(onlineName)).equals(online))
                    return (String)p.get(desktopName) + "             (" + (String)p.get(database)+")";
            }            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }    
    */
    /*
    returns supplied desktop name if this recording is required according to publictopics
    */
    public String findJsonValidRecording(JSONArray results, String desktop, boolean sentences){
        if(desktop.indexOf("ill a ship sail on the road")>=0){
            int g; 
            g =0;
        }
        
        try {
            for(int i = 0; i < results.size(); i++) {
                JSONObject p = (JSONObject) results.get(i);
                                
                String currUsed = (String)p.get(currentlyUsed);
                if(currUsed!=null  && currUsed.equals("false"))continue;
                String simp = (String)p.get(simpleSent);
                String simpfull = (String)p.get(simpleSentFull);
                
                String stand = (String)p.get(standSent);
                
                if(simp!=null)simp = simp.replace("|", " ");
                if(simpfull!=null)simpfull = simpfull.replace("|", " ");
                if(stand!=null)stand = stand.replace("|", " ");
                
                
                
                int h;
        if(desktop.indexOf("tess to the shop")>=0 && stand!= null && stand.indexOf("to the shop")>=0){
            int g; 
            g =0;
        }
                if(simp!= null && (h = simp.indexOf('{'))>=0){
                    simp = simp.substring(0, h);
                }
                if(simpfull!= null && (h = simpfull.indexOf('{'))>=0){
                    simpfull = simpfull.substring(0, h);
                }
                if(stand!= null && (h = stand.indexOf('{'))>=0){
                    stand = stand.substring(0, h);
                }                
                if(sentences && (desktop.equalsIgnoreCase(simp) ||  desktop.equalsIgnoreCase(stand) ||  desktop.equalsIgnoreCase(simpfull)  )){
                    return desktop;
                }
                if(!sentences && (desktop.equalsIgnoreCase((String)p.get(headings))))
                    return desktop;
            }            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /*
    Should publictopics be checked as to whether this recording is required?
    */
    private boolean checkNeeded(String s){
        return s.startsWith(pre_sentence+"_") || s.startsWith(pre_sentencesimple+"_") || s.startsWith(pre_topicRecording+"_") || s.startsWith(pre_sentencesimplefull+"_");
    }
            
    private boolean isSentence(String s){
        return s.startsWith(pre_sentence+"_") || s.startsWith(pre_sentencesimple+"_") || s.startsWith(pre_sentencesimplefull+"_");
    }
    
    private void writeRecordingsJson(String filePath, JSONArray results, JSONArray prevRecResults, String s1[], String s2[], String sFullSent[], String s3[], String sVocab[], String sPlain[])
    {
        JSONArray arrayElementOneArray = (JSONArray)prevRecResults.clone();
        for(int i = 0; i < s3.length; i++){
            
            
            if(s2[i].equals("publicsent2")){
                int ff;
                ff=0;
            }
            if(s3[i].indexOf("findJsonValidRecording")>=0){
                int g;
                g = 9;
            }
            if(s3[i].indexOf("SENT_display_3.mp3")>=0){
                int g;
                g = 9;
            }
            if(checkNeeded(s3[i])){
                if(s3[i].startsWith(pre_sentencesimplefull)){
                    int g; 
                    g = 0;
                }
                if(findJsonValidRecording(results, s1[i], isSentence(s3[i])?true:false) !=null){
                    JSONObject obSub = new JSONObject();
                    obSub.put(desktopName, s1[i]);
                    obSub.put(database, s2[i]);
                    obSub.put(onlineName, s3[i]);
                    if(sFullSent!=null) obSub.put(fullSentence, sFullSent[i]); 
            //        obSub.put(fullSentence, sFullSent[i]); 
                    obSub.put(vocab, sVocab[i]);
                    obSub.put(word, sPlain[i]);
                    obSub.put(s3key, s3[i].substring(0, s3[i].indexOf("_")).toLowerCase()+"/"+s3[i]);
                    obSub.put(currentlyUsed, "true");
                    arrayElementOneArray.add(obSub);
                }
                else{
                    int g;
                    g = 0;
                }                
            }
            else{
                    JSONObject obSub = new JSONObject();
                    obSub.put(desktopName, s1[i]);
                    obSub.put(database, s2[i]);
                    obSub.put(onlineName, s3[i]);
                    if(sFullSent!=null) obSub.put(fullSentence, sFullSent[i]);
      //              obSub.put(fullSentence, sFullSent[i]);
                    obSub.put(vocab, sVocab[i]);
                    obSub.put(word, sPlain[i]);
                    obSub.put(s3key, s3[i].substring(0, s3[i].indexOf("_")).toLowerCase()+"/"+s3[i]);
                    obSub.put(currentlyUsed, "true");
                    arrayElementOneArray.add(obSub);               
            }            
        }
        JSONObject objectmain = new JSONObject();
        
        objectmain.put(elements, arrayElementOneArray);          
        try (FileWriter file = new FileWriter(filePath)) {

            file.write(objectmain.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }    
    
    private void writeImagesJson(String filePath, String s1[], String s2[], String s3[], String svocab[], String swordshark[], String sword[], String anim[])
    {
        JSONArray arrayElementOneArray = new JSONArray();
        for(int i = 0; i < s1.length; i++){
            JSONObject obSub = new JSONObject();
            if(s1!=null)obSub.put(desktopName, s1[i].toLowerCase());
            if(s2!=null)obSub.put(database, s2[i]);
            if(s3!=null)obSub.put(onlineName, s3[i]);
            if(svocab!=null)obSub.put(vocab, svocab[i]);
            if(swordshark!=null)obSub.put(isWordshark, swordshark[i]);
            if(sword!=null)obSub.put(word, sword[i]);
    //        obSub.put(s3key, s3[i].substring(0, s3[i].indexOf("_")).toLowerCase()+"//"+s3[i]);     
            if(s3!=null)obSub.put(s3key, s3[i].substring(0, s3[i].indexOf("_")).toLowerCase()+"/"+s3[i]);     
            if(anim!=null)obSub.put(animated, anim[i]);
            arrayElementOneArray.add(obSub);
        }
        JSONObject objectmain = new JSONObject();
        objectmain.put(elements, arrayElementOneArray);          
        try (FileWriter file = new FileWriter(filePath)) {
            file.write(objectmain.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    
    
    
    
    
    
    
    /*
    public void writeTopicsJson2(String filePath, String key[], String head[], String headtop[])
    {        
        JSONArray arrayElementOneArray = new JSONArray();
        for(int i = 0; i < head.length; i++){
            JSONObject obSub = new JSONObject();
            obSub.put(key[i], head[i]);
            obSub.put(topicName, headtop[i]);
            arrayElementOneArray.add(obSub);
        }
        JSONObject objectmain = new JSONObject();
        objectmain.put(elements, arrayElementOneArray);          
        try (FileWriter file = new FileWriter(filePath)) {
            file.write(objectmain.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    } 
    */

    public void writeTopicsJson2(String filePath, String head[], String headtop[], String simpsent[], String simpsentfull[],String simpsenttop[], String sent[], String senttop[],
             String captions[], String captionstop[])
    {        
        JSONArray arrayElementOneArray = new JSONArray();
        for(int i = 0; i < head.length; i++){
            JSONObject obSub = new JSONObject();
            obSub.put(headings, head[i]);
            obSub.put(topicName, headtop[i]);
            arrayElementOneArray.add(obSub);
        }
        for(int i = 0; i < simpsent.length; i++){
            JSONObject obSub = new JSONObject();
            obSub.put(simpleSent, simpsent[i]);
            obSub.put(simpleSentFull, simpsentfull[i]);
            obSub.put(topicName, simpsenttop[i]);
            arrayElementOneArray.add(obSub);
        }
        for(int i = 0; i < sent.length; i++){
            if(sent[i].indexOf("is a sign (for example")>= 0){
                int h;
                 h =0;
            }
            JSONObject obSub = new JSONObject();
            obSub.put(standSent, sent[i]);
            obSub.put(topicName, senttop[i]);
            arrayElementOneArray.add(obSub);
        }
        for(int i = 0; i < captions.length; i++){
            JSONObject obSub = new JSONObject();
            obSub.put(caption, captions[i]);
            obSub.put(topicName, captionstop[i]);
            arrayElementOneArray.add(obSub);
        }
        JSONObject objectmain = new JSONObject();
        objectmain.put(elements, arrayElementOneArray);          
        try (FileWriter file = new FileWriter(filePath)) {
            file.write(objectmain.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }       

    /*
    public void writeTopicsJson(String filePath, String s0[], String s1[], String s2[], String s3[])
    {        
        JSONArray arrayElementOneArray = new JSONArray();
        for(int i = 0; i < s1.length; i++){
            JSONObject obSub = new JSONObject();
            obSub.put(topicName, s0[i]);
            if(s1[i]!=null)obSub.put(simpleSent, s1[i]);
            if(s2[i]!=null)obSub.put(standSent, s2[i]);
            if(s3[i]!=null)obSub.put(headings, s3[i]);
            arrayElementOneArray.add(obSub);
        }
        JSONObject objectmain = new JSONObject();
        objectmain.put(elements, arrayElementOneArray);          
        try (FileWriter file = new FileWriter(filePath)) {
            file.write(objectmain.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }    
    */
    /*
    public void getSoundFilesJson(){
        String dbs[] = new String[]{ "publicsay1","publicsay3", "publicsent1", "publicsent2", "publicsent3"};
        String basePath = "G:\\FullSounds\\";

//        for(int i = 0; i < dbs.length; i++){
//            String res[] = new String[]{};
//            String ss[] = db.list(basePath+dbs[i], db.WAV);
//            for(int k = 0; k < ss.length; k++){
//                if(ss[k].trim().equals("")) continue;
//                res = u.addString(res, ss[k]);
//            } 
 //           JSONArray arrayElementOneArray = new JSONArray();
 //           for(int k = 0; k < res.length; k++){
 //               JSONObject obSub = new JSONObject();
 //               res[k] = res[k].replace((char)8216, '\'');
 //               res[k] = res[k].replace((char)8217, '\'');
  //              obSub.put(desktopName, res[k]);
  //              arrayElementOneArray.add(obSub);
  //          }
  //          JSONObject objectmain = new JSONObject();
  //          objectmain.put(elements, arrayElementOneArray);          
  //          try (FileWriter file = new FileWriter(basePath+dbs[i]+".json")) {
  //              file.write(objectmain.toJSONString());
  //              file.flush();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }             
//            writeFile(basePath+dbs[i]+".txt", res);   
//        }
   
        

        JSONArray arrayElementOneArray = new JSONArray();
        for(int i = 0; i < sgameecordings.length; i++){
            if(sgameecordings[i][1].trim().equals("")) continue;
            String str = sgameecordings[i][1];
            JSONObject obSub = new JSONObject();
            str = str.replace((char)8216, '\'');
            str = str.replace((char)8217, '\'');
            obSub.put(desktopName, str);
            arrayElementOneArray.add(obSub);
        }
        JSONObject objectmain = new JSONObject();
        objectmain.put(elements, arrayElementOneArray);          
        try (FileWriter file = new FileWriter(basePath+gameRecordings+jsonext)) {
            file.write(objectmain.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }             
    }
    */
    
    
    public void removeExtraExtensions(){
        String unwantedExt = ".mp3";
        File ff[] = new File("D:\\Dropbox\\New folder (10)\\Claire2019-04-11\\wetransfer-16b1bf\\cleaned").listFiles();
        String dest = "D:\\Dropbox\\New folder (10)\\Claire2019-04-11\\res\\";
        for(int i = 0; i < ff.length; i++){
            String s = ff[i].getName();
            if(u.splitString(s, ".").length<=2)continue;
            if(!s.endsWith(unwantedExt))continue;
            try{
                FileUtils.copyFile(ff[i], new File(dest+s.substring(0, s.length()-unwantedExt.length()))); 
            }
            catch(Exception ee){
                int h;
                h = 0;
            }               
        }
        
    }
    
    
    public void nameDesktopRecordingsFromJson(){
        String outputPath = "E:\\DESKTOPRECORDINGS\\Output\\";
        
        String ssPublicSay1[] = u.readFile("E:\\DESKTOPRECORDINGS\\Lists\\publicsay1.txt");
        String ssPublicSay3[] = u.readFile("E:\\DESKTOPRECORDINGS\\Lists\\publicsay3.txt");
        String ssPublicSent1[] = u.readFile("E:\\DESKTOPRECORDINGS\\Lists\\publicsent1.txt");
        String ssPublicSent2[] = u.readFile("E:\\DESKTOPRECORDINGS\\Lists\\publicsent2.txt");
        String ssPublicSent3[] = u.readFile("E:\\DESKTOPRECORDINGS\\Lists\\publicsent3.txt");
        String lists[][] = new String[][]{ssPublicSay1,ssPublicSay3,ssPublicSent1,ssPublicSent2,ssPublicSent3};
        File files[][] = new File[][]{
            new File("E:\\DESKTOPRECORDINGS\\Files\\publicsay1\\").listFiles(),
            new File("E:\\DESKTOPRECORDINGS\\Files\\publicsay3\\").listFiles(),
            new File("E:\\DESKTOPRECORDINGS\\Files\\publicsent1\\").listFiles(),
            new File("E:\\DESKTOPRECORDINGS\\Files\\publicsent2\\").listFiles(),
            new File("E:\\DESKTOPRECORDINGS\\Files\\publicsent3\\").listFiles()
        };
        
        JSONParser parser = new JSONParser();
        try {
            JSONObject json2 = (JSONObject)parser.parse(new FileReader(readJsonRecording));       
            JSONArray recResults = (JSONArray) json2.get(elements); 
            for(int i = 0; i <recResults.size(); i++) {
                JSONObject p = (JSONObject) recResults.get(i);
                String deskName = (String)p.get(desktopName);
                String db = (String)p.get(database);
                File ff[];
                String list[];
                if(db.equals("publicsay1")){
                    ff = files[0];
                    list = lists[0];
                }
                else if(db.equals("publicsay3")){
                    ff = files[1];
                    list = lists[1];                    
                }               
                else if(db.equals("publicsent1")){
                    ff = files[2];
                    list = lists[2];                    
                }
                else if(db.equals("publicsent2")){
                    ff = files[3];
                    list = lists[3];                    
                }
                else if(db.equals("publicsent3")){
                    ff = files[4];
                    list = lists[4];                    
                }
                else continue;
                
                int k = u.findString(list, deskName);
                if(k<0)continue;
                
                String onName = (String)p.get(onlineName);
                
                    try{
                        FileUtils.copyFile(ff[k], new File(outputPath+onName)); 
                    }
                    catch(Exception ee){
                        int h;
                        h = 0;
                    }                
            }       

            
            
        }        
        catch (Exception e) {
            e.printStackTrace();
        }        
        
        
        
    }
    
    /*
    public void removeSentencesFromPrevRecordingsJson(){
        JSONParser parser = new JSONParser();
        try {
            JSONObject json2 = (JSONObject)parser.parse(new FileReader(readPrevJsonRecording));       
            JSONArray prevRecResults = (JSONArray) json2.get(elements); 
            for(int i = prevRecResults.size()-1; i >=0 ; i--) {
                String underscore = "_";
                JSONObject p = (JSONObject) prevRecResults.get(i);
                String onlName = (String)p.get(onlineName);
                if(onlName.startsWith(pre_sentence+underscore)
                        ||onlName.startsWith(pre_sentencesimple+underscore)
                        ||onlName.startsWith(pre_sentencesimplefull+underscore)){
                    prevRecResults.remove(i);
                }
            }       
            
            JSONObject objectmain = new JSONObject();

            objectmain.put(elements, prevRecResults);          
            try (FileWriter file = new FileWriter(sharkStartFrame.publicPathplus+jsonFolder+shark.sep+"prev"+shark.sep+"recordings2"+jsonext)) {

                file.write(objectmain.toJSONString());
                file.flush();

            } catch (IOException e) {
                e.printStackTrace();
            }            
            
            
            
        }        
        catch (Exception e) {
            e.printStackTrace();
        }           
        
    }
    */
    
    public void checkRecordingsForOnlineNameDuplicates(){
        
        
        JSONParser parser = new JSONParser();
        JSONArray results = null;
        try {
 //           File f = new File(fileSounds);
  //          File f2 = f.getParentFile(); 
   //         JSONObject json = (JSONObject)parser.parse(new FileReader(f2.getAbsolutePath()+shark.sep + new File(fileTopics).getName()));
            JSONObject json = (JSONObject)parser.parse(new FileReader(readJsonRecording));
            results = (JSONArray) json.get(elements);           
            
            innerloop:for(int i = 0; i < results.size(); i++) {
                JSONObject pi = (JSONObject) results.get(i);
                String onlinenamei = (String)pi.get(onlineName);
                String databasei = (String)pi.get(database);
                for(int j = 0; j < results.size(); j++) {
                    if(i==j)continue innerloop;
                    JSONObject pj = (JSONObject) results.get(j);
                    String onlinenamej = (String)pj.get(onlineName);
                    String databasej = (String)pj.get(database);
                    if(onlinenamej.equals(onlinenamei)){
                        if(databasei.equals(databasej)){
                            System.out.println("----------PROBLEM!!!---------------");
                            System.out.println(onlinenamej+ "    "+databasei);
                            System.out.println(onlinenamej+ "    "+databasej);
                            System.out.println("-------------------------");
                        }
                    }

                }   
            //   System.out.println(pi.toJSONString());
            }   
        } 
        catch (Exception e) {
            e.printStackTrace();
        } 
    }
    
    /*
    goes through recording json - only looking at used recordings
    identifying recordings not recorded by allow voice artists
    */
    public void checkRecordings(){
        try{
            String mainPath = "D:\\S3syncRecordings\\";
            JSONParser parser = new JSONParser();
            String tags[] = new String[]{};
            String allowedArtists[] = new String[]{"Claire", "Nigel"};
            String needToReplaceTags[] = new String[]{};
            String needToReplaceTagsAuthor[] = new String[]{};
            JSONArray results = null;
            JSONObject json = (JSONObject)parser.parse(new FileReader(readJsonRecording));
            results = (JSONArray) json.get(elements); 
            for(int i = 0; i < results.size(); i++){
                JSONObject p = (JSONObject) results.get(i);
                if(((String)p.get(currentlyUsed)).equals("false"))continue;
                String onlinename = (String)p.get(onlineName);
                String onlines3key = ((String)p.get(s3key)).replace("/", "\\");
                if(onlines3key.startsWith("topic"))continue;   // temporarily avoid the topic ones
                if(onlines3key.startsWith("game"))continue;   // temporarily avoid the game ones
                if(onlines3key.startsWith("sound"))continue;   // temporarily avoid the sound ones
                
                String unwantedAuthor = null;
                if((unwantedAuthor = isMp3AllowedArtist(mainPath+onlines3key, allowedArtists)) != null){
                    needToReplaceTags = u.addString(needToReplaceTags, onlinename);
                    needToReplaceTagsAuthor = u.addString(needToReplaceTagsAuthor, unwantedAuthor);
                }
                
                /*
                File f = new File(mainPath+onlines3key);
                FileInputStream file = new FileInputStream(f); 
                int size = (int)f.length(); 
                file.skip(size - 128); 
                byte[] last128 = new byte[128]; 
                file.read(last128); 
                String id3 = new String(last128); 
                String tag = id3.substring(0, 3); 
                if (tag.equals("TAG")) { 
                    String artist = id3.substring(33, 62);
                    if(u.findString(tags, artist)<0){
                        tags = u.addString(tags, artist);
                    }
                    boolean found = false;
                    for(int j = 0; j < allowedArtists.length; j++){
                        if(artist.startsWith(allowedArtists[j])) {
                            found = true;
                            break;
                        }
                    }
                    if(!found){
                        needToReplaceTags = u.addString(needToReplaceTags, onlinename);
                    }
                } 
                else 
                   System.out.println(onlines3key + " does not contain" 
                      + " ID3 info.");  
                file.close();
                */
            }
            /*
            for(int i = 0; i < tags.length; i++){
                System.out.println(tags[i]);  
            }
            */
            System.out.println("-------------------------------------------------------"); 
            for(int i = 0; i < needToReplaceTags.length; i++){
                JSONObject p = getJsonRecording(results, needToReplaceTags[i]);
                String full = (String)p.get(fullSentence);
                if(full == null || full.equals("null")){
                    full = (String)p.get(word);
                }
           //     System.out.println( (String)p.get(desktopName)+ "   "   +  needToReplaceTags[i]); 
            //    System.out.println( full); 
            String sout = needToReplaceTagsAuthor[i] + " - " + needToReplaceTags[i];
            if(sout.endsWith(mp3ext))
                sout = sout.substring(0, sout.length()-mp3ext.length());
             System.out.println(sout); 
 //               System.out.println((String)p.get(desktopName));
            }
            
            
            
            
            
        }
        catch(Exception e){
            int h;
            h= 0;
        }
        
    }  
    
    
    private String isMp3AllowedArtist(String filePath, String[] allowed){
        String artist = "";
        try{
            Mp3File mp3file = new Mp3File(filePath);
            if (mp3file.hasId3v2Tag()) {
                ID3v2 id3v2Tag = mp3file.getId3v2Tag();
                artist = id3v2Tag.getArtist();
                    for(int j = 0; j < allowed.length; j++){
                        if(artist.startsWith(allowed[j])) {
                            return null;
                        }
                    }
             }
        }
        catch(Exception e){
            int h = 0;
            h = 0;
        }
        return artist;
    }
    
    
    public void checkRecordingsAgainstJson(){
        try{
        String ss[] = new File("D:\\Dropbox\\New folder (10)\\cleaned").list();
            JSONParser parser = new JSONParser();
            JSONArray results = null;
            JSONObject json = (JSONObject)parser.parse(new FileReader(readJsonRecording));
            results = (JSONArray) json.get(elements); 
        for(int i = 0; i < ss.length; i++){
            if(!findJsonRecording(results, ss[i]))
                System.out.println(ss[i]);
        }
        }
        catch(Exception e){
            int h;
            h= 0;
        }
    }    
    
    
    public void saveAllRecordings(){
   //     String existingRecordingPath = "E:\\S3syncRecordings\\";
        JSONParser parser = new JSONParser();
        JSONArray results = null;
        JSONArray prevRecResults = null;
        try {
 //           File f = new File(fileSounds);
  //          File f2 = f.getParentFile(); 
   //         JSONObject json = (JSONObject)parser.parse(new FileReader(f2.getAbsolutePath()+shark.sep + new File(fileTopics).getName()));
            JSONObject json = (JSONObject)parser.parse(new FileReader(fileTopics));
            results = (JSONArray) json.get(elements);           
            
            JSONObject json2 = (JSONObject)parser.parse(new FileReader(readPrevJsonRecording));
            prevRecResults = (JSONArray) json2.get(elements);               
        } 
        catch (Exception e) {
            e.printStackTrace();
        }   
        String dbs[] = new String[]{ "publicsay1","publicsay3", "publicsent1", "publicsent2", "publicsent3", "publicsent4"};
        String vocabdbs[] = new String[]{ "publicsay3", "publicsent3"};
        String ssFile[] = new String[]{};
        String ssDesktop[] = new String[]{};
        String ssOnline[] = new String[]{};
        String ssVocabulary[] = new String[]{};
        String ssDesktopNameAlready[] = new String[]{};
        String ssFullSentence[] = null;
        boolean hasFullSentence = false;
        String ssPlain[] = new String[]{};
        String ssOnlineMinusExt[] = new String[]{};
        
        String ssInPreviousRecordingJson[] = new String[]{};
        // mark all previous recordings as unused
        for(int i = 0; i < prevRecResults.size(); i++) {
            JSONObject p = (JSONObject) prevRecResults.get(i);
            String onlinename = (String)p.get(onlineName);
            int kk;
            if((kk=onlinename.lastIndexOf('.'))==onlinename.length()-3-1){
                onlinename = onlinename.substring(0, kk);
            }    
            ssOnlineMinusExt = u.addString(ssOnlineMinusExt,  onlinename);
            String deskname = (String)p.get(desktopName);
            deskname = deskname.replace("|", " ");
            ssDesktopNameAlready = u.addString(ssDesktopNameAlready, deskname);
            String dbname = (String)p.get(database);
            if(deskname.indexOf("ill a ship sail on the road")>=0){
                int h;
                h = 0;
            }
            if(deskname.indexOf("Jill has long ")>=0){
                int h;
                h = 0;
            }
            p.put(desktopName,deskname);
            p.put(currentlyUsed,"false");
            prevRecResults.set(i, p);
            ssInPreviousRecordingJson = u.addString(ssInPreviousRecordingJson, dbname+deskname);    
        }        
        for(int i = 0; i < dbs.length; i++){
            String ss[] = db.list(dbs[i], db.WAV);
            loopss:for(int k = 0; k < ss.length; k++){
                String item = ss[k].toLowerCase();
                if(item.indexOf("ill a ship sail on the road")>=0){
                    int g;
                    g = 9;
                }                
                if(item.equalsIgnoreCase("d~")){
                    int g;
                    g = 9;
                } 
               if(item.trim().equals(""))continue;
               String pss[] = getTypePrefix(results, dbs[i],item);
               item = item.replace("|", " ");
               
               String pref = pss[0]+ "_";
               String fullsentence = pss[1];
               String str = null;               
               String oristr = null;
             
               // use details of previous recording -- so that the same Desktop recording has the same name in 
               // WOL. If not, we'd tie course version to S3. S3 is fixed, there are many versions of courses.
               short kk[];
               if((kk=u.findAllStrings(ssInPreviousRecordingJson, dbs[i]+item))!=null){
                   for(int pi = 0; pi < kk.length; pi++){
                        JSONObject p = (JSONObject) prevRecResults.get(kk[pi]);
                        String deskname = (String)p.get(desktopName);
                        String onlinename = (String)p.get(onlineName);
                           if(deskname.indexOf("a crab went into a crack in the rock")>=0){
                               int h;
                               h = 0;
                           } 
                        // therefore if a change of category, e.g. CAP_ to SSENT, previous is not used.
                        // the new recordings name might have a '_2' suffix even though there isn't a previous similar online name
                        // but I think that's ok?
                        if(onlinename.startsWith(pref.toUpperCase())){
                            p.put(currentlyUsed,"true");
                            prevRecResults.set(kk[pi], p);
                            continue loopss;
                        }                        
                   }                  
               }
               // recording not in previous
               if(item.equalsIgnoreCase("d~")){
                        int y = 0; 
                         y = 0;
                    }
               oristr = str = pref+getRecordingS3Name(dbs[i], item, ssOnlineMinusExt, ssDesktopNameAlready, pref);

              
     //           // do we have a sound file for it??
    //           boolean soundFileMissing = false;
    //           soundFileMissing = !doesSoundFileExist(existingRecordingPath, oristr);                
               
               
               
//               String str = pref+getRecordingS3Name(dbs[i], itemLowerCase, ssOnlineMinusExt, pref);
               if(str == null){
                   continue;
               }
              // String strMinusExt = str;
             //  str += wavext;
               ssOnlineMinusExt = u.addString(ssOnlineMinusExt,  str);
  //             String ext = u.findString(wavTypes, pss[0])>=0?wavext:mp3ext;
               ssFile = u.addString(ssFile, dbs[i]);
               
               ssDesktop = u.addString(ssDesktop, item); 
               ssDesktopNameAlready = u.addString(ssDesktopNameAlready, item);
           ///    ssOnlineMinusExt= u.addString(ssOnlineMinusExt,  strMinusExt);
               ssOnline = u.addString(ssOnline,  str+mp3ext);
               ssVocabulary = u.addString(ssVocabulary, u.findString(vocabdbs,dbs[i])>=0?"1":"0"); 
               ssPlain = u.addString(ssPlain,  getPlainString(item, pref));
 //              ssOnlineFileOnly = u.addString(ssOnlineFileOnly,  str+mp3ext);
               if(fullsentence!=null) 
                   hasFullSentence = true;
               ssFullSentence = u.addString(ssFullSentence,  fullsentence);
               
  //             if(soundFileMissing && u.findString(dummySounds, str)<0)
  //                dummySounds = u.addString(dummySounds, str);
               
          /*     
               if(pref.equals(pre_sentencesimple+"_")){
                   String dbps3 = "publicsay3";
          //         sentence sent = new sentence(ww[i].value,ww[i].database);
 //                  sentence sent = (new sentence(item,null));
                   
  //                 String sfull = sent.stripcloze();
  //                 if(db.query(dbps3, sfull, db.WAV)>=0){
                       ssDesktop = u.addString(ssDesktop, item); 
                       ssFile = u.addString(ssFile, dbps3);
                       ssOnline = u.addString(ssOnline, pre_sentencesimplefull+"_"+str.substring(pref.length()));
                       ssVocabulary = u.addString(ssVocabulary,  "1"); 
                       ssPlain = u.addString(ssPlain,  getPlainString(item, pref));
   //                }
    //               else{
    //                   int h;
    //                   h = 0;
    //               }
               }   
               */             
           }          
    //       writeFile("C:\\Users\\paulr\\Documents\\Text\\"+dbs[i]+".txt", ssOnlineFileOnly);
        }
   //     writeFile(fileDummyRecordings, dummySounds);
        
        
        
        
        // rely on the previous entries for games. I'm not sure why some don't have db specified in the json
            for(int i = 0; i < sgameecordings.length; i++){
                String gamePrefix = pre_gameMessage+"_";
                String ss[] = sgameecordings[i];
                int kk;
                if(  (kk=u.findString(ssInPreviousRecordingJson, ss[0]+ss[1].toLowerCase()))>=0 ){
                    JSONObject p = (JSONObject) prevRecResults.get(kk);
                    p.put(currentlyUsed,"true");
                    prevRecResults.set(kk, p);
                    continue;
                }            
                else{
                    int y; 
                    y = 0;
                }
                if(db.query(ss[0], ss[1], db.WAV)<0){                
                       spokenWord sw = spokenWord.findandreturn(new String[]{ss[1]},"publicsent1");     
                       if(sw!=null){
                           String str = pre_gameMessage+"_"+getRecordingS3Name(ss[0], ss[1], ssOnlineMinusExt, ssDesktopNameAlready, gamePrefix);
                           ssOnlineMinusExt = u.addString(ssOnlineMinusExt,  str);
                            ssDesktop = u.addString(ssDesktop, ss[1]); 
                 //           ssSoundMissing = u.addboolean(ssSoundMissing, false);
                            ssDesktopNameAlready = u.addString(ssDesktopNameAlready, ss[1]);
                            ssFile = u.addString(ssFile, ""); 
                            ssOnline = u.addString(ssOnline, str+mp3ext); 
                            ssFullSentence = u.addString(ssFullSentence,  (String)null);
                            ssVocabulary = u.addString(ssVocabulary,  "0"); 
                            ssPlain = u.addString(ssPlain,  getPlainString(ss[1], pre_gameMessage));

                       }
                }
                else{
                    String str = pre_gameMessage+"_"+getRecordingS3Name(ss[0], ss[1], ssOnlineMinusExt, ssDesktopNameAlready, gamePrefix);
                    ssOnlineMinusExt = u.addString(ssOnlineMinusExt,  str);
                    ssDesktop = u.addString(ssDesktop, ss[1]);
         //           ssSoundMissing = u.addboolean(ssSoundMissing, false);
                    ssDesktopNameAlready = u.addString(ssDesktopNameAlready, ss[1]);
           //         ss[0] += mp3ext;
                    ssFile = u.addString(ssFile, ss[0]);

                    ssOnline = u.addString(ssOnline, str+mp3ext);   
                    ssFullSentence = u.addString(ssFullSentence,  (String)null);
                    ssVocabulary = u.addString(ssVocabulary,  "0"); 
                    ssPlain = u.addString(ssPlain,  getPlainString(ss[1], pre_gameMessage));
                }
             
        }
        writeRecordingsJson(fileSounds, results, prevRecResults, ssDesktop, ssFile, hasFullSentence?ssFullSentence:null, ssOnline, ssVocabulary, ssPlain);
    }
    
    boolean doesSoundFileExist(String existingPath, String fileName){
        int k = fileName.indexOf("_");
        String prefs =  fileName.substring(0, k);
        return new File(existingPath+prefs.toLowerCase()+shark.sep+prefs+fileName.substring(k)).exists();
    }
    
    void writeRecordingJson(String filePath, JSONArray jsarr){
        JSONObject objectmain = new JSONObject();
        
        objectmain.put(elements, jsarr);          
        try (FileWriter file = new FileWriter(filePath)) {

            file.write(objectmain.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    public String getSaveSharkImageID(sharkImage.saveSharkImage si){
        String id = String.valueOf(si.x1)+String.valueOf(si.x2)+String.valueOf(si.y1)+String.valueOf(si.y2)+String.valueOf(si.radius);
        sharkImage.saveImagePart[] ps = si.parts;
        int tally = 0;
        for(int i = 0; i < ps.length; i++){
            tally += ps[i].x+ps[i].y;
        }
        id += String.valueOf(ps.length);
        id += String.valueOf(tally);
        sharkImage.saveImageControl[] cs = si.controls;
        tally = 0;
        for(int i = 0; i < cs.length; i++){
            tally += cs[i].max+cs[i].min;
        }        
        id += String.valueOf(cs.length);
        id += String.valueOf(tally);         
        return id;
    }
    
    public String getImageFileID(String filePath){
        String md5 = null;
        try (InputStream is = java.nio.file.Files.newInputStream(java.nio.file.Paths.get(filePath))) {
            md5 = org.apache.commons.codec.digest.DigestUtils.md5Hex(is);
        }
        catch(Exception e){
            int h;
           h = 0;
        }
        int l;
        l = 0;
        return md5;
    }    
    
     public String getPlainString(String s, String pref){     
         String oris = s;
        if(pref.equals(pre_homophoneExplanation))return s.substring(0, s.lastIndexOf("="));
        if(pref.equals(pre_phonicSyllable) || pref.equals(pre_phonicSound) || pref.equals(pre_topicRecording))
            return removeNonAlphaNum(s);
        if(pref.equals(pre_nonsenseWord) || pref.equals(pre_nonWord))
            return s.substring(0, s.lastIndexOf("!"));
        if(pref.equals(pre_letterName))return s.substring(0, 1);
        if(pref.equals(pre_system))return removeNonAlpha(s);
        if(pref.equals(pre_sentence) || pref.equals(pre_sentencesimple)){
            sentence sent = (new sentence(s,null));       
            return sent.stripcloze();          
        }
        if(pref.equals(pre_wordshark_images) || pref.equals(pre_photographs)){
            int k;
            if((k=s.indexOf("^"))>0)s = s.substring(0, k);    
        }
        int k;
        if((k=s.indexOf("@"))>0)return s.substring(0, k);
        s = removeNonAlphaNum(s);               
    //    while(s.indexOf("  ")>=0)
    //        s.replace("  "," ");
    if(s==null){
        int g; 
        g   =0 ;
    }
        return s.replaceAll("\\s{2,}", " ").trim();
     }
     

     public String[] findDuplicates(String s[], String ss[]){
        String ret[] = new String[]{};
        for(int i = 0; i < s.length; i++)
        {
            for(int j = 0; j < ss.length; j++)
            {
                if(s[i].equals(ss[j]) && u.findString(ret, s[i])<0)
                {
                    ret = u.addString(ret, s[i]);
                }
            }
        }
        return ret;
     }     
     
     
     public String removeNonAlphaNum(String s){
         char c[] = s.toCharArray();
         String ret = "";
         for(int i = 0; i < c.length; i++){
             if(Character.isLetterOrDigit(c[i])|| c[i]==' ')
                 ret += c[i];
         }
         if(ret.length()==0)return null;
         return ret;
     } 
     
     
     public void selectUsedWordsharkImages(){
         String srcPath = "E:\\R\\S3syncImages_New\\iws";
         String descPath = "E:\\R\\wsout";
        JSONParser parser = new JSONParser();
        JSONObject json = null;
        JSONArray results = null;
        try{
            json = (JSONObject)parser.parse(new FileReader(fileImages));
            results = (JSONArray) json.get(elements);
        }
        catch(Exception e){
            int h; 
            h = 0;
        }
        File ff[] = new File(srcPath).listFiles();
        for(int i = 0; i < ff.length; i++){     
            boolean found =false; 
            String nam = ff[i].getName();
            if(nam.startsWith("."))continue;
            String name = nam.substring(0, nam.indexOf(".")); 
            inner: for(int k = 0; k < results.size(); k++) {
                JSONObject p = (JSONObject) results.get(k);
                String olName = ((String)p.get(onlineName));
                if(!olName.startsWith(pre_wordshark_images))continue inner;
                String inStr = charToAscii((olName).toLowerCase());                  
                if(name.equalsIgnoreCase(inStr.substring(0, inStr.indexOf(".")))){
                    found =true;
                    try{
            //           FileUtils.copyFile(ff[i], new File(descPath+shark.sep+olName)); 
                        int f;
                        f = 0;
                    }
                    catch(Exception ee){
                        int h;
                        h = 0;
                    }
                    break;
                }
            }
            if(!found){
                System.out.println("not found Wordshark image "+ff[i].getName());
                int g; 
                 g  = 0;
            }
        }

     }      
     
     public void dirToLowerCaseNames(){
         File ff[] = new File("E:\\CaseResources\\ssentfull").listFiles();
        for(int i = 0; i < ff.length; i++){     
            String nam = ff[i].getName();
            int k = nam.indexOf("_");
                    try{
                        FileUtils.copyFile(ff[i], new File("E:\\CaseResourcesDone\\ssentfull"+shark.sep+nam.substring(0, k)+nam.substring(k).toLowerCase())); 
                    }
                    catch(Exception ee){
                        int h;
                        h = 0;
                    }
        }
     }
     
     

     
     public void writeJsonToTxt(){
         String srcPath = "C:\\jshark-shared2\\publictopicsPrint\\images.json";
         String dest = "C:\\jshark-shared2\\publictopicsPrint\\dest.txt";
         JSONParser parser = new JSONParser();
        JSONObject json = null;
        JSONArray results = null;
        try{
            json = (JSONObject)parser.parse(new FileReader(srcPath));
            results = (JSONArray) json.get(elements);
        }
        catch(Exception e){
            int h; 
            h = 0;
        } 
        String ss[] = new String[]{};
            inner: for(int k = 0; k < results.size(); k++) {
                JSONObject p = (JSONObject) results.get(k);
                ss = u.addString(ss, p.toJSONString());

            }
            
            
            writeFile(dest, ss);
     }
     
     // RECORDINGS PHASE 4  - check if any recordings are missing
     public void checkForRecordings(){
         JSONParser parser = new JSONParser();
        JSONObject json = null;
        JSONArray results = null;
        try{
            json = (JSONObject)parser.parse(new FileReader("C:\\jshark-shared2\\publictopicsPrint\\MappingWORD.json"));
            results = (JSONArray) json.get(elements);
        }
        catch(Exception e){
            int h; 
            h = 0;
        } 
         String filenames[] = new File("E:\\R\\S3syncRecordings_New\\word").list();
        String ss[] = new String[]{};
            inner: for(int k = 0; k < results.size(); k++) {
                JSONObject p = (JSONObject) results.get(k);
                ss = u.addString(ss, p.toJSONString());
                String olName = ((String)p.get(CsvStruct.type_filename));
                if(!olName.endsWith(mp3ext))olName +=mp3ext;
                if(u.findString(filenames, olName)<0){
                    System.out.println(olName);
                }
            }
     }
     
     
     // identifies issues in the  PhotographicResourcesForOnline  folder
     // copies the available photos to a folder - renames them to online name
     //p 4
     public void selectUsedPhotoImages(){
         String srcPath = "E:\\R\\PhotographicResourcesForOnline";
         String descPath = "E:\\R\\photoout";
        JSONParser parser = new JSONParser();
        JSONObject json = null;
        JSONArray results = null;
        try{
            json = (JSONObject)parser.parse(new FileReader(fileImages));
            results = (JSONArray) json.get(elements);
        }
        catch(Exception e){
            int h; 
            h = 0;
        }
        File ff[] = new File(srcPath).listFiles();
        for(int i = 0; i < ff.length; i++){     
            boolean found =false; 
            String nam = ff[i].getName();
            if(nam.startsWith("."))continue;
            String name = nam.substring(0, nam.indexOf(".")); 
            inner: for(int k = 0; k < results.size(); k++) {
                JSONObject p = (JSONObject) results.get(k);
                String olName = ((String)p.get(onlineName));
                if(!olName.startsWith(pre_photographs))continue inner;
                String inStr = charToAscii(((String)p.get(desktopName)).toLowerCase());                  
                if(name.equalsIgnoreCase(inStr)){
                    found =true;
                    try{
                       FileUtils.copyFile(ff[i], new File(descPath+shark.sep+olName)); 
                        int f;
                        f = 0;
                    }
                    catch(Exception ee){
                        int h;
                        h = 0;
                    }
                    break;
                }
            }
            if(!found){
                System.out.println(ff[i].getName());
                int g; 
                 g  = 0;
            }
        }
     }  
     
     public void compareTwoTextLists(String s1, String s2, String possibleExt){
         if(!possibleExt.startsWith("."))possibleExt = "."+possibleExt;
         String ss1[] = u.readFile(s1);
         String ss2[] = u.readFile(s2);
         
         for(int i = 0; i < ss1.length; i++){
             if(ss1[i].endsWith(possibleExt))
                 ss1[i] = ss1[i].substring(0, ss1[i].length()-possibleExt.length());
         }
         for(int i = 0; i < ss2.length; i++){
             if(ss2[i].endsWith(possibleExt))
                 ss2[i] = ss2[i].substring(0, ss2[i].length()-possibleExt.length());
         }         
         String store1[] = new String[]{};
         for(int i = 0; i < ss1.length; i++){
             if(u.findString(ss2, ss1[i]) < 0)
                 store1 = u.addStringSort(store1, ss1[i]);
         }
         for(int i = 0; i < store1.length; i++){
            System.out.println(store1[i]);
         }
         System.out.println("------------------------------------------------");
         String store2[] = new String[]{};
         for(int i = 0; i < ss2.length; i++){
             if(u.findString(ss1, ss2[i]) < 0)
                 store2 = u.addStringSort(store2, ss2[i]);
         }
         for(int i = 0; i < store2.length; i++){
            System.out.println(store2[i]);
         }
     }       
     
     
     
     public String removeNonAlpha(String s){
         char c[] = s.toCharArray();
         String ret = "";
         for(int i = 0; i < c.length; i++){
             if(Character.isAlphabetic(c[i]))ret += c[i];
         }
         if(ret.length()==0)return null;
         return ret;
     }  
    
    public void writeFile(String path, String data[]){
        for(int i = 0; i < data.length; i++) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path, true))) {
            String s;
            s = data[i];
            bw.write(s);
            bw.newLine();
            bw.flush();
        } catch(IOException ex) {
        int o;
        o = 9;
        }  
    }
    }
    
    public String getImageS3Name(String db, String s, String already[], String prefix){
  //      if(prefix == null)
 //           prefix="";
        s = adjustName(s);if(s==null)return null;
        s = s.toLowerCase();
 //       if((db.indexOf("publicsent3")>=0 || db.indexOf("publicsay3")>=0 ) && prefix.equals(pre_word)){
  //          s = "V_"+s;
    //    }
        while(s.indexOf("__")>=0){
            s = s.replace("__", "_");
        }
        if(s.startsWith("_"))s = s.substring(1);
        if(s.length()>maxChars)
            s = s.substring(0,maxChars);
        String temps = prefix+s;
        while(already!=null && u.findString(already, temps)>=0){
            int k = temps.lastIndexOf("_");
            int snoi;
            String sno = k<0?null:temps.substring(temps.lastIndexOf("_")+1);
            try{
                snoi = Integer.valueOf(sno);
            }
            catch(Exception ee){
                temps = prefix+s + "_2";
                continue;
            }
            int p = temps.lastIndexOf("_");
//            temps = prefix+temps.substring(0, temps.length()-(sno.length()-1))+repeatcount++; 
            snoi++;
            temps = temps.substring(0, p+1)+(snoi); 
        }
        return temps.substring(prefix.length());
    }
    
    public String getRecordingS3Name(String db, String s, String already[], String desktopnamealready[], String prefix){
        if(prefix == null)prefix="";
        if((db.indexOf("publicsent2")>=0 || db.indexOf("publicsent3")>=0) ||
                (db.indexOf("publicsay1")>=0 && (prefix.equals(pre_sentencesimple)||prefix.equals(pre_sentencesimplefull))   ))
                {
            String sss = getSentenceType(s);
            if(sss!=null){
                String targets[] = getSentenceTargetWords(s, sss);
                if(targets!=null){
                    String s1 = "";
                    for(int i = 0; targets!=null && i < targets.length; i++){
                        if(i > 0) s+="_";
                        s1 += targets[i];
                    }
                    if(!s1.trim().equals("")){
                        s = s1;
                    }
                }
            }
        }
        if(db.indexOf("publicsent4")>=0){
            s = s.substring(s.indexOf('['));
            s = s.substring(0, s.indexOf(']'));
        }
        s = adjustName(s);if(s==null)return null;
        
        if(u.findString(maintainCasePrefixes, prefix.substring(0, prefix.length()-1))<0)
            s = s.toLowerCase();
       
        s = s.replaceAll("\\s{2,}", " ");
        if(s.startsWith("_"))s = s.substring(1);
        
        if(s.length()>maxChars)
            s = s.substring(0,maxChars);
 //       int repeatcount = 2;
        String temps = prefix+s;
        int h;
        int desktopNameAlreadyUsed =  u.findString(desktopnamealready, s);  // attempting to have diff sounds for well and we'll
        boolean doNewName = prefixes_isUnique[u.findString(sprefixes, prefix.substring(0, prefix.length()-1))];  // prefixes where all items must have unqiue name
        if(!doNewName && desktopNameAlreadyUsed>=0){
            h = already[desktopNameAlreadyUsed].indexOf('_');
            return already[desktopNameAlreadyUsed].substring(h+1);   
        }
        if(doNewName || desktopNameAlreadyUsed<0){
            while(u.findString(already, temps)>=0){
                int k = temps.lastIndexOf("_");
                int snoi;
                String sno = k<0?null:temps.substring(temps.lastIndexOf("_")+1);
                try{
                    snoi = Integer.valueOf(sno);
                }
                catch(Exception ee){
                    temps = prefix+s + "_2";
                    continue;
                }
                // worried that this looks wrong:
//                temps = prefix+temps.substring(0, temps.length()-(sno.length()-1))+repeatcount++; 
                // so doing this instead:
                /*
                NEED TO TEST - HOW COME THE ABOVE SEEMED TO BE WORKING?
                
                */
                int p = temps.lastIndexOf("_");
                snoi++;
                temps = temps.substring(0, p+1)+(snoi); 
                int g; 
                g  =9;
            }            
        }
        h = temps.indexOf('_');
        return temps.substring(h+1);
    }  
    
    
    /*
    public void productDesktopRecordings(){
        String pathJson = "C:\\jshark-shared2\\publictopicsPrint\\MappingLNAME.json";
  //      String pathRecordingsBaseFiles = "Files";
  //      String pathRecordingsBaseLists = "Lists";
        String pathOutput = fileRecordingsBase + "FileList\\";
        ArrayList dbLists = new ArrayList();

        JSONParser parser = new JSONParser();
        try {
            JSONObject json = (JSONObject)parser.parse(new FileReader(pathJson));
            JSONArray results = (JSONArray) json.get(elements);
            for(int i = 0; i < results.size(); i++) {
                JSONObject p = (JSONObject) results.get(i);
                String ssLists[] = new String[]{};
                String ssFiles[] = new String[]{};
                String db = (String)p.get(CsvStruct.type_desktopdb);
                boolean found = false;
                for(int j = 0; j < dbLists.size(); j++){
                    if(dbLists.get(0).equals(db)){
                        found = true;
                        ssLists = (String[])dbLists.get(1);
                        ssFiles = (String[])dbLists.get(2);
                    }
                }
                if(!found){
                    dbLists.add(db);                    
                    ssLists = u.readFile(fileRecordingsBase+fileRecordingsSubFolderLists+shark.sep+db+txtext);
                    File ffFiles[] = (new File(fileRecordingsBase+fileRecordingsSubFolderFiles+shark.sep+db)).listFiles();
                    ssFiles = new String[]{};
                    for(int j = 0; j < ffFiles.length; j++){
                        ssFiles = u.addString(ssFiles, ffFiles[j].getAbsolutePath());
                    }
                    if(ssLists.length != ssFiles.length){
                        int g;
                        g = 0;
                    }
                    
                    
                    for(int j = 0; j < ssLists.length; j++){
                        ssLists[j] = ssLists[j].replace("Ã¯Â»Â¿", "");
                        ssLists[j] = ssLists[j].replace("Ã‚", "");
                    }
                    
  //                  if(ssLists[0].startsWith("Ã¯Â»Â¿"))ssLists[0] = ssLists[0].substring(3);
                    dbLists.add(ssLists);
                    dbLists.add(ssFiles);
                }
                String desktopname = (String)p.get(CsvStruct.type_desktopname);
                String onlinename = (String)p.get(CsvStruct.type_filename);
                File f = recordingFileFromDesktopName(ssLists, ssFiles, desktopname);
                try{
                    FileUtils.copyFile(f, new File(pathOutput+(onlinename))); 
                }
                catch(Exception ee){
                    int h;
                    h = 0;
                }                          
            }            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    */
    
    
    
    
    public File recordingFileFromDesktopName(String ssLists[], String ssFiles[], String s){
        int k = u.findString(ssLists, s);
        if(k < 0){
            int g;
            g = 0;
        }
        
        return new File(ssFiles[k]);
    }    
    
    
    
    private String getSentenceType(String s){    
        s = s.trim();
        if(s.indexOf("/")>=0){
            return SENTENCECLOZE_TYPE;
        }
        else return null;
    }
       
    
    private String[] getSentenceTargetWords(String s, String type){
        sentence sent = new sentence(s,null);
        if(sent.type == sent.JUMBLED ||
                sent.type == sent.TYPE ||
                sent.type == sent.PICK
                ){
            return null;
        }    
        
        String ret[] = new String[]{};
        ret = sent.getAnswerList();

        if(ret.length==0)return null;
        return ret;
    }
     
    
    
    private String adjustName(String s){
        s = s.replace("!!", "");
        s = s.replace("~~", "");
        s = s.replace("~", "");
        if(s.endsWith("=")) s = s.substring(0, s.length()-1);
        s = s.replace("!1", "");
        s = s.replace("!", "");
        char c[] = s.toCharArray();
        String ret = "";
        for(int i = 0; i < c.length; i++){
            if(c[i] == '_' && i !=c.length-1){
                ret += c[i];
            }
            if(Character.isLetterOrDigit(c[i])){
                ret += c[i];
            }
            else if(c[i] == u.phonicsplit || c[i] == ' '){
                ret += "_";
            }
        }
        if(ret.trim().equals(""))ret = null;
        return ret;
//        return ret;
    }
        
    
    private String[] getTypePrefix(JSONArray results, String db1, String s){
        boolean fl;
        if(db1.indexOf("publicsent1")>=0){
            return new String[]{pre_topicRecording, null};
        }
        if(db1.indexOf("publicsent4")>=0){
            return new String[]{pre_sentencetest, null};
        }
        else if((fl=db1.indexOf("publicsent3")>=0) || db1.indexOf("publicsent2")>=0){
            for(int i = 0; i < results.size(); i++) {
                JSONObject p = (JSONObject) results.get(i);
                String simpori = (String)p.get(simpleSentFull);
                String simp = simpori;
                String stand = (String)p.get(standSent);
                if(simp == null && stand == null)
                    continue;
                int h;
                if(simp!= null && (h = simp.indexOf('{'))>=0){
                    simp = simp.substring(0, h);
                }
                if(stand!= null && (h = stand.indexOf('{'))>=0){
                    stand = stand.substring(0, h);
                }                
                if(s.equalsIgnoreCase(simp)){
                    String other = (String)p.get(simpleSent);
                    if(db.query(fl?"publicsay3":"publicsay1", other, db.WAV)<0){ 
                        System.out.println("Not in "+(fl?"publicsay3":"publicsay1")+"   "+ other);
                        int g;
                        g = 0;
                    }                   
                    String s1 =  new sentence(simpori, null).stripcloze(); 
                    return new String[]{pre_sentencesimple, s1};    // not null
                }
                else if(s.equalsIgnoreCase(stand)){
                    return new String[]{pre_sentence, null};
                }
            }
            return new String[]{pre_sentence, null};
        }
        else{
            boolean bfl;
            if(  (bfl= db1.indexOf("publicsay3")>=0) || db1.indexOf("publicsay1")>=0  ){
                for(int i = 0; i < results.size(); i++) {
                    JSONObject p = (JSONObject) results.get(i);
                    String cap = (String)p.get(simpleSent);
                    if(cap == null)
                        continue;
                    if(s.equalsIgnoreCase(cap)){
                        String otherori = (String)p.get(simpleSent);
                        String other = (String)p.get(simpleSentFull);
                        int k;
                        if((k=other.lastIndexOf('{'))>=0){
                            other = other.substring(0, k);
                        }
                        if(db.query((bfl?"publicsent3":"publicsent2"), other, db.WAV)<0){
                            System.out.println("Not in "+(fl?"publicsent3":"publicsent2")+"   "+ other);
                            int g;
                            g = 0;
                        }
                        return new String[]{pre_sentencesimplefull, otherori};     // not null
                    }
                }
            }
            if(db1.indexOf("publicsay1")>=0){
                String hellos[] = new String[]{"hello", "hello1", "hello2", "hello3", "hello4"};
                if(u.findString(hellos, s)>=0)return new String[]{pre_system, null};
            }
            
            if (s.endsWith("="))return new String[]{pre_homophoneExplanation, null};
            else if (s.endsWith("~~"))return new String[]{pre_phonicSyllable, null};
            else if (s.endsWith("!1~"))return new String[]{pre_letterName, null};
            else if (s.endsWith("~"))return new String[]{pre_phonicSound, null};
            else if (s.endsWith("!1"))return new String[]{pre_nonWord, null};        
            else if (s.endsWith("!!"))return new String[]{pre_nonWord, null};  
            else if (s.endsWith("!") && s.indexOf(' ')<0)return new String[]{pre_nonsenseWord, null};
            else {
                for(int i = 0; i < results.size(); i++) {
                    JSONObject p = (JSONObject) results.get(i);
                    String cap = (String)p.get(caption);
                    if(cap == null)
                        continue;
                    if(s.equalsIgnoreCase(cap)){
                        return new String[]{pre_cap, null}; 
                    }
                }
            }
        }
        return new String[]{pre_word, null};
    }
    
    public void findUnmatchedSimpleSentences(){
        JSONParser parser = new JSONParser();
        JSONArray jsonssent3 = null;
        try {
            JSONObject json = (JSONObject)parser.parse(new FileReader(fileSounds));
            jsonssent3 = (JSONArray) json.get(elements);

        } catch (Exception e) {
            e.printStackTrace();
        }   
        outer: for(int i = 0; i < jsonssent3.size(); i++) {
            JSONObject p = (JSONObject) jsonssent3.get(i);
            String sent = (String)p.get(fullSentence);
            String on = (String)p.get(onlineName);
            int which = on.startsWith(pre_sentencesimplefull)?1:(on.startsWith(pre_sentencesimple)?0:-1);
            if(which<0)continue;
            boolean found = false;
            inner: for(int j = 0; j < jsonssent3.size(); j++) {
                JSONObject p2 = (JSONObject) jsonssent3.get(i);
                String sent2 = (String)p2.get(fullSentence);
                if(sent2==null)continue inner;
                if(!sent2.equals(sent))continue inner;
                String on2 = (String)p2.get(onlineName);
                int which2 = on2.startsWith(pre_sentencesimplefull)?1:(on2.startsWith(pre_sentencesimple)?0:-1);
                if(which2<0)continue inner;
                found = true;
                continue outer;
            }
            if(!found){
                System.out.println(on);
            }
        }                
    }
    
    
    public void findUnmatchedSimpleSentences2(){
        JSONParser parser = new JSONParser();
        JSONArray jsonssent3 = null;
        try {
            JSONObject json = (JSONObject)parser.parse(new FileReader("C:\\jshark-shared2\\publictopicsPrint\\MappingSSENT.json"));
            jsonssent3 = (JSONArray) json.get(elements);

        } catch (Exception e) {
            e.printStackTrace();
        }   
        String ss[] = new String[]{};
        int ii[] = new int[]{};
        outer: for(int i = 0; i < jsonssent3.size(); i++) {
            JSONObject p = (JSONObject) jsonssent3.get(i);
            String sent = (String)p.get("FullSentence");
            if(sent==null)continue;
            String s2 = new sentence(sent, null).stripcloze();
            int k;
            if((k=u.findString(ss, s2))>=0){
                System.out.println(sent + "      "+s2);
                JSONObject p2 = (JSONObject) jsonssent3.get(ii[k]);
                System.out.println((String)p2.get("FullSentence") + "      "+s2);
            }
            ss = u.addString(ss, s2);
            ii = u.addint(ii, i);
        }                
    }    
    
    
    
    // after this do renameToOnlineNameImages
public void giffingMakeGifs(boolean copyNonGifs, boolean checkloop) { 
    String src = "D:\\WordsharkImageScreenshotsOUT\\";
    String out = "D:\\WordsharkImageScreenshotsOUTGiffed\\";
    String sloop = "D:\\WordsharkImageScreenshotsOUTTexts\\";

    String startAnim = "_0001";
    // delay is   delay / 100  = FPS
    // FPS / 10 = every X ms
    double delay = 0.066;
    File f = new File(src);
    String fileNames[] = f.list();
    Arrays.sort(fileNames);
    for(int i = 0; i < fileNames.length; i++){
        int k;
        if((k=fileNames[i].indexOf(startAnim+pngext))>=0){
            String s = fileNames[i].substring(0, k);
            boolean toloop = false;
            if(checkloop){
                if(new File(sloop + s + "_loop.txt").exists()) toloop = true;
                else if(new File(sloop + s + "_nonloop.txt").exists()) toloop = false;
                else
                {
         //           u.okmess(shark.programName, "problem3523", sharkStartFrame.mainFrame);
                } 
            }
           
            String cmd = giffingGetCmd("\""+src+ s+"_*"+pngext+"\"", "\""+out + s + gifext+ "\"", delay, toloop);
            try{
                Process p = Runtime.getRuntime().exec(cmd);
                int exitCode = p.waitFor();
                if(exitCode != 0){
                    u.okmess(shark.programName, "problem95433", sharkStartFrame.mainFrame);
                }
            }
            catch(Exception e){
               u.okmess(shark.programName, "problem73273", sharkStartFrame.mainFrame);
            }
            int h;
             h = 0;
        }
        else if(copyNonGifs){
            boolean isAnimation = false;
            try{
                if(fileNames[i].endsWith(pngext)){
                    String s = fileNames[i].substring(fileNames[i].length() - (pngext.length()+5));            
                    if(s.startsWith("_")){
                        s = s.substring(0, s.length() - pngext.length());
                        s = s.substring(1);
                        Pattern pattern = Pattern.compile("[0-9]+");
                        Matcher matcher =  pattern.matcher(s);
                        if(matcher.matches()){
                            isAnimation = true;  
                        }  
                    }
                }                
            }
            catch(Exception e){
                
            }
            if(!isAnimation)
                u.copyfile(new File(src+fileNames[i]), new File(out+fileNames[i]));
        }
    }
}         
    
    
    // after this do renameToOnlineNameImages
public void giffingPhotosMakeGifs() { 
    String src = "D:\\T\\";
    String out = "D:\\TOUT\\";
    String sloop = "D:\\WordsharkImageScreenshotsOUTTexts\\";

    String startAnim = "00";
    String fileNameTemplate = "frame_*_delay-0.01s";
    // delay is   delay / 100  = FPS
    // FPS / 10 = every X ms
    double delay = 0.066;
    File f = new File(src);
    String fileNames[] = f.list();
    Arrays.sort(fileNames);
 //   for(int i = 0; i < fileNames.length; i++){
        int k;

      //      String s = fileNames[i].replace("*",startAnim);
     //       startAnim = getNextNumber(startAnim);
            boolean toloop = false;

           
            String cmd = giffingGetCmd("\""+src+fileNameTemplate+gifext+"\"", "\""+out + "33333" + gifext+ "\"", delay, toloop);
            try{
                Process p = Runtime.getRuntime().exec(cmd);
                int exitCode = p.waitFor();
                if(exitCode != 0){
                    u.okmess(shark.programName, "problem95433", sharkStartFrame.mainFrame);
                }
            }
            catch(Exception e){
               u.okmess(shark.programName, "problem73273", sharkStartFrame.mainFrame);
            }
            int h;
             h = 0;
 

  //  }
}    

private String getNextNumber(String n) {
    int i = Integer.parseInt(n);
    i++;
    String s2 = String.valueOf(i);
    if(s2.length()==1)s2 = "0"+s2;
    return s2;
}
    
    


    // after this do renameToOnlineNameImages
public void giffingMakeVideoGifs() { 
    String src = "D:\\OneDrive\\Desktop\\jpgs\\1\\";
    String out = "D:\\OneDrive\\Desktop\\jpgsOUT\\";
    String startAnim = "00";
    // delay is   delay / 100  = FPS
    // FPS / 10 = every X ms
    double delay = 0.066;
    File f = new File(src);
    String fileNames[] = f.list();
//    for(int j = 0; j < folderNames.length; j++){
//        String fileNames[] = folderNames[j].list();
        Arrays.sort(fileNames);
        for(int i = 0; i < fileNames.length; i++){
            int k;
            if((k=fileNames[i].indexOf(startAnim+jpgext))>=0){
                String s = fileNames[i].substring(0, k);
                boolean toloop = false;

                String cmd = giffingGetVideoCmd("\""+src+ s+"*"+jpgext+"\"", "\""+out + s + gifext+ "\"", delay, toloop);
                try{
                    Process p = Runtime.getRuntime().exec(cmd);
                    int exitCode = p.waitFor();
                    if(exitCode != 0){
                        u.okmess(shark.programName, "problem95433", sharkStartFrame.mainFrame);
                    }
                }
                catch(Exception e){
                   u.okmess(shark.programName, "problem73273", sharkStartFrame.mainFrame);
                }
                int h;
                 h = 0;
            }
        }
    //}
}     
    

public void commentPngs(String folderPath,  String comment) {
    File f = new File(folderPath);
    File files[] = f.listFiles();
    for(int i = 0; i < files.length; i++){
        if(files[i].getName().endsWith(pngext)){
            String cmd = giffingGetCommentPngCmd(files[i].getAbsolutePath(), comment);
                try{
                    Process p = Runtime.getRuntime().exec(cmd);
                    int exitCode = p.waitFor();
                    if(exitCode != 0){
                        u.okmess(shark.programName, "problem95433", sharkStartFrame.mainFrame);
                    }
                }
                catch(Exception e){
                   u.okmess(shark.programName, "problem73273", sharkStartFrame.mainFrame);
                }
                int h;
                 h = 0;
            }
        }    
    
    
    /*
    
    to get the comment, you have to run:
    cd C:\Program Files (x86)\ImageMagick-7.0.8-Q16
    identify.exe -verbose d:\Test\CAL_04_APRIL.png
    */
}


public String giffingGetCommentPngCmd(String filePath,  String comment) {
    return "\"C:\\Program Files (x86)\\ImageMagick-7.0.8-Q16\\mogrify.exe\" -comment "+"\""+comment+ "\" "+"\""+filePath+"\"";   
}


// after strip you can end up with instances of " ,"
public String stripPipes(String s) {
    /*
    char cc[] = s.toCharArray();
    
    for(int i = 0; i < cc.length; i++){
        if(cc[i] == ','){
            
        }
        
    }      
    */

    String ss[] = u.splitString(s);
    for(int i = 0; i < ss.length; i++){
        ss[i] = ss[i].trim();
    }
    return u.combineString(ss, " ");
  
}

public String addPipes(String s, int charLimit) {
    int startPos = 0;
    int segStart = 1;
    int charStart = 0;
    int loopIndex = 0;
    String ss[] = new String[]{s};
    while(true){
        // -1 because of the | just added has removed a " " seg
        int ii[] = pipesLookFor(s, charStart+charLimit, segStart-1);
        if(ii[0] < 0){
            if(loopIndex > 0){
                int noWordsInLastLine = ii[2];
                // don't have anything under 3 words on last line
                if(noWordsInLastLine < 3){
                    return ss[loopIndex-1];
                }
            }
            return s;
        }
        charStart = ii[0];
        segStart = ii[1];
        loopIndex++;
        s = s.substring(startPos, charStart).trim() + "|" + s.substring(ii[0]).trim();
        ss = u.addString(ss, s);
    }
}

private int[] pipesLookFor(String s, int charLimit, int inst) {
    sentence sen = new sentence(s);
    int wordedSegCount = 0;
    for(int i = inst; i < sen.segs.length; i++){
        boolean isDistractor = sen.segs[i].choice && !sen.segs[i].firstchoice;
        boolean hasCharacter = sen.segs[i].val.matches(".*[a-zA-Z].*");
        String lastseg;
        if(!isDistractor && hasCharacter){
            wordedSegCount++;
        }
        // don't create line break in the middle of the multiple choice options
        if(isDistractor){
            charLimit+=sen.segs[i].val.length();
        }
        // don't create line break if this is the last of the segs
        else if(i == sen.segs.length-1){
            int h = 0;
        }
        // don't create line break the seg after the potential break is contains no letters (e.g. a comma)
        else if(!hasCharacter && sen.segs[i].val.length() == 1){
            int h = 0;
        }
        // don't create line break when seg before is not a space and current is not a space
        // to get rid of this problem:
        // "You can multiply out the /brackets/expand/like terms/solve/ in 3(|4+5y) to get 12 + 15y"
        else if(i > 0 && !sen.segs[i-1].val.trim().equals("")){
            int h = 0;
        }
        // don't do a new line after last non blank seg is a single non letter character
        // to get rid of this problem:
        // "Through photosynthesis, plants produce oxygen/ -|essential to nearly all of life"
        // from
        // "Through photosynthesis, plants produce oxygen/ - essential to nearly all of life"
        else if((lastseg = getLastNonBlankSeg(sen.segs, i)) != null && 
                (
                lastseg.equals("-") || 
                lastseg.equals("–")
                ))   {
            int h = 0;
        }        
        
        else if(sen.segs[i].pos1 > charLimit){
 //           if(!sen.segs[i].val.trim().equals("") && sen.segs.length < i+1) 
 //               return new int[]{-1, -1, wordedSegCount};
            // 0: char index of segment after aplit, 1: index of char after split
            int[] ii = new int[]{sen.segs[i].pos1, i};
            return ii;         
        }
    }    
    //return pos in string for pipe to be added
    return new int[]{-1, -1, wordedSegCount};
}


private String getLastNonBlankSeg(sentence.seg[] s, int i) { 
    while(i > 0){
        i--;
        if(!s[i].val.trim().equals(""))
            return s[i].val;
    }
    return null;
}  

private void pngsFromMP4() { 
    String cmd = "\"C:\\Program Files\\ffmpeg-20190211-6174686-win64-static\\bin\\ffmpeg";
    cmd += " -i stopping.mp4 -vf fps=10 out%d.png";

}  


    
private String giffingGetVideoCmd(String searchName, String outputName, double delay, boolean loop) { 
    String cmd = "\"C:\\Program Files (x86)\\ImageMagick-7.0.8-Q16\\convert\" -delay ";
    cmd += String.valueOf(delay);
    cmd += " -dispose Background -loop ";
    cmd += loop?"0 ":"1 ";   
    cmd += searchName +  " ";
  //    cmd += "-strip -coalesce -layers optimize-transparency ";       // optimize & optimize-frame & optimize-plus turns the transparency into reduced dimesions. 
   cmd += "-fuzz 5% -layers Optimize "; 
//     cmd += "-layers optimize-transparency ";  // no size reduction - displays ok
//     cmd += "-strip -coalesce ";  // no size reduction - displays ok    
 //   cmd += "-strip -coalesce -layers optimize-transparency ";  // size reduction - displays badly  
 //   cmd += "-coalesce -layers optimize-transparency ";   // size reduction - displays badly  
 //   cmd += "-strip -layers optimize-transparency ";   // no size reduction - displays ok
  //   nothing   //    // no size reduction - displays ok
    // THEREFORE COALESCE WITH OPTIMISE SEEMS TO BE THE PROBLEM
    
      cmd += "-dither none -matte -depth 8 ";    // reduces 354kb to 302kb  - displays ok
 //   cmd += "-dither none ";       // reduces 354kb to 302kb  - displays ok
 //   cmd += "-matte -depth 8  ";  // no size reduction - displays ok
 //     cmd += "-depth 8 ";    // no size reduction - displays ok
 //   cmd += "-fuzz 7%  ";    // no size reduction - displays ok
 //    cmd += "-quality 40%  ";    // no size reduction - displays ok   
     
    return cmd += outputName;
}   


private String giffingGetCmd(String searchName, String outputName, double delay, boolean loop) { 
    String cmd = "\"C:\\Program Files (x86)\\ImageMagick-7.0.8-Q16\\convert\" -delay ";
    cmd += String.valueOf(delay);
    cmd += " -dispose Background -loop ";
    cmd += loop?"0 ":"1 ";   
    cmd += searchName +  " ";
  //    cmd += "-strip -coalesce -layers optimize-transparency ";       // optimize & optimize-frame & optimize-plus turns the transparency into reduced dimesions. 

//     cmd += "-layers optimize-transparency ";  // no size reduction - displays ok
//     cmd += "-strip -coalesce ";  // no size reduction - displays ok    
//    cmd += "-strip -coalesce -layers optimize-transparency ";  // size reduction - displays badly  
//    cmd += "-coalesce -layers optimize-transparency ";   // size reduction - displays badly  
 //   cmd += "-strip -layers optimize-transparency ";   // no size reduction - displays ok
  //   nothing   //    // no size reduction - displays ok
    // THEREFORE COALESCE WITH OPTIMISE SEEMS TO BE THE PROBLEM
    
 //     cmd += "-dither none -matte -depth 8 ";    // reduces 354kb to 302kb  - displays ok
    cmd += "-dither none ";       // reduces 354kb to 302kb  - displays ok
 //   cmd += "-matte -depth 8  ";  // no size reduction - displays ok
 //     cmd += "-depth 8 ";    // no size reduction - displays ok
 //   cmd += "-fuzz 7%  ";    // no size reduction - displays ok
 //    cmd += "-quality 40%  ";    // no size reduction - displays ok   
     
    return cmd += outputName;
}    



    public void makeTransparentBG_PNGs(){
       try { 
    //                File ff[] = new File("F:\\IWS_Images_Pool_Converted\\AllA-Kpublicimage").listFiles();
   //                 String outputFolder = "F:\\IWS_Images_Pool_Converted\\OUT4\\";
    //        File ff[] = new File("D:\\Dropbox\\PhotosUnmatchedResourcesForOnline").listFiles();
    //        String outputFolder = "D:\\S3syncImagesOUT\\";
            File ff[] = new File("D:\\WordsharkImageScreenshots").listFiles();
     String outputFolder = "D:\\WordsharkImageScreenshotsOUT\\";       
            
            
       for(int i = 0; i < ff.length; i++){
               if(ff[i].getName().startsWith(".") || ff[i].getName().endsWith(".ini") || ff[i].getName().endsWith(".txt"))continue;
            BufferedImage image = ImageIO.read(ff[i]);
            if(image==null){
                int g; 
                 g = 0;
            }
            float imW = image.getWidth();
            float imH = image.getHeight();
            
            float contW = 529;
            float contH = 529;
            if(imW == contW && imH == contH){
                u.copyfile(ff[i], new File(outputFolder+ff[i].getName()));
                continue;
            }
            int x;
            int y;
            if(imW / imH > contW / contH)
            {
                // width > height
                imH = contH*imH/imW;
                image = makeTransparentBG_PNGs_resize(image, (int)contW, (int)imH);
                x = 0;
                y = ((int)contH-(int)imH)/2;
            }
            else{
                imW = contW*imW/imH;
                image = makeTransparentBG_PNGs_resize(image, (int)imW, (int)contH);
                x = ((int)contW-(int)imW)/2;
                y = 0;
            }
            BufferedImage result = new BufferedImage(
                    (int)contW,
                    (int)contH,
                    BufferedImage.TYPE_INT_ARGB);
            result.createGraphics().drawImage(image, x, y, null, null);
            ImageIO.write(result, "png", new File(outputFolder+ff[i].getName().substring(0, ff[i].getName().lastIndexOf("."))+".png"));                        
                    }
                    
      //      File input = new File("C:\\Users\\paulr\\Desktop\\test.jpg");
      //      File output = new File("C:\\Users\\paulr\\Desktop\\test2.png");
      //              image.getWidth(),
       //             image.getHeight(),


        }  catch (IOException e) {
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    private BufferedImage makeTransparentBG_PNGs_resize(BufferedImage img, int newW, int newH){
    Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
    BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

    Graphics2D g2d = dimg.createGraphics();
    g2d.drawImage(tmp, 0, 0, null);
    g2d.dispose();

    return dimg;
        
        
    }
   
 
    
    
}
