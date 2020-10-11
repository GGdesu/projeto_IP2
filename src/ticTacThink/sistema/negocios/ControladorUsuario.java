package ticTacThink.sistema.negocios;

import ticTacThink.sistema.dados.IRepositorioUsuarios;
import ticTacThink.sistema.negocios.beans.Usuario;

public class ControladorUsuario {
	IRepositorioUsuarios repositorio;
	
	//Constructor
	public ControladorUsuario(IRepositorioUsuarios instanciaUsuario) {
		this.repositorio = instanciaUsuario;
	}
	
	//METODOS
	public Usuario login(String email, String senha) {
		if(this.repositorio.verificarExistenciaUsuario(email)) {
			Usuario usuario = this.repositorio.verificarUsuario(email);
			if(usuario.getSenha().contains(senha)) {
				return usuario;
			}
		}
		return null;
		
	}
	
	
	public void cadastrar(Usuario usuario) {
		if(usuario == null) {
			//mensagem de erro
			System.out.println("par�metro incorreto.");
		}else if(!this.repositorio.verificarExistenciaUsuario(usuario.getEmail())) {
			this.repositorio.cadastrarUsuario(usuario);
			
		}else {
			//mensagem de usuario existente
			System.out.println("Usuario ja existe.");
			
			
		}
		
	}
	
	//Usado para excluir a conta do usuario.
	//o usuario pode excluir a propria conta caso queria, Pensando ainda nisso.
	//esse remover vai servir pra excluir a conta atual de qualquer jeito.
	public void remover(Usuario usuario) {
		if(this.repositorio.verificarExistenciaUsuario(usuario.getEmail())) {
			this.repositorio.removerUsuario(usuario);
		}else {
			//Usuario n�o existe no banco de dados
			System.out.println("Usu�rio n�o encontrado.");
		}
		
	}
	
	//atualiza as informa��es do usuario.
	//n�o fa�o verifica��o aqui pois o usuario logado n�o pode ser null.
	public void atualizar(Usuario usuario) {
		this.repositorio.atualizarUsuario(usuario);
		
	}
	
	/*
	 * CREIO QUE N�O PRECISO COLOCAR UMA FUN��O VERIFICAR E PROCURAR AQUI.
	 * AT� O MOMENTO USEI ONDE TINHA QUE USAR AQUI NESSE CONTROLADOR POR MEIO
	 * DA INTERFACE. NO MOMENTO NO GERENCIADOR PRINCIPAL NAO TENHO PQ USAR ELA
	 * E COMO O APP N�O VAI PERMITIR QUE D� PRA PROCURAR OUTROS USUARIO N�O VEJO
	 * NECCESSIDADE DE USA-LAS PARA MAIS COISA
	 */
	

}
