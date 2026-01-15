package shark.games;

import java.awt.*;
import shark.*;

public class chunks extends  sharkGame {
  sentence sentences[];
  String sents[];
  int margin = mover.WIDTH / 20;
  int width, lineinc, curr = -1;
  int order[], stot, ptot, swidth;
  long wantnext = gtime;
  sharkImage lookfor[], copies[];
  sharkImage distract[];
  int maxwidth = mover.WIDTH / 6, minwidth = mover.WIDTH / 10;
  int maxheight = mover.HEIGHT / 3, minheight = mover.WIDTH / 5;
  int narrh = mover.HEIGHT / 10, narrw = mover.WIDTH / 2;
  boolean found[];
  boolean ending;
  boolean removeanimals;
  int miny;
  mover mess;
  boolean spaces;
  boolean first = true, isstarter;
  boolean dontsee, donthear;
  mover startmessage;
  int watery = mover.HEIGHT / 8, bottom = mover.HEIGHT * 5 / 8, bottom2 = mover.HEIGHT;
  boolean haderr;
  long wantchunks, wantfull;
  chunk chunklist[];
  String ok = rgame.getParm("ok");
  sharkImage tick = sharkImage.random("tick_");
  okbutton okbutton;
  pic pics[];
  Font picf;
  FontMetrics picm;
  int piclastw1;
  boolean fitted;


   public chunks() {
    int i,j;
    errors = true;
    gamescore1 = true;
    peeps = false;
    listen= true;
    peep = false;
    wantspeed = true;
    bgavoid = new Color[] {Color.yellow};
    gamePanel.clearWholeScreen = true;
//    gamePanel.savefixed = true;
    sentences = sharkStartFrame.mainFrame.wordTree.getsentences(sharkStartFrame.currPlayTopic.getSpecials(new String[]{topic.sentencegames2[0]}));
    stot = 0;
    ptot = 0;
    for (i = 0; i < sentences.length; ++i) {
           if (sentences[i].chunks != null)  {
             ++stot;
             if(sentences[i].picture != null) {
                   ++ptot;
             }
           }
    }
    if(stot < sentences.length) {                          // keep only sentences with chunks
         sentence sentences2[] = new sentence[stot];
         for(i=j=0;i<sentences.length;++i) {
            if(sentences[i].chunks != null) sentences2[j++] = sentences[i];
         }
         sentences = sentences2;
    }
    if(ptot != 0) {
      pics = new pic[ptot];
      for(i=j=0;j<sentences.length;++j) {
         if(sentences[j].picture != null) pics[i++]= new pic(sentences[j].picture,  j);
      }
    }
    swidth = mover.WIDTH/stot;
    order = u.shuffle(u.select(stot,stot));
    optionlist = new String[]{"crossword1-seehear"};
    dontsee=donthear=false;
    switch(options.optionval("crossword1-seehear")) {
           case 1:donthear = true; break;
           case 2:dontsee = true; break;
    }
//startPR2007-11-19^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    wantSprite=false;
    gamePanel.winsprite = true;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    buildTopPanel();
    gamePanel.setBackground(new Color(80,80,160+u.rand(60)));
    markoption();
    help("help_chunks");
    setlisten(!donthear);
   }
   //---------------------------------------------------------
   public void restart() {
      dontsee=donthear=false;
      switch(options.optionval("crossword1-seehear")) {
            case 1:donthear = true; break;
            case 2:dontsee = true; break;
      }
      setlisten(!donthear);
      markoption();

      curr=-1;
      haderr = false;

      wantnext=gtime;
      first=true;
      isstarter=false;
//      startmessage = showmessage(rgame.getParm("startmessage"), 0, mover.HEIGHT * 3 / 4, mover.WIDTH, mover.HEIGHT);
      spaces = options.option(optionlist[0]);
      help(donthear?"help_chunkssee":"help_chunks");
   }
   //------------------------------------------------------------------
   public void afterDraw(long t){
     int i;
     if(wantchunks != 0 && gtime > wantchunks) {
         wantchunks = 0;
         gamePanel.removeAllMovers();
         help("help_chunks2");
         setlisten(false);
         okbutton = new  okbutton();
         chunklist = new chunk[sentences[order[curr]].chunks.length];
         for(i=0;i<chunklist.length;++i) {
            chunklist[i] = new chunk(sentences[order[curr]].chunks[i],i);
         }
     }
     if(wantfull != 0 && gtime > wantfull) {
       wantfull = 0;
       gamePanel.removeAllMovers();
       showsent showsent  = new showsent(sentences[order[curr]].stripcloze(),chunklist[0].tox, chunklist[0].toy);
       if(sentences[order[curr]].picture != null) {
         fitted = false;
         showsent.moveto(mover.WIDTH/2-showsent.w/2, mover.HEIGHT/2 - showsent.h/2,1000);
         int o[] = u.shuffle(u.select(ptot,ptot));
         for(i=0;i<pics.length;++i) {
            pics[i].addpic(o[i]);
         }
       }
       else wantnext = gtime + 3000;
     }
     if(wantnext > 0 && gtime > wantnext) {
        setlisten(!donthear);
        wantnext = 0;
        haderr = false;
        help(donthear?"help_chunks1":"help_chunks1a");
        if(curr>=0) {
          gamePanel.removeAllMovers();
        }
        if(++curr < stot) {
          if(curr>0) {
           }
           setsentence();
        }
        else {
           exitbutton(mover.HEIGHT*7/8);
           removeMover(sentences[order[curr-1]]);
           String message=null;
          }
      }
   }
   //---------------------------------------------------------------------
   void setsentence() {
     sentences[order[curr]].manager = gamePanel;
     sentences[order[curr]].dontsee = dontsee;
     sentences[order[curr]].donthear = donthear;
     sentences[order[curr]].nohelp = true;
     sentences[order[curr]].finished = false;               // rb 9/1/08
     sentences[order[curr]].setup(mover.WIDTH, mover.HEIGHT/4, wordlist.getNormalWordlist()) ;  // rb 16/1/08
     if(sentences[order[curr]].picture != null) {
       sentences[order[curr]].picture.w = mover.WIDTH/3 ;
       sentences[order[curr]].picture.h = mover.HEIGHT*3/8 ;
       addMover( sentences[order[curr]].picture, mover.WIDTH/2 - sentences[order[curr]].picture.w/2, 0);
       addMover(sentences[order[curr]],0,mover.HEIGHT/2 );
     }
     else addMover(sentences[order[curr]],0,mover.HEIGHT/2 - sentences[order[curr]].h/2);
   }
   public void listen1() {
     if(curr>=0 && curr < order.length)
       sentences[order[curr]].readsentence();
   }
   //-------------------------------------------------------------
  public void sparefunc() {    // after doing something in sentence
    sentences[order[curr]].crosswd[sentences[order[curr]].doneword].say();
    if (sentences[order[curr]].finished)
         wantchunks = gtime + 1000;
  }
  short[] chunksaligned() {
      int i;
      int xlist[] = new int[chunklist.length];
      for(i=0;i<chunklist.length; ++i) {
        xlist[i] = chunklist[i].x;
      }
      short o[] = u.getorder(xlist);
      for(i=0;i<chunklist.length; ++i) {
        xlist[i] = chunklist[i].x;
      }
      for(i=1;i<chunklist.length; ++i) {
        if(Math.abs(chunklist[o[i]].y - chunklist[o[i-1]].y) > chunklist[o[i]].h/3
            || chunklist[o[i]].x < chunklist[o[i-1]].x + chunklist[o[i-1]].w
            || chunklist[o[i]].x > chunklist[o[i-1]].x + chunklist[o[i-1]].w + mover.WIDTH/20) return null;
      }
      return o;
  }
  //==================================================================
  class chunk extends mover {
     String val;
     int pos;
     Font f;
     FontMetrics m;
     int lastwo;
     chunk(String ch, int pos1) {
        int i;
        val = ch;
        pos = pos1;
        val = val.substring(0,1).toLowerCase() + val.substring(1);
        f = sentences[order[curr]].font;
        m = sentences[order[curr]].metrics;
        w = m.stringWidth(ch)*mover.WIDTH/screenwidth;
        h = m.getHeight()*mover.HEIGHT/screenheight;
        chunklist[pos] = this;
        loopc: while(true) {
          x = u.rand(mover.WIDTH - w);
          y = u.rand(okbutton.y - h);
          int xgap = mover.WIDTH/5;
          for (i=0; i<pos; ++i) {
             if(chunklist[i].y < y + h *4 && chunklist[i].y > y-h*3
                && chunklist[i].x < x + w + xgap && chunklist[i].x + chunklist[i].w > x-xgap) continue loopc;
          }
          break loopc;
         }
         addMover(this,x,y);
     }
     public void paint(Graphics g, int x1, int y1, int w1, int h1) {
       g.setFont(f);
       g.setColor(Color.black);
       g.drawString(val, x1 + w1 / 2 - m.stringWidth(val) / 2, y1 + h1 / 2 + m.getAscent() / 2);
     }
     public void mouseClicked(int x, int y) {
        if(!gamePanel.movesWithMouse(this)) gamePanel.moveWithMouse(this);
        else {
          gamePanel.drop();
          y = toy = Math.min(toy, okbutton.y - h);
        }
     }
  }
  class okbutton extends mover {
    long pressed;
    Color bg = Color.yellow;
    Font fok;
    FontMetrics fokm;
    int lastwo;
    boolean done;
    okbutton() {
       w = mover.WIDTH / 12;
      h = mover.HEIGHT / 16;
      addMover(this,mover.WIDTH/2 - w/2, mover.HEIGHT - h*3/2);
    }

    public void paint(Graphics g, int x1, int y1, int w1, int h1) {
      if(chunksaligned()==null || gamePanel.mousemovertot > 0) return;
      if (fok == null || lastwo != w1) {
        fok = sizeFont(new String[] {ok}, w1 + 3 / 4, h1 * 3 / 4);
        fokm = getFontMetrics(fok);
        lastwo = w1;
      }
      g.setColor(bg);
      g.fillRect(x1, y1, w1, h1);
      g.setFont(fok);
      g.setColor(Color.black);
      g.drawString(ok, x1 + w1 / 2 - fokm.stringWidth(ok) / 2, y1 + h1 / 2 - fokm.getHeight()/2 + fokm.getAscent());
      u.buttonBorder(g, new Rectangle(x1, y1, w1, h1), bg, gtime - pressed > 500);
    }

    public void mouseClicked(int mx, int my) {
      if(done) return;
      short o[] = chunksaligned();
      int i;
      if(o == null || gamePanel.mousemovertot > 0) return;
      for(i=0;i<o.length;++i) {
         if(o[i] != i) {
             gamescore(-1);
             noise.groan();
             return;
         }
      }
      done = true;
      gamescore(1);
      tick.w = mover.WIDTH/10;
      tick.h = h*2;
      addMover(tick,x+w,mover.HEIGHT - tick.h);
      int yy=chunklist[0].y, xx = chunklist[0].x;
      int gap = chunklist[0].m.charWidth(' ')*mover.WIDTH/screenwidth;
      for(i=0;i<chunklist.length;++i) {
        chunklist[i].moveto(xx,yy,1000);
        xx += chunklist[i].w + gap;
      }
      wantfull = gtime+1000;
     }
  }
  class showsent extends mover {
    String val;
    int pos;
    Font f;
    FontMetrics m;
    int lastwo;
    showsent(String ch, int xx, int yy) {
       int i;
       val = ch;
       f = sentences[order[curr]].font;
       m = sentences[order[curr]].metrics;
       w = m.stringWidth(ch)*mover.WIDTH/screenwidth;
       h = m.getHeight()*mover.HEIGHT/screenheight;
       addMover(this,xx,yy);
    }
    public void paint(Graphics g, int x1, int y1, int w1, int h1) {
       g.setColor(Color.black);
       g.setFont(f);
       g.drawString(val,x1,y1+m.getAscent());
    }
  }
  class pic extends mover {
     sharkImage im;
     int sentno;   // sentence no
     int pos;      // rel position on screen
     boolean done;
     String val;
     pic(sharkImage im1,int sentno1) {
       im = im1;
       sentno = sentno1;
       val = sentences[sentno].stripcloze();
       w = mover.WIDTH/Math.max(2,(ptot+1)/2);
       h = mover.HEIGHT*3/4 / 2;
     }
     void addpic(int pos1) {
       pos = pos1;
       boolean top = pos <(ptot+1)/2;
       y = top?0 : mover.HEIGHT-h;
       int across = top ?(ptot+1)/2 : (ptot)/2;
       int pa = pos %((ptot+1)/2);
       if(across > 1) x = pa * (mover.WIDTH - w)/(across-1);
       else x = mover.WIDTH/2 - w/2;
       addMover(this,x,y);
     }
     public void paint(Graphics g, int x1, int y1, int w1, int h1) {
        int i;
        if(picf == null || piclastw1 != w1){
          piclastw1 = w1;
          String ss[] = new String[pics.length];
          for (i = 0; i < pics.length; ++i)
            ss[i] = pics[i].val;
          picf = sizeFont(ss, w1*7/8, h1/4*ptot);
          picm = getFontMetrics(picf);
        }
        im.paint(g,x1,y1,w1,h1*3/4);

        if(done && fitted) {
          g.setColor(Color.white);
          g.drawRect(x1,y1,w1,h1);
          g.setFont(picf);
          g.drawString(val, x1 + w1 / 2 - picm.stringWidth(val)/2, y1 + h1 - picm.getHeight() + picm.getAscent());
        }
     }
     public void mouseClicked(int mx, int my) {
        if(fitted) return;
        if(sentno == order[curr]) {
          gamescore(1);
          noise.plop();
          wantnext = gtime + 3000;
          fitted = true;
          done = true;
        }
        else {
           noise.groan();
           gamescore(-1);
        }
     }
  }
}
