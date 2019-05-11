package node;

import component.PaneUtils;
import component.StaticUtils;
import secondstage.Frame;
import secondstage.ViewerService;

import java.io.File;

public class PictureNode extends FlowPaneNode {
    public PictureNode(String nodePath, int index, PaneUtils pUtils) {
        super(nodePath,index,pUtils);
        init(StaticUtils.tmpPath);
    }

    @Override
    protected void doubleClickedEvent() {
        //TODO:打开图片查看窗口
        ViewerService.open(new File(this.getNodePath()));
        new Frame();
    }
    /*
     * 必须先创建Frame对象
     * 每打开一个路径，传入过滤好的图片文件集合到ViewerService中 setCurrentFiles(List<File> files)方法
     * 每点击一个缩略图打开图片时，传入图片文件到ViewerService中 open(File file)方法
     * 可以在点击缩略图时顺便创建Frame对象，先检测是否存在Frame对象
     */
    @Override
    public int compareTo(Object o) {
        return this.compareTo(o);
    }
}
