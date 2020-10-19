package ticTacThink.aplicacao.beans;

public class PerguntaInfo {
    Pergunta pergunta;
    int aparicoes, acertos, erros;

    public PerguntaInfo(Pergunta pergunta, int aparicoes, int acertos, int erros) {
        this.pergunta = pergunta;
        this.aparicoes = aparicoes;
        this.acertos = acertos;
        this.erros = erros;
    }

    @Override
    public String toString() {
        return "PerguntaInfo [acertos=" + acertos + ", aparicoes=" + aparicoes + ", erros=" + erros + ",\npergunta="
                + pergunta + "]";
    }
    
}
