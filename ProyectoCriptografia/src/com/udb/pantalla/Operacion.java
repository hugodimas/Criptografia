package com.udb.pantalla;

import java.awt.EventQueue;
import java.beans.PropertyVetoException;
import java.math.BigInteger;

import javax.swing.JInternalFrame;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.SwingConstants;
import javax.swing.JPasswordField;
import java.awt.GridLayout;
import java.awt.HeadlessException;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;

import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import com.udb.algoritmos.AES;
import com.udb.algoritmos.RSA;
import com.udb.algoritmos.Vigenere;

import javax.swing.UIManager;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.AbstractListModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.JTextField;

public class Operacion extends JInternalFrame {
	private JPasswordField passwordField;
	ButtonGroup grupoAlgoritmos;
	JRadioButton rbtnCifrar;
	JRadioButton rbtnDecifrar;
	JList listadoAlgoritmos;
	JTextArea textAreaEventos;
	JTextArea textAreaCifrado;
	JTextArea textAreaMensaje;
	JLabel lblDescripcion;
	private JTextField txtTamanioPrimos;
	private RSA rsa;
	private BigInteger[] textoCifrado;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Operacion frame = new Operacion();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Operacion() {
		setIconifiable(true);
		setClosable(true);
		setTitle(".:: Criptograf\u00EDa - Cifrar/Descifrar ::.");
		setFrameIcon(new ImageIcon(Operacion.class.getResource("/com/udb/images/appcrypt-icon16.png")));
		setBounds(100, 100, 847, 557);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel Controles = new JPanel();
		getContentPane().add(Controles, BorderLayout.CENTER);
		Controles.setLayout(null);
		
		JPanel panel_general = new JPanel();
		panel_general.setBounds(10, 11, 366, 49);
		Controles.add(panel_general);
		panel_general.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JPanel panel_normal = new JPanel();
		panel_general.add(panel_normal);
		
		passwordField = new JPasswordField();
		
		JLabel label = new JLabel("LLAVE");
		label.setVerticalAlignment(SwingConstants.TOP);
		label.setToolTipText("");
		label.setFont(new Font("Tahoma", Font.BOLD, 11));
		GroupLayout gl_panel_normal = new GroupLayout(panel_normal);
		gl_panel_normal.setHorizontalGroup(
			gl_panel_normal.createParallelGroup(Alignment.LEADING)
				.addComponent(label)
				.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, 366, GroupLayout.PREFERRED_SIZE)
		);
		gl_panel_normal.setVerticalGroup(
			gl_panel_normal.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_normal.createSequentialGroup()
					.addComponent(label)
					.addGap(5)
					.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		);
		panel_normal.setLayout(gl_panel_normal);
		
		JPanel panel_rsa = new JPanel();
		panel_general.add(panel_rsa);
		
		JLabel lblTamaoNumeroPrimo = new JLabel("TAMA\u00D1O NUMERO PRIMO");
		lblTamaoNumeroPrimo.setVerticalAlignment(SwingConstants.TOP);
		lblTamaoNumeroPrimo.setToolTipText("");
		lblTamaoNumeroPrimo.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		txtTamanioPrimos = new JTextField();
		txtTamanioPrimos.setColumns(10);
		
		JButton btnGenerarClaves = new JButton("GENERAR CLAVES");
		GroupLayout gl_panel_rsa = new GroupLayout(panel_rsa);
		gl_panel_rsa.setHorizontalGroup(
			gl_panel_rsa.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_rsa.createSequentialGroup()
					.addGroup(gl_panel_rsa.createParallelGroup(Alignment.LEADING)
						.addComponent(lblTamaoNumeroPrimo, GroupLayout.PREFERRED_SIZE, 165, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtTamanioPrimos, GroupLayout.PREFERRED_SIZE, 188, GroupLayout.PREFERRED_SIZE))
					.addGap(5)
					.addComponent(btnGenerarClaves, GroupLayout.PREFERRED_SIZE, 173, GroupLayout.PREFERRED_SIZE))
		);
		gl_panel_rsa.setVerticalGroup(
			gl_panel_rsa.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_rsa.createSequentialGroup()
					.addComponent(lblTamaoNumeroPrimo)
					.addComponent(txtTamanioPrimos, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_panel_rsa.createSequentialGroup()
					.addGap(13)
					.addComponent(btnGenerarClaves, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
		);
		panel_rsa.setLayout(gl_panel_rsa);
		btnGenerarClaves.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(txtTamanioPrimos.getText().equals(""))
		            JOptionPane.showMessageDialog(null,"No haz introducido el tamaño del primo", "Tenemos problemas", JOptionPane.ERROR_MESSAGE);
		        else {
		            rsa = new RSA(Integer.parseInt(txtTamanioPrimos.getText()));
		            rsa.generaPrimos();
		            rsa.generaClaves();
		            JTextArea area = new JTextArea(15,15);
		            area.setEditable(false);
		            area.setLineWrap(true);
		            area.append("Tamaño de clave: "+ txtTamanioPrimos.getText()+"\n");
		            area.append("p:["+rsa.damep()+"]\nq:["+rsa.dameq()+"]\n\n");
		            area.append("Clave pública (n,e):\nn:["+rsa.damen()+"]\ne:["+rsa.damee()+"]\n\n");
		            area.append("Clave privada (n,d):\nn:["+rsa.damen()+"]\nd:["+rsa.damed()+"]");
		            JOptionPane.showMessageDialog(null, new JScrollPane(area),".:: Números primos generados exitosamente ::.", JOptionPane.INFORMATION_MESSAGE);
		        }
			}
		});
		
		JLabel lblEntrada = new JLabel("ENTRADA");
		lblEntrada.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblEntrada.setBounds(10, 63, 149, 14);
		Controles.add(lblEntrada);
		
		JLabel lblSalida = new JLabel("SALIDA");
		lblSalida.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblSalida.setBounds(10, 255, 149, 14);
		Controles.add(lblSalida);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 270, 366, 156);
		Controles.add(scrollPane);
		
		textAreaCifrado = new JTextArea();
		scrollPane.setViewportView(textAreaCifrado);
		textAreaCifrado.setRows(10);
		textAreaCifrado.setLineWrap(true);
		textAreaCifrado.setColumns(5);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 80, 366, 144);
		Controles.add(scrollPane_1);
		
		textAreaMensaje = new JTextArea();
		scrollPane_1.setViewportView(textAreaMensaje);
		textAreaMensaje.setRows(10);
		textAreaMensaje.setLineWrap(true);
		textAreaMensaje.setColumns(5);
		
		JButton btnProcesar = new JButton("PROCESAR");
		btnProcesar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				//Inicializacion de variables para control de flujo
				int algoritmoSeleccionado = listadoAlgoritmos.getSelectedIndex();
				int tipoOperacion = validarTipoOperacion();
				
				System.out.println("Algoritmo seleccionado: " + algoritmoSeleccionado);
				System.out.println("Tipo operacion: " + tipoOperacion);
				
				if(algoritmoSeleccionado < 0){
					JOptionPane.showMessageDialog(null, "Debe seleccionar un algoritmo de la lista", ".:: Error de Algoritmo ::.", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				try {
					switch(tipoOperacion){
						case 1:
							procesarCifrado(algoritmoSeleccionado);
							break;
						case 2:
							procesarDecifrado(algoritmoSeleccionado);
							break;
						default:
							JOptionPane.showMessageDialog(null, "Debe seleccionar una de las operaciones de cifrado o decifrado disponibles", ".:: Error de Operación ::.", JOptionPane.ERROR_MESSAGE);
							break;
					}
				} catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, e.getMessage(), ".:: Error Inesperado ::.", JOptionPane.ERROR_MESSAGE);
					textAreaEventos.append(e.getMessage() + "\n");
				}
				
			}
		});
		btnProcesar.setBounds(255, 228, 121, 30);
		Controles.add(btnProcesar);
		
		JPanel Algoritmos = new JPanel();
		getContentPane().add(Algoritmos, BorderLayout.WEST);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Algoritmos Disponibles", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_3.setLayout(new BorderLayout(0, 0));
		
		listadoAlgoritmos = new JList();
		listadoAlgoritmos.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				
				if (!arg0.getValueIsAdjusting()) {
					panel_normal.setVisible(true);
					panel_rsa.setVisible(false);
					switch(listadoAlgoritmos.getSelectedIndex()){
						case 0:
							lblDescripcion.setText("<html>El cifrado Vigenère es un cifrado basado en diferentes series de caracteres o letras del cifrado César formando estos caracteres una tabla, llamada tabla de<br> Vigenère, que se usa como clave. El cifrado de Vigenère es un cifrado de sustitución simple polialfabético.<html>");
							break;
						case 1:
							lblDescripcion.setText("<html>Advanced Encryption Standard (AES), también conocido como Rijndael (pronunciado 'Rain Doll' en inglés), es un esquema de cifrado por bloques adoptado como<br> un estándar de cifrado por el gobierno de los Estados Unidos. El AES fue anunciado por el Instituto Nacional de Estándares y Tecnología (NIST) como FIPS PUB 197 <br>de los Estados Unidos (FIPS 197) el 26 de noviembre de 2001 después de un proceso de estandarización que duró 5 años.<br> Se transformó en un estándar efectivo el 26 de mayo de 2002. Desde 2006, el AES es uno de los algoritmos más populares usados en criptografía simétrica.<html>");
							break;
						case 2:
							panel_rsa.setVisible(true);
							panel_normal.setVisible(false);
							lblDescripcion.setText("<html>En criptografía, RSA (Rivest, Shamir y Adleman) es un sistema criptográfico de clave pública desarrollado en 1979. Es el primer y más utilizado algoritmo<br> de este tipo y es válido tanto para cifrar como para firmar digitalmente.<html>");
							break;
						default:
							lblDescripcion.setText("");
							break;
					}
	            }
			}
		});
		listadoAlgoritmos.setLayoutOrientation(JList.VERTICAL_WRAP);
		panel_3.add(listadoAlgoritmos);
		listadoAlgoritmos.setModel(new AbstractListModel() {
			String[] values = new String[] {"VEGENERE", "AES 256", "RSA"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		listadoAlgoritmos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Modos de Operaci\u00F3n", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		
		grupoAlgoritmos = new ButtonGroup();
		rbtnCifrar = new JRadioButton("CIFRAR");
		grupoAlgoritmos.add(rbtnCifrar);
		rbtnDecifrar = new JRadioButton("DECIFRAR");
		grupoAlgoritmos.add(rbtnDecifrar);
		
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(5)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addComponent(rbtnDecifrar, GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
						.addComponent(rbtnCifrar, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE))
					.addGap(15))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addComponent(rbtnCifrar)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(rbtnDecifrar, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(46))
		);
		panel_1.setLayout(gl_panel_1);
		GroupLayout gl_Algoritmos = new GroupLayout(Algoritmos);
		gl_Algoritmos.setHorizontalGroup(
			gl_Algoritmos.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_Algoritmos.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_Algoritmos.createParallelGroup(Alignment.LEADING)
						.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(panel_3, GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE)))
		);
		gl_Algoritmos.setVerticalGroup(
			gl_Algoritmos.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_Algoritmos.createSequentialGroup()
					.addGap(5)
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(210, Short.MAX_VALUE))
		);
		Algoritmos.setLayout(gl_Algoritmos);
		
		JPanel LogEventos = new JPanel();
		getContentPane().add(LogEventos, BorderLayout.EAST);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Log de Eventos", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		LogEventos.add(panel_2);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		panel_2.add(scrollPane_2);
		
		textAreaEventos = new JTextArea();
		textAreaEventos.setEditable(false);
		textAreaEventos.setLineWrap(true);
		textAreaEventos.setBackground(Color.BLACK);
		textAreaEventos.setForeground(Color.WHITE);
		textAreaEventos.setFont(new Font("Consolas", Font.PLAIN, 13));
		scrollPane_2.setViewportView(textAreaEventos);
		textAreaEventos.setColumns(35);
		textAreaEventos.setRows(24);
		
		JPanel Descripcion = new JPanel();
		getContentPane().add(Descripcion, BorderLayout.NORTH);
		Descripcion.setLayout(new BoxLayout(Descripcion, BoxLayout.X_AXIS));
		
		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		panel.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Descripci\u00F3n del algoritmo", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		Descripcion.add(panel);
		
		lblDescripcion = new JLabel("Seleccione un algoritmo de la lista");
		lblDescripcion.setVerticalAlignment(SwingConstants.TOP);
		panel.add(lblDescripcion);
		lblDescripcion.setHorizontalAlignment(SwingConstants.LEFT);
		lblDescripcion.setFont(new Font("Tahoma", Font.ITALIC, 11));

	}

	protected void procesarDecifrado(int algoritmoSeleccionado) throws Exception {
		
		String respuesta;
		String llave;
		textAreaEventos.append("Obteniendo valor de llave\n");
		String mensajeCifrado = textAreaMensaje.getText();
		
		if(mensajeCifrado.trim().equals(""))
            JOptionPane.showMessageDialog(null,"No se ha introducido el mensaje que se desea descifrar", ".:: Error de Entrada ::.", JOptionPane.ERROR_MESSAGE);
        else {
        	switch(algoritmoSeleccionado) {
				case 0:
					llave = new String(passwordField.getPassword()).toUpperCase();
					System.out.println(llave);
					textAreaEventos.append("Cifrando mensaje\n");
					respuesta = Vigenere.Descifrar(llave, mensajeCifrado.trim());
					textAreaEventos.append("Mensaje descifrado exitosamente\n");
					textAreaCifrado.setText(respuesta);
					break;
				case 1:
					llave = new String(passwordField.getPassword());
					System.out.println(llave);
					AES algoritmoAES = new AES();
					textAreaEventos.append("Descifrando mensaje\n");
					respuesta = algoritmoAES.decrypt(llave, mensajeCifrado.trim());
					textAreaEventos.append("Mensaje descifrado exitosamente\n");
					textAreaCifrado.setText(respuesta);
					break;
				case 2:
					textAreaEventos.append("Descifrando mensaje\n");
					textAreaCifrado.setText("");
	                String recuperarTextoPlano = rsa.desencripta(textoCifrado);
	                textAreaCifrado.setText(recuperarTextoPlano);
	                textAreaEventos.append("Mensaje descifrado exitosamente\n");
	                break;
			}
        }
	}

	protected void procesarCifrado(int algoritmoSeleccionado) throws Exception {
		
		String respuesta;
		String llave;
		String mensajePlano;
		
		textAreaEventos.append("Leyendo llave ingresada y procesando\n");
		mensajePlano = textAreaMensaje.getText(); 
		
		if(mensajePlano.trim().equals(""))
            JOptionPane.showMessageDialog(null,"No ha introducido el mensaje que desea cifrar", ".:: Error de Entrada ::.", JOptionPane.ERROR_MESSAGE);
        else {
        	switch(algoritmoSeleccionado) {
				case 0:
					Vigenere algoritmo = new Vigenere();
					textAreaEventos.append("Validando clave\n");
					llave = new String(passwordField.getPassword()).toUpperCase();
					System.out.println(llave); 
					boolean resultado = algoritmo.claveCorrecta(llave);
			  
					if(resultado == true) { 
						textAreaEventos.append("Clave ingresada OK\n");
						textAreaEventos.append("Iniciando cifrado de mensaje\n");
						respuesta = algoritmo.Cifrar(llave, mensajePlano.trim());
						textAreaEventos.append("Mensaje cifrado exitosamente\n");
						textAreaCifrado.setText(respuesta); 
					}else {
						textAreaEventos.append("Clave ingresada no es válida, verficiar clave\n");
					}
					break;
				case 1:
					textAreaEventos.append("Validando clave\n");
					llave = new String(passwordField.getPassword());
					System.out.println(llave); 
					textAreaEventos.append("Clave ingresada OK\n");
					textAreaEventos.append("Iniciando cifrado de mensaje\n");
					AES algoritmoAES = new AES();
					respuesta = algoritmoAES.encyrpt(llave, mensajePlano.trim());
					textAreaEventos.append("Mensaje cifrado exitosamente\n");
					textAreaCifrado.setText(respuesta);
					break;
				case 2:
					textAreaEventos.append("Obteniendo claves generadas (Números primos)\n");
					textAreaEventos.append("Clave ingresada OK\n");
					textAreaEventos.append("Iniciando cifrado de mensaje\n");
					textoCifrado = rsa.encripta(mensajePlano.trim());
	                System.out.println(textoCifrado);
	                textAreaCifrado.setText("");
	                for(int i=0; i<textoCifrado.length; i++)
	                	textAreaCifrado.append(textoCifrado[i].toString());
	                textAreaEventos.append("Mensaje cifrado exitosamente\n");
					break;
			}
        }
		
	}

	protected int validarTipoOperacion() {
		if (rbtnCifrar.isSelected()) {
			return 1;
		}
		if (rbtnDecifrar.isSelected()) {
			return 2;
		}
		return 0;
	}
}
