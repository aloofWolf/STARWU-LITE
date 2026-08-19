package starwu.lite.distributed.transaction.core;

import com.alibaba.fastjson.JSONObject;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import starwu.lite.metadata.constant.plus.threadLocal.ThreadLocalKey;
import starwu.lite.metadata.entity.distributed.transaction.Transaction;
import starwu.lite.metadata.entity.distributed.transaction.TransactionLog;
import starwu.lite.metadata.entity.web.RequestLog;
import starwu.lite.distributed.transaction.call.api.TransactionRemoteCallApi;
import starwu.lite.distributed.transaction.dao.TransactionDao;
import starwu.lite.distributed.transaction.dao.TransactionLogDao;
import starwu.lite.metadata.enums.distributed.transaction.CallTimeType;
import starwu.lite.metadata.enums.web.ErrorCodeType;
import starwu.lite.metadata.enums.web.ResponseResult;
import starwu.lite.plus.threadLocal.ThreadLocalPlus;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class TransactionCore {

    private final TransactionDao transactionDao;
    private final TransactionLogDao transactionLogDao;

    public <T> void add(String targetUrl,T param) {
        Transaction transaction = new Transaction();
        JSONObject paramJson = null;
        if(param != null) {
            paramJson = JSONObject.parseObject(param.toString());
        }

        RequestLog requestLog = ThreadLocalPlus.get(ThreadLocalKey.REQUEST_LOG_KEY);
        if(requestLog != null) {
            transaction.setCode(requestLog.getCode());
            transaction.setSourceUrl(requestLog.getUrl());
            transaction.setUserId(requestLog.getUserId());

        }
        transaction.setTargetUrl(targetUrl);
        transaction.setParams(paramJson);
        transaction.setCallTimeType(CallTimeType.IMMEDIATELY);
        transaction.setCallCount(0);
        transaction.setCreateTime(new Date());
        transactionDao.insert(transaction);
    }

    public void successHandle(Transaction transaction, TransactionRemoteCallApi api){
        transaction.setCallCount(transaction.getCallCount() + 1);
        transaction.setCallTime(new Date());
        transaction.setResult(ResponseResult.SUCCESS);
        transaction.setCallTimeType(CallTimeType.NONE);
        transactionDao.updateById(transaction);
        api.afterSuccessHandle(transaction);
    }

    public void failHandle(Transaction transaction, TransactionRemoteCallApi api, ErrorCodeType errorCode, String errorMsg){
        transaction.setCallCount(transaction.getCallCount() + 1);
        transaction.setCallTime(new Date());
        transaction.setResult(ResponseResult.FAIL);
        if(transaction.getCallCount() >= api.retryCount()){
            transaction.setCallTimeType(CallTimeType.NONE);
        }else{
            transaction.setCallTimeType(CallTimeType.SCHEDULED);
        }
        transactionDao.updateById(transaction);

        TransactionLog log = new TransactionLog();
        log.setTransactionId(transaction.getId());
        log.setResult(ResponseResult.FAIL);
        log.setParams(transaction.getParams());
        log.setTargetUrl(transaction.getTargetUrl());
        log.setErrCode(errorCode);
        log.setErrMsg(errorMsg);
        log.setCallTime(transaction.getCallTime());
        transactionLogDao.insert(log);
    }
}
