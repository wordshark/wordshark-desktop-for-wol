package shark;

import java.awt.*;

public class simplefish extends mover3d {
int z;
sharkImage sideim,sidegrinim,frontgrinim;
public sharkImage frontim;
public Color col,mouthcolor;
public boolean happy,snapping,zooming,aiming;
long startzoom,endzoom,lasttime;
int dx,dy,oldz,zoomtime,basew,baseh,oldw,oldh;

long bloodend=-1;
long bloodstart;
public randrange_base xspeed = new randrange_base(-mover.WIDTH/40, mover.WIDTH/40, mover.WIDTH/50);
public randrange_base yspeed = new randrange_base(-mover.WIDTH/200, mover.WIDTH/200, mover.WIDTH/300);
randrange_base zpos = new randrange_base(BASEZ-BASEU, BASEZ + BASEU, BASEU/4);
public String fishname = "fish";

public static final short TYPE_NORMAL = 0;
public static final short TYPE_SALVAGE = 1;
public static final short TYPE_NORMAL_WITH_TILES = 2;
short type;

public simplefish(short mode) {
   super();
   type = mode;
}
public void setup(int w1, runMovers manager, boolean classic) {
   int i;
   sharkImage ims[];
   color = u.fishcolor(manager.getBackground());
   if(classic) {
      ims = sharkImage.randomset(new String[]{"c"+fishname+"side_","c"+fishname+"_","c"+fishname+"sidegrin_"});
      xspeed.maxchange *=8;
   }
   else ims = sharkImage.randomset(new String[]{fishname+"side_",fishname+"_",fishname+"sidegrin_"});
   sideim = ims[0];
   sideim.rotates = true;
   frontim = ims[1];
   mouthcolor = frontim.getcolor(1);
   sidegrinim = ims[2];
   sidegrinim.rotates = true;
   w = w1;
   sideim.w =w1;
   if(manager.screenheight == 0)
        sideim.h = w1*sharkStartFrame.screenSize.width/sharkStartFrame.screenSize.width;
   else sideim.h = w1*manager.screenwidth/manager.screenheight;
   frontim.recolor(Color.black,color);
   sideim.recolor(Color.black,color);
   sidegrinim.recolor(Color.black,color);
   basew = sideim.w;
   baseh = sideim.h;
   rcentre = new point3d_base(0,0,0);
   rcentre.z = zpos.currval;
   w = basew * BASEZ/rcentre.z;
   h = baseh * BASEZ/rcentre.z;
   rcentre.x = (x = u.rand(mover.WIDTH - w)) + w/2;
   rcentre.y = (y = u.rand(mover.HEIGHT - h)) + h/2;
}
//---------------------------------------------------------------
public void paint(Graphics g) {
   short i;
   int dx=1, dy=0, x1, y1, w1, h1;
   long t =  sharkGame.gtime();
   sharkImage im;
   if(!zooming) {
      im = happy?sidegrinim:sideim;
      int wspeed = (manager.currgame != null)?manager.currgame.speed:1;
//      oldz = rcentre.z = zpos.next(t);
//      rcentre.x += (dx = xspeed.next(t)) * (3+wspeed)/12;
//      rcentre.y += (dy = yspeed.next(t)) * (3+wspeed)/12;
      if(type == TYPE_SALVAGE){
          if(aiming)wspeed = 3 + wspeed/2;
          if(!aiming) {
            dx = xspeed.next(t);
            dy = yspeed.next(t);
            lasttime = 0;
          }
          else {
              if(lasttime == 0) lasttime = sharkGame.gtime()-50;
              double angle = Math.atan2((tocentre.y - y-h/2)*manager.screenheight,
                                        (tocentre.x - x-w/2)*manager.screenwidth);
              long tt = (int)(sharkGame.gtime() - lasttime)*5000;
              dx =(int)(tt * Math.cos(angle) / manager.screenwidth);
              dy =(int)(tt * Math.sin(angle) / manager.screenheight);
              lasttime = sharkGame.gtime();
           }
          rcentre.x += dx * (3 + wspeed) / 12;
          rcentre.y += dy * (3 + wspeed) / 12;
      }
      else{
          oldz = rcentre.z = zpos.next(t);
          rcentre.x += (dx = xspeed.next(t)) * (3+wspeed)/12;
          rcentre.y += (dy = yspeed.next(t)) * (3+wspeed)/12;
      }

      oldw = w = basew * BASEZ/rcentre.z;
      oldh = h = baseh * BASEZ/rcentre.z;

       if(rcentre.x < 0) {
            rcentre.x = - rcentre.x;
            xspeed.currval = - xspeed.currval;
            xspeed.currchange = - xspeed.currchange;
      }
      if(rcentre.x >  mover.WIDTH-w) {
            rcentre.x = mover.WIDTH*2 - w*2-rcentre.x;
            xspeed.currval = - xspeed.currval;
            xspeed.currchange = - xspeed.currchange;
      }
      if(rcentre.y < 0) {
            rcentre.y = - rcentre.y;
            yspeed.currval = - yspeed.currval;
            yspeed.currchange = - yspeed.currchange;
      }
      if(rcentre.y >  mover.HEIGHT-h) {
            rcentre.y = mover.HEIGHT*2 - h*2 - rcentre.y;
            yspeed.currval = - yspeed.currval;
            yspeed.currchange = - yspeed.currchange;
      }
      x = rcentre.x;
      y = rcentre.y;
      im.lefttoright = dx < 0;
      im.angle = Math.atan2(dy,Math.max(1,Math.abs(dx)));
      im.angle = Math.max(-Math.PI/8,Math.min(Math.PI/8,im.angle));
      im.paint(g, x1=x*manager.screenwidth/mover.WIDTH,
                      y1=y*manager.screenheight/mover.HEIGHT,
                      w1=w*manager.screenwidth/mover.WIDTH,
                      h1=h*manager.screenheight/mover.HEIGHT);
      if(bloodend >= 0) {
         if(sideim.lefttoright) {
            drawblood(g,t, x1+w1/2 - (int)(w1/4*Math.cos(sideim.angle)),
                         y1+h1/2 + (int)(h1/4*Math.sin(sideim.angle)),
                         w1/2);
         }
         else {
            drawblood(g,t, x1+w1/2 + (int)(w1/4*Math.cos(sideim.angle)),
                         y1+h1/2 + (int)(h1/4*Math.sin(sideim.angle)),
                         w1/2);
         }
      }
   }
   else{
      if(t>endzoom) {
         if(happy) zooming = false;
         t = endzoom;
      }
      if(happy) {
         frontim.w = oldw + (int)((mover.WIDTH * 7/8)*(endzoom-t)/zoomtime);
         frontim.h = oldh + (int)((mover.HEIGHT * ((type==TYPE_NORMAL_WITH_TILES)?6:7)/8)* (endzoom-t)/zoomtime);
      }
      else {
         frontim.w = oldw + (int)((mover.WIDTH * 7/8-oldw)*(t-startzoom)/zoomtime);
         frontim.h = oldh + (int)((mover.HEIGHT * ((type==TYPE_NORMAL_WITH_TILES)?6:7)/8-oldw)* (t-startzoom)/zoomtime);
      }
      frontim.adjustSize(manager.screenwidth, manager.screenheight);
      w = frontim.w;
      h = frontim.h;
      x = Math.max(mover.WIDTH/32,Math.min(mover.WIDTH-w-mover.WIDTH/32,rcentre.x - w/2));
      y = Math.max(mover.HEIGHT/32,Math.min(mover.HEIGHT-h-mover.HEIGHT/((type==TYPE_NORMAL_WITH_TILES)?10:32),rcentre.y - h/2));
      frontim.paint(g, x1=x*manager.screenwidth/mover.WIDTH,
                      y1=y*manager.screenheight/mover.HEIGHT,
                      w1=w*manager.screenwidth/mover.WIDTH,
                      h1=h*manager.screenheight/mover.HEIGHT);
      if(input != null) {
         Rectangle rr = frontim.getRect(1);
         int w2 = rr.width/2;
         int h2 = rr.height/2;
         int x2 =  rr.x + rr.width/4;
         int y2 =  rr.y + rr.height/4;
         input.paint(g,x2,y2,w2,h2,manager.screenheight);
      }
   }
}
//-----------------------------------------------------------------
public void snap1(int x, int y, int time) {
   sideim.lefttoright  = x > rcentre.x + w/2;
   sideim.angle = Math.atan2( y - (rcentre.y+h/2), Math.max(1,Math.abs(x - (rcentre.x+w/2))));
   sideim.setControl("snap");
   snapping = true;
}
//-------------------------------------------------------------
public void snap2(int time) {
   long t = sharkGame.gtime();
   sideim.setControl("snap");
   snapping = true;
 }
//-----------------------------------------------------------
void endsnap() {
   snapping = false;
}
//-----------------------------------------------------------
public void fillscreen(int time) {
  zooming = true;
  startzoom = sharkGame.gtime();
  endzoom = startzoom+time;
  rcentre.z = BASEU;           // force to front
  zoomtime = time;
}
//-----------------------------------------------------------
public void zoomback(int time) {
  zooming = true;
  startzoom = sharkGame.gtime();
  endzoom = startzoom+time;
  zoomtime = time;
}
//-----------------------------------------------------------
public void blood(long t, int limit) {
    bloodend = t+limit;
    bloodstart =  t;
}
//------------------------------------------------------------
void drawblood(Graphics g,long t,int x1, int y1,int maxrad) {
   if(t >= bloodend) {
      bloodend = -1;
      snapping = false;
   }
   else  {
      g.setColor(Color.red);
      g.fillPolygon(u.randpolygon(x1,y1,maxrad/4+(int)(maxrad*3/4*(t-bloodstart)/(bloodend-bloodstart))));
   }
   xmax = Math.max(xmax,x1+maxrad);
   xmin = Math.min(xmax,x1-maxrad);
   ymax = Math.max(ymax,y1+maxrad);
   ymin = Math.min(ymax,y1-maxrad);
 }
//---------------------------------------------------------------
//public boolean inmouth(int mx, int my) {
//   int i;
//   sharkpoly p = new sharkpoly();
//   p.addPoint(x + w/2, y + h/2);
//   if(!sideim.lefttoright) {
//       p.addPoint(x + w, y  + h/2- h/3);
//       p.addPoint(x + w, y  + h/2+ h/3);
//   }
//   else {
//       p.addPoint(x, y  + h/2- h/3);
//       p.addPoint(x, y  + h/2 + h/3);
//   }
//   return (p.rotate(x+w/2, y+h/2, sideim.angle,manager.screenwidth,manager.screenheight).contains(mx,my));
//}
 public boolean inmouth(int mx, int my) {
   return mouth().contains(mx,my);
 }
 //---------------------------------------------------------------
 public sharkpoly mouth() {
   int i;
   sharkpoly p = new sharkpoly();
   p.addPoint(x + w/2, y + h/2);
   sharkImage im;
   im = happy?sidegrinim:sideim;
   if(!im.lefttoright) {
       p.addPoint(x + w, y  + h/2- h/3);
       p.addPoint(x + w, y  + h/2+ h/3);
       return p.rotate(x+w/2, y+h/2, im.angle,manager.screenwidth,manager.screenheight);
   }
   else {
       p.addPoint(x, y  + h/2- h/3);
       p.addPoint(x, y  + h/2 + h/3);
       return p.rotate(x+w/2, y+h/2, -im.angle,manager.screenwidth,manager.screenheight);
   }
}
//------------------------------------------------------------
public boolean isOver(int mx,int my) {
   mx = mx*mover.WIDTH/manager.screenwidth;
   my = my*mover.HEIGHT/manager.screenheight ;
   if(sideim.lefttoright) {
      return mx > x && mx < x + w*3/4 && my > y && my < y+h;
   }
   return mx > x+w/4 && mx < x + w && my > y && my < y+h;
}
public boolean isOver2(int mx,int my) {
   mx = mx*mover.WIDTH/manager.screenwidth;
   my = my*mover.HEIGHT/manager.screenheight ;
   if(sideim.lefttoright) {
      return mx > x && mx < x + w*3/4 && my > y+h/4 && my < y+h*3/4;
   }
   return mx > x+w/4 && mx < x + w && my > y+h/4 && my < y+h*3/4;
}

}
