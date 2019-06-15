package com.bhushan.testapplication.activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bhushan.testapplication.R;

public class TicketBooking extends AppCompatActivity {

    TextView date,from,to,arrival,depar,cost,airline,modelname,totseats,duration,qty,price;
    ImageView minus,plus;
    int totprice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_booking);

        date = findViewById(R.id.date);
        from = findViewById(R.id.from);
        to = findViewById(R.id.to);
        arrival = findViewById(R.id.arrival);
        depar = findViewById(R.id.depar);
        cost = findViewById(R.id.cost);
        airline = findViewById(R.id.fname);
        modelname = findViewById(R.id.modelname);
        totseats = findViewById(R.id.totseats);
        duration = findViewById(R.id.duration);
        qty = findViewById(R.id.qty);
        minus = findViewById(R.id.minus);
        plus = findViewById(R.id.plus);
        price = findViewById(R.id.price);

        date.setText("Date : " + getIntent().getStringExtra("fDATE"));
        from.setText(getIntent().getStringExtra("f_FROM"));
        to.setText(getIntent().getStringExtra("f_TO"));
        arrival.setText(getIntent().getStringExtra("aRRIVAL"));
        depar.setText(getIntent().getStringExtra("dEPARTURE"));
        cost.setText("₹. "+getIntent().getStringExtra("eCOST"));
        airline.setText(getIntent().getStringExtra("fNAME"));
        modelname.setText(getIntent().getStringExtra("fMODEL"));
        totseats.setText(getIntent().getStringExtra("tOTSEAT"));
        duration.setText(getIntent().getStringExtra("f_DURATION"));
        qty.setText("1");
        price.setText("₹. "+getIntent().getStringExtra("eCOST"));


    }
}
