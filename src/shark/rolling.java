package shark;

import java.awt.*;

public class rolling extends  sharkGame {//SS 5/12/03
   public String items[];
   int bwidth,bheight;
   ball currball;
   bucket buckets[];
   chute1 chute;
   boolean caps;
   short o[],curr,addscore;
   mmover mmess;
   mmover messes[] = new mmover[3];
   static final int gfactor = 100000;
   int maxitems;
   Color ballcolor = u.verybrightcolor(background);
   cwarning warning = new cwarning();
   boolean showwarning;
   int lastw1;
   boolean wantag,wanthn,wantot,wantuz, sayphonic;


   public rolling() {
    wantSprite=false;
    errors = true;
    gamescore1 = true;
    clickonrelease = true;
//    peeps = true;
//    listen= true;
//    peep = true;
    wantspeed = true;
    optionlist = new String[] {"sort-capitals","sort-ag","sort-hn","sort-ot","sort-uz","sort-sayphonic"};
    rgame.options |= word.CENTRE;
    buildTopPanel();

    messes[0] = new mmover(rgame.getParm("bullseye"),mover.WIDTH*2/3,mover.HEIGHT/8,Color.red);
    messes[1] = new mmover(rgame.getParm("close"),mover.WIDTH*2/3,mover.HEIGHT/8,Color.blue);
    messes[2] = new mmover(rgame.getParm("notbad"),mover.WIDTH*2/3,mover.HEIGHT/8,Color.gray);
    help("help_rolling");
    setup1();
 }
 //------------------------------------------------------------------
 void setup1() {
    int i;
    String itemlist = rgame.getParm("list"),itemlist2=null;
    caps = options.option(optionlist[0]);
    wantag = options.option("sort-ag");
    wanthn = options.option("sort-hn");
    wantot = options.option("sort-ot");
    wantuz = options.option("sort-uz");
    sayphonic = options.option("sort-sayphonic");
    markoption();
    curr=0;
    if(caps) itemlist = itemlist.toUpperCase();
    items = new String[itemlist.length()];
    for(i=0;i<itemlist.length();++i) {
       items[i] = itemlist.substring(i,i+1);
    }
    u.sort(items);
    bwidth = mover.WIDTH/items.length;
    bheight = bwidth*screenwidth/screenheight;
//    wordfont = sizeFont(items,bwidth*screenwidth/mover.WIDTH,bwidth*screenwidth/mover.WIDTH * items.length);
//    metrics = getFontMetrics(wordfont);
    chute = new chute1();
    buildbuckets();
    o = u.shuffle(u.select((short)items.length,(short)items.length));
    if((!wantag ||!wanthn ||!wantot ||!wantuz) && (wantag || wanthn || wantot || wantuz)) {
      String s = (wantag?u.gettext("sort-ag","letters"):"")
          + (wanthn?u.gettext("sort-hn","letters"):"")
          + (wantot?u.gettext("sort-ot","letters"):"")
          + (wantuz?u.gettext("sort-uz","letters"):"");
       if(caps) s = s.toUpperCase();
       for (i = 0; i < buckets.length; ++i) {
          if (s.indexOf(items[i].charAt(0)) < 0)    buckets[i].ignore = true;
      }
    }
    nextball();
  }
 //--------------------------------------------------------------
 public void restart() {
    super.restart();
    setup1();
 }
 //--------------------------------------------------------------
  void buildbuckets() {
     buckets = new bucket[items.length];
     for(short i=0; i<items.length; ++i) {
        buckets[i] = new bucket(i);
     }
  }
  //----------------------------------------------------------------
  void nextball() {
//startPR2005-04-18^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    if(Demo_base.isDemo){
      if (Demo_base.demoIsReadyForExit(1)) return;
    }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    while(curr < o.length && buckets[o[curr]].ignore) ++curr;

    if(curr >= o.length) {
        removeMover(chute);
        String message;
        int av;
        score(av = gametot1/items.length);
        if(av>8)  message = rgame.getParm("wonderful") ;
        else if(av >= 5)   message = rgame.getParm("good");
        else   message = rgame.getParm("end");
        if(mmess != null) {
           removeMover(mmess);
           mmess = null;
        }
        showmessage(u.edit(message,String.valueOf(gametot1)),
           mover.WIDTH/3,mover.HEIGHT/4, mover.WIDTH*2/3,mover.HEIGHT/2);
        exitbutton(mover.HEIGHT/2);
     }
     else {
       if(delayedflip && !completed){
           flip();
       }
        for(short i=0;i<buckets.length;++i) {
           if(buckets[i].showvalue) {
              buckets[i].showvalue = false;
             if(buckets[i].filled) buckets[i].hasball.ended = false;
           }
        }
        currball  = new ball(o[curr]);
        if(sayphonic) spokenWord.sayPhonic(items[o[curr]]);
        ++curr;
     }
     gamePanel.clearall();
  }
  public boolean click(int mx,int my) {
     if(gamePanel.mousey < mover.HEIGHT*7/8 && currball != null && currball.y == 0 && !currball.falling) {
        if(mmess != null) {
           removeMover(mmess);
           mmess = null;
        }
        short bno = currball.ballno;
        currball.falling = currball.inchute = true;
        for(short i=(short)Math.max(0,bno-2);i<Math.min(buckets.length,bno+3);++i)
                          buckets[i].show((short)Math.abs(i-bno));
     }
     return true;
  }
  class ball extends mover {
     short ballno,val;
     int dy,dx,bouncedy;
     boolean falling, inchute,bouncing;
     long lasttime;
     ball(short bno) {
        super(true);
        ballno = bno;
        w = bwidth;
        h = bwidth*screenwidth/screenheight;
        addMover(this, mover.WIDTH/2 - bwidth/2, 0);
        keepMoving=true;
        lasttime = gtime();
     }
     public void changeImage(long time) {
        w = bwidth;
        h = bheight = bwidth*screenwidth/screenheight;
        if(falling) {
           int cw = chute.chutewidth*mover.WIDTH/screenwidth;
           int ch = chute.chuteheight* mover.HEIGHT /screenheight;
           int g  =  (3+speed) * mover.HEIGHT/30;
           long t1 = Math.min(500,time-lasttime);
           int newvel;
           if(inchute) {
              int g2  = (int)(g * Math.sin(chute.angle));
              newvel = (int)(dy + g2 * t1) ;
              int dist = (int)((newvel+dy) * t1 / 2 /gfactor);
              if(y + dist + bheight/2 > ch) {
                 t1 = (long)(((-dy) + Math.sqrt((double)dy*dy + (double)2 * g2 *(ch-y-bheight/2)*gfactor))/g2);
                 dy = (int)(dy + t1 * g2);
                 y = ch - bheight/2;
                 x = mover.WIDTH/2 - w/2 - cw;
                 dx = (int)(dy /Math.tan(chute.angle)*screenheight/screenwidth);
                 t1 = (time-lasttime-t1);
            //     if(t1>0) {
           //         newvel = (int)(dy + g * t1);
           //         y += (dy+newvel)*t1/2/gfactor;
           //         x += (int)(dx*t1/gfactor);
            //     }
                 inchute = false;
              }
              else {
                  
                 y += dist;
                 dy = newvel;
                 x = mover.WIDTH/2 - w/2
                       + (int)((y+bheight/2)/Math.tan(chute.angle)*screenheight/screenwidth);
                  
              }
           }
           else {
               newvel = (int)(dy + g * t1);
               int oldy=y;
               y += (dy+newvel)*t1/2/gfactor;

//               if(y > mover.HEIGHT - bheight*2 && !bouncing) // ---rb  2005/07/10 -------------------------------
  //                  x += (int)(dx*t1*(mover.HEIGHT-bheight*2-oldy)/(y-oldy)/gfactor);
    //            else 
                   x += dx*t1/gfactor;
                dy = newvel;
           }
/*
           if(x < 0) {
              x = -x;
              dx = -dx;
           }
           if(x > (buckets.length-1)*bwidth) {
              x = (buckets.length-1)*bwidth * 2 - x;
              dx = -dx;
           }

           if(bouncing &&  ( dx > 0 && x > bwidth*ballno - bwidth/2
                         || dx < 0 && x < bwidth*ballno + bwidth/2)) {
              stopmoving();
           }
           else if(y > mover.HEIGHT - bheight*2) {
              if(!bouncing) {
                int diff = Math.abs((x+bwidth/2)/bwidth - ballno);
                if(diff==0) {mmess = messes[0]; addscore = 10; }
                else if(diff==1) {mmess = messes[1];addscore = 5;}
                else if(diff==2) {mmess =  messes[2];addscore = 1;}
                else addscore = 0;
                if(diff>items.length/3) {
                    noise.groan();
                    error();
                }
              }
              if (bouncing && (dx<0 && x < bwidth*ballno || dx>0 && x > bwidth*ballno)) stopmoving();   // ---rb  2005/07/10 -------------------------------
              else if(Math.abs(bwidth*ballno - x) >  bwidth/3) {
                if(!bouncing) {
                   bouncing = true;
                   y =  mover.HEIGHT - bheight*2;
                   bouncedy = dy = -dy/8;
                   if(x <ballno*bwidth) {
                     dx = mover.WIDTH*10;
                   }
                   else dx = - mover.WIDTH*10;
                }
                else {
                   y =  mover.HEIGHT - bheight*2;
                   dy = bouncedy;
                }
              }
              else stopmoving();
           }
           */
        }
        else if(y==0) {     // at top of chute
            
           x = mover.WIDTH/2 - w/2
              + (int)((bwidth/2)/Math.tan(chute.angle));

        }
        else y = mover.HEIGHT - bheight;
        lasttime = time;
        tox=x;toy=y;
     }
     void stopmoving() {
        falling = false;
        x = ballno*bwidth;
        y = mover.HEIGHT - bheight;
        if(mmess != null) {
           addMover(mmess, mover.WIDTH/6,mover.HEIGHT*2/3);
        }
        if(addscore != 0) gamescore(addscore);
        addscore = 0;
        keepMoving = false;
        ended = false;
        nextball();
        buckets[ballno].filled = true;
        buckets[ballno].hasball = this;
     }
     public void paint(Graphics g,int x1, int y1, int w1, int h1) {
        int hwidth = bwidth*screenheight/mover.HEIGHT;
        if(w1 != lastw1) {
          wordfont = sizeFont(items,bwidth*screenwidth/mover.WIDTH,bwidth*screenwidth/mover.WIDTH * items.length);
          metrics = getFontMetrics(wordfont);
          lastw1 = w1;
          gamePanel.copyall = true;
        }
        g.setColor(ballcolor);
        ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        g.fillOval(x1,y1,w1,h1);
       ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);
        g.setColor(Color.black);
        g.setFont(wordfont);
        g.drawString(items[ballno], x1+w1/2-metrics.stringWidth(items[ballno])/2,
                          y1+h1/2-metrics.getHeight()/2 + metrics.getAscent());
     }
  }
  class bucket extends mover {
     short buckno,val;
     boolean showvalue,filled,ignore;
     ball hasball;
     bucket(short bno) {
        super(false);
        buckno = bno;
        h = bheight *2;
        w = bwidth;
        addMover(this,bwidth*buckno,mover.HEIGHT-bheight*2);
     }
     public void paint(Graphics g,int x1, int y1, int w1, int h1) {
        int ww = bwidth*(buckno+1)*screenwidth/mover.WIDTH - x1;
        int hh = ww;
        int y2 = screenheight-hh-1;
        g.setColor(Color.black);
        ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        if(ignore) g.fillOval(x1,y2,ww,hh);
          else   g.drawArc(x1,y2,ww,hh,180,180);
        ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);
        g.drawLine(x1,y2,x1,screenheight-(hh+1)/2);
        if(!ignore) {
          g.setFont(wordfont);
          if (showvalue && !filled && metrics != null) {
            g.drawString(items[buckno],
                         x1 + ww / 2 - metrics.stringWidth(items[buckno]) / 2,
                         y2 + hh / 2 - metrics.getHeight() / 2 +
                         metrics.getAscent() / 2);
          }
          if (showvalue && val > 0 && metrics != null) {
            String s = String.valueOf(val);
            g.setColor(white());
            g.drawString(s, x1 + ww / 2 - metrics.stringWidth(s) / 2,
                         y2 - hh / 2 - metrics.getHeight() / 2 +
                         metrics.getAscent());
          }
        }
     }
     void show(short diff) {
           if(diff == 0) val = 10;
           else if(diff == 1) val = 5;
           else if(diff ==2) val = 1;
           else val = 0;
           showvalue = true;
           if(filled) hasball.ended = false;
           ended = false;
           if(buckno < buckets.length-1) {
             buckets[buckno + 1].ended = false;
             if(buckets[buckno + 1].filled)
               buckets[buckno + 1].hasball.ended = false;
           }
     }
  }
  class chute1 extends  mover {
      int lenx = mover.HEIGHT / 4;
      double angle;
      int chuteheight;
      int chutewidth ;
      int chutediffx,chutediffy;
      int len;
      int hwidth;
      chute1() {
         super(false);
         w = lenx * 2 + bwidth*2;
         h = lenx * screenwidth/screenheight;
         addMover(this,mover.WIDTH/2 - w/2,0);
         keepMoving = true;
      }
      public void changeImage(long time) {
         mx = gamePanel.mousexs-screenwidth/2;
         my = Math.max(1,gamePanel.mouseys);
//         gamePanel.showSprite = (my <screenheight/8);
         if(my > screenheight*7/8 || gamePanel.mouseOutside) {
            if(!showwarning) {addMover(warning,0,0);showwarning=true;}
            return;
         }
         if(showwarning) {
            removeMover(warning);
            showwarning=false;
         }
         angle = Math.max(Math.PI/8,
                        Math.min(Math.PI*7/8,Math.atan2(my,mx)));
         len = (lenx+(int)(bwidth/2/Math.sin(angle))) * screenheight/mover.HEIGHT;
         hwidth = (int)Math.abs((double)bwidth*screenwidth/mover.WIDTH / Math.sin(angle)) + 1;
         chuteheight = (int)(len * Math.sin(angle));
         chutewidth = - (int)(len * Math.cos(angle));
         
         

         chutediffy = Math.abs((int)(bwidth*screenwidth/mover.WIDTH*Math.cos(angle)/2));
         chutediffx = (int)(bwidth*screenwidth/mover.WIDTH*Math.sin(angle)/2);

      }
      public void paint(Graphics g,int x1, int y1, int w1, int h1) {
         g.setColor(Color.black);
         ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
         if(angle>Math.PI/2) {
            g.drawLine(screenwidth/2 - hwidth/2-1, 0,
                    screenwidth/2-chutewidth-chutediffx-1, chuteheight-chutediffy);
            g.drawLine(screenwidth/2 + hwidth/2, 0,
                    screenwidth/2 -chutewidth+chutediffx, chuteheight+chutediffy);
         }
         else {
            g.drawLine(screenwidth/2 - hwidth/2-1, 0,
                    screenwidth/2-chutewidth-chutediffx-1, chuteheight+chutediffy);
            g.drawLine(screenwidth/2 + hwidth/2, 0,
                    screenwidth/2 - chutewidth+chutediffx, chuteheight-chutediffy);
         }
         ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);
     }
  }
  class cwarning extends mover {
//startPR2007-11-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      Font f  = new Font(sharkStartFrame.wordfont.getName(),sharkStartFrame.wordfont.getStyle(),16);
    Font f  =  sharkStartFrame.wordfont.deriveFont((float)16);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      FontMetrics m = getFontMetrics(f);
      String text = rgame.getParm("keeppointer");
      cwarning() {
        super(false);
        w = mover.WIDTH;
        h = mover.HEIGHT*7/8;
      }
      public void paint(Graphics g,int x1, int y1, int w1, int h1) {
         g.setColor(Color.red);
         g.drawRect(x1+2,y1,w1-5,h1-1);
         g.drawRect(x1+3,y1+1,w1-7,h1-3);
         g.drawRect(x1+4,y1+2,w1-9,h1-5);
         g.setFont(f);
         g.drawString(text,x1+w1/2-m.stringWidth(text)/2,
                           y1+h1-4 - m.getHeight() + m.getAscent());
      }
  }
}
