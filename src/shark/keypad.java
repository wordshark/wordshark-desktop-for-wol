package shark;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

public class keypad extends JTable {
  static boolean updating;
  plab lab = new plab();
  static final int borderw = 6;
  public static String keypadname;
  public static class savekeypad implements Serializable  {
     static final long serialVersionUID = 6071256065748532876L;
      char  keys[][];
      String fontname;
      int fontsize;
      int fonttype;
   }
   boolean used[][];
   public savekeypad kp;
   public static int lastcode = -1;
   public static String keypad_db = sharkStartFrame.sharedPathplus+"keypad";
   public static String publickeypad_db = sharkStartFrame.publicKeypadLib;
   boolean empty;

   // accents recognised (with CTL)
   public static int GRAVE = KeyStroke.getKeyStroke('`').getKeyCode();
   public static int ACUTE =  KeyStroke.getKeyStroke('\'').getKeyCode();
   public static int CIRCUMFLEX =  KeyStroke.getKeyStroke('^').getKeyCode();
   public static int CEDILLA =  KeyStroke.getKeyStroke(',').getKeyCode();
   public static int TILDE =  KeyStroke.getKeyStroke('~').getKeyCode();
   public static int UMLAUT   =  KeyStroke.getKeyStroke(':').getKeyCode();

   public static final byte ENTER = 9;
   public static final byte BACKSPACE = 8;
   public static final byte DELETE = 2;
   public static final byte INSERT = 3;
   public static final byte LEFT = 4;
   public static final byte RIGHT = 7;
   public static final byte SHIFT = 1;
   public static final byte UP = 5;
   public static final byte DOWN = 6;
//startPR2010-05-04^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   public static final int DEFAULT = 0;
   public static final int BOTTOMLEFT = 1;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   public static final int BOTTOMRIGHTABSOLUTE = 2;


   static String codes[] = new String[] {"aeiouAEIOU","aeiouAEIOU","aeiouAEIOU","cC","aonAON","aeiouAEIOU"};
   static char replace[][] = new char[][] {{224,232,236,242,249, 192,200,204,210,217}, //grave
                                    {225,233,237,243,250, 193,201,205,211,218}, //acute
                                    {226,234,238,244,251, 194,202,206,212,219}, //circumflex
                                    {231,199},  //cedilla
                                    {227,245, 241,195,213,209}, //tilde
                                    {228,235,239,246,252, 196,203,207,214,220}}; //umlaut
   int kw,kh;
   Window sendto;
   keypad currkeypad,thiskp;
   keypad source,destination;
   Font font, font2;
   FontMetrics m;
   boolean changed,shift,extras;
   int maxlen;
   int selrow=-1, selcol=-1;
   int clickedrow=-1, clickedcol=-1;
   long clickedTime = -1;
   static ImageIcon entericon = new ImageIcon(sharkStartFrame.publicPathplus+"sprites" +sharkStartFrame.separator+"enter.gif");
   static ImageIcon backspaceicon = new ImageIcon(sharkStartFrame.publicPathplus+"sprites" +sharkStartFrame.separator+"backspace.gif");
   static ImageIcon lefticon = new ImageIcon(sharkStartFrame.publicPathplus+"sprites" +sharkStartFrame.separator+"left.gif");
   static ImageIcon righticon = new ImageIcon(sharkStartFrame.publicPathplus+"sprites" +sharkStartFrame.separator+"right.gif");
   static ImageIcon shifticon = new ImageIcon(sharkStartFrame.publicPathplus+"sprites" +sharkStartFrame.separator+"shift.gif");
   static ImageIcon shiftdownicon = new ImageIcon(sharkStartFrame.publicPathplus+"sprites" +sharkStartFrame.separator+"shiftdown.gif");
   static ImageIcon inserticon = new ImageIcon(sharkStartFrame.publicPathplus+"sprites" +sharkStartFrame.separator+"insert.gif");
   static ImageIcon deleteicon = new ImageIcon(sharkStartFrame.publicPathplus+"sprites" +sharkStartFrame.separator+"delete.gif");
   static ImageIcon spaceicon = new ImageIcon(sharkStartFrame.publicPathplus+"sprites" +sharkStartFrame.separator+"space.gif");
   static ImageIcon upicon = new ImageIcon(sharkStartFrame.publicPathplus+"sprites" +sharkStartFrame.separator+"up.gif");
   static ImageIcon downicon = new ImageIcon(sharkStartFrame.publicPathplus+"sprites" +sharkStartFrame.separator+"down.gif");
   boolean gotalpha;
   boolean gotnumbers;
   public int keyspacing = 2;
   int keyborder = 0;
   Color keypadbackgroundcol = new Color(210,210,210);
   Color keypadclickedcol = new Color(110,110,110);
   Color keypadcol = new Color(250,250,250);
   Color mouseovercol = new Color(170,170,170);
   int clickShowTime = 300;
   boolean fractionkeypad = false;
   float clickalpha = 1.0f;
   int whitedelay = 100;
   
   final int BASEFONTSIZE = 35;
   
   TableModel dataModel = new AbstractTableModel() {
          public int getRowCount() {
             if(empty) return 0;
             if(kp.keys.length==0) return 8;
             return (source==null)?kp.keys.length:Math.max(8,kp.keys.length+1);
          }
          public int getColumnCount() {
            if(empty) return 0;
             if(kp.keys.length==0) return 8;
             return (source==null)?maxlen:Math.max(8,maxlen+1);
          }
          public Class getColumnClass(int col) {return String.class;}
          public Object getValueAt(int row, int col) {
            char ch;
            if(row<kp.keys.length && col <kp.keys[row].length) {
                ch = kp.keys[row][col];
                return new String(new char[]{ch});
            }
            return " ";
          }
   };
   TableCellRenderer rr = new TableCellRenderer() {
     public Component getTableCellRendererComponent(JTable table,
                                               Object value,
                                               boolean isSelected,
                                               boolean hasFocus,
                                               int row,
                                               int column) {
        lab.invisible = false;
        if(row >= kp.keys.length || column >= kp.keys[row].length) {
            lab.setText(" ");
            lab.setIcon(null);
            lab.invisible = true;
        }
        else switch(((String)value).charAt(0)) {
           case 0:
            lab.setText(" ");
            lab.setIcon(null);
            if(source == null) {
               lab.setBorder(null);
               return lab;
            }
            break;
           case ENTER:
              lab.setIcon(entericon);
              lab.setText(null);
              break;
           case BACKSPACE:
              lab.setIcon(backspaceicon);
              lab.setText(null);
              break;
          case SHIFT:
              lab.setIcon(shift?shiftdownicon:shifticon);
              lab.setText(null);
              break;
          case INSERT:
              lab.setIcon(inserticon);
              lab.setText(null);
              break;
          case DELETE:
              lab.setIcon(deleteicon);
               lab.setText(null);
             break;
          case LEFT:
              lab.setIcon(lefticon);
              lab.setText(null);
              break;
          case RIGHT:
              lab.setIcon(righticon);
              lab.setText(null);
              break;
          case UP:
                lab.setIcon(upicon);
                lab.setText(null);
                break;
          case DOWN:
                  lab.setIcon(downicon);
                  lab.setText(null);
                  break;
          case ' ':
             if(row<kp.keys.length && column < kp.keys[row].length
                      && kp.keys[row][column] != 0) {
                lab.setIcon(spaceicon);
                lab.setText(null);
                break;
             }
             // else drop thru
          default:
             lab.setIcon(null);
             lab.setText(shift?((String)value).toUpperCase():(String)value);
             lab.setForeground((used != null && used[row][column])?Color.lightGray:Color.black);
             break;
        }
        if(font2==null) {
          font2 = u.fontFromString(font.getName(), font.getStyle(),
                           (float)font.getSize() * 4 / 6);
        }

        String labtext = lab.getText();
        boolean smallerNumber = gotalpha && gotnumbers && labtext!=null && !labtext.trim().trim().equals("")
                && u.numbers.indexOf(labtext) >= 0;
        lab.setFont(smallerNumber?font2:font);
        lab.setHorizontalAlignment(lab.CENTER);
        lab.setVerticalAlignment(lab.CENTER);
        lab.selected = (!updating && row == selrow && column == selcol || updating && isSelected);
        lab.clicked = row == clickedrow && column == clickedcol &&
                clickedTime>0 && System.currentTimeMillis() < clickedTime+clickShowTime;
        return lab;
     }
   };
   class plab extends JLabel {
     boolean selected;
     boolean clicked;
     int row, col;
     boolean invisible = false;

     public void paint(Graphics g) {
       ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
       ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
       g.setColor(keypadbackgroundcol);
       if(invisible)
           g.fillRect(0, 0, this.getWidth(), getHeight());
       boolean dorect = false;
       if(selected) {
         dorect = true;
         g.setColor(mouseovercol);
       }
       if(clicked){
          dorect = true;
       }
       else if(!invisible && !selected){
          dorect = true;
          g.setColor(keypadcol);

       }
       if(dorect){
          float savealpha = clickalpha;
          if(clicked && savealpha!=0) ((Graphics2D)g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, clickalpha));
          if(clicked && System.currentTimeMillis()-(clickedTime+whitedelay)<0)
              g.setColor(Color.white);
          g.fillRect(keyborder, keyborder, this.getWidth()-(keyborder*2), getHeight()-(keyborder*2));
          if(clicked) ((Graphics2D)g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
       }
       super.paint(g);
       ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);
       ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
     } 
   }

   
   public keypad(String name) {
      super();
      int i,j;
      thiskp = this;
      if(name != null) {
        if((kp = (savekeypad)db.find(keypad_db,name,db.SAVEKEYPAD))==null)
            if((kp = (savekeypad)db.find(publickeypad_db,name,db.SAVEKEYPAD))==null)
               if((kp = (savekeypad)db.find(sharkStartFrame.publicPathplus+"publiclangkeypad",name,db.SAVEKEYPAD))==null){
                   kp = (savekeypad)db.find(sharkStartFrame.sharedPathplus+"Keypads"+shark.sep+"Keypad"+name,name,db.SAVEKEYPAD);
               }
      }

      else empty = true;
      this.setCursor(Cursor.getDefaultCursor());
      setBackground(keypadbackgroundcol);
      if(kp != null) {
          maxlen = 0;
          for(i=0;i<kp.keys.length;++i) maxlen = Math.max(maxlen,kp.keys[i].length);
       }
       else {
            kp = new savekeypad();
            kp.keys = new char[0][];
            font = sharkStartFrame.wordfont;
            kp.fontname = font.getName();
            kp.fontsize = font.getSize();
            kp.fonttype = font.getStyle();
       }
       if(u.isdefaultfont(kp.fontname))
         kp.fontname = sharkStartFrame.wordfont.getName();
//       font = u.fontFromString(kp.fontname,kp.fonttype, (float)kp.fontsize);
       font = u.fontFromString(kp.fontname,kp.fonttype, u2_base.adjustMaxFontSizeToResolution(BASEFONTSIZE));
       m = getFontMetrics(font);
       setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
       setRowHeight(kh = m.getHeight());
       setIntercellSpacing(new Dimension(keyspacing,keyspacing));
       setColumnSelectionAllowed(true);
       this.setAutoResizeMode( JTable.AUTO_RESIZE_ALL_COLUMNS);
       this.setDefaultRenderer(String.class,rr);
       this.setTableHeader(null);
       kw = Math.max(26,m.charWidth('W'));
       for(i=0;i<kp.keys.length;++i) {
         for(j=0;j<kp.keys[i].length;++j) {
           kw = Math.max(kw,m.charWidth(kp.keys[i][j])+4);
           kw = Math.max(kw,m.charWidth(Character.toUpperCase(kp.keys[i][j]))+4);
           if(u.numbers.indexOf(kp.keys[i][j]) < 0) gotalpha = true;
           else gotnumbers = true;
         }
       }
       if(!updating) addMouseMotionListener(new MouseMotionAdapter()   {
         public void mouseMoved(MouseEvent e) {
            int row,col;
            int yy = e.getY();
            int xx = e.getX();
//           repaint();
            for(row=0;row<kp.keys.length;++row) {
              for (col = 0; col < kp.keys[row].length; ++col) {
                 if (getCellRect(row,col,true).contains(xx,yy)) {
                   if(row != selrow || col != selcol) {
                      selrow = row;
                      selcol = col;
                      repaint();
                   }
                   break;
                 }
              }
           }
         }
       });

       final Timer testTimer;
       testTimer = (new Timer(1, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                repaint();
                if(System.currentTimeMillis() > (clickedTime+whitedelay)+clickShowTime){
                    ((Timer)e.getSource()).stop();
                    clickedTime = -1;
                    clickalpha = 1.0f;
                }
                long d = Math.max(((clickedTime+whitedelay)+clickShowTime)-System.currentTimeMillis(),0);
                long d2 =clickShowTime- (d/clickShowTime);
                clickalpha =Math.min(Math.max(1-((float)1*(float)d/(float)d2),0),1);
            }
       }));
       testTimer.setRepeats(true);


       addMouseListener(new MouseAdapter() {
            public void mouseExited(MouseEvent e) {
              selrow = selcol = -1;
              repaint();
            }
            public void mousePressed(MouseEvent e) {
              clickedTime = System.currentTimeMillis();
              clickedrow = selrow = getSelectedRow();
              clickedcol = selcol = getSelectedColumn();
              if(student.fishpanel!=null && !sharkStartFrame.mainFrame.hasFocus()){
                  sharkStartFrame.mainFrame.requestFocusInWindow();
                }
              repaint();
              testTimer.start();
            }
            public void mouseReleased(MouseEvent e) {
              int row = selrow;
              int col = selcol;
              if(updating) {
                row = getSelectedRow();
                col = getSelectedColumn();
              }
              if(source == null) {
                  if(destination == null) {
                     if(row>=0 && row < kp.keys.length && col >= 0 && col < kp.keys[row].length) {
                        char ch = kp.keys[row][col];
                        int code;
                        switch(ch) {
                          case 0: {repaint();return;}
                          case ENTER:
                             code = KeyEvent.VK_ENTER;
                             ch = 0;
                             break;
                          case SHIFT:
                             shift ^= true;
                             repaint();
                             return;
                          case BACKSPACE:
                             code = KeyEvent.VK_BACK_SPACE;
                             ch = KeyEvent.VK_BACK_SPACE;
                             break;
                          case DELETE:
                             code = KeyEvent.VK_DELETE;
                             ch = 0;
                             break;
                           case INSERT:
                             code = KeyEvent.VK_INSERT;
                             ch = 0;
                             break;
                           case LEFT:
                             code = KeyEvent.VK_LEFT;
                             ch = 0;
                             break;
                           case RIGHT:
                             code = KeyEvent.VK_RIGHT;
                             ch = 0;
                             break;
                           case UP:
                             code = KeyEvent.VK_UP;
                             ch = 0;
                             break;
                           case DOWN:
                             code = KeyEvent.VK_DOWN;
                             ch = 0;
                             break;
                           default:
                             if(shift) {
                                ch = Character.toUpperCase(ch);
                                shift = false;
                                repaint();
                             }
                             code = KeyStroke.getKeyStroke(ch).getKeyCode();
                             break;
                        }
                        send(new KeyEvent(thiskp,KeyEvent.KEY_PRESSED,System.currentTimeMillis(),0,code,ch));
                        if(ch != 0) send(new KeyEvent(thiskp,KeyEvent.KEY_TYPED,System.currentTimeMillis(),0,KeyEvent.VK_UNDEFINED,ch));
                        send(new KeyEvent(thiskp,KeyEvent.KEY_RELEASED,System.currentTimeMillis(),0,code,ch));
                     }
                  }
                  else {  // destination supplied
                    if(row < kp.keys.length && col < kp.keys[row].length
                                                           && used != null) {
                        if(!used[row][col]) {
                           destination.addkey(kp.keys[row][col]);
                           used[row][col] = true;
                        }
                        else {
                           destination.removekey(kp.keys[row][col]);
                           used[row][col] = false;
                        }
                    }
                  }
               }
               repaint();
            }
      });
      addKeyListener(new KeyListener() {
         public void keyPressed(KeyEvent e) {
           if(source != null) {
              if(e.getKeyChar() == e.VK_DELETE) {
                int row=getSelectedRow(),col=getSelectedColumn();
                if(row<0 || col < 0 || row >= kp.keys.length || col >= kp.keys[row].length) return;
                removekey(kp.keys[row][col]);
                source.fix();
                source.createDefaultColumnsFromModel();
                changed = true;
              }
              else if(e.getKeyCode() == e.VK_INSERT || (!shark.production  &&  e.isControlDown() && e.getKeyCode() == KeyEvent.VK_I)) {
                int row=getSelectedRow(),col=getSelectedColumn();
                if(row <kp.keys.length && col < kp.keys[row].length) {
                   char newk[] = new char[kp.keys[row].length+1];
                   System.arraycopy(kp.keys[row],0,newk,0,col);
                   System.arraycopy(kp.keys[row],col,newk,col+1,
                                            kp.keys[row].length-col);
                   kp.keys[row] = newk;
                   fixup();
                   changed=true;
                   createDefaultColumnsFromModel();
                   changeSelection(row,col,false,false);
                }
                changed=true;
              }
           }
//           else if(sendto == null) return;
//            KeyEvent e2;
//            if((e2 = keycheck(e)) != null) send(e2);
           else if(destination == null) send(e);
         }
         public void keyReleased(KeyEvent e) {
            if(source==null && destination == null) send(e);
         }
         public void keyTyped(KeyEvent e) {
            if(source==null && destination == null) send(e);
         }
      });
      setModel(dataModel);
      validate();
   }

  void extrakeys(char addk[]) {  // CRET, BACKSPACE, DELETE, INSERT
     int len = kp.keys.length,i;
     if(addk == null) {
       if(extras) {
         char newk[][] = new char[--len][];
         System.arraycopy(kp.keys,0,newk,0,len);
         kp.keys = newk;
         extras = false;
         maxlen=0;
         for(i=0;i<=len;++i) maxlen = Math.max(maxlen,kp.keys[i].length);
      }
      return;
     }
     if(!extras) {
        char newk[][] = new char[len+1][];
        System.arraycopy(kp.keys,0,newk,0,len);
        kp.keys = newk;
     }
     else --len;
     kp.keys[len] = new char[0];
     for(i=0;i<addk.length;++i) {
        if(addk[i] == 0 || !find(addk[i])) {
          kp.keys[len] = u.addchar(kp.keys[len],addk[i]);
           if(addk[i] > 16) {
             kw = Math.max(kw, m.charWidth(addk[i]));
             kw = Math.max(kw, m.charWidth(Character.toUpperCase(addk[i])));
           }
        }
     }
     int o[] = new int[kp.keys[len].length];
     for(i=0;i<o.length;++i) o[i] = convert(kp.keys[len][i]);
     short o2[] = u.getorder(o);
     char newo[] = new char[o.length];  // to hold sorted list
     for(i=0;i<o.length;++i) newo[i] = kp.keys[len][o2[i]];
     maxlen=0;
     for(i=0;i<=len;++i) maxlen = Math.max(maxlen,kp.keys[i].length);
     kp.keys[len] = newo;
     extras = true;
   }

   public void setsource(keypad source1) {
      source = source1;
   }
   public int columnAtPoint(Point point) {
     if(source != null || destination != null) return super.columnAtPoint(point);
     int row = rowAtPoint(point),col;
     if (row < 0 || row >= kp.keys.length) return -1;
     for(col=0;col<kp.keys[row].length;++col) {
           if(getCellRect(row,col,false).contains(point)) {
                     return col;
           }
     }
     return -1;
   }
   //------------------------------------------------------------
   public Rectangle getCellRect(int row,int column,boolean includeSpacing) {
      Rectangle r = super.getCellRect(row,column,includeSpacing);
      if(source==null && destination==null) {
         r.x += (maxlen - kp.keys[row].length)*kw/2;
      }
      return r;
   }
  //--------------------------------------------------------------------
   void getfromfont() {
      char i;
      int col=-1,row=0;
      Font f = font;
      int tot = f.getNumGlyphs();
      kp.keys = new char[(tot+15)/16][];
      for(i=0;i<kp.keys.length-1;++i) {
        kp.keys[i] = new char[16];
      }
      kp.keys[i] = new char[(tot%16==0)?16:(tot%16)];

      for(i=0; tot > 0 ; ++i) {
        if(f.canDisplay(i)) {
          --tot;
          if(++col>=16) {
             col=0;
             ++row;
          }
          kp.keys[row][col] = i;
        }
      }

   }
        //-------------------------------------------------------------------
   void fixup() { // allow for extra slots if building
      int i,len2 = kp.keys.length;
      maxlen=0;
      for(i=0;i<len2;++i) {
        maxlen = Math.max(maxlen,kp.keys[i].length);
      }
      revalidate();
   }
        //-------------------------------------------------------------------
   void fix() {    // set used elements if a source
      int i,j;
      used = new boolean[kp.keys.length][];
      for(i=0;i<kp.keys.length;++i) {
        used[i] = new boolean[kp.keys[i].length];
        for(j=0;j<kp.keys[i].length;++j) {
           used[i][j]  = destination.find(kp.keys[i][j]);
        }
      }
   }
       //-------------------------------------------------------------------
   boolean find(char k) {    // test if char is in keypad
      int i,j;
      for(i=0;i<kp.keys.length;++i) {
        for(j=0;j<kp.keys[i].length;++j) {
           if(kp.keys[i][j]  == k) return true;
        }
      }
      return false;
   }
        //-------------------------------------------------------------------
   void removeempty() { // remove empty slots
      int i,j,len2;
      for(i=0;i<kp.keys.length;++i) {
        for(j=0;j<kp.keys[i].length;++j) {
          if(kp.keys[i][j] < 0) kp.keys[i] = u.removeChar(kp.keys[i],j--);
        }
        if(kp.keys[i].length == 0) {
          len2 = kp.keys.length;
          char newc[][] = new char[len2-1][];
          System.arraycopy(kp.keys,0,newc,0,i);
          if(i<len2-1) System.arraycopy(kp.keys,i+1,newc,i,len2-i-1);
          kp.keys = newc;
          --i;
        }
      }
   }
        //-------------------------------------------------------------------
   void addkey(char k) {
      int i,j;
      if(destination != null) {
         iloop:for(i=0;i<kp.keys.length;++i) {
            for(j=0;j<kp.keys[i].length;++j) {
               if(used[i][j] && kp.keys[i][j] == k) {
                  used[i][j] = true;
                  break iloop;
               }
            }
         }
         fixup();
      }
      else  {
          int row = getSelectedRow();
          int col = getSelectedColumn();
          int oldrows = kp.keys.length;
          if (row<0 || col < 0) return;
          if(row >= kp.keys.length) {
            char newk[][] = new char[row+1][];
            System.arraycopy(kp.keys,0,newk,0,kp.keys.length);
            for(i=kp.keys.length;i<=row;++i) newk[i] = new char[0];
            kp.keys = newk;
          }
          while(col >= kp.keys[row].length) kp.keys[row] = u.addchar(kp.keys[row],(char)0);
          kp.keys[row][col] = k;
          int oldmaxlen = maxlen;
          fixup();
          if(maxlen != oldmaxlen || oldrows != kp.keys.length)
                                createDefaultColumnsFromModel();
          changeSelection(row,col+1,false,false);
          changed = true;
      }
   }
        //-------------------------------------------------------------------
   void removekey(char k) {
      int i,j,len;
      iloop:for(i=0;i<kp.keys.length;++i) {
         for(j=0;j<kp.keys[i].length;++j) {
            if(kp.keys[i][j] == k) {
              kp.keys[i] = u.removeChar(kp.keys[i],j);
              changed = true;
              fixup();
              createDefaultColumnsFromModel();
              changeSelection(i,j,false,false);
              changed=true;
              break iloop;
            }
         }
      }
    }
        //--------------------------------------------------------------------
                    // handle output
   void send(KeyEvent e) {
      if(sendto==null || !sendto.isDisplayable())
          return;
//      if(sharkStartFrame.mainFrame.easywordlistpanel != null) {
//         if(!sharkStartFrame.mainFrame.easywordlistpanel.words.isEditing()) {
//startPR2006-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//             sharkStartFrame.mainFrame.easywordlistpanel.words.dispatchEvent(e);
//           Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(e);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//             return;
//         }
//      }
      if(sendto instanceof messages) {
//startPR2006-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//        ((messages)sendto).disp.dispatchEvent(e);
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(e);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        return;
      }
      Component c;
      if((c = goteditor(sendto)) != null ) {
//startPR2006-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//        c.dispatchEvent(e);
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(e);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        c.requestFocus();
      }
      else
//startPR2006-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//        sendto.dispatchEvent(e);
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(e);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   }
   //---------------------------------------------------------------
   Component goteditor(Component c) {
      if(!(c instanceof Container)) return null;
      else if(c instanceof javax.swing.tree.DefaultTreeCellEditor.EditorContainer)
         return ((Container)c).getComponent(0);
      else for(int i=0;i< ((Container)c).getComponentCount();++i) {
          Component c2;
          if ( (c2 = goteditor(((Container) c).getComponent(i))) != null)
            return c2;
       }
       return null;
   }
     // routine for 'accents' available for any component
   //---------------------------------------------------------------
   public static KeyEvent keycheck(KeyEvent e) {
      int i;
      char ch;
      int co = e.getKeyCode();
      if(e.isControlDown()) {
        lastcode = -1;
        if(co == GRAVE) {lastcode = 0;   return null;}
        else if(co == ACUTE) {lastcode = 1;   return null;}
        else if(co == CIRCUMFLEX) {lastcode = 2; return null;}
        else if(co == CEDILLA) {lastcode = 3; return null;}
        else if(co == TILDE) {lastcode = 4;   return null;}
        else if(co == UMLAUT) {lastcode = 5;  return null;}
      }
      else if(lastcode>=0 && (i = codes[lastcode].indexOf(ch = e.getKeyChar())) >= 0) {
         lastcode = -1;
         e.setKeyChar(replace[lastcode][i]);
         return e;
      }
      lastcode = -1;
      return e;
   }
     //==========================================================
    public static class updatekeypad
//startPR2004-08-27^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        extends JDialog {
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       keypad from,to;
       updatekeypad thisframe = this;
       String name;
       String list[];
       JList jlist;
       JPanel p1=new JPanel(new GridBagLayout());
       JPanel p1a=new JPanel(new GridBagLayout());
       JPanel p1b=new JPanel(new GridBagLayout());
       JPanel p1x=new JPanel(new GridBagLayout());
       JPanel p2=new JPanel(new GridBagLayout());
       JPanel p3=new JPanel(new GridBagLayout());
       GridBagConstraints grid1 = new GridBagConstraints();
       JButton button_new,button_ren,button_del,button_save,button_exit,button_font;
       JLabel label3;
       String currname,renametext,deletetext,savetext,label3text;
       String prevKeypadName;


       public updatekeypad() {
//startPR2004-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         super(sharkStartFrame.mainFrame);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          setBounds(sharkStartFrame.mainFrame.getBounds());
          this.setResizable(false);
          setVisible(true);
          prevKeypadName = keypad.keypadname;
          keypad.keypadname = null;
        this.addWindowListener(new java.awt.event.WindowAdapter() {
          public void windowClosing(WindowEvent e) {
              keypad.keypadname = prevKeypadName;
          }
        });
          setTitle(u.gettext("keypad_","titleup"));
          list = db.list(keypad_db,db.SAVEKEYPAD);
          jlist = new JList(list);
          getContentPane().setLayout(new GridBagLayout());
          grid1.weightx = grid1.weighty = 1;
          grid1.fill = GridBagConstraints.BOTH;
          grid1.gridx = -1;
          grid1.gridy = 0;
          grid1.weightx = 0;
          getContentPane().add(p1,grid1);
          grid1.weightx = 1;
          getContentPane().add(p1x,grid1);
          grid1.gridx = 0;
          grid1.gridy = -1;
          p1x.add(p2,grid1);
          p1x.add(p3,grid1);
          grid1.weightx = 1;
          grid1.gridx = 0;
          grid1.gridy = -1;
          p1.add(p1a,grid1);
          p1.add(p1b,grid1);
          grid1.fill = GridBagConstraints.HORIZONTAL;
          grid1.weighty = 0;
          p1a.add(u.mlabel("keypad_list"),grid1);
          grid1.fill = GridBagConstraints.BOTH;
          grid1.weighty = 1;
          p1a.add(new JScrollPane(jlist),grid1);
          grid1.fill = GridBagConstraints.NONE;
          grid1.weighty = 1;
          p1a.setBorder(BorderFactory.createEtchedBorder());
          p1b.setBorder(BorderFactory.createEtchedBorder());
          p1b.add(button_new = u.Button("keypad_new"),grid1);
          p1b.add(button_font = u.Button("keypad_font"),grid1);
          p1b.add(button_ren = u.Button("keypad_rename"),grid1);
          p1b.add(button_del = u.Button("keypad_delete"),grid1);
          p1b.add(button_save = u.Button("keypad_save"),grid1);
          p1b.add(button_exit = u.Button("keypad_exit"),grid1);
          grid1.fill = GridBagConstraints.BOTH;
          deletetext = button_del.getText();
          renametext = button_ren.getText();
          savetext = button_save.getText();
          from = new keypad(null);
          to = new keypad(null);
          to.autoCreateColumnsFromModel = true;
          from.autoCreateColumnsFromModel = true;
          to.setsource(from);
          from.destination = to;
          to.fixup();
          to.changeSelection(0,0,false,false);
          from.fix();

          grid1.fill = GridBagConstraints.HORIZONTAL;
          grid1.weighty = 0;
          p2.add(u.mlabel("keypad_from"), grid1);
          grid1.fill = GridBagConstraints.BOTH;
          grid1.weighty = 1;
          p2.add(new JScrollPane(from), grid1);
          grid1.fill = GridBagConstraints.HORIZONTAL;
          grid1.weighty = 0;
          p3.add(label3 = u.mlabel("keypad_to"), grid1);
          label3text = label3.getText();
          grid1.fill = GridBagConstraints.BOTH;
          grid1.weighty = 1;
          p3.add(new JScrollPane(to), grid1);

          jlist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
          jlist.addMouseListener(new java.awt.event.MouseAdapter() {
               public void mouseReleased(MouseEvent e) {
                  if(currname != null && to.changed)
                    if(!saveit(true))
                      return;
                  currname = (String)jlist.getSelectedValue();
//startSS04-05-12^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
                  if(currname==null)
                    return;
//endSS^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SS
                  to.kp = (savekeypad)db.find(keypad_db,
                            currname,db.SAVEKEYPAD);
//startPR2007-11-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//                  from.font = to.font = new Font(to.kp.fontname,to.kp.fonttype,to.kp.fontsize);
                  from.font = to.font = u.fontFromString(to.kp.fontname,to.kp.fonttype,(float)to.kp.fontsize);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                  to.m = from.m = getFontMetrics(to.font);
                  from.kp.fontname = to.kp.fontname;
                  from.kp.fontsize = to.kp.fontsize;
                  from.kp.fonttype = to.kp.fonttype;
                  from.getfromfont();
                  to.empty = from.empty = false;
                  to.fixup();
                  to.changeSelection(0,0,false,false);
                  from.fix();
                  from.fixup();
                  to.createDefaultColumnsFromModel();
                  from.createDefaultColumnsFromModel();
                  label3.setText(u.edit(label3text,currname));
            }
          });
          button_font.addActionListener(new java.awt.event.ActionListener() {
              public void actionPerformed(ActionEvent e) {
//startPR2008-01-15^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//                new FontChooser(u.gettext("choosefont2","label"),to.font,false){
                      new FontChooser(u.gettext("choosefont2","label"),sharkStartFrame.defaultfontname, null, null, null,false, false, false, thisframe){
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                  public void update(){     //OVERRIDE FONTCHOOSER.UPDATE()
                   from.font = to.font = chosenfont;
                   to.m = from.m = getFontMetrics(to.font);
                   from.kp.fontname = to.kp.fontname = to.font.getName();
                   from.kp.fontsize = to.kp.fontsize = to.font.getSize();
                   from.kp.fonttype = to.kp.fonttype = to.font.getStyle();
                   from.getfromfont();
                   from.fix();
                   from.fixup();
                   to.createDefaultColumnsFromModel();
                   from.createDefaultColumnsFromModel();
                   to.changed = true;
                  }
                };
              }
          });

          button_new.addActionListener(new java.awt.event.ActionListener() {
              public void actionPerformed(ActionEvent e) {
                  if(to != null) if(!saveit(true));
                  JOptionPane getpw = new JOptionPane(
                     u.gettext("keypad_", "entername"),
                     JOptionPane.PLAIN_MESSAGE,
                     JOptionPane.OK_CANCEL_OPTION);
                 getpw.setWantsInput(true);
//startPR2004-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//                 JDialog dialog2 = getpw.createDialog(null, u.gettext("keypad_", "newh"));
                 JDialog dialog2 = getpw.createDialog(thisframe, u.gettext("keypad_", "newh"));
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                 while(true) {
                  dialog2.setVisible(true);
                  Object result = getpw.getValue();
                  if(result == null
                      || result instanceof Integer
                      &&((Integer)result).intValue() != JOptionPane.OK_OPTION) return;
                  String newname = (String)getpw.getInputValue();
                  if(newname.length() == 0) continue;
                  if(db.query(keypad_db,newname,db.SAVEKEYPAD) >= 0) {
//startPR2004-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//                      u.okmess(u.gettext("keypad_", "newh"), u.gettext("keypad_", "already",newname));
                    u.okmess(u.gettext("keypad_", "newh"), u.gettext("keypad_", "already",newname), thisframe);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                      continue;
                  }
                  currname = newname;
                  from.font = to.font = sharkStartFrame.wordfont;
                  to.m = from.m = getFontMetrics(to.font);
                  from.kp.fontname = to.kp.fontname = to.font.getName();
                  from.kp.fontsize = to.kp.fontsize = to.font.getSize();
                  from.kp.fonttype = to.kp.fonttype = to.font.getStyle();
                  to.kp.keys = new char[0][];
                  from.kp.keys = new char[0][];
                  to.empty = from.empty = false;
                  from.getfromfont();
                  to.fixup();
                  from.fix();
                  from.fixup();
                  to.createDefaultColumnsFromModel();
                  from.createDefaultColumnsFromModel();
                  to.changeSelection(0,0,false,false);
                  label3.setText(u.edit(label3text,currname));
                  validate();
                  return;
                 }
              }
          });
          button_ren.addActionListener(new java.awt.event.ActionListener() {
              public void actionPerformed(ActionEvent e) {
                  if (currname == null) return;
                  if(to != null) if(!saveit(true)) return;
                  JOptionPane getpw = new JOptionPane(
                     u.gettext("keypad_", "enternewname", currname),
                     JOptionPane.PLAIN_MESSAGE,
                     JOptionPane.OK_CANCEL_OPTION);
                 getpw.setWantsInput(true);
//startPR2004-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//                 JDialog dialog2 = getpw.createDialog(null, u.gettext("keypad_", "renameh"));
                 JDialog dialog2 = getpw.createDialog(thisframe, u.gettext("keypad_", "renameh"));
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                 while(true) {
                  dialog2.setVisible(true);
                  Object result = getpw.getValue();
                  if(result == null
                      || result instanceof Integer
                      &&((Integer)result).intValue() != JOptionPane.OK_OPTION) return;
                  String newname = (String)getpw.getInputValue();
                  if(newname.length() == 0) continue;
                  if(db.query(keypad_db,newname,db.SAVEKEYPAD) >= 0) {
//startPR2004-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//                      u.okmess(u.gettext("keypad_", "renameh", currname), u.gettext("keypad_", "already",newname));
                    u.okmess(u.gettext("keypad_", "renameh", currname), u.gettext("keypad_", "already",newname), thisframe);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                      continue;
                  }
                  db.rename(keypad_db,currname,newname,db.SAVEKEYPAD);
                  list[u.findString(list,currname)]= newname;
                  jlist.setListData(list);
                  currname = newname;
                  label3text = label3.getText();
                  button_del.setText(u.edit(deletetext,currname));
                  button_ren.setText(u.edit(renametext,currname));
                  button_save.setText(u.edit(savetext,currname));
                  label3.setText(u.edit(label3text,currname));
                  sharkStartFrame.mainFrame.addchoosekeypad();
                  break;
                 }
              }
          });
          button_del.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
              if(currname != null && !currname.equals("")
//startPR2004-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//                 && u.yesnomess(u.gettext("keypad_", "deleteh"),u.gettext("keypad_", "delete", currname))){
                 && u.yesnomess(u.gettext("keypad_", "deleteh"),u.gettext("keypad_", "delete", currname), thisframe)){
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                  db.delete(keypad_db,currname,db.SAVEKEYPAD);
                  jlist.setListData(list=u.removeString(list,currname));
                  jlist.clearSelection();
                  label3.setText(u.edit(label3text,""));
                  currname = null;
                  to.empty = from.empty = true;
                  to.createDefaultColumnsFromModel();
                  from.createDefaultColumnsFromModel();
                  sharkStartFrame.mainFrame.addchoosekeypad();
             }
           }
         });
         button_save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
              if(currname != null && !currname.equals("")) {
                saveit(false);
              }
            }
         });
         button_exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
              if(currname != null && !currname.equals("")) {
                if(saveit(true)) dispose();
              }
              else dispose();
            }
         });
         addWindowListener(new java.awt.event.WindowAdapter() {
          public void windowDeactivated(WindowEvent e) {
//             dispose();
          }
          public void windowClosed(WindowEvent e) {
            updating = false;
           }
          public void windowClosing(WindowEvent e) {
             if(saveit(true)) {
                sharkStartFrame.mainFrame.addchoosekeypad();
                sharkStartFrame.mainFrame.manBar1.validate();
                sharkStartFrame.mainFrame.manBar1.repaint();
                dispose();
             }
          }
         });
         addKeyListener(new KeyAdapter() {
          public void keyPressed(KeyEvent e) {
              int code = e.getKeyCode();
              if(code == KeyEvent.VK_ESCAPE) dispose();
           }
         });
//startPR2004-09-03^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
           // enables exiting screen via the ESC key
           jlist.addKeyListener(new KeyAdapter() {
             public void keyPressed(KeyEvent e) {
               int code = e.getKeyCode();
               if(code == KeyEvent.VK_ESCAPE)
                 dispose();
             }
           });
           button_new.addKeyListener(new KeyAdapter() {
             public void keyPressed(KeyEvent e) {
               int code = e.getKeyCode();
               if(code == KeyEvent.VK_ESCAPE)
                 dispose();
             }
           });
           button_font.addKeyListener(new KeyAdapter() {
             public void keyPressed(KeyEvent e) {
               int code = e.getKeyCode();
               if(code == KeyEvent.VK_ESCAPE)
                 dispose();
             }
           });
           button_ren.addKeyListener(new KeyAdapter() {
             public void keyPressed(KeyEvent e) {
               int code = e.getKeyCode();
               if(code == KeyEvent.VK_ESCAPE)
                 dispose();
             }
           });
           button_del.addKeyListener(new KeyAdapter() {
             public void keyPressed(KeyEvent e) {
               int code = e.getKeyCode();
               if(code == KeyEvent.VK_ESCAPE)
                 dispose();
             }
           });
           button_save.addKeyListener(new KeyAdapter() {
             public void keyPressed(KeyEvent e) {
               int code = e.getKeyCode();
               if(code == KeyEvent.VK_ESCAPE)
                 dispose();
             }
           });
           to.addKeyListener(new KeyAdapter() {
             public void keyPressed(KeyEvent e) {
               int code = e.getKeyCode();
               if(code == KeyEvent.VK_ESCAPE)
                 dispose();
             }
           });
           from.addKeyListener(new KeyAdapter() {
             public void keyPressed(KeyEvent e) {
               int code = e.getKeyCode();
               if(code == KeyEvent.VK_ESCAPE)
                 dispose();
             }
           });
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         validate();
         requestFocus();
       }
     //----------------------------------------------------------------
     boolean  saveit(boolean mess) {
         int i,len;
         if(!to.changed || currname==null || currname.length()==0) return  true;
         if(mess) {
//startPR2004-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//             i = u.yesnocancel(u.gettext("keypad_", "saveh"),u.gettext("keypad_", "save",currname));
           i = u.yesnocancel(u.gettext("keypad_", "saveh"),u.gettext("keypad_", "save",currname), thisframe);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
             if(i < 0) return false;   // cancel
             if(i > 0) return true;    // no
         }
         len = to.kp.keys.length;
         for(i=0;i<len;++i) {
            if(to.kp.keys[i].length == 0) {
               char newk[][] = new char[--len][];
               System.arraycopy(to.kp.keys,0,newk,0,i);
               if(len>i) System.arraycopy(to.kp.keys,i+1,newk,i,len-i);
               to.kp.keys = newk;
               --i;
            }
         }
//         to.kp.fontsize = 24;     //while building publickeypad - maybe should be in all the time?
         db.update(keypad_db, currname, to.kp, db.SAVEKEYPAD);
         jlist.setListData(list=u.addStringSort(list,currname));
         to.changed = false;
         sharkStartFrame.mainFrame.addchoosekeypad();
         return true;
     }
 }
 //=======================================================================

public static usekeypad getNewUseKeypad(String name1,JDialog owner1, char extrakeys[], int pos){
    // needed so that can get rid of close button
    JDialog.setDefaultLookAndFeelDecorated(true);
    usekeypad retusekeypad = new usekeypad(name1, owner1, extrakeys, pos);
    JDialog.setDefaultLookAndFeelDecorated(false);
    return retusekeypad;
}

public static usekeypad getNewUseKeypad(String name1,JDialog owner1, char extrakeys[]){
    // needed so that can get rid of close button
    JDialog.setDefaultLookAndFeelDecorated(true);
    usekeypad retusekeypad = new usekeypad(name1, owner1, extrakeys);
    JDialog.setDefaultLookAndFeelDecorated(false);
    return retusekeypad;
}


public static usekeypad getNewUseKeypad(String name1,JFrame owner1, char extrakeys[], int pos){
    // needed so that can get rid of close button
    JDialog.setDefaultLookAndFeelDecorated(true);
    usekeypad retusekeypad = new usekeypad(name1, owner1, extrakeys, pos);
    JDialog.setDefaultLookAndFeelDecorated(false);
    return retusekeypad;
}

public static usekeypad getNewUseKeypad(String name1,JFrame owner1, char extrakeys[]){
    // needed so that can get rid of close button
    JDialog.setDefaultLookAndFeelDecorated(true);
    usekeypad retusekeypad = new usekeypad(name1, owner1, extrakeys);
    JDialog.setDefaultLookAndFeelDecorated(false);
    return retusekeypad;
}

public static usekeypad getNewUseKeypad(String name1,JDialog owner1){
    JDialog.setDefaultLookAndFeelDecorated(true);
    usekeypad retusekeypad = new usekeypad(name1, owner1);
    JDialog.setDefaultLookAndFeelDecorated(false);
    return retusekeypad;
}

public static usekeypad getNewUseKeypad(String name1,JFrame owner1){
    JDialog.setDefaultLookAndFeelDecorated(true);
    usekeypad retusekeypad = new usekeypad(name1, owner1);
    JDialog.setDefaultLookAndFeelDecorated(false);
    return retusekeypad;
}



 public static class usekeypad extends JDialog {
   public static String keypadname;
   String name;
   keypad kk;
   int startx,starty;
   int lastx = -1;
   int lasty = -1;
//   JLabel label;
   JPanel pp;
//startPR2008-06-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   Window owner;
   usekeypad ukp = this;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   Dimension mindim;
   Dimension maxdim;

   int fixedheight = -1;
   int fixedwidth = -1;
   int fixedmaxheight = -1;
   int fixedmaxwidth = -1;

   public static int borderInWindow = 6;
   Color ukpbgcolor = null;

   public boolean mousePressing = false;
   int pos;



//   public usekeypad(String name1,Window owner1, char extrakeys[]) {
//     super(owner1);
//startPR2008-06-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//     owner = owner1;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//     setup(name1,extrakeys);
//   }
//   public usekeypad(String name1,Window owner1) {
//     super(owner1);
//startPR2008-06-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//     owner = owner1;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//     setup(name1, null);
//   }


   public usekeypad(String name1,JDialog owner1, char extrakeys[], int pos) {
     super(owner1);
//startPR2008-06-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     owner = owner1;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     setup(name1,extrakeys, pos);
   }
   public usekeypad(String name1,JDialog owner1, char extrakeys[]) {
     super(owner1);
//startPR2008-06-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     owner = owner1;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     setup(name1,extrakeys, DEFAULT);
   }
   public usekeypad(String name1,JFrame owner1, char extrakeys[], int pos) {
     super(owner1);
//startPR2008-06-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     owner = owner1;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     setup(name1,extrakeys, pos);
   }
   public usekeypad(String name1,JFrame owner1, char extrakeys[]) {
     super(owner1);
//startPR2008-06-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     owner = owner1;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     setup(name1,extrakeys, DEFAULT);
   }
   public usekeypad(String name1,JDialog owner1) {
     super(owner1);
//startPR2008-06-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     owner = owner1;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     setup(name1, null, DEFAULT);
   }
   public usekeypad(String name1,JFrame owner1) {
     super(owner1);
//startPR2008-06-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     owner = owner1;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     setup(name1, null, DEFAULT);
   }


public static void removeCloseButton(Component comp) {
    if (comp instanceof JMenu) {
      Component[] children = ((JMenu) comp).getMenuComponents();
      for (int i = 0; i < children.length; ++i)
        removeCloseButton(children[i]);
    }
    else if (comp instanceof AbstractButton) {
      Action action = ((AbstractButton) comp).getAction();
      String cmd = (action == null) ? "" : action.toString();
  //    if (cmd.contains("CloseAction")) {
        comp.getParent().remove(comp);
  //    }
    }
    else if (comp instanceof Container) {
      Component[] children = ((Container) comp).getComponents();
      for (int i = 0; i < children.length; ++i)
        removeCloseButton(children[i]);
    }
    comp.validate();
  }



   void setup(String name1, char extrakeys[], int pos1) {
     name = name1;
     pos = pos1;
     this.setFocusableWindowState(false);
     this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
     removeCloseButton(ukp);
     ukp.setCursor(Cursor.getDefaultCursor());
     pp = new JPanel(new BorderLayout());
        
     this.addComponentListener(new ComponentAdapter() {
      public void componentResized(ComponentEvent e) {
        adjust();
      }
      public void componentMoved(ComponentEvent e) {
          if(mousePressing){
              lastx = ((Window)e.getSource()).getLocation().x;
              lasty = ((Window)e.getSource()).getLocation().y;
          }
      }
    });

     kk = new keypad(name);
     kk.setFocusable(false);
     kk.setShowVerticalLines(false);
     kk.setShowHorizontalLines(false);
     if(extrakeys != null) {
        kk.extrakeys(extrakeys);
        kk.createDefaultColumnsFromModel();
     }
     kk.sendto = getOwner();

     getContentPane().add(pp, BorderLayout.CENTER);
//     pp.add(label, BorderLayout.NORTH);
     pp.add(kk, BorderLayout.CENTER);
    ukp.setCursor(Cursor.getDefaultCursor());
//startPR2008-06-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//     if(shark.macOS){
       addMouseListener(new java.awt.event.MouseAdapter() {
         public void mouseEntered(MouseEvent e) {
           Component c;
           if(owner instanceof sharkGame) c = ((sharkGame)owner).gamePanel;
           else c = owner;
           c.setCursor(Cursor.getDefaultCursor());
         }

         public void mouseReleased(MouseEvent e) {
             mousePressing = false;
             adjust();
             ukp.kk.setVisible(true);
             Point p = ukp.getLocation();
             Rectangle r;
             Window ww;
             if(pos == BOTTOMRIGHTABSOLUTE && (ww=ukp.getOwner().getOwner())!=null){
                 r = ww.getBounds();
             }
             else
                  r = ukp.getOwner().getBounds();
             int px = Math.min(Math.max(0+ukp.borderInWindow, p.x), r.width-ukp.getWidth()-ukp.borderInWindow);
             int py = Math.min(Math.max(0+ukp.borderInWindow, p.y), r.height-ukp.getHeight()-ukp.borderInWindow);
             ukp.setLocation(px,py);
         }

         public void mousePressed(MouseEvent e) {
             mousePressing = true;
         }


         public void mouseExited(MouseEvent e) {
           if(ukp.contains(e.getPoint())) return;
           if(owner instanceof sharkGame) ((sharkGame)owner).setsprite();
         }
       });
//     }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//     setName(keypadname);
 //   pack(); // needed so that extra button appear
 //   position();
 //   doresize();
//    validate();
 
   }

   void adjust(){
          ukp.validate();
          ukp.doresize();
          ukp.validate();
   }


   void doresize(){
        int i = (kk.getHeight())/kk.kp.keys.length;
        if(i>0){
            kk.setRowHeight(kk.kh = i);
            kk.repaint();
       }
        kk.kw = kk.getWidth()/kk.maxlen;
   }

   void setwindowsize() {
       // kk.kh deliberately - to make keys square and keypad a bit wider for touch screens
     int ww = kk.kh*kk.maxlen + ((kk.maxlen+1)*kk.keyborder);
     if(fixedwidth<0)fixedwidth = ww;
     int topbar = getHeight()-getContentPane().getHeight();
     int hh = kk.kh * kk.kp.keys.length + topbar + ((kk.kp.keys.length +1) * kk.keyborder);
     if(fixedheight<0)fixedheight = hh;
     this.setMinimumSize(new Dimension(fixedwidth, fixedheight));
     Rectangle r = owner.getBounds();
     setSize(ww,hh);
     Window wwin;
     if(pos == BOTTOMLEFT){
          setLocation(r.x + borderInWindow, r.y+r.height - getHeight()-borderInWindow);
     }
     else if(pos == BOTTOMRIGHTABSOLUTE && (wwin=ukp.getOwner().getOwner())!=null){
         r = wwin.getBounds();
         setLocation(r.x + r.width - getWidth()-borderInWindow,
                      r.y+r.height - getHeight()-borderInWindow);
     }
     else{
         setLocation(r.x + r.width - getWidth()-borderInWindow,
                      r.y+r.height - getHeight()-borderInWindow);
     }
   }


   void makekeypad( char extrakeys[]) {
     kk = new keypad(name);
     kk.setFocusable(false);
     kk.setShowVerticalLines(false);
     kk.setShowHorizontalLines(false);
//     savekeys = kk.kp.keys;
     if(extrakeys != null && extrakeys.length>0) {
        kk.extrakeys(extrakeys);
        kk.createDefaultColumnsFromModel();
     }
     kk.sendto = getOwner();
   }


 }




    static int convert(char c) {
     if(c == SHIFT) return -1;
     if(c <= ENTER) return c << 17;
     return c;
   }


   public static void activate(Window win,char[] extrakeys) {
       activate(win,extrakeys,DEFAULT);
   }

    public static void activate(Window win,char[] extrakeys, int pos) {

      usekeypad w = doactivate(win,extrakeys,pos);
      if(w != null) {
        w.kk.shift = false;
        w.setVisible(true);
        if(win instanceof sharkGame && ((sharkGame)win).helpPanel != null) {
          ((sharkGame)win).adjusthelp();
        }
      }
   };


   static usekeypad doactivate(Window win,char extrakeys[], int pos) {
      JDialog jdg = null;
      JFrame jfm = null;
      if(win instanceof JDialog)jdg = (JDialog)win;
      else if (win instanceof JFrame)jfm = (JFrame)win;
     usekeypad w = find(win);

     String keypadname2 = keypadname;
     if(win instanceof sharkGame && sharkGame.studentflipped) {
         keypadname2 = sharkGame.otherplayer.optionstring2("keypad");
     }
     if(keypadname2 != null) {
        if(w == null){
            if(jdg!=null)
                w = getNewUseKeypad(keypadname2, jdg, extrakeys, pos);
            else
                w = getNewUseKeypad(keypadname2, jfm, extrakeys, pos);
        }
        else {
           w.kk.extrakeys(extrakeys);
           w.kk.createDefaultColumnsFromModel();
        }
        
        w.validate();
        w.pack();
        w.kk.kh = w.kk.m.getHeight();
        w.setwindowsize();  // based on table height
        w.doresize();  // based on table size
        
        w.validate();        
        return w;
     }
     return null;
   }

    static public void dofullscreenkeypad(Window w, boolean on){
         if((student.optionstring("keypad")) == null)return;
         if(on){
            String defkey;
            if((defkey = u.gettext("keypad_","signon")) != null) {
               keypad.keypadname = defkey;
               keypad.activate(w,new char[] {(char)keypad.SHIFT,' ',
                   (char)keypad.BACKSPACE,(char)keypad.ENTER}, keypad.BOTTOMRIGHTABSOLUTE);
            }
         }
         else{
             keypad.deactivate(w);
         }
     }


   static  public void deactivate(Window win) {
     usekeypad w = find(win);
     if(w != null) {
       w.setVisible(false);
       if(win instanceof sharkGame) {
        if(((sharkGame)win).helpPanel != null)  ((sharkGame)win).adjusthelp();
        ((sharkGame)win).requestFocus();
       }
     }
   }
   public static int keypadheight(Window win) {
      JDialog jdg = null;
      JFrame jfm = null;
      if(win instanceof JDialog)jdg = (JDialog)win;
      else if (win instanceof JFrame)jfm = (JFrame)win;
     usekeypad w = find(win);
     if(w == null){
         if(jdg!=null)
             w = getNewUseKeypad(keypadname,jdg);
         else
             w = getNewUseKeypad(keypadname,jfm);
     }
     return w.getHeight() + (w.kk.extras?w.kk.kh:0);
   }

   public static int keypadwidth(Window win) {
      JDialog jdg = null;
      JFrame jfm = null;
      if(win instanceof JDialog)jdg = (JDialog)win;
      else if (win instanceof JFrame)jfm = (JFrame)win;
     usekeypad w = find(win);
     if(w == null){
         if(jdg!=null)
             w = getNewUseKeypad(keypadname,jdg);
         else
             w = getNewUseKeypad(keypadname,jfm);
     }
     return w.getWidth();
   }
   public static usekeypad find(Window win) {
      Window w[] = win.getOwnedWindows();
      usekeypad ret = null;
      for(int i=0;i<w.length;++i) {
         if (w[i] instanceof usekeypad) {
            if(keypadname!=null && !((usekeypad)w[i]).name.equals(keypadname))
                      { w[i].dispose(); }
            else ret = (usekeypad)w[i];
         }
      }
      return ret;
   }
   
   
   public static Font getfont(String kpname) {
       return getfont(kpname, false);
   }   
   
   public static Font getfont(String kpname, boolean nosetwordfont) {
      savekeypad kp;
      if((kp = (savekeypad)db.find(keypad_db,kpname,db.SAVEKEYPAD))==null)
          kp = (savekeypad)db.find(publickeypad_db,kpname,db.SAVEKEYPAD);
      if(kp == null) return null;
//startPR2007-11-19^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      if(kp.fontname.equalsIgnoreCase("wordshark"))
        if(!nosetwordfont && u.isdefaultfont(kp.fontname)){
          sharkStartFrame.mainFrame.setwordfont();
          kp.fontname = sharkStartFrame.wordfont.getName();
        }
//      return new Font(kp.fontname,Font.PLAIN,sharkStartFrame.BASICFONTPOINTS);
        return u.fontFromString(kp.fontname,Font.PLAIN,(float)sharkStartFrame.BASICFONTPOINTS);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   }
   public static  char[] addmore(char list[],word w[]) {
      char ret[] = null;
      int i,j,k;
      String s;
      char ch;
      for(i=0;i<w.length;++i) {
        s = w[i].v();
        jloop: for(j=0;j<s.length();++j) {
          ch = Character.toLowerCase(s.charAt(j));
          if(u.letters.indexOf(ch) < 0 ) {
             for(k=0; k<list.length;++k)
                   if(ch == list[k]) continue jloop;
             list = u.addchar(list, ch);
          }
        }
      }
      return list;
   }
   public static  char[] addmore(char list[],sentence.seg w[]) {
      char ret[] = null;
      int i,j,k;
      String s;
      char ch;
      for(i=0;i<w.length;++i) {
        jloop: for(j=0;j<w[i].val.length();++j) {
          ch = Character.toLowerCase(w[i].val.charAt(j));
          if(u.letters.indexOf(ch) < 0 ) {
             for(k=0; k<list.length;++k)
                   if(ch == list[k]) continue jloop;
             list = u.addchar(list, ch);
          }
        }
      }
      return list;
   }
}
