package com.example.fcmdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.d
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.StringBuilder

const val BASE_URL = "https://jsonplaceholder.typicode.com/"

class MainActivity : AppCompatActivity() {

    lateinit var myadapter: Myadapter
    lateinit var linearLayoutManager : LinearLayoutManager
    lateinit var recyclerView: RecyclerView


    //var txtv: TextView= findViewById<TextView>(R.id.textUser)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView=findViewById<RecyclerView>(R.id.recycler_id)
        recyclerView.setHasFixedSize(true)

        linearLayoutManager= LinearLayoutManager(this)
        recyclerView.layoutManager=linearLayoutManager

         getMyData()

    }

    private fun getMyData() {
       val retrofitBuilder=Retrofit.Builder()
           .addConverterFactory(GsonConverterFactory.create())
           .baseUrl(BASE_URL)
           .build()
           .create(ApiInterface::class.java)

        val retrofitData=retrofitBuilder.getData()

        retrofitData.enqueue(object : Callback<List<MyDataItem>?> {

            override fun onResponse(call: Call<List<MyDataItem>?>, response: Response<List<MyDataItem>?>) {

                val responsebody=response.body()!!

                myadapter= Myadapter(baseContext,responsebody)
                myadapter.notifyDataSetChanged();

                recyclerView.adapter=myadapter

              /*  val myStringBuilder=StringBuilder()

                for (myData in responsebody)
                {
                    myStringBuilder.append(myData.id)
                    myStringBuilder.append("\n")

                }

                txtv.text=myStringBuilder*/

            }

            override fun onFailure(call: Call<List<MyDataItem>?>, t: Throwable) {

                d("Main Activity","On Failure : "+t.message)

            }
        })
    }
}