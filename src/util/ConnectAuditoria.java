package util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import auxiliares.Audit;
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleResultSet;
import telas.Auditoria;

public class ConnectAuditoria {

	private OraclePreparedStatement pst = null;
	private OracleResultSet rs = null;
	private String sql = null;
	private Connection conn = null;

	public ConnectAuditoria(){

	}

	public ResultSet consultar(Auditoria audit){

		String where = "";

		sql = "SELECT id, nome_tabela, usuario, evento, data_evento, comando FROM LOG";

		// monta where
		if (audit.isUpdate())
			if(where.trim().equals(""))
				where += " where evento = 'U' ";
			else
				where += " or evento = 'U' ";

		if (audit.isInsert())
			if(where.trim().equals(""))
				where += " where evento = 'I' ";
			else
				where += " or evento  = 'I' ";

		if (audit.isDelete())
			if(where.trim().equals(""))
				where += " where evento = 'D' ";
			else
				where += " or evento  = 'D' ";

		if (audit.isSistema())
			if(where.trim().equals(""))
				where += " where evento = 'S' ";
			else
				where += "or evento = 'S' ";

		if (!audit.getUsuario().trim().equals(""))
			if(where.trim().equals(""))
				where += " where usuario = '" +audit.getUsuario()+"'";
			else
				where += "or usuario = '" +audit.getUsuario()+"'";

		if (!audit.getTabela().trim().equals(""))
			if(where.trim().equals(""))
				where += " where nome_tabela = '" +audit.getTabela()+"'";
			else
				where += " or nome_tabela  = '" +audit.getTabela()+"'";

		if (!audit.getTrigger().trim().equals("")){

			String nomeTabelaTrigger = audit.getTrigger().substring(4);

			if(where.trim().equals(""))
				where += " where nome_tabela = '" +nomeTabelaTrigger+"'";
			else
				where += " or nome_tabela  = '" +nomeTabelaTrigger+"'";
		}

		if (!audit.getDataInicio().trim().equals(""))
			if(where.trim().equals(""))
				where += " where data_evento >= to_date('"+audit.getDataInicio()+"', 'dd/MM/yyyy')";
			else
				where += " or data_evento  >= to_date('"+audit.getDataInicio()+"', 'dd/MM/yyyy')";

		if (!audit.getDataFim().trim().equals(""))
			if(where.trim().equals(""))
				where += " where data_evento <= to_date('"+audit.getDataFim()+"', 'dd/MM/yyyy')";
			else
				where += " or data_evento  <= to_date('"+audit.getDataFim()+"', 'dd/MM/yyyy')";

		sql += where;

		try {
			conn = ConnectDB.Connect();
			pst = (OraclePreparedStatement) conn.prepareStatement(sql);
			rs = (OracleResultSet) pst.executeQuery();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao recuperar informações do banco de dados");
			e.printStackTrace();
		}

		return rs;
	}

	public ArrayList<String> populaComboUsuario() throws SQLException{
		ArrayList<String> retornoConsulta = new ArrayList<String>();
		conn = ConnectDB.Connect();
		sql = "select usuario from usuario order by usuario";
		try {
			pst = (OraclePreparedStatement) conn.prepareStatement(sql);
			rs = (OracleResultSet) pst.executeQuery();
			int inserted = 0;
			while(rs.next()){
				if (inserted == 0){
					retornoConsulta.add("");
					retornoConsulta.add(rs.getString(1));
					inserted++;
				}else{
					retornoConsulta.add(rs.getString(1));
				}
			}
			pst.close();
			conn.close();
			rs.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Erro ao carregar dados do usu\u00E1rio");
			pst.close();
			conn.close();
			rs.close();
			e.printStackTrace();
		}
		return retornoConsulta;
	}






}
