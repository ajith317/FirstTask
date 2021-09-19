package com.example.firsttask

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import com.example.firsttask.FileUtils
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import rebus.permissionutils.PermissionEnum
import rebus.permissionutils.PermissionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


class DaySixteenTask : AppCompatActivity(),View.OnClickListener {

    lateinit var error_text: TextView
    lateinit var  selectBtn: Button
    private val REQUEST_PICK_PHOTO = 2

    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_day_sixteen_task)

        error_text=findViewById(R.id.error_text)

        selectBtn=findViewById<Button>(R.id.selectImage)
        PermissionManager.Builder()
                .permission(PermissionEnum.READ_EXTERNAL_STORAGE)
                .ask(this)
        selectBtn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {

        if(v==selectBtn){
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Image"), REQUEST_PICK_PHOTO)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_PICK_PHOTO && resultCode == Activity.RESULT_OK) {
            val selectedImage: Uri = data!!.data!!
            val file =FileUtils.getFile(this, selectedImage)
            Log.d("ImageURL",file.toString())
            try {
                uploadFile(file)
            }catch (e:Exception){
                Log.d("ImageError",e.message.toString())
            }

        }

    }

    private fun uploadFile(file: File)
    {

        val request=ServiceBuilder.buildService(HotelService::class.java)

        val first_name= RequestBody.create("text/plain".toMediaTypeOrNull(), "ajith")
        val last_name= RequestBody.create("text/plain".toMediaTypeOrNull(), "babu")
        val email= RequestBody.create("text/plain".toMediaTypeOrNull(), "ajithbabu@gmail.com")
        val gender= RequestBody.create("text/plain".toMediaTypeOrNull(), "male")
        val school= RequestBody.create("text/plain".toMediaTypeOrNull(), "hjnnhl")
        val phone= RequestBody.create("text/plain".toMediaTypeOrNull(), "8483282838")
        val dob= RequestBody.create("text/plain".toMediaTypeOrNull(), "20-04-1997")
        val work= RequestBody.create("text/plain".toMediaTypeOrNull(), "bhhbehehe")
        val user_location= RequestBody.create("text/plain".toMediaTypeOrNull(), "ghhd")
        val about_me= RequestBody.create("text/plain".toMediaTypeOrNull(), "Tony Stark")
        val token= RequestBody.create("text/plain".toMediaTypeOrNull(), "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwczovL21ha2VudC50cmlvYW5nbGVkZW1vLmNvbS9hcGkvbG9naW4iLCJpYXQiOjE2MjMxMjc2ODAsImV4cCI6MTYyNTc1NTY4MCwibmJmIjoxNjIzMTI3NjgwLCJqdGkiOiJBRGdBMnBqUnhqeml6bDVIIiwic3ViIjoxMDAzNywicHJ2IjoiMjNiZDVjODk0OWY2MDBhZGIzOWU3MDFjNDAwODcyZGI3YTU5NzZmNyJ9.BJnSmm9S5NGQzRm7i_wQu9lajrdVL-0VK416KYKBghg")

        val image= RequestBody.create("image/*".toMediaTypeOrNull(), file)

        val imageBody = MultipartBody.Part.createFormData("image", file.name, image)

        val call = request.updateProfile(imageBody, gender, school, phone, dob, work, last_name, user_location, first_name,
                about_me, token, email)

        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    error_text.text = response.toString()

                } else {

                    error_text.text = response.body().toString()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("Upload error:", t.message.toString())
            }
        })

    }

    fun getRealPathFromURI(context: Context, contentUri: Uri): String {
        var cursor: Cursor? = null
        try {
            val proj = arrayOf(MediaStore.Images.Media.DATA)
            cursor = context.contentResolver.query(contentUri, proj, null, null, null)
            val column_index = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            return cursor.getString(column_index)
        } catch (e: Exception) {
            return ""
        } finally {
            cursor?.close()
        }
    }

}