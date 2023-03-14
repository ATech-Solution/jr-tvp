package com.atech.data.repositoriesimpl

import com.atech.data.datasource.DataSource
import com.atech.data.datasource.StubDataSource
import com.atech.domain.entities.LoginResponseModel
import com.atech.domain.entities.MessageModel
import com.atech.domain.repositories.JrRepository
import io.reactivex.Flowable
import javax.inject.Inject

/**
 * Created by Abraham Lay on 2020-06-09.
 */
@Suppress("unused")
class RepositoryImpl @Inject constructor(
    private val source: DataSource,
    private val stubDataSource: StubDataSource
): JrRepository {
  
    var isUsingDummyData = false
    override fun login(username: String, password: String): Flowable<LoginResponseModel> {
        return source.login(username, password).map { it.toModel() }
    }

    override fun logout(): Flowable<MessageModel> {
        return source.logout().map { it.toModel() }
    }

}