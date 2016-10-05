
package dispatchdepot;


public class TestClass
{

    public static void main(String[] args)
    {
        Box testBox = new Box(1, 2, 30, 35, 50);
        Envelope testEnvelope = new Envelope(2, 3, 1);
        
        
        System.out.println("This box's charge is " + String.format("£%.2f", testBox.getCharge()));
        System.out.println(testBox.toString());
        
        System.out.println("The envelope's charge is " + String.format("£%.2f", testEnvelope.getCharge()));
        System.out.println(testEnvelope.toString());
    }
    
}
