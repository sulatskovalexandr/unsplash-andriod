package com.unsplash.sulatskov.domain.use_case.login_usecase

import com.unsplash.sulatskov.domain.model.Me
import com.unsplash.sulatskov.domain.model.dto.ProfileImage
import com.unsplash.sulatskov.domain.repository.LoginRepository
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
class GetMeUseCaseTest {

    @RelaxedMockK
    private lateinit var loginRepository: LoginRepository
    private lateinit var useCase: GetMeUseCase

    @Before
    fun beforeTest(){
        MockKAnnotations.init(this)
        useCase = GetMeUseCase(loginRepository)
    }

    @Test
    fun `success test GetMeUseCaseTest`() = runTest {
        coEvery {
           loginRepository.getMe()
        }coAnswers {
            Me(profileImage = ProfileImage())
        }

        val testResult = useCase().isSuccess

        withClue("testResult should be true") {
            testResult shouldBe true
        }
    }

    @Test
    fun `negative test GetMeUseCaseTest`() = runTest {
        coEvery {
            loginRepository.getMe()
        }coAnswers {
           throw Throwable()
        }
        val testResult = useCase().isFailure

        withClue("testResult should be true") {
            testResult shouldBe true
        }
    }
}