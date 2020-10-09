package ticTacThink.triviaApi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import ticTacThink.pergunta.Pergunta;

public class TriviaApi {
	
	// ATRIBUTOS 
	private HashMap<String,Integer> categoriasID = new HashMap<String, Integer>();
	private String tokenDeSessao = null;
	// TODO: resetar token ao fim de suas questões
	
	// CONSTRUTORES
	public TriviaApi() {
		this(false);
	}
	
	public TriviaApi(boolean useToken) {
		// Pega todas as categorias disponiveis
		JsonObject json = baixarJson("https://opentdb.com/api_category.php");
		JsonArray categorias = json.get("trivia_categories").getAsJsonArray();
		for (JsonElement jsonElement : categorias) {
			JsonObject objeto = jsonElement.getAsJsonObject();
			String nome = objeto.get("name").getAsString();
			Integer id = objeto.get("id").getAsInt();
			categoriasID.put(nome,id);
		}
		if (useToken)
			tokenDeSessao = pegarNovoToken();
		
	}
	
	public String[] getCategorias() {
		Object[] obj = this.categoriasID.keySet().toArray();
		String[] array = new String[obj.length];
		for (int i = 0; i < array.length; i++)
			array[i] = obj[i].toString();
		return array;
	}
	
	// MÉTODOS PRIVADOS
	private String pegarNovoToken() {
		String link = "https://opentdb.com/api_token.php?command=request";
		JsonObject json = baixarJson(link);

		int response_code = json.get("response_code").getAsInt();
		System.out.println(json.get("response_message").getAsString());
		
		if (response_code == 0)
			return json.get("token").getAsString();
		return null;
	}
	
	private JsonObject baixarJson(String link) {		
		try {
			URL url = new URL(link);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			
			if (con.getResponseCode() != 200)
				System.out.print("HTTP error code : " + con.getResponseCode());
			
			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			
			String output,json="";
			while ((output = br.readLine()) != null)
				json += output;
			con.disconnect();
			
			return new Gson().fromJson(json, JsonObject.class);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Não foi possivel baixar o arquivo JSON.");
		return null;
	}
	
	private ArrayList<Pergunta> converterParaPerguntas(JsonArray array) {
		ArrayList<Pergunta> perguntas = new ArrayList<Pergunta>();
		for (JsonElement jsonElement : array) {
			JsonObject pergunta = jsonElement.getAsJsonObject();
			
			String tipo = pergunta.get("type").getAsString();
			String categoria = pergunta.get("category").getAsString();
			String dificuldade = pergunta.get("difficulty").getAsString();
			String textoPergunta = pergunta.get("question").getAsString();
			
			String respostaCerta = pergunta.get("correct_answer").getAsString();
			JsonArray respostasErradas = pergunta.get("incorrect_answers").getAsJsonArray();
			
			String[] respostas;
			int indiceRespostaCerta = 0;				
			
			if (tipo.equals("boolean")) {
				respostas = new String[2];
				respostas[0] = "True";
				respostas[1] = "False";
			} else { // multiple
				// Misturando respostas
				ArrayList<String> sorteador = new ArrayList<String>(4);
				sorteador.add(respostaCerta);
				for (JsonElement resposta : respostasErradas)
					sorteador.add(resposta.getAsString());
				Collections.shuffle(sorteador);
				
				// Convertendo para array
				respostas = new String[4];
				respostas = sorteador.toArray(respostas);
			}
			
			// Encontrando indice da resposta certa
			for (int i = 0; i < respostas.length; i++) {
				if (respostas[i].equals(respostaCerta)) {
					indiceRespostaCerta = i;
					break;
				}
			}
			perguntas.add(new Pergunta(categoria, tipo, dificuldade, textoPergunta, respostas, indiceRespostaCerta));
		} 
		return perguntas;
	}
	
	private String criarLink(int quantidade, String categoria, String dificuldade, String tipo) {
		assert(quantidade > 0);
		String link = "https://opentdb.com/api.php?";
		link += "amount="+quantidade;
		if (categoria != null)
			link += "&category="+this.categoriasID.get(categoria);
		if (dificuldade != null)
			link += "&difficulty="+ dificuldade;
		if (tipo != null)
			link += "&type="+ tipo;
		if (this.tokenDeSessao != null)
			link += "&token="+ this.tokenDeSessao;
		return link;
	}
	
	// MÉTODOS PÚBLICOS
	
	// quantidade [min = 1, max = 50]
	// Any = null (ex: "qualquer categoria" -> null)
	public ArrayList<Pergunta> baixarPerguntas(int quantidade, String categoria, String dificuldade, String tipo) {
		String link = criarLink(quantidade, categoria, dificuldade, tipo);
		JsonObject json = baixarJson(link);
		
		int response_code = json.get("response_code").getAsInt(); 
		
		if (response_code > 1) {
			System.out.println("Erro: response_code = "+response_code); 
			return null;
		}
		JsonArray resultados = json.get("results").getAsJsonArray();
		return converterParaPerguntas(resultados);
	}
	
	public ArrayList<Pergunta> baixarPerguntas(int quantidade) {
		return baixarPerguntas(quantidade,null,null,null);
	}
	
	public void salvarPerguntas(ArrayList<Pergunta> perguntas, boolean[] acertadas) {
		int perguntaAtual = 0;
		Gson gson = new Gson();
		try {
			for (Pergunta pergunta : perguntas) {
				// Ajustando diretório
				String path = "data/perguntas/";
				File file = new File(path);
				
				// criando diretório
				if (!file.isDirectory())
					file.mkdirs();
				
				path += categoriasID.get(pergunta.getCategoria()) + ".json";
				file = new File(path);

				JsonObject json;
				JsonArray array; // [aparições, acertos, erros]
				
				boolean arquivoExiste = file.exists();
				if (arquivoExiste)
					json = gson.fromJson(new FileReader(file), JsonObject.class); // lê
				else
					json = new JsonObject(); // cria
												
				boolean perguntaJaFeita = json.has(pergunta.getPergunta());
				if (perguntaJaFeita) {
					array = json.get(pergunta.getPergunta()).getAsJsonArray(); // pega					
				} else { 
					array = new JsonArray(); // cria
					array.add(0);
					array.add(0);
					array.add(0);
				}
				
				int aparicoes = array.get(0).getAsInt();
				int acertos = array.get(1).getAsInt();
				int erros = array.get(2).getAsInt(); 
				
				// atualizando valores
				aparicoes++; 
				if (acertadas[perguntaAtual])
					acertos++;
				else
					erros++;
				
				// enviando para o array
				array.set(0, new JsonPrimitive(aparicoes));				
				array.set(1, new JsonPrimitive(acertos));
				array.set(2, new JsonPrimitive(erros));
				
				// Adicionando pergunta e valores
				json.add(pergunta.getPergunta(), array);
				try (FileWriter writer = new FileWriter(file)){
					gson.toJson(json, writer);
				}
				perguntaAtual++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
