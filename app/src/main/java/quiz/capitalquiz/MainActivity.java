package quiz.capitalquiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Quiz> mQuiz = new ArrayList<>();
    private HashMap<String, String> mCapitals = new HashMap<>();
    private String mCurrentCapital;

    private TextView tvCapitalName;
    private EditText etAnswer;
    private Button bNext;
    private TableLayout tlResults;
    private TextView labelResults;
    private Button shareResult;

    private void init() {
        mCurrentCapital = null;
        String counrtiesAndCapitals[] = getString(R.string.capitals).split("[{]|,|[}]");
        for (String cac : counrtiesAndCapitals) {
            String pair[] = cac.split(":");
            if (pair.length >= 2) {
                mCapitals.put(pair[0], pair[1]);
            }
        }

        tvCapitalName = (TextView) findViewById(R.id.countryName);
        etAnswer = (EditText) findViewById(R.id.answer);
        bNext = (Button) findViewById(R.id.next);
        tlResults = (TableLayout) findViewById(R.id.results);
        labelResults = (TextView) findViewById(R.id.labelResults);
        shareResult = (Button) findViewById(R.id.share);
//        Log.d("STATE", "===>>>" + mCapitals.toString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        final Iterator<Map.Entry<String, String>> it = mCapitals.entrySet().iterator();
        if (it.hasNext()) {
            mCurrentCapital = it.next().getKey();
            tvCapitalName.setText(mCurrentCapital);
        }

        bNext.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
               nextCountry(it);
            }
        });

        shareResult.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                share();
            }
        });
    }

    private void share() {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = "Here is the share content body";
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

    private void nextCountry(Iterator<Map.Entry<String, String>> it) {
        Quiz quiz = new Quiz();
        quiz.setCountry(mCurrentCapital);
        String answer = etAnswer.getText().toString();
        quiz.setCapital(answer);
        if (answer != null && answer.length() > 0 &&
                answer.toLowerCase().equals(mCapitals.get(mCurrentCapital).toLowerCase())) {
            quiz.setCorrectly(true);
        } else {
            quiz.setCorrectly(false);
        }
        mQuiz.add(quiz);

        if (it.hasNext()) {
            mCurrentCapital = it.next().getKey().toString();
            tvCapitalName.setText(mCurrentCapital);
            etAnswer.setText("");
        } else {
            tvCapitalName.setVisibility(View.INVISIBLE);
            etAnswer.setVisibility(View.INVISIBLE);
            bNext.setVisibility(View.INVISIBLE);
            tlResults.setVisibility(View.VISIBLE);
            labelResults.setVisibility(View.VISIBLE);
            shareResult.setVisibility(View.VISIBLE);
            initResultsTable(tlResults, labelResults);
        }
    }

    private void initResultsTable(TableLayout tl, TextView tv) {
        tl.setStretchAllColumns(false);
        int countRightAnswer = 0;
        for (Quiz q : mQuiz) {
            TableRow row = new TableRow(MainActivity.this);

            TextView country = new TextView(MainActivity.this);
            country.setText(q.getCountry());
            row.addView(country, new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));

            TextView capital = new TextView(MainActivity.this);
            capital.setText(q.getCapital());
            row.addView(capital, new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));

            TextView correctness = new TextView(MainActivity.this);
            correctness.setText(q.isCorrectly() ? "+" : "-");
            row.addView(correctness, new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));

            tl.addView(row, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT));

            if (q.isCorrectly()) {
                countRightAnswer++;
            }
        }
        tv.setText("Result: " + (int) (100 * countRightAnswer/mQuiz.size()) + "%");
    }
}