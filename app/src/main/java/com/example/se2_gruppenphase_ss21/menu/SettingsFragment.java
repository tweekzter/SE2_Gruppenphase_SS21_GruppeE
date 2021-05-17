package com.example.se2_gruppenphase_ss21.menu;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.example.se2_gruppenphase_ss21.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment {

    private static String MY_PREFS = "switch_prefs";
    private static String SWITCH_STATUS = "switch_status";

    boolean switch_status;

    SharedPreferences myPreferences;
    SharedPreferences.Editor myEditor;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SettingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
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
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        Switch soundSwitch = view.findViewById(R.id.switch_sound);

        myPreferences = getActivity().getSharedPreferences(MY_PREFS, Context.MODE_PRIVATE);
        myEditor = getActivity().getSharedPreferences(MY_PREFS, Context.MODE_PRIVATE).edit();
        switch_status = myPreferences.getBoolean(SWITCH_STATUS, true);
        soundSwitch.setChecked(switch_status);

        soundSwitch.setOnCheckedChangeListener((CompoundButton compoundButton, boolean isChecked) -> {
            AudioManager amanager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

            if (isChecked) {
                amanager.setStreamMute(AudioManager.STREAM_NOTIFICATION, false);

                myEditor.putBoolean(SWITCH_STATUS, true);
                myEditor.apply();
                soundSwitch.setChecked(true);
            } else {
                amanager.setStreamMute(AudioManager.STREAM_NOTIFICATION, true);

                myEditor.putBoolean(SWITCH_STATUS, false);
                myEditor.apply();
                soundSwitch.setChecked(false);
            }
        });

        return view;
    }

}