package com.example.hhcs;

import com.example.hhcs.dao.*;
import com.example.hhcs.domain.*;
import com.example.hhcs.util.HeroPower;
import com.example.hhcs.util.MD5Util;
import com.example.hhcs.util.MailUtils;
import com.example.hhcs.util.WxOpenid;
import com.google.gson.Gson;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@SpringBootTest
class HhcsApplicationTests {
    @Autowired
    private AdminDao adminDao;
    @Autowired
    private CstoreDao cstoreDao;
    @Autowired
    private WareHouseDao wareHouseDao;
    @Autowired
    private WxOpenid wxOpenid;
    @Autowired
    private SstoreDao sstoreDao;
    @Autowired
    private ImageDao imageDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private PurchaseHistoryDao purchaseHistoryDao;
    @Autowired
    private BrowseHistoryDao browseHistoryDao;
    @Autowired
    private ZhanDao zhanDao;
    @Resource
    private TableMapper tableMapper;
    @Autowired
    private MsgDao msgDao;
    @Autowired
    private BstoreDao bstoreDao;
    @Test
    void contextLoads() {
        Message message=msgDao.getmsg("oS7eE5fc74g542qN4mPINo-SBPTQ1659712456644");
        if(message!=null)
        {
            System.out.println(message.getMsg());
        }
        assert message != null;
        System.out.println(message.getOpenid());
    }

    @Test
    void image(){
        String s="oS7eE5bkGfkuP15Utnd1T3vEhVds1693382978542*oS7eE5bkGfkuP15Utnd1T3vEhVds1693382772216*oS7eE5Ze9AmfjrTHXLkjmr186PF81686399713955*";
        String[] zan=s.split("[*]");
        for(String i:zan)
        {
            Cstore cstore=cstoreDao.getc(i);
            System.out.println(cstore.getName());
        }
    }

    @Test
    void user(){
        tableMapper.createTable("chcs");
    }

    @Test
    void redis(){
//        stringRedisTemplate.opsForValue().set("Sstore:flag","0");
//        stringRedisTemplate.opsForValue().set("Sstore:openid","0");
        Random random=new Random(System.currentTimeMillis());
        StringBuilder stringBuilder=new StringBuilder();
        for(int i=1;i<=12;i++)
        {
            if(i%2==0)
            {
                while (true)
                {
                    int res=random.nextInt(123);
                    if(res>=97)
                    {
                        stringBuilder.append((char) res);
                        break;
                    }
                }
            }
            else
            {
                stringBuilder.append(random.nextInt(10));
            }
        }
        String res=new String(stringBuilder);
        System.out.println(res);
        stringRedisTemplate.opsForValue().set("key:"+res,"");
    }

    @Test
    void purchasehis()
    {
        int a=purchaseHistoryDao.ups(6,"oS7eE5bkGfkuP15UtnVds");
        System.out.println(a);
    }
    @Test
    void returnid(){
        wxOpenid.getcode("gfty");
    }

    @Test
    void ddfc() throws UnsupportedEncodingException, URISyntaxException {

        HttpClient httpClient=new HttpClient();
        // 构建 URI
        URIBuilder builder = new URIBuilder("https://search.douban.com/movie/subject_search");
        builder.setParameter("search_text", "不要欺负我，长瀞同学 第二季");

        URI uri = builder.build();
        String url=uri.toString();
        System.out.println(url);
        GetMethod getMethod=new GetMethod(url);
        try {
            int num = httpClient.executeMethod(getMethod);
            System.out.println(num);
            if(num==200){
                System.out.println(getMethod.getResponseBodyAsString());
            }
            else{
                System.out.println("状态码不对");;
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void cnsdj() throws IOException {
        // 1. 生成Http对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 2. 获取httpGet对象
        HttpGet httpGet = new HttpGet("https://www.kisssub.org/addon.php?r=bangumi/table");
        // 3. 执行httpGet
        CloseableHttpResponse response = httpClient.execute(httpGet);
        // 4. 获取返回的实体
        HttpEntity httpEntity = response.getEntity();
        // 5. 解析实体类
        String entityJson = EntityUtils.toString(httpEntity,"utf-8");
        // 6. 打印数据,观看结果
        System.out.println("返回的数据是:" + entityJson);
        // 7. 关闭连接对象
        response.close();
        httpClient.close();
    }


    @Test
    void fcd() throws NoSuchAlgorithmException, IOException {
        String url="https://www.kisssub.org/addon.php?r=bangumi/table";
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
        System.out.println(entityJson);
    }

    @Test
    void ncs(){
        HttpClient httpClient = new HttpClient();
        String url="https://www.kisssub.org/addon.php?r=bangumi/table";
        GetMethod getMethod=new GetMethod(url);
        getMethod.addRequestHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36");
        try {
            int num = httpClient.executeMethod(getMethod);
            if(num==200){
                String c=getMethod.getResponseBodyAsString();
                System.out.println(c);
            }
        } catch (IOException e) {
        }
    }





}
