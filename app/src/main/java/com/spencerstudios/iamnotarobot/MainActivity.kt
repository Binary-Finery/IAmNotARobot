package com.spencerstudios.iamnotarobot

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.DisplayMetrics
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.EditorInfo
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    private var displayWidth = 0
    private var captchaWidth = 0
    private var captchaHeight = 0

    private lateinit var captcha: Captcha
    private lateinit var chars : ArrayList<Char>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val displayMetrics = DisplayMetrics()
        this.windowManager.defaultDisplay.getMetrics(displayMetrics)

        displayWidth = displayMetrics.widthPixels
        captchaWidth = (displayWidth / 2) + (displayWidth / 4)
        captchaHeight = captchaWidth / 4

        chars = getChars()

        setCaptcha()

        inputCaptcha.setOnEditorActionListener { _, i, _ ->
            when (i) {
                EditorInfo.IME_ACTION_DONE -> displaySuccessMsg(captcha.getAnswer() == inputCaptcha.text.toString())
            }
            true
        }
    }

    private fun setCaptcha() {
        captcha = Captcha(this, captchaWidth, captchaHeight, chars)
        captchaView.setImageBitmap(captcha.getImage())
    }

    private fun displaySuccessMsg(b: Boolean) {
        when {
            b -> displaySuccessDialog()
            else -> inputCaptcha.error = "incorrect, try again"
        }
    }

    private fun displaySuccessDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Correct")
        builder.setMessage("you got it!!!")
        builder.setPositiveButton("show me another") { d, _ ->
            refreshCaptcha()
            d.dismiss()
        }
        builder.setNegativeButton("close") { d, _ -> d.dismiss() }
        builder.create().show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_refresh -> {
                refreshCaptcha()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun refreshCaptcha() {
        inputCaptcha.setText("")
        inputCaptcha.error = null
        setCaptcha()
    }
}
