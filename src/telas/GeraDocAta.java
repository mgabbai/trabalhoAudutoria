package telas;

import javax.swing.JButton;

import java.awt.Cursor;
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
import javax.swing.JOptionPane;

public class GeraDocAta extends JDialog{
	private JTextField txtDiretorio;
	private JComboBox<String> cmbNumeroAta;
	private JButton btnGerar;
	private ConnectDoc doc = new ConnectDoc();
	private boolean alterouCaminho = false;
	private boolean inseriuCaminho = false;
	private String retorno;
	public GeraDocAta(Menu parent, boolean modal) {
		super(parent, modal);
		createWindow();

	}

	public void createWindow(){

		setIconImage(Toolkit.getDefaultToolkit().getImage("." + File.separator + "src" + File.separator + "telas" + File.separator + "if_41-File-Document-process_3213319.png"));
		setResizable(false);
		getContentPane().setLayout(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

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
		txtDiretorio.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(retorno.equals(txtDiretorio.getText()))
					setAlterouCaminho(true);
				else
					setAlterouCaminho(false);

			}
		});

		btnGerar = new JButton("Gerar");
		btnGerar.setBounds(185, 114, 89, 23);
		getContentPane().add(btnGerar);
		btnGerar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				geraAta();
				setCursor(Cursor.getDefaultCursor());
			}
		});

		iniciaComponents();
	}

	private void iniciaComponents(){
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		retorno = doc.getPath();

		if(retorno.trim().equals("")){
			String caminho = null;
			caminho = JOptionPane.showInputDialog("Sem cadastro de caminho para salvar arquivo gerado, por favor digite o caminho desejado:");

			if(caminho.trim().equals("")){
				dispose();
			}else{
				txtDiretorio.setText(caminho);
				setInseriuCaminho(true);
			}

		}else{
			txtDiretorio.setText(retorno);
			setInseriuCaminho(false);
		}
		populaComboNumeroAta();
		setCursor(Cursor.getDefaultCursor());
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
		try{
			if(cmbNumeroAta.getSelectedItem().toString().trim().equals("")){
				JOptionPane.showMessageDialog(this, "Necessário selecionar numero de Ata!");
			}else{
				doc.geraDoc(cmbNumeroAta.getSelectedItem().toString(), isInseriuCaminho(), isAlterouCaminho(), path(txtDiretorio.getText()));
				iniciaComponents();
			}
		}catch (Exception e) {
			e.printStackTrace();
		}

	}

	private String path(String path){

		path = path.replace("\\", File.separator);
		path = path.replace("/", File.separator);

		if(path.endsWith(File.separator)){
			path = path.substring(0, path.length()-1);
		}

		return path;
	}

	private void setAlterouCaminho(boolean alterouCaminho){
		this.alterouCaminho = alterouCaminho;
	}

	private boolean isAlterouCaminho(){
		return alterouCaminho;
	}

	private void setInseriuCaminho(boolean inseriuCaminho){
		this.inseriuCaminho = inseriuCaminho;
	}

	private boolean isInseriuCaminho(){
		return inseriuCaminho;
	}
}
