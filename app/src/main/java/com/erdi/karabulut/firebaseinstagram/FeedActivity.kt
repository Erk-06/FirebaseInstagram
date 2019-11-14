package com.erdi.karabulut.firebaseinstagram

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_feed.*

class FeedActivity : AppCompatActivity() {

    private lateinit var  auth:FirebaseAuth
    private lateinit var db: FirebaseStorage
    var userEmailFromFB:ArrayList<String> = ArrayList()
    var userCommentFromFB: ArrayList<String> = ArrayList()
    var userImageFromFB: ArrayList<String> = ArrayList()

    var adapter: FeedRecyclerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)
        auth= FirebaseAuth.getInstance()
        db= FirebaseStorage.getInstance()

        getDataFromFirestore()
        //recyclerview
        val linerLayoutManager=LinearLayoutManager(applicationContext)
         recyclerView.layoutManager=linerLayoutManager


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val menuInfalter=menuInflater
        menuInfalter.inflate(R.menu.options_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId==R.id.add_post)
        {
            val  intent=Intent(applicationContext,UploadActivity::class.java)
            startActivity(intent)

        }
        else if(item.itemId==R.id.logout)
        {
            auth.signOut()
            val intent=Intent(applicationContext,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
    fun getDataFromFirestore() {

    }
}
