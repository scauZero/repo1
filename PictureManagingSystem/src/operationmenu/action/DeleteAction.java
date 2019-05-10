package operationmenu.action;


import node.FlowPaneNode;
import component.StaticUtils;

public class DeleteAction extends MenuItemAction{
    public DeleteAction(FlowPaneNode node) {
        action(node);
    }

    @Override
    public void action(FlowPaneNode node) {
        setFile(node);
        String parent = presentFile.getParent();
        presentFile.delete();
        StaticUtils.deleteEvent(node.getIndex());//刷新界面
    }
}
