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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class IniciarSesionActivity extends AppCompatActivity {

    private EditText etCorreo, etContra;
    private Button bIngresar;

    //variables para iniciar sesion
    private String correo = "";
    private String contra = "";

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);

        mAuth = FirebaseAuth.getInstance();

        etCorreo = (EditText)findViewById(R.id.correo);
        etContra = (EditText)findViewById(R.id.contrasena);
        bIngresar = (Button)findViewById(R.id.btnIngresar);

        bIngresar.setOnClickListener((v)->{
            correo = etCorreo.getText().toString();
            contra = etContra.getText().toString();

            if(!correo.isEmpty() && !contra.isEmpty()){
                iniciarSesion();
            }else{
                Toast.makeText(IniciarSesionActivity.this, "Complete los campos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void iniciarSesion(){

       mAuth.signInWithEmailAndPassword(correo, contra).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    FirebaseUser usuario = mAuth.getCurrentUser();
                    Toast.makeText(IniciarSesionActivity.this, "Ingreso existoso", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(IniciarSesionActivity.this, InicioActivity.class));
                }else{
                    Toast.makeText(IniciarSesionActivity.this, "Usuario no encontrado", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void irRegistro(View view){
        startActivity(new Intent(IniciarSesionActivity.this, RegistroActivity.class));
    }
}