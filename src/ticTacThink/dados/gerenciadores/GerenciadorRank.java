package ticTacThink.dados.gerenciadores;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import ticTacThink.aplicacao.beans.Rank;
import ticTacThink.dados.IGerenciadorRank;

public class GerenciadorRank implements IGerenciadorRank {

	private static final String CAMINHO_ARQUIVO = "arquivos/rank.csv";

	private static GerenciadorRank uniqueInstance;

	private ArrayList<Rank> rank = new ArrayList<>();


	private GerenciadorRank() {
		lerArquivo(CAMINHO_ARQUIVO);

	}

	public static GerenciadorRank getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new GerenciadorRank();
		}
		return uniqueInstance;
	}

	@Override
	public void cadastrar(Rank ranker) {
		this.rank.add(ranker);
		atualizarArquivo();

	}

	@Override
	public void remover(Rank ranker) {
		this.rank.remove(ranker);
		atualizarArquivo();

	}

	@Override
	public void atualizar(Rank ranker) {
		this.rank.set(ranker.getPosicao(), ranker);
		atualizarArquivo();

	}

	@Override
	public Rank verificarObjeto(String email) {

		for (Rank r : this.rank) {
			if (r.getEmail().equals(email)) {
				return r;
			}
		}

		return null;
	}

	@Override
	public boolean verificarExistenciaObjeto(String email) {

		for (Rank r : this.rank) {
			if (r.getEmail().equals(email)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public int verificarPosicao(Rank ranker) {
		// Ele come�a com 100 pois � uma pontua��o que n�o entra no rank. (rank pos 0 -
		// 99)
		int pos = 100;

		for (Rank r : this.rank) {
			if ((ranker.getPontuacao() >= r.getPontuacao()) && (pos > r.getPosicao())) {
				pos = r.getPosicao();
			}
		}
		return pos;
	}

	@Override
	public ArrayList<Rank> getRank() {
		return this.rank;
	}

	public void atualizarArquivo() {

		try {
			FileWriter csvWriter = new FileWriter(CAMINHO_ARQUIVO);
			for (Rank r : this.rank) {
				csvWriter.append(r.getEmail());
				csvWriter.append(",");
				csvWriter.append(r.getNome());
				csvWriter.append(",");
				csvWriter.append(String.valueOf(r.getPontuacao()));
				csvWriter.append(",");
				csvWriter.append(String.valueOf(r.getPosicao()));
				csvWriter.append("\n");
			}

			csvWriter.flush();
			csvWriter.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void lerArquivo(String caminho) {

		try {

			BufferedReader reader = new BufferedReader(new FileReader(caminho));

			String linha = null;

			try {
				
				while ((linha = reader.readLine()) != null) {
					
					String[] dados = linha.split(",");
					Rank ranker = new Rank(dados[0], dados[1], dados[2], dados[3]);
					this.rank.add(ranker);

				}

				reader.close();

			} catch (IOException e) {
				e.printStackTrace();
			}

		} catch (IOException e) {
			System.out.println("Rank: Arquivo inexistente!");

		}
	}

}
