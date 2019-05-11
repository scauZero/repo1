package operationmenu;
import component.PaneUtils;
import component.StaticUtils;
import javafx.scene.layout.FlowPane;
import operationmenu.action.*;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import node.FlowPaneNode;

public class NodeMenu extends MyMenu {
    private FlowPaneNode paneNode;
    private PaneUtils pUtils;
    public NodeMenu(FlowPaneNode paneNode,PaneUtils pUtils) {
        super();
        this.pUtils = pUtils;
        this.paneNode = paneNode;
    }

    @Override
    public void setItemDisable() {
        multipleRename.setDisable(true);
        if(paneNode.getClass().getName().equals("node.PictureNode")|| StaticUtils.copyList.getSize()==0){
            paste.setDisable(true);
        }
        if(paneNode.getClass().getName().equals("node.DirectoryNode")){
            copy.setDisable(true);
        }
    }

    @Override
    public void setItemEvent() {
        selectAll.setOnAction(event -> {
            new SelectAllAction(paneNode);
        });
        rename.setOnAction(event -> {
            new RenameAction(paneNode,pUtils);
        });
        delete.setOnAction(event -> {
            new DeleteAction(paneNode);
        });
        paste.setOnAction(event -> {
            new PasteAction(paneNode);
        });
        copy.setOnAction(event -> {
            new CopyAction(paneNode);
        });
    }
}
