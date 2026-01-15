package shark;

import java.awt.*;

public class catching extends  sharkGame {//SS 4/12/03
   public String items[];
   int bwidth,bheight;
   boolean darkballs;
   short o[],curr,addscore;
   static final int gfactor = 100000;
   int lastmousex=-1;
   ball droppedball[],droppingball;
   short droppedtot;
   boolean caps,singleletterphonic;
   boolean wantag,wanthn,wantot,wantuz, sayphonic;
   int lastw1;

   public catching() {
    errors = false;
    gamescore1 = true;
//    peeps = true;
//    listen= true;
//    peep = true;
    wantspeed = true;
    forceSharedColor = true;
    optionlist = new String[] {"catchingsort-alpha","sort-capitals","sort-ag","sort-hn","sort-ot","sort-uz","sort-sayphonic"};
    rgame.options |= word.CENTRE;
    darkballs = (u.rand(2) != 0);
    gamePanel.setBackground(darkballs?u.brightcolor():u.darkcolor());
    gamePanel.showSprite = false;
    gamePanel.winsprite = true;
    buildTopPanel();
    help("help_catching");
    setup1();
   }
   //------------------------------------------------------------------
   void setup1() {
    short type, i,j;
    if(phonics && !phonicsw && singleword())    type = 1;
    else type = options.optionval(optionlist[0]);
//startPR2008-08-05^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    // get rid of the "this word list" option if the words are too long
    if(word.maxlen(rgame.w)>6){
      String s[] =  u.splitString(u.gettext(optionlist[0],"label"));
      if(type == 3){
        type = (short) Integer.parseInt(options.defaultstring(optionlist[0], runningGame.currGameRunner.gamename));
        unwantedOptions = new removeoption[]{new removeoption(optionlist[0], s[2], type)};
      }
      else{
        unwantedOptions = new removeoption[]{new removeoption(optionlist[0], s[2])};
      }
    }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    caps = options.option("sort-capitals");
//startPR2008-08-05^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    if(type <0) type = 1;
    if(type <=0) type = 1;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    wantag = options.option("sort-ag");
    wanthn = options.option("sort-hn");
    wantot = options.option("sort-ot");
    wantuz = options.option("sort-uz");
    sayphonic = options.option("sort-sayphonic");
    markoption();
    if(type == 1) {                  // single letter
       String itemlist = rgame.getParm("list1");
       if((!wantag ||!wanthn ||!wantot ||!wantuz) && (wantag || wanthn || wantot || wantuz)) {
         itemlist = (wantag?u.gettext("sort-ag","letters"):"")
             + (wanthn?u.gettext("sort-hn","letters"):"")
             + (wantot?u.gettext("sort-ot","letters"):"")
             + (wantuz?u.gettext("sort-uz","letters"):"");
       }
       items = new String[itemlist.length()];
       for(i=0;i<itemlist.length();++i)
           items[i] = itemlist.substring(i,i+1);
       if(phonics) singleletterphonic = true;
    }
    else if(type == 2) {                  // pairs of letters
       int len2 = -2;
       String letterlist2 = new String();
 //      String itemlist = rgame.getParm("list2");
 //      String itemlist2 = rgame.getParm("list3");
       String diclist[] = db.list(sharkStartFrame.getPrimarySoundDb(sharkStartFrame.publicSoundLib),db.WAV);
       diclist = u.filterIrregularWords(diclist);
        for(i=0;i<diclist.length;++i) {
          if(diclist[i].length() >= 2 &&  u.onlylower(diclist[i])
                && (len2<0 || !letterlist2.substring(len2).equals(diclist[i].substring(0,2)))) {
               letterlist2 = letterlist2 + diclist[i].substring(0,2);
               len2+=2;
          }
       }
       items = new String[15];
       short start;
       for(i=0,start=(short)(u.rand(len2/2+1-15)*2); i<15; ++i,start+=2) {
          items[i] = letterlist2.substring(start,start+2);
       }
    }                                   // this word list
    else if(type == 3){
       items = new String[rgame.w.length];
       for(i=0;i<rgame.w.length;++i) {
           items[i] = rgame.w[i].v();
           if(items[i].length() > 8) items[i]=items[i].substring(0,8);
       }
    }
    else if(type == 4){
       items = u.randword(14,3,3);
    }
    else if(type == 5){
       items = u.randword(12,4,4);
    }
    if(caps) for(i=0;i<items.length;++i) {
          items[i] = items[i].toUpperCase();
    }
    u.sort(items);
    droppedball = new ball[items.length];
    bwidth = mover.WIDTH/Math.max(items.length,10);
    bheight = bwidth*screenwidth/screenheight;
    o = u.shuffle(u.select((short)items.length,(short)items.length));
    nextball();
    lastmousex = gamePanel.mousex;
  }
  boolean singleword() {
      int i;
      for(i=0;i<rgame.w.length;++i) {
        if(rgame.w[i].v().length() != 1) return false;
      }
      return true;
  }
 //--------------------------------------------------------------
 public void restart() {
    gamePanel.removeAllMovers();
    super.restart();
    droppedtot=0;
    curr = 0;
    droppingball = null;
    setup1();
 }
 public void afterDraw(long t){
   if(completed) gamePanel.showSprite = true;
   else gamePanel.showSprite = gamePanel.mousey < mover.HEIGHT /6 || gamePanel.mousey > mover.HEIGHT*7/8;
 }
  //----------------------------------------------------------------
  void nextball() {
//startPR2005-04-18^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    if(Demo_base.isDemo){
      if (Demo_base.demoIsReadyForExit(1)) return;
    }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     if(curr >= o.length) {
        String message;
        int av;
        score(av = gametot1/items.length);
        if(av>8)  message = rgame.getParm("wonderful") ;
        else if(av >= 5)   message = rgame.getParm("good");
        else   message = rgame.getParm("end");
        showmessage(u.edit(message,String.valueOf(gametot1)),
           mover.WIDTH/3,mover.HEIGHT/4, mover.WIDTH*2/3,mover.HEIGHT/2);
        exitbutton(mover.HEIGHT/2);
        gamePanel.showSprite = true;
     }
     else {
       if(delayedflip && !completed){
           flip();
       }
       droppingball  = new ball(o[curr]);
       if(sayphonic && items[o[curr]].length()==1)  spokenWord.sayPhonic(items[o[curr]]);
        ++curr;
     }
  }
  //---------------------------------------------------------------------
  void calcpos() {
     int moveinc = mover.WIDTH/screenwidth,inc,i,j;
     int mousedist,wantdist,gotdist,start;
     if(droppingball != null && droppingball.y > mover.HEIGHT - bheight*2) {
         for(i=0;i<droppedtot;++i) {
            if(droppingball.x > droppedball[i].x) {
               while(droppingball.touches(droppedball[i])) {
                   droppedball[i].x -= moveinc;
               }
               for( j=i-1;j>=0;--j)
                  droppedball[j].x = Math.min(droppedball[j].x, droppedball[j+1].x - bwidth);
            }
            else {
               while(droppingball.touches(droppedball[i])) {
                   droppedball[i].x += moveinc;
               }
               for( j=i+1; j<droppedtot; ++j)
                  droppedball[j].x = Math.max(droppedball[j].x, droppedball[j-1].x + bwidth);
            }
         }
     }
     mousedist = gamePanel.mousex - lastmousex;
     if(mousedist > 0 && droppedtot>0) {
         wantdist = (mover.WIDTH - droppedball[0].x - bwidth/2);
         gotdist = mover.WIDTH - lastmousex;
         mousedist = mousedist*wantdist/gotdist;
         start = droppedball[0].x + mousedist;
         for(i=0;i<droppedtot;++i) {
            if(droppingball != null && droppingball.y > mover.HEIGHT - bheight*2
                &&  droppingball.x > droppedball[i].x
                &&  droppingball.x < start + bwidth
                && (i>=droppedtot-1 ||  droppingball.x < droppedball[i+1].x)){
               do {
                  droppedball[i].x += moveinc;
                  if(droppingball.touches(droppedball[i]))
                       droppingball.x += moveinc;
                  if(i < droppedtot-1 && droppingball.touches(droppedball[i+1]))
                       droppedball[i+1].x += moveinc;
               } while(droppedball[i].x  < start);
            }
            else {
               droppedball[i].x = Math.max(start,droppedball[i].x);
            }
            start = droppedball[i].x + bwidth;
        }
     }
     else if(mousedist < 0 && droppedtot>0) {
         wantdist = (droppedball[droppedtot-1].x + bwidth/2);
         gotdist = lastmousex;
         mousedist = mousedist*wantdist/gotdist;
         start = droppedball[droppedtot-1].x + mousedist;
         for(i=droppedtot-1;i>=0;--i) {
            if(droppingball != null && droppingball.y > mover.HEIGHT - bheight*2
                &&  droppingball.x < droppedball[i].x
                &&  droppingball.x > start - bwidth
                && (i==0 ||  droppingball.x > droppedball[i-1].x)){
               do {
                  droppedball[i].x -= moveinc;
                  if(droppingball.touches(droppedball[i]))
                       droppingball.x -= moveinc;
                  if(i > 0 && droppingball.touches(droppedball[i-1]))
                       droppedball[i-1].x -= moveinc;
               } while(droppedball[i].x  > start);
            }
            else {
               droppedball[i].x = Math.min(start,droppedball[i].x);
            }
            start = droppedball[i].x - bwidth;
        }
        if(droppingball.falling && droppingball.y < mover.HEIGHT-bheight*2)
              droppingball.x = Math.max(0,Math.min(mover.WIDTH-bwidth,droppingball.x));
     }
     for(i=0;i<droppedtot;++i) {droppedball[i].tox=droppedball[i].x;droppedball[i].toy=droppedball[i].y;  }
     lastmousex = gamePanel.mousex;
  }
  //---------------------------------------------------------------------
  class ball extends mover {
     short ballno;
     int dy,dx,bouncedy;
     boolean falling;
     long lasttime;
     Color ballcolor = darkballs?u.fairlydarkcolor():u.brightcolor();
     ball(short bno) {
        super(false);
        ballno = bno;
        w = bwidth;
        h = bwidth*screenwidth/screenheight;
        addMover(this, bwidth + u.rand(mover.WIDTH - bwidth*3), 0);
        falling = keepMoving=true;
        lasttime = gtime();
        addscore = 2;
     }
     public void changeImage(long time) {
        w = bwidth;
        h = bheight = bwidth*screenwidth/screenheight;
        if(falling) {
           int g  =  (2+speed) * mover.HEIGHT/500;
           int t1 = (int)(time-lasttime);
           int newvel = dy + g * t1;
           y += (dy+newvel)*t1/2/gfactor;
           dy = newvel;

           if(y > mover.HEIGHT - bheight) {
              if(correctpos())   stopmoving();
              else {
                   addscore = (short)Math.max(0,addscore-1);
                   y =  (mover.HEIGHT - bheight)*2-y;
                   if(bouncedy == 0){
                      bouncedy = dy = -dy*2/3;
                      noise.groan();
                   }
                   else dy = bouncedy;
              }
           }
           calcpos();
           tox=x;toy=y;
        }
        else y=toy=mover.HEIGHT-h;
        lasttime = time;
     }
     boolean correctpos() {
        for(short i=0; i<droppedtot;++i) {
           if(ballno < droppedball[i].ballno && x > droppedball[i].x
               || ballno > droppedball[i].ballno && x < droppedball[i].x) return false;
        }
        return true;
     }
     boolean touches(ball b) {
        return (x-b.x)*(x-b.x)+(y-b.y)*(y-b.y)  <= bwidth*bwidth;
     }
     void stopmoving() {
        short i;
        noise.plop();
        if(lastmousex < 0) lastmousex = gamePanel.mousex;
        falling = false;
        y = mover.HEIGHT - bheight;
        calcpos();
        if(addscore != 0) gamescore(addscore);
        for(i=0;i<droppedtot;++i) {
           if(x < droppedball[i].x) {
               System.arraycopy(droppedball,i,droppedball,i+1,droppedtot-i);
               break;
           }
        }
        droppedball[i] = this;
        droppedtot++;
        droppingball = null;
        nextball();
     }
     public void paint(Graphics g,int x1, int y1, int w1, int h1) {
        if(w1 != lastw1) {
          wordfont = sizeFont(items,bwidth*screenwidth/mover.WIDTH,
                 bwidth*screenwidth/mover.WIDTH * items.length *
                 (5-Math.min(3,maxWordLen(items))) /4);
          metrics = getFontMetrics(wordfont);
          lastw1 = w1;
        }
        int hwidth = bwidth*screenheight/mover.HEIGHT;
        g.setColor(ballcolor);
        ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        g.fillOval(x1,y1,w1,h1);
        ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);
        g.setColor(darkballs?Color.white:Color.black);
        g.setFont(wordfont);
        g.drawString(items[ballno], x1+w1/2-metrics.stringWidth(items[ballno])/2,
                          y1+h1/2-metrics.getHeight()/2 + metrics.getAscent());
     }
  }
}
