/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package shark;

import java.io.*;
import java.util.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import javax.swing.text.*;

/**
 *
 * @author Paul Rubie
 */
public class ListenDialog extends JDialog{

    JDialog thisd = this;
//    String phrase;
    String ph[];
    String title;
    String mainText;
    int position;
    public int returnValue = -1;
    public static int YES = 0;
    public static int CANCEL = 1;
    public static int RESTART = 2;

    Dimension dialogDim;
    listenButton btListen;
    EmptyBorder empt = new EmptyBorder(20,20,20,20);
  //  String fontname = UIManager.getFont("Tree.font").getName();
  //  String fontname;
    String db;
    JButton[] buttons;
    Font normalfont;
   int sayGap;

    Toolkit t = Toolkit.getDefaultToolkit();

    public ListenDialog(String t, String mt, String saydb, String str[], int timeGap, JDialog dg, int pos, JButton[] bts){
      super(dg);
      ph = str;
      sayGap = timeGap;
      init(t, mt, saydb, pos, bts);
      this.setSize(dialogDim);
      addConstructor();
    }

    public ListenDialog(String t, String mt, String saydb, String str, JDialog dg, int pos, JButton[] bts){
      super(dg);
      ph = new String[]{str};
      init(t, mt, saydb, pos, bts);
      this.setSize(dialogDim);
      addConstructor();
    }
    public ListenDialog(String t, String mt, String saydb, String str, JFrame fr, int pos, JButton[] bts){
      super(fr);
      ph = new String[]{str};
      init(t, mt, saydb, pos, bts);
      this.setSize(dialogDim.width,(int)(dialogDim.height*1.2));
      addConstructor();
    }


    private void init(String t, String mt, String saydb, int pos, JButton[] bts){
        db = saydb;
          title = t;
          mainText = mt;
          position = pos;
          buttons = bts;
          dialogDim = new Dimension((int)(sharkStartFrame.mainFrame.getWidth()/2), (int)(sharkStartFrame.mainFrame.getHeight()/2.1));
    }

    private void addConstructor(){
      Demo_base.isDialogOpen = true;
      this.setModal(true);
      this.setResizable(false);
      this.setTitle(title);

      btListen = new listenButton(sharkStartFrame.defaultbg, ph, sayGap);

      btListen.setFocusable(false);
      btListen.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
      Dimension dim = new Dimension(btListen.speak.getIconWidth() + 10, btListen.speak.getIconHeight() + 10);
      btListen.setPreferredSize(dim);
      display text = new display();
      text.setBorder(empt);

      setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
      this.addWindowListener(new java.awt.event.WindowAdapter() {
          public void windowClosing(WindowEvent e) {
            Demo_base.isDialogOpen = false;
            returnValue = CANCEL;
            spokenWord.flushspeaker(true);
             dispose();
          }
      });

      JButton btfirst = buttons[0];
  //    fontname = btfirst.getName();
      this.getRootPane().setDefaultButton(btfirst);
      normalfont = btfirst.getFont();


      StyledDocument doc = text.getStyledDocument();
      SimpleAttributeSet standard = new SimpleAttributeSet();
      StyleConstants.setAlignment(standard, StyleConstants.ALIGN_CENTER);
      StyleConstants.setFontFamily(standard, normalfont.getFamily());
      StyleConstants.setFontSize(standard, normalfont.getSize());
      StyleConstants.setBold(standard, true);
      doc.setParagraphAttributes(0, 0, standard, true);

      text.setContentType("text/html");
   //   text.setFont(new Font(normalfont.getName(), Font.PLAIN, normalfont.getSize()));
      text.setText(setDisplay(mainText, true, 14));
      text.setEditable(false);
      text.addHyperlinkListener(new HyperlinkListener() {
         public void hyperlinkUpdate(HyperlinkEvent e) {
           if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
             int k;
             String path = e.getDescription();
             String WEB = "web:";
             String MAILTO = "mailto:";
             if((k = path.indexOf(":"))>=0){
               if((path.indexOf(MAILTO))>=0){
                 u.launchMailto(e.getURL().toString());
               }
               else if((k=path.indexOf(WEB))>=0){
                 u.launchWebSite(path.substring(k+WEB.length()));
               }
             }
           }
         }
      });

      JPanel pp = new JPanel(new GridBagLayout());
      JPanel p1 = new JPanel(new GridBagLayout());
      JPanel p2 = new JPanel(new GridBagLayout());
      GridBagConstraints grid = new GridBagConstraints();

      grid.weightx = grid.weighty = 1;
      grid.gridx = 0;
      grid.gridy = 0;
      grid.fill = GridBagConstraints.BOTH;
      p1.add(text, grid);
      grid.fill = GridBagConstraints.CENTER;
      grid.insets = new Insets(10,0,10,0);

      grid.gridx = -1;
      grid.gridy = 0;
      grid.fill = GridBagConstraints.SOUTH;
      grid.insets = new Insets(10,0,10,0);

      if(shark.macOS){
          for(int i = buttons.length-1; i >= 0; i--){
    //          buttons[i].setFont(new Font(normalfont., Font.BOLD, 13));
              grid.insets = new Insets(10,i==buttons.length-1?10:0,10,10);
              p2.add(buttons[i], grid);
          }
      }
      else{
          for(int i = 0; i < buttons.length; i++){
      //        buttons[i].setFont(new Font(fontname, Font.BOLD, 13));
              grid.insets = new Insets(10,i==0?10:0,10,10);
              p2.add(buttons[i], grid);
          }
      }

      
      grid.gridx = 1;
      grid.insets = new Insets(10,0,10,0);
      grid.gridx = 0;
      grid.gridy = 0;
      pp.add(btListen, grid);



      text.setBackground(sharkStartFrame.defaultbg);

      this.getContentPane().add(p2, BorderLayout.SOUTH);
      this.getContentPane().add(pp, BorderLayout.NORTH);
      this.getContentPane().add(p1, BorderLayout.CENTER);

      this.pack();

      thisd.setSize(text.getWidth()+10, text.getHeight()+90+btfirst.getHeight()+btListen.getHeight());
      if(position == 0)
        this.setLocation((sharkStartFrame.mainFrame.getWidth() - this.getWidth())/2, (sharkStartFrame.mainFrame.getHeight() - this.getHeight())/2);
      else if(position == 1){
        this.setLocation((sharkStartFrame.mainFrame.gamePanel.getWidth() - this.getWidth())-2,
                         (int)(sharkStartFrame.mainFrame.getLocation().getY()
                               + sharkStartFrame.mainFrame.getInsets().top
                                    + sharkStartFrame.mainFrame.getHeight()/2)
                                    -(this.getHeight()/2));
      }
      else{
        this.setLocation((position - this.getWidth())-5,
                         (int)(sharkStartFrame.mainFrame.getLocation().getY()
                               + sharkStartFrame.mainFrame.getInsets().top
                                    + sharkStartFrame.mainFrame.getHeight()/2)
                                    -(this.getHeight()/2));
      }

      
      btfirst.requestFocus();
    }
    
     private String setDisplay(String s, boolean centre, int fontSize){
       s = u.convertToHtml(s);
       String htmlBegin = "<html><b>";
       String htmlEnd = "";
       if(centre){
         htmlBegin+="<center>";
         htmlEnd+="</center>";
       }
       htmlBegin+="<font face='"+String.valueOf(normalfont.getName())+"' style='font-size:"+String.valueOf(fontSize)+"px'>";
       htmlEnd+="</font></b></html>";
       String text = htmlBegin+s+htmlEnd;
       return setfontsize(text, fontSize, -3, +5);
     }    
     
 String setfontsize(String s, int normalsize, int smallsize, int bigsize){
   String htmlBeginBig = "<font face='"+String.valueOf(normalfont.getName())+"' style='font-size:"+String.valueOf(normalsize+bigsize)+"px'>";
   String htmlBeginSmall = "<font face='"+String.valueOf(normalfont.getName())+"' style='font-size:"+String.valueOf(normalsize+smallsize)+"px'>";
   String htmlEnd = "</font>";
   while(s.indexOf(":s:")>=0){
     s = s.replaceFirst(":s:", htmlBeginSmall);
     s = s.replaceFirst(":s:", htmlEnd);
   }
   while(s.indexOf(":b:")>=0){
     s = s.replaceFirst(":b:", htmlBeginBig);
     s = s.replaceFirst(":b:", htmlEnd);
   }
   return s;
 }     



 class listenButton extends JButton{
   Toolkit t = Toolkit.getDefaultToolkit();
   String dir = sharkStartFrame.publicPathplus + "sprites" +
      sharkStartFrame.separator;
  Image list;
  Image liston;
public ImageIcon speakOn;
public ImageIcon speak;
  javax.swing.Timer listenColor;
   JButton jb = this;
   Color col;
   String sayStr[];
   int sayGap;
 //  String key;
   JButton thisbt = this;

   listenButton(Color c, String say[], int gap){
     super();
        list = sharkStartFrame.t.createImage(sharkStartFrame.publicPathplus + "sprites" +
                                File.separator + "Speak.jpg");

        liston = sharkStartFrame.t.createImage(sharkStartFrame.publicPathplus + "sprites" +
                                File.separator + "SpeakOn.jpg");
        MediaTracker tracker=new MediaTracker(this);
        tracker.addImage(list,1);
        tracker.addImage(liston,2);
        try{
            tracker.waitForAll();
        }
        catch (InterruptedException ie)
        {
        }
  speakOn = new ImageIcon(liston.getScaledInstance( -1,
      sharkStartFrame.screenSize.height / 20, Image.SCALE_SMOOTH));
  speak = new ImageIcon(list.getScaledInstance( -1,
      sharkStartFrame.screenSize.height / 20, Image.SCALE_SMOOTH));
     sayStr = say;
     sayGap = gap;
     jb.setIcon(speak);
     col = c;
     init();
   }

   void init(){
     Dimension dim = new Dimension(speak.getIconWidth() + 10, speak.getIconHeight() + 10);
     setPreferredSize(dim);
     setMaximumSize(dim);
     setFocusable(false);
     setBackground(col);
     setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));


   listenColor = (new javax.swing.Timer(500, new ActionListener() {
     public void actionPerformed(ActionEvent e) {
       if(spokenWord.isfree()){
         listenColor.stop();
         thisbt.setIcon(speak);
       }
     }
   }));
   listenColor.setRepeats(true);


   addActionListener(new java.awt.event.ActionListener() {
     public void actionPerformed(ActionEvent e) {
       if(spokenWord.isfree()){
         setIcon(speakOn);
         repaint();
         if(sayStr.length==0)
             spokenWord.findandsay(sayStr[0], sharkStartFrame.publicTestSayLib);
         else{
             spokenWord spw[] = new spokenWord[]{};
             for(int i = 0; i < sayStr.length; i++){
                spw = u.addSpokenWord(spw, spokenWord.findandreturn(sayStr[i].replaceAll(" ", "_"), sharkStartFrame.publicTestSayLib));
             }
            spokenWord sw = spokenWord.combine(spw, sayGap);
            if (sw != null) {
                sw.say();           
            }
         }
         listenColor.start();
//         activeListen = jb;
       }
       else{
         stopSpeak();
       }
     }
   });
   }

   public void stopSpeak(){
     setIcon(speak);
     setBackground(col);
     spokenWord.stopspeaker();
     spokenWord.endsay = spokenWord.endsay2 = System.currentTimeMillis();
     listenColor.stop();
   }
 }



  class display extends JTextPane {
   public void paint(Graphics g) {
     ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
     super.paint(g);
   }
  }

}
