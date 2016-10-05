
package dispatchdepot;

import javax.swing.ImageIcon;

/**
 * 
 * @author Matthew Hamilton
 * <b>Description<b>:One of the subclasses of class Parcel that I will be using.
 * 
 */
public class Tube extends Parcel
{

    private int length;

    Tube()
    {
        super();
        length = 0;
    }
    
    Tube(int id, int z, int l)
    {
        super(id, z);
        length = l;
        if(length >= 150)
        {
            isLarge = true;
        }
        
        if(length < 150)
        {
            charge = 6.50;
        }
        else if(length < 250)
        {
            charge = 10.50;
        }
        else
        {
            charge = 0;
        }
    }
    
    public void setLength(int l)
    {
        length = l;
    }
    
    @Override
    public double getCharge()
    {
        if(getIsLarge() == false)
        {
            charge = 6.50;
        }
        else if(length < 2.5)
        {
            charge = 10.50;
        }
        else
        {
            charge = 0;
        } 
        return charge;
    }

    public int getLength()
    {
        return length;
    }

    @Override
    public boolean getIsLarge()
    {
        if(length >= 150)
        {
            isLarge = true;
        }
        return isLarge;
    }

    @Override
    public String toString()
    {
        return "Tube{" + super.toString() + ", length=" + length + "cm}";
    }

    @Override
    public ImageIcon getImage()
    {
        if(getIsLarge() == false)
        {
            image = new ImageIcon("tube.png");
        }
        else
        {
            image = new ImageIcon("tube-large.png");
        }
        return image;
    }
}
