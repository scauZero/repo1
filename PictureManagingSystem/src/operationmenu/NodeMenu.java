package operationmenu;
import operationmenu.action.CopyAction;
import operationmenu.action.DeleteAction;
import operationmenu.action.PasteAction;
import operationmenu.action.RenameAction;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import node.FlowPaneNode;

public class NodeMenu extends ContextMenu {
    private MenuItem delete = new MenuItem("delete");
    private MenuItem copy = new MenuItem("copy");
    private MenuItem paste = new MenuItem("paste");
    private MenuItem rename = new MenuItem("rename");
    private FlowPaneNode node;
    public NodeMenu(FlowPaneNode node){
        super();
        this.node = node;
        setItemEvent();
        setItemDisable();
        this.getItems().addAll(delete);
        this.getItems().addAll(copy);
        this.getItems().addAll(paste);
        this.getItems().addAll(rename);
    }

    private void setItemDisable() {
        if (node.getClass().getName().equals("node.PictureNode")){
            paste.setDisable(true);
        }
        if (node.getClass().getName().equals("node.DirectoryNode")){
            copy.setDisable(true);
        }
    }

    private void setItemEvent(){
        delete.setOnAction((event)->{
            new DeleteAction(node);
        });
        rename.setOnAction(event -> {
            new RenameAction(node);
        });
        copy.setOnAction(event -> {

            new CopyAction(node);
        });
        paste.setOnAction(event -> {
            new PasteAction(node);
        });
    }
}
