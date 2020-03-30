package iamstmvasan.reviser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

public class Profile extends AppCompatActivity {
    Button submit,signout;
    TextView usr_name , usr_mail;
    ImageView usr_image;
    private GoogleSignInClient mGoogleSignInClient;
    RadioGroup rgCourse , rgType;
    RadioButton cbtn , tbtn;
    public static String courseSelection , typeSelection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        submit = findViewById(R.id.submit);

        usr_name = findViewById(R.id.name);
        usr_mail = findViewById(R.id.gmail);
        usr_image = findViewById(R.id.imageView);

        signout = findViewById(R.id.signout);
        rgCourse = findViewById(R.id.rgCourse);
        rgType = findViewById(R.id.rgType);

        //Intent i = getIntent();
        //String name = i.getStringExtra("UserName");
        usr_name.setText(login.personName);
        usr_mail.setText(login.personEmail);
        usr_image.setImageURI(login.personPhoto);

        String imageUrl = login.personPhoto.toString();

        //Loading image using Picasso
        Picasso.get().load(imageUrl).into(usr_image);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int courseCheckedId = rgCourse.getCheckedRadioButtonId();
                int typeCheckedId = rgType.getCheckedRadioButtonId();
                if(courseCheckedId == -1 || typeCheckedId == -1){
                    Toast.makeText(Profile.this, "Invalid selection...", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(Profile.this, "valid selection...", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(Profile.this , exam.class);
                    //i.putExtra("courseType" , courseSelection+" "+typeSelection);
                    startActivity(i);
                }
            }
        });
        rgCourse.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                cbtn = findViewById(checkedId);
                courseSelection = cbtn.getText().toString();
                Toast.makeText(Profile.this, cbtn.getText().toString() , Toast.LENGTH_SHORT).show();
            }
        });
        rgType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                tbtn = findViewById(checkedId);
                typeSelection = tbtn.getText().toString();
                Toast.makeText(Profile.this, tbtn.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });




    }
    public void signOut (final View view) {
        FirebaseAuth.getInstance().signOut();

        GoogleSignIn.getClient(this,new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build())
                .signOut().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                startActivity(new Intent(view.getContext(),login.class));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Profile.this, "Signout Failed.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
