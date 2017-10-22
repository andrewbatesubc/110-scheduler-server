package com.dto;

import lombok.Data;

/**
 * Data transfer object to represent inbound and outbound schedule state
 */
@Data
public class ScheduleDto {
    String[] schedulesByDay = null;
}
