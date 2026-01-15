package shark;
import java.io.*;
import java.awt.*;

public class savestudent  implements Serializable{
        static final long serialVersionUID = -447749458179642011L;
        String name,dbname;
        String spritename;
        boolean administrator;
        String password;
        String passwordhint;
        topicPlayed[] studentrecord;
        String[] students;
        String currTopic,currGame,currTab;
        String options[];
        boolean wantpics,wantpicsgames,notwantrealpics,wantsigns,wantfingers,wantfingersall,picbg;
//startPR2008-06-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        boolean wantsignvids;
//endPRimagesandvids^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        boolean gameicons;
        byte beepvol;
        boolean nospiders;
        byte rewardfreq;   //3.07
        String[] besttime; // 3.07
        String[] excludegames;  // 3.07
        Color printfg;  // 3.07
        String version;  // 4
        char which;  // 4
        int sametopic; //5
        String picprefs[];
        long lastplayed;
}
