package node;

import component.PaneUtils;
import component.StaticUtils;

public class DirectoryNode extends FlowPaneNode {
    public DirectoryNode(String nodePath, int index, PaneUtils pUtils) {
        super(nodePath,index,pUtils);
        init(StaticUtils.directoryImage);
    }

    @Override
    protected void doubleClickedEvent() {
        StaticUtils.jumpEvent(nodePath);
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
