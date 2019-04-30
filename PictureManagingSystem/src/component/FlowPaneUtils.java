package component;

import javafx.scene.layout.FlowPane;
import node.DirectoryNode;
import java.io.File;

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
        update();
    }

    @Override
    public void update() {
        clearAllNodes();
        File[] files = new File(StaticUtils.presentPath).listFiles();
        for(File f:files){
            if (f.isDirectory()&&!f.isHidden()){
              DirectoryNode dNode = new DirectoryNode(f.getPath());
              flowPane.getChildren().add(dNode);
            }
        }
    }
}
