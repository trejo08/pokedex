package net.gr33nme3dia.alex.pokedex;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.gr33nme3dia.alex.pokedex.Pokemon;
import net.gr33nme3dia.alex.pokedex.R;

import java.util.List;

/**
 * Created by alex on 10/26/14.
 */
public class PokemonAdapter extends ArrayAdapter<Pokemon> {
    private List<Pokemon> pokemons;
    private Context context;
    public PokemonAdapter(List<Pokemon> pokemons, Context ctx) {
        super(ctx, R.layout.list_item_pokemon, pokemons);
        this.pokemons = pokemons;
        this.context = ctx;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            // This a new view we inflate the new layout
            ViewHolder viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item_pokemon, viewGroup, false);

            viewHolder.textView = (TextView) view.findViewById(R.id.list_item_pokemon_textview);
            viewHolder.imageView = (ImageView) view.findViewById(R.id.image_pokemon);
            view.setTag(viewHolder);
        }
        ViewHolder holder = (ViewHolder)view.getTag();
        Pokemon p = (Pokemon)getItem(i);
        holder.textView.setText(p.getNombre());
        if (TextUtils.isEmpty(p.getAvatar()) || p.getAvatar() == null)
            Picasso.with(context).load(R.drawable.ic_launcher).into(holder.imageView);
        else
            Picasso.with(context).load(p.getAvatar()).error(R.drawable.ic_launcher).into(holder.imageView);

        return view;
    }

    static class ViewHolder{
        public TextView textView;
        public ImageView imageView;
    }

}
