package br.com.joe.service

import br.com.joe.configs.mapper.DozerMapper
import br.com.joe.entity.vo.CategoryVO
import br.com.joe.repository.CategoryRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CategoryService {

    @Autowired
    private lateinit var categoryRepository: CategoryRepository

    @Autowired
    private lateinit var mapper: DozerMapper

    @Transactional
    fun saveCategory(categoryVO: CategoryVO): CategoryVO{
        val categoria = mapper.toCategoria(categoryVO)
        categoria.categoria = categoria.categoria.uppercase()
        val saveCategoria = categoryRepository.save(categoria)
        return mapper.toCategoriaVO(saveCategoria)
    }
}