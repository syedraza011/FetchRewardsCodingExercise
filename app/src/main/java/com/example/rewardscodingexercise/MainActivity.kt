package com.example.rewardscodingexercise

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.rewardscodingexercise.model.Items
import com.example.rewardscodingexercise.adapter.ItemsAdapter
import com.example.rewardscodingexercise.network.RetrofitAPI

class MainActivity : AppCompatActivity() {

        // on below line we are creating variables.
        lateinit var itemsRV: RecyclerView
        lateinit var loadingPB: ProgressBar
        lateinit var itemsRVAdapter: ItemsAdapter
        lateinit var itemsList: ArrayList<Items>
        lateinit var btnFilter: Button
        lateinit var btnResult: Button


        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)

            // on below line we are initializing
            // our variable with their ids.
            itemsRV = findViewById(R.id.rvItems)
            loadingPB = findViewById(R.id.progressBar)
            btnFilter=findViewById(R.id.btnFilteredResults)
            btnResult=findViewById(R.id.btnAllResults)

            // on below line we are initializing our list
            itemsList = ArrayList()


            // on below line we are calling
            // get all items method to get data.
            getAllItems(0)


            btnFilter.setOnClickListener {
                getAllItems(1)
            }

            btnResult.setOnClickListener {
                getAllItems(2)
            }
        }

        private fun getAllItems(callFrom:Int) {
            loadingPB.visibility= View.VISIBLE
            //0--> Normal
            //1--> Filter result by name if null or empty
            //2--> Reload normal state

            // on below line we are creating a retrofit
            // builder and passing our base url
            val retrofit = Retrofit.Builder()
                .baseUrl("https://fetch-hiring.s3.amazonaws.com/")
                // on below line we are calling add
                // Converter factory as Gson converter factory.
                // at last we are building our retrofit builder.
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            // below line is to create an instance for our retrofit api class.
            val retrofitAPI = retrofit.create(RetrofitAPI::class.java)

            // on below line we are calling a method to get all the courses from API.
            val call: Call<ArrayList<Items>> = retrofitAPI.getAllItems()

            lifecycleScope.launch {
                try {

                    // on below line we are calling method to enqueue and calling
                    // all the data from array list.
                    call!!.enqueue(object : Callback<ArrayList<Items>?> {
                        override fun onResponse(
                            call: Call<ArrayList<Items>?>,
                            response: Response<ArrayList<Items>?>
                        ) {
                            if (response.isSuccessful) {
                                loadingPB.visibility = View.GONE


                                if (callFrom==0 || callFrom==2)
                                {
                                    itemsList = response.body()!!
                                }

                                if (callFrom==1)
                                {
                                    val list = response.body()!!
                                    itemsList= list.filter { it.name == "" || it.name=="null" } as ArrayList<Items>
                                }
                            }

                            // on below line we are initializing our adapter.
                            itemsRVAdapter = ItemsAdapter(itemsList)

                            // on below line we are setting adapter to recycler view.
                            itemsRV.adapter = itemsRVAdapter

                            if (callFrom==1 || callFrom==2){
                                itemsRVAdapter.notifyDataSetChanged()
                            }
                        }

                        override fun onFailure(call: Call<ArrayList<Items>?>, t: Throwable) {
                            // displaying an error message in toast
                            Toast.makeText(this@MainActivity, "Fail to get the data..", Toast.LENGTH_SHORT)
                                .show()
                        }
                    })
                }
                catch (e:Exception){
                    Log.d("Error",e.toString())
                }
            }

        }

}