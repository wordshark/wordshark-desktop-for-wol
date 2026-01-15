package shark;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;


public abstract class FontChooser
  extends JDialog {
    private JDialog fontFrame;
    static Font chosenfont;    //HOLDS THE CURRENTLY CHOSEN FONT OBJECT
    private int w;               //TO HOLD WIDTH OF FRAME
    private int h;               //TO HOLD HEIGHT OF FRAME
    public static int pointSizeMin = 8;         //MINIMUM FONT POINT SIZE
    public static int pointSizeMax = sharkStartFrame.MAXFONTPOINTS_2;        //MAXIMUM FONT POINT SIZE
    private static int pointSizeStep = 1;        //POINT SIZE STEP
//startPR2007-12-06^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    public static short pointSizeMenuMax = 30;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR

    static Font chosenInfantFont;
    private boolean changePoints;
    private String title;
    private Font oldFont;
    private Font oldInfantFont;
    private Font defaultInfantFont;
    private Font defaultFont;
    ChooseFontPanel currPanel;
    ChooseFontPanel stdPanel;
    ChooseFontPanel infantPanel;
    public static Font defaultTreeFont;
//startPR2007-11-25^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    boolean isinfantcourse = sharkStartFrame.mainFrame.isinfantcourse();
    boolean allowInfantTabFirst = false;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2008-01-15^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    boolean admindefault = false;
    static boolean settoadmindefault = false;
    String strdefault = " "+u.gettext("font","defaultlabel");
    Window parentwin;
    boolean isWordlistsAndGames;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR

//startPR2008-01-15^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      public  FontChooser (String titl, Font oldFt, Font defaultft, Font oldInfantFt, Font defaultInfantFt, boolean changePts, JFrame owner)
          public  FontChooser (String titl, Font oldFt, Font defaultft, Font oldInfantFt, Font defaultInfantFt, boolean changePts, boolean wantAdmindefaultButton, boolean forWordlistAndGames, JFrame owner)
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      {
        super(owner);
        parentwin = owner;
//startPR2007-11-25^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        allowInfantTabFirst = true;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        fontFrame = new JDialog(owner,true);  // pr 13/11/06
        title = titl;
        oldFont = oldFt;
        defaultFont = defaultft;
        oldInfantFont = oldInfantFt;
        defaultInfantFont = defaultInfantFt;
        changePoints = changePts;
//startPR2008-01-15^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        admindefault = wantAdmindefaultButton;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        isWordlistsAndGames = forWordlistAndGames;
        addConstructor();
      }
//startPR2008-01-15^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      public  FontChooser (String titl, Font oldFt, Font defaultft, Font oldInfantFt, Font defaultInfantFt, boolean changePts, JDialog owner)
          public  FontChooser (String titl, Font oldFt, Font defaultft, Font oldInfantFt, Font defaultInfantFt, boolean changePts, boolean wantAdmindefaultButton, boolean forWordlistAndGames, JDialog owner)
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      {
        super(owner);
        parentwin = owner;
        fontFrame = new JDialog(owner,true); // pr 13/11/06
        title = titl;
        oldFont = oldFt;
        defaultFont = defaultft;
        oldInfantFont = oldInfantFt;
        defaultInfantFont = defaultInfantFt;
        changePoints = changePts;
//startPR2008-01-15^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        admindefault = wantAdmindefaultButton;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        isWordlistsAndGames = forWordlistAndGames;
        addConstructor();
      }

      private void addConstructor(){
        w = sharkStartFrame.mainFrame.getSize().width;
        h = sharkStartFrame.mainFrame.getSize().height;
//        fontFrame.setBounds(w/8,h/8,w*3/8,h*2/3);
        fontFrame.setBounds(u2_base.adjustBounds(new Rectangle(w/8,h/8,w*3/8,h*2/3)));
        fontFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        fontFrame.setTitle(title);//TITLE IS PASSED HERE AS A PARAMETER - IT IS FOUND USING U.GETTEXT
        fontFrame.setResizable(false);

        this.addWindowListener(new WindowAdapter()//IF FRAME LOOSES FOCUS IT IS CLOSED
        {
          public void windowDeactivated(WindowEvent e){
            dispose();
          }

        });
//startPR2008-01-15^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        settoadmindefault = false;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        chosenfont = oldFont;
        if(oldInfantFont!=null){
          chosenInfantFont = oldInfantFont;
          JTabbedPane jtp = new JTabbedPane();
//startPR2007-11-25^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//            jtp.addTab("Letters and Sounds Course", infantPanel = new ChooseFontPanel(chosenInfantFont, defaultInfantFont));
//            jtp.addTab("Other Courses", currPanel = stdPanel = new ChooseFontPanel(chosenfont, defaultFont));
          jtp.addTab(u.gettext("fontchooser", "tab1"), infantPanel = new ChooseFontPanel(chosenInfantFont, defaultInfantFont));
          jtp.addTab(u.gettext("fontchooser", "tab2"), stdPanel = new ChooseFontPanel(chosenfont, defaultFont));
//          if(isinfantcourse && allowInfantTabFirst) currPanel = infantPanel;
//          else currPanel = stdPanel;
          currPanel = stdPanel;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          jtp.setFocusable(false);
          jtp.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent evt) {
              JTabbedPane pane = (JTabbedPane) evt.getSource();
              if(pane.getSelectedIndex()>0){
                currPanel = stdPanel;
              }
              else{
                currPanel = infantPanel;
              }
              currPanel.updateFont();
            }
          });
//startPR2007-11-25^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//          jtp.setSelectedIndex(1);
//          if(isinfantcourse && allowInfantTabFirst)jtp.setSelectedIndex(0);
//          else 
              jtp.setSelectedIndex(1);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          fontFrame.getContentPane().add(jtp);
        }
        else{
            Font optionsFont = defaultFont;
            if(isinfantcourse){
                String fontdet[] =  (String[]) db.find(sharkStartFrame.optionsdb, "wordfontinfant", db.TEXT);
                if(fontdet!=null){
                    Font finfant = u.fontFromString(fontdet[0], Integer.parseInt(fontdet[1]),sharkStartFrame. BASICFONTPOINTS);
                    optionsFont = finfant;                     
                }
            }
            else{
                String fontdet[] =  (String[]) db.find(sharkStartFrame.optionsdb, "wordfont", db.TEXT);
                if(fontdet!=null){
                    Font finfant = u.fontFromString(fontdet[0], Integer.parseInt(fontdet[1]),sharkStartFrame. BASICFONTPOINTS);
                    optionsFont = finfant;                     
                }
            }
          currPanel = new ChooseFontPanel(chosenfont, optionsFont);
          fontFrame.getContentPane().add(currPanel);
        }
        currPanel.updateFont();
        fontFrame.setVisible(true);
        fontFrame.validate();
      }


      public void doUpdate(){
        update();
                    if(parentwin instanceof admin){
                ((admin)parentwin).updateonactivate = admin.ACTIVATESETTINGS;
            }
      }

      public abstract void update();    //CALLED WHEN OK BUTTON PRESSED,
                                      //BODY DEFINED WHERE A NEW FONTCHOOSER IS CREATED

  class JListRenderer extends JLabel implements ListCellRenderer{
    JSeparator separator;

    public JListRenderer() {
      setOpaque(true);
      setBorder(new javax.swing.border.EmptyBorder(1, 1, 1, 1));
      separator = new JSeparator(JSeparator.HORIZONTAL);
    }

    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus){
      String str = (value == null) ? "" : value.toString();
      if(str.equals(new String("SEPARATOR"))){
        return separator;
      }
      if(isSelected){
        setBackground(list.getSelectionBackground());
        setForeground(list.getSelectionForeground());
      }else{
        setBackground(list.getBackground());
        setForeground(list.getForeground());
      }
      setFont(list.getFont());
      setText(str);
      return this;
    }
  }

  class ChooseFontPanel extends JPanel{
    JSeparator separator;
    JLabel displayLabel;           //LABEL TO DISPLAY CURRENT FONT
    JTextArea fontDisplay;
    GridBagLayout layout1;               //LAYOUT FOR FRAME
    GridBagConstraints grid;             //CONSTRAINTS FOR FRAME
    JList fontList;                      //LIST OF THE FONTNAMES AVAILABLE
    JScrollPane choosefont;              //SCROLLPANE TO PUT FONT LIST IN
    int j;                               //INDEX TO REFERENCE CURRENTLY CHOSEN FONT
    JPanel buttonPanel;                  //PANEL FOR THE BUTTONS
    GridBagLayout gbLayout;              //LAYOUT FOR THE BUTTON PANEL
    GridBagConstraints bGrid;            //CONSTRAINTS FOR THE BUTTON PANEL
    JButton ok;                          //OK BUTTON
    JButton cancel;                      //CANCEL BUTTON
    JPanel sizePane;                     //FOR SPINNER AND POINT CHANGE LABEL
    JLabel label;                        //POINT CHANGE LABEL
    JSpinner chooseSize;                 //SPINNER TO CHOOSE POINT SIZE
//startPR2008-01-15^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    JCheckBox setDefault;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    Font defaultFnt;
    private int fontStyle;
    private int fontSize;
    String fontnames [];
//startPR2008-01-15^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
 //   JButton btadmindefault;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2012-06-16^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    JPanel mainpn;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR

    public ChooseFontPanel(Font prevFont, Font defaultfn) {
      super(new GridBagLayout());

      fontStyle = prevFont.getStyle();
      fontSize = prevFont.getSize();

      defaultFnt = defaultfn;
      grid = new GridBagConstraints();
//startPR2012-06-16^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      grid.fill = grid.BOTH;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      //SET CONSTRAINTS FOR THE FRAME'S GRID
      grid.weightx = grid.weighty = 1;
      grid.gridwidth = grid.REMAINDER;

      //CREATE A LABEL TO SAY "SAMPLE"
      displayLabel = new JLabel(u.gettext("font_label","label"));
 //     displayLabel.setPreferredSize(new Dimension(100,20));

      //CREATE A TEXTFIELD TO DISPLAY CHOSEN FONT
      fontDisplay = new JTextArea("Aa Bb Cc Dd Ee Ff Gg Hh Ii Jj Kk " +
                                      "Ll Mm Nn Oo Pp Qq Rr Ss Tt Uu Vv Ww Xx Zz");
      fontDisplay.setBorder(BorderFactory.createEtchedBorder());
      fontDisplay.setLineWrap(true);
      fontDisplay.setPreferredSize(new Dimension(250,120));
      fontDisplay.setEditable(false);
      fontDisplay.setBackground(displayLabel.getBackground());
      fontDisplay.setFont(prevFont);
//startPR2008-01-15^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      if(defaultFnt !=null){
//        setDefault = new JCheckBox(u.gettext("default_font", "label"));
//        setDefault.addActionListener(new java.awt.event.ActionListener() {
//          public void actionPerformed(ActionEvent e) {
//            setDefault.setSelected(true);
//            if (u.findString(fontnames, defaultFnt.getName()) >= 0)
//              fontList.setSelectedValue(defaultFnt.getName(), true);
//            else if (u.findString(fontnames, defaultFnt.getName().toLowerCase()) >= 0)
//              fontList.setSelectedValue(defaultFnt.getName(), true);
//startPR2007-12-06^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//            if(changePoints){
//              fontSize = u.getminimumfontsize(defaultTreeFont);
//              updateFont();
//            }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//          }
//        });
//      }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      //CREATE SCROLL PANE TO TAKE LIST OF NATIVE FONTS
      String recommended[] = u.splitString(u.gettext("font", "recommended"));
      if(shark.macOS){
          for(int i = 0; i < recommended.length; i++){
              recommended[i] = recommended[i].replaceAll(" ", "");
          }
      }
      GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();//GET NATIVE FONTS
      Font systemFonts[] = e.getAllFonts();
      fontnames = new String [systemFonts.length];
      for(int i = 0; i < fontnames.length; i++){
        fontnames[i] = systemFonts[i].getName();
      }
      String addto[] = new String[]{};
      for(int p = 0; p < sharkStartFrame.importedfonts.length; p++){
        loop1:for(int i = 0; i < fontnames.length; i++){
          if(fontnames[i].equalsIgnoreCase(sharkStartFrame.importedfonts[p].getName())){
            continue loop1;
          }
        }
        addto = u.addString(addto,sharkStartFrame.importedfonts[p].getName());
      }
      for(int i = 0; i < addto.length; i++){
        fontnames = u.addStringSort(fontnames, addto[i]);
      }
      String recommendedx[] = recommended;
      loop:for(int k = recommended.length-1; k >= 0; k--){
        for(int i = 0; i < fontnames.length; i++){
          if(recommended[k].equalsIgnoreCase(fontnames[i])){
            continue loop;
          }
        }
        recommended = u.removeString(recommended, k);
      }
      for(int i = 0; i < recommended.length; i++){
        fontnames = u.addStringSort(fontnames, recommended[i]);
      }
      for(int i = recommendedx.length-1; i >= 0; i--){
        if (recommendedx[i].equalsIgnoreCase(prevFont.getName())
            ||
           (defaultFnt!=null&&recommendedx[i].equalsIgnoreCase(defaultFnt.getName()))) {
          recommendedx = u.removeString(recommendedx, i);
        }
      }
      if(defaultFnt!=null)
        recommendedx = u.addString(recommendedx, defaultFnt.getName(), 0);
      if(defaultFnt==null||!defaultFnt.getName().equalsIgnoreCase(prevFont.getName()))
        // can't be prevFont.getFontName - sometimes gives wrong font.
       recommendedx = u.addString(recommendedx, prevFont.getName(), 0);
     if(recommendedx.length>0)
       recommendedx= u.addString(recommendedx, "SEPARATOR", recommendedx.length);
//startPR2008-01-25^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     if(defaultFnt!=null){
       for (int i = 0; i < recommendedx.length; i++) {
         if (recommendedx[i].equalsIgnoreCase(defaultFnt.getName())) {
           recommendedx[i] = recommendedx[i] + strdefault;
         }
       }
     }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     fontnames = u.addString(recommendedx,fontnames);
     fontList = new JList(fontnames);               //CREATE A LIST OF FONTNAMES
     fontList.setCellRenderer(new JListRenderer());
     fontList.setValueIsAdjusting(true);                  //SINGLE EVENT SELECTION
     fontList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);//CHOOSE 1 FONT
//startPR2008-01-15^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2008-01-25^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//     fontList.setSelectedValue(prevFont.getName().equalsIgnoreCase(defaultFnt.getName())?prevFont.getName()+strdefault:prevFont.getName(),true);
     fontList.setSelectedValue(defaultFnt!=null&&prevFont.getName().equalsIgnoreCase(defaultFnt.getName())?prevFont.getName()+strdefault:prevFont.getName(),true);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//     if(setDefault!=null)
//       setDefault.setSelected(prevFont.getName().equalsIgnoreCase(defaultFont.getName()));
//     if(setDefault!=null){
//       if(changePoints) setDefault.setSelected(prevFont.equals(defaultFont));
//       else setDefault.setSelected(prevFont.getName().equalsIgnoreCase(defaultFont.getName()));
//     }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     fontList.addListSelectionListener(new ListSelectionListener(){
           public void valueChanged (ListSelectionEvent e){

             updateFont();
           }
         });
     choosefont = new JScrollPane(fontList); //SCROLL LIST TO DISPLAY FONTS
//startPR2008-01-15^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    choosefont.setHorizontalScrollBarPolicy(choosefont.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    choosefont.setVerticalScrollBarPolicy(choosefont.VERTICAL_SCROLLBAR_AS_NEEDED);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     choosefont.setMinimumSize(new Dimension(400,200));
     choosefont.setWheelScrollingEnabled(true);

      //SET UP A PANEL FOR BUTTONS
      buttonPanel = new JPanel();

      //SET UP LAYOUT AND CONSTRAINTS FOR THE BUTTON PANEL
      gbLayout = new GridBagLayout();
  //    buttonPanel.setLayout(gbLayout);
      bGrid = new GridBagConstraints();
      bGrid.weightx = bGrid.weighty = 2;
      bGrid.fill = GridBagConstraints.BOTH;

      //ADD BUTTONS
      ok = u.Button("font_OK");
      ok.addActionListener(new java.awt.event.ActionListener(){
            public void actionPerformed(ActionEvent e){
   //           update();      //CALLS THE SUBCLASS'S OWN VERSION OF UPDATE
               doUpdate();
              fontFrame.dispose();
            }
          });
//startPR2008-01-15^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//          btadmindefault = u.Button("font_admindefault");
//          btadmindefault.addActionListener(new java.awt.event.ActionListener(){
//            public void actionPerformed(ActionEvent e){
 //             settoadmindefault = true;
//              doUpdate();
 //             fontFrame.dispose();
 //           }
 //         });
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      cancel = u.Button("font_cancel");
      cancel.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
              fontFrame.dispose();
            }
       });
       if(shark.macOS){
         buttonPanel.add(cancel);
         buttonPanel.add(ok);
       }
       else{
         buttonPanel.add(ok);
         buttonPanel.add(cancel);
       }
//startPR2008-01-15^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
 //       if(admindefault)
 //         buttonPanel.add(btadmindefault);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       // enables exiting screen via the ESC key
       fontList.addKeyListener(new KeyAdapter() {
         public void keyPressed(KeyEvent e) {
           int code = e.getKeyCode();
           if(code == KeyEvent.VK_ESCAPE)
             fontFrame.dispose();
         }
       });
       fontDisplay.addKeyListener(new KeyAdapter() {
         public void keyPressed(KeyEvent e) {
           int code = e.getKeyCode();
           if(code == KeyEvent.VK_ESCAPE)
             fontFrame.dispose();
         }
       });
      //ADD ALL COMPONENTS TO THE FRAME

      JPanel fdisplay =  new JPanel(new GridBagLayout());
      GridBagConstraints gb = new GridBagConstraints();
      gb.gridx = 0;
      gb.gridy = -1;
      gb.weightx = gb.weighty = 1;
      fdisplay.add(choosefont, gb);
//      if(defaultFont!=null){
//        gb.insets = new Insets(5,0,0,0);
//        fdisplay.add(setDefault, gb);
//        gb.insets = new Insets(0,0,0,0);
//      }
      JPanel fsample =  new JPanel(new GridBagLayout());
      gb.gridx = 0;
      gb.gridy = -1;

      fsample.add(displayLabel, gb);
      gb.insets = new Insets(5,0,0,0);
      fsample.add(fontDisplay, gb);
      gb.insets = new Insets(0,0,0,0);
//startPR2012-06-16^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      add(fdisplay,grid);
      mainpn = new JPanel(new GridBagLayout());
      mainpn.add(fdisplay,grid);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2008-01-15^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      if(defaultFont!=null)
//        add(setDefault, grid);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2012-06-16^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      add(fsample,grid);
      mainpn.add(fsample,grid);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      if (changePoints == true){//ADD POINT CHANGING FACILITIES WHEN THEY ARE REQUIRED
       addPointChange();
      }
//startPR2012-06-16^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      add(buttonPanel,grid);
      mainpn.add(buttonPanel,grid);
      add(new JScrollPane(mainpn), grid);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      setVisible(true);
      validate();
    }

    public void updateFont(){     //CALLED WHEN THE FONT IS CHANGED
      if(fontList.getSelectedIndex()<0)return;
      String fontName = (String)fontList.getSelectedValue();
      settoadmindefault = fontName.endsWith(strdefault);
//startPR2008-01-15^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      if(settoadmindefault){
        fontName = fontName.substring(0, fontName.length()-strdefault.length());
      }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      // default is bold if not for wordlists and games
      Font tf = u.fontFromString(fontName, isWordlistsAndGames?0:1, fontSize);
      if(chooseSize!=null){
//startPR2007-12-06^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//        int minsize = Math.min(u.getminimumfontsize(u.fontFromString(fontName, 1, pointSizeMin)),pointSizeMenuMax);
        chooseSize.setModel(new SpinnerNumberModel( Math.max(fontSize,pointSizeMin),
            pointSizeMin, pointSizeMax,pointSizeStep));
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      }
      boolean fontcantdobold = false;
      // test for whether font is one that can't display bold
      String s1 = defaultTreeFont.getName();
      if(fontcantdobold=(tf.getFontName().equalsIgnoreCase(s1) &&
         !tf.getName().equalsIgnoreCase(s1)))
        tf = u.fontFromString(fontName, 0, fontSize);
      if(currPanel.equals(infantPanel))
        chosenInfantFont = tf;
      else
        chosenfont = tf;
//startPR2008-01-15^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      if(setDefault!=null)
//        setDefault.setSelected(fontName.equalsIgnoreCase(defaultFnt.getName()));
//      if(setDefault!=null){
//        if(changePoints) setDefault.setSelected(tf.equals(defaultFont));
//        else setDefault.setSelected(fontName.equalsIgnoreCase(defaultFont.getName()));
//      }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      fontDisplay.setFont(tf.deriveFont(fontcantdobold?0:fontStyle, (float)tf.getSize()));
      fontDisplay.repaint();
    }

    private void addPointChange(){         //CALLED WHEN POINT CHANGE IS ALLOWED
      //SET UP PANEL FOR DISPLAYING FONT SIZE CHOICES
      sizePane = new JPanel();
      label = new JLabel(u.gettext("font_pointSize","label"));
      sizePane.add(label);
      chooseSize = new JSpinner(new SpinnerNumberModel(fontSize,
          pointSizeMin, pointSizeMax, pointSizeStep));
      chooseSize.addChangeListener(new ChangeListener() {
        public void stateChanged(ChangeEvent e){
          fontSize = ((Number)(((JSpinner)e.getSource()).getValue())).intValue();
          updateFont();
        }
      });
      sizePane.add(chooseSize);
//startPR2012-06-16^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      add(sizePane, grid);
      mainpn.add(sizePane, grid);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    }
  }
}


