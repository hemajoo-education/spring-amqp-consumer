package com.hemajoo.education.wow.queue.commons;

import lombok.Getter;
import lombok.NonNull;

import java.util.Arrays;

public enum EventType
{
    WARSONG_GULCH(EventCategoryType.BATTLEGROUND, "Warsong Gulch"),
    ARATHI_BASIN(EventCategoryType.BATTLEGROUND, "Arathi Basin"),
    EYE_OF_THE_STORM(EventCategoryType.BATTLEGROUND, "Eye of the Storm"),
    TWIN_PEAKS(EventCategoryType.BATTLEGROUND, "Twin Peaks"),
    BATTLE_FOR_GILNEAS(EventCategoryType.BATTLEGROUND, "Battle for Gilneas"),
    TEMPLE_OF_KOTMOGU(EventCategoryType.BATTLEGROUND, "Temple of Kotmogu"),
    SILVERSHARD_MINES(EventCategoryType.BATTLEGROUND, "Silvershard Mines"),
    DEEPWIND_GORGE(EventCategoryType.BATTLEGROUND, "Deepwind Gorge"),

    RING_OF_TRIALS(EventCategoryType.ARENA, "Ring of Trials"),
    CIRCLE_OF_BLOOD(EventCategoryType.ARENA, "Circle of Blood"),
    RUINS_OF_LORDAERON(EventCategoryType.ARENA, "Ruins of Lordaeron"),
    DALARAN(EventCategoryType.ARENA, "Dalaran"),
    TOL_VIRON(EventCategoryType.ARENA, "Tol'Viron"),
    TIGER_PEAK(EventCategoryType.ARENA, "Tiger's Peak"),
    ASHAMANE_FALL(EventCategoryType.ARENA, "Ashamane's Fall");

    @Getter
    private EventCategoryType categoryType;

    @Getter
    private String eventName;

    EventType(final EventCategoryType categoryType, final @NonNull String eventName)
    {
        this.categoryType = categoryType;
        this.eventName = eventName;
    }

    public static EventType from(final @NonNull String name)
    {
        EventType event = Arrays.stream(EventType.values())
                .filter(p -> p.eventName.toLowerCase().equals(name.toLowerCase()))
                .findFirst()
                .orElse(null);

        return event;
    }
}
