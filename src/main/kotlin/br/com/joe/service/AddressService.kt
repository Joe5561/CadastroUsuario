package br.com.joe.service

import br.com.joe.configs.mapper.DozerMapper
import br.com.joe.entity.vo.AddressVO
import br.com.joe.exception.AddressNotFoundException
import br.com.joe.exception.ExistingNumberException
import br.com.joe.repository.AddressRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AddressService {

    @Autowired
    private lateinit var mapper: DozerMapper

    @Autowired
    private lateinit var addressRepository: AddressRepository

    @Transactional
    fun saveAddress(addressVO: AddressVO): AddressVO{
        val existingNumber = addressRepository.findByNumero(addressVO.numero)
        if (existingNumber != null){
            throw ExistingNumberException("Residence already registered!!")
        }
        val address = mapper.toAddress(addressVO)
        val saveAddress = addressRepository.save(address)
        return mapper.toAddressVO(saveAddress)
    }

    @Transactional
    fun findAllAddress(): List<AddressVO>{
        val address = addressRepository.findAll()
        if (address.isEmpty()){
            AddressNotFoundException("Address not found for this $address")
        }
        return mapper.toAddressVOList(address)
    }
}