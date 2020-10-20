package ticTacThink.aplicacao;

import ticTacThink.aplicacao.beans.Rank;
import ticTacThink.dados.IGerenciadorRank;

public class ControladorRank {
	
	private IGerenciadorRank gerenciadorRank;
	
	
	public ControladorRank(IGerenciadorRank instanciaRank) {
		this.gerenciadorRank = instanciaRank;
	}
	
	
	public void verificaPontuacao(Rank ranker) {
		
		if(gerenciadorRank.verificarExistenciaObjeto(ranker.getEmail())) {
			ranker = verificarIguais(ranker);
			
		}
		
		if(ranker != null) {
			ranker.setPosicao(gerenciadorRank.verificarPosicao(ranker));
			
		}
		
		
		//Mensagem de parabens com a nova posi��o fica pras telas
		//acho que n�o preciso colocar aqui.		
		
	}
	
	private Rank verificarIguais(Rank ranker) {
		Rank rankIgual = gerenciadorRank.verificarObjeto(ranker.getEmail());
		if(ranker.getPontuacao() > rankIgual.getPontuacao()) {
			gerenciadorRank.remover(rankIgual);
			return ranker;
			
		}else {
			System.out.println("O jogador " + ranker.getNome() + " j� possui uma pontua��o maior no rank.");
			return null;
		}
		
	}
	

}
