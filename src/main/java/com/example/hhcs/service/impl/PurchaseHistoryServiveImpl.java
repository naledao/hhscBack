package com.example.hhcs.service.impl;

import com.example.hhcs.dao.PurchaseHistoryDao;
import com.example.hhcs.dao.SstoreDao;
import com.example.hhcs.dao.ZhanDao;
import com.example.hhcs.domain.PurchaseHistory;
import com.example.hhcs.domain.Sstore;
import com.example.hhcs.domain.Zhan;
import com.example.hhcs.service.PurchaseHistoryServive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PurchaseHistoryServiveImpl implements PurchaseHistoryServive {
    @Autowired
    private SstoreDao sstoreDao;
    @Autowired
    private ZhanDao zhanDao;
    @Autowired
    private PurchaseHistoryDao purchaseHistorydao;
    @Override
    public int ups(int s, String openid) {
//        判断历史中是否存在该用户的的id，不存在插入，存在执行求购
        String a=purchaseHistorydao.getyn(openid);
        if(a==null)
        {
//            用户不存在，插入用户
            PurchaseHistory purchaseHistory=new PurchaseHistory();
            purchaseHistory.setOpenid(openid);
            purchaseHistory.setS(s);
            int b=purchaseHistorydao.addpeop(purchaseHistory);
            return b;
        }
        else
        {
            return purchaseHistorydao.ups(s,openid);
        }

    }
}
