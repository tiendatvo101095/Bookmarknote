package com.example.tiendat.saveurl;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mBookmark;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Bookmarks");

        mBookmark = (RecyclerView) findViewById(R.id.bookmarks_list);
        mBookmark.setHasFixedSize(true);
        mBookmark.setLayoutManager(new LinearLayoutManager(this));


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddActivity.class));

            }
        });
    }

    @Override
    protected void  onStart(){
        super.onStart();

        FirebaseRecyclerAdapter<Bookmarks, BookmarkViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Bookmarks, BookmarkViewHolder>(

                Bookmarks.class,
                R.layout.bookmark_row,
                BookmarkViewHolder.class,
                mDatabase

        ) {
            @Override
            protected void populateViewHolder(BookmarkViewHolder viewHolder, Bookmarks model, int position) {

                viewHolder.setUrl(model.getUrl());
                viewHolder.setName(model.getName());
                viewHolder.setImage( getApplicationContext() ,model.getImage());
            }
        };

        mBookmark.setAdapter(firebaseRecyclerAdapter);
    }


    public static class BookmarkViewHolder extends RecyclerView.ViewHolder
    {


        View mView;

        public BookmarkViewHolder(View itemView) {
            super(itemView);
            mView = itemView ;
        }

        public void setUrl(String url){

            TextView bookmark_url = (TextView) mView.findViewById(R.id.bookmark_url);
            bookmark_url.setText(url);
        }

        public void setName(String name){
            TextView bookmark_name = (TextView) mView.findViewById(R.id.bookmark_name);
            bookmark_name.setText(name);
        }

        public void setImage(Context ctx, String image){
            ImageView bookmark_image = (ImageView) mView.findViewById(R.id.bookmark_image);
            Picasso.with(ctx).load(image).into(bookmark_image);
        }
    }
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
