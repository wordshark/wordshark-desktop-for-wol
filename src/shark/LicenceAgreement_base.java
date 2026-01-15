package shark;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.awt.print.*;

/**
 * <p>Title: Your Product Name</p>
 * <p>Description: Your description</p>
 * <p>Copyright: Copyright (c) 1997</p>
 * <p>Company: Your Company</p>
 * @author Your Name
 * @version 1.0
 */

public class LicenceAgreement_base extends IntroFrame_base {
  String[] args;
  GridBagConstraints grid = new GridBagConstraints();
  ButtonGroup bg = new ButtonGroup();
  JRadioButton r1;
  JRadioButton r2;
  IntroFrame_base thisjd;
  byte state;
  static final byte DECLINE = 0;
  static final byte ACCEPT = 1;
  JButton ok;
  int border = 10;
  JTextPane ta;
  FontMetrics fm;

  ItemListener bglisten = new java.awt.event.ItemListener() {
      public void itemStateChanged(ItemEvent e) {
        byte oldstate = state;
        if(r1.isSelected()) state= ACCEPT;
        else if(r2.isSelected()) state= DECLINE;
        else state=0;
        if(state != oldstate) {
           if(state==ACCEPT){
             ok.setEnabled(true);
           }
           if(state==DECLINE){
             ok.setEnabled(false);
           }
        }
      }
   };
   String lines[];
   String longestline;

  public LicenceAgreement_base() {
    super(true);
    grid.weighty = 1;
    grid.weightx = 1;
    grid.fill = GridBagConstraints.NONE;
    grid.gridx = 0;
    grid.gridy = -1;
    thisjd = this;
    JPanel textPanel = new JPanel(new GridBagLayout());
    ta = new JTextPane();
    grid.fill = GridBagConstraints.BOTH;
//startPR2009-10-21^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    String smess;
    if(shark.network)
        smess = "netmess";
    else if(shark.isLicenceActivated())
      smess = "activationmess";
    else if(shark.licenceType.equals(shark.LICENCETYPE_USB))
      smess = "usbmess";
    else {  
      smess = "standardmess";
    }
    String mess = u.gettext("licenceagreement", smess);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    ta.setEditable(false);
    ta.setBorder(BorderFactory.createEtchedBorder());
    fm = ta.getFontMetrics(ta.getFont());

    JScrollPane jsp = new JScrollPane(ta);
    jsp.setHorizontalScrollBarPolicy(jsp.HORIZONTAL_SCROLLBAR_NEVER);
    jsp.setVerticalScrollBarPolicy(jsp.VERTICAL_SCROLLBAR_AS_NEEDED);
    jsp.setPreferredSize(new Dimension(this.getWidth()/2,this.getHeight()/2));
    JButton print;
    print = new JButton(u.gettext("printtext", "label"));
    print.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        printDetails pd = new printDetails();
        pd.printD();
      }
    });

    grid.fill = GridBagConstraints.BOTH;
    textPanel.add(jsp, grid);
    grid.fill = GridBagConstraints.NONE;
    grid.anchor = GridBagConstraints.EAST;
    grid.insets = new Insets(border,0,0,0);
    textPanel.add(print, grid);
    grid.insets = new Insets(0,0,0,0);
    grid.anchor = GridBagConstraints.CENTER;

    grid.fill = GridBagConstraints.NONE;
    JPanel choicePanel = new JPanel(new GridBagLayout());
    r1 = new JRadioButton(u.gettext("licenceagreement", "choice1"));
    r2 = new JRadioButton(u.gettext("licenceagreement", "choice2"));
    r1.addItemListener(bglisten);
    r2.addItemListener(bglisten);
    bg.add(r1);
    bg.add(r2);
    r1.setOpaque(false);
    r2.setOpaque(false);
    r2.setSelected(true);
    grid.anchor = GridBagConstraints.WEST;
    choicePanel.add(r1, grid);
    choicePanel.add(r2, grid);
    grid.anchor = GridBagConstraints.CENTER;
    ok = new JButton(u.gettext("ok", "label"));
    ok.setEnabled(false);
    ok.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if(r1.isSelected()){
          closeonexit = false;
          thisjd.dispose();
        }
      }
    });

    JButton exit = new JButton(u.gettext("exit", "label"));
    exit.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        System.exit(0);
      }
    });

    JPanel buttonPanel = new JPanel(new GridBagLayout());
    grid.gridx = -1;
    grid.gridy = 0;
    grid.insets = new Insets(0,0,0,border*2);
    if(shark.macOS){
        buttonPanel.add(exit, grid);
        grid.insets = new Insets(0,0,0,0);
        buttonPanel.add(ok, grid);
    }
    else{
        buttonPanel.add(ok, grid);
        grid.insets = new Insets(0,0,0,0);
        buttonPanel.add(exit, grid);
    }
    grid.gridx = 0;
    grid.gridy = -1;
    JPanel contentPanel = new JPanel(new GridBagLayout());
    contentPanel.add(textPanel, grid);
    contentPanel.add(choicePanel, grid);
    contentPanel.add(buttonPanel, grid);
    grid.fill = GridBagConstraints.BOTH;
    addToBase(contentPanel, grid);
    setTitle(u.gettext("licenceagreement", "title"));
    setColor();
    this.setVisible(true);
    int w = (int)ta.getWidth();
    w = w*9/10;
    String sents[] = u.splitString(mess);
    lines = new String[]{};
    String strlines="";
    String s = "";
    int longest = 0;
    longestline = null;
    for(int n = 0; n < sents.length; n++){
      if(sents[n].trim().equals("")){
        lines = u.addString(lines, "");
        continue;
      }
      String words[] = sents[n].split(" ");
      for (int i = 0; i < words.length; i++) {
        String temps = s.concat(words[i] + " ");
        int p;
        if ( (p = fm.stringWidth(temps)) > w) {
          if (p > longest) {
            longest = p;
            longestline = s;
          }
          lines = u.addString(lines, s);
          s = temps.substring(s.length());
        }
        else s = s.concat(words[i] + " ");
      }
      lines = u.addString(lines, s);
      s="";
    }
    for(int i = 0; i < lines.length; i++){
      strlines += lines[i]+"\n";
    }
    ta.setText(strlines);
    ta.setCaretPosition(0);
  }



  public class printDetails implements Printable {
    int lastPage = -1;
    String printlines[];
    int currline = 0;
    int totlines;
    printDetails() {
      printlines = lines;
      totlines = printlines.length;
    }

    boolean printD() {
      PrinterJob printJob = PrinterJob.getPrinterJob();
      printJob.setJobName(u.gettext("licenceagreement", "printjobname"));
      PageFormat pageFormat = printJob.defaultPage();
      pageFormat.setOrientation(pageFormat.PORTRAIT);
      printJob.setPrintable(this, pageFormat);
      if (printJob.printDialog()) {
        try {
          printJob.print();
          return true;
        }
        catch (Throwable t) {}
      }
      return false;
    }

    public int print(Graphics g, PageFormat f, int a) throws PrinterException {
      // end the print if it has been completed
      if(currline >= totlines){
        lastPage = -1;
        return NO_SUCH_PAGE;
      }

      // allows skipping of the buffer's graphics context
      if (a > lastPage) {
        lastPage = a;
        return Printable.PAGE_EXISTS;
      }
      g.translate( (int) f.getImageableX(), (int) f.getImageableY());
      Dimension dd = new Dimension( (int) f.getImageableWidth(),
                                    (int) f.getImageableHeight());
      int w = dd.width;
      int h = dd.height;
      int xx = 0;
      int yy = 0;
      Font f1 = sizeFont(g, longestline, w);
      g.setFont(f1);
      FontMetrics m1 = g.getFontMetrics();
      for(int i = currline; i < printlines.length; i++){
        yy+=m1.getHeight();
        if(yy>=h){
          currline++;
          return Printable.PAGE_EXISTS;
        }
        currline = i;
        g.drawString(printlines[i], xx, yy);
        if(currline>=printlines.length-1)totlines=0;
      }
      return Printable.PAGE_EXISTS;
    }

    public Font sizeFont(Graphics g, String s, int w) {
      Font f = g.getFont().deriveFont( (float) 40);
      g.setFont(f);
      FontMetrics m = g.getFontMetrics();
      while (f.getSize() > 8 && m.stringWidth(s) > w) {
        f = f.deriveFont( (float) f.getSize() - 1);
        g.setFont(f);
        m = g.getFontMetrics();
      }
      return f;
    }
  }
}
