package sju.sejong.capstonedesign.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import sju.sejong.capstonedesign.model.login.*
import sju.sejong.capstonedesign.retrofit.RetrofitInstance.service
import sju.sejong.capstonedesign.util.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Response
import sju.sejong.capstonedesign.model.BasicResponse
import javax.inject.Inject

private val Context.dataStore by preferencesDataStore(name = "dataStore")

class LoginRepository @Inject constructor(@ApplicationContext private val context: Context) {
    val ACCESS_TOKEN = Constants.ACCESS_TOKEN

    // 회원가입
    suspend fun signup(signupPost: SignupPost) : Response<SignupResponse> {
        return service.signup(signupPost)
    }

    // 로그인
    suspend fun login(loginPost: LoginPost) : Response<LoginResponse> {
        return service.login(loginPost)
    }

    // 회원정보 조회
    suspend fun getMemberInfo(memberId: Long) : Response<MemberInfoResponse> {
        return service.getMemberInfo("Bearer $ACCESS_TOKEN", memberId)
    }

    // 닉네임 변경
    suspend fun modifyNickname(modifyUserInfo: ModifyUserInfo, memberId: Long) : Response<BasicResponse> {
        return service.modifyNickname("Bearer $ACCESS_TOKEN", modifyUserInfo, memberId)
    }

    // dataStore에 토큰 쓰기
    suspend fun setLoginKey(accessToken: String, memberId: Long) {
        context.dataStore.edit {
            it[loginKey] = accessToken
            it[memberIdKey] = memberId
        }
    }

    // DataStore 엑세스토큰 읽기
    fun getAccessToken(): String {
        var token = ""
        val job = CoroutineScope(Dispatchers.IO).launch {
            token = loginStatus.first()
        }
        runBlocking {
            job.join()
        }
        return token
    }

    // DataStore MemberId 읽기
    fun getMemberId(): Long {
        var id: Long = 0
        val job = CoroutineScope(Dispatchers.IO).launch {
            id = memberStatus.first()
        }
        runBlocking {
            job.join()
        }
        return id
    }

    // DataStore
    private val loginKey = stringPreferencesKey("login_status")
    private val memberIdKey = longPreferencesKey("member_id")

    private val loginStatus : Flow<String> = context.dataStore.data
        .map {preferences ->
            preferences[loginKey] ?: ""
        }
    private val memberStatus : Flow<Long> = context.dataStore.data
        .map {preferences ->
            (preferences[memberIdKey] ?: 0) as Long
        }
}