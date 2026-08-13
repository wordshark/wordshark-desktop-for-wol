/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shark;

import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.*;
import java.util.*;
import java.io.*;
import javax.swing.*;
import java.util.Arrays;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;
import javax.swing.JOptionPane;
import shark.games.pattern;
import shark.games.snakesandladders;

import java.net.HttpURLConnection;
import java.text.*;

import java.net.URLEncoder;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
/**
 *
 * @author paulr
 */
public class MYSQLUpload {
    
    final static String PROJECT_PATH = "C:\\Users\\PaulRubie\\Documents\\NetBeansProjects\\wordshark_desktop_for_wol\\";
    static String accessToken = null;
    static int currentEnvironment = -1;
    static String currentCourse = null;
    
    static JSONArray insertsJson = new JSONArray();
    
    public class apiConfig{
      String url = null;
      String accessToken = null;
      String accessGrantType = "password";
      String accessClientId = null;
      String accessSecret = null;
      String accessScope = "*";
      String accessUserName = null;
      String accessPassword = null;
      
      public apiConfig(
              String urlP,
              String accessClientIdP,
              String accessSecretP,
              String accessUserNameP,
              String accessPasswordP
              ){
          url = urlP;
          accessClientId = accessClientIdP;
          accessSecret = accessSecretP;
          accessUserName = accessUserNameP;
          accessPassword = accessPasswordP;

       }   
    }
    
    final apiConfig[] API_CONFIGS = new apiConfig[]{
        new apiConfig(
                "http://course-api.onwordshark.local/",
                "2",
                "Xv9EUOiayTVU6utfgHc6NM48JFOReSy4FwFzmuBs",
                "platformnoreply@onwordshark.com",
                "X4HvBt6%BuZ=q&nB"
        ),
        new apiConfig(
                "https://course-api-staging.onwordshark.com/",
                "2",
                "Xv9EUOiayTVU6utfgHc6NM48JFOReSy4FwFzmuBs",
                "platformnoreply@onwordshark.com",
                "X4HvBt6%BuZ=q&nB"
        ),        
        new apiConfig(
                "https://course-api.onwordshark.com/",
                "2",
                "Xv9EUOiayTVU6utfgHc6NM48JFOReSy4FwFzmuBs",
                "platformnoreply@onwordshark.com",
                "X4HvBt6%BuZ=q&nB"
        )
    };

    String currCourseVersion = null;

    int WORD_TYPE_STANDARD = 0;
    int WORD_TYPE_PAIRS = 1;
    int WORD_TYPE_EXTENDED = 2;
    int WORD_TYPE_PATTERNGAME = 3;
    int WORD_TYPE_SENTENCE = 4;
    int WORD_TYPE_TARGET = 5;
    int WORD_TYPE_FL = 6;
    int WORD_TYPE_SIMPLE = 7;
    int HELICOPTERMAXSELECT = 9;
    
    final static int ENV_LOCAL = 0;
    final static int ENV_STAGING = 1;
    final static int ENV_LIVE = 2;    
    
    final static String[] ENV_NAMES = new String[]{
        "Local Environment",
        "Staging",
        "Live"
    };

    static final int selectCountTargetNoPattern = 15;
    static final int selectCountDistractorNoPattern = 15;
    static final int selectCountTargetNoSnakesAndLadders = 30;
    static final int selectCountDistractorNoSnakesAndLadders = 100;

    final static String GAME_BLENDSOUNDS = "Blend sounds";
    final static String GAME_BOMBS = "Bombs";
    final static String GAME_BUILDSENTENCESPOKEN = "Build sentence";
    final static String GAME_CATCHING = "Catching";
    final static String GAME_FINDPICTUREFORSENTENCE = "Find pics (sentence)";
    final static String GAME_FINDPICTUREFROMWRITTEN = "Find pics for words";
    final static String GAME_FINDPICTUREPHONICS = "Find picture (ph)";
    final static String GAME_FINDPICTUREVOCABULARY = "Find pics (vocab)";
    final static String GAME_FINDSOUND = "Find sound";
    final static String GAME_FINDSYMBOL = "Find symbol";
    final static String GAME_FINDWORD = "Find word";
    final static String GAME_FINDWORDPHONICS = "Find word (ph)";
    final static String GAME_HELICOPTERLISTEN = "Helicopter (listen)";
    final static String GAME_HELICOPTERSPELL = "Helicopter (spell)";
    final static String GAME_HUNT = "Hunt";
    final static String GAME_HUNTPHONICS = "Hunt (ph)";
    final static String GAME_HUNTSYLLABLES = "Hunt (syllables)";
    final static String GAME_JIGSAWONSETANDRIME = "Jigsaw (onset & rime)";
    final static String GAME_JIGSAWPHONICS = "Jigsaw (ph)";
    final static String GAME_JIGSAWSYLLABLES = "Jigsaw (syllables)";
    final static String GAME_LEARNVOCAB = "Learn vocab";
    final static String GAME_MAZESPELLTEST = "Maze spell check";
    final static String GAME_MEMORY = "Memory";
    final static String GAME_MOVINGSPELLCHECK = "Moving spell check";
    final static String GAME_NOAHSARK = "Noah's ark";
    final static String GAME_NOAHSARKPHONICS = "Noah's ark (ph)";
    final static String GAME_NOAHSARKSYLLABLES = "Noah's ark (syllables)";
    final static String GAME_PAIRS = "Pairs";
    final static String GAME_PATTERN = "Pattern";
    final static String GAME_PHONICTILES = "Phonics tiles";
    final static String GAME_READINGTEST = "Reading check";
    final static String GAME_SALVAGE = "Salvage";
    final static String GAME_SAYSENTENCE = "Say sentence";
    final static String GAME_SAYSOUNDSPHONICS = "Say sounds (ph)";
    final static String GAME_SAYWORD = "Say word";
    final static String GAME_SAYWORDFORPICTURE = "Say word (pictures)";
    final static String GAME_SENTENCECROSSWORD = "Sentence crossword";
    final static String GAME_SHARKS = "Sharks";
    final static String GAME_SHARKSALTER = "Sharks alter";
    final static String GAME_SIMPLECROSSWORD = "Simple crossword";
    final static String GAME_SNAKESANDLADDERS = "Snakes & ladders";
    final static String GAME_SNAP = "Snap";
    final static String GAME_SPELLTEST = "Spell check";
    final static String GAME_SPLITSOUND = "Split sound";
    final static String GAME_WORDSEARCH = "Word search";
    final static String GAME_JUMBLED = "Jumbled";
    final static String GAME_TRACKING = "Tracking";

    final static String[] ALL_GAMES = new String[]{
        GAME_BLENDSOUNDS,
        GAME_BOMBS,
        GAME_BUILDSENTENCESPOKEN,
        GAME_CATCHING,
        GAME_FINDPICTUREFORSENTENCE,
        GAME_FINDPICTUREFROMWRITTEN,
        GAME_FINDPICTUREPHONICS,
        GAME_FINDPICTUREVOCABULARY,
        GAME_FINDSOUND,
        GAME_FINDSYMBOL,
        GAME_FINDWORD,
        GAME_FINDWORDPHONICS,
        GAME_HELICOPTERLISTEN,
        GAME_HELICOPTERSPELL,
        GAME_HUNT,
        GAME_HUNTPHONICS,
        GAME_HUNTSYLLABLES,
        GAME_JIGSAWONSETANDRIME,
        GAME_JIGSAWPHONICS,
        GAME_JIGSAWSYLLABLES,
        GAME_LEARNVOCAB,
        GAME_MAZESPELLTEST,
        GAME_MEMORY,
        GAME_MOVINGSPELLCHECK,
        GAME_NOAHSARK,
        GAME_NOAHSARKPHONICS,
        GAME_NOAHSARKSYLLABLES,
        GAME_PAIRS,
        GAME_PATTERN,
        GAME_PHONICTILES,
        GAME_READINGTEST,
        GAME_SALVAGE,
        GAME_SAYSENTENCE,
        GAME_SAYSOUNDSPHONICS,
        GAME_SAYWORD,
        GAME_SAYWORDFORPICTURE,
        GAME_SENTENCECROSSWORD,
        GAME_SHARKS,
        GAME_SHARKSALTER,
        GAME_SIMPLECROSSWORD,
        GAME_SNAKESANDLADDERS,
        GAME_SNAP,
        GAME_SPELLTEST,
        GAME_SPLITSOUND,
        GAME_WORDSEARCH,
        GAME_JUMBLED,
        GAME_TRACKING
    };

    final static String COURSE_WORDSHARK = "Wordshark course";
    final static String COURSE_SUPPLEMENTARY_LISTS = "Supplementary lists";
    final static String COURSE_NAT_CURRICULUM = "English National Curriculum: spellings";
    final static String COURSE_ALPHA_TO_OMEGA = "'Alpha to Omega'";
    final static String COURSE_EVERYDAY_VOCAB = "Everyday vocabulary";
    final static String COURSE_SECONDARY_SUBJECT = "Secondary school subject lists";
    final static String COURSE_HFW = "High Frequency Words (HFW)";
    final static String COURSE_WORDSHARK_TEST = "Wordshark course test";
    final static String COURSE_SPELLING_CATCH_UP = "Spelling catch up for older users";

    final static String[] ALL_COURSES = new String[]{
        COURSE_WORDSHARK,
        COURSE_SUPPLEMENTARY_LISTS,
        COURSE_NAT_CURRICULUM,
        COURSE_ALPHA_TO_OMEGA,
        COURSE_EVERYDAY_VOCAB,
        COURSE_SECONDARY_SUBJECT,
        COURSE_HFW,
        COURSE_WORDSHARK_TEST,
        COURSE_SPELLING_CATCH_UP
    };
    
    
    int[] singleSoundGameIdOrder = new int[]{}; 
    int[] wholeWordGameIdOrder = new int[]{};   
    
    static String letterPatternGames[] = new String[]{GAME_SNAKESANDLADDERS, GAME_PATTERN, GAME_TRACKING};

    static String max19CharGames[] = new String[]{GAME_FINDPICTUREFORSENTENCE, GAME_FINDPICTUREFROMWRITTEN, GAME_FINDPICTUREPHONICS, GAME_FINDPICTUREVOCABULARY};

    static boolean doingPort = false;
    String picPreKey = null;
    final String PICPREF_VALWORDSHARKIM = "wordshark";
    final String PICPREF_VALPHOTOIM = "photo";
    final String PICPREF_KEYSIMPLESENT = "simplesent";

    boolean picPreIsPhoto;

    static boolean MYSQLGameFiltering = false;
    static boolean gotSplits = false;
    static int currAvailableGamesIDs[] = null;

    static final String GTX_GAMES = topic.types[topic.GAMES];
    static final String WORDSHARKTESTPREFIX = "WSCT_";
    static final String WORDSHARKTESTCOURSE = COURSE_WORDSHARK_TEST;
    static final String[] FL_COURSES = new String[]{COURSE_EVERYDAY_VOCAB};
    static final String[] NON_UNIT_NUMBERED_COURSES = new String[]{COURSE_EVERYDAY_VOCAB};
    static final String REWARDS = "Rewards";
    static final String RUDEWORDGAME = GAME_WORDSEARCH;

    static final String ISBEEPSENTENCSIMPLEGAME = GAME_SIMPLECROSSWORD;

    int CAT_PHONICS = 2;
    int CAT_SOUNDS = 1;
    int CAT_PHRASES = 3;
    int CAT_NONPHONICS = 0;
    static final String[] GAMESBLOCKSTOIGNORE = new String[]{"crossword 2", "add suffix", "classify", "snakes & ladders"};
    static final String[] GAMESTOIGNORE = new String[]{"add suffix", "balance", "balloons", "bingo listen", "bingo words", "build phrase (from spoken)", "build phrase (with picture)", "build word", "chunks", "classify", "crossword 2", "dictionary fish", "fast find", "find phrase", "find sound (for picture)", "flums", "fruit machine", "hidden letter", "holes", "jumbled", "learn", "letter maze", "lottery", "maze alter", "moving", "pairs (linked words)", "pairs (sound+letter)", "say phrase", "scan", "scan (linked words)", "shredder", "snap (linked words)", "split sound", "tilt", "tracking", "trains", "trains (phonics)", "trains (syllables)", "word sort", "save the sharks", "rolling"};
    static final String[] PHONICDISTRACTOR_GAMECODEID = new String[]{"findsound"};
    static final String GTX_SUFFIXES = GTX_GAMES + "Add suffix";
    static final String GTX_SENTENCES1 = GTX_GAMES + GAME_SENTENCECROSSWORD;
    static final String GTX_SENTENCES2 = GTX_GAMES + "Crossword 2";
    static final String GTX_SENTENCESSIMPLE = GTX_GAMES + ISBEEPSENTENCSIMPLEGAME;
    static final String[] GTX_PLUS_GAMES = new String[]{"Add suffix", "Classify"};
    static final String[] GTX_SUFFIX_GAMES = new String[]{"Add suffix", "Classify"};
    static final String[] GTX_WANT_EXTENDED_FROM_REFS_GAMES = new String[]{GAME_HELICOPTERLISTEN, GAME_HELICOPTERSPELL};

    static String[] simpleSentence3Games = new String[]{};
    static String[] simpleSentence1Games = new String[]{};

    static String[] fullSentenceGames = new String[]{};


    public static boolean generateImageFiles = false;
    public static sharkImage.saveSharkImage currSaveSharkImage;
    public static String mySqlRudeWords[];
    static long startTime;
    static int attributeCount = -1;
    static int attributeIndexCount = -1;
    static final String SVGIMAGEFOLDER = PROJECT_PATH + "svg";

    static final String RESTJSONFOLDER = PROJECT_PATH + "json_output";
    

    static final String BMPIMAGEFOLDERPLUS = "C:\\xampp\\htdocs\\img\\publicimages\\";
    static final String WEBIMAGEFOLDERPLUS = "/img/publicimages/";
    static final String WEBSVGFOLDERPLUS = "/img/svg/";

    String TOBERECORDEDCSV = sharkStartFrame.publicPathplus + "csv" + shark.sep + "toBeRecorded.csv";

    static final String BMPIMAGEEXTENSIONS[] = new String[]{".jpg", ".png"};


    static final String TOPICBLOCKGAMETYPE_HELICOPTERLISTEN = "HELICOPTERLISTEN";
    static final String TOPICBLOCKGAMETYPE_HELICOPTERSPELL = "HELICOPTERSPELL";
    static final String TOPICBLOCKGAMETYPE_PATTERN = "PATTERN";
    static final String TOPICBLOCKGAMETYPE_PATTERNSNAKES = "PATTERNSNAKES";
    static final String TOPICBLOCKGAMETYPE_CROSSWORD = "SENTENCE";
    static final String TOPICBLOCKGAMETYPE_SIMPLECROSSWORD = "SIMPLESENTENCE";
    static final String GTX_SELECTS_XML = "Selects";
    static final String GTX_SELECT_XML = "Select";
    static final String GTX_SELECTNO_AT_XML = "SelectNo";
    static final String GTX_SELECTGROUPNO_AT_XML = "SelectGroupNo";
    static final String GTX_SELECTDISTRACTORNO_AT_XML = "SelectDistractorNo";
    static final String GTX_REFERENCES_XML = "References";
    static final String GTX_REFERENCE_XML = "Reference";
    static final String GTX_DISTRACTORS_XML = "Distractors";
    static final String GTX_DISTRACTOR_XML = "Distractor";
    static final String GTX_WORD_XML = "Word";
    static final String GTX_WORDNAME_AT_XML = "WordName";
    static final String GTX_WORDIMAGENAME_AT_XML = "WordImageName";



    static final String GTX_WORD_EXCLUDE_REVISE_AT_XML = "WordExcludeRevise";
    static final String GTX_WORD_EXCLUDE_AP_TEST_AT_XML = "WordExcludeApTest";

    static final String GTX_WORD_ROOT_AT_XML = "Root";
    static final String GTX_WORD_SUFFIX_AT_XML = "Suffix";
    static final String GTX_WORDITEMTYPE_AT_XML = "ItemType";
    static final String GTX_WORDISVOWEL_AT_XML = "IsVowel";
    static final String GTX_ISHOMOPHONE_AT_XML = "IsHomophone";
    static final String GTX_ISSOUND_AT_XML = "IsSound";
    static final String GTX_ISLETTERNAME_AT_XML = "IsLetterName";
    static final String GTX_ISBLENDED_AT_XML = "IsBlended";
    static final String GTX_ISNONSENSE_AT_XML = "IsNonsense";
    static final String GTX_ISRUBBISH_AT_XML = "IsRubbish";

    static final String GTX_WORDSPLIT_XML = "WordSplit";
    static final String GTX_WORDSPLITTYPE_AT_XML = "WordSplitType";
    static final String GTX_WORDSPLITPOSITION_AT_XML = "SplitPosition";
    static final String GTX_WORDSPLITSOUNDNAME_AT_XML = "SoundName";

    static final String GTX_GAME_XML = "Game";
    static final String GTX_GAMEISHEADING_AT_XML = "IsGameHeading";
    static final String GTX_GAMENAME_AT_XML = "GameName";
    static final String GTX_GAMECATEGORY_AT_XML = "GameCategory";

    static final String GTX_GAMEBLOCKTYPE_AT_XML = "GameBlockType";


    static final String GTX_TOPICNAME_AT_XML = "TopicName";

    static final String GTX_ISREVISION_AT_XML = "IsRevision";
    static final String GTX_TEACHINGNOTE = "\\";
    static final String GTX_TEACHINGNOTE_AT_XML = "TopicTeachingNote";

    static final String GTX_WORDLISTORDER = "WordListOrder";
    static final String GTX_APPRIORITY = "ApPriority";
    static final String GTX_APNOTINTEST = "ApNotInTest";
    static final String GTX_APNOTINUNITORTEST = "ApNotInUnitOrTest";
    static final String GTX_RECOMMENDEDPHONICONGAMES = "RecommendedPhonicOnGames";
    static final String GTX_RECOMMENDEDPHONICOFFGAMES = "RecommendedPhonicOffGames";
    static final String GTX_PHONICONGAMES = "PhonicOnGames";
    static final String GTX_PHONICOFFGAMES = "PhonicOffGames";
    static final String GTX_SPLITONGAMES = "SplitOnGames";
    static final String GTX_SPLITOFFGAMES = "SplitOffGames";

    static final String GTX_COURSES = topic.types[topic.COURSES];

    static final String GTX_HOMOPHONES = topic.types[topic.HOMOPHONES];
    static final String GTX_TOPICGAMECATEGORYTYPE = "TopicGameCategoryTypes";

    static final String GTX_HOMOPHONES_AT_XML = "AllowHomophones";
    static final String GTX_PAIRS_XML = "Pairs";
    static final String GTX_PAIR_AT_XML = "Pair";
    static final String GTX_PAIRS = topic.types[topic.PAIR];
    static final String GTX_PAIRS_INDEX_AT_XML = "PairsIndex";
    static final String GTX_PAIRS_AT_XML = "AllowPairs";
    static final String GTX_ALLORNONE = topic.types[topic.ALLORNONE];
    static final String GTX_ALLORNONE_AT_XML = "AllOrNone";
    static final String GTX_ALLORNONE_INDEX_AT_XML = "AllOrNoneIndex";
    static final String GTX_INORDER = topic.types[topic.INORDER];
    static final String GTX_INORDER_AT_XML = "InOrder";

    static final String GTX_REVISION = topic.types[topic.REVISE];
    static final String GTX_FL = topic.types[topic.FL];
    static final String GTX_FL_AT_XML = "FL";
    static final String GTX_PHONICS_SINGLE_AT_XML = "PhonicSounds";
    static final String GTX_PHONICS_WORDS_AT_XML = "PhonicWords";
    static final String GTX_SELECTEXTENDEDNO = topic.types[topic.SELITEMS];
    static final String GTX_SELECTEXTENDEDNO_AT_XML = "ExtendedSelectNo";
    static final String GTX_SELECTEXTENDEDNOGROUP = topic.types[topic.SELGROUPS];

    static final String GTX_HEADING = topic.types[topic.HEADING];
    static final String GTX_HEADING_AT_XML = "HeadingText";
    static final String GTX_HEADING_AT_SOUND_UUID = "HeadingSoundUUID";
    static final String GTX_HEADING_XML = "Heading";

    static final String GTX_IS_WORD_TARGET_AT_XML = "IsWordTarget";

    static final String GTX_GAME_OPTION = topic.types[topic.GAMEOPTIONS];
    static final String GTX_GAME_OPTION_XML = "GameOptions";
    static final String GTX_GAME_OPTION_OPTION_XML = "Option";
    static final String GTX_GAME_OPTION_OPTIONNAME_AT_XML = "OptionName";
    static final String GTX_GAME_OPTION_OPTIONVALUE_AT_XML = "OptionValue";

    static final String GTX_PHONIC_DISRACTORS = topic.types[topic.PHONICDISTRACT];
    static final String GTX_PHONIC_DISRACTORS_SOUND = "PhonicDistractorSounds";
    static final String GTX_PHONIC_DISRACTORS_SOUNDGROUP = "SoundGroup";
    static final String GTX_PHONIC_DISRACTORS_SOUNDNAME_AT_XML = "SoundName";

    static final String GTX_FILTERS = "Filters";
    static final String GTX_FILTER = "Filter";
    static final String GTX_FILTER_INCLUDE_AT_XML = "FilterInclude";
    static final String GTX_FILTER_TYPE_AT_XML = "FilterType";

    static final String GTX_SELECTIONS_TYPE_AT_XML = "SelectionType";
    static final String SELECTION_TYPE_HIGHLIGHT = "SELECTIONTYPEHIGHLIGHT";
    static final String SELECTION_TYPE_DICTATION = "SELECTIONTYPEDICTATION";

    static final int NO_FLAG = -2;

    static final String PROGRAM_NAME_TYPE = "PROGRAMNAME";
    static final String TOPIC_COURSE_NAME_TYPE = "TOPICCOURSENAME";
    static final String TOPIC_HEADING_NAME_TYPE = "TOPICHEADINGNAME";
    static final String GAME_DESCRIPTION_TYPE = "GAMEDESCRIPTION";
    static final String GAME_NAME_TYPE = "GAMENAME";
    static final String TOPICMAINCATEGORY = "TOPICMAINCATEGORY";
    static final String GAMEHEADINGFLAG = "GAMEHEADING";
    static final String SENTENCE_TEXT = "SENTENCETEXT";

    static final String TOPICFLAGCONDITION_REQUIRE = "CONDITIONREQUIRE";
    static final String TOPICFLAGCONDITION_OK = "CONDITIONOK";
    static final String TOPICFLAGCONDITION_NO = "CONDITIONNO";
    static final String TOPICFLAGCONDITION_USES = "CONDITIONUSES";
    static final String TOPICFLAG = "TOPICFLAG";

    static final String GAMETYPESPELLING = "GAMEEDUCATIONALTYPESPELLING";
    static final String GAMETYPERECOGNITION = "GAMEEDUCATIONALTYPERECOGNITION";
    static final String GAMETYPEALPHABET = "GAMEEDUCATIONALTYPEALPHABET";
    static final String LOCALE = "en-GB";

    static final String TOPICNAME_TYPE = "TOPICNAME";
    static final String TOPICTEACHINGNOTE_TYPE = "TOPICTEACHINGNOTE";
    static final String RECORDINGSTANDARD_TYPE = "RECORDINGSTANDARD";
    static final String WORDSPLITPHONIC_TYPE = "WORDSPLITPHONIC";
    static final String WORDSPLITPHONICSYLL_TYPE = "WORDSPLITPHONICSYLL";
    static final String WORDSPLITSYLL_TYPE = "WORDSPLITSYLL";
    static final String WORDSPLITPHONIC_TYPE_VALUE = "0";
    static final String WORDSPLITPHONICSYLL_TYPE_VALUE = "1";
    static final String WORDSPLITSYLL_TYPE_VALUE = "2";
    static final String WORDPHONICSOUND_TYPE = "WORDPHONICSOUND";
    static final String WORDPHONICSYLLSOUND_TYPE = "WORDPHONICSYLLSOUND";
    static final String TOPICBLOCKSTANDARD_TYPE = "TOPICBLOCKSTANDARD";
    static final String TOPICBLOCKEXTENDED_TYPE = "TOPICBLOCKEXTENDED";
    static final String TOPICBLOCKNOTGAMES_TYPE = "TOPICBLOCKNOTGAMES";
    static final String TOPICBLOCKPAIRS_TYPE = "TOPICBLOCKPAIRS";
    static final String TOPICBLOCKPAIRS_SUB_TYPE = "TOPICBLOCKPAIRSSUB";
    static final String TOPICBLOCKDISTRACTORNONSENSERIME_TYPE = "TOPICBLOCKDISTRACTORNONSENSERIME";
    static final String TOPICBLOCKDISTRACTORPHONICSOUNDS_TYPE = "TOPICBLOCKDISTRACTORPHONICSOUNDS";

    static final String TOPICBLOCKGAME_TYPE = "TOPICBLOCKGAME";
    static final String TOPICBLOCKGAMESENTENCE_TYPE = "TOPICBLOCKGAMESENTENCE";
    static final String TOPICBLOCKGAMESUFFIX_TYPE = "TOPICBLOCKGAMESUFFIX";
    static final String TOPICBLOCKRECOMMENDEDGAMES_TYPE = "TOPICBLOCKRECOMMENDEDGAMES";
    static final String TOPICBLOCKRECOMMENDED2GAMES_TYPE = "TOPICBLOCKRECOMMENDED2GAMES";
    static final String TOPICBLOCKITEMWORD_TYPE = "TOPICBLOCKITEMWORD";
    static final String WORDWORD_TYPE = "WORDWORD";
    static final String WORDSOUND_TYPE = "WORDSOUND";
    static final String WORDCAPTION_TYPE = "WORDCAPTION";

    static final String GAMENAME_TYPE = "GAMENAME";
    static final String WORDCOMPONENTROOT_TYPE = "WORDCOMPONENTROOT";
    static final String WORDCOMPONENTSUFFIX_TYPE = "WORDCOMPONENTSUFFIX";

    static final int GAME_BLOCK_TYPE_SENTENCE = 0;
    static final int GAME_BLOCK_TYPE_SUFFIX = 1;

    static final String[] LEGACYSOUNDDB = new String[]{
        "RECORDING_PUBLICSAY1_TYPE",
        "RECORDING_PUBLICSAY3_TYPE",
        "RECORDING_PUBLICSSENT1_TYPE",
        "RECORDING_PUBLICSENT2_TYPE",
        "RECORDING_PUBLICSENT3_TYPE"
    };
    static final int LEGACYSOUNDDB_PUBLICSAY1_TYPE = 0;
    static final int LEGACYSOUNDDB_PUBLICSAY3_TYPE = 1;
    static final int LEGACYSOUNDDB_PUBLICSENT1_TYPE = 2;
    static final int LEGACYSOUNDDB_PUBLICSENT2_TYPE = 3;
    static final int LEGACYSOUNDDB_PUBLICSENT3_TYPE = 4;

    static final int IMAGE_SVG_TYPE = 0;
    static final int IMAGE_BMP_TYPE = 1;
    static final String[] IMAGE_TYPES = new String[]{
        "IMAGE_SVG_TYPE",
        "IMAGE_BMP_TYPE"};

    static final String TEXTSELECTHEADINGTYPE_TYPE = "TEXTSELECTHEADINGTYPE";
    static final String REFWORDTYPE = "REFWORD";
    static final String REFTOPICYPE = "REFTOPIC";

    static final String SENTTARGETTYPE = "SENTTARGETTYPE";
    static final String SENTDISTRACTORTYPE = "SENTDISTRACTORTYPE";

    static final String SENTENCESIMPLE_TYPE = "SENTENCESIMPLE";
    static final String SENTENCECLOZE_TYPE = "SENTENCECLOZE";
    static final String SENTENCEGARBAGE_TYPE = "SENTENCEGARBAGE";
    static final String SENTENCEDICTATION_TYPE = "SENTENCEDICTATION";
    static final String SENTENCEJUMBLED_TYPE = "SENTENCEJUMBLED";
    static final String SENTENCECLOZEWITHDISTRACTORS_TYPE = "SENTENCECLOZEWITHDISTRACTORS";

    static final String GTX_SENTENCES_XML = "Sentences";
    static final String GTX_SENTENCE_XML = "Sentence";
    static final String GTX_SENTENCE_TEXT_AT_XML = "SentenceText";
    static final String GTX_SENTENCE_PLAIN_TEXT_AT_XML = "SentencePlainText";
    static final String GTX_SENTENCE_IMAGE_AT_XML = "SentenceImage";
    static final String GTX_SENTENCE_TYPE_AT_XML = "SentenceType";
    static final String GTX_SENTENCE_IMNAME_AT_XML = "ImageName";
    static final String GTX_SENTENCE_RECNAME_AT_XML = "RecName";
    static final String GTX_SENTENCE_RECNAMENOPEEP_AT_XML = "RecNameNoBeep";
    static final String SENTENCETARGETCHARACTER = "*";
    static final String GTX_TARGETS_XML = "Targets";
    static final String GTX_TARGET_XML = "Target";

    static final String GTX_COURSE_FILTER_AT_XML = "CourseFilter";

    static final String GTX_BRACKETED_WORD_AT_XML = "BracketedWord";

    static final String TOPICFLAGPHONICS = "TOPICFLAGPHONICS";
    static final String TOPICFLAGPHONICSW = "TOPICFLAGPHONICSW";
    static final String TOPICFLAGPHONICSOUND = "TOPICFLAGPHONICSOUND";
    static final String TOPICFLAGSYLLSPLIT = "TOPICFLAGSYLLSPLIT";
    static final String TOPICFLAGANYSPLIT = "TOPICFLAGANYSPLIT";
    static final String TOPICFLAGONSET = "TOPICFLAGONSET";
    static final String TOPICFLAGBAD = "TOPICFLAGBAD";
    static final String TOPICFLAGPATTERN = "TOPICFLAGPATTERN";
    static final String TOPICFLAGSPECIAL = "TOPICFLAGSPECIAL";
    static final String TOPICFLAGPAIRED = "TOPICFLAGPAIRED";

    static final String TOPICFLAGONLYPAIRED = "TOPICFLAGONLYPAIRED";
    static final String TOPICFLAGONLYALLORNONE = "TOPICFLAGONLYALLORNONE";

    static final String TOPICFLAGSINGLETTERS = "TOPICFLAGSINGLETTERS";
    static final String TOPICFLAGMULTISYLLABLE = "TOPICFLAGMULTISYLLABLE";
    static final String TOPICFLAGTRANSLATIONS = "TOPICFLAGTRANSLATIONS";
    static final String TOPICFLAGDESCRIPTION = "TOPICFLAGDESCRIPTION";
    static final String TOPICFLAGFL = "TOPICFLAGFL";
    static final String TOPICFLAGDUPLICATES = "TOPICFLAGDUPLICATES";
    static final String TOPICFLAGBLENDED = "TOPICFLAGBLENDED";
    static final String TOPICFLAGPICTURES = "TOPICFLAGPICTURES";
    static final String TOPICFLAGSENTENCES1 = "TOPICFLAGSENTENCES1";
    static final String TOPICFLAGSENTENCES3 = "TOPICFLAGSENTENCES";
    static final String TOPICFLAGCHUNKS = "TOPICFLAGCHUNKS";
    static final String TOPICFLAGAVERAGEWORDLENGTH4 = "TOPICFLAGAVERAGEWORDLENGTH4";
    static final String TOPICFLAGSHAPES = "TOPICFLAGSHAPES";
    static final String TOPICFLAGOWLRECORDINGS = "TOPICFLAGOWLRECORDINGS";
    static final String TOPICFLAGEXTRARECORDINGS = "TOPICFLAGEXTRARECORDINGS";

    static final String TOPICFLAGPHONICDISTRACTORS = "TOPICFLAGPHONICDISTRACTORS";
    
    
    static final String UPLOAD_TYPE_WORDLIST = "wordlist";    
    static final String UPLOAD_TYPE_STANDARD = "standard";
    static final String UPLOAD_TYPE_EXTENDED = "extended";
    static final String UPLOAD_TYPE_HELICTOPER_LISTEN = "helicopterListen";
    static final String UPLOAD_TYPE_HELICTOPER_SPELL = "helicopterSpell";
    static final String UPLOAD_TYPE_PAIRS = "pairs";
    static final String UPLOAD_TYPE_PATTERN = "pattern";
    static final String UPLOAD_TYPE_PHONIC_DISTRACTORS = "phonicDistractors";
    static final String UPLOAD_TYPE_SENTENCES = "sentences";
    static final String UPLOAD_TYPE_SENTENCES_SIMPLE = "sentencesSimple";   
    

    static String[] saveImageHashes = new String[]{};
    static String[] uploadTopicNames = new String[]{};
    static int[] uploadTopicUUID = new int[]{};
    static String[] uploadWordXml = new String[]{};


    static String[] uploadRecordingDetails = new String[]{};
    static int[] uploadRecordingUUID = new int[]{};

    static String[] uploadWordXMLRecord = new String[]{};
    static int[] uploadWordUUID = new int[]{};
    static boolean uploadStageUploadTopics = false;
    static boolean uploadStageUploadTopicToHeading = false;
    static boolean uploadStageUploadWords = false;
    static boolean uploadStageUploadRest = false;

    static int[] topicIdsDoneForRest = new int[]{};

    static word[] wordsForSounds = null;
    static String[] separateSounds = null;
    static topic t;
    static jnode tjn;
    static topicTree topicTL;
    static String course;
    static Element lastMainGameSelects = null;
    static String htext = null;
    static String lastheading = null;
    static int selectLevelAdjuster = 0;
    static int lastActualSelectLevel = 0;
    static int lastPropLevel = 0;
    static int currentselectLevelAdjuster = 0;

    static String[] currgames = new String[]{};

    static ArrayList standardListAllOrNones = new ArrayList();

    public ToolsOnlineResources tor = new ToolsOnlineResources();
    String gamesMainCategories[] = null;

    public static String currImageDb = null;
    public JSONArray jsonRecResults = null;
    JSONArray jsonImageResults = null;
    topicTree ttGames = null;

    static int currCat = -1;
    static int currGameIds[] = null;
    static String[] allGameNames = new String[]{};
    static String gameCategory[] = new String[]{};
    static int[] allGameIDs = new int[]{};
    int gameReferenceNodeCount = 0;
    final String STR_NO = "No";
    word[] currStandardWords;
    int longestWord;
    boolean phonicshomo_on = false;
    String toBeRecordeds[] = new String[]{};
    String[] helicopterNoColumnFilters = null;
    String disabledGameFilePath = sharkStartFrame.publicPathplus + "json" + shark.sep + "DisabledGames.json";
    int disabledGames[] = new int[]{};

    topicTree mgcoursetree = new topicTree();
    ArrayList mgcoursetreeCodes;
    ArrayList mgcoursetreeSeqs;

    public MYSQLUpload() {

        try {
            JSONParser parser = new JSONParser();
            JSONArray jsonarr = (JSONArray) parser.parse(new FileReader(disabledGameFilePath));
            for (int i = 0; i < jsonarr.size(); i++) {
                JSONObject jo = (JSONObject) jsonarr.get(i);
                disabledGames = u.addint(disabledGames, ((Long) jo.get("id")).intValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        JSONParser parser = new JSONParser();
        try {
            JSONObject json = (JSONObject) parser.parse(new FileReader(sharkStartFrame.publicPathplus + "json\\recordings.json"));
            jsonRecResults = (JSONArray) json.get(ToolsOnlineResources.elements);
            json = (JSONObject) parser.parse(new FileReader(sharkStartFrame.publicPathplus + "json\\images.json"));
            jsonImageResults = (JSONArray) json.get(ToolsOnlineResources.elements);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ttGames = new topicTree();
        ttGames.publicname = "publicgames";
        ttGames.setup(sharkStartFrame.updateList(sharkStartFrame.currStudent, db.GAME),
                true, db.GAME, false, "Topic lists for update");
        jnode jns[] = ((jnode) ((jnode) ((jnode) ttGames.root.getChildAt(1)).getChildAt(1))).getChildren();
        for (int i = 0; i < 4; i++) {
            gamesMainCategories = u.addString(gamesMainCategories, jns[i].get());
        }
        


        jnode node;

        allGameNames = new String[]{};
        String staticGameSubNames[] = new String[]{};
        String staticGameWholeNames[] = new String[]{};
        int staticGameIDs[] = new int[]{};
        String cvsSplitBy = ",";
        JSONArray jsonarr = null;
        try {
            JSONParser parserGames = new JSONParser();
            jsonarr = (JSONArray) parserGames.parse(new FileReader(sharkStartFrame.publicPathplus + "json" + shark.sep + "GamesAndIDs.json"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < jsonarr.size(); i++) {
            JSONObject jo = (JSONObject) jsonarr.get(i);
            staticGameIDs = u.addint(staticGameIDs, ((Long) jo.get("id")).intValue());
            String gname = (String) jo.get("name");
            String gnamess[] = gname.split(cvsSplitBy);
            String name = gnamess[0].trim();
            String subname = gnamess.length > 1 ? gnamess[1].trim() : "";

            staticGameSubNames = u.addString(staticGameSubNames, subname);
            
            String wholeName = (name + " " + subname).trim();
            staticGameWholeNames = u.addString(staticGameWholeNames, wholeName);
            
            allGameNames = u.addString(allGameNames, wholeName);
            allGameIDs = u.addint(allGameIDs, Integer.parseInt(String.valueOf(jo.get("id"))));
        }


        String problemGames[] = new String[]{};
        for (int i = 0; i < ALL_GAMES.length; i++) {
            if (u.findString(staticGameWholeNames, ALL_GAMES[i]) < 0) {
                problemGames = u.addString(problemGames, ALL_GAMES[i]);
            }
        }
        if (problemGames.length > 0) {
            u.okmess(shark.programName + " Misnamed games", u.combineString(problemGames), sharkStartFrame.mainFrame);
            return;
        }        
        
        
        gamestoplay gameTreeWholeWords = new gamestoplay();     
        gameTreeWholeWords.setup(sharkStartFrame.publicGameLib, true, true, "porting", new int[] {0});
        
        for (node = (jnode) gameTreeWholeWords.root.getFirstLeaf(); node != null; node = (jnode) node.getNextLeaf()) {
            int i = u.findString(allGameNames, node.get());
            if (i >= 0) {
                wholeWordGameIdOrder = u.addint(wholeWordGameIdOrder, getGameID(node.get()), wholeWordGameIdOrder.length);
            }
        }    
        
        
        gamestoplay gameTreeSingleSounds = new gamestoplay();     
        gameTreeSingleSounds.setup(sharkStartFrame.publicGameLib, true, true, "porting", new int[] {1});
        
        for (node = (jnode) gameTreeSingleSounds.root.getFirstLeaf(); node != null; node = (jnode) node.getNextLeaf()) {
            int i = u.findString(sharkStartFrame.gamename, node.get());
            if (i >= 0) {
                singleSoundGameIdOrder = u.addint(singleSoundGameIdOrder, getGameID(node.get()), singleSoundGameIdOrder.length);
            }
        } 
        int he;
        he = 0;
    }


    
     String apiGetAccessToken(int env) {
        apiConfig config = API_CONFIGS[env];
        String ret = null;
        try {
            java.net.URL url = new java.net.URL(config.url + "oauth/token");
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);
            String jsonInputString = "{\"grant_type\": \""+
                    config.accessGrantType + "\", " +
                    "\"client_id\": \"" + config.accessClientId + "\", " +
                    "\"client_secret\": \"" + config.accessSecret+"\", " +
                    "\"scope\": \"" + config.accessScope + "\", " +
                    "\"username\": \"" + config.accessUserName + "\", " +
                    "\"password\": \"" + config.accessPassword + "\"}";
            try(OutputStream os = con.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);			
            }
            try(BufferedReader br = new BufferedReader(
              new InputStreamReader(con.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                JSONParser parser = new JSONParser();
                ret = (String)((JSONObject)parser.parse(response.toString())).get("access_token");
            }
            
        } catch (Exception rr) {
            int dd;
            dd = 0;
        }
        return ret;
    }
       
     JSONObject apiRequest(String urlPath, String jsonInputString, int env) {
        if(API_CONFIGS[env].accessToken == null){
            setAccessToken(env);
        } 
        apiConfig config = API_CONFIGS[env];
        JSONObject ret = null;
        long startTimeMillis = System.currentTimeMillis();
        try {
            java.net.URL url = new java.net.URL(urlPath);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestProperty("Authorization", "Bearer " + config.accessToken);
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            if(jsonInputString != null){
                try(OutputStream os = con.getOutputStream()) {
                    byte[] input = jsonInputString.getBytes("utf-8");
                    os.write(input, 0, input.length);			
                }              
            }   
            try(BufferedReader br = new BufferedReader(
              new InputStreamReader(con.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                
                long endTimeMillis = System.currentTimeMillis();
                System.out.println("Request duration: " + TimeUnit.MILLISECONDS.toSeconds(endTimeMillis - startTimeMillis));
                
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                JSONParser parser = new JSONParser();
                return (JSONObject)parser.parse(response.toString());
            }
            
        } catch (Exception rr) {
           System.out.println("Error:  " + rr.getMessage());
        }
        
        u.okmess(shark.programName, "Failed");
        System.exit(0);
        return null;
    }
    
    boolean apiHead(String urlPath, int env) {
        if(API_CONFIGS[env].accessToken == null){
            setAccessToken(env);
        } 
        apiConfig config = API_CONFIGS[env];
        try {
            java.net.URL url = new java.net.URL(urlPath);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestProperty("Authorization", "Bearer " + config.accessToken);
            con.setRequestMethod("HEAD");
            con.setDoOutput(true);

            // Check response code FIRST
            int responseCode = con.getResponseCode();
            if(responseCode == 200){
                return true;
            }
            return false;

        } catch (Exception rr) {
            u.okmess(shark.programName, "Failed: " + rr.getMessage());
        }
        u.okmess(shark.programName, "Failed");
        System.exit(0);
        return false;
    }  
     
     
    void setAccessToken(int env){
        String accessToken = apiGetAccessToken(env);
        if(accessToken==null){
            int h;
            h = 0; 
        }
        API_CONFIGS[env].accessToken = accessToken;
    }

    public JSONObject UploadTopicHeading(String HeadingDisplay, String HeadingIndex, String OwningCourse,
            String TopicHeadingNameType,String UnitType, String Description) {

        // get rid of the numbering
        int k = HeadingDisplay.indexOf(')');
        if (k > 0) {
            try {
                Integer.parseInt(HeadingDisplay.substring(0, k).trim());
                HeadingDisplay = HeadingDisplay.substring(k + 1).trim();
            } catch (Exception e) {
                e = e;
            }
        }

        if (u.findString(NON_UNIT_NUMBERED_COURSES, OwningCourse) < 0) {
            HeadingDisplay = String.valueOf(Integer.parseInt(HeadingIndex) + 1) + ") " + HeadingDisplay;
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", HeadingDisplay);
        jsonObject.put("unit_order", HeadingIndex);
        jsonObject.put("version_id", currCourseVersion);
        jsonObject.put("description", Description);
        if(UnitType != null){
            jsonObject.put("type", UnitType);
        }
        
        return jsonObject;
    }
      
    private int getSharkChallengePlacementUnitIndex(int unitIndex, int wordlistIndex){
        if(unitIndex == 0){
            return -1;
        }
        boolean isInFirstMainTest = 1 == unitIndex;
        switch(wordlistIndex) {
            case 0:
               return isInFirstMainTest ? 0 : 24;
            case 1: 
                return isInFirstMainTest ? 4 : 26;
            case 2: 
                return isInFirstMainTest ? 7 : 27;
            case 3: 
                return isInFirstMainTest ? 9 : 28;
            case 4: 
                return isInFirstMainTest ? 10 : 29;         
            case 5: 
                return isInFirstMainTest ? 11 : 30;         
            case 6: 
                return isInFirstMainTest ? 12 : 32;       
            case 7: 
                return isInFirstMainTest ? 15 : 33;   
            case 8: 
                return isInFirstMainTest ? 16 : 36; 
            case 9: 
                return isInFirstMainTest ? 18 : 37;
            case 10: 
                return isInFirstMainTest ? 19 : 38; 
            case 11: 
                return isInFirstMainTest ? 22 : 39;
            case 12: 
                return 40;            
            case 13: 
                return 41;              
            default:
              return -1;
        }
    }
    
    private int getSharkChallengeCompletedUnitIndex(int unitIndex, int wordlistIndex){
        if(unitIndex == 0){
            return -1;
        }
        return 2 == unitIndex && wordlistIndex == 13 ? 42 : -1;
    }

    private String mergedGameIds(String ss[]) {
        int ii[] = new int[]{};
        for (int i = 0; i < ss.length; i++) {
            String sss[] = u.splitString(ss[i], ",");
            for (int j = 0; j < sss.length; j++) {
                int it = Integer.parseInt(sss[j]);
                if (!u.inlist(ii, it)) {
                    ii = u.addint(ii, it);
                }
            }
        }
        String ret = "";
        for (int i = 0; i < ii.length; i++) {
            String s = String.valueOf(ii[i]);
            if (i > 0) {
                ret += ",";
            }
            ret += s;
        }
        return ret;
    }
    
    private String mergedGameIds2(String s1, String s2, boolean isSingleSound) {
        String wordlistGameIdsCsv = mergedGameIds(new String[]{s1, s2});

        
        int[] orderedReferenceArray = isSingleSound ? singleSoundGameIdOrder : wholeWordGameIdOrder;        
        int[] orderedArray = new int[orderedReferenceArray.length];
        
        String[] wordlistStringGameIds = u.splitString(wordlistGameIdsCsv, ",");
        
        for (int i = 0; i < wordlistStringGameIds.length; i++) {
            int gameId = Integer.valueOf(wordlistStringGameIds[i]);
            int index = u.findIndexOfIntInArray(orderedReferenceArray, gameId);
            if(index < 0){
                System.out.println("INCOMPATIBLE GAME ID" + t.name + "   " + String.valueOf(isSingleSound)  + " " + String.valueOf(gameId));
                continue;
            }
            orderedArray[index] = gameId;
        }
        
        String[] resultInts = new String[0];

        for (int i = 0; i < orderedArray.length; i++) {
            if(orderedArray[i] > 0){
                resultInts = u.addString(resultInts, String.valueOf(orderedArray[i]));
            }
        }

        return u.combineString(resultInts, ",");
    }
    
    

    public static void gatherGames(gamestoplay gametree) {
        jnode root = gametree.root;
        jnode tab[] = root.getChildren();
        for (int i = 0; i < tab.length; i++) {
            if (tab[i].isLeaf()) {
                int g;
                g = 0;
            }
            jnode games[] = tab[i].getChildren();
            for (int k = 0; k < games.length; k++) {
                if (!games[k].isLeaf()) {
                    int g;
                    g = 0;
                }
                int gid = getGameID(games[k].get());
                if (gid < 0) {
                    int g;
                    g = 0;
                }

                if (currGameIds == null || !u.inlist(currGameIds, gid)) {
                    currGameIds = u.addint(currGameIds, gid);
                }
            }
        }
    }

    ArrayList gameOptionsStaticLookup(String game) {
        String names[] = new String[]{};
        Object values[] = new Object[]{};
        int defaultSpeed = 5;
        switch (game) {
            case GAME_SAYWORD:
                names = u.addString(names, "recOn");
                values = u2_base.addObject(values, true);
                break;
            case GAME_SAYWORDFORPICTURE:
                names = u.addString(names, "recOn");
                values = u2_base.addObject(values, true);
                break;
            case GAME_SAYSENTENCE:
                names = u.addString(names, "recOn");
                values = u2_base.addObject(values, true);
                break;
            case GAME_SAYSOUNDSPHONICS:
                names = u.addString(names, "recOn");
                values = u2_base.addObject(values, true);
                break;
            case GAME_HELICOPTERSPELL:
                names = u.addString(names, "speed");
                values = u2_base.addObject(values, defaultSpeed);
                break;
            case GAME_HELICOPTERLISTEN:
                names = u.addString(names, "speed");
                values = u2_base.addObject(values, defaultSpeed);
                break;
            case GAME_HUNT:
                names = u.addString(names, "speed");
                values = u2_base.addObject(values, defaultSpeed);
                break;
            case GAME_HUNTPHONICS:
                names = u.addString(names, "speed");
                values = u2_base.addObject(values, defaultSpeed);
                break;
            case GAME_HUNTSYLLABLES:
                names = u.addString(names, "speed");
                values = u2_base.addObject(values, defaultSpeed);
                break;
            case GAME_PATTERN:
                break;
            case GAME_TRACKING:
                break;
            case GAME_WORDSEARCH:
                break;
            case GAME_SPELLTEST:
                break;
            case GAME_SALVAGE:
                names = u.addString(names, "speed");
                values = u2_base.addObject(values, defaultSpeed);
                break;
            case GAME_MAZESPELLTEST:
                names = u.addString(names, "speed");
                values = u2_base.addObject(values, defaultSpeed);
                break;
            case GAME_SHARKS:
                names = u.addString(names, "speed");
                values = u2_base.addObject(values, defaultSpeed);
                break;
            case GAME_SHARKSALTER:
                names = u.addString(names, "speed");
                values = u2_base.addObject(values, defaultSpeed);
                break;
            case GAME_SPLITSOUND:
                break;
            case GAME_FINDWORDPHONICS:
                names = u.addString(names, "speed");
                values = u2_base.addObject(values, defaultSpeed);
                break;
            case GAME_FINDPICTUREPHONICS:
                names = u.addString(names, "speed");
                values = u2_base.addObject(values, defaultSpeed);
                break;
            case GAME_FINDPICTUREVOCABULARY:
                break;
            case GAME_FINDPICTUREFROMWRITTEN:
                break;
            case GAME_FINDPICTUREFORSENTENCE:
                break;
            case GAME_FINDWORD:
                names = u.addString(names, "speed");
                values = u2_base.addObject(values, defaultSpeed);
                break;
            case GAME_JIGSAWONSETANDRIME:
                break;
            case GAME_JIGSAWPHONICS:
                break;
            case GAME_JIGSAWSYLLABLES:
                break;
            case GAME_READINGTEST:
                break;
            case GAME_JUMBLED:
                break;
        }
        if (names.length == 0) {
            return null;
        }
        ArrayList retArr = new ArrayList();
        retArr.add(names);
        retArr.add(values);
        return retArr;
    }

    ArrayList gameOptionsLookup(String game, String optionString) {
        optionString = optionString.replace("==", "=");
        optionString = optionString.replace("^", "");
        String ss[] = u.splitStringi(optionString, '=');
        String res_name = null;
        Object res_value = null;
        if (!Character.isUpperCase(game.charAt(0))) {
            game = String.valueOf(game.charAt(0)).toUpperCase() + game.substring(1);
        }
        switch (game) {
            case GAME_CATCHING:
                switch (ss[0]) {
                    case "catchingsort-alpha":
                        int opt = Integer.parseInt(ss[1].trim());
                        if (opt == 1) {
                            res_name = "content_1";
                            res_value = true;
                        } else if (opt == 2) {
                            res_name = "content_2";
                            res_value = true;
                        } else if (opt == 3) {
                            res_name = "content_3";
                            res_value = true;
                        } else if (opt == 4) {
                            res_name = "content_4";
                            res_value = true;
                        }
                        break;
                    case "sort-capitals":
                        res_name = "sort_capitals";
                        res_value = ss[1].trim().equalsIgnoreCase("y");
                        break;
                    case "sort-ag":
                        res_name = "sort_ag";
                        res_value = ss[1].trim().equalsIgnoreCase("y");
                        break;
                    case "sort-hn":
                        res_name = "sort_hn";
                        res_value = ss[1].trim().equalsIgnoreCase("y");
                        break;
                    case "sort-ot":
                        res_name = "sort_ot";
                        res_value = ss[1].trim().equalsIgnoreCase("y");
                        break;
                    case "sort-uz":
                        res_name = "sort_uz";
                        res_value = ss[1].trim().equalsIgnoreCase("y");
                        break;
                }
                break;
            case GAME_FINDPICTUREFORSENTENCE:
                break;
            case GAME_FINDPICTUREFROMWRITTEN:
                break;
            case GAME_FINDPICTUREPHONICS:
                break;
            case GAME_FINDPICTUREVOCABULARY:
                break;
            case GAME_FINDWORD:
                switch (ss[0]) {
                    case "fastfind":
                        res_name = "fast_find";
                        res_value = ss[1].trim().equalsIgnoreCase("y");
                        break;
                }
                break;
            case GAME_FINDWORDPHONICS:
                switch (ss[0]) {
                    case "fastfind":
                        res_name = "fast_find";
                        res_value = ss[1].trim().equalsIgnoreCase("y");
                        break;
                }
                break;
            case GAME_HELICOPTERLISTEN:
                break;
            case GAME_HELICOPTERSPELL:
                break;
            case GAME_HUNT:
                break;
            case GAME_HUNTPHONICS:
                break;
            case GAME_HUNTSYLLABLES:
                break;
            case GAME_JIGSAWONSETANDRIME:
                break;
            case GAME_JIGSAWPHONICS:
                break;
            case GAME_JIGSAWSYLLABLES:
                break;
            case GAME_MAZESPELLTEST:
                switch (ss[0]) {
                    case "maze-size":
                        res_name = "maze_size";
                        res_value = Math.round(Integer.parseInt(ss[1].trim()) / 10.0) * 10;  // WOL maze requires maze size to be a multiple of 10
                        break;
                    case "maze_tiles":
                        res_name = "use_tiles";
                        res_value = ss[1].trim().equalsIgnoreCase("y");
                        break;
                }
                break;
            case GAME_MOVINGSPELLCHECK:
                switch (ss[0]) {
                    case "moving_tiles":
                        res_name = "use_tiles";
                        res_value = ss[1].trim().equalsIgnoreCase("y");
                        break;
                }
                break;
            case GAME_PAIRS:
                switch (ss[0]) {
                    case "pelmanism-image-cardback":
                        res_name = "cardback";
                        res_value = Integer.parseInt(ss[1].trim().substring(ss[1].trim().length() - 1));
                        break;
                }
                break;
            case GAME_PATTERN:
                break;
            case GAME_TRACKING:
                break;
            case GAME_READINGTEST:
                break;
            case GAME_SALVAGE:
                switch (ss[0]) {
                    case "salvage_tiles":
                        res_name = "use_tiles";
                        res_value = ss[1].trim().equalsIgnoreCase("y");
                        break;
                }
                break;
            case GAME_SAYSENTENCE:
                switch (ss[0]) {
                    case "sayword_nostartmess":
                        res_name = "noMessage";
                        res_value = ss[1].trim().equalsIgnoreCase("y");
                        break;
                }
                break;
            case GAME_SAYSOUNDSPHONICS:
                switch (ss[0]) {
                    case "sayword_nostartmess":
                        res_name = "noMessage";
                        res_value = ss[1].trim().equalsIgnoreCase("y");
                        break;
                }
                break;
            case GAME_SAYWORD:
                switch (ss[0]) {
                    case "sayword_nostartmess":
                        res_name = "noMessage";
                        res_value = ss[1].trim().equalsIgnoreCase("y");
                        break;
                }
                break;
            case GAME_SAYWORDFORPICTURE:
                switch (ss[0]) {
                    case "sayword_nostartmess":
                        res_name = "noMessage";
                        res_value = ss[1].trim().equalsIgnoreCase("y");
                        break;
                }
                break;
            case GAME_SHARKS:
                switch (ss[0]) {
                    case "sharks_auto":
                        res_name = "auto_shark";
                        res_value = ss[1].trim().equalsIgnoreCase("y");
                        break;
                    case "sharks_tiles":
                        res_name = "use_tiles";   // not use_tiles
                        res_value = ss[1].trim().equalsIgnoreCase("y");
                        break;
                }
                break;
            case GAME_SHARKSALTER:
                switch (ss[0]) {
                    case "sharks_auto":
                        res_name = "auto_shark";
                        res_value = ss[1].trim().equalsIgnoreCase("y");
                        break;
                    case "sharks_tiles":
                        res_name = "use_tiles";   // not use_tiles
                        res_value = ss[1].trim().equalsIgnoreCase("y");
                        break;
                }
                break;
            case GAME_SNAP:
                switch (ss[0]) {
                    case "pelmanism-image-cardback":
                        res_name = "cardback";
                        res_value = Integer.parseInt(ss[1].trim().substring(ss[1].trim().length() - 1));
                        break;
                }
                break;
            case GAME_SPELLTEST:
                switch (ss[0]) {
                    case "spellingtest_tiles":
                        res_name = "use_tiles";
                        res_value = ss[1].trim().equalsIgnoreCase("y");
                        break;
                }
                break;
            case GAME_SPLITSOUND:
                switch (ss[0]) {
                    case "findsound-hard":
                        res_name = "findsound_hard";
                        res_value = ss[1].trim().equalsIgnoreCase("2");
                        break;
                }
                break;
            case GAME_WORDSEARCH:
                switch (ss[0]) {
                    case "wordsearch-horiz":
                        res_name = "horizontal";
                        res_value = ss[1].trim().equalsIgnoreCase("y");
                        break;
                    case "wordsearch-vert":
                        res_name = "vertical";
                        res_value = ss[1].trim().equalsIgnoreCase("y");
                        break;
                    case "wordsearch-diag":
                        res_name = "diagonal";
                        res_value = ss[1].trim().equalsIgnoreCase("y");
                        break;
                }
                break;
        }
        if (res_name == null) {
            return null;
        }
        ArrayList retAl = new ArrayList();
        retAl.add(res_name);
        retAl.add(res_value);
        return retAl;
    }

    String getRecommededGameIds(String mergedGames[], String mg[], String mgcode) {
        if (mg == null && mgcode == null) {
            return "";
        }
        String resGameIds = "";
        String mgames[] = new String[0];
        if (mg != null && mg.length > 0) {
            for (int i = 0; i < mg.length; i++) {
                String game = String.valueOf(getGameID(mg[i]));
                if (u.findString(mergedGames, game) >= 0) {
                    mgames = u.addString(mgames, game);
                }
            }
        } else if (mgcode != null) {
            for (int i = 0; i < mgcoursetreeCodes.size(); i++) {
                if (mgcode.equalsIgnoreCase((String) ((ArrayList) mgcoursetreeCodes.get(i)).get(0))) {
                    mgames = ((String[]) ((ArrayList) mgcoursetreeCodes.get(i)).get(1));
                    break;
                }
            }
        }
        resGameIds = u.combineString(mgames, ",");
        return resGameIds;
    }
    
    
    void doImagesUpload() {
        currentEnvironment = ShowSelectEnvDialog();
        int insertItemCount = imagesUploadAction(true);
        boolean okToProceed = u.yesnomess(shark.programName, "About to update " + String.valueOf(insertItemCount) + " items. Proceed?" , sharkStartFrame.mainFrame);
        if(okToProceed){
           imagesUploadAction(false); 
        }
        u.okmess(shark.programName, "Finished upload", sharkStartFrame.mainFrame);
    }
        
    int imagesUploadAction(boolean dummyRun) {
        int counter = 0;
        try {
            for (int i = 0; i < jsonImageResults.size(); i++) {
                JSONObject p = (JSONObject) jsonImageResults.get(i);
                String strS3key = (String) p.get(ToolsOnlineResources.s3key);
                String strDesktopName = (String) p.get(ToolsOnlineResources.desktopName);
                String strIsVocab = (String) p.get(ToolsOnlineResources.vocab);
                String currUsed = (String) p.get(ToolsOnlineResources.currentlyUsed);

                String sep = "/";
                if (strS3key == null) {
                    int h;
                    h = 0;
                }
                int j = strS3key.indexOf(sep);
                String strType = strS3key.substring(0, j).toUpperCase();
                String strFileName = strS3key.substring(j + sep.length());

                if (strDesktopName.trim().equals("")) {
                    continue;
                }
                if (strDesktopName.indexOf('_') >= 0) {
                    continue;
                }

                word w = new word(strDesktopName, "publictopics");
                String imword = w.v();
                int kk;
                if ((kk = imword.indexOf("^")) >= 0) {
                    imword = imword.substring(0, kk);
                }


                String s3Key = (String)p.get(ToolsOnlineResources.s3key);
                boolean keyExistsInDatabase = checkS3KeyExistsInDatabase(s3Key, "images");

                if(!keyExistsInDatabase){
                    System.out.println(s3Key + "doesn't exist in DATABASE");
                }
                    
                boolean keyExistsInS3 = new File(ToolsOnlineResources.imagesS3Path+s3Key).exists();    
                if (!keyExistsInDatabase) {
                    System.out.println(s3Key);
                    if(!keyExistsInS3){
                        System.out.println(s3Key + "doesn't exist in S3");
                    }
                    counter++;
                    if(!dummyRun){
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("word", u.formatTextforUpload(getStrippedSoundName(strDesktopName)));
                        jsonObject.put("filename", strFileName);
                        jsonObject.put("s3key", strS3key);
                        jsonObject.put("IsVocab", Boolean.parseBoolean(strIsVocab));

                        apiRequest(API_CONFIGS[currentEnvironment].url + "images", jsonObject.toString(), currentEnvironment); 
                    }
                 }   
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return counter;
    }

    void doRecordingsUpload() {
        currentEnvironment = ShowSelectEnvDialog();
        int insertItemCount = recordingsUploadAction(true);
        boolean okToProceed = u.yesnomess(shark.programName, "About to update " + String.valueOf(insertItemCount) + " items. Proceed?" , sharkStartFrame.mainFrame);
        if(okToProceed){
           recordingsUploadAction(false); 
        }
        u.okmess(shark.programName, "Finished upload", sharkStartFrame.mainFrame);
    }
    
    
    int recordingsUploadAction(boolean dummyRun) {
        int counter = 0;
        try {
            for (int i = 0; i < jsonRecResults.size(); i++) {
                JSONObject p = (JSONObject) jsonRecResults.get(i);
                String currUsed = (String) p.get(ToolsOnlineResources.currentlyUsed);
                if (currUsed != null && currUsed.equals("false")) {
                    continue;
                }

                String sep = "/";
                String strS3key = (String) p.get(ToolsOnlineResources.s3key);
                int j = strS3key.indexOf(sep);
                String strType = strS3key.substring(0, j).toUpperCase();
                if (strType.equals(ToolsOnlineResources.pre_gameMessage)) {
                    continue;
                }
                String strDesktopName = (String) p.get(ToolsOnlineResources.desktopName);

                
                String strIsVocab = (String) p.get(ToolsOnlineResources.vocab);
                String strFileName = strS3key.substring(j + sep.length());
                if (!strFileName.endsWith(".mp3")) {
                    int g;
                    g = 0;
                }

                String s3Key = (String)p.get(ToolsOnlineResources.s3key);
                boolean keyExistsInDatabase = checkS3KeyExistsInDatabase(s3Key, "sounds");
                boolean keyExistsInS3 = new File(ToolsOnlineResources.audioS3Path+s3Key).exists();    
                if (!keyExistsInDatabase) {
                    System.out.println(s3Key);
                    if(!keyExistsInS3){
                        System.out.println(s3Key + "doesn't exist in S3");
                    }
                    counter++;
                    if(!dummyRun){
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("type", strType);
                        jsonObject.put("word", u.formatTextforUpload(getStrippedSoundName(strDesktopName)));
                        jsonObject.put("filename", strFileName);
                        jsonObject.put("s3key", strS3key);
                        jsonObject.put("IsVocab", Boolean.parseBoolean(strIsVocab));

                        apiRequest(API_CONFIGS[currentEnvironment].url + "sounds", jsonObject.toString(), currentEnvironment);                
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return counter;
    }
    
    String urlEncodeS3Key(String key){
        String encoded = null;
        try{
            encoded = URLEncoder.encode(key, "UTF-8");
        } catch(UnsupportedEncodingException e){
            e.printStackTrace();
        }
        return encoded;
    }

    boolean checkS3KeyExistsInDatabase(String key, String resourceRouteString){
        String urlEncodedS3Key = urlEncodeS3Key(key);
        return apiHead(API_CONFIGS[currentEnvironment].url + resourceRouteString + "/check?s3key="+urlEncodedS3Key, currentEnvironment);
    }
    
    void doTopicsPrint(topicTree topicTreeList) {

        mgcoursetree.publicname = sharkStartFrame.mainFrame.publicMarkGamesCoursesLib[0];
        mgcoursetree.setup(new String[]{sharkStartFrame.mainFrame.publicMarkGamesCoursesLib[0]}, true, db.TEXT, true, "Right-click to specify mark games codes");
        jnode jcodes = mgcoursetree.find(topicTree.MARKGAMESCODES);
        jnode jnsc[] = jcodes.getChildren();
        mgcoursetreeCodes = new ArrayList();
        for (int ii = 0; ii < jnsc.length; ii++) {
            String s = jnsc[ii].get();
            if (s.trim().equals("")) {
                continue;
            }
            if (s.indexOf("=") >= 0) {
                String s2 = s.substring(0, s.indexOf("="));
                String s3 = s.substring(s.indexOf("=") + 1);
                String s3ss[] = u.splitString(s3, ",");
                String s3ssres[] = new String[0];
                for (int jj = 0; jj < s3ss.length; jj++) {
                    s3ssres = u.addString(s3ssres, String.valueOf(getGameID(s3ss[jj])));
                }
                ArrayList sub = new ArrayList();
                sub.add(s2);
                sub.add(s3ssres);
                mgcoursetreeCodes.add(sub);
            } else {
                u.okmess(shark.programName, "Issue with Mark Games Code: " + s, sharkStartFrame.mainFrame);
            }
        }

        int n;
        jnode sel[] = topicTreeList.getSelectedNodes();//The games are not playable AND pictures will not show when words are spoken
        if (sel == null || sel.length == 0) //If 1.1
        {
            return;
        }
        doingPort = true;
        
        currentEnvironment = ShowSelectEnvDialog();
       
        currCourseVersion = JOptionPane.showInputDialog("Version no for " + ENV_NAMES[currentEnvironment] + ":").trim();
        
        startTime = Calendar.getInstance().getTimeInMillis();

        jnode jncourses[] = ((jnode) topicTreeList.root.getChildAt(1)).getChildren();

        String sscourses[] = new String[]{};

        for (int ic = 0; ic < jncourses.length; ic++) {
            String nodename = jncourses[ic].get().trim();
            if (!nodename.equals("") && !nodename.startsWith("<")) {
                sscourses = u.addString(sscourses, nodename);
            }
        }

        String ssproblemcourses[] = new String[]{};
        for (int ic = 0; ic < ALL_COURSES.length; ic++) {
            if (u.findString(sscourses, ALL_COURSES[ic]) < 0) {
                ssproblemcourses = u.addString(ssproblemcourses, ALL_COURSES[ic]);
            }
        }
        if (ssproblemcourses.length > 0) {
            u.okmess(shark.programName + " Misnamed courses", u.combineString(ssproblemcourses), sharkStartFrame.mainFrame);
            return;
        }

        String courseids[] = new String[]{};
        String coursenames[] = new String[]{};

        try {
            JSONParser parser = new JSONParser();
            JSONArray jsonarr = (JSONArray) parser.parse(new FileReader(sharkStartFrame.publicPathplus + "json" + shark.sep + "CoursesAndIDs.json"));
            for (int ix = 0; ix < jsonarr.size(); ix++) {
                JSONObject jo = (JSONObject) jsonarr.get(ix);
                courseids = u.addString(courseids, String.valueOf(((Long) jo.get("id")).intValue()));
                coursenames = u.addString(coursenames, (String) jo.get("name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        mainloop:
        for (n = 0; n < sel.length; ++n) {          
            MYSQLUpload.uploadStageUploadTopicToHeading = true;//ii == 0;
            MYSQLUpload.uploadStageUploadWords = true;//ii == 1;
            MYSQLUpload.uploadStageUploadRest = true;//ii == 2;
            MYSQLUpload.course = sel[n].get();
            currentCourse = sel[n].get();
            MYSQLGameFiltering = true;
            JSONObject unitsResponse = topicScanForUnits(topicTreeList, sel[n], n, courseids, coursenames);
            topicScanForWordlists(Integer.parseInt(currCourseVersion), (JSONArray)unitsResponse.get("unit_ids"), topicTreeList, sel[n], n, courseids, coursenames);
        }

        doingPort = false;    
    }
    
    
    int ShowSelectEnvDialog(){
        JCheckBox checkbox_le = new JCheckBox(ENV_NAMES[0]);
        JCheckBox checkbox_staging = new JCheckBox(ENV_NAMES[1]);
        JCheckBox checkbox_live = new JCheckBox(ENV_NAMES[2]);
        String message = "Select Environment:";
        Object[] params = {message, checkbox_le, checkbox_staging, checkbox_live};
        JOptionPane.showConfirmDialog(sharkStartFrame.mainFrame, params, "Select Environment", JOptionPane.YES_NO_OPTION);
        if(checkbox_le.isSelected()){
            return ENV_LOCAL;
        }
        else if(checkbox_staging.isSelected()){
            return ENV_STAGING;
        }
        else if(checkbox_live.isSelected()){
            return ENV_LIVE;
        }
        return -1;
    }

    public void generateOWLAvailableGameSets() {
        int SPLTS_ON = 0;
        int SPLTS_OFF = 1;
        int MULTISYLL_ON = 2;
        int MULTISYLL_OFF = 3;
        int ONSET_ON = 4;
        int ONSET_OFF = 5;
        int DEFINTION_ON = 6;
        int DEFINTION_OFF = 7;
        int PICTURE_ON = 8;
        int PICTURE_OFF = 9;
        int ALL_SINGLE_LETTER_WORDS = 10;

        String gameSets[] = new String[]{"splits", "no_splits", "multisyllable", "no_multisyllable", "onset", "no_onset", "definition", "no_definition", "pictures", "no_pictures", "all_single_lettered_words"};
        MYSQLGameFiltering = true;
        // get available sets of game ids 
        String owlnames[][] = new String[gameSets.length][];
        owlnames[SPLTS_ON] = new String[]{"splits_onset", "splits_multisyllable", "splits_definitions Â· descriptions"};
        owlnames[SPLTS_OFF] = new String[]{"no_split_onset", "no_splits_multisyllable", "no_splits_definitions Â· descriptions"};
        owlnames[MULTISYLL_ON] = new String[]{"multisyllable", "multisyllable_definitions Â· descriptions"};
        owlnames[MULTISYLL_OFF] = new String[]{"no_multisyllable", "no_multisyllable_definitions Â· descriptions"};
        owlnames[ONSET_ON] = new String[]{"onset", "onset_definitions Â· descriptions"};
        owlnames[ONSET_OFF] = new String[]{"no_onset", "no_onset_definitions Â· descriptions"};
        owlnames[DEFINTION_ON] = new String[]{"definition_onset Â· descriptions", "definition_multisyllable Â· descriptions"};
        owlnames[DEFINTION_OFF] = new String[]{"no_definition_onset", "no_definition_multisyllable"};
        owlnames[PICTURE_ON] = new String[]{"pictures_onset", "pictures_multisyllable", "pictures_definitions Â· descriptions"};
        owlnames[PICTURE_OFF] = new String[]{"no_pictures_onset", "no_pictures_multisyllable", "no_pictures_definitions Â· descriptions"};
        owlnames[ALL_SINGLE_LETTER_WORDS] = new String[]{"all_letters_definitions Â· descriptions", "all_letters_no_definitions"};
        String gameSetsResults[] = new String[gameSets.length];

        for (int i = 0; i < owlnames.length; i++) {
            currGameIds = null;
            //gather games
            currCat = CAT_NONPHONICS;
            thedoloop:
            for (int k = 0; k < owlnames[i].length; k++) {
                sharkStartFrame.mainFrame.wordTree.font = null;
                String adminame = "OwnWordListTemplates";
                topic top = new topic(adminame, owlnames[i][k], null, null);
                top.getSplits();
                top.split4 = gotSplits = top.splitwords != null;
                sharkStartFrame.mainFrame.currPlayTopic = top;
                sharkStartFrame.mainFrame.wordTree.setup(top, null);
                String coursename = u.gettext("topics", "adminlists");
                String endpath = topicTree.ISTOPIC + adminame + topicTree.ISPATH + owlnames[i][k];
                sharkStartFrame.mainFrame.studentList[sharkStartFrame.mainFrame.currStudent].currTopic
                        = coursename + topicTree.CSEPARATOR + adminame + topicTree.CSEPARATOR + endpath;
                sharkStartFrame.mainFrame.setTopicList(coursename, endpath);
                sharkStartFrame.mainFrame.setCourseListSelection(coursename);
                sharkStartFrame.mainFrame.setupGametree();
            }
            String gamesetres = null;
            for (int p = 0; currGameIds != null && p < currGameIds.length; p++) {
                if (gamesetres == null) {
                    gamesetres = "";
                }
                if (p > 0) {
                    gamesetres += ",";
                }
                gamesetres += String.valueOf(currGameIds[p]);
            }
            gameSetsResults[i] = gamesetres;
        }
        String s = getMultiArrayJson(gameSets, gameSetsResults);
        String filePath = sharkStartFrame.sharedPathplus + ToolsOnlineResources.outputFolder + shark.sep + "OWLAvailableGameSets.json";
        try (FileWriter file = new FileWriter(filePath)) {
            file.write(s);
            file.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
        u2_base.launchExplorer(sharkStartFrame.sharedPathplus + ToolsOnlineResources.outputFolder);

        MYSQLGameFiltering = false;

    }

    static String makeRecordingDetails(String db, String name) {
        return db + name;
    }

    int isSoundVowel(String s) {
        return (isSoundSingleSound(s) > 0 && u.vowels.indexOf(s.charAt(0)) >= 0) ? 1 : 0;

    }

    int isSoundName(String s) {
        return (s.endsWith("!1") || s.endsWith("!1~") || s.endsWith("!!")) ? 1 : 0; //TODO ask about "!!" eg cr!! and dr!!
    }

    int isSoundSingleSound(String s) {
        return (s.endsWith("~") && !s.endsWith("~~") && !s.endsWith("!1~")) ? 1 : 0;
    }

    int isSoundHomophoneDescription(String s) {
        return s.endsWith("=") ? 1 : 0;
    }

    int isSoundNonsense(String s) {
        return s.indexOf(' ') < 0 && s.endsWith("!") ? 1 : 0;
    }

    int isSoundSyllable(String s) {
        return s.endsWith("~~") ? 1 : 0;
    }

    String getStrippedSoundName(String s) {
        int k;
        if ((k = s.indexOf("@")) >= 0) {
            s = s.substring(0, k);
        }
        if (s.endsWith("!1")) {
            s = s.substring(0, s.length() - 2);
        }
        if (s.endsWith("!!")) {
            s = s.substring(0, s.length() - 2);
        }
        if (s.endsWith("!1~")) {
            s = s.substring(0, s.length() - 3);
        }
        if (s.endsWith("~~")) {
            s = s.substring(0, s.length() - 2);
        }
        if (s.endsWith("~")) {
            s = s.substring(0, s.length() - 1);
        }
        if (s.endsWith("=")) {
            s = s.substring(0, s.length() - 1);
        }
        if (s.indexOf(' ') < 0 && s.endsWith("!")) {
            s = s.substring(0, s.length() - 1);
        }
        s = s.replaceAll(u.phonicsplits, "");
        return s;
    }

    public static String getSoundDbType(String s) {
        if (s.equals("publicsay1")) {
            return LEGACYSOUNDDB[LEGACYSOUNDDB_PUBLICSAY1_TYPE];
        } else if (s.equals("publicsay3")) {
            return LEGACYSOUNDDB[LEGACYSOUNDDB_PUBLICSAY3_TYPE];
        } else if (s.equals("publicsent1")) {
            return LEGACYSOUNDDB[LEGACYSOUNDDB_PUBLICSENT1_TYPE];
        } else if (s.equals("publicsent2")) {
            return LEGACYSOUNDDB[LEGACYSOUNDDB_PUBLICSENT2_TYPE];
        } else if (s.equals("publicsent3")) {
            return LEGACYSOUNDDB[LEGACYSOUNDDB_PUBLICSENT3_TYPE];
        }
        return null;
    }

    static String stripAts(String s) {
        int k = s.indexOf('@');
        if (k < 0) {
            return s;
        } else {
            return s.substring(0, k);
        }
    }


    static String getHashUUID(String s) {
        return String.valueOf(s.hashCode());
    }

    public static long longHash(String string) {
        long h = 98764321261L;
        int l = string.length();
        char[] chars = string.toCharArray();

        for (int i = 0; i < l; i++) {
            h = 31 * h + chars[i];
        }
        return h;
    }

//    String getGameIDFromNames(int staticGameIDs[], String staticGameNames[], String staticGameSubNames[], String gamename, String gamesubname) {
//        for (int i = 0; i < staticGameNames.length; i++) {
//            if (gamename.equalsIgnoreCase(staticGameNames[i]) && gamesubname.equals(staticGameSubNames[i])) {
//                return String.valueOf(staticGameIDs[i]);
//            }
//        }
//        return null;
//    }
    
    String getCourseIDFromNames(int staticCourseIDs[], String staticCourseNames[], String coursename) {
        for (int i = 0; i < staticCourseNames.length; i++) {
            if (coursename.equalsIgnoreCase(staticCourseNames[i])) {
                return String.valueOf(staticCourseIDs[i]);
            }
        }
        return null;
    }
    
    
    /*
     public void UploadGames(boolean wantUpdate) {

        String staticGameNames[] = new String[]{};
        String staticGameSubNames[] = new String[]{};
        String staticGameWholeNames[] = new String[]{};
        int staticGameIDs[] = new int[]{};
        String cvsSplitBy = ",";
        try {
            JSONParser parser = new JSONParser();
            JSONArray jsonarr = (JSONArray) parser.parse(new FileReader(sharkStartFrame.publicPathplus + "json" + shark.sep + "GamesAndIDs.json"));
            for (int i = 0; i < jsonarr.size(); i++) {
                JSONObject jo = (JSONObject) jsonarr.get(i);
                staticGameIDs = u.addint(staticGameIDs, ((Long) jo.get("id")).intValue());
                String gname = (String) jo.get("name");
                String gnamess[] = gname.split(cvsSplitBy);
                String name = gnamess[0].trim();
                String subname = gnamess.length > 1 ? gnamess[1].trim() : "";
                staticGameNames = u.addString(staticGameNames, name);
                staticGameSubNames = u.addString(staticGameSubNames, subname);
                staticGameWholeNames = u.addString(staticGameWholeNames, (name + " " + subname).trim());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String problemGames[] = new String[]{};
        for (int i = 0; i < ALL_GAMES.length; i++) {
            if (u.findString(staticGameWholeNames, ALL_GAMES[i]) < 0) {
                problemGames = u.addString(problemGames, ALL_GAMES[i]);
            }
        }
        if (problemGames.length > 0) {
            u.okmess(shark.programName + " Misnamed games", u.combineString(problemGames), sharkStartFrame.mainFrame);
            return;
        }

        jnode listofgames = (jnode) ttGames.root.getChildAt(1).getChildAt(0);
        int index = 0;

        int mexlength = 0;

        jnode gametree = (jnode) ttGames.root.getChildAt(1).getChildAt(1);

        loop1:

        for (Enumeration e = gametree.children(); e.hasMoreElements();) {
            jnode c = (jnode) e.nextElement();
            String s = c.get();

            // main category
            if (s.equalsIgnoreCase(REWARDS)) {
                continue loop1;
            }

            loop2:
            for (Enumeration e2 = c.children(); e2.hasMoreElements();) {
                jnode c2 = (jnode) e2.nextElement();

                loop3:
                for (Enumeration e3 = c2.children(); e3.hasMoreElements();) {
                    jnode c3 = (jnode) e3.nextElement();
                    String game = c3.get();

                    DocumentBuilderFactory docFactory = null;

                    Document doc = null;


                    DocumentBuilderFactory docFactory2 = null;
                    DocumentBuilder docBuilder2 = null;
                    Document doc2 = null;
                    Element rootElement2 = null;


                    // the games LIST OF GAMES
                    loop4:
                    for (Enumeration e4 = listofgames.children(); e4.hasMoreElements();) {
                        jnode c4 = (jnode) e4.nextElement();
                        String gamename = c4.get();

                        if (game.substring(1).equals(gamename)) {

                            ArrayList staticAl = gameOptionsStaticLookup(gamename);
                            String jsonKeys[] = new String[]{};
                            Object jsonValues[] = new Object[]{};

                            for (int i = 0; staticAl != null && i < staticAl.size(); i++) {
                                String nams[] = (String[]) staticAl.get(0);
                                Object vals[] = (Object[]) staticAl.get(1);
                                jsonKeys = u.addString(jsonKeys, nams);
                                jsonValues = u2_base.addObjects(jsonValues, vals);
                            }


                            String gametooltip = null;
                            String gametooltiph = null;


                            // game parameters
                            loop5:
                            for (Enumeration e5 = c4.children(); e5.hasMoreElements();) {
                                jnode c5 = (jnode) e5.nextElement();
                                String gameparameter = c5.get();
                                String preparam = null;
                                String endparam = null;
                                int k = gameparameter.indexOf('=');
                                if (k >= 0) {
                                    preparam = gameparameter.substring(0, gameparameter.indexOf('='));
                                    endparam = gameparameter.substring(gameparameter.indexOf('=') + 1);
                                }
                                if (gameparameter.equalsIgnoreCase("needsentences3")) {
                                    if (u.findString(simpleSentence3Games, gamename) < 0) {
                                        simpleSentence3Games = u.addString(simpleSentence3Games, gamename);
                                    }
                                    gameparameter = "special";
                                }
                                if (gameparameter.equalsIgnoreCase("needsentences1")) {
                                    if (u.findString(simpleSentence1Games, gamename) < 0) {
                                        simpleSentence1Games = u.addString(simpleSentence1Games, gamename);
                                    }
                                    gameparameter = "special";
                                }

                                if (preparam != null && preparam.equalsIgnoreCase("id") && u.findString(PHONICDISTRACTOR_GAMECODEID, endparam) >= 0) {

                                    gameparameter = "usesphonicdistractors";
                                }

                                if (isTopicFlag(gameparameter)) {
                                    String ss[] = doTopicFlag(gameparameter);
                                    if (ss != null) {
                                        try {
                                            if (docFactory2 == null) {
                                                docFactory2 = DocumentBuilderFactory.newInstance();
                                                docBuilder2 = docFactory2.newDocumentBuilder();
                                                doc2 = docBuilder2.newDocument();
                                                rootElement2 = doc2.createElement("TopicFlags");
                                                doc2.appendChild(rootElement2);
                                            }
                                        } catch (Exception ee) {
                                        }

                                        // TopicFlag element
                                        Element topicflag = doc2.createElement("TopicFlag");
                                        rootElement2.appendChild(topicflag);

                                        // set attributes
                                        Attr attr = doc2.createAttribute("TypeFlag");
                                        attr.setValue(String.valueOf(ss[0]));
                                        topicflag.setAttributeNode(attr);

                                        attr = doc2.createAttribute("Condition");
                                        attr.setValue(String.valueOf(ss[1]));
                                        topicflag.setAttributeNode(attr);
                                    }

                                } else if (preparam != null && preparam.equalsIgnoreCase("tooltip")) {
                                    gametooltip = endparam;
                                } else if (preparam != null && preparam.equalsIgnoreCase("tooltiph")) {
                                    gametooltiph = endparam;
                                } else if (preparam != null && preparam.equalsIgnoreCase("options")) {
                                    try {
                                        String ss[] = u.splitString(endparam, ',');
                                        mexlength = Math.max(ss.length, mexlength);
                                        loop6:
                                        for (int i = 0; i < ss.length; i++) {
                                            ArrayList al = gameOptionsLookup(gamename, ss[i]);
                                            if (al == null) {
                                                continue loop6;
                                            }

                                            jsonKeys = u.addString(jsonKeys, String.valueOf(al.get(0)));
                                            jsonValues = u2_base.addObject(jsonValues, al.get(1));
                                        }
                                    } catch (Exception ex) {
                                    }

                                }
                            };

                            if (docFactory != null) {
                                try {
                                    DOMSource domSource = new DOMSource(doc);
                                    StringWriter writer = new StringWriter();
                                    StreamResult result = new StreamResult(writer);
                                    TransformerFactory tf = TransformerFactory.newInstance();
                                    Transformer transformer = tf.newTransformer();
                                    transformer.transform(domSource, result);
                                    writer.flush();
                                    writer.toString();
                                } catch (TransformerException ex) {
                                    ex.printStackTrace();
                                }
                            }

                            if (docFactory2 != null) {
                                try {
                                    DOMSource domSource = new DOMSource(doc2);
                                    StringWriter writer = new StringWriter();
                                    StreamResult result = new StreamResult(writer);
                                    TransformerFactory tf = TransformerFactory.newInstance();
                                    Transformer transformer = tf.newTransformer();
                                    transformer.transform(domSource, result);
                                    writer.flush();
                                } catch (TransformerException ex) {
                                    ex.printStackTrace();
                                }
                            }
                            if (gametooltip != null && gametooltip.trim().equals("")) {
                                gametooltip = null;
                            }
                            if (gametooltiph != null && gametooltiph.trim().equals("")) {
                                gametooltiph = null;
                            }

                            String sss = getGameProperty(c4, "icon=");
                            String sicon = "i_" + sss;
                            sharkImage im = sharkImage.find(sicon);
                            if (im != null && currSaveSharkImage != null && !sicon.equals("i_salvage")) {
                                getHashOfSaveImage(currSaveSharkImage);
                            }
                            int k;
                            String gamesubname = null;
                            String origamename = gamename;
                            if ((k = gamename.indexOf("(")) > 1) {
                                gamesubname = gamename.substring(k).trim();
                                gamename = gamename.substring(0, k).trim();
                            }
//                            String gameId = getGameIDFromNames(staticGameIDs, staticGameNames, staticGameSubNames, gamename, gamesubname == null ? "" : gamesubname);
//                            if (gameId == null) {
//                                System.out.println("Game ID is null");
//                                System.exit(0);
//                            }
//                            int g = Integer.parseInt(gameId);
//                            gameNames = u.addString(gameNames, origamename);
//                            gameCategory = u.addString(gameCategory, String.valueOf(index));
//                            gameID = u.addint(gameID, g);
                            continue loop4;
                        }
                    }
                }
            }
            index++;
        }
        int g;
        g = 0;
    }   
    
    */

//    static int getGameID(String name, String category) {
//        for (int i = 0; i < gameNames.length; i++) {
//            if (gameNames[i].equalsIgnoreCase(name) && gameCategory[i].equals(category)) {
//                return gameID[i];
//            }
//        }
//        return -1;
//    }

    static int getGameID(String fullNameIncludingSubName) {
        for (int i = 0; i < allGameNames.length; i++) {
            if (allGameNames[i].equalsIgnoreCase(fullNameIncludingSubName)) {
                return allGameIDs[i];
            }
        }
        return -1;
    }

    static String[] getGameNamesFromIDs(String s) {
        String ss[] = u.splitString(s, ',');
        String ret[] = new String[]{};
        for (int i = 0; i < ss.length; i++) {

            int y = u.inintlist(allGameIDs, Integer.parseInt(ss[i]));
            ret = u.addString(ret, allGameNames[y]);
        }
        return ret;
    }

    static String getGameProperty(jnode jn, String key) {
        jnode children[] = jn.getChildren();
        for (int i = 0; i < children.length; i++) {
            String s = children[i].get();
            if (s.startsWith(key)) {
                return s.substring(key.length());
            }
        }
        return null;
    }

    static String getHashOfSaveImage(sharkImage.saveSharkImage ssi) {
        String ss = "";
        for (int i = 0; i < ssi.parts.length; i++) {
            ss += String.valueOf(ssi.parts[i].attachno);
            ss += String.valueOf(ssi.parts[i].controlno);
            ss += String.valueOf(ssi.parts[i].group);
            ss += String.valueOf(ssi.parts[i].type);
            ss += String.valueOf(ssi.parts[i].x);
            ss += String.valueOf(ssi.parts[i].y);
            ss += String.valueOf(ssi.controls.length);
        }
        return getHashUUID(ss);
    }

    static String getSVGViaFileFromHash(String sicon, String ihash) {
        String ss[] = u.readFile(SVGIMAGEFOLDER + shark.sep + sicon + "_" + String.valueOf(ihash) + ".svg");
        String s = null;
        if (ss != null) {
            s = combineStringArray(ss);
        }
        return s;
    }

    static String combineStringArray(String ss[]) {
        StringBuffer sbf = new StringBuffer();
        if (ss.length > 0) {
            sbf.append(ss[0]);
            for (int i = 1; i < ss.length; i++) {
                sbf.append(ss[i]);
            }
        }
        return sbf.toString();
    }


    static String formatForFileWrite(String wordname) {
        wordname = wordname.replaceAll("/", "-");
        wordname = wordname.replaceAll("&", "-");
        return wordname;
    }

    static boolean writeToSVGImageFile(String imname, String hash, String svg) {
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new FileWriter(SVGIMAGEFOLDER + shark.sep + formatImageName(imname) + "_" + hash + ".svg"));
            pw.println(svg);
            pw.flush();
            saveImageHashes = u.addString(saveImageHashes, hash);
            return true;
        } catch (Exception e) {
        }
        return false;
    }

    static String getSVGPathFile(String imname, String hash) {
        return WEBSVGFOLDERPLUS + formatImageName(imname) + "_" + hash + ".svg";
    }

    static String formatImageName(String imname) {
        String ret = "";
        char c[] = imname.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (Character.isLetterOrDigit(c[i]) || c[i] == '_') {
                ret += String.valueOf(c[i]);
            }
        }
        return ret;
    }

    public JSONObject topicScanForUnits(topicTree topicTreeList, jnode selnode, int p, String courseids[], String coursenames[]) {
        String parentName = null;
        if ((u.findString(coursenames, currentCourse)) >= 0) {
            parentName = currentCourse;
        }       
        int lastUnitIndex = -1;
        
        JSONArray unitsJsonArray = new JSONArray();

        boolean firstone = true;
        enumloop:
        for (Enumeration e = ((jnode) selnode).preorderEnumeration(); e.hasMoreElements();) {
            jnode jn = (jnode) e.nextElement();
            if (firstone) {
                firstone = false;
                continue enumloop;
            }
            
            if (jn.isLeaf()) {
                continue enumloop;
            }   
            String sh = jn.get();
            if (sh.trim().equals("")) {
                continue enumloop;
            }
            lastUnitIndex += 1;
            String unitType = null;
            jnode jnode1 = (jnode) jn.getNextSibling();
            if (jnode1 == null || (jnode1.get().trim() == "")) {
                if (selnode.get().equals(COURSE_WORDSHARK)) {
                    unitType = "ap_end";
                }
            }
                        
            JSONObject jsonObject = UploadTopicHeading(u.formatTextforUpload(sh), String.valueOf(lastUnitIndex), parentName, MYSQLUpload.TOPIC_HEADING_NAME_TYPE,
                unitType, "dummy description"); 

            unitsJsonArray.add(jsonObject);

        }
        
        JSONObject postJsonObject = new JSONObject();
        postJsonObject.put("json_data", unitsJsonArray);
        
        return apiRequest(API_CONFIGS[currentEnvironment].url + "ports/units",
            postJsonObject.toJSONString(), currentEnvironment);   
    }

    public void topicScanForWordlists(int versionNo, JSONArray unitIds, topicTree topicTreeList, jnode selnode, int p, String courseids[], String coursenames[]) {
        boolean firstone = true;
        int unitIndex = -1;
        int wordlistIndex = -1;
        int wordlistDoneCount = 0; 
        int topicCount = getTopicCount(selnode);
        enumloop:
        for (Enumeration e = ((jnode) selnode).preorderEnumeration(); e.hasMoreElements();) {
            jnode jn = (jnode) e.nextElement();
 
            if (firstone) {
                firstone = false;
                continue enumloop;
            }
            if (!jn.isLeaf()) {
                unitIndex++;
                wordlistIndex = -1;
                continue enumloop;
            } 
            String sh = jn.get();
            if (sh.trim().equals("")) {
                continue enumloop;
            }
            
            wordlistIndex++;


            String nam = jn.get();
            if (nam.startsWith(topicTree.ISTOPIC)) {
                nam = nam.substring(1);
            }

            saveTree1 st1 = (saveTree1) db.find(topic.publictopics, nam, db.TOPIC);
                
            String topicName = stripAts(st1.curr.names[0]);
            t = topic.findtopic(topicName);
            t.getWords(null, false);
  
            treeDetails tree = new treeDetails(
                t ,
                st1,
                topicTreeList,
                jn
            );
                    
            JSONObject wordlistJson = getTopicJsonForUpload(tree, Integer.parseInt(String.valueOf(unitIds.get(unitIndex))), wordlistIndex, selnode.get());
            
            JSONObject postJson = new JSONObject();
            postJson.put("wordlist_data", wordlistJson);
            postJson.put("version_id", versionNo);

            JSONObject postJsonObject = new JSONObject();
            postJsonObject.put("json_data", postJson);

            JSONObject responseJson = apiRequest(API_CONFIGS[currentEnvironment].url + "ports/wordlists/queue",
                        postJsonObject.toString(), currentEnvironment);
            wordlistDoneCount++;
            System.out.println(String.valueOf(responseJson.get("message")) + ": " + sh + " PROGRESS " + String.valueOf((int)(((float)wordlistDoneCount/topicCount)*100)) + "%");
        }
        
        JSONObject responseJson = apiRequest(API_CONFIGS[currentEnvironment].url + "ports/versions/"+String.valueOf(versionNo)+"/process-imports",
                    null, currentEnvironment);
        System.out.println("FINISHED: Initiated wordlist jobs in the API");
        u.okmess(shark.programName, String.valueOf(responseJson.get("message")), sharkStartFrame.mainFrame);
    }    
    
    
    public String getSingleArrayJson(String keys[], Object values[]) {
        JSONObject objectmain = new JSONObject();
        for (int i = 0; i < keys.length; i++) {
            objectmain.put(keys[i], values[i]);
        }
        return objectmain.toJSONString();
    }

    public String getMultiArrayJson(String keys[], String values[]) {
        JSONArray arrayElementOneArray = new JSONArray();
        for (int i = 0; i < keys.length; i++) {
            JSONObject obSub = new JSONObject();
            obSub.put(keys[i], values[i]);
            arrayElementOneArray.add(obSub);
        }
        return arrayElementOneArray.toJSONString();
    }

    static public String getWordListGameOptionSettings(ArrayList al) {
        JSONObject objectmain = new JSONObject();
        for (int i = 0; i < al.size(); i++) {
            ArrayList alinner = (ArrayList) al.get(i);
            int gameid = (int) alinner.get(0);
            ArrayList alinnervals = (ArrayList) alinner.get(1);
            if (alinnervals.size() > 0) {
                JSONObject objectsub = new JSONObject();
                for (int k = 0; k < alinnervals.size(); k++) {
                    ArrayList al2 = (ArrayList) alinnervals.get(k);
                    objectsub.put((String) al2.get(0), al2.get(1));
                }
                objectmain.put(gameid, objectsub);
            }
        }
        return objectmain.toJSONString();
    }

    String getPhonicSplitParts(word w, String wholesound) {
        if (!w.phonics) {
            return null;
        }
        String sounds[] = new String[]{};
        if (w.phonicsw) {
            String si[] = w.phonicsall();
            if (w.value.indexOf("'") >= 0) {
                int k;
                k = 0;
            }
            String lastsyll = "";
            boolean gotsyll = false;
            int lastSplitIndex = -1;
            for (int ix = 0; ix < si.length; ix++) {
                if (si[ix].replace("@", "").trim().equals("")) {
                    continue;
                }
                if (si[ix].equals("'")) {
                    continue;
                }
                int k = si[ix].indexOf("@");
                if (k > 0) {
                    si[ix] = si[ix].substring(0, k);
                }
                if (si[ix].trim().equals("")) {
                    continue;
                }
                if (si[ix].startsWith("/")) {
                    si[ix] = si[ix].substring(1);
                    if (lastSplitIndex < 0 || ix > lastSplitIndex + 1) { // don't want single sounds spoken as syllables
                        sounds = u.addString(sounds, lastsyll.substring(0, lastsyll.length() - 1) + "~~");
                        gotsyll = true;
                    }
                    lastsyll = "";
                    lastSplitIndex = ix;
                }
                sounds = u.addString(sounds, si[ix] + "~");
                lastsyll += si[ix] + u.phonicsplits;
            }
            if (gotsyll) {
                sounds = u.addString(sounds, lastsyll.substring(0, lastsyll.length() - 1) + "~~");
            }
        } else {
            sounds = u.addString(sounds, w.phonics()[0] + "~");
        }
        // special case - not sure what to do
        for (int i = sounds.length - 1; i >= 0; i--) {
            if (sounds[i].equals("-~")) {
                sounds = u.removeString(sounds, i);
            }
        }
        for (int i = 0; i < sounds.length; i++) {
            //            if(sounds[i].equals("-~"))continue;//short form words e.g. I'm, - is for apostrophe
            String s1 = tor.findJsonRecording(jsonRecResults, "publicsay1", sounds[i]);
            if (s1 == null) {
                s1 = tor.findJsonRecording(jsonRecResults, "publicsay1", sounds[i].toLowerCase());
            }
            if (s1 == null) {
                s1 = tor.findJsonRecording(jsonRecResults, "publicsay3", sounds[i]);
            }
            if (s1 == null) {
                s1 = tor.findJsonRecording(jsonRecResults, "publicsay3", sounds[i].toLowerCase());
            }
            if (s1 == null) {
                System.out.println("**@@@@@@@****NOJSONSOUND*****************" + sounds[i]);
                System.exit(0);
            }
            sounds[i] = s1;
        }
        if (w.phonicsw) {
            sounds = u.addString(sounds, wholesound);
        }
        return u.combineString(sounds);
    }

    public String[] getTopicSettings(topicTree topicTreeList, jnode jn, saveTree1 st, int topic_id) {
        return new String[]{
            getTopicDetail(st, topic.types[topic.HOMOPHONES], true),
            getTopicDetail(st, topic.types[topic.INORDER], true),
            getTopicDetail(st, topic.types[topic.JUSTPHONICS], true),
            getTopicDetail(st, topic.types[topic.NOTPHONICS], true),
            getTopicDetail(st, topic.types[topic.STARTPHONICS], true),
            getTopicDetail(st, topic.types[topic.NONSENSE], true)
        };
    }

    public int getLongestWord(word ww[]) {
        int n = Integer.MIN_VALUE;
        for (int i = 0; i < ww.length; i++) {
            n = Math.max(ww[i].v().length(), n);
        }
        return n;
    }

    static String getImageFileWebPathForWord(String s) {
        String imname = null;
        for (int i = 0; i < BMPIMAGEEXTENSIONS.length; i++) {
            String wpath = BMPIMAGEFOLDERPLUS + s + BMPIMAGEEXTENSIONS[i];
            File f = new File(wpath);
            if (f.exists()) {
                imname = WEBIMAGEFOLDERPLUS + s + BMPIMAGEEXTENSIONS[i];
                break;
            }
        }
        return imname;
    }

    static String getImageFileIdentifierForWord(String s) {
        String imid = null;
        for (int i = 0; i < BMPIMAGEEXTENSIONS.length; i++) {
            String wpath = BMPIMAGEFOLDERPLUS + s + BMPIMAGEEXTENSIONS[i];
            File f = new File(wpath);
            if (f.exists()) {
                imid = String.valueOf(f.length());
                break;
            }
        }
        return imid;
    }

    static void getRudeWords() {

        runningGame rg = new runningGame(RUDEWORDGAME, sharkStartFrame.mainFrame.wordTree, sharkStartFrame.currPlayTopic.name, false);
        mySqlRudeWords = u.splitString(rg.getParm("rudewords"));

    }

    String findImageinStandardList(word w) {
        for (int i = 0; currStandardWords != null && i < currStandardWords.length; i++) {
            if (w.v().equalsIgnoreCase(currStandardWords[i].v())) {
                return currStandardWords[i].vpic();
            }
        }
        return null;
    }

    boolean isCaption(String s) {
        for (int i = 0; currStandardWords != null && i < currStandardWords.length; i++) {
            if (s.equalsIgnoreCase(currStandardWords[i].v())) {
                return true;
            }
        }
        return false;
    }

    String adjustedWordValue(String s) {
        int k;
        if ((k = s.indexOf("@")) > 0) {
            s = s.substring(0, k);
        }
        // don't want sounds with uppercase
        if ((k = s.indexOf("=")) >= 0) {
            String temps = s.substring(k + 1).toLowerCase();
            s = s.substring(0, k + 1) + temps;
        }
        String oris = s;
        // remove the phonics dot from words like:  I=igh·
        if ((k = s.indexOf("=")) >= 0 && s.endsWith(u.phonicsplits)) {
            String temps = s.substring(k + 1);
            if ((temps.substring(0, temps.length() - 1)).indexOf(u.phonicsplits) < 0) {
                s = oris = s.substring(0, s.length() - 1);
            }
        }

        String sep = "-·";
        if ((k = s.indexOf("'")) < 0) {
            return oris;
        }
        if (s.substring(k).indexOf("=") < 0) {
            return oris;
        }
        s = s.substring(0, k + 1) + s.substring(k + 2); // gets rid of phonics dot to the right of the ' (on the left of the =)
        k = s.indexOf("-");
        if ((k = s.indexOf(sep)) < 0) {
            return oris;
        }
        s = s.substring(0, k) + s.substring(k + sep.length());
        return s;
    }

    String addSyllableSplits(String topicName, String word) {

        int ii[] = getTopicSplits(topicName, word);
        char c[] = word.toCharArray();
        char cout[] = new char[]{};
        if (ii != null && ii.length > 1) {
            int g;
            g = 0;
            int currsplit = 1;
            int currc = 0;
            for (int i = 0; i < c.length; i++) {
                if (c[i] != u.phonicsplit) {
                    if (currsplit < ii.length && currc == ii[currsplit]) {
                        cout = u.addchar(cout, '/');
                        currsplit++;
                    }
                    currc++;
                }
                cout = u.addchar(cout, c[i]);
            }
        } else {
            cout = c;
        }
        return String.valueOf(cout);
    }

    int[] getTopicSplits(String topicName, String word) {
        String ss[] = (String[]) db.find("publicsplits", topicName, db.TEXT);
        if (ss == null) {
            return null;
        }
        int k;
        for (int i = 0; i < ss.length; i++) {
            if ((k = ss[i].indexOf('=')) > 0) {
                ss[i] = ss[i].substring(0, k);
            }
            ss[i] = ss[i].replaceAll(u.phonicsplits, "");
        }
        if ((k = word.indexOf('=')) > 0) {
            word = word.substring(0, k);
        }
        word = word.replaceAll(u.phonicsplits, "");
        int reti[] = null;
        for (int i = 0; i < ss.length; i++) {
            if (ss[i].replaceAll("/", "").equals(word.replaceAll("/", ""))) {
                String ss2[] = u.splitString(ss[i], "/");
                if (ss2.length > 1) {
                    reti = new int[]{0};
                    int prevlength = ss2[0].length();
                    int tally = 0;
                    for (k = 1; k < ss2.length; k++) {
                        reti = u.addint(reti, (tally == 0 ? 0 : tally) + prevlength);
                        tally += prevlength;
                        prevlength = ss2[k].length();

                    }
                }
                break;
            }
        }
        return reti;
    }

    String adjustGamesBlockTitle(String s) {
        s = s.substring(GTX_GAMES.length());
        String ss[] = u.splitString(s, ",");
        for (int i = ss.length - 1; i >= 0; i--) {
            if (u.findString(GAMESBLOCKSTOIGNORE, ss[i]) >= 0 || u.findString(GAMESBLOCKSTOIGNORE, ss[i].toLowerCase()) >= 0) {
                ss = u.removeString(ss, i);
            }
        }
        if (ss.length == 0) {
            return null;
        }
        return GTX_GAMES + u.combineString(ss, ",");
    }
    
    String[] getAllSentenceTargetWords(int j, int baselev, saveTree1 st, boolean isCaptions){
        String allTargets[] = new String[]{};
        while (j < st.curr.names.length && st.curr.levels[j] >= baselev && (!isCaptions || isCaption(st.curr.names[j]))) {
            String senttype = getSentenceType(st.curr.names[j]);
            String ssTargets[] = getSentenceTargetWords(st.curr.names[j], senttype, true);
            
            for (int i = 0; i < ssTargets.length; i++) {
                String newString = getCorrectCaseFromStandardList(ssTargets[i]);
                  // String newString = ssTargets[i].toLowerCase();
                if(u.findString(allTargets, newString) < 0){
                    allTargets = u.addString(allTargets, newString);
                }
            }
            j++;
        }
        return allTargets;
    }   
    
    String getCorrectCaseFromStandardList(String word){
        for (int i = 0; i < currStandardWords.length; i++) {
            String plainWord = currStandardWords[i].v();
            if(plainWord.toLowerCase().equals(word.toLowerCase())){
                return plainWord;
             }
        }                
        return word;
    } 

    static String getSentenceType(String s) {
        s = s.trim();
        int k;
        if (s.startsWith("~")) {
            return SENTENCEJUMBLED_TYPE;
        } else if (s.indexOf("[") >= 0 && s.indexOf("]") >= 0) {
            return SENTENCEDICTATION_TYPE;
        } else if (s.indexOf("/") >= 0) {
            String ss[] = u.splitString(s, " ");
            for (int i = 0; i < ss.length; i++) {
                if (ss[i].length() - ss[i].replace("/", "").length() > 1) {
                    return SENTENCECLOZEWITHDISTRACTORS_TYPE;
                }
            }
            return SENTENCECLOZE_TYPE;
        } else {
            return SENTENCEGARBAGE_TYPE;
        }
    }

    static String getSentenceText(String s, String type) {
        String ret = "";
        sentence sent = new sentence(s, null);
        if (sent.val.indexOf('|') >= 0) {
            int f;
            f = 9;
        }
        if (sent.type == sent.TYPE) {
            ret = sent.val;
        } else if (sent.type == sent.CLOZE || sent.type == sent.SIMPLECLOZE) {
            ret = sent.stripcloze();
        } else {
            ret = sent.stripcloze();
        }
        ret = u.formatTextforUpload(ret);
        return ret;
    }

    // do we need to adjust this to acommodate multiple target images in same sentence?
    static String[] getSentenceTargetImages(String s) {
        int k;
        if ((k = s.indexOf("{")) >= 0) {
            s = s.substring(k + 1);
            if ((k = s.indexOf("}")) >= 0) {
                return new String[]{s.substring(0, k)};
            }
        }
        return null;
    }

    static String[] getSentenceTargetWords(String s, String type, boolean withAts) {
        sentence sent = new sentence(s, null);
        if (sent.type == sent.JUMBLED
                || sent.type == sent.TYPE
                || sent.type == sent.PICK) {
            return null;
        }

        String ret[] = new String[]{};
        if (withAts) {
            ret = sent.getAnswerListWithAts();
        } else {
            ret = sent.getAnswerList();
        }
        if (ret.length == 0) {
            return null;
        }
        return ret;
    }

    static String[] getSentenceDistractorWords(String s, String type) {

        sentence sent = new sentence(s, null);
        if (sent.type == sent.JUMBLED
                || sent.type == sent.TYPE) {
            return null;
        }
        String ret[] = new String[]{};

        if (sent.type == sent.SIMPLECLOZE || sent.type == sent.CLOZE) {
            for (int i = 0; i < sent.segs.length; i++) {
                if (sent.segs[i].choice && !sent.segs[i].firstchoice) {
                    String distractorText = sent.segs[i].val;
                    if (u.findString(ret, distractorText) >= 0) {
                        u.okmess(shark.programName, "Duplicate distractor in sentence: " + distractorText, sharkStartFrame.mainFrame);
                        System.exit(0);
                    }
                    ret = u.addString(ret, sent.segs[i].val);
                }

            }
        } else if (sent.type == sent.PICK) {
            topic t = new topic(u.absoluteToRelative(sharkStartFrame.publicTopicLib[0]), "garbage", null, null);
            if (t != null) {
                word[] garbagew = t.getAllWordsNoSent();
                for (int i = 0; i < garbagew.length; ++i) {
                    String distractorText = garbagew[i].v();
                    if (u.findString(ret, distractorText) >= 0) {
                        u.okmess(shark.programName, "Duplicate distractor in sentence: " + distractorText, sharkStartFrame.mainFrame);
                        System.exit(0);
                    }
                    ret = u.addString(ret, distractorText);
                }
            }
        }

        if (ret.length == 0) {
            return null;
        }
        return ret;
    }

    static boolean isAmongstSiblings(saveTree1 st, int j) {
        int orilev = st.curr.levels[j];
        int count = 0;

        innerloop:
        while (j < st.curr.levels.length && st.curr.levels[j] >= orilev) {
            if (st.curr.levels[j] == orilev) {
                count++;
            }
            j++;
        }
        return count > 1;
    }

    static String getRootSuffix(String s, String val, String suff) {

        boolean q_double = false;
        boolean no_double = false;
        boolean q_strip_e = false;
        boolean keep_e_cons = false;
        boolean keep_e_cg = false;
        boolean keep_e_long = false;
        boolean q_y_to_i = false;
        boolean keep_y = false;
        boolean q_i_to_y = false;
        boolean keep_i = false;
        boolean keep_f = false;
        boolean q_f_to_v = false;
        boolean q_f_to_v2 = false;
        String valsuff;

        int pluspos = s.indexOf("+");

        int len = val.length();
        char last = val.charAt(len - 1);
        char last2 = val.charAt(len - 2);
        String lastcs = val.substring(len - 1);
        char suff1 = suff.charAt(0);
        if (u.vowelsy.indexOf(last) < 0) {
            q_double = true;
            if (s.charAt(pluspos + 1) == '+') {
                no_double = true;
            } else if (u.vowelsy.indexOf(suff1) < 0) {
                no_double = true;
            } else if (u.vowels.indexOf(last2) < 0) {
                no_double = true;
            } else if (len == 2 || u.vowels.indexOf(val.charAt(len - 3)) >= 0) {
                no_double = true;
            } else if (last == 'w' || last == 'x' || last == 's') {
                no_double = true;
            }
        }
        if (last == 'e' && last2 == 'i') {
            q_i_to_y = true;
            if (suff1 != 'i') {
                keep_i = true;
            }
        }
        if (last == 'e') {
            q_strip_e = true;
            if (u.vowelsy.indexOf(suff1) < 0) {
                keep_e_cons = true;
            } else if ("cg".indexOf(last2) >= 0 && "aou".indexOf(suff1) >= 0) {
                keep_e_cg = true;
            } else if (len == 2
                    || suff1 != 'e'
                    && u.vowelsy.indexOf(last2) >= 0
                    && (!q_i_to_y || keep_i)) {
                keep_e_long = true;
            }
        }
        if (last == 'y') {
            q_y_to_i = true;
            if (suff1 == 'i' || u.vowels.indexOf(last2) >= 0) {
                keep_y = true;
            }
        }
        if (last == 'f') {
            q_f_to_v = true;
            if (!suff.equals("es")) {
                keep_f = true;
            }
        }
        if (last2 == 'f' && last == 'e') {
            q_f_to_v2 = true;
            if (!suff.equals("s")) {
                keep_f = true;
            }
        }
        valsuff = val.substring(0, len - 2);
        if (q_i_to_y && !keep_i) {
            valsuff += "y";
        } else if (q_f_to_v2 && !keep_f) {
            valsuff += "v";
        } else {
            valsuff = valsuff + val.substring(len - 2, len - 1);
        }
        if (q_y_to_i && !keep_y) {
            valsuff += "i";
        } else if (q_f_to_v && !keep_f) {
            valsuff += "v";
        } else if (!q_strip_e || keep_e_cons || keep_e_cg || keep_e_long) {
            valsuff += lastcs;
        }
        if (q_double && !no_double) {
            valsuff += lastcs;
        }
        valsuff += suff;
        return valsuff;
    }

    static word[] getStandardWords(saveTree1 st) {
        word ww[] = new word[]{};
        mloop:
        for (int j = 0; j < st.curr.names.length; ++j) {
            if (st.curr.levels[j] != 1) {
                continue mloop;
            }
            String allornones[] = new String[]{};
            if (st.curr.names[j].startsWith(GTX_ALLORNONE)) {
                int jj = j + 1;
                while (st.curr.levels[jj] > 1) {
                    for (int k = 0; k < topic.types.length; ++k) {
                        if (st.curr.names[jj].startsWith(topic.types[k])) {
                            continue mloop;
                        }
                    }
                    word wd = new word(st.curr.names[jj], "publictopics");
                    allornones = u.addString(allornones, wd.value);
                    ww = u.addWords(ww, wd);
                    jj++;
                }
                if (allornones.length > 1) {
                    standardListAllOrNones.add(allornones);
                }
                continue mloop;
            }
            if (st.curr.names[j].startsWith(GTX_TEACHINGNOTE)) {
                continue mloop;
            }
            for (int k = 0; k < topic.types.length; ++k) {
                if (st.curr.names[j].startsWith(topic.types[k])) {
                    continue mloop;
                }
            }
            if (st.curr.names[j].startsWith("(")) {
                continue mloop;
            }
            ww = u.addWords(ww, new word(st.curr.names[j], "publictopics"));
        }
        return ww.length > 0 ? ww : null;
    }

    static word[] getPairs(saveTree1 st) {
        word ww[] = new word[]{};
        mloop:
        for (int j = 0; j < st.curr.names.length; ++j) {
            if (st.curr.names[j].startsWith(GTX_PAIRS)) {
                j++;
                ww = u.addWords(ww, new word(st.curr.names[j], "publictopics"));
                j++;
                ww = u.addWords(ww, new word(st.curr.names[j], "publictopics"));
            }
        }
        return ww.length > 0 ? ww : null;
    }

    String doGameOptions(saveTree1 st) {
        ArrayList al = new ArrayList();
        mloop:
        for (int j = 0; j < st.curr.names.length; ++j) {
            if (st.curr.levels[j] != 1) {
                continue mloop;
            }
            if (!st.curr.names[j].startsWith(GTX_GAME_OPTION)) {
                continue mloop;
            }

            String g = st.curr.names[j].substring(GTX_GAME_OPTION.length());
            int k;
            String gname = null;
            String ss[] = null;
            try {
                gname = g.substring(0, (k = g.indexOf(',')));
                ss = u.splitString(g.substring(k + 1), ',');
            } catch (Exception ses) {
                continue mloop;
            }
            int gameid = getGameID(gname);
            if (gameid < 0) { // old games not in the online version are still present - need to be ignored
                continue;
            }
            ArrayList alinner = new ArrayList();
            alinner.add(gameid);
            ArrayList innersettings = new ArrayList();
            for (int i = 0; i < ss.length; i++) {
                ArrayList arl = gameOptionsLookup(gname, ss[i]);
                if (arl != null) {
                    innersettings.add(arl);
                }
            }
            if (innersettings.size() > 0) {
                alinner.add(innersettings);
                al.add(alinner);
            }
        }
        return al.size() == 0 ? null : getWordListGameOptionSettings(al);
    }

    String[] getNonsenseRhymeDistractors(saveTree1 st) {
        String ss[] = new String[]{};
        mloop:
        for (int j = 0; j < st.curr.names.length; ++j) {
            String trimName = st.curr.names[j].trim();
            if (trimName.startsWith("(") && trimName.endsWith(")")) {
                ss = u.addString(ss, trimName.substring(1, trimName.length() - 1));
            }
        }
        return ss.length > 0 ? ss : null;
    }

    String[] getPhonicDistractors(saveTree1 st) {
        String ss[] = new String[]{};
        mloop:
        for (int j = 0; j < st.curr.names.length; ++j) {
            if (st.curr.levels[j] != 1) {
                continue mloop;
            }
            if (!st.curr.names[j].startsWith(GTX_PHONIC_DISRACTORS)) {
                continue mloop;
            }
            ss = u.addString(ss, st.curr.names[j].substring(GTX_PHONIC_DISRACTORS.length()));
        }
        return ss.length > 0 ? ss : null;
    }

    boolean isSelect(String s) {
        return s.startsWith(GTX_SELECTEXTENDEDNO) || s.startsWith(GTX_SELECTEXTENDEDNOGROUP);
    }

    String getSelectAtt(String s) {
        if (s.startsWith(GTX_SELECTEXTENDEDNO)) {
            return GTX_SELECTNO_AT_XML;
        }
        if (s.startsWith(GTX_SELECTEXTENDEDNOGROUP)) {
            return GTX_SELECTGROUPNO_AT_XML;
        }
        return null;

    }


    static JSONObject doTopicBlockJson(String topicBlockType) {
        return doTopicBlockJson(topicBlockType, null);
    }

    
    static JSONObject doTopicBlockJson(String topicBlockType, String gameType) {
        JSONObject topicBlock = new JSONObject();
        topicBlock.put("TBType", topicBlockType);
        if (gameType != null) {
            topicBlock.put("GameBlockType", gameType);
        }         
        return topicBlock;
    }

    static int doSelectLevelAdjuster(int propLev) {
        if (lastPropLevel != propLev) {
            currentselectLevelAdjuster = 0;
        }

        // if propLev is different from last time = different level
        int dif = propLev - lastActualSelectLevel;
        if (currentselectLevelAdjuster == 0 && dif > 1) {
            currentselectLevelAdjuster = Math.abs(dif - 1);
        }
        lastActualSelectLevel = propLev - currentselectLevelAdjuster;
        lastPropLevel = propLev;
        return lastActualSelectLevel;
    }

    void initSelectLevelAdjuster(int initLev, int start) {
        selectLevelAdjuster = initLev;
        lastActualSelectLevel = start;
        lastPropLevel = start;
        currentselectLevelAdjuster = 0;
    }

    String[] makeUpTheNumbers(String s[], int wanted) {
        String ss[] = s;
        while (ss.length < wanted) {
            for (int i = 0; i < s.length; i++) {
                ss = u.addString(ss, s[i]);
            }
        }
        return ss;
    }

    String[] getWordValues(word w[]) {
        String ss[] = new String[]{};
        for (int i = 0; i < w.length; i++) {
            ss = u.addString(ss, w[i].value);
        }
        return ss;
    }

    
    static boolean isDuplicate(Element parent, Element currentRef) {
        NodeList nodeList = currentRef.getElementsByTagName("Word");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node childNode = nodeList.item(i);
            if (childNode.getAttributes() != null && childNode.getAttributes().getNamedItem("WordUUID") != null) {
                return isDuplicate2(parent.getChildNodes(), childNode.getAttributes().getNamedItem("WordUUID").getNodeValue());
            }
        }
        return false;
    }

    static boolean isDuplicate2(NodeList nodeList, String val) {
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node childNode = nodeList.item(i);
            if (childNode.getAttributes() != null && childNode.getAttributes().getNamedItem("WordUUID") != null && childNode.getAttributes().getNamedItem("WordUUID").getNodeValue().equals(val)) {
                return true;
            }
            NodeList children = childNode.getChildNodes();
            if (children != null) {
                isDuplicate2(children, val);
            }
        }
        return false;
    }

    // if no pattern, assume is a target
    static String getTargetWithAnyPattern(String wval, String ss[]) {
        if (ss == null || ss.length == 0) {
            return wval;
        }
        for (int i = 0; i < ss.length; i++) {
            boolean b[] = wordlist.fitsPattern(wval, ss[i], new boolean[wval.length()], 0);
            boolean found = false;
            for (int j = 0; j < b.length; j++) {
                if (b[j]) {
                    found = true;
                    break;
                }
            }
            if (found) {
                String s = wval;
                boolean rightBracketNext = true;
                for (int j = b.length - 1; j >= 0; j--) {
                    if (rightBracketNext && b[j]) {
                        s = s.substring(0, j + 1) + "]" + s.substring(j + 1);
                        rightBracketNext = false;
                    }
                    if (!rightBracketNext && !b[j]) {
                        s = s.substring(0, j + 1) + "[" + s.substring(j + 1);
                        rightBracketNext = true;
                    }
                }
                if (!rightBracketNext) {
                    s = "[" + s;
                }
                return s;
            }
        }
        return null;
    }

    static word[] getWordsWithoutPattern(word w[], String pats[]) {
        if (pats == null || pats.length == 0 || w == null || w.length == 0) {
            return w;
        }
        for (int i = w.length - 1; i >= 0; i--) {
            for (int j = 0; j < pats.length; j++) {
                if (wordlist.fits(w[i].v(), pats[j]) >= 0) {
                    w = u.removeword(w, i);
                }
            }
        }
        return w;
    }


    static String getGamesCategory(jnode jn) {
        int c = sharkStartFrame.mainFrame.publicGameTree.root.getChildAt(0).getChildCount();
        for (int i = 0; i < c; i++) {
            if (jn.isNodeAncestor(sharkStartFrame.mainFrame.publicGameTree.root.getChildAt(0).getChildAt(i))) {
                return getMainCategoryText(i, true);
            }
        }
        return null;
    }

    static public String getTopicDetail(saveTree1 st, String key, boolean returnBool) {
        for (int j = 0; j < st.curr.names.length; ++j) { //For 2.1
            if (st.curr.names[j].startsWith(GTX_TEACHINGNOTE) && key.equals((GTX_TEACHINGNOTE))) {
                int k = j + 1;
                String tnote = st.curr.names[j].substring(key.length());
                while (k < st.curr.names.length && st.curr.names[k].startsWith(GTX_TEACHINGNOTE)) {
                    tnote += " " + st.curr.names[k].substring(key.length());
                    k++;
                }
                return u.setTextHtmlFormattedForUpload(tnote);
            } else if (st.curr.levels[j] == 1 && st.curr.names[j].startsWith(key)) {
                if (st.curr.names[j].trim().length() <= key.length()) {
                    return "1";
                }
                return st.curr.names[j].substring(key.length());
            }
        }
        return returnBool ? "0" : null;
    }

    boolean isTopicFlag(String param) {
        if (param.equalsIgnoreCase("usesphonicdistractors")) {
            return true;
        }
        if (param.equalsIgnoreCase("phonics")) {
            return true;
        }
        if (param.equalsIgnoreCase("notphonics")) {
            return true;
        }
        if (param.equalsIgnoreCase("phonicsw")) {
            return true;
        }
        if (param.equalsIgnoreCase("notphonicsw")) {
            return true;
        }
        if (param.equalsIgnoreCase("phonicsingles")) {
            return true;
        }
        if (param.equalsIgnoreCase("needsyllsplit")) {
            return true;
        }
        if (param.equalsIgnoreCase("needanysplit")) {
            return true;
        }
        if (param.equalsIgnoreCase("needonset")) {
            return true;
        }
        if (param.equalsIgnoreCase("needbad")) {
            return true;
        }
        if (param.equalsIgnoreCase("usepattern")) {
            return true;
        }
        if (param.equalsIgnoreCase("special")) {
            return true;
        }
        if (param.equalsIgnoreCase("pairedwords")) {
            return true;
        }
        if (param.equalsIgnoreCase("justpairedwords")) {
            return true;
        }
        if (param.equalsIgnoreCase("notpairedwords")) {
            return true;
        }
        if (param.equalsIgnoreCase("nosingleletters")) {
            return true;
        }
        if (param.equalsIgnoreCase("multisyllable")) {
            return true;
        }
        if (param.equalsIgnoreCase("notmultisyllable")) {
            return true;
        }
        if (param.equalsIgnoreCase("nottranslations")) {
            return true;
        }
        if (param.equalsIgnoreCase("notdefinitions")) {
            return true;
        }
        if (param.equalsIgnoreCase("flonly")) {
            return true;
        }
        if (param.equalsIgnoreCase("notfl")) {
            return true;
        }
        if (param.equalsIgnoreCase("notifdups")) {
            return true;
        }
        if (param.equalsIgnoreCase("notblended")) {
            return true;
        }
        if (param.equalsIgnoreCase("needpictures")) {
            return true;
        }
        if (param.equalsIgnoreCase("needsentences1")) {
            return true;
        }
        if (param.equalsIgnoreCase("needsentences3")) {
            return true;
        }
        if (param.equalsIgnoreCase("needchunks")) {
            return true;
        }
        if (param.equalsIgnoreCase("avwordlen4")) {
            return true;
        }
        if (param.equalsIgnoreCase("needshapes")) {
            return true;
        }
        if (param.equalsIgnoreCase("notshapes")) {
            return true;
        }
        if (param.equalsIgnoreCase("owlneedrec")) {
            return true;
        }
        if (param.equalsIgnoreCase("owllackextrarecs")) {
            return true;
        }
        return false;

    }

    String[] doTopicFlag(String param) {
        // not sure that this does anything that isn't taken care of by the games cateogory changing
        // phonics as in phonic sounds or phonic words
        if (param.equalsIgnoreCase("phonics")) {
            return new String[]{TOPICFLAGPHONICS, TOPICFLAGCONDITION_REQUIRE};
        }
        if (param.equalsIgnoreCase("notphonics")) {
            return new String[]{TOPICFLAGPHONICS, TOPICFLAGCONDITION_NO};
        }
        if (param.equalsIgnoreCase("phonicsw")) {
            return new String[]{TOPICFLAGPHONICSW, TOPICFLAGCONDITION_REQUIRE};
        }
        if (param.equalsIgnoreCase("notphonicsw")) {
            return new String[]{TOPICFLAGPHONICSW, TOPICFLAGCONDITION_NO};
        }
        // the games hunt / trains / Noahs's ark (syllables have this)
        // only diff I can see at the moment is that they require all the
        // long words to be split, not just 4 (as with balloons etc)
        if (param.equalsIgnoreCase("needsyllsplit")) {
            return new String[]{TOPICFLAGSYLLSPLIT, TOPICFLAGCONDITION_REQUIRE};
        }
        // not sure that the now does anything different to "needsyllsplit"  
        if (param.equalsIgnoreCase("needanysplit")) {
            return new String[]{TOPICFLAGANYSPLIT, TOPICFLAGCONDITION_REQUIRE};
        }
        // used by jigsaw and jigsaw shredder
        if (param.equalsIgnoreCase("needonset")) {
            return new String[]{TOPICFLAGONSET, TOPICFLAGCONDITION_REQUIRE};
        }

        // DOESN'T SEEM TO BE USED - no game with flag "usepattern"
        if (param.equalsIgnoreCase("usepattern")) {
            return new String[]{TOPICFLAGPATTERN, TOPICFLAGCONDITION_REQUIRE};
        }
        // for the games which need special input e.g. crosswords etc    
        if (param.equalsIgnoreCase("special")) {
            return new String[]{TOPICFLAGSPECIAL, TOPICFLAGCONDITION_REQUIRE};
        }
        // yet to test start ----------------------------------------------
        // needs a list of bad words for rime, i.e. TOPICBLOCKDISTRACTORNONSENSERIME_TYPE
        if (param.equalsIgnoreCase("needbad")) {
            return new String[]{TOPICFLAGBAD, TOPICFLAGCONDITION_REQUIRE};
        }
        if (param.equalsIgnoreCase("pairedwords")) {
            return new String[]{TOPICFLAGPAIRED, TOPICFLAGCONDITION_REQUIRE};
        }
        // standard list is made up of nothing but pairs of AllOrNones
        if (param.equalsIgnoreCase("justpairedwords")) {
            return new String[]{TOPICFLAGONLYALLORNONE, TOPICFLAGCONDITION_REQUIRE};
        }
        // not if standard list is only paired words
        if (param.equalsIgnoreCase("notpairedwords")) {
            return new String[]{TOPICFLAGONLYPAIRED, TOPICFLAGCONDITION_NO};
        }
        if (param.equalsIgnoreCase("nosingleletters")) {
            return new String[]{TOPICFLAGSINGLETTERS, TOPICFLAGCONDITION_NO};
        }
        if (param.equalsIgnoreCase("notifdups")) {
            return new String[]{TOPICFLAGDUPLICATES, TOPICFLAGCONDITION_NO};
        }
        if (param.equalsIgnoreCase("notdefinitions")) {
            return new String[]{TOPICFLAGDESCRIPTION, TOPICFLAGCONDITION_NO};
        }
        if (param.equalsIgnoreCase("notblended")) {
            return new String[]{TOPICFLAGBLENDED, TOPICFLAGCONDITION_NO};
        }
        if (param.equalsIgnoreCase("needpictures")) {
            return new String[]{TOPICFLAGPICTURES, TOPICFLAGCONDITION_REQUIRE};
        }
        // yet to test end ----------------------------------------------

        if (param.equalsIgnoreCase("multisyllable")) {
            return new String[]{TOPICFLAGMULTISYLLABLE, TOPICFLAGCONDITION_REQUIRE};
        }
        if (param.equalsIgnoreCase("notmultisyllable")) {
            return new String[]{TOPICFLAGMULTISYLLABLE, TOPICFLAGCONDITION_NO};
        }
        if (param.equalsIgnoreCase("flonly")) {
            return new String[]{TOPICFLAGFL, TOPICFLAGCONDITION_REQUIRE};
        }
        if (param.equalsIgnoreCase("usesphonicdistractors")) {
            return new String[]{TOPICFLAGPHONICDISTRACTORS, TOPICFLAGCONDITION_USES};
        }
        if (param.equalsIgnoreCase("owlneedrec")) {
            return new String[]{TOPICFLAGOWLRECORDINGS, TOPICFLAGCONDITION_REQUIRE};
        }
        return null;

    }

    public Document getXMLDocument(File file) {
        Document doc = null;
        if (!file.exists()) {
            return null;
        }
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            dbf.setValidating(false);
            doc = db.parse(file);
            return doc;
        } catch (Exception trans) {
            int hh;
            hh = 8;
        }
        return null;
    }

    static String getMainCategoryText(int i, boolean getFullMainCategory) {
        String pre = getFullMainCategory ? TOPICMAINCATEGORY + "_" : "";
        if (i == 0) {
            return pre + "NONPHONICS";
        } else if (i == 1) {
            return pre + "PHONICSOUNDS";
        } else if (i == 2) {
            return pre + "PHONICS";
        } else {
            return pre + "PHONICSENTENCES";
        }
    }
    
    //_________________________________________________________________________
    
    
    
    JSONObject getTopicJsonForUpload(treeDetails tree, int unitId, int lastTopicIndex, String courseName) {        
        JSONObject topicObject = new JSONObject();
   
        currStandardWords = getStandardWords(tree.st);
        
        System.out.println("....Doing standard list");
        JSONArray standard = getStandard(tree, UPLOAD_TYPE_STANDARD);
        if(!standard.isEmpty()){
            topicObject.put(UPLOAD_TYPE_STANDARD, standard);
        }        

        System.out.println("....Doing extended list");
        JSONObject extended = getExtended(tree, UPLOAD_TYPE_EXTENDED);
        if(!extended.isEmpty()){
            topicObject.put(UPLOAD_TYPE_EXTENDED, extended);
        }
        
        System.out.println("....Doing helicopter listen");
        JSONObject helicopterListen = getHeadingGameContent(tree, UPLOAD_TYPE_HELICTOPER_LISTEN);
        if(!helicopterListen.isEmpty()){
            topicObject.put(UPLOAD_TYPE_HELICTOPER_LISTEN, helicopterListen);
        }  
        
        System.out.println("....Doing helicopter spell");
        JSONObject helicopterSpell = getHeadingGameContent(tree, UPLOAD_TYPE_HELICTOPER_SPELL);
        if(!helicopterSpell.isEmpty()){
            topicObject.put(UPLOAD_TYPE_HELICTOPER_SPELL, helicopterSpell);
        }  
        
        System.out.println("....Doing pairs");
        JSONObject pairs = getPairsBlock(tree, UPLOAD_TYPE_PAIRS);
        if(!pairs.isEmpty()){
            topicObject.put(UPLOAD_TYPE_PAIRS, pairs);
        }             
      
        System.out.println("....Doing pattern");
        JSONObject pattern = getHeadingGameContent(tree, UPLOAD_TYPE_PATTERN);
        if(!pattern.isEmpty()){
            topicObject.put(UPLOAD_TYPE_PATTERN, pattern);
        }
        
        System.out.println("....Doing phonic distractors");
        JSONObject phonicDistractors = getPhonicDistractorsContent(tree, UPLOAD_TYPE_PHONIC_DISTRACTORS);
        if(!phonicDistractors.isEmpty()){
            topicObject.put(UPLOAD_TYPE_PHONIC_DISTRACTORS, phonicDistractors);
        }  

        System.out.println("....Doing sentence crossword");
        JSONObject sentenceCrossword = getSentenceContent(tree, UPLOAD_TYPE_SENTENCES);
        if(!sentenceCrossword.isEmpty()){
            topicObject.put(UPLOAD_TYPE_SENTENCES, sentenceCrossword);
        }    
        
        System.out.println("....Doing simple crossword");
        JSONObject simpleSentence = getSentenceContent(tree, UPLOAD_TYPE_SENTENCES_SIMPLE);
        if(!simpleSentence.isEmpty()){
            topicObject.put(UPLOAD_TYPE_SENTENCES_SIMPLE, simpleSentence);
        }
        System.out.println("....Doing topic details");
        topicObject.put(
                UPLOAD_TYPE_WORDLIST, 
                getTopicDetails(
                    tree, 
                    unitId, 
                    lastTopicIndex,
                    !standard.isEmpty(),
                    !extended.isEmpty(),
                    courseName
                )
        );
        
        return topicObject;
    }

    JSONObject getTopicDetails(treeDetails tree, int unitId, int lastTopicIndex, boolean hasStandard, boolean hasExtended, String courseName) {
        JSONObject object = new JSONObject();
        object.put("unit_id", unitId); 
        object.put("name", getTopicName(tree.st));
        object.put("description", getTopicDetail(tree, GTX_TEACHINGNOTE, false));
        object.put("settings", getTopicSettings(tree));
        String gameOptions = doGameOptions(tree.st);
        object.put("game_settings", gameOptions == null ? null : gameOptions.toString());
        object.put("wl_order", lastTopicIndex);
        object.put("is_unit_revision", tree.t.unitrevision ? 1 : 0);
        object.put("is_revisionlist", Integer.parseInt(getTopicDetail(tree, GTX_REVISION, true)));
        object.put("exclude_ap", getTopicDetail(tree, topic.types[topic.APNOTINUNITORTEST], true));
        object.put("exclude_unittest", getTopicDetail(tree, topic.types[topic.APNOTINTEST], true));
        object.put("ap_priority", getAPPriority(tree));
        object.put("has_standard", hasStandard);
        object.put("has_extended", hasExtended);
        object.put("game_category_id", ((tree.t.phonics && !tree.t.phonicsw) ? 1 : 2));
        
        if(courseName.equalsIgnoreCase(WORDSHARKTESTCOURSE)){
            int sharkChallengePlacementUnitIndex = getPlacementUnitIndex(unitId, lastTopicIndex);
            if(sharkChallengePlacementUnitIndex >= 0){
                object.put("placement_unit_index", sharkChallengePlacementUnitIndex);
            }
            
            int sharkChallengeCompletedUnitIndex = getSharkChallengeCompletedUnitIndex(unitId, lastTopicIndex);
            if(sharkChallengeCompletedUnitIndex >= 0){
                object.put("completed_unit_index", sharkChallengeCompletedUnitIndex);
            }
        }        

        ArrayList mergedGamesResults = getMergedGames(tree);
        String mergedGames = (String)mergedGamesResults.get(0);
        String mergedRecommendedGames = (String)mergedGamesResults.get(1);
        
        object.put("available_games", getGameReferenceArrayFromCsv(mergedGames));
        if(!mergedRecommendedGames.equals("")){
            object.put("suggested_games", getGameReferenceArrayFromCsv(mergedRecommendedGames));
        }
     
        return object;
    }
    
    int getPlacementUnitIndex(int unitOrder, int wordlistOrder) {
        return getSharkChallengePlacementUnitIndex(unitOrder, wordlistOrder);
    }  

    int getCompletedUnitIndex(int unitOrder, int wordlistOrder) {
        return getSharkChallengeCompletedUnitIndex(unitOrder, wordlistOrder);
    }      
    
    ArrayList getMergedGames(treeDetails tree) {
        ArrayList arrayList = new ArrayList();
        int PH_ON = 0;
        int PH_OFF = 1;
        MYSQLGameFiltering = true;
        int availablegamescats[] = null;
        if (tree.t.phrases) {  //captions
            availablegamescats = new int[]{CAT_PHRASES};
            currCat = CAT_PHRASES;
        } else if (tree.t.phonics && !tree.t.phonicsw) {  // sounds
            availablegamescats = new int[]{CAT_SOUNDS};
            currCat = CAT_SOUNDS;
        } else if (!tree.t.phonicsw || tree.t.notphonics) {  // nonphonic
            availablegamescats = new int[]{CAT_NONPHONICS};
            currCat = CAT_NONPHONICS;
        } else if (tree.t.justphonics) {  // justphonic
            availablegamescats = new int[]{CAT_PHONICS};
            currCat = CAT_PHONICS;
        } else {
            availablegamescats = new int[]{CAT_NONPHONICS, CAT_PHONICS};
        }
        int[] gameSets = new int[]{PH_ON, PH_OFF};
        String gameSetsResults[] = new String[gameSets.length];
        for (int i = 0; i < gameSets.length; i++) {
            currGameIds = null;
            boolean inphonics = false;
            if (i == PH_ON) {
                inphonics = true;
            } else if (i == PH_OFF) {
                inphonics = false;
            }
            //gather games
            thedoloop:
            for (int k = 0; k <= 1; k++) {
                // if word list isn't able to be in a phonics mode, can't do any phonics-on set
                if (inphonics && !u.inlist(availablegamescats, CAT_PHONICS)) {
                    continue thedoloop;
                }
                sharkStartFrame.mainFrame.wordTree.font = null;
                sharkStartFrame.mainFrame.currPlayTopic = t;
                if (availablegamescats.length > 1) {
                    currCat = inphonics ? CAT_PHONICS : CAT_NONPHONICS;
                }
                wordlist.usephonics = inphonics;
                sharkStartFrame.mainFrame.wordTree.setup(tree.t, null);
                if (inphonics) {
                    student.setOption("s_usephonics");
                } else {
                    student.clearOption("s_usephonics");
                }
                wordlist.splitsInDevMode = true;
                for (int m = 0; tree.t.splitwords != null && m < tree.t.splitwords.length; m++) {
                    String s2 = tree.t.splitwords[m];
                    int kk;
                    if ((kk = s2.indexOf("=")) >= 0) {
                        s2 = s2.substring(0, kk);
                    }
                    s2 = s2.replace("/", "");
                    s2 = s2.replace(u.phonicsplits, "");

                }
                String s = tree.topicTree.getCurrentTopicPath();
                sharkStartFrame.mainFrame.studentList[sharkStartFrame.mainFrame.currStudent].currTopic = s;
                sharkStartFrame.mainFrame.setTopicList(course, tree.jn.get().substring(1));
                sharkStartFrame.mainFrame.setCourseListSelection(course);
                sharkStartFrame.mainFrame.setupGametree();
            }
            String gamesetres = null;

            int excludeGameIds[] = new int[max19CharGames.length];
            for (int ii = 0; ii < excludeGameIds.length; ii++) {
                excludeGameIds[ii] = getGameID(max19CharGames[ii]);
            }
            for (int p = 0; currGameIds != null && p < currGameIds.length; p++) {
                if (gamesetres == null) {
                    gamesetres = "";
                }
                if (currGameIds[p] != 3) {
                    if (longestWord > 19 && u.inlist(excludeGameIds, currGameIds[p])) {
                        continue;
                    }
                }
                if (u.inlist(disabledGames, currGameIds[p])) {
                    continue;
                }
                if (p > 0 && gamesetres.length() > 0) {
                    gamesetres += ",";
                }
                gamesetres += String.valueOf(currGameIds[p]);
            }
            if (gamesetres != null && gamesetres.length() == 0) {
                gamesetres = "-1";
            }
            gameSetsResults[i] = gamesetres;

        }
        String mergedGames = null;
        mergedGames = mergedGameIds2(gameSetsResults[PH_ON], gameSetsResults[PH_OFF], tree.t.singlesound);
        String mergedRecommendedGames = getRecommededGameIds(u.splitString(mergedGames, ","), tree.t.markgames, tree.t.markgamescode);

        // strip out games from recommended which aren't in the available games
        String mergedGamesArray[] = u.splitString(mergedGames, ',');
        String mergedRecommendedGamesArray[] = u.splitString(mergedRecommendedGames, ',');
        String newRecommendedGames[] = new String[0];
        for (int i = 0; i < mergedRecommendedGamesArray.length; i++) {
            if (u.findString(mergedGamesArray, mergedRecommendedGamesArray[i]) >= 0) {
                newRecommendedGames = u.addString(newRecommendedGames, mergedRecommendedGamesArray[i]);
            }
        }
        mergedRecommendedGames = u.combineString(newRecommendedGames, ",");        
        
        arrayList.add(mergedGames);
        arrayList.add(mergedRecommendedGames);
        return arrayList;
    }       

    int getAPPriority(treeDetails tree) {
        String apPriority1 = getTopicDetail(tree.st, topic.types[topic.APPRIORITY1], true);
        String apPriority2 = getTopicDetail(tree.st, topic.types[topic.APPRIORITY2], true);
        int apPriority = 0;
        if (apPriority1.equals("1")) {
            apPriority = 1;
        }
        if (apPriority2.equals("1")) {
            apPriority = 2;
        }
        return apPriority;
    }   
    
    String getTopicSettings(treeDetails tree) {
        String settingKeys[] = new String[]{"Homophones", "InOrder", "ForcePhonics", "DisablePhonics", "StartPhonics", "Nonsense"};
        String settingValues[] = getTopicSettings(tree.topicTree, null, tree.st, -1);
        phonicshomo_on = (tree.t.phonics && !tree.t.phonicsw && tree.t.singlesound);
        if (tree.t.phonics && !tree.t.phonicsw && tree.t.singlesound) {
            // the homophone setting
            settingValues[0] = "1";  
        }
        String topicSettings = getSingleArrayJson(settingKeys, settingValues); 
        JSONParser parser = new JSONParser();  
        JSONObject settings = null;
        try{
            settings = (JSONObject) parser.parse(topicSettings); 
        }
        catch(Exception e){}
            
        return settings.toString();
    }    
    
    JSONArray getStandard(treeDetails tree, String uploadType) {
        JSONArray words = new JSONArray();
        word[] ww = getStandardWords(tree.st);
        for (int i = 0; ww != null && i < ww.length; i++) {;
            // the sentences for the Wordshark Test course
            if (ww[i].value.startsWith(sentence.TEST_PREFIX)) {
                continue;  
            }
            if (ww[i].value.startsWith("(")) {
                continue;
            }
            words.add(getWordReference(tree, new word(ww[i].value.toLowerCase(),"publictopics"), -1, uploadType));
        }     
        return words;
    }
    
    JSONObject getSentenceContent(treeDetails tree, String uploadType) {
        JSONObject object = new JSONObject();
        JSONArray selects = getSentenceSubSelect(tree, 1, uploadType);

        if(selects.isEmpty()){
            return object;
        }
        object.put("top", getGroupOneTop());
        object.put("selects", selects);
        
        JSONObject sentences = new JSONObject();
        sentences.put("desktopSelectIndex", ((JSONObject)selects.get(0)).get("desktopSelectIndex"));  
        sentences.put("sentences", getSentenceReferences(tree, selects, 1, uploadType));  
        JSONArray references = new JSONArray();
        
        references.add(sentences);
        object.put("references", references);
        
        return object;
    }
    
    JSONArray getSentenceReferences(treeDetails tree, JSONArray selects, int startIndex, String uploadType) {
        JSONArray references = new JSONArray();
        for (int j = 0; j < selects.size(); ++j) {
            JSONObject select = (JSONObject)selects.get(j);
            int selectedIndex = (int)select.get("desktopSelectIndex");
            int postSelectIndex = selectedIndex + 1;
            int startLevel = tree.st.curr.levels[postSelectIndex];
            
            String allSentenceTargetWords[] = getAllSentenceTargetWords(postSelectIndex, tree.st.curr.levels[postSelectIndex], tree.st, t.phrases);

            for (int i = postSelectIndex; i < tree.st.curr.levels.length; ++i) {     
                if(tree.st.curr.levels[i]<startLevel){
                    break;
                }
                JSONObject reference = getSentenceReference(tree, i, startIndex, uploadType, allSentenceTargetWords);
                references.add(reference);
            }  
        }
        return references;
    }   

    JSONObject getSentenceReference(treeDetails tree, int i, int selectIndex, String uploadType, String[] allSentenceTargetWords) {
        JSONObject subSelect = new JSONObject();
        String sentType = getSentenceType(tree.st.curr.names[i]);
        String sentenceDistractors[] = getSentenceDistractorWords(tree.st.curr.names[i], sentType);

        // Get ALL target words in one array
        String targetWords[] = getSentenceTargetWords(tree.st.curr.names[i], sentType, true);

        // Create case-insensitive set of target words
        Set<String> targetSet = new HashSet<String>();
        for (String target : targetWords) {
            if (target != null) {
                targetSet.add(target.toLowerCase()); // Convert to lowercase for comparison
            }
        }

        // Filter allSentenceTargetWords (case-insensitive)
        List<String> filteredAllWords = new ArrayList<String>();
        for (String word : allSentenceTargetWords) {
            if (word != null && !targetSet.contains(word.toLowerCase())) {
                filteredAllWords.add(word);
            }
        }
        String[] filteredWords = filteredAllWords.toArray(new String[filteredAllWords.size()]);

        // Filter distractors if they exist (case-insensitive)
        if(sentenceDistractors != null){
            List<String> filteredDistractors = new ArrayList<String>();
            for (String word : sentenceDistractors) {
                if (word != null && !targetSet.contains(word.toLowerCase())) {
                    filteredDistractors.add(word);
                }
            }
            String[] filteredDistractorsArray = filteredDistractors.toArray(new String[filteredDistractors.size()]);
            subSelect.put("wrongWords", getSentenceWords(filteredDistractorsArray, null));
        } else {
            subSelect.put("wrongWords", getSentenceWords(filteredWords, null));
        }
        /*
        
        getSentenceTargetImages
        
        */
        sentence sent = (new sentence(tree.st.curr.names[i], null));
        subSelect.put("desktopSelectIndex", selectIndex);
        subSelect.put("sentence", u.formatTextforUpload(sent.stripclozereplacewildcard()));
        
        subSelect.put("rightWords", getSentenceWords(targetWords, getSentenceTargetImages(tree.st.curr.names[i])));
        String sentText = removeSentenceImageSuffix(tree.st.curr.names[i].toLowerCase().replace("|", " "));
        subSelect.put("soundPeep", tor.findJsonRecording(jsonRecResults, getSoundDatabaseForSentenceGame(tree, uploadType, false), sentText));
        if(uploadType == UPLOAD_TYPE_SENTENCES_SIMPLE){   
            String simpleSentence = sent.stripcloze();
            subSelect.put("plain_sentence", u.formatTextforUpload(simpleSentence));
            subSelect.put(
                   "sound", 
                   tor.findJsonRecording(
                        jsonRecResults, 
                        getSoundDatabaseForSentenceGame(tree, uploadType, true), 
                        simpleSentence.toLowerCase().replace("|", " ")
                    )
           ); 
        }
        return subSelect;
    }
    
    String removeSentenceImageSuffix(String sent) {
        int p;
        if ((p = sent.indexOf('{')) > 0) {
            return sent.substring(0, p);
        }     
        return sent;
    }  
    
    String getSoundDatabaseForSentenceGame(treeDetails tree, String uploadType, boolean isFullSentence) {
        if(tree.t.fl){
            return isFullSentence ? "publicsay3" : "publicsent3";
        }
    
        return "publicsent2";
    }      
    
    JSONArray getSentenceWords(String[] words, String[] images) {
        JSONArray wordsArray = new JSONArray();
        for (int i = 0; i < words.length; ++i) {
            JSONObject word = new JSONObject();
            word.put("plainword", words[i].toLowerCase());
            if(images != null){
                word.put("image_file_name", getSentenceWordImageFileName(images[i]));
            }          
            wordsArray.add(word);
        }  
        return wordsArray;
    }
    
    String getSentenceWordImageFileName(String imageName){
        sharkImage si = sharkImage.find(imageName);
        if (si != null) {
            return tor.findJsonImage(jsonImageResults, currImageDb.substring(currImageDb.lastIndexOf(shark.sep) + 1), imageName, true);
        }
        return tor.findJsonImage(jsonImageResults, currImageDb, imageName, false); 
    }

    JSONArray getSentenceSubSelect(treeDetails tree, int startIndex, String uploadType) {
        JSONArray selects = new JSONArray();
        for (int i = startIndex; i < tree.st.curr.levels.length; ++i) {
            if(isGamesNode(tree.st.curr.names[i]) && tree.st.curr.names[i].toLowerCase().contains(getGameNameFromType(uploadType))){
                selects.add(getSentenceSelect(i));
            }
        }  
        return selects;
    }  
    
    JSONObject getSentenceSelect(int startIndex) {
        JSONObject subSelect = new JSONObject();
        subSelect.put("desktopSelectIndex", startIndex);
        subSelect.put("select_count", 1);
        return subSelect;
    } 
    
     JSONObject getPhonicDistractorsContent(treeDetails tree, String uploadType) {
        JSONObject object = new JSONObject();
        JSONArray selects = getPhonicDistractorsSubSelects(tree, 1);
        if(selects.isEmpty()){
            return object;
        }     
        JSONObject top = getGroupOneTop();
        object.put("top", top);
        object.put("selects", selects);
        object.put("references", getPhonicDistractorsReferences(tree, selects, 1, uploadType));       
        return object;
    }
    
    JSONArray getPhonicDistractorsReferences(treeDetails tree, JSONArray selects, int startIndex, String uploadType) {
        JSONArray references = new JSONArray();
        for (int j = 0; j < selects.size(); ++j) {
            JSONObject select = (JSONObject)selects.get(j);
            int selectIndex = (int)select.get("desktopSelectIndex");            
            JSONObject reference = new JSONObject();
            reference.put("sounds", getSoundReferenceArrayFromCsv(tree, getPostColonText(tree.st.curr.names[selectIndex]), uploadType));
            reference.put("desktopSelectIndex", selectIndex);
            references.add(reference);
        }
        return references;
    }      
     
    JSONArray getPhonicDistractorsSubSelects(treeDetails tree, int startIndex) {
        JSONArray selects = new JSONArray();
        for (int i = startIndex; i < tree.st.curr.levels.length; ++i) {
            if(tree.st.curr.levels[startIndex] == tree.st.curr.levels[i] && tree.st.curr.names[i].startsWith("Phonicdistractors")){
                selects.add(getPhonicDistractorsSelect(tree.st, i));
            }
        }  
        return selects;
    }      
    
    JSONObject getPhonicDistractorsSelect(saveTree1 st, int startIndex) {
        JSONObject subSelect = new JSONObject();
        subSelect.put("desktopSelectIndex", startIndex);
        return subSelect;
    }     

    JSONObject getPairsBlock(treeDetails tree, String uploadType) {
        JSONObject object = new JSONObject();
        JSONArray selects = getPairsSubSelects(tree, 1);
        if(selects.isEmpty()){
            return object;
        }
        object.put("top", getGroupOneTop());
        object.put("selects", selects);
        object.put("references", getPairsReferences(tree, selects, 1, uploadType));       
        return object;
    }    
    
    JSONArray getPairsReferences(treeDetails tree, JSONArray selects, int startIndex, String uploadType) {
        JSONArray references = new JSONArray();
        for (int j = 0; j < selects.size(); ++j) {
            JSONObject select = (JSONObject)selects.get(j);
            int index = (int)select.get("desktopSelectIndex");
            JSONObject reference = new JSONObject();
            reference.put("desktopSelectIndex", index);
            JSONArray words = new JSONArray();
            
            int postSelectIndex = index + 1;
            int startLevel = tree.st.curr.levels[postSelectIndex];

            for (int i = postSelectIndex; i < tree.st.curr.levels.length; ++i) { 
                if(tree.st.curr.levels[i]<startLevel){
                    break;
                }
                if(isLeaf(tree.st, i) && !tree.st.curr.names[i].startsWith(topicTree.ISPATH)){
                    words.add(getWordReference(tree, new word(tree.st.curr.names[i].toLowerCase(),"publictopics"), index, uploadType));
                }

            }
            reference.put("words", words);
            
            references.add(reference);
        }
        return references;
    }   

    JSONObject getWordReference(treeDetails tree, word w, int selectIndex, String uploadType) {
        JSONObject reference = new JSONObject();
        reference.put("plainword", w.v());
        
        reference.put("fullword", stripAts(w.value));
        reference.put("patternword", w.pat);  // helicopter/pattern
        
        if(wantResourceProperties(uploadType)){
             String mainSoundFileName = getSoundFileName(tree, w);
            reference.put("sound_file_name", mainSoundFileName);

            String wordsharkImage = getWordsharkImageFileName(w);
            
            if(wordsharkImage != null){
                reference.put("wordshark_image_file_name", wordsharkImage);
            }

            String photoImage = getPhotoImageFileName(w);
            if(photoImage != null){
                reference.put("photo_image_file_name", photoImage);
            }

            String homophoneSound = getHomophoneFileName(tree, w);
            if(homophoneSound != null){
                reference.put("homophone_file_name", homophoneSound);
            }

            if((uploadType == UPLOAD_TYPE_STANDARD || uploadType == UPLOAD_TYPE_EXTENDED) &&
                    w.phonicsw){            
                reference.put("split_sound_file_names", getSoundsForWord(w, mainSoundFileName));
            }   
        }
        
        reference.put("exclude_unit_test", isExcludedFromUnitText(tree, w, uploadType));

        if(selectIndex >= 0){
            reference.put("desktopSelectIndex", selectIndex);
        }
        
        return reference;
    }     
    
    boolean wantResourceProperties(String uploadType) {
        return !uploadType.equals(UPLOAD_TYPE_PATTERN);
    }  
    
    boolean isExcludedFromUnitText(treeDetails tree, word w, String uploadType) {
        boolean isExcuded = false;
        if (uploadType.equals(UPLOAD_TYPE_STANDARD)|| tree.t.revision) {
            Object o[] = topic.getExcludedWord(u.absoluteToRelative(sharkStartFrame.publicTopicLib[0]),
                    tree.topicTree.getAncestors(tree.jn, u.absoluteToRelative(sharkStartFrame.publicTopicLib[0])),
                    w.v());
            isExcuded = (int) (o[0]) >= 0;
        }
        if (isExcuded && tree.t.revision) {
            return false;
        }
        return false;
    }     
        
    String getWordsharkImageFileName(word w) {        
        return tor.findJsonImage(jsonImageResults, null, w.vpic(), true);
    }       
    
    String getPhotoImageFileName(word w) {
        String imageName = w.vpic();
        if (MYSQLUpload.course.equalsIgnoreCase(WORDSHARKTESTCOURSE)) {
            imageName = u.getPhotoNameInWordsharkTestCourse(imageName);
        }
        return tor.findJsonImage(jsonImageResults, null, w.vpic(), false);
    }      
    
    String getSoundFileName(treeDetails tree, word w) {
        String details[] = getRecordingFileName(tree, w);
        if(details == null){      
            System.out.println("....NO SOUND FOR: " + w.v());
            System.exit(0);
        }
        return tor.findJsonRecording(jsonRecResults, details[1], details[0]);
    }
    
    String getHomophoneFileName(treeDetails tree, word w) {
        String details[] = getRecordingFileName(tree, w);
        return tor.findJsonRecording(jsonRecResults, details[1], details[0] + "=");
    }    
    
    String[] getRecordingFileName(treeDetails tree, word w) {
        String word = w.vsay();
        if (w.phonics && !w.phonicsw) {
            word = w.phonics()[0] + "~";
        }
        String datab = null;
        if (tree.t.fl) {
            datab = "publicsay3";
        }
        for (int i = 0; i < sharkStartFrame.publicSoundLib.length; ++i) {
            if (db.query(sharkStartFrame.publicSoundLib[i], word, db.WAV) >= 0) {
                datab = (new File(sharkStartFrame.publicSoundLib[i])).getName();
                break;
            }
        }
        if (datab == null) {
            return null;
        }
        if (db.query(datab, word, db.WAV) < 0) {
            return null;
        }
        return new String[]{word.toLowerCase(), datab};
    }
    
    JSONArray getSoundsForWord(word w, String wholeWordSoundFileName) {
        JSONArray sounds = new JSONArray();
        String soundsString = getPhonicSplitParts(w, wholeWordSoundFileName);
        String arr[] = u.splitString(soundsString,"|");
        for (int i = 0; i < arr.length; ++i) {
            sounds.add(arr[i]);
        }  
        return sounds;
    }     
    
    JSONArray getPairsSubSelects(treeDetails tree, int startIndex) {
        JSONArray selects = new JSONArray();
        int startLevel = tree.st.curr.levels[startIndex];
        for (int i = startIndex; i < tree.st.curr.levels.length; ++i) {
            if(tree.st.curr.levels[startIndex] == tree.st.curr.levels[i] && tree.st.curr.names[i].startsWith("Pair")){
                selects.add(getPairsSubSelect(tree.st, i));
            }
            if(tree.st.curr.levels[i] < startLevel){
                break;
            }
        }  
        return selects;
    } 
    
    JSONObject getPairsSubSelect(saveTree1 st, int startIndex) {
        JSONObject subSelect = new JSONObject();
        subSelect.put("desktopSelectIndex", startIndex);
        return subSelect;
    }     
    
    JSONObject getHeadingGameContent(treeDetails tree, String uploadType) {
        JSONObject object = new JSONObject();
        for (int i = 0; i < tree.st.curr.levels.length; ++i) {
            if(tree.st.curr.levels[i] == 1){
                if(isGamesNode(tree.st.curr.names[i]) && tree.st.curr.names[i].toLowerCase().contains(getGameNameFromType(uploadType))){
                    object.put("top", getGroupOneTop());
                    selectGameDetails sgd = getSelectGameDetails(tree, uploadType);
                    JSONArray subSelects = getHeadingGameSubSelects(tree, sgd);
                    object.put("selects", subSelects);
                    object.put("references", getHeadingGameReferences(tree, subSelects, sgd, uploadType));
                }
            }
        }         
        return object;
    }  
    
    String getGameNameFromType(String uploadType) {
        switch (uploadType) {
            case UPLOAD_TYPE_HELICTOPER_LISTEN:
                return "helicopter (listen)";
 
            case UPLOAD_TYPE_HELICTOPER_SPELL:
                return "helicopter (spell)";

            case UPLOAD_TYPE_PATTERN:
                return "pattern";
                
            case UPLOAD_TYPE_SENTENCES:
                return "sentence crossword";
                
            case UPLOAD_TYPE_SENTENCES_SIMPLE:
                return "simple crossword";
        }
        return null;
    }   
    
    JSONArray getHeadingGameReferences(treeDetails tree, JSONArray selects, selectGameDetails sgd, String uploadType) {
        JSONArray references = new JSONArray();
        for (int i = 0; i < sgd.groups.length; ++i) {
            selectGameDetails.selectGameBucket sgb = sgd.groups[i];
            JSONObject select = new JSONObject();
            select.put("name", sgb.heading);
            select.put("headingSoundFile", sgb.headingSoundFile);
            select.put("select_count", sgb.selectNo);
            String patterns[] = getPatternsStringsFromSelect((JSONObject)selects.get(i));
            select.put("words", getWordReferenceArrayFromArray(tree, sgb.words, patterns, uploadType));
            select.put("desktopSelectIndex", i);
            references.add(select);
        }
        return references;
    } 


    String[] getPatternsStringsFromSelect(JSONObject select) {
        Object patterns = select.get("patterns");
        String patternStrings[] = new String[0];
        if(patterns != null){
            JSONArray sl = (JSONArray)patterns;
            for (int i = 0; i < sl.size(); ++i) {
                patternStrings = u.addString(patternStrings, (String)((JSONObject)sl.get(i)).get("pattern"));
            }
        }
        return patternStrings;
    }     
    
    JSONArray getHeadingGameSubSelects(treeDetails tree, selectGameDetails sgd) {
        JSONArray selects = new JSONArray();
        for (int i = 0; i < sgd.groups.length; ++i) {
            selectGameDetails.selectGameBucket dd = sgd.groups[i];
            selects.add(getHeadingGameSubSelect(tree, dd, i));
        }  
        return selects.size() > 0 ? selects : null;
    } 
	    
    JSONObject getHeadingGameSubSelect(treeDetails tree, selectGameDetails.selectGameBucket sgb, int index) {
        JSONObject subSelect = new JSONObject();
        JSONArray patterns = getHeadingPatterns(tree, sgb);
        if(!patterns.isEmpty()){
            subSelect.put("patterns", patterns);
        }
        subSelect.put("desktopSelectIndex", index);
        return subSelect;
    }
    
    JSONArray getHeadingPatterns(treeDetails tree, selectGameDetails.selectGameBucket sgb) {
        JSONArray patterns = new JSONArray();
        jnode jn = tree.t.find(tree.t.root, tree.t.HEADING_TEXT+sgb.heading);
        String patternsArray[] = new String[0];
        if(jn != null){
            patternsArray = tree.t.getpatterns(jn);
        }
        for (int i = 0; i < patternsArray.length; ++i) {
            patterns.add(getPatternReference(patternsArray[i]));
        }
        return patterns;
    }
    
    boolean isGamesNode(String node) {     
        return node.startsWith("Games:");
    }
	
    JSONObject getGroupOneTop() {
        JSONObject top = new JSONObject();
        top.put("select_count", 1);
        top.put("group_type", "GROUPSELECT");
        return top;
    }        
     
    JSONObject getExtended(treeDetails tree, String uploadType) {
        JSONObject extendedObject = new JSONObject();
        for (int i = 0; i < tree.st.curr.levels.length; ++i) {
            if(tree.st.curr.levels[i] == 1){
                if(tree.st.curr.names[i].startsWith("Select")){
                    JSONObject extendedTop = getExtendedTop(tree, i);
                    if(extendedTop == null){
                        return null;
                    }
                    
                    int subSelectsStart = i;
                    if(isNodeTextSelect(tree.st.curr.names[subSelectsStart + 1])){
                        subSelectsStart++;
                    }
                    extendedObject.put("top", extendedTop);
                    JSONArray extendedSelects = getExtendedSubSelects(tree, subSelectsStart);
                    extendedObject.put("selects", extendedSelects);
                    extendedObject.put("references", getExtendedReferences(tree, extendedSelects,  subSelectsStart, uploadType));
                }
            }
        }         
        return extendedObject;
    }
    
    JSONArray getExtendedReferences(treeDetails tree, JSONArray selects, int startIndex, String uploadType) {
        JSONArray references = new JSONArray();
        
        for (int j = 0; j < selects.size(); ++j) {
            JSONObject select = (JSONObject)selects.get(j);
            int postSelectIndex = (int)select.get("desktopSelectIndex") + 1;
            int startLevel = tree.st.curr.levels[postSelectIndex];
            int startAllOrNoneLevel = tree.st.curr.levels[postSelectIndex];
            int startSelectNoLevel = tree.st.curr.levels[postSelectIndex];
            String patterns[] = new String[0];
            
            int selectNo = -1;
            String name = null;
            
            for (int i = postSelectIndex; i < tree.st.curr.levels.length; ++i) {
                if(tree.st.curr.levels[i] <= startAllOrNoneLevel){
                    selectNo = -1;
                }
                if(tree.st.curr.levels[i] <= startSelectNoLevel){
                    name = null;
                }                
                if(isLeaf(tree.st, i)){
                    if(tree.st.curr.names[i].indexOf("*") >=0){
                        patterns = u.addString(patterns, tree.st.curr.names[i]);
                    }
                    else{
                        JSONObject reference = getWordReferences(tree, i, select, patterns, name, selectNo, uploadType);
                        i = (int)reference.get("desktoplastIndex");
                        reference.remove("desktoplastIndex");
                        references.add(reference);                   
                    }
                }
                else{
                    if(tree.st.curr.names[i].startsWith("Select")){
                        startSelectNoLevel = tree.st.curr.levels[i];
                        selectNo = getSelectNoFromSelectString(tree.st.curr.names[i]);               
                    }                
                    if(tree.st.curr.names[i].startsWith("All or none")){
                        startAllOrNoneLevel = tree.st.curr.levels[i];
                        name = "All or none";
                    }                   
                }
                if(tree.st.curr.levels[i]<startLevel){
                    break;
                }
            }  
        }
        return references;
    }
    
    JSONArray getGameReferenceArrayFromCsv(String csv) {
        JSONArray references = new JSONArray();
        String stringArray[] = u.splitString(csv, ',');
        for (int i = 0; i < stringArray.length; ++i) {
            references.add(getGameReference(stringArray[i]));
        }
        return references;
    }
    
    JSONObject getGameReference(String gameId) {
        JSONObject reference = new JSONObject();
        reference.put("gameId", gameId);
        return reference;
    }  
    
    JSONObject getPatternReference(String pattern) {
        JSONObject reference = new JSONObject();
        reference.put("pattern", pattern);
        return reference;
    } 
    
    JSONArray getSoundReferenceArrayFromCsv(treeDetails tree, String csv, String uploadType) {
        JSONArray references = new JSONArray();
        String stringArray[] = u.splitString(csv, ',');
        for (int i = 0; i < stringArray.length; ++i) {
            references.add(getSoundFileName(stringArray[i] + "~"));
        }
        return references;
    }
    
    
    String getSoundFileName(String sound) {
        String s1 = tor.findJsonRecording(jsonRecResults, "publicsay1", sound);
        if (s1 == null) {
            s1 = tor.findJsonRecording(jsonRecResults, "publicsay1", sound.toLowerCase());
        }
        return s1;
    }     
    
    JSONArray getWordReferenceArrayFromArray(treeDetails tree, String[] stringArray, String[] patterns, String uploadType) {
        JSONArray references = new JSONArray();
        for (int i = 0; i < stringArray.length; ++i) {
            word word = new word(stringArray[i].toLowerCase(),"publictopics");
            String patternWord = getTargetWithAnyPattern(word.v(), patterns);
            if(patternWord != null && !patternWord.equals(word.v())){
                word.pat = patternWord;
            }
            references.add(getWordReference(tree, word, -1, uploadType));
        }
        return references;
    }       
    
    JSONObject getWordReferences(treeDetails tree, int startIndex, JSONObject select, String[] patterns, String name, int selectNo, String uploadType) {
        JSONObject reference = new JSONObject();
        int startLevel = tree.st.curr.levels[startIndex];
        int i = startIndex;
        JSONArray references = new JSONArray();
        for (; i < tree.st.curr.levels.length; ++i) {
            if(tree.st.curr.levels[i] != startLevel){
                break;
            }
            word words[] = getLeafReferences(tree, patterns, tree.st.curr.names[i], uploadType);
            for (int j = 0; j < words.length; j++) {
                references.add(getWordReference(tree, words[j], -1, uploadType)); 
            }
        }
        if(name != null){
            reference.put("name", name);
        }
        reference.put("words", references);
        if(selectNo > 0){
           reference.put("select_count", selectNo); 
        }
        reference.put("desktopSelectIndex", (int)select.get("desktopSelectIndex"));
        reference.put("desktoplastIndex", i--);
        return reference;
    }     
    
    
    word[] getLeafReferences(treeDetails tree, String[] patterns, String nodeText, String uploadType) {
        word words[] = new word[0];
        if(!nodeText.startsWith(topicTree.ISTOPIC) && !nodeText.startsWith(topicTree.ISPATH)){
            words = u.addWords(words, new word(nodeText.toLowerCase(),"publictopics"));  
        }
        else{
            boolean topicWantsExtended = nodeText.endsWith("+");
               
            boolean wantExtendedListFromReference = 
                    uploadType == UPLOAD_TYPE_HELICTOPER_LISTEN || 
                    uploadType == UPLOAD_TYPE_HELICTOPER_SPELL || 
                    topicWantsExtended;
           
            topic tts[] = getTopicsFromReference(nodeText);
            word www[] = getWordsFromTopicsReference(tts, patterns, wantExtendedListFromReference);
            for (int i = 0; i < www.length; i++) {
                words = u.addWords(words, new word(www[i].v(),"publictopics"));
            }
        }            
        return words;
    } 
    
    word[] getWordsFromTopicsReference(topic[] topics, String[] patterns, boolean wantExtended) {
        word www[] = new word[]{};
        for (int i = 0; i < topics.length; i++) {
            www = u.addWords(www, topics[i].getAllWords(wantExtended, true));
        }

        for (int i = www.length - 1; i >= 0; i--) {
            if(null == getTargetWithAnyPattern2(www[i].v(), patterns)){
                www = u.removeword(www, i);
            }
        }

        return www;
    }    
    
    topic[] getTopicsFromReference(String nodeText) {
        topic tts[] = new topic[]{};
        if(nodeText.startsWith(topicTree.ISPATH)){  
            topic topics[] = topicTree.getTopics(nodeText);
            for (int it = 0; it < topics.length; ++it) {
                tts = u.addTopic(tts, topics[it]);
            }
        }
        else{ 
            tts = u.addTopic(tts, new topic(u.absoluteToRelative(sharkStartFrame.publicTopicLib[0]), adjustNodeText(nodeText), null, null));
        }
        return tts;
    }      
  
    String adjustNodeText(String nodeText) {
        String strpt = "«publictopics»";
        nodeText = nodeText.substring(strpt.length());
        if(nodeText.endsWith("+")) {
            return nodeText.substring(0, nodeText.length() - 1);
        }
        return nodeText;
    }
    
    boolean isLeaf(saveTree1 st, int startIndex) {
        for (int i = startIndex; i < st.curr.levels.length; ++i) {
            if(st.curr.levels[i]<st.curr.levels[startIndex]){
                return true;
            }
            if(st.curr.levels[i]>st.curr.levels[startIndex]){
                return false;
            }
        }
        return true;
    } 
    
    JSONArray getExtendedSubSelects(treeDetails tree, int startIndex) {
        JSONArray selects = new JSONArray();
        int startLevel = tree.st.curr.levels[startIndex];
        for (int i = startIndex; i < tree.st.curr.levels.length; ++i) {
            if(tree.st.curr.levels[startIndex] == tree.st.curr.levels[i] && isNodeTextSelect(tree.st.curr.names[i])){
                selects.add(getExtendedSubSelect(tree.st, i));
            }
            if(tree.st.curr.levels[i] < startLevel){
                break;
            }
        }  
        return selects.size() > 0 ? selects : null;
    } 
    
    
    boolean isNodeTextSelect(String nodeText) {
        return nodeText.startsWith("Select");
    }    
    
    
    JSONObject getExtendedSubSelect(saveTree1 st, int startIndex) {
        JSONObject subSelect = new JSONObject();
        subSelect.put("select_count", getSelectNoFromSelectString(st.curr.names[startIndex]));
        subSelect.put("desktopSelectIndex", startIndex);
        return subSelect;
    }     
    
    JSONObject getExtendedTop(treeDetails tree, int startIndex) {
        JSONObject top = new JSONObject();
        boolean added = false;
        int selectNo = getSelectNoFromSelectString(tree.st.curr.names[startIndex]);
        if(selectNo > 0){
            top.put("select_count", selectNo);
            added = true;
        }
        String selectType = getSelectTypeFromSelectString(tree.st.curr.names[startIndex]);   
        if(selectType != null){
            top.put("group_type", selectType);
            added = true;
        }
        if(!added){
            return null;
        }
        return top;
    } 
     
     int getSelectNoFromSelectString(String selectString) {
         String[] splitString = u.splitString(selectString, ':');
         if(splitString.length == 1){
             return -1;
         }
         return Integer.parseInt(splitString[splitString.length-1]);
    } 
     
     String getPostColonText(String string) {
         String[] splitString = u.splitString(string, ':');
         if(splitString.length == 1){
             return null;
         }
         return splitString[splitString.length-1];
    } 
     
     String getSelectTypeFromSelectString(String selectString) {
         if(selectString.contains("Select words")){
             return "WORDSELECT";
         }

         if(selectString.contains("Select groups")){
             return "GROUPSELECT";
         }

         return null;
    } 
     
    String getTopicName(saveTree1 st) {
        for (int i = 0; i < st.curr.levels.length; ++i) {
            if(st.curr.levels[i] == 0){
                return st.curr.names[i];
            }
        }         
        return null;
    }  
    
    selectGameDetails getSelectGameDetails(treeDetails tree,String uploadType) {
        String ngs[] = new String[1];
        
        ngs[0] = getGameNameFromType(uploadType);   

        if (uploadType.equals(UPLOAD_TYPE_HELICTOPER_LISTEN)
            || uploadType.equals(UPLOAD_TYPE_HELICTOPER_SPELL)
            || uploadType.equals(UPLOAD_TYPE_PATTERN)) {
                int selectNoTarget = -1;
                int selectNoDistractor = -1;
                int allocNoGood = -1;
                int allocNoBad = -1;
                if (uploadType.equals(UPLOAD_TYPE_PATTERN)) {
                    selectNoTarget = selectCountTargetNoPattern;
                    selectNoDistractor = selectCountDistractorNoPattern;
                    allocNoGood = pattern.ALLOCGOOD;
                    allocNoBad = pattern.ALLOCBAD;
                }
                tree.t.clearHeadingLists();
                return tree.t.getSelectGameBuckets(ngs,
                    uploadType.equals(UPLOAD_TYPE_HELICTOPER_LISTEN) || uploadType.equals(UPLOAD_TYPE_HELICTOPER_SPELL),
                    selectNoTarget,
                    selectNoDistractor,
                    allocNoGood,
                    allocNoBad,
                    this
                    )[0];
            }
            return null;
        }
    
        String getTopicDetail(treeDetails tree, String key, boolean returnBool) {
            for (int j = 0; j < tree.st.curr.names.length; ++j) { //For 2.1
                if (tree.st.curr.names[j].startsWith(GTX_TEACHINGNOTE) && key.equals((GTX_TEACHINGNOTE))) {
                    int k = j + 1;
                    String tnote = tree.st.curr.names[j].substring(key.length());
                    while (k < tree.st.curr.names.length && tree.st.curr.names[k].startsWith(GTX_TEACHINGNOTE)) {
                        tnote += " " + tree.st.curr.names[k].substring(key.length());
                        k++;
                    }
                    return u.setTextHtmlFormattedForUpload(tnote);
                } else if (tree.st.curr.levels[j] == 1 && tree.st.curr.names[j].startsWith(key)) {
                    if (tree.st.curr.names[j].trim().length() <= key.length()) {
                        return "1";
                    }
                    return tree.st.curr.names[j].substring(key.length());
                }
            }
            return returnBool ? "0" : null;
        }
        
        String toSafeFilename(String input) {
            // Replace illegal characters with underscore
            String safe = input.replaceAll("[\\\\/:*?\"<>|]", "_");

            // Trim trailing periods or spaces (Windows does not allow them)
            safe = safe.replaceAll("[\\. ]+$", "");

            // Optionally trim leading spaces
            safe = safe.trim();

            return safe;
        }
        
        int getTopicCount(jnode node){
            jnode[] units = node.getChildren();
            int count = 0;
            for (int i = 0; i < units.length; i++) {
                jnode[] wordlists = units[i].getChildren();
                for (int j = 0; j < wordlists.length; j++) {

                    if(!wordlists[j].get().trim().equals("")){
                        count++;
                    }
                }
            }
            return count;
        }
        
    String getTargetWithAnyPattern2(String wval, String ss[]) {
        if (ss == null || ss.length == 0) {
            return wval;
        }
        for (int i = 0; i < ss.length; i++) {
            boolean b[] = wordlist.fitsPattern(wval, ss[i], new boolean[wval.length()], 0);
            boolean found = false;
            for (int j = 0; j < b.length; j++) {
                if (b[j]) {
                    found = true;
                    break;
                }
            }
            if (found) {
                String s = wval;
                boolean rightBracketNext = true;
                for (int j = b.length - 1; j >= 0; j--) {
                    if (rightBracketNext && b[j]) {
                        s = s.substring(0, j + 1) + "]" + s.substring(j + 1);
                        rightBracketNext = false;
                    }
                    if (!rightBracketNext && !b[j]) {
                        s = s.substring(0, j + 1) + "[" + s.substring(j + 1);
                        rightBracketNext = true;
                    }
                }
                if (!rightBracketNext) {
                    s = "[" + s;
                }
                return s;
            }
        }
        return null;
    }        
    
    private class treeDetails {
        public topic t;
        public saveTree1 st;
        public topicTree topicTree;
        public jnode jn;
            
        treeDetails(topic tParam, saveTree1 stParam, topicTree topicTreeParam, jnode jnParam) {
            t = tParam;
            st = stParam;
            topicTree = topicTreeParam;
            jn = jnParam;
        }
    }
}
