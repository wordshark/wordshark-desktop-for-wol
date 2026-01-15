/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package shark;

/**
 *
 * @author White Space
 */
public class xres {
    public String name;
    public byte rec[];

    xres(String n, byte reca[]) {
        name = n.replaceAll("/", "");
        rec = reca;
    }
    xres(String n) {
        name = n.replaceAll("/", "");
    }
}
