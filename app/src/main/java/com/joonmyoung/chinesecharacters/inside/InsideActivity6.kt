package com.joonmyoung.chinesecharacters.inside

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.joonmyoung.chinesecharacters.Adapter.CharacterModel
import com.joonmyoung.chinesecharacters.Adapter.CharacterRVAdapter
import com.joonmyoung.chinesecharacters.R
import com.joonmyoung.chinesecharacters.canvas.CanvasDialog
import com.joonmyoung.chinesecharacters.databinding.ActivityInside6Binding
import com.joonmyoung.chinesecharacters.setting.SettingData
import org.json.JSONArray
import org.json.JSONObject
import java.security.SecureRandom

class InsideActivity6 : AppCompatActivity() {

    private val binding by lazy {
        ActivityInside6Binding.inflate(layoutInflater)
    }
    private val characterDataList = mutableListOf<CharacterModel>()
    private lateinit var characterRVAdapter: CharacterRVAdapter
    var check = SettingData.check
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.backBtn.setOnClickListener {
            finish()
        }
        val jsonString = assets.open("data.json").reader().readText()
        Log.d("jsonString",jsonString)

        val jsonArray = JSONArray(jsonString)
        Log.d("jsonArray",jsonArray.toString())

        val mainTitle = jsonArray.getJSONObject(8)
        Log.d("getJSONArray",mainTitle.toString())

        val jsonObject = mainTitle.getJSONArray("준3급.txt")
        Log.d("getJSONObject",jsonObject.toString())


        for (i in 0 until jsonObject.length()) {

            val jsonOb = jsonObject.getJSONObject(i)
            val title = jsonOb.getString("title")
            val radical = jsonOb.getString("radical")
            val mean = jsonOb.getString("mean")
            val writeCount = jsonOb.getString("writeCount")
            val image = jsonOb.getString("img")


            characterDataList.add(CharacterModel(title,radical,mean, writeCount, image))

        }



        val characterRV = findViewById<RecyclerView>(R.id.characterRV)




//        val dividerItemDecoration = DividerItemDecoration(this,GridLayoutManager.HORIZONTAL)
//        characterRV.addItemDecoration(dividerItemDecoration)

        characterRV.layoutManager= GridLayoutManager(this,4)
        characterRVAdapter = CharacterRVAdapter(characterDataList,this,check)
        characterRV.adapter = characterRVAdapter


        binding.member1.setOnClickListener {
            characterDataList.shuffle()
            characterRVAdapter.notifyDataSetChanged()
        }
        binding.member2.setOnClickListener {
            val dialog = CanvasDialog()
            dialog.show(supportFragmentManager,null)
        }
        binding.member3.setOnClickListener {

            testShowDialog(characterDataList)


        }






    }
    private fun testShowDialog(characterList:MutableList<CharacterModel>) {

        val index2 = SecureRandom()
        val index = index2.nextInt(characterDataList.size-1)


        val mDialogView = LayoutInflater.from(this).inflate(R.layout.dialog_test, null)

        val mBuilder = AlertDialog.Builder(this).setView(mDialogView).setCancelable(false)

        val alertDialog = mBuilder.show()

        val question = alertDialog.findViewById<TextView>(R.id.dialog_question)
        val radical = alertDialog.findViewById<TextView>(R.id.dialog_radical)
        val mean = alertDialog.findViewById<TextView>(R.id.dialog_mean)
        val writeCount = alertDialog.findViewById<TextView>(R.id.dialog_writeCount)
        radical?.visibility = View.INVISIBLE
        mean?.visibility = View.INVISIBLE
        writeCount?.visibility = View.INVISIBLE


        question?.text = characterList[index].title
        radical?.text = characterList[index].radical
        mean?.text = characterList[index].mean
        writeCount?.text = characterList[index].writeCount



        alertDialog.findViewById<Button>(R.id.answerBtn)?.setOnClickListener {
            radical?.visibility = View.VISIBLE
            mean?.visibility = View.VISIBLE
            writeCount?.visibility = View.VISIBLE
        }
        alertDialog.findViewById<Button>(R.id.nextBtn)?.setOnClickListener {

            alertDialog.dismiss()
            testShowDialog(characterList)

        }

        alertDialog.findViewById<Button>(R.id.closeBtn)?.setOnClickListener {
            alertDialog.dismiss()
        }

    }
}