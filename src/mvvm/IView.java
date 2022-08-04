package mvvm;

import javax.swing.table.DefaultTableModel;
import java.util.List;

/**
 * this interface represents the View in the MVVM architecture.
 * this will be the class that will show data on the screen/window
 */
public interface IView {
    void showItems(DefaultTableModel items);
    void showMessage(mvvm.Message message);
    void setIViewModel(IViewModel vm);
    void init(String userName);
    void start();
    void showCategories(List<String> finalCategoryData);
    void showCurr(List<String> currItems);

}
