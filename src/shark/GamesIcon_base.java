/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package shark;

import java.awt.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;

/**
 *
 * @author White Space
 */
public class GamesIcon_base {
    sharkImage image;
    String gname[];
    Color bgcolor;
    Color bgcolorinit;
    int outsideborder = 2;
    int round =  10;
    runMovers runm;
    Rectangle r;
    Color colstripe;
    Color oricolstripe;
    int youterring;
    int yinnerring;
    int ytoinnerring;
    int ystripe;
    int winner;
    int hinner;
    int xinner;
    int xtoinner;
    int gcol = 130;
    Color txtgradientcolor = new Color(gcol,gcol,gcol);
    float txtgradientalpha = 0.4f;
    boolean demomode;
    boolean enabled = true;
    boolean isclickable;
    String text;
    boolean disableable = true;
    static Image im1 = Toolkit.getDefaultToolkit().getImage(sharkStartFrame.publicPathplus + "sprites"
                        + sharkStartFrame.separator
                        + "infoCIRCLELight_il48.png");      

    sharkImage infoim;// = new sharkImage(u.im1, "infolabel");
//    static Font tempf2=  null;



    public GamesIcon_base(runMovers rm, Rectangle rec, sharkImage sharkim, String text1, Color col,
            Color stripe, boolean clickable, boolean isdemo, boolean isdisableable) {
        image = sharkim;
        colstripe = oricolstripe = stripe;
        bgcolor = bgcolorinit = col;
        r = rec;
        runm = rm;
        text = text1;
        demomode = isdemo;
        isclickable = clickable;
        disableable = isdisableable;
        init();
    }

    public GamesIcon_base(runMovers rm, Rectangle rec, sharkImage sharkim, String text1, Color col,
            Color stripe, boolean clickable, boolean isdemo) {
        image = sharkim;
        colstripe = oricolstripe = stripe;
        bgcolor = bgcolorinit = col;
        r = rec;
        runm = rm;
        text = text1;
        demomode = isdemo;
        isclickable = clickable;
        init();
    }

    void init(){
        if(disableable && !shark.numbershark){
            sharkStartFrame.gameflag ff = sharkStartFrame.gameflag.get(text);
            if(ff.owllackpics || ff.owllackrecs || ff.owllackextrarecs) enabled = false;
        }
        text = u.halveText(text, 10);
        gname = u.splitString(text);
        GamesIconBase gib = new GamesIconBase();
        if(image!=null){
            image.isGamesIconPart = true;
            if(!demomode)
                image.handcursor = gib.handcursor = isclickable;
        }
        gib.gameicon = true;
        gib.isGamesIconPart = true;
        if(!enabled) {
            demomode = true;
                if(infoim==null){   

           //         im1 = im1.getScaledInstance((hinner/2)*mover.WIDTH/runm.screenwidth,
           //             (hinner/2)*mover.WIDTH/runm.screenwidth, Image.SCALE_SMOOTH);
                    infoim = new sharkImage(im1, "infolabel");
                infoim.isGamesIconPart = true;
                if(!demomode)
                    infoim.handcursor = gib.handcursor = isclickable;
                }
        }
        runm.addMover(gib, r.x, r.y);      
    }

    public void changecolour(Color col){
        txtgradientcolor = col;
        colstripe = col;
    }

    public void defaultcolor(){
        txtgradientcolor = new Color(gcol,gcol,gcol);
        colstripe = oricolstripe;
    }
    class GamesIconBase extends mover {
        int gborder = -1;
        int gx1 = -1;
        int gy1 = -1;
        int gw1 = -1;
        int gh1 = -1;
        int gsq = -1;
        int border2 = -1;
        Color gradientcolor = Color.lightGray;
        float gradientalpha = 0.7f;
        int gradientheight;
        int gx2 = -1;
        int gy2 = -1;
        int bandh;
        int th;
        int th2;
        Point p[];
        boolean setup = true;
        Font tempf2;         
        int mh_infoim;
        int xxinner_infoim;
        int wwinner_infoim;
        int yyinner_infoim;
        
        public GamesIconBase() {
            w = (int)r.getWidth();
            h = (int)r.getHeight();

        }

        public void paint(Graphics g,int x, int y, int w, int h) {
   //         if(true){
  //              g.setColor(Color.black);
   //           g.fillRect(x, y, w, h);
   //         }
            

            boolean redo = false;
            if(w>h){
                if(gsq != h)redo = true;
            }
            else{
                if(gsq != w)redo = true;
            }
            if(redo){
                if(w>h){
                    gsq = h;
                    gborder = gsq/30;
                    gx1 = x+((w-gsq)/2)+gborder;
                    gx2 = x+((w-h)/2);
                    xtoinner = ((w-gsq)/2)+gborder;
                    gy1 = y+gborder;
                    gy2 = y;
                    ytoinnerring = gborder;
                }
                else{
                    gsq = w;
                    gborder = gsq/30;
                    gx1 = x+gborder;
                    gx2 = x;
                    xtoinner = gborder;
                    gy1 = y+((h-gsq)/2)+gborder;
                    gy2 = y+((h-w)/2);
                    ytoinnerring = ((h-gsq)/2)+gborder;
                }
                gw1 = gsq-(gborder*2);
                gh1 = gsq-(gborder*2);
                border2 = gborder*4/10;
                youterring = (gy1-border2);
                yinnerring = gy1;
                winner = gw1;
                xinner = gx1;
                hinner = gh1;
                
                // prepare game name text
                th = (hinner*13/36);
                bandh = th/8;
                gradientheight = yinnerring+(hinner*16/20);
                th2 = th-bandh;
                tempf2 = sharkStartFrame.treefont.deriveFont((float)40);
                FontMetrics fm2 = runm.getFontMetrics(tempf2);
                String longstr;
                if(gname.length>1){
                    longstr = gname[0].length()>gname[1].length()?gname[0]:gname[1];
                }
                else{
                    longstr = gname[0];
                }
                int textw = (int)(winner*0.98);
                while(fm2.getAscent() > th2/2 || fm2.stringWidth(longstr)>textw){
                    tempf2 = tempf2.deriveFont((float)(tempf2.getSize()-1));
                    fm2 = runm.getFontMetrics(tempf2);
                }
                p = new Point[gname.length];
                for(int i = 0; i < gname.length; i++){
                    FontMetrics fmet = runm.getFontMetrics(tempf2);
                    int yy = yinnerring+(hinner-th2)+(th2/2)-fmet.getDescent()+((fmet.getDescent()+fmet.getAscent())/2);
                    if(gname.length>1){
                        if(i==0) yy = yy-(th2/4);
                        else yy = yy+(th2/4);
                    }
                    p[i] = new Point(xinner+((winner-fmet.stringWidth(gname[i]))/2), yy);
                }
                if(infoim!=null){   
                   infoim.w = (hinner/2)*mover.WIDTH/runm.screenwidth;
                    infoim.h = (hinner/2)*mover.HEIGHT/runm.screenheight;
 //                  infoim.adjustSize(runm.screenwidth,runm.screenheight);
                }


                 
                
                
            }
            Graphics2D g2d = ((Graphics2D)g);
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
           if(!demomode)  g.setColor(Color.white);
           else g.setColor(bgcolor);
           g2d.fillRoundRect(gx2, gy2, gsq, gsq,round,round);
            g.setColor(bgcolor);
            if(!demomode) g2d.fillRoundRect(xinner, yinnerring,winner, hinner,round,round);

            if(!demomode){
            RoundRectangle2D innerRoundRectangle = new RoundRectangle2D.Float(xinner, yinnerring,winner, hinner,round,round);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
         //   g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);
            gradientheight = yinnerring+(hinner*7/10);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, gradientalpha));
            GradientPaint gp = new GradientPaint(xinner+(winner/10), yinnerring+(hinner/10),
                Color.white, xinner+(winner/10), gradientheight, gradientcolor, true);
            g2d.setPaint(gp);
            g2d.fill(innerRoundRectangle);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));

  }
            // color behind text
            RoundRectangle2D roundRectangle = new RoundRectangle2D.Float(xinner,yinnerring+(hinner-th), winner, th, round, round);
            g2d.setColor(demomode? u.lighter(txtgradientcolor, 1.3f):txtgradientcolor);
            g2d.fill(roundRectangle);
            g2d.fillRect(xinner,yinnerring+(hinner-th), winner, th/2);

            //draw stripe
  //          if(!demomode){
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
            g2d.setColor(colstripe);
            g2d.fillRect(xinner, yinnerring+(hinner-th), winner, bandh);
            

            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);



            // draw game name text
            g2d.setColor(Color.white);
            if(p!=null){
                for(int i = 0; i < gname.length; i++){
                    if(tempf2!=null)
                        g2d.setFont(tempf2);
                    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
                    g2d.drawString(gname[i], p[i].x, p[i].y);
                }
            }
            
            
          
           if(!enabled){
               if(infoim!=null){
            
                wwinner_infoim = (winner*mover.WIDTH/runm.screenwidth);
                xxinner_infoim = xinner*mover.WIDTH/runm.screenwidth;
                yyinner_infoim = yinnerring*mover.HEIGHT/runm.screenheight;
                mh_infoim = (hinner*mover.HEIGHT/runm.screenheight-th*mover.HEIGHT/runm.screenheight);
                   int iix = xxinner_infoim+((wwinner_infoim-infoim.w)/2);
                   int iiy = yyinner_infoim + ((mh_infoim-infoim.h)/2);
                  if(setup){
                    infoim.gameicon = true;
                    runm.addMover(infoim, iix , iiy);
                    setup = false;
                  }
                  else{
                    infoim.moveto(iix, iiy, 0);
                  }
               } 
           }             
            
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);

            
            // add sharkImage
            if(!demomode){
                int nytoinnerring = ytoinnerring*mover.HEIGHT/runm.screenheight;
                int nxtoinner = xtoinner*mover.WIDTH/runm.screenwidth;
                int ix1 = r.x + nxtoinner;
                int iy1 = r.y + nytoinnerring;
                int hhinner = (hinner-th)*mover.HEIGHT/runm.screenheight;
                int wwinner = winner*mover.WIDTH/runm.screenwidth;



                if(image!=null){
     //               image.mustSave = false;
     //               image.dontclear = true;
                    image.h = hhinner;//*3/9);
                    image.w = wwinner;
                    if(setup){
                        image.gameicon = true;
                        runm.addMover(image, ix1 , iy1);
                        runm.bringtotop(image);
                        setup = false;
                    }
                    if(image.x!=ix1 || image.y!=iy1){
                        image.moveto(ix1, iy1, 0);
                    }
                }
            }      
        }
    }

}
