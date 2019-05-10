package operationmenu;
import operationmenu.action.CopyAction;
import operationmenu.action.DeleteAction;
import operationmenu.action.PasteAction;
import operationmenu.action.RenameAction;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import node.FlowPaneNode;

public class NodeMenu extends MyMenu {
    private FlowPaneNode paneNode;

    public NodeMenu(FlowPaneNode paneNode) {
        super();
        this.paneNode = paneNode;
    }

    @Override
    public void setItemDisable() {

    }
}
