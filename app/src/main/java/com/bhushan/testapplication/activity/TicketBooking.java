package com.bhushan.testapplication.activity;

import android.app.ActivityOptions;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bhushan.testapplication.R;
import com.bhushan.testapplication.pojo.FormElementsItem;
import com.bhushan.testapplication.pojo.FormList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
                
                openPopup();
            }
        });

    }

    private void openPopup() {
        final Dialog dialog = new Dialog(TicketBooking.this);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.passenger_form_popup);
        CardView buttonNo = dialog.findViewById(R.id.no);
        CardView buttonYes = dialog.findViewById(R.id.yes);
        final RecyclerView recyclerView = dialog.findViewById(R.id.passengerpopuplist);
        recyclerView.setLayoutManager(new LinearLayoutManager(TicketBooking.this));
        recyclerView.setAdapter(new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view= LayoutInflater.from(TicketBooking.this).inflate(R.layout.data_form_adapter, viewGroup,false);
                Holder holder=new Holder(view);
                return holder;
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                final Holder rowViewHolder= (Holder) viewHolder;
                final FormElementsItem model = formlistelm.get(i);

                rowViewHolder.name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View view, boolean hasFocus) {
                        if (!hasFocus) {
                            model.setName(rowViewHolder.name.getText().toString());
                        }

                    }
                });

                rowViewHolder.age.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View view, boolean hasFocus) {
                        if (!hasFocus) {
                            model.setAge(rowViewHolder.age.getText().toString());
                        }

                    }
                });

                rowViewHolder.phno.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View view, boolean hasFocus) {
                        if (!hasFocus) {
                            model.setMobno(rowViewHolder.phno.getText().toString());
                        }

                    }
                });

                rowViewHolder.male.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked) {
                            model.setGender("Male");
                        }else{
                            model.setGender("");
                        }
                    }
                });

                rowViewHolder.female.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked) {
                            model.setGender("Female");
                        }else{
                            model.setGender("");
                        }
                    }
                });
            }

            @Override
            public int getItemCount() {
                return formlistelm.size();
            }
            class Holder extends RecyclerView.ViewHolder {
                public EditText name,age,phno;
                public CheckBox male,female;

                public Holder(@NonNull View itemView) {
                    super(itemView);
                    name = itemView.findViewById(R.id.name);
                    phno = itemView.findViewById(R.id.phno);
                    age = itemView.findViewById(R.id.age);
                    male = itemView.findViewById(R.id.male);
                    female = itemView.findViewById(R.id.female);
                }
            } }
        );

        recyclerView.getAdapter().notifyDataSetChanged();
        buttonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                formlistelm.clear();
                dialog.dismiss();
            }
        });
        buttonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.clearFocus();
                bookTicket(dialog);
                //dialog.dismiss();
            }
        });
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    private void bookTicket(Dialog dialog) {
        for (int i=0;i<formlistelm.size();i++){
            FormElementsItem chk = formlistelm.get(i);
            if(chk.getName().isEmpty()){
                Toast.makeText(TicketBooking.this, "Form is incomplete please fill form completetyly", Toast.LENGTH_LONG).show();
                return;
            }else if(chk.getAge().isEmpty()){
                Toast.makeText(TicketBooking.this, "Form is incomplete please fill form completetyly", Toast.LENGTH_LONG).show();
                return;
            }else if(chk.getMobno().isEmpty()){
                Toast.makeText(TicketBooking.this, "Form is incomplete please fill form completetyly", Toast.LENGTH_LONG).show();
                return;
            }else if(chk.getGender().isEmpty()){
                Toast.makeText(TicketBooking.this, "Form is incomplete please fill form completetyly", Toast.LENGTH_LONG).show();
                return;
            }
        }

        Gson gson = new GsonBuilder().create();
        JsonArray myCustomArray = gson.toJsonTree(formlistelm).getAsJsonArray();
        //todo call Api and dismiss dialog
    }
}
