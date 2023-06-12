package org.mambey.gestiondestock.interceptor;

import org.hibernate.EmptyInterceptor;
import org.slf4j.MDC;
import org.springframework.util.StringUtils;


public class Interceptor extends EmptyInterceptor{
    
    @Override
    public String onPrepareStatement(String sql) {
        
        if(StringUtils.hasLength(sql) && sql.toLowerCase().startsWith("select")){
            //select utilisateur0_.
            final String entityName = sql.substring(7, sql.indexOf("."));
            final String idEntreprise = MDC.get("idEntreprise");
            if(StringUtils.hasLength(entityName)
                && !entityName.toLowerCase().contains("entreprise")
                && !entityName.toLowerCase().contains("roles")
                && !entityName.toLowerCase().contains("utilisateu")
                && StringUtils.hasLength(idEntreprise)
            ){
                if(sql.contains("where")){
                    sql += " and " + entityName + ".idEntreprise = " + idEntreprise;
                }else{
                    sql += " where " + entityName + ".idEntreprise = " + idEntreprise;
                }
            }
        }
        
        return super.onPrepareStatement(sql);
    }
}
