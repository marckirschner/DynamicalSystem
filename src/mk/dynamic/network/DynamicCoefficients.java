package mk.dynamic.network;

/**
 *
 * @author mkirschner
 */
public class DynamicCoefficients implements Rule {
    Grid grid; 
    
    public DynamicCoefficients(Grid grid) {
        this.grid = grid;
    }
    
    @Override
    public void performRule() {
        for (Cell [] cellArray : this.grid.grid) {
            for (Cell c : cellArray) {
                Node n = c.node;
                
               n.contactRate = n.contactRateMax - n.contactRateMax*(n.infectivePop);
               n.removalRate = n.removalRateMax - n.removalRate*(n.susceptiblePop);
               
            //   n.removalRate = n.removalRateMax - n.contactRate*(n.infectivePop);
                 // random Disease
               /* if (Math.random() < 0.00001) {
                    n.susceptiblePopTmp += -1*0.9*n.susceptiblePop;
                    n.infectivePopTmp += 1*0.9*n.susceptiblePop;
                    
                }*/
    
              //  for (Node n2 : n.friends) {
                //    n.infectivePopTmp += (n.contactRate/2)*n.susceptiblePop*n2.infectivePop;
                //}     
                
               
                
               // n.swap();
            }
            
        }
        
       
    }
}
