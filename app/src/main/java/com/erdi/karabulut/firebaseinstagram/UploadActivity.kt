package com.erdi.karabulut.firebaseinstagram

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.ImageDecoder
import android.media.Image
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_upload.*
import java.lang.Exception
import java.security.Permission
import java.util.*

class UploadActivity : AppCompatActivity() {
var selectedImage:Uri?=null
    private  lateinit var db:FirebaseFirestore
    private lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)
        auth= FirebaseAuth.getInstance()
        db= FirebaseFirestore.getInstance()

    }
    fun imageViewClicked(view: View) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                1
            )
        }
        else{
            val intent=Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent,2)


        }


    }
    fun uploadClicked (view: View) {
         val uuid=UUID.randomUUID()

        val imageName="$uuid.jpg"
        val storage=FirebaseStorage.getInstance()
        val reference=storage.reference
        val imagesReference=reference.child("images").child(imageName)

        if (selectedImage!=null){
            imagesReference.putFile(selectedImage!!).addOnSuccessListener { taskSnapshot ->
                val uploadedPictureReferences=FirebaseStorage.getInstance().reference.child("images").child(imageName)
                  uploadedPictureReferences.downloadUrl.addOnSuccessListener { uri ->
                      val downloaddUrl=uri.toString()
                      val postMap= hashMapOf<String,Any>("downloadUrl" to downloaddUrl,"userEmail" to auth.currentUser!!.email.toString(),"comment" to uploadCommentText.text.toString(),"date" to Timestamp.now())

                   db.collection("Posts").add(postMap).addOnCompleteListener { task ->
                    if (task.isComplete&&task.isSuccessful)
                    {
                        finish()
                    }
                   }



                  }
            }.addOnFailureListener { exception ->
                Toast.makeText(applicationContext,""+exception.localizedMessage,Toast.LENGTH_LONG).show()

            }
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray) {

        if (requestCode==1)
        {
            if (grantResults.size > 0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                val intent=Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(intent,2)

            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode==2&&resultCode== Activity.RESULT_OK&& data != null){
            selectedImage=data.data

            try {
                if (selectedImage!=null){
                 if (Build.VERSION.SDK_INT>=28)
                 {
                      val source=ImageDecoder.createSource(contentResolver,selectedImage!!)
                      val bitmap=ImageDecoder.decodeBitmap(source)
                     uploadImageView.setImageBitmap(bitmap)
                 }
                    else{
                     val bitmap=MediaStore.Images.Media.getBitmap(contentResolver,selectedImage)
                     uploadImageView.setImageBitmap(bitmap)


                 }
                }

            }catch (e:Exception){
               Log.e("UploadActivity.kt",e.toString())

            }
        }
    }
}
