package operationmenu.action;

import component.PaneUtils;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import node.FlowPaneNode;
import component.StaticUtils;

import java.io.File;


public class RenameAction extends MenuItemAction {
    private TextField inputField;
    private FlowPaneNode node;
    public RenameAction(FlowPaneNode node) {
        this.node = node;
        action(this.node);
        StaticUtils.setRenamingIndex(this,true);
    }

    @Override
    public void action(PaneUtils pUtils) {

    }

    /*
     * 将原来放文字的label暂时替换为输入框，
     * 当输入回车的时候实现重命名
     * */
    @Override
    public void action(FlowPaneNode node) {
        setFile(node);
        String oldName = node.getNodeName().getText();
        node.getChildren().remove(1);
        inputField = initInputField(oldName);//输入框
        node.getChildren().add(inputField);
    }
    private TextField initInputField(String oldName){
        TextField tmp = new TextField();
        tmp.setText(oldName);
        tmp.setPrefSize(100,10);
        //TODO:焦点选中
        tmp.requestFocus();
        tmp.setAlignment(Pos.CENTER);
        tmp.setOnKeyPressed((event)->{
            if(event.getCode().equals(KeyCode.ENTER)){
                Rename(tmp.getText());
            }
        });//回车的时候重命名
        return tmp;
    }
    public void Rename(String newName){
        File newFile = new File(presentFile.getParent()+"\\"+newName);
        if (newFile.exists()){
            //TODO:errorAlert : same name
        }else {
            presentFile.renameTo(newFile);
            node.getChildren().remove(1);
            Label name = new Label(newFile.getName());
            name.setAlignment(Pos.CENTER);
            name.setMaxSize(110, 15);
            name.setWrapText(false);
            node.getChildren().add(name);
            node.setNodePath(newFile.getPath());
            node.setNodeName(name);
            StaticUtils.setRenamingIndex(this,false);
        }
    }
    public String getInputField(){
        return inputField.getText();
    }
}
