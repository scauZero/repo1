package utils;

import javafx.scene.input.MouseButton;
import operationmenu.action.RenameAction;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import node.DirectoryNode;
import java.io.File;
import java.util.ArrayList;

public class FlowPaneUtils implements InitializeUtils{
    private FlowPane flowPane;
    private int selectedIndex = -1;
    private int count = 0;
    private boolean isRenaming = false;
    private RenameAction renameAction;
    private ArrayList<File> pictureFileList = new ArrayList<>();
    public void setRenaming(boolean renaming,RenameAction renameAction) {
        isRenaming = renaming;
        this.renameAction = renameAction;
    }

    //TODO:右键显示菜单
    public void singleSelectedEvent(int selectedIndex,MouseEvent event) {
        if(event.getButton().equals(MouseButton.PRIMARY)) {
            if (this.selectedIndex != selectedIndex && this.selectedIndex != -1) {
                flowPane.getChildren().get(this.selectedIndex).setStyle(null);
            }
            this.selectedIndex = selectedIndex;
            flowPane.getChildren().get(this.getSelectedIndex()).setStyle("-fx-background-color: #F0FFFF ");
        }else {

        }
        /*
        * CopyList应为静态类，通过OperationMenu维护
        *
        * */
    }

    public FlowPaneUtils(FlowPane flowPane) {
        this.flowPane = flowPane;
        initialize();
    }

    private void clearAllNodes(){
        count = 0;
        if (flowPane.getChildren().size()!=0)
            flowPane.getChildren().remove(0,flowPane.getChildren().size());
    }
    @Override
    public void initialize() {
        flowPane.setBackground(new Background(new BackgroundFill(Color.WHITE,null,null)));

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

    private void cancelSelected(MouseEvent event){
        int x = (int) (event.getX()/115);
        int y = (int) (event.getY()/130);
        if((x+y*9)>=flowPane.getChildren().size()&&selectedIndex!=-1){
            flowPane.getChildren().get(selectedIndex).setStyle(null);
            selectedIndex = -1;
        }
    }

    private void setMouseEvent(){
        flowPane.setOnMouseClicked((MouseEvent event)->{
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                if (isRenaming) {
                    renameAction.Rename(renameAction.getInputField());
                    isRenaming = false;
                }
                                       //左键如果有东西在改名，点击布局其他位置就完成改名
                                       //取消选中
            }else {                    //右键取消选中，弹出菜单

            }
            cancelSelected(event);
        });
        flowPane.setOnMousePressed((MouseEvent event)-> {

            //TODO：记录初始点
            }
        );
        flowPane.setOnMouseDragged((MouseEvent event)-> {
            //TODO:获取当前鼠标位置
            //TODO:刷新矩形
            }
        );
        flowPane.setOnMouseReleased((MouseEvent event)-> {
            //TODO:使框中的节点被选中
            //TODO:消去矩形框
            }
        );/*TODO:框选，画出矩形*/
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }

}
