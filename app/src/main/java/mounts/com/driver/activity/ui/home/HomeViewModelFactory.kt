package mounts.com.driver.activity.ui.home

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import mounts.com.driver.activity.ui.gallery.GalleryViewModel
import mounts.com.driver.data.repositories.MapRepsitory
import mounts.com.driver.data.repositories.UserRepository

@Suppress("UNCHECKED_CAST")
class HomeViewModelFactory(private val repository: MapRepsitory,private val application: Application):
        ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeViewModel(repository,application) as T
    }
}