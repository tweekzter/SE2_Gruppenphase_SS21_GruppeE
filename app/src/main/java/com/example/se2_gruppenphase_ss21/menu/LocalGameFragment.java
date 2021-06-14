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

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";


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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_local_game, container, false);

        // get userName from arguments
        String userName = getArguments().getString(ARG_PARAM1);

        Button createRoomButton = view.findViewById(R.id.button_createRoom);
        // open createRoomFragment and pass userName as argument
        createRoomButton.setOnClickListener((View v) -> getParentFragmentManager().beginTransaction().replace(R.id.container, CreateRoomFragment.newInstance(userName)).addToBackStack("tag").commit());

        Button joinRoomButton = view.findViewById(R.id.button_joinRoom);
        // open joinRoomFragment with userName as argument.
        joinRoomButton.setOnClickListener((View v) -> getParentFragmentManager().beginTransaction().replace(R.id.container, JoinRoomFragment.newInstance(userName)).addToBackStack("tag").commit());

        return view;
    }
}