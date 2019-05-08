package utils;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import operationmenu.PaneMenu;
import operationmenu.action.RenameAction;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import node.DirectoryNode;
import java.io.File;
import java.util.ArrayList;

public class PaneUtils implements InitializeUtils{
    private FlowPane flowPane;
    private int selectedIndex = -1;
    private int count = 0;
    private boolean isRenaming = false;
    private RenameAction renameAction;
    private ArrayList<File> pictureFileList = new ArrayList<>();
    private PaneMenu menu = new PaneMenu();
    private Canvas canvas;
    private GraphicsContext gc;
    private double startX = 0;
    private double startY = 0;
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
        if (this.selectedIndex != selectedIndex && this.selectedIndex != -1) {
            flowPane.getChildren().get(this.selectedIndex).setStyle(null);
        }
        this.selectedIndex = selectedIndex;
        flowPane.getChildren().get(this.getSelectedIndex()).setStyle("-fx-background-color: #c7fdff ");
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
        if((x+y*9)>=flowPane.getChildren().size()&&selectedIndex!=-1){
            flowPane.getChildren().get(selectedIndex).setStyle(null);
            selectedIndex = -1;
        }
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
            }else {                    //右键取消选中，弹出菜单
                menu.show(flowPane,event.getScreenX(),event.getScreenY());
            }
            cancelSelected(event);
        });
        flowPane.setOnMousePressed(event -> {
            setStartingPoint(event);
        });
        flowPane.setOnMouseDragged(event -> {
            drawRectangle(event);
        });
        flowPane.setOnMouseReleased(event -> {
            clearRectangle();
        });
    }
    private void setStartingPoint(MouseEvent event){
        startX = event.getX();
        startY = event.getY();
    }
    private void drawRectangle(MouseEvent event){
        gc.clearRect(0,0,flowPane.getWidth(),flowPane.getHeight());
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
        gc.clearRect(0,0,flowPane.getWidth(),flowPane.getHeight());
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
        selectedIndex = -1;
        File[] files = new File(StaticUtils.presentPath).listFiles();
        if (files!=null)
            for(File f:files){
                if (f.isDirectory()&&!f.isHidden()){
                    DirectoryNode dNode = new DirectoryNode(f.getPath(),count++);
                    flowPane.getChildren().add(dNode);
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
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }

}
