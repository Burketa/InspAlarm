package br.edu.utfpr.rogerio.inspalarm.Activities;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import br.edu.utfpr.rogerio.inspalarm.DB.SchedulesDB;
import br.edu.utfpr.rogerio.inspalarm.R;
import br.edu.utfpr.rogerio.inspalarm.model.Quote;

public class QuoteActivity extends AppCompatActivity {

    private ListView quoteListView;
    private ArrayAdapter<Quote> listAdapter;
    private List<Quote> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quote);
        setTitle("New Quote");

        quoteListView = findViewById(R.id.quote_list_view);

        //Lista os quotes do banco
        //ListQuotes();
    }

    public void ListQuotes() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                SchedulesDB db = SchedulesDB.getDatabase(QuoteActivity.this);

                list = db.quoteDAO().queryAll();

                QuoteActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listAdapter = new ArrayAdapter<>(QuoteActivity.this,
                                android.R.layout.simple_list_item_1,
                                list);

                        quoteListView.setAdapter(listAdapter);
                    }
                });
            }
        });
    }
}
