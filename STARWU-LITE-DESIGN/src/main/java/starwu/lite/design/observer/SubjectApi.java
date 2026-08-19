package starwu.lite.design.observer;

import lombok.extern.slf4j.Slf4j;
import starwu.lite.metadata.exception.AsyncException;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
public abstract class SubjectApi<T> {

    private List<ObserverApi> globalList = new ArrayList<>();

    private ExecutorService executor;


    protected <V extends ObserverApi> void  regist(V v){
        globalList.add(v);
    }

    @PostConstruct
    public void init(){
        this.executor = getExecutorService();

    }

    public void notify(T t) throws InterruptedException {
        for(ObserverApi v : globalList){
            v.received(t);
        }

    }

    public abstract ExecutorService getExecutorService();
}
