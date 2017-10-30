
public class Tareas {
	public static void main(String[] args) {
		Gestor wc3270 = new Gestor();
		wc3270.start();
		//newEspecificTask("0101", "sda", "asdads");
		//newGeneralTask("0201", "asdad");
		//newGeneralTask("1202", "xxxd");
		
		wc3270.viewTask();
		wc3270.runOnce();
		
	}
}
