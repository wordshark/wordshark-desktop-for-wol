package shark;
import java.util.*;
   //---------------------------------------------------------
public class noise  {
public static noise beep,zapp,groan,squeek,plopplop,shock,pop,bang,fitshape;
static long lastgroan;

static final short USUALRATE = 22050; //8000;
static final short fade = 127;
short RATE = USUALRATE;
      public static final byte ADD = 1;
      public static final byte NOTE = 2;
      public static final byte GO = 4;
      public byte data[];
      static byte sincurva[];
      static boolean nogroan;
      static byte oldbeepvol,beepvol = 4;
      static byte maxvol,minvol;
      static int herzes[] = {1024,1024*16/15,1024*9/8,1024*6/5,1024*5/4,
                                  1024*4/3,1024*45/32,1024*3/2,
                                  1024*8/5,1024*5/3,1024*9/5,1024*15/8};
      static byte AGlist[] = {9,11,0,2,4,5,7};
      public noise() { }
    //------------------------------------------------------------------
   void noise(int from,int to, int tim, boolean musical, boolean playit)     // time in 1/1000 secs
   {
      if(to==0) to = from;
      int loops = RATE * tim / 1000, oldlen=0;
      double add1 = (double)from * 256 / RATE;
      double add2 = (double)to * 256 / RATE;
      double pos;
      double factor = Math.pow(add2/add1, ((double)1)/loops);
      int i,j,k;

      if(sincurva == null || beepvol != oldbeepvol) {
         oldbeepvol = beepvol;
         sincurva = new byte[256];
         for(i = 0; i < 256; ++i) {
            sincurva[i] = 0;
            sincurva[i] |= (int)Math.rint(Math.sin(i * Math.PI / 128) * (144-(12-beepvol)*(12-beepvol)) * 127 / 144 + 127);
         }
         minvol = maxvol = 0;
         minvol |= (127 - 127 * beepvol / 12);
         maxvol |= (127 + 127 * beepvol / 12);
      }
 //     if(beepvol == 0) return;
      if(tim != 0) {
        if(data == null) data = new byte[loops];
        else {
           oldlen = data.length;
           byte d2[] =  new byte[loops+oldlen];
           System.arraycopy(data,0,d2,0,oldlen);
           data = d2;
        }
        if(from == 0)  {      // silence
          Arrays.fill(data,oldlen,oldlen+loops,(byte)127);
        }
        else if(musical) {
          for(i=0,pos = 0; i<loops; ++i, pos+=add1, add1*=factor) {
               data[i+oldlen] = sincurva[((int)pos) & 0x00ff];
          }
        }
        else {
           for(i=0,pos = 0; i<loops; ++i, pos+=add1, add1*=factor) {
               data[i+oldlen] = ((((int)pos)& 0x00ff) > 128) ? maxvol:minvol;
           }
        }
      }
      if(playit && data != null) {
         for(i=data.length-1,j=0; j< fade; --i,++j) {
                  k = (int)data[i] & 0x00ff;
                  data[i] = 0;
                  data[i] |= (k-127)*j/fade + 127;
         }
         play();
      }
   }
   //------------------------------------------------------------
   public static void setnew() {
          beep=zapp=groan=squeek=plopplop=shock=pop=fitshape=null;
   }
   //----------------------------------------------------------
   void play() {
       spokenWord.say(data);
   }
    //---------------------------------------------------------------------
    // list is : octave '0'-'5' for 32/64/128/256/512/1024,  note('C','D' etc)
    //                 # or b, (nnn) duration in 1/1000sec, / play
public static void play(String list,boolean vary)
{
 int   i, hz=0, octave=3, duration=400, j=0;
 int factor = (vary)?(64+u.rand(128)):128;
 noise n = new noise();
 char ch;

while(j<list.length()) {
   switch(ch = list.charAt(j)) {
     case '#':   ++hz; break;
     case 'b':   --hz; break;
     case '(':
                 duration = u.getint(list.substring(j+1));
                 j += list.substring(j).indexOf(')');
                 break;
     case '/':
           i = (herzes[hz] * factor / 128) >> (5 - octave);
           n.noise(i,i,duration,true,false);
           break;
     default:
          if(ch >= 'A' && ch <='G')   hz = AGlist[ch - 'A'];
          else if(ch >= '0' && ch <='5')   octave = ch -'0';
    }
    ++j;
}
n.noise(0,0,0,true,true);
}
  //----------------------------------------------------------
    public static void beep() {
       if(beepvol==0) return;
       if (beep != null)  beep.play();
       else {
          beep = new noise();
          beep.noise(200 + u.rand((short)500),0,200,false,true);
       }
    }
   public static void zapp() {
      if(beepvol==0) return;
      if (zapp != null)  zapp.play();
      else
      (zapp=new noise()). noise(300 + u.rand((short)300),1200 + u.rand((short)400),200,false,true);
   }
   public static void pop() {
      if(beepvol==0) return;
      if (pop != null)  pop.play();
      else
     (pop=new noise()). noise(300 + u.rand((short)300),800 + u.rand((short)400),100,false,true);
   }
   public static void groan() {
      if(beepvol==0 || nogroan) return;
      if(System.currentTimeMillis() < lastgroan+500) {lastgroan = System.currentTimeMillis();return;}
      if (groan != null)  groan.play();
      else
      (groan=new noise()). noise(200 + u.rand((short)100),40+u.rand((short)40),500,false,true);
      lastgroan = System.currentTimeMillis();
   }
   public static byte[] turnover() {
        int p;
        noise n = new noise();
        byte savebeep = beepvol;
        if(beepvol!=0)  beepvol=1;
        for(short i=0;i<2;++i) {
           p = 300 + u.rand(100);
           n. noise(p,p+10,50+u.rand(50),false,false);
        }
        beepvol = savebeep;
        return n.data;
   }
   public static byte[] grind() {
        noise n = new noise();
        for(short i=0;i<5;++i) {

           n. noise(40 + u.rand((short)20),40+u.rand((short)20),100,false,false);
        }
        return n.data;
   }
   public static void fitshape() {
      if(beepvol==0) return;
      if (fitshape != null)  fitshape.play();
      else {
        int i = 400 + u.rand(300);
        noise fitshape = new noise();
        fitshape.noise(i,i*2,200,false,false);
        fitshape.noise(i*2,i,200,false,true);
      }
   }
   public static void verygood() {
      if(beepvol==0) return;
      play("3G(200)/4C(600)/D(200)/C/D/E/G/",true);
   }
   public static void coindrop() {
      if(beepvol==0) return;
      int i = 512 + u.rand(512);
      (new noise()). noise(i,i,100,false,true);
   }
   public static void chink() {
      if(beepvol==0) return;
      int i = 2000 + u.rand(400) ;
      (new noise()). noise(i,i,50,false,true);
   }
   public static void bang() {
      if(beepvol==0) return;
      if (bang != null)  bang.play();
      else (bang=new noise()). noise(400,3000,100,true,true);
   }
   public static void squash() {
      if(beepvol==0) return;
      (new noise()). noise(100+u.rand(50),100,200,false,true);
   }
   public static void squeek() {
      if(beepvol==0) return;
      if (squeek != null)  squeek.play();
      else {
         int i = 2500 + u.rand(500);
         noise squeek = new noise();
         squeek.noise(i,i-800,200,true,false);
         squeek.noise(i,i-800,200,false,true);
      }
   }
   public static void scream() {
      if(beepvol==0) return;
      int i = 500 + u.rand(200);
     (new noise()). noise(i,i-300,150,true,true);
   }
   public static void plop() {
      if(beepvol==0) return;
      (new noise()). noise(256,200,100,true,true);
   }
   public static void shock() {
      if(beepvol==0) return;
      if (shock != null)  shock.play();
      else
      (shock = new noise()). noise(600,2000,100,false,true);
   }
   public static void plopplop() {
      if(beepvol==0) return;
      if (plopplop != null)  plopplop.play();
      else {
       noise plopplop = new noise();
       plopplop.noise(256,200,100,true,false);
       plopplop.noise(0,0,600,true,false);
       plopplop.noise(256,200,100,true,true);
      }
   }
   public static byte[] marker() {
      noise n = new noise();
      n.RATE = (short)spokenWord.RATE;
      n.noise(0,0,300,true,false);
      n.noise(300+u.rand(50),0,400,true,false);
      n.noise(0,0,300,true,false);
      return n.data;
   }
//startPR2006-08-22^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   public static byte[] marker_onesound() {
      noise n = new noise();
      n.RATE = (short)spokenWord.RATE;
      n.noise(0,0,300,true,false);
      n.noise(300,0,400,true,false);
      n.noise(0,0,300,true,false);
      return n.data;
   }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   public static byte[] silence(int ti) {
      noise n = new noise();
      n.RATE = (short)spokenWord.RATE;
      n.noise(0,0,ti,false,false);
      return n.data;
   }
   public static byte[] marker2() {
      int i,j,k;
      noise n = new noise();
      n.RATE = (short)spokenWord.RATE;
      n.noise(300+u.rand(50),0,500,true,false);
      for(i=n.data.length-1,j=0; j< fade; --i,++j) {
                  k = (int)n.data[i] & 0x00ff;
                  n.data[i] = 0;
                  n.data[i] |= (k-127)*j/fade + 127;
      }
      return n.data;
   }
}

