package action;

import node.FlowPaneNode;

import java.io.File;

public abstract class MenuItemAction {
    File presentFile;
    public abstract void action(FlowPaneNode node);
    public void setFile(FlowPaneNode Node){
       presentFile = new File(Node.getNodePath());
    }
}
