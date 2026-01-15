/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shark;

/**
 *
 * @author paulr
 */
public class selectGameDetails {   
    int selectNoTarget = -1;
    int selectNoDistractor = -1;  
    int allocatedGood = -1;
    int allocatedBad = -1;  
    
  class selectGameBucket{
    String words[] = null;
    String heading = null;
    int selectNo = -1;
    String headingSoundFile = null;
    
    void setDetails (String wIDS[], String head, int selectNumber, String headSound, boolean isTarget){
   //     int minWords = isTarget?allocatedGood:allocatedBad;
   if(selectNumber>0){
       selectNo = selectNumber;  
   }else{
       selectNo = isTarget?selectNoTarget:selectNoDistractor;  
   }
        words = wIDS;
        heading = head;
        headingSoundFile = headSound;
    }
   }    
    
   selectGameBucket[] groups;
   
   public selectGameDetails(int selectNumberTarget, int selectNumberDistractor, int allocGood, int allocBad){
       allocatedGood = allocGood;
       allocatedBad = allocBad;
       selectNoTarget = selectNumberTarget;
       selectNoDistractor = selectNumberDistractor;
   }
 
   
  public void newBucket(String wIDs[], String head, int selectNumber, String headSound, boolean isTarget) {
    selectGameBucket sgb = new selectGameBucket();
    sgb.setDetails(wIDs, head, selectNumber, headSound, isTarget);
    add(sgb);
 }     
   
  void add(selectGameBucket s) {
      if(groups==null)groups = new selectGameBucket[]{};
     int len = (int)groups.length;
     selectGameBucket news[] = new selectGameBucket[len+1];
     System.arraycopy(groups,0,news,0,len);
     news[len] = s;
     groups = news;
 }  
  

}


