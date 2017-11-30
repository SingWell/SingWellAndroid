package com.example.vbarboza.singwell2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by evaramirez on 11/20/17.
 * Still need to connect to backend and obtain list of choirs per specific organization
 * Following mitchtabian instagram tutorials https://www.youtube.com/watch?v=Cdn0jEFW6FM
 */

public class ChoirListLFragment extends Fragment{
    private static final String TAG = "ChoirListFragment";

    private ListView choirsListView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_choir_list_l,container,false);
        choirsListView =  view.findViewById(R.id.listView);

        //Array of cards
        ArrayList<Card> list = new ArrayList<>();

        //Add cards to array list, REMOVE these cards and update array with choirs per specifc organization
        list.add(new Card("drawable://" + R.drawable.iceland, "Traditional Choir"));
        list.add(new Card("drawable://" + R.drawable.heaven, "Choir 2"));
        list.add(new Card("drawable://" + R.drawable.milkyway, "Choir 3"));
        list.add(new Card("drawable://" + R.drawable.national_park, "Choir 4"));
        list.add(new Card("drawable://" + R.drawable.switzerland, "Choir 5"));

        CustomListAdapter adapter = new CustomListAdapter(getActivity(), R.layout.card_layout_main, list);
        choirsListView.setAdapter(adapter);

        return view;
    }
}
