package io.ylab.intensive.lesson04.eventsourcing.request;

/**
 * Класс запрос. Содержит метод, по которому
 * в DbApp будет происходить понимание
 * какой именно запрос пришел - get или post
 */
public class Request {
    private RequestMethod method;

    public Request(RequestMethod method) {
        this.method = method;
    }

    public void setMethod(RequestMethod method) {
        this.method = method;
    }


    public RequestMethod getMethod() {
        return method;
    }

}
