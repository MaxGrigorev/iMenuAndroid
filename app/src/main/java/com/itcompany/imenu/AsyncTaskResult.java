package com.itcompany.imenu;

/**
 * Created by Max on 19.05.2015.
 */
public class AsyncTaskResult//<T>
{
    //private T result;
    private Integer error;

    public AsyncTaskResult () {

    }

    //public T getResult() {
    //    return result;
    //}
    public Integer getError() {
        return error;
    }


    //public void setResult(T result) {
    //    this.result = result;
    //}


    public void setError(Integer error) {
        this.error = error;
    }
}