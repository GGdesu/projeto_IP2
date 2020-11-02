package ticTacThink.dados.gerenciadores;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

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
		int posicao = verificarPosicao(ranker);
		this.rank.set(posicao, ranker);
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
		for (int i = 0; i < rank.size(); i++) {
			if (ranker.getEmail().equals(rank.get(i).getEmail())) {
				return i;
			}
		}
		return -1; // não encontrado
	}

	@Override
	public ArrayList<Rank> getRank() {
		this.rank.sort(new Comparator<Rank>(){
			@Override
			public int compare(Rank o1, Rank o2) {
				// organizando de forma reversa: maior para o menor
				return o2.getPontuacao() - o1.getPontuacao();
			}
			
		});
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

			String linha = reader.readLine();

			try {
				while (linha != null && !linha.isEmpty()) {
					String[] dados = linha.split(",");
					Rank ranker = new Rank(dados[0], dados[1], Integer.parseInt(dados[2]));
					this.rank.add(ranker);

					linha = reader.readLine();
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
