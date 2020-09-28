package projeto_IP2;

public class Pergunta {
	private final String categoria;
	private final String dificuldade;
	private final String pergunta;
	
	public Pergunta(String categoria, String dificuldade, String pergunta) {
		this.categoria = categoria;
		this.dificuldade = dificuldade;
		this.pergunta = pergunta;
	}
	
	public String toString() {
		return "Pergunta: " + this.pergunta + "\n" + 
				"Catergoria: " + this.categoria + "\n" + 
				"Dificuldade: " + this.dificuldade + "\n" +
				"Alternativas: ";
	}
}
