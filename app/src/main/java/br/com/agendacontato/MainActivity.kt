package br.com.agendacontato

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import br.com.agendacontato.ui.theme.AgendaContatoTheme

class MainActivity : ComponentActivity() {
    private val contatoViewModel : ContatoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AgendaContatoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ContatoViewForm(
                        modifier = Modifier.padding(innerPadding),
                        contatoViewModel
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ContatoPreview(){
    ContatoViewForm(modifier = Modifier, contatoViewModel = ContatoViewModel())
}