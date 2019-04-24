package widget;

import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.io.File;
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
        pathField.setText(StaticUtils.desktopPath);
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
//            不切实际,用作测试
//            nodeAutoExpansion(tmp);
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
    private void nodeAutoExpansion(String tmp){
        StringBuffer buf = new StringBuffer();
        TreeItem rootItem = treeView.getRoot();
        Stack<String> fileNameStack = new Stack<>();
        for (int i = tmp.length()-1  ; i > 1; i--) {
            if(tmp.charAt(i)=='\\'){
                fileNameStack.push(String.valueOf(buf));
                buf.delete(0,buf.length());
            }else {
                buf.insert(0,tmp.charAt(i));
            }
        }
        fileNameStack.push(tmp.substring(0,3));
        while (!fileNameStack.isEmpty()){
            String nameTmp = fileNameStack.pop();
            for (int i = 0; i < rootItem.getChildren().size(); i++) {
                TreeItem childrenItem = (TreeItem) rootItem.getChildren().get(i);
                if (childrenItem.getValue().equals(nameTmp)){
                    childrenItem.setExpanded(true);
                    rootItem = childrenItem;
                    break;
                }
            }
        }
    }

}
