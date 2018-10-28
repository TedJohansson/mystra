package com.mystra.controller;

import com.mystra.model.ActivityDay;
import com.mystra.service.ActivityDayService;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;
import com.mystra.model.ActivityItem;


import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class RootLayoutController {
    private ActivityDayService activityDayService = new ActivityDayService();
    private BitSet keyboardBitSet = new BitSet();
    private ActivityDay day;
    @FXML
    private ListView<ActivityItem> activityItemListView;
    @FXML
    private TextArea itemDetailsTextArea;
    @FXML
    private BorderPane mainBoarderPane;
    @FXML
    private Label dateLabel;
    @FXML
    private DatePicker pickDate;
    @FXML
    private Button tomorrowButton;
    @FXML
    private Button yesterdayButton;

    public void initialize() {
        activityItemListView.getSelectionModel().selectedItemProperty().addListener(
                (ChangeListener<? super ActivityItem>)(ov, oldValue, newValue) ->
                {if(newValue != null ) handleChangeListView();});

        day = activityDayService.getActivityDayByDate(LocalDate.now());
        loadActivityItemsToListView();

        pickDate.valueProperty().addListener((ov, oldValue, newValue) -> changeDay(newValue));

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
        fxmlLoader.setLocation(getClass().getResource("/view/activityItemDialog.fxml"));
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
    public void handleKeyPressedListView(KeyEvent keyEvent) {
        ActivityItem selectedItem = activityItemListView.getSelectionModel().getSelectedItem();
        if(selectedItem != null) {
            if (keyEvent.getCode().equals(KeyCode.E) || keyEvent.getCode().equals(KeyCode.ENTER)) {
                showUpdateItemDialog(selectedItem);
            }
        }
    }
    @FXML
    public void handleKeyPressedDateChange(ActionEvent event) {
        if (event.getSource() == tomorrowButton) {
            changeDay(day.getDate().plusDays(1));
        } else if ( event.getSource() == yesterdayButton) {
            changeDay(day.getDate().plusDays(-1));
        }
    }

    @FXML
    public void handleKeyPressed(KeyEvent keyEvent) {
        keyboardBitSet.set(keyEvent.getCode().ordinal(), true);
        handleKeyboardState();
    }

    @FXML
    public void handleKeyReleased(KeyEvent keyEvent) {
        keyboardBitSet.set(keyEvent.getCode().ordinal(), false);
        handleKeyboardState();
    }

    public void handleKeyboardState() {
        List<KeyCode> pressedKeys = new ArrayList<>();
        for( KeyCode keyCode: KeyCode.values()) {

            if( keyboardBitSet.get(keyCode.ordinal())) {
                pressedKeys.add(keyCode);
            }
        }
        if (pressedKeys.contains(KeyCode.CONTROL)) {
            if (pressedKeys.contains(KeyCode.B)) {
                changeDay(day.getDate().plusDays(-1));
            } else if (pressedKeys.contains(KeyCode.F)) {
                changeDay(day.getDate().plusDays(1));
            } else if (pressedKeys.contains(KeyCode.DIGIT1)) {
                changeListItemSelected(0);
            } else if (pressedKeys.contains(KeyCode.DIGIT2)) {
                changeListItemSelected(1);
            } else if (pressedKeys.contains(KeyCode.DIGIT3)) {
                changeListItemSelected(2);
            } else if (pressedKeys.contains(KeyCode.DIGIT4)) {
                changeListItemSelected(3);
            } else if (pressedKeys.contains(KeyCode.DIGIT5)) {
                changeListItemSelected(4);
            } else if (pressedKeys.contains(KeyCode.DIGIT6)) {
                changeListItemSelected(5);
            } else if (pressedKeys.contains(KeyCode.DIGIT7)) {
                changeListItemSelected(6);
            } else if (pressedKeys.contains(KeyCode.DIGIT8)) {
                changeListItemSelected(7);
            } else if (pressedKeys.contains(KeyCode.DIGIT9)) {
                changeListItemSelected(8);
            }
        }
    }

    private void changeListItemSelected(Integer index) {
        activityItemListView.getSelectionModel().select(index);
        activityItemListView.requestFocus();
    }

    private void changeDay(LocalDate date) {
        day = activityDayService.getActivityDayByDate(date);
        loadActivityItemsToListView();
    }

    private void handleChangeListView() {
        ActivityItem item = activityItemListView.getSelectionModel().getSelectedItem();
        itemDetailsTextArea.setText(item.getDetails());
    }

    private void loadActivityItemsToListView() {
        dateLabel.setText(day.getDate().toString());
        ObservableList<ActivityItem> activityItems = FXCollections.observableArrayList(day.getActivities());
        SortedList<ActivityItem> sortedList = new SortedList<>(activityItems,
                Comparator.comparing(ActivityItem::getHourOfDay));

        activityItemListView.setItems(sortedList);
        activityItemListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }
}
