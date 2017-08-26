package com.example.bruno.descubrasenha;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private int p, q, tentativas;
    private int r[] = new int[4];
    private int t[] = new int[4];
    private boolean jogoEncerrado;
    private List<Resposta> listTentativas = new ArrayList<Resposta>();

    private Button btnReiniciar;
    private Button btnSair;
    private Button btnVerificar;
    private Button btnNums[] = new Button[10];
    private ListView lstvRespostas;
    private EditText respostas[] = new EditText[4];
    private TextView lblTentaivas;
    private TextView lblMsgFinalJogo;

    //Construtor para inicializações iniciais
    public MainActivity(){

        //Inicializando o número de tentativas
        this.tentativas = 10;

        //Informando que o jogo não está encerrado.
        this.jogoEncerrado = false;

        //Gerando a senha que se tentará descobrir qual é.
        for (int i = 0; i < 4; i++){
            this.r[i] = (int)(Math.random()*10);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Conectando os elementos do layout ao código

        //Botões Reinicar e Sair
        btnReiniciar = (Button)findViewById(R.id.btnReiniciar);
        btnReiniciar.setOnClickListener(this);

        btnSair = (Button)findViewById(R.id.btnSair);
        btnSair.setOnClickListener(this);

        //TextViews informativos
        lblTentaivas = (TextView)findViewById(R.id.lblTentativas);
        lblTentaivas.setText(String.valueOf(tentativas));
        lblMsgFinalJogo = (TextView)findViewById(R.id.lblMsgFinalJogo);

        //EditTexts das respostas
        respostas[0] = (EditText)findViewById(R.id.edtN1);
        //Impede que o teclado do Android seja ativado. pesquisar mais depois sobre o setInpuType()
        respostas[0].setInputType(0);
        respostas[1] = (EditText)findViewById(R.id.edtN2);
        respostas[1].setInputType(0);
        respostas[2] = (EditText)findViewById(R.id.edtN3);
        respostas[2].setInputType(0);
        respostas[3] = (EditText)findViewById(R.id.edtN4);
        respostas[3].setInputType(0);

        respostas[0].setFocusable(true);
        respostas[0].requestFocus();

        //Adicionando um método para mudar de EditText quando
        //o mesmo for preenchido
        for(int i = 0; i < respostas.length-1; i++){

            final TextView txtAux1 = respostas[i];
            final TextView txtAux2 = respostas[i+1];

            respostas[i].addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    if(txtAux1.length() > 0) {
                        txtAux2.setFocusable(true);
                        txtAux2.requestFocus();
                    }else{
                        txtAux1.setFocusable(true);
                        txtAux1.requestFocus();
                    }
                }
            });
        }

        //Botão Verificar
        btnVerificar = (Button)findViewById(R.id.btnVerificar);
        //Conectando botão ao método click pelo método de listeners
        btnVerificar.setOnClickListener(this);

        //Botões numéricos
        btnNums[0] = (Button)findViewById(R.id.btn0);
        btnNums[1] = (Button)findViewById(R.id.btn1);
        btnNums[2] = (Button)findViewById(R.id.btn2);
        btnNums[3] = (Button)findViewById(R.id.btn3);
        btnNums[4] = (Button)findViewById(R.id.btn4);
        btnNums[5] = (Button)findViewById(R.id.btn5);
        btnNums[6] = (Button)findViewById(R.id.btn6);
        btnNums[7] = (Button)findViewById(R.id.btn7);
        btnNums[8] = (Button)findViewById(R.id.btn8);
        btnNums[9] = (Button)findViewById(R.id.btn9);
        //Conectando botões ao método click pelo método de listeners
        for(int i = 0; i < btnNums.length; i++){
            btnNums[i].setOnClickListener(this);
        }

        //Lista de respostas do usuario
        lstvRespostas = (ListView)findViewById(R.id.lstvRespostas);


    }

    @Override
    public void onClick(View view) {

        //Teremos inicialmente de definir qual botão chamou esse método
        if(view.getId()== btnVerificar.getId()) {

            //Aqui é onde a magia acontece!

            p = 0;
            q = 0;
            //Testando se todos os campos foram preenchidos
            if ((respostas[0].length() > 0) && (respostas[1].length() > 0) && (respostas[2].length() > 0) && (respostas[3].length() > 0)) {

                //Efetuamos o processamento propriamente
                if ((tentativas > 0) && (!jogoEncerrado)) {

                    tentativas--;
                    lblTentaivas.setText(String.valueOf(tentativas));

                    //Passando os valores informados pelo usuario para o array de respostas
                    for (int i = 0; i < t.length; i++) {
                        t[i] = Integer.parseInt(respostas[i].getText().toString());
                    }

                    //Vetor auxiliar para ser exibido já que t[] será modificado
                    //durante o processamento.
                    int aux[] = new int[4];
                    for (int i = 0; i < aux.length; i++) {
                        aux[i] = t[i];
                    }

                    //Verificando quantos valores na posição correta nós temos...
                    for (int i = 0; i < t.length; i++) {
                        if (t[i] == r[i]) {
                            p++;
                        }
                    }

                    //Verificando quantos valores corretos foram informados...
                    for (int i = 0; i < r.length; i++) {
                        for (int j = 0; j < t.length; j++) {
                            if (r[i] == t[j]) {
                                //para o caso de existirem números repetidos ele não contar duas vezes
                                t[j] = 10;

                                //para sair do laço
                                j = 4;

                                //incrementa q
                                q++;
                            }
                        }
                    }

                    //Adicionando na listview..
                    this.listTentativas.add(0, new Resposta(aux, q, p));
                    ArrayAdapter<Resposta> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, this.listTentativas);
                    lstvRespostas.setAdapter(adapter);

                    //Limpando os campos para a proxima rodada
                    this.limpaCampos();

                    //Verifica se o jogo acabou
                    if (p == 4) {

                        String msg = "Parabéns, a combinação correta era: ";
                        for (int i = 0; i < 4; i++) {
                            msg += (r[i]) + " ";
                        }
                        msg += "e você acertou. Jogo encerrado!!!";

                        lblMsgFinalJogo.setTextColor(Color.rgb(0, 0, 255));
                        lblMsgFinalJogo.setText(msg);
                        jogoEncerrado = true;
                    }

                }

            } else {
                Toast.makeText(MainActivity.this, "Todos os valores precisam ser informados!", Toast.LENGTH_SHORT).show();
            }

            if ((tentativas == 0) && (!jogoEncerrado)) {

                String msg = "Limite de tentativas atingido!\n A cobinação correta era: ";
                for (int i = 0; i < 4; i++) {
                    msg += (r[i]) + " ";
                }
                msg += "\nJogo Encerrado!";
                lblMsgFinalJogo.setTextColor(Color.rgb(255, 0, 0));
                lblMsgFinalJogo.setText(msg);
            }

        }else if(view.getId() == btnReiniciar.getId()) {

            if(this.tentativas < 10) {

                //Inicializando o número de tentativas
                this.tentativas = 10;
                lblTentaivas.setText(String.valueOf(this.tentativas));

                //Limpando a listView.
                ArrayAdapter<Resposta> adapter = (ArrayAdapter<Resposta>) lstvRespostas.getAdapter();
                adapter.clear();
                adapter.notifyDataSetChanged();

                //Quantidade de corretas q.
                //Quantidade na posição correta p.
                this.p = 0;
                this.q = 0;

                //Informando que o jogo não está encerrado.
                this.jogoEncerrado = false;

                //Limpando a mensagem final do jogo
                this.lblMsgFinalJogo.setText("");

                //Gerando a senha que se tentará descobrir qual é.
                for (int i = 0; i < 4; i++) {
                    this.r[i] = (int) (Math.random() * 10);
                }

                this.limpaCampos();

                //Colocando o foco no primeiro EditText
                respostas[0].setFocusable(true);
                respostas[0].requestFocus();

            }

        }else if(view.getId() == btnSair.getId()) {

            finishAffinity();

        }else{

            for(int i = 0; i < btnNums.length; i++){

                if(view.getId() == btnNums[i].getId()){

                    for(int j = 0; j < respostas.length; j++){
                        if(respostas[j].hasFocus()){
                            respostas[j].setText(String.valueOf(i));

                            break;
                        }
                    }
                    break;
                }

            }

        }

    }

    private void limpaCampos(){
        for(int i = 0; i < 4; i++){
            this.respostas[i].setText("");
        }

        this.respostas[0].setFocusable(true);
        this.respostas[0].requestFocus();
    }

}
