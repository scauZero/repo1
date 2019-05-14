package controller;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;

import java.util.Timer;

public class ToolTipEvent {
    private Tooltip tooltip;
    private Button confirm;
    private TextField textField;
    public ToolTipEvent(Tooltip tooltip, Button confirm,TextField textField) {
        this.tooltip = tooltip;
        this.confirm = confirm;
        this.textField = textField;
        System.out.println("1");
        toolTipShow(textField);
    }

    private void toolTipShow(TextField inputField) {
        double x = inputField.getScene().getWindow().getX() + inputField.getLayoutX();
        double y = inputField.getScene().getWindow().getY() + inputField.getLayoutY() + inputField.getHeight();
        tooltip.show(inputField, x, y);

        Timer timer = new Timer();
        ToolTipTimerTask task = new ToolTipTimerTask(tooltip, confirm);
        timer.schedule(task, 3000);
    }
}
