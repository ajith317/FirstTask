package com.example.firsttask

import android.app.Activity
import android.app.slice.Slice
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.stripe.android.ApiResultCallback
import com.stripe.android.PaymentIntentResult
import com.stripe.android.Stripe
import com.stripe.android.model.ConfirmPaymentIntentParams
import com.stripe.android.model.StripeIntent
import com.stripe.android.view.CardInputWidget
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException
import java.lang.ref.WeakReference
import java.lang.reflect.Array.get
import java.util.*




class CheckoutActivity : AppCompatActivity() {

    private val backendUrl = "https://ancient-beach-26280.herokuapp.com/"
    private val httpClient = OkHttpClient()
    private lateinit var paymentIntentClientSecret: String
    private lateinit var stripe: Stripe
    var amountTextview: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)
        amountTextview = findViewById(R.id.day15_stripe_amnt)
        stripe = Stripe(applicationContext, "pk_test_51J3XVnSGNS3XDRZrhehlEvzSJlRgo5bIyfWTKBreoJv70YsYVRHnAMi8x30NbfX2HnmUShi8PskY069GWCL0SHA700g2NkZiDF")
        GlobalScope.launch {
            startCheckout()
        }

    }
    private fun displayAlert(
            activity: Activity,
            title: String,
            message: String,
            restartDemo: Boolean = false
    ) {
        runOnUiThread {
            val builder = AlertDialog.Builder(activity)
                    .setTitle(title)
                    .setMessage(message)
            builder.setPositiveButton("Ok", null)
            builder.create().show()
        }
    }
    private fun startCheckout() {
        val weakActivity = WeakReference<Activity>(this)


        // Hook up the pay button to the card widget and stripe instance
        val payButton: Button = findViewById(R.id.payButton)
        payButton.setOnClickListener {

            val mediaType = "application/json; charset=utf-8".toMediaType()

            val amount = (amountTextview!!.text.toString())
            val payMap: MutableMap<String, Any> = HashMap()
            val itemMap: MutableMap<String, Any> = HashMap()
            val itemList: MutableList<Map<String, Any>> = ArrayList()
            payMap["currency"] = "usd"
            itemMap["id"] = "photo_subscription"
            itemMap["amount"] = amount
            itemList.add(itemMap)
            payMap["items"] = itemList
            val json = Gson().toJson(payMap)
            val body = json.toRequestBody(mediaType)

            val request = Request.Builder()
                    .url(backendUrl + "create-payment-intent")
                    .post(body)
                    .build()
            httpClient.newCall(request)
                    .enqueue(object: Callback {
                        override fun onFailure(call: Call, e: IOException) {
                            weakActivity.get()?.let { activity ->
                                displayAlert(activity, "Failed to load page", "Error: $e")
                            }
                        }
                        override fun onResponse(call: Call, response: Response) {
                            if (!response.isSuccessful) {
                                weakActivity.get()?.let { activity ->
                                    displayAlert(
                                            activity,
                                            "Failed to load page",
                                            "Error: $response"
                                    )
                                }
                            } else {
                                val responseData = response.body?.string()
                                val responseJson =
                                        responseData?.let { JSONObject(it) } ?: JSONObject()
                                // For added security, our sample app gets the publishable key
                                // from the server.
                                paymentIntentClientSecret = responseJson.getString("clientSecret")
                                val cardInputWidget =
                                        findViewById<CardInputWidget>(R.id.cardInputWidget)
                                cardInputWidget.paymentMethodCreateParams?.let { params ->
                                    val confirmParams = ConfirmPaymentIntentParams
                                            .createWithPaymentMethodCreateParams(params, paymentIntentClientSecret)
                                    stripe.confirmPayment(this@CheckoutActivity, confirmParams)
                                }
                            }
                        }
                    })



        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val weakActivity = WeakReference<Activity>(this)

        runOnUiThread {
            GlobalScope.launch(Dispatchers.Main) {

                if (stripe.onPaymentResult(requestCode, data, object: ApiResultCallback<PaymentIntentResult>{
                            override fun onError(e: Exception) {

                            }

                            override fun onSuccess(result: PaymentIntentResult) {

                            }

                        })) {
                    lifecycleScope.launch {
                        runCatching {
                            stripe.retrievePaymentIntentSynchronous(paymentIntentClientSecret, "acct_1J3XVnSGNS3XDRZr")
                        }.fold(
                                onSuccess = { paymentIntent ->
                                    val status = paymentIntent!!.status
                                    if (status == StripeIntent.Status.Succeeded) {
                                        val gson = GsonBuilder().setPrettyPrinting().create()
                                        weakActivity.get()?.let { activity ->
                                            displayAlert(
                                                    activity,
                                                    "Payment succeeded",
                                                    gson.toJson(paymentIntent)
                                            )
                                        }
                                    } else if(status == StripeIntent.Status.RequiresPaymentMethod) {
                                        weakActivity.get()?.let { activity ->
                                            displayAlert(
                                                    activity,
                                                    "Payment failed",
                                                    paymentIntent.lastPaymentError?.message.orEmpty()
                                            )
                                        }
                                    }
                                },
                                onFailure = {
                                    weakActivity.get()?.let { activity ->
                                        displayAlert(
                                                activity,
                                                "Payment failed",
                                                it.toString()
                                        )
                                    }
                                }
                        )
                    }
                }
            }

        }

    }
}






