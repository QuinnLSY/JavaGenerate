package com.java_generate_demo.entity.query;
import com.java_generate_demo.enums.PageSize;


public class SimplePage {
    private int pageNo;
    private int pageSize;
    private int countTotal;
    private int pageTotal;
    private int start;
    private int end;

    public SimplePage(){

    }

    public SimplePage(Integer pageNo, int pageSize, int countTotal) {
        if(null == pageNo){
            pageNo = 0;
        }
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.countTotal = countTotal;
        action();
    }

    public SimplePage(int start, int end){
        this.start = start;
        this.end = end;
    }

    private void action(){
        if(this.pageSize <= 0){
            this.pageSize = PageSize.SIZE20.getSize();
        }
        if(this.countTotal > 0){
            this.pageTotal = this.countTotal % this.pageSize == 0 ? this.countTotal / this.pageSize : this.countTotal / this.pageSize + 1;
        } else {
            pageTotal = 1;
        }
        if(pageNo <= 1){
            pageNo = 1;
        }
        if(pageNo >= pageTotal){
            pageNo = pageTotal;
        }
        this.start = (pageNo - 1) * pageSize;
        this.end = this.pageSize;
    }


    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCountTotal() {
        return countTotal;
    }

    public void setCountTotal(int countTotal) {
        this.countTotal = countTotal;
        this.action();
    }

    public int getPageTotal() {
        return pageTotal;
    }

    public void setPageTotal(int pageTotal) {
        this.pageTotal = pageTotal;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }
}
