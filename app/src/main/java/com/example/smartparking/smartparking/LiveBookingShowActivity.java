package com.example.smartparking.smartparking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LiveBookingShowActivity extends AppCompatActivity {

    DatabaseReference databaseReference;

    private RecyclerView mBlogeList;
    private ImageView priviousPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_booking_show);

        databaseReference =FirebaseDatabase.getInstance().getReference().child("User").child("Requestparking")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        databaseReference.keepSynced(true);
        mBlogeList =(RecyclerView)findViewById(R.id.myRecycleview);
        mBlogeList.setHasFixedSize(true);
        mBlogeList.setLayoutManager(new LinearLayoutManager(this));

        priviousPage = findViewById(R.id.prePage);
        priviousPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LiveBookingShowActivity.this,HomeMainActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();


        FirebaseRecyclerAdapter<Booking,WordViewHolde> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Booking, LiveBookingShowActivity.WordViewHolde>
                (Booking.class,R.layout.showdatalist,LiveBookingShowActivity.WordViewHolde.class,databaseReference) {
            @Override
            protected void populateViewHolder(LiveBookingShowActivity.WordViewHolde viewHolder, Booking model, int position) {

                viewHolder.setMDate(model.getLdate());
                viewHolder.setMTime(model.getLtime());
                viewHolder.setName(model.getUname());
                viewHolder.setStarttime(model.getStarttime());
                viewHolder.setEndTime(model.getEndtime());
                viewHolder.setOtp(model.getOtp());

            }
        };

        mBlogeList.setAdapter(firebaseRecyclerAdapter);
/*
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for(DataSnapshot wordSnapshot : dataSnapshot.getChildren()){

                    Word word = wordSnapshot.getValue(Word.class);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        */

    }

    public static class WordViewHolde extends RecyclerView.ViewHolder
    {
        View mView;
        public WordViewHolde(View itemView)
        {
            super(itemView);
            mView=itemView;
        }
        public void setMDate(String date){
            TextView post_date=(TextView)mView.findViewById(R.id.editDate);
            post_date.setText(date);
        }
        public void setMTime(String time){
            TextView post_time=(TextView)mView.findViewById(R.id.editTime);
            post_time.setText(time);
        }
        public void setName(String name){
            TextView post_time=(TextView)mView.findViewById(R.id.editName);
            post_time.setText(name);
        }
        public void setStarttime(String startTime){
            TextView post_correct=(TextView)mView.findViewById(R.id.editStarttime);
            post_correct.setText(startTime);
        }
        public void setEndTime(String endTime){
            TextView post_incorrect=(TextView)mView.findViewById(R.id.editendTime);
            post_incorrect.setText(endTime);
        }
        public void setOtp(String otp){
            TextView post_correct=(TextView)mView.findViewById(R.id.edidOtp);
            post_correct.setText(otp);
        }



    }
}