package telas;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTable;
import java.awt.Color;
import java.awt.Toolkit;
import java.io.File;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JTextField;
import javax.swing.JLabel;

public class ConsultaAta extends JDialog{
	private JTable table;
	private JTextField txtNumeroAta;
	public ConsultaAta() {

		setIconImage(Toolkit.getDefaultToolkit().getImage("." + File.separator + "src" + File.separator + "telas" + File.separator + "if_41-File-Document-process_3213319.png"));

		getContentPane().setLayout(null);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(496, 359, 89, 23);
		getContentPane().add(btnCancelar);

		JButton btnConsultar = new JButton("Consultar");
		btnConsultar.setBounds(397, 359, 89, 23);
		getContentPane().add(btnConsultar);

		table = new JTable();
		table.setBackground(Color.WHITE);
		table.setBounds(34, 333, 527, -235);
		getContentPane().add(table);

		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(336, 50, 184, 20);
		getContentPane().add(comboBox);

		txtNumeroAta = new JTextField();
		txtNumeroAta.setBounds(34, 50, 131, 20);
		getContentPane().add(txtNumeroAta);
		txtNumeroAta.setColumns(10);

		JLabel lblNmeroDaAta = new JLabel("N\u00FAmero da Ata");
		lblNmeroDaAta.setBounds(34, 25, 110, 14);
		getContentPane().add(lblNmeroDaAta);

		JLabel lblUsurio = new JLabel("Usu\u00E1rio");
		lblUsurio.setBounds(336, 25, 46, 14);
		getContentPane().add(lblUsurio);
	}
}
