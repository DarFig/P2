import javax.swing.JPanel;

public class PrincipalThread extends Thread{
	private JPanel panel;
	Gestor wc3270;
	
	
	public PrincipalThread(JPanel panelO, Gestor wc3270){
		this.panel = panelO;
		this.wc3270 = wc3270;
	}
	
	@Override
	public void run(){
		try {
			//Thread.sleep(500);
		
		while (true) {
		
			
			wc3270.viewTask();
			wc3270.runOnce();
			this.panel.repaint();
			Thread.sleep(50000);
			
				
		}
		} catch (InterruptedException e1) {
			
			e1.printStackTrace();
		}
	}
	
	
}
