package ticTacThink.aplicacao;

import java.util.ArrayList;

import ticTacThink.aplicacao.beans.Rank;
import ticTacThink.dados.IGerenciadorRank;

public class ControladorRank {
	
	private IGerenciadorRank gerenciadorRank;
	
	
	public ControladorRank(IGerenciadorRank instanciaRank) {
		this.gerenciadorRank = instanciaRank;
	}
	public void adicionar(Rank rank) {
		boolean existe = gerenciadorRank.verificarExistenciaObjeto(rank.getEmail());
		if (existe) {
			gerenciadorRank.atualizar(rank);
		} else {
			gerenciadorRank.cadastrar(rank);
		}
	}
	public ArrayList<Rank> pegarLista() {
		return gerenciadorRank.getRank();
	}
	
}
