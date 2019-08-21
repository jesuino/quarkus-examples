package org.kie.soup.sql.rest;

import java.util.stream.Stream;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.dashbuilder.DataSetCore;
import org.dashbuilder.dataprovider.DataSetProviderRegistry;
import org.dashbuilder.dataprovider.sql.SQLDataSetProvider;
import org.dashbuilder.dataset.DataSet;
import org.dashbuilder.dataset.def.DataSetDefRegistry;
import org.dashbuilder.dataset.def.SQLDataSetDef;

@Path("/datasets")
public class DatasetResource {
    
    @Inject
    DefaultDatasourceLocator dataSourceLocator;
    private DataSet theDataSet;

    @PostConstruct
    public void buildDataSet() throws Exception {
        DataSetDefRegistry dataSetDefRegistry = DataSetCore.get().getDataSetDefRegistry();
        SQLDataSetProvider sqlDataSetProvider = SQLDataSetProvider.get();

        sqlDataSetProvider.setDataSourceLocator(dataSourceLocator);

        // Add SQL data sets support
        DataSetProviderRegistry dataSetProviderRegistry = DataSetCore.get().getDataSetProviderRegistry();
        dataSetProviderRegistry.registerDataProvider(sqlDataSetProvider);

        // Register the SQL data set
        SQLDataSetDef def = new SQLDataSetDef();
        def.setDbTable("PERSON");
        def.setDbSQL("select * from PERSON");

        def.setUUID("TEST_DS");
        def.setDataSource("TEST");
        dataSetDefRegistry.registerDataSetDef(def);

        theDataSet = sqlDataSetProvider.lookupDataSet(def, null);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Object hello() {
        return theDataSet.getValueAt(0, 0);
    }
}