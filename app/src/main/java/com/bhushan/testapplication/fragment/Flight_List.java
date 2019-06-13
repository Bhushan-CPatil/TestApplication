package com.bhushan.testapplication.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bhushan.testapplication.R;
import com.bhushan.testapplication.others.ViewDialog;

public class Flight_List extends Fragment {

    View view;
    ViewDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_flight__list, container, false);
        progressDialog=new ViewDialog(getActivity());




        return view;
    }

}
