package node;

import operationmenu.NodeMenu;
import component.StaticUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import java.io.File;
import java.util.Timer;

public abstract class FlowPaneNode extends VBox {
    protected String nodePath;
    protected Image nodeImage;
    protected ImageView nodeView;
    protected Label nodeName;
    protected int index;

    public int getIndex() {
        return index;
    }

    protected int leftClickCount = 0;
    protected boolean isMultipleSelected = false;

    public void setMultipleSelected(boolean multipleSelected) {
        isMultipleSelected = multipleSelected;
    }

    protected NodeMenu menu = new NodeMenu(this);

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
        setSingleChoiceEvent();
    }

    protected void setSingleChoiceEvent() {
        this.setOnMouseClicked((event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                leftClickCount++;
                if(leftClickCount == 1) {
                    StaticUtils.flowPaneSelectedEvent(index,event);
                    Timer timer = new Timer();
                    NodeTimer task = new NodeTimer(this);
                    timer.schedule(task, 1000);
                }
                else if(leftClickCount ==2){
                    doubleClickedEvent();
                }
            } else if (event.getButton().equals(MouseButton.SECONDARY)) {
                StaticUtils.flowPaneSelectedEvent(index,event);
                if(!isMultipleSelected)
                    menu.show(this, event.getScreenX(), event.getScreenY());
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

    public void setClickCount(int clickCount) {
        this.leftClickCount = clickCount;
    }

    public Label getNodeName() {
        return nodeName;
    }

    public void setNodeName(Label nodeName) {
        this.nodeName = nodeName;
        nodeName.setAlignment(Pos.CENTER);
    }
}
