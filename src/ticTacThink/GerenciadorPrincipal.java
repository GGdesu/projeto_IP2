package ticTacThink;

import ticTacThink.aplicacao.ControladorUsuario;
import ticTacThink.aplicacao.beans.Usuario;
import ticTacThink.dados.gerenciadores.GerenciadorUsuario;
import ticTacThink.exceptions.UsuarioJaExiste;

public class GerenciadorPrincipal {
	
	private static GerenciadorPrincipal instancePrincipal;
	private static Usuario usuarioON;

	private ControladorUsuario controladorUsuario;
	
	private GerenciadorPrincipal() {
		controladorUsuario = new ControladorUsuario(GerenciadorUsuario.getInstance());
	}
	
	//FUNÇAO NO PADRÃO SINGLETON
	public static GerenciadorPrincipal getInstance() {
		if(instancePrincipal == null) {
			instancePrincipal = new GerenciadorPrincipal();
		}
		
		return instancePrincipal;
	}
	
	//FUNÇÃO NO PADRÃO SINGLETON
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
	
	
}
