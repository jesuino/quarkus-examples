package org.fxapps.datasource;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.StreamSupport;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.sql.DataSource;

import io.agroal.api.AgroalDataSource;
import io.agroal.api.configuration.supplier.AgroalPropertiesReader;
import io.quarkus.runtime.StartupEvent;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class DataSourceLoader {

    private static final String DATASOURCES = "datasources";
    private static final String DATASOURCE = "datasource";
    private static final String PREFIX_TEMPLATE = DATASOURCE + ".%s.";

    @Inject
    Config config;

    @ConfigProperty(name = DATASOURCES, defaultValue = "")
    Optional<List<String>> datasourcesProp;

    Map<String, DataSource> registeredDataSources;

    void load(@Observes StartupEvent startup) throws SQLException {
        var allProps = new HashMap<String, String>();
        var datasources = datasourcesProp.orElse(List.of());
        
        registeredDataSources = new HashMap<>();

        StreamSupport.stream(config.getPropertyNames().spliterator(), false)
                     .filter(p -> p.startsWith(DATASOURCE))
                     .forEach(k -> allProps.put(k, config.getValue(k, String.class)));

        for (String ds : datasources) {
            var prefix = PREFIX_TEMPLATE.formatted(ds, AgroalPropertiesReader.JDBC_URL);
            var agroalProps = new AgroalPropertiesReader(prefix);
            agroalProps.readProperties(allProps);
            registeredDataSources.put(ds, AgroalDataSource.from(agroalProps.get()));
        }
        
    }

    public Optional<DataSource> getDataSource(String name) {
        return Optional.ofNullable(registeredDataSources.get(name));
    }
    
    public Set<String> datasources() {
        return registeredDataSources.keySet();
    }

}