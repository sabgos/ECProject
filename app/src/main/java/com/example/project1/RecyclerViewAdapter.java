package com.example.project1;

import android.app.DatePickerDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project1.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<String> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private static final String TAG = "ButtonClicked";
    private int mYear, mMonth, mDay;
    String mdate;

    // data is passed into the constructor
    RecyclerViewAdapter(Context context, List<String> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_row, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String date = mData.get(position);
        holder.txtDate.setText(date);
    }
    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtDate;
        Button btnDatePicker;

        ViewHolder(View itemView) {
            super(itemView);

            txtDate = itemView.findViewById(R.id.datePicker1);
            btnDatePicker = itemView.findViewById(R.id.date_btn);

            btnDatePicker.setOnClickListener(this);
            //itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            //  if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
            Log.e(TAG, "Hello Sab");
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(view.getRootView().getContext(), new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year,
                                      int monthOfYear, int dayOfMonth) {
                    view.setMinDate(System.currentTimeMillis() - 1000);

                   // Toast.makeText(view.getContext(), mDay+" "+mMonth+" "+mYear+"--"+dayOfMonth+"."+monthOfYear+"."+year, Toast.LENGTH_LONG).show();

                    SimpleDateFormat sdfo= new SimpleDateFormat("yyyy-MM-dd");
                    Date d1 = null;
                    try {
                        d1 = sdfo.parse(mYear+"-"+mMonth+"-"+mDay);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Date d2 = null;
                    try {
                        d2 = sdfo.parse(year+"-"+monthOfYear+"-"+dayOfMonth);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    if(d1.compareTo(d2) > 0)
                    {
                        Toast.makeText(view.getContext(), "Please select a correct date!", Toast.LENGTH_SHORT).show();
                        txtDate.setText("");
                    }


                    else {
                        txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        mdate = txtDate.getText().toString();
                        mData.set(getAdapterPosition(), mdate);
                    }

                }
            }, mYear, mMonth, mDay);
            datePickerDialog.show();
           // btnDatePicker.setVisibility(View.GONE);
            //txtDate.setVisibility(View.VISIBLE);
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