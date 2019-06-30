package com.utn.nutricionista.detalleComida

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.chrisbanes.photoview.PhotoView
import com.utn.nutricionista.ApiClient
import com.utn.nutricionista.R
import com.utn.nutricionista.models.Comida
import com.utn.nutricionista.models.Diet
import com.utn.nutricionista.models.MomentoComida
import kotlinx.android.synthetic.main.activity_detalle_comida.*
import kotlinx.android.synthetic.main.activity_detalle_comida.fab
import kotlinx.android.synthetic.main.activity_weight.*
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

        val dietaSeleccionada = getIntentExtras()
        setContentView(R.layout.activity_detalle_comida)

        refreshDietaPredef(dietaConcreta, dietaSeleccionada.nombre)
        refreshDietaExtras(dietaConcreta, dietaSeleccionada.nombre)

       /* TODO:
        toolbarDetalle!!.title = dietaSeleccionada.nombre.capitalize()
        setSupportActionBar(toolbarDetalle)*/

        takePictureIntent(dietaSeleccionada.foto)
        fab.setOnClickListener { view -> openAddFoodRecord(view) }
    }


    private fun openAddFoodRecord(view: View) {
        InputFoodDialogFragment().show(this.supportFragmentManager,"inputFood")
    }

    fun saveNewFoodRecord(foodName : String) {
        val nuevaComida = Comida(true, foodName )
        val dietaConcreta = getExtrasDietaConcreta()
        val dietaSeleccionada = getExtrasDietaSeleccionada()
        dietaConcreta.addNewComida( dietaSeleccionada.nombre, nuevaComida )
        ApiClient.putDieta(dietaConcreta).addOnSuccessListener {
            val postedDieta = it
            Log.d("SUCCESS", "Saved Id:${postedDieta.id} with fecha ${postedDieta.fecha}, Action: Add new food: ${nuevaComida.nombreComida}")
        }.addOnFailureListener { e ->
            Log.d("ERROR", "Insert failed with error ${e.message}}")
        }

        refreshDietaExtras(dietaConcreta, dietaSeleccionada.nombre)
    }

    fun getIntentExtras() : MomentoComida{

        return intent.extras!!["dietaSeleccionada"]!! as MomentoComida
    }

    private fun getExtrasDietaConcreta() : Diet {
        return intent.extras!!["dietaConcreta"]!! as Diet
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val dietaConcreta = getExtrasDietaConcreta()
        val dietaSeleccionada = getExtrasDietaSeleccionada()
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            val imageView = findViewById<ImageView>(R.id.comidaImage)

            updateDietaConcreta(dietaConcreta, dietaSeleccionada )
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

    private fun updateDietaConcreta(dietaConcreta: Diet, momento: MomentoComida){

        dietaConcreta.updateFotoComida( momento.nombre, currentPhotoPath.split("/").last() )
        ApiClient.putDieta(dietaConcreta).addOnSuccessListener {
            val postedDieta = it
            Log.d("SUCCESS", "Saved Id:${postedDieta.id} with fecha ${postedDieta.fecha}, Action: Updated foto path: ${momento.foto}")
        }.addOnFailureListener { e ->
            Log.d("ERROR", "Insert failed with error ${e.message}}")
        }
    }


    private fun refreshDietaPredef(dietaConcreta: Diet, nombreMomento: String){
        val momento = dietaConcreta.getMomento(nombreMomento)
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.dietaPropuestaFragmentContainer,
                DetalleComidaFragment.newDietaInstance(momento, DIETA_PREDEF, dietaConcreta)
            )
            .commit()
    }

    private fun refreshDietaExtras(dietaConcreta: Diet, nombreMomento: String) {
        val momento = dietaConcreta.getMomento(nombreMomento)
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.dietaExtraFragmentContainer,
                DetalleComidaFragment.newDietaInstance(momento, FUERA_DIETA_PREDEF, dietaConcreta)
            )
            .commit()
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
