package shark;

import java.awt.*;

public class mover3d extends mover {
double ax,ay,az;    // rotation  relative to screen
double rax,ray,raz;  // rotation relative to master
double fromax,toax,fromay,toay,fromaz,toaz;
int fromgx,fromgy,fromgz,togx,togy,togz;
public long fixuntil;
long  startrottime;
int rottime;
int cosax,sinax,cosay,sinay,cosaz,sinaz;
static final short FACTOR=1024;
public static final int BASEU=0x00004000, BASEZ = BASEU*4;
int xmin,ymin,xmax,ymax;
int screenwidth,screenheight;
public int screenmax=600;
int xmousex,xmousey;
int movespeed,maxspeed;
randrange_base vary[];
public boolean straight;   // move in straight line

point3d_base centre = new point3d_base(0,0,0);         // centre in screen co-ords
public point3d_base fromcentre,tocentre,lastcentre1,lastcentre2;
int movetime;

//int speed;            // speed in direction of front(original x-axis)
                       // units are mover_WIDTH units per second
//int topspeed;          // vary speed up to this

public point3d_base rcentre;      // centre in BASEU units

public boolean randrotate,randmove,keeprotate,keepmove;

public int minx,maxx,miny,maxy,minz,maxz;


Color color;

long movestarttime,rotstarttime;

//------------------------------------------------------------
public mover3d() {
   super(false);
   minx = miny = 0;
   maxz = BASEZ*3;
   maxx = BASEU;
   maxy = BASEU;
   minz = BASEZ*4/3;
}
//----------------------------------------------------------------
public point3d_base transform(point3d_base p) {
   int start;
   int y = (p.y*cosax-p.z*sinax)/FACTOR;
   int z = (p.y*sinax+p.z*cosax)/FACTOR;
   int x = (p.x*cosaz-y*sinaz)/FACTOR;
   int y2 = (p.x*sinaz+y*cosaz)/FACTOR;
   int x2 = (x*cosay-z*sinay)/FACTOR;
   int z2 = (x*sinay+z*cosay)/FACTOR  + rcentre.z;
   z2 = Math.max(1,z2);
   x2 = (x2+rcentre.x - manager.viewerx) * BASEZ/z2 + manager.viewerx;
   y2 = (y2+rcentre.y - manager.viewery) * BASEZ/z2 + manager.viewery;
   if(screenwidth>screenheight) {
      start = (screenwidth-screenheight)/2;
      x2 =  x2*screenwidth/BASEU;
      y2 =  y2*screenwidth/BASEU - start;
   }
   else {
      start = (screenheight-screenwidth)/2;
      x2 =  x2*screenheight/BASEU - start;
      y2 =  y2*screenheight/BASEU;
   }
   xmax = Math.max(x2+1,xmax);
   xmin = Math.min(x2-1,xmin);
   ymax = Math.max(y2+1,ymax);
   ymin = Math.min(y2-1,ymin);

   return new point3d_base(x2,y2,z2);
}
//----------------------------------------------------------------
public point3d_base transform(int x1, int y1, int z1) {
   int start;
   int y = (y1*cosax-z1*sinax)/FACTOR;
   int z = (y1*sinax+z1*cosax)/FACTOR;
   int x = (x1*cosaz-y*sinaz)/FACTOR;
   int y2 = (x1*sinaz+y*cosaz)/FACTOR;
   int x2 = (x*cosay-z*sinay)/FACTOR;
   int z2 = (x*sinay+z*cosay)/FACTOR  + rcentre.z;
   z2 = Math.max(1,z2);
   x2 = (x2+rcentre.x - manager.viewerx) * BASEZ/z2 + manager.viewerx;
   y2 = (y2+rcentre.y - manager.viewery) * BASEZ/z2 + manager.viewery;
   if(screenwidth>screenheight) {
      start = (screenwidth-screenheight)/2;
      x2 =  x2*screenwidth/BASEU;
      y2 =  y2*screenwidth/BASEU - start;
   }
   else {
      start = (screenheight-screenwidth)/2;
      x2 =  x2*screenheight/BASEU - start;
      y2 =  y2*screenheight/BASEU;
   }
   xmax = Math.max(x2+1,xmax);
   xmin = Math.min(x2-1,xmin);
   ymax = Math.max(y2+1,ymax);
   ymin = Math.min(y2-1,ymin);

   return new point3d_base(x2,y2,z2);
}
//----------------------------------------------------------
public point3d_base[] transform(point3d_base in[]) {
   point3d_base ret[] = new point3d_base[in.length];
   for(int i=0;i<in.length;++i) {
      ret[i] = transform(in[i]);
   }
   return ret;
}
//----------------------------------------------------------
public Polygon getPolygon(point3d_base in[]) {
   Polygon ret = new Polygon();
   point3d_base pp;
   for(int i=0;i<in.length;++i) {
      pp = transform(in[i]);
      ret.addPoint(pp.x,pp.y);
   }
   return ret;
}
//----------------------------------------------------------
public Polygon transformPolygon(Polygon p, int zval) {
   point3d_base p3d;
   Polygon p2 = new Polygon();
   for(int i=0;i<p.npoints;++i) {
      p3d = transform(new point3d_base(p.xpoints[i],p.ypoints[i], zval));
      p2.addPoint(p3d.x,p3d.y);
   }
   return p2;
}
//----------------------------------------------------------
public point3d_base[] transform(Polygon p, int zval) {
   point3d_base ret[] = new point3d_base[p.npoints];
   for(int i=0;i<p.npoints;++i) {
      ret[i] = transform(new point3d_base(p.xpoints[i],p.ypoints[i], zval));
   }
   return ret;
}
//-----------------------------------------------------------
public void paint3d(Graphics g, point3d_base[] p) {
   for(int i=0; i<p.length-1; ++i) {
        g.drawLine(p[i].x,p[i].y,p[i+1].x,p[i+1].y);
   }
}
//-----------------------------------------------------------
public void paint(Graphics g) {
   int i,start;
   long t = sharkGame.gtime();
   boolean changed = move(t);
   Polygon p;

   if(changed || !drawn) {
      screenwidth = manager.screenwidth;
      screenheight = manager.screenheight;
      screenmax = Math.max(screenwidth, screenheight);
      xmousex = (manager.mousex*screenwidth/WIDTH - screenwidth/2)
                * BASEU  / screenmax + BASEU/2;
      xmousey = (manager.mousey*screenheight/HEIGHT - screenheight/2)
                * BASEU  / screenmax + BASEU/2;
      xmax = -0x0fffffff; xmin=0x0fffffff;
      ymax = -0x0fffffff; ymin=0x0fffffff;
      ax = rax; ay=ray; az=raz;
      centre.x = (rcentre.x - manager.viewerx) * BASEZ/rcentre.z + manager.viewerx;
      centre.y = (rcentre.y - manager.viewery) * BASEZ/rcentre.z + manager.viewery;
      centre.z = rcentre.z;
      if(screenwidth>screenheight) {
          start = (screenwidth-screenheight)/2;
          centre.x =  centre.x*screenwidth/BASEU;
          centre.y =  centre.y*screenwidth/BASEU - start;
      }
      else {
         start = (screenheight-screenwidth)/2;
         centre.x =  centre.x*screenheight/BASEU - start;
         centre.y =  centre.y*screenheight/BASEU;
      }
      x = mover3d.BASEU;
      y = mover3d.BASEU;
      cosax = (int)(Math.cos(ax)*FACTOR);
      sinax = (int)(Math.sin(ax)*FACTOR);
      cosay = (int)(Math.cos(ay)*FACTOR);
      sinay = (int)(Math.sin(ay)*FACTOR);
      cosaz = (int)(Math.cos(az)*FACTOR);
      sinaz = (int)(Math.sin(az)*FACTOR);
   }
   paintm(g,t);
   w = (xmax-xmin+1) * mover.WIDTH/screenwidth;
   x = xmin * mover.WIDTH/screenwidth;
   h = (ymax-ymin+1)* mover.HEIGHT/screenheight;
   y = ymin * mover.HEIGHT/screenheight;
   ended = false;
}
//---------------------------------------------------------
public void saveimage() {
   int ww,hh;
   w = (ww=xmax-xmin+1) * mover.WIDTH/screenwidth;
   x = xmin * mover.WIDTH/screenwidth;
   h = (hh=ymax-ymin+1)* mover.HEIGHT/screenheight;
   y = ymin * mover.HEIGHT/screenheight;

   savex = Math.max(0,xmin);
   savey = Math.max(0,ymin);
   savew = Math.max(0,Math.min(xmin+ww,screenwidth)-savex);
   saveh = Math.max(0,Math.min(ymin+hh,screenheight)-savey);
   if(save == null ||save.getWidth(manager) != ww || save.getHeight(manager) != hh) {
           save = manager.createImage(ww, hh);
   }
   if(xmin < screenwidth && xmin + ww > 0 && ymin < screenheight && ymin+hh > 0) {
      save.getGraphics().drawImage(manager.offscreen[manager.curroffscreen], 0, 0, savew, saveh,
                   savex, savey, savex+savew, savey+saveh, manager);
   }
}
//---------------------------------------------------------
void paintm(Graphics g, long t) {}
//---------------------------------------------------------------
public boolean move(long t) {
   int elapsed, wk;
   short i;
   boolean changed = false;
   screenwidth = manager.screenwidth;
   screenheight = manager.screenheight;
   screenmax = Math.max(screenwidth, screenheight);
   if(vary != null) {
      changed = true;
      for(i=0;i<vary.length;++i) {
         vary[i].next(t);
      }
   }
   if(rottime !=0) {
      changed = true;
      elapsed = (int)(t - startrottime);
      if(rottime <= elapsed) {
         rax = toax;
         ray = toay;
         raz = toaz;
         if(keeprotate) rotateto(fromax,fromay,fromaz,rottime);
         else {
            rottime = 0;
         }
      }
      else {
         rax = (fromax*(rottime-elapsed) + toax*elapsed) /rottime;
         ray = (fromay*(rottime-elapsed) + toay*elapsed) /rottime;
         raz = (fromaz*(rottime-elapsed) + toaz*elapsed) /rottime;
      }
   }
   if(movetime != 0 &&  rottime == 0)  {
      changed = true;
      elapsed = (int)(t - movestarttime);
      if(movetime <= elapsed) {
         rcentre = tocentre;
         if(keepmove) moveto(fromcentre,movetime);
         else {
             wk = (int)Math.sqrt((tocentre.x - lastcentre2.x)*(tocentre.x - lastcentre2.x)
                      + (tocentre.z - lastcentre2.z)*(tocentre.z - lastcentre2.z));
             ray = Math.atan2(tocentre.z - lastcentre1.z,
                             tocentre.x - lastcentre1.x);
             raz = Math.atan2(tocentre.y - lastcentre2.y, wk);
             raz = Math.max(-Math.PI/4,Math.min(Math.PI/4,raz));
             ray = Math.min(-Math.PI*3/8,Math.max(-Math.PI*5/8,ray));
             movetime = 0;
             if(!randmove) endmove3();
         }
      }
      else {
         point3d_base newcentre = new point3d_base(
            interpolate(fromcentre.x,tocentre.x, fromgx, togx,elapsed,movetime),
            interpolate(fromcentre.y,tocentre.y, fromgy, togy,elapsed,movetime),
            interpolate(fromcentre.z,tocentre.z, fromgz, togz,elapsed,movetime));
         newcentre.z = Math.min(maxz,newcentre.z);
         if(t > fixuntil && Math.abs(newcentre.x - lastcentre1.x) + Math.abs(newcentre.z - lastcentre1.z) >6) {
            ray = Math.atan2(newcentre.z - lastcentre1.z,
                             newcentre.x - lastcentre1.x);
            lastcentre1 = newcentre;
         }
         if(t > fixuntil && Math.abs(newcentre.y - lastcentre2.y)
             + (wk = (int)Math.sqrt((newcentre.x - lastcentre2.x)*(newcentre.x - lastcentre2.x)
                      + (newcentre.z - lastcentre2.z)*(newcentre.z - lastcentre2.z))) > 6) {
            raz = Math.atan2(newcentre.y - lastcentre2.y, wk);
            raz = Math.max(-Math.PI/4,Math.min(Math.PI/4,raz));
            rax = 0;
            lastcentre2 = newcentre;
         }
         if(t<=fixuntil) {
            lastcentre1 = lastcentre2 = newcentre;
         }
         rcentre = newcentre;
     }
   }
   if(randmove && movetime == 0) {
      changed = true;
      nextmove();
   }
   return changed;
}
//--------------------------------------------------------
void nextmove() {
      byte speed = (manager != null && manager.currgame != null)? manager.currgame.speed:5;
      int newz = minz+u.rand(maxz-minz);
      int allow =  (BASEU/2-w*rcentre.z/BASEZ/2)*newz/BASEZ;
      minx = BASEU/2 - allow*screenwidth/screenmax;
      maxx = BASEU/2 + allow*screenwidth/screenmax;
      miny = BASEU/2 - allow*screenheight/screenmax;
      maxy = BASEU/2 + allow*screenheight/screenmax;
      moveto(new point3d_base(minx + u.rand(maxx-minx),
                         miny + u.rand(maxy-miny),
                         newz),(1000+u.rand(4000))* 8 / (speed+4));
}
//-------------------------------------------------------------
int interpolate(int x1, int x2, int g1, int g2, int etime, int ttime) {
  int rtime = ttime-etime;
  if(straight) return (x1*rtime + x2*etime)/ttime;
  return ((x1+g1*etime/ttime)*rtime + (x2-g2*rtime/ttime)*etime) / ttime ;
}

//---------------------------------------
   public void moveto(int x1, int y1, int z1,int time) {
     moveto(new point3d_base(x1,y1,z1), time);
   }
//---------------------------------------
   public void moveto(point3d_base newcentre,int time) {
     fromcentre = lastcentre1=lastcentre2=rcentre;
     tocentre = newcentre;
     fromgx=togx;
     fromgy=togy;
     fromgz=togz;
     fromax=ax;
     fromay=ay;
     fromaz=az;
     ended = false;
     time = time ;

     togx = (tocentre.x-fromcentre.x);
     togy = (tocentre.y-fromcentre.y);
     togz = (tocentre.z-fromcentre.z);

     movestarttime = sharkGame.gtime();
     movetime = time;
     movespeed = (int)Math.sqrt((double)togx*togx + (double)togy*togy + (double)togz*togz)*100/time;
     maxspeed = BASEU*100/time;
     newmove();
}
void newmove() {};
public void endmove3() {};
  //---------------------------------------

public void rotateto(double ax1, double ay1, double az1,int time) {
     fromax = rax;
     fromay = ray;
     fromaz = raz;
     toax = ax1;
     toay = ay1;
     toaz = az1;
     startrottime = sharkGame.gtime();
     rottime = time;
     ended = false;
}
//--------------------------------------------------------
   // convert from screen co-ord to mover3d units with given z
public int convertx(int mx,int z) {
   if(screenwidth>screenheight) {
     return (mx*BASEU/screenwidth - manager.viewerx)*z/BASEZ + manager.viewerx;
   }
   else {
      int start = (screenheight-screenwidth)/2;
      return ((mx+start)*BASEU/screenheight - manager.viewerx)*z/BASEZ + manager.viewerx;
   }
}
//--------------------------------------------------------
   // convert from screen co-ord to mover3d units with given z
public int converty(int my,int z) {
   if(screenwidth>screenheight) {
      int start = (screenwidth-screenheight)/2;
      return ((my+start)*BASEU/screenwidth - manager.viewery)*z/BASEZ + manager.viewery;
   }
   else  {
      return (my*BASEU/screenheight - manager.viewery)*z/BASEZ + manager.viewery;
   }
}
//--------------------------------------------------------
   // convert from screen co-ord to mover3d units with given y returning z
public int convertz(int my,int y) {
   if(screenwidth>screenheight) {
      int start = (screenwidth-screenheight)/2;
      return (y - manager.viewery) * BASEZ / ((my+start)*BASEU/screenwidth - manager.viewery);
   }
   else  {
      return (y - manager.viewery) * BASEZ / (my*BASEU/screenwidth - manager.viewery);
   }
}
public void aftercopyscreen() {}
//--------------------------------------------------------
}
