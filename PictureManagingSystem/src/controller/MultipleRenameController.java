package controller;

import component.PaneUtils;
import component.StaticUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import multipleeventservice.MultipleStaticAction;
import node.FlowPaneNode;

import java.io.File;
import java.net.URL;
import java.util.*;

public class MultipleRenameController implements Initializable {
    @FXML
    private Label dialog;
    @FXML
    private Button confirm;
    @FXML
    private Button cancel;
    @FXML
    private TextField nameField;
    @FXML
    private TextField startIndex;
    private StringBuffer end = new StringBuffer();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dialog.setText("Totally select "+ StaticUtils.multipleSelectedCount+" items.");
    }

    public void confirmOnClicked(MouseEvent event){
        //改名
        int start = Integer.parseInt(startIndex.getText());
        PaneUtils pUtils = MultipleStaticAction.pUtils;
        ArrayList<FlowPaneNode> paneNodeList = MultipleStaticAction.pUtils.getPaneNodeList();
        HashSet<Integer> selectedSet = MultipleStaticAction.pUtils.getSelectedSet();
        out:
        for (int i : selectedSet) {
            Label newName = new Label();
            newName.setAlignment(Pos.CENTER);
            newName.setMaxSize(110, 15);
            newName.setWrapText(false);
            newName.setText("副本"+(start++) + "_" + nameField.getText());
            if(rename(paneNodeList.get(i), newName)) {
                paneNodeList.get(i).setNodeName(newName);
                newName.setText(newName.getText()+end);
                paneNodeList.get(i).getChildren().set(1, newName);
                end.delete(0,end.length());
            }else {
                Alert errSameName = new Alert(Alert.AlertType.ERROR);
                errSameName.setHeaderText("File "+newName.getText()+" is exists");
                errSameName.show();
                break out;
            }
        }
        Stage stage = (Stage) confirm.getScene().getWindow();
        stage.close();
    }

    private boolean rename(FlowPaneNode paneNode,Label newName){
        File oldFile = new File(paneNode.getNodePath());
        for (int i = 0; i < StaticUtils.endOfPicture.length; i++) {
            if (oldFile.getName().toLowerCase().endsWith(StaticUtils.endOfPicture[i])){
                end.append(StaticUtils.endOfPicture[i]);
                break;
            }
        }
        File newFile = new File(oldFile.getParent()+"\\"+newName.getText()+end);
        if(newFile.exists()) {
            System.out.println("1");
            return false;
        }else {
            paneNode.setNodePath(newFile.getPath());
            while (!oldFile.renameTo(newFile)){
                System.gc();
            }
            MultipleStaticAction.pUtils.setRenaming(false,null,oldFile,newFile);
        }
        return true;
    }

    public void cancelOnClicked(MouseEvent event){
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }
    public void startIndexPressEvent(KeyEvent event){
        if (event.getCharacter().compareTo("0")>=0||event.getCharacter().compareTo("9")<=0){
            startIndex.setText("");
        }
    }
}
