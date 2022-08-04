package mvvm;

/**
 * this interface represents the ViewModel in the MVVM architecture.
 * this will be the connector between the view and the model.
 */
public interface IViewModel {
    void setView(IView view);
    void setModel(IModel model);
    void addItem(Item item);
    void getItems(String userName);
    void getItemBetween(Item item, String userName);
    boolean addUser(Item item) throws CostManagerException;
    boolean checkUserPass(Item item) throws CostManagerException;
    void addCategory(Item item);

}
