package component;

import javafx.scene.input.MouseEvent;
import operationmenu.CopyList;
import operationmenu.action.RenameAction;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.util.ArrayList;

public class StaticUtils implements InitializeUtils{
    public static File[] root = File.listRoots();
    public static String desktopPath = new String(FileSystemView.getFileSystemView().getHomeDirectory().getPath());
    public static String presentPath = desktopPath;
    public static String directoryImage = "src/image/Directory.png";
    public static String tmpPath = "src/image/Loading.png";
    public static String[] endOfPicture = {".jpg",".jpeg",".gif",".bmp",".png"};
    public static int maxIndexCount = 0;
    public static int presentIndex = 0;
    public static int multipleSelectedCount = 0;
    public static ArrayList<String> browsedPath = new ArrayList<>();
    public static CopyList copyList = new CopyList();
    private static PaneUtils fUtils;
    private static ButtonUtils bUtils;
    private static DirectoryTreeUtils dUtils;
    private static TextFieldUtils tUtils;
    public static void jumpEvent(String path){
        updateList(path);
        presentPath = browsedPath.get(presentIndex);
        fUtils.update();
        bUtils.update();
        tUtils.setPathField(presentPath);
    }

    private static void updateList(String path){
        //与当前位置相同不加入
        //当前位置不是最后位置时
        //将当前位置之后的所有节点都删除
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
    }

    public StaticUtils(ButtonUtils bUtils, DirectoryTreeUtils dUtils, PaneUtils fUtils, TextFieldUtils tUtils){
        this.dUtils = dUtils;
        this.tUtils = tUtils;
        this.fUtils = fUtils;
        this.bUtils = bUtils;
        initialize();
    }

    public static void flowPaneSelectedEvent(int index, MouseEvent event){
        fUtils.singleSelectedEvent(index,event);
    }

    public static void setRenamingIndex(RenameAction renameAction, boolean statement, File presentFile, File newFile) {
        fUtils.setRenaming(statement,renameAction,presentFile,newFile);
    }

    public static boolean isPicture(File f) {
        String tmp = f.getName().toLowerCase();
        for (int i = 0; i < endOfPicture.length; i++) {
            if (tmp.endsWith(endOfPicture[i])){
                return true;
            }
        }
        return false;
    }

    public static void deleteEvent(int index){
        fUtils.deleteEvent(index);
    }

    @Override
    public void initialize() {
        browsedPath.add(desktopPath);
        jumpEvent(desktopPath);
    }

    @Override
    public void update() {

    }
}
