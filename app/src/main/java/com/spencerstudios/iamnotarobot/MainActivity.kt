package com.spencerstudios.iamnotarobot

import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.support.v4.content.res.ResourcesCompat
import android.support.v4.text.HtmlCompat
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
    private lateinit var tf : Typeface
    private lateinit var chars : ArrayList<Char>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        initCaptchaDimens()
        tf = ResourcesCompat.getFont(this, R.font.anonymous_pro_bold)!!
        chars = getChars()
        setCaptcha()

        inputCaptcha.setOnEditorActionListener { _, i, _ ->
            when (i) {
                EditorInfo.IME_ACTION_DONE ->
                    displaySuccessMsg(captcha.getAnswer() == inputCaptcha.text.toString())
            }
            true
        }

        acronym.text = HtmlCompat.fromHtml("<B><U>C</U></B>ompletely <B><U>A</U></B>utomated <B><U>P</U></B>ublic <B><U>T</U></B>uring <I>test to tell</I> <B><U>C</U></B>omputers <I>and</I> <B><U>H</U></B>umans <B><U>A</U></B>part",HtmlCompat.FROM_HTML_MODE_LEGACY)
        learnMore.text = HtmlCompat.fromHtml("<U>Learn More</U>", HtmlCompat.FROM_HTML_MODE_LEGACY)
        learnMore.setOnClickListener {
            val uri = Uri.parse("https://en.wikipedia.org/wiki/CAPTCHA")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }
    }

    private fun setCaptcha() {
        captcha = Captcha(captchaWidth, captchaHeight, chars, tf)
        captchaView.setImageBitmap(captcha.getImage())
    }

    private fun displaySuccessMsg(b: Boolean) {
        when {
            b -> displaySuccessDialog()
            else -> inputCaptcha.error = "incorrect, try again"
        }
    }

    private fun initCaptchaDimens(){
        val displayMetrics = DisplayMetrics()
        this.windowManager.defaultDisplay.getMetrics(displayMetrics)

        displayWidth = displayMetrics.widthPixels
        captchaWidth = (displayWidth / 2) + (displayWidth / 4)
        captchaHeight = captchaWidth / 4
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
