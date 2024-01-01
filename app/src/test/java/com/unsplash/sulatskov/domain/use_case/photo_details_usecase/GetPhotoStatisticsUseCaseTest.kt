package com.unsplash.sulatskov.domain.use_case.photo_details_usecase

import com.unsplash.sulatskov.domain.model.*
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
class GetPhotoStatisticsUseCaseTest {

    @RelaxedMockK
    private lateinit var photoDetailsRepository: PhotoDetailsRepository
    private lateinit var useCase: GetPhotoStatisticsUseCase
    private val photoId = "photoId"

    @Before
    fun beforeTest() {
        MockKAnnotations.init(this)
        useCase = GetPhotoStatisticsUseCase(photoDetailsRepository)
    }

    @Test
    fun `success test GetPhotoStatisticsUseCaseTest`() = runTest {
        coEvery {
            photoDetailsRepository.getPhotoStatistics(photoId)
        } coAnswers {
            PhotoStatistics(downloads = Downloads(), views = Views(), likes = Likes())
        }

        val testResult = useCase.invoke(photoId).isSuccess

        withClue("testResult should be true") {
            testResult shouldBe true
        }
    }

    @Test
    fun `negative test GetPhotoStatisticsUseCaseTest`() = runTest {
        coEvery {
            photoDetailsRepository.getPhotoStatistics(photoId)
        } coAnswers {
           throw Throwable()
        }

        val testResult = useCase.invoke(photoId).isFailure

        withClue("testResult should be true") {
            testResult shouldBe true
        }
    }
}