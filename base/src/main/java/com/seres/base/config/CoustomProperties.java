package com.seres.base.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 系统通用自定义配置类，根据实际项目情况自定义配置文件及类属性、方法
 */
@Data
@ConfigurationProperties(prefix = "coustom")
public class CoustomProperties {

//    /**
//     * 管理单位，如：coustom.admin-orgs: 1,2,3
//     */
//    private List<Integer> adminOrgs;
//
//    /**
//     * 是否为管理单位
//     * @param org
//     * @return
//     */
//    public boolean isAdminOrg(Integer org){
//        if(!StringUtils.isEmpty(org) && adminOrgs.contains(org)){
//            return true;
//        }
//        return false;
//    }

}