package com.kissheart.community.community.Provider;

import com.alibaba.fastjson.JSON;
import com.kissheart.community.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;
import com.kissheart.community.community.dto.accessTokenDto;
import com.kissheart.community.community.dto.GithubUser;
import java.io.IOException;

@Component
public class githubProvider {
    public String getAccessToken(accessTokenDto accessTokenDto){
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();


        RequestBody body = RequestBody.create(JSON.toJSONString(accessTokenDto),mediaType);
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()){
            String string = response.body().string();
            String token = string.split("&")[0].split("=")[1];

            return token;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
    public GithubUser getUser(String accessToken){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+accessToken)
                .header("Authorization","token "+accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);

            return githubUser;
        } catch (IOException e) {
        }
        return null;
    }
}
