package controller;

import component.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;

import java.net.URL;
import java.util.ResourceBundle;


public class MainUIController implements Initializable {
    @FXML
    private TreeView<String> directoryTree;
    @FXML
    private TextField pathField;
    @FXML
    private FlowPane flowPane;
    @FXML
    private Button goBtn;
    @FXML
    private Button backwardsBtn;
    @FXML
    private Button forwardsBtn;
    @FXML
    private Button upperBtn ;
    private DirectoryTreeUtils dUtils;
    private TextFieldUtils tUtils;
    private StaticUtils sUtils;
    private FlowPaneUtils fUtils;
    private ButtonUtils bUtils;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dUtils = new DirectoryTreeUtils(pathField,directoryTree);
        tUtils = new TextFieldUtils(pathField,directoryTree);
        fUtils = new FlowPaneUtils(flowPane);
        bUtils = new ButtonUtils(backwardsBtn,forwardsBtn);
        sUtils = new StaticUtils(fUtils,bUtils);
    }

    public void goButtonOnClicked(MouseEvent mouseEvent) {
        tUtils.update();
    }

    public void upperBtnOnClicked(MouseEvent event) {
        tUtils.getUpperPath();
    }

    public void backwardsBtnOnClicked(MouseEvent event) {
        if (sUtils.presentIndex!=0) {
            pathField.setText(sUtils.browsedPath.get(--sUtils.presentIndex));
            tUtils.update();
        }
    }

    public void forwardsBtnOnclicked(MouseEvent event) {
        if (sUtils.presentIndex != sUtils.maxIndexCount) {
            pathField.setText(sUtils.browsedPath.get(++sUtils.presentIndex));
            tUtils.update();
        }
    }
}
