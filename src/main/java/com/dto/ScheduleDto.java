package com.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Data transfer object to represent inbound and outbound schedule state. Please have this match 1:1 to client-side DTO.
 */
@Data
@AllArgsConstructor
public class ScheduleDto {
    String taName = null;
    String[] schedulesByDay = null;
}
