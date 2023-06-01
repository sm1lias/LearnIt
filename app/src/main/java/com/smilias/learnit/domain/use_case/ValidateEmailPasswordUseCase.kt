package com.smilias.learnit.domain.use_case

import com.smilias.learnit.R
import com.smilias.learnit.utils.UiText

class ValidateEmailPasswordUseCase {

    operator fun invoke(
        email: String,
        password: String
    ): Result {
        if (email.isBlank() || password.isBlank()){
            return Result.Error(
                message = UiText.StringResource(R.string.cant_be_null)
            )
        }
        return Result.Success
    }

    sealed class Result {
        object Success : Result()
        data class Error(val message: UiText): Result()
    }
}