package starwu.lite.design.chain;

import java.util.ArrayList;
import java.util.List;

public abstract class ChainEntryApi<T,V extends ChainHandleApi> {

    private List<V> globalList = new ArrayList<>();


    protected void  regist(V v){
        if(globalList.size() == 0){
            globalList.add(v);
            return;
        }

        if(v.order() >= globalList.get(globalList.size() - 1).order()){
            globalList.add(v);
            return;
        }
        for(int i = 0; i < globalList.size(); i++){
            if(globalList.get(i).order() > v.order()){
                globalList.add(i, v);
                return;
            }
        }
    }

    public void process(T t){
        for(V v : globalList){
            if(v.isMatch(t)){
                boolean result = v.process(t);
                if(!result){
                    return;
                }
            }
        }
    }
}
