package ticTacThink.sistema.dados.repositorios;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import ticTacThink.sistema.dados.IRepositorioUsuarios;
import ticTacThink.sistema.negocios.beans.Usuario;

public class RepositorioUsuarios implements IRepositorioUsuarios{
	
	private static final String CAMINHO_ARQUIVO = "arquivo\\usuarios.csv";
	
	private static RepositorioUsuarios uniqueInstance;
	
	private ArrayList<Usuario> usuarios;
	
	
	private RepositorioUsuarios() {
		lerArquivo(CAMINHO_ARQUIVO);
	}
	
	public static RepositorioUsuarios getInstance() {
		if(uniqueInstance == null) {
			uniqueInstance = new RepositorioUsuarios();
		}
		
		return uniqueInstance;
	}
	
	public void cadastrarUsuario(Usuario usuario) {
		this.usuarios.add(usuario);
		atualizarArquivo();
	}
	
	public void removerUsuario(Usuario usuario) {
		this.usuarios.remove(usuario);
		atualizarArquivo();
	}
	
	public void atualizarUsuario(Usuario usuario) {
		int posUsuario = this.usuarios.indexOf(usuario);
		this.usuarios.set(posUsuario, usuario);
		atualizarArquivo();
	}
	
	public Usuario verificarUsuario(String email) {
		
		for(Usuario u : this.usuarios) {
			if(u.getEmail().contains(email)) {
				return u;
			}
		}
		
		return null;
	}
	
	public boolean verificarExistenciaUsuario(String email) {
		
		for(Usuario u: this.usuarios) {
			if(u.getEmail().contains(email)) {
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
	
	/*
	 * criado pra testar no main de test
	public ArrayList<Usuario> getUser() {
		return this.usuarios;
	}
	 */
	
	/*
	 * main de test
	 
	public static void main(String[] args){
		
		//BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("C:\\Users\\gabri\\Desktop\\projeto_ip2\\usuario.csv")));
		RepositorioUsuarios repo = new RepositorioUsuarios();
		repo.lerArquivo();
		
		for(Usuario u : repo.getUser()) {
			
			System.out.println( u.getNome());
			System.out.println( u.getEmail());
			System.out.println( u.getSenha());
			System.out.println( u.getPais() );
			System.out.println( u.getGenero());
			System.out.println( u.getIdade());
			System.out.println("------------");
		}
		
		
	}
	*/
	

}
