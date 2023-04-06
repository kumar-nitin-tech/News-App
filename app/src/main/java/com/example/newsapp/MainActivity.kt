package com.example.newsapp


import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import com.android.volley.toolbox.JsonObjectRequest

class MainActivity : AppCompatActivity(), newsItemClicked {
    private lateinit var recyclerView: RecyclerView
    private lateinit var madapter: NewsListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        fetchData()
        madapter = NewsListAdapter(this)
        recyclerView.adapter = madapter
    }
    private fun fetchData() {
        val url = "https://gnews.io/api/v4/search?q=example&lang=en&country=in&apikey=6d21246d8383ab730a5437ff1cb71c34"
        val jsonObjectRequest = JsonObjectRequest(
            com.android.volley.Request.Method.GET,
            url,null,
            {
            //We have make a json array as in json api data of news is in json array format
            val newJsonArray = it.getJSONArray("articles")
                //Creatin a array list of news data class to store the data
                val newsArray = ArrayList<NewsData>()
                for (i in 0 until newJsonArray.length()){
                    val newJsonObject = newJsonArray.getJSONObject(i)
                    val news = NewsData(
                        newJsonObject.getString("title"),
                        newJsonObject.getString("url"),
                        newJsonObject.getString("image")
                    )
                    newsArray.add(news)
                }
                madapter.updateNews(newsArray)
            }, {

            }
        )
        Singleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    override fun onItemClicked(items: NewsData) {
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(items.url))
    }
}