package org.giu.storage;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

import org.giu.model.Band;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.util.stream.Collectors.toList;

/**
 *  In Memory storage for Band objects.
 */
@ApplicationScoped
public class BandStorage {

    private static final AtomicInteger idGenerator = new AtomicInteger(0);

    private static Map<Integer, Band> storage = new HashMap<>();

    Logger logger = LoggerFactory.getLogger(BandStorage.class);

    /**
     * Internal method that adds some base data
     */
    @PostConstruct
    public void init() {
        logger.info("Adding initial data to the storage");
        List.of(
                Band.of("The Beatles", 1957),
                Band.of("Rolling Stones", 1962),
                Band.of("Pink Floyd", 1965),
                Band.of("The Doors", 1965),
                Band.of("Black Sabbath", 1968),
                Band.of("Led Zeppelin", 1968),
                Band.of("Queen", 1970))
            .forEach(this::store);

    }

    /**
     * Store bands
     * @param band
     * The band object. The id is always overwritten. 
     */
    public void store(Band band) {
        band.setId(idGenerator.incrementAndGet());
        storage.put(band.getId(), band);
    }

    /**
     * 
     * Access all stored bands
     * @return
     * The list of all stored bands. 
     */
    public Collection<Band> all() {
        return storage.values();
    }

    /**
     * 
     * Remove a band from storage.
     * @param id
     * The band ID to be returned.
     * @return
     * true if a band was found and returned, false otherwise.
     */
    public boolean remove(int id) {
        return storage.remove(id) != null;
    }

    /**
     * Search bands by name.
     * @param bandName
     * a band name or part of the band name
     * @return
     * The list of bands found in the storage;
     */
    public List<Band> searchByName(String bandName) {
        return filterBands(band -> band.getName()
                                       .toLowerCase()
                                       .contains(bandName));
    }

    /**
     * Search bands by debug year.
     * @param year
     * The band debug year
     * @return
     * The list of bands found in the storage;
     */
    public List<Band> searchByYear(int year) {
        return filterBands(band -> band.getDebutYear() == year);
    }

    /**
     * 
     * Filter bands in the storage.
     * @param filter
     * The filter to the applied to all bands in the storage.
     * @return
     * The list of bands found according to the filter.
     */
    private List<Band> filterBands(Predicate<Band> filter) {
        return storage.values().stream()
                      .filter(filter)
                      .collect(toList());
    }

}