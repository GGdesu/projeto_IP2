package projeto_IP2;

public class PerguntaBooleana extends Pergunta {
	
	public boolean resposta;
	
	public PerguntaBooleana(String categoria, String dificuldade, String pergunta, boolean resposta) {
		super(categoria, dificuldade, pergunta);
		this.resposta = resposta; 
	}
	boolean responder(boolean resposta) {
		return resposta == this.resposta;
	}
	
	public String toString() {
		String res = super.toString();
		res += "true / false";
		return res;
	}
}
