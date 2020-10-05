package ticTacThink.triviaApi;

import java.net.URL;
import java.util.ArrayList;

import ticTacThink.pergunta.Pergunta;

public class TriviaApi {
	
	ArrayList<Pergunta> baixarPerguntas() { 
		URL url = new URL("https://opentdb.com/api.php?amount=10");
		
		return new ArrayList<Pergunta>();
	}
}
