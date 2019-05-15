package controller;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import component.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.awt.*;
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
    private Button upperBtn;
    @FXML
    private Button closeBtn;
    @FXML
    private Canvas canvas;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private Text numOfPictures;
    @FXML
    private Text totalSize;
    @FXML
    private Text selectCount;
    private DirectoryTreeUtils dUtils;
    private TextFieldUtils tUtils;
    private StaticUtils sUtils;
    private PaneUtils fUtils;
    private ButtonUtils bUtils;
    private TextUtils textUtils;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dUtils = new DirectoryTreeUtils(directoryTree);
        tUtils = new TextFieldUtils(pathField);
        fUtils = new PaneUtils(flowPane, canvas, scrollPane);
        bUtils = new ButtonUtils(backwardsBtn, forwardsBtn);
        textUtils = new TextUtils(numOfPictures,totalSize,selectCount);
        sUtils = new StaticUtils(bUtils, dUtils, fUtils, tUtils,textUtils);
    }

    public void goButtonOnClicked(MouseEvent mouseEvent) {
        tUtils.update();
    }

    public void upperBtnOnClicked(MouseEvent event) {
        tUtils.getUpperPath();
    }

    public void backwardsBtnOnClicked(MouseEvent event) {
        if (sUtils.presentIndex != 0) {
            pathField.setText(sUtils.browsedPath.get(--sUtils.presentIndex));
            tUtils.update();
        }
    }

    public void forwardsBtnOnClicked(MouseEvent event) {
        if (sUtils.presentIndex != sUtils.maxIndexCount) {
            pathField.setText(sUtils.browsedPath.get(++sUtils.presentIndex));
            tUtils.update();
        }
    }

    public void closeBtnOnClicked(MouseEvent event) {
        Platform.exit();
    }

    public void closeBtnEnter(MouseEvent event) {
        closeBtn.setStyle("-fx-background-color: RED");
    }

    public void closeBtnExit(MouseEvent event) {
        closeBtn.setStyle("-fx-background-color: transparent");
    }

}
