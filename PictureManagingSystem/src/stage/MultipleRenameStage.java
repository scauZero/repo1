package stage;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MultipleRenameStage extends Stage {


    public MultipleRenameStage() throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/MultipleRenameUI.fxml"));
        Parent root = (Parent) loader.load();
        Scene scene = new Scene(root);
        this.setScene(scene);
        this.initStyle(StageStyle.UNDECORATED);
    }
}
