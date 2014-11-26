package net.gr33nme3dia.alex.pokedex;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by alex on 10/26/14.
 */
public class Pokemon implements Parcelable {
    private String nombre;
    private String avatar;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String imagen) {
        this.avatar = avatar;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.nombre);
        dest.writeString(this.avatar);
    }

    public Pokemon() {
    }

    private Pokemon(Parcel in) {
        this.nombre = in.readString();
        this.avatar = in.readString();
    }

    public static final Creator<Pokemon> CREATOR = new Creator<Pokemon>() {
        public Pokemon createFromParcel(Parcel source) {
            return new Pokemon(source);
        }

        public Pokemon[] newArray(int size) {
            return new Pokemon[size];
        }
    };
}
