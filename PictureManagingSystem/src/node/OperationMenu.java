package node;
import action.DeleteAction;
import action.RenameAction;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;

public class OperationMenu extends ContextMenu {
    private MenuItem delete = new MenuItem("delete");
    private MenuItem copy = new MenuItem("copy");
    private MenuItem paste = new MenuItem("paste");
    private MenuItem rename = new MenuItem("rename");
    private FlowPaneNode node;
    public OperationMenu(FlowPaneNode node){
        super();
        this.node = node;
        initItem();
        this.getItems().addAll(delete);
        this.getItems().addAll(copy);
        this.getItems().addAll(paste);
        this.getItems().addAll(rename);
    }
    private void initItem(){
        delete.setOnAction((event)->{
            new DeleteAction(node);
        });
        rename.setOnAction(event -> {
            new RenameAction(node);
        });
    }
}
