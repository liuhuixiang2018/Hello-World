package com.lhx.demo.service.impl;

import com.psbc.psbccommon.mapper.TbClmMerChkDtlMapper;
import com.psbc.psbccommon.mapper.TbClmOrdTxnMapper;
import com.psbc.psbccommon.mode.TbClmMerChkDtlKey;
import com.psbc.psbccommon.mode.TbClmOrdTxn;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cassandra.CassandraProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
public class MyServiceImpl {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private Redisson redisson;
    @Autowired
    private TbClmOrdTxnMapper orderMapper;

    @Autowired
    private TbClmMerChkDtlMapper tbClmMerChkDtlMapper;

    public String callService() {
        String url = "http://localhost:8080/hello";
        System.out.println("restTemplate"+restTemplate.toString());
        return restTemplate.getForObject(url, String.class);
    }
    @Transactional(rollbackFor = Exception.class)
    public boolean order(CassandraProperties.Request request) {
        RLock lock = redisson.getLock(request.toString());
        try {
            //一锁
            lock.lock();
            //二查
            TbClmOrdTxn order = orderMapper.selectBybatid("20240823","120");
            if (order!=null) {
                order.setDtTxnStatus("01");
                return false;
            }
            //三更新，保存订单数据
            orderMapper.insert(null);

        } finally {
            lock.unlock();
        }
        //三更新，保存订单流水
        tbClmMerChkDtlMapper.insert(null);
        return true;
    }
}