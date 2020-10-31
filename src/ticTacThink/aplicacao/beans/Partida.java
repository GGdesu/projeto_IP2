package ticTacThink.aplicacao.beans;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;

public class Partida {

    private boolean ranqueada;
    private Deque<Pergunta> perguntas;
    private Pergunta perguntaAtual;
    private Deque<PerguntaInfo> respondidas;

    public Partida(boolean ranqueada, Collection<Pergunta> perguntas) {
        respondidas = new ArrayDeque<>();
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

    /* 
     * Salva uma referencia da pergunta e a entrega.
     */
    public Pergunta pegarPergunta() {
        perguntaAtual = this.perguntas.pop();
        return perguntaAtual;
    }

    /* 
     * Responde a ultima pergunta pedida.
     */
    public void responderPergunta(String resposta) {
        boolean acertou = perguntaAtual.responder(resposta);
        PerguntaInfo perguntaInfo = new PerguntaInfo(perguntaAtual, 1, acertou ? 1 : 0);

        respondidas.push(perguntaInfo);
        perguntaAtual = null;
    }


    // pega array de respondidas para salvar
    public Deque<PerguntaInfo> getRespondidas() {
        return respondidas;
    }
}