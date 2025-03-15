package br.com.agendacontato

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.agendacontato.ui.theme.AgendaContatoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val lista = mutableListOf<Contato>()


        setContent {
            AgendaContatoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ContatoFormulario(
                        modifier = Modifier.padding(innerPadding),
                        onGravar = { nome, telefone, email ->
                           val contato =  Contato(nome, telefone, email)
                            lista.add(contato)
                            Log.i("agenda_contato", "Lista: $lista")
                        },
                        onPesquisar = { nome ->
                            var contato = lista.find { it.nome === nome }
                            if (contato === null){
                                Log.i("agenda_pesquisar", "Contato: Contato nao encontrado")
                                return@ContatoFormulario Contato("", "", "")
                            }
                            Log.i("agenda_pesquisar", "Contato encontrado: ${contato.nome}")
                            return@ContatoFormulario contato
                        },

                    )
                }
            }
        }
    }
}

@Composable
fun ContatoFormulario(modifier: Modifier,
                      onGravar: (String,  String, String)->Unit,
                      onPesquisar: (String)->Contato){

    var nome by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var telefone by remember { mutableStateOf("") }
    var contatoPesquisado by remember {mutableStateOf(Contato("","",""))}

   Column (modifier ){
       Text(text = "Nome completo:", modifier = Modifier.padding(5.dp))
       OutlinedTextField(
           value = nome,
           onValueChange = { nome = it}
       )
       Text(text = "Email:", modifier = Modifier.padding(5.dp))
       OutlinedTextField(
           value = email,
           onValueChange = { email = it}
       )
       Text(text = "Telefone:", modifier = Modifier.padding(5.dp))
       OutlinedTextField(
           value = telefone,
           onValueChange = { telefone = it}
       )
       Row{
           Button(
               onClick = {
                   onGravar(nome, telefone, email)
               }
           ) {
               Text("Gravar")
           }
           Button(
               onClick = {
                   val contato = onPesquisar(nome)
                   contatoPesquisado = contato
                   nome = contatoPesquisado.nome
                   telefone = contatoPesquisado.telefone
                   email = contatoPesquisado.email
               }
           ) {
               Text("Pesquisar")
           }
           Text(text = "Nome: ${contatoPesquisado.nome}")
           Text(text = "Telefone: ${contatoPesquisado.telefone}")
           Text(text = "Email: ${contatoPesquisado.email}")
       }


   }
}

@Preview(showBackground = true)
@Composable
fun ContatoPreview(){
    ContatoFormulario(modifier = Modifier, onGravar = {nome,telefone,email-> }, onPesquisar = { nome -> Contato("", "", "")})
}