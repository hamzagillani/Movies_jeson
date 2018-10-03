package com.digicon_valley.movies;


import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView listView;

   // TextView texView, textV2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //texView = findViewById(R.id.textView);

        listView=findViewById(R.id.listView);
        listView.setOnItemClickListener(this);



        new CheckConnection().execute("https://api.themoviedb.org/3/movie/popular?api_key=b496d4502d2577fa5da524af72e78843&language=en-US&page=1");

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

       Toast.makeText(MainActivity.this,"click",Toast.LENGTH_SHORT).show();
      //  Log.i("onItem","this method is invoked");

        Intent intent=new Intent(this,MovieDetails_list.class);
        intent.putExtra("MOVIE_DETAILS",(MovieDetails)parent.getItemAtPosition(position));
        startActivity(intent);


       /*Intent intent= new Intent(this,MovieDetailsList.class);
        intent.getExtras("MOVIE_DETAILS",(MovieDetails)parent.getItemIdAtPosition(position));
        startActivity(intent);
*/
    }

    class CheckConnection extends AsyncTask<String, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //texView.setText("");

            }

            @Override
            protected String doInBackground(String... strings) {


                URL url = null;

                try {
                    url = new URL(strings[0]);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

                try {
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    //urlConnection.setDoOutput(true);

                    //texView.setText(String.valueOf(urlConnection.getResponseCode()));
                  /*  Uri.Builder builder = new Uri.Builder().
                            appendQueryParameter("username", "adminkmkmik").
                            appendQueryParameter("password", "admin");
                    OutputStream outputStream = urlConnection.getOutputStream();*/

                  /*
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    bufferedWriter.write(builder.build().getEncodedQuery());
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();*/

                    //urlConnection.connect();

                    InputStream inputStream = urlConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    String s = bufferedReader.readLine();
                    bufferedReader.close();
                    return s;

                    // return  String.valueOf(urlConnection.getResponseCode());
                    //Log.i("Response: ",String.valueOf(urlConnection.getResponseCode()));
                } catch (IOException e) {
                    Log.e("Error: ", e.getMessage(), e);
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                JSONObject jsonObject=null;


                try {
                     jsonObject=new JSONObject(s);

                    // Map<String,Integer> companieMap=new HashMap<>();

                    ArrayList<MovieDetails> movieList=new ArrayList<>();


                    JSONArray jsonArray=jsonObject.getJSONArray("results");
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject object=jsonArray.getJSONObject(i);

                        MovieDetails movieDetails=new MovieDetails();
                        movieDetails.setOriginal_title(object.getString("original_title"));
                        movieDetails.setVote_average(object.getDouble("vote_average"));
                        movieDetails.setOverview(object.getString("overview"));
                        movieDetails.setRelease_date(object.getString("release_date"));
                        movieDetails.setPoster_path(object.getString("poster_path"));

                        movieList.add(movieDetails);

                       // Log.i("List",movieList.get(2).getOriginal_title());


                        // companieMap.put(object.getString("name"),object.getInt("id"));
                    }
                    MovieArrayAdapter movieArrayAdapter=new MovieArrayAdapter(MainActivity.this,R.layout.movie_list,movieList);
                    listView.setAdapter(movieArrayAdapter);
                   // texView.setText(String.valueOf(companieMap.get("Atman Entertainment")));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
