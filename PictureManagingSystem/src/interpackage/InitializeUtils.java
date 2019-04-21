package interpackage;

public interface InitializeUtils {
    public abstract void initialize();
    public abstract void update();
    /*
    *   update方法用于更新不同控件之间的行为
    *   比如说目录树被点击，相对应地就要更新路径文本框
    *   相对应地也要更新图片显示框
    * */
}
