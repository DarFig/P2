
public class Tareas {
	public static void main(String[] args) {
		
		Gestor wc3270 = new Gestor();
		wc3270.start();
		//newEspecificTask("0101", "sda", "asdads");
		//newGeneralTask("0201", "asdad");
		//newGeneralTask("1202", "xxxd");
		
		wc3270.viewTask();
		wc3270.runOnce();
		for (int i = 0; i < wc3270.listaTareas[0].length; i++) {
			for (int j = 0; j < wc3270.listaTareas.length; j++) {
				if (wc3270.listaTareas[j][i] != null){
					System.out.println(i + "-" + j + "--- " + wc3270.listaTareas[j][i]);
					if (j==3)
						System.out.println("================");
				}
			}
			
		}
		
	}
}
