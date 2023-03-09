package com.example.weatherapplication

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_week_weather.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.concurrent.Executors

class WeekWeatherActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_week_weather)


        imgClose.setOnClickListener(View.OnClickListener {
            this.finish()
        })
        val weather = RetrofitHelper.getInstance().create(WeatherApi::class.java)
        var post: Weather? = null
        GlobalScope.launch {
            val result = weather.getWeather()
            if (result != null) {
                runOnUiThread(Runnable {

                    val post = result.body()



                    for (i in 0..6) {

                        //Day in week
                        val idDay = "tvDay" + (i + 1)
                        val iddDay: Int = resources.getIdentifier(idDay, "id", packageName)
                        val day = findViewById<TextView>(iddDay)
                        val patern = DateTimeFormatter.ofPattern("EEE")
                        var localdate =
                            LocalDate.parse(post?.forecast?.forecastday?.elementAt(i)?.date.toString())
                        day.text = localdate.format(patern)

                        //Avg temp
                        val idTemp = "tvAvgTemp" + (i + 1).toString()
                        val iddTemp: Int = resources.getIdentifier(idTemp, "id", packageName)
                        val avgTemp = findViewById<TextView>(iddTemp)
                        avgTemp.text =
                            post?.forecast?.forecastday?.elementAt(i)?.day?.avgtemp_c?.toInt()
                                .toString() + "°"

                        //icon
                        val idImage = "imgWeather" + (i + 1).toString()

                        val iddImage: Int = resources.getIdentifier(idImage, "id", packageName)

                        val imageView = findViewById<ImageView>(iddImage)
                        val executor = Executors.newSingleThreadExecutor()
                        val handler = Handler(Looper.getMainLooper())
                        var image: Bitmap? = null
                        executor.execute {
                            val imageURL =
                                "https:" + post?.forecast?.forecastday?.elementAt(i)?.day?.condition?.icon.toString()

                            try {
                                val im = java.net.URL(imageURL).openStream()
                                image = BitmapFactory.decodeStream(im)
                                handler.post {
                                    imageView.setImageBitmap(image)
                                }
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }

                        }

                        //Weather text
                        val idCondition = "tvWeather" + (i+1).toString()
                        val iddCondition: Int = resources.getIdentifier(idCondition, "id", packageName)
                        val condition = findViewById<TextView>(iddCondition)
                        condition.text = post?.forecast?.forecastday?.elementAt(i)?.day?.condition?.text.toString()

                    }
                })
            }
        }
    }
}