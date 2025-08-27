package br.com.joe.configs

import com.github.dozermapper.core.DozerBeanMapperBuilder
import com.github.dozermapper.core.Mapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DozerMapperConfig {

    @Bean(name = ["dozerCoreMapper"])
    fun dozerMapper(): Mapper{
        return DozerBeanMapperBuilder.buildDefault()
    }
}