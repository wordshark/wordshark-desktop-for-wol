/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package shark;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
/**
 *
 * @author MacBook Air
 */
public class DragPanel extends JComponent{
    private volatile int draggedAtX;
    private volatile int draggedAtY = 0;
    JComponent thispanel = this;
    JComponent ch1;
    
    public DragPanel(JFrame owner, JComponent child1){
        super();
        /*
        ch1= child1;
        owner.setGlassPane(this);
    //    setDoubleBuffered(false);
     //   setBackground(Color.blue);
        this.setLayout(null);
 //       setSize(25, 25);
 //       setPreferredSize(new Dimension(25, 25));
        addMouseListener(new MouseAdapter(){
            public void mousePressed(MouseEvent e){
                draggedAtX = MouseInfo.getPointerInfo().getLocation().x;
                draggedAtY = 0;
            }
        });

        addMouseMotionListener(new MouseMotionAdapter(){
            public void mouseDragged(MouseEvent e){
                int g = MouseInfo.getPointerInfo().getLocation().x-draggedAtX;
                if(g<0)g = g*-1;
       //         if(g>40){
                    ch1.setLocation(MouseInfo.getPointerInfo().getLocation().x - draggedAtX,
                        0);
                    
      //          }
            }
        });
        setVisible(true);
        
         */
    }
    
}
