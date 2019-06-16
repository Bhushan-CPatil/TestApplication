package com.bhushan.testapplication.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bhushan.testapplication.R;
import com.bhushan.testapplication.api.RetrofitClient;
import com.bhushan.testapplication.others.Global;
import com.bhushan.testapplication.others.ViewDialog;
import com.bhushan.testapplication.pojo.FormElementsItem;
import com.bhushan.testapplication.pojo.HistoryItem;
import com.bhushan.testapplication.pojo.SummaryItem;
import com.bhushan.testapplication.pojo.SummaryResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class TicketSummary extends AppCompatActivity {

    String tcode,uid;
    int passenger = 1,amount;
    ViewDialog progressDialog;
    RelativeLayout rlt;
    public static RecyclerView rv_list;
    List<SummaryItem> summlist = new ArrayList<>();
    LinearLayout listview , ErrorView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_summary);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        progressDialog=new ViewDialog(TicketSummary.this);
        tcode = getIntent().getStringExtra("tcode");
        uid = getIntent().getStringExtra("uid");
        rv_list = findViewById(R.id.rv_summ);
        rlt = findViewById(R.id.rlt);
        listview = findViewById(R.id.l2);
        ErrorView = findViewById(R.id.l1);
        rv_list.setNestedScrollingEnabled(false);
        rv_list.setLayoutManager(new LinearLayoutManager(TicketSummary.this));

        rv_list.setAdapter(new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view= LayoutInflater.from(TicketSummary.this).inflate(R.layout.summary_adapter, viewGroup,false);
                Holder holder=new Holder(view);
                return holder;
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
                final Holder myHolder= (Holder) viewHolder;
                final SummaryItem model = summlist.get(i);
                myHolder.passengerno.setText("Passenger - " + passenger++);
                myHolder.name.setText(model.getTNAME());
                myHolder.gender.setText(model.getTGENDER());
                myHolder.age.setText(model.getTAGE());
                myHolder.phno.setText(model.getTMOBNO());
                myHolder.airline.setText(model.getFNAME());
                myHolder.modelname.setText(model.getFMODEL());
                myHolder.tid.setText(model.getTCODE());
                myHolder.seatno.setText(model.getSEATNO());
                myHolder.from.setText(model.getFFROM());
                myHolder.to.setText(model.getFTO());
                myHolder.arrival.setText(model.getARRIVAL());
                myHolder.depar.setText(model.getDEPARTURE());
                myHolder.cost.setText(model.getECOST());
                myHolder.duration.setText(model.getFDURATION());
            }

            @Override
            public int getItemCount() {
                return summlist.size();
            }
            class Holder extends RecyclerView.ViewHolder {
                TextView passengerno,name,gender,age,phno,tid,from,to,arrival,depar,cost,airline,modelname,seatno,duration;
                public Holder(@NonNull View itemView) {
                    super(itemView);
                    passengerno = itemView.findViewById(R.id.passengerno);
                    name = itemView.findViewById(R.id.name);
                    gender = itemView.findViewById(R.id.gender);
                    age = itemView.findViewById(R.id.age);
                    phno = itemView.findViewById(R.id.phno);
                    airline = itemView.findViewById(R.id.fname);
                    modelname = itemView.findViewById(R.id.fmodel);
                    tid = itemView.findViewById(R.id.tid);
                    seatno = itemView.findViewById(R.id.seatno);
                    from = itemView.findViewById(R.id.from);
                    to = itemView.findViewById(R.id.to);
                    arrival = itemView.findViewById(R.id.arrival);
                    depar = itemView.findViewById(R.id.depar);
                    cost = itemView.findViewById(R.id.cost);
                    duration = itemView.findViewById(R.id.duration);
                }
            } }
        );

        getSummList();
    }

    private void getSummList() {
        progressDialog.show();
        Call<SummaryResponse> call = RetrofitClient.getInstance().getApi().getTicketSummary(uid,tcode);
        call.enqueue(new Callback<SummaryResponse>() {
            @Override
            public void onResponse(Call<SummaryResponse> call, retrofit2.Response<SummaryResponse> response) {
                SummaryResponse fList = response.body();
                progressDialog.dismiss();
                summlist = fList.getSummary();
                rv_list.getAdapter().notifyDataSetChanged();

                if (summlist.size() == 0) {
                    listview.setVisibility(View.GONE);
                    ErrorView.setVisibility(View.VISIBLE);
                }else {
                    listview.setVisibility(View.VISIBLE);
                    ErrorView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<SummaryResponse> call, Throwable t) {
                progressDialog.dismiss();
                if (t instanceof IOException) {
                    Snackbar snackbar = Snackbar.make(rlt, "Internet Issue ! Failed to process your request !", Snackbar.LENGTH_LONG);
                    snackbar.show();
                } else {
                    Snackbar snackbar = Snackbar.make(rlt, "Data Conversion Issue ! Contact to admin", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        } return true;
    }

    @Override
    public void onBackPressed() {
        finish();
        TicketSummary.this.overridePendingTransition(R.anim.trans_right_in,R.anim.trans_right_out);
    }
}
