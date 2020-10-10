package com.skc.braintrainer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    ConstraintLayout firstScreen, secondScreen;
    TextView titleTextView, tapTextView, timeTextView, questionTextView, scoreTextView;
    ImageView firstImageView, brainImageView;
    Button playAgainButton, firstButton, secondButton, thirdButton, fourthButton;
    Random random = new Random();
    int score = 0, questions = 0, result = 0;

    public void timer() {
        new CountDownTimer(30000 + 200, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                timeTextView.setText(String.valueOf((int)(millisUntilFinished/1000)));
            }

            @Override
            public void onFinish() {
                questionTextView.setText("");
                firstButton.setText("");
                secondButton.setText("");
                thirdButton.setText("");
                fourthButton.setText("");
                firstButton.setEnabled(false);
                secondButton.setEnabled(false);
                thirdButton.setEnabled(false);
                fourthButton.setEnabled(false);
                brainImageView.setImageDrawable(null);
                playAgainButton.setVisibility(View.VISIBLE);
            }
        }.start();
    }

    public void getResult() {
        int choice = random.nextInt(4) + 1;

        switch (choice) {
            case 1: result = additionQuestion();
            break;
            case 2: result = subtractionQuestion();
            break;
            case 3: result = multiplicationQuestion();
            break;
            case 4: result = divisionQuestion();
            break;
        }

        List<Integer> options = new ArrayList<>(4);

        for(int i=0; i<4; ) {
            int temp = Math.abs(random.nextInt(40) + (result - 20));
            if(temp != 0 && temp != result && !options.contains(temp)) {
                options.add(temp);
                i++;
            }
        }

        options.set(random.nextInt(4), result);
        firstButton.setText(options.get(0).toString());
        secondButton.setText(options.get(1).toString());
        thirdButton.setText(options.get(2).toString());
        fourthButton.setText(options.get(3).toString());
    }

    public void startGame(View view) {
        titleTextView.setVisibility(View.INVISIBLE);
        tapTextView.setVisibility(View.INVISIBLE);
        firstImageView.setVisibility(View.INVISIBLE);
        secondScreen.setVisibility(View.VISIBLE);
        getResult();
        timer();
    }

    public void playAgain(View view) {
        score = 0;
        questions = 0;
        result = 0;
        timeTextView.setText("30");
        scoreTextView.setText("0/0");
        firstButton.setEnabled(true);
        secondButton.setEnabled(true);
        thirdButton.setEnabled(true);
        fourthButton.setEnabled(true);
        playAgainButton.setVisibility(View.INVISIBLE);
        brainImageView.setImageResource(R.drawable.secondhappy);
        getResult();
        timer();
    }

    public int additionQuestion() {
        int firstNum = random.nextInt(81) + 20;
        int secondNum = random.nextInt(81) + 20;
        int sum = firstNum + secondNum;
        questionTextView.setText(firstNum + " + " + secondNum);
        return sum;
    }

    public int subtractionQuestion() {
        int firstNum = random.nextInt(91) + 10;
        int secondNum = 10;

        for(int i=0; ; i++) {
            int temp = random.nextInt(91) + 10;
            if(temp <= firstNum) {
                secondNum = temp;
                break;
            }
        }

        int difference = firstNum - secondNum;
        questionTextView.setText(firstNum + " - " + secondNum);
        return difference;
    }

    public int multiplicationQuestion() {
        int firstNum = random.nextInt(20) + 1;
        int secondNum = random.nextInt(20) + 1;
        int product = firstNum * secondNum;
        questionTextView.setText(firstNum + " x " + secondNum);
        return product;
    }

    public int divisionQuestion() {
        int firstNum = random.nextInt(91) + 10;
        int secondNum = 10;

        for(int i=0; ; i++) {
            int temp = random.nextInt(91) + 10;
            if(firstNum % temp == 0) {
                secondNum = temp;
                break;
            }
        }

        int quotient = firstNum / secondNum;
        questionTextView.setText(firstNum + " / " + secondNum);
        return quotient;
    }

    public void buttonPressed(View view) {
        Button pressedButton = (Button) view;
        int answer = Integer.parseInt(pressedButton.getText().toString());

        if(answer == result) {
            brainImageView.setImageResource(R.drawable.secondhappy);
            score++;
        } else {
            brainImageView.setImageResource(R.drawable.secondsad);
        }

        questions++;
        scoreTextView.setText(score + "/" + questions);
        getResult();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstScreen = findViewById(R.id.firstScreen);
        secondScreen = findViewById(R.id.secondScreen);
        titleTextView = findViewById(R.id.titleTextView);
        tapTextView = findViewById(R.id.tapTextView);
        firstImageView = findViewById(R.id.firstImageView);
        timeTextView = findViewById(R.id.timeTextView);
        questionTextView = findViewById(R.id.questionTextView);
        scoreTextView = findViewById(R.id.scoreTextView);
        playAgainButton = findViewById(R.id.playAgainButton);
        firstButton = findViewById(R.id.firstButton);
        secondButton = findViewById(R.id.secondButton);
        thirdButton = findViewById(R.id.thirdButton);
        fourthButton = findViewById(R.id.fourthButton);
        brainImageView = findViewById(R.id.brainImageView);
    }
}