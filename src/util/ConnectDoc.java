package util;

import java.awt.Desktop;
import java.awt.Window;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import javax.swing.JOptionPane;

import auxiliares.Doc;
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
	private Doc doc = new Doc();
	private boolean dadosHeader = false;
	private boolean dadosAta = false;
	private String ataAno = null;

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

	public void geraDoc(String numeroAta, boolean inseriuCaminho, boolean  alterouCaminho, String path){

		boolean executaAlteracao = false;

		if(inseriuCaminho){
			sql = "insert into caminho_impressao (usuario, caminho)values((select usuario from usuario_logado where status = 'S'), '"+path+"')";
			executaAlteracao = true;
		}else if(alterouCaminho){
			sql = "update caminho_impressao set usuario = (select usuario from usuario_logado where status = 'S'), caminho = "+path+"')";
			executaAlteracao = true;
		}

		if(executaAlteracao){
			try {
				 pst = (OraclePreparedStatement) conn.prepareStatement(sql);
			     pst.executeUpdate();
			     pst.close();
			     conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}


	    IDocument myDoc = new word.w2004.Document2004();
	    myDoc.encoding(Document2004.Encoding.ISO8859_1); //or ISO8859-1. Default is UTF-8

	    sql = "select nome_resumido,nome,endereco,estado,municipio,telefone,email,site,cnpj from dados";

	    conn = ConnectDB.Connect();
	    try {
	        pst = (OraclePreparedStatement) conn.prepareStatement(sql);
	        rs = (OracleResultSet) pst.executeQuery();

	        if(rs.next()){
	        	doc.setNomeResumido((rs.getString(1).trim().equals("")? "": rs.getString(1)));
	        	doc.setNome((rs.getString(2).trim().equals("")? "" : rs.getString(2)));
	        	doc.setEndereco((rs.getString(3).trim().equals("")? "" : rs.getString(3)));
	        	doc.setEstado((rs.getString(4).trim().equals("")? "" : rs.getString(4)));
	        	doc.setMunicipio((rs.getString(5).trim().equals("")? "" : rs.getString(5)));
	        	doc.setTelefone((rs.getString(6).trim().equals("")? "" : rs.getString(6)));
	        	doc.setEmail((rs.getString(7).trim().equals("")? "" : rs.getString(7)));
	        	doc.setSite((rs.getString(8).trim().equals("")? "" : rs.getString(8)));
	        	doc.setCnpj((rs.getString(9).trim().equals("")? "" : rs.getString(9)));
	        	setDadosHeader(true);
	        }else{
	        	setDadosHeader(false);
	        }

	        conn.close();
	        rs.close();
	        pst.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    try {
			conn = ConnectDB.Connect();
			sql = "select data, conteudo, presentes from atas where id_ata = '"+numeroAta+"'";

			pst = (OraclePreparedStatement) conn.prepareStatement(sql);
			rs = (OracleResultSet) pst.executeQuery();

			if(rs.next()){
				doc.setDataAta(new SimpleDateFormat("EEEE, dd 'de' MMMM 'de' yyyy").format(rs.getDate(1)));
				ataAno = new SimpleDateFormat("yyyy").format(rs.getDate(1));
				doc.setConteudoAta(rs.getString(2));
				doc.setPresentesAta(rs.getString(3));
				setDadosAta(true);
			}else{
				setDadosAta(false);
			}

			 conn.close();
		     rs.close();
		     pst.close();

		} catch (Exception e) {
			// TODO: handle exception
		}

	    if(isDadosHeader()){

	    	ParagraphPiece nome  = ParagraphPiece.with(doc.getNome()).withStyle().bold().caps().fontSize("14").create();
	        ParagraphPiece nomeResumido = ParagraphPiece.with(doc.getNomeResumido()).withStyle().bold().caps().fontSize("14").create();
	        ParagraphPiece nomeNomeResumido = ParagraphPiece.with(doc.getNome()+" - " + doc.getNomeResumido()).withStyle().bold().caps().fontSize("14").create();
	        ParagraphPiece endereco = ParagraphPiece.with(doc.getEndereco()).withStyle().fontSize("11").create();
	        ParagraphPiece estado = ParagraphPiece.with(doc.getEstado()).withStyle().smallCaps().fontSize("11").create();
	        ParagraphPiece enderecoMunicipioEstado = ParagraphPiece.with(doc.getEndereco() + ", " + doc.getMunicipio() + " - " + doc.getEstado()).withStyle().smallCaps().fontSize("11").create();
	        ParagraphPiece municipio = ParagraphPiece.with(doc.getMunicipio()).withStyle().fontSize("11").create();
	        ParagraphPiece municipioEstado = ParagraphPiece.with(doc.getMunicipio() + " - " + doc.getEstado()).withStyle().fontSize("11").create();
	        ParagraphPiece enderecoMunicipio = ParagraphPiece.with(doc.getEndereco() + ", " + doc.getMunicipio()).withStyle().fontSize("11").create();
	        ParagraphPiece telefone = ParagraphPiece.with(doc.getTelefone()).withStyle().fontSize("11").create();
	        ParagraphPiece email = ParagraphPiece.with(doc.getEmail()).withStyle().fontSize("11").create();
	        ParagraphPiece telefoneEmail = ParagraphPiece.with(doc.getTelefone() + " - " + doc.getEmail()).withStyle().fontSize("11").create();
	        ParagraphPiece site = ParagraphPiece.with(doc.getSite()).withStyle().fontSize("11").create();
	        ParagraphPiece cnpj = ParagraphPiece.with("CNPJ: " + doc.getCnpj()).withStyle().fontSize("11").create();
	        ParagraphPiece siteCnpj = ParagraphPiece.with(doc.getSite() + ", CNPJ: " + doc.getCnpj()).withStyle().fontSize("11").create();


	        if(!doc.getNome().trim().equals("")){
	        	if(!doc.getNomeResumido().trim().equals("")){
	        		myDoc.getHeader().addEle(Paragraph.withPieces(nomeNomeResumido).withStyle().align(ParagraphStyle.Align.CENTER).create());
	        	}else{
	        		myDoc.getHeader().addEle(Paragraph.withPieces(nome).withStyle().align(ParagraphStyle.Align.CENTER).create());
	        	}
	        }else{
	        	if(!doc.getNomeResumido().trim().equals("")){
		        	myDoc.getHeader().addEle(Paragraph.withPieces(nomeResumido).withStyle().align(ParagraphStyle.Align.CENTER).create());
		        }
	        }

	        if(!doc.getEndereco().trim().equals("")){
	        	if(!doc.getMunicipio().trim().equals("")){
	        		if(!doc.getEstado().trim().equals("")){
		        		myDoc.getHeader().addEle(Paragraph.withPieces(enderecoMunicipioEstado).withStyle().align(ParagraphStyle.Align.CENTER).create());
	        		}else{
		        		myDoc.getHeader().addEle(Paragraph.withPieces(enderecoMunicipio).withStyle().align(ParagraphStyle.Align.CENTER).create());
	        		}
	        	}else{
		        	myDoc.getHeader().addEle(Paragraph.withPieces(endereco).withStyle().align(ParagraphStyle.Align.CENTER).create());
	        	}
	        }else{
	        	if(!doc.getMunicipio().trim().equals("")){
	        		if(!doc.getEstado().trim().equals("")){
		        		myDoc.getHeader().addEle(Paragraph.withPieces(municipioEstado).withStyle().align(ParagraphStyle.Align.CENTER).create());
	        		}else{
		        		myDoc.getHeader().addEle(Paragraph.withPieces(municipio).withStyle().align(ParagraphStyle.Align.CENTER).create());
	        		}
	        	}else{
	        		if(!doc.getEstado().trim().equals("")){
		        		myDoc.getHeader().addEle(Paragraph.withPieces(estado).withStyle().align(ParagraphStyle.Align.CENTER).create());
	        		}
	        	}
	        }


	        if(!doc.getTelefone().trim().equals("")){
	        	if(!doc.getEmail().trim().equals("")){
	        		myDoc.getHeader().addEle(Paragraph.withPieces(telefoneEmail).withStyle().align(ParagraphStyle.Align.CENTER).create());
	        	}else{
		        	myDoc.getHeader().addEle(Paragraph.withPieces(telefone).withStyle().align(ParagraphStyle.Align.CENTER).create());
	        	}
	        }else{
	        	if(!doc.getEmail().trim().equals("")){
	        		myDoc.getHeader().addEle(Paragraph.withPieces(email).withStyle().align(ParagraphStyle.Align.CENTER).create());
	        	}
	        }


	       	if(!doc.getSite().trim().equals("")){
	       		if(!doc.getCnpj().trim().equals("")){
	        		myDoc.getHeader().addEle(Paragraph.withPieces(siteCnpj).withStyle().align(ParagraphStyle.Align.CENTER).create());
	        	}else{
	        		myDoc.getHeader().addEle(Paragraph.withPieces(site).withStyle().align(ParagraphStyle.Align.CENTER).create());
	        	}
	        }else{
	        	if(!doc.getCnpj().trim().equals("")){
	        		myDoc.getHeader().addEle(Paragraph.withPieces(cnpj).withStyle().align(ParagraphStyle.Align.CENTER).create());
	        	}
	        }

	    }

	    if(isDadosAta()){

	    	ParagraphPiece conteudo  = ParagraphPiece.with(doc.getConteudoAta()).withStyle().fontSize("12").create();
	        ParagraphPiece presentes = ParagraphPiece.with(doc.getPresentesAta()).withStyle().fontSize("12").create();
	        ParagraphPiece dados = ParagraphPiece.with("ATA Nº0" + numeroAta +"/" + ataAno).withStyle().bold().fontSize("12").create();
	        ParagraphPiece data = ParagraphPiece.with(doc.getDataAta()).withStyle().fontSize("12").create();
	        ParagraphPiece dados2 = ParagraphPiece.with("Presentes na Reuni\u00E3o").withStyle().bold().fontSize("12").create();

    		myDoc.addEle(BreakLine.times(2).create());


	    	myDoc.addEle(Paragraph.withPieces(data).withStyle().align(ParagraphStyle.Align.RIGHT).create());

	    	myDoc.addEle(BreakLine.times(2).create());

	    	myDoc.addEle(Paragraph.withPieces(dados).withStyle().align(ParagraphStyle.Align.CENTER).create());

	    	myDoc.addEle(BreakLine.times(2).create());

	    	myDoc.addEle(Paragraph.withPieces(conteudo).withStyle()//.indent(ParagraphStyle.Indent.ONE)
	    			.align(ParagraphStyle.Align.JUSTIFIED).create());

	    	myDoc.addEle(BreakLine.times(2).create());

	    	myDoc.addEle(Paragraph.withPieces(dados2).withStyle().align(ParagraphStyle.Align.JUSTIFIED).create());

	    	myDoc.addEle(BreakLine.times(1).create());

	    	myDoc.addEle(Paragraph.withPieces(presentes).withStyle()//.indent(ParagraphStyle.Indent.ONE)
	    			.align(ParagraphStyle.Align.JUSTIFIED).create());

	    }

	    if (isDadosAta()){

	        File fileObj = new File( path + File.separator +"ATA0"+numeroAta+".doc");
	        PrintWriter writer = null;

	        try {
	            writer = new PrintWriter(fileObj);
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        }

	        String myword = myDoc.getContent();

	        writer.println(myword);
	        writer.close();

	        String[] option = {"Sim", "Não", "Cancelar"};

	        int answer = JOptionPane.showOptionDialog(null, "Ata gerada com sucesso, deseja abri-la?", "Finalizado",
	                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
	                null, option, null);

	        if(answer == 0){
	        	Desktop desktop = Desktop.getDesktop();
	        	try {
	        		if(fileObj.exists())
	        			desktop.open(fileObj);
	        		else
	        			throw new IOException("Erro ao abrir arquivo");
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, "Falha ao Abrir o Arquivo!");
					e.printStackTrace();
				}
	        }
	    }
	}


	public String getPath(){
		conn = ConnectDB.Connect();
		sql = "select caminho from caminho_impressao where usuario = (select usuario from usuario_logado where status = 'S')";

		try {
			pst = (OraclePreparedStatement) conn.prepareStatement(sql);
			rs = (OracleResultSet) pst.executeQuery();

			if(rs.next())
				return rs.getString(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "";
	}

	private void setDadosHeader(boolean dadosHeader){
		this.dadosHeader = dadosHeader;
	}

	private boolean isDadosHeader(){
		return dadosHeader;
	}

	private void setDadosAta(boolean dadosAta){
		this.dadosAta = dadosAta;
	}

	private boolean isDadosAta(){
		return dadosAta;
	}



}
