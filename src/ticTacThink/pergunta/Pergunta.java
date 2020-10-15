package ticTacThink.pergunta;

public class Pergunta {
	private final String categoria;
	private final String tipo;
	private final String dificuldade;
	
	private final String pergunta;
	
	private final String respostas[];
	private final int certa;
	
	public Pergunta(String categoria, String tipo, String dificuldade, String pergunta,
			String[] respostas, int respostaCerta) {
		this.categoria = categoria;
		this.tipo = tipo;
		this.dificuldade = dificuldade;
		this.pergunta = pergunta;
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
		String resultado = 	"Pergunta: " + this.pergunta + "\n" + 
							"Catergoria: " + this.categoria + "\n" + 
							"Tipo: " + this.tipo + "\n" +
							"Dificuldade: " + this.dificuldade + "\n" + 
							"Alternativas:\n";
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
	public String getPergunta() {
		return pergunta;
	}
	public String[] getRespostas() {
		return respostas;
	}
}
