package util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleResultSet;

public class ConnectDados {

	private Connection conn = null;
	private OraclePreparedStatement pst = null;
	private OracleResultSet rs = null;
	private String sql = null;

	public ConnectDados(){

	}


	public ArrayList<String> carregaDados() throws SQLException{
		ArrayList<String> retornoConsulta = new ArrayList<String>();
		conn = ConnectDB.Connect();
		sql = " select nome_resumido,nome,endereco,estado,municipio,telefone,email,site,cnpj from dados";
		try {
			pst = (OraclePreparedStatement) conn.prepareStatement(sql);
			rs = (OracleResultSet) pst.executeQuery();
			if(rs.next()){
					retornoConsulta.add("S");
					retornoConsulta.add((rs.getString(1).equals("")? "":rs.getString(1)));
					retornoConsulta.add((rs.getString(2).equals("")? "":rs.getString(2)));
					retornoConsulta.add((rs.getString(3).equals("")? "":rs.getString(3)));
					retornoConsulta.add((rs.getString(4).equals("")? "":rs.getString(4)));
					retornoConsulta.add((rs.getString(5).equals("")? "":rs.getString(5)));
					retornoConsulta.add((rs.getString(6).equals("")? "":rs.getString(6)));
					retornoConsulta.add((rs.getString(7).equals("")? "":rs.getString(7)));
					retornoConsulta.add((rs.getString(8).equals("")? "":rs.getString(8)));
					retornoConsulta.add((rs.getString(9).equals("")? "":rs.getString(9)));


			}else{
				retornoConsulta.add("N");
			}

			pst.close();
			conn.close();
			rs.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Erro ao carregar dados!");
			pst.close();
			conn.close();
			rs.close();
			e.printStackTrace();
		}


		return retornoConsulta;
	}

	public ArrayList<String> populaComboEstados() throws SQLException{
		ArrayList<String> retornoConsulta = new ArrayList<String>();
		conn = ConnectDB.Connect();
		sql = "select sigla from estados order by sigla";
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

	public boolean salvar(String nomeResumido, String nome, String endereco, String estado, String municipio,
			                                   String telefone, String email, String site, String cnpj ) throws SQLException{
		conn = ConnectDB.Connect();
		sql = "insert into dados (nome_resumido,nome,endereco,estado,municipio,telefone,email,site,cnpj) "+
		      " VALUES ('"+nomeResumido+"','"+nome+"','"+endereco+"','"+estado+"','"+municipio+"','"+telefone+"','"+email+"','"+site+"','"+cnpj+"')";
		try {
			pst = (OraclePreparedStatement) conn.prepareStatement(sql);
			pst.executeUpdate();
			pst.close();
			conn.close();
			return true;
		} catch (SQLException e) {

				JOptionPane.showMessageDialog(null, "Erro ao inserir dados!");
				pst.close();
				conn.close();

			e.printStackTrace();
			return false;
		}
	}

	public boolean atualizar(String nomeResumido, String nome, String endereco, String estado, String municipio,
                                                  String telefone, String email, String site, String cnpj) throws SQLException{
		conn = ConnectDB.Connect();
		sql = "update dados set nome_resumido = '"+nomeResumido+"', nome = '"+nome+"',endereco = '"+endereco+"',"+
		                       "estado = '"+estado+"',municipio = '"+municipio+"',telefone = '"+telefone+"',email = '"+email+"',site = '"+site+"',cnpj ='"+cnpj+"'";
		try {
			pst = (OraclePreparedStatement) conn.prepareStatement(sql);
			pst.executeUpdate();
			pst.close();
			conn.close();
			return true;
		} catch (SQLException e) {

				JOptionPane.showMessageDialog(null, "Erro ao atualizar os dados!");
				pst.close();
				conn.close();

			e.printStackTrace();
			return false;
		}

	}
}
