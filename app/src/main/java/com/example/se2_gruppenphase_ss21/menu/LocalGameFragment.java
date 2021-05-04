package com.example.se2_gruppenphase_ss21.menu;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.se2_gruppenphase_ss21.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LocalGameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LocalGameFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LocalGameFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment LocalGameFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LocalGameFragment newInstance(String param1) {
        LocalGameFragment fragment = new LocalGameFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_local_game, container, false);

        String userName =  getArguments().getString(ARG_PARAM1);

        Button createRoomButton = view.findViewById(R.id.button_createRoom);
        createRoomButton.setOnClickListener((View v) -> {
            //TODO: open createRoom Fragment with userName
        });

        Button joinRoomButton = view.findViewById(R.id.button_joinRoom);
        joinRoomButton.setOnClickListener((View v) -> {
            //TODO: open joinRoomFragment with userName as param.
        });

        return view;
    }
}