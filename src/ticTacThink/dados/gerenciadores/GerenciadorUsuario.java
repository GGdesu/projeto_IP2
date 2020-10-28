package ticTacThink.dados.gerenciadores;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import ticTacThink.aplicacao.beans.Usuario;
import ticTacThink.dados.IGerenciadorUsuario;

public class GerenciadorUsuario implements IGerenciadorUsuario{
	
	private static final String CAMINHO_ARQUIVO = "arquivos/usuarios.csv";
	
	private static GerenciadorUsuario uniqueInstance;
	
	private ArrayList<Usuario> usuarios = new ArrayList<>();
	
	
	private GerenciadorUsuario() {
		lerArquivo(CAMINHO_ARQUIVO);
	}
	
	public static GerenciadorUsuario getInstance() {
		if(uniqueInstance == null) {
			uniqueInstance = new GerenciadorUsuario();
		}
		
		return uniqueInstance;
	}
	
	@Override
	public void cadastrar(Usuario usuario) {
		this.usuarios.add(usuario);
		atualizarArquivo();
	}
	
	@Override
	public void remover(Usuario usuario) {
		this.usuarios.remove(usuario);
		atualizarArquivo();
	}
	
	@Override
	public void atualizar(Usuario usuario) {
		int posUsuario = this.usuarios.indexOf(usuario);
		this.usuarios.set(posUsuario, usuario);
		atualizarArquivo();
	}
	
	@Override
	public Usuario verificarObjeto(String email) {
		
		for(Usuario u : this.usuarios) {
			if(u.getEmail().equals(email)) {
				return u;
			}
		}
		
		return null;
	}
	
	@Override
	public boolean verificarExistenciaObjeto(String email) {
		
		for(Usuario u: this.usuarios) {
			if(u.getEmail().equals(email)) {
				return true;
			}
		}
		
		return false;
	}
	
	public void atualizarArquivo() {
		
		try {
			FileWriter csvWriter = new FileWriter(CAMINHO_ARQUIVO);
			for(Usuario u : this.usuarios) {
				csvWriter.append(u.getNome());
				csvWriter.append(",");
				csvWriter.append(u.getSenha());
				csvWriter.append(",");
				csvWriter.append(u.getEmail());
				csvWriter.append(",");
				csvWriter.append(u.getPais());
				csvWriter.append(",");
				csvWriter.append(u.getGenero());
				csvWriter.append(",");
				csvWriter.append(String.valueOf(u.getIdade()));
				csvWriter.append(",");
				csvWriter.append(String.valueOf(u.getPontuacao()));
				csvWriter.append("\n");
			}
			
			csvWriter.flush();
			csvWriter.close();
		}catch(IOException e) {
			e.printStackTrace();
			
		}
		
	}
	
	
	public void lerArquivo(String caminhoArquivo) {
		
		
		try {

			BufferedReader reader = new BufferedReader(new FileReader(caminhoArquivo));

			String linha = null;

			try {

				while ((linha = reader.readLine()) != null) {

					String[] dados = linha.split(",");
					Usuario usuario = new Usuario(dados[0], dados[1], dados[2], dados[3], dados[4], dados[5]);
					this.usuarios.add(usuario);
				}

				reader.close();

			} catch (IOException e) {
				e.printStackTrace();
			}

		} catch (FileNotFoundException e1) {
			System.out.println("Usuario: Arquivo inexistente!");
		}
	}
}
