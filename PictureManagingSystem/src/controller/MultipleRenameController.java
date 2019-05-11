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
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dialog.setText("Totally select "+ StaticUtils.multipleSelectedCount+" items.");
    }

    public void confirmOnClicked(MouseEvent event){
        //改名
        int count = 0;
        PaneUtils pUtils = MultipleStaticAction.pUtils;
        ArrayList<FlowPaneNode> paneNodeList = MultipleStaticAction.pUtils.getPaneNodeList();
        HashSet<Integer> selectedSet = MultipleStaticAction.pUtils.getSelectedSet();
        out:
        for (int i : selectedSet) {
            Label newName = new Label();
            newName.setAlignment(Pos.CENTER);
            newName.setMaxSize(110, 15);
            newName.setWrapText(false);
            if(count==0){
                newName.setText(nameField.getText());
                count++;
            }else {
                newName.setText("副本"+(count++) + "_" + nameField.getText());
            }
            if(rename(paneNodeList.get(i), newName)) {
                paneNodeList.get(i).setNodeName(newName);
                paneNodeList.get(i).getChildren().set(1, newName);
            }else {
                Alert errSameName = new Alert(Alert.AlertType.ERROR);
                errSameName.setHeaderText("File"+newName+"is exists");
                errSameName.show();
                count = 0;
                break out;
            }
        }

        count = 0;
        Stage stage = (Stage) confirm.getScene().getWindow();
        stage.close();
    }

    private boolean rename(FlowPaneNode paneNode,Label newName){
        File oldFile = new File(paneNode.getNodePath());
        String end = new String("");
        for (int i = 0; i < StaticUtils.endOfPicture.length; i++) {
            if (oldFile.getName().toLowerCase().equals(StaticUtils.endOfPicture[i])){
                end = StaticUtils.endOfPicture[i];
                break;
            }
        }
        File newFile = new File(oldFile.getParent()+"\\"+newName.getText()+end);
        if(newFile.exists()) {
            return false;
        }else {
            paneNode.setNodePath(newFile.getPath());
            oldFile.renameTo(newFile);
        }
        return true;
    }

    public void cancelOnClicked(MouseEvent event){
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }
}
