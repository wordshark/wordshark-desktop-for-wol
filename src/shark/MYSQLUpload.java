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
/**
 *
 * @author paulr
 */
public class MYSQLUpload {

    final static int MODE_VIA_DUMP = 0;
    final static int MODE_DIRECT = 1;
    
    final static int CURRENT_MODE = MODE_DIRECT;
    static String accessToken = null;
    static int currentEnvironment = -1;
    static String currentCourse = null;
    
    static JSONArray insertsJson = new JSONArray();
    
    public class apiConfig{
      String accessUrl = null;
      String accessToken = null;
      String accessGrantType = "password";
      String accessClientId = null;
      String accessSecret = null;
      String accessScope = "*";
      String accessUserName = null;
      String accessPassword = null;
      
      public apiConfig(
              String accessUrlP,
              String accessClientIdP,
              String accessSecretP,
              String accessUserNameP,
              String accessPasswordP
              ){
          accessUrl = accessUrlP;
          accessClientId = accessClientIdP;
          accessSecret = accessSecretP;
          accessUserName = accessUserNameP;
          accessPassword = accessPasswordP;

       }   
    }
    
    final apiConfig[] API_CONFIGS = new apiConfig[]{
        new apiConfig(
                "http://course-api.onwordshark.local/oauth/token",
                "2",
                "Xv9EUOiayTVU6utfgHc6NM48JFOReSy4FwFzmuBs",
                "platformnoreply@onwordshark.com",
                "X4HvBt6%BuZ=q&nB"
        ),
        new apiConfig(
                "https://course-api-staging.onwordshark.com/oauth/token",
                "2",
                "Xv9EUOiayTVU6utfgHc6NM48JFOReSy4FwFzmuBs",
                "platformnoreply@onwordshark.com",
                "X4HvBt6%BuZ=q&nB"
        ),        
        new apiConfig(
                "https://course-api.onwordshark.com/oauth/token",
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
    final static String COURSE_LETTERS_AND_SOUNDS = "'Letters and Sounds'";
    final static String COURSE_NAT_CURRICULUM = "English National Curriculum: spellings";
    final static String COURSE_NC_PHONIC_SCREEN = "NC Year 1 Phonics screen";
    final static String COURSE_ALPHA_TO_OMEGA = "'Alpha to Omega'";
    final static String COURSE_EVERYDAY_VOCAB = "Everyday vocabulary";
    final static String COURSE_SECONDARY_SUBJECT = "Secondary school subject lists";
    final static String COURSE_ALPHABET = "Alphabet and dictionary course";
    final static String COURSE_HFW = "High Frequency Words (HFW)";
    final static String COURSE_WORDSHARK_TEST = "Wordshark course test";
    final static String COURSE_DEV_TEST = "DevTest";

    final static String[] ALL_COURSES = new String[]{
        COURSE_WORDSHARK,
        COURSE_SUPPLEMENTARY_LISTS,
        COURSE_LETTERS_AND_SOUNDS,
        COURSE_NAT_CURRICULUM,
        COURSE_NC_PHONIC_SCREEN,
        COURSE_ALPHA_TO_OMEGA,
        COURSE_EVERYDAY_VOCAB,
        COURSE_SECONDARY_SUBJECT,
        COURSE_ALPHABET,
        COURSE_HFW,
        COURSE_WORDSHARK_TEST,
        COURSE_DEV_TEST
    };

    static String apiCalls[] = new String[0];
    
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
    static final String[] GAMESBLOCKSTOIGNORE = new String[]{"crossword 2", "add suffix", "classify", "tracking", "snakes & ladders"};
    static final String[] GAMESTOIGNORE = new String[]{"add suffix", "balance", "balloons", "bingo listen", "bingo words", "build phrase (from spoken)", "build phrase (with picture)", "build word", "chunks", "classify", "crossword 2", "dictionary fish", "fast find", "find phrase", "find sound (for picture)", "flums", "fruit machine", "hidden letter", "holes", "jumbled", "learn", "letter maze", "lottery", "maze alter", "moving", "pairs (linked words)", "pairs (sound+letter)", "say phrase", "scan", "scan (linked words)", "shredder", "snap (linked words)", "split sound", "tilt", "tracking", "trains", "trains (phonics)", "trains (syllables)", "word sort", "save the sharks", "rolling"};
    //   static final String[] GAMESTABSTOIGNORE = new String[]{"crossword 2","add suffix", "classify", "tracking"};
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

    public static boolean uploadImages = false;
    public static boolean restXML = true;
    public static boolean generateImageFiles = false;
    public static sharkImage.saveSharkImage currSaveSharkImage;
    public static String mySqlRudeWords[];
    static long startTime;
    static int attributeCount = -1;
    static int attributeIndexCount = -1;
    static final String SVGIMAGEFOLDER = "C:\\Users\\PaulRubie\\Documents\\NetBeansProjects\\wordshark_desktop_for_wol\\svg";
    static final String RESTXMLFOLDER = "C:\\Users\\PaulRubie\\Documents\\NetBeansProjects\\wordshark_desktop_for_wol\\XML\\restXML";
    static final String RESTJSONFOLDER = "C:\\Users\\PaulRubie\\Documents\\NetBeansProjects\\wordshark_desktop_for_wol\\json_output";
    
    
    static final String WORDSXMLFOLDER = "C:\\Users\\PaulRubie\\Documents\\NetBeansProjects\\wordshark_desktop_for_wol\\XML\\wordsXML";
    static final String BMPIMAGEFOLDERPLUS = "C:\\xampp\\htdocs\\img\\publicimages\\";
    static final String WEBIMAGEFOLDERPLUS = "/img/publicimages/";
    static final String WEBSVGFOLDERPLUS = "/img/svg/";

    String TOBERECORDEDCSV = sharkStartFrame.publicPathplus + "csv" + shark.sep + "toBeRecorded.csv";

    static final String BMPIMAGEEXTENSIONS[] = new String[]{".jpg", ".png"};

    static final String GTX_ROOT_XML = "root";
    static final String GTX_TOPICROOT_XML = "Topic";
    static final String GTX_TBS_XML = "TopicBlocks";
    static final String GTX_TB_XML = "TopicBlock";
    static final String GTX_TB_TYPE_AT_XML = "TBType";
    static final String GTX_REF_TYPE_AT_XML = "RefType";
    static final String GTX_TB_NUM_AT_GAMEBLOCKTYPE = "GameBlockType";

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
    static final String GTX_WORDUUID_AT_XML = "WordUUID";

    static final String GTX_IMUUID_AT_XML = "ImUUID";
    static final String GTX_IMBMPUUID_AT_XML = "ImBMPUUID";
    static final String GTX_RECUUID_AT_XML = "RecUUID";
    static final String GTX_HOMOPHONERECUUID_AT_XML = "HomoRecUUID";
    static final String GTX_SENT_RECUUID_AT_XML = "RecSentUUID";

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

    static final String GTX_TOPICNAME = "GTX_TOPICNAME";
    static final String GTX_TOPICNAME_AT_XML = "TopicName";
    static final String GTX_TOPICUUID_AT_XML = "TopicUUID";
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
    static String[] uploadWordSVG = new String[]{};
    static String[] uploadWordBMPIdentifier = new String[]{};
    static String[] uploadRecordingDetails = new String[]{};
    static int[] uploadRecordingUUID = new int[]{};

    static int[] uploadWordSVGUUID = new int[]{};
    static int[] uploadWordBMPUUID = new int[]{};
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
    static String gameNames[] = new String[]{};
    static String gameCategory[] = new String[]{};
    static int gameID[] = new int[]{};
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
    }

    private String getPhonicsHomoMainRec(String sound, String alreadyRec) {
        String ret = "SOUNDHOMO_" + sound + ".mp3";
        switch (sound) {
            case "c":
                return ret;
            case "k":
                return ret;
            case "x":
                return ret;
            default:
                return alreadyRec;
        }
    }

    private String getPhonicsHomoHomoRec(String sound) {
        String p = "SOUNDHOMO_" + sound + ".mp3";
        switch (sound) {
            case "c":
                return null;
            case "k":
                return null;
            case "x":
                return null;
            default:
                return p;
        }
    }

    static String uploadToService(String phpPath, String[] names, String[] values) {
        if(u.findString(apiCalls, phpPath) < 0){
            apiCalls = u.addString(apiCalls, phpPath);
        }
        String ret = "";
        try {
            java.net.URL url = new java.net.URL(phpPath);
            java.net.URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());

            String w = "";
            for (int i = 0; i < names.length; i++) {
                w += names[i] + "=" + values[i];
                if (i < names.length - 1) {
                    w += "&";
                }
            }
            writer.write(w);

            writer.flush();
            String line;

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((line = reader.readLine()) != null) {
                ret += line;
            }
            writer.close();
            reader.close();
        } catch (Exception rr) {
            int h = 0;
        }
        return ret;
    }
    
     String apiGetAccessToken() {
        apiConfig config = API_CONFIGS[currentEnvironment];
        String ret = null;
        try {
            java.net.URL url = new java.net.URL(config.accessUrl);
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
     
     int apiGetId(String urlPath, String jsonInputString) {
        if(API_CONFIGS[currentEnvironment].accessToken == null){
            setAccessToken();
        } 
        apiConfig config = API_CONFIGS[currentEnvironment];
        JSONObject ret = null;
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
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                JSONParser parser = new JSONParser();
                JSONObject ob = (JSONObject)parser.parse(response.toString());
                if(con.getResponseCode() == 201 || con.getResponseCode() == 200){
                    return Integer.parseInt(String.valueOf(((JSONObject)ob.get("data")).get("id")));
                }
                else{
                    System.out.println(con.getErrorStream().toString());
                    System.out.println(con.getResponseMessage());
                    int dd;
                    dd = 0;   
                }
            }
            
        } catch (Exception rr) {
            int dd;
            dd = 0;
        }
        u.okmess(shark.programName, "Failed");
        System.exit(0);
        return -1;
    }
    
    void setAccessToken(){
        String accessToken = apiGetAccessToken();
        if(accessToken==null){
            int h;
            h = 0; 
        }
        API_CONFIGS[currentEnvironment].accessToken = accessToken;
    }

    public void StartUpload() {
        uploadTopicNames = new String[]{};
        uploadTopicUUID = new int[]{};
        uploadWordXml = new String[]{};
        uploadWordSVG = new String[]{};
        uploadWordBMPIdentifier = new String[]{};
        uploadWordSVGUUID = new int[]{};
        uploadWordBMPUUID = new int[]{};
        topicIdsDoneForRest = new int[]{};
        uploadRecordingDetails = new String[]{};
        uploadRecordingUUID = new int[]{}; 
        uploadWordUUID = new int[]{};
        uploadStageUploadTopicToHeading = false;
        uploadStageUploadWords = false;
        uploadStageUploadRest = false; 
    }

    public String UploadCourse(String CourseName) {
        if (!uploadStageUploadTopicToHeading) {
            return null;
        }

        String csvFile = sharkStartFrame.publicPathplus + "csv" + shark.sep + "CoursesAndIDs.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        String staticCourseNames[] = new String[]{};
        int staticCourseIDs[] = new int[]{};
        try {
            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
                // use comma as separator
                String[] item = line.split(cvsSplitBy);
                String no = "";
                char c[] = item[0].toCharArray();
                for (int i = 0; i < c.length; i++) {
                    if (Character.isDigit(c[i])) {
                        no += String.valueOf(c[i]);
                    }
                }
                staticCourseIDs = u.addint(staticCourseIDs, Integer.parseInt(no));
                staticCourseNames = u.addString(staticCourseNames, item[1].trim());

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return getCourseIDFromNames(staticCourseIDs, staticCourseNames, CourseName);
    }

    public int UploadTopicHeading(String HeadingDisplay, String HeadingIndex, String OwningCourseID, String OwningCourse, String OwningHeadingID,
            String TopicHeadingNameType, String Locale, String UnitType, String Description) {
        if (!uploadStageUploadTopicToHeading) {
            return -1;
        }

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

        System.out.println("Heading :" + "  " + HeadingDisplay);
        
        String s= null;
        int ret = -1;
        if(CURRENT_MODE == MODE_DIRECT){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", HeadingDisplay);
            jsonObject.put("unit_order", HeadingIndex);
            jsonObject.put("version_id", currCourseVersion);
            jsonObject.put("description", Description);
            if(UnitType != null){
                jsonObject.put("type", UnitType);
            }
            ret = apiGetId("http://course-api.onwordshark.local/ports/unit",
                jsonObject.toString());   
        }
        else{
            s = uploadToService("http://localhost/so_uploadTopicHeading.php",
                    new String[]{"heading_display", "heading_index", "owning_course_id", "owning_heading_id", "topic_heading_name_type", "locale", "unit_type", "version", "description"},
                    new String[]{HeadingDisplay, HeadingIndex, OwningCourseID, OwningHeadingID, TopicHeadingNameType, Locale, UnitType, currCourseVersion, Description});            
        
            try {
                ret = Integer.parseInt(s);
            } catch (Exception e) {
                int gg;
                gg = 0;
            }
        }

        return ret;
    }
    
    public int UploadTopicAndSounds(topic t, String topicSettings, int unit_id, int unit_index, int wordlist_index, String course, jnode jn, String treepath,
            String teachingNote, String topicName, String isUnitRevision, String revision, String apNotInTest, String apNotInUnitOrTest, String apPriority, String hasStandard, String hasExtended) {

        if (t.name.equals("garbage")) {
            return -1;
        }

        if (t.name.equals("Alternative sounds for 'u'")) {
            int gg;
            gg = 9;
        }

        word standardWords[] = t.getWords(null, false);
        System.out.println("Topic :" + "  " + t.name);

        int availablegamescats[] = null;
        if (t.phrases) {  //captions
            availablegamescats = new int[]{CAT_PHRASES};
            currCat = CAT_PHRASES;
        } else if (t.phonics && !t.phonicsw) {  // sounds
            availablegamescats = new int[]{CAT_SOUNDS};
            currCat = CAT_SOUNDS;
        } else if (!t.phonicsw || t.notphonics) {  // nonphonic
            availablegamescats = new int[]{CAT_NONPHONICS};
            currCat = CAT_NONPHONICS;
        } else if (t.justphonics) {  // justphonic
            availablegamescats = new int[]{CAT_PHONICS};
            currCat = CAT_PHONICS;
        } else {
            availablegamescats = new int[]{CAT_NONPHONICS, CAT_PHONICS};
        }

        int PH_ON = 0;
        int PH_OFF = 1;

        MYSQLGameFiltering = true;
        // get available sets of game ids 
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
                sharkStartFrame.mainFrame.wordTree.setup(t, null);
                if (inphonics) {
                    student.setOption("s_usephonics");
                } else {
                    student.clearOption("s_usephonics");
                }
                wordlist.splitsInDevMode = true;
                t.getSplits();
                int splitCountInStandard = 0;
                for (int m = 0; t.splitwords != null && m < t.splitwords.length; m++) {
                    String s2 = t.splitwords[m];
                    int kk;
                    if ((kk = s2.indexOf("=")) >= 0) {
                        s2 = s2.substring(0, kk);
                    }
                    s2 = s2.replace("/", "");
                    s2 = s2.replace(u.phonicsplits, "");
                    for (int n = 0; n < standardWords.length; n++) {
                        String s1 = standardWords[n].v();
                        if (s2.equalsIgnoreCase(s1)) {
                            splitCountInStandard++;
                        }
                    }
                }
                gotSplits = t.splitwords != null && splitCountInStandard >= 4;
                if (gotSplits) {
                    int g;
                    g = 0;
                }
                String s = treepath;
                sharkStartFrame.mainFrame.studentList[sharkStartFrame.mainFrame.currStudent].currTopic = s;
                sharkStartFrame.mainFrame.setTopicList(course, jn.get().substring(1));
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
                if (currGameIds[p] == 3) {
                    int h;
                    h = 0;
                } else {
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
        mergedGames = mergedGameIds2(gameSetsResults[PH_ON], gameSetsResults[PH_OFF], t.singlesound);
        String mergedRecommendedGames = getRecommededGameIds(u.splitString(mergedGames, ","), t.markgames, t.markgamescode);

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

        MYSQLGameFiltering = false;
        String keys[];
        String vals[];
        String phpName;
        String isSingleSoundList = (t.phonics && !t.phonicsw) ? "1" : "0";
        
            keys = new String[]{"locale", "topic_name_type", "topic_teaching_note_type", "recording_standard_type", "word_split_phonic", "word_phonic_sound", "topic_block_type_standard", "topic_block_type_not_games", "topic_block_type_game", "topic_block_type_recommended_games", "topic_block_type_recommended2_games", "topic_block_type_item_word", "word_word", "game_name_type", "xml_att_recid_type", "settings", "unit_id", "wordlist_index",
                "phonic_on_games", "phonics_off_games", "split_on_games", "split_off_games",
                "game_ids", "recommended_game_ids", "is_single_sounds_list",
                "teaching_note", "topic_name", "is_unit_revision", "revision", "ap_not_in_test", "ap_not_in_unit_or_test", "ap_priority", "has_standard", "has_extended"
            };
            vals = new String[]{LOCALE, TOPICNAME_TYPE, TOPICTEACHINGNOTE_TYPE, RECORDINGSTANDARD_TYPE, WORDSPLITPHONIC_TYPE, WORDPHONICSOUND_TYPE, TOPICBLOCKSTANDARD_TYPE, TOPICBLOCKNOTGAMES_TYPE, TOPICBLOCKGAME_TYPE, TOPICBLOCKRECOMMENDEDGAMES_TYPE, TOPICBLOCKRECOMMENDED2GAMES_TYPE, TOPICBLOCKITEMWORD_TYPE, WORDWORD_TYPE, GAMENAME_TYPE, GTX_RECUUID_AT_XML, topicSettings, String.valueOf(unit_id), String.valueOf(wordlist_index),
                gameSetsResults[0], gameSetsResults[1], null, null,
                mergedGames, mergedRecommendedGames, isSingleSoundList,
                teachingNote, topicName, isUnitRevision, revision, apNotInTest, apNotInUnitOrTest, apPriority, hasStandard, hasExtended
            };
            
         String placementUnitIndex = null;
         String completedUnitIndex = null;
         
        if(MYSQLUpload.course.equalsIgnoreCase(WORDSHARKTESTCOURSE)){
            int placementInt = getSharkChallengePlacementUnitIndex(unit_index, wordlist_index);
            placementUnitIndex = placementInt < 0 ? null : String.valueOf(placementInt); 
            
            int completedInt = getSharkChallengeCompletedUnitIndex(unit_index, wordlist_index);
            completedUnitIndex = completedInt < 0 ? null : String.valueOf(completedInt);
        }

            phpName = "http://localhost/so_uploadTopicAndSoundsMerged.php";
            
            keys = new String[]{"locale", "topic_name_type", "topic_teaching_note_type", "recording_standard_type", "word_split_phonic", "word_phonic_sound", "topic_block_type_standard", "topic_block_type_not_games", "topic_block_type_game", "topic_block_type_recommended_games", "topic_block_type_recommended2_games", "topic_block_type_item_word", "word_word", "game_name_type", "xml_att_recid_type", "settings", "unit_id", "wordlist_index",
                "phonic_on_games", "phonics_off_games", "split_on_games", "split_off_games",
                "game_ids", "recommended_game_ids", "is_single_sounds_list",
                "teaching_note", "topic_name", "is_unit_revision", "revision", "ap_not_in_test", "ap_not_in_unit_or_test", "ap_priority", "has_standard", "has_extended", "placement_unit_index", "completed_unit_index"
            };
            vals = new String[]{LOCALE, TOPICNAME_TYPE, TOPICTEACHINGNOTE_TYPE, RECORDINGSTANDARD_TYPE, WORDSPLITPHONIC_TYPE, WORDPHONICSOUND_TYPE, TOPICBLOCKSTANDARD_TYPE, TOPICBLOCKNOTGAMES_TYPE, TOPICBLOCKGAME_TYPE, TOPICBLOCKRECOMMENDEDGAMES_TYPE, TOPICBLOCKRECOMMENDED2GAMES_TYPE, TOPICBLOCKITEMWORD_TYPE, WORDWORD_TYPE, GAMENAME_TYPE, GTX_RECUUID_AT_XML, topicSettings, String.valueOf(unit_id), String.valueOf(wordlist_index),
                gameSetsResults[0], gameSetsResults[1], null, null,
                mergedGames, mergedRecommendedGames, isSingleSoundList,
                teachingNote, topicName, isUnitRevision, revision, apNotInTest, apNotInUnitOrTest, apPriority, hasStandard, hasExtended, placementUnitIndex, completedUnitIndex
            };

            String ret = uploadToService(phpName, keys, vals);    
            try {
                if (Integer.parseInt(ret) >= 0) {
                    return Integer.parseInt(ret);
                }
            } catch (Exception ee) {
                int gg;
                gg = 9;
            }
            if (ret.startsWith("Error:")) {
                int gg;
                gg = 9;
            }
     

        return -1;
    }
       
    private JSONArray stringArrayToIntJsonArray(String[] stringArray){
        JSONArray array = new JSONArray();
        for (int i = 0; i < stringArray.length; i++) {
            array.add(Integer.parseInt(stringArray[i]));
        }
        return array;
    }
    
     private JSONArray stringArrayToStringJsonArray(String[] stringArray){
        JSONArray array = new JSONArray();
        for (int i = 0; i < stringArray.length; i++) {
            array.add(stringArray[i]);
        }
        return array;
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
        int[] singleSoundGameIdOrder = new int[]{
            getGameID(GAME_FINDSYMBOL),
            getGameID(GAME_SAYSOUNDSPHONICS),
            getGameID(GAME_READINGTEST),
            getGameID(GAME_SNAP),
            getGameID(GAME_SHARKS),
            getGameID(GAME_SALVAGE),
            getGameID(GAME_SPELLTEST),
            getGameID(GAME_MAZESPELLTEST),
            getGameID(GAME_MOVINGSPELLCHECK),
            getGameID(GAME_PATTERN),
            getGameID(GAME_TRACKING),
            getGameID(GAME_CATCHING)
        };
        int[] wholeWordGameIdOrder = new int[]{
            getGameID(GAME_FINDPICTUREPHONICS),
            getGameID(GAME_FINDWORDPHONICS),
            getGameID(GAME_SAYSOUNDSPHONICS),
            getGameID(GAME_SPLITSOUND),
            getGameID(GAME_JIGSAWPHONICS),
            getGameID(GAME_LEARNVOCAB),
            getGameID(GAME_FINDPICTUREVOCABULARY),
            getGameID(GAME_SAYWORDFORPICTURE),
            getGameID(GAME_FINDPICTUREFROMWRITTEN),
            getGameID(GAME_FINDWORD),
            getGameID(GAME_WORDSEARCH),
            getGameID(GAME_PAIRS),
            getGameID(GAME_HUNT),
            getGameID(GAME_SAYWORD),
            getGameID(GAME_SNAP),
            getGameID(GAME_READINGTEST),
            getGameID(GAME_SHARKS),
            getGameID(GAME_SHARKSALTER),
            getGameID(GAME_SALVAGE),
            getGameID(GAME_HELICOPTERSPELL),
            getGameID(GAME_JUMBLED),
            getGameID(GAME_SPELLTEST),
            getGameID(GAME_MAZESPELLTEST),
            getGameID(GAME_MOVINGSPELLCHECK),
            getGameID(GAME_JIGSAWSYLLABLES),
            getGameID(GAME_HUNTSYLLABLES),
            getGameID(GAME_HELICOPTERLISTEN),
            getGameID(GAME_PATTERN),
            getGameID(GAME_TRACKING),
            getGameID(GAME_SIMPLECROSSWORD),
            getGameID(GAME_FINDPICTUREFORSENTENCE),
            getGameID(GAME_SAYSENTENCE),
            getGameID(GAME_SENTENCECROSSWORD),
            getGameID(GAME_CATCHING)
        };
        
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
                int gid = getGameID(games[k].get(), String.valueOf(currCat));
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

    public void saveRecordingArrays() {
        db.update(sharkStartFrame.optionsdb, "sql_recordingnames", uploadRecordingDetails, db.TEXT);

        String ss[] = new String[]{};
        for (int i = 0; i < uploadRecordingUUID.length; i++) {
            ss = u.addString(ss, String.valueOf(uploadRecordingUUID[i]));
        }
        db.update(sharkStartFrame.optionsdb, "sql_recordinguuid", ss, db.TEXT);
    }

    public void getRecordingArrays() {
        if (uploadRecordingDetails.length != 0) {
            return;
        }
        uploadRecordingDetails = (String[]) db.find(sharkStartFrame.optionsdb, "sql_recordingnames", db.TEXT);
        String ss[] = (String[]) db.find(sharkStartFrame.optionsdb, "sql_recordinguuid", db.TEXT);
        for (int i = 0; i < ss.length; i++) {
            uploadRecordingUUID = u.addint(uploadRecordingUUID, Integer.parseInt(ss[i]));
        }
    }

    void doImagesUpload() {
        try {
            for (int i = 0; i < jsonImageResults.size(); i++) {
                JSONObject p = (JSONObject) jsonImageResults.get(i);
                String strS3key = (String) p.get(ToolsOnlineResources.s3key);
                String strDesktopName = (String) p.get(ToolsOnlineResources.desktopName);
                String strIsVocab = (String) p.get(ToolsOnlineResources.vocab);
                String strIsAnimated = (String) p.get(ToolsOnlineResources.animated);
                String currUsed = (String) p.get(ToolsOnlineResources.currentlyUsed);
                if (currUsed != null && currUsed.equals("false")) {
                    continue;
                }

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

                String ret = null;
                int k = -1;
                try {
                    ret = uploadToService("http://localhost/so_uploadImage.php",
                            new String[]{"type", "word",
                                "filename", "s3key", "is_vocab", "is_animated"},
                            new String[]{strType, u.formatTextforUpload(imword, CURRENT_MODE), strFileName, strS3key, strIsVocab, strIsAnimated});
                    if (ret.startsWith("Error:")) {
                        int gg;
                        gg = 9;
                        return;
                    }
                    k = Integer.parseInt(ret);
                } catch (Exception e) {
                    int ff;
                    ff = 99;
                }

                // String strIsVocab = (String)p.get(ToolsOnlineResources.vocab);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    void doRecordingsUpload() {
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

                System.out.println(strDesktopName);

                String strIsVocab = (String) p.get(ToolsOnlineResources.vocab);
                String strFileName = strS3key.substring(j + sep.length());
                if (!strFileName.endsWith(".mp3")) {
                    int g;
                    g = 0;
                }

                if (strDesktopName.trim().equals("")) {
                    continue;
                }
                if (strDesktopName.indexOf('_') >= 0) {
                    continue;
                }
                word w = new word(strDesktopName, "publictopics");
                String ret = null;
                int k = -1;
                try {
                    ret = uploadToService("http://localhost/so_uploadSound.php",
                            new String[]{"type", "word",
                                "filename", "s3key", "is_vocab"},
                            new String[]{strType, u.formatTextforUpload(getStrippedSoundName(strDesktopName), CURRENT_MODE), strFileName, strS3key, strIsVocab});
                    if (ret.startsWith("Error:")) {
                        int gg;
                        gg = 9;
                        System.out.println(ret);
                        return;
                    }
                    k = Integer.parseInt(ret);
                } catch (Exception e) {
                    int ff;
                    ff = 99;
                    System.out.println(e.getMessage());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        int g;
        g = 0;
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
        int gg = 0;
        int hr = -1;
        int hr2 = -1;

        if ((hr = getRecordingWordID("head" + "=", false)) >= 0) {
            int ff;
            ff = 9;
        }
        if ((hr2 = getRecordingWordID("head", false)) >= 0) {
            int ff;
            ff = 9;
        }

        int i, n;
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
        if (sscourses.length != ALL_COURSES.length) {
            u.okmess(shark.programName + " Bad Courses", "Wrong number of courses", sharkStartFrame.mainFrame);
            return;
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
            topicScan(topicTreeList, sel[n], n, courseids, coursenames);
        }

        long timetaken = Calendar.getInstance().getTimeInMillis() - MYSQLUpload.startTime;

        String str = String.valueOf(java.util.concurrent.TimeUnit.MILLISECONDS.toMinutes(timetaken));
        doingPort = false;

        for (int index = 0; index < apiCalls.length; ++index) {          
            System.out.println("api calls :" + apiCalls[index]);
        }
        if(CURRENT_MODE == MODE_VIA_DUMP){
            u.okmess(shark.programName, "Finished in : " + str + " minutes.", sharkStartFrame.mainFrame);         
        }        
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

    int UploadWord2(String topicID, String isStandard, String isExcluded, String plainword, String fullword, String patternword, String definition, String soundName, String imageNameWS, String imageNamePhoto, String homophoneName, String splitsounds) {
        int k = -1;
        String ret = "";
        fullword = fullword.replace("@", "");
        try {
            if (topicID.equals("0")) {
                int g;
                g = 0;
            }
                
               ret = uploadToService("http://localhost/so_uploadWord2.php",
                    new String[]{"topic_id", "is_standard",
                        "is_excluded", "plain_word", "full_word", "pattern_word", "definition", "sound_name", "image_ws_name", "image_photo_name",
                        "homophone_name", "sound_splits"},
                    new String[]{topicID, isStandard,
                        isExcluded, u.formatTextforUpload(plainword, CURRENT_MODE), u.formatTextforUpload(fullword, CURRENT_MODE), u.formatTextforUpload(patternword, CURRENT_MODE), definition,
                        soundName, imageNameWS, imageNamePhoto, homophoneName, splitsounds}
                );        
               

            if (ret.startsWith("Error:")) {
                int gg;
                gg = 9;
                return -1;
            }
            k = Integer.parseInt(ret);
        } catch (Exception e) {
            int ff;
            ff = 99;
        }

        return k;
    }

    public void UploadTopicRest(String xmlForTopic, String topicName, int id, String gameOptions) {
        if (!uploadStageUploadRest) {
            return;
        }
        if (u.inlist(topicIdsDoneForRest, id)) {
            return;
        }
        topicIdsDoneForRest = u.addint(topicIdsDoneForRest, id);
        if (topicName.equals("tch for year 1")) {
            int f;
            f = 9;
        }
        writeRestXML(topicName, xmlForTopic);
        if (topicName.equals("tch for year 1")) {
            int f;
            f = 9;
        }

        if (topicName.equals("decide to double/ no doubling")) {
            int gg;
            gg = 9;
        }
        if (topicName.equals("garbage")) {
            return;
        }
        System.out.println("Topic rest:" + "  " + topicName);

        if (topicName.equals("- air -")) {
            int gg;
            gg = 9;
        }
        String ret = null;
            ret = uploadToService("http://localhost/so_uploadTopicRest.php",
                new String[]{
                    "topic_xml",
                    "topic_block_extended",
                    "topic_block_pairs",
                    "topic_block_distractor_phonic_sounds",
                    "topic_block_game",
                    "xml_topic",
                    "xml_topic_block",
                    "xml_topic_blocks",
                    "xml_topic_reference",
                    "xml_topic_references",
                    "xml_topic_sentence",
                    "xml_topic_sentences",
                    "xml_select",
                    "xml_pairs",
                    "xml_att_btopic_uuid",
                    "xml_att_tbtype",
                    "xml_att_select_no",
                    "xml_att_select_group_no",
                    "xml_att_sent_text",
                    "game_options"

                },
                new String[]{
                    xmlForTopic,
                    TOPICBLOCKEXTENDED_TYPE,
                    TOPICBLOCKPAIRS_TYPE,
                    TOPICBLOCKDISTRACTORPHONICSOUNDS_TYPE,
                    TOPICBLOCKGAME_TYPE,
                    GTX_TOPICROOT_XML,
                    GTX_TB_XML,
                    GTX_TBS_XML,
                    GTX_REFERENCE_XML,
                    GTX_REFERENCES_XML,
                    GTX_SENTENCE_XML,
                    GTX_SENTENCES_XML,
                    GTX_SELECT_XML,
                    GTX_PAIRS_XML,
                    GTX_TOPICUUID_AT_XML,
                    GTX_TB_TYPE_AT_XML,
                    GTX_SELECTNO_AT_XML,
                    GTX_SELECTGROUPNO_AT_XML,
                    GTX_SENTENCE_TEXT_AT_XML,
                    gameOptions
                }
            );
            if (ret.startsWith("Error:")) {
                int gg;
                gg = 9;
            }
            if (!ret.equals("0")) {
                int d;
                d = 9;
            }


        int g;
        g = 9;
    }

    public static int UploadWord(word w, topic tt, String xmlForTopic, boolean overrideblock) {
        if (true || !overrideblock && !uploadStageUploadWords) {
            return -1;
        }
        String word = u.formatTextforUpload(w.value, CURRENT_MODE);
        int recordingid = getRecordingWordID(w.vsay(), tt.fl);
        if (w.phonics && !w.phonicsw) {

        } else {
            if (recordingid < 0) {
                int gg;
                gg = 9;
            }
        }
        String ret = null;

        if (!generateImageFiles) {
            int topicUUID = uploadTopicUUID[u.findString(uploadTopicNames, tt.name)];
            try {
                ret = uploadToService("http://localhost/so_uploadWord.php",
                        new String[]{"topic_id", "topic_xml", "word_phonic_split_type", "word_phonic_syllable_split_type", "word_syllable_split_type", "word_phonic_split_sound", "word_syllable_split_sound", "word_root_type", "word_suffix_type", "recording_id", "locale"},
                        new String[]{String.valueOf(topicUUID), xmlForTopic, WORDSPLITPHONIC_TYPE, WORDSPLITPHONICSYLL_TYPE, WORDSPLITSYLL_TYPE, WORDPHONICSOUND_TYPE, WORDPHONICSYLLSOUND_TYPE, WORDCOMPONENTROOT_TYPE, WORDCOMPONENTSUFFIX_TYPE, recordingid >= 0 ? String.valueOf(recordingid) : null, LOCALE});
                int f;
                f = 9;
            } catch (Exception e) {
                int ff;
                ff = 99;
            }
        } else {
            ret = "";
        }
        System.out.println("Word :" + word + "  " + ret);
        if (ret.startsWith("Error:")) {
            int gg;
            gg = 9;
            return -1;
        }
        if (!generateImageFiles && u.findString(uploadWordXml, xmlForTopic) < 0) {
            if (uploadWordXml.length != uploadWordUUID.length) {
                int gg;
                gg = 9;
            }
            int addedi = -1;
            try {
                uploadWordXml = u.addString(uploadWordXml, xmlForTopic);
                addedi = Integer.parseInt(ret);
                uploadWordUUID = u.addint(uploadWordUUID, addedi);
                writeWordsXML(w, word, String.valueOf(addedi), xmlForTopic);
                if (uploadWordXml.length != uploadWordUUID.length) {
                    int gg;
                    gg = 9;
                }
            } catch (Exception ee) {
                int gg;
                gg = 9;
            }
            return addedi;
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

    // not doing svg anymore so this must change
    public static void UploadImage(String picName) {
        if (!uploadStageUploadWords) {
            return;
        }

        currSaveSharkImage = null;
        sharkImage im = sharkImage.find(picName);
        String image = null;
        String shash = null;
        if (im != null && currSaveSharkImage != null && u.findString(saveImageHashes, (shash = getHashOfSaveImage(currSaveSharkImage))) < 0) {
            if (generateImageFiles) {
                String[] ss = null;
                if (ss != null) {
                    image = combineStringArray(ss);
                }
                if (image != null) {
                    writeToSVGImageFile(picName, shash, image);
                }
            } else {
                image = getSVGViaFileFromHash(picName, shash);
            }
        }
        if (!uploadImages) {
            return;
        }

        if (image == null) {
            return;
        }
        if (u.findString(uploadWordSVG, shash) < 0) {
            String ret = null;
            image = u.formatTextforUpload(image, CURRENT_MODE);
            try {
                ret = uploadToService("http://localhost/so_uploadImage.php",
                        new String[]{"image_name", "image_type", "image_content", "image_svg_type", "image_bmp_type", "locale"},
                        //      new String[]{u.formatTextforUpload(stripAts(picName)), IMAGE_TYPES[IMAGE_SVG_TYPE], image, IMAGE_TYPES[IMAGE_SVG_TYPE], IMAGE_TYPES[IMAGE_BMP_TYPE], LOCALE});
                        new String[]{u.formatTextforUpload(stripAts(picName), CURRENT_MODE), IMAGE_TYPES[IMAGE_SVG_TYPE], getSVGPathFile(picName, shash), IMAGE_TYPES[IMAGE_SVG_TYPE], IMAGE_TYPES[IMAGE_BMP_TYPE], LOCALE});
                int f;
                f = 9;
            } catch (Exception e) {
                int ff;
                ff = 99;
            }
            System.out.println("Image :" + "  " + ret);
            if (ret == null) {
                return;
            }
            if (ret.trim().equals("")) {
                return;
            }
            if (ret.startsWith("Error:")) {
                System.out.println("****************************************SVGIMAGEERROR: " + picName);
                return;
            }

            uploadWordSVG = u.addString(uploadWordSVG, shash);
            uploadWordSVGUUID = u.addint(uploadWordSVGUUID, Integer.parseInt(ret));

            // is there a photo too?
            String imid = getImageFileIdentifierForWord(picName);
            if (imid != null && (u.findString(uploadWordBMPIdentifier, imid) < 0)) {

                String ret2 = null;
                try {
                    ret2 = uploadToService("http://localhost/so_uploadImage.php",
                            new String[]{"image_name", "image_type", "image_content", "image_svg_type", "image_bmp_type", "locale"},
                            new String[]{stripAts(picName), IMAGE_TYPES[IMAGE_BMP_TYPE], getImageFileWebPathForWord(picName), IMAGE_TYPES[IMAGE_SVG_TYPE], IMAGE_TYPES[IMAGE_BMP_TYPE], LOCALE});
                    int ff;
                    ff = 9;
                } catch (Exception e) {
                    int ff;
                    ff = 99;
                }
                if (ret == null) {
                    return;
                }
                if (ret.trim().equals("")) {
                    return;
                }
                if (ret2.startsWith("Error:")) {
                    System.out.println("****************************************BMPIMAGEERROR: " + picName);
                    return;
                }

                uploadWordBMPIdentifier = u.addString(uploadWordBMPIdentifier, imid);
                uploadWordBMPUUID = u.addint(uploadWordBMPUUID, Integer.parseInt(ret2));

            }

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

    public void UploadRudeWords(String ss[]) {
        for (int i = 0; i < ss.length; i++) {
            String ret = null;
            try {
                ret = uploadToService("http://localhost/so_uploadRudeWords.php",
                        new String[]{"word"},
                        new String[]{ss[i]});
                int f;
                f = 9;
            } catch (Exception e) {
                int ff;
                ff = 99;
            }
            if (ret.startsWith("Error:")) {
                int gg;
                gg = 9;

            }
        }
    }

    public void UploadUSSpelling(String ss[]) {

        for (int i = 0; i < ss.length; i++) {
            String ret = null;
            try {
                ret = uploadToService("http://localhost/so_uploadUSSpelling.php",
                        new String[]{"uk_word", "us_word"},
                        new String[]{ss[0], ss[1]});
                int f;
                f = 9;
            } catch (Exception e) {
                int ff;
                ff = 99;
            }
        }

    }

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
            int headingindex = 0;

            // main category
            if (s.equalsIgnoreCase(REWARDS)) {
                continue loop1;
            }

            loop2:
            for (Enumeration e2 = c.children(); e2.hasMoreElements();) {
                jnode c2 = (jnode) e2.nextElement();
                String gameHeading = c2.get();
                if (gameHeading.equalsIgnoreCase("Sound & letter patterns")) {
                    int gg;
                    gg = 0;
                }
                int gameunderheadingindex = 0;

                loop3:
                for (Enumeration e3 = c2.children(); e3.hasMoreElements();) {
                    jnode c3 = (jnode) e3.nextElement();
                    String game = c3.get();

                    DocumentBuilderFactory docFactory = null;
                    DocumentBuilder docBuilder = null;
                    Document doc = null;
                    Element rootElement = null;

                    DocumentBuilderFactory docFactory2 = null;
                    DocumentBuilder docBuilder2 = null;
                    Document doc2 = null;
                    Element rootElement2 = null;
                    gameunderheadingindex++;

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

                            String gametype = GAMETYPERECOGNITION;
                            String gametooltip = null;
                            String gametooltiph = null;
                            String gameid = null;
                            String gameOptionJson = null;

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
                                    gameid = endparam;
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

                                } else if (gameparameter.equalsIgnoreCase("spelling")) {
                                    gametype = GAMETYPESPELLING;
                                } else if (gameparameter.equalsIgnoreCase("recognition")) {
                                    gametype = GAMETYPERECOGNITION;
                                } else if (gameparameter.equalsIgnoreCase("alphabet")) {
                                    gametype = GAMETYPEALPHABET;
                                } else if (preparam != null && preparam.equalsIgnoreCase("id")) {
                                    gameid = endparam;
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
                            }

                            if (jsonKeys.length > 0 && jsonValues.length > 0) {
                                gameOptionJson = getSingleArrayJson(jsonKeys, jsonValues);
                            }
                            String xmltopicflagres = null;

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
                                    xmltopicflagres = writer.toString();
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
                            String iconImageExt = ".gif";
                            String s3BucketName = "gameicons";
                            String iconImagePath = s3BucketName + "/" + s3BucketName.toUpperCase() + "_" + tor.getImageS3Name(null, gamename, null, s3BucketName) + iconImageExt;
                            iconImagePath = "game-bg-5_03.png";
                            String settings = "{\"beep\": false, \"speed\": \"1\", \"tiles\": true, \"complexity\": \"1\"}";

                            String gameId = getGameIDFromNames(staticGameIDs, staticGameNames, staticGameSubNames, gamename, gamesubname == null ? "" : gamesubname);
                            if (gameId == null) {
                                int g;
                                g = 9;
                            }

                            int g = Integer.parseInt(gameId);
                            if (wantUpdate) {
                                try {
                                    String res = uploadToService("http://localhost/so_uploadGames.php",
                                            new String[]{"game_id", "settings", "program_name", "game_name", "game_sub_name", "game_description", "game_description2", "game_code", "game_educational_type", "game_index_no_in_heading", "category_flag", "category_flag_condition", "heading_display_name", "heading_index_no", "heading_grouping", "options_xml", "topic_flags_xml", "locale", "game_description_type", "game_name_type", "icon_image_path"},
                                            new String[]{gameId, settings, shark.programName, u.formatTextforUpload(gamename, CURRENT_MODE), gamesubname == null ? "" : u.formatTextforUpload(gamesubname, CURRENT_MODE), u.formatTextforUpload(u.setTextHtmlFormattedForUpload2(gametooltip, CURRENT_MODE),CURRENT_MODE), u.formatTextforUpload(u.setTextHtmlFormattedForUpload2(gametooltiph, CURRENT_MODE),CURRENT_MODE), gameid, gametype, String.valueOf(gameunderheadingindex), s, TOPICFLAGCONDITION_OK, u.formatTextforUpload(gameHeading, CURRENT_MODE), String.valueOf(headingindex), GAMEHEADINGFLAG, gameOptionJson, xmltopicflagres, LOCALE, GAME_DESCRIPTION_TYPE, GAME_NAME_TYPE, iconImagePath});

                                    g = Integer.parseInt(res);
                                } catch (Exception ee) {
                                    g = 0;
                                }

                                if (g < 0) {
                                    int h;
                                    h = 0;
                                }
                            }

                            gameNames = u.addString(gameNames, origamename);
                            gameCategory = u.addString(gameCategory, String.valueOf(index));
                            gameID = u.addint(gameID, g);
                            continue loop4;
                        }
                    }
                }
                headingindex++;
            }
            index++;
        }
        int g;
        g = 0;
    }

    String getGameIDFromNames(int staticGameIDs[], String staticGameNames[], String staticGameSubNames[], String gamename, String gamesubname) {
        for (int i = 0; i < staticGameNames.length; i++) {
            if (gamename.equalsIgnoreCase(staticGameNames[i]) && gamesubname.equals(staticGameSubNames[i])) {
                return String.valueOf(staticGameIDs[i]);
            }
        }
        return null;
    }

    String getCourseIDFromNames(int staticCourseIDs[], String staticCourseNames[], String coursename) {
        for (int i = 0; i < staticCourseNames.length; i++) {
            if (coursename.equalsIgnoreCase(staticCourseNames[i])) {
                return String.valueOf(staticCourseIDs[i]);
            }
        }
        return null;
    }

    static int getGameID(String name, String category) {
        for (int i = 0; i < gameNames.length; i++) {
            if (gameNames[i].equalsIgnoreCase(name) && gameCategory[i].equals(category)) {
                return gameID[i];
            }
        }
        return -1;
    }

    static int getGameID(String fullNameIncludingSubName) {
        for (int i = 0; i < gameNames.length; i++) {
            if (gameNames[i].equalsIgnoreCase(fullNameIncludingSubName)) {
                return gameID[i];
            }
        }
        return -1;
    }

    static String[] getGameNamesFromIDs(String s) {
        String ss[] = u.splitString(s, ',');
        String ret[] = new String[]{};
        for (int i = 0; i < ss.length; i++) {

            int y = u.inintlist(gameID, Integer.parseInt(ss[i]));
            ret = u.addString(ret, gameNames[y]);
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

    static void writeRestXML(String topicName, String xml) {
        if (!restXML) {
            return;
        }
        File f = new File(RESTXMLFOLDER + shark.sep + formatForFileWrite(topicName) + ".xml");
        File fp = new File(RESTXMLFOLDER);
        if (!fp.exists()) {
            fp.mkdirs();
        }
        if (!f.exists()) {
            PrintWriter pw = null;
            try {
                pw = new PrintWriter(new FileWriter(f.getAbsolutePath()));
                pw.println(xml);
                pw.flush();
            } catch (Exception e) {
            }
        }
    }

    static void writeWordsXML(word w, String wordname, String wordid, String xml) {
        if (!restXML) {
            return;
        }
        File f = new File(WORDSXMLFOLDER + shark.sep + w.v() + "_" + wordid + formatForFileWrite(wordname) + ".xml");
        File fp = new File(WORDSXMLFOLDER);
        if (!fp.exists()) {
            fp.mkdirs();
        }
        if (!f.exists()) {
            PrintWriter pw = null;
            try {
                pw = new PrintWriter(new FileWriter(f.getAbsolutePath()));
                pw.println(xml);
                pw.flush();
            } catch (Exception e) {
            }
        }
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

    public void topicScan(topicTree topicTreeList, jnode selnode, int p, String courseids[], String coursenames[]) {
        currentCourse = selnode.get();
        long portStartTime = Calendar.getInstance().getTimeInMillis();
        String parentCourseID = null;
        String parentName = null;
        boolean start = true;
        int levelStart = 2;
        
        int k;
        if ((k = u.findString(coursenames, currentCourse)) >= 0) {
            parentName = currentCourse;
            parentCourseID = courseids[k];
        } else {
            int y;
            y = 9;
        }

        int topicCount = getTopicCount(selnode);
        int wordlistDoneCount = 0;        

        
        String headingss[] = new String[20];
        int lastUnitId = -1;
        int lastUnitIndex = -1;
        int lasttopicindex = 0;

        boolean firstone = true;
        enumloop:
        for (Enumeration e = ((jnode) selnode).preorderEnumeration(); e.hasMoreElements();) {
            jnode jn = (jnode) e.nextElement();
            if (firstone) {
                firstone = false;
                continue enumloop;
            }
            String sh = jn.get();
            if (sh.trim().equals("")) {
                continue enumloop;
            }

            if (!jn.isLeaf()) {
                if (!start) {
                    continue enumloop;
                }
                lastUnitIndex += 1;
                int id = jn.getLevel();
                String unitType = null;
                jnode jnode1 = (jnode) jn.getNextSibling();
                if (jnode1 == null || (jnode1.get().trim() == "")) {
                    if (selnode.get().equals(COURSE_WORDSHARK)) {
                        unitType = "ap_end";
                    }
                }
                String hhid = null;
                if (id > levelStart) {
                    hhid = headingss[id - levelStart - 1];
                } else if (id == levelStart) {
                    hhid = null;
                }
                int parentHeadingID = UploadTopicHeading(u.formatTextforUpload(sh, CURRENT_MODE), String.valueOf(lastUnitIndex), parentCourseID, parentName, hhid, MYSQLUpload.TOPIC_HEADING_NAME_TYPE,
                        MYSQLUpload.LOCALE, unitType, "dummy description");
                if (uploadStageUploadTopicToHeading) {
                    System.out.println("****    " + u.formatTextforUpload(sh, CURRENT_MODE) + "   " + String.valueOf(lastUnitIndex) + "    " + parentCourseID + "     " + hhid);
                }


                lastUnitId = parentHeadingID;
                lasttopicindex = 0;
                headingss[id - levelStart] = String.valueOf(parentHeadingID);
            } else {
                String nam = jn.get();
                if (nam.indexOf("-tion mixed") >= 0) {
                    start = true;
                }
                if (!start) {
                    continue enumloop;
                }

                if (nam.startsWith(topicTree.ISTOPIC)) {
                    nam = nam.substring(1);
                }

                saveTree1 st1 = (saveTree1) db.find(topic.publictopics, nam, db.TOPIC);
                
                String topicName = stripAts(st1.curr.names[0]);
                t = topic.findtopic(topicName);
                t.getWords(null, false);
                if(CURRENT_MODE == MODE_VIA_DUMP){
                    int repeatval = u.findString(MYSQLUpload.uploadTopicNames, nam);
                    if (repeatval >= 0) {
                        repeatval = MYSQLUpload.uploadTopicUUID[repeatval];
                    }

                    String teachingNote = getTopicXML2(st1, GTX_TEACHINGNOTE, false);
                    teachingNote = u.formatTextforUpload(teachingNote,CURRENT_MODE);
                    topicName = u.formatTextforUpload(topicName,CURRENT_MODE);
                    String revision = getTopicXML2(st1, GTX_REVISION, true);
                    String apNotInTest = getTopicXML2(st1, topic.types[topic.APNOTINTEST], true);
                    String apNotInUnitOrTest = getTopicXML2(st1, topic.types[topic.APNOTINUNITORTEST], true);
                    String apPriority1 = getTopicXML2(st1, topic.types[topic.APPRIORITY1], true);
                    String apPriority2 = getTopicXML2(st1, topic.types[topic.APPRIORITY2], true);
                    String apPriority = "0";
                    if (apPriority1.equals("1")) {
                        apPriority = "1";
                    }
                    if (apPriority2.equals("1")) {
                        apPriority = "2";
                    }
                    String settingKeys[] = new String[]{"Homophones", "InOrder", "ForcePhonics", "DisablePhonics", "StartPhonics", "Nonsense"};
                    String settingValues[] = getTopicSettings(topicTreeList, null, st1, -1);

                    phonicshomo_on = (t.phonics && !t.phonicsw && t.singlesound);
                    if (phonicshomo_on) {
                        settingValues[0] = "1";  // the homophone setting
                    }

                    word allword[] = t.getAllWords(false, true);
                    allword = u.addWords(allword, t.getAllWords(true, true));
                    longestWord = getLongestWord(allword);
                    word[] wa = t.getAllWords(false, true);
                    String hasStandard = wa.length > 0 ? "1" : "0";
                    String hasExtended = t.canextend() ? "1" : "0";

                    String isUnitRevision = t.unitrevision ? "1" : "0";

                    String topicSettings = getSingleArrayJson(settingKeys, settingValues);
                    int topicID = UploadTopicAndSounds(t, topicSettings, lastUnitId, lastUnitIndex, lasttopicindex, selnode.get(), jn, topicTreeList.getCurrentTopicPath(),
                            teachingNote, topicName, isUnitRevision, revision, apNotInTest, apNotInUnitOrTest, apPriority, hasStandard, hasExtended);


                    String thexml = getTopicXML(topicTreeList, jn, st1, topicID);
                    String gameOptions = doGameOptions(st1);

                    if (thexml != null) {
                        UploadTopicRest(thexml, nam, topicID, gameOptions);
                        lasttopicindex++;
                    } else {
                        int g;
                        g = 0;
                    }
                 }       
                else{
                    treeDetails tree = new treeDetails(
                        t ,
                        st1,
                        topicTreeList,
                        jn
                    );
                    
                    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
                    System.out.println(timeStamp + " Starting: " + t.name);
                    JSONObject postJsonObject = new JSONObject();
                    postJsonObject.put("json_data", getTopicJsonForUpload(tree, lastUnitId, lasttopicindex, selnode.get()));
                    wordlistDoneCount++;
                    writeJson(t.name, postJsonObject.toJSONString());
                    int g = apiGetId("http://course-api.onwordshark.local/ports/wordlist",
                        postJsonObject.toString());
                    lasttopicindex++;
                    System.out.println("....Finished: " + String.valueOf(g) + " PROGRESS " + String.valueOf((int)(((float)wordlistDoneCount/topicCount)*100)) + "%");
                }
            }
        }
        
        String str = String.valueOf(java.util.concurrent.TimeUnit.MILLISECONDS.toMinutes(Calendar.getInstance().getTimeInMillis() - portStartTime));
        int res = apiGetId("http://course-api.onwordshark.local/ports/"+currCourseVersion+"/activate", null);
        if(Integer.parseInt(currCourseVersion) == res){
            u.okmess(shark.programName, "Successfully finished in : " + str + " minutes.", sharkStartFrame.mainFrame);        
        }
        else{
            u.okmess(shark.programName, "Failed in : " + str + " minutes.", sharkStartFrame.mainFrame);          
        }
    }

    public static int getTopicID(String topic) {
        int k;
        if ((k = getTopicID2(topic)) < 1 && uploadStageUploadRest && topic.endsWith("+")) {
            k = getTopicID2(topic.substring(0, topic.length() - 1));
        }
        return k;
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

    public static int getTopicID2(String topic) {
        if (!uploadStageUploadRest && !uploadStageUploadTopicToHeading) {
            return -1;
        }
        int k;
        try {
            if ((k = u.findString(uploadTopicNames, topic)) >= 0) {
                return uploadTopicUUID[k];
            }
        } catch (Exception e) {
            int gg;
            gg = 9;
        }
        return -1;
    }

    public static int getWordID(String word) {
        if (!uploadStageUploadRest) {
            return -1;
        }
        int k;
        try {
            if ((k = u.findString(uploadWordXml, word)) >= 0) {
                return uploadWordUUID[k];
            }
        } catch (Exception e) {
            int gg;
            gg = 9;
        }
        return -1;
    }

    public static int getImageID(String word) {
        if (!uploadImages) {
            return 1;
        }
        if (!uploadStageUploadRest) {
            return -1;
        }
        currSaveSharkImage = null;
        sharkImage im = sharkImage.find(word);
        String shash = null;
        if (im != null && currSaveSharkImage != null) {
            shash = getHashOfSaveImage(currSaveSharkImage);
            int k;
            try {
                if ((k = u.findString(uploadWordSVG, shash)) >= 0) {
                    return uploadWordSVGUUID[k];
                }
            } catch (Exception e) {
                int gg;
                gg = 9;
            }
        }
        return -1;
    }

    public static int getRecordingSoundID(String soundname) {
        String datab = "publicsay1";
        int k;
        if ((k = u.findString(uploadRecordingDetails, makeRecordingDetails(getSoundDbType(datab), soundname))) >= 0) {
            return uploadRecordingUUID[k];
        }
        return -1;

    }

    String[] getRecordingWordID2(word w, boolean isFL) {
        String word = w.vsay();
        if (w.phonics && !w.phonicsw) {
            word = w.phonics()[0] + "~";
        }
        String datab = null;
        if (isFL) {
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

    public static int getRecordingWordID(String word, boolean isFL) {
        word w = new word(word, "publictopics");
        String name1;
        if (word.endsWith("=")) {
            name1 = word;
        } else {
            name1 = w.vsay();
        }
        String datab = null;
        if (isFL) {
            datab = "publicsay3";
        }
        for (int i = 0; i < sharkStartFrame.publicSoundLib.length; ++i) {
            if (db.query(sharkStartFrame.publicSoundLib[i], name1, db.WAV) >= 0) {
                datab = (new File(sharkStartFrame.publicSoundLib[i])).getName();
                break;
            }
        }
        if (datab == null) {
            return -1;
        }
        if (db.query(datab, name1, db.WAV) < 0) {
            return -1;
        }
        int k;
        if ((k = u.findString(uploadRecordingDetails, makeRecordingDetails(getSoundDbType(datab), name1))) >= 0) {
            return uploadRecordingUUID[k];
        }
        return -1;
    }

    public static int getImageBMPID(String id) {
        if (!uploadImages || !uploadStageUploadRest) {
            return -1;
        }
        int k;
        try {
            if ((k = u.findString(uploadWordBMPIdentifier, id)) >= 0) {
                return uploadWordBMPUUID[k];
            }
        } catch (Exception e) {
            int gg;
            gg = 9;
        }
        return -1;
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
                return null;
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
            getTopicXML2(st, topic.types[topic.HOMOPHONES], true),
            getTopicXML2(st, topic.types[topic.INORDER], true),
            getTopicXML2(st, topic.types[topic.JUSTPHONICS], true),
            getTopicXML2(st, topic.types[topic.NOTPHONICS], true),
            getTopicXML2(st, topic.types[topic.STARTPHONICS], true),
            getTopicXML2(st, topic.types[topic.NONSENSE], true)
        };
    }

    public int getLongestWord(word ww[]) {
        int n = Integer.MIN_VALUE;
        for (int i = 0; i < ww.length; i++) {
            n = Math.max(ww[i].v().length(), n);
        }
        return n;
    }

    public String getTopicXML(topicTree topicTreeList, jnode jn, saveTree1 st, int topic_id) {
        wordsForSounds = null;
        separateSounds = null;
        String topicName = getTopicXML2(st, GTX_TOPICNAME, false);

        if (topicName.indexOf("change y to i rule before adding suffix") >= 0) {
            if (uploadStageUploadRest) {
                int ff;
                ff = 9;
            }
        }

        t = topic.findtopic(topicName);
        t.getWords(null, false);

        if (t.picturePrefence != null) {
            String ppss[] = u.splitString(t.picturePrefence, ",");
            picPreKey = ppss[0].trim();
            ppss[1] = ppss[1].trim();
            picPreIsPhoto = false;
            if (ppss[1].equalsIgnoreCase(PICPREF_VALWORDSHARKIM)) {
                picPreIsPhoto = false;
            } else if (ppss[1].equalsIgnoreCase(PICPREF_VALPHOTOIM)) {
                picPreIsPhoto = true;
            } else {
                picPreKey = null;
            }
        }

        t.mySQL_Topic_ID = topic_id;
        tjn = jn;
        topicTL = topicTreeList;
        topicName = stripAts(topicName);
        topicName = u.formatTextforUpload(topicName,CURRENT_MODE);

        String teachingNote = getTopicXML2(st, GTX_TEACHINGNOTE, false);
        teachingNote = u.formatTextforUpload(teachingNote,CURRENT_MODE);

        String homophones = getTopicXML2(st, GTX_HOMOPHONES, true);

        String apNotInTest = getTopicXML2(st, topic.types[topic.APNOTINTEST], true);
        String apNotInUnitOrTest = getTopicXML2(st, topic.types[topic.APNOTINUNITORTEST], true);
        String apPriority1 = getTopicXML2(st, topic.types[topic.APPRIORITY1], true);
        String apPriority2 = getTopicXML2(st, topic.types[topic.APPRIORITY2], true);
        String apPriority = "0";
        if (apPriority1.equals("1")) {
            apPriority = "1";
        }
        if (apPriority2.equals("1")) {
            apPriority = "2";
        }

        String topicgamescats = null;

        if (t.phrases) {
            topicgamescats = gamesMainCategories[3];
        } else if (t.phonics && !t.phonicsw) {
            topicgamescats = gamesMainCategories[1];
        } else if (t.phonicsw) {
            topicgamescats = gamesMainCategories[0] + "|" + gamesMainCategories[2];
        } else {
            topicgamescats = gamesMainCategories[0];
        }

        String pairs = getTopicXML2(st, GTX_PAIRS, true);
        String allornone = getTopicXML2(st, GTX_ALLORNONE, true);
        String inorder = getTopicXML2(st, GTX_INORDER, true);
        String fl = getTopicXML2(st, GTX_FL, true);
        String revision = getTopicXML2(st, GTX_REVISION, true);
        String isblended = t.blended ? "1" : "0";
        String phonicSounds = (t.phonics && !t.phonicsw) ? "1" : "0";
        String phonicWords = (t.phonicsw) ? "1" : "0";
        
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement(GTX_ROOT_XML);
            doc.appendChild(rootElement);
            // main topic element
            Element mainTopicElement = doc.createElement(GTX_TOPICROOT_XML);
            rootElement.appendChild(mainTopicElement);
            Attr attr = doc.createAttribute(GTX_TOPICNAME_AT_XML);
            attr.setValue(topicName);
            mainTopicElement.setAttributeNode(attr);

            attr = doc.createAttribute(GTX_APPRIORITY);
            attr.setValue(apPriority);
            mainTopicElement.setAttributeNode(attr);

            attr = doc.createAttribute(GTX_APNOTINTEST);
            attr.setValue(apNotInTest);
            mainTopicElement.setAttributeNode(attr);

            attr = doc.createAttribute(GTX_APNOTINUNITORTEST);
            attr.setValue(apNotInUnitOrTest);
            mainTopicElement.setAttributeNode(attr);
           
            attr = doc.createAttribute(GTX_RECOMMENDEDPHONICONGAMES);
            attr.setValue("dummy_rpg_on");
            mainTopicElement.setAttributeNode(attr);
            attr = doc.createAttribute(GTX_RECOMMENDEDPHONICOFFGAMES);
            attr.setValue("dummy_rpg_off");
            mainTopicElement.setAttributeNode(attr);
            attr = doc.createAttribute(GTX_PHONICONGAMES);
            attr.setValue("dummy_pg_on");
            mainTopicElement.setAttributeNode(attr);
            attr = doc.createAttribute(GTX_PHONICOFFGAMES);
            attr.setValue("dummy_pg_off");
            mainTopicElement.setAttributeNode(attr);
            attr = doc.createAttribute(GTX_SPLITONGAMES);
            attr.setValue("dummy_sg_on");
            mainTopicElement.setAttributeNode(attr);
            attr = doc.createAttribute(GTX_SPLITOFFGAMES);
            attr.setValue("dummy_sg_off");
            mainTopicElement.setAttributeNode(attr);

            attr = doc.createAttribute(GTX_TOPICGAMECATEGORYTYPE);
            attr.setValue(topicgamescats);
            mainTopicElement.setAttributeNode(attr);

            attr = doc.createAttribute(GTX_ISREVISION_AT_XML);
            attr.setValue(revision);
            mainTopicElement.setAttributeNode(attr);

            if (uploadStageUploadRest) {
                attr = doc.createAttribute(GTX_TOPICUUID_AT_XML);
                attr.setValue(String.valueOf(topic_id));
                mainTopicElement.setAttributeNode(attr);
            }
            if (teachingNote != null) {
                attr = doc.createAttribute(GTX_TEACHINGNOTE_AT_XML);
                attr.setValue(teachingNote);
                mainTopicElement.setAttributeNode(attr);
            }
            attr = doc.createAttribute(GTX_HOMOPHONES_AT_XML);
            attr.setValue(homophones);
            mainTopicElement.setAttributeNode(attr);
            attr = doc.createAttribute(GTX_PAIRS_AT_XML);
            attr.setValue(pairs);
            mainTopicElement.setAttributeNode(attr);
            attr = doc.createAttribute(GTX_ALLORNONE_AT_XML);
            attr.setValue(allornone);
            mainTopicElement.setAttributeNode(attr);

            attr = doc.createAttribute(GTX_ISBLENDED_AT_XML);
            attr.setValue(isblended);
            mainTopicElement.setAttributeNode(attr);

            attr = doc.createAttribute(GTX_INORDER_AT_XML);
            attr.setValue(inorder);
            mainTopicElement.setAttributeNode(attr);

            attr = doc.createAttribute(GTX_PHONICS_SINGLE_AT_XML);
            attr.setValue(phonicSounds);
            mainTopicElement.setAttributeNode(attr);
            attr = doc.createAttribute(GTX_PHONICS_WORDS_AT_XML);
            attr.setValue(phonicWords);
            mainTopicElement.setAttributeNode(attr);

            mainTopicElement.setAttributeNode(attr);
            attr = doc.createAttribute(GTX_FL_AT_XML);
            attr.setValue(fl);
            mainTopicElement.setAttributeNode(attr);

            // THE TOPICBLOCKS            
            Element topicBlocksElement = doc.createElement(GTX_TBS_XML);
            rootElement.appendChild(topicBlocksElement);

            word[] ww;
            if (t.name.indexOf("months 7-12") >= 0) {
                int gg;
                gg = 8;
            }
            standardListAllOrNones = new ArrayList();
            ww = getStandardWords(st);
            currStandardWords = ww;
            // do the STANDARD LIST
            for (int i = 0; ww != null && i < ww.length; i++) {;
                if (ww[i].value.startsWith(sentence.TEST_PREFIX)) {
                    continue;  // the sentences for the Wordshark Test course
                }
                word specialSoundWord = null;
                if (ww.length > i + 1 && ww[i + 1].value.startsWith(sentence.TEST_PREFIX)) {
                    specialSoundWord = ww[i + 1];
                }
                Element topicBlockElement = doTopicBlock(doc, topicBlocksElement, TOPICBLOCKSTANDARD_TYPE);
                Element refrencesElement = doc.createElement(GTX_REFERENCES_XML);
                topicBlockElement.appendChild(refrencesElement);
                Element refrenceElement = doc.createElement(GTX_REFERENCE_XML);
                refrencesElement.appendChild(refrenceElement);
                attributeCount = -1;
                attributeIndexCount = -1;
                for (int j = 0; j < standardListAllOrNones.size(); j++) {
                    String sa[] = (String[]) standardListAllOrNones.get(j);
                    int k = u.findString(sa, ww[i].value);
                    if (k >= 0) {
                        attributeCount = j;
                        attributeIndexCount = k;
                        break;
                    }
                }
                if (attributeCount >= 0) {
                    attr = doc.createAttribute(GTX_ALLORNONE_AT_XML);
                    attr.setValue(String.valueOf(attributeCount));
                    refrenceElement.setAttributeNode(attr);
                    attr = doc.createAttribute(GTX_ALLORNONE_INDEX_AT_XML);
                    attr.setValue(String.valueOf(attributeIndexCount));
                    refrenceElement.setAttributeNode(attr);
                }

                if (ww[i].value.startsWith("(")) {
                    continue;
                }

                if (uploadStageUploadWords && jn != null && topic_id >= 0) {
                    doWordNew(ww[i], specialSoundWord, topic_id, null, new int[]{WORD_TYPE_STANDARD});
                }
            }
            standardListAllOrNones = new ArrayList();
            // do the extended
            doExtended(topicTreeList, st, doc, topicBlocksElement);

            if (t.phrases) {
                Element tbEle = doGamesBlocks(st, doc, topicBlocksElement, GAME_SIMPLECROSSWORD, TOPICBLOCKGAME_TYPE, GAME_BLOCK_TYPE_SENTENCE);
                doSentences(st, doc, topic_id, tbEle, 1, topicName, true, true);
            }

            // do the pairs
            ww = getPairs(st);
            if (ww != null) {
                Element topicBlockElement = doTopicBlock(doc, topicBlocksElement, TOPICBLOCKPAIRS_TYPE);
                Element pairsElement = doc.createElement(GTX_PAIRS_XML);
                topicBlockElement.appendChild(pairsElement);
                Element refrencesElement = null;

                for (int i = 0; i < ww.length; i++) {
                    boolean first = i % 2 == 0;
                    if (first) {
                        refrencesElement = doc.createElement(GTX_REFERENCES_XML);
                        pairsElement.appendChild(refrencesElement);
                        Element refrenceElement = doc.createElement(GTX_REFERENCE_XML);
                        refrencesElement.appendChild(refrenceElement);
                        int wordid = doWordNew(ww[i], topic_id, null, new int[]{WORD_TYPE_PAIRS});
                        if (wordid < 0) {
                            int g;
                            g = 0;
                            continue;
                        }
                        String wordids = String.valueOf(wordid) + ",";
                        wordid = doWordNew(ww[i + 1], topic_id, null, new int[]{WORD_TYPE_PAIRS});
                        if (wordid < 0) {
                            int g;
                            g = 0;
                            continue;
                        }
                        wordids += wordid;
                        attr = doc.createAttribute("WordID");
                        attr.setValue(wordids);
                        refrenceElement.setAttributeNode(attr);

                    }
                }
            }
            
            // do games
            doGames(topicTreeList, st, topic_id, doc, topicBlocksElement, TOPICBLOCKGAME_TYPE, topicName);
            currgames = new String[]{};

            if (topicName.indexOf("introduce a") >= 0) {
                int ff;
                ff = 9;
            }

            // get the nonsense rime distractors                    
            String ss[] = getNonsenseRhymeDistractors(st);
            Element topicBlockElement = null;
            Element distractorsElement = null;
            for (int i = 0; ss != null && i < ss.length; i++) {
                if (topicBlockElement == null) {
                    topicBlockElement = doTopicBlock(doc, topicBlocksElement, TOPICBLOCKDISTRACTORNONSENSERIME_TYPE);
                    distractorsElement = doc.createElement(GTX_DISTRACTORS_XML);
                    topicBlockElement.appendChild(distractorsElement);
                }
                Element distractorElement = doc.createElement(GTX_DISTRACTOR_XML);
                distractorsElement.appendChild(distractorElement);
                int b = doWord(new word(ss[i], "publictopics"), t, doc, distractorElement, true, topicName);
                if (b < 0) {
                    int gg;
                    gg = 0;
                }
            }
            ss = getPhonicDistractors(st);
            topicBlockElement = null;
            for (int i = 0; ss != null && i < ss.length; i++) {
                if (topicBlockElement == null) {
                    topicBlockElement = doTopicBlock(doc, topicBlocksElement, TOPICBLOCKDISTRACTORPHONICSOUNDS_TYPE);
                }
                
                String sssounds[] = u.splitString(ss[i], ',');
                if (sssounds != null) {
                    Element soundGroupElement = doc.createElement(GTX_PHONIC_DISRACTORS_SOUNDGROUP);
                    for (int j = 0; j < sssounds.length; j++) {
                        Element soundElement = doc.createElement(GTX_PHONIC_DISRACTORS_SOUND);
                        attr = doc.createAttribute(GTX_PHONIC_DISRACTORS_SOUNDNAME_AT_XML);
                        soundElement.setAttributeNode(attr);
                        String sound = sssounds[j] + "~";
                        String s1 = tor.findJsonRecording(jsonRecResults, "publicsay1", sound);
                        if (s1 == null) {
                            s1 = tor.findJsonRecording(jsonRecResults, "publicsay1", sound.toLowerCase());
                        }
                        if (s1 == null) {
                            u.okmess(shark.programName, s1 + "no sound");
                        }
                        attr.setValue(s1);
                        if (separateSounds == null) {
                            separateSounds = new String[]{sssounds[j]};
                        } else {
                            separateSounds = u.addString(separateSounds, sssounds[j]);
                        }
                        soundGroupElement.appendChild(soundElement);
                    }
                    topicBlockElement.appendChild(soundGroupElement);
                }
            }

            // get the sounds from the list          
            DOMSource domSource = new DOMSource(doc);
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.transform(domSource, result);
            writer.flush();
            return writer.toString();

        } catch (TransformerException ex) {
            int ff;
            ff = 9;
        } catch (javax.xml.parsers.ParserConfigurationException ex) {
            int ff;
            ff = 9;
        }

        return null;
    }

    int doWordNew(word w, int topic_id, String imageName, int word_type[]) {
        return doWordNew(w, null, null, topic_id, imageName, word_type);
    }

    int doWordNew(word w, word specialSoundWord, int topic_id, String imageName, int word_type[]) {
        return doWordNew(w, specialSoundWord, null, topic_id, imageName, word_type);
    }

    public int doWordNew(word w, word specialSoundWord, String patternText, int topic_id, String imageName, int word_type[]) {
        boolean checkForExcluded = u.inlist(word_type, WORD_TYPE_STANDARD) || t.revision;
        boolean wantPhonicSplits = u.inlist(word_type, WORD_TYPE_STANDARD) || u.inlist(word_type, WORD_TYPE_EXTENDED);
        boolean wantSyllableSplits = u.inlist(word_type, WORD_TYPE_STANDARD) || u.inlist(word_type, WORD_TYPE_EXTENDED);
        if (patternText != null) {
            int h;
            h = 0;
        }
        
        boolean wordsharkImSimpleSentPref = PICPREF_KEYSIMPLESENT.equals(picPreKey) && u.inlist(word_type, WORD_TYPE_SENTENCE) && u.inlist(word_type, WORD_TYPE_SIMPLE);
        boolean isSentFLTargetIm = imageName != null && u.inlist(word_type, WORD_TYPE_SENTENCE) && !u.inlist(word_type, WORD_TYPE_SIMPLE)
                && u.inlist(word_type, WORD_TYPE_FL) && u.inlist(word_type, WORD_TYPE_TARGET);
        boolean wordsharkImagePreference = isSentFLTargetIm || wordsharkImSimpleSentPref;   // preference for wordshark images for crossword1 FL
        boolean photoImagePreference = MYSQLUpload.course.equalsIgnoreCase(WORDSHARKTESTCOURSE)
                || (imageName != null && (u.inlist(word_type, WORD_TYPE_SENTENCE) && u.inlist(word_type, WORD_TYPE_TARGET) && !isSentFLTargetIm));
        if (wordsharkImagePreference && photoImagePreference) {
            u.okmess(shark.programName, "overlapping picture preference", sharkStartFrame.mainFrame);
        }

        boolean requireImage = u.inlist(word_type, WORD_TYPE_SIMPLE) && u.inlist(word_type, WORD_TYPE_SENTENCE) && u.inlist(word_type, WORD_TYPE_FL) && u.inlist(word_type, WORD_TYPE_TARGET);
        boolean dontWantImage = (u.inlist(word_type, WORD_TYPE_SENTENCE)
                && (imageName == null || !u.inlist(word_type, WORD_TYPE_TARGET)));
        boolean isStandard = u.inlist(word_type, WORD_TYPE_STANDARD);
        boolean isExcuded = false;
        if (checkForExcluded) {
            Object o[] = topic.getExcludedWord(u.absoluteToRelative(sharkStartFrame.publicTopicLib[0]),
                    topicTL.getAncestors(tjn, u.absoluteToRelative(sharkStartFrame.publicTopicLib[0])),
                    w.v());
            isExcuded = (int) (o[0]) >= 0;
        }
        if (isExcuded && t.revision) {
            return -1;
        }
        String ss[];
        if (MYSQLUpload.course.equalsIgnoreCase(WORDSHARKTESTCOURSE)
                && specialSoundWord != null
                && specialSoundWord.value.startsWith(sentence.TEST_PREFIX)) {
            ss = new String[]{specialSoundWord.value.substring(sentence.TEST_PREFIX.length()).toLowerCase(), "publicsent4"};
        } else {
            ss = getRecordingWordID2(w, t.fl);
        }
        String soundName = null;
        String imageName_wordshark = null;
        String imageName_photo = null;
        String homophoneName = null;
        String recdesktopname = null;
        String recdbname = null;

        if (ss != null) {
            recdesktopname = ss[0];
            recdbname = ss[1];
            soundName = tor.findJsonRecording(jsonRecResults, recdbname, recdesktopname);
            homophoneName = tor.findJsonRecording(jsonRecResults, recdbname, recdesktopname + "=");
        }
        if (soundName == null) {
            if (u.findString(toBeRecordeds, w.value) < 0) {
                if (w.value.indexOf("internet") >= 0) {
                    int g;
                    g = 0;
                }
                System.out.println("Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£ NO SOUND   " + recdbname + "     " + recdesktopname + "     " + w.value);
            }
        }

        currImageDb = null;
        String im;
        if (imageName == null) {
            im = w.vpic();
        } else {
            im = imageName;
        }
        if (phonicshomo_on) {
            String sh;
            if ((sh = u.gettext("phonicshomos", w.v())) != null) {
                im = sh;
            }
            soundName = getPhonicsHomoMainRec(w.vpic().replace("~", ""), soundName);
            homophoneName = getPhonicsHomoHomoRec(w.vpic().replace("~", ""));
        }
        sharkImage si = null;
        if (!dontWantImage && (!MYSQLUpload.course.equalsIgnoreCase(WORDSHARKTESTCOURSE) || !im.trim().endsWith("@@none"))) {
            if (MYSQLUpload.course.equalsIgnoreCase(WORDSHARKTESTCOURSE)) {
                im = u.getPhotoNameInWordsharkTestCourse(im);
            }
            if (si == null) {
                si = sharkImage.find(im);
            }
            if (currImageDb != null) {
                currImageDb = currImageDb.substring(currImageDb.lastIndexOf(shark.sep) + 1);
            }
            if (si != null) {
                imageName_wordshark = tor.findJsonImage(jsonImageResults, currImageDb, im, true);
            }
            imageName_photo = tor.findJsonImage(jsonImageResults, currImageDb, im, false);
            if (wordsharkImagePreference && imageName_wordshark != null) {
                imageName_photo = imageName_wordshark;
            } else if (photoImagePreference && imageName_photo != null) {
                imageName_wordshark = imageName_photo;
            } else {
                //  so the user setting act more like a preference than a choice. if user has photo set, they may get wordshark image instead
                if (imageName_photo == null && imageName_wordshark != null) {
                    imageName_photo = imageName_wordshark;
                } else if (imageName_wordshark == null && imageName_photo != null) {
                    imageName_wordshark = imageName_photo;
                }
            }
        }
        /*
        File ff[] = new File("C:\\xampp\\htdocs\\img\\publicimages\\new").listFiles();
        for(int i = 0; i < ff.length; i++){ 
            System.out.println(ff[i].getName() + "    " + ff[i].getName() + "      " + tor.getImageFileID(ff[i].getAbsolutePath()));
        
        }
        */
        
        if (requireImage && (imageName_wordshark == null && imageName_photo == null)) {
            System.out.println("^&^&^&^&^&^&^&^&^^&^^&^NO IMAGE      " + w.v() + "      " + im + Arrays.toString(word_type));
        }

        String adjustedValue = adjustedWordValue(w.value);
        int wid = UploadWord2(String.valueOf(topic_id),
                isStandard ? "1" : "0", isExcuded ? "1" : "0", w.v(), wantSyllableSplits ? addSyllableSplits(t.name, adjustedValue) : adjustedValue, patternText, null,
                soundName, imageName_wordshark, imageName_photo, homophoneName, wantPhonicSplits ? getPhonicSplitParts(w, soundName) : null);
        if (wid < 0) {
            int g;
            g = 0;
        }
        return wid;
    }

    int doWord(word w, topic t, Document doc, Element parentElement, String topicName) {
        return doWord(w, t, doc, parentElement, false, false, null, null, null, topicName, null);
    }

    int doWord(word w, topic t, Document doc, Element parentElement, String topicName, int sentIm[]) {
        return doWord(w, t, doc, parentElement, false, false, null, null, null, topicName, sentIm);
    }

    int doWord(word w, topic t, Document doc, Element parentElement, boolean isNonsense, String topicName) {
        return doWord(w, t, doc, parentElement, isNonsense, false, null, null, null, topicName, null);
    }

    int doWord(word w, topic t, Document doc, Element parentElement, boolean isNonsense, boolean isRubbish, String bracketedWord, String root, String suffix) {
        return doWord(w, t, doc, parentElement, isNonsense, isRubbish, bracketedWord, root, suffix, null, null);
    }

    int doWord(word w, topic t, Document doc, Element parentElement, boolean isNonsense, boolean isRubbish, String bracketedWord, String root, String suffix, String topicName, int sentIm[]) {
        if (uploadStageUploadTopicToHeading) {
            return 0;
        }
        if (topicName != null && topicName.equals("garbage")) {
            isRubbish = true;
        }
        String xml2 = null;
        try {
            if (generateImageFiles) {
                System.out.println(t.name + "   " + w.value);
                UploadImage(w.vpic());
            } else {
                DocumentBuilderFactory docFactory2 = DocumentBuilderFactory.newInstance();
                DocumentBuilder docBuilder2 = docFactory2.newDocumentBuilder();
                Document doc2 = docBuilder2.newDocument();
                Element rootElement2 = doc2.createElement(GTX_ROOT_XML);
                doc2.appendChild(rootElement2);
                doWord2(w, -1, -1, -1, -1, t, doc2, rootElement2, isNonsense, isRubbish, bracketedWord, root, suffix);
                DOMSource domSource2 = new DOMSource(doc2);
                StringWriter writer2 = new StringWriter();
                StreamResult result2 = new StreamResult(writer2);
                TransformerFactory tf2 = TransformerFactory.newInstance();
                Transformer transformer = tf2.newTransformer();
                transformer.transform(domSource2, result2);
                writer2.flush();
                xml2 = writer2.toString();
                if (uploadStageUploadWords) {
                    if (u.findString(uploadWordXml, xml2) < 0) {
                        UploadWord(w, t, xml2, false);
                    }
                    UploadImage(w.vpic());
                }
            }
        } catch (Exception ee) {
            int g;
            g = 8;
        }
        if (xml2 == null) {
            int g;
            g = 9;
            return -1;
        }
        if (uploadStageUploadRest) {
            int k = getWordID(xml2);
            int kim = -1;
            int kimbmp = -1;
            int recid = -1;
            if (k < 0) {
                UploadWord(w, t, xml2, true);
                k = getWordID(xml2);
            }
            if (k < 0) {
                return -1;
            } else {
                recid = getRecordingWordID(w.vsay(), t.fl);
                boolean issentencewithnoim = false;
                if (sentIm != null) {
                    for (int i = 0; i < sentIm.length; i++) {
                        if (sentIm[i] < 0) {
                            issentencewithnoim = true;
                            break;
                        }
                    }
                }
                if (sentIm != null) {
                    kim = sentIm[0];
                    kimbmp = sentIm[1];
                } else if (!issentencewithnoim) {
                    kim = getImageID(w.vpic());
                    kimbmp = getImageBMPID(getImageFileIdentifierForWord(w.vpic()));
                }
                if (kim >= 0) {
                    Attr attr = doc.createAttribute(GTX_IMUUID_AT_XML);
                    attr.setValue(String.valueOf(kim));
                    parentElement.setAttributeNode(attr);
                }
                if (kimbmp >= 0) {
                    Attr attr = doc.createAttribute(GTX_IMBMPUUID_AT_XML);
                    attr.setValue(String.valueOf(kimbmp));
                    parentElement.setAttributeNode(attr);
                }
                return doWord2(w, k, kim, kimbmp, recid, t, doc, parentElement, isNonsense, isRubbish, bracketedWord, root, suffix);
            }
        }

        return 0;

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

    int doWord2(word w, int uuid, int imuuid, int imuuidbmp, int recordingid, topic t, Document doc, Element parentElement, boolean isNonsense, boolean isRubbish, String bracketedWord, String root, String suffix) {
        if (true) {
            return -1;
        }

        Element wordElement = doc.createElement(GTX_WORD_XML);
        parentElement.appendChild(wordElement);
        Attr attr = null;
        if (w.v().indexOf("+") >= 0 && (uploadStageUploadWords || uploadStageUploadRest)) {
            int gg;
            gg = 9;
        }
        attr = doc.createAttribute(GTX_WORDNAME_AT_XML);
        attr.setValue(u.formatTextforUpload(w.v(),CURRENT_MODE));
        wordElement.setAttributeNode(attr);

        if (bracketedWord != null) {
            attr = doc.createAttribute(GTX_BRACKETED_WORD_AT_XML);
            attr.setValue(u.formatTextforUpload(bracketedWord,CURRENT_MODE));
            wordElement.setAttributeNode(attr);
        }

        boolean issound = w.phonics && !w.phonicsw;
        boolean islettername = w.value.endsWith("!1");

        attr = doc.createAttribute(GTX_ISSOUND_AT_XML);
        attr.setValue(issound ? "1" : "0");
        wordElement.setAttributeNode(attr);

        attr = doc.createAttribute(GTX_ISLETTERNAME_AT_XML);
        attr.setValue(islettername ? "1" : "0");
        wordElement.setAttributeNode(attr);

        attr = doc.createAttribute(GTX_ISHOMOPHONE_AT_XML);
        attr.setValue(w.homophone ? "1" : "0");
        wordElement.setAttributeNode(attr);
        attr = doc.createAttribute(GTX_ISNONSENSE_AT_XML);
        attr.setValue(isNonsense || t.nonsense ? "1" : "0");
        wordElement.setAttributeNode(attr);
        attr = doc.createAttribute(GTX_ISRUBBISH_AT_XML);
        attr.setValue(isRubbish ? "1" : "0");
        wordElement.setAttributeNode(attr);
        if (root != null) {
            attr = doc.createAttribute(GTX_WORD_ROOT_AT_XML);
            attr.setValue(root);
            wordElement.setAttributeNode(attr);
        }
        if (suffix != null) {
            attr = doc.createAttribute(GTX_WORD_SUFFIX_AT_XML);
            attr.setValue(suffix);
            wordElement.setAttributeNode(attr);
        }

        if (uuid >= 0) {
            attr = doc.createAttribute(GTX_WORDUUID_AT_XML);
            attr.setValue(String.valueOf(uuid));
            wordElement.setAttributeNode(attr);
        }

        if (recordingid >= 0) {
            attr = doc.createAttribute(GTX_RECUUID_AT_XML);
            attr.setValue(String.valueOf(recordingid));
            wordElement.setAttributeNode(attr);
        }
        attr = doc.createAttribute(GTX_WORDITEMTYPE_AT_XML);
        if (t.phrases) {
            attr.setValue(WORDCAPTION_TYPE);
        } else {
            attr.setValue((w.phonics && !w.phonicsw) ? WORDSOUND_TYPE : WORDWORD_TYPE);
        }
        wordElement.setAttributeNode(attr);
        int hr;
        if ((hr = getRecordingWordID(w.v() + "=", false)) >= 0) {
            attr = doc.createAttribute(GTX_HOMOPHONERECUUID_AT_XML);
            attr.setValue(String.valueOf(hr));
            wordElement.setAttributeNode(attr);
        }
        if (t.name.equals("longer words with wh  kn  wr  mb")) {
            int gg;
            gg = 9;
        }

        int n;
        String phoniclysplit[] = null;
        if ((n = w.value.indexOf("=")) > 0) {
            String ps = w.value.substring(0, n);
            phoniclysplit = u.splitString(ps, u.phonicsplits);
        }
        if (phoniclysplit != null) {
            String segs[] = phoniclysplit;
            int splitpos[] = new int[segs.length];
            for (int i = 0; i < splitpos.length; i++) {
                if (i == 0) {
                    splitpos[0] = 0;
                } else {
                    splitpos[i] = splitpos[i - 1] + segs[i - 1].length();
                }
            }
            for (int i = segs.length - 1; i >= 0; i--) {
                if (segs[i].equals("\'")) {
                    segs = u.removeString(segs, i);
                    splitpos = u.removeint(splitpos, i);
                }
            }
            String ps[] = w.phonics();
            for (int i = ps.length - 1; i >= 0; i--) {
                if (ps[i].equals("\'")) {
                    ps = u.removeString(ps, i);
                }
            }
            if (ps.length > 0) {
                boolean found = false;
                for (int p = 0; wordsForSounds != null && p < wordsForSounds.length; p++) {
                    if (wordsForSounds[p].v().equals(w.v())) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    if (wordsForSounds == null) {
                        wordsForSounds = new word[]{w};
                    } else {
                        wordsForSounds = u.addWords(wordsForSounds, w);
                    }
                }
            }

            int phonsylls[] = w.phsplitlist();
            int nps[] = new int[]{};
            if (phonsylls.length > 0) {
                nps = u.addint(nps, 0);
                for (int m = 0; m < phonsylls.length - 1; m++) {
                    nps = u.addint(nps, phonsylls[m]);
                }
            }
            phonsylls = nps;
            int ti = 0;
            for (int j = 0; segs.length > 1 && j < segs.length; j++) {
                Element wordSplitElement = doc.createElement(GTX_WORDSPLIT_XML);
                wordElement.appendChild(wordSplitElement);
                attr = doc.createAttribute(GTX_WORDSPLITTYPE_AT_XML);
                attr.setValue(WORDSPLITPHONIC_TYPE_VALUE);
                wordSplitElement.setAttributeNode(attr);
                attr = doc.createAttribute(GTX_WORDSPLITPOSITION_AT_XML);
                attr.setValue(String.valueOf(splitpos[j]));
                wordSplitElement.setAttributeNode(attr);
                if (!w.isphonicsilent()[j]) {   // not in the case of magic e and so sound is missed off
                    attr = doc.createAttribute(GTX_WORDSPLITSOUNDNAME_AT_XML);
                    attr.setValue(ps[ti]);
                    wordSplitElement.setAttributeNode(attr);
                }
                int pk;
                if ((pk = u.inintlist(phonsylls, ti)) >= 0) {

                    wordSplitElement = doc.createElement(GTX_WORDSPLIT_XML);
                    wordElement.appendChild(wordSplitElement);
                    attr = doc.createAttribute(GTX_WORDSPLITTYPE_AT_XML);
                    attr.setValue(WORDSPLITPHONICSYLL_TYPE_VALUE);
                    wordSplitElement.setAttributeNode(attr);
                    attr = doc.createAttribute(GTX_WORDSPLITPOSITION_AT_XML);

                    int posval = 0;

                    for (int y = 0; pk > 0 && y < j; y++) {
                        posval = posval + segs[y].length();
                    }

                    attr.setValue(String.valueOf(posval));
                    wordSplitElement.setAttributeNode(attr);
                    attr = doc.createAttribute(GTX_WORDSPLITSOUNDNAME_AT_XML);
                    int end = 0;
                    if (pk + 1 >= phonsylls.length) {
                        end = ps.length;
                    } else {
                        end = phonsylls[pk + 1];
                    }
                    String val = "";
                    for (int c = phonsylls[pk]; c < end; c++) {
                        val += ps[c];
                    }
                    attr.setValue(val);
                    wordSplitElement.setAttributeNode(attr);
                }
                if (!w.isphonicsilent()[j]) {
                    ti++;
                }
            }
        }
        int ii[];
        if ((ii = getTopicSplits(t.name, w.value)) != null) {
            for (int k = 0; k < ii.length; k++) {
                Element wordSplitElement = doc.createElement(GTX_WORDSPLIT_XML);
                wordElement.appendChild(wordSplitElement);
                attr = doc.createAttribute(GTX_WORDSPLITTYPE_AT_XML);
                attr.setValue(WORDSPLITSYLL_TYPE_VALUE);
                wordSplitElement.setAttributeNode(attr);
                attr = doc.createAttribute(GTX_WORDSPLITPOSITION_AT_XML);
                attr.setValue(String.valueOf(ii[k]));
                wordSplitElement.setAttributeNode(attr);
            }
        }
        //       }
        return 1;
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

    void doGames(topicTree topicTreeList, saveTree1 st, int topic_id, Document doc, Element parentElement, String blockType, String topicName) {
        mloop:
        for (int j = 0; j < st.curr.names.length; ++j) {
            if (st.curr.levels[j] != 1) {
                continue mloop;
            }

            if (st.curr.names[j].startsWith(GTX_GAMES)) {
                String oriNode = st.curr.names[j];
                String adjustedGamesBlockTitle = adjustGamesBlockTitle(st.curr.names[j]);
                if (adjustedGamesBlockTitle == null) {
                    continue;
                } else if (!st.curr.names[j].equals(adjustedGamesBlockTitle)) {
                    st.curr.names[j] = adjustedGamesBlockTitle;
                }
                int extraGameType = -1;

                // is this a game that deals with root + suffix words
                for (int h = 0; h < GTX_PLUS_GAMES.length; h++) {
                    if (st.curr.names[j].indexOf(GTX_PLUS_GAMES[h]) >= 0) {
                        if (topicName.equals("ey as /ay/")) {
                            int gg;
                            gg = 9;
                        }
                        
                        // are there any root + suffix words in there? - look ahead.
                        int f = j;
                        int flevel = st.curr.levels[f];
                        int forilevel = st.curr.levels[f];
                        boolean foundaplus = false;
                        do {
                            if (st.curr.names[f].indexOf("+") >= 0) {
                                foundaplus = true;
                                break;
                            }
                            f++;
                            flevel = st.curr.levels[f];
                        } while (flevel > forilevel);
                        if (foundaplus) {
                            extraGameType = GAME_BLOCK_TYPE_SUFFIX;
                        }
                        break;
                    }
                }
                boolean isSentenceCrossword = GAME_SENTENCECROSSWORD.toLowerCase().equals(st.curr.names[j].substring(GTX_GAMES.length()).toLowerCase());
                boolean isSimpleCrossword = GAME_SIMPLECROSSWORD.toLowerCase().equals(st.curr.names[j].substring(GTX_GAMES.length()).toLowerCase());
                if (isSentenceCrossword || isSimpleCrossword) {
                    String gamesstr = st.curr.names[j];
                    String gamessetother = "";
                    if (isSimpleCrossword) {
                        // add the other games which have the needsentence3 flag
                        gamessetother = GTX_GAMES;
                        for (int i = 0; i < simpleSentence3Games.length; i++) {
                            gamesstr += "," + simpleSentence3Games[i];
                        }
                    } else if (isSentenceCrossword) {
                        // add the other games which have the needsentence1 flag (just save the sharks)
                        for (int i = 0; i < simpleSentence1Games.length; i++) {
                            gamesstr += ("," + simpleSentence1Games[i]);
                        }
                    }

                    // if simple sentences, do twice for full sentence games and beep sentence game
                    //               for(int n = 0; (gamessetother.length()>0||n==0) && n < 2; n++){
                    //                  if(n==1)recDb = "publicsay3";// simple sentence full (no beep)
                    Element tbEle = doGamesBlocks(st, doc, parentElement, gamesstr.substring(GTX_GAMES.length()), blockType, GAME_BLOCK_TYPE_SENTENCE);
                    doSentences(st, doc, topic_id, tbEle, j, topicName, isSimpleCrossword, false);
                    //              }      

                } else {
                    j = doGame2(topicTreeList, st, doc, parentElement, blockType, j, extraGameType, oriNode) - 1;
                }
                int f;
                f = 0;
            }
        }
    }

    void doSentences(saveTree1 st, Document doc, int topic_id, Element parentElement, int j, String topicName, boolean isSimpleCrossword, boolean isCaptions) {
        if (topicName.equals("-ar-")) {
            int g;
            g = 99;
        }
        if (MYSQLUpload.uploadStageUploadRest) {
            int g;
            g = 99;
        }
        if (topicName.equals("the family 1")) {
            int f;
            f = 9;
        }

        Element sentencesElement = doc.createElement(GTX_SENTENCES_XML);
        parentElement.appendChild(sentencesElement);
        if (!isCaptions) {
            j++;
        }
        int baselev = st.curr.levels[j];
        while (j < st.curr.names.length && st.curr.levels[j] >= baselev && (!isCaptions || isCaption(st.curr.names[j]))) {
            Attr attr = null;
            if (st.curr.names[j].startsWith(topic.types[topic.SELECTDISTRACTORS])) {
                // TODO not tested - add to xml
                int sentenceDistractorNo = u.getint(st.curr.names[j].substring(topic.types[topic.SELECTDISTRACTORS].length()));
                attr = doc.createAttribute("DistractorNo");
                attr.setValue(String.valueOf(sentenceDistractorNo));
                sentencesElement.setAttributeNode(attr);
                j++;
                continue;
            }
            Element sentenceElement = doc.createElement(GTX_SENTENCE_XML);
            sentencesElement.appendChild(sentenceElement);
            String senttype = getSentenceType(st.curr.names[j]);

            String senttext = senttext = st.curr.names[j];
            if (senttext.indexOf("i will wear a hat") >= 0) {
                int g;
                g = 0;
            }
            if (!isCaptions) {
                attr = doc.createAttribute(GTX_SENTENCE_TEXT_AT_XML);
                sentence sentpl = (new sentence(st.curr.names[j], null));
                int p;
                if ((p = senttext.indexOf('{')) >= 0) {
                    senttext = senttext.substring(0, p);
                }
                attr.setValue(u.formatTextforUpload(sentpl.stripclozereplacewildcard(),CURRENT_MODE));
                sentenceElement.setAttributeNode(attr);
            }
            if (isSimpleCrossword) {
                attr = doc.createAttribute(GTX_SENTENCE_PLAIN_TEXT_AT_XML);
                sentence sentpl = (new sentence(st.curr.names[j], null));
                String splain = sentpl.stripcloze();
                attr.setValue(u.formatTextforUpload(splain,CURRENT_MODE));
                sentenceElement.setAttributeNode(attr);
            }
            String ssTargets[];
            String ss[];
            Element mainE = null;
            String sentenceDistractors[] = null;
            if (!isCaptions) {
                String targetImages[] = getSentenceTargetImages(st.curr.names[j]);
                String temps[] = getSentenceTargetWords(st.curr.names[j], senttype, false);
                String tempsWithAts[] = getSentenceTargetWords(st.curr.names[j], senttype, true);
                ssTargets = ss = tempsWithAts;
                int ii[] = new int[ss.length];

                for (int i = 0; ss != null && i < ss.length; i++) {
                    if (mainE == null) {
                        mainE = doc.createElement(GTX_TARGETS_XML);
                        sentenceElement.appendChild(mainE);
                    }
                    Element eleSub = doc.createElement(GTX_TARGET_XML);
                    String imName = null;
                    if (targetImages != null && targetImages.length == ss.length) {
                        imName = targetImages[i];
                    }

                    int iiarr[] = new int[]{WORD_TYPE_TARGET, WORD_TYPE_SENTENCE};
                    if (isSimpleCrossword) {
                        iiarr = u.addint(iiarr, WORD_TYPE_SIMPLE);
                    }
                    if (t.fl) {
                        iiarr = u.addint(iiarr, WORD_TYPE_FL);
                    }

                    String cword = ss[i];
                    for (int p = 0; currStandardWords != null && p < currStandardWords.length; p++) {
                        if (currStandardWords[p].v().toLowerCase().equals(cword.toLowerCase())) {
                            cword = currStandardWords[p].v();
                        }
                    }
                    if ((sentenceDistractors = getSentenceDistractorWords(st.curr.names[j], senttype)) != null) {
                        // convert the explicit sentence distractors to the correct case based on the target word
                        for (int p = 0; p < sentenceDistractors.length; p++) {
                            boolean isTargetWordCapital = Character.isUpperCase(cword.charAt(0));
                            boolean isDistractorCapital = Character.isUpperCase(sentenceDistractors[p].charAt(0));
                            if (isTargetWordCapital != isDistractorCapital) {
                                String d = sentenceDistractors[p];
                                if (isTargetWordCapital) {
                                    sentenceDistractors[p] = d.substring(0, 1).toUpperCase() + d.substring(1);
                                } else {
                                    sentenceDistractors[p] = d.substring(0, 1).toLowerCase() + d.substring(1);
                                }
                            }
                        }
                    }

                    ii[i] = doWordNew(new word(cword, "publictopics"), topic_id, imName, iiarr);
                    attr = doc.createAttribute("WordID");
                    attr.setValue(String.valueOf(ii[i]));
                    eleSub.setAttributeNode(attr);
                    mainE.appendChild(eleSub);
                }
            } else {
                // no photographs for captions
                String im = findImageinStandardList(new word(st.curr.names[j], "publictopics"));
                sharkImage si = sharkImage.find(im);
                if (currImageDb != null) {
                    currImageDb = currImageDb.substring(currImageDb.lastIndexOf(shark.sep) + 1);
                }
                String imageName = null;
                if (si != null) {
                    imageName = tor.findJsonImage(jsonImageResults, currImageDb, im, true);
                    if (imageName == null) {
                        u.okmess(shark.programName, "caption - no image");
                    }
                    attr = doc.createAttribute(GTX_SENTENCE_IMAGE_AT_XML);
                    attr.setValue(u.formatTextforUpload(imageName,CURRENT_MODE));
                    sentenceElement.setAttributeNode(attr);
                }
            }

            String recDb;

            if (t.fl) {
                recDb = "publicsent3";
            } else if (isCaptions) {
                recDb = "publicsay1";
            } else {
                recDb = "publicsent2";
            }

            String searchSentText = senttext.toLowerCase();
            searchSentText = searchSentText.replace("|", " ");
            String s1r = tor.findJsonRecording(jsonRecResults, recDb, searchSentText);
            if (s1r == null) {
                if (u.findString(toBeRecordeds, searchSentText) < 0) {
                    System.out.println("**@@@@@@@****NOJSONSENTENCESOUND*****************" + searchSentText);
                }
            } else {
                attr = doc.createAttribute(isCaptions ? GTX_SENTENCE_RECNAMENOPEEP_AT_XML : GTX_SENTENCE_RECNAME_AT_XML);
                attr.setValue(s1r);
                sentenceElement.setAttributeNode(attr);
            }
            if (isSimpleCrossword && !isCaptions) {
                recDb = "publicsay3";
                sentence sent = new sentence(st.curr.names[j], recDb);
                String ss3 = sent.stripcloze();

                String searchSimpSentText = ss3.toLowerCase();
                searchSimpSentText = searchSimpSentText.replace("|", " ");

                s1r = tor.findJsonRecording(jsonRecResults, recDb, searchSimpSentText);
                if (s1r == null) {
                    System.out.println("**@@@@@@@****NOJSONSENTENCESOUND2222*****************" + ss3);
                } else {
                    attr = doc.createAttribute(GTX_SENTENCE_RECNAMENOPEEP_AT_XML);
                    attr.setValue(s1r);
                    sentenceElement.setAttributeNode(attr);
                }
            }

            if (sentenceDistractors != null) {
                // ssTargets
                ss = sentenceDistractors;
            } else {
                ss = getSentenceDistractorWords(st.curr.names[j], senttype);
            }
            mainE = null;
            for (int i = 0; ss != null && i < ss.length; i++) {
                if (mainE == null) {
                    mainE = doc.createElement(GTX_DISTRACTORS_XML);
                    sentenceElement.appendChild(mainE);
                }
                Element eleSub = doc.createElement(GTX_DISTRACTOR_XML);
                mainE.appendChild(eleSub);
                //              word w1 = new word(ss[i].toLowerCase(),"publictopics");
                String cword = ss[i];
                for (int p = 0; currStandardWords != null && p < currStandardWords.length; p++) {
                    if (currStandardWords[p].v().toLowerCase().equals(cword.toLowerCase())) {
                        cword = currStandardWords[p].v();
                    }
                }
                word w1 = new word(cword, "publictopics");
                String im = findImageinStandardList(w1);
                int iiarr[] = new int[]{WORD_TYPE_SENTENCE};
                if (isSimpleCrossword) {
                    iiarr = u.addint(iiarr, WORD_TYPE_SIMPLE);
                }
                if (t.fl) {
                    iiarr = u.addint(iiarr, WORD_TYPE_FL);
                }

                int b = doWordNew(w1, topic_id, im, iiarr);
                attr = doc.createAttribute("WordID");
                attr.setValue(String.valueOf(b));
                eleSub.setAttributeNode(attr);
                if (b < 0) {
                    int gg;
                    gg = 9;
                }
            }
            j++;
        }
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
        ret = u.formatTextforUpload(ret,CURRENT_MODE);
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

    int doGame2(topicTree topicTreeList, saveTree1 st, Document doc, Element parentElement, String blockType, int j, int extraGameBlockType, String oriNodeName) {
        Element tbEle = null;
        int oriLev = st.curr.levels[j];
        String games = st.curr.names[j].substring(GTX_GAMES.length());
        String ngs[] = null;
        int count = 1;
        boolean snakesFound = false;
        boolean patternFound = false;
        if (games != null && !games.trim().equals("") && (ngs = u.splitString(games, ",")).length > 0) {
            for (int i = 0; i < ngs.length; i++) {
                if (ngs[i].toLowerCase().equals(GAME_PATTERN.toLowerCase()) || ngs[i].toLowerCase().equals(GAME_TRACKING.toLowerCase())) {
                    patternFound = true;
                } else if (ngs[i].toLowerCase().equals(GAME_SNAKESANDLADDERS.toLowerCase())) {
                    snakesFound = true;
                }
            }
            // need to double up on these due to the different select_count numbers
            if (snakesFound && patternFound) {
                count = 2;
            }
        }
        String gameStr = st.curr.names[j];

        int orij = j;
        for (int n = 0; ngs != null && n < count; n++) {
            if (snakesFound && patternFound) {
                if (n == 1) {
                    j = orij;
                    String newgs[] = u.removeString2(ngs, GAME_PATTERN.toLowerCase());
                    newgs = u.removeString2(ngs, GAME_PATTERN);
                    gameStr = GTX_GAMES + u.combineString(newgs, ",");
                } else {
                    String newgs[] = u.removeString2(ngs, GAME_SNAKESANDLADDERS.toLowerCase());
                    newgs = u.removeString2(ngs, GAME_SNAKESANDLADDERS);
                    gameStr = GTX_GAMES + u.combineString(newgs, ",");
                }
            }

            tbEle = doGamesBlocks(st, doc, parentElement, gameStr.substring(GTX_GAMES.length()), blockType, extraGameBlockType);
            if (tbEle == null) {
                return j;
            }

            String gType = tbEle.getAttribute(GTX_TB_NUM_AT_GAMEBLOCKTYPE);

            if (gType.equals(TOPICBLOCKGAMETYPE_HELICOPTERLISTEN)
                    || gType.equals(TOPICBLOCKGAMETYPE_HELICOPTERSPELL)
                    || gType.equals(TOPICBLOCKGAMETYPE_PATTERN)
                    || gType.equals(TOPICBLOCKGAMETYPE_PATTERNSNAKES)) {

                int selectNoTarget = -1;
                int selectNoDistractor = -1;
                int allocNoGood = -1;
                int allocNoBad = -1;
                if (gType.equals(TOPICBLOCKGAMETYPE_PATTERN)) {
                    selectNoTarget = selectCountTargetNoPattern;
                    selectNoDistractor = selectCountDistractorNoPattern;
                    allocNoGood = pattern.ALLOCGOOD;
                    allocNoBad = pattern.ALLOCBAD;
                } else if (gType.equals(TOPICBLOCKGAMETYPE_PATTERNSNAKES)) {
                    selectNoTarget = selectCountTargetNoSnakesAndLadders;
                    selectNoDistractor = selectCountDistractorNoSnakesAndLadders;
                    allocNoGood = snakesandladders.ALLOCGOOD;
                    allocNoBad = snakesandladders.ALLOCBAD;
                }
                t.clearHeadingLists();
                selectGameDetails gs[] = t.getSelectGameBuckets(ngs,
                        gType.equals(TOPICBLOCKGAMETYPE_HELICOPTERLISTEN) || gType.equals(TOPICBLOCKGAMETYPE_HELICOPTERSPELL),
                        selectNoTarget,
                        selectNoDistractor,
                        allocNoGood,
                        allocNoBad,
                        this
                );
                int selectNo = 0;
                Element lastEle = tbEle;
                if (gs.length > 1) {
                    Element selectsElement = doc.createElement(GTX_SELECT_XML + String.valueOf(selectNo++));
                    tbEle.appendChild(selectsElement);
                    lastEle = selectsElement;
                    Attr attr = doc.createAttribute(GTX_SELECTGROUPNO_AT_XML);
                    attr.setValue("1");
                    lastEle.setAttributeNode(attr);
                }
                int startSelNo = selectNo;
                for (int h = 0; h < gs.length; h++) {
                    selectNo = startSelNo;
                    Element selectsElement2 = doc.createElement(GTX_SELECT_XML + String.valueOf(selectNo++));
                    lastEle.appendChild(selectsElement2);
                    for (int i = 0; i < gs[h].groups.length; i++) {
                        Element selectsElement3 = doc.createElement(GTX_SELECT_XML + String.valueOf(selectNo));
                        selectsElement2.appendChild(selectsElement3);
                        Attr attrh = doc.createAttribute(GTX_HEADING_AT_XML);
                        attrh.setValue(u.formatTextforUpload(gs[h].groups[i].heading,CURRENT_MODE));
                        selectsElement3.setAttributeNode(attrh);
                        if (gs[h].groups[i].headingSoundFile != null) {
                            Attr attrhs = doc.createAttribute(GTX_HEADING_AT_SOUND_UUID);
                            attrhs.setValue(gs[h].groups[i].headingSoundFile);
                            selectsElement3.setAttributeNode(attrhs);
                        }
                        if (gs[h].groups[i].words.length >= 0) {
                            Element referenceElement = doc.createElement(GTX_REFERENCES_XML + String.valueOf(selectNo));
                            selectsElement3.appendChild(referenceElement);
                            String refs = u.combineString(gs[h].groups[i].words, ",");
                            Attr attrrefs = doc.createAttribute("WordIDs");
                            attrrefs.setValue(refs);
                            referenceElement.setAttributeNode(attrrefs);
                            if (gs[h].groups[i].selectNo >= 0) {
                                Attr attrsel = doc.createAttribute(GTX_SELECTNO_AT_XML);
                                attrsel.setValue(String.valueOf(gs[h].groups[i].selectNo));
                                referenceElement.setAttributeNode(attrsel);
                            }
                        }
                    }
                }
                j++;
                while (j < st.curr.levels.length && st.curr.levels[j] > oriLev) {
                    j++;
                }
            }
        }

        return j;
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

    static Element doGamesBlocks(saveTree1 st, Document doc, Element parentElement, String names, String blockType) {
        return doGamesBlocks(st, doc, parentElement, names, blockType, -1);
    }

    static Element doGamesBlocks(saveTree1 st, Document doc, Element parentElement, String names, String blockType, int gameBlockType) {
        currgames = new String[]{};
        if (names == null) {
            return null;
        }
        String ngs[];
        Element tbElement = null;
        if (names != null && !names.trim().equals("") && (ngs = u.splitString(names, ",")).length > 0) {
            String gameType = null;
            for (int k = 0; gameType == null && k < ngs.length; k++) {
                if (ngs[k].toLowerCase().equals(GAME_HELICOPTERLISTEN.toLowerCase())) {
                    gameType = TOPICBLOCKGAMETYPE_HELICOPTERLISTEN;
                } else if (ngs[k].toLowerCase().equals(GAME_HELICOPTERSPELL.toLowerCase())) {
                    gameType = TOPICBLOCKGAMETYPE_HELICOPTERSPELL;
                } else if (ngs[k].toLowerCase().equals(GAME_PATTERN.toLowerCase()) || ngs[k].toLowerCase().equals(GAME_TRACKING.toLowerCase())) {
                    gameType = TOPICBLOCKGAMETYPE_PATTERN;
                } else if (ngs[k].toLowerCase().equals(GAME_SNAKESANDLADDERS.toLowerCase())) {
                    gameType = TOPICBLOCKGAMETYPE_PATTERNSNAKES;
                }
            }
            tbElement = doTopicBlock(doc, parentElement, blockType, gameType);
            if (gameBlockType == GAME_BLOCK_TYPE_SENTENCE) {
                String lowercasengs[] = new String[ngs.length];
                for (int m = 0; m < ngs.length; m++) {
                    lowercasengs[m] = ngs[m].toLowerCase();
                }

                Attr attr = doc.createAttribute(GTX_GAMEBLOCKTYPE_AT_XML);
                String game = null;
                if (u.findString(lowercasengs, GAME_SENTENCECROSSWORD.toLowerCase()) >= 0) {
                    game = TOPICBLOCKGAMETYPE_CROSSWORD;
                }
                if (u.findString(lowercasengs, GAME_SIMPLECROSSWORD.toLowerCase()) >= 0) {
                    game = TOPICBLOCKGAMETYPE_SIMPLECROSSWORD;
                }
                if (game == null) {
                    u.okmess(shark.programName, "problem with sentences");
                }
                attr.setValue(game);
                tbElement.setAttributeNode(attr);
            }
            if (gameBlockType == GAME_BLOCK_TYPE_SUFFIX) {
                Attr attr = doc.createAttribute(GTX_GAMEBLOCKTYPE_AT_XML);
                attr.setValue(TOPICBLOCKGAMESUFFIX_TYPE);
                tbElement.setAttributeNode(attr);
            }
            //remove duplicates and empties
            String notgamess[] = new String[]{};
            for (int j = 0; j < ngs.length; j++) {
                if (u.findString(notgamess, ngs[j]) < 0 && !ngs[j].trim().equals("")) {
                    notgamess = u.addString(notgamess, ngs[j]);
                }
            }

            for (int j = 0; j < notgamess.length; j++) {
                String ng = notgamess[j].trim();
                char c = ng.charAt(0);
                if (Character.isLowerCase(c)) {
                    ng = String.valueOf(c).toUpperCase() + ng.substring(1);
                }
                if (u.findString(gameNames, ng) < 0) {
                    continue;
                }
                int i;
                String spgc = null;
                if (ng.endsWith("^") && (i = ng.substring(0, ng.length() - 2).indexOf("^")) > 0) {
                    spgc = ng.substring(i + 1, ng.length() - 1);
                    ng = ng.substring(0, i);
                }

                if (blockType.equals(TOPICBLOCKRECOMMENDED2GAMES_TYPE) || blockType.equals(TOPICBLOCKRECOMMENDEDGAMES_TYPE)) {

                } else {
                    jnode isncs[] = sharkStartFrame.mainFrame.publicGameTree.find2(ng);
                    String preng = ng;
                    ng = u.formatTextforUpload(ng,CURRENT_MODE);
                    tbElement.appendChild(doGamesBlocks2(st, doc, ng, spgc, isncs[0]));
                    if (u.findString(currgames, preng) < 0) {
                        currgames = u.addString(currgames, preng);
                    }               
                }
            }
        }
        return tbElement;
    }

    static Element doGamesBlocks2(saveTree1 st, Document doc, String ng, String spgc, jnode jn) {
        Element ngElement = doc.createElement(GTX_GAME_XML);
        Attr attr = doc.createAttribute(GTX_GAMENAME_AT_XML);
        attr.setValue(ng);
        ngElement.setAttributeNode(attr);
        attr = doc.createAttribute(GTX_GAMEISHEADING_AT_XML);
        if (jn == null) {
            int ff;
            ff = 8;
        }
        attr.setValue((jn == null || jn.isLeaf()) ? "0" : "1");
        ngElement.setAttributeNode(attr);
        String gc;
        if (spgc != null) {
            int t = -1;
            try {
                t = Integer.parseInt(spgc);
            } catch (Exception e) {
                int gg;
                gg = 9;
            }
            if (t >= 0) {
                attr = doc.createAttribute(GTX_GAMECATEGORY_AT_XML);
                attr.setValue(String.valueOf(t));
                ngElement.setAttributeNode(attr);
            }
        } else if ((gc = getGamesCategory(jn)) != null) {
            attr = doc.createAttribute(GTX_GAMECATEGORY_AT_XML);
            attr.setValue(gc);
            ngElement.setAttributeNode(attr);
        }
        return ngElement;
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

    void doExtended(topicTree topicTreeList, saveTree1 st, Document doc, Element parentElement) {
        lastMainGameSelects = null;
        mloop:
        for (int j = 0; j < st.curr.names.length; ++j) {
            if (st.curr.levels[j] != 1) {
                continue mloop;
            }
            if (!isSelect(st.curr.names[j])) {
                continue mloop;
            }
            
            Element topicBlockElement = doTopicBlock(doc, parentElement, TOPICBLOCKEXTENDED_TYPE);
            // add selects
            htext = null;
            initSelectLevelAdjuster(st.curr.levels[j], 0);
            doSelects(topicTreeList, st, doc, topicBlockElement, null, st.curr, j, j, 0);
        }
    }
    
    JSONObject doExtendedJson(topicTree topicTreeList, saveTree1 st) {
        
        JSONObject extendedBlock = new JSONObject();
        JSONArray extendedArray = new JSONArray();
        
        lastMainGameSelects = null;
        mloop:
        for (int j = 0; j < st.curr.names.length; ++j) {
            if (st.curr.levels[j] != 1) {
                continue mloop;
            }
            if (!isSelect(st.curr.names[j])) {
                continue mloop;
            }
            
            
            JSONObject extended = doTopicBlockJson(TOPICBLOCKEXTENDED_TYPE);
            
            // add selects
            htext = null;
            initSelectLevelAdjuster(st.curr.levels[j], 0);
            doSelectsJson(topicTreeList, st, extended, null, st.curr, j, j, 0);
            
            
            extendedArray.add(extended);
            
        }
        extendedBlock.put("extended", extendedArray);
       
        return extendedBlock;
    }

    static Element doTopicBlock(Document doc, Element parentElement, String topicBlockType) {
        return doTopicBlock(doc, parentElement, topicBlockType, null);
    }
    
    static JSONObject doTopicBlockJson(String topicBlockType) {
        return doTopicBlockJson(topicBlockType, null);
    }

    static Element doTopicBlock(Document doc, Element parentElement, String topicBlockType, String gameType) {
        Element topicBlockElement = doc.createElement(GTX_TB_XML);
        parentElement.appendChild(topicBlockElement);
        Attr attr = doc.createAttribute(GTX_TB_TYPE_AT_XML);
        attr.setValue(topicBlockType);
        topicBlockElement.setAttributeNode(attr);
        if (gameType != null) {
            attr = doc.createAttribute(GTX_TB_NUM_AT_GAMEBLOCKTYPE);
            attr.setValue(gameType);
            topicBlockElement.setAttributeNode(attr);
        }
        return topicBlockElement;
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

    int doSelects(topicTree topicTreeList, saveTree1 st, Document doc, Element parentElement, String headingText, saveTree1.saveTree2 st2, int j, int selectsj, int type) {
        return doSelects(topicTreeList, st, doc, parentElement, headingText, st2, j, selectsj, type, null);
    }
    
    int doSelectsJson(topicTree topicTreeList, saveTree1 st, JSONObject doc, String headingText, saveTree1.saveTree2 st2, int j, int selectsj, int type) {
        return doSelectsJson(topicTreeList, st, doc, headingText, st2, j, selectsj, type, null);
    }

    int doSelects(topicTree topicTreeList, saveTree1 st, Document doc, Element parentElement, String headingText, saveTree1.saveTree2 st2, int j, int selectsj, int extraGameBlockType, String games[]) {
        int newbase = st.curr.levels[j];
        int headingtextbase = -1;
        String selno = (st.curr.names[j].substring(st.curr.names[j].indexOf(":") + 1));
        try {
            Integer.parseInt(selno);
        } catch (Exception e) {
            if (st.curr.names[j].startsWith(topic.types[topic.SELGROUPS]) || st.curr.names[j].startsWith(topic.types[topic.SELITEMS])) {
                selno = null;
            }
        }
        int ti = doSelectLevelAdjuster(st.curr.levels[j] - selectLevelAdjuster);
        Element selectsElement = doc.createElement(GTX_SELECT_XML + String.valueOf(ti));
        lastActualSelectLevel = ti;

        parentElement.appendChild(selectsElement);
        Attr attr;
        if (selno != null && !selno.trim().equals("")) {
            String gatt = getSelectAtt(st.curr.names[j]);
            attr = doc.createAttribute(gatt + String.valueOf(ti));
            attr.setValue(selno);
            selectsElement.setAttributeNode(attr);
        }
        if (headingText != null) {
            int in;
            if (headingText.startsWith("=") && (in = headingText.indexOf(',')) >= 0) {
                String soundid = headingText.substring(1, in);
                int in2 = getRecordingSoundID(soundid + "~");
                if (in2 >= 0) {
                    attr = doc.createAttribute(GTX_HEADING_AT_SOUND_UUID + String.valueOf(ti));
                    attr.setValue(String.valueOf(in2));
                    selectsElement.setAttributeNode(attr);
                } else {
                    int gg;
                    gg = 9;
                }
                headingText = headingText.substring(in + 1);
            }
            attr = doc.createAttribute(GTX_HEADING_AT_XML);
            attr.setValue(u.formatTextforUpload(headingText,CURRENT_MODE));

            selectsElement.setAttributeNode(attr);
        }
        if (isSelect(st.curr.names[j])) {
            j++;
        }
        selloop:
        do {
            if (st.curr.names[j].trim().equals("")) {
                j++;
                continue;
            }
            if (st.curr.levels[j] <= headingtextbase) {
                htext = null;
            }
            if (isSelect(st.curr.names[j])) {
                if (!(st.curr.levels.length > j + 1 && st.curr.levels[j] >= st.curr.levels[j + 1])) {
                    j = doSelects(topicTreeList, st, doc, selectsElement, htext, st2, j, selectsj, extraGameBlockType, games);
                }
            } else if (st.curr.names[j].startsWith(GTX_HEADING)) {
                htext = u.formatTextforUpload(st.curr.names[j].substring(GTX_HEADING.length()),CURRENT_MODE);
                if (st.curr.names.length > j + 1 && isSelect(st.curr.names[j + 1])) {
                    headingtextbase = st.curr.levels[j];
                } else {
                    j = doSelects(topicTreeList, st, doc, selectsElement, htext, st2, j + 1, selectsj, extraGameBlockType, games);
                }
            } else {
                htext = null;
                j = doReferences(topicTreeList, st, doc, selectsElement, st2, j, extraGameBlockType, String.valueOf(ti), games);
                if (j < st.curr.levels.length) {
                    if (isSelect(st.curr.names[j])) {
                        if (st.curr.levels[j] > st.curr.levels[selectsj]) {
                            return j - 1;
                        } else {
                            return j - 1;
                        }
                    } else if (st.curr.levels[j] <= st.curr.levels[selectsj]) {
                        return j - 1;
                    } else if (st.curr.names[j].startsWith(GTX_HEADING)) {
                        return j - 1;
                    }
                }
            }
            j++;
        } while (j < st.curr.levels.length && st.curr.levels[j] > newbase);
        return j - 1;
    }
    
    
    int doSelectsJson(topicTree topicTreeList, saveTree1 st, JSONObject doc, String headingText, saveTree1.saveTree2 st2, int j, int selectsj, int extraGameBlockType, String games[]) {
        int newbase = st.curr.levels[j];
        int headingtextbase = -1;
        String selno = (st.curr.names[j].substring(st.curr.names[j].indexOf(":") + 1));
        try {
            Integer.parseInt(selno);
        } catch (Exception e) {
            if (st.curr.names[j].startsWith(topic.types[topic.SELGROUPS]) || st.curr.names[j].startsWith(topic.types[topic.SELITEMS])) {
                selno = null;
            }
        }
        int ti = doSelectLevelAdjuster(st.curr.levels[j] - selectLevelAdjuster);
        
        JSONObject select = new JSONObject();
        
        if (selno != null && !selno.trim().equals("")) {
            select.put(getSelectAtt(st.curr.names[j]), selno);
        }

        lastActualSelectLevel = ti;
        
        if (headingText != null) {
            int in;
            if (headingText.startsWith("=") && (in = headingText.indexOf(',')) >= 0) {
                String soundid = headingText.substring(1, in);
                int in2 = getRecordingSoundID(soundid + "~");
                if (in2 >= 0) {
                    select.put(GTX_HEADING_AT_SOUND_UUID, String.valueOf(in2));
                }
                headingText = headingText.substring(in + 1);
            }
            select.put(GTX_HEADING_AT_XML, u.formatTextforUpload(headingText,CURRENT_MODE));
        }

        if (isSelect(st.curr.names[j])) {
            j++;
        }
        selloop:
        do {
            if (st.curr.names[j].trim().equals("")) {
                j++;
                continue;
            }
            if (st.curr.levels[j] <= headingtextbase) {
                htext = null;
            }
            if (isSelect(st.curr.names[j])) {
                if (!(st.curr.levels.length > j + 1 && st.curr.levels[j] >= st.curr.levels[j + 1])) {
                    j = doSelectsJson(topicTreeList, st, select, htext, st2, j, selectsj, extraGameBlockType, games);
                }
            } else if (st.curr.names[j].startsWith(GTX_HEADING)) {
                htext = u.formatTextforUpload(st.curr.names[j].substring(GTX_HEADING.length()),CURRENT_MODE);
                if (st.curr.names.length > j + 1 && isSelect(st.curr.names[j + 1])) {
                    headingtextbase = st.curr.levels[j];
                } else {
                    j = doSelectsJson(topicTreeList, st, select, htext, st2, j + 1, selectsj, extraGameBlockType, games);
                }
            } else {
                htext = null;
                j = doReferencesJson(topicTreeList, st, select, st2, j, extraGameBlockType, String.valueOf(ti), games);
                if (j < st.curr.levels.length) {
                    if (isSelect(st.curr.names[j])) {
                        if (st.curr.levels[j] > st.curr.levels[selectsj]) {
                            return j - 1;
                        } else {
                            return j - 1;
                        }
                    } else if (st.curr.levels[j] <= st.curr.levels[selectsj]) {
                        return j - 1;
                    } else if (st.curr.names[j].startsWith(GTX_HEADING)) {
                        return j - 1;
                    }
                }
            }
            j++;
        } while (j < st.curr.levels.length && st.curr.levels[j] > newbase);

        return j - 1;

    }

    int doSelectsGame(topicTree topicTreeList, saveTree1 st, Document doc, Element gameElement, Element parentElement, String headingText, saveTree1.saveTree2 st2, int j, int selectsj, int extraGameBlockType, String games[]) {
        int newbase = st.curr.levels[j];
        int headingtextbase = -1;

        String selno = (st.curr.names[j].substring(st.curr.names[j].indexOf(":") + 1));
        try {
            Integer.parseInt(selno);
        } catch (Exception e) {
            if (!(st.curr.names[j].startsWith(topic.types[topic.SELGROUPS]) || st.curr.names[j].startsWith(topic.types[topic.SELITEMS]))) {
                selno = null;
            }
        }
        int ti = doSelectLevelAdjuster(st.curr.levels[j] - selectLevelAdjuster);
        Element selectsElement = doc.createElement(GTX_SELECT_XML + String.valueOf(ti));
        lastActualSelectLevel = ti;

        parentElement.appendChild(selectsElement);
        Attr attr;
        boolean isGroupSelect = false;
        if (selno != null && !selno.trim().equals("")) {
            String gatt = getSelectAtt(st.curr.names[j]);
            isGroupSelect = GTX_SELECTGROUPNO_AT_XML.equals(gatt);
            if (isGroupSelect) {
                int g;
                g = 0;
            }
            attr = doc.createAttribute(gatt);
            attr.setValue(selno);
            selectsElement.setAttributeNode(attr);
        }
        if (headingText != null) {
            String s1 = tor.findJsonRecording(jsonRecResults, "publicsent1", headingText);
            if (s1 == null) {
                s1 = tor.findJsonRecording(jsonRecResults, "publicsent1", headingText.toLowerCase());
            }
            attr = doc.createAttribute(GTX_HEADING_AT_XML);
            attr.setValue(u.formatTextforUpload(headingText,CURRENT_MODE));
            selectsElement.setAttributeNode(attr);
            if (s1 != null) {
                attr = doc.createAttribute(GTX_HEADING_AT_SOUND_UUID);
                attr.setValue(s1);
                selectsElement.setAttributeNode(attr);
            }
        }
        if (isSelect(st.curr.names[j])) {
            j++;
        }
        selloop:
        do {
            if (st.curr.names[j].trim().equals("")) {
                j++;
                continue;
            }
            if (st.curr.levels[j] <= headingtextbase) {
                htext = null;
            }
            if (isSelect(st.curr.names[j])) {
                if (!(st.curr.levels.length > j + 1 && st.curr.levels[j] >= st.curr.levels[j + 1])) {
                    j = doSelectsGame(topicTreeList, st, doc, gameElement, selectsElement, htext, st2, j, selectsj, extraGameBlockType, games);
                }
            } else if (st.curr.names[j].startsWith(GTX_HEADING)) {
                lastheading = st.curr.names[j];
                htext = u.formatTextforUpload(st.curr.names[j].substring(GTX_HEADING.length()),CURRENT_MODE);
                if (st.curr.names.length > j + 1 && isSelect(st.curr.names[j + 1])) {
                    headingtextbase = st.curr.levels[j];
                } else {
                    j = doSelectsGame(topicTreeList, st, doc, gameElement, selectsElement, htext, st2, j + 1, selectsj, extraGameBlockType, games);
                }
            } else {
                htext = null;
                j = doReferencesGame(topicTreeList, st, doc, gameElement, parentElement, selectsElement, st2, j, extraGameBlockType, String.valueOf(ti), games);
                if (j < st.curr.levels.length) {
                    if (isSelect(st.curr.names[j])) {
                        if (st.curr.levels[j] > st.curr.levels[selectsj]) {
                            return j - 1;
                        } else {
                            return j - 1;
                        }
                    } else if (st.curr.levels[j] <= st.curr.levels[selectsj]) {
                        return j - 1;
                    } else if (st.curr.names[j].startsWith(GTX_HEADING)) {
                        return j - 1;
                    }
                }
            }
            j++;
        } while (j < st.curr.levels.length && st.curr.levels[j] > newbase);
        return j - 1;
    }

    int doReferences(topicTree topicTreeList, saveTree1 st, Document doc, Element parentElement, saveTree1.saveTree2 st2, int j, int extraGameBlockType, String depth, String games[]) {
        int base = st.curr.levels[j - 1]; // the select level

        if (uploadStageUploadRest) {
            if (t.name.equals("revise letters  a i s t p m d g c")) {
                int ff;
                ff = 9;
            }
            if (t.name.equals("-oo- as in 'food'")) {
                int ff;
                ff = 9;
            }
        }

        String attributeText = null;
        int attributeLevel = -1;
        attributeCount = -1;
        attributeIndexCount = -1;
        String filters[] = null;

        Element refrencesElement = doc.createElement(GTX_REFERENCES_XML + depth);
        parentElement.appendChild(refrencesElement);
        String wordIDs[] = new String[]{};
        int lastAllOrNoneLevel = -1;
        doloop:
        do {
            String currtext = st.curr.names[j];
            if (currtext.startsWith(GTX_ALLORNONE)) {
                lastAllOrNoneLevel = st.curr.levels[j];
            }
            if (currtext.indexOf("*") >= 0) {
                do {
                    filters = u.addString(filters, st.curr.names[j]);
                    j++;
                    int h;
                    h = 0;
                } while (st.curr.names[j].indexOf("*") >= 0);
                continue doloop;
            }
            if (filters != null && (games != null && games.length > 0) && uploadStageUploadRest) {
                word www[] = t.getAllWordsBoth();    // standard and extended
                for (int i = 0; i < www.length; i++) {
                    if (www[i].value.startsWith("\\")) {
                        continue;
                    }     
                    String f = getTargetWithAnyPattern(www[i].v(), filters);
                    String isTarget = f == null ? "0" : "1";
                    wordIDs = u.addString(wordIDs, String.valueOf(addWordElement(st, doc, refrencesElement, j, attributeText, extraGameBlockType, games, www[i], isTarget)));
                }
            }
            if (st.curr.levels[j] <= attributeLevel) {
                attributeLevel = -1;
                attributeText = null;
                attributeIndexCount = -1;
                refrencesElement = doc.createElement(GTX_REFERENCES_XML + depth);
                parentElement.appendChild(refrencesElement);
                wordIDs = new String[]{};
            }
            if (attributeLevel < 0) {
                attributeText = (currtext.startsWith(GTX_ALLORNONE) || currtext.startsWith(GTX_PAIRS)) ? GTX_ALLORNONE_AT_XML : null;
                if (attributeText != null) {
                    attributeLevel = st.curr.levels[j];
                    attributeCount++;
                    j++;
                    int h;
                    h = 0;
                }
            }
            String type;
            boolean istopic = st.curr.names[j].startsWith(topicTree.ISTOPIC);
            if (istopic) {
                type = "1";
            } else {
                boolean ispath = st.curr.names[j].startsWith(topicTree.ISPATH);
                if (ispath) {
                    type = "2";
                } else {
                    type = "0";
                }
            }

            if (type.equals("1") || type.equals("2")) {
                boolean extendedtoo = false;
                String top;
                String strpt = "«publictopics»";

                top = st.curr.names[j].substring(strpt.length());

                boolean wantExtendedFromRefsGame = false;
                if (games != null && games.length > 0) {
                    for (int i = 0; i < games.length; i++) {
                        if (u.findString(GTX_WANT_EXTENDED_FROM_REFS_GAMES, games[i]) >= 0) {
                            wantExtendedFromRefsGame = true;
                            break;
                        }
                    }
                }
                if (st.curr.names[j].endsWith("+")) {
                    top = top.substring(0, top.length() - 1);
                    extendedtoo = true;
                } else if (wantExtendedFromRefsGame) {
                    extendedtoo = true;
                }
                // is a reference to a whole unit
                topic tts[] = new topic[]{};
                if (type.equals("2")) {
                    topic t[] = topicTree.getTopics(st.curr.names[j]);
                    for (int it = 0; it < t.length; ++it) {
                        tts = u.addTopic(tts, t[it]);
                    }
                } else {
                    tts = u.addTopic(tts, new topic(u.absoluteToRelative(sharkStartFrame.publicTopicLib[0]), top, null, null));
                }
                word www[] = new word[]{};
                for (int i = 0; i < tts.length; i++) {
                    www = u.addWords(www, tts[i].getAllWords(extendedtoo));
                }
                for (int i = 0; i < www.length; i++) {
                    if (www[i].value.startsWith("\\")) {
                        continue;
                    }
                    String f = getTargetWithAnyPattern(www[i].v(), filters);
                    String isTarget = f == null ? "0" : "1";
                    wordIDs = u.addString(wordIDs, String.valueOf(addWordElement(st, doc, refrencesElement, j, attributeText, extraGameBlockType, games, www[i], isTarget)));

                }
                www = u.stripdups(www);
            }
            if (type.equals("0")) {
                word w = new word(st.curr.names[j], "publictopics");
                String f = getTargetWithAnyPattern(w.v(), filters);
                String isTarget = f == null ? "0" : "1";
                wordIDs = u.addString(wordIDs, String.valueOf(addWordElement(st, doc, refrencesElement, j, attributeText, extraGameBlockType, games, new word(st.curr.names[j], "publictopics"), isTarget)));
            }
            j++;
            if (attributeLevel < 0 || st.curr.levels[j] <= attributeLevel) {
                if (wordIDs.length > 0) {
                    Attr attr = doc.createAttribute("WordIDs");
                    attr.setValue(u.combineString(wordIDs, ","));
                    refrencesElement.setAttributeNode(attr);
                }
                if (attributeText != null && attributeText.equals("AllOrNone")) {
                    Attr attr = doc.createAttribute("AllOrNones");
                    attr.setValue("1");
                    refrencesElement.setAttributeNode(attr);
                }
            }

        } while (j < st.curr.levels.length && st.curr.levels[j] > base);

        return j;
    }
    
    
    int doReferencesJson(topicTree topicTreeList, saveTree1 st, JSONObject doc, saveTree1.saveTree2 st2, int j, int extraGameBlockType, String depth, String games[]) {
        int base = st.curr.levels[j - 1]; // the select level

        if (uploadStageUploadRest) {
            if (t.name.equals("revise letters  a i s t p m d g c")) {
                int ff;
                ff = 9;
            }
            if (t.name.equals("-oo- as in 'food'")) {
                int ff;
                ff = 9;
            }
        }

        String attributeText = null;
        int attributeLevel = -1;
        attributeCount = -1;
        attributeIndexCount = -1;
        String filters[] = null;
        
        JSONArray references = new JSONArray();
        
        
        JSONObject reference = new JSONObject();
       
        /*
        Element refrencesElement = doc.createElement(GTX_REFERENCES_XML + depth);
        parentElement.appendChild(refrencesElement);
*/
        String wordIDs[] = new String[]{};
        doloop:
        do {
            String currtext = st.curr.names[j];
            if (currtext.indexOf("*") >= 0) {
                do {
                    filters = u.addString(filters, st.curr.names[j]);
                    j++;
                    int h;
                    h = 0;
                } while (st.curr.names[j].indexOf("*") >= 0);
                continue doloop;
            }
            if (filters != null && (games != null && games.length > 0) && uploadStageUploadRest) {
                word www[] = t.getAllWordsBoth();    // standard and extended
                for (int i = 0; i < www.length; i++) {
                    if (www[i].value.startsWith("\\")) {
                        continue;
                    }     
                    String f = getTargetWithAnyPattern(www[i].v(), filters);
                    String isTarget = f == null ? "0" : "1";
                    wordIDs = u.addString(wordIDs, String.valueOf(addWordElementJson(st, reference, j, attributeText, extraGameBlockType, games, www[i], isTarget)));
                }
            }
            if (st.curr.levels[j] <= attributeLevel) {
                attributeLevel = -1;
                attributeText = null;
                attributeIndexCount = -1;
                /*
                JSONArray references2 = new JSONArray();
                references.add(references2);
*/
                wordIDs = new String[]{};
            }
            if (attributeLevel < 0) {
                attributeText = (currtext.startsWith(GTX_ALLORNONE) || currtext.startsWith(GTX_PAIRS)) ? GTX_ALLORNONE_AT_XML : null;
                if (attributeText != null) {
                    attributeLevel = st.curr.levels[j];
                    attributeCount++;
                    j++;
                    int h;
                    h = 0;
                }
            }
            String type;
            boolean istopic = st.curr.names[j].startsWith(topicTree.ISTOPIC);
            if (istopic) {
                type = "1";
            } else {
                boolean ispath = st.curr.names[j].startsWith(topicTree.ISPATH);
                if (ispath) {
                    type = "2";
                } else {
                    type = "0";
                }
            }

            if (type.equals("1") || type.equals("2")) {
                boolean extendedtoo = false;
                String top;
                String strpt = "«publictopics»";

                top = st.curr.names[j].substring(strpt.length());

                boolean wantExtendedFromRefsGame = false;
                if (games != null && games.length > 0) {
                    for (int i = 0; i < games.length; i++) {
                        if (u.findString(GTX_WANT_EXTENDED_FROM_REFS_GAMES, games[i]) >= 0) {
                            wantExtendedFromRefsGame = true;
                            break;
                        }
                    }
                }
                if (st.curr.names[j].endsWith("+")) {
                    top = top.substring(0, top.length() - 1);
                    extendedtoo = true;
                } else if (wantExtendedFromRefsGame) {
                    extendedtoo = true;
                }
                // is a reference to a whole unit
                topic tts[] = new topic[]{};
                if (type.equals("2")) {
                    topic t[] = topicTree.getTopics(st.curr.names[j]);
                    for (int it = 0; it < t.length; ++it) {
                        tts = u.addTopic(tts, t[it]);
                    }
                } else {
                    tts = u.addTopic(tts, new topic(u.absoluteToRelative(sharkStartFrame.publicTopicLib[0]), top, null, null));
                }
                word www[] = new word[]{};
                for (int i = 0; i < tts.length; i++) {
                    www = u.addWords(www, tts[i].getAllWords(extendedtoo));
                }
                for (int i = 0; i < www.length; i++) {
                    if (www[i].value.startsWith("\\")) {
                        continue;
                    }
                    String f = getTargetWithAnyPattern(www[i].v(), filters);
                    String isTarget = f == null ? "0" : "1";
                    wordIDs = u.addString(wordIDs, String.valueOf(addWordElementJson(st, reference, j, attributeText, extraGameBlockType, games, www[i], isTarget)));

                }
                www = u.stripdups(www);
            }
            if (type.equals("0")) {
                word w = new word(st.curr.names[j], "publictopics");
                String f = getTargetWithAnyPattern(w.v(), filters);
                String isTarget = f == null ? "0" : "1";
                wordIDs = u.addString(wordIDs, String.valueOf(addWordElementJson(st, reference, j, attributeText, extraGameBlockType, games, new word(st.curr.names[j], "publictopics"), isTarget)));
            }
            j++;
            if (attributeLevel < 0 || st.curr.levels[j] <= attributeLevel) {
                if (wordIDs.length > 0) {
                    
                    reference.put("WordIDs", u.combineString(wordIDs, ","));
                   
                }
                if (attributeText != null && attributeText.equals("AllOrNone")) {
                    
                    reference.put("AllOrNones", "1");
                }
            }
            
            references.add(reference);

        } while (j < st.curr.levels.length && st.curr.levels[j] > base);
        doc.put("references", references);
        
        
        return j;
    }

    int doReferencesGame(topicTree topicTreeList, saveTree1 st, Document doc, Element gameElement, Element parentParentElement, Element parentElement, saveTree1.saveTree2 st2, int j, int extraGameBlockType, String depth, String games[]) {
        int base = st.curr.levels[j - 1]; // the select level
        String attributeText = null;
        int attributeLevel = -1;
        attributeCount = -1;
        attributeIndexCount = -1;
        String filters[] = null;
        String gType = gameElement.getAttribute(GTX_TB_NUM_AT_GAMEBLOCKTYPE);
        word words_yes[] = new word[]{};
        String wordPatterns[] = new String[]{};
        word words_no[] = new word[]{};

        Element refrencesElement = doc.createElement(GTX_REFERENCES_XML + depth);
        parentElement.appendChild(refrencesElement);
        gameReferenceNodeCount++;
        String wordIDs[] = new String[]{};

        // if no select in a helicopter game, add one of select 9
        // if select no exists for helicopter game and is over 9, change it to 9.
        if ((gType.equals(TOPICBLOCKGAMETYPE_HELICOPTERLISTEN) || gType.equals(TOPICBLOCKGAMETYPE_HELICOPTERSPELL))) {
            String sel = parentElement.getAttribute(GTX_SELECTNO_AT_XML);
            Element currEle = parentElement;
            if (sel == null || sel.trim().equals("")) {
                sel = parentParentElement.getAttribute(GTX_SELECTNO_AT_XML);
                currEle = parentParentElement;
            }
            if (sel == null || sel.trim().equals("")) {
                parentElement.setAttribute(GTX_SELECTNO_AT_XML, String.valueOf(HELICOPTERMAXSELECT));
            } else {
                try {
                    int k = Integer.parseInt(sel);
                    if (k > HELICOPTERMAXSELECT) {
                        currEle.setAttribute(GTX_SELECTNO_AT_XML, String.valueOf(HELICOPTERMAXSELECT));
                    }
                } catch (Exception ee) {
                    currEle.setAttribute(GTX_SELECTNO_AT_XML, String.valueOf(HELICOPTERMAXSELECT));
                }
            }
        }

        doloop:
        do {
            String currtext = st.curr.names[j];
            if (currtext.indexOf("*") >= 0) {
                do {
                    filters = u.addString(filters, st.curr.names[j]);
                    j++;
                } while (st.curr.names[j].indexOf("*") >= 0);
                if (j < st.curr.levels.length && st.curr.levels[j] < st.curr.levels[j - 1] && words_yes.length == 0 && words_no.length == 0) {
                    if (filters != null && (games != null && games.length > 0)) {
                        word www[] = t.getAllWordsBoth();    // standard and extended
                        www = u.stripdups(www);
                        for (int i = 0; i < www.length; i++) {
                            if (www[i].value.startsWith("\\")) {
                                continue;
                            }
                            String s;
                            if ((s = getTargetWithAnyPattern(www[i].v(), filters)) != null) {
                                words_yes = u.addWords(words_yes, www[i]);
                                wordPatterns = u.addString(wordPatterns, www[i].v().equals(s) ? null : s);
                            } else {
                                words_no = u.addWords(words_no, www[i]);
                            }
                        }
                    }
                }

                continue doloop;
            }
            if (filters != null && (games != null && games.length > 0)) {
                word www[] = t.getAllWordsBoth();    // standard and extended
                www = u.stripdups(www);
                for (int i = 0; i < www.length; i++) {
                    if (www[i].value.startsWith("\\")) {
                        continue;
                    }
                    String s;
                    if ((s = getTargetWithAnyPattern(www[i].v(), filters)) != null) {
                        words_yes = u.addWords(words_yes, www[i]);
                        wordPatterns = u.addString(wordPatterns, www[i].v().equals(s) ? null : s);
                    } else {
                        words_no = u.addWords(words_no, www[i]);
                    }
                }
            }
            if (st.curr.levels[j] <= attributeLevel) {
                attributeLevel = -1;
                attributeText = null;
                attributeIndexCount = -1;
                refrencesElement = doc.createElement(GTX_REFERENCES_XML + depth);
                parentElement.appendChild(refrencesElement);
                wordIDs = new String[]{};
            }
            if (attributeLevel < 0) {
                attributeText = (currtext.startsWith(GTX_ALLORNONE) || currtext.startsWith(GTX_PAIRS)) ? GTX_ALLORNONE_AT_XML : null;
                if (attributeText != null) {
                    attributeLevel = st.curr.levels[j];
                    attributeCount++;
                    j++;
                    int h;
                    h = 0;
                }
            }
            String type;
            boolean istopic = st.curr.names[j].startsWith(topicTree.ISTOPIC);
            if (istopic) {
                type = "1";
            } else {
                boolean ispath = st.curr.names[j].startsWith(topicTree.ISPATH);
                if (ispath) {
                    type = "2";
                } else {
                    type = "0";
                }
            }
            if (type.equals("1") || type.equals("2")) {
                boolean extendedtoo = false;
                String top;
                String strpt = "«publictopics»";
                top = st.curr.names[j].substring(strpt.length());
                boolean wantExtendedFromRefsGame = false;
                if (games != null && games.length > 0) {
                    for (int i = 0; i < games.length; i++) {
                        if (u.findString(GTX_WANT_EXTENDED_FROM_REFS_GAMES, games[i]) >= 0) {
                            wantExtendedFromRefsGame = true;
                            break;
                        }
                    }
                }
                if (st.curr.names[j].endsWith("+")) {
                    top = top.substring(0, top.length() - 1);
                    extendedtoo = true;
                } else if (wantExtendedFromRefsGame) {
                    extendedtoo = true;
                }
                // is a reference to a whole unit
                topic tts[] = new topic[]{};
                if (type.equals("2")) {
                    topic t[] = topicTree.getTopics(st.curr.names[j]);
                    for (int it = 0; it < t.length; ++it) {
                        tts = u.addTopic(tts, t[it]);
                    }
                } else {
                    topic t1 = new topic(u.absoluteToRelative(sharkStartFrame.publicTopicLib[0]), top, null, null);
                    tts = u.addTopic(tts, t1);
                }
                word www[] = new word[]{};
                for (int i = 0; i < tts.length; i++) {
                    www = u.addWords(www, tts[i].getAllWords(extendedtoo));
                }
                www = u.stripdups(www);
                for (int i = 0; i < www.length; i++) {
                    if (www[i].value.startsWith("\\")) {
                        continue;
                    }  
                    String s;
                    if ((s = getTargetWithAnyPattern(www[i].v(), filters)) != null) {
                        words_yes = u.addWords(words_yes, www[i]);
                        wordPatterns = u.addString(wordPatterns, www[i].v().equals(s) ? null : s);
                    } else {
                        words_no = u.addWords(words_no, www[i]);
                    }
                }
            }
            if (type.equals("0")) {
                word w = new word(st.curr.names[j], "publictopics");
                String s;
                if ((s = getTargetWithAnyPattern(w.v(), filters)) != null) {
                    words_yes = u.addWords(words_yes, w);
                    wordPatterns = u.addString(wordPatterns, w.v().equals(s) ? null : s);
                } else {
                    words_no = u.addWords(words_no, w);
                }
            }
            j++;
        } while (j < st.curr.levels.length && st.curr.levels[j] > base);

        if (helicopterNoColumnFilters != null && lastheading.startsWith("Heading:No")) {
            words_yes = getWordsWithoutPattern(words_yes, helicopterNoColumnFilters);
        }

        for (int i = 0; i < words_yes.length; i++) {
            int g = doWordNew(words_yes[i], null, wordPatterns[i], t.mySQL_Topic_ID, null, new int[]{WORD_TYPE_PATTERNGAME});
            if (u.findString(wordIDs, String.valueOf(g)) < 0) {
                wordIDs = u.addString(wordIDs, String.valueOf(g));
            }
        }
        if (wordIDs.length > 0 && (gType.equals(TOPICBLOCKGAMETYPE_PATTERNSNAKES) || gType.equals(TOPICBLOCKGAMETYPE_PATTERN))) {
            wordIDs = makeUpTheNumbers(wordIDs, gType.equals(TOPICBLOCKGAMETYPE_PATTERNSNAKES) ? snakesandladders.ALLOCGOOD : pattern.ALLOCGOOD);
        }
        if (attributeLevel < 0 || st.curr.levels[j] <= attributeLevel) {
            if (wordIDs.length > 0) {
                Attr attr = doc.createAttribute("WordIDs");
                attr.setValue(u.combineString(wordIDs, ","));
                refrencesElement.setAttributeNode(attr);
                if (gType != null) {
                    if (gType.equals(TOPICBLOCKGAMETYPE_PATTERNSNAKES) || gType.equals(TOPICBLOCKGAMETYPE_PATTERN)) {
                        attr = doc.createAttribute(GTX_SELECTNO_AT_XML);
                        boolean isSnakes = gType.equals(TOPICBLOCKGAMETYPE_PATTERNSNAKES);
                        if (gameReferenceNodeCount > 1) {
                            attr.setValue(String.valueOf(Math.min(isSnakes ? selectCountDistractorNoSnakesAndLadders : selectCountDistractorNoPattern, wordIDs.length)));
                            parentElement.setAttribute(GTX_HEADING_AT_XML, STR_NO);
                            parentElement.removeAttribute(GTX_HEADING_AT_SOUND_UUID);
                        } else {
                            attr.setValue(String.valueOf(Math.min(isSnakes ? selectCountTargetNoSnakesAndLadders : selectCountTargetNoPattern, wordIDs.length)));
                        }
                        refrencesElement.setAttributeNode(attr);

                    }
                }
            }
            if (attributeText != null && attributeText.equals("AllOrNone")) {
                Attr attr = doc.createAttribute("AllOrNones");
                attr.setValue("1");
                refrencesElement.setAttributeNode(attr);
            }
        }

        if (gType != null) {
            String nonWordIds[] = new String[]{};
            for (int i = 0; words_no != null && i < words_no.length; i++) {
                int g = doWordNew(words_no[i], t.mySQL_Topic_ID, null, new int[]{WORD_TYPE_PATTERNGAME});
                if (u.findString(nonWordIds, String.valueOf(g)) < 0) {
                    nonWordIds = u.addString(nonWordIds, String.valueOf(g));
                }
            }

            if (nonWordIds.length > 0 && (gType.equals(TOPICBLOCKGAMETYPE_PATTERNSNAKES) || gType.equals(TOPICBLOCKGAMETYPE_PATTERN))) {
                nonWordIds = makeUpTheNumbers(nonWordIds, gType.equals(TOPICBLOCKGAMETYPE_PATTERNSNAKES) ? snakesandladders.ALLOCBAD : pattern.ALLOCBAD);
            }

            if (nonWordIds.length > 0 && (gType.equals(TOPICBLOCKGAMETYPE_PATTERNSNAKES) || gType.equals(TOPICBLOCKGAMETYPE_PATTERN))) {
                // creating the No column that doesn't exist in publictopics
                Attr attr = doc.createAttribute(GTX_HEADING_AT_XML);
                attr.setValue(STR_NO);
                Element parentElement2 = doc.createElement(parentElement.getNodeName());
                parentElement2.setAttributeNode(attr);
                parentParentElement.appendChild(parentElement2);
                Element referencesElement = doc.createElement(GTX_REFERENCES_XML + depth);
                parentElement2.appendChild(referencesElement);
                boolean isSnakes = gType.equals(TOPICBLOCKGAMETYPE_PATTERNSNAKES);
                attr = doc.createAttribute(GTX_SELECTNO_AT_XML);
                attr.setValue(String.valueOf(Math.min(isSnakes ? selectCountDistractorNoSnakesAndLadders : selectCountDistractorNoPattern, nonWordIds.length)));
                parentElement2.setAttributeNode(attr);
                attr = doc.createAttribute("WordIDs");
                attr.setValue(u.combineString(nonWordIds, ","));
                referencesElement.setAttributeNode(attr);

                //if group select, need to create a containing select
                String s1 = parentParentElement.getAttribute(GTX_SELECTGROUPNO_AT_XML + String.valueOf(Integer.parseInt(depth) - 1));
                if (s1 == null || s1.trim().equals("")) // not sure if some have numbers and some don't??
                {
                    s1 = parentParentElement.getAttribute(GTX_SELECTGROUPNO_AT_XML);
                }
                boolean isGroupSelect = s1 != null && !s1.trim().equals("");
                if (isGroupSelect) {
                    parentParentElement.removeChild(parentElement);
                    parentParentElement.removeChild(parentElement2);
                    Element newGroupEle = doc.createElement(GTX_SELECT_XML + depth);
                    parentParentElement.appendChild(newGroupEle);
                    newGroupEle.appendChild(parentElement);
                    newGroupEle.appendChild(parentElement2);
                    doc.renameNode(parentElement.getFirstChild(), "", GTX_REFERENCES_XML + String.valueOf(Integer.parseInt(depth) + 1));
                    doc.renameNode(parentElement, "", GTX_SELECT_XML + String.valueOf(Integer.parseInt(depth) + 1));
                    doc.renameNode(parentElement2, "", GTX_SELECT_XML + String.valueOf(Integer.parseInt(depth) + 1));
                    doc.renameNode(referencesElement, "", GTX_REFERENCES_XML + String.valueOf(Integer.parseInt(depth) + 1));
                    gameReferenceNodeCount = 0;
                }
            }
        }
        return j;
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

    int addWordElement(saveTree1 st, Document doc, Element parentElement, int j, String attributeText, int extraGameBlockType, String games[], word w, String isTarget) {
        int wordid = doWordNew(w, t.mySQL_Topic_ID, null, new int[]{WORD_TYPE_EXTENDED});
        if (wordid < 0) {
            int g;
            g = 0;
        }
        Element refrenceElement = doc.createElement(GTX_REFERENCE_XML);
        Attr attr = doc.createAttribute(GTX_REF_TYPE_AT_XML);
        attr.setValue("0");
        refrenceElement.setAttributeNode(attr);
        if (attributeText != null) {
            attributeIndexCount++;
            attr = doc.createAttribute(attributeText);
            attr.setValue(String.valueOf(attributeCount));
            refrenceElement.setAttributeNode(attr);
            attr = doc.createAttribute(GTX_ALLORNONE_INDEX_AT_XML);
            attr.setValue(String.valueOf(attributeIndexCount));
            refrenceElement.setAttributeNode(attr);
        }
        if (isTarget != null) {
            attr = doc.createAttribute(GTX_IS_WORD_TARGET_AT_XML);
            attr.setValue(isTarget);
            refrenceElement.setAttributeNode(attr);
        }
        attr = doc.createAttribute("WordID");
        attr.setValue(String.valueOf(wordid));
        refrenceElement.setAttributeNode(attr);
        int b = doReferencesWord(t, w.value, st, j, extraGameBlockType, doc, refrenceElement);
        if (b >= 0) {
            if (!isDuplicate(parentElement, refrenceElement)) {
                parentElement.appendChild(refrenceElement);
                return wordid;
            } else {
                int gg;
                gg = 9;
            }
        } else {
            int gg;
            gg = 0;
        }
        return -1;
    }

    
     int addWordElementJson(saveTree1 st, JSONObject doc,int j, String attributeText, int extraGameBlockType, String games[], word w, String isTarget) {
        int wordid = doWordNew(w, t.mySQL_Topic_ID, null, new int[]{WORD_TYPE_EXTENDED});
        if (wordid < 0) {
            int g;
            g = 0;
        }
        
        doc.put(GTX_REF_TYPE_AT_XML, "0");

        if (attributeText != null) {
            attributeIndexCount++;
            doc.put(attributeText, String.valueOf(attributeCount));
            doc.put(GTX_ALLORNONE_INDEX_AT_XML, String.valueOf(attributeIndexCount));
        }
        if (isTarget != null) {
            doc.put(GTX_IS_WORD_TARGET_AT_XML, isTarget);
        }
        
        doc.put("Word", w.value);
        
     //   doReferencesWordJson(t, w.value, st, j, extraGameBlockType, doc, refrenceElement);

        return -1;
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

    int doReferencesWord(topic tt, String wordname, saveTree1 st, int j, int extraGameBlockType, Document doc, Element refrenceElement) {
        String theword = wordname;
        String root = null;
        String suffix = null;
        if (extraGameBlockType == GAME_BLOCK_TYPE_SUFFIX) {
            String full = wordname;
            int k;
            if ((k = full.indexOf("+")) >= 0) {
                root = full.substring(0, k);
                suffix = full.substring(k + 1);
                theword = getRootSuffix(full, root, suffix);
            }
        }
        String bracketedWord = null;
        if (wordname.indexOf('[') >= 0 && wordname.indexOf(']') >= 0) {
            bracketedWord = wordname;
        }
        return doWord(new word(theword, "publictopics"), tt, doc, refrenceElement, false, false, bracketedWord, root, suffix);
    }
    
     int doReferencesWordJson(topic tt, String wordname, saveTree1 st, int j, int extraGameBlockType, Document doc, Element refrenceElement) {
        String theword = wordname;
        String root = null;
        String suffix = null;
        if (extraGameBlockType == GAME_BLOCK_TYPE_SUFFIX) {
            String full = wordname;
            int k;
            if ((k = full.indexOf("+")) >= 0) {
                root = full.substring(0, k);
                suffix = full.substring(k + 1);
                theword = getRootSuffix(full, root, suffix);
            }
        }
        String bracketedWord = null;
        if (wordname.indexOf('[') >= 0 && wordname.indexOf(']') >= 0) {
            bracketedWord = wordname;
        }
        return doWord(new word(theword, "publictopics"), tt, doc, refrenceElement, false, false, bracketedWord, root, suffix);
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

    static public String getTopicXML2(saveTree1 st, String key, boolean returnBool) {
        if (key.equals(GTX_TOPICNAME)) {
            return st.curr.names[0];
        }
        for (int j = 0; j < st.curr.names.length; ++j) { //For 2.1
            if (st.curr.names[j].startsWith(GTX_TEACHINGNOTE) && key.equals((GTX_TEACHINGNOTE))) {
                int k = j + 1;
                String tnote = st.curr.names[j].substring(key.length());
                while (k < st.curr.names.length && st.curr.names[k].startsWith(GTX_TEACHINGNOTE)) {
                    tnote += " " + st.curr.names[k].substring(key.length());
                    k++;
                }
                return u.setTextHtmlFormattedForUpload(tnote, CURRENT_MODE);
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
        String apPriority1 = getTopicXML2(tree.st, topic.types[topic.APPRIORITY1], true);
        String apPriority2 = getTopicXML2(tree.st, topic.types[topic.APPRIORITY2], true);
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
            int postSelectIndex = (int)select.get("desktopSelectIndex") + 1;
            int startLevel = tree.st.curr.levels[postSelectIndex];

            for (int i = postSelectIndex; i < tree.st.curr.levels.length; ++i) {     
                if(tree.st.curr.levels[i]<startLevel){
                    break;
                }
                JSONObject reference = getSentenceReference(tree, i, startIndex, uploadType);
                references.add(reference);
            }  
        }
        return references;
    }   

    JSONObject getSentenceReference(treeDetails tree, int i, int selectIndex, String uploadType) {
        JSONObject subSelect = new JSONObject();
        String sentType = getSentenceType(tree.st.curr.names[i]);
        String sentenceDistractors[] = getSentenceDistractorWords(tree.st.curr.names[i], sentType);
        if(sentenceDistractors != null){
            subSelect.put("wrongWords", getSentenceWords(sentenceDistractors, null));
        }
        /*
        
        getSentenceTargetImages
        
        */
        sentence sent = (new sentence(tree.st.curr.names[i], null));
        subSelect.put("desktopSelectIndex", selectIndex);
        subSelect.put("sentence", u.formatTextforUpload(sent.stripclozereplacewildcard(),CURRENT_MODE));
        subSelect.put("rightWords", getSentenceWords(getSentenceTargetWords(tree.st.curr.names[i], sentType, true), getSentenceTargetImages(tree.st.curr.names[i])));
        String sentText = removeSentenceImageSuffix(tree.st.curr.names[i].toLowerCase().replace("|", " "));
        subSelect.put("soundPeep", tor.findJsonRecording(jsonRecResults, getSoundDatabaseForSentenceGame(tree, uploadType, false), sentText));
        if(uploadType == UPLOAD_TYPE_SENTENCES_SIMPLE){   
            String simpleSentence = sent.stripcloze();
            subSelect.put("plain_sentence", u.formatTextforUpload(simpleSentence,CURRENT_MODE));
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
            return null;
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
            if (key.equals(GTX_TOPICNAME)) {
                return tree.st.curr.names[0];
            }
            for (int j = 0; j < tree.st.curr.names.length; ++j) { //For 2.1
                if (tree.st.curr.names[j].startsWith(GTX_TEACHINGNOTE) && key.equals((GTX_TEACHINGNOTE))) {
                    int k = j + 1;
                    String tnote = tree.st.curr.names[j].substring(key.length());
                    while (k < tree.st.curr.names.length && tree.st.curr.names[k].startsWith(GTX_TEACHINGNOTE)) {
                        tnote += " " + tree.st.curr.names[k].substring(key.length());
                        k++;
                    }
                    return u.setTextHtmlFormattedForUpload(tnote, CURRENT_MODE);
                } else if (tree.st.curr.levels[j] == 1 && tree.st.curr.names[j].startsWith(key)) {
                    if (tree.st.curr.names[j].trim().length() <= key.length()) {
                        return "1";
                    }
                    return tree.st.curr.names[j].substring(key.length());
                }
            }
            return returnBool ? "0" : null;
        }
        
        void writeJson(String topicName, String json) {

            File f = new File(
                RESTJSONFOLDER + shark.sep
                + ENV_NAMES[currentEnvironment] + shark.sep
                + currentCourse + shark.sep
                + currCourseVersion + shark.sep
                + toSafeFilename(topicName) + ".json"
            );

            // Ensure parent directory exists
            f.getParentFile().mkdirs();

            
            if (!f.exists()) {
                PrintWriter pw = null;
                try {
                    pw = new PrintWriter(new FileWriter(f.getAbsolutePath()));
                    pw.println(json);
                    pw.flush();
                } catch (Exception e) {
                }
            }
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
