package secondstage;

import secondstage.BuffUtils;
import secondstage.UtilsPanel;
import secondstage.ViewerPanel;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
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
		private BuffUtils buffUtils = null;
		private static Frame frame = null;
		private Play play = Play.getInstance();
		
		
		
		public static Frame getInstance() {
			if(frame == null)
				frame = new Frame();
			frame.setVisible(true);
			return frame;
		}
		
		public Frame() {
			super();
			this.setAlwaysOnTop(true);
			init();
		}
		
		public void init() {
			this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
			if(buffUtils == null)
				buffUtils = new BuffUtils();
			//if(play == null)
				//play = new Play();
			//.start();
			buffUtils.start();
			setVisible(true);
			this.addWindowListener(new WindowListener() {
				@Override
				public void windowOpened(WindowEvent e) {
					 
	            }
				@Override
	            public void windowClosing(WindowEvent e) {
	                // 此处加入操作动作
					play.pausePlay();
					ViewerService.getInstance().pFlag = true;
	            }
	 
	            @Override
	            public void windowClosed(WindowEvent e) {
	 
	            }
	 
	            @Override
	            public void windowIconified(WindowEvent e) {
	            }
	 
	            @Override
	            public void windowDeiconified(WindowEvent e) {
	 
	            }
	 
	            @Override
	            public void windowActivated(WindowEvent e) {
	 
	            }
	 
	            @Override
	            public void windowDeactivated(WindowEvent e) {
	 
	            }
			});
		}
		
		
		public void setShow() {
			this.setVisible(true);
		}
}
