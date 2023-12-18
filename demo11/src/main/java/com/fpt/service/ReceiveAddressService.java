package com.fpt.service;

import com.fpt.dto.ReceiveAddressDto;
import com.fpt.dto.ReceiveAddressRequestDto;
import com.fpt.model.ReceiveAddress;

import java.util.List;

public interface ReceiveAddressService {
    ReceiveAddress findById(Long id);

    ReceiveAddressDto findReceiverById(Long id);

    ReceiveAddressDto createReceiveAddress (ReceiveAddressDto receiveAddress);

    ReceiveAddressDto updateReceiveAddress(ReceiveAddressRequestDto receiveAddressRequestDto);

    boolean deleteReceiveAddress(Long id);

    List<ReceiveAddressDto> listReceiveAddressByUserId(Long userId);

    ReceiveAddressDto getReceiveById(Long id);

    ReceiveAddressDto updateByDefaultAddress(Long id, ReceiveAddressDto receiveAddressDto);
}
