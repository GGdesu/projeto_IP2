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
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import org.jsoup.Jsoup;

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
	private List<Pergunta> converterParaPerguntas(JsonArray array) {
		List<Pergunta> perguntas = new ArrayList<Pergunta>();
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
				List<String> sorteador = new ArrayList<String>(4);
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
			
			// Correção de elementos HTML
			textoPergunta = Jsoup.parse(textoPergunta).text();
			for (int i = 0; i < respostas.length; i++) {
				respostas[i] = Jsoup.parse(respostas[i]).text();
			}
			// Adicionando a lista
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
	private File getArquivo(Pergunta pergunta) {
		return new File(this.diretorio + categoriasID.get(pergunta.getCategoria()) + "/" + 
				pergunta.getTipo() + "/" + pergunta.getDificuldade() + ".json");
	}

	private boolean verificarExistencia(PerguntaInfo perguntaInfo) {
		File arquivo = getArquivo(perguntaInfo.getPergunta());
		if (!arquivo.exists()) return false;
		try {
			FileReader leitor = new FileReader(arquivo);
			JsonObject json = gson.fromJson(leitor, JsonObject.class);
			return json.has(perguntaInfo.getPergunta().getTexto());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		
	}
	private void atualizar(PerguntaInfo perguntaInfo) {
		try {
			File arquivo = getArquivo(perguntaInfo.getPergunta());
			FileReader leitor = new FileReader(arquivo);
			JsonObject json = gson.fromJson(leitor, JsonObject.class);
			String textoPergunta = perguntaInfo.getPergunta().getTexto();
			JsonObject perguntaJson = json.get(textoPergunta).getAsJsonObject();

			var array = perguntaJson.get("info").getAsJsonArray();
			int aparicoes = array.get(0).getAsInt();
			int acertos	  = array.get(1).getAsInt();
			
			aparicoes += perguntaInfo.getAparicoes(); 
			acertos += perguntaInfo.getAcertos();
			
			array.set(0, new JsonPrimitive(aparicoes));				
			array.set(1, new JsonPrimitive(acertos));

			FileWriter writer = new FileWriter(arquivo);
			gson.toJson(json, writer);
			writer.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void cadastrar(PerguntaInfo perguntaInfo) {
		var pergunta = perguntaInfo.getPergunta();
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

			JsonArray arrayRespostas = new JsonArray();
			arrayRespostas.add(pergunta.getCerta());
			for (var r : pergunta.getRespostas())
			arrayRespostas.add(r);
			
			JsonArray arrayInfo = new JsonArray();
			arrayInfo.add(perguntaInfo.getAparicoes());
			arrayInfo.add(perguntaInfo.getAcertos());
			
			JsonObject item = new JsonObject();
			item.add("resp", arrayRespostas);
			item.add("info", arrayInfo);

			// Fomato "Pergunta":["resposta": [...], "info": [...]]
			json.add(pergunta.getTexto(), item);
			
			FileWriter writer = new FileWriter(arquivo);
			gson.toJson(json, writer);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// MÉTODOS PÚBLICOS
	public List<String> getCategorias() {
		ArrayList<String> strings = new ArrayList<>(this.categoriasID.keySet());
		Collections.sort(strings);
		return strings;
	}

	// quantidade [min = 1, max = 50]
	// Any = null (ex: "qualquer categoria" -> null)
	public List<Pergunta> baixarPerguntas(int quantidade, String categoria, String dificuldade, String tipo) {
		String link = criarLink(quantidade, categoria, dificuldade, tipo);
		JsonObject json = baixarJson(link);
		
		int response_code = json.get("response_code").getAsInt(); 
		JsonArray resultados = json.get("results").getAsJsonArray();

		switch (response_code) {
		case 0:
			return converterParaPerguntas(resultados);

		case 1: 
			System.out.println("baixarPerguntas: Resultados Reduzidos... foram encontrados "+ resultados.size() +" dos "+ quantidade); // mesmo assim tenta pegar algo
			if (this.tokenDeSessao != null)
				token("reset");
			return converterParaPerguntas(resultados);

		case 2: System.out.println("baixarPerguntas: Parametro Inválido.");	break;

		case 3: System.out.println("baixarPerguntas: Token não encontrado."); break;

		case 4:
			System.out.println("baixarPerguntas: Token Já obteve todas as perguntas, Resetando...");
			token("reset");
			return baixarPerguntas(quantidade, categoria, dificuldade, tipo);
		}
		return null;
	}
	public List<Pergunta> baixarPerguntas(int quantidade) {
		return baixarPerguntas(quantidade,null,null,null);
	}

	public void salvarPerguntas(Collection<PerguntaInfo> perguntas) {
			for (PerguntaInfo pergunta : perguntas) {
			boolean existe = verificarExistencia(pergunta);
			if (existe) atualizar(pergunta);
			else cadastrar(pergunta);
		}
	}

	public List<PerguntaInfo> lerPerguntas() {
		File dir = new File(this.diretorio);
		File[] pastasCategorias = dir.listFiles();
		List<String> listaCategoria = this.getCategorias();

		ArrayDeque<PerguntaInfo> deque = new ArrayDeque<>();
		
		// Para cada diretório de categoria
		for (File pastaCategoria : pastasCategorias) {
			File[] pastasTipos = pastaCategoria.listFiles();
			
			// obtendo nome da categoria pelo id
			String nomeCategoria = "";
			for (String categoria : listaCategoria) {
				// compara duas strings numericas e.g "11".equals("11")
				if (this.categoriasID.get(categoria).toString().equals(pastaCategoria.getName())) {
					nomeCategoria = categoria;
					break;
				}
			}
			// Para cada pasta de tipo [boolean, multiple]
			for (File pastaTipo : pastasTipos) {
				File[] arquivosDificuldades = pastaTipo.listFiles();

				// Para cada pasta de dificuldade [easy, medium, hard]
				for (File arquivoDificuldade : arquivosDificuldades) {
					try {
						// Ler arquivo e pegar perguntas
						BufferedReader leitorArquivo = new BufferedReader(new FileReader(arquivoDificuldade));
						JsonObject json = gson.fromJson(leitorArquivo, JsonObject.class);
						var entrySet = json.entrySet();
						for (var entry : entrySet) {
							String pergunta = entry.getKey();

							// Lendo estatisticas
							JsonArray estatisticas = entry.getValue().getAsJsonObject().get("info").getAsJsonArray();
							JsonArray resp = entry.getValue().getAsJsonObject().get("resp").getAsJsonArray();
							
							// Lendo Respostas
							int certa = resp.get(0).getAsInt();
							String respostas[] = new String[resp.size()-1]; // -1: retirando valor da resposta certa
							for (int i = 1; i < resp.size(); i++)
								respostas[i-1] = resp.get(i).getAsString();
							
							String tipo = pastaTipo.getName();
							String dificuldade = arquivoDificuldade.getName().replace(".json", "");

							var perguntaLida = new Pergunta(nomeCategoria, tipo, dificuldade, pergunta, respostas, certa);
							
							int acertos = estatisticas.get(0).getAsInt();
							int aparicoes = estatisticas.get(1).getAsInt();
							var perguntaInfoLida = new PerguntaInfo(perguntaLida, acertos, aparicoes);
							// adicionando ao conjunto
							deque.push(perguntaInfoLida);
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
