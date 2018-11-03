package telas;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import org.eclipse.wb.swing.FocusTraversalOnArray;

import util.ConnectUsuario;

import java.awt.Component;
import java.awt.List;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JComboBox;

public class CadastroUsuario extends JDialog {
	private JTextField txtNome;
	private JTextField txtEmail;
	private JTextField txtUsuario;
	private JPasswordField pwdSenha;
	private JCheckBox chckbxAuditor;
	private boolean consultaRealizada = false;
	private JComboBox<String> cmbUsuario;
	private JCheckBox chckbxUsurio;
	private ConnectUsuario connUser = new ConnectUsuario();
	private boolean wait = false;

	public CadastroUsuario() {
		createWindow();
	}

	private void createWindow(){



		setIconImage(Toolkit.getDefaultToolkit().getImage("." + File.separator + "src" + File.separator + "telas" + File.separator + "if_41-File-Document-process_3213319.png"));

		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);

		JLabel lblNome = new JLabel("Nome *");
		lblNome.setBounds(58, 23, 46, 14);
		getContentPane().add(lblNome);

		JLabel lblSenha = new JLabel("Senha *");
		lblSenha.setBounds(58, 184, 46, 14);
		getContentPane().add(lblSenha);

		JLabel lblEmail = new JLabel("Email");
		lblEmail.setBounds(58, 79, 46, 14);
		getContentPane().add(lblEmail);

		JLabel lblPermissao = new JLabel("Permiss\u00E3o");
		lblPermissao.setBounds(62, 250, 100, 14);
		getContentPane().add(lblPermissao);

		JLabel lblUsuario = new JLabel("Usu\u00E1rio *");
		lblUsuario.setBounds(58, 131, 66, 14);
		getContentPane().add(lblUsuario);

		chckbxAuditor = new JCheckBox("Auditor");
		chckbxAuditor.setBounds(177, 271, 97, 23);
		getContentPane().add(chckbxAuditor);

		txtNome = new JTextField();
		txtNome.setBounds(58, 48, 479, 20);
		getContentPane().add(txtNome);
		txtNome.setColumns(10);
		txtNome.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				if (!txtNome.getText().trim().equals("")){
					txtNome.select(0, txtNome.getText().length());
				}
			}
		});

		txtEmail = new JTextField();
		txtEmail.setBounds(58, 100, 479, 20);
		getContentPane().add(txtEmail);
		txtEmail.setColumns(10);
		txtEmail.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				if (!txtEmail.getText().trim().equals("")){
					txtEmail.select(0, txtEmail.getText().length());
				}
			}
		});

		txtUsuario = new JTextField();
		txtUsuario.setBounds(58, 156, 224, 20);
		getContentPane().add(txtUsuario);
		txtUsuario.setColumns(10);
		txtUsuario.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				if (!txtUsuario.getText().trim().equals("")){
					txtUsuario.select(0, txtUsuario.getText().length());
				}
			}
		});

		pwdSenha = new JPasswordField();
		pwdSenha.setBounds(58, 209, 224, 20);
		getContentPane().add(pwdSenha);
		pwdSenha.setColumns(10);
		pwdSenha.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				if (!String.valueOf(pwdSenha.getPassword()).trim().equals("")){
					pwdSenha.select(0,String.valueOf(pwdSenha.getPassword()).length());
				}
			}
		});

		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.setBounds(347, 326, 89, 23);
		getContentPane().add(btnSalvar);
		btnSalvar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (txtNome.getText().trim().equals("")||txtUsuario.getText().trim().equals("")||String.valueOf(pwdSenha.getPassword()).trim().equals("")){
					JOptionPane.showMessageDialog(null, "Necess\u00E1rio preencher todos os campos obrigat\u00F3ios");
				}else{
					int permissao;
					boolean ok = false;
					if(chckbxAuditor.isSelected())
						permissao = 1;
					else
						permissao = 2;

					try {
						if(txtEmail.getText().trim().equals("")){
							if(isConsultaRealizada()){
								ok = connUser.atualizar(txtNome.getText(), txtUsuario.getText(), String.valueOf(pwdSenha.getPassword()), permissao);
							}else{
								ok = connUser.salvar(txtNome.getText(), txtUsuario.getText(), String.valueOf(pwdSenha.getPassword()), permissao);
							}
						}else{
							if(isConsultaRealizada()){
								ok = connUser.atualizar(txtNome.getText(), txtEmail.getText(), txtUsuario.getText(), String.valueOf(pwdSenha.getPassword()), permissao);
							}else{
								ok = connUser.salvar(txtNome.getText(), txtEmail.getText(), txtUsuario.getText(), String.valueOf(pwdSenha.getPassword()), permissao);
							}
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					if(ok){
						JOptionPane.showMessageDialog(null, "Registros inseridos/atualizados com sucesso!");
						limpar();
					}else{
						JOptionPane.showMessageDialog(null, "Falha ao inserir/atualizar registro!");
						limpar();
					}


				}

			}
		});

		JButton btnNovo = new JButton("Novo");
		btnNovo.setBounds(248, 326, 89, 23);
		getContentPane().add(btnNovo);
		btnNovo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				limpar();
			}
		});

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(448, 326, 89, 23);
		getContentPane().add(btnCancelar);
		btnCancelar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});

		cmbUsuario = new JComboBox<String>();
		cmbUsuario.setBounds(309, 156, 228, 20);
		getContentPane().add(cmbUsuario);

		JButton btnLimpar = new JButton("Limpar");
		btnLimpar.setBounds(58, 326, 89, 23);
		getContentPane().add(btnLimpar);

		chckbxUsurio = new JCheckBox("Usu\u00E1rio");
		chckbxUsurio.setBounds(58, 271, 117, 23);
		getContentPane().add(chckbxUsurio);

		btnLimpar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				limpar();
			}
		});


		cmbUsuario.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(!wait)
					populaTelaClickCombo();
			}
		});



		setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{txtNome, txtEmail, txtUsuario, pwdSenha, chckbxAuditor, btnSalvar, btnLimpar, btnCancelar, getContentPane(), lblNome, lblSenha, lblEmail, lblPermissao, lblUsuario}));
		limpar();
	}

	private void limpar(){
		txtEmail.setText("");
		txtNome.setText("");
		txtUsuario.setText("");
		pwdSenha.setText("");
		chckbxAuditor.setSelected(false);
		txtUsuario.setEnabled(true);
		setConsultaRealizada(false);
		txtNome.requestFocus(true);
		wait = true;
		populaComboUsuario();
		wait = false;
		cmbUsuario.setSelectedIndex(0);
		chckbxUsurio.setSelected(true);
	}

	private void setConsultaRealizada(boolean consultaRealizada){
		this.consultaRealizada = consultaRealizada;
	}

	private boolean isConsultaRealizada(){
		return consultaRealizada;
	}

	private void populaTelaClickCombo(){
		if( !cmbUsuario.getSelectedItem().equals("")){
			ArrayList<String> retornoConsulta = new ArrayList<String>();

			try {
				retornoConsulta = connUser.consultar(cmbUsuario.getSelectedItem().toString());
				if(!retornoConsulta.get(0).equals("S")){
					JOptionPane.showMessageDialog(null, "Erro ao recuperar as informações!");
				}else{

						txtNome.setText(retornoConsulta.get(1));
						txtUsuario.setText(retornoConsulta.get(2));
						pwdSenha.setText(retornoConsulta.get(3));
						if(retornoConsulta.get(4).equals("1")){
							chckbxAuditor.setSelected(true);
						}
						txtEmail.setText(retornoConsulta.get(5));

						txtUsuario.setEnabled(false);

						chckbxUsurio.setSelected(true);


				}


			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

				setConsultaRealizada(true);

		}
	}

	private void populaComboUsuario(){
		ArrayList<String> retornoConsulta = new ArrayList<String>();
		try {
			retornoConsulta = connUser.populaComboUsuario();

			if(!retornoConsulta.isEmpty())
				cmbUsuario.removeAllItems();

			for(String item : retornoConsulta){
				cmbUsuario.addItem(item);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
