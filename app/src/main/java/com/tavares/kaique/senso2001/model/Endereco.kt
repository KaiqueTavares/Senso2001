package com.tavares.kaique.senso2001.model

import com.google.gson.annotations.SerializedName

data class  Endereco(
    val cep: String,
    val logradouro: String,
    val complemento: String,
    val bairro: String,
    //SerializedName utilizado para representar o que a api requer para fazer a pesquisa
    //No caso preciso mandar para a API Localidade mas vou chamar no meu projeto
    //A variavel com o nome de Cidade
    //Toda vez que eu quiser passar a variavel com outro nome utilizo este elemento
    @SerializedName("localidade") val cidade:String,
    val uf : String
)
