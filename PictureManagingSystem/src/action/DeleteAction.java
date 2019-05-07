package action;


import node.FlowPaneNode;
import utils.StaticUtils;

public class DeleteAction extends MenuItemAction{
    public DeleteAction(FlowPaneNode node) {
        action(node);
    }

    @Override
    public void action(FlowPaneNode node) {
        setFile(node);
        String parent = presentFile.getParent();
        presentFile.delete();
        StaticUtils.jumpEvent(parent);//刷新界面
    }
}
