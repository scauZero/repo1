package operationmenu.action;

import component.PaneUtils;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import node.FlowPaneNode;
import component.StaticUtils;
import secondstage.ViewerService;

import java.io.File;
import java.util.ArrayList;


public class RenameAction extends MenuItemAction {
    private TextField inputField;
    private FlowPaneNode node;
    private PaneUtils pUtils;
    private ArrayList<File> files;
    public RenameAction(FlowPaneNode node,PaneUtils pUtils) {
        this.node = node;
        this.pUtils = pUtils;
        action(this.node);
        File newFile = new File(node.getNodePath());
        StaticUtils.setRenamingIndex(this,true, presentFile,newFile);
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
        ViewerService.setCurrentFiles(null);
        if(newName.equals("")) {
            newName = presentFile.getName();
        }else {
            if(!hasSuffixInput(newName)){
                newName = newName + getSuffix(presentFile);
            }
        }
        File newFile = new File(presentFile.getParent() + "\\" + newName);
        ArrayList<FlowPaneNode> nodeList = pUtils.getPaneNodeList();

        if (hasSameName(newName,nodeList)) {
            Alert errSameName = new Alert(Alert.AlertType.ERROR);
            errSameName.setHeaderText("File"+newName+"is exists");
            errSameName.show();
        } else if (newFile!=presentFile){
            node.getChildren().remove(1);
            Label name = new Label(newFile.getName());
            name.setAlignment(Pos.CENTER);
            name.setMaxSize(110, 15);
            name.setWrapText(false);
            node.getChildren().add(name);
            node.setNodePath(newFile.getPath());
            node.setNodeName(name);
            StaticUtils.setRenamingIndex(this, false,presentFile,newFile);
            while (!presentFile.renameTo(newFile)){
                System.gc();
            }
        }
    }

    private String getSuffix(File presentFile) {
        int startIndex = presentFile.getName().lastIndexOf('.');
        if (startIndex >= 0)
            return new String(presentFile.getName().substring(startIndex));
        else
            return "";
    }

    private boolean hasSuffixInput(String newName) {
        boolean statement = false;
        File tmp = new File(newName);
        System.out.println(getSuffix(tmp));
        if (!getSuffix(tmp).equals("")){
            statement = true;
        }
        return statement;
    }

    private boolean hasSameName(String newName, ArrayList<FlowPaneNode> nodeList){
        boolean statement = false;
        for (FlowPaneNode node:nodeList){
            if (newName.equals(node.getNodeName().getText())&&!presentFile.getName().equals(node.getNodeName().getText())){
                statement = true;
                break;
            }
        }
        return statement;
    }
    public String getInputField(){
        return inputField.getText();
    }
}
