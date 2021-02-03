package firebase.app.appprueba;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RegistroActivity extends AppCompatActivity {
    private EditText etNombre, etCorreo, etContra;
    private Button bRegistrar;

    //variables para los datos a registrar
    private String nombre = "";
    private String correo = "";
    private String contra = "";

    //dependencias
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        etNombre = (EditText)findViewById(R.id.nombre);
        etCorreo = (EditText)findViewById(R.id.correo);
        etContra = (EditText)findViewById(R.id.contrasena);
        bRegistrar = (Button)findViewById(R.id.btnRegistrar);

        bRegistrar.setOnClickListener((v)->{
            nombre = etNombre.getText().toString();
            correo = etCorreo.getText().toString().trim();
            contra = etContra.getText().toString();

            if(!nombre.isEmpty() && !correo.isEmpty() && !contra.isEmpty()){
                if(contra.length() >= 6){
                    registrarUsuario();
                }else{
                    Toast.makeText(RegistroActivity.this, "La contraseña debe tener al menos 6 dígitios", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(RegistroActivity.this, "Campos obligatorios", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private  void registrarUsuario(){
        mAuth.createUserWithEmailAndPassword(correo, contra).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) { //si se crea usuario
                    Map<String, Object> map = new HashMap<>();
                    map.put("nombre", nombre);
                    map.put("correo", correo);
                    map.put("contra", contra);

                    String id = mAuth.getCurrentUser().getUid();
                    mDatabase.child("Usuarios").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {if(task2.isSuccessful()){ //si se registran los datos en la base de datos
                                startActivity(new Intent(RegistroActivity.this, InicioActivity.class));
                                Toast.makeText(RegistroActivity.this, "Usuario registrado", Toast.LENGTH_SHORT).show();
                                finish();
                            }else{
                                Toast.makeText(RegistroActivity.this, "No se registraron los datos correctamente", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(RegistroActivity.this, "No se pudo registrar usuario",
                            Toast.LENGTH_SHORT).show();

                }
            }
        });

    }
    public void irIngresar(View view){
        startActivity(new Intent(RegistroActivity.this, IniciarSesionActivity.class));
    }

}