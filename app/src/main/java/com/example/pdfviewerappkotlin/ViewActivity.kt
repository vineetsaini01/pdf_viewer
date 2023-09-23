package com.example.pdfviewerappkotlin

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_view.*

class ViewActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private var currentPage=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view)

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE)
        getCurrentPage()
        showPDF()
    }

    override fun onPause() {
        super.onPause()
        saveCurrentPage()
    }

    private fun showPDF() {
        progress_bar.visibility = View.GONE
        pdf_view
            .fromAsset("Example_PDF_Kotlin_Wikipedia.pdf")
            .defaultPage(currentPage)
            .enableSwipe(true)
            .swipeHorizontal(false)
            .enableDoubletap(true)
            .onPageChange { page, _ ->
                currentPage=page
            }
            .load()
    }

    private fun saveCurrentPage() {
        val editor = sharedPreferences.edit()
        editor.putInt("currentPage", currentPage)
        editor.apply()
    }

    private fun getCurrentPage() {
        val defaultValue = 0
        currentPage= sharedPreferences.getInt("currentPage", defaultValue)
    }
}
