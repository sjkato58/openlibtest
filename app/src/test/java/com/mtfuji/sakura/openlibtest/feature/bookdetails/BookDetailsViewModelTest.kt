package com.mtfuji.sakura.openlibtest.feature.bookdetails

import androidx.lifecycle.SavedStateHandle
import com.mtfuji.sakura.openlibtest.data.books.BooksRepositoryImpl
import com.mtfuji.sakura.openlibtest.data.books.sources.local.BooksLocalDataSourceImpl
import com.mtfuji.sakura.openlibtest.data.books.sources.remote.DummyRemoteDataSource
import com.mtfuji.sakura.openlibtest.data.models.ApiBookDetailsModel
import com.mtfuji.sakura.openlibtest.data.models.ApiBookEntryModel
import com.mtfuji.sakura.openlibtest.data.models.ApiBookModel
import com.mtfuji.sakura.openlibtest.data.models.ApiBookResponse
import com.mtfuji.sakura.openlibtest.data.models.Author
import com.mtfuji.sakura.openlibtest.data.models.AuthorDetails
import com.mtfuji.sakura.openlibtest.data.models.AuthorType
import com.mtfuji.sakura.openlibtest.data.models.BookType
import com.mtfuji.sakura.openlibtest.data.models.Created
import com.mtfuji.sakura.openlibtest.data.models.FirstSentence
import com.mtfuji.sakura.openlibtest.data.models.LastModified
import com.mtfuji.sakura.openlibtest.domain.models.BookDetailsModel
import com.mtfuji.sakura.openlibtest.domain.usecase.GetBookDetailsUseCaseImpl
import com.mtfuji.sakura.openlibtest.network.COVER_KEY
import com.mtfuji.sakura.openlibtest.network.COVER_KEY_VALUE
import com.mtfuji.sakura.openlibtest.network.COVER_MEDIUM_URL
import com.mtfuji.sakura.openlibtest.ui.UiState
import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins
import io.reactivex.rxjava3.observers.TestObserver
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class BookDetailsViewModelTest {
    private val remoteDataSource = DummyRemoteDataSource()
    private lateinit var localDataSource: BooksLocalDataSourceImpl
    private lateinit var booksRepository: BooksRepositoryImpl
    private lateinit var useCase: GetBookDetailsUseCaseImpl

    private lateinit var viewModel: BookDetailsViewModel

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
        const val COVERID3 = 34512
        const val PUBLISHDATE1 = "January First"
        const val DESCRIPTION1 = "This is a description"
        const val SUBJECT1 = "Biology"
        const val SUBJECT2 = "Sociology"
        const val SUBJECT3 = "Mathematics"
        const val FIRSTSENTENCE1 = "This is a teaser!"
        const val LATESTREVISION1 = 55
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
    val bookDetailsApi = ApiBookDetailsModel(
        key = KEY1,
        title = TITLE1,
        firstPublishDate = PUBLISHDATE1,
        authors = listOf(
            Author(
                AuthorDetails(AUTHOR1),
                AuthorType("")
            ),
            Author(
                AuthorDetails(AUTHOR2),
                AuthorType("")
            )
        ),
        type = BookType("text"),
        description = DESCRIPTION1,
        covers = listOf(COVERID1, COVERID2, COVERID3),
        lcClassifications = listOf(),
        deweyNumber = listOf(),
        subjects = listOf(SUBJECT1, SUBJECT2, SUBJECT3),
        firstSentence = FirstSentence("", FIRSTSENTENCE1),
        subjectTimes = listOf(),
        latestRevision = LATESTREVISION1,
        revision = 11,
        created = Created("", ""),
        lastModified = LastModified("", "")
    )

    val bookDetails = BookDetailsModel(
        key = KEY1,
        title = TITLE1,
        firstPublishedData = PUBLISHDATE1,
        authors = listOf(AUTHOR1, AUTHOR2),
        description = DESCRIPTION1,
        covers = listOf(
            COVER_MEDIUM_URL.replace(COVER_KEY, "id")
                .replace(COVER_KEY_VALUE, COVERID1.toString()),
            COVER_MEDIUM_URL.replace(COVER_KEY, "id")
                .replace(COVER_KEY_VALUE, COVERID2.toString()),
            COVER_MEDIUM_URL.replace(COVER_KEY, "id")
                .replace(COVER_KEY_VALUE, COVERID3.toString())
        ),
        subjects = listOf(SUBJECT1, SUBJECT2, SUBJECT3),
        firstSentence = FIRSTSENTENCE1,
        latestRevision = LATESTREVISION1
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
        useCase = GetBookDetailsUseCaseImpl(booksRepository)
    }

    @Test
    fun `when requesting book details then a success state should be emitted`() {
        remoteDataSource.setCurrentlyReading(demoApi)
        remoteDataSource.setBookDetails(bookDetailsApi)

        val testObserver = TestObserver<UiState<BookDetailsModel>>()
        viewModel = BookDetailsViewModel(SavedStateHandle(), useCase)

        viewModel.uiState
            .subscribeOn(Schedulers.trampoline())
            .observeOn(Schedulers.trampoline())
            .subscribe(testObserver)

        viewModel.saveBookKey(KEY1)

        testObserver.awaitCount(2)
        println("result: $testObserver")

        testObserver.assertValueAt(0) { it is UiState.Loading }
        testObserver.assertValueAt(1) { it is UiState.Success }
        testObserver.assertValueAt(1) { it is UiState.Success && it.data == bookDetails }
    }

    @Test
    fun `when requesting book details with the book list already in cache then a success state should be emitted`() {
        localDataSource.saveCurrentlyReading(demoApi)
        remoteDataSource.setBookDetails(bookDetailsApi)

        val testObserver = TestObserver<UiState<BookDetailsModel>>()
        viewModel = BookDetailsViewModel(SavedStateHandle(), useCase)

        viewModel.uiState
            .subscribeOn(Schedulers.trampoline())
            .observeOn(Schedulers.trampoline())
            .subscribe(testObserver)

        viewModel.saveBookKey(KEY1)

        testObserver.awaitCount(2)
        println("result: $testObserver")

        testObserver.assertValueAt(0) { it is UiState.Loading }
        testObserver.assertValueAt(1) { it is UiState.Success && it.data == bookDetails }
    }

    @Test
    fun `when requesting book details and an error occurs then an error state should be emitted`() {
        val demoError = "Something went terribly wrong!"

        localDataSource.saveCurrentlyReading(demoApi)
        remoteDataSource.setApiBookDetailsError(demoError)

        val testObserver = TestObserver<UiState<BookDetailsModel>>()
        viewModel = BookDetailsViewModel(SavedStateHandle(), useCase)

        viewModel.uiState
            .subscribeOn(Schedulers.trampoline())
            .observeOn(Schedulers.trampoline())
            .subscribe(testObserver)

        viewModel.saveBookKey(KEY1)

        testObserver.awaitCount(2)
        println("result: $testObserver")

        testObserver.assertValueAt(0) { it is UiState.Loading }
        testObserver.assertValueAt(1) { it is UiState.Error && it.message == demoError }
    }
}