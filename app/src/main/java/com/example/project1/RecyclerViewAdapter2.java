package com.example.project1;

import android.app.DatePickerDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project1.R;

import java.util.Calendar;
import java.util.List;

public class RecyclerViewAdapter2 extends RecyclerView.Adapter<RecyclerViewAdapter2.ViewHolder> {

    private List<String> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private static final String TAG = "ButtonClicked";
    private int mYear, mMonth, mDay;
    String mName;

    // data is passed into the constructor
    RecyclerViewAdapter2(Context context, List<String> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_row2, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String name = mData.get(position);
        holder.txtNum.setText(name);
    }
    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtNum;
        Button btnDatePicker;


        ViewHolder(View itemView) {
            super(itemView);

            txtNum = itemView.findViewById(R.id.textView6);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            //  if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
            //mName= txtName.getText().toString();
            //Log.e(TAG, "Name="+ mName);
            //mData.set(getAdapterPosition(),mName);
        }
    }
    // convenience method for getting data at click position
    String getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}