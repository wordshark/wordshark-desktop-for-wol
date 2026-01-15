/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package shark;

/**
 *
 * @author Paul Rubie
 */
import java.awt.event.*;
import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

class XrButton_base extends JButton implements MouseListener
{
	String  text[];
	boolean mouseIn = false;
	boolean mouseDown = false;
        int arcsize = 20;
        Color bg;
        Color colbut;
        Color coloutlinebut;
        Color coltext;
        Font ff = sharkStartFrame.treefont.deriveFont((float)sharkStartFrame.treefont.getSize()+2);
        FontMetrics ffm = sharkStartFrame.mainFrame.getFontMetrics(ff);
        boolean resizetext = true;


public XrButton_base(Color backcolor)
{
	super();
        bg = backcolor;
        init();
}

public XrButton_base(String s, Color backcolor)
{
	super(s);
        bg = backcolor;
        text =  u.splitString(s);
        init();
}

public void init(){
	setBorderPainted(false);
	addMouseListener(this);
	setContentAreaFilled(false);
        setOpaque(true);
        UIDefaults defaults = UIManager.getDefaults();
        try{
            Object obj    = defaults.get( "Button.focus" );
            if(obj instanceof Color){
                coloutlinebut = (Color)obj;
                colbut = u.darker(coloutlinebut, 0.6f);
            }
            else colbut = Color.gray;
       }
        catch(Exception e){
            colbut = Color.gray;
        }

         try{
            Object obj = defaults.get("Button.foreground");
            if(obj instanceof Color)
                coltext = (Color)obj;
            else coltext = Color.black;
       }
        catch(Exception e){
            coltext = Color.black;
        }

}

public void changeText(String s){
    text = u.splitString(s);
    resizetext=  true;
    ff = sharkStartFrame.treefont.deriveFont((float)sharkStartFrame.treefont.getSize()+2);
    ffm = sharkStartFrame.mainFrame.getFontMetrics(ff);
}

public void paintComponent(Graphics g)
{
 	Graphics2D g2 = (Graphics2D)g;
//	if (getModel().isPressed())
//	{
	//	g.setColor(Color.pink);
	//	g2.fillRect(3,3,getWidth()-6,getHeight()-6);
	//}
	super.paintComponent(g);
        g2.setColor(bg);
        g2.fillRect(0, 0, getWidth(), getHeight());

        /*
	if (mouseIn) g2.setColor(Color.red);
		else     g2.setColor(new Color(128,0,128));
	g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	g2.setStroke(new BasicStroke(1.2f));
	g2.draw(new RoundRectangle2D.Double(1,1,(getWidth()-3),(getHeight()-3),12,8));
	g2.setStroke(new BasicStroke(1.5f));
	g2.drawLine(4,getHeight()-3,getWidth()-4,getHeight()-3);
         */
        if(mouseDown){
            g2.setColor(colbut);
        }
        else{
          GradientPaint gp = new GradientPaint(getWidth(), getHeight()/3,
                Color.white, getWidth(), getHeight(), colbut  , true);
           g2.setPaint(gp);
        }

        g2.fill(new RoundRectangle2D.Double(1,2,(getWidth()-4),(getHeight()-4),16,16));
	if (mouseIn)
            g2.setColor(Color.white);
        else
            g2.setColor(coloutlinebut);
	g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

	g2.setStroke(new BasicStroke(4.0f));
	g2.draw(new RoundRectangle2D.Double(1,2,(getWidth()-4),(getHeight()-4),16,16));


        g2.setColor(coltext);


        g2.setFont(ff);

        String longest = "";
        int longestii = -1;
        int kl = -1;
        if(text==null)return;
        for(int i = 0; i < text.length; i++){
            if((kl=ffm.stringWidth(text[i]))>longestii){
                longestii = kl;
                longest = text[i];
            }
        }
        int twid;
        for(int i = 0; i < text.length; i++){
            twid = ffm.stringWidth(longest);
            int maxw;
            if(text.length>1)maxw = getWidth()*7/12;
            else maxw = getWidth()*5/8;
            while(i<1 && (resizetext || twid > maxw)){
                ff = sharkStartFrame.treefont.deriveFont((float)ff.getSize()-1);
                ffm = sharkStartFrame.mainFrame.getFontMetrics(ff);
                twid = ffm.stringWidth(longest);
                g2.setFont(ff);
                resizetext = false;
            }
            int thei = (int)ffm.getHeight()/2;
            if(text.length>1){
                if(i==0)thei -= (int)ffm.getHeight()*5/6;
                else thei += (int) ffm.getHeight() *5/6;
            }
            g2.drawString(text[i], (getWidth()-ffm.stringWidth(text[i]))/2, (getHeight()/2)+(thei/2));
        }




	g2.dispose();
}
public void mouseClicked(MouseEvent e){}
public void mouseEntered(MouseEvent e)
{
	mouseIn = true;
}
public void mouseExited(MouseEvent e)
{
	mouseIn = false;
}
public void mousePressed(MouseEvent e)
{
    mouseDown = true;
}
public void mouseReleased(MouseEvent e)
{
    mouseDown = false;
    mouseIn = false;
}
}