package telas;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;


public class Menu extends JFrame{
	private int permissao;
	public Menu(int permissao) {

		setIconImage(Toolkit.getDefaultToolkit().getImage("." + File.separator + "src" + File.separator + "telas" + File.separator + "if_41-File-Document-process_3213319.png"));

		this.permissao = permissao;

		createWindow();
	}

	private void createWindow(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		if (permissao == 0){
			JMenu mnCadastro = new JMenu("Cadastro");
			menuBar.add(mnCadastro);

			JMenuItem mntmUsurios = new JMenuItem("Usu\u00E1rios");
			mnCadastro.add(mntmUsurios);
			mntmUsurios.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					CadastroUsuario usuario = new CadastroUsuario();
					usuario.setPreferredSize(new Dimension(610, 430));
					usuario.pack();
					usuario.setLocationRelativeTo(null);
					usuario.setVisible(true);

				}
			});
		}

		if(permissao <= 1){
			JMenu mnAuditoria = new JMenu("Auditoria");
			menuBar.add(mnAuditoria);

			JMenuItem mntmConsultarLogs = new JMenuItem("Consultar logs");
			mnAuditoria.add(mntmConsultarLogs);
			mntmConsultarLogs.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub

				}
			});
		}

		if(permissao <= 2){
			JMenu mnAtas = new JMenu("Atas");
			menuBar.add(mnAtas);

			JMenuItem mntmCadastrar = new JMenuItem("Cadastrar");
			mnAtas.add(mntmCadastrar);
			mntmCadastrar.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub

				}
			});

			JMenuItem mntmConsultar = new JMenuItem("Consultar");
			mnAtas.add(mntmConsultar);
			mntmConsultar.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub

				}
			});
		}

		JLabel background=new JLabel(new ImageIcon("." + File.separator + "src" + File.separator + "telas" + File.separator + "if_41-File-Document-process_3213319.png"));
        add(background);
        background.setLayout(new FlowLayout());

		setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);

	}
}
