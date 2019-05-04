package component;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import node.DirectoryNode;
import node.PictureNode;

import java.io.File;

import static javafx.scene.input.MouseEvent.MOUSE_CLICKED;

public class FlowPaneUtils implements InitializeUtils{
    private FlowPane flowPane;
    public FlowPaneUtils(FlowPane flowPane) {
        this.flowPane = flowPane;
        initialize();
    }
    public FlowPane getFlowPane() {
        return flowPane;
    }
    private void clearAllNodes(){
        if (flowPane.getChildren().size()!=0)
            flowPane.getChildren().remove(0,flowPane.getChildren().size());
    }
    @Override
    public void initialize() {
        flowPane.setBackground(new Background(new BackgroundFill(Color.WHITE,null,null)));
        flowPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

            }
        });
        update();
    }

    @Override
    public void update() {
        clearAllNodes();
        File[] files = new File(StaticUtils.presentPath).listFiles();
        if (files!=null)
        for(File f:files){
            if (f.isDirectory()&&!f.isHidden()){
              DirectoryNode dNode = new DirectoryNode(f.getPath());
              flowPane.getChildren().add(dNode);
            }
//            if(StaticUtils.isPicture(f)){
//                PictureNode pNode = new PictureNode(f.getPath());
//                flowPane.getChildren().add(pNode);
//            }
        }
    }

}
