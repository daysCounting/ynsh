package Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.bank.root.myapplication.ListView.CardsAdapter;
import com.bank.root.myapplication.R;

import java.util.ArrayList;


/**
 * Created by root on 15-2-28.
 */
public class TabFragmentTwo extends Fragment {
    private ListView cardsList;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_two,
                container, false);
        cardsList = (ListView) rootView.findViewById(R.id.cards_list);
        setupList();
        return rootView;

    }


    private void setupList() {
        cardsList.setAdapter(createAdapter());
        cardsList.setOnItemClickListener(new ListItemClickListener());
    }

    private CardsAdapter createAdapter() {
        ArrayList<String> items = new ArrayList<String>();

        for (int i = 0; i < 100; i++) {
            items.add(i, "Detail " + i);
        }

        return new CardsAdapter(getActivity(), items,
                new ListItemButtonClickListener());
    }

    private final class ListItemButtonClickListener implements OnClickListener {
        @Override
        public void onClick(View v) {
            for (int i = cardsList.getFirstVisiblePosition(); i <= cardsList
                    .getLastVisiblePosition(); i++) {
                if (v == cardsList.getChildAt(
                        i - cardsList.getFirstVisiblePosition()).findViewById(
                        R.id.list_item_card_button_1)) {
                    // PERFORM AN ACTION WITH THE ITEM AT POSITION i
                    Toast.makeText(getActivity(),
                            "Clicked on Left Action Button of List Item " + i,
                            Toast.LENGTH_SHORT).show();
                } else if (v == cardsList.getChildAt(
                        i - cardsList.getFirstVisiblePosition()).findViewById(
                        R.id.list_item_card_button_2)) {
                    // PERFORM ANOTHER ACTION WITH THE ITEM AT POSITION i
                    Toast.makeText(getActivity(),
                            "Clicked on Right Action Button of List Item " + i,
                            Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private final class ListItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            Toast.makeText(getActivity(), "Clicked on List Item " + position,
                    Toast.LENGTH_SHORT).show();
        }
    }
}
