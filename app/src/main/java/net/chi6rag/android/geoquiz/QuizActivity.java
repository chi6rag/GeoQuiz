package net.chi6rag.android.geoquiz;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private Button mTrueButton;
    private Button mFalseButton;
    private TextView mQuestionTextView;
    private ImageButton mNextButton;
    private ImageButton mPreviousButton;
    private Question[] mQuestionBank = new Question[]{
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_oceans, true)

    };
    private int mCurrentIndex = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // Inflate XML Widgets
        mTrueButton = (Button) findViewById(R.id.true_button);
        mFalseButton = (Button) findViewById(R.id.false_button);
        mQuestionTextView = (TextView) findViewById(R.id.question);
        mNextButton = (ImageButton) findViewById(R.id.next_button);
        mPreviousButton = (ImageButton) findViewById(R.id.previous_button);

        // Set the text of mQuestionTextView to CurrentIndex of QuestionBank array
        int mQuestionResId = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(mQuestionResId);

        // Add event listeners to widgets
        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateQuestion();
            }
        });

        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isRight = checkAnswer(true);
                popupToast(isRight);
                updateQuestion();
            }
        });

        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isRight = checkAnswer(false);
                popupToast(isRight);
                updateQuestion();
            }
        });

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateQuestion();
            }
        });

        mPreviousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateQuestion(-1);
            }
        });
    }

    private void updateQuestion(){
        // raise Error if difference >= mQuestionBank.length
        mCurrentIndex = (mCurrentIndex+1) % mQuestionBank.length;
        int mQuestionResId = mQuestionBank[ mCurrentIndex<0 ? -mCurrentIndex: mCurrentIndex ]
                .getTextResId();
        mQuestionTextView.setText(mQuestionResId);
    }

    private void updateQuestion(int difference){
        // raise Error if difference >= mQuestionBank.length
        // the values of difference can be:
        //                                a. 0
        //                                b. less than mQuestionBank.length
        //                                c. more than mQuestionBank.length
        mCurrentIndex = (mCurrentIndex+difference) % mQuestionBank.length;
        int mQuestionResId = mQuestionBank[ mCurrentIndex<0 ? -mCurrentIndex: mCurrentIndex]
                             .getTextResId();
        mQuestionTextView.setText(mQuestionResId);
    }

    private boolean checkAnswer(boolean userPressedAnswer){
        int index = mCurrentIndex<0 ? -mCurrentIndex: mCurrentIndex;
        boolean mQuestionAnswer = mQuestionBank[index].isAnswerTrue();
        return mQuestionAnswer == userPressedAnswer;
    }

    private void popupToast(boolean isRight){
        if(isRight){
            Toast.makeText( QuizActivity.this,
                    R.string.incorrect_toast,
                    Toast.LENGTH_SHORT
            ).show();
        }
        else{
            Toast.makeText(QuizActivity.this, R.string.correct_toast, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}