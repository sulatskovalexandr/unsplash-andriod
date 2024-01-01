package com.unsplash.sulatskov.domain.use_case.photo_details_usecase

import com.unsplash.sulatskov.domain.model.DownloadPhotoUrl
import com.unsplash.sulatskov.domain.repository.PhotoDetailsRepository
import io.kotest.assertions.withClue
import io.kotest.matchers.shouldBe
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class PhotoDownloadUrlTest {
    @RelaxedMockK
    private lateinit var photoDetailsRepository: PhotoDetailsRepository
    private lateinit var useCase: PhotoDownloadUrl
    private val photoId = "photoId"
    private val url = "url"

    @Before
    fun beforeTest() {
        MockKAnnotations.init(this)
        useCase = PhotoDownloadUrl(photoDetailsRepository)
    }

    @Test
    fun `success test PhotoDownloadUrlTest`() = runTest {
        coEvery {
            photoDetailsRepository.getDownloadPhotoUrl(photoId)
        } coAnswers {
            DownloadPhotoUrl(url)
        }

        val testResult = useCase(photoId).isSuccess

        withClue("testResult should be true") {
            testResult shouldBe true
        }
    }

    @Test
    fun `negative test PhotoDownloadUrlTest`() = runTest {
        coEvery {
            photoDetailsRepository.getDownloadPhotoUrl(photoId)
        } coAnswers {
            throw Throwable()
        }

        val testResult = useCase(photoId).isFailure

        withClue("testResult should be true") {
            testResult shouldBe true
        }
    }
}