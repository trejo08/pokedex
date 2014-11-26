package net.gr33nme3dia.alex.pokedex;

import android.annotation.TargetApi;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by alex on 10/19/14.
 */
public class PokemonListFragment extends Fragment {

    private ProgressBar progressBar;
    private ListView listPokemon;
    private PokemonAdapter pokemonAdapter;
    private Callbacks mCallbacks = sDummyCallbacks;

    public interface Callbacks {
        /**
         * Callback for when an item has been selected.
         */
        public void onItemSelected(Pokemon pokemon);
    }

    private static Callbacks sDummyCallbacks = new Callbacks() {
        @Override
        public void onItemSelected(Pokemon pokemon) {

        }
    };

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (!(activity instanceof Callbacks)) {
            throw new IllegalStateException(
                    "Activity must implement fragment's callbacks.");
        }
        mCallbacks = (Callbacks) activity;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_my, container, false);
        listPokemon = (ListView) rootView.findViewById(R.id.listView_pokemon);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar2);
        if (pokemonAdapter == null){
            List<Pokemon> pokemonList = new ArrayList<Pokemon>();
            pokemonAdapter = new PokemonAdapter(pokemonList, getActivity());
            runTask();
        }
        listPokemon.setAdapter(pokemonAdapter);
        listPokemon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Pokemon pokemon = (Pokemon)listPokemon.getAdapter().getItem(i);
                mCallbacks.onItemSelected(pokemon);
            }
        });

        return rootView;
    }
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public void runTask(){
//        PokemonListApiTask pokemonListApiTask = new PokemonListApiTask(pokemonAdapter,listPokemon,progressBar);
//        pokemonListApiTask.execute();
        listPokemon.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);

        GsonRequest<Pokemon[]> getPersons =
            new GsonRequest<Pokemon[]>("http://mi-pokedex.herokuapp.com/api/v1/pokemons", Pokemon[].class,

                new Response.Listener<Pokemon[]>() {
                    @Override
                    public void onResponse(Pokemon[] response) {
                        List<Pokemon> pokemons = Arrays.asList(response);
                        if(pokemons!=null){
                            pokemonAdapter.clear();
                            pokemonAdapter.addAll(pokemons);
                        }
                        progressBar.setVisibility(View.INVISIBLE);
                        listPokemon.setVisibility(View.VISIBLE);
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.INVISIBLE);
                listPokemon.setVisibility(View.VISIBLE);
            }
        });

        PokedexApplication.getInstance().addToRequestQueue(getPersons);
    }


}
