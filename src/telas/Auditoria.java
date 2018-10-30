package telas;

import javax.swing.JDialog;
import javax.swing.JFormattedTextField;

import java.awt.Component;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JTable;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javafx.scene.control.DatePicker;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

public class Auditoria extends JDialog{
	private JTable table;
	private JTextField txtDataDe;
	private JTextField txtDataAte;
	public Auditoria() {
		getContentPane().setLayout(null);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(789, 532, 89, 23);
		getContentPane().add(btnCancelar);

		JButton btnConsultar = new JButton("Consultar");
		btnConsultar.setBounds(671, 532, 89, 23);
		getContentPane().add(btnConsultar);

		table = new JTable();
		table.setBounds(41, 160, 815, 361);

		getContentPane().add(table);

		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(41, 99, 273, 20);
		getContentPane().add(comboBox);

		JLabel lblUsuario = new JLabel("Usuario");
		lblUsuario.setBounds(41, 74, 46, 14);
		getContentPane().add(lblUsuario);

		JCheckBox chckbxInsert = new JCheckBox("Insert");
		chckbxInsert.setBounds(351, 59, 97, 23);
		getContentPane().add(chckbxInsert);

		JCheckBox chckbxUpdate = new JCheckBox("Update");
		chckbxUpdate.setBounds(351, 85, 97, 23);
		getContentPane().add(chckbxUpdate);

		JCheckBox chckbxDelete = new JCheckBox("Delete");
		chckbxDelete.setBounds(351, 111, 97, 23);
		getContentPane().add(chckbxDelete);

		JLabel lblOperao = new JLabel("Opera\u00E7\u00E3o");
		lblOperao.setBounds(351, 38, 97, 14);
		getContentPane().add(lblOperao);

		MaskFormatter dateMask = null;
		try {
		    dateMask = new MaskFormatter("##/##/####");
		    dateMask.setPlaceholderCharacter('/');
		    dateMask.setValidCharacters("0123456789");
		} catch (ParseException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}

		txtDataDe = new JFormattedTextField(dateMask);
		txtDataDe.setBounds(647, 99, 122, 20);
		getContentPane().add(txtDataDe);
		txtDataDe.setColumns(10);

		txtDataAte = new JFormattedTextField(dateMask);
		txtDataAte.setBounds(499, 99, 122, 20);
		getContentPane().add(txtDataAte);
		txtDataAte.setColumns(10);

		JLabel lblDe = new JLabel("De");
		lblDe.setBounds(499, 74, 46, 14);
		getContentPane().add(lblDe);

		JLabel lblAte = new JLabel("Ate");
		lblAte.setBounds(647, 74, 46, 14);
		getContentPane().add(lblAte);

	}
}
