// compress/decompress for speech

package shark;

import javax.sound.sampled.*;
import java.awt.event.*;
import javax.swing.*;
//-------------------------------------------------------------------
public class spokenWord implements Runnable {

   class savespeech {
      int expanded_length;
      byte[] header;
      byte[] body;
   }
static SourceDataLine speaker=null;

static final short FIXEDLEN = 200;
static final short MAXSTORE = 20;
public static final int RATE = 22050, MACRATE=44100;
static AudioFormat format = new AudioFormat(
                              (float)spokenWord.RATE,
                              8,1,false,false);
static AudioFormat format2 = new AudioFormat(
                                                   (float)spokenWord.RATE,
                                                   16,1,true,false);
//static AudioFormat macformat = new AudioFormat(
//                           (float)spokenWord.MACRATE,
//                           8,1,false,false);
static AudioFormat macformat2 = new AudioFormat(
                                                (float)spokenWord.MACRATE,
                                                16,1,true,false);
static TargetDataLine mike;
public static boolean recording;
public boolean running;   // subtask is doing recording or sorting out after recording
static recordWords currrecord;

static recordtool currrectool;
byte in[],out[];
public byte data[];
public static int savefrom,saveto;
/**
* When the last word was spoken.
*/
public static long endsay = System.currentTimeMillis();
public static long endsay2 = System.currentTimeMillis();   // this can be used by games after say()
public static long opentime;
int offset;
int pos;
int startpos,endpos;
static int boost=100; // used by recordWords - zero for autoboost
public static boolean autoboost = true;
public String database;
String name;      // the word itself (index for database)
public boolean decompressed,homophone;                 // rb 27/5/08
static DataLine.Info info;
Thread currthread;
public static String extrainf;
public int gotsofar;
public boolean skipmess;
public int sig = 4;
public int phonicsend[];    // positions of phonics after getPhonicsWord
public boolean notrim;
static boolean notrim2;
//------------------------------------------------------
public static String sayfull[] = u.splitString(u.gettext("phonics_","sayfull"),',');
static spokenWord store[] = new spokenWord[MAXSTORE];
static int storeTot;
//startPR2008-01-15^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
static boolean needMacRate = false;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
static byte hdr[] = new byte[] {'R','I','F','F',0,0,0,0,
                                       'W','A','V','E',
                                       'f','m','t',' ',16,0,0,0,
                                       1,0,1,0,
                                       (byte)(RATE & 0x00ff),
                                       (byte)(RATE>>>8),0,0,
                                       (byte)(RATE & 0x00ff),
                                       (byte)(RATE>>>8),0,0,
                                       1,0,8,0,
                                       'd','a','t','a',0,0,0,0};
static boolean speakerstarted = false;
static byte[] saydata=  null;
static int saystartpos;
static int sayendpos;
boolean extraadded = false;


  //---------------------------------------------------------
public spokenWord(String name1,  String database1,boolean homophone1) {
   name = name1;
   database = database1;
   if(homophone1) {
      if(db.query(database,name1+"=",db.WAV) >= 0) homophone = true;
   }
}
//-------------------------------------------------------------
static boolean startspeaker() {
  try {
      if(info==null) info = new DataLine.Info(SourceDataLine.class, shark.macOS?format2:format);
      speaker = (SourceDataLine)AudioSystem.getLine(info);
      speaker.open(shark.macOS?format2:format,RATE*12);
   }
   catch(LineUnavailableException e) {
      JOptionPane.showMessageDialog(sharkStartFrame.mainFrame,u.convertToHtml(u.gettext("sound_", "nospeaker")));
      speaker = null;
      return false;
   }
   catch(IllegalArgumentException e) {
      JOptionPane.showMessageDialog(sharkStartFrame.mainFrame,u.convertToHtml(u.gettext("sound_", "nospeaker")));
      speaker = null;
      return false;
   }
   if(speaker == null) {
      JOptionPane.showMessageDialog(sharkStartFrame.mainFrame,u.convertToHtml(u.gettext("sound_", "nospeaker")));
      speaker = null;
      return false;
   }
   speaker.start();
   opentime = System.currentTimeMillis();
   return true;
}
 /**
  * Used at end of a game to stop any speach
  */
 public static synchronized void flushspeaker(boolean doendsayreset) {
   if(speaker != null) {
     speaker.flush();
     if(doendsayreset)
        endsay = System.currentTimeMillis();
  }
}

//--------------------------------------------------------------
static synchronized boolean closespeaker() {
  if(speaker != null
      && (sharkStartFrame.mainFrame.refreshsound!=null && sharkStartFrame.mainFrame.refreshsound.getState())
      && sharkStartFrame.mainFrame.recording == null
      && System.currentTimeMillis() > endsay + 1000          /* idle for 1 sec*/
      && System.currentTimeMillis() > opentime + 1800000) {  /* on for 30 min*/
     stopspeaker();
     endsay = System.currentTimeMillis();
     return true;
  }
  return false;
}
//------------------------------------------------------------

static byte[] convert8BitTo16Bit(byte[] data) {
        byte[] ret = new byte[data.length*2];
        short[] bitOriginal= new short[data.length];
        float runningTotal=0.0f;
        float averageVal;
        for(int i = 0; i < data.length; i++){
            int firstByte;
            firstByte = (0x000000FF & ((int)(data[i])));
            short d = (short)firstByte;
            bitOriginal[i]=d;
            runningTotal=runningTotal+(float)bitOriginal[i];
        }
        short soundScaler=127;
        averageVal=runningTotal/(float)(data.length);
        for(int i = 0; i < data.length; i++){
            bitOriginal[i]=(short)(((float)bitOriginal[i]-averageVal)*soundScaler);
            ret[(i*2)] = (byte)(bitOriginal[i]& 0xff);
            ret[(i*2)+1] =(byte) ( (bitOriginal[i] >> 8) & 0xff); 
        }
        return ret;
    } 

public static synchronized void say(byte[] data) {
    if(shark.macOS){
        data = convert8BitTo16Bit(data);
    }
  saydata = data;
  if(speaker==null){
      try {
          if(info==null) info = new DataLine.Info(SourceDataLine.class, shark.macOS?format2:format);
          speaker = (SourceDataLine)AudioSystem.getLine(info);
          // there was a problem that after doing a fairly long recording, the
          // immediate playback wouldn't start at the beginning. Having the code
          // inside here seems to fix it.
          speaker.addLineListener(
            new LineListener(){
              public void update(LineEvent e){
                if(e.getType().equals(LineEvent.Type.OPEN)){
                   if(speaker != null) {
                     opentime = System.currentTimeMillis();
                     endsay = opentime + (long)saydata.length*1000/RATE;
                     flushspeaker(false);
                     speaker.start();
                     speaker.write(saydata,0,saydata.length);
                   }
                   else {
                     JOptionPane.showMessageDialog(sharkStartFrame.mainFrame,u.convertToHtml(u.gettext("sound_", "nospeaker")));
                     speaker = null;
                   }
                }
              }
            }
          );
          speaker.open(shark.macOS?format2:format,RATE*12);
       }
       catch(LineUnavailableException e) {
          JOptionPane.showMessageDialog(sharkStartFrame.mainFrame,u.convertToHtml(e.getMessage()+"||"+u.gettext("sound_", "nospeaker")));
          speaker = null;
       }
  }
  else{
      endsay = System.currentTimeMillis() + (long)data.length*1000/RATE;
      flushspeaker(false);
      speaker.write(data,0,data.length);
  }
}
 //------------------------------------------------------------

public static synchronized void say(byte[] data,int startpos,int endpos) {
  saydata = data;
  saystartpos = startpos;
  sayendpos = endpos;
  if(speaker==null){
      try {
          if(info==null) info = new DataLine.Info(SourceDataLine.class, shark.macOS?format2:format);
          speaker = (SourceDataLine)AudioSystem.getLine(info);
          // there was a problem that after doing a fairly long recording, the
          // immediate playback wouldn't start at the beginning. Having the code
          // inside here seems to fix it.
          speaker.addLineListener(
            new LineListener(){
              public void update(LineEvent e){
                if(e.getType().equals(LineEvent.Type.OPEN)){
                   if(speaker != null) {
                     opentime = System.currentTimeMillis();
                     endsay = opentime + (sayendpos-saystartpos)*1000/RATE;
                     flushspeaker(false);
                     speaker.start();
                     speaker.write(saydata,saystartpos,sayendpos-saystartpos);
                   }
                   else {
                     JOptionPane.showMessageDialog(sharkStartFrame.mainFrame, u.convertToHtml(u.gettext("sound_", "nospeaker")));
                     speaker = null;
                   }
                }
              }
            }
          );
          speaker.open(shark.macOS?format2:format,RATE*12);
       }
       catch(LineUnavailableException e) {
          JOptionPane.showMessageDialog(sharkStartFrame.mainFrame,u.convertToHtml(e.getMessage()+"||"+u.gettext("sound_", "nospeaker")));
          speaker = null;
       }
  }
  else{
      endsay = System.currentTimeMillis() + (endpos-startpos)*1000/RATE;
      flushspeaker(false);
      speaker.write(data,startpos,endpos-startpos);
  }
}


  //---------------------------------------------------------
void addbits(short val,int len) {
  for(short inpos = (short)(1<<(len-1));inpos!=0;inpos>>>=1){
   if((val & inpos) != 0) out[offset] |= pos;
   if((pos>>>=1) == 0) {
      ++offset;
      pos = 0x0080;
   }
 }
}
//--------------------------------------------------------------------

public byte[] save2()  {
     int inlen;
     byte realout[];
     inlen = (endpos != 0)?(endpos-startpos):(data.length-startpos);
     short val,oldval = 0x007f, diff;
     out = new byte[5 + inlen*3/2];
     offset = 0;
     int in = 0;
     short maxct,i,j,bits,efflen=0;
     short maxi;
     short diffs[] = new short[4];

     pos = 0x0080;

     addbits((short)(inlen>>16),8);  //put length
     addbits((short)(inlen>>8),8);
     addbits((short)inlen,8);
     addbits((short)0,8);  //type=0 for new-style list
     while(in<inlen) {
        maxct = (short)Math.min(4,inlen-in);
        for(i=0,maxi = -1;i<maxct;++i,++in) {  //  get max
           val = (short)(((short)data[in+startpos]) & 0x00ff);
           diff  = (short)(val - oldval);
           oldval=val;
           diffs[i] = diff;
           if(diff != 0)
              maxi = (short)Math.max(maxi,(short)((diff>=0)?(diff):(-diff-1)));
        }
        if(maxi == -1)   bits = 0;
        else {
           bits = 1;
           while(maxi>0) {++bits; maxi >>= 1;}
        }
        j = (short)(bits - efflen);
        efflen = bits;
        if(j==0) addbits((short)0,(short)2);
        else if(j == 1)  addbits((short)1,(short)2);
        else if(j == -1) addbits((short)2,(short)2);
        else if(j < -1) {
           addbits((short)7,(short)3);
           for(j+=2;j!=0;++j) addbits((short)1,(short)1);
           addbits((short)0,(short)1);
        }
        else  {
           addbits((short)6,(short)3);
           for(j-=2;j!=0;--j) addbits((short)1,(short)1);
           addbits((short)0,(short)1);
        }
        if(efflen!=0) for(i=0;i<maxct;++i) addbits(diffs[i],efflen);
     }
     if(pos != 0x0080) ++offset;
     realout = new byte[offset];
     System.arraycopy(out,0,realout,0,(int)offset);
     out = null;
     return realout;
  }


             // compress and save
public boolean save()  {
     int inlen;
     byte realout[];
     inlen = (endpos != 0)?(endpos-startpos):(data.length-startpos);
     short val,oldval = 0x007f, diff;
     out = new byte[5 + inlen*3/2];
     offset = 0;
     int in = 0;
     short maxct,i,j,bits,efflen=0;
     short maxi;
     short diffs[] = new short[4];

     pos = 0x0080;

     addbits((short)(inlen>>16),8);  //put length
     addbits((short)(inlen>>8),8);
     addbits((short)inlen,8);
     addbits((short)0,8);  //type=0 for new-style list
     while(in<inlen) {
        maxct = (short)Math.min(4,inlen-in);
        for(i=0,maxi = -1;i<maxct;++i,++in) {  //  get max
           val = (short)(((short)data[in+startpos]) & 0x00ff);
           diff  = (short)(val - oldval);
           oldval=val;
           diffs[i] = diff;
           if(diff != 0)
              maxi = (short)Math.max(maxi,(short)((diff>=0)?(diff):(-diff-1)));
        }
        if(maxi == -1)   bits = 0;
        else {
           bits = 1;
           while(maxi>0) {++bits; maxi >>= 1;}
        }
        j = (short)(bits - efflen);
        efflen = bits;
        if(j==0) addbits((short)0,(short)2);
        else if(j == 1)  addbits((short)1,(short)2);
        else if(j == -1) addbits((short)2,(short)2);
        else if(j < -1) {
           addbits((short)7,(short)3);
           for(j+=2;j!=0;++j) addbits((short)1,(short)1);
           addbits((short)0,(short)1);
        }
        else  {
           addbits((short)6,(short)3);
           for(j-=2;j!=0;--j) addbits((short)1,(short)1);
           addbits((short)0,(short)1);
        }
        if(efflen!=0) for(i=0;i<maxct;++i) addbits(diffs[i],efflen);
     }
     if(pos != 0x0080) ++offset;
     realout = new byte[offset];
     System.arraycopy(out,0,realout,0,(int)offset);
     out = null;
     db.updatewav(database,name,realout);
     return true;
  }
  //---------------------------------------------------------------------
  short getbits(short num) {
   short ret = 0;
   for(short outpos = (short)(1 << (num -1)); outpos!=0;
              outpos>>>=1) {
      if((in[offset] & pos) != 0)  ret |= outpos;
      if((pos>>>=1) == 0) {
         ++offset;
         pos = 0x0080;
      }
   }
   return ret;
   }
  //---------------------------------------------------------------------
  short getbitsSign(short num) {
   short ret = ((in[offset] & pos)==0)?0:(short)((-1)<<num);
   for(short outpos = (short)(1 << (num -1)); outpos!=0;
              outpos>>>=1) {
      if((in[offset] & pos) != 0)  ret |= outpos;
      if((pos>>>=1) == 0) {
         ++offset;
         pos = 0x0080;
      }
   }
   return ret;
   }
    //---------------------------------------------------------------------
   public void decomp() {
     data = decomp1();
   }
   public void decomp(byte input[]) {
     data = decompmain(input);
   }
   //---------------------------------------------------------------------
  boolean decomphomo() {
       data = decomp1();
       String savename = name;
       name += "=";
       byte[] data2 = decomp1();
       if(data2.length > 0) {
           byte[] mark = noise.marker();
           byte newdata[] = new byte[data.length+data2.length+mark.length];
           System.arraycopy(data,0,newdata,0,data.length);
           System.arraycopy(mark,0,newdata,data.length,mark.length);
           System.arraycopy(data2,0,newdata,data.length+mark.length,data2.length);
           data = newdata;
       }
       name=savename;
       return true;
 }
 //---------------------------------------------------------------------
boolean addhomo(String tname) {
     String savename = name;
     name = tname+"=";
     byte[] data2 = decomp1();
     if(data2.length > 0) {
         byte[] mark = noise.marker();
         byte newdata[] = new byte[data.length+data2.length+mark.length];
         System.arraycopy(data,0,newdata,0,data.length);
         System.arraycopy(mark,0,newdata,data.length,mark.length);
         System.arraycopy(data2,0,newdata,data.length+mark.length,data2.length);
         data = newdata;
     }
     name=savename;
      return true;
}

boolean addextra(String tname, boolean privatelists) {
    extraadded = true;
     String savename = name;
//     byte[] data2 = decompextra1(tname);
     byte[] data2 = decompextra1(tname, database, privatelists);
     name = tname+"=";
     if(data2.length > 0 && data!=null && data.length>0) {
         byte[] mark = noise.marker();
         byte newdata[] = new byte[data.length+data2.length+mark.length];

//         System.arraycopy(data2,0,newdata,data.length+mark.length,data2.length);
//         System.arraycopy(mark,0,newdata,data.length,mark.length);
//         System.arraycopy(data,0,newdata,0,data.length);
         System.arraycopy(data2,0,newdata,0,data2.length);
         System.arraycopy(mark,0,newdata,data2.length,mark.length);
         System.arraycopy(data,0,newdata,data2.length+mark.length,data.length);


         data = newdata;
     }
     name=savename;
      return true;
}

    //---------------------------------------------------------------
    //  decomp, accumulating
  boolean decomp2() {
        byte outs[] = decomp1();
        if(outs.length == 0) return false;
        if(data != null) {
           byte[] ret = new byte[data.length + outs.length];
           System.arraycopy(data,0,ret,0,data.length);
           System.arraycopy(outs,0,ret,data.length,outs.length);
           data = ret;
        }
        else data = outs;
//        data = topandtail(outs,outs.length,data,2);
        return true;
  }
     //---------------------------------------------------------------
  public static byte[] topandtail(byte[] buffer, int len,  int sig, boolean tempauto) {
    int end = len,i,jinc;
    int start = 0;
    byte ret[];
    if(tempauto) {  // used by sayword
      byte newbuf[] = new byte[len/2];
//startPR2008-01-15^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    jinc = shark.macOS?4:2;
      jinc = needMacRate?4:2;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      for(i=0;i<len;i+=jinc) {
        newbuf[i/jinc] = (byte)Math.max(0,Math.min(255,
                   ((((int)buffer[i+1])<<8) | (((int)buffer[i]) & 0x00ff))
                        /256 + 0x007f));
      }
      end = (len /= jinc);
      return getsig(newbuf,end);
    }
    else if(autoboost) {      // autoboost
       int max = 1;
//startPR2008-01-15^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    jinc = shark.macOS?4:2;
      jinc = needMacRate?4:2;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      if(savefrom >= 0) {
        int j;
        len = saveto-savefrom;
        ret = new byte[len];
        for(i=savefrom*jinc,j=0;j<len;i+=jinc,++j) {
           max = Math.max(max,
                      ((((int)buffer[i+1])<<8) | (((int)buffer[i]) & 0x00ff))
                           );
         }
        for(i=savefrom*jinc,j=0;j<len;i+= jinc,++j) {
                ret[j] = (byte)Math.max(0,Math.min(255,
                   ((((int)buffer[i+1])<<8) | (((int)buffer[i]) & 0x00ff))
                          * 0x7fff / max /256 + 0x007f));
         }
         return ret;
      }
      for(i=0;i<len;i+=jinc) {
         max = Math.max(max,
                    ((((int)buffer[i+1])<<8) | (((int)buffer[i]) & 0x00ff))
                         );
       }
      for(i=0;i<len;i+= jinc) {
              buffer[i/jinc] = (byte)Math.max(0,Math.min(255,
                 ((((int)buffer[i+1])<<8) | (((int)buffer[i]) & 0x00ff))
                        * 0x7fff / max /256 + 0x007f));
       }
       end = (len /= jinc);
       return getsig(buffer,end);
    }
    else if(boost != 100) {
//startPR2008-01-15^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    jinc = shark.macOS?4:2;
       jinc = needMacRate?4:2;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       for(i=0;i<len;i+=jinc) {
         buffer[i/jinc] = (byte)Math.max(0,Math.min(255,
                    ((((int)buffer[i+1])<<8) | (((int)buffer[i]) & 0x00ff))
                         * boost/25600 + 0x007f));
       }
       end = (len /= jinc);
    }
//startPR2008-01-15^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    else if(shark.macOS) {
    else if(needMacRate) {
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        for(i=0;i<len;i+=2) {
          buffer[i/2] = buffer[i];
        }
        end = (len /= 2);
    }
    short startch = (short) ( ( (short) buffer[start]) & 0x00ff);
    short endch = (short) ( ( (short) buffer[end-1]) & 0x00ff);
    while(++start<end && Math.abs((((short)buffer[start]) & 0x00ff) - startch) < sig);
    if(start==end) {return null;}
    while((((short) buffer[start]) & 0x00ff) != startch) --start;
    while(Math.abs((((short)buffer[--end]) & 0x00ff) - endch) < sig);
    if(start==end) {return null;}
    ret = new byte[end-start];
    System.arraycopy(buffer,start,ret,0,end-start);
    return ret;
   }
   //---------------------------------------------------------------
public static int topandtail2(byte[] buffer, int len,  int sig, boolean tempauto) {
  int end = len,i,jinc;
  int start = 0;
  byte ret[];
    byte newbuf[] = new byte[len/2];
//startPR2008-01-15^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    jinc = shark.macOS?4:2;
        jinc = needMacRate?4:2;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    for(i=0;i<len;i+=jinc) {
      newbuf[i/jinc] = (byte)Math.max(0,Math.min(255,
                 ((((int)buffer[i+1])<<8) | (((int)buffer[i]) & 0x00ff))
                      /256 + 0x007f));
    }
    end = (len /= jinc);
    return getsig2(newbuf,end);
  }
  //--------------------------------------------------------------------------
   public static byte[] getsig(byte[] buffer,int len) {
     int min1=0x007f, max0=0x007f,min0=0x007f, max1=0x007f,min2=0x007f, max2=0x007f,min3=0x007f, max3=0x007f, ch, sig = 4, i,end=len,start=0;
     byte ret[];
     if(notrim2) {
       savefrom = 0;saveto = len;
       return buffer;
     }
     start = RATE/8;
     if(len > RATE/4) {
       for (i = len - 1; i >= start; --i) {
         ch = ((int)buffer[i]) & 0x00ff;
         if(i < start + RATE/8) {
           min0 = Math.min(ch, min0);
           max0 = Math.max(ch, max0);
         }
         if(i > len - RATE/8) {
           min1 = Math.min(ch, min1);
           max1 = Math.max(ch, max1);
         }
         if(i > len - RATE/2) {    // no 'else' - min2/max2 include the whole last 1/2 sec
           min2 = Math.min(ch, min2);
           max2 = Math.max(ch, max2);
         }
         else  {
           min3 = Math.min(ch, min3);
           max3 = Math.max(ch, max3);
         }
       }
       if(max3-min3 > (max2-min2)*4 && max1<=max0 && min1>=min0 ) {
         byte mid = (byte)((max1+min1)/2);
         int extra = Math.max(1,(max1-min1)/4);
         max1 += extra;
         min1 -= extra;
         while(++start<end && (ch=((int)buffer[start]) & 0x00ff) <= max1 && ch >= min1);
         if(start==end) {return null;}
         while( start>0 && buffer[start] != mid) --start;
         while((ch=((int)buffer[--end]) & 0x00ff) <= max1 && ch >= min1);
         while(end<len && buffer[end] != mid ) ++end;
         if(start==end) {return null;}
         ret = new byte[end-start];
         System.arraycopy(buffer,start,ret,0,end-start);
         savefrom = start;saveto = end;
         return ret;
       }
     }
     savefrom = 0;saveto = len;
     return buffer;
   }
      // dont strip off start - return length
   public static int getsig2(byte[] buffer,int len) {
     int min1=0x007f, max0=0x007f,min0=0x007f, max1=0x007f,min2=0x007f, max2=0x007f,min3=0x007f, max3=0x007f, ch, sig = 4, i,end=len,start=0;
     byte ret[];
     start = RATE/8;
     if(len > RATE/4) {
       for (i = len - 1; i >= start; --i) {
         ch = ((int)buffer[i]) & 0x00ff;
         if(i < start + RATE/8) {
           min0 = Math.min(ch, min0);
           max0 = Math.max(ch, max0);
         }
         if(i > len - RATE/8) {
           min1 = Math.min(ch, min1);
           max1 = Math.max(ch, max1);
         }
         if(i > len - RATE/2) {    // no 'else' - min2/max2 include the whole last 1/2 sec
           min2 = Math.min(ch, min2);
           max2 = Math.max(ch, max2);
         }
         else  {
           min3 = Math.min(ch, min3);
           max3 = Math.max(ch, max3);
         }
       }
       if(max3-min3 > (max2-min2)*4 && max1<=max0 && min1>=min0 ) {
         byte mid = (byte)((max1+min1)/2);
         int extra = Math.max(1,(max1-min1)/4);
         max1 += extra;
         min1 -= extra;
//         while(++start<end && (ch=((int)buffer[start]) & 0x00ff) <= max1 && ch >= min1);
//         if(start==end) {return 0;}
//         while( start>0 && buffer[start] != mid) --start;
         while((ch=((int)buffer[--end]) & 0x00ff) <= max1 && ch >= min1);
         while(end<len && buffer[end] != mid ) ++end;
         return end-start;
       }
     }
     savefrom = 0;saveto = len;
     return len;
   }
     //---------------------------------------------------------------------
  byte[] decompmain(byte input[]) {
   if(input==null) return new byte[0];
   int len = input.length;
   short val = 0x007f;
   short efflen = 0;
   int outcurr=0;
   int outlen=0;
   byte outr[] = null;
   short i;

   decompressed = true;
   if(len>=4 && input[3] != 0) {
    if(len <= FIXEDLEN) {       // old style -----------------------------
         outr = input;
    }
    else try{
      offset = FIXEDLEN;
      outcurr=FIXEDLEN;
      pos = 0x0080;
      in = input;
      outlen = (((int)getbits((short)8))<<16);
      outlen += (((int)getbits((short)8))<<8);
      outlen += getbits((short)8);
      outr = new byte[outlen];
      System.arraycopy(input,0,outr,0,FIXEDLEN);
      while(outcurr < outlen) {
         switch(getbits((short)2)) {
            case 1: ++efflen;break;
            case 2: --efflen;break;
            case 3:
              if(getbits((short)1)!=0){efflen-=2; while (getbits((short)1) != 0) --efflen;}
              else             {efflen+=2; while (getbits((short)1) != 0) ++efflen;}
         }
         for(i=(short)Math.min(4,outlen-outcurr); i>0;--i) {
            if(efflen !=0) val += getbitsSign(efflen);
            outr[outcurr++] = (byte)val;
         }
      }
     }
     catch (IndexOutOfBoundsException ex)  {
       int outsofar  = outcurr;
       int outlennow = outlen;
       byte[] outrnow = outr;
       int insofar =offset;
       in=null;
       return null;
     }
     int hlen = hdr.length+2;
     byte[] newdata = new byte[outr.length - hlen];
     System.arraycopy(outr, hlen, newdata, 0, outr.length - hlen );
     in=null;
     return newdata;
   }
   else { //------------- new style --------------------------------
    try{
      offset = 0;
      outcurr = 0;
      pos = 0x0080;
      in = input;
      outlen = (((int)getbits((short)8))<<16);
      outlen += (((int)getbits((short)8))<<8);
      outlen += getbits((short)8);
      ++offset;  // skip 'type' byte
      outr = new byte[outlen];
      while(outcurr < outlen) {
         switch(getbits((short)2)) {
            case 1: ++efflen;break;
            case 2: --efflen;break;
            case 3:
              if(getbits((short)1)!=0){efflen-=2; while (getbits((short)1) != 0) --efflen;}
              else             {efflen+=2; while (getbits((short)1) != 0) ++efflen;}
         }
         for(i=(short)Math.min(4,outlen-outcurr); i>0;--i) {
            if(efflen !=0) val += getbitsSign(efflen);
            outr[outcurr++] = (byte)val;
         }
      }
     }
     catch (IndexOutOfBoundsException ex)  {
       int outsofar  = outcurr;
       int outlennow = outlen;
       byte[] outrnow = outr;
       int insofar =offset;
       in = null;
       return null;
     }
   }
   in=null;
   return outr;
  }


  byte[] decomp1() {
    return decompmain(db.findwav(database,name));
  }

  byte[] decompextra1(String strname, String db1, boolean privatelist) { 
     byte b[] = null; 
    if(privatelist && sharkStartFrame.currPlayTopic!=null && sharkStartFrame.currPlayTopic.recs!=null){
                    for(int j = 0; j < sharkStartFrame.currPlayTopic.recs.length; j++){
                        if(sharkStartFrame.currPlayTopic.recs[j].name.equals(strname)){
                            b = sharkStartFrame.currPlayTopic.recs[j].rec;
                            break;
                        }
                    }
    }
    if(db1==null)
        db1 = sharkStartFrame.resourcesdb;
    if(b==null)
        b = db.findwav(db1,strname);
    if(b==null){
        for(int i = 0; i < sharkStartFrame.publicSoundLib.length; i++){
           b = db.findwav(sharkStartFrame.publicSoundLib[i],strname);
           if(b!=null)break;
        }
    }
    return decompmain(b);
  }

   //------------------------------------------------------
       // decompress (if necessary) and say
   public void say() {
      waitforsay();
      endsay =System.currentTimeMillis() + 1000; // reserve it
      if(decompressed)  
          endsay2 = System.currentTimeMillis() + (long)data.length*1000/RATE;
      (new Thread(this)).start();
   }
   //------------------------------------------------------
   public  void run() {
      long len;
      int i,j;
      if(recording ) {
         running = true;
         notrim2 = notrim;
         // to extend recording time change the 16s below
         data = (boost==100 && !autoboost)? new byte[RATE*29]:new byte[RATE*29*2];
         i=0;
         try {
            DataLine.Info infot;
//startPR2008-01-15^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//            if(shark.macOS)
//               infot = new DataLine.Info(TargetDataLine.class,
//                                                      (boost==100 && !autoboost)?macformat:macformat2);
//            else  infot = new DataLine.Info(TargetDataLine.class,
//                                                    (boost==100 && !autoboost)?format:format2);
//            mike  = (TargetDataLine)AudioSystem.getLine(infot);
            if(!needMacRate)
              infot = new DataLine.Info(TargetDataLine.class,
                                        (boost==100 && !autoboost)?format:format2);
            else
              infot = new DataLine.Info(TargetDataLine.class,
//                                        (boost==100 && !autoboost)?macformat:macformat2);
            macformat2);
            try{
              mike  = (TargetDataLine)AudioSystem.getLine(infot);
            }
            catch(IllegalArgumentException e1){
              if(shark.macOS){
                infot = new DataLine.Info(TargetDataLine.class,
//                                          (boost==100 && !autoboost)?macformat:macformat2);
                macformat2);
                mike  = (TargetDataLine)AudioSystem.getLine(infot);
                needMacRate = true;
              }
            }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            mike.open((boost==100 && !autoboost)?format:format2);
         }
         catch(LineUnavailableException e) {
//startPR2004-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//            JOptionPane.showMessageDialog(null,e.getMessage());
            if(runningGame.currGameRunner==null || runningGame.currGameRunner.game==null)
                JOptionPane.showMessageDialog(sharkStartFrame.mainFrame,e.getMessage());
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            recording = false;
            if(currrecord != null){
              currrecord.doingRecording = false;
              currrecord.setButtons();
            }
            else if (currrectool != null){
              currrectool.haltRecording();
//              currrectool.setButtons();                
            }

            running = false;
            return;
         }
         mike.addLineListener(new LineListener() {
           public void update(LineEvent e) {
              if(e.getType() == LineEvent.Type.STOP) {
                 recording=false;
              }
           }
         });
         mike.start();
         u.pause(200);
         long t1 = System.currentTimeMillis();
         int lastlength = -1;
         long lasttime = System.currentTimeMillis();
         while(recording && i < data.length) {
            if(mike.available() > 0 ){
               i += mike.read(data, i, Math.min(data.length-i,mike.available()));
               
            }
            gotsofar = i;
//            if(autoboost && currrecord != null) {
//                int dlen = spokenWord.topandtail2(data, gotsofar,4,true); // temp
//                if(data != null && dlen == lastlength && System.currentTimeMillis() > lasttime + 600) {
//                  stopRecording();
//                  currrecord.doingRecording = false;
//                  currrecord.setButtons();
//                  currrecord.requestFocus(); // get rid of info box if long text to record
//                  running = false;
//                  break;
//                }
//                else if (data != null && dlen > lastlength) {
//                   currrecord.newpatt.setup(data,dlen);
//                   lastlength = dlen;
//                   lasttime = System.currentTimeMillis();
//                }
//            }
            if(autoboost) {
                if(currrecord != null){
                    int dlen = spokenWord.topandtail2(data, gotsofar,4,true); // temp
                    if(data != null && dlen == lastlength && System.currentTimeMillis() > lasttime + 600) {
                      stopRecording();
                      currrecord.doingRecording = false;
                      currrecord.setButtons();
                      currrecord.requestFocus(); // get rid of info box if long text to record
                      running = false;
                      break;
                    }
                    else if (data != null && dlen > lastlength) {
                       currrecord.newpatt.setup(data,dlen);
                       lastlength = dlen;
                       lasttime = System.currentTimeMillis();
                    }
                }
                else if (currrectool != null){
                    int dlen = spokenWord.topandtail2(data, gotsofar,4,true); // temp
                    if(data != null && dlen == lastlength && System.currentTimeMillis() > lasttime + 600) {
                      stopRecording();
                      currrectool.haltRecording();
//                      currrectool.setButtons();
                      currrectool.focusSound(); // get rid of info box if long text to record
                      running = false;
                      break;
                    }
                    else if (data != null && dlen > lastlength) {
                        currrectool.setupSoundW(data,dlen);
 
                       lastlength = dlen;
                       lasttime = System.currentTimeMillis();
                    }
                }
            }


            u.pause(100);
         }
         long t2=System.currentTimeMillis();
         int want = (int) ((t2-t1)*RATE/1000);
         if(boost != 100  || autoboost) want *=2;
         want = Math.min(data.length,want);
         while(i<want && System.currentTimeMillis() < t2+2000) {
            if(mike.available() > 0 && i<want )
              i += mike.read(data, i, Math.min(want - i, mike.available()));
            gotsofar = i;
         }
         mike.stop();
         mike.close();
         mike=null;
         recording = false;
         //------------------
         gotsofar = 0;
         byte newdata[] = newdata = topandtail(data, i, sig, false);
         if (newdata == null) {
             data = new byte[0];
             if (currrecord != null)
              currrecord.isNewWord = false;
 //            if (currrectool != null)
   //            currrectool.isNewWord = false;
   //startPR2004-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   //             u.okmess("rec_nosound");
             if (!skipmess)
               u.okmess("rec_nosound", sharkStartFrame.mainFrame);
   //endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         }
         else {
             decompressed = true;
             if (currrecord != null)
               currrecord.isNewWord = true;
    //         else if (currrectool != null)
    //           currrectool.isNewWord = true;
             data = newdata;
             gotsofar = data.length;
             if (currrecord != null) {
               currrecord.newpatt.setup(data);
               say(data);
             }
             else if (currrectool != null) {
               currrectool.setupSoundW(data);
               say(data);
             }
         }
         if (currrecord != null) {
             currrecord.doingRecording = false;
             currrecord.setButtons();
         }
         else if (currrectool != null) {
             currrectool.haltRecording();
//             currrecord.setButtons();
         }
         running = false;
         return;
      }
      if(!decompressed) {
         if(homophone) decomphomo();
         else          decomp();
      }
      if(data != null)
          say(data);
      return;
   }
   //-------------------------------------------------------------------
   
    public static boolean isMicrophoneDefinitelyUnavailable() {
        try {
            if (!AudioSystem.isLineSupported(Port.Info.MICROPHONE)) {
                return true;
            } else {
                return false;
            }
        } catch (IllegalArgumentException e) {
        }
        return false;
    } 
    
    
   static void stopspeaker() {
      if(speaker != null)  {
         speaker.flush();
         speaker.stop();
         speaker.close();
         speaker = null;
         u.pause(400);
      }
   }
   //----------------------------------------------------------------
   public void startRecording() {
      int i;
      savefrom = -1;
      stopspeaker();
      recording=true;
      gotsofar = 0;
      new Thread(this).start();
   }
   public void stopRecording() {
//startPR2008-10-31^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      if(mike != null) mike.drain();
      if(mike != null)
          mike.flush();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      recording = false;
   }
   //--------------------------------------------------------
   public static boolean sayPhonicsWord(word w, int gap, boolean both,boolean fromlisten,boolean notsayfull) {  // rb 20/5/07
     int i;
     if ( (i = w.value.indexOf('=')) > 0) {
      if(w.value.indexOf(u.phonicsplit) > 0) { // phonics word


        String wvalue = w.value.substring(i + 1);
        int p;
        if((p = wvalue.indexOf('@'))>0)wvalue = wvalue.substring(0, p);


        String s[] = u.splitString(wvalue, u.phonicsplit);
//        if(w.onephoneme && s.length>1 && s[s.length-1].trim().equals("")){
//            s = u.removeString(s, s.length-1);
//        }   
        spokenWord sw[] = new spokenWord[both ? s.length + 1 : s.length];
//startPR2006-08-22^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        boolean someSoundsNotSet = false;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        for (i = 0; i < s.length; ++i){
          if(s[i].startsWith("/")) s[i] = s[i].substring(1);
//startPR2006-08-22^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          if (s[i].indexOf("?")>=0) {
            someSoundsNotSet = true;
            sw[i] = new spokenWord("dummy",null,false);
            sw[i].data = noise.marker_onesound();
          }
          else
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          sw[i] = findandreturn(s[i] + '~', w.database);
        }
        if (both){
          sw[i] = getPhonicsWhole(w);
//startPR2006-08-22^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          if(sw[i]==null){
            spokenWord news[] = new spokenWord[sw.length-1];
            System.arraycopy(sw,0,news,0,sw.length-1);
            sw = news;
          }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//          else if(w.onephoneme) sw = new spokenWord[]{sw[0]};
          else if(w.onephoneme) sw = new spokenWord[]{sw[i]};
        }
        spokenWord sw2 = combine(sw, gap);
//startPR2006-08-22^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        // required, otherwise doesn't work
        if(someSoundsNotSet)
            sw2.decompressed = true;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        if(w.homophone){
            sw2.addhomo(w.vsay());
        }
        sw2.say();
        return true;
      }
      else  if(w.value.indexOf('+') < 0) { // phonics sound - use part after =
        String sh = w.value.substring(i+1);
        String sh2 = w.value.substring(0,i);
        spokenWord sw = spokenWord.findandreturn(sh+'~',w.database);
        if(sw != null) {
          if((fromlisten || u.findString(sayfull,w.value.substring(0,i))>=0) && !notsayfull) {  // rb 20/5/07
//startPR2008-09-23^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//            spokenWord sw2 = spokenWord.findandreturn(sh+"~=",w.database);
//            if(sw2 == null && (sh = u.gettext("phonicshomos",sh2)) != null) {
//              sw2 =  spokenWord.findandreturn(sh,w.database);
//            }
            spokenWord sw2 = null;
            if((sh = u.gettext("phonicshomos",sh2)) != null){
                int j;
                if((j=sh.lastIndexOf("@@homos"))>=0)sh = sh.substring(0,j);
              sw2 =  spokenWord.findandreturn(sh,w.database);
            }
            if(sw2 == null) sw2 = spokenWord.findandreturn(sh+"~=",w.database);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            if(sw2 != null) {
                 byte[] mark = noise.marker();
                 byte newdata[] = new byte[sw.data.length+sw2.data.length+mark.length];
                 System.arraycopy(sw.data,0,newdata,0,sw.data.length);
                 System.arraycopy(mark,0,newdata,sw.data.length,mark.length);
                 System.arraycopy(sw2.data,0,newdata,sw.data.length+mark.length,sw2.data.length);
                 sw.data = newdata;
             }
          }
          sw.say();
          return true;
        }
      }
     }
     return false;
   }
   //--------------------------------------------------------
                 // save phonemes separately and say them marking the word
   public static boolean sayPhonicsWord(word w, int gap, boolean both,boolean fromlisten,boolean notsayfull,JComponent jc) {
     if(shark.language.equals(shark.LANGUAGE_NL) )notsayfull=false;
     int i;
     if ( (i = w.value.indexOf('=')) > 0) {
      if(w.value.indexOf(u.phonicsplit) > 0) { // phonics word

        String wvalue = w.value.substring(i + 1);
        int p;
        if((p = wvalue.indexOf('@'))>0)wvalue = wvalue.substring(0, p);

        String s[] = u.splitString(wvalue, u.phonicsplit);
        spokenWord sw[] = new spokenWord[both ? s.length + 1 : s.length];
        for (i = 0; i < s.length; ++i){
//startPR2006-08-22^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          if(s[i].startsWith("/")) s[i] = s[i].substring(1);
          if (s[i].indexOf("?")>=0) {
            sw[i] = new spokenWord("dummy",null,false);
            sw[i].data = noise.marker_onesound();
          }
          else
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          sw[i] = findandreturn(s[i] + '~', w.database);
        }
        if (both){
          sw[i] = getPhonicsWhole(w);
//          if(sw[i]!=null && w.onephoneme) sw = new spokenWord[]{sw[0],null};
          if(sw[i]!=null && w.onephoneme) sw = new spokenWord[]{sw[i],null};
        }
        if(w.homophone){
            int k = i;
            if(w.onephoneme){
                for(int n = 0; n < sw.length; n++){
                    if(sw[n]!=null)k = n;
                }
            }
            sw[k].addhomo(w.vsay());
        }
        w.sayandmark(sw,jc);
        return true;
      }
      else  if(w.value.indexOf('+') < 0) { // phonics sound - use part after =
        String sh = w.value.substring(i+1);
        String sh2 = w.value.substring(0,i);
        spokenWord sw = spokenWord.findandreturn(sh+'~',w.database);
        if(sw != null) {
          if((fromlisten || u.findString(sayfull,w.value.substring(0,i))>=0) && !notsayfull) {  // rb 20/5/07
//startPR2008-09-23^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//            spokenWord sw2 = spokenWord.findandreturn(sh+"~=",w.database);
//            if(sw2 == null && (sh = u.gettext("phonicshomos",sh2)) != null) {
//              sw2 =  spokenWord.findandreturn(sh,w.database);
//            }
            spokenWord sw2 = null;
            if((sh = u.gettext("phonicshomos",sh2)) != null){
                int j;
                if((j=sh.lastIndexOf("@@homos"))>=0)sh = sh.substring(0,j);
                sw2 =  spokenWord.findandreturn(sh,w.database);
            }
            if(sw2 == null) sw2 = spokenWord.findandreturn(sh+"~=",w.database);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            if(sw2 != null) {
                 byte[] mark = noise.marker();
                 byte newdata[] = new byte[sw.data.length+sw2.data.length+mark.length];
                 System.arraycopy(sw.data,0,newdata,0,sw.data.length);
                 System.arraycopy(mark,0,newdata,sw.data.length,mark.length);
                 System.arraycopy(sw2.data,0,newdata,sw.data.length+mark.length,sw2.data.length);
                 sw.data = newdata;
             }
          }
          sw.say();
          return true;
        }
      }
     }
     return false;
   }
   //--------------------------------------------------------
   public static boolean getPhonicsWord(word w, int gap, boolean both,boolean fromlisten,boolean notsayfull) { // rb 20/5/07
     int i;
     int phonicsend1[];
     int phonicscurr = 0;
     if ( (i = w.value.indexOf('=')) > 0) {
      if(w.value.indexOf(u.phonicsplit) > 0) { // phonics word
          
        String wvalue = w.value.substring(i + 1);
        int p;
        if((p = wvalue.indexOf('@'))>0)wvalue = wvalue.substring(0, p);          
          
        String s[] = u.splitString(wvalue, u.phonicsplit);
        if(w.onephoneme && s.length>1 && s[s.length-1].trim().equals("")){
            s = u.removeString(s, s.length-1);
        }
        phonicsend1 = new int[s.length];
        spokenWord sw[] = new spokenWord[both ? s.length + 1 : s.length];
//startPR2006-08-22^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        boolean someSoundsNotSet = false;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        for (i = 0; i < s.length; ++i){
          if(s[i].startsWith("/")) s[i] = s[i].substring(1);
//startPR2006-08-22^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          if (s[i].indexOf("?")>=0) {
            someSoundsNotSet = true;
            sw[i] = new spokenWord("dummy",null,false);
            sw[i].data = noise.marker_onesound();
          }
          else
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
             sw[i] = findandreturn(s[i] + '~', w.database);
          phonicsend1[i] = (phonicscurr += sw[i].data.length + spokenWord.RATE * gap / 1000);
         }
        if (both){
          sw[i] = getPhonicsWhole(w);
//startPR2006-08-22^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          if(sw[i]==null){
            spokenWord news[] = new spokenWord[sw.length-1];
            System.arraycopy(sw,0,news,0,sw.length-1);
            sw = news;
          }
          else if(w.onephoneme) {
//             sw = new spokenWord[] {sw[0]};
             sw = new spokenWord[] {sw[i]};
          }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        }
        spokenWord sw2 = combine(sw, gap);
        sw2.phonicsend = phonicsend1;
//startPR2006-08-22^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        // required, otherwise doesn't work
        if(someSoundsNotSet)
            sw2.decompressed = true;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        if (sw2 != null)
          if(w.homophone) sw2.addhomo(w.vsay());
          w.spokenword = sw2;
        return true;
      }
      else  if(w.value.indexOf('+') < 0) { // phonics sound - use part after =
        String sh = w.value.substring(i+1);
        String sh2 = w.value.substring(0,1);
        spokenWord sw = spokenWord.findandreturn(sh+'~',w.database);
        if(sw != null) {
          if((fromlisten || u.findString(sayfull,w.value.substring(0,i))>=0) && !notsayfull) { // rb 20/5/07
//startPR2008-09-23^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//            spokenWord sw2 = spokenWord.findandreturn(sh+"~=",w.database);
//            if(sw2 == null && (sh = u.gettext("phonicshomos",sh2)) != null) {
//              sw2 =  spokenWord.findandreturn(sh,w.database);
//            }
            spokenWord sw2 = null;
            if((sh = u.gettext("phonicshomos",sh2)) != null){
                int j;
                if((j=sh.lastIndexOf("@@homos"))>=0)sh = sh.substring(0,j);
              sw2 =  spokenWord.findandreturn(sh,w.database);
            }
            if(sw2 == null) sw2 = spokenWord.findandreturn(sh+"~=",w.database);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            if(sw2 != null) {
                 byte[] mark = noise.marker();
                 byte newdata[] = new byte[sw.data.length+sw2.data.length+mark.length];
                 System.arraycopy(sw.data,0,newdata,0,sw.data.length);
                 System.arraycopy(mark,0,newdata,sw.data.length,mark.length);
                 System.arraycopy(sw2.data,0,newdata,sw.data.length+mark.length,sw2.data.length);
                 sw.data = newdata;
             }
          }
          w.spokenword = sw;
          return true;
        }
      }
     }
     return false;
   }
   //--------------------------------------------------------
   public static boolean sayPhonicsBit(String s, word[] ww) {   // locate segment somewhere in words and say it
     int i,j,k;
     for(j=0;j<ww.length;++j) {
       if((i=ww[j].value.indexOf('=')) > 0) {
         if(ww[j].value.indexOf(u.phonicsplit) > 0) {   // phonics word
           if((k = u.findString(ww[j].phsegs(),s)) >=0) {
              sayPhonicsBit(ww[j],k);
              return true;
           }
         }
         else          {    // phonics sound
           if(s.equalsIgnoreCase(ww[j].value.substring(0,i))) {
             sayPhonicsWord(ww[j], 500, false, false,false);
             return true;
           }
         }
       }
     }
     return false;
   }
   //--------------------------------------------------------
   public static String findPhonicsBit(String s, word[] ww) {   // locate segment somewhere in words
     int i,j,k;
     for(j=0;j<ww.length;++j) {
       if((i=ww[j].value.indexOf('=')) > 0) {
         if(ww[j].value.indexOf(u.phonicsplit) > 0) {   // phonics word
           if((k = u.findString(ww[j].phsegs(),s)) >=0) {
              return ww[j].phsegs()[k];
           }
         }
         else          {    // phonics sound
           if(s.equalsIgnoreCase(ww[j].value.substring(0,i))) {
             return ww[j].value.substring(i+1);
           }
         }
       }
     }
     return null;
   }
   //--------------------------------------------------------
   public static boolean isSilent(String s, word[] ww) {   // locate segment somewhere in words and see if silent
     int i,j,k;
     for(j=0;j<ww.length;++j) {
       if((i=ww[j].value.indexOf('=')) > 0) {
         if(ww[j].value.indexOf(u.phonicsplit) > 0) {   // phonics word
           if((k = u.findString(ww[j].phsegs(),s)) >=0) {
              return ww[j].phonicsilent[k];
           }
         }
       }
     }
     return false;
   }
   //--------------------------------------------------------
   public static String phonicname(String s, word[] ww) {   // locate segment somewhere in words and say it
     int i,j,k;
     for(j=0;j<ww.length;++j) {
       if((i=ww[j].value.indexOf('=')) > 0) {
         if(ww[j].value.indexOf(u.phonicsplit) > 0) {   // phonics word
           if((k = u.findString(ww[j].phsegs(),s)) >=0) {
             return ww[j].phonics()[k];
            }
         }
         else          {    // phonics sound
           if(s.equalsIgnoreCase(ww[j].value.substring(0,i))) {
             return ww[j].phonics()[0];
            }
         }
       }
     }
     return null;
   }
   //--------------------------------------------------------
   public static boolean sayPhonicsBit(word w, int which) {
     int i;
     if((i=w.value.indexOf('=')) > 0 && w.value.indexOf(u.phonicsplit) > 0) {   // phonics word
         spokenWord sw =   findandreturn(w.phonics()[which] + '~',w.database);
         if(sw != null) {
           sw.say();
           return true;
         }
     }
     return false;
   }
   //----------------------------------------------------------------------
   public static void sayPhonicsSyl(word w, int which) {    // say phonics syllable
     if(w.phonicsplitlist != null) {
        if(u.inlist(w.phonicsplitlist,which)) {
          String ss = w.phpart(which);
          if(ss.indexOf(u.phonicsplit)<0 ) return;
          if (!findandsay1(ss + "~~") && !findandsay1(ss + "~"))
            findandsay1(ss);
        }
     }
   }
   //--------------------------------------------------------
   public static boolean sayPhonic(String ss) {
     spokenWord sw =   findandreturn(ss + '~',null);
     if(sw != null) {sw.say();        return true;}
     return false;
   }
   //--------------------------------------------------------
   public static boolean sayMagicPhonic(String ss) {
     spokenWord sw =   findandreturn(ss + "-e~",null);
     if(sw != null) {sw.say();        return true;}
     return false;
   }
   //--------------------------------------------------------
   public static spokenWord getPhonicsWhole(word w) {
     int i;
     spokenWord sw;
     if(w.phonicsw && !w.onephoneme)     {
       sw = findandreturn(w.vsay() + '~', w.database);
       if (sw != null)   {
           return sw;
       }
     }
     else if(w.phonicsw && w.onephoneme) {
         sw = findandreturn(w.phonics()[0], w.database);
         if(sw==null) sw = findandreturn(w.phonics()[0] + "~", w.database);
         if (sw != null)     {
             return sw;
         }
     }
     else if(w.phonics && w.onephoneme) {
         sw = findandreturn(w.phonics()[0] + '~', w.database);
         if(sw==null) sw = findandreturn(w.phonics()[0] + "~", w.database);
         if (sw != null)     {
             return sw;
         }

       }
     sw = findandreturn(w.vsay(),w.database);
     if(sw != null) {
       if(w.homophone){
           sw.decomphomo();
       }
       else          sw.decomp();
     }
     return sw;
   }
   //--------------------------------------------------------
   public static boolean sayPhonicsWhole(word w) {
     int i;
     spokenWord sw = getPhonicsWhole(w);
     if(w.homophone){
         sw.addhomo(w.vsay());
     }
     if(sw != null) {sw.say();        return true;}
     return false;
   }

   //--------------------------------------------------------
   public static boolean getSpokenWord(word w) {
       return getSpokenWord(w, false);
   }
   
   public static boolean getSpokenWord(word w, boolean overrideSpecialPrivate) {
      spokenWord ret = null;
      int i;
      String name = w.vsay();
      if(w.dbprivate != null){
         if(sharkStartFrame.currPlayTopic!=null && sharkStartFrame.currPlayTopic.ownlist){
            w.database=w.dbprivate;
   //         if((sharkStartFrame.currPlayTopic.translations || sharkStartFrame.currPlayTopic.definitions)){
                if(!overrideSpecialPrivate && (sharkStartFrame.currPlayTopic.xrecs!=null &&((sharkStartFrame.currPlayTopic.definitions && wordlist.usedefinitions) ||
           (sharkStartFrame.currPlayTopic.translations &&  wordlist.usetranslations)))){
                    for(int j = 0; j < sharkStartFrame.currPlayTopic.xrecs.length; j++){
                        if(sharkStartFrame.currPlayTopic.xrecs[j].name.equals(w.value.replaceAll("/", ""))){
                            w.spokenword = new spokenWord(w.value,w.dbprivate ,false);
                            w.spokenword.decomp(sharkStartFrame.currPlayTopic.xrecs[j].rec);
                            return true;
                        }
                    }
                }
      
                else{
                    if(sharkStartFrame.currPlayTopic.recs!=null){
                    for(int j = 0; j < sharkStartFrame.currPlayTopic.recs.length; j++){
                        if(sharkStartFrame.currPlayTopic.recs[j].name.equals(w.value.replaceAll("/", ""))){
                            w.spokenword = new spokenWord(w.value,w.dbprivate ,false);
                            w.spokenword.decomp(sharkStartFrame.currPlayTopic.recs[j].rec);
                            return true;
                        }
                    }
                }                      
                }
                
     //       }
         }
      }
      if(sharkStartFrame.currPlayTopic!=null &&(sharkStartFrame.currPlayTopic.fl && (!sharkStartFrame.currPlayTopic.translations && !sharkStartFrame.currPlayTopic.definitions)))
          w.database="publicsay3";
      if(w.database == null || db.query(w.database,name,db.WAV) < 0) {
         for(i = 0;i < sharkStartFrame.publicSoundLib.length;++i) {
            if(db.query(sharkStartFrame.publicSoundLib[i],name,db.WAV) >= 0) {
               w.database = sharkStartFrame.publicSoundLib[i];
               break;
            }
         }
         if(i >= sharkStartFrame.publicSoundLib.length) {           // rb 27/5/08
           ret = findandreturn(new String[]{w.vsay()}, w.database); // rb 27/5/08
           if(ret==null) return false;                              // rb 27/5/08
           w.spokenword = ret;                                      // rb 27/5/08
           return true;                                             // rb 27/5/08
         }                                                          // rb 27/5/08
      }
      for (i = storeTot-1; i>=0; --i) {
         if(store[i].name.equalsIgnoreCase(name)
               && store[i].database.equalsIgnoreCase(w.database)
               && store[i].homophone == w.homophone) {
            if(i != storeTot-1) {
               ret = store[i];
               System.arraycopy(store,i+1,store,i,storeTot-i-1);
               store[storeTot-1] = ret;
               w.spokenword = ret;
               return true;
            }
         }
      }
      if(storeTot == MAXSTORE) {
         System.arraycopy(store,1,store,0,MAXSTORE-1);
         i =  MAXSTORE-1;
      }
      else {
         i =storeTot++;
      }
      store[i] = ret = new spokenWord(name,
                   w.database,w.homophone);
      w.spokenword = ret;
      return true;
   }
   //-------------------------------------------------------------
     public static boolean findandsay(String name) {
       spokenWord sw = findandreturn(new String[] {name}, null);
       if (sw != null) {
         sw.say();
         return true;
       }
       else
         return false;
     }
     //-------------------------------------------------------------
       public static boolean findandsay(String name, String database) {
         spokenWord sw = findandreturn(new String[] {name}, database);
         if (sw != null) {
           sw.say();
           return true;
         }
         else
           return false;
       }
       //-------------------------------------------------------------
       public static boolean findandsay1(String name) {
           spokenWord sw = findandreturn(name,null);
           if (sw != null) {
             sw.say();
             return true;
           }
           else
             return false;
       }
//-------------------------------------------------------------
public static boolean findandsay(Object[] names) {
   spokenWord sw = findandreturn(names,null);
   if(sw != null) {
      sw.say();
      return true;
   }
   else return false;
}
//-------------------------------------------------------------
public static boolean findandsaydef(String name) {
    short i;
    extrainf = null;
    for(i = 0;i < sharkStartFrame.publicDefLib.length;++i) {
         if(db.query(sharkStartFrame.publicDefLib[i],name,db.WAV) >= 0) {
           spokenWord sw = new spokenWord(name,
                                    sharkStartFrame.publicDefLib[i],false);
           if(sw != null) {
             sw.say();
             extrainf = (String)db.find(sharkStartFrame.publicDefLib[i],name,db.TEXT);
             return true;
           }
         }
    }
    return false;
}
//-------------------------------------------------------------
public static boolean findandsaysentence(String name) {
    short i;
    short j;
    if((j=(short)name.indexOf("{")) > 0 && name.indexOf("}") > 0)
        name = name.substring(0,j);
    for(i = 0;i < sharkStartFrame.publicSentLib.length;++i) {
         if(db.query(sharkStartFrame.publicSentLib[i],name,db.WAV) >= 0) {
            spokenWord sw = new spokenWord(name, sharkStartFrame.publicSentLib[i],false);
            sw.say();
            return true;
         }
    }
    return false;
}
//-------------------------------------------------------------
public static boolean findandsaysentence2(String name) {
    short i;
    short j;
    if((j=(short)name.indexOf("{")) > 0 && name.indexOf("}") > 0)
        name = name.substring(0,j);
    for(i = 0;i < sharkStartFrame.publicSent2Lib.length;++i) {
         if(db.query(sharkStartFrame.publicSent2Lib[i],name,db.WAV) >= 0) {
            spokenWord sw = new spokenWord(name, sharkStartFrame.publicSent2Lib[i],false);
            sw.say();
            return true;
         }
    }
    return false;
}
//-------------------------------------------------------------
public static boolean findandsaysentence3(String name) {
    short i;
    short j;
    if((j=(short)name.indexOf("{")) > 0 && name.indexOf("}") > 0)
        name = name.substring(0,j);
    for(i = 0;i < sharkStartFrame.publicSent3Lib.length;++i) {
         if(db.query(sharkStartFrame.publicSent3Lib[i],name,db.WAV) >= 0) {
            spokenWord sw = new spokenWord(name, sharkStartFrame.publicSent3Lib[i],false);
            sw.say();
            return true;
         }
    }
    return false;
}
//startPR2010-05-28^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//public static boolean findandsaysentence4(String name) {
//    short i;
//    for(i = 0;i < sharkStartFrame.publicSent4Lib.length;++i) {
//         if(db.query(sharkStartFrame.publicSent4Lib[i],name,db.WAV) >= 0) {
//            spokenWord sw = new spokenWord(name, sharkStartFrame.publicSent4Lib[i],false);
//            sw.say();
//            return true;
//         }
//    }
//    return false;
//}
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  //-------------------------------------------------------------------
  public static boolean saysentencefor(String s) {
    sentence sen[];
    int i;
    if(sharkStartFrame.currPlayTopic != null ) {
      sen = sharkStartFrame.mainFrame.wordTree.getsentences(sharkStartFrame.currPlayTopic.getSpecials(topic.sentencegames2));
      for(i = 0; i<sen.length;++i) {
        if(sen[i].type == sentence.SIMPLECLOZE
               && u.findString(sen[i].getAnswerList(),s) >= 0) {
           sen[i].readsentence();
           return true;
        }
      }
    }
    return false;
  }
//------------------------------------------------------------
public static spokenWord findandreturn(String name,String database1) {
    String database = null;
    spokenWord sw = null;
    int i;
    if (database1 != null && db.query(database1, name, db.WAV) >= 0) {
      database = database1;
    }
    else {
      for (i = 0; i < sharkStartFrame.publicSoundLib.length; ++i) {
        if (db.query(sharkStartFrame.publicSoundLib[i], name, db.WAV) >= 0) {
          database = sharkStartFrame.publicSoundLib[i];
          break;
        }
      }
    }
    if (database != null) {
      sw = new spokenWord(name, database, false);
      sw.decomp();
     }
//startPR2009-07-14^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    if(sw == null) {                                           // rb 27/5/08
     // put in because northern words weren't being pronounced in the northern way
    if(sw == null && !name.endsWith("!~")) {                                           // rb 27/5/08
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       return  findandreturn(new String[]{name}, database1);   // rb 27/5/08
     }                                                         // rb 27/5/08
    return sw;
  }

//------------------------------------------------------------
public static boolean query(String name,String database1) {
    String database = null;
    spokenWord sw = null;
    int i;
    if (database1 != null && db.query(database1, name, db.WAV) >= 0) {
      return true;
    }
    else {
      for (i = 0; i < sharkStartFrame.publicSoundLib.length; ++i) {
        if (db.query(sharkStartFrame.publicSoundLib[i], name, db.WAV) >= 0) {
          return true;
        }
      }
    }
    return false;
  }

//------------------------------------------------------------
public static spokenWord findandreturn(Object[] names,String database1) {
   String database = null,name1,name="dummy",list[];
   word wd;
   spokenWord sw = null;
   byte mark[] = null;
   short namelen;
   short i,len,len2,pos=0,pos2;
   int j;
   for(int k=0;k<names.length;++k) {
    if(names[k] instanceof String) {
     pos = 0;
     name = (String)names[k];
     namelen = (short)name.length();
     while(pos < namelen) {
      database = null;
      while(pos < namelen && sentence.separators.indexOf(name.charAt(pos)) >= 0)
               ++pos;
      if(pos == namelen) break;
      if(name.charAt(pos)=='\'') {
        ++pos;
        if(pos == namelen) break;
      }
      for (pos2 = (short)(pos+1);
          pos2 < namelen && sentence.separators.indexOf(name.charAt(pos2)) < 0;
           ++pos2);
      if(name.charAt(pos2-1)=='\'') --pos2;
      name1 = name.substring(pos,pos2);
      if(name1.equals("%%") ) {
         mark = noise.marker();
         if(sw != null) {
            byte newdata[] = new byte[sw.data.length+mark.length];
            System.arraycopy(sw.data,0,newdata,0,sw.data.length);
            System.arraycopy(mark,0,newdata,sw.data.length,mark.length);
            sw.data = newdata;
            mark = null;
         }
          pos = pos2;
         continue;
      }
      if(name1.startsWith("%%%") ) {   // silence
         int gap = Integer.parseInt(name1.substring(3));
         mark = noise.silence(gap);
         if(sw != null) {
            byte newdata[] = new byte[sw.data.length+mark.length];
            System.arraycopy(sw.data,0,newdata,0,sw.data.length);
            System.arraycopy(mark,0,newdata,sw.data.length,mark.length);
            sw.data = newdata;
            mark = null;
         }
          pos = pos2;
         continue;
      }
      if(pos2 < namelen || pos>0) { // try for phrase recorded
//        list = db.list(sharkStartFrame.publicSentLib[0],db.WAV,name1);
        list = db.list(sharkStartFrame.getPrimarySoundDb(sharkStartFrame.publicSentLib),db.WAV,name1);        
        for(i=0;i<list.length;++i) {
           len2 = (short)(list[i].length() +  pos);
           if(namelen >= len2 && name.substring(pos,len2).equalsIgnoreCase(list[i])
                   && (namelen ==  len2
                  || sentence.separators.indexOf(name.charAt(len2))>=0)) {
//               database = sharkStartFrame.publicSentLib[0];
               database = sharkStartFrame.getPrimarySoundDb(sharkStartFrame.publicSentLib);              
               name1 = list[i];
               pos2 = len2;
           }
        }
      }
      if(database == null) {
        if(u.numbers.indexOf(name1.charAt(0)) >= 0) {
           name = name.substring(0,pos) + u.numberinwords(name1)
            + name.substring(pos+name1.length());
           namelen = (short)name.length();
           continue;
        }
      }
      while((j=name1.indexOf('[')) >= 0 || (j=name1.indexOf(']')) >= 0) {
         if (j+1<name1.length()) name1 = name1.substring(0,j)+name1.substring(j+1);
         else name1 = name1.substring(0,j);
      }


//     if(sharkStartFrame.currPlayTopic.fl)
//          database1="publicsay3";
      
      if(database1 != null  && db.query(database1,name1,db.WAV) >= 0) {
         database = database1;
      }
      else for(i = 0;i < sharkStartFrame.publicSoundLib.length;++i) {
         if(db.query(sharkStartFrame.publicSoundLib[i],name1,db.WAV) >= 0) {
            database = sharkStartFrame.publicSoundLib[i];
            break;
         }
      }
      if(database != null) {
         if(sw == null) {
            sw = new spokenWord(name1,database,false);
            if(pos2 == namelen && mark==null) {sw.decomp(); break;}
          }
          else {
             sw.database = database;
             sw.name =  name1;
          }
          sw.decomp2();
          if(mark != null) {
            byte newdata[] = new byte[sw.data.length+mark.length];
            System.arraycopy(mark,0,newdata,0,mark.length);
            System.arraycopy(sw.data,0,newdata,mark.length,sw.data.length);
            sw.data = newdata;
            mark = null;
          }
      }
      else if((i = (short)name1.indexOf('-')) >= 0) { // split hyphenated words
         name = name.substring(pos,pos+i) + " " + name.substring(pos+i+1);
         namelen = (short)name.length();
         pos = 0;
         continue;
      }
      pos = (short)(pos2+1);
     }
    }
    else if(names[k] instanceof word) {
      wd = (word)names[k];
      if(wd.spokenword == null) getSpokenWord(wd);
      if(wd.spokenword != null) {
        if(!wd.spokenword.decompressed) {
           if(wd.homophone) wd.spokenword.decomphomo();
           else          wd.spokenword.decomp();
        }
        if(wd.spokenword.data != null) {
         if(sw == null) {
            sw = new spokenWord("dummy",null,false);
            sw.decompressed=true;
         }
         if(sw.data == null)   sw.data = wd.spokenword.data;
         else {
            byte newdata[] = new byte[sw.data.length+wd.spokenword.data.length];
            System.arraycopy(sw.data,0,newdata,0,sw.data.length);
            System.arraycopy(wd.spokenword.data,0,newdata,sw.data.length,wd.spokenword.data.length);
            sw.data = newdata;
         }
        }
      }
    }
   }
   if(sw != null) {
      sw.name = name;
      sw.decompressed = true;
   }
   return sw;
}
 //------------------------------------------------------------------------
 public static spokenWord combine(spokenWord w[], int gap) {
   int len=0,i;
   byte[]  gapx = null;
   if(gap>0)
       gapx = noise.silence(gap);
   spokenWord sw = null;
   for(i=0;i<w.length;++i) if (w[i] != null) {
       if(sw==null) {
         sw = w[i];
       }
       else len += gapx.length;
       len +=w[i].data.length;
   }
   if(sw == null) return null;
   byte newdata[] = new byte[len];
   boolean first= true;
   for(i=len=0;i<w.length;++i) {
     if (w[i] != null) {
       if (gap > 0 && !first ) {
         System.arraycopy(gapx, 0, newdata, len, gapx.length);
         len += gapx.length;
       }
       System.arraycopy(w[i].data, 0, newdata, len, w[i].data.length);
       len += w[i].data.length;
       first = false;
     }
   }
   sw.data = newdata;
   return sw;
 }
//------------------------------------------------------------
public static boolean exists(String name) {
   String database = null;
   short i;
   if(sharkStartFrame.currStudent >= 0
       && sharkStartFrame.studentList[sharkStartFrame.currStudent].hassay
       && db.query(sharkStartFrame.studentList[sharkStartFrame.currStudent].name,name,db.WAV) >= 0)
                     return true;
   for(i = 0;i < sharkStartFrame.publicSoundLib.length;++i) {
      if(db.query(sharkStartFrame.publicSoundLib[i],name,db.WAV) >= 0) {
         return true;
      }
   }
   return false;
}
   //---------------------------------------------------------------
   public static void waitforsay() {
      long i;
      long t = System.currentTimeMillis();
      while((i=endsay - t)>0) {
         u.pause((int)i);
         t = System.currentTimeMillis();
      }
   }
   //---------------------------------------------------------------
   public static boolean isfree() {
     return(System.currentTimeMillis()>Math.max(endsay,endsay2));
   }
   //---------------------------------------------------------------
   public static abstract class whenfree {
      int i;
      long t;
      public abstract void action();
      public whenfree(int extratime) {
        Timer waitTimer;
        if(isfree() && extratime == 0) {
           endsay =System.currentTimeMillis() + 1000; // reserve it
           waitTimer = (new Timer(10,new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               endsay = System.currentTimeMillis();
               action();
           }
          }));
        }
        else {
          long diff = Math.max(endsay,endsay2) - System.currentTimeMillis();
          diff = Math.min(10000,Math.max(diff,10));
          waitTimer = (new Timer(extratime+(int)diff,new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               action();
            }
          }));
         }
         waitTimer.setRepeats(false);
         waitTimer.start();
      }
   }
}
