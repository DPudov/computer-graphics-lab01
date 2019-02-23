package mainpkg;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("fxml/main_window.fxml"));
        primaryStage.setTitle("Пудов. Лабораторная работа №1. Компьютерная графика");
        Scene scene = new Scene(root, 1008, 402);
        scene.getStylesheets().add("/mainpkg/styles/PointCell.css");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
