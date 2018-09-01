package com.tavares.kaique.senso2001

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.facebook.stetho.Stetho
import com.tavares.kaique.senso2001.api.CepAPI
import com.tavares.kaique.senso2001.model.Endereco
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.facebook.stetho.okhttp3.StethoInterceptor
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.OkHttpClient




class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Inicializando o Stetho
        Stetho.initializeWithDefaults(this);

        //Ao clicar no meu botão vou executar a função de pesquisar o cep
        btPesquisar.setOnClickListener { pesquisarCep() }
    }

    private fun pesquisarCep(){

        //Aqui estou colocando o metodo dele de carregar dentro de uma variavel para
        //Que seja iniciada toda vez que eu chamar esta função
        val okHttpClient = OkHttpClient.Builder()
                .addNetworkInterceptor(StethoInterceptor())
                .build();

        val retrofit = Retrofit.Builder()
                .baseUrl("https://viacep.com.br")
                .addConverterFactory(GsonConverterFactory.create())
                //Toda vez que eu chamar meu Retrofit vou executar o okHttpCliente
                .client(okHttpClient)
                .build()

        val service = retrofit.create(CepAPI::class.java)
        service.pesquisar("09931260")
                //Estou executando um call back em um log por tras
                //Primeiro crio um object que recebe um callBack do retroFit e pego meu arquivo Endereco
                //Depois disto clico com alt+Enter em object para criar os overrider fun
                .enqueue(object : Callback<Endereco>{
                    override fun onFailure(call: Call<Endereco>?, t: Throwable?) {
                        //O Errro retorna dentro do Throwable
                       exibeErro(t)
                    }
                    //Dando sucesso na requisição do dado
                    override fun onResponse(call: Call<Endereco>?, response: Response<Endereco>?) {
                        //Vou chamar minha função de preencher o endereço passando a resposta dele
                        preencheEndereco(response?.body())
                    }
                })
    }

    //Função que chamo quando eu consigo pegar os dados na API
    //Pego o endereco e permito nulo para receber pois o onResponse pode mandar nulo logo tenho que receber nulo
    private fun preencheEndereco(endereco: Endereco?){
        //Coloco o logradouro no meu toast
        Toast.makeText(this,endereco?.logradouro,Toast.LENGTH_LONG).show()
    }

    //Função para erro
    //Como quando der falha ele vai dar um Throwable
    //Logo vou receber o mesmo Throwable
    private fun exibeErro(erro: Throwable?){
        //Jogo a mensagem de erro no toast
        Toast.makeText(this,erro?.message,Toast.LENGTH_LONG).show()
    }
}
