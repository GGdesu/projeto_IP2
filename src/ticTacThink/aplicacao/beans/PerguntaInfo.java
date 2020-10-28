package ticTacThink.aplicacao.beans;

public class PerguntaInfo implements Comparable<PerguntaInfo> {
    Pergunta pergunta;
    int aparicoes, acertos;

    public PerguntaInfo(Pergunta pergunta, int aparicoes, int acertos) {
        this.pergunta = pergunta;
        this.aparicoes = aparicoes;
        this.acertos = acertos;
    }

    @Override
    public String toString() {
        return "PerguntaInfo [pergunta=\n" +pergunta.getTexto()+ ", acertos=" + acertos + ", aparicoes=" + aparicoes + "]";
    }

    public Pergunta getPergunta() {
        return pergunta;
    }

    public int getAparicoes() {
        return aparicoes;
    }

    public int getAcertos() {
        return acertos;
    }
    
    @Override
    public int compareTo(PerguntaInfo o) {
        return this.aparicoes - o.getAparicoes();
    }
}
