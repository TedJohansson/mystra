package mystra;

import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import mystra.datamodel.ActivityDay;
import mystra.datamodel.ActivityItem;

public class DialogController {
    @FXML
    private TextField shortDescriptionField;
    @FXML
    private TextArea detailsArea;
    @FXML
    private Slider hourOfDaySlider;

    public ActivityItem processResults() {
        String shordDescription = shortDescriptionField.getText().trim();
        String details = detailsArea.getText().trim();
        int hourOfDayValue = (int) hourOfDaySlider.getValue();
        ActivityItem item = new ActivityItem(shordDescription, details, hourOfDayValue);
        ActivityDay.getInstance().addTodoItem(item);
        return item;
    }
}
