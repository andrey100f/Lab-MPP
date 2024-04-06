package ro.mpp2024.concertpro.utils.event;

import ro.mpp2024.concertpro.dao.model.Spectacle;

public class SpectacleChangeEventType implements Event {
    private final ChangeEventType type;
    private final Spectacle data;
    private Spectacle oldData;

    public SpectacleChangeEventType(ChangeEventType type, Spectacle data) {
        this.type = type;
        this.data = data;
    }

    public SpectacleChangeEventType(ChangeEventType type, Spectacle data, Spectacle oldData) {
        this.type = type;
        this.data = data;
        this.oldData = oldData;
    }

    public ChangeEventType getType() {
        return type;
    }

    public Spectacle getData() {
        return data;
    }

    public Spectacle getOldData() {
        return oldData;
    }
}
