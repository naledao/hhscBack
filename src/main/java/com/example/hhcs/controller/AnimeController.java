package com.example.hhcs.controller;


import com.example.hhcs.dao.AnimeDao;
import com.example.hhcs.domain.AnimeMsgDomain;
import com.example.hhcs.domain.MdDomain;
import com.example.hhcs.domain.RenewAnimeHistoryDomain;
import com.example.hhcs.util.MD5Util;
import com.example.hhcs.util.PAUtil;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@RestController
@RequestMapping("/api/anime")
@CrossOrigin
public class AnimeController {
    private static final String passwo="gcb3u7835628sgd267hyuw64856cs7";
    private static final String secret="chds68mndkjclwe27384h2fdu83ygt378fb7243";
    @Resource
    private AnimeDao animeDao;
    @GetMapping("/renewHistory")
    private List<RenewAnimeHistoryDomain> getAnime(@RequestParam("password") String password,@RequestParam("url") String url) throws URISyntaxException {
        if(password==null || !password.equals(passwo) || url==null){
            return new ArrayList<>();
        }
        return animeDao.getAllHistory(url);
    }

    @PostMapping("/renew")
    private Integer renewAnime(@RequestBody AnimeMsgDomain animeMsgDomain,@RequestParam("cur") Integer cur,@RequestParam("total") Integer total,@RequestParam("password") String password,@RequestParam("name") String name)  {
        if(animeMsgDomain==null || cur==null || cur<=0 || total==null || cur>total || password==null || !password.equals(passwo) || name==null || name.isEmpty() || animeMsgDomain.getName()==null || animeMsgDomain.getName().isEmpty()){
            return 0;
        }
        animeMsgDomain.setCreateTime(new Date());
        animeMsgDomain.setUpdateTime(new Date());
        animeMsgDomain.setIsDel(0);
        animeMsgDomain.setQuarter(name);
        animeMsgDomain.setIsHand(0);
        if(animeMsgDomain.getSubjectId()==null){
            animeMsgDomain.setSubjectId("-1");
        }
        AnimeMsgDomain msgByNQ = animeDao.getMsgByNQ(animeMsgDomain.getName(), name);
        if(msgByNQ==null){
            Integer res= animeDao.addAnimeMsg(animeMsgDomain);
            if(res!=1){
                return 0;
            }
            if(cur.equals(total)){
                animeDao.addHis(null,name,new Date(),new Date(),0);
                return 2;
            }
        }
        else{
            if(msgByNQ.getIsHand()==0){
                Integer res=animeDao.updateMsg(animeMsgDomain.getPic(),animeMsgDomain.getMsg(),animeMsgDomain.getDec(),new Date(),animeMsgDomain.getName(),name,animeMsgDomain.getWeek(), animeMsgDomain.getRate(), animeMsgDomain.getSubjectId(),animeMsgDomain.getIsHand());
                if(res!=1){
                    return 0;
                }
                if(cur.equals(total)){
                    animeDao.addHis(null,name,new Date(),new Date(),0);
                    return 2;
                }
                return 1;
            }
        }
        return 1;
    }

    @GetMapping("/subject")
    private String getdec(@RequestParam("id") String id,@RequestParam("password") String password){
        if(id==null || id.isEmpty() || password==null || !password.equals(passwo)){
            return "";
        }
        HttpClient httpClient = new HttpClient();
        String url="https://movie.douban.com/subject/"+id;
        GetMethod getMethod=new GetMethod(url);
        try {
            int num = httpClient.executeMethod(getMethod);
            if(num==200){
                String c=getMethod.getResponseBodyAsString();
                int index2=c.indexOf("<span property=\"v:summary\" class=\"\">");
                if(index2==-1){
                    return "";
                }
                c=c.substring(index2+37);
                index2=c.indexOf("</span>");
                c=c.substring(0,index2).trim();
                c=c.replaceAll("&quot","");
                c=c.replaceAll("<br />","");
                c=c.replaceAll("\n","");
                c=c.replaceAll("\\s+","");
                return c;
            }
            else{
               return "";
            }
        } catch (IOException e) {
            return "";
        }
    }

    @GetMapping("/getAllTimes")
    private List<String> getAllTimes(@RequestParam("unEncrypted") String unEncrypted,@RequestParam("encrypted") String encrypted){
        if(unEncrypted == null || !MD5Util.getMd5(unEncrypted+secret).equals(encrypted)){
            return new ArrayList<>();
        }
        List<String> list=animeDao.getAllQuarter();
        if(list==null){
            return new ArrayList<>();
        }
        Set<String> set = new HashSet<>(list);
        List<String> res=new ArrayList<>(set);
        res.sort(Comparator.reverseOrder());
        return res;
    }

    @GetMapping("getAnimeMsg")
    private List<AnimeMsgDomain> getAnimeMsg(@RequestParam("quarter") String quarter,@RequestParam("week") String week,@RequestParam("unEncrypted") String unEncrypted,@RequestParam("encrypted") String encrypted){
        if(quarter==null || week==null || unEncrypted == null || !MD5Util.getMd5(unEncrypted+secret).equals(encrypted)){
            return new ArrayList<>();
        }
        if(week.equals("全部")){
            List<AnimeMsgDomain> list=animeDao.getAnimeByQuarter(quarter);
            return list==null?new ArrayList<>():list;
        }
        else{
            List<AnimeMsgDomain> list=animeDao.getAnimeByQuarterAndWeek(quarter,week);
            return list==null?new ArrayList<>():list;
        }
    }
    @GetMapping("/search")
    private List<AnimeMsgDomain> searchAnime(@RequestParam("password") String password,@RequestParam("searchValue") String searchValue){
        if(password==null || !password.equals(passwo) || searchValue==null){
            return new ArrayList<>();
        }
        List<AnimeMsgDomain> list=animeDao.searchAnime(searchValue);
        return list==null?new ArrayList<>():list;
    }

    @PostMapping("/edit")
    private Integer edit(@RequestBody AnimeMsgDomain animeMsgDomain){
        if(animeMsgDomain==null){
            return 0;
        }
        animeMsgDomain.setUpdateTime(new Date());
        return animeDao.editMsg(animeMsgDomain);
    }

    @GetMapping("/getdouban")
    private String getdouban(@RequestParam("name") String name) throws IOException, URISyntaxException {
        return PAUtil.getdouban(name);
    }

    @PostMapping("/getMonthlyAnime")
    private String getMonthlyAnime(@RequestBody MdDomain mdDomain) throws IOException {
        return PAUtil.getMonthlyAnime(mdDomain.getUrl());
    }

    @GetMapping("/wx/message")
    private String wxMessage(@RequestParam("signature") String signature,@RequestParam("echostr") String echostr,@RequestParam("timestamp") String timestamp,@RequestParam("nonce") String nonce) throws NoSuchAlgorithmException {
        String token="1885251Qwer";
        String[] arr=new String[]{token,timestamp,nonce};
        Arrays.sort(arr);
        StringBuilder stringBuilder= new StringBuilder();
        for(String i:arr){
            stringBuilder.append(i);
        }
        MessageDigest digest = MessageDigest.getInstance("SHA-1");
        byte[] encodedhash = digest.digest(stringBuilder.toString().getBytes());
        String base64Encoded = Base64.getEncoder().encodeToString(encodedhash);
        if(base64Encoded.equals(signature)){
            return echostr;
        }
        return "";
    }
}
