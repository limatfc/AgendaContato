package br.com.agendacontato

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ContatoViewForm(modifier: Modifier,
                      contatoViewModel : ContatoViewModel){
    Column (modifier ){
        Text(text = "Nome completo:", modifier = Modifier.padding(5.dp))
        OutlinedTextField(
            value = contatoViewModel.nome.value,
            onValueChange = { contatoViewModel.nome.value = it}
        )
        Text(text = "Email:", modifier = Modifier.padding(5.dp))
        OutlinedTextField(
            value = contatoViewModel.email.value,
            onValueChange = { contatoViewModel.email.value = it}
        )
        Text(text = "Telefone:", modifier = Modifier.padding(5.dp))
        OutlinedTextField(
            value = contatoViewModel.telefone.value,
            onValueChange = { contatoViewModel.telefone.value = it}
        )
        Row{
            Button(
                onClick = {
                    contatoViewModel.adicionar()
                }
            ) {
                Text("Gravar")
            }
            Button(
                onClick = {
                   contatoViewModel.procurar()
                }
            ) {
                Text("Pesquisar")
            }

        }
        Column {
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "Nome: ${contatoViewModel.contatoPesquisado.value.nome}")
            Text(text = "Telefone: ${contatoViewModel.contatoPesquisado.value.telefone}")
            Text(text = "Email: ${contatoViewModel.contatoPesquisado.value.email}")
        }

    }
}