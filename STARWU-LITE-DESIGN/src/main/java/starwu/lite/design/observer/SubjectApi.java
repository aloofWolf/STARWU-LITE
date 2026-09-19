package starwu.lite.design.observer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import starwu.lite.manage.async.threadPool.AsyncThreadPool;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public abstract class SubjectApi<T> {

    private List<ObserverApi> globalList = new ArrayList<>();

    private ThreadPoolTaskExecutor executor;

    @Autowired
    private AsyncThreadPool asyncThreadPool;


    protected <V extends ObserverApi> void  regist(V v){
        globalList.add(v);
    }

    @PostConstruct
    public void init(){
        this.executor = asyncThreadPool.getThreadPoolTaskExecutor(getAsyncThreadName());

    }

    public void notify(T t)  {
        for(ObserverApi v : globalList){
            executor.execute(() -> {
                if(v.isReceived(t)){
                    v.received(t);
                }

            });
        }
    }

    public abstract String getAsyncThreadName();
}
