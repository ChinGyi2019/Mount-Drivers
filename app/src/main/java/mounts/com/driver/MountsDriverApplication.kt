package mounts.com.driver

import android.app.Application
import android.app.Service
import com.google.firebase.messaging.FirebaseMessagingService
import mounts.com.driver.Helpers.FirebaseHelper
import mounts.com.driver.Helpers.GoogleMapHelper
import mounts.com.driver.Helpers.MarkerAnimationHelper
import mounts.com.driver.Service.MyFirebaseMessagingService
import mounts.com.driver.activity.ui.auth.AuthViewModelFactory
import mounts.com.driver.activity.ui.gallery.GalleryViewModel
import mounts.com.driver.activity.ui.gallery.GalleryViewModelFactory
import mounts.com.driver.activity.ui.home.HomeViewModelFactory
import mounts.com.driver.data.db.AppDatabase
import mounts.com.driver.data.netwrok.FirebaseSource
import mounts.com.driver.data.netwrok.MyApi
import mounts.com.driver.data.netwrok.NetworkConnectionInterceptor
import mounts.com.driver.data.netwrok.getFCM_Token
import mounts.com.driver.data.repositories.MapRepsitory
import mounts.com.driver.data.repositories.UserRepository
import org.kodein.di.generic.bind
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton
import java.util.logging.LogManager
import java.util.logging.Logger

class MountsDriverApplication :Application(),KodeinAware{

    override val kodein = Kodein.lazy {
        import(androidXModule(this@MountsDriverApplication))


        bind() from singleton{ NetworkConnectionInterceptor(instance()) }
         bind() from singleton{ GoogleMapHelper()}
       // bind() from singleton{UiHelper}
        bind() from singleton { MarkerAnimationHelper() }
        bind() from singleton { FirebaseSource(instance()) }
        bind() from singleton { FirebaseHelper() }
        bind() from singleton { MyApi(instance()) }
        bind() from singleton { AppDatabase(instance())}

        //bind<Logger>() with scopedSingleton(androidServiceScope) { LogManager.getNamedLogger(it.localClassName) }
        //bind() from provider  {MyFirebaseMessagingService(instance())}
        bind() from singleton { getFCM_Token(instance()) }
        bind() from singleton { MapRepsitory(instance(),instance(),instance(),instance()) }
        bind() from singleton { UserRepository(instance(),instance(),instance(),instance())}
        bind() from provider { AuthViewModelFactory(instance())}
        bind() from provider { GalleryViewModelFactory(instance()) }
        bind() from provider { HomeViewModelFactory(instance(),instance()) }

    }
}