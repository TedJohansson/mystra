package com.mystra.controller;

import com.jfoenix.controls.JFXListView;
import com.mystra.service.ActivityDayService;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import com.mystra.model.ActivityItem;


import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Optional;

public class RootLayoutController {
    private ActivityDayService activityDayService = new ActivityDayService();
    @FXML
    private JFXListView<ActivityItem> activityItemListView;
    @FXML
    private TextArea itemDetailsTextArea;
    @FXML
    private BorderPane mainBoarderPane;

    public void initialize() {
        activityItemListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ActivityItem>() {
            @Override
            public void changed(ObservableValue<? extends ActivityItem> observableValue, ActivityItem activityItem, ActivityItem t1) {
                if(t1 != null) {
                    handleChangeListView();
                }
            }
        });
        loadActivityItemsToListView();

        activityItemListView.setCellFactory(new Callback<>() {
            @Override
            public ListCell<ActivityItem> call(ListView<ActivityItem> activityItemListView) {
                ListCell<ActivityItem> cell = new ListCell<>() {
                    @Override
                    protected void updateItem(ActivityItem item, boolean empty) {
                        super.updateItem(item, empty);
                        if(empty) {
                            setText(null);
                        } else {
                            int currentHour = LocalDateTime.now().getHour();
                            if(item.getHourOfDay() == currentHour) {
                                setTextFill(Color.web("#ff4c4c"));
                            } else if(item.getHourOfDay() > currentHour - 2 && item.getHourOfDay() < currentHour + 2 ) {
                                setTextFill(Color.web("#f9c57c"));
                            }
                            setText(String.format("%02d-%02d: %s",
                                    item.getHourOfDay(),
                                    item.getHourOfDay() + 1,
                                    item.getShortDescription()));
                        }
                    }
                };
                return cell;
            }
        });
    }

    @FXML
    private void showUpdateItemDialog(ActivityItem selectedActivityItem) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBoarderPane.getScene().getWindow());
        dialog.setTitle(String.format("Update (%s)", selectedActivityItem.getShortDescription()));
        dialog.setHeaderText("Use this dialog to update an activity");

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/com/mystra/view/activityItemDialog.fxml"));
        DialogController controller;
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
            controller = fxmlLoader.getController();
            controller.setActivityItem(selectedActivityItem);
        } catch (IOException e) {
            System.out.println("Couln't load dialog");
            e.printStackTrace();
            return;
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            ActivityItem updatedItem = controller.processResults();
            loadActivityItemsToListView();
            activityItemListView.getSelectionModel().select(updatedItem);
        }

    }

    @FXML
    public void handleKeyPressed(KeyEvent keyEvent) {
        ActivityItem selectedItem = activityItemListView.getSelectionModel().getSelectedItem();
        if(selectedItem != null) {
            if (keyEvent.getCode().equals(KeyCode.E) || keyEvent.getCode().equals(KeyCode.ENTER)) {
                showUpdateItemDialog(selectedItem);
            }
        }
    }

    private void handleChangeListView() {
        ActivityItem item = activityItemListView.getSelectionModel().getSelectedItem();
        itemDetailsTextArea.setText(item.getDetails());
    }

    private void loadActivityItemsToListView() {
        ObservableList<ActivityItem> activityItems = FXCollections.observableArrayList(
                activityDayService
                        .getTodaysActivityDay()
                        .getActivities());
        SortedList<ActivityItem> sortedList = new SortedList<>(activityItems,
                Comparator.comparing(ActivityItem::getHourOfDay));

        activityItemListView.setItems(sortedList);
        activityItemListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }
}
