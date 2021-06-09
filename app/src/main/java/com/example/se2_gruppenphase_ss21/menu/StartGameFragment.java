package com.example.se2_gruppenphase_ss21.menu;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.se2_gruppenphase_ss21.R;
import com.example.se2_gruppenphase_ss21.networking.MulticastReceiver;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StartGameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StartGameFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public StartGameFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StartGameFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StartGameFragment newInstance(String param1, String param2) {
        StartGameFragment fragment = new StartGameFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
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
        View view = inflater.inflate(R.layout.fragment_start_game, container, false);

        Button localGameButton = view.findViewById(R.id.button_localGame);
        localGameButton.setOnClickListener((View v) -> {
            String userName = getUsername(view);
            if (userName != null) {
                // open LocalGameFragment and pass userName as an argument
                getParentFragmentManager().beginTransaction().replace(R.id.container, LocalGameFragment.newInstance(userName)).addToBackStack("tag").commit();

                // MulticastReceiver starts to listen
                MulticastReceiver.startListen();

            }
        });

        Button onlineGameButton = view.findViewById(R.id.button_onlineGame);
        onlineGameButton.setOnClickListener((View v) -> {
            String userName = getUsername(view);
            if (userName != null) {
                // open OnlineGameFragment and pass userName as an argument
                getParentFragmentManager().beginTransaction().replace(R.id.container, OnlineGameFragment.newInstance(userName)).addToBackStack("tag").commit();

                // MulticastReceiver starts to listen
                //MulticastReceiver.startListen();
            }
        });

        return view;
    }

    /**
     * returns userName from input fiels and and NULL if nothing is entered
     * @param view
     * @return userNmae input as String of NULL
     */
    public String getUsername(View view) {
        EditText username = view.findViewById(R.id.editTextUserName);

        if (username.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "you did not enter a name!", Toast.LENGTH_SHORT).show();
            return null;
        }

        return username.getText().toString();
    }
}