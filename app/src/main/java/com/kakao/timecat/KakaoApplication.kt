package com.kakao.timecat
import android.app.Application;
import com.kakao.sdk.common.KakaoSdk

class KakaoApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // 다른 초기화 코드들

        // Kakao SDK 초기화
        KakaoSdk.init(this, "5420c61fa3a9ddd0acb3dc6309c5d0d9");
    }
}