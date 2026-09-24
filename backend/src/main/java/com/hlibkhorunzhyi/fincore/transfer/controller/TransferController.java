package com.hlibkhorunzhyi.fincore.transfer.controller;

import com.hlibkhorunzhyi.fincore.transfer.dto.TransferRequest;
import com.hlibkhorunzhyi.fincore.transfer.dto.TransferResponse;
import com.hlibkhorunzhyi.fincore.transfer.service.TransferService;
import com.hlibkhorunzhyi.fincore.user.entity.User;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/transfers")
public class TransferController {

    private final TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransferResponse> getTransfer(
            @PathVariable Long id,
            @AuthenticationPrincipal User user
            ) {

        TransferResponse response = transferService.getTransfer(id, user.getId());
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<TransferResponse> transfer(
            @Valid @RequestBody TransferRequest request,
            @AuthenticationPrincipal User user
            ){

        TransferResponse response = transferService.transfer(request, user.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
