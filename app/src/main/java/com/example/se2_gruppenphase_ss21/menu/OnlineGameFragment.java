package com.example.se2_gruppenphase_ss21.menu;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.se2_gruppenphase_ss21.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OnlineGameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OnlineGameFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    public OnlineGameFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment OnlineGameFragment.
     */
    public static OnlineGameFragment newInstance(String param1) {
        OnlineGameFragment fragment = new OnlineGameFragment();
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
        View view = inflater.inflate(R.layout.fragment_online_game, container, false);

        String userName = getArguments().getString(ARG_PARAM1);
        EditText roomNameEditText = view.findViewById(R.id.editTextRoomNameOnline);

        Button createOnlineGameButton = view.findViewById(R.id.button_createOnlineGame);
        createOnlineGameButton.setOnClickListener((View v) -> {

            String roomName = roomNameEditText.getText().toString();

            // open OnlineRoomFragment and pass userName and roomName as an argument
            getParentFragmentManager().beginTransaction().replace(R.id.container, OnlineRoomFragment.newInstance(userName, roomName)).addToBackStack("tag").commit();

        });

        /**
         * overrides behaviour of 'back-button'
         */
        requireActivity().getOnBackPressedDispatcher().addCallback(getActivity(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                getParentFragmentManager().beginTransaction().replace(R.id.container, StartGameFragment.newInstance()).addToBackStack("tag").commit();
            }
        });



        return view;
    }
}