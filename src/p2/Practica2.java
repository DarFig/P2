import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Practica2 {
	final static int waitT = 500;
	static PrintWriter in;
	//static PrintWriter err;
	static BufferedReader out;
	static Process process;
	static String stringTask, stringData;
	static String[][] listaTareas = new String[4][100];

	public static void main(String[] args) {
		start();
		//newEspecificTask("0101", "sda", "asdads");
		//newGeneralTask("0201", "asdad");
		//newGeneralTask("1202", "xxxd");
		
		viewTask();
		getTareas();
		setlistaTareas();
		for (int i = 0; i < listaTareas[0].length; i++) {
			for (int j = 0; j < listaTareas.length; j++) {
				if (listaTareas[j][i] != null){
					System.out.println(i + "-" + j + "--- " + listaTareas[j][i]);
					if (j==3)
						System.out.println("================");
				}
			}
			
		}
		try {
			Thread.sleep(waitT * 4);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

	
	public static void start() {
		//lanzar ws3270 y llegar hasta tareas.c
		try {
			process = Runtime.getRuntime().exec("C:/Program Files/wc3270/ws3270 155.210.152.51:101");
			in = new PrintWriter(new OutputStreamWriter(process.getOutputStream()));
			//err = new PrintWriter(new OutputStreamWriter(process.getErrorStream()));
			out = new BufferedReader(new InputStreamReader(process.getInputStream()));
			
			try {
				Thread.sleep(waitT * 2);
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
				Thread.sleep(waitT * 10);
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
				if (cadena.length() >= 5 && cadena.substring(0, 5).equals("data:")) {
					stringData += cadena + "\n";
				} else if (cadena.length() >= 4 && (cadena.charAt(0) != 'U' || cadena.charAt(4) != 'U')) {
					// fallo de ejecucion se repite
					noTerminado = true;
				}
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
				int nTask = Integer.parseInt(aux2[2].substring(0,aux2[2].length() - 1));
				listaTareas[0][nTask] = aux2[4];// date
				listaTareas[1][nTask] = aux2[5];// name
				listaTareas[2][nTask] = aux2[6];// description
				listaTareas[3][nTask] = aux2[3];// SPECIFIC o GENERAL
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
