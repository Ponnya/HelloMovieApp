/*
package com.padc.ponnya.hellomovieapp.network.dataagents

import android.os.AsyncTask
import android.util.Log
import com.google.gson.Gson
import com.padc.ponnya.hellomovieapp.network.responses.MovieListResponse
import com.padc.ponnya.hellomovieapp.utils.API_GET_NOW_PLAYING
import com.padc.ponnya.hellomovieapp.utils.BASE_URL
import com.padc.ponnya.hellomovieapp.utils.MOVIE_API_KEY
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

object MovieDataAgentImpl : MovieDataAgent {
    override fun getNowPlayingMovies() {
        GetNowPlayingMoviesTask().execute()
    }

    class GetNowPlayingMoviesTask() : AsyncTask<Void, Void, MovieListResponse?>() {
        override fun doInBackground(vararg p0: Void?): MovieListResponse? {
            val url: URL
            var reader: BufferedReader? = null
            val stringBuilder: StringBuilder

            try {
                //create the HttpURLConnection
                url =
                    URL("""$BASE_URL$API_GET_NOW_PLAYING?api_key=$MOVIE_API_KEY&language=en-US&page=1""")

                val connection = url.openConnection() as HttpURLConnection

                //Set HTTP Method
                connection.requestMethod = "GET"

                //give it 15 seconds to respond
                connection.readTimeout = 15 * 1000

                connection.doInput = true
                connection.doOutput = false

                connection.connect()

                reader = BufferedReader(InputStreamReader(connection.inputStream))

                stringBuilder = StringBuilder()

                for (line in reader.readLines()) {
                    stringBuilder.append(line + "\n")
                }

                val responseString = stringBuilder.toString()
                Log.d("NowPlayingMovies", responseString)

                val movieListResponse = Gson().fromJson(
                    responseString,
                    MovieListResponse::class.java
                )

                return movieListResponse
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("NewsError", e.message ?: "")
            } finally {
                if (reader != null) {
                    try {
                        reader.close()
                    } catch (ioe: IOException) {
                        ioe.printStackTrace()
                    }

                }
            }
            return null
        }

        override fun onPostExecute(result: MovieListResponse?) {
            super.onPostExecute(result)
        }

    }
}*/
