package stage;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;

public class MainStage extends Application {
private double offsetX=0;
private double offsetY=0;
    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/view/MainUI.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        root.setOnMousePressed(event -> {
            offsetX = event.getSceneX();
            offsetY = event.getSceneY();
        });

        root.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - offsetX);
            primaryStage.setY(event.getScreenY() - offsetY);
        });
        Scene scene = new Scene(root);
        primaryStage.setTitle("demo");
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();
        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest(event -> {
            Platform.exit();
        });
    }


    public static void main(String[] args) {
        launch(args);
    }

}
