package com.bhushan.testapplication.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bhushan.testapplication.R;
import com.bhushan.testapplication.activity.TicketBooking;
import com.bhushan.testapplication.api.RetrofitClient;
import com.bhushan.testapplication.others.ViewDialog;
import com.bhushan.testapplication.pojo.FlightListRes;
import com.bhushan.testapplication.pojo.FlightlistItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class Flight_List extends Fragment {

    View view;
    ViewDialog progressDialog;
    RecyclerView rv_list;
    List<FlightlistItem> flightlist = new ArrayList<>();
    LinearLayout listview , ErrorView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_flight__list, container, false);
        progressDialog=new ViewDialog(getActivity());
        rv_list = view.findViewById(R.id.rv_history);
        listview = view.findViewById(R.id.l2);
        ErrorView = view.findViewById(R.id.l1);
        rv_list.setNestedScrollingEnabled(false);
        rv_list.setLayoutManager(new LinearLayoutManager(getActivity()));

        rv_list.setAdapter(new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view=LayoutInflater.from(getActivity()).inflate(R.layout.history_adapter, viewGroup,false);
                Holder holder=new Holder(view);
                return holder;
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                final Holder myHolder= (Holder) viewHolder;
                final FlightlistItem model = flightlist.get(i);
                myHolder.date.setText("Date : " + model.getFDATE());
                myHolder.from.setText(model.getFFROM());
                myHolder.to.setText(model.getFTO());
                myHolder.arrival.setText(model.getARRIVAL());
                myHolder.depar.setText(model.getDEPARTURE());
                myHolder.itemView.setTag(i);

                        myHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), TicketBooking.class);
                        intent.putExtra("fID", model.getFID());
                        intent.putExtra("aRRIVAL", model.getARRIVAL());
                        intent.putExtra("dEPARTURE", model.getDEPARTURE());
                        intent.putExtra("f_TO", model.getFTO());
                        intent.putExtra("fMODEL", model.getFMODEL());
                        intent.putExtra("eCOST", model.getECOST());
                        intent.putExtra("f_DURATION", model.getFDURATION());
                        intent.putExtra("f_FROM", model.getFFROM());
                        intent.putExtra("fDATE", model.getFDATE());
                        intent.putExtra("bCOST", model.getBCOST());
                        intent.putExtra("tOTSEAT", model.getTOTSEAT());
                        intent.putExtra("fNAME", model.getFNAME());
                        startActivity(intent);
                    }
                });

            }

            @Override
            public int getItemCount() {
                return flightlist.size();
            }
            class Holder extends RecyclerView.ViewHolder {
                TextView date,from,to,arrival,depar,cost;
                public Holder(@NonNull View itemView) {
                    super(itemView);
                    date = itemView.findViewById(R.id.date);
                    from = itemView.findViewById(R.id.from);
                    to = itemView.findViewById(R.id.to);
                    arrival = itemView.findViewById(R.id.arrival);
                    depar = itemView.findViewById(R.id.depar);
                    cost = itemView.findViewById(R.id.cost);
                }
            } }
        );

        getHistList();



        return view;
    }

    private void getHistList() {
        //data variables call
        progressDialog.show();
        Call<FlightListRes> call = RetrofitClient.getInstance().getApi().flightList();
        call.enqueue(new Callback<FlightListRes>() {
            @Override
            public void onResponse(Call<FlightListRes> call, retrofit2.Response<FlightListRes> response) {
                FlightListRes fList = response.body();
                progressDialog.dismiss();
                flightlist = fList.getFlightlist();
                rv_list.getAdapter().notifyDataSetChanged();

                if (flightlist.size() == 0) {
                    listview.setVisibility(View.GONE);
                    ErrorView.setVisibility(View.VISIBLE);
                }else {
                    listview.setVisibility(View.VISIBLE);
                    ErrorView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<FlightListRes> call, Throwable t) {
                progressDialog.dismiss();
                if (t instanceof IOException) {
                    Snackbar snackbar = Snackbar.make(view, "Internet Issue ! Failed to process your request !", Snackbar.LENGTH_LONG);
                    snackbar.show();
                } else {
                    Snackbar snackbar = Snackbar.make(view, "Data Conversion Issue ! Contact to admin", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }
        });
    }

}
