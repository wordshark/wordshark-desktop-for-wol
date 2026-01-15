package shark;
import java.awt.*;
public class sharkpoly extends Polygon {
   public boolean reverse;
   int revpoint;

   public sharkpoly() {
      super();
   }
   public sharkpoly(int xp[], int yp[],int n) {
      super(xp,yp,n);
   }
   public sharkpoly(Polygon p) {
      super(p.xpoints,p.ypoints,p.npoints);
   }
   public void add(int len,double angle) {
      if(reverse) {
         addPoint(0,0);
         System.arraycopy(xpoints,revpoint,xpoints,revpoint+1,npoints-revpoint-1);
         System.arraycopy(ypoints,revpoint,ypoints,revpoint+1,npoints-revpoint-1);
         xpoints[revpoint]= xpoints[revpoint+1] + (int)(len*Math.cos(angle));
         ypoints[revpoint]= ypoints[revpoint+1] + (int)(len*Math.sin(angle));
      }
      else addPoint(xpoints[npoints-1] + (int)(len*Math.cos(angle)),
               ypoints[npoints-1] + (int)(len*Math.sin(angle)));
   }
   public void addBackwards(int x, int y) {
      revpoint = npoints;
      addPoint(x,y);
      reverse = true;
   }
   public sharkpoly convert(int sw,int sh) {
      sharkpoly ret = new sharkpoly();
      for(short i = 0; i<npoints; ++i) {
         ret.addPoint(xpoints[i]*sw/mover.WIDTH, ypoints[i]*sh/mover.HEIGHT);
      }
      return ret;
   }
   public sharkpoly convertback(int sw,int sh) {
      sharkpoly ret = new sharkpoly();
      for(short i = 0; i<npoints; ++i) {
         ret.addPoint(xpoints[i]*mover.WIDTH/sw, ypoints[i]*mover.HEIGHT/sh);
      }
      return ret;
   }
   public sharkpoly convert(int sm, int from, int to) {
      sharkpoly ret = new sharkpoly();
      for(short i = (short)from; i<to; ++i) {
         ret.addPoint(xpoints[i]*sm/mover3d.BASEU, ypoints[i]*sm/mover3d.BASEU);
      }
      return ret;
   }
                   // rotate and convert
   public sharkpoly rotate(int x, int y, double angle,int sw, int sh) {
      sharkpoly ret = new sharkpoly();
      int sin = (int)(Math.sin(angle) * 1000);
      int cos = (int)(Math.cos(angle) * 1000);
      int xa = x*sw/mover.WIDTH;
      int ya = y*sh/mover.HEIGHT;

      for(short i = 0; i<npoints; ++i) {
        ret.addPoint(xa + (int)(((long)(xpoints[i]-x)*sw*cos/mover.WIDTH - (long)(ypoints[i]-y)*sh*sin/mover.HEIGHT)/1000),
                     ya + (int)(((long)(xpoints[i]-x)*sw*sin/mover.WIDTH + (long)(ypoints[i]-y)*sh*cos/mover.HEIGHT)/1000));
      }
      return ret;
   }
   public sharkpoly simplerotate(int x, int y, double angle) {
      int sin = (int)(Math.sin(angle) * 1000);
      int cos = (int)(Math.cos(angle) * 1000);
      int xa,ya;

      for(short i = 0; i<npoints; ++i) {
        xa = xpoints[i] - x;
        ya = ypoints[i] - y;
        xpoints[i] = x + (int)(((long)xa*cos - (long)ya*sin)/1000);
        ypoints[i] = y + (int)(((long)xa*sin + (long)ya*cos)/1000);
      }
      return this;
   }
   public static sharkpoly simplerotate(Polygon p, int x, int y, double a) {
      int xa,ya;
      sharkpoly pp = new sharkpoly(new int[p.npoints],new int[p.npoints],p.npoints);
      for(short i = 0; i<p.npoints; ++i) {
        xa = p.xpoints[i] - x;
        ya = p.ypoints[i] - y;
        pp.xpoints[i] = x + (int)(xa*Math.cos(a) - ya*Math.sin(a));
        pp.ypoints[i] = y + (int)(xa*Math.sin(a) + ya*Math.cos(a));
      }
      return pp;
   }
   public sharkpoly tranrotate(int addx, int addy,int x, int y, double angle,int sw, int sh) {
      sharkpoly ret = new sharkpoly();
      int sin = (int)(Math.sin(angle) * 1000);
      int cos = (int)(Math.cos(angle) * 1000);
      int xa = x*sw/mover.WIDTH;
      int ya = y*sh/mover.HEIGHT;

      for(short i = 0; i<npoints; ++i) {
        ret.addPoint(xa + (int)(((long)(xpoints[i]+addx-x)*sw*cos/mover.WIDTH - (long)(ypoints[i]+addy-y)*sh*sin/mover.HEIGHT)/1000),
                     ya + (int)(((long)(xpoints[i]+addx-x)*sw*sin/mover.WIDTH + (long)(ypoints[i]+addy-y)*sh*cos/mover.HEIGHT)/1000));
      }
      return ret;
   }
   public sharkpoly translate(int addx,int addy,int sw,int sh) {
      sharkpoly ret = new sharkpoly();

      for(short i = 0; i<npoints; ++i) {
        ret.addPoint((xpoints[i]+addx)*sw/mover.WIDTH,
                     (ypoints[i]+addy)*sh/mover.HEIGHT);
      }
      return ret;
    }
    public sharkpoly translatecopy(int addx,int addy) {
       sharkpoly ret = new sharkpoly();

       for(short i = 0; i<npoints; ++i) {
         ret.addPoint(xpoints[i]+addx,
                      ypoints[i]+addy);
       }
       return ret;
     }
   public sharkpoly fit(int x1, int y1, int w1,int h1)  {
      int x2 = x1+w1-1;
      int y2 = y1+h1-1;
      for(short i = 0; i<npoints; ++i) {
         xpoints[i] = Math.max(x1,Math.min(x2,xpoints[i]));
         ypoints[i] = Math.max(y1,Math.min(y2,ypoints[i]));
      }
      return this;
   }
   public static sharkpoly rect(int x, int y, int w, int h, int midx, int midy, double angle, int sw, int sh) {
      return (new sharkpoly(new int[]{x,x+w,x+w,x},new int[]{y,y,y+h,y+h},4)).rotate(midx,midy,angle,sw,sh);
   }
   public static sharkpoly rect(int x, int y, int w, int h, int midx, int midy, double angle) {
      return (new sharkpoly(new int[]{x,x,x+w,x+w},new int[]{y,y+h,y+h,y},4)).simplerotate(midx,midy,angle);
   }
   public static sharkpoly oval(int x, int y, int a, int b, int points) {
      double angle;
      sharkpoly ret = new sharkpoly();
      for(short i = 0 ; i< points;++i) {
         angle = Math.PI*2*i/points;
         ret.addPoint(x + (int)(a*Math.cos(angle)), y + (int)(b*Math.sin(angle)));
      }
      return ret;
   }
   public static sharkpoly arc(int x, int y, int a, int b, int points,double from, double to) {
      double angle;
      sharkpoly ret = new sharkpoly();
      for(short i = 0 ; i< points;++i) {
         angle = from + (to-from)*i/(points-1);
         ret.addPoint(x + (int)(a*Math.cos(angle)), y + (int)(b*Math.sin(angle)));
      }
      return ret;
   }
   public void drawline(Graphics g) {
      g.drawPolyline(xpoints,ypoints,npoints);
   }
   public boolean intersects(sharkpoly pp) {
      int i,j;
      if(!pp.intersects(getBounds())) return false;
      for(i=0;i<npoints;++i)
        if(pp.contains(xpoints[i], ypoints[i])) return true;
      for (j = 0; j < pp.npoints; ++j)
        if(contains(pp.xpoints[j], pp.ypoints[j])) return true;
      for(i=0;i<npoints;++i) {
         int ni = (i+1)%npoints;
         for (j = 0; j < pp.npoints; ++j) {
            int nj =  (j+1)%pp.npoints;
            if(u.linescross(pp.xpoints[j], pp.ypoints[j],pp.xpoints[nj], pp.ypoints[nj],
                           xpoints[i], ypoints[i], pp.xpoints[ni], pp.ypoints[ni])) return true;

        }
      }
      return false;
   }
}
