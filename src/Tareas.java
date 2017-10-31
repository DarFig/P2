import javax.swing.JFrame;



public class Tareas {
	public static void main(String[] args) {
		
		Gestor wc3270 = new Gestor();
		
		/*
		 *Tarda unos 9s en conectar y arrancar el tareas.c
		 *y luego otros 3s para obtener las tareas
		 *--todo esto depende de los tiempos de espera y la conexion
		 */
		wc3270.start();
		System.out.println("Sincronizando Tareas....");
		
		//sistema de ventanas
		AppWindows principal = new AppWindows(wc3270);
		principal.setVisible(true);
		principal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		principal.run();
		
		
		
		
		
	}
}
