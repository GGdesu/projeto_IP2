package ticTacThink.aplicacao.beans;

public class Usuario {
	//ATRIBUTOS
	private String nome;
	private String senha;
	private String email;
	private int pontuacao = 0;
	private String pais;
	private String genero;
	private String dataNasc;

	//CONSTRUCTOR
	public Usuario() {}
	
	public Usuario(String nome, String senha, String email, String pais, String genero, String dataNasc) {
		this.nome = nome;
		this.senha = senha;
		this.email = email;
		this.pais = pais;
		this.genero = genero;
		this.dataNasc = dataNasc;
	}


	@Override
	public boolean equals(Object objUsuario) {
		Usuario usuario = (Usuario) objUsuario;
		
		if(usuario != null) {
			if(this.email.equals(usuario.getEmail())) {
				return true;
			}else {
				return false;
			}
		}else {
			return false;
		}
		
	}



	//METODOS GET E SET
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getPontuacao() {
		return pontuacao;
	}

	public void setPontuacao(int pontuacao) {
		this.pontuacao = pontuacao;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public String getDataNasc() {
		return dataNasc;
	}

	public void setDataNasc(String dataNasc) {
		this.dataNasc = dataNasc;
	}

	@Override
	public String toString() {
		return "Usuario [email=" + email + ", genero=" + genero + ", Data de Nascimento=" + dataNasc + ", nome=" + nome + ", pais="
				+ pais + ", pontuacao=" + pontuacao + ", senha=" + senha + "]";
	}
	
	

}
