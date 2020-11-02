package ticTacThink.aplicacao.beans;

public class Rank {
	
	private String email;
	private String nome;
	private int pontuacao;
	
	//construtor
	public Rank() {}
	
	public Rank(String email, String nome, int pontuacao) {
		this.email = email;
		this.nome = nome;
		this.pontuacao = pontuacao;
	}

	//getters & setters
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getPontuacao() {
		return pontuacao;
	}

	public void setPontuacao(int pontuacao) {
		this.pontuacao = pontuacao;
	}

	public boolean equals(Rank rank) {

		if(rank != null) {
			return getEmail().contains(rank.getEmail());
		
		}else {
			return false;
			
		}
				
	}

	@Override
	public String toString() {
		return "Rank [email=" + email + ", nome=" + nome + ", pontuacao=" + pontuacao + "]";
	}
	

}
