package net.gr33nme3dia.alex.pokedex;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alex on 10/19/14.
 */
public class PokemonListApiTask extends AsyncTask<Void, Void, ArrayList<Pokemon>> {

    private final String LOG_TAG = PokemonListApiTask.class.getSimpleName();

    private PokemonAdapter mPokemonAdapter;
    private ListView listView;
    private ProgressBar progressBar;

    public PokemonListApiTask(PokemonAdapter mPokemonAdapter, ListView listView, ProgressBar progressBar){
        this.mPokemonAdapter = mPokemonAdapter;
        this.listView =listView;
        this.progressBar = progressBar;

    }
    @Override
    protected void onPostExecute(ArrayList<Pokemon> result){
        if( result != null){
            mPokemonAdapter.clear();
            for( Pokemon dayForecastStr : result){
                mPokemonAdapter.add(dayForecastStr);
            }
            this.listView.setVisibility(View.VISIBLE);
            this.progressBar.setVisibility(View.INVISIBLE);
        }

    }



    protected void onPreExecute(){
        super.onPreExecute();
        this.listView.setVisibility(View.INVISIBLE);
        this.progressBar.setVisibility(View.VISIBLE);
    }


    @Override
    protected ArrayList<Pokemon> doInBackground(Void... voids) {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String pokemonJsonStr = null;

        try {

            URL url = new URL("http://mi-pokedex.herokuapp.com/api/v1/pokemons");

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                return null;
            }
            pokemonJsonStr = buffer.toString();
            JSONArray pokemonsArray = new JSONArray(pokemonJsonStr);
            ArrayList<Pokemon> results = new ArrayList<Pokemon>();
            for(int i = 0; i < pokemonsArray.length(); i++) {
                JSONObject pokemonJson = pokemonsArray.getJSONObject(i);
                Pokemon pokemon = new Pokemon();
                pokemon.setNombre(pokemonJson.getString("nombre"));
                pokemon.setAvatar(pokemonJson.getString("avatar"));
                results.add(pokemon);
            }
            return results;


        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
        } finally{
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e("PlaceholderFragment", "Error closing stream", e);
                }
            }
        }
        return null;
    }
}
