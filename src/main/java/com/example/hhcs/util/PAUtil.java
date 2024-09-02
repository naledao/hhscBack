package com.example.hhcs.util;

import com.example.hhcs.domain.Result;
import com.example.hhcs.domain.WxReturn;
import com.google.gson.Gson;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;

public class PAUtil {
    private static final String getMonthlyAnimeUrl="https://www.kisssub.org";
    private static final String getdoubanurl="https://search.douban.com/movie/subject_search";
    public static String getdouban(String name) throws IOException, URISyntaxException {
        HttpClient httpClient = new HttpClient();
        URIBuilder builder = new URIBuilder(getdoubanurl);
        builder.setParameter("search_text", name);
        URI uri = builder.build();
        String url=uri.toString();
        GetMethod getMethod=new GetMethod(url);
        try {
            int num = httpClient.executeMethod(getMethod);
            if(num==200){
                return getMethod.getResponseBodyAsString();

            }
            else {
                return "";
            }
        } catch (IOException e) {
            return "";
        }
    }
    public static String getMonthlyAnime(String url1) throws IOException {
        String url=getMonthlyAnimeUrl+url1;
        System.out.println(url);
        // 1. 生成Http对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 2. 获取httpGet对象
        HttpGet httpGet = new HttpGet(url);
        // 3. 执行httpGet
        CloseableHttpResponse response = httpClient.execute(httpGet);
        // 4. 获取返回的实体
        HttpEntity httpEntity = response.getEntity();
        // 5. 解析实体类
        String entityJson = EntityUtils.toString(httpEntity,"utf-8");
        // 6. 打印数据,观看结果
        // 7. 关闭连接对象
        response.close();
        httpClient.close();
        return entityJson;
    }
}
