package telas;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import util.ConnectAta;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.JTextArea;
import javax.swing.JButton;

public class CadastraAta extends JDialog {
	private JTextField txtNumeroAta;
	private JTextField txtDataAta;
	private JLabel lblConteudoDaReunio;
	private JTextArea txtrConteudo;
	private JScrollPane scrollPaneConteudo;
	private JTextArea txtrPresentes;
	private JScrollPane scrollPanePresentes;
	private JButton btnCancelar;
	private JButton btnSalvar;
	private JButton btnAtras;
	private JButton btnFrente;
	private JButton btnFim;
	private JButton btnInicio;
	private LocalDateTime data;
	private boolean novaAta = false;

	ConnectAta connAta = new ConnectAta();
	private JButton btnNovo;

	public CadastraAta(int tipo, Menu parent, boolean modal) {
		super(parent, modal);

		createWindow();
		if(tipo == 0)
			iniciaComponentes();
		else
			iniciaAta();
	}

	private void createWindow(){
		setIconImage(Toolkit.getDefaultToolkit().getImage("." + File.separator + "src" + File.separator + "telas" + File.separator + "if_41-File-Document-process_3213319.png"));
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		getContentPane().setLayout(null);

		JLabel lblNmeroDaAta = new JLabel("N\u00FAmero da Ata");
		lblNmeroDaAta.setBounds(10, 11, 145, 14);
		getContentPane().add(lblNmeroDaAta);

		txtNumeroAta = new JTextField();
		txtNumeroAta.setBackground(Color.LIGHT_GRAY);
		txtNumeroAta.setBounds(10, 28, 86, 20);
		getContentPane().add(txtNumeroAta);
		txtNumeroAta.setColumns(10);
		txtNumeroAta.setEditable(false);


		JLabel lblData = new JLabel("Data");
		lblData.setBounds(856, 37, 86, 14);
		getContentPane().add(lblData);

		txtDataAta = new JTextField();
		txtDataAta.setBounds(856, 54, 86, 20);
		getContentPane().add(txtDataAta);
		txtDataAta.setColumns(10);
		txtDataAta.setEditable(false);

		lblConteudoDaReunio = new JLabel("Conte\u00FAdo da Reuni\u00E3o");
		lblConteudoDaReunio.setBounds(10, 65, 191, 14);
		getContentPane().add(lblConteudoDaReunio);

		txtrConteudo = new JTextArea();
		txtrConteudo.setBounds(10, 90, 932, 299);
		txtrConteudo.setLineWrap(true);
		txtrConteudo.setWrapStyleWord(true);
		scrollPaneConteudo = new JScrollPane(txtrConteudo);
		scrollPaneConteudo.setBounds(10, 90, 932, 299);
		getContentPane().add(scrollPaneConteudo);


		JLabel lblPresentes = new JLabel("Presentes");
		lblPresentes.setBounds(10, 400, 86, 14);
		getContentPane().add(lblPresentes);

		txtrPresentes = new JTextArea();
		txtrPresentes.setBounds(10, 425, 932, 103);
		txtrPresentes.setLineWrap(true);
		txtrPresentes.setWrapStyleWord(true);
		scrollPanePresentes = new JScrollPane(txtrPresentes);
		scrollPanePresentes.setBounds(10, 425, 932, 103);
		getContentPane().add(scrollPanePresentes);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(853, 576, 89, 23);
		getContentPane().add(btnCancelar);
		btnCancelar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});

		btnSalvar = new JButton("Salvar");
		btnSalvar.setBounds(754, 576, 89, 23);
		getContentPane().add(btnSalvar);
		btnSalvar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if(txtrConteudo.getText().trim().equals("")&&txtrPresentes.getText().trim().equals("")){
					JOptionPane.showMessageDialog(null, "Preencha o conte\u00FAdo e as pessoas presentes na ata antes de salva-la!");
				}else if(isNovaAta()){

					setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

					try {
						java.sql.Date.valueOf(data.toLocalDate());
						connAta.insertAta(Integer.parseInt(txtNumeroAta.getText()), getDate(), txtrConteudo.getText(), txtrPresentes.getText());
						JOptionPane.showMessageDialog(null, "Ata salva com sucesso!");
						iniciaComponentes();
					} catch (NumberFormatException e1) {
						e1.printStackTrace();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}

					setCursor(Cursor.getDefaultCursor());
				}else{
					JOptionPane.showMessageDialog(null, "Insira uma nova ata para Salvar");
				}


			}
		});

		btnAtras = new JButton("<");
		btnAtras.setBounds(77, 576, 60, 23);
		getContentPane().add(btnAtras);
		btnAtras.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				retornaValorConsultado(btnAtras.getText(), txtNumeroAta.getText());
				setCursor(Cursor.getDefaultCursor());
			}
		});

		btnFrente = new JButton(">");
		btnFrente.setBounds(147, 576, 60, 23);
		getContentPane().add(btnFrente);
		btnFrente.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				retornaValorConsultado(btnFrente.getText(), txtNumeroAta.getText());
				setCursor(Cursor.getDefaultCursor());
			}
		});

		btnFim = new JButton(">>");
		btnFim.setBounds(217, 576, 60, 23);
		getContentPane().add(btnFim);
		btnFim.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				retornaValorConsultado(btnFim.getText(), txtNumeroAta.getText());
				setCursor(Cursor.getDefaultCursor());
			}
		});

		btnInicio = new JButton("<<");
		btnInicio.setBounds(7, 576, 60, 23);
		getContentPane().add(btnInicio);
		btnInicio.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				retornaValorConsultado(btnInicio.getText(), txtNumeroAta.getText());
				setCursor(Cursor.getDefaultCursor());

			}
		});

		btnNovo = new JButton("Novo");
		btnNovo.setBounds(655, 576, 89, 23);
		getContentPane().add(btnNovo);
		btnNovo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				iniciaComponentes();
			}
		});
	}

	private void iniciaComponentes(){
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		txtDataAta.setText(getCurrentDateFormated());
		txtNumeroAta.setText(Integer.toString(connAta.getNumeroSequencialAta()));
		txtrConteudo.setText("");
		txtrPresentes.setText("");
		txtrConteudo.setEditable(true);
		txtrPresentes.setEditable(true);
		txtrConteudo.requestFocus(true);
		setNovaAta(true);
		setCursor(Cursor.getDefaultCursor());
	}

	private String getCurrentDateFormated(){
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDateTime date = LocalDateTime.now();
		setDate(date);
		return date.format(dateTimeFormatter);
	}

	private void iniciaAta(){
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		ArrayList<String> retornoConsulta = new ArrayList<String>();
		retornoConsulta = connAta.dadosAta();

		if(retornoConsulta.size() == 1 && retornoConsulta.get(0).equals("NOK")){
			iniciaComponentes();
		}else{
			txtNumeroAta.setText(retornoConsulta.get(0));
			txtDataAta.setText(retornoConsulta.get(1));
			txtrConteudo.setText(retornoConsulta.get(2));
			txtrPresentes.setText(retornoConsulta.get(3));
		}
		setNovaAta(false);
		setCursor(Cursor.getDefaultCursor());
	}

	private void retornaValorConsultado(String comando, String id){
		ArrayList<String> retornoConsulta = new ArrayList<String>();
		retornoConsulta = connAta.dadosAta(comando, id);

		if(retornoConsulta.size() == 1){
			if(retornoConsulta.get(0).equals("NOK"))
				JOptionPane.showMessageDialog(null, "Erro no comando "+comando+" !");
			//if(retornoConsulta.get(0).equals("VAZIO") && comando.equals(">"))
				//iniciaComponentes();
		}else{
			txtNumeroAta.setText(retornoConsulta.get(0));
			txtDataAta.setText(retornoConsulta.get(1));
			txtrConteudo.setText(retornoConsulta.get(2));
			txtrPresentes.setText(retornoConsulta.get(3));
			setNovaAta(false);
		}

	}

	private void setDate(LocalDateTime data){
		this.data = data;
	}

	private LocalDateTime getDate(){
		return data;
	}

	private void setNovaAta(boolean novaAta){
		this.novaAta = novaAta;
		txtrConteudo.setEditable(novaAta);
		txtrPresentes.setEditable(novaAta);
	}

	private boolean isNovaAta(){
		return novaAta;
	}
}
