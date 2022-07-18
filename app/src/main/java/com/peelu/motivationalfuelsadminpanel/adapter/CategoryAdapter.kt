package com.peelu.motivationalfuelsadminpanel.adapter


import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.peelu.motivationalfuelsadminpanel.AllQuotesActivity
import com.peelu.motivationalfuelsadminpanel.MainActivity
import com.peelu.motivationalfuelsadminpanel.databinding.ItemCategoryBinding
import com.peelu.motivationalfuelsadminpanel.model.CategoryModel

class CategoryAdapter(private val mainActivity: MainActivity, private val quotes: ArrayList<CategoryModel>) :
    RecyclerView.Adapter<CategoryAdapter.CatViewHolder>() {
    class CatViewHolder( val binding: ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root)

    private val db = FirebaseFirestore.getInstance()

    private val colorsList = arrayListOf("#55efc4","#74b9ff","#d63031","#e17055","#9b59b6")


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        return CatViewHolder(
            ItemCategoryBinding.inflate(LayoutInflater.from(parent.context),parent, false))
    }

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        // Changing Color randomly
        if(position%5==0){
            holder.binding.itemText.setBackgroundColor(Color.parseColor(colorsList[0]))
        }else if(position%5==1){
            holder.binding.itemText.setBackgroundColor(Color.parseColor(colorsList[1]))
        }else if(position%5==2){
            holder.binding.itemText.setBackgroundColor(Color.parseColor(colorsList[2]))
        }else if(position%5==3){
            holder.binding.itemText.setBackgroundColor(Color.parseColor(colorsList[3]))
        }else if(position%5==4){
            holder.binding.itemText.setBackgroundColor(Color.parseColor(colorsList[4]))
        }

        // deleting category
        holder.binding.btnDelete.setOnClickListener{
            db.collection("Quotes").document(quotes[position].id!!).delete().addOnSuccessListener {
                Toast.makeText(mainActivity,"Category Deleted Successfully",Toast.LENGTH_SHORT).show()
            }
        }

        holder.binding.itemText.text = quotes[position].name.toString()
        holder.binding.root.setOnClickListener{
            val intent = Intent(mainActivity, AllQuotesActivity::class.java)
            intent.putExtra("id",quotes[position].id)
            intent.putExtra("name",quotes[position].name)
            mainActivity.startActivity(intent)
        }
    }




override fun getItemCount() = quotes.size
}