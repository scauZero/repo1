package node;

import component.StaticUtils;

public class DirectoryNode extends FlowPaneNode {
    public DirectoryNode(String nodePath) {
        super(nodePath);
        init(StaticUtils.directoryImage);

    }

}
