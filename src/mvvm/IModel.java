package mvvm;

import javax.swing.table.DefaultTableModel;
import java.util.List;

/**
 * this interface represents the Model in the MVVM architecture.
 * this will be the class that is sending queries to the database
 * and sends back data to the view threw the view-model.
 */
public interface IModel {
    void addItem(Item item) throws CostManagerException;
    DefaultTableModel getItems(String userName) throws CostManagerException;
    DefaultTableModel getItemsDates(Item item, String userName) throws CostManagerException;
    boolean addUser(Item item) throws CostManagerException;
    boolean checkUserPass(Item item) throws CostManagerException;
    void addCategory(Item item) throws CostManagerException;
    List<String> getCatItems() throws CostManagerException;
    List<String> getCurrItems() throws CostManagerException;
}
