package com.aliraza.mad_final_exam

import android.R.attr.key
import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.activity_sell.*
import java.io.IOException
import java.util.*
import kotlin.collections.HashMap


class SellActivity : AppCompatActivity() {

    private val PICK_IMAGE_REQUEST = 71
    private var filePath: Uri? = null
    private var firebaseStore: FirebaseStorage? = null
    private var storageReference: StorageReference? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sell)

        val progressDialog = ProgressDialog(this@SellActivity)
        progressDialog.setTitle("Uploading")
        progressDialog.setMessage("Please wait...")
        progressDialog.setCancelable(false)


        firebaseStore = FirebaseStorage.getInstance()
        storageReference = FirebaseStorage.getInstance().reference

        gallBtn.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(
                Intent.createChooser(intent, "Select Picture"),
                PICK_IMAGE_REQUEST
            )
        }

        uploadBtn.setOnClickListener {
            if (filePath != null) {
                progressDialog.show()
                val ref = storageReference?.child("uploads/" + UUID.randomUUID().toString())
                val uploadTask = ref?.putFile(filePath!!)
                uploadTask?.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
                        if (!task.isSuccessful) {
                            task.exception?.let {
                                throw it
                            }
                        }
                        return@Continuation ref.downloadUrl
                    })?.addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val downloadUri = task.result
                            val sellMap: MutableMap<String, String> = HashMap()
                            sellMap["make"] = sellMake.text.toString()
                            sellMap["model"] = sellModel.text.toString()
                            sellMap["mileage"] = sellMileage.text.toString()
                            sellMap["images"] = downloadUri.toString()
                            sellMap["description"] = selldesc.text.toString()
                            sellMap["color"] = sellColor.text.toString()
                            sellMap["city"] = sellCity.text.toString()
                            val database = FirebaseDatabase.getInstance()
                            val myRef = database.getReference("Sells").child(FirebaseAuth.getInstance().uid.toString())
                            myRef.setValue(sellMap).addOnCompleteListener {task ->
                                if(task.isSuccessful){
                                    progressDialog.cancel()
                                    Toast.makeText(this, "Uploaded!!", Toast.LENGTH_SHORT).show()
                                    finish()

                                }else{
                                    progressDialog.cancel()
                                    Toast.makeText(this, ""+task.exception!!.message, Toast.LENGTH_SHORT).show()
                                }
                            }
                        } else {
                            // Handle failures
                        }
                    }?.addOnFailureListener {
                    progressDialog.cancel()
                    Toast.makeText(this, ""+it.message, Toast.LENGTH_SHORT).show()
                    }
            } else {
                progressDialog.cancel()
                Toast.makeText(this, "Please Upload an Image", Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            if (data == null || data.data == null) {
                return
            }
            filePath = data.data
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filePath)
                uploadImage.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

}