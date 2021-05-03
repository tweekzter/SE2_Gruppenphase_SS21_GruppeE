package com.example.se2_gruppenphase_ss21.networking;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.se2_gruppenphase_ss21.Player;
import com.example.se2_gruppenphase_ss21.R;

import java.util.ArrayList;
import java.util.List;

public class PlayerArrayAdapter extends ArrayAdapter<Player> {
    private static final String TAG = "PlayerArrayAdapter";
    private List<Player> playerList = new ArrayList<Player>();

    static class PlayerViewHolder{
        TextView position;
        TextView name;
        TextView points;
    }

    public PlayerArrayAdapter(Context context, int textViewResourceId){
        super(context, textViewResourceId);
    }

    @Override
    public void add(Player object){
        playerList.add(object);
        super.add(object);
    }

    @Override
    public int getCount(){
        return this.playerList.size();
    }

    @Override
    public Player getItem(int index){
        return this.playerList.get(index);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View row = convertView;
        PlayerViewHolder viewHolder;
        if (row == null){
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.listview_row_layout, parent, false);
            viewHolder = new PlayerViewHolder();
            viewHolder.position = (TextView) row.findViewById(R.id.position);
            viewHolder.name = (TextView) row.findViewById(R.id.playerName);
            viewHolder.points = (TextView) row.findViewById(R.id.points);
            return row;
        }else {
            viewHolder = (PlayerViewHolder) row.getTag();
        }
        Player player = getItem(position);
        viewHolder.position.setText(player.getPosition());
        viewHolder.name.setText(player.getName());
        viewHolder.points.setText(player.getName());
        return row;
    }

    public Bitmap decodeToBitmap(byte[] decodedByte){
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }
}
