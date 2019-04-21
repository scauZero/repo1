package utils;

import interpackage.InitializeUtils;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Stack;

public class TextFieldUtils implements InitializeUtils {
    private TextField pathField;
    private TreeView treeView;
    private Alert errorAlert  = new Alert(Alert.AlertType.ERROR);

    public TextFieldUtils(TextField textField, TreeView<String> directoryTree) {
        this.pathField = textField;
        this.treeView = directoryTree;
        initialize();
    }


    public TextField getPathField() {
        return pathField;
    }


    @Override
    public void initialize() {
        pathField.setText(String.valueOf(treeView.getRoot().getValue()));
    }

    @Override
    public void update() {
        String tmp = pathField.getText();
        File file = new File(tmp);
        if (!file.exists()){

            errorAlert.setHeaderText(tmp+"不是一个有效的路径");
            errorAlert.show();
        }else{
            nodeAutoExpansion(tmp);
        }
    }
//TODO:修改下面的方法，使得可以自动展开
    private void nodeAutoExpansion(String tmp){
        TreeItem treeRoot = treeView.getRoot();
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < tmp.length(); i++) {
            if (tmp.charAt(i)!='\\'){
                buf.append(tmp.charAt(i));
            }else {
                i++;
                for (int j = 0; j < treeRoot.getChildren().size(); j++) {
                    TreeItem inputItem = (TreeItem) treeRoot.getChildren().get(j);
                    if (inputItem.getValue().equals(String.valueOf(buf))){
                        inputItem.setExpanded(true);
                        treeRoot = inputItem;
                    }
                }
                buf.delete(0,buf.length());
            }
        }
    }

}
