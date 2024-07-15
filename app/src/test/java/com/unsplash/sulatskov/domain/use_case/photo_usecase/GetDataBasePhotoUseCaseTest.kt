package com.unsplash.sulatskov.domain.use_case.photo_usecase

import com.unsplash.sulatskov.domain.model.Photo
import com.unsplash.sulatskov.domain.repository.PhotoRepository
import com.unsplash.sulatskov.ui.photo_screen.OrderListPhoto
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
    private  var photoParam:ListPhotoParam  = ListPhotoParam(1,OrderListPhoto.LATEST)

    @Before
    fun beforeTest() {
        MockKAnnotations.init(this)
        useCase = GetDataBasePhotoUseCase(photoRepository)
    }

    @Test
    fun `success test GetDataBasePhotoUseCaseTest`() = runTest {
        coEvery {
            photoRepository.getDataBaseListPhoto(1, OrderListPhoto.LATEST.value)
        } coAnswers {
            listOf(Photo(id = "", userName = "", profileImage = "", urls = "", createdTime = 0))
        }
        val testResult = useCase(1, photoParam.orderBy.value).isSuccess

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