package com.halo.eventer.domain.program_reservation.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@NoArgsConstructor
public class ProgramBookingInfoRequest {
    @NotNull
    @Schema(example = "2026-02-01")
    private LocalDate bookingOpenDate;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    @Schema(example = "10:00")
    private LocalTime bookingOpenTime;

    @NotNull @Schema(example = "2026-02-28")
    private LocalDate bookingCloseDate;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    @Schema(example = "10:00")
    private LocalTime bookingCloseTime;
}
