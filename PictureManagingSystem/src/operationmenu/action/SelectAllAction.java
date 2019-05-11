package operationmenu.action;

import component.PaneUtils;
import node.FlowPaneNode;

public class SelectAllAction extends MenuItemAction{
    private PaneUtils pUtils;
    private FlowPaneNode paneNode;
    public SelectAllAction(PaneUtils pUtils){
        this.pUtils = pUtils;
        action(this.pUtils);
    }
    public SelectAllAction(FlowPaneNode paneNode){
        this.paneNode = paneNode;
        action(paneNode);
    }
    @Override
    public void action(PaneUtils pUtils) {
        pUtils.setIsMultipleSelected(true);
    }

    @Override
    public void action(FlowPaneNode node) {
        pUtils = node.getpUtils();
        action(pUtils);
    }
}
