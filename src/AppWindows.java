import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;


public class AppWindows extends JFrame{
	Gestor wc3270;
	
	//config
	private final int WIDTH = 1024, HIGH = 768;
	
	//grid
	private JPanel panelNorte;
	private JPanel panelCentro;
	
	private Container content;
	private final String[] listType = {"General Task", "Especific Task" };
	
	private PrincipalThread thread;
	
	
	//elementos
	protected JTable listadoTareas;
	protected JScrollPane scroll;
	protected JTextField date, name, description;
	protected JComboBox type;
	protected JButton nuevaTarea;
	
	public AppWindows(Gestor wc3270) {
		this.wc3270 = wc3270;
		setTitle("Tareas");
		setSize(this.WIDTH, this.HIGH);
		setLocationRelativeTo(null);//ajustar al centro de la pantalla
		setResizable(false);//no se puede modificar tamaño
		
		content = getContentPane();
		content.setLayout(new BorderLayout());
		
		this.panelNorte = crearPanelNorteGeneral();
		content.add(panelNorte, BorderLayout.NORTH);
		this.panelCentro = crearPanelCentro();
		content.add(panelCentro, BorderLayout.CENTER);
		
	}
	
	private JPanel crearPanelNorteGeneral(){
		JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));
		
		p.add(new JLabel(" Type : "));
		this.type = new JComboBox(listType);
		this.type.setSelectedItem(listType[0]);
		this.type.addActionListener(new CapturaType());
		p.add(this.type);
		
		p.add(new JLabel(" Date(ddmm) : "));
		this.date = new JTextField(4);
		p.add(this.date);
		
		p.add(new JLabel(" Description : "));
		this.description = new JTextField(20);
		p.add(this.description);
		
		this.nuevaTarea = new JButton("Nueva Tarea");
		this.nuevaTarea.addActionListener(new CapturaNuevaTarea());
		p.add(this.nuevaTarea);
		
		return p;
	}
	private JPanel crearPanelNorteEspecifico(){
		JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));
		
		p.add(new JLabel(" Type : "));
		this.type = new JComboBox(listType);
		this.type.setSelectedItem(listType[1]);
		this.type.addActionListener(new CapturaType());
		p.add(this.type);
		
		p.add(new JLabel(" Date(ddmm) : "));
		this.date = new JTextField(4);
		p.add(this.date);
		
		p.add(new JLabel(" Name : "));
		this.name = new JTextField(10);
		p.add(this.name);
		
		p.add(new JLabel(" Description : "));
		this.description = new JTextField(20);
		p.add(this.description);
		
		this.nuevaTarea = new JButton("Nueva Tarea");
		this.nuevaTarea.addActionListener(new CapturaNuevaTarea());
		p.add(this.nuevaTarea);
		
		return p;
	}
	
	private JPanel crearPanelCentro(){
		JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER));
		String contenido = "";
		
		String[] cols = {"Date", "Name", "Description", "Type"};
		
		

		this.listadoTareas = new JTable(wc3270.listaTareas, cols){
			public boolean isCellEditable(int data, int columns){
				return false;
			}
		};
		this.listadoTareas.setPreferredScrollableViewportSize(new Dimension(this.WIDTH-50, this.HIGH-200));
		this.listadoTareas.setFillsViewportHeight(true);
		this.scroll = new JScrollPane(this.listadoTareas, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		
		p.add(this.scroll);		
		return p;
	}
	public void run(){	
		//Hilo
		this.thread = new PrincipalThread(this.panelCentro, this.wc3270);
		thread.start();
	}
	
	class CapturaType implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(type.getSelectedItem().equals(listType[0])){
				content.remove(panelNorte);
				content.setVisible(false);
				panelNorte = crearPanelNorteGeneral();
				content.add(panelNorte, BorderLayout.NORTH);
				content.setVisible(true);
			
			}else{
				content.remove(panelNorte);
				content.setVisible(false);
				panelNorte = crearPanelNorteEspecifico();
				content.add(panelNorte, BorderLayout.NORTH);
				content.setVisible(true);
			}
		}
	}
	
	class CapturaNuevaTarea implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			//try {
			if(type.getSelectedItem().equals(listType[0])){
				String fecha = date.getText();
				String descripcion = description.getText();
				wc3270.newGeneralTask(fecha, descripcion);
				wc3270.viewTask();
				wc3270.runOnce();
				panelCentro.repaint();
			}else{
				String fecha = date.getText();
				String  nombre = name.getText();
				String descripcion = description.getText();
				wc3270.newEspecificTask(fecha, nombre, descripcion);
				wc3270.viewTask();
				wc3270.runOnce();
				panelCentro.repaint();
			}
			//}catch (Exception e2) {		
				//JOptionPane.showMessageDialog(AppWindows.this, "Error en los campos de entrada o en la conexión");
				
			//}
		} 
		
	}
	

}
