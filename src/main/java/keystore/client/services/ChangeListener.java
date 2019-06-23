package keystore.client.services;

import keystore.client.models.ChangeEvent;

@FunctionalInterface
public interface ChangeListener {
    void handle(ChangeEvent ce);
}
