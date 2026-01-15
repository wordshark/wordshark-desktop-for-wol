package shark;
public class csay_base {
  static {
    System.loadLibrary(/*sharkStartFrame.dllpath+*/"jsay");
  }
  public String path;
  public csay_base() {
  }
  public void word(String name) {
     String ss =  new String(path+"\\"+name+".wav");
     byte bb[] =  ss.getBytes();
     byte b = bb[0];
     byte b1 = bb[1];
     byte b2 = bb[2];
     wsay(bb);
  }
  public static native void wsay(byte[] nn);
  public static native void wstart(byte[] nn);
  public static native void wend();
}
