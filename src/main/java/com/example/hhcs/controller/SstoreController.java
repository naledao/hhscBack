package com.example.hhcs.controller;

import com.example.hhcs.domain.Sstore;
import com.example.hhcs.domain.User;
import com.example.hhcs.service.SstoreService;
import com.example.hhcs.service.UserService;
import com.example.hhcs.util.ToBrowseHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/store")
public class SstoreController {
    @Autowired
    private SstoreService sstoreService;
    @Autowired
    private UserService userService;
    @Autowired
    private ToBrowseHistory toBrowseHistory;
    @GetMapping("/sstore/{op}")
    private Sstore get(@PathVariable String op){
        if(!op.equals("0")){
//            添加商品浏览记录
            User user= userService.get(op);
            int browseid=user.getWarehouse();
//            s级商品id
            int historyid=sstoreService.get().getId();
            toBrowseHistory.addhistory("s",browseid,historyid);
        }
        return sstoreService.get();
    }

}
