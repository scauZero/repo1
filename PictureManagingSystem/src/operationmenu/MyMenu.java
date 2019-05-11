package operationmenu;

import component.StaticUtils;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;

public abstract class MyMenu extends ContextMenu {
    protected MenuItem delete = new MenuItem("delete");
    protected MenuItem copy = new MenuItem("copy");
    protected MenuItem paste = new MenuItem("paste");
    protected MenuItem rename = new MenuItem("rename");
    protected MenuItem multipleRename = new MenuItem("Multiple Rename");
    protected MenuItem selectAll = new MenuItem("Select All");
    protected SeparatorMenuItem separator = new SeparatorMenuItem();
    public MyMenu(){
        super();
        setItemEvent();
        this.getItems().addAll(delete,copy,paste,rename,separator,multipleRename,selectAll);
    }
    public void update(){
        setItemDisable();
    }
    public abstract void setItemDisable();
    public abstract void setItemEvent();
}
