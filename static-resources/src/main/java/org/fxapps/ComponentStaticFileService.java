package org.fxapps;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class ComponentStaticFileService {
    
    @Inject
    @ConfigProperty(name = "components.dir", defaultValue = "/tmp/components")
    String baseDir;

    public InputStream getComponentFile(String componentId, String fileName) throws FileNotFoundException {
        Path path = Paths.get(baseDir, componentId, fileName);
        return new FileInputStream(path.toFile());
    }

}