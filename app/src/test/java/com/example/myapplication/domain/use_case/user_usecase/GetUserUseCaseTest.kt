package com.example.myapplication.domain.use_case.user_usecase

import com.example.myapplication.domain.model.User
import com.example.myapplication.domain.model.dto.ProfileImage
import com.example.myapplication.domain.repository.UserRepository
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
class GetUserUseCaseTest {
    @RelaxedMockK
    private lateinit var userRepository: UserRepository
    private lateinit var useCase: GetUserUseCase

    @Before
    fun beforeTest() {
        MockKAnnotations.init(this)
        useCase = GetUserUseCase(userRepository)
    }

    @Test
    fun `success test GetUserUseCaseTest`() = runTest {
        coEvery {
            userRepository.getUser("Name")
        } coAnswers {
            User(profileImage = ProfileImage())
        }
        val testResult = useCase.invoke("Name").isSuccess

        withClue("testResult should be true") {
            testResult shouldBe true
        }
    }

    @Test
    fun `negative test GetUserUseCaseTest`() = runTest {
        coEvery {
            userRepository.getUser("Name")
        } coAnswers {
            throw Throwable()
        }
        val testResult = useCase.invoke("Name").isFailure

        withClue("testResult should be true") {
            testResult shouldBe true
        }
    }
}