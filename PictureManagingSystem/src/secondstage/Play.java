package secondstage;


public class Play extends Thread{
	private ViewerService viewerService = ViewerService.getInstance();
	/*
	 * true为停止
	 * false为播放
	*/
	
	private static Play play = null;
	
	public static Play getInstance() {
		if(play == null) {
			play = new Play();
			play.start();
		}
		return play;
	}
	
	private final Object lock = new Object();
	
	private boolean isContinue = true;
	public void run() {
		super.run();
		while(true) {
			//this.isContinue = viewerService.getisContinue();
			//System.out.println("play out running...");
			while (isContinue){
                onPause();
            }
			
			/*System.out.println("play...");
			System.out.println("isContinue ="+this.isContinue);*/
			try {
				viewerService.nextPlay(viewerService.getCurrentFile(), viewerService.getCurrentFiles(), viewerService);
				this.sleep(1500);
			}catch(InterruptedException e) {
				e.printStackTrace();
				break;
			}
			
		}
	}
	
	public void pausePlay() {
		this.isContinue = true;
	}
	
	public void resumePlay() {
		this.isContinue = false;
		synchronized (lock){
            lock.notify();
        }
	}
	
	public void onPause() {
		synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
	}
	
}
