package auxiliares;

import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class Audit {

	public Audit(){

	}

	public Vector<Vector<Object>> buildTableModel(ResultSet rs)
	        throws SQLException {

	    ResultSetMetaData metaData = rs.getMetaData();

	    // data of the table
	    Vector<Vector<Object>> data = new Vector<Vector<Object>>();
	    while (rs.next()) {

	    	Vector<Object> vector = new Vector<Object>();

	    	vector.add(rs.getObject(1));
	        vector.add(rs.getObject(2));
	        vector.add(rs.getObject(3));
	        vector.add(rs.getObject(4));
	        vector.add(new SimpleDateFormat("dd/MM/yyyy").format(rs.getDate(5)));

	        Clob clob = rs.getClob(6);
	        vector.add(clob.getSubString(1, (int) clob.length()));

	        data.add(vector);
	    }

	    return data;

	}

	public Vector<String> getTableColumnNames(){


		Vector<String> columnNames = new Vector<>();

		columnNames.add("ID");
		columnNames.add("Objeto/Tabela");
		columnNames.add("Usuário");
		columnNames.add("E");
		columnNames.add("Data");
		columnNames.add("Comando");

		return  columnNames;
	}

}
