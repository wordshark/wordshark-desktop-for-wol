
package shark;
import java.awt.*;

public class maze extends mover3d {
   Image buffer;
   public Polygon outerpoly;
   public boolean lettermaze,threeD,iscursor=true,mustclick;
   sharkGame thisgame;
   int oldheight,oldwidth;
   class wall {
     Polygon p;
     boolean missing,extra,door,moved,outside;
   }
   wall walls[] = new wall[100];
   Polygon wall2d[];
   class wall3d {
     Polygon p,ptop;
     int dist;
   }
   wall3d newwall[];
   short cells[][] = new short[100][];
   short adjcells[][];
   short fromexit[];
   Point cellmiddle[];
   Polygon cellpoly[];
   short wtot,walltot;
   public short celltot;
   short exitwall,exitcell;
   public int wallheight = BASEU/4;
   int animalwidth;
   public animal animals[] = new animal[10];
   public animal frozenanimal,zapping;
   long zappend,nozapp;
   public short atot;
   maze thismaze=this;
   Point spritept= new Point(BASEU/2,BASEU/2),lastspritept;
   boolean gotspritepos;
   Toolkit t = Toolkit.getDefaultToolkit();
   public Color animalcolor;
   public int mazeheight;
   Font letterfont;
   public FontMetrics lettermetrics;
   int letterdy;
   short spritecell;
   long lastcatch = sharkGame.gtime();

   class strip {
      int x,y1,y2;
      public strip(int xx,int yy1,int yy2) {x=xx;y1=yy1;y2=yy2;}
   }
   boolean gotmaze;
   public boolean freeform;
   public short mazesize;
   int pathwidth,minwidth,path;
   public sharkImage mazesprite
          = new sharkImage(sharkStartFrame.studentList[sharkStartFrame.currStudent].spritename,
          false);

   public maze(runMovers gamePanel1,sharkGame gg) {
      manager = gamePanel1;
      thisgame =gg;
      freeform = options.option("maze-freeform");
      mazesize = 50;//(short)Math.max(1,options.optionval("maze-size"));
      threeD =   options.option("maze-3d");
      if(freeform) manager.getPolygon();
      screenwidth = manager.screenwidth;
      screenheight = manager.screenheight;
      rcentre = new point3d_base(BASEU/2,BASEU/2,BASEZ+wallheight);
      dontclear = true;
   }
   //----------------------------------------------------------
   void buildsquaremaze(int x1, int y1, int w1, int h1) {  // mover units
      int width = w1 - (w1%pathwidth);
      int height = h1 - (h1%pathwidth);
      Rectangle r;
      int x,y;
      x1 = x1+w1-width;
      y1 = y1+h1-height;
//      outerpoly = new Polygon(new int[] {x1,x1+width,x1+width,x1},new int[] {y1,y1,y1+height,y1+height},4);
      outerpoly = new Polygon();
      for(x = x1; x<x1+width;x += pathwidth) outerpoly.addPoint(x,y1);
      for(y=y1;y<y1+height;y+=pathwidth) outerpoly.addPoint(x1+width,y);
      for(x = x1+width; x>0;x -= pathwidth) outerpoly.addPoint(x,y1+height);
      for(y=y1+height;y> 0;y-=pathwidth) outerpoly.addPoint(x1,y);

      short across = (short)(width/pathwidth);
      short down = (short)(height/pathwidth);
      for(y=y1;y<=y1+height;y+=pathwidth) {
         for(x = x1; x<x1+width;x += pathwidth) {
            addwall(new Polygon(new int[] {x,x+pathwidth},new int[]{y,y},2), false);
            if(y==y1 || y==y1+height) walls[walltot-1].outside=true;
         }
      }
      int endacross = walltot;
      for(x = x1; x<=x1+width;x += pathwidth) {
         for(y=y1;y<y1+height;y += pathwidth) {
            addwall(new Polygon(new int[] {x,x},new int[]{y,y+pathwidth},2), false);
            if(x==x1 || x==x1+width)  walls[walltot-1].outside=true;
         }
      }
      switch(u.rand(4)) {
         case 0: exitwall = u.rand(across);   break;
         case 1: exitwall = (short)(endacross - 1 - u.rand(across));  break;
         case 2: exitwall = (short)(endacross + u.rand(down));  break;
         case 3: exitwall = (short)(walltot - 1 - u.rand(down));  break;
      }
      celltot = (short)(across*down);
      cells = new short[celltot][];
      cellmiddle = new Point[celltot];
      cellpoly = new Polygon[celltot];
      short i = 0;
      for(y=0;y<down;++y) {
         for(x = 0; x<across;++x) {
            cells[i++] = new short[] {
                      (short)(x + y*across),
                      (short)(endacross + (x+1)*down + y),
                      (short)(x + (y+1) * across),
                      (short)(endacross + x*down + y)};
         }
     }
     for(i=0;i<celltot;++i) {
        cellpoly[i] = cellpoly(i);
        r=cellpoly[i].getBounds();
        cellmiddle[i] = new Point(r.x+r.width/2,r.y+r.height/2);
     }
   }
   //----------------------------------------------------------
   public boolean buildmaze(Polygon p, int x1, int y1, int w1, int h1, int pathwidth1) {  // mover units
      Polygon p1,p2;
      int repeats = 0,repeats2=0;
      rcentre.z = BASEZ+wallheight;
      pathwidth = pathwidth1;
      animalwidth = pathwidth*2/3;
      minwidth = pathwidth*15/16;
      path = pathwidth*pathwidth;
      int i;
      bigloop:while(true) {
         if(++repeats > 2000) return false;
         gotspritepos = false;
         walltot= 0; celltot=0;
         mazeheight = h1;
         if(p != null) {
            p1= p;
            repeats2 = 0;
            while(true) {
             if(++repeats2 >1000) return false;
             outerpoly = p1 = padPolygon(fitPolygon(p1, x1,y1,w1,h1));
             Polygon p3 = u.clean2(p1,path);
             if(p3 == p1) break;
             p1=p3;
           }
           splitPolygon(p1);
           for(i=0;i<walltot;++i) {
             walls[i].outside = true;
           }
           exitwall = u.rand(walltot);
           short oldwalltot=walltot;
           newpoly(p1,(short)0,walltot);
           if(walltot < oldwalltot*2) {
             for(i=0;i<p.npoints;++i) {
               p.xpoints[i] /= 2;
               p.ypoints[i] /= 2;
             }
             continue bigloop;
           }
           else buildcells();
         }
         else buildsquaremaze(x1,y1,w1,h1);
         if(!connect()) {         // make interconnecting
            p.npoints = Math.min(p.npoints-1,p.npoints *3/4);
            continue bigloop;
         }
         break;
      }
      buildadj();
      buildfromexit();
      manager.clearWholeScreen = true;
      return true;
  }
  //-----------------------------------------------------------
  void buildadj() {
     adjcells = new short[celltot][];
     short i,k,j;
     for(i = 0; i<celltot; ++i) {
        adjcells[i] = new short[0];
        for(j=0;j<cells[i].length;++j) {
           if(walls[cells[i][j]].missing
                && (k=adjacent(i,cells[i][j])) >= 0)
                     adjcells[i] = u.addshort(adjcells[i],k);
        }
     }
  }
  //-----------------------------------------------------------
                  // build inner polygons
  void newpoly(Polygon p,short fromwall1,short towall1) {
     Rectangle r = p.getBounds();
     int x1 = r.x+pathwidth;
     int x2 = r.x+r.width-pathwidth;
     int y1 = r.y+pathwidth;
     int y2 = r.y+r.height-pathwidth;
     int inc = pathwidth/2;
     int incy = pathwidth/2;
     int md;
     strip strips[] = new  strip[100];
     int striptot=0,strip;
     int i,j,k,x,y,yt,diff;
     boolean left;


     for(x=x1;x<x2;x+=inc) {   // find points on new polygon(s)
        for(y=y1;y<y2;y+=incy) {
           if(p.contains(x,y) && (md=u.mindist(p,x,y)) >= path && md < path*4) {
              diff = incy/2;
              y -= diff;
              while((diff/=2) > 0) {
                 if(u.mindist(p,x,y) >= path) y -=diff;
                 else y += diff;
              }
              yt = y;
              while(u.mindist(p,x,y+= incy) >= path);
              if(y != yt+incy) {
                 diff = incy/2;
                 y -= diff;
                 while((diff/=2) > 0) {
                    if(u.mindist(p,x,y) >= path) y +=diff;
                    else y -= diff;
                 }
                 if(striptot >= strips.length-1) {
                    strip newstrips[] = new strip[striptot+100];
                    System.arraycopy(strips,0,newstrips,0,striptot);
                    strips  = newstrips;
                 }
                 strips[striptot++] = new strip(x,yt,y);
              }
           }
        }
     }
     if(striptot==0) return;
     left = false;
     strip = 0;
     Polygon pnew = new Polygon();
     loop1: while(striptot > 0) {
        if(!left) {          // going right, doing tops
           pnew.addPoint(x = strips[strip].x, y = strips[strip].y1);
           for(i=strip+1;i<striptot && strips[i].x <= x+inc;++i) {
              if(strips[i].x == x+inc    //overlap to right?
                    && strips[i].y2 > y
                    && strips[i].y1 < strips[strip].y2 ) {
                 if(strips[i].y1 < y       // is there a strip above current one?
                       && strip>0
                       && strips[strip-1].x == x
                       && strips[strip-1].y2 > strips[i].y1) {
                    pnew.addPoint(strips[i].x,(y*3 + strips[strip-1].y2)/4);
                    pnew.addPoint(strips[i].x,(y + strips[strip-1].y2*3)/4);
                    left = true;
                    --strip;
                 }
                 else {     // go to top of overlapping one on right
                    strip = i;
                 }
                 continue loop1;
              }
           }
           left = true;   // no overlap to right - switch to bottoms
        }
        else  {          // going left, doing bottoms
           pnew.addPoint(x = strips[strip].x, y = strips[strip].y2);
           for(i=strip-1;i>=0 && strips[i].x >= x-inc;--i) {
              if(strips[i].x == x-inc    //overlap to left?
                    && strips[i].y2 > strips[strip].y1
                    && strips[i].y1 < strips[strip].y2 ) {
                 if(strips[i].y2 > y       // is there a strip above current one?
                       && strip<striptot-1
                       && strips[strip+1].x == x
                       && strips[strip+1].y1 < strips[i].y2) {
                    left = false;
                    pnew.addPoint(strips[i].x,(y*3 + strips[strip+1].y1)/4);
                    pnew.addPoint(strips[i].x,(y + strips[strip+1].y1*3)/4);
                    ++strip;
                 }
                 else {     // go to bottom of overlapping one on left
                    strip = i;
                 }
                 continue loop1;
              }
           }
           left = false;   // no overlap to left - switch to tops
           if(strip == 0) {    // gone round
                    // remove strips that we used
              for(j=0;j<pnew.npoints ;++j) {
                 for(i=0;i<striptot;++i) {
                    if(pnew.xpoints[j] == strips[i].x
                          && (pnew.ypoints[j] == strips[i].y1
                              || pnew.ypoints[j] == strips[i].y2)) {
                       System.arraycopy(strips,i+1,strips,i,--striptot - i);
                       break;
                    }
                 }
              }
              short fromwall2 = walltot;
              pnew = padPolygon(pnew);
              splitPolygon(pnew);   // split newp and add cross-walls
              short towall2  = walltot;
              if(fromwall2 != towall2) {
                 addwalls(fromwall1,towall1,fromwall2,towall2);
                 newpoly(pnew,fromwall2,towall2);
              }
              if(striptot > 0) {
                 pnew = new Polygon();
                 strip = 0;
                 left = false;
              }
              continue loop1;
           }
        }
     }
  }
  //--------------------------------------------------------------
                         // build cells
  void buildcells() {
     int x1,y1,x2,y2;
     short list[]=new short[walltot],listtot, i,j,k;
     Polygon p;
     Rectangle r;
     for (i=0;i<walltot;++i) {
        p = walls[i].p;
        list[0] = i;
        listtot = addConnection((short)i,i,
                            Math.atan2( p.ypoints[0]-p.ypoints[p.npoints-1],
                                    p.xpoints[0] - p.xpoints[p.npoints-1]),
                                  (double)0,list,(short)1,
                                  p.xpoints[p.npoints-1],
                                  p.ypoints[p.npoints-1],
                                  p.xpoints[0],
                                  p.ypoints[0]);
        if(listtot>0) addcell(list,listtot);

        list[0] = i;
        listtot = addConnection((short)i,i,
                            Math.atan2( -p.ypoints[0]+p.ypoints[p.npoints-1],
                                    -p.xpoints[0] + p.xpoints[p.npoints-1]),
                                  (double)0,list,(short)1,
                                  p.xpoints[0],
                                  p.ypoints[0],
                                  p.xpoints[p.npoints-1],
                                  p.ypoints[p.npoints-1]);
        if(listtot>0) addcell(list,listtot);
      }
      finishcells();
  }
  //-------------------------------------------------------------------
  void finishcells() {
      Rectangle r;
      cellmiddle=new Point[celltot];
      cellpoly = new Polygon[celltot];
      for(short i=0;i<celltot;++i) {
        cellpoly[i] = cellpoly(i);
        r = cellpoly[i].getBounds();
        cellmiddle[i] = new Point(r.x+r.width/2,r.y+r.height/2);
      }
  }
  //-----------------------------------------------------------
  short addConnection(short start,short exclude,double angle,
                 double totangle,short[] list,short listtot,
                 int stopx, int stopy, int x, int y) {
     double minangle = Math.PI*3;
     double angle1,tangle,newangle=0;
     Polygon p;
     int newx=0,newy=0;
     short min = -1;
     for (short j=0;j<walltot;++j) {
         if(j==exclude) continue;
         p = walls[j].p;
         if(p.xpoints[0] == x && p.ypoints[0] == y) {
            if(p.xpoints[p.npoints-1] == stopx
                     && p.ypoints[p.npoints-1] == stopy) {
                 if(j <= start) return 0;
                 if(totangle > Math.PI*listtot) return 0;
                 list[listtot++] = j;
                 return listtot;
            }
            else {
               angle1 = Math.atan2(p.ypoints[p.npoints-1]-p.ypoints[0],
                                p.xpoints[p.npoints-1]-p.xpoints[0]);
               tangle = (angle1 + Math.PI*5 - angle);
               while(tangle>Math.PI*2) tangle -= Math.PI*2;
               if(tangle < minangle) {
                   minangle = tangle;
                   newangle = angle1;
                   min = j;
                   newx = p.xpoints[p.npoints-1];
                   newy = p.ypoints[p.npoints-1];
               }
            }
         }
         else if(p.xpoints[p.npoints-1] == x && p.ypoints[p.npoints-1] == y) {
            if(p.xpoints[0] == stopx && p.ypoints[0] == stopy) {
               if(j <= start) return 0;
               if(totangle > Math.PI*listtot) return 0;
               list[listtot++] = j;
               return listtot;
            }
            else {
               angle1 = Math.atan2(-p.ypoints[p.npoints-1]+p.ypoints[0],
                               -p.xpoints[p.npoints-1]+p.xpoints[0]);
               tangle = (angle1 + Math.PI*5 - angle);
               while(tangle>Math.PI*2) tangle -= Math.PI*2;
               if(tangle < minangle) {
                   minangle = tangle;
                   newangle = angle1;
                   min = j;
                   newx = p.xpoints[0];
                   newy = p.ypoints[0];
               }
            }
         }
     }
     if(min <= start) return 0;
     list[listtot++] = min;
     totangle += minangle;

     return(addConnection(start,min,newangle,totangle,list,listtot,
                stopx,stopy,newx,newy));
  }
  //-----------------------------------------------------------
  void splitPolygon(Polygon p) {
     int i,j,k,x,y,lasti=0,dist;
     Polygon p2;
     short startwall  = walltot;
     for(i=0;i<p.npoints;++i) {
        if(lasti != 0
          && (p.xpoints[i]-p.xpoints[0])* (p.xpoints[i]-p.xpoints[0])
             + (p.ypoints[i]-p.ypoints[0])*(p.ypoints[i]-p.ypoints[0]) < path)
                 break;
        dist = (p.xpoints[i]-p.xpoints[lasti])* (p.xpoints[i]-p.xpoints[lasti])
             + (p.ypoints[i]-p.ypoints[lasti])*(p.ypoints[i]-p.ypoints[lasti]);
        if (dist > path) {
            addwall(u.extract(p,lasti,i),false);
            lasti = i;
        }
     }
     dist = (p.xpoints[0]-p.xpoints[lasti])* (p.xpoints[0]-p.xpoints[lasti])
             + (p.ypoints[0]-p.ypoints[lasti])*(p.ypoints[0]-p.ypoints[lasti]);
     if (lasti != 0) {
         Polygon newp = u.extract(p,lasti,p.npoints-1);
         newp.addPoint(p.xpoints[0],p.ypoints[0]);
         addwall(newp,false);
     }
     for(i=startwall;i<walltot;++i) {
        p = walls[i].p;
        x = p.xpoints[0];
        y = p.ypoints[0];
        for(j=i + 2;j<walltot-1;++j) {
           p2 = walls[i].p;
           if((p2.xpoints[p.npoints-1]-x)*(p2.xpoints[p.npoints-1]-x)
                + (p2.ypoints[p.npoints-1]-y)*(p2.ypoints[p.npoints-1]-y) < path/2) {
              if(!walls[j].moved) {
                 walls[j].p = new Polygon(new int[] {walls[j].p.xpoints[0],walls[j].p.ypoints[0]},
                                         new int[] {x,y},2);
                 walls[j].moved = true;
              }
              else {
                 System.arraycopy(walls,j+1,walls,j,--walltot - j);
                 --j;
              }
              k = j+1;
              if(k==walltot) k = 0;
              if(!walls[k].moved) {
                walls[k].p = new Polygon(new int[] {x,y},
                               new int[] {walls[k].p.xpoints[walls[k].p.npoints-1],walls[k].p.ypoints[walls[k].p.npoints-1]},2);
                walls[k].moved = true;
              }
              else {
                 System.arraycopy(walls,k+1,walls,k,--walltot - k);
                 --j;
              }
           }
        }
     }
  }
  //-----------------------------------------------------------
  boolean connect() {
     short o[];
     short t[];
     short i,k,w,next,curr,connecttot=1,last=-1,lastcon=-1;
     boolean connected[] = new boolean[celltot];

               // start in random place
     connected[curr = u.rand(celltot)] = true;
     loop1: while(connecttot < celltot) {
        if(connecttot == lastcon) return false;
        lastcon=connecttot;
        t = cellWallList(cells[curr]);
        for(k=0;k<t.length;++k) {
           w = cells[curr][t[k]];
           next = adjacent(curr,w);
           if(next>=0 && !connected[next]) {
              walls[w].missing = true;
              connected[next] = true;
              ++connecttot;
              curr = next;
              continue loop1;
           }
        }
              //   Cannot connect current cell to new place.
              //   Find unconnected cell next to connected one
              // and continue there
        o = u.shuffle(u.select(celltot,celltot));
        for(i=0;i<celltot;++i) {
           curr = o[i];
           if(!connected[curr]) {
              t = cellWallList(cells[curr]);
              for(k=0;k<t.length;++k) {
                 w = cells[curr][t[k]];
                 next = adjacent(curr,w);
                 if(next>=0 && connected[next]) {
                    walls[w].missing = true;
                    connected[curr] = true;
                    ++connecttot;
                    continue loop1;
                 }
              }
           }
        }
     }
     return true;
  }
  //----------------------------------------------------------
  short[] cellWallList(short[] cell) {
     short ct = (short)cell.length,i,j;
     for(i =0;i<cell.length;++i) {
        if(walls[i].extra) ct+=2;
     }
     short ret[] = new short[ct];
     for(i =0,j=0;i<cell.length;++i) {
        ret[j++] = i;
        if(walls[i].extra) {ret[j++] = i; ret[j++] = i;}
     }
     return u.shuffle(ret);
  }
  //-----------------------------------------------------------
  short adjacent(short cellno, short wno) {
     short i,j;
     for(i = 0; i<celltot; ++i) {
        if(i!=cellno) for(j=0;j<cells[i].length;++j) {
           if(cells[i][j] == wno)  return i;
        }
     }
     return -1;
  }
  //-----------------------------------------------------------
  boolean nextcell(short cell1, short cell2) {
     short i,j;
     for(i=0;i<cells[cell1].length;++i) {
        if(walls[cells[cell1][i]].missing) {
           for(j=0;j<cells[cell2].length;++j) {
               if(cells[cell2][j] == cells[cell1][i]) return true;
           }
        }
     }
     return false;
  }
  //-----------------------------------------------------------
  void buildfromexit() {
     short i,j,c[];
     loop1: for(i=0;i<celltot;++i) {
        c = cells[i];
        for(j=0;j<c.length;++j) {
           if(c[j]==exitwall) {
              exitcell = i;
              break loop1;
           }
        }
     }
     fromexit = new short[celltot];
     for(i=0;i<celltot;++i) fromexit[i] = celltot;
     assignexitcount(exitcell,fromexit,(short)0);
  }
  void assignexitcount(short cell,short[] fromexit1,short dist) {
     short i,c[],j;
     fromexit1[cell] = dist;
     c = cells[cell];
     for(i=0;i<c.length;++i) {
        if(walls[c[i]].missing && !walls[c[i]].outside
                && (j = adjacent(cell,c[i])) >= 0) {
           if(fromexit1[j] > dist+1) assignexitcount(j,fromexit1,(short)(dist+1));
        }
     }
  }
  //-----------------------------------------------------------
  short incell(Point pt) {
     for(short i=0;i<celltot;++i) {
        if(cellpoly(i).contains(pt)) return(i);
     }
     return -1;
  }
  //-----------------------------------------------------------
  boolean notcloser(Polygon p, Point pt, int dist) {
     int i;
     int d = dist*dist;
     for(i=0; i<p.npoints;++i) {
        if((pt.x-p.xpoints[i])*(pt.x-p.xpoints[i])
           * (pt.y-p.ypoints[i])*(pt.y-p.ypoints[i])
            < d)  return false;
     }
     return true;
  }
  //-----------------------------------------------------------
               // add extra walls between polygons
  void addwalls(short outer1,short outer2,short inner1,short inner2) {
     int i,j,next,used[] = new int[outer2-outer1],usedtot=0;

     next = closestwall(inner1,outer1,outer2);
     addwall(new Polygon(new int[] {walls[inner1].p.xpoints[0],walls[next].p.xpoints[0]},
                         new int[] {walls[inner1].p.ypoints[0],walls[next].p.ypoints[0]},2),true);
     loop1: for(i=inner1+1;i<inner2;++i) {
         next =  closestwall(i,outer1,outer2);
         for(j=0;j<usedtot;++j) {
            if(next == used[j]) {++i; continue loop1;}
         }
         addwall(new Polygon(new int[] {walls[i].p.xpoints[0],walls[next].p.xpoints[0]},
                         new int[] {walls[i].p.ypoints[0],walls[next].p.ypoints[0]},2),true);
         used[usedtot++]=next;
     }
  }
  //----------------------------------------------------------
  int closestwall(int w, short from, short to) {
      int dist2,dist1 = 0x7fffffff,ret=-1;
      int x = walls[w].p.xpoints[0];
      int y = walls[w].p.ypoints[0];
      short i,j;
      for(i=from;i<to;++i) {
         dist2 = (walls[i].p.xpoints[0]-x)*(walls[i].p.xpoints[0]-x)
              + (walls[i].p.ypoints[0]-y)* (walls[i].p.ypoints[0]-y);
         if(dist2 < dist1) {
            ret = i;
            dist1=dist2;
         }
      }
      return ret;
   }
  //-----------------------------------------------------------
  short[] localwalls(int x,int y) {
     short i,j=0,ret[];
     int minx = x - pathwidth*2;
     int maxx = x + pathwidth*2;
     int miny = y - pathwidth*2;
     int maxy = y + pathwidth*2;
     Polygon p;

     for(i=0;i<walltot;++i) {
        if((!walls[i].missing || walls[i].outside)
            && (p=walls[i].p).xpoints[0] > minx
            && p.xpoints[0] < maxx
            && p.ypoints[0] > miny
            && p.ypoints[0] < maxy) {
          ++j;
        }
     }
     ret = new short[j];
     j=0;
     for(i=0;i<walltot;++i) {
        if((!walls[i].missing || walls[i].outside)
            && (p=walls[i].p).xpoints[0] > minx
            && p.xpoints[0] < maxx
            && p.ypoints[0] > miny
            && p.ypoints[0] < maxy) {
          ret[j++] = i;
        }
     }
     return ret;
  }
  //-----------------------------------------------------------
  boolean tooclose(int x,int y,int mind,short lwalls[]) {
     short i;
     int j;
     Polygon p;
     wall w;

     for(i=0;i<lwalls.length;++i) {
        w = walls[lwalls[i]];
        p=w.p;
        if(p.npoints == 2 ) {
            if(u.overline(x,y,p.xpoints[0],p.ypoints[0],
                            p.xpoints[1],p.ypoints[1])
                   && u.dist(x,y,p.xpoints[0],p.ypoints[0],p.xpoints[1],p.ypoints[1]) < mind)
              return true;
         }
         for(j=0;j<p.npoints-1;++j) {
            if((p.xpoints[j]-x)*(p.xpoints[j]-x)
                   + (p.ypoints[j]-y)*(p.ypoints[j]-y) < mind)
               return true;

         }
     }
     return false;
  }
  //-----------------------------------------------------------
        // returns new direction
  double moverand(Point pt,double dir,int dist) {
     int x1,y1,trydist;
     double inc=0,newdir;
     boolean ahead=false,right=false,left=false,back=false;
     short lwalls[];
     while(dist > pathwidth/4) {
        dir = moverand(pt,dir,pathwidth/4);
        dist -= pathwidth/4;
     }
     lwalls = localwalls(pt.x,pt.y);
     while(true) {
        if(!ahead &&  u.rand(8) != 0)
                   {ahead=true;newdir = dir;}
        else if(!left && (right || u.rand(2)==0)) {left=true;newdir = dir-Math.PI/2;}
        else if(!right) {right = true; newdir = dir+Math.PI/2;}
        else if(!left) {left=true;newdir = dir-Math.PI/2;}
        else if(!ahead) {ahead=true;newdir = dir;}
        else if(!back) {back=true; newdir = dir+Math.PI;}
        else  {
           dist += pathwidth/8;
           ahead=left=right=back=false;
           continue;
        }
        trydist = Math.max(pathwidth/4,dist);
        for(inc=0;inc<=Math.PI/4;inc += Math.PI/16) {
           x1 = pt.x + (int)(trydist*Math.cos(newdir+inc));
           y1 = pt.y + (int)(trydist*Math.sin(newdir+inc));
           if(!tooclose(x1,y1,path/6,lwalls)) {
              pt.x += (int)(dist*Math.cos(newdir+inc));
              pt.y += (int)(dist*Math.sin(newdir+inc));
              return newdir+inc;
           }
           x1 = pt.x + (int)(trydist*Math.cos(newdir-inc));
           y1 = pt.y + (int)(trydist*Math.sin(newdir-inc));
           if(!tooclose(x1,y1,path/6,lwalls)) {
              pt.x += (int)(dist*Math.cos(newdir-inc));
              pt.y += (int)(dist*Math.sin(newdir-inc));
              return newdir-inc;
           }
        }
     }
  }
  //-----------------------------------------------------------
  short nextWallToExit(short cell) {
     short i,j;
     short c[] = cells[cell];
     for(i=0; i<c.length; ++i) {
        if (walls[c[i]].missing
           &&  (walls[c[i]].outside
               || (j = adjacent(cell,c[i])) >= 0
                   && fromexit[j] < fromexit[cell])) return c[i];
     }
     return -1;
  }
  //-----------------------------------------------------------
        // returns new direction
  double movetoexit(Point pt,int dist,animal a) {
     Point oldpt = new Point(pt);
     int x1,x2=0,y1,y2=0;
     short i;
     if(a.fixed) return 0;
     if(!a.outside) {
        short wno = nextWallToExit(a.cell);
        if(wno == -1) return(0);
        Polygon p = walls[wno].p;
        int x = (p.xpoints[0] +  p.xpoints[p.npoints-1])/2, wx=x;
        int y = (p.ypoints[0] +  p.ypoints[p.npoints-1])/2, wy=y;
        int d1 = (int)Math.sqrt((pt.x-x)*(pt.x-x) + (pt.y-y)*(pt.y-y));
        if(d1>dist) {
           x = pt.x + (x - pt.x)*dist/d1;
           y = pt.y + (y - pt.y)*dist/d1;
        }
        else gotonextcell(a,wno);
        if(!movecloseto(pt,x,y)) {
           pt.x = wx;
           pt.y = wy;
        }
     }
     else {
        if(a.curredge != a.closestOnPoly) {
           Polygon p =  outerpoly;
           x1 = p.xpoints[a.curredge];
           y1 = p.ypoints[a.curredge];
           i=a.curredge;
           loop1:while(i != a.closestOnPoly) {
              if(i<p.npoints && (p.xpoints[i]-x1)*(p.xpoints[i]-x1)
                  +(p.ypoints[i]-y1)*(p.ypoints[i]-y1) > dist*dist){
                 int rad = pathwidth,radsq = rad*rad*7/8;
                 x1 = p.xpoints[i];
                 y1 = p.ypoints[i];
                 for(double an=0;an<Math.PI*2;an += Math.PI/32) {
                    x2 = x1 + (int)(rad*Math.cos(an));
                    y2 = y1 + (int)(rad*Math.sin(an));
                    if(!outerpoly.contains(x2,y2)
                      && u.mindist(outerpoly,x2,y2) >= radsq)
                            break loop1;
                 }
              }
              if(i>a.closestOnPoly
                   && outerpoly.npoints-i+a.closestOnPoly < i-a.closestOnPoly
                 || i<a.closestOnPoly
                   && outerpoly.npoints-a.closestOnPoly+i > a.closestOnPoly-1) {
                 if(++i > p. npoints) i=0;
              }
              else    if(--i < 0 ) i = (short)(p.npoints-1);
           }
           pt.x = x2; pt.y = y2;
           a.curredge=i;
        }
        if(a.curredge == a.closestOnPoly) {
           int dx = a.target.x-oldpt.x;
           int dy = a.target.y-oldpt.y;
           int dist2 = (int)Math.sqrt(dx*dx+dy*dy);
           if(dist  <  dist2) {
              pt.x = oldpt.x + dx * dist/dist2;
              pt.y = oldpt.y + dy * dist/dist2;
           }
           else {      // ended
              pt.x = a.target.x;
              pt.y = a.target.y;
              a.fixed = true;
              return 0;
           }
        }
     }
     return Math.atan2(pt.y-oldpt.y, pt.x - oldpt.x);
  }
  //--------------------------------------------------------------
  public boolean stillactive() {
     for(short i=0; i<atot;++i) {
        if(!animals[i].fixed) return true;
     }
     return false;
  }
  //--------------------------------------------------------------
  void findcell(animal a) {
     short i;
     Point pt = new Point(a.rcentre.x-rcentre.x,
                          a.rcentre.y-rcentre.y);
     a.cell = incell(pt);
  }
  //-------------------------------------------------------------
  void gotonextcell(animal a, short wno) {
      if(fromexit[a.cell] > 0) a.cell = adjacent(a.cell,wno);
      else {
         a.outside = true;
         a.curredge = (short)u.closest(outerpoly, a.rcentre.x-rcentre.x,
                          a.rcentre.y-rcentre.y);
      }
  }
  //------------------------------------------------------------
  boolean movecloseto(Point pt, int x, int y) {
     int dist,x1,y1;
     double newdir,inc;
     short lwalls[];

     loop1: while(true) {
        dist = (int)Math.min(pathwidth/4,
                   Math.sqrt((pt.x-x)*(pt.x-x) + (pt.y-y)*(pt.y-y)));
        if(dist < pathwidth/16) return true;
        newdir = Math.atan2(y-pt.y, x-pt.x);
        lwalls = localwalls(pt.x,pt.y);
        for(inc=0;inc<Math.PI/3;inc += Math.PI/12/*64*/) {
           x1 = pt.x + (int)(dist*Math.cos(newdir+inc));
           y1 = pt.y + (int)(dist*Math.sin(newdir+inc));
           if(!tooclose(x1,y1,path/32,lwalls)) {
              pt.x = x1;
              pt.y = y1;
              continue loop1;
           }
           x1 = pt.x + (int)(dist*Math.cos(newdir-inc));
           y1 = pt.y + (int)(dist*Math.sin(newdir-inc));
           if(!tooclose(x1,y1,path/32,lwalls)) {
              pt.x = x1;
              pt.y = y1;
              continue loop1;
           }
        }
        return false; // cant move
     }
  }
  //------------------------------------------------------------
  short movecloseto2(Point pt, short fromcell, int x, int y) {
     short tocell = incell(new Point(x,y)),savecell = -1;
     int dist=0x7fffffff,d2,x1,y1,i;
     Point pt2;

     if(fromcell==tocell) {
        for(i=0;i<adjcells[fromcell].length;++i) {
           tocell = adjcells[fromcell][i];
           if(u.overline(x,y,cellmiddle[fromcell].x,cellmiddle[fromcell].y,
                            cellmiddle[tocell].x,cellmiddle[tocell].y)
               && (d2=(int)u.dist(x,y,cellmiddle[fromcell].x,cellmiddle[fromcell].y,
                            cellmiddle[tocell].x,cellmiddle[tocell].y)) < dist) {
                dist = d2;
                savecell = tocell;
           }
        }
        if(savecell>=0) {
           tocell = savecell;
           pt2 = u.closest(x,y,cellmiddle[fromcell].x,cellmiddle[fromcell].y,
                            cellmiddle[tocell].x,cellmiddle[tocell].y);
           pt.x = pt2.x; pt.y = pt2.y; return fromcell;
        }
        else return fromcell;
     }
     else if(areadjacent(fromcell,tocell)) {
        if(u.overline(x,y,cellmiddle[fromcell].x,cellmiddle[fromcell].y,
                            cellmiddle[tocell].x,cellmiddle[tocell].y)) {
           pt2 = u.closest(x,y,cellmiddle[fromcell].x,cellmiddle[fromcell].y,
                            cellmiddle[tocell].x,cellmiddle[tocell].y);
           pt.x = pt2.x; pt.y = pt2.y; return tocell;
        }
        else   {
           pt.x = cellmiddle[tocell].x; pt.y = cellmiddle[tocell].y;
           return movecloseto2(pt,tocell,x,y);
        }
     }
     return fromcell;
  }
  //-----------------------------------------------------------
  boolean areadjacent(short c1,short c2) {
     for(short i=0;i<adjcells[c1].length;++i)
               if(adjcells[c1][i] == c2) return true;
     return false;
  }
  //-----------------------------------------------------------
  Polygon cellpoly(short cellno) {
     short list[] = cells[cellno];
     boolean used[] = new boolean[list.length];
     Polygon p = new Polygon(),p2;
     short i,j;
     short totused;

     for(i = 0;i<list.length;++i) used[i]=false;
     p2 = walls[list[0]].p;
     for(j=0;j<p2.npoints;++j) {
        p.addPoint(p2.xpoints[j],p2.ypoints[j]);
     }
     used[0] = true;
     totused = 1;
     while(totused<list.length)   {
        for(i = 0;i<list.length;++i) {
           if(!used[i]) {
               p2 = walls[list[i]].p;
               if(p2.xpoints[0] == p.xpoints[p.npoints-1]
                     &&   p2.ypoints[0] == p.ypoints[p.npoints-1])  {
                  for(j=1;j<p2.npoints;++j) {
                     p.addPoint(p2.xpoints[j],p2.ypoints[j]);
                  }
                  used[i] = true;
                  ++totused;
                  break;
               }
               else if(p2.xpoints[p2.npoints-1] == p.xpoints[p.npoints-1]
                     &&   p2.ypoints[p2.npoints-1] == p.ypoints[p.npoints-1])  {
                  for(j=(short)(p2.npoints-2);j>=0;--j) {
                     p.addPoint(p2.xpoints[j],p2.ypoints[j]);
                  }
                  used[i] = true;
                  ++totused;
                  break;
               }
           }
        }
     }
     return p;
  }
 //-----------------------------------------------------------
  void addcell(short list[],short listtot) {
     short newlist[] = new short[listtot];
     System.arraycopy(list,0,newlist,0,listtot);
     if(celltot >= cells.length-1) {
        short newcell[][] = new short[cells.length + 50][];
        System.arraycopy(cells,0,newcell,0,cells.length);
        cells = newcell;
     }
     cells[celltot++] = newlist;
  }
  //------------------------------------------------------------
  short addwall(Polygon p,boolean extra) {
     Polygon p2;
     short i,j;
     wall w = new wall();
     outer: for(i=0;i<walltot;++i) {
        p2 = walls[i].p;
        if(p2.npoints==p.npoints) {
           for(j=0;j<p.npoints;++j) {
               if(p2.xpoints[j] != p.xpoints[j]
                   || p2.ypoints[j] != p.ypoints[j]) break outer;
           }
           return i;      //all points the same
        }
     }
     if(walltot >= walls.length-1) {
        wall newwall[] = new wall[walls.length + 50];
        System.arraycopy(walls,0,newwall,0,walls.length);
        walls = newwall;
     }
     w.p = p;
     w.extra = extra;
     walls[walltot++] = w;
     return(short)(walltot-1);
  }
  //-----------------------------------------------------------
  Polygon fitPolygon(Polygon p, int x1, int y1, int w1,int h1) {
     Polygon p1 = new Polygon();
     Rectangle r = p.getBounds();
     int tox = x1 + w1/2;
     int toy = y1 + h1/2;
     int fromx = r.x + r.width/2;
     int fromy = r.y + r.height/2;
     int i,j,k,lastx=0,lasty=0;

     for(i = 0; i < p.npoints; ++i) {
        x = tox + (p.xpoints[i] - fromx)* w1 / r.width;
        y = toy + (p.ypoints[i] - fromy)* h1 / r.height;
        if(i>0 && (k = (lastx - x)*(lastx - x)
                   + (lasty - y)*(lasty - y)) > path/16) {
           k = (int) Math.sqrt((double)k * 16 / path)+1;
           for(j=1;j<k;++j) {
              p1.addPoint(lastx + (x-lastx)*j/k, lasty + (y-lasty)*j/k);
           }
        }
        p1.addPoint(lastx=x,lasty=y);
     }
     x = p1.xpoints[0];
     y = p1.ypoints[0];
     if((k = (lastx - x)*(lastx - x)
                   + (lasty - y)*(lasty - y)) > path/16) {
           k = (int) Math.sqrt((double)k * 16 / path)+1;
           for(j=1;j<k;++j) {
              p1.addPoint(lastx + (x-lastx)*j/k, lasty + (y-lasty)*j/k);
           }
     }
     for(i = 1; i < p.npoints; ++i) {
        if((p.xpoints[i]-p.xpoints[i-1])*(p.xpoints[i]-p.xpoints[i-1])
            +  (p.ypoints[i]-p.ypoints[i-1])*(p.ypoints[i]-p.ypoints[i-1]) > path/8) {
          j=i;
        }
     }
     return p1;
   }
  //-----------------------------------------------------------
                     // ensure all lines < pathwidth/4
  Polygon padPolygon(Polygon p) {
     Polygon p1 = new Polygon();
     Rectangle r = p.getBounds();
     int ii,i,j,k,lastx=0,lasty=0;

     x = p.xpoints[0];
     y = p.ypoints[0];
     p1.addPoint(lastx=x,lasty=y);
     for(ii = 1; ii <= p.npoints; ++ii) {
        i = ii%p.npoints;
        x = p.xpoints[i];
        y = p.ypoints[i];
        if((k = (lastx - x)*(lastx - x)
                   + (lasty - y)*(lasty - y)) > path/16) {
           k = (int) Math.sqrt(k * 16 / path)+1;
           for(j=1;j<k;++j) {
              p1.addPoint(lastx + (x-lastx)*j/k, lasty + (y-lasty)*j/k);
           }
        }
        p1.addPoint(lastx=x,lasty=y);
     }
     return p1;
   }
  //-----------------------------------------------------------
  public void openExit() {
       walls[exitwall].missing = true;
  }
  //-----------------------------------------------------------
  public void setforexit(int x, int y) {
     frozenanimal.exit = true;
     findcell(frozenanimal);
     frozenanimal.target = new Point(x, y);
     frozenanimal.closestOnPoly = u.closest(outerpoly,
                                            frozenanimal.target.x,
                                            frozenanimal.target.y);
     frozenanimal = null;
     nozapp = sharkGame.gtime()+2000;
  }
  //-----------------------------------------------------------
  void paintm(Graphics g,long t)  {    //  3d paint routine
     int x,y,i,j,k,start,len;
     point3d_base curr;
     point3d_base p3d[],p3dbot[];
     animal a;
     wall3d wall;
     int spritedim=pathwidth*screenmax/BASEU;
     mazesprite.w =  spritedim* mover.WIDTH/screenwidth;
     mazesprite.h =  spritedim* mover.HEIGHT/screenheight;
     mazesprite.manager = manager;
     // for touchscreens - else sprite appears off top left
     if(manager.mousex<=0 && manager.mousey<=0){
        Point pt2 = manager.getLocationOnScreen();
        manager.mousemoved(sharkStartFrame.mouseonscreenx - pt2.x,
                                sharkStartFrame.mouseonscreeny - pt2.y);
     }
     if(!gotspritepos && manager.mousex >= 0) {
      spritept.x  = convertx(manager.mousexs,rcentre.z)-rcentre.x;
      spritept.y  = converty(manager.mouseys,rcentre.z)-rcentre.y;
      if((spritecell =  incell(spritept)) >=0) {
         gotspritepos=true;
         lastcatch = sharkGame.gtime();
      }
     }
     if(threeD
       && (newwall == null
        ||   (spritept.x - lastspritept.x) * (spritept.x - lastspritept.x)
           + (spritept.y - lastspritept.y) * (spritept.y - lastspritept.y)
                >  path * 9
        || oldwidth != manager.screenwidth
        || oldheight != manager.screenheight)) {
        oldwidth = manager.screenwidth;
        oldheight = manager.screenheight;
        manager.viewerx = spritept.x + rcentre.x;
        manager.viewery = spritept.y + rcentre.y;
        lastspritept = new Point(spritept);
        curr = transform(new point3d_base(lastspritept.x,lastspritept.y,0));

        newwall = new wall3d[walltot*2];
        wtot = 0;
        for(i=0;i<walltot;++i) {
           if(!walls[i].missing) {
              p3d = transform(walls[i].p,-wallheight);
              p3dbot = transform(walls[i].p,0);
              len = p3d.length;
              start=0;
              for(j=0;j<len;++j) {
                 if(j>start && (j == len-1 || (p3d[j].x - p3d[j-1].x)*(p3d[j+1].x - p3d[j].x)<1)) {
                    wall = new wall3d();
                    wall.p = new Polygon();
                    wall.ptop = new Polygon();
                    for(k=start;k<=j;++k) {
                       wall.p.addPoint(p3d[k].x,p3d[k].y);
                       wall.ptop.addPoint(p3d[k].x,p3d[k].y);
                    }
                    wall.dist = (curr.x-p3dbot[start].x)*(curr.x-p3dbot[start].x)
                          + (curr.y-p3dbot[start].y)*(curr.y-p3dbot[start].y)
                          + (curr.x-p3dbot[j].x)*(curr.x-p3dbot[j].x)
                          + (curr.y-p3dbot[j].y)*(curr.y-p3dbot[j].y);
                    for(k=j;k>=start;--k) {
                        wall.p.addPoint(p3dbot[k].x,p3dbot[k].y);
                    }
                    if(wtot >= newwall.length-1) {
                       wall3d neww[] = new wall3d[wtot+20];
                       System.arraycopy(newwall,0,neww,0,wtot);
                       newwall = neww;
                    }
                    newwall[wtot++] = wall;
                    start = j;
                 }
              }
           }
        }
        for(i=0;i<wtot;++i) {     //sort
           for(j=0;j<i;++j) {
              if(newwall[j].dist < newwall[i].dist) {
                 wall = newwall[j];
                 newwall[j] = newwall[i];
                 newwall[i] = wall;
               }
            }
        }
     }
     else curr = transform(new point3d_base(spritept.x,spritept.y,0));

     for(i=0;i<atot;++i) {  //fix animals
        a = animals[i];
        a.moveanimal(t);
        if(!lettermaze)  a.distinmaze =  ((curr.x - a.rcentre.x) * (curr.x - a.rcentre.x)
                        + (curr.y - a.rcentre.y) * (curr.y - a.rcentre.y))*2;
        else  a.distinmaze = 0;
     }
     if(threeD) for(i=0;i<atot;++i) {     //sort animals
        for(j=0;j<i;++j) {
           if(animals[j].distinmaze < animals[i].distinmaze) {
              a = animals[j];
              animals[j] = animals[i];
              animals[i] = a;
            }
         }
     }
     if(!threeD) {
       if(buffer == null || buffer.getWidth(null) != manager.screenwidth
                   || buffer.getHeight(null) != manager.screenheight) {
           buffer = runMovers.buffers[2];
           draw2d(buffer.getGraphics());
        }
        g.drawImage(buffer,0,0,manager.screenwidth,manager.screenheight, 0,0,manager.screenwidth,manager.screenheight, null);
        for(i=0;i<atot;++i) {
           animals[i].paint(g);
        }
     }
     else for(i=0,j=0;i<wtot || j< atot;) {    //display
        if(i < wtot
              && (j>=atot || newwall[i].dist > animals[j].distinmaze)) {
           g.setColor(Color.black);
           g.fillPolygon(newwall[i].p);
           g.setColor(Color.white);
           g.drawPolyline(newwall[i].ptop.xpoints,newwall[i].ptop.ypoints,newwall[i].ptop.npoints);
           ++i;
        }
        else {
           animals[j].paint(g);
           ++j;
        }
     }
     if(zapping != null) {
        if(t>zappend) zapping = null;
        else {
           int pw = pathwidth*screenmax/BASEU;
           g.setColor(Color.red);
           g.fillPolygon(u.explode(curr.x,curr.y,pw/2,pw*2));
        }
     }
     if(zapping == null && frozenanimal==null) cursorpos();
     point3d_base pt = transform(new point3d_base(spritept.x,spritept.y,0));
     int spx = pt.x-spritedim/2; int spy=pt.y-spritedim/2;
     if(zapping == null && frozenanimal==null){
        if(manager.sprite!=null && !mazesprite.name.equals(manager.sprite.name))
          mazesprite = manager.sprite;
        mazesprite.paint(g,
         spx,spy,spritedim,spritedim);
     }
     extradraw(g,t,spx,spy);
  }
  //---------------------------------------------------------------
  void draw2d(Graphics g) {
           int  xp[],yp[],i,j,k;
           point3d_base pt[];
           g.setColor(manager.bg);
           g.fillRect(0,0,manager.screenwidth, manager.screenheight);
           g.setColor(Color.white);
           /*if(wall2d == null)*/ wall2d = new Polygon[walltot];
           for(i=0;i<walltot;++i) {
              if(!walls[i].missing) {
                 if(wall2d[i] == null)  {
                    pt = transform(walls[i].p,0);
                    wall2d[i] = new Polygon();
                    for(j=0;j<walls[i].p.npoints;++j) {
                       wall2d[i].addPoint(pt[j].x,pt[j].y);
                    }
                 }
                 g.drawPolyline(wall2d[i].xpoints,wall2d[i].ypoints,wall2d[i].npoints);
              }
           }
  }

  //---------------------------------------------------------------
  public void extradraw(Graphics g, long t, int x, int y) {};  // to be overridden
  //--------------------------------------------------------------
  public void cursorpos() {
    int mmx,mmy;
//     movecloseto(spritept,(manager.mousexs-screenwidth/2)*BASEU/manager.screenmax*rcentre.z/BASEZ,
//                     (manager.mouseys-screenheight/2)*BASEU/manager.screenmax*rcentre.z/BASEZ);
     movecloseto(spritept,mmx=convertx(manager.mousexs,rcentre.z)-rcentre.x,
               mmy=converty(manager.mouseys,rcentre.z)-rcentre.y);
     spritecell=incell(spritept);
     if(spritecell < 0) {gotspritepos = false;return;}
     if((mmx-spritept.x)*(mmx-spritept.x) + (mmy-spritept.y)*(mmy-spritept.y) < path/4) {
       if(iscursor) {
          manager.setCursor(sharkStartFrame.nullcursor);
          iscursor=false;
       }
     }
     else  {
        if(!iscursor) {
            manager.setCursor(null);
            iscursor = true;
        }
     }
  }
  //-------------------------------------------------------------
  public void addAnimal() {
     if(atot >= animals.length-1) {
        animal newa[] = new animal[atot+5];
        System.arraycopy(animals,0,newa,0,atot);
        animals = newa;
     }
     switch(atot%4) {
        case 0: animals[atot] = new snake(u.rand(celltot));  break;
        case 1: animals[atot] = new crab(u.rand(celltot));   break;
        case 2: animals[atot] = new jellyfish(u.rand(celltot));   break;
        case 3: animals[atot] = new snapper(u.rand(celltot));   break;
//       case 4: animals[atot] = new swordfish(u.rand(celltot));   break;
     }
     animals[atot].manager = manager;
     ++atot;
  }
  //-------------------------------------------------------------
  public boolean addLetters(String s[], short curr, short tot) {
     short len = (short) s.length;
     short i,j,cellno,ok,limit = 8;
     Color color = u.brightcolor();

     if(atot+len >= animals.length) {
        animal newa[] = new animal[atot+len+10];
        System.arraycopy(animals,0,newa,0,atot);
        animals = newa;
     }
     cellno = (short)(celltot*curr/tot);
     short fromthis[] = new short[celltot];
     for(i=0;i<celltot;++i) fromthis[i] = celltot;
     assignexitcount(cellno,fromthis,(short)0);
     short okcells[] = new short[celltot];
     loop1:while(true) {
        ok = 0;
        loop2:for(i=0;i<celltot;++i) {
            if(fromthis[i] < limit) {
               for(j=0;j<atot;++j)
                   if(animals[j].cell == i) continue loop2;
               okcells[ok++] = i;
            }
        }
        if(ok >= len) break loop1;
        else if(++limit > celltot) return false;
     }
     for(i=0;i<len;++i) {
        if(s[i].equals(" ")) continue;
        cellno = okcells[j=u.rand(ok)];
        animals[atot] = new letter(cellno,s[i],manager);
        animals[atot].color = color;
        animals[atot].manager = manager;
        ++atot;
        System.arraycopy(okcells,j+1,okcells,j,(--ok)-j);
     }
     return true;
  }
  //------------------------------------------------------------
  boolean spriteover(animal a, boolean samecell) {
     if(spritecell < 0 || a.cell < 0) return false;
     if(a.cell != spritecell && (samecell || !nextcell(a.cell,spritecell))) return false;
     if(!freeform) return true;
     int xs = spritept.x;
     int ys = spritept.y;
     int x = a.rcentre.x-rcentre.x;
     int y = a.rcentre.y-rcentre.y;

     return ((xs-x)*(xs-x) + (ys-y)*(ys-y) < path);
  }
  //-------------------------------------------------------------
  public boolean attackanimal() {
    animal a;
    Point pt;
    if(zapping != null || sharkGame.gtime() < zappend+600) return false;
    for(short i = 0;i<atot;++i) {
       a  = animals[i];
       pt = new Point(spritept.x,spritept.y);
       if(!a.exit && spriteover(a,false)) {
          animalcolor = a.color;
          frozenanimal = animals[i];
          return true;
       }
    }
    return false;
  }
  //-------------------------------------------------------------
  public String attackletter() {
    animal a;

    int possible = -1;
    for(short i = 0;i<atot;++i) {
       a  = animals[i];
       if(!a.exit && spriteover(a,false)) {
          if(a.cell == spritecell) {
             frozenanimal = a;
             return ((letter)a).s;
          }
          else possible = i;
       }
    }
    if (possible >=0) {
             frozenanimal = animals[possible];
             return ((letter)animals[possible]).s;
    }
    return null;
  }
  //----------------------------------------------------------
  public void aftercopyscreen() {
     for(short i = 0;i<atot;++i) {
       animals[i].oldcentre.x = animals[i].centre.x;
       animals[i].oldcentre.y = animals[i].centre.y;
     }
  }
  //--------------------------------------------------------------
  class animal extends mover3d {
      short cell,curredge;
      boolean exit,attack,outside,fixed;
      Point target;
      int closestOnPoly;
      int distinmaze;
      long lasttime;
      Color color = u.brightcolor();
      Point oldcentre = new Point(0,0);
      public animal(short cellno) {
         super();
         cell = cellno;
         Point pt = cellmiddle[cellno];
         rcentre = new point3d_base(pt.x+thismaze.rcentre.x, pt.y+thismaze.rcentre.y, thismaze.rcentre.z + animalwidth/2);
         lasttime = sharkGame.gtime();
         while(incell(pt) != cellno) {
           cellno = u.rand(celltot);
           pt = cellmiddle[cellno];
           rcentre = new point3d_base(pt.x+thismaze.rcentre.x, pt.y+thismaze.rcentre.y, thismaze.rcentre.z + animalwidth/2);
           cell = cellno;
         }
      }
      public boolean move(long t) {
         return(true);
      }
      void moveanimal(long t) {
         if(manager==null || manager.currgame == null) return;
         Point pt = new Point(rcentre.x-thismaze.rcentre.x,
                          rcentre.y-thismaze.rcentre.y);
         int speed = pathwidth *  ((int)(t-lasttime))
                          / (300+(8-manager.currgame.speed)*100);
         double dd;
         if(exit) raz = thismaze.movetoexit(pt,
               pathwidth *  ((int)(t-lasttime)) / 80, this);
         else if(this != frozenanimal && this != zapping
                         && !(this instanceof letter))
               raz = thismaze.moverand(pt,raz, speed)+Math.PI*2;
         while(raz>Math.PI*2)  raz -= Math.PI*2;
         rcentre.x = pt.x+thismaze.rcentre.x;
         rcentre.y = pt.y+thismaze.rcentre.y;
         point3d_base newcentre = transform(rcentre);
         if(!mustclick &&  zapping == null && frozenanimal==null
                  && sharkGame.gtime() > lastcatch + 2000
                  && !exit && !(this instanceof letter)
                  && spriteover(this,true)) {
              lastcatch = sharkGame.gtime();
              startexplode();
         }
         if(mustclick && zapping == null && frozenanimal==null
                  && !exit && !(this instanceof letter)
                  && t > nozapp
                  && spriteover(this,true)
                  && (dd = raz - Math.atan2(spritept.x*screenmax/BASEU+screenwidth/2-centre.y,
                                 spritept.y*screenmax/BASEU+screenheight/2-centre.x)) < Math.PI/4
                  && dd > - Math.PI/4 )  {
            zapping = this;
            zappend = t + 1000;
            nozapp = t + 3000;
            thisgame.death(1);
            noise.squeek();
         }
         centre = newcentre;
         lasttime = t;
         if(!exit) findcell(this);
      }
  }
  public void startexplode(){}
    //---------------------------------------------------------------
  class snake extends animal {
     long lastt = sharkGame.gtime();
     int length=animalwidth;
     int headrad = length/6;
     int mouthrad = length/12;
     int headright = length/2;
     int headleft = headright - headrad*2;
     int headmid = (headright+headleft)/2;
     int bodylen = headleft+length/2;
     int topx = 0;

     public snake(short cellno){
        super(cellno);

        vary = new randrange_base[] {new randrange_base(-length*2,length*2,length)};   // pos of 'top' wriggle
     }
     void paintm(Graphics g,long t) {
        int lx,ly;

        for(short i=0;i<vary.length;++i) {
           vary[i].next(t);
        }
                         // head -------------------
        point3d_base hmid = transform(new point3d_base(headmid,0,0));
        int hrad = headrad * screenmax /BASEU;
        g.setColor(color);
        g.fillOval(hmid.x - hrad, hmid.y - hrad,hrad*2,hrad*2);
                        // mouth -----------------------
        point3d_base mmid = transform(new point3d_base(headright-mouthrad,0,0));
        g.setColor(Color.red);
        int mrad = mouthrad * screenmax /BASEU;
        g.fillOval(mmid.x - mrad, mmid.y - mrad, mrad*2, mrad*2);

                        // body ------------------------
        g.setColor(color);
        topx += vary[0].currval*(t-lastt)/1000;
        while(topx > headleft) topx -= bodylen;
        while(topx < -length/2) topx += bodylen;
        int topy = (headleft-topx) * length/ 2 / bodylen;
        int botx = topx + bodylen/2;
        int bx1 = -length/2;
        int bx2 = topx, bx3 = botx;
        int by2 = topy,  by3, by1;
        if(botx > headleft) {
           bx2 = (botx -= bodylen);
           by2 = -(headleft-botx) * length/ 2 / bodylen;
           bx3 = topx;
           by3 = topy;
           by1 = - (length/2 - length/2 * (bx2-bx1)/(bodylen/2));
        }
        else {
           by3 = -(headleft-botx) * length/ 2 / bodylen;
           by1 = length/2 - length/2 * (bx2-bx1)/(bodylen/2);
        }
        double leftangle = Math.atan2(by2-by1, bx2-bx1);

        point3d_base left = transform(new point3d_base(bx1,by1,0));
        point3d_base mid1 = transform(new point3d_base(bx2,by2,0));
        point3d_base mid2 = transform(new point3d_base(bx3,by3,0));
        point3d_base right = transform(new point3d_base(headleft,0,0));

        Polygon p = new Polygon();
        u.drawcurve(p,left.x, left.y, leftangle+raz, mid1.x,mid1.y,raz);
        u.drawcurve(p,mid1.x,mid1.y,raz,mid2.x,mid2.y,raz);
        u.drawcurve(p,mid2.x,mid2.y,raz,right.x,right.y,raz);
        g.drawPolyline(p.xpoints,p.ypoints,p.npoints);
        lastt = t;
     }
  }
   //-----------------------------------------------------------
  class crab extends animal {
     long lastt = sharkGame.gtime();
     int length=animalwidth;
     int bodyright = 0;
     int bodyleft = -length/2;
     int bodymid = -length/4;
     int bodyrad = length/4;
     int leglen = length/6;
     int clawlen = length/2;
     int clawrad = length/5;
     double legangle[] = new double[] {-Math.PI*7/12,
                                       -Math.PI*9/12,
                                       Math.PI*7/12,
                                       Math.PI*9/12};

     public crab(short cellno){
        super(cellno);

        vary = new randrange_base[] {new randrange_base(0,100,100),  // claw 1 angle
                                new randrange_base(0,100,100), // claw 2 angle
                                new randrange_base(-110,-70,500), // leg angles
                                new randrange_base(-110,-70,500), // leg angles
                               new randrange_base(70,110,500), // leg angles
                                new randrange_base(70,110,500), // leg angles
                                };
     }
     void paintm(Graphics g,long t) {
        int lx,ly;

        for(short i=0;i<vary.length;++i) {
           vary[i].next(t);

        }
                         // body -------------------
        point3d_base bmid = transform(new point3d_base(bodymid,0,0));
        int brad = bodyrad * screenmax /BASEU;
        g.setColor(color);
        g.fillOval(bmid.x-brad, bmid.y-brad,brad*2+1,brad*2+1);
                        // claws -------------------------
        double cangle1 = Math.PI/8 + vary[0].currval*Math.PI/500;
        double cangle2 = - Math.PI/8 - vary[1].currval*Math.PI/500;
        int crad = clawrad * screenmax /BASEU;
        point3d_base cend = transform(new point3d_base(bodymid+(int)(clawlen*Math.cos(cangle1)),(int)(clawlen*Math.sin(cangle1)),0));
        g.drawLine(bmid.x,bmid.y,cend.x,cend.y);
        int cdegrees = (int)((-cangle1-raz+Math.PI*2)*180/Math.PI);
        g.fillArc(cend.x - crad,cend.y - crad, crad*2,crad*2,
                      (cdegrees+45)%360,270);

        cend = transform(new point3d_base(bodymid+(int)(clawlen*Math.cos(cangle2)),(int)(clawlen*Math.sin(cangle2)),0));
        g.drawLine(bmid.x,bmid.y,cend.x,cend.y);
        cdegrees = (int)((-cangle2-raz+Math.PI*2)*180/Math.PI);
        g.fillArc(cend.x - crad,cend.y - crad, crad*2,crad*2,
                      (cdegrees+45)%360,  270);

                       // legs ---------------------------------
        point3d_base lstart,lend;
        double langle;
        for(short i=0; i<4;++i) {
           langle =  Math.PI * vary[i+2].currval / 180;
           lstart =   transform(new point3d_base(lx = bodymid+(int)(bodyrad*Math.cos(legangle[i])),ly=(int)(bodyrad*Math.sin(legangle[i])),0));
           lend = transform(new point3d_base(lx+(int)(leglen*Math.cos(langle)),ly+(int)(leglen*Math.sin(langle)),0));
           g.drawLine(lstart.x,lstart.y,lend.x,lend.y);
        }
        lastt = t;
     }
  }
   //-----------------------------------------------------------
  class jellyfish extends animal {
     long lastt = sharkGame.gtime();
     int length=animalwidth;
     int bodyright = 0;
     int bodyrad = length/2;
     short tenttot = 8;
     int eyerad = length/7;
     int eyey = length/5;

     public jellyfish(short cellno){
        super(cellno);

        vary = new randrange_base[] {new randrange_base(0,100,100),  // claw 1 angle
                                new randrange_base(0,100,100), // claw 2 angle
                                new randrange_base(-110,-70,500), // leg angles
                                new randrange_base(-110,-70,500), // leg angles
                               new randrange_base(70,110,500), // leg angles
                                new randrange_base(70,110,500), // leg angles
                                };
     }
     void paintm(Graphics g,long t) {
        int lx,ly;

        for(short i=0;i<vary.length;++i) {
           vary[i].next(t);

        }
                         // body -------------------
        point3d_base bmid = transform(new point3d_base(0,0,0));
        int brad = bodyrad * screenmax /BASEU;
        g.setColor(color);
        int bdegrees = (int)((-raz+Math.PI*2)*180/Math.PI);
        g.fillArc(bmid.x-brad, bmid.y-brad,brad*2+1,brad*2+1,(bdegrees+270)%360,180);
                        // tentacles -------------------------
        point3d_base tent;
        int yrad = length/2 * screenmax /BASEU,yy;
        Polygon p;
        for(short i=0; i<tenttot; ++i) {
           tent = transform(new point3d_base(0,yy = -length/2 + length*i/(tenttot-1),0));
           yy =  yy  * screenmax /BASEU;
           p = u.wavyline(tent.x,tent.y,length/2*screenmax/BASEU, raz + Math.PI,
                            -yy - yrad, yrad - yy);
           g.drawPolyline(p.xpoints,p.ypoints,p.npoints);
        }
                        // eyes ---------------------------------
        int erad = eyerad* screenmax /BASEU;
        g.setColor(Color.red);
        point3d_base mideye = transform(new point3d_base(length/2-eyerad,-eyey,0));
        g.fillOval(mideye.x-erad,mideye.y-erad,erad*2,erad*2);
        mideye = transform(new point3d_base(length/2-eyerad,eyey,0));
        g.fillOval(mideye.x-erad,mideye.y-erad,erad*2,erad*2);

        lastt = t;
     }
   }
   //-----------------------------------------------------------
  class snapper extends animal {
     long lastt = sharkGame.gtime();
     int length=animalwidth;
     int taily = length/2;
     int bodymid = 0;
     int bodyrad = length/2;
     int eyerad = length/7;
     int eyey = length*5/16;
     int eyex = length/8;

     public snapper(short cellno){
        super(cellno);

        vary = new randrange_base[] {new randrange_base(0,45,200)  // angle
                                                 };
     }
     void paintm(Graphics g,long t) {
        int lx,ly;

        for(short i=0;i<vary.length;++i) {
           vary[i].next(t);

        }
                         // body -------------------
        point3d_base bmid = transform(new point3d_base(bodymid,0,0));
        int brad = bodyrad * screenmax /BASEU;
        int angle = vary[0].currval;  // in degrees
        g.setColor(color);
        int bdegrees = (int)((-raz+Math.PI*2)*180/Math.PI);
        g.fillArc(bmid.x-brad, bmid.y-brad,brad*2+1,brad*2+1,
                      (bdegrees+angle)%360,360-angle*2);
                        // tail -------------------------
        point3d_base top = transform(new point3d_base(-length/2,-taily,0));
        point3d_base bot = transform(new point3d_base(-length/2,taily,0));
        g.fillPolygon(new int[]{bmid.x,top.x,bot.x},
                      new int[]{bmid.y,top.y,bot.y}, 3);
                        // eye ---------------------------------
        int erad = eyerad* screenmax /BASEU;
        point3d_base mideye;
        g.setColor(Color.black);
        eyex = length/8 - length*angle/5/45;
        if(Math.cos(raz) >= 0)
           mideye = transform(new point3d_base(eyex,-eyey,0));
        else
           mideye = transform(new point3d_base(eyex,eyey,0));
        g.fillOval(mideye.x-erad,mideye.y-erad,erad*2,erad*2);
        lastt = t;
     }
   }
   //-----------------------------------------------------------
  class swordfish extends animal {
     long lastt = sharkGame.gtime();
     int length=animalwidth;
     int taily = length/2;
     int bodymid = -length*3/16;
     int bodyrad = length*5/16;
     int swordlen = length/2;
     int swordy = length/8;
     int swordx = 0;
     int eyerad = length/7;
     int eyey = 0;
     int eyex = -length/4;

     public swordfish(short cellno){
        super(cellno);

        vary = new randrange_base[] {new randrange_base(-100,100,200)  // angle
                                                 };
     }
     void paintm(Graphics g,long t) {
        int lx,ly;

        for(short i=0;i<vary.length;++i) {
           vary[i].next(t);

        }
                         // body -------------------
        point3d_base bmid = transform(new point3d_base(bodymid,0,0));
        int brad = bodyrad * screenmax /BASEU;
        g.setColor(color);
        g.fillOval(bmid.x-brad, bmid.y-brad,brad*2+1,brad*2+1);
                        // tail -------------------------
        point3d_base top = transform(new point3d_base(-length/2,-taily,0));
        point3d_base bot = transform(new point3d_base(-length/2,taily,0));
        g.fillPolygon(new int[]{bmid.x,top.x,bot.x},
                      new int[]{bmid.y,top.y,bot.y}, 3);
                        // sword -------------------------------
        double angle = vary[0].currval*Math.PI/1000;
        point3d_base send = transform(new point3d_base(swordx+(int)(swordlen*Math.cos(angle)),(int)(swordlen*Math.sin(angle)),0));
        top =  transform(new point3d_base(swordx,-swordy,0));
        bot =  transform(new point3d_base(swordx,swordy,0));
        g.fillPolygon(new int[] {send.x,top.x,bot.x},
                      new int[] {send.y,top.y,bot.y},3);
                        // eye ---------------------------------
        int erad = eyerad* screenmax /BASEU;
        point3d_base mideye;
        g.setColor(Color.black);
        eyex = length/8;
        if(Math.cos(raz) >= 0)
           mideye = transform(new point3d_base(eyex,-eyey,0));
        else
           mideye = transform(new point3d_base(eyex,eyey,0));
        g.fillOval(mideye.x-erad,mideye.y-erad,erad*2,erad*2);
        lastt = t;

     }
   }
   //-----------------------------------------------------------
  public class letter extends animal {
     long lastt = sharkGame.gtime();
     int length=animalwidth;
     public String s;

     public letter(short cellno, String s1,runMovers manager1){
        super(cellno);
        s = s1;
        if(letterfont == null ) {
           short points = (short)sharkStartFrame.MAXFONTPOINTS_1;
           int ht = length * screenmax/BASEU;
           manager = manager1;
//startPR2007-11-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//           Font f  = new Font(sharkStartFrame.wordfont.getName(),
//                       sharkStartFrame.wordfont.getStyle(),points);
           Font f  = sharkStartFrame.wordfont.deriveFont((float)points);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
           FontMetrics m = manager.getFontMetrics(f);

           while(f.getSize() > 24 && m.getHeight() > ht && m.stringWidth(s)>ht*3/2) {
//startPR2007-11-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//               f = new Font(f.getName(), f.getStyle(), --points);
             f = f.deriveFont((float)--points);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
             m = manager.getFontMetrics(f);
           }
           letterfont = f;
           lettermetrics = m;
           letterdy =  -m.getHeight()/2+m.getMaxAscent();
        }
        rcentre.z = thismaze.rcentre.z - wallheight;
     }
     void paintm(Graphics g,long t) {
        int lx,ly;
if(shark.macOS) ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                         // body -------------------
        point3d_base mid = transform(new point3d_base(0,0,0));
        g.setFont(letterfont);
        g.setColor(color);
        if(s.equals(" ") && !fixed) g.drawRect(mid.x - lettermetrics.charWidth(' ')/2,mid.y-lettermetrics.getHeight()/2,
                                      lettermetrics.charWidth(' '),lettermetrics.getHeight());
        else g.drawString(s,mid.x - lettermetrics.stringWidth(s)/2,
                          mid.y + letterdy);
        lastt = t;
     }
   }
}
