package mystra;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mystra.datamodel.ActivityDay;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("mainwindow.fxml"));
        primaryStage.setTitle("Todo List");
        primaryStage.setScene(new Scene(root, 900, 500));
        primaryStage.show();
//        ActivityItem activityItem = new ActivityItem("First DB activity", "The first Activity I have created on the db",
//                LocalDate.now());
//
//
//        Session session = HibernateUtil.getSessionFactory().openSession();
//
//        session.beginTransaction();
//        session.save(activityItem);
//        session.getTransaction().commit();
//        session.close();
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
        ActivityDay.getInstance().loadTodoItems();
        super.init();
    }
}