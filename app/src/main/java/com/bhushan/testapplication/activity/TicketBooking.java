package com.bhushan.testapplication.activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bhushan.testapplication.R;
import com.bhushan.testapplication.pojo.FormElementsItem;
import com.bhushan.testapplication.pojo.FormList;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TicketBooking extends AppCompatActivity {

    TextView date,from,to,arrival,depar,cost,airline,modelname,totseats,duration,qty,price;
    ImageView minus,plus;
    Button next;
    int totprice,totalseats,amount;
    int minteger = 1;
    String s;
    List<FormElementsItem> formlistelm = new ArrayList<>();
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
        next = findViewById(R.id.next);

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
        qty.setText(""+minteger);
        totalseats = Integer.parseInt(getIntent().getStringExtra("tOTSEAT"));
        amount = Integer.parseInt(getIntent().getStringExtra("eCOST"));
        totprice = minteger * Integer.parseInt(getIntent().getStringExtra("eCOST"));
        price.setText("Total Price: ₹. "+totprice);

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(minteger<totalseats) {
                    minteger = minteger + 1;
                    qty.setText(String.valueOf(minteger));
                    int cal2 = amount * minteger;
                    s = String.valueOf(cal2);
                    price.setText("Total Price: ₹ "+s);
                }

            }
        });
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(minteger>1) {
                    minteger = minteger - 1;
                    qty.setText(String.valueOf(minteger));
                    int cal2 = amount * minteger;
                    s = String.valueOf(cal2);
                    price.setText("Total Price: ₹ "+s);
                }
                else {
                    qty.setText("1");
                }

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int noofcards = Integer.parseInt(qty.getText().toString().trim());
                String jsnstr = "{\"FormElements\" : [";
                for(int k = 0 ; k<noofcards; k++){
                    if(k==0){
                        jsnstr += "{\"name\" : \"\",\"gender\" : \"\",\"age\" : \"\",\"mobileno\" : \"\"}";
                    }else{
                        jsnstr += ",{\"name\" : \"\",\"gender\" : \"\",\"age\" : \"\",\"mobileno\" : \"\"}";
                    }
                }
                jsnstr += "]}";
                Gson gson = new Gson();
                FormList nameList = gson.fromJson(jsnstr, FormList.class);
                formlistelm = nameList.getFormElements();
            }
        });

    }
}
