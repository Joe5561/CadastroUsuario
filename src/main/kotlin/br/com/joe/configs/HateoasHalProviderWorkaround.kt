package br.com.joe.configs

import org.springdoc.core.providers.HateoasHalProvider
import org.springdoc.core.providers.ObjectMapperProvider
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.hateoas.HateoasProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.Optional

@Configuration
class HateoasHalProviderWorkaround {

    @Value("\${spring.hateoas.use-hal-as-default-json-media-type:true}")
    private var useHalAsDefaultJsonMediaType: Boolean = true

    @Bean
    fun hateoasHalProvider(
        hateoasPropertiesOptional: Optional<HateoasProperties>,
        objectMapperProvider: ObjectMapperProvider
    ): HateoasHalProvider {
        return object : HateoasHalProvider(hateoasPropertiesOptional, objectMapperProvider) {
            override fun isHalEnabled(): Boolean {
                return useHalAsDefaultJsonMediaType
            }
        }
    }
}