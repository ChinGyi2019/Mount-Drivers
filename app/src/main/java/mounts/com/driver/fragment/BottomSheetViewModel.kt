package mounts.com.driver.fragment


import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import mounts.com.driver.R


class BottomSheetViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    fun accept_Decline(view: View){
        if(view.id == R.id.acceptBtn){
         //   .toast("Accept")
            Log.e("onclick","Accept")

        }else if(view.id == R.id.button){
            //context.toast("Decline")
            Log.e("onclick","Decline")
        }
    }
}
