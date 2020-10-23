package ticTacThink.dados;

import java.util.ArrayList;

import ticTacThink.aplicacao.beans.Rank;

public interface IGerenciadorRank {

	public void cadastrar(Rank ranker);
	public void remover(Rank ranker);
	public void atualizar(Rank ranker);
	public Rank verificarObjeto(String email);
	public boolean verificarExistenciaObjeto(String email);
	public int verificarPosicao(Rank ranker);
	public ArrayList<Rank> getRank();

}
