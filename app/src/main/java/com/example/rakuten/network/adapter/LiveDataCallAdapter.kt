package com.example.rakuten.network.adapter

import androidx.lifecycle.LiveData
import com.example.rakuten.data.ApiResponse
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type

const val UNKNOWN_ERROR: Int = 100

class LiveDataCallAdapter<R>(private val responseType: Type) : CallAdapter<R, LiveData<ApiResponse<R>>> {

    override fun responseType(): Type {
        return responseType
    }

    override fun adapt(call: Call<R>): LiveData<ApiResponse<R>> {
        return object : LiveData<ApiResponse<R>>() {
            private var isSuccess = false;
            override fun onActive() {
                super.onActive()
                if (!isSuccess) {
                    enqueue()
                }
            }

            override fun onInactive() {
                super.onInactive()
                dequeue()
            }

            private fun enqueue() {
                call.enqueue(object : Callback<R> {
                    override fun onResponse(call: Call<R>, response: Response<R>) {
                        postValue(ApiResponse.create(response))
                        isSuccess = true
                    }

                    override fun onFailure(call: Call<R>, t: Throwable) {
                        postValue(ApiResponse.create(UNKNOWN_ERROR,t))
                    }

                })
            }

            private fun dequeue() {
                if (call.isExecuted) {
                    call.cancel()
                }
            }
        }
    }
}