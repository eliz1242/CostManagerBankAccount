package mvvm;

/**
 * this class represents a message
 */
public class Message {
    private String content;

    public Message(String content) {
        this.content = content;
    }

    /**
     * @return the string of the message
     */
    public String getText() {
        return content;
    }
}
