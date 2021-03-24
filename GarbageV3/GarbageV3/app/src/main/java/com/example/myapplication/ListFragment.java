package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class ListFragment extends Fragment implements Observer {

    public void update(Observable o, Object data) {
        localDB = itemsDB.getAll();
        mAdapter.notifyDataSetChanged();
    }

    private static ItemsDB itemsDB;
    private RecyclerView listThings;
    private ItemAdapter mAdapter;
    private ArrayList<Item> localDB;


    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        itemsDB = ItemsDB.get(getActivity());
        itemsDB.addObserver(this);
        localDB = itemsDB.getAll();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.fragment_list, container, false);
        listThings = v.findViewById(R.id.listItems);
        listThings.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new ItemAdapter();
        listThings.setAdapter(mAdapter);
        return v;
    }

    private class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView mWhatTextView, mWhereTextView, mNoView;

        public ItemHolder(View itemView) {
            super(itemView);
            mNoView = itemView.findViewById(R.id.item_no);
            mWhatTextView = itemView.findViewById(R.id.item_what);
            mWhereTextView = itemView.findViewById(R.id.item_where);
        }

        public void bindTogether(Item item, int position) {
            mNoView.setText(" " + position + " ");
            mWhatTextView.setText(item.getWhat());
            mWhereTextView.setText(item.getWhere());
        }

        @Override public void onClick(View v) {
            String what= (String)((TextView)v.findViewById(R.id.item_what)).getText();
            itemsDB.removeItem(what);
        }
    }

    private class ItemAdapter extends RecyclerView.Adapter<ItemHolder> {


        @NonNull @Override
        public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View v = layoutInflater.inflate(R.layout.one_row, parent, false);
            return new ItemHolder(v);
        }


        //CHECK IT LATER
        @Override public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
            Item item = localDB.get(position);
            holder.bindTogether(item, position);
        }

        @Override public int getItemCount() {
            return localDB.size();
        }
    }


}
