package com.mystra.controller;

import com.mystra.model.ActivityDay;
import com.mystra.model.ActivityItem;
import com.mystra.service.ActivityDayService;
import com.mystra.service.ActivityItemService;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class DialogController {
    private ActivityDayService activityDayService = new ActivityDayService();
    private ActivityItemService activityItemService = new ActivityItemService();
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
        ActivityDay activityDay = activityDayService.getTodaysActivityDay();
        ActivityItem item = new ActivityItem(shordDescription, details, hourOfDayValue, activityDay);
        activityItemService.persist(item);

        return item;
    }
}
