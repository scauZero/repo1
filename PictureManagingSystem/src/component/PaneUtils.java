package component;

import component.pictureload.DATAList;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseButton;
import node.FlowPaneNode;
import node.PictureNode;
import operationmenu.CopyList;
import operationmenu.PaneMenu;
import operationmenu.action.RenameAction;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import node.DirectoryNode;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;

public class PaneUtils implements InitializeUtils{
    private FlowPane flowPane;
    private ScrollPane scrollPane;
    private int count = 0;
    private double startX = 0;
    private double startY = 0;
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
    private DATAList loadPictures;

    public PaneUtils(FlowPane flowPane, Canvas canvas, ScrollPane scrollPane) {
        this.flowPane = flowPane;
        this.canvas = canvas;
        this.scrollPane = scrollPane;
        initialize();
    }

    public void setRenaming(boolean renaming,RenameAction renameAction) {
        isRenaming = renaming;
        this.renameAction = renameAction;
    }

    public void singleSelectedEvent(int selectedIndex,MouseEvent event) {
        if (event.getButton().equals(MouseButton.PRIMARY)||event.getButton().equals(MouseButton.SECONDARY)&&!selectedSet.contains(selectedIndex)){
            clearAllSelected();
        }
        flowPane.getChildren().get(selectedIndex).setStyle("-fx-background-color: #c7fdff ");
        selectedSet.add(selectedIndex);
        if(event.getButton().equals(MouseButton.SECONDARY)) {

        }
    }

    private void cancelSelected(MouseEvent event){
        int x = (int) (event.getX()/115);
        int y = (int) (event.getY()/130);
        int index = y*9+x;
        if(index>flowPane.getChildren().size()){//判断点击位置是否在其他地方
            clearAllSelected();
        }
    }

    private void clearAllSelected(){
        isMultipleSelecting = false;
        for (int i : selectedSet) {
            paneNodeList.get(i).setMultipleSelected(isMultipleSelecting);
            paneNodeList.get(i).setStyle(null);
        }
        selectedSet.removeAll(selectedSet);
    }

    private void setMouseEvent(){
        flowPane.setOnMouseClicked((event)->{
            clickPaneEvent(event);
        });
        flowPane.setOnMousePressed(event -> {
            setStartingPoint(event);
        });
        flowPane.setOnMouseDragged(event -> {
            menu.hide();
            drawRectangle(event);
            if(event.getButton().equals(MouseButton.PRIMARY))
                multipleChangeBackground(event);
        });
        flowPane.setOnMouseReleased(event -> {
            clearRectangle();
            multipleSelectedEvent();
        });
    }

    private void clickPaneEvent(MouseEvent event){
        menu.hide();
        if (isRenaming) {
            renameAction.Rename(renameAction.getInputField());//如果改名时点击
                                                                //让他完成改名
        }
        else if(event.getButton().equals(MouseButton.SECONDARY)&&selectedSet.size()!=1){
            if(flowPane.getChildren().size()<((int)event.getX()/115+(int)event.getY()/130*9)){
                isMultipleSelecting = false;
            }
            menu.update();
            menu.show(flowPane,event.getScreenX(),event.getScreenY());
        }
        cancelSelected(event);//取消选中
    }

    private void multipleSelectedEvent() {
        for (int i = 0 ; i < flowPane.getChildren().size() ; i++){
            if(!flowPane.getChildren().get(i).getStyle().equals("")){
                selectedSet.add(i);
            }
        }
        if (selectedSet.size()>1){
            isMultipleSelecting = true;
            for (int i :selectedSet) {
                paneNodeList.get(i).setMultipleSelected(isMultipleSelecting);
            }
        }
        StaticUtils.multipleSelectedCount = selectedSet.size();
    }

    private void multipleChangeBackground(MouseEvent event) {
        for (Node node:flowPane.getChildren()) {
            node.setStyle(null);
        }
        int startRow = (int) ((event.getY()>startY?startY:event.getY())/130);
        int startCol = (int) ((event.getX()>startX?startX:event.getX())/115)>0
                ?(int)((event.getX()>startX?startX:event.getX())/115):0;
        int endRow = ((int) ((event.getY()<startY?startY:event.getY())/130)>(flowPane.getChildren().size()/9)
                ?(flowPane.getChildren().size()/9):(int) ((event.getY()<startY?startY:event.getY())/130))+1;
        int endCol = (int) ((event.getX()<startX?startX:event.getX())/115)>0
                ?(int)((event.getX()<startX?startX:event.getX())/115):0;

        endCol = endCol>8?8:endCol;
        for (int i = startRow ; i < endRow; i++) {
            for (int j = startCol; j <= endCol; j++) {
                if (i*9+j>=flowPane.getChildren().size()){
                    break;
                }
                flowPane.getChildren().get(i*9+j).setStyle("-fx-background-color: rgb(199,253,255)");
            }
        }
    }

    private void setStartingPoint(MouseEvent event){
        startX = event.getX();
        startY = event.getY();
    }

    private void drawRectangle(MouseEvent event){
        gc.clearRect(0,0,canvas.getWidth(),canvas.getHeight());
        gc.strokeLine(startX,startY,event.getX(),startY);
        gc.strokeLine(startX,startY,startX,event.getY());
        gc.strokeLine(event.getX(),startY,event.getX(),event.getY());
        gc.strokeLine(startX,event.getY(),event.getX(),event.getY());
        gc.stroke();
        paintRectangle(event);
    }

    private void paintRectangle(MouseEvent event) {
        double x = startX<event.getX()?startX:event.getX();
        double y = startY<event.getY()?startY:event.getY();
        double width = Math.abs(startX-event.getX());
        double height = Math.abs(startY-event.getY());
        gc.fillRect(x,y,width,height);
    }

    private void clearRectangle(){
        gc.clearRect(0,0,canvas.getWidth(),canvas.getHeight());
        startX = 0;
        startY = 0;
    }

    @Override
    public void initialize() {
        gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.rgb(199,253,255));
        setMouseEvent();
        update();
    }

    @Override
    public void update() {
        clearAllNodes();
        File[] files = new File(StaticUtils.presentPath).listFiles();
        if (files!=null)
            for(File f:files){
                if (f.isDirectory()&&!f.isHidden()){
                    DirectoryNode dNode = new DirectoryNode(f.getPath(),count++,this);
                    flowPane.getChildren().add(dNode);
                    paneNodeList.add(dNode);
                }
                if(StaticUtils.isPicture(f)){
                    pictureFileList.add(f);
                }
            }
        //TODO:将pictureFileList传走
        for(File f:pictureFileList){
            PictureNode pNode = new PictureNode(f.getPath(),count++,this);
            flowPane.getChildren().add(pNode);
            paneNodeList.add(pNode);
            pictureBoxList.add(pNode);
        }
        loadPictures = new DATAList(pictureFileList,pictureBoxList,scrollPane,flowPane);
        loadPictures.run();
    }

    private void clearAllNodes(){
        count = 0;
        if (flowPane.getChildren().size()!=0)
            flowPane.getChildren().remove(0,flowPane.getChildren().size());
        selectedSet.removeAll(selectedSet);
        pictureFileList.removeAll(pictureFileList);
        paneNodeList.removeAll(paneNodeList);
    }

    public void deleteEvent(int index) {
        flowPane.getChildren().remove(index);
        for (int i = index+1; i < paneNodeList.size(); i++) {
            paneNodeList.get(i).setIndex(i-1);
        }
        paneNodeList.remove(index);
    }

    public boolean isMultipleSelecting() {
        return isMultipleSelecting;
    }

    public void setIsMultipleSelected(boolean b) {
        this.isMultipleSelecting = b;
        selectedSet.removeAll(selectedSet);
        if(b==true) {
            for (int i = 0; i < flowPane.getChildren().size(); i++) {
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
