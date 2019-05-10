package operationmenu;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;

public abstract class MyMenu extends ContextMenu {
    private MenuItem delete = new MenuItem("delete");
    private MenuItem copy = new MenuItem("copy");
    private MenuItem paste = new MenuItem("paste");
    private MenuItem rename = new MenuItem("rename");
    private MenuItem multipleRename = new MenuItem("Multiple Rename");
    private MenuItem selectAll = new MenuItem("Select All");
    private SeparatorMenuItem separator = new SeparatorMenuItem();

    public MyMenu(){
        super();
        setItemDisable();
        setItemEvent();
        this.getItems().addAll(delete,copy,paste,rename,separator,multipleRename,selectAll);
    }

    public abstract void setItemDisable();

    public void setItemEvent(){

    }
}
