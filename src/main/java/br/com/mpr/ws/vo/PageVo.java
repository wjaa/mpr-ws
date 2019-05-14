package br.com.mpr.ws.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 *
 */
public class PageVo<T> {

    private int pageTotal;
    private int page;
    private int pageSize;
    private List<T> result;

    @JsonIgnore
    public static PageVo from(Page<ProdutoVo> p) {
        PageVo page = new PageVo();
        page.page = p.getNumber();
        page.pageTotal = p.getTotalPages();
        page.pageSize = p.getSize();
        page.result = p.getContent();
        return page;
    }

    public int getPageTotal() {
        return pageTotal;
    }

    public void setPageTotal(int pageTotal) {
        this.pageTotal = pageTotal;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }
}
