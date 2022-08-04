package tests;

import mvvm.*;
import org.junit.jupiter.api.Test;

import javax.swing.table.DefaultTableModel;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class SimpleDBModelTest {

    @Test
    void getItemsDates() throws CostManagerException {
        ViewModel vm = new ViewModel();
        ProgramView programView = new ProgramView();
        DBModel model = new DBModel();
        vm.setModel(model);
        vm.setView(programView);
        programView.setIViewModel(vm);
        programView.init("username");
        programView.start();
        String datesString = "'2021-12-14' AND '2021-12-16'";
        Item item = new Item(datesString);
        DefaultTableModel data = model.getItemsDates(item, "almog");
        ArrayList<String> dates = new ArrayList<String>();
        String[] firstDate = new String[3];
        firstDate[0] = datesString.substring(1,5);
        firstDate[1] = datesString.substring(6,8);
        firstDate[2] = datesString.substring(9,11);
        String[] secondDate = new String[3];
        secondDate[0] = datesString.substring(18,22);
        secondDate[1] = datesString.substring(23,25);
        secondDate[2] = datesString.substring(26,28);
        boolean[] answers = new boolean[data.getRowCount()];
        Arrays.fill(answers,false);
        for(int i = 0; i<data.getRowCount();i++){
            dates.add((String) data.getValueAt(i, 6));
            String check = dates.get(i);
            String year = check.substring(0,4);
            String month = check.substring(5,7);
            String day = check.substring(check.lastIndexOf("-")+1);
            if(Integer.parseInt(year)>=Integer.parseInt(firstDate[0]) &&
                    Integer.parseInt(year) <= Integer.parseInt(secondDate[0])){
                if(Integer.parseInt(month)>=Integer.parseInt(firstDate[1]) &&
                        Integer.parseInt(month) <= Integer.parseInt(secondDate[1])){
                    if(Integer.parseInt(day)>=Integer.parseInt(firstDate[2]) &&
                            Integer.parseInt(day) <= Integer.parseInt(secondDate[2])){
                        answers[i] = true;
                    }
                }
            }
        }

        boolean finalAnswer = true;
        for(boolean i:answers){
            if(i == false){
                finalAnswer = false;
                break;
            }
        }
        assertEquals(finalAnswer,true);

    }

}