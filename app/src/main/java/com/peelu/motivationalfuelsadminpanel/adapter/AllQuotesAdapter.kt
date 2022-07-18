package com.peelu.motivationalfuelsadminpanel.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.peelu.motivationalfuelsadminpanel.AllQuotesActivity
import com.peelu.motivationalfuelsadminpanel.databinding.ItemQuotesBinding
import com.peelu.motivationalfuelsadminpanel.model.QuotesModel

class AllQuotesAdapter(
    private val allQuotesActivity: AllQuotesActivity,
    private val quotesList: ArrayList<QuotesModel>,
    private val categoryId: String
) : RecyclerView.Adapter<AllQuotesAdapter.QuotesViewHolder>(){
    private val db = FirebaseFirestore.getInstance()



    // implementing all members
    // added QuotesViewHolder class
    class QuotesViewHolder(val binding: ItemQuotesBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuotesViewHolder {
        // binding QuotesViewHolder with ItemQuotesBinding
        return  QuotesViewHolder(ItemQuotesBinding.inflate(LayoutInflater.from(parent.context),parent,false))


    }

    override fun onBindViewHolder(holder:QuotesViewHolder , position: Int) {


        holder.binding.itemQuotes.text = quotesList[position].data.toString()

        holder.binding.btnDelete.setOnClickListener{
            db.collection("Quotes")
                .document(categoryId)
                .collection("all")
                .document(quotesList[position].id!!)
                .delete()
                .addOnSuccessListener {
                    Toast.makeText(allQuotesActivity,"Deleted Successfully",Toast.LENGTH_SHORT).show()
                }
        }

    }

    override fun getItemCount() = quotesList.size



}