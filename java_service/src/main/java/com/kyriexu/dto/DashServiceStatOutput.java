package com.kyriexu.dto;

import lombok.Data;

import java.util.List;

/**
 * @author KyrieXu
 * @since 2021/4/10 15:35
 **/
@Data
public class DashServiceStatOutput {
    private List<String> legend;
    private List<ServiceStatItemOutput> data;
}
