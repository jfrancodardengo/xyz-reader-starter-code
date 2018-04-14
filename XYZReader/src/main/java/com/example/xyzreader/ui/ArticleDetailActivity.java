package com.example.xyzreader.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.xyzreader.R;
import com.squareup.picasso.Picasso;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ArticleDetailActivity extends AppCompatActivity {
    private TextView author;
    private TextView date;
    private TextView title;
//    private TextView body;
    private ImageView photo_url;
//    private Toolbar toolbar;
    private FloatingActionButton fab;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss");

    private String dateConverted;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);

//        toolbar = (Toolbar) findViewById(R.id.toolbar);
        author = (TextView) findViewById(R.id.author);
        date = (TextView) findViewById(R.id.date);
        title = (TextView) findViewById(R.id.title);
//        body =  (TextView) findViewById(R.id.body);
        photo_url =  (ImageView) findViewById(R.id.photo_url);
        fab = (FloatingActionButton) findViewById(R.id.share_fab);

        Intent it = getIntent();

        if( it != null  ) {

            String titleString = it.getStringExtra("TITLE");
//            toolbar.setTitle(titleString);
            title.setText(titleString.toString());

            String authorString = it.getStringExtra("AUTHOR");
            author.setText(authorString.toString());

            String dateString = it.getStringExtra("PUBLISHED_DATE");

            Date dateOld = null;
            try {
                dateOld = dateFormat.parse(dateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            SimpleDateFormat fmtOut = new SimpleDateFormat("dd-MM-yyyy");

            date.setText(String.format("%s por", fmtOut.format(dateOld)));

            String bodyString = it.getStringExtra("BODY");

            WebView view = (WebView) findViewById(R.id.textContent);
            String text;
            text = "<html><body><p align=\"justify\">";
            text+= bodyString;
            text+= "</p></body></html>";
            view.loadData(text, "text/html", "utf-8");

//            bodyString = bodyString.replaceAll("(\\r|\\n)", "");
//            body.setText(bodyString.toString().toLowerCase());


            Picasso.get().load(it.getStringExtra("PHOTO_URL")).into(photo_url);

            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fab(v);
                }
            });

        }
    }

    public void fab(View view) {
        startActivity(Intent.createChooser(ShareCompat.IntentBuilder.from(this)
                .setType("text/plain")
                .setText("Some sample text")
                .getIntent(), getString(R.string.action_share)));
    }
}
