
package dispatchdepot;

import javax.swing.ImageIcon;

/**
 * 
 * @author Matthew Hamilton
 * <b>Description<b>:One of the subclasses of class Parcel that I will be using.
 * 
 */
public class Envelope extends Parcel
{

    private int size;
    
    Envelope()
    {
        super();
        size = 0;
    }

    Envelope(int id, int z, int s)
    {
        super(id, z);
        size = s;
        
        if(size == 3)
        {
            isLarge = true;
        }
        
        switch(size)
        {
            case 1: charge = 4.25;
                break;
            case 2: charge = 8.25;
                break;
            case 3: charge = 16.49;
                break;
            default: charge = 0;
                break;
        }        
        if(zone == 2)
        {
            charge = charge * 1.5;
        }
        else if(zone == 3)
        {
            charge = charge * 2;
        }        
    }

    @Override
    public boolean getIsLarge()
    {
        if(size == 3)
        {
            isLarge = true;
        }
        return isLarge;
    }

    public void setSize(int s)
    {
        size = s;
    }
    
    @Override
    public double getCharge()
    {
        switch(size)
        {
            case 1: charge = 4.25;
                break;
            case 2: charge = 8.25;
                break;
            case 3: charge = 16.49;
                break;
            default: charge = 0;
                break;
        }
        
        if(zone == 2)
        {
            charge = charge * 1.5;
        }
        else if(zone == 3)
        {
            charge = charge * 2;
        }
        
        return charge;
    }

    @Override
    public String toString()
    {
        return "Envelope{" + super.toString() + ", size=" + size + "}";
    }

    public int getSize()
    {
        return size;
    }

    @Override
    public ImageIcon getImage()
    {
        switch(size)
        {
            case 1: image = new ImageIcon("envelope-small.png");
                break;
            case 2: image = new ImageIcon("envelope-medium.png");
                break;
            case 3: image = new ImageIcon("envelope-large.png");
                break;
            default: image = null;
                break;
        }
        return image;
    }

}
