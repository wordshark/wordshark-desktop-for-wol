package shark;

import java.io.*;
import java.awt.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.awt.datatransfer.*;
import java.awt.dnd.*;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

//startPR2010-04-23^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  // update entire file
  // publictext - choosesharedpath - change all
//endPRtoupdate^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR

public class chooseshared_base
    extends JDialog {
  String publicpath;
  String folderName;
  String strTitle;
  String strReset;
  String strSet;
  String noAccess;
  String or;
  String messageCurrDir;
  String messageSet;
  String messageUnavailable;
  String notSet;
  String folderWarning;
  String setLoc;
  String resetLoc;
  String cannotwrite;
  String notcreatefolder;
  public static final String SERVERPATH = "serverpath";
  
  int counter = 0;
  JPanel topPanel = new JPanel();
  JPanel anPanel = new JPanel();
  JPanel middlePanel = new JPanel();
  JPanel bottomPanel = new JPanel();
  JLabel labInstuctions = new JLabel();
  JLabel labWarning= new JLabel();
  JLabel labOne = new JLabel();
  JLabel serverBoxTitle = new JLabel();
  JLabel labDirShow = new JLabel();
  JLabel messageTitle = new JLabel();
  JButton ok = new JButton();
  JButton butBrowse = new JButton();
  JButton cancel = new JButton();
  Dialog thisdia;
  Color col;
  boolean highlightBox = false;
  Toolkit t = Toolkit.getDefaultToolkit();
  Color highCol = UIManager.getColor("Table.focusCellForeground");
  File fl;
  Image inactiveFolder;
  ImageIcon inactiveFolderIcon;
  Image activeFolder;
  ImageIcon activeFolderIcon;
  dragLabel dropBox;
  String strok;
  String create;
  int thisWidth = 570;
  int thisHeight;
  public static boolean changed = false;
  boolean wantcancel = false;
  String oldpath;
  boolean createshared;

  public chooseshared_base(boolean showcancel) {
    if(shark.macOS)
      publicpath= System.getProperty("user.dir") + File.separator + shark.programName.toLowerCase() +"-public"+File.separator;
    else return;
    if(u2_base.gettext(publicpath, "choosesharedpath", "test")==null){
      shark.okmess(shark.programName, "Error reading from '"+shark.programName.toLowerCase()+"-public' folder. Please check permissions.");
      System.exit(0);
    }
    folderName = "'"+shark.programName.toLowerCase()+"-shared'";
    strTitle = u2_base.gettext(publicpath, "choosesharedpath", "choose", folderName);
    strReset = u2_base.gettext(publicpath, "choosesharedpath", "resetserver", folderName);
    strSet = u2_base.gettext(publicpath, "choosesharedpath", "setserver", folderName);
    noAccess = u2_base.gettext(publicpath, "choosesharedpath", "noaccess");
    or = u2_base.gettext(publicpath, "choosesharedpath", "or");
    messageCurrDir = u2_base.gettext(publicpath, "choosesharedpath", "currloc");
    messageSet = u2_base.gettext(publicpath, "choosesharedpath", "dirset", folderName);
    messageUnavailable = u2_base.gettext(publicpath, "choosesharedpath", "unavailable");
    notSet = u2_base.gettext(publicpath, "choosesharedpath", "notset");
    folderWarning = u2_base.gettext(publicpath, "choosesharedpath", "named", folderName);
    setLoc = u2_base.gettext(publicpath, "choosesharedpath", "setonserver", folderName);
    resetLoc = u2_base.gettext(publicpath, "choosesharedpath", "resetonserver", folderName);
    cannotwrite = u2_base.gettext(publicpath, "choosesharedpath", "cannotwrite", shark.programName.toLowerCase());
    notcreatefolder = u2_base.gettext(publicpath, "choosesharedpath", "notcreatefolder", shark.programName.toLowerCase());
    strok = u2_base.gettext(publicpath, "OK", "label");
    create = u2_base.gettext(publicpath, "choosesharedpath", "create", shark.programName.toLowerCase());
    if(showcancel)
      wantcancel = true;
    thisHeight = 380;
    try {
      oldpath = u2_base.getXMLAttribute(shark.xmlNetworkFile, SERVERPATH);
      if(oldpath==null)oldpath= "";
      activeFolder = t.getImage(publicpath+"sprites"+File.separator+"folder.gif");
      activeFolderIcon = new ImageIcon(activeFolder);
      inactiveFolder = t.getImage(publicpath+"sprites"+File.separator+"folder_inactive.gif");
      inactiveFolderIcon = new ImageIcon(inactiveFolder);
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    thisdia = this;
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    this.setBounds(40, 40, thisWidth, thisHeight);
    this.setResizable(false);
    this.setLocation(this.getX(), (this.getY()));
    this.setModal(true);
    dropBox.setIcon(inactiveFolderIcon);
    changed = false;
    ok.setEnabled(false);
  }

  private void jbInit() throws Exception {
    this.addWindowListener(new java.awt.event.WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        u2_base.setXMLAttribute(shark.xmlNetworkFile, SERVERPATH, oldpath);
        if(wantcancel)
          exit_actionPerformed();
        else
          System.exit(0);
      }
    });
    ok.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        int i = exit_actionPerformed();
        if(i==-1){
            messageTitle.setText("   ");
            labDirShow.setForeground(Color.red);
            labDirShow.setText(notcreatefolder);
        }
        else if(i==-2){
            messageTitle.setText("   ");
            labDirShow.setForeground(Color.red);
            labDirShow.setText(cannotwrite);
        }
      }
    });

    cancel.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if(u2_base.setXMLAttribute(shark.xmlNetworkFile, SERVERPATH, oldpath))
          exit_actionPerformed();
      }
    });

    butBrowse.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        JFileChooser fc;
        if(shark.macOS)
          fc = new JFileChooser("/Volumes/");
        else if (shark.linuxOS)
          fc = new JFileChooser("/");
        else
          fc = new JFileChooser();
        fc.setAcceptAllFileFilterUsed(false);
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fc.setFileFilter(new FileFilter() {
          public boolean accept(File f) {
            if (!(f.isDirectory())) {
              return false;
            }
            return true;
          }

          public String getDescription() {
            return u2_base.gettext(publicpath, "alldirectories", "label");
          }
        });
        int returnVal = fc.showOpenDialog(thisdia);
        if (returnVal == fc.APPROVE_OPTION) {
          File selectedFile = fc.getSelectedFile();
            String path2 = String.valueOf(selectedFile.getAbsolutePath());
            setDirLabel(path2);
            String s = path2+File.separator+shark.programName.toLowerCase()+"-shared";
            boolean isok = true;
            if(!new File(s).exists()){
              ok.setText(create);
              createshared = true;
            }
            else {
              ok.setText(strok);
              createshared = false;
              if(!u2_base.setXMLAttribute(shark.xmlNetworkFile, SERVERPATH, s)){
                messageTitle.setText("   ");
                labDirShow.setForeground(Color.red);
                labDirShow.setText(cannotwrite);   
                isok = false;
              }
            }
            if(isok){
                ok.setEnabled(true);
                ok.requestFocus();
                messageTitle.setText(u2_base.gettext(publicpath, "choosesharedpath", "parentdir", shark.programName.toLowerCase()));
            }
            dropBox.setIcon(inactiveFolderIcon);
        }
      }
    });
    JPanel mPanel = new JPanel();
    anPanel.setBorder(BorderFactory.createEtchedBorder());
    bottomPanel.setBorder(BorderFactory.createEtchedBorder());
    GridBagConstraints grid = new GridBagConstraints();
    this.setTitle(strTitle);
    this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    labDirShow.setHorizontalAlignment(SwingConstants.LEFT);
    labInstuctions.setFont(new java.awt.Font("SansSerif", 0, 12));
    labInstuctions.setHorizontalAlignment(SwingConstants.CENTER);
    labWarning.setFont(new java.awt.Font("SansSerif", 0, 12));
    labInstuctions.setText(strSet);
    labOne.setFont(new java.awt.Font("SansSerif", 0, 12));
    labOne.setHorizontalAlignment(SwingConstants.CENTER);
    labOne.setText(or);
    labWarning.setText(noAccess);
    labWarning.setHorizontalAlignment(SwingConstants.CENTER);
    serverBoxTitle.setFont(new java.awt.Font("SansSerif", 0, 12));
    messageTitle.setFont(new java.awt.Font("SansSerif", 0, 12));
    JLabel createnew = new JLabel(u2_base.gettext("choosesharedpath", "newdir", shark.programName.toLowerCase()));
    JLabel existing = new JLabel(u2_base.gettext("choosesharedpath", "existdir", shark.programName.toLowerCase()));
    createnew.setFont(new java.awt.Font("SansSerif", 0, 12));
    existing.setFont(new java.awt.Font("SansSerif", 0, 12));
    serverBoxTitle.setText(setLoc);
    messageTitle.setText(messageCurrDir);
    labDirShow.setFont(new java.awt.Font("SansSerif", 0, 12));
    butBrowse.setText(u2_base.gettext(publicpath, "browse", "label"));
    ok.setText(strok);
    cancel.setText(u2_base.gettext(publicpath, "cancel", "label"));
    dropBox = new dragLabel();

    GridBagLayout layout1 = new GridBagLayout();
    GridBagLayout layout2 = new GridBagLayout();
    GridBagLayout layout3 = new GridBagLayout();
    GridBagLayout layout4 = new GridBagLayout();
    GridBagLayout layout5 = new GridBagLayout();
    GridBagLayout layout6 = new GridBagLayout();
    getContentPane().setLayout(layout2);
    mPanel.setLayout(layout3);

    Dimension dim = new Dimension(70, 70);
    dropBox.setPreferredSize(dim);
    dropBox.setMaximumSize(dim);
    dropBox.setMinimumSize(dim);
    dropBox.setHorizontalAlignment(SwingConstants.CENTER);
    dropBox.setVerticalAlignment(SwingConstants.CENTER);
    grid.weighty = 1;
    grid.weightx = 1;
    topPanel.setLayout(layout1);
    middlePanel.setLayout(layout5);
    bottomPanel.setLayout(layout4);
    anPanel.setLayout(layout6);
    grid.anchor = GridBagConstraints.WEST;
    grid.gridx = 0;
    grid.gridy = 0;
    grid.fill = GridBagConstraints.NONE;
    grid.insets = new Insets(10, 10, 0, 10);
    topPanel.add(messageTitle, grid);
    grid.gridy = 1;
    grid.insets = new Insets(0, 10, 10, 10);
    topPanel.add(labDirShow, grid);
    grid.gridy = 0;
    grid.gridx = -1;
    JPanel jp1 = new JPanel(new GridBagLayout());
    jp1.add(createnew, grid);
    jp1.add(butBrowse, grid);
    JPanel jp2 = new JPanel(new GridBagLayout());
    jp2.add(existing, grid);
    jp2.add(dropBox, grid);
    grid.gridy = -1;
    grid.gridx = 0;
    middlePanel.add(labInstuctions, grid);
    grid.anchor = GridBagConstraints.CENTER;
    middlePanel.add(jp1, grid);
    middlePanel.add(labOne, grid);
    middlePanel.add(jp2, grid);
    grid.anchor = GridBagConstraints.WEST;
    grid.insets = new Insets(0, 0, 0, 0);
    grid.gridy = -1;
    grid.gridx = 0;
    anPanel.add(topPanel, grid);
    anPanel.add(middlePanel, grid);
    grid.gridx = 0;
    grid.gridy = 1;
    grid.insets = new Insets(10, 10, 10, 0);
    grid.gridx = 1;
    bottomPanel.add(labWarning, grid);
    grid.gridx = 0;
    grid.gridy = -1;
    grid.insets = new Insets(0, 10, 0, 0);
    mPanel.add(serverBoxTitle, grid);
    grid.insets = new Insets(0, 0, 0, 0);
    mPanel.add(anPanel, grid);
    grid.fill = GridBagConstraints.NONE;
    grid.insets = new Insets(20, 0, 0, 0);
    JPanel buttonpanel = new JPanel(new GridBagLayout());
    GridBagConstraints g = new GridBagConstraints();
    g.fill = GridBagConstraints.NONE;
    g.gridx = -1;
    g.gridy = 0;
    g.weighty = 1;
    g.weightx = 1;
    if(shark.macOS){
      if(wantcancel){
        buttonpanel.add(cancel, g);
      }
      buttonpanel.add(ok,g);
    }
    else{
      buttonpanel.add(ok,g);
      if(wantcancel){
        buttonpanel.add(cancel, g);
      }
    }
    grid.fill = GridBagConstraints.HORIZONTAL;
    mPanel.add(buttonpanel, grid);
    grid.insets = new Insets(0, 0, 0, 0);
    grid.gridx = 0;
    grid.gridy = 0;
    grid.fill = GridBagConstraints.BOTH;
    getContentPane().add(mPanel);
    setDirLabel(oldpath);
    this.requestFocus();
  }


  int exit_actionPerformed() {
    if(createshared){
        String ss = labDirShow.getText();
        File f = new File(ss);
        if(f.exists()){
            f = new File((ss=f.getAbsoluteFile()+File.separator+shark.programName.toLowerCase()+"-shared"));
            if(!f.exists()){
                if(!f.mkdir())return -1;   
                u2_base.setNewFilePermissions(f);
            }
            if(!u2_base.setXMLAttribute(shark.xmlNetworkFile, SERVERPATH, ss))
                return -2;
        }
        else return -1;
    }
    if(!oldpath.equals(u2_base.getXMLAttribute(shark.xmlNetworkFile, SERVERPATH))){
      changed = true;
    }
    dispose();
    return 0;
  }

  private class dragLabel
      extends JLabel
      implements DropTargetListener, Serializable {
    DropTarget dropT;
    DataFlavor[] flavors = {
        DataFlavor.javaFileListFlavor};

    dragLabel() {
      super();
      dropT = new DropTarget(this, this);
      setBorder(BorderFactory.createLoweredBevelBorder());
      col = getBackground();
    }

    public void paint(Graphics g) {
      super.paint(g);
      if (highlightBox) {
        this.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED,
            highCol, highCol.darker()));
        this.setBackground(col.darker());
      }
      else {
        this.setBorder(BorderFactory.createLoweredBevelBorder());
        this.setBackground(col);
      }
    }

    public void dragEnter(DropTargetDragEvent dsde) {
      if (!this.hasFocus()) {
        this.requestFocusInWindow();
        this.requestFocus();
      }
      highlightBox = true;
      this.repaint();
    }

    public void dragExit(DropTargetEvent dse) {
      highlightBox = false;
      this.repaint();
    }

    public void dragOver(DropTargetDragEvent dsde) {}

    public void dropActionChanged(DropTargetDragEvent dsde) {}

    public void drop(DropTargetDropEvent dsde) {
      File ff = null;
      try {
        Transferable te = dsde.getTransferable();
        dsde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
        flavors = te.getTransferDataFlavors();
        for (int i = 0; i < flavors.length; i++) {
          DataFlavor dataFlavor = flavors[i];
          try {
            if (dataFlavor.equals(DataFlavor.javaFileListFlavor)) {
              java.util.List fileList = (java.util.List) te.getTransferData(
                dataFlavor);
              ff = (File) fileList.get(0);
            }
            else if (dataFlavor.equals(DataFlavor.stringFlavor)) {}
          }
          catch (Exception e) {}
        }
        if (ff.isDirectory() &&
            ff.getName().equals(shark.programName.toLowerCase() + "-shared")) {
          String path2 = String.valueOf(ff.getAbsolutePath());
          if(u2_base.setXMLAttribute(shark.xmlNetworkFile, SERVERPATH, path2)){
            ok.setText(strok);
            createshared = false;
            ok.setEnabled(true);
            thisdia.requestFocusInWindow();
            dsde.dropComplete(true);
            ok.requestFocus();
            highlightBox = false;
            setDirLabel(path2);
            labDirShow.repaint();
            this.setBackground(col);
            this.setIcon(activeFolderIcon);
            messageTitle.setText(messageSet);
            this.repaint();
          }
          else{
            messageTitle.setText("   ");
            labDirShow.setForeground(Color.red);
            labDirShow.setText(cannotwrite);
          }
        }
        else {
          labDirShow.setForeground(Color.red);
          labDirShow.setText(folderWarning);
          messageTitle.setText("   ");
          highlightBox = false;
          this.repaint();
        }
      }
      catch (Exception e) {
        e.printStackTrace();
      }
    }
  }



  private void setDirLabel(String str) {
      int i = thisWidth - 90;
      String newString = str;
      while (labDirShow.getFontMetrics(labDirShow.getFont()).stringWidth(
          newString) > i) {
        newString = newString.substring(0, (newString.length() - 5));
        newString = newString + "...";
      }
      if (newString.endsWith("...")) {
//        ToolTipManager.sharedInstance().setEnabled(true);
        labDirShow.setToolTipText(str);
        labDirShow.repaint();
        ok.requestFocus();
      }
//      else {
//        ToolTipManager.sharedInstance().setEnabled(false);
//      }
      if(newString.equals("null")){
        labDirShow.setText(notSet);
      }
      else
        labDirShow.setText(newString);
      labDirShow.setForeground(Color.black);
      labDirShow.repaint();
    }


  }
