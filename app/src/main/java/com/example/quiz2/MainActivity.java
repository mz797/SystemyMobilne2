package com.example.quiz2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private Button trueButton;
    private Button falseButton;
    private Button nextButton;
    private Button hintButton;
    private TextView questionTestView;
    private int currentIndex=0;
    private final String QUIZ_TAG = "MainActivity";
    private static final String KEY_CURRENT_INDEX="currentIndex";
    public static final String KEY_EXTRA_ANSWER = "com.example.quiz2.correctAnswer";
    public static final int REQUEST_CODE_PROMPT =0;
    private boolean answerWasShown;
    private Question[] questions= new Question[]{
            new Question(R.string.q_warsaw, true),
            new Question(R.string.q_months,false),
            new Question(R.string.q_sun,true),
            new Question(R.string.q_china,false),
            new Question(R.string.q_eyes,true)
    };
    private void checkAnswerCorrectness(boolean userAnswer){
        boolean correctAnswer = questions[currentIndex].isTrueAnswer();
        int resultMessageId=0;
        if(answerWasShown){
            resultMessageId=R.string.answer_was_shown;
        }
        else{
            if(userAnswer==correctAnswer){
                resultMessageId=R.string.correct_answer;
            }  else{
                resultMessageId=R.string.incorrect_answer;
            }
        }
        Toast.makeText(this,resultMessageId, Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        Log.d(QUIZ_TAG,"Wywołana została metoda cyklu życia: onSaveInstanceState");
        outState.putInt(KEY_CURRENT_INDEX,currentIndex);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(QUIZ_TAG,"Wywołana została metoda cyklu życia onCreate");
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            currentIndex = savedInstanceState.getInt(KEY_CURRENT_INDEX);
        }
        hintButton=findViewById(R.id.hint_button);
        trueButton=findViewById(R.id.true_button);
        falseButton=findViewById(R.id.false_button);
        nextButton=findViewById(R.id.next_button);
        questionTestView=findViewById(R.id.question_text_view);

        hintButton.setOnClickListener((v)->{
            Intent intent = new Intent(MainActivity.this,PromptActivity.class);
            boolean correctAnswer=questions[currentIndex].isTrueAnswer();
            intent.putExtra(KEY_EXTRA_ANSWER,correctAnswer);

            //startActivity(intent);
            startActivityForResult(intent, REQUEST_CODE_PROMPT);
        });
        trueButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                checkAnswerCorrectness(true);
            }
        });
        falseButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                checkAnswerCorrectness(false);
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                currentIndex=(currentIndex +1)%questions.length;
                answerWasShown=false;
                setNextQuestion();
            }
        });
        setNextQuestion();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) { return; }
        if (requestCode == REQUEST_CODE_PROMPT) {
            if (data == null) { return; }
            answerWasShown = data.getBooleanExtra(PromptActivity.KEY_EXTRA_ANSWER_SHOWN, false);
        }
    }
    @Override
    protected void onStart(){
        super.onStart();
        Log.d(QUIZ_TAG,"Wywołanie onStart()");
    }
    @Override
    protected void onResume(){
        super.onResume();
        Log.d(QUIZ_TAG,"Wywołanie onResume()");
    }
    @Override
    protected void onPause(){
        super.onPause();
        Log.d(QUIZ_TAG,"Wywołanie onPause()");
    }
    @Override
    protected void onStop(){
        super.onStop();
        Log.d(QUIZ_TAG,"Wywołanie onStop()");
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.d(QUIZ_TAG,"Wywołanie onDestroy()");
    }

    private void setNextQuestion(){
        questionTestView.setText(questions[currentIndex].getQuestionId());
    }

}