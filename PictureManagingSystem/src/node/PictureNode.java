package node;

public class PictureNode extends FlowPaneNode {
    public PictureNode(String nodePath,int index) {
        super(nodePath,index);
        init(nodePath);
    }

    @Override
    protected void doubleClickedEvent() {
        //TODO:打开图片查看窗口

    }
}
