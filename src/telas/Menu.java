package telas;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JMenu;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;


public class Menu extends JFrame{
	private int permissao;

	public Menu(int permissao) {
		this.permissao = permissao;
		createWindow();
	}

	private void createWindow(){

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e){
				String[] option = {"Sim", "Não"};

		        int answer = JOptionPane.showOptionDialog(getContentPane(), "Deseja realmente sair do sistema?", "Atenção",
		                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, option, null);

				if(answer == 0)
					System.exit(0);

			}


		});

		setIconImage(Toolkit.getDefaultToolkit().getImage("." + File.separator + "src" + File.separator + "telas" + File.separator + "if_41-File-Document-process_3213319.png"));

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
					CadastroUsuario usuario = new CadastroUsuario(parent(), true);
					usuario.setPreferredSize(new Dimension(610, 430));
					usuario.pack();
					usuario.setLocationRelativeTo(null);
					usuario.setVisible(true);

				}
			});

			JMenuItem mntmDados = new JMenuItem("Dados");
			mnCadastro.add(mntmDados);
			mntmDados.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					Dados dados = new Dados(parent(), true);
					dados.setPreferredSize(new Dimension(541, 390));
					dados.pack();
					dados.setLocationRelativeTo(null);
					dados.setVisible(true);

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
					Auditoria auditoria = new Auditoria(parent(), true);
					auditoria.setPreferredSize(new Dimension(905, 619));
					auditoria.pack();
					auditoria.setLocationRelativeTo(null);
					auditoria.setVisible(true);

				}
			});
		}


		if(permissao == 0 || permissao > 1){
			JMenu mnAtas = new JMenu("Atas");
			menuBar.add(mnAtas);

			JMenuItem mntmCadastrar = new JMenuItem("Cadastrar");
			mnAtas.add(mntmCadastrar);
			mntmCadastrar.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					CadastraAta ata = new CadastraAta(0, parent(), true);
					ata.setPreferredSize(new Dimension(969, 649));
					ata.pack();
					ata.setLocationRelativeTo(null);
					ata.setVisible(true);

				}
			});

			JMenuItem mntmConsultar = new JMenuItem("Consultar");
			mnAtas.add(mntmConsultar);
			mntmConsultar.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					CadastraAta ata = new CadastraAta(1, parent(), true);
					ata.setPreferredSize(new Dimension(969, 649));
					ata.pack();
					ata.setLocationRelativeTo(null);
					ata.setVisible(true);
				}
			});

			JMenuItem mntmGeraDoc = new JMenuItem("Gerar .doc");
			mnAtas.add(mntmGeraDoc);


			mntmGeraDoc.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					GeraDocAta doc = new GeraDocAta(parent(), true);
					doc.setPreferredSize(new Dimension(482, 205));
					doc.pack();
					doc.setLocationRelativeTo(null);
					doc.setVisible(true);
				}
			});
		}

		JMenu mnOpes = new JMenu("Op\u00E7\u00F5es");
		menuBar.add(mnOpes);


		JMenuItem mntmLogout = new JMenuItem("Logout");
		mnOpes.add(mntmLogout);
		mntmLogout.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				String[] option = {"Sim", "Não"};

		        int answer = JOptionPane.showOptionDialog(getContentPane(), "Deseja realmente trocar usuário?", "Atenção",
		                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, option, null);

		        if(answer == 0){
		        	dispose();
					EventQueue.invokeLater(new Runnable() {
			            public void run() {
			                try {
			                   Login inicia = new Login();
			                   inicia.setPreferredSize(new Dimension(450, 330));
			                   inicia.pack();
			                   inicia.setLocationRelativeTo(null);
			                   inicia.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			                   inicia.setVisible(true);
			                  } catch (Exception e) {
			                    e.printStackTrace();
			                }
			            }
			        });
		        }

			}
		});

		JMenuItem mntmSair = new JMenuItem("Sair");
		mnOpes.add(mntmSair);
		mntmSair.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				String[] option = {"Sim", "Não"};

		        int answer = JOptionPane.showOptionDialog(getContentPane(), "Deseja realmente sair do sistema?", "Atenção",
		                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, option, null);

		        if(answer == 0){
		        	System.exit(0);
		        }

			}
		} );

		JLabel background=new JLabel(new ImageIcon("." + File.separator + "src" + File.separator + "telas" + File.separator + "if_41-File-Document-process_3213319.png"));
        getContentPane().add(background);
        background.setLayout(new FlowLayout());

		setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);

	}

	private Menu parent(){
		return this;
	}

}
