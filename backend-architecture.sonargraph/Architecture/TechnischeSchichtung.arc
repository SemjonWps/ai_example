
artifact technischeschichtung
{
    // different architectures for different bounded contexts
    artifact layered
    {
        include "**/filmauswahl/**"
        
        relaxed artifact web
        {
            include "**/web/**"
        }
        
        relaxed artifact application
        {
            include "**/application/**"
        }
        
        relaxed artifact domain
        {
            include "**/domain/**"
        }
    }
    
    artifact hexagonal
    {
        include "**/kartenverkauf/**"
        
        artifact primary_adapters
        {
            include "**/adapters/primary/**"
            connect to primary_ports, domain.data
        }
        
        artifact secondary_adapters
        {
            include "**/adapters/secondary/**"
            connect to secondary_ports, domain.data
        }
        
        artifact application_services
        {
            include "**/application/services/**"
            include "**/application/fixtures/**"
            connect to primary_ports, secondary_ports, domain
        }
        
        artifact primary_ports
        {
            include "**/application/ports/primary/**"
            connect to domain.data
        }
        
        artifact secondary_ports
        {
            include "**/application/ports/secondary/**"
            connect to domain.data
        }
        
        artifact domain
        {
            include "**/application/domain/**"
            
            // data access required for two-way-mapping
            interface data
            {
                include "**/entities/**"
                include "**/valueobjects/**"
                include "**/events/**"
            }
        }
    }
    
    public artifact common
    {
        include "**/common/**"
    }
}

