package telas;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTable;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JTextField;

import util.ConnectDoc;

import javax.swing.JLabel;

public class GeraDocAta extends JDialog{
	private JTextField txtDiretorio;
	private JComboBox<String> cmbNumeroAta;
	private JButton btnGerar;
	private ConnectDoc doc = new ConnectDoc();
	public GeraDocAta() {
		createWindow();

	}

	public void createWindow(){
		setIconImage(Toolkit.getDefaultToolkit().getImage("." + File.separator + "src" + File.separator + "telas" + File.separator + "if_41-File-Document-process_3213319.png"));

		getContentPane().setLayout(null);

		JLabel lblNumroAta = new JLabel("Num\u00E9ro Ata");
		lblNumroAta.setBounds(10, 11, 93, 14);
		getContentPane().add(lblNumroAta);

		cmbNumeroAta = new JComboBox<String>();
		cmbNumeroAta.setBounds(10, 32, 93, 20);
		getContentPane().add(cmbNumeroAta);

		JLabel lblDiretrioParaSalvar = new JLabel("Diret\u00F3rio para salvar documento gerado");
		lblDiretrioParaSalvar.setBounds(10, 63, 446, 14);
		getContentPane().add(lblDiretrioParaSalvar);

		txtDiretorio = new JTextField();
		txtDiretorio.setBounds(10, 83, 446, 20);
		getContentPane().add(txtDiretorio);
		txtDiretorio.setColumns(10);

		btnGerar = new JButton("Gerar");
		btnGerar.setBounds(185, 114, 89, 23);
		getContentPane().add(btnGerar);
		btnGerar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				geraAta();

			}
		});

		iniciaComponents();
	}

	private void iniciaComponents(){
		txtDiretorio.setText("C:"+File.separator+"temp");
		populaComboNumeroAta();
	}

	private void populaComboNumeroAta(){
		ArrayList<String> retornoConsulta = new ArrayList<String>();
		try {
			retornoConsulta = doc.populaComboNumeroAta();

			if(!retornoConsulta.isEmpty())
				cmbNumeroAta.removeAllItems();

			for(String item : retornoConsulta){
				cmbNumeroAta.addItem(item);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void geraAta(){
		doc.geraDoc(cmbNumeroAta.getSelectedItem().toString(), path(txtDiretorio.getText()));
		iniciaComponents();
	}

	private String path(String path){

		path = path.replace("\\", File.separator);
		path = path.replace("/", File.separator);

		if(path.endsWith(File.separator)){
			path = path.substring(0, path.length()-1);
		}

		return path;
	}
}
