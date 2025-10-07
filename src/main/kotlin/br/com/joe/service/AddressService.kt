package br.com.joe.service

import br.com.joe.configs.mapper.DozerMapper
import br.com.joe.entity.vo.AddressVO
import br.com.joe.repository.AddressRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AddressService {

    @Autowired
    private lateinit var mapper: DozerMapper

    @Autowired
    private lateinit var addressRepository: AddressRepository

    fun saveAddress(addressVO: AddressVO): AddressVO{
        val address = mapper.toAddress(addressVO)
        val saveAddress = addressRepository.save(address)
        return mapper.toAddressVO(saveAddress)
    }
}