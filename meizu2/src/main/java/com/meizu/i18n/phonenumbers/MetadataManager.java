package com.meizu.i18n.phonenumbers;

import com.meizu.i18n.phonenumbers.Phonemetadata.PhoneMetadata;
import com.meizu.i18n.phonenumbers.Phonemetadata.PhoneMetadataCollection;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

class MetadataManager {
    private static final String ALTERNATE_FORMATS_FILE_PREFIX = "/com/meizu/i18n/phonenumbers/data/PhoneNumberAlternateFormatsProto";
    private static final Logger LOGGER = Logger.getLogger(MetadataManager.class.getName());
    private static final Map<Integer, PhoneMetadata> callingCodeToAlternateFormatsMap = Collections.synchronizedMap(new HashMap());
    private static final Set<Integer> countryCodeSet = AlternateFormatsCountryCodeSet.getCountryCodeSet();

    private MetadataManager() {
    }

    private static void close(InputStream inputStream) {
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e) {
                LOGGER.log(Level.WARNING, e.toString());
            }
        }
    }

    private static void loadMetadataFromFile(int i) {
        InputStream objectInputStream;
        IOException e;
        Throwable th;
        try {
            objectInputStream = new ObjectInputStream(PhoneNumberMatcher.class.getResourceAsStream("/com/meizu/i18n/phonenumbers/data/PhoneNumberAlternateFormatsProto_" + i));
            try {
                PhoneMetadataCollection phoneMetadataCollection = new PhoneMetadataCollection();
                phoneMetadataCollection.readExternal(objectInputStream);
                for (PhoneMetadata phoneMetadata : phoneMetadataCollection.getMetadataList()) {
                    callingCodeToAlternateFormatsMap.put(Integer.valueOf(phoneMetadata.getCountryCode()), phoneMetadata);
                }
                close(objectInputStream);
            } catch (IOException e2) {
                e = e2;
                try {
                    LOGGER.log(Level.WARNING, e.toString());
                    close(objectInputStream);
                } catch (Throwable th2) {
                    th = th2;
                    close(objectInputStream);
                    throw th;
                }
            }
        } catch (IOException e3) {
            e = e3;
            objectInputStream = null;
            LOGGER.log(Level.WARNING, e.toString());
            close(objectInputStream);
        } catch (Throwable th3) {
            th = th3;
            objectInputStream = null;
            close(objectInputStream);
            throw th;
        }
    }

    static PhoneMetadata getAlternateFormatsForCountry(int i) {
        if (!countryCodeSet.contains(Integer.valueOf(i))) {
            return null;
        }
        synchronized (callingCodeToAlternateFormatsMap) {
            if (!callingCodeToAlternateFormatsMap.containsKey(Integer.valueOf(i))) {
                loadMetadataFromFile(i);
            }
        }
        return (PhoneMetadata) callingCodeToAlternateFormatsMap.get(Integer.valueOf(i));
    }
}
