package com.example.se2_gruppenphase_ss21;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PlayerArrayAdapter extends ArrayAdapter<Player> {
    private List<Player> playerList = new ArrayList<>();

    static class PlayerViewHolder{
        TextView position;
        TextView name;
        TextView points;
        CheckBox challenge;
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
            viewHolder.challenge = (CheckBox) row.findViewById(R.id.challenge);
            row.setTag(viewHolder);
        }else {
            viewHolder = (PlayerViewHolder) row.getTag();
        }
        Player player = getItem(position);
        viewHolder.position.setText(player.getPosition());
        viewHolder.name.setText(player.getName());
        viewHolder.points.setText(player.getPoints());
        viewHolder.challenge.setChecked(false);
        return row;
    }

    public Bitmap decodeToBitmap(byte[] decodedByte){
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void notifyDataSetChanged() {
        playerList.sort(new MostPointsComparator());
        super.notifyDataSetChanged();
    }

    class MostPointsComparator implements Comparator<Player> {
        @Override
        public int compare(Player p1, Player p2) {
            return (Integer.valueOf(p2.getPoints().split(" ", 2)[0]) - Integer.valueOf(p1.getPoints().split(" ", 2)[0]));
        }
    }

}
