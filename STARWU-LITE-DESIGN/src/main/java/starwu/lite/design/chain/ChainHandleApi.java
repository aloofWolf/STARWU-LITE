package starwu.lite.design.chain;

import starwu.lite.util.SpringUtil;

import javax.annotation.PostConstruct;

public interface ChainHandleApi<T,CHAIN extends ChainEntryApi> {

    public int order();

    public default boolean isMatch(T t){
        return true;
    }

    public boolean process(T t);

    public Class<CHAIN> getChainCls();

    @PostConstruct
    public default void regist(){
        CHAIN chain = SpringUtil.getBean(getChainCls());
        chain.regist(this);
        

    }
}
