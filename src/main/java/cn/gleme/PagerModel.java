package cn.gleme;

import java.util.List;

/**
 * 
 * <p>Title: 分页list</p>
 *
 * @author cc
 * @date 2018年05月11日 下午2:37:10
 */

public class PagerModel<T> {

    private List<T> list;
    private Pager pager;

    public PagerModel() {
    }
   
    public PagerModel(List<T> listTemp,Pager pagerTemp){
    	this.list=listTemp;
    	this.pager=pagerTemp;
    }
    public List<T> getList() {
        return list;
    }

    
    public void setList(List<T> list) {
        this.list = list;
    }


    public Pager getPager() {
        return pager;
    }

    
    public void setPager(Pager pager) {
        this.pager = pager;
    }


    
  
}
