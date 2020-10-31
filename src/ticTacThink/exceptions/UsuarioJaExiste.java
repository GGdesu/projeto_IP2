package ticTacThink.exceptions;

public class UsuarioJaExiste extends Exception{
	
	public UsuarioJaExiste(String email) {
		super("O email" + email + "Já está em uso");
		
	}
}
