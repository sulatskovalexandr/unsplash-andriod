package com.unsplash.sulatskov.domain.use_case.photo_usecase

import com.unsplash.sulatskov.domain.model.Photo
import com.unsplash.sulatskov.domain.repository.PhotoRepository
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
//@LargeTest
class GetPhotoUseCaseTest {
    @RelaxedMockK
    private lateinit var photoRepository: PhotoRepository
    private lateinit var useCase: GetPhotoUseCase
    private val param = ListPhotoParam(1, GetPhotoUseCase.Companion.OrderListPhoto.LATEST)

    @Before
    fun beforeTest() {
        MockKAnnotations.init(this)
        useCase = GetPhotoUseCase(photoRepository)
    }

    @Test
    fun `success test GetPhotoUseCaseTest`() = runTest {
        coEvery {
            photoRepository.getListPhoto(param.page, param.orderBy.value)
        } coAnswers {
            listOf(Photo(id = "", userName = "", profileImage = "", urls = "", createdTime = 0))
        }
        val testResult = useCase(param).isSuccess

        withClue("testResult should be true") {
            testResult shouldBe true
        }
    }

    @Test
    fun `negative test GetPhotosUseCaseTest`() = runTest {
        coEvery {
            photoRepository.getListPhoto(param.page, param.orderBy.value)
        } coAnswers {
            throw Throwable()
        }
        val testResult = useCase(param).isFailure

        withClue("testResult should be true") {
            testResult shouldBe true
        }
    }
}
//    @Test
//    suspend fun shouldReturnTheSameDataAsInPhotoRepository(photo: Photo) {
//        val testListPhoto: List<Photo> =
//            listOf(Photo(photo.id, photo.userName, photo.profileImage, photo.urls, 200))
//        Mockito.`when`(
//            photoRepository.getListPhoto(
//                0,
//                GetPhotoUseCase.Companion.Order.LATEST.toString()
//            )
//        ).thenReturn(testListPhoto)
//        val useCase = GetPhotoUseCase(photoRepository)
//        val actual =
//            useCase.invoke(param = ListPhotoParam(0, GetPhotoUseCase.Companion.Order.LATEST))
//        Assertions.assertEquals(testListPhoto, actual)
//    }
