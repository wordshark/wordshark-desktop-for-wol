/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shark;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.Timer;
import javax.swing.*;
import org.apache.commons.io.*;
import org.apache.commons.io.filefilter.*;
import java.util.*;
import java.nio.file.*;

/**
 *
 * @author paulr
 */
public class ImportShared_base extends JDialog{
    File f;
progress_base progbar;

    JDialog thisd = this;
        int screenWidth;
        int screenHeight;
        boolean copying = false;
        boolean overWriteLastOn;
        boolean deleteAllLastOn;

    public ImportShared_base(File importFile){
        f = importFile;
        this.getContentPane().setLayout(new GridBagLayout());
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        screenWidth = (int) env.getMaximumWindowBounds().getWidth();
        screenHeight = (int) env.getMaximumWindowBounds().getHeight();
        int w = screenWidth*8/16;
        int h = screenHeight*7/16;
        int border = w*1/20;
        this.setBounds(u2_base.adjustBounds(new Rectangle((screenWidth-w)/2,(screenHeight-h)/2,w,h)));
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setTitle(u.gettext("importshared", "title", shark.programName.toLowerCase()));
        this.setResizable(false);       
        this.setModal(true);
        JPanel pnMain = new JPanel(new GridBagLayout());
        GridBagConstraints grid = new GridBagConstraints();
        grid.weightx = 0;
        grid.weighty = 0;
        grid.gridx = 0;
        grid.gridy = -1;
        grid.anchor = GridBagConstraints.WEST;
        grid.fill = GridBagConstraints.NONE;
        
        JLabel lbTemplate = new JLabel();
        Font boldfont = lbTemplate.getFont();
        Font plainfont = boldfont.deriveFont((int)Font.PLAIN);
       Font smallplainfont = plainfont.deriveFont((float)plainfont.getSize()-3);
       
       JPanel pnOptions = new JPanel(new GridBagLayout());
      
       final JCheckBox cbChooseImport = new JCheckBox(u.gettext("importshared", "chooseimport"));
       final JCheckBox cbFreshStart = new JCheckBox(u.gettext("importshared", "freshstart"));
       final JCheckBox cbOverwrite = new JCheckBox(u.gettext("importshared", "overwrite"));
       
       final JButton btOK = new JButton(u.gettext("ok", "label"));
       
      cbOverwrite.setSelected(false);
      overWriteLastOn = true;
      cbChooseImport.setFont(boldfont); 
      cbFreshStart.setFont(plainfont); 
      cbOverwrite.setFont(plainfont); 
      
      
        cbChooseImport.addActionListener(new java.awt.event.ActionListener() {
          public void actionPerformed(ActionEvent e) {
              JCheckBox cb = (JCheckBox)e.getSource();
              cbFreshStart.setEnabled(cb.isSelected());
              cbOverwrite.setEnabled(cb.isSelected());
              btOK.setEnabled(cb.isSelected());
              boolean chooseImportOn = cbChooseImport.isSelected();
              if(chooseImportOn){
                  cbFreshStart.setSelected(deleteAllLastOn);
                  cbOverwrite.setSelected(overWriteLastOn);                     
              }
              else{
                  cbFreshStart.setSelected(false);
                  cbOverwrite.setSelected(false);                 
              }
              cbOverwrite.setEnabled(!cbFreshStart.isSelected() && chooseImportOn);
      

          }
        });    
    
       pnOptions.add(cbChooseImport, grid);
       grid.insets = new Insets(0, border, 0, 0);
       pnOptions.add(cbFreshStart, grid);
       pnOptions.add(cbOverwrite, grid);
       grid.insets = new Insets(0, 0, 0, 0);
       
       cbFreshStart.setEnabled(false);
       deleteAllLastOn = false;
       cbOverwrite.setEnabled(false);
        
        final JCheckBox cb = new JCheckBox(u.gettext("noaskagain", "label"));  
 
        cb.addActionListener(new java.awt.event.ActionListener() {
          public void actionPerformed(ActionEvent e) {
              if(cb.isSelected())btOK.setEnabled(true);
              else btOK.setEnabled(cbChooseImport.isSelected());
          }
        });        
        
        cbFreshStart.addActionListener(new java.awt.event.ActionListener() {
          public void actionPerformed(ActionEvent e) {
              cbOverwrite.setEnabled(!cbFreshStart.isSelected());
              if(cbFreshStart.isSelected()){
                  cbOverwrite.setSelected(false);
              }
              else{
                  cbOverwrite.setSelected(overWriteLastOn);
              }
              deleteAllLastOn = cbFreshStart.isSelected();
          }
        });
        
        
        
        cbOverwrite.addActionListener(new java.awt.event.ActionListener() {
          public void actionPerformed(ActionEvent e) {
              overWriteLastOn = cbOverwrite.isSelected();
          }
        });        
        
        
        
       JPanel pnButton = new JPanel(new GridBagLayout());
       
       JButton btCancel = new JButton(u.gettext("cancel", "label"));
       btOK.setEnabled(false);
       btOK.addActionListener(new java.awt.event.ActionListener() {
          public void actionPerformed(ActionEvent e) {              
               if(cbChooseImport.isSelected()){  
                boolean clear = cbFreshStart.isSelected();
                boolean overwrite = cbOverwrite.isSelected();
                String messstrWarnMess1 = u.gettext("importshared", "warn1");
                if(clear)messstrWarnMess1 += "||"+u.gettext("importshared", "warn2");
                else if(overwrite)messstrWarnMess1 += "||"+u.gettext("importshared", "warn3");
                // 
                if(u.yesnomess(shark.programName, messstrWarnMess1, thisd)){      
                   String tempFilesPath =  FileUtils.getTempDirectory().getAbsolutePath()
                        + shark.sep + "WS" + System.currentTimeMillis();
                   File tempDir = new File(tempFilesPath);
                   tempDir.mkdirs();
                   // unzip to temp folder
                   try{
                       u2_base.unZip(new java.util.zip.ZipFile(f), tempFilesPath);
                   }
                   catch(Exception ex){
                       cleanUpTemp(tempDir);
                       // fail message
                       u.okmess(shark.programName, u.gettext("importshared", "failed"), thisd);
                       return;
                   }
                   File tfile[] = tempDir.listFiles();
                   // dig in to find the shared folder - otherwise tempDir is the shared folder (even though not called as such)
                   boolean found = false;
                   try {
                        Collection files = FileUtils.listFilesAndDirs(tempDir, new NotFileFilter(TrueFileFilter.INSTANCE), DirectoryFileFilter.DIRECTORY);
                        for (Iterator iterator = files.iterator(); iterator.hasNext();) {
                            File file = (File) iterator.next();
                            if(file.getName().equalsIgnoreCase(shark.programName.toLowerCase()+"-shared")){
                                tempDir = file;
                                found = true;
                                break;
                            }
                        }
                   } catch (Exception ex) {}                   
                   if(found)
                       tfile = tempDir.listFiles();                   
                   // check whether too many users are being imported
                   db.alternativeShared = tempDir.getAbsolutePath();
                   ArrayList al = u.restrictedUserCount(tempDir, sharkStartFrame.sharedPath, clear, overwrite);
                   db.alternativeShared = null;
                   boolean exceedingUserLimit = false;
                   if(al !=null){
                       int adminNo = (int)al.get(0);
                       int stuNo = (int)al.get(1);
                       if(adminNo > shark.maxUsers_Admins || stuNo > shark.maxUsers_Students){
                           exceedingUserLimit = true;
                       }        
                   }
                   if(exceedingUserLimit){
                       cleanUpTemp(tempDir);
                       // fail message
                       u.okmess(shark.programName, u.gettext("importshared", "exceeded"), thisd);
                       return;                        
                   }
                   
                   // copy
                   copying = true;
                   Timer  importTimer = new javax.swing.Timer(100, new ActionListener() {
                              public void actionPerformed(ActionEvent e) {
                                    int pw = (screenWidth/2);
                                    int ph = (screenHeight/5);
                                    if(copying)
                                     progbar = new progress_base(thisd, shark.programName,
                                                            u.gettext("importing", "label"),
                                                            new Rectangle(((screenWidth-pw)/2),
                                                                          ((screenHeight-ph)/2),
                                                                          pw,ph));
                              }
                    });
                    importTimer.setRepeats(false);
                    importTimer.start();
                   

                    // delete everything from the shared folder except the activation xml
                   if(clear){
                       File f1[] = sharkStartFrame.sharedPath.listFiles();
                       for(int i = 0; i < f1.length; i++){
                           if(f1[i].isDirectory()){
                                try{
                                    FileUtils.deleteDirectory(f1[i]);
                                 }
                                 catch(Exception ex){}                                
                               continue;
                           }
                           if(f1[i].getName().equals(u2_base.saXmlFileName))continue;
                           f1[i].delete();
                       }
                   }                    
                   
                   // copy files from temp dir to shared folder
                   for(int i = 0; i < tfile.length; i++){
                       final File source = tfile[i];
                       File dest = new File(sharkStartFrame.sharedPathplus + source.getName());
                       if(source.isDirectory()){
                           copyDir(source, dest, overwrite);  
                       }
                       else{
                            if(source.getName().equals(u2_base.saXmlFileName))continue;
                            if(dest.exists() && !overwrite) continue;
                            Path from = source.toPath();
                            Path to = Paths.get(dest.getAbsolutePath());
                            try{
                                Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING);
                            }
                            catch(Exception ee){}                        
                       }
 
                   }    
                   cleanUpTemp(tempDir);
                   copying = false;
                   if(progbar!=null){
                       progbar.dispose();
                   }
                   
                   String sharedType = (String) db.find(sharkStartFrame.optionsdb, "lictype", db.TEXT);                   
                   String jarType = shark.singledownload?shark.LICENCETYPE_STANDALONEACTIVATION_HOME:shark.licenceType; 
                   if(!jarType.equals(sharedType)){
                      db.update(sharkStartFrame.optionsdb, "lictype", jarType, db.TEXT);
                   }                   
                   
                   
                   
                   offerRemoveZip(u.gettext("importshared", "complete", f.getAbsolutePath()), f);
                   String fn = getOptionsString(f);
                   if(db.query(sharkStartFrame.optionsdb,fn,db.TEXT )<0){
                       db.update(sharkStartFrame.optionsdb, fn, new String[]{""},db.TEXT);
                   }
                }
                else{
                    return;
                }
              }
               else if(cb.isSelected()){
                   String fn = getOptionsString(f);
                   if(db.query(sharkStartFrame.optionsdb,fn,db.TEXT )<0){
                       db.update(sharkStartFrame.optionsdb, fn, new String[]{""},db.TEXT);
                   }
                   else{
                       db.delete(sharkStartFrame.optionsdb, fn,db.TEXT);
                   }                 
               }
              thisd.dispose();
          }
          
          
        });
        btCancel.addActionListener(new java.awt.event.ActionListener() {
          public void actionPerformed(ActionEvent e) {
              thisd.dispose();
          }
        });

        grid.gridx = 0;
        grid.gridy = -1;        
        pnMain.add(pnOptions, grid);
        grid.insets = new Insets(border/2, 0, 0, 0);
        JLabel lbFound = new JLabel(u.gettext("importshared", "lblocation"));
        lbFound.setFont(smallplainfont);
        pnMain.add(lbFound, grid);
        JLabel lbPath = new JLabel(importFile.getAbsolutePath());
        lbPath.setFont(smallplainfont);
        grid.insets = new Insets(border/6, 0, 0, 0);
        
        pnMain.add(lbPath, grid);
        
        
        
             
        cb.setFont(smallplainfont);
        grid.insets = new Insets(border*4/3, 0, 0, 0);
        pnMain.add(cb, grid);
        grid.insets = new Insets(0, 0, 0, 0);
        
        grid.fill = GridBagConstraints.HORIZONTAL;

        
        grid.anchor = GridBagConstraints.CENTER;
        grid.gridx = -1;
        grid.gridy = 0;
        grid.fill = GridBagConstraints.NONE;
        grid.fill = GridBagConstraints.HORIZONTAL;
  //      pnButton.add(btOK, grid);
        pnButton.add(!shark.macOS?btOK:btCancel, grid);
        grid.insets = new Insets(0, border, 0, 0);
        pnButton.add(shark.macOS?btOK:btCancel, grid);    
        grid.fill = GridBagConstraints.BOTH;
        
        grid.insets = new Insets(border, border, 0, border);
        grid.gridx = 0;
        grid.gridy = -1;     
        grid.weighty = 1;
        this.getContentPane().add(pnMain, grid);
        grid.weighty = 0;
        grid.insets = new Insets(0, border, border, border);
        this.getContentPane().add(pnButton, grid); 
        setVisible(true);
    }
    
    void copyDir(File source, File dest, boolean overwrite){
        dest.mkdir();
        File ff[] = source.listFiles();
        for(int i = 0; i < ff.length; i++){
            File fdest=new File(dest.getAbsoluteFile()+ shark.sep+ source.getName());
            if(ff[i].isDirectory()){
                if(fdest.isDirectory() && !overwrite) continue;
                try{
                    FileUtils.copyDirectory(source, dest);
                }
                catch(Exception ex){}                
            }
            else{
                if(!fdest.isDirectory() && !overwrite) continue;
                try{
                    FileUtils.copyFileToDirectory(ff[i],dest,true);
                }
                catch(Exception ex){}   
            }
        }
    }
    
    void deleteDirContents(){
        try{
            FileUtils.deleteDirectory(f);
        }
        catch(Exception ex){}           
    }
    
    
    void offerRemoveZip(String mess, File f){
        if(u.yesnomess(shark.programName, mess, thisd)){
              f.delete();
        }       
    }    
    
    void cleanUpTemp(File f){
        try{
            FileUtils.deleteDirectory(f);
        }
        catch(Exception ex){}        
    }
    
    String getOptionsString(File file){
        return "imf_"+file.getName();
    }
    

}
