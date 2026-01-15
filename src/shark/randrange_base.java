package shark;

public class randrange_base {  // maxchange is per sec
   public int minval,maxval,maxchange,currchange;

   public int currval;
   public boolean fixed,fixedspeed,highspeed;
   int target;
   long lastt;
      public randrange_base(int minval1, int maxval1, int maxchange1) {
      minval=minval1;
      maxval=maxval1;
      maxchange=maxchange1;
      currval = minval + u.rand(maxval-minval);
      currchange = -maxchange + u.rand(maxchange*2+1);
      lastt=sharkGame.gtime();
   }
   public void setcurr(int val) {
      currval = val;
      lastt = sharkGame.gtime();
   }
   public int next(long t) {
      int maxc = (int)(maxchange*Math.min(t-lastt,500)/1000);
      if(fixed) {
         if(target != currval) {
            if(target > currval)  {
               currchange -= maxc;
               if((currval+=currchange*(t-lastt)/1000) <= currval) {
                  currchange = 0;
                  currval = target;
               }
            }
            else {
               currchange += maxc;
               if((currval+=currchange*(t-lastt)/1000) >= currval) {
                  currchange = 0;
                  currval = target;
               }
            }
         }
      }
      else {
         if(!fixedspeed) {
            int change = Math.max(2,maxc/5);
            currchange = Math.max(-maxchange,Math.min(maxchange,
                    currchange - change + u.rand(change*2+1)));
         }
         if(highspeed && Math.abs(currchange) < maxchange/2) {
            if(currchange <0) currchange = -maxchange/2;
            else currchange = maxchange/2;
         }
         currval+=currchange*(t-lastt)/1000;
      }
      if(currval < minval) {
         currchange = -currchange;
         currval = Math.min(maxval,minval*2-currval);
      }
      else if(currval > maxval) {
         currchange = -currchange;
         currval = Math.max(minval,maxval*2-currval);
      }
      lastt = t;
      return currval;
   }
   public int curr() {return currval;}
}
