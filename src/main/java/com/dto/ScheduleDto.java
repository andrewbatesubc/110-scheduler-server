package com.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Data transfer object to represent inbound and outbound schedule state
 */
@Data
@AllArgsConstructor
public class ScheduleDto {
    String taName = null;
    String[] schedulesByDay = null;
}
