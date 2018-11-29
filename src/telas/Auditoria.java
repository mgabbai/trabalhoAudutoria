package telas;

import javax.swing.JDialog;
import java.awt.Cursor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JTable;

import auxiliares.Audit;
import util.ConnectAuditoria;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JCheckBox;
import javax.swing.table.TableColumnModel;
import com.toedter.calendar.JDateChooser;


public class Auditoria extends JDialog{
	private JTable table;
	private Audit audit = new Audit();
	private ConnectAuditoria auditoria = new ConnectAuditoria();
	private boolean update = false;
	private boolean insert = false;
	private boolean delete = false;
	private JScrollPane scrollPaneTable;
	private boolean sistema = false;
	private String tabelaSelecionada = "";
	private String usuarioSelecionado = "";
	private String triggerSelecionada = "";
	private JComboBox<String> cmbTrigger;
	private JComboBox<String> cmbUsuario;
	private JComboBox<String> cmbTabela ;
	private JDateChooser txtDataInicio;
	private JDateChooser txtDataFim;
	private JCheckBox chckbxInsert;
	private JCheckBox chckbxUpdate;
	private JCheckBox chckbxDelete;
	private JCheckBox chckbxSistema;
	private String dataInicio = "";
	private String dataFim = "";

	public Auditoria(Menu parent, boolean modal) {
		super(parent, modal);

		createWindow();

	}

	private void createWindow(){

		setIconImage(Toolkit.getDefaultToolkit().getImage("." + File.separator + "src" + File.separator + "telas" + File.separator + "if_41-File-Document-process_3213319.png"));
		getContentPane().setLayout(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(770, 532, 89, 23);
		getContentPane().add(btnCancelar);
		btnCancelar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});

		JButton btnConsultar = new JButton("Consultar");
		btnConsultar.setBounds(652, 532, 89, 23);
		getContentPane().add(btnConsultar);
		btnConsultar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

				if(!txtDataInicio.getDateFormatString().equalsIgnoreCase(""))
					setDataInicio(txtDataInicio.getDate());

				if(!txtDataFim.getDateFormatString().equalsIgnoreCase(""))
					setDataFim(txtDataFim.getDate());

				createTable(auditoria.consultar(getContent()));

				setCursor(Cursor.getDefaultCursor());
			}



		});



		cmbUsuario = new JComboBox<String>();
		cmbUsuario.setBounds(41, 50, 273, 20);
		getContentPane().add(cmbUsuario);
		cmbUsuario.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				setUsuario((cmbUsuario.getSelectedItem().toString().equals("")? "":cmbUsuario.getSelectedItem().toString()));

			}
		});

		JLabel lblUsuario = new JLabel("Usuario");
		lblUsuario.setBounds(41, 25, 46, 14);
		getContentPane().add(lblUsuario);

		chckbxInsert = new JCheckBox("Insert");
		chckbxInsert.setBounds(636, 59, 97, 23);
		getContentPane().add(chckbxInsert);
		chckbxInsert.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if(chckbxInsert.isSelected())
					setInsert(true);
				else
					setInsert(false);

			}
		});


		chckbxUpdate = new JCheckBox("Update");
		chckbxUpdate.setBounds(636, 85, 97, 23);
		getContentPane().add(chckbxUpdate);
		chckbxUpdate.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if(chckbxUpdate.isSelected())
					setUpdate(true);
				else
					setUpdate(false);

			}
		});

		chckbxDelete = new JCheckBox("Delete");
		chckbxDelete.setBounds(636, 111, 97, 23);
		getContentPane().add(chckbxDelete);
		chckbxDelete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if(chckbxDelete.isSelected())
					setDelete(true);
				else
					setDelete(false);

			}
		});


		JLabel lblOperao = new JLabel("Opera\u00E7\u00E3o");
		lblOperao.setBounds(636, 38, 97, 14);
		getContentPane().add(lblOperao);

		table = new JTable();
		table.setBounds(41, 160, 815, 361);
		scrollPaneTable = new JScrollPane(table);
		scrollPaneTable.setBounds(41, 160, 818, 361);
		getContentPane().add(scrollPaneTable);

		JLabel lblTabela = new JLabel("Tabela");
		lblTabela.setBounds(41, 81, 127, 14);
		getContentPane().add(lblTabela);

		cmbTabela = new JComboBox<String>();
		cmbTabela.setBounds(41, 100, 273, 20);
		getContentPane().add(cmbTabela);
		cmbTabela.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				setTabela(cmbTabela.getSelectedItem().toString().equals("")? "":cmbTabela.getSelectedItem().toString());

			}
		});

		JLabel lblTriggers = new JLabel("Triggers");
		lblTriggers.setBounds(337, 25, 97, 14);
		getContentPane().add(lblTriggers);

		cmbTrigger = new JComboBox<String>();
		cmbTrigger.setBounds(337, 50, 273, 20);
		getContentPane().add(cmbTrigger);
		cmbTrigger.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				setTrigger((cmbTrigger.getSelectedItem().toString().equals("")? "":cmbTrigger.getSelectedItem().toString()));


			}
		});

		chckbxSistema = new JCheckBox("Sistema");
		chckbxSistema.setBounds(735, 63, 105, 23);
		getContentPane().add(chckbxSistema);

		JLabel lblData = new JLabel("Data");
		lblData.setBounds(337, 81, 46, 14);
		getContentPane().add(lblData);

		txtDataInicio = new JDateChooser("dd/MM/yyyy HH:mm", "##/##/#### ##:##", '_');
		txtDataInicio.setBounds(337, 118, 127, 20);
		getContentPane().add(txtDataInicio);
		txtDataInicio.setLocale(getLocale());


		txtDataFim = new JDateChooser("dd/MM/yyyy HH:mm", "##/##/#### ##:##", '_');
		txtDataFim.setBounds(483, 118, 127, 20);
		getContentPane().add(txtDataFim);
		txtDataInicio.setLocale(getLocale());

		JLabel lblInicio = new JLabel("Inicio");
		lblInicio.setBounds(337, 100, 71, 14);
		getContentPane().add(lblInicio);

		JLabel lblFim = new JLabel("Fim");
		lblFim.setBounds(483, 100, 89, 14);
		getContentPane().add(lblFim);


		chckbxSistema.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if(chckbxUpdate.isSelected())
					setSistema(true);
				else
					setSistema(false);

			}
		});

		JButton btnLimpaFiltros = new JButton("Limpar");
		btnLimpaFiltros.setBounds(41, 532, 144, 23);
		getContentPane().add(btnLimpaFiltros);
		btnLimpaFiltros.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				limpar();
			}
		});

		populaCombo();
	}

	private void createTable(ResultSet consulta) {
		try {

			clearTable();

			table = new JTable(audit.buildTableModel(consulta), audit.getTableColumnNames());
			table.setBounds(41, 160, 815, 361);
			scrollPaneTable = new JScrollPane(table);

			//getContentPane().add(table);
			table.setAutoscrolls(true);
			table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

			scrollPaneTable.setBounds(41, 160, 818, 361);
			getContentPane().add(scrollPaneTable);


			TableColumnModel columnModel = table.getColumnModel();

			columnModel.getColumn(0).setPreferredWidth(65);
			columnModel.getColumn(1).setPreferredWidth(115);
			columnModel.getColumn(2).setPreferredWidth(80);
			columnModel.getColumn(3).setPreferredWidth(25);
			columnModel.getColumn(4).setPreferredWidth(100);
			columnModel.getColumn(5).setPreferredWidth(430);

			revalidate();
			repaint();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	private void clearTable(){
		if (table != null){
			getContentPane().remove(table);
			getContentPane().remove(scrollPaneTable);
		}

	}

	private void setUpdate(boolean update){
		this.update = update;
	}

	public boolean isUpdate(){
		return update;
	}

	private void setInsert(boolean insert){
		this.insert = insert;
	}

	public boolean isInsert(){
		return insert;
	}

	private void setDelete(boolean delete){
		this.delete = delete;
	}

	public boolean isDelete(){
		return delete;
	}

	private Auditoria getContent(){
		return this;
	}

	private void setSistema(boolean sistema){
		this.sistema = sistema;
	}

	public boolean isSistema(){
		return sistema;
	}

	private void setTrigger(String triggerSelecionada){
		this.triggerSelecionada = triggerSelecionada;
	}

	private void setTabela(String tabelaSelecionada){
		this.tabelaSelecionada = tabelaSelecionada;
	}

	private void setUsuario(String usuarioSelecionado){
		this.usuarioSelecionado = usuarioSelecionado;
	}

	public String getTrigger(){
		return this.triggerSelecionada;
	}

	public String getTabela(){
		return this.tabelaSelecionada;
	}

	public String getUsuario(){
		return this.usuarioSelecionado;
	}

	private void setDataFim(Date dateFormatString) {
		if(dateFormatString != null){
			String date = new SimpleDateFormat("dd/MM/yyyy").format(dateFormatString);
			dataFim = date;
		}
	}

	private void setDataInicio(Date dateFormatString) {
		if(dateFormatString != null){
			String date = new SimpleDateFormat("dd/MM/yyyy").format(dateFormatString);
			dataInicio = date;
		}
	}

	public String getDataFim(){
		return this.dataFim;
	}

	public String getDataInicio(){
		return this.dataInicio;
	}

	private void populaCombo(){

		cmbTabela.addItem("");
		cmbTabela.addItem("ATAS");
		cmbTabela.addItem("CAMINHO_IMPRESSAO");
		cmbTabela.addItem("DADOS");
		cmbTabela.addItem("USUARIO");
		cmbTabela.addItem("USUARIO_LOGADO");

		cmbTrigger.addItem("");
		cmbTrigger.addItem("TRG_ATAS");
		cmbTrigger.addItem("TRG_CAMINHO_IMPRESSAO");
		cmbTrigger.addItem("TRG_DADOS");
		cmbTrigger.addItem("TRG_USUARIO");
		cmbTrigger.addItem("TRG_USUARIO_LOGADO");

		ArrayList<String> retornoConsulta = new ArrayList<String>();
		try {
			retornoConsulta = auditoria.populaComboUsuario();

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

	private void limpar(){
		if(cmbUsuario.getItemCount()>0)
			cmbUsuario.setSelectedIndex(0);
		if(cmbTabela.getItemCount()>0)
			cmbTabela.setSelectedIndex(0);
		if(cmbTrigger.getItemCount()>0)
			cmbTrigger.setSelectedIndex(0);
		if(chckbxDelete.isSelected())
			chckbxDelete.doClick();
		if(chckbxInsert.isSelected())
			chckbxInsert.doClick();
		if(chckbxSistema.isSelected())
			chckbxSistema.doClick();
		if(chckbxUpdate.isSelected())
			chckbxUpdate.doClick();

		txtDataInicio.setDateFormatString("");
		txtDataFim.setDateFormatString("");

		clearTable();
		table = new JTable();
		table.setBounds(41, 160, 815, 361);
		scrollPaneTable = new JScrollPane(table);
		table.setAutoscrolls(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		scrollPaneTable.setBounds(41, 160, 818, 361);
		getContentPane().add(scrollPaneTable);
	}
}

