

artifact backend
{
    include "**/kartenverkauf/**"
    
    artifact fixtures
    {
        include "**/fixtures/**"
        connect to repositories, jpaentities
    }
    
    artifact applicationservices
    {
        include "**/application/services/**"
        connect to services, factories, repositories, entities, events, valueobjects
    }
    
    artifact controllers
    {
        include "**/web/controllers/**"
        connect to entities, events, valueobjects, dtos, dtomappers
    }
    
    artifact dtomappers
    {
        include "**/web/mappers/**"
        connect to entities, valueobjects, dtos
    }
    
    artifact dtos
    {
        include "**/web/model/**"
    }
    
    artifact repositories
    {
        include "**/persistence/repositories/**"
        connect to entities, valueobjects, jpaentities, entitymappers
    }
    
    artifact entitymappers
    {
        include "**/persistence/mappers/**"
        connect to entities, valueobjects, jpaentities
    }
    
    artifact jpaentities
    {
        include "**/persistence/model/**"
    }
    
    artifact factories
    {
        include "**/factories/**"
        connect to entities, valueobjects
    }
    
    artifact services
    {
        include "**/services/**"
        connect to entities, valueobjects
    }
    
    artifact entities
    {
        include "**/entities/**"
        connect to valueobjects
    }
    
    artifact events
    {
        include "**/events/**"
        connect to valueobjects
    }
    
    artifact valueobjects
    {
        include "**/valueobjects/**"
    }
    
    
}

