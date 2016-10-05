
package dispatchdepot;

import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

/**
 * 
 * @author Matthew Hamilton
 * <b>Description<b>: The driver class I will be executing for this program that will communicate with all other sections of the program.
 */

public class DispatchDepot
{

    public static void main(String[] args)
    {
        JFrame depotWindow = new JFrame("Dispatch Depot");
        DepotPanel mainPanel = new DepotPanel();
        depotWindow.add(mainPanel);
        depotWindow.setDefaultCloseOperation(EXIT_ON_CLOSE);
        depotWindow.setResizable(false); 
        depotWindow.setSize(500,578);
        depotWindow.setVisible(true);
    }

}
