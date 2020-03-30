package iamstmvasan.reviser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    ProgressBar pb;
    CountDownTimer cd;

    int p = 25;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pb = findViewById(R.id.progressBar);
        pb.setVisibility(View.VISIBLE);

        //pb.setBackgroundColor(Color.WHITE);
        getStart();

    }

    private void getStart() {

        cd = new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                /*p+=25;
                pb.setProgress(p);*/
                /*Toast.makeText(MainActivity.this, "hiii", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, login.class));*/

            }

            @Override
            public void onFinish() {
                //Toast.makeText(MainActivity.this, "hello", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, login.class));

            }
        }.start();
    }

}
