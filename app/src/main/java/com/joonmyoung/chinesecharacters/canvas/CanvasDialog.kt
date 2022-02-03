package com.joonmyoung.chinesecharacters.canvas

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.Gravity
import android.view.MotionEvent
import android.view.WindowManager
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.joonmyoung.chinesecharacters.R


class CanvasDialog : DialogFragment(){
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//        val mDialogView = LayoutInflater.from(this).inflate(R.layout.item_view, null)
        val mBuilder =
            AlertDialog.Builder(requireContext(), R.style.AppTheme_AlertDialogTheme).setView(CanvasView(context)).setNegativeButton("닫기",
                DialogInterface.OnClickListener(){
                    dialogInterface, i ->
                dismiss()

            })
//            .setPositiveButton("지우기",DialogInterface.OnClickListener(){
//                dialogInterface, i ->
////                CanvasView(context).path.reset()
//            })

//        val alertDialog = mBuilder.show()

        return mBuilder.create()
    }

    override fun onStart() {
        super.onStart()

        dialog?.window?.setGravity(Gravity.BOTTOM)
        dialog?.window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)



    }


//        alertDialog.findViewById<Button>(R.id.closeBtn)?.setOnClickListener {
//            alertDialog.dismiss()
//        }
}