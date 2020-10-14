package ticTacThink.dados.repositorios;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import ticTacThink.aplicacao.beans.Usuario;
import ticTacThink.dados.IGerenciador;

public class GerenciadorUsuario implements IGerenciador{
	
	private static final String CAMINHO_ARQUIVO = "arquivo\\usuarios.csv";
	
	private static GerenciadorUsuario uniqueInstance;
	
	private ArrayList<Usuario> usuarios;
	
	
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
	public void cadastrar(Object bean) {
		Usuario usuario = (Usuario) bean;
		this.usuarios.add(usuario);
		atualizarArquivo();
	}
	
	@Override
	public void remover(Object bean) {
		Usuario usuario = (Usuario) bean;
		this.usuarios.remove(usuario);
		atualizarArquivo();
	}
	
	@Override
	public void atualizar(Object bean) {
		Usuario usuario = (Usuario) bean;
		int posUsuario = this.usuarios.indexOf(usuario);
		this.usuarios.set(posUsuario, usuario);
		atualizarArquivo();
	}
	
	@Override
	public Usuario verificarObjeto(String parametro) {
		
		for(Usuario u : this.usuarios) {
			if(u.getEmail().contains(parametro)) {
				return u;
			}
		}
		
		return null;
	}
	
	@Override
	public boolean verificarExistenciaObjeto(String parametro) {
		
		for(Usuario u: this.usuarios) {
			if(u.getEmail().contains(parametro)) {
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
		this.usuarios = new ArrayList<Usuario>();
		
		try {
			
			BufferedReader reader = new BufferedReader(new FileReader(caminhoArquivo));
			
			String linha = null;
			
			try {
				
				while((linha = reader.readLine()) != null) {
					
					String[] dados = linha.split(",");
					Usuario usuario = new Usuario(dados[0], dados[1], dados[2], dados[3], dados[4], dados[5]);
					this.usuarios.add(usuario);
				}
				
				reader.close();
				
			}catch(IOException e) {}
			
		}catch(FileNotFoundException e) {
			System.out.println("Arquivo Não Encontrado!");
		}
		
	}


}
