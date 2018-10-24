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
    private ActivityItem activityItem;
    @FXML
    private TextField shortDescriptionField;
    @FXML
    private TextArea detailsArea;

    public ActivityItem processResults() {
        activityItem.setShortDescription(shortDescriptionField.getText().trim());
        activityItem.setDetails(detailsArea.getText().trim());
        activityItemService.update(activityItem);

        return activityItem;
    }

    public void setActivityItem(ActivityItem selectedActivityItem) {
        activityItem = selectedActivityItem;
        shortDescriptionField.setText(activityItem.getShortDescription());
        detailsArea.setText(activityItem.getDetails());

    }
}
