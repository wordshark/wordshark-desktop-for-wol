

package shark;
import java.io.*;
import java.util.*;

public class topicPlayed implements Serializable {
  static final long serialVersionUID = -1222649876408421531L;
  public String topic;
  public String game;
  public Date date,dateoff;
  public String errorList[];
  public String peepList[];
  public short time;    // seconds
  public byte speed;
  public boolean teacher,incomplete;
  public boolean shuffled;
  public short score;
  public String testresults[];
  String workforteacher;                          // rb  23/10/06
  TimeZone timezone;
  String sharedwith;                              // v5 rb 9/3/08
}
