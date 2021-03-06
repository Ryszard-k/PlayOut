package com.example.clientapp;

import static com.example.clientapp.Authentication.Prefs.MyPREFERENCES;
import static com.example.clientapp.Authentication.Prefs.Username;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.clientapp.BasketballEvent.Basketball;
import com.example.clientapp.Football.APIClient;
import com.example.clientapp.Football.ActiveEvents;
import com.example.clientapp.Football.Model.FootballEvent;
import com.example.clientapp.VolleyballEvent.Volleyball;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyEvents#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyEvents extends Fragment implements EventClickListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView rvME;
    private List<FootballEvent> footballs = new ArrayList<>();
    private List<Basketball> basketballs = new ArrayList<>();
    private List<Volleyball> volleyballs = new ArrayList<>();

    public MyEvents() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FootballEventActivity.
     */
    // TODO: Rename and change types and number of parameters
    public static MyEvents newInstance(String param1, String param2) {
        MyEvents fragment = new MyEvents();
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
        View view = inflater.inflate(R.layout.fragmnet_my_events, container, false);

        rvME = view.findViewById(R.id.RVFootballEvent);
        rvME.setLayoutManager(new LinearLayoutManager(view.getContext()));
        rvME.addItemDecoration(new DividerItemDecoration(view.getContext(), DividerItemDecoration.VERTICAL));
        EventClickListener eventClickListener = this;

        SharedPreferences sharedpreferences = container.getContext().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        Thread thread = new Thread(() -> {
            Call<EventsWrapper> call = APIClient.createService(EventAPI.class)
                    .getMyActiveEvent(sharedpreferences.getString(Username, Username));
            call.enqueue(new Callback<EventsWrapper>() {
                @Override
                public void onResponse(Call<EventsWrapper> call, Response<EventsWrapper> response) {
                    if (response.isSuccessful()) {
                        assert response.body() != null;

                        footballs.clear();
                        basketballs.clear();
                        volleyballs.clear();

                        footballs = response.body().getEventsWrapperWithFootball();
                        basketballs = response.body().getEventsWrapperWithBasketball();
                        volleyballs = response.body().getEventsWrapperWithVolleyball();

                        rvME.setAdapter(new ActiveEvents(footballs, basketballs, volleyballs, eventClickListener));
                        Log.d("ActiveFootballEvents", "Registered Successfully");
                    }
                }

                @Override
                public void onFailure(Call<EventsWrapper> call, Throwable t) {
                    Log.d("myEvents", Log.getStackTraceString(t));
                }
            });
        });
        thread.start();

        return view;
    }



    @Override
    public void onItemClick(int position, View view) {
        ActiveEvents activeEvents = (ActiveEvents) rvME.getAdapter();
        assert activeEvents != null;
        Object o = activeEvents.getItemByPosition(position);
        startActivity(new Intent(getContext(), MyEventDetails.class).putExtra("object", (Serializable) o));
    }


}