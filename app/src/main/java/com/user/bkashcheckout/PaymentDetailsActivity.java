package com.user.bkashcheckout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class PaymentDetailsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private Spinner category, sub_category;
    private ImageView back;
    private EditText date, orderid,bkashno,trasnid;
    private RadioGroup radioGroup;
    private CardView save;
    String[] Category = {"Bkash","Nogod"};
    String CategoryText1, CategoryText2, method;
    String[] Sub_category = {"Cash On Delivery", "Online"};
    private FirebaseFirestore db;
    private DocumentReference document_reference;
    private CollectionReference feed;
    private FeedAdapter adapter;

    private CardView update, delete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paymentdetails);
        update = findViewById(R.id.update);
        delete = findViewById(R.id.delete);
        category = findViewById(R.id.category);
        sub_category = findViewById(R.id.sub_category);
        back = findViewById(R.id.back);
        date = findViewById(R.id.etdate);
        orderid= findViewById(R.id.et_orderid);
        trasnid = findViewById(R.id.et_transcationid);
        bkashno = findViewById(R.id.et_bkasno);
        save = findViewById(R.id.save);
        //Spinner for Category
        category.setOnItemSelectedListener(this);


        ArrayAdapter ad = new ArrayAdapter(this, android.R.layout.simple_spinner_item, Category);
        ArrayAdapter ad2 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, Sub_category);

        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(ad);

        ad2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sub_category.setAdapter(ad2);



        //Database
        FirebaseApp.initializeApp(this);
        db = FirebaseFirestore.getInstance();
        document_reference = db.collection("Report").document();

//...............................

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        loadData();
        update();

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                document_reference.delete();
                finish();
                Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void update() {

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Date = date.getText().toString();
                String BkashNo = bkashno.getText().toString();
                String OrderID = orderid.getText().toString();
                String TransID = trasnid.getText().toString();
                String Category = CategoryText1;


                if (!Date.isEmpty()  && !OrderID.isEmpty()) {

                    final String id = document_reference.getId();
                    Map<String, Object> userMap = new HashMap<>();

                    userMap.put("date", Date);
                    userMap.put("orderid", OrderID);
                    userMap.put("bkashno",BkashNo);
                    userMap.put("category",Category);
                    userMap.put("transcationid",TransID);
                    userMap.put("id", id);
                    userMap.put("timestamp", FieldValue.serverTimestamp());


                    document_reference.update(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(getApplicationContext(), "Updating..", Toast.LENGTH_SHORT).show();
                            Intent saved = new Intent(getApplicationContext(), Confirmorder.class);
                            startActivity(saved);

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "You must fill all the fields!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void loadData() {
        document_reference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                if (documentSnapshot.exists()) {


                    String BkashNo = documentSnapshot.getString("bkashno");
                    String Date = documentSnapshot.getString("date");
                    String OrderID = documentSnapshot.getString("orderid");
                    String TranscationID = documentSnapshot.getString("transcationid");
                    String Category = documentSnapshot.getString("category");


                    bkashno.setText(BkashNo);
                    date.setText(Date);
                    orderid.setText(OrderID);
                    trasnid.setText(TranscationID);



                    //Spinner
                    if (Category.equals("Cash On Delivery")) {
                        sub_category.setSelection(0);
                    } else if (Category.equals("Online")) {
                        sub_category.setSelection(1);
                    }
//.............................


//.....................................

                } else {
                    Toast.makeText(PaymentDetailsActivity.this, "Something wrong!", Toast.LENGTH_LONG).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        CategoryText1 = Sub_category[position];

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
