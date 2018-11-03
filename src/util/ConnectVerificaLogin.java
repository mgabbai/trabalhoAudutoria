package util;

import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleResultSet;

public class ConnectVerificaLogin {

	private String usuario;
	private String password;
	private int permissao;

	public ConnectVerificaLogin(String usuario, String password) {
		this.usuario = usuario;
		this.password = password;
	}

	public boolean verificaLogin() throws SQLException{

		Connection conn = null;
		OraclePreparedStatement pst = null;
		OracleResultSet rs = null;
		String sql = null;

		sql = "select count(1) as ok, permissao from usuario where usuario = '"+usuario+"' and f_decrypt(senha) = '"+password+"' group by permissao";

		conn = ConnectDB.Connect();

		try {
			pst = (OraclePreparedStatement) conn.prepareStatement(sql);
			rs = (OracleResultSet) pst.executeQuery();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao connectar com banco de dados, tente mais tarde!");
			return false;
		}

		if(rs.next()){
			if(rs.getInt("ok") == 1){
				setPermissao(rs.getInt("permissao"));
				sql = "update usuario_logado set status = 'N'";
				pst = (OraclePreparedStatement) conn.prepareStatement(sql);
				pst.executeUpdate();
				sql = "insert into usuario_logado (usuario,status, data) values ('"+usuario+"','S', sysdate)";
				pst = (OraclePreparedStatement) conn.prepareStatement(sql);
				pst.executeUpdate();
				pst.close();
				conn.close();
				return true;
			}
		}


		return false;
	}

	private void setPermissao(int permissao){
		this.permissao = permissao;
	}

	public int getPermissao(){
		return permissao;
	}


}
