package util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleResultSet;

public class ConnectUsuario {

	private Connection conn = null;
	private OraclePreparedStatement pst = null;
	private OracleResultSet rs = null;
	private String sql = null;

	public ConnectUsuario() {
	}


	public boolean salvar(String nome, String email, String usuario, String senha, int permissao) throws SQLException{
		conn = ConnectDB.Connect();
		sql = "insert into usuario (id_usuario, nome, email, usuario, senha, permissao) VALUES (SEQ_USUARIO.nextval, '"+nome+"','"+email+"', '"+usuario+"', f_encrypt('"+senha+"'), "+permissao+")";
		try {
			pst = (OraclePreparedStatement) conn.prepareStatement(sql);
			pst.executeUpdate();
			pst.close();
			conn.close();
			return true;
		} catch (SQLException e) {
			if(e.getErrorCode() == 1){
				JOptionPane.showMessageDialog(null, "Usu\u00E1rio j\u00E1 existente!");
				pst.close();
				conn.close();
			}else{
				JOptionPane.showMessageDialog(null, "Erro ao inserir novo usu\u00E1rio");
				pst.close();
				conn.close();
			}
			e.printStackTrace();
			return false;
		}

	}
	public boolean salvar(String nome, String usuario, String senha, int permissao) throws SQLException{
		conn = ConnectDB.Connect();
		sql = "insert into usuario (id_usuario, nome, usuario, senha, permissao) VALUES (SEQ_USUARIO.nextval, '"+nome+"', '"+usuario+"', f_encrypt('"+senha+"'), "+permissao+")";
		try {
			pst = (OraclePreparedStatement) conn.prepareStatement(sql);
			pst.executeUpdate();
			pst.close();
			conn.close();
			return true;
		} catch (SQLException e) {
			if(e.getErrorCode() == 1){
				JOptionPane.showMessageDialog(null, "Usu\u00E1rio j\u00E1 existente!");
				pst.close();
				conn.close();
			}else{
				JOptionPane.showMessageDialog(null, "Erro ao inserir novo usu\u00E1rio");
				pst.close();
				conn.close();
			}
			e.printStackTrace();
			return false;
		}
	}

	public boolean atualizar(String nome, String email, String usuario, String senha, int permissao) throws SQLException{
		conn = ConnectDB.Connect();
		sql = "update usuario set nome = '"+nome+"',email = '"+email+"', usuario = '"+usuario+"', senha = f_encrypt('"+senha+"'),permissao = "+permissao+" where usuario = '"+usuario+"'";
		try {
			pst = (OraclePreparedStatement) conn.prepareStatement(sql);
			pst.executeUpdate();
			pst.close();
			conn.close();
			return true;
		} catch (SQLException e) {
			if(e.getErrorCode() == 1){
				JOptionPane.showMessageDialog(null, "Usu\u00E1rio j\u00E1 existente!");
				pst.close();
				conn.close();
			}else{
				JOptionPane.showMessageDialog(null, "Erro ao atualizar os dados do usu\u00E1rio");
				pst.close();
				conn.close();
			}
			e.printStackTrace();
			return false;
		}

	}
	public boolean atualizar(String nome, String usuario, String senha, int permissao) throws SQLException{
		conn = ConnectDB.Connect();
		sql = "update usuario set nome = '"+nome+"', usuario = '"+usuario+"', senha = f_encrypt('"+senha+"'),permissao = "+permissao+" where usuario = '"+usuario+"'";
		try {
			pst = (OraclePreparedStatement) conn.prepareStatement(sql);
			pst.executeUpdate();
			pst.close();
			conn.close();
			return true;
		} catch (SQLException e) {
			if(e.getErrorCode() == 1){
				JOptionPane.showMessageDialog(null, "Usu\u00E1rio j\u00E1 existente!");
				pst.close();
				conn.close();
			}else{
				JOptionPane.showMessageDialog(null, "Erro ao atualizar os dados do usu\u00E1rio");
				pst.close();
				conn.close();
			}
			e.printStackTrace();
			return false;
		}
	}

	public ArrayList<String> consultar(String usuario) throws SQLException{
		ArrayList<String> retornoConsulta = new ArrayList<String>();
		conn = ConnectDB.Connect();
		sql = "select nome, usuario, f_decrypt(senha), to_char(permissao), email from usuario where usuario = '"+usuario+"'";
		try {
			pst = (OraclePreparedStatement) conn.prepareStatement(sql);
			rs = (OracleResultSet) pst.executeQuery();
			while(rs.next()){
					retornoConsulta.add("S");
					retornoConsulta.add(rs.getString(1));
					retornoConsulta.add(rs.getString(2));
					retornoConsulta.add(rs.getString(3));
					retornoConsulta.add(rs.getString(4));
					retornoConsulta.add(rs.getString(5));

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

	public ArrayList<String> populaComboUsuario() throws SQLException{
		ArrayList<String> retornoConsulta = new ArrayList<String>();
		conn = ConnectDB.Connect();
		sql = "select usuario from usuario where usuario <> 'ADM' order by usuario";
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
