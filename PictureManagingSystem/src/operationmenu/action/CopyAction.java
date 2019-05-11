package operationmenu.action;

import component.PaneUtils;
import component.StaticUtils;
import node.FlowPaneNode;
import operationmenu.CopyList;

import java.util.ArrayList;
import java.util.HashSet;

public class CopyAction extends MenuItemAction {
    public CopyAction(FlowPaneNode node) {
        action(node);
    }

    public CopyAction(PaneUtils paneUtils){
        action(paneUtils);
    }

    @Override
    public void action(PaneUtils pUtils) {
        StaticUtils.copyList.removeAll(StaticUtils.copyList);
        HashSet<Integer> selectedSet = pUtils.getSelectedSet();
        ArrayList<FlowPaneNode> nodeList = pUtils.getPaneNodeList();
        for(Integer i:selectedSet) {
                action(nodeList.get(i));
        }
    }

    @Override
    public void action(FlowPaneNode node) {
        System.out.println(node.getClass().getName());
        if (node.getClass().getName().equals("node.PictureNode"))
            StaticUtils.copyList.add(node);
    }
}
