package action;

import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import node.FlowPaneNode;
import utils.StaticUtils;

import java.io.File;


public class RenameAction extends MenuItemAction {
    private TextField inputField;

    public RenameAction(FlowPaneNode node) {
        action(node);
        StaticUtils.setRenamingIndex(this);
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
        //TODO:将输入框设为焦点，并选中文字
        //emm
        tmp.requestFocus();
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
            StaticUtils.jumpEvent(presentFile.getParent());
        }
    }
    public String getInputField(){
        return inputField.getText();
    }
}
