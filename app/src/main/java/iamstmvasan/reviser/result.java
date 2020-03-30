package iamstmvasan.reviser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class result extends AppCompatActivity {
    TextView course2 , type2;
    Button total , correct , incorrect , performance;
    Button reTest;

    TextView uname1 , umail1;
    ImageView uimage1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        total = findViewById(R.id.totalquestion);
        correct = findViewById(R.id.correct);
        incorrect = findViewById(R.id.incorrect);
        performance = findViewById(R.id.performance);
        reTest = findViewById(R.id.retest);

        uname1 = findViewById(R.id.name10);
        umail1 = findViewById(R.id.gmail3);
        uimage1 = findViewById(R.id.imageView3);

        uname1.setText(login.personName);
        umail1.setText(login.personEmail);

        String imageUrl = login.personPhoto.toString();

        //Loading image using Picasso
        Picasso.get().load(imageUrl).into(uimage1);

        course2 = findViewById(R.id.coursename2);
        type2 = findViewById(R.id.coursetype2);

        course2.setText(Profile.courseSelection);
        type2.setText(Profile.typeSelection);

        Intent i = getIntent();
        total.setText(i.getStringExtra("total"));
        correct.setText(i.getStringExtra("correct"));
        incorrect.setText(i.getStringExtra("incorrect"));
        int percent = Integer.valueOf(correct.getText().toString());
        performance.setText(String.valueOf(percent*20)+"%");


        reTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(result.this , Profile.class));
            }
        });

    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //startActivity(new Intent(this,Profile.class));
        Toast.makeText(this, "back", Toast.LENGTH_SHORT).show();
    }
}
