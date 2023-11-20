package com.example.myapplication.domain.use_case.photo_usecase

import com.example.myapplication.domain.model.Photo
import com.example.myapplication.domain.repository.PhotoRepository
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
class GetDataBasePhotoUseCaseTest {
    @RelaxedMockK
    private lateinit var photoRepository: PhotoRepository
    private lateinit var useCase: GetDataBasePhotoUseCase

    @Before
    fun beforeTest() {
        MockKAnnotations.init(this)
        useCase = GetDataBasePhotoUseCase(photoRepository)
    }

    @Test
    fun `success test GetDataBasePhotoUseCaseTest`() = runTest {
        coEvery {
            photoRepository.getDataBaseListPhoto(1)
        } coAnswers {
            listOf(Photo(id = "", userName = "", profileImage = "", urls = "", createdTime = 0))
        }

        val testResult = useCase(1).isSuccess

        withClue("testResult should be true") {
            testResult shouldBe true
        }
    }

    @Test
    fun `negative test GetDataBasePhotoUseCaseTest`() = runTest {
        coEvery {
            photoRepository.getDataBaseListPhoto(1)
        } coAnswers {
            throw Throwable()
        }

        val testResult = useCase(1).isFailure

        withClue("testResult should be true") {
            testResult shouldBe true
        }
    }
}