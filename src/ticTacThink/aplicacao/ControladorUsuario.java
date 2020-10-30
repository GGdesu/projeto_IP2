package ticTacThink.aplicacao;

import ticTacThink.aplicacao.beans.Usuario;
import ticTacThink.dados.IGerenciadorUsuario;
import ticTacThink.exceptions.UsuarioJaExiste;

public class ControladorUsuario {
	
	private IGerenciadorUsuario gerenciadorUsuario;
	
	//Constructor
	public ControladorUsuario(IGerenciadorUsuario instanciaUsuario) {
		this.gerenciadorUsuario = instanciaUsuario;
	}
	
	//METODOS
	public Usuario login(String email, String senha) {
		if(this.gerenciadorUsuario.verificarExistenciaUsuario(email)) {
			Usuario usuario = this.gerenciadorUsuario.verificarUsuario(email);
			if(usuario.getSenha().equals(senha)) {
				return usuario;
			}
		}
		return null;
		
	}
	
	
	public void cadastrar(Usuario usuario) throws UsuarioJaExiste {
		
		if(usuario == null) {
			throw new IllegalArgumentException("par�metro incorreto.");
			
		}else if(!this.gerenciadorUsuario.verificarExistenciaUsuario(usuario.getEmail())) {
			this.gerenciadorUsuario.cadastrar(usuario);
			
		}else {
			throw new UsuarioJaExiste(usuario.getEmail());
			
			
		}
		
	}
	
	/*Usado para excluir a conta do usuario.
	 *o usuario pode excluir a propria conta caso queria, Pensando ainda nisso.
	 *esse remover vai servir pra excluir a conta atual de qualquer jeito.
	*/
	public void remover(Usuario usuario) {
		if(this.gerenciadorUsuario.verificarExistenciaUsuario(usuario.getEmail())) {
			this.gerenciadorUsuario.remover(usuario);
		}else {
			//Usuario n�o existe no banco de dados
			System.out.println("Usu�rio n�o encontrado.");
		}
		
	}
	
	public boolean verificarExistenciaUsuario(String email) {
		return this.gerenciadorUsuario.verificarExistenciaUsuario(email);
		
	}
	
	//atualiza as informa��es do usuario.
	//n�o fa�o verifica��o aqui pois o usuario logado n�o pode ser null.
	public void atualizar(Usuario usuario) {
		this.gerenciadorUsuario.atualizar(usuario);
		
	}
	
	/*
	 * CREIO QUE N�O PRECISO COLOCAR UMA FUN��O VERIFICAR E PROCURAR AQUI.
	 * AT� O MOMENTO USEI ONDE TINHA QUE USAR AQUI NESSE CONTROLADOR POR MEIO
	 * DA INTERFACE. NO MOMENTO NO GERENCIADOR PRINCIPAL NAO TENHO PQ USAR ELA
	 * E COMO O APP N�O VAI PERMITIR QUE D� PRA PROCURAR OUTROS USUARIO N�O VEJO
	 * NECCESSIDADE DE USA-LAS PARA MAIS COISA
	 */
	

}
