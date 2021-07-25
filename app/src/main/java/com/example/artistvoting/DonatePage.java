package com.example.artistvoting;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

public class DonatePage extends AppCompatActivity implements PaymentResultListener {
    //Initialize variable
    Button btPay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate_page);

        //Assign variable
        btPay = findViewById(R.id.bt_pay);

        //Initialize amount
        String sAmount = "100";

        //Convert and round off
        int amount = Math.round(Float.parseFloat(sAmount) * 100);
        btPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Initialize razorpay checkout
                Checkout checkout = new Checkout();
                //Set key id
                checkout.setKeyID("rzp_test_et4uScu8ZwxN7f");
                //Set Image
                checkout.setImage(R.drawable.rzp_logo);
                //initialize json object
                JSONObject object = new JSONObject();

                try {
                    //Put name
                    object.put("name", "Donate");
                    //put description
                    object.put("description","Test Payment");
                    //Put theme color
                    object.put("theme.color","#8739F9");
                    //Put currency unit
                    object.put("currency","INR");
                    //Put amount
                    object.put("amount",amount);
                    //Put mobile number
                    object.put("prefill.contact","Enter Mobile");
                    //Put email
                    object.put("prefill.email"
                            ,"Enter E-mail");
                    //Open razorpay checkout activaty
                    checkout.open(DonatePage.this,object);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

    }

    @Override
    public void onPaymentSuccess(String s) {
        //Initialize alert dialogue
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //Set title
        builder.setTitle("Payment ID");
        //Set message
        builder.setMessage(s);
        //Show alert
        builder.show();

    }

    @Override
    public void onPaymentError(int i, String s) {
        //Display toast
        Toast.makeText(getApplicationContext()
                ,s,Toast.LENGTH_SHORT).show();

    }
}