package starwu.lite.canal.notify;

import starwu.lite.metadata.bean.canal.CanalDataBean;
import starwu.lite.design.observer.ObserverApi;

public  interface CanalObserverApi extends ObserverApi<CanalDataBean> {

    @Override
    public default Class<CanalSubject> getSubjectCls(){
        return CanalSubject.class;
    }
}
