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
 * Use the {@link MenuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MenuFragment extends Fragment {

    public MenuFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MenuFragment.
     */
    public static MenuFragment newInstance() {
        MenuFragment fragment = new MenuFragment();
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
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        Button rulesButton = view.findViewById(R.id.button_rules);
        rulesButton.setOnClickListener((View v) -> getParentFragmentManager().beginTransaction().replace(R.id.container, new RulesFragment()).addToBackStack("tag").commit());

        Button settingsButton = view.findViewById(R.id.button_settings);
        settingsButton.setOnClickListener((View v) -> getParentFragmentManager().beginTransaction().replace(R.id.container, new SettingsFragment()).addToBackStack("tag").commit());

        Button startGameButton = view.findViewById(R.id.button_startGame);
        startGameButton.setOnClickListener((View v) -> getParentFragmentManager().beginTransaction().replace(R.id.container, new StartGameFragment()).addToBackStack("tag").commit());

        return view;
    }

}