package node;
import java.util.TimerTask;


public class NodeTimer extends TimerTask{
    private FlowPaneNode f;
    public NodeTimer(FlowPaneNode f) {
        this.f = f;
    }

    @Override
    public void run() {
        f.setClickCount(0);
    }
}
