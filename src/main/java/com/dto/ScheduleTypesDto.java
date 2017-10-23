package com.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Data transfer object to represent inbound and outbound schedule types. Please have this match 1:1 to client-side DTO.
 */
@Data
@AllArgsConstructor
public class ScheduleTypesDto {
    String[] scheduleTypes = null;
}
