package operationmenu.action;

import component.PaneUtils;
import multipleeventservice.MultipleStaticAction;
import node.FlowPaneNode;
import stage.MultipleRenameStage;

public class MultipleRename extends MenuItemAction{
    public MultipleRename(PaneUtils pUtils){
        action(pUtils);
    }
    @Override
    public void action(PaneUtils pUtils) {
        MultipleRenameStage renameStage = null;
        try {
            renameStage = new MultipleRenameStage();
        } catch (Exception e) {
            e.printStackTrace();
        }
        MultipleStaticAction.pUtils = pUtils;
        renameStage.show();
    }

    @Override
    public void action(FlowPaneNode node) {

    }
}
