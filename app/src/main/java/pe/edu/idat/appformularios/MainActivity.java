package pe.edu.idat.appformularios;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import pe.edu.idat.appformularios.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements
        View.OnClickListener, AdapterView.OnItemSelectedListener {
    private ActivityMainBinding binding;
    private String estadoCivil = "";
    private List<String> listaPreferencias = new ArrayList<>();
    private List<String> listaPersonas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(
                this, R.array.estado_civil, android.R.layout.simple_spinner_item
        );
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spestadocivil.setAdapter(arrayAdapter);
        binding.spestadocivil.setOnItemSelectedListener(this);
        binding.btnregistrar.setOnClickListener(this);
        binding.btnlistar.setOnClickListener(this);
        binding.cbdeportes.setOnClickListener(this);
        binding.cbdibujo.setOnClickListener(this);
        binding.cbotro.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnregistrar: registrarPersona(); break;
            case R.id.cbdeportes: agregarQuitarPreferencia(view, "Deporte"); break;
            case R.id.cbdibujo: agregarQuitarPreferencia(view, "Dibujo"); break;
            case R.id.cbotro: agregarQuitarPreferencia(view, "Otro"); break;
            case R.id.btnlistar: irListarActivity(); break;
        }
    }

    private void irListarActivity() {
        Intent intentLista = new Intent(this, ListaActivity.class);
        intentLista.putExtra("listaPersonas", (ArrayList<String>)listaPersonas);
        startActivity(intentLista);
    }

    private void registrarPersona(){
        if(validarFormulario()){
            StringBuilder infoPersona = new StringBuilder();
            infoPersona.append(binding.etnombre.getText().toString()+" ");
            infoPersona.append(binding.etapellido.getText().toString()+" ");
            infoPersona.append(obtenerGeneroSelecionado()+" ");
            infoPersona.append(obtenerPreferencias()+" ");
            infoPersona.append(estadoCivil+" ");
            infoPersona.append(binding.swnotificar.isChecked() + " ");
            listaPersonas.add(infoPersona.toString());
            Toast.makeText(this, "Persona Registrada", Toast.LENGTH_LONG).show();
            setearControles();

        }
    }

    private void setearControles() {
        listaPreferencias.clear();
        binding.etnombre.setText("");
        binding.etapellido.setText("");
        binding.radioGroup.clearCheck();
        binding.cbdeportes.setChecked(false);
        binding.cbdibujo.setChecked(false);
        binding.cbotro.setChecked(false);
        binding.spestadocivil.setSelection(0);
        binding.swnotificar.setChecked(false);
        binding.etnombre.setFocusableInTouchMode(true);
        binding.etnombre.requestFocus();
    }

    private void agregarQuitarPreferencia(View view, String preferencia){
        Boolean cheched = ((CheckBox)view).isChecked();
        if(cheched)
            listaPreferencias.add(preferencia);
        else
            listaPreferencias.remove(preferencia);
    }

    private String obtenerPreferencias(){
        String preferencias = "";
        for(String pref: listaPreferencias){
            preferencias += pref + "-";
        }
        return preferencias;
    }

    private String obtenerGeneroSelecionado(){
        String genero = "";
        switch (binding.radioGroup.getCheckedRadioButtonId()){
            case R.id.rbmasculino: genero = binding.rbmasculino.getText().toString(); break;
            case R.id.rbfemenino: genero = binding.rbfemenino.getText().toString(); break;

        }
        return genero;
    }

    private Boolean validarFormulario(){
        Boolean respuesta = false;
        String mensaje = "";
        if(!validarNombreApellido()){
            mensaje = "Ingrese nombre y apellido";
        }else if(!validarGenero()){
            mensaje = "Selecione un Genero";
        }else if(!validarPreferencia()){
            mensaje = "Selecione al menos una preferencia";
        }else if(!validarEStadoCivil()){
            mensaje = "Selecione su estado civil";
        }else{
            respuesta = true;
        }
        if(!respuesta)
            Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
        return respuesta;
    }

    private Boolean validarNombreApellido(){
        Boolean respuesta = true;
        if(binding.etnombre.getText().toString().trim().isEmpty()){
            binding.etnombre.setFocusableInTouchMode(true);
            binding.etnombre.requestFocus();
            respuesta = false;
        }else if(binding.etapellido.getText().toString().trim()
                .isEmpty()){
            binding.etnombre.setFocusableInTouchMode(true);
            binding.etapellido.requestFocus();
            respuesta = false;
        }
        return respuesta;
    }

    private Boolean validarGenero(){
        Boolean respuesta = true;
        if(binding.radioGroup.getCheckedRadioButtonId() == -1){
            respuesta = false;
        }
        return respuesta;
    }

    private Boolean validarPreferencia(){
        Boolean respuesta = false;
        if(binding.cbdeportes.isChecked() || binding.cbdibujo.isChecked() || binding.cbotro.isChecked()){
            respuesta = true;
        }
        return respuesta;
    }

    private Boolean validarEStadoCivil(){
        Boolean respuesta = true;
        if(estadoCivil.equals("")){
            respuesta = false;
        }
        return respuesta;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(i > 0){
            estadoCivil = adapterView.getItemAtPosition(i).toString();
        }else{
            estadoCivil = "";
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}