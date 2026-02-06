package com.halo.eventer.domain.program_reservation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.halo.eventer.domain.program_reservation.ProgramBlock;
import com.halo.eventer.domain.program_reservation.ProgramBlock.BlockType;

public interface ProgramBlockRepository extends JpaRepository<ProgramBlock, Long> {

    List<ProgramBlock> findAllByProgramIdOrderBySortOrder(Long programId);

    List<ProgramBlock> findAllByProgramIdAndTypeOrderBySortOrder(Long programId, BlockType type);

    void deleteAllByProgramId(Long programId);
}
