package com.example.bruno.descubrasenha;

/**
 * Created by BRUNO on 25/08/2017.
 */

public class Resposta {

    private int[] resposta = new int[4];
    private int q;
    private int p;

    public Resposta(int[] resposta, int q, int p){
        this.resposta = resposta;
        this.q = q;
        this.p = p;
    }

    public int[] getResposta() {
        return resposta;
    }

    public int getQ() {
        return q;
    }

    public int getP() {
        return p;
    }

    @Override
    public String toString(){
        return "Sua resposta: "+resposta[0]+" "+resposta[1]+" "+resposta[2]+" "+resposta[3]+" - q: "+q+" - p: "+p;
    }

}
