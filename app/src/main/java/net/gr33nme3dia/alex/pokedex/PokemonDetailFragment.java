package net.gr33nme3dia.alex.pokedex;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.app.NavUtils;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.ShareActionProvider;

import com.squareup.picasso.Picasso;

/**
 * Created by alex on 10/31/14.
 */
public class PokemonDetailFragment extends Fragment {

    private static final String ARG_NOMBRE = "nombre";
    private static final String ARG_AVATAR = "avatar";
    private String mnombre;
    private String mavatar;
    private ShareActionProvider mShareActionProvider;


    public static PokemonDetailFragment newInstance(Pokemon pokemon) {
        PokemonDetailFragment fragment = new PokemonDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_NOMBRE, pokemon.getNombre());
        args.putString(ARG_AVATAR, pokemon.getAvatar());
        fragment.setArguments(args);
        return fragment;
    }
    public PokemonDetailFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mnombre = getArguments().getString(ARG_NOMBRE);
            mavatar = getArguments().getString(ARG_AVATAR);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setHasOptionsMenu(true); //

        View rootView = inflater.inflate(R.layout.fragment_pokemon_detail,
                container, false);

        TextView nombre = (TextView)rootView.findViewById(R.id.textview_pokemon_nombre);
        ImageView imageView = (ImageView)rootView.findViewById(R.id.imageView3);
        if (TextUtils.isEmpty(mavatar) || mavatar == null)
            Picasso.with(getActivity()).load(R.drawable.ic_launcher).error(R.drawable.ic_launcher).into(imageView);
        else
            Picasso.with(getActivity()).load(mavatar).error(R.drawable.ic_launcher).into(imageView);
        nombre.setText(mnombre+"..."+mavatar);

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.my, menu);

        MenuItem item = menu.findItem(R.id.action_share);
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
        mShareActionProvider.setShareIntent(getDefaultShareIntent());
        super.onCreateOptionsMenu(menu,inflater);
    }

    private Intent getDefaultShareIntent(){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        if (mavatar != null) {
            intent.putExtra(Intent.EXTRA_SUBJECT, "¿Quien ese ese pokemon?");
            intent.putExtra(Intent.EXTRA_TEXT, mavatar);
        }
        return intent;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            NavUtils.navigateUpFromSameTask(getActivity());
            return true;
        }else if (id == R.id.action_show_image){
            if (mavatar != null){
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mavatar));
                startActivity(intent);
            }
        }else if (id == R.id.action_call){
            String url = "tel:70418755";

            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(url));
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}