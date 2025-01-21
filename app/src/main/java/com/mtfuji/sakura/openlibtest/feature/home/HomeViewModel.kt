package com.mtfuji.sakura.openlibtest.feature.home

import androidx.lifecycle.ViewModel
import com.mtfuji.sakura.openlibtest.domain.models.BookModel
import com.mtfuji.sakura.openlibtest.domain.usecase.GetCurrentlyReadingBooksUseCase
import com.mtfuji.sakura.openlibtest.ui.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCurrentlyReadingBooksUseCase: GetCurrentlyReadingBooksUseCase
) : ViewModel() {
    private val compositeDispose = CompositeDisposable()

    private val _uiState = BehaviorSubject.create<UiState<List<BookModel>>>()
    val uiState: Observable<UiState<List<BookModel>>> = _uiState.hide()

    init {
        fetchCurrentlyReadingBooks()
    }

    fun fetchCurrentlyReadingBooks() {
        _uiState.onNext(UiState.Loading)

        getCurrentlyReadingBooksUseCase.execute()
            .subscribeOn(Schedulers.io())
            //.observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {

                },
                {

                }
            )
            .also { compositeDispose.add(it) }
    }

    override fun onCleared() {
        compositeDispose.dispose()
        super.onCleared()
    }
}