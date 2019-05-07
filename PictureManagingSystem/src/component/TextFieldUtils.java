package component;

import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.File;
import java.util.Stack;

public class TextFieldUtils implements InitializeUtils {
    private TextField pathField;

    private Alert errorAlert  = new Alert(Alert.AlertType.ERROR);

    public TextFieldUtils(TextField textField) {
        this.pathField = textField;
        initialize();
    }


    public TextField getPathField() {
        return pathField;
    }


    @Override
    public void initialize() {
        pathField.setText(StaticUtils.desktopPath);
        pathField.setOnKeyPressed((KeyEvent event)-> {
                if(event.getCode().equals(KeyCode.ENTER)){
                    update();
                }
            }
        );
    }

    @Override
    public void update() {
        String tmp = pathField.getText();
        File file = new File(tmp);
        if (!file.exists()){
            errorAlert.setHeaderText(tmp+"不是一个有效的路径");
            errorAlert.show();
        }else{
            StaticUtils.jumpEvent(tmp);
        }
    }
    public void getUpperPath(){
        File tmp = new File(pathField.getText());
        if(tmp.getParent()!=null) {
            tmp = new File(tmp.getParent());
            pathField.setText(tmp.getPath());
            update();
        }
    }
    public void setPathField(String Path){
        pathField.setText(Path);
    }
}
