package telas;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import util.ConnectDados;

import javax.swing.JComboBox;

import java.awt.Cursor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;

public class Dados extends JDialog {
	private JTextField txtNome;
	private JTextField txtNomeResumido;
	private JTextField txtMunicipio;
	private JTextField txtEndereco;
	private JTextField txtTelefone;
	private JTextField txtCnpj;
	private JTextField txtEmail;
	private JTextField txtSite;
	private ConnectDados dados = new ConnectDados();
	private JComboBox<String> cmbEstado;
	private boolean telaCarregada = false;

	public Dados(Menu parent, boolean modal) {
		super(parent, modal);
		createWindow();
	}

	private void createWindow(){

		getContentPane().setLayout(null);
		setIconImage(Toolkit.getDefaultToolkit().getImage("." + File.separator + "src" + File.separator + "telas" + File.separator + "if_41-File-Document-process_3213319.png"));
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);

		JLabel lblNome = new JLabel("Raz\u00E3o Social");
		lblNome.setBounds(10, 11, 84, 14);
		getContentPane().add(lblNome);

		txtNome = new JTextField();
		txtNome.setBounds(10, 30, 505, 20);
		getContentPane().add(txtNome);
		txtNome.setColumns(10);

		JLabel lblNomeResumido = new JLabel("Nome Fantasia");
		lblNomeResumido.setBounds(10, 61, 104, 14);
		getContentPane().add(lblNomeResumido);

		txtNomeResumido = new JTextField();
		txtNomeResumido.setBounds(8, 79, 176, 20);
		getContentPane().add(txtNomeResumido);
		txtNomeResumido.setColumns(10);

		JLabel lblMunicipio = new JLabel("Munic\u00EDpio");
		lblMunicipio.setBounds(194, 61, 67, 14);
		getContentPane().add(lblMunicipio);

		txtMunicipio = new JTextField();
		txtMunicipio.setBounds(194, 79, 223, 20);
		getContentPane().add(txtMunicipio);
		txtMunicipio.setColumns(10);

		cmbEstado = new JComboBox<String>();
		cmbEstado.setBounds(427, 79, 88, 20);
		getContentPane().add(cmbEstado);

		JLabel lblEstado = new JLabel("Estado");
		lblEstado.setBounds(427, 61, 46, 14);
		getContentPane().add(lblEstado);

		JLabel lblEndereco = new JLabel("Endere\u00E7o");
		lblEndereco.setBounds(10, 110, 114, 14);
		getContentPane().add(lblEndereco);

		txtEndereco = new JTextField();
		txtEndereco.setBounds(10, 131, 505, 20);
		getContentPane().add(txtEndereco);
		txtEndereco.setColumns(10);

		JLabel lblTelefone = new JLabel("Telefone");
		lblTelefone.setBounds(10, 162, 104, 14);
		getContentPane().add(lblTelefone);

		txtTelefone = new JTextField();
		txtTelefone.setBounds(10, 182, 174, 20);
		getContentPane().add(txtTelefone);
		txtTelefone.setColumns(10);

		JLabel lblCnpj = new JLabel("CNPJ");
		lblCnpj.setBounds(194, 162, 46, 14);
		getContentPane().add(lblCnpj);

		txtCnpj = new JTextField();
		txtCnpj.setBounds(194, 182, 321, 20);
		getContentPane().add(txtCnpj);
		txtCnpj.setColumns(10);

		JLabel lblEmail = new JLabel("E-mail");
		lblEmail.setBounds(10, 213, 84, 14);
		getContentPane().add(lblEmail);

		txtEmail = new JTextField();
		txtEmail.setBounds(10, 234, 505, 20);
		getContentPane().add(txtEmail);
		txtEmail.setColumns(10);

		JLabel lblSite = new JLabel("Site");
		lblSite.setBounds(10, 265, 46, 14);
		getContentPane().add(lblSite);

		txtSite = new JTextField();
		txtSite.setBounds(10, 282, 505, 20);
		getContentPane().add(txtSite);
		txtSite.setColumns(10);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(426, 317, 89, 23);
		getContentPane().add(btnCancelar);
		btnCancelar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});

		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.setBounds(328, 317, 89, 23);
		getContentPane().add(btnSalvar);
		btnSalvar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (txtNomeResumido.getText().trim().equals("") && txtNome.getText().trim().equals("") && txtEndereco.getText().trim().equals("") &&
			             txtMunicipio.getText().trim().equals("") && txtTelefone.getText().trim().equals("") && txtEmail.getText().trim().equals("") &&
			             txtSite.getText().trim().equals("") && txtCnpj.getText().trim().equals("")){
					JOptionPane.showMessageDialog(null, "Necess\u00E1rio algum dos compos!");
				}else{
					boolean ok = false;
					setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
					setCursor(Cursor.getDefaultCursor());

					try {

						if(isTelaCarregada()){
							ok = dados.atualizar(txtNomeResumido.getText(), txtNome.getText(), txtEndereco.getText(), cmbEstado.getSelectedItem().toString(),
									             txtMunicipio.getText(), txtTelefone.getText(), txtEmail.getText(), txtSite.getText(), txtCnpj.getText());
						}else{
							ok = dados.salvar(txtNomeResumido.getText(), txtNome.getText(), txtEndereco.getText(), cmbEstado.getSelectedItem().toString(),
						             txtMunicipio.getText(), txtTelefone.getText(), txtEmail.getText(), txtSite.getText(), txtCnpj.getText());
						}

					}catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					if(ok){
						JOptionPane.showMessageDialog(null, "Registros inseridos/atualizados com sucesso!");
					}else{
						JOptionPane.showMessageDialog(null, "Falha ao inserir/atualizar registro!");
					}


				}


			}
		});

		populaComboEstados();
		iniciaComponents();
	}

	private void iniciaComponents(){
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		setCursor(Cursor.getDefaultCursor());
			ArrayList<String> retornoConsulta = new ArrayList<String>();

			try {
				retornoConsulta = dados.carregaDados();
				if(retornoConsulta.get(0).equals("S")){
					  txtNome.setText(retornoConsulta.get(2));
					  txtNomeResumido.setText(retornoConsulta.get(1));
					  txtMunicipio.setText(retornoConsulta.get(5));
					  txtEndereco.setText(retornoConsulta.get(3));
					  txtTelefone.setText(retornoConsulta.get(6));
					  txtCnpj.setText(retornoConsulta.get(9));
					  txtEmail.setText(retornoConsulta.get(7));
					  txtSite.setText(retornoConsulta.get(8));
					  if(!retornoConsulta.get(4).trim().equals("")){
						  int index = 0;

						  while(!cmbEstado.getItemAt(index).equals(retornoConsulta.get(4))) index++;

						  cmbEstado.setSelectedIndex(index);
					  }

					  setTelaCarregada(true);

				}else{
					setTelaCarregada(false);
				}


			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}


	private void populaComboEstados(){
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		setCursor(Cursor.getDefaultCursor());
		ArrayList<String> retornoConsulta = new ArrayList<String>();
		try {
			retornoConsulta = dados.populaComboEstados();

			if(!retornoConsulta.isEmpty())
				cmbEstado.removeAllItems();

			for(String item : retornoConsulta){
				cmbEstado.addItem(item);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	private void setTelaCarregada(boolean telaCarregada){
		this.telaCarregada = telaCarregada;
	}

	private boolean isTelaCarregada(){
		return telaCarregada;
	}
}
