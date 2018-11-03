package util;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleResultSet;

public class ConnectAta {

	private Connection conn = null;
	private OraclePreparedStatement pst = null;
	private OracleResultSet rs = null;
	private String sql = null;

	public ConnectAta() {
	}


	public void insertAta(int numeroAta, LocalDateTime data, String conteudo, String presentes) throws SQLException{
		conn = ConnectDB.Connect();
		sql = "insert into atas(id_ata, data, conteudo, presentes, id_usuario) values (?,?,?,?,(select id_usuario from usuario where usuario = (select usuario from usuario_logado where status = 'S')))";
		try {
			pst = (OraclePreparedStatement) conn.prepareStatement(sql);
			pst.setInt(1, numeroAta);
			pst.setDate(2, java.sql.Date.valueOf(data.toLocalDate()));
			pst.setString(3, conteudo);
			pst.setString(4, presentes);
			pst.executeUpdate();
			conn.close();
			pst.close();
		} catch (SQLException e) {
			if(e.getErrorCode() == 1){
				JOptionPane.showMessageDialog(null, "N\u00FAmero de ata j\u00E1 existente!");
				pst.close();
				conn.close();
			}else{
				JOptionPane.showMessageDialog(null, "Erro ao inserir nova Ata!");
				e.printStackTrace();
				pst.close();
				conn.close();
			}
		}

	}


	public int getNumeroSequencialAta(){
		conn = ConnectDB.Connect();
		sql = "select max(id_ata) from atas";
		int numeroSequencial = 0;

		try {
			pst = (OraclePreparedStatement) conn.prepareStatement(sql);
			rs = (OracleResultSet) pst.executeQuery();

			if(rs.next()){
				numeroSequencial = rs.getInt(1) + 1;
			}

			conn.close();
			pst.close();
			rs.close();

		} catch (SQLException e) {
			if(e.getErrorCode() == 1403){
				numeroSequencial = 1;
			}
		}


		return numeroSequencial;
	};


	public ArrayList<String> dadosAta(){
		ArrayList<String> retornoConsulta = new ArrayList<String>();
		String date;

		conn = ConnectDB.Connect();
		sql = "select id_ata, data, conteudo, presentes from atas where id_ata = 1";

		try {
			pst = (OraclePreparedStatement) conn.prepareStatement(sql);
			rs = (OracleResultSet) pst.executeQuery();

			if (rs.next()){
				retornoConsulta.add(Integer.toString(rs.getInt(1)));
				//Date d = new SimpleDateFormat("dd/MM/yyyy").parse((2));
				date = new SimpleDateFormat("dd/MM/yyyy").format(rs.getDate(2));
				retornoConsulta.add(date);
				retornoConsulta.add(rs.getString(3));
				retornoConsulta.add(rs.getString(4));

				return retornoConsulta;
			}else{
				retornoConsulta.add("NOK");
			}
		} catch (SQLException e) {
			if(e.getErrorCode() == 1403){
				retornoConsulta.add("NOK");
			}
			e.printStackTrace();
		}
		return retornoConsulta;
	}


	public ArrayList<String> dadosAta(String commando, String id){
		ArrayList<String> retornoConsulta = new ArrayList<String>();
		String date;
		int numeroAta = Integer.valueOf(id);

		conn = ConnectDB.Connect();

		if(commando.equals(">>"))
			sql = "select id_ata, data, conteudo, presentes from atas where id_ata = (select max(id_ata) from atas)";
		else if (commando.equals("<<"))
			sql = "select id_ata, data, conteudo, presentes from atas where id_ata = (select min(id_ata) from atas)";
		else if (commando.equals("<"))
			sql = "select id_ata, data, conteudo, presentes from atas where id_ata = "+(numeroAta - 1 <= 0? 1 : numeroAta - 1);
		else if (commando.equals(">"))
			sql = "select id_ata, data, conteudo, presentes from atas where id_ata = "+(numeroAta + 1);

		try {
			pst = (OraclePreparedStatement) conn.prepareStatement(sql);
			rs = (OracleResultSet) pst.executeQuery();

			if (rs.next()){
				retornoConsulta.add(Integer.toString(rs.getInt(1)));
				//Date d = new SimpleDateFormat("dd/MM/yyyy").parse((2));
				date = new SimpleDateFormat("dd/MM/yyyy").format(rs.getDate(2));
				retornoConsulta.add(date);
				retornoConsulta.add(rs.getString(3));
				retornoConsulta.add(rs.getString(4));

				return retornoConsulta;
			}else{
				retornoConsulta.add("VAZIO");
			}
		} catch (SQLException e) {
			if(e.getErrorCode() == 1403){
				retornoConsulta.add("NOK");
			}
			e.printStackTrace();
		}
		return retornoConsulta;
	}

}
