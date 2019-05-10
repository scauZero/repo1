package component;


import javafx.scene.control.Button;

public class ButtonUtils implements InitializeUtils {
    Button backwardsButton;
    Button forwardsButton;

    public ButtonUtils(Button backwardsButton, Button forwardsButton) {
        this.backwardsButton = backwardsButton;
        this.forwardsButton = forwardsButton;
    }

    @Override
    public void initialize() {

    }

    @Override
    public void update() {
        if (StaticUtils.presentIndex==StaticUtils.maxIndexCount){
            forwardsButton.setDisable(true);
        }else
            forwardsButton.setDisable(false);
        if (StaticUtils.presentIndex==0)
            backwardsButton.setDisable(true);
        else
            backwardsButton.setDisable(false);
    }
}
