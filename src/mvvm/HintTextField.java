package mvvm;

import javax.swing.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * this class is for the creating a text field with a hint inside
 */
class HintTextField extends JTextField implements FocusListener {

    private final String hint;
    private boolean showingHint;

    /**
     * constructor for this class
     * sets the hint and showingHint fields
     * @param hint is the hint string
     */
    public HintTextField(final String hint) {
        super(hint);
        this.hint = hint;
        this.showingHint = true;
        super.addFocusListener(this);
    }

    /**
     * making the hint empty if no hint was set
     * @param e
     */
    @Override
    public void focusGained(FocusEvent e) {
        if (this.getText().isEmpty()) {
            super.setText("");
            showingHint = false;
        }
    }

    /**
     * THIS FUNCTION MAKE THE HINT VISIBLE IF NEEDED
     * @param e
     */
    @Override
    public void focusLost(FocusEvent e) {
        if (this.getText().isEmpty()) {
            super.setText(hint);
            showingHint = true;
        }
    }

    /**
     * GETTING THE TEXTFIELD
     * @return EMPTY FIELD OR A HINT FIELD
     */
    @Override
    public String getText() {
        return showingHint ? "" : super.getText();
    }
}
