package com.bhushan.testapplication.activity;

import android.app.ActivityOptions;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
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
import com.bhushan.testapplication.api.RetrofitClient;
import com.bhushan.testapplication.fragment.Flight_List;
import com.bhushan.testapplication.others.Global;
import com.bhushan.testapplication.others.ViewDialog;
import com.bhushan.testapplication.pojo.DefaultResponse;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TicketBooking extends AppCompatActivity {

    TextView date,from,to,arrival,depar,cost,airline,modelname,totseats,duration,qty,price;
    ImageView minus,plus;
    Button next;
    int totprice,totalseats,amount;
    ViewDialog progressDialog;
    int minteger = 1,position;
    String s,fid;
    List<FormElementsItem> formlistelm = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_booking);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        progressDialog=new ViewDialog(TicketBooking.this);
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
        fid =getIntent().getStringExtra("fID");
        position = Integer.parseInt(getIntent().getStringExtra("position"));
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
                            rowViewHolder.female.setChecked(false);
                        }else{
                            model.setGender("Female");
                            rowViewHolder.female.setChecked(true);
                        }
                    }
                });

                rowViewHolder.female.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked) {
                            model.setGender("Female");
                            rowViewHolder.male.setChecked(false);
                        }else{
                            model.setGender("Male");
                            rowViewHolder.male.setChecked(true);
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
                recyclerView.clearFocus();
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

    private void bookTicket(final Dialog dialog) {
        for (int i=0;i<formlistelm.size();i++){
            FormElementsItem chk = formlistelm.get(i);
            if(chk.getName() != null && chk.getName().equalsIgnoreCase("")){
                Toast.makeText(TicketBooking.this, "Form is incomplete please fill form completetyly", Toast.LENGTH_LONG).show();
                return;
            }else if(chk.getAge() != null && chk.getAge().equalsIgnoreCase("")){
                Toast.makeText(TicketBooking.this, "Form is incomplete please fill form completetyly", Toast.LENGTH_LONG).show();
                return;
            }else if(chk.getMobno() != null && chk.getMobno().equalsIgnoreCase("")){
                Toast.makeText(TicketBooking.this, "Form is incomplete please fill form completetyly", Toast.LENGTH_LONG).show();
                return;
            }else if(chk.getGender() != null && chk.getGender().equalsIgnoreCase("")){
                Toast.makeText(TicketBooking.this, "Form is incomplete please fill form completetyly", Toast.LENGTH_LONG).show();
                return;
            }
        }

        Gson gson = new GsonBuilder().create();
        JsonArray myCustomArray = gson.toJsonTree(formlistelm).getAsJsonArray();
        //todo call Api and dismiss dialog
        progressDialog.show();
        Call<DefaultResponse> call = RetrofitClient.getInstance().getApi().ft_addEntryInDB(Global.uid,fid,myCustomArray.toString());
        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                progressDialog.dismiss();
                final DefaultResponse res = response.body();
                dialog.dismiss();
                if(res.isAccess()){
                    if(!res.getErrormsg().equalsIgnoreCase("")){
                        int ogseat = Integer.parseInt(totseats.getText().toString());
                        int bookedseat = Integer.parseInt(qty.getText().toString());
                        String calseats = Integer.toString(ogseat-bookedseat);
                        totseats.setText(calseats);

                        AlertDialog.Builder builder = new AlertDialog.Builder(TicketBooking.this);
                        builder.setCancelable(false);
                        builder.setTitle("Success.");
                        builder.setMessage("Click next to view summary ?");
                        builder.setPositiveButton("NEXT", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(TicketBooking.this,TicketSummary.class);
                                intent.putExtra("uid", Global.uid);
                                intent.putExtra("tcode", res.getErrormsg());
                                Bundle bndlanimation = ActivityOptions.makeCustomAnimation(TicketBooking.this, R.anim.trans_left_in, R.anim.trans_left_out).toBundle();
                                startActivity(intent, bndlanimation);
                            }
                        });
                        AlertDialog dialogx = builder.create();
                        dialogx.show();
                    }else{
                        Toast.makeText(TicketBooking.this, "Something went wrong !", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(TicketBooking.this, "Something went wrong !", Toast.LENGTH_LONG).show();
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
        TicketBooking.this.overridePendingTransition(R.anim.trans_right_in,R.anim.trans_right_out);
    }
}
