package iamstmvasan.reviser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class exam extends AppCompatActivity {
    TextView timer, question_txt;
    TextView uname , umail;
    ImageView uimage;
    TextView courseName, courseType;
    Button option1, option2, option3, option4, stop;
    String course_type = Profile.courseSelection+Profile.typeSelection;

    int total = 0;
    int correct = 0;
    int incorrect = 0;
    int computercount = 0;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);

        timer = findViewById(R.id.timer);
        question_txt = findViewById(R.id.question_txt);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);
        stop = findViewById(R.id.stop);
        courseName = findViewById(R.id.coursename);
        courseType = findViewById(R.id.coursetype);

        uname = findViewById(R.id.name7);
        umail = findViewById(R.id.gmail2);
        uimage = findViewById(R.id.imageView2);

        uname.setText(login.personName);
        umail.setText(login.personEmail);
        String imageUrl = login.personPhoto.toString();

        //Loading image using Picasso
        Picasso.get().load(imageUrl).into(uimage);


        courseName.setText(Profile.courseSelection);
        courseType.setText(Profile.typeSelection);

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(exam.this, result.class);
                i.putExtra("total", String.valueOf(total));
                i.putExtra("correct", String.valueOf(correct));
                i.putExtra("incorrect", String.valueOf(incorrect));
                startActivity(i);
            }
        });

        updateQuestion();
        reverseTimer(120,timer);
    }

    private void updateQuestion() {

        try {

            if (computercount > 4) {
                Toast.makeText(getApplicationContext(), "Game Over", Toast.LENGTH_SHORT).show();
                Intent myIntent = new Intent(exam.this, result.class);
                myIntent.putExtra("total", String.valueOf(total));
                myIntent.putExtra("correct", String.valueOf(correct));
                myIntent.putExtra("incorrect", String.valueOf(incorrect));
                startActivity(myIntent);
            } else {
                //databaseReference = FirebaseDatabase.getInstance().getReference().child("Questions").child(String.valueOf(computercount));
                if (course_type.equalsIgnoreCase("CEasy")) {
                    databaseReference = FirebaseDatabase.getInstance().getReference().child("CEasy").child("CEasy").child(String.valueOf(computercount));
                } else if (course_type.equalsIgnoreCase("CMedium")) {
                    databaseReference = FirebaseDatabase.getInstance().getReference().child("CMedium").child("CMedium").child(String.valueOf(computercount));
                } else if (course_type.equalsIgnoreCase("CHard")) {
                    databaseReference = FirebaseDatabase.getInstance().getReference().child("CHard").child("CHard").child(String.valueOf(computercount));
                } else if (course_type.equalsIgnoreCase("JavaEasy")) {
                    databaseReference = FirebaseDatabase.getInstance().getReference().child("JavaEasy").child("JavaEasy").child(String.valueOf(computercount));
                } else if (course_type.equalsIgnoreCase("JavaMedium")) {
                    databaseReference = FirebaseDatabase.getInstance().getReference().child("JavaMedium").child("JavaMedium").child(String.valueOf(computercount));
                } else if (course_type.equalsIgnoreCase("JavaHard")) {
                    databaseReference = FirebaseDatabase.getInstance().getReference().child("JavaHard").child("JavaHard").child(String.valueOf(computercount));
                } else if (course_type.equalsIgnoreCase("PythonEasy")) {
                    databaseReference = FirebaseDatabase.getInstance().getReference().child("PythonEasy").child("PythonEasy").child(String.valueOf(computercount));
                } else if (course_type.equalsIgnoreCase("PythonMedium")) {
                    databaseReference = FirebaseDatabase.getInstance().getReference().child("PythonMedium").child("PythonMedium").child(String.valueOf(computercount));
                } else if (course_type.equalsIgnoreCase("PythonHard")) {
                    databaseReference = FirebaseDatabase.getInstance().getReference().child("PythonHard").child("PythonHard").child(String.valueOf(computercount));
                }


                computercount++;
                total++;

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        final Question question = dataSnapshot.getValue(Question.class);
                        question_txt.setText(question.getQuestion());
                        option1.setText(question.getOption1());
                        option2.setText(question.getOption2());
                        option3.setText(question.getOption3());
                        option4.setText(question.getOption4());

                        option1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (option1.getText().toString().equalsIgnoreCase(question.getAnswer())) {
                                    Toast.makeText(getApplicationContext(), "Correct answer", Toast.LENGTH_SHORT).show();
                                    option1.setBackgroundResource(R.drawable.correctbtn);
                                    correct = correct + 1;
                                    Handler handler = new Handler();

                                    handler.postDelayed(new Runnable() {
                                        public void run() {
                                            option1.setBackgroundResource(R.drawable.custom_btn);
                                            updateQuestion();
                                        }
                                    }, 500);

                                } else {
                                    Toast.makeText(getApplicationContext(), "Incorrect", Toast.LENGTH_SHORT).show();
                                    incorrect = incorrect + 1;

                                    option1.setBackgroundResource(R.drawable.incorrectbtn);

                                    if (option2.getText().toString().equalsIgnoreCase(question.getAnswer())) {
                                        option2.setBackgroundResource(R.drawable.correctbtn);
                                    } else if (option3.getText().toString().equalsIgnoreCase(question.getAnswer())) {
                                        option3.setBackgroundResource(R.drawable.correctbtn);
                                    } else if (option4.getText().toString().equalsIgnoreCase(question.getAnswer())) {
                                        option4.setBackgroundResource(R.drawable.correctbtn);
                                    }


                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        public void run() {
                                            option1.setBackgroundResource(R.drawable.custom_btn);
                                            option2.setBackgroundResource(R.drawable.custom_btn);
                                            option3.setBackgroundResource(R.drawable.custom_btn);
                                            option4.setBackgroundResource(R.drawable.custom_btn);
                                            updateQuestion();
                                        }
                                    }, 500);


                                }
                            }
                        });

                        option2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (option2.getText().toString().equalsIgnoreCase(question.getAnswer())) {
                                    Toast.makeText(getApplicationContext(), "Correct answer", Toast.LENGTH_SHORT).show();
                                    option2.setBackgroundResource(R.drawable.correctbtn);
                                    correct = correct + 1;
                                    Handler handler = new Handler();

                                    handler.postDelayed(new Runnable() {
                                        public void run() {
                                            option2.setBackgroundResource(R.drawable.custom_btn);
                                            updateQuestion();
                                        }
                                    }, 500);

                                } else {
                                    Toast.makeText(getApplicationContext(), "Incorrect", Toast.LENGTH_SHORT).show();
                                    incorrect = incorrect + 1;

                                    option2.setBackgroundResource(R.drawable.incorrectbtn);

                                    if (option1.getText().toString().equalsIgnoreCase(question.getAnswer())) {
                                        option1.setBackgroundResource(R.drawable.correctbtn);
                                    } else if (option3.getText().toString().equalsIgnoreCase(question.getAnswer())) {
                                        option3.setBackgroundResource(R.drawable.correctbtn);
                                    } else if (option4.getText().toString().equalsIgnoreCase(question.getAnswer())) {
                                        option4.setBackgroundResource(R.drawable.correctbtn);
                                    }


                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        public void run() {
                                            option1.setBackgroundResource(R.drawable.custom_btn);
                                            option2.setBackgroundResource(R.drawable.custom_btn);
                                            option3.setBackgroundResource(R.drawable.custom_btn);
                                            option4.setBackgroundResource(R.drawable.custom_btn);
                                            updateQuestion();
                                        }
                                    }, 500);


                                }
                            }
                        });

                        option3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (option3.getText().toString().equalsIgnoreCase(question.getAnswer())) {
                                    Toast.makeText(getApplicationContext(), "Correct answer", Toast.LENGTH_SHORT).show();
                                    option3.setBackgroundResource(R.drawable.correctbtn);
                                    correct = correct + 1;
                                    Handler handler = new Handler();

                                    handler.postDelayed(new Runnable() {
                                        public void run() {
                                            option3.setBackgroundResource(R.drawable.custom_btn);
                                            updateQuestion();
                                        }
                                    }, 500);

                                } else {
                                    Toast.makeText(getApplicationContext(), "Incorrect", Toast.LENGTH_SHORT).show();
                                    incorrect = incorrect + 1;

                                    option3.setBackgroundResource(R.drawable.incorrectbtn);

                                    if (option2.getText().toString().equalsIgnoreCase(question.getAnswer())) {
                                        option2.setBackgroundResource(R.drawable.correctbtn);
                                    } else if (option1.getText().toString().equalsIgnoreCase(question.getAnswer())) {
                                        option1.setBackgroundResource(R.drawable.correctbtn);
                                    } else if (option4.getText().toString().equalsIgnoreCase(question.getAnswer())) {
                                        option4.setBackgroundResource(R.drawable.correctbtn);
                                    }


                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        public void run() {
                                            option1.setBackgroundResource(R.drawable.custom_btn);
                                            option2.setBackgroundResource(R.drawable.custom_btn);
                                            option3.setBackgroundResource(R.drawable.custom_btn);
                                            option4.setBackgroundResource(R.drawable.custom_btn);
                                            updateQuestion();
                                        }
                                    }, 500);


                                }
                            }
                        });

                        option4.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (option4.getText().toString().equalsIgnoreCase(question.getAnswer())) {
                                    Toast.makeText(getApplicationContext(), "Correct answer", Toast.LENGTH_SHORT).show();
                                    option1.setBackgroundResource(R.drawable.correctbtn);
                                    correct = correct + 1;
                                    Handler handler = new Handler();

                                    handler.postDelayed(new Runnable() {
                                        public void run() {
                                            option1.setBackgroundResource(R.drawable.custom_btn);
                                            updateQuestion();
                                        }
                                    }, 500);

                                } else {
                                    Toast.makeText(getApplicationContext(), "Incorrect", Toast.LENGTH_SHORT).show();
                                    incorrect = incorrect + 1;

                                    option4.setBackgroundResource(R.drawable.incorrectbtn);

                                    if (option2.getText().toString().equalsIgnoreCase(question.getAnswer())) {
                                        option2.setBackgroundResource(R.drawable.correctbtn);
                                    } else if (option3.getText().toString().equalsIgnoreCase(question.getAnswer())) {
                                        option3.setBackgroundResource(R.drawable.correctbtn);
                                    } else if (option1.getText().toString().equalsIgnoreCase(question.getAnswer())) {
                                        option1.setBackgroundResource(R.drawable.correctbtn);
                                    }


                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        public void run() {
                                            option1.setBackgroundResource(R.drawable.custom_btn);
                                            option2.setBackgroundResource(R.drawable.custom_btn);
                                            option3.setBackgroundResource(R.drawable.custom_btn);
                                            option4.setBackgroundResource(R.drawable.custom_btn);
                                            updateQuestion();
                                        }
                                    }, 500);


                                }
                            }
                        });


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }

        }catch (Exception e)
        {
            Toast.makeText(this, "Something is wrong...!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(exam.this , Profile.class));
        }
    }
    public void reverseTimer(int Seconds,final TextView tv){

        new CountDownTimer(Seconds* 1000+1000, 1000) {

            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000);
                int minutes = seconds / 60;
                seconds = seconds % 60;
                tv.setText(String.format("%02d", minutes)
                        + ":" + String.format("%02d", seconds));
            }

            public void onFinish() {
                tv.setText("Completed");
                Intent myIntent = new Intent(exam.this,result.class);
                myIntent.putExtra("total",String.valueOf(total));
                myIntent.putExtra("correct",String.valueOf(correct));
                myIntent.putExtra("incorrect",String.valueOf(incorrect));
                startActivity(myIntent);
            }
        }.start();
    }

}
