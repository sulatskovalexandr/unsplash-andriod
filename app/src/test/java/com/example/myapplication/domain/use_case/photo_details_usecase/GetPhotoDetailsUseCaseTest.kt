package com.example.myapplication.domain.use_case.photo_details_usecase

import com.example.myapplication.domain.model.*
import com.example.myapplication.domain.model.dto.ProfileImage
import com.example.myapplication.domain.model.dto.Urls
import com.example.myapplication.domain.model.dto.UserDto
import com.example.myapplication.domain.repository.PhotoDetailsRepository
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
class GetPhotoDetailsUseCaseTest {

    @RelaxedMockK
    private lateinit var photoDetailsRepository: PhotoDetailsRepository
    private lateinit var useCase: GetPhotoDetailsUseCase
    private val photoId = "photoId"

    @Before
    fun beforeTest() {
        MockKAnnotations.init(this)
        useCase = GetPhotoDetailsUseCase(photoDetailsRepository)
    }

    @Test
    fun `success test GetPhotoDetailsUseCaseTest`() = runTest {
        coEvery {
            photoDetailsRepository.getPhotoDetail(photoId)
        } coAnswers {
            PhotoDetails(
                exif = Exif(),
                location = Location(),
                tags = listOf(Tag()),
                user = UserDto(profileImage = ProfileImage()),
                urls = Urls()
            )
        }

        val testResult = useCase.invoke(photoId).isSuccess

        withClue("testResult should be true") {
            testResult shouldBe true
        }
    }

    @Test
    fun `negative test GetPhotoDetailsUseCaseTest`() = runTest {
        coEvery {
            photoDetailsRepository.getPhotoDetail(photoId)
        } coAnswers {
            throw Throwable()
        }

        val testResult = useCase.invoke(photoId).isFailure

        withClue("testResult should be true") {
            testResult shouldBe true
        }
    }
}