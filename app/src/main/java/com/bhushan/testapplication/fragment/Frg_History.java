package com.bhushan.testapplication.fragment;

import android.app.ActivityOptions;
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
import com.bhushan.testapplication.activity.TicketSummary;
import com.bhushan.testapplication.api.RetrofitClient;
import com.bhushan.testapplication.others.Global;
import com.bhushan.testapplication.others.ViewDialog;
import com.bhushan.testapplication.pojo.HistoryItem;
import com.bhushan.testapplication.pojo.HistoryResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class Frg_History extends Fragment {

    View view;
    ViewDialog progressDialog;
    public static RecyclerView rv_list;
    public static List<HistoryItem> historylist = new ArrayList<>();
    LinearLayout listview , ErrorView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_frg__history, container, false);
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
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
                final Holder myHolder= (Holder) viewHolder;
                final HistoryItem model = historylist.get(i);
                myHolder.date.setText("Date : " + model.getFdate());
                myHolder.itemView.setTag(i);
                myHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), TicketSummary.class);
                        intent.putExtra("uid", model.getUid());
                        intent.putExtra("tcode", model.getTCODE());
                        //intent.putExtra("cost", model.getTcost());
                        //intent.putExtra("passenger", model.getPassenger());
                        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getActivity(), R.anim.trans_left_in, R.anim.trans_left_out).toBundle();
                        startActivity(intent, bndlanimation);
                    }
                });

            }

            @Override
            public int getItemCount() {
                return historylist.size();
            }
            class Holder extends RecyclerView.ViewHolder {
                TextView date;
                public Holder(@NonNull View itemView) {
                    super(itemView);
                    date = itemView.findViewById(R.id.date);
                }
            } }
        );

        getHistList();
        return view;
    }

    private void getHistList() {
        //data variables call
        progressDialog.show();
        Call<HistoryResponse> call = RetrofitClient.getInstance().getApi().ft_history(Global.uid);
        call.enqueue(new Callback<HistoryResponse>() {
            @Override
            public void onResponse(Call<HistoryResponse> call, retrofit2.Response<HistoryResponse> response) {
                HistoryResponse fList = response.body();
                progressDialog.dismiss();
                historylist = fList.getHistory();
                rv_list.getAdapter().notifyDataSetChanged();

                if (historylist.size() == 0) {
                    listview.setVisibility(View.GONE);
                    ErrorView.setVisibility(View.VISIBLE);
                }else {
                    listview.setVisibility(View.VISIBLE);
                    ErrorView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<HistoryResponse> call, Throwable t) {
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
