package ticTacThink.dados.gerenciadores;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import ticTacThink.aplicacao.beans.Pergunta;
import ticTacThink.aplicacao.beans.PerguntaInfo;

public class GerenciadorPergunta {
	
	private static Gson gson = new Gson();
	private String diretorio = "arquivos/perguntas/";
	private HashMap<String,Integer> categoriasID = new HashMap<>();
	private String tokenDeSessao = null;
	
	// CONSTRUTORES
	public GerenciadorPergunta() {
		this(false);
	}
	public GerenciadorPergunta(boolean useToken) {
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
			tokenDeSessao = token("request");
		
		// criando diretório
		File file = new File(diretorio);
		if (!file.isDirectory())
			file.mkdirs();
		
	}
	
	// MÉTODOS PARA API ONLINE
	// Sessão online (Perguntas não serão repetidas até o fim da lista)
	// @param: comando = [´request´ ou ´reset´]
	private String token(String comando) {
		String link = "https://opentdb.com/api_token.php?command="+ comando;
		if (this.tokenDeSessao != null)
			link += "&" + this.tokenDeSessao;
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
			JsonObject json = gson.fromJson(br, JsonObject.class);
			con.disconnect();
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Não foi possivel baixar o arquivo JSON.");
			return null;
		}
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
	
	// MÉTODOS DE ARMAZENAMENTO
	private File arquivoDa(Pergunta pergunta) {
		return new File(this.diretorio + categoriasID.get(pergunta.getCategoria()) + "/" + 
				pergunta.getTipo() + "/" + pergunta.getDificuldade() + ".json");
	}
	private boolean verificarExistencia(Pergunta pergunta) {
		File arquivo = arquivoDa(pergunta);
		if (!arquivo.exists()) return false;
		try {
			FileReader leitor = new FileReader(arquivo);
			JsonObject json = gson.fromJson(leitor, JsonObject.class);
			return json.has(pergunta.getPergunta());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		
	}
	private void atualizar(Pergunta pergunta, boolean acertada) {
		try {
			File arquivo = arquivoDa(pergunta);
			FileReader leitor = new FileReader(arquivo);
			JsonObject json = gson.fromJson(leitor, JsonObject.class);
			JsonArray info = json.get(pergunta.getPergunta()).getAsJsonArray();
			
			int aparicoes = info.get(0).getAsInt();
			int acertos = info.get(1).getAsInt();
			int erros = info.get(2).getAsInt();
			
			aparicoes++; 
			if (acertada)
				acertos++;
			else
				erros++;
			info.set(0, new JsonPrimitive(aparicoes));				
			info.set(1, new JsonPrimitive(acertos));
			info.set(2, new JsonPrimitive(erros));
			
			json.add(pergunta.getPergunta(), info);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	private void cadastrar(Pergunta pergunta) {
		String caminho = this.diretorio +
				categoriasID.get(pergunta.getCategoria()) +"/"+
				pergunta.getTipo() +"/"+ pergunta.getDificuldade() + ".json";
		try {
			File arquivo = new File(caminho);
			JsonObject json;
			
			if (!arquivo.exists()) {
				arquivo.getParentFile().mkdirs(); 
				json = new JsonObject();
			} else {
				json = gson.fromJson(new FileReader(arquivo), JsonObject.class);
			}
			JsonArray info = new JsonArray();
			info.add(0); // aparicoes
			info.add(0); // acertos
			info.add(0); // erros
			json.add(pergunta.getPergunta(), info);
			
			FileWriter writer = new FileWriter(arquivo);
			gson.toJson(json, writer);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// MÉTODOS PÚBLICOS
	public String[] getCategorias() {
		String[] strings = new String[this.categoriasID.size()];
		return this.categoriasID.keySet().toArray(strings);
	}

	// quantidade [min = 1, max = 50]
	// Any = null (ex: "qualquer categoria" -> null)
	public ArrayList<Pergunta> baixarPerguntas(int quantidade, String categoria, String dificuldade, String tipo) {
		String link = criarLink(quantidade, categoria, dificuldade, tipo);
		JsonObject json = baixarJson(link);
		
		int response_code = json.get("response_code").getAsInt(); 
		
		switch (response_code) {
		case 0:
			JsonArray resultados = json.get("results").getAsJsonArray();
			return converterParaPerguntas(resultados);
		case 1: System.out.println("baixarPerguntas: Sem Resultados!"); break;
		case 2: System.out.println("baixarPerguntas: Parametro Inválido!");	break;
		case 3: System.out.println("baixarPerguntas: Token não encontrado!"); break;
		case 4:
			System.out.println("baixarPerguntas: Token Já obteve todas as perguntas, Resetando...");
			token("reset");
			break;
		}
		return null;
	}
	public ArrayList<Pergunta> baixarPerguntas(int quantidade) {
		return baixarPerguntas(quantidade,null,null,null);
	}

	public void salvarPerguntas(ArrayList<Pergunta> perguntas, boolean[] acertadas) {
		int perguntaAtual = 0;
		for (Pergunta pergunta : perguntas) {
			boolean existe = verificarExistencia(pergunta);
			if (existe)
				atualizar(pergunta, acertadas[perguntaAtual]);
			else
				cadastrar(pergunta);
			perguntaAtual++;
		}
	}

	public ArrayList<PerguntaInfo> lerPerguntas() {
		var dir = new File(this.diretorio);
		var categorias = dir.listFiles();
		var categoriasDisponiveis = this.getCategorias();

		ArrayDeque<PerguntaInfo> deque = new ArrayDeque<>();
		
		// Entrando nos diretorios de categorias 
		for (File dirCategoriaID : categorias) {
			var tipos = dirCategoriaID.listFiles();
			
			// obtendo nome da categoria pelo id
			String categoria = "";
			for (String c : categoriasDisponiveis) {
				
				// compara duas strings numericas e.g "11".equals("11")
				if (this.categoriasID.get(c).toString().equals(dirCategoriaID.getName())) {
					categoria = c;
					break;
				}

			}
			// Para cada pasta de tipo [boolean, multiple]
			for (File tipo : tipos) {
				var dificuldades = tipo.listFiles();

				// Para cada pasta de dificuldade [easy, medium, hard]
				for (File dificuldade : dificuldades) {
					try {
						// Ler arquivo e pegar perguntas
						var arquivo = new BufferedReader(new FileReader(dificuldade));
						JsonObject json = gson.fromJson(arquivo, JsonObject.class);
						var entrySet = json.entrySet();
						for (var entry : entrySet) {
							String pergunta = entry.getKey();
							JsonArray stats = entry.getValue().getAsJsonArray();

							// temporario
							deque.push(new PerguntaInfo(
									new Pergunta(categoria, tipo.getName(), dificuldade.getName(), pergunta, null, 0),
									stats.get(0).getAsInt(), stats.get(1).getAsInt(), stats.get(2).getAsInt()));
						}
						

					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return new ArrayList<PerguntaInfo>(deque);
	}
}
