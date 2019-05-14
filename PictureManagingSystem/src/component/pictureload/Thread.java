package component.pictureload;

import component.StaticUtils;
import javafx.application.Platform;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

public class Thread implements Runnable {
    private ArrayList<File> filesList = new ArrayList<File>();
    private ArrayList vBoxesList = new ArrayList<VBox>();
    private ArrayList<Boolean> occupyList;
    private ArrayList loadFlagsList = new ArrayList<Boolean>();
    private int index;
    private int vBoxPreHeight;
    private int vBoxPreWidth;
    private int flowElementHeight;//vBox Height + flow Hgap
    private ScrollPane scrollPane;
    private int theNumOfvBox;//每一行的vBox 数目
    private int theNumOfRaw;//flow中 vBox 行数
    private int sign;
    private double scrollPaneLocation;
    private String dirPath;
    private boolean directoryFlag = true;

    //occupyList 表示 线程占用 ，loadFlagsList  表示 是否已经加载
    public Thread(ArrayList<File> filesList, ArrayList vBoxesList, ScrollPane scrollPane, FlowPane flow) {
        this.sign = 1;
        this.index = 0;
        this.dirPath = StaticUtils.presentPath;
        this.scrollPane = scrollPane;
        this.scrollPaneLocation = 0.0;
        this.vBoxPreHeight = 135;
        this.vBoxPreWidth = 115;
        this.theNumOfRaw = (int) flow.getPrefHeight() / (flowElementHeight = (vBoxPreHeight + (int) flow.getHgap()));
        this.theNumOfvBox = (int) flow.getPrefWidth() / (vBoxPreWidth + (int) flow.getVgap());
        if (filesList.size() == 0 || vBoxesList.size() == 0) ;
        else {
            for(File file : filesList) {
                this.filesList.add(file);
            }
            this.vBoxesList = vBoxesList;
            this.occupyList = new ArrayList<Boolean>(Collections.nCopies(filesList.size(), true));
            this.loadFlagsList = new ArrayList<Boolean>(Collections.nCopies(filesList.size(), true));
        }
    }

     @Override
    public void run() {
        //顺序加载
        new java.lang.Thread(() -> {
            while (directoryFlag && index < filesList.size()) {
                if (sign > 0) {
                    if ((boolean) loadFlagsList.get(index) && (boolean) occupyList.get(index)) {
                        try {
                            occupyList.set(index, false);
                            File file = (File) filesList.get(index);
                            VBox vBox = (VBox) vBoxesList.get(index);
                            int[] wh = LoadPicture.getWidthHeight(file);
                            Image img = new Image("file:" + file.getAbsolutePath(), wh[0], wh[1], false, true);
                            filesList.remove(index);
                            filesList.add(index,null);
                            ImageView imageView = new ImageView();
                            Platform.runLater(() -> {
                                vBox.getChildren().remove(0);
                                imageView.setImage(img);
//                                imageView.setFitHeight(110);
//                                imageView.setFitWidth(105);
                                vBox.getChildren().add(0, imageView);
                            });

                            loadFlagsList.set(index, false);
                        } catch (Exception e) {
                            e.getStackTrace();
                        }
                    }
                    index++;
                } else {
                    try {
                        java.lang.Thread.currentThread().sleep(500);//滚动条加载时 该线程睡眠0.5秒判断一次
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            if(index==filesList.size()){
                filesList.removeAll(filesList);
                vBoxesList.removeAll(vBoxesList);
            }

        }).start();

        //滚动条加载
        new java.lang.Thread(() -> {
            while (directoryFlag && index < filesList.size()) {

                double scrollPaneLocation = Math.abs((scrollPane.getViewportBounds().getMaxY() - scrollPane.getViewportBounds().getHeight()));
                //判断滚轮是否移动且滚动条加载线程少于4个，true才更新滚动条位置及创建新的线程
                if (this.scrollPaneLocation != scrollPaneLocation&&sign>-3) {
                    this.scrollPaneLocation = scrollPaneLocation;
                    //创建一个关于滚动条位置的加载线程（滚动条的多次拖动会产生多个线程）
                    java.lang.Thread A = new java.lang.Thread(() -> {
                        sign--;
                        //通过计算得出 目前 显示屏上需要加载的缩略图的下标
                        int minIndex = (int) ((scrollPaneLocation / flowElementHeight)) * (theNumOfvBox) ;
                        int maxIndex = (int) ((scrollPaneLocation / flowElementHeight)) * theNumOfvBox + (theNumOfRaw + 1) * theNumOfvBox;
                        if (minIndex < 0 || minIndex > filesList.size())
                            minIndex = 0;
                        if (maxIndex < 0 || maxIndex > filesList.size())
                            maxIndex = filesList.size();
                        for (int scrollIndex = minIndex; scrollIndex < maxIndex; scrollIndex++) {
                            while (directoryFlag && scrollIndex < maxIndex) {//add dir path judge
                                if ((boolean) loadFlagsList.get(scrollIndex) && (boolean) occupyList.get(scrollIndex)) {
                                    try {
                                        occupyList.set(scrollIndex, false);
                                        File file = (File) filesList.get(scrollIndex);
                                        VBox vBox = (VBox) vBoxesList.get(scrollIndex);
                                        int[] wh = LoadPicture.getWidthHeight(file);
                                        Image img = new Image("file:" + file.getAbsolutePath(), wh[0], wh[1], false, true);
                                        ImageView imageView = new ImageView();
                                        Platform.runLater(() -> {
                                            vBox.getChildren().remove(0);
                                            imageView.setImage(img);
                                            vBox.getChildren().add(0, imageView);
                                        });
                                        loadFlagsList.set(scrollIndex, false);
                                    } catch (Exception e) {
                                        e.getStackTrace();
                                    }
                                }
                                scrollIndex++;
                            }
                        }
                        sign++;
                    });
                    A.start();
                }else try {
                    java.lang.Thread.currentThread().sleep(3000);//滚动条在同一位置留超过3秒 新建一个线程加载缩略图，刷新。
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new java.lang.Thread(() -> {
            while (directoryFlag) {
                try {
                    java.lang.Thread.currentThread().sleep(100);//每0.1秒监测本文件夹路径与目录树点击路径一致
                    if (dirPath.equals(StaticUtils.presentPath))
                        ;
                    else directoryFlag = false;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
