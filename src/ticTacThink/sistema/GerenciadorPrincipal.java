package ticTacThink.sistema;

import ticTacThink.sistema.dados.repositorios.RepositorioUsuarios;
import ticTacThink.sistema.negocios.ControladorUsuario;
import ticTacThink.sistema.negocios.beans.Usuario;

public class GerenciadorPrincipal {
	
	private static GerenciadorPrincipal instancePrincipal;
	private static Usuario usuarioON;

	private ControladorUsuario controladorUsuario;
	
	private GerenciadorPrincipal() {
		controladorUsuario = new ControladorUsuario(RepositorioUsuarios.getInstance());
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
	public void cadastrarUsuario(Usuario usuario) {
		this.controladorUsuario.cadastrar(usuario);
	}
	
	public void atualizarUsuario(Usuario usuario) {
		this.controladorUsuario.atualizar(usuario);
	}
	
	
}
