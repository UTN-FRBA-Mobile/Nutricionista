package com.utn.nutricionista.detalleComida

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.github.chrisbanes.photoview.PhotoView
import com.utn.nutricionista.R
import com.utn.nutricionista.models.Diet
import com.utn.nutricionista.models.MomentoComida
import kotlinx.android.synthetic.main.activity_detalle_comida.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class DetalleComidaActivity : AppCompatActivity(),
    DetalleComidaFragment.OnListFragmentInteractionListener {

    private val REQUEST_IMAGE_CAPTURE = 1
    private var currentPhotoPath: String = ""
    private val DIETA_PREDEF = 1
    private val FUERA_DIETA_PREDEF = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dietaConcreta = getExtrasDietaConcreta()
        val dietaSeleccionada = getExtrasDietaSeleccionada()
        setContentView(R.layout.activity_detalle_comida)
                supportFragmentManager.beginTransaction()
            .replace(
                R.id.dietaPropuestaFragmentContainer,
                DetalleComidaFragment.newDietaInstance(dietaSeleccionada, DIETA_PREDEF, dietaConcreta)
            )
            .commit()
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.dietaExtraFragmentContainer,
                DetalleComidaFragment.newDietaInstance(dietaSeleccionada, FUERA_DIETA_PREDEF, dietaConcreta)
            )
            .commit()

       /* TODO:
        toolbarDetalle!!.title = dietaSeleccionada.nombre.capitalize()
        setSupportActionBar(toolbarDetalle)*/

        takePictureIntent(dietaSeleccionada.foto)

    }


    fun getExtrasDietaSeleccionada() : MomentoComida{

        return intent.extras!!["dietaSeleccionada"]!! as MomentoComida
    }

    private fun getExtrasDietaConcreta() : Diet {
        return intent.extras!!["dietaConcreta"]!! as Diet
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            val imageView = findViewById<ImageView>(R.id.comidaImage)
            imageView.setImageBitmap(BitmapFactory.decodeFile(currentPhotoPath))
            imageView.setOnClickListener{

                val mBuilder = AlertDialog.Builder(this)
                val mView = layoutInflater.inflate(R.layout.fullscreen_image, null)
                val photoView = mView.findViewById<PhotoView>(R.id.fullscreenImage)
                photoView.setImageBitmap(BitmapFactory.decodeFile(currentPhotoPath))
                mBuilder.setView(mView)
                val mDialog = mBuilder.create()
                mDialog.show()

            }
        }
    }


    private fun takePictureIntent(path: String) {

        val checkFile = File(getExternalFilesDir(Environment.DIRECTORY_PICTURES).absolutePath+"/"+path)

        if(checkFile.exists()){
            val photoFile: String? = getExternalFilesDir(Environment.DIRECTORY_PICTURES+"/"+path)?.absolutePath
            val imageView = findViewById<ImageView>(R.id.comidaImage)

            imageView.setImageBitmap(BitmapFactory.decodeFile(photoFile))

        }else {
            Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
                takePictureIntent.resolveActivity(packageManager)?.also {
                    val photoFile: File? = try {
                        createImageFile()
                    } catch (ex: IOException) {
                        // Error occurred while creating the File
                        null
                    }

                    // Continue only if the File was successfully created
                    photoFile?.also {
                        val photoURI: Uri = FileProvider.getUriForFile(
                            this,
                            "com.nutricionistas.android.fileprovider",
                            it
                        )
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)

                    }
                }
            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "Detalle_Comida_3",
            ".jpg",
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }


    override fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.dietaPropuestaFragmentContainer, fragment)
            .commit()
    }

}
