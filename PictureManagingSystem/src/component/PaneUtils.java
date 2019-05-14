package component;

import component.pictureload.Thread;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseButton;
import javafx.scene.text.Text;
import node.FlowPaneNode;
import node.PictureNode;
import operationmenu.PaneMenu;
import operationmenu.action.RenameAction;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import node.DirectoryNode;
import secondstage.ViewerService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashSet;

public class PaneUtils implements InitializeUtils {
    private FlowPane flowPane;
    private ScrollPane scrollPane;
    private int count = 0;
    private double startX = 0;
    private double startY = 0;
    private double gap = 10;
    private double vBoxWidth = 115;
    private double vBoxHeight = 135;
    private boolean isRenaming = false;
    private boolean isMultipleSelecting = false;
    private RenameAction renameAction;
    private ArrayList<PictureNode> pictureBoxList = new ArrayList<>();
    private ArrayList<File> pictureFileList = new ArrayList<>();
    private ArrayList<FlowPaneNode> paneNodeList = new ArrayList<>();
    private HashSet<Integer> selectedSet = new HashSet<Integer>();
    private Canvas canvas;
    private GraphicsContext gc;
    private PaneMenu menu = new PaneMenu(this);
    private Thread loadPictures;
    private boolean multipleSelectLock = false;

    public PaneUtils(FlowPane flowPane, Canvas canvas, ScrollPane scrollPane) {
        this.flowPane = flowPane;
        flowPane.setVgap(gap);
        flowPane.setHgap(gap);
        this.canvas = canvas;
        this.scrollPane = scrollPane;
        initialize();
    }

    public void setRenaming(boolean renaming, RenameAction renameAction, File presentFile, File newFile) {
        isRenaming = renaming;
        this.renameAction = renameAction;
        if (renaming == false) {
            int i = pictureFileList.indexOf(presentFile);
            pictureFileList.set(i,newFile);
            ViewerService.setCurrentFiles(pictureFileList);
        }
    }

    public void singleSelectedEvent(int selectedIndex, MouseEvent event) {
        if (event.getButton().equals(MouseButton.PRIMARY) || event.getButton().equals(MouseButton.SECONDARY) && !selectedSet.contains(selectedIndex)) {
            clearAllSelected();
        }
        flowPane.getChildren().get(selectedIndex).setStyle("-fx-background-color: #c7fdff ");
        selectedSet.add(selectedIndex);
        StaticUtils.setMultipleSelectedCount(selectedSet.size());
    }

    private void cancelSelected(MouseEvent event) {
        int x = (int) (event.getX() / (vBoxWidth + gap));
        int y = (int) (event.getY() / (vBoxHeight + gap));
        int index = y * 9 + x;
        if (index > flowPane.getChildren().size() ||
                event.getY() % (vBoxHeight + gap) > vBoxHeight ||
                event.getX() % (vBoxWidth + gap) > vBoxWidth ||
                event.getX()>(vBoxWidth+gap)*9) {//判断点击位置是否在其他地方
            clearAllSelected();
        }
    }

    private void clearAllSelected() {
        isMultipleSelecting = false;
        multipleSelectLock = false;
        for (int i : selectedSet) {
            paneNodeList.get(i).setMultipleSelected(isMultipleSelecting);
            paneNodeList.get(i).setStyle(null);
        }
        selectedSet.removeAll(selectedSet);
        StaticUtils.setMultipleSelectedCount(selectedSet.size());
    }

    private void setMouseEvent() {
        flowPane.setOnMouseClicked((event) -> {
            clickPaneEvent(event);
        });
        flowPane.setOnMousePressed(event -> {
            setStartingPoint(event);
        });
        flowPane.setOnMouseDragged(event -> {
            menu.hide();
            drawRectangle(event);
            if (event.getButton().equals(MouseButton.PRIMARY))
                multipleChangeBackground(event);
        });
        flowPane.setOnMouseReleased(event -> {
            clearRectangle();
            multipleSelectedEvent();
        });
    }

    private void clickPaneEvent(MouseEvent event) {
        menu.hide();
        System.out.println(multipleSelectLock);
        if(!multipleSelectLock) {
            if (isRenaming) {
                renameAction.Rename(renameAction.getInputField());//如果改名时点击
                //让他完成改名
            } else if (event.getButton().equals(MouseButton.SECONDARY) && selectedSet.size() != 1) {
                if (flowPane.getChildren().size() < ((int) (event.getX() / (vBoxWidth)) + (int) (event.getY() / (vBoxHeight)) * 9)) {
                    isMultipleSelecting = false;
                    multipleSelectLock = false;
                }
                menu.update();
                menu.show(flowPane, event.getScreenX(), event.getScreenY());
            }
            cancelSelected(event);//取消选中
        }else {
            multipleSelectLock = false;
            if (event.getButton().equals(MouseButton.SECONDARY) && selectedSet.size() != 1) {
                if (flowPane.getChildren().size() < ((int) (event.getX() / (vBoxWidth)) + (int) (event.getY() / (vBoxHeight)) * 9)) {
                    isMultipleSelecting = false;
                    multipleSelectLock = false;
                }
                menu.update();
                menu.show(flowPane, event.getScreenX(), event.getScreenY());
            }
        }
    }

    private void multipleSelectedEvent() {
        for (int i = 0; i < flowPane.getChildren().size(); i++) {
            if (!flowPane.getChildren().get(i).getStyle().equals("")) {
                selectedSet.add(i);
            }
        }
        if (selectedSet.size() > 1) {
            isMultipleSelecting = true;
            for (int i : selectedSet) {
                paneNodeList.get(i).setMultipleSelected(isMultipleSelecting);
            }
        }
        StaticUtils.setMultipleSelectedCount(selectedSet.size());
    }

    private void multipleChangeBackground(MouseEvent event) {
        selectedSet.removeAll(selectedSet);
        StaticUtils.setMultipleSelectedCount(selectedSet.size());
        for (Node node : flowPane.getChildren()) {
            node.setStyle(null);
        }
        double tmpStartX = event.getX() > startX ? startX : event.getX();
        double tmpStartY = event.getY() > startY ? startY : event.getY();
        double tmpEndX = event.getX() < startX ? startX : event.getX();
        double tmpEndY = event.getY() < startY ? startY : event.getY();
        //initialize start point and end point

        int startCol = (int) (tmpStartX / (vBoxWidth + gap));
        int startRow = (int) (tmpStartY / (vBoxHeight + gap));
        int endCol = (int) (tmpEndX / (vBoxWidth + gap));
        int endRow = (int) (tmpEndY / (vBoxHeight + gap));
        //map location to flow pane column and row
        if (Math.abs(tmpStartX % (vBoxWidth + gap)) > vBoxWidth) {
            startCol = tmpStartX > 0 ? ++startCol : 0;
        }
        if (Math.abs(tmpStartY % (vBoxHeight + gap) )> vBoxHeight) {
            startRow = tmpStartY > 0 ? ++startRow : 0;
        }
        //modify start point

        endCol = tmpEndX > 9 * (vBoxWidth + gap) ? 8 : endCol;
        endRow = endRow > flowPane.getChildren().size() / 9 ? flowPane.getChildren().size() / 9 + 1 : ++endRow;
        //modify end point

        for (int i = startRow; i < endRow; i++) {
            for (int j = startCol; j <= endCol; j++) {
                if ((i * 9 + j )>= flowPane.getChildren().size()) {
                    break;
                }
                if (paneNodeList.get(i * 9 + j).getClass().getName().equals("node.PictureNode")) {
                    paneNodeList.get(i * 9 + j).setStyle("-fx-background-color: rgb(199,253,255)");
                }
            }
        }
        StaticUtils.setMultipleSelectedCount(selectedSet.size());
        multipleSelectLock = true;
    }

    private void setStartingPoint(MouseEvent event) {
        startX = event.getX();
        startY = event.getY();
    }

    private void drawRectangle(MouseEvent event) {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.strokeLine(startX, startY, event.getX(), startY);
        gc.strokeLine(startX, startY, startX, event.getY());
        gc.strokeLine(event.getX(), startY, event.getX(), event.getY());
        gc.strokeLine(startX, event.getY(), event.getX(), event.getY());
        gc.stroke();
        paintRectangle(event);
    }

    private void paintRectangle(MouseEvent event) {
        double x = startX < event.getX() ? startX : event.getX();
        double y = startY < event.getY() ? startY : event.getY();
        double width = Math.abs(startX - event.getX());
        double height = Math.abs(startY - event.getY());
        gc.fillRect(x, y, width, height);
    }

    private void clearRectangle() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        startX = 0;
        startY = 0;
    }

    @Override
    public void initialize() {
        gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.rgb(199, 253, 255));
        setMouseEvent();
    }

    @Override
    public void update() {
        clearAllNodes();
        File[] files = new File(StaticUtils.presentPath).listFiles();
        if (files != null)
            for (File f : files) {
                if (f.isDirectory() && !f.isHidden()) {
                    DirectoryNode dNode = new DirectoryNode(f.getPath(), count++, this);
                    flowPane.getChildren().add(dNode);
                    paneNodeList.add(dNode);
                }
                if (StaticUtils.isPicture(f)) {
                    pictureFileList.add(f);
                }
            }
        for (File f : pictureFileList) {
            PictureNode pNode = new PictureNode(f.getPath(), count++, this);
            flowPane.getChildren().add(pNode);
            paneNodeList.add(pNode);
            pictureBoxList.add(pNode);
        }
        ViewerService.setCurrentFiles(pictureFileList);
        loadPictures = new Thread(pictureFileList, pictureBoxList, scrollPane, flowPane);
        loadPictures.run();
    }

    private void clearAllNodes() {
        count = 0;
        if (flowPane.getChildren().size() != 0)
            flowPane.getChildren().remove(0, flowPane.getChildren().size());
        selectedSet.removeAll(selectedSet);
        pictureFileList.removeAll(pictureFileList);
        paneNodeList.removeAll(paneNodeList);
        pictureBoxList.removeAll(pictureBoxList);
    }

    public void deleteEvent(int index) {
        pictureFileList.remove(new File(paneNodeList.get(index).getNodePath()));
        flowPane.getChildren().remove(index);
        for (int i = index + 1; i < paneNodeList.size(); i++) {
            paneNodeList.get(i).setIndex(i - 1);
        }
        paneNodeList.remove(index);
        selectedSet.remove(index);
        ViewerService.setCurrentFiles(pictureFileList);
    }

    public boolean isMultipleSelecting() {
        return isMultipleSelecting;
    }

    public void setIsMultipleSelected(boolean b) {
        this.isMultipleSelecting = b;
        selectedSet.removeAll(selectedSet);
        if (b == true) {
            for (int i = 0; i < flowPane.getChildren().size(); i++) {
                if (flowPane.getChildren().get(i).getClass().getName().equals("node.PictureNode"))
                    flowPane.getChildren().get(i).setStyle("-fx-background-color: #c7fdff ");
            }
            multipleSelectedEvent();
        }
    }

    public ArrayList getPaneNodeList() {
        return paneNodeList;
    }

    public HashSet<Integer> getSelectedSet() {
        return selectedSet;
    }
}
