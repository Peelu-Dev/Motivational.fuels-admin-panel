package com.peelu.motivationalfuelsadminpanel

import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.peelu.motivationalfuelsadminpanel.adapter.CategoryAdapter
import com.peelu.motivationalfuelsadminpanel.databinding.ActivityMainBinding
import com.peelu.motivationalfuelsadminpanel.databinding.DialogAddCategoryBinding
import com.peelu.motivationalfuelsadminpanel.model.CategoryModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var db: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        db = FirebaseFirestore.getInstance()

        binding.btnAddCat.setOnClickListener {
            val addCatDialog = Dialog(this@MainActivity)
            val binding = DialogAddCategoryBinding.inflate(layoutInflater)
            addCatDialog.setContentView(binding.root)

            if (addCatDialog.window != null) {
                addCatDialog.window!!.setBackgroundDrawable(ColorDrawable(0))
            }

            binding.dialogBtnAddCat.setOnClickListener {
                // neccessary variables
                val name = binding.addCat.text.toString()
                val id = db.collection("Quotes").document().id
                val data = CategoryModel(id,name)

                // Fetching Data
                db.collection("Quotes").document(id).set(data).addOnSuccessListener {
                    Toast.makeText(this@MainActivity,"Add",Toast.LENGTH_SHORT).show()
                    addCatDialog.dismiss()
                }.addOnCanceledListener {
                    Toast.makeText(this@MainActivity,"$it",Toast.LENGTH_SHORT).show()
                }
            }
            addCatDialog.show()
        }

        db.collection("Quotes").addSnapshotListener{ value,_ ->
            val quotes = arrayListOf<CategoryModel>()
            val data = value?.toObjects(CategoryModel::class.java)
            quotes.addAll(data!!)
            binding.recyclerViewCategory.layoutManager = LinearLayoutManager(this)
            binding.recyclerViewCategory.adapter = CategoryAdapter(this,quotes)
        }
    }
}