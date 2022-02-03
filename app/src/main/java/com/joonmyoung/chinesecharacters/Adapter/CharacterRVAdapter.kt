package com.joonmyoung.chinesecharacters.Adapter

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.util.Log
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.joonmyoung.chinesecharacters.R

class CharacterRVAdapter(val characterList:MutableList<CharacterModel>,val context: Context,val check:Boolean):RecyclerView.Adapter<CharacterRVAdapter.ViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CharacterRVAdapter.ViewHolder {
       val v = LayoutInflater.from(parent.context).inflate(R.layout.character_list_item,parent,false)
    return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: CharacterRVAdapter.ViewHolder, position: Int) {



       if (!check){
           val title = holder.itemView.findViewById<TextView>(R.id.title)
           title.text = characterList[position].title
       }
       if (check){
           val title = holder.itemView.findViewById<TextView>(R.id.title)
           title.text = characterList[position].mean
       }



        holder.itemView.setOnClickListener {
            showDialog(position,holder)
        }

    }

    override fun getItemCount(): Int {
        return characterList.size
    }

    class ViewHolder(view:View):RecyclerView.ViewHolder(view){

    }
    private fun showDialog(position: Int, holder: ViewHolder) {
        val mDialogView = LayoutInflater.from(context).inflate(R.layout.dialog_character, null)
        val mBuilder = AlertDialog.Builder(context).setView(mDialogView)

        val alertDialog = mBuilder.show()

        val title = alertDialog.findViewById<TextView>(R.id.dialog_title)
        val radical = alertDialog.findViewById<TextView>(R.id.dialog_radical)
        val mean = alertDialog.findViewById<TextView>(R.id.dialog_mean)
        val writeCount = alertDialog.findViewById<TextView>(R.id.dialog_writeCount)
//        val image = alertDialog.findViewById<ImageView>(R.id.dialog_image)

//        val imageUri = characterList[position].image
//        Log.d("TAG",imageUri)


        title?.text = characterList[position].title
        radical?.text = characterList[position].radical
        mean?.text = characterList[position].mean
        writeCount?.text = characterList[position].writeCount

//        val imageView = context.resources.getIdentifier("@drawable/"+imageUri,"drawable",context.packageName)
//        image?.setImageResource(imageView)

        alertDialog.findViewById<Button>(R.id.closeBtn)?.setOnClickListener {
            alertDialog.dismiss()
        }



    }


}