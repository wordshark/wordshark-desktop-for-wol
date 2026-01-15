package shark;
import java.awt.*;

public class fish extends mover3d {
int y1,y2,x1,x2,a,b,c;
point3d_base t1,t2,t3,b1,b2,b3;
public double taz;
int tcosaz,tsinaz;
int mx,my;
int mh,mh1,mh2,mw,limitmh;
int mtopx,mtopz;           // top of mouth x
double mtopa,ma;    // angle to horiz of top of mouth,width/2
int tb,ta;           //top & left bounds of head
int fw,fh;           //width,height of head
point3d_base eye;
int eyeab;
int ex,ey,ez,pupilr,pupilr1;
double pupilangle;
Color eyecolor;
public Color mouthcolor;
Color savecolor,altcolor;

double topteetha[],botteetha[];
int topteethy[],botteethy[];
point3d_base topteeth[],botteeth[],lefteye[],righteye[],lefteye1[]=new point3d_base[4],righteye1[]=new point3d_base[4];
int leftx,lefty,rightx,righty;
int topx,topy,botx,boty;
int leftmx,leftmy,rightmx,rightmy;
short teethtot;
Graphics g;

public boolean happy,snapping,snapping1,snapping2,zooming;
int zstartzoom;
long endsnap;

long bloodend=-1;
long bloodstart;
public Polygon phead, phead2, ptail,pmouth;
public Polygon oldphead;

public fish(int x1i, int y1i, int z1i,         //pos
              int a1, int b1,int c1,   //size
              Color col,
              int mx1,int my1,       // dist from centre to mouth
              int teethtot1,          // no of teeth
              int tailx,int taily,
              int midx,int midy,
              int topfinwidth,
              int topfinheight,
              int bottomfinwidth,
              int bottomfinheight) {
   super();
   int red,green,blue,tot;
   color = altcolor =col;
   do {
      red = altcolor.getRed();
      green =altcolor.getGreen();
      blue =altcolor.getBlue();
      tot = (red+blue+green)*5/4;
      altcolor = new Color(red*red/tot,green*green/tot,blue*blue/tot);
   }while(altcolor.getRed() + altcolor.getGreen() + altcolor.getBlue() > 128);
   eyecolor = new Color(180,180,255);
   mouthcolor = new Color(164,0,0);
   a = a1; b=b1; c=c1;
   mx = mx1; my=my1;
   mw = (int) (c * Math.sqrt(1 - (double)my*my/b/b)- (double)mx*mx/a/a);
   mtopx = a*(int)Math.sqrt(1 - my*my/b/b);
   mtopz = c*(int)Math.sqrt(1 - my*my/b/b);
   mtopa = Math.atan2(my,mtopx);
   mh = a/4;   // to start with
   setupeyes(500);

   teethtot = (short)teethtot1;
   pupilr = eyeab/3 + u.rand(eyeab/3);
   limitmh = b-my-b/12;

   vary = new randrange_base[] {new randrange_base(limitmh/16,limitmh,b*2),
                           new randrange_base(0,100,200),   // pupil to edge
                           new randrange_base(0,10000,1000),  // pupil angle
                           new randrange_base(-c,c,c*4)}; // tail
   setupteeth();

   x1 = tailx;
   y1 = taily;
   x2 = midx;
   y2 = midy;
   rcentre = new point3d_base(x1i,y1i,z1i);
   w = a*2;h=a*2;
}
//-------------------------------------------------------------
void setupeyes(int maxangle) {
                        // allocate eyes
   int minab = Math.min(a,b);
   eyeab = minab/8 + u.rand(minab/8);
   int rad = 700 + u.rand(300);
   double angle = ((my==0?200:100)+u.rand(maxangle)) * Math.PI/2 /1000;
   ex = (int)((a-eyeab)*Math.cos(angle)*rad/1000);
   ey = (int)((b-eyeab)*Math.sin(angle)*rad/1000);
   ez = (int)(Math.sqrt(1 - ey*ey/b/b - ex*ex/a/a)*c);
   lefteye = new point3d_base[]{new point3d_base(ex-eyeab, -ey, -getez(ex-eyeab,ey)),
                           new point3d_base(ex, -ey-eyeab, -getez(ex,-ey-eyeab)),
                           new point3d_base(ex+eyeab, -ey, -getez(ex+eyeab,ey)),
                           new point3d_base(ex, -ey+eyeab, -getez(ex,-ey+eyeab))};
   righteye = new point3d_base[]{new point3d_base(ex+eyeab, -ey, getez(ex+eyeab,-ey)),
                           new point3d_base(ex, -ey-eyeab, getez(ex,-ey-eyeab)),
                           new point3d_base(ex-eyeab, -ey, getez(ex-eyeab,ey)),
                           new point3d_base(ex, -ey+eyeab, getez(ex,-ey+eyeab))};
}
//----------------------------------------------------------------
int getez(int x, int y) {
    return (int)(Math.sqrt(1 - (double)x*x/a/a - (double)y*y/b/b)*c);
}
//---------------------------------------------------------------
void setupteeth() {
   short i;
   int h = limitmh/4;
   ma = Math.atan2(mw,mx);
   double a2 = ma/(teethtot);
   topteetha  = new double[teethtot*2+1];
   botteetha  = new double[teethtot*2+1];
   topteethy = new int[teethtot];
   botteethy  = new int[teethtot];
   topteeth = new point3d_base[teethtot*2+1];
   botteeth = new point3d_base[teethtot*2+1];

   for(i=0;i<teethtot;++i) {
      topteethy[i] = botteethy[i] = h;
   }
   for(i=0;i<teethtot*2-1;++i) {
      topteetha[i] = botteetha[i] = a2;
   }
   vteeth(topteetha,a2/2,a2*4/3);
   vteeth(botteetha,a2/2,a2*4/3);
   vteeth(topteethy,h/2,h*4/3);
   vteeth(botteethy,h/2,h*4/3);
   double at1 = -ma,at2 = -ma;
   for(i=0;i<teethtot*2+1;++i) {
      topteetha[i] = (at1 += topteetha[i]);
      botteetha[i] = (at2 += botteetha[i]);
   }
}
//-------------------------------------------------------------
void vteeth(int t[],int tmin,int tmax) {
   short ct = teethtot;
   short i,j,k;
   int n;
   for(i=0;i<ct*2;++i) {
      j = u.rand(ct);
      k = u.rand(ct);
      if(j==k || t[j] < tmin || t[k] >tmax) {--i; continue;}
      t[j] -= (n = t[j]/8);
      t[k] +=  n;
   }
}
//-------------------------------------------------------------
void vteeth(double t[],double tmin,double tmax) {
   short ct = teethtot;
   short i,j,k;
   double n;
   for(i=0;i<ct*2;++i) {
      j = u.rand(ct);
      k = u.rand(ct);
      if(j==k || t[j] < tmin || t[k] >tmax) {--i; continue;}
      t[j] -= (n = t[j]/8);
      t[k] +=  n;
   }
}
//---------------------------------------------------------------
void transformTeeth() {
   short i,j;
   for(i = 0;i<teethtot;++i) {
      j = (short)(i*2);
      topteeth[j+1] = transform(new point3d_base((int)(mtopx*Math.cos(topteetha[j])),
                                          my+mh1 - (int)(mh1*Math.cos(Math.PI/2/ma * topteetha[j])) + topteethy[i],
                                          (int)(mtopz*Math.sin(topteetha[j]))));
      topteeth[j+2] = transform(new point3d_base((int)(mtopx*Math.cos(topteetha[j+1])),
                                          my+mh1 - (int)(mh1*Math.cos(Math.PI/2/ma * topteetha[j+1])),
                                          (int)(mtopz*Math.sin(topteetha[j+1]))));
      botteeth[j+1] = transform(new point3d_base((int)(mtopx*Math.cos(botteetha[j])),
                                          my+mh1 + (int)(mh2*Math.cos(Math.PI/2/ma * botteetha[j])) - botteethy[i],
                                          (int)(mtopz*Math.sin(botteetha[j]))));
      botteeth[j+2] = transform(new point3d_base((int)(mtopx*Math.cos(botteetha[j+1])),
                                          my+mh1 + (int)(mh2*Math.cos(Math.PI/2/ma * botteetha[j+1])),
                                          (int)(mtopz*Math.sin(botteetha[j+1]))));
   }
   topteeth[0] = botteeth[0] = transform(new point3d_base((int)(mtopx*Math.cos(-ma)),
                                        my+mh1,
                                        (int)(mtopz*Math.sin(-ma))));
   topteeth[teethtot*2] = botteeth[teethtot*2]
           = transform(new point3d_base((int)(mtopx*Math.cos(ma)),
                                        my+mh1,
                                        (int)(mtopz*Math.sin(ma))));
}
//---------------------------------------------------------------
void happyTeeth() {
   short i,j;
   int topy = b/2;
   int boty = b*2/3;
   int ty = b/4;
   for(i = 0;i<teethtot;++i) {
      j = (short)(i*2);
      topteeth[j+1] = transform(new point3d_base((int)(mtopx*Math.cos(topteetha[j])),
                                          (int)(ty+topy*Math.cos(Math.PI/2/ma * topteetha[j])) + topteethy[i],
                                          (int)(mtopz*Math.sin(topteetha[j]))));
      topteeth[j+2] = transform(new point3d_base((int)(mtopx*Math.cos(topteetha[j+1])),
                                           (int)(ty+topy*Math.cos(Math.PI/2/ma * topteetha[j+1])),
                                          (int)(mtopz*Math.sin(topteetha[j+1]))));
      botteeth[j+1] = transform(new point3d_base((int)(mtopx*Math.cos(botteetha[j])),
                                          (int)(ty+boty*Math.cos(Math.PI/2/ma * botteetha[j])) - botteethy[i],
                                          (int)(mtopz*Math.sin(botteetha[j]))));
      botteeth[j+2] = transform(new point3d_base((int)(mtopx*Math.cos(botteetha[j+1])),
                                           (int)(ty+boty*Math.cos(Math.PI/2/ma * botteetha[j+1])),
                                          (int)(mtopz*Math.sin(botteetha[j+1]))));
   }
   topteeth[0] = botteeth[0] = transform(new point3d_base((int)(mtopx*Math.cos(-ma)),
                                        ty,
                                        (int)(mtopz*Math.sin(-ma))));
   topteeth[teethtot*2] = botteeth[teethtot*2]
           = transform(new point3d_base((int)(mtopx*Math.cos(ma)),
                                        ty,
                                        (int)(mtopz*Math.sin(ma))));
}
//---------------------------------------------------------------
void newmove() {
   vary[3].fixedspeed=true;
   vary[3].currchange = vary[3].maxchange * movespeed/maxspeed;
}
public void endmove3() {
   vary[3].fixedspeed=false;
   vary[3].currchange /= 3;
}
//---------------------------------------------------------------
public void paintm(Graphics g1, long t) {
   short i;
   g = g1;
   if(happy) {
        savecolor = altcolor;
   }
   else savecolor = color;

   mh = vary[0].curr();
   pupilangle = vary[2].curr() * Math.PI * 2 / 1000;
   int ratio = vary[1].curr();
   short stop = -1;

   oldphead = (phead2 != null)?phead:null;
   taz = az * cosay/FACTOR;
   tcosaz = (int)(Math.cos(taz)*FACTOR);
   tsinaz = (int)(Math.sin(taz)*FACTOR);

   pupilr1 = pupilr * BASEZ/rcentre.z * screenmax/BASEU;
   if(zooming) adjustZoom();
   mh1 = mh/3; mh2 = mh-mh1;
   t1 = transform(new point3d_base(-x1,-y1,vary[3].currval));
   b1 = transform(new point3d_base(-x1,y1,vary[3].currval));
   t2 = transform(new point3d_base(-x2,-y2,0));
   b2 = transform(new point3d_base(-x2,y2,0));
   t3 = transform(new point3d_base(0,-b,0));
   b3 = transform(new point3d_base(0,b,0));
   ta = (int)(Math.sqrt((double)(a * cosay )*(a * cosay)
                +  (double)(c * sinay ) *( c * sinay)) / FACTOR);              // width

   if(happy) happyTeeth(); else transformTeeth();
   for(i=0;i<4;++i) {
      lefteye1[i] = transform(lefteye[i]);
      righteye1[i] = transform(righteye[i]);
   }
   int maxw =  Math.max(ta,b+mh/2) * 12 / 10 *  BASEZ/rcentre.z * screenmax/BASEU;
   xmax = Math.max(xmax, centre.x + maxw);
   ymax = Math.max(ymax, centre.y + maxw);
   xmin = Math.min(xmin, centre.x - maxw);
   ymin = Math.min(ymin, centre.y - maxw);
   g.setColor(savecolor);
   if(!snapping) adjust();
   topx = translatex(0,-b);
   topy = translatey(0,-b);
   botx = translatex(0,b);
   boty = translatey(0,b+(zooming?0:(mh/2)));
   leftx = translatex(-ta,0);
   lefty = translatey(-ta,0);
   rightx = translatex(ta,0);
   righty = translatey(ta,0);
   leftmx = translatex(-ta,my+mh1);
   leftmy = translatey(-ta,my+mh1);
   rightmx = translatex(ta,my+mh1);
   rightmy = translatey(ta,my+mh1);

   drawwithmouth();
   if(input != null) {
      int w = (topteeth[teethtot*2].x - topteeth[0].x);
      int h = (botteeth[teethtot/2*2].y - topteeth[teethtot/2*2].y);
      int x1 =  topteeth[0].x + w/6;
      int y1 =  topteeth[teethtot/2*2].y + h/4;
      input.paint(g,x1,y1,w*2/3,h/2,screenheight);
      togz = fromgz = Math.abs(togz);
      togy = fromgy = 0;
      togx = fromgx = 0;
   }
   if(snapping1 && endsnap < t) {
      endsnap();
   }
   else if(snapping2 && endsnap < t) {
      endsnap();
   }
   if(bloodend != -1) {
      drawblood(t,(topteeth[teethtot*2].x + topteeth[0].x)/2,
                  (botteeth[teethtot/2*2].y + topteeth[teethtot/2*2].y)/2,
                  screenwidth/8);
   }
}
//-----------------------------------------------------------------
void drawtail() {
   ptail = new Polygon(new int[]{t3.x,t2.x,t1.x,b1.x,b2.x,b3.x},
                      new int[]{t3.y,t2.y,t1.y,b1.y,b2.y,b3.y}, 6);
                     // draw tail
   g.fillPolygon(ptail);
   g.setColor(Color.black);
   g.drawPolyline(new int[]{t2.x,t1.x,b1.x,b2.x},
                      new int[]{t2.y,t1.y,b1.y,b2.y}, 4);
   g.setColor(savecolor);
}
//----------------------------------------------------------------
void drawhead()  {        // head without mouth
   pmouth = phead2 = null;
   phead = new Polygon();
       u.drawcurve(phead,
              botx,boty,taz,
              rightx, righty,taz-Math.PI/2);
       u.drawcurve(phead,
              rightx,righty,taz-Math.PI/2,
              topx,topy,taz-Math.PI);
       u.drawcurve(phead,
              topx, topy, taz-Math.PI,
              leftx, lefty, taz+Math.PI/2);
       u.drawcurve(phead,
              leftx,lefty,taz+Math.PI/2,
              botx,boty,taz);
       g.fillPolygon(phead);
       drawtail();
}
//----------------------------------------------------------------
void drawteeth(point3d_base pt[], short from, short to) {
   for(short i = from; i < to; i+=2) {
      g.fillPolygon(new int[] {pt[i].x, pt[i+1].x, pt[i+2].x},
                  new int[] {pt[i].y, pt[i+1].y, pt[i+2].y},3);
   }
}
//----------------------------------------------------------------
void drawmouth() {
   short i;
   Polygon p = new Polygon(),p2=new Polygon();
   for(i=0;i<=teethtot*2;i+=2) {
      p.addPoint(topteeth[i].x,topteeth[i].y);
      p2.addPoint(botteeth[i].x,botteeth[i].y);
   }
   g.setColor(mouthcolor);
   g.fillPolygon(p);
   g.fillPolygon(p2);
}
//----------------------------------------------------------------
void draweye(point3d_base eye[],boolean left) {
   if(eye[0].x < eye[2].x && eye[1].y < eye[3].y ) {
      int eyeab1 = eyeab * BASEZ / rcentre.z * screenmax / BASEU;
      int midx = (eye[0].x + eye[1].x + eye[2].x + eye[3].x)/4;
      int midy = (eye[0].y + eye[1].y + eye[2].y + eye[3].y)/4;
      Polygon p = new Polygon();
      g.setColor(eyecolor);
      double a1 = Math.atan2(eye[1].y-eye[3].y,eye[1].x-eye[3].x);
      double a2 = Math.atan2(eye[2].y-eye[0].y,eye[2].x-eye[0].x);
      u.drawcurve(p, eye[0].x, eye[0].y, a1,eye[1].x, eye[1].y, a2);
      u.drawcurve(p, eye[1].x, eye[1].y, a2,eye[2].x, eye[2].y, a1+Math.PI);
      u.drawcurve(p, eye[2].x, eye[2].y, a1+Math.PI,eye[3].x, eye[3].y, a2+Math.PI);
      u.drawcurve(p, eye[3].x, eye[3].y, a2+Math.PI,eye[0].x, eye[0].y, a1);
      g.fillPolygon(p);
      if(eye[2].x - eye[0].x > pupilr1
          && eye[3].y - eye[1].y > pupilr1) {
          double angle = (left?(Math.PI-pupilangle):pupilangle) + taz;
          int dist = findmaxpoly(p,midx,midy,angle);
          if(dist >0) {
             midx += (dist-pupilr1)*Math.cos(angle);
             midy += (dist-pupilr1)*Math.sin(angle);
             g.setColor(Color.black);
             g.fillOval(midx-pupilr1,midy-pupilr1,pupilr1*2,pupilr1*2);
          }
      }
   }
}
//-----------------------------------------------------------------
void  zoomeyes() {
   int w = eyeab * BASEZ / rcentre.z * screenmax / BASEU ;
   int h = w*5/8;
   int midx = topteeth[teethtot].x;
   int midy =  topy + (boty-topy)/8;
   int ex1 = c  * BASEZ / rcentre.z * screenmax / BASEU *5/12;
   int midy1 = centre.y-ey* BASEZ / rcentre.z * screenmax / BASEU;
   midy  =  midy1 + (midy - midy1)*(rcentre.z-zstartzoom)/(tocentre.z-zstartzoom);

   pupilr1 = Math.min(pupilr1,h);

   g.setColor(eyecolor);
   g.fillOval(midx-ex1-w, midy-h, w*2, h*2);
   g.fillOval(midx+ex1-w, midy-h, w*2, h*2);
   g.setColor(Color.black);
   g.fillOval(midx-ex1-pupilr1,midy-pupilr1,pupilr1*2,pupilr1*2);
   g.fillOval(midx+ex1-pupilr1,midy-pupilr1,pupilr1*2,pupilr1*2);
}

//---------------------------------------------------------------
void moveeye(point3d_base eye[],int x, int y) {
   for(short i=0;i<4;++i) {
      eye[i].x += x;
      eye[i].y += y;
   }
}
      //----------------------------------------------------------------
int findmaxpoly(Polygon p, int midx,int midy,double angle) {
   int rad = 2;
   int cosa = (int)(Math.cos(angle)*1000);
   int sina = (int)(Math.sin(angle)*1000);
   int x = midx;y=midy;

   while(p.contains(x, y)) {
     x = midx + rad*cosa/1000;
     y = midy + rad*sina/1000;
     rad+=2;
   }
   return rad-4;
}
//----------------------------------------------------------------
void drawwithmouth() {  // draw mouth as part of head
   g.setColor(savecolor);
   phead = new Polygon();
   phead2 = new Polygon();
   short i,tleft = -1, tright= (short)(teethtot*2),bleft = 0, bright= (short)(teethtot*2);
   double moutha;

   for(i=0;i<teethtot*2;i+=2) {
      if(topteeth[i].x < topteeth[i+2].x) {tleft = i;break;}
   }
   if(tleft==-1 || topteeth[teethtot/2*2].y > botteeth[teethtot/2*2].y)
                    {drawhead(); return;}
   drawtail();
   for(i=(short)(tleft+2);i<teethtot*2;i+=2) {
      if(topteeth[i].x >= topteeth[i+2].x) {tright = i;break;}
   }
   for(i=0;i<teethtot*2;i+=2) {
      if(botteeth[i].x < botteeth[i+2].x) {bleft = i;break;}
   }
   for(i=(short)(bleft+2);i<teethtot*2;i+=2) {
      if(botteeth[i].x >= botteeth[i+2].x) {bright = i;break;}
   }
   pmouth = new Polygon();
            //  top of head -----------------
   for(i=tleft;i<=tright;i+=2) {
      phead.addPoint(topteeth[i].x,topteeth[i].y);
      pmouth.addPoint(topteeth[i].x,topteeth[i].y);
   }
   if(tright == teethtot*2)
      phead.addPoint(rightmx, rightmy);
   else
      u.drawcurve(phead,
              topteeth[tright].x,topteeth[tright].y, taz-Math.PI/2,
              rightx, righty,taz-Math.PI/2);
   u.drawcurve(phead,
              rightx,righty,taz-Math.PI/2,
              topx,topy,taz+Math.PI);
   u.drawcurve(phead,
              topx, topy, taz+Math.PI,
              leftx, lefty, taz+Math.PI/2);
   if(tleft == 0)
        phead.addPoint(leftmx,leftmy);
   else
      u.drawcurve(phead,
              leftx,lefty,taz+Math.PI/2,
              topteeth[tleft].x,topteeth[tleft].y, taz+Math.PI/2);

           //  bottom of head -----------------
   for(i=bright;i>=bleft;i-=2) {
      pmouth.addPoint(botteeth[i].x,botteeth[i].y);
   }
   for(i=bleft;i<=bright;i+=2) {
      phead2.addPoint(botteeth[i].x,botteeth[i].y);
   }
   if(bright == teethtot*2)
      u.drawcurve(phead2,
              rightmx, rightmy, taz+Math.PI/2,
              botx,boty,taz+Math.PI);
   else
      u.drawcurve(phead2,
              botteeth[bright].x,botteeth[bright].y, taz+Math.PI/2,
              botx,boty,taz+Math.PI);
   if(bleft == 0)
        u.drawcurve(phead2,
              botx, boty, taz+Math.PI,
              leftmx,leftmy,taz-Math.PI/2);
   else
        u.drawcurve(phead2,
              botx, boty, taz+Math.PI,
              botteeth[bleft].x,botteeth[bleft].y, taz-Math.PI/2);

   if(ay < 0) drawmouth();
   g.setColor(Color.white);
   drawteeth(topteeth,tleft,tright);
   drawteeth(botteeth,bleft,bright);
   g.setColor(savecolor);
   g.fillPolygon(phead);
   g.fillPolygon(phead2);
   if(zooming) zoomeyes();
   else {
      draweye(lefteye1,true);
      draweye(righteye1,false);
   }
   }
//----------------------------------------------------------------

void adjx(int dx) {
   short i;
   t1.x += dx;
   b1.x += dx;
   t2.x += dx;
   b2.x += dx;
   t3.x += dx;
   b3.x += dx;
   leftx += dx;
   rightx += dx;
   for(i=0;i<4;++i) {
      lefteye1[i].x += dx;
      righteye1[i].x += dx;
   }
   centre.x += dx;
   xmin += dx; xmax+=dx;
   for(i = 1;i<teethtot*2;++i) {
      topteeth[i].x += dx;
      botteeth[i].x += dx;
   }
   topteeth[0].x += dx;
   topteeth[teethtot*2].x += dx;
}
//----------------------------------------------------------------
void adjy(int dy) {
   short i;
   t1.y += dy;
   b1.y += dy;
   t2.y += dy;
   b2.y += dy;
   t3.y += dy;
   b3.y += dy;
   lefty += dy;
   righty += dy;
   for(i=0;i<4;++i) {
      lefteye1[i].y += dy;
      righteye1[i].y += dy;
   }
   centre.y += dy;
   for(i = 1;i<teethtot*2;++i) {
      topteeth[i].y += dy;
      botteeth[i].y += dy;
   }
   topteeth[0].y += dy;
   topteeth[teethtot*2].y += dy;
   ymin += dy; ymax+=dy;
}
//---------------------------------------------------------------
void adjustZoom() {
      mh = limitmh+ (b*3/2-limitmh)*(rcentre.z-zstartzoom)/(tocentre.z-zstartzoom);
      my = Math.min(my, b - b/12 - mh);
}
//---------------------------------------------------------------
void adjust() {
   if(xmax-xmin < screenwidth) {
      if(xmin<0)  adjx(-xmin);
      if(xmax>=screenwidth) adjx(-(xmax-screenwidth+1));
   }
   if(ymax-ymin < screenheight) {
      if(ymin<0)  adjy(-ymin);
      if(ymax>=screenheight) adjy(-(ymax-screenheight+1));
   }
}
//----------------------------------------------------------------
int translatex(int x, int y) {
   return  centre.x + (x*tcosaz-y*tsinaz)/FACTOR * BASEZ/rcentre.z * screenmax/BASEU;
}
//----------------------------------------------------------------
int translatey(int x, int y) {
   return  centre.y + (x*tsinaz+y*tcosaz)/FACTOR * BASEZ/rcentre.z * screenmax/BASEU;
}
//-----------------------------------------------------------------
public void snap1(int x, int y, int time) {
   byte speed = (manager != null && manager.currgame != null)? manager.currgame.speed:5;
   int newz = rcentre.z * 7/8;
   x = convertx(x, newz );
   y = converty(y, newz);
   long t = sharkGame.gtime();
   y -=  my + limitmh/2;
   time = time * 4 / speed;
   snapping = snapping1 = true;
   endsnap = t+time;
   vary[0].fixed = true;
   vary[0].target = vary[0].maxval;
   vary[0].lastt = t;
   moveto(x,y,newz,time);
   randmove = false;
}
//-------------------------------------------------------------
public void snap2(int time) {
   long t = sharkGame.gtime();
   ray=-Math.PI/2;
   raz=0;
   snapping1 = false;
   snapping = snapping2 = true;
   endsnap = t+time;
   vary[0].fixed = true;
   vary[0].target = vary[0].minval;
   vary[0].currchange = -(vary[0].maxval - vary[0].minval)/time;
   vary[0].lastt = t;
   randmove = false;
}
//-----------------------------------------------------------
void endsnap() {
   if(snapping1) {
      rotateto(0,-Math.PI/2,0,500);
   }
   snapping = snapping1 = snapping2 = false;
   vary[0].fixed = false;
   vary[0].currchange = 0;
   randmove = true;
}
//-----------------------------------------------------------
public void fillscreen(int time) {
  int screenmax = Math.max(manager.screenwidth,manager.screenheight);
  int z1 = c *  2 * BASEZ / BASEU * screenmax  / manager.screenwidth;
  int z2 = b *  5/3 * BASEZ / BASEU * screenmax  / manager.screenheight;
  ray = Math.min(-Math.PI/3,Math.max(-Math.PI*2/3,ray));
  raz = Math.min(Math.PI/3,Math.max(-Math.PI/3,raz));
  rax = Math.min(Math.PI/3,Math.max(-Math.PI/3,rax));
  zooming = true;
  zstartzoom = rcentre.z;
  my = Math.min(b/4,my);
  limitmh = b-my-b/12;
  randmove = false;
  vary[0].fixed = true;
  vary[0].target = vary[0].maxval;
  vary[0].lastt = sharkGame.gtime();
  moveto(BASEU/2,BASEU/2,Math.max(z1,z2)*6/5,time);
}
//-----------------------------------------------------------------
public void pointforward() {
  ray = Math.min(-Math.PI*5/12,Math.max(-Math.PI*7/12,ray));
  raz = Math.min(Math.PI/12,Math.max(-Math.PI/12,raz));
  rax = Math.min(Math.PI/12,Math.max(-Math.PI/12,rax));
}
//-----------------------------------------------------------
public void blood(long t, int limit) {
    bloodend = t+limit;
    bloodstart =  t;
}
//------------------------------------------------------------
void drawblood(long t,int x1, int y1,int maxrad) {
   if(t >= bloodend) {
      bloodend = -1;
   }
   else {
      g.setColor(Color.red);
      g.fillPolygon(u.randpolygon(x1,y1,maxrad/4+(int)(maxrad*3/4*(t-bloodstart)/(bloodend-bloodstart))));
   }
   xmax = Math.max(xmax,x1+maxrad);
   xmin = Math.min(xmax,x1-maxrad);
   ymax = Math.max(ymax,y1+maxrad);
   ymin = Math.min(ymax,y1-maxrad);
 }
//---------------------------------------------------------------
public boolean inmouth(int mx, int my) {
  int i;
  if(ay > 0)  return false;
  if(mx < topteeth[0].x || mx > topteeth[teethtot*2].x
     ||my < topteeth[teethtot/2*2].y || my >botteeth[teethtot/2*2].y)
         return false;
  return (pmouth.contains(mx,my));
//  Polygon p = new Polygon();
//  for(i=0;i<teethtot*2;i+=2) {
//    p.addPoint(topteeth[i].x, topteeth[i].y);
//  }
//  for(;i>0;--i) {
//    p.addPoint(botteeth[i].x, botteeth[i].y);
//  }
//  return p.contains(mx,my);
}
//------------------------------------------------------------
public boolean isOver(int mx,int my) {
//   mx = convertx(mx*screenwidth/mover.WIDTH, rcentre.z );
//   my = converty(my*screenheight/mover.HEIGHT, rcentre.z);
   return phead != null && phead.contains(mx,my)
          || phead2 != null && phead2.contains(mx,my)
          || ptail != null && ptail.contains(mx,my)
          || pmouth != null && pmouth.contains(mx,my);
}
}
