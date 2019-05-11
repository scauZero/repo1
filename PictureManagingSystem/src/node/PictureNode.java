package node;

import component.PaneUtils;
import component.StaticUtils;

public class PictureNode extends FlowPaneNode {
    public PictureNode(String nodePath, int index, PaneUtils pUtils) {
        super(nodePath,index,pUtils);
        init(StaticUtils.tmpPath);
    }

    @Override
    protected void doubleClickedEvent() {
        //TODO:打开图片查看窗口

    }

    @Override
    public int compareTo(Object o) {
        return this.compareTo(o);
    }
}
