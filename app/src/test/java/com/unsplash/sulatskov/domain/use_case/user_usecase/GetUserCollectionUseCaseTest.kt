package com.unsplash.sulatskov.domain.use_case.user_usecase

import com.unsplash.sulatskov.domain.model.Collection
import com.unsplash.sulatskov.domain.model.CoverPhoto
import com.unsplash.sulatskov.domain.model.User
import com.unsplash.sulatskov.domain.model.dto.ProfileImage
import com.unsplash.sulatskov.domain.model.dto.Urls
import com.unsplash.sulatskov.domain.repository.UserRepository
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
class GetUserCollectionUseCaseTest {

    @RelaxedMockK
    private lateinit var userRepository: UserRepository
    private lateinit var useCase: GetUserCollectionUseCase
    private val param = UserPhotoParam("userName", 1)

    @Before
    fun beforeTest() {
        MockKAnnotations.init(this)
        useCase = GetUserCollectionUseCase(userRepository)
    }

    @Test
    fun `success test GetUserCollectionUseCaseTest`() = runTest {
        coEvery {
            userRepository.getUserCollection(param.userName, param.page)
        } coAnswers {
            listOf(
                Collection(
                    coverPhoto = CoverPhoto(
                        user = User(profileImage = ProfileImage()),
                        url = Urls()
                    ),
                    user = User(profileImage = ProfileImage()),
                    private = false
                )
            )
        }

        val testResult = useCase.invoke(param).isSuccess

        withClue("testResult should be true") {
            testResult shouldBe true
        }
    }

    @Test
    fun `negative test GetUserCollectionUseCaseTest`() = runTest {
        coEvery {
            userRepository.getUserCollection(param.userName, param.page)
        } coAnswers {
            throw Throwable()
        }

        val testResult = useCase.invoke(param).isFailure

        withClue("testResult should be true") {
            testResult shouldBe true
        }
    }
}