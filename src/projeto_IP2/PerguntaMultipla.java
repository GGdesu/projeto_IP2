package projeto_IP2;

public class PerguntaMultipla extends Pergunta {
	
	private String[] respostas;
	private int certa;

	public PerguntaMultipla(String categoria, String dificuldade, String pergunta,
			String[] respostas, int certa) {
		
		super(categoria, dificuldade, pergunta);
		this.respostas = respostas;
		this.certa = certa;
	}
	
	boolean responder(String resposta) {
		return resposta.equalsIgnoreCase(respostas[certa]);
	}
	
	boolean responder(int indice) {
		return indice == this.certa;
	}
	
	public String toString() {
		String res = super.toString();
		for (String resposta : respostas)
			res += "\n" + resposta;
		return res;
	}
}
