package operationmenu;

import node.FlowPaneNode;

import java.io.File;
import java.util.ArrayList;

public class CopyList extends ArrayList<File> {
    public int getSize(){
        return this.size();
    }

    public void add(FlowPaneNode node) {
        super.add(new File(node.getNodePath()));
    }

    public void remove(int from, int to) {
        if(from!=to) {
            super.removeRange(from, to);
        }
    }
}
