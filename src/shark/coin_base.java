package shark;
import java.awt.*;
import java.util.Date;


public class coin_base extends mover3d {
   point3d_base top[],bottom[];
   int i,dropfrom,dropto,accel=mover3d.BASEU;
   short pointtot;
   boolean dropping=true;
   Color colorfill,coloredge;
   long startdrop = sharkGame.gtime(); // rb 15/01/07
   long startturn = sharkGame.gtime(); // rb 15/01/07
   public double startax,startay,startaz,endax,enday, endaz;
   long droptime,turntime,lasttime;
   public coin_base pile[];
   int height,width;


   public coin_base(int rad,int height1, int x1, int y1,double ax1,double ay1,double az1,Color colorfill1,Color coloredge1, int bottomy) {
      super();
      pointtot = 16;
      height=height1;
      width = rad*2;
      top = new point3d_base[pointtot];
      bottom = new point3d_base[pointtot];
      for(i=0;i<pointtot;++i) {
         top[i] = new point3d_base((int)(rad*Math.cos(Math.PI*2*i/pointtot)),
                                   -height/2,
                                   (int)(rad*Math.sin(Math.PI*2*i/pointtot)));
         bottom[i] = new point3d_base((int)(rad*Math.cos(Math.PI*2*i/pointtot)),
                                   height/2,
                                   (int)(rad*Math.sin(Math.PI*2*i/pointtot)));
      }
      startax = endax = rax = ax1;
      startay = enday = ray = ay1;
      startaz = endaz = raz = az1;
      rcentre = new point3d_base(x1,y1,mover3d.BASEZ);
      dropfrom = y1;
      colorfill = colorfill1;
      coloredge = coloredge1;
      dropto = bottomy-rad*2/3;
      droptime = turntime = (int)Math.sqrt((double)(dropto-dropfrom)*2000000/accel);
      keepMoving=true;
      w = width; h = width;
  }
  public boolean move(long t) {
     int maxleft,maxright;
     int dropto2 = (dropto*manager.screenheight/mover.HEIGHT - manager.screenheight/2)*mover3d.BASEU/manager.screenmax + mover3d.BASEU/2;
     if(dropping) {
        rax = startax + (endax-startax)*(t-startturn)/turntime;
        ray = startay + (enday-startay)*(t-startturn)/turntime;
        raz = startaz + (endaz-startaz)*(t-startturn)/turntime;
        rcentre.y = dropfrom + (int)(accel * (t-startdrop)*(t-startdrop) / 1000000 / 2);
        rcentre.y = Math.min(rcentre.y, dropto2);
        if(!overpile(rcentre.x, rcentre.y)
           || !overpile(rcentre.x -= width/4,rcentre.y)
           || !overpile(rcentre.x += width/2,rcentre.y)
           || !overpile(rcentre.x -= width*3/4,rcentre.y)
           || !overpile(rcentre.x += width,rcentre.y)) {} // ok
        else {
           rcentre.x -= width/2;   //restore x
           while(overpile(rcentre.x,--rcentre.y));
           dropping=false;
           keepMoving = false;
           rax = Math.PI/8;
           raz /= 2;
           if(manager.sentencefrommover==null)
               noise.chink();
        }
        if(dropping && rcentre.y >= dropto2) {
           dropping = false;
           rcentre.y = dropto2 - u.rand(height*2);
           raz =  0;
           rax = Math.PI/12;
           if(manager.sentencefrommover==null)
               noise.chink();
           keepMoving=false;
        }
        lasttime=t;
        return true;
     }
     lasttime=t;
     return false;
  }
 //-------------------------------------------------------------------
  boolean overpile(int x, int y) {
     if(pile != null) {
        for(short i = 0;i<pile.length;++i) {
           if(pile[i] == null)  break;
           if(!pile[i].dropping && y >= pile[i].rcentre.y - height*2
              && Math.abs(x-pile[i].rcentre.x) < width/2) return true;
        }
     }
     return false;
  }
  //-------------------------------------------------------------------
  public void paintm(Graphics g, long t) {
     int  i,j;
     int thickness,xinc,yinc;
     boolean p2ontop=false;
     Polygon p1 = getPolygon(top);
     Polygon p2 = getPolygon(bottom);
     thickness = u.LCM(Math.abs(p1.xpoints[0]-p2.xpoints[0]), Math.abs(p1.ypoints[0]-p2.ypoints[0]));
     saveimage();

     g.setColor(coloredge);
     int xx[] = new int[pointtot];
     int yy[] = new int[pointtot];
     for(i=0;i<=thickness;++i) {
        xinc = (p1.xpoints[0]-p2.xpoints[0])*i/thickness;
        yinc = (p1.ypoints[0]-p2.ypoints[0])*i/thickness;
        for(j=0;j<pointtot;++j) {
           xx[j] = p2.xpoints[j] + xinc;
           yy[j] = p2.ypoints[j] + yinc;
        }
        g.drawPolygon(xx,yy,pointtot);
     }
     if(rax>0) {
        g.setColor(colorfill);
        g.fillPolygon(p1);
        g.setColor(coloredge);
        g.drawPolygon(p1);
     }
     else {
        g.setColor(colorfill);
        g.fillPolygon(p2);
        g.setColor(coloredge);
        g.drawPolygon(p2);
     }
  }
}
