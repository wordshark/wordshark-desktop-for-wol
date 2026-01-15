package shark.games;

import shark.*;

public class cloze extends sharkGame {
   int startx = screenwidth/40, curry;
   public cloze() {
      errors = true;
      gamescore1 = true;
      listen= true;
      peep = true;
//      rgame.options |= word.CENTRE;
      buildTopPanel();
   }
}
