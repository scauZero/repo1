package component.pictureload;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class LoadPicture {
    public static  int WIDTH_SIZE=105;
    public static  int HEIGHT_SIZE=110;
    public static int[] getWidthHeight(File file){
        int[] widthHeight=new int[2];
        try {
            String fileName = file.getName();
            String suffix=fileName.substring(fileName.lastIndexOf(".") + 1);
            Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName(suffix);
            ImageReader reader = (ImageReader) readers.next();
            ImageInputStream iis = ImageIO.createImageInputStream(file);
            reader.setInput(iis, true);
            widthHeight[0]=reader.getWidth(0);
            widthHeight[1]=reader.getHeight(0);
            if(widthHeight[0]>widthHeight[1]){
                widthHeight[1]=(int)((float)widthHeight[1]/widthHeight[0]*WIDTH_SIZE);
                widthHeight[0]=WIDTH_SIZE;
            }else if (widthHeight[1]==widthHeight[0])
                widthHeight[0]=widthHeight[1]=WIDTH_SIZE;
            else {
                widthHeight[0] = (int)((float)widthHeight[0] / widthHeight[1] * HEIGHT_SIZE);
                widthHeight[1] = HEIGHT_SIZE;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return widthHeight;
    }
}
