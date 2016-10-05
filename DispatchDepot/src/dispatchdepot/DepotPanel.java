
package dispatchdepot;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.border.Border;

/**
*
 * @author Matthew Hamilton
 * <b>Description</b>: The main JPanel used in my dispatch depot application.
*
*/
public class DepotPanel extends JPanel implements ActionListener, MouseListener, Serializable
{
    
    private final Parcel[] parcelArray;
    private final Parcel[] lrParcelArray;
    private int pArrayNext;
    private int lPArrayNext;
    private double totalCharge;
    private int bayClicked;
    private boolean isLargeBayClicked;
    private final JMenuBar menuBar;
    private final JMenu fileMenu;
    private final JMenu addMenu;
    private final JMenuItem loadItem;
    private final JMenuItem saveItem;
    private final JMenuItem addBoxItem;
    private final JMenuItem addTubeItem;
    private final JMenuItem addEnvItem;
    private final JPanel bayArea;
    private final JPanel smallBayArea;
    private final Dimension smPnSize;
    private final JPanel smallBay[];
    private final JLabel smallBayIcons[];    
    private final JPanel largeBayArea;
    private final Dimension lrPnSize;    
    private final JPanel largeBay[];
    private final JLabel largeBayIcons[];    
    private final JPanel bottomPanel;
    private final JButton clearButton;
    private final JButton currCharButton;
    private final JButton totCharButton;
    private final Border blackBorder;
    private JFrame loadWindow;
    private JTextField loadText;
    private JButton loadConfirmBtn;
    private JButton loadCancelBtn;
    private JFrame saveWindow;
    private JTextField saveText;
    private JButton saveConfirmBtn;
    private JButton saveCancelBtn;
    private JFrame addBoxWindow;
    private JTextField bIdText;
    private JTextField bZoneText;
    private JTextField bWidthText;
    private JTextField bLengthText;
    private JTextField bHeightText;
    private JButton addBoxConfBtn;
    private JButton addBoxCancBtn;
    private JFrame addTubeWindow;
    private JTextField tIdText;
    private JTextField tZoneText;
    private JTextField tLengthText;
    private JButton addTubeConfBtn;
    private JButton addTubeCancBtn;
    private JFrame addEnvWindow;
    private JTextField eIdText;
    private JTextField eZoneText;
    private JComboBox eSizeCBox;
    private JButton addEnvConfBtn;
    private JButton addEnvCancBtn;
    private JFrame dialogWindow;
    private JButton diaConfBtn;
    private JFrame currCharWindow;
    private JButton currCharConfBtn;
    private JFrame totCharWindow;
    private JButton totCharConfBtn;
    private JFrame dispDetailsWindow;
    private JButton dispDetaConfBtn;
    private JFrame bayEmptyWindow;
    private JButton bayEmptyConfBtn;
    private JFrame removeParcelWindow;
    private JButton remParConfBtn;
    private Parcel currParcel;
    private JButton updBoxConfBtn;
    private JButton updBoxCancBtn;
    private JButton updTubeConfBtn;
    private JButton updTubeCancBtn;
    private JButton updEnvConfBtn;
    private JButton updEnvCancBtn;
    private JButton remParCancBtn;
    
    /**
     *  This is the constructor for the main panel, creating the initial GUI.
     */
    
    DepotPanel()
    {
        /*
        * Initialising the main counters and arrays of my program.
        */
        parcelArray = new Parcel[12];
        lrParcelArray = new Parcel[4];
        pArrayNext = 0;
        lPArrayNext = 0;
        totalCharge = 0.0;
        isLargeBayClicked = false;
        bayClicked = 0;
        
        /**
         * Setting the layout for the panel, creating a menu bar
         */
        setLayout(new BorderLayout());
        menuBar = new JMenuBar();
        add(menuBar, BorderLayout.NORTH);
        
        /**
         * Adding the menus to the menu bar.
         */
        fileMenu = new JMenu("File");
        addMenu = new JMenu("Add");
        menuBar.add(fileMenu);
        menuBar.add(addMenu);
        
        /**
         * Adding the items to the menus in the bar. I'm also adding actionListeners to detect clicks on these.
         */
        loadItem = new JMenuItem("Load...");
        saveItem = new JMenuItem("Save...");
        loadItem.addActionListener(this);
        fileMenu.add(loadItem);
        saveItem.addActionListener(this);
        fileMenu.add(saveItem);        
        addBoxItem = new JMenuItem("New Box...");
        addTubeItem = new JMenuItem("New Tube...");
        addEnvItem = new JMenuItem("New Envelope...");
        addBoxItem.addActionListener(this);
        addMenu.add(addBoxItem);
        addTubeItem.addActionListener(this);
        addMenu.add(addTubeItem);
        addEnvItem.addActionListener(this);
        addMenu.add(addEnvItem);
        
        /**
         * Adding the bayArea section which will house all bays, and then adding smallBayArea, which will be where the program adds non-large items.
         */
        bayArea = new JPanel(new BorderLayout());
        add(bayArea, BorderLayout.CENTER);
        smallBayArea = new JPanel(new GridLayout(3,4));
        bayArea.add(smallBayArea, BorderLayout.NORTH);
        smallBay = new JPanel[12];
        smallBayIcons = new JLabel[12];
        blackBorder = BorderFactory.createLineBorder(Color.black);
        smPnSize = new Dimension(115,100);
        
        /**
         * Traversing arrays of JPanels and JLabels to set them up on the GUI.
         */
        for(int i = 0; i <= 11; i++)
        {
            smallBay[i] = new JPanel();
            smallBayIcons[i] = new JLabel();
            setupLabel(smallBayIcons[i], smPnSize, smallBay[i], smallBayArea);
        }
        
        /**
         * Setting up the large section of bayArea, housing large items.
         */
        largeBayArea = new JPanel(new GridLayout(1,4));
        bayArea.add(largeBayArea, BorderLayout.SOUTH);
        largeBay = new JPanel[4];
        largeBayIcons = new JLabel[4];       
        lrPnSize = new Dimension(115,200);
        for(int i = 0; i <= 3; i++)
        {
            largeBay[i] = new JPanel();
            largeBayIcons[i] = new JLabel();
            setupLabel(largeBayIcons[i], lrPnSize, largeBay[i], largeBayArea);
        }     
        
        /**
         * Adding final buttons to GUI and adding MouseListeners
         */
        currCharButton = new JButton("Current Charge");
        totCharButton = new JButton("Total Charge");
        clearButton = new JButton("Clear All");
        bottomPanel = new JPanel(new GridLayout(1, 4, 10, 10));
        bottomPanel.add(currCharButton);
        currCharButton.addMouseListener(this);
        bottomPanel.add(totCharButton);
        totCharButton.addMouseListener(this);
        bottomPanel.add(clearButton);
        clearButton.addMouseListener(this);
        add(bottomPanel, BorderLayout.SOUTH);
        
       
    }
    
    /**
     * This method sets up my labels and panels of my GUI neatly and efficiently.
     * 
     * @param l The label being added to the panel.
     * @param d The size the panel must be sized as.
     * @param j The panel being added to the interface housing a single bay (single label).
     * @param jp The panel my j panel is being added to which houses all bays (either small or large).
     */
    private void setupLabel(JLabel l, Dimension d,  JPanel j, JPanel jp)
    {
        j.setPreferredSize(d);
        j.setBorder(blackBorder);
        j.setVisible(true);
        j.setPreferredSize(d);
        l.setOpaque(true);
        j.addMouseListener(this);
        j.add(l);
        jp.add(j);        
    }      
 
    /**
     * This will help with designing my GridBagLayouts when making new panels.
     * 
     * @param j The panel using a GridBagLayout.
     * @param c The component being added to the interface.
     * @param x The x-coordinate the part will be placed in the grid.
     * @param y The y-coordinate the part will be placed in the grid.
     * @param w The width of the component in cells of the grid.
     * @param h The height of the component in cells of the grid.
     * @param i The amount of insets the component will have in the grid.
     * @param isLabel Boolean value for if the component is a label.
     */
    private void addGridBagLayout(JPanel j, Component c, int x, int y, int w, int h, int i, boolean isLabel)
    {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = w;
        gbc.gridheight = h;
        if(isLabel)
        {
            gbc.anchor = GridBagConstraints.LINE_END;
        }
        else
        {
            gbc.anchor = GridBagConstraints.LINE_START;
        }
        gbc.insets = new Insets(i,i,i,i);
        j.add(c, gbc);
    }
    
    /**
     * Simple method to show user a dialog where a confirmation must be received.
     */
    private void showDialog(String s)
    {
        dialogWindow = new JFrame("Error");
        JPanel diaPanel = new JPanel();
        dialogWindow.add(diaPanel);
        diaPanel.setLayout(new GridBagLayout());
        JLabel diaLabel = new JLabel(s);
        diaConfBtn = new JButton("OK");
        diaConfBtn.addActionListener(this);
        
        /**
         * This section adds elements to the panel using addGridBagLayout() and sets it up correctly.
         */
        addGridBagLayout(diaPanel, diaLabel, 0, 0, 1, 1, 5, true);
        addGridBagLayout(diaPanel, diaConfBtn, 0, 1, 1, 1, 5, false);
        dialogWindow.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        dialogWindow.setResizable(false);
        dialogWindow.setSize(400,100);
        dialogWindow.setLocationRelativeTo(this);
        dialogWindow.setVisible(true);
    }
    
    /**
     * A method to find a new bay from an array of parcels.
     * @param p The array of parcels where the method will search to find a new bay.
     * @return the integer value of the position in the array a free space will be found.
     */
    private int findNextFreeBay(Parcel[] p)
    {
        try
        {    
            Parcel[] currParcelArray = p;
            int count = -1;
            int length = currParcelArray.length;
            Parcel currParcel = new Parcel(); 
            /**
             * This states that while nothing is in currParcel, raise count by 1. This successfully lands on the next bay left even after one has been 
             * removed, and identifies when there are none left.
             */
            while(currParcel != null)
            {
                count++;
                currParcel = currParcelArray[count];
            }
            if((count < 0) || (count > length - 1))
            {
                count = length;
                return count;
            }
            else
            {
                return count;
            }
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
            return 12;
        }
    }        
    /**
     * This method is triggered whenever the 'load' button is pressed, and sets up the GUI for entry of file from the user.
     */
    private void loadOpen()
    {
        loadWindow = new JFrame("Load");        
        JPanel lPanel = new JPanel();
        loadWindow.add(lPanel);
        lPanel.setLayout(new GridBagLayout());
        JLabel loadLabel = new JLabel("Enter a data file (end with .dat):");
        loadText = new JTextField(20);
        loadConfirmBtn = new JButton("OK");
        loadConfirmBtn.addActionListener(this);
        loadCancelBtn = new JButton("Cancel");
        loadCancelBtn.addActionListener(this);
        
        /**
         * This section adds elements to the panel using addGridBagLayout() and sets it up correctly.
         */
        addGridBagLayout(lPanel, loadLabel, 0, 0, 1, 1, 5, true);
        addGridBagLayout(lPanel, loadText, 1, 0, 2, 1, 5, false);
        addGridBagLayout(lPanel, loadConfirmBtn, 1, 1, 1, 1, 5, false);
        addGridBagLayout(lPanel, loadCancelBtn, 2, 1, 1, 1, 5, false);
        loadWindow.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        loadWindow.setResizable(false);
        loadWindow.setSize(500,100);
        loadWindow.setLocationRelativeTo(this);
        loadWindow.setVisible(true);
    }
    
    /**
     * This method is triggered when the user clicks the 'OK' button and wants data to be loaded in from the specified data file.
     */
    private void loadClose()
    {
        String dataFile = loadText.getText();
        try
        {
            // This loads in an array of Parcels, and then stores it in a local variable.
            FileInputStream load = new FileInputStream(dataFile);
            ObjectInputStream input = new ObjectInputStream(load);
            Parcel[] newParcelArray = null;
            newParcelArray = (Parcel[])input.readObject();
            
            /**
             * This traverses both the large and small arrays sets current array data to null and sets up the new panellabel.
             */
            for(int i = 0; i <= 11; i++)
            {
                try
                {
                    parcelArray[i] = null;
                    smallBayIcons[i].setIcon(null);
                    parcelArray[i] = newParcelArray[i];
                    smallBayIcons[i].setIcon(parcelArray[i].getImage());
                }
                /**
                 * This catches if there is a null pointer in the array and carries on taking in values regardless.
                 */
                catch(NullPointerException e)
                {
                }    
            }
            for(int j = 0; j <= 3; j++)
            {
                try
                {    
                    lrParcelArray[j] = newParcelArray[j+12];
                    largeBayIcons[j].setIcon(lrParcelArray[j].getImage());
                }
                catch(NullPointerException e)
                {                    
                }    
            }
            input.close();
        }
        /**
         * These exceptions are found when there are errors with this procedure.
         */
        catch(IOException ex)
        {
            showDialog("Error connecting or reading to file");
        }
        catch(ClassNotFoundException ex)
        {
            showDialog("Class not found error");
        }    
        loadWindow.dispose();
    }        
            
    /**
     * This method is triggered when the user clicks the 'Save' item, and is used to set up the GUI for an entry of data file from the user.
     */
    private void saveOpen()
    {
        saveWindow = new JFrame("Save");
        JPanel sPanel = new JPanel();
        saveWindow.add(sPanel);
        sPanel.setLayout(new GridBagLayout());
        JLabel saveLabel = new JLabel("Enter a data file (end with .dat):");
        saveText = new JTextField(20);
        saveConfirmBtn = new JButton("OK");
        saveConfirmBtn.addActionListener(this);
        saveCancelBtn = new JButton("Cancel");
        saveCancelBtn.addActionListener(this);
        
        /**
         * This section adds elements to the panel using addGridBagLayout() and sets it up correctly.
         */
        addGridBagLayout(sPanel, saveLabel, 0, 0, 1, 1, 5, true);
        addGridBagLayout(sPanel, saveText, 1, 0, 2, 1, 5, false);
        addGridBagLayout(sPanel, saveConfirmBtn, 1, 1, 1, 1, 5, false);
        addGridBagLayout(sPanel, saveCancelBtn, 2, 1, 1, 1, 5, false);
        saveWindow.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        saveWindow.setResizable(false);
        saveWindow.setSize(500,100);
        saveWindow.setLocationRelativeTo(this);
        saveWindow.setVisible(true);
    }
    
    /**
     * This method is triggered when the user clicks the 'OK' button and wants data to be saved into the specified data file.
     */
    private void saveClose()
    {
        String dataFile = saveText.getText();
        Parcel[] newParcelArray = new Parcel[16];
        
        for(int i = 0; i <= 11; i++)
        {
            newParcelArray[i] = parcelArray[i];
        }
        for(int j = 0; j <= 3; j++)
        {
            newParcelArray[j+12] = lrParcelArray[j];
        }
        
        FileOutputStream fos = null;
        
        try
        {
            fos = new FileOutputStream(dataFile);
            ObjectOutputStream output = new ObjectOutputStream(fos);
            output.writeObject(newParcelArray);
            output.close();
        }
        catch(IOException ex)
        {
            showDialog("Error connecting or reading to file");
        }        
        
        saveWindow.dispose();
    }        
    
    /**
     * This item is triggered when the user clicks the 'New Box' item, and sets up the GUI for new box entry.
     */
    private void addBoxOpen()
    {
        addBoxWindow = new JFrame("Add New Box");
        JPanel bPanel = new JPanel();
        addBoxWindow.add(bPanel);
        bPanel.setLayout(new GridBagLayout());
        JLabel bIdLabel = new JLabel("ID no:");
        bIdText = new JTextField(10);
        JLabel bZoneLabel = new JLabel("Zone:");
        bZoneText = new JTextField(5);
        JLabel bWidthLabel = new JLabel("Width (in cm):");
        bWidthText = new JTextField(5);
        JLabel bLengthLabel = new JLabel("Length (in cm):");
        bLengthText = new JTextField(5);
        JLabel bHeightLabel = new JLabel("Height (in cm):");
        bHeightText = new JTextField(5);
        addBoxConfBtn = new JButton("OK");
        addBoxConfBtn.addActionListener(this);
        addBoxCancBtn = new JButton("Cancel");
        addBoxCancBtn.addActionListener(this);
        
        addGridBagLayout(bPanel, bIdLabel, 0, 0, 1, 1, 2, true);
        addGridBagLayout(bPanel, bIdText, 1, 0, 2, 1, 2, false);
        addGridBagLayout(bPanel, bZoneLabel, 0, 1, 1, 1, 2, true);
        addGridBagLayout(bPanel, bZoneText, 1, 1, 1, 1, 2, false);
        addGridBagLayout(bPanel, bWidthLabel, 0, 2, 1, 1, 2, true); 
        addGridBagLayout(bPanel, bWidthText, 1, 2, 1, 1, 2, false);
        addGridBagLayout(bPanel, bLengthLabel, 0, 3, 1, 1, 2, true);
        addGridBagLayout(bPanel, bLengthText, 1, 3, 1, 1, 2, false);
        addGridBagLayout(bPanel, bHeightLabel, 0, 4, 1, 1, 2, true);
        addGridBagLayout(bPanel, bHeightText, 1, 4, 1, 1, 2, false);
        addGridBagLayout(bPanel, addBoxConfBtn, 1, 5, 1, 1, 2, false);
        addGridBagLayout(bPanel, addBoxCancBtn, 2, 5, 1, 1, 2, false);
        addBoxWindow.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        addBoxWindow.setResizable(false);
        addBoxWindow.setSize(300,250);
        addBoxWindow.setLocationRelativeTo(this);
        addBoxWindow.setVisible(true);
       
    }
    
    /**
     * This method is triggered when the 'OK' button is clicked, and is used to add a new box to the array based on the user's specified values.
     */
    private void addBoxClose()
    {
        int idNumber = Integer.parseInt(bIdText.getText());
        int zone = Integer.parseInt(bZoneText.getText());
        int width = Integer.parseInt(bWidthText.getText());
        int length = Integer.parseInt(bLengthText.getText());
        int height = Integer.parseInt(bHeightText.getText());
        
        Box newBox = new Box(idNumber, zone, height, length, width);
        
        /**
         * This searches through either the small or the larg parcel arrays and checks to see if there is a free space
         */
        boolean isLarge = newBox.getIsLarge();
        if(isLarge == false)
        {
            if(pArrayNext < 12)
            {
                /**
                 * This writes the new Box to the array, adds to the total charge, and sets a new image for the label
                 */
                parcelArray[pArrayNext] = newBox;
                totalCharge += newBox.getCharge();
                ImageIcon image = newBox.getImage();
                smallBayIcons[pArrayNext].setIcon(image);
            }
            else
            {
                showDialog("There are no empty spaces for this item.");
            }
            /**
             * This then finds the next free bay before another item is placed.
             */
            pArrayNext = findNextFreeBay(parcelArray);    
        }
        else
        {
            if(lPArrayNext < 4)
            {
                lrParcelArray[lPArrayNext] = newBox;
                totalCharge += newBox.getCharge();
                ImageIcon image = newBox.getImage();
                largeBayIcons[lPArrayNext].setIcon(image);
            }
            else
            {
                showDialog("There are no empty spaces for this item.");
            }
            lPArrayNext = findNextFreeBay(lrParcelArray);
        }
        /**
         * This then disposes of the new frame created by addBoxOpen()
         */
        addBoxWindow.dispose();
    }        
    
    /**
     * This method is triggered when the 'New Envelope' item is clicked, and sets up the GUI.
     */
    private void addEnvelopeOpen()
    {
        addEnvWindow = new JFrame("Add New Envelope");
        JPanel ePanel = new JPanel();
        addEnvWindow.add(ePanel);
        ePanel.setLayout(new GridBagLayout());
        JLabel eIdLabel = new JLabel("ID no:");
        eIdText = new JTextField(10);
        JLabel eZoneLabel = new JLabel("Zone:");
        eZoneText = new JTextField(5);
        JLabel eSizeLabel = new JLabel("Size:");
        String[] sizeChoices = {"Small", "Medium", "Large"};
        eSizeCBox = new JComboBox(sizeChoices);
        addEnvConfBtn = new JButton("OK");
        addEnvConfBtn.addActionListener(this);
        addEnvCancBtn = new JButton("Cancel");
        addEnvCancBtn.addActionListener(this);
        
        addGridBagLayout(ePanel, eIdLabel, 0, 0, 1, 1, 2, true);
        addGridBagLayout(ePanel, eIdText, 1, 0, 2, 1, 2, false);
        addGridBagLayout(ePanel, eZoneLabel, 0, 1, 1, 1, 2, true);
        addGridBagLayout(ePanel, eZoneText, 1, 1, 1, 1, 2, false);
        addGridBagLayout(ePanel, eSizeLabel, 0, 2, 1, 1, 2, true); 
        addGridBagLayout(ePanel, eSizeCBox, 1, 2, 1, 1, 2, false);
        addGridBagLayout(ePanel, addEnvConfBtn, 1, 3, 1, 1, 2, false);
        addGridBagLayout(ePanel, addEnvCancBtn, 2, 3, 1, 1, 2, false);
        addEnvWindow.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        addEnvWindow.setResizable(false);
        addEnvWindow.setSize(250,200);
        addEnvWindow.setLocationRelativeTo(this);
        addEnvWindow.setVisible(true);
    }
    
    /**
     * This method is triggered from the user clicking the 'OK' button on the AddEnvelope GUI, and entering those values into the array.
     */
    private void addEnvelopeClose()
    {
        int id = Integer.parseInt(eIdText.getText());
        int zone = Integer.parseInt(eZoneText.getText());
        int size = eSizeCBox.getSelectedIndex() + 1;
        
        Envelope newEnv = new Envelope(id, zone, size);
        
        boolean isLarge = newEnv.getIsLarge();
        if(isLarge == false)
        {
            if(pArrayNext < 12)
            {
                parcelArray[pArrayNext] = newEnv;
                totalCharge += newEnv.getCharge();
                ImageIcon image = newEnv.getImage();
                smallBayIcons[pArrayNext].setIcon(image);
            }
            else
            {
                showDialog("There are no empty spaces for this item.");
            }
            pArrayNext = findNextFreeBay(parcelArray);
        }
        else
        {
            if(lPArrayNext < 4)
            {
                lrParcelArray[lPArrayNext] = newEnv;
                totalCharge += newEnv.getCharge();
                ImageIcon image = newEnv.getImage();
                largeBayIcons[lPArrayNext].setIcon(image);
            }
            else
            {
                showDialog("There are no empty spaces for this item.");
            }
            lPArrayNext = findNextFreeBay(lrParcelArray);
        }
        addEnvWindow.dispose();
    }        
    
    /**
     * This method is triggered when the 'New Tube' item is clicked, and sets up the GUI.
     */
    private void addTubeOpen()
    {
        addTubeWindow = new JFrame("Add New Tube");
        JPanel tPanel = new JPanel();
        addTubeWindow.add(tPanel);
        tPanel.setLayout(new GridBagLayout());
        JLabel tIdLabel = new JLabel("ID no:");
        tIdText = new JTextField(10);
        JLabel tZoneLabel = new JLabel("Zone:");
        tZoneText = new JTextField(5);
        JLabel tLengthLabel = new JLabel("Length (in cm):");
        tLengthText = new JTextField(5);
        addTubeConfBtn = new JButton("OK");
        addTubeConfBtn.addActionListener(this);
        addTubeCancBtn = new JButton("Cancel");
        addTubeCancBtn.addActionListener(this);
        
        addGridBagLayout(tPanel, tIdLabel, 0, 0, 1, 1, 2, true);
        addGridBagLayout(tPanel, tIdText, 1, 0, 2, 1, 2, false);
        addGridBagLayout(tPanel, tZoneLabel, 0, 1, 1, 1, 2, true);
        addGridBagLayout(tPanel, tZoneText, 1, 1, 1, 1, 2, false);
        addGridBagLayout(tPanel, tLengthLabel, 0, 2, 1, 1, 2, true); 
        addGridBagLayout(tPanel, tLengthText, 1, 2, 1, 1, 2, false);
        addGridBagLayout(tPanel, addTubeConfBtn, 1, 3, 1, 1, 2, false);
        addGridBagLayout(tPanel, addTubeCancBtn, 2, 3, 1, 1, 2, false);
        addTubeWindow.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        addTubeWindow.setResizable(false);
        addTubeWindow.setSize(250,200);
        addTubeWindow.setLocationRelativeTo(this);
        addTubeWindow.setVisible(true);
    }
    
    /**
     * This method is triggered from the user clicking the 'OK' button on the AddEnvelope GUI, and entering those values into the array.
     */
    private void addTubeClose()
    {
        int id = Integer.parseInt(tIdText.getText());
        int zone = Integer.parseInt(tZoneText.getText());
        int length = Integer.parseInt(tLengthText.getText());
        Tube newTube = new Tube(id, zone, length);
        
        boolean isLarge = newTube.getIsLarge();
        if(isLarge == false)
        {
            if(pArrayNext < 12)
            {
                parcelArray[pArrayNext] = newTube;
                totalCharge += newTube.getCharge();
                ImageIcon image = newTube.getImage();
                smallBayIcons[pArrayNext].setIcon(image);
            }
            else
            {
                showDialog("There are no empty spaces for this item.");
            }
            pArrayNext = findNextFreeBay(parcelArray);
        }
        else
        {
            if(lPArrayNext < 4)
            {
                lrParcelArray[lPArrayNext] = newTube;
                totalCharge += newTube.getCharge();
                ImageIcon image = newTube.getImage();
                largeBayIcons[lPArrayNext].setIcon(image);
            }
            else
            {
                showDialog("There are no empty spaces for this item.");
            }
            lPArrayNext = findNextFreeBay(lrParcelArray);
        }
        addTubeWindow.dispose();
    }
    
    /**
     * This method is triggered when the user clicks the 'Current Charge' button, and displays the charge of the current parcels in the bay.
     */
    private void calcCurrCharge()
    {
        
        currCharWindow = new JFrame("Current Charge");
        JPanel cPanel = new JPanel();
        currCharWindow.add(cPanel);
        cPanel.setLayout(new GridBagLayout());  
        
        double currCharge = 0.0;
       
        /**
         * This traverses both the small and large arrays and adds any item's charge to currCharge.  
         */
        for(int i = 0; i <= 11; i++)
        {
            try
            {    
                currCharge += parcelArray[i].getCharge();
            }
            catch(ArrayIndexOutOfBoundsException | NullPointerException e)
            {               
            }    
        }         

        for(int j = 0; j <= 3; j++)
        {
            try
            {    
                currCharge += lrParcelArray[j].getCharge();
            }
            catch(ArrayIndexOutOfBoundsException | NullPointerException e)
            {                
            }    
        }    
        
        JLabel currCharLabel = new JLabel("The current charge is: " + String.format("£%.2f", currCharge));
        currCharConfBtn = new JButton("OK");
        currCharConfBtn.addActionListener(this);
        addGridBagLayout(cPanel, currCharLabel, 0, 0, 1, 1, 2, true);
        addGridBagLayout(cPanel, currCharConfBtn, 1, 0, 1, 1, 2, true);
        currCharWindow.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        currCharWindow.setResizable(false);
        currCharWindow.setSize(350,100);
        currCharWindow.setLocationRelativeTo(this);
        currCharWindow.setVisible(true);
        
    }
    
    /**
     * This method is triggered when the user clicks the 'Total Charge' button and displays the total charge of all parcels in bay since last cleared.
     */
    private void calcTotalCharge()
    {
        /**
         * This accesses the global variable totalCharge instead of calculating itself to find the total charge since its been wiped.
         */
        totCharWindow = new JFrame("Total Charge");
        JPanel tPanel = new JPanel();
        totCharWindow.add(tPanel);
        tPanel.setLayout(new GridBagLayout());
        JLabel totCharLabel = new JLabel("The total charge is: " + String.format("£%.2f", totalCharge));
        totCharConfBtn = new JButton("OK");
        totCharConfBtn.addActionListener(this);
        addGridBagLayout(tPanel, totCharLabel, 0, 0, 1, 1, 2, true);
        addGridBagLayout(tPanel, totCharConfBtn, 1, 0, 1, 1, 2, true);
        totCharWindow.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        totCharWindow.setResizable(false);
        totCharWindow.setSize(350,100);
        totCharWindow.setLocationRelativeTo(this);
        totCharWindow.setVisible(true);
    }
    
    /**
     * This method clears all of the parcels in the bay, including setting the label's icons to null, and resets the initial counters to 0.
     */
    private void clearAll()
    {
        /**
         * This traverses small and large bays to set both the parcel array and the label array to null. It also sets counters to 0.
         */
        for(int i = 0; i <= 11; i++)
        {    
            parcelArray[i] = null;
            smallBayIcons[i].setIcon(null);
        }
        for(int j = 0; j <=3; j++)
        {
            lrParcelArray[j] = null;
            largeBayIcons[j].setIcon(null);
        }
        totalCharge = 0.0;
        pArrayNext = 0;
        lPArrayNext = 0;
    }
    
    /**
     * This method is triggered when the user left-clicks on a bay after going through validateMouseAction(). It finds the bay clicked and displays the parcel's toString method.
     */
    private void displayDetails()
    {    
        dispDetailsWindow = new JFrame("Display Details");
        JPanel dPanel = new JPanel();
        dispDetailsWindow.add(dPanel);
        dPanel.setLayout(new GridBagLayout());
        JLabel dispDetailsLabel = new JLabel();
        dispDetaConfBtn = new JButton("OK");
        Parcel p;
        dispDetaConfBtn.addActionListener(this);

        try
        {    
            /**
             * This takes the variables changed in MouseClicked to set details for label string. It then resets counters to 0.
             */
            if(isLargeBayClicked == false)
            {
                switch(bayClicked)
                {            
                    case 0: p = parcelArray[0];
                    dispDetailsLabel.setText(p.toString());
                        break;
                    case 1: p = parcelArray[1];
                    dispDetailsLabel.setText(p.toString());
                        break;
                    case 2: p = parcelArray[2];
                    dispDetailsLabel.setText(p.toString());
                        break;
                    case 3: p = parcelArray[3];
                    dispDetailsLabel.setText(p.toString());
                        break;
                    case 4: p = parcelArray[4];
                    dispDetailsLabel.setText(p.toString());
                    case 5: p = parcelArray[5];
                    dispDetailsLabel.setText(p.toString());
                        break;
                    case 6: p = parcelArray[6];
                    dispDetailsLabel.setText(p.toString());
                        break;
                    case 7: p = parcelArray[7];
                    dispDetailsLabel.setText(p.toString());
                        break;
                    case 8: p = parcelArray[8];
                    dispDetailsLabel.setText(p.toString());
                        break;
                    case 9: p = parcelArray[9];
                    dispDetailsLabel.setText(p.toString());
                        break;
                    case 10: p = parcelArray[10];
                    dispDetailsLabel.setText(p.toString());
                        break;
                    case 11: p = parcelArray[11];
                    dispDetailsLabel.setText(p.toString());
                        break;
                }  
            }
            else
            {
                switch(bayClicked)
                {    
                    case 12: p = lrParcelArray[0];
                    dispDetailsLabel.setText(p.toString());
                        break;
                    case 13: p = lrParcelArray[1];
                    dispDetailsLabel.setText(p.toString());
                        break;
                    case 14: p = lrParcelArray[2];
                    dispDetailsLabel.setText(p.toString());
                        break;
                    case 15: p = lrParcelArray[3];
                    dispDetailsLabel.setText(p.toString());
                        break;
                    default: break;
                }  
            }    
            
            isLargeBayClicked = false;
            bayClicked = 0;

            addGridBagLayout(dPanel, dispDetailsLabel, 0, 0, 1, 1, 2, true);
            addGridBagLayout(dPanel, dispDetaConfBtn, 0, 1, 1, 1, 2, false);
            dispDetailsWindow.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            dispDetailsWindow.setResizable(false);
            dispDetailsWindow.setSize(400,200);
            dispDetailsWindow.setLocationRelativeTo(this);
            dispDetailsWindow.setVisible(true);
        }
        catch(NullPointerException e)
        {
            showDialog("This bay is empty.");
        }
    }
    
    /**
     * This method is triggered when the user right-clicks on a bay, and is used to setup the GUI for confirmation from the user.
     */
    private void removeParcelOpen()
    {
        try
        {    
            if(isLargeBayClicked == false)
            {
                /**
                 * Setting currParcel as the parcel clicked
                 */
                switch(bayClicked)
                {            
                    case 0: currParcel = parcelArray[0];
                        break;
                    case 1: currParcel = parcelArray[1];
                        break;
                    case 2: currParcel = parcelArray[2];
                        break;
                    case 3: currParcel = parcelArray[3];
                        break;
                    case 4: currParcel = parcelArray[4];
                        break;
                    case 5: currParcel = parcelArray[5];
                        break;
                    case 6: currParcel = parcelArray[6];
                        break;
                    case 7: currParcel = parcelArray[7];
                        break;
                    case 8: currParcel = parcelArray[8];
                        break;
                    case 9: currParcel = parcelArray[9];;
                        break;
                    case 10: currParcel = parcelArray[10];
                        break;
                    case 11: currParcel = parcelArray[11];
                        break;
                }  
            }
            else
            {
                switch(bayClicked)
                {    
                    case 12: currParcel = lrParcelArray[0];
                        break;
                    case 13: currParcel = lrParcelArray[1];
                        break;
                    case 14: currParcel = lrParcelArray[2];
                        break;
                    case 15: currParcel = lrParcelArray[3];
                        break;
                    default: break;
                }    
            }
            /**
             * If null, show dialog, else, show removeParcelWindow frame
             */
            if(currParcel == null)
            {
                showDialog("This bay is empty.");
            }
            else
            {    
                removeParcelWindow = new JFrame("Remove Parcel");
                JPanel rPanel = new JPanel();
                removeParcelWindow.add(rPanel);
                rPanel.setLayout(new GridBagLayout());
                JLabel remParLabel = new JLabel("Are you sure you want to remove this parcel?");
                remParConfBtn = new JButton("OK");
                remParConfBtn.addActionListener(this);
                remParCancBtn = new JButton("Cancel");
                remParCancBtn.addActionListener(this);
                addGridBagLayout(rPanel, remParLabel, 0, 0, 2, 1, 2, true);
                addGridBagLayout(rPanel, remParConfBtn, 0, 1, 1, 1, 2, false);
                addGridBagLayout(rPanel, remParCancBtn, 1, 1, 1, 1, 2, false);
                removeParcelWindow.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                removeParcelWindow.setResizable(false);
                removeParcelWindow.setSize(300,100);
                removeParcelWindow.setLocationRelativeTo(this);
                removeParcelWindow.setVisible(true);
            }    
        }
        catch(NullPointerException e)
        {
            /**
             * If nothing found, show dialog.
             */
            showDialog("This bay is empty.");
        }
    }
    
    /**
     * This method is triggered when the user has confirmed they want to remove the parcel clicked from the bay.
     */
    private void removeParcelClose()
    {
        if(isLargeBayClicked == false)
            {
                switch(bayClicked)
                {
                    /**
                     * Remove parcel clicked on by setting to null, setting label to null, and resetting counter for next.
                     */
                    case 0: 
                    parcelArray[0] = null;
                    smallBayIcons[0].setIcon(null);
                    pArrayNext = findNextFreeBay(parcelArray);
                        break;
                    case 1: 
                    parcelArray[1] = null;
                    smallBayIcons[1].setIcon(null);
                    pArrayNext = findNextFreeBay(parcelArray);
                        break;
                    case 2: 
                    parcelArray[2] = null;
                    smallBayIcons[2].setIcon(null);
                    pArrayNext = findNextFreeBay(parcelArray);
                        break;
                    case 3: 
                    parcelArray[3] = null;
                    smallBayIcons[3].setIcon(null);
                    pArrayNext = findNextFreeBay(parcelArray);
                        break;
                    case 4: 
                    parcelArray[4] = null;
                    smallBayIcons[4].setIcon(null);
                    pArrayNext = findNextFreeBay(parcelArray);
                        break;
                    case 5: 
                    parcelArray[5] = null;
                    smallBayIcons[5].setIcon(null);
                    pArrayNext = findNextFreeBay(parcelArray);
                        break;
                    case 6: 
                    parcelArray[6] = null;
                    smallBayIcons[6].setIcon(null);
                    pArrayNext = findNextFreeBay(parcelArray);                   
                        break;
                    case 7: 
                    parcelArray[7] = null;
                    smallBayIcons[7].setIcon(null);
                    pArrayNext = findNextFreeBay(parcelArray);
                        break;
                    case 8: 
                    parcelArray[8] = null;
                    smallBayIcons[8].setIcon(null);
                    pArrayNext = findNextFreeBay(parcelArray);
                        break;
                    case 9: 
                    parcelArray[9] = null;
                    smallBayIcons[9].setIcon(null);
                    pArrayNext = findNextFreeBay(parcelArray);
                        break;
                    case 10: 
                    parcelArray[10] = null;
                    smallBayIcons[10].setIcon(null);
                    pArrayNext = findNextFreeBay(parcelArray);
                        break;
                    case 11: 
                    parcelArray[11] = null;
                    smallBayIcons[11].setIcon(null);
                    pArrayNext = findNextFreeBay(parcelArray);
                        break;
                    default: break;    
                }  
            }
        else
        {
            switch(bayClicked)
            {    
                case 12: 
                lrParcelArray[0] = null;
                largeBayIcons[0].setIcon(null);
                lPArrayNext = findNextFreeBay(lrParcelArray);
                    break;
                case 13: 
                lrParcelArray[1] = null;
                largeBayIcons[1].setIcon(null);
                lPArrayNext = findNextFreeBay(lrParcelArray);
                    break;
                case 14: 
                lrParcelArray[2] = null;
                largeBayIcons[2].setIcon(null);
                lPArrayNext = findNextFreeBay(lrParcelArray);
                    break;
                case 15: 
                lrParcelArray[3] = null;
                largeBayIcons[3].setIcon(null);
                lPArrayNext = findNextFreeBay(lrParcelArray);
                    break;
                default: break;
            }    
        }
        
        /**
         * Reset values to 0 and dispose window.
         */
        bayClicked = 0;
        isLargeBayClicked = false;
        currParcel = null;
        removeParcelWindow.dispose();
        
    }
    
    /**
     * This method is triggered when the user middle-clicks a bay, and defers to the formula identifyClass().
     */
    private void updateParcel()
    {
        try
        {            
            /**
             * Send the array and position of parcel to identifyClass();
             */
            if(isLargeBayClicked == false)
            {    
                switch(bayClicked)
                {
                    case 0: 
                        identifyClass(0, parcelArray);
                        break;
                    case 1:
                        identifyClass(1, parcelArray);
                        break;
                    case 2:
                        identifyClass(2, parcelArray);
                        break;
                    case 3:
                        identifyClass(3, parcelArray);
                        break;
                    case 4:
                        identifyClass(4, parcelArray);
                        break;
                    case 5:
                        identifyClass(5, parcelArray);
                        break;
                    case 6:
                        identifyClass(6, parcelArray);
                        break;
                    case 7:
                        identifyClass(7, parcelArray);
                        break;
                    case 8:
                        identifyClass(8, parcelArray);
                        break;
                    case 9:
                        identifyClass(9, parcelArray);
                        break;
                    case 10:
                        identifyClass(10, parcelArray);
                        break;
                    case 11:
                        identifyClass(11, parcelArray);
                        break;
                }
            }
            else
            {
                switch(bayClicked)
                {
                    case 12:
                        identifyClass(0, lrParcelArray);
                        break;
                    case 13:
                        identifyClass(1, lrParcelArray);
                        break;
                    case 14:
                        identifyClass(2, lrParcelArray);
                        break;
                    case 15:
                        identifyClass(3, lrParcelArray);
                        break;
                }    
            }    
        }
        catch(NullPointerException e)
        {
            showDialog("This bay is empty.");
        }    
    }
    
    /**
     * This method identifies the subclass of a given parcel from an array knowing only the array of parcels and the position.
     * @param n The position in the array of the parcel wishing to be identified.
     * @param p The array of parcels.
     */
    private void identifyClass(int n, Parcel[] p)
    {
        currParcel = p[n];
        Class c = currParcel.getClass();
        Box currBox;
        Tube currTube;
        Envelope currEnv;
        /**
         * Store in s class name, and parse it to get to correct class of parcel
         */
        String s = c.getName();
        switch (s) 
        {
            case "dispatchdepot.Box":
                currBox = (Box) currParcel;
                updateBoxOpen(currBox);
                break;
            case "dispatchdepot.Tube":
                currTube = (Tube) currParcel;
                updateTubeOpen(currTube);
                break;
            case "dispatchdepot.Envelope":
                currEnv = (Envelope) currParcel;
                updateEnvelopeOpen(currEnv);
                break;
        }    
    }        
    
    /**
     * This method is triggered when a box is to be updated from the preceding methods.
     * @param b The box that was identified in the identifyClass() method.
     */
    private void updateBoxOpen(Box b)
    {
        /**
         * Open addBoxWindow like method addBoxOpen(), but instead taking methods from Box b to fill in current information in boxes
         */
        addBoxWindow = new JFrame("Update Box");
        JPanel bPanel = new JPanel();
        addBoxWindow.add(bPanel);
        bPanel.setLayout(new GridBagLayout());
        JLabel bIdLabel = new JLabel("ID no:");
        bIdText = new JTextField(10);
        bIdText.setText(Integer.toString(b.getIdNum()));
        JLabel bZoneLabel = new JLabel("Zone:");
        bZoneText = new JTextField(5);
        bZoneText.setText(Integer.toString(b.getZone()));
        JLabel bWidthLabel = new JLabel("Width (in cm):");
        bWidthText = new JTextField(5);
        bWidthText.setText(Integer.toString(b.getWidth()));
        JLabel bLengthLabel = new JLabel("Length (in cm):");
        bLengthText = new JTextField(5);
        bLengthText.setText(Integer.toString(b.getLength()));
        JLabel bHeightLabel = new JLabel("Height (in cm):");
        bHeightText = new JTextField(5);
        bHeightText.setText(Integer.toString(b.getHeight()));
        updBoxConfBtn = new JButton("OK");
        updBoxConfBtn.addActionListener(this);
        updBoxCancBtn = new JButton("Cancel");
        updBoxCancBtn.addActionListener(this);
        
        addGridBagLayout(bPanel, bIdLabel, 0, 0, 1, 1, 2, true);
        addGridBagLayout(bPanel, bIdText, 1, 0, 2, 1, 2, false);
        addGridBagLayout(bPanel, bZoneLabel, 0, 1, 1, 1, 2, true);
        addGridBagLayout(bPanel, bZoneText, 1, 1, 1, 1, 2, false);
        addGridBagLayout(bPanel, bWidthLabel, 0, 2, 1, 1, 2, true); 
        addGridBagLayout(bPanel, bWidthText, 1, 2, 1, 1, 2, false);
        addGridBagLayout(bPanel, bLengthLabel, 0, 3, 1, 1, 2, true);
        addGridBagLayout(bPanel, bLengthText, 1, 3, 1, 1, 2, false);
        addGridBagLayout(bPanel, bHeightLabel, 0, 4, 1, 1, 2, true);
        addGridBagLayout(bPanel, bHeightText, 1, 4, 1, 1, 2, false);
        addGridBagLayout(bPanel, updBoxConfBtn, 1, 5, 1, 1, 2, false);
        addGridBagLayout(bPanel, updBoxCancBtn, 2, 5, 1, 1, 2, false);
        addBoxWindow.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        addBoxWindow.setResizable(false);
        addBoxWindow.setSize(300,250);
        addBoxWindow.setLocationRelativeTo(this);
        addBoxWindow.setVisible(true);
    }
    
    /**
     * This method is triggered when the user, on the addBoxWindow, has confirmed they wish to update the values with the 'OK' button, and updates the array.
     */
    private void updateBoxClose()
    {
        int idNumber = Integer.parseInt(bIdText.getText());
        int zone = Integer.parseInt(bZoneText.getText());
        int width = Integer.parseInt(bWidthText.getText());
        int length = Integer.parseInt(bLengthText.getText());
        int height = Integer.parseInt(bHeightText.getText());
        
        Box currBox = new Box(idNumber, zone, height, length, width);
        /**
         * Identify bay clicked and overwrite current parcel with new value
         */
        if(isLargeBayClicked == false)
            {    
                switch(bayClicked)
                {
                    case 0: 
                        parcelArray[0] = currBox;
                        break;
                    case 1:
                        parcelArray[1] = currBox;
                        break;
                    case 2:
                        parcelArray[2] = currBox;
                        break;
                    case 3:
                        parcelArray[3] = currBox;
                        break;
                    case 4:
                        parcelArray[4] = currBox;
                        break;
                    case 5:
                        parcelArray[5] = currBox;
                        break;
                    case 6:
                        parcelArray[6] = currBox;
                        break;
                    case 7:
                        parcelArray[7] = currBox;
                        break;
                    case 8:
                        parcelArray[8] = currBox;
                        break;
                    case 9:
                        parcelArray[9] = currBox;
                        break;
                    case 10:
                        parcelArray[10] = currBox;
                        break;
                    case 11:
                        parcelArray[11] = currBox;
                        break;
                }
            }
            else
            {
                switch(bayClicked)
                {
                    case 12:
                        lrParcelArray[0] = currBox;
                        break;
                    case 13:
                        lrParcelArray[1] = currBox;
                        break;
                    case 14:
                        lrParcelArray[2] = currBox;
                        break;
                    case 15:
                        lrParcelArray[3] = currBox;
                        break;
                }    
            }
        bayClicked = 0;
        isLargeBayClicked = false;
        currParcel = null;
        addBoxWindow.dispose();
    }        
    
    /**
     * This method is triggered when a tube is to be updated from the preceding methods.
     * @param t The tube that was identified in the method identifyClass().
     */
    private void updateTubeOpen(Tube t)
    {
        /**
         * Open addTubeWindow like method addTubeOpen(), but instead taking methods from Tube t to fill in current information in boxes
         */
        addTubeWindow = new JFrame("Update Tube");
        JPanel tPanel = new JPanel();
        addTubeWindow.add(tPanel);
        tPanel.setLayout(new GridBagLayout());
        JLabel tIdLabel = new JLabel("ID no:");
        tIdText = new JTextField(10);
        tIdText.setText(Integer.toString(t.getIdNum()));
        JLabel tZoneLabel = new JLabel("Zone:");
        tZoneText = new JTextField(5);
        tZoneText.setText(Integer.toString(t.getZone()));
        JLabel tLengthLabel = new JLabel("Length (in cm):");
        tLengthText = new JTextField(5);
        tLengthText.setText(Integer.toString(t.getLength()));
        updTubeConfBtn = new JButton("OK");
        updTubeConfBtn.addActionListener(this);
        updTubeCancBtn = new JButton("Cancel");
        updTubeCancBtn.addActionListener(this);
        
        addGridBagLayout(tPanel, tIdLabel, 0, 0, 1, 1, 2, true);
        addGridBagLayout(tPanel, tIdText, 1, 0, 2, 1, 2, false);
        addGridBagLayout(tPanel, tZoneLabel, 0, 1, 1, 1, 2, true);
        addGridBagLayout(tPanel, tZoneText, 1, 1, 1, 1, 2, false);
        addGridBagLayout(tPanel, tLengthLabel, 0, 2, 1, 1, 2, true); 
        addGridBagLayout(tPanel, tLengthText, 1, 2, 1, 1, 2, false);
        addGridBagLayout(tPanel, updTubeConfBtn, 1, 3, 1, 1, 2, false);
        addGridBagLayout(tPanel, updTubeCancBtn, 2, 3, 1, 1, 2, false);
        addTubeWindow.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        addTubeWindow.setResizable(false);
        addTubeWindow.setSize(250,200);
        addTubeWindow.setLocationRelativeTo(this);
        addTubeWindow.setVisible(true);
    }
    
    /**
     * This method is triggered when, on the addTubeWindow, the user has confirmed they wish to update the values with the 'OK' button, and updates the array.
     */
    private void updateTubeClose()
    {
        int idNumber = Integer.parseInt(tIdText.getText());
        int zone = Integer.parseInt(tZoneText.getText());
        int length = Integer.parseInt(tLengthText.getText());
        
        Tube currTube = new Tube(idNumber, zone, length);
        
        /**
         * Identify bay clicked and overwrite current parcel with new value
         */
        if(isLargeBayClicked == false)
            {    
                switch(bayClicked)
                {
                    case 0: 
                        parcelArray[0] = currTube;
                        break;
                    case 1:
                        parcelArray[1] = currTube;
                        break;
                    case 2:
                        parcelArray[2] = currTube;
                        break;
                    case 3:
                        parcelArray[3] = currTube;
                        break;
                    case 4:
                        parcelArray[4] = currTube;
                        break;
                    case 5:
                        parcelArray[5] = currTube;
                        break;
                    case 6:
                        parcelArray[6] = currTube;
                        break;
                    case 7:
                        parcelArray[7] = currTube;
                        break;
                    case 8:
                        parcelArray[8] = currTube;
                        break;
                    case 9:
                        parcelArray[9] = currTube;
                        break;
                    case 10:
                        parcelArray[10] = currTube;
                        break;
                    case 11:
                        parcelArray[11] = currTube;
                        break;
                }
            }
            else
            {
                switch(bayClicked)
                {
                    case 12:
                        lrParcelArray[0] = currTube;
                        break;
                    case 13:
                        lrParcelArray[1] = currTube;
                        break;
                    case 14:
                        lrParcelArray[2] = currTube;
                        break;
                    case 15:
                        lrParcelArray[3] = currTube;
                        break;
                }    
            }
        bayClicked = 0;
        isLargeBayClicked = false;
        currParcel = null;
        addTubeWindow.dispose();
    }        
    
    /**
     * This method is triggered when an envelope is to be updated from the preceding methods.
     * @param e The envelope that was identified in the method identifyClass().
     */
    private void updateEnvelopeOpen(Envelope e)
    {
        /**
         * Open addEnvWindow like method addEnvelopeOpen(), but instead taking methods from Envelope e to fill in current information in boxes
         */
        addEnvWindow = new JFrame("Update Envelope");
        JPanel ePanel = new JPanel();
        addEnvWindow.add(ePanel);
        ePanel.setLayout(new GridBagLayout());
        JLabel eIdLabel = new JLabel("ID no:");
        eIdText = new JTextField(10);
        eIdText.setText(Integer.toString(e.getIdNum()));
        JLabel eZoneLabel = new JLabel("Zone:");
        eZoneText = new JTextField(5);
        eZoneText.setText(Integer.toString(e.getZone()));
        JLabel eSizeLabel = new JLabel("Size:");
        String[] sizeChoices = {"Small", "Medium", "Large"};
        eSizeCBox = new JComboBox(sizeChoices);
        eSizeCBox.setSelectedIndex(e.getSize() - 1);
        updEnvConfBtn = new JButton("OK");
        updEnvConfBtn.addActionListener(this);
        updEnvCancBtn = new JButton("Cancel");
        updEnvCancBtn.addActionListener(this);

        addGridBagLayout(ePanel, eIdLabel, 0, 0, 1, 1, 2, true);
        addGridBagLayout(ePanel, eIdText, 1, 0, 2, 1, 2, false);
        addGridBagLayout(ePanel, eZoneLabel, 0, 1, 1, 1, 2, true);
        addGridBagLayout(ePanel, eZoneText, 1, 1, 1, 1, 2, false);
        addGridBagLayout(ePanel, eSizeLabel, 0, 2, 1, 1, 2, true); 
        addGridBagLayout(ePanel, eSizeCBox, 1, 2, 1, 1, 2, false);
        addGridBagLayout(ePanel, updEnvConfBtn, 1, 3, 1, 1, 2, false);
        addGridBagLayout(ePanel, updEnvCancBtn, 2, 3, 1, 1, 2, false);
        addEnvWindow.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        addEnvWindow.setResizable(false);
        addEnvWindow.setSize(250,200);
        addEnvWindow.setLocationRelativeTo(this);
        addEnvWindow.setVisible(true);
    }
    
    /**
     * This method is triggered when, on the addEnvelopeWindow, the user has confirmed they wish to update the values with the 'OK' button, and updates the array.
     */
    private void updateEnvelopeClose()
    {
        int idNumber = Integer.parseInt(eIdText.getText());
        int zone = Integer.parseInt(eZoneText.getText());
        int size = eSizeCBox.getSelectedIndex() + 1;
        
        Envelope currEnv = new Envelope(idNumber, zone, size);
        
        /**
         * Identify bay clicked and overwrite current parcel with new value
         */
        if(isLargeBayClicked == false)
            {    
                switch(bayClicked)
                {
                    case 0: 
                        parcelArray[0] = currEnv;
                        break;
                    case 1:
                        parcelArray[1] = currEnv;
                        break;
                    case 2:
                        parcelArray[2] = currEnv;
                        break;
                    case 3:
                        parcelArray[3] = currEnv;
                        break;
                    case 4:
                        parcelArray[4] = currEnv;
                        break;
                    case 5:
                        parcelArray[5] = currEnv;
                        break;
                    case 6:
                        parcelArray[6] = currEnv;
                        break;
                    case 7:
                        parcelArray[7] = currEnv;
                        break;
                    case 8:
                        parcelArray[8] = currEnv;
                        break;
                    case 9:
                        parcelArray[9] = currEnv;
                        break;
                    case 10:
                        parcelArray[10] = currEnv;
                        break;
                    case 11:
                        parcelArray[11] = currEnv;
                        break;
                }
            }
            else
            {
                switch(bayClicked)
                {
                    case 12:
                        lrParcelArray[0] = currEnv;
                        break;
                    case 13:
                        lrParcelArray[1] = currEnv;
                        break;
                    case 14:
                        lrParcelArray[2] = currEnv;
                        break;
                    case 15:
                        lrParcelArray[3] = currEnv;
                        break;
                }    
            }
        bayClicked = 0;
        isLargeBayClicked = false;
        currParcel = null;
        addEnvWindow.dispose();
    }        

    /**
     * The abstract method used with MouseListener to identify mouse clicks from the bays, as well as some buttons.
     * @param me The MouseEvent which has triggered this method.
     */
    @Override
    public void mouseClicked(MouseEvent me)
    {
        /**
         * Identify bay clicked and go to validateMouseAction()
         */
        if(me.getSource() == smallBay[0]) 
        {
            isLargeBayClicked = false;
            bayClicked = 0;
            validateMouseAction(me);
        }
        else if(me.getSource() == smallBay[1])
        {
            isLargeBayClicked = false;
            bayClicked = 1;
            validateMouseAction(me);
        }
        else if(me.getSource() == smallBay[2])
        {
            isLargeBayClicked = false;
            bayClicked = 2;
            validateMouseAction(me);
        }
        else if(me.getSource() == smallBay[3])
        {
            isLargeBayClicked = false;
            bayClicked = 3;
            validateMouseAction(me);
        }
        else if(me.getSource() == smallBay[4])
        {
            isLargeBayClicked = false;
            bayClicked = 4;
            validateMouseAction(me);
        }
        else if(me.getSource() == smallBay[5])
        {
            isLargeBayClicked = false;
            bayClicked = 5;
            validateMouseAction(me);
        }
        else if(me.getSource() == smallBay[6])
        {
            isLargeBayClicked = false;
            bayClicked = 6;
            validateMouseAction(me);
        }
        else if(me.getSource() == smallBay[7])
        {
            isLargeBayClicked = false;
            bayClicked = 7;
            validateMouseAction(me);
        }
        else if(me.getSource() == smallBay[8])
        {
            isLargeBayClicked = false;
            bayClicked = 8;
            validateMouseAction(me);
        }
        else if(me.getSource() == smallBay[9])
        {
            isLargeBayClicked = false;
            bayClicked = 9;
            validateMouseAction(me);
        }
        else if(me.getSource() == smallBay[10])
        {
            isLargeBayClicked = false;
            bayClicked = 10;
            validateMouseAction(me);
        }
        else if(me.getSource() == smallBay[11])
        {
            isLargeBayClicked = false;
            bayClicked = 11;
            validateMouseAction(me);
        }
        else if(me.getSource() == largeBay[0])
        {
            isLargeBayClicked = true;
            bayClicked = 12;
            validateMouseAction(me);
        }
        else if(me.getSource() == largeBay[1])
        {
            isLargeBayClicked = true;
            bayClicked = 13;
            validateMouseAction(me);
        }
        else if(me.getSource() == largeBay[2])
        {
            isLargeBayClicked = true;
            bayClicked = 14;
            validateMouseAction(me);
        }
        else if(me.getSource() == largeBay[3])
        {
            isLargeBayClicked = true;
            bayClicked = 15;
            validateMouseAction(me);
        }
        /**
         * If buttons are clicked, execute these methods
         */
        else if(me.getSource() == currCharButton)
        { 
            calcCurrCharge();
        }
        else if(me.getSource() == totCharButton)
        {
            calcTotalCharge();
        }
        else if(me.getSource() == clearButton)
        {
            clearAll();
        }            
    }
    
    /**
     * This method validates which button was used to click on the bays to identify the proper method to be used.
     * @param me The MouseEvent which has triggered this method.
     */
    private void validateMouseAction(MouseEvent me)
    {
        /**
         * This takes the MouseEvent and finds which button was pressed on the panel. It then executes the appropriate method.
         */
        switch(me.getButton())
        {
            case MouseEvent.BUTTON1:
                displayDetails();
                break;
            case MouseEvent.BUTTON2:
                updateParcel();
                break;
            case MouseEvent.BUTTON3:               
                removeParcelOpen();
                break;
            default:                
                break;    
        }
    }  
    
    /**
     * One of the MouseListener methods not used, but needed to implement the method.
     * @param me The MouseEvent which has triggered this method.
     */
    @Override
    public void mousePressed(MouseEvent me)
    {
        
    }
    
    /**
     * One of the MouseListener methods not used, but needed to implement the method.
     * @param me The MouseEvent which has triggered this method.
     */
    @Override
    public void mouseReleased(MouseEvent me)
    {
        
    }
    
    /**
     * One of the MouseListener methods not used, but needed to implement the method.
     * @param me The MouseEvent which has triggered this method.
     */
    @Override
    public void mouseEntered(MouseEvent me)
    {
        
    }

    /**
     * One of the MouseListener methods not used, but needed to implement the method.
     * @param me The MouseEvent which has triggered this method.
     */
    @Override
    public void mouseExited(MouseEvent me)
    {
        
    }

    /**
     * This abstract method, used with ActionListener, is used to identify the action after a button has been pressed.
     * @param ae The ActionEvent which has triggered this method.
     */
    @Override
    public void actionPerformed(ActionEvent ae)
    {
        /**
         * If a button is pressed, execute the appropriate method.
         */
        if(ae.getSource() == loadItem)
        {
            loadOpen();
        }
        else if(ae.getSource() == loadConfirmBtn)
        {
            loadClose();
        }        
        else if(ae.getSource() == saveItem)
        {
            saveOpen();
        }
        else if(ae.getSource() == saveConfirmBtn)
        {
            saveClose();
        }        
        else if(ae.getSource() == addBoxItem)
        {
            addBoxOpen();
        }
        else if(ae.getSource() == addBoxConfBtn)
        {
            addBoxClose();
        }        
        else if(ae.getSource() == addTubeItem)
        {
            addTubeOpen();
        }
        else if(ae.getSource() == addTubeConfBtn)
        {
            addTubeClose();
        }    
        else if(ae.getSource() == addEnvItem)
        {
            addEnvelopeOpen();
        }
        else if(ae.getSource() == addEnvConfBtn)
        {
            addEnvelopeClose();
        }    
        else if(ae.getSource() == diaConfBtn)
        {
            dialogWindow.dispose();
        }
        else if(ae.getSource() == currCharConfBtn)
        {
            currCharWindow.dispose();
        }
        else if(ae.getSource() == totCharConfBtn)
        {
            totCharWindow.dispose();
        }
        else if(ae.getSource() == bayEmptyConfBtn)
        {
            bayEmptyWindow.dispose();
        }
        else if(ae.getSource() == dispDetaConfBtn)
        {
            dispDetailsWindow.dispose();
        }
        else if(ae.getSource() == remParConfBtn)
        {
            removeParcelClose();
        }
        else if(ae.getSource() == updBoxConfBtn)
        {
            updateBoxClose();
        }
        else if(ae.getSource() == updTubeConfBtn)
        {
            updateTubeClose();
        }
        else if(ae.getSource() == updEnvConfBtn)
        {
            updateEnvelopeClose();
        }
        else if(ae.getSource() == loadCancelBtn)
        {
            loadWindow.dispose();
        }
        else if(ae.getSource() == saveCancelBtn)
        {
            saveWindow.dispose();
        }
        else if(ae.getSource() == addBoxCancBtn)
        {
            addBoxWindow.dispose();
        }
        else if(ae.getSource() == addTubeCancBtn)
        {
            addTubeWindow.dispose();
        }
        else if(ae.getSource() == addEnvCancBtn)
        {
            addEnvWindow.dispose();
        }
        else if(ae.getSource() == updBoxCancBtn)
        {
            addBoxWindow.dispose();
        }
        else if(ae.getSource() == updTubeCancBtn)
        {
            addTubeWindow.dispose();
        }
        else if(ae.getSource() == updEnvCancBtn)
        {
            addEnvWindow.dispose();
        }
        else if(ae.getSource() == remParCancBtn)
        {
            removeParcelWindow.dispose();
        }    
    }
   
}
