package component;

import javafx.scene.layout.FlowPane;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.util.ArrayList;

public class StaticUtils{
    public static File[] root = File.listRoots();
    public static String desktopPath = new String(FileSystemView.getFileSystemView().getHomeDirectory().getPath());
    public static String presentPath = desktopPath;
    public static String directoryImage = "src/image/Directory.jpg";
    public static int maxIndexCount = 0;
    public static int presentIndex = 0;
    public static ArrayList<String> browsedPath = new ArrayList<>();
    private static FlowPaneUtils fUtils;
    private static ButtonUtils bUtils;
    public static void jumpEvent(String path){
        //与当前位置相同不加入
        if(!path.equals(browsedPath.get(presentIndex))){
            presentIndex ++;
            maxIndexCount++;
            browsedPath.add(presentIndex,path);
            if (presentIndex!=maxIndexCount){
                //当前位置不是最后位置时
                //将当前位置之后的所有节点都删除
                for (int i = presentIndex+1; i < browsedPath.size(); i++) {
                    browsedPath.remove(i);
                }
                maxIndexCount = presentIndex;
            }
        }
        presentPath = browsedPath.get(presentIndex);
        fUtils.update();
        bUtils.update();
    }

    public StaticUtils(FlowPaneUtils fUtils, ButtonUtils bUtils){
        browsedPath.add(desktopPath);
        this.fUtils = fUtils;
        this.bUtils = bUtils;
        jumpEvent(desktopPath);
    }
}
