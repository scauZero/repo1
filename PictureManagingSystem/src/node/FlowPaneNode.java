package node;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.io.File;

public class FlowPaneNode extends VBox {
    protected String nodePath;
    protected Image nodeImage;
    protected ImageView nodeView;
    protected Label nodeName;

    public FlowPaneNode(String nodePath) {
        this.nodePath = nodePath;
        this.setPadding(new Insets(5,5,5,5));
    }

    public void init(String imagePath){
        nodeImage = new Image("file:"+imagePath);
        nodeView = new ImageView(nodeImage);
        nodeView.setFitWidth(100);
        nodeView.setFitHeight(110);
        nodeName = new Label(new File(nodePath).getName());
        nodeName.setMaxSize(100,15);
        nodeName.setWrapText(false);
        nodeName.setAlignment(Pos.CENTER);
        this.getChildren().addAll(nodeView, nodeName);
        this.setMaxSize(100,120);
    }





























































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
}
