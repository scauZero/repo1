package operationmenu;

import component.PaneUtils;
import component.StaticUtils;
import node.FlowPaneNode;
import operationmenu.action.*;

import java.util.ArrayList;
import java.util.HashSet;

public class PaneMenu extends MyMenu {
    private PaneUtils pUtils;
    ArrayList<FlowPaneNode> nodeList;
    public PaneMenu(PaneUtils pUtils){
        super();
        copy.setText("Copy Pictures");
        this.pUtils = pUtils;
        nodeList = pUtils.getPaneNodeList();
    }

    @Override
    public void setItemDisable() {
        rename.setDisable(true);
        if(!pUtils.isMultipleSelecting()){
            multipleRename.setDisable(true);
            delete.setDisable(true);
            selectAll.setDisable(false);
        }else {
            multipleRename.setDisable(false);
            delete.setDisable(false);
            selectAll.setDisable(true);
        }
        if (StaticUtils.copyList.isEmpty()){
            paste.setDisable(true);
        }else {
            paste.setDisable(false);
        }
        HashSet<Integer> selectedSet = pUtils.getSelectedSet();
        boolean flag = true;
        for (int i:selectedSet){
            if (nodeList.get(i).getClass().getName().equals("node.PictureNode")){
                flag = false;
                break;
            }
        }
        copy.setDisable(flag);
    }

    @Override
    public void setItemEvent() {
        selectAll.setOnAction(event -> {
            new SelectAllAction(pUtils);
        });
        multipleRename.setOnAction(event -> {
            new MultipleRename(pUtils);
        });
        delete.setOnAction(event -> {
            new DeleteAction(pUtils);
        });
        copy.setOnAction(event -> {
            new CopyAction(pUtils);
        });
        paste.setOnAction(event -> {
            new PasteAction(pUtils);
        });
    }
}
