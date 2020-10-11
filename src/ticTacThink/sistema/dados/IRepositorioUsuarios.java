package ticTacThink.sistema.dados;

import ticTacThink.sistema.negocios.beans.Usuario;

public interface IRepositorioUsuarios {
	
	public void cadastrarUsuario(Usuario usuario);
	public void removerUsuario(Usuario usuario);
	public void atualizarUsuario(Usuario usuario);
	public Usuario verificarUsuario(String email);
	public boolean verificarExistenciaUsuario(String email);

}
