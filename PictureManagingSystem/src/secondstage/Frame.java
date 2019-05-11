package secondstage;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


/*
 * 必须先创建Frame对象
 * 每打开一个路径，传入过滤好的图片文件集合到ViewerService中 setCurrentFiles(List<File> files)方法
 * 每点击一个缩略图打开图片时，传入图片文件到ViewerService中 open(File file)方法
 * 可以在点击缩略图时顺便创建Frame对象，先检测是否存在Frame对象
*/

public class Frame extends JFrame{
	//面板宽和高
		private int width = 1200;
		private int height = 800;
		private JPanel viewer = null;
		private JPanel util = null;
		BuffUtils buffUtils = null;
		
		
		public Frame() {
			super();
			init();
		}
		
		public void init() {
			this.setTitle("图片显示");
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.setResizable(false);
			this.setSize(width, height);
			//在屏幕居中显示
			Toolkit toolkit = Toolkit.getDefaultToolkit();
			Dimension screen = toolkit.getScreenSize();
			int x = (screen.width - this.getWidth())/2;
			int y = (screen.height - this.getHeight())/2;
			this.setLocation(x, y);
			this.setLayout(null);
			this.add(ViewerPanel.getInstance());
			this.add(UtilsPanel.getInstance());
			buffUtils = new BuffUtils();
			Play play = new Play();
			buffUtils.start();
			setVisible(true);
		}
		
}
