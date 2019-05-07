package node;

import component.StaticUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;


import java.io.File;
import java.util.Timer;

public abstract class FlowPaneNode extends VBox {
    protected String nodePath;
    protected Image nodeImage;
    protected ImageView nodeView;
    protected Label nodeName;
    protected int index;
    protected int clickCount = 0;

    public FlowPaneNode(String nodePath, int index) {
        this.nodePath = nodePath;
        this.index = index;
        this.setPadding(new Insets(5));
    }

    public void init(String imagePath) {
        nodeImage = new Image("file:" + imagePath);
        nodeView = new ImageView(nodeImage);
        nodeView.setFitWidth(105);
        nodeView.setFitHeight(110);
        nodeName = new Label(new File(nodePath).getName());
        nodeName.setMaxSize(110, 15);
        nodeName.setWrapText(false);
        nodeName.setAlignment(Pos.CENTER);
        this.getChildren().addAll(nodeView, nodeName);
        this.setMaxSize(115, 130);
        setVboxEvent();
    }

    protected void setVboxEvent() {
        setSingleClickedEvent();
    }
    protected void setSingleClickedEvent() {
        this.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                clickCount++;
                if(clickCount == 1) {
                    StaticUtils.flowPaneSelectedEvent(index);
                    Timer timer = new Timer();
                    NodeTimer task = new NodeTimer(this);
                    timer.schedule(task, 1000);
                }
                else if(clickCount ==2){
                    doubleClickedEvent();
                }
            } else if (event.getButton().equals(MouseButton.SECONDARY)) {
                StaticUtils.flowPaneSelectedEvent(index);
                //TODO:右键菜单
            }
        });
    }
    protected abstract void doubleClickedEvent();
    /*getter and setter*/
    public String getNodePath() {
        return nodePath;
    }

    public void setNodePath(String nodePath) {
        this.nodePath = nodePath;
    }

    public Image getNodeImage() {
        return nodeImage;
    }

    public void setNodeImage(Image nodeImage) {
        this.nodeImage = nodeImage;
    }

    public ImageView getNodeView() {
        return nodeView;
    }

    public void setNodeView(Image nodeImage) {
        this.nodeView = new ImageView(nodeImage);
    }

    public int getClickCount() {
        return clickCount;
    }

    public void setClickCount(int clickCount) {
        this.clickCount = clickCount;
    }
}
