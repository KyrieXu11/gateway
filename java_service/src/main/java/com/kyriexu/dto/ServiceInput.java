package com.kyriexu.dto;

import com.kyriexu.utils.Constant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * @author KyrieXu
 * @since 2021/3/24 11:40
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceInput {
    @Min(value = 1, message = "页数最小为1")
    private int page;
    @Min(value = 1, message = "页大小最小为1")
    @Max(value = Constant.SIZE_LIMIT, message = "页大小最多为" + Constant.SIZE_LIMIT)
    private int size;
    private String info;


    public ServiceInput(int page, int size) {
        this.page = page;
        this.size = size;
    }
}
