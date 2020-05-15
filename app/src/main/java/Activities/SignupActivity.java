package Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hbb20.CountryCodePicker;
import com.infusibleCoder.grocerystorefyp.R;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {

    private EditText edt_username,edt_password,edt_email,edt_phone;
    private Button btn_login,btn_signup,btn_googleSignup;
    private CountryCodePicker ccp;
    

    private GoogleSignInClient mGoogleSignInClient;
    private int RC_SIGN_IN = 7;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference("Users");
        
        ccp = (CountryCodePicker) findViewById(R.id.ccpicker);
        edt_email = findViewById(R.id.edt_email);
        edt_username = findViewById(R.id.edt_username);
        edt_password = findViewById(R.id.edt_password);
        edt_phone = findViewById(R.id.edt_phone);
        btn_signup = findViewById(R.id.btn_signup);

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signUpWithEmail();
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
       // updateUI(currentUser);
    }

    public void signUpWithEmail(){

        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this);
        progressDialog.setTitle("Signing up...");
        String phone = ccp.getSelectedCountryCode() + " "+ edt_phone.getText().toString();

        if(!TextUtils.isEmpty(edt_email.getText().toString()) && !TextUtils.isEmpty(edt_password.getText().toString())
        && !TextUtils.isEmpty(edt_username.getText().toString()) && !TextUtils.isEmpty(edt_phone.getText().toString())){

            progressDialog.show();
            mAuth.createUserWithEmailAndPassword(edt_email.getText().toString(),edt_password.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                       
                            if(task.isSuccessful()){

                                Map<String, String> map = new HashMap<>();
                                map.put("user_name", edt_username.getText().toString());
                                map.put("user_email", edt_email.getText().toString());
                                map.put("user_password",edt_password.getText().toString());
                                map.put("phone_number",edt_phone.getText().toString());
                                
                                mRef.child(mAuth.getCurrentUser().getUid()).setValue(map).addOnSuccessListener(
                                        new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {

                                                progressDialog.dismiss();
                                                Toast.makeText(SignupActivity.this, "Account Created Successfully!", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(SignupActivity.this, HomeScreen.class));
                                                finish();
                                            }
                                        }
                                );
                            }else{

                                progressDialog.dismiss();
                                Toast.makeText(SignupActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            
        }else{

            Toast.makeText(this, "Fields must not be empty!", Toast.LENGTH_SHORT).show();
        }
    }
}
