package com.example.se2_gruppenphase_ss21.menu;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.se2_gruppenphase_ss21.R;
import com.example.se2_gruppenphase_ss21.networking.AvailableRoom;
import com.example.se2_gruppenphase_ss21.networking.MulticastReceiver;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link JoinRoomFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class JoinRoomFragment extends Fragment {


    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    public JoinRoomFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment JoinRoomFragment.
     */
    public static JoinRoomFragment newInstance(String param1) {
        JoinRoomFragment fragment = new JoinRoomFragment();
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
        View view = inflater.inflate(R.layout.fragment_join_room, container, false);

        String userName = getArguments().getString(ARG_PARAM1);

        updateRooms(view, userName);

        /**
         * overrides behaviour of 'back-button'; client should be removed from room when clicking back button
         */
        requireActivity().getOnBackPressedDispatcher().addCallback(getActivity(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                getParentFragmentManager().beginTransaction().replace(R.id.container, LocalGameFragment.newInstance(userName)).addToBackStack("tag").commit();
            }
        });


        return view;
    }

    public void updateRooms(View view, String userName) {

        // list of all rooms that have been created
        List<AvailableRoom> rooms = MulticastReceiver.getRooms();

        // layout that will contain the created rooms
        LinearLayout layout = view.findViewById(R.id.layoutJoinRoom);

        // for each available room a button will be generated
        for (AvailableRoom r : rooms) {

            Button availableRoomButton;

            availableRoomButton = new Button(getContext());
            availableRoomButton.setText(r.getName() + "    (" + r.getCurrentPlayers() + "/" + r.getMaxPlayers() + ")");

            // open RoomFragment and pass the userName and the available room as an argument
            availableRoomButton.setOnClickListener((View v) -> getParentFragmentManager().beginTransaction().replace(R.id.container, RoomFragment.newInstance(userName, r)).addToBackStack("tag").commit());

            layout.addView(availableRoomButton);
        }
    }
}

