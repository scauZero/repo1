package utils;

import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import node.FlowPaneNode;
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
    private int count = 0;
    private double startX = 0;
    private double startY = 0;
    private boolean isRenaming = false;
    private boolean isMultipleSelecting = false;
    private RenameAction renameAction;
    private ArrayList<FlowPaneNode> paneNodeList = new ArrayList<>();
    private ArrayList<File> pictureFileList = new ArrayList<>();
    private HashSet<Integer> selectedSet = new HashSet<>();
    private Canvas canvas;
    private GraphicsContext gc;
    private PaneMenu menu = new PaneMenu(flowPane);

    public PaneUtils(FlowPane flowPane, Canvas canvas) {
        this.flowPane = flowPane;
        this.canvas = canvas;
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
        /*
         * CopyList应为静态类，通过OperationMenu维护
         *
         * */
    }

    private void cancelSelected(MouseEvent event){
        int x = (int) (event.getX()/115);
        int y = (int) (event.getY()/130);
        int index = y*9+x;
        if(index>flowPane.getChildren().size()){
            clearAllSelected();
        }
    }

    private void clearAllSelected(){
        isMultipleSelecting = false;
        for (int i : selectedSet) {
            paneNodeList.get(i).setMultipleSelected(isMultipleSelecting);
            paneNodeList.get(i).setStyle(null);
        }
        menu.hide();
        selectedSet.removeAll(selectedSet);
    }

    private void setMouseEvent(){
        flowPane.setOnMouseClicked((event)->{
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                if (isRenaming) {
                    renameAction.Rename(renameAction.getInputField());
                    isRenaming = false;
                }
                                       //左键如果有东西在改名，点击布局其他位置就完成改名
                                       //取消选中
            }else if(isMultipleSelecting){                    //右键取消选中，弹出菜单
                menu.show(flowPane,event.getScreenX(),event.getScreenY());
            }
            cancelSelected(event);
        });
        flowPane.setOnMousePressed(event -> {
            setStartingPoint(event);
        });
        flowPane.setOnMouseDragged(event -> {
            drawRectangle(event);
            if(event.getButton().equals(MouseButton.PRIMARY))
                multipleChangeBackground(event);
        });
        flowPane.setOnMouseReleased(event -> {
            clearRectangle();
            multipleSelectedEvent();
        });
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
        for (int i = startRow ; i < endRow; i++) {
            for (int j = startCol; j < endCol; j++) {
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
                    DirectoryNode dNode = new DirectoryNode(f.getPath(),count++);
                    flowPane.getChildren().add(dNode);
                    paneNodeList.add(dNode);
                }
                if(StaticUtils.isPicture(f)){
                    pictureFileList.add(f);
                }
            }
        //TODO:将pictureFileList传走

    }

    private void clearAllNodes(){
        count = 0;
        if (flowPane.getChildren().size()!=0)
            flowPane.getChildren().remove(0,flowPane.getChildren().size());
        selectedSet.removeAll(selectedSet);
        pictureFileList.removeAll(pictureFileList);
        paneNodeList.removeAll(paneNodeList);
    }
}
