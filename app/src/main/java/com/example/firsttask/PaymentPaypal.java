package com.example.firsttask;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.braintreepayments.api.dropin.DropInRequest;
import com.braintreepayments.api.dropin.DropInResult;
import com.braintreepayments.api.models.PaymentMethodNonce;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class PaymentPaypal extends AppCompatActivity {

    private static final int  REEQUEST_CODE=1234;
    String token;
    Button btn_pay;
    EditText edt_amnt;
    LinearLayout group_waiting,group_payment;

    CompositeDisposable compositeDisposable=new CompositeDisposable();
    IBrainTreeApi myApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_paypal);

        myApi=RetrofitClient.getInstance().create(IBrainTreeApi.class);

        group_payment=(LinearLayout)findViewById(R.id.payament_group);
        group_waiting=(LinearLayout)findViewById(R.id.waiting_group);
        btn_pay=(Button) findViewById(R.id.btn_pay);
        edt_amnt=(EditText)findViewById(R.id.edt_amnt);

        btn_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitPayment();
            }
        });

        compositeDisposable.add(myApi.getToken().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<BraintreeToken>() {
                       @Override
                       public void accept(BraintreeToken braintreeToken) throws Exception {
                            if (braintreeToken.isSuccess()){
                                group_waiting.setVisibility(View.INVISIBLE);
                                group_payment.setVisibility(View.VISIBLE);
                                token=braintreeToken.getClientToken();

                            }
                       }
                   }, new Consumer<Throwable>() {
                       @Override
                       public void accept(Throwable throwable) throws Exception {
                            Toast.makeText(PaymentPaypal.this,"Error Occur"+throwable.getMessage(),Toast.LENGTH_SHORT).show();
                       }
        }));
    }

    private void submitPayment(){
        DropInRequest dropInRequest=new DropInRequest().clientToken(token);
        startActivityForResult(dropInRequest.getIntent(this),REEQUEST_CODE);
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REEQUEST_CODE){
            if (resultCode == RESULT_OK) {
                DropInResult result=data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
                PaymentMethodNonce nonce=result.getPaymentMethodNonce();
                if(!TextUtils.isEmpty(edt_amnt.getText().toString())){
                    String amount=edt_amnt.getText().toString();
                    compositeDisposable.add(myApi.submitPayment(amount,nonce.getNonce())
                            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Consumer<BraintreeTransaction>() {
                                @Override
                                public void accept(BraintreeTransaction braintreeTransaction) throws Exception {
                                    if(braintreeTransaction.isSuccess()){
                                        Toast.makeText(PaymentPaypal.this,"Success",Toast.LENGTH_SHORT).show();
                                        //Toast.makeText(PaymentPaypal.this,""+braintreeTransaction.getTransaction().getId(),Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                        Toast.makeText(PaymentPaypal.this,"Payment Failed",Toast.LENGTH_SHORT).show();
                                }
                            }, new Consumer<Throwable>() {
                                @Override
                                public void accept(Throwable throwable) throws Exception {
                                    Toast.makeText(PaymentPaypal.this,"Ajith"+throwable.getMessage(),Toast.LENGTH_SHORT).show();
                                }
                            }));
                    }
                }
            }
        }
    }
