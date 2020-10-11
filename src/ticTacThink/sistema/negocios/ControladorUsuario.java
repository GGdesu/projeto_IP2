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
			System.out.println("parâmetro incorreto.");
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
			//Usuario não existe no banco de dados
			System.out.println("Usuário não encontrado.");
		}
		
	}
	
	//atualiza as informações do usuario.
	//não faço verificação aqui pois o usuario logado não pode ser null.
	public void atualizar(Usuario usuario) {
		this.repositorio.atualizarUsuario(usuario);
		
	}
	
	/*
	 * CREIO QUE NÃO PRECISO COLOCAR UMA FUNÇÃO VERIFICAR E PROCURAR AQUI.
	 * ATÉ O MOMENTO USEI ONDE TINHA QUE USAR AQUI NESSE CONTROLADOR POR MEIO
	 * DA INTERFACE. NO MOMENTO NO GERENCIADOR PRINCIPAL NAO TENHO PQ USAR ELA
	 * E COMO O APP NÃO VAI PERMITIR QUE DÊ PRA PROCURAR OUTROS USUARIO NÃO VEJO
	 * NECCESSIDADE DE USA-LAS PARA MAIS COISA
	 */
	

}
