package com.example.braintrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class SecondActivity extends AppCompatActivity {
    // Views & Tags
    public static final String TAG = "Activity :";
    ArrayList<Integer> answers = new ArrayList<Integer>();
    int locationOfCorrectAnswer;
    int score = 0;
    int totalNumberOfQuestions = 0;
    int defaultTimer = 30;
    Button playAgain;
    TextView toastTextView,timerTextView,scoreTextView;
    TextView sumTextView;
    Button grid0,grid1,grid2,grid3;
    Random rand = new Random();
    CountDownTimer countDownTimer;

    // Methods
    protected void setTimerTextView(int progress){
        String time;
        if(progress>9) time = Integer.toString(progress) + "s";
        else time = "0" + Integer.toString(progress) +"s";
        timerTextView.setText(time);
    }
    protected void resetGrid(){
        int a = rand.nextInt(21);
        int b = rand.nextInt(21);
        String sumText = Integer.toString(a)+" + "+Integer.toString(b);
        sumTextView.setText(sumText);
        locationOfCorrectAnswer = rand.nextInt(4);
        answers.clear();
        for(int i=0;i<4;i++){
            if(i==locationOfCorrectAnswer){
                answers.add(a+b);
            } else {
                int wrongAnswer = rand.nextInt(41);
                while(wrongAnswer == a+b) wrongAnswer = rand.nextInt();
                answers.add(rand.nextInt(41));
            }
        }
        grid0.setText(Integer.toString(answers.get(0)));
        grid1.setText(Integer.toString(answers.get(1)));
        grid2.setText(Integer.toString(answers.get(2)));
        grid3.setText(Integer.toString(answers.get(3)));
    }
    public void checkSum(View view) { // Answer Buttons
        String ans;
        if(Integer.toString(locationOfCorrectAnswer).equals(view.getTag().toString())){
            ans = "Correct";
            score++;
        } else {
            ans = "Wrong :(";
        }
        totalNumberOfQuestions++;
        toastTextView.setText(ans);
        toastTextView.setVisibility(View.VISIBLE);
        String scoreView = Integer.toString(score) +"/"+Integer.toString(totalNumberOfQuestions);
        scoreTextView.setText(scoreView);
        resetGrid();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        playAgain = (Button) findViewById(R.id.playAgain);
        toastTextView = (TextView) findViewById(R.id.toastTextView);
        timerTextView = (TextView) findViewById(R.id.timerTextView);
        sumTextView = (TextView) findViewById(R.id.sumTextView);
        scoreTextView = (TextView) findViewById(R.id.scoreTextView);
        final GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);
        grid0 = (Button) findViewById(R.id.button0);
        grid1 = (Button) findViewById(R.id.button1);
        grid2 = (Button) findViewById(R.id.button2);
        grid3 = (Button) findViewById(R.id.button3);

        // Application code
        setTimerTextView(30);
        resetGrid();
        countDownTimer = new CountDownTimer(30100,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                setTimerTextView((int) millisUntilFinished/1000);
            }

            @Override
            public void onFinish() {
                playAgain.setVisibility(View.VISIBLE);
                if(toastTextView.getVisibility() != View.VISIBLE) toastTextView.setVisibility(View.VISIBLE);
                toastTextView.setText("Done!");
            }
        }.start();
        playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG,"Button Pressed");
                setTimerTextView(defaultTimer);
                score = 0;
                totalNumberOfQuestions = 0;
                scoreTextView.setText("0/0");
                playAgain.setVisibility(View.INVISIBLE);
                resetGrid();
                countDownTimer.start();
            }
        });


    }
}