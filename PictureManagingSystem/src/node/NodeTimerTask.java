package node;
import java.util.TimerTask;


public class NodeTimerTask extends TimerTask{
    private FlowPaneNode f;
    public NodeTimerTask(FlowPaneNode f) {
        this.f = f;
    }

    @Override
    public void run() {
        f.setClickCount(0);
    }
}
