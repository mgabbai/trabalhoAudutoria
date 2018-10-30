package telas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JFrame;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.SwingConstants;

import util.ConnectVerificaLogin;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;



public class Login extends JFrame{
	private JTextField txtUsuario;
	private JPasswordField pwdSenha;
	private JButton btnOk;

	public Login() {
		setIconImage(Toolkit.getDefaultToolkit().getImage("." + File.separator + "src" + File.separator + "telas" + File.separator + "if_41-File-Document-process_3213319.png"));
		createWindow();
	}

	private void createWindow(){

		setResizable(false);

		JLabel lblUsurio = new JLabel("Usu\u00E1rio");

		JLabel lblSenha = new JLabel("Senha");

		txtUsuario = new JTextField();
		txtUsuario.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				if (!txtUsuario.getText().trim().equals("")){
					txtUsuario.select(0, txtUsuario.getText().length());
				}
			}
		});
		txtUsuario.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if(arg0.getKeyCode() == KeyEvent.VK_ENTER){
					pwdSenha.requestFocus();
				}
			}
		});
		txtUsuario.setColumns(10);

		pwdSenha = new JPasswordField();
		pwdSenha.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if(arg0.getKeyCode() == KeyEvent.VK_ENTER){
					btnOk.doClick();
				}
			}
		});

		pwdSenha.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				if (!String.valueOf(pwdSenha.getPassword()).trim().equals("")){
					pwdSenha.select(0, String.valueOf(pwdSenha.getPassword()).length());
				}
			}
		});

		btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				if(txtUsuario.getText().trim().equals("")||String.valueOf(pwdSenha.getPassword()).trim().equals("")){
					JOptionPane.showMessageDialog(null, "Necess\u00E1rio inserir usu\u00E1rio e senha!");
					txtUsuario.requestFocus();
				}else{

					setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

					ConnectVerificaLogin connect = new ConnectVerificaLogin(txtUsuario.getText(), String.valueOf(pwdSenha.getPassword()));

					try {
						if(connect.verificaLogin()){
							Menu principal = new Menu(connect.getPermissao());
							setVisible(false);
							principal.setPreferredSize(new Dimension(500, 500));
							principal.pack();
							principal.setLocationRelativeTo(null);
							principal.setVisible(true);
							dispose();
						}else{
							JOptionPane.showMessageDialog(null, "Usu\u00E1rio ou Senha incorretos!");
							txtUsuario.setText("");
							pwdSenha.setText("");
							txtUsuario.requestFocus();
							setCursor(Cursor.getDefaultCursor());
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
						setCursor(Cursor.getDefaultCursor());
					}


				}
			}
		});

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});

		JLabel lblAtasGenerator = new JLabel("Atas Generator");
		lblAtasGenerator.setHorizontalAlignment(SwingConstants.CENTER);
		lblAtasGenerator.setFont(new Font("Tahoma", Font.PLAIN, 18));
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(42)
					.addComponent(lblAtasGenerator, GroupLayout.PREFERRED_SIZE, 347, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(42)
					.addComponent(lblUsurio, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
					.addGap(20)
					.addComponent(txtUsuario, GroupLayout.PREFERRED_SIZE, 281, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(42)
					.addComponent(lblSenha, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
					.addGap(20)
					.addComponent(pwdSenha, GroupLayout.PREFERRED_SIZE, 281, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(108)
					.addComponent(btnOk, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
					.addGap(35)
					.addComponent(btnCancelar, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(36)
					.addComponent(lblAtasGenerator, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
					.addGap(11)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblUsurio)
						.addComponent(txtUsuario, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(27)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblSenha)
						.addComponent(pwdSenha, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(25)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(btnOk)
						.addComponent(btnCancelar)))
		);
		getContentPane().setLayout(groupLayout);
	}
}
