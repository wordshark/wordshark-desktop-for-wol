/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package shark;

import java.io.*;
import java.awt.*;
/**
 *
 * @author Paul Rubie
 */
  public class namesquare_base extends mover {
    public boolean editable;
    public String text;
    int textw;
    int pointerx = 0;
    int timeron = 0;
    Image image;
    float fl;
    Font dofont;
    FontMetrics fm;
    public int noneditablew;
    int ih;
    int iw;
    int iix = -1;
    int iiy = -1;
    int iiw = -1;
    int iih = -1;
    public int mode;
    static final int MODE_SIGNON = 0;
    static final int MODE_CHOOSE = 1;
//    static final int MODE_SHARETOCHOOSE = 2;
//    static final int MODE_SHARECHOSEN = 3;
//    static final int MODE_SHARERESET = 4;
    static final int MODE_GAMESSCREEN = 2;
    int lastw = -1;
    int lasty = -1;
    int margins;
    int sqmargins;
    int picspace;
    int left;
    int sq;
    Font tempf2;
    FontMetrics fm2;
    String lasttext = "";
    int corners;
    runMovers rm;
    namesquare_base partnersq;
    boolean activepartner;

     namesquare_base(String s, runMovers rmovers, boolean edit, namesquare_base secondedit, boolean partneractive, float alpha, int type, int arccorners, int wid, int hei) {
        super(false);
        mode = type;
        fl = alpha;
        text = s;
        editable = edit;
        partnersq = secondedit;
        activepartner = partneractive;
        rm = rmovers;
        w = noneditablew = wid;
        h = hei;
        corners = arccorners;
        if(mode==MODE_SIGNON){
            w = w*12/10;
            h = h*14/10;
        }
//        else if (mode==MODE_SHARETOCHOOSE){
//            w = w*6/10;
//            h = h*7/10;
//        }
        int edith;
        if(mode==MODE_SIGNON)edith = h*13/20;
        else if (mode==MODE_CHOOSE)edith = h/2;
//        else if (mode==MODE_SHARETOCHOOSE)edith = h/2;
        else if (mode==MODE_GAMESSCREEN)edith = h*8/12;
        else edith = h/2;
        dofont = rm.getFont().deriveFont((float) 80);
        fm = rm.getFontMetrics(dofont);
        while (fm.getHeight() > edith * rm.screenheight / mover.HEIGHT) {
            dofont = dofont.deriveFont((float) (dofont.getSize() - 1));
            fm = rm.getFontMetrics(dofont);
        }
        if(!editable && partnersq == null){
            byte buf[];
            MediaTracker tracker=new MediaTracker(rm);
            if (sharkStartFrame.allowStuImportPics_Icon &&  (buf = (byte[]) db.find(s, PickPicture.ownpicstuset, db.PICTURE)) != null) {
               image = sharkStartFrame.t.createImage(buf);
               tracker.addImage(image,1);
            }
            else if ( (buf = (byte[]) db.find(s, PickPicture.ownpic, db.PICTURE)) != null) {
               image = sharkStartFrame.t.createImage(buf);
               tracker.addImage(image,1);
            }
            else{
                String imtext;
                if(u.findString(student.adminlist, text)>=0 || u.findString(student.teacherlist, text)>=0)
                    imtext = "signonAdminLight_il96.png";
                else 
                    imtext = "signonStudLight_il96.png";
                image = sharkStartFrame.t.createImage(sharkStartFrame.publicPathplus + "sprites" +
                                File.separator + imtext);
                tracker.addImage(image,1);
            }
            try
            {
                tracker.waitForAll();
            }
            catch (InterruptedException ie)
            {
            }
            ih = image.getHeight(null);
            iw = image.getWidth(null);
        }
        if(editable || (partnersq != null && activepartner)) {
            input2 = this;
        }
        else handcursor = true;

     }

     public void paint(Graphics g,int x1, int y1, int w1, int h1) {
        boolean redo = lastw != w1 || lasty != y1;
        lastw = w1;
        lasty = y1;

        if(redo  || margins<0){
            redo = true;
            margins = w1/30;
            sqmargins = w1/50;
            picspace = sq = h1 - (sqmargins*2);
            if(!editable)
                left = x1+(margins*2)+picspace;
            else left = x1+margins;

  //          left = x1+margins+picspace;

            if(pointerx<0)pointerx = left;
            textw = (x1+w1-margins) - left;
        }
        Graphics2D g2d = ((Graphics2D)g);
//        if(mode==MODE_SHARETOCHOOSE || mode==MODE_SHARECHOSEN || mode==MODE_SHARERESET){
//            if(mode==MODE_SHARECHOSEN){
//                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
//            }
//            else if(mouseOver && (fl!=0.9f))
//                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.9f));
 //           else
//                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
//        }

        if(fl!=1.0f)
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, fl));
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setColor(Color.white);
        g.fillRoundRect(x1, y1, w1, h1, corners, corners);
        if(fl!=1.0f)
           g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));

        if(image!=null){
            if(redo){
                sq = h1 - (sqmargins*2);
                if(ih>iw){
                    iih = sq;
                    iiw = sq*iw/ih;
                    iix = x1 + margins + ((sq-iiw)/2);
                }
                else{
                    iiw = sq;
                    iix = x1 + margins;
                    iih = sq*ih/iw;
                }
                if(ih>iw){
                    iiy = y1 + sqmargins;
                }
                else{
                    iiy = y1 + sqmargins + ((sq-iih)/2);
                }
            }
            g2d.drawImage(image, iix, iiy, iiw, iih, null);
        }
        g.setColor(Color.black);
        if(mode==MODE_GAMESSCREEN)
            g.setColor(Color.darkGray);
        if(!text.equals(lasttext) || text.equals("")){
            tempf2 = dofont;
            fm2 = fm;
            while(fm2.stringWidth(text)>textw){
                tempf2 = tempf2.deriveFont((float)(tempf2.getSize()-1));
                fm2 = rm.getFontMetrics(tempf2);
            }
        }
        g.setFont(tempf2);
        g.drawString(text, left, y1+(h1/2) + (g.getFontMetrics().getAscent()/3));
        if(editable && (partnersq == null || activepartner)   && fm2!=null && text!=null){
            if(timeron == 0){
                int px;
                if(pointerx<1)px = left;
                else px= left+fm2.stringWidth(text.substring(0, pointerx));
                g.drawLine(px, y1+(h1/4), px, y1+h1-(h1/4));
            }
        }
//        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);
        g.setFont(dofont);
        lasttext = text;
     }
  }
