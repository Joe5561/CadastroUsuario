package br.com.joe.service

import br.com.joe.configs.mapper.DozerMapper
import br.com.joe.entity.dto.CategoryResponseDTO
import br.com.joe.entity.vo.CategoryVO
import br.com.joe.exception.CategoryNotFoundException
import br.com.joe.exception.ExistingCategoryException
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
        val existCategory = categoryRepository.findByCategoria(categoryVO.categoria)
        if (existCategory != null){
            throw ExistingCategoryException("Category already registered!!")
        }
        val saveCategoria = categoryRepository.save(categoria)
        return mapper.toCategoriaVO(saveCategoria)
    }

    @Transactional
    fun findAllCategory(): List<CategoryVO>{
        val category = categoryRepository.findAll()
        if (category.isEmpty()){
            CategoryNotFoundException("Category not found!!")
        }
        return mapper.toCategoriaVOList(category)
    }

    @Transactional
    fun atualizarCategoria(categoria: String, novoCategoria: String): CategoryResponseDTO {
        val categoriaUpper = categoria.uppercase()
        val novoCategoriaUpper = novoCategoria.uppercase()
        val category = categoryRepository.findByCategoria(categoriaUpper)
            ?: throw CategoryNotFoundException("Category not found for this $categoria")
        category.categoria = novoCategoriaUpper
        val categoryUpdate = categoryRepository.save(category)
        val categoryVO = mapper.toCategoriaVO(categoryUpdate)
        return mapper.toCategoryResponseDTO(categoryVO)
    }

    @Transactional
    fun findByCategory(categoria: String): List<CategoryResponseDTO>{
        val category = categoryRepository.findByCategoriaContainingIgnoreCase(categoria)
            ?:throw CategoryNotFoundException("Category not found for this $categoria")
        val categoryVO = mapper.toCategoriaVOList(category)
        return mapper.toCategoryResponseDTOList(categoryVO)
    }
}