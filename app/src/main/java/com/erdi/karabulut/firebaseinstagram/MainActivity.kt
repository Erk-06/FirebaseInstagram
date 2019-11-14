package com.erdi.karabulut.firebaseinstagram

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var auth:FirebaseAuth;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
auth= FirebaseAuth.getInstance()
        val currentUser=auth.currentUser
        if (currentUser!=null)
        {  val intent=Intent(this,FeedActivity::class.java)
            startActivity(intent)
             finish()
        }

    }
    fun signInClicked(view:View){
     auth.signInWithEmailAndPassword(userEmailText.text.toString(),passwordText.text.toString()).addOnCompleteListener { task ->
         if (task.isSuccessful)
         {
             Toast.makeText(applicationContext,"Hoş geldiniz ${auth.currentUser?.email}",Toast.LENGTH_LONG).show()
        val intent=Intent(applicationContext,FeedActivity::class.java)
             startActivity(intent)
             finish()
         }

     }.addOnFailureListener { exception ->

         Toast.makeText(applicationContext,exception.localizedMessage.toString(),Toast.LENGTH_LONG).show()

     }
    }
    fun signUpClicked(view:View){

    }
}
