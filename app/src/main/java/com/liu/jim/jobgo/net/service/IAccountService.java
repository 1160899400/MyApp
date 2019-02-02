package com.liu.jim.jobgo.net.service;

import com.liu.jim.jobgo.entity.response.result.ModifyInfoResult;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * @author Hongzhi.Liu
 * @date 2019/1/26
 */
public interface IAccountService {


    /**
     * 修改用户个人信息
     *
     * @param send
     * @return
     */
    @POST("accountInfo/update.do")
    Observable<ModifyInfoResult> updateInfo(@Header("token") String token, @Body RequestBody send);
}
