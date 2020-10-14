package ticTacThink.dados;

import ticTacThink.aplicacao.beans.Usuario;

public interface IGerenciador {
	
	public void cadastrar(Object bean);
	public void remover(Object bean);
	public void atualizar(Object bean);
	public Usuario verificarObjeto(String parametro);
	public boolean verificarExistenciaObjeto(String parametro);

}
