package com.mtfuji.sakura.openlibtest.feature.bookdetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.mtfuji.sakura.openlibtest.domain.models.BookDetailsModel
import com.mtfuji.sakura.openlibtest.domain.usecase.GetBookDetailsUseCaseImpl
import com.mtfuji.sakura.openlibtest.network.BOOK_KEY
import com.mtfuji.sakura.openlibtest.ui.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import javax.inject.Inject

@HiltViewModel
class BookDetailsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val useCaseImpl: GetBookDetailsUseCaseImpl
): ViewModel() {

    private val compositeDispose = CompositeDisposable()

    private val _uiState = BehaviorSubject.create<UiState<BookDetailsModel>>()
    val uiState: Observable<UiState<BookDetailsModel>> = _uiState.hide()

    fun saveBookKey(key: String) {
        savedStateHandle[BOOK_KEY] = key
        fetchBookData()
    }

    private fun fetchBookData() {
        _uiState.onNext(UiState.Loading)

        val key = savedStateHandle[BOOK_KEY] ?: ""
        useCaseImpl.execute(key)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { bookDetailsModel ->
                    _uiState.onNext(UiState.Success(bookDetailsModel))
                },
                { throwable ->
                    _uiState.onNext(UiState.Error(throwable.message ?: ""))
                }
            )
            .also { compositeDispose.add(it) }
    }

    fun onErrorRetry() {
        fetchBookData()
    }

    override fun onCleared() {
        compositeDispose.dispose()
        super.onCleared()
    }
}