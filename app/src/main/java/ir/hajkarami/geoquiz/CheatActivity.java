package ir.hajkarami.geoquiz;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CheatActivity extends AppCompatActivity {

    private static final String EXTRA_ANSWER_IS_TRUE =
            "ir.hajkarami.geoquiz.answer_is_true";
    private static final String EXTRA_ANSWER_SHOWN =
            "ir.hajkarami.geoquiz.answer_shown";
    private boolean mAnswerIsTrue;
    private Button mShowAnswer;
    private TextView mAnswerTextView;

    public static Intent newIntent (Context packageContext , Boolean answersTrue){
        Intent intent = new Intent(packageContext, CheatActivity.class);
        intent.putExtra(EXTRA_ANSWER_IS_TRUE,answersTrue);
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cheat);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE,false);

        mAnswerTextView = (TextView) findViewById(R.id.textView);

        mShowAnswer = (Button) findViewById(R.id.show_answer_button);

        if (mAnswerIsTrue) mAnswerTextView.setText(R.string.true_text);
        else mAnswerTextView.setText(R.string.false_text);

        setExtraAnswerShownResult(true);

    }

    public static boolean wasAnswerShown (Intent result){
        return result.getBooleanExtra(EXTRA_ANSWER_IS_TRUE,false);
    }

    private void setExtraAnswerShownResult(Boolean isAnswerIsShown){
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN,isAnswerIsShown);
        setResult(RESULT_OK,data);

    }
}