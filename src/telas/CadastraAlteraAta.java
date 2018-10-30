package telas;

import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

import java.awt.Color;
import java.awt.Toolkit;
import java.io.File;
import java.text.ParseException;
import javax.swing.JTextArea;
import javax.swing.JButton;

public class CadastraAlteraAta extends JDialog {
	private JTextField txtNumeroAta;
	private JTextField txtDataAta;
	private JLabel lblConteudoDaReunio;
	public CadastraAlteraAta() {
		setIconImage(Toolkit.getDefaultToolkit().getImage("." + File.separator + "src" + File.separator + "telas" + File.separator + "if_41-File-Document-process_3213319.png"));

		getContentPane().setLayout(null);

		JLabel lblNmeroDaAta = new JLabel("N\u00FAmero da Ata");
		lblNmeroDaAta.setBounds(10, 11, 83, 14);
		getContentPane().add(lblNmeroDaAta);

		txtNumeroAta = new JTextField();
		txtNumeroAta.setBackground(Color.LIGHT_GRAY);
		txtNumeroAta.setBounds(10, 28, 86, 20);
		getContentPane().add(txtNumeroAta);
		txtNumeroAta.setColumns(10);

		JLabel lblData = new JLabel("Data");
		lblData.setBounds(518, 11, 46, 14);
		getContentPane().add(lblData);

		MaskFormatter dateMask = null;
		try {
		    dateMask = new MaskFormatter("##/##/####");
		    dateMask.setPlaceholderCharacter('/');
		    dateMask.setValidCharacters("0123456789");
		} catch (ParseException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}

		txtDataAta = new JFormattedTextField(dateMask);
		txtDataAta.setBounds(478, 28, 86, 20);
		getContentPane().add(txtDataAta);
		txtDataAta.setColumns(10);

		lblConteudoDaReunio = new JLabel("Conteudo da Reuni\u00E3o");
		lblConteudoDaReunio.setBounds(10, 60, 191, 14);
		getContentPane().add(lblConteudoDaReunio);

		JTextArea txtrConteudo = new JTextArea();
		txtrConteudo.setBounds(10, 90, 554, 299);
		getContentPane().add(txtrConteudo);

		JLabel lblPresentes = new JLabel("Presentes");
		lblPresentes.setBounds(10, 400, 86, 14);
		getContentPane().add(lblPresentes);

		JTextArea txtrPresentes = new JTextArea();
		txtrPresentes.setBounds(10, 425, 554, 103);
		getContentPane().add(txtrPresentes);

		JButton btnEditar = new JButton("Editar");
		btnEditar.setBounds(7, 576, 89, 23);
		getContentPane().add(btnEditar);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(475, 576, 89, 23);
		getContentPane().add(btnCancelar);

		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.setBounds(376, 576, 89, 23);
		getContentPane().add(btnSalvar);

	}
}
