package com.kyriexu.service;

import com.kyriexu.dto.PanelGroupDataOutput;

import javax.servlet.http.HttpServletRequest;

/**
 * @author KyrieXu
 * @since 2021/3/27 14:05
 **/
public interface DashBoardService {
    default PanelGroupDataOutput getRemotePanelGroupData(HttpServletRequest request,boolean isConsul) {
        return null;
    }

    PanelGroupDataOutput getLocalPanelGroupData();
}
