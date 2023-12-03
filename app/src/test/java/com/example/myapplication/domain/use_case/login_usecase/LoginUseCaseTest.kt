package com.example.myapplication.domain.use_case.login_usecase

import com.example.myapplication.domain.model.AccessToken
import com.example.myapplication.domain.repository.LoginRepository
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
class LoginUseCaseTest {

    @RelaxedMockK
    private lateinit var loginRepository: LoginRepository
    private lateinit var useCase: LoginUseCase
    private val code = "code"

    @Before
    fun beforeTest() {
        MockKAnnotations.init(this)
        useCase = LoginUseCase(loginRepository)
    }

    @Test
    fun `success test LoginUseCaseTest`() = runTest {
        coEvery {
            loginRepository.getAccessToken(code)
        } coAnswers {
            AccessToken()
        }

        val testResult = useCase.invoke(code).isSuccess

        withClue("testResult should be true") {
            testResult shouldBe true
        }
    }

    @Test
    fun `negative test LoginUseCaseTest`() = runTest {
        coEvery {
            loginRepository.getAccessToken(code)
        } coAnswers {
            throw Throwable()
        }

        val testResult = useCase.invoke(code).isFailure

        withClue("testResult should be true") {
            testResult shouldBe true
        }
    }
}