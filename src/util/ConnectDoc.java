package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleResultSet;
import word.api.interfaces.IDocument;
import word.w2004.Document2004;
import word.w2004.elements.BreakLine;
import word.w2004.elements.Heading1;
import word.w2004.elements.Heading2;
import word.w2004.elements.PageBreak;
import word.w2004.elements.Paragraph;
import word.w2004.elements.ParagraphPiece;
import word.w2004.style.ParagraphStyle;

public class ConnectDoc {
	private Connection conn = null;
	private OraclePreparedStatement pst = null;
	private OracleResultSet rs = null;
	private String sql = null;
	private String path;


	public ConnectDoc(){

	}


	public ArrayList<String> populaComboNumeroAta() throws SQLException{
		ArrayList<String> retornoConsulta = new ArrayList<String>();
		conn = ConnectDB.Connect();
		sql = "select id_ata from atas order by id_ata";
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
			JOptionPane.showMessageDialog(null, "Erro ao carregar o n\u00E9mero das atas!");
			pst.close();
			conn.close();
			rs.close();
			e.printStackTrace();
		}
		return retornoConsulta;
	}

	public void geraDoc(String numeroAta, String path){

	    IDocument myDoc = new word.w2004.Document2004();
	    myDoc.encoding(Document2004.Encoding.ISO8859_1); //or ISO8859-1. Default is UTF-8

	    String sql = "";


	    conn = ConnectDB.Connect();
	    try {
	        pst = (OraclePreparedStatement) conn.prepareStatement(sql);
	        //pst.setString(1, user_id.getText());
	        rs = (OracleResultSet) pst.executeQuery();

	    } catch (Exception e) {
	        JOptionPane.showMessageDialog(null, e);
	    }// FIM TRY-CATCH CONECÇÃO

	    /*
	    1 - NOME_FUNCIONALIDADE
	    2 - COD_CHAMADO
	    3 - DESCRICAO
	    4 - IMPACTOS
	    5 - DESCR_CATEG
	    6 - NOME_BASELINE
	    7 - INCIDENTE
	    8 - TEM_TEST_CASE
	    9 - TC_PRECONDICOES // CASO HOUVER TEST CASE
	    10 - TC_PASSOS // CASO HOUVER TEST CASE
	    11 - TC_RESULTADO // CASO HOUVER TEST CASE

	     */
	    int head = 0, tem_reg = 0, ja_fui = 0, testCaseOK = 0;
	    ParagraphPiece produto_nome  = ParagraphPiece.with(nome_produto).withStyle().bold().fontSize("16").underline().create();
	    myDoc.getHeader().addEle(Paragraph.withPieces(produto_nome).create());
	    while (rs.next()) {
	        contagem++;
	        if (tem_reg == 0){
	            tem_reg = 1;
	        }

	        chamado.titulo = rs.getString(1);
	        //chamado.nrm="RM " + rs.getString(0);
	        chamado.impacto = rs.getString(4);
	        if (chamado.impacto == null) {
	            chamado.impacto = "N/A";
	        }
	        chamado.descricao = rs.getString(3);
	        if (chamado.descricao == null) {
	            chamado.descricao = "N/A";
	        }

	        chamado.categoria = rs.getString(5);
	        String incidente = rs.getString(7);

	        if (incidente == null) {
	            chamado.nrm = "RM " + rs.getString(2);
	        } else {
	            chamado.nrm = "RM " + rs.getString(2) + " / Incidente " + incidente;
	        }

	        chamado.temTestCase = rs.getInt(8);

	        if (chamado.temTestCase > 0){

	            chamado.precondicoes = rs.getString(9);
	            chamado.passos = rs.getString(10);
	            chamado.resultado = rs.getString(11);
	            testCaseOK = 1;

	        }

	        ParagraphPiece categoria  = ParagraphPiece.with(chamado.categoria).withStyle().bold().fontSize("13").create();
	        ParagraphPiece titulo = ParagraphPiece.with(chamado.titulo).withStyle().bold().fontSize("12").textColor("ffffff").create();
	        ParagraphPiece nRM = ParagraphPiece.with(chamado.nrm).withStyle().fontSize("12").bold().textColor("808080").create();
	        ParagraphPiece head_desc = ParagraphPiece.with(chamado.titulo_descricao).withStyle().smallCaps().fontSize("11").bold().textColor("808080").create();
	        ParagraphPiece descricao = ParagraphPiece.with(chamado.descricao).withStyle().fontSize("12").textColor("808080").create();
	        ParagraphPiece head_impa = ParagraphPiece.with(chamado.titulo_impacto).withStyle().textColor("808080").fontSize("11").bold().smallCaps().create();
	        ParagraphPiece impacto = ParagraphPiece.with(chamado.impacto).withStyle().textColor("808080").fontSize("12").create();
	        ParagraphPiece head_precond = ParagraphPiece.with(chamado.titulo_precond).withStyle().textColor("808080").fontSize("11").bold().smallCaps().create();
	        ParagraphPiece precond = ParagraphPiece.with(chamado.precondicoes).withStyle().textColor("808080").fontSize("12").create();
	        ParagraphPiece head_passos = ParagraphPiece.with(chamado.titulo_passos).withStyle().textColor("808080").fontSize("11").bold().smallCaps().create();
	        ParagraphPiece passos = ParagraphPiece.with(chamado.passos).withStyle().textColor("808080").fontSize("12").create();
	        ParagraphPiece head_result = ParagraphPiece.with(chamado.titulo_result).withStyle().textColor("808080").fontSize("11").bold().smallCaps().create();
	        ParagraphPiece result = ParagraphPiece.with(chamado.resultado).withStyle().textColor("808080").fontSize("12").create();
	        ParagraphPiece titulo_testcase = ParagraphPiece.with(chamado.titulo_testCase).withStyle().textColor("808080").fontSize("14").bold().underline().smallCaps().create();



	        if (chamado.categoria.equals("Customização") && head == 0){

	           myDoc.addEle(Heading2.with(chamado.categoria).create());
	           myDoc.addEle(BreakLine.times(1).create());

	           head = 1;

	        }else if (chamado.categoria.equals("Evolução Legal") && head == 1 || head == 0){

	            myDoc.addEle(Heading2.with(chamado.categoria).create());
	            myDoc.addEle(BreakLine.times(1).create());

	            head = 2;

	        } else if (chamado.categoria.equals("Plano de Produto") && head == 2 || head == 0 || head == 1){

	            myDoc.addEle(Heading2.with(chamado.categoria).create());
	            myDoc.addEle(BreakLine.times(1).create());

	            head = 3;

	        } else if (ja_fui == 0 && chamado.categoria.equals("Problema de Produto") && head == 3 || head == 0 || head == 1 || head == 2){

	            myDoc.addEle(Heading2.with(chamado.categoria).create());
	            myDoc.addEle(BreakLine.times(1).create());

	            head = 4;
	            ja_fui = 1;

	        }
	        myDoc.addEle(Paragraph.withPieces(titulo).withStyle().bgColor("808080").create());
	        myDoc.addEle(BreakLine.times(2).create());

	        myDoc.addEle(Paragraph.withPieces(nRM).withStyle().bgColor("DCDCDC").create());
	        myDoc.addEle(BreakLine.times(2).create());

	        myDoc.addEle(Paragraph.withPieces(head_desc).withStyle().indent(ParagraphStyle.Indent.ONE).create());
	        myDoc.addEle(BreakLine.times(1).create());

	        myDoc.addEle(Paragraph.withPieces(descricao).withStyle().indent(ParagraphStyle.Indent.TWO).align(ParagraphStyle.Align.JUSTIFIED).create());
	        myDoc.addEle(BreakLine.times(1).create());

	        myDoc.addEle(Paragraph.withPieces(head_impa).withStyle().indent(ParagraphStyle.Indent.ONE).create());
	        myDoc.addEle(BreakLine.times(1).create());

	        myDoc.addEle(Paragraph.withPieces(impacto).withStyle().indent(ParagraphStyle.Indent.TWO).align(ParagraphStyle.Align.JUSTIFIED).create());

	        if (testCaseOK == 1){

	            myDoc.addEle(BreakLine.times(1).create());

	            myDoc.addEle(Paragraph.withPieces(titulo_testcase).withStyle().indent(ParagraphStyle.Indent.ONE).create());
	            myDoc.addEle(BreakLine.times(1).create());
	            myDoc.addEle(Paragraph.withPieces(head_precond).withStyle().indent(ParagraphStyle.Indent.ONE).create());
	            myDoc.addEle(Paragraph.withPieces(precond).withStyle().indent(ParagraphStyle.Indent.TWO).align(ParagraphStyle.Align.JUSTIFIED).create());
	            myDoc.addEle(BreakLine.times(1).create());

	            myDoc.addEle(Paragraph.withPieces(head_passos).withStyle().indent(ParagraphStyle.Indent.ONE).create());
	            myDoc.addEle(Paragraph.withPieces(passos).withStyle().indent(ParagraphStyle.Indent.TWO).align(ParagraphStyle.Align.JUSTIFIED).create());
	            myDoc.addEle(BreakLine.times(1).create());

	            myDoc.addEle(Paragraph.withPieces(head_result).withStyle().indent(ParagraphStyle.Indent.ONE).create());
	            myDoc.addEle(Paragraph.withPieces(result).withStyle().indent(ParagraphStyle.Indent.TWO).align(ParagraphStyle.Align.JUSTIFIED).create());
	            myDoc.addEle(BreakLine.times(2).create());

	            testCaseOK = 0;

	        }else{

	            myDoc.addEle(BreakLine.times(2).create());

	        }

	    } // FIM WHILE RS.NEXT

	    if (tem_reg == 1){
	        File fileObj = new File( RN_Tela.caminho +"RN_"+produto+".doc");

	        PrintWriter writer = null;

	        try {
	            writer = new PrintWriter(fileObj);
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        }

	        String myword = myDoc.getContent();

	        writer.println(myword);
	        writer.close();
	    }
	} // fim method imprime


}
