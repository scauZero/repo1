package component;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import node.DirectoryNode;
import java.io.File;

public class FlowPaneUtils implements InitializeUtils{
    private FlowPane flowPane;
    private int selectedIndex = -1;
    private int count = 0;
    public int getSelectedIndex() {
        return selectedIndex;
    }

    public void singleSelectedEvent(int selectedIndex) {
        if(this.selectedIndex!=selectedIndex&&this.selectedIndex!=-1){
            flowPane.getChildren().get(this.selectedIndex).setStyle(null);
        }
        this.selectedIndex = selectedIndex;
        flowPane.getChildren().get(this.getSelectedIndex()).setStyle("-fx-background-color: #F0FFFF ");
    }

    public FlowPaneUtils(FlowPane flowPane) {
        this.flowPane = flowPane;
        initialize();
    }
    public FlowPane getFlowPane() {
        return flowPane;
    }
    private void clearAllNodes(){
        count = 0;
        if (flowPane.getChildren().size()!=0)
            flowPane.getChildren().remove(0,flowPane.getChildren().size());
    }
    @Override
    public void initialize() {
        flowPane.setBackground(new Background(new BackgroundFill(Color.WHITE,null,null)));
        update();
    }

    private void setMouseDraggedEvent(){
        flowPane.setOnMousePressed((MouseEvent event)-> {

            }
        );
        flowPane.setOnMouseDragged((MouseEvent event)-> {

            }
        );
        flowPane.setOnMouseReleased((MouseEvent event)-> {

            }
        );
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
//            if(StaticUtils.isPicture(f)){
//                PictureNode pNode = new PictureNode(f.getPath());
//                flowPane.getChildren().add(pNode);
//            }
        }
    }

}
