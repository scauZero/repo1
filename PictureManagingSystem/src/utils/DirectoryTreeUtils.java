package utils;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.io.File;
import java.util.Stack;
/*
* author : Rorke
* latest update : 2019.04.24
* usage : build directory tree
* */
public class DirectoryTreeUtils implements InitializeUtils {
    private TreeView<String> treeView;
    private TreeItem<String> rootItem = new TreeItem<>("My Computer");
    private TreeItem<String> selectedItem;

    public DirectoryTreeUtils(TreeView<String> treeView) {
        this.treeView = treeView;
        initialize();
    }


    public TreeView<String> getTreeView() {
        return treeView;
    }


    private void setItemExpansionEvent(TreeItem<String> t){
        t.addEventHandler(TreeItem.branchExpandedEvent(), new EventHandler<TreeItem.TreeModificationEvent<Object>>() {
            @Override
            public void handle(TreeItem.TreeModificationEvent<Object> event) {
                TreeItem expandedItem = event.getTreeItem();
                updateTree(expandedItem);
            }
        });
    }


    private void setTreeViewSelectedEvent(TreeView<String> t){
        treeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<String>>() {
            @Override
            public void changed(ObservableValue<? extends TreeItem<String>> observable, TreeItem<String> oldValue, TreeItem<String> newValue) {
                selectedItem = newValue;
                update();
            }
        });//设置点击文件夹树的某个文件夹的时候更新该节点的子节点
           //并在更新右边显示缩略图及文件夹
           //并在更新路径文本框
    }


    //点击时更新文件夹树
    private void updateTree(TreeItem<String> item){
        if (!item.isLeaf()&&!isLoaded(item)) {
            String path = getPath(item);
            item.setValue(path);
            buildSubDirectroyTree(item);
            item.getChildren().remove(0);
            item.setValue(new File(path).getName());
        }
    }


    //构建磁盘符和磁盘符下第一层的文件夹树
    private void buildRootDirectoryTree(){
        for(File f :StaticUtils.root) {
            TreeItem<String> rootDirectory = new TreeItem<>(f.getPath());
            buildSubDirectroyTree(rootDirectory);
            rootItem.getChildren().add(rootDirectory);
        }
        rootItem.setExpanded(true);
    }

    //构建下一层的文件夹树
    private void buildSubDirectroyTree(TreeItem<String> rootItem){
        File[] files = new File(rootItem.getValue()).listFiles();
        if (files!=null) {
            for (File f : files) {
                if (f.isDirectory() && !f.isHidden()) {
                    TreeItem<String> t = new TreeItem<>(f.getName());
                    if(isContainDirectory(f)) {
                        t.getChildren().add(new TreeItem<>());
                    }
                    rootItem.getChildren().add(t);
                }
            }
        }
    }


    //判断下一层是否存在文件夹
    private boolean isContainDirectory(File file){
        boolean statement = false;
        File[] tmp = file.listFiles();
        if(tmp!=null)
            for(File f:tmp){
                if(f.isDirectory()){
                    statement = true;
                    break;
                }
            }
        return statement;
    }


    //判断是否加载了下一层的文件夹
    private boolean isLoaded(TreeItem<String> item){
        if (item.getChildren().get(0).getValue()==null){
            return false;
        }else
            return true;
    }

    /*
     *获取对应目录树单元的路径
     *
     */
    private String getPath(TreeItem<String> item){
        StringBuffer pathBuf = new StringBuffer();
        TreeItem<String> tmp = item;
        Stack<String> pathStack = new Stack<>();
        while (!tmp.getValue().contains("\\")){
            pathStack.push(tmp.getValue());
            tmp = tmp.getParent();
        }
        pathBuf.append(tmp.getValue());
        int flag = 0;
        String tmpString;
        while (!pathStack.isEmpty()){
            if(flag==0){
                tmpString = pathStack.pop();
            }else {
                tmpString = "\\"+pathStack.pop();
            }
            flag++;
            pathBuf.append(tmpString);
        }
        return String.valueOf(pathBuf);
    }


    @Override
    public void initialize() {
        setItemExpansionEvent(rootItem);
        buildRootDirectoryTree();
        treeView.setRoot(rootItem);
        treeView.setShowRoot(false);
        setTreeViewSelectedEvent(treeView);
    }

    @Override
    public void update() {
        StaticUtils.jumpEvent(getPath(selectedItem));
    }
}
