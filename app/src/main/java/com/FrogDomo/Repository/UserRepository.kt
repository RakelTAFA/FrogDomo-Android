package com.FrogDomo.Repository

import com.FrogDomo.Model.LoginUser
import com.FrogDomo.Model.User
import com.FrogDomo.DataBase.DBDataSource
import com.FrogDomo.DataSource.CacheDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

object UserRepository {
    suspend fun createUser(createuser: User) : Flow<User> = flow {
        DBDataSource.insertUser(createuser);
        val user = DBDataSource.getUser(createuser.email)
        emit(user)
    }
    suspend fun loginUser(loginUser: LoginUser) : Flow<User> = flow {
        val user = DBDataSource.getUser(loginUser.userEmail)

        if(user!=null && loginUser.password==user.password){
            emit(user)
        }
        else{
            emit(User(username = "", email = ""))
        }
    }

    suspend fun updateUser(user: User) : Flow<User> = flow {
        DBDataSource.updateUser(user)
        val updated_user =DBDataSource.getUser(user.email)
        emit(updated_user)
    }

    fun getUserLogin():User{
        return CacheDataSource.getInstance().getUserLogin();
    }
    fun setUserLogin(user:User){
        CacheDataSource.getInstance().setUserLogin(user)
    }
}