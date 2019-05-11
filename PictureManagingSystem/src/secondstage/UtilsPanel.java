package secondstage;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class UtilsPanel extends JPanel{
	private ViewerService viewerService = ViewerService.getInstance();
	private static UtilsPanel utilsPanel = null;
	//监听器实例
	ActionListener buttonsListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			viewerService.buttonDo(ViewerPanel.getInstance(), e.getActionCommand());
		}
	};
	
	
	
	private UtilsPanel() {
		init();
	}
	
	/*
	 * 获取单静态实例
	*/
	public static UtilsPanel getInstance() {
		if(utilsPanel==null) {
			utilsPanel = new UtilsPanel();
		}
		return utilsPanel;
	}
	
	public void init() {
		this.setLayout(null);
		this.setBackground(Color.cyan);
		this.setBounds(0, 650, 1200, 250);
		ImageIcon nextIcon = new ImageIcon(getClass().getResource("/image/next.png"));
		nextIcon = new ImageIcon(nextIcon.getImage().getScaledInstance(70, 50, Image.SCALE_DEFAULT));
		
		ImageIcon lastIcon = new ImageIcon(getClass().getResource("/image/last.png"));
		lastIcon = new ImageIcon(lastIcon.getImage().getScaledInstance(70, 50, Image.SCALE_DEFAULT));
		
		ImageIcon bigIcon = new ImageIcon(getClass().getResource("/image/big.png"));
		bigIcon = new ImageIcon(bigIcon.getImage().getScaledInstance(60, 50, Image.SCALE_DEFAULT));
		
		ImageIcon smallIcon = new ImageIcon(getClass().getResource("/image/small.png"));
		smallIcon = new ImageIcon(smallIcon.getImage().getScaledInstance(60, 50, Image.SCALE_DEFAULT));
		
		ImageIcon startIcon = new ImageIcon(getClass().getResource("/image/begin.png"));
		startIcon = new ImageIcon(startIcon.getImage().getScaledInstance(70, 50, Image.SCALE_DEFAULT));
		
		ImageIcon pauseIcon = new ImageIcon(getClass().getResource("/image/pause.png"));
		pauseIcon = new ImageIcon(pauseIcon.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
		
		JButton next = new JButton(nextIcon);
		JButton last = new JButton(lastIcon);
		JButton big = new JButton(bigIcon);
		JButton small = new JButton(smallIcon);
		JButton start = new JButton(startIcon);
		JButton pause = new JButton(pauseIcon);
		
		last.setText("last");
		last.addActionListener(buttonsListener);
		last.setBounds(220,30,50,50);
		next.setText("next");
		next.addActionListener(buttonsListener);
		next.setBounds(350,30,50,50);
		big.setText("big");
		big.addActionListener(buttonsListener);
		big.setBounds(480,30,50,50);
		small.setText("small");
		small.addActionListener(buttonsListener);
		small.setBounds(610,30,50,50);
		start.setText("start");
		start.addActionListener(buttonsListener);
		start.setBounds(740,30,50,50);
		pause.setText("pause");
		pause.addActionListener(buttonsListener);
		pause.setBounds(870, 30, 50, 50);
		
		this.add(next);
		this.add(last);
		this.add(big);
		this.add(small);
		this.add(start);
		this.add(pause);
	}
	
}
