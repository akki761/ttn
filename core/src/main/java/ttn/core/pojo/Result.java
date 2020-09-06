package ttn.core.pojo;

import ttn.core.models.PageModel;

import java.util.List;

public class Result {

    private List<PageModel> pageModelList;

    private long totalResult;

    public List<PageModel> getPageModelList() {
        return pageModelList;
    }

    public void setPageModelList(List<PageModel> pageModelList) {
        this.pageModelList = pageModelList;
    }

    public long getTotalResult() {
        return totalResult;
    }

    public void setTotalResult(long totalResult) {
        this.totalResult = totalResult;
    }
}
