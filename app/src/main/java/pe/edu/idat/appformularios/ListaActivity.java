package pe.edu.idat.appformularios;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import pe.edu.idat.appformularios.databinding.ActivityListaBinding;

public class ListaActivity extends AppCompatActivity {

    private ActivityListaBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ArrayList<String> listaPersonas = (ArrayList<String>) getIntent().getSerializableExtra("listaPersonas");

        ArrayAdapter arrayAdapter = new ArrayAdapter(
                this, android.R.layout.simple_list_item_1, listaPersonas
        );
        binding.lvpersonas.setAdapter(arrayAdapter);

    }
}