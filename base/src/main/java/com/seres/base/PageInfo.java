package com.seres.base;

import com.github.pagehelper.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@ApiModel(description = "分页信息")
public class PageInfo<T> {
    @ApiModelProperty(value = "分页数据")
    private List<T> list;
    @ApiModelProperty(value = "页码")
    private int pageNum;
    @ApiModelProperty(value = "页大小")
    private int pageSize;
    @ApiModelProperty(value = "总条数")
    private long total;
    @ApiModelProperty(value = "总页数")
    private int pages;


    public PageInfo(List<T> list) {
        if (list instanceof Page) {
            Page page = (Page)list;
            this.pageNum = page.getPageNum();
            this.pageSize = page.getPageSize();
            this.pages = page.getPages();
            this.total = page.getTotal();
        } else {
            this.pageNum = 1;
            this.pageSize = list.size();
            this.pages = this.pageSize > 0 ? 1 : 0;
            this.total = list.size();
        }
        this.list = list;
    }

    public static <T> PageInfo<T> of(List<T> list) {
        return new PageInfo(list);
    }
}
