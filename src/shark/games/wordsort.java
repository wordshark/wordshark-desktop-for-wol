package shark.games;

import java.util.*;

import java.awt.*;

import shark.*;

public class wordsort extends  sharkGame {//SS 03/12/05
   short items[],find[],char2tots[],start2pos[];
   short curr=-1,curr2,addscore;
   String diclist[],diclist2[];
   short dic[];
   short dictot;
   short currtest,currdic;
   String letterlist,letterlist2;
   String currstring;
   boolean wantsingle,wantpair,want2words,wantfinal;
   mover oldmessage;
   int slotwidth,slotheight;
   int startx,starty;
   short across,down;
   static final int ITEMTOT  = 100,ITEMFILL = 10, DOWN = 16;
   moveword currmover;
   short itemtot,findtot;
   long lastflip;
   moveword mw[];
   boolean outoforder,haderror;
   Color colors[];
   int target;
   long wantnext;

   public wordsort() {
    errors = true;
    gamescore1 = true;
//    peeps = true;
//    listen= true;
//    peep = true;
    wantspeed = false;
    forceSharedColor = true;
    diclist2 = db.list(sharkStartFrame.getPrimarySoundDb(sharkStartFrame.publicSoundLib),db.WAV);
    diclist2 = u.filterIrregularWords(diclist2);
    diclist = spellchange.spellchange(diclist2);
    dic = new short[diclist.length];
    gamePanel.setBackground(background = Color.black);
    optionlist = new String[] {"dicfish_3letters","dicfish_4letters","dicfish_5letters","dicfish_6letters","dicfish_7letters","dicfish_8letters"};
//    opnarr = new String[] {"3-letter words","4-letter words","5-letter words","6-letter words","7-letter words","8-letter words"};
    help("help_sortwords");
    buildTopPanel();
    gamePanel.showSprite = false;
    gamePanel.clearWholeScreen = true;
    setup1();
  }
   //------------------------------------------------------------------
   void setup1() {
    short i,j,k,len1=-1;
    boolean lookbefore,lookafter;
    letterlist = new String();
    char ch;
    findtot=0;
    boolean want[] = new boolean[9];
    want[3] =  options.option(optionlist[0]);
    want[4] =  options.option(optionlist[1]);
    want[5] =  options.option(optionlist[2]);
    want[6] =  options.option(optionlist[3]);
    want[7] =  options.option(optionlist[4]);
    want[8] =  options.option(optionlist[5]);
    if(!want[3] &&  !want[4] && !want[5] && !want[6] && !want[7] && !want[8]) {
       want[3] = want[4] = want[5] = want[6] = want[7] = want[8] = true;
    }
    markoption();

    for(i=0,dictot=0;i<diclist.length;++i) {
        j = (short)diclist[i].length();
        if(j<9 && want[j] && u.onlylower(diclist[i]))  {
           dic[dictot++] = i;
           if(len1<0 || letterlist.charAt(len1) != diclist[i].charAt(0))  {
              letterlist = letterlist + diclist[i].substring(0,1);
              ++len1;
           }
        }
    }
    ++len1;
    int tots[] = new int[len1];
    short startpos[] = new short[len1];
    int pos = -1;
    int maxtot=0;
    char thischar,lastchar = 0;

    // get a list of initial letters that have over or equal to ITEMTOT or
    // the 4 initial letters with the most words - one of which to be chosen randomly
    int maxno = 4;
    int maxwords[] = new int[maxno];
    int qualifyingwordnos[] = new int[]{};
    for(i=0;i<dictot;++i) {
       thischar = diclist[dic[i]].charAt(0);
       if(thischar != lastchar) {
          if(pos>=0){
              if(tots[pos] < ITEMTOT){
                 int lowest = Integer.MAX_VALUE;
                 int lowindex = 0;
                 for(int n = 0; n < maxwords.length; n++){
                     if(maxwords[n] < lowest) lowest = maxwords[lowindex = n];
                 }
                 if(tots[pos]>maxwords[lowindex]){
                     maxwords[lowindex]=tots[pos];
                 }
              }
              else qualifyingwordnos = u.addint(qualifyingwordnos, tots[pos]);
          }
          startpos[++pos] = i;
          lastchar = thischar;
       }
       ++tots[pos];
       maxtot = Math.max(maxtot,tots[pos]);
    }
    if(tots[pos] < ITEMTOT){
        int lowest = Integer.MAX_VALUE;
        int lowindex = 0;
        for(int n = 0; n < maxwords.length; n++){
            if(maxwords[n] < lowest) lowest = maxwords[lowindex = n];
        }
        if(tots[pos]>maxwords[lowindex]){
            maxwords[lowindex]=tots[pos];
        }
    }
    else qualifyingwordnos = u.addint(qualifyingwordnos, tots[pos]);
    qualifyingwordnos = u.addint(qualifyingwordnos, maxwords);
    char start;
    do {
       start = diclist[dic[u.rand(dictot)]].charAt(0);
//    } while(tots[pos = letterlist.indexOf(start)] < Math.min(ITEMTOT,maxtot));
    } while(!u.inlist(qualifyingwordnos, tots[pos = letterlist.indexOf(start)]));
    short endpos = (short)(startpos[pos]+tots[pos]);
    short startfrom = startpos[pos];
    lastchar = 0;
    letterlist2 = new String();
    for(i = startfrom; i < endpos;++i) {
       thischar = diclist[dic[i]].charAt(1);
       if(thischar != lastchar) {
          letterlist2 = letterlist2 + thischar;
          lastchar = thischar;
       }
    }
    tots = new int[letterlist2.length()];
    start2pos = new short[letterlist2.length()];
    char2tots = new short[letterlist2.length()];
    lastchar = 0;
    for(i = startfrom,pos=-1; i < endpos;++i) {
       thischar = diclist[dic[i]].charAt(1);
       if(thischar != lastchar) {
          start2pos[++pos] = i;
          lastchar = thischar;
       }
       ++tots[pos];
    }
    itemtot = (short)Math.max(Math.min(endpos-startfrom,ITEMTOT),letterlist2.length());
    items =  new short[itemtot];
    find = new short[itemtot];
    for(i=j=0;i<letterlist2.length();++i) {
       find[findtot++] = items[j++] = (short)(start2pos[i] + u.rand(tots[i]));
       ++char2tots[i];
       if(tots[i] > 1) {
          do {
             k = (short)(start2pos[i] + u.rand(tots[i]));
          }while (k == items[j-1]);
          items[j++] = k;
          ++char2tots[i];
       }
    }
    i=j;
    outloop: while(i<itemtot) {
       items[i] = (short)(startfrom + u.rand(endpos-startfrom));
       for(j=0;j<i;++j) if(items[j] == items[i]) continue outloop;
       for(j=0,k=startfrom; j<letterlist2.length(); ++j) {
           if((k+=tots[j]) > items[i]) {++char2tots[j]; break;}
       }
       ++i;
    }
    Arrays.sort(items);
    loop2: while(findtot < ITEMFILL && findtot < itemtot/3) {
       k = u.rand(itemtot);
       ch = diclist[dic[items[k]]].charAt(1);
       lookbefore = k>0;
       lookafter = k<itemtot-1;
       for(i = 0; i < findtot; ++i) {
          if(find[i] == items[k]
           || lookbefore &&  find[i] == items[k-1]
           || lookafter && find[i] == items[k+1])
              continue loop2;
       }
       find[findtot++]  = items[k];
    }
    short[] newfind = new short[findtot];
    System.arraycopy(find,0,newfind,0,findtot);
    find = newfind;
    mw = new moveword[itemtot];
    colors = new Color[letterlist2.length()];
    colors[0] = u.backgroundcolor(null);
    for(i=1;i<colors.length;++i) {
       colors[i] = u.backgroundcolor(new Color[] {colors[i-1]});
    }
    down = (short)DOWN;
    across = (short)(itemtot/down + 1);
    int slack = across*down - itemtot;
    slotwidth = mover.WIDTH/(across);
    slotheight = (screenheight/(down+1))*mover.HEIGHT/screenheight;
    int yinc = slotheight-mover.HEIGHT/screenheight*5/4;
    int ystart = u.rand(slack * yinc);
    int xstart = 0;
    int yextra = mover.HEIGHT/screenheight*3;
    int top=0;
    wordfont = sizeFont(null,"mmmmaaaa",slotwidth,slotheight);
    Arrays.sort(find);
    for(i=j=0;i<itemtot;++i) {
       while(j<findtot && items[i] > find[j]) ++j;
       if(j>=findtot || items[i] != find[j])
             mw[i] = new moveword(diclist[dic[items[i]]],xstart,ystart,true,false);
       else  mw[i] = new moveword(diclist[dic[items[i]]],xstart,ystart,false,false);

       ystart+=yinc;
       if(ystart > mover.HEIGHT - slotheight) {
          if(xstart == 0) {
             top = ystart = ystart - down * slotheight;
          }
          else ystart = top;
          xstart += slotwidth;
       }
       else {
          if(i<itemtot-1 && diclist[dic[items[i+1]]].charAt(1) !=  diclist[dic[items[i]]].charAt(1))
                ystart += yextra;
       }
    }
    u.shuffle(find);
    wantnext = gtime()+500;
  }
 //--------------------------------------------------------------
 public void restart() {
    gamePanel.removeAllMovers();
    if(currmover != null) gamePanel.finishwith(currmover);
    super.restart();
    curr = -1;
    setup1();
 }
 //--------------------------------------------------------------
 public void afterDraw(long t){
   if (wantnext != 0 && gtime>wantnext) {
     wantnext = 0;
     nextword();
   }
 }
 //---------------------------------------------------------------------
 void nextword() {
   int i;
//startPR2005-04-18^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    if(Demo_base.isDemo){
      if (Demo_base.demoIsReadyForExit(1)) return;
    }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    haderror=false;
    if(++curr <findtot) {
        curr2 = find[curr];
        for(i=0;i<itemtot;++i) if(items[i] == curr2)     break;
        target = i;
        currmover = new moveword(mw[i].val,mw[i].targetx,mw[i].targety,false,true);
        spokenWord.findandsay(diclist2[dic[curr2]]);
        addMover(currmover,0,0);
  //      if(delayedflip && !completed){
        if(curr>0)
            flip();
  //      }
    }
    else {
       score(Math.min(gametot1,6));
       clearexit = true;
       exitbutton(mover.HEIGHT/2);
    }
 }
 //----------------------------------------------------------------------
 public boolean click(int mx, int my) {
    if(completed) return true;
    int slot = currmover.overslot();
    if(slot < 0) { return true;}
    int i;
    if(slot != target)  {
       noise.groan();
       error();
       haderror = true;
       return true;
    }
    else if(!haderror) gamescore(1);
    for(i=0;i<itemtot;++i) if(items[i] == curr2)
       {mw[i].filled = true; mw[i].ended = false; break;}
    gamePanel.finishwith(currmover);
    currmover = null;
    wantnext = gtime+500;
    return  true;
 }
 //------------------------------------------------------------
 class moveword extends mover {
    String val;
    Color color;
    int targetx,targety,w2;
    boolean filled;
    moveword(String v,int x1,int y1,boolean fixed,boolean ismover)  {
       super(false);
       val = v;
       filled = fixed;
       targetx = x1;
       targety = y1;
       if(ismover) w = (metrics.stringWidth(val)+2)*mover.WIDTH/screenwidth;
       else {
          w2 = Math.min(slotwidth - mover.WIDTH*5/4/screenwidth, metrics.stringWidth(val + "aa")*mover.WIDTH/screenwidth);
          if(!filled ) w = slotwidth - mover.WIDTH*5/4/screenwidth;
          else w = w2;
       }
       h = slotheight;
       color = colors[letterlist2.indexOf(val.charAt(1))];
       if(ismover) {
          this.mx = - w/2;
          this.my = - h/2;
          gamePanel.moveWithMouse(this);
          gamePanel.mousemoved(0,0);
       }
       else {
          addMover(this,targetx,targety);
       }
    }
    int overslot() {
       for(short i=0;i<itemtot;++i) {
          if(!mw[i].filled
             && x >= mw[i].x && x+w <= mw[i].x + mw[i].w
             &&  Math.abs(y - mw[i].y) < slotheight/3)
            return i;
       }
       return -1;
    }
    boolean overblack() {
       for(short i=0;i<itemtot;++i) {
          if( x+w > mw[i].x && x < mw[i].x + mw[i].w
             && Math.abs(y - mw[i].y) < slotheight) return false;
       }
       return true;
    }
    public void paint(Graphics g,int x,int y,int w,int h) {
        if(shark.macOS) ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
          String s;
          int ww;
          int x1=x + w/2 - metrics.stringWidth(val)/2, y1=y+h/2-metrics.getHeight()/2+metrics.getMaxAscent();
          g.setFont(wordfont);
          if(gamePanel.movesWithMouse(this)) {
             if(overblack()) g.setColor(Color.white);
             else if(overslot()>=0) g.setColor(Color.black);
             else g.setColor(Color.gray);
             keepMoving=true;
             g.drawString(val, x1, y1);
             g.setPaintMode();
          }
          else {
             keepMoving=false;
             g.setColor(color);
             if(filled) {
                g.fillRect(x,y,ww=w2*screenwidth/mover.WIDTH,h+1);
                g.setColor(Color.red);
                g.drawString(s=val.substring(0,2),x1=x + ww/2 - metrics.stringWidth(val)/2,
                   y1=y+h/2-metrics.getHeight()/2+metrics.getMaxAscent());
                g.setColor(Color.black);
                g.drawString(val.substring(2),x1 + metrics.stringWidth(s),y1);
             }
             else g.fillRect(x,y,w,h+1);
          }
    }
 }
}
