package starwu.lite.design.observer;

import starwu.lite.util.SpringUtil;

import javax.annotation.PostConstruct;

public interface ObserverApi<T> {

    public boolean isReceived(T t);

    public Class<? extends SubjectApi> getSubjectCls();

    public void received(T t);

    @PostConstruct
    public default void regist(){
        SubjectApi sub = SpringUtil.getBean(getSubjectCls());
        sub.regist(this);

    }
}
