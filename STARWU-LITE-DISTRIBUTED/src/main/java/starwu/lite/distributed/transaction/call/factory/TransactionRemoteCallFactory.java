package starwu.lite.distributed.transaction.call.factory;

import org.springframework.stereotype.Component;
import starwu.lite.design.factory.FactoryEntryApi;
import starwu.lite.distributed.transaction.call.api.TransactionRemoteCallApi;

@Component
public class TransactionRemoteCallFactory extends FactoryEntryApi<String, TransactionRemoteCallApi> {

    @Override
    public <C extends TransactionRemoteCallApi> Class<C> getDefaultCls() {
        return null;
    }
}
