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
        for (File file: StaticUtils.copyList) {
            try {
                Files.copy(file.toPath(),new File(StaticUtils.presentPath+"\\"+file.getName()).toPath());
            } catch (IOException e) {
                Alert err = new Alert(Alert.AlertType.ERROR);
                err.setHeaderText("复制错误");
                e.printStackTrace();
            }
        }
        pUtils.update();
        StaticUtils.copyList.removeAll(StaticUtils.copyList);
    }

    @Override
    public void action(FlowPaneNode node) {
        for (File file: StaticUtils.copyList) {
            try {
                Files.copy(file.toPath(),new File(node.getNodePath()+"\\"+file.getName()).toPath());
            } catch (IOException e) {
                Alert err = new Alert(Alert.AlertType.ERROR);
                err.setHeaderText("复制错误");
                e.printStackTrace();
            }
        }
        StaticUtils.copyList.removeAll(StaticUtils.copyList);
    }
}
