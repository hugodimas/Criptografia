package com.udb.pantalla;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JDesktopPane;
import java.awt.Color;
import javax.swing.border.BevelBorder;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.border.EtchedBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MDIWindow extends JFrame {

	private JPanel contentPane;
	JDesktopPane desktopPane;
	JMenuBar barraMenu; 

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MDIWindow frame = new MDIWindow();
					frame.setVisible(true);
					frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MDIWindow() {
		setForeground(new Color(51, 0, 204));
		setIconImage(Toolkit.getDefaultToolkit().getImage(MDIWindow.class.getResource("/com/udb/images/appcrypt-icon300.png")));
		setTitle(".:: Criptigraf\u00EDa - Proyecto Final ::.");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 636, 486);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 1, 0, 0));
		//contentPane.setLayout(null);
		
		desktopPane = new JDesktopPane();
		desktopPane.setBackground(new Color(0, 0, 204));
		desktopPane.setBounds(403, 222, -317, -187);
		contentPane.add(desktopPane);
		
		JLabel lblNewLabel = new JLabel(new ImageIcon(MDIWindow.class.getResource("/com/udb/images/franjaUDB.jpg")));
		lblNewLabel.setBackground(new Color(51, 0, 255));
		lblNewLabel.setBounds(-5, 37, 1700, 200);
		desktopPane.add(lblNewLabel);
		
		barraMenu = new JMenuBar();

		JMenu NuevoMenu = new JMenu( "Nuevo" );
		JMenuItem generarClaveMenuItem = new JMenuItem( "Generar clave" );
		generarClaveMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				GenerarClave proceso = new GenerarClave();
				desktopPane.add(proceso);
				proceso.setVisible(true);
			}
		});
		NuevoMenu.add( generarClaveMenuItem );
		
		JMenuItem operacionCifradoAlgoritmosMenuItem = new JMenuItem( "Cifrar/Descifrar" );
		operacionCifradoAlgoritmosMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Operacion proceso = new Operacion();
				desktopPane.add(proceso);
				proceso.setVisible(true);
			}
		});
		NuevoMenu.add( operacionCifradoAlgoritmosMenuItem );
		
		JMenuItem certificadosMenuItem = new JMenuItem( "Generar certificados" );
		certificadosMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Certificados proceso = new Certificados();
				desktopPane.add(proceso);
				proceso.setVisible(true);
			}
		});
		NuevoMenu.add( certificadosMenuItem );
		barraMenu.add( NuevoMenu );
		
		JMenu AcercaDeMenu = new JMenu( "Acerca de" );
		JMenuItem licenciaMenuItem = new JMenuItem( "Licencia" );
		licenciaMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				About proceso = new About();
				desktopPane.add(proceso);
				proceso.setVisible(true);
			}
		});
		AcercaDeMenu.add( licenciaMenuItem );
		barraMenu.add( AcercaDeMenu );
		
		setJMenuBar( barraMenu );
	}
}
