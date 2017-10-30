import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Gestor {
	final static int waitT = 1000;
	static PrintWriter in;
	//static PrintWriter err;
	static BufferedReader out;
	static Process process;
	static String stringTask, stringData;
	public static String[][] listaTareas = new String[4][1000];
	
	public Gestor(){}
	
	public static void start() {
		//lanzar wc3270 y llegar hasta tareas.c
		try {
			process = Runtime.getRuntime().exec("C:/Program Files/wc3270/ws3270 155.210.152.51:101");
			in = new PrintWriter(new OutputStreamWriter(process.getOutputStream()));
			//err = new PrintWriter(new OutputStreamWriter(process.getErrorStream()));
			out = new BufferedReader(new InputStreamReader(process.getInputStream()));
			
			try {
				Thread.sleep(waitT);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//pantalla inicial
			send("enter");
			//pantalla de log
			send("string grupo_01");
			send("tab");
			send("string secreto6");
			send("enter");
			send("enter");
			//iniciar tareas.c
			send("string tareas.c");
			send("enter");
			try {
				//hay que esperar tareas.c
				//se pude ir probando
				Thread.sleep(waitT * 5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	public static void send(String msg) {
		//se encarga de enviar los comandos
		try {
			in.println(msg);
			in.flush();
			String cadena;
			boolean noTerminado = false;
			while (!(cadena = out.readLine()).equals("ok")) {
				if (cadena.length() >= 5 && cadena.substring(0, 5).equals("data:")) 
					stringData += cadena + "\n";
				else if (cadena.length() >= 4 && (cadena.charAt(0) != 'U' || cadena.charAt(4) != 'U')) 
					// fallo de ejecucion se repite
					noTerminado = true;
				
			}
			if (noTerminado) {
				try {
					Thread.sleep(waitT);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				send(msg);
			}
		} catch (IOException e) {

		}
	}

	public void runOnce(){
		getTareas();
		setlistaTareas();
		
	}
	public static void newEspecificTask(String date, String name, String description) {
		//envia una nueva tarea especifica
		send("clear");
		send("string 1");
		send("enter");
		send("string 2");
		send("enter");
		send("string " + date);
		send("enter");
		send("string " + name);
		send("enter");
		send("string " + description);
		send("enter");
		send("string 3");
		send("enter");
		send("clear");
	}

	public static void newGeneralTask(String date, String description) {
		//envia una nueva tarea general
		send("clear");
		send("string 1");
		send("enter");
		send("string 1");
		send("enter");
		send("string " + date);
		send("enter");
		send("string " + description);
		send("enter");
		send("string 3");
		send("enter");
		send("clear");
	}

	public static void viewTask() {
		stringData = "";
		send("clear");
		send("string 2");
		send("enter");
		send("clear");
		send("string 1");
		send("enter");
		while (noTerminado())send("enter");
		send("clear");
		send("string 2");
		send("enter");
		while (noTerminado())send("enter");
		send("clear");
		send("string 3");
		send("enter");
		send("clear");
	}
	
	
	private static void getTareas() {
		stringTask = "";
		if (stringData != null) {
			String[] aux = stringData.split("\n");
			for (int i = 0; i < aux.length; i++) {
				if (aux[i].length() >= 10 && aux[i].substring(0, 10).equals("data: TASK")) {
					stringTask += aux[i] + "\n";
				}
			}
		}
	}
	
	private static void setlistaTareas() {
		if (stringTask != null && !stringTask.equals("")) {
			String[] aux = stringTask.split("\n");
			
			String[] aux2;
			for (int i = 0; i < aux.length; i++) {
				
				aux2 = aux[i].split(" ");
				int numTarea = Integer.parseInt(aux2[2].substring(0,aux2[2].length() - 1));
				// date(ddmm)
				listaTareas[0][numTarea] = aux2[4];
				// name o nada
				listaTareas[1][numTarea] = aux2[5];
				// description
				listaTareas[2][numTarea] = aux2[6];
				// SPECIFIC o GENERAL
				listaTareas[3][numTarea] = aux2[3];
			}
			
		}
	}
	private static boolean noTerminado() {
		send("ascii");
		boolean noTerminado = true;
		String[] aux = stringData.split("\n");
		for (int i = aux.length - 43; i < aux.length; i++) {
			if (aux[i].length() >= 16 && aux[i].substring(0, 16).equals("data: TOTAL TASK"))
				noTerminado = false;
		}
		return noTerminado;
	}
}
