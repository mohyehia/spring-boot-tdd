package com.moh.yehia.tdd.model.dto;

public record TaskDTO(String name, String description, int hour, int minute, String executionDays,
                      String executionCommand) {
}
