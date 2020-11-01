package ticTacThink;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ticTacThink.aplicacao.ControladorRank;
import ticTacThink.aplicacao.ControladorUsuario;
import ticTacThink.aplicacao.beans.*;
import ticTacThink.dados.gerenciadores.*;
import ticTacThink.exceptions.UsuarioJaExiste;

public class GerenciadorPrincipal {
	
	private static GerenciadorPrincipal instancePrincipal;
	private static Usuario usuarioON;
	private static Partida partida;

	private ControladorUsuario controladorUsuario;
	private ControladorRank controladorRank;
	private GerenciadorPergunta gerenciadorPergunta = new GerenciadorPergunta(false); // TODO: será true após todos os testes
	private final List<String> CATEGORIAS_DISPONIVEIS = gerenciadorPergunta.getCategorias();
	
	//CONSTRUCTOR
	private GerenciadorPrincipal() {
		controladorUsuario = new ControladorUsuario(GerenciadorUsuario.getInstance());
		controladorRank = new ControladorRank(GerenciadorRank.getInstance());
	}
	
	//FUN�AO NO PADR�O SINGLETON
	public static GerenciadorPrincipal getInstance() {
		if(instancePrincipal == null) {
			instancePrincipal = new GerenciadorPrincipal();
		}
		
		return instancePrincipal;
	}
	
	//FUN��O NO PADR�O SINGLETON
	public static Usuario getUsuarioInstancia() {
		return usuarioON;
	}
	
	//LOGIN
	public void login(String email, String senha) {
		if(instancePrincipal != null && usuarioON == null) {
			usuarioON = this.controladorUsuario.login(email, senha);
		}
	}
	
	public void logout() {
		if(instancePrincipal != null && usuarioON != null) {
			usuarioON = null;
		}
	}
	
	//USUARIO
	public void cadastrarUsuario(Usuario usuario) throws UsuarioJaExiste {
		this.controladorUsuario.cadastrar(usuario);
	}
	
	public void atualizarUsuario(Usuario usuario) {
		this.controladorUsuario.atualizar(usuario);
	}
	
	public boolean existeUsuario(String email) {
		return this.controladorUsuario.verificarExistenciaUsuario(email);
	}
	
	public void removerConta(Usuario usuario) {
		this.controladorUsuario.remover(usuario);
	}
	
	//RANK
	public void verificarRanker(Rank ranker) {
		this.controladorRank.verificaPontuacao(ranker);
	}
	
	public ArrayList<Rank> rank() {
		return this.controladorRank.pegarLista();
	}


	//GerenciadorPergunta
	public List<Pergunta> baixarPerguntas(int quantidade, String categoria, String dificuldade, String tipo) {
		return gerenciadorPergunta.baixarPerguntas(quantidade, categoria, dificuldade, tipo);
	}

	public List<Pergunta> baixarPerguntas(int quantidade) {
		return gerenciadorPergunta.baixarPerguntas(quantidade);
	}

	public void salvarPerguntas(Collection<PerguntaInfo> perguntas) {
		gerenciadorPergunta.salvarPerguntas(perguntas);
	}

	public List<PerguntaInfo> estatisticasPerguntas() {
		return gerenciadorPergunta.lerPerguntas();
	}

	public List<String> getCategoriasDisponiveis() {
		return this.CATEGORIAS_DISPONIVEIS;
	}

	//Partida
	public void criarPartida(String categoria, String dificuldade, String tipo, boolean ranqueada, Collection<Pergunta> perguntas) {
		partida = new Partida(categoria, dificuldade, tipo, ranqueada, perguntas);
	}

	public static Partida getPartida() {
		return partida;
	}

	public void finalizarPartida() {
		// TODO: salvar para o usuario atual as perguntas no seu histórico
		var respondidas = partida.getRespondidas();
		gerenciadorPergunta.salvarPerguntas(respondidas);
	}

}
