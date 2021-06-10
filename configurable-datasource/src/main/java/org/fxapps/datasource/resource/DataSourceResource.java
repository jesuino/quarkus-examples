package org.fxapps.datasource.resource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.fxapps.datasource.DataSourceLoader;

@Path("datasource")
@Produces(MediaType.APPLICATION_JSON)
public class DataSourceResource {

    @Inject
    DataSourceLoader loader;
    
    @GET
    public Collection<String> list() {
        return loader.datasources();
    } 

    @POST
    @Path("{dsName}/query")
    public Response executeQuery(@PathParam("dsName") String dsName, String query) throws SQLException {
        var dataSourceOp = loader.getDataSource(dsName);
        
        if (dataSourceOp.isEmpty()) {
            return Response.status(Status.NOT_FOUND).build();
        }
        
        var ds = dataSourceOp.get();
        var connection = ds.getConnection();
        var result = extractResult(query, connection);
        connection.close();

        return Response.ok(result).build();
    }

    private HashMap<String, List<String>> extractResult(String query, Connection connection) throws SQLException {
        var result = new HashMap<String, List<String>>();
        try (var stmt = connection.createStatement()) {
            var rs = stmt.executeQuery(query);
            var meta = rs.getMetaData();
            var nColumns = meta.getColumnCount();
            while (rs.next()) {
                for (int i = 1; i <= nColumns; i++) {
                    var column = meta.getColumnName(i);
                    var value = rs.getString(i);

                    result.putIfAbsent(column, new ArrayList<String>());
                    result.get(column).add(value);
                }
            }
        }
        return result;
    }

}