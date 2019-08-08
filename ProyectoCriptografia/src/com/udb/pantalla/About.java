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

public class About extends JInternalFrame {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					About frame = new About();
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
	public About() {
		setIconifiable(true);
		setClosable(true);
		setTitle(".:: Criptograf\u00EDa - Licencia ::.");
		setFrameIcon(new ImageIcon(About.class.getResource("/com/udb/images/appcrypt-icon16.png")));
		setBounds(100, 100, 493, 337);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JTextArea txtrCopyrightcThis = new JTextArea();
		txtrCopyrightcThis.setEditable(false);
		txtrCopyrightcThis.setLineWrap(true);
		txtrCopyrightcThis.setText("Copyright (C) 2019 Nathaly Flores, Hugo Dimas\r\n\r\nThis program is free software: you can redistribute it and/or modify\r\nit under the terms of the GNU General Public License as published by\r\nthe Free Software Foundation, either version 3 of the License, or\r\n(at your option) any later version.\r\n\r\nThis program is distributed in the hope that it will be useful,\r\nbut WITHOUT ANY WARRANTY; without even the implied warranty of\r\nMERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the\r\nGNU General Public License for more details.\r\n\r\nYou should have received a copy of the GNU General Public License\r\nalong with this program.  If not, see <http://www.gnu.org/licenses/>");
		txtrCopyrightcThis.setRows(10);
		txtrCopyrightcThis.setColumns(10);
		getContentPane().add(txtrCopyrightcThis, BorderLayout.CENTER);

	}

	
}
