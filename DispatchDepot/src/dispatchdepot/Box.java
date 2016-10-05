
package dispatchdepot;

import javax.swing.ImageIcon;

/**
 * 
 * @author Matthew Hamilton
 * <b>Description<b>: One of the subclasses of class Parcel that I will be using.
 * 
 */

public class Box extends Parcel
{

    private int height;
    private int length;
    private int width;
    private double weight;
    
    Box()
    {
        super();
        height = 0;
        length = 0;
        width = 0;
        weight = 0.0;
    }

    Box(int id, int z, int h, int l, int w)
    {
        super(id, z);
        height = h;
        length = l;
        width = w;
        
        if(length >= 150)
        {
            isLarge = true;
        }
        
        weight = (height * length * width) / 6000.0;
        switch(zone)
        {
            case 1: charge = weight * 1.70;
                break;
            case 2: charge = weight * 2.20;
                break;
            case 3: charge = weight * 3.05;
                break;
            default: charge = 0;
                break;
        }        
    }
    
    public void setHeight(int h)
    {
        height = h;
    }

    public void setLength(int l)
    {
        length = l;
    }

    public void setWidth(int w)
    {
        width = w;
    }

    public void setWeight(double w)
    {
        weight = w;
    }

    public int getHeight()
    {
        return height;
    }

    public int getLength()
    {
        return length;
    }

    public int getWidth()
    {
        return width;
    }

    @Override
    public double getCharge()
    {
        switch(zone)
        {
            case 1: charge = weight * 1.70;
                break;
            case 2: charge = weight * 2.20;
                break;
            case 3: charge = weight * 3.05;
                break;
            default: charge = 0;
                break;
        }
        return charge;
    }

    @Override
    public String toString()
    {
        return "Box{" + super.toString() + ", weight=" + String.format("%.2f", weight) + "kg}";
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
    public ImageIcon getImage()
    {
        if(isLarge == false)
        {
            image = new ImageIcon("box.png");
        }
        else
        {
            image = new ImageIcon("box-large.png");
        }
        return image;
    }
}
