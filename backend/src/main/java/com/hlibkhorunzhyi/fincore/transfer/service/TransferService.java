package com.hlibkhorunzhyi.fincore.transfer.service;


import com.hlibkhorunzhyi.fincore.transfer.dto.TransferRequest;
import com.hlibkhorunzhyi.fincore.transfer.dto.TransferResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TransferService {

    TransferResponse getTransfer(Long transferId, Long userId);

    Page<TransferResponse> getTransfersByAccountId(Long accountId, Long userId, Pageable pageable);

    TransferResponse transfer(TransferRequest request, Long userId);
}
