package operationmenu.action;


import component.PaneUtils;
import component.StaticUtils;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import node.FlowPaneNode;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;


public class PasteAction extends MenuItemAction {
    public PasteAction(FlowPaneNode node) {
        action(node);
    }

    public PasteAction(PaneUtils pUtils) {
        action(pUtils);
    }

    @Override
    public void action(PaneUtils pUtils) {
        boolean success = true;
        int count = 0;
        for (File file: StaticUtils.copyList) {
            File tmp = new File(StaticUtils.presentPath+"\\"+file.getName());
            if(!tmp.exists()) {
                try {
                    Files.copy(file.toPath(), new File(StaticUtils.presentPath + "\\" + file.getName()).toPath());
                    count++;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else {
                Alert err = new Alert(Alert.AlertType.ERROR);
                err.setHeaderText("复制错误,有相同的文件:"+tmp.getName());
                err.show();
                success = false;
            }
        }
        if (success||count!=0) {
            pUtils.update();
        }
        StaticUtils.copyList.removeAll(StaticUtils.copyList);
    }

    @Override
    public void action(FlowPaneNode node) {
        for (File file: StaticUtils.copyList) {
            File tmp = new File(node.getNodePath()+"\\"+file.getName());
                if(!tmp.exists()) {
                    try {
                        Files.copy(file.toPath(), new File(node.getNodePath() + "\\" + file.getName()).toPath());
                    } catch (IOException e) {
                    e.printStackTrace();
                }
            }else {
                Alert err = new Alert(Alert.AlertType.ERROR);
                err.setHeaderText("复制错误,有相同的文件:"+tmp.getName());
                err.show();
                break;
            }
        }
            StaticUtils.copyList.removeAll(StaticUtils.copyList);
    }
}
