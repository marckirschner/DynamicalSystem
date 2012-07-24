/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mkirschner
 */
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import java.awt.event.*;

import javax.swing.Timer;
import java.util.ArrayList;
import javax.swing.JRadioButton;

/*import mk.dynamic.network.SIRModel;
import mk.dynamic.network.SIRModelSwap;
import mk.dynamic.network.Grid;
import mk.dynamic.network.Rule;*/

import mk.dynamic.network.*;

public class DrawingSurface extends JPanel implements ActionListener {

       int mouseX,mouseY,paintBrushWidth,paintBrushHeight;
     
                
    public Grid grid;
  
    public Timer timer;
    public int delay=10;
     
    public ArrayList<Rule> ruleList;
    
    TestFrame controlPanel;
    Cell selectedCell;
    
    private void handleNodeCreation(Cell cell) {
        double pop= Double.parseDouble(controlPanel.textFieldPopulation.getText());
                
               
        if (controlPanel.radioButtonSusAdd.isSelected()) {
            if (cell.node.susceptiblePop <= 1) {
                
                cell.node.susceptiblePop += pop*(cell.node.infectivePop+cell.node.removedPop);
                cell.node.infectivePop-=pop*cell.node.infectivePop;
                cell.node.removedPop-=pop*cell.node.removedPop;
       
                
                if (cell.node.susceptiblePop >= 1) {
                    cell.node.susceptiblePop = 1;
                    cell.node.infectivePop = 0;
                    cell.node.removedPop = 0;
                }
            }
        }
        else if (controlPanel.radioButtonInfAdd.isSelected()) {
            if (cell.node.infectivePop <=1) {
                
                cell.node.infectivePop += pop*(cell.node.susceptiblePop+cell.node.removedPop);
                cell.node.susceptiblePop-=pop*cell.node.susceptiblePop;
                cell.node.removedPop-=pop*cell.node.removedPop;
       
                
                
                
                if (cell.node.infectivePop >= 1) {
                    cell.node.infectivePop = 1;
                    
                    cell.node.susceptiblePop = 0;
                    cell.node.removedPop = 0;
                }
            }
        }
        
        else if (controlPanel.radioButtonRemAdd.isSelected()) {
            if (cell.node.removedPop <=1) {
                
                cell.node.removedPop += pop*(cell.node.infectivePop+cell.node.susceptiblePop);
               
                cell.node.infectivePop-=pop*cell.node.infectivePop;
                cell.node.susceptiblePop-=pop*cell.node.susceptiblePop;
                
                if (cell.node.removedPop >= 1) {
                    cell.node.removedPop = 1;
                    
                    cell.node.susceptiblePop = 0;
                    cell.node.infectivePop = 0;
                }
            }
        }
        else {
          /*  if (cell.node.susceptiblePop <1) {
                cell.node.susceptiblePop += pop;
                if (cell.node.susceptiblePop > 1) {
                    cell.node.susceptiblePop = 1;
                }
            */
        }
        
    }
    
    public void handleMouseControlPanelNode(MouseEvent e) {
        for (int j=0; j<grid.numY; j++) {
                    for (int i=0; i<grid.numX; i++) {
                        
                        if (e.getX()+paintBrushWidth > grid.grid[i][j].x && e.getX()-paintBrushWidth < grid.grid[i][j].x + grid.grid[i][j].width &&
                                e.getY()+paintBrushHeight > grid.grid[i][j].y && e.getY()-paintBrushHeight < grid.grid[i][j].y+grid.grid[i][j].height) {
                            
                            if ( controlPanel.radioButtonMouseCreate.isSelected() ) {
                                handleNodeCreation(grid.grid[i][j]);   
                                
                               // if (!timer.isRunning()) {
                                    repaint();
                                //}
                                
                            }
                            controlPanel.setSelectedCell(grid.grid[i][j]);
                           
                            selectedCell = grid.grid[i][j];
                            
                            controlPanel.jLabel8.setText(""+selectedCell.node.susceptiblePop);
                            controlPanel.jLabel9.setText(""+selectedCell.node.infectivePop);
                            controlPanel.jLabel10.setText(""+selectedCell.node.removedPop);
                            
                            
                            controlPanel.jTextField3.setText(""+selectedCell.node.contactRate);
                            controlPanel.jTextField4.setText(""+selectedCell.node.removalRate);
                            controlPanel.jTextField5.setText(""+selectedCell.node.remToSucRate);
                     
                       //     return;
                        }
                        
                    }
                }
    }
    
    public DrawingSurface() {
        
          paintBrushWidth=10;
       paintBrushHeight=10;
       
       
        setBorder(BorderFactory.createLineBorder(Color.black));
       /* timer = new Timer(delay,this);
        
        ruleList = new ArrayList<Rule>();
        grid = new Grid(50,50,10);
        SIRModel sirModel = new SIRModel(grid);
        SIRSModel sirsModel = new SIRSModel(grid);
        SIRModelSwap sirModelSwap = new SIRModelSwap(grid);
        
        //ruleList.add(sirModel);
        ruleList.add(sirsModel);
        ruleList.add(sirModelSwap);*/
        
        this.init();
        
        /**
         * 
         *  It might be better to create a hash whose key is the coordinate of the 
         *  cell and whose value is the actual cell. That way, instead of iterating 
         *  across the grid every time a mouse is clicked, it would just have to iterate 
         *  across a 1 dimensional object. 
         * 
         * 
         */
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                handleMouseControlPanelNode(e);
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent e) {  
                handleMouseControlPanelNode(e);
                mouseX = e.getX();
                mouseY = e.getY();
            }
        });
        
         addMouseMotionListener(new MouseAdapter() {
            public void mouseMoved(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
            }
        });
    }
    
    public void init() {
        timer = new Timer(delay,this);
        
        ruleList = new ArrayList<Rule>();
        grid = new Grid(50,50,10);
        SIRModel sirModel = new SIRModel(grid);
        SIRSModel sirsModel = new SIRSModel(grid);
        SIRModelSwap sirModelSwap = new SIRModelSwap(grid);
        DynamicCoefficients dc = new DynamicCoefficients(grid);
        
      // ruleList.add(sirModel);
       ruleList.add(sirsModel);
        ruleList.add(dc);
       // ruleList.add(sirModelSwap);
    }

    @Override
    public Dimension getPreferredSize() {
        int x=this.grid.numX*this.grid.size+this.grid.size;
        int y=this.grid.numY*this.grid.size+this.grid.size;
        return new Dimension(x,y);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);       
        grid.render(g);
        
          //g.setColor(Color.black);
         // g.fillOval(mouseX,mouseY,paintBrushWidth,paintBrushHeight);
    }  
    
    @Override
    public void actionPerformed(ActionEvent e) {
      //  System.out.println("Drawing Surface");
       // System.out.println("YES");
        for (Rule rule : ruleList) {
            rule.performRule();
        }
        
    //    System.out.println("Performed Rules");
        repaint();
      //  System.out.println("Repainted");
        
        Graphics g = this.controlPanel.selectedCellPanel.getGraphics();
        
      //  if (g == null) {
        //    System.out.println("Graphics Object is null");
       // }
        
        //if (this.selectedCell == null) {
          //  System.out.println("Selected Cell is null");
        //}
        
      //  System.out.println("Retreived graphics object from cell panel");
        
        this.selectedCell.render(g);
        
    
        
     //   System.out.println("Rendered Selected Cell");
        this.controlPanel.selectedCellPanel.repaint();
        
        controlPanel.jLabel8.setText(""+selectedCell.node.susceptiblePop);
        controlPanel.jLabel9.setText(""+selectedCell.node.infectivePop);
        controlPanel.jLabel10.setText(""+selectedCell.node.removedPop);
        
      //   if (controlPanel.radioButtonMouseCreate.isSelected()) {

            
       //     System.out.println("Drawing oval at " + mouseX + "," + mouseY);
      //  }
        
        
     //   System.out.println(""+selectedCell.node.susceptiblePop);
       // System.out.println(""+selectedCell.node.infectivePop);
      //  System.out.println(""+selectedCell.node.removedPop);
       // controlPanel.nodeControlPane.repaint();
    }
}

