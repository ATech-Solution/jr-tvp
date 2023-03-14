package com.atech.domain.repositories

import com.atech.domain.entities.*
import io.reactivex.Flowable

/**
 * Created by Abraham Lay on 2019-09-29.
 */

interface JrRepository {
    fun login(username: String, password: String): Flowable<LoginResponseModel>

    fun logout(): Flowable<MessageModel>
}