package com.example.firsttask


import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.facebook.*
import com.facebook.AccessToken
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.ResultCallback
import com.google.android.gms.common.api.Status
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_day_seventeen_task.*
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*


const val RC_SIGN_IN=123
class DaySeventeenTask : AppCompatActivity(), GoogleApiClient.OnConnectionFailedListener{


    override fun onConnectionFailed(p0: ConnectionResult) {
        Log.d("Failed", "onConnectionFailed:" + p0);
    }
    private var callbackManager: CallbackManager? = null
    lateinit var textView: TextView
    lateinit var button: Button

    private var mGoogleApiClient: GoogleApiClient? = null

    private var btnLogout: Button? = null
    private val accessToken: AccessToken?=null
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_day_seventeen_task)


        btnLogout = findViewById(R.id.day17_gSignOut_btn)



        updateUI(false)

        FacebookSdk.sdkInitialize(applicationContext)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()

        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        val account=GoogleSignIn.getLastSignedInAccount(this)

        textView=findViewById(R.id.day17_id_fName)

        button=findViewById(R.id.day17_loginBtn)
       // val fbLogout=findViewById<Button>(R.id.day17_flogOut)

        callbackManager= CallbackManager.Factory.create()



        try {
            val info = packageManager.getPackageInfo(
                    "com.example.firsttask",
                    PackageManager.GET_SIGNATURES)
            for (signature in info.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())

                Log.d("key", Base64.getEncoder().encodeToString(md.digest()))
            }
        } catch (e: Exception) {
        } catch (e: NoSuchAlgorithmException) {
        }

        val fImage:ImageView
         fImage=findViewById(R.id.day17_fImage)


       // fbLogout.visibility=View.GONE



        button.setOnClickListener {
            LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email"))
            LoginManager.getInstance().registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
                override fun onSuccess(result: LoginResult) {

                    textView.setText("Login Sucessfull \n" + result!!.accessToken.userId)
                }
                override fun onCancel() {
                    textView.setText("Login Cancell")
                }
                override fun onError(error: FacebookException) {
                    if (error != null) {
                        textView.setText(error.message)
                    }
                }

                val accessToken = AccessToken.getCurrentAccessToken()

            })

           // fbLogout.visibility=View.VISIBLE
           // button.visibility=View.GONE
        }

//        fbLogout.setOnClickListener({
//            button.visibility=View.VISIBLE
//            if (AccessToken.getCurrentAccessToken() != null) {
//                GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions/", null, HttpMethod.DELETE,
//                        GraphRequest.Callback {
//                            AccessToken.setCurrentAccessToken(null)
//                            LoginManager.getInstance().logOut()
//
//                        }
//                ).executeAsync()
//            }
//        })



        mGoogleApiClient = GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build()





        day17_gSign_btn.visibility=View.VISIBLE
        day17_id_gName.visibility=View.GONE




        day17_gSign_btn?.setOnClickListener(View.OnClickListener {
            val signInIntent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)

        })

        btnLogout?.setOnClickListener(View.OnClickListener {
            Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                    object : ResultCallback<Status> {
                        override fun onResult(status: Status) {
                            updateUI(false)

                        }
                    })
        })

    }

    private fun updateUI(isLogin: Boolean) {
        if (isLogin) {
            day17_gSign_btn?.visibility = View.GONE
            btnLogout?.visibility = View.VISIBLE


        } else {
            day17_gSign_btn?.visibility = View.VISIBLE
            btnLogout?.visibility = View.GONE
            day17_id_gName.text=""
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        callbackManager!!.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task=GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
        else {
            val accessTokenTracker = AccessToken.getCurrentAccessToken()

            val graphRequest = GraphRequest.newMeRequest(accessTokenTracker, GraphRequest.GraphJSONObjectCallback
            { `object`, response ->
                Log.d("FBTEST", `object`.toString())

                val name = `object`.getString("name")
                val firstName = `object`.getString("first_name")
                val lastName = `object`.getString("last_name")

                val id = `object`.getString("id")
                val pic = `object`.getJSONObject("picture").getJSONObject("data").getString("url")
                textView.text = "Name : " + name + "\nFirst Name:" + firstName + "\nLast Name :" + lastName
                Picasso.get().load(pic)
                        .into(day17_fImage)
            })

            val bundle = Bundle()
            bundle.putString("fields", "gender,name,id,first_name,last_name,picture.width(150).height(150)")
            graphRequest.setParameters(bundle)
            graphRequest.executeAsync()
        }


    }



    private fun handleSignInResult(compeletedTask: com.google.android.gms.tasks.Task<GoogleSignInAccount>)
    {

        try {
            val account=compeletedTask.getResult(ApiException::class.java)
            day17_gSign_btn.visibility=View.GONE
            day17_id_gName.visibility=View.VISIBLE
            day17_id_gName.text="WELCOME "+account?.displayName
            btnLogout?.visibility = View.VISIBLE

        }catch (e: Exception)
        {
            day17_gSign_btn.visibility=View.VISIBLE
            day17_id_gName.visibility=View.GONE
            updateUI(true)
        }

    }

}

