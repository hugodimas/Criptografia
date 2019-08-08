package com.udb.pantalla;

import java.awt.EventQueue;
import java.beans.PropertyVetoException;

import javax.swing.JInternalFrame;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.SwingConstants;
import javax.swing.JPasswordField;
import java.awt.GridLayout;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;

import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import com.udb.algoritmos.AES;
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

public class GenerarClave extends JInternalFrame {
	private JPasswordField passwordField;
	JTextArea textAreaMensaje;
	JLabel lblDescripcion;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GenerarClave frame = new GenerarClave();
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
	public GenerarClave() {
		setIconifiable(true);
		setClosable(true);
		setTitle(".:: Criptograf\u00EDa - Generar Clave ::.");
		setFrameIcon(new ImageIcon(GenerarClave.class.getResource("/com/udb/images/appcrypt-icon16.png")));
		setBounds(100, 100, 493, 337);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel Controles = new JPanel();
		getContentPane().add(Controles, BorderLayout.CENTER);
		Controles.setLayout(null);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(21, 30, 434, 20);
		Controles.add(passwordField);
		
		JLabel label = new JLabel("LLAVE");
		label.setBounds(21, 11, 33, 14);
		Controles.add(label);
		label.setVerticalAlignment(SwingConstants.TOP);
		label.setToolTipText("");
		label.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		JLabel lblEntrada = new JLabel("ENTRADA");
		lblEntrada.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblEntrada.setBounds(21, 75, 149, 14);
		Controles.add(lblEntrada);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(21, 92, 434, 144);
		Controles.add(scrollPane_1);
		
		textAreaMensaje = new JTextArea();
		scrollPane_1.setViewportView(textAreaMensaje);
		textAreaMensaje.setRows(10);
		textAreaMensaje.setLineWrap(true);
		textAreaMensaje.setColumns(5);
		
		JButton btnProcesar = new JButton("GENERAR");
		btnProcesar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					String llave = new String(passwordField.getPassword()).toUpperCase();
					AES objeto = new AES();
					String respuesta = objeto.generarClave(llave);
					
					textAreaMensaje.setText("");
					textAreaMensaje.append("SALT: " + objeto.getStringSALT() + "\n");
					textAreaMensaje.append("IV: " + objeto.getStringIV() + "\n");
					textAreaMensaje.append("KEY: " + objeto.getStringKEY() + "\n");
					textAreaMensaje.append("CLAVE FINAL: " + respuesta + "\n");
				} catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, e.getMessage(), ".:: Error Inesperado ::.", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		btnProcesar.setBounds(333, 55, 121, 30);
		Controles.add(btnProcesar);
		
		JPanel Descripcion = new JPanel();
		getContentPane().add(Descripcion, BorderLayout.NORTH);
		Descripcion.setLayout(new BoxLayout(Descripcion, BoxLayout.X_AXIS));
		
		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		panel.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Indicaciones", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		Descripcion.add(panel);
		
		lblDescripcion = new JLabel("Ingrese palabra clave para generar los par\u00E1metros de clave a utilizar para algoritmo AES256");
		panel.add(lblDescripcion);
		lblDescripcion.setHorizontalAlignment(SwingConstants.LEFT);
		lblDescripcion.setFont(new Font("Tahoma", Font.ITALIC, 11));

	}

	
}
