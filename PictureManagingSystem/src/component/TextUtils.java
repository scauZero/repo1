package component;

import javafx.scene.text.Text;
import secondstage.ViewerService;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

public class TextUtils implements InitializeUtils {
    private double totalPictureSize = 0;
    private int totalSelectedAmount = 0;
    private Text sizeInfor;
    private Text selectedInfor;

    public TextUtils(Text sizeInfor, Text selectedInfor) {
        this.sizeInfor = sizeInfor;
        this.selectedInfor = selectedInfor;
        initialize();
    }

    private String readTotalSize(ArrayList<File> pictures) {
        FileChannel channel = null;
        if (pictures != null) {
            for (File file : pictures) {
                try {
                    FileInputStream inputStream = new FileInputStream(file);
                    channel = inputStream.getChannel();
                    totalPictureSize += channel.size();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (pictures!=null&&pictures.isEmpty()) {
                totalPictureSize = 0;
            }
        }
        String end = "B";
        if (totalPictureSize > 1024) {
            totalPictureSize /= 1024;
            end = "K" + end;
        }
        if (totalPictureSize >  1024) {
            totalPictureSize /=  1024;
            end = "B";
            end = "M" + end;
        }
        if (totalPictureSize >   1024) {
            totalPictureSize /=  1024;
            end = "B";
            end = "G" + end;
        }
        return end;
    }

    @Override
    public void initialize() {
        String end = readTotalSize((ArrayList<File>) ViewerService.getCurrentFiles());
        selectedInfor.setText("Amount of selected item:" + totalSelectedAmount);
        sizeInfor.setText("Total Pictures' size:" + String.format("%.2f",totalPictureSize) + end);
    }

    @Override
    public void update() {
        String end = readTotalSize((ArrayList<File>) ViewerService.getCurrentFiles());
        totalSelectedAmount = StaticUtils.multipleSelectedCount;
        sizeInfor.setText("Total Pictures' size:" + String.format("%.2f",totalPictureSize) + end);
        selectedInfor.setText("Amount of selected item:" + totalSelectedAmount);
    }
}
