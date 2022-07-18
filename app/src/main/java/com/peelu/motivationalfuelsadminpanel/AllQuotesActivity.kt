package com.peelu.motivationalfuelsadminpanel

import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.peelu.motivationalfuelsadminpanel.adapter.AllQuotesAdapter
import com.peelu.motivationalfuelsadminpanel.databinding.ActivityAllQuotesBinding
import com.peelu.motivationalfuelsadminpanel.databinding.DialogAddShayariBinding
import com.peelu.motivationalfuelsadminpanel.model.QuotesModel

class AllQuotesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAllQuotesBinding
    private lateinit var db: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllQuotesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = FirebaseFirestore.getInstance()

        supportActionBar?.hide()

        val name = intent.getStringExtra("name")
        val id = intent.getStringExtra("id")

        binding.toolbarTitle.text = name.toString()

        db.collection("Quotes").document(id!!).collection("all")
            .addSnapshotListener{ value,_ ->
                val quotesList = arrayListOf<QuotesModel>()
                val data = value?.toObjects(QuotesModel::class.java)
                quotesList.addAll(data!!)
                binding.rcvAllQuotes.layoutManager = LinearLayoutManager(this)
                binding.rcvAllQuotes.adapter = AllQuotesAdapter(this,quotesList,id)

            }

        binding.btnAddQuotes.setOnClickListener{
            val addCatDialog = Dialog(this@AllQuotesActivity)
            val  binding = DialogAddShayariBinding.inflate(layoutInflater)
            addCatDialog.setContentView(binding.root)

            if(addCatDialog.window !=null){
                addCatDialog.window!!.setBackgroundDrawable(ColorDrawable(0))
            }

            binding.dialogBtnAddQuotes.setOnClickListener{
                val uniqueId = db.collection("Quotes").document().id
                val addQuotes = binding.DialogQuotes.text.toString()
                val finalData = QuotesModel(uniqueId,addQuotes)

                db.collection("Quotes")
                    .document(id)
                    .collection("all")
                    .document(uniqueId)
                    .set(finalData)
                    .addOnCompleteListener{
                        if(it.isSuccessful){
                            addCatDialog.dismiss()
                            Toast.makeText(this@AllQuotesActivity,"Quotes Added Successfully",Toast.LENGTH_SHORT).show()
                        }
                        Toast.makeText(this@AllQuotesActivity,"" + it.exception?.localizedMessage,Toast.LENGTH_SHORT).show()
                    }
            }
            addCatDialog.show()
        }
    }

}
