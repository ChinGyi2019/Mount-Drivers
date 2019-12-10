package mounts.com.driver.activity.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import mounts.com.driver.R
import mounts.com.driver.Util.toast
import mounts.com.driver.activity.MainActivity
import mounts.com.driver.data.db.AppDatabase
import mounts.com.driver.data.netwrok.MyApi
import mounts.com.driver.data.repositories.UserRepository
import mounts.com.driver.databinding.ActivityLoginBinding
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class LoginActivity : AppCompatActivity(),AuthListener,KodeinAware {

    override val kodein by kodein()
    private val factory : AuthViewModelFactory by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //val api = MyApi()
       // val db = AppDatabase(this)
      //  val repository = UserRepository(api, db)

        //val factory = AuthViewModelFactory(repository)

        val LoginActivityBinding: ActivityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        val viewModel = ViewModelProviders.of(this,factory).get(AuthViewModel::class.java)
        LoginActivityBinding.viewmodel = viewModel

        viewModel.authListener =this

        viewModel.getLoginedUser().observe(this, Observer { user ->
            if(user != null){

                Intent(this, MainActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                }
            }
        })





    }

    override fun onStarted() {
        toast("Login Started")
       // progress_bar.show()
        }

    override fun onSuccess(isSuccessful: Boolean?) {
        toast("User is Logging in")

    }

    override fun onFailure(message: String?) {
        toast(message!!)
    }

    override fun onStart() {
        super.onStart()
        val LoginActivityBinding: ActivityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        val viewModel = ViewModelProviders.of(this,factory).get(AuthViewModel::class.java)
        LoginActivityBinding.viewmodel = viewModel

        viewModel.getLoginedUser().observe(this, Observer { user ->

            if(user != null){
                Intent(this, MainActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                }
            }
        })
    }

}