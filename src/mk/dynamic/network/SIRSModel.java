package mk.dynamic.network;

/**
 *
 * @author mkirschner
 */
public class SIRSModel implements Rule {
    Grid grid; 
    
    public SIRSModel(Grid grid) {
        this.grid = grid;
    }
    
    @Override
    public void performRule() {
        for (Cell [] cellArray : this.grid.grid) {
            for (Cell c : cellArray) {
                Node n = c.node;
                   
                n.susceptiblePopTmp = n.susceptiblePop + -1*n.contactRate*n.susceptiblePop*n.infectivePop +n.remToSucRate*n.removedPop;
                
                n.infectivePopTmp = n.infectivePop + n.contactRate*n.susceptiblePop*n.infectivePop + -1*n.removalRate*n.infectivePop;
                
                n.removedPopTmp = n.removedPop +  n.removalRate*n.infectivePop -n.remToSucRate*n.removedPop;
                 
                for (Node n2 : n.friends) {
                    double infNeighb=(n.contactRate/3)*n.susceptiblePop*n2.infectivePop;
                    
                    n.susceptiblePopTmp += -1*infNeighb;
                    n.infectivePopTmp += infNeighb;
                }
                
                // random Miracles
                if (Math.random() < 0.00001) {
                    n.susceptiblePopTmp += 0.9*n.infectivePop;
                    n.infectivePopTmp += -1*0.9*n.infectivePop;
                    
                }
                
                 // random Disease
                if (Math.random() < 0.00001) {
                    n.susceptiblePopTmp += -1*0.9*n.susceptiblePop;
                    n.infectivePopTmp += 1*0.9*n.susceptiblePop;
                    
                }
                
//                n.susceptiblePopTmp = n.susceptiblePop + -1*n.contactRate*n.susceptiblePop*n.infectivePop +n.remToSucRate*n.removedPop;
//
//                for (Node n2 : n.friends) {
//                    n.susceptiblePopTmp += -1*(n.contactRate/3)*n.susceptiblePop*n2.infectivePop;
//                }
//
//                n.infectivePopTmp = n.infectivePop + n.contactRate*n.susceptiblePop*n.infectivePop;
//                for (Node n2 : n.friends) {
//                    n.infectivePopTmp += (n.contactRate/3)*n.susceptiblePop*n2.infectivePop;
//                }     
//                n.infectivePopTmp += -1*n.removalRate*n.infectivePop;   
//                n.removedPopTmp = n.removedPop +  n.removalRate*n.infectivePop -n.remToSucRate*n.removedPop;
            }
            
        }
        
        for (int j=0; j<this.grid.numY; j++) {
            for (int i=0; i<this.grid.numX; i++) {
                this.grid.grid[i][j].node.swap();
            }
        }
    }
}
