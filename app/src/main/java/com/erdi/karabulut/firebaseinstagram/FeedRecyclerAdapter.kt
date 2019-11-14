package com.erdi.karabulut.firebaseinstagram

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class FeedRecyclerAdapter(private  val userEmailArray: ArrayList<String>, private val userCommentArray: ArrayList<String>, private val userImageArray: ArrayList<String>) :RecyclerView.Adapter<FeedRecyclerAdapter.PostHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val inflater=LayoutInflater.from(parent.context)
        val view=inflater.inflate(R.layout.recycler_view_row,parent,false)
        return PostHolder(view)
    }

    override fun getItemCount(): Int {
        return userEmailArray.size
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {
    }

    class PostHolder(view: View) :RecyclerView.ViewHolder(view){


}
}