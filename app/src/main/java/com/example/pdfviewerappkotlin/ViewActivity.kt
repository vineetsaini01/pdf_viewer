package com.example.pdfviewerappkotlin

import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import com.krishna.fileloader.FileLoader
import com.krishna.fileloader.listener.FileRequestListener
import com.krishna.fileloader.pojo.FileResponse
import com.krishna.fileloader.request.FileLoadRequest
import kotlinx.android.synthetic.main.activity_view.*
import java.io.File

class ViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view)

        if(intent != null){
            val viewType = intent.getStringExtra("ViewType")

            if(!TextUtils.isEmpty(viewType) || viewType != null){
                if(viewType.equals("assets")){
                    progress_bar.visibility = View.GONE // Hide progress bar
                    pdf_view.fromAsset("Example_PDF_Kotlin_Wikipedia.pdf")
                        .password(null) // Only if have password.
                        .defaultPage(0)
                        .enableSwipe(true)
                        .swipeHorizontal(false)
                        .enableDoubletap(true) // Double tap to zoom
                        .onDraw{canvas, pageWidth, pageHeight, displayedPage ->
                            //Enter code here
                        }
                        .onDrawAll{canvas, pageWidth, pageHeight, displayedPage ->
                            // Enter code here
                        }
                        .onPageChange{page, pageCount ->
                            // Enter code here
                        }
                        .onPageError{page, t ->
                            Toast.makeText(this@ViewActivity,"Error while opening page" +page, Toast.LENGTH_LONG).show()
                            Log.d("ERROR", ""+t.localizedMessage)
                        }
                        .onTap { false }
                        .onRender{nbPages, pageWidth, pageHeight ->
                            pdf_view.fitToWidth() // Fit to screen size
                        }
                        .enableAnnotationRendering(true)
                        .invalidPageColor(Color.RED)
                        .load()
                }
                else if(viewType.equals("storage")){
                    val selectedPDF = Uri.parse(intent.getStringExtra("FileUri"))

                    pdf_view.fromUri(selectedPDF)
                        .password(null) // Only if have password.
                        .defaultPage(0)
                        .enableSwipe(true)
                        .swipeHorizontal(false)
                        .enableDoubletap(true) // Double tap to zoom
                        .onDraw{canvas, pageWidth, pageHeight, displayedPage ->
                            //Enter code here
                        }
                        .onDrawAll{canvas, pageWidth, pageHeight, displayedPage ->
                            // Enter code here
                        }
                        .onPageChange{page, pageCount ->
                            // Enter code here
                        }
                        .onPageError{page, t ->
                            Toast.makeText(this@ViewActivity,"Error while opening page" +page, Toast.LENGTH_LONG).show()
                            Log.d("ERROR", ""+t.localizedMessage)
                        }
                        .onTap { false }
                        .onRender{nbPages, pageWidth, pageHeight ->
                            pdf_view.fitToWidth() // Fit to screen size
                        }
                        .enableAnnotationRendering(true)
                        .invalidPageColor(Color.RED)
                        .load()
                }

                else if(viewType.equals("internet")){
                    progress_bar.visibility = View.VISIBLE

                    FileLoader.with(this)
                        .load("https://kotlinlang.org/assets/kotlin-media-kit.pdf", true)
                        .fromDirectory("PDFFile", FileLoader.DIR_EXTERNAL_PUBLIC)
                        .asFile(object:FileRequestListener<File>{
                            override fun onLoad(
                                request: FileLoadRequest?,
                                response: FileResponse<File>?
                            ) {
                                progress_bar.visibility = View.GONE // Hide progress bar

                                val pdfFile = response!!.body

                                pdf_view.fromFile(pdfFile)
                                    .password(null) // Only if have password.
                                    .defaultPage(0)
                                    .enableSwipe(true)
                                    .swipeHorizontal(false)
                                    .enableDoubletap(true) // Double tap to zoom
                                    .onDraw{canvas, pageWidth, pageHeight, displayedPage ->
                                        //Enter code here
                                    }
                                    .onDrawAll{canvas, pageWidth, pageHeight, displayedPage ->
                                        // Enter code here
                                    }
                                    .onPageChange{page, pageCount ->
                                        // Enter code here
                                    }
                                    .onPageError{page, t ->
                                        Toast.makeText(this@ViewActivity,"Error while opening page" +page, Toast.LENGTH_LONG).show()
                                        Log.d("ERROR", ""+t.localizedMessage)
                                    }
                                    .onTap { false }
                                    .onRender{nbPages, pageWidth, pageHeight ->
                                        pdf_view.fitToWidth() // Fit to screen size
                                    }
                                    .enableAnnotationRendering(true)
                                    .invalidPageColor(Color.RED)
                                    .load()
                            }

                            override fun onError(request: FileLoadRequest?, t: Throwable?) {
                                Toast.makeText(this@ViewActivity,"Error On Load",Toast.LENGTH_LONG).show()

                                progress_bar.visibility = View.GONE // Hide progress bar
                            }

                        })
                }
            }
        }
    }
}
