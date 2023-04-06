package com.example.newsapp


import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

class NewsListAdapter(private val listener: newsItemClicked): RecyclerView.Adapter<newsViewHolder>() {

    private val items: ArrayList<NewsData> = ArrayList()
    //Creating the viewholder which would hold the data in recycler view
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): newsViewHolder {
        //Layout inflator is used to use xml file as view
        val view = LayoutInflater.from(p0.context).inflate(R.layout.list_item,p0,false)
        val newsViewHolderPos = newsViewHolder(view)
        view.setOnClickListener{
            listener.onItemClicked(items[newsViewHolderPos.adapterPosition])
        }
        return newsViewHolderPos
    }

    //It gives the no. of items in the recycler view is required
    override fun getItemCount(): Int {
        return items.size
    }

    //It bind the data with View holder
    override fun onBindViewHolder(p0: newsViewHolder, p1: Int) {
        val currItem = items[p1]
        p0.titleView.text = currItem.title
        Glide.with(p0.itemView.context).load(currItem.imageUrl).into(p0.image)
    }

    //Creating a function to update a news item

    fun updateNews(updatedNews : ArrayList<NewsData>){
        items.clear()
        items.addAll(updatedNews)

        notifyDataSetChanged()
    }
}


//This the view Holder in which the data is shown
class newsViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
    val titleView:TextView = itemView.findViewById(R.id.newsTitle)
    val image:ImageView = itemView.findViewById(R.id.newsImage)
}

interface newsItemClicked{
    fun onItemClicked(items: NewsData)
}