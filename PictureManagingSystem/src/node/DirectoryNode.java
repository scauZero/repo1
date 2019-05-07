package node;

import utils.StaticUtils;

public class DirectoryNode extends FlowPaneNode {
    public DirectoryNode(String nodePath,int index) {
        super(nodePath,index);
        init(StaticUtils.directoryImage);
    }

    @Override
    protected void doubleClickedEvent() {
        StaticUtils.jumpEvent(nodePath);
    }

}
