package mvvm;

/**
 * this will be the item moving from the view to the model.
 */
public class Item {
    public Item(String content) {
        this.content = content;
    }

    private String content;

    public String getText() {
        return content;
    }
}
