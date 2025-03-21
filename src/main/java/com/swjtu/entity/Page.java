package com.swjtu.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class Page {
    private int current = 1;
    private int limit = 10;
    private int rows;
    private String path;


    /**
     * 获取
     * @return current
     */
    public int getCurrent() {
        return current;
    }

    /**
     * 设置
     * @param current
     */
    public void setCurrent(int current) {
        if(current >=1){
            this.current = current;
        }
    }

    /**
     * 获取
     * @return limit
     */
    public int getLimit() {
        return limit;
    }

    /**
     * 设置
     * @param limit
     */
    public void setLimit(int limit) {
        if(limit >= 1 && limit <= 100){
            this.limit = limit;
        }
    }

    /**
     * 获取
     * @return rows
     */
    public int getRows() {
        return rows;
    }

    /**
     * 设置
     * @param rows
     */
    public void setRows(int rows) {
        if(rows >= 0){
            this.rows = rows;
        }
    }

    /**
     * 获取
     * @return path
     */
    public String getPath() {
        return path;
    }

    /**
     * 设置
     * @param path
     */
    public void setPath(String path) {
        this.path = path;
    }

    public String toString() {
        return "Page{current = " + current + ", limit = " + limit + ", rows = " + rows + ", path = " + path + "}";
    }

    //获取当前页的起始行
    public int getOffset(){
        return (current - 1) * limit;
    }
    /*
    * 获取总页数
    *
    * */
    public int getTotal(){
        if(rows % limit == 0){
            return rows / limit;
        }else {
            return rows / limit + 1;
        }
    }

    //获取起始页码
    public int getFrom(){
        int from = current - 2;
        return from < 1 ? 1 : from;
    }
    //获取结束页码
    public int getTo() {
        int to = current + 2;
        int total = getTotal();
        return to > total ? total : to;
    }
}
