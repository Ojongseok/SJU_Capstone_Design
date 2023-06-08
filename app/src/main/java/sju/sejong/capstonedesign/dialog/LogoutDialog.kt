package sju.sejong.capstonedesign.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.content.getSystemService
import androidx.fragment.app.DialogFragment
import sju.sejong.capstonedesign.databinding.DialogLoginBinding
import sju.sejong.capstonedesign.databinding.DialogLogoutBinding

class LogoutDialog(private val context: Context) {
    private lateinit var itemClickListener: OnItemClickListener

    fun showLogoutDialog() {
        val logoutDialog = Dialog(context)
        val binding = DialogLogoutBinding.inflate(LayoutInflater.from(context))

        logoutDialog.setContentView(binding.root)
        logoutDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        logoutDialog.setCanceledOnTouchOutside(false)
        logoutDialog.show()

        binding.dialogLogoutComplete.setOnClickListener {
            logoutDialog.dismiss()
            itemClickListener.onCompleteClick(it)
        }

        binding.dialogLogoutCancel.setOnClickListener {
            logoutDialog.dismiss()
        }
    }

    interface OnItemClickListener {
        fun onCompleteClick(v: View)
    }

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }
}