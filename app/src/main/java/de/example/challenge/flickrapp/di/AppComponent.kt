package de.example.challenge.flickrapp.di

import dagger.Component
import de.example.challenge.flickrapp.fragments.FlowFragment
import de.example.challenge.flickrapp.fragments.childFragments.HistoryFragment
import de.example.challenge.flickrapp.fragments.childFragments.HistoryViewModel
import de.example.challenge.flickrapp.fragments.childFragments.search.SearchingFragment
import javax.inject.Singleton

@Component(modules = [AppModule::class, DataModule::class, DomainModule::class])
@Singleton
interface AppComponent {
    fun inject(flowFragment: FlowFragment)
    fun inject(historyFragment: HistoryFragment)
    fun inject(searchingFragment: SearchingFragment)
}