package com.inz.PlayOut.Model;

import javax.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
public class SportEvent {

    private final String name;
    private final LocalDateTime dateTime;
    private final String location;
    private final String note;

    protected SportEvent(Builder<?> builder) {
        this.name = builder.name;
        this.dateTime = builder.dateTime;
        this.location = builder.location;
        this.note = builder.note;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getLocation() {
        return location;
    }

    public String getNote() {
        return note;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder<T extends Builder<T>>{
        private String name;
        private LocalDateTime dateTime;
        private String location;
        private String note;

        public T name(final String name) {
            this.name = name;
            return (T) this;
        }

        public T dateTime(final LocalDateTime dateTime) {
            this.dateTime = dateTime;
            return (T) this;
        }

        public T location(final String location) {
            this.location = location;
            return (T) this;
        }

        public T note(final String note) {
            this.note = note;
            return (T) this;
        }

        public SportEvent build() {
            return new SportEvent(this);
        }
    }
}
