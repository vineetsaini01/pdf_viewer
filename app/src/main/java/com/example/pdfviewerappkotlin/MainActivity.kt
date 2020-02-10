package com.example.pdfviewerappkotlin

import android.Manifest
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.BaseMultiplePermissionsListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object{
        private val PICK_PDF_CODE = 1000
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Dexter.withActivity(this)
            .withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .withListener(object:BaseMultiplePermissionsListener(){
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    super.onPermissionsChecked(report)
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<PermissionRequest>?,
                    token: PermissionToken?
                ) {
                    super.onPermissionRationaleShouldBeShown(permissions, token)
                }
            })

        btn_view_assets.setOnClickListener {
            val intent = Intent(this@MainActivity,ViewActivity::class.java)
            intent.putExtra("ViewType","assets")
            startActivity(intent)
        }

        btn_view_storage.setOnClickListener {
            val pdfIntent = Intent(Intent.ACTION_GET_CONTENT)
            pdfIntent.type = "application/pdf"
            pdfIntent.addCategory(Intent.CATEGORY_OPENABLE)
            startActivityForResult(Intent.createChooser(pdfIntent,"Select PDF"), PICK_PDF_CODE)
        }

        btn_view_internet.setOnClickListener {
            val intent = Intent(this@MainActivity,ViewActivity::class.java)
            intent.putExtra("ViewType","internet")
            startActivity(intent)
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == PICK_PDF_CODE && resultCode == Activity.RESULT_OK && data != null){
            val selectedPDF = data.data
            val intent = Intent(this@MainActivity,ViewActivity::class.java)
            intent.putExtra("ViewType","storage")
            intent.putExtra("FileUri",selectedPDF.toString())
            startActivity(intent)
        }
    }
}
