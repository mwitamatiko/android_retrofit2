package com.mwita.userui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

//import kotlinx.android.synthetic.main.documents.*
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import com.mwita.uploadfiles.ImageBuilder
import com.mwita.uploadfiles.ImageInterface
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream


open class MainActivity : AppCompatActivity() {
    private lateinit var imageUri: Uri

    private val PICK_ID_IMAGE_FRONT = 123
    private val CAPTURE_ID_IMAGE_FRONT = 456

    private val PICK_ID_IMAGE_BACK = 234
    private val CAPTURE_ID_IMAGE_BACK = 567

    private val CAPTURE_PASSPORT_IMAGE = 678
    private val PICK_PASSPORT_IMAGE = 789

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        supportFragmentManager.beginTransaction().apply {
//            replace(R.id.fl_fragment,Documents())
//            addToBackStack(null)
//            commit()
//        }


//        uploadImageBtn.setOnClickListener {
//            val intent = Intent()
//            intent.type = "image/*"
//            intent.action = Intent.ACTION_GET_CONTENT
//            startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE)
////            image_view.setImageURI(imageUri)
//        }


        /**
         * scan and capture ID images - FRONT
         */
        front_capture_id_btn.setOnClickListener {
            var intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, CAPTURE_ID_IMAGE_FRONT)
        }
        front_pick_id_btn.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, PICK_ID_IMAGE_FRONT)
        }

        /**
         * scan and capture ID images - BACK
         */
        back_capture_id_btn.setOnClickListener {
            var intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, CAPTURE_ID_IMAGE_BACK)
        }
        back_pick_id_btn.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, PICK_ID_IMAGE_BACK)
        }

        /**
         * scan and capture Passport images
         */
        capture_passport_btn.setOnClickListener {
            var intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, CAPTURE_PASSPORT_IMAGE)
        }
        passport_pick_btn.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, PICK_PASSPORT_IMAGE)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        imageUri = data?.data!!
        var bitmapImage = data?.extras?.get("data") as Bitmap

        if (requestCode == CAPTURE_ID_IMAGE_FRONT && resultCode == Activity.RESULT_OK) {
            national_front_id_imageView.setImageBitmap(bitmapImage)
        }
        else if(requestCode == PICK_ID_IMAGE_FRONT && resultCode == Activity.RESULT_OK){
            national_front_id_imageView.setImageURI(imageUri)
        }
        else if(requestCode == CAPTURE_ID_IMAGE_BACK && resultCode == Activity.RESULT_OK){
            national_back_id_imageView.setImageBitmap(bitmapImage)
        }
        else if(requestCode == PICK_ID_IMAGE_BACK && resultCode == Activity.RESULT_OK){
            national_back_id_imageView.setImageURI(imageUri)

        }
        else if(requestCode == CAPTURE_PASSPORT_IMAGE && resultCode == Activity.RESULT_OK){
            passport_imageView.setImageBitmap(bitmapImage)

        }
        else if (requestCode == PICK_PASSPORT_IMAGE && resultCode == Activity.RESULT_OK) {
            passport_imageView.setImageURI(imageUri)
        }

//        else if (next_btn.isPressed){
//            val file = savebitmap(bitmapImage)
//            val files = MultipartBody.Part.createFormData("files", file!!.name, file.asRequestBody("image/*".toMediaTypeOrNull()))
//            filesArray.add(files)
//
//            next_btn.setOnClickListener {
//                val list: ArrayList<MultipartBody.Part> = ArrayList()
//                val user_id = RequestBody.create("text/plain".toMediaTypeOrNull(), "upload_test")
//
//                for (uri in filesArray) {
//                    list.add(uri)
//                }
//
//                val service = ImageBuilder.buildService(ImageInterface::class.java)
//                val req = service.uploadImageFile(list,user_id)
//                req.enqueue(object:retrofit2.Callback<ResponseBody> {
//                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
//                        t.message?.let { Log.e("Upload error:", it) }
//                    }
//                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
//                        Log.v("Upload", "success")
//                    }
//
//                })
//            }
//        }


    }

    private fun savebitmap(bmp: Bitmap): File? {
        val extStorageDirectory = Environment.getExternalStorageDirectory().toString()
        var outStream: OutputStream? = null
        // String temp = null;
        var file = File(extStorageDirectory, "temp.png")
        if (file.exists()) {
            file.delete()
            file = File(extStorageDirectory, "temp.png")
        }
        try {
            outStream = FileOutputStream(file)
            bmp.compress(Bitmap.CompressFormat.PNG, 100, outStream)
            outStream.flush()
            outStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
        return file
    }
}