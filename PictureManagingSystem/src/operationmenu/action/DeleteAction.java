package operationmenu.action;


import component.PaneUtils;
import node.FlowPaneNode;
import component.StaticUtils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class DeleteAction extends MenuItemAction{
    public DeleteAction(FlowPaneNode node) {
        action(node);
    }
    public DeleteAction(PaneUtils pUtils){
        action(pUtils);
    }
    @Override
    public void action(PaneUtils pUtils) {
        ArrayList<FlowPaneNode> nodeList = pUtils.getPaneNodeList();
        HashSet<Integer> selectedIndex = pUtils.getSelectedSet();
        Iterator iter = selectedIndex.iterator();
        Integer[] tmp = selectedIndex.toArray(new Integer[selectedIndex.size()]);
        for (int i = 0; i<tmp.length; i++){
            action(nodeList.get(tmp[i]));
            for (int j = i+1; j < tmp.length; j++) {
                tmp[j]--;
            }
        }
        pUtils.setIsMultipleSelected(false);
    }

    @Override
    public void action(FlowPaneNode node) {
        setFile(node);
        String parent = presentFile.getParent();
        if(presentFile.delete())
            StaticUtils.deleteEvent(node.getIndex());//刷新界面
    }
}
