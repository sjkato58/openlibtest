package com.mtfuji.sakura.openlibtest.feature.home

import com.mtfuji.sakura.openlibtest.data.books.BooksRepositoryImpl
import com.mtfuji.sakura.openlibtest.data.books.sources.local.BooksLocalDataSourceImpl
import com.mtfuji.sakura.openlibtest.data.books.sources.remote.DummyRemoteDataSource
import com.mtfuji.sakura.openlibtest.data.models.ApiBookEntryModel
import com.mtfuji.sakura.openlibtest.data.models.ApiBookModel
import com.mtfuji.sakura.openlibtest.data.models.ApiBookResponse
import com.mtfuji.sakura.openlibtest.domain.models.BookModel
import com.mtfuji.sakura.openlibtest.domain.usecase.GetCurrentlyReadingBooksUseCaseImpl
import com.mtfuji.sakura.openlibtest.network.COVER_KEY
import com.mtfuji.sakura.openlibtest.network.COVER_KEY_VALUE
import com.mtfuji.sakura.openlibtest.network.COVER_SMALL_URL
import com.mtfuji.sakura.openlibtest.ui.UiState
import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins
import io.reactivex.rxjava3.observers.TestObserver
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class HomeViewModelTest {

    private val remoteDataSource = DummyRemoteDataSource()

    private lateinit var localDataSource: BooksLocalDataSourceImpl
    private lateinit var booksRepository: BooksRepositoryImpl
    private lateinit var getCurrentlyReadingBooksUseCase: GetCurrentlyReadingBooksUseCaseImpl

    private lateinit var viewModel: HomeViewModel

    companion object {
        const val TITLE1 = "Title 1"
        const val TITLE2 = "Title 2"
        const val KEY1 = "Key1"
        const val KEY2 = "Key2"
        const val AUTHOR1 = "AuthorOne"
        const val AUTHOR2 = "AuthorTwo"
        const val AUTHOR3 = "AuthorThree"
        const val COVERID1 = 12345
        const val COVERID2 = 54321
    }

    val demoApi = ApiBookResponse(
        page = 1,
        numFound = 2,
        bookEntries = listOf(
            ApiBookEntryModel(
                bookModel = ApiBookModel(
                    title = TITLE1,
                    key = KEY1,
                    authorKeys = listOf(AUTHOR1, AUTHOR2),
                    authorNames = listOf(AUTHOR1, AUTHOR2),
                    firstPublishYear = 123,
                    lendingEditions = "Some Editions",
                    editionKey = listOf("akey", "bkey"),
                    coverId = COVERID1,
                    coverEditionKey = "12345"
                ),
                loggedEdition = null,
                loggedDate = "numbers"
            ),
            ApiBookEntryModel(
                bookModel = ApiBookModel(
                    title = TITLE2,
                    key = KEY2,
                    authorKeys = listOf(AUTHOR2, AUTHOR3),
                    authorNames = listOf(AUTHOR2, AUTHOR3),
                    firstPublishYear = 123,
                    lendingEditions = "Some Editions",
                    editionKey = listOf("akey", "bkey"),
                    coverId = COVERID2,
                    coverEditionKey = "12345"
                ),
                loggedEdition = null,
                loggedDate = "numbers"
            ),
        )
    )

    val demoBooks = listOf(
        BookModel(
            title = TITLE1,
            key = KEY1,
            authorNames = listOf(AUTHOR1, AUTHOR2),
            coverUrl = COVER_SMALL_URL.replace(COVER_KEY, "id")
                .replace(COVER_KEY_VALUE, COVERID1.toString())
        ),
        BookModel(
            title = TITLE2,
            key = KEY2,
            authorNames = listOf(AUTHOR2, AUTHOR3),
            coverUrl = COVER_SMALL_URL.replace(COVER_KEY, "id")
                .replace(COVER_KEY_VALUE, COVERID2.toString())
        ),
    )

    @BeforeEach
    fun setUp() {
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setNewThreadSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setSingleSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }

        remoteDataSource.refresh()

        localDataSource = BooksLocalDataSourceImpl()
        booksRepository = BooksRepositoryImpl(localDataSource, remoteDataSource)
        getCurrentlyReadingBooksUseCase = GetCurrentlyReadingBooksUseCaseImpl(booksRepository)
    }

    @Test
    fun `when fetchCurrentlyReadingBooks is called then a success state should be emitted`() {
        remoteDataSource.setCurrentlyReading(demoApi)

        val testObserver = TestObserver<UiState<List<BookModel>>>()
        viewModel = HomeViewModel(getCurrentlyReadingBooksUseCase)

        viewModel.uiState
            .subscribeOn(Schedulers.trampoline())
            .observeOn(Schedulers.trampoline())
            .subscribe(testObserver)

        testObserver.awaitCount(1)
        println("result: $testObserver")

        testObserver.assertValueAt(0) { it is UiState.Success && it.data.size == 2 }
        testObserver.assertValueAt(0) { it is UiState.Success && it.data[0] == demoBooks[0] }
        testObserver.assertValueAt(0) { it is UiState.Success && it.data[1] == demoBooks[1] }
    }

    @Test
    fun `when fetchCurrentlyReadingBooks is called with an error then an error state should be emitted`() {
        val demoError = "Something went terribly wrong!"
        remoteDataSource.setApiBookResponseError(demoError)

        val testObserver = TestObserver<UiState<List<BookModel>>>()
        viewModel = HomeViewModel(getCurrentlyReadingBooksUseCase)

        viewModel.uiState
            .subscribeOn(Schedulers.trampoline())
            .observeOn(Schedulers.trampoline())
            .subscribe(testObserver)

        testObserver.awaitCount(1)
        println("result: $testObserver")

        testObserver.assertValueAt(0) { it is UiState.Error }
        testObserver.assertValueAt(0) { it is UiState.Error && it.message == demoError }
    }

    @Test
    fun `when fetchCurrentlyReadingBooks is called after error which is successful then a loading and success state should be emitted`() {
        val demoError = "Something went terribly wrong!"
        remoteDataSource.setApiBookResponseError(demoError)

        val testObserver = TestObserver<UiState<List<BookModel>>>()
        viewModel = HomeViewModel(getCurrentlyReadingBooksUseCase)

        viewModel.uiState
            .subscribeOn(Schedulers.trampoline())
            .observeOn(Schedulers.trampoline())
            .subscribe(testObserver)

        testObserver.awaitCount(1)

        remoteDataSource.setApiBookResponseError("")
        remoteDataSource.setCurrentlyReading(demoApi)

        viewModel.onErrorRetry()

        testObserver.awaitCount(3)
        println("result: $testObserver")

        testObserver.assertValueAt(0) { it is UiState.Error }
        testObserver.assertValueAt(0) { it is UiState.Error && it.message == demoError }
        testObserver.assertValueAt(1) { it is UiState.Loading }
        testObserver.assertValueAt(2) { it is UiState.Success && it.data.size == 2 }
        testObserver.assertValueAt(2) { it is UiState.Success && it.data[0] == demoBooks[0] }
        testObserver.assertValueAt(2) { it is UiState.Success && it.data[1] == demoBooks[1] }
    }
}