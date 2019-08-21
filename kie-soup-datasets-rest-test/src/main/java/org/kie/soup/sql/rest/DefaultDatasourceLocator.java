package org.kie.soup.sql.rest;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.sql.DataSource;

import org.dashbuilder.dataprovider.sql.SQLDataSourceLocator;
import org.dashbuilder.dataset.def.SQLDataSetDef;

import io.agroal.api.AgroalDataSource;

@ApplicationScoped
public class DefaultDatasourceLocator implements SQLDataSourceLocator {
    
    @Inject
    @io.quarkus.agroal.DataSource("contacts")
    AgroalDataSource datasource;

    @Override
    public DataSource lookup(SQLDataSetDef def) throws Exception {
        
        return datasource;
    }

}
