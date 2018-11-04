package auxiliares;

public class Doc {

	private String nomeResumido;
	private String nome;
	private String endereco;
	private String estado;
	private String municipio;
	private String telefone;
	private String site;
	private String email;
	private String cnpj;
	private String dataAta;
	private String conteudoAta;
	private String presentesAta;

	public Doc(){
	}

	public void setNomeResumido(String nomeResumido){
		this.nomeResumido = nomeResumido;
	}

	public String getNomeResumido(){
		return nomeResumido;
	}

	public void setNome(String nome){
		this.nome = nome;
	}

	public String getNome(){
		return nome;
	}

	public void setEndereco(String endereco){
		this.endereco = endereco ;
	}

	public String getEndereco(){
		return endereco;
	}

	public void setEstado(String estado){
		this.estado = estado;
	}

	public String getEstado(){
		return estado;
	}

	public void setMunicipio(String municipio){
		this.municipio = municipio;
	}

	public String getMunicipio(){
		return municipio;
	}

	public void setTelefone(String telefone){
		this.telefone = telefone;
	}

	public String getTelefone(){
		return telefone;
	}

	public void setSite(String site){
		this.site = site;
	}

	public String getSite(){
		return site;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	public void setCnpj(String cnpj){
		this.cnpj = cnpj;
	}

	public String getCnpj(){
		return cnpj;
	}

	public void setDataAta(String dataAta){
		this.dataAta = dataAta;
	}

	public String getDataAta(){
		return dataAta;
	}

	public void setConteudoAta(String conteudo){
		this.conteudoAta = conteudo;
	}

	public String getConteudoAta(){
		return conteudoAta;
	}

	public void setPresentesAta(String presentes){
		this.presentesAta = presentes;
	}

	public String getPresentesAta(){
		return presentesAta;
	}


}
