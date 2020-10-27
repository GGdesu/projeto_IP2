package ticTacThink.aplicacao.beans;

public class Rank {
	
	private String email;
	private String nome;
	private int pontuacao;
	private int posicao;
	
	//construtor
	public Rank() {}
	
	public Rank(String email, String nome, String pontuacao, String posicao) {
		this.email = email;
		this.nome = nome;
		this.pontuacao = Integer.parseInt(pontuacao);
		this.posicao = Integer.parseInt(posicao);
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

	
	public int getPosicao() {
		return posicao;
	}

	public void setPosicao(int posicao) {
		this.posicao = posicao;
	}

	@Override
	public boolean equals(Object objRank) {
		Rank rank = (Rank) objRank;
		
		if(rank != null) {
			return getEmail().contains(rank.getEmail());
		
		}else {
			return false;
			
		}
				
	}
	

}
