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

class LoginDialog(private val context: Context) {
    private lateinit var itemClickListener: OnItemClickListener

    fun showLoginDialog() {
        val loginDialog = Dialog(context)
        val binding = DialogLoginBinding.inflate(LayoutInflater.from(context))

        loginDialog.setContentView(binding.root)
        loginDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        loginDialog.setCanceledOnTouchOutside(false)
        loginDialog.show()

        binding.btnDialogLogin.setOnClickListener {
            loginDialog.dismiss()
            itemClickListener.onCompleteClick(it)
        }

        binding.btnDialogLoginClose.setOnClickListener {
            loginDialog.dismiss()
        }
    }

    interface OnItemClickListener {
        fun onCompleteClick(v: View)
    }

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }
}