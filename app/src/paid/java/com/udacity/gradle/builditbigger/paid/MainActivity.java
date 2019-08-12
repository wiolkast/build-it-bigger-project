package com.udacity.gradle.builditbigger.paid;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.jokedisplay.JokeActivity;
import com.udacity.gradle.builditbigger.EndpointsAsyncTask;
import com.udacity.gradle.builditbigger.R;
import com.udacity.gradle.builditbigger.SimpleIdlingResource;

public class MainActivity extends AppCompatActivity {
    private static final String JOKE_EXTRA = "jokeExtra";
    private ProgressBar progressBar;
    private TextView errorView;
    @Nullable
    private SimpleIdlingResource idlingResource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progress_bar);
        errorView = findViewById(R.id.error_view);

        getIdlingResource();
    }

    public void tellJoke(View view) {
        if(idlingResource != null) idlingResource.setIdleState(false);
        progressBar.setVisibility(View.VISIBLE);
        errorView.setVisibility(View.INVISIBLE);
        EndpointsAsyncTask endpointsAsyncTask = new EndpointsAsyncTask(
                new EndpointsAsyncTask.OnEventListener<String>(){
            @Override
            public void onSuccess(String object) {
                progressBar.setVisibility(View.INVISIBLE);
                Intent intent = new Intent(getApplicationContext(), JokeActivity.class);
                intent.putExtra(JOKE_EXTRA, object);
                getApplicationContext().startActivity(intent);
                if(idlingResource != null) idlingResource.setIdleState(true);
            }

            @Override
            public void onFailure(Exception e) {
                progressBar.setVisibility(View.INVISIBLE);
                errorView.setVisibility(View.VISIBLE);
                if(idlingResource != null) idlingResource.setIdleState(true);
            }
        });
        endpointsAsyncTask.execute();
    }

    @VisibleForTesting
    @NonNull
    public SimpleIdlingResource getIdlingResource(){
        if(idlingResource == null){
            idlingResource = new SimpleIdlingResource();
        }
        return idlingResource;
    }
}
