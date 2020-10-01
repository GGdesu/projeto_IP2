package projeto_IP2;

public class Pergunta {
	private final String categoria;
	private final String tipo;
	private final String dificuldade;
	
	private final String pergunta;
	
	private final boolean respostaBooleana; 
	private final String respostaCerta; 
	private final String respostasErradas[];
	
	
	public Pergunta() {
		// TODO: Quando TriviaApi estiver pronta
	}
	
	public Pergunta(String categoria, String tipo, String dificuldade, String pergunta, boolean respostaBooleana,
			String respostaCerta, String[] respostasErradas) {
		this.categoria = categoria;
		this.tipo = tipo;
		this.dificuldade = dificuldade;
		this.pergunta = pergunta;
		this.respostaBooleana = respostaBooleana;
		this.respostaCerta = respostaCerta;
		this.respostasErradas = respostasErradas;
	}

	boolean responder(String resposta) {
		if (resposta.equalsIgnoreCase(this.respostaCerta)) {
			return true;
		}
		return false;
	}
	
	boolean responder(boolean resposta) {
		return resposta == this.respostaBooleana;
	}
	
	public String toString() {
		String resultado = 	"Pergunta: " + this.pergunta + "\n" + 
							"Catergoria: " + this.categoria + "\n" + 
							"Tipo: " + this.tipo + "\n" +
							"Dificuldade: " + this.dificuldade + "\n";
		
		resultado += "Alternativas: ";
		if (this.tipo.equals("boolean"))
			resultado += this.respostaBooleana;
		else {
			resultado += this.respostaCerta;
			for (String string : respostasErradas)
				resultado += string;
		}
		return resultado; 
	}
	
	// Getters
	public String getTipo() {
		return tipo;
	}
}
