package ticTacThink.dados;

import ticTacThink.aplicacao.beans.Usuario;

public interface IGerenciadorUsuario {
	
	public void cadastrar(Usuario usuario);
	public void remover(Usuario usuario);
	public void atualizar(Usuario usuario);
	public Usuario verificarObjeto(String email);
	public boolean verificarExistenciaObjeto(String email);

}
