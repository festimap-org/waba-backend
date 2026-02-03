package com.halo.eventer.domain.program_reservation.dto.request;

import com.halo.eventer.domain.program_reservation.ProgramReservationStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.List;

@Getter
public class AdminStatusUpdateRequest {
    @NotEmpty
    private List<Long> reservationIds;
    @Schema(type = "string", example = "REJECTED") @NotNull private ProgramReservationStatus status;
}
