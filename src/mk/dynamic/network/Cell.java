package mk.dynamic.network;

import java.awt.*;

/**
 *
 * @author mkirschner
 */
public class Cell {
    public int x,y;
    public int xidx, yidx;
    public int width, height;
    public Color color;
    public Node node;
    
    public Cell(int x, int y) {
        node = new Node(x,y);
        this.x = x;
        this.y = y;
        color = new Color(255,0,0);
        
    }
    
    public void render(Graphics g) {
      //  int red = 255-(int)node.infectivePop*255;
       // int green = 255-(int)node.susceptiblePop*255;
      //  int blue = 255-(int)node.removedPop*255;
        float red = Math.abs( (1-(float)node.infectivePop) % 256);
        float green = Math.abs( ((1-(float)node.susceptiblePop)) % 256);
        float blue = Math.abs( ((1-(float)node.removedPop)) % 256);
        
       
        
     //   System.out.println(red + "," + green + "," + blue);
       // if (red < 0) red=0;
       // if (green < 0) green=0;
       // if (blue < 0) blue =0;
        color = new Color(red,green,blue);
        
        
        g.setColor(color);
        g.fillRect(x, y, width, height);
        
        g.setColor(Color.BLACK);
        g.drawRect(x, y, width, height);
    }
        public void render(Graphics g,int x,int y,int width,int height) {
      //  int red = 255-(int)node.infectivePop*255;
      //  int green = 255-(int)node.susceptiblePop*255;
      //  int blue = 255-(int)node.removedPop*255;
        float red = 1-(float)node.infectivePop;
        float green = 1-(float)node.susceptiblePop;
        float blue = 1-(float)node.removedPop;
        
        color = new Color(red,green,blue);
        
        g.setColor(color);
        g.fillRect(x, y, width, height);
        
        g.setColor(Color.BLACK);
        g.drawRect(x, y, width, height);
    }
    
}
