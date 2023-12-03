package com.example.myapplication.domain.use_case.collection_usecase

import com.example.myapplication.domain.model.Collection
import com.example.myapplication.domain.model.CoverPhoto
import com.example.myapplication.domain.model.User
import com.example.myapplication.domain.model.dto.ProfileImage
import com.example.myapplication.domain.model.dto.Urls
import com.example.myapplication.domain.repository.CollectionRepository
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
class GetCollectionUseCaseTest {
    @RelaxedMockK
    private lateinit var collectionRepository: CollectionRepository
    private lateinit var useCase: GetCollectionUseCase

    @Before
    fun beforeTest() {
        MockKAnnotations.init(this)
        useCase = GetCollectionUseCase(collectionRepository)
    }

    @Test
    fun `success test GetCollectionUseCaseTest`() = runTest {
        coEvery {
            collectionRepository.getListCollections(1)
        } coAnswers {
            listOf(
                Collection(
                    coverPhoto = CoverPhoto(
                        user = User(profileImage = ProfileImage()),
                        url = Urls()
                    ), private = false,
                    user = User(profileImage = ProfileImage())
                )
            )
        }
        val testResult = useCase.invoke(1).isSuccess

        withClue("testResult should be true") {
            testResult shouldBe true
        }
    }

    @Test
    fun `negative test GetCollectionUseCaseTest`() = runTest {
        coEvery {
            collectionRepository.getListCollections(1)
        } coAnswers {
         throw Throwable()
        }
        val testResult = useCase.invoke(1).isFailure

        withClue("testResult should be true") {
            testResult shouldBe true
        }
    }
}