package kt.example.wxapi.dagger.component

import dagger.Component
import kt.example.wxapi.dagger.module.OpenWeatherAPIModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(OpenWeatherAPIModule::class))
interface OpenWeatherAPIComponent {
    fun inject(presenter: MainPresenter);
}