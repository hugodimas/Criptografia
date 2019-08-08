package com.udb.pantalla;

import java.awt.EventQueue;
import java.beans.PropertyVetoException;
import java.util.Date;
import java.util.Properties;

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
import com.udb.algoritmos.GeneradorCertificados;
import com.udb.algoritmos.Vigenere;
import com.udb.util.DateLabelFormatter;

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
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.AbstractListModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.event.ListSelectionListener;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.event.ListSelectionEvent;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SpringLayout;

public class Certificados extends JInternalFrame {
	JLabel lblDescripcion;
	private JTextField txtUbicacion;
	private JFileChooser chooser;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Certificados frame = new Certificados();
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
	public Certificados() {
		setIconifiable(true);
		setClosable(true);
		setTitle(".:: Criptograf\u00EDa - Generar Certificados ::.");
		setFrameIcon(new ImageIcon(Certificados.class.getResource("/com/udb/images/appcrypt-icon16.png")));
		setBounds(100, 100, 493, 194);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel Controles = new JPanel();
		getContentPane().add(Controles, BorderLayout.CENTER);
		Controles.setLayout(null);
		
		JLabel lblFechaExpiracin = new JLabel("FECHA EXPIRACION");
		lblFechaExpiracin.setBounds(21, 11, 111, 14);
		Controles.add(lblFechaExpiracin);
		lblFechaExpiracin.setVerticalAlignment(SwingConstants.TOP);
		lblFechaExpiracin.setToolTipText("");
		lblFechaExpiracin.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		UtilDateModel model = new UtilDateModel();
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		SpringLayout springLayout = (SpringLayout) datePicker.getLayout();
		springLayout.putConstraint(SpringLayout.SOUTH, datePicker.getJFormattedTextField(), 0, SpringLayout.SOUTH, datePicker);
		datePicker.setLocation(142, 5);
		datePicker.setSize(313, 31);
		 
		Controles.add(datePicker);
		
		JButton btnProcesar = new JButton("GENERAR");
		btnProcesar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					
					if(txtUbicacion.getText().length() <= 0){
						JOptionPane.showMessageDialog(null, "Seleccione una ubicación para almacenar los certificados a generar", ".:: Ubicación de Almacenamiento ::.", JOptionPane.ERROR_MESSAGE);
						return;
					}
					
					Date selectedDate = (Date) datePicker.getModel().getValue();
					System.out.println(selectedDate.toString());
					GeneradorCertificados.generarCertificados(txtUbicacion.getText(), selectedDate);
					JOptionPane.showMessageDialog(null, "Certificados generados exitosamente, verifique la ubicación", ".:: Proceso Finalizado ::.", JOptionPane.INFORMATION_MESSAGE);
					
				} catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, e.getMessage(), ".:: Error Inesperado ::.", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		btnProcesar.setBounds(333, 75, 121, 30);
		Controles.add(btnProcesar);
		
		JLabel lblUbicacion = new JLabel("UBICACION");
		lblUbicacion.setVerticalAlignment(SwingConstants.TOP);
		lblUbicacion.setToolTipText("");
		lblUbicacion.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblUbicacion.setBounds(21, 44, 111, 14);
		Controles.add(lblUbicacion);
		
		txtUbicacion = new JTextField();
		txtUbicacion.setEditable(false);
		txtUbicacion.setColumns(10);
		txtUbicacion.setBounds(142, 39, 284, 25);
		Controles.add(txtUbicacion);
		
		JButton button = new JButton("...");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				chooser = new JFileChooser(); 
			    chooser.setCurrentDirectory(new java.io.File("."));
			    chooser.setDialogTitle("Seleccione ubicación de almacenamiento");
			    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			    chooser.setAcceptAllFileFilterUsed(false);
			    
			    if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) { 
			    	txtUbicacion.setText(chooser.getSelectedFile().getAbsolutePath());
			    }
			}
		});
		button.setBounds(427, 39, 28, 25);
		Controles.add(button);
		
		JPanel Descripcion = new JPanel();
		getContentPane().add(Descripcion, BorderLayout.NORTH);
		Descripcion.setLayout(new BoxLayout(Descripcion, BoxLayout.X_AXIS));
		
		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		panel.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Indicaciones", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		Descripcion.add(panel);
		
		lblDescripcion = new JLabel("Ingrese los campos solicitados para generar los certificados");
		panel.add(lblDescripcion);
		lblDescripcion.setHorizontalAlignment(SwingConstants.LEFT);
		lblDescripcion.setFont(new Font("Tahoma", Font.ITALIC, 11));

	}
}
