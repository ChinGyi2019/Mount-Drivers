package mounts.com.driver.activity.ui.gallery

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.messaging.FirebaseMessagingService
import kotlinx.android.synthetic.main.fragment_gallery.*
import mounts.com.driver.R
import mounts.com.driver.activity.ui.home.HomeViewModelFactory
import mounts.com.driver.databinding.FragmentGalleryBinding
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance


class GalleryFragment : Fragment(),KodeinAware{
    override val kodein by kodein()
    private val factory : GalleryViewModelFactory by instance()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding :FragmentGalleryBinding= DataBindingUtil.inflate(inflater,R.layout.fragment_gallery,container,false)
        val viewModel =
                ViewModelProviders.of(this,factory).get(GalleryViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.getFCMToken().observe(viewLifecycleOwner, Observer {fcmToken->
            if(fcmToken.fcmToken != null){
            text_gallery.setText(fcmToken.fcmToken.toString())
                Log.e("Gallery:FCM ",fcmToken.fcmToken)
            }else {
                text_gallery.setText("**** null ****")
                Log.e("Gallery:FCM ","**** null ****")
            }
        })

        viewModel.getRealtimeToken().observe(viewLifecycleOwner, Observer { token->
            if(token != null){
                //viewModel.login(token.token.toString())
                textView2.setText("${token.token}")
                Log.e("Gallery:realtime Token","${token.token}")
            }else {
                textView2.setText("****  Real time token null ****")
                Log.e("Gallery:realtime Token","**** null ****")
            }
        })



        return binding.root
    }
}