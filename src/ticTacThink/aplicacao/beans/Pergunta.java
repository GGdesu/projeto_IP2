package ticTacThink.aplicacao.beans;

public class Pergunta {
	private final String categoria;
	private final String tipo;
	private final String dificuldade;
	
	private final String texto;
	
	private final String respostas[];
	private final int certa;
	
	public Pergunta(String categoria, String tipo, String dificuldade, String pergunta,
			String[] respostas, int respostaCerta) {
		this.categoria = categoria;
		this.tipo = tipo;
		this.dificuldade = dificuldade;
		this.texto = pergunta;
		this.respostas = respostas;
		this.certa = respostaCerta;
	}

	boolean responder(String resposta) {
		return resposta.equalsIgnoreCase(this.respostas[certa]); 
	}
	
	boolean responder(int indice) {
		return this.certa == indice;
	}
	
	public String toString() {
		String resultado = 	"Pergunta: " + this.texto + "\n" + 
							"Catergoria: " + this.categoria + "\n" + 
							"Tipo: " + this.tipo + "\n" +
							"Dificuldade: " + this.dificuldade + "\n" + 
							"Alternativas:\n";
		if (respostas != null)
			for (String string : respostas)
				resultado += "\t" + string + "\n";
		return resultado;
	}
	
	// Getters
	public String getTipo() {
		return tipo;
	}
	public String getCategoria() {
		return categoria;
	}
	public String getDificuldade() {
		return dificuldade;
	}
	public String getTexto() {
		return texto;
	}
	public String[] getRespostas() {
		return respostas;
	}
	public int getCerta() {
		return certa;
	}
}
