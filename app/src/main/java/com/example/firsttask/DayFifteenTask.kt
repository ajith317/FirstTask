package com.example.firsttask

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.paypal.android.sdk.payments.*
import org.json.JSONException
import java.math.BigDecimal


class DayFifteenTask : AppCompatActivity() {

    val  payPalRequestCode=111

    val PaypalClientID ="ASa3kMlunB6pQXyhPuF4qUGnhIqEX3QNi1TAHoF6rjGtHjcl6XGdXT7HcUT8HEsulN8RTjNLh-NgqA3-"

    val config=PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_SANDBOX).clientId(PaypalClientID)
    lateinit var payBtn:Button
    lateinit var payAmnt:EditText
    lateinit var amnt:String
    lateinit var stripeBtn:Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_day_fifteen_task)

        val intent = Intent(this, PayPalService::class.java)
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config)
        startService(intent)

        payBtn=findViewById(R.id.day15_pay_btn)
        payAmnt=findViewById(R.id.day15_amnt)
        stripeBtn=findViewById(R.id.stripe_btn)
        val payPal:Button=findViewById(R.id.payPal)



            payBtn.setOnClickListener({
                if(payAmnt.text.length>0){
                    paymentProccess()
                }
                else
                    Toast.makeText(this,"Amount Require",Toast.LENGTH_SHORT).show()
            })

        payPal.setOnClickListener({
            startActivity(Intent(applicationContext,  PaymentPaypal::class.java))
        })
        stripeBtn.setOnClickListener({
            startActivity(Intent(applicationContext, CheckoutActivity ::class.java))
        })



    }

    private fun paymentProccess() {
        amnt=payAmnt.text.toString()
        val payment = PayPalPayment(BigDecimal(java.lang.String.valueOf(amnt)), "USD", "TrioAngle",
                PayPalPayment.PAYMENT_INTENT_SALE)

        val intent = Intent(this, PaymentActivity::class.java)
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config)
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment)
        startActivityForResult(intent, payPalRequestCode)
    }

    @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == payPalRequestCode    )
        {
            Toast.makeText(this,requestCode,Toast.LENGTH_LONG).show()
            if (resultCode == RESULT_OK)
            {
                val confirm: PaymentConfirmation = data!!.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION)
                if (confirm != null) {
                    try {
                        val paymentDetails = confirm.toJSONObject().toString(4)
                        startActivity(Intent(this, PaymentProcess::class.java)
                                .putExtra("PaymentDetails", paymentDetails)
                                .putExtra("PaymentAmount", amnt))
                    } catch (e: JSONException) {
                        Log.e("paymentTask", "Error ", e)
                    }
                }
            } else if (resultCode == RESULT_CANCELED) {
                    Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show()
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                Toast.makeText(this, "Invalid", Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onDestroy() {
        stopService(Intent(this, PayPalService::class.java))
        super.onDestroy()
    }

}