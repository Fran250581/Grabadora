package com.example.fran.grabadora

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.Button
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private val LOG_TAG = "Grabadora"
    private var fichero: String? = Environment.getExternalStorageDirectory().absolutePath + "/myaudio.3gp"
    private var mediaRecorder: MediaRecorder? = null
    private var mediaPlayer: MediaPlayer? = null
    private var bGrabar: Button? = null
    private var bDetener: Button? = null
    private var bReproducir: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bGrabar = findViewById<View>(R.id.bGrabar) as Button
        bDetener = findViewById<View>(R.id.bDetener) as Button
        bReproducir = findViewById<View>(R.id.bReproducir) as Button

        bDetener!!.isEnabled = false
        bReproducir!!.isEnabled = false
    }

    fun grabar(view: View) {
        bGrabar!!.isEnabled = false
        bDetener!!.isEnabled = true
        bReproducir!!.isEnabled = false

        try {
            mediaRecorder = MediaRecorder()
            mediaRecorder?.setOutputFile(fichero)
            mediaRecorder?.setAudioSource(MediaRecorder.AudioSource.MIC)
            mediaRecorder?.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            mediaRecorder?.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)

            mediaRecorder!!.prepare()
        }
        catch (e: IOException) {
            Log.e(LOG_TAG, "Fallo en grabación")
        }
        mediaRecorder!!.start()
    }

    fun detenerGrabacion(view: View) {
        bGrabar!!.isEnabled = true
        bDetener!!.isEnabled = false
        bReproducir!!.isEnabled = true
        mediaRecorder!!.stop()
        mediaRecorder!!.release()
    }

    fun reproducir(view: View) {
        mediaPlayer = MediaPlayer()
        try {
            mediaPlayer!!.setDataSource(fichero)
            mediaPlayer!!.prepare()
            mediaPlayer!!.start()
        }
        catch (e: IOException) {
            Log.e(LOG_TAG, "Fallo en reproducción")
        }
    }

}