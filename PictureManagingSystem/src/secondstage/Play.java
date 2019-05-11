package secondstage;

public class Play extends Thread{
	private ViewerService viewerService = ViewerService.getInstance();
	private boolean isContinue = true;
	public void run() {
		while(true) {
			if(isContinue)
				break;
			viewerService.nextPlay(viewerService.getCurrentFile(), viewerService.getCurrentFiles(), viewerService);
			System.out.println("play...");
			try {
				this.sleep(2300);
			}catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void setContinue(boolean flag) {
		this.isContinue = flag;
		System.out.println("set play "+ flag);
	}
	
}
