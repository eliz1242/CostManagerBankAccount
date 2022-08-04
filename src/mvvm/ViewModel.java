package mvvm;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ViewModel implements IViewModel {

    private IModel model;
    private IView view;
    private ExecutorService service;
    private String userName, latestDates;

    /**
     * sets a new thread-pool.
     */
    public ViewModel() {
        this.service = Executors.newFixedThreadPool(3);
    }

    /**
     * sets the view for this viewModel
     * @param view
     */
    @Override
    public void setView(IView view) {
        this.view = view;
    }

    /**
     * sets the model for this viewModel
     * @param model
     */
    @Override
    public void setModel(IModel model) {
        this.model = model;
    }

    /**
     * invoked from the view class and calls the addItem method from the model class.
     * adds a new item to the items' table in the DB
     * @param item
     */
    @Override
    public void addItem(Item item) {
        service.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    model.addItem(item);
                    DefaultTableModel data = model.getItems(userName);
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            view.showMessage(new Message("item was added"));
                            view.showItems(data);//tells the view to show the latest items table
                        }
                    });
                } catch(CostManagerException e) {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            view.showMessage(new Message(e.getMessage()));
                        }
                    });
                }
            }
        });
    }

    /**
     * invoked from the view class and calls the getItemBetween method from the model class.
     * tells the model to get only the relevant items from the DB
     * @param item is the dates
     * @param userName currently user in use
     */
    @Override
    public void getItemBetween(Item item, String userName) {
        this.latestDates = item.getText();
        service.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    DefaultTableModel items = model.getItemsDates(item, userName);
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            view.showMessage(new Message("purchases in dates selected"));
                            view.showItems(items);//tells the view to show the latest items table
                        }
                    });
                } catch(CostManagerException e) {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            view.showMessage(new Message("problem with fetching the items from the model"));
                        }
                    });
                }
            }
        });
    }

    /**
     * method for test
     * @return the latest dates request
     */
    public String getLatestDates(){
        return latestDates;
    }

    /**
     * invoked from the view class and calls the getItems method from the model class.
     * gets all the items for a specific user from the model
     * @param userName currently user in use
     */
    @Override
    public void getItems(String userName) {
        this.userName = userName;
        service.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    DefaultTableModel items = model.getItems(userName);
                    List<String> CategoryData = model.getCatItems();
                    List<String> finalCategoryData = CategoryData;
                    List<String> CurrList = model.getCurrItems();
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            view.showItems(items);
                            view.showCategories(finalCategoryData);
                            view.showCurr(CurrList);
                        }
                    });
                } catch(CostManagerException e) {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            view.showMessage(new Message("problem with fetching"));
                        }
                    });
                }
            }
        });
    }

    /**
     * invokes the addUser method in the model class
     * @param item is the userName and password
     * @return true if a new user was added to the DB
     * @throws CostManagerException is the exception sent back from the model
     * @throws SQLException is being thrown when there's a problem
     */
    public boolean addUser(Item item) throws CostManagerException {
        boolean exist;
        exist = model.addUser(item);
        if(!exist){
            view.showMessage(new Message("UserName already exist!"));
            return false;
        }
        return true;
    }

    /**
     * invokes the checkUserPass method in the model class
     * @param item is the userName and password
     * @return true if theres a matching userName and password in the DB
     * @throws CostManagerException is the exception sent back from the model
     * @throws SQLException is being thrown when there's a problem
     */
    public boolean checkUserPass(Item item) throws CostManagerException {
        boolean correct;
        correct = model.checkUserPass(item);
        if(!correct){
            return false;
        }
        return true;
    }

    /**
     * adds a new category to the category list in the DB
     * @param item is the name of the new category
     */
    @Override
    public void addCategory(Item item) {
        service.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    model.addCategory(item);
                    List<String> CategoryData = new ArrayList<String>();
                    CategoryData = model.getCatItems();
                    List<String> finalCategoryData = CategoryData;
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            view.showMessage(new Message("Category was added"));
                            view.showCategories(finalCategoryData);
                        }
                    });
                } catch(CostManagerException e) {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            view.showMessage(new Message(e.getMessage()));
                        }
                    });
                }
            }
        });
    }
}

