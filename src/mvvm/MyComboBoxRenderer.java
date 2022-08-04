package mvvm;

import javax.swing.*;
import java.awt.*;


@SuppressWarnings("rawtypes")
class MyComboBoxRenderer extends JLabel implements ListCellRenderer {
    private final String _title;

    public MyComboBoxRenderer(String title) {
        _title = title;
    }

    /**
     * this will be the drop box of the currencies/categories
     */
    @Override
    public Component getListCellRendererComponent(JList list, Object value,
                                                  int index, boolean isSelected, boolean hasFocus) {
        if (index == -1 && value == null) setText(_title);
        else setText(value.toString());
        return this;
    }
}
