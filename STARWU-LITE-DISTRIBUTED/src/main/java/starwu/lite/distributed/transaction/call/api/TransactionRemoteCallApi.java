package starwu.lite.distributed.transaction.call.api;

import com.alibaba.fastjson2.JSONObject;
import starwu.lite.metadata.bean.web.ResponseBean;
import starwu.lite.design.factory.FactoryHandleApi;
import starwu.lite.distributed.transaction.call.factory.TransactionRemoteCallFactory;
import starwu.lite.metadata.entity.distributed.transaction.Transaction;
import starwu.lite.metadata.enums.web.ErrorCodeType;

public interface TransactionRemoteCallApi extends FactoryHandleApi<String> {

    @Override
    public default Class<TransactionRemoteCallFactory> getFactoryCls() {
        return TransactionRemoteCallFactory.class;
    }

    public <T> ResponseBean call(JSONObject json);

    public default void afterSuccessHandle(Transaction transaction){}

    public default void afterFailHandle(Transaction transaction, ErrorCodeType errorCode,String errMsg) {}

    public default int retryCount(){
        return 3;
    }


}

