package tests;

import mvvm.Message;
import mvvm.ProgramView;
import mvvm.ViewModel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProgramViewTest {

    @Test
    void showMessage() {
        ProgramView programView = new ProgramView();
        ViewModel vm = new ViewModel();
        programView.setIViewModel(vm);
        programView.init("username");
        programView.start();
        programView.showMessage(new Message("message test"));
        assertEquals("message test", programView.getLatestMessage());
    }

}