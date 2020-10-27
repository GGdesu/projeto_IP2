package ticTacThink;

import java.util.ArrayList;

import ticTacThink.aplicacao.ControladorRank;
import ticTacThink.aplicacao.ControladorUsuario;
import ticTacThink.aplicacao.beans.Rank;
import ticTacThink.aplicacao.beans.Usuario;
import ticTacThink.dados.gerenciadores.GerenciadorRank;
import ticTacThink.dados.gerenciadores.GerenciadorUsuario;
import ticTacThink.exceptions.UsuarioJaExiste;

public class GerenciadorPrincipal {
	
	private static GerenciadorPrincipal instancePrincipal;
	private static Usuario usuarioON;

	private ControladorUsuario controladorUsuario;
	private ControladorRank controladorRank;
	
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
	
	//RANK
	public void verificarRanker(Rank ranker) {
		this.controladorRank.verificaPontuacao(ranker);
	}
	
	public ArrayList<Rank> rank() {
		return this.controladorRank.pegarLista();
	}
	
}
