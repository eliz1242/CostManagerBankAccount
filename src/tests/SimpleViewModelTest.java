package tests;

import mvvm.*;
import org.junit.jupiter.api.Test;

import javax.swing.table.DefaultTableModel;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class SimpleViewModelTest {

    @Test
    void addItem() throws CostManagerException {
        ViewModel vm = new ViewModel();
        ProgramView programView = new ProgramView();
        DBModel model = new DBModel();
        vm.setModel(model);
        vm.setView(programView);
        programView.setIViewModel(vm);
        programView.init("username");
        programView.start();
        String temp = "'Name', 'category', '777', 'dollar'";
        Item item = new Item(temp);
        vm.addItem(item);
        DefaultTableModel data = model.getItems("username");
        StringBuffer actualItem = new StringBuffer();
        for (int i = 1; i < 6; i++) {
            actualItem.append(data.getValueAt(data.getRowCount()-1, i).toString());
            if (i != 5) {
                actualItem.append(", ");
            }
        }
        String actualItem1 = actualItem.toString();
        System.out.println("actual: " + actualItem);
        String expectedItem = "username, Name, category, 777, dollar";
        System.out.println("expected: " + expectedItem);

        assertEquals(expectedItem, actualItem1);
    }

    @Test
    void getItemBetween() throws CostManagerException {
        ViewModel vm = new ViewModel();
        ProgramView programView = new ProgramView();
        DBModel model = new DBModel();
        vm.setModel(model);
        vm.setView(programView);
        programView.setIViewModel(vm);
        programView.init("username");
        programView.start();
        String dates = "'2021-12-14' AND '2021-12-14'";
        Item item = new Item(dates);
        vm.getItemBetween(item, "username");
        String actualDates = vm.getLatestDates();
        assertEquals(dates, actualDates);


    }

    @Test
    void addUser() throws CostManagerException, SQLException {
        ViewModel vm = new ViewModel();
        ProgramView programView = new ProgramView();
        DBModel model = new DBModel();
        vm.setModel(model);
        vm.setView(programView);
        programView.setIViewModel(vm);
        programView.init("username");
        programView.start();
        String username = "almog";
        Item item = new Item(username);
        assertEquals(false, vm.addUser(item));
        //we know that 'almog' exists in the users DB, so we expect false.
    }
}