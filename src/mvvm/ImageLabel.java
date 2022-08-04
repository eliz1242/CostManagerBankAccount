package mvvm;

import javax.swing.*;

/**
 * this class is for creating an image object on the window.
 */
class ImageLabel extends JLabel {

    public ImageLabel(String img) {
        this(new ImageIcon(img));
    }

    public ImageLabel(ImageIcon icon) {
        setIcon(icon);
        setIconTextGap(0);
        setBorder(null);
        setText(null);
    }
}
