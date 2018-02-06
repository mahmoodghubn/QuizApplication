package com.example.android.quizapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    int index2;
    private String editAnswer;
    private int counter = 0;
    private EditText nameView;
    private String name;
    private Questions questionsObject = new Questions();
    private TextView radioQuestionsView;
    private TextView checkBoxQuestionsView;
    private RadioButton R1;
    private RadioButton R2;
    private RadioButton R3;
    private RadioButton R4;
    private CheckBox C1;
    private CheckBox C2;
    private CheckBox C3;
    private CheckBox C4;
    private EditText editAnswerView;
    private boolean[] checkBoxAnswers = new boolean[4];
    private class Questions {
        String[] question = {"Which of the following men does not have a chemical element named for him?"
                , "Which of these U.S. presidents appeared on the television series Laugh-In?"
                , "Who did artist Grant Wood use as the model for the farmer in his classic painting \"American Gothic\"?"
                , "Khrushchev's famous 1960 \"shoe-banging\" outburst at the U.N. was in response to a delegate from what nation?"
                , "Who won the FIFA Player of the Year award five times "
                , "How many arms does an octopus have"};
        String[] first = {"Albert Einstein", "Lyndon Johnson", "Traveling salesman", "Australia", "Lionel Messi"};
        String[] second = {"Issac Newton", "Jimmy Carter", "His dentist", "Turkey", "Cristiano Ronaldo"};
        String[] third = {"Niels Bohr", "Richard Nixon", "Local sheriff", "The Netherlands", "Diego Armando Maradona"};
        String[] fourth = {"Enrico Fermi", "Gerald Ford", "His butcher", "The Philippines", "Pele"};
        int[] answers = {2, 3, 2, 4};
    }

    private class UserRadioAnswers {
        boolean[] isAnswered = {false, false, false, false};
        int[] playerAnswers = new int[4];

        void setAnswer(int answer, int index) {
            playerAnswers[index] = answer;
            isAnswered[index] = true;
        }
    }
    UserRadioAnswers userRadioAnswers = new UserRadioAnswers();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("counter", counter);
        if (counter == 0) {
            EditText nameText = (EditText) findViewById(R.id.editText1);
            name = nameText.getText().toString();
            savedInstanceState.putString("name", name);
            
        }
        if (counter != 6)
            savedInstanceState.putString("editAnswer", editAnswer);
        else
            savedInstanceState.putString("editAnswer", editAnswerView.getText().toString());

        savedInstanceState.putBooleanArray("isAnswered", userRadioAnswers.isAnswered);
        savedInstanceState.putIntArray("playerAnswers", userRadioAnswers.playerAnswers);
        savedInstanceState.putBooleanArray("checkBoxAnswers", checkBoxAnswers);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            counter = savedInstanceState.getInt("counter");
            editAnswer = savedInstanceState.getString("editAnswer");
            userRadioAnswers.isAnswered = savedInstanceState.getBooleanArray("isAnswered");
            userRadioAnswers.playerAnswers = savedInstanceState.getIntArray("playerAnswers");
            checkBoxAnswers = savedInstanceState.getBooleanArray("checkBoxAnswers");
            name = savedInstanceState.getString("name");


        }
        if (counter == 0) {
            setContentView(R.layout.activity_main);
            EditText nameText = (EditText) findViewById(R.id.editText1);

            nameText.setText(name);
        } else
            next();
    }

    /**
     * This method is called when the start button is clicked.
     */
    public void start(View v) {
        // Get user's name
        nameView = (EditText) findViewById(R.id.editText1);
        name = nameView.getText().toString();
        if (name.isEmpty()) {
            Toast.makeText(getBaseContext(), "inter your name", Toast.LENGTH_LONG).show();
        } else {
            //start the quiz display the first question
            setContentView(R.layout.start);
            radioQuestionsView = (TextView) findViewById(R.id.radioQuestion);
            R1 = (RadioButton) findViewById(R.id.r1);
            R2 = (RadioButton) findViewById(R.id.r2);
            R3 = (RadioButton) findViewById(R.id.r3);
            R4 = (RadioButton) findViewById(R.id.r4);
            radioQuestionsView.setText(questionsObject.question[0]);
            R1.setText(questionsObject.first[0]);
            R2.setText(questionsObject.second[0]);
            R3.setText(questionsObject.third[0]);
            R4.setText(questionsObject.fourth[0]);
            counter++;
            Button prevButton = (Button) findViewById(R.id.radioprev);
            prevButton.setEnabled(false);
        }
    }

    /**
     * This method is called when the the user check RadioButton.
     */
    public void saveAnswer(View v) {
        //save the user answers for radiobutton
        index2 = counter - 1;
        userRadioAnswers.isAnswered[index2] = true;
        if (R1.isChecked() == true) {
            userRadioAnswers.setAnswer(1, index2);
        } else if (R2.isChecked() == true) {
            userRadioAnswers.setAnswer(2, index2);
        } else if (R3.isChecked() == true) {
            userRadioAnswers.setAnswer(3, index2);
        } else if (R4.isChecked() == true) {
            userRadioAnswers.setAnswer(4, index2);
        }
    }

    /**
     * This method is called when the the user check CheckBbutton.
     */
    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch (view.getId()) {
            case R.id.CheckBox1:
                if (checked)
                    //save the user answer for checkbox question
                    checkBoxAnswers[0] = true;
                else
                    checkBoxAnswers[0] = false;
                break;
            case R.id.CheckBox2:
                if (checked)
                    checkBoxAnswers[1] = true;
                else
                    checkBoxAnswers[1] = false;
                break;
            case R.id.CheckBox3:
                if (checked)
                    checkBoxAnswers[2] = true;
                else
                    checkBoxAnswers[2] = false;
                break;
            case R.id.CheckBox4:
                if (checked)
                    checkBoxAnswers[3] = true;
                else
                    checkBoxAnswers[3] = false;
                break;
        }
    }

    /**
     * This method is called when swap to landscape mode or return to the original mode
     */
    private void next() {
        //display radio button questions
        if (counter <= 4) {
            setContentView(R.layout.start);
            //display the radiobutton question
            radioQuestionsView = (TextView) findViewById(R.id.radioQuestion);
            R1 = (RadioButton) findViewById(R.id.r1);
            R2 = (RadioButton) findViewById(R.id.r2);
            R3 = (RadioButton) findViewById(R.id.r3);
            R4 = (RadioButton) findViewById(R.id.r4);
            index2 = counter - 1;
            radioQuestionsView.setText(questionsObject.question[index2]);
            R1.setText(questionsObject.first[index2]);
            R2.setText(questionsObject.second[index2]);
            R3.setText(questionsObject.third[index2]);
            R4.setText(questionsObject.fourth[index2]);

            //display the answers that user chose before
            if (userRadioAnswers.isAnswered[index2]) {
                int index = userRadioAnswers.playerAnswers[index2];
                switch (index) {
                    case 1:
                        R1.setChecked(true);
                        break;
                    case 2:
                        R2.setChecked(true);
                        break;
                    case 3:
                        R3.setChecked(true);
                        break;
                    case 4:
                        R4.setChecked(true);
                        break;
                }
            }
            if (counter == 1) {//the first question
                Button prevButton = (Button) findViewById(R.id.radioprev);
                prevButton.setEnabled(false);
            }
        } else if (counter == 5) {
            //display checkbox question
            setContentView(R.layout.checkboxquestion);
            checkBoxQuestionsView = (TextView) findViewById(R.id.checkBoxQuestionTextView);
            C1 = (CheckBox) findViewById(R.id.CheckBox1);
            C2 = (CheckBox) findViewById(R.id.CheckBox2);
            C3 = (CheckBox) findViewById(R.id.CheckBox3);
            C4 = (CheckBox) findViewById(R.id.CheckBox4);
            checkBoxQuestionsView.setText(questionsObject.question[4]);
            C1.setText(questionsObject.first[4]);
            C2.setText(questionsObject.second[4]);
            C3.setText(questionsObject.third[4]);
            C4.setText(questionsObject.fourth[4]);
            if (checkBoxAnswers[0]) {
                C1.setChecked(true);
            }
            if (checkBoxAnswers[1]) {
                C2.setChecked(true);
            }
            if (checkBoxAnswers[2]) {
                C3.setChecked(true);
            }
            if (checkBoxAnswers[3]) {
                C4.setChecked(true);
            }

        } else if (counter == 6) {
            //display the edit question
            setContentView(R.layout.editquestion);
            editAnswerView = (EditText) findViewById(R.id.editText2);
            editAnswerView.setText(editAnswer);
            TextView editQuestion = (TextView) findViewById(R.id.editQuestion);
            editQuestion.setText(questionsObject.question[5]);

        } else {
            //display the result
            setContentView(R.layout.editquestion);
            editAnswerView = (EditText) findViewById(R.id.editText2);
            editAnswerView.setText(editAnswer);
            TextView editQuestion = (TextView) findViewById(R.id.editQuestion);
            editQuestion.setText(questionsObject.question[5]);
            Button prevButton = (Button) findViewById(R.id.prev);
            prevButton.setEnabled(false);
            Button finishButton = (Button) findViewById(R.id.finish);
            finishButton.setEnabled(false);
            EditText answerEditText = (EditText) findViewById(R.id.editText2);
            answerEditText.setEnabled(false);
            Button shareButton = (Button) findViewById(R.id.share);
            shareButton.setEnabled(true);
        }
    }
    /**
     * This method is called when the next button or the finish button is clicked.
     */
    public void next(View v) {
        //display radio button questions
        if (counter < 4) {
            setContentView(R.layout.start);
            //display the radiobutton question
            questionsObject = new Questions();
            radioQuestionsView = (TextView) findViewById(R.id.radioQuestion);
            R1 = (RadioButton) findViewById(R.id.r1);
            R2 = (RadioButton) findViewById(R.id.r2);
            R3 = (RadioButton) findViewById(R.id.r3);
            R4 = (RadioButton) findViewById(R.id.r4);

            radioQuestionsView.setText(questionsObject.question[counter]);
            R1.setText(questionsObject.first[counter]);
            R2.setText(questionsObject.second[counter]);
            R3.setText(questionsObject.third[counter]);
            R4.setText(questionsObject.fourth[counter]);
            counter++;

            //display the answers that user chose before
            if (userRadioAnswers.isAnswered[counter - 1]) {
                int index = userRadioAnswers.playerAnswers[counter - 1];
                switch (index) {
                    case 1:
                        R1.setChecked(true);
                        break;
                    case 2:
                        R2.setChecked(true);
                        break;
                    case 3:
                        R3.setChecked(true);
                        break;
                    case 4:
                        R4.setChecked(true);
                        break;
                }
            }
        } else if (counter == 4) {
            //display checkbox question
            counter++;
            setContentView(R.layout.checkboxquestion);
            checkBoxQuestionsView = (TextView) findViewById(R.id.checkBoxQuestionTextView);
            C1 = (CheckBox) findViewById(R.id.CheckBox1);
            C2 = (CheckBox) findViewById(R.id.CheckBox2);
            C3 = (CheckBox) findViewById(R.id.CheckBox3);
            C4 = (CheckBox) findViewById(R.id.CheckBox4);
            checkBoxQuestionsView.setText(questionsObject.question[4]);
            C1.setText(questionsObject.first[4]);
            C2.setText(questionsObject.second[4]);
            C3.setText(questionsObject.third[4]);
            C4.setText(questionsObject.fourth[4]);
            //display the answer the user has checked before
            if (checkBoxAnswers[0]) {
                C1.setChecked(true);
            }
            if (checkBoxAnswers[1]) {
                C2.setChecked(true);
            }
            if (checkBoxAnswers[2]) {
                C3.setChecked(true);
            }
            if (checkBoxAnswers[3]) {
                C4.setChecked(true);
            }

        } else if (counter == 5) {
            //display the edit question

            counter++;
            setContentView(R.layout.editquestion);
            editAnswerView = (EditText) findViewById(R.id.editText2);
            editAnswerView.setText(editAnswer);
            TextView editQuestion = (TextView) findViewById(R.id.editQuestion);
            editQuestion.setText(questionsObject.question[5]);


        } else {
            counter++;

            //display the result
            Toast.makeText(getBaseContext(), getString(R.string.result_message)+ calculateReult(), Toast.LENGTH_LONG).show();
            Button share = (Button)findViewById(R.id.share);
            share.setEnabled(true);

        }
    }
    public void share(View v){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, "My result is "+ calculateReult());
        startActivity(Intent.createChooser(intent, "Dialog title text"));

    }


    /**
     * This method is called by the next(View) function when the finish button is clicked
     */
    private int calculateReult() {
        int result = 0;
        for (int i = 0; i < 4; i++) {
            //if it is the right answer
            if (userRadioAnswers.playerAnswers[i] == questionsObject.answers[i]) {
                result++;
            }
        }
        if (checkBoxAnswers[0] && checkBoxAnswers[1] && !checkBoxAnswers[2] && !checkBoxAnswers[3])
            result++;
        EditText ed = (EditText) findViewById(R.id.editText2);
        String S = ed.getText().toString();
        //the right answer is 8
        if (S.contentEquals("8"))
            result++;
        Button prevButton = (Button) findViewById(R.id.prev);
        prevButton.setEnabled(false);
        Button finishButton = (Button) findViewById(R.id.finish);
        finishButton.setEnabled(false);
        EditText answerEditText = (EditText) findViewById(R.id.editText2);
        answerEditText.setEnabled(false);
        return result;

    }

    /**
     * This method is called when the prev button is clicked.
     */
    public void prev(View v) {
        if (counter == 6) {
            //save the answer for the edit text question
            editAnswerView = (EditText) findViewById(R.id.editText2);
            editAnswer = editAnswerView.getText().toString();
            counter--;
            //display the check box question
            setContentView(R.layout.checkboxquestion);
            checkBoxQuestionsView = (TextView) findViewById(R.id.checkBoxQuestionTextView);
            C1 = (CheckBox) findViewById(R.id.CheckBox1);
            C2 = (CheckBox) findViewById(R.id.CheckBox2);
            C3 = (CheckBox) findViewById(R.id.CheckBox3);
            C4 = (CheckBox) findViewById(R.id.CheckBox4);
            checkBoxQuestionsView.setText(questionsObject.question[4]);
            C1.setText(questionsObject.first[4]);
            C2.setText(questionsObject.second[4]);
            C3.setText(questionsObject.third[4]);
            C4.setText(questionsObject.fourth[4]);
            //display the answers
            if (checkBoxAnswers[0]) {
                C1.setChecked(true);
            }
            if (checkBoxAnswers[1]) {
                C2.setChecked(true);
            }
            if (checkBoxAnswers[2]) {
                C3.setChecked(true);
            }
            if (checkBoxAnswers[3]) {
                C4.setChecked(true);
            }
        } else if (counter <= 5) {
            setContentView(R.layout.start);
            //display the radion button question
            questionsObject = new Questions();
            radioQuestionsView = (TextView) findViewById(R.id.radioQuestion);
            R1 = (RadioButton) findViewById(R.id.r1);
            R2 = (RadioButton) findViewById(R.id.r2);
            R3 = (RadioButton) findViewById(R.id.r3);
            R4 = (RadioButton) findViewById(R.id.r4);
            counter--;
            index2 = counter - 1;
            radioQuestionsView.setText(questionsObject.question[index2]);
            R1.setText(questionsObject.first[index2]);
            R2.setText(questionsObject.second[index2]);
            R3.setText(questionsObject.third[index2]);
            R4.setText(questionsObject.fourth[index2]);

            //display the answers that user chose before
            if (userRadioAnswers.isAnswered[index2]) {
                int index = userRadioAnswers.playerAnswers[index2];
                switch (index) {
                    case 1:
                        R1.setChecked(true);
                        break;
                    case 2:
                        R2.setChecked(true);
                        break;
                    case 3:
                        R3.setChecked(true);
                        break;
                    case 4:
                        R4.setChecked(true);
                        break;
                }
            }
            if (counter == 1) {//the first question
                Button prevButton = (Button) findViewById(R.id.radioprev);
                prevButton.setEnabled(false);
            }
        }
    }
}

