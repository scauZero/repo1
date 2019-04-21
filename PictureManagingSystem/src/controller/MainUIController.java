package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import utils.DirectoryTreeUtils;
import utils.TextFieldUtils;

import java.net.URL;
import java.util.ResourceBundle;


public class MainUIController implements Initializable {
    @FXML
    private TreeView<String> directoryTree;
    @FXML
    private TextField pathField;
    @FXML
    private Button goBtn;
    @FXML
    private Button backwardBtn;
    @FXML
    private Button forwardBtn;
    @FXML
    private Button upperBtn;
    private DirectoryTreeUtils d;
    private TextFieldUtils t;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        d = new DirectoryTreeUtils(pathField,directoryTree);
        t = new TextFieldUtils(pathField,directoryTree);
        goBtn = new Button("Go");
        directoryTree = d.getTreeView();
        pathField = t.getPathField();
    }

    public void goButtonOnClicked(MouseEvent mouseEvent) {
        t.update();
    }
}
