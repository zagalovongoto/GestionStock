package org.mambey.gestiondestock.interceptor;

import org.hibernate.EmptyInterceptor;
import org.slf4j.MDC;
import org.springframework.util.StringUtils;


public class Interceptor extends EmptyInterceptor{
    
    @Override
    public String onPrepareStatement(String sql) {

        String sqlLower = sql.toLowerCase();

        if(StringUtils.hasLength(sql) && sqlLower.startsWith("select")){
            //select utilisateur0_.
            //final String entityName = sql.substring(7, sql.indexOf("."));;
            final String entityName;
            if(sqlLower.startsWith("select sum")){
                entityName = sql.substring(11, sql.indexOf("."));
            }
            else if(sqlLower.startsWith("select count")){
                entityName = sql.substring(13, sql.indexOf("."));
            }
            else{
                entityName = sql.substring(7, sql.indexOf("."));
            }

            final String idEntreprise = MDC.get("idEntreprise");
            if(StringUtils.hasLength(entityName)
                && !entityName.toLowerCase().contains("entreprise")
                && !entityName.toLowerCase().contains("roles")
                && !entityName.toLowerCase().contains("utilisateu")
                && StringUtils.hasLength(idEntreprise)
            ){
                if(sqlLower.contains("limit")){

                    int index = sqlLower.indexOf("limit");
                    String sub = sql.substring(index);
                    String sub2;
                    if(sql.contains("where")){
                        sub2 = "and " + entityName + ".idEntreprise = " + idEntreprise + " " + sub;
                    }else{
                        sub2 = "where " + entityName + ".idEntreprise = " + idEntreprise + " " + sub;
                    }

                    sql = sql.replace(sub, sub2) ;

                }else if(sql.contains("where")){
                    sql += " and " + entityName + ".idEntreprise = " + idEntreprise;
                }else{
                    sql += " where " + entityName + ".idEntreprise = " + idEntreprise;
                }
            }
            
        }
        return super.onPrepareStatement(sql);
    }
}
