package projeto_IP2;
import java.util.*;

public class Partida {
	
	private boolean rankeada;
	private Usuario usuario;
	private float tempo;
	ArrayDeque<Pergunta> perguntas = new ArrayDeque<Pergunta>;
	ArrayDeque<Pergunta> respondidas = new ArrayDeque<Pergunta>;
	

	//CONSTRUTOR
	
	public Partida(boolean rankeada, Usuario usuario, float tempo, ArrayDeque<Pergunta> perguntas,
			ArrayDeque<Pergunta> respondidas) {
		
		
		super();
		this.rankeada = rankeada;
		this.usuario = usuario;
		this.tempo = tempo;
		this.perguntas = perguntas;
		this.respondidas = respondidas;
	
	}
	
	
	
	//GETS & SETS
	

	public boolean isRankeada() {
		return rankeada;
	}

	public void setRankeada(boolean rankeada) {
		this.rankeada = rankeada;
	}


	public Usuario getUsuario() {
		return usuario;
	}


	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}


	public float getTempo() {
		return tempo;
	}


	public void setTempo(float tempo) {
		this.tempo = tempo;
	}


	public ArrayDeque<Pergunta> getPerguntas() {
		return perguntas;
	}


	public void setPerguntas(ArrayDeque<Pergunta> perguntas) {
		this.perguntas = perguntas;
	}


	public ArrayDeque<Pergunta> getRespondidas() {
		return respondidas;
	}


	public void setRespondidas(ArrayDeque<Pergunta> respondidas) {
		this.respondidas = respondidas;
	}
	

}
