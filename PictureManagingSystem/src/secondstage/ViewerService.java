package secondstage;

import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;

public class ViewerService {
	private static ViewerPanel viewerPanel = ViewerPanel.getInstance();
	private static ViewerService service = null;
	//当前图片文件
	private static File currentFile = null;
	//新图片
	private ImageIcon newIcon = null;
	private static ImageIcon icon = null;
	//当前文件夹所有图片
	private static List<File> currentFiles = null;
	private static Map<File, ImageIcon> buff = new HashMap<File, ImageIcon>();
	/*// 目前的文件夹
	private File currentDirectory = null;*/
	//缩放比例
	private double range = 0.2;
	//放大倍数
	private static double times = 0;
	private Play play = null;
	//true为不运行幻灯片
	private boolean pFlag = true;
	
	private ViewerService() {
		
		//测试用
		/*this.currentFiles = testSetCurrentFiles("img");
		this.currentFile = this.currentFiles.get(0);
		open(this.currentFile);*/
	}
	
	
	
	//获取静态实例
	public static ViewerService getInstance() {
		if(service == null) {
			service = new ViewerService();
		}
		return service;
	}
	
	
	
	//打开图片时调用
	public static void open(File file) {
		resetTimes();
		//检测图片是否在集合内
		boolean flag = true;
		Iterator <File> it = currentFiles.iterator();
		while(it.hasNext()) {
			File t = (File)it.next();
			if(t.getName().equals(file.getName())) {
				flag = false;
				break;
			}
		}
		//，没有则加入集合
		if(flag) {
			currentFiles.add(file);
		}
		currentFile = file;
		icon = addBuff(currentFile, currentFiles, buff);
		viewerPanel.setLabelIcon(icon);
	}
	
	//缩放
	private void zoom(ViewerPanel viewerPanel,boolean isEnlarge) {
		// 获取放大或者缩小的乘比
		if(isEnlarge)
			times++;
		else
			times--;
				
		int width;
		int height;
		if (icon != null) {
			if(times>=0) {
				width = (int)(icon.getIconWidth()*Math.pow(1+range,times));
				height = (int)(icon.getIconHeight()*Math.pow(1+range,times));
			}
			else {
				width = (int)(icon.getIconWidth()*Math.pow(1-range,Math.abs(times)));
				height = (int)(icon.getIconHeight()*Math.pow(1-range,Math.abs(times)));
			}
			
		// 获取改变大小后的图片
			newIcon = new ImageIcon(icon.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
		// 改变显示的图片
			viewerPanel.setLabelIcon(newIcon);
			System.out.println(this.currentFile.getPath());
			System.out.println(viewerPanel.getLabel().getName());
		}
		if(isEnlarge)
			System.out.println("big!");
		else
			System.out.println("small!");
	}
	
	private static void resetTimes() {
		times = 0;
		System.out.println("reset times!");
	}
	
	private void next(ViewerPanel viewerPanel) {
		if(this.currentFiles != null && !this.currentFiles.isEmpty()) {
			int index = this.currentFiles.indexOf(this.currentFile);
			//下一个
			if(index+1 < this.currentFiles.size()) {
				System.out.println("next success");
				this.currentFile = (File) this.currentFiles.get(index + 1);
				System.out.println("next:"+this.currentFile.getName());
				opent(this.currentFile);
			}
		}
		if(this.currentFiles == null)
			System.out.println("currentFiles null");
		if(this.currentFiles.isEmpty())
			System.out.println("currentFiles empty");
		System.out.println("next!");
	}
	
	private void last(ViewerPanel viewerPanel) {
		if (this.currentFiles != null && !this.currentFiles.isEmpty()) {
			int index = this.currentFiles.indexOf(this.currentFile);
			// 打开上一个
			if (index > 0) {
				System.out.println("last success");
				this.currentFile = (File) this.currentFiles.get(index - 1);
				System.out.println("next:"+this.currentFile.getName());
				opent(this.currentFile);
			}
		}
		if(this.currentFiles == null)
			System.out.println("currentFiles null");
		if(this.currentFiles.isEmpty())
			System.out.println("currentFiles empty");
		System.out.println("last!");
	}
	
	private void start() {
		System.out.println("start");
		if(play == null) {
			play = new Play();
			play.start();
		}
		if(pFlag) {
			play.setContinue(false);
			pFlag = false;
		}
	}
	
	private void pause() {
		System.out.println("pause");
		if(play == null) {
			play = new Play();
			play.start();
		}
		if(!pFlag) {
			play.setContinue(true);
			pFlag = true;
		}
	}
	
	public void buttonDo(ViewerPanel viewerPanel, String cmd) {
		if(cmd.equals("next") && pFlag) {
			next(viewerPanel);
		}
		if(cmd.equals("last") && pFlag) {
			last(viewerPanel);
		}
		if(cmd.equals("big") && pFlag) {
			zoom(viewerPanel, true);
		}
		if(cmd.equals("small") && pFlag) {
			zoom(viewerPanel, false);
		}
		if(cmd.equals("start")) {
			start();
		}
		if(cmd.equals("pause")) {
			pause();
		}
	}
	
	
	
	/*
	//导入文件夹内图片，集合内已经是过滤好的文件
	 * 目录树点进一个文件夹内有图片时，调用此方法，把图片文件集合传入
	*/	
	public static void setCurrentFiles(List<File> files){
		currentFiles = files;
	}
	
	/*
	 * 测试用
	*/
	private List<File> testSetCurrentFiles(String path){
		List<File> files = new ArrayList<File>();
		File dir = new File(path);
		File [] tfiles = dir.listFiles();
		for(int i=0;i<tfiles.length;i++) {
			files.add(tfiles[i]);
		}
		Iterator <File> it = files.iterator();
		while(it.hasNext()) {
			File file = (File)it.next();
			System.out.println(file.getName());
		}
		return files;
	}
	
	/*
	 * 上下不可能存在不在集合中的，不需要遍历集合
	*/
	public void opent(File file) {
		resetTimes();
		this.currentFile = file;
		icon = new ImageIcon(file.getPath());
		//适应面板大小
		icon = addBuff(this.currentFile, this.currentFiles, this.buff);
		viewerPanel.setLabelIcon(icon);
	}
	
	
	
	/*
	 * 图片缓冲
	*/
	private static ImageIcon addBuff(File currentFile, List<File> currentFiles, Map<File, ImageIcon> buff) {
		System.out.println("enter addBuff");
		int size = currentFiles.size();
		ImageIcon test = getImageIcon(currentFile);
		System.out.println("end addBuff");
		return test;
	}
	
	/*
	 * 设置适应面板图片
	*/
	private static ImageIcon getImageIcon(File file) {
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

	
	/*
	 * 幻灯片播放调用
	*/
	public void nextPlay(File currentFile, List<File> currentFiles, ViewerService viewerService) {
		if(currentFiles != null && !currentFiles.isEmpty()) {
			int index = currentFiles.indexOf(currentFile);
			//下一个
			if(index+1 < currentFiles.size()) {
				System.out.println("next success");
				currentFile = (File) currentFiles.get(index + 1);
				System.out.println("next:"+currentFile.getName());
				opent(currentFile);
				System.out.println("play next");
			}
		}
		if(currentFiles == null)
			System.out.println("play currentFiles null");
		if(this.currentFiles.isEmpty())
			System.out.println("play currentFiles empty");
	}


	public File getCurrentFile() {
		return currentFile;
	}



	public Map<File, ImageIcon> getBuff() {
		return buff;
	}



	public List<File> getCurrentFiles() {
		return currentFiles;
	}
}
