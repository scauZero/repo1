package component;

public interface InitializeUtils {
    public abstract void initialize();
    public abstract void update();
    /*
    *   update方法用于更新控件的行为
    *   比如说目录树被点击，相对应地就要更新路径文本框
    * */
}
