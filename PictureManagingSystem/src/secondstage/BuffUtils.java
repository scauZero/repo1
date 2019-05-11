package secondstage;

import java.awt.Image;
import java.io.File;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;

public class BuffUtils extends Thread{
	private ViewerService viewerService = ViewerService.getInstance();
	private File lastCurrentFile = null;
	
	public BuffUtils() {
		this.lastCurrentFile = viewerService.getCurrentFile();
	}
	
	public void run() {
		while(true) {
			addBuff(viewerService.getCurrentFile(), viewerService.getCurrentFiles(), viewerService.getBuff());
			try {
				this.sleep(800);
			}catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void addBuff(File currentFile, List<File> currentFiles, Map<File, ImageIcon> buff) {
		if(!this.lastCurrentFile.getPath().equals(currentFile.getPath())) {
		if(currentFile != null && currentFiles != null) {
		//System.out.println("Thread enter addBuff");
		int size = currentFiles.size();
		
		int index = currentFiles.indexOf(currentFile);
		//System.out.println(index);
		
		if((index - 6 >=0) && !buff.containsKey(currentFile)) {
			buff.remove(currentFiles.get(index - 6));
			//System.out.println("buff remove");
		}
		
		if((index + 6 < size) && !buff.containsKey(currentFile)) {
			buff.remove(currentFiles.get(index + 6));
			//System.out.println("buff remove");
		}
		
		for(int i=1;i<=5;i++) {
			if(index+i >= size)
				break;
			else {
				if(!buff.containsKey(currentFile)) {
					buff.put(currentFiles.get(index+i), getImageIcon(currentFiles.get(index+i)));
					//System.out.println("addbuff" + i);
				}
				else {
					continue;
				}
			}
		}
		
		for(int i=-1;i>=-5;i--) {
			if(index + i < 0) {
				break;
			}
			else {
				if(!buff.containsKey(currentFile)) {
					buff.put(currentFiles.get(index+i), getImageIcon(currentFiles.get(index+i)));
					//System.out.println("addbuff" + i);
				}
				else {
					continue;
				}
			}
		}
		//System.out.println("Thread end addBuff");
		}
		}
	}
	
	private ImageIcon getImageIcon(File file) {
		ImageIcon t = new ImageIcon(file.getPath());
		double icW = t.getIconWidth();
		double icH = t.getIconHeight();
		
			double f = 1;
			if(icW > icH) {
				if(icW>1200) {
					f = 1200 / icW;
				}
			}
			else {
				if(icH>650) {
					f = 650/icH;
				}
			}
			double width = icW*f;
			double height = icH*f;
			t = new ImageIcon(t.getImage().getScaledInstance((int)width, (int)height, Image.SCALE_DEFAULT));
		
		return t;
	}
}
