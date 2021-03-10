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

public class ListFragment extends Fragment {

    private static ItemsDB itemsDB;
    private RecyclerView listThings;
    private ItemAdapter mAdapter;

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        itemsDB = ItemsDB.get(getActivity());
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

    private class ItemHolder extends RecyclerView.ViewHolder {
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
    }

    private class ItemAdapter extends RecyclerView.Adapter<ItemHolder> {
        public ItemAdapter() {
        }

        @NonNull @Override
        public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View v = layoutInflater.inflate(R.layout.one_row, parent, false);
            return new ItemHolder(v);
        }

        @Override public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
            Item item = itemsDB.getAll().get(position);
            holder.bindTogether(item, position);
        }

        @Override public int getItemCount() {
            return itemsDB.size();
        }
    }


}
