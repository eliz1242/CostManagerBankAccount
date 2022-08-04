package mvvm;

import javax.swing.*;

public class Main {

    public static void main(String args[]) throws CostManagerException {

        ProgramView view = new ProgramView();
        view.setTitle("Login Form");
        view.setVisible(true); //makes the window visible
        view.setBounds(10, 10, 460, 400);
        view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        view.setLocationRelativeTo(null);//makes the default window position to the center of the screen
        view.setResizable(false);//makes the window not resizable
        IModel model = new DBModel();
        IViewModel vm = new ViewModel();
        vm.setModel(model);
        vm.setView(view);
        view.setIViewModel(vm);
    }
}
