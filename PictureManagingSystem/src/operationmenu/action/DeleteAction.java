package operationmenu.action;


import component.PaneUtils;
import node.FlowPaneNode;
import component.StaticUtils;
import java.util.ArrayList;
import java.util.HashSet;

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
        ArrayList<FlowPaneNode> pictureList = new ArrayList<FlowPaneNode>();
        HashSet<Integer> selectedIndex = pUtils.getSelectedSet();
        Integer[] tmp = selectedIndex.toArray(new Integer[selectedIndex.size()]);
        for (int i = 0; i<tmp.length; i++){
            pictureList.add(nodeList.get(tmp[i]));
        }
        for (FlowPaneNode node:pictureList){
            action(node);
        }
        pUtils.setIsMultipleSelected(false);
    }

    @Override
    public void action(FlowPaneNode node) {
        setFile(node);
        int count = 0;
        while(!presentFile.delete()){
            System.gc();
            System.out.println(presentFile.getName()+" gc times:"+ ++count);
        }
        StaticUtils.deleteEvent(node.getIndex());//刷新界面
    }
}
