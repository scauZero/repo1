package secondstage;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ViewerPanel extends JPanel{
	ImageIcon icon = new ImageIcon("image/001.jpg");
	private static ViewerPanel viewerPanel = null;
	private JLabel label = new JLabel();
//	拖动状态：0为未拖动，1为在拖动
	private int dragStatus = 0;
//	图片当前位置
	private Point labelPosition = new Point(0,0);
//	拖动开始前图片位置，即上一次拖动后的位置
	private Point labelStartPosition = new Point(0,0);
//	鼠标拖动前位置
	private Point mouseStartPosition = null;
	
	
	private ViewerPanel() {
		init();
	}
	
	public static ViewerPanel getInstance() {
		if(ViewerPanel.viewerPanel == null) {
			ViewerPanel.viewerPanel = new ViewerPanel();
			System.out.println("静态面板");
		}
		return ViewerPanel.viewerPanel;
	}
	
	public void init() {
		this.setLayout(null);
		this.setBounds(0,0,1200,650);
		//this.setBackground(Color.red);
		this.add(label);
		addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {
				
			}
			public void mousePressed(MouseEvent e) {
				if(dragStatus == 0) {
					dragStatus = 1;
					mouseStartPosition = e.getPoint();
					labelStartPosition.setLocation(label.getLocation());
				}
			}
			public void mouseReleased(MouseEvent e) {
				if(dragStatus == 1) {
					dragStatus = 0;
				}
			}
			public void mouseEntered(MouseEvent e) {
				
			}
			public void mouseExited(MouseEvent e) {
				
			}
		});
		
		addMouseMotionListener(new MouseMotionListener() {
			public void mouseDragged(MouseEvent e) {
				if(dragStatus == 1) {
					moveLabel(e.getPoint());
				}
			}
			public void mouseMoved(MouseEvent e) {
				
			}
		});
	}
	
	public void moveLabel(Point point) {
//		图片的当前位置等于图片的起始位置加上鼠标位置的偏移量
		labelPosition.setLocation(
				labelStartPosition.getX() + (point.getX()-mouseStartPosition.getX()),
				labelStartPosition.getY() + (point.getY()-mouseStartPosition.getY())
				);
		this.label.setBounds((int)labelPosition.getX(), (int)labelPosition.getY(), label.getWidth(), label.getHeight());
	}
	
	public void setLabelIcon(ImageIcon icon) {
		this.label.setIcon(icon);
		this.label.setBounds((int)(600-0.5*icon.getIconWidth()),(int)(325-0.5*icon.getIconHeight()),icon.getIconWidth(),icon.getIconHeight());
	}
	
	public void setLabelBounds(int x, int y, int width, int height) {
		this.label.setBounds(x, y, width, height);
		this.labelPosition.setLocation(x, y);
	}
	
	
	public void setZoomBounds(ImageIcon icon) {
		this.label.setBounds((int)labelPosition.getX(), (int)labelPosition.getY(), icon.getIconWidth(),icon.getIconHeight());
	}
	
	public JLabel getLabel() {
		return this.label;
	}

	
}
