package ticTacThink.aplicacao;

import ticTacThink.aplicacao.beans.Usuario;
import ticTacThink.dados.IGerenciador;
import ticTacThink.exceptions.UsuarioJaExiste;

public class ControladorUsuario {
	IGerenciador repositorio;
	
	//Constructor
	public ControladorUsuario(IGerenciador instanciaUsuario) {
		this.repositorio = instanciaUsuario;
	}
	
	//METODOS
	public Usuario login(String email, String senha) {
		if(this.repositorio.verificarExistenciaObjeto(email)) {
			Usuario usuario = this.repositorio.verificarObjeto(email);
			if(usuario.getSenha().contains(senha)) {
				return usuario;
			}
		}
		return null;
		
	}
	
	
	public void cadastrar(Usuario usuario) throws UsuarioJaExiste {
		
		if(usuario == null) {
			throw new IllegalArgumentException("parâmetro incorreto.");
			
		}else if(!this.repositorio.verificarExistenciaObjeto(usuario.getEmail())) {
			this.repositorio.cadastrar(usuario);
			
		}else {
			throw new UsuarioJaExiste(usuario.getEmail());
			
			
		}
		
	}
	
	/*Usado para excluir a conta do usuario.
	 *o usuario pode excluir a propria conta caso queria, Pensando ainda nisso.
	 *esse remover vai servir pra excluir a conta atual de qualquer jeito.
	*/
	public void remover(Usuario usuario) {
		if(this.repositorio.verificarExistenciaObjeto(usuario.getEmail())) {
			this.repositorio.remover(usuario);
		}else {
			//Usuario não existe no banco de dados
			System.out.println("Usuário não encontrado.");
		}
		
	}
	
	//atualiza as informações do usuario.
	//não faço verificação aqui pois o usuario logado não pode ser null.
	public void atualizar(Usuario usuario) {
		this.repositorio.atualizar(usuario);
		
	}
	
	/*
	 * CREIO QUE NÃO PRECISO COLOCAR UMA FUNÇÃO VERIFICAR E PROCURAR AQUI.
	 * ATÉ O MOMENTO USEI ONDE TINHA QUE USAR AQUI NESSE CONTROLADOR POR MEIO
	 * DA INTERFACE. NO MOMENTO NO GERENCIADOR PRINCIPAL NAO TENHO PQ USAR ELA
	 * E COMO O APP NÃO VAI PERMITIR QUE DÊ PRA PROCURAR OUTROS USUARIO NÃO VEJO
	 * NECCESSIDADE DE USA-LAS PARA MAIS COISA
	 */
	

}
