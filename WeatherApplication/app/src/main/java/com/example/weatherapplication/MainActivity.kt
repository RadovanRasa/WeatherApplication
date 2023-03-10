package com.example.weatherapplication

import android.content.Intent
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {

    var city: String = "Belgrade"
    val key: String = "f74e983a89614c87a24155510230703"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        val paint = textView.paint
        val width = paint.measureText(tvNext.text.toString())
        val textShader: Shader = LinearGradient(0f, 0f, width, tvNext.textSize, intArrayOf(
            Color.parseColor("#A4773CAC"),
            Color.parseColor("#EC8C44"),
            Color.parseColor("#C9F3AA5C")
        ), null, Shader.TileMode.CLAMP)

        tvNext.paint.setShader(textShader)


        LoadWeather(key,city)
        tvLocation.setOnClickListener(View.OnClickListener {
            etCity.visibility = EditText.VISIBLE
            etCity.isEnabled = true
            btnLoad.visibility = Button.VISIBLE
            btnLoad.isEnabled = true
        })

        btnLoad.setOnClickListener(View.OnClickListener {
            city = etCity.text.toString()
            tvLocation.text = etCity.text
            etCity.visibility = EditText.INVISIBLE
            etCity.isEnabled = false
            btnLoad.visibility = Button.INVISIBLE
            btnLoad.isEnabled = false
            LoadWeather(key,city)
        })
        imgNext.setOnClickListener(View.OnClickListener {
            val intent =Intent(this, WeekWeatherActivity::class.java)
            intent.putExtra("City", city)
            intent.putExtra("Key", key)
            startActivity(intent)
        })


    }
    fun LoadWeather(key:String,city: String){
        val weather = RetrofitHelper.getInstance().create(WeatherApi::class.java)
        var post:Weather? = null
        GlobalScope.launch {
            val result = weather.getWeather(key, city)
            if(result!=null){
                Log.d("Prognoza: ", result.body().toString())
                post = result.body()
                println("Temperatura je: " + post?.current?.temp_c.toString())

                runOnUiThread(Runnable {
                    tvTemperature.text = post?.current?.temp_c?.toInt().toString()+"°C"
                    tvWeather.text = post?.current?.condition?.text.toString()
                    tvLocation.text = post?.location?.name.toString()
                    tvSunrise.text = post?.forecast?.forecastday?.elementAt(0)?.astro?.sunrise.toString()
                    tvSunset.text = post?.forecast?.forecastday?.elementAt(0)?.astro?.sunset.toString()
                    tvHumidity.text = post?.current?.humidity.toString() + "%"
                    tvPrecipitation.text = post?.current?.precip_mm.toString() + " mm"
                    tvWindDir.text = post?.current?.wind_dir.toString()
                    tvWindSpeed.text = post?.current?.wind_kph.toString() + " kmph"
                    val formater = DateTimeFormatter.ofPattern("EEEE dd - HH:mm")
                    val current = LocalDateTime.now().format(formater)
                    tvDateTime.text = current.toString()
                    var hello = ""
                    val formater2 = DateTimeFormatter.ofPattern("HH")
                    val hour = LocalDateTime.now().format(formater2).toInt()
                    if(hour in 6..11){
                        hello="Good morning"
                    }
                    else if(hour in 12..18){
                        hello="Good afternoon"
                    }
                    else{
                        hello = "Good evening"
                    }
                    tvHello.text = hello

                    when(post?.current?.condition?.text.toString()){
                        "Moderate rain" -> ivWeather.setImageResource(R.drawable.rain)
                        "Sunny" -> ivWeather.setImageResource(R.drawable.sun)
                        "Thunder" -> ivWeather.setImageResource(R.drawable.thunder)
                        "Clear" -> ivWeather.setImageResource(R.drawable.moon)
                        else -> ivWeather.setImageResource(R.drawable.suncloud)
                    }
                })
            }
        }
    }
}