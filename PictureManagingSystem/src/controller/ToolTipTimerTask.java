package controller;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;

import java.util.TimerTask;

public class ToolTipTimerTask extends TimerTask {
    private Tooltip tooltip;
    private Button confirm;
    public ToolTipTimerTask(Tooltip tooltip, Button confirm) {
        this.tooltip = tooltip;
        this.confirm = confirm;
    }

    @Override
    public void run() {
        Platform.runLater(()->{
            tooltip.hide();
            confirm.setDisable(false);
        });
    }
}
