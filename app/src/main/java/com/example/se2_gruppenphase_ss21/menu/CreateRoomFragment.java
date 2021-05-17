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
import com.example.se2_gruppenphase_ss21.networking.server.GameServer;

import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateRoomFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateRoomFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CreateRoomFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment CreateRoomFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateRoomFragment newInstance(String param1) {
        CreateRoomFragment fragment = new CreateRoomFragment();
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

        // get userName from arguments
        View view = inflater.inflate(R.layout.fragment_create_room, container, false);

        // get userName from arguments
        String userName = getArguments().getString(ARG_PARAM1);


        Button startLocalServerButton = view.findViewById(R.id.button_startLocalServer);
        startLocalServerButton.setOnClickListener((View v) -> {

            EditText roomNameEditText = view.findViewById(R.id.editTextNameOfRoom);
            String roomName = roomNameEditText.getText().toString();

            EditText maxUsersEditText = view.findViewById(R.id.editTextMaxUsers);

            try {
                int maxUsers = Integer.parseInt(maxUsersEditText.getText().toString());

                if (maxUsers <= 1) {
                    Toast.makeText(getActivity(), "room should have at least two players!", Toast.LENGTH_SHORT).show();
                } else {
                    startServer(roomName, maxUsers);
                    Toast.makeText(getActivity(), "room successfully created!", Toast.LENGTH_SHORT).show();
                    getParentFragmentManager().beginTransaction().replace(R.id.container, LocalGameFragment.newInstance(userName)).addToBackStack("tag").commit();
                }

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "please enter max players and name!", Toast.LENGTH_SHORT).show();
            }

        });

        return view;
    }

    /**
     * starts a new game server
     *
     * @param roomName name of the created room
     * @param maxUser  number of max users that can participate in the game
     * @throws IOException
     */
    public void startServer(String roomName, int maxUser) throws IOException {
        GameServer localServer = new GameServer();
        localServer.setDynamicRoomCreation(false);
        localServer.createRoom(roomName, maxUser);
        localServer.start();
    }
}