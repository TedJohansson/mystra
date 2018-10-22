package com.mystra;

import com.mystra.model.ActivityItem;
import com.mystra.util.HibernateUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.mystra.model.ActivityDay;
import org.hibernate.Session;

import java.time.LocalDate;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
//        ActivityDay activityDay = new ActivityDay(LocalDate.now());
//
//        Session session = HibernateUtil.getSessionFactory().openSession();
//
//        session.beginTransaction();
//        session.save(activityDay);
//        session.getTransaction().commit();
//        session.beginTransaction();
//        ActivityItem activityItem = new ActivityItem("First DB activity", "The first Activity I have created on the db",
//                1, activityDay);
//
//        session.save(activityItem);
//        session.getTransaction().commit();
//        session.close();

        Parent root = FXMLLoader.load(getClass().getResource("view/mainwindow.fxml"));
        primaryStage.setTitle("Todo List");
        primaryStage.setScene(new Scene(root, 900, 500));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void stop() throws Exception {
        HibernateUtil.getSessionFactory().close();
        super.stop();
    }

    @Override
    public void init() throws Exception {
        super.init();
    }
}
