package com.udacity.gradle.builditbigger.paid;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.jokedisplay.JokeActivity;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.R;
import com.udacity.gradle.builditbigger.SimpleIdlingResource;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;

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
        new com.udacity.gradle.builditbigger.paid.MainActivity.EndpointsAsyncTask(this).execute();
    }

    private class EndpointsAsyncTask extends AsyncTask<Void, Void, String> {
        private MyApi myApiService = null;
        private Context context;
        private Boolean error = false;

        private EndpointsAsyncTask(Context context){
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if(idlingResource != null) idlingResource.setIdleState(false);
            progressBar.setVisibility(View.VISIBLE);
            errorView.setVisibility(View.INVISIBLE);
        }

        @Override
        protected String doInBackground(Void... params) {
            if(myApiService == null) {  // Only do this once
                MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                        new AndroidJsonFactory(), null)
                        // options for running against local devappserver
                        // - 10.0.2.2 is localhost's IP address in Android emulator
                        // - turn off compression when running against local devappserver
                        .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                        .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                            @Override
                            public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                                abstractGoogleClientRequest.setDisableGZipContent(true);
                            }
                        });
                myApiService = builder.build();
            }

            try {
                error = false;
                return myApiService.tellJoke().execute().getData();
            } catch (IOException e) {
                error = true;
                return e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            progressBar.setVisibility(View.INVISIBLE);
            if(error) {
                errorView.setVisibility(View.VISIBLE);
            } else {
                Intent intent = new Intent(context, JokeActivity.class);
                intent.putExtra(JOKE_EXTRA, result);
                context.startActivity(intent);
            }
            if(idlingResource != null) idlingResource.setIdleState(true);
        }
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
