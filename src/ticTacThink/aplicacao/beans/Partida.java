package ticTacThink.aplicacao.beans;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;
import java.util.NoSuchElementException;

public class Partida {

    // Parametros selecionados
    // serão null caso não seja selecionado
    private String categoria;
    private String dificuldade;
    private String tipo;

    private int pontuacao;

    private boolean ranqueada;
    private Deque<Pergunta> perguntas;
    private Pergunta perguntaAtual;
    private Deque<PerguntaInfo> respondidas;

    public Partida(String categoria, String dificuldade, String tipo, boolean ranqueada, Collection<Pergunta> perguntas) {
        this.dificuldade = dificuldade;
        this.categoria = categoria;
        this.tipo = tipo;
        this.respondidas = new ArrayDeque<>();
        this.perguntas = new ArrayDeque<>(perguntas);
        this.ranqueada = ranqueada;
    }

    public boolean isRanqueada() {
        return ranqueada;
    }

    // numero de perguntas restantes
    public int numPerguntasRestante(){
        return perguntas.size();
    }

    // Salva uma referencia da pergunta e a entrega.
    public Pergunta pegarPergunta() throws NoSuchElementException {
        perguntaAtual = this.perguntas.pop();
        return perguntaAtual;
    }

    // Responde a ultima pergunta pedida.
    public boolean responderPergunta(String resposta) {
        boolean acertou = perguntaAtual.responder(resposta);
        if (acertou) pontuacao += 20;
        PerguntaInfo perguntaInfo = new PerguntaInfo(perguntaAtual, 1, acertou ? 1 : 0);

        respondidas.push(perguntaInfo);
        perguntaAtual = null;
        return acertou;
    }

    // Adiciona novas perguntas ao deque
    public void adicionarPerguntas(Collection<Pergunta> perguntasAdicionais) {
        perguntas.addAll(perguntasAdicionais);
    }

    // Get / Set
    
    public Deque<PerguntaInfo> getRespondidas() {
        return respondidas;
    }

    public String getDificuldade() {
        return dificuldade;
    }

    public void setDificuldade(String dificuldade) {
        this.dificuldade = dificuldade;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getPontuacao() {
        return pontuacao;
    }
}