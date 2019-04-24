package widget;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.util.ArrayList;

public class StaticUtils{
    public static File[] root = File.listRoots();
    public static String desktopPath = new String(FileSystemView.getFileSystemView().getHomeDirectory().getPath());
    public static String presentPath;
    public static int maxIndexCount = 0;
    public static int presentIndex = 0;
    public static ArrayList<String> browsedPath = new ArrayList<>();
    public static void jumpEvent(String path){
        //与当前位置相同不加入
        if(!path.equals(browsedPath.get(presentIndex))){
            presentIndex ++;
            maxIndexCount++;
            browsedPath.add(presentIndex,path);
            if (presentIndex!=maxIndexCount){
                for (int i = presentIndex+1; i < browsedPath.size(); i++) {
                    browsedPath.remove(i);
                }
                maxIndexCount = presentIndex;
            }
        }
        presentPath = browsedPath.get(presentIndex);
    }

    public StaticUtils(){
        browsedPath.add(desktopPath);
    }
}
