package com.example.myapplication.domain.use_case.photo_usecase

import androidx.test.ext.junit.runners.AndroidJUnit4
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

@RunWith(AndroidJUnit4::class)
class GetPhotoUseCaseTest {
    @RelaxedMockK
    private lateinit var photoRepository: PhotoRepository
    private lateinit var useCase: GetPhotoUseCase
    private var param = ListPhotoParam(1, GetPhotoUseCase.Companion.Order.LATEST)

//    val photoRepository = mock<PhotoRepository>()

    @Before
    fun beforeTest() {
        MockKAnnotations.init(this)
        useCase = GetPhotoUseCase(photoRepository)
    }

//    @Test
//    fun `success test GetPhotoUseCaseTest`(param: ListPhotoParam) = runTest {
//        coEvery {
//            photoRepository.getListPhoto(1, "")
//        } coAnswers {
//            listOf(Photo(id = "", userName = "", profileImage = "", urls = "", createdTime = 0))
//        }
//
//        val testResult = useCase(param).isSuccess
//
//        withClue("testResult should be true") {
//            testResult shouldBe true
//        }
//    }
    @Test
    fun `negative test GetPhotosUseCaseTest`() = runTest {
        coEvery {
            photoRepository.getListPhoto(1,"")
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
