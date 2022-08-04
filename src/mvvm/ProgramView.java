package mvvm;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Objects;

public class ProgramView extends JFrame implements IView, ActionListener {
    private JCheckBox showPassword;
    private JPasswordField passwordField;
    private JLabel itemName, chooseCat, itemCost, itemCurr, userNameLabel, passwordLabel;
    private JFrame frame;
    private JTextField itemNameTF, costTF, currencyTF, tfMessage, addCategoryTextField, dateTF, userNameTextField;
    private JComboBox<String> categoryList, currList;
    private JButton btAddItem, btDateCheck, btAddCategory, loginButton, signUpButton, resetButton, currenciesButton;
    private JTextArea ta;
    private JPanel panelWest, panelNorth, panelCenter;
    private IViewModel vm;
    private Font font1;
    private String userName, msg;
    private JTable table;
    private DefaultTableModel data;
    ImageLabel image;
    Container container = this.getContentPane();

    /**
     * initializes all the objects of the window
     */
    public ProgramView() {
        userNameLabel = new JLabel("USERNAME");
        passwordLabel = new JLabel("PASSWORD");
        userNameTextField = new JTextField();
        signUpButton = new JButton("SIGN UP");
        passwordField = new JPasswordField();
        showPassword = new JCheckBox("Show Password");
        loginButton = new JButton("LOGIN");
        resetButton = new JButton("RESET");
        currenciesButton = new JButton("Currencies");
        image = new ImageLabel(new ImageIcon("C:\\Users\\almog\\IdeaProjects\\MVVM\\title.png"));
        setLayoutManager();
        setLocationAndSize();
        addComponentsToContainer();
        addActionEvent();
    }

    /**
     * sets the layout to null
     */
    public void setLayoutManager() {
        container.setLayout(null);
    }

    /**
     * sets the size of the objects
     */
    public void setLocationAndSize() {
        userNameLabel.setBounds(90, 150, 100, 30);
        passwordLabel.setBounds(90, 220, 100, 30);
        userNameTextField.setBounds(180, 150, 180, 30);
        passwordField.setBounds(180, 220, 180, 30);
        showPassword.setBounds(180, 250, 150, 30);
        loginButton.setBounds(50, 300, 100, 30);
        signUpButton.setBounds(290, 300, 100, 30);
        resetButton.setBounds(170, 300, 100, 30);
        image.setBounds(75, -426,1000,1000);
    }

    /**
     * adds the buttons and text fields to the login window
     *
     */
    public void addComponentsToContainer() {
        container.add(userNameLabel);
        container.add(passwordLabel);
        container.add(userNameTextField);
        container.add(signUpButton);
        container.add(passwordField);
        container.add(showPassword);
        container.add(loginButton);
        container.add(resetButton);
        container.add(image);
    }

    /**
     * sets action listners to the login window buttons
     */
    public void addActionEvent() {
        loginButton.addActionListener(this);
        resetButton.addActionListener(this);
        signUpButton.addActionListener(this);
        showPassword.addActionListener(this);
    }

    /**
     * sets action listeners for all the login buttons
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) { // login button pressed
            userName = userNameTextField.getText();
            System.out.println("Login---"+ userName);
            Item item = new Item(userNameTextField.getText() + "," + passwordField.getText() + " ");
            try {
                boolean successful = vm.checkUserPass(item); //true if username and password are matching in DB
                if(successful) {
                    JOptionPane.showMessageDialog(this, "Welcome Back!");
                    System.out.println("Loged In!");
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            dispose();
                            init(userName);
                            start();
                        }
                    });
                }
                else {
                    JOptionPane.showMessageDialog(this, "Invalid Username or Password");
                }
            } catch (CostManagerException ex) {
                JOptionPane.showMessageDialog(this, "Invalid Username or Password");
                ex.printStackTrace();
            }

        }
        if (e.getSource() == resetButton) {
            userNameTextField.setText("");
            passwordField.setText("");
        }
        if (e.getSource() == showPassword) {
            if (showPassword.isSelected()) {
                passwordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar('*');
            }

        }
        if (e.getSource() == signUpButton){ // signup button pressed
            Item item = new Item("'" + userNameTextField.getText() + "', '" + passwordField.getText() + "'");
            this.userName = userNameTextField.getText();
            try {
                boolean successful = vm.addUser(item); // true if username doesnt exist in DB
                if(successful) {
                    System.out.println("UserName is available!");
                    JOptionPane.showMessageDialog(this, "Welcome!");
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            dispose();
                            init(userName);
                            start();
                        }
                    });

                }
            } catch (CostManagerException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * initializes all the objects in the main program window
     */
    public void init(String userName) {
        this.userName = userName;
        dateTF =  new HintTextField("'yyyy-mm-dd' AND 'yyyy-mm-dd'");
        itemName = new JLabel("  item name:");
        chooseCat = new JLabel("  choose category:");
        itemCost = new JLabel("  item cost:");
        itemCurr = new JLabel("  currency:");
        frame = new JFrame();
        categoryList = new JComboBox<>();
        currList = new JComboBox<>();
        itemNameTF = new JTextField(18);
        costTF = new JTextField(18);
        currencyTF = new JTextField(18);
        addCategoryTextField = new HintTextField("  Category name");
        tfMessage = new JTextField();
        btAddItem = new JButton("Add item");
        btDateCheck = new JButton("show dates between:");
        btAddCategory = new JButton("add category");
        ta = new JTextArea();
        panelWest = new JPanel();
        panelNorth = new JPanel();
        panelCenter = new JPanel();
    }

    /**
     * adds all the objects to the panels and main frame of the program
     * sets action listeners to the buttons of the program
     */
    public void start() {
        font1 = new Font("SansSerif", Font.BOLD, 18);
        Font fontBtn = new Font("SansSerif", Font.BOLD, 18);
        panelNorth.setBackground(Color.LIGHT_GRAY);
        panelNorth.setLayout(new FlowLayout());
        tfMessage.setColumns(30);
        panelNorth.add(tfMessage);
        panelWest.setLayout(new GridLayout(7,2,10, 10));
        panelWest.add(addCategoryTextField);
        panelWest.add(btAddCategory);
        panelCenter.setBackground(Color.LIGHT_GRAY);
        panelCenter.setLayout(new GridLayout(1,1));
        btAddItem.setFont(fontBtn);
        btDateCheck.setFont(fontBtn);
        panelWest.add(btDateCheck);
        dateTF.setFont(font1);
        costTF.setFont(font1);
        currencyTF.setFont(font1);
        itemName.setFont(font1);
        itemCost.setFont(font1);
        itemCurr.setFont(font1);
        panelWest.add(dateTF);
        panelWest.add(chooseCat);
        panelWest.add(categoryList);
        panelWest.add(itemName);
        panelWest.add(itemNameTF);
        panelWest.add(itemCost);
        panelWest.add(costTF);
        panelWest.add(itemCurr);
        panelWest.add(currList);
        panelWest.add(btAddItem);
        panelWest.add(currenciesButton);
        currenciesButton.setFont(font1);
        chooseCat.setFont(font1);
        itemNameTF.setFont(font1);
        panelWest.setBackground(Color.LIGHT_GRAY);
        addCategoryTextField.setFont(font1);
        addCategoryTextField.setToolTipText("Cost");
        btAddCategory.setFont(fontBtn);
        categoryList.setFont(fontBtn);
        currList.setFont(fontBtn);
        frame.setLayout(new BorderLayout());
        frame.add(panelWest, BorderLayout.WEST);
        frame.add(panelCenter, BorderLayout.EAST);
        frame.add(panelNorth,BorderLayout.NORTH);
        frame.setBounds(10, 10, 1040, 450);
        vm.getItems(this.userName);
        frame.setVisible(true);
        frame.setResizable(false);
        ta.setFont(ta.getFont().deriveFont(25f));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);//positioning the window to center of the screen;

        btAddItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("inside actionPerformed... " + Thread.currentThread().getName());
                String selectedCategory = String.valueOf(categoryList.getSelectedItem());
                String selectedCurr = String.valueOf(currList.getSelectedItem());
                boolean isNumeric = costTF.getText().chars().allMatch( Character::isDigit );//checks if cost value is a number
                if(categoryList.getSelectedIndex() == -1){
                    System.out.println("invalid category");
                    tfMessage.setText("please select a category");//works
                    return;
                }
                if(Objects.equals(itemNameTF.getText(), "")){
                    System.out.println("invalid item name");
                    tfMessage.setText("please enter a name for the item");
                    return;
                }
                if(!isNumeric || Objects.equals(costTF.getText(), "")){
                    System.out.println("invalid cost");
                    tfMessage.setText("please enter a valid cost");//works
                    return;
                }
                if(currList.getSelectedIndex() == -1){
                    System.out.println("invalid currency");
                    tfMessage.setText("please select a currency");//works
                    return;
                }

                String temp = "'" + itemNameTF.getText() + "', '" + selectedCategory + "', '" + costTF.getText() + "', '" + selectedCurr + "'";
                Item item = new Item(temp);
                vm.addItem(item);
            }
        });

        btDateCheck.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Item item = new Item(dateTF.getText());
                vm.getItemBetween(item, userName);
            }
        });

        btAddCategory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Item item = new Item(addCategoryTextField.getText());
                vm.addCategory(item);
                JPanel panel = new JPanel();
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                panel.setOpaque(true);
                frame.setVisible(true);
            }
        });

        currenciesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JLabel currencyTitle, dollar, PoundSterling, Euro, CanadianDollar;
                JFrame frame = new JFrame("currencies");
                currencyTitle = new JLabel("  currency                 value");
                currencyTitle.setFont(font1);
                dollar = new JLabel("  US Dollar                  3.14");
                dollar.setFont(font1);
                PoundSterling = new JLabel("  PoundSterling          4.26");
                PoundSterling.setFont(font1);
                Euro = new JLabel("  Euro                         3.56");
                Euro.setFont(font1);
                CanadianDollar = new JLabel("  CanadianDollar         1.44");
                CanadianDollar.setFont(font1);
                frame.setBounds(100,100,250,300);

                frame.getContentPane().setLayout(new GridLayout(5,1));
                frame.add(currencyTitle);
                frame.add(dollar);
                frame.add(PoundSterling);
                frame.add(Euro);
                frame.add(CanadianDollar);
                frame.setLocationRelativeTo(null);//positioning the window to center of the screen;
                frame.setVisible(true);

            }
        });
    }

    /**
     * shows the category list from the DB
     */
    @Override
    public void showCategories(List<String> catItems) {
        categoryList.setModel(new DefaultComboBoxModel<String>(catItems.toArray(new String[0]))); // this will assign the list of categories with the array list from the model.
        categoryList.setRenderer(new MyComboBoxRenderer("  category"));//puts hint "choose category" on the category list.
        categoryList.setSelectedIndex(-1);
    }

    /**
     * shows the currency list from the DB
     */
    public void showCurr(List<String> currItems){
        currList.setModel(new DefaultComboBoxModel<String>(currItems.toArray(new String[0])));
        currList.setRenderer(new MyComboBoxRenderer("  currency"));
        currList.setSelectedIndex(-1);
    }

    /**
     * shows the items from the main table from the DB.
     * this func is invoked when the program is starting and when a new item is added
     */
    @Override
    public void showItems(DefaultTableModel items) {
        this.data = items;
        System.out.println("programView showItems Log!  the table has " + data.getRowCount() + " rows.");
        if(table == null){
            table = new JTable(data);
            table.setFont(new Font("TRUETYPE_FONT", Font.BOLD, 15));
            panelCenter.add(new JScrollPane(table), BorderLayout.CENTER);
            showMessage(new Message("your items"));
            table.setRowHeight(27);
            resizeColumnWidth(table);//Auto resizing the JTable column widths
            frame.setVisible(true);
        }
        else {
            table.setModel(data);
            table.setRowHeight(27);
            resizeColumnWidth(table);//Auto resizing the JTable column widths
            frame.setVisible(true);
        }
    }

    /**
     * adjusting the columns widths according to the strings inside
     */
    public void resizeColumnWidth(JTable table) {
        final TableColumnModel columnModel = table.getColumnModel();
        for (int column = 0; column < table.getColumnCount(); column++) {
            int width = 15; // Min width
            for (int row = 0; row < table.getRowCount(); row++) {
                TableCellRenderer renderer = table.getCellRenderer(row, column);
                Component comp = table.prepareRenderer(renderer, row, column);
                width = Math.max(comp.getPreferredSize().width +1 , width);
            }
            if(width > 300)
                width=300;
            columnModel.getColumn(column).setPreferredWidth(width);
        }
    }


    /**
     * this func gets a string message and shows it on top of the window
     */
    @Override
    public void showMessage(Message message) {
        this.msg = message.getText();
        tfMessage.setText(message.getText());
        tfMessage.setFont(font1);
        tfMessage.setColumns(30);
    }

    /**
     * method for the test
     * @return
     */
    public String getLatestMessage(){
        return this.msg;
    }

    /**
     * sets the viewModel
     * @param vm
     */
    @Override
    public void setIViewModel(IViewModel vm) {
        this.vm = vm;
    }

}

