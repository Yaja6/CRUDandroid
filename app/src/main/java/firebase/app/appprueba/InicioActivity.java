package firebase.app.appprueba;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.UUID;

import firebase.app.appprueba.Adaptadores.ListaContactosAdaptador;
import firebase.app.appprueba.Modelos.Contacto;

public class InicioActivity extends AppCompatActivity {


    private ArrayList<Contacto> listaContactos = new ArrayList<Contacto>();
    ArrayAdapter<Contacto> arrayAdapterContacto;
    ListaContactosAdaptador listaContactosAdaptador;
    LinearLayout linearLayoutEditar;

    private EditText etNombre, etTelefono;
    private ListView lContactos;
    private Button btnCancelar;

    //variables para los datos a registrar
    private String id = "";
    private String nombre = "";
    private String telefono = "";

    Contacto contactoSeleccionado;

    //id de usuario actual
    String idCurrentUser = "";

    //FIREBASE
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        inicializarFirebase();
        verContactos();
        idCurrentUser = mAuth.getCurrentUser().getUid(); //toma id del current user
        etNombre = (EditText)findViewById(R.id.nombre);
        etTelefono = (EditText)findViewById(R.id.telefono);
        btnCancelar =(Button) findViewById(R.id.cancelar);
        lContactos = (ListView)findViewById(R.id.contactos);
        linearLayoutEditar = findViewById(R.id.linearLayoutEditar);

        lContactos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                contactoSeleccionado = (Contacto) parent.getItemAtPosition(position); //casting clase persona convierte al objeto en tipo persona (inicializamos) set nombre..
                etNombre.setText(contactoSeleccionado.getNombre());
                etTelefono.setText(contactoSeleccionado.getTelefono());

                //hacer visible LinearLayout con datos
                linearLayoutEditar.setVisibility(View.VISIBLE);
            }
        });
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayoutEditar.setVisibility(View.GONE);
                contactoSeleccionado = null; //objeto nulo para que quite datos al guardar o eliminar
            }
        });

    }
    private void verContactos(){
        databaseReference.child("Contactos").orderByChild("timestamp").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaContactos.clear();
                for (DataSnapshot objSnapShot: snapshot.getChildren()){

                    Contacto c = objSnapShot.getValue(Contacto.class); //toma todos los datos
                    if(c.getIdUser().equals(idCurrentUser)){ // toma los contactos solo del usuario actual
                        listaContactos.add(c);
                    }

                }

                //iniciar adaptador propio
                listaContactosAdaptador = new ListaContactosAdaptador(InicioActivity.this, listaContactos);
                lContactos.setAdapter(listaContactosAdaptador); //necesita un toString -> en model
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void nuevoTelf(View view){
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(InicioActivity.this);

        View mview = getLayoutInflater().inflate(R.layout.insertar, null);
        Button btnAñadir = (Button) mview.findViewById(R.id.agregar);
        final EditText mNombre = (EditText) mview.findViewById(R.id.nombre);
        final EditText mTelefono = (EditText) mview.findViewById(R.id.telefono);

        mBuilder.setView(mview);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();

        btnAñadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nombre = mNombre.getText().toString();
                telefono = mTelefono.getText().toString();

                if(nombre.isEmpty() || nombre.length() < 3){
                    Toast.makeText(InicioActivity.this, "Nombre inválido (Min. 3 dígitos)", Toast.LENGTH_SHORT).show();
                }else if(telefono.isEmpty() || telefono.length() != 10){
                    Toast.makeText(InicioActivity.this, "Teléfono inválido (10 dígitos)", Toast.LENGTH_SHORT).show();
                }else{
                    Contacto c = new Contacto();
                    c.setId(UUID.randomUUID().toString());
                    c.setIdUser(idCurrentUser);
                    c.setNombre(nombre);
                    c.setTelefono(telefono);
                    c.setFecha(getFechaNormal(getFechaMiliseg())); //fecha en milisegundos /fechanormal -> fecha normal
                    c.setTimestamp(getFechaMiliseg() * -1);
                    databaseReference.child("Contactos").child(c.getId()).setValue(c).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(InicioActivity.this, "Contacto registrado", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }else{
                                Toast.makeText(InicioActivity.this, "No se pudo registrar", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }
            }
        });

    }
    public void guardarTelf(View v){
        if(validarInputs() == false){
            Contacto c = new Contacto();
            c.setId(contactoSeleccionado.getId());
            c.setIdUser(idCurrentUser);
            c.setNombre(nombre);
            c.setTelefono(telefono);
            c.setFecha(contactoSeleccionado.getFecha());
            c.setTimestamp(contactoSeleccionado.getTimestamp());
            databaseReference.child("Contactos").child(c.getId()).setValue(c).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(InicioActivity.this, "Contacto actualizado", Toast.LENGTH_SHORT).show();
                        linearLayoutEditar.setVisibility(View.GONE);
                        contactoSeleccionado = null;
                    }else {
                        Toast.makeText(InicioActivity.this, "No se guardaron los cambios", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    public void eliminarContacto(View v){
        Contacto c = new Contacto();
        c.setId(contactoSeleccionado.getId());
        databaseReference.child("Contactos").child(c.getId()).removeValue();
        Toast.makeText(InicioActivity.this, "Eliminado", Toast.LENGTH_SHORT).show();
        contactoSeleccionado = null;
        linearLayoutEditar.setVisibility(View.GONE);
    }
    private void inicializarFirebase(){
        FirebaseApp.initializeApp(this);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
    }
    public long getFechaMiliseg(){
        Calendar calendar = Calendar.getInstance();
        long tiempo = calendar.getTimeInMillis();
        return tiempo;
    }
    public String getFechaNormal(long fechaMiliseg){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT-5"));
        String fecha = sdf.format(fechaMiliseg);
        return fecha;
    }

    public void limpiar(){
        etTelefono.setText("");
        etNombre.setText("");
    }
    public void salir(View v) {
        mAuth.signOut();
        startActivity(new Intent(InicioActivity.this, IniciarSesionActivity.class));
    }
    public boolean validarInputs(){
        nombre = etNombre.getText().toString();
        telefono = etTelefono.getText().toString();
        if(nombre.isEmpty() || nombre.length() < 3){
            Toast.makeText(InicioActivity.this, "Nombre inválido (Min. 3 dígitos)", Toast.LENGTH_SHORT).show();
            return true;
        }else if(telefono.isEmpty() || telefono.length() != 10){
            Toast.makeText(InicioActivity.this, "Teléfono inválido (10 dígitos)", Toast.LENGTH_SHORT).show();
            return  true;
        }else{
            return false;
        }
    }
}