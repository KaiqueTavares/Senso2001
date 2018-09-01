package com.tavares.kaique.senso2001.api

import com.tavares.kaique.senso2001.model.Endereco
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CepAPI {

    //{cep} = MINHA VARIAVEL QUE VOU PASSAR NA QUERRY DO MEU PATH = URL
    @GET("/ws/{cep}/json")

    //@Path("cep") estou falando que estou pegando meu path variable e jogando na querry do get acima
    fun pesquisar(@Path("cep")cep: String): Call<Endereco>
}