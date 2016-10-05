
package dispatchdepot;

import java.io.Serializable;
import javax.swing.ImageIcon;

/**
 * 
 * @author Matthew Hamilton
 * <b>Description<b>: The main class I will be using to store the parcels needed.
 * 
 */

public class Parcel implements Serializable
{

    public final int idNum;
    public final int zone;
    public double charge;
    public ImageIcon image;
    public boolean isLarge;

    /**
     * This sets the parcel to default values when nothing has been specified.
     */
    Parcel()
    {
        idNum = 0;
        zone = 0;
        charge = 0;
        isLarge = false;
        image = null;
    }
    
    /**
     * This sets the parcel details 
     * @param id
     * @param z 
     */
    Parcel(int id, int z)
    {
        idNum = id;
        zone = z;
        charge = 0;
        isLarge = false;
        image = null;
    }

    /**
     * This gets the isLarge value which is changed and overridden by subclasses.
     * @return isLarge
     */
    public boolean getIsLarge()
    {
        return isLarge;
    }

    /**
     * This gets the current charge which is overridden by subclass methods.
     * @return charge
     */
    public double getCharge()
    {
        return charge;
    }

    /**
     * This gets the image which is overridden by subclass methods.
     * @return image
     */
    public ImageIcon getImage()
    {
        return image;
    }
    
    /**
     * The classes toString method which is overridden and overrides its subclasses toString methods. 
     * @return toString
     */
    @Override
    public String toString()
    {
        return "id=" + idNum + ", zone=" + zone + ", charge=" + String.format("£%.2f", getCharge());
    }

    /**
     * This returns the idNum int value.
     * @return idNum
     */
    public int getIdNum()
    {
        return idNum;
    }

    /**
     * This returns the zone int value.
     * @return zone
     */
    public int getZone()
    {
        return zone;
    }

}
