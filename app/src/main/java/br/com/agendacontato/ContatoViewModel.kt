package br.com.agendacontato

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class CustomHttpLogger : Logger {
    override fun log (message: String) {
        Log.d("AGENDA_CONTATO", message)
    }
}

class ContatoViewModel : ViewModel() {
    private val URL_BASE = "https://agendacontato-24929-default-rtdb.europe-west1.firebasedatabase.app/"
    private val client = HttpClient(Android) {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
            })
        }
        install(Logging) {
            logger = CustomHttpLogger()
            level = LogLevel.ALL
        }
    }

    private val lista = mutableListOf<Contato>()
    var nome =  mutableStateOf("")
    var email =  mutableStateOf("")
    var telefone =  mutableStateOf("")
    var contatoPesquisado = mutableStateOf(Contato("","",""))

    fun adicionar (  ){
        val contato =  Contato(nome.value, email.value, telefone.value )
        lista.add(contato)
        Log.i("agenda_contato", "Lista: $lista")
        viewModelScope.launch {
            client.post(URL_BASE + "contatos.json") {
                setBody(contato)
                contentType(ContentType.Application.Json)
            }
        }
    }

   fun procurar ()  {
       viewModelScope.launch {
          val contatos : Map<String, Contato> = client.get(URL_BASE + "contatos.json").body()
           lista.clear()
           lista.addAll(contatos.values)
           val contato = contatos.values.find { it.nome.contains(nome.value) }
           if (contato == null){
               Log.i("agenda_pesquisar", "Contato: Contato nao encontrado")
               contatoPesquisado.value = Contato("", "", "")
           } else {
               Log.i("agenda_pesquisar", "Contato encontrado: ${contato.nome}")
               contatoPesquisado.value = contato
           }
       }
    }
}