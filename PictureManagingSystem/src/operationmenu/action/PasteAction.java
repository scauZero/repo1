package operationmenu.action;


import javafx.scene.control.MenuItem;
import node.FlowPaneNode;


public class PasteAction extends MenuItemAction {
    private FlowPaneNode node;
    public PasteAction(FlowPaneNode node) {
        this.node = node;
        action(node);
    }

    @Override
    public void action(FlowPaneNode node) {

    }
}
