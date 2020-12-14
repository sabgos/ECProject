package com.ElderCare.ElderCareFD.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ElderCare.ElderCareFD.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class RecyclerViewAdapterTwoPersons extends RecyclerView.Adapter<RecyclerViewAdapterTwoPersons.ViewHolder> {

    private List<String> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private static final String TAG = "ButtonClicked";
    private int mYear, mMonth, mDay;
    String mName;

    // data is passed into the constructor
    RecyclerViewAdapterTwoPersons(Context context, List<String> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_row_two_select_persons, parent, false);
        return new ViewHolder(view);


    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final String num = mData.get(position);
        holder.txtNum.setText(num);

        Log.d(TAG,"position="+position);
        if (num.equals("1")) {
            FirebaseAuth firebaseAuth;
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference myRef = database.getReference();
            final String UID = user.getUid();


            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                private static final String TAG = "Hi";

                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String name = snapshot.child("users").child(UID).child("username").getValue(String.class);

                    String diabTxt = snapshot.child("users").child(UID).child("diabetic").getValue(String.class);
                    String vegTxt = snapshot.child("users").child(UID).child("veg").getValue(String.class);
                    holder.txtName.setText(name+num);

                    if (diabTxt.equalsIgnoreCase("Diabetic"))
                        holder.diab.setChecked(true);
                    else
                        holder.diab.setChecked(false);

                    if (vegTxt.equalsIgnoreCase("Vegetarian"))
                        holder.veg.setChecked(true);
                    else
                        holder.veg.setChecked(false);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }
    }
    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtNum;
        EditText txtName;
        Button btnDatePicker;
        CheckBox diab,veg;

        ViewHolder(View itemView) {
            super(itemView);

            txtNum = itemView.findViewById(R.id.textView6);
            txtName = itemView.findViewById(R.id.textView6Name);
            diab = itemView.findViewById(R.id.radioButton2);
            veg = itemView.findViewById(R.id.radioButton);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
             // if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
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