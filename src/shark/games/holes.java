package shark.games;

import java.awt.*;

import shark.*;

public class holes extends  sharkGame {
   public char items[];
   int bwidth,bheight;
   boolean darkballs;
   short o[],curr;
   static final int gfactor = 17000;
   int lastmousex=-1;
   hole holes[];
   ball balls[];
   boolean usedim[];
   sharkImage holeimages[];
   boolean usedimg[];
   short droptot;
   int freezetime = 800;
   short ballgot,wantball = 16,dropping;
   long delaytime;
   final int showlim = 4000;
   final int smoketime = 1000;
   final int initspeed1 = mover.HEIGHT;
   final int initspeed2 = mover.HEIGHT*2/3;
   final int highspeed = mover.HEIGHT*20;
   boolean caps, animate;
   boolean wantag,wanthn,wantot,wantuz, sayphonic;

   int holey;
   sharkGame thisg = this;
   long exitat;
   Color eyecolor = new Color(180,180,255);

   public holes() {
    errors = false;
    gamescore1 = true;
    deaths = false;
 //    peeps = true;
 //    listen= true;
 //    peep = true;
    wantspeed = true;
    optionlist = new String[] {"holes-alpha","sort-capitals","holes-animate","sort-ag","sort-hn","sort-ot","sort-uz","sort-sayphonic"};
   // opnarr = new String[] {"1 ball|2 balls|3 balls|4 balls|5 balls|6 balls"};
    rgame.options |= word.CENTRE;
    help("help_holes");
    buildTopPanel();
//    gamePanel.setBackground(u.brightcolor());
    if(keypad.keypadname == null) gamePanel.showSprite = false;
    gamePanel.clearWholeScreen = true;
   setup1();
   }
   //------------------------------------------------------------------
   void setup1() {
    int i,j,extrax=0;
    if(keypad.keypadname != null) {
      keypad.activate(this, null);
      extrax = keypad.keypadwidth(this) * mover.WIDTH / screenwidth;
      if (extrax > mover.WIDTH / 3) {
        extrax = 0;
        new fillbottomleft(keypad.keypadheight(this)*mover.HEIGHT/screenheight);
      }
     }
    droptot = options.optionval(optionlist[0]);
    caps = options.option(optionlist[1]);
    animate = options.option(optionlist[2]);
    wantag = options.option("sort-ag");
    wanthn = options.option("sort-hn");
    wantot = options.option("sort-ot");
    wantuz = options.option("sort-uz");
    sayphonic = options.option("sort-sayphonic");
    markoption();
    String itemlist = rgame.getParm("list");
    if(caps) itemlist = itemlist.toUpperCase();
    items = new char[itemlist.length()];
    for(i=0;i<itemlist.length();++i)
           items[i] = itemlist.charAt(i);
    java.util.Arrays.sort(items);
    holeimages = sharkImage.findall("hole_");
    usedimg = new boolean[holeimages.length];
    bwidth = (mover.WIDTH)/Math.max(items.length,10);
    bheight = bwidth*screenwidth/screenheight;
    holey = Math.min(mover.HEIGHT*3/4, mover.HEIGHT - bheight*5);
    if(keypad.keypadname != null)
        holey = Math.min(holey,  mover.HEIGHT - keypad.keypadheight(this) * mover.HEIGHT / screenheight-bheight*2);
    balls = new ball[wantball];
    buildholes();
    if((!wantag ||!wanthn ||!wantot ||!wantuz) && (wantag || wanthn || wantot || wantuz)) {
      String s = (wantag?u.gettext("sort-ag","letters"):"")
          + (wanthn?u.gettext("sort-hn","letters"):"")
          + (wantot?u.gettext("sort-ot","letters"):"")
          + (wantuz?u.gettext("sort-uz","letters"):"");
       if(caps) s = s.toUpperCase();
       for (i = 0; i < holes.length; ++i) {
          if (s.indexOf(items[i]) < 0)   {
            holes[i].ignore = true;
           }
      }
    }
    wordfont = sizeFont(null,"w",bwidth, bheight);
    metrics = getFontMetrics(wordfont);
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
    int i,j,ii, jj;
    droptot = options.optionval(optionlist[0]);
    caps = options.option(optionlist[1]);
    animate = options.option(optionlist[2]);
    wantag = options.option("sort-ag");
    wanthn = options.option("sort-hn");
    wantot = options.option("sort-ot");
    wantuz = options.option("sort-uz");
    sayphonic = options.option("sort-sayphonic");
    if (droptot < 0)   droptot=1;
    markoption();
    String s = u.lowercase;
    if((!wantag ||!wanthn ||!wantot ||!wantuz) && (wantag || wanthn || wantot || wantuz))
       s = (wantag?u.gettext("sort-ag","letters"):"")
          + (wanthn?u.gettext("sort-hn","letters"):"")
          + (wantot?u.gettext("sort-ot","letters"):"")
          + (wantuz?u.gettext("sort-uz","letters"):"");
    if(caps) s = s.toUpperCase();
    for(i=0;i<items.length;++i) {
       if(caps) items[i] = Character.toUpperCase(items[i]);
       else items[i] = Character.toLowerCase(items[i]);
    }
    for(i=0;i<holes.length;++i) {
      boolean wantignore = s.indexOf(items[i]) < 0;
      if (holes[i].ignore && !wantignore) {
        holes[i].ignore = false;
      }
      else
        if (!holes[i].ignore && wantignore) {
          holes[i].ignore = true;
        }
      addMover(holes[i], holes[i].x, holes[i].y);
    }
    ballloop: for(i=0;i<ballgot;++i) {
        if(balls[i].falling) {
          for(j=0;j<holes.length;++j) {
             if(holes[j].ignore  && holes[j].x >= balls[i].x && holes[j].x  < balls[i].x + balls[i].w) {
                int o[] = u.shuffle(u.select(holes.length,holes.length));
                iiloop: for(ii=0;ii<o.length;++ii) {
                  jj = o[ii];
                  for(j = jj;  j<holes.length && holes[j].x < holes[jj].x+balls[i].w; ++j) {
                     if(holes[j].ignore) continue iiloop;
                  }
                  balls[i].x = holes[jj].x;
                  continue ballloop;
                }
             }
          }
        }
    }
    for(i=0;i<ballgot;++i)
         if(balls[i].falling) addMover(balls[i],balls[i].x,balls[i].y);
    nextball();
 }
 public void afterDraw(long t) {
     if(exitat != 0 && gtime >= exitat) {
       exitat = 0 ;
       String message;
       if(gametot1==wantball)  message = rgame.getParm("wonderful") ;
       else if(gametot1>wantball*3/4)  message = rgame.getParm("good") ;
       else   message = rgame.getParm("end");
       showmessage(u.edit(message,String.valueOf(gametot1)),
          0,0, mover.WIDTH,mover.HEIGHT/3);
       exitbutton(holey - bheight*4);
       gamePanel.showSprite = true;
       score(gametot1);
       animate = true;
     }
 }
 //--------------------------------------------------------------
 public void keyhit(char key) {
    if(completed) return;
    if(delaytime > 0 && gtime < delaytime){
      if(spokenWord.endsay < gtime)
        noise.beep();
      delaytime = Math.min(gtime+1000,delaytime+400);
      return;
    }
    delaytime = delaytime = gtime+400;
    if(caps) key = Character.toUpperCase(key);
    else key = Character.toLowerCase(key);
    for(short i=0;i<items.length;++i) {
       if(key == items[i] && holes[i].showvalue == 0 && !holes[i].ignore) {
          holes[i].showvalue = gtime;
          if(holes[i].isneeded()) delaytime = 0;
          if(sayphonic) spokenWord.sayPhonic(new String(new char[]{key}));
          break;
       }
    }
 }
 //--------------------------------------------------------------
  void buildholes() {
     holes = new hole[items.length];
     for(short i=0; i<items.length; ++i) {
        holes[i] = new hole(i);
     }
  }
  //----------------------------------------------------------------
  void nextball() {
    int i;
//startPR2005-04-18^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    if(Demo_base.isDemo){
      if (Demo_base.demoIsReadyForExit(1)) return;
    }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     if(exitat==0 && dropping==0 && ballgot >=wantball) {
        exitat = gtime + 800;
        return;
     }
     else{
       if(delayedflip && !completed){
           flip();
       }
     }
     while(dropping<droptot && ballgot < wantball && ishole()) {
        do {
           if(curr >10000) curr = 0;
           i = o[curr++ % items.length];
        }while(holes[i].showvalue  != 0 || holes[i].ignore);
        int ex = u.rand(4);
        int start=i,end=i;
        while(end-start < ex) {
           boolean ok=false;
           if(u.rand(2)==0 && start >0 && !holes[start-1].ignore) --start;
           else if(end < holes.length-2 &&  !holes[end+1].ignore) ++end;
           else if(start >0 && !holes[start-1].ignore) --start;
           else break;
        }
        balls[ballgot++] = new ball(start,end-start+1);
        ++dropping;
     }
  }
  boolean ishole() {
      int i;
      for(i=0;i<holes.length;++i) if(holes[i].showvalue  == 0 && !holes[i].ignore) return true;
      return false;
  }
  //---------------------------------------------------------------------
  class ball extends mover {
     int dy,dx;
     boolean falling,dead,gotthru,wayopen;
     long lasttime;
     int tot;
     String ss;
     Color col = u.brightcolor();
     long frozen;
     int ballbottom = mover.HEIGHT;
     randrange_base eyerr = new randrange_base(0,100,50);
     randrange_base xrr;
     boolean left;
     randrange_base leg11rr = new randrange_base(50,100,200);
     randrange_base leg12rr = new randrange_base(0,100,200);

     randrange_base leg21rr = new randrange_base(50,100,200);
     randrange_base leg22rr = new randrange_base(0,100,200);

     randrange_base arm11rr = new randrange_base(20,180,100);
     randrange_base arm12rr = new randrange_base(-100,100,100);

     randrange_base arm21rr = new randrange_base(20,180,100);
     randrange_base arm22rr = new randrange_base(-100,100,100);

     randrange_base bounce = new randrange_base(0,100,200);

     ball(int start,int len) {
        super(true);
        tot=len;
        ss = String.valueOf(tot);
        dy = (initspeed1 + u.rand(initspeed2));
        falling = keepMoving = true;
        lasttime = gtime();
        h = bheight;
        w = bwidth*tot;
        x = holes[start].x;
        if(keypad.keypadname != null && x+w > (mover.WIDTH-keypad.keypadwidth(thisg) * mover.WIDTH / screenwidth)) {
             ballbottom = mover.HEIGHT - keypad.keypadheight(thisg) * mover.HEIGHT / screenheight;
        }
        xrr = new randrange_base(0,mover.WIDTH-w,mover.WIDTH/4);
        addMover(this, x, -h);
     }
     public void changeImage(long time) {
        int i;
        if(frozen != 0) {
           if(gtime>frozen + freezetime) {
             kill = true;
             dead = true;
             --dropping;
             nextball();
           }
           return;
        }
        if(falling && !options.active) {
           if(!wayopen && toy+h < holey) {
             boolean notopen = false;
             for (i = 0; i < holes.length; ++i) {
               if (holes[i].x >= x && holes[i].x < x + w && holes[i].showvalue == 0) {
                 notopen = true;
                 break;
               }
             }
             if(!notopen) {
                dy=highspeed;
                wayopen=true;
             }
           }
           int t1 = (int)(time-lasttime);
           y = y + dy*t1*(speed+3)/11/gfactor;
        }
        if(falling) {
           tox=x;toy=y;
           if(!gotthru && toy+h >= holey) {
             if(wayopen) {
              --dropping;
              nextball();
              gotthru = true;
            }
            else {
              noise.groan();
              frozen = gtime;
              y=toy=holey-h;
            }
          }
          if(y > ballbottom - h) {
              falling = false;
              xrr.setcurr(x);
              gamescore(1);
              y=toy=ballbottom-h;
           }
        }
        else if(animate) {               // on bottom
           tox = xrr.next(gtime);
           left = tox < x;
           x = tox;
           if(keypad.keypadname != null) {
              int kkx =  mover.WIDTH - keypad.keypadwidth(thisg) * mover.WIDTH / screenwidth;
              int kky = mover.HEIGHT - keypad.keypadheight(thisg)*mover.HEIGHT / screenheight;
              if(x >= kkx) {
                y = toy = mover.HEIGHT - keypad.keypadheight(thisg)*mover.HEIGHT / screenheight - h;
              }
              else if(x < kkx && x >= kkx - w/2) {
                y = toy = kky - h - h/2 * (kkx - x)/(w/2);
              }
              else if(x < kkx - w/2 && x > kkx - w*3/2) {
                y = toy = mover.HEIGHT - h - (mover.HEIGHT-(kky-h/2))*(x - (kkx-w*3/2))/w;
              }
              else y = toy = mover.HEIGHT - h;
           }
        }
        lasttime = time;
     }
      public void paint(Graphics g,int x1, int y1, int w1, int h1) {
        if(!falling && animate) {
          y1 -= h1 / 2 + bounce.next(gtime)* h1/4 /100;
          Polygon l1 = new Polygon(),l2=new Polygon();
          l1.addPoint(x1+w1/4,y1+h1);
          l2.addPoint(x1+w1*3/4,y1+h1);

          double aa1 = leg11rr.next(gtime)*Math.PI/2/100;            // leg 1
          double aa2 = aa1 + leg12rr.next(gtime)*Math.PI/2/100;
          int dx11 = (int)(h1/4*Math.cos(aa1));
          int dy11 = (int)(h1/4*Math.sin(aa1));
          int dx12 = (int)(h1/4*Math.cos(aa2));
          int dy12 = (int)(h1/4*Math.sin(aa2));
          int dx13 = (int)(h1/8*Math.cos(aa2-Math.PI/2));
          int dy13 = (int)(h1/8*Math.sin(aa2-Math.PI/2));
          if(left) {
            l1.addPoint(x1+w1/4 - dx11, y1+h1+dy11);
            l1.addPoint(x1+w1/4 - dx11 - dx12, y1+h1+dy11+dy12);
            l1.addPoint(x1+w1/4 - dx11 - dx12 - dx13, y1+h1+dy11+dy12+dy13);
          }
          else {
            l1.addPoint(x1+w1/4 + dx11, y1+h1+dy11);
            l1.addPoint(x1+w1/4 + dx11 + dx12, y1+h1+dy11+dy12);
            l1.addPoint(x1+w1/4 + dx11 + dx12 + dx13, y1+h1+dy11+dy12+dy13);
          }
          aa1 = leg21rr.next(gtime)*Math.PI/2/100;     // leg 2
          aa2 = aa1 + leg22rr.next(gtime)*Math.PI/2/100;
          dx11 = (int)(h1/4*Math.cos(aa1));
          dy11 = (int)(h1/4*Math.sin(aa1));
          dx12 = (int)(h1/4*Math.cos(aa2));
          dy12 = (int)(h1/4*Math.sin(aa2));
          dx13 = (int)(h1/8*Math.cos(aa2-Math.PI/2));
          dy13 = (int)(h1/8*Math.sin(aa2-Math.PI/2));
          if(left) {
            l2.addPoint(x1+w1*3/4 - dx11, y1+h1+dy11);
            l2.addPoint(x1+w1*3/4 - dx11 - dx12, y1+h1+dy11+dy12);
            l2.addPoint(x1+w1*3/4 - dx11 - dx12 - dx13, y1+h1+dy11+dy12+dy13);
          }
          else {
            l2.addPoint(x1+w1*3/4 + dx11, y1+h1+dy11);
            l2.addPoint(x1+w1*3/4 + dx11 + dx12, y1+h1+dy11+dy12);
            l2.addPoint(x1+w1*3/4 + dx11 + dx12 + dx13, y1+h1+dy11+dy12+dy13);
          }
          g.setColor(Color.black);
          g.drawPolyline(l1.xpoints,l1.ypoints,l1.npoints);
          g.drawPolyline(l2.xpoints,l2.ypoints,l2.npoints);

          Polygon a1 = new Polygon(),a2=new Polygon();
          a1.addPoint(x1,y1+h1/2);
          a2.addPoint(x1+w1,y1+h1/2);

          aa1 = Math.PI/2 + arm11rr.next(gtime)*Math.PI/2/100;     // arm 1
          aa2 = aa1 + arm12rr.next(gtime)*Math.PI/2/100;
          dx11 = (int)(h1/4*Math.cos(aa1));
          dy11 = (int)(h1/4*Math.sin(aa1));
          dx12 = (int)(h1/4*Math.cos(aa2));
          dy12 = (int)(h1/4*Math.sin(aa2));
          dx13 = (int)(h1/8*Math.cos(aa2-Math.PI/3));
          dy13 = (int)(h1/8*Math.sin(aa2-Math.PI/3));
          int dx14 = (int)(h1/8*Math.cos(aa2+Math.PI/3));
          int dy14 = (int)(h1/8*Math.sin(aa2+Math.PI/3));
          int dx15 = (int)(h1/8*Math.cos(aa2));
          int dy15 = (int)(h1/8*Math.sin(aa2));
          a1.addPoint(x1 + dx11, y1+h1/2+dy11);
          a1.addPoint(x1 + dx11 + dx12, y1+h1/2+dy11+dy12);
          a1.addPoint(x1 + dx11 + dx12 + dx13, y1+h1/2+dy11+dy12+dy13);
          g.drawLine(x1 + dx11 + dx12, y1+h1/2+dy11+dy12, x1 + dx11 + dx12 + dx15, y1+h1/2+dy11+dy12+dy15);
          g.drawLine(x1 + dx11 + dx12, y1+h1/2+dy11+dy12, x1 + dx11 + dx12 + dx14, y1+h1/2+dy11+dy12+dy14);

          aa1 = - Math.PI/2 + arm11rr.next(gtime)*Math.PI/2/100;     // arm 2
          aa2 = aa1 + arm12rr.next(gtime)*Math.PI/2/100;
          dx11 = (int)(h1/4*Math.cos(aa1));
          dy11 = (int)(h1/4*Math.sin(aa1));
          dx12 = (int)(h1/4*Math.cos(aa2));
          dy12 = (int)(h1/4*Math.sin(aa2));
          dx13 = (int)(h1/8*Math.cos(aa2-Math.PI/2));
          dy13 = (int)(h1/8*Math.sin(aa2-Math.PI/2));
          dx14 = (int)(h1/8*Math.cos(aa2+Math.PI/3));
          dy14 = (int)(h1/8*Math.sin(aa2+Math.PI/3));
          dx15 = (int)(h1/8*Math.cos(aa2));
          dy15 = (int)(h1/8*Math.sin(aa2));
          a2.addPoint(x1 + w1 + dx11, y1+h1/2+dy11);
          a2.addPoint(x1 + w1 + dx11 + dx12, y1+h1/2+dy11+dy12);
          a2.addPoint(x1 + w1 + dx11 + dx12 + dx13, y1+h1/2+dy11+dy12+dy13);
          g.drawLine(x1 + w1 + dx11 + dx12, y1+h1/2+dy11+dy12, x1 + w1 + dx11 + dx12 + dx15, y1+h1/2+dy11+dy12+dy15);
          g.drawLine(x1 + w1 + dx11 + dx12, y1+h1/2+dy11+dy12, x1 + w1 + dx11 + dx12 + dx14, y1+h1/2+dy11+dy12+dy14);

          g.drawPolyline(a1.xpoints,a1.ypoints,a1.npoints);
          g.drawPolyline(a2.xpoints,a2.ypoints,a2.npoints);

        }
        g.setColor(col);
        g.fillRect(x1, y1, w1, h1);
        ( (Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int eyeh = h1 / 3;
        int hh;
        g.setColor(eyecolor);
        g.fillOval(x1+w1/3-eyeh/2,y1+h1/3-eyeh/2,eyeh,eyeh);
        g.fillOval(x1+w1*2/3-eyeh/2,y1+h1/3-eyeh/2,eyeh,eyeh);
        g.setColor(Color.black);
        int happy = 1000 * (holey-h - y) /(holey - h);
        double eyeangle = eyerr.next(gtime)*Math.PI/50;
        if(wayopen) happy = 1000;
        else {
           if(happy < 500) eyeangle = Math.PI/2;
        }
        int eyex1 = x1+w1/3+(int)(eyeh/4*Math.cos(eyeangle))-eyeh/4;
        int eyex2 = x1+w1*2/3+(int)(eyeh/4*Math.cos(eyeangle))-eyeh/4;
        int eyey = y1+h1/3+(int)(eyeh/4*Math.sin(eyeangle))-eyeh/4;
        g.fillOval(eyex1,eyey,eyeh/2,eyeh/2);
        g.fillOval(eyex2,eyey,eyeh/2,eyeh/2);
        if(happy>500) {
          hh = h1*(happy - 500)/500/4;
          g.drawArc(x1 + w1 / 5, y1 + h1 * 2/3 - hh, w1 * 3 / 5, hh*2, 0, -180);
        }
        else {
          hh = h1*(500 - happy)/500/4;
          g.drawArc(x1 + w1 / 5, y1 + h1 * 2/3, w1 * 3 / 5, hh*2, 0, 180);
        }
        ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);
     }
  }
  class hole extends mover {
     short buckno,val;
     long showvalue;
     boolean ignore;
     hole(short bno) {
        super(false);
        buckno = bno;
        h = bheight;
        w = bwidth;
        addMover(this,bwidth*buckno,holey);
        keepMoving = true;
     }
     public void paint(Graphics g,int x1, int y1, int w1, int h1) {
        g.setColor(Color.black);
        if(ignore) {
          if(buckno == 0) g.fillRect(x1,y1,w1-1,h1);
          else g.fillRect(x1,y1,w1,h1);
        }
        else {
          if(showvalue != 0) {
            int i;
            if (!isneeded() && gtime - showvalue > showlim) {
              showvalue = 0;
            }
            else {
              g.setFont(wordfont);
              g.setColor(Color.red);
              g.drawString(String.valueOf(items[buckno]), x1 + w1 / 2 - metrics.charWidth(items[buckno]) / 2,
                           y1 + h1 - metrics.getHeight() + metrics.getAscent());
            }
          }
          if(showvalue == 0) {
            if (buckno == holes.length - 1 || holes[buckno+1].showvalue != 0)           // 28/1/08
              g.drawLine(x1 + w1, y1, x1 + w1, y1 + h1);
            g.drawPolyline(new int[] {x1 + w1, x1, x1, x1 + w1}, new int[] {y1, y1, y1 + h1, y1 + h1}, 4);
          }
        }
     }
     boolean isneeded() {
       int i;
       for(i=0;i<ballgot;++i) {
       if(!balls[i].dead && balls[i].y <= y + h
          && x >= balls[i].x && x < balls[i].x + balls[i].w) return true;
       }
       return false;
     }
  }
  class fillbottomleft extends mover {
    Color col;
    fillbottomleft(int hh) {
      w = mover.WIDTH;
      h = hh;
      addMover(this, 0, mover.HEIGHT - hh);
      col = Color.black;
    }

    public void paint(Graphics g, int x1, int y1, int w1, int h1) {
      g.setColor(col);
      g.fillRect(x1,y1,w1,h1);
    }
  }
}
