package ru.tashkent.stocks.other

import androidx.lifecycle.Observer

class UiObserver<T>(
    private val onError: ((String) -> Unit)? = null,
    private val onLoading: (() -> Unit)? = null,
    private val onSuccess: ((T) -> Unit)
) : Observer<UiResource<T>> {

    override fun onChanged(resource: UiResource<T>) {
        when (resource) {
            is UiResource.Success -> {
                resource.data?.let(onSuccess)
            }
            is UiResource.Error -> {
                onError?.let { showError ->
                    showError(resource.errorMsg!!)
                }
            }
            is UiResource.Loading -> {
                onLoading?.let { showLoading ->
                    showLoading()
                }
            }
        }
    }
}