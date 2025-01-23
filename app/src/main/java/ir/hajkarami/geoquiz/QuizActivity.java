package ir.hajkarami.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class QuizActivity extends AppCompatActivity {

    private static final String EXTRA_ANSWER_IS_TRUE =
            "ir.hajkarami.geoquiz.answer_is_true";

    private static final String KEY_INDEX = "index";
    private static final int REQUEST_CODE_CHEAT = 0;

    private Button mTrueButton;
    private Button mFalseButton;
    private TextView  mQuestionsTextView;

    private Question[] mQuestionsBank = new Question[]{
            new Question(R.string.question1, true),
            new Question(R.string.question2, false),
            new Question(R.string.question3, true),
            new Question(R.string.question4, false),
            new Question(R.string.question5, false),
            new Question(R.string.question6, true)
    };

    private int mCurrentIndex = 0;
    private boolean misCheater;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });

        if (savedInstanceState != null)
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX,0);


        Button mCheatButton = (Button) findViewById(R.id.cheat_button);
        mQuestionsTextView = (TextView) findViewById(R.id.question_text_view);
        mTrueButton = findViewById(R.id.btn_true);
        mFalseButton = findViewById(R.id.btn_false);
        Button mNextButton = (Button) findViewById(R.id.btn_next);





        //Recommended method
        mFalseButton.setOnClickListener(
                v -> checkAnswer(false)
        );

        mCheatButton.setOnClickListener(
                v -> {

                    boolean answerTrue = mQuestionsBank[mCurrentIndex].isAnswerTrue();
                    Intent intent = CheatActivity.newIntent(QuizActivity.this,answerTrue);

//                    startActivities(new Intent[]{intent});
                    startActivityForResult(intent,REQUEST_CODE_CHEAT);
                }
        );

        mNextButton.setOnClickListener(
                v -> {
                    mCurrentIndex = (mCurrentIndex + 1) % mQuestionsBank.length;
                    updateQuestion();
                }
        );

        //The method written in the book
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });

        updateQuestion();
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) return;
        if (resultCode == REQUEST_CODE_CHEAT)
            if (data == null) return;

        misCheater = CheatActivity.wasAnswerShown(data);
    }


    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt(KEY_INDEX,mCurrentIndex);
    }

    private void updateQuestion(){
        int question = mQuestionsBank[mCurrentIndex].getTextResId();
        mQuestionsTextView.setText(question);
    }

    private void checkAnswer(boolean userPressedTrue){
        boolean answerIsTrue = mQuestionsBank[mCurrentIndex].isAnswerTrue();
        int messageResId = 0;

        if (misCheater) messageResId = R.string.judgment_toast;
        else {
        if (userPressedTrue == answerIsTrue) messageResId = R.string.correct_toast;
        else messageResId = R.string.incorrect_toast;
        }
        Toast.makeText(getApplicationContext(),messageResId,Toast.LENGTH_SHORT).show();

    }




}