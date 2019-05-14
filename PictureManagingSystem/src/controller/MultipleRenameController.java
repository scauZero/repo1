package controller;

import component.PaneUtils;
import component.StaticUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
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
    private Tooltip numErrTooltip = new Tooltip("Input must be a number,please input a number again!");
    private Tooltip formatTip = new Tooltip("Format must be lower than 200,please input a lower number!");
    private Tooltip lengthErrorTip = new Tooltip("Summary of all inputs' length must be lower than 240!");
    private int tmpBit = String.valueOf(StaticUtils.multipleSelectedCount).length();
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
    @FXML
    private TextField format;
    private StringBuffer end = new StringBuffer();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dialog.setText("Totally select " + StaticUtils.multipleSelectedCount + " items.");
        startIndex.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue.matches("[0-9]")){
                startIndex.setText(newValue.replaceAll("[^0-9]", ""));
            }
        });
        format.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue.matches("[0-9]")){
                format.setText(newValue.replaceAll("[^0-9]", ""));
            }
        });
    }

    public void confirmOnClicked(MouseEvent event) {
        //改名
        if (format.getText().equals("")) {
            format.setText(String.valueOf(tmpBit));
        }
        if (isFormatMatch(format)) {
            int start;
            if(startIndex.getText().equals("")) {
                start = 0;
            }else {
                start = Integer.parseInt(startIndex.getText());
            }
            int bit;
            if(Integer.parseInt(format.getText())<tmpBit){
                bit = tmpBit;
            }else {
                bit = Integer.parseInt(format.getText());
            }
            //get input start index and format bit

            StringBuffer tmpName = new StringBuffer();
            ArrayList<FlowPaneNode> paneNodeList = MultipleStaticAction.pUtils.getPaneNodeList();
            HashSet<Integer> selectedSet = MultipleStaticAction.pUtils.getSelectedSet();
            for (int i : selectedSet) {
                if (nameField.getText().equals("")) {
                    tmpName.append(
                            paneNodeList.get(0).getNodeName().getText().replaceAll(
                                    getSuffix(paneNodeList.get(0).getNodeName().getText()),"")
                                    + "_"+String.format("%0" + bit + "d", start++)  /*+getSuffix*/);
                }else {
                    tmpName.append( nameField.getText()+ "_" +String.format("%0" + bit + "d", start++) /*+getSuffix*/);
                }
                Label newName = new Label(String.valueOf(tmpName));
                newName.setAlignment(Pos.CENTER);
                newName.setMaxSize(110, 15);
                newName.setWrapText(false);
                if (tmpName.length() > 240) {
                    new ToolTipEvent(lengthErrorTip,confirm,nameField);
                    confirm.setDisable(true);
                    break;
                } else {
                    if (rename(paneNodeList.get(i), newName)) {
                        paneNodeList.get(i).setNodeName(newName);
                        newName.setText(newName.getText() + end);
                        paneNodeList.get(i).getChildren().set(1, newName);
                        end.delete(0, end.length());
                    } else {
                        Alert errSameName = new Alert(Alert.AlertType.ERROR);
                        errSameName.setHeaderText("File " + newName.getText() + " is exists");
                        errSameName.show();
                    }
                }
                tmpName.delete(0, tmpName.length());
            }
            Stage stage = (Stage) confirm.getScene().getWindow();
            stage.close();
        } else {
            new ToolTipEvent(formatTip,confirm,format);
            confirm.setDisable(true);
        }
    }

    public void cancelOnClicked(MouseEvent event) {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }

    public void inputFieldOnKeyPress(KeyEvent event) {
        if(!(event.getCode().equals(KeyCode.TAB)||event.getCode().equals(KeyCode.BACK_SPACE))) {
            if (event.getTarget().toString().contains("startIndex") && !isNum(event)) {
                new ToolTipEvent(numErrTooltip, confirm, startIndex);
                startIndex.replaceText(0, startIndex.getText().length(), "");
                confirm.setDisable(true);
            } else if (event.getTarget().toString().contains("format") && !isNum(event)) {
                new ToolTipEvent(numErrTooltip, confirm, format);
                format.replaceText(0, format.getText().length(), "");
                confirm.setDisable(true);
            }
        }
    }

    private boolean isNum(KeyEvent event) {
        boolean isNumber = event.getText().matches("[0-9]");
        return isNumber;
    }

    private boolean rename(FlowPaneNode paneNode, Label newName) {
        File oldFile = new File(paneNode.getNodePath());
        String tmp = newName.getText();
        int dotIndex = newName.getText().lastIndexOf(".");
        if (dotIndex == -1) {
            dotIndex = oldFile.getName().lastIndexOf(".");
            end.append(oldFile.getName().substring(dotIndex));
        }
        File newFile = new File(oldFile.getParent() + "\\" + tmp + end);
        if (newFile.exists()) {
            return false;
        } else {
            paneNode.setNodePath(newFile.getPath());
            int count = 0;
            while (!oldFile.renameTo(newFile)) {
                System.out.println(oldFile.getName()+" gc times:"+count++);
                System.gc();
            }
            MultipleStaticAction.pUtils.setRenaming(false, null, oldFile, newFile);
        }
        return true;
    }

    private boolean isFormatMatch(TextField format) {
        return Integer.parseInt(format.getText()) < 200;
    }

    private String getSuffix(String fileName){
        int tmp = fileName.lastIndexOf('.');
        return fileName.substring(tmp);
    }
}
