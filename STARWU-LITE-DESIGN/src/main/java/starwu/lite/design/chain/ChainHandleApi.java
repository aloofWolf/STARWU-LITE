package starwu.lite.design.chain;

import starwu.lite.util.SpringUtil;

import javax.annotation.PostConstruct;

public interface ChainHandleApi<T> {

    public int order();

    public default boolean isMatch(T t){
        return true;
    }

    public boolean process(T t);

    public Class<? extends ChainEntryApi> getChainCls();

    @PostConstruct
    public default void regist(){
        ChainEntryApi chain = SpringUtil.getBean(getChainCls());
        chain.regist(this);
        

    }
}
