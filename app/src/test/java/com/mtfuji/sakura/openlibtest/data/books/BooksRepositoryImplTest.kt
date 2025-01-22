package com.mtfuji.sakura.openlibtest.data.books

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
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class BooksRepositoryImplTest {
    private val remoteDataSource = DummyRemoteDataSource()

    private lateinit var localDataSource: BooksLocalDataSourceImpl
    private lateinit var repository: BooksRepositoryImpl

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

    @BeforeEach
    fun setUp() {
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setNewThreadSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setSingleSchedulerHandler { Schedulers.trampoline() }

        localDataSource = BooksLocalDataSourceImpl()
        repository = BooksRepositoryImpl(localDataSource, remoteDataSource)
    }

    @Test
    fun `when current reading is successfully requested then an api model is returned`() {
        remoteDataSource.setCurrentlyReading(demoApi)

        val testObserver = repository.getCurrentlyReading().test()

        testObserver.awaitCount(1)

        testObserver.assertComplete()
            .assertNoErrors()
            .assertValueAt(0) {
                it == demoApi
            }

        val testObserver2 = localDataSource.getCurrentlyReading().test()
        testObserver2.awaitCount(1)
        testObserver2.assertComplete()
            .assertNoErrors()
            .assertValueAt(0) {
                it == demoApi
            }
    }

    @Test
    fun `when current reading is requested with an error then said error is returned`() {
        val errorMessage = "Something went terribly Wrong!"
        remoteDataSource.setApiBookResponseError(errorMessage)

        val testObserver = repository.getCurrentlyReading().test()

        testObserver.awaitCount(1)

        testObserver
            .assertError{ it.message == errorMessage }
            .assertNotComplete()
    }

    @Test
    fun `when want to read is successfully requested then an api model is returned`() {
        remoteDataSource.setWantToRead(demoApi)

        val testObserver = repository.getWantToRead().test()

        testObserver.awaitCount(1)

        testObserver.assertComplete()
            .assertNoErrors()
            .assertValueAt(0) {
                it == demoApi
            }

        val testObserver2 = localDataSource.getWantToRead().test()
        testObserver2.awaitCount(1)
        testObserver2.assertComplete()
            .assertNoErrors()
            .assertValueAt(0) {
                it == demoApi
            }
    }

    @Test
    fun `when want to read is requested with an error then said error is returned`() {
        val errorMessage = "Something went terribly Wrong!"
        remoteDataSource.setApiBookResponseError(errorMessage)

        val testObserver = repository.getWantToRead().test()

        testObserver.awaitCount(1)

        testObserver
            .assertError{ it.message == errorMessage }
            .assertNotComplete()
    }

    @Test
    fun `when requesting book details successfully requested then an api model is returned`() {
        remoteDataSource.setBookDetails(bookDetailsApi)
        localDataSource.saveCurrentlyReading(demoApi)

        val testObserver = repository.getBookDetails(KEY1).test()
        testObserver.awaitCount(1)
        testObserver.assertComplete()
            .assertNoErrors()
            .assertValueAt(0) {
                it == bookDetailsApi
            }
    }

    @Test
    fun `when requesting book details with an error then said error is returned`() {
        val errorMessage = "Something went terribly Wrong!"
        remoteDataSource.setApiBookDetailsError(errorMessage)
        localDataSource.saveCurrentlyReading(demoApi)

        val testObserver = repository.getBookDetails(KEY1).test()

        testObserver.awaitCount(1)

        testObserver
            .assertError{ it.message == errorMessage }
            .assertNotComplete()
    }
}