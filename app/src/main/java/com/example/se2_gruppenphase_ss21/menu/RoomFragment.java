//package com.example.se2_gruppenphase_ss21.menu;
//
//import android.content.Context;
//import android.os.Bundle;
//
//import androidx.fragment.app.Fragment;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.LinearLayout;
//
//import com.example.se2_gruppenphase_ss21.R;
//import com.example.se2_gruppenphase_ss21.networking.AvailableRoom;
//import com.example.se2_gruppenphase_ss21.networking.client.GameClient;
//import com.example.se2_gruppenphase_ss21.networking.client.ServerMessageListener;
//import com.example.se2_gruppenphase_ss21.networking.server.GameServer;
//import com.example.se2_gruppenphase_ss21.networking.server.logic.GameLogicException;
//
//import java.io.IOException;
//
///**
// * A simple {@link Fragment} subclass.
// * Use the {@link RoomFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
//public class RoomFragment extends Fragment {
//
//    static LinearLayout layout;
//    static Context context;
//
//
//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
//
//    public RoomFragment() {
//        // Required empty public constructor
//    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @return A new instance of fragment RoomFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static RoomFragment newInstance(String param1, AvailableRoom r) {
//        RoomFragment fragment = new RoomFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        fragment.setArguments(args);
//
//        String userName = param1;
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                GameClient client = null;
//                try {
//                    client = new GameClient(r, userName);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//                try {
//                    client.connect();
//
//                    ServerMessageListener listener = new ServerMessageListener() {
//                        @Override
//                        public void readyCount(int current, int max) {
//                            System.out.println(current + "!!!!!!!!!!!");
//                        }
//
//                        @Override
//                        public void onGameStart() {
//
//                        }
//
//                        @Override
//                        public void userDisconnect(String nickname) {
//
//                        }
//
//                        @Override
//                        public void receiveUserList(String[] nicknames) {
//                            onCreateView();
//                            updatePlayers(nicknames);
////                            for (String name : nicknames) {
////                                System.out.println(name + "!!!!!!!!");
////                            }
//                        }
//
//                        @Override
//                        public void unknownMessage(String message) {
//
//                        }
//                    };
//
//
//                    client.registerListener(listener);
//                    client.startReceiveLoop();
////                  client.sendReady(true);
//                    // TODO: display roomname and users in this fragment... and num users and ready button; how to get users
////                    System.out.println(listener.receiveUserList(););
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } catch (GameLogicException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        context = getContext();
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.fragment_room, container, false);
//
//        layout = view.findViewById(R.id.layoutRoom);
//
//        return view;
//    }
//
//    public static void updatePlayers(String[] nicknames) {
//
////        for (String n : nicknames) {
////            // TODO not possible from this thread.....
////            Button newbtn;
////            newbtn = new Button(context);
////            newbtn.setText(n);
////            layout.addView(newbtn);
////            System.out.println(n);
////
////        }
//
////        Button newbtn;
////
////        newbtn = new Button(context);
////        newbtn.setText(r.toString());
////
////        layout.addView(newbtn);
//
////        getParentFragmentManager().beginTransaction().replace(R.id.container, RoomFragment.newInstance(userName, r)).addToBackStack("tag").commit();
////        do i have access to instances?
////        getParentFragmentManager().beginTransaction().replace(R.id.container, new RoomFragment()).addToBackStack("tag").commit();
//
//
//    }
//}


package com.example.se2_gruppenphase_ss21.menu;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.se2_gruppenphase_ss21.R;
import com.example.se2_gruppenphase_ss21.networking.AvailableRoom;
import com.example.se2_gruppenphase_ss21.networking.client.GameClient;
import com.example.se2_gruppenphase_ss21.networking.client.ServerMessageListener;
import com.example.se2_gruppenphase_ss21.networking.server.logic.GameLogicException;

import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RoomFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RoomFragment extends Fragment {


    static AvailableRoom room;
    String[] names;

    Handler h = new Handler();


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RoomFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment RoomFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RoomFragment newInstance(String param1, AvailableRoom r) {
        RoomFragment fragment = new RoomFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        room = r;
        fragment.setArguments(args);

        String userName = param1;


        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
        // context = getContext();
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        // System.out.println(getArguments().getString(ARG_PARAM1));
        // updatePlayers(names, getView());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_room, container, false);

        String userName = getArguments().getString(ARG_PARAM1);

        String[] test = {"a", "b", "c"};

//        final Runnable r = new Runnable() {
//            public void run() {
//                updatePlayers(test, view);
//            }
//        };
//
//        new Handler().postDelayed(r, 1000);


        // working
        // updatePlayers(test, view);

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                GameClient client = null;
//                try {
//                    client = new GameClient(room, userName);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//                try {
//                    client.connect();
//
//                    ServerMessageListener listener = new ServerMessageListener() {
//                        @Override
//                        public void readyCount(int current, int max) {
//                            System.out.println(current + "!!!!!!!!!!!");
//                        }
//
//                        @Override
//                        public void onGameStart() {
//
//                        }
//
//                        @Override
//                        public void userDisconnect(String nickname) {
//
//                        }
//
//                        @Override
//                        public void receiveUserList(String[] nicknames) {
//                            updatePlayers(nicknames, view);
////                            for (String name : nicknames) {
////                                System.out.println(name + "!!!!!!!!");
////                            }
//                        }
//
//                        @Override
//                        public void unknownMessage(String message) {
//
//                        }
//                    };
//
//
//                    client.registerListener(listener);
//                    client.startReceiveLoop();
////                  client.sendReady(true);
//                    // TODO: display roomname and users in this fragment... and num users and ready button; how to get users
////                    System.out.println(listener.receiveUserList(););
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } catch (GameLogicException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();

        GameClient client = null;
        try {
            client = new GameClient(room, userName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            client.connect();

            ServerMessageListener listener = new ServerMessageListener() {
                @Override
                public void readyCount(int current, int max) {
                    System.out.println(current + "!!!!!!!!!!!");
                }

                @Override
                public void onGameStart() {

                }

                @Override
                public void userDisconnect(String nickname) {

                }

                @Override
                public void receiveUserList(String[] nicknames) {
                    // updatePlayers(nicknames, view);
//                            for (String name : nicknames) {
//                                System.out.println(name + "!!!!!!!!");
//                            }
//                    names = nicknames;
//                    onCreate(savedInstanceState);
                    updatePlayers(nicknames, view);


//                    final Runnable r = new Runnable() {
//                        public void run() {
//                            updatePlayers(nicknames, view);
//                            System.out.println("calling");
//                        }
//                    };
//
//                    new Handler().postDelayed(r, 1000);


                }

                @Override
                public void unknownMessage(String message) {

                }
            };


            client.registerListener(listener);
            client.startReceiveLoop();
//                  client.sendReady(true);
            // TODO: display roomname and users in this fragment... and num users and ready button; how to get users
//                    System.out.println(listener.receiveUserList(););

        } catch (IOException e) {
            e.printStackTrace();
        } catch (GameLogicException e) {
            e.printStackTrace();
        }

        return view;
    }

    public void updatePlayers(String[] nicknames, View view) {

        final Runnable r = new Runnable() {
            public void run() {
                // updatePlayers(nicknames, view);
                // System.out.println("calling");


                LinearLayout layout = view.findViewById(R.id.layoutRoom);

                for (String n : nicknames) {
                    Button newbtn;

                    newbtn = new Button(getContext());
                    newbtn.setText(n);
                    layout.addView(newbtn);
                    System.out.println(n);

                }
            }
        };

        h.postDelayed(r, 1000);


//        LinearLayout layout = view.findViewById(R.id.layoutRoom);
//
//        for (String n : nicknames) {
//            Button newbtn;
//
//            newbtn = new Button(getContext());
//            newbtn.setText(n);
//            layout.addView(newbtn);
//            System.out.println(n);
//
//        }

    }


}