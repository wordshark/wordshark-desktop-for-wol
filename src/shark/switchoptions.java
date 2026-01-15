package shark;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

/**
 * <p>Title: WordShark</p>
 * <p>Description: Creates the frame, from which choices about switch access
 *  are made.</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: WhiteSpace</p>
 * @author Sara Stewart
 * @version 1.0
 */
public class switchoptions
//startPR2004-08-27^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    extends JDialog
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    implements ChangeListener
{
//startPR2004-08-27^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  //was JFrame
  private JDialog switchFrame = new JDialog(sharkStartFrame.mainFrame);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  /**
   *  Width of frame
   */
  private int w;
  /**
   * Height of frame
   */
  private int h;
  private String mswitchoptions;
  private GridLayout newLayout;
  private JRadioButton none;
  private JRadioButton oneSwitch;
  private JRadioButton twoSwitches;
  private JPanel switchPanel;
  private ButtonGroup RButtonGrp;
  private JSlider timeSlider;
  private JLabel timeLabel;
  /**
   * Current response time
   */
  private int currResponse;
  /**
   * Displays "Set a Response Time"
   */
  private JLabel responseLabel;
  /**
   * Panel layout
   */
  private GridBagLayout gbLayout;
  /**
   * Button Panel constraints
   */
  private GridBagConstraints bGrid;
  /**
   * Holds slider and label for setting response times
   */
  private JPanel response;
  /**
   * Response panel layout
   */
  private GridBagLayout layout2;
  /**
   * Constraints for response panel
   */
  private GridBagConstraints RGrid;
  /**
   * Panel for the OK and Cancel buttons
   */
  private JPanel buttonPanel;
  /**
   *
   */
  private JButton ok;
  /**
   *
   */
  private JButton cancel;
  /**
   * Text displaying chosen response time.
   */
  private String chosenTime =u.gettext("switch_label2","label");
  /**
   * Constructs the frame for choosing how to use switches
   * @param options Indicates whether a student needs 0,1 or 2 switches
   * @param responsetime Response time needed by student.
   */
   public switchoptions(short options, short responsetime) {
//startPR2004-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     super(sharkStartFrame.mainFrame);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    if(responsetime < 0) responsetime = 15; // default if new
    currResponse = responsetime;//initialises the current response time.
    w = sharkStartFrame.mainFrame.getSize().width;
    h = sharkStartFrame.mainFrame.getSize().height;
    switchFrame.setBounds(w / 8, h / 8, w * 5 / 16, h * 5/8);
    switchFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    switchFrame.setTitle(u.gettext("mswitchoptions", "label"));
//startPR2004-08-27^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    switchFrame.setResizable(false);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR

    this.addWindowListener(new WindowAdapter() { //IF FRAME LOOSES FOCUS IT IS CLOSED
      public void windowDeactivated(WindowEvent e) {
        dispose();
      }
    });
    newLayout = new GridLayout(3, 1);
    switchFrame.getContentPane().setLayout(newLayout);

    //CREATE A LABEL "Set Response times"
    responseLabel = new JLabel(u.gettext("switch_label1", "label"));

   //Create a slider for response times
   timeSlider = new JSlider(JSlider.HORIZONTAL, 0, 30, responsetime);
   timeSlider.setMinorTickSpacing(2);
   timeSlider.setMajorTickSpacing(10);
   timeSlider.setPaintTicks(true);
   timeSlider.setPaintLabels(true);
   timeSlider.setValue((int)currResponse);
   timeSlider.addChangeListener(this);

   //Create a label to display response time.
   timeLabel = new JLabel();
   timeLabel.setText(u.edit(chosenTime,String.valueOf(currResponse)));

   //Create a panel for the slider and label
   layout2 = new GridBagLayout();
   response = new JPanel(layout2);
   response.setBorder(BorderFactory.createEtchedBorder());

    //Create Radio Buttons
    ItemListener optionListener = new ItemListener() {
      public void itemStateChanged(ItemEvent e) {
        if((oneSwitch.isSelected())){
          response.setVisible(true);
        }
        else{
          response.setVisible(false);
        }
      }
    };

    none = u.RadioButton("switch_0", optionListener);
    oneSwitch = u.RadioButton("switch_1", optionListener);
    twoSwitches = u.RadioButton("switch_2", optionListener);

    switch (options){  //BY DEFAULT SET THE SELECTED BUTTON TO THE ONE SET IN STUDENT OPTIONS
      case 0: none.setSelected(true);  break;
      case 1: oneSwitch.setSelected(true);  break;
      case 2: twoSwitches.setSelected(true); break;
    }

    //Group the buttons
    RButtonGrp = new ButtonGroup();
    RButtonGrp.add(none);
    RButtonGrp.add(oneSwitch);
    RButtonGrp.add(twoSwitches);

    //Create a panel to put the radio buttons on
    switchPanel = new JPanel();
    switchPanel.setBorder(BorderFactory.createEtchedBorder());

    //Set layout and constraints for the radio button panel
    gbLayout = new GridBagLayout();
    switchPanel.setLayout(gbLayout);
    bGrid = new GridBagConstraints();
    bGrid.gridx = 0;
    bGrid.gridy = -1;
    bGrid.fill = GridBagConstraints.HORIZONTAL;

    //Add the Radio buttons
    switchPanel.add(none, bGrid);
    switchPanel.add(oneSwitch, bGrid);
    switchPanel.add(twoSwitches, bGrid);

    bGrid.fill = GridBagConstraints.CENTER;
    bGrid.insets = new Insets (5,0,5,0);
    //Add label and slider to panel
    response.add(responseLabel, bGrid);
    response.add(timeSlider,bGrid);
    response.add(timeLabel,bGrid);

    //Set up a panel for the OK and Cancel buttons
    buttonPanel = new JPanel();
    GridBagLayout layout2 = new GridBagLayout();
    buttonPanel.setLayout(layout2);
    buttonPanel.setBorder(BorderFactory.createEtchedBorder());

    //Add buttons
    ok = u.Button("switch_ok");
    ok.addActionListener(new java.awt.event.ActionListener(){
      public void actionPerformed(ActionEvent e){
        if(oneSwitch.isSelected()){
          sharkStartFrame.switchOptions = 1;
          student.setOption("switchoptions",(short)1);//SETS THE OPTIONS FOR THE STUDENT
          sharkStartFrame.switchResponse = (short)timeSlider.getValue();
          student.setOption("switchresponse",(short)timeSlider.getValue());//SETS THE RESPONSE TIME FOR THE STUDENT
        }
        else if(twoSwitches.isSelected()){
          student.setOption("switchoptions",(short)2);//SETS THE OPTIONS FOR THE STUDENT
          sharkStartFrame.switchOptions = 2;
        }
        else{
          student.setOption("switchoptions",(short)0);//SETS THE OPTIONS FOR THE STUDENT
          sharkStartFrame.switchOptions = 0;
        }
        switchFrame.dispose();
    }});

    bGrid.gridx = -1;
    bGrid.gridy = 0;
    bGrid.fill = GridBagConstraints.BOTH;
    buttonPanel.add(ok,bGrid);

    cancel = u.Button("switch_cancel");
    cancel.addActionListener(new java.awt.event.ActionListener(){
     public void actionPerformed(ActionEvent e){
       switchFrame.dispose();
    }});
    buttonPanel.add(cancel,bGrid);

    //ADD ALL COMPONENTS TO THE FRAME
    switchFrame.getContentPane().add(switchPanel, newLayout);
    switchFrame.getContentPane().add(response, newLayout);
    switchFrame.getContentPane().add(buttonPanel, newLayout);

    switchFrame.validate();
    switchFrame.setVisible(true);
  }
  /**
   * Resets the label every time the slider is moved so it displays the correct time.
   * @param e Event
   */
  public void stateChanged(ChangeEvent e) {
        JSlider source = (JSlider)e.getSource();
        if (!source.getValueIsAdjusting()) {
            currResponse = (int)source.getValue();
            timeLabel.setText(u.edit(chosenTime,String.valueOf(currResponse)));
        }
    }

}

