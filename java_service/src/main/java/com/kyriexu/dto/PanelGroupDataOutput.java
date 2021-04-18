package com.kyriexu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author KyrieXu
 * @since 2021/3/25 17:41
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PanelGroupDataOutput {
    private long serviceNum;
    private long appNum;
    private long currentQps;
    private long todayRequestNum;
}
