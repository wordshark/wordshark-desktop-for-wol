package shark;

public class reward_base extends gamestoplay {
  static int lastreward = -1;
  static int lastreward_flip = -1;
   //----------------------------------------------------------
   public reward_base() {
     super();
     init(false);

   }
   public reward_base(boolean flipped) {
     super();
     init(flipped);
   }

   void init(boolean flipped){
     String list[] =  flipped?sharkStartFrame.okrewards_flip:sharkStartFrame.okrewards;
     if(list.length>0)  {
        int i;
        do {
          i = u.rand(list.length);
        } while(i == (flipped?lastreward_flip:lastreward) && list.length>1);
        if(flipped)lastreward_flip = i;
        else lastreward = i;
        new runningGame(list[i],
                       sharkStartFrame.mainFrame.wordTree,
                       sharkStartFrame.mainFrame.currPlayTopic.name,true);
     }
   }
}
