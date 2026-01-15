package shark;

/**
 * <p>Title: WordShark</p>
 * <p>Description: Creates the help panels</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: WhiteSpace</p>
 * @author Roger Burton
 * @version 1.0
 */
public class helppanel extends runMovers {
   sharkImage im;
   /**
    * Displays the necessary help by showing the image that is requested
    * @param newim String identifying which "help" image to show
    */
   void show(String newim) {
      if(im != null)
//startPR2005-10-16^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//        removeMover(im);
        mtot = 0;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      im=null;
      if(newim==null)
        return;
      if(runthread==null)
        start1();
      im = new sharkImage(newim, false);
      if(im != null) {
          im.w = mover.WIDTH;
          im.h = mover.HEIGHT;
          im.cansay = true;
          addMover(im,0,0);
          copyall=true;
      }
   }
   /**
    * Causes the help panel text to be spoken when mouse is clicked on it
    * @param x1 gives position of mouse x
    * @param y1 gives position of mouse y
    */
   public void mouseclick(int x1, int y1) {       //  overwrite
        if(im != null) im.saytext(mousexs,mouseys);
    }

}
