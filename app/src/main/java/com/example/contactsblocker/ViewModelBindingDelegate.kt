package com.example.contactsblocker


import io.reactivex.disposables.Disposable
import java.lang.ref.WeakReference
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class ViewModelBinder(private val activityRef: WeakReference<BaseViewModelActivity>) {

        fun bindViewModel(viewModel: BaseViewModel) {

            activityRef.get()?.compositeDisposable?.addAll(*observeAllStates(viewModel))
            viewModel.onBind()
        }

    private fun observeAllStates(viewModel: BaseViewModel): Array<Disposable> {
        val disposableList :ArrayList<Disposable> = arrayListOf()

        if(viewModel.lifecycleState.hasNoObservers())
            disposableList.add(viewModel.lifecycleState.subscribe { state -> handleLifecycleStateChange(state) })


        return disposableList.toTypedArray()
    }

    private fun handleLifecycleStateChange(state: ViewModelLifecycleState) {
        activityRef.get()?.let {
                when (state) {
                    is ViewModelLifecycleState.StartWithRequest -> it.onViewModelStartWithRequest(state)
                    is ViewModelLifecycleState.FinishedWithResult -> it.onViewModelFinish()//generic for any other call to activity
                }
        }
    }

}

class ViewModelActivityBindingDelegate : ReadOnlyProperty<BaseViewModelActivity, ViewModelBinder> {
    override fun getValue(thisRef: BaseViewModelActivity, property: KProperty<*>): ViewModelBinder {
        return ViewModelBinder(WeakReference(thisRef))
    }
}
