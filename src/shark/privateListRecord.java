package shark;

import java.util.*;
import java.io.*;

public class privateListRecord implements Serializable {
  // name of the database key that stores details of changes to private word lists
  public static final String LISTSDB = "privateLists";
  // name of the database key that stores details of changes administrator's names
  public static final String RENAMEDADMINDB = "renamedAdmin";
  // message to send details of changes to appropriate administrator
  private messageForAdmin message;


  /**
   * Stores details of changes to private word lists.
   *
   * Each entry exprires after a year
   */
  static class plist implements Serializable {
    static final long serialVersionUID = 573135789755653581L;
    public Calendar expiresDate;
    public String adminName;
    // the different names a private list has been called
    public Vector topics;
    public boolean deleted;
    public boolean removeImmediately;

    plist(String admin, String topicName, boolean del) {
      expiresDate = Calendar.getInstance();
      expiresDate.add(Calendar.YEAR, 1);
      adminName = admin;
      topics = new Vector();
      topics.add(topicName);
      deleted = del;
      removeImmediately = false;
    }
  }


  /**
   * Stores the name changes that administrators go through.
   *
   * Each entry expires after a year of creation.
   *
   */
  static class adminNameChanges implements Serializable {
    static final long serialVersionUID = 863836493711637448L;
    public Calendar expiresDate;
    public Vector names;

    adminNameChanges(String oldname, String newname) {
      expiresDate = Calendar.getInstance();
      expiresDate.add(Calendar.YEAR, 1);
      names = new Vector();
      names.add(oldname);
      names.add(newname);
    }
  }


  /**
   * When a private list is deleted - stores the name. Creates a new entry if the
   * list hasn't been renamed before. Otherwise adds to existing extry. Updates
   * database.
   *
   * @param adminname Name of the administrator for the deleted list.
   * @param currname Name of the of the deleted list.
   */
  public static void listDeleted(String adminname, String currname) {
    Vector v = new Vector();
    if (db.query(sharkStartFrame.plistRec, LISTSDB, db.VECTOR) < 0) {
      v.add(new plist(adminname, currname, true));
    }
    else {
      // get the administrator's Vector of topic records
      v = (Vector) db.find(sharkStartFrame.plistRec, LISTSDB, db.VECTOR);
      // scan through admin's topic records
      boolean found = false;
      for (int i = 0; i < v.size(); i++) {
        plist plr = (plist) v.get(i);
        String str = (String) plr.topics.elementAt(plr.topics.size() - 1);
        if (str!=null && currname!=null && str.equals(currname)) {
          found = true;
          plr.deleted = true;
          break;
        }
      }
      if (!found) {
        v.add(new plist(adminname, currname, true));
      }
    }
    db.update(sharkStartFrame.plistRec, LISTSDB, v, db.VECTOR);
  }

  /**
   * When a private list is renamed - stores the old and new name.
   *
   * @param adminname Name of the administrator for the renamed list.
   * @param oldname Name of the list before the change.
   * @param newname Name of the list after the change.
   */
  public static void listRenamed(String adminname, String oldname,
                                 String newname) {
    Vector v = new Vector();
    if (db.query(sharkStartFrame.plistRec, LISTSDB, db.VECTOR) < 0) {
      plist priv = new plist(adminname, oldname, false);
      priv.topics.add(newname);
      v.add(priv);
    }
    else {
      boolean found = false;
      // get the administrator's Vector of topic records
      v = (Vector) db.find(sharkStartFrame.plistRec, LISTSDB, db.VECTOR);
      if (v != null) {
        // scan through admin's topic records
        for (int i = 0; i < v.size(); i++) {
          plist plr = (plist) v.get(i);
          // latest name of the topic
          String str = (String) plr.topics.elementAt(plr.topics.size() - 1);
          if (str.equals(oldname)) {
            found = true;
            plr.topics.add(newname);
            break;
          }
        }
        if (!found) {
          plist priv = new plist(adminname, oldname, false);
          priv.topics.add(newname);
          v.add(priv);
        }
      }
    }
    db.update(sharkStartFrame.plistRec, LISTSDB, v, db.VECTOR);
  }

  /**
   * When an administrator or teacher is deleted - deletes the entry  (if it exists)
   * in the admin names database that has stored that the names changes of this
   * administrator/teacher. Also deletes the entries of this administrator/teacher's
   * private list changes.
   *
   * @param adminname Name of the administrator/teacher.
   */
  public static void adminDeleted(String adminname) {
    deleteNamesForAdmin(adminname);
    deleteListsForAdmin(adminname);
  }

  /**
   * When an administrator is renamed - stores the old and new name.
   *
   * Also renames the relevent administrator's name in the store of changes to
   * private lists.
   *
   * @param oldname The administrator's name before the change.
   * @param newname The administrator's name after the change.
   */
  public static void adminRenamed(String oldname, String newname) {
    // add to admin renamed Vector
    Vector v = new Vector();
    if (db.query(sharkStartFrame.plistRec, RENAMEDADMINDB, db.VECTOR) < 0) {
      adminNameChanges anc = new adminNameChanges(oldname, newname);
      v.add(anc);
    }
    else {
      boolean found = false;
      // get list of admin renames
      v = (Vector) db.find(sharkStartFrame.plistRec, RENAMEDADMINDB, db.VECTOR);
      if (v != null) {
        for (int i = 0; i < v.size(); i++) {
          adminNameChanges anc = (adminNameChanges) v.get(i);
          // latest name of the admin
          String str = (String) anc.names.elementAt(anc.names.size() - 1);
          if (str.equals(oldname)) {
            found = true;
            anc.names.add(newname);
            break;
          }
        }
        if (!found) {
          adminNameChanges anc = new adminNameChanges(oldname, newname);
          v.add(anc);
        }
      }
    }
    db.update(sharkStartFrame.plistRec, RENAMEDADMINDB, v, db.VECTOR);

    // rename the admin in the store of renamed/deleted wordlists
    renameListsForAdmin(oldname, newname);
  }

  /**
   * Returns all the names that this administrator/teacher has had.
   *
   * @param adminname Name of the administrator/teacher.
   * @return String[] A list of all the names that this administrator/teacher
   * has had.
   */
  private static String[] getAdminNames(String adminname) {
    // add to admin renamed Vector
    Vector v = new Vector();
    if ( (v = (Vector) db.find(sharkStartFrame.plistRec, RENAMEDADMINDB,
                               db.VECTOR)) != null) {
      for (int i = 0; i < v.size(); i++) {
        adminNameChanges anc = (adminNameChanges) v.get(i);
        for (int k = 0; k < anc.names.size(); k++) {
          String str = (String) anc.names.elementAt(k);
          if (str.equals(adminname)) {
            String sArr[] = new String[anc.names.size()];
            for (int j = 0; j < anc.names.size(); j++) {
              sArr[j] = (String) anc.names.elementAt(j);
            }
            // return all other names this admin has had
            return sArr;
          }
        }
      }
    }
    // or just return the admin name - they haven't had any other names
    return new String[] {adminname};
  }

  /**
   * Returns all the invalid topics in a saveprogram.
   *
   * @param sp The saveprogram to search for invalid topics.
   * @return Vector The names of all the invalid topics in this saveprogram.
   */
  public Vector getInvalidTopics(program.saveprogram sp) {
    boolean anyinvalid = false;
    int ig, it;
    Vector invalidTopicNames = new Vector();
    for (int i = 0; i < sp.it.length; i++) {
      for (int k = 0; k < sp.it[i].topics.length; k++) {
        String ss = sp.it[i].topics[k];
        it = ss.indexOf(topicTree.ISPATH);
        if ( (ig = ss.indexOf(topicTree.ISTOPIC)) >= 0) {
          topic t = new topic(ss.substring(ig + 1, it),
                              ss.substring(it + 1), null, null);
          if (t.invalid) {
            invalidTopicNames.add(topicTree.ISTOPIC +
                                  t.databaseName + topicTree.ISPATH +
                                  t.name);
            anyinvalid = true;
          }
        }
      }
    }
    if (anyinvalid) {
      return invalidTopicNames;
    }
    else {
      return null;
    }
  }

  /**
   * Searches and deletes any expired entries from the database entries that store
   * changes to private lists and to administrator names.
   */
  public static void check() {
    Calendar today = Calendar.getInstance();
    Vector v = (Vector) db.find(sharkStartFrame.plistRec,
                                LISTSDB, db.VECTOR);
    // delete expired entries from the store of changed wordlists
    if (v != null) {
      for (int i = v.size() - 1; i >= 0; i--) {
        plist plr = (plist) v.get(i);
        if (today.after(plr.expiresDate)) {
          v.removeElementAt(i);
        }
      }
      db.update(sharkStartFrame.plistRec, LISTSDB, v, db.VECTOR);
    }

    // delete expired entries from the store of admin name changes
    v = (Vector) db.find(sharkStartFrame.plistRec, RENAMEDADMINDB, db.VECTOR);
    if (v != null) {
      for (int i = v.size() - 1; i >= 0; i--) {
        adminNameChanges anc = (adminNameChanges) v.get(i);
        if (today.after(anc.expiresDate)) {
          v.removeElementAt(i);
        }
      }
      db.update(sharkStartFrame.plistRec, RENAMEDADMINDB, v, db.VECTOR);
    }
  }

  /**
   * Updates an administrator/teacher's name in the store of private list changes.
   *
   * @param oldname Name of the list before the change.
   * @param newname Name of the list after the change.
   */
  private static void renameListsForAdmin(String oldname, String newname) {
    Vector v = (Vector) db.find(sharkStartFrame.plistRec,
                                LISTSDB, db.VECTOR);
    if (v != null) {
      for (int i = v.size() - 1; i >= 0; i--) {
        plist plr = (plist) v.get(i);
        if (plr.adminName.equals(oldname)) {
          plr.adminName = newname;
        }
      }
      db.update(sharkStartFrame.plistRec, LISTSDB, v, db.VECTOR);
    }
  }

  /**
   * Deletes the entries of this administrator/teacher's private list changes.
   *
   * @param adminname Name of the administrator/teacher.
   */
  private static void deleteListsForAdmin(String adminname) {
    Vector v = (Vector) db.find(sharkStartFrame.plistRec,
                                LISTSDB, db.VECTOR);
    if (v != null) {
      for (int i = v.size() - 1; i >= 0; i--) {
        plist plr = (plist) v.get(i);
        if (plr.adminName.equals(adminname)) {
          v.removeElementAt(i);
        }
      }
      db.update(sharkStartFrame.plistRec, LISTSDB, v, db.VECTOR);
    }
  }

  /**
   * deletes the entry (if it exists) in the admin names database that has
   * stored that the names changes of this administrator/teacher.

   * @param adminname Name of the administrator/teacher.
   */
  private static void deleteNamesForAdmin(String adminname) {
    Vector v = (Vector) db.find(sharkStartFrame.plistRec,
                                RENAMEDADMINDB, db.VECTOR);
    if (v != null) {
      for (int i = v.size() - 1; i >= 0; i--) {
        adminNameChanges anc = (adminNameChanges) v.get(i);
        if ( ( (String) anc.names.elementAt(anc.names.size() - 1)).equals(
            adminname)) {
          v.removeElementAt(i);
        }
      }
      db.update(sharkStartFrame.plistRec, RENAMEDADMINDB, v, db.VECTOR);
    }
  }

  /**
   * Updates a saveprogram if it contains invalid topics. Updates the invalid topics
   * if any name changes of private lists or administrator's can be matched. If
   * the invalid topics can't be repaired they are deleted from the saveprogram.
   *
   * Notifies the administrator/teacher of any relevant changes.
   *
   *
   * @param currStudent A student the saveprogram is assigned to.
   * @param projectlist The projectlist of the saveprogram.
   * @param sp The saveprogram for which the invalid topics are updated.
   * @param invalidTopics The invalid topics.
   *
   * @return saveprogram The updated saveprogram.
   */
  public program.saveprogram updateSaveProgram(String currStudent,
                                     String projectlist,
                                     program.saveprogram sp,
                                     Vector invalidTopics) {
    message = new messageForAdmin();
    int spItemNo = sp.it.length;
    int noEmpty = 0;
    String admin = "";
    String topic;
    Vector v = new Vector();
    message.spName = projectlist;
    message.affectedStudents = sp.students;
    message.administrator = sp.teacher;
    // for each invalid topic - find admin and get their privateListRecord
    boolean anychange = false;
    boolean adminChangedName = false;
    loop1:for (int i = 0; i < invalidTopics.size(); i++) {
      boolean fixed = false;
      String ss = (String) invalidTopics.get(i);
      // invalid topic
      topic = ss.substring(ss.indexOf(topicTree.ISPATH) + 1);
      admin = ss.substring(ss.indexOf(topicTree.ISTOPIC) + 1,
                           ss.indexOf(topicTree.ISPATH));
      String[] allAdminNames = getAdminNames(admin);

      // check if just the admin name is wrong
       for (int k = 0; k < allAdminNames.length; k++) {
        topic t = new topic(allAdminNames[k], topic, null, null);
        if (!t.invalid) {
          for (int r = 0; r < sp.it.length; r++) { //was i
            for (int d = 0; d < sp.it[r].topics.length; d++) { //was k
              String sss = sp.it[r].topics[d];
              if ( (sss.substring(sss.length() - topic.length())).equals(topic)) {
                int ii;
                if ( (ii = sss.indexOf(topicTree.ISPATH)) >= 0) {
                  String preName = sss.substring(0, ii + 1);
                  preName = preName.substring(0,
                                              preName.lastIndexOf(topicTree.ISTOPIC) +
                                              1) + allAdminNames[k] +
                      topicTree.ISPATH;
                  String st = preName.substring(0,
                                                preName.lastIndexOf(topicTree.
                      CSEPARATOR));
                  preName = st.substring(0,
                                         st.lastIndexOf(topicTree.CSEPARATOR) + 1) +
                      allAdminNames[k] +
                      preName.substring(preName.lastIndexOf(topicTree.
                      CSEPARATOR));
                  sp.it[r].topics[d] = preName + topic;
                  for (int y = 0; y < sp.it[r].trees[d].names.length; y++) {
                    String rr = sp.it[r].trees[d].names[y];
                    if (rr.substring(rr.length() - topic.length()).equals(topic)) {
                      int kk;
                      if ( ( (ii = rr.indexOf(topicTree.ISPATH)) >= 0) &&
                          (kk = rr.indexOf(topicTree.ISTOPIC)) >= 0) {
                        sp.it[r].trees[d].names[y] = rr.substring(0, kk + 1) +
                            allAdminNames[k] + rr.substring(ii);
                      }
                    }
                  }
//startPR2007-12-10^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                  sp.teacher = allAdminNames[k];
                  for(int b = 0; b < sp.students.length; b++)
                    db.update(sp.students[b], projectlist, sp, db.PROGRAM);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                  continue loop1;
                }
              }
            }
          }
        }
      }
      // look for match in private list records
      if (db.query(sharkStartFrame.plistRec, LISTSDB, db.VECTOR) >=
          0) {
        fixed = false;
        //  vector for changes to admin's lists
        v = (Vector) db.find(sharkStartFrame.plistRec, LISTSDB,
                             db.VECTOR);
        // scan through each set of list changes of admin
        for (int k = 0; k < v.size(); k++) {
          plist plr = (plist) v.get(k);
          for (int z = allAdminNames.length - 1; z >= 0; z--) {
            if (plr.adminName.equals(allAdminNames[z])) {
              String correctTopicName = (String) plr.topics.elementAt(plr.
                  topics.
                  size() - 1);
              // scan through topic names for record
              for (int j = 0; j < plr.topics.size(); j++) {
                String str = (String) plr.topics.get(j);
                // if match found
                if (str.equals(topic)) {
                  if (plr.deleted) {
                    // update sp
                    for (int p = 0; p < sp.it.length; p++) {
                      // update topics
                      for (int g = 0; g < sp.it[p].topics.length; g++) {
                        int ii;
                        String stopics = sp.it[p].topics[g];
                        if ( (ii = stopics.indexOf(topicTree.ISPATH)) >= 0) {
                          if ( (stopics.substring(ii + 1)).equals(topic)) {
                            fixed = true;
                            plr.removeImmediately = true;
                            sp.it[p].topics = deleteFromArray(sp.it[p].topics,
                                g);
                            sp.it[p].trees = deleteFromArray(sp.it[p].trees, g);
                            message.addTopic(allAdminNames[z], topic, null, true);
                            // if the saveprogram programitem is empty - delete it
                            if (sp.it[p].topics.length == 0) {
                              sp.it = deleteFromArray(sp.it, p);
                              noEmpty++;
                              break;
                            }
                          }
                        }
                      }
                    }
                  }
                  else {
                    // a match has been found in the wordlist store with one of the
                    // admins in the rename list.
                    String correctAdminName = allAdminNames[allAdminNames.
                        length - 1];
                    adminChangedName = !admin.equals(allAdminNames[z]);
                    // update sp with correct topic name
                    for (int p = 0; p < sp.it.length; p++) {
                      // update topics
                      for (int g = 0; g < sp.it[p].topics.length; g++) {
                        int ii;
                        String ts = sp.it[p].topics[g];
                        if ( (ii = ts.indexOf(topicTree.ISPATH)) >= 0) {
                          String preName = ts.substring(0, ii + 1);
                          if (adminChangedName) {
                            preName = preName.substring(0,
                                preName.lastIndexOf(topicTree.ISTOPIC) + 1) +
                                correctAdminName + topicTree.ISPATH;
                            String st = preName.substring(0,
                                preName.lastIndexOf(topicTree.CSEPARATOR));
                            preName = st.substring(0,
                                st.lastIndexOf(topicTree.CSEPARATOR) + 1) +
                                correctAdminName +
                                preName.substring(preName.
                                                  lastIndexOf(topicTree.CSEPARATOR));
                          }
                          String tname = ts.substring(ii + 1);
                          if (tname.equals(topic)) {
                            fixed = true;
                            plr.removeImmediately = true;
                            sp.it[p].topics[g] = preName + correctTopicName;
                          }
                        }
                      }
                      // update trees
                      for (int h = 0; h < sp.it[p].trees.length; h++) {
                        for (int f = 0; f < sp.it[p].trees[h].names.length; f++) {
                          int ii;
                          String ts = sp.it[p].trees[h].names[f];
                          if ( (ii = ts.indexOf(topicTree.ISPATH)) >= 0) {
                            String preName = ts.substring(0, ii + 1);
                            if (adminChangedName) {
                              preName = preName.substring(0,
                                  preName.lastIndexOf(topicTree.ISTOPIC) + 1) +
                                  correctAdminName + topicTree.ISPATH;
                            }
                            String tname = ts.substring(ii + 1);
                            if (tname.equals(topic)) {
                              fixed = true;
                              plr.removeImmediately = true;
                              sp.it[p].trees[h].names[f] = preName +
                                  correctTopicName;
                            }
                          }
                        }
                      }
                    }
                    message.addTopic(allAdminNames[z], topic, correctTopicName, false);
                  }
                }
              }
            }
          }
        }
        if (fixed) {
          for (int index = v.size() - 1; index >= 0; index--) {
            plist plr2 = (plist) v.get(index);
            if (plr2.removeImmediately) {
              v.removeElementAt(index);
              db.update(sharkStartFrame.plistRec, LISTSDB, v, db.VECTOR);
            }
          }
          anychange = true;
        }
      }
      // if the invalid topic couldn't be repaired - remove it from the sp
      if (!fixed) {
        for (int p = 0; p < sp.it.length; p++) {
          for (int g = 0; g < sp.it[p].topics.length; g++) {
            int ii;
            String stopics = sp.it[p].topics[g];
            if ( (ii = stopics.lastIndexOf(topicTree.ISPATH)) >= 0) {
              if ( (stopics.substring(ii + 1)).equals(topic)) {
                anychange = true;
                sp.it[p].topics = deleteFromArray(sp.it[p].topics, g);
                sp.it[p].trees = deleteFromArray(sp.it[p].trees, g);
                message.addTopic(stopics.substring(stopics.lastIndexOf(topicTree.ISTOPIC)+1 ,ii),  topic, null, true);
                // if the saveprogram is empty - delete it
                if (sp.it[p].topics.length == 0) {
                  sp.it = deleteFromArray(sp.it, p);
                  noEmpty++;
                  break;
                }
              }
            }
          }
        }
      }
    }
    if (anychange && (invalidTopics != null)) {
      if (noEmpty >= spItemNo) {
//startPR2007-12-10^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//        sp = null;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        for(int i = 0; i < sp.students.length; i++)
          db.delete(currStudent, projectlist, db.PROGRAM);
//startPR2007-12-10^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        sp = null;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      }
      else {
        message.sendMessage();
        for(int i = 0; i < sp.students.length; i++)
          db.update(sp.students[i], projectlist, sp, db.PROGRAM);
      }
    }
    return sp;
  }

  private static String[] deleteFromArray(String[] sp, int removeIndex) {
    int len = sp.length;
    String news[] = new String[len - 1];
    System.arraycopy(sp, 0, news, 0, removeIndex);
    if (len > removeIndex - 1) {
      System.arraycopy(sp, removeIndex + 1, news, removeIndex,
                       len - removeIndex - 1);
    }
    return news;
  }

  private saveTree1.saveTree2[] deleteFromArray(saveTree1.saveTree2[] sp,
                                                int removeIndex) {
    int len = sp.length;
    saveTree1.saveTree2 news[] = new saveTree1.saveTree2[len - 1];
    System.arraycopy(sp, 0, news, 0, removeIndex);
    if (len > removeIndex - 1) {
      System.arraycopy(sp, removeIndex + 1, news, removeIndex,
                       len - removeIndex - 1);
    }
    return news;
  }

  private program.programitem[] deleteFromArray(program.programitem[] sp,
                                                int removeIndex) {
    int len = sp.length;
    program.programitem news[] = new program.programitem[len - 1];
    System.arraycopy(sp, 0, news, 0, removeIndex);
    if (len > removeIndex - 1) {
      System.arraycopy(sp, removeIndex + 1, news, removeIndex,
                       len - removeIndex - 1);
    }
    return news;
  }


  /**
   * Messages that can be sent to the relevant administrator/teacher's that notify
   * them of changes to private word list assignments.
   */
  private class messageForAdmin {
    public boolean deleted = false;
    public boolean renamed = false;
    public String spName;
    public String administrator;
    public Vector topics = new Vector();
    String[] affectedStudents;

    private class mtopic {
      boolean deleted;
      String nameBefore;
      String nameAfter;
      String owner;
      mtopic(String own, String nb, String na, boolean del) {
        owner = own;
        nameBefore = nb;
        nameAfter = na;
        deleted = del;
      }
    }

    public void addTopic(String own, String nb, String na, boolean del) {
      if (del) {
        deleted = true;
      }
      else {
        renamed = true;
      }
      topics.add(new mtopic(own, nb, na, del));
    }

    public void sendMessage() {
      String text = "";
      String spaces = "  ";
      text = (u.gettext("privatelistrecord", "updatedsetwork", spName)).replace('|', '\n');
      if (deleted) {
        text += (u.gettext("privatelistrecord", "deleted")).replace('|', '\n');
        for (int i = 0; i < topics.size(); i++) {
          mtopic currtop = (mtopic) topics.get(i);
          if (currtop.deleted) {
            text += spaces + "- "+ u.gettext("privatelistrecord", "author", currtop.owner)+spaces +"'" + currtop.nameBefore + "'\n";
          }
        }
        text += "\n";
      }
      if (renamed) {
        text += (u.gettext("privatelistrecord", "renamed")).replace('|', '\n');
        for (int i = 0; i < topics.size(); i++) {
          mtopic currtop = (mtopic) topics.get(i);
          if (!currtop.deleted) {
            text += spaces + "- "+ u.gettext("privatelistrecord", "author", currtop.owner)
                +spaces +"'" + currtop.nameBefore + "'" + spaces +
                u.gettext("privatelistrecord", "renamedto") + spaces + "'"
                + currtop.nameAfter + "'\n";
          }
        }
        text += "\n";
      }
      text += (u.gettext("privatelistrecord", "studentsset")).replace('|', '\n');
      for (int i = 0; i < affectedStudents.length; i++) {
          text += spaces + "- " + affectedStudents[i] + "\n";
      }
      messages.sendMessage(administrator, null, text);
    }
  }
}
